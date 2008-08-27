package jp.go.aist.rtm.rtctemplate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.go.aist.rtm.rtctemplate.corba.idl.parser.IDLParser;
import jp.go.aist.rtm.rtctemplate.corba.idl.parser.ParseException;
import jp.go.aist.rtm.rtctemplate.corba.idl.parser.syntaxtree.specification;
import jp.go.aist.rtm.rtctemplate.generator.GeneratedResult;
import jp.go.aist.rtm.rtctemplate.generator.IDLParamConverter;
import jp.go.aist.rtm.rtctemplate.generator.PreProcessor;
import jp.go.aist.rtm.rtctemplate.generator.param.DataPortParam;
import jp.go.aist.rtm.rtctemplate.generator.param.GeneratorParam;
import jp.go.aist.rtm.rtctemplate.generator.param.RtcParam;
import jp.go.aist.rtm.rtctemplate.generator.param.ServiceReferenceParam;
import jp.go.aist.rtm.rtctemplate.generator.param.idl.ServiceClassParam;
import jp.go.aist.rtm.rtctemplate.generator.parser.MergeBlockParser;
import jp.go.aist.rtm.rtctemplate.manager.CXXGenerateManager;
import jp.go.aist.rtm.rtctemplate.manager.CXXWinGenerateManager;
import jp.go.aist.rtm.rtctemplate.manager.CommonGenerateManager;
import jp.go.aist.rtm.rtctemplate.manager.GenerateManager;
import jp.go.aist.rtm.rtctemplate.manager.JavaGenerateManager;
import jp.go.aist.rtm.rtctemplate.manager.PythonGenerateManager;

import org.eclipse.jface.dialogs.IDialogConstants;

/**
 * ジェネレータクラス
 */
public class Generator {
	
	private HashMap<String, GenerateManager> generateManagerList = new HashMap<String, GenerateManager>();

	public Generator() {
		this.addGenerateManager("Common", new CommonGenerateManager());
		this.addGenerateManager( RtcParam.LANG_PYTHON, new PythonGenerateManager());
		this.addGenerateManager( RtcParam.LANG_CPP, new CXXGenerateManager());
		this.addGenerateManager( RtcParam.LANG_CPPWIN, new CXXWinGenerateManager());
		this.addGenerateManager( RtcParam.LANG_JAVA, new JavaGenerateManager());
	}
	
	/**
	 * ジェネレート・マネージャを追加する
	 * 
	 * @param genManager　生成対象のジェネレート・マネージャ
	 */
	public void addGenerateManager(String managerKey, GenerateManager genManager) {
		generateManagerList.put(managerKey, genManager);
	}
	/**
	 * ジェネレート・マネージャをクリアする
	 */
	public void clearGenerateManager() {
		generateManagerList = new HashMap<String, GenerateManager>();
	}
	/**
	 * ジェネレートする
	 * 
	 * @param generatorParam
	 *            パラメータ
	 * @return GeneratedResultのリスト
	 * @throws ParseException
	 *             IDLのパースに失敗した場合など
	 */
	public List<GeneratedResult> doGenerate(GeneratorParam generatorParam)
			throws Exception {

		for(RtcParam rtcParam : generatorParam.getRtcParams()) {
			rtcParam.getConsumerIdlPathes().clear();
			rtcParam.getProviderIdlPathes().clear();
		}

		validate(generatorParam);

//		List<ServiceClassParam> serviceClassParamList = filterReferencedServiceParam(
//				getRtcServiceClass(generatorParam), generatorParam);
		List<ServiceClassParam> rtcServiceClasses = new ArrayList<ServiceClassParam>();
		rtcServiceClasses.addAll(getRtcServiceClass(generatorParam, generatorParam.getConsumerIDLPathParams()));
		rtcServiceClasses.addAll(getRtcServiceClass(generatorParam, generatorParam.getProviderIDLPathParams()));
		for(RtcParam rtcParam : generatorParam.getRtcParams()) {
			if(rtcParam.getConsumerIdlPathes().size() > 0)
				rtcServiceClasses.addAll(getRtcServiceClass(generatorParam, rtcParam.getConsumerIdlPathes()));
			if(rtcParam.getProviderIdlPathes().size() > 0)
				rtcServiceClasses.addAll(getRtcServiceClass(generatorParam, rtcParam.getProviderIdlPathes()));
		}
		
		checkReferencedServiceParam(rtcServiceClasses, generatorParam);
		List<ServiceClassParam> serviceClassParamList = new ArrayList<ServiceClassParam>();
		List<String> serviceClassNameList = new ArrayList<String>();
		for( ServiceClassParam serviceClassParam : rtcServiceClasses ) {
			if( !serviceClassNameList.contains(serviceClassParam.getName()) ) {
				serviceClassNameList.add(serviceClassParam.getName());
				serviceClassParamList.add(serviceClassParam);
			}
		}

		generatorParam.getServiceClassParams().clear();

		for (ServiceClassParam param : serviceClassParamList) {
			param.setParent(generatorParam);
			generatorParam.getServiceClassParams().add(param);
		}

		List<GeneratedResult> result = new ArrayList<GeneratedResult>();
		for (GenerateManager manager : generateManagerList.values() ) {
			result.addAll(manager.doGenerate(generatorParam));
		}

		return result;
	}

