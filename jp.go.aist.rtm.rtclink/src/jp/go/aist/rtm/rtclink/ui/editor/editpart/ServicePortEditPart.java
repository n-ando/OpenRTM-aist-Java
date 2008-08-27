package jp.go.aist.rtm.rtclink.ui.editor.editpart;

import jp.go.aist.rtm.rtclink.manager.PreferenceManager;
import jp.go.aist.rtm.rtclink.model.component.ServicePort;
import jp.go.aist.rtm.rtclink.ui.editor.figure.ServicePortFigure;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.PlatformUI;

/**
 * InPortのEditPartクラス
 */
public class ServicePortEditPart extends PortEditPart {

	/**
	 * コンストラクタ
	 * 
	 * @param actionRegistry
	 *            ActionRegistry
	 */
	public ServicePortEditPart(ActionRegistry actionRegistry) {
		super(actionRegistry);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public ServicePort getModel() {
		return (ServicePort) super.getModel();
	}

	/**
	 * {@inheritDoc}
	 */
	public void notifyChanged(Notification notification) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				if (isActive()) {
					// if (ConnectorTarget.TARGET_CONNECTION.equals(evt
					// .getPropertyName())) {
					refresh();
					refreshTargetConnections();
					// }
				}
			}
		});
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected IFigure createFigure() {
		IFigure result = new ServicePortFigure(getModel());
		result.setLocation(new Point(0, 0));

		OutPortEditPart.supportAutoCreateConnectorToolMode(getViewer(), result);

		return result;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void refreshVisuals() {
		Color color = PreferenceManager.getInstance().getColor(
				PreferenceManager.COLOR_SERVICEPORT_NO_CONNECT);
		if (getModel().getPortProfile() != null
				&& getModel().getPortProfile().getConnectorProfiles() != null
				&& getModel().getPortProfile().getConnectorProfiles().size() >= 1) {
			color = PreferenceManager.getInstance().getColor(
					PreferenceManager.COLOR_SERVICEPORT_CONNECTED);
		}

		figure.setBackgroundColor(color);

		((GraphicalEditPart) getParent()).setLayoutConstraint(this,
				getFigure(), getFigure().getBounds());
	}

}
