package jp.go.aist.rtm.rtctemplate._test.python;

import java.util.ArrayList;
import java.util.List;

import jp.go.aist.rtm.rtctemplate.Generator;
import jp.go.aist.rtm.rtctemplate._test.TestBase;
import jp.go.aist.rtm.rtctemplate.generator.GeneratedResult;
import jp.go.aist.rtm.rtctemplate.generator.param.DataPortParam;
import jp.go.aist.rtm.rtctemplate.generator.param.GeneratorParam;
import jp.go.aist.rtm.rtctemplate.generator.param.RtcParam;
import jp.go.aist.rtm.rtctemplate.generator.param.ServiceReferenceParam;

public class AISTTest extends TestBase {

	protected void setUp() throws Exception {
	}

	public void testAIST6() throws Exception{
		String resourceDir = "Python\\AIST6";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_PYTHON);
		rtcParam.setName("test");
		rtcParam.setDescription("test component");
		rtcParam.setVersion("1.0.0");
		rtcParam.setVender("S.Kurihara");
		rtcParam.setCategory("sample");
		rtcParam.setComponentType("STATIC");
		rtcParam.setActivityType("PERIODIC");
		rtcParam.setMaxInstance(1);

		genParam.addProviderIDLPath(rootPath + "resource\\Python\\AIST6\\work\\MyService.idl");
		genParam.addProviderIDLPath(rootPath + "resource\\Python\\AIST6\\work\\MyServiceAIST.idl");
		genParam.getRtcParams().add(rtcParam);
		List<ServiceReferenceParam> srvproport = new ArrayList<ServiceReferenceParam>(); 
		srvproport.add(new ServiceReferenceParam("MySVPro0","myservice0","MyService", rtcParam));
		srvproport.add(new ServiceReferenceParam("MySVPro1","myservice","MyService2", rtcParam));
		rtcParam.setProviderReferences(srvproport);
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);
		//
		genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		rtcParam.getProviderReferences().clear();
		rtcParam.getConsumerReferences().clear();
		genParam.getRtcParams().add(rtcParam);
		generator = new Generator();
		result = generator.doGenerate(genParam);

		String targetDir = rootPath +  "\\resource\\" + resourceDir + "\\";
		checkCode(result, targetDir, "test.py");
		checkCode(result, targetDir, "README.test");
	}

