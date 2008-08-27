package jp.go.aist.rtm.rtcbuilder.generator.param;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeFactory;

import com.sun.org.apache.xerces.internal.jaxp.datatype.DatatypeFactoryImpl;

import jp.go.aist.rtm.rtcbuilder.IRtcBuilderConstants;
import jp.go.aist.rtm.rtcbuilder.IRtcBuilderConstantsJava;
import jp.go.aist.rtm.rtcbuilder.IRtcBuilderConstantsPython;
import jp.go.aist.rtm.rtcbuilder.generator.ProfileHandler;
import jp.go.aist.rtm.rtcbuilder.generator.param.idl.IdlFileParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.idl.ServiceClassParam;

/**
 * RTCを表すクラス
 */
public class RtcParam implements Serializable {

	private GeneratorParam parent;

	private String schemaVersion;
	//基本
	private String abstractDesc;
	private String name;
	private String category;
	private String creationDate;
	private String description;
	private String version;
	private String vender;
	private String componentType;
	private String activityType;
	private String componentKind;
	private int maxInstance;
	private String updateDate;
	private List<String> versionUpLog;
	private String executionType;
	private double executionRate;
	//データポート
	private List<DataPortParam> inports = new ArrayList<DataPortParam>();
	private List<DataPortParam> outports = new ArrayList<DataPortParam>();
	//サービスポート
	private List<ServicePortParam> serviceports = new ArrayList<ServicePortParam>();
//	private List<String> idlSearchPathes = new ArrayList<String>();
	private String includeIDLPath = null;
	//
	List<ServiceClassParam> serviceClassParams = new ArrayList<ServiceClassParam>();
	//コンフィギュレーション
	private List<ConfigSetParam> configParams = new ArrayList<ConfigSetParam>();
	private List<ConfigParameterParam> configParameterParams = new ArrayList<ConfigParameterParam>();
	//言語・環境
	private List<String> langList = new ArrayList<String>();
	private List<String> cxxLibraryPath = new ArrayList<String>();
	private String architecture = new String();
	private List<String> javaClassPath = new ArrayList<String>();
	//RTC.xml
	private String rtcxml;
	//ドキュメント
	private String doc_description;
	private String doc_in_out;
	private String doc_algorithm;
	//
	private List<ActionsParam> actions;
	//
	private String doc_creator;
	private String doc_license;
	private String doc_reference;
	//
	private String outputProject = null;

	private Map extentionData = new HashMap();

	private List<IdlFileParam> providerIdlPathes = new ArrayList<IdlFileParam>();
	private List<IdlFileParam> consumerIdlPathes = new ArrayList<IdlFileParam>();

	private List<String> originalProviderIdls = new ArrayList<String>();
	private List<String> originalConsumerIdls = new ArrayList<String>();

	private List<String> privateAttributes = new ArrayList<String>();
	private List<String> protectedAttributes = new ArrayList<String>();
	private List<String> publicAttributes = new ArrayList<String>();
	private List<String> privateOperations = new ArrayList<String>();
	private List<String> protectedOperations = new ArrayList<String>();
	private List<String> publicOperations = new ArrayList<String>();
	
	private static Map<String, String> argMap = new HashMap<String, String>();

