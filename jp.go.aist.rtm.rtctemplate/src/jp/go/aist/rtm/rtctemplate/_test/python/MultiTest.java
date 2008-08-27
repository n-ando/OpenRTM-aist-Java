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

		String targetDir = rootPath +  "\\resource\\" + resourceDir + "\\";
		checkCode(result, targetDir, "foo.py");
		checkCode(result, targetDir, "MyService_idl.py");
		checkCode(result, targetDir, "MyService2_idl.py");
		checkCode(result, targetDir, "DAQService_idl.py");
		checkCode(result, targetDir, "\\_GlobalIDL\\__init__.py");
		checkCode(result, targetDir, "\\_GlobalIDL__POA\\__init__.py");
		checkCode(result, targetDir, "MyService_idl_example.py");
		checkCode(result, targetDir, "MyService2_idl_example.py");
		checkCode(result, targetDir, "README.foo");
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

		String targetDir = rootPath +  "\\resource\\" + resourceDir + "\\";
		checkCode(result, targetDir, "foo.py");
		checkCode(result, targetDir, "MyService_idl.py");
		checkCode(result, targetDir, "DAQService_idl.py");
		checkCode(result, targetDir, "\\_GlobalIDL\\__init__.py");
		checkCode(result, targetDir, "\\_GlobalIDL__POA\\__init__.py");
		checkCode(result, targetDir, "README.foo");
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

		String targetDir = rootPath +  "\\resource\\" + resourceDir + "\\";
		checkCode(result, targetDir, "foo.py");
		checkCode(result, targetDir, "MyService_idl.py");
		checkCode(result, targetDir, "DAQService_idl.py");
		checkCode(result, targetDir, "\\_GlobalIDL\\__init__.py");
		checkCode(result, targetDir, "\\_GlobalIDL__POA\\__init__.py");
		checkCode(result, targetDir, "MyService_idl_example.py");
		checkCode(result, targetDir, "DAQService_idl_example.py");
		checkCode(result, targetDir, "README.foo");
	}
}
