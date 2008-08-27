package jp.go.aist.rtm.systemeditor.ui.action;

import java.util.Iterator;

import jp.go.aist.rtm.systemeditor.ui.editor.AbstractSystemDiagramEditor;
import jp.go.aist.rtm.systemeditor.ui.editor.NullEditorInput;
import jp.go.aist.rtm.systemeditor.ui.editor.OfflineSystemDiagramEditor;
import jp.go.aist.rtm.systemeditor.ui.util.ComponentUtil;
import jp.go.aist.rtm.toolscommon.model.component.AbstractComponent;
import jp.go.aist.rtm.toolscommon.model.component.Component;
import jp.go.aist.rtm.toolscommon.model.component.ComponentSpecification;
import jp.go.aist.rtm.toolscommon.model.component.SystemDiagram;
import jp.go.aist.rtm.toolscommon.synchronizationframework.SynchronizationSupport;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

/**
 * 複合コンポーネントを開くアクション
 */
public class OpenCompositeComponentAction extends Action {

	private IWorkbenchPart parentSystemDiagramEditor;

	private AbstractComponent compositeComponent;

	private IEditorPart compositeComponentEditor;

	private boolean compositeComponentEditorOpened;
	
	private static IPartListener listener;

	public static final String ACTION_ID = OpenCompositeComponentAction.class
			.getName()
			+ "_ACTION_ID";

	public OpenCompositeComponentAction(IWorkbenchPart parentPart) {
		setId(ACTION_ID);
		this.parentSystemDiagramEditor = parentPart;
	}

