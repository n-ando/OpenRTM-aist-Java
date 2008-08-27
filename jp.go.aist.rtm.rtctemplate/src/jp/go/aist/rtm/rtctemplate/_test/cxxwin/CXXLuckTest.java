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

public class CXXLuckTest extends TestBase {

	protected void setUp() throws Exception {
	}

	public void testConsumerNoType() throws Exception{
		String resourceDir = "CXXWin\\Exception\\ConNoName";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPPWIN);
		rtcParam.setName("foo");
		rtcParam.setDescription("test module");
		rtcParam.setVersion("1.0.1");
		rtcParam.setVender("TA");
		rtcParam.setCategory("sample");
		rtcParam.setComponentType("STATIC");
		rtcParam.setActivityType("PERIODIC");
		rtcParam.setMaxInstance(2);
		genParam.getRtcParams().add(rtcParam);
		
		List<DataPortParam> dataInport = new ArrayList<DataPortParam>(); 
		dataInport.add(new DataPortParam("in1", "TimedShort"));
		rtcParam.setInports(dataInport);

		List<DataPortParam> dataOutport = new ArrayList<DataPortParam>(); 
		dataOutport.add(new DataPortParam("out1", "TimedLong"));
		rtcParam.setOutports(dataOutport);

		List<ServiceReferenceParam> proport = new ArrayList<ServiceReferenceParam>(); 
		proport.add(new ServiceReferenceParam("MySVPro","myservice0","MyService", rtcParam));
		rtcParam.setProviderReferences(proport);
		genParam.addProviderIDLPath(rootPath + "\\resource\\CXX\\Exception\\MyService.idl");
		
		List<ServiceReferenceParam> conport = new ArrayList<ServiceReferenceParam>(); 
		conport.add(new ServiceReferenceParam("MyConPro","myservice1","", rtcParam));
		rtcParam.setConsumerReferences(conport);
		genParam.addConsumerIDLPath(rootPath + "\\resource\\CXX\\Exception\\MyService.idl");

		Generator generator = new Generator();
		try {
			List<GeneratedResult> result = generator.doGenerate(genParam);
			fail();
		} catch ( Exception ex ) {
			assertEquals("'' is not found in IDL", ex.getMessage());
		}
	}

	public void testConsumerNoName() throws Exception{
		String resourceDir = "CXXWin\\Exception\\ConNoName";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPPWIN);
		rtcParam.setName("foo");
		rtcParam.setDescription("test module");
		rtcParam.setVersion("1.0.1");
		rtcParam.setVender("TA");
		rtcParam.setCategory("sample");
		rtcParam.setComponentType("STATIC");
		rtcParam.setActivityType("PERIODIC");
		rtcParam.setMaxInstance(2);
		genParam.getRtcParams().add(rtcParam);
		
		List<DataPortParam> dataInport = new ArrayList<DataPortParam>(); 
		dataInport.add(new DataPortParam("in1", "TimedShort"));
		rtcParam.setInports(dataInport);

		List<DataPortParam> dataOutport = new ArrayList<DataPortParam>(); 
		dataOutport.add(new DataPortParam("out1", "TimedLong"));
		rtcParam.setOutports(dataOutport);

		List<ServiceReferenceParam> proport = new ArrayList<ServiceReferenceParam>(); 
		proport.add(new ServiceReferenceParam("MySVPro","myservice0","MyService", rtcParam));
		rtcParam.setProviderReferences(proport);
		genParam.addProviderIDLPath(rootPath + "\\resource\\CXX\\Exception\\MyService.idl");
		
		List<ServiceReferenceParam> conport = new ArrayList<ServiceReferenceParam>(); 
		conport.add(new ServiceReferenceParam("MyConPro","","MyService", rtcParam));
		rtcParam.setConsumerReferences(conport);
		genParam.addConsumerIDLPath(rootPath + "\\resource\\CXX\\Exception\\MyService.idl");

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		assertEquals("fooComp.cpp", result.get(0).getName());
		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\fooComp.cpp";
		String expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(0).getCode());

