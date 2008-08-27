package jp.go.aist.rtm.rtcbuilder.ui.preference;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

import jp.go.aist.rtm.rtcbuilder.RtcBuilderPlugin;

import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.PlatformUI;

public class BuilderViewPreferenceManager {
	private static BuilderViewPreferenceManager __instance = new BuilderViewPreferenceManager();

	/**
	 * コンストラクタ
	 * 
	 * @return シングルトン
	 */
	public static BuilderViewPreferenceManager getInstance() {
		return __instance;
	}

	/**
	 * Component Colorのキー
	 */
	public static final String COLOR_COMPONENT = BuilderViewPreferenceManager.class.getName()
			+ "COMPONENT_COLOR";
	/**
	 * DataInPort Colorのキー
	 */
	public static final String COLOR_DATAINPORT = BuilderViewPreferenceManager.class.getName()
			+ "DATA_INPORT_COLOR";

	/**
	 * DataOutPort Colorのキー
	 */
	public static final String COLOR_DATAOUTPORT = BuilderViewPreferenceManager.class.getName()
			+ "DATA_OUTPORT_COLOR";
	/**
	 * ServicePort Colorのキー
	 */
	public static final String COLOR_SERVICEPORT = BuilderViewPreferenceManager.class.getName()
			+ "SERVICE_PORT_COLOR";

	/**
	 * Service I/F Colorのキー
	 */
	public static final String COLOR_SERVICEIF = BuilderViewPreferenceManager.class.getName()
			+ "SERVICE_IF_COLOR";

	protected PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);

	/**
	 * デフォルトの色を管理するマップ
	 */
	public static final Map<String, RGB> defaultRGBMap = new HashMap<String, RGB>();
	static {
		defaultRGBMap.put(COLOR_COMPONENT, new RGB(136, 190, 240));
		defaultRGBMap.put(COLOR_DATAINPORT, new RGB(55, 97, 217));
		defaultRGBMap.put(COLOR_DATAOUTPORT, new RGB(55, 97, 217));
		defaultRGBMap.put(COLOR_SERVICEPORT, new RGB(192, 192, 192));
		defaultRGBMap.put(COLOR_SERVICEIF, new RGB(101, 136, 22));
	}

	/**
	 * キャッシュした色（リソース）を管理するマップ
	 */
	private static transient final Map<String, Color> cachedColorMap = new HashMap<String, Color>();

	/**
	 * キーから色を返す
	 * <p>
	 * 色はりソースであるため、キャッシュして使用している。
	 * 
	 * @param key
	 * @return 色
	 */
	public synchronized Color getColor(String key) {
		RGB rgb = getRGB(key);

		Color result = cachedColorMap.get(key);
		if (result == null || rgb.equals(result.getRGB()) == false) {
			if (result != null) {
				result.dispose();
			}
			result = new Color(PlatformUI.getWorkbench().getDisplay(), rgb);
			cachedColorMap.put(key, result);
		}

		return result;
	}

	/**
	 * キーからRGBを取得する
	 * 
	 * @param key
	 * @return RGB
	 */
	public RGB getRGB(String key) {
		RGB result = PreferenceConverter.getColor(RtcBuilderPlugin.getDefault()
				.getPreferenceStore(), key);
		if (result == PreferenceConverter.COLOR_DEFAULT_DEFAULT) { // caution
			// "=="
			result = defaultRGBMap.get(key);
		}

		return result;
	}

	/**
	 * キーに、RGBを関連付ける
	 * 
	 * @param key
	 *            キー
	 * @param newRGB
	 *            関連付けるRGB
	 */
	public void setRGB(String key, RGB newRGB) {
		RGB oldRgb = getRGB(key);

		PreferenceConverter.setValue(RtcBuilderPlugin.getDefault()
				.getPreferenceStore(), key, newRGB);

		propertyChangeSupport.firePropertyChange(key, oldRgb, newRGB);
	}

	/**
	 * デフォルト色のマップを取得する
	 * 
	 * @return デフォルト色のマップ
	 */
	public Map<String, RGB> getDefaultRGBMap() {
		return defaultRGBMap;
	}

	/**
	 * @see PropertyChangeSupport#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	/**
	 * @see PropertyChangeSupport#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}
}
