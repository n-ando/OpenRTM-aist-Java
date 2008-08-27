/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package jp.go.aist.rtm.rtclink.model.component.impl;

import java.util.Collection;

import jp.go.aist.rtm.rtclink.model.component.ComponentPackage;
import jp.go.aist.rtm.rtclink.model.component.Connector;
import jp.go.aist.rtm.rtclink.model.component.ConnectorSource;
import jp.go.aist.rtm.rtclink.model.component.ConnectorTarget;
import jp.go.aist.rtm.rtclink.model.core.Rectangle;
import jp.go.aist.rtm.rtclink.model.core.impl.CorbaWrapperObjectImpl;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Connector Source</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link jp.go.aist.rtm.rtclink.model.component.impl.ConnectorSourceImpl#getSourceConnectors <em>Source Connectors</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConnectorSourceImpl extends CorbaWrapperObjectImpl implements
		ConnectorSource {
	/**
	 * The cached value of the '{@link #getSourceConnectors() <em>Source Connectors</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceConnectors()
	 * @generated
	 * @ordered
	 */
	protected EList sourceConnectors = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected ConnectorSourceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ComponentPackage.eINSTANCE.getConnectorSource();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList getSourceConnectors() {
		if (sourceConnectors == null) {
			sourceConnectors = new EObjectContainmentWithInverseEList(Connector.class, this, ComponentPackage.CONNECTOR_SOURCE__SOURCE_CONNECTORS, ComponentPackage.CONNECTOR__SOURCE);
		}
		return sourceConnectors;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean validateConnector(ConnectorTarget target) {
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case ComponentPackage.CONNECTOR_SOURCE__SOURCE_CONNECTORS:
					return ((InternalEList)getSourceConnectors()).basicAdd(otherEnd, msgs);
				default:
					return eDynamicInverseAdd(otherEnd, featureID, baseClass, msgs);
			}
		}
		if (eContainer != null)
			msgs = eBasicRemoveFromContainer(msgs);
		return eBasicSetContainer(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case ComponentPackage.CONNECTOR_SOURCE__SOURCE_CONNECTORS:
					return ((InternalEList)getSourceConnectors()).basicRemove(otherEnd, msgs);
				default:
					return eDynamicInverseRemove(otherEnd, featureID, baseClass, msgs);
			}
		}
		return eBasicSetContainer(null, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case ComponentPackage.CONNECTOR_SOURCE__CONSTRAINT:
				return getConstraint();
			case ComponentPackage.CONNECTOR_SOURCE__CORBA_OBJECT:
				return getCorbaObject();
			case ComponentPackage.CONNECTOR_SOURCE__SOURCE_CONNECTORS:
				return getSourceConnectors();
		}
		return eDynamicGet(eFeature, resolve);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(EStructuralFeature eFeature, Object newValue) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case ComponentPackage.CONNECTOR_SOURCE__CONSTRAINT:
				setConstraint((Rectangle)newValue);
				return;
			case ComponentPackage.CONNECTOR_SOURCE__CORBA_OBJECT:
				setCorbaObject((org.omg.CORBA.Object)newValue);
				return;
			case ComponentPackage.CONNECTOR_SOURCE__SOURCE_CONNECTORS:
				getSourceConnectors().clear();
				getSourceConnectors().addAll((Collection)newValue);
				return;
		}
		eDynamicSet(eFeature, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case ComponentPackage.CONNECTOR_SOURCE__CONSTRAINT:
				setConstraint(CONSTRAINT_EDEFAULT);
				return;
			case ComponentPackage.CONNECTOR_SOURCE__CORBA_OBJECT:
				setCorbaObject(CORBA_OBJECT_EDEFAULT);
				return;
			case ComponentPackage.CONNECTOR_SOURCE__SOURCE_CONNECTORS:
				getSourceConnectors().clear();
				return;
		}
		eDynamicUnset(eFeature);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case ComponentPackage.CONNECTOR_SOURCE__CONSTRAINT:
				return CONSTRAINT_EDEFAULT == null ? constraint != null : !CONSTRAINT_EDEFAULT.equals(constraint);
			case ComponentPackage.CONNECTOR_SOURCE__CORBA_OBJECT:
				return CORBA_OBJECT_EDEFAULT == null ? corbaObject != null : !CORBA_OBJECT_EDEFAULT.equals(corbaObject);
			case ComponentPackage.CONNECTOR_SOURCE__SOURCE_CONNECTORS:
				return sourceConnectors != null && !sourceConnectors.isEmpty();
		}
		return eDynamicIsSet(eFeature);
	}

	public static final String SOURCE_CONNECTION = "SOURCE_CONNECTION";

} // ConnectorSourceImpl
