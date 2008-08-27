package jp.go.aist.rtm.systemeditor.ui.views.configurationview.configurationwrapper;

import java.util.ArrayList;
import java.util.List;

import jp.go.aist.rtm.toolscommon.model.component.ConfigurationSet;

import org.apache.commons.lang.builder.CompareToBuilder;

public class ConfigurationSetConfigurationWrapper implements Comparable {

	private List<NamedValueConfigurationWrapper> namedValueList = new ArrayList<NamedValueConfigurationWrapper>();

	private String id;

	private boolean isNameModified = false;

	private ConfigurationSet configurationSet;

	public ConfigurationSet getConfigurationSet() {
		return configurationSet;
	}

	public ConfigurationSetConfigurationWrapper(
			ConfigurationSet configurationSet, String name) {
		this.id = name;
		this.configurationSet = configurationSet;
	}

	public List<NamedValueConfigurationWrapper> getNamedValueList() {
		return namedValueList;
	}

	public void addNamedValue(NamedValueConfigurationWrapper namedValue) {
		namedValueList.add(namedValue);
	}

	public void removeNamedValue(NamedValueConfigurationWrapper namedValue) {
		namedValueList.remove(namedValue);
	}

	public String getId() {
		return id;
	}

	public void setId(String name) {
		if (name.equals(this.id)) {
			return;
		}

		this.id = name;
		isNameModified = true;
	}

	public boolean isNameModified() {
		return isNameModified;
	}

	public boolean isActivateModified() {
		return false;
	}

	/**
	 * @see java.lang.Comparable#compareTo(Object)
	 */
	public int compareTo(Object object) {
		ConfigurationSetConfigurationWrapper myClass = (ConfigurationSetConfigurationWrapper) object;
		return new CompareToBuilder().append(this.id, myClass.id)
				.toComparison();
	}

}
