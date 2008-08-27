/**
 * <copyright>
 * </copyright>
 *
 * $Id: NamingContextNodeImpl.java,v 1.5 2008/01/28 09:44:51 yamashita Exp $
 */
package jp.go.aist.rtm.nameserviceview.model.nameservice.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import jp.go.aist.rtm.nameserviceview.model.nameservice.NameservicePackage;
import jp.go.aist.rtm.nameserviceview.model.nameservice.NamingContextNode;
import jp.go.aist.rtm.nameserviceview.model.nameservice.Node;
import jp.go.aist.rtm.toolscommon.corba.CorbaUtil;
import jp.go.aist.rtm.toolscommon.model.core.CorePackage;
import jp.go.aist.rtm.toolscommon.synchronizationframework.LocalObject;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.AttributeMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ClassMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ConstructorParamMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ManyReferenceMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.MappingRule;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ReferenceMapping;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.omg.CosNaming.Binding;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.AlreadyBound;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Naming Context Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NamingContextNodeImpl#getNodes <em>Nodes</em>}</li>
 * <li>{@link jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NamingContextNodeImpl#isZombie <em>Zombie</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class NamingContextNodeImpl extends NodeImpl implements
		NamingContextNode {
	/**
	 * The cached value of the '{@link #getNodes() <em>Nodes</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getNodes()
	 * @generated
	 * @ordered
	 */
	protected EList nodes = null;

	/**
	 * The default value of the '{@link #isZombie() <em>Zombie</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isZombie()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ZOMBIE_EDEFAULT = false;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public NamingContextNodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return NameservicePackage.Literals.NAMING_CONTEXT_NODE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList getNodes() {
		if (nodes == null) {
			nodes = new EObjectContainmentEList(Node.class, this,
					NameservicePackage.NAMING_CONTEXT_NODE__NODES);
		}
		return nodes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isZombie() {
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
		case NameservicePackage.NAMING_CONTEXT_NODE__NODES:
			return ((InternalEList) getNodes()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case NameservicePackage.NAMING_CONTEXT_NODE__NODES:
			return getNodes();
		case NameservicePackage.NAMING_CONTEXT_NODE__ZOMBIE:
			return isZombie() ? Boolean.TRUE : Boolean.FALSE;
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case NameservicePackage.NAMING_CONTEXT_NODE__NODES:
			getNodes().clear();
			getNodes().addAll((Collection) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case NameservicePackage.NAMING_CONTEXT_NODE__NODES:
			getNodes().clear();
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case NameservicePackage.NAMING_CONTEXT_NODE__NODES:
			return nodes != null && !nodes.isEmpty();
		case NameservicePackage.NAMING_CONTEXT_NODE__ZOMBIE:
			return isZombie() != ZOMBIE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	private Binding createLastBinding(Binding binding) {
		Binding result = new Binding();
		result.binding_type = binding.binding_type;
		result.binding_name = new NameComponent[] { binding.binding_name[binding.binding_name.length - 1] };

		return result;
	}

	public static final MappingRule MAPPING_RULE = new MappingRule(
			NodeImpl.MAPPING_RULE,
			new ClassMapping(
					NamingContextNodeImpl.class,
					new ConstructorParamMapping[] { new ConstructorParamMapping(
							NamingContextExt.class, CorePackage.eINSTANCE
									.getCorbaWrapperObject_CorbaObject()) }) {
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
			}, new AttributeMapping[] {},
			new ReferenceMapping[] { new ManyReferenceMapping(
					NameservicePackage.eINSTANCE.getNamingContextNode_Nodes(),
					true) {

				@Override
				public List getNewRemoteLinkList(Object[] remoteObjects) {
					List<Binding> bindingList = CorbaUtil
							.getBindingList((NamingContextExt) remoteObjects[0]);

					return bindingList;
				}

				public boolean isLinkEquals(java.lang.Object o1,
						java.lang.Object o2) {
					return NodeImpl.COMARATOR.compare((Binding) o1,
							(Binding) o2) == 0;
				}

				@Override
				public Object[] getRemoteObjectByRemoteLink(
						LocalObject localObject, Object[] remoteObjects,
						java.lang.Object link) {
					try {
						return new Object[] { ((NamingContextExt) remoteObjects[0])
								.resolve(((Binding) link).binding_name) };
					} catch (NotFound e) {
						throw new RuntimeException(); // systemError
					} catch (CannotProceed e) {
						throw new RuntimeException(); // systemError
					} catch (InvalidName e) {
						throw new RuntimeException(); // systemError
					}
				}

				@Override
				public List getOldRemoteLinkList(LocalObject localObject) {
					List<Binding> result = new ArrayList<Binding>();
					for (Iterator iter = ((EList) localObject
							.eGet(getLocalFeature())).iterator(); iter
							.hasNext();) {
						Node bindNode = (Node) iter.next();

						Binding binding = bindNode.getNameServiceReference()
								.getBinding();

						Binding lastBinding = new Binding();
						lastBinding.binding_name = new NameComponent[] { binding.binding_name[binding.binding_name.length - 1] };
						lastBinding.binding_type = binding.binding_type;

						result.add(lastBinding);
					}

					return result;
				}

				@Override
				public LocalObject getLocalObjectByRemoteLink(
						LocalObject parent, Object link) {
					LocalObject result = null;
					for (Iterator iter = ((EList) parent
							.eGet(getLocalFeature())).iterator(); iter
							.hasNext();) {
						Node bindNode = (Node) iter.next();

						Binding binding = bindNode.getNameServiceReference()
								.getBinding();

						Binding lastBinding = new Binding();
						lastBinding.binding_name = new NameComponent[] { binding.binding_name[binding.binding_name.length - 1] };
						lastBinding.binding_type = binding.binding_type;

						if (isLinkEquals(link, lastBinding)) {
							result = bindNode;
							break;
						}
					}

					return result;
				}

			} });

	public NamingContextExt getCorbaObjectInterface() {
		return NamingContextExtHelper.narrow(super.getCorbaObject());
	}

	public void createContextR(NameComponent[] path) throws NotFound,
			AlreadyBound, CannotProceed, InvalidName {
		getCorbaObjectInterface().bind_new_context(path);

	}

	public void createNamingObjectR(NameComponent[] path,
			org.omg.CORBA.Object object) throws NotFound, AlreadyBound,
			CannotProceed, InvalidName {
		getCorbaObjectInterface().bind(path, object);
	}
} // NamingContextNodeImpl
