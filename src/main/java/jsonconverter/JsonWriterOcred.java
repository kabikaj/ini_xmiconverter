package jsonconverter;

import static org.apache.commons.io.IOUtils.closeQuietly;

import java.io.OutputStream;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import de.tudarmstadt.ukp.dkpro.core.api.io.JCasFileWriter_ImplBase;
import de.tudarmstadt.ukp.dkpro.core.api.ner.type.NamedEntity;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import edu.stanford.nlp.pipeline.Annotation;

import webanno.custom.Page;
import webanno.custom.Section;
import webanno.custom.SemanticLayer;

@TypeCapability(
        inputs={
                "de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData"})

/**
 * UIMA CAS consumer writing the CAS document text in custom format.
 * 
 * Code adapted from TextWriter.
 *
 * @author Alicia Gonzalez Martinez
 */
public class JsonWriterOcred extends JCasFileWriter_ImplBase {

	
    public static final String PARAM_FILENAME_SUFFIX = "filenameSuffix";
    @ConfigurationParameter(name = PARAM_FILENAME_SUFFIX, mandatory = true, defaultValue = ".json")
    private String filenameSuffix;

    @Override
    public void process(JCas aJCas) throws AnalysisEngineProcessException
    {
    	JSONObject outjson = new JSONObject();
    	
    	JSONArray pages = new JSONArray();
    	JSONArray sections = new JSONArray();
    	JSONArray motives = new JSONArray();
    	JSONArray metamotives = new JSONArray();
    	JSONArray errors = new JSONArray();
    	
    	JSONObject annInstance = new JSONObject();    	
    	
        OutputStream docOS = null;
        try {
            docOS = getOutputStream(aJCas, filenameSuffix);
            Iterator<JCas> viewIterator = aJCas.getViewIterator();
            
			outjson.put("content", aJCas.getDocumentText());
            
        	while(viewIterator.hasNext())
        	{
        		JCas view = viewIterator.next();
        		FSIterator<org.apache.uima.jcas.tcas.Annotation> fsi = view.getAnnotationIndex().iterator();
        		
        		while(fsi.hasNext()) {
        			AnnotationFS a = fsi.next();

        			/*String text;	
        			try {
        				text = a.getCoveredText();
        			} catch(Exception e) {
        				text = "ERROR";
        			}*/
        			
        			if(a instanceof Page) {
        				
        		    	annInstance.put("value", ((Page)a).getPagename());
        		    	annInstance.put("start", a.getBegin());
        		    	annInstance.put("end", a.getEnd());
        		    	pages.add(annInstance.clone());
        				
        			} else if(a instanceof Section) {

        		    	annInstance.put("value", ((Section)a).getSectionname());
        		    	annInstance.put("start", a.getBegin());
        		    	annInstance.put("end", a.getEnd());
        		    	sections.add(annInstance.clone());
        				
        			} else if (a instanceof SemanticLayer ) {
        				
        				String aMotive = ((SemanticLayer)a).getMotives();
        				String aMetamotive = ((SemanticLayer)a).getMetamotives();
        				String aError = ((SemanticLayer)a).getError();
        				
        				if (aMotive != null){
        					
            		    	annInstance.put("value", aMotive);
            		    	annInstance.put("start", a.getBegin());
            		    	annInstance.put("end", a.getEnd());
            		    	motives.add(annInstance.clone());
        					
        				}
        				
        				else if (aMetamotive != null){
        					
            		    	annInstance.put("value", aMetamotive);
            		    	annInstance.put("start", a.getBegin());
            		    	annInstance.put("end", a.getEnd());
            		    	metamotives.add(annInstance.clone());
        				}
        				
        				else if (aError != null) {
        					
            		    	annInstance.put("value", aError);
            		    	annInstance.put("start", a.getBegin());
            		    	annInstance.put("end", a.getEnd());
            		    	errors.add(annInstance.clone());
        				}
        				
        				else {
        					System.err.println("KABOOM!!!! Check the layer " + a.getClass().getName() + " in file \"" + docOS.toString() + "\"");
        					//System.exit(1);  //FIXME
        				}
        				
        			} else {
        				System.err.println("Warning!  " + a.getClass().getName()); //FIXME
        			}
        		}
        	}
			outjson.put("pages", pages);
			outjson.put("sections", sections);
			outjson.put("motives", motives);
			outjson.put("metamotives", motives);
			outjson.put("errors", errors);
			
        	IOUtils.write(outjson.toJSONString(), docOS);
            
        }
        
        catch (Exception e) {
            throw new AnalysisEngineProcessException(e);
        }
        
        finally {
            closeQuietly(docOS);
        }
    }        
}



