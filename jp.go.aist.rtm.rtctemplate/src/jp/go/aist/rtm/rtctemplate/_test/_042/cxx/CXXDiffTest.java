package jp.go.aist.rtm.rtctemplate._test._042.cxx;

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
		String resourceDir = "042\\CXX\\diffname2";
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

		genParam.addProviderIDLPath(rootPath + "\\resource\\" + resourceDir + "\\MyService3.idl");
		genParam.addProviderIDLPath(rootPath + "\\resource\\" + resourceDir + "\\DAQService.idl");
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));
		genParam.getRtcParams().add(rtcParam);
		List<ServiceReferenceParam> srvproport = new ArrayList<ServiceReferenceParam>(); 
		srvproport.add(new ServiceReferenceParam("MySVPro0","myservice0","MyService", rtcParam));
		rtcParam.setProviderReferences(srvproport);

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		String targetDir = rootPath + "\\resource\\042\\CXX\\diffname2\\";
		checkCode(result, targetDir, "testComp.cpp");
		checkCode(result, targetDir, "Makefile.test");
		checkCode(result, targetDir, "test.h");
		checkCode(result, targetDir, "test.cpp");
		checkCode(result, targetDir, "MyService3SVC_impl.h");
		checkCode(result, targetDir, "MyService3SVC_impl.cpp");
		checkCode(result, targetDir, "DAQServiceSVC_impl.h");
		checkCode(result, targetDir, "DAQServiceSVC_impl.cpp");
		checkCode(result, targetDir, "README.test");
		//
		checkCode(result, targetDir, "copyprops.bat");
		checkCode(result, targetDir, "user_config.vsprops");
	}

	public void testDiffName() throws Exception{
		String resourceDir = "042\\CXX\\diffname";
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

		genParam.addProviderIDLPath(rootPath + "resource\\042\\CXX\\diffname\\MyService3.idl");
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));
		genParam.getRtcParams().add(rtcParam);
		List<ServiceReferenceParam> srvproport = new ArrayList<ServiceReferenceParam>(); 
		srvproport.add(new ServiceReferenceParam("MySVPro0","myservice0","MyService", rtcParam));
		rtcParam.setProviderReferences(srvproport);

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		String targetDir = rootPath + "\\resource\\042\\CXX\\diffname\\";
		checkCode(result, targetDir, "testComp.cpp");
		checkCode(result, targetDir, "Makefile.test");
		checkCode(result, targetDir, "test.h");
		checkCode(result, targetDir, "test.cpp");
		checkCode(result, targetDir, "MyService3SVC_impl.h");
		checkCode(result, targetDir, "MyService3SVC_impl.cpp");
		checkCode(result, targetDir, "README.test");
		//
		checkCode(result, targetDir, "copyprops.bat");
		checkCode(result, targetDir, "user_config.vsprops");
	}
}
