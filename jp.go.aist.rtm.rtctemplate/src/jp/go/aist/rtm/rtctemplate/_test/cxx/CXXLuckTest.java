package jp.go.aist.rtm.rtctemplate._test.cxx;

import java.util.ArrayList;
import java.util.List;

import jp.go.aist.rtm.rtctemplate.Generator;
import jp.go.aist.rtm.rtctemplate._test.TestBase;
import jp.go.aist.rtm.rtctemplate.generator.GeneratedResult;
import jp.go.aist.rtm.rtctemplate.generator.param.DataPortParam;
import jp.go.aist.rtm.rtctemplate.generator.param.GeneratorParam;
import jp.go.aist.rtm.rtctemplate.generator.param.RtcParam;
import jp.go.aist.rtm.rtctemplate.generator.param.ServiceReferenceParam;

public class CXXLuckTest extends TestBase {

	protected void setUp() throws Exception {
	}

	public void testConsumerNoType() throws Exception{
		String resourceDir = "CXX\\Exception\\ConNoName";
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
		rtcParam.setProviderReferences(proport);
		genParam.addProviderIDLPath(rootPath + "\\resource\\CXX\\Exception\\MyService.idl");
		
		List<ServiceReferenceParam> conport = new ArrayList<ServiceReferenceParam>(); 
		conport.add(new ServiceReferenceParam("MyConPro","myservice1","", rtcParam));
		rtcParam.setConsumerReferences(conport);
		genParam.addConsumerIDLPath(rootPath + "\\resource\\CXX\\Exception\\MyService.idl");
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));

		Generator generator = new Generator();
		try {
			List<GeneratedResult> result = generator.doGenerate(genParam);
			fail();
		} catch ( Exception ex ) {
			assertEquals("'' is not found in IDL", ex.getMessage());
		}
	}

	public void testConsumerNoName() throws Exception{
		String resourceDir = "CXX\\Exception\\ConNoName";
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
		rtcParam.setProviderReferences(proport);
		genParam.addProviderIDLPath(rootPath + "\\resource\\CXX\\Exception\\MyService.idl");
		
		List<ServiceReferenceParam> conport = new ArrayList<ServiceReferenceParam>(); 
		conport.add(new ServiceReferenceParam("MyConPro","","MyService", rtcParam));
		rtcParam.setConsumerReferences(conport);
		genParam.addConsumerIDLPath(rootPath + "\\resource\\CXX\\Exception\\MyService.idl");
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		String targetDir = rootPath + "\\resource\\CXX\\Exception\\ConNoName\\";
		checkCode(result, targetDir, "fooComp.cpp");
		checkCode(result, targetDir, "Makefile.foo");
		checkCode(result, targetDir, "foo.h");
		checkCode(result, targetDir, "foo.cpp");
		checkCode(result, targetDir, "MyServiceSVC_impl.h");
		checkCode(result, targetDir, "MyServiceSVC_impl.cpp");
		checkCode(result, targetDir, "README.foo");
	}

	public void testConsumerNoPortName() throws Exception{
		String resourceDir = "CXX\\Exception\\ConNoPortName";
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
		rtcParam.setProviderReferences(proport);
		genParam.addProviderIDLPath(rootPath + "\\resource\\CXX\\Exception\\MyService.idl");
		
		List<ServiceReferenceParam> conport = new ArrayList<ServiceReferenceParam>(); 
		conport.add(new ServiceReferenceParam("","myservice1","MyService", rtcParam));
		rtcParam.setConsumerReferences(conport);
		genParam.addConsumerIDLPath(rootPath + "\\resource\\CXX\\Exception\\MyService.idl");
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		String targetDir = rootPath + "\\resource\\CXX\\Exception\\ConNoPortName\\";
		checkCode(result, targetDir, "fooComp.cpp");
		checkCode(result, targetDir, "Makefile.foo");
		checkCode(result, targetDir, "foo.h");
		checkCode(result, targetDir, "foo.cpp");
		checkCode(result, targetDir, "MyServiceSVC_impl.h");
		checkCode(result, targetDir, "MyServiceSVC_impl.cpp");
		checkCode(result, targetDir, "README.foo");
	}

	public void testProviderNoType() throws Exception{
		String resourceDir = "CXX\\Exception\\ProNoType";
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
		proport.add(new ServiceReferenceParam("MySVPro","myservice0","", rtcParam));
		rtcParam.setProviderReferences(proport);
		genParam.addProviderIDLPath(rootPath + "\\resource\\CXX\\Exception\\MyService.idl");
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));
		
		Generator generator = new Generator();
		try {
			List<GeneratedResult> result = generator.doGenerate(genParam);
			fail();
		} catch ( Exception ex ) {
			assertEquals("'' is not found in IDL", ex.getMessage());
		}

	}

	public void testProviderNoName() throws Exception{
		String resourceDir = "CXX\\Exception\\ProNoName";
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
		proport.add(new ServiceReferenceParam("MySVPro","","MyService", rtcParam));
		rtcParam.setProviderReferences(proport);
		genParam.addProviderIDLPath(rootPath + "\\resource\\CXX\\Exception\\MyService.idl");
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));
		
		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		String targetDir = rootPath + "\\resource\\CXX\\Exception\\ProNoName\\";
		checkCode(result, targetDir, "fooComp.cpp");
		checkCode(result, targetDir, "Makefile.foo");
		checkCode(result, targetDir, "foo.h");
		checkCode(result, targetDir, "foo.cpp");
		checkCode(result, targetDir, "MyServiceSVC_impl.h");
		checkCode(result, targetDir, "MyServiceSVC_impl.cpp");
		checkCode(result, targetDir, "README.foo");
	}

	public void testProviderNoPortName() throws Exception{
		String resourceDir = "CXX\\Exception\\ProNoPortName";
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
		proport.add(new ServiceReferenceParam("","myservice0","MyService", rtcParam));
		rtcParam.setProviderReferences(proport);
		genParam.addProviderIDLPath(rootPath + "\\resource\\CXX\\Exception\\MyService.idl");
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));
		
		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		String targetDir = rootPath + "\\resource\\CXX\\Exception\\ProNoPortName\\";
		checkCode(result, targetDir, "fooComp.cpp");
		checkCode(result, targetDir, "Makefile.foo");
		checkCode(result, targetDir, "foo.h");
		checkCode(result, targetDir, "foo.cpp");
		checkCode(result, targetDir, "MyServiceSVC_impl.h");
		checkCode(result, targetDir, "MyServiceSVC_impl.cpp");
		checkCode(result, targetDir, "README.foo");
	}

	public void testOutPortNoType() throws Exception{
		String resourceDir = "CXX\\Exception\\OutPortNoType";
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
		dataOutport.add(new DataPortParam("out1", ""));
		rtcParam.setOutports(dataOutport);
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));
		
		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		String targetDir = rootPath + "\\resource\\CXX\\Exception\\OutPortNoType\\";
		checkCode(result, targetDir, "fooComp.cpp");
		checkCode(result, targetDir, "Makefile.foo");
		checkCode(result, targetDir, "foo.h");
		checkCode(result, targetDir, "foo.cpp");
		checkCode(result, targetDir, "README.foo");
	}

	public void testOutPortNoName() throws Exception{
		String resourceDir = "CXX\\Exception\\OutPortNoName";
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
		dataOutport.add(new DataPortParam("", "TimedLong"));
		rtcParam.setOutports(dataOutport);
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));
		
		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		String targetDir = rootPath + "\\resource\\CXX\\Exception\\OutPortNoName\\";
		checkCode(result, targetDir, "fooComp.cpp");
		checkCode(result, targetDir, "Makefile.foo");
		checkCode(result, targetDir, "foo.h");
		checkCode(result, targetDir, "foo.cpp");
		checkCode(result, targetDir, "README.foo");
	}

	public void testInPortNoType() throws Exception{
		String resourceDir = "CXX\\Exception\\InPortNoType";
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
		
		List<DataPortParam> dataport = new ArrayList<DataPortParam>(); 
		dataport.add(new DataPortParam("in1", ""));
		rtcParam.setInports(dataport);
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));
		
		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		String targetDir = rootPath + "\\resource\\CXX\\Exception\\InPortNoType\\";
		checkCode(result, targetDir, "fooComp.cpp");
		checkCode(result, targetDir, "Makefile.foo");
		checkCode(result, targetDir, "foo.h");
		checkCode(result, targetDir, "foo.cpp");
		checkCode(result, targetDir, "README.foo");
	}

	public void testInPortNoName() throws Exception{
		String resourceDir = "CXX\\Exception\\InPortNoName";
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
		
		List<DataPortParam> dataport = new ArrayList<DataPortParam>(); 
		dataport.add(new DataPortParam("", "TimedShort"));
		rtcParam.setInports(dataport);
		rtcParam.setOriginalConsumerIdls(new ArrayList<String>(genParam.getConsumerIDLPathParams()));
		rtcParam.setOriginalProviderIdls(new ArrayList<String>(genParam.getProviderIDLPathParams()));
		
		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		String targetDir = rootPath + "\\resource\\CXX\\Exception\\InPortNoName\\";
		checkCode(result, targetDir, "fooComp.cpp");
		checkCode(result, targetDir, "Makefile.foo");
		checkCode(result, targetDir, "foo.h");
		checkCode(result, targetDir, "foo.cpp");
		checkCode(result, targetDir, "README.foo");
	}
}
