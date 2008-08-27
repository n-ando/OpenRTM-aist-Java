package jp.go.aist.rtm.toolscommon.factory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.go.aist.rtm.toolscommon.ToolsCommonPlugin;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.MappingRule;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;

/**
 * Plugin.xmlに記述されたマッピングルールを解析するクラス
 */
public class MappingRuleFactory {
	public static final String EXTENTION_POINT_NAME = "wrapperfactory";

	private static MappingRule[] mappingRules = null;

	public static MappingRule[] getMappingRule() {
		if (mappingRules == null) {
			Map<Integer, MappingRule> tempMappingRules = new HashMap<Integer, MappingRule>();
			IExtension[] extensions = Platform.getExtensionRegistry()
					.getExtensionPoint(ToolsCommonPlugin.class.getPackage().getName(),
							EXTENTION_POINT_NAME).getExtensions();
			for (IExtension extension : extensions) {
				for (IConfigurationElement configurationElement : extension
						.getConfigurationElements()) {
					String seq = configurationElement.getAttribute("seq");
					String mappingRuleValue = configurationElement
							.getAttribute("mappingrule");

					try {
						int lastIndexOf = mappingRuleValue.lastIndexOf(".");

						Class clazz = Platform.getBundle(
								extension.getNamespace()).loadClass(
								mappingRuleValue.substring(0, lastIndexOf));
						Field field = clazz.getDeclaredField(mappingRuleValue
								.substring(lastIndexOf + ".".length()));

						tempMappingRules.put(Integer.parseInt(seq),
								(MappingRule) field.get(clazz.newInstance()));

					} catch (Exception e1) {
						throw new RuntimeException(e1); // system error
					}

				}
			}

			List<Integer> keys = new ArrayList<Integer>(tempMappingRules
					.keySet());
			Collections.sort(keys);

			List<MappingRule> mappingRuleList = new ArrayList<MappingRule>();
			for (Integer key : keys) {
				mappingRuleList.add(tempMappingRules.get(key));
			}

			mappingRules = mappingRuleList
					.toArray(new MappingRule[mappingRuleList.size()]);
		}

		return mappingRules;
	}
}
