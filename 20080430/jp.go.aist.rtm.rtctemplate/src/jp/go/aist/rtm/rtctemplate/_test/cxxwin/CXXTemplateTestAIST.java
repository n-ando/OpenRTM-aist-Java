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

public class CXXTemplateTestAIST extends TestBase {

	protected void setUp() throws Exception {
	}

	public void testAIST5() throws Exception{
		String resourceDir = "CXXWin\\AIST5";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPPWIN);
		rtcParam.setName("test");
		rtcParam.setDescription("test component");
		rtcParam.setVersion("1.0.0");
		rtcParam.setVender("S.Kurihara");
		rtcParam.setCategory("sample");
		rtcParam.setComponentType("STATIC");
		rtcParam.setActivityType("PERIODIC");
		rtcParam.setMaxInstance(1);

		genParam.addProviderIDLPath(rootPath + "resource\\CXX\\AIST5\\MyService.idl");
		genParam.getRtcParams().add(rtcParam);
		List<ServiceReferenceParam> srvproport = new ArrayList<ServiceReferenceParam>(); 
		srvproport.add(new ServiceReferenceParam("MySVPro0","myservice0","MyServiceT", rtcParam));
		srvproport.add(new ServiceReferenceParam("MySVPro1","myservice","MyServiceOpen", rtcParam));
		rtcParam.setProviderReferences(srvproport);

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);


		assertEquals("testComp.cpp", result.get(0).getName());
		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\testComp.cpp";
		String expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(0).getCode());

//		assertEquals("Makefile.test", result.get(1).getName());
//		expReadMeS = rootPath +  "\\resource\\" +resourceDir + "\\Makefile.test";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(1).getCode());

//		assertEquals("test.h", result.get(2).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\test.h";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(2).getCode());

//		assertEquals("test.cpp", result.get(3).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\test.cpp";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(3).getCode());

		assertEquals("OpenRTM-aist.vsprops", result.get(4).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\OpenRTM-aist.vsprops";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(4).getCode());

		assertEquals("test.vcproj", result.get(5).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\test.vcproj";
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
		
//		assertEquals("README.test", result.get(8).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.test";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(8).getCode());

	}

	public void testAIST4() throws Exception{
		String resourceDir = "CXXWin\\AIST4";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPPWIN);
		rtcParam.setName("test");
		rtcParam.setDescription("test component");
		rtcParam.setVersion("1.0.0");
		rtcParam.setVender("S.Kurihara");
		rtcParam.setCategory("sample");
		rtcParam.setComponentType("STATIC");
		rtcParam.setActivityType("PERIODIC");
		rtcParam.setMaxInstance(1);

		genParam.addProviderIDLPath(rootPath + "resource\\CXX\\AIST4\\MyService.idl");
		genParam.getRtcParams().add(rtcParam);
		List<ServiceReferenceParam> srvproport = new ArrayList<ServiceReferenceParam>(); 
		srvproport.add(new ServiceReferenceParam("MySVPro0","myservice0","MyService", rtcParam));
		srvproport.add(new ServiceReferenceParam("MySVPro1","myservice","MyService", rtcParam));
		rtcParam.setProviderReferences(srvproport);

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);


		assertEquals("testComp.cpp", result.get(0).getName());
		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\testComp.cpp";
		String expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(0).getCode());

//		assertEquals("Makefile.test", result.get(1).getName());
//		expReadMeS = rootPath +  "\\resource\\" +resourceDir + "\\Makefile.test";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(1).getCode());

//		assertEquals("test.h", result.get(2).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\test.h";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(2).getCode());

