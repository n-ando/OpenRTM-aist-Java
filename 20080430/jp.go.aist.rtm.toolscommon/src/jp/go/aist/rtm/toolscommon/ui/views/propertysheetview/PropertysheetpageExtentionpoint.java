package jp.go.aist.rtm.toolscommon.ui.views.propertysheetview;

import java.util.ArrayList;
import java.util.List;

import jp.go.aist.rtm.toolscommon.ToolsCommonPlugin;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;

/**
 * システムダイアグラムのEditPartFactory
 */
public class PropertysheetpageExtentionpoint {
	private static final String EXTENTION_POINT_NAME = "propertysheetpageextentionpoint";

	private static List<Class> displayclassList = null;

	public static List<Class> getDisplayclassList() {
		if (displayclassList == null) {
			displayclassList = new ArrayList<Class>();
			IExtension[] extensions = Platform.getExtensionRegistry()
					.getExtensionPoint(ToolsCommonPlugin.class.getPackage().getName(),
							EXTENTION_POINT_NAME).getExtensions();
			for (IExtension extension : extensions) {
				for (IConfigurationElement configurationElement : extension
						.getConfigurationElements()) {
					String displayclass = configurationElement
							.getAttribute("displayclass");

					try {
						final Class targetclass = Platform.getBundle(
								extension.getNamespace()).loadClass(
								displayclass);

						displayclassList.add(targetclass);
					} catch (Exception e) {
						throw new RuntimeException(e); // system error
					}
				}
			}
		}

		return displayclassList;
	}

}
