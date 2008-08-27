package jp.go.aist.rtm.rtcbuilder._test.cxx;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import jp.go.aist.rtm.rtcbuilder.generator.param.DataPortParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.GeneratorParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.RtcParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.idl.ServiceArgumentParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.idl.ServiceClassParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.idl.ServiceMethodParam;
import jp.go.aist.rtm.rtcbuilder.template.TemplateUtil;
import jp.go.aist.rtm.rtcbuilder.template.cpp.CXXConverter;

public class TemplateTest {
	public static void main(String[] args) {
		GeneratorParam generatorParam = new GeneratorParam();
		RtcParam rtcParam = new RtcParam(generatorParam);

		ServiceClassParam serviceClassParamA = new ServiceClassParam();
		serviceClassParamA.setParent(rtcParam);
		serviceClassParamA.setName("!MODULE!::!serviceClassParamAName!");
		serviceClassParamA.setIdlPath("!serviceClassParamAidlPath.idl!");
		ServiceMethodParam serviceMethodParamAA = new ServiceMethodParam();
		serviceMethodParamAA.setName("!serviceMethodParamAAMethodName!");
		serviceMethodParamAA.setType("!serviceMethodParamAAMethodType!");
		ServiceArgumentParam serviceArgumentParamAAA = new ServiceArgumentParam();
		serviceArgumentParamAAA.setName("serviceArgumentParamAAAName");
		serviceArgumentParamAAA.setType("float");
		serviceMethodParamAA.getArguments().add(serviceArgumentParamAAA);
		ServiceArgumentParam serviceArgumentParamAAB = new ServiceArgumentParam();
		serviceArgumentParamAAB.setName("serviceArgumentParamAABName");
		serviceArgumentParamAAB.setType("serviceArgumentParamAABType");
		serviceMethodParamAA.getArguments().add(serviceArgumentParamAAB);
		serviceClassParamA.getMethods().add(serviceMethodParamAA);
		ServiceMethodParam serviceMethodParamAB = new ServiceMethodParam();
		serviceMethodParamAB.setName("!serviceMethodParamABMethodName!");
		serviceMethodParamAB.setType("double");
		serviceClassParamA.getMethods().add(serviceMethodParamAB);
		rtcParam.getServiceClassParams().add(serviceClassParamA);

		ServiceClassParam serviceClassParamB = new ServiceClassParam();
		serviceClassParamB.setParent(rtcParam);
		serviceClassParamB.setName("!serviceClassParamBName!");
		serviceClassParamB.setIdlPath("!serviceClassParamBidlPath.idl!");
		ServiceMethodParam serviceMethodParamBA = new ServiceMethodParam();
		serviceMethodParamBA.setName("!serviceMethodParamBAMethodName!");
		serviceMethodParamBA.setType("!serviceMethodParamBAMethodType!");
		ServiceArgumentParam serviceArgumentParamBAA = new ServiceArgumentParam();
		serviceArgumentParamBAA.setName("serviceArgumentParamBAAName");
		serviceArgumentParamBAA.setType("serviceArgumentParamBAAType");
		serviceMethodParamBA.getArguments().add(serviceArgumentParamBAA);
		serviceClassParamB.getMethods().add(serviceMethodParamBA);
		ServiceMethodParam serviceMethodParamBB = new ServiceMethodParam();
		serviceMethodParamBB.setName("!serviceMethodParamBBMethodName!");
		serviceMethodParamBB.setType("!serviceMethodParamBBMethodType!");
		serviceClassParamB.getMethods().add(serviceMethodParamBB);
		rtcParam.getServiceClassParams().add(serviceClassParamB);

		rtcParam.setName("!ModuleNameA!");
		rtcParam.setDescription("!DesctiptionA!");
		rtcParam.setVersion("!Version!");
		rtcParam.setVender("!VenderA!");
		rtcParam.setCategory("!Category!");
		rtcParam.setComponentType("!ComponentType!");
		rtcParam.setActivityType("!ActivityTypeA!");
		rtcParam.setMaxInstance(777);
		rtcParam.setLanguage("c++");
		rtcParam.setInports(Arrays.asList(new DataPortParam("!inPortA!",
				"!portTypeA!", "", 0), new DataPortParam("!inPortB!", "!portTypeB!", "", 0)));
		rtcParam
				.setOutports(Arrays.asList(new DataPortParam("!outPortA!",
						"!portTypeA!", "", 0), new DataPortParam("!outPortB!",
						"!portTypeB!", "", 0)));

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("serviceClassParam", serviceClassParamA);
		map.put("cXXUtil", new CXXConverter());

		System.out.println(TemplateUtil.generate(
				TemplateTest.class.getClassLoader().getResourceAsStream("jp/go/aist/rtm/rtctemplate/template/cpp/CXX_SVC_Source_src.template"),
						map));
	}

}
