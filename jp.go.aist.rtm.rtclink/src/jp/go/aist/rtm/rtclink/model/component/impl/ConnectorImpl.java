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
import jp.go.aist.rtm.rtclink.model.core.impl.WrapperObjectImpl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Connector</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link jp.go.aist.rtm.rtclink.model.component.impl.ConnectorImpl#getRoutingConstraint <em>Routing Constraint</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtclink.model.component.impl.ConnectorImpl#getSource <em>Source</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtclink.model.component.impl.ConnectorImpl#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class ConnectorImpl extends WrapperObjectImpl implements
		Connector {

	/**
	 * The cached value of the '{@link #getRoutingConstraint() <em>Routing Constraint</em>}' map.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getRoutingConstraint()
	 * @generated
	 * @ordered
	 */
	protected EMap routingConstraint = null;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected ConnectorTarget target = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected ConnectorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ComponentPackage.eINSTANCE.getConnector();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EMap getRoutingConstraint() {
		if (routingConstraint == null) {
			routingConstraint = new EcoreEMap(ComponentPackage.eINSTANCE.getEIntegerObjectToPointMapEntry(), EIntegerObjectToPointMapEntryImpl.class, this, ComponentPackage.CONNECTOR__ROUTING_CONSTRAINT);
		}
		return routingConstraint;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ConnectorSource getSource() {
		if (eContainerFeatureID != ComponentPackage.CONNECTOR__SOURCE) return null;
		return (ConnectorSource)eContainer;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setSource(ConnectorSource newSource) {
		if (newSource != eContainer || (eContainerFeatureID != ComponentPackage.CONNECTOR__SOURCE && newSource != null)) {
			if (EcoreUtil.isAncestor(this, newSource))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eContainer != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newSource != null)
				msgs = ((InternalEObject)newSource).eInverseAdd(this, ComponentPackage.CONNECTOR_SOURCE__SOURCE_CONNECTORS, ConnectorSource.class, msgs);
			msgs = eBasicSetContainer((InternalEObject)newSource, ComponentPackage.CONNECTOR__SOURCE, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.CONNECTOR__SOURCE, newSource, newSource));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ConnectorTarget getTarget() {
		if (target != null && target.eIsProxy()) {
			ConnectorTarget oldTarget = target;
			target = (ConnectorTarget)eResolveProxy((InternalEObject)target);
			if (target != oldTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ComponentPackage.CONNECTOR__TARGET, oldTarget, target));
			}
		}
		return target;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ConnectorTarget basicGetTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTarget(ConnectorTarget newTarget, NotificationChain msgs) {
		ConnectorTarget oldTarget = target;
		target = newTarget;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ComponentPackage.CONNECTOR__TARGET, oldTarget, newTarget);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarget(ConnectorTarget newTarget) {
		if (newTarget != target) {
			NotificationChain msgs = null;
			if (target != null)
				msgs = ((InternalEObject)target).eInverseRemove(this, ComponentPackage.CONNECTOR_TARGET__TARGET_CONNECTORS, ConnectorTarget.class, msgs);
			if (newTarget != null)
				msgs = ((InternalEObject)newTarget).eInverseAdd(this, ComponentPackage.CONNECTOR_TARGET__TARGET_CONNECTORS, ConnectorTarget.class, msgs);
			msgs = basicSetTarget(newTarget, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.CONNECTOR__TARGET, newTarget, newTarget));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 */
	public void attachSource() {
		// if (source.getSourceConnectors().contains(this) == false) {
		getSource().getSourceConnectors().add(this);
		// }
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 */
	public void dettachSource() {
		if (getSource() != null) {
			getSource().getSourceConnectors().remove(this);
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 */
	public void attachTarget() {
		// if (target.getTargetConnectors().contains(this) == false) {
		getTarget().getTargetConnectors().add(this);
		// }
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 */
	public void dettachTarget() {
		if (getTarget() != null) {
			getTarget().getTargetConnectors().remove(this);
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 */
	public boolean createConnectorR() {
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 */
	public boolean deleteConnectorR() {
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case ComponentPackage.CONNECTOR__SOURCE:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, ComponentPackage.CONNECTOR__SOURCE, msgs);
				case ComponentPackage.CONNECTOR__TARGET:
					if (target != null)
						msgs = ((InternalEObject)target).eInverseRemove(this, ComponentPackage.CONNECTOR_TARGET__TARGET_CONNECTORS, ConnectorTarget.class, msgs);
					return basicSetTarget((ConnectorTarget)otherEnd, msgs);
				default:
					return eDynamicInverseAdd(otherEnd, featureID, baseClass, msgs);
			}
		}
		if (eContainer != null)
			msgs = eBasicRemoveFromContainer(msgs);
		return eBasicSetContainer(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case ComponentPackage.CONNECTOR__ROUTING_CONSTRAINT:
					return ((InternalEList)getRoutingConstraint()).basicRemove(otherEnd, msgs);
				case ComponentPackage.CONNECTOR__SOURCE:
					return eBasicSetContainer(null, ComponentPackage.CONNECTOR__SOURCE, msgs);
				case ComponentPackage.CONNECTOR__TARGET:
					return basicSetTarget(null, msgs);
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
	public NotificationChain eBasicRemoveFromContainer(NotificationChain msgs) {
		if (eContainerFeatureID >= 0) {
			switch (eContainerFeatureID) {
				case ComponentPackage.CONNECTOR__SOURCE:
					return eContainer.eInverseRemove(this, ComponentPackage.CONNECTOR_SOURCE__SOURCE_CONNECTORS, ConnectorSource.class, msgs);
				default:
					return eDynamicBasicRemoveFromContainer(msgs);
			}
		}
		return eContainer.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - eContainerFeatureID, null, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case ComponentPackage.CONNECTOR__CONSTRAINT:
				return getConstraint();
			case ComponentPackage.CONNECTOR__ROUTING_CONSTRAINT:
				return getRoutingConstraint();
			case ComponentPackage.CONNECTOR__SOURCE:
				return getSource();
			case ComponentPackage.CONNECTOR__TARGET:
				if (resolve) return getTarget();
				return basicGetTarget();
		}
		return eDynamicGet(eFeature, resolve);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(EStructuralFeature eFeature, Object newValue) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case ComponentPackage.CONNECTOR__CONSTRAINT:
				setConstraint((Rectangle)newValue);
				return;
			case ComponentPackage.CONNECTOR__ROUTING_CONSTRAINT:
				getRoutingConstraint().clear();
				getRoutingConstraint().addAll((Collection)newValue);
				return;
			case ComponentPackage.CONNECTOR__SOURCE:
				setSource((ConnectorSource)newValue);
				return;
			case ComponentPackage.CONNECTOR__TARGET:
				setTarget((ConnectorTarget)newValue);
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
			case ComponentPackage.CONNECTOR__CONSTRAINT:
				setConstraint(CONSTRAINT_EDEFAULT);
				return;
			case ComponentPackage.CONNECTOR__ROUTING_CONSTRAINT:
				getRoutingConstraint().clear();
				return;
			case ComponentPackage.CONNECTOR__SOURCE:
				setSource((ConnectorSource)null);
				return;
			case ComponentPackage.CONNECTOR__TARGET:
				setTarget((ConnectorTarget)null);
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
			case ComponentPackage.CONNECTOR__CONSTRAINT:
				return CONSTRAINT_EDEFAULT == null ? constraint != null : !CONSTRAINT_EDEFAULT.equals(constraint);
			case ComponentPackage.CONNECTOR__ROUTING_CONSTRAINT:
				return routingConstraint != null && !routingConstraint.isEmpty();
			case ComponentPackage.CONNECTOR__SOURCE:
				return getSource() != null;
			case ComponentPackage.CONNECTOR__TARGET:
				return target != null;
		}
		return eDynamicIsSet(eFeature);
	}

} // ConnectorImpl
