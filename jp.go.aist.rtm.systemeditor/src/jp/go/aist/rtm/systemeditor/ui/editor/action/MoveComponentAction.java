package jp.go.aist.rtm.systemeditor.ui.editor.action;

import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

/**
 * RTCを移動するアクション
 */
public class MoveComponentAction extends SelectionAction {

	/**
	 * 大きく移動する場合のインターバル
	 */
	public static final int MOVE_L_SIZE = 10;

	/**
	 * 小さく上に移動するアクションのID：このIDがplugin.xmlに指定されなければならない。
	 */
	public static final String MOVE_UP_ACTION_ID = MoveComponentAction.class
			.getName()
			+ "_UP";

	/**
	 * 小さく下に移動するアクションのID：このIDがplugin.xmlに指定されなければならない。
	 */
	public static final String MOVE_DOWN_ACTION_ID = MoveComponentAction.class
			.getName()
			+ "_DOWN";

	/**
	 * 小さく右に移動するアクションのID：このIDがplugin.xmlに指定されなければならない。
	 */
	public static final String MOVE_RIGHT_ACTION_ID = MoveComponentAction.class
			.getName()
			+ "_RIGHT";

	/**
	 * 小さく左に移動するアクションのID：このIDがplugin.xmlに指定されなければならない。
	 */
	public static final String MOVE_LEFT_ACTION_ID = MoveComponentAction.class
			.getName()
			+ "_LEFT";

	/**
	 * 大きく上に移動するアクションのID：このIDがplugin.xmlに指定されなければならない。
	 */
	public static final String MOVE_UP_L_ACTION_ID = MoveComponentAction.class
			.getName()
			+ "_UP_L";

	/**
	 * 大きく下に移動するアクションのID：このIDがplugin.xmlに指定されなければならない。
	 */
	public static final String MOVE_DOWN_L_ACTION_ID = MoveComponentAction.class
			.getName()
			+ "_DOWN_L";

	/**
	 * 大きく右に移動するアクションのID：このIDがplugin.xmlに指定されなければならない。
	 */
	public static final String MOVE_RIGHT_L_ACTION_ID = MoveComponentAction.class
			.getName()
			+ "_RIGHT_L";

	/**
	 * 大きく左に移動するアクションのID：このIDがplugin.xmlに指定されなければならない。
	 */
	public static final String MOVE_LEFT_L_ACTION_ID = MoveComponentAction.class
			.getName()
			+ "_LEFT_L";

	/**
	 * コンストラクタ
	 * @param part IWorkbenchPart
	 * @param id　アクションのID
	 */
	public MoveComponentAction(IWorkbenchPart part, String id) {
		super(part);
		setId(id);
		setText("Move");
	}

	/**
	 * {@inheritDoc}
	 */
	protected boolean calculateEnabled() {
		Command cmd = createMoveCommand(getSelectedObjects());
		if (cmd == null)
			return false;
		return cmd.canExecute();
	}

	/**
	 * 移動のコマンドを作成する
	 * @param selectedEditParts 対象のEditPart
	 * @return 作成したコマンド
	 */
	protected Command createMoveCommand(List selectedEditParts) {
		if (selectedEditParts.isEmpty())
			return null;
		if (!(selectedEditParts.get(0) instanceof EditPart))
			return null;

		ChangeBoundsRequest request = new ChangeBoundsRequest();
		request.setType(RequestConstants.REQ_MOVE);

		Point moveDeltaPoint = new Point();
		if (MOVE_UP_ACTION_ID.equals(getId())) {
			moveDeltaPoint = new Point(0, -1);
		} else if (MOVE_DOWN_ACTION_ID.equals(getId())) {
			moveDeltaPoint = new Point(0, 1);
		} else if (MOVE_RIGHT_ACTION_ID.equals(getId())) {
			moveDeltaPoint = new Point(1, 0);
		} else if (MOVE_LEFT_ACTION_ID.equals(getId())) {
			moveDeltaPoint = new Point(-1, 0);
		} else if (MOVE_UP_L_ACTION_ID.equals(getId())) {
			moveDeltaPoint = new Point(0, -MOVE_L_SIZE);
		} else if (MOVE_DOWN_L_ACTION_ID.equals(getId())) {
			moveDeltaPoint = new Point(0, MOVE_L_SIZE);
		} else if (MOVE_RIGHT_L_ACTION_ID.equals(getId())) {
			moveDeltaPoint = new Point(MOVE_L_SIZE, 0);
		} else if (MOVE_LEFT_L_ACTION_ID.equals(getId())) {
			moveDeltaPoint = new Point(-MOVE_L_SIZE, 0);
		}

		request.setMoveDelta(moveDeltaPoint);

		CompoundCommand compoundCmd = new CompoundCommand();
		for (int i = 0; i < selectedEditParts.size(); i++) {
			EditPart editPart = (EditPart) selectedEditParts.get(i);
			if (editPart.understandsRequest(request)) {
				Command cmd = editPart.getCommand(request);
				if (cmd != null)
					compoundCmd.add(cmd);
			}
		}

		return compoundCmd;
	}

	/**
	 * {@inheritDoc}
	 */
	public void run() {
		execute(createMoveCommand(getSelectedObjects()));
	}

	/**
	 * {@inheritDoc}
	 */
	protected void init() {
		super.init();
		setEnabled(false);
	}

}
