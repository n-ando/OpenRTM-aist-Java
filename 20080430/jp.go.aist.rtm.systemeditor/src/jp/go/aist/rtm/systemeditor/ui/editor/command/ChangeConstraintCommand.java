package jp.go.aist.rtm.systemeditor.ui.editor.command;

import jp.go.aist.rtm.systemeditor.ui.util.Draw2dUtil;
import jp.go.aist.rtm.toolscommon.model.core.ModelElement;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

/**
 * 制約（位置、大きさ）の変更を行うコマンド
 */
public class ChangeConstraintCommand extends Command {
	private ModelElement model;

	private Rectangle constraint;

	private Rectangle oldConstraint;

	private ClearLineConstraintCommand clearLineConstraintCommand = new ClearLineConstraintCommand();

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void execute() {
		model.setConstraint(Draw2dUtil.toRtcLinkRectangle(constraint));

		clearLineConstraintCommand.setModel(model);
		clearLineConstraintCommand.execute();
	}

	/**
	 * 制約（位置、大きさ）を設定する
	 * 
	 * @param constraint
	 *            制約
	 */
	public void setConstraint(Rectangle constraint) {
		if( constraint.x < 0 ) constraint.x = 0;
		if( constraint.y < 0 ) constraint.y = 0;
		this.constraint = constraint;
	}

	/**
	 * 変更対象のモデルを設定
	 * 
	 * @param model
	 *            変更対象のモデル
	 */
	public void setModel(ModelElement model) {
		this.model = model;
		this.oldConstraint = Draw2dUtil
				.toDraw2dRectangle(model.getConstraint());
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void undo() {
		clearLineConstraintCommand.undo();

		model.setConstraint(Draw2dUtil.toRtcLinkRectangle(oldConstraint));
	}
}
