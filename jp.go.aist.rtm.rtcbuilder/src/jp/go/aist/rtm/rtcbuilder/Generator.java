package jp.go.aist.rtm.rtcbuilder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.go.aist.rtm.rtcbuilder.corba.idl.parser.IDLParser;
import jp.go.aist.rtm.rtcbuilder.corba.idl.parser.ParseException;
import jp.go.aist.rtm.rtcbuilder.corba.idl.parser.syntaxtree.specification;
import jp.go.aist.rtm.rtcbuilder.generator.GeneratedResult;
import jp.go.aist.rtm.rtcbuilder.generator.IDLParamConverter;
import jp.go.aist.rtm.rtcbuilder.generator.PreProcessor;
import jp.go.aist.rtm.rtcbuilder.generator.param.DataPortParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.GeneratorParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.RtcParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.ServicePortInterfaceParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.ServicePortParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.idl.ServiceClassParam;
import jp.go.aist.rtm.rtcbuilder.generator.parser.MergeBlockParser;
import jp.go.aist.rtm.rtcbuilder.manager.CXXGenerateManager;
import jp.go.aist.rtm.rtcbuilder.manager.CXXWinGenerateManager;
import jp.go.aist.rtm.rtcbuilder.manager.CommonGenerateManager;
import jp.go.aist.rtm.rtcbuilder.manager.GenerateManager;
import jp.go.aist.rtm.rtcbuilder.manager.JavaGenerateManager;
import jp.go.aist.rtm.rtcbuilder.manager.PythonGenerateManager;
import jp.go.aist.rtm.rtcbuilder.ui.Perspective.LanguageProperty;
import jp.go.aist.rtm.rtcbuilder.util.FileUtil;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.IDialogConstants;

/**
 * ジェネレータクラス
 */
public class Generator {
	
	private HashMap<String, GenerateManager> generateManagerList = new HashMap<String, GenerateManager>();

	public Generator() {
		this.addGenerateManager(new CommonGenerateManager());
		this.addGenerateManager(new PythonGenerateManager());
		this.addGenerateManager(new CXXGenerateManager());
		this.addGenerateManager(new CXXWinGenerateManager());
		this.addGenerateManager(new JavaGenerateManager());
	}
	
