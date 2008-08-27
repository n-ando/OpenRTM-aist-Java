package jp.go.aist.rtm.rtclink.ui.views.propertysheetview;

import jp.go.aist.rtm.rtclink.model.component.Component;

/**
 * コンポーネントのラッパクラス
 */
public class ComponentWrapper {
	private Component component;

	/**
	 * コンストラクタ
	 * 
	 * @param component
	 *            ドメインモデル
	 */
	public ComponentWrapper(Component component) {
		this.component = component;
	}

	/**
	 * コンポーネントを取得する
	 * 
	 * @return コンポーネント
	 */
	public Component getComponent() {
		return component;
	}

	/**
	 * コンポーネントを設定する
	 * 
	 * @param component
	 *            コンポーネント
	 */
	public void setComponent(Component component) {
		this.component = component;
	}
}
