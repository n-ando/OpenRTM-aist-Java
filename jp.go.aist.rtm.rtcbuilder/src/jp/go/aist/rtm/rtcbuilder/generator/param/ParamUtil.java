package jp.go.aist.rtm.rtcbuilder.generator.param;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeFactory;

import jp.go.aist.rtm.rtcbuilder.IRtcBuilderConstants;
import jp.go.aist.rtm.rtcbuilder.IRtcBuilderConstantsJava;
import jp.go.aist.rtm.rtcbuilder.IRtcBuilderConstantsPython;
import jp.go.aist.rtm.rtcbuilder.ui.preference.ComponentPreferenceManager;
import jp.go.aist.rtm.rtcbuilder.ui.preference.ConfigPreferenceManager;
import jp.go.aist.rtm.rtcbuilder.ui.preference.DocumentPreferenceManager;

import org.openrtp.namespaces.rtc.ActionStatusDoc;
import org.openrtp.namespaces.rtc.Actions;
import org.openrtp.namespaces.rtc.BasicInfoExt;
import org.openrtp.namespaces.rtc.ConfigurationDoc;
import org.openrtp.namespaces.rtc.Cxxlang;
import org.openrtp.namespaces.rtc.DataportExt;
import org.openrtp.namespaces.rtc.DocAction;
import org.openrtp.namespaces.rtc.DocBasic;
import org.openrtp.namespaces.rtc.DocConfiguration;
import org.openrtp.namespaces.rtc.DocDataport;
import org.openrtp.namespaces.rtc.DocServiceinterface;
import org.openrtp.namespaces.rtc.DocServiceport;
import org.openrtp.namespaces.rtc.Javalang;
import org.openrtp.namespaces.rtc.Language;
import org.openrtp.namespaces.rtc.ObjectFactory;
import org.openrtp.namespaces.rtc.Parameter;
import org.openrtp.namespaces.rtc.Position;
import org.openrtp.namespaces.rtc.RtcProfile;
import org.openrtp.namespaces.rtc.ServiceinterfaceDoc;
import org.openrtp.namespaces.rtc.ServiceportExt;

import com.sun.org.apache.xerces.internal.jaxp.datatype.DatatypeFactoryImpl;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

public class ParamUtil {
	
