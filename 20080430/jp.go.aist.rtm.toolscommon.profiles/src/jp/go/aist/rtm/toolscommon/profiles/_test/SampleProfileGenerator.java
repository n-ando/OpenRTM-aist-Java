package jp.go.aist.rtm.toolscommon.profiles._test;

import java.math.BigInteger;

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
import org.openrtp.namespaces.rtc.ObjectFactory;
import org.openrtp.namespaces.rtc.Parameter;
import org.openrtp.namespaces.rtc.Position;
import org.openrtp.namespaces.rtc.RtcProfile;
import org.openrtp.namespaces.rtc.ServiceinterfaceDoc;
import org.openrtp.namespaces.rtc.ServiceportExt;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

public class SampleProfileGenerator {

	public RtcProfile generateProfile() {
		
		ObjectFactory factory = new ObjectFactory();
		RtcProfile profile =  factory.createRtcProfile();
		profile.setId("RTC:SampleVendor.SampleCategory.SampleComponent:1.0.0");
		profile.setVersion("1.0");
		//
		BasicInfoExt basic = factory.createBasicInfoExt();
		basic.setName("SampleComponent");
		basic.setComponentType("STATIC");
		basic.setActivityType("PERIODIC");
		basic.setComponentKind("DataFlowComponent");
		basic.setCategory("SampleCategory");
		basic.setDescription("SampleDescription");
		basic.setExecutionRate(1000.0);
		basic.setExecutionType("PeriodicExecutionContext");
		basic.setMaxInstances(BigInteger.valueOf(1));
		basic.setVendor("SampleVendor");
		basic.setVersion("1.0.0");
		basic.setAbstract("SampleAbstract");
		basic.setCreationDate(XMLGregorianCalendarImpl.createDateTime(2008, 4, 18, 14, 0, 0));
//		basic.setUpdateDate(XMLGregorianCalendarImpl.createDateTime(2008, 4, 17, 14, 0, 0));
		basic.setUpdateDate(XMLGregorianCalendarImpl.parse("2008-04-17T14:00:00"));
		profile.setBasicInfo(basic);
		//
		DocBasic docbasic = factory.createDocBasic();
		docbasic.setDescription("SampleBasicDecription");
		docbasic.setInout("SampleBasicInout");
		docbasic.setAlgorithm("SampleAlgorithm");
		docbasic.setCreator("SampleCreator");
		docbasic.setLicense("SampleLicense");
		docbasic.setReference("SampleReference");
		basic.setDoc(docbasic);
		//
		basic.getVersionUpLog().add("2008/04/18 14:00:00:Ver1.0");
		basic.getVersionUpLog().add("2008/04/18 17:00:00:Ver1.1");
		//
		Actions actions = factory.createActions();
		ActionStatusDoc oninit = factory.createActionStatusDoc();
		oninit.setImplemented(true);
		DocAction initdoc = factory.createDocAction();
		initdoc.setDescription("on_initialize description");
		initdoc.setPreCondition("on_initialize Pre_condition");
		initdoc.setPostCondition("on_initialize Post_condition");
		oninit.setDoc(initdoc);
		actions.setOnInitialize(oninit);
		//
		ActionStatusDoc onfinal = factory.createActionStatusDoc();
		onfinal.setImplemented(false);
		DocAction finaldoc = factory.createDocAction();
		finaldoc.setDescription("on_finalize description");
		finaldoc.setPreCondition("on_finalize Pre_condition");
		finaldoc.setPostCondition("on_finalize Post_condition");
		onfinal.setDoc(finaldoc);
		actions.setOnFinalize(onfinal);
		//
		ActionStatusDoc onstart = factory.createActionStatusDoc();
		onstart.setImplemented(false);
		DocAction startdoc = factory.createDocAction();
		startdoc.setDescription("on_startup description");
		startdoc.setPreCondition("on_startup Pre_condition");
		startdoc.setPostCondition("on_startup Post_condition");
		onstart.setDoc(startdoc);
		actions.setOnStartup(onstart);
		//
		ActionStatusDoc onshut = factory.createActionStatusDoc();
		onshut.setImplemented(true);
		DocAction shutdoc = factory.createDocAction();
		shutdoc.setDescription("on_shutdown description");
		shutdoc.setPreCondition("on_shutdown Pre_condition");
		shutdoc.setPostCondition("on_shutdown Post_condition");
		onshut.setDoc(shutdoc);
		actions.setOnShutdown(onshut);
		//
		ActionStatusDoc onact = factory.createActionStatusDoc();
		onact.setImplemented(true);
		DocAction actdoc = factory.createDocAction();
		actdoc.setDescription("on_activated description");
		actdoc.setPreCondition("on_activated Pre_condition");
		actdoc.setPostCondition("on_activated Post_condition");
		onact.setDoc(actdoc);
		actions.setOnActivated(onact);
		//
		ActionStatusDoc ondeact = factory.createActionStatusDoc();
		ondeact.setImplemented(false);
		DocAction deactdoc = factory.createDocAction();
		deactdoc.setDescription("on_deactivated description");
		deactdoc.setPreCondition("on_deactivated Pre_condition");
		deactdoc.setPostCondition("on_deactivated Post_condition");
		ondeact.setDoc(deactdoc);
		actions.setOnDeactivated(ondeact);
		//
		ActionStatusDoc onabort = factory.createActionStatusDoc();
		onabort.setImplemented(true);
		DocAction abortdoc = factory.createDocAction();
		abortdoc.setDescription("on_aborting description");
		abortdoc.setPreCondition("on_aborting Pre_condition");
		abortdoc.setPostCondition("on_aborting Post_condition");
		onabort.setDoc(abortdoc);
		actions.setOnAborting(onabort);
		//
		ActionStatusDoc onerrort = factory.createActionStatusDoc();
		onerrort.setImplemented(false);
		DocAction errordoc = factory.createDocAction();
		errordoc.setDescription("on_error description");
		errordoc.setPreCondition("on_error Pre_condition");
		errordoc.setPostCondition("on_error Post_condition");
		onerrort.setDoc(errordoc);
		actions.setOnError(onerrort);
		//
		ActionStatusDoc onreset = factory.createActionStatusDoc();
		onreset.setImplemented(false);
		DocAction resetdoc = factory.createDocAction();
		resetdoc.setDescription("on_reset description");
		resetdoc.setPreCondition("on_reset Pre_condition");
		resetdoc.setPostCondition("on_reset Post_condition");
		onreset.setDoc(resetdoc);
		actions.setOnReset(onreset);
		//
		ActionStatusDoc onexec = factory.createActionStatusDoc();
		onexec.setImplemented(false);
		DocAction execdoc = factory.createDocAction();
		execdoc.setDescription("on_execute description");
		execdoc.setPreCondition("on_execute Pre_condition");
		execdoc.setPostCondition("on_execute Post_condition");
		onexec.setDoc(execdoc);
		actions.setOnExecute(onexec);
		//
		ActionStatusDoc onstate = factory.createActionStatusDoc();
		onstate.setImplemented(false);
		DocAction statedoc = factory.createDocAction();
		statedoc.setDescription("on_state_update description");
		statedoc.setPreCondition("on_state_update Pre_condition");
		statedoc.setPostCondition("on_state_update Post_condition");
		onstate.setDoc(statedoc);
		actions.setOnStateUpdate(onstate);
		//
		ActionStatusDoc onrate = factory.createActionStatusDoc();
		onrate.setImplemented(false);
		DocAction rateedoc = factory.createDocAction();
		rateedoc.setDescription("on_rate_changed description");
		rateedoc.setPreCondition("on_rate_changed Pre_condition");
		rateedoc.setPostCondition("on_rate_changed Post_condition");
		onrate.setDoc(rateedoc);
		actions.setOnRateChanged(onrate);
		//
		profile.setActions(actions);
		//
		ConfigurationSet configset = factory.createConfigurationSet();
		ConfigurationDoc config = factory.createConfigurationDoc();
		config.setName("config1");
		config.setType("int");
		config.setVarname("var1");
		config.setDefaultValue("1");
		DocConfiguration docconfig = factory.createDocConfiguration(); 
		docconfig.setDataname("dataname1");
		docconfig.setDefaultValue("default1");
		docconfig.setDescription("config_Desc1");
		docconfig.setUnit("config_unit1");
		docconfig.setRange("config_range1");
		docconfig.setConstraint("config_constraint1");
		config.setDoc(docconfig);
		configset.getConfiguration().add(config);
		//
		ConfigurationDoc config2 = factory.createConfigurationDoc();
		config2.setName("config2");
		config2.setType("String");
		config2.setVarname("var2");
		config2.setDefaultValue("Sample");
		configset.getConfiguration().add(config2);
		//
		profile.setConfigurationSet(configset);
		//
		DataportExt dataport1 = factory.createDataportExt();
		dataport1.setPortType("DataInPort");
		dataport1.setName("inport1");
		dataport1.setType("RTC::TimedLong");
		dataport1.setVarname("In1Var");
		dataport1.setPosition(Position.LEFT);
		dataport1.setIdlFile("DataPort1.idl");
		dataport1.setInterfaceType("CorbaPort");
		dataport1.setDataflowType("Push,Pull");
		dataport1.setSubscriprionType("Periodic,New,Flush");
		
		DocDataport docdatp1 = factory.createDocDataport();
		docdatp1.setDescription("In1Description");
		docdatp1.setType("In1Type");
		docdatp1.setNumber("In1Number");
		docdatp1.setSemantics("In1Semantics");
		docdatp1.setUnit("In1Unit");
		docdatp1.setOccerrence("In1Occerrence");
		docdatp1.setOperation("In1Operation");
		dataport1.setDoc(docdatp1);
		profile.getDataPorts().add(dataport1);
		//
		DataportExt dataport2 = factory.createDataportExt();
		dataport2.setPortType("DataInPort");
		dataport2.setName("inport2");
		dataport2.setType("RTC::TimedDouble");
		dataport2.setVarname("In2Var");
		dataport2.setPosition(Position.LEFT);
		dataport2.setInterfaceType("CorbaPort");
		dataport2.setDataflowType("Push,Pull");
		dataport2.setSubscriprionType("New,Periodic");
		profile.getDataPorts().add(dataport2);
		//
		DataportExt dataport3 = factory.createDataportExt();
		dataport3.setPortType("DataOutPort");
		dataport3.setName("outport1");
		dataport3.setType("RTC::TimedLong");
		dataport3.setVarname("Out1Var");
		dataport3.setPosition(Position.RIGHT);
		dataport3.setInterfaceType("CorbaPort");
		dataport3.setDataflowType("Push");
		dataport3.setSubscriprionType("New,Periodic");

		DocDataport docdatp3 = factory.createDocDataport();
		docdatp3.setDescription("Out1Description");
		docdatp3.setType("Out1Type");
		docdatp3.setNumber("Out1Number");
		docdatp3.setSemantics("Out1Semantics");
		docdatp3.setUnit("Out1Unit");
		docdatp3.setOccerrence("Out1Occerrence");
		docdatp3.setOperation("Out1Operation");
		dataport3.setDoc(docdatp3);
		profile.getDataPorts().add(dataport3);
		//
		DataportExt dataport4 = factory.createDataportExt();
		dataport4.setPortType("DataOutPort");
		dataport4.setName("outport2");
		dataport4.setType("RTC::TimedDouble");
		dataport4.setVarname("Out2Var");
		dataport4.setPosition(Position.RIGHT);
		dataport4.setInterfaceType("CorbaPort");
		dataport4.setDataflowType("Push,Pull");
		dataport4.setSubscriprionType("New,Periodic");
		profile.getDataPorts().add(dataport4);
		//
		ServiceportExt service1 = factory.createServiceportExt();
		service1.setName("SrvPort1");
		service1.setPosition(Position.LEFT);
		DocServiceport serviceDoc1 = factory.createDocServiceport();
		serviceDoc1.setDescription("ServicePort1 description");
		serviceDoc1.setIfdescription("ServicePort1 I/F description");
		service1.setDoc(serviceDoc1);
		//
		ServiceinterfaceDoc serviceIF1 = factory.createServiceinterfaceDoc();
		serviceIF1.setName("S1IF1");
		serviceIF1.setDirection("Provided");
		serviceIF1.setInstanceName("IF1Instance");
		serviceIF1.setVarname("IF1VarName");
		serviceIF1.setIdlFile("IF1Idlfile.idl");
		serviceIF1.setType("IF1Type");
		serviceIF1.setPath("IF1SearchPath");
		//
		DocServiceinterface docIf1 = factory.createDocServiceinterface();
		docIf1.setDescription("if1 Description");
		docIf1.setDocArgument("if1 Argument");
		docIf1.setDocReturn("if1 Return");
		docIf1.setDocException("if1 Exception");
		docIf1.setDocPreCondition("if1 PreCond");
		docIf1.setDocPostCondition("if1 PostCond");
		serviceIF1.setDoc(docIf1);
		service1.getServiceInterface().add(serviceIF1);
		//
		ServiceinterfaceDoc serviceIF2 = factory.createServiceinterfaceDoc();
		serviceIF2.setName("S1IF2");
		serviceIF2.setDirection("Required");
		serviceIF2.setInstanceName("IF2Instance");
		serviceIF2.setVarname("IF2VarName");
		serviceIF2.setIdlFile("IF2Idlfile.idl");
		serviceIF2.setType("IF2Type");
		serviceIF2.setPath("IF2SearchPath");
		service1.getServiceInterface().add(serviceIF2);
		profile.getServicePorts().add(service1);
		//
		ServiceportExt service2 = factory.createServiceportExt();
		service2.setName("SrvPort2");
		service2.setPosition(Position.RIGHT);
		DocServiceport serviceDoc2 = factory.createDocServiceport();
		serviceDoc2.setDescription("ServicePort2 description");
		serviceDoc2.setIfdescription("ServicePort2 I/F description");
		service2.setDoc(serviceDoc2);
		profile.getServicePorts().add(service2);
		//
		Parameter param1 = factory.createParameter();
		param1.setName("param1");
		param1.setDefaultValue("param_def1");
		profile.getParameters().add(param1);
		//
		Parameter param2 = factory.createParameter();
		param2.setName("param2");
		param2.setDefaultValue("param_def2");
		profile.getParameters().add(param2);
		//
		Javalang java = factory.createJavalang();
		java.getLibrary().add("library1");
		Language lang = factory.createLanguage();
		lang.setJava(java);
		profile.setLanguage(lang);
		
		return profile;
	}
}
