package jp.go.aist.rtm.rtcbuilder.ui.editpart.factory;

import jp.go.aist.rtm.rtcbuilder.model.component.BuildView;
import jp.go.aist.rtm.rtcbuilder.model.component.Component;
import jp.go.aist.rtm.rtcbuilder.model.component.DataInPort;
import jp.go.aist.rtm.rtcbuilder.model.component.DataOutPort;
import jp.go.aist.rtm.rtcbuilder.model.component.ServiceInterface;
import jp.go.aist.rtm.rtcbuilder.model.component.ServicePort;
import jp.go.aist.rtm.rtcbuilder.ui.editpart.BuildViewEditPart;
import jp.go.aist.rtm.rtcbuilder.ui.editpart.ComponentEditPart;
import jp.go.aist.rtm.rtcbuilder.ui.editpart.InPortEditPart;
import jp.go.aist.rtm.rtcbuilder.ui.editpart.OutPortEditPart;
import jp.go.aist.rtm.rtcbuilder.ui.editpart.ServiceInterfaceEditPart;
import jp.go.aist.rtm.rtcbuilder.ui.editpart.ServicePortEditPart;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

public class RtcBuilderEditPartFactory implements EditPartFactory {

	public EditPart createEditPart(EditPart context, Object model) {
		EditPart part = null;
		
		if( model instanceof BuildView ) {
			part = new BuildViewEditPart();
		} else if( model instanceof Component ) {
			part = new ComponentEditPart();
		} else if( model instanceof DataInPort ) {
			part = new InPortEditPart();
		} else if( model instanceof DataOutPort ) {
			part = new OutPortEditPart();
		} else if( model instanceof ServicePort ) {
			part = new ServicePortEditPart();
		} else if( model instanceof ServiceInterface ) {
			part = new ServiceInterfaceEditPart();
		}

		if (part != null) {
			part.setModel(model);
		}
		return part;
	}

}
