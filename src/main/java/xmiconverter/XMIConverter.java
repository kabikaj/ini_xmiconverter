package xmiconverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData;
import de.tudarmstadt.ukp.dkpro.core.io.xmi.XmiWriter;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter;
//import de.tudarmstadt.ukp.dkpro.core.testing.dumper.CasDumpWriter;
import webanno.custom.Page;
import webanno.custom.Section;

/* about correcting the text
 * https://groups.google.com/forum/#!topic/dkpro-core-user/N87H-XyDqik
 * check tutorials on: DKPro Core textnormalizer module
 * 
 * interesting: https://groups.google.com/forum/#!topic/dkpro-core-user/H1wP0d0QTwg
 */

public class XMIConverter {

	public static void main(String[] args) throws UIMAException, FileNotFoundException, IOException, ParseException {
		
        // read configuration file
		File configFile = new File("config.properties");
		FileReader configReader = new FileReader(configFile);
		Properties props = new Properties();
		props.load(configReader);
		
		String INPUT_PATH =  props.getProperty("input_path");
		String OUTPUT_PATH =  props.getProperty("output_path");
		
		// get all input files
		Files.walk(Paths.get(INPUT_PATH)).forEach(filePath ->
		{
			
			System.err.println("Processing file " + filePath.toString()); //DEBUG
		    
			//if (Files.isRegularFile(filePath) && filePath.endsWith(".json")) //DEBUB
		    if (Files.isRegularFile(filePath))
		    {
		    	try {
		    		JSONParser parser = new JSONParser();
		    		//Object obj = parser.parse(new FileReader("input_files/arabic_input_example.json")); //DEBUG
		    		Object obj = parser.parse(new FileReader(filePath.toString()));
		    		JSONObject jsonObject = (JSONObject) obj;

		    		JCas document = JCasFactory.createJCas();
		    		document.setDocumentLanguage("en");  //FIXME it doesn't work with arabic ??
		
		    		DocumentMetaData dmd = DocumentMetaData.create(document);
		    		dmd.setCollectionId("cobhuni");
		    		dmd.setDocumentId(FilenameUtils.removeExtension(filePath.getFileName().toString()));

		    		document.setDocumentText((String) jsonObject.get("text"));

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
		
		    		AnalysisEngineDescription segmenter = AnalysisEngineFactory.createEngineDescription(
		    				StanfordSegmenter.class,
		    				StanfordSegmenter.PARAM_LANGUAGE, "ar");
		
		    		AnalysisEngineDescription tagger = AnalysisEngineFactory.createEngineDescription(
		    				StanfordPosTagger.class,
		    				StanfordSegmenter.PARAM_LANGUAGE, "ar");

		    		AnalysisEngineDescription writer = AnalysisEngineFactory.createEngineDescription(
		    				XmiWriter.class,
		    				XmiWriter.PARAM_TARGET_LOCATION, OUTPUT_PATH);
		    				//XmiWriter.PARAM_TARGET_LOCATION, "target/output_arabic");  //DEBUG

		    		//AnalysisEngineDescription dumper = AnalysisEngineFactory.createEngineDescription(
		    		//		CasDumpWriter.class);
		
		    		SimplePipeline.runPipeline(document, segmenter, tagger, writer);
		    	
		    	} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    
		    			
		    }
		});

	}
}
