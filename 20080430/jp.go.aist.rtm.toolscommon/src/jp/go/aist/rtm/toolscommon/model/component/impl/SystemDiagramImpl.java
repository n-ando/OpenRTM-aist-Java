/**
 * <copyright>
 * </copyright>
 *
 * $Id: SystemDiagramImpl.java,v 1.16 2008/03/31 04:25:06 yamashita Exp $
 */
package jp.go.aist.rtm.toolscommon.model.component.impl;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import jp.go.aist.rtm.toolscommon.model.component.AbstractComponent;
import jp.go.aist.rtm.toolscommon.model.component.Component;
import jp.go.aist.rtm.toolscommon.model.component.ComponentPackage;
import jp.go.aist.rtm.toolscommon.model.component.Connector;
import jp.go.aist.rtm.toolscommon.model.component.SystemDiagram;
import jp.go.aist.rtm.toolscommon.model.component.SystemDiagramKind;
import jp.go.aist.rtm.toolscommon.model.core.CorbaWrapperObject;
import jp.go.aist.rtm.toolscommon.model.core.ModelElement;
import jp.go.aist.rtm.toolscommon.model.core.impl.ModelElementImpl;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jface.action.Action;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>System Diagram</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.SystemDiagramImpl#getComponents <em>Components</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.SystemDiagramImpl#getKind <em>Kind</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.SystemDiagramImpl#getOpenCompositeComponentAction <em>Open Composite Component Action</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.SystemDiagramImpl#getEditorId <em>Editor Id</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.SystemDiagramImpl#isConnectorProcessing <em>Connector Processing</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.SystemDiagramImpl#getSystemId <em>System Id</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.SystemDiagramImpl#getCreationDate <em>Creation Date</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.SystemDiagramImpl#getUpdateDate <em>Update Date</em>}</li>
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
	 * The default value of the '{@link #getKind() <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected static final SystemDiagramKind KIND_EDEFAULT = SystemDiagramKind.ONLINE_LITERAL;

	/**
	 * The cached value of the '{@link #getKind() <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected SystemDiagramKind kind = KIND_EDEFAULT;

	/**
	 * The default value of the '{@link #getOpenCompositeComponentAction() <em>Open Composite Component Action</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOpenCompositeComponentAction()
	 * @generated
	 * @ordered
	 */
	protected static final Action OPEN_COMPOSITE_COMPONENT_ACTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOpenCompositeComponentAction() <em>Open Composite Component Action</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOpenCompositeComponentAction()
	 * @generated
	 * @ordered
	 */
	protected Action openCompositeComponentAction = OPEN_COMPOSITE_COMPONENT_ACTION_EDEFAULT;

	/**
	 * The default value of the '{@link #getEditorId() <em>Editor Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditorId()
	 * @generated
	 * @ordered
	 */
	protected static final String EDITOR_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEditorId() <em>Editor Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditorId()
	 * @generated
	 * @ordered
	 */
	protected String editorId = EDITOR_ID_EDEFAULT;

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
					List<EObject> components = new ArrayList<EObject>();
					if (notification.getOldValue() instanceof Component) {
						components.add((Component) notification.getOldValue());
						components.addAll(((Component) notification
								.getOldValue()).getComponents());
					}
					for (EObject component : components) {
						for (Iterator iter = component.eAllContents(); iter
								.hasNext();) {
							Object element = iter.next();
							if (element instanceof Connector) {
								connectors.add((Connector) element);
							}
						}
					}

					for (Iterator iter = self.eAllContents(); iter.hasNext();) {
						Object element = iter.next();
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
	@Override
	protected EClass eStaticClass() {
		return ComponentPackage.Literals.SYSTEM_DIAGRAM;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList getComponents() {
		if (components == null) {
			components = new EObjectContainmentEList(AbstractComponent.class, this, ComponentPackage.SYSTEM_DIAGRAM__COMPONENTS);
		}
		return components;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SystemDiagramKind getKind() {
		return kind;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKind(SystemDiagramKind newKind) {
		SystemDiagramKind oldKind = kind;
		kind = newKind == null ? KIND_EDEFAULT : newKind;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.SYSTEM_DIAGRAM__KIND, oldKind, kind));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Action getOpenCompositeComponentAction() {
		return openCompositeComponentAction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOpenCompositeComponentAction(Action newOpenCompositeComponentAction) {
		Action oldOpenCompositeComponentAction = openCompositeComponentAction;
		openCompositeComponentAction = newOpenCompositeComponentAction;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.SYSTEM_DIAGRAM__OPEN_COMPOSITE_COMPONENT_ACTION, oldOpenCompositeComponentAction, openCompositeComponentAction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEditorId() {
		return editorId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEditorId(String newEditorId) {
		String oldEditorId = editorId;
		editorId = newEditorId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.SYSTEM_DIAGRAM__EDITOR_ID, oldEditorId, editorId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isConnectorProcessing() {
		return connectorProcessing;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConnectorProcessing(boolean newConnectorProcessing) {
		boolean oldConnectorProcessing = connectorProcessing;
		connectorProcessing = newConnectorProcessing;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.SYSTEM_DIAGRAM__CONNECTOR_PROCESSING, oldConnectorProcessing, connectorProcessing));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSystemId() {
		return systemId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSystemId(String newSystemId) {
		String oldSystemId = systemId;
		systemId = newSystemId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.SYSTEM_DIAGRAM__SYSTEM_ID, oldSystemId, systemId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCreationDate() {
		return creationDate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCreationDate(String newCreationDate) {
		String oldCreationDate = creationDate;
		creationDate = newCreationDate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.SYSTEM_DIAGRAM__CREATION_DATE, oldCreationDate, creationDate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUpdateDate() {
		return updateDate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUpdateDate(String newUpdateDate) {
		String oldUpdateDate = updateDate;
		updateDate = newUpdateDate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.SYSTEM_DIAGRAM__UPDATE_DATE, oldUpdateDate, updateDate));
	}

	private Timer timer;
	/**
	 * The default value of the '{@link #isConnectorProcessing() <em>Connector Processing</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isConnectorProcessing()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CONNECTOR_PROCESSING_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isConnectorProcessing() <em>Connector Processing</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isConnectorProcessing()
	 * @generated
	 * @ordered
	 */
	protected boolean connectorProcessing = CONNECTOR_PROCESSING_EDEFAULT;

	/**
	 * The default value of the '{@link #getSystemId() <em>System Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSystemId()
	 * @generated
	 * @ordered
	 */
	protected static final String SYSTEM_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSystemId() <em>System Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSystemId()
	 * @generated
	 * @ordered
	 */
	protected String systemId = SYSTEM_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getCreationDate() <em>Creation Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreationDate()
	 * @generated
	 * @ordered
	 */
	protected static final String CREATION_DATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCreationDate() <em>Creation Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreationDate()
	 * @generated
	 * @ordered
	 */
	protected String creationDate = CREATION_DATE_EDEFAULT;

	/**
	 * The default value of the '{@link #getUpdateDate() <em>Update Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpdateDate()
	 * @generated
	 * @ordered
	 */
	protected static final String UPDATE_DATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUpdateDate() <em>Update Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpdateDate()
	 * @generated
	 * @ordered
	 */
	protected String updateDate = UPDATE_DATE_EDEFAULT;

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
						List contents = new ArrayList(eContents());
						for (Iterator iter = contents.iterator(); iter.hasNext();) {
							ModelElement element = (ModelElement) iter.next();
							if (element instanceof CorbaWrapperObject) {
								((CorbaWrapperObject) element)
										.getSynchronizationSupport()
										.synchronizeLocal();
							}
							try {
								if (getOpenCompositeComponentAction() != null) {
									getOpenCompositeComponentAction().runWithEvent(null);
								}
							} catch (Exception e) {
								//void
							}
						}
					} catch (Exception e) {
					}
				}
			}, 0, milliSecond);
		}
	}

	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);
	/**
	 * <!-- begin-user-doc -->
	 * コンポーネンツ変更の通知を行うリスナを登録する
	 * @param listener
	 * 
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	/**
	 * <!-- begin-user-doc -->
	 * コンポーネンツ変更の通知を行うリスナを取得する
	 * @param listener
	 * 
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public PropertyChangeSupport getPropertyChangeSupport() {
		return propertyChangeSupport;
	}

	/**
	 * <!-- begin-user-doc -->
	 * コンポーネンツ変更の通知を行うリスナを削除する
	 * @param listener
	 * 
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ComponentPackage.SYSTEM_DIAGRAM__COMPONENTS:
				return ((InternalEList)getComponents()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ComponentPackage.SYSTEM_DIAGRAM__COMPONENTS:
				return getComponents();
			case ComponentPackage.SYSTEM_DIAGRAM__KIND:
				return getKind();
			case ComponentPackage.SYSTEM_DIAGRAM__OPEN_COMPOSITE_COMPONENT_ACTION:
				return getOpenCompositeComponentAction();
			case ComponentPackage.SYSTEM_DIAGRAM__EDITOR_ID:
				return getEditorId();
			case ComponentPackage.SYSTEM_DIAGRAM__CONNECTOR_PROCESSING:
				return isConnectorProcessing() ? Boolean.TRUE : Boolean.FALSE;
			case ComponentPackage.SYSTEM_DIAGRAM__SYSTEM_ID:
				return getSystemId();
			case ComponentPackage.SYSTEM_DIAGRAM__CREATION_DATE:
				return getCreationDate();
			case ComponentPackage.SYSTEM_DIAGRAM__UPDATE_DATE:
				return getUpdateDate();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ComponentPackage.SYSTEM_DIAGRAM__COMPONENTS:
				getComponents().clear();
				getComponents().addAll((Collection)newValue);
				return;
			case ComponentPackage.SYSTEM_DIAGRAM__KIND:
				setKind((SystemDiagramKind)newValue);
				return;
			case ComponentPackage.SYSTEM_DIAGRAM__OPEN_COMPOSITE_COMPONENT_ACTION:
				setOpenCompositeComponentAction((Action)newValue);
				return;
			case ComponentPackage.SYSTEM_DIAGRAM__EDITOR_ID:
				setEditorId((String)newValue);
				return;
			case ComponentPackage.SYSTEM_DIAGRAM__CONNECTOR_PROCESSING:
				setConnectorProcessing(((Boolean)newValue).booleanValue());
				return;
			case ComponentPackage.SYSTEM_DIAGRAM__SYSTEM_ID:
				setSystemId((String)newValue);
				return;
			case ComponentPackage.SYSTEM_DIAGRAM__CREATION_DATE:
				setCreationDate((String)newValue);
				return;
			case ComponentPackage.SYSTEM_DIAGRAM__UPDATE_DATE:
				setUpdateDate((String)newValue);
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
			case ComponentPackage.SYSTEM_DIAGRAM__COMPONENTS:
				getComponents().clear();
				return;
			case ComponentPackage.SYSTEM_DIAGRAM__KIND:
				setKind(KIND_EDEFAULT);
				return;
			case ComponentPackage.SYSTEM_DIAGRAM__OPEN_COMPOSITE_COMPONENT_ACTION:
				setOpenCompositeComponentAction(OPEN_COMPOSITE_COMPONENT_ACTION_EDEFAULT);
				return;
			case ComponentPackage.SYSTEM_DIAGRAM__EDITOR_ID:
				setEditorId(EDITOR_ID_EDEFAULT);
				return;
			case ComponentPackage.SYSTEM_DIAGRAM__CONNECTOR_PROCESSING:
				setConnectorProcessing(CONNECTOR_PROCESSING_EDEFAULT);
				return;
			case ComponentPackage.SYSTEM_DIAGRAM__SYSTEM_ID:
				setSystemId(SYSTEM_ID_EDEFAULT);
				return;
			case ComponentPackage.SYSTEM_DIAGRAM__CREATION_DATE:
				setCreationDate(CREATION_DATE_EDEFAULT);
				return;
			case ComponentPackage.SYSTEM_DIAGRAM__UPDATE_DATE:
				setUpdateDate(UPDATE_DATE_EDEFAULT);
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
			case ComponentPackage.SYSTEM_DIAGRAM__COMPONENTS:
				return components != null && !components.isEmpty();
			case ComponentPackage.SYSTEM_DIAGRAM__KIND:
				return kind != KIND_EDEFAULT;
			case ComponentPackage.SYSTEM_DIAGRAM__OPEN_COMPOSITE_COMPONENT_ACTION:
				return OPEN_COMPOSITE_COMPONENT_ACTION_EDEFAULT == null ? openCompositeComponentAction != null : !OPEN_COMPOSITE_COMPONENT_ACTION_EDEFAULT.equals(openCompositeComponentAction);
			case ComponentPackage.SYSTEM_DIAGRAM__EDITOR_ID:
				return EDITOR_ID_EDEFAULT == null ? editorId != null : !EDITOR_ID_EDEFAULT.equals(editorId);
			case ComponentPackage.SYSTEM_DIAGRAM__CONNECTOR_PROCESSING:
				return connectorProcessing != CONNECTOR_PROCESSING_EDEFAULT;
			case ComponentPackage.SYSTEM_DIAGRAM__SYSTEM_ID:
				return SYSTEM_ID_EDEFAULT == null ? systemId != null : !SYSTEM_ID_EDEFAULT.equals(systemId);
			case ComponentPackage.SYSTEM_DIAGRAM__CREATION_DATE:
				return CREATION_DATE_EDEFAULT == null ? creationDate != null : !CREATION_DATE_EDEFAULT.equals(creationDate);
			case ComponentPackage.SYSTEM_DIAGRAM__UPDATE_DATE:
				return UPDATE_DATE_EDEFAULT == null ? updateDate != null : !UPDATE_DATE_EDEFAULT.equals(updateDate);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (kind: ");
		result.append(kind);
		result.append(", openCompositeComponentAction: ");
		result.append(openCompositeComponentAction);
		result.append(", editorId: ");
		result.append(editorId);
		result.append(", ConnectorProcessing: ");
		result.append(connectorProcessing);
		result.append(", systemId: ");
		result.append(systemId);
		result.append(", creationDate: ");
		result.append(creationDate);
		result.append(", updateDate: ");
		result.append(updateDate);
		result.append(')');
		return result.toString();
	}

} // SystemDiagramImpl
