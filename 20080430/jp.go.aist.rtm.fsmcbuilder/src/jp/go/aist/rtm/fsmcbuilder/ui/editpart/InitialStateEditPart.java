package jp.go.aist.rtm.fsmcbuilder.ui.editpart;

import jp.go.aist.rtm.fsmcbuilder.ui.figures.InitialStateFigure;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;

public class InitialStateEditPart extends NodeElementEditPart {

	protected IFigure createFigure() {
		InitialStateFigure figure = new InitialStateFigure();
		return figure;
	}

	protected void createEditPolicies() {
		super.createEditPolicies();
		installEditPolicy("ResizePolicy", new NonResizableEditPolicy());
	}

}
