package jp.go.aist.rtm.fsmcbuilder.ui.editpart;

import java.util.List;

import jp.go.aist.rtm.fsmcbuilder.model.ModelPackage;
import jp.go.aist.rtm.fsmcbuilder.model.NodeElement;
import jp.go.aist.rtm.fsmcbuilder.ui.policies.ElementComponentEditPolicy;
import jp.go.aist.rtm.fsmcbuilder.ui.policies.ElementGraphicalNodeEditPolicy;
import jp.go.aist.rtm.fsmcbuilder.util.AdapterUtil;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;

public abstract class NodeElementEditPart extends AbstractModelEditPart implements NodeEditPart {

	public void notifyChanged(Notification notification) {
        int id = notification.getFeatureID(NodeElement.class);
        switch (id) {
        case ModelPackage.NODE_ELEMENT__X:
            refreshVisuals();
            break;
        case ModelPackage.NODE_ELEMENT__Y:
            refreshVisuals();
            break;
        case ModelPackage.NODE_ELEMENT__INCOMING:
            refreshTargetConnections();
            break;
        case ModelPackage.NODE_ELEMENT__OUTGOING:
        	refreshSourceConnections();
        	break;
        }
	}
	
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new ElementComponentEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new ElementGraphicalNodeEditPolicy());
	}
	
	protected void refreshVisuals() {
		super.refreshVisuals();
		NodeElement element = (NodeElement) getModel();
		Point point = new Point(element.getX(), element.getY()); 
		Dimension dimension = new Dimension(-1,-1);
		Rectangle rectangle = new Rectangle(point, dimension);
		
		GraphicalEditPart parent = (GraphicalEditPart) getParent();
		parent.setLayoutConstraint(this, getFigure(), rectangle);		
	}

	protected List getModelSourceConnections() {
		NodeElement element = (NodeElement) getModel();
		return element.getOutgoing();
	}

	protected List getModelTargetConnections() {
		NodeElement element = (NodeElement) getModel();
		return element.getIncoming();
	}
	
	public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
		return new ChopboxAnchor(getFigure());
	}

	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return new ChopboxAnchor(getFigure());
	}

	public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
		return new ChopboxAnchor(getFigure());
	}

	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		return new ChopboxAnchor(getFigure());
	}

	public Object getAdapter(Class key) {
		Object result = AdapterUtil.getAdapter(getModel(), key);
		if( result == null ) {
			result = super.getAdapter(key);
		}

		return result;
	}
}
