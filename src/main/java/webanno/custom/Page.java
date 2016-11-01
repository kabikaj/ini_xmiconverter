

/* First created by JCasGen Wed Jun 22 17:04:05 CEST 2016 */
package webanno.custom;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Mon Oct 31 14:59:47 CET 2016
 * XML source: /home/alicia/COBHUNI/development/corpus/annotation/xmiconverter/src/main/resources/desc/type/typeSystemDescriptor.xml
 * @generated */
public class Page extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Page.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Page() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Page(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Page(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Page(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
  //*--------------*
  //* Feature: pagename

  /** getter for pagename - gets 
   * @generated
   * @return value of the feature 
   */
  public String getPagename() {
    if (Page_Type.featOkTst && ((Page_Type)jcasType).casFeat_pagename == null)
      jcasType.jcas.throwFeatMissing("pagename", "webanno.custom.Page");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Page_Type)jcasType).casFeatCode_pagename);}
    
  /** setter for pagename - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setPagename(String v) {
    if (Page_Type.featOkTst && ((Page_Type)jcasType).casFeat_pagename == null)
      jcasType.jcas.throwFeatMissing("pagename", "webanno.custom.Page");
    jcasType.ll_cas.ll_setStringValue(addr, ((Page_Type)jcasType).casFeatCode_pagename, v);}    
  }

    