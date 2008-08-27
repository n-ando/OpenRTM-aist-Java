package jp.go.aist.rtm.logview.preferences;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import jp.go.aist.rtm.logview.LogviewPlugin;

public class PreferenceManager {
	private static PreferenceManager __instance = new PreferenceManager();

	/**
	 * コンストラクタ
	 * 
	 * @return シングルトン
	 */
	public static PreferenceManager getInstance() {
		return __instance;
	}

	/**
	 * デフォルト描画時間周期
	 */
	public static final int defaultRedrawPeriod = 200;

	/**
	 * デフォルト・コンフィギュレーション・ファイル
	 */
	public static final String defaultConfigFile = "rtc.conf";

	/**
	 * コンフィギュレーション・ファイルのキー
	 */
	public static final String CONFIGURATION_FILE = PreferenceManager.class
			.getName()
			+ "CONFIGURATION_FILE";

	/**
	 * 描画時間周期のキー
	 */
	public static final String REDRAW_RERIOD = PreferenceManager.class
			.getName()
			+ "REDRAW_PERIOD";

	protected PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);

	/**
	 * コンフィギュレーション・ファイルを設定する
	 * 
	 * @param key
	 *            キー
	 * @param configFile
	 *            コンフィギュレーション・ファイル
	 */
	public void setConfiguratiolFile(String key, String configFile) {
		String oldConfigFile = getConfigurationFile(key);

		LogviewPlugin.getDefault().getPreferenceStore().setValue(key, configFile);

		propertyChangeSupport.firePropertyChange(key, oldConfigFile, configFile);
	}

	/**
	 * コンフィギュレーション・ファイルを取得する
	 * 
	 * @param key
	 *            キー
	 * @return コンフィギュレーション・ファイル
	 */
	public String getConfigurationFile(String key) {
		LogviewPlugin.getDefault().getPreferenceStore().setDefault(key, "");

		String result = LogviewPlugin.getDefault().getPreferenceStore().getString(key);
		if (result.equals("")) { // defaultvalue
			result = defaultConfigFile;
		}

		return result;
	}

	/**
	 * 描画時間周期を設定する
	 * 
	 * @param key
	 *            キー
	 * @param configFile
	 *            描画時間周期
	 */
	public void setRedrawPeriod(String key, int interval) {
		int oldPeriod = getRedrawPeriod(key);

		LogviewPlugin.getDefault().getPreferenceStore().setValue(key, interval);

		propertyChangeSupport.firePropertyChange(key, oldPeriod, interval);
	}

	/**
	 * 描画時間周期を取得する
	 * 
	 * @param key
	 *            キー
	 * @return 描画時間周期
	 */
	public int getRedrawPeriod(String key) {
		LogviewPlugin.getDefault().getPreferenceStore().setDefault(key, "");

		int result = LogviewPlugin.getDefault().getPreferenceStore().getInt(key);
		if (result == 0) { // defaultvalue
			result = defaultRedrawPeriod;
		}

		return result;
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
