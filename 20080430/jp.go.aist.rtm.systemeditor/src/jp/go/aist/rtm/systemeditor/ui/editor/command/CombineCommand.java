package jp.go.aist.rtm.systemeditor.ui.editor.command;

import java.util.ArrayList;
import java.util.List;

import jp.go.aist.rtm.systemeditor.factory.SystemEditorWrapperFactory;
import jp.go.aist.rtm.systemeditor.ui.action.OpenCompositeComponentAction;
import jp.go.aist.rtm.systemeditor.ui.util.ComponentUtil;
import jp.go.aist.rtm.toolscommon.model.component.AbstractComponent;
import jp.go.aist.rtm.toolscommon.model.component.SystemDiagram;
import jp.go.aist.rtm.toolscommon.synchronizationframework.SynchronizationSupport;

import org.eclipse.gef.commands.Command;

/**
 * システムダイアグラムにRtcを追加するコマンド
 */
public class CombineCommand extends Command {
	private SystemDiagram parent;

	private AbstractComponent target;

	private AbstractComponent targetcopy;

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void execute() {
		List<AbstractComponent> selectedComponents = new ArrayList<AbstractComponent>();
		selectedComponents.addAll(target.getComponents());
		AbstractComponent copyCompositeComponent = null;
		OpenCompositeComponentAction openAction = null;
		openAction = (OpenCompositeComponentAction) parent
				.getOpenCompositeComponentAction();
		if (openAction != null) {
			OpenCompositeComponentAction parentAction = openAction;
			AbstractComponent parentCompositeComponent = parentAction
					.getCompositeComponent();
			// 親エディタ分の複合コンポーネントをコピーし、
			// その親エディタに存在する結合対象(selectedComponent)を探して、
			// 複合コンポーネントに設定する。
			while (parentAction != null) {
				copyCompositeComponent = (AbstractComponent) SystemEditorWrapperFactory
						.getInstance().copy(target);
				for (AbstractComponent selectedComponent : selectedComponents) {
					AbstractComponent tmp = null;
					if (selectedComponent.getCorbaBaseObject() != null) {
						tmp = (AbstractComponent) SynchronizationSupport
								.findLocalObjectByRemoteObject(
										new Object[] { selectedComponent
												.getCorbaBaseObject() },
										parentAction
												.getParentSystemDiagramEditor()
												.getSystemDiagram());
					} else {
						tmp = ComponentUtil.findComponentByPathId(
								selectedComponent, parentAction
										.getParentSystemDiagramEditor()
										.getSystemDiagram());
					}
					if (tmp != null) {
						copyCompositeComponent.getComponents().add(tmp);
					} else {
						throw new RuntimeException();
					}

				}
				if (parentCompositeComponent.getCorbaObject() != null) {
					copyCompositeComponent
							.setCompositeComponent((AbstractComponent) SynchronizationSupport
									.findLocalObjectByRemoteObject(
											new Object[] { parentCompositeComponent
													.getCorbaBaseObject() },
											parentAction
													.getParentSystemDiagramEditor()
													.getSystemDiagram()));
				} else {
					copyCompositeComponent.setCompositeComponent(ComponentUtil
							.findComponentByPathId(parentCompositeComponent,
									parentAction.getParentSystemDiagramEditor()
											.getSystemDiagram()));
				}
				parentAction.getParentSystemDiagramEditor().getSystemDiagram()
						.getComponents().add(copyCompositeComponent);
				parentAction = (OpenCompositeComponentAction) parentAction
						.getParentSystemDiagramEditor().getSystemDiagram()
						.getOpenCompositeComponentAction();
			}
		}
		parent.getComponents().add(target);
	}

	/**
	 * 親となるシステムダイアグラムを設定する
	 * 
	 * @param parent
	 *            親となるシステムダイアグラム
	 */
	public void setParent(SystemDiagram parent) {
		this.parent = parent;
	}

	/**
	 * 複合コンポーネントを設定する
	 * 
	 * @param target
	 *            複合コンポーネント
	 */
	public void setTarget(AbstractComponent target) {
		this.target = target;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void undo() {
		SystemDiagram systemDiagram = parent;
		OpenCompositeComponentAction parentAction = (OpenCompositeComponentAction) parent
				.getOpenCompositeComponentAction();
		do {
			AbstractComponent tmp = null;
			if (target.getCorbaBaseObject() != null) {
				tmp = (AbstractComponent) SynchronizationSupport
						.findLocalObjectByRemoteObject(new Object[] { target
								.getCorbaBaseObject() }, systemDiagram);
			} else {
				tmp = ComponentUtil.findComponentByPathId(target,
						systemDiagram);
			}
			if (tmp != null) {
				systemDiagram.getComponents().remove(tmp);
				tmp.getComponents().clear();
				tmp.setOpenCompositeComponentAction(null);
			} else {
				throw new RuntimeException();
			}
			systemDiagram = parentAction.getParentSystemDiagramEditor()
					.getSystemDiagram();
			parentAction = (OpenCompositeComponentAction) parentAction
					.getParentSystemDiagramEditor().getSystemDiagram()
					.getOpenCompositeComponentAction();
		} while (parentAction != null);
	}
}
