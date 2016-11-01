package jsonconverter;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpSegmenter;

import de.tudarmstadt.ukp.dkpro.core.io.xmi.XmiReader;
import de.tudarmstadt.ukp.dkpro.core.io.xmi.XmiWriter;

import de.tudarmstadt.ukp.dkpro.core.io.xml.XmlReader;
import de.tudarmstadt.ukp.dkpro.core.io.xml.XmlWriterInline;

import de.tudarmstadt.ukp.dkpro.core.io.text.TextReader;
import de.tudarmstadt.ukp.dkpro.core.io.text.TextWriter;

import de.tudarmstadt.ukp.dkpro.core.io.tcf.TcfWriter;


public class Pipeline {
	
	private static String INPUT_PATH_OCRED;
	private static String OUTPUT_PATH_OCRED;
	
	/**
	 * Loads input and output target paths from config file
	 */
	private static void loadConfig() throws IOException {
		
		File configFile = new File("config.properties");
		FileReader configReader = new FileReader(configFile);
		Properties props = new Properties();
		props.load(configReader);
		
		INPUT_PATH_OCRED = props.getProperty("xmi_to_json_input_ocred");
		OUTPUT_PATH_OCRED =  props.getProperty("xmi_to_json_output_ocred");
	}

	public static void main(String[] args) throws Exception {
		
		loadConfig();
		
		runPipeline(
		        createReaderDescription(XmiReader.class,
		                                XmiReader.PARAM_SOURCE_LOCATION, INPUT_PATH_OCRED,
		                                XmiReader.PARAM_LANGUAGE, "en",
		                                XmiReader.PARAM_LENIENT, true),
		        
		        createEngineDescription(JsonWriterOcred.class,
		        		                JsonWriterOcred.PARAM_TARGET_LOCATION, OUTPUT_PATH_OCRED));
	}

}
