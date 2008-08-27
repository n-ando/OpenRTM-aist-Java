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

public class BaseTest extends TestBase {

	protected void setUp() throws Exception {
	}

	public void testServicePort2() throws Exception{
		String resourceDir = "Python\\service2";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		genParam.addProviderIDLPath(rootPath + "\\resource\\MyService.idl");
		genParam.addConsumerIDLPath(rootPath + "\\resource\\DAQService.idl");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_PYTHON);
		rtcParam.setName("foo");
		rtcParam.setDescription("MDesc");
		rtcParam.setVersion("1.0.1");
		rtcParam.setVender("TA");
		rtcParam.setCategory("Manip");
		rtcParam.setComponentType("STATIC2");
		rtcParam.setActivityType("PERIODIC2");
		rtcParam.setMaxInstance(5);
		genParam.getRtcParams().add(rtcParam);
		List<DataPortParam> dataport = new ArrayList<DataPortParam>(); 
		dataport.add(new DataPortParam("InP1", "TimedShort"));
		dataport.add(new DataPortParam("InP2", "TimedLong"));
		rtcParam.setInports(dataport);
		List<DataPortParam> outport = new ArrayList<DataPortParam>(); 
		outport.add(new DataPortParam("OutP1", "TimedLong"));
		outport.add(new DataPortParam("OutP2", "TimedFloat"));
		rtcParam.setOutports(outport);

		List<ServiceReferenceParam> srvport = new ArrayList<ServiceReferenceParam>(); 
		srvport.add(new ServiceReferenceParam("svPort","acc","MyService", rtcParam));
		rtcParam.setProviderReferences(srvport);
		
		List<ServiceReferenceParam> cmvport = new ArrayList<ServiceReferenceParam>(); 
		cmvport.add(new ServiceReferenceParam("cmPort","rate","DAQService", rtcParam));
		rtcParam.setConsumerReferences(cmvport);
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

		assertEquals("README.foo", result.get(6).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.foo";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(6).getCode());
	}

	public void testServicePort1() throws Exception{
		String resourceDir = "Python\\service1";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		genParam.addProviderIDLPath(rootPath + "\\resource\\MyService.idl");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_PYTHON);
		rtcParam.setName("foo");
		rtcParam.setDescription("MDesc");
		rtcParam.setVersion("1.0.1");
		rtcParam.setVender("TA");
		rtcParam.setCategory("Manip");
		rtcParam.setComponentType("STATIC2");
		rtcParam.setActivityType("PERIODIC2");
		rtcParam.setMaxInstance(5);
		genParam.getRtcParams().add(rtcParam);
		List<DataPortParam> dataport = new ArrayList<DataPortParam>(); 
		dataport.add(new DataPortParam("InP1", "TimedShort"));
		dataport.add(new DataPortParam("InP2", "TimedLong"));
		rtcParam.setInports(dataport);
		List<DataPortParam> outport = new ArrayList<DataPortParam>(); 
		outport.add(new DataPortParam("OutP1", "TimedLong"));
		outport.add(new DataPortParam("OutP2", "TimedFloat"));
		rtcParam.setOutports(outport);

		List<ServiceReferenceParam> srvport = new ArrayList<ServiceReferenceParam>(); 
		srvport.add(new ServiceReferenceParam("svPort","acc","MyService", rtcParam));
		rtcParam.setProviderReferences(srvport);
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

