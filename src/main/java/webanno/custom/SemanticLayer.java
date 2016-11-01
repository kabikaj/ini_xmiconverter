

/* First created by JCasGen Mon Oct 31 14:59:47 CET 2016 */
package webanno.custom;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Mon Oct 31 14:59:47 CET 2016
 * XML source: /home/alicia/COBHUNI/development/corpus/annotation/xmiconverter/src/main/resources/desc/type/typeSystemDescriptor.xml
 * @generated */
public class SemanticLayer extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(SemanticLayer.class);
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
  protected SemanticLayer() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public SemanticLayer(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public SemanticLayer(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public SemanticLayer(JCas jcas, int begin, int end) {
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
  //* Feature: error

  /** getter for error - gets 
   * @generated
   * @return value of the feature 
   */
  public String getError() {
    if (SemanticLayer_Type.featOkTst && ((SemanticLayer_Type)jcasType).casFeat_error == null)
      jcasType.jcas.throwFeatMissing("error", "webanno.custom.SemanticLayer");
    return jcasType.ll_cas.ll_getStringValue(addr, ((SemanticLayer_Type)jcasType).casFeatCode_error);}
    
  /** setter for error - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setError(String v) {
    if (SemanticLayer_Type.featOkTst && ((SemanticLayer_Type)jcasType).casFeat_error == null)
      jcasType.jcas.throwFeatMissing("error", "webanno.custom.SemanticLayer");
    jcasType.ll_cas.ll_setStringValue(addr, ((SemanticLayer_Type)jcasType).casFeatCode_error, v);}    
   
    
  //*--------------*
  //* Feature: motives

  /** getter for motives - gets 
   * @generated
   * @return value of the feature 
   */
  public String getMotives() {
    if (SemanticLayer_Type.featOkTst && ((SemanticLayer_Type)jcasType).casFeat_motives == null)
      jcasType.jcas.throwFeatMissing("motives", "webanno.custom.SemanticLayer");
    return jcasType.ll_cas.ll_getStringValue(addr, ((SemanticLayer_Type)jcasType).casFeatCode_motives);}
    
  /** setter for motives - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setMotives(String v) {
    if (SemanticLayer_Type.featOkTst && ((SemanticLayer_Type)jcasType).casFeat_motives == null)
      jcasType.jcas.throwFeatMissing("motives", "webanno.custom.SemanticLayer");
    jcasType.ll_cas.ll_setStringValue(addr, ((SemanticLayer_Type)jcasType).casFeatCode_motives, v);}    
   
    
  //*--------------*
  //* Feature: metamotives

  /** getter for metamotives - gets 
   * @generated
   * @return value of the feature 
   */
  public String getMetamotives() {
    if (SemanticLayer_Type.featOkTst && ((SemanticLayer_Type)jcasType).casFeat_metamotives == null)
      jcasType.jcas.throwFeatMissing("metamotives", "webanno.custom.SemanticLayer");
    return jcasType.ll_cas.ll_getStringValue(addr, ((SemanticLayer_Type)jcasType).casFeatCode_metamotives);}
    
  /** setter for metamotives - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setMetamotives(String v) {
    if (SemanticLayer_Type.featOkTst && ((SemanticLayer_Type)jcasType).casFeat_metamotives == null)
      jcasType.jcas.throwFeatMissing("metamotives", "webanno.custom.SemanticLayer");
    jcasType.ll_cas.ll_setStringValue(addr, ((SemanticLayer_Type)jcasType).casFeatCode_metamotives, v);}    
  }

    