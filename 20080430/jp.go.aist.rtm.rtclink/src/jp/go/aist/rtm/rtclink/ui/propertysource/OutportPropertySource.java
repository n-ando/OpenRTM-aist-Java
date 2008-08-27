package jp.go.aist.rtm.rtclink.ui.propertysource;

import jp.go.aist.rtm.rtclink.model.component.OutPort;

/**
 * OutportのPropertySourceクラス
 */
public class OutportPropertySource extends PortPropertySource {
	/**
	 * コンストラクタ
	 * 
	 * @param inport
	 *            モデル
	 */
	public OutportPropertySource(OutPort outport) {
		super(outport);
	}
}
