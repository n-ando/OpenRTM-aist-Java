package jp.go.aist.rtm.rtclink.model.component;

/**
 * ポート間接続を表現するクラス
 * 
 * @model
 */
public interface PortConnector extends Connector{
	/**
	 * @model containment="true"
	 */
	public ConnectorProfile getConnectorProfile();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.rtclink.model.component.PortConnector#getConnectorProfile <em>Connector Profile</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Connector Profile</em>' containment reference.
	 * @see #getConnectorProfile()
	 * @generated
	 */
	void setConnectorProfile(ConnectorProfile value);

	public jp.go.aist.rtm.rtclink.model.component.Port getSource();

	public jp.go.aist.rtm.rtclink.model.component.Port getTarget();
}
