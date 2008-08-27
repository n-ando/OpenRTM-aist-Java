package jp.go.aist.rtm.systemeditor.ui.editor;

import jp.go.aist.rtm.systemeditor.ui.editor.action.OpenAndRestoreAction;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.jface.action.IMenuManager;

/**
 * システムダイアグラムのContextMenuProviderクラス
 */
public class SystemDiagramContextMenuProvider extends
		AbstractSystemDiagramContextMenuProvider {

	public SystemDiagramContextMenuProvider(EditPartViewer viewer,
			ActionRegistry actionRegistry) {

		super(viewer, actionRegistry);
	}

	public void buildContextMenu(IMenuManager menuManager) {

		super.buildContextMenu(menuManager);

		appendAction(menuManager, OpenAndRestoreAction.ID, "save");
	}

}
