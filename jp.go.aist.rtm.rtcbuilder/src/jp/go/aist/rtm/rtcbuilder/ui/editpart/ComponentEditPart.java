package jp.go.aist.rtm.rtcbuilder.ui.editpart;

import java.util.ArrayList;
import java.util.List;

import jp.go.aist.rtm.rtcbuilder.IRtcBuilderConstants;
import jp.go.aist.rtm.rtcbuilder.model.component.Component;
import jp.go.aist.rtm.rtcbuilder.ui.figure.ComponentFigure;
import jp.go.aist.rtm.rtcbuilder.ui.preference.BuilderViewPreferenceManager;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.editparts.FreeformGraphicalRootEditPart;
import org.eclipse.ui.PlatformUI;

public class ComponentEditPart extends AbstractEditPart {

	@Override
	protected IFigure createFigure() {
		String componentName = ((Component)getModel()).getComponent_Name();
		
		ComponentFigure component = new ComponentFigure(componentName,
				BuilderViewPreferenceManager.getInstance().getColor(BuilderViewPreferenceManager.COLOR_COMPONENT));
		
		int width = ((FreeformGraphicalRootEditPart)getRoot()).getFigure().getBounds().width;
		int height = ((FreeformGraphicalRootEditPart)getRoot()).getFigure().getBounds().height;
		int x = ((FreeformGraphicalRootEditPart)getRoot()).getFigure().getBounds().width/4; //235
		int y = ((FreeformGraphicalRootEditPart)getRoot()).getFigure().getBounds().height/4; //66
		//
		int leftMax = getModel().getLeftMaxNum();
		int rightMax = getModel().getRightMaxNum();
		int tempMax;
		if( rightMax>leftMax ) tempMax = rightMax;
		else tempMax = leftMax;
		if( height < tempMax*IRtcBuilderConstants.PORT_SPACE_HEIGHT ) height = tempMax*IRtcBuilderConstants.PORT_SPACE_HEIGHT;
		//
		int topMax = getModel().getTopMaxNum();
		int bottomMax = getModel().getBottomMaxNum();
		if( bottomMax>topMax ) tempMax = bottomMax;
		else tempMax = topMax;
		if( width < tempMax*IRtcBuilderConstants.PORT_SPACE_WIDTH ) width = tempMax*IRtcBuilderConstants.PORT_SPACE_WIDTH;
		//
		Point location = new Point(x,y);
		component.setSize(width,height);
		component.setLocation(location);
		
		return component;
	}

	@Override
	protected void createEditPolicies() {
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public Component getModel() {
		return (Component) super.getModel();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected List getModelChildren() {
		List result = new ArrayList();
		result.addAll(getModel().getDataInPorts());
		result.addAll(getModel().getDataOutPorts());
		result.addAll(getModel().getServicePorts());

		return result;
	}

	public void notifyChanged(Notification notification) {
//		int id = notification.getFeatureID(Component.class);
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				if (isActive()) {
					refresh();
					refreshChildren();
				}
			}
		});
	}
}

