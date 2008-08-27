package jp.go.aist.rtm.rtctemplate._test._042.cxx;

import java.util.ArrayList;
import java.util.List;

import jp.go.aist.rtm.rtctemplate.Generator;
import jp.go.aist.rtm.rtctemplate._test.TestBase;
import jp.go.aist.rtm.rtctemplate.generator.GeneratedResult;
import jp.go.aist.rtm.rtctemplate.generator.param.DataPortParam;
import jp.go.aist.rtm.rtctemplate.generator.param.GeneratorParam;
import jp.go.aist.rtm.rtctemplate.generator.param.RtcParam;
import jp.go.aist.rtm.rtctemplate.generator.param.ServiceReferenceParam;

public class CXXTemplateTest extends TestBase {

	protected void setUp() throws Exception {
	}

//	public void testOperation() throws Exception{
//		String resourceDir = "CXX\\operation";
//		GeneratorParam genParam = new GeneratorParam();
//		genParam.setOutputDirectory(rootPath + "\\resource\\work");
//		RtcParam rtcParam = new RtcParam(genParam);
//		rtcParam.setLanguage(rtcParam.LANG_CPP);
//		rtcParam.setName("foo");
//		rtcParam.setDescription("MDesc");
//		rtcParam.setVersion("1.0.1");
//		rtcParam.setVender("TA");
//		rtcParam.setCategory("Manip");
//		rtcParam.setComponentType("STATIC2");
//		rtcParam.setActivityType("PERIODIC2");
//		rtcParam.setMaxInstance(5);
//
//		List<String> privateAtt = new ArrayList<String>(); 
//		privateAtt.add(new String("int private1"));
//		privateAtt.add(new String("static int attribute_4"));
//		rtcParam.setPrivateAttributes(privateAtt);
//
//		List<String> protectedAtt = new ArrayList<String>(); 
//		protectedAtt.add(new String("String protectval"));
//		protectedAtt.add(new String("static float attribute_4"));
//		rtcParam.setProtectedAttributes(protectedAtt);
//
//		List<String> publicAtt = new ArrayList<String>(); 
//		publicAtt.add(new String("boolean pubbol1"));
//		publicAtt.add(new String("double attribute_5"));
//		rtcParam.setPublicAttributes(publicAtt);
//
//		List<String> publicOpe = new ArrayList<String>(); 
//		publicOpe.add(new String("int operation_2(int param_1 = 10)"));
//		publicOpe.add(new String("String operation_1(String param_1, int param_2)"));
//		rtcParam.setPublicOperations(publicOpe);
//
//		genParam.getRtcParams().add(rtcParam);
//		
//		Generator generator = new Generator();
//		List<GeneratedResult> result = generator.doGenerate(genParam);
//
//		String targetDir = rootPath + "\\resource\\CXX\\operation\\";
//		checkCode(result, targetDir, "fooComp.cpp");
//		checkCode(result, targetDir, "Makefile.foo");
//		checkCode(result, targetDir, "foo.h");
//		checkCode(result, targetDir, "foo.cpp");
//		checkCode(result, targetDir, "README.foo");
//	}
//
//	public void testAttribute() throws Exception{
//		String resourceDir = "CXX\\attribute";
//		GeneratorParam genParam = new GeneratorParam();
//		genParam.setOutputDirectory(rootPath + "\\resource\\work");
//		RtcParam rtcParam = new RtcParam(genParam);
//		rtcParam.setLanguage(rtcParam.LANG_CPP);
//		rtcParam.setName("foo");
//		rtcParam.setDescription("MDesc");
//		rtcParam.setVersion("1.0.1");
//		rtcParam.setVender("TA");
//		rtcParam.setCategory("Manip");
//		rtcParam.setComponentType("STATIC2");
//		rtcParam.setActivityType("PERIODIC2");
//		rtcParam.setMaxInstance(5);
//
//		List<String> privateAtt = new ArrayList<String>(); 
//		privateAtt.add(new String("int private1"));
//		privateAtt.add(new String("static int attribute_4"));
//		rtcParam.setPrivateAttributes(privateAtt);
//
//		List<String> protectedAtt = new ArrayList<String>(); 
//		protectedAtt.add(new String("String protectval"));
//		protectedAtt.add(new String("static float attribute_4"));
//		rtcParam.setProtectedAttributes(protectedAtt);
//
//		List<String> publicdAtt = new ArrayList<String>(); 
//		publicdAtt.add(new String("boolean pubbol1"));
//		publicdAtt.add(new String("double attribute_5"));
//		rtcParam.setPublicAttributes(publicdAtt);
//
//		genParam.getRtcParams().add(rtcParam);
//		
//		Generator generator = new Generator();
//		List<GeneratedResult> result = generator.doGenerate(genParam);
//
//		String targetDir = rootPath + "\\resource\\CXX\\attribute\\";
//		checkCode(result, targetDir, "fooComp.cpp");
//		checkCode(result, targetDir, "Makefile.foo");
//		checkCode(result, targetDir, "foo.h");
//		checkCode(result, targetDir, "foo.cpp");
//		checkCode(result, targetDir, "README.foo");
//	}

