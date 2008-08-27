package jp.go.aist.rtm.rtcbuilder.generator.param;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * サービスポートを表すクラス
 */
public class ServicePortParam extends PortBaseParam implements Serializable {
	
	private String name;
	private List<ServicePortInterfaceParam> serviceinterfaces = new ArrayList<ServicePortInterfaceParam>();
	//
	private String doc_description;
	private String doc_if_description;

	public ServicePortParam() {
		this.name = "";
		this.selection = 0;
		setPositionByIndex(selection);
		//
		this.doc_description = "";
		this.doc_if_description = "";
	}
	
	public ServicePortParam(String name, String direction, 
								String doc_description, String doc_if_description) {
		this.name = name;
		this.setPosition(direction);
		//
		this.doc_description = doc_description;
		this.doc_if_description = doc_if_description;
	}
	
	public ServicePortParam(String name, int selection) {
		this.name = name;
		this.selection = selection;
		setPositionByIndex(selection);
		//
		this.doc_description = "";
		this.doc_if_description = "";
	}

	public String getName() {
		return name;
	}
	public List<ServicePortInterfaceParam> getServicePortInterfaces() {
		return serviceinterfaces;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setServicePortInterfaces(List<ServicePortInterfaceParam> serviceInterfaces) {
		this.serviceinterfaces = serviceInterfaces;
	}

	//
	public String getDocDescription() {
		return doc_description;
	}
	public String getDocIfDescription() {
		return doc_if_description;
	}
	
	public void setDocDescription(String description) {
		this.doc_description = description;
	}
	public void setDocIfDescription(String ifdescription) {
		this.doc_if_description = ifdescription;
	}
}
