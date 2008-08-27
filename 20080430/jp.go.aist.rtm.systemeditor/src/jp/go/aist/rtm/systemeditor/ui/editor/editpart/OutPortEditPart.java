package jp.go.aist.rtm.systemeditor.ui.editor.editpart;

import jp.go.aist.rtm.systemeditor.manager.SystemEditorPreferenceManager;
import jp.go.aist.rtm.systemeditor.ui.editor.figure.OutPortFigure;
import jp.go.aist.rtm.toolscommon.model.component.AbstractComponent;
import jp.go.aist.rtm.toolscommon.model.component.Connector;
import jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile;
import jp.go.aist.rtm.toolscommon.model.component.OutPort;
import jp.go.aist.rtm.toolscommon.model.component.Port;
import jp.go.aist.rtm.toolscommon.model.component.impl.AbstractPortConnectorImpl;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.requests.SimpleFactory;
import org.eclipse.gef.tools.ConnectionDragCreationTool;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.PlatformUI;

/**
 * OutPortのEditPartクラス
 */
public class OutPortEditPart extends PortEditPart {

	private Notification notification;

	/**
	 * コンストラクタ
	 * 
	 * @param actionRegistry
	 *            ActionRegistry
	 */
	public OutPortEditPart(ActionRegistry actionRegistry) {
		super(actionRegistry);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public OutPort getModel() {
		return (OutPort) super.getModel();
	}

	/**
	 * {@inheritDoc}
	 */
	public void notifyChanged(Notification notification) {
		this.notification = notification;
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				if (isActive()) {
					// if (ConnectorSource.SOURCE_CONNECTION.equals(evt
					// .getPropertyName())) {
					refresh();
					refreshVisuals();
					refreshSourceConnections();
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
		Shape result = new OutPortFigure(getModel());
		result.setLocation(new Point(0, 0));

		supportAutoCreateConnectorToolMode(getViewer(), result);

		PolygonDecoration deco = new PolygonDecoration();
		deco.add(result);

		return result;
	}

	/**
	 * ポート上で、自動的にコネクタを作成するモードに変更する機能を付加するメソッド
	 */
	public static void supportAutoCreateConnectorToolMode(
			EditPartViewer viewer, IFigure figure) {
		final EditDomain domain = viewer.getEditDomain();
		final AutoConnectorCreationTool connectionCreationTool = new AutoConnectorCreationTool();

		figure.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent me) {
			}

			public void mouseEntered(MouseEvent me) {
				connectionCreationTool.setFactory(new SimpleFactory(
						AbstractPortConnectorImpl.class));
				domain.setActiveTool(connectionCreationTool);
			}

			public void mouseExited(MouseEvent me) {
				if (domain.getActiveTool() == connectionCreationTool
						&& connectionCreationTool.isStartedState() == false) {
					domain.setActiveTool(domain.getDefaultTool());
				}
			}

			public void mouseHover(MouseEvent me) {
			}

			public void mouseMoved(MouseEvent me) {
			}
		});
	}

	/**
	 * コネクタを作成するツール
	 */
	public static class AutoConnectorCreationTool extends
			ConnectionDragCreationTool {
		@Override
		protected void handleFinished() {
			getDomain().setActiveTool(getDomain().getDefaultTool());
			super.handleFinished();
		}

		public boolean isStartedState() {
			return super.isInState(STATE_CONNECTION_STARTED);
		}
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void refreshVisuals() {
		Color color = SystemEditorPreferenceManager.getInstance().getColor(
				SystemEditorPreferenceManager.COLOR_DATAPORT_NO_CONNECT);
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

		figure.setToolTip(InPortEditPart.getDataPortToolTip(getModel()
				.getPortProfile()));

		((GraphicalEditPart) getParent()).setLayoutConstraint(this,
				getFigure(), getFigure().getBounds());
	}

}
