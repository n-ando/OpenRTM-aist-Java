package jp.go.aist.rtm.rtcbuilder.template;

import java.io.File;

import jp.go.aist.rtm.rtcbuilder.IRtcBuilderConstants;
import jp.go.aist.rtm.rtcbuilder.ui.StringUtil;

/**
 * テンプレートを出力する際に使用されるヘルパー
 */
public class TemplateHelper {

	/**
	 * ベース名を取得する
	 * 
	 * @param fullName
	 * @return
	 */
	public static String getBasename(String fullName) {
		String[] split = fullName.split("::");
		return split[split.length - 1];
	}

	/**
	 * ファイル名を取得する
	 * 
	 * @param fullPath
	 * @return
	 */
	public static String getFileName(String fullPath) {
		File file = new File(fullPath);
		return file.getName();
	}
	/**
	 * 拡張子無しファイル名を取得する
	 * 
	 * @param fullPath
	 * @return
	 */
	public static String getFilenameNoExt(String fullPath) {
		String fileName = getFileName(fullPath);

		if(fileName == null) return "";
		
		int index = fileName.lastIndexOf('.');
		if(index>0 && index<fileName.length()-1) {
			return fileName.substring(0,index);
		}
		return "";
	}
	public static String getServiceImplSuffix() {
		return IRtcBuilderConstants.DEFAULT_SVC_IMPL_SUFFIX;
	}

	public static String getServiceSkelSuffix() {
		return IRtcBuilderConstants.DEFAULT_SVC_SKEL_SUFFIX;
	}

	public static String getServiceStubSuffix() {
		return IRtcBuilderConstants.DEFAULT_SVC_STUB_SUFFIX;
	}

	public String getOpenRtmVersion() {
		return IRtcBuilderConstants.OPEN_RTM_VERSION;
	}
	