	public void testServicePort2() throws Exception{
		String resourceDir = "042\\CXX\\service2";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		genParam.addProviderIDLPath(rootPath + "\\resource\\MyService.idl");
		genParam.addConsumerIDLPath(rootPath + "\\resource\\DAQService.idl");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPP);
		rtcParam.setName("foo");
		rtcParam.setDescription("MDesc");
		rtcParam.setVersion("1.0.1");
		rtcParam.setVender("TA");
		rtcParam.setCategory("Manip");
		rtcParam.setComponentType("STATIC2");
		rtcParam.setActivityType("PERIODIC2");
		rtcParam.setMaxInstance(5);
		rtcParam.setRtmVersion("0.4.2");
		rtcParam.setIsTest(true);
		genParam.getRtcParams().add(rtcParam);
		List<DataPortParam> dataport = new ArrayList<DataPortParam>(); 
		dataport.add(new DataPortParam("InP1", "TimedShort"));
		dataport.add(new DataPortParam("InP2", "TimedLong"));
		rtcParam.setInports(dataport);
		List<DataPortParam> outport = new ArrayList<DataPortParam>(); 
		outport.add(new DataPortParam("OutP1", "TimedInt"));
		outport.add(new DataPortParam("OutP2", "TimedFloat"));
		rtcParam.setOutports(outport);

		List<ServiceReferenceParam> srvport = new ArrayList<ServiceReferenceParam>(); 
		srvport.add(new ServiceReferenceParam("svPort","acc","MyService", rtcParam));
		rtcParam.setProviderReferences(srvport);
		
