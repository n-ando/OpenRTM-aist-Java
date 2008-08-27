package jp.go.aist.rtm.rtcbuilder.ui.editpart;

import java.util.List;

import jp.go.aist.rtm.rtcbuilder.model.component.ServicePort;
import jp.go.aist.rtm.rtcbuilder.ui.figure.ComponentFigure;
import jp.go.aist.rtm.rtcbuilder.ui.figure.ServicePortBaseFigure;
import jp.go.aist.rtm.rtcbuilder.ui.figure.ServicePortFigure;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;

public class ServicePortEditPart extends PortEditPartBase {

	@Override
	protected List getModelChildren() {
		return ((ServicePort)getModel()).getServiceInterfaces();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public ServicePort getModel() {
		return (ServicePort) super.getModel();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected IFigure createFigure() {
		ComponentFigure parentFigure = (ComponentFigure)((ComponentEditPart)this.getParent()).getFigure();
		int direction = this.getModel().getDirection().getValue();
		ServicePortBaseFigure result = new ServicePortBaseFigure(getModel(), direction);
		int index = this.getModel().getIndex();
		result.setSize(Integer.MAX_VALUE, Integer.MAX_VALUE); 
		result.setLocation(new Point(0,0));
		
		ServicePortFigure innerFigure = (ServicePortFigure)modifyPosition(parentFigure, direction, index, result.getInnerFigure());
		result.setInnerFigure(innerFigure);

		return result;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void refreshVisuals() {
		ServicePortBaseFigure srvport = (ServicePortBaseFigure)getFigure();
		originalChildren = srvport.getParent().getChildren();
		super.refreshVisuals();
	}
}
