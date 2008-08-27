package jp.go.aist.rtm.rtcbuilder.ui.editpart;

import jp.go.aist.rtm.rtcbuilder.model.component.DataOutPort;
import jp.go.aist.rtm.rtcbuilder.ui.figure.ComponentFigure;
import jp.go.aist.rtm.rtcbuilder.ui.figure.OutPortFigure;
import jp.go.aist.rtm.rtcbuilder.ui.preference.BuilderViewPreferenceManager;

import org.eclipse.draw2d.IFigure;

/**
 * OutPort‚ÌEditPartƒNƒ‰ƒX
 */
public class OutPortEditPart extends PortEditPartBase {

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected IFigure createFigure() {
		ComponentFigure parentFigure = (ComponentFigure)((ComponentEditPart)this.getParent()).getFigure();
		int direction = this.getModel().getDirection().getValue();
		OutPortFigure result = new OutPortFigure(getModel(), direction,
				BuilderViewPreferenceManager.getInstance().getColor(BuilderViewPreferenceManager.COLOR_DATAOUTPORT));
		int index = this.getModel().getIndex();

		return modifyPosition(parentFigure, direction, index, result);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public DataOutPort getModel() {
		return (DataOutPort) super.getModel();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void refreshVisuals() {
		OutPortFigure outport = (OutPortFigure)getFigure();
		originalChildren = outport.getParent().getChildren();
		super.refreshVisuals();
	}

}
