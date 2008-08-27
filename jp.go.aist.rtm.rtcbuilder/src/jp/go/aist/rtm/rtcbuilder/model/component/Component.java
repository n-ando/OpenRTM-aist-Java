/**
 * <copyright>
 * </copyright>
 *
 * $Id: Component.java,v 1.1 2007/12/25 05:43:00 tsakamoto Exp $
 */
package jp.go.aist.rtm.rtcbuilder.model.component;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Component</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link jp.go.aist.rtm.rtcbuilder.model.component.Component#getComponent_Name <em>Component Name</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtcbuilder.model.component.Component#getDataInPorts <em>Data In Ports</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtcbuilder.model.component.Component#getDataOutPorts <em>Data Out Ports</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtcbuilder.model.component.Component#getServicePorts <em>Service Ports</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtcbuilder.model.component.Component#getRightMaxNum <em>Right Max Num</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtcbuilder.model.component.Component#getLeftMaxNum <em>Left Max Num</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtcbuilder.model.component.Component#getTopMaxNum <em>Top Max Num</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtcbuilder.model.component.Component#getBottomMaxNum <em>Bottom Max Num</em>}</li>
 * </ul>
 * </p>
 *
 * @see jp.go.aist.rtm.rtcbuilder.model.component.ComponentPackage#getComponent()
 * @model
 * @generated
 */
public interface Component extends EObject {
	/**
	 * Returns the value of the '<em><b>Component Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component Name</em>' attribute.
	 * @see #setComponent_Name(String)
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.ComponentPackage#getComponent_Component_Name()
	 * @model
	 * @generated
	 */
	String getComponent_Name();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.rtcbuilder.model.component.Component#getComponent_Name <em>Component Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component Name</em>' attribute.
	 * @see #getComponent_Name()
	 * @generated
	 */
	void setComponent_Name(String value);

	/**
	 * Returns the value of the '<em><b>Data In Ports</b></em>' reference list.
	 * The list contents are of type {@link jp.go.aist.rtm.rtcbuilder.model.component.DataInPort}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data In Ports</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data In Ports</em>' reference list.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.ComponentPackage#getComponent_DataInPorts()
	 * @model type="jp.go.aist.rtm.rtcbuilder.model.component.DataInPort"
	 * @generated NOT
	 */
	List getDataInPorts();

	/**
	 * Returns the value of the '<em><b>Data Out Ports</b></em>' reference list.
	 * The list contents are of type {@link jp.go.aist.rtm.rtcbuilder.model.component.DataOutPort}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data Out Ports</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data Out Ports</em>' reference list.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.ComponentPackage#getComponent_DataOutPorts()
	 * @model type="jp.go.aist.rtm.rtcbuilder.model.component.DataOutPort"
	 * @generated NOT
	 */
	List getDataOutPorts();

	/**
	 * Returns the value of the '<em><b>Service Ports</b></em>' reference list.
	 * The list contents are of type {@link jp.go.aist.rtm.rtcbuilder.model.component.ServicePort}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Service Ports</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Service Ports</em>' reference list.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.ComponentPackage#getComponent_ServicePorts()
	 * @model type="jp.go.aist.rtm.rtcbuilder.model.component.ServicePort"
	 * @generated NOT
	 */
	List getServicePorts();

	/**
	 * Returns the value of the '<em><b>Right Max Num</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Right Max Num</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Right Max Num</em>' attribute.
	 * @see #isSetRightMaxNum()
	 * @see #unsetRightMaxNum()
	 * @see #setRightMaxNum(int)
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.ComponentPackage#getComponent_RightMaxNum()
	 * @model default="0" unsettable="true"
	 * @generated
	 */
	int getRightMaxNum();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.rtcbuilder.model.component.Component#getRightMaxNum <em>Right Max Num</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Right Max Num</em>' attribute.
	 * @see #isSetRightMaxNum()
	 * @see #unsetRightMaxNum()
	 * @see #getRightMaxNum()
	 * @generated
	 */
	void setRightMaxNum(int value);

	/**
	 * Unsets the value of the '{@link jp.go.aist.rtm.rtcbuilder.model.component.Component#getRightMaxNum <em>Right Max Num</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetRightMaxNum()
	 * @see #getRightMaxNum()
	 * @see #setRightMaxNum(int)
	 * @generated
	 */
	void unsetRightMaxNum();

	/**
	 * Returns whether the value of the '{@link jp.go.aist.rtm.rtcbuilder.model.component.Component#getRightMaxNum <em>Right Max Num</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Right Max Num</em>' attribute is set.
	 * @see #unsetRightMaxNum()
	 * @see #getRightMaxNum()
	 * @see #setRightMaxNum(int)
	 * @generated
	 */
	boolean isSetRightMaxNum();