//		assertEquals("test.cpp", result.get(3).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\test.cpp";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(3).getCode());

		assertEquals("OpenRTM-aist.vsprops", result.get(4).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\OpenRTM-aist.vsprops";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(4).getCode());

		assertEquals("test.vcproj", result.get(5).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\test.vcproj";
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
		
//		assertEquals("README.test", result.get(8).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.test";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(8).getCode());

	}

	public void testAIST3() throws Exception{
		String resourceDir = "CXXWin\\AIST3";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPPWIN);
		rtcParam.setName("test");
		rtcParam.setDescription("test component");
		rtcParam.setVersion("1.0.0");
		rtcParam.setVender("S.Kurihara");
		rtcParam.setCategory("example");
		rtcParam.setComponentType("STATIC");
		rtcParam.setActivityType("PERIODIC");
		rtcParam.setMaxInstance(1);

		List<DataPortParam> dataport = new ArrayList<DataPortParam>(); 
		dataport.add(new DataPortParam("in", "TimedFloatSeq"));
		rtcParam.setInports(dataport);

		List<DataPortParam> dataoutport = new ArrayList<DataPortParam>(); 
		dataoutport.add(new DataPortParam("out", "TimedFloatSeq"));
		rtcParam.setOutports(dataoutport);

		genParam.addProviderIDLPath(rootPath + "resource\\CXX\\AIST3\\MyService.idl");
		genParam.getRtcParams().add(rtcParam);
		List<ServiceReferenceParam> srvproport = new ArrayList<ServiceReferenceParam>(); 
		srvproport.add(new ServiceReferenceParam("MySVPro","myservice0","MyService", rtcParam));
		rtcParam.setProviderReferences(srvproport);

		genParam.addConsumerIDLPath(rootPath + "resource\\CXX\\AIST3\\MyService.idl");
		List<ServiceReferenceParam> srvport = new ArrayList<ServiceReferenceParam>(); 
		srvport.add(new ServiceReferenceParam("MySVCon","myservice1","MyService", rtcParam));
		rtcParam.setConsumerReferences(srvport);

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);


		assertEquals("testComp.cpp", result.get(0).getName());
		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\testComp.cpp";
		String expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(0).getCode());

//		assertEquals("Makefile.test", result.get(1).getName());
//		expReadMeS = rootPath +  "\\resource\\" +resourceDir + "\\Makefile.test";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(1).getCode());

//		assertEquals("test.h", result.get(2).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\test.h";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(2).getCode());

//		assertEquals("test.cpp", result.get(3).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\test.cpp";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(3).getCode());

		assertEquals("OpenRTM-aist.vsprops", result.get(4).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\OpenRTM-aist.vsprops";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(4).getCode());

		assertEquals("test.vcproj", result.get(5).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\test.vcproj";
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
		
//		assertEquals("README.test", result.get(8).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.test";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(8).getCode());

	}
	
	public void testAIST2() throws Exception{
		String resourceDir = "CXXWin\\AIST2";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPPWIN);
		rtcParam.setName("test");
		rtcParam.setDescription("test component");
		rtcParam.setVersion("1.0.0");
		rtcParam.setVender("S.Kurihara");
		rtcParam.setCategory("example");
		rtcParam.setComponentType("STATIC");
		rtcParam.setActivityType("PERIODIC");
		rtcParam.setMaxInstance(1);

		List<DataPortParam> dataport = new ArrayList<DataPortParam>(); 
		dataport.add(new DataPortParam("in", "TimedFloatSeq"));
		rtcParam.setInports(dataport);

		List<DataPortParam> dataoutport = new ArrayList<DataPortParam>(); 
		dataoutport.add(new DataPortParam("out", "TimedFloatSeq"));
		rtcParam.setOutports(dataoutport);

		genParam.addConsumerIDLPath(rootPath + "resource\\CXX\\AIST2\\MyService.idl");
		genParam.getRtcParams().add(rtcParam);

		List<ServiceReferenceParam> srvport = new ArrayList<ServiceReferenceParam>(); 
		srvport.add(new ServiceReferenceParam("MySVCon","myservice","MyService", rtcParam));
		rtcParam.setConsumerReferences(srvport);

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		assertEquals("testComp.cpp", result.get(0).getName());
		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\testComp.cpp";
		String expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(0).getCode());

//		assertEquals("Makefile.test", result.get(1).getName());
//		expReadMeS = rootPath +  "\\resource\\" +resourceDir + "\\Makefile.test";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(1).getCode());

//		assertEquals("test.h", result.get(2).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\test.h";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(2).getCode());

