/**
 * <copyright>
 * </copyright>
 *
 * $Id: ServiceInterface.java,v 1.1 2007/12/25 05:43:00 tsakamoto Exp $
 */
package jp.go.aist.rtm.rtcbuilder.model.component;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Service Interface</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link jp.go.aist.rtm.rtcbuilder.model.component.ServiceInterface#getServiceInterface_Name <em>Service Interface Name</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtcbuilder.model.component.ServiceInterface#getDirection <em>Direction</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtcbuilder.model.component.ServiceInterface#getIndex <em>Index</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtcbuilder.model.component.ServiceInterface#getParentDirection <em>Parent Direction</em>}</li>
 * </ul>
 * </p>
 *
 * @see jp.go.aist.rtm.rtcbuilder.model.component.ComponentPackage#getServiceInterface()
 * @model
 * @generated
 */
public interface ServiceInterface extends EObject {
	/**
	 * Returns the value of the '<em><b>Service Interface Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Service Interface Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Service Interface Name</em>' attribute.
	 * @see #setServiceInterface_Name(String)
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.ComponentPackage#getServiceInterface_ServiceInterface_Name()
	 * @model
	 * @generated
	 */
	String getServiceInterface_Name();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.rtcbuilder.model.component.ServiceInterface#getServiceInterface_Name <em>Service Interface Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Service Interface Name</em>' attribute.
	 * @see #getServiceInterface_Name()
	 * @generated
	 */
	void setServiceInterface_Name(String value);

	/**
	 * Returns the value of the '<em><b>Direction</b></em>' attribute.
	 * The literals are from the enumeration {@link jp.go.aist.rtm.rtcbuilder.model.component.InterfaceDirection}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Direction</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Direction</em>' attribute.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.InterfaceDirection
	 * @see #setDirection(InterfaceDirection)
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.ComponentPackage#getServiceInterface_Direction()
	 * @model
	 * @generated
	 */
	InterfaceDirection getDirection();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.rtcbuilder.model.component.ServiceInterface#getDirection <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Direction</em>' attribute.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.InterfaceDirection
	 * @see #getDirection()
	 * @generated
	 */
	void setDirection(InterfaceDirection value);

	/**
	 * Returns the value of the '<em><b>Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Index</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Index</em>' attribute.
	 * @see #setIndex(int)
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.ComponentPackage#getServiceInterface_Index()
	 * @model
	 * @generated
	 */
	int getIndex();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.rtcbuilder.model.component.ServiceInterface#getIndex <em>Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Index</em>' attribute.
	 * @see #getIndex()
	 * @generated
	 */
	void setIndex(int value);

	/**
	 * Returns the value of the '<em><b>Parent Direction</b></em>' attribute.
	 * The literals are from the enumeration {@link jp.go.aist.rtm.rtcbuilder.model.component.PortDirection}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent Direction</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent Direction</em>' attribute.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.PortDirection
	 * @see #setParentDirection(PortDirection)
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.ComponentPackage#getServiceInterface_ParentDirection()
	 * @model
	 * @generated
	 */
	PortDirection getParentDirection();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.rtcbuilder.model.component.ServiceInterface#getParentDirection <em>Parent Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent Direction</em>' attribute.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.PortDirection
	 * @see #getParentDirection()
	 * @generated
	 */
	void setParentDirection(PortDirection value);

} // ServiceInterface