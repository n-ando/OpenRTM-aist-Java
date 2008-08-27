package jp.go.aist.rtm.rtctemplate._test.cxxwin;

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
//		assertEquals("fooComp.cpp", result.get(0).getName());
//		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\fooComp.cpp";
//		String expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(0).getCode());
//
//		assertEquals("Makefile.foo", result.get(1).getName());
//		expReadMeS = rootPath +  "\\resource\\" +resourceDir + "\\Makefile.foo";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(1).getCode());
//
//		assertEquals("foo.h", result.get(2).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.h";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(2).getCode());
//
//		assertEquals("foo.cpp", result.get(3).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.cpp";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(3).getCode());
//
//		assertEquals("README.foo", result.get(4).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.foo";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(4).getCode());
//
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
//		assertEquals("fooComp.cpp", result.get(0).getName());
//		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\fooComp.cpp";
//		String expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(0).getCode());
//
//		assertEquals("Makefile.foo", result.get(1).getName());
//		expReadMeS = rootPath +  "\\resource\\" +resourceDir + "\\Makefile.foo";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(1).getCode());
//
//		assertEquals("foo.h", result.get(2).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.h";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(2).getCode());
//
//		assertEquals("foo.cpp", result.get(3).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.cpp";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(3).getCode());
//
//		assertEquals("README.foo", result.get(4).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.foo";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(4).getCode());
//
//	}
//
	public void testServicePort2() throws Exception{
		String resourceDir = "CXXWin\\service2";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		genParam.addProviderIDLPath(rootPath + "\\resource\\MyService.idl");
		genParam.addConsumerIDLPath(rootPath + "\\resource\\DAQService.idl");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPPWIN);
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

		assertEquals("fooComp.cpp", result.get(0).getName());
		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\fooComp.cpp";
		String expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(0).getCode());

		assertEquals("Makefile.foo", result.get(1).getName());
		expReadMeS = rootPath +  "\\resource\\" +resourceDir + "\\Makefile.foo";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(1).getCode());

		assertEquals("foo.h", result.get(2).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.h";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(2).getCode());

		assertEquals("foo.cpp", result.get(3).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.cpp";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(3).getCode());

		assertEquals("OpenRTM-aist.vsprops", result.get(4).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\OpenRTM-aist.vsprops";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(4).getCode());

		assertEquals("foo.vcproj", result.get(5).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.vcproj";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(5).getCode());

		assertEquals("MyServiceSVC_impl.h", result.get(6).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\MyServiceSVC_impl.h";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(6).getCode());

		assertEquals("MyServiceSVC_impl.cpp", result.get(7).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\MyServiceSVC_impl.cpp";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(7).getCode());

		assertEquals("README.foo", result.get(8).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.foo";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(8).getCode());
	}

	public void testServicePort1() throws Exception{
		String resourceDir = "CXXWin\\service1";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		genParam.addProviderIDLPath(rootPath + "\\resource\\MyService.idl");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPPWIN);
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
		dataport.add(new DataPortParam("InP1", "TimedShort"));
		dataport.add(new DataPortParam("InP2", "TimedLong"));
		rtcParam.setInports(dataport);
		List<DataPortParam> outport = new ArrayList<DataPortParam>(); 
		outport.add(new DataPortParam("OutP1", "TimedShort"));
		outport.add(new DataPortParam("OutP2", "TimedFloat"));
		rtcParam.setOutports(outport);

		List<ServiceReferenceParam> srvport = new ArrayList<ServiceReferenceParam>(); 
		srvport.add(new ServiceReferenceParam("svPort","acc","MyService", rtcParam));
		rtcParam.setProviderReferences(srvport);
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));
		
		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		assertEquals("fooComp.cpp", result.get(0).getName());
		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\fooComp.cpp";
		String expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(0).getCode());

		assertEquals("Makefile.foo", result.get(1).getName());
		expReadMeS = rootPath +  "\\resource\\" +resourceDir + "\\Makefile.foo";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(1).getCode());

		assertEquals("foo.h", result.get(2).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.h";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(2).getCode());

		assertEquals("foo.cpp", result.get(3).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.cpp";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(3).getCode());

		assertEquals("OpenRTM-aist.vsprops", result.get(4).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\OpenRTM-aist.vsprops";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(4).getCode());

		assertEquals("foo.vcproj", result.get(5).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.vcproj";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(5).getCode());

		assertEquals("MyServiceSVC_impl.h", result.get(6).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\MyServiceSVC_impl.h";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(6).getCode());

		assertEquals("MyServiceSVC_impl.cpp", result.get(7).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\MyServiceSVC_impl.cpp";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(7).getCode());

		assertEquals("README.foo", result.get(8).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.foo";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(8).getCode());
}

	public void testOutPort2() throws Exception{
		String resourceDir = "CXXWin\\outport2";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPPWIN);
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
		dataport.add(new DataPortParam("InP1", "TimedShort"));
		dataport.add(new DataPortParam("InP2", "TimedLong"));
		rtcParam.setInports(dataport);
		List<DataPortParam> outport = new ArrayList<DataPortParam>(); 
		outport.add(new DataPortParam("OutP1", "TimedInt"));
		outport.add(new DataPortParam("OutP2", "TimedFloat"));
		rtcParam.setOutports(outport);
		
		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		assertEquals("fooComp.cpp", result.get(0).getName());
		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\fooComp.cpp";
		String expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(0).getCode());

		assertEquals("Makefile.foo", result.get(1).getName());
		expReadMeS = rootPath +  "\\resource\\" +resourceDir + "\\Makefile.foo";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(1).getCode());

		assertEquals("foo.h", result.get(2).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.h";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(2).getCode());

		assertEquals("foo.cpp", result.get(3).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.cpp";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(3).getCode());

		assertEquals("OpenRTM-aist.vsprops", result.get(4).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\OpenRTM-aist.vsprops";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(4).getCode());

		assertEquals("foo.vcproj", result.get(5).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.vcproj";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(5).getCode());

		assertEquals("README.foo", result.get(6).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.foo";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(6).getCode());
	}

	public void testOutPort1() throws Exception{
		String resourceDir = "CXXWin\\outport1";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPPWIN);
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
		dataport.add(new DataPortParam("InP1", "TimedShort"));
		dataport.add(new DataPortParam("InP2", "TimedLong"));
		rtcParam.setInports(dataport);
		List<DataPortParam> outport = new ArrayList<DataPortParam>(); 
		outport.add(new DataPortParam("OutP1", "TimedInt"));
		rtcParam.setOutports(outport);
		
		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		assertEquals("fooComp.cpp", result.get(0).getName());
		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\fooComp.cpp";
		String expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(0).getCode());

		assertEquals("Makefile.foo", result.get(1).getName());
		expReadMeS = rootPath +  "\\resource\\" +resourceDir + "\\Makefile.foo";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(1).getCode());

		assertEquals("foo.h", result.get(2).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.h";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(2).getCode());

		assertEquals("foo.cpp", result.get(3).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.cpp";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(3).getCode());

		assertEquals("OpenRTM-aist.vsprops", result.get(4).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\OpenRTM-aist.vsprops";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(4).getCode());

		assertEquals("foo.vcproj", result.get(5).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.vcproj";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(5).getCode());

		assertEquals("README.foo", result.get(6).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.foo";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(6).getCode());
	}

	public void testInPort2() throws Exception{
		String resourceDir = "CXXWin\\inport2";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPPWIN);
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
		dataport.add(new DataPortParam("InP1", "TimedShort"));
		dataport.add(new DataPortParam("InP2", "TimedLong"));
		rtcParam.setInports(dataport);
		
		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		assertEquals("fooComp.cpp", result.get(0).getName());
		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\fooComp.cpp";
		String expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(0).getCode());

		assertEquals("Makefile.foo", result.get(1).getName());
		expReadMeS = rootPath +  "\\resource\\" +resourceDir + "\\Makefile.foo";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(1).getCode());

		assertEquals("foo.h", result.get(2).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.h";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(2).getCode());

		assertEquals("foo.cpp", result.get(3).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.cpp";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(3).getCode());

		assertEquals("OpenRTM-aist.vsprops", result.get(4).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\OpenRTM-aist.vsprops";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(4).getCode());

		assertEquals("foo.vcproj", result.get(5).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.vcproj";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(5).getCode());

		assertEquals("README.foo", result.get(6).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.foo";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(6).getCode());
	}

	public void testInPort() throws Exception{
		String resourceDir = "CXXWin\\inport1";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPPWIN);
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
		dataport.add(new DataPortParam("InP1", "TimedShort"));
		rtcParam.setInports(dataport);
		
		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		assertEquals("fooComp.cpp", result.get(0).getName());
		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\fooComp.cpp";
		String expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(0).getCode());

		assertEquals("Makefile.foo", result.get(1).getName());
		expReadMeS = rootPath +  "\\resource\\" +resourceDir + "\\Makefile.foo";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(1).getCode());

		assertEquals("foo.h", result.get(2).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.h";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(2).getCode());

		assertEquals("foo.cpp", result.get(3).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.cpp";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(3).getCode());

		assertEquals("OpenRTM-aist.vsprops", result.get(4).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\OpenRTM-aist.vsprops";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(4).getCode());

		assertEquals("foo.vcproj", result.get(5).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.vcproj";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(5).getCode());

		assertEquals("README.foo", result.get(6).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.foo";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(6).getCode());
	}

	public void testName2() throws Exception{
		String resourceDir = "CXXWin\\name2";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPPWIN);
		rtcParam.setName("bar");
		rtcParam.setDescription("MDesc");
		rtcParam.setVersion("1.0.1");
		rtcParam.setVender("TA");
		rtcParam.setCategory("Manip");
		rtcParam.setComponentType("STATIC2");
		rtcParam.setActivityType("PERIODIC2");
		rtcParam.setMaxInstance(5);
		genParam.getRtcParams().add(rtcParam);
		
		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		assertEquals("README.bar", result.get(6).getName());
		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.bar";
		String expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(6).getCode());

		assertEquals("barComp.cpp", result.get(0).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\barComp.cpp";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(0).getCode());

		assertEquals("Makefile.bar", result.get(1).getName());
		expReadMeS = rootPath +  "\\resource\\" +resourceDir + "\\Makefile.bar";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(1).getCode());

		assertEquals("bar.h", result.get(2).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\bar.h";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(2).getCode());

		assertEquals("bar.cpp", result.get(3).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\bar.cpp";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(3).getCode());

		assertEquals("OpenRTM-aist.vsprops", result.get(4).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\OpenRTM-aist.vsprops";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(4).getCode());

		assertEquals("bar.vcproj", result.get(5).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\bar.vcproj";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(5).getCode());
	}

	public void testBasic() throws Exception{
		String resourceDir = "CXXWin\\name";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPPWIN);
		rtcParam.setName("foo");
		rtcParam.setDescription("MDesc");
		rtcParam.setVersion("1.0.1");
		rtcParam.setVender("TA");
		rtcParam.setCategory("Manip");
		rtcParam.setComponentType("STATIC2");
		rtcParam.setActivityType("PERIODIC2");
		rtcParam.setMaxInstance(5);
		genParam.getRtcParams().add(rtcParam);
		
		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		assertEquals("README.foo", result.get(6).getName());
		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.foo";
		String expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(6).getCode());

		assertEquals("fooComp.cpp", result.get(0).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\fooComp.cpp";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(0).getCode());

		assertEquals("Makefile.foo", result.get(1).getName());
		expReadMeS = rootPath +  "\\resource\\" +resourceDir + "\\Makefile.foo";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(1).getCode());

		assertEquals("foo.h", result.get(2).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.h";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(2).getCode());

		assertEquals("foo.cpp", result.get(3).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.cpp";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(3).getCode());

		assertEquals("OpenRTM-aist.vsprops", result.get(4).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\OpenRTM-aist.vsprops";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(4).getCode());

		assertEquals("foo.vcproj", result.get(5).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.vcproj";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(5).getCode());
	}

}
