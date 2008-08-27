package jp.go.aist.rtm.rtctemplate._debug;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import jp.go.aist.rtm.rtctemplate.CuiRtcTemplate;
import jp.go.aist.rtm.rtctemplate.Generator;
import jp.go.aist.rtm.rtctemplate.corba.idl.parser.ParseException;
import jp.go.aist.rtm.rtctemplate.generator.GeneratedResult;
import jp.go.aist.rtm.rtctemplate.generator.param.DataPortParam;
import jp.go.aist.rtm.rtctemplate.generator.param.GeneratorParam;
import jp.go.aist.rtm.rtctemplate.generator.param.RtcParam;
import jp.go.aist.rtm.rtctemplate.generator.param.ServiceReferenceParam;

public class Main {
	public static void main(String[] args) throws Exception {
		double hoge = 139.76916074752808;
		
		System.out.println(hoge);
		
		CuiRtcTemplate.main(new String[]{"--output=aga","--module-name=moduleName1"});
		
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
		inports.add(new DataPortParam("pos", "TimeFloatSeq"));
		inports.add(new DataPortParam("vel", "TimeFloat"));
		rtcParam.setInports(inports);

		List<DataPortParam> outports = new ArrayList<DataPortParam>();
		outports.add(new DataPortParam("num", "TimeShort"));
		outports.add(new DataPortParam("acc", "TimeInt"));
		rtcParam.setOutports(outports);

		generatorParam
				.addProviderIDLPath("C:\\Documents and Settings\\yamashita.TA\\workspace\\jp.go.aist.rtm.rtctemplate\\"
						+ "test\\MyService.idl");
		Vector service = new Vector();
		service.add(new ServiceReferenceParam("MyServiceIF", "MySVCS",
				"MySVCT", rtcParam));
		rtcParam.setProviderReferences(service);
		// rtcParam.setServiceImplSuffix("IMPL");
		// rtcParam.setServiceSkelSuffix("SKEL2");
		// rtcParam.setServiceStubSuffix("STUB2");

		Generator rtcTemplate = new Generator();
		List<GeneratedResult> result = rtcTemplate.doGenerate(generatorParam);

	}

	private static String readFile(String fileName) {
		StringBuffer stbRet = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));

			String str;
			while ((str = br.readLine()) != null) {
				stbRet.append(str + "\n");
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stbRet.toString();
	}
}