	/**
	 * バリデートを行う
	 * 
	 * @param generatorParam
	 */
	private void validate(GeneratorParam generatorParam) {
		if (generatorParam.getOutputDirectory() == null) {
			throw new RuntimeException("OutputDirectoryが存在しません。");
		}
		
		List<String> providerIdlPathList = new ArrayList<String>();
		List<String> consumerIdlPathList = new ArrayList<String>();
		
		for(String idlPath :  generatorParam.getProviderIDLPathParams()) {
			if( !providerIdlPathList.contains(idlPath) )
				providerIdlPathList.add(idlPath);
		}
		for(String idlPath :  generatorParam.getConsumerIDLPathParams()) {
			if( !providerIdlPathList.contains(idlPath) && !consumerIdlPathList.contains(idlPath) )
				consumerIdlPathList.add(idlPath);
		}
		
		for (RtcParam rtcParam : generatorParam.getRtcParams()) {
			if (rtcParam.isLanguageExist(RtcParam.LANG_PYTHON)) {
				rtcParam.getConsumerIdlPathes().clear();
				rtcParam.getProviderIdlPathes().clear();
			}
			if (rtcParam.getName() == null) {
				throw new RuntimeException("Module Nameが存在しません。");
			}
			if (rtcParam.getMaxInstance() == -1) {
				throw new RuntimeException("Max Instanceが存在しません。");
			}

			List portNames = new ArrayList();
			for (DataPortParam port : rtcParam.getInports()) {
				if (portNames.contains(port.getName())) {
					throw new RuntimeException("Portに同じ名前が存在します。 :"
							+ rtcParam.getName());
				} else {
					portNames.add(port.getName());
				}
			}
			for (DataPortParam port : rtcParam.getOutports()) {
				if (portNames.contains(port.getName())) {
					throw new RuntimeException("Portに同じ名前が存在します。 :"
							+ rtcParam.getName());
				} else {
					portNames.add(port.getName());
				}
			}

			List referenceNames = new ArrayList();
			for (ServiceReferenceParam serviceReferenceParam : rtcParam
					.getProviderReferences()) {
				if (referenceNames.contains(serviceReferenceParam.getName())) {
					throw new RuntimeException(
							"ProviderもしくはConsumerに同じ名前が存在します。 :"
									+ rtcParam.getName());
				} else {
					referenceNames.add(serviceReferenceParam.getName());
				}
			}
			for (ServiceReferenceParam serviceReferenceParam : rtcParam
					.getConsumerReferences()) {
				if (referenceNames.contains(serviceReferenceParam.getName())) {
					throw new RuntimeException(
							"ProviderもしくはConsumerに同じ名前が存在します。 :"
									+ rtcParam.getName());
				} else {
					referenceNames.add(serviceReferenceParam.getName());
				}
			}

			List referenceInterfaceNames = new ArrayList();
			for (ServiceReferenceParam serviceReferenceParam : rtcParam
					.getProviderReferences()) {
				if (referenceInterfaceNames.contains(serviceReferenceParam
						.getInterfaceName())) {
					throw new RuntimeException(
							"ProviderもしくはConsumerに同じPortNameが存在します。 :"
									+ rtcParam.getName());
				} else {
					referenceInterfaceNames.add(serviceReferenceParam
							.getInterfaceName());
				}
			}
			for (ServiceReferenceParam serviceReferenceParam : rtcParam
					.getConsumerReferences()) {
				if (referenceInterfaceNames.contains(serviceReferenceParam
						.getInterfaceName())) {
					throw new RuntimeException(
							"ProviderもしくはConsumerに同じPortNameが存在します。 :"
									+ rtcParam.getName());
				} else {
					referenceInterfaceNames.add(serviceReferenceParam
							.getInterfaceName());
				}
			}
			
			List<String> IdlPaths = new ArrayList<String>();
			IdlPaths.addAll(rtcParam.getProviderIdlPathes());
			IdlPaths.addAll(providerIdlPathList);
			rtcParam.setProviderIdlPathes(IdlPaths);
			IdlPaths = new ArrayList<String>();
			IdlPaths.addAll(rtcParam.getConsumerIdlPathes());
			IdlPaths.addAll(consumerIdlPathList);
			rtcParam.setConsumerIdlPathes(IdlPaths);
		}

	}

