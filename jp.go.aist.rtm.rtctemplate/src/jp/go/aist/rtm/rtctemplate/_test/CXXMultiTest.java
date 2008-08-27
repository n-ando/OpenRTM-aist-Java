package jp.go.aist.rtm.rtctemplate._test;

import java.util.ArrayList;
import java.util.List;

import jp.go.aist.rtm.rtctemplate.Generator;
import jp.go.aist.rtm.rtctemplate.generator.GeneratedResult;
import jp.go.aist.rtm.rtctemplate.generator.param.DataPortParam;
import jp.go.aist.rtm.rtctemplate.generator.param.GeneratorParam;
import jp.go.aist.rtm.rtctemplate.generator.param.RtcParam;
import jp.go.aist.rtm.rtctemplate.generator.param.ServiceReferenceParam;

public class CXXMultiTest extends TestBase {

	protected void setUp() throws Exception {
	}

	public void testProConMulti() throws Exception{
		String resourceDir = "CXX\\Multi\\ProConMulti";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPP);
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
		genParam.addProviderIDLPath(rootPath + "\\resource\\CXX\\Multi\\MyService.idl");
		genParam.addProviderIDLPath(rootPath + "\\resource\\CXX\\Multi\\MyService2.idl");
		
		List<ServiceReferenceParam> conport = new ArrayList<ServiceReferenceParam>();
		conport.add(new ServiceReferenceParam("MyConPro","myservice0","MyService", rtcParam));
		conport.add(new ServiceReferenceParam("MyConPro2","myservice2","DAQService", rtcParam));
		rtcParam.setConsumerReferences(conport);
		genParam.addConsumerIDLPath(rootPath + "\\resource\\CXX\\Multi\\MyService.idl");
		genParam.addConsumerIDLPath(rootPath + "\\resource\\CXX\\Multi\\DAQService.idl");

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		assertEquals("fooComp.cpp", result.get(0).getName());
		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\fooComp.cpp";
		String expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(0).getCode());

		assertEquals("Makefile.foo", result.get(1).getName());
		expReadMeS = rootPath +  "\\resource\\" +resourceDir + "\\Makefile.foo";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(1).getCode());

		assertEquals("foo.h", result.get(2).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.h";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(2).getCode());

		assertEquals("foo.cpp", result.get(3).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.cpp";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(3).getCode());

		assertEquals("MyServiceSVC_impl.h", result.get(4).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\MyServiceSVC_impl.h";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(4).getCode());

		assertEquals("MyServiceSVC_impl.cpp", result.get(5).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\MyServiceSVC_impl.cpp";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(5).getCode());

		assertEquals("MyService2SVC_impl.h", result.get(6).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\MyService2SVC_impl.h";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(6).getCode());

		assertEquals("MyService2SVC_impl.cpp", result.get(7).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\MyService2SVC_impl.cpp";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(7).getCode());

		assertEquals("README.foo", result.get(8).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.foo";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(8).getCode());
	}

	public void testConsumerMulti() throws Exception{
		String resourceDir = "CXX\\Multi\\ConMulti";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPP);
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
		proport.add(new ServiceReferenceParam("MyConPro","myservice0","MyService", rtcParam));
		proport.add(new ServiceReferenceParam("MyConPro2","myservice2","DAQService", rtcParam));
		rtcParam.setConsumerReferences(proport);
		genParam.addConsumerIDLPath(rootPath + "\\resource\\CXX\\Multi\\MyService.idl");
		genParam.addConsumerIDLPath(rootPath + "\\resource\\CXX\\Multi\\DAQService.idl");
		
		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		assertEquals("fooComp.cpp", result.get(0).getName());
		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\fooComp.cpp";
		String expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(0).getCode());

		assertEquals("Makefile.foo", result.get(1).getName());
		expReadMeS = rootPath +  "\\resource\\" +resourceDir + "\\Makefile.foo";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(1).getCode());

		assertEquals("foo.h", result.get(2).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.h";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(2).getCode());

		assertEquals("foo.cpp", result.get(3).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.cpp";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(3).getCode());

		assertEquals("README.foo", result.get(4).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.foo";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(4).getCode());
	}

	public void testProviderMulti() throws Exception{
		String resourceDir = "CXX\\Multi\\ProMulti";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPP);
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
		proport.add(new ServiceReferenceParam("MySVPro","myservice0","MyService", rtcParam));
		proport.add(new ServiceReferenceParam("MySVPro2","myservice2","DAQService", rtcParam));
		rtcParam.setProviderReferences(proport);
		genParam.addProviderIDLPath(rootPath + "\\resource\\CXX\\Multi\\MyService.idl");
		genParam.addProviderIDLPath(rootPath + "\\resource\\CXX\\Multi\\DAQService.idl");
		
		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		assertEquals("fooComp.cpp", result.get(0).getName());
		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\fooComp.cpp";
		String expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(0).getCode());

		assertEquals("Makefile.foo", result.get(1).getName());
		expReadMeS = rootPath +  "\\resource\\" +resourceDir + "\\Makefile.foo";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(1).getCode());

		assertEquals("foo.h", result.get(2).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.h";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(2).getCode());

		assertEquals("foo.cpp", result.get(3).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.cpp";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(3).getCode());

		assertEquals("MyServiceSVC_impl.h", result.get(4).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\MyServiceSVC_impl.h";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(4).getCode());

		assertEquals("MyServiceSVC_impl.cpp", result.get(5).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\MyServiceSVC_impl.cpp";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(5).getCode());

		assertEquals("DAQServiceSVC_impl.h", result.get(6).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\DAQServiceSVC_impl.h";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(6).getCode());

		assertEquals("DAQServiceSVC_impl.cpp", result.get(7).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\DAQServiceSVC_impl.cpp";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(7).getCode());

		assertEquals("README.foo", result.get(8).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.foo";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(8).getCode());
	}
}
