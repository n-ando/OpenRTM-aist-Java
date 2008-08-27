package jp.go.aist.rtm.rtclink.model.component;

import jp.go.aist.rtm.rtclink.model.core.CorbaWrapperObject;

import org.eclipse.emf.common.util.EList;

/**
 * Ú‘±Œ³‚ğ•\Œ»‚·‚éƒNƒ‰ƒX
 *
 * @model
 */
public interface ConnectorSource extends CorbaWrapperObject {
	/**
	 * <!-- begin-user-doc --> EList<? extends Connector> <!-- end-user-doc
	 * -->
	 * 
	 * @model type="Connector" containment="true" opposite="source"
	 */
	public EList getSourceConnectors();

	/**
	 * @model
	 */
	public boolean validateConnector(ConnectorTarget target);

}
