package jp.go.aist.rtm.fsmcbuilder.model.command;

import jp.go.aist.rtm.fsmcbuilder.model.State;

import org.eclipse.gef.commands.Command;

public class DirectEditCommand extends Command {
	private String oldText, newText;
	private State stateModel;

	public void execute() {
		oldText = stateModel.getName();
		stateModel.setName(newText);
	}

	public void setModel(Object model) {
		stateModel = (State) model;
	}

	public void setText(String text) {
		newText = text;
	}

	public void undo() {
		stateModel.setName(oldText);
	}
}
