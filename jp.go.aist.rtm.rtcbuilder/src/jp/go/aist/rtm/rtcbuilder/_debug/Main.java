package jp.go.aist.rtm.rtcbuilder._debug;

import java.util.ArrayList;
import java.util.List;

import jp.go.aist.rtm.rtcbuilder.CuiRtcBuilder;
import jp.go.aist.rtm.rtcbuilder.Generator;
import jp.go.aist.rtm.rtcbuilder.generator.param.DataPortParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.GeneratorParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.RtcParam;

public class Main {
	public static void main(String[] args) throws Exception {
		double hoge = 139.76916074752808;
		
		System.out.println(hoge);
		
		CuiRtcBuilder.main(new String[]{"--output=aga","--module-name=moduleName1"});
		
		GeneratorParam generatorParam = new GeneratorParam();
		RtcParam rtcParam = new RtcParam(generatorParam);

		rtcParam.setName("foo");
		rtcParam.setDescription("Thisisdesc");
		rtcParam.setVersion("1.5");
		rtcParam.setVender("TA");
		rtcParam.setCategory("Manip");
		rtcParam.setComponentType("STATIC");
		rtcParam.setActivityType("PERIODIC");
		rtcParam.setMaxInstance(5);
		rtcParam.setLanguage("python");

		List<DataPortParam> inports = new ArrayList<DataPortParam>();
		inports.add(new DataPortParam("pos", "TimeFloatSeq", "", 0));
		inports.add(new DataPortParam("vel", "TimeFloat", "", 0));
		rtcParam.setInports(inports);

		List<DataPortParam> outports = new ArrayList<DataPortParam>();
		outports.add(new DataPortParam("num", "TimeShort", "", 0));
		outports.add(new DataPortParam("acc", "TimeInt", "", 0));
		rtcParam.setOutports(outports);

		Generator rtcTemplate = new Generator();
		rtcTemplate.generateTemplateCode(generatorParam);

	}
}