		List<ServiceReferenceParam> cmvport = new ArrayList<ServiceReferenceParam>(); 
		cmvport.add(new ServiceReferenceParam("cmPort","rate","DAQService", rtcParam));
		rtcParam.setConsumerReferences(cmvport);
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		String targetDir = rootPath + "\\resource\\042\\CXX\\service2\\";
		checkCode(result, targetDir, "fooComp.cpp");
		checkCode(result, targetDir, "Makefile.foo");
		checkCode(result, targetDir, "foo.h");
		checkCode(result, targetDir, "foo.cpp");
		checkCode(result, targetDir, "MyServiceSVC_impl.h");
		checkCode(result, targetDir, "MyServiceSVC_impl.cpp");
		checkCode(result, targetDir, "README.foo");
		//
		checkCode(result, targetDir, "foo_vc8.sln");
		checkCode(result, targetDir, "foo_vc8.vcproj");
		checkCode(result, targetDir, "foo_vc9.sln");
		checkCode(result, targetDir, "foo_vc9.vcproj");
		checkCode(result, targetDir, "fooComp_vc8.vcproj");
		checkCode(result, targetDir, "fooComp_vc9.vcproj");
	}

	public void testServicePort1() throws Exception{
		String resourceDir = "042\\CXX\\service1";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		genParam.addProviderIDLPath(rootPath + "\\resource\\MyService.idl");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPP);
		rtcParam.setName("foo");
		rtcParam.setDescription("MDesc");
		rtcParam.setVersion("1.0.1");
		rtcParam.setVender("TA");
		rtcParam.setCategory("Manip");
		rtcParam.setComponentType("STATIC2");
		rtcParam.setActivityType("PERIODIC2");
		rtcParam.setRtmVersion("0.4.2");
		rtcParam.setIsTest(true);
		rtcParam.setMaxInstance(5);
		genParam.getRtcParams().add(rtcParam);
		List<DataPortParam> dataport = new ArrayList<DataPortParam>(); 
		dataport.add(new DataPortParam("InP1", "TimedShort"));
		dataport.add(new DataPortParam("InP2", "TimedLong"));
		rtcParam.setInports(dataport);
		List<DataPortParam> outport = new ArrayList<DataPortParam>(); 
		outport.add(new DataPortParam("OutP1", "TimedDouble"));
		outport.add(new DataPortParam("OutP2", "TimedFloat"));
		rtcParam.setOutports(outport);

		List<ServiceReferenceParam> srvport = new ArrayList<ServiceReferenceParam>(); 
		srvport.add(new ServiceReferenceParam("svPort","acc","MyService", rtcParam));
		rtcParam.setProviderReferences(srvport);
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));
		
		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		String targetDir = rootPath + "\\resource\\042\\CXX\\service1\\";
		checkCode(result, targetDir, "fooComp.cpp");
		checkCode(result, targetDir, "Makefile.foo");
		checkCode(result, targetDir, "foo.h");
		checkCode(result, targetDir, "foo.cpp");
		checkCode(result, targetDir, "MyServiceSVC_impl.h");
		checkCode(result, targetDir, "MyServiceSVC_impl.cpp");
		checkCode(result, targetDir, "README.foo");
		//
		checkCode(result, targetDir, "foo_vc8.sln");
		checkCode(result, targetDir, "foo_vc8.vcproj");
		checkCode(result, targetDir, "foo_vc9.sln");
		checkCode(result, targetDir, "foo_vc9.vcproj");
		checkCode(result, targetDir, "fooComp_vc8.vcproj");
		checkCode(result, targetDir, "fooComp_vc9.vcproj");
//		checkCode(result, targetDir, "rtm_config.vsprops");
//		checkCode(result, targetDir, "rtm_config_omni412.vsprops");
		//
		checkCode(result, targetDir, "copyprops.bat");
		checkCode(result, targetDir, "user_config.vsprops");
	}

	public void testOutPort2() throws Exception{
		String resourceDir = "042\\CXX\\outport2";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPP);
		rtcParam.setName("foo");
		rtcParam.setDescription("MDesc");
		rtcParam.setVersion("1.0.1");
		rtcParam.setVender("TA");
		rtcParam.setCategory("Manip");
		rtcParam.setComponentType("STATIC2");
		rtcParam.setActivityType("PERIODIC2");
		rtcParam.setMaxInstance(5);
		rtcParam.setRtmVersion("0.4.2");
		rtcParam.setIsTest(true);
		genParam.getRtcParams().add(rtcParam);
		List<DataPortParam> dataport = new ArrayList<DataPortParam>(); 
		dataport.add(new DataPortParam("InP1", "TimedShort"));
		dataport.add(new DataPortParam("InP2", "TimedLong"));
		rtcParam.setInports(dataport);
		List<DataPortParam> outport = new ArrayList<DataPortParam>(); 
		outport.add(new DataPortParam("OutP1", "TimedInt"));
		outport.add(new DataPortParam("OutP2", "TimedFloat"));
		rtcParam.setOutports(outport);
		
		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		String targetDir = rootPath + "\\resource\\042\\CXX\\outport2\\";
		checkCode(result, targetDir, "fooComp.cpp");
		checkCode(result, targetDir, "Makefile.foo");
		checkCode(result, targetDir, "foo.h");
		checkCode(result, targetDir, "foo.cpp");
		checkCode(result, targetDir, "README.foo");
		//
		checkCode(result, targetDir, "foo_vc8.sln");
		checkCode(result, targetDir, "foo_vc8.vcproj");
		checkCode(result, targetDir, "foo_vc9.sln");
		checkCode(result, targetDir, "foo_vc9.vcproj");
		checkCode(result, targetDir, "fooComp_vc8.vcproj");
		checkCode(result, targetDir, "fooComp_vc9.vcproj");
