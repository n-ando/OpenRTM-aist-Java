package jp.go.aist.rtm.rtclink.ui.editor.dnd;

import jp.go.aist.rtm.rtclink.factory.CorbaWrapperFactory;
import jp.go.aist.rtm.rtclink.model.component.Component;
import jp.go.aist.rtm.rtclink.model.core.WrapperObject;

import org.eclipse.gef.requests.CreationFactory;

/**
 * ドラッグ＆ドロップ時、コンポーネントを作成するファクトリ
 */
public class ComponentFactory implements CreationFactory {
	private Component component;

	/**
	 * {@inheritDoc}
	 */
	public Object getNewObject() {
		WrapperObject result = null;
		if (component != null) {
			result = CorbaWrapperFactory.getInstance().copy(component);
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object getObjectType() {
		return Component.class;
	}

	/**
	 * コンポーネントのリモートオブジェクトを設定する
	 * 
	 * @param remoteObject
	 *            コンポーネントのリモートオブジェクト
	 */
	public void setComponent(Component component) {
		this.component = component;
	}
}
