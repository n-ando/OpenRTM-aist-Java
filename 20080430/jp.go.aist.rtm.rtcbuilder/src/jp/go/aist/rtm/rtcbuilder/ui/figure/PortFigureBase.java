package jp.go.aist.rtm.rtcbuilder.ui.figure;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.XYLayout;

public class PortFigureBase extends PolygonFigure {

	protected Label portLabel;
	public static int SIZE = 11; 
	protected int LABELWIDTH = 200; 
	public static int LABELMARGIN = 4;
	
	protected PortFigureBase() {
		setLayoutManager(new XYLayout());
		portLabel = new Label();
		setForegroundColor(ColorConstants.black);
	}
}
