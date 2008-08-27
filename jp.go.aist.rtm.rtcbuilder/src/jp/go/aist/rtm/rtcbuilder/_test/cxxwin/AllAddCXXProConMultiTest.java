package jp.go.aist.rtm.rtcbuilder._test.cxxwin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jp.go.aist.rtm.rtcbuilder.Generator;
import jp.go.aist.rtm.rtcbuilder.IRtcBuilderConstants;
import jp.go.aist.rtm.rtcbuilder.generator.GeneratedResult;
import jp.go.aist.rtm.rtcbuilder.generator.param.DataPortParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.GeneratorParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.RtcParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.ServicePortInterfaceParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.ServicePortParam;
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
		RtcParam rtcParam = new RtcParam(genParam, true);
		rtcParam.setOutputProject(rootPath + "\\resource\\work");
		rtcParam.setLanguage(IRtcBuilderConstants.LANG_CPPWIN);
		rtcParam.setName("foo");
		rtcParam.setDescription("test module");
		rtcParam.setVersion("1.0.1");
		rtcParam.setVender("TA");
		rtcParam.setCategory("sample");
		rtcParam.setComponentType("STATIC");
		rtcParam.setActivityType("PERIODIC");
		rtcParam.setMaxInstance(2);
		
		List<DataPortParam> dataInport = new ArrayList<DataPortParam>(); 
		dataInport.add(new DataPortParam("in1", "TimedShort", "", 0));
		rtcParam.setInports(dataInport);

		List<DataPortParam> dataOutport = new ArrayList<DataPortParam>(); 
		dataOutport.add(new DataPortParam("out1", "TimedLong", "", 0));
		rtcParam.setOutports(dataOutport);

		ServicePortParam service1 = new ServicePortParam("MySVPro",0);
		List<ServicePortInterfaceParam> srvinterts = new ArrayList<ServicePortInterfaceParam>(); 
		ServicePortInterfaceParam int1 = new ServicePortInterfaceParam(service1, "myserviceP1", "", "", 
				rootPath + "resource\\CXX\\Multi\\MyService.idl", "MyService", "", 0);
		srvinterts.add(int1);
		service1.setServicePortInterfaces(srvinterts);
		List<ServicePortParam> srvports = new ArrayList<ServicePortParam>();
		srvports.add(service1);

		ServicePortParam service2 = new ServicePortParam("MySVPro2",0);
		List<ServicePortInterfaceParam> srvinterts2 = new ArrayList<ServicePortInterfaceParam>(); 
		ServicePortInterfaceParam int2 = new ServicePortInterfaceParam(service1, "myserviceP2", "", "", 
				rootPath + "resource\\CXX\\Multi\\MyService2.idl", "MyService2", "", 0);
		srvinterts2.add(int2);
		service2.setServicePortInterfaces(srvinterts2);
		srvports.add(service2);
		rtcParam.setServicePorts(srvports);

		ServicePortParam service3 = new ServicePortParam("MyConPro",0);
		List<ServicePortInterfaceParam> srvintert3 = new ArrayList<ServicePortInterfaceParam>(); 
		ServicePortInterfaceParam int3 = new ServicePortInterfaceParam(service1, "myservice0", "", "", 
				rootPath + "resource\\CXX\\Multi\\MyService.idl", "MyService", "", 1);
		srvintert3.add(int3);
		service3.setServicePortInterfaces(srvintert3);
		srvports.add(service3);

		ServicePortParam service4 = new ServicePortParam("MyConPro2",0);
		List<ServicePortInterfaceParam> srvinterts4 = new ArrayList<ServicePortInterfaceParam>(); 
		ServicePortInterfaceParam int4 = new ServicePortInterfaceParam(service1, "myservice2", "", "", 
				rootPath + "resource\\CXX\\Multi\\DAQService.idl", "DAQService", "", 1);
		srvinterts4.add(int4);
		service4.setServicePortInterfaces(srvinterts4);
		srvports.add(service4);
		rtcParam.setServicePorts(srvports);
		genParam.getRtcParams().add(rtcParam);

		Generator generator = new Generator();
		result = generator.generateTemplateCode(genParam);
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
