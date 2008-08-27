package jp.go.aist.rtm.rtctemplate.generator.param;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * RTC‚ð•\‚·ƒNƒ‰ƒX
 */
public class RtcParam implements Serializable {

	public static final String[] COMPONENT_TYPE_ITEMS = new String[] {
		"DataFlowComponent", "STATIC", "UNIQUE", "COMMUTATIVE" };

	public static final String[] ACTIVITY_TYPE_ITEMS = new String[] {
			"PERIODIC", "SPORADIC", "EVENT_DRIVEN" };

	public static final String TAG_BACKEND = "backend";
	public static final String TAG_SVC_IDL = "svc-idl";
	public static final String TAG_SVC_NAME = "svc-name";
	public static final String TAG_INPORT_TYPE = "type";
	public static final String TAG_OUTPORT_TYPE = "type";
	public static final String TAG_SERVICE_PORT_PORTNAME = "portname";
	public static final String TAG_SERVICE_PORT_TYPE = "type";

	public static final String LANG_CPP = "C++";
	public static final String LANG_PYTHON = "Python";
	public static final String LANG_JAVA = "Java";
	public static final String LANG_CPPWIN = "C++(Windows)";

	public static final String LANG_CPP_ARG = "cxx";
	public static final String LANG_JAVA_ARG = "java";
	public static final String LANG_CPPWIN_ARG = "cxxwin";
	public static final String LANG_PYTHON_ARG = "python";

	private static final String DEFAULT_ACTIVITY_TYPE = ACTIVITY_TYPE_ITEMS[0];
	private static final String DEFAULT_VENDER = "VenderName";
	private static final String DEFAULT_CATEGORY = "Category";
	private static final String DEFAULT_COMPONENT_TYPE = COMPONENT_TYPE_ITEMS[0];
	private static final String DEFAULT_DESCRIPTION = "ModuleDescription";
	private static final int DEFAULT_MAXINST = 1;
	private static final String DEFAULT_COMPONENT_NAME = "ModuleName";
	private static final String DEFAULT_VERSION = "1.0.0";
	//
	public static final String DEFAULT_RTM_VERSION = "0.4.1";
	public static final String RTM_VERSION_042 = "0.4.2";

	private GeneratorParam parent;

	private String activityType = DEFAULT_ACTIVITY_TYPE;
	private String vender = DEFAULT_VENDER;
	private String category = DEFAULT_CATEGORY;
	private String componentType = DEFAULT_COMPONENT_TYPE;
	private String description = DEFAULT_DESCRIPTION;
	private Map extentionData = new HashMap();
	private List<DataPortParam> inports = new ArrayList<DataPortParam>();
	private int maxInstance = DEFAULT_MAXINST;
	private String name = DEFAULT_COMPONENT_NAME;
	private List<DataPortParam> outports = new ArrayList<DataPortParam>();
	private List<ServiceReferenceParam> providerReferences = new ArrayList<ServiceReferenceParam>();
	private List<ServiceReferenceParam> consumerReferences = new ArrayList<ServiceReferenceParam>();
	private List<ConfigSetParam> configParams = new ArrayList<ConfigSetParam>();
	private String version = DEFAULT_VERSION;
	private List<String> langList = new ArrayList<String>();
	//
	private String rtm_version = DEFAULT_RTM_VERSION;
	private boolean test_version = false;

	private List<String> providerIdlPathes = new ArrayList<String>();
	private List<String> consumerIdlPathes = new ArrayList<String>();

	private List<String> originalProviderIdls = new ArrayList<String>();
	private List<String> originalConsumerIdls = new ArrayList<String>();

	private List<String> privateAttributes = new ArrayList<String>();
	private List<String> protectedAttributes = new ArrayList<String>();
	private List<String> publicAttributes = new ArrayList<String>();
	private List<String> privateOperations = new ArrayList<String>();
	private List<String> protectedOperations = new ArrayList<String>();
	private List<String> publicOperations = new ArrayList<String>();
	
	private static Map argMap = new HashMap();

	public RtcParam(GeneratorParam parent) {
		this.parent = parent;
		//
		argMap.put(LANG_CPP, LANG_CPP_ARG);
		argMap.put(LANG_CPPWIN, LANG_CPPWIN_ARG);
		argMap.put(LANG_JAVA, LANG_JAVA_ARG);
		argMap.put(LANG_PYTHON, LANG_PYTHON_ARG);
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

	public String getActivityType() {
		return activityType;
	}

	public String getVender() {
		return vender;
	}

	public String getCategory() {
		return category;
	}

	public String getComponentType() {
		return componentType;
	}

	public String getDescription() {
		return description;
	}

	public List<DataPortParam> getInports() {
		return inports;
	}

	public String getLanguage() {
		return getLangageListString(langList);
	}

	public int getMaxInstance() {
		return maxInstance;
	}

	public String getName() {
		return name;
	}

	public List<DataPortParam> getOutports() {
		return outports;
	}

	public String getLanguageArg() {
		if( argMap.size()==0) {
			argMap.put(LANG_CPP, LANG_CPP_ARG);
			argMap.put(LANG_CPPWIN, LANG_CPPWIN_ARG);
			argMap.put(LANG_JAVA, LANG_JAVA_ARG);
			argMap.put(LANG_PYTHON, LANG_PYTHON_ARG);
		}
		return getLangageListArgString(langList);
	}

	public List<ServiceReferenceParam> getProviderReferences() {
		return providerReferences;
	}

	public String getVersion() {
		return version;
	}

	public void setActivityType(String act_type) {
		this.activityType = act_type;
	}

	public void setVender(String vender) {
		this.vender = vender;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setComponentType(String comp_type) {
		this.componentType = comp_type;
	}

	public void setDescription(String desc) {
		this.description = desc;
	}

	public void setInports(List<DataPortParam> inports) {
		this.inports = inports;
	}

	public void setLanguage(String lang) {
		if (lang != null) {
			this.langList = Arrays.asList(lang.split(","));
		}
	}

	public void setMaxInstance(int maxInst) {
		this.maxInstance = maxInst;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOutports(List<DataPortParam> outport) {
		this.outports = outport;
	}

	public void setProviderReferences(
			List<ServiceReferenceParam> providerReferenceList) {
		this.providerReferences = providerReferenceList;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Map getExtentionData() {
		return extentionData;
	}

	public List<String> getLangList() {
		return langList;
	}

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

	public GeneratorParam getParent() {
		return parent;
	}

	public List<ServiceReferenceParam> getConsumerReferences() {
		return consumerReferences;
	}

	public void setConsumerReferences(
			List<ServiceReferenceParam> consumerReferenceList) {
		this.consumerReferences = consumerReferenceList;
	}

	public List<String> getProviderIdlPathes() {
		return providerIdlPathes;
	}

	public void setProviderIdlPathes(List<String> idlFiles) {
		this.providerIdlPathes = idlFiles;
	}

	public List<String> getConsumerIdlPathes() {
		return consumerIdlPathes;
	}

	public void setConsumerIdlPathes(List<String> idlFiles) {
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

	public List<ConfigSetParam> getConfigParams() {
		return configParams;
	}

	public void setConfigParams(List<ConfigSetParam> configParam) {
		this.configParams = configParam;
	}
	//
	public String getRtmVersion() {
		return this.rtm_version;
	}
	public void setRtmVersion(String version) {
		this.rtm_version = version;
	}

	public boolean getIsTest() {
		return this.test_version;
	}
	public void setIsTest(boolean isTest) {
		this.test_version = isTest;
	}
}
