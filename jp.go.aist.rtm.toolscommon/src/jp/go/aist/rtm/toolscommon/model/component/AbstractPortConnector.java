/**
 * <copyright>
 * </copyright>
 *
 * $Id: AbstractPortConnector.java,v 1.2 2008/03/05 12:01:47 terui Exp $
 */
package jp.go.aist.rtm.toolscommon.model.component;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Port Connector</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.AbstractPortConnector#getConnectorProfile <em>Connector Profile</em>}</li>
 * </ul>
 * </p>
 *
 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#getAbstractPortConnector()
 * @model abstract="true"
 * @generated
 */
public interface AbstractPortConnector extends Connector {
	/**
	 * Returns the value of the '<em><b>Connector Profile</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Connector Profile</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Connector Profile</em>' containment reference.
	 * @see #setConnectorProfile(ConnectorProfile)
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#getAbstractPortConnector_ConnectorProfile()
	 * @model containment="true"
	 * @generated
	 */
	ConnectorProfile getConnectorProfile();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractPortConnector#getConnectorProfile <em>Connector Profile</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Connector Profile</em>' containment reference.
	 * @see #getConnectorProfile()
	 * @generated
	 */
	void setConnectorProfile(ConnectorProfile value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	boolean createConnectorR();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	boolean deleteConnectorR();

} // AbstractPortConnector