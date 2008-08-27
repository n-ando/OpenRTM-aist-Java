/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package jp.go.aist.rtm.rtclink.model.nameservice.impl;

import java.util.Collection;

import jp.go.aist.rtm.rtclink.model.core.CorePackage;
import jp.go.aist.rtm.rtclink.model.core.Rectangle;
import jp.go.aist.rtm.rtclink.model.nameservice.ManagerNamingContext;
import jp.go.aist.rtm.rtclink.model.nameservice.NameServiceReference;
import jp.go.aist.rtm.rtclink.model.nameservice.NameservicePackage;
import jp.go.aist.rtm.rtclink.model.nameservice.NamingContextNode;
import jp.go.aist.rtm.rtclink.model.nameservice.Node;
import jp.go.aist.rtm.rtclink.synchronizationframework.LocalObject;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.AttributeMapping;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.ClassMapping;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.ConstructorParamMapping;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.MappingRule;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.ReferenceMapping;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.InternalEList;
import org.omg.CosNaming.Binding;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Manager Naming Context</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class ManagerNamingContextImpl extends NamingContextNodeImpl implements
		ManagerNamingContext {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public ManagerNamingContextImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return NameservicePackage.eINSTANCE.getManagerNamingContext();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case NameservicePackage.MANAGER_NAMING_CONTEXT__NODES:
					return ((InternalEList)getNodes()).basicRemove(otherEnd, msgs);
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
			case NameservicePackage.MANAGER_NAMING_CONTEXT__CONSTRAINT:
				return getConstraint();
			case NameservicePackage.MANAGER_NAMING_CONTEXT__CORBA_OBJECT:
				return getCorbaObject();
			case NameservicePackage.MANAGER_NAMING_CONTEXT__NAME_SERVICE_REFERENCE:
				if (resolve) return getNameServiceReference();
				return basicGetNameServiceReference();
			case NameservicePackage.MANAGER_NAMING_CONTEXT__NODES:
				return getNodes();
			case NameservicePackage.MANAGER_NAMING_CONTEXT__ZOMBIE:
				return isZombie() ? Boolean.TRUE : Boolean.FALSE;
		}
		return eDynamicGet(eFeature, resolve);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(EStructuralFeature eFeature, Object newValue) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case NameservicePackage.MANAGER_NAMING_CONTEXT__CONSTRAINT:
				setConstraint((Rectangle)newValue);
				return;
			case NameservicePackage.MANAGER_NAMING_CONTEXT__CORBA_OBJECT:
				setCorbaObject((org.omg.CORBA.Object)newValue);
				return;
			case NameservicePackage.MANAGER_NAMING_CONTEXT__NAME_SERVICE_REFERENCE:
				setNameServiceReference((NameServiceReference)newValue);
				return;
			case NameservicePackage.MANAGER_NAMING_CONTEXT__NODES:
				getNodes().clear();
				getNodes().addAll((Collection)newValue);
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
			case NameservicePackage.MANAGER_NAMING_CONTEXT__CONSTRAINT:
				setConstraint(CONSTRAINT_EDEFAULT);
				return;
			case NameservicePackage.MANAGER_NAMING_CONTEXT__CORBA_OBJECT:
				setCorbaObject(CORBA_OBJECT_EDEFAULT);
				return;
			case NameservicePackage.MANAGER_NAMING_CONTEXT__NAME_SERVICE_REFERENCE:
				setNameServiceReference((NameServiceReference)null);
				return;
			case NameservicePackage.MANAGER_NAMING_CONTEXT__NODES:
				getNodes().clear();
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
			case NameservicePackage.MANAGER_NAMING_CONTEXT__CONSTRAINT:
				return CONSTRAINT_EDEFAULT == null ? constraint != null : !CONSTRAINT_EDEFAULT.equals(constraint);
			case NameservicePackage.MANAGER_NAMING_CONTEXT__CORBA_OBJECT:
				return CORBA_OBJECT_EDEFAULT == null ? corbaObject != null : !CORBA_OBJECT_EDEFAULT.equals(corbaObject);
			case NameservicePackage.MANAGER_NAMING_CONTEXT__NAME_SERVICE_REFERENCE:
				return nameServiceReference != null;
			case NameservicePackage.MANAGER_NAMING_CONTEXT__NODES:
				return nodes != null && !nodes.isEmpty();
			case NameservicePackage.MANAGER_NAMING_CONTEXT__ZOMBIE:
				return isZombie() != ZOMBIE_EDEFAULT;
		}
		return eDynamicIsSet(eFeature);
	}

	public static final String KIND = "mgr_cxt";

	public static final MappingRule MAPPING_RULE = new MappingRule(
			NamingContextNodeImpl.MAPPING_RULE,
			new ClassMapping(
					ManagerNamingContextImpl.class,
					new ConstructorParamMapping[] { new ConstructorParamMapping(
							NamingContextExt.class,
							CorePackage.eINSTANCE
									.getCorbaWrapperObject_CorbaObject()) }) {
				@Override
				public boolean isTarget(LocalObject parent,
						Object[] remoteObjects, java.lang.Object link) {
					boolean result = false;
					if (link != null) {
						NameComponent[] nameComponent = ((Binding) link).binding_name;

						if (super.isTarget(parent, remoteObjects, link)
								&& nameComponent[nameComponent.length - 1].kind
										.equals(KIND)) {
							result = true;
						}
					}

					return result;
				}

				@Override
				public LocalObject createLocalObject(LocalObject parent,
						Object[] remoteObjects, java.lang.Object link) {
					LocalObject result = super.createLocalObject(parent,
							remoteObjects, link);
					((NamingContextNode) result)
							.setNameServiceReference(((Node) parent)
									.getNameServiceReference()
									.createMergedNameServiceReference(
											(Binding) link));

					return result;
				}
			}, new AttributeMapping[] {}, new ReferenceMapping[] {});

} // ManagerNamingContextImpl