	public String convertDoc(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_DEFAULT_PREFIX, IRtcBuilderConstants.DOC_DEFAULT_OFFSET);
	}
	public String convertDescDoc(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_DESC_PREFIX, IRtcBuilderConstants.DOC_DESC_OFFSET);
	}
	public String convertPreDoc(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_DESC_PREFIX, IRtcBuilderConstants.DOC_PRE_OFFSET);
	}
	public String convertPostDoc(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_DESC_PREFIX, IRtcBuilderConstants.DOC_POST_OFFSET);
	}
	public String convertUnitDoc(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_UNIT_PREFIX, IRtcBuilderConstants.DOC_UNIT_OFFSET);
	}
	public String convertRangeDoc(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_RANGE_PREFIX, IRtcBuilderConstants.DOC_RANGE_OFFSET);
	}
	public String convertConstraintDoc(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_CONSTRAINT_PREFIX, IRtcBuilderConstants.DOC_CONSTRAINT_OFFSET);
	}
	public String convertTypeDoc(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_UNIT_PREFIX, IRtcBuilderConstants.DOC_UNIT_OFFSET);
	}
	public String convertNumberDoc(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_NUMBER_PREFIX, IRtcBuilderConstants.DOC_NUMBER_OFFSET);
	}
	public String convertSemanticsDoc(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_SEMANTICS_PREFIX, IRtcBuilderConstants.DOC_SEMANTICS_OFFSET);
	}
	public String convertFrequencyDoc(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_SEMANTICS_PREFIX, IRtcBuilderConstants.DOC_SEMANTICS_OFFSET);
	}
	public String convertCycleDoc(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_CYCLE_PREFIX, IRtcBuilderConstants.DOC_CYCLE_OFFSET);
	}
	public String convertInterfaceDoc(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_INTERFACE_PREFIX, IRtcBuilderConstants.DOC_INTERFACE_OFFSET);
	}
	public String convertInterfaceDetailDoc(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_INTERFACE_DETAIL_PREFIX, IRtcBuilderConstants.DOC_INTERFACE_DETAIL_OFFSET);
	}
	//
	public String convertReadMePortDoc(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_README_PORT_PREFIX, IRtcBuilderConstants.DOC_README_PORT_OFFSET);
	}
	public String convertReadMePortDetailDoc(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_README_PORT_DETAIL_PREFIX, IRtcBuilderConstants.DOC_README_PORT_DETAIL_OFFSET);
	}
	public String convertReadMeInterfaceDoc(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_README_INTERFACE_PREFIX, IRtcBuilderConstants.DOC_README_INTERFACE_OFFSET);
	}
	public String convertReadMeConfigDoc(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_README_PORT_DETAIL_PREFIX, IRtcBuilderConstants.DOC_README_PORT_DETAIL_OFFSET);
	}
	public String convertReadMeModuleDoc(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_README_MODULE_PREFIX, IRtcBuilderConstants.DOC_README_MODULE_OFFSET);
	}
	public String convertReadMeActivityDoc(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_README_ACTIVITY_PREFIX, IRtcBuilderConstants.DOC_README_ACTIVITY_OFFSET);
	}
	public String convertReadMeAuthorDoc(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_README_PREFIX, IRtcBuilderConstants.DOC_AUTHOR_OFFSET);
	}
	public String convertReadMeCopyRightDoc(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_README_COPYRIGHT_PREFIX, IRtcBuilderConstants.DOC_DEFAULT_OFFSET);
	}
	public String convertAuthorDoc(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_DEFAULT_PREFIX, IRtcBuilderConstants.DOC_AUTHOR_OFFSET);
	}
	//
	public String convertDescDocJava(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_DESC_PREFIX_JAVA, IRtcBuilderConstants.DOC_DESC_OFFSET_JAVA);
	}
	public String convertPreDocJava(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_DESC_PREFIX_JAVA, IRtcBuilderConstants.DOC_PRE_OFFSET_JAVA);
	}
	public String convertPostDocJava(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_DESC_PREFIX_JAVA, IRtcBuilderConstants.DOC_POST_OFFSET_JAVA);
	}
	public String convertUnitDocJava(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_UNIT_PREFIX_JAVA, IRtcBuilderConstants.DOC_UNIT_OFFSET_JAVA);
	}
	public String convertRangeDocJava(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_RANGE_PREFIX_JAVA, IRtcBuilderConstants.DOC_RANGE_OFFSET_JAVA);
	}
	public String convertConstraintDocJava(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_CONSTRAINT_PREFIX_JAVA, IRtcBuilderConstants.DOC_CONSTRAINT_OFFSET_JAVA);
	}
	public String convertTypeDocJava(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_UNIT_PREFIX_JAVA, IRtcBuilderConstants.DOC_UNIT_OFFSET_JAVA);
	}
	public String convertNumberDocJava(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_NUMBER_PREFIX_JAVA, IRtcBuilderConstants.DOC_NUMBER_OFFSET_JAVA);
	}
	public String convertSemanticsDocJava(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_SEMANTICS_PREFIX_JAVA, IRtcBuilderConstants.DOC_SEMANTICS_OFFSET_JAVA);
	}
	public String convertFrequencyDocJava(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_SEMANTICS_PREFIX_JAVA, IRtcBuilderConstants.DOC_SEMANTICS_OFFSET_JAVA);
	}
	public String convertCycleDocJava(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_CYCLE_PREFIX_JAVA, IRtcBuilderConstants.DOC_CYCLE_OFFSET_JAVA);
	}
	public String convertInterfaceDocJava(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_INTERFACE_PREFIX_JAVA, IRtcBuilderConstants.DOC_INTERFACE_OFFSET_JAVA);
	}
	public String convertInterfaceDetailDocJava(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_INTERFACE_DETAIL_PREFIX_JAVA, IRtcBuilderConstants.DOC_INTERFACE_DETAIL_OFFSET_JAVA);
	}
	//
	public String convertDocPy(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_DEFAULT_PREFIX_PY, IRtcBuilderConstants.DOC_DEFAULT_OFFSET_PY);
	}
	public String convertAuthorDocPy(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_DEFAULT_PREFIX_PY, IRtcBuilderConstants.DOC_AUTHOR_OFFSET_PY);
	}
	public String convertModuleDocPy(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_MODULE_PREFIX_PY, IRtcBuilderConstants.DOC_DEFAULT_OFFSET_PY);
	}
	public String convertDescDocPy(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_DESC_PREFIX_PY, IRtcBuilderConstants.DOC_DESC_OFFSET_PY);
	}
	public String convertTypeDocPy(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_UNIT_PREFIX_PY, IRtcBuilderConstants.DOC_UNIT_OFFSET);
	}
	public String convertNumberDocPy(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_NUMBER_PREFIX_PY, IRtcBuilderConstants.DOC_NUMBER_OFFSET_PY);
	}
	public String convertSemanticsDocPy(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_SEMANTICS_PREFIX_PY, IRtcBuilderConstants.DOC_SEMANTICS_OFFSET);
	}
	public String convertFrequencyDocPy(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_FREQUENCY_PREFIX_PY, IRtcBuilderConstants.DOC_FREQUENCY_OFFSET_PY);
	}
	public String convertCycleDocPy(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_CYCLE_PREFIX_PY, IRtcBuilderConstants.DOC_CYCLE_OFFSET_PY);
	}
	public String convertInterfacePy(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_NUMBER_PREFIX_PY, IRtcBuilderConstants.DOC_NUMBER_OFFSET_PY);
	}
	public String convertDetailPy(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_DETAIL_PREFIX_PY, IRtcBuilderConstants.DOC_DETAIL_OFFSET_PY);
	}
	public String convertUnitDocPy(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_UNIT_PREFIX_PY, IRtcBuilderConstants.DOC_UNIT_OFFSET);
	}
	public String convertRangeDocPy(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_RANGE_PREFIX_PY, IRtcBuilderConstants.DOC_RANGE_OFFSET_PY);
	}
	public String convertConstraintDocPy(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_CONSTRAINT_PREFIX_PY, IRtcBuilderConstants.DOC_CONSTRAINT_OFFSET_PY);
	}
	public String convertPreDocPy(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_PRE_PREFIX_PY, IRtcBuilderConstants.DOC_PRE_OFFSET_PY);
	}
	public String convertPostDocPy(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_POST_PREFIX_PY, IRtcBuilderConstants.DOC_POST_OFFSET_PY);
	}
	public String convertActivityDocPy(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_ACTIVITY_PREFIX_PY, IRtcBuilderConstants.DOC_ACTIVITY_OFFSET_PY);
	}
	public String convertPreShDocPy(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_PRESH_PREFIX_PY, IRtcBuilderConstants.DOC_PRE_OFFSET_PY);
	}
	public String convertPostShDocPy(String source) {
		return StringUtil.splitString(source, IRtcBuilderConstants.DOC_DEFAULT_WIDTH, 
				IRtcBuilderConstants.DOC_POSTSH_PREFIX_PY, IRtcBuilderConstants.DOC_POST_OFFSET_PY);
	}
}
