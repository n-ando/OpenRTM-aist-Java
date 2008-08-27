package jp.go.aist.rtm.systemeditor.ui.editor.action;

import java.util.HashMap;
import java.util.List;

import jp.go.aist.rtm.systemeditor.ui.editor.editpolicy.EditPolicyConstraint;
import jp.go.aist.rtm.toolscommon.model.component.Component;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

/**
 * RTCの向きを変更するアクション
 */
public class ChangeComponentDirectionAction extends SelectionAction {

	/**
	 * 水平方向への向きを指示する際に使用されるID。この値が、Plugin.xmlに指定されなければならない。
	 */
	public static final String HORIZON_DIRECTION_ACTION_ID = ChangeComponentDirectionAction.class
			.getName()
			+ "_HORIZON";

	/**
	 * 垂直方向への向きを指示する際に使用されるID。この値が、Plugin.xmlに指定されなければならない。
	 */
	public static final String VERTICAL_DIRECTION_ACTION_ID = ChangeComponentDirectionAction.class
			.getName()
			+ "_VERTICAL";

	/**
	 * コンストラクタ
	 * @param part 
	 * @param id　アクションのID
	 */
	public ChangeComponentDirectionAction(IWorkbenchPart part, String id) {
		super(part);
		setId(id);
		setText("Change Direction");
	}

	protected boolean calculateEnabled() {
		Command cmd = createCommand(getSelectedObjects());
		if (cmd == null)
			return false;
		return cmd.canExecute();
	}

	protected Command createCommand(List selectedEditParts) {
		if (selectedEditParts.isEmpty())
			return null;
		if (!(selectedEditParts.get(0) instanceof EditPart))
			return null;

		ChangeBoundsRequest request = new ChangeBoundsRequest();
		request.setType(EditPolicyConstraint.REQ_CHANGE_DIRECTION);

		int direction = Component.RIGHT_DIRECTION;
		if (HORIZON_DIRECTION_ACTION_ID.equals(getId())) {
			direction = Component.CHANGE_HORIZON_DIRECTION;
		} else if (VERTICAL_DIRECTION_ACTION_ID.equals(getId())) {
			direction = Component.CHANGE_VERTICAL_DIRECTION;
		}

		HashMap<String, Integer> data = new HashMap<String, Integer>();
		data.put(EditPolicyConstraint.DIRECTION_KEY, direction);

		request.setExtendedData(data);

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

	public void run() {
		execute(createCommand(getSelectedObjects()));
	}

	protected void init() {
		super.init();
	}

}