//	public void testAIST5() throws Exception{
//		String resourceDir = "Python\\AIST5";
//		GeneratorParam genParam = new GeneratorParam();
//		genParam.setOutputDirectory(rootPath + "\\resource\\work");
//		RtcParam rtcParam = new RtcParam(genParam);
//		rtcParam.setLanguage(rtcParam.LANG_PYTHON);
//		rtcParam.setName("test");
//		rtcParam.setDescription("test component");
//		rtcParam.setVersion("1.0.0");
//		rtcParam.setVender("S.Kurihara");
//		rtcParam.setCategory("sample");
//		rtcParam.setComponentType("STATIC");
//		rtcParam.setActivityType("PERIODIC");
//		rtcParam.setMaxInstance(1);
//
//		genParam.addProviderIDLPath(rootPath + "resource\\Python\\AIST5\\MyService.idl");
//		genParam.getRtcParams().add(rtcParam);
//		List<ServiceReferenceParam> srvproport = new ArrayList<ServiceReferenceParam>(); 
//		srvproport.add(new ServiceReferenceParam("MySVPro0","myservice0","MyServiceT", rtcParam));
//		srvproport.add(new ServiceReferenceParam("MySVPro1","myservice","MyServiceOpen", rtcParam));
//		rtcParam.setProviderReferences(srvproport);
//		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
//		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));
//
//		Generator generator = new Generator();
//		List<GeneratedResult> result = generator.doGenerate(genParam);
//
//
//		assertEquals("test.py", result.get(0).getName());
//		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\test.py";
//		String expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(0).getCode());
//
//		assertEquals("MyService_idl.py", result.get(1).getName());
//		expReadMeS = rootPath +  "\\resource\\" +resourceDir + "\\MyService_idl.py";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(1).getCode());
//
//		assertEquals("\\_GlobalIDL\\__init__.py", result.get(2).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\_GlobalIDL\\__init__.py";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(2).getCode());
//		
//		assertEquals("\\_GlobalIDL__POA\\__init__.py", result.get(3).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\_GlobalIDL__POA\\__init__.py";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(3).getCode());
//
//		assertEquals("MyService_idl_example.py", result.get(4).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\MyService_idl_example.py";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(4).getCode());
//
//		assertEquals("README.test", result.get(5).getName());
//		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.test";
//		expReadMe = readFile(expReadMeS);
//		assertEquals(expReadMe, result.get(5).getCode());
//	}

	public void testAIST4() throws Exception{
		String resourceDir = "Python\\AIST4";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_PYTHON);
		rtcParam.setName("test");
		rtcParam.setDescription("test component");
		rtcParam.setVersion("1.0.0");
		rtcParam.setVender("S.Kurihara");
		rtcParam.setCategory("sample");
		rtcParam.setComponentType("STATIC");
		rtcParam.setActivityType("PERIODIC");
		rtcParam.setMaxInstance(1);

		genParam.addProviderIDLPath(rootPath + "resource\\Python\\AIST4\\MyService.idl");
		genParam.getRtcParams().add(rtcParam);
		List<ServiceReferenceParam> srvproport = new ArrayList<ServiceReferenceParam>(); 
		srvproport.add(new ServiceReferenceParam("MySVPro0","myservice0","MyService", rtcParam));
		srvproport.add(new ServiceReferenceParam("MySVPro1","myservice","MyService", rtcParam));
		rtcParam.setProviderReferences(srvproport);
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		String targetDir = rootPath +  "\\resource\\" + resourceDir + "\\";
		checkCode(result, targetDir, "test.py");
		checkCode(result, targetDir, "MyService_idl.py");
		checkCode(result, targetDir, "\\_GlobalIDL\\__init__.py");
		checkCode(result, targetDir, "\\_GlobalIDL__POA\\__init__.py");
		checkCode(result, targetDir, "MyService_idl_example.py");
		checkCode(result, targetDir, "README.test");
	}

	public void testAIST3() throws Exception{
		String resourceDir = "Python\\AIST3";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_PYTHON);
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

		genParam.addProviderIDLPath(rootPath + "resource\\Python\\AIST3\\MyService.idl");
		genParam.getRtcParams().add(rtcParam);
		List<ServiceReferenceParam> srvproport = new ArrayList<ServiceReferenceParam>(); 
		srvproport.add(new ServiceReferenceParam("MySVPro","myservice0","MyService", rtcParam));
		rtcParam.setProviderReferences(srvproport);

		genParam.addConsumerIDLPath(rootPath + "resource\\Python\\AIST3\\MyService.idl");
		List<ServiceReferenceParam> srvport = new ArrayList<ServiceReferenceParam>(); 
		srvport.add(new ServiceReferenceParam("MySVCon","myservice1","MyService", rtcParam));
		rtcParam.setConsumerReferences(srvport);
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		String targetDir = rootPath +  "\\resource\\" + resourceDir + "\\";
		checkCode(result, targetDir, "test.py");
		checkCode(result, targetDir, "MyService_idl.py");
		checkCode(result, targetDir, "\\_GlobalIDL\\__init__.py");
		checkCode(result, targetDir, "\\_GlobalIDL__POA\\__init__.py");
		checkCode(result, targetDir, "MyService_idl_example.py");
		checkCode(result, targetDir, "README.test");
	}
	
	public void testAIST2() throws Exception{
		String resourceDir = "Python\\AIST2";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_PYTHON);
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

		genParam.addConsumerIDLPath(rootPath + "resource\\Python\\AIST2\\MyService.idl");
		genParam.getRtcParams().add(rtcParam);

		List<ServiceReferenceParam> srvport = new ArrayList<ServiceReferenceParam>(); 
		srvport.add(new ServiceReferenceParam("MySVCon","myservice","MyService", rtcParam));
		rtcParam.setConsumerReferences(srvport);
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		String targetDir = rootPath +  "\\resource\\" + resourceDir + "\\";
		checkCode(result, targetDir, "test.py");
		checkCode(result, targetDir, "MyService_idl.py");
		checkCode(result, targetDir, "\\_GlobalIDL\\__init__.py");
		checkCode(result, targetDir, "\\_GlobalIDL__POA\\__init__.py");
		checkCode(result, targetDir, "README.test");
	}

	public void testType2() throws Exception{
		String resourceDir = "Python\\type2";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		genParam.addProviderIDLPath(rootPath + "resource\\Python\\type2\\MyService.idl");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_PYTHON);
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
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		String targetDir = rootPath +  "\\resource\\" + resourceDir + "\\";
		checkCode(result, targetDir, "test.py");
		checkCode(result, targetDir, "MyService_idl.py");
		checkCode(result, targetDir, "\\_GlobalIDL\\__init__.py");
		checkCode(result, targetDir, "\\_GlobalIDL__POA\\__init__.py");
		checkCode(result, targetDir, "MyService_idl_example.py");
		checkCode(result, targetDir, "README.test");
	}

	public void testType() throws Exception{
		String resourceDir = "Python\\type";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		genParam.addProviderIDLPath(rootPath + "resource\\Python\\type\\MyService.idl");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_PYTHON);
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
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		String targetDir = rootPath +  "\\resource\\" + resourceDir + "\\";
		checkCode(result, targetDir, "test.py");
		checkCode(result, targetDir, "MyService_idl.py");
		checkCode(result, targetDir, "\\_GlobalIDL\\__init__.py");
		checkCode(result, targetDir, "\\_GlobalIDL__POA\\__init__.py");
		checkCode(result, targetDir, "MyService_idl_example.py");
		checkCode(result, targetDir, "README.test");
	}

	public void testServicePort() throws Exception{
		String resourceDir = "\\Python\\AIST1";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		genParam.addProviderIDLPath(rootPath + "resource\\Python\\AIST1\\MyService.idl");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_PYTHON);
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
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		String targetDir = rootPath +  "\\resource\\" + resourceDir + "\\";
		checkCode(result, targetDir, "test.py");
		checkCode(result, targetDir, "MyService_idl.py");
		checkCode(result, targetDir, "\\_GlobalIDL\\__init__.py");
		checkCode(result, targetDir, "\\_GlobalIDL__POA\\__init__.py");
		checkCode(result, targetDir, "MyService_idl_example.py");
		checkCode(result, targetDir, "README.test");
	}
}