	public static RtcProfile initialXml(String creationDate) {
		ObjectFactory factory = new ObjectFactory();
		RtcProfile profileType = factory.createRtcProfile();
		String moduleId = IRtcBuilderConstants.SPEC_SUFFIX + IRtcBuilderConstants.SPEC_MAJOR_SEPARATOR +
							ComponentPreferenceManager.getInstance().getBasic_VendorName() + IRtcBuilderConstants.SPEC_MINOR_SEPARATOR +
							ComponentPreferenceManager.getInstance().getBasic_Category() + IRtcBuilderConstants.SPEC_MINOR_SEPARATOR +
							ComponentPreferenceManager.getInstance().getBasic_ComponentName() + IRtcBuilderConstants.SPEC_MAJOR_SEPARATOR +
							ComponentPreferenceManager.getInstance().getBasic_Version();
		profileType.setId(moduleId);
		
		BasicInfoExt basic = factory.createBasicInfoExt();
		basic.setName(ComponentPreferenceManager.getInstance().getBasic_ComponentName());
		basic.setDescription(ComponentPreferenceManager.getInstance().getBasic_Description());
		basic.setVersion(ComponentPreferenceManager.getInstance().getBasic_Version());
		basic.setVendor(ComponentPreferenceManager.getInstance().getBasic_VendorName());
		basic.setCategory(ComponentPreferenceManager.getInstance().getBasic_Category());
		basic.setComponentType(ComponentPreferenceManager.getInstance().getBasic_ComponentType());
		basic.setActivityType(ComponentPreferenceManager.getInstance().getBasic_ActivityType());
		basic.setComponentKind(ComponentPreferenceManager.getInstance().getBasic_ComponentKind());
		basic.setMaxInstances(BigInteger.valueOf(ComponentPreferenceManager.getInstance().getBasic_MaxInstances()));
		basic.setExecutionType(ComponentPreferenceManager.getInstance().getBasic_ExecutionType());
		basic.setExecutionRate(ComponentPreferenceManager.getInstance().getBasic_ExecutionRate());
		//
		DatatypeFactory dateFactory = new DatatypeFactoryImpl();
		basic.setCreationDate(dateFactory.newXMLGregorianCalendar(creationDate));
		basic.setUpdateDate(dateFactory.newXMLGregorianCalendar(creationDate));
		//
		DocBasic docBasic = factory.createDocBasic();
		docBasic.setCreator(DocumentPreferenceManager.getInstance().getCreatorValue());
		docBasic.setLicense(DocumentPreferenceManager.getInstance().getLicenseValue());
		basic.setDoc(docBasic);
		
		profileType.setBasicInfo(basic);
		//
		ArrayList<String> docs = DocumentPreferenceManager.getInstance().getDocumentValue();
		Actions actionType = factory.createActions();
		//
		ActionStatusDoc actionStatus = factory.createActionStatusDoc();
		actionStatus.setImplemented(Boolean.valueOf(docs.get(IRtcBuilderConstants.ACTIVITY_INITIALIZE)).booleanValue());
		actionType.setOnInitialize(actionStatus);

		actionStatus = factory.createActionStatusDoc();
		actionStatus.setImplemented(Boolean.valueOf(docs.get(IRtcBuilderConstants.ACTIVITY_FINALIZE)).booleanValue());
		actionType.setOnFinalize(actionStatus);

		actionStatus = factory.createActionStatusDoc();
		actionStatus.setImplemented(Boolean.valueOf(docs.get(IRtcBuilderConstants.ACTIVITY_STARTUP)).booleanValue());
		actionType.setOnStartup(actionStatus);

		actionStatus = factory.createActionStatusDoc();
		actionStatus.setImplemented(Boolean.valueOf(docs.get(IRtcBuilderConstants.ACTIVITY_SHUTDOWN)).booleanValue());
		actionType.setOnShutdown(actionStatus);

		actionStatus = factory.createActionStatusDoc();
		actionStatus.setImplemented(Boolean.valueOf(docs.get(IRtcBuilderConstants.ACTIVITY_ACTIVATED)).booleanValue());
		actionType.setOnActivated(actionStatus);

		actionStatus = factory.createActionStatusDoc();
		actionStatus.setImplemented(Boolean.valueOf(docs.get(IRtcBuilderConstants.ACTIVITY_DEACTIVATED)).booleanValue());
		actionType.setOnDeactivated(actionStatus);

		actionStatus = factory.createActionStatusDoc();
		actionStatus.setImplemented(Boolean.valueOf(docs.get(IRtcBuilderConstants.ACTIVITY_EXECUTE)).booleanValue());
		actionType.setOnExecute(actionStatus);

		actionStatus = factory.createActionStatusDoc();
		actionStatus.setImplemented(Boolean.valueOf(docs.get(IRtcBuilderConstants.ACTIVITY_ABORTING)).booleanValue());
		actionType.setOnAborting(actionStatus);

		actionStatus = factory.createActionStatusDoc();
		actionStatus.setImplemented(Boolean.valueOf(docs.get(IRtcBuilderConstants.ACTIVITY_ERROR)).booleanValue());
		actionType.setOnError(actionStatus);

		actionStatus = factory.createActionStatusDoc();
		actionStatus.setImplemented(Boolean.valueOf(docs.get(IRtcBuilderConstants.ACTIVITY_RESET)).booleanValue());
		actionType.setOnReset(actionStatus);

		actionStatus = factory.createActionStatusDoc();
		actionStatus.setImplemented(Boolean.valueOf(docs.get(IRtcBuilderConstants.ACTIVITY_STATE_UPDATE)).booleanValue());
		actionType.setOnStateUpdate(actionStatus);

		actionStatus = factory.createActionStatusDoc();
		actionStatus.setImplemented(Boolean.valueOf(docs.get(IRtcBuilderConstants.ACTIVITY_RATE_CHANGED)).booleanValue());
		actionType.setOnRateChanged(actionStatus);

		profileType.setActions(actionType);
		return profileType;
	}

