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

public class CXXTemplateTestAIST extends TestBase {

	protected void setUp() throws Exception {
	}

	public void testAIST5() throws Exception{
		String resourceDir = "042\\CXX\\AIST5";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPP);
		rtcParam.setName("test");
		rtcParam.setDescription("test component");
		rtcParam.setVersion("1.0.0");
		rtcParam.setVender("S.Kurihara");
		rtcParam.setCategory("sample");
		rtcParam.setComponentType("STATIC");
		rtcParam.setActivityType("PERIODIC");
		rtcParam.setMaxInstance(1);
		rtcParam.setRtmVersion("0.4.2");
		rtcParam.setIsTest(true);

		genParam.addProviderIDLPath(rootPath + "resource\\042\\CXX\\AIST5\\MyService.idl");
		genParam.getRtcParams().add(rtcParam);
		List<ServiceReferenceParam> srvproport = new ArrayList<ServiceReferenceParam>(); 
		srvproport.add(new ServiceReferenceParam("MySVPro0","myservice0","MyServiceT", rtcParam));
		srvproport.add(new ServiceReferenceParam("MySVPro1","myservice","MyServiceOpen", rtcParam));
		rtcParam.setProviderReferences(srvproport);
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		String targetDir = rootPath + "\\resource\\042\\CXX\\AIST5\\";
		checkCode(result, targetDir, "testComp.cpp");
		checkCode(result, targetDir, "Makefile.test");
		checkCode(result, targetDir, "test.h");
		checkCode(result, targetDir, "test.cpp");
		checkCode(result, targetDir, "MyServiceSVC_impl.h");
		checkCode(result, targetDir, "MyServiceSVC_impl.cpp");
		checkCode(result, targetDir, "README.test");
		//
		checkCode(result, targetDir, "test_vc8.sln");
		checkCode(result, targetDir, "test_vc8.vcproj");
		checkCode(result, targetDir, "test_vc9.sln");
		checkCode(result, targetDir, "test_vc9.vcproj");
		checkCode(result, targetDir, "testComp_vc8.vcproj");
		checkCode(result, targetDir, "testComp_vc9.vcproj");
		//
		checkCode(result, targetDir, "copyprops.bat");
		checkCode(result, targetDir, "user_config.vsprops");
	}

	public void testAIST4() throws Exception{
		String resourceDir = "042\\CXX\\AIST4";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPP);
		rtcParam.setName("test");
		rtcParam.setDescription("test component");
		rtcParam.setVersion("1.0.0");
		rtcParam.setVender("S.Kurihara");
		rtcParam.setCategory("sample");
		rtcParam.setComponentType("STATIC");
		rtcParam.setActivityType("PERIODIC");
		rtcParam.setMaxInstance(1);
		rtcParam.setRtmVersion("0.4.2");
		rtcParam.setIsTest(true);

		genParam.addProviderIDLPath(rootPath + "resource\\042\\CXX\\AIST4\\MyService.idl");
		genParam.getRtcParams().add(rtcParam);
		List<ServiceReferenceParam> srvproport = new ArrayList<ServiceReferenceParam>(); 
		srvproport.add(new ServiceReferenceParam("MySVPro0","myservice0","MyService", rtcParam));
		srvproport.add(new ServiceReferenceParam("MySVPro1","myservice","MyService", rtcParam));
		rtcParam.setProviderReferences(srvproport);
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		String targetDir = rootPath + "\\resource\\042\\CXX\\AIST4\\";
		checkCode(result, targetDir, "testComp.cpp");
		checkCode(result, targetDir, "Makefile.test");
		checkCode(result, targetDir, "test.h");
		checkCode(result, targetDir, "test.cpp");
		checkCode(result, targetDir, "MyServiceSVC_impl.h");
		checkCode(result, targetDir, "MyServiceSVC_impl.cpp");
		checkCode(result, targetDir, "README.test");
		//
		checkCode(result, targetDir, "test_vc8.sln");
		checkCode(result, targetDir, "test_vc8.vcproj");
		checkCode(result, targetDir, "test_vc9.sln");
		checkCode(result, targetDir, "test_vc9.vcproj");
		checkCode(result, targetDir, "testComp_vc8.vcproj");
		checkCode(result, targetDir, "testComp_vc9.vcproj");
		//
		checkCode(result, targetDir, "copyprops.bat");
		checkCode(result, targetDir, "user_config.vsprops");
	}

	public void testAIST3() throws Exception{
		String resourceDir = "042\\CXX\\AIST3";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPP);
		rtcParam.setName("test");
		rtcParam.setDescription("test component");
		rtcParam.setVersion("1.0.0");
		rtcParam.setVender("S.Kurihara");
		rtcParam.setCategory("example");
		rtcParam.setComponentType("STATIC");
		rtcParam.setActivityType("PERIODIC");
		rtcParam.setMaxInstance(1);
		rtcParam.setRtmVersion("0.4.2");
		rtcParam.setIsTest(true);

		List<DataPortParam> dataport = new ArrayList<DataPortParam>(); 
		dataport.add(new DataPortParam("in", "TimedFloatSeq"));
		rtcParam.setInports(dataport);

		List<DataPortParam> dataoutport = new ArrayList<DataPortParam>(); 
		dataoutport.add(new DataPortParam("out", "TimedFloatSeq"));
		rtcParam.setOutports(dataoutport);

		genParam.addProviderIDLPath(rootPath + "resource\\042\\CXX\\AIST3\\MyService.idl");
		genParam.getRtcParams().add(rtcParam);
		List<ServiceReferenceParam> srvproport = new ArrayList<ServiceReferenceParam>(); 
		srvproport.add(new ServiceReferenceParam("MySVPro","myservice0","MyService", rtcParam));
		rtcParam.setProviderReferences(srvproport);

		genParam.addConsumerIDLPath(rootPath + "resource\\042\\CXX\\AIST3\\MyService.idl");
		List<ServiceReferenceParam> srvport = new ArrayList<ServiceReferenceParam>(); 
		srvport.add(new ServiceReferenceParam("MySVCon","myservice1","MyService", rtcParam));
		rtcParam.setConsumerReferences(srvport);
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		String targetDir = rootPath + "\\resource\\042\\CXX\\AIST3\\";
		checkCode(result, targetDir, "testComp.cpp");
		checkCode(result, targetDir, "Makefile.test");
		checkCode(result, targetDir, "test.h");
		checkCode(result, targetDir, "test.cpp");
		checkCode(result, targetDir, "MyServiceSVC_impl.h");
		checkCode(result, targetDir, "MyServiceSVC_impl.cpp");
		checkCode(result, targetDir, "README.test");
		//
		checkCode(result, targetDir, "test_vc8.sln");
		checkCode(result, targetDir, "test_vc8.vcproj");
		checkCode(result, targetDir, "test_vc9.sln");
		checkCode(result, targetDir, "test_vc9.vcproj");
		checkCode(result, targetDir, "testComp_vc8.vcproj");
		checkCode(result, targetDir, "testComp_vc9.vcproj");
		//
		checkCode(result, targetDir, "copyprops.bat");
		checkCode(result, targetDir, "user_config.vsprops");
	}
	
	public void testAIST2() throws Exception{
		String resourceDir = "042\\CXX\\AIST2";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPP);
		rtcParam.setName("test");
		rtcParam.setDescription("test component");
		rtcParam.setVersion("1.0.0");
		rtcParam.setVender("S.Kurihara");
		rtcParam.setCategory("example");
		rtcParam.setComponentType("STATIC");
		rtcParam.setActivityType("PERIODIC");
		rtcParam.setMaxInstance(1);
		rtcParam.setRtmVersion("0.4.2");
		rtcParam.setIsTest(true);

		List<DataPortParam> dataport = new ArrayList<DataPortParam>(); 
		dataport.add(new DataPortParam("in", "TimedFloatSeq"));
		rtcParam.setInports(dataport);

		List<DataPortParam> dataoutport = new ArrayList<DataPortParam>(); 
		dataoutport.add(new DataPortParam("out", "TimedFloatSeq"));
		rtcParam.setOutports(dataoutport);

		genParam.addConsumerIDLPath(rootPath + "resource\\042\\CXX\\AIST2\\MyService.idl");
		genParam.getRtcParams().add(rtcParam);

		List<ServiceReferenceParam> srvport = new ArrayList<ServiceReferenceParam>(); 
		srvport.add(new ServiceReferenceParam("MySVCon","myservice","MyService", rtcParam));
		rtcParam.setConsumerReferences(srvport);
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		String targetDir = rootPath + "\\resource\\042\\CXX\\AIST2\\";
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
		//
		checkCode(result, targetDir, "copyprops.bat");
		checkCode(result, targetDir, "user_config.vsprops");
	}

	public void testType() throws Exception{
		String resourceDir = "042\\CXX\\type";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		genParam.addProviderIDLPath(rootPath + "resource\\042\\CXX\\type\\MyService.idl");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPP);
		rtcParam.setName("test");
		rtcParam.setDescription("test component");
		rtcParam.setVersion("1.0.0");
		rtcParam.setVender("S.Kurihara");
		rtcParam.setCategory("exmple");
		rtcParam.setComponentType("STATIC");
		rtcParam.setActivityType("PERIODIC");
		rtcParam.setMaxInstance(1);
		rtcParam.setRtmVersion("0.4.2");
		rtcParam.setIsTest(true);
		genParam.getRtcParams().add(rtcParam);

		List<ServiceReferenceParam> srvport = new ArrayList<ServiceReferenceParam>(); 
		srvport.add(new ServiceReferenceParam("MySVPro","myservice","MyService", rtcParam));
		rtcParam.setProviderReferences(srvport);
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		String targetDir = rootPath + "\\resource\\042\\CXX\\type\\";
		checkCode(result, targetDir, "testComp.cpp");
		checkCode(result, targetDir, "Makefile.test");
		checkCode(result, targetDir, "test.h");
		checkCode(result, targetDir, "test.cpp");
		checkCode(result, targetDir, "MyServiceSVC_impl.h");
		checkCode(result, targetDir, "MyServiceSVC_impl.cpp");
		checkCode(result, targetDir, "README.test");
		//
		checkCode(result, targetDir, "test_vc8.sln");
		checkCode(result, targetDir, "test_vc8.vcproj");
		checkCode(result, targetDir, "test_vc9.sln");
		checkCode(result, targetDir, "test_vc9.vcproj");
		checkCode(result, targetDir, "testComp_vc8.vcproj");
		checkCode(result, targetDir, "testComp_vc9.vcproj");
		//
		checkCode(result, targetDir, "copyprops.bat");
		checkCode(result, targetDir, "user_config.vsprops");
	}

	public void testServicePort() throws Exception{
		String resourceDir = "\\042\\CXX\\AIST1";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		genParam.addProviderIDLPath(rootPath + "resource\\042\\CXX\\AIST1\\MyService.idl");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPP);
		rtcParam.setName("test");
		rtcParam.setDescription("test component");
		rtcParam.setVersion("1.0.0");
		rtcParam.setVender("S.Kurihara");
		rtcParam.setCategory("exmple");
		rtcParam.setComponentType("STATIC");
		rtcParam.setActivityType("PERIODIC");
		rtcParam.setMaxInstance(1);
		rtcParam.setRtmVersion("0.4.2");
		rtcParam.setIsTest(true);
		genParam.getRtcParams().add(rtcParam);

		List<ServiceReferenceParam> srvport = new ArrayList<ServiceReferenceParam>(); 
		srvport.add(new ServiceReferenceParam("MySVPro","myservice","MyService", rtcParam));
		rtcParam.setProviderReferences(srvport);
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		String targetDir = rootPath + "\\resource\\042\\CXX\\AIST1\\";
		checkCode(result, targetDir, "testComp.cpp");
		checkCode(result, targetDir, "Makefile.test");
		checkCode(result, targetDir, "test.h");
		checkCode(result, targetDir, "test.cpp");
		checkCode(result, targetDir, "MyServiceSVC_impl.h");
		checkCode(result, targetDir, "MyServiceSVC_impl.cpp");
		checkCode(result, targetDir, "README.test");
		//
		checkCode(result, targetDir, "test_vc8.sln");
		checkCode(result, targetDir, "test_vc8.vcproj");
		checkCode(result, targetDir, "test_vc9.sln");
		checkCode(result, targetDir, "test_vc9.vcproj");
		checkCode(result, targetDir, "testComp_vc8.vcproj");
		checkCode(result, targetDir, "testComp_vc9.vcproj");
		//
		checkCode(result, targetDir, "copyprops.bat");
		checkCode(result, targetDir, "user_config.vsprops");
	}
}
