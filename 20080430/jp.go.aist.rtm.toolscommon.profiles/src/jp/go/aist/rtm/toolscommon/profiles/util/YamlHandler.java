package jp.go.aist.rtm.toolscommon.profiles.util;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.XMLGregorianCalendar;

import org.ho.yaml.Yaml;
import org.openrtp.namespaces.RtcProfileHolder;
import org.openrtp.namespaces.rtc.ActionStatusDoc;
import org.openrtp.namespaces.rtc.Actions;
import org.openrtp.namespaces.rtc.BasicInfoExt;
import org.openrtp.namespaces.rtc.ConfigurationDoc;
import org.openrtp.namespaces.rtc.ConfigurationSet;
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

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

public class YamlHandler {

	final static private String containClassName = ": !";
	final static private String prefixClassName = "- !";

	public String convertToYamlRtc(RtcProfile profile) throws Exception {
		RtcProfileHolder holder = new RtcProfileHolder();
		holder.setRtcProfile(profile);

		String yamlText = Yaml.dump(holder, true);
		String[] yamlSplt = removeClassInfo(yamlText);
		StringBuffer buffer = new StringBuffer();
		String lineSeparator = System.getProperty( "line.separator" );
		if( lineSeparator==null || lineSeparator.equals("") ) lineSeparator = "\n";
		for(int intIdx=0;intIdx<yamlSplt.length;intIdx++) {
			buffer.append(yamlSplt[intIdx] + lineSeparator);
		}
		return buffer.toString();
	}
	
	private String[] removeClassInfo(String strInput) {
		String lineSeparator = System.getProperty( "line.separator" );
		if( lineSeparator==null || lineSeparator.equals("") ) lineSeparator = "\n";
		String splitStr[] = strInput.split(lineSeparator);
	
		String strWork = "";
		for( int intidx=0;intidx<splitStr.length;intidx++ ) {
			strWork = splitStr[intidx];
			if(strWork.trim().contains(containClassName)) {
				int end = strWork.indexOf(containClassName); 
				splitStr[intidx] = strWork.substring(0,end+1);
			}
			if(strWork.trim().startsWith(prefixClassName)) {
				int end = strWork.indexOf(prefixClassName); 
				splitStr[intidx] = strWork.substring(0,end+1);
			}
		}
		return splitStr;
	}

	public RtcProfile restoreFromYamlRtc(InputStream input) {
		Map profileYOrg  = (Map)Yaml.load(input);
		return mapToRtc(profileYOrg);
	}

	public RtcProfile restoreFromYamlRtc(String targetYaml) {
		Map profileYOrg  = (Map)Yaml.load(targetYaml);
		return mapToRtc(profileYOrg);
	}

