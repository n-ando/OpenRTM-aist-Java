package jp.go.aist.rtm.rtcbuilder.generator.param;

import java.io.Serializable;
import java.util.Iterator;

import jp.go.aist.rtm.rtcbuilder.generator.param.idl.ServiceClassParam;

/**
 * サービスへの参照を表すクラス
 */
public class ServiceReferenceParam implements Serializable {

	private String interfaceName;
	private String name;
	private String type;
	private RtcParam parent;

	public ServiceReferenceParam(String interfaceName, String serviceName,
			String type, RtcParam rtcParam) {
		this.interfaceName = interfaceName;
		this.name = serviceName;
		this.type = type;
		this.parent = rtcParam;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getName() {
		return name;
	}

	public void setName(String serviceName) {
		this.name = serviceName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public RtcParam getParent() {
		return parent;
	}

	public void setParent(RtcParam rtcParam) {
		this.parent = rtcParam;
	}

	public ServiceClassParam getServiceClassParam() {
		ServiceClassParam result = null;
		for (Iterator iter = getParent().getServiceClassParams()
				.iterator(); iter.hasNext();) {
			ServiceClassParam serviceClassParam = (ServiceClassParam) iter
					.next();
			if (serviceClassParam.getName().equals(type)) {
				result = serviceClassParam;
				break;
			}
		}

		if (result == null) {
			throw new RuntimeException("" + type + " : is undefined in idl");
		}

		return result;
	}
}
