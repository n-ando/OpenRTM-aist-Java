package jp.go.aist.rtm.toolscommon.manager;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import jp.go.aist.rtm.toolscommon.ToolsCommonPlugin;

/**
 * 設定を管理するマネージャ
 * <p>
 * 設定情報にアクセスするにはこのインスタンスを使用する
 */
public class ToolsCommonPreferenceManager {
	private static ToolsCommonPreferenceManager __instance = new ToolsCommonPreferenceManager();

	/**
	 * タイムアウト判定時間
	 */
	public static final String DEFAULT_TIMEOUT_PERIOD = ToolsCommonPreferenceManager.class
			.getName()
			+ "DEFAULT_TIMEOUT_PERIOD";
	/**
	 * コンストラクタ
	 * 
	 * @return シングルトン
	 */
	public static ToolsCommonPreferenceManager getInstance() {
		return __instance;
	}
	protected PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);

	/**
	 * デフォルトタイムアウト判定時間
	 */
	public static final int defaultTimeoutPeriod = 3000;
	
	/**
	 * デフォルトタイムアウト判定時間を取得する
	 * 
	 * @param key
	 *            キー
	 * @return デフォルトポート
	 */
	public int getDefaultTimeout(String key) {
		// コンソールから起動された場合には、pluginは存在しない為、nullとなる。
		if (ToolsCommonPlugin.getDefault() == null){
			return defaultTimeoutPeriod;
		}
		
		ToolsCommonPlugin.getDefault().getPreferenceStore().setDefault(key, -1);

		int result = ToolsCommonPlugin.getDefault().getPreferenceStore().getInt(key);
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

		ToolsCommonPlugin.getDefault().getPreferenceStore().setValue(key,
				defaultTimeout);

		propertyChangeSupport.firePropertyChange(key, oldDefaultTimeout,
				defaultTimeout);
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
