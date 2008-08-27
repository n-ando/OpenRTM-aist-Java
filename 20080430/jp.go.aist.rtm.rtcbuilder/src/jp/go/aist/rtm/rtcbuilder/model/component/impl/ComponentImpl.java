/**
 * <copyright>
 * </copyright>
 *
 * $Id: ComponentImpl.java,v 1.1 2007/12/25 05:43:03 tsakamoto Exp $
 */
package jp.go.aist.rtm.rtcbuilder.model.component.impl;

import java.util.Collection;

import jp.go.aist.rtm.rtcbuilder.model.component.Component;
import jp.go.aist.rtm.rtcbuilder.model.component.ComponentPackage;
import jp.go.aist.rtm.rtcbuilder.model.component.DataInPort;
import jp.go.aist.rtm.rtcbuilder.model.component.DataOutPort;
import jp.go.aist.rtm.rtcbuilder.model.component.PortBase;
import jp.go.aist.rtm.rtcbuilder.model.component.PortDirection;
import jp.go.aist.rtm.rtcbuilder.model.component.ServiceInterface;
import jp.go.aist.rtm.rtcbuilder.model.component.ServicePort;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Component</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link jp.go.aist.rtm.rtcbuilder.model.component.impl.ComponentImpl#getComponent_Name <em>Component Name</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtcbuilder.model.component.impl.ComponentImpl#getDataInPorts <em>Data In Ports</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtcbuilder.model.component.impl.ComponentImpl#getDataOutPorts <em>Data Out Ports</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtcbuilder.model.component.impl.ComponentImpl#getServicePorts <em>Service Ports</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtcbuilder.model.component.impl.ComponentImpl#getRightMaxNum <em>Right Max Num</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtcbuilder.model.component.impl.ComponentImpl#getLeftMaxNum <em>Left Max Num</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtcbuilder.model.component.impl.ComponentImpl#getTopMaxNum <em>Top Max Num</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtcbuilder.model.component.impl.ComponentImpl#getBottomMaxNum <em>Bottom Max Num</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ComponentImpl extends EObjectImpl implements Component {
	/**
	 * The default value of the '{@link #getComponent_Name() <em>Component Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponent_Name()
	 * @generated
	 * @ordered
	 */
	protected static final String COMPONENT_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getComponent_Name() <em>Component Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponent_Name()
	 * @generated
	 * @ordered
	 */
	protected String component_Name = COMPONENT_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDataInPorts() <em>Data In Ports</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataInPorts()
	 * @generated
	 * @ordered
	 */
	protected EList dataInPorts = null;

	/**
	 * The cached value of the '{@link #getDataOutPorts() <em>Data Out Ports</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataOutPorts()
	 * @generated
	 * @ordered
	 */
	protected EList dataOutPorts = null;

	/**
	 * The cached value of the '{@link #getServicePorts() <em>Service Ports</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getServicePorts()
	 * @generated
	 * @ordered
	 */
	protected EList servicePorts = null;

	/**
	 * The default value of the '{@link #getRightMaxNum() <em>Right Max Num</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRightMaxNum()
	 * @generated
	 * @ordered
	 */
	protected static final int RIGHT_MAX_NUM_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getRightMaxNum() <em>Right Max Num</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRightMaxNum()
	 * @generated
	 * @ordered
	 */
	protected int rightMaxNum = RIGHT_MAX_NUM_EDEFAULT;

	/**
	 * This is true if the Right Max Num attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean rightMaxNumESet = false;

	/**
	 * The default value of the '{@link #getLeftMaxNum() <em>Left Max Num</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLeftMaxNum()
	 * @generated
	 * @ordered
	 */
	protected static final int LEFT_MAX_NUM_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLeftMaxNum() <em>Left Max Num</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLeftMaxNum()
	 * @generated
	 * @ordered
	 */
	protected int leftMaxNum = LEFT_MAX_NUM_EDEFAULT;

	/**
	 * This is true if the Left Max Num attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean leftMaxNumESet = false;

	/**
	 * The default value of the '{@link #getTopMaxNum() <em>Top Max Num</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTopMaxNum()
	 * @generated
	 * @ordered
	 */
	protected static final int TOP_MAX_NUM_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getTopMaxNum() <em>Top Max Num</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTopMaxNum()
	 * @generated
	 * @ordered
	 */
	protected int topMaxNum = TOP_MAX_NUM_EDEFAULT;

	/**
	 * This is true if the Top Max Num attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean topMaxNumESet = false;

	/**
	 * The default value of the '{@link #getBottomMaxNum() <em>Bottom Max Num</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBottomMaxNum()
	 * @generated
	 * @ordered
	 */
	protected static final int BOTTOM_MAX_NUM_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getBottomMaxNum() <em>Bottom Max Num</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBottomMaxNum()
	 * @generated
	 * @ordered
	 */
	protected int bottomMaxNum = BOTTOM_MAX_NUM_EDEFAULT;

	/**
	 * This is true if the Bottom Max Num attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean bottomMaxNumESet = false;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComponentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ComponentPackage.Literals.COMPONENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getComponent_Name() {
		return component_Name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComponent_Name(String newComponent_Name) {
		String oldComponent_Name = component_Name;
		component_Name = newComponent_Name;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT__COMPONENT_NAME, oldComponent_Name, component_Name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getDataInPorts() {
		if (dataInPorts == null) {
			dataInPorts = new EObjectContainmentEList(DataInPort.class, this, ComponentPackage.COMPONENT__DATA_IN_PORTS);
		}
		return dataInPorts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getDataOutPorts() {
		if (dataOutPorts == null) {
			dataOutPorts = new EObjectContainmentEList(DataOutPort.class, this, ComponentPackage.COMPONENT__DATA_OUT_PORTS);
		}
		return dataOutPorts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getServicePorts() {
		if (servicePorts == null) {
			servicePorts = new EObjectContainmentEList(ServicePort.class, this, ComponentPackage.COMPONENT__SERVICE_PORTS);
		}
		return servicePorts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getRightMaxNum() {
		return rightMaxNum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRightMaxNum(int newRightMaxNum) {
		int oldRightMaxNum = rightMaxNum;
		rightMaxNum = newRightMaxNum;
		boolean oldRightMaxNumESet = rightMaxNumESet;
		rightMaxNumESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT__RIGHT_MAX_NUM, oldRightMaxNum, rightMaxNum, !oldRightMaxNumESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetRightMaxNum() {
		int oldRightMaxNum = rightMaxNum;
		boolean oldRightMaxNumESet = rightMaxNumESet;
		rightMaxNum = RIGHT_MAX_NUM_EDEFAULT;
		rightMaxNumESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ComponentPackage.COMPONENT__RIGHT_MAX_NUM, oldRightMaxNum, RIGHT_MAX_NUM_EDEFAULT, oldRightMaxNumESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetRightMaxNum() {
		return rightMaxNumESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getLeftMaxNum() {
		return leftMaxNum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLeftMaxNum(int newLeftMaxNum) {
		int oldLeftMaxNum = leftMaxNum;
		leftMaxNum = newLeftMaxNum;
		boolean oldLeftMaxNumESet = leftMaxNumESet;
		leftMaxNumESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT__LEFT_MAX_NUM, oldLeftMaxNum, leftMaxNum, !oldLeftMaxNumESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetLeftMaxNum() {
		int oldLeftMaxNum = leftMaxNum;
		boolean oldLeftMaxNumESet = leftMaxNumESet;
		leftMaxNum = LEFT_MAX_NUM_EDEFAULT;
		leftMaxNumESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ComponentPackage.COMPONENT__LEFT_MAX_NUM, oldLeftMaxNum, LEFT_MAX_NUM_EDEFAULT, oldLeftMaxNumESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetLeftMaxNum() {
		return leftMaxNumESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getTopMaxNum() {
		return topMaxNum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTopMaxNum(int newTopMaxNum) {
		int oldTopMaxNum = topMaxNum;
		topMaxNum = newTopMaxNum;
		boolean oldTopMaxNumESet = topMaxNumESet;
		topMaxNumESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT__TOP_MAX_NUM, oldTopMaxNum, topMaxNum, !oldTopMaxNumESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetTopMaxNum() {
		int oldTopMaxNum = topMaxNum;
		boolean oldTopMaxNumESet = topMaxNumESet;
		topMaxNum = TOP_MAX_NUM_EDEFAULT;
		topMaxNumESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ComponentPackage.COMPONENT__TOP_MAX_NUM, oldTopMaxNum, TOP_MAX_NUM_EDEFAULT, oldTopMaxNumESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetTopMaxNum() {
		return topMaxNumESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getBottomMaxNum() {
		return bottomMaxNum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBottomMaxNum(int newBottomMaxNum) {
		int oldBottomMaxNum = bottomMaxNum;
		bottomMaxNum = newBottomMaxNum;
		boolean oldBottomMaxNumESet = bottomMaxNumESet;
		bottomMaxNumESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT__BOTTOM_MAX_NUM, oldBottomMaxNum, bottomMaxNum, !oldBottomMaxNumESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetBottomMaxNum() {
		int oldBottomMaxNum = bottomMaxNum;
		boolean oldBottomMaxNumESet = bottomMaxNumESet;
		bottomMaxNum = BOTTOM_MAX_NUM_EDEFAULT;
		bottomMaxNumESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ComponentPackage.COMPONENT__BOTTOM_MAX_NUM, oldBottomMaxNum, BOTTOM_MAX_NUM_EDEFAULT, oldBottomMaxNumESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetBottomMaxNum() {
		return bottomMaxNumESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void addDataInport(DataInPort inport) {
		sortIndex(inport);
		getDataInPorts().add(inport);
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT__DATA_IN_PORTS, null, inport));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void addDataOutport(DataOutPort outport) {
		sortIndex(outport);
		getDataOutPorts().add(outport);
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT__DATA_OUT_PORTS, null, outport));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void addServiceport(ServicePort serviceport) {
		sortIndex(serviceport);
		getServicePorts().add(serviceport);
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT__SERVICE_PORTS, null, serviceport));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void clearDataOutports() {
		EList oldList = this.dataOutPorts;
		this.dataOutPorts = null;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT__DATA_OUT_PORTS, oldList, this.dataOutPorts));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void clearServiceports() {
		EList oldList = this.servicePorts;
		this.servicePorts = null;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT__SERVICE_PORTS, oldList, this.servicePorts));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void clearDataInports() {
		EList oldList = this.dataInPorts;
		this.dataInPorts = new EObjectContainmentEList(DataInPort.class, this, ComponentPackage.COMPONENT__DATA_IN_PORTS);
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT__DATA_IN_PORTS, oldList, this.dataInPorts ));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ComponentPackage.COMPONENT__DATA_IN_PORTS:
				return ((InternalEList)getDataInPorts()).basicRemove(otherEnd, msgs);
			case ComponentPackage.COMPONENT__DATA_OUT_PORTS:
				return ((InternalEList)getDataOutPorts()).basicRemove(otherEnd, msgs);
			case ComponentPackage.COMPONENT__SERVICE_PORTS:
				return ((InternalEList)getServicePorts()).basicRemove(otherEnd, msgs);
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
			case ComponentPackage.COMPONENT__COMPONENT_NAME:
				return getComponent_Name();
			case ComponentPackage.COMPONENT__DATA_IN_PORTS:
				return getDataInPorts();
			case ComponentPackage.COMPONENT__DATA_OUT_PORTS:
				return getDataOutPorts();
			case ComponentPackage.COMPONENT__SERVICE_PORTS:
				return getServicePorts();
			case ComponentPackage.COMPONENT__RIGHT_MAX_NUM:
				return new Integer(getRightMaxNum());
			case ComponentPackage.COMPONENT__LEFT_MAX_NUM:
				return new Integer(getLeftMaxNum());
			case ComponentPackage.COMPONENT__TOP_MAX_NUM:
				return new Integer(getTopMaxNum());
			case ComponentPackage.COMPONENT__BOTTOM_MAX_NUM:
				return new Integer(getBottomMaxNum());
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
			case ComponentPackage.COMPONENT__COMPONENT_NAME:
				setComponent_Name((String)newValue);
				return;
			case ComponentPackage.COMPONENT__DATA_IN_PORTS:
				getDataInPorts().clear();
				getDataInPorts().addAll((Collection)newValue);
				return;
			case ComponentPackage.COMPONENT__DATA_OUT_PORTS:
				getDataOutPorts().clear();
				getDataOutPorts().addAll((Collection)newValue);
				return;
			case ComponentPackage.COMPONENT__SERVICE_PORTS:
				getServicePorts().clear();
				getServicePorts().addAll((Collection)newValue);
				return;
			case ComponentPackage.COMPONENT__RIGHT_MAX_NUM:
				setRightMaxNum(((Integer)newValue).intValue());
				return;
			case ComponentPackage.COMPONENT__LEFT_MAX_NUM:
				setLeftMaxNum(((Integer)newValue).intValue());
				return;
			case ComponentPackage.COMPONENT__TOP_MAX_NUM:
				setTopMaxNum(((Integer)newValue).intValue());
				return;
			case ComponentPackage.COMPONENT__BOTTOM_MAX_NUM:
				setBottomMaxNum(((Integer)newValue).intValue());
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
			case ComponentPackage.COMPONENT__COMPONENT_NAME:
				setComponent_Name(COMPONENT_NAME_EDEFAULT);
				return;
			case ComponentPackage.COMPONENT__DATA_IN_PORTS:
				getDataInPorts().clear();
				return;
			case ComponentPackage.COMPONENT__DATA_OUT_PORTS:
				getDataOutPorts().clear();
				return;
			case ComponentPackage.COMPONENT__SERVICE_PORTS:
				getServicePorts().clear();
				return;
			case ComponentPackage.COMPONENT__RIGHT_MAX_NUM:
				unsetRightMaxNum();
				return;
			case ComponentPackage.COMPONENT__LEFT_MAX_NUM:
				unsetLeftMaxNum();
				return;
			case ComponentPackage.COMPONENT__TOP_MAX_NUM:
				unsetTopMaxNum();
				return;
			case ComponentPackage.COMPONENT__BOTTOM_MAX_NUM:
				unsetBottomMaxNum();
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
			case ComponentPackage.COMPONENT__COMPONENT_NAME:
				return COMPONENT_NAME_EDEFAULT == null ? component_Name != null : !COMPONENT_NAME_EDEFAULT.equals(component_Name);
			case ComponentPackage.COMPONENT__DATA_IN_PORTS:
				return dataInPorts != null && !dataInPorts.isEmpty();
			case ComponentPackage.COMPONENT__DATA_OUT_PORTS:
				return dataOutPorts != null && !dataOutPorts.isEmpty();
			case ComponentPackage.COMPONENT__SERVICE_PORTS:
				return servicePorts != null && !servicePorts.isEmpty();
			case ComponentPackage.COMPONENT__RIGHT_MAX_NUM:
				return isSetRightMaxNum();
			case ComponentPackage.COMPONENT__LEFT_MAX_NUM:
				return isSetLeftMaxNum();
			case ComponentPackage.COMPONENT__TOP_MAX_NUM:
				return isSetTopMaxNum();
			case ComponentPackage.COMPONENT__BOTTOM_MAX_NUM:
				return isSetBottomMaxNum();
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
		result.append(" (Component_Name: ");
		result.append(component_Name);
		result.append(", rightMaxNum: ");
		if (rightMaxNumESet) result.append(rightMaxNum); else result.append("<unset>");
		result.append(", leftMaxNum: ");
		if (leftMaxNumESet) result.append(leftMaxNum); else result.append("<unset>");
		result.append(", topMaxNum: ");
		if (topMaxNumESet) result.append(topMaxNum); else result.append("<unset>");
		result.append(", bottomMaxNum: ");
		if (bottomMaxNumESet) result.append(bottomMaxNum); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

	private void sortIndex(PortBase addition) {
		int indexes[] = { 0, 0, 0, 0};
		
		getDataInPorts();
		for( int intidx=0;intidx<dataInPorts.size();intidx++ ) {
			DataInPort inport = (DataInPort)dataInPorts.get(intidx);
			inport.setIndex(indexes[inport.getDirection().getValue()]);
			indexes[inport.getDirection().getValue()]++;
		}
		//
		getDataOutPorts();
		for( int intidx=0;intidx<dataOutPorts.size();intidx++ ) {
			DataOutPort outport = (DataOutPort)dataOutPorts.get(intidx);
			outport.setIndex(indexes[outport.getDirection().getValue()]);
			indexes[outport.getDirection().getValue()]++;
		}
		//
		getServicePorts();
		for( int intidx=0;intidx<servicePorts.size();intidx++ ) {
			ServicePort serviceport = (ServicePort)servicePorts.get(intidx);
			int delta = serviceport.getServiceInterfaces().size();
			if( delta>1 )
				indexes[serviceport.getDirection().getValue()] = indexes[serviceport.getDirection().getValue()] + delta - 1;
			serviceport.setIndex(indexes[serviceport.getDirection().getValue()]-delta/2);
			indexes[serviceport.getDirection().getValue()]++;
//			indexes[serviceport.getDirection().getValue()] = indexes[serviceport.getDirection().getValue()]+delta;
		}
		//
		if( addition instanceof ServicePort ) {
			ServicePort serviceport = (ServicePort)addition;
			int delta = serviceport.getServiceInterfaces().size();
			if( delta>1 )
				indexes[serviceport.getDirection().getValue()] = indexes[serviceport.getDirection().getValue()] + delta - 1;
			serviceport.setIndex(indexes[serviceport.getDirection().getValue()]-delta/2);
			indexes[serviceport.getDirection().getValue()]++;
		} else {
			addition.setIndex(indexes[addition.getDirection().getValue()]);
			indexes[addition.getDirection().getValue()]++;
		}
		//
		this.leftMaxNum = indexes[PortDirection.LEFT];
		this.rightMaxNum = indexes[PortDirection.RIGHT];
		this.topMaxNum = indexes[PortDirection.TOP];
		this.bottomMaxNum = indexes[PortDirection.BOTTOM];
	}

} //ComponentImpl