	private RtcProfile mapToRtc(Map profileYOrg) {
		boolean isActionAdded = false;
		RtcProfile profile = null;
		ObjectFactory factory = new ObjectFactory();
		if( profileYOrg != null ) {
			Map profileY  = (Map)profileYOrg.get("rtcProfile");
			if( profileY != null ) {
				profile = factory.createRtcProfile();
				profile.setId((String)profileY.get("id"));
				profile.setVersion((String)profileY.get("version"));
				//Šî–{
				Map basicY = (Map)profileY.get("basicInfo");
				if( basicY != null ) {
					BasicInfoExt basic = factory.createBasicInfoExt();
					basic.setAbstract((String)basicY.get("abstract"));
					basic.setActivityType((String)basicY.get("activityType"));
					basic.setCategory((String)basicY.get("category"));
					basic.setComponentKind((String)basicY.get("componentKind"));
					basic.setComponentType((String)basicY.get("componentType"));
					basic.setCreationDate(createDateTimeFromYaml((Map)basicY.get("creationDate")));
					basic.setDescription((String)basicY.get("description"));
					basic.setExecutionRate((Double)basicY.get("executionRate"));
					basic.setExecutionType((String)basicY.get("executionType"));
					if(basicY.get("maxInstances")!=null) basic.setMaxInstances(BigInteger.valueOf((Integer)basicY.get("maxInstances")));
					basic.setName((String)basicY.get("name"));
					basic.setUpdateDate(createDateTimeFromYaml((Map)basicY.get("updateDate")));
					basic.setVendor((String)basicY.get("vendor"));
					basic.setVersion((String)basicY.get("version"));
					//Basic Doc
					Map docbasicY = (Map)basicY.get("doc");
					if( docbasicY != null ) {
						DocBasic docbasic = factory.createDocBasic();
						docbasic.setDescription((String)docbasicY.get("description"));
						docbasic.setInout((String)docbasicY.get("inout"));
						docbasic.setAlgorithm((String)docbasicY.get("algorithm"));
						docbasic.setCreator((String)docbasicY.get("creator"));
						docbasic.setLicense((String)docbasicY.get("license"));
						docbasic.setReference((String)docbasicY.get("reference"));
						basic.setDoc(docbasic);
					}
					//Basic ext
					if( basicY.get("versionUpLog")!=null )basic.getVersionUpLog().addAll((List)basicY.get("versionUpLog"));
					profile.setBasicInfo(basic);
				}
				//Actions
				Map actionsY = (Map)profileY.get("actions");
				if( actionsY != null ) {
					Actions actions = factory.createActions();
					Map eachActionY = (Map)actionsY.get("onInitialize");
					if( eachActionY != null ) {
						ActionStatusDoc oninit = factory.createActionStatusDoc();
						actions.setOnInitialize(createActionFromYaml(eachActionY));
						isActionAdded = true;
					}
					//
					eachActionY = (Map)actionsY.get("onFinalize");
					if( eachActionY != null ) {
						actions.setOnFinalize(createActionFromYaml(eachActionY));
						isActionAdded = true;
					}
					//
					eachActionY = (Map)actionsY.get("onStartup");
					if( eachActionY != null ) {
						actions.setOnStartup(createActionFromYaml(eachActionY));
						isActionAdded = true;
					}
					//
					eachActionY = (Map)actionsY.get("onShutdown");
					if( eachActionY != null ) {
						actions.setOnShutdown(createActionFromYaml(eachActionY));
						isActionAdded = true;
					}
					//
					eachActionY = (Map)actionsY.get("onActivated");
					if( eachActionY != null ) {
						actions.setOnActivated(createActionFromYaml(eachActionY));
						isActionAdded = true;
					}
					//
					eachActionY = (Map)actionsY.get("onDeactivated");
					if( eachActionY != null ) {
						actions.setOnDeactivated(createActionFromYaml(eachActionY));
						isActionAdded = true;
					}
					//
					eachActionY = (Map)actionsY.get("onExecute");
					if( eachActionY != null ) {
						actions.setOnExecute(createActionFromYaml(eachActionY));
						isActionAdded = true;
					}
					//
					eachActionY = (Map)actionsY.get("onAborting");
					if( eachActionY != null ) {
						actions.setOnAborting(createActionFromYaml(eachActionY));
						isActionAdded = true;
					}
					//
					eachActionY = (Map)actionsY.get("onError");
					if( eachActionY != null ) {
						actions.setOnError(createActionFromYaml(eachActionY));
						isActionAdded = true;
					}
					//
					eachActionY = (Map)actionsY.get("onReset");
					if( eachActionY != null ) {
						actions.setOnReset(createActionFromYaml(eachActionY));
						isActionAdded = true;
					}
					//
					eachActionY = (Map)actionsY.get("onStateUpdate");
					if( eachActionY != null ) {
						actions.setOnStateUpdate(createActionFromYaml(eachActionY));
						isActionAdded = true;
					}
					//
					eachActionY = (Map)actionsY.get("onRateChanged");
					if( eachActionY != null ) {
						actions.setOnRateChanged(createActionFromYaml(eachActionY));
						isActionAdded = true;
					}
					if( isActionAdded ) profile.setActions(actions);
				}
			
				//Data Ports
				List dataPortListY = (List)profileY.get("dataPorts");
				if( dataPortListY != null ) {
					for(int intIdx=0;intIdx<dataPortListY.size();intIdx++ ) {
						Map dataPortInfoY = (Map)dataPortListY.get(intIdx);
						if( dataPortInfoY != null ) {
							DataportExt dataport = createDataPortFromYaml(dataPortInfoY);
							profile.getDataPorts().add(dataport);
						}
					}
				}
				//Service Ports
				List servicePortListY = (List)profileY.get("servicePorts");
				if( servicePortListY != null ) {
					for(int intIdx=0;intIdx<servicePortListY.size();intIdx++ ) {
						Map servicePortY = (Map)servicePortListY.get(intIdx);
						if( servicePortY != null ) {
							ServiceportExt serviceport = createServicePortFromYaml(servicePortY);
							profile.getServicePorts().add(serviceport);
						}
					}
				}
				//Configuration
				Map configSetInfoY = (Map)profileY.get("configurationSet");
				if( configSetInfoY != null ) {
					List configsInfoY = (List)configSetInfoY.get("configuration");
					if( configsInfoY != null ) {
						ConfigurationSet configset = factory.createConfigurationSet();
						for(int intIdx=0;intIdx<configsInfoY.size();intIdx++ ) {
							Map configInfoY = (Map)configsInfoY.get(intIdx);
							if( configInfoY != null ) {
								ConfigurationDoc config = factory.createConfigurationDoc();
								config.setDefaultValue((String)configInfoY.get("defaultValue"));
								config.setName((String)configInfoY.get("name"));
								config.setType((String)configInfoY.get("type"));
								config.setVarname((String)configInfoY.get("varname"));
								configset.getConfiguration().add(config);
								//
								Map configDocY = (Map)configInfoY.get("doc");
								if( configDocY != null ) {
									DocConfiguration docconfig = factory.createDocConfiguration();
									docconfig.setConstraint((String)configDocY.get("constraint"));
									docconfig.setDataname((String)configDocY.get("dataname"));
									docconfig.setDefaultValue((String)configDocY.get("defaultValue"));
									docconfig.setDescription((String)configDocY.get("description"));
									docconfig.setRange((String)configDocY.get("range"));
									docconfig.setUnit((String)configDocY.get("unit"));
									
									config.setDoc(docconfig);
								}
							}
						}
						profile.setConfigurationSet(configset);
					}
				}
					
				//Parameter
				List paramsInfoY = (List)profileY.get("parameters");
				if( paramsInfoY != null ) {
					for(int intIdx=0;intIdx<paramsInfoY.size();intIdx++ ) {
						Map paramInfoY = (Map)paramsInfoY.get(intIdx);
						if( paramInfoY != null ) {
							Parameter param = factory.createParameter();
							param.setName((String)paramInfoY.get("name"));
							param.setDefaultValue((String)paramInfoY.get("defaultValue"));
							profile.getParameters().add(param);
						}
					}
				}
				//Language
				Map langInfoY = (Map)profileY.get("language");
				if( langInfoY != null ) {
					Language language = factory.createLanguage();
					Map cxxInfoY = (Map)langInfoY.get("cxx");
					if( cxxInfoY != null ) {
						Cxxlang cxxlang = factory.createCxxlang();
						cxxlang.setOs((String)cxxInfoY.get("os"));
						cxxlang.setArch((String)cxxInfoY.get("arch"));
						List libsInfoY = (List)cxxInfoY.get("library");
						if( libsInfoY != null ) {
							cxxlang.getLibrary().addAll(libsInfoY);
						}
						language.setCxx(cxxlang);
					}
					Map javaInfoY = (Map)langInfoY.get("java");
					if( javaInfoY != null ) {
						Javalang javaLang = factory.createJavalang();
						List libsInfoY = (List)javaInfoY.get("library");
						if( libsInfoY != null ) {
							javaLang.getLibrary().addAll(libsInfoY);
						}
						language.setJava(javaLang);
					}
					if( langInfoY.get("Python") != null ) {
						language.setPython("Python");
					}
					if( langInfoY.get("Csharp") != null ) {
						language.setCsharp("Csharp");
					}
					if( langInfoY.get("Ruby") != null ) {
						language.setRuby("Ruby");
					}
					profile.setLanguage(language);
				}
			}
		}
		return profile;
	}

