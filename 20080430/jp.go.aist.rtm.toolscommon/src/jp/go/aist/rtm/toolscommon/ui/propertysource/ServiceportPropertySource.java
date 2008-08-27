package jp.go.aist.rtm.toolscommon.ui.propertysource;

import jp.go.aist.rtm.toolscommon.model.component.ServicePort;

/**
 * ServiceportのPropertySourceクラス
 */
public class ServiceportPropertySource extends PortPropertySource {
	/**
	 * コンストラクタ
	 * 
	 * @param serviceport
	 *            モデル
	 */
	public ServiceportPropertySource(ServicePort serviceport) {
		super(serviceport);
	}
}