//		checkCode(result, targetDir, "rtm_config.vsprops");
//		checkCode(result, targetDir, "rtm_config_omni412.vsprops");
		//
		checkCode(result, targetDir, "copyprops.bat");
		checkCode(result, targetDir, "user_config.vsprops");
	}

	public void testOutPort1() throws Exception{
		String resourceDir = "042\\CXX\\outport1";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPP);
		rtcParam.setName("foo");
		rtcParam.setDescription("MDesc");
		rtcParam.setVersion("1.0.1");
		rtcParam.setVender("TA");
		rtcParam.setCategory("Manip");
		rtcParam.setComponentType("STATIC2");
		rtcParam.setActivityType("PERIODIC2");
		rtcParam.setMaxInstance(5);
		rtcParam.setRtmVersion("0.4.2");
		rtcParam.setIsTest(true);
		genParam.getRtcParams().add(rtcParam);
		List<DataPortParam> dataport = new ArrayList<DataPortParam>(); 
		dataport.add(new DataPortParam("InP1", "TimedShort"));
		dataport.add(new DataPortParam("InP2", "TimedLong"));
		rtcParam.setInports(dataport);
		List<DataPortParam> outport = new ArrayList<DataPortParam>(); 
		outport.add(new DataPortParam("OutP1", "TimedInt"));
		rtcParam.setOutports(outport);
		
		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		String targetDir = rootPath + "\\resource\\042\\CXX\\outport1\\";
		checkCode(result, targetDir, "fooComp.cpp");
		checkCode(result, targetDir, "Makefile.foo");
		checkCode(result, targetDir, "foo.h");
		checkCode(result, targetDir, "foo.cpp");
		checkCode(result, targetDir, "README.foo");
		//
		checkCode(result, targetDir, "foo_vc8.sln");
		checkCode(result, targetDir, "foo_vc8.vcproj");
		checkCode(result, targetDir, "foo_vc9.sln");
		checkCode(result, targetDir, "foo_vc9.vcproj");
		checkCode(result, targetDir, "fooComp_vc8.vcproj");
		checkCode(result, targetDir, "fooComp_vc9.vcproj");
//		checkCode(result, targetDir, "rtm_config.vsprops");
//		checkCode(result, targetDir, "rtm_config_omni412.vsprops");
		//
		checkCode(result, targetDir, "copyprops.bat");
		checkCode(result, targetDir, "user_config.vsprops");
	}

	public void testInPort2() throws Exception{
		String resourceDir = "042\\CXX\\inport2";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPP);
		rtcParam.setName("foo");
		rtcParam.setDescription("MDesc");
		rtcParam.setVersion("1.0.1");
		rtcParam.setVender("TA");
		rtcParam.setCategory("Manip");
		rtcParam.setComponentType("STATIC2");
		rtcParam.setActivityType("PERIODIC2");
		rtcParam.setMaxInstance(5);
		rtcParam.setRtmVersion("0.4.2");
		rtcParam.setIsTest(true);
		genParam.getRtcParams().add(rtcParam);
		List<DataPortParam> dataport = new ArrayList<DataPortParam>(); 
		dataport.add(new DataPortParam("InP1", "TimedShort"));
		dataport.add(new DataPortParam("InP2", "TimedLong"));
		rtcParam.setInports(dataport);
		
		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		String targetDir = rootPath + "\\resource\\042\\CXX\\inport2\\";
		checkCode(result, targetDir, "fooComp.cpp");
		checkCode(result, targetDir, "Makefile.foo");
		checkCode(result, targetDir, "foo.h");
		checkCode(result, targetDir, "foo.cpp");
		checkCode(result, targetDir, "README.foo");
		//
		checkCode(result, targetDir, "foo_vc8.sln");
		checkCode(result, targetDir, "foo_vc8.vcproj");
		checkCode(result, targetDir, "foo_vc9.sln");
		checkCode(result, targetDir, "foo_vc9.vcproj");
		checkCode(result, targetDir, "fooComp_vc8.vcproj");
		checkCode(result, targetDir, "fooComp_vc9.vcproj");
