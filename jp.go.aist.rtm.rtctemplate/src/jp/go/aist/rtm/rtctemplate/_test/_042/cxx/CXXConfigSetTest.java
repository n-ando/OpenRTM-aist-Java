package jp.go.aist.rtm.rtctemplate._test._042.cxx;

import java.util.ArrayList;
import java.util.List;

import jp.go.aist.rtm.rtctemplate.Generator;
import jp.go.aist.rtm.rtctemplate._test.TestBase;
import jp.go.aist.rtm.rtctemplate.generator.GeneratedResult;
import jp.go.aist.rtm.rtctemplate.generator.param.ConfigSetParam;
import jp.go.aist.rtm.rtctemplate.generator.param.GeneratorParam;
import jp.go.aist.rtm.rtctemplate.generator.param.RtcParam;

public class CXXConfigSetTest extends TestBase {

	protected void setUp() throws Exception {
	}

	public void testConfigSet() throws Exception{
		String resourceDir = "042\\CXX\\ConfigSet\\configset1";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPP);
		rtcParam.setName("foo");
		rtcParam.setDescription("MDesc");
		rtcParam.setVersion("1.0.3");
		rtcParam.setVender("TA2");
		rtcParam.setCategory("manip2");
		rtcParam.setComponentType("STATIC2");
		rtcParam.setActivityType("PERIODIC2");
		rtcParam.setMaxInstance(3);
		rtcParam.setRtmVersion("0.4.2");
		rtcParam.setIsTest(true);

		genParam.getRtcParams().add(rtcParam);
		List<ConfigSetParam> configset = new ArrayList<ConfigSetParam>(); 
		configset.add(new ConfigSetParam("int_param0","int","0"));
		rtcParam.setConfigParams(configset);

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		String targetDir = rootPath + "\\resource\\042\\CXX\\ConfigSet\\configset1\\";
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
//		checkCode(result, targetDir, "rtm_config.vsprops");
//		checkCode(result, targetDir, "rtm_config_omni412.vsprops");
		//
		checkCode(result, targetDir, "copyprops.bat");
		checkCode(result, targetDir, "user_config.vsprops");
	}

	public void testConfigSet2() throws Exception{
		String resourceDir = "042\\CXX\\ConfigSet\\configset2";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPP);
		rtcParam.setName("foo");
		rtcParam.setDescription("MDesc");
		rtcParam.setVersion("1.0.3");
		rtcParam.setVender("TA2");
		rtcParam.setCategory("manip2");
		rtcParam.setComponentType("STATIC2");
		rtcParam.setActivityType("PERIODIC2");
		rtcParam.setMaxInstance(3);
		rtcParam.setRtmVersion("0.4.2");
		rtcParam.setIsTest(true);

		genParam.getRtcParams().add(rtcParam);
		List<ConfigSetParam> configset = new ArrayList<ConfigSetParam>(); 
		configset.add(new ConfigSetParam("int_param0","int","0"));
		configset.add(new ConfigSetParam("int_param1","int","1"));
		rtcParam.setConfigParams(configset);

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		String targetDir = rootPath + "\\resource\\042\\CXX\\ConfigSet\\configset2\\";
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
//		checkCode(result, targetDir, "rtm_config.vsprops");
//		checkCode(result, targetDir, "rtm_config_omni412.vsprops");
		//
		checkCode(result, targetDir, "copyprops.bat");
		checkCode(result, targetDir, "user_config.vsprops");
	}

	public void testConfigSet3() throws Exception{
		String resourceDir = "042\\CXX\\ConfigSet\\configset3";
		GeneratorParam genParam = new GeneratorParam();
		genParam.setOutputDirectory(rootPath + "\\resource\\work");
		RtcParam rtcParam = new RtcParam(genParam);
		rtcParam.setLanguage(rtcParam.LANG_CPP);
		rtcParam.setName("foo");
		rtcParam.setDescription("MDesc");
		rtcParam.setVersion("1.0.3");
		rtcParam.setVender("TA2");
		rtcParam.setCategory("manip2");
		rtcParam.setComponentType("STATIC2");
		rtcParam.setActivityType("PERIODIC2");
		rtcParam.setRtmVersion("0.4.2");
		rtcParam.setIsTest(true);
		rtcParam.setMaxInstance(3);

		genParam.getRtcParams().add(rtcParam);
		List<ConfigSetParam> configset = new ArrayList<ConfigSetParam>(); 
		configset.add(new ConfigSetParam("int_param0","int","0"));
		configset.add(new ConfigSetParam("int_param1","int","1"));
		configset.add(new ConfigSetParam("double_param0","double","0.11"));
		configset.add(new ConfigSetParam("str_param0","std::string","hoge"));
		configset.add(new ConfigSetParam("str_param1","std::string","dara"));
		rtcParam.setConfigParams(configset);

		Generator generator = new Generator();
		List<GeneratedResult> result = generator.doGenerate(genParam);

		String targetDir = rootPath + "\\resource\\042\\CXX\\ConfigSet\\configset3\\";
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
//		checkCode(result, targetDir, "rtm_config.vsprops");
//		checkCode(result, targetDir, "rtm_config_omni412.vsprops");
		//
		checkCode(result, targetDir, "copyprops.bat");
		checkCode(result, targetDir, "user_config.vsprops");
	}
}
