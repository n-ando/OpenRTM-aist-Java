package jp.go.aist.rtm.rtclink.model.component;

import jp.go.aist.rtm.rtclink.model.core.WrapperObject;

import org.eclipse.emf.common.util.EMap;

/**
 * ê⁄ë±Çï\åªÇ∑ÇÈÉNÉâÉX
 * 
 * @model abstract="true"
 */
public interface Connector extends WrapperObject{
	/**
	 * @model
	 */
	public void attachSource();

	/**
	 * @model
	 */
	public void dettachSource();

	/**
	 * @model
	 */
	public void attachTarget();

	/**
	 * @model
	 */
	public void dettachTarget();

	public static final String ROUTING_CONSTRAINT = "ROUTING_CONSTRAINT";

	/**
	 * @model containment="true" keyType="java.lang.Integer"
	 *        valueType="jp.go.aist.rtm.rtclink.model.core.Point"
	 */
	public EMap getRoutingConstraint();

	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' reference isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(ConnectorSource)
	 * @see jp.go.aist.rtm.rtclink.model.component.ComponentPackage#getConnector_Source()
	 * @model opposite="sourceConnectors"
	 * @generated
	 */
	ConnectorSource getSource();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.rtclink.model.component.Connector#getSource <em>Source</em>}' container reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' container reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(ConnectorSource value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(ConnectorTarget)
	 * @see jp.go.aist.rtm.rtclink.model.component.ComponentPackage#getConnection_Target()
	 * @model opposite="targetConnectors"
	 * @generated
	 */
	ConnectorTarget getTarget();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.rtclink.model.component.Connector#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(ConnectorTarget value);

	/**
	 * @model
	 * @return
	 */
	public boolean createConnectorR();

	/**
	 * @model
	 * @return
	 */
	public boolean deleteConnectorR();

}
