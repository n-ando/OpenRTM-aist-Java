package jp.go.aist.rtm.systemeditor.ui.editor.command;

import jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile;
import jp.go.aist.rtm.toolscommon.model.component.ConnectorSource;
import jp.go.aist.rtm.toolscommon.model.component.ConnectorTarget;
import jp.go.aist.rtm.toolscommon.model.component.Port;

/**
 * コネクタープロファイルを作成する役割を持ったインタフェース
 */
public interface ConnectorCreateManager {

	/**
	 * コネクタプロファイルを取得する
	 * 
	 * @param port
	 *            ポート1
	 * @param port
	 *            ポート2
	 * 
	 * @return コネクタプロファイル
	 */
	public ConnectorProfile getConnectorProfile();

	/**
	 * 1番目のポートを取得する
	 * 
	 * @return 1番目のポート
	 */
	public ConnectorSource getFirst();

	/**
	 * 1番目のポートを設定する
	 * 
	 * @param first
	 *            1番目のポート
	 */
	public void setFirst(Port first);

	/**
	 * 2番目のポートを取得する
	 * 
	 * @return 2番目のポート
	 */
	public ConnectorTarget getSecond();

	/**
	 * 2番目のポートを設定する
	 * 
	 * @param second
	 *            2番目のポート
	 */
	public void setSecond(Port second);

	/**
	 * 接続可能であるか確認する
	 * 
	 * @return 接続可能であるか
	 */
	public boolean validate();

	/**
	 * プロファイルを作成し、コネクタ作成する
	 */
	public void createProfileAndConnector();

	/**
	 * プロファイルによって、コネクタを作成する
	 * 
	 * @param connectorProfile
	 * @return
	 */
	public boolean connectR(ConnectorProfile connectorProfile);

}
