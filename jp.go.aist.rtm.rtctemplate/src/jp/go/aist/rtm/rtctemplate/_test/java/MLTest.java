package jp.go.aist.rtm.rtctemplate._test.java;

import java.util.ArrayList;
import java.util.List;

import jp.go.aist.rtm.rtctemplate.Generator;
import jp.go.aist.rtm.rtctemplate._test.TestBase;
import jp.go.aist.rtm.rtctemplate.generator.GeneratedResult;
import jp.go.aist.rtm.rtctemplate.generator.param.GeneratorParam;
import jp.go.aist.rtm.rtctemplate.generator.param.RtcParam;
import jp.go.aist.rtm.rtctemplate.generator.param.ServiceReferenceParam;

public class MLTest extends TestBase {

	protected void setUp() throws Exception {
	}

	public void testML3() throws Exception{
		String resourceDir = "\\Java\\ML3";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		genParam.addProviderIDLPath(rootPath + "resource\\Java\\ML3\\MyServiceType1.idl");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(RtcParam.LANG_JAVA);
		rtcParam.setName("ModuleName");
		rtcParam.setDescription("ModuleDescription");
		rtcParam.setVersion("1.0.0");
		rtcParam.setVender("VenderName");
		rtcParam.setCategory("Category");
		rtcParam.setComponentType("DataFlowComponent");
		rtcParam.setActivityType("PERIODIC");
		rtcParam.setMaxInstance(1);
		genParam.getRtcParams().add(rtcParam);

		List<ServiceReferenceParam> srvport = new ArrayList<ServiceReferenceParam>(); 
		srvport.add(new ServiceReferenceParam("portName","name","MyService", rtcParam));
		rtcParam.setProviderReferences(srvport);
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		String targetDir = rootPath + "\\resource\\Java\\ML3\\";
		checkCode(result, targetDir, "ModuleNameComp.java");
		checkCode(result, targetDir, "build_ModuleName.xml");
		checkCode(result, targetDir, "ModuleName.java");
		checkCode(result, targetDir, "ModuleNameImpl.java");
		checkCode(result, targetDir, "MyServiceSVC_impl.java");
		checkCode(result, targetDir, "README.ModuleName");
	}

	public void testML2() throws Exception{
		String resourceDir = "\\Java\\ML2";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		genParam.addProviderIDLPath(rootPath + "resource\\Java\\ML2\\MyServiceType1.idl");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(RtcParam.LANG_JAVA);
		rtcParam.setName("ModuleName");
		rtcParam.setDescription("ModuleDescription");
		rtcParam.setVersion("1.0.0");
		rtcParam.setVender("VenderName");
		rtcParam.setCategory("Category");
		rtcParam.setComponentType("DataFlowComponent");
		rtcParam.setActivityType("PERIODIC");
		rtcParam.setMaxInstance(1);
		genParam.getRtcParams().add(rtcParam);

		List<ServiceReferenceParam> srvport = new ArrayList<ServiceReferenceParam>(); 
		srvport.add(new ServiceReferenceParam("portName","name","MyService", rtcParam));
		rtcParam.setProviderReferences(srvport);
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		String targetDir = rootPath + "\\resource\\Java\\ML2\\";
		checkCode(result, targetDir, "ModuleNameComp.java");
		checkCode(result, targetDir, "build_ModuleName.xml");
		checkCode(result, targetDir, "ModuleName.java");
		checkCode(result, targetDir, "ModuleNameImpl.java");
		checkCode(result, targetDir, "MyServiceSVC_impl.java");
		checkCode(result, targetDir, "README.ModuleName");
	}

	public void testML1() throws Exception{
		String resourceDir = "\\Java\\ML1";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		genParam.addProviderIDLPath(rootPath + "resource\\Java\\ML1\\MyServiceAIST2.idl");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(RtcParam.LANG_JAVA);
		rtcParam.setName("ModuleName");
		rtcParam.setDescription("ModuleDescription");
		rtcParam.setVersion("1.0.0");
		rtcParam.setVender("VenderName");
		rtcParam.setCategory("Category");
		rtcParam.setComponentType("DataFlowComponent");
		rtcParam.setActivityType("PERIODIC");
		rtcParam.setMaxInstance(1);
		genParam.getRtcParams().add(rtcParam);

		List<ServiceReferenceParam> srvport = new ArrayList<ServiceReferenceParam>(); 
		srvport.add(new ServiceReferenceParam("portName","name","MyService", rtcParam));
		rtcParam.setProviderReferences(srvport);
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		String targetDir = rootPath + "\\resource\\Java\\ML1\\";
		checkCode(result, targetDir, "ModuleNameComp.java");
		checkCode(result, targetDir, "build_ModuleName.xml");
		checkCode(result, targetDir, "ModuleName.java");
		checkCode(result, targetDir, "ModuleNameImpl.java");
		checkCode(result, targetDir, "MyServiceSVC_impl.java");
		checkCode(result, targetDir, "README.ModuleName");
	}
}
