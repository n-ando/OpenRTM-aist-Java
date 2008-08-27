package jp.go.aist.rtm.systemeditor.ui.editor.dnd;

import java.util.Iterator;

import jp.go.aist.rtm.systemeditor.factory.SystemEditorWrapperFactory;
import jp.go.aist.rtm.systemeditor.ui.util.ComponentUtil;
import jp.go.aist.rtm.toolscommon.model.component.AbstractComponent;

import org.eclipse.gef.requests.CreationFactory;

/**
 * ドラッグ＆ドロップ時、コンポーネントを作成するファクトリ
 */
public class ComponentFactory implements CreationFactory {
	private AbstractComponent component;

	/**
	 * {@inheritDoc}
	 */
	public Object getObjectType() {
		return component.getClass();
	}

	/**
	 * コンポーネントのリモートオブジェクトを設定する
	 * 
	 * @param remoteObject
	 *            コンポーネントのリモートオブジェクト
	 */
	public void setComponent(AbstractComponent component) {
		this.component = component;
	}
	
	/**
	 * コンポーネントのリモートオブジェクトを設定する
	 * 
	 * @param remoteObject
	 *            コンポーネントのリモートオブジェクト
	 */
	protected AbstractComponent getComponent() {
		return this.component;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Object getNewObject() {
		AbstractComponent result = null;
		if (getComponent() != null) {
			result = (AbstractComponent) SystemEditorWrapperFactory
					.getInstance().copy(getComponent());
			for (Iterator iterator = getComponent().getComponents().iterator(); iterator
					.hasNext();) {
				AbstractComponent tempComponent = (AbstractComponent) iterator.next();
				result.getComponents().add(
						SystemEditorWrapperFactory.getInstance().copy(
								tempComponent));
				ComponentUtil.copieAndSetCompositeComponents(result,
						tempComponent);
			}
		}

		return result;
	}
}
