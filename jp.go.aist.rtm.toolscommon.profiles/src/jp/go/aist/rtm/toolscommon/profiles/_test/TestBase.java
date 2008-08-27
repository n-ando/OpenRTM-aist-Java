package jp.go.aist.rtm.toolscommon.profiles._test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import junit.framework.TestCase;

import org.openrtp.namespaces.rtc.ActionStatusDoc;
import org.openrtp.namespaces.rtc.Actions;
import org.openrtp.namespaces.rtc.BasicInfoExt;
import org.openrtp.namespaces.rtc.ConfigurationDoc;
import org.openrtp.namespaces.rtc.ConfigurationSet;
import org.openrtp.namespaces.rtc.DataportExt;
import org.openrtp.namespaces.rtc.DocAction;
import org.openrtp.namespaces.rtc.DocBasic;
import org.openrtp.namespaces.rtc.DocConfiguration;
import org.openrtp.namespaces.rtc.DocDataport;
import org.openrtp.namespaces.rtc.DocServiceinterface;
import org.openrtp.namespaces.rtc.DocServiceport;
import org.openrtp.namespaces.rtc.Javalang;
import org.openrtp.namespaces.rtc.Language;
import org.openrtp.namespaces.rtc.Parameter;
import org.openrtp.namespaces.rtc.Position;
import org.openrtp.namespaces.rtc.RtcProfile;
import org.openrtp.namespaces.rtc.ServiceinterfaceDoc;
import org.openrtp.namespaces.rtc.ServiceportExt;

public class TestBase extends TestCase {
	protected String rootPath;

