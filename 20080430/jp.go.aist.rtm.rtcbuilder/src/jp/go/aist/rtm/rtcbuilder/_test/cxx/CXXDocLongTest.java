package jp.go.aist.rtm.rtcbuilder._test.cxx;

import java.util.ArrayList;
import java.util.List;

import jp.go.aist.rtm.rtcbuilder.Generator;
import jp.go.aist.rtm.rtcbuilder.IRtcBuilderConstants;
import jp.go.aist.rtm.rtcbuilder._test.TestBase;
import jp.go.aist.rtm.rtcbuilder.generator.GeneratedResult;
import jp.go.aist.rtm.rtcbuilder.generator.param.ConfigSetParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.DataPortParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.GeneratorParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.RtcParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.ServicePortInterfaceParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.ServicePortParam;

public class CXXDocLongTest extends TestBase {

	protected void setUp() throws Exception {
	}

	public void testDocAllLong() throws Exception{
		GeneratorParam genParam = new GeneratorParam();
		RtcParam rtcParam = new RtcParam(genParam, true);
		rtcParam.setOutputProject(rootPath + "\\resource\\work");
		rtcParam.setLanguage(IRtcBuilderConstants.LANG_CPP);
		rtcParam.setName("foo");
		rtcParam.setDescription("MDesc");
		rtcParam.setVersion("1.0.1");
		rtcParam.setVender("TA");
		rtcParam.setCategory("Manip");
		rtcParam.setComponentType("STATIC2");
		rtcParam.setActivityType("PERIODIC2");
		rtcParam.setMaxInstance(5);
		//
		rtcParam.setDocCreator("Noriaki Ando <n-ando@aist.go.jp>34567894123456789512345678961234567897123456789812345");
		rtcParam.setDocLicense("Copyright (C) 2006-2008 ライセンス12345678901234567890123456789012345678901234567890");
		rtcParam.setDocDescription("本コンポーネントの概要説明1234567890123456789012345678901234567890123456789012345678901234567890");
		rtcParam.setDocInOut("本コンポーネントの入出力1234567890123456789012345678901234567890123456789012345678901234567890");
		rtcParam.setDocAlgorithm("本コンポーネントのアルゴリズムなど1234567890123456789012345678901234567890123456789012345678901234567890");
		rtcParam.setDocReference("参考文献の情報1234567890123456789012345678901234567890123456789012345678901234567890");
		//
		rtcParam.setDocActionOverView(IRtcBuilderConstants.ACTIVITY_INITIALIZE, "on_initialize概要説明1234567890123456789012345678901234567890123456789012345678901234567890");
		rtcParam.setDocActionPreCondition(IRtcBuilderConstants.ACTIVITY_INITIALIZE, "on_initialize事前条件1234567890123456789012345678901234567890123456789012345678901234567890");
		rtcParam.setDocActionPostCondition(IRtcBuilderConstants.ACTIVITY_INITIALIZE, "on_initialize事後条件1234567890123456789012345678901234567890123456789012345678901234567890");
		//
		rtcParam.setDocActionOverView(IRtcBuilderConstants.ACTIVITY_FINALIZE, "on_finalize概要説明1234567890123456789012345678901234567890123456789012345678901234567890");
		rtcParam.setDocActionPreCondition(IRtcBuilderConstants.ACTIVITY_FINALIZE, "on_finalize事前条件1234567890123456789012345678901234567890123456789012345678901234567890");
		rtcParam.setDocActionPostCondition(IRtcBuilderConstants.ACTIVITY_FINALIZE, "on_finalize事後条件1234567890123456789012345678901234567890123456789012345678901234567890");
		//
		rtcParam.setDocActionOverView(IRtcBuilderConstants.ACTIVITY_STARTUP, "on_startup概要説明1234567890123456789012345678901234567890123456789012345678901234567890");
		rtcParam.setDocActionPreCondition(IRtcBuilderConstants.ACTIVITY_STARTUP, "on_startup事前条件1234567890123456789012345678901234567890123456789012345678901234567890");
		rtcParam.setDocActionPostCondition(IRtcBuilderConstants.ACTIVITY_STARTUP, "on_startup事後条件1234567890123456789012345678901234567890123456789012345678901234567890");
		//
		rtcParam.setDocActionOverView(IRtcBuilderConstants.ACTIVITY_SHUTDOWN, "on_shutdown概要説明1234567890123456789012345678901234567890123456789012345678901234567890");
		rtcParam.setDocActionPreCondition(IRtcBuilderConstants.ACTIVITY_SHUTDOWN, "on_shutdown事前条件1234567890123456789012345678901234567890123456789012345678901234567890");
		rtcParam.setDocActionPostCondition(IRtcBuilderConstants.ACTIVITY_SHUTDOWN, "on_shutdown事後条件1234567890123456789012345678901234567890123456789012345678901234567890");
		//
		rtcParam.setDocActionOverView(IRtcBuilderConstants.ACTIVITY_ACTIVATED, "on_activated概要説明1234567890123456789012345678901234567890123456789012345678901234567890");
		rtcParam.setDocActionPreCondition(IRtcBuilderConstants.ACTIVITY_ACTIVATED, "on_activated事前条件1234567890123456789012345678901234567890123456789012345678901234567890");
		rtcParam.setDocActionPostCondition(IRtcBuilderConstants.ACTIVITY_ACTIVATED, "on_activated事後条件1234567890123456789012345678901234567890123456789012345678901234567890");
		//
		rtcParam.setDocActionOverView(IRtcBuilderConstants.ACTIVITY_DEACTIVATED, "on_deactivated概要説明1234567890123456789012345678901234567890123456789012345678901234567890");
		rtcParam.setDocActionPreCondition(IRtcBuilderConstants.ACTIVITY_DEACTIVATED, "on_deactivated事前条件1234567890123456789012345678901234567890123456789012345678901234567890");
		rtcParam.setDocActionPostCondition(IRtcBuilderConstants.ACTIVITY_DEACTIVATED, "on_deactivated事後条件1234567890123456789012345678901234567890123456789012345678901234567890");
		//
		rtcParam.setDocActionOverView(IRtcBuilderConstants.ACTIVITY_EXECUTE, "on_execute概要説明1234567890123456789012345678901234567890123456789012345678901234567890");
		rtcParam.setDocActionPreCondition(IRtcBuilderConstants.ACTIVITY_EXECUTE, "on_execute事前条件1234567890123456789012345678901234567890123456789012345678901234567890");
		rtcParam.setDocActionPostCondition(IRtcBuilderConstants.ACTIVITY_EXECUTE, "on_execute事後条件1234567890123456789012345678901234567890123456789012345678901234567890");
		//
		rtcParam.setDocActionOverView(IRtcBuilderConstants.ACTIVITY_ABORTING, "on_aborting概要説明1234567890123456789012345678901234567890123456789012345678901234567890");
		rtcParam.setDocActionPreCondition(IRtcBuilderConstants.ACTIVITY_ABORTING, "on_aborting事前条件1234567890123456789012345678901234567890123456789012345678901234567890");
		rtcParam.setDocActionPostCondition(IRtcBuilderConstants.ACTIVITY_ABORTING, "on_aborting事後条件1234567890123456789012345678901234567890123456789012345678901234567890");
		//
		rtcParam.setDocActionOverView(IRtcBuilderConstants.ACTIVITY_ERROR, "on_error概要説明1234567890123456789012345678901234567890123456789012345678901234567890");
		rtcParam.setDocActionPreCondition(IRtcBuilderConstants.ACTIVITY_ERROR, "on_error事前条件1234567890123456789012345678901234567890123456789012345678901234567890");
		rtcParam.setDocActionPostCondition(IRtcBuilderConstants.ACTIVITY_ERROR, "on_error事後条件1234567890123456789012345678901234567890123456789012345678901234567890");
		//
		rtcParam.setDocActionOverView(IRtcBuilderConstants.ACTIVITY_RESET, "on_reset概要説明1234567890123456789012345678901234567890123456789012345678901234567890");
		rtcParam.setDocActionPreCondition(IRtcBuilderConstants.ACTIVITY_RESET, "on_reset事前条件1234567890123456789012345678901234567890123456789012345678901234567890");
		rtcParam.setDocActionPostCondition(IRtcBuilderConstants.ACTIVITY_RESET, "on_reset事後条件1234567890123456789012345678901234567890123456789012345678901234567890");
		//
		rtcParam.setDocActionOverView(IRtcBuilderConstants.ACTIVITY_STATE_UPDATE, "on_state_update概要説明1234567890123456789012345678901234567890123456789012345678901234567890");
		rtcParam.setDocActionPreCondition(IRtcBuilderConstants.ACTIVITY_STATE_UPDATE, "on_state_update事前条件1234567890123456789012345678901234567890123456789012345678901234567890");
		rtcParam.setDocActionPostCondition(IRtcBuilderConstants.ACTIVITY_STATE_UPDATE, "on_state_update事後条件1234567890123456789012345678901234567890123456789012345678901234567890");
		//
		rtcParam.setDocActionOverView(IRtcBuilderConstants.ACTIVITY_RATE_CHANGED, "on_rate_changed概要説明1234567890123456789012345678901234567890123456789012345678901234567890");
		rtcParam.setDocActionPreCondition(IRtcBuilderConstants.ACTIVITY_RATE_CHANGED, "on_rate_changed事前条件1234567890123456789012345678901234567890123456789012345678901234567890");
		rtcParam.setDocActionPostCondition(IRtcBuilderConstants.ACTIVITY_RATE_CHANGED, "on_rate_changed事後条件1234567890123456789012345678901234567890123456789012345678901234567890");
		//
		genParam.getRtcParams().add(rtcParam);
		List<DataPortParam> dataport = new ArrayList<DataPortParam>(); 

		DataPortParam datap1 = new DataPortParam("InP1", "TimedShort", "InName1", 0);
		datap1.setDocDescription("InPort1の概要1234567890123456789012345678901234567890123456789012345678901234567890");
		datap1.setDocType("InPort1のデータの型1234567890123456789012345678901234567890123456789012345678901234567890");
		datap1.setDocNum("InPort1のデータの数1234567890123456789012345678901234567890123456789012345678901234567890");
		datap1.setDocSemantics("InPort1のデータの意味1234567890123456789012345678901234567890123456789012345678901234567890");
		datap1.setDocUnit("InPort1のデータの単位1234567890123456789012345678901234567890123456789012345678901234567890");
		datap1.setDocOccurrence("InPort1のデータの発生頻度1234567890123456789012345678901234567890123456789012345678901234567890");
		datap1.setDocOperation("InPort1のデータの処理周期1234567890123456789012345678901234567890123456789012345678901234567890");
		dataport.add(datap1);

		DataPortParam datap2 = new DataPortParam("InP2", "TimedLong", "InNm2", 0);
		datap2.setDocDescription("InPort2の概要1234567890123456789012345678901234567890123456789012345678901234567890");
		datap2.setDocType("InPort2のデータの型1234567890123456789012345678901234567890123456789012345678901234567890");
		datap2.setDocNum("InPort2のデータの数1234567890123456789012345678901234567890123456789012345678901234567890");
		datap2.setDocSemantics("InPort2のデータの意味1234567890123456789012345678901234567890123456789012345678901234567890");
		datap2.setDocUnit("InPort2のデータの単位1234567890123456789012345678901234567890123456789012345678901234567890");
		datap2.setDocOccurrence("InPort2のデータの発生頻度1234567890123456789012345678901234567890123456789012345678901234567890");
		datap2.setDocOperation("InPort2のデータの処理周期1234567890123456789012345678901234567890123456789012345678901234567890");
		dataport.add(datap2);
		
		rtcParam.setInports(dataport);
		//
		List<DataPortParam> outport = new ArrayList<DataPortParam>(); 

		DataPortParam datap3 = new DataPortParam("OutP1", "TimedLong", "OutName1", 0);
		datap3.setDocDescription("OutPort1の概要1234567890123456789012345678901234567890123456789012345678901234567890");
		datap3.setDocType("OutPort1のデータの型1234567890123456789012345678901234567890123456789012345678901234567890");
		datap3.setDocNum("OutPort1のデータの数1234567890123456789012345678901234567890123456789012345678901234567890");
		datap3.setDocSemantics("OutPort1のデータの意味1234567890123456789012345678901234567890123456789012345678901234567890");
		datap3.setDocUnit("OutPort1のデータの単位1234567890123456789012345678901234567890123456789012345678901234567890");
		datap3.setDocOccurrence("OutPort1のデータの発生頻度1234567890123456789012345678901234567890123456789012345678901234567890");
		datap3.setDocOperation("OutPort1のデータの処理周期1234567890123456789012345678901234567890123456789012345678901234567890");
		outport.add(datap3);

		DataPortParam datap4 = new DataPortParam("OutP2", "TimedFloat", "OutNme2", 0);
		datap4.setDocDescription("OutPort2の概要1234567890123456789012345678901234567890123456789012345678901234567890");
		datap4.setDocType("OutPort2のデータの型1234567890123456789012345678901234567890123456789012345678901234567890");
		datap4.setDocNum("OutPort2のデータの数1234567890123456789012345678901234567890123456789012345678901234567890");
		datap4.setDocSemantics("OutPort2のデータの意味1234567890123456789012345678901234567890123456789012345678901234567890");
		datap4.setDocUnit("OutPort2のデータの単位1234567890123456789012345678901234567890123456789012345678901234567890");
		datap4.setDocOccurrence("OutPort2のデータの発生頻度1234567890123456789012345678901234567890123456789012345678901234567890");
		datap4.setDocOperation("OutPort2のデータの処理周期1234567890123456789012345678901234567890123456789012345678901234567890");
		outport.add(datap4);

		rtcParam.setOutports(outport);

		ServicePortParam service1 = new ServicePortParam("svPort",0);
		service1.setDocDescription("ServicePort1の概要1234567890123456789012345678901234567890123456789012345678901234567890");
		service1.setDocIfDescription("ServicePort1のインターフェースの概要1234567890123456789012345678901234567890123456789012345678901234567890");
		List<ServicePortInterfaceParam> srvinterts = new ArrayList<ServicePortInterfaceParam>(); 
		ServicePortInterfaceParam int1 = new ServicePortInterfaceParam(service1, "acc", "", "", 
				rootPath + "\\resource\\MyService.idl", "MyService", "", 0);
		int1.setDocDescription("ServiceIF1の概要説明1234567890123456789012345678901234567890123456789012345678901234567890");
		int1.setDocArgument("ServiceIF1の引数1234567890123456789012345678901234567890123456789012345678901234567890");
		int1.setDocReturn("ServiceIF1の返値1234567890123456789012345678901234567890123456789012345678901234567890");
		int1.setDocException("ServiceIF1の例外1234567890123456789012345678901234567890123456789012345678901234567890");
		int1.setDocPreCondition("ServiceIF1の事前条件1234567890123456789012345678901234567890123456789012345678901234567890");
		int1.setDocPostCondition("ServiceIF1の事後条件1234567890123456789012345678901234567890123456789012345678901234567890");
		srvinterts.add(int1);
		service1.setServicePortInterfaces(srvinterts);
		List<ServicePortParam> srvports = new ArrayList<ServicePortParam>();
		srvports.add(service1);
		
		ServicePortParam service2 = new ServicePortParam("cmPort",0);
		service2.setDocDescription("ServicePort2の概要1234567890123456789012345678901234567890123456789012345678901234567890");
		service2.setDocIfDescription("ServicePort2のインターフェースの概要1234567890123456789012345678901234567890123456789012345678901234567890");
		List<ServicePortInterfaceParam> srvinterts2 = new ArrayList<ServicePortInterfaceParam>(); 
		ServicePortInterfaceParam int2 = new ServicePortInterfaceParam(service2, "rate", "", "", 
				rootPath + "\\resource\\DAQService.idl", "DAQService", "", 1);
		int2.setDocDescription("ServiceIF2の概要説明1234567890123456789012345678901234567890123456789012345678901234567890");
		int2.setDocArgument("ServiceIF2の引数1234567890123456789012345678901234567890123456789012345678901234567890");
		int2.setDocReturn("ServiceIF2の返値1234567890123456789012345678901234567890123456789012345678901234567890");
		int2.setDocException("ServiceIF2の例外1234567890123456789012345678901234567890123456789012345678901234567890");
		int2.setDocPreCondition("ServiceIF2の事前条件1234567890123456789012345678901234567890123456789012345678901234567890");
		int2.setDocPostCondition("ServiceIF2の事後条件1234567890123456789012345678901234567890123456789012345678901234567890");
		srvinterts2.add(int2);
		service2.setServicePortInterfaces(srvinterts2);
		srvports.add(service2);
		
		List<ConfigSetParam> configset = new ArrayList<ConfigSetParam>(); 
		ConfigSetParam config1 = new ConfigSetParam("int_param0","int","", "0");
		config1.setDocDataName("Config1の名前");
		config1.setDocDescription("Config1の概要1234567890123456789012345678901234567890123456789012345678901234567890");
		config1.setDocDefaultVal("Config1のデフォルト値");
		config1.setDocUnit("Config1の単位1234567890123456789012345678901234567890123456789012345678901234567890");
		config1.setDocRange("Config1の範囲1234567890123456789012345678901234567890123456789012345678901234567890");
		config1.setDocConstraint("Config1の制約条件1234567890123456789012345678901234567890123456789012345678901234567890");
		configset.add(config1);
		ConfigSetParam config2 = new ConfigSetParam("int_param1","int","", "1");
		config2.setDocDataName("Config2の名前");
		config2.setDocDescription("Config2の概要1234567890123456789012345678901234567890123456789012345678901234567890");
		config2.setDocDefaultVal("Config2のデフォルト値");
		config2.setDocUnit("Config2の単位1234567890123456789012345678901234567890123456789012345678901234567890");
		config2.setDocRange("Config2の範囲1234567890123456789012345678901234567890123456789012345678901234567890");
		config2.setDocConstraint("Config2の制約条件1234567890123456789012345678901234567890123456789012345678901234567890");
		configset.add(config2);
		ConfigSetParam config3 = new ConfigSetParam("double_param0","double","", "0.11");
		config3.setDocDataName("Config3の名前");
		config3.setDocDescription("Config3の概要1234567890123456789012345678901234567890123456789012345678901234567890");
		config3.setDocDefaultVal("Config3のデフォルト値");
		config3.setDocUnit("Config3の単位1234567890123456789012345678901234567890123456789012345678901234567890");
		config3.setDocRange("Config3の範囲1234567890123456789012345678901234567890123456789012345678901234567890");
		config3.setDocConstraint("Config3の制約条件1234567890123456789012345678901234567890123456789012345678901234567890");
		configset.add(config3);
		ConfigSetParam config4 = new ConfigSetParam("str_param0","std::string","", "hoge");
		config4.setDocDataName("Config4の名前");
		config4.setDocDescription("Config4の概要1234567890123456789012345678901234567890123456789012345678901234567890");
		config4.setDocDefaultVal("Config4のデフォルト値");
		config4.setDocUnit("Config4の単位1234567890123456789012345678901234567890123456789012345678901234567890");
		config4.setDocRange("Config4の範囲1234567890123456789012345678901234567890123456789012345678901234567890");
		config4.setDocConstraint("Config4の制約条件1234567890123456789012345678901234567890123456789012345678901234567890");
		configset.add(config4);
		ConfigSetParam config5 = new ConfigSetParam("str_param1","std::string","", "dara");
		config5.setDocDataName("Config5の名前");
		config5.setDocDescription("Config5の概要1234567890123456789012345678901234567890123456789012345678901234567890");
		config5.setDocDefaultVal("Config5のデフォルト値");
		config5.setDocUnit("Config5の単位1234567890123456789012345678901234567890123456789012345678901234567890");
		config5.setDocRange("Config5の範囲1234567890123456789012345678901234567890123456789012345678901234567890");
		config5.setDocConstraint("Config5の制約条件1234567890123456789012345678901234567890123456789012345678901234567890");
		configset.add(config5);
		rtcParam.setConfigParams(configset);

		rtcParam.setServicePorts(srvports);

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.generateTemplateCode(genParam);

		String resourceDir = rootPath +  "\\resource\\CXX\\Doc\\fullLong\\";

		checkCode(result, resourceDir, "fooComp.cpp");
		checkCode(result, resourceDir, "Makefile.foo");
		checkCode(result, resourceDir, "foo.h");
		checkCode(result, resourceDir, "foo.cpp");
		checkCode(result, resourceDir, "MyServiceSVC_impl.h");
		checkCode(result, resourceDir, "MyServiceSVC_impl.cpp");
		checkCode(result, resourceDir, "README.foo");
	}

}
