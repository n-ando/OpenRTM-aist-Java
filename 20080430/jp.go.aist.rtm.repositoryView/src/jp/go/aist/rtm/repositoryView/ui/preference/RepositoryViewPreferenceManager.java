package jp.go.aist.rtm.repositoryView.ui.preference;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import jp.go.aist.rtm.repositoryView.RepositoryViewPlugin;

public class RepositoryViewPreferenceManager {
	private static RepositoryViewPreferenceManager __instance = new RepositoryViewPreferenceManager();

	/**
	 * コンストラクタ
	 * 
	 * @return シングルトン
	 */
	public static RepositoryViewPreferenceManager getInstance() {
		return __instance;
	}

	/**
	 * 警告数のキー
	 */
	public static final String CAUTION_COUNT = RepositoryViewPreferenceManager.class.getName()
			+ "CAUTION_COUNT";

	protected PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);

	/**
	 * デフォルトの警告数
	 */
	public static final int defaultCautionCount = 500;

	/**
	 * 警告数を設定する
	 * 
	 * @param key キー
	 * @param defaulCount 設定値
	 */
	public void setCaution_Count(int defaulCount) {
		int oldCautionCount = defaultCautionCount;

		RepositoryViewPlugin.getDefault().getPreferenceStore().setValue(CAUTION_COUNT, defaulCount);

		propertyChangeSupport.firePropertyChange(CAUTION_COUNT, oldCautionCount, defaulCount);
	}
	
	/**
	 * 警告数を取得する
	 * 
	 * @param key キー
	 * @return cautionCount 設定値
	 */
	public int getCaution_Count() {
		RepositoryViewPlugin.getDefault().getPreferenceStore().setDefault(CAUTION_COUNT, "");

		int resultTemp = RepositoryViewPlugin.getDefault().getPreferenceStore().getInt(CAUTION_COUNT);
		int result;
		if (resultTemp == 0 ) { // defaultvalue
			result = defaultCautionCount;
		} else {
			result = resultTemp;
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