	/**
	 * ジェネレート・マネージャを追加する
	 * 
	 * @param genManager　生成対象のジェネレート・マネージャ
	 */
	public void addGenerateManager(GenerateManager genManager) {
		generateManagerList.put(genManager.getManagerKey(), genManager);
	}
	/**
	 * ジェネレート・マネージャをクリアする
	 */
	public void clearGenerateManager() {
		generateManagerList = new HashMap<String, GenerateManager>();
	}
	public List<GeneratedResult> generateTemplateCode(GeneratorParam generatorParam)	throws Exception {
		return generateTemplateCode(generatorParam, true);
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
	public List<GeneratedResult> generateTemplateCode(GeneratorParam generatorParam, boolean validateFlag)	 
					throws Exception {

		if( validateFlag ) {
			for( RtcParam rtcParam : generatorParam.getRtcParams() ) {
				validate(rtcParam);
			}
		}

		List<ServiceClassParam> rtcServiceClasses = new ArrayList<ServiceClassParam>();
		//IDL重複チェック用
		List<String> IDLPathes = new ArrayList<String>();
		//IDL読み込み用
		List<ServiceClassParam> IDLPathParams = new ArrayList<ServiceClassParam>();
		List<GeneratedResult> result = new ArrayList<GeneratedResult>();
		for( RtcParam rtcParam : generatorParam.getRtcParams() ) {
			rtcParam.checkAndSetParameter();
			for( ServicePortParam serviceport : rtcParam.getServicePorts() ) {
				for( ServicePortInterfaceParam serviceInterfaces : serviceport.getServicePortInterfaces() ) {
					if( !IDLPathes.contains(serviceInterfaces.getIdlFullPath()) )
						IDLPathes.add(serviceInterfaces.getIdlFullPath());
						IDLPathParams.add(new ServiceClassParam(serviceInterfaces.getIdlFullPath(),
																 serviceInterfaces.getIdlSearchPath()));
				}
			}
			rtcServiceClasses.addAll(getRtcServiceClass(rtcParam, IDLPathParams));
			checkReferencedServiceParam(rtcServiceClasses, rtcParam);
			List<ServiceClassParam> serviceClassParamList = new ArrayList<ServiceClassParam>();
			List<String> serviceClassNameList = new ArrayList<String>();
			for( ServiceClassParam serviceClassParam : rtcServiceClasses ) {
				if( !serviceClassNameList.contains(serviceClassParam.getName()) ) {
					serviceClassNameList.add(serviceClassParam.getName());
					serviceClassParamList.add(serviceClassParam);
				}
			}
	
			rtcParam.getServiceClassParams().clear();
	
			for( ServiceClassParam param : serviceClassParamList ) {
				param.setParent(rtcParam);
				rtcParam.getServiceClassParams().add(param);
			}
			List<GeneratedResult> resultEach = new ArrayList<GeneratedResult>();
			for( GenerateManager manager : generateManagerList.values() ) {
				resultEach.addAll(manager.generateTemplateCode(rtcParam));
			}
			result.addAll(resultEach);
		}	

		return result;
	}

	/**
	 * バリデートを行う
	 * 
	 * @param generatorParam
	 */
	private void validate(RtcParam rtcParam) {

		if( rtcParam.getOutputProject() == null ) {
			throw new RuntimeException("OutputProjectが指定されていません。");
		}
		if( rtcParam.getName() == null ) {
			throw new RuntimeException("Component Nameが指定されていません。");
		}

		List<String> portNames = new ArrayList<String>();
		for( DataPortParam inport : rtcParam.getInports() ) {
			if (portNames.contains(inport.getName()))
				throw new RuntimeException("Portに同じ名前が存在します。 :" + rtcParam.getName());
			portNames.add(inport.getName());
		}
		for (DataPortParam outport : rtcParam.getOutports()) {
			if( portNames.contains(outport.getName()) )
				throw new RuntimeException("Portに同じ名前が存在します。 :" + rtcParam.getName());
			portNames.add(outport.getName());
		}

		List<String> servicePortNames = new ArrayList<String>();
		for( ServicePortParam servicePort : rtcParam.getServicePorts() ) {
			if( servicePortNames.contains(servicePort.getName()) )
				throw new RuntimeException(
						"ProviderもしくはConsumerに同じ名前が存在します。 :" + rtcParam.getName());
			servicePortNames.add(servicePort.getName());
		}

		List<String> serviceInterfaceNames = new ArrayList<String>();
		for( ServicePortParam servicePort : rtcParam.getServicePorts() ) {
			for( ServicePortInterfaceParam serviceInterface : servicePort.getServicePortInterfaces() ) {
				if (serviceInterfaceNames.contains(serviceInterface.getName()))
					throw new RuntimeException(
							"ProviderもしくはConsumerに同じPortNameが存在します。 :" + rtcParam.getName());
				serviceInterfaceNames.add(serviceInterface.getName());
			}
		}
	}

	/**
	 * 参照されているServiceが存在するか確認する
	 * 
	 * @param rtcServiceClasses
	 * @param generatorParam
	 * @return
	 */
	private void checkReferencedServiceParam(List<ServiceClassParam> rtcServiceClasses, RtcParam param) {

		List<String> serviceTypes = new ArrayList<String>();
		for( ServicePortParam serviceport : param.getServicePorts() ) {
			for( ServicePortInterfaceParam serviceInterfaces : serviceport.getServicePortInterfaces() ) {
				if( !serviceTypes.contains(serviceInterfaces.getInterfaceType()) )
					serviceTypes.add(serviceInterfaces.getInterfaceType());
			}
		}

		for( String serviceType : serviceTypes ) {
			ServiceClassParam find = null;
			for( ServiceClassParam serviceClassParam : rtcServiceClasses ) {
				if( serviceType.equals(serviceClassParam.getName()) ) {
					find = serviceClassParam;
					break;
				}
			}
			if( find == null )
				throw new RuntimeException("'" + serviceType + "' is not found in IDL");
		}
	}

	/**
	 * サービスクラス,型定義を取得する
	 * 
	 * @param generatorParam
	 * @param IDLPathes
	 * @return
	 * @throws ParseException
	 */
	private List<ServiceClassParam> getRtcServiceClass(
			RtcParam rtcParam, List<ServiceClassParam> IDLPathes) throws ParseException {
		List<ServiceClassParam> result = new ArrayList<ServiceClassParam>();

		for(int intIdx=0; intIdx<IDLPathes.size(); intIdx++ ) {
			if(IDLPathes.get(intIdx) != null ) {
				String idlContent = FileUtil.readFile(IDLPathes.get(intIdx).getName());
				if (idlContent != null) {
//					String idl = PreProcessor.parse(idlContent, rtcParam.getIncludeIDLDic());
					String idl = PreProcessor.parse(idlContent, getIncludeIDLDic(IDLPathes.get(intIdx).getIdlPath()));
					IDLParser parser = new IDLParser(new StringReader(idl));
	
					specification spec = parser.specification();
	
					List<ServiceClassParam> serviceClassParams = IDLParamConverter
							.convert(spec, IDLPathes.get(intIdx).getName());
					Map<String,String> typedefParams = IDLParamConverter
							.convert_typedef(spec, IDLPathes.get(intIdx).getName());
					for(ServiceClassParam scp : serviceClassParams) {
						scp.setTypeDef(typedefParams);
					}
					result.addAll(serviceClassParams);
				}
			}
		}

		return result;
	}

	private File getIncludeIDLDic(String targetDir) {
		File result = null;
		if( targetDir!=null && targetDir.length()>0 ) {
			File file = new File(targetDir);
			if (file.exists()) {
				result = file;
			} else {
				throw new RuntimeException("Include IDL のディレクトリが見つかりません。");
			}
		}
		return result;
	}

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyyMMddHHmmss");

	private void writeFile(List<GeneratedResult> generatedResultList,
			RtcParam rtcParam, MergeHandler handler) throws IOException {

		IWorkspaceRoot workspaceHandle = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = workspaceHandle.getProject(rtcParam.getOutputProject());
		if(!project.exists()) {
			try {
				project.create(null);
				project.open(null);
				LanguageProperty langProp = LanguageProperty.checkPlugin(rtcParam);
				if(langProp != null) {
					IProjectDescription description = project.getDescription();
					String[] ids = description.getNatureIds();
					String[] newIds = new String[ids.length + langProp.getNatures().size()];
					System.arraycopy(ids, 0, newIds, 0, ids.length);
					for( int intIdx=0; intIdx<langProp.getNatures().size(); intIdx++ ) {
						newIds[ids.length+intIdx] = langProp.getNatures().get(intIdx);
					}
					description.setNatureIds(newIds);
					project.setDescription(description, null);
				}
			} catch (CoreException e) {
				throw new RuntimeException("プロジェクトの生成に失敗しました");
			}
		}
		
		for (GeneratedResult generatedResult : generatedResultList) {
			if (generatedResult.getName().equals("") == false) {
				writeFile(generatedResult, project, handler);
			}
		}
	}

	private void writeFile(GeneratedResult generatedResult, IProject outputProject,
			MergeHandler handler) throws IOException {
		
		File targetFile = new File(outputProject.getLocation().toOSString(), generatedResult.getName());

		boolean isOutput = false;
		if (targetFile.exists()) {
			String originalFileContents = FileUtil.readFile(targetFile.getAbsolutePath());
			if (originalFileContents.equals(generatedResult.getCode()) == false) {
				int selectedProcess = handler.getSelectedProcess(generatedResult, originalFileContents);
				if (selectedProcess != MergeHandler.PROCESS_ORIGINAL_ID
						&& selectedProcess != IDialogConstants.CANCEL_ID) {
					IResource originalFile = outputProject.findMember(generatedResult.getName());
					IFile renameFile = outputProject.getFile(generatedResult.getName() + DATE_FORMAT.format(new GregorianCalendar().getTime()) );
					try {
						originalFile.move(renameFile.getFullPath(), true, null);

						if (selectedProcess == MergeHandler.PROCESS_MERGE_ID) {
							generatedResult.setCode(MergeBlockParser.merge(
									originalFileContents, MergeBlockParser
											.parse(generatedResult.getCode())));
						}
	
						isOutput = true;
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			isOutput = true;
		}

		if (isOutput) {
			IFile outputFile = outputProject.getFile(generatedResult.getName());
			try {
				outputFile.create(new ByteArrayInputStream(generatedResult.getCode().getBytes("UTF-8")), false, null);
			} catch (CoreException e) {
				e.printStackTrace();
			}
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
		
		for( RtcParam rtcParam : generatorParam.getRtcParams() ) {
			List<GeneratedResult> generatedResult = generateTemplateCode(generatorParam);
			writeFile(generatedResult, rtcParam, handler);
		}
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
