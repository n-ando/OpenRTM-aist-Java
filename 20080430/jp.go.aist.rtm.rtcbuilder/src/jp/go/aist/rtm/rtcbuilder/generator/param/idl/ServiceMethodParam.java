package jp.go.aist.rtm.rtcbuilder.generator.param.idl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * サービスのメソッドを表すクラス
 */
public class ServiceMethodParam implements Serializable {
	private String type;

	private String name;

	private List<ServiceArgumentParam> arguments = new ArrayList<ServiceArgumentParam>();

	public boolean getIsVoid() {
		return type.equals("void");
	}
	public boolean getIsBoolean() {
		return type.equals("boolean");
	}

	public String getName() {
		return name;
	}

	public void setName(String methodName) {
		this.name = methodName;
	}

	public String getType() {
		return type;
	}

	public void setType(String methodType) {
		this.type = methodType;
	}

	public List<ServiceArgumentParam> getArguments() {
		return arguments;
	}
}
