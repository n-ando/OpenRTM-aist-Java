/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package jp.go.aist.rtm.rtcbuilder.model.component;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Port Base</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link jp.go.aist.rtm.rtcbuilder.model.component.PortBase#getDirection <em>Direction</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtcbuilder.model.component.PortBase#getIndex <em>Index</em>}</li>
 * </ul>
 * </p>
 *
 * @see jp.go.aist.rtm.rtcbuilder.model.component.ComponentPackage#getPortBase()
 * @model
 * @generated
 */
public interface PortBase extends EObject {
	/**
	 * Returns the value of the '<em><b>Direction</b></em>' attribute.
	 * The literals are from the enumeration {@link jp.go.aist.rtm.rtcbuilder.model.component.PortDirection}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Direction</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Direction</em>' attribute.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.PortDirection
	 * @see #setDirection(PortDirection)
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.ComponentPackage#getPortBase_Direction()
	 * @model
	 * @generated
	 */
	PortDirection getDirection();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.rtcbuilder.model.component.PortBase#getDirection <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Direction</em>' attribute.
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.PortDirection
	 * @see #getDirection()
	 * @generated
	 */
	void setDirection(PortDirection value);

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
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.ComponentPackage#getPortBase_Index()
	 * @model
	 * @generated
	 */
	int getIndex();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.rtcbuilder.model.component.PortBase#getIndex <em>Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Index</em>' attribute.
	 * @see #getIndex()
	 * @generated
	 */
	void setIndex(int value);

} // PortBase