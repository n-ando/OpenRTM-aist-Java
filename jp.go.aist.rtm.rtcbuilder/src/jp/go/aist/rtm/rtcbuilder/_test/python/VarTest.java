package jp.go.aist.rtm.rtcbuilder._test.python;

import java.util.ArrayList;
import java.util.List;

import jp.go.aist.rtm.rtcbuilder.Generator;
import jp.go.aist.rtm.rtcbuilder.IRtcBuilderConstantsPython;
import jp.go.aist.rtm.rtcbuilder._test.TestBase;
import jp.go.aist.rtm.rtcbuilder.generator.GeneratedResult;
import jp.go.aist.rtm.rtcbuilder.generator.param.ConfigSetParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.DataPortParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.GeneratorParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.RtcParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.ServicePortInterfaceParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.ServicePortParam;

public class VarTest extends TestBase {

	protected void setUp() throws Exception {
	}

	public void testConfigSetVar() throws Exception{
		GeneratorParam genParam = new GeneratorParam();
		RtcParam rtcParam = new RtcParam(genParam, true);
		rtcParam.setOutputProject(rootPath + "\\resource\\work");
		rtcParam.setLanguage(IRtcBuilderConstantsPython.LANG_PYTHON);
		rtcParam.setName("foo");
		rtcParam.setDescription("MDesc");
		rtcParam.setVersion("1.0.3");
		rtcParam.setVender("TA2");
		rtcParam.setCategory("manip2");
		rtcParam.setComponentType("STATIC2");
		rtcParam.setActivityType("PERIODIC2");
		rtcParam.setMaxInstance(3);

		genParam.getRtcParams().add(rtcParam);
		List<ConfigSetParam> configset = new ArrayList<ConfigSetParam>(); 
		configset.add(new ConfigSetParam("int_param0","int","varname1","0"));
		configset.add(new ConfigSetParam("int_param1","int","","1"));
		configset.add(new ConfigSetParam("double_param0","double","varname2","0.11"));
		configset.add(new ConfigSetParam("str_param0","String","","hoge"));
		configset.add(new ConfigSetParam("str_param1","String","varname3","dara"));
		rtcParam.setConfigParams(configset);

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.generateTemplateCode(genParam);

		String resourceDir = rootPath +  "\\resource\\Python\\ConfigSet\\ConfigVar\\";

		checkCode(result, resourceDir, "foo.py");
		checkCode(result, resourceDir, "README.foo");
	}

	public void testConfigSetOriginal() throws Exception{
		GeneratorParam genParam = new GeneratorParam();
		RtcParam rtcParam = new RtcParam(genParam, true);
		rtcParam.setOutputProject(rootPath + "\\resource\\work");
		rtcParam.setLanguage(IRtcBuilderConstantsPython.LANG_PYTHON);
		rtcParam.setName("foo");
		rtcParam.setDescription("MDesc");
		rtcParam.setVersion("1.0.3");
		rtcParam.setVender("TA2");
		rtcParam.setCategory("manip2");
		rtcParam.setComponentType("STATIC2");
		rtcParam.setActivityType("PERIODIC2");
		rtcParam.setMaxInstance(3);

		genParam.getRtcParams().add(rtcParam);
		List<ConfigSetParam> configset = new ArrayList<ConfigSetParam>(); 
		configset.add(new ConfigSetParam("int_param0","int","","0"));
		configset.add(new ConfigSetParam("int_param1","int","","1"));
		configset.add(new ConfigSetParam("double_param0","double","","0.11"));
		configset.add(new ConfigSetParam("str_param0","String","","hoge"));
		configset.add(new ConfigSetParam("str_param1","String","","dara"));
		rtcParam.setConfigParams(configset);

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.generateTemplateCode(genParam);

		String resourceDir = rootPath +  "\\resource\\Python\\ConfigSet\\configset3\\";

		checkCode(result, resourceDir, "foo.py");
		checkCode(result, resourceDir, "README.foo");
	}

