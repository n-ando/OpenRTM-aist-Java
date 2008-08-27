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

public class CXXTemplateTestAIST2 extends TestBase {

	protected void setUp() throws Exception {
	}

	public void testAISTmodule() throws Exception{
		String resourceDir = "042\\CXX\\module";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPP);
		rtcParam.setName("ModuleName");
		rtcParam.setDescription("ModuleDescription");
		rtcParam.setVersion("1.0.0");
		rtcParam.setVender("VenderName");
		rtcParam.setCategory("Category");
		rtcParam.setComponentType("DataFlowComponent");
		rtcParam.setActivityType("PERIODIC");
		rtcParam.setMaxInstance(1);
		rtcParam.setRtmVersion("0.4.2");
		rtcParam.setIsTest(true);

		genParam.addProviderIDLPath(rootPath + "resource\\042\\CXX\\module\\MyService.idl");
		genParam.getRtcParams().add(rtcParam);
		List<ServiceReferenceParam> srvproport = new ArrayList<ServiceReferenceParam>(); 
		srvproport.add(new ServiceReferenceParam("portName","name","Test::MyService", rtcParam));
		rtcParam.setProviderReferences(srvproport);
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		String targetDir = rootPath + "\\resource\\042\\CXX\\module\\";
		checkCode(result, targetDir, "ModuleNameComp.cpp");
		checkCode(result, targetDir, "Makefile.ModuleName");
		checkCode(result, targetDir, "ModuleName.h");
		checkCode(result, targetDir, "ModuleName.cpp");
		checkCode(result, targetDir, "MyServiceSVC_impl.h");
		checkCode(result, targetDir, "MyServiceSVC_impl.cpp");
		checkCode(result, targetDir, "README.ModuleName");
		//
		checkCode(result, targetDir, "ModuleName_vc8.sln");
		checkCode(result, targetDir, "ModuleName_vc8.vcproj");
		checkCode(result, targetDir, "ModuleName_vc9.sln");
		checkCode(result, targetDir, "ModuleName_vc9.vcproj");
		checkCode(result, targetDir, "ModuleNameComp_vc8.vcproj");
		checkCode(result, targetDir, "ModuleNameComp_vc9.vcproj");
		//
		checkCode(result, targetDir, "copyprops.bat");
		checkCode(result, targetDir, "user_config.vsprops");
	}
}