	public RtcParam(GeneratorParam parent) {
		this(parent, false);
	}
	public RtcParam(GeneratorParam parent, boolean isTest) {
		this.parent = parent;
		//
		if( !isTest ) {
			DatatypeFactory dateFactory = new DatatypeFactoryImpl();
			String dateTime = dateFactory.newXMLGregorianCalendar(new GregorianCalendar()).toString();
			ProfileHandler handler = new ProfileHandler();
			rtcxml = handler.createInitialRtcXml(dateTime);
			this.creationDate = dateTime;
			this.updateDate = dateTime;
		}
		argMap.put(IRtcBuilderConstants.LANG_CPP, IRtcBuilderConstants.LANG_CPP_ARG);
		argMap.put(IRtcBuilderConstants.LANG_CPPWIN, IRtcBuilderConstants.LANG_CPPWIN_ARG);
		argMap.put(IRtcBuilderConstantsJava.LANG_JAVA, IRtcBuilderConstantsJava.LANG_JAVA_ARG);
		argMap.put(IRtcBuilderConstantsPython.LANG_PYTHON, IRtcBuilderConstantsPython.LANG_PYTHON_ARG);
		//
		actions = new ArrayList<ActionsParam>();
		for( int intidx=IRtcBuilderConstants.ACTIVITY_INITIALIZE; intidx<IRtcBuilderConstants.ACTIVITY_RATE_CHANGED+1; intidx++) {
			actions.add(new ActionsParam());
		}
	}

	public List<String> getPrivateOperations() {
		return privateOperations;
	}
	public void setPrivateOperations(List<String> private_Operations) {
		this.privateOperations = private_Operations;
	}
	public List<String> getProtectedOperations() {
		return protectedOperations;
	}
	public void setProtectedOperations(List<String> protected_Operations) {
		this.protectedOperations = protected_Operations;
	}
	public List<String> getPublicOperations() {
		return publicOperations;
	}
	public void setPublicOperations(List<String> public_Operations) {
		this.publicOperations = public_Operations;
	}

	public List<String> getPrivateAttributes() {
		return privateAttributes;
	}
	public void setPrivateAttributes(List<String> private_Attribute) {
		this.privateAttributes = private_Attribute;
	}
	public List<String> getProtectedAttributes() {
		return protectedAttributes;
	}
	public void setProtectedAttributes(List<String> protected_Attribute) {
		this.protectedAttributes = protected_Attribute;
	}
	public List<String> getPublicAttributes() {
		return publicAttributes;
	}
	public void setPublicAttributes(List<String> public_Attribute) {
		this.publicAttributes = public_Attribute;
	}

