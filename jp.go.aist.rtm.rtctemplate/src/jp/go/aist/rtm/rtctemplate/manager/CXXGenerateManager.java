package jp.go.aist.rtm.rtctemplate.manager;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.go.aist.rtm.rtctemplate.generator.GeneratedResult;
import jp.go.aist.rtm.rtctemplate.generator.param.GeneratorParam;
import jp.go.aist.rtm.rtctemplate.generator.param.RtcParam;
import jp.go.aist.rtm.rtctemplate.generator.param.ServiceReferenceParam;
import jp.go.aist.rtm.rtctemplate.generator.param.idl.IdlFileParam;
import jp.go.aist.rtm.rtctemplate.generator.param.idl.ServiceClassParam;
import jp.go.aist.rtm.rtctemplate.template.TemplateUtil;
import jp.go.aist.rtm.rtctemplate.template.cpp.CXXConverter;

/**
 * CXXファイルの出力を制御するマネージャ
 */
public class CXXGenerateManager implements GenerateManager {

	/**
	 * ファイルを出力する
	 * 
	 * @param generatorParam	生成用パラメータ
	 * @return 出力結果のリスト
	 */
	public List<GeneratedResult> doGenerate(GeneratorParam generatorParam) {
		InputStream ins = null;

		List<String> allIdlPathes = new ArrayList<String>();
		List<IdlFileParam> allIdlFileParams = new ArrayList<IdlFileParam>();

		List<GeneratedResult> result = new ArrayList<GeneratedResult>();
		for (RtcParam rtcParam : generatorParam.getRtcParams()) {
			Map contextMap = new HashMap();
			contextMap.put("rtcParam", rtcParam);
			contextMap.put("cXXConv", new CXXConverter());
			if (rtcParam.isLanguageExist(RtcParam.LANG_CPP)) {
				result = generateCompSource(rtcParam, result);

				if (rtcParam.getName() != null) {
					result = generateMakeFile(rtcParam, contextMap, result);
					result = generateRTCHeader(rtcParam, contextMap, result);
					result = generateRTCSource(rtcParam, result);
					result = generateRTCExtend(rtcParam, result);

					for( String idlFile : rtcParam.getProviderIdlPathes() ) {
						if( !allIdlPathes.contains(idlFile) )
							allIdlPathes.add(idlFile);
							allIdlFileParams.add(new IdlFileParam(idlFile, generatorParam));
					}
				}
			}
		}

		//IDLファイル内に記述されているServiceClassParamを設定する
		for( IdlFileParam idlFileParam : allIdlFileParams ) {
			for (ServiceClassParam serviceClassParam : generatorParam.getServiceClassParams()) {
				if( idlFileParam.getIdlPath().equals(serviceClassParam.getIdlPath()) )
					idlFileParam.addServiceClassParams(serviceClassParam);
			}
		}

		//Providerに参照されているServiceClassParamを作成する
		Set<ServiceClassParam> providerRefenencedServiceClassParam = new HashSet<ServiceClassParam>();
		for (RtcParam param : generatorParam.getRtcParams()) {
			List<ServiceReferenceParam> serviceReferenceParamList = new ArrayList<ServiceReferenceParam>();
			serviceReferenceParamList.addAll(param.getProviderReferences());

			for (ServiceReferenceParam serviceReferenceParam : serviceReferenceParamList) {
				ServiceClassParam find = null;
				for (ServiceClassParam serviceClassParam : generatorParam
						.getServiceClassParams()) {
					if (serviceReferenceParam.getType().equals(
							serviceClassParam.getName())) {
						find = serviceClassParam;
						break;
					}
				}

				if (find != null) {
					providerRefenencedServiceClassParam.add(find);
				}
			}
		}

		for (IdlFileParam idlFileParm : allIdlFileParams) {
			Map contextMap = new HashMap();
			contextMap.put("idlFileParam", idlFileParm);
			contextMap.put("cXXConv", new CXXConverter());
			
			result = generateSVCHeader(idlFileParm, contextMap, result);
			result = generateSVCSource(idlFileParm, contextMap, result);
		}

		try {
			if( ins != null) ins.close();
		} catch (Exception e) {
			throw new RuntimeException(e); // system error
		}

		return result;
	}
	
	/**
	 * Standalone componentを生成する
	 * 
	 * @param rtcParam	生成用パラメータ
	 * @param result	生成結果格納先
	 * @return 出力結果のリスト
	 */
	protected List<GeneratedResult> generateCompSource(RtcParam rtcParam, List<GeneratedResult> result) {
		InputStream ins = null;

		ins = CXXGenerateManager.class.getClassLoader()	
			.getResourceAsStream("jp/go/aist/rtm/rtctemplate/template/cpp/CXX_Comp_src.template");
		result.add(TemplateUtil.createGeneratedResult(ins, "rtcParam", rtcParam, rtcParam.getName() + "Comp.cpp"));

		try {
			if( ins != null) ins.close();
		} catch (Exception e) {
			throw new RuntimeException(e); // system error
		}

		return result;
	}
	
