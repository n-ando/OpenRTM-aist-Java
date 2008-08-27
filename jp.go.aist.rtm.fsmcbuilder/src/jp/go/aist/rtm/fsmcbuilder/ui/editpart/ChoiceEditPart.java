package jp.go.aist.rtm.fsmcbuilder.ui.editpart;

import jp.go.aist.rtm.fsmcbuilder.ui.figures.ChoiceFigure;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;

public class ChoiceEditPart extends NodeElementEditPart {

	protected IFigure createFigure() {
		ChoiceFigure figure = new ChoiceFigure();
		return figure;
	}

	protected void createEditPolicies() {
		super.createEditPolicies();
		installEditPolicy("ResizePolicy", new NonResizableEditPolicy());
	}
}
