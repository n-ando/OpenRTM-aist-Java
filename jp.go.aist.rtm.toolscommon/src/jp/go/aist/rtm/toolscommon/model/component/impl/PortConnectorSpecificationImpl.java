package jp.go.aist.rtm.toolscommon.model.component.impl;

/**
 * <copyright>
 * </copyright>
 *
 * $Id: PortConnectorSpecificationImpl.java,v 1.5 2008/04/03 01:53:59 yamashita Exp $
 */
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jp.go.aist.rtm.toolscommon.model.component.ComponentFactory;
import jp.go.aist.rtm.toolscommon.model.component.ComponentPackage;
import jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile;
import jp.go.aist.rtm.toolscommon.model.component.Port;
import jp.go.aist.rtm.toolscommon.model.component.PortConnectorSpecification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Port Connector Specification</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class PortConnectorSpecificationImpl extends AbstractPortConnectorImpl
		implements PortConnectorSpecification {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected PortConnectorSpecificationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ComponentPackage.Literals.PORT_CONNECTOR_SPECIFICATION;
	}

	/**
	 * <!-- begin-user-doc --><!--
	 * end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, Class baseClass, NotificationChain msgs) {
		switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
		case ComponentPackage.PORT_CONNECTOR__SOURCE:
		case ComponentPackage.PORT_CONNECTOR__TARGET:
			setTarget(null);
			setSource(null);
		}

		return super.eInverseRemove(otherEnd, featureID, baseClass, msgs);
	}

	@SuppressWarnings("unchecked")
	public static boolean createConnectorR(Port first, Port second,
			ConnectorProfile connectorProfile) {
		if (connectorProfile.getConnectorId() == null) {
			connectorProfile.setConnectorId(UUID.randomUUID().toString());
		}
		boolean  firstExist = false;
		for (Object object : first.getPortProfile().getConnectorProfiles()) {
			if (connectorProfile.getConnectorId().equals(
					((ConnectorProfile) object).getConnectorId())) {
				firstExist = true;
				break;
			}
		}
		if (!firstExist) {
			first.getPortProfile().getConnectorProfiles().add(connectorProfile);
		}
		boolean  secondExist = false;
		for (Object object : second.getPortProfile().getConnectorProfiles()) {
			if (connectorProfile.getConnectorId().equals(
					((ConnectorProfile) object).getConnectorId())) {
				secondExist = true;
				break;
			}
		}
		if (!secondExist) {
			second.getPortProfile().getConnectorProfiles().add(connectorProfile);
		}
		PortConnectorSpecification connector = ComponentFactory.eINSTANCE
			.createPortConnectorSpecification();
		
		connector.setConnectorProfile(connectorProfile);
		List<PortConnectorSpecification> connectors = new ArrayList<PortConnectorSpecification>();
		connectors.addAll(first.getSourceConnectors());
		connectors.addAll(second.getSourceConnectors());
		connectors.addAll(first.getTargetConnectors());
		connectors.addAll(second.getTargetConnectors());
		for (PortConnectorSpecification temp : connectors) {
			if (temp.getConnectorProfile() == null) {
				EcoreUtil.remove(temp);
				continue;
			}
			if (connectorProfile.getConnectorId().equals(
					temp.getConnectorProfile().getConnectorId())
					&& temp != connectorProfile.eContainer()) {
				EcoreUtil.remove(temp);
			}
		}
		connector.setTarget(second);
		connector.setSource(first);
		return true;
	}

	public boolean deleteConnectorR() {
		boolean result = false;
		try {
			getSource().getPortProfile().getConnectorProfiles().remove(
					getConnectorProfile());
			getTarget().getPortProfile().getConnectorProfiles().remove(
					getConnectorProfile());

			result = true;
		} catch (RuntimeException e) {
			// void
		}

		return result;
	}

	@Override
	public Port getSource() {
		return (Port) super.getSource();
	}

	@Override
	public Port getTarget() {
		return (Port) super.getTarget();
	}

	@Override
	public boolean createConnectorR() {
		return createConnectorR(getSource(), getTarget(),
				getConnectorProfile());

	}

} // PortConnectorSpecificationImpl
