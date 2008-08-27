/**
 * <copyright>
 * </copyright>
 *
 * $Id: RTManagerImpl.java,v 1.2 2008/03/06 04:01:49 yamashita Exp $
 */
package jp.go.aist.rtm.toolscommon.model.manager.impl;

import java.util.Collection;

import jp.go.aist.rtm.toolscommon.model.component.Component;
import jp.go.aist.rtm.toolscommon.model.core.impl.CorbaWrapperObjectImpl;
import jp.go.aist.rtm.toolscommon.model.manager.ManagerPackage;
import jp.go.aist.rtm.toolscommon.model.manager.RTManager;
import jp.go.aist.rtm.toolscommon.model.manager.RTModule;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>RT Manager</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.manager.impl.RTManagerImpl#getOwnedModules <em>Owned Modules</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.manager.impl.RTManagerImpl#getOwnedComponents <em>Owned Components</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RTManagerImpl extends CorbaWrapperObjectImpl implements RTManager {
	/**
	 * The cached value of the '{@link #getOwnedModules() <em>Owned Modules</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnedModules()
	 * @generated
	 * @ordered
	 */
	protected EList ownedModules = null;

	/**
	 * The cached value of the '{@link #getOwnedComponents() <em>Owned Components</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnedComponents()
	 * @generated
	 * @ordered
	 */
	protected EList ownedComponents = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RTManagerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ManagerPackage.Literals.RT_MANAGER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getOwnedModules() {
		if (ownedModules == null) {
			ownedModules = new EObjectContainmentEList(RTModule.class, this, ManagerPackage.RT_MANAGER__OWNED_MODULES);
		}
		return ownedModules;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getOwnedComponents() {
		if (ownedComponents == null) {
			ownedComponents = new EObjectContainmentEList(Component.class, this, ManagerPackage.RT_MANAGER__OWNED_COMPONENTS);
		}
		return ownedComponents;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ManagerPackage.RT_MANAGER__OWNED_MODULES:
				return ((InternalEList)getOwnedModules()).basicRemove(otherEnd, msgs);
			case ManagerPackage.RT_MANAGER__OWNED_COMPONENTS:
				return ((InternalEList)getOwnedComponents()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ManagerPackage.RT_MANAGER__OWNED_MODULES:
				return getOwnedModules();
			case ManagerPackage.RT_MANAGER__OWNED_COMPONENTS:
				return getOwnedComponents();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ManagerPackage.RT_MANAGER__OWNED_MODULES:
				getOwnedModules().clear();
				getOwnedModules().addAll((Collection)newValue);
				return;
			case ManagerPackage.RT_MANAGER__OWNED_COMPONENTS:
				getOwnedComponents().clear();
				getOwnedComponents().addAll((Collection)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(int featureID) {
		switch (featureID) {
			case ManagerPackage.RT_MANAGER__OWNED_MODULES:
				getOwnedModules().clear();
				return;
			case ManagerPackage.RT_MANAGER__OWNED_COMPONENTS:
				getOwnedComponents().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ManagerPackage.RT_MANAGER__OWNED_MODULES:
				return ownedModules != null && !ownedModules.isEmpty();
			case ManagerPackage.RT_MANAGER__OWNED_COMPONENTS:
				return ownedComponents != null && !ownedComponents.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //RTManagerImpl