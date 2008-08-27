/**
 * <copyright>
 * </copyright>
 *
 * $Id: NameServiceReferenceImpl.java,v 1.3 2008/01/21 01:50:04 yamashita Exp $
 */
package jp.go.aist.rtm.nameserviceview.model.nameservice.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jp.go.aist.rtm.nameserviceview.corba.NameServerAccesser;
import jp.go.aist.rtm.nameserviceview.model.nameservice.NameServiceReference;
import jp.go.aist.rtm.nameserviceview.model.nameservice.NameservicePackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.omg.CosNaming.Binding;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Name Service Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameServiceReferenceImpl#getBinding <em>Binding</em>}</li>
 *   <li>{@link jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameServiceReferenceImpl#getNameServerName <em>Name Server Name</em>}</li>
 *   <li>{@link jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameServiceReferenceImpl#getRootNamingContext <em>Root Naming Context</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NameServiceReferenceImpl extends EObjectImpl implements
		NameServiceReference {
	/**
	 * The default value of the '{@link #getBinding() <em>Binding</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getBinding()
	 * @generated
	 * @ordered
	 */
	protected static final Binding BINDING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBinding() <em>Binding</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getBinding()
	 * @generated
	 * @ordered
	 */
	protected Binding binding = BINDING_EDEFAULT;

	/**
	 * The default value of the '{@link #getNameServerName() <em>Name Server Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getNameServerName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_SERVER_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNameServerName() <em>Name Server Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getNameServerName()
	 * @generated
	 * @ordered
	 */
	protected String nameServerName = NAME_SERVER_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getRootNamingContext() <em>Root Naming Context</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getRootNamingContext()
	 * @generated
	 * @ordered
	 */
	protected static final NamingContextExt ROOT_NAMING_CONTEXT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRootNamingContext() <em>Root Naming Context</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getRootNamingContext()
	 * @generated
	 * @ordered
	 */
	protected NamingContextExt rootNamingContext = ROOT_NAMING_CONTEXT_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 */
	public NameServiceReferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return NameservicePackage.Literals.NAME_SERVICE_REFERENCE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Binding getBinding() {
		return binding;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setBinding(Binding newBinding) {
		Binding oldBinding = binding;
		binding = newBinding;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NameservicePackage.NAME_SERVICE_REFERENCE__BINDING, oldBinding, binding));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String getNameServerName() {
		return nameServerName;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setNameServerName(String newNameServerName) {
		String oldNameServerName = nameServerName;
		nameServerName = newNameServerName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NameservicePackage.NAME_SERVICE_REFERENCE__NAME_SERVER_NAME, oldNameServerName, nameServerName));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NamingContextExt getRootNamingContext() {
		return rootNamingContext;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setRootNamingContext(NamingContextExt newRootNamingContext) {
		NamingContextExt oldRootNamingContext = rootNamingContext;
		rootNamingContext = newRootNamingContext;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NameservicePackage.NAME_SERVICE_REFERENCE__ROOT_NAMING_CONTEXT, oldRootNamingContext, rootNamingContext));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (binding: ");
		result.append(binding);
		result.append(", nameServerName: ");
		result.append(nameServerName);
		result.append(", rootNamingContext: ");
		result.append(rootNamingContext);
		result.append(')');
		return result.toString();
	}

	public NameServiceReference createMergedNameServiceReference(
			Binding childBinding) {
		NameServiceReference result = new NameServiceReferenceImpl();
		result.setRootNamingContext(this.getRootNamingContext());
		result.setNameServerName(this.getNameServerName());

		Binding binding = new Binding();
		List<NameComponent> temp = new ArrayList<NameComponent>();
		Collections.addAll(temp, this.getBinding().binding_name);
		Collections.addAll(temp, childBinding.binding_name);

		binding.binding_name = temp.toArray(new NameComponent[temp.size()]);
		binding.binding_type = childBinding.binding_type;

		result.setBinding(binding);

		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case NameservicePackage.NAME_SERVICE_REFERENCE__BINDING:
				return getBinding();
			case NameservicePackage.NAME_SERVICE_REFERENCE__NAME_SERVER_NAME:
				return getNameServerName();
			case NameservicePackage.NAME_SERVICE_REFERENCE__ROOT_NAMING_CONTEXT:
				return getRootNamingContext();
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
			case NameservicePackage.NAME_SERVICE_REFERENCE__BINDING:
				setBinding((Binding)newValue);
				return;
			case NameservicePackage.NAME_SERVICE_REFERENCE__NAME_SERVER_NAME:
				setNameServerName((String)newValue);
				return;
			case NameservicePackage.NAME_SERVICE_REFERENCE__ROOT_NAMING_CONTEXT:
				setRootNamingContext((NamingContextExt)newValue);
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
			case NameservicePackage.NAME_SERVICE_REFERENCE__BINDING:
				setBinding(BINDING_EDEFAULT);
				return;
			case NameservicePackage.NAME_SERVICE_REFERENCE__NAME_SERVER_NAME:
				setNameServerName(NAME_SERVER_NAME_EDEFAULT);
				return;
			case NameservicePackage.NAME_SERVICE_REFERENCE__ROOT_NAMING_CONTEXT:
				setRootNamingContext(ROOT_NAMING_CONTEXT_EDEFAULT);
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
			case NameservicePackage.NAME_SERVICE_REFERENCE__BINDING:
				return BINDING_EDEFAULT == null ? binding != null : !BINDING_EDEFAULT.equals(binding);
			case NameservicePackage.NAME_SERVICE_REFERENCE__NAME_SERVER_NAME:
				return NAME_SERVER_NAME_EDEFAULT == null ? nameServerName != null : !NAME_SERVER_NAME_EDEFAULT.equals(nameServerName);
			case NameservicePackage.NAME_SERVICE_REFERENCE__ROOT_NAMING_CONTEXT:
				return ROOT_NAMING_CONTEXT_EDEFAULT == null ? rootNamingContext != null : !ROOT_NAMING_CONTEXT_EDEFAULT.equals(rootNamingContext);
		}
		return super.eIsSet(featureID);
	}

	public String getPathId() {
		StringBuffer result = new StringBuffer(getNameServerName());
		for (NameComponent name : getBinding().binding_name) {
			result.append("/" + name.id + "." + name.kind);
		}

		return result.toString();
	}

	/**
	 * PathIdからNameServerNameを取得する
	 * 
	 * @param pathId
	 * @return
	 */
	public static String getNameServerNameFromPathId(String pathId) {
		return pathId.split("/")[0];
	}

	/**
	 * PathIdからObjectを取得する
	 * 
	 * @param pathId
	 * @return
	 */
	public static org.omg.CORBA.Object getObjectFromPathId(String pathId) {
		org.omg.CORBA.Object result = null;

		NamingContextExt namingContext = NameServerAccesser.getInstance()
				.getNameServerRootContext(getNameServerNameFromPathId(pathId));

		try {
			result = namingContext.resolve(getNameComponentsFromPathId(pathId));
		} catch (Exception e) {
			// void
		}

		return result;
	}

	/**
	 * PathIdからNameComponentを取得する
	 * <p>
	 * ネームサーバ名は除く
	 * 
	 * @param pathId
	 * @return
	 */
	public static NameComponent[] getNameComponentsFromPathId(String pathId) {
		List<NameComponent> result = new ArrayList<NameComponent>();
		String[] split = pathId.split("/");
		for (int i = 0; i < split.length; ++i) {
			if (i > 0) {
				int index = split[i].lastIndexOf(".");
				result.add(new NameComponent(split[i].substring(0, index),
						split[i].substring(index + ".".length())));
			}
		}

		return result.toArray(new NameComponent[result.size()]);
	}

} // NameServiceReferenceImpl
