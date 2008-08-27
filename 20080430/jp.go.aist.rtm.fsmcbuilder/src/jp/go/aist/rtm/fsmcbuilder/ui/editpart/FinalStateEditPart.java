package jp.go.aist.rtm.fsmcbuilder.ui.editpart;

import jp.go.aist.rtm.fsmcbuilder.ui.figures.FinalStateFigure;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;

public class FinalStateEditPart extends NodeElementEditPart {

	protected IFigure createFigure() {
		FinalStateFigure figure = new FinalStateFigure();
		return figure;
	}

	protected void createEditPolicies() {
		super.createEditPolicies();
		installEditPolicy("ResizePolicy", new NonResizableEditPolicy());
	}
}
