package jp.go.aist.rtm.rtctemplate._test.cxxwin;
import java.util.ArrayList;
import java.util.List;

import jp.go.aist.rtm.rtctemplate.Generator;
import jp.go.aist.rtm.rtctemplate._test.TestBase;
import jp.go.aist.rtm.rtctemplate.generator.GeneratedResult;
import jp.go.aist.rtm.rtctemplate.generator.param.GeneratorParam;
import jp.go.aist.rtm.rtctemplate.generator.param.RtcParam;
import jp.go.aist.rtm.rtctemplate.generator.param.ServiceReferenceParam;

public class CXXDiffTest extends TestBase {

	protected void setUp() throws Exception {
	}

	public void testDiffName2() throws Exception{
		String resourceDir = "CXXWin\\diffname2";
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

		genParam.addProviderIDLPath(rootPath + "\\resource\\" + resourceDir + "\\MyService3.idl");
		genParam.addProviderIDLPath(rootPath + "\\resource\\" + resourceDir + "\\DAQService.idl");
		genParam.getRtcParams().add(rtcParam);
		List<ServiceReferenceParam> srvproport = new ArrayList<ServiceReferenceParam>(); 
		srvproport.add(new ServiceReferenceParam("MySVPro0","myservice0","MyService", rtcParam));
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

		assertEquals("MyService3SVC_impl.h", result.get(6).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\MyService3SVC_impl.h";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(6).getCode());

		assertEquals("MyService3SVC_impl.cpp", result.get(7).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\MyService3SVC_impl.cpp";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(7).getCode());
		
		assertEquals("DAQServiceSVC_impl.h", result.get(8).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\DAQServiceSVC_impl.h";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(8).getCode());

		assertEquals("DAQServiceSVC_impl.cpp", result.get(9).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\DAQServiceSVC_impl.cpp";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(9).getCode());

//		assertEquals("README.test", result.get(10).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.test";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(10).getCode());

	}

	public void testDiffName() throws Exception{
		String resourceDir = "CXXWin\\diffname";
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

		genParam.addProviderIDLPath(rootPath + "resource\\MyService3.idl");
		genParam.getRtcParams().add(rtcParam);
		List<ServiceReferenceParam> srvproport = new ArrayList<ServiceReferenceParam>(); 
		srvproport.add(new ServiceReferenceParam("MySVPro0","myservice0","MyService", rtcParam));
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

		assertEquals("MyService3SVC_impl.h", result.get(6).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\MyService3SVC_impl.h";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(6).getCode());

		assertEquals("MyService3SVC_impl.cpp", result.get(7).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\MyService3SVC_impl.cpp";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(7).getCode());
		
//		assertEquals("README.test", result.get(8).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.test";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(8).getCode());

	}
}
