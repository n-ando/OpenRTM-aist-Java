package jp.go.aist.rtm.rtclink.ui.editor.editpart;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import jp.go.aist.rtm.rtclink.manager.PreferenceManager;
import jp.go.aist.rtm.rtclink.model.component.Port;
import jp.go.aist.rtm.rtclink.ui.editor.editpolicy.PortGraphicalNodeEditPolicy;
import jp.go.aist.rtm.rtclink.ui.editor.figure.PortAnchor;
import jp.go.aist.rtm.rtclink.ui.editor.figure.PortFigure;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.ui.actions.ActionRegistry;

/**
 * ポートのEditPartクラス
 */
public abstract class PortEditPart extends AbstractEditPart implements
		NodeEditPart {

	/**
	 * コンストラクタ
	 * 
	 * @param actionRegistry
	 *            ActionRegistry
	 */
	public PortEditPart(ActionRegistry actionRegistry) {
		super(actionRegistry);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
				new PortGraphicalNodeEditPolicy());
	}

	/**
	 * {@inheritDoc}
	 */
	public ConnectionAnchor getSourceConnectionAnchor(
			ConnectionEditPart connection) {
		return new PortAnchor(getFigure());
	}

	/**
	 * {@inheritDoc}
	 */
	public ConnectionAnchor getTargetConnectionAnchor(
			ConnectionEditPart connection) {
		return new PortAnchor(getFigure());
	}

	/**
	 * {@inheritDoc}
	 */
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return new PortAnchor(getFigure());
	}

	/**
	 * {@inheritDoc}
	 */
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		return new PortAnchor(getFigure());
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public PortFigure getFigure() {
		return (PortFigure) super.getFigure();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected List getModelTargetConnections() {
		return getModel().getTargetConnectors();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected List getModelSourceConnections() {
		return getModel().getSourceConnectors();
	}

	@Override
	public Port getModel() {
		return (Port) super.getModel();
	}

	/**
	 * 設定マネージャを監視するリスナ
	 */
	PropertyChangeListener preferenceChangeListener = new PropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent evt) {
			refreshVisuals();
		}
	};

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void activate() {
		super.activate();

		PreferenceManager.getInstance().addPropertyChangeListener(
				preferenceChangeListener);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void deactivate() {
		super.deactivate();

		PreferenceManager.getInstance().removePropertyChangeListener(
				preferenceChangeListener);
	}

}
