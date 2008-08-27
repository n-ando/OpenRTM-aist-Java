package jp.go.aist.rtm.rtctemplate._test.python;

import java.util.ArrayList;
import java.util.List;

import jp.go.aist.rtm.rtctemplate.Generator;
import jp.go.aist.rtm.rtctemplate._test.TestBase;
import jp.go.aist.rtm.rtctemplate.generator.GeneratedResult;
import jp.go.aist.rtm.rtctemplate.generator.param.ConfigSetParam;
import jp.go.aist.rtm.rtctemplate.generator.param.GeneratorParam;
import jp.go.aist.rtm.rtctemplate.generator.param.RtcParam;

public class ConfigSetTest extends TestBase {

	protected void setUp() throws Exception {
	}

	public void testConfigSet() throws Exception{
		String resourceDir = "Python\\ConfigSet\\configset1";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_PYTHON);
		rtcParam.setName("foo");
		rtcParam.setDescription("MDesc");
		rtcParam.setVersion("1.0.3");
		rtcParam.setVender("TA2");
		rtcParam.setCategory("manip2");
		rtcParam.setComponentType("STATIC2");
		rtcParam.setActivityType("PERIODIC2");
		rtcParam.setMaxInstance(3);

		genParam.getRtcParams().add(rtcParam);
		List<ConfigSetParam> configset = new ArrayList<ConfigSetParam>(); 
		configset.add(new ConfigSetParam("int_param0","int","0"));
		rtcParam.setConfigParams(configset);

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

	public void testConfigSet2() throws Exception{
		String resourceDir = "Python\\ConfigSet\\configset2";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_PYTHON);
		rtcParam.setName("foo");
		rtcParam.setDescription("MDesc");
		rtcParam.setVersion("1.0.3");
		rtcParam.setVender("TA2");
		rtcParam.setCategory("manip2");
		rtcParam.setComponentType("STATIC2");
		rtcParam.setActivityType("PERIODIC2");
		rtcParam.setMaxInstance(3);

		genParam.getRtcParams().add(rtcParam);
		List<ConfigSetParam> configset = new ArrayList<ConfigSetParam>(); 
		configset.add(new ConfigSetParam("int_param0","int","0"));
		configset.add(new ConfigSetParam("int_param1","int","1"));
		rtcParam.setConfigParams(configset);

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

	public void testConfigSet3() throws Exception{
		String resourceDir = "Python\\ConfigSet\\configset3";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_PYTHON);
		rtcParam.setName("foo");
		rtcParam.setDescription("MDesc");
		rtcParam.setVersion("1.0.3");
		rtcParam.setVender("TA2");
		rtcParam.setCategory("manip2");
		rtcParam.setComponentType("STATIC2");
		rtcParam.setActivityType("PERIODIC2");
		rtcParam.setMaxInstance(3);

		genParam.getRtcParams().add(rtcParam);
		List<ConfigSetParam> configset = new ArrayList<ConfigSetParam>(); 
		configset.add(new ConfigSetParam("int_param0","int","0"));
		configset.add(new ConfigSetParam("int_param1","int","1"));
		configset.add(new ConfigSetParam("double_param0","double","0.11"));
		configset.add(new ConfigSetParam("str_param0","String","hoge"));
		configset.add(new ConfigSetParam("str_param1","String","dara"));
		rtcParam.setConfigParams(configset);

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
	public void testConfigSet4() throws Exception{
		String resourceDir = "Python\\ConfigSet\\configset4";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_PYTHON);
		rtcParam.setName("foo");
		rtcParam.setDescription("MDesc");
		rtcParam.setVersion("1.0.3");
		rtcParam.setVender("TA2");
		rtcParam.setCategory("manip2");
		rtcParam.setComponentType("STATIC2");
		rtcParam.setActivityType("PERIODIC2");
		rtcParam.setMaxInstance(3);

		genParam.getRtcParams().add(rtcParam);
		List<ConfigSetParam> configset = new ArrayList<ConfigSetParam>(); 
		configset.add(new ConfigSetParam("int_param0","int","0"));
		configset.add(new ConfigSetParam("int_param1","int","1"));
		configset.add(new ConfigSetParam("double_param0","double","0.11"));
		configset.add(new ConfigSetParam("str_param0","String","hoge"));
		configset.add(new ConfigSetParam("str_param1","String","dara"));
		configset.add(new ConfigSetParam("vector_param0","double","0.0,1.0,2.0,3.0"));
		rtcParam.setConfigParams(configset);

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
