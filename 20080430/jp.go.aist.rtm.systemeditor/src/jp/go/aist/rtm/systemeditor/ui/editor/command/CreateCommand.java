package jp.go.aist.rtm.systemeditor.ui.editor.command;

import java.util.ArrayList;
import java.util.Iterator;
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
public class CreateCommand extends Command {
	private SystemDiagram parent;

	private AbstractComponent target;

	private AbstractComponent targetcopy;

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void execute() {
		AbstractComponent copyTarget = (AbstractComponent) SystemEditorWrapperFactory
			.getInstance().copy(target);
		parent.getComponents().add(copyTarget);
		ComponentUtil.copieAndSetCompositeComponents(parent, target);
		setComponentsConstraint(copyTarget);
		if (parent.getOpenCompositeComponentAction() != null) {
			OpenCompositeComponentAction action = (OpenCompositeComponentAction) parent
					.getOpenCompositeComponentAction();
			AbstractComponent localObject = null;
			AbstractComponent compositeComponent = action.getCompositeComponent();
			while (action != null) {
				if (target.getCorbaBaseObject() != null) {
					localObject = (AbstractComponent) SynchronizationSupport
						.findLocalObjectByRemoteObject(new Object[] { target
							.getCorbaBaseObject() }, action
							.getParentSystemDiagramEditor()
							.getSystemDiagram());
				} else {
					localObject = ComponentUtil.findComponentByPathId(target,
							action.getParentSystemDiagramEditor()
									.getSystemDiagram());
				}
				
				if (localObject == null) {
					targetcopy = (AbstractComponent) SystemEditorWrapperFactory
							.getInstance().copy(target);
					if (compositeComponent.getCorbaObject() != null) {
						targetcopy.setCompositeComponent((AbstractComponent) SynchronizationSupport
						.findLocalObjectByRemoteObject(new Object[] { compositeComponent.getCorbaObject() },
								action
								.getParentSystemDiagramEditor()
								.getSystemDiagram()));
					} else {
						targetcopy.setCompositeComponent(ComponentUtil
								.findComponentByPathId(compositeComponent, action
										.getParentSystemDiagramEditor()
										.getSystemDiagram()));
					}
					
					action.getParentSystemDiagramEditor().getSystemDiagram()
							.getComponents().add(targetcopy);
					ComponentUtil.copieAndSetCompositeComponents(action.getParentSystemDiagramEditor()
							.getSystemDiagram(), target);
				} else {
					if (compositeComponent.getCorbaObject() != null) {
						localObject.setCompositeComponent((AbstractComponent) SynchronizationSupport
						.findLocalObjectByRemoteObject(new Object[] { compositeComponent.getCorbaObject() },
								action
								.getParentSystemDiagramEditor()
								.getSystemDiagram()));
					} else {
						localObject.setCompositeComponent(ComponentUtil
								.findComponentByPathId(compositeComponent, action
										.getParentSystemDiagramEditor()
										.getSystemDiagram()));
					}
				}
				action = (OpenCompositeComponentAction) action
						.getParentSystemDiagramEditor().getSystemDiagram()
						.getOpenCompositeComponentAction();
			}
		}
	}

	List<Integer> counts = new ArrayList<Integer>();
	private void setComponentsConstraint(AbstractComponent component) {
		int count = 0;
		counts.add(count);
		Integer temp = counts.get(counts.size() - 1);
		for (Iterator iterator = component.getComponents().iterator(); iterator
				.hasNext();) {
			AbstractComponent tempComponent = (AbstractComponent) iterator.next();
			if (tempComponent.getConstraint() == null) {
				tempComponent.setConstraint(ComponentUtil
						.getNewComponentConstraint(component.getConstraint(), temp));
				temp++;
				counts.set(counts.size() - 1, temp);
			}
			if (tempComponent.isCompositeComponent()) {
				setComponentsConstraint(tempComponent);
			}
		}
		counts.remove(counts.size() - 1);
		if (counts.size() > 1) {
			temp = counts.get(counts.size() - 1);
		}
		
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
	 * 作成対象のRtcを設定する
	 * 
	 * @param target
	 *            作成対象のRtc
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
				tmp.setCompositeComponent(null);
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
