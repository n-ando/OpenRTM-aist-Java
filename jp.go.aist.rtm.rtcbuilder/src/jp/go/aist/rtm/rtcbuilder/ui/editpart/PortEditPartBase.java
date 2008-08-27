package jp.go.aist.rtm.rtcbuilder.ui.editpart;

import java.util.ArrayList;
import java.util.List;

import jp.go.aist.rtm.rtcbuilder.model.component.PortDirection;
import jp.go.aist.rtm.rtcbuilder.ui.figure.ComponentFigure;
import jp.go.aist.rtm.rtcbuilder.ui.figure.PortFigureBase;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.ui.PlatformUI;

public abstract class PortEditPartBase extends AbstractEditPart {
	
	protected List originalChildren = null;

	protected IFigure modifyPosition(ComponentFigure parentFigure, int direction, int index, PortFigureBase result) {
		int maxNum, posX, posY;
		Rectangle parentBound = parentFigure.getInnerFigure().getBounds();
		if(direction == PortDirection.RIGHT) {
			maxNum = ((ComponentEditPart)this.getParent()).getModel().getRightMaxNum();
			posX = parentBound.x + parentBound.width - result.getSize().width + PortFigureBase.SIZE;
			posY = parentBound.y + (parentBound.height-PortFigureBase.SIZE) - (parentBound.height-PortFigureBase.SIZE)*(index+1)/(maxNum+1);
		} else if(direction == PortDirection.TOP) {
			maxNum = ((ComponentEditPart)this.getParent()).getModel().getTopMaxNum();
			posX = parentBound.x - result.getSize().width/2 + (parentBound.width-PortFigureBase.SIZE)*(index+1)/(maxNum+1);
			posY = parentBound.y - result.getSize().height + PortFigureBase.SIZE + PortFigureBase.LABELMARGIN;
		} else if(direction == PortDirection.BOTTOM) {
			maxNum = ((ComponentEditPart)this.getParent()).getModel().getBottomMaxNum();
			posX = parentBound.x - result.getSize().width/2 + (parentBound.width-PortFigureBase.SIZE)*(index+1)/(maxNum+1);
			posY = parentBound.y + parentBound.height - PortFigureBase.SIZE - PortFigureBase.LABELMARGIN;
		} else {
			maxNum = ((ComponentEditPart)this.getParent()).getModel().getLeftMaxNum();
			posX = parentBound.x - PortFigureBase.SIZE;
			posY = parentBound.y + (parentBound.height-PortFigureBase.SIZE) - (parentBound.height-PortFigureBase.SIZE)*(index+1)/(maxNum+1);
		}
		result.setLocation(new Point(posX, posY));
		return result;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void createEditPolicies() {
	}

	/**
	 * {@inheritDoc}
	 */
	public void notifyChanged(Notification notification) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				if (isActive()) {
					refresh();
					refreshVisuals();
				}
			}
		});
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void refreshVisuals() {
		List reOrderedItems = new ArrayList(2);
		List reOrderedTemp = new ArrayList();
		
		for( int intIdx=0; intIdx<originalChildren.size();intIdx++ ) {
			Object item = originalChildren.get(intIdx);
			if( item instanceof RectangleFigure ) {
				reOrderedItems.add(0, item);
			} else if( item instanceof Label ) {
				reOrderedItems.add(1, item);
			} else {
				reOrderedTemp.add(item);
			}
		}
		reOrderedItems.addAll(reOrderedTemp);
		originalChildren.clear();
		originalChildren.addAll(reOrderedItems);
	}
}