	public TestBase () {
		File fileCurrent = new File(".");
		rootPath = fileCurrent.getAbsolutePath();
		rootPath = rootPath.substring(0,rootPath.length()-1);
	}
	protected String readFile(String fileName, String splitter) {
		StringBuffer stbRet = new StringBuffer();
		try{
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
	
			String str = new String();
			while( (str = br.readLine()) != null ){
				stbRet.append(str + splitter);
			}
			br.close();
			fr.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		return stbRet.toString();
	}
	
	protected void checkDetail(RtcProfile profile){
		
		assertEquals("RTC:SampleVendor.SampleCategory.SampleComponent:1.0.0",profile.getId());
		assertEquals("1.0", profile.getVersion());
		//
		BasicInfoExt basic = (BasicInfoExt)profile.getBasicInfo();
		assertEquals("SampleComponent", basic.getName());
		assertEquals("STATIC", basic.getComponentType());
		assertEquals("PERIODIC", basic.getActivityType());
		assertEquals("DataFlowComponent", basic.getComponentKind());
		assertEquals("SampleCategory", basic.getCategory());
		assertEquals("SampleDescription", basic.getDescription());
		assertEquals(1000.0, basic.getExecutionRate());
		assertEquals("PeriodicExecutionContext", basic.getExecutionType());
		assertEquals((long)1, basic.getMaxInstances().longValue());
		assertEquals("SampleVendor", basic.getVendor());
		assertEquals("1.0.0", basic.getVersion());
		assertEquals("SampleAbstract", basic.getAbstract());
		assertEquals("2008-04-18T14:00:00", basic.getCreationDate().toString());
		assertEquals("2008-04-17T14:00:00", basic.getUpdateDate().toString());
		//
		DocBasic docbasic = basic.getDoc();
		assertEquals("SampleBasicDecription", docbasic.getDescription());
		assertEquals("SampleBasicInout", docbasic.getInout());
		assertEquals("SampleAlgorithm", docbasic.getAlgorithm());
		assertEquals("SampleCreator", docbasic.getCreator());
		assertEquals("SampleLicense", docbasic.getLicense());
		assertEquals("SampleReference", docbasic.getReference());
		//
		assertEquals("2008/04/18 14:00:00:Ver1.0", basic.getVersionUpLog().get(0));
		assertEquals("2008/04/18 17:00:00:Ver1.1", basic.getVersionUpLog().get(1));
		//
		Actions actions = profile.getActions();
		ActionStatusDoc oninit = (ActionStatusDoc)actions.getOnInitialize();
		assertEquals(true, oninit.isImplemented());
		DocAction initdoc = oninit.getDoc();
		assertEquals("on_initialize description", initdoc.getDescription());
		assertEquals("on_initialize Pre_condition", initdoc.getPreCondition());
		assertEquals("on_initialize Post_condition", initdoc.getPostCondition());
		//
		ActionStatusDoc onfinal = (ActionStatusDoc)actions.getOnFinalize();
		assertEquals(false, onfinal.isImplemented());
		DocAction finaldoc = onfinal.getDoc();
		assertEquals("on_finalize description", finaldoc.getDescription());
		assertEquals("on_finalize Pre_condition", finaldoc.getPreCondition());
		assertEquals("on_finalize Post_condition", finaldoc.getPostCondition());
		//
		ActionStatusDoc onstart = (ActionStatusDoc)actions.getOnStartup();
		assertEquals(false, onstart.isImplemented());
		DocAction startdoc = onstart.getDoc();
		assertEquals("on_startup description", startdoc.getDescription());
		assertEquals("on_startup Pre_condition", startdoc.getPreCondition());
		assertEquals("on_startup Post_condition", startdoc.getPostCondition());
		//
		ActionStatusDoc onshut = (ActionStatusDoc)actions.getOnShutdown();
		assertEquals(true, onshut.isImplemented());
		DocAction shutdoc = onshut.getDoc();
		assertEquals("on_shutdown description", shutdoc.getDescription());
		assertEquals("on_shutdown Pre_condition", shutdoc.getPreCondition());
		assertEquals("on_shutdown Post_condition", shutdoc.getPostCondition());
		//
		ActionStatusDoc onact = (ActionStatusDoc)actions.getOnActivated();
		assertEquals(true, onact.isImplemented());
		DocAction actdoc = onact.getDoc();
		assertEquals("on_activated description", actdoc.getDescription());
		assertEquals("on_activated Pre_condition", actdoc.getPreCondition());
		assertEquals("on_activated Post_condition", actdoc.getPostCondition());
		//
		ActionStatusDoc ondeact = (ActionStatusDoc)actions.getOnDeactivated();
		assertEquals(false, ondeact.isImplemented());
		DocAction deactdoc = ondeact.getDoc();
		assertEquals("on_deactivated description", deactdoc.getDescription());
		assertEquals("on_deactivated Pre_condition", deactdoc.getPreCondition());
		assertEquals("on_deactivated Post_condition", deactdoc.getPostCondition());
		//
		ActionStatusDoc onabort = (ActionStatusDoc)actions.getOnAborting();
		assertEquals(true, onabort.isImplemented());
		DocAction abortdoc = onabort.getDoc();
		assertEquals("on_aborting description", abortdoc.getDescription());
		assertEquals("on_aborting Pre_condition", abortdoc.getPreCondition());
		assertEquals("on_aborting Post_condition", abortdoc.getPostCondition());
		//
		ActionStatusDoc onerrort = (ActionStatusDoc)actions.getOnError();
		assertEquals(false, onerrort.isImplemented());
		DocAction errordoc = onerrort.getDoc();
		assertEquals("on_error description", errordoc.getDescription());
		assertEquals("on_error Pre_condition", errordoc.getPreCondition());
		assertEquals("on_error Post_condition", errordoc.getPostCondition());
		//
		ActionStatusDoc onreset = (ActionStatusDoc)actions.getOnReset();
		assertEquals(false, onerrort.isImplemented());
		DocAction resetdoc = onreset.getDoc();
		assertEquals("on_reset description", resetdoc.getDescription());
		assertEquals("on_reset Pre_condition", resetdoc.getPreCondition());
		assertEquals("on_reset Post_condition", resetdoc.getPostCondition());
		//
		ActionStatusDoc onexec = (ActionStatusDoc)actions.getOnExecute();
		assertEquals(false, onerrort.isImplemented());
		DocAction execdoc = onexec.getDoc();
		assertEquals("on_execute description", execdoc.getDescription());
		assertEquals("on_execute Pre_condition", execdoc.getPreCondition());
		assertEquals("on_execute Post_condition", execdoc.getPostCondition());
		//
		ActionStatusDoc onstate = (ActionStatusDoc)actions.getOnStateUpdate();
		assertEquals(false, onstate.isImplemented());
		DocAction statedoc = onstate.getDoc();
		assertEquals("on_state_update description", statedoc.getDescription());
		assertEquals("on_state_update Pre_condition", statedoc.getPreCondition());
		assertEquals("on_state_update Post_condition", statedoc.getPostCondition());
		//
		ActionStatusDoc onrate = (ActionStatusDoc)actions.getOnRateChanged();
		assertEquals(false, onrate.isImplemented());
		DocAction rateedoc = onrate.getDoc();
		assertEquals("on_rate_changed description", rateedoc.getDescription());
		assertEquals("on_rate_changed Pre_condition", rateedoc.getPreCondition());
		assertEquals("on_rate_changed Post_condition", rateedoc.getPostCondition());
		//
		ConfigurationSet configset = profile.getConfigurationSet();
		ConfigurationDoc config = (ConfigurationDoc)configset.getConfiguration().get(0);
		assertEquals("config1", config.getName());
		assertEquals("int", config.getType());
		assertEquals("var1", config.getVarname());
		assertEquals("1", config.getDefaultValue());
		DocConfiguration docconfig = config.getDoc(); 
		assertEquals("dataname1", docconfig.getDataname());
		assertEquals("default1", docconfig.getDefaultValue());
		assertEquals("config_Desc1", docconfig.getDescription());
		assertEquals("config_unit1", docconfig.getUnit());
		assertEquals("config_range1", docconfig.getRange());
		assertEquals("config_constraint1", docconfig.getConstraint());
		//
		ConfigurationDoc config2 = (ConfigurationDoc)configset.getConfiguration().get(1);
		assertEquals("config2", config2.getName());
		assertEquals("String", config2.getType());
		assertEquals("var2", config2.getVarname());
		assertEquals("Sample", config2.getDefaultValue());
		//
		DataportExt dataport1 = (DataportExt)profile.getDataPorts().get(0);
		assertEquals("DataInPort", dataport1.getPortType());
		assertEquals("inport1", dataport1.getName());
		assertEquals("RTC::TimedLong", dataport1.getType());
		assertEquals("In1Var", dataport1.getVarname());
		assertEquals(Position.LEFT.toString(), dataport1.getPosition().toString());
		assertEquals("DataPort1.idl", dataport1.getIdlFile());
		assertEquals("CorbaPort", dataport1.getInterfaceType());
		assertEquals("Push,Pull", dataport1.getDataflowType());
		assertEquals("Periodic,New,Flush", dataport1.getSubscriprionType());
		//
		DocDataport docdatp1 = dataport1.getDoc();
		assertEquals("In1Description", docdatp1.getDescription());
		assertEquals("In1Type", docdatp1.getType());
		assertEquals("In1Number", docdatp1.getNumber());
		assertEquals("In1Semantics", docdatp1.getSemantics());
		assertEquals("In1Unit", docdatp1.getUnit());
		assertEquals("In1Occerrence", docdatp1.getOccerrence());
		assertEquals("In1Operation", docdatp1.getOperation());
		//
		DataportExt dataport2 = (DataportExt)profile.getDataPorts().get(1);
		assertEquals("DataInPort", dataport2.getPortType());
		assertEquals("inport2", dataport2.getName());
		assertEquals("RTC::TimedDouble", dataport2.getType());
		assertEquals("In2Var", dataport2.getVarname());
		assertEquals(Position.LEFT.toString(), dataport2.getPosition().toString());
		assertEquals("CorbaPort", dataport2.getInterfaceType());
		assertEquals("Push,Pull", dataport2.getDataflowType());
		assertEquals("New,Periodic", dataport2.getSubscriprionType());
		//
		DataportExt dataport3 = (DataportExt)profile.getDataPorts().get(2);
		assertEquals("DataOutPort", dataport3.getPortType());
		assertEquals("outport1", dataport3.getName());
		assertEquals("RTC::TimedLong", dataport3.getType());
		assertEquals("Out1Var", dataport3.getVarname());
		assertEquals(Position.RIGHT.toString(), dataport3.getPosition().toString());
		assertEquals("CorbaPort", dataport3.getInterfaceType());
		assertEquals("Push", dataport3.getDataflowType());
		assertEquals("New,Periodic", dataport3.getSubscriprionType());
		//
		DocDataport docdatp3 = dataport3.getDoc();
		assertEquals("Out1Description", docdatp3.getDescription());
		assertEquals("Out1Type", docdatp3.getType());
		assertEquals("Out1Number", docdatp3.getNumber());
		assertEquals("Out1Semantics", docdatp3.getSemantics());
		assertEquals("Out1Unit", docdatp3.getUnit());
		assertEquals("Out1Occerrence", docdatp3.getOccerrence());
		assertEquals("Out1Operation", docdatp3.getOperation());
		//
		DataportExt dataport4 = (DataportExt)profile.getDataPorts().get(3);
		assertEquals("DataOutPort", dataport4.getPortType());
		assertEquals("outport2", dataport4.getName());
		assertEquals("RTC::TimedDouble", dataport4.getType());
		assertEquals("Out2Var", dataport4.getVarname());
		assertEquals(Position.RIGHT.toString().toString(), dataport4.getPosition().toString());
		assertEquals("CorbaPort", dataport4.getInterfaceType());
		assertEquals("Push,Pull", dataport4.getDataflowType());
		assertEquals("New,Periodic", dataport4.getSubscriprionType());
		//
		ServiceportExt service1 = (ServiceportExt)profile.getServicePorts().get(0);
		assertEquals("SrvPort1", service1.getName());
		assertEquals(Position.LEFT.toString().toString(), service1.getPosition().toString());
		DocServiceport serviceDoc1 = service1.getDoc();
		assertEquals("ServicePort1 description", serviceDoc1.getDescription());
		assertEquals("ServicePort1 I/F description", serviceDoc1.getIfdescription());
		//
		ServiceinterfaceDoc serviceIF1 = (ServiceinterfaceDoc)service1.getServiceInterface().get(0);
		assertEquals("S1IF1", serviceIF1.getName());
		assertEquals("Provided", serviceIF1.getDirection());
		assertEquals("IF1Instance", serviceIF1.getInstanceName());
		assertEquals("IF1VarName", serviceIF1.getVarname());
		assertEquals("IF1Idlfile.idl", serviceIF1.getIdlFile());
		assertEquals("IF1Type", serviceIF1.getType());
		assertEquals("IF1SearchPath", serviceIF1.getPath());
		//
		DocServiceinterface docIf1 = serviceIF1.getDoc();
		assertEquals("if1 Description", docIf1.getDescription());
		assertEquals("if1 Argument", docIf1.getDocArgument());
		assertEquals("if1 Return", docIf1.getDocReturn());
		assertEquals("if1 Exception", docIf1.getDocException());
		assertEquals("if1 PreCond", docIf1.getDocPreCondition());
		assertEquals("if1 PostCond", docIf1.getDocPostCondition());
		//
		ServiceinterfaceDoc serviceIF2 = (ServiceinterfaceDoc)service1.getServiceInterface().get(1);
		assertEquals("S1IF2", serviceIF2.getName());
		assertEquals("Required", serviceIF2.getDirection());
		assertEquals("IF2Instance", serviceIF2.getInstanceName());
		assertEquals("IF2VarName", serviceIF2.getVarname());
		assertEquals("IF2Idlfile.idl", serviceIF2.getIdlFile());
		assertEquals("IF2Type", serviceIF2.getType());
		assertEquals("IF2SearchPath", serviceIF2.getPath());
		//
		ServiceportExt service2 = (ServiceportExt)profile.getServicePorts().get(1);
		assertEquals("SrvPort2", service2.getName());
		assertEquals(Position.RIGHT.toString().toString(), service2.getPosition().toString());
		DocServiceport serviceDoc2 = service2.getDoc();
		assertEquals("ServicePort2 description", serviceDoc2.getDescription());
		assertEquals("ServicePort2 I/F description", serviceDoc2.getIfdescription());
		//
		Parameter param1 = (Parameter)profile.getParameters().get(0);
		assertEquals("param1", param1.getName());
		assertEquals("param_def1", param1.getDefaultValue());
		//
		Parameter param2 = (Parameter)profile.getParameters().get(1);
		assertEquals("param2", param2.getName());
		assertEquals("param_def2", param2.getDefaultValue());
		//
		Language lang = profile.getLanguage();
		Javalang java = lang.getJava();
		assertEquals("library1", java.getLibrary().get(0));
	}
}
