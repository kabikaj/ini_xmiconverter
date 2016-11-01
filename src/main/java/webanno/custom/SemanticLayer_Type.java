
/* First created by JCasGen Mon Oct 31 14:59:47 CET 2016 */
package webanno.custom;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Mon Oct 31 14:59:47 CET 2016
 * @generated */
public class SemanticLayer_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (SemanticLayer_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = SemanticLayer_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new SemanticLayer(addr, SemanticLayer_Type.this);
  			   SemanticLayer_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new SemanticLayer(addr, SemanticLayer_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = SemanticLayer.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("webanno.custom.SemanticLayer");
 
  /** @generated */
  final Feature casFeat_error;
  /** @generated */
  final int     casFeatCode_error;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getError(int addr) {
        if (featOkTst && casFeat_error == null)
      jcas.throwFeatMissing("error", "webanno.custom.SemanticLayer");
    return ll_cas.ll_getStringValue(addr, casFeatCode_error);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setError(int addr, String v) {
        if (featOkTst && casFeat_error == null)
      jcas.throwFeatMissing("error", "webanno.custom.SemanticLayer");
    ll_cas.ll_setStringValue(addr, casFeatCode_error, v);}
    
  
 
  /** @generated */
  final Feature casFeat_motives;
  /** @generated */
  final int     casFeatCode_motives;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getMotives(int addr) {
        if (featOkTst && casFeat_motives == null)
      jcas.throwFeatMissing("motives", "webanno.custom.SemanticLayer");
    return ll_cas.ll_getStringValue(addr, casFeatCode_motives);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setMotives(int addr, String v) {
        if (featOkTst && casFeat_motives == null)
      jcas.throwFeatMissing("motives", "webanno.custom.SemanticLayer");
    ll_cas.ll_setStringValue(addr, casFeatCode_motives, v);}
    
  
 
  /** @generated */
  final Feature casFeat_metamotives;
  /** @generated */
  final int     casFeatCode_metamotives;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getMetamotives(int addr) {
        if (featOkTst && casFeat_metamotives == null)
      jcas.throwFeatMissing("metamotives", "webanno.custom.SemanticLayer");
    return ll_cas.ll_getStringValue(addr, casFeatCode_metamotives);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setMetamotives(int addr, String v) {
        if (featOkTst && casFeat_metamotives == null)
      jcas.throwFeatMissing("metamotives", "webanno.custom.SemanticLayer");
    ll_cas.ll_setStringValue(addr, casFeatCode_metamotives, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public SemanticLayer_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_error = jcas.getRequiredFeatureDE(casType, "error", "uima.cas.String", featOkTst);
    casFeatCode_error  = (null == casFeat_error) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_error).getCode();

 
    casFeat_motives = jcas.getRequiredFeatureDE(casType, "motives", "uima.cas.String", featOkTst);
    casFeatCode_motives  = (null == casFeat_motives) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_motives).getCode();

 
    casFeat_metamotives = jcas.getRequiredFeatureDE(casType, "metamotives", "uima.cas.String", featOkTst);
    casFeatCode_metamotives  = (null == casFeat_metamotives) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_metamotives).getCode();

  }
}



    