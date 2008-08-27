package jp.go.aist.rtm.systemeditor.ui.editor.editpart;

import jp.go.aist.rtm.systemeditor.manager.SystemEditorPreferenceManager;
import jp.go.aist.rtm.systemeditor.ui.editor.figure.ServicePortFigure;
import jp.go.aist.rtm.toolscommon.model.component.AbstractComponent;
import jp.go.aist.rtm.toolscommon.model.component.Connector;
import jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile;
import jp.go.aist.rtm.toolscommon.model.component.Port;
import jp.go.aist.rtm.toolscommon.model.component.ServicePort;

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
		Color color = SystemEditorPreferenceManager.getInstance().getColor(
				SystemEditorPreferenceManager.COLOR_SERVICEPORT_NO_CONNECT);
//		if (getModel().getPortProfile() != null
//				&& getModel().getPortProfile().getConnectorProfiles() != null
//				&& getModel().getPortProfile().getConnectorProfiles().size() >= 1) {
//
//			AbstractComponent c = (AbstractComponent) getModel().eContainer();
//			if (c.getCompositeComponent() == null) {
//
//				color = SystemEditorPreferenceManager.getInstance().getColor(
//						SystemEditorPreferenceManager.COLOR_DATAPORT_CONNECTED);
//
//			} else {
//
//				AbstractComponent root = c;
//				for (; root.getCompositeComponent() != null;) {
//					root = root.getCompositeComponent();
//				}
//
//				for (Object e : getModel().getPortProfile()
//						.getConnectorProfiles()) {
//					ConnectorProfile cp = (ConnectorProfile) e;
//					Connector con = (Connector) cp.eContainer();
//					Port p = null;
//					if (con.getTarget() != getModel()) {
//						p = (Port) con.getTarget();
//					} else if (con.getSource() != getModel()) {
//						p = (Port) con.getSource();
//					}
//					AbstractComponent dest = (AbstractComponent) p.eContainer();
//					boolean contents = false;
//					for (Object ee : root.getAllComponents()) {
//						if (ee == dest) {
//							contents = true;
//						}
//					}
//					if (contents == false) {
//						color = SystemEditorPreferenceManager
//								.getInstance()
//								.getColor(
//										SystemEditorPreferenceManager.COLOR_DATAPORT_CONNECTED);
//						break;
//					}
//				}
//			}
//		}
		if (isConnected()) {
			color = SystemEditorPreferenceManager.getInstance().getColor(
					SystemEditorPreferenceManager.COLOR_DATAPORT_CONNECTED);
		}

		figure.setBackgroundColor(color);

		((GraphicalEditPart) getParent()).setLayoutConstraint(this,
				getFigure(), getFigure().getBounds());
	}

}
