package jp.go.aist.rtm.systemeditor.ui.views.configurationview.configurationwrapper;

import java.util.ArrayList;
import java.util.List;

public class ComponentConfigurationWrapper {

	private List<ConfigurationSetConfigurationWrapper> configurationSetList = new ArrayList<ConfigurationSetConfigurationWrapper>();

	private ConfigurationSetConfigurationWrapper activeConfigurationSet;

	public List<ConfigurationSetConfigurationWrapper> getConfigurationSetList() {
		return configurationSetList;
	}

	public void setActiveConfigSet(
			ConfigurationSetConfigurationWrapper configurationSet) {
		this.activeConfigurationSet = configurationSet;
	}

	public ConfigurationSetConfigurationWrapper getActiveConfigSet() {
		return activeConfigurationSet;
	}

	public void addConfigurationSet(
			ConfigurationSetConfigurationWrapper configurationSet) {
		configurationSetList.add(configurationSet);
	}

	public void removeConfigurationSet(
			ConfigurationSetConfigurationWrapper configurationSet) {
		configurationSetList.remove(configurationSet);
	}

}
