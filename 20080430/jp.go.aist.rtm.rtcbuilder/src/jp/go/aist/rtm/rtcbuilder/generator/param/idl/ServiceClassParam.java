package jp.go.aist.rtm.rtcbuilder.generator.param.idl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.go.aist.rtm.rtcbuilder.generator.param.RtcParam;

/**
 * サービスクラスをあらわすクラス
 */
public class ServiceClassParam implements Serializable {
	private String name;
	private String idlPath;
	private List<ServiceMethodParam> methods = new ArrayList<ServiceMethodParam>();
	private RtcParam parent;
	private Map<String,String> typeDef = new HashMap<String,String>();

	public ServiceClassParam() {
	}

	public ServiceClassParam(String name, String searchPath) {
		this.name = name;
		this.idlPath = searchPath;
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

	public RtcParam getParent() {
		return parent;
	}

	public void setParent(RtcParam parent) {
		this.parent = parent;
	}

	public String getIdlPath() {
		return this.idlPath;
	}

	public void setIdlPath(String idlPath) {
		this.idlPath = idlPath;
	}

	public Map<String,String> getTypeDef() {
		return this.typeDef;
	}

	public void setTypeDef(Map<String,String> typeDef) {
		this.typeDef = typeDef;
	}

	public List<TypeDefParam> getTypeDefList() {
		List<TypeDefParam> typeDefList = new ArrayList<TypeDefParam>();
		Set<String> keys = typeDef.keySet();
		Iterator<String> iterator = keys.iterator();
		while(iterator.hasNext()) {
			TypeDefParam typedef = new TypeDefParam();
			String key = iterator.next();
			String value = this.typeDef.get(key);
			typedef.setTargetDef(key);
			typedef.setOriginalDef(value);
			typeDefList.add(typedef);
		}
		return typeDefList;
	}
}
