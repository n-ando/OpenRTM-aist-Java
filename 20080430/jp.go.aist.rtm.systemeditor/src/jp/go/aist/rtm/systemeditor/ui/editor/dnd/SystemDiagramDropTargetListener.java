package jp.go.aist.rtm.systemeditor.ui.editor.dnd;

import jp.go.aist.rtm.nameserviceview.model.nameservice.NamingObjectNode;
import jp.go.aist.rtm.toolscommon.model.component.AbstractComponent;
import jp.go.aist.rtm.toolscommon.model.component.Component;
import jp.go.aist.rtm.toolscommon.model.component.impl.ComponentImpl;
import jp.go.aist.rtm.toolscommon.util.AdapterUtil;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.dnd.AbstractTransferDropTargetListener;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.views.navigator.LocalSelectionTransfer;

/**
 * システムダイアグラムのDropTargetListener
 */
public class SystemDiagramDropTargetListener extends
		AbstractTransferDropTargetListener {

	/**
	 * コンストラクタ
	 * 
	 * @param viewer
	 *            EditPartViewer
	 */
	public SystemDiagramDropTargetListener(EditPartViewer viewer) {
		super(viewer, LocalSelectionTransfer.getInstance());
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void updateTargetRequest() {
		((CreateRequest) getTargetRequest()).setLocation(getDropLocation());
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected Request createTargetRequest() {
		AbstractComponent component = null;
		if (getCurrentEvent().data instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) getCurrentEvent().data;
			Object firstElement = selection.getFirstElement();

			if (AdapterUtil.getAdapter(firstElement, AbstractComponent.class) != null) {
				component = (AbstractComponent) AdapterUtil.getAdapter(firstElement,
						AbstractComponent.class);
			}
		}

		CreateRequest result = new CreateRequest(); // nullObjectとして返す。
		ComponentFactory factory = new ComponentFactory();
		if (component != null) {
			if( component instanceof Component) {
				ComponentImpl.synchronizeLocalAttribute((Component) component, null);
				ComponentImpl.synchronizeLocalReference((Component) component);
				
			}
			if (component.getPathId() == null
					&& component.eContainer() instanceof NamingObjectNode) {
				component.setPathId(((NamingObjectNode) component.eContainer())
							.getNameServiceReference().getPathId());
			}
			factory.setComponent(component);
		}
		result.setFactory(factory);
		return result;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void handleDrop() {
		IStructuredSelection selection = (IStructuredSelection) LocalSelectionTransfer
				.getInstance().getSelection();
		getCurrentEvent().data = selection;
		super.handleDrop();
	}

}
