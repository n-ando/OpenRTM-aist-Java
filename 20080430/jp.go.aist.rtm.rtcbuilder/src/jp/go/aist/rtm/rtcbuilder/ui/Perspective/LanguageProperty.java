package jp.go.aist.rtm.rtcbuilder.ui.Perspective;

import java.util.Arrays;
import java.util.List;

import jp.go.aist.rtm.rtcbuilder.IRtcBuilderConstants;
import jp.go.aist.rtm.rtcbuilder.IRtcBuilderConstantsJava;
import jp.go.aist.rtm.rtcbuilder.IRtcBuilderConstantsPython;
import jp.go.aist.rtm.rtcbuilder.generator.param.RtcParam;

import org.eclipse.core.runtime.Platform;

public abstract class LanguageProperty {
	public abstract String getPerspectiveId();
	public abstract String getPerspectiveName();
	public abstract String getPluginId();
	public abstract List<String> getNatures();
	
	public static LanguageProperty getLanguageProperty(RtcParam rtcParam) {
		LanguageProperty langProp = null;
		if(rtcParam.isLanguageExist(IRtcBuilderConstantsJava.LANG_JAVA)) {
			langProp = new JavaProperty();
		} else if(rtcParam.isLanguageExist(IRtcBuilderConstants.LANG_CPP) ||
				rtcParam.isLanguageExist(IRtcBuilderConstants.LANG_CPPWIN)) {
			langProp = new CppProperty();
		} else if(rtcParam.isLanguageExist(IRtcBuilderConstantsPython.LANG_PYTHON) ) {
			langProp = new PythonProperty();
		}
		return langProp;
	}
	
	public static LanguageProperty checkPlugin(RtcParam rtcParam) {
		LanguageProperty langProp = getLanguageProperty(rtcParam);
		//PluginÇÃë∂ç›ämîF
		if( langProp != null ) {
			String[] plugins = Platform.getExtensionRegistry().getNamespaces();
			List<String> pluginMap = Arrays.asList(plugins);
			if( !pluginMap.contains(langProp.getPluginId())) {
				langProp = null;
			}
		}
		return langProp;
	}
}