	@Override
	/**
	 * {@inheritDoc}
	 * 
	 */
	public void run() {
		IEditorPart oldIEditorPart = null;
		compositeComponentEditorOpened = true;
		boolean notExist = true;
		try {
			if (compositeComponentEditor == null) {
				compositeComponentEditor = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage().openEditor(
								new NullEditorInput(),
								getParentSystemDiagramEditor().getEditorId());
			} else {
				oldIEditorPart = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage().findEditor(
								compositeComponentEditor.getEditorInput());
				if (oldIEditorPart == null) {
					oldIEditorPart = compositeComponentEditor;
					compositeComponentEditor = PlatformUI
							.getWorkbench()
							.getActiveWorkbenchWindow()
							.getActivePage()
							.openEditor(
									compositeComponentEditor.getEditorInput(),
									getParentSystemDiagramEditor().getEditorId());
				} else {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow()
							.getActivePage().activate(compositeComponentEditor);
					notExist = false;
				}
			}
			if (compositeComponentEditor instanceof OfflineSystemDiagramEditor) {
				setListener();
			}
			if (notExist) {
				compositeComponent2Editor();
				getCompositeSystemDiagram().setOpenCompositeComponentAction(
						this);
				OpenCompositeComponentAction action = this;
				while (action != null) {
					
					AbstractComponent compositecomponent = null;
					if (this.compositeComponent.getCorbaObject() != null) {
						compositecomponent = (AbstractComponent) SynchronizationSupport
							.findLocalObjectByRemoteObject(
								new Object[] { this.compositeComponent
										.getCorbaBaseObject() }, action
										.getParentSystemDiagramEditor()
										.getSystemDiagram());
					} else {
						compositecomponent = ComponentUtil
						.findComponentByPathId(this.compositeComponent, action
								.getParentSystemDiagramEditor()
								.getSystemDiagram());	
					}
					if (compositecomponent != null) {
						compositecomponent
								.setOpenCompositeComponentAction(this);
					}
					action = (OpenCompositeComponentAction) action
							.getParentSystemDiagramEditor().getSystemDiagram()
							.getOpenCompositeComponentAction();
				}
				for (Iterator iterator = getCompositeComponent()
						.getComponents().iterator(); iterator.hasNext();) {
					AbstractComponent compnent = (AbstractComponent) iterator.next();
					if (compnent.isCompositeComponent()
							&& compnent.getOpenCompositeComponentAction() != null) {
						if (((OpenCompositeComponentAction) compnent
								.getOpenCompositeComponentAction())
								.getParentSystemDiagramEditor() == oldIEditorPart) {
							((OpenCompositeComponentAction) compnent
									.getOpenCompositeComponentAction())
									.setParentSystemDiagramEditor(getCompositeComponentEditor());
						}
					}
				}
				getCompositeComponentEditor().changeFile(null);
			}
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 複合コンポーネントのリモートオブジェクトが死んでしまった時に呼ばれ、 複合コンポーネントから開いたエディタ(及び子エディタ)を閉じる。
	 */
	public void runWithEvent(Event event) {
		if (event == null && compositeComponentEditorOpened) {
			Component localObject = null;
			SystemDiagram systemDiagram = ComponentUtil
					.getRootSystemDiagram(getParentSystemDiagramEditor()
							.getSystemDiagram());
			if (compositeComponent.getCorbaBaseObject() != null) {
				localObject = (Component) SynchronizationSupport
						.findLocalObjectByRemoteObject(
								new Object[] { compositeComponent
										.getCorbaBaseObject() }, systemDiagram);
			} else {
				localObject = (Component) ComponentUtil.findComponentByPathId(
						compositeComponent, systemDiagram);
			}
			if (localObject == null) {
				getCompositeSystemDiagram().getPropertyChangeSupport()
						.firePropertyChange("SYSTEM_DIAGRAM_COMPONENTS",
								compositeComponent, null);
			}
		}
	}

	public void setCompositeComponent(AbstractComponent component) {
		this.compositeComponent = component;
	}

	public AbstractComponent getCompositeComponent() {
		return this.compositeComponent;
	}

	public void compositeComponent2Editor() {
		getCompositeSystemDiagram().getComponents().clear();
		int count = 0;
		for (Iterator iterator = this.compositeComponent.getAllComponents()
				.iterator(); iterator.hasNext();) {
			AbstractComponent component = (AbstractComponent) iterator.next();
			if (component.getConstraint() == null) {
				component.setConstraint(ComponentUtil.getNewComponentConstraint(
						this.compositeComponent.getConstraint(), count));
				count++;
			}
		}
		ComponentUtil.copieAndSetCompositeComponents(getCompositeSystemDiagram(),
				this.compositeComponent);
		if (compositeComponent instanceof ComponentSpecification) {
			ComponentUtil.createSpecificationConnector(getCompositeSystemDiagram(), true);
		}
	}

	public AbstractSystemDiagramEditor getParentSystemDiagramEditor() {
		return (AbstractSystemDiagramEditor) this.parentSystemDiagramEditor;
	}

	public void setParentSystemDiagramEditor(AbstractSystemDiagramEditor parent) {
		this.parentSystemDiagramEditor = parent;
	}

	public AbstractSystemDiagramEditor getCompositeComponentEditor() {
		return (AbstractSystemDiagramEditor) this.compositeComponentEditor;
	}

	private SystemDiagram getCompositeSystemDiagram() {
		return getCompositeComponentEditor().getSystemDiagram();
	}

	public void setCompositeComponentEditorOpened(
			boolean compositeComponentEditorOpened) {
		this.compositeComponentEditorOpened = compositeComponentEditorOpened;
	}
	
	private void setListener() {
		if (listener == null) {
			listener = new IPartListener() {
				public void partActivated(IWorkbenchPart part) {
					if (part instanceof OfflineSystemDiagramEditor) {
						ComponentUtil.createSpecificationConnector(
								((OfflineSystemDiagramEditor) part)
										.getSystemDiagram(), true);
					}
					
				}

				public void partBroughtToTop(IWorkbenchPart part) {
					
				}

				public void partClosed(IWorkbenchPart part) {
					
				}

				public void partDeactivated(IWorkbenchPart part) {
					
				}

				public void partOpened(IWorkbenchPart part) {
					
				}
			};
			PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().addPartListener(listener);
		}
	}
}
