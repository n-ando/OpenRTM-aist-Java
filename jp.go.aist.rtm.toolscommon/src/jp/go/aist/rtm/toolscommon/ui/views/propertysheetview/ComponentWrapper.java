package jp.go.aist.rtm.toolscommon.ui.views.propertysheetview;

import org.eclipse.emf.ecore.EObject;

/**
 * コンポーネントのラッパクラス
 */
public class ComponentWrapper {
	private EObject component;

	/**
	 * コンストラクタ
	 * 
	 * @param component
	 *            ドメインモデル
	 */
	public ComponentWrapper(EObject component) {
		this.component = component;
	}

	/**
	 * コンポーネントを取得する
	 * 
	 * @return コンポーネント
	 */
	public EObject getComponent() {
		return component;
	}

	/**
	 * コンポーネントを設定する
	 * 
	 * @param component
	 *            コンポーネント
	 */
	public void setComponent(EObject component) {
		this.component = component;
	}
}
