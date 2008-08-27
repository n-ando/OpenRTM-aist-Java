package jp.go.aist.rtm.systemeditor.ui.editor.editpolicy;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jp.go.aist.rtm.systemeditor.ui.editor.command.MoveLineCommand;
import jp.go.aist.rtm.systemeditor.ui.editor.editpart.router.LineMoveHandle;

import org.eclipse.draw2d.AbsoluteBendpoint;
import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.SelectionHandlesEditPolicy;
import org.eclipse.gef.requests.BendpointRequest;

/**
 * コネクタのベンドポイント編集のEditPolicyクラス
 */
public class PortConnectorBendpointEditPolicy extends
		SelectionHandlesEditPolicy implements PropertyChangeListener {

	private static final Map NULL_CONSTRAINT = Collections.EMPTY_MAP;

	private Map<Integer, Point> originalConstraint;

	private boolean isDeleting = false;

	/**
	 * {@inheritDoc}
	 */
	public void activate() {
		super.activate();
		getConnection().addPropertyChangeListener(Connection.PROPERTY_POINTS,
				this);
	}

	/**
	 * {@inheritDoc}
	 */
	public void deactivate() {
		getConnection().removePropertyChangeListener(
				Connection.PROPERTY_POINTS, this);
		super.deactivate();
	}

	/**
	 * {@inheritDoc}
	 */
	protected void eraseConnectionFeedback(BendpointRequest request) {
		restoreOriginalConstraint();
		originalConstraint = null;
	}

	/**
	 * {@inheritDoc}
	 */
	public void eraseSourceFeedback(Request request) {
		if (REQ_MOVE_BENDPOINT.equals(request.getType())
				|| REQ_CREATE_BENDPOINT.equals(request.getType()))
			eraseConnectionFeedback((BendpointRequest) request);
	}

	/**
	 * {@inheritDoc}
	 */
	public Command getCommand(Request request) {
		if (REQ_MOVE_BENDPOINT.equals(request.getType())) {
			if (isDeleting)
				return getDeleteBendpointCommand((BendpointRequest) request);
			return getMoveBendpointCommand((BendpointRequest) request);
		}
		if (REQ_CREATE_BENDPOINT.equals(request.getType()))
			return getCreateBendpointCommand((BendpointRequest) request);
		return null;
	}

	protected Connection getConnection() {
		return (Connection) ((ConnectionEditPart) getHost()).getFigure();
	}

	/**
	 * {@inheritDoc}
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if (getHost().getSelected() != EditPart.SELECTED_NONE)
			addSelectionHandles();
	}

	/**
	 * {@inheritDoc}
	 */
	protected void restoreOriginalConstraint() {
		if (originalConstraint != null) {
			if (originalConstraint == NULL_CONSTRAINT)
				getConnection().setRoutingConstraint(null);
			else
				getConnection().setRoutingConstraint(originalConstraint);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	protected void saveOriginalConstraint() {
		originalConstraint = (Map) getConnection().getRoutingConstraint();
		if (originalConstraint == null)
			originalConstraint = NULL_CONSTRAINT;
		getConnection().setRoutingConstraint(
				new TreeMap<Integer, Point>(originalConstraint));
	}

	/**
	 * {@inheritDoc}
	 */
	protected void showCreateBendpointFeedback(BendpointRequest request) {
		Point p = new Point(request.getLocation());
		List constraint;
		getConnection().translateToRelative(p);
		Bendpoint bp = new AbsoluteBendpoint(p);
		if (originalConstraint == null) {
			saveOriginalConstraint();
			constraint = (List) getConnection().getRoutingConstraint();
			constraint.add(request.getIndex(), bp);
		} else {
			constraint = (List) getConnection().getRoutingConstraint();
		}
		constraint.set(request.getIndex(), bp);
		getConnection().setRoutingConstraint(constraint);
	}

	/**
	 * {@inheritDoc}
	 */
	protected void showDeleteBendpointFeedback(BendpointRequest request) {
		if (originalConstraint == null) {
			saveOriginalConstraint();
			List constraint = (List) getConnection().getRoutingConstraint();
			constraint.remove(request.getIndex());
			getConnection().setRoutingConstraint(constraint);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	protected void showMoveBendpointFeedback(BendpointRequest request) {
		Point p = new Point(request.getLocation());
		if (isDeleting) {
			isDeleting = false;
			eraseSourceFeedback(request);
		}
		if (originalConstraint == null)
			saveOriginalConstraint();
		Map constraint = (Map) getConnection().getRoutingConstraint();
		getConnection().translateToRelative(p);

		constraint.put(request.getIndex(), p);
		getConnection().setRoutingConstraint(constraint);
	}

	/**
	 * {@inheritDoc}
	 */
	public void showSourceFeedback(Request request) {
		if (REQ_MOVE_BENDPOINT.equals(request.getType()))
			showMoveBendpointFeedback((BendpointRequest) request);
		else if (REQ_CREATE_BENDPOINT.equals(request.getType()))
			showCreateBendpointFeedback((BendpointRequest) request);
	}

	protected Command getCreateBendpointCommand(BendpointRequest request) {
		return null;
	}

	protected Command getDeleteBendpointCommand(BendpointRequest request) {
		return null;
	}

	protected Command getMoveBendpointCommand(BendpointRequest request) {
		MoveLineCommand result = new MoveLineCommand();
		result
				.setModel((jp.go.aist.rtm.toolscommon.model.component.Connector) getHost()
						.getModel());
		Point location = request.getLocation();
		getConnection().translateToRelative(location);
		
		result.setLocation(location);
		result.setIndex(request.getIndex());

		return result;
	}

	protected List createSelectionHandles() {
		List<LineMoveHandle> result = new ArrayList<LineMoveHandle>();
		ConnectionEditPart connEP = (ConnectionEditPart) getHost();
		PointList points = getConnection().getPoints();

		if (points.size() > 2) {
			for (int i = 1; i < points.size() - 2; i++) {
				result.add(new LineMoveHandle(connEP, i));
			}
		}

		return result;
	}

}
