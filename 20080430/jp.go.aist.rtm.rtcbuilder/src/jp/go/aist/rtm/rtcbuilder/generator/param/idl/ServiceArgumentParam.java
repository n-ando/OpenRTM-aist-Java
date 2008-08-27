package jp.go.aist.rtm.rtcbuilder.generator.param.idl;

import java.io.Serializable;

/**
 * サービスのメソッド引数をあらわすクラス
 */
public class ServiceArgumentParam implements Serializable {
	private String type;
	private String name;
	private String direction;

	public String getName() {
		return name;
	}

	public void setName(String argName) {
		this.name = argName;
	}

	public String getType() {
		return type;
	}

	public void setType(String argType) {
		this.type = argType;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String argDirection) {
		this.direction = argDirection;
	}
}
