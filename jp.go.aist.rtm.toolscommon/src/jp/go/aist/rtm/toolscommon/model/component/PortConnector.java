package jp.go.aist.rtm.toolscommon.model.component;

/**
 * ポート間接続を表現するクラス
 * 
 * @model
 */
public interface PortConnector extends AbstractPortConnector {
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

	/**
	 * @model containment="true"
	 */
	public ConnectorProfile getConnectorProfile();

	public jp.go.aist.rtm.toolscommon.model.component.Port getSource();

	public jp.go.aist.rtm.toolscommon.model.component.Port getTarget();
}
