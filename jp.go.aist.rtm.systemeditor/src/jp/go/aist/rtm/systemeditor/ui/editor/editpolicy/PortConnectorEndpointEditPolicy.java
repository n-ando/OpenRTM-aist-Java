package jp.go.aist.rtm.systemeditor.ui.editor.editpolicy;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;
import org.eclipse.gef.requests.ReconnectRequest;

/**
 * ポートのEditPolicyクラス
 */
public class PortConnectorEndpointEditPolicy extends
		ConnectionEndpointEditPolicy {

	private static final Map NULL_CONSTRAINT = Collections.EMPTY_MAP;

	private Map originalConstraint;

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void showConnectionMoveFeedback(ReconnectRequest request) {
		if (originalConstraint == null) {
			saveOriginalConstraint();
		}

		TreeMap newConstraint = new TreeMap();
		getConnection().setRoutingConstraint(newConstraint);

		super.showConnectionMoveFeedback(request);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void eraseConnectionMoveFeedback(ReconnectRequest request) {
		restoreOriginalConstraint();

		super.eraseConnectionMoveFeedback(request);
	}

	protected void restoreOriginalConstraint() {
		if (originalConstraint != null) {
			if (originalConstraint == NULL_CONSTRAINT)
				getConnection().setRoutingConstraint(null);
			else
				getConnection().setRoutingConstraint(originalConstraint);
		}
	}

	protected void saveOriginalConstraint() {
		originalConstraint = (Map) getConnection().getRoutingConstraint();
		if (originalConstraint == null)
			originalConstraint = NULL_CONSTRAINT;
		getConnection().setRoutingConstraint(
				new TreeMap<Integer, Point>(originalConstraint));
	}

}