	public void testDataPortVar() throws Exception{
		GeneratorParam genParam = new GeneratorParam();
		RtcParam rtcParam = new RtcParam(genParam, true);
		rtcParam.setOutputProject(rootPath + "\\resource\\work");
		rtcParam.setLanguage(IRtcBuilderConstantsPython.LANG_PYTHON);
		rtcParam.setName("foo");
		rtcParam.setDescription("MDesc");
		rtcParam.setVersion("1.0.1");
		rtcParam.setVender("TA");
		rtcParam.setCategory("Manip");
		rtcParam.setComponentType("STATIC2");
		rtcParam.setActivityType("PERIODIC2");
		rtcParam.setMaxInstance(5);
		genParam.getRtcParams().add(rtcParam);
		List<DataPortParam> dataport = new ArrayList<DataPortParam>(); 
		dataport.add(new DataPortParam("InP1", "TimedShort", "InName1", 0));
		dataport.add(new DataPortParam("InP2", "TimedLong", "InNm2", 0));
		rtcParam.setInports(dataport);
		List<DataPortParam> outport = new ArrayList<DataPortParam>(); 
		outport.add(new DataPortParam("OutP1", "TimedLong", "OutName1", 0));
		outport.add(new DataPortParam("OutP2", "TimedFloat", "OutNme2", 0));
		rtcParam.setOutports(outport);

		ServicePortParam service1 = new ServicePortParam("svPort",0);
		List<ServicePortInterfaceParam> srvinterts = new ArrayList<ServicePortInterfaceParam>(); 
		ServicePortInterfaceParam int1 = new ServicePortInterfaceParam(service1, "acc", "", "", 
				rootPath + "\\resource\\MyService.idl", "MyService", "", 0);
		srvinterts.add(int1);
		service1.setServicePortInterfaces(srvinterts);
		List<ServicePortParam> srvports = new ArrayList<ServicePortParam>();
		srvports.add(service1);
		
		ServicePortParam service2 = new ServicePortParam("cmPort",0);
		List<ServicePortInterfaceParam> srvinterts2 = new ArrayList<ServicePortInterfaceParam>(); 
		ServicePortInterfaceParam int2 = new ServicePortInterfaceParam(service2, "rate", "", "", 
				rootPath + "\\resource\\DAQService.idl", "DAQService", "", 1);
		srvinterts2.add(int2);
		service2.setServicePortInterfaces(srvinterts2);
		srvports.add(service2);
		
		rtcParam.setServicePorts(srvports);

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.generateTemplateCode(genParam);

		String resourceDir = rootPath +  "\\resource\\Python\\PortVar\\";

		checkCode(result, resourceDir, "foo.py");
		checkCode(result, resourceDir, "MyService_idl.py");
		checkCode(result, resourceDir, "DAQService_idl.py");
		checkCode(result, resourceDir, "\\_GlobalIDL\\__init__.py");
		checkCode(result, resourceDir, "\\_GlobalIDL__POA\\__init__.py");
		checkCode(result, resourceDir, "MyService_idl_example.py");
		checkCode(result, resourceDir, "README.foo");
	}
	
	public void testOriginalPort() throws Exception{
		GeneratorParam genParam = new GeneratorParam();
		RtcParam rtcParam = new RtcParam(genParam, true);
		rtcParam.setOutputProject(rootPath + "\\resource\\work");
		rtcParam.setLanguage(IRtcBuilderConstantsPython.LANG_PYTHON);
		rtcParam.setName("foo");
		rtcParam.setDescription("MDesc");
		rtcParam.setVersion("1.0.1");
		rtcParam.setVender("TA");
		rtcParam.setCategory("Manip");
		rtcParam.setComponentType("STATIC2");
		rtcParam.setActivityType("PERIODIC2");
		rtcParam.setMaxInstance(5);
		genParam.getRtcParams().add(rtcParam);
		List<DataPortParam> dataport = new ArrayList<DataPortParam>(); 
		dataport.add(new DataPortParam("InP1", "TimedShort", "", 0));
		dataport.add(new DataPortParam("InP2", "TimedLong", "", 0));
		rtcParam.setInports(dataport);
		List<DataPortParam> outport = new ArrayList<DataPortParam>(); 
		outport.add(new DataPortParam("OutP1", "TimedLong", "", 0));
		outport.add(new DataPortParam("OutP2", "TimedFloat", "", 0));
		rtcParam.setOutports(outport);

		ServicePortParam service1 = new ServicePortParam("svPort",0);
		List<ServicePortInterfaceParam> srvinterts = new ArrayList<ServicePortInterfaceParam>(); 
		ServicePortInterfaceParam int1 = new ServicePortInterfaceParam(service1, "acc", "", "", 
				rootPath + "\\resource\\MyService.idl", "MyService", "", 0);
		srvinterts.add(int1);
		service1.setServicePortInterfaces(srvinterts);
		List<ServicePortParam> srvports = new ArrayList<ServicePortParam>();
		srvports.add(service1);
		
		ServicePortParam service2 = new ServicePortParam("cmPort",0);
		List<ServicePortInterfaceParam> srvinterts2 = new ArrayList<ServicePortInterfaceParam>(); 
		ServicePortInterfaceParam int2 = new ServicePortInterfaceParam(service2, "rate", "", "", 
				rootPath + "\\resource\\DAQService.idl", "DAQService", "", 1);
		srvinterts2.add(int2);
		service2.setServicePortInterfaces(srvinterts2);
		srvports.add(service2);
		
		rtcParam.setServicePorts(srvports);

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.generateTemplateCode(genParam);

		String resourceDir = rootPath +  "\\resource\\Python\\service2\\";

		checkCode(result, resourceDir, "foo.py");
		checkCode(result, resourceDir, "MyService_idl.py");
		checkCode(result, resourceDir, "DAQService_idl.py");
		checkCode(result, resourceDir, "\\_GlobalIDL\\__init__.py");
		checkCode(result, resourceDir, "\\_GlobalIDL__POA\\__init__.py");
		checkCode(result, resourceDir, "MyService_idl_example.py");
		checkCode(result, resourceDir, "README.foo");
	}
}
