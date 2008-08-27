package jp.go.aist.rtm.rtcbuilder.generator.param;

import java.io.Serializable;

/**
 * コンフィギュレーションプロファイルを表すクラス
 */
public class ConfigParameterParam implements Serializable {
	
	private String[] Config_Items;
	private String[] Default_Items;

    private String configName;
	private String defaultValue;
	protected int selection = 0;

	public ConfigParameterParam() {
		this.configName = "";
		this.defaultValue = "";
	}

	public ConfigParameterParam(String[] config_items) {
		this();
		this.Config_Items = config_items;
	}

	public ConfigParameterParam(String configName, String defaultVal) {
		this.configName = configName;
		this.defaultValue = defaultVal;
	}

	public String getConfigName() {
		return configName;
	}
	public String getDefaultVal() {
		return defaultValue;
	}
	public int getIndex() {
		return selection;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}
	public void setDefaultVal(String defaultVal) {
		this.defaultValue = defaultVal;
	}
	public void setIndex(int index) {
		this.selection = index;
		this.configName = this.Config_Items[this.selection];
	}
	public void setConfigItems(String[] config_items) {
		this.Config_Items = config_items;
	}
	public void setDefaultItems(String[] default_items) {
		this.Default_Items = default_items;
	}
}
