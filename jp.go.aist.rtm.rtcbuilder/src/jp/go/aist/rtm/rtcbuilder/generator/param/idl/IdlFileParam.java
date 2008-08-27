package jp.go.aist.rtm.rtcbuilder.generator.param.idl;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.go.aist.rtm.rtcbuilder.generator.param.RtcParam;

/**
 * IDLファイルをあらわすクラス
 */
public class IdlFileParam implements Serializable {

	private String idlPath;
	private List<ServiceClassParam> serviceClass = new ArrayList<ServiceClassParam>();
	private RtcParam parent;
	private List<String> idlSearchPathes = new ArrayList<String>();

	public IdlFileParam() {
	}

	public IdlFileParam(String idlPath, RtcParam parent) {
		this.idlPath = idlPath;
		this.parent = parent;
	}

	public RtcParam getParent() {
		return parent;
	}

	public void setParent(RtcParam parent) {
		this.parent = parent;
	}

	public List<ServiceClassParam> getServiceClassParams() {
		return serviceClass;
	}

	public void addServiceClassParams(	ServiceClassParam serviceClassParam) {
		this.serviceClass.add(serviceClassParam);
	}

	public void setServiceClassParams(	List<ServiceClassParam> serviceClassParamList) {
		this.serviceClass = serviceClassParamList;
	}

	public String getIdlFile() {
		File file = new File(idlPath);
		return file.getName();
	}

	public String getIdlFileNoExt() {
		File file = new File(idlPath);
		String fileName = file.getName();
		if(fileName == null) return "";
		
		int index = fileName.lastIndexOf('.');
		if(index>0 && index<fileName.length()-1) {
			return fileName.substring(0,index);
		}
		return "";
	}

	public String getIdlPath() {
		return idlPath;
	}

	public void setIdlPath(String idlPath) {
		this.idlPath = idlPath;
	}

	public List<String> getIdlSearchPathes() {
		return idlSearchPathes;
	}

	public void setIdlSearchPathes(List<String> idlFiles) {
		this.idlSearchPathes = idlFiles;
	}
}
