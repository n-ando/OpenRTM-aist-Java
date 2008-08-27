package jp.go.aist.rtm.nameserviceview.manager;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

import jp.go.aist.rtm.nameserviceview.NameServiceViewPlugin;


/**
 * 設定を管理するマネージャ
 * <p>
 * 設定情報にアクセスするにはこのインスタンスを使用する
 */
public class NameServiceViewPreferenceManager {
	private static NameServiceViewPreferenceManager __instance = new NameServiceViewPreferenceManager();

	/**
	 * 同期間隔のキー： ネームサーバ
	 */
	public static final String SYNC_NAMESERVER_INTERVAL = NameServiceViewPreferenceManager.class
			.getName()
			+ "SYNC_NAMESERVICE_INTERVAL";

	/**
	 * デフォルト接続ポート
	 */
	public static final String DEFAULT_CONNECTION_PORT = NameServiceViewPreferenceManager.class
			.getName()
			+ "DEFAULT_CONNECTION_PORT";

	/**
	 * タイムアウト判定時間
	 */
	public static final String DEFAULT_TIMEOUT_PERIOD = NameServiceViewPreferenceManager.class
			.getName()
			+ "DEFAULT_TIMEOUT_PERIOD";

	/**
	 * デフォルトの同期間隔を管理するマップ
	 */
	public static final Map<String, Integer> defaultInvervalMap = new HashMap<String, Integer>();
	static {
		defaultInvervalMap.put(SYNC_NAMESERVER_INTERVAL, 1000);
	}

	/**
	 * デフォルト接続ポート
	 */
	public static final String defaultConnectionPort = new String("2809");


	/**
	 * コンストラクタ
	 * 
	 * @return シングルトン
	 */
	public static NameServiceViewPreferenceManager getInstance() {
		return __instance;
	}

	protected PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);

	/**
	 * 間隔を取得する
	 * 
	 * @param key
	 *            キー
	 * @return 間隔
	 */
	public int getInterval(String key) {
		NameServiceViewPlugin.getDefault().getPreferenceStore().setDefault(key, -1);

		int result = NameServiceViewPlugin.getDefault().getPreferenceStore().getInt(key);
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

		NameServiceViewPlugin.getDefault().getPreferenceStore().setValue(key, interval);

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
		if (NameServiceViewPlugin.getDefault() == null){
			return defaultConnectionPort;
		}
		NameServiceViewPlugin.getDefault().getPreferenceStore().setDefault(key, "");

		String result = NameServiceViewPlugin.getDefault().getPreferenceStore().getString(
				key);
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

		NameServiceViewPlugin.getDefault().getPreferenceStore().setValue(key, defaultPort);

		propertyChangeSupport.firePropertyChange(key, oldDefaultPort,
				defaultPort);
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
	 * デフォルト間隔のマップを取得する
	 * 
	 * @return デフォルト間隔のマップ
	 */
	public Map<String, Integer> getDefaultIntervalMap() {
		return defaultInvervalMap;
	}

}
