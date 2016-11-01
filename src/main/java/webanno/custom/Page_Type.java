
/* First created by JCasGen Wed Jun 22 17:04:05 CEST 2016 */
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
public class Page_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Page_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Page_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Page(addr, Page_Type.this);
  			   Page_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Page(addr, Page_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Page.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("webanno.custom.Page");



  /** @generated */
  final Feature casFeat_pagename;
  /** @generated */
  final int     casFeatCode_pagename;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getPagename(int addr) {
        if (featOkTst && casFeat_pagename == null)
      jcas.throwFeatMissing("pagename", "webanno.custom.Page");
    return ll_cas.ll_getStringValue(addr, casFeatCode_pagename);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setPagename(int addr, String v) {
        if (featOkTst && casFeat_pagename == null)
      jcas.throwFeatMissing("pagename", "webanno.custom.Page");
    ll_cas.ll_setStringValue(addr, casFeatCode_pagename, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Page_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_pagename = jcas.getRequiredFeatureDE(casType, "pagename", "uima.cas.String", featOkTst);
    casFeatCode_pagename  = (null == casFeat_pagename) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_pagename).getCode();

  }
}



    