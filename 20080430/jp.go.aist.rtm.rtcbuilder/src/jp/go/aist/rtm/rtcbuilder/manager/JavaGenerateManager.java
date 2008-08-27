package jp.go.aist.rtm.rtcbuilder.manager;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.go.aist.rtm.rtcbuilder.IRtcBuilderConstantsJava;
import jp.go.aist.rtm.rtcbuilder.generator.GeneratedResult;
import jp.go.aist.rtm.rtcbuilder.generator.param.RtcParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.ServicePortInterfaceParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.ServicePortParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.idl.IdlFileParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.idl.ServiceClassParam;
import jp.go.aist.rtm.rtcbuilder.template.TemplateHelper;
import jp.go.aist.rtm.rtcbuilder.template.TemplateUtil;
import jp.go.aist.rtm.rtcbuilder.template.java.JavaConverter;

/**
 * CXXファイルの出力を制御するマネージャ
 */
public class JavaGenerateManager extends GenerateManager {

	public String getManagerKey() {
		return IRtcBuilderConstantsJava.LANG_JAVA;
	}

	/**
	 * ファイルを出力する
	 * 
	 * @param generatorParam	生成用パラメータ
	 * @return 出力結果のリスト
	 */
	public List<GeneratedResult> generateTemplateCode(RtcParam rtcParam) {

		InputStream ins = null;
		List<GeneratedResult> result = new ArrayList<GeneratedResult>();

		if (rtcParam.isLanguageExist(IRtcBuilderConstantsJava.LANG_JAVA) && rtcParam.getName() != null) {
			Map<String, Object> contextMap = new HashMap<String, Object>();
			contextMap.put("rtcParam", rtcParam);
			contextMap.put("tmpltHelper", new TemplateHelper());
			contextMap.put("javaConv", new JavaConverter());

			result = generateCompSource(contextMap, result);

			result = generateBuildFile(contextMap, result);
			result = generateRTCSource(contextMap, result);
			result = generateRTCImplSource(contextMap, result);
			result = generateRTCExtend(contextMap, result);

			//IDLファイル内に記述されているServiceClassParamを設定する
			for( IdlFileParam idlFileParam : rtcParam.getProviderIdlPathes() ) {
				for (ServiceClassParam serviceClassParam : rtcParam.getServiceClassParams()) {
					if( idlFileParam.getIdlPath().equals(serviceClassParam.getIdlPath()) )
						idlFileParam.addServiceClassParams(serviceClassParam);
				}
			}

			//Providerに参照されているServiceClassParamを作成する
			Set<ServiceClassParam> providerRefenencedServiceClassParam = new HashSet<ServiceClassParam>();
			for( ServicePortParam servicePort : rtcParam.getServicePorts() ) {
				for( ServicePortInterfaceParam serviceInterface : servicePort.getServicePortInterfaces() ) {
					if( serviceInterface.getDirection().equals(ServicePortInterfaceParam.INTERFACE_DIRECTION_PROVIDED) ) {
						ServiceClassParam find = null;
						for (ServiceClassParam serviceClassParam : rtcParam.getServiceClassParams()) {
							if (serviceInterface.getInterfaceType().equals(
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
			}

			for (IdlFileParam idlFileParm : rtcParam.getProviderIdlPathes()) {
				contextMap = new HashMap<String, Object>();
				contextMap.put("rtcParam", rtcParam);
				contextMap.put("idlFileParam", idlFileParm);
				contextMap.put("tmpltHelper", new TemplateHelper());
				contextMap.put("javaConv", new JavaConverter());
				
				result = generateSVCSource(contextMap, result);
				result = generateSVCExtend(contextMap, result);
			}
	
			try {
				if( ins != null) ins.close();
			} catch (Exception e) {
				throw new RuntimeException(e); // system error
			}
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
	protected List<GeneratedResult> generateCompSource(Map<String, Object> contextMap, List<GeneratedResult> result) {
		InputStream ins = null;

		ins = JavaGenerateManager.class.getClassLoader()	
			.getResourceAsStream("jp/go/aist/rtm/rtcbuilder/template/java/Java_Comp_src.template");
		result.add(TemplateUtil.createGeneratedResult(ins, contextMap, 
				((RtcParam)contextMap.get("rtcParam")).getName() + "Comp.java"));

		try {
			if( ins != null) ins.close();
		} catch (Exception e) {
			throw new RuntimeException(e); // system error
		}

		return result;
	}
	
	/**
	 * build.xmlを生成する
	 * 
	 * @param contextMap	生成用パラメータ
	 * @param result	生成結果格納先
	 * @return 出力結果のリスト
	 */
	protected List<GeneratedResult> generateBuildFile(Map<String, Object> contextMap, List<GeneratedResult> result) {
		InputStream ins = null;

		ins = JavaGenerateManager.class.getClassLoader()	
			.getResourceAsStream("jp/go/aist/rtm/rtcbuilder/template/java/Java_Build_src.template");
		result.add(TemplateUtil.createGeneratedResult(ins, contextMap, 
				"build_" + ((RtcParam)contextMap.get("rtcParam")).getName() +".xml"));

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
	 * @param result	生成用パラメータ
	 * @return 出力結果のリスト
	 */
	protected List<GeneratedResult> generateRTCSource(Map<String, Object> contextMap, List<GeneratedResult> result) {
		InputStream ins = null;

		ins = JavaGenerateManager.class.getClassLoader()	
			.getResourceAsStream("jp/go/aist/rtm/rtcbuilder/template/java/Java_RTC_Source_src.template");
		result.add(TemplateUtil.createGeneratedResult(ins, contextMap, 
				((RtcParam)contextMap.get("rtcParam")).getName() + ".java"));

		try {
			if( ins != null) ins.close();
		} catch (Exception e) {
			throw new RuntimeException(e); // system error
		}

		return result;
	}
	
	protected List<GeneratedResult> generateRTCExtend(Map<String, Object> contextMap, List<GeneratedResult> result) {
		InputStream ins = null;

		ins = JavaGenerateManager.class.getClassLoader()	
			.getResourceAsStream("jp/go/aist/rtm/rtcbuilder/template/java/Java_ClassPath_src.template");
		result.add(TemplateUtil.createGeneratedResult(ins, contextMap, ".classpath"));

		try {
			if( ins != null) ins.close();
		} catch (Exception e) {
			throw new RuntimeException(e); // system error
		}

		return result;
	}

	/**
	 * RTCImplのソース・ファイルを生成する
	 * 
	 * @param rtcParam	生成用パラメータ
	 * @param result	生成結果格納先
	 * @return 出力結果のリスト
	 */
	protected List<GeneratedResult> generateRTCImplSource(Map<String, Object> contextMap, List<GeneratedResult> result) {
		InputStream ins = null;

		ins = JavaGenerateManager.class.getClassLoader()	
			.getResourceAsStream("jp/go/aist/rtm/rtcbuilder/template/java/Java_RTC_Impl_Source_src.template");
		result.add(TemplateUtil.createGeneratedResult(ins, contextMap, 
				((RtcParam)contextMap.get("rtcParam")).getName() + "Impl.java"));

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
	 * @param contextMap	生成用パラメータ
	 * @param result	生成結果格納先
	 * @return 出力結果のリスト
	 */
	protected List<GeneratedResult> generateSVCSource(Map<String, Object> contextMap, List<GeneratedResult> result) {
		InputStream ins = null;

		for(ServiceClassParam serviceClass : ((IdlFileParam)contextMap.get("idlFileParam")).getServiceClassParams()) {
			ins = JavaGenerateManager.class.getClassLoader()	
					.getResourceAsStream("jp/go/aist/rtm/rtcbuilder/template/java/Java_SVC_Source_src.template");
			contextMap.put("serviceClassParam", serviceClass);
			result.add(TemplateUtil.createGeneratedResult(ins, contextMap, 
									TemplateHelper.getBasename(serviceClass.getName())
										+ TemplateHelper.getServiceImplSuffix() + ".java"));
	
			try {
				if( ins != null) ins.close();
			} catch (Exception e) {
				throw new RuntimeException(e); // system error
			}
		}

		return result;
	}
	protected List<GeneratedResult> generateSVCExtend(Map contextMap, List<GeneratedResult> result) {
		return result;		
	}
}
