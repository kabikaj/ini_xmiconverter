/*******************************************************************************
 * Copyright 2016
 * COBHUNI, Universität Hamburg
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

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
 * Code adapted from TextWriter. Original author Richard Eckart de Castilho. Ubiquitous Knowledge Processing (UKP) Lab. Technische Universität Darmstadt.
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
        					System.err.println("KABOOM!!!! The annotation instance " + a.getClass().getName() + " in file \"" + docOS.toString() + "\" is empty.");
        					//System.exit(1);  //FIXME
        				}
        				
        			} else {
        				System.err.println("Warning! Not processing type " + a.getClass().getName());
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



