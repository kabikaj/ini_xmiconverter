package xmiconverter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.cas.Feature;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.DocumentAnnotation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import de.tudarmstadt.ukp.dkpro.core.api.io.JCasFileWriter_ImplBase;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.SegmenterBase;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.io.xmi.XmiWriter;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter;
import webanno.custom.Page;
import webanno.custom.Section;


/**
* Convert json files containing text and annotation offsets into
* enriched xmi file. The text is tokenized, segmented and POS tagged. 
*
* @author  alrazi
* @version 1.0
* @since   06/07/16 
* 
* NOTES
* =====
* - TAGSET: https://zoidberg.ukp.informatik.tu-darmstadt.de/jenkins/job/DKPro%20Core%20Documentation%20%28GitHub%29/de.tudarmstadt.ukp.dkpro.core%24de.tudarmstadt.ukp.dkpro.core.doc-asl/doclinks/3/tagset-reference.html#tagset-ar-atb-pos141
* - MODELS: https://zoidberg.ukp.informatik.tu-darmstadt.de/jenkins/job/DKPro%20Core%20Documentation%20%28GitHub%29/de.tudarmstadt.ukp.dkpro.core%24de.tudarmstadt.ukp.dkpro.core.doc-asl/doclinks/3/model-reference.html#_ar_4
* 
* TODO
* ====
* - check POS annotation, PAT tagset, models (in notes). it fails with arabic puctuation! 
* 
* DEBUG
* =====
* - added [POS] info in the errorChecker() warnings
*/
public class XmiConverter {
	
	private static final char TA_MARBUTA = 'ة';
	private static final char HAMZA = 'ء';
	private static final char SUKUN = 'ْ';
	
	private static String INPUT_PATH;
	private static String OUTPUT_PATH;
	private static int MAX_LEN_TOK;
	
	/**
	 * Loads input and output target paths from config file
	 */
	private static void loadConfig() throws IOException {
		
		File configFile = new File("config.properties");
		FileReader configReader = new FileReader(configFile);
		Properties props = new Properties();
		props.load(configReader);
		
		INPUT_PATH = props.getProperty("input_path");
		OUTPUT_PATH =  props.getProperty("output_path");
		MAX_LEN_TOK = Integer.valueOf(props.getProperty("max_length_token"));
	}
	
	/**
     * Add custom annotation of pages into target doc
	 * 
     * @param jsonObject Input file to extract offset annotations from
	 * @param document Doc to include custom annotation into
	 */
	private static void addPageOffsets(JSONObject jsonObject, JCas document) {

		JSONArray pages = (JSONArray) jsonObject.get("pages");
		@SuppressWarnings("unchecked")
		Iterator<JSONObject> iterpages = pages.iterator();
		while(iterpages.hasNext())
		{
			JSONObject p = iterpages.next();
			Page ann = new Page(
					document,
					Integer.parseInt(p.get("start").toString()),
					Integer.parseInt(p.get("end").toString())
			);
			ann.setPagename(p.get("name").toString());	
			ann.addToIndexes();
		}
	}

	/**
	 * Add custom annotation of sections into target doc
	 * 
     * @param jsonObject Input file to extract offset annotations from
	 * @param document Doc to include custom annotation into
	 */	
    private static void addSectionOffsets(JSONObject jsonObject, JCas document) {
	
		JSONArray sections = (JSONArray) jsonObject.get("sections");
		@SuppressWarnings("unchecked")
		Iterator<JSONObject> itersections = sections.iterator();
		while(itersections.hasNext())
		{
			JSONObject s = itersections.next();
			Section ann = new Section(
					document,
					Integer.parseInt(s.get("start").toString()),
					Integer.parseInt(s.get("end").toString())
			);
			ann.setSectionname(s.get("name").toString());	
			ann.addToIndexes();
		}
	}
    
    /**
	 * Check if a word contains only valid Arabic alphabetic characters
	 * 
	 * @param word string to check
	 * @return boolean true if str is a valid Arabic alphabetic character, false otherwise
     */
	private static boolean isArabicAlpha(String word, boolean ignoreDir, boolean ignoreTatweel) {
		
		for(int i=0; i < word.length(); i++)
		{
		    char chr = word.charAt(i);
		    
		    // char is LTR mark or RTL mark
		    if((int) chr == 0x200E || (int) chr == 0x200F)
		    {
		    	if(ignoreDir == true) {
		    		continue;
		    	}
		    	return false;
		    }

		    // char is tatweel
		    if((int) chr == 0x640)
		    {
		    	if(ignoreTatweel == true) {
		    		continue;
		    	}
		    	return false;
		    }
		    
		    // char is not in Arabic range
		    if(!(chr >= HAMZA && chr <= SUKUN)) {
		    	return false;
		    }
		}
		return true;		
	}
	
