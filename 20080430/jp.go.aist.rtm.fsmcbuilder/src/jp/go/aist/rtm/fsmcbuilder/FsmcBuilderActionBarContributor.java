package jp.go.aist.rtm.fsmcbuilder;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.gef.ui.actions.ActionBarContributor;
import org.eclipse.gef.ui.actions.AlignmentRetargetAction;
import org.eclipse.gef.ui.actions.DeleteRetargetAction;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.RedoRetargetAction;
import org.eclipse.gef.ui.actions.UndoRetargetAction;
import org.eclipse.gef.ui.actions.ZoomComboContributionItem;
import org.eclipse.gef.ui.actions.ZoomInRetargetAction;
import org.eclipse.gef.ui.actions.ZoomOutRetargetAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.actions.ActionFactory;

public class FsmcBuilderActionBarContributor extends ActionBarContributor {

	protected void buildActions() {
		addRetargetAction(new DeleteRetargetAction());
		addRetargetAction(new UndoRetargetAction());
		addRetargetAction(new RedoRetargetAction());
		//
		addRetargetAction(new ZoomInRetargetAction());
	    addRetargetAction(new ZoomOutRetargetAction());
	    //
	    addRetargetAction(new AlignmentRetargetAction(PositionConstants.LEFT));
	    addRetargetAction(new AlignmentRetargetAction(PositionConstants.CENTER));
	    addRetargetAction(new AlignmentRetargetAction(PositionConstants.RIGHT));
	    addRetargetAction(new AlignmentRetargetAction(PositionConstants.TOP));
	    addRetargetAction(new AlignmentRetargetAction(PositionConstants.MIDDLE));
	    addRetargetAction(new AlignmentRetargetAction(PositionConstants.BOTTOM));
	    //
	}

	public void contributeToToolBar(IToolBarManager toolBarManager) {
		toolBarManager.add(getAction(ActionFactory.DELETE.getId()));
		toolBarManager.add(getAction(ActionFactory.UNDO.getId()));
		toolBarManager.add(getAction(ActionFactory.REDO.getId()));
		//
		toolBarManager.add(new Separator());
	    toolBarManager.add(getActionRegistry().getAction(GEFActionConstants.ALIGN_LEFT));
	    toolBarManager.add(getActionRegistry().getAction(GEFActionConstants.ALIGN_CENTER));
	    toolBarManager.add(getActionRegistry().getAction(GEFActionConstants.ALIGN_RIGHT));
	    toolBarManager.add(new Separator());
	    toolBarManager.add(getActionRegistry().getAction(GEFActionConstants.ALIGN_TOP));
	    toolBarManager.add(getActionRegistry().getAction(GEFActionConstants.ALIGN_MIDDLE));
	    toolBarManager.add(getActionRegistry().getAction(GEFActionConstants.ALIGN_BOTTOM));
		//
		toolBarManager.add(new Separator());
	    toolBarManager.add(getActionRegistry().getAction(GEFActionConstants.ZOOM_IN));
	    toolBarManager.add(getActionRegistry().getAction(GEFActionConstants.ZOOM_OUT));
	    toolBarManager.add(new ZoomComboContributionItem(getPage()));
	}

	protected void declareGlobalActionKeys() {
	}
}
