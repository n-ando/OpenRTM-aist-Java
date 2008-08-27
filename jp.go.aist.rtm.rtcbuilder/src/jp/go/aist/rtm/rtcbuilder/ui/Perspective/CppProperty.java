package jp.go.aist.rtm.rtcbuilder.ui.Perspective;

import java.util.ArrayList;
import java.util.List;

public class CppProperty extends LanguageProperty {
	private String PerspectiveId = "org.eclipse.cdt.ui.CPerspective";
	private String PerspectiveName = "C/C++";
	private String PluginId = "org.eclipse.cdt";

	public String getPerspectiveId() {
		return PerspectiveId;
	}

	public String getPerspectiveName() {
		return PerspectiveName;
	}

	public String getPluginId() {
		return PluginId;
	}

	@Override
	public List<String> getNatures() {
		List<String> natures = new ArrayList<String>();
		natures.add("org.eclipse.cdt.core.cnature");
		natures.add("org.eclipse.cdt.make.core.makeNature");
		natures.add("org.eclipse.cdt.make.core.ScannerConfigNature");
		natures.add("org.eclipse.cdt.core.ccnature");
		return natures;
	}
}
