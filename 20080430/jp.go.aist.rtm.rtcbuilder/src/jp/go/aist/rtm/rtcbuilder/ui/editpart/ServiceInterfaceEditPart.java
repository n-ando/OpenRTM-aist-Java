package jp.go.aist.rtm.rtcbuilder.ui.editpart;

import jp.go.aist.rtm.rtcbuilder.model.component.PortDirection;
import jp.go.aist.rtm.rtcbuilder.model.component.ServiceInterface;
import jp.go.aist.rtm.rtcbuilder.model.component.ServicePort;
import jp.go.aist.rtm.rtcbuilder.ui.figure.PortFigureBase;
import jp.go.aist.rtm.rtcbuilder.ui.figure.ServiceInterfaceFigure;
import jp.go.aist.rtm.rtcbuilder.ui.figure.ServicePortBaseFigure;
import jp.go.aist.rtm.rtcbuilder.ui.preference.BuilderViewPreferenceManager;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

public class ServiceInterfaceEditPart extends PortEditPartBase {

	@Override
	/**
	 * {@inheritDoc}
	 */
	public ServiceInterface getModel() {
		return (ServiceInterface) super.getModel();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected IFigure createFigure() {
		ServicePortBaseFigure parentBase = (ServicePortBaseFigure)((ServicePortEditPart)this.getParent()).getFigure();
		Rectangle parentBound = parentBase.getInnerFigure().getBounds();
		ServicePort parentModel = ((ServicePortEditPart)this.getParent()).getModel();
		int ifDirection = this.getModel().getDirection().getValue();
		int parentDirection = this.getModel().getParentDirection().getValue();
		PortFigureBase result = new ServiceInterfaceFigure(getModel(), parentDirection, ifDirection,
				BuilderViewPreferenceManager.getInstance().getColor(BuilderViewPreferenceManager.COLOR_SERVICEIF));
		int index = this.getModel().getIndex();

		int posX, posY;
		int maxNum = parentModel.getServiceInterfaces().size();
		if(parentDirection == PortDirection.RIGHT) {
			posX = parentBound.x + parentBound.width + ServiceInterfaceFigure.OFFSET_X;
			posY = parentBound.y - ServiceInterfaceFigure.SPACE_Y/2*(maxNum-1) + ServiceInterfaceFigure.SPACE_Y*(index);
			parentBase.addLinePoint(posX, posY+PortFigureBase.SIZE/2);
			parentBase.addLinePoint(parentBound.x+parentBound.width, parentBound.y+PortFigureBase.SIZE/2);
		} else if(parentDirection == PortDirection.TOP) {
			posX = parentBound.x - ServiceInterfaceFigure.SPACE_X/2*(maxNum-1) + ServiceInterfaceFigure.SPACE_X*(index);
			posY = parentBound.y - ServiceInterfaceFigure.OFFSET_TOP;
			parentBase.addLinePoint(posX+result.getSize().width/2, posY+PortFigureBase.SIZE*2+PortFigureBase.SIZE/3);
			parentBase.addLinePoint(parentBound.x+parentBound.width/2, parentBound.y);
		} else if(parentDirection == PortDirection.BOTTOM) {
			posX = parentBound.x - ServiceInterfaceFigure.SPACE_X/2*(maxNum-1) + ServiceInterfaceFigure.SPACE_X*(index);
			posY = parentBound.y + parentBound.height + ServiceInterfaceFigure.OFFSET_BOTTOM;
			parentBase.addLinePoint(posX+result.getSize().width/2, posY);
			parentBase.addLinePoint(parentBound.x+parentBound.width/2, parentBound.y+PortFigureBase.SIZE*2);
		} else {
			posX = parentBound.x - result.getSize().width - ServiceInterfaceFigure.OFFSET_X;
			posY = parentBound.y - ServiceInterfaceFigure.SPACE_Y/2*(maxNum-1) + ServiceInterfaceFigure.SPACE_Y*(index);
			parentBase.addLinePoint(posX+result.getSize().width, posY+PortFigureBase.SIZE/2);
			parentBase.addLinePoint(parentBound.x, parentBound.y+PortFigureBase.SIZE/2);
		}
		result.setLocation(new Point(posX, posY));

		return result;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void refreshVisuals() {
	}
}
