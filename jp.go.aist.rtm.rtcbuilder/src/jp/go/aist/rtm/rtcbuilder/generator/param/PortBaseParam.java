package jp.go.aist.rtm.rtcbuilder.generator.param;

import java.io.Serializable;

/**
 * ポート情報のベースクラス
 */
public class PortBaseParam implements Serializable {
	
	public static final String[] COMBO_ITEM = 
		new String[] {"left", "right" ,"top", "bottom"};
	protected String position;
	protected int selection = 0;

	public String getPosition() {
		return position;
	}
	public int getPositionByIndex() {
		return selection;
	}

	public void setPositionByIndex(int index) {
		this.selection = index;
		this.position = PortBaseParam.COMBO_ITEM[index];
	}
	public void setPosition(String position) {
		this.position = position;
		if(position.equals(COMBO_ITEM[1])) {
			this.selection = 1;
		} else if(position.equals(COMBO_ITEM[2])) {
			this.selection = 2;
		} else if(position.equals(COMBO_ITEM[3])) {
			this.selection = 3;
		} else {
			this.selection = 0;
		}
	}
}