	private XMLGregorianCalendar createDateTimeFromYaml(Map dateY) {
		return XMLGregorianCalendarImpl.createDateTime(
				((Integer)dateY.get("year")).intValue(),
				((Integer)dateY.get("month")).intValue(),
				((Integer)dateY.get("day")).intValue(),
				((Integer)dateY.get("hour")).intValue(),
				((Integer)dateY.get("minute")).intValue(),
				((Integer)dateY.get("second")).intValue() );
	}
	
	private ActionStatusDoc createActionFromYaml(Map actionY) {
		ObjectFactory factory = new ObjectFactory();
		ActionStatusDoc actionStatus = factory.createActionStatusDoc();
		Boolean impl = (Boolean)actionY.get("implemented");
		if( impl!=null) {
			actionStatus.setImplemented(impl.booleanValue());
		} else {
			actionStatus.setImplemented(false);
		}

		Map docActionY = (Map)actionY.get("doc");
		if( docActionY != null ) {
			DocAction docAction = factory.createDocAction();
			docAction.setDescription((String)docActionY.get("description"));
			docAction.setPreCondition((String)docActionY.get("preCondition"));
			docAction.setPostCondition((String)docActionY.get("postCondition"));
			actionStatus.setDoc(docAction);
		}
		return actionStatus;
	}

