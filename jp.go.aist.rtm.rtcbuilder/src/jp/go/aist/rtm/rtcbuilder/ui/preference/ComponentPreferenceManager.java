package jp.go.aist.rtm.rtcbuilder.ui.preference;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import jp.go.aist.rtm.rtcbuilder.IRtcBuilderConstants;
import jp.go.aist.rtm.rtcbuilder.RtcBuilderPlugin;

public class ComponentPreferenceManager {
	private static ComponentPreferenceManager __instance = new ComponentPreferenceManager();
	protected PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);

	/**
	 * コンストラクタ
	 * 
	 * @return シングルトン
	 */
	public static ComponentPreferenceManager getInstance() {
		return __instance;
	}

	//コード生成
	/**
	 * Module Nameのキー
	 */
	private static final String Generate_Basic_Name = ComponentPreferenceManager.class.getName()
			+ "GENERATE_MODULE_NAME";
	/**
	 * Descriptionのキー
	 */
	private static final String Generate_Basic_Description = ComponentPreferenceManager.class.getName()
			+ "GENERATE_BASIC_DESCRIPTION";
	/**
	 * Versionのキー
	 */
	private static final String Generate_Basic_Version = ComponentPreferenceManager.class.getName()
			+ "GENERATE_BASIC_VERSION";
	/**
	 * Vendor Nameのキー
	 */
	private static final String Generate_Basic_Vendor_Name = ComponentPreferenceManager.class.getName()
			+ "GENERATE_BASIC_VENDOR_NAME";
	/**
	 * Categoryのキー
	 */
	private static final String Generate_Basic_Category = ComponentPreferenceManager.class.getName()
			+ "GENERATE_BASIC_CATEGORY";
	/**
	 * Component Typeのキー
	 */
	private static final String Generate_Basic_ComponentType = ComponentPreferenceManager.class.getName()
			+ "GENERATE_BASIC_COMPONENT_TYPE";
	/**
	 * Activity Typeのキー
	 */
	private static final String Generate_Basic_ActivityType = ComponentPreferenceManager.class.getName()
			+ "GENERATE_BASIC_ACTIVITY_TYPE";
	/**
	 * Component Kindのキー
	 */
	private static final String Generate_Basic_ComponentKind = ComponentPreferenceManager.class.getName()
			+ "GENERATE_BASIC_COMPONENT_KIND";
	/**
	 * Max Instanceのキー
	 */
	private static final String Generate_Basic_Max_Instance = ComponentPreferenceManager.class.getName()
			+ "GENERATE_BASIC_MAX_INSTANCES";
	/**
	 * Execution Typeのキー
	 */
	private static final String Generate_Basic_ExecutionType = ComponentPreferenceManager.class.getName()
			+ "GENERATE_BASIC_EXECUTION_TYPE";
	/**
	 * Execution Rateのキー
	 */
	private static final String Generate_Basic_Execution_Rate = ComponentPreferenceManager.class.getName()
			+ "GENERATE_BASIC_EXECUTION_RATE";
	////
	/**
	 * DataPort Nameのキー
	 */
	private static final String Generate_DataPort_Name = ComponentPreferenceManager.class.getName()
			+ "GENERATE_DATAPORT_NAME";
	/**
	 * DataPort Typeのキー
	 */
	private static final String Generate_DataPort_Type = ComponentPreferenceManager.class.getName()
			+ "GENERATE_DATAPORT_TYPE";
	/**
	 * DataPort VarNameのキー
	 */
	private static final String Generate_DataPort_VarName = ComponentPreferenceManager.class.getName()
			+ "GENERATE_DATAPORT_VARNAME";
	////
	/**
	 * ServicePort Nameのキー
	 */
	private static final String Generate_ServicePort_Name = ComponentPreferenceManager.class.getName()
			+ "GENERATE_SERVICEPORT_NAME";
	/**
	 * ServiceInterfacet Nameのキー
	 */
	private static final String Generate_ServiceIF_Name = ComponentPreferenceManager.class.getName()
			+ "GENERATE_SERVICEIF_NAME";
	/**
	 * ServiceInterfacet Instance Nameのキー
	 */
	private static final String Generate_ServiceIF_InstanceName = ComponentPreferenceManager.class.getName()
			+ "GENERATE_SERVICEIF_INSTANCENAME";
	/**
	 * ServiceInterfacet Varriable Nameのキー
	 */
	private static final String Generate_ServiceIF_VarName = ComponentPreferenceManager.class.getName()
			+ "GENERATE_SERVICEIF_VARNAME";
	////
	/**
	 * Configuration Nameのキー
	 */
	private static final String Generate_Configuration_Name = ComponentPreferenceManager.class.getName()
			+ "GENERATE_CONFIGURATION_NAME";
	/**
	 * Configuration Typeのキー
	 */
	private static final String Generate_Configuration_Type = ComponentPreferenceManager.class.getName()
			+ "GENERATE_CONFIGURATION_TYPE";
	/**
	 * Configuration Variable Nameのキー
	 */
	private static final String Generate_Configuration_VarName = ComponentPreferenceManager.class.getName()
			+ "GENERATE_CONFIGURATION_VARNAME";
	/**
	 * Configuration Default Valueのキー
	 */
	private static final String Generate_Configuration_Default = ComponentPreferenceManager.class.getName()
			+ "GENERATE_CONFIGURATION_DEFAULT";

	public static final String DEFAULT_COMPONENT_NAME = "ModuleName";
	public static final String DEFAULT_DESCRIPTION = "ModuleDescription";
	public static final String DEFAULT_CATEGORY = "Category";
	public static final String DEFAULT_VERSION = "1.0.0";
	public static final String DEFAULT_VENDER = "VenderName";
	public static final String DEFAULT_COMPONENT_TYPE = IRtcBuilderConstants.COMPONENT_TYPE_ITEMS[0];
	public static final String DEFAULT_ACTIVITY_TYPE = IRtcBuilderConstants.ACTIVITY_TYPE_ITEMS[0];
	public static final String DEFAULT_COMPONENT_KIND = IRtcBuilderConstants.COMPONENT_KIND_ITEMS[0];
	public static final int DEFAULT_MAXINST = 1;
	public static final String DEFAULT_EXECUTION_TYPE = IRtcBuilderConstants.EXECUTIONCONTEXT_TYPE_ITEMS[0];
	public static final double DEFAULT_EXECUTION_RATE = 1.0;
	//
	public static final String DEFAULT_DATAPORT_NAME = "dp_name";
	public static final String DEFAULT_DATAPORT_TYPE = "dp_type";
	public static final String DEFAULT_DATAPORT_VARNAME = "dp_vname";
	//
	public static final String DEFAULT_SERVICEPORT_NAME = "sv_name";
	public static final String DEFAULT_SERVICEIF_NAME = "if_name";
	public static final String DEFAULT_SERVICEIF_INSTANCENAME = "if_instance";
	public static final String DEFAULT_SERVICEIF_VARNAME = "if_varname";
	//
	public static final String DEFAULT_CONFIGURATION_NAME = "conf_name";
	public static final String DEFAULT_CONFIGURATION_TYPE = "conf_type";
	public static final String DEFAULT_CONFIGURATION_VARNAME = "conf_varname";
	public static final String DEFAULT_CONFIGURATION_DEFAULT = "conf_default";

	/**
	 * コード生成時の ModuleName デフォルト値を取得する
	 * 
	 * @param key キー
	 * @return Module Name デフォルト値
	 */
	public String getBasic_ComponentName() {
		RtcBuilderPlugin.getDefault().getPreferenceStore().setDefault(Generate_Basic_Name, "");

		String resultTemp = RtcBuilderPlugin.getDefault().getPreferenceStore().getString(Generate_Basic_Name);
		String result = new String();
		if (resultTemp.equals("")) { // defaultvalue
			result = DEFAULT_COMPONENT_NAME;
		} else {
			result = resultTemp;
		}
		return result;
	}
	/**
	 * コード生成時の ModuleName デフォルト値を設定する
	 * 
	 * @param key キー
	 * @param defaultModuleName	 ModuleName デフォルト値
	 */
	public void setBasic_ComponentName(String defaultModuleName) {
		String oldModuleName = getBasic_ComponentName();

		RtcBuilderPlugin.getDefault().getPreferenceStore().setValue(Generate_Basic_Name, defaultModuleName);

		propertyChangeSupport.firePropertyChange(Generate_Basic_Name, oldModuleName, defaultModuleName);
	}
	
	/**
	 * コード生成時の ModuleDescription デフォルト値を取得する
	 * 
	 * @param key キー
	 * @return Module Description デフォルト値
	 */
	public String getBasic_Description() {
		RtcBuilderPlugin.getDefault().getPreferenceStore().setDefault(Generate_Basic_Description, "");

		String resultTemp = RtcBuilderPlugin.getDefault().getPreferenceStore().getString(Generate_Basic_Description);
		String result = new String();
		if (resultTemp.equals("")) { // defaultvalue
			result = DEFAULT_DESCRIPTION;
		} else {
			result = resultTemp;
		}
		return result;
	}
	/**
	 * コード生成時の ModuleDescription デフォルト値を設定する
	 * 
	 * @param key キー
	 * @param defaultDescription	 Module Description デフォルト値
	 */
	public void setBasic_Description(String defaultDescription) {
		String oldDescription = getBasic_Description();

		RtcBuilderPlugin.getDefault().getPreferenceStore().setValue(Generate_Basic_Description, defaultDescription);

		propertyChangeSupport.firePropertyChange(Generate_Basic_Description, oldDescription, defaultDescription);
	}

	/**
	 * コード生成時の Module Category デフォルト値を取得する
	 * 
	 * @param key キー
	 * @return Module Category デフォルト値
	 */
	public String getBasic_Category() {
		RtcBuilderPlugin.getDefault().getPreferenceStore().setDefault(Generate_Basic_Category, "");

		String resultTemp = RtcBuilderPlugin.getDefault().getPreferenceStore().getString(Generate_Basic_Category);
		String result = new String();
		if (resultTemp.equals("")) { // defaultvalue
			result = DEFAULT_CATEGORY;
		} else {
			result = resultTemp;
		}
		return result;
	}
	/**
	 * コード生成時の Module Category デフォルト値を設定する
	 * 
	 * @param key キー
	 * @param defaultCategory	 Module Category デフォルト値
	 */
	public void setBasic_Category(String defaultCategory) {
		String oldCategory = getBasic_Category();

		RtcBuilderPlugin.getDefault().getPreferenceStore().setValue(Generate_Basic_Category, defaultCategory);

		propertyChangeSupport.firePropertyChange(Generate_Basic_Category, oldCategory, defaultCategory);
	}

	/**
	 * コード生成時の Module Version デフォルト値を取得する
	 * 
	 * @param key キー
	 * @return Module Version デフォルト値
	 */
	public String getBasic_Version() {
		RtcBuilderPlugin.getDefault().getPreferenceStore().setDefault(Generate_Basic_Version, "");

		String resultTemp = RtcBuilderPlugin.getDefault().getPreferenceStore().getString(Generate_Basic_Version);
		String result = new String();
		if (resultTemp.equals("")) { // defaultvalue
			result = DEFAULT_VERSION;
		} else {
			result = resultTemp;
		}
		return result;
	}
	/**
	 * コード生成時の Module Version デフォルト値を設定する
	 * 
	 * @param key キー
	 * @param defaultVersion	 Module Version デフォルト値
	 */
	public void setBasic_Version(String defaultVersion) {
		String oldVersion = getBasic_Version();

		RtcBuilderPlugin.getDefault().getPreferenceStore().setValue(Generate_Basic_Version, defaultVersion);

		propertyChangeSupport.firePropertyChange(Generate_Basic_Version, oldVersion, defaultVersion);
	}

	/**
	 * コード生成時の Module Component Type デフォルト値を取得する
	 * 
	 * @param key キー
	 * @return Module Component Type デフォルト値
	 */
	public String getBasic_ComponentType() {
		RtcBuilderPlugin.getDefault().getPreferenceStore().setDefault(Generate_Basic_ComponentType, "");

		String resultTemp = RtcBuilderPlugin.getDefault().getPreferenceStore().getString(Generate_Basic_ComponentType);
		String result = new String();
		if (resultTemp.equals("")) { // defaultvalue
			result = DEFAULT_COMPONENT_TYPE;
		} else {
			result = resultTemp;
		}
		return result;
	}
	/**
	 * コード生成時の Module Component Type デフォルト値を設定する
	 * 
	 * @param key キー
	 * @param defaultCompType	 Module Component Type デフォルト値
	 */
	public void setBasic_ComponentType(String defaultCompType) {
		String oldCompType = getBasic_ComponentType();

		RtcBuilderPlugin.getDefault().getPreferenceStore().setValue(Generate_Basic_ComponentType, defaultCompType);

		propertyChangeSupport.firePropertyChange(Generate_Basic_ComponentType, oldCompType, defaultCompType);
	}

	/**
	 * コード生成時の Module Activity Type デフォルト値を取得する
	 * 
	 * @param key キー
	 * @return Module Activity Type デフォルト値
	 */
	public String getBasic_ActivityType() {
		RtcBuilderPlugin.getDefault().getPreferenceStore().setDefault(Generate_Basic_ActivityType, "");

		String resultTemp = RtcBuilderPlugin.getDefault().getPreferenceStore().getString(Generate_Basic_ActivityType);
		String result = new String();
		if (resultTemp.equals("")) { // defaultvalue
			result = DEFAULT_ACTIVITY_TYPE;
		} else {
			result = resultTemp;
		}
		return result;
	}
	/**
	 * コード生成時の Module Activity Type デフォルト値を設定する
	 * 
	 * @param key キー
	 * @param defaultActivityType	 Module Activity Type デフォルト値
	 */
	public void setBasic_ActivityType(String defaultActivityType) {
		String oldActivityType = getBasic_ActivityType();

		RtcBuilderPlugin.getDefault().getPreferenceStore().setValue(Generate_Basic_ActivityType, defaultActivityType);

		propertyChangeSupport.firePropertyChange(Generate_Basic_ActivityType, oldActivityType, defaultActivityType);
	}

	/**
	 * コード生成時の Module Component Kind デフォルト値を取得する
	 * 
	 * @param key キー
	 * @return Module Component Kind デフォルト値
	 */
	public String getBasic_ComponentKind() {
		RtcBuilderPlugin.getDefault().getPreferenceStore().setDefault(Generate_Basic_ComponentKind, "");

		String resultTemp = RtcBuilderPlugin.getDefault().getPreferenceStore().getString(Generate_Basic_ComponentKind);
		String result = new String();
		if (resultTemp.equals("")) { // defaultvalue
			result = DEFAULT_COMPONENT_KIND;
		} else {
			result = resultTemp;
		}
		return result;
	}
	/**
	 * コード生成時の Module Component Kind デフォルト値を設定する
	 * 
	 * @param key キー
	 * @param defaultCompKind	 Module Component Kind デフォルト値
	 */
	public void setBasic_ComponentKind(String defaultCompKind) {
		String oldCompKind = getBasic_ComponentKind();

		RtcBuilderPlugin.getDefault().getPreferenceStore().setValue(Generate_Basic_ComponentKind, defaultCompKind);

		propertyChangeSupport.firePropertyChange(Generate_Basic_ComponentKind, oldCompKind, defaultCompKind);
	}

	/**
	 * コード生成時の Module Vendor Name デフォルト値を取得する
	 * 
	 * @param key キー
	 * @return Module Vendor Name デフォルト値
	 */
	public String getBasic_VendorName() {
		RtcBuilderPlugin.getDefault().getPreferenceStore().setDefault(Generate_Basic_Vendor_Name, "");

		String resultTemp = RtcBuilderPlugin.getDefault().getPreferenceStore().getString(Generate_Basic_Vendor_Name);
		String result = new String();
		if (resultTemp.equals("")) { // defaultvalue
			result = DEFAULT_VENDER;
		} else {
			result = resultTemp;
		}
		return result;
	}
	/**
	 * コード生成時の Module Vendor Name デフォルト値を設定する
	 * 
	 * @param key キー
	 * @param defaultVendor	 Module Vendor Name デフォルト値
	 */
	public void setBasic_VendorName(String defaultVendor) {
		String oldVendor = getBasic_VendorName();

		RtcBuilderPlugin.getDefault().getPreferenceStore().setValue(Generate_Basic_Vendor_Name, defaultVendor);

		propertyChangeSupport.firePropertyChange(Generate_Basic_Vendor_Name, oldVendor, defaultVendor);
	}

	/**
	 * コード生成時の Module Max Instances デフォルト値を取得する
	 * 
	 * @param key キー
	 * @return Module Max Instances デフォルト値
	 */
	public int getBasic_MaxInstances() {
		RtcBuilderPlugin.getDefault().getPreferenceStore().setDefault(Generate_Basic_Max_Instance, -1);

		int resultTemp = RtcBuilderPlugin.getDefault().getPreferenceStore().getInt(Generate_Basic_Max_Instance);
		int result;
		if (resultTemp == -1) { // defaultvalue
			result = DEFAULT_MAXINST;
		} else {
			result = resultTemp;
		}
		return result;
	}
	/**
	 * コード生成時の Module Max Instances デフォルト値を設定する
	 * 
	 * @param key キー
	 * @param defaultMaxInst	 Module Max Instances デフォルト値
	 */
	public void setBasic_MaxInstances(int defaultMaxInst) {
		int oldMaxInst = getBasic_MaxInstances();

		RtcBuilderPlugin.getDefault().getPreferenceStore().setValue(Generate_Basic_Max_Instance, defaultMaxInst);

		propertyChangeSupport.firePropertyChange(Generate_Basic_Max_Instance, oldMaxInst, defaultMaxInst);
	}

	/**
	 * コード生成時の Module Execution Type デフォルト値を取得する
	 * 
	 * @param key キー
	 * @return Module Execution Type デフォルト値
	 */
	public String getBasic_ExecutionType() {
		RtcBuilderPlugin.getDefault().getPreferenceStore().setDefault(Generate_Basic_ExecutionType, "");

		String resultTemp = RtcBuilderPlugin.getDefault().getPreferenceStore().getString(Generate_Basic_ExecutionType);
		String result = new String();
		if (resultTemp.equals("")) { // defaultvalue
			result = DEFAULT_EXECUTION_TYPE;
		} else {
			result = resultTemp;
		}
		return result;
	}
	/**
	 * コード生成時の Module Execution Type デフォルト値を設定する
	 * 
	 * @param key キー
	 * @param defaultExecutionType	 Module Execution Type デフォルト値
	 */
	public void setBasic_ExecutionType(String defaultExecutionType) {
		String oldExecutionType = getBasic_ExecutionType();

		RtcBuilderPlugin.getDefault().getPreferenceStore().setValue(Generate_Basic_ExecutionType, defaultExecutionType);

		propertyChangeSupport.firePropertyChange(Generate_Basic_ExecutionType, oldExecutionType, defaultExecutionType);
	}

	/**
	 * コード生成時の Execution Rate デフォルト値を取得する
	 * 
	 * @param key キー
	 * @return Execution Rate デフォルト値
	 */
	public double getBasic_ExecutionRate() {
		RtcBuilderPlugin.getDefault().getPreferenceStore().setDefault(Generate_Basic_Execution_Rate, -1);

		double resultTemp = RtcBuilderPlugin.getDefault().getPreferenceStore().getDouble(Generate_Basic_Execution_Rate);
		double result;
		if (resultTemp == -1) { // defaultvalue
			result = DEFAULT_EXECUTION_RATE;
		} else {
			result = resultTemp;
		}
		return result;
	}
	/**
	 * コード生成時の Execution Rate デフォルト値を設定する
	 * 
	 * @param key キー
	 * @param defaultExecutionRate	 Execution Rate デフォルト値
	 */
	public void setBasic_ExecutionRate(double defaultExecutionRate) {
		double oldExecutionRate = getBasic_ExecutionRate();

		RtcBuilderPlugin.getDefault().getPreferenceStore().setValue(Generate_Basic_Execution_Rate, defaultExecutionRate);

		propertyChangeSupport.firePropertyChange(Generate_Basic_Execution_Rate, oldExecutionRate, defaultExecutionRate);
	}
