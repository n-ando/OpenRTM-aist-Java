package jp.go.aist.rtm.fsmcbuilder.model.command;

import jp.go.aist.rtm.fsmcbuilder.model.NodeElement;

import org.eclipse.gef.commands.Command;

public class ResizeCommand extends Command {

	private NodeElement element;
	private int oldw, width;
	private int oldh, height;

	
	public ResizeCommand(NodeElement element, int width, int height) {
		super();
		this.element = element;
		this.width = width;
		this.height = height;
	}

	public void execute() {
		oldw = element.getWidth();
		oldh = element.getHeight();
		element.setWidth(width);
		element.setHeight(height);
	}
	
	public void undo() {
		element.setWidth(oldw);
		element.setHeight(oldh);
	}
}