	public static RtcParam convertFromModule(RtcProfile profile, GeneratorParam generatorParam) {
		RtcParam rtcParam = new RtcParam(generatorParam);
		
		rtcParam.setSchemaVersion(profile.getVersion());

		BasicInfoExt basic = (BasicInfoExt)profile.getBasicInfo();
		//Šî–{
		rtcParam.setName(basic.getName());
		rtcParam.setDescription(basic.getDescription());
		rtcParam.setVersion(basic.getVersion());
		rtcParam.setVender(basic.getVendor());
		rtcParam.setCategory(basic.getCategory());
		rtcParam.setComponentType(basic.getComponentType());
		rtcParam.setActivityType(basic.getActivityType());
		rtcParam.setComponentKind(basic.getComponentKind());
		if( basic.getMaxInstances() != null )
			rtcParam.setMaxInstance(basic.getMaxInstances().intValue());
		rtcParam.setExecutionType(basic.getExecutionType());
		rtcParam.setExecutionRate(basic.getExecutionRate());
		rtcParam.setAbstract(basic.getAbstract());
		rtcParam.setCreationDate(basic.getCreationDate().toString());
		rtcParam.setUpdateDate(basic.getUpdateDate().toString());
		rtcParam.setVersionUpLog(basic.getVersionUpLog());
		//Doc Basic
		DocBasic docbasic = basic.getDoc();
		if( docbasic != null ) {
			rtcParam.setDocDescription(docbasic.getDescription());
			rtcParam.setDocInOut(docbasic.getInout());
			rtcParam.setDocAlgorithm(docbasic.getAlgorithm());
			rtcParam.setDocCreator(docbasic.getCreator());
			rtcParam.setDocLicense(docbasic.getLicense());
			rtcParam.setDocReference(docbasic.getReference());
		}
		//Data Ports
		if( profile.getDataPorts() != null ) {
			createDataPortParam(profile.getDataPorts(), rtcParam);
		}
		//Service Ports
		if( profile.getServicePorts() != null ) {
			createServicePortParam(profile.getServicePorts(), rtcParam.getServicePorts());
		}
		//Configuration
		if( profile.getConfigurationSet() != null ) {
			createConfigParam(profile.getConfigurationSet().getConfiguration(), rtcParam);
		}
		//Parameter
		String[] Config_Items = ConfigPreferenceManager.getInstance().getConfigName();
		for( Object paramObj : profile.getParameters() ) {
			Parameter param = (Parameter)paramObj;
			ConfigParameterParam paramp = new ConfigParameterParam(Config_Items);
			paramp.setConfigName(param.getName());
			paramp.setDefaultVal(param.getDefaultValue());
			
			for( int intIdx=0; intIdx<Config_Items.length;intIdx++ ) {
				if( param.getName().equals(Config_Items[intIdx]) ) {
					paramp.setIndex(intIdx);
					break;
				}
			}
			rtcParam.getConfigParameterParams().add(paramp);
		}
		//Language
		Language language = profile.getLanguage();
		if( language != null ) {
			Cxxlang cxx = language.getCxx();
			Javalang java = language.getJava();
			if( cxx != null ) {
				if( cxx.getOs().equals("Windows") ) {
					rtcParam.getLangList().add(IRtcBuilderConstants.LANG_CPPWIN);
				} else {
					rtcParam.getLangList().add(IRtcBuilderConstants.LANG_CPP);
				}
				rtcParam.setArchitecture(cxx.getArch());
				rtcParam.setCxxLibraryPathes(cxx.getLibrary());
			} else 	if( java != null ) {
				rtcParam.getLangList().add(IRtcBuilderConstantsJava.LANG_JAVA);
				rtcParam.setJavaClassPathes(java.getLibrary());
			} else if( language.getPython() != null ) {
				rtcParam.getLangList().add(IRtcBuilderConstantsPython.LANG_PYTHON);
			} else if( language.getCsharp() != null ) {
				rtcParam.getLangList().add(IRtcBuilderConstants.LANG_CSHARP);
			} else if( language.getRuby() != null ) {
				rtcParam.getLangList().add(IRtcBuilderConstants.LANG_RUBY);
			}
		}
		//Actions
		Actions actions = profile.getActions();
		if( actions != null ) {
			if( actions.getOnInitialize() != null )
				setActions( rtcParam, IRtcBuilderConstants.ACTIVITY_INITIALIZE, (ActionStatusDoc)actions.getOnInitialize());
			if( actions.getOnFinalize() != null )
				setActions( rtcParam, IRtcBuilderConstants.ACTIVITY_FINALIZE, (ActionStatusDoc)actions.getOnFinalize());
			if( actions.getOnStartup() != null )
				setActions( rtcParam, IRtcBuilderConstants.ACTIVITY_STARTUP, (ActionStatusDoc)actions.getOnStartup());
			if( actions.getOnShutdown() != null )
				setActions( rtcParam, IRtcBuilderConstants.ACTIVITY_SHUTDOWN, (ActionStatusDoc)actions.getOnShutdown());
			if( actions.getOnActivated() != null )
				setActions( rtcParam, IRtcBuilderConstants.ACTIVITY_ACTIVATED, (ActionStatusDoc)actions.getOnActivated());
			if( actions.getOnDeactivated() != null )
				setActions( rtcParam, IRtcBuilderConstants.ACTIVITY_DEACTIVATED, (ActionStatusDoc)actions.getOnDeactivated());
			if( actions.getOnExecute() != null )
				setActions( rtcParam, IRtcBuilderConstants.ACTIVITY_EXECUTE, (ActionStatusDoc)actions.getOnExecute());
			if( actions.getOnAborting() != null )
				setActions( rtcParam, IRtcBuilderConstants.ACTIVITY_ABORTING, (ActionStatusDoc)actions.getOnAborting());
			if( actions.getOnError() != null )
				setActions( rtcParam, IRtcBuilderConstants.ACTIVITY_ERROR, (ActionStatusDoc)actions.getOnError());
			if( actions.getOnReset() != null )
				setActions( rtcParam, IRtcBuilderConstants.ACTIVITY_RESET, (ActionStatusDoc)actions.getOnReset());
			if( actions.getOnStateUpdate() != null )
				setActions( rtcParam, IRtcBuilderConstants.ACTIVITY_STATE_UPDATE, (ActionStatusDoc)actions.getOnStateUpdate());
			if( actions.getOnRateChanged() != null )
				setActions( rtcParam, IRtcBuilderConstants.ACTIVITY_RATE_CHANGED, (ActionStatusDoc)actions.getOnRateChanged());
		}
		//
		return rtcParam;
	}

