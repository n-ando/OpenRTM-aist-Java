package jp.go.aist.rtm.systemeditor.ui.editor.editpolicy;

import jp.go.aist.rtm.systemeditor.ui.dialog.DataConnectorCreaterDialog;
import jp.go.aist.rtm.systemeditor.ui.dialog.ServiceConnectorCreaterDialog;
import jp.go.aist.rtm.systemeditor.ui.editor.command.ConnectorCreateManager;
import jp.go.aist.rtm.toolscommon.model.component.Component;
import jp.go.aist.rtm.toolscommon.model.component.ComponentFactory;
import jp.go.aist.rtm.toolscommon.model.component.ComponentSpecification;
import jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile;
import jp.go.aist.rtm.toolscommon.model.component.ConnectorSource;
import jp.go.aist.rtm.toolscommon.model.component.ConnectorTarget;
import jp.go.aist.rtm.toolscommon.model.component.InPort;
import jp.go.aist.rtm.toolscommon.model.component.OutPort;
import jp.go.aist.rtm.toolscommon.model.component.Port;
import jp.go.aist.rtm.toolscommon.model.component.PortConnectorSpecification;
import jp.go.aist.rtm.toolscommon.model.component.ServicePort;
import jp.go.aist.rtm.toolscommon.model.component.impl.PortConnectorImpl;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class GraphicalConnectorCreateManager implements ConnectorCreateManager {

	private Shell shell;

	private Port first;

	private Port second;

	public GraphicalConnectorCreateManager(Shell shell) {
		this.shell = shell;
	}

	public ConnectorProfile getConnectorProfile() {
		ConnectorProfile connectorProfile = null;
		if (getSource() instanceof OutPort && getTarget() instanceof InPort) {
			connectorProfile = new DataConnectorCreaterDialog(shell)
					.getConnectorProfile((OutPort) getSource(),
							(InPort) getTarget());
		} else if (getSource() instanceof ServicePort
				&& getTarget() instanceof ServicePort) {
			connectorProfile = new ServiceConnectorCreaterDialog(shell)
					.getConnectorProfile((ServicePort) getSource(),
							(ServicePort) getTarget());
		}

		return connectorProfile;
	}

	/**
	 * {@inheritDoc}
	 */
	public Port getFirst() {
		return first;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setFirst(Port first) {
		this.first = first;
	}

	/**
	 * {@inheritDoc}
	 */
	public Port getSecond() {
		return second;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setSecond(Port second) {
		this.second = second;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean validate() {
		return getSource().validateConnector((ConnectorTarget) getTarget())
				&& getTarget().validateConnector((ConnectorSource) getSource());
	}

	/**
	 * {@inheritDoc}
	 */
	public void createProfileAndConnector() {
		ConnectorProfile connectorProfile = getConnectorProfile();
		if (connectorProfile == null) {
			return;
		}

		boolean result = connectR(connectorProfile);
		if (result == false) {
			return;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean connectR(ConnectorProfile connectorProfile) {
		boolean result = false;
		if (getSource().eContainer() instanceof Component) {
			result = PortConnectorImpl.createConnectorR(getFirst(),
					getSecond(), connectorProfile);	
		}else if (getSource().eContainer() instanceof ComponentSpecification) {
			PortConnectorSpecification connector = ComponentFactory.eINSTANCE
			.createPortConnectorSpecification();
			connector.setSource(getFirst());
			connector.setTarget(getSecond());
			connector.setConnectorProfile(connectorProfile);
			result = connector.createConnectorR();
		}
		if (result == false) {
			MessageDialog.openError(shell, "ÉGÉâÅ[", "ê⁄ë±Ç…é∏îsÇµÇ‹ÇµÇΩÅB");
		}

		return result;
	}

	/**
	 * ê⁄ë±å≥ÇéÊìæÇ∑ÇÈ
	 */
	public Port getSource() {
		Port result = first;
		if (second instanceof OutPort) {
			result = second;
		}
		return result;
	}

	/**
	 * ê⁄ë±êÊÇéÊìæÇ∑ÇÈ
	 */
	public Port getTarget() {
		Port result = first;
		if (first == getSource()) {
			result = second;
		}

		return result;
	}

}