	public String getSchemaVersion() {
		return schemaVersion;
	}
	//
	public void setSchemaVersion(String version) {
		this.schemaVersion = version;
	}
	//基本
	public String getAbstract() {
		return abstractDesc;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public List<String> getVersionUpLog() {
		return versionUpLog;
	}
	public String getName() {
		return name;
	}
	public String getCategory() {
		return category;
	}
	public String getDescription() {
		return description;
	}
	public String getVersion() {
		return version;
	}
	public String getVender() {
		return vender;
	}
	public String getComponentType() {
		return componentType;
	}
	public String getActivityType() {
		return activityType;
	}
	public String getComponentKind() {
		return componentKind;
	}
	public int getMaxInstance() {
		return maxInstance;
	}
	public String getExecutionType() {
		return executionType;
	}
	public double getExecutionRate() {
		return executionRate;
	}
	public boolean IsExecutionRateSet() {
		return executionRate>0.0;
	}
	//
	public void setAbstract(String abst) {
		this.abstractDesc = abst;
	}
	public void setCreationDate(String date) {
		this.creationDate = date;
	}
	public void setUpdateDate(String date) {
		this.updateDate = date;
	}
	public void setVersionUpLog(List<String> log) {
		this.versionUpLog = log;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setDescription(String desc) {
		this.description = desc;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public void setVender(String vender) {
		this.vender = vender;
	}
	public void setComponentType(String comp_type) {
		this.componentType = comp_type;
	}
	public void setActivityType(String act_type) {
		this.activityType = act_type;
	}
	public void setComponentKind(String comp_kind) {
		this.componentKind = comp_kind;
	}
	public void setMaxInstance(int maxInst) {
		this.maxInstance = maxInst;
	}
	public void setExecutionType(String exec_type) {
		this.executionType = exec_type;
	}
	public void setExecutionRate(double exec_rate) {
		this.executionRate = exec_rate;
	}
	//データポート
	public List<DataPortParam> getInports() {
		return inports;
	}
	public List<DataPortParam> getOutports() {
		return outports;
	}
	//
	public void setInports(List<DataPortParam> inports) {
		this.inports = inports;
	}
	public void setOutports(List<DataPortParam> outport) {
		this.outports = outport;
	}
	//サービスポート
	public List<ServicePortParam> getServicePorts() {
		return serviceports;
	}
	public void setServicePorts(List<ServicePortParam> serviceport) {
		this.serviceports = serviceport;
	}
	//
	public String getIncludeIDLPath() {
		return includeIDLPath;
	}
	public void setIncludeIDLPath(String includeIdlPath) {
		this.includeIDLPath = includeIdlPath;
	}

	public List<ServiceClassParam> getServiceClassParams() {
		return serviceClassParams;
	}

	//コンフィギュレーション
	public List<ConfigSetParam> getConfigParams() {
		return configParams;
	}
	public List<ConfigParameterParam> getConfigParameterParams() {
		return configParameterParams;
	}
	//
	public void setConfigParams(List<ConfigSetParam> configParam) {
		this.configParams = configParam;
	}
	public void setConfigParameterParams(List<ConfigParameterParam> configParameterParam) {
		this.configParameterParams = configParameterParam;
	}
	//RTC.xml
	public String getRtcXml() {
		return rtcxml;
	}
	public void setRtcXml(String rtcXml) {
		this.rtcxml = rtcXml;
	}
	//言語・環境
	public String getLanguage() {
		return getLangageListString(langList);
	}
	public String getLanguageArg() {
		return getLangageListArgString(langList);
	}
	public List<String> getLangList() {
		return langList;
	}
	public List<String> getCxxLibraryPathes() {
		return cxxLibraryPath;
	}
	public String getArchitecture() {
		return this.architecture;
	}
	public List<String> getJavaClassPathes() {
		return javaClassPath;
	}
	//
	public void setLanguage(String lang) {
		if (lang != null) {
			this.langList = Arrays.asList(lang.split(","));
		}
	}
	public void setCxxLibraryPathes(List<String> libraryPathes) {
		this.cxxLibraryPath = libraryPathes;
	}
	public void setArchitecture(String arch) {
		this.architecture = arch;
	}
	public void setJavaClassPathes(List<String> classPathes) {
		this.javaClassPath = classPathes;
	}
	//
	public boolean isLanguageExist(String language) {
		boolean result = false;
		for (String str : getLangList()) {
			if (language.equalsIgnoreCase(str)) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	//ドキュメント-Component
	public boolean isDocExist() {
		if( (doc_description==null || doc_description.equals("")) &&
			(doc_in_out==null || doc_in_out.equals("")) && 
			(doc_algorithm==null || doc_algorithm.equals("")) && 
			(doc_creator==null || doc_creator.equals("")) && 
			(doc_license==null || doc_license.equals("")) && 
			(doc_reference==null || doc_reference.equals("")) )
				return false;
		return true;
	}
	public String getDocDescription() {
		if(doc_description==null) doc_description = ""; 
		return doc_description;
	}
	public String getDocInOut() {
		if(doc_in_out==null) doc_in_out = ""; 
		return doc_in_out;
	}
	public String getDocAlgorithm() {
		if(doc_algorithm==null) doc_algorithm = ""; 
		return doc_algorithm;
	}
	//
	public void setDocDescription(String description) {
		this.doc_description = description;
	}
	public void setDocInOut(String inout) {
		this.doc_in_out = inout;
	}
	public void setDocAlgorithm(String algorithm) {
		this.doc_algorithm = algorithm;
	}
	//Actions
	public boolean isActionsExist(int actionsId) {
		if( actions == null )
			return false;
		if( actions.get(actionsId) == null )
			return false;
		if( actions.get(actionsId).getOverView() == null || 
				actions.get(actionsId).getPreCondition() == null ||
				actions.get(actionsId).getPostCondition() == null )
					return false;
		if( actions.get(actionsId).getOverView().equals("") && 
				actions.get(actionsId).getPreCondition().equals("") &&
				actions.get(actionsId).getPostCondition().equals("") )
					return false;
		return true;
	}
	public boolean IsNotImplemented(int actionId) {
		return !actions.get(actionId).getImplemented();
	}
	public String getActionImplemented(int actionId) {
		if( actions.get(actionId).getImplemented() )
			return "true";
		return "false";
//		return actions.get(actionId).getImplemented();
	}
	public String getDocActionOverView(int actionId) {
		return actions.get(actionId).getOverView();
	}
	public String getDocActionPreCondition(int actionId) {
		return actions.get(actionId).getPreCondition();
	}
	public String getDocActionPostCondition(int actionId) {
		return actions.get(actionId).getPostCondition();
	}
	//
	public void setActionImplemented(int actionId, boolean implemented) {
		actions.get(actionId).setImplemaented(implemented);
	}
	public void setActionImplemented(int actionId, String implemented) {
		actions.get(actionId).setImplemaented(implemented);
	}
	public void setDocActionOverView(int actionId, String overview) {
		actions.get(actionId).setOverview(overview);
	}
	public void setDocActionPreCondition(int actionId, String precond) {
		actions.get(actionId).setPreCondition(precond);
	}
	public void setDocActionPostCondition(int actionId, String postcond) {
		actions.get(actionId).setPostCondition(postcond);
	}
	public void setAction(int actionId, boolean implemented, String overview, String precond, String postcond) {
		actions.get(actionId).setImplemaented(implemented);
		actions.get(actionId).setOverview(overview);
		actions.get(actionId).setPreCondition(precond);
		actions.get(actionId).setPostCondition(postcond);
	}
	//ドキュメント-その他
	public String getDocCreator() {
		if(doc_creator==null) doc_creator = ""; 
		return doc_creator;
	}
	public String getDocLicense() {
		if(doc_license==null) doc_license = ""; 
		return doc_license;
	}
	public String getDocReference() {
		if(doc_reference==null) doc_reference = ""; 
		return doc_reference;
	}
	//
	public void setDocCreator(String creator) {
		this.doc_creator = creator;
	}
	public void setDocLicense(String license) {
		this.doc_license = license;
	}
	public void setDocReference(String reference) {
		this.doc_reference = reference;
	}

	public static String getLangageListArgString(List langList) {
		StringBuffer result = new StringBuffer();
		for (Iterator iter = langList.iterator(); iter.hasNext();) {
			String element = (String) iter.next();
			if ("".equals(result.toString()) == false) {
				result.append(",");
			}
			result.append(argMap.get(element));
		}
		return result.toString();
	}

	public static String getLangageListString(List langList) {
		StringBuffer result = new StringBuffer();
		for (Iterator iter = langList.iterator(); iter.hasNext();) {
			String element = (String) iter.next();
			if ("".equals(result.toString()) == false) {
				result.append(",");
			}
			result.append(element);
		}
		return result.toString();
	}
	//
	public String getOutputProject() {
		return outputProject;
	}

	public void setOutputProject(String outputDirectory) {
		this.outputProject = outputDirectory;
	}

	public Map getExtentionData() {
		return extentionData;
	}

	public GeneratorParam getParent() {
		return parent;
	}

	public List<IdlFileParam> getProviderIdlPathes() {
		return providerIdlPathes;
	}

	public void setProviderIdlPathes(List<IdlFileParam> idlFiles) {
		this.providerIdlPathes = idlFiles;
	}

	public List<IdlFileParam> getConsumerIdlPathes() {
		return consumerIdlPathes;
	}

	public void setConsumerIdlPathes(List<IdlFileParam> idlFiles) {
		this.consumerIdlPathes = idlFiles;
	}

	public List<String> getOriginalProviderIdls() {
		return originalProviderIdls;
	}
	public void setOriginalProviderIdls(List<String> idlFiles) {
		this.originalProviderIdls = idlFiles;
	}

	public List<String> getOriginalConsumerIdls() {
		return originalConsumerIdls;
	}

	public void setOriginalConsumerIdls(List<String> idlFiles) {
		this.originalConsumerIdls = idlFiles;
	}
	//
	public void checkAndSetParameter() {

		List<String> providerIdlStrings = new ArrayList<String>();
		List<String> consumerIdlStrings = new ArrayList<String>();
		List<IdlFileParam> providerIdlParams = new ArrayList<IdlFileParam>();
		List<IdlFileParam> consumerIdlParams = new ArrayList<IdlFileParam>();
		List<String> originalConsumerIdlPathList = new ArrayList<String>();

		//IDLパス，IDLサーチパスの確認
		for( ServicePortParam servicePort : this.getServicePorts() ) {
			for( ServicePortInterfaceParam serviceInterface : servicePort.getServicePortInterfaces() ) {
				if( serviceInterface.getDirection().equals(ServicePortInterfaceParam.INTERFACE_DIRECTION_PROVIDED)) {
					if( !providerIdlStrings.contains(serviceInterface.getIdlFullPath()) ) {
						providerIdlStrings.add(serviceInterface.getIdlFullPath());
						providerIdlParams.add(new IdlFileParam(serviceInterface.getIdlFullPath(),this));
					}
				}
			}
		}
		for( ServicePortParam servicePort : this.getServicePorts() ) {
			for( ServicePortInterfaceParam serviceInterface : servicePort.getServicePortInterfaces() ) {
				if( serviceInterface.getDirection().equals(ServicePortInterfaceParam.INTERFACE_DIRECTION_REQUIRED)) {
					originalConsumerIdlPathList.add(serviceInterface.getIdlFullPath());
					if( !providerIdlStrings.contains(serviceInterface.getIdlFullPath()) && 
							!consumerIdlStrings.contains(serviceInterface.getIdlFullPath()) ) {
						consumerIdlStrings.add(serviceInterface.getIdlFullPath());
						consumerIdlParams.add(new IdlFileParam(serviceInterface.getIdlFullPath(),this));
					}
				}
			}
		}
		for( ServicePortParam servicePort : this.getServicePorts() ) {
			for( ServicePortInterfaceParam serviceInterface : servicePort.getServicePortInterfaces() ) {
				if( serviceInterface.getIdlSearchPath()!=null && !serviceInterface.getIdlSearchPath().equals("") ){
					for( IdlFileParam idlParam : providerIdlParams ) {
						if( serviceInterface.getIdlFullPath().trim().equals(idlParam.getIdlPath().trim()) ) {
							idlParam.getIdlSearchPathes().add(serviceInterface.getIdlSearchPath());
							break;
						}
					}
					for( IdlFileParam idlParam : consumerIdlParams ) {
						if( serviceInterface.getIdlFullPath().trim().equals(idlParam.getIdlPath().trim()) ) {
							idlParam.getIdlSearchPathes().add(serviceInterface.getIdlSearchPath());
							break;
						}
					}
				}
			}
		}
		//
		this.setProviderIdlPathes(providerIdlParams);
		this.setConsumerIdlPathes(consumerIdlParams);
		this.setOriginalProviderIdls(providerIdlStrings);
		this.setOriginalConsumerIdls(originalConsumerIdlPathList);
		//
		
		//クラスパスの重複削除
		ArrayList<String> javaClassPathes = new ArrayList<String>();
		for( String classPath : this.getJavaClassPathes() ) {
			if( !javaClassPathes.contains(classPath)) {
				javaClassPathes.add(classPath);
			}
		}
		this.setJavaClassPathes(javaClassPathes);
	}
}
