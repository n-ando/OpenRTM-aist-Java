/**
 * <copyright>
 * </copyright>
 *
 * $Id: DataInPort.java,v 1.1 2007/12/25 05:43:00 tsakamoto Exp $
 */
package jp.go.aist.rtm.rtcbuilder.model.component;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Data In Port</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link jp.go.aist.rtm.rtcbuilder.model.component.DataInPort#getInPort_Name <em>In Port Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see jp.go.aist.rtm.rtcbuilder.model.component.ComponentPackage#getDataInPort()
 * @model
 * @generated
 */
public interface DataInPort extends PortBase {
	/**
	 * Returns the value of the '<em><b>In Port Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>In Port Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>In Port Name</em>' attribute.
	 * @see #setInPort_Name(String)
	 * @see jp.go.aist.rtm.rtcbuilder.model.component.ComponentPackage#getDataInPort_InPort_Name()
	 * @model
	 * @generated
	 */
	String getInPort_Name();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.rtcbuilder.model.component.DataInPort#getInPort_Name <em>In Port Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>In Port Name</em>' attribute.
	 * @see #getInPort_Name()
	 * @generated
	 */
	void setInPort_Name(String value);

} // DataInPort