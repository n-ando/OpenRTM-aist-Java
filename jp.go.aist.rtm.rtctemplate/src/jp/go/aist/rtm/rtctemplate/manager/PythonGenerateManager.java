package jp.go.aist.rtm.rtctemplate.manager;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import jp.go.aist.rtm.rtctemplate.generator.GeneratedResult;
import jp.go.aist.rtm.rtctemplate.generator.param.GeneratorParam;
import jp.go.aist.rtm.rtctemplate.generator.param.RtcParam;
import jp.go.aist.rtm.rtctemplate.template.TemplateUtil;

/**
 * CXXファイルの出力を制御するマネージャ
 */
public class PythonGenerateManager implements GenerateManager {

	/**
	 * ファイルを出力する
	 * 
	 * @param generatorParam
	 * @return 出力結果のリスト
	 */
	public List<GeneratedResult> doGenerate(GeneratorParam generatorParam) {

		List<GeneratedResult> result = new ArrayList<GeneratedResult>();

		for (RtcParam rtcParam : generatorParam.getRtcParams()) {
			if (rtcParam.isLanguageExist(RtcParam.LANG_PYTHON)) {
				result = generatePythonSource(rtcParam, result);
				result = generateCommonExtend(rtcParam, result);
			}
		}

		return result;
	}
	
	/**
	 * Pythonコードを生成する
	 * 
	 * @param rtcParam	生成用パラメータ
	 * @param result	生成結果格納先
	 * @return 出力結果のリスト
	 */
	protected List<GeneratedResult> generatePythonSource(RtcParam rtcParam, List<GeneratedResult> result) {
		InputStream ins = null;

		ins = CommonGenerateManager.class.getClassLoader()
			.getResourceAsStream("jp/go/aist/rtm/rtctemplate/template/python/Py_src.template");
		result.add(TemplateUtil.createGeneratedResult(ins, "rtcParam", rtcParam, rtcParam.getName() + ".py"));

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
