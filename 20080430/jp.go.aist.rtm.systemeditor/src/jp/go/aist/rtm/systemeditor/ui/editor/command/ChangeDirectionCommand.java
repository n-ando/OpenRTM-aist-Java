package jp.go.aist.rtm.systemeditor.ui.editor.command;

import jp.go.aist.rtm.toolscommon.model.component.AbstractComponent;

import org.eclipse.gef.commands.Command;

/**
 * 方向を変更するコマンド
 */
public class ChangeDirectionCommand extends Command {

	private AbstractComponent model;

	private int direction;

	private int oldDirection;

	private ClearLineConstraintCommand clearLineConstraintCommand = new ClearLineConstraintCommand();

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void execute() {
		model.setOutportDirection(direction);

		clearLineConstraintCommand.setModel(model);
		clearLineConstraintCommand.execute();
	}

	/**
	 * 方向を設定する
	 * 
	 * @param direction
	 *            方向
	 */
	public void setDirection(int direction) {
		this.direction = direction;
	}

	/**
	 * 変更対象のモデルを設定する
	 * 
	 * @param model
	 *            変更対象のモデル
	 */
	public void setModel(AbstractComponent model) {
		this.model = model;
		this.oldDirection = model.getOutportDirection();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void undo() {
		clearLineConstraintCommand.undo();
		model.setOutportDirection(oldDirection);
	}
}