	private static void createConfigParam(List configs, RtcParam rtcParam) {
		for( Object config : configs ) {
			ConfigurationDoc configDoc = (ConfigurationDoc)config;
			ConfigSetParam configp = new ConfigSetParam(
					configDoc.getName(), configDoc.getType(), configDoc.getVarname(), configDoc.getDefaultValue());
			DocConfiguration docConfig = configDoc.getDoc();
			if( docConfig!=null ) {
				configp.setDocDataName(docConfig.getDataname());
				configp.setDocDescription(docConfig.getDescription());
				configp.setDocDefaultVal(docConfig.getDefaultValue());
				configp.setDocUnit(docConfig.getUnit());
				configp.setDocRange(docConfig.getRange());
				configp.setDocConstraint(docConfig.getConstraint());
			}
			rtcParam.getConfigParams().add(configp);
		}
	}
	
	private static void setActions(RtcParam rtcParam, int actionId, ActionStatusDoc actionStatus) {
		rtcParam.setActionImplemented( actionId, actionStatus.isImplemented());
		if( actionStatus.getDoc() != null ) {
			rtcParam.setDocActionOverView( actionId, actionStatus.getDoc().getDescription()); 
			rtcParam.setDocActionPreCondition( actionId, actionStatus.getDoc().getPreCondition()); 
			rtcParam.setDocActionPostCondition( actionId, actionStatus.getDoc().getPostCondition()); 
		}
	}

