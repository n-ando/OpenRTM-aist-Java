package jp.go.aist.rtm.rtctemplate.generator.param;

import java.io.Serializable;

/**
 * ポートを表すクラス
 */
public class ConfigSetParam implements Serializable {
	private String name;
	private String type;
	private String defaultValue;

	public ConfigSetParam(String name, String type, String defaultVal) {
		this.name = name;
		this.type = type;
		this.defaultValue = defaultVal;
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

	public String getDefaultVal() {
		return defaultValue;
	}
	public void setDefaultVal(String defaultVal) {
		this.defaultValue = defaultVal;
	}
}
