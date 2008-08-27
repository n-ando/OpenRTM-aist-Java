package jp.go.aist.rtm.systemeditor.manager;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jp.go.aist.rtm.systemeditor.RTSystemEditorPlugin;

import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.PlatformUI;

/**
 * 設定を管理するマネージャ
 * <p>
 * 設定情報にアクセスするにはこのインスタンスを使用する
 */
public class SystemEditorPreferenceManager {
	private static final String Separator = ":";
	private static SystemEditorPreferenceManager __instance = new SystemEditorPreferenceManager();

	/**
	 * RTC状態色のキー： Start
	 */
	public static final String COLOR_RTC_STATE_CREATED = SystemEditorPreferenceManager.class
			.getName()
			+ "COLOR_RTC_STATE_CREATED";

	/**
	 * RTC状態色のキー： InActive
	 */
	public static final String COLOR_RTC_STATE_INACTIVE = SystemEditorPreferenceManager.class
			.getName()
			+ "COLOR_RTC_STATE_INACTIVE";

	/**
	 * RTC状態色のキー： Active
	 */
	public static final String COLOR_RTC_STATE_ACTIVE = SystemEditorPreferenceManager.class
			.getName()
			+ "COLOR_RTC_STATE_ACTIVE";

	/**
	 * RTC状態色のキー： Error
	 */
	public static final String COLOR_RTC_STATE_ERROR = SystemEditorPreferenceManager.class
			.getName()
			+ "COLOR_RTC_STATE_ERROR";

	/**
	 * RTC状態色のキー： UnKnown
	 */
	public static final String COLOR_RTC_STATE_UNKNOWN = SystemEditorPreferenceManager.class
			.getName()
			+ "COLOR_RTC_STATE_UNKNOWN";

	/**
	 * RTCExecutionContext色のキー： Running
	 */
	public static final String COLOR_RTC_EXECUTION_CONTEXT_RUNNING = SystemEditorPreferenceManager.class
			.getName()
			+ "COLOR_RTC_EXECUTION_CONTEXT_RUNNING";

	/**
	 * RTCExecutionContext色のキー： Stopped
	 */
	public static final String COLOR_RTC_EXECUTION_CONTEXT_STOPPED = SystemEditorPreferenceManager.class
			.getName()
			+ "COLOR_RTC_EXECUTION_CONTEXT_STOPPED";

	/**
	 * DataPort色のキー： 未接続
	 */
	public static final String COLOR_DATAPORT_NO_CONNECT = SystemEditorPreferenceManager.class
			.getName()
			+ "COLOR_DATAPORT_NO_CONNECT";

	/**
	 * DataPort色のキー： 接続済
	 */
	public static final String COLOR_DATAPORT_CONNECTED = SystemEditorPreferenceManager.class
			.getName()
			+ "COLOR_DATAPORT_CONNECTED";

	/**
	 * ServicePort色のキー： 未接続
	 */
	public static final String COLOR_SERVICEPORT_NO_CONNECT = SystemEditorPreferenceManager.class
			.getName()
			+ "COLOR_SERVICEPORT_NO_CONNECT";

	/**
	 * ServicePort色のキー： 接続済
	 */
	public static final String COLOR_SERVICEPORT_CONNECTED = SystemEditorPreferenceManager.class
			.getName()
			+ "COLOR_SERVICEPORT_CONNECTED";

	/**
	 * 同期間隔のキー： システムエディタ
	 */
	public static final String SYNC_SYSTEMEDITOR_INTERVAL = SystemEditorPreferenceManager.class
			.getName()
			+ "SYNC_SYSTEMEDITOR_INTERVAL";

	//接続情報
	/**
	 * Interface Typeのキー
	 */
	private static final String CONNECT_INTERFACE_TYPE = SystemEditorPreferenceManager.class.getName()
			+ "CONNECT_INTERFACE_TYPE";

	/**
	 * DataFlow Typeのキー
	 */
	private static final String CONNECT_DATAFLOW_TYPE = SystemEditorPreferenceManager.class.getName()
			+ "CONNECT_DATAFLOW_TYPE";

