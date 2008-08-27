package jp.go.aist.rtm.rtclink.manager;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

import jp.go.aist.rtm.rtclink.RtcLinkPlugin;

import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.PlatformUI;

/**
 * 設定を管理するマネージャ
 * <p>
 * 設定情報にアクセスするにはこのインスタンスを使用する
 */
public class PreferenceManager {
	private static PreferenceManager __instance = new PreferenceManager();

	/**
	 * RTC状態色のキー： Start
	 */
	public static final String COLOR_RTC_STATE_CREATED = PreferenceManager.class
			.getName()
			+ "COLOR_RTC_STATE_CREATED";

	/**
	 * RTC状態色のキー： InActive
	 */
	public static final String COLOR_RTC_STATE_INACTIVE = PreferenceManager.class
			.getName()
			+ "COLOR_RTC_STATE_INACTIVE";

	/**
	 * RTC状態色のキー： Active
	 */
	public static final String COLOR_RTC_STATE_ACTIVE = PreferenceManager.class
			.getName()
			+ "COLOR_RTC_STATE_ACTIVE";

	/**
	 * RTC状態色のキー： Error
	 */
	public static final String COLOR_RTC_STATE_ERROR = PreferenceManager.class
			.getName()
			+ "COLOR_RTC_STATE_ERROR";

	/**
	 * RTC状態色のキー： UnKnown
	 */
	public static final String COLOR_RTC_STATE_UNKNOWN = PreferenceManager.class
			.getName()
			+ "COLOR_RTC_STATE_UNKNOWN";

	/**
	 * RTCExecutionContext色のキー： Running
	 */
	public static final String COLOR_RTC_EXECUTION_CONTEXT_RUNNING = PreferenceManager.class
			.getName()
			+ "COLOR_RTC_EXECUTION_CONTEXT_RUNNING";

	/**
	 * RTCExecutionContext色のキー： Stopped
	 */
	public static final String COLOR_RTC_EXECUTION_CONTEXT_STOPPED = PreferenceManager.class
			.getName()
			+ "COLOR_RTC_EXECUTION_CONTEXT_STOPPED";

	/**
	 * DataPort色のキー： 未接続
	 */
	public static final String COLOR_DATAPORT_NO_CONNECT = PreferenceManager.class
			.getName()
			+ "COLOR_DATAPORT_NO_CONNECT";

	/**
	 * DataPort色のキー： 接続済
	 */
	public static final String COLOR_DATAPORT_CONNECTED = PreferenceManager.class
			.getName()
			+ "COLOR_DATAPORT_CONNECTED";

	/**
	 * ServicePort色のキー： 未接続
	 */
	public static final String COLOR_SERVICEPORT_NO_CONNECT = PreferenceManager.class
			.getName()
			+ "COLOR_SERVICEPORT_NO_CONNECT";

	/**
	 * ServicePort色のキー： 接続済
	 */
	public static final String COLOR_SERVICEPORT_CONNECTED = PreferenceManager.class
			.getName()
			+ "COLOR_SERVICEPORT_CONNECTED";

	/**
	 * 同期間隔のキー： システムエディタ
	 */
	public static final String SYNC_SYSTEMEDITOR_INTERVAL = PreferenceManager.class
			.getName()
			+ "SYNC_SYSTEMEDITOR_INTERVAL";

	/**
	 * 同期間隔のキー： ネームサーバ
	 */
	public static final String SYNC_NAMESERVER_INTERVAL = PreferenceManager.class
			.getName()
			+ "SYNC_NAMESERVICE_INTERVAL";

	/**
	 * デフォルト接続ポート
	 */
	public static final String DEFAULT_CONNECTION_PORT = PreferenceManager.class
			.getName()
			+ "DEFAULT_CONNECTION_PORT";

	/**
	 * タイムアウト判定時間
	 */
	public static final String DEFAULT_TIMEOUT_PERIOD = PreferenceManager.class
			.getName()
			+ "DEFAULT_TIMEOUT_PERIOD";

	/**
	 * デフォルトの色を管理するマップ
	 */
	public static final Map<String, RGB> defaultRGBMap = new HashMap<String, RGB>();
	static {
		defaultRGBMap.put(COLOR_RTC_STATE_CREATED, new RGB(255, 255, 255));
		defaultRGBMap.put(COLOR_RTC_STATE_INACTIVE, new RGB(0, 0, 255));
		defaultRGBMap.put(COLOR_RTC_STATE_ACTIVE, new RGB(0, 255, 0));
		defaultRGBMap.put(COLOR_RTC_STATE_ERROR, new RGB(255, 0, 0));
		defaultRGBMap.put(COLOR_RTC_STATE_UNKNOWN, new RGB(0, 0, 0));
		defaultRGBMap.put(COLOR_RTC_EXECUTION_CONTEXT_RUNNING, new RGB(128,
				128, 128));
		defaultRGBMap
				.put(COLOR_RTC_EXECUTION_CONTEXT_STOPPED, new RGB(0, 0, 0));
		defaultRGBMap.put(COLOR_DATAPORT_NO_CONNECT, new RGB(0, 0, 255));
		defaultRGBMap.put(COLOR_DATAPORT_CONNECTED, new RGB(96, 255, 96));
		defaultRGBMap.put(COLOR_SERVICEPORT_NO_CONNECT, new RGB(127, 127, 255));
		defaultRGBMap.put(COLOR_SERVICEPORT_CONNECTED, new RGB(0, 255, 255));
	}

