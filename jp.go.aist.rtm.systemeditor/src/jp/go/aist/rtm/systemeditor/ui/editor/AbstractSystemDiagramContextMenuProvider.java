package jp.go.aist.rtm.systemeditor.ui.editor;

import jp.go.aist.rtm.systemeditor.ui.editor.action.OpenAction;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.actions.ActionFactory;

/**
 * システムダイアグラムのContextMenuProviderクラス
 */
public class AbstractSystemDiagramContextMenuProvider extends
		ContextMenuProvider {

	private final ActionRegistry actionRegistry;

	public AbstractSystemDiagramContextMenuProvider(EditPartViewer viewer,
			ActionRegistry actionRegistry) {
		super(viewer);
		this.actionRegistry = actionRegistry;

	}

	public void buildContextMenu(IMenuManager menuManager) {
		menuManager.add(new Separator("component_before"));
		menuManager.add(new Separator("component"));
		menuManager.add(new Separator("component_after"));
		menuManager.add(new Separator("executionContext_before"));
		menuManager.add(new Separator("executionContext"));
		menuManager.add(new Separator("executionContext_after"));
		menuManager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS
				+ "_before"));
		menuManager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		menuManager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS
				+ "_after"));
		menuManager.add(new Separator("delete_before"));
		menuManager.add(new Separator("delete"));
		menuManager.add(new Separator("delete_after"));
		menuManager.add(new Separator("all_before"));
		menuManager.add(new Separator("all"));
		menuManager.add(new Separator("all_after"));
		menuManager.add(new Separator("save_before"));
		menuManager.add(new Separator("save"));
		menuManager.add(new Separator("save_after"));

		GEFActionConstants.addStandardActionGroups(menuManager);

		appendAction(menuManager, ActionFactory.DELETE.getId(), "delete");

		appendAction(menuManager, OpenAction.ID, "save");
		appendAction(menuManager, ActionFactory.SAVE.getId(), "save");
		appendAction(menuManager, ActionFactory.SAVE_AS.getId(), "save");
	}

	protected ActionRegistry getActionRegistry() {
		return actionRegistry;
	}

	protected void appendAction(IMenuManager menu, String actionId, String group) {
		IAction action = getActionRegistry().getAction(actionId);
		if (null != action && action.isEnabled())
			menu.appendToGroup(group, action);
	}

}