	private static void createServicePortParam(List servicePorts, List<ServicePortParam> targetPort) {
		for( Object serviceport : servicePorts ) {
			ServiceportExt servicePortExt = (ServiceportExt)serviceport;
			ServicePortParam serviceportp = new ServicePortParam();
			serviceportp.setName(servicePortExt.getName());
			serviceportp.setPosition(servicePortExt.getPosition().toString());
			DocServiceport doc = servicePortExt.getDoc();
			if( doc != null ) {
				serviceportp.setDocDescription(doc.getDescription());
				serviceportp.setDocIfDescription(doc.getIfdescription());
			}
			//Service Interface
			for( Object serviceinterface : servicePortExt.getServiceInterface() ) {
				ServiceinterfaceDoc serviceIfDoc = (ServiceinterfaceDoc)serviceinterface;
				DocServiceinterface docSrv = serviceIfDoc.getDoc();
				ServicePortInterfaceParam serviceIF = new ServicePortInterfaceParam(serviceportp);
				serviceIF.setName(serviceIfDoc.getName());
				serviceIF.setDirection(serviceIfDoc.getDirection());
				serviceIF.setInstanceName(serviceIfDoc.getInstanceName());
				serviceIF.setVarName(serviceIfDoc.getVarname());
				serviceIF.setIdlFile(serviceIfDoc.getIdlFile());
				serviceIF.setInterfaceType(serviceIfDoc.getType());
				serviceIF.setIdlSearchPath(serviceIfDoc.getPath());
				if( docSrv!=null ) {
					serviceIF.setDocDescription(docSrv.getDescription());
					serviceIF.setDocArgument(docSrv.getDocArgument());
					serviceIF.setDocReturn(docSrv.getDocReturn());
					serviceIF.setDocException(docSrv.getDocException());
					serviceIF.setDocPreCondition(docSrv.getDocPreCondition());
					serviceIF.setDocPostCondition(docSrv.getDocPostCondition());
				}
				serviceportp.getServicePortInterfaces().add(serviceIF);
			}
			targetPort.add(serviceportp);
		}
	}

	private static void createDataPortParam(List dataPorts, RtcParam rtcParam) {
		List<DataPortParam> InPortList = new ArrayList<DataPortParam>();
		List<DataPortParam> OutPortList = new ArrayList<DataPortParam>();
		for( Object dataport : dataPorts ) {
			DataportExt dataPortExt = (DataportExt)dataport;
			DataPortParam dataportp = new DataPortParam();
			dataportp.setName(dataPortExt.getName());
			dataportp.setType(dataPortExt.getType());
			dataportp.setVarName(dataPortExt.getVarname());
			dataportp.setPosition(dataPortExt.getPosition().toString());
			dataportp.setDataFlowType(dataPortExt.getDataflowType());
			dataportp.setInterfaceType(dataPortExt.getInterfaceType());
			dataportp.setSubscriptionType(dataPortExt.getSubscriprionType());
			dataportp.setIdlFile(dataPortExt.getIdlFile());
			
			DocDataport docPort = dataPortExt.getDoc();
			if( docPort!=null ) {
				dataportp.setDocDescription(docPort.getDescription());
				dataportp.setDocType(docPort.getType());
				dataportp.setDocNum(docPort.getNumber());
				dataportp.setDocSemantics(docPort.getSemantics());
				dataportp.setDocUnit(docPort.getUnit());
				dataportp.setDocOccurrence(docPort.getOccerrence());
				dataportp.setDocOperation(docPort.getOperation());
			}
			if(dataPortExt.getPortType().equals(IRtcBuilderConstants.SPEC_DATA_INPORT_KIND) )
				InPortList.add(dataportp);
			else
				OutPortList.add(dataportp);
		}
		rtcParam.setInports(InPortList);
		rtcParam.setOutports(OutPortList);
	}

