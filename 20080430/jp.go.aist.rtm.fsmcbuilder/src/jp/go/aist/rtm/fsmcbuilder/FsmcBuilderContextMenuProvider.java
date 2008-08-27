package jp.go.aist.rtm.fsmcbuilder;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.actions.ActionFactory;

public class FsmcBuilderContextMenuProvider extends ContextMenuProvider {

	private ActionRegistry registry;

	public FsmcBuilderContextMenuProvider(EditPartViewer viewer, ActionRegistry registry) {
		super(viewer);
		this.registry = registry;
	}

	public void buildContextMenu(IMenuManager menu) {
		GEFActionConstants.addStandardActionGroups(menu);
		
		menu.appendToGroup(GEFActionConstants.GROUP_UNDO, registry.getAction(ActionFactory.UNDO.getId()));
		menu.appendToGroup(GEFActionConstants.GROUP_UNDO, registry.getAction(ActionFactory.REDO.getId()));
		menu.appendToGroup(GEFActionConstants.GROUP_EDIT, registry.getAction(ActionFactory.DELETE.getId()));
	}

}