//		assertEquals("Makefile.foo", result.get(1).getName());
//		expReadMeS = rootPath +  "\\resource\\" +resourceDir + "\\Makefile.foo";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(1).getCode());

//		assertEquals("foo.h", result.get(2).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.h";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(2).getCode());

//		assertEquals("foo.cpp", result.get(3).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.cpp";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(3).getCode());

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

//		assertEquals("README.foo", result.get(8).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.foo";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(8).getCode());
	}

	public void testConsumerNoPortName() throws Exception{
		String resourceDir = "CXXWin\\Exception\\ConNoPortName";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPPWIN);
		rtcParam.setName("foo");
		rtcParam.setDescription("test module");
		rtcParam.setVersion("1.0.1");
		rtcParam.setVender("TA");
		rtcParam.setCategory("sample");
		rtcParam.setComponentType("STATIC");
		rtcParam.setActivityType("PERIODIC");
		rtcParam.setMaxInstance(2);
		genParam.getRtcParams().add(rtcParam);
		
		List<DataPortParam> dataInport = new ArrayList<DataPortParam>(); 
		dataInport.add(new DataPortParam("in1", "TimedShort"));
		rtcParam.setInports(dataInport);

		List<DataPortParam> dataOutport = new ArrayList<DataPortParam>(); 
		dataOutport.add(new DataPortParam("out1", "TimedLong"));
		rtcParam.setOutports(dataOutport);

		List<ServiceReferenceParam> proport = new ArrayList<ServiceReferenceParam>(); 
		proport.add(new ServiceReferenceParam("MySVPro","myservice0","MyService", rtcParam));
		rtcParam.setProviderReferences(proport);
		genParam.addProviderIDLPath(rootPath + "\\resource\\CXX\\Exception\\MyService.idl");
		
		List<ServiceReferenceParam> conport = new ArrayList<ServiceReferenceParam>(); 
		conport.add(new ServiceReferenceParam("","myservice1","MyService", rtcParam));
		rtcParam.setConsumerReferences(conport);
		genParam.addConsumerIDLPath(rootPath + "\\resource\\CXX\\Exception\\MyService.idl");

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		assertEquals("fooComp.cpp", result.get(0).getName());
		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\fooComp.cpp";
		String expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(0).getCode());

//		assertEquals("Makefile.foo", result.get(1).getName());
//		expReadMeS = rootPath +  "\\resource\\" +resourceDir + "\\Makefile.foo";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(1).getCode());

//		assertEquals("foo.h", result.get(2).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.h";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(2).getCode());

//		assertEquals("foo.cpp", result.get(3).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.cpp";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(3).getCode());

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