	/**
	 * Returns the value of the '<em><b>Left Max Num</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Left Max Num</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Left Max Num</em>' attribute.
	 * @see #isSetLeftMaxNum()
	 * @see #unsetLeftMaxNum()
	 * @see #setLeftMaxNum(int)
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.ComponentPackage#getComponent_LeftMaxNum()
	 * @model default="0" unsettable="true"
	 * @generated
	 */
	int getLeftMaxNum();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.rtcbuilder.model.component.Component#getLeftMaxNum <em>Left Max Num</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Left Max Num</em>' attribute.
	 * @see #isSetLeftMaxNum()
	 * @see #unsetLeftMaxNum()
	 * @see #getLeftMaxNum()
	 * @generated
	 */
	void setLeftMaxNum(int value);

	/**
	 * Unsets the value of the '{@link jp.go.aist.rtm.rtcbuilder.model.component.Component#getLeftMaxNum <em>Left Max Num</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetLeftMaxNum()
	 * @see #getLeftMaxNum()
	 * @see #setLeftMaxNum(int)
	 * @generated
	 */
	void unsetLeftMaxNum();

	/**
	 * Returns whether the value of the '{@link jp.go.aist.rtm.rtcbuilder.model.component.Component#getLeftMaxNum <em>Left Max Num</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Left Max Num</em>' attribute is set.
	 * @see #unsetLeftMaxNum()
	 * @see #getLeftMaxNum()
	 * @see #setLeftMaxNum(int)
	 * @generated
	 */
	boolean isSetLeftMaxNum();

	/**
	 * Returns the value of the '<em><b>Top Max Num</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Top Max Num</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Top Max Num</em>' attribute.
	 * @see #isSetTopMaxNum()
	 * @see #unsetTopMaxNum()
	 * @see #setTopMaxNum(int)
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.ComponentPackage#getComponent_TopMaxNum()
	 * @model default="0" unsettable="true"
	 * @generated
	 */
	int getTopMaxNum();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.rtcbuilder.model.component.Component#getTopMaxNum <em>Top Max Num</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Top Max Num</em>' attribute.
	 * @see #isSetTopMaxNum()
	 * @see #unsetTopMaxNum()
	 * @see #getTopMaxNum()
	 * @generated
	 */
	void setTopMaxNum(int value);

	/**
	 * Unsets the value of the '{@link jp.go.aist.rtm.rtcbuilder.model.component.Component#getTopMaxNum <em>Top Max Num</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetTopMaxNum()
	 * @see #getTopMaxNum()
	 * @see #setTopMaxNum(int)
	 * @generated
	 */
	void unsetTopMaxNum();

	/**
	 * Returns whether the value of the '{@link jp.go.aist.rtm.rtcbuilder.model.component.Component#getTopMaxNum <em>Top Max Num</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Top Max Num</em>' attribute is set.
	 * @see #unsetTopMaxNum()
	 * @see #getTopMaxNum()
	 * @see #setTopMaxNum(int)
	 * @generated
	 */
	boolean isSetTopMaxNum();

	/**
	 * Returns the value of the '<em><b>Bottom Max Num</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bottom Max Num</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bottom Max Num</em>' attribute.
	 * @see #isSetBottomMaxNum()
	 * @see #unsetBottomMaxNum()
	 * @see #setBottomMaxNum(int)
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.ComponentPackage#getComponent_BottomMaxNum()
	 * @model default="0" unsettable="true"
	 * @generated
	 */
	int getBottomMaxNum();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.rtcbuilder.model.component.Component#getBottomMaxNum <em>Bottom Max Num</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Bottom Max Num</em>' attribute.
	 * @see #isSetBottomMaxNum()
	 * @see #unsetBottomMaxNum()
	 * @see #getBottomMaxNum()
	 * @generated
	 */
	void setBottomMaxNum(int value);

	/**
	 * Unsets the value of the '{@link jp.go.aist.rtm.rtcbuilder.model.component.Component#getBottomMaxNum <em>Bottom Max Num</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetBottomMaxNum()
	 * @see #getBottomMaxNum()
	 * @see #setBottomMaxNum(int)
	 * @generated
	 */
	void unsetBottomMaxNum();

	/**
	 * Returns whether the value of the '{@link jp.go.aist.rtm.rtcbuilder.model.component.Component#getBottomMaxNum <em>Bottom Max Num</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Bottom Max Num</em>' attribute is set.
	 * @see #unsetBottomMaxNum()
	 * @see #getBottomMaxNum()
	 * @see #setBottomMaxNum(int)
	 * @generated
	 */
	boolean isSetBottomMaxNum();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void addDataInport(DataInPort inport);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void addDataOutport(DataOutPort outport);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void addServiceport(ServicePort serviceport);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void clearDataOutports();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void clearServiceports();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void clearDataInports();

} // Component