//
	/**
	 * コード生成時の DataPort Name デフォルト値を取得する
	 * 
	 * @param key キー
	 * @return DataPort Name デフォルト値
	 */
	public String getDataPort_Name() {
		RtcBuilderPlugin.getDefault().getPreferenceStore().setDefault(Generate_DataPort_Name, "");

		String resultTemp = RtcBuilderPlugin.getDefault().getPreferenceStore().getString(Generate_DataPort_Name);
		String result;
		if (resultTemp.equals("")) { // defaultvalue
			result = DEFAULT_DATAPORT_NAME;
		} else {
			result = resultTemp;
		}
		return result;
	}
	/**
	 * コード生成時の DataPort Name デフォルト値を設定する
	 * 
	 * @param key キー
	 * @param defaultDataPortName	 DataPort Name デフォルト値
	 */
	public void setDataPort_Name(String defaultDataPortName) {
		String oldDataPortName = getDataPort_Name();

		RtcBuilderPlugin.getDefault().getPreferenceStore().setValue(Generate_DataPort_Name, defaultDataPortName);

		propertyChangeSupport.firePropertyChange(Generate_DataPort_Name, oldDataPortName, defaultDataPortName);
	}

	/**
	 * コード生成時の DataPort Type デフォルト値を取得する
	 * 
	 * @param key キー
	 * @return DataPort Type デフォルト値
	 */
	public String getDataPort_Type() {
		RtcBuilderPlugin.getDefault().getPreferenceStore().setDefault(Generate_DataPort_Type, "");

		String resultTemp = RtcBuilderPlugin.getDefault().getPreferenceStore().getString(Generate_DataPort_Type);
		String result;
		if (resultTemp.equals("")) { // defaultvalue
			result = DEFAULT_DATAPORT_TYPE;
		} else {
			result = resultTemp;
		}
		return result;
	}
	/**
	 * コード生成時の DataPort Type デフォルト値を設定する
	 * 
	 * @param key キー
	 * @param defaultDataPortType	 DataPort Type デフォルト値
	 */
	public void setDataPort_Type(String defaultDataPortType) {
		String oldDataPortType = getDataPort_Type();

		RtcBuilderPlugin.getDefault().getPreferenceStore().setValue(Generate_DataPort_Type, defaultDataPortType);

		propertyChangeSupport.firePropertyChange(Generate_DataPort_Type, oldDataPortType, defaultDataPortType);
	}

	/**
	 * コード生成時の DataPort 変数名 デフォルト値を取得する
	 * 
	 * @param key キー
	 * @return DataPort 変数名 デフォルト値
	 */
	public String getDataPort_VarName() {
		RtcBuilderPlugin.getDefault().getPreferenceStore().setDefault(Generate_DataPort_VarName, "");

		String resultTemp = RtcBuilderPlugin.getDefault().getPreferenceStore().getString(Generate_DataPort_VarName);
		String result;
		if (resultTemp.equals("")) { // defaultvalue
			result = DEFAULT_DATAPORT_VARNAME;
		} else {
			result = resultTemp;
		}
		return result;
	}
	/**
	 * コード生成時の DataPort 変数名 デフォルト値を設定する
	 * 
	 * @param key キー
	 * @param defaultDataPortVarName	 DataPort 変数名 デフォルト値
	 */
	public void setDataPort_VarName(String defaultDataPortVarName) {
		String oldDataPortVarName = getDataPort_VarName();

		RtcBuilderPlugin.getDefault().getPreferenceStore().setValue(Generate_DataPort_VarName, defaultDataPortVarName);

		propertyChangeSupport.firePropertyChange(Generate_DataPort_VarName, oldDataPortVarName, defaultDataPortVarName);
	}

	/**
	 * コード生成時の ServicePort 名 デフォルト値を取得する
	 * 
	 * @param key キー
	 * @return ServicePort 変数名 デフォルト値
	 */
	public String getServicePort_Name() {
		RtcBuilderPlugin.getDefault().getPreferenceStore().setDefault(Generate_ServicePort_Name, "");

		String resultTemp = RtcBuilderPlugin.getDefault().getPreferenceStore().getString(Generate_ServicePort_Name);
		String result;
		if (resultTemp.equals("")) { // defaultvalue
			result = DEFAULT_SERVICEPORT_NAME;
		} else {
			result = resultTemp;
		}
		return result;
	}
	/**
	 * コード生成時の ServicePort 名 デフォルト値を設定する
	 * 
	 * @param key キー
	 * @param defaultServicePortName	 ServicePort 名 デフォルト値
	 */
	public void setServicePort_Name(String defaultServicePortName) {
		String oldServicePortName = getServicePort_Name();

		RtcBuilderPlugin.getDefault().getPreferenceStore().setValue(Generate_ServicePort_Name, defaultServicePortName);

		propertyChangeSupport.firePropertyChange(Generate_ServicePort_Name, oldServicePortName, defaultServicePortName);
	}

	/**
	 * コード生成時の ServiceIF 名 デフォルト値を取得する
	 * 
	 * @param key キー
	 * @return ServiceIF名 デフォルト値
	 */
	public String getServiceIF_Name() {
		RtcBuilderPlugin.getDefault().getPreferenceStore().setDefault(Generate_ServiceIF_Name, "");

		String resultTemp = RtcBuilderPlugin.getDefault().getPreferenceStore().getString(Generate_ServiceIF_Name);
		String result;
		if (resultTemp.equals("")) { // defaultvalue
			result = DEFAULT_SERVICEIF_NAME;
		} else {
			result = resultTemp;
		}
		return result;
	}
	/**
	 * コード生成時の ServiceIF 名 デフォルト値を設定する
	 * 
	 * @param key キー
	 * @param defaultServiceIFName	 ServiceIF 名 デフォルト値
	 */
	public void setServiceIF_Name(String defaultServiceIFName) {
		String oldServiceIFName = getServiceIF_Name();

		RtcBuilderPlugin.getDefault().getPreferenceStore().setValue(Generate_ServiceIF_Name, defaultServiceIFName);

		propertyChangeSupport.firePropertyChange(Generate_ServiceIF_Name, oldServiceIFName, defaultServiceIFName);
	}

	/**
	 * コード生成時の ServiceIF インスタンス名 デフォルト値を取得する
	 * 
	 * @param key キー
	 * @return ServiceIF インスタンス名 デフォルト値
	 */
	public String getServiceIF_InstanceName() {
		RtcBuilderPlugin.getDefault().getPreferenceStore().setDefault(Generate_ServiceIF_InstanceName, "");

		String resultTemp = RtcBuilderPlugin.getDefault().getPreferenceStore().getString(Generate_ServiceIF_InstanceName);
		String result;
		if (resultTemp.equals("")) { // defaultvalue
			result = DEFAULT_SERVICEIF_INSTANCENAME;
		} else {
			result = resultTemp;
		}
		return result;
	}
	/**
	 * コード生成時の ServiceIF インスタンス名 デフォルト値を設定する
	 * 
	 * @param key キー
	 * @param defaultServiceIFInstanceName	 ServiceIF インスタンス名 デフォルト値
	 */
	public void setServiceIF_InstanceName(String defaultServiceIFInstanceName) {
		String oldServiceIFInstanceName = getServiceIF_InstanceName();

		RtcBuilderPlugin.getDefault().getPreferenceStore().setValue(Generate_ServiceIF_InstanceName, defaultServiceIFInstanceName);

		propertyChangeSupport.firePropertyChange(Generate_ServiceIF_InstanceName, oldServiceIFInstanceName, defaultServiceIFInstanceName);
	}

	/**
	 * コード生成時の ServiceIF 変数名 デフォルト値を取得する
	 * 
	 * @param key キー
	 * @return ServiceIF 変数名 デフォルト値
	 */
	public String getServiceIF_VarName() {
		RtcBuilderPlugin.getDefault().getPreferenceStore().setDefault(Generate_ServiceIF_VarName, "");

		String resultTemp = RtcBuilderPlugin.getDefault().getPreferenceStore().getString(Generate_ServiceIF_VarName);
		String result;
		if (resultTemp.equals("")) { // defaultvalue
			result = DEFAULT_SERVICEIF_VARNAME;
		} else {
			result = resultTemp;
		}
		return result;
	}
	/**
	 * コード生成時の ServiceIF 変数名 デフォルト値を設定する
	 * 
	 * @param key キー
	 * @param defaultServiceIFVarName	 ServiceIF 変数名 デフォルト値
	 */
	public void setServiceIF_VarName(String defaultServiceIFVarName) {
		String oldServiceIFVarName = getServiceIF_VarName();

		RtcBuilderPlugin.getDefault().getPreferenceStore().setValue(Generate_ServiceIF_VarName, defaultServiceIFVarName);

		propertyChangeSupport.firePropertyChange(Generate_ServiceIF_VarName, oldServiceIFVarName, defaultServiceIFVarName);
	}

	/**
	 * コード生成時の Configuration Name デフォルト値を取得する
	 * 
	 * @param key キー
	 * @return Configuration Name デフォルト値
	 */
	public String getConfiguration_Name() {
		RtcBuilderPlugin.getDefault().getPreferenceStore().setDefault(Generate_Configuration_Name, "");

		String resultTemp = RtcBuilderPlugin.getDefault().getPreferenceStore().getString(Generate_Configuration_Name);
		String result;
		if (resultTemp.equals("")) { // defaultvalue
			result = DEFAULT_CONFIGURATION_NAME;
		} else {
			result = resultTemp;
		}
		return result;
	}
	/**
	 * コード生成時の Configuration Name デフォルト値を設定する
	 * 
	 * @param key キー
	 * @param defaultConfigurationName	 Configuration Name デフォルト値
	 */
	public void setConfiguration_Name(String defaultConfigurationName) {
		String oldConfigName = getConfiguration_Name();

		RtcBuilderPlugin.getDefault().getPreferenceStore().setValue(Generate_Configuration_Name, defaultConfigurationName);

		propertyChangeSupport.firePropertyChange(Generate_Configuration_Name, oldConfigName, defaultConfigurationName);
	}

	/**
	 * コード生成時の Configuration Type デフォルト値を取得する
	 * 
	 * @param key キー
	 * @return Configuration Type デフォルト値
	 */
	public String getConfiguration_Type() {
		RtcBuilderPlugin.getDefault().getPreferenceStore().setDefault(Generate_Configuration_Type, "");

		String resultTemp = RtcBuilderPlugin.getDefault().getPreferenceStore().getString(Generate_Configuration_Type);
		String result;
		if (resultTemp.equals("")) { // defaultvalue
			result = DEFAULT_CONFIGURATION_TYPE;
		} else {
			result = resultTemp;
		}
		return result;
	}
	/**
	 * コード生成時の Configuration Type デフォルト値を設定する
	 * 
	 * @param key キー
	 * @param defaultConfigurationType	 Configuration Type デフォルト値
	 */
	public void setConfiguration_Type(String defaultConfigurationType) {
		String oldConfigType = getConfiguration_Type();

		RtcBuilderPlugin.getDefault().getPreferenceStore().setValue(Generate_Configuration_Type, defaultConfigurationType);

		propertyChangeSupport.firePropertyChange(Generate_Configuration_Type, oldConfigType, defaultConfigurationType);
	}

	/**
	 * コード生成時の Configuration 変数名 デフォルト値を取得する
	 * 
	 * @param key キー
	 * @return Configuration 変数名 デフォルト値
	 */
	public String getConfiguration_VarName() {
		RtcBuilderPlugin.getDefault().getPreferenceStore().setDefault(Generate_Configuration_VarName, "");

		String resultTemp = RtcBuilderPlugin.getDefault().getPreferenceStore().getString(Generate_Configuration_VarName);
		String result;
		if (resultTemp.equals("")) { // defaultvalue
			result = DEFAULT_CONFIGURATION_VARNAME;
		} else {
			result = resultTemp;
		}
		return result;
	}
	/**
	 * コード生成時の Configuration 変数名 デフォルト値を設定する
	 * 
	 * @param key キー
	 * @param defaultConfigurationVarName	 Configuration 変数名 デフォルト値
	 */
	public void setConfiguration_VarName(String defaultConfigurationVarName) {
		String oldConfigVarName = getConfiguration_VarName();

		RtcBuilderPlugin.getDefault().getPreferenceStore().setValue(Generate_Configuration_VarName, defaultConfigurationVarName);

		propertyChangeSupport.firePropertyChange(Generate_Configuration_VarName, oldConfigVarName, defaultConfigurationVarName);
	}

	/**
	 * コード生成時の Configuration デフォルト値を取得する
	 * 
	 * @param key キー
	 * @return Configuration デフォルト値
	 */
	public String getConfiguration_Default() {
		RtcBuilderPlugin.getDefault().getPreferenceStore().setDefault(Generate_Configuration_Default, "");

		String resultTemp = RtcBuilderPlugin.getDefault().getPreferenceStore().getString(Generate_Configuration_Default);
		String result;
		if (resultTemp.equals("")) { // defaultvalue
			result = DEFAULT_CONFIGURATION_DEFAULT;
		} else {
			result = resultTemp;
		}
		return result;
	}
	/**
	 * コード生成時の Configuration デフォルト値を設定する
	 * 
	 * @param key キー
	 * @param defaultConfigurationDefault	 Configuration デフォルト値
	 */
	public void setConfiguration_Default(String defaultConfigurationDefault) {
		String oldConfigDefault = getConfiguration_Default();

		RtcBuilderPlugin.getDefault().getPreferenceStore().setValue(Generate_Configuration_Default, defaultConfigurationDefault);

		propertyChangeSupport.firePropertyChange(Generate_Configuration_Default, oldConfigDefault, defaultConfigurationDefault);
	}

	/**
	 * @see PropertyChangeSupport#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	/**
	 * @see PropertyChangeSupport#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}
}
