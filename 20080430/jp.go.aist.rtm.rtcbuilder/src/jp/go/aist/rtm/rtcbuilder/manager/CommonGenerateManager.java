package jp.go.aist.rtm.rtcbuilder.manager;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.go.aist.rtm.rtcbuilder.generator.GeneratedResult;
import jp.go.aist.rtm.rtcbuilder.generator.param.RtcParam;
import jp.go.aist.rtm.rtcbuilder.template.TemplateHelper;
import jp.go.aist.rtm.rtcbuilder.template.TemplateUtil;

/**
 * 一般ファイルの出力を制御するマネージャ
 */
public class CommonGenerateManager extends GenerateManager {

	public String getManagerKey() {
		return "Common";
	}

	/**
	 * ファイルを出力する
	 * 
	 * @param generatorParam
	 * @return 出力結果のリスト
	 */
	public List<GeneratedResult> generateTemplateCode(RtcParam rtcParam) {
		List<GeneratedResult> result = new ArrayList<GeneratedResult>();

		result = generateReadMe(rtcParam, result);
		if(rtcParam.getExecutionRate() > 0.0) {
			result = generateRTCConf(rtcParam, result);				
		}
		result = generateCommonExtend(rtcParam, result);

		return result;
	}
	
	/**
	 * ReadMeを生成する
	 * 
	 * @param rtcParam	生成用パラメータ
	 * @param contextMap	生成元情報
	 * @param result	生成結果格納先
	 * @return 出力結果のリスト
	 */
	protected List<GeneratedResult> generateReadMe(RtcParam rtcParam, List<GeneratedResult> result) {
		InputStream ins = null;

		Map<String, Object> contextMap = new HashMap<String, Object>();
		contextMap.put("rtcParam", rtcParam);
		contextMap.put("tmpltHelper", new TemplateHelper());

		ins = CommonGenerateManager.class.getClassLoader()
			.getResourceAsStream("jp/go/aist/rtm/rtcbuilder/template/common/README_src.template");
		result.add(TemplateUtil.createGeneratedResult(ins, contextMap, "README." + rtcParam.getName()));

		try {
			if( ins != null) ins.close();
		} catch (Exception e) {
			throw new RuntimeException(e); // system error
		}

		return result;
	}
	
	protected List<GeneratedResult> generateRTCConf(RtcParam rtcParam, List<GeneratedResult> result) {
		InputStream ins = null;

		ins = CommonGenerateManager.class.getClassLoader()	
			.getResourceAsStream("jp/go/aist/rtm/rtcbuilder/template/common/rtc_conf.template");
		result.add(TemplateUtil.createGeneratedResult(ins, "rtcParam", rtcParam, "rtc.conf"));

		try {
			if( ins != null) ins.close();
		} catch (Exception e) {
			throw new RuntimeException(e); // system error
		}

		return result;
	}

	protected List<GeneratedResult> generateCommonExtend(RtcParam rtcParam, List<GeneratedResult> result) {
		return result;		
	}
}
