package jp.go.aist.rtm.toolscommon.ui.propertysource;

import jp.go.aist.rtm.toolscommon.model.component.OutPort;

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
