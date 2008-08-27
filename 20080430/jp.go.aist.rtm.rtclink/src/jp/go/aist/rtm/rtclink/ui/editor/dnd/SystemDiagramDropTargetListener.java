package jp.go.aist.rtm.rtclink.ui.editor.dnd;

import jp.go.aist.rtm.rtclink.model.component.Component;
import jp.go.aist.rtm.rtclink.util.AdapterUtil;

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
		Component component = null;
		if (getCurrentEvent().data instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) getCurrentEvent().data;
			Object firstElement = selection.getFirstElement();

			if (AdapterUtil.getAdapter(firstElement, Component.class) != null) {
				component = (Component) AdapterUtil.getAdapter(firstElement,
						Component.class);
			}
		}

		CreateRequest result = new CreateRequest(); // nullObjectとして返す。
		if (component != null) {
			ComponentFactory factory = new ComponentFactory();
			factory.setComponent(component);

			// result = new CreateRequest();
			result.setFactory(factory);
		}

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
