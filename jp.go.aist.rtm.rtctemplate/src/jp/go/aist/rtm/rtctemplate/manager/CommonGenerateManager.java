package jp.go.aist.rtm.rtctemplate.manager;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.go.aist.rtm.rtctemplate.generator.GeneratedResult;
import jp.go.aist.rtm.rtctemplate.generator.param.GeneratorParam;
import jp.go.aist.rtm.rtctemplate.generator.param.RtcParam;
import jp.go.aist.rtm.rtctemplate.template.TemplateUtil;

/**
 * 一般ファイルの出力を制御するマネージャ
 */
public class CommonGenerateManager implements GenerateManager {

	/**
	 * ファイルを出力する
	 * 
	 * @param generatorParam
	 * @return 出力結果のリスト
	 */
	public List<GeneratedResult> doGenerate(GeneratorParam generatorParam) {
		List<GeneratedResult> result = new ArrayList<GeneratedResult>();

		for (RtcParam rtcParam : generatorParam.getRtcParams()) {
			result = generateReadMe(rtcParam, result);
			result = generateCommonExtend(rtcParam, result);
		}

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

		ins = CommonGenerateManager.class.getClassLoader()
			.getResourceAsStream("jp/go/aist/rtm/rtctemplate/template/common/README_src.template");
		result.add(TemplateUtil.createGeneratedResult(ins, "rtcParam", rtcParam, "README." + rtcParam.getName()));

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
