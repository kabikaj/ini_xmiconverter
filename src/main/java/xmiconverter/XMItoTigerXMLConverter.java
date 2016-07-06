package xmiconverter;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import de.tudarmstadt.ukp.dkpro.core.io.tiger.TigerXmlWriter;
import de.tudarmstadt.ukp.dkpro.core.io.xmi.XmiReader;
import de.tudarmstadt.ukp.dkpro.core.testing.dumper.CasDumpWriter;


public class XMItoTigerXMLConverter {

	public static void main(String[] args) throws Exception
	{
		CollectionReaderDescription reader = CollectionReaderFactory.createReaderDescription(XmiReader.class,
				XmiReader.PARAM_SOURCE_LOCATION, "target/output/arabic_example.xmi",
				XmiReader.PARAM_LANGUAGE, "en");
		
		// tigerxml does not support custom annotation layers
		AnalysisEngineDescription writer = AnalysisEngineFactory.createEngineDescription(TigerXmlWriter.class,
				TigerXmlWriter.PARAM_TARGET_LOCATION, "target/output");
		
		AnalysisEngineDescription dumper = AnalysisEngineFactory.createEngineDescription(
				CasDumpWriter.class);
		
		SimplePipeline.runPipeline(reader, writer, dumper);	}
	
}
