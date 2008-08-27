package jp.go.aist.rtm.fsmcbuilder.model.command;

import jp.go.aist.rtm.fsmcbuilder.model.NodeElement;

import org.eclipse.gef.commands.Command;

public class MoveElementCommand extends Command {

  private int x, oldx;
  private int y, oldy;
  private NodeElement element;

  public MoveElementCommand(int x, int y, NodeElement element) {
    super();
    this.x = x;
    this.y = y;
    this.element = element;
  }

  public void execute() {
    oldx = element.getX();
    oldy = element.getY();
    element.setX(x);
    element.setY(y);
  }

  public void undo() {
    element.setX(oldx);
    element.setY(oldy);
  }
}
