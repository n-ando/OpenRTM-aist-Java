package jp.go.aist.rtm.rtctemplate.generator.param.idl;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.go.aist.rtm.rtctemplate.generator.param.GeneratorParam;

/**
 * サービスクラスをあらわすクラス
 */
public class ServiceClassParam implements Serializable {
	private String name;
	private String idlPath;
	private List<ServiceMethodParam> methods = new ArrayList<ServiceMethodParam>();
	private GeneratorParam parent;

	public ServiceClassParam() {
	}

	public List<ServiceMethodParam> getMethods() {
		return methods;
	}

	public String getName() {
		return name;
	}

	public void setName(String serviceName) {
		this.name = serviceName;
	}

	public GeneratorParam getParent() {
		return parent;
	}

	public void setParent(GeneratorParam parent) {
		this.parent = parent;
	}

	public String getIdlPath() {
		return this.idlPath;
	}

	public void setIdlPath(String idlPath) {
		this.idlPath = idlPath;
	}
}
