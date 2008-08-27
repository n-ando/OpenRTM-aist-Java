package jp.go.aist.rtm.rtcbuilder.ui.editpart;

import jp.go.aist.rtm.rtcbuilder.model.component.DataInPort;
import jp.go.aist.rtm.rtcbuilder.ui.figure.ComponentFigure;
import jp.go.aist.rtm.rtcbuilder.ui.figure.InPortFigure;
import jp.go.aist.rtm.rtcbuilder.ui.figure.PortFigureBase;
import jp.go.aist.rtm.rtcbuilder.ui.preference.BuilderViewPreferenceManager;

import org.eclipse.draw2d.IFigure;

/**
 * InPort‚ÌEditPartƒNƒ‰ƒX
 */
public class InPortEditPart extends PortEditPartBase {

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected IFigure createFigure() {
		ComponentFigure parentFigure = (ComponentFigure)((ComponentEditPart)this.getParent()).getFigure();
		int direction = this.getModel().getDirection().getValue();
		PortFigureBase result = new InPortFigure(getModel(), direction,
				BuilderViewPreferenceManager.getInstance().getColor(BuilderViewPreferenceManager.COLOR_DATAINPORT));

		int index = this.getModel().getIndex();

		return modifyPosition(parentFigure, direction, index, result);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public DataInPort getModel() {
		return (DataInPort) super.getModel();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void refreshVisuals() {
		InPortFigure inport = (InPortFigure)getFigure();
		originalChildren = inport.getParent().getChildren();
		super.refreshVisuals();
	}
}
