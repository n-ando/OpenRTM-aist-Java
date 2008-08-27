/**
 * <copyright>
 * </copyright>
 *
 * $Id: AbstractComponentImpl.java,v 1.10 2008/03/14 05:35:51 yamashita Exp $
 */
package jp.go.aist.rtm.toolscommon.model.component.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import jp.go.aist.rtm.toolscommon.model.component.AbstractComponent;
import jp.go.aist.rtm.toolscommon.model.component.Component;
import jp.go.aist.rtm.toolscommon.model.component.ComponentPackage;
import jp.go.aist.rtm.toolscommon.model.component.ConfigurationSet;
import jp.go.aist.rtm.toolscommon.model.component.InPort;
import jp.go.aist.rtm.toolscommon.model.component.OutPort;
import jp.go.aist.rtm.toolscommon.model.component.Port;
import jp.go.aist.rtm.toolscommon.model.component.ServicePort;
import jp.go.aist.rtm.toolscommon.model.core.impl.CorbaWrapperObjectImpl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jface.action.Action;

import RTC.ComponentProfile;
import RTC.RTObject;
import _SDOPackage.Configuration;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Abstract Component</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.AbstractComponentImpl#getSDOConfiguration <em>SDO Configuration</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.AbstractComponentImpl#getConfigurationSets <em>Configuration Sets</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.AbstractComponentImpl#getActiveConfigurationSet <em>Active Configuration Set</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.AbstractComponentImpl#getPorts <em>Ports</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.AbstractComponentImpl#getInports <em>Inports</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.AbstractComponentImpl#getOutports <em>Outports</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.AbstractComponentImpl#getServiceports <em>Serviceports</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.AbstractComponentImpl#getRTCComponentProfile <em>RTC Component Profile</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.AbstractComponentImpl#getInstanceNameL <em>Instance Name L</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.AbstractComponentImpl#getVenderL <em>Vender L</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.AbstractComponentImpl#getDescriptionL <em>Description L</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.AbstractComponentImpl#getCategoryL <em>Category L</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.AbstractComponentImpl#getTypeNameL <em>Type Name L</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.AbstractComponentImpl#getVersionL <em>Version L</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.AbstractComponentImpl#getPathId <em>Path Id</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.AbstractComponentImpl#getOutportDirection <em>Outport Direction</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.AbstractComponentImpl#getComponents <em>Components</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.AbstractComponentImpl#getCompositeComponent <em>Composite Component</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.AbstractComponentImpl#getAllInPorts <em>All In Ports</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.AbstractComponentImpl#getAllOutPorts <em>All Out Ports</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.AbstractComponentImpl#getAllServiceports <em>All Serviceports</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.AbstractComponentImpl#getCompsiteType <em>Compsite Type</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.AbstractComponentImpl#getOpenCompositeComponentAction <em>Open Composite Component Action</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class AbstractComponentImpl extends CorbaWrapperObjectImpl
		implements AbstractComponent {
	/**
	 * The default value of the '{@link #getSDOConfiguration() <em>SDO Configuration</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getSDOConfiguration()
	 * @generated
	 * @ordered
	 */
	protected static final Configuration SDO_CONFIGURATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSDOConfiguration() <em>SDO Configuration</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getSDOConfiguration()
	 * @generated
	 * @ordered
	 */
	protected Configuration sDOConfiguration = SDO_CONFIGURATION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getConfigurationSets() <em>Configuration Sets</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getConfigurationSets()
	 * @generated
	 * @ordered
	 */
	protected EList configurationSets = null;

	/**
	 * The cached value of the '{@link #getActiveConfigurationSet() <em>Active Configuration Set</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getActiveConfigurationSet()
	 * @generated
	 * @ordered
	 */
	protected ConfigurationSet activeConfigurationSet = null;

	/**
	 * The cached value of the '{@link #getPorts() <em>Ports</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getPorts()
	 * @generated
	 * @ordered
	 */
	protected EList ports = null;

	/**
	 * The default value of the '{@link #getRTCComponentProfile() <em>RTC Component Profile</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getRTCComponentProfile()
	 * @generated
	 * @ordered
	 */
	protected static final ComponentProfile RTC_COMPONENT_PROFILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRTCComponentProfile() <em>RTC Component Profile</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getRTCComponentProfile()
	 * @generated
	 * @ordered
	 */
	protected ComponentProfile rTCComponentProfile = RTC_COMPONENT_PROFILE_EDEFAULT;

	/**
	 * The default value of the '{@link #getInstanceNameL() <em>Instance Name L</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getInstanceNameL()
	 * @generated
	 * @ordered
	 */
	protected static final String INSTANCE_NAME_L_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInstanceNameL() <em>Instance Name L</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getInstanceNameL()
	 * @generated
	 * @ordered
	 */
	protected String instanceNameL = INSTANCE_NAME_L_EDEFAULT;

	/**
	 * The default value of the '{@link #getVenderL() <em>Vender L</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getVenderL()
	 * @generated
	 * @ordered
	 */
	protected static final String VENDER_L_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getDescriptionL() <em>Description L</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getDescriptionL()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_L_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getCategoryL() <em>Category L</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getCategoryL()
	 * @generated
	 * @ordered
	 */
	protected static final String CATEGORY_L_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getTypeNameL() <em>Type Name L</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getTypeNameL()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_NAME_L_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getVersionL() <em>Version L</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getVersionL()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_L_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getPathId() <em>Path Id</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getPathId()
	 * @generated
	 * @ordered
	 */
	protected static final String PATH_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPathId() <em>Path Id</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getPathId()
	 * @generated
	 * @ordered
	 */
	protected String pathId = PATH_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getOutportDirection() <em>Outport Direction</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getOutportDirection()
	 * @generated
	 * @ordered
	 */
	protected static final int OUTPORT_DIRECTION_EDEFAULT = 1;

	/**
	 * The cached value of the '{@link #getOutportDirection() <em>Outport Direction</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getOutportDirection()
	 * @generated
	 * @ordered
	 */
	protected int outportDirection = OUTPORT_DIRECTION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getComponents() <em>Components</em>}' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getComponents()
	 * @generated
	 * @ordered
	 */
	protected EList components = null;

	/**
	 * The cached value of the '{@link #getCompositeComponent() <em>Composite Component</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getCompositeComponent()
	 * @generated
	 * @ordered
	 */
	protected AbstractComponent compositeComponent = null;

	/**
	 * The default value of the '{@link #getCompsiteType() <em>Compsite Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getCompsiteType()
	 * @generated
	 * @ordered
	 */
	protected static final int COMPSITE_TYPE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getCompsiteType() <em>Compsite Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getCompsiteType()
	 * @generated
	 * @ordered
	 */
	protected int compsiteType = COMPSITE_TYPE_EDEFAULT;

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

	private static final String OUTPORT_DIRECTION_RIGHT_LITERAL = "right";
	private static final String OUTPORT_DIRECTION_LEFT_LITERAL = "left";
	private static final String OUTPORT_DIRECTION_UP_LITERAL = "up";
	private static final String OUTPORT_DIRECTION_DOWN_LITERAL = "down";
	
	private static final int OUTPORT_DIRECTION_RIGHT = 1;
	private static final int OUTPORT_DIRECTION_LEFT = 2;
	private static final int OUTPORT_DIRECTION_UP = 3;
	private static final int OUTPORT_DIRECTION_DOWN = 4;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractComponentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ComponentPackage.Literals.ABSTRACT_COMPONENT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Configuration getSDOConfiguration() {
		return sDOConfiguration;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setSDOConfiguration(Configuration newSDOConfiguration) {
		Configuration oldSDOConfiguration = sDOConfiguration;
		sDOConfiguration = newSDOConfiguration;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.ABSTRACT_COMPONENT__SDO_CONFIGURATION, oldSDOConfiguration, sDOConfiguration));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList getConfigurationSets() {
		if (configurationSets == null) {
			configurationSets = new EObjectContainmentEList(ConfigurationSet.class, this, ComponentPackage.ABSTRACT_COMPONENT__CONFIGURATION_SETS);
		}
		return configurationSets;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ConfigurationSet getActiveConfigurationSet() {
		if (activeConfigurationSet != null && activeConfigurationSet.eIsProxy()) {
			InternalEObject oldActiveConfigurationSet = (InternalEObject)activeConfigurationSet;
			activeConfigurationSet = (ConfigurationSet)eResolveProxy(oldActiveConfigurationSet);
			if (activeConfigurationSet != oldActiveConfigurationSet) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ComponentPackage.ABSTRACT_COMPONENT__ACTIVE_CONFIGURATION_SET, oldActiveConfigurationSet, activeConfigurationSet));
			}
		}
		return activeConfigurationSet;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ConfigurationSet basicGetActiveConfigurationSet() {
		return activeConfigurationSet;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setActiveConfigurationSet(ConfigurationSet newActiveConfigurationSet) {
		ConfigurationSet oldActiveConfigurationSet = activeConfigurationSet;
		activeConfigurationSet = newActiveConfigurationSet;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.ABSTRACT_COMPONENT__ACTIVE_CONFIGURATION_SET, oldActiveConfigurationSet, activeConfigurationSet));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList getPorts() {
		if (ports == null) {
			ports = new EObjectContainmentEList(Port.class, this, ComponentPackage.ABSTRACT_COMPONENT__PORTS);
		}
		return ports;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList getInports() {
		EList result = new BasicEList();
		for (Iterator iter = getPorts().iterator(); iter.hasNext();) {
			Port port = (Port) iter.next();
			if (port instanceof InPort) {
				result.add(port);
			}
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList getOutports() {
		EList result = new BasicEList();
		for (Iterator iter = getPorts().iterator(); iter.hasNext();) {
			Port port = (Port) iter.next();
			if (port instanceof OutPort) {
				result.add(port);
			}
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList getServiceports() {
		EList result = new BasicEList();
		for (Iterator iter = getPorts().iterator(); iter.hasNext();) {
			Port port = (Port) iter.next();
			if (port instanceof ServicePort) {
				result.add(port);
			}
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ComponentProfile getRTCComponentProfile() {
		return rTCComponentProfile;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setRTCComponentProfile(ComponentProfile newRTCComponentProfile) {
		ComponentProfile oldRTCComponentProfile = rTCComponentProfile;
		rTCComponentProfile = newRTCComponentProfile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.ABSTRACT_COMPONENT__RTC_COMPONENT_PROFILE, oldRTCComponentProfile, rTCComponentProfile));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String getInstanceNameL() {
		return instanceNameL;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setInstanceNameL(String newInstanceNameL) {
		String oldInstanceNameL = instanceNameL;
		instanceNameL = newInstanceNameL;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.ABSTRACT_COMPONENT__INSTANCE_NAME_L, oldInstanceNameL, instanceNameL));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public String getVenderL() {
		return getRTCComponentProfile() == null ? null
				: getRTCComponentProfile().vendor;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public String getTypeNameL() {
		return getRTCComponentProfile() == null ? null
				: getRTCComponentProfile().type_name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public String getVersionL() {
		return getRTCComponentProfile() == null ? null
				: getRTCComponentProfile().version;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public String getDescriptionL() {
		return getRTCComponentProfile() == null ? null
				: getRTCComponentProfile().description;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public String getCategoryL() {
		return getRTCComponentProfile() == null ? null
				: getRTCComponentProfile().category;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String getPathId() {
		return pathId;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setPathId(String newPathId) {
		String oldPathId = pathId;
		pathId = newPathId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.ABSTRACT_COMPONENT__PATH_ID, oldPathId, pathId));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public int getOutportDirection() {
		return outportDirection;
	}

	public String getOutportDirectionStr() {
		String result = "";
		
		if(outportDirection==OUTPORT_DIRECTION_LEFT) {
			result = OUTPORT_DIRECTION_LEFT_LITERAL;
		} else if(outportDirection==OUTPORT_DIRECTION_UP) {
			result = OUTPORT_DIRECTION_UP_LITERAL;
		} else if(outportDirection==OUTPORT_DIRECTION_DOWN) {
				result = OUTPORT_DIRECTION_DOWN_LITERAL;
		} else {
			result = OUTPORT_DIRECTION_RIGHT_LITERAL;
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setOutportDirection(int newOutportDirection) {
		int oldOutportDirection = outportDirection;
		outportDirection = newOutportDirection;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.ABSTRACT_COMPONENT__OUTPORT_DIRECTION, oldOutportDirection, outportDirection));
	}

	public void setOutportDirection(String newOutportDirection) {
		if(newOutportDirection.toLowerCase().equals("left")) {
			setOutportDirection(0);
		} else	if(newOutportDirection.toLowerCase().equals("right")) {
			setOutportDirection(1);
		} else	if(newOutportDirection.toLowerCase().equals("up")) {
			setOutportDirection(2);
		} else	if(newOutportDirection.toLowerCase().equals("down")) {
			setOutportDirection(3);
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList getComponents() {
		if (components == null) {
			components = new EObjectWithInverseResolvingEList(AbstractComponent.class, this, ComponentPackage.ABSTRACT_COMPONENT__COMPONENTS, ComponentPackage.ABSTRACT_COMPONENT__COMPOSITE_COMPONENT);
		}
		return components;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public AbstractComponent getCompositeComponent() {
		if (compositeComponent != null && compositeComponent.eIsProxy()) {
			InternalEObject oldCompositeComponent = (InternalEObject)compositeComponent;
			compositeComponent = (AbstractComponent)eResolveProxy(oldCompositeComponent);
			if (compositeComponent != oldCompositeComponent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ComponentPackage.ABSTRACT_COMPONENT__COMPOSITE_COMPONENT, oldCompositeComponent, compositeComponent));
			}
		}
		return compositeComponent;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public AbstractComponent basicGetCompositeComponent() {
		return compositeComponent;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCompositeComponent(AbstractComponent newCompositeComponent, NotificationChain msgs) {
		AbstractComponent oldCompositeComponent = compositeComponent;
		compositeComponent = newCompositeComponent;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ComponentPackage.ABSTRACT_COMPONENT__COMPOSITE_COMPONENT, oldCompositeComponent, newCompositeComponent);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setCompositeComponent(AbstractComponent newCompositeComponent) {
		if (newCompositeComponent != compositeComponent) {
			NotificationChain msgs = null;
			if (compositeComponent != null)
				msgs = ((InternalEObject)compositeComponent).eInverseRemove(this, ComponentPackage.ABSTRACT_COMPONENT__COMPONENTS, AbstractComponent.class, msgs);
			if (newCompositeComponent != null)
				msgs = ((InternalEObject)newCompositeComponent).eInverseAdd(this, ComponentPackage.ABSTRACT_COMPONENT__COMPONENTS, AbstractComponent.class, msgs);
			msgs = basicSetCompositeComponent(newCompositeComponent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.ABSTRACT_COMPONENT__COMPOSITE_COMPONENT, newCompositeComponent, newCompositeComponent));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList getAllInPorts() {
		EList result = new BasicEList();
		result.addAll(getInports());
		for (Iterator iter = getAllComponents().iterator(); iter.hasNext();){
			AbstractComponent component = (AbstractComponent)iter.next();
			if (!component.isCompositeComponent()) {
				result.addAll(component.getAllInPorts());	
			} else {
				result.addAll(component.getInports());
			}
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList getAllOutPorts() {
		EList result = new BasicEList();
		result.addAll(getOutports());
		for (Iterator iter = getAllComponents().iterator(); iter.hasNext();){
			AbstractComponent component = (AbstractComponent)iter.next();
			if (!component.isCompositeComponent()) {
				result.addAll(component.getAllOutPorts());	
			} else {
				result.addAll(component.getOutports());
			}
		}

		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList getAllServiceports() {
		EList result = new BasicEList();
		result.addAll(getServiceports());
		for (Iterator iter = getAllComponents().iterator(); iter.hasNext();){
			AbstractComponent component = (AbstractComponent)iter.next();
			if (!component.isCompositeComponent()) {
				result.addAll(component.getAllServiceports());	
			} else {
				result.addAll(component.getServiceports());
			}
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public int getCompsiteType() {
		return compsiteType;
	}

	public String getCompsiteTypeStr() {
		String result = "";
		if( compsiteType==4 ) {
			result = "Choice";
		} else if( compsiteType==3 ) {
			result = "NonShared";
		} else if( compsiteType==2 ) {
			result = "ECShared";
		} else if( compsiteType==1 ) {
			result = "AllShared";
		} else {
			result = "None";
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setCompsiteType(int newCompsiteType) {
		int oldCompsiteType = compsiteType;
		compsiteType = newCompsiteType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.ABSTRACT_COMPONENT__COMPSITE_TYPE, oldCompsiteType, compsiteType));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.ABSTRACT_COMPONENT__OPEN_COMPOSITE_COMPONENT_ACTION, oldOpenCompositeComponentAction, openCompositeComponentAction));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean updateConfigurationSetListR(List list,
			ConfigurationSet activeConfigurationSet, List originallist) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public RTObject getCorbaObjectInterface() {
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean isCompositeComponent() {
		return this.compsiteType == Component.COMPOSITE_COMPONENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList getAllComponents() {
		EList result = new BasicEList();
		result.addAll(getComponents());
		for (Iterator iterator = getComponents().iterator(); iterator.hasNext(); ) {
			AbstractComponent component = (AbstractComponent) iterator.next();
			if (!component.equals(this)) {
				result.addAll(component.getAllComponents());
			}
		}
		
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ComponentPackage.ABSTRACT_COMPONENT__COMPONENTS:
				return ((InternalEList)getComponents()).basicAdd(otherEnd, msgs);
			case ComponentPackage.ABSTRACT_COMPONENT__COMPOSITE_COMPONENT:
				if (compositeComponent != null)
					msgs = ((InternalEObject)compositeComponent).eInverseRemove(this, ComponentPackage.ABSTRACT_COMPONENT__COMPONENTS, AbstractComponent.class, msgs);
				return basicSetCompositeComponent((AbstractComponent)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ComponentPackage.ABSTRACT_COMPONENT__CONFIGURATION_SETS:
				return ((InternalEList)getConfigurationSets()).basicRemove(otherEnd, msgs);
			case ComponentPackage.ABSTRACT_COMPONENT__PORTS:
				return ((InternalEList)getPorts()).basicRemove(otherEnd, msgs);
			case ComponentPackage.ABSTRACT_COMPONENT__COMPONENTS:
				return ((InternalEList)getComponents()).basicRemove(otherEnd, msgs);
			case ComponentPackage.ABSTRACT_COMPONENT__COMPOSITE_COMPONENT:
				return basicSetCompositeComponent(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ComponentPackage.ABSTRACT_COMPONENT__SDO_CONFIGURATION:
				return getSDOConfiguration();
			case ComponentPackage.ABSTRACT_COMPONENT__CONFIGURATION_SETS:
				return getConfigurationSets();
			case ComponentPackage.ABSTRACT_COMPONENT__ACTIVE_CONFIGURATION_SET:
				if (resolve) return getActiveConfigurationSet();
				return basicGetActiveConfigurationSet();
			case ComponentPackage.ABSTRACT_COMPONENT__PORTS:
				return getPorts();
			case ComponentPackage.ABSTRACT_COMPONENT__INPORTS:
				return getInports();
			case ComponentPackage.ABSTRACT_COMPONENT__OUTPORTS:
				return getOutports();
			case ComponentPackage.ABSTRACT_COMPONENT__SERVICEPORTS:
				return getServiceports();
			case ComponentPackage.ABSTRACT_COMPONENT__RTC_COMPONENT_PROFILE:
				return getRTCComponentProfile();
			case ComponentPackage.ABSTRACT_COMPONENT__INSTANCE_NAME_L:
				return getInstanceNameL();
			case ComponentPackage.ABSTRACT_COMPONENT__VENDER_L:
				return getVenderL();
			case ComponentPackage.ABSTRACT_COMPONENT__DESCRIPTION_L:
				return getDescriptionL();
			case ComponentPackage.ABSTRACT_COMPONENT__CATEGORY_L:
				return getCategoryL();
			case ComponentPackage.ABSTRACT_COMPONENT__TYPE_NAME_L:
				return getTypeNameL();
			case ComponentPackage.ABSTRACT_COMPONENT__VERSION_L:
				return getVersionL();
			case ComponentPackage.ABSTRACT_COMPONENT__PATH_ID:
				return getPathId();
			case ComponentPackage.ABSTRACT_COMPONENT__OUTPORT_DIRECTION:
				return new Integer(getOutportDirection());
			case ComponentPackage.ABSTRACT_COMPONENT__COMPONENTS:
				return getComponents();
			case ComponentPackage.ABSTRACT_COMPONENT__COMPOSITE_COMPONENT:
				if (resolve) return getCompositeComponent();
				return basicGetCompositeComponent();
			case ComponentPackage.ABSTRACT_COMPONENT__ALL_IN_PORTS:
				return getAllInPorts();
			case ComponentPackage.ABSTRACT_COMPONENT__ALL_OUT_PORTS:
				return getAllOutPorts();
			case ComponentPackage.ABSTRACT_COMPONENT__ALL_SERVICEPORTS:
				return getAllServiceports();
			case ComponentPackage.ABSTRACT_COMPONENT__COMPSITE_TYPE:
				return new Integer(getCompsiteType());
			case ComponentPackage.ABSTRACT_COMPONENT__OPEN_COMPOSITE_COMPONENT_ACTION:
				return getOpenCompositeComponentAction();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ComponentPackage.ABSTRACT_COMPONENT__SDO_CONFIGURATION:
				setSDOConfiguration((Configuration)newValue);
				return;
			case ComponentPackage.ABSTRACT_COMPONENT__CONFIGURATION_SETS:
				getConfigurationSets().clear();
				getConfigurationSets().addAll((Collection)newValue);
				return;
			case ComponentPackage.ABSTRACT_COMPONENT__ACTIVE_CONFIGURATION_SET:
				setActiveConfigurationSet((ConfigurationSet)newValue);
				return;
			case ComponentPackage.ABSTRACT_COMPONENT__PORTS:
				getPorts().clear();
				getPorts().addAll((Collection)newValue);
				return;
			case ComponentPackage.ABSTRACT_COMPONENT__RTC_COMPONENT_PROFILE:
				setRTCComponentProfile((ComponentProfile)newValue);
				return;
			case ComponentPackage.ABSTRACT_COMPONENT__INSTANCE_NAME_L:
				setInstanceNameL((String)newValue);
				return;
			case ComponentPackage.ABSTRACT_COMPONENT__PATH_ID:
				setPathId((String)newValue);
				return;
			case ComponentPackage.ABSTRACT_COMPONENT__OUTPORT_DIRECTION:
				setOutportDirection(((Integer)newValue).intValue());
				return;
			case ComponentPackage.ABSTRACT_COMPONENT__COMPONENTS:
				getComponents().clear();
				getComponents().addAll((Collection)newValue);
				return;
			case ComponentPackage.ABSTRACT_COMPONENT__COMPOSITE_COMPONENT:
				setCompositeComponent((AbstractComponent)newValue);
				return;
			case ComponentPackage.ABSTRACT_COMPONENT__COMPSITE_TYPE:
				setCompsiteType(((Integer)newValue).intValue());
				return;
			case ComponentPackage.ABSTRACT_COMPONENT__OPEN_COMPOSITE_COMPONENT_ACTION:
				setOpenCompositeComponentAction((Action)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(int featureID) {
		switch (featureID) {
			case ComponentPackage.ABSTRACT_COMPONENT__SDO_CONFIGURATION:
				setSDOConfiguration(SDO_CONFIGURATION_EDEFAULT);
				return;
			case ComponentPackage.ABSTRACT_COMPONENT__CONFIGURATION_SETS:
				getConfigurationSets().clear();
				return;
			case ComponentPackage.ABSTRACT_COMPONENT__ACTIVE_CONFIGURATION_SET:
				setActiveConfigurationSet((ConfigurationSet)null);
				return;
			case ComponentPackage.ABSTRACT_COMPONENT__PORTS:
				getPorts().clear();
				return;
			case ComponentPackage.ABSTRACT_COMPONENT__RTC_COMPONENT_PROFILE:
				setRTCComponentProfile(RTC_COMPONENT_PROFILE_EDEFAULT);
				return;
			case ComponentPackage.ABSTRACT_COMPONENT__INSTANCE_NAME_L:
				setInstanceNameL(INSTANCE_NAME_L_EDEFAULT);
				return;
			case ComponentPackage.ABSTRACT_COMPONENT__PATH_ID:
				setPathId(PATH_ID_EDEFAULT);
				return;
			case ComponentPackage.ABSTRACT_COMPONENT__OUTPORT_DIRECTION:
				setOutportDirection(OUTPORT_DIRECTION_EDEFAULT);
				return;
			case ComponentPackage.ABSTRACT_COMPONENT__COMPONENTS:
				getComponents().clear();
				return;
			case ComponentPackage.ABSTRACT_COMPONENT__COMPOSITE_COMPONENT:
				setCompositeComponent((AbstractComponent)null);
				return;
			case ComponentPackage.ABSTRACT_COMPONENT__COMPSITE_TYPE:
				setCompsiteType(COMPSITE_TYPE_EDEFAULT);
				return;
			case ComponentPackage.ABSTRACT_COMPONENT__OPEN_COMPOSITE_COMPONENT_ACTION:
				setOpenCompositeComponentAction(OPEN_COMPOSITE_COMPONENT_ACTION_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ComponentPackage.ABSTRACT_COMPONENT__SDO_CONFIGURATION:
				return SDO_CONFIGURATION_EDEFAULT == null ? sDOConfiguration != null : !SDO_CONFIGURATION_EDEFAULT.equals(sDOConfiguration);
			case ComponentPackage.ABSTRACT_COMPONENT__CONFIGURATION_SETS:
				return configurationSets != null && !configurationSets.isEmpty();
			case ComponentPackage.ABSTRACT_COMPONENT__ACTIVE_CONFIGURATION_SET:
				return activeConfigurationSet != null;
			case ComponentPackage.ABSTRACT_COMPONENT__PORTS:
				return ports != null && !ports.isEmpty();
			case ComponentPackage.ABSTRACT_COMPONENT__INPORTS:
				return !getInports().isEmpty();
			case ComponentPackage.ABSTRACT_COMPONENT__OUTPORTS:
				return !getOutports().isEmpty();
			case ComponentPackage.ABSTRACT_COMPONENT__SERVICEPORTS:
				return !getServiceports().isEmpty();
			case ComponentPackage.ABSTRACT_COMPONENT__RTC_COMPONENT_PROFILE:
				return RTC_COMPONENT_PROFILE_EDEFAULT == null ? rTCComponentProfile != null : !RTC_COMPONENT_PROFILE_EDEFAULT.equals(rTCComponentProfile);
			case ComponentPackage.ABSTRACT_COMPONENT__INSTANCE_NAME_L:
				return INSTANCE_NAME_L_EDEFAULT == null ? instanceNameL != null : !INSTANCE_NAME_L_EDEFAULT.equals(instanceNameL);
			case ComponentPackage.ABSTRACT_COMPONENT__VENDER_L:
				return VENDER_L_EDEFAULT == null ? getVenderL() != null : !VENDER_L_EDEFAULT.equals(getVenderL());
			case ComponentPackage.ABSTRACT_COMPONENT__DESCRIPTION_L:
				return DESCRIPTION_L_EDEFAULT == null ? getDescriptionL() != null : !DESCRIPTION_L_EDEFAULT.equals(getDescriptionL());
			case ComponentPackage.ABSTRACT_COMPONENT__CATEGORY_L:
				return CATEGORY_L_EDEFAULT == null ? getCategoryL() != null : !CATEGORY_L_EDEFAULT.equals(getCategoryL());
			case ComponentPackage.ABSTRACT_COMPONENT__TYPE_NAME_L:
				return TYPE_NAME_L_EDEFAULT == null ? getTypeNameL() != null : !TYPE_NAME_L_EDEFAULT.equals(getTypeNameL());
			case ComponentPackage.ABSTRACT_COMPONENT__VERSION_L:
				return VERSION_L_EDEFAULT == null ? getVersionL() != null : !VERSION_L_EDEFAULT.equals(getVersionL());
			case ComponentPackage.ABSTRACT_COMPONENT__PATH_ID:
				return PATH_ID_EDEFAULT == null ? pathId != null : !PATH_ID_EDEFAULT.equals(pathId);
			case ComponentPackage.ABSTRACT_COMPONENT__OUTPORT_DIRECTION:
				return outportDirection != OUTPORT_DIRECTION_EDEFAULT;
			case ComponentPackage.ABSTRACT_COMPONENT__COMPONENTS:
				return components != null && !components.isEmpty();
			case ComponentPackage.ABSTRACT_COMPONENT__COMPOSITE_COMPONENT:
				return compositeComponent != null;
			case ComponentPackage.ABSTRACT_COMPONENT__ALL_IN_PORTS:
				return !getAllInPorts().isEmpty();
			case ComponentPackage.ABSTRACT_COMPONENT__ALL_OUT_PORTS:
				return !getAllOutPorts().isEmpty();
			case ComponentPackage.ABSTRACT_COMPONENT__ALL_SERVICEPORTS:
				return !getAllServiceports().isEmpty();
			case ComponentPackage.ABSTRACT_COMPONENT__COMPSITE_TYPE:
				return compsiteType != COMPSITE_TYPE_EDEFAULT;
			case ComponentPackage.ABSTRACT_COMPONENT__OPEN_COMPOSITE_COMPONENT_ACTION:
				return OPEN_COMPOSITE_COMPONENT_ACTION_EDEFAULT == null ? openCompositeComponentAction != null : !OPEN_COMPOSITE_COMPONENT_ACTION_EDEFAULT.equals(openCompositeComponentAction);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (sDOConfiguration: ");
		result.append(sDOConfiguration);
		result.append(", rTCComponentProfile: ");
		result.append(rTCComponentProfile);
		result.append(", instanceNameL: ");
		result.append(instanceNameL);
		result.append(", pathId: ");
		result.append(pathId);
		result.append(", outportDirection: ");
		result.append(outportDirection);
		result.append(", compsiteType: ");
		result.append(compsiteType);
		result.append(", openCompositeComponentAction: ");
		result.append(openCompositeComponentAction);
		result.append(')');
		return result.toString();
	}

} // AbstractComponentImpl
