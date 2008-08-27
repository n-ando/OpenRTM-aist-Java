package jp.go.aist.rtm.repositoryView.model;

import jp.go.aist.rtm.toolscommon.model.component.ComponentSpecification;

public class RepositoryViewLeafItem extends RepositoryViewItem {

	public static final String RTC_LEAF = "RTC";
	public static final String RTSystem_LEAF = "RTSystem";
	public static final String RTSenario_LEAF = "RTSeanario";
	public static final String RTModel_LEAF = "RTModel";

	private ComponentSpecification component;
	private boolean isRepository = false;
	private String uriString;

	public RepositoryViewLeafItem(String name) {
		super(name, RepositoryViewItem.LEAF_ITEM);
	}
	//
	public ComponentSpecification getComponent(){
		return this.component;
	}
	public void setComponent(ComponentSpecification component) {
		this.component = component;
	}
	//
	public boolean isRepositoryitem(){
		return this.isRepository;
	}
	public void setRepositoryitem(boolean repository){
		this.isRepository = repository;
	}
	//
	public String getServerAddress() {
		RepositoryViewItem target = this;
		while(!(target instanceof ServerRVRootItem)) {
			target = (RepositoryViewItem)target.getParent();
		}
		return target.getName();
	}
}
