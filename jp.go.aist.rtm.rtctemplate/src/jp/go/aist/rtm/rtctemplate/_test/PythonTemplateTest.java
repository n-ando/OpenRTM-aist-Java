package jp.go.aist.rtm.rtctemplate._test;

import java.util.List;

import jp.go.aist.rtm.rtctemplate.Generator;
import jp.go.aist.rtm.rtctemplate.generator.GeneratedResult;
import jp.go.aist.rtm.rtctemplate.generator.param.GeneratorParam;
import jp.go.aist.rtm.rtctemplate.generator.param.RtcParam;

public class PythonTemplateTest extends TestBase {

	protected void setUp() throws Exception {
	}

//	public void testServicePort2() throws Exception{
//		String resourceDir = "CXX\\service2";
//		GeneratorParam genParam = new GeneratorParam();
//		genParam.setOutputDirectory(rootPath + "\\resource\\work");
//		genParam.addConsumerIDLPath(rootPath + "\\resource\\MyService.idl");
//		genParam.addConsumerIDLPath(rootPath + "\\resource\\DAQService.idl");
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
//		genParam.getRtcParams().add(rtcParam);
//		List<DataPortParam> dataport = new ArrayList<DataPortParam>(); 
//		dataport.add(new DataPortParam("InP1", "TimedShort"));
//		dataport.add(new DataPortParam("InP2", "TimedLong"));
//		rtcParam.setInports(dataport);
//		List<DataPortParam> outport = new ArrayList<DataPortParam>(); 
//		outport.add(new DataPortParam("OutP1", "TimedInt"));
//		outport.add(new DataPortParam("OutP2", "TimedFloat"));
//		rtcParam.setOutports(outport);
//
//		List<ServiceReferenceParam> srvport = new ArrayList<ServiceReferenceParam>(); 
//		srvport.add(new ServiceReferenceParam("svPort","acc","MyService", rtcParam));
//		rtcParam.setProviderReferences(srvport);
//		
//		List<ServiceReferenceParam> cmvport = new ArrayList<ServiceReferenceParam>(); 
//		cmvport.add(new ServiceReferenceParam("cmPort","rate","DAQService", rtcParam));
//		rtcParam.setConsumerReferences(cmvport);
//
//		Generator generator = new Generator();
//		List<GeneratedResult> result = generator.doGenerate(genParam);
//
//		assertEquals("README.foo", result.get(0).getName());
//		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.foo";
//		String expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(0).getCode());
//
//		assertEquals("fooComp.cpp", result.get(1).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\fooComp.cpp";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(1).getCode());
//
//		assertEquals("Makefile.foo", result.get(2).getName());
//		expReadMeS = rootPath +  "\\resource\\" +resourceDir + "\\Makefile.foo";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(2).getCode());
//
//		assertEquals("foo.h", result.get(3).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.h";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(3).getCode());
//
//		assertEquals("foo.cpp", result.get(4).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.cpp";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(4).getCode());
//
//		assertEquals("MyServiceSVC_impl.h", result.get(5).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\MyServiceSVC_impl.h";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(5).getCode());
//
//		assertEquals("MyServiceSVC_impl.cpp", result.get(6).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\MyServiceSVC_impl.cpp";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(6).getCode());
//	}
//
//	public void testServicePort1() throws Exception{
//		String resourceDir = "CXX\\service1";
//		GeneratorParam genParam = new GeneratorParam();
//		genParam.setOutputDirectory(rootPath + "\\resource\\work");
//		genParam.addConsumerIDLPath(rootPath + "\\resource\\MyService.idl");
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
//		genParam.getRtcParams().add(rtcParam);
//		List<DataPortParam> dataport = new ArrayList<DataPortParam>(); 
//		dataport.add(new DataPortParam("InP1", "TimedShort"));
//		dataport.add(new DataPortParam("InP2", "TimedLong"));
//		rtcParam.setInports(dataport);
//		List<DataPortParam> outport = new ArrayList<DataPortParam>(); 
//		outport.add(new DataPortParam("OutP1", "TimedInt"));
//		outport.add(new DataPortParam("OutP2", "TimedFloat"));
//		rtcParam.setOutports(outport);
//
//		List<ServiceReferenceParam> srvport = new ArrayList<ServiceReferenceParam>(); 
//		srvport.add(new ServiceReferenceParam("svPort","acc","MyService", rtcParam));
//		rtcParam.setProviderReferences(srvport);
//		
//		Generator generator = new Generator();
//		List<GeneratedResult> result = generator.doGenerate(genParam);
//
//		assertEquals("README.foo", result.get(0).getName());
//		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.foo";
//		String expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(0).getCode());
//
//		assertEquals("fooComp.cpp", result.get(1).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\fooComp.cpp";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(1).getCode());
//
//		assertEquals("Makefile.foo", result.get(2).getName());
//		expReadMeS = rootPath +  "\\resource\\" +resourceDir + "\\Makefile.foo";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(2).getCode());
//
//		assertEquals("foo.h", result.get(3).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.h";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(3).getCode());
//
//		assertEquals("foo.cpp", result.get(4).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.cpp";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(4).getCode());
//
//		assertEquals("MyServiceSVC_impl.h", result.get(5).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\MyServiceSVC_impl.h";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(5).getCode());
//
//		assertEquals("MyServiceSVC_impl.cpp", result.get(6).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\MyServiceSVC_impl.cpp";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(6).getCode());
//	}
//
//	public void testOutPort2() throws Exception{
//		String resourceDir = "CXX\\outport2";
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
//		genParam.getRtcParams().add(rtcParam);
//		List<DataPortParam> dataport = new ArrayList<DataPortParam>(); 
//		dataport.add(new DataPortParam("InP1", "TimedShort"));
//		dataport.add(new DataPortParam("InP2", "TimedLong"));
//		rtcParam.setInports(dataport);
//		List<DataPortParam> outport = new ArrayList<DataPortParam>(); 
//		outport.add(new DataPortParam("OutP1", "TimedInt"));
//		outport.add(new DataPortParam("OutP2", "TimedFloat"));
//		rtcParam.setOutports(outport);
//		
//		Generator generator = new Generator();
//		List<GeneratedResult> result = generator.doGenerate(genParam);
//
//		assertEquals("README.foo", result.get(0).getName());
//		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.foo";
//		String expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(0).getCode());
//
//		assertEquals("fooComp.cpp", result.get(1).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\fooComp.cpp";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(1).getCode());
//
//		assertEquals("Makefile.foo", result.get(2).getName());
//		expReadMeS = rootPath +  "\\resource\\" +resourceDir + "\\Makefile.foo";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(2).getCode());
//
//		assertEquals("foo.h", result.get(3).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.h";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(3).getCode());
//
//		assertEquals("foo.cpp", result.get(4).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.cpp";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(4).getCode());
//	}
//
//	public void testOutPort1() throws Exception{
//		String resourceDir = "CXX\\outport1";
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
//		genParam.getRtcParams().add(rtcParam);
//		List<DataPortParam> dataport = new ArrayList<DataPortParam>(); 
//		dataport.add(new DataPortParam("InP1", "TimedShort"));
//		dataport.add(new DataPortParam("InP2", "TimedLong"));
//		rtcParam.setInports(dataport);
//		List<DataPortParam> outport = new ArrayList<DataPortParam>(); 
//		outport.add(new DataPortParam("OutP1", "TimedInt"));
//		rtcParam.setOutports(outport);
//		
//		Generator generator = new Generator();
//		List<GeneratedResult> result = generator.doGenerate(genParam);
//
//		assertEquals("README.foo", result.get(0).getName());
//		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.foo";
//		String expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(0).getCode());
//
//		assertEquals("fooComp.cpp", result.get(1).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\fooComp.cpp";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(1).getCode());
//
//		assertEquals("Makefile.foo", result.get(2).getName());
//		expReadMeS = rootPath +  "\\resource\\" +resourceDir + "\\Makefile.foo";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(2).getCode());
//
//		assertEquals("foo.h", result.get(3).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.h";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(3).getCode());
//
//		assertEquals("foo.cpp", result.get(4).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.cpp";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(4).getCode());
//	}
//
//	public void testInPort2() throws Exception{
//		String resourceDir = "CXX\\inport2";
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
//		genParam.getRtcParams().add(rtcParam);
//		List<DataPortParam> dataport = new ArrayList<DataPortParam>(); 
//		dataport.add(new DataPortParam("InP1", "TimedShort"));
//		dataport.add(new DataPortParam("InP2", "TimedLong"));
//		rtcParam.setInports(dataport);
//		
//		Generator generator = new Generator();
//		List<GeneratedResult> result = generator.doGenerate(genParam);
//
//		assertEquals("README.foo", result.get(0).getName());
//		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.foo";
//		String expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(0).getCode());
//
//		assertEquals("fooComp.cpp", result.get(1).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\fooComp.cpp";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(1).getCode());
//
//		assertEquals("Makefile.foo", result.get(2).getName());
//		expReadMeS = rootPath +  "\\resource\\" +resourceDir + "\\Makefile.foo";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(2).getCode());
//
//		assertEquals("foo.h", result.get(3).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.h";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(3).getCode());
//
//		assertEquals("foo.cpp", result.get(4).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.cpp";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(4).getCode());
//	}
//
//	public void testInPort() throws Exception{
//		String resourceDir = "CXX\\inport1";
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
//		genParam.getRtcParams().add(rtcParam);
//		List<DataPortParam> dataport = new ArrayList<DataPortParam>(); 
//		dataport.add(new DataPortParam("InP1", "TimedShort"));
//		rtcParam.setInports(dataport);
//		
//		Generator generator = new Generator();
//		List<GeneratedResult> result = generator.doGenerate(genParam);
//
//		assertEquals("README.foo", result.get(0).getName());
//		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.foo";
//		String expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(0).getCode());
//
//		assertEquals("fooComp.cpp", result.get(1).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\fooComp.cpp";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(1).getCode());
//
//		assertEquals("Makefile.foo", result.get(2).getName());
//		expReadMeS = rootPath +  "\\resource\\" +resourceDir + "\\Makefile.foo";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(2).getCode());
//
//		assertEquals("foo.h", result.get(3).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.h";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(3).getCode());
//
//		assertEquals("foo.cpp", result.get(4).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.cpp";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(4).getCode());
//	}

	public void testBasic() throws Exception{
		String resourceDir = "CXX\\name";
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
		genParam.getRtcParams().add(rtcParam);
		
		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		assertEquals("README.foo", result.get(0).getName());
		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.foo";
		String expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(0).getCode());

		assertEquals("fooComp.cpp", result.get(1).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\fooComp.cpp";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(1).getCode());

		assertEquals("Makefile.foo", result.get(2).getName());
		expReadMeS = rootPath +  "\\resource\\" +resourceDir + "\\Makefile.foo";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(2).getCode());

		assertEquals("foo.h", result.get(3).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.h";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(3).getCode());

		assertEquals("foo.cpp", result.get(4).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.cpp";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(4).getCode());
	}

}
