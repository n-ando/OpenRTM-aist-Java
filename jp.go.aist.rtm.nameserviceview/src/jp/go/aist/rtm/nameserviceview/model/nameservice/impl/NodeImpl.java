/**
 * <copyright>
 * </copyright>
 *
 * $Id: NodeImpl.java,v 1.5 2008/01/28 09:44:51 yamashita Exp $
 */
package jp.go.aist.rtm.nameserviceview.model.nameservice.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import jp.go.aist.rtm.nameserviceview.model.nameservice.NameServiceReference;
import jp.go.aist.rtm.nameserviceview.model.nameservice.NameservicePackage;
import jp.go.aist.rtm.nameserviceview.model.nameservice.NamingContextNode;
import jp.go.aist.rtm.nameserviceview.model.nameservice.Node;
import jp.go.aist.rtm.toolscommon.model.core.impl.CorbaWrapperObjectImpl;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.AttributeMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ClassMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.MappingRule;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ReferenceMapping;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

/**
 * 
 * @generated
 */
public class NodeImpl extends CorbaWrapperObjectImpl implements Node {

	public static final String THIS = "THIS";

	/**
	 * The cached value of the '{@link #getNameServiceReference() <em>Name Service Reference</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getNameServiceReference()
	 * @generated
	 * @ordered
	 */
	protected NameServiceReference nameServiceReference= null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public NodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return NameservicePackage.Literals.NODE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NameServiceReference getNameServiceReference() {
		if (nameServiceReference != null && nameServiceReference.eIsProxy()) {
			InternalEObject oldNameServiceReference = (InternalEObject)nameServiceReference;
			nameServiceReference = (NameServiceReference)eResolveProxy(oldNameServiceReference);
			if (nameServiceReference != oldNameServiceReference) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, NameservicePackage.NODE__NAME_SERVICE_REFERENCE, oldNameServiceReference, nameServiceReference));
			}
		}
		return nameServiceReference;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NameServiceReference basicGetNameServiceReference() {
		return nameServiceReference;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setNameServiceReference(NameServiceReference newNameServiceReference) {
		NameServiceReference oldNameServiceReference = nameServiceReference;
		nameServiceReference = newNameServiceReference;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NameservicePackage.NODE__NAME_SERVICE_REFERENCE, oldNameServiceReference, nameServiceReference));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 */
	public void deleteR() throws NotFound, CannotProceed, InvalidName {
		NameComponent[] nameComponents = getNameServiceReference().getBinding().binding_name;
		eContainer()
				.getCorbaObjectInterface()
				.unbind(
						new NameComponent[] { nameComponents[nameComponents.length - 1] });
		// setParent(null);
		EcoreUtil.remove(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case NameservicePackage.NODE__NAME_SERVICE_REFERENCE:
				if (resolve) return getNameServiceReference();
				return basicGetNameServiceReference();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case NameservicePackage.NODE__NAME_SERVICE_REFERENCE:
				setNameServiceReference((NameServiceReference)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case NameservicePackage.NODE__NAME_SERVICE_REFERENCE:
				setNameServiceReference((NameServiceReference)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case NameservicePackage.NODE__NAME_SERVICE_REFERENCE:
				return nameServiceReference != null;
		}
		return super.eIsSet(featureID);
	}

	public NodeImpl(org.omg.CORBA.Object remoteObject,
			NameServiceReference nameServiceReference) {
		setCorbaObject(remoteObject);
		this.nameServiceReference = nameServiceReference;
	}

	public static Comparator<org.omg.CosNaming.Binding> COMARATOR = new Comparator<org.omg.CosNaming.Binding>() {
		public int compare(org.omg.CosNaming.Binding o1,
				org.omg.CosNaming.Binding o2) {
			for (int i = 0; i < o1.binding_name.length
					&& i < o2.binding_name.length; i++) {
				int compareId = o1.binding_name[i].id
						.compareTo(o2.binding_name[i].id);
				if (compareId != 0) {
					return compareId;
				}

				int compareKind = o1.binding_name[i].kind
						.compareTo(o2.binding_name[i].kind);
				if (compareKind != 0) {
					return compareKind;
				}
			}

			return o1.binding_name.length - o2.binding_name.length;
		}
	};

	public static void removeAll(List<org.omg.CosNaming.Binding> target,
			List<org.omg.CosNaming.Binding> deleteList) {

		List<org.omg.CosNaming.Binding> tempDeleteList = new ArrayList<org.omg.CosNaming.Binding>();
		for (Iterator iter = deleteList.iterator(); iter.hasNext();) {
			org.omg.CosNaming.Binding oldBinding = (org.omg.CosNaming.Binding) iter
					.next();

			for (Iterator iterator = target.iterator(); iterator.hasNext();) {
				org.omg.CosNaming.Binding newBinding = (org.omg.CosNaming.Binding) iterator
						.next();

				if (COMARATOR.compare(oldBinding, newBinding) == 0) {
					tempDeleteList.add(newBinding);
				}
			}
		}

		for (org.omg.CosNaming.Binding deleteBinding : tempDeleteList) {
			target.remove(deleteBinding);
		}
	}

	@Override
	public NamingContextNode eContainer() {
		return (NamingContextNode) super.eContainer();
	}

	public static final MappingRule MAPPING_RULE = new MappingRule(null,
			new ClassMapping(NodeImpl.class, null), new AttributeMapping[] {},
			new ReferenceMapping[] {});

} // NodeImpl
