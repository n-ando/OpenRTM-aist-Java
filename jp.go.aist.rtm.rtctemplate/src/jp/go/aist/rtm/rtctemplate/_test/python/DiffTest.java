package jp.go.aist.rtm.rtctemplate._test.python;

import java.util.ArrayList;
import java.util.List;

import jp.go.aist.rtm.rtctemplate.Generator;
import jp.go.aist.rtm.rtctemplate._test.TestBase;
import jp.go.aist.rtm.rtctemplate.generator.GeneratedResult;
import jp.go.aist.rtm.rtctemplate.generator.param.GeneratorParam;
import jp.go.aist.rtm.rtctemplate.generator.param.RtcParam;
import jp.go.aist.rtm.rtctemplate.generator.param.ServiceReferenceParam;

public class DiffTest extends TestBase {

	protected void setUp() throws Exception {
	}

	public void testDiffName2() throws Exception{
		String resourceDir = "Python\\diffname2";
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

		assertEquals("test.py", result.get(0).getName());
		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\test.py";
		String expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(0).getCode());

		assertEquals("MyService3_idl.py", result.get(1).getName());
		expReadMeS = rootPath +  "\\resource\\" +resourceDir + "\\MyService3_idl.py";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(1).getCode());

		assertEquals("DAQService_idl.py", result.get(2).getName());
		expReadMeS = rootPath +  "\\resource\\" +resourceDir + "\\DAQService_idl.py";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(2).getCode());

		assertEquals("\\_GlobalIDL\\__init__.py", result.get(3).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\_GlobalIDL\\__init__.py";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(3).getCode());
		
		assertEquals("\\_GlobalIDL__POA\\__init__.py", result.get(4).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\_GlobalIDL__POA\\__init__.py";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(4).getCode());

		assertEquals("MyService3_idl_example.py", result.get(5).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\MyService3_idl_example.py";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(5).getCode());

		assertEquals("DAQService_idl_example.py", result.get(6).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\DAQService_idl_example.py";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(6).getCode());

		assertEquals("README.test", result.get(7).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.test";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(7).getCode());
	}

	public void testDiffName() throws Exception{
		String resourceDir = "Python\\diffname";
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

		genParam.addProviderIDLPath(rootPath + "resource\\Python\\diffname\\MyService3.idl");
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));
		genParam.getRtcParams().add(rtcParam);
		List<ServiceReferenceParam> srvproport = new ArrayList<ServiceReferenceParam>(); 
		srvproport.add(new ServiceReferenceParam("MySVPro0","myservice0","MyService", rtcParam));
		rtcParam.setProviderReferences(srvproport);

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);


		assertEquals("test.py", result.get(0).getName());
		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\test.py";
		String expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(0).getCode());

		assertEquals("MyService3_idl.py", result.get(1).getName());
		expReadMeS = rootPath +  "\\resource\\" +resourceDir + "\\MyService3_idl.py";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(1).getCode());

		assertEquals("\\_GlobalIDL\\__init__.py", result.get(2).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\_GlobalIDL\\__init__.py";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(2).getCode());
		
		assertEquals("\\_GlobalIDL__POA\\__init__.py", result.get(3).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\_GlobalIDL__POA\\__init__.py";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(3).getCode());

		assertEquals("MyService3_idl_example.py", result.get(4).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\MyService3_idl_example.py";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(4).getCode());

		assertEquals("README.test", result.get(5).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.test";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(5).getCode());
	}
}
