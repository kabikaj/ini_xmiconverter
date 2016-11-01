
/* First created by JCasGen Wed Jun 22 17:24:23 CEST 2016 */
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
public class Section_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Section_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Section_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Section(addr, Section_Type.this);
  			   Section_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Section(addr, Section_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Section.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("webanno.custom.Section");
 
  /** @generated */
  final Feature casFeat_sectionname;
  /** @generated */
  final int     casFeatCode_sectionname;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getSectionname(int addr) {
        if (featOkTst && casFeat_sectionname == null)
      jcas.throwFeatMissing("sectionname", "webanno.custom.Section");
    return ll_cas.ll_getStringValue(addr, casFeatCode_sectionname);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSectionname(int addr, String v) {
        if (featOkTst && casFeat_sectionname == null)
      jcas.throwFeatMissing("sectionname", "webanno.custom.Section");
    ll_cas.ll_setStringValue(addr, casFeatCode_sectionname, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Section_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_sectionname = jcas.getRequiredFeatureDE(casType, "sectionname", "uima.cas.String", featOkTst);
    casFeatCode_sectionname  = (null == casFeat_sectionname) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_sectionname).getCode();

  }
}



    