    /**
	 * Check if there are possible typos in the tokens of the document and report them
	 * 
	 * @param doc Document to check
	 * @param name Name of the document
     */
	private static void errorChecker(JCas doc, String name) {

		Iterator<Token> tokens = JCasUtil.select(doc, Token.class).iterator();
		
		while(tokens.hasNext())
		{
			Token tokObj = tokens.next();
			String tok = tokObj.getCoveredText();
			int toklen = tok.length();
			
			// skip punctuation
			String POS = tokObj.getPos().getPosValue(); 
			//FIXME used a regex to skip punct with wrong POS annotation
			//FIXME add other tags? IN, CC ??
			if(POS.equals("PUNC") || Pattern.matches("([\\[\\]…\"§/«»❊؟،؛]|[٠-٩]+)", tok)) { //HACK
				continue;
			}
			
			// token is not a valid Arabic word
			if(!isArabicAlpha(tok, true, true)) {
				System.err.println("Warning in scan \"" + name + "\": token \"" + tok + "\" may contain a typo (non-Arabic chars in token)");
				//System.err.println("Warning in scan \"" + name + "\": token \"" + tok + "\" may contain a typo (non-Arabic chars in token)" + "[" + POS + "]"); //DEBUG
				continue; // show token only once
			}
			    
            // exceeds max length
            if(toklen > MAX_LEN_TOK) {
            	System.err.println("Warning in scan \"" + name + "\": token \"" + tok + "\" may contain a typo (word too long)");
            	//System.err.println("Warning in scan \"" + name + "\": token \"" + tok + "\" may contain a typo (word too long)" + "[" + POS + "]"); //DEBUG
            	continue; 
            }     
        
            // ta marbuta (U+0629) in forbidden position. It can go in second-to-last position when last is vowel
            if(toklen > 4 && (tok.substring(0, toklen-3).indexOf(TA_MARBUTA) != -1)) {
            	System.err.println("Warning in scan \"" + name + "\": token \"" + tok + "\" may contain a typo (ta marbuta in the middle)");
            	//System.err.println("Warning in scan \"" + name + "\": token \"" + tok + "\" may contain a typo (ta marbuta in the middle)" + "[" + POS + "]"); //DEBUG
            	continue; 
            }

            // there cannot be more than one vocalic diacritic together: "aa" (0x064e0x064e), "uu" (0x064f0x064f), "ii"(0x0650x0650)
            if(tok.contains("ََ") || tok.contains("ُُ") || tok.contains("ِِ")) {
            	System.err.println("Error in scan \"" + name + "\": token \"" + tok + "\" contain 2 or more vocalic diacritics together");
            	//System.err.println("Error in scan \"" + name + "\": token \"" + tok + "\" contain 2 or more vocalic diacritics together" + "[" + POS + "]"); //DEBUG
            }	
		}
	}
	
	
	/**
	 * Parse input data, enrich it and perform conversion 
	 * 
	 */
	public static void main(String[] args) throws UIMAException, ParseException, IOException {
		
		loadConfig();
				
		Files.walk(Paths.get(INPUT_PATH)).forEach(filePath ->
		{
		    if (Files.isRegularFile(filePath) && filePath.toString().endsWith(".json"))
		    {
		    	System.err.println("Processing file " + filePath.toString()); //DEBUG
		    	
		    	try {
		    		JSONParser parser = new JSONParser();
		    		Object obj = parser.parse(new FileReader(filePath.toString()));
		    		JSONObject jsonObject = (JSONObject) obj;

		    		JCas document = JCasFactory.createJCas();
		    		document.setDocumentLanguage("en");  //FIXME it breaks if language is "ar"
		
		    		DocumentMetaData dmd = DocumentMetaData.create(document);
		    		dmd.setCollectionId("cobhuni");
		    		dmd.setDocumentId(FilenameUtils.removeExtension(filePath.getFileName().toString()));

		    		document.setDocumentText((String) jsonObject.get("text"));

		    		addPageOffsets(jsonObject, document);
		    		addSectionOffsets(jsonObject, document);
		
		    		AnalysisEngineDescription segmenter = AnalysisEngineFactory.createEngineDescription(
		    				StanfordSegmenter.class,
		    				SegmenterBase.PARAM_LANGUAGE, "ar");
		
		    		AnalysisEngineDescription tagger = AnalysisEngineFactory.createEngineDescription(
		    				StanfordPosTagger.class,
		    				//StanfordPosTagger.PARAM_PRINT_TAGSET, true,  //DEBUG
		    				SegmenterBase.PARAM_LANGUAGE, "ar");

		    		AnalysisEngineDescription writer = AnalysisEngineFactory.createEngineDescription(
		    				XmiWriter.class,
		    				JCasFileWriter_ImplBase.PARAM_TARGET_LOCATION, OUTPUT_PATH);
		
		    		SimplePipeline.runPipeline(document, segmenter, tagger, writer);
		    		
		    		errorChecker(document, filePath.getFileName().toString());
		    		
		    	} catch (Exception e1) {
					e1.printStackTrace();
				}
		    }
		});

	}
}
