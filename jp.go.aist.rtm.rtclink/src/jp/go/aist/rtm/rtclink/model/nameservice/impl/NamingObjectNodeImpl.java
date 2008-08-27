/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package jp.go.aist.rtm.rtclink.model.nameservice.impl;

import jp.go.aist.rtm.rtclink.model.component.Component;
import jp.go.aist.rtm.rtclink.model.core.CorePackage;
import jp.go.aist.rtm.rtclink.model.core.Rectangle;
import jp.go.aist.rtm.rtclink.model.core.WrapperObject;
import jp.go.aist.rtm.rtclink.model.nameservice.NameServiceReference;
import jp.go.aist.rtm.rtclink.model.nameservice.NameservicePackage;
import jp.go.aist.rtm.rtclink.model.nameservice.NamingObjectNode;
import jp.go.aist.rtm.rtclink.model.nameservice.Node;
import jp.go.aist.rtm.rtclink.synchronizationframework.LocalObject;
import jp.go.aist.rtm.rtclink.synchronizationframework.SynchronizationManager;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.AttributeMapping;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.ClassMapping;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.ConstructorParamMapping;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.MappingRule;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.OneReferenceMapping;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.ReferenceMapping;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.omg.CosNaming.Binding;
import org.omg.CosNaming.BindingType;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Naming Object Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link jp.go.aist.rtm.rtclink.model.nameservice.impl.NamingObjectNodeImpl#isZombie <em>Zombie</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtclink.model.nameservice.impl.NamingObjectNodeImpl#getEntry <em>Entry</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NamingObjectNodeImpl extends NodeImpl implements NamingObjectNode {
	/**
	 * The default value of the '{@link #isZombie() <em>Zombie</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isZombie()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ZOMBIE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #getEntry() <em>Entry</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getEntry()
	 * @generated
	 * @ordered
	 */
	protected WrapperObject entry = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public NamingObjectNodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return NameservicePackage.eINSTANCE.getNamingObjectNode();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean isZombie() {
		return getEntry() == null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public WrapperObject getEntry() {
		return entry;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEntry(WrapperObject newEntry, NotificationChain msgs) {
		WrapperObject oldEntry = entry;
		entry = newEntry;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, NameservicePackage.NAMING_OBJECT_NODE__ENTRY, oldEntry, newEntry);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setEntry(WrapperObject newEntry) {
		if (newEntry != entry) {
			NotificationChain msgs = null;
			if (entry != null)
				msgs = ((InternalEObject)entry).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - NameservicePackage.NAMING_OBJECT_NODE__ENTRY, null, msgs);
			if (newEntry != null)
				msgs = ((InternalEObject)newEntry).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - NameservicePackage.NAMING_OBJECT_NODE__ENTRY, null, msgs);
			msgs = basicSetEntry(newEntry, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NameservicePackage.NAMING_OBJECT_NODE__ENTRY, newEntry, newEntry));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case NameservicePackage.NAMING_OBJECT_NODE__ENTRY:
					return basicSetEntry(null, msgs);
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
			case NameservicePackage.NAMING_OBJECT_NODE__CONSTRAINT:
				return getConstraint();
			case NameservicePackage.NAMING_OBJECT_NODE__CORBA_OBJECT:
				return getCorbaObject();
			case NameservicePackage.NAMING_OBJECT_NODE__NAME_SERVICE_REFERENCE:
				if (resolve) return getNameServiceReference();
				return basicGetNameServiceReference();
			case NameservicePackage.NAMING_OBJECT_NODE__ZOMBIE:
				return isZombie() ? Boolean.TRUE : Boolean.FALSE;
			case NameservicePackage.NAMING_OBJECT_NODE__ENTRY:
				return getEntry();
		}
		return eDynamicGet(eFeature, resolve);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(EStructuralFeature eFeature, Object newValue) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case NameservicePackage.NAMING_OBJECT_NODE__CONSTRAINT:
				setConstraint((Rectangle)newValue);
				return;
			case NameservicePackage.NAMING_OBJECT_NODE__CORBA_OBJECT:
				setCorbaObject((org.omg.CORBA.Object)newValue);
				return;
			case NameservicePackage.NAMING_OBJECT_NODE__NAME_SERVICE_REFERENCE:
				setNameServiceReference((NameServiceReference)newValue);
				return;
			case NameservicePackage.NAMING_OBJECT_NODE__ENTRY:
				setEntry((WrapperObject)newValue);
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
			case NameservicePackage.NAMING_OBJECT_NODE__CONSTRAINT:
				setConstraint(CONSTRAINT_EDEFAULT);
				return;
			case NameservicePackage.NAMING_OBJECT_NODE__CORBA_OBJECT:
				setCorbaObject(CORBA_OBJECT_EDEFAULT);
				return;
			case NameservicePackage.NAMING_OBJECT_NODE__NAME_SERVICE_REFERENCE:
				setNameServiceReference((NameServiceReference)null);
				return;
			case NameservicePackage.NAMING_OBJECT_NODE__ENTRY:
				setEntry((WrapperObject)null);
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
			case NameservicePackage.NAMING_OBJECT_NODE__CONSTRAINT:
				return CONSTRAINT_EDEFAULT == null ? constraint != null : !CONSTRAINT_EDEFAULT.equals(constraint);
			case NameservicePackage.NAMING_OBJECT_NODE__CORBA_OBJECT:
				return CORBA_OBJECT_EDEFAULT == null ? corbaObject != null : !CORBA_OBJECT_EDEFAULT.equals(corbaObject);
			case NameservicePackage.NAMING_OBJECT_NODE__NAME_SERVICE_REFERENCE:
				return nameServiceReference != null;
			case NameservicePackage.NAMING_OBJECT_NODE__ZOMBIE:
				return isZombie() != ZOMBIE_EDEFAULT;
			case NameservicePackage.NAMING_OBJECT_NODE__ENTRY:
				return entry != null;
		}
		return eDynamicIsSet(eFeature);
	}

	public static final ThreadLocal<NamingObjectNode> PROXY_THREAD_LOCAL = new ThreadLocal<NamingObjectNode>();

	// /**
	// * エントリのオブジェクトを作成する
	// * <p>
	// * エントリのオブジェクトがComponentである場合、pathIDを設定する
	// *
	// * @param remoteObjects
	// * @param nameServiceReference
	// * @return
	// */
	// private static WrapperObject createEntryObject(Object[] remoteObjects,
	// NameServiceReference nameServiceReference) {
	// WrapperObject createWrapperObject = CorbaWrapperFactory.getInstance()
	// .createWrapperObject(remoteObjects[0]);
	//
	// if (createWrapperObject instanceof Component) {
	// ((Component) createWrapperObject).setPathId(nameServiceReference
	// .getPathId());
	// }
	//
	// return createWrapperObject;
	// }

	public static final MappingRule MAPPING_RULE = new MappingRule(
			NodeImpl.MAPPING_RULE,
			new ClassMapping(
					NamingObjectNodeImpl.class,
					new ConstructorParamMapping[] { new ConstructorParamMapping(
							Object.class, CorePackage.eINSTANCE
									.getCorbaWrapperObject_CorbaObject()) },
					true) {
				@Override
				public boolean isTarget(LocalObject parent,
						Object[] remoteObjects, java.lang.Object link) {
					boolean result = false;
					if (link != null && link instanceof Binding) {
						if (((Binding) link).binding_type == BindingType.nobject) {
							result = true;
						}
					}

					return result;
				}

				@Override
				public LocalObject createLocalObject(LocalObject parent,
						Object[] remoteObjects, java.lang.Object link) {
					NamingObjectNode result = (NamingObjectNode) super
							.createLocalObject(parent, remoteObjects, link);

					NameServiceReference createMergedNameServiceReference = ((Node) parent)
							.getNameServiceReference()
							.createMergedNameServiceReference((Binding) link);

					// WrapperObject createWrapperObject = createEntryObject(
					// remoteObjects, createMergedNameServiceReference);
					//
					// result.setEntry(createWrapperObject);
					((Node) result)
							.setNameServiceReference(createMergedNameServiceReference);
					return result;
				}

			}, new AttributeMapping[] { new AttributeMapping(
					CorePackage.eINSTANCE.getCorbaWrapperObject_CorbaObject()) {
				// ゾンビの場合に、新たなオブジェクトに更新されても検出できるようにするための同期。
				// ここで実装するのはあまりきれいではない設計
				// 本来ならば、NamingContextExtで検出するべきだが、
				// ゾンビ化して新たなオブジェクトに更新されてもlinkが全く同じであるため、
				// 違うNamingObjectであると判断されないため、ここに実装することにした。

				public Object getRemoteAttributeValue(LocalObject localObject,
						Object[] remoteObjects) {

					NamingObjectNode namingObjectNode = ((NamingObjectNode) localObject);

					Object result = null;
					if (namingObjectNode.eContainer() != null) {
						Binding binding = namingObjectNode
								.getNameServiceReference().getBinding();
						try {
							result = namingObjectNode
									.eContainer()
									.getCorbaObjectInterface()
									.resolve(
											new NameComponent[] { binding.binding_name[binding.binding_name.length - 1] });
						} catch (NotFound e) {
						} catch (CannotProceed e) {
						} catch (InvalidName e) {
						}
					} else {
						result = namingObjectNode.getCorbaObject();
					}

					return result;
				}
			}

			}, new ReferenceMapping[] {

			new OneReferenceMapping(NameservicePackage.eINSTANCE
					.getNamingObjectNode_Entry()) {
				@Override
				public Object getNewRemoteLink(LocalObject localObject,
						Object[] remoteObjects) {
					return ((NamingObjectNode) localObject)
							.getCorbaBaseObject();
				}

				@Override
				public LocalObject loadLocalObjectByRemoteObject(
						LocalObject localObject,
						SynchronizationManager synchronizationManager,
						java.lang.Object link, Object[] remoteObject) {
					LocalObject result = super.loadLocalObjectByRemoteObject(
							localObject, synchronizationManager, link,
							remoteObject);
					if (result instanceof Component) {
						((Component) result)
								.setPathId(((NamingObjectNode) localObject)
										.getNameServiceReference().getPathId());
					}

					return result;
				}
			} });

} // NamingObjectNodeImpl
