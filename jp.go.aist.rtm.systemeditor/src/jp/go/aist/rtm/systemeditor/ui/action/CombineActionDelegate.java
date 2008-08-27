package jp.go.aist.rtm.systemeditor.ui.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jp.go.aist.rtm.systemeditor.factory.SystemEditorWrapperFactory;
import jp.go.aist.rtm.systemeditor.ui.dialog.NewCompositeComponentDialog;
import jp.go.aist.rtm.systemeditor.ui.editor.AbstractSystemDiagramEditor;
import jp.go.aist.rtm.systemeditor.ui.editor.OfflineSystemDiagramEditor;
import jp.go.aist.rtm.systemeditor.ui.editor.command.CombineCommand;
import jp.go.aist.rtm.systemeditor.ui.editor.editpart.ComponentEditPart;
import jp.go.aist.rtm.toolscommon.model.component.AbstractComponent;
import jp.go.aist.rtm.toolscommon.model.component.Component;
import jp.go.aist.rtm.toolscommon.model.component.ComponentFactory;
import jp.go.aist.rtm.toolscommon.model.core.Rectangle;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;

/**
 * 複合コンポーネントを作成するアクション
 */
public class CombineActionDelegate implements IEditorActionDelegate {

	private ISelection selection;

	private AbstractSystemDiagramEditor targetEditor;

	private List<AbstractComponent> selectedComponents;

	/**
	 * アクションのメインの実行メソッド
	 * 
	 */
	public void run(final IAction action) {
		if (selectedComponents.size() > 0) {
			AbstractComponent compositeComponent = null;
			if (OfflineSystemDiagramEditor.OFFLINE_SYSTEM_DIAGRAM_EDITOR_ID
					.equals(targetEditor.getEditorId())) {
				compositeComponent = ComponentFactory.eINSTANCE
						.createComponentSpecification();
			} else {
				compositeComponent = ComponentFactory.eINSTANCE
						.createComponent();
			}

			NewCompositeComponentDialog dialog = new NewCompositeComponentDialog(
					targetEditor.getSite().getShell(), compositeComponent,
					selectedComponents);
			if (dialog.open() == IDialogConstants.OK_ID) {
				if (compositeComponent.getConstraint() == null) {
					Rectangle rectangle = new Rectangle();
					rectangle.setX(selectedComponents.get(0).getConstraint()
							.getX());
					rectangle.setY(selectedComponents.get(0).getConstraint()
							.getY());
					rectangle.setHeight(selectedComponents.get(0)
							.getConstraint().getHeight());
					rectangle.setWidth(selectedComponents.get(0)
							.getConstraint().getWidth());
					compositeComponent.setConstraint(rectangle);
				}
				if (compositeComponent.getPathId() == null) {
					String pathId = selectedComponents.get(0).getPathId();
					compositeComponent.setPathId(pathId.substring(0, pathId
							.indexOf("/") + 1)
							+ compositeComponent.getInstanceNameL() + ".rtc");
				}
				SystemEditorWrapperFactory.getInstance()
						.getSynchronizationManager()
						.assignSynchonizationSupport(compositeComponent);
				compositeComponent.getComponents().addAll(selectedComponents);
				compositeComponent
						.setCompsiteType(Component.COMPOSITE_COMPONENT);
				CombineCommand command = new CombineCommand();
				command.setParent(this.targetEditor.getSystemDiagram());
				command.setTarget(compositeComponent);
				if (targetEditor.getAdapter(CommandStack.class) != null) {
					((CommandStack) targetEditor.getAdapter(CommandStack.class))
							.execute(command);
				} else {
					throw new RuntimeException();
				}
			}
		}
	}

	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		this.targetEditor = (AbstractSystemDiagramEditor) targetEditor;
	}

	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
		action.setEnabled(isEnable());
	}

	private boolean isEnable() {
		selectedComponents = new ArrayList<AbstractComponent>();
		if (selection instanceof IStructuredSelection) {
			for (Iterator iterator = ((IStructuredSelection) selection)
					.iterator(); iterator.hasNext();) {
				Object part = iterator.next();
				if (part instanceof ComponentEditPart) {
					selectedComponents.add(((ComponentEditPart) part)
							.getModel());
				}
			}
		}
		return (selectedComponents.size() > 0);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean combineR() {
		// boolean result = PortConnectorImpl.createConnectorR(getFirst(),
		// getSecond(), connectorProfile);
		boolean result = true;
		if (result == false) {
			MessageDialog.openError(targetEditor.getSite().getShell(), "エラー",
					"接続に失敗しました。");
		}

		return result;
	}
}