	/**
	 * デフォルトの同期間隔を管理するマップ
	 */
	public static final Map<String, Integer> defaultInvervalMap = new HashMap<String, Integer>();
	static {
		defaultInvervalMap.put(SYNC_SYSTEMEDITOR_INTERVAL, 1000);
		defaultInvervalMap.put(SYNC_NAMESERVER_INTERVAL, 1000);
	}

	/**
	 * デフォルト接続ポート
	 */
	public static final String defaultConnectionPort = new String("2809");

	/**
	 * デフォルトタイムアウト判定時間
	 */
	public static final int defaultTimeoutPeriod = 3000;

	/**
	 * キャッシュした色（リソース）を管理するマップ
	 */
	private static transient final Map<String, Color> cachedColorMap = new HashMap<String, Color>();

	/**
	 * コンストラクタ
	 * 
	 * @return シングルトン
	 */
	public static PreferenceManager getInstance() {
		return __instance;
	}

	protected PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);

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
		RGB result = PreferenceConverter.getColor(RtcLinkPlugin.getDefault()
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

		PreferenceConverter.setValue(RtcLinkPlugin.getDefault()
				.getPreferenceStore(), key, newRGB);

		propertyChangeSupport.firePropertyChange(key, oldRgb, newRGB);
	}

	/**
	 * 間隔を取得する
	 * 
	 * @param key
	 *            キー
	 * @return 間隔
	 */
	public int getInterval(String key) {
		RtcLinkPlugin.getDefault().getPreferenceStore().setDefault(key, -1);

		int result = RtcLinkPlugin.getDefault().getPreferenceStore()
				.getInt(key);
		if (result == -1) { // defaultvalue
			result = defaultInvervalMap.get(key);
		}

		return result;
	}

	/**
	 * 間隔を設定する
	 * 
	 * @param key
	 *            キー
	 * @param interval
	 *            間隔
	 */
	public void setInterval(String key, int interval) {
		int oldInterval = getInterval(key);

		RtcLinkPlugin.getDefault().getPreferenceStore().setValue(key, interval);

		propertyChangeSupport.firePropertyChange(key, oldInterval, interval);
	}

	/**
	 * デフォルトポートを取得する
	 * 
	 * @param key
	 *            キー
	 * @return デフォルトポート
	 */
	public String getDefaultPort(String key) {
		RtcLinkPlugin.getDefault().getPreferenceStore().setDefault(key, "");

		String result = RtcLinkPlugin.getDefault().getPreferenceStore()
				.getString(key);
		if (result.equals("")) { // defaultvalue
			result = defaultConnectionPort;
		}

		return result;
	}

	/**
	 * デフォルト接続ポートを設定する
	 * 
	 * @param key
	 *            キー
	 * @param interval
	 *            間隔
	 */
	public void setDefaultPort(String key, String defaultPort) {
		String oldDefaultPort = getDefaultPort(key);

		RtcLinkPlugin.getDefault().getPreferenceStore().setValue(key, defaultPort);

		propertyChangeSupport.firePropertyChange(key, oldDefaultPort, defaultPort);
	}

	/**
	 * デフォルトタイムアウト判定時間を取得する
	 * 
	 * @param key
	 *            キー
	 * @return デフォルトポート
	 */
	public int getDefaultTimeout(String key) {
		RtcLinkPlugin.getDefault().getPreferenceStore().setDefault(key, -1);

		int result = RtcLinkPlugin.getDefault().getPreferenceStore()
				.getInt(key);
		if (result == -1) { // defaultvalue
			result = defaultTimeoutPeriod;
		}

		return result;
	}

	/**
	 * デフォルトタイムアウト判定時間を設定する
	 * 
	 * @param key
	 *            キー
	 * @param interval
	 *            間隔
	 */
	public void setDefaultTimeout(String key, int defaultTimeout) {
		int oldDefaultTimeout = getDefaultTimeout(key);

		RtcLinkPlugin.getDefault().getPreferenceStore().setValue(key, defaultTimeout);

		propertyChangeSupport.firePropertyChange(key, oldDefaultTimeout, defaultTimeout);
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

	/**
	 * デフォルト色のマップを取得する
	 * 
	 * @return デフォルト色のマップ
	 */
	public Map<String, RGB> getDefaultRGBMap() {
		return defaultRGBMap;
	}

	/**
	 * デフォルト間隔のマップを取得する
	 * 
	 * @return デフォルト間隔のマップ
	 */
	public Map<String, Integer> getDefaultIntervalMap() {
		return defaultInvervalMap;
	}

}
