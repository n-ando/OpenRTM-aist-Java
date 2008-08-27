package jp.go.aist.rtm.fsmcbuilder.ui.editpart;

import java.util.ArrayList;
import java.util.List;

import jp.go.aist.rtm.fsmcbuilder.model.ModelPackage;
import jp.go.aist.rtm.fsmcbuilder.model.Transition;
import jp.go.aist.rtm.fsmcbuilder.ui.figures.TransitionFigure;
import jp.go.aist.rtm.fsmcbuilder.ui.policies.FsmcBendpointEditPolicy;
import jp.go.aist.rtm.fsmcbuilder.ui.policies.TransitionEditPolicy;

import org.eclipse.draw2d.AbsoluteBendpoint;
import org.eclipse.draw2d.BendpointConnectionRouter;
import org.eclipse.draw2d.BendpointLocator;
import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MidpointLocator;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RelativeBendpoint;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;

public class TransitionEditPart extends AbstractConnectionEditPart {
	private Label actionLabel;
	private int figurePosition = 1;

	@Override
	protected IFigure createFigure() {
		TransitionFigure connection = new TransitionFigure();
		connection.setConnectionRouter(new BendpointConnectionRouter());
//		connection.setConnectionRouter(new ManhattanConnectionRouter());
		PolylineDecoration decoration = new PolylineDecoration();
		decoration.setScale(10, 5);
		connection.setTargetDecoration(decoration);
		//
		Transition transition = (Transition) getModel();
		transition.setEffect("");
		transition.setGuard("");
		//
		MidpointLocator actionLocator = new MidpointLocator(connection,0);
		actionLocator.setRelativePosition(PositionConstants.NORTH);
		actionLabel = new Label();
		connection.add(actionLabel,actionLocator);
		
		return connection;
	}

	private ConnectionAdapter adapter = new ConnectionAdapter();
	
	public void activate() {
		super.activate();
		EObject model = (EObject) getModel();
		model.eAdapters().add(adapter);
	}

	public void deactivate() {
		super.deactivate();
		EObject model = (EObject) getModel();
		model.eAdapters().remove(adapter);
	}

	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.CONNECTION_ROLE, new TransitionEditPolicy());
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new ConnectionEndpointEditPolicy());
		installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE, new FsmcBendpointEditPolicy());
	}

	private class ConnectionAdapter implements Adapter {
		
		private Notifier target;

		public Notifier getTarget() {
			return target;
		}

		public boolean isAdapterForType(Object type) {
			return type.equals(getModel().getClass());
		}

		public void notifyChanged(Notification notification) {
			TransitionEditPart.this.notifyChanged(notification);
		}

		public void setTarget(Notifier target) {
			this.target = target;
		}
	}

	public void notifyChanged(Notification notification) {
		int id = notification.getFeatureID(Transition.class);
		if( id == ModelPackage.TRANSITION__BENDPOINTS ) {
			refreshBendpoints();
		} else if( id == ModelPackage.TRANSITION__GUARD || id == ModelPackage.TRANSITION__EFFECT ) {
			Transition trans = (Transition) getModel();
			String effect = trans.getEffect();
			String guard = trans.getGuard();
			StringBuffer actionText = new StringBuffer();
			if( guard != null && guard.length() > 0 ) {
				actionText.append("[" + guard + "]");
			}
			if( effect != null && effect.length() > 0 ) {
				if( actionText.length() > 0 ) actionText.append("/");
				actionText.append(effect);
			}
			actionLabel.setText(actionText.toString());
		} else if( id == ModelPackage.TRANSITION__TARGET ) {
			Transition trans = (Transition) getModel();
			if( trans.getSource() == trans.getTarget() ) { 
				createSelfTransition();

			}
		}
	}

	public void createSelfTransition() {
		figurePosition *= -1;
		this.removeEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE);
		TransitionFigure transFig = (TransitionFigure)getFigure();
		ArrayList list = createSelfTransFig(transFig, figurePosition);
		transFig.setRoutingConstraint(list);
		modifyLabelPosition(list);
	}

	private ArrayList createSelfTransFig(TransitionFigure transFig, int direction) {
		ArrayList<RelativeBendpoint> bendPointsList = new ArrayList<RelativeBendpoint>();
		int posY1 = 55 * direction;
		int posY2 = 80 * direction;
		
		RelativeBendpoint rbp = new RelativeBendpoint(transFig);
		Dimension dimension = new Dimension(-30, posY1);
		rbp.setRelativeDimensions(dimension, dimension);
		bendPointsList.add(rbp);
		//
		rbp = new RelativeBendpoint(transFig);
		dimension = new Dimension(-15, posY2);
		rbp.setRelativeDimensions(dimension,dimension);
		bendPointsList.add(rbp);
		//
		rbp = new RelativeBendpoint(transFig);
		dimension = new Dimension(15, posY2);
		rbp.setRelativeDimensions(dimension,dimension);
		bendPointsList.add(rbp);
		//
		rbp = new RelativeBendpoint(transFig);
		dimension = new Dimension(30, posY1);
		rbp.setRelativeDimensions(dimension,dimension);
		bendPointsList.add(rbp);

		return bendPointsList;
	}

	protected void refreshBendpoints() {
		List bendpoints = ((Transition) getModel()).getBendpoints();
	    List<AbsoluteBendpoint> constraint = new ArrayList<AbsoluteBendpoint>();

	    for( int intIdx = 0; intIdx < bendpoints.size(); intIdx++ ) {
	      constraint.add(new AbsoluteBendpoint((Point) bendpoints.get(intIdx)));
	    }
	    getConnectionFigure().setRoutingConstraint(constraint);
	    //
		modifyLabelPosition(bendpoints);
	}

	private void modifyLabelPosition(List bendpoints) {
		TransitionFigure transFig = (TransitionFigure)getFigure();
	    ConnectionLocator actionLocator;
	    if( bendpoints.size() > 1 ) {
			actionLocator = new BendpointLocator(transFig, bendpoints.size()/2);
			if( figurePosition > 0) {
				actionLocator.setRelativePosition(PositionConstants.SOUTH);
			} else {
				actionLocator.setRelativePosition(PositionConstants.NORTH);
			}
	    } else {
			actionLocator = new MidpointLocator(transFig,0);
			actionLocator.setRelativePosition(PositionConstants.NORTH);
	    }
		transFig.add(actionLabel,actionLocator);
	}

	protected void refreshVisuals() {
		refreshBendpoints();
	}

}
