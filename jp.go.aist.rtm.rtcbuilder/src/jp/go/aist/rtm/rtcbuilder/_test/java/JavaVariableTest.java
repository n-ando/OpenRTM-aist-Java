package jp.go.aist.rtm.rtcbuilder._test.java;

import java.util.ArrayList;
import java.util.List;

import jp.go.aist.rtm.rtcbuilder.Generator;
import jp.go.aist.rtm.rtcbuilder.IRtcBuilderConstantsJava;
import jp.go.aist.rtm.rtcbuilder._test.TestBase;
import jp.go.aist.rtm.rtcbuilder.generator.GeneratedResult;
import jp.go.aist.rtm.rtcbuilder.generator.param.DataPortParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.GeneratorParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.RtcParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.ServicePortInterfaceParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.ServicePortParam;

public class JavaVariableTest extends TestBase {

	protected void setUp() throws Exception {
	}

	public void testServicePort2() throws Exception{
		GeneratorParam genParam = new GeneratorParam();
		RtcParam rtcParam = new RtcParam(genParam, true);
		rtcParam.setOutputProject(rootPath + "\\resource\\work");
		rtcParam.setLanguage(IRtcBuilderConstantsJava.LANG_JAVA);
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
		ServicePortInterfaceParam int1 = new ServicePortInterfaceParam(service1, "acc", "acinst", "acvaria", 
				rootPath + "\\resource\\MyService.idl", "MyService", "", 0);
		srvinterts.add(int1);
		service1.setServicePortInterfaces(srvinterts);
		List<ServicePortParam> srvports = new ArrayList<ServicePortParam>();
		srvports.add(service1);
		
		ServicePortParam service2 = new ServicePortParam("cmPort",0);
		List<ServicePortInterfaceParam> srvinterts2 = new ArrayList<ServicePortInterfaceParam>(); 
		ServicePortInterfaceParam int2 = new ServicePortInterfaceParam(service2, "rate", "rinst", "rvaria", 
				rootPath + "\\resource\\DAQService.idl", "DAQService", "", 1);
		srvinterts2.add(int2);
		service2.setServicePortInterfaces(srvinterts2);
		srvports.add(service2);
		
		rtcParam.setServicePorts(srvports);

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.generateTemplateCode(genParam);

		String resourceDir = rootPath +  "\\resource\\Java\\Variable\\ServicePort2\\";

		checkCode(result, resourceDir, "fooComp.java");
		checkCode(result, resourceDir, "build_foo.xml");
		checkCode(result, resourceDir, "foo.java");
		checkCode(result, resourceDir, "fooImpl.java");
		checkCode(result, resourceDir, "MyServiceSVC_impl.java");
		checkCode(result, resourceDir, "README.foo");
	}

	public void testServicePort1() throws Exception{
		GeneratorParam genParam = new GeneratorParam();
		RtcParam rtcParam = new RtcParam(genParam, true);
		rtcParam.setOutputProject(rootPath + "\\resource\\work");
		rtcParam.setLanguage(IRtcBuilderConstantsJava.LANG_JAVA);
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
		ServicePortInterfaceParam int1 = new ServicePortInterfaceParam(service1, "acc", "acinst", "", 
				rootPath + "\\resource\\MyService.idl", "MyService", "", 0);
		srvinterts.add(int1);
		service1.setServicePortInterfaces(srvinterts);
		List<ServicePortParam> srvports = new ArrayList<ServicePortParam>();
		srvports.add(service1);
		
		ServicePortParam service2 = new ServicePortParam("cmPort",0);
		List<ServicePortInterfaceParam> srvinterts2 = new ArrayList<ServicePortInterfaceParam>(); 
		ServicePortInterfaceParam int2 = new ServicePortInterfaceParam(service2, "rate", "rinst", "", 
				rootPath + "\\resource\\DAQService.idl", "DAQService", "", 1);
		srvinterts2.add(int2);
		service2.setServicePortInterfaces(srvinterts2);
		srvports.add(service2);
		
		rtcParam.setServicePorts(srvports);

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.generateTemplateCode(genParam);

		String resourceDir = rootPath +  "\\resource\\Java\\Variable\\ServicePort1\\";

		checkCode(result, resourceDir, "fooComp.java");
		checkCode(result, resourceDir, "build_foo.xml");
		checkCode(result, resourceDir, "foo.java");
		checkCode(result, resourceDir, "fooImpl.java");
		checkCode(result, resourceDir, "MyServiceSVC_impl.java");
		checkCode(result, resourceDir, "README.foo");
	}

	public void testDataPort() throws Exception{
		GeneratorParam genParam = new GeneratorParam();
		RtcParam rtcParam = new RtcParam(genParam, true);
		rtcParam.setOutputProject(rootPath + "\\resource\\work");
		rtcParam.setLanguage(IRtcBuilderConstantsJava.LANG_JAVA);
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
		dataport.add(new DataPortParam("InP1", "TimedShort", "VarInP1", 0));
		dataport.add(new DataPortParam("InP2", "TimedLong", "", 0));
		rtcParam.setInports(dataport);
		List<DataPortParam> outport = new ArrayList<DataPortParam>(); 
		outport.add(new DataPortParam("OutP1", "TimedLong", "", 0));
		outport.add(new DataPortParam("OutP2", "TimedFloat", "VarOutP2", 0));
		rtcParam.setOutports(outport);
		
		Generator generator = new Generator();
		List<GeneratedResult> result = generator.generateTemplateCode(genParam);

		String resourceDir = rootPath +  "\\resource\\Java\\Variable\\DataPort\\";

		checkCode(result, resourceDir, "fooComp.java");
		checkCode(result, resourceDir, "build_foo.xml");
		checkCode(result, resourceDir, "foo.java");
		checkCode(result, resourceDir, "fooImpl.java");
		checkCode(result, resourceDir, "README.foo");
	}
}
