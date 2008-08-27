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

public class MultiTest extends TestBase {

	protected void setUp() throws Exception {
	}

	public void testProConMulti() throws Exception{
		String resourceDir = "Python\\Multi\\ProConMulti";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_PYTHON);
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
		proport.add(new ServiceReferenceParam("MySVPro","myserviceP1","MyService", rtcParam));
		proport.add(new ServiceReferenceParam("MySVPro2","myserviceP2","MyService2", rtcParam));
		rtcParam.setProviderReferences(proport);
		genParam.addProviderIDLPath(rootPath + "\\resource\\Python\\Multi\\ProConMulti\\MyService.idl");
		genParam.addProviderIDLPath(rootPath + "\\resource\\Python\\Multi\\ProConMulti\\MyService2.idl");
		
		List<ServiceReferenceParam> conport = new ArrayList<ServiceReferenceParam>();
		conport.add(new ServiceReferenceParam("MyConPro","myservice0","MyService", rtcParam));
		conport.add(new ServiceReferenceParam("MyConPro2","myservice2","DAQService", rtcParam));
		rtcParam.setConsumerReferences(conport);
		genParam.addConsumerIDLPath(rootPath + "\\resource\\Python\\Multi\\ProConMulti\\MyService.idl");
		genParam.addConsumerIDLPath(rootPath + "\\resource\\Python\\Multi\\ProConMulti\\DAQService.idl");
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		assertEquals("foo.py", result.get(0).getName());
		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.py";
		String expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(0).getCode());

		assertEquals("MyService_idl.py", result.get(1).getName());
		expReadMeS = rootPath +  "\\resource\\" +resourceDir + "\\MyService_idl.py";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(1).getCode());

		assertEquals("MyService2_idl.py", result.get(2).getName());
		expReadMeS = rootPath +  "\\resource\\" +resourceDir + "\\MyService2_idl.py";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(2).getCode());

		assertEquals("DAQService_idl.py", result.get(3).getName());
		expReadMeS = rootPath +  "\\resource\\" +resourceDir + "\\DAQService_idl.py";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(3).getCode());

		assertEquals("\\_GlobalIDL\\__init__.py", result.get(4).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\_GlobalIDL\\__init__.py";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(4).getCode());
		
		assertEquals("\\_GlobalIDL__POA\\__init__.py", result.get(5).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\_GlobalIDL__POA\\__init__.py";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(5).getCode());

		assertEquals("MyService_idl_example.py", result.get(6).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\MyService_idl_example.py";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(6).getCode());

		assertEquals("MyService2_idl_example.py", result.get(7).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\MyService2_idl_example.py";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(7).getCode());

		assertEquals("README.foo", result.get(8).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.foo";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(8).getCode());
	}

	public void testConsumerMulti() throws Exception{
		String resourceDir = "Python\\Multi\\ConMulti";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_PYTHON);
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
		dataInport.add(new DataPortParam("in", "TimedShort"));
		rtcParam.setInports(dataInport);

		List<DataPortParam> dataOutport = new ArrayList<DataPortParam>(); 
		dataOutport.add(new DataPortParam("out", "TimedLong"));
		rtcParam.setOutports(dataOutport);

		List<ServiceReferenceParam> proport = new ArrayList<ServiceReferenceParam>();
		proport.add(new ServiceReferenceParam("MyConPro","myservice0","MyService", rtcParam));
		proport.add(new ServiceReferenceParam("MyConPro2","myservice2","DAQService", rtcParam));
		rtcParam.setConsumerReferences(proport);
		genParam.addConsumerIDLPath(rootPath + "\\resource\\Python\\Multi\\ConMulti\\MyService.idl");
		genParam.addConsumerIDLPath(rootPath + "\\resource\\Python\\Multi\\ConMulti\\DAQService.idl");
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));
		
		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		assertEquals("foo.py", result.get(0).getName());
		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.py";
		String expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(0).getCode());

		assertEquals("MyService_idl.py", result.get(1).getName());
		expReadMeS = rootPath +  "\\resource\\" +resourceDir + "\\MyService_idl.py";
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

		assertEquals("README.foo", result.get(5).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.foo";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(5).getCode());
	}

	public void testProviderMulti() throws Exception{
		String resourceDir = "Python\\Multi\\ProMulti";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_PYTHON);
		rtcParam.setName("foo");
		rtcParam.setDescription("test component");
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
		proport.add(new ServiceReferenceParam("MySVPro2","myservice2","DAQService", rtcParam));
		rtcParam.setProviderReferences(proport);
		genParam.addProviderIDLPath(rootPath + "\\resource\\Python\\Multi\\ProMulti\\MyService.idl");
		genParam.addProviderIDLPath(rootPath + "\\resource\\Python\\Multi\\ProMulti\\DAQService.idl");
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));
		
		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		assertEquals("foo.py", result.get(0).getName());
		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.py";
		String expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(0).getCode());

		assertEquals("MyService_idl.py", result.get(1).getName());
		expReadMeS = rootPath +  "\\resource\\" +resourceDir + "\\MyService_idl.py";
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

		assertEquals("MyService_idl_example.py", result.get(5).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\MyService_idl_example.py";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(5).getCode());

		assertEquals("DAQService_idl_example.py", result.get(6).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\DAQService_idl_example.py";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(6).getCode());

		assertEquals("README.foo", result.get(7).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.foo";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(7).getCode());
	}
}