	/**
	 * 参照されているServiceが存在するか確認する
	 * 
	 * @param rtcServiceClasses
	 * @param generatorParam
	 * @return
	 */
	private void checkReferencedServiceParam(List<ServiceClassParam> rtcServiceClasses,GeneratorParam generatorParam) {

		for (RtcParam param : generatorParam.getRtcParams()) {
			List<ServiceReferenceParam> serviceReferenceParamList = new ArrayList<ServiceReferenceParam>();
			serviceReferenceParamList.addAll(param.getProviderReferences());
			serviceReferenceParamList.addAll(param.getConsumerReferences());

			for (ServiceReferenceParam serviceReferenceParam : serviceReferenceParamList) {
				ServiceClassParam find = null;
				for (ServiceClassParam serviceClassParam : rtcServiceClasses) {
					if (serviceReferenceParam.getType().equals(serviceClassParam.getName())) {
						find = serviceClassParam;
						break;
					}
				}
	
				if (find == null)
					throw new RuntimeException("'" + serviceReferenceParam.getType() + "' is not found in IDL");
			}
		}
	}
//	/**
//	 * 参照されているServiceのみにフィルタする
//	 * 
//	 * @param rtcServiceClasses
//	 * @param generatorParam
//	 * @return
//	 */
//	private List<ServiceClassParam> filterReferencedServiceParam(
//			List<ServiceClassParam> rtcServiceClasses,
//			GeneratorParam generatorParam) {
//		Set<ServiceClassParam> result = new HashSet<ServiceClassParam>();
//
//		for (RtcParam param : generatorParam.getRtcParams()) {
//			List<ServiceReferenceParam> serviceReferenceParamList = new ArrayList<ServiceReferenceParam>();
//			serviceReferenceParamList.addAll(param.getProviderReferences());
//			serviceReferenceParamList.addAll(param.getConsumerReferences());
//
//			for (ServiceReferenceParam serviceReferenceParam : serviceReferenceParamList) {
//				ServiceClassParam find = null;
//				for (ServiceClassParam serviceClassParam : rtcServiceClasses) {
//					if (serviceReferenceParam.getType().equals(
//							serviceClassParam.getName())) {
//						find = serviceClassParam;
//						break;
//					}
//				}
//
//				if (find != null) {
//					result.add(find);
//				} else {
//					throw new RuntimeException("'"
//							+ serviceReferenceParam.getType()
//							+ "' is not found in IDL");
//				}
//			}
//		}
//
//		return Arrays.asList(result
//				.toArray(new ServiceClassParam[result.size()]));
//	}

	/**
	 * サービスクラス,型定義を取得する
	 * 
	 * @param generatorParam
	 * @param IDLPathes
	 * @return
	 * @throws ParseException
	 */
	private List<ServiceClassParam> getRtcServiceClass(
			GeneratorParam generatorParam, List<String> IDLPathes) throws ParseException {
		List<ServiceClassParam> result = new ArrayList<ServiceClassParam>();

		for(int intIdx=0; intIdx<IDLPathes.size(); intIdx++ ) {
			if(IDLPathes.get(intIdx) != null ) {
				String idlContent = Util.readFile(IDLPathes.get(intIdx));
				if (idlContent != null) {
					String idl = PreProcessor.parse(idlContent, generatorParam
							.getIncludeIDLDic());
					IDLParser parser = new IDLParser(new StringReader(idl));
	
					specification spec = parser.specification();
	
					List<ServiceClassParam> serviceClassParams = IDLParamConverter
							.convert(spec, IDLPathes.get(intIdx));
					Map<String,String> typedefParams = IDLParamConverter
							.convert_typedef(spec, IDLPathes.get(intIdx));
					for(ServiceClassParam scp : serviceClassParams) {
						scp.setTypeDef(typedefParams);
					}
					result.addAll(serviceClassParams);
				}
			}
		}
//		for(int intIdx=0; intIdx<generatorParam.getProviderIDLPathParams().size(); intIdx++ ) {
//			if (generatorParam.getProviderIDLContents(intIdx) != null) {
//				String idl = PreProcessor.parse(generatorParam
//						.getProviderIDLContents(intIdx), generatorParam
//						.getIncludeIDLDic());
//				IDLParser parser = new IDLParser(new StringReader(idl));
//
//				specification spec = parser.specification();
//
//				List<ServiceClassParam> serviceClassParams = IDLParamConverter
//						.convert(spec, generatorParam.getProviderIDLPath(intIdx));
//				result.addAll(serviceClassParams);
//			}
//		}
//		for(int intIdx=0; intIdx<generatorParam.getConsumerIDLPathParams().size(); intIdx++ ) {
//			if (generatorParam.getConsumerIDLContents(intIdx) != null) {
//				String idl = PreProcessor.parse(generatorParam
//						.getConsumerIDLContents(intIdx), generatorParam
//						.getIncludeIDLDic());
//				IDLParser parser = new IDLParser(new StringReader(idl));
//	
//				specification spec = parser.specification();
//	
//				result.addAll(IDLParamConverter.convert(spec, generatorParam
//						.getConsumerIDLPath(intIdx)));
//			}
//		}

//		if (generatorParam.getProviderIDLContents() != null) {
//			String idl = PreProcessor.parse(generatorParam
//					.getProviderIDLContents(), generatorParam
//					.getIncludeIDLDic());
//			IDLParser parser = new IDLParser(new StringReader(idl));
//
//			specification spec = parser.specification();
//
//			List<ServiceClassParam> serviceClassParams = IDLParamConverter
//					.convert(spec, generatorParam.getProviderIDLPath());
//			result.addAll(serviceClassParams);
//		}
//		if (generatorParam.getConsumerIDLContents() != null) {
//			String idl = PreProcessor.parse(generatorParam
//					.getConsumerIDLContents(), generatorParam
//					.getIncludeIDLDic());
//			IDLParser parser = new IDLParser(new StringReader(idl));
//
//			specification spec = parser.specification();
//
//			result.addAll(IDLParamConverter.convert(spec, generatorParam
//					.getConsumerIDLPath()));
//		}

		return result;
	}

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyyMMddHHmmss");