//		assertEquals("README.foo", result.get(8).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.foo";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(8).getCode());
	}

	public void testProviderNoType() throws Exception{
		String resourceDir = "CXXWin\\Exception\\ProNoType";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPPWIN);
		rtcParam.setName("foo");
		rtcParam.setDescription("test module");
		rtcParam.setVersion("1.0.1");
		rtcParam.setVender("TA");
		rtcParam.setCategory("sample");
		rtcParam.setComponentType("STATIC");
		rtcParam.setActivityType("PERIODIC");
		rtcParam.setMaxInstance(2);
		genParam.getRtcParams().add(rtcParam);
		
		List<DataPortParam> dataInport = new ArrayList<DataPortParam>(); 
		dataInport.add(new DataPortParam("in1", "TimedShort"));
		rtcParam.setInports(dataInport);

		List<DataPortParam> dataOutport = new ArrayList<DataPortParam>(); 
		dataOutport.add(new DataPortParam("out1", "TimedLong"));
		rtcParam.setOutports(dataOutport);

		List<ServiceReferenceParam> proport = new ArrayList<ServiceReferenceParam>(); 
		proport.add(new ServiceReferenceParam("MySVPro","myservice0","", rtcParam));
		rtcParam.setProviderReferences(proport);
		genParam.addProviderIDLPath(rootPath + "\\resource\\CXX\\Exception\\MyService.idl");
		
		Generator generator = new Generator();
		try {
			List<GeneratedResult> result = generator.doGenerate(genParam);
			fail();
		} catch ( Exception ex ) {
			assertEquals("'' is not found in IDL", ex.getMessage());
		}

	}

	public void testProviderNoName() throws Exception{
		String resourceDir = "CXXWin\\Exception\\ProNoName";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPPWIN);
		rtcParam.setName("foo");
		rtcParam.setDescription("test module");
		rtcParam.setVersion("1.0.1");
		rtcParam.setVender("TA");
		rtcParam.setCategory("sample");
		rtcParam.setComponentType("STATIC");
		rtcParam.setActivityType("PERIODIC");
		rtcParam.setMaxInstance(2);
		genParam.getRtcParams().add(rtcParam);
		
		List<DataPortParam> dataInport = new ArrayList<DataPortParam>(); 
		dataInport.add(new DataPortParam("in1", "TimedShort"));
		rtcParam.setInports(dataInport);

		List<DataPortParam> dataOutport = new ArrayList<DataPortParam>(); 
		dataOutport.add(new DataPortParam("out1", "TimedLong"));
		rtcParam.setOutports(dataOutport);

		List<ServiceReferenceParam> proport = new ArrayList<ServiceReferenceParam>(); 
		proport.add(new ServiceReferenceParam("MySVPro","","MyService", rtcParam));
		rtcParam.setProviderReferences(proport);
		genParam.addProviderIDLPath(rootPath + "\\resource\\CXX\\Exception\\MyService.idl");
		
		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		assertEquals("fooComp.cpp", result.get(0).getName());
		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\fooComp.cpp";
		String expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(0).getCode());

//		assertEquals("Makefile.foo", result.get(1).getName());
//		expReadMeS = rootPath +  "\\resource\\" +resourceDir + "\\Makefile.foo";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(1).getCode());

//		assertEquals("foo.h", result.get(2).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.h";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(2).getCode());

//		assertEquals("foo.cpp", result.get(3).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.cpp";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(3).getCode());

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

//		assertEquals("README.foo", result.get(8).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.foo";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(8).getCode());
}

	public void testProviderNoPortName() throws Exception{
		String resourceDir = "CXXWin\\Exception\\ProNoPortName";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPPWIN);
		rtcParam.setName("foo");
		rtcParam.setDescription("test module");
		rtcParam.setVersion("1.0.1");
		rtcParam.setVender("TA");
		rtcParam.setCategory("sample");
		rtcParam.setComponentType("STATIC");
		rtcParam.setActivityType("PERIODIC");
		rtcParam.setMaxInstance(2);
		genParam.getRtcParams().add(rtcParam);
		
		List<DataPortParam> dataInport = new ArrayList<DataPortParam>(); 
		dataInport.add(new DataPortParam("in1", "TimedShort"));
		rtcParam.setInports(dataInport);

		List<DataPortParam> dataOutport = new ArrayList<DataPortParam>(); 
		dataOutport.add(new DataPortParam("out1", "TimedLong"));
		rtcParam.setOutports(dataOutport);

		List<ServiceReferenceParam> proport = new ArrayList<ServiceReferenceParam>(); 
		proport.add(new ServiceReferenceParam("","myservice0","MyService", rtcParam));
		rtcParam.setProviderReferences(proport);
		genParam.addProviderIDLPath(rootPath + "\\resource\\CXX\\Exception\\MyService.idl");
		
		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		assertEquals("fooComp.cpp", result.get(0).getName());
		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\fooComp.cpp";
		String expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(0).getCode());

//		assertEquals("Makefile.foo", result.get(1).getName());
//		expReadMeS = rootPath +  "\\resource\\" +resourceDir + "\\Makefile.foo";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(1).getCode());

//		assertEquals("foo.h", result.get(2).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.h";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(2).getCode());

//		assertEquals("foo.cpp", result.get(3).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.cpp";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(3).getCode());

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

