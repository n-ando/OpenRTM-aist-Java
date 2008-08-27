package jp.go.aist.rtm.fsmcbuilder.ui.figures;

public class ChoiceFigure extends PolygonFigure {
	public ChoiceFigure() {
		addPolyPoint(10, 0);
		addPolyPoint(20, 6);
		addPolyPoint(10, 12);
		addPolyPoint(0, 6);

		setSize(20+1, 12+1);
	}
}
