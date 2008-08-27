/**
 * <copyright>
 * </copyright>
 *
 * $Id: NamingObjectNodeImpl.java,v 1.7 2008/03/27 06:58:52 yamashita Exp $
 */
package jp.go.aist.rtm.nameserviceview.model.nameservice.impl;

import jp.go.aist.rtm.nameserviceview.model.nameservice.NameServiceReference;
import jp.go.aist.rtm.nameserviceview.model.nameservice.NameservicePackage;
import jp.go.aist.rtm.nameserviceview.model.nameservice.NamingObjectNode;
import jp.go.aist.rtm.nameserviceview.model.nameservice.Node;
import jp.go.aist.rtm.toolscommon.model.component.Component;
import jp.go.aist.rtm.toolscommon.model.core.CorePackage;
import jp.go.aist.rtm.toolscommon.model.core.WrapperObject;
import jp.go.aist.rtm.toolscommon.synchronizationframework.LocalObject;
import jp.go.aist.rtm.toolscommon.synchronizationframework.SynchronizationManager;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.AttributeMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ClassMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ConstructorParamMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.MappingRule;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.OneReferenceMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ReferenceMapping;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
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
 *   <li>{@link jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NamingObjectNodeImpl#isZombie <em>Zombie</em>}</li>
 *   <li>{@link jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NamingObjectNodeImpl#getEntry <em>Entry</em>}</li>
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
	 * The cached value of the '{@link #getEntry() <em>Entry</em>}' reference.
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
	@Override
	protected EClass eStaticClass() {
		return NameservicePackage.Literals.NAMING_OBJECT_NODE;
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
	public void setEntry(WrapperObject newEntry) {
		WrapperObject oldEntry = entry;
		entry = newEntry;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NameservicePackage.NAMING_OBJECT_NODE__ENTRY, oldEntry, entry));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case NameservicePackage.NAMING_OBJECT_NODE__ZOMBIE:
				return isZombie() ? Boolean.TRUE : Boolean.FALSE;
			case NameservicePackage.NAMING_OBJECT_NODE__ENTRY:
				return getEntry();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case NameservicePackage.NAMING_OBJECT_NODE__ENTRY:
				setEntry((WrapperObject)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case NameservicePackage.NAMING_OBJECT_NODE__ENTRY:
				setEntry((WrapperObject)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case NameservicePackage.NAMING_OBJECT_NODE__ZOMBIE:
				return isZombie() != ZOMBIE_EDEFAULT;
			case NameservicePackage.NAMING_OBJECT_NODE__ENTRY:
				return entry != null;
		}
		return super.eIsSet(featureID);
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

			},// null
			new ReferenceMapping[] {
					new OneReferenceMapping(NameservicePackage.eINSTANCE
							.getNamingObjectNode_Entry()) {
						@Override
						public Object getNewRemoteLink(LocalObject localObject,
								Object[] remoteObjects) {
							return ((NamingObjectNode) localObject)
									.getCorbaBaseObject();
							// return null;
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
					}
			});
	
	private static final ReferenceMapping getReferenceMapping() {
		OneReferenceMapping mapping = new OneReferenceMapping(NameservicePackage.eINSTANCE
				.getNamingObjectNode_Entry()) {
			@Override
			public Object getNewRemoteLink(LocalObject localObject,
					Object[] remoteObjects) {
				return ((NamingObjectNode) localObject)
						.getCorbaBaseObject();
				// return null;
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
		};
		return mapping;
	}
	public static void synchronizeLocalReference(NamingObjectNode node) {
		if (node.isZombie()) {
			//getReferenceMapping().syncronizeLocal(node);
		}
	}
	
} // NamingObjectNodeImpl