//		checkCode(result, targetDir, "rtm_config.vsprops");
//		checkCode(result, targetDir, "rtm_config_omni412.vsprops");
		//
		checkCode(result, targetDir, "copyprops.bat");
		checkCode(result, targetDir, "user_config.vsprops");
	}

	public void testInPort() throws Exception{
		String resourceDir = "042\\CXX\\inport1";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPP);
		rtcParam.setName("test");
		rtcParam.setDescription("MDesc");
		rtcParam.setVersion("1.0.1");
		rtcParam.setVender("TA");
		rtcParam.setCategory("Manip");
		rtcParam.setComponentType("STATIC2");
		rtcParam.setActivityType("PERIODIC2");
		rtcParam.setMaxInstance(5);
		rtcParam.setRtmVersion("0.4.2");
		rtcParam.setIsTest(true);
		genParam.getRtcParams().add(rtcParam);
		List<DataPortParam> dataport = new ArrayList<DataPortParam>(); 
		dataport.add(new DataPortParam("InP1", "TimedShort"));
		rtcParam.setInports(dataport);
		
		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		String targetDir = rootPath + "\\resource\\042\\CXX\\inport1\\";
		checkCode(result, targetDir, "testComp.cpp");
		checkCode(result, targetDir, "Makefile.test");
		checkCode(result, targetDir, "test.h");
		checkCode(result, targetDir, "test.cpp");
		checkCode(result, targetDir, "README.test");
		//
		checkCode(result, targetDir, "test_vc8.sln");
		checkCode(result, targetDir, "test_vc8.vcproj");
		checkCode(result, targetDir, "test_vc9.sln");
		checkCode(result, targetDir, "test_vc9.vcproj");
		checkCode(result, targetDir, "testComp_vc8.vcproj");
		checkCode(result, targetDir, "testComp_vc9.vcproj");
//		checkCode(result, targetDir, "rtm_config.vsprops");
//		checkCode(result, targetDir, "rtm_config_omni412.vsprops");
		checkCode(result, targetDir, "copyprops.bat");
		checkCode(result, targetDir, "user_config.vsprops");
	}

	public void testBasic() throws Exception{
		String resourceDir = "042\\CXX\\name";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPP);
		rtcParam.setName("foo");
		rtcParam.setDescription("MDesc");
		rtcParam.setVersion("1.0.1");
		rtcParam.setVender("TA");
		rtcParam.setCategory("Manip");
		rtcParam.setComponentType("STATIC2");
		rtcParam.setActivityType("PERIODIC2");
		rtcParam.setMaxInstance(5);
		rtcParam.setRtmVersion("0.4.2");
		rtcParam.setIsTest(true);
		genParam.getRtcParams().add(rtcParam);
		
		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		String targetDir = rootPath + "\\resource\\042\\CXX\\name\\";
		checkCode(result, targetDir, "fooComp.cpp");
		checkCode(result, targetDir, "Makefile.foo");
		checkCode(result, targetDir, "foo.h");
		checkCode(result, targetDir, "foo.cpp");
		checkCode(result, targetDir, "README.foo");
		//
		checkCode(result, targetDir, "foo_vc8.sln");
		checkCode(result, targetDir, "foo_vc8.vcproj");
		checkCode(result, targetDir, "foo_vc9.sln");
		checkCode(result, targetDir, "foo_vc9.vcproj");
		checkCode(result, targetDir, "fooComp_vc8.vcproj");
		checkCode(result, targetDir, "fooComp_vc9.vcproj");
//		checkCode(result, targetDir, "rtm_config.vsprops");
//		checkCode(result, targetDir, "rtm_config_omni412.vsprops");
		//
		checkCode(result, targetDir, "copyprops.bat");
		checkCode(result, targetDir, "user_config.vsprops");
	}
}