		assertEquals("\\_GlobalIDL\\__init__.py", result.get(2).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\_GlobalIDL\\__init__.py";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(2).getCode());
		
		assertEquals("\\_GlobalIDL__POA\\__init__.py", result.get(3).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\_GlobalIDL__POA\\__init__.py";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(3).getCode());

		assertEquals("MyService_idl_example.py", result.get(4).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\MyService_idl_example.py";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(4).getCode());

		assertEquals("README.foo", result.get(5).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.foo";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(5).getCode());
	}

	public void testOutPort2() throws Exception{
		String resourceDir = "Python\\outport2";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_PYTHON);
		rtcParam.setName("foo");
		rtcParam.setDescription("MDesc");
		rtcParam.setVersion("1.0.1");
		rtcParam.setVender("TA");
		rtcParam.setCategory("Manip");
		rtcParam.setComponentType("STATIC2");
		rtcParam.setActivityType("PERIODIC2");
		rtcParam.setMaxInstance(5);
		genParam.getRtcParams().add(rtcParam);
		List<DataPortParam> dataport = new ArrayList<DataPortParam>(); 
		dataport.add(new DataPortParam("InP1", "TimedShort"));
		dataport.add(new DataPortParam("InP2", "TimedLong"));
		rtcParam.setInports(dataport);
		List<DataPortParam> outport = new ArrayList<DataPortParam>(); 
		outport.add(new DataPortParam("OutP1", "TimedLong"));
		outport.add(new DataPortParam("OutP2", "TimedFloat"));
		rtcParam.setOutports(outport);
		
		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		assertEquals("foo.py", result.get(0).getName());
		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.py";
		String expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(0).getCode());

		assertEquals("README.foo", result.get(1).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.foo";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(1).getCode());
	}

	public void testOutPort1() throws Exception{
		String resourceDir = "Python\\outport1";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_PYTHON);
		rtcParam.setName("foo");
		rtcParam.setDescription("MDesc");
		rtcParam.setVersion("1.0.1");
		rtcParam.setVender("TA");
		rtcParam.setCategory("Manip");
		rtcParam.setComponentType("STATIC2");
		rtcParam.setActivityType("PERIODIC2");
		rtcParam.setMaxInstance(5);
		genParam.getRtcParams().add(rtcParam);
		List<DataPortParam> dataport = new ArrayList<DataPortParam>(); 
		dataport.add(new DataPortParam("InP1", "TimedShort"));
		dataport.add(new DataPortParam("InP2", "TimedLong"));
		rtcParam.setInports(dataport);
		List<DataPortParam> outport = new ArrayList<DataPortParam>(); 
		outport.add(new DataPortParam("OutP1", "TimedLong"));
		rtcParam.setOutports(outport);
		
		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		assertEquals("foo.py", result.get(0).getName());
		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.py";
		String expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(0).getCode());

		assertEquals("README.foo", result.get(1).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.foo";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(1).getCode());
	}

	public void testInPort2() throws Exception{
		String resourceDir = "Python\\inport2";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_PYTHON);
		rtcParam.setName("foo");
		rtcParam.setDescription("MDesc");
		rtcParam.setVersion("1.0.1");
		rtcParam.setVender("TA");
		rtcParam.setCategory("Manip");
		rtcParam.setComponentType("STATIC2");
		rtcParam.setActivityType("PERIODIC2");
		rtcParam.setMaxInstance(5);
		genParam.getRtcParams().add(rtcParam);
		List<DataPortParam> dataport = new ArrayList<DataPortParam>(); 
		dataport.add(new DataPortParam("InP1", "TimedShort"));
		dataport.add(new DataPortParam("InP2", "TimedLong"));
		rtcParam.setInports(dataport);
		
		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		assertEquals("foo.py", result.get(0).getName());
		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.py";
		String expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(0).getCode());

		assertEquals("README.foo", result.get(1).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.foo";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(1).getCode());
	}

	public void testInPort() throws Exception{
		String resourceDir = "Python\\inport1";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_PYTHON);
		rtcParam.setName("foo");
		rtcParam.setDescription("MDesc");
		rtcParam.setVersion("1.0.1");
		rtcParam.setVender("TA");
		rtcParam.setCategory("Manip");
		rtcParam.setComponentType("STATIC2");
		rtcParam.setActivityType("PERIODIC2");
		rtcParam.setMaxInstance(5);
		genParam.getRtcParams().add(rtcParam);
		List<DataPortParam> dataport = new ArrayList<DataPortParam>(); 
		dataport.add(new DataPortParam("InP1", "TimedShort"));
		rtcParam.setInports(dataport);
		
		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		assertEquals("foo.py", result.get(0).getName());
		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.py";
		String expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(0).getCode());

		assertEquals("README.foo", result.get(1).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.foo";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(1).getCode());
	}

	public void testBasic() throws Exception{
		String resourceDir = "Python\\name";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_PYTHON);
		rtcParam.setName("foo");
		rtcParam.setDescription("MDesc");
		rtcParam.setVersion("1.0.1");
		rtcParam.setVender("TA");
		rtcParam.setCategory("Manip");
		rtcParam.setComponentType("STATIC2");
		rtcParam.setActivityType("PERIODIC2");
		rtcParam.setMaxInstance(5);
		genParam.getRtcParams().add(rtcParam);
		
		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		assertEquals("foo.py", result.get(0).getName());
		String expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\foo.py";
		String expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(0).getCode());

		assertEquals("README.foo", result.get(1).getName());
		expReadMeS = rootPath +  "\\resource\\" + resourceDir + "\\README.foo";
		expReadMe = readFile(expReadMeS);
		assertEquals(expReadMe, result.get(1).getCode());
	}
}
