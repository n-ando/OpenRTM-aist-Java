package jp.go.aist.rtm.fsmcbuilder.ui.figures;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.CompoundBorder;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.MarginBorder;

public class FinalStateFigure extends Ellipse {

	public FinalStateFigure() {
		setSize(18, 18);
		setBackgroundColor(ColorConstants.white);
		setLayoutManager(new BorderLayout());
		CompoundBorder border = new CompoundBorder(null, new MarginBorder(3));
		setBorder(border);
		//
		Ellipse center = new Ellipse();
		center.setSize(12, 12);
		center.setBackgroundColor(ColorConstants.black);
		add(center, BorderLayout.CENTER);
	}
}