	private void writeFile(List<GeneratedResult> generatedResultList,
			String outputDirectory, MergeHandler handler) throws IOException {
		for (GeneratedResult generatedResult : generatedResultList) {
			if (generatedResult.getName().equals("") == false) {
				writeFile(generatedResult, outputDirectory, handler);
			}
		}
	}

	private void writeFile(GeneratedResult generatedResult, String strOutDir,
			MergeHandler handler) throws IOException {
		
		File outputDirectory = new File(strOutDir);
		if (outputDirectory.exists() == false) {
			throw new RuntimeException("出力対象のディレクトリが見つかりません");
		}
		
		File targetFile = new File(outputDirectory, generatedResult.getName());

		boolean isOutput = false;
		if (targetFile.exists()) {
			String originalFileContents = Util.readFile(targetFile.getAbsolutePath());
			if (originalFileContents.equals(generatedResult.getCode()) == false) {
				int selectedProcess = handler.getSelectedProcess(generatedResult, originalFileContents);
				if (selectedProcess != MergeHandler.PROCESS_ORIGINAL_ID
						&& selectedProcess != IDialogConstants.CANCEL_ID) {
					targetFile.renameTo(new File((strOutDir + "\\" + generatedResult.getName())+ DATE_FORMAT.format(new GregorianCalendar().getTime())));

					if (selectedProcess == MergeHandler.PROCESS_MERGE_ID) {
						generatedResult.setCode(MergeBlockParser.merge(
								originalFileContents, MergeBlockParser
										.parse(generatedResult.getCode())));
					}

					isOutput = true;
				}
			}
		} else {
			isOutput = true;
		}

		if (targetFile.exists() == false) {
			targetFile.createNewFile();
		}

		if (isOutput) {
			FileOutputStream fos = new FileOutputStream(new File(strOutDir,
					generatedResult.getName()));
			OutputStreamWriter osw = new OutputStreamWriter(fos, System
					.getProperty("file.encoding"));
			BufferedWriter bw = new BufferedWriter(osw);
			bw.write(generatedResult.getCode());
			bw.close();
			osw.close();
			fos.close();
		}
	}

	/**
	 * ジェネレートし、ファイル出力を行う
	 * 
	 * @param generatorParam
	 *            パラメータ
	 * @param handler
	 *            MergeHandler
	 * @throws ParseException
	 * @throws IOException
	 */
	public void doGenerateWrite(GeneratorParam generatorParam,
			MergeHandler handler) throws Exception {
		
		List<GeneratedResult> generatedResult = doGenerate(generatorParam);
		writeFile(generatedResult, generatorParam.getOutputDirectory(), handler);
	}

	/**
	 * マージハンドラ
	 */
	public interface MergeHandler {
		/**
		 * プロセス：オリジナルを残す
		 */
		public static final int PROCESS_ORIGINAL_ID = 10;

		/**
		 * プロセス：新しく生成したものを利用する
		 */
		public static final int PROCESS_GENERATE_ID = 20;

		/**
		 * プロセス：マージを行う
		 */
		public static final int PROCESS_MERGE_ID = 30;

		/**
		 * プロセスを選択する
		 * 
		 * @param generatedResult
		 *            生成結果
		 * @param originalFileContents
		 *            既存ファイルの内容
		 * @return
		 */
		public int getSelectedProcess(GeneratedResult generatedResult,
				String originalFileContents);
	}
}
