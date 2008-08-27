package jp.go.aist.rtm.rtctemplate._test.cxxwin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jp.go.aist.rtm.rtctemplate.Generator;
import jp.go.aist.rtm.rtctemplate.generator.GeneratedResult;
import jp.go.aist.rtm.rtctemplate.generator.param.DataPortParam;
import jp.go.aist.rtm.rtctemplate.generator.param.GeneratorParam;
import jp.go.aist.rtm.rtctemplate.generator.param.RtcParam;
import jp.go.aist.rtm.rtctemplate.generator.param.ServiceReferenceParam;
import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllAddCXXProConMultiTest extends TestCase {

	static private String rootPath;
	static private List<GeneratedResult> result;
	static private String resourceDir;

	static void oneTimeSetup() throws Exception {
		File fileCurrent = new File(".");
		rootPath = fileCurrent.getAbsolutePath();
		rootPath = rootPath.substring(0,rootPath.length()-1);
		
		resourceDir = "CXXWin\\Multi\\ProConMulti";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPPWIN);
		rtcParam.setName("foo");
		rtcParam.setDescription("test module");
		rtcParam.setVersion("1.0.1");
		rtcParam.setVender("TA");
		rtcParam.setCategory("sample");
		rtcParam.setComponentType("STATIC");
		rtcParam.setActivityType("PERIODIC");
		rtcParam.setMaxInstance(2);
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
		genParam.addProviderIDLPath(rootPath + "\\resource\\CXX\\Multi\\MyService.idl");
		genParam.addProviderIDLPath(rootPath + "\\resource\\CXX\\Multi\\MyService2.idl");
		
		List<ServiceReferenceParam> conport = new ArrayList<ServiceReferenceParam>();
		conport.add(new ServiceReferenceParam("MyConPro","myservice0","MyService", rtcParam));
		conport.add(new ServiceReferenceParam("MyConPro2","myservice2","DAQService", rtcParam));
		rtcParam.setConsumerReferences(conport);
		genParam.addConsumerIDLPath(rootPath + "\\resource\\CXX\\Multi\\MyService.idl");
		genParam.addConsumerIDLPath(rootPath + "\\resource\\CXX\\Multi\\DAQService.idl");
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));

		Generator generator = new Generator();
		result = generator.doGenerate(genParam);
	}

	static public Test suite() throws Exception {
		  TestSuite suite = new TestSuite(AllAddCXXProConMultiTest.class);
		  TestSetup wrapper = new TestSetup(suite) {
		      public void setUp() throws Exception {
		        oneTimeSetup();
		      }
		    };
		    return wrapper;
		}
	
	protected void setUp() throws Exception {
	}

	public void testComp() throws Exception{
		assertEquals(TestAllStatic.readFile(rootPath +  "\\resource\\" + resourceDir + "\\fooComp.cpp"),
				result.get(0).getCode());
	}

	public void testMake() throws Exception{
		assertEquals("Makefile.foo", result.get(1).getName());
		assertEquals(TestAllStatic.readFile(rootPath +  "\\resource\\" + resourceDir + "\\Makefile.foo"),
				result.get(1).getCode());
	}

	public void testHedder() throws Exception{
		assertEquals("foo.h", result.get(2).getName());
		assertEquals(TestAllStatic.readFile(rootPath +  "\\resource\\" + resourceDir + "\\foo.h"),
				result.get(2).getCode());
	}

	public void testCpp() throws Exception{
		assertEquals("foo.cpp", result.get(3).getName());
		assertEquals(TestAllStatic.readFile(rootPath +  "\\resource\\" + resourceDir + "\\foo.cpp"),
				result.get(3).getCode());
	}

	public void testRTMPro() throws Exception{
		assertEquals("OpenRTM-aist.vsprops", result.get(4).getName());
		assertEquals(TestAllStatic.readFile(rootPath +  "\\resource\\" + resourceDir + "\\OpenRTM-aist.vsprops"),
				result.get(4).getCode());
	}

	public void testPro() throws Exception{
		assertEquals("foo.vcproj", result.get(5).getName());
		assertEquals(TestAllStatic.readFile(rootPath +  "\\resource\\" + resourceDir + "\\foo.vcproj"),
				result.get(5).getCode());
	}

	public void testSVCH() throws Exception{
		assertEquals("MyServiceSVC_impl.h", result.get(6).getName());
		assertEquals(TestAllStatic.readFile(rootPath +  "\\resource\\" + resourceDir + "\\MyServiceSVC_impl.h"),
				result.get(6).getCode());
	}

	public void testSVCCpp() throws Exception{
		assertEquals("MyServiceSVC_impl.cpp", result.get(7).getName());
		assertEquals(TestAllStatic.readFile(rootPath +  "\\resource\\" + resourceDir + "\\MyServiceSVC_impl.cpp"),
				result.get(7).getCode());
	}

	public void testSVC2H() throws Exception{
		assertEquals("MyService2SVC_impl.h", result.get(8).getName());
		assertEquals(TestAllStatic.readFile(rootPath +  "\\resource\\" + resourceDir + "\\MyService2SVC_impl.h"),
				result.get(8).getCode());
	}

	public void testSVC2Cpp() throws Exception{
		assertEquals("MyService2SVC_impl.cpp", result.get(9).getName());
		assertEquals(TestAllStatic.readFile(rootPath +  "\\resource\\" + resourceDir + "\\MyService2SVC_impl.cpp"),
				result.get(9).getCode());
	}

	public void testreadMe() throws Exception{
		assertEquals("README.foo", result.get(10).getName());
		assertEquals(TestAllStatic.readFile(rootPath +  "\\resource\\" + resourceDir + "\\README.foo"),
				result.get(10).getCode());
	}

}
