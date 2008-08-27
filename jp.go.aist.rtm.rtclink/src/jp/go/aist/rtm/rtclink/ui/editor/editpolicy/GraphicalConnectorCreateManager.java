package jp.go.aist.rtm.rtclink.ui.editor.editpolicy;

import jp.go.aist.rtm.rtclink.model.component.ConnectorProfile;
import jp.go.aist.rtm.rtclink.model.component.ConnectorSource;
import jp.go.aist.rtm.rtclink.model.component.ConnectorTarget;
import jp.go.aist.rtm.rtclink.model.component.InPort;
import jp.go.aist.rtm.rtclink.model.component.OutPort;
import jp.go.aist.rtm.rtclink.model.component.Port;
import jp.go.aist.rtm.rtclink.model.component.ServicePort;
import jp.go.aist.rtm.rtclink.model.component.impl.PortConnectorImpl;
import jp.go.aist.rtm.rtclink.ui.dialog.DataConnectorCreaterDialog;
import jp.go.aist.rtm.rtclink.ui.dialog.ServiceConnectorCreaterDialog;
import jp.go.aist.rtm.rtclink.ui.editor.command.ConnectorCreateManager;

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
		boolean result = PortConnectorImpl.createConnectorR(getFirst(),
				getSecond(), connectorProfile);
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
