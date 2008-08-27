/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package jp.go.aist.rtm.fsmcbuilder.model.impl;

import java.util.Collection;

import jp.go.aist.rtm.fsmcbuilder.model.Container;
import jp.go.aist.rtm.fsmcbuilder.model.ModelPackage;
import jp.go.aist.rtm.fsmcbuilder.model.NodeElement;
import jp.go.aist.rtm.fsmcbuilder.model.State;
import jp.go.aist.rtm.fsmcbuilder.model.provider.StateItemProvider;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>State</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link jp.go.aist.rtm.fsmcbuilder.model.impl.StateImpl#getElements <em>Elements</em>}</li>
 *   <li>{@link jp.go.aist.rtm.fsmcbuilder.model.impl.StateImpl#getName <em>Name</em>}</li>
 *   <li>{@link jp.go.aist.rtm.fsmcbuilder.model.impl.StateImpl#getEntry <em>Entry</em>}</li>
 *   <li>{@link jp.go.aist.rtm.fsmcbuilder.model.impl.StateImpl#getDo <em>Do</em>}</li>
 *   <li>{@link jp.go.aist.rtm.fsmcbuilder.model.impl.StateImpl#getExit <em>Exit</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class StateImpl extends NodeElementImpl implements State {
	/**
	 * The cached value of the '{@link #getElements() <em>Elements</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElements()
	 * @generated
	 * @ordered
	 */
	protected EList elements = null;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getEntry() <em>Entry</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntry()
	 * @generated
	 * @ordered
	 */
	protected static final String ENTRY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEntry() <em>Entry</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntry()
	 * @generated
	 * @ordered
	 */
	protected String entry = ENTRY_EDEFAULT;

	/**
	 * The default value of the '{@link #getDo() <em>Do</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDo()
	 * @generated
	 * @ordered
	 */
	protected static final String DO_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDo() <em>Do</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDo()
	 * @generated
	 * @ordered
	 */
	protected String do_ = DO_EDEFAULT;

	/**
	 * The default value of the '{@link #getExit() <em>Exit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExit()
	 * @generated
	 * @ordered
	 */
	protected static final String EXIT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getExit() <em>Exit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExit()
	 * @generated
	 * @ordered
	 */
	protected String exit = EXIT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ModelPackage.Literals.STATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getElements() {
		if (elements == null) {
			elements = new EObjectContainmentWithInverseEList(NodeElement.class, this, ModelPackage.STATE__ELEMENTS, ModelPackage.NODE_ELEMENT__PARENT);
		}
		return elements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.STATE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEntry() {
		return entry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEntry(String newEntry) {
		String oldEntry = entry;
		entry = newEntry;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.STATE__ENTRY, oldEntry, entry));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDo() {
		return do_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDo(String newDo) {
		String oldDo = do_;
		do_ = newDo;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.STATE__DO, oldDo, do_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getExit() {
		return exit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExit(String newExit) {
		String oldExit = exit;
		exit = newExit;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.STATE__EXIT, oldExit, exit));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.STATE__ELEMENTS:
				return ((InternalEList)getElements()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.STATE__ELEMENTS:
				return ((InternalEList)getElements()).basicRemove(otherEnd, msgs);
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
			case ModelPackage.STATE__ELEMENTS:
				return getElements();
			case ModelPackage.STATE__NAME:
				return getName();
			case ModelPackage.STATE__ENTRY:
				return getEntry();
			case ModelPackage.STATE__DO:
				return getDo();
			case ModelPackage.STATE__EXIT:
				return getExit();
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
			case ModelPackage.STATE__ELEMENTS:
				getElements().clear();
				getElements().addAll((Collection)newValue);
				return;
			case ModelPackage.STATE__NAME:
				setName((String)newValue);
				return;
			case ModelPackage.STATE__ENTRY:
				setEntry((String)newValue);
				return;
			case ModelPackage.STATE__DO:
				setDo((String)newValue);
				return;
			case ModelPackage.STATE__EXIT:
				setExit((String)newValue);
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
			case ModelPackage.STATE__ELEMENTS:
				getElements().clear();
				return;
			case ModelPackage.STATE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ModelPackage.STATE__ENTRY:
				setEntry(ENTRY_EDEFAULT);
				return;
			case ModelPackage.STATE__DO:
				setDo(DO_EDEFAULT);
				return;
			case ModelPackage.STATE__EXIT:
				setExit(EXIT_EDEFAULT);
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
			case ModelPackage.STATE__ELEMENTS:
				return elements != null && !elements.isEmpty();
			case ModelPackage.STATE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ModelPackage.STATE__ENTRY:
				return ENTRY_EDEFAULT == null ? entry != null : !ENTRY_EDEFAULT.equals(entry);
			case ModelPackage.STATE__DO:
				return DO_EDEFAULT == null ? do_ != null : !DO_EDEFAULT.equals(do_);
			case ModelPackage.STATE__EXIT:
				return EXIT_EDEFAULT == null ? exit != null : !EXIT_EDEFAULT.equals(exit);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class baseClass) {
		if (baseClass == Container.class) {
			switch (derivedFeatureID) {
				case ModelPackage.STATE__ELEMENTS: return ModelPackage.CONTAINER__ELEMENTS;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class baseClass) {
		if (baseClass == Container.class) {
			switch (baseFeatureID) {
				case ModelPackage.CONTAINER__ELEMENTS: return ModelPackage.STATE__ELEMENTS;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", entry: ");
		result.append(entry);
		result.append(", do: ");
		result.append(do_);
		result.append(", exit: ");
		result.append(exit);
		result.append(')');
		return result.toString();
	}

	public java.lang.Object getAdapter(Class adapter) {
		java.lang.Object result = null;
		if (IPropertySource.class.equals(adapter)) {
			result = new StateItemProvider(this);
		}

		if (result == null) {
			result = super.getAdapter(adapter);
		}

		return result;
	}
} //StateImpl