	/**
	 * Subscription Typeのキー
	 */
	private static final String CONNECT_SUBSCRIPTION_TYPE = SystemEditorPreferenceManager.class.getName()
			+ "CONNECT_SUBSCRIPTION_TYPE";

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
	}

	/**
	 * キャッシュした色（リソース）を管理するマップ
	 */
	private static transient final Map<String, Color> cachedColorMap = new HashMap<String, Color>();

	/**
	 * Interface Typeのデフォルト値
	 */
	public static String[] defaultConnectInterfaceType = {"CORBA_Any", "TCP_Any"};

	/**
	 * DataFlow Typeのデフォルト値
	 */
	public static String[] defaultConnectDataFlowType = {"PUSH", "PULL"};
	
	/**
	 * subscription Typeのデフォルト値
	 */
	public static String[] defaultConnectSubscriptionType = {"Flush", "New", "Periodic"};

	/**
	 * コンストラクタ
	 * 
	 * @return シングルトン
	 */
	public static SystemEditorPreferenceManager getInstance() {
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
		RGB result = PreferenceConverter.getColor(RTSystemEditorPlugin.getDefault()
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

		PreferenceConverter.setValue(RTSystemEditorPlugin.getDefault()
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
		RTSystemEditorPlugin.getDefault().getPreferenceStore().setDefault(key, -1);

		int result = RTSystemEditorPlugin.getDefault().getPreferenceStore().getInt(key);
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

		RTSystemEditorPlugin.getDefault().getPreferenceStore().setValue(key, interval);

		propertyChangeSupport.firePropertyChange(key, oldInterval, interval);
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

	/**
	 * Interface Typeを取得する
	 * 
	 * @return Interface Typeリスト
	 */
	public String[] getInterfaceTypes() {
		RTSystemEditorPlugin.getDefault().getPreferenceStore().setDefault(CONNECT_INTERFACE_TYPE, "");

		String resultTemp = RTSystemEditorPlugin.getDefault().getPreferenceStore().getString(CONNECT_INTERFACE_TYPE);
		String[] result;
		if (resultTemp.equals("")) { // defaultvalue
			result = defaultConnectInterfaceType;
		} else {
			result = resultTemp.split(Separator);
		}
		return result;
	}
	/**
	 * Interface Typeを設定する
	 * 
	 * @param interfaceType
	 *            Interface Typeリスト
	 */
	public void setInterfaceTypes(ArrayList<String> interfaceType) {
		String[] oldInterfaceType = getInterfaceTypes();
		RTSystemEditorPlugin.getDefault().getPreferenceStore().setValue(CONNECT_INTERFACE_TYPE, convertList2String(interfaceType));
		propertyChangeSupport.firePropertyChange(CONNECT_INTERFACE_TYPE, oldInterfaceType, interfaceType);
	}

	/**
	 * Data Flow Typeを取得する
	 * 
	 * @return Data Flow Typeリスト
	 */
	public String[] getDataFlowTypes() {
		RTSystemEditorPlugin.getDefault().getPreferenceStore().setDefault(CONNECT_DATAFLOW_TYPE, "");

		String resultTemp = RTSystemEditorPlugin.getDefault().getPreferenceStore().getString(CONNECT_DATAFLOW_TYPE);
		String[] result;
		if (resultTemp.equals("")) { // defaultvalue
			result = defaultConnectDataFlowType;
		} else {
			result = resultTemp.split(Separator);
		}
		return result;
	}
	/**
	 * Data Flow Typeを設定する
	 * 
	 * @param dataFlowType
	 *            Data Flow Typeリスト
	 */
	public void setDataFlowTypes(ArrayList<String> dataflowType) {
		String[] oldDataFlowType = getDataFlowTypes();
		RTSystemEditorPlugin.getDefault().getPreferenceStore().setValue(CONNECT_DATAFLOW_TYPE, convertList2String(dataflowType));
		propertyChangeSupport.firePropertyChange(CONNECT_DATAFLOW_TYPE, oldDataFlowType, dataflowType);
	}

	/**
	 * Subscription Typeを取得する
	 * 
	 * @return Subscription Typeリスト
	 */
	public String[] getSubscriptionTypes() {
		RTSystemEditorPlugin.getDefault().getPreferenceStore().setDefault(CONNECT_SUBSCRIPTION_TYPE, "");

		String resultTemp = RTSystemEditorPlugin.getDefault().getPreferenceStore().getString(CONNECT_SUBSCRIPTION_TYPE);
		String[] result;
		if (resultTemp.equals("")) { // defaultvalue
			result = defaultConnectSubscriptionType;
		} else {
			result = resultTemp.split(Separator);
		}
		return result;
	}
	/**
	 * subscription Typeを設定する
	 * 
	 * @param subscriptionType
	 *            subscription Typeリスト
	 */
	public void setSubscriptionTypes(ArrayList<String> subscriptionType) {
		String[] oldSubscriptionType = getSubscriptionTypes();
		RTSystemEditorPlugin.getDefault().getPreferenceStore().setValue(CONNECT_SUBSCRIPTION_TYPE, convertList2String(subscriptionType));
		propertyChangeSupport.firePropertyChange(CONNECT_SUBSCRIPTION_TYPE, oldSubscriptionType, subscriptionType);
	}

	private static String convertList2String(ArrayList<String> source) {
		StringBuffer resultTemp = new StringBuffer();
		
		for(String target : source) {
			resultTemp.append(target);
			resultTemp.append(Separator);
		}
		String result = resultTemp.toString();
		if(result.length() ==0) return "";
		return result.substring(0, result.length()-1);
	}

}
