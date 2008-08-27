package jp.go.aist.rtm.rtctemplate._test.java;

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
		String resourceDir = "Java\\service2";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		genParam.addProviderIDLPath(rootPath + "\\resource\\MyService.idl");
		genParam.addConsumerIDLPath(rootPath + "\\resource\\DAQService.idl");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(RtcParam.LANG_JAVA);
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

		String targetDir = rootPath + "\\resource\\Java\\service2\\";
		checkCode(result, targetDir, "fooComp.java");
		checkCode(result, targetDir, "build_foo.xml");
		checkCode(result, targetDir, "foo.java");
		checkCode(result, targetDir, "fooImpl.java");
		checkCode(result, targetDir, "MyServiceSVC_impl.java");
		checkCode(result, targetDir, "README.foo");
	}

	public void testServicePort1() throws Exception{
		String resourceDir = "Java\\service1";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		genParam.addProviderIDLPath(rootPath + "\\resource\\MyService.idl");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_JAVA);
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

		String targetDir = rootPath + "\\resource\\Java\\service1\\";
		checkCode(result, targetDir, "fooComp.java");
		checkCode(result, targetDir, "build_foo.xml");
		checkCode(result, targetDir, "foo.java");
		checkCode(result, targetDir, "fooImpl.java");
		checkCode(result, targetDir, "MyServiceSVC_impl.java");
		checkCode(result, targetDir, "README.foo");
	}

	public void testOutPort2() throws Exception{
		String resourceDir = "Java\\outport2";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_JAVA);
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

		String targetDir = rootPath + "\\resource\\Java\\outport2\\";
		checkCode(result, targetDir, "fooComp.java");
		checkCode(result, targetDir, "build_foo.xml");
		checkCode(result, targetDir, "foo.java");
		checkCode(result, targetDir, "fooImpl.java");
		checkCode(result, targetDir, "README.foo");
	}

	public void testOutPort1() throws Exception{
		String resourceDir = "Java\\outport1";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_JAVA);
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

		String targetDir = rootPath + "\\resource\\Java\\outport1\\";
		checkCode(result, targetDir, "fooComp.java");
		checkCode(result, targetDir, "build_foo.xml");
		checkCode(result, targetDir, "foo.java");
		checkCode(result, targetDir, "fooImpl.java");
		checkCode(result, targetDir, "README.foo");
	}

	public void testInPort2() throws Exception{
		String resourceDir = "Java\\inport2";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_JAVA);
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

		String targetDir = rootPath + "\\resource\\Java\\inport2\\";
		checkCode(result, targetDir, "fooComp.java");
		checkCode(result, targetDir, "build_foo.xml");
		checkCode(result, targetDir, "foo.java");
		checkCode(result, targetDir, "fooImpl.java");
		checkCode(result, targetDir, "README.foo");
	}

	public void testInPort() throws Exception{
		String resourceDir = "Java\\inport1";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_JAVA);
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

		String targetDir = rootPath + "\\resource\\Java\\inport1\\";
		checkCode(result, targetDir, "fooComp.java");
		checkCode(result, targetDir, "build_foo.xml");
		checkCode(result, targetDir, "foo.java");
		checkCode(result, targetDir, "fooImpl.java");
		checkCode(result, targetDir, "README.foo");
	}

	public void testBasic() throws Exception{
		String resourceDir = "Java\\name";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_JAVA);
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

		String targetDir = rootPath + "\\resource\\Java\\name\\";
		checkCode(result, targetDir, "fooComp.java");
		checkCode(result, targetDir, "build_foo.xml");
		checkCode(result, targetDir, "foo.java");
		checkCode(result, targetDir, "fooImpl.java");
		checkCode(result, targetDir, "README.foo");
	}
}