	public static RtcProfile convertToModule(GeneratorParam generatorParam) {
		RtcParam rtcParam = generatorParam.getRtcParams().get(0);
		
		ObjectFactory factory = new ObjectFactory();
		RtcProfile profile = factory.createRtcProfile();
		String moduleId = IRtcBuilderConstants.SPEC_SUFFIX + IRtcBuilderConstants.SPEC_MAJOR_SEPARATOR +
							rtcParam.getVender() + IRtcBuilderConstants.SPEC_MINOR_SEPARATOR +
							rtcParam.getCategory() + IRtcBuilderConstants.SPEC_MINOR_SEPARATOR +
							rtcParam.getName() + IRtcBuilderConstants.SPEC_MAJOR_SEPARATOR +
							rtcParam.getVersion();
		profile.setId(moduleId);
		profile.setVersion(rtcParam.getSchemaVersion());
		//Šî–{
		BasicInfoExt basic = factory.createBasicInfoExt();
		basic.setName(rtcParam.getName());
		basic.setDescription(rtcParam.getDescription());
		basic.setVersion(rtcParam.getVersion());
		basic.setVendor(rtcParam.getVender());
		basic.setCategory(rtcParam.getCategory());
		basic.setComponentType(rtcParam.getComponentType());
		basic.setActivityType(rtcParam.getActivityType());
		basic.setComponentKind(rtcParam.getComponentKind());
		basic.setMaxInstances(BigInteger.valueOf(rtcParam.getMaxInstance()));
		basic.setExecutionType(rtcParam.getExecutionType());
		basic.setExecutionRate(rtcParam.getExecutionRate());
		//
		basic.setAbstract(rtcParam.getAbstract());
		if(rtcParam.getCreationDate()!=null) basic.setCreationDate(XMLGregorianCalendarImpl.parse(rtcParam.getCreationDate()));
		if(rtcParam.getUpdateDate()!=null) basic.setUpdateDate(XMLGregorianCalendarImpl.parse(rtcParam.getUpdateDate()));
		if(rtcParam.getVersionUpLog()!=null)basic.getVersionUpLog().addAll(rtcParam.getVersionUpLog());
		//Doc Basic
		DocBasic docbasic = factory.createDocBasic();
		if( rtcParam.isDocExist() ) {
			docbasic.setDescription(rtcParam.getDocDescription());
			docbasic.setInout(rtcParam.getDocInOut());
			docbasic.setAlgorithm(rtcParam.getDocAlgorithm());
			docbasic.setCreator(rtcParam.getDocCreator());
			docbasic.setLicense(rtcParam.getDocLicense());
			docbasic.setReference(rtcParam.getDocReference());
			basic.setDoc(docbasic);
		}
		profile.setBasicInfo(basic);
		//Actions
		Actions actions = factory.createActions();
		actions.setOnInitialize(createActions(IRtcBuilderConstants.ACTIVITY_INITIALIZE, rtcParam));
		actions.setOnFinalize(createActions(IRtcBuilderConstants.ACTIVITY_FINALIZE, rtcParam));
		actions.setOnStartup(createActions(IRtcBuilderConstants.ACTIVITY_STARTUP, rtcParam));
		actions.setOnShutdown(createActions(IRtcBuilderConstants.ACTIVITY_SHUTDOWN, rtcParam));
		actions.setOnActivated(createActions(IRtcBuilderConstants.ACTIVITY_ACTIVATED, rtcParam));
		actions.setOnDeactivated(createActions(IRtcBuilderConstants.ACTIVITY_DEACTIVATED, rtcParam));
		actions.setOnExecute(createActions(IRtcBuilderConstants.ACTIVITY_EXECUTE, rtcParam));
		actions.setOnAborting(createActions(IRtcBuilderConstants.ACTIVITY_ABORTING, rtcParam));
		actions.setOnError(createActions(IRtcBuilderConstants.ACTIVITY_ERROR, rtcParam));
		actions.setOnReset(createActions(IRtcBuilderConstants.ACTIVITY_RESET, rtcParam));
		actions.setOnStateUpdate(createActions(IRtcBuilderConstants.ACTIVITY_STATE_UPDATE, rtcParam));
		actions.setOnRateChanged(createActions(IRtcBuilderConstants.ACTIVITY_RATE_CHANGED, rtcParam));
		profile.setActions(actions);
		
		//Data Ports
		for( DataPortParam dataportp : rtcParam.getInports() ) {
			profile.getDataPorts().add(createDataPort(dataportp, IRtcBuilderConstants.SPEC_DATA_INPORT_KIND));
		}
		for( DataPortParam dataportp : rtcParam.getOutports() ) {
			profile.getDataPorts().add(createDataPort(dataportp, IRtcBuilderConstants.SPEC_DATA_OUTPORT_KIND));
		}
		//Service Ports
		for( ServicePortParam serviceportp : rtcParam.getServicePorts() ) {
			ServiceportExt serviceport = createServicePort(serviceportp);
			profile.getServicePorts().add(serviceport);
		}
		//Configuration
		for( ConfigSetParam configp : rtcParam.getConfigParams() ) {
			ConfigurationDoc config = factory.createConfigurationDoc();
			config.setName(configp.getName());
			config.setType(configp.getType());
			config.setVarname(configp.getVarName());
			config.setDefaultValue(configp.getDefaultVal());
			profile.getConfigurationSet().getConfiguration().add(config);
			//
			DocConfiguration docconfig = factory.createDocConfiguration();
			docconfig.setDataname(configp.getDocDataName());
			docconfig.setDefaultValue(configp.getDocDefaultVal());
			docconfig.setDescription(configp.getDocDescription());
			docconfig.setUnit(configp.getDocUnit());
			docconfig.setRange(configp.getDocRange());
			docconfig.setConstraint(configp.getDocConstraint());
			if( !configp.getDocDataName().equals("") ||
				 !configp.getDocDefaultVal().equals("") ||
				 !configp.getDocDescription().equals("") ||
				 !configp.getDocUnit().equals("") ||
				 !configp.getDocRange().equals("") ||
				 !configp.getDocConstraint().equals("") ) {
				config.setDoc(docconfig);
			}

		}
		//Parameter
		for( ConfigParameterParam configp : rtcParam.getConfigParameterParams() ) {
			Parameter param = factory.createParameter();
			param.setName(configp.getConfigName());
			param.setDefaultValue(configp.getDefaultVal());
			profile.getParameters().add(param);
		}
		//Language
		for( String languagep : rtcParam.getLangList() ) {
			Language language = factory.createLanguage();
			if(languagep.equals(IRtcBuilderConstants.LANG_CPP)) {
				Cxxlang cxx = factory.createCxxlang();
				cxx.setOs("etc");
				cxx.setArch(rtcParam.getArchitecture());
				cxx.getLibrary().clear();
				cxx.getLibrary().addAll(rtcParam.getCxxLibraryPathes());
				language.setCxx(cxx);
			} else 	if(languagep.equals(IRtcBuilderConstants.LANG_CPPWIN)) {
				Cxxlang cxx = factory.createCxxlang();
				cxx.setOs("Windows");
				cxx.setArch(rtcParam.getArchitecture());
				cxx.getLibrary().clear();
				cxx.getLibrary().addAll(rtcParam.getCxxLibraryPathes());
				language.setCxx(cxx);
			}  else if(languagep.equals(IRtcBuilderConstantsJava.LANG_JAVA)) {
				Javalang javaLang = factory.createJavalang();
				javaLang.getLibrary().clear();
				javaLang.getLibrary().addAll(rtcParam.getJavaClassPathes());
				language.setJava(javaLang);
			}  else if(languagep.equals(IRtcBuilderConstantsPython.LANG_PYTHON)) {
				language.setPython("Python");
			}  else if(languagep.equals(IRtcBuilderConstants.LANG_CSHARP)) {
				language.setCsharp("Csharp");
			}  else if(languagep.equals(IRtcBuilderConstants.LANG_RUBY)) {
				language.setRuby("Ruby");
			}
			profile.setLanguage(language);
		}
		//
		return profile;
	}