//		assertEquals("test.cpp", result.get(3).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\test.cpp";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(3).getCode());

		assertEquals("OpenRTM-aist.vsprops", result.get(4).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\OpenRTM-aist.vsprops";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(4).getCode());

		assertEquals("test.vcproj", result.get(5).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\test.vcproj";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(5).getCode());

//		assertEquals("README.test", result.get(6).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.test";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(6).getCode());

	}

	public void testType() throws Exception{
		String resourceDir = "CXXWin\\type";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		genParam.addProviderIDLPath(rootPath + "resource\\CXX\\type\\MyService.idl");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPPWIN);
		rtcParam.setName("test");
		rtcParam.setDescription("test component");
		rtcParam.setVersion("1.0.0");
		rtcParam.setVender("S.Kurihara");
		rtcParam.setCategory("exmple");
		rtcParam.setComponentType("STATIC");
		rtcParam.setActivityType("PERIODIC");
		rtcParam.setMaxInstance(1);
		genParam.getRtcParams().add(rtcParam);

		List<ServiceReferenceParam> srvport = new ArrayList<ServiceReferenceParam>(); 
		srvport.add(new ServiceReferenceParam("MySVPro","myservice","MyService", rtcParam));
		rtcParam.setProviderReferences(srvport);

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		assertEquals("testComp.cpp", result.get(0).getName());
		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\testComp.cpp";
		String expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(0).getCode());

//		assertEquals("Makefile.test", result.get(1).getName());
//		expReadMeS = rootPath +  "\\resource\\" +resourceDir + "\\Makefile.test";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(1).getCode());

//		assertEquals("test.h", result.get(2).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\test.h";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(2).getCode());

//		assertEquals("test.cpp", result.get(3).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\test.cpp";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(3).getCode());

		assertEquals("OpenRTM-aist.vsprops", result.get(4).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\OpenRTM-aist.vsprops";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(4).getCode());

		assertEquals("test.vcproj", result.get(5).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\test.vcproj";
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
		
//		assertEquals("README.test", result.get(8).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.test";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(8).getCode());

	}

	public void testServicePort() throws Exception{
		String resourceDir = "\\CXXWin\\AIST1";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		genParam.addProviderIDLPath(rootPath + "resource\\CXX\\AIST1\\MyService.idl");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPPWIN);
		rtcParam.setName("test");
		rtcParam.setDescription("test component");
		rtcParam.setVersion("1.0.0");
		rtcParam.setVender("S.Kurihara");
		rtcParam.setCategory("exmple");
		rtcParam.setComponentType("STATIC");
		rtcParam.setActivityType("PERIODIC");
		rtcParam.setMaxInstance(1);
		genParam.getRtcParams().add(rtcParam);

		List<ServiceReferenceParam> srvport = new ArrayList<ServiceReferenceParam>(); 
		srvport.add(new ServiceReferenceParam("MySVPro","myservice","MyService", rtcParam));
		rtcParam.setProviderReferences(srvport);

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		assertEquals("testComp.cpp", result.get(0).getName());
		String expReadMeS = rootPath + "\\resource\\" + resourceDir + "\\testComp.cpp";
		String expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(0).getCode());

//		assertEquals("Makefile.test", result.get(1).getName());
//		expReadMeS = rootPath + "\\resource\\" + resourceDir + "\\Makefile.test";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(1).getCode());

//		assertEquals("test.h", result.get(2).getName());
//		expReadMeS = rootPath + "\\resource\\" + resourceDir + "\\test.h";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(2).getCode());

//		assertEquals("test.cpp", result.get(3).getName());
//		expReadMeS = rootPath + "\\resource\\" + resourceDir + "\\test.cpp";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(3).getCode());

		assertEquals("OpenRTM-aist.vsprops", result.get(4).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\OpenRTM-aist.vsprops";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(4).getCode());

		assertEquals("test.vcproj", result.get(5).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\test.vcproj";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(5).getCode());

		assertEquals("MyServiceSVC_impl.h", result.get(6).getName());
		expReadMeS = rootPath + "\\resource\\" + resourceDir + "\\MyServiceSVC_impl.h";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(6).getCode());

		assertEquals("MyServiceSVC_impl.cpp", result.get(7).getName());
		expReadMeS = rootPath + "\\resource\\" + resourceDir + "\\MyServiceSVC_impl.cpp";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(7).getCode());
		
//		assertEquals("README.test", result.get(8).getName());
//		expReadMeS = rootPath + "\\resource\\" + resourceDir + "\\README.test";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(8).getCode());

	}

}
