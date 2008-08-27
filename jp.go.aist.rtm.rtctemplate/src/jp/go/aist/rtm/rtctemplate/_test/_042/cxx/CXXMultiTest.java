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

public class CXXMultiTest extends TestBase {

	protected void setUp() throws Exception {
	}

	public void testProConMulti() throws Exception{
		String resourceDir = "042\\CXX\\Multi\\ProConMulti";
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
		rtcParam.setRtmVersion("0.4.2");
		rtcParam.setIsTest(true);
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
		genParam.addProviderIDLPath(rootPath + "\\resource\\042\\CXX\\Multi\\ProConMulti\\MyService.idl");
		genParam.addProviderIDLPath(rootPath + "\\resource\\042\\CXX\\Multi\\ProConMulti\\MyService2.idl");
		
		List<ServiceReferenceParam> conport = new ArrayList<ServiceReferenceParam>();
		conport.add(new ServiceReferenceParam("MyConPro","myservice0","MyService", rtcParam));
		conport.add(new ServiceReferenceParam("MyConPro2","myservice2","DAQService", rtcParam));
		rtcParam.setConsumerReferences(conport);
		genParam.addConsumerIDLPath(rootPath + "\\resource\\042\\CXX\\Multi\\ProConMulti\\MyService.idl");
		genParam.addConsumerIDLPath(rootPath + "\\resource\\042\\CXX\\Multi\\ProConMulti\\DAQService.idl");
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		String targetDir = rootPath + "\\resource\\042\\CXX\\Multi\\ProConMulti\\";
		checkCode(result, targetDir, "fooComp.cpp");
		checkCode(result, targetDir, "Makefile.foo");
		checkCode(result, targetDir, "foo.h");
		checkCode(result, targetDir, "foo.cpp");
		checkCode(result, targetDir, "MyServiceSVC_impl.h");
		checkCode(result, targetDir, "MyServiceSVC_impl.cpp");
		checkCode(result, targetDir, "MyService2SVC_impl.h");
		checkCode(result, targetDir, "MyService2SVC_impl.cpp");
		checkCode(result, targetDir, "README.foo");
		//
		checkCode(result, targetDir, "foo_vc8.sln");
		checkCode(result, targetDir, "foo_vc8.vcproj");
		checkCode(result, targetDir, "foo_vc9.sln");
		checkCode(result, targetDir, "foo_vc9.vcproj");
		checkCode(result, targetDir, "fooComp_vc8.vcproj");
		checkCode(result, targetDir, "fooComp_vc9.vcproj");
		//
		checkCode(result, targetDir, "copyprops.bat");
		checkCode(result, targetDir, "user_config.vsprops");
	}

	public void testConsumerMulti() throws Exception{
		String resourceDir = "042\\CXX\\Multi\\ConMulti";
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
		rtcParam.setRtmVersion("0.4.2");
		rtcParam.setIsTest(true);
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
		genParam.addConsumerIDLPath(rootPath + "\\resource\\042\\CXX\\Multi\\ConMulti\\MyService.idl");
		genParam.addConsumerIDLPath(rootPath + "\\resource\\042\\CXX\\Multi\\ConMulti\\DAQService.idl");
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));
		
		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		String targetDir = rootPath + "\\resource\\042\\CXX\\Multi\\ConMulti\\";
		checkCode(result, targetDir, "fooComp.cpp");
		checkCode(result, targetDir, "Makefile.foo");
		checkCode(result, targetDir, "foo.h");
		checkCode(result, targetDir, "foo.cpp");
		checkCode(result, targetDir, "README.foo");
		//
		checkCode(result, targetDir, "foo_vc8.sln");
		checkCode(result, targetDir, "foo_vc8.vcproj");
		checkCode(result, targetDir, "foo_vc9.sln");
		checkCode(result, targetDir, "foo_vc9.vcproj");
		checkCode(result, targetDir, "fooComp_vc8.vcproj");
		checkCode(result, targetDir, "fooComp_vc9.vcproj");
		//
		checkCode(result, targetDir, "copyprops.bat");
		checkCode(result, targetDir, "user_config.vsprops");
	}

	public void testProviderMulti() throws Exception{
		String resourceDir = "042\\CXX\\Multi\\ProMulti";
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
		rtcParam.setRtmVersion("0.4.2");
		rtcParam.setIsTest(true);
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
		genParam.addProviderIDLPath(rootPath + "\\resource\\042\\CXX\\Multi\\ProMulti\\MyService.idl");
		genParam.addProviderIDLPath(rootPath + "\\resource\\042\\CXX\\Multi\\ProMulti\\DAQService.idl");
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));
		
		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		String targetDir = rootPath + "\\resource\\042\\CXX\\Multi\\ProMulti\\";
		checkCode(result, targetDir, "fooComp.cpp");
		checkCode(result, targetDir, "Makefile.foo");
		checkCode(result, targetDir, "foo.h");
		checkCode(result, targetDir, "foo.cpp");
		checkCode(result, targetDir, "MyServiceSVC_impl.h");
		checkCode(result, targetDir, "MyServiceSVC_impl.cpp");
		checkCode(result, targetDir, "DAQServiceSVC_impl.h");
		checkCode(result, targetDir, "DAQServiceSVC_impl.cpp");
		checkCode(result, targetDir, "README.foo");
		//
		checkCode(result, targetDir, "foo_vc8.sln");
		checkCode(result, targetDir, "foo_vc8.vcproj");
		checkCode(result, targetDir, "foo_vc9.sln");
		checkCode(result, targetDir, "foo_vc9.vcproj");
		checkCode(result, targetDir, "fooComp_vc8.vcproj");
		checkCode(result, targetDir, "fooComp_vc9.vcproj");
		//
		checkCode(result, targetDir, "copyprops.bat");
		checkCode(result, targetDir, "user_config.vsprops");
	}
}