	private static DataportExt createDataPort(DataPortParam dataportp, String portType) {
		ObjectFactory factory = new ObjectFactory();
		DataportExt dataport = factory.createDataportExt();
		dataport.setPortType(portType);
		dataport.setName(dataportp.getName());
		dataport.setType(dataportp.getType());
		dataport.setVarname(dataportp.getVarName());
		dataport.setPosition(Position.valueOf(dataportp.getPosition()));
		dataport.setIdlFile(dataportp.getIdlFile());
		dataport.setDataflowType(dataportp.getDataFlowType());
		dataport.setInterfaceType(dataportp.getInterfaceType());
		dataport.setSubscriprionType(dataportp.getSubscriptionType());
		//
		DocDataport docdataport = factory.createDocDataport();
		docdataport.setDescription(dataportp.getDocDescription());
		docdataport.setType(dataportp.getDocType());
		docdataport.setNumber(dataportp.getDocNum());
		docdataport.setSemantics(dataportp.getDocSemantics());
		docdataport.setUnit(dataportp.getDocUnit());
		docdataport.setOccerrence(dataportp.getDocOccurrence());
		docdataport.setOperation(dataportp.getDocOperation());
		if( !dataportp.getDocDescription().equals("") ||
			 !dataportp.getDocType().equals("") ||
			 !dataportp.getDocNum().equals("") ||
			 !dataportp.getDocSemantics().equals("") ||
			 !dataportp.getDocUnit().equals("") ||
			 !dataportp.getDocOccurrence().equals("") ||
			 !dataportp.getDocOperation().equals("") ) {
			dataport.setDoc(docdataport);
		}
		//
		return dataport;
	}

