package xmiconverter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import de.tudarmstadt.ukp.dkpro.core.api.io.JCasFileWriter_ImplBase;
import de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.SegmenterBase;
import de.tudarmstadt.ukp.dkpro.core.io.xmi.XmiWriter;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter;

/**
* Convert altafsir json files into xmi.
* The text is tokenized, segmented and POS tagged. 
*
* @author  alrazi
* @version 1.0
* @since   28/07/16
* 
* usage:
*   $ mvn clean dependency:copy-dependencies package
*   $ java -cp target/dependency/*:target/xmiconverter-0.0.1-SNAPSHOT.jar xmiconverter/XmiConverterAltafsir
*  
* TODO
* ====
* - Check errors in POS annotation.
* - Convert offsets too (aya|verse|hadith). But they need first a preprocessing.
* - In a bunch of files, eg. 7-53-53-1-62, edu.stanford.ntl.process.PBTLexer next
*   reports a warning> Untokenizable (U+200D, decimal 8205). Check this. 
* 
*/
public class XmiConverterAltafsir {

	private static String INPUT_PATH;
	private static String OUTPUT_PATH;
	
	/**
	 * Loads input and output target paths from config file
	 */
	private static void loadConfig() throws IOException {
		
		File configFile = new File("config.properties");
		FileReader configReader = new FileReader(configFile);
		Properties props = new Properties();
		props.load(configReader);
		
		INPUT_PATH = props.getProperty("input_path_altafsir");
		OUTPUT_PATH =  props.getProperty("output_path_altafsir");
	}
	
	public static void main(String[] args) throws IOException {

		loadConfig();
		
		Files.walk(Paths.get(INPUT_PATH)).forEach(filePath ->
		{
		    if (Files.isRegularFile(filePath) && filePath.toString().endsWith(".json"))
		    {
		    	System.err.println("\n** Processing file " + filePath.toString());
		    	
		    	try {
		    		JSONParser parser = new JSONParser();
		    		Object obj = parser.parse(new FileReader(filePath.toString()));
		    		JSONObject jsonObject = (JSONObject) obj;

		    		JCas document = JCasFactory.createJCas();
		    		document.setDocumentLanguage("en");
		
		    		DocumentMetaData dmd = DocumentMetaData.create(document);
		    		dmd.setCollectionId("cobhuni");
		    		dmd.setDocumentId(FilenameUtils.removeExtension(filePath.getFileName().toString()));

		    		document.setDocumentText((String) jsonObject.get("text"));
		
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
		    				    		
		    	} catch (Exception e1) {
					e1.printStackTrace();
				}
		    }
		});


	}

}
