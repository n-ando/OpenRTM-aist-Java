package jp.go.aist.rtm.rtcbuilder.generator.param;

import java.io.Serializable;

/**
 * サービスポートを表すクラス
 */
public class ServicePortInterfaceParam implements Serializable {
	
	public static final String[] COMBO_ITEM = 
		new String[] {"Provided", "Required"};
	public static final String INTERFACE_DIRECTION_PROVIDED = "Provided";
	public static final String INTERFACE_DIRECTION_REQUIRED = "Required";
	private int selection = 0;

	private ServicePortParam parent;
	private String name;
	private String direction;
	private String instancename;
	private String varname;
	private String idlfile;
	private String interfacetype;
	private String idlSearchPath;
	//
	private String doc_description;
	private String doc_argument;
	private String doc_return;
	private String doc_exception;
	private String doc_pre_condition;
	private String doc_post_condition;

	public ServicePortInterfaceParam(ServicePortParam parent) {
		this.parent = parent;
		this.name = "";
		this.instancename = "";
		this.varname = "";
		this.idlfile = "";
		this.interfacetype = "";
		this.idlSearchPath = "";
		this.selection = 0;
		this.direction = "";
		//
		this.doc_description = "";
		this.doc_argument = "";
		this.doc_return = "";
		this.doc_exception = "";
		this.doc_pre_condition = "";
		this.doc_post_condition = "";
	}

	public ServicePortInterfaceParam(ServicePortParam parent, String name, String instancename, String varname, 
					String idlfile, String interfacetype, String idlpath, int selection) {
		this.parent = parent;
		this.name = name;
		this.instancename = instancename;
		this.varname = varname;
		this.idlfile = idlfile;
		this.interfacetype = interfacetype;
		this.idlSearchPath = idlpath;
		this.selection = selection;
		this.direction = ServicePortInterfaceParam.COMBO_ITEM[selection];
		//
		this.doc_description = "";
		this.doc_argument = "";
		this.doc_return = "";
		this.doc_exception = "";
		this.doc_pre_condition = "";
		this.doc_post_condition = "";
	}

	public ServicePortInterfaceParam(ServicePortParam parent, String name, String selection, 
			String instancename, String varname, 
			String idlfile, String interfacetype, String idlpath, 
			String doc_description, String doc_argument, String doc_return, String doc_exception,
			String doc_pre_condition, String doc_post_condition) {
		this.parent = parent;
		this.name = name;
		this.instancename = instancename;
		this.varname = varname;
		this.idlfile = idlfile;
		this.interfacetype = interfacetype;
		this.idlSearchPath = idlpath;
		this.setDirection(selection);
		//
		this.doc_description = doc_description;
		this.doc_argument = doc_argument;
		this.doc_return = doc_return;
		this.doc_exception = doc_exception;
		this.doc_pre_condition = doc_pre_condition;
		this.doc_post_condition = doc_post_condition;
	}

	public ServicePortParam getParent() {
		return parent;
	}
	public String getName() {
		return name;
	}
	public String getDirection() {
		return direction;
	}
	public String getInstanceName() {
		return instancename;
	}
	public String getVarName() {
		return varname;
	}
	public String getIdlFile() {
		return idlfile;
	}
	public String getInterfaceType() {
		return interfacetype;
	}
	public String getIdlSearchPath() {
		return idlSearchPath;
	}
	public int getIndex() {
		return selection;
	}
	public String getIdlFullPath() {
		return idlfile;
	}
	//
	public String getTmplVarName() {
		if( this.varname!=null && !this.varname.equals(""))
			return this.varname;
		if( this.instancename!=null && !this.instancename.equals(""))
			return this.instancename;
		return this.name;
	}
	//
	
	public void setName(String name) {
		this.name = name;
	}
	public void setInstanceName(String instancename) {
		this.instancename = instancename;
	}
	public void setVarName(String varname) {
		this.varname = varname;
	}
	public void setIdlFile(String idlfile) {
		this.idlfile = idlfile;
	}
	public void setInterfaceType(String interfacetype) {
		this.interfacetype = interfacetype;
	}
	public void setIdlSearchPath(String idlpath) {
		this.idlSearchPath = idlpath;
	}
	public void setIndex(int index) {
		this.selection = index;
		this.direction = ServicePortInterfaceParam.COMBO_ITEM[index];
	}
	public void setDirection(String direction) {
		this.direction = direction;
		if(direction.equals(COMBO_ITEM[1])) {
			this.selection = 1;
		} else {
			this.selection = 0;
		}
	}
	//
	public String getDocDescription() {
		return doc_description;
	}
	public String getDocArgument() {
		return doc_argument;
	}
	public String getDocReturn() {
		return doc_return;
	}
	public String getDocException() {
		return doc_exception;
	}
	public String getDocPreCondition() {
		return doc_pre_condition;
	}
	public String getDocPostCondition() {
		return doc_post_condition;
	}
	
	public void setDocDescription(String description) {
		this.doc_description = description;
	}
	public void setDocArgument(String argument) {
		this.doc_argument = argument;
	}
	public void setDocReturn(String returnDesc) {
		this.doc_return = returnDesc;
	}
	public void setDocException(String exception) {
		this.doc_exception = exception;
	}
	public void setDocPreCondition(String precondition) {
		this.doc_pre_condition = precondition;
	}
	public void setDocPostCondition(String postcondition) {
		this.doc_post_condition = postcondition;
	}
}
