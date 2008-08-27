package jp.go.aist.rtm.systemeditor.ui.editor.editpart.router;

import org.eclipse.draw2d.MidpointLocator;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.SharedCursors;
import org.eclipse.gef.handles.BendpointHandle;
import org.eclipse.gef.tools.ConnectionBendpointTracker;

/**
 * ライン移動の際のハンドル
 */
public class LineMoveHandle extends BendpointHandle {

	{
		setCursor(SharedCursors.SIZEALL);
		setPreferredSize(new Dimension(DEFAULT_HANDLE_SIZE + 1,
				DEFAULT_HANDLE_SIZE + 1));
	}

	/**
	 * コンストラクタ
	 * 
	 * @param owner
	 *            オーナーのConnectionEditPart
	 * @param index
	 *            変更場所のインデックス
	 */
	public LineMoveHandle(ConnectionEditPart owner, int index) {
		setOwner(owner);
		setIndex(index);
		setLocator(new MidpointLocator(getConnection(), index));
	}

	/**
	 * {@inheritDoc}
	 */
	protected DragTracker createDragTracker() {
		ConnectionBendpointTracker tracker;
		tracker = new ConnectionBendpointTracker(
				(ConnectionEditPart) getOwner(), getIndex());
		tracker.setType(RequestConstants.REQ_MOVE_BENDPOINT);
		tracker.setDefaultCursor(getCursor());
		return tracker;
	}

}
