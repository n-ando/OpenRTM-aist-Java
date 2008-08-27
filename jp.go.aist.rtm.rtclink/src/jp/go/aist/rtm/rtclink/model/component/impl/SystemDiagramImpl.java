/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package jp.go.aist.rtm.rtclink.model.component.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import jp.go.aist.rtm.rtclink.model.component.Component;
import jp.go.aist.rtm.rtclink.model.component.ComponentPackage;
import jp.go.aist.rtm.rtclink.model.component.Connector;
import jp.go.aist.rtm.rtclink.model.component.SystemDiagram;
import jp.go.aist.rtm.rtclink.model.core.CorbaWrapperObject;
import jp.go.aist.rtm.rtclink.model.core.ModelElement;
import jp.go.aist.rtm.rtclink.model.core.Rectangle;
import jp.go.aist.rtm.rtclink.model.core.impl.ModelElementImpl;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>System Diagram</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link jp.go.aist.rtm.rtclink.model.component.impl.SystemDiagramImpl#getComponents <em>Components</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SystemDiagramImpl extends ModelElementImpl implements
		SystemDiagram {

	/**
	 * The cached value of the '{@link #getComponents() <em>Components</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getComponents()
	 * @generated
	 * @ordered
	 */
	protected EList components = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	protected SystemDiagramImpl() {
		super();

		final EObject self = this;

		// コンポーネント削除時に、親がいなくなったconnectorを削除するようにする。
		this.eAdapters().add(new Adapter() {
			public void notifyChanged(Notification notification) {
				if (notification.getFeatureID(SystemDiagram.class) == ComponentPackage.eINSTANCE
						.getSystemDiagram_Components().getFeatureID()
						&& notification.getEventType() == Notification.REMOVE) {
					List<Connector> connectors = new ArrayList<Connector>();
					for (Iterator iter = ((Component) notification
							.getOldValue()).eAllContents(); iter.hasNext();) {
						Object element = (Object) iter.next();
						if (element instanceof Connector) {
							connectors.add((Connector) element);
						}
					}
					for (Iterator iter = self.eAllContents(); iter.hasNext();) {
						Object element = (Object) iter.next();
						if (element instanceof Connector) {
							connectors.add((Connector) element);
						}
					}

					for (Connector connector : connectors) {
						if ((EcoreUtil.isAncestor(self, connector.getSource()) && EcoreUtil
								.isAncestor(self, connector.getTarget())) == false) {
							EcoreUtil.remove(connector);
						}
					}
				}
			}

			public Notifier getTarget() {
				return null;
			}

			public void setTarget(Notifier newTarget) {
			}

			public boolean isAdapterForType(Object type) {
				return false;
			}

		});
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ComponentPackage.eINSTANCE.getSystemDiagram();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList getComponents() {
		if (components == null) {
			components = new EObjectContainmentEList(Component.class, this, ComponentPackage.SYSTEM_DIAGRAM__COMPONENTS);
		}
		return components;
	}

	private Timer timer;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 */
	public void setSynchronizeInterval(long milliSecond) {
		if (timer != null) {
			timer.cancel();
		}
		timer = null;

		if (milliSecond > 0) {
			timer = new Timer(true);

			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					try {
						for (Iterator iter = new ArrayList(eContents())
								.iterator(); iter.hasNext();) {
							ModelElement element = (ModelElement) iter.next();
							if (element instanceof CorbaWrapperObject) {
								((CorbaWrapperObject) element)
										.getSynchronizationSupport()
										.synchronizeLocal();
							}
						}
					} catch (Exception e) {
					}
				}
			}, 0, milliSecond);
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case ComponentPackage.SYSTEM_DIAGRAM__COMPONENTS:
					return ((InternalEList)getComponents()).basicRemove(otherEnd, msgs);
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
			case ComponentPackage.SYSTEM_DIAGRAM__CONSTRAINT:
				return getConstraint();
			case ComponentPackage.SYSTEM_DIAGRAM__COMPONENTS:
				return getComponents();
		}
		return eDynamicGet(eFeature, resolve);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(EStructuralFeature eFeature, Object newValue) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case ComponentPackage.SYSTEM_DIAGRAM__CONSTRAINT:
				setConstraint((Rectangle)newValue);
				return;
			case ComponentPackage.SYSTEM_DIAGRAM__COMPONENTS:
				getComponents().clear();
				getComponents().addAll((Collection)newValue);
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
			case ComponentPackage.SYSTEM_DIAGRAM__CONSTRAINT:
				setConstraint(CONSTRAINT_EDEFAULT);
				return;
			case ComponentPackage.SYSTEM_DIAGRAM__COMPONENTS:
				getComponents().clear();
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
			case ComponentPackage.SYSTEM_DIAGRAM__CONSTRAINT:
				return CONSTRAINT_EDEFAULT == null ? constraint != null : !CONSTRAINT_EDEFAULT.equals(constraint);
			case ComponentPackage.SYSTEM_DIAGRAM__COMPONENTS:
				return components != null && !components.isEmpty();
		}
		return eDynamicIsSet(eFeature);
	}

} // SystemDiagramImpl
