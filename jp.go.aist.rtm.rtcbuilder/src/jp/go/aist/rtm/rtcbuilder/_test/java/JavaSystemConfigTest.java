package jp.go.aist.rtm.rtcbuilder._test.java;

import java.util.ArrayList;
import java.util.List;

import jp.go.aist.rtm.rtcbuilder.Generator;
import jp.go.aist.rtm.rtcbuilder.IRtcBuilderConstantsJava;
import jp.go.aist.rtm.rtcbuilder._test.TestBase;
import jp.go.aist.rtm.rtcbuilder.generator.GeneratedResult;
import jp.go.aist.rtm.rtcbuilder.generator.param.ConfigParameterParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.ConfigSetParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.GeneratorParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.RtcParam;

public class JavaSystemConfigTest extends TestBase {

	protected void setUp() throws Exception {
	}

	public void testExecutionContext() throws Exception{
		GeneratorParam genParam = new GeneratorParam();
		RtcParam rtcParam = new RtcParam(genParam, true);
		rtcParam.setOutputProject(rootPath + "\\resource\\work");
		rtcParam.setLanguage(IRtcBuilderConstantsJava.LANG_JAVA);
		rtcParam.setName("foo");
		rtcParam.setDescription("MDesc");
		rtcParam.setVersion("1.0.3");
		rtcParam.setVender("TA2");
		rtcParam.setCategory("manip2");
		rtcParam.setComponentType("STATIC2");
		rtcParam.setActivityType("PERIODIC2");
		rtcParam.setMaxInstance(3);
		rtcParam.setExecutionRate(5.0);

		genParam.getRtcParams().add(rtcParam);
		List<ConfigSetParam> configset = new ArrayList<ConfigSetParam>(); 
		configset.add(new ConfigSetParam("int_param0","int","","0"));
		configset.add(new ConfigSetParam("int_param1","int","","1"));
		configset.add(new ConfigSetParam("double_param0","double","","0.11"));
		configset.add(new ConfigSetParam("str_param0","String","","hoge"));
		configset.add(new ConfigSetParam("str_param1","String","","dara"));
		rtcParam.setConfigParams(configset);

		List<ConfigParameterParam> configparam = new ArrayList<ConfigParameterParam>(); 
		configparam.add(new ConfigParameterParam("timer.tick","0.1"));
		configparam.add(new ConfigParameterParam("naming.update.interval","10.0"));
		rtcParam.setConfigParameterParams(configparam);

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.generateTemplateCode(genParam);

		String resourceDir = rootPath +  "\\resource\\Java\\SystemConfig\\";

		checkCode(result, resourceDir, "fooComp.java");
		checkCode(result, resourceDir, "build_foo.xml");
		checkCode(result, resourceDir, "foo.java");
		checkCode(result, resourceDir, "fooImpl.java");
		checkCode(result, resourceDir, "README.foo");
		checkCode(result, resourceDir, "rtc.conf");
	}
}
