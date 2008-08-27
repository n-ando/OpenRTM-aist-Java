package jp.go.aist.rtm.rtctemplate._test._042.cxx;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import jp.go.aist.rtm.rtctemplate.generator.param.DataPortParam;
import jp.go.aist.rtm.rtctemplate.generator.param.GeneratorParam;
import jp.go.aist.rtm.rtctemplate.generator.param.RtcParam;
import jp.go.aist.rtm.rtctemplate.generator.param.ServiceReferenceParam;
import jp.go.aist.rtm.rtctemplate.generator.param.idl.ServiceArgumentParam;
import jp.go.aist.rtm.rtctemplate.generator.param.idl.ServiceClassParam;
import jp.go.aist.rtm.rtctemplate.generator.param.idl.ServiceMethodParam;
import jp.go.aist.rtm.rtctemplate.template.TemplateUtil;
import jp.go.aist.rtm.rtctemplate.template.cpp.CXXConverter;

public class TemplateTest {
	public static void main(String[] args) {
		GeneratorParam generatorParam = new GeneratorParam();
		generatorParam.addProviderIDLPath("consumerIdlPath.idl");
		generatorParam.addConsumerIDLPath("consumerIdlPath.idl");

		ServiceClassParam serviceClassParamA = new ServiceClassParam();
		serviceClassParamA.setParent(generatorParam);
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
		generatorParam.getServiceClassParams().add(serviceClassParamA);

		ServiceClassParam serviceClassParamB = new ServiceClassParam();
		serviceClassParamB.setParent(generatorParam);
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
		generatorParam.getServiceClassParams().add(serviceClassParamB);

		RtcParam rtcParam = new RtcParam(generatorParam);
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
				"!portTypeA!"), new DataPortParam("!inPortB!", "!portTypeB!")));
		rtcParam
				.setOutports(Arrays.asList(new DataPortParam("!outPortA!",
						"!portTypeA!"), new DataPortParam("!outPortB!",
						"!portTypeB!")));
		rtcParam.setProviderReferences(Arrays.asList(new ServiceReferenceParam(
				"!interfaceA!", "!providerA!", "!serviceClassParamAName!",
				rtcParam), new ServiceReferenceParam("!interfaceB!",
				"!providerB!", "!serviceClassParamBName!", rtcParam)));
		rtcParam.setConsumerReferences(Arrays.asList(new ServiceReferenceParam(
				"!interfaceA!", "!consumerA!", "!serviceClassParamAName!",
				rtcParam), new ServiceReferenceParam("!interfaceB!",
				"!consumerB!", "!serviceClassParamBName!", rtcParam)));

		Map map = new HashMap();
		map.put("serviceClassParam", serviceClassParamA);
		map.put("cXXUtil", new CXXConverter());

//		System.out
//				.println(TemplateUtil
//						.generate(
//								"jp/go/aist/rtm/rtctemplate/template/cpp/CXX_SVC_Source_src.template",
//								map));
		System.out.println(TemplateUtil.generate(
				TemplateTest.class.getClassLoader().getResourceAsStream("jp/go/aist/rtm/rtctemplate/template/cpp/CXX_SVC_Source_src.template"),
						map));
	}

}
