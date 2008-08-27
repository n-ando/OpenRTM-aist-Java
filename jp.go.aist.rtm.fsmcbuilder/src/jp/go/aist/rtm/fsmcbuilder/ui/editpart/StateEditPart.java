package jp.go.aist.rtm.fsmcbuilder.ui.editpart;

import jp.go.aist.rtm.fsmcbuilder.model.ModelPackage;
import jp.go.aist.rtm.fsmcbuilder.model.State;
import jp.go.aist.rtm.fsmcbuilder.ui.editpart.direct.StateCellEditorLocator;
import jp.go.aist.rtm.fsmcbuilder.ui.editpart.direct.StateDirectEditManager;
import jp.go.aist.rtm.fsmcbuilder.ui.figures.StateFigure;
import jp.go.aist.rtm.fsmcbuilder.ui.policies.ContainerComponentEditPolicy;
import jp.go.aist.rtm.fsmcbuilder.ui.policies.FsmcDirectEditPolicy;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editpolicies.ResizableEditPolicy;
import org.eclipse.jface.viewers.TextCellEditor;

public class StateEditPart extends NodeElementEditPart {

	private static int s_instanceCount = 0;
	private StateFigure stateFigure;
	private StateDirectEditManager directManager = null;

	@Override
	public void performRequest(Request req) {
		if( req.getType().equals(RequestConstants.REQ_DIRECT_EDIT) ) {
			performDirectEdit();
			return;
	    }
		super.performRequest(req);
	}

	protected IFigure createFigure() {
		String stateName;

		stateName = ((State) getModel()).getName();
		stateName = "State_" + s_instanceCount;
		stateFigure = new StateFigure(new org.eclipse.draw2d.Label(stateName));
		State state = (State) getModel();
		state.setName(stateName);
		state.setEntry("");
		state.setDo("");
		state.setExit("");
		s_instanceCount++;

		return stateFigure;
	}

	protected void createEditPolicies() {
		super.createEditPolicies();
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new ContainerComponentEditPolicy());
		installEditPolicy("ResizePolicy", new ResizableEditPolicy());
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new FsmcDirectEditPolicy());
	}

	public void notifyChanged(Notification notification) {
		int id = notification.getFeatureID(State.class);
		if( id == ModelPackage.STATE__X ) {
			refreshVisuals();
		} else if( id == ModelPackage.STATE__Y ) {
			refreshVisuals();
		} else if( id == ModelPackage.STATE__HEIGHT ) {
			refreshVisuals();
		} else if( id == ModelPackage.STATE__WIDTH ) {
			refreshVisuals();
		} else if( id == ModelPackage.STATE__NAME || id == ModelPackage.STATE__ENTRY ||
					id == ModelPackage.STATE__DO || id == ModelPackage.STATE__EXIT) {
			refreshName(id);
		}
		super.notifyChanged(notification);
	}
	private void refreshName(int id) {
		StateFigure figure = (StateFigure) getFigure();
		State state = (State) getModel();
		if( id == ModelPackage.STATE__NAME ) {
			figure.setNameText(state.getName());
		} else if( id == ModelPackage.STATE__ENTRY ) {
			figure.setEntryActionText(state.getEntry());
		} else if( id == ModelPackage.STATE__DO ) {
			figure.setDoActionText(state.getDo());
		} else if( id == ModelPackage.STATE__EXIT ) {
			figure.setExitActionText(state.getExit());
		}
		refreshVisuals();
	}

	private void performDirectEdit() {
		if( directManager == null ) {
			directManager = new StateDirectEditManager(this, TextCellEditor.class,
				new StateCellEditorLocator(getFigure()));
		}
		directManager.show();
	}
}