	private DataportExt createDataPortFromYaml(Map yamlMap) {
		ObjectFactory factory = new ObjectFactory();
		DataportExt dataport = factory.createDataportExt();
		dataport.setDataflowType((String)yamlMap.get("dataflowType"));
		dataport.setIdlFile((String)yamlMap.get("idlFile"));
		dataport.setInterfaceType((String)yamlMap.get("interfaceType"));
		dataport.setName((String)yamlMap.get("name"));
		dataport.setPortType((String)yamlMap.get("portType"));
		dataport.setPosition(Position.fromValue(((String)yamlMap.get("position")).toLowerCase()));
		dataport.setSubscriprionType((String)yamlMap.get("subscriprionType"));
		dataport.setType((String)yamlMap.get("type"));
		dataport.setVarname((String)yamlMap.get("varname"));
		//
		Map portdocY = (Map)yamlMap.get("doc");
		if( portdocY != null ) {
			DocDataport docdataport = factory.createDocDataport();
			docdataport.setDescription((String)portdocY.get("description"));
			docdataport.setNumber((String)portdocY.get("number"));
			docdataport.setOccerrence((String)portdocY.get("occerrence"));
			docdataport.setOperation((String)portdocY.get("operation"));
			docdataport.setSemantics((String)portdocY.get("semantics"));
			docdataport.setType((String)portdocY.get("type"));
			docdataport.setUnit((String)portdocY.get("unit"));
			dataport.setDoc(docdataport);
		}
		//
		return dataport;
	}
	
	private ServiceportExt createServicePortFromYaml(Map yamlMap) {
		ObjectFactory factory = new ObjectFactory();
		ServiceportExt serviceport = factory.createServiceportExt();
		serviceport.setName((String)yamlMap.get("name"));
		serviceport.setPosition(Position.fromValue(((String)yamlMap.get("position")).toLowerCase()));
		//
		Map portdocY = (Map)yamlMap.get("doc");
		if( portdocY != null ) {
			DocServiceport docserviceport = factory.createDocServiceport();
			docserviceport.setDescription((String)portdocY.get("description"));
			docserviceport.setIfdescription((String)portdocY.get("ifdescription"));
			serviceport.setDoc(docserviceport);
		}
		//Service Interface
		List interfacesY = (List)yamlMap.get("serviceInterface");
		if( interfacesY != null ) {
			for(int intIdx=0;intIdx<interfacesY.size();intIdx++ ) {
				Map interfaceY = (Map)interfacesY.get(intIdx);
				ServiceinterfaceDoc serviceIF = factory.createServiceinterfaceDoc();
				serviceIF.setDirection((String)interfaceY.get("direction"));
				serviceIF.setIdlFile((String)interfaceY.get("idlFile"));
				serviceIF.setInstanceName((String)interfaceY.get("instanceName"));
				serviceIF.setName((String)interfaceY.get("name"));
				serviceIF.setPath((String)interfaceY.get("path"));
				serviceIF.setType((String)interfaceY.get("type"));
				serviceIF.setVarname((String)interfaceY.get("varname"));
				//
				Map ifDocY = (Map)interfaceY.get("doc");
				if( ifDocY != null ) {
					DocServiceinterface docserviceIF = factory.createDocServiceinterface();
					docserviceIF.setDescription((String)ifDocY.get("description"));
					docserviceIF.setDocArgument((String)ifDocY.get("docArgument"));
					docserviceIF.setDocException((String)ifDocY.get("docException"));
					docserviceIF.setDocPostCondition((String)ifDocY.get("docPostCondition"));
					docserviceIF.setDocPreCondition((String)ifDocY.get("docPreCondition"));
					docserviceIF.setDocReturn((String)ifDocY.get("docReturn"));
					serviceIF.setDoc(docserviceIF);
				}
				serviceport.getServiceInterface().add(serviceIF);
			}
		}
		return serviceport;
	}
}
