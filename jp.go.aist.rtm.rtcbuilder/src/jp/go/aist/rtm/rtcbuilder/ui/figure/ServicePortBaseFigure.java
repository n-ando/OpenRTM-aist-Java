package jp.go.aist.rtm.rtcbuilder.ui.figure;

import jp.go.aist.rtm.rtcbuilder.model.component.ServicePort;
import jp.go.aist.rtm.rtcbuilder.ui.preference.BuilderViewPreferenceManager;

import org.eclipse.draw2d.XYLayout;

public class ServicePortBaseFigure extends PortFigureBase {

	private ServicePortFigure servicePortFig;

	public ServicePortBaseFigure(ServicePort servicePort, int direction) {
		servicePortFig = new ServicePortFigure(servicePort, direction,
				BuilderViewPreferenceManager.getInstance().getColor(BuilderViewPreferenceManager.COLOR_SERVICEPORT));
		setLayoutManager(new XYLayout());
		add(servicePortFig);
	}
	
	public ServicePortFigure getInnerFigure() {
		return this.servicePortFig;
	}

	public void setInnerFigure(ServicePortFigure figure) {
		this.servicePortFig = figure;
	}
}
