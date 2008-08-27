package jp.go.aist.rtm.rtcbuilder.ui.figure;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.graphics.Color;

public class ComponentFigure extends RectangleFigure {

	private Label componentLabel;
	private RectangleFigure innerFigure;

	public ComponentFigure(String componentName, Color backColor) {
		setLayoutManager(new XYLayout());
		setForegroundColor(ColorConstants.white);
		setBackgroundColor(ColorConstants.white);
		//
		this.innerFigure = new RectangleFigure();
		this.innerFigure.setForegroundColor(ColorConstants.black);
		this.innerFigure.setBackgroundColor(backColor);
		//
		this.add(this.innerFigure);
		//
		this.componentLabel = new Label();
		this.componentLabel.setText(componentName);
		this.componentLabel.setForegroundColor(ColorConstants.black);
		this.add(this.componentLabel);
	}

	public Label getComponentLabel() {
		return this.componentLabel;
	}

	public RectangleFigure getInnerFigure() {
		return this.innerFigure;
	}

	@Override
	public void setSize(int w, int h) {
		this.innerFigure.setSize(new Dimension(w/2,h/2-15));
		this.componentLabel.setSize(new Dimension(w/2,15));
		int base_y = this.innerFigure.getLocation().y;
		int base_h = this.innerFigure.getSize().height;
		this.componentLabel.setLocation(new Point(this.innerFigure.getLocation().x, base_y+base_h));
		super.setSize(w, h);
	}

	@Override
	public void setLocation(Point p) {
		this.innerFigure.setLocation(p);
		int base_y = this.innerFigure.getLocation().y;
		int base_h = this.innerFigure.getSize().height;
		this.componentLabel.setLocation(new Point(this.innerFigure.getLocation().x, base_y+base_h));
		super.setLocation(new Point(0,0));
	}
}