	private static ServiceportExt createServicePort(ServicePortParam serviceportp) {
		ObjectFactory factory = new ObjectFactory();
		ServiceportExt serviceport = factory.createServiceportExt();
		serviceport.setName(serviceportp.getName());
		serviceport.setPosition(Position.valueOf(serviceportp.getPosition()));
		//
		DocServiceport docserviceport = factory.createDocServiceport();
		docserviceport.setDescription(serviceportp.getDocDescription());
		docserviceport.setIfdescription(serviceportp.getDocIfDescription());
		if( !serviceportp.getDocDescription().equals("") ||
			 !serviceportp.getDocIfDescription().equals("") ) {
			serviceport.setDoc(docserviceport);
		}
		//Service Interface
		for( ServicePortInterfaceParam serviceinterfacep : serviceportp.getServicePortInterfaces() ) {
			ServiceinterfaceDoc serviceIF = factory.createServiceinterfaceDoc();
			serviceIF.setName(serviceinterfacep.getName());
			serviceIF.setDirection(serviceinterfacep.getDirection());
			serviceIF.setInstanceName(serviceinterfacep.getInstanceName());
			serviceIF.setVarname(serviceinterfacep.getVarName());
			serviceIF.setIdlFile(serviceinterfacep.getIdlFile());
			serviceIF.setType(serviceinterfacep.getInterfaceType());
			serviceIF.setPath(serviceinterfacep.getIdlSearchPath());
			//
			DocServiceinterface docserviceIF = factory.createDocServiceinterface();
			docserviceIF.setDescription(serviceinterfacep.getDocDescription());
			docserviceIF.setDocArgument(serviceinterfacep.getDocArgument());
			docserviceIF.setDocReturn(serviceinterfacep.getDocReturn());
			docserviceIF.setDocException(serviceinterfacep.getDocException());
			docserviceIF.setDocPreCondition(serviceinterfacep.getDocPreCondition());
			docserviceIF.setDocPostCondition(serviceinterfacep.getDocPostCondition());
			if( !serviceinterfacep.getDocDescription().equals("") ||
				 !serviceinterfacep.getDocArgument().equals("") ||
				 !serviceinterfacep.getDocReturn().equals("") ||
				 !serviceinterfacep.getDocException().equals("") ||
				 !serviceinterfacep.getDocPreCondition().equals("") ||
				 !serviceinterfacep.getDocPostCondition().equals("") ) {
				serviceIF.setDoc(docserviceIF);
			}
			
			serviceport.getServiceInterface().add(serviceIF);
		}
		return serviceport;
	}

	private static ActionStatusDoc createActions(int actionId, RtcParam rtcParam) {
		ObjectFactory factory = new ObjectFactory();
		ActionStatusDoc status = factory.createActionStatusDoc();
		DocAction docAction = factory.createDocAction();
		//
		docAction.setDescription(rtcParam.getDocActionOverView(actionId));
		docAction.setPreCondition(rtcParam.getDocActionPreCondition(actionId));
		docAction.setPostCondition(rtcParam.getDocActionPostCondition(actionId));
		if( (docAction.getDescription()!=null && !docAction.getDescription().equals("")) || 
			 (docAction.getPreCondition()!=null && !docAction.getPreCondition().equals("")) ||
			 (docAction.getPostCondition()!=null && !docAction.getPostCondition().equals("")) ) {
			status.setDoc(docAction);
		}
		//
		status.setImplemented(Boolean.valueOf(rtcParam.getActionImplemented(actionId)).booleanValue());
		//
		return status;
	}
}