	/**
	 * Makefileを生成する
	 * 
	 * @param rtcParam	生成用パラメータ
	 * @param contextMap	生成元情報
	 * @param result	生成結果格納先
	 * @return 出力結果のリスト
	 */
	protected List<GeneratedResult> generateMakeFile(RtcParam rtcParam, Map contextMap, List<GeneratedResult> result) {
		InputStream ins = null;

		ins = CXXGenerateManager.class.getClassLoader()	
			.getResourceAsStream("jp/go/aist/rtm/rtctemplate/template/cpp/CXX_Makefile_src.template");
		result.add(TemplateUtil.createGeneratedResult(ins, contextMap, "Makefile." + rtcParam.getName()));

		try {
			if( ins != null) ins.close();
		} catch (Exception e) {
			throw new RuntimeException(e); // system error
		}

		return result;
	}
	
	/**
	 * RTCのヘッダ・ファイルを生成する
	 * 
	 * @param rtcParam	生成用パラメータ
	 * @param contextMap	生成元情報
	 * @param result	生成結果格納先
	 * @return 出力結果のリスト
	 */
	protected List<GeneratedResult> generateRTCHeader(RtcParam rtcParam, Map contextMap, List<GeneratedResult> result) {
		InputStream ins = null;

		ins = CXXGenerateManager.class.getClassLoader()	
			.getResourceAsStream("jp/go/aist/rtm/rtctemplate/template/cpp/CXX_RTC_Header_src.template");
		result.add(TemplateUtil.createGeneratedResult(ins, contextMap, rtcParam.getName() + ".h"));

		try {
			if( ins != null) ins.close();
		} catch (Exception e) {
			throw new RuntimeException(e); // system error
		}

		return result;
	}
	
	/**
	 * RTCのソース・ファイルを生成する
	 * 
	 * @param rtcParam	生成用パラメータ
	 * @param result	生成結果格納先
	 * @return 出力結果のリスト
	 */
	protected List<GeneratedResult> generateRTCSource(RtcParam rtcParam, List<GeneratedResult> result) {
		InputStream ins = null;

		ins = CXXGenerateManager.class.getClassLoader()	
			.getResourceAsStream("jp/go/aist/rtm/rtctemplate/template/cpp/CXX_RTC_Source_src.template");
		result.add(TemplateUtil.createGeneratedResult(ins, "rtcParam", rtcParam, rtcParam.getName() + ".cpp"));

		try {
			if( ins != null) ins.close();
		} catch (Exception e) {
			throw new RuntimeException(e); // system error
		}

		return result;
	}
	
	protected List<GeneratedResult> generateRTCExtend(RtcParam rtcParam, List<GeneratedResult> result) {
		return result;		
	}
	/**
	 * Service implementation headerを生成する
	 * 
	 * @param idlFileParm	生成対象のIDLファイルパラメータ
	 * @param contextMap	生成元情報
	 * @param result	生成結果格納先
	 * @return 出力結果のリスト
	 */
	protected List<GeneratedResult> generateSVCHeader(IdlFileParam idlFileParm, Map contextMap, List<GeneratedResult> result) {
		InputStream ins = null;

		ins = CXXGenerateManager.class.getClassLoader()	
			.getResourceAsStream("jp/go/aist/rtm/rtctemplate/template/cpp/CXX_SVC_Header_src.template");
		result.add(TemplateUtil.createGeneratedResult(ins, contextMap, 
								new CXXConverter().getBasename(idlFileParm.getIdlFileNoExt())
									+ idlFileParm.getParent().getServiceImplSuffix() + ".h"));

		try {
			if( ins != null) ins.close();
		} catch (Exception e) {
			throw new RuntimeException(e); // system error
		}

		return result;
	}

	/**
	 * Service implementation codeを生成する
	 * 
	 * @param idlFileParm	生成対象のIDLファイルパラメータ
	 * @param contextMap	生成元情報
	 * @param result	生成結果格納先
	 * @return 出力結果のリスト
	 */
	protected List<GeneratedResult> generateSVCSource(IdlFileParam idlFileParm, Map contextMap, List<GeneratedResult> result) {
		InputStream ins = null;

		ins = CXXGenerateManager.class.getClassLoader()	
			.getResourceAsStream("jp/go/aist/rtm/rtctemplate/template/cpp/CXX_SVC_Source_src.template");
		result.add(TemplateUtil.createGeneratedResult(ins, contextMap, 
								new CXXConverter().getBasename(idlFileParm.getIdlFileNoExt())
									+ idlFileParm.getParent().getServiceImplSuffix() + ".cpp"));

		try {
			if( ins != null) ins.close();
		} catch (Exception e) {
			throw new RuntimeException(e); // system error
		}

		return result;
	}
	protected List<GeneratedResult> generateSVCExtend(IdlFileParam idlFileParm, Map contextMap, List<GeneratedResult> result) {
		return result;		
	}
}
