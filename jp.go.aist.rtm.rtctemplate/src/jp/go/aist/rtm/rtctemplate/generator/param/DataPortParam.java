package jp.go.aist.rtm.rtctemplate.generator.param;

import java.io.Serializable;

/**
 * ポートを表すクラス
 */
public class DataPortParam implements Serializable {
	private String name;

	private String type;

	public DataPortParam(String name, String type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