//		assertEquals("README.foo", result.get(8).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.foo";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(8).getCode());
}

	public void testOutPortNoType() throws Exception{
		String resourceDir = "CXXWin\\Exception\\OutPortNoType";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPPWIN);
		rtcParam.setName("foo");
		rtcParam.setDescription("test module");
		rtcParam.setVersion("1.0.1");
		rtcParam.setVender("TA");
		rtcParam.setCategory("sample");
		rtcParam.setComponentType("STATIC");
		rtcParam.setActivityType("PERIODIC");
		rtcParam.setMaxInstance(2);
		genParam.getRtcParams().add(rtcParam);
		
		List<DataPortParam> dataInport = new ArrayList<DataPortParam>(); 
		dataInport.add(new DataPortParam("in1", "TimedShort"));
		rtcParam.setInports(dataInport);

		List<DataPortParam> dataOutport = new ArrayList<DataPortParam>(); 
		dataOutport.add(new DataPortParam("out1", ""));
		rtcParam.setOutports(dataOutport);
		
		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		assertEquals("fooComp.cpp", result.get(0).getName());
		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\fooComp.cpp";
		String expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(0).getCode());

//		assertEquals("Makefile.foo", result.get(1).getName());
//		expReadMeS = rootPath +  "\\resource\\" +resourceDir + "\\Makefile.foo";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(1).getCode());

//		assertEquals("foo.h", result.get(2).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.h";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(2).getCode());

//		assertEquals("foo.cpp", result.get(3).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.cpp";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(3).getCode());

		assertEquals("OpenRTM-aist.vsprops", result.get(4).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\OpenRTM-aist.vsprops";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(4).getCode());

		assertEquals("foo.vcproj", result.get(5).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.vcproj";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(5).getCode());

//		assertEquals("README.foo", result.get(6).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.foo";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(6).getCode());
	}

	public void testOutPortNoName() throws Exception{
		String resourceDir = "CXXWin\\Exception\\OutPortNoName";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPPWIN);
		rtcParam.setName("foo");
		rtcParam.setDescription("test module");
		rtcParam.setVersion("1.0.1");
		rtcParam.setVender("TA");
		rtcParam.setCategory("sample");
		rtcParam.setComponentType("STATIC");
		rtcParam.setActivityType("PERIODIC");
		rtcParam.setMaxInstance(2);
		genParam.getRtcParams().add(rtcParam);
		
		List<DataPortParam> dataInport = new ArrayList<DataPortParam>(); 
		dataInport.add(new DataPortParam("in1", "TimedShort"));
		rtcParam.setInports(dataInport);

		List<DataPortParam> dataOutport = new ArrayList<DataPortParam>(); 
		dataOutport.add(new DataPortParam("", "TimedLong"));
		rtcParam.setOutports(dataOutport);
		
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

//		assertEquals("foo.h", result.get(2).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.h";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(2).getCode());

//		assertEquals("foo.cpp", result.get(3).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.cpp";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(3).getCode());

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

	public void testInPortNoType() throws Exception{
		String resourceDir = "CXXWin\\Exception\\InPortNoType";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPPWIN);
		rtcParam.setName("foo");
		rtcParam.setDescription("test module");
		rtcParam.setVersion("1.0.1");
		rtcParam.setVender("TA");
		rtcParam.setCategory("sample");
		rtcParam.setComponentType("STATIC");
		rtcParam.setActivityType("PERIODIC");
		rtcParam.setMaxInstance(2);
		genParam.getRtcParams().add(rtcParam);
		
		List<DataPortParam> dataport = new ArrayList<DataPortParam>(); 
		dataport.add(new DataPortParam("in1", ""));
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

	public void testInPortNoName() throws Exception{
		String resourceDir = "CXXWin\\Exception\\InPortNoName";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPPWIN);
		rtcParam.setName("foo");
		rtcParam.setDescription("test module");
		rtcParam.setVersion("1.0.1");
		rtcParam.setVender("TA");
		rtcParam.setCategory("sample");
		rtcParam.setComponentType("STATIC");
		rtcParam.setActivityType("PERIODIC");
		rtcParam.setMaxInstance(2);
		genParam.getRtcParams().add(rtcParam);
		
		List<DataPortParam> dataport = new ArrayList<DataPortParam>(); 
		dataport.add(new DataPortParam("", "TimedShort"));
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

}
