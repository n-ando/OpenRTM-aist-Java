package jp.go.aist.rtm.systemeditor.ui.editor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import jp.go.aist.rtm.systemeditor.ui.editor.editpart.ComponentEditPart;
import jp.go.aist.rtm.systemeditor.ui.editor.editpart.PortConnectorEditPart;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ui.actions.ActionBarContributor;
import org.eclipse.gef.ui.actions.DeleteRetargetAction;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;

/**
 * システムダイアグラムのActionBarContributorクラス
 */
public class OfflineSystemDiagramEditorContributor extends ActionBarContributor {

	private StatusLineDrawer statusLineDrawer;

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void buildActions() {
		 addRetargetAction(new DeleteRetargetAction());

	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void declareGlobalActionKeys() {
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void contributeToToolBar(IToolBarManager toolBarManager) {
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void contributeToStatusLine(
			final IStatusLineManager statusLineManager) {
		statusLineDrawer = new StatusLineDrawer(getPage(), statusLineManager);

		super.contributeToStatusLine(statusLineManager);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void dispose() {
		if (statusLineDrawer != null) {
			statusLineDrawer.dispose();
		}

		super.dispose();
	}

	/**
	 * ステータス行を表示するリスナクラス
	 * <p>
	 * ComponentEditPartに対して仕掛ける
	 */
	private final class StatusLineDrawer implements ISelectionListener,
			PropertyChangeListener {
		private final IStatusLineManager manager;

		private ComponentEditPart componentEditPart;
		private PortConnectorEditPart portConnectorEditPart;
		
		private IWorkbenchPage page;

		private StatusLineDrawer(IWorkbenchPage page, IStatusLineManager manager) {
			super();
			this.manager = manager;
			this.page = page;

			page.addSelectionListener(this);
		}

		/**
		 * {@inheritDoc}
		 */
		public void selectionChanged(IWorkbenchPart part, ISelection selection) {
			ComponentEditPart tempComponenEditPart = null;
			PortConnectorEditPart tempPortConnectorEditPart = null;
			if (selection instanceof IStructuredSelection) {
				IStructuredSelection structuredSelection = (IStructuredSelection) selection;

				Object firstElement = structuredSelection.getFirstElement();
				if (firstElement != null
						&& firstElement instanceof ComponentEditPart) {
					tempComponenEditPart = (ComponentEditPart) firstElement;
				}else if (firstElement != null
						&& firstElement instanceof PortConnectorEditPart) {
					tempPortConnectorEditPart = (PortConnectorEditPart) firstElement;
				}
			}

			if (componentEditPart != null) {
				componentEditPart.removePropertyChangeListener(this);
			}

			componentEditPart = tempComponenEditPart;

			if (componentEditPart != null) {
				componentEditPart.addPropertyChangeListener(this);
			}
			
			if (portConnectorEditPart != null) {
				portConnectorEditPart.removePropertyChangeListener(this);
			}

			portConnectorEditPart = tempPortConnectorEditPart;

			if (portConnectorEditPart != null) {
				portConnectorEditPart.addPropertyChangeListener(this);
			}

			drawMessage();
		}

		private void drawMessage() {
			page.getWorkbenchWindow().getShell().getDisplay().asyncExec(
					new Runnable() {
						public void run() {
							String message = "";
							if (componentEditPart != null) {
								IFigure figure = componentEditPart.getFigure();

								Rectangle bounds = figure.getBounds();
								message = "Pos: (" + bounds.x + "," + bounds.y
										+ ") Size: (" + bounds.width + ","
										+ bounds.height + ")";
							}

							manager.setMessage(message);
						}
					});
		}
		/**
		 * {@inheritDoc}
		 */
		public void propertyChange(PropertyChangeEvent evt) {
			drawMessage();
		}

		public void dispose() {
			if (componentEditPart != null) {
				componentEditPart.removePropertyChangeListener(this);
			}

			page.removeSelectionListener(this);
		}
	}

}
