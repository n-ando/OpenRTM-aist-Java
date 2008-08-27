/**
 * <copyright>
 * </copyright>
 *
 * $Id: AbstractComponent.java,v 1.9 2008/03/14 05:35:51 yamashita Exp $
 */
package jp.go.aist.rtm.toolscommon.model.component;

import java.util.List;

import jp.go.aist.rtm.toolscommon.model.core.CorbaWrapperObject;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.action.Action;

import RTC.ComponentProfile;
import RTC.RTObject;
import _SDOPackage.Configuration;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Component</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getSDOConfiguration <em>SDO Configuration</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getConfigurationSets <em>Configuration Sets</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getActiveConfigurationSet <em>Active Configuration Set</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getPorts <em>Ports</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getInports <em>Inports</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getOutports <em>Outports</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getServiceports <em>Serviceports</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getRTCComponentProfile <em>RTC Component Profile</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getInstanceNameL <em>Instance Name L</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getVenderL <em>Vender L</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getDescriptionL <em>Description L</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getCategoryL <em>Category L</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getTypeNameL <em>Type Name L</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getVersionL <em>Version L</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getPathId <em>Path Id</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getOutportDirection <em>Outport Direction</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getComponents <em>Components</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getCompositeComponent <em>Composite Component</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getAllInPorts <em>All In Ports</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getAllOutPorts <em>All Out Ports</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getAllServiceports <em>All Serviceports</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getCompsiteType <em>Compsite Type</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getOpenCompositeComponentAction <em>Open Composite Component Action</em>}</li>
 * </ul>
 * </p>
 *
 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#getAbstractComponent()
 * @model abstract="true"
 * @generated
 */
public interface AbstractComponent extends CorbaWrapperObject {
	/**
	 * Returns the value of the '<em><b>SDO Configuration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>SDO Configuration</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>SDO Configuration</em>' attribute.
	 * @see #setSDOConfiguration(Configuration)
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#getAbstractComponent_SDOConfiguration()
	 * @model dataType="jp.go.aist.rtm.toolscommon.model.component.SDOConfiguration"
	 * @generated
	 */
	Configuration getSDOConfiguration();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getSDOConfiguration <em>SDO Configuration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>SDO Configuration</em>' attribute.
	 * @see #getSDOConfiguration()
	 * @generated
	 */
	void setSDOConfiguration(Configuration value);

	/**
	 * Returns the value of the '<em><b>Configuration Sets</b></em>' containment reference list.
	 * The list contents are of type {@link jp.go.aist.rtm.toolscommon.model.component.ConfigurationSet}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Configuration Sets</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Configuration Sets</em>' containment reference list.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#getAbstractComponent_ConfigurationSets()
	 * @model type="jp.go.aist.rtm.toolscommon.model.component.ConfigurationSet" containment="true"
	 * @generated
	 */
	EList getConfigurationSets();

	/**
	 * Returns the value of the '<em><b>Active Configuration Set</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Active Configuration Set</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Active Configuration Set</em>' reference.
	 * @see #setActiveConfigurationSet(ConfigurationSet)
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#getAbstractComponent_ActiveConfigurationSet()
	 * @model
	 * @generated
	 */
	ConfigurationSet getActiveConfigurationSet();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getActiveConfigurationSet <em>Active Configuration Set</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Active Configuration Set</em>' reference.
	 * @see #getActiveConfigurationSet()
	 * @generated
	 */
	void setActiveConfigurationSet(ConfigurationSet value);

	/**
	 * Returns the value of the '<em><b>Ports</b></em>' containment reference list.
	 * The list contents are of type {@link jp.go.aist.rtm.toolscommon.model.component.Port}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ports</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ports</em>' containment reference list.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#getAbstractComponent_Ports()
	 * @model type="jp.go.aist.rtm.toolscommon.model.component.Port" containment="true"
	 * @generated
	 */
	EList getPorts();

	/**
	 * Returns the value of the '<em><b>Inports</b></em>' reference list.
	 * The list contents are of type {@link jp.go.aist.rtm.toolscommon.model.component.InPort}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inports</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inports</em>' reference list.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#getAbstractComponent_Inports()
	 * @model type="jp.go.aist.rtm.toolscommon.model.component.InPort" transient="true" changeable="false" volatile="true"
	 * @generated
	 */
	EList getInports();

	/**
	 * Returns the value of the '<em><b>Outports</b></em>' reference list.
	 * The list contents are of type {@link jp.go.aist.rtm.toolscommon.model.component.OutPort}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outports</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outports</em>' reference list.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#getAbstractComponent_Outports()
	 * @model type="jp.go.aist.rtm.toolscommon.model.component.OutPort" transient="true" changeable="false" volatile="true"
	 * @generated
	 */
	EList getOutports();

	/**
	 * Returns the value of the '<em><b>Serviceports</b></em>' reference list.
	 * The list contents are of type {@link jp.go.aist.rtm.toolscommon.model.component.ServicePort}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Serviceports</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Serviceports</em>' reference list.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#getAbstractComponent_Serviceports()
	 * @model type="jp.go.aist.rtm.toolscommon.model.component.ServicePort" transient="true" changeable="false" volatile="true"
	 * @generated
	 */
	EList getServiceports();

	/**
	 * Returns the value of the '<em><b>RTC Component Profile</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>RTC Component Profile</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>RTC Component Profile</em>' attribute.
	 * @see #setRTCComponentProfile(ComponentProfile)
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#getAbstractComponent_RTCComponentProfile()
	 * @model dataType="jp.go.aist.rtm.toolscommon.model.component.RTCComponentProfile" transient="true"
	 * @generated
	 */
	ComponentProfile getRTCComponentProfile();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getRTCComponentProfile <em>RTC Component Profile</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>RTC Component Profile</em>' attribute.
	 * @see #getRTCComponentProfile()
	 * @generated
	 */
	void setRTCComponentProfile(ComponentProfile value);

	/**
	 * Returns the value of the '<em><b>Instance Name L</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Instance Name L</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Instance Name L</em>' attribute.
	 * @see #setInstanceNameL(String)
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#getAbstractComponent_InstanceNameL()
	 * @model
	 * @generated
	 */
	String getInstanceNameL();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getInstanceNameL <em>Instance Name L</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Instance Name L</em>' attribute.
	 * @see #getInstanceNameL()
	 * @generated
	 */
	void setInstanceNameL(String value);

	/**
	 * Returns the value of the '<em><b>Vender L</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Vender L</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Vender L</em>' attribute.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#getAbstractComponent_VenderL()
	 * @model transient="true" changeable="false" volatile="true"
	 * @generated
	 */
	String getVenderL();

	/**
	 * Returns the value of the '<em><b>Description L</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description L</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description L</em>' attribute.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#getAbstractComponent_DescriptionL()
	 * @model transient="true" changeable="false" volatile="true"
	 * @generated
	 */
	String getDescriptionL();

	/**
	 * Returns the value of the '<em><b>Category L</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Category L</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Category L</em>' attribute.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#getAbstractComponent_CategoryL()
	 * @model transient="true" changeable="false" volatile="true"
	 * @generated
	 */
	String getCategoryL();

	/**
	 * Returns the value of the '<em><b>Type Name L</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type Name L</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type Name L</em>' attribute.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#getAbstractComponent_TypeNameL()
	 * @model transient="true" changeable="false" volatile="true"
	 * @generated
	 */
	String getTypeNameL();

	/**
	 * Returns the value of the '<em><b>Version L</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version L</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version L</em>' attribute.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#getAbstractComponent_VersionL()
	 * @model transient="true" changeable="false" volatile="true"
	 * @generated
	 */
	String getVersionL();

	/**
	 * Returns the value of the '<em><b>Path Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Path Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Path Id</em>' attribute.
	 * @see #setPathId(String)
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#getAbstractComponent_PathId()
	 * @model
	 * @generated
	 */
	String getPathId();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getPathId <em>Path Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Path Id</em>' attribute.
	 * @see #getPathId()
	 * @generated
	 */
	void setPathId(String value);

	/**
	 * Returns the value of the '<em><b>Outport Direction</b></em>' attribute.
	 * The default value is <code>"1"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outport Direction</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outport Direction</em>' attribute.
	 * @see #setOutportDirection(int)
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#getAbstractComponent_OutportDirection()
	 * @model default="1"
	 * @generated
	 */
	int getOutportDirection();

	String getOutportDirectionStr();
	
	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getOutportDirection <em>Outport Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Outport Direction</em>' attribute.
	 * @see #getOutportDirection()
	 * @generated
	 */
	void setOutportDirection(int value);

	void setOutportDirection(String value);

	/**
	 * Returns the value of the '<em><b>Components</b></em>' reference list.
	 * The list contents are of type {@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent}.
	 * It is bidirectional and its opposite is '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getCompositeComponent <em>Composite Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Components</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Components</em>' reference list.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#getAbstractComponent_Components()
	 * @see jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getCompositeComponent
	 * @model type="jp.go.aist.rtm.toolscommon.model.component.AbstractComponent" opposite="compositeComponent"
	 * @generated
	 */
	EList getComponents();

	/**
	 * Returns the value of the '<em><b>Composite Component</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getComponents <em>Components</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Composite Component</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Composite Component</em>' reference.
	 * @see #setCompositeComponent(AbstractComponent)
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#getAbstractComponent_CompositeComponent()
	 * @see jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getComponents
	 * @model opposite="components"
	 * @generated
	 */
	AbstractComponent getCompositeComponent();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getCompositeComponent <em>Composite Component</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Composite Component</em>' reference.
	 * @see #getCompositeComponent()
	 * @generated
	 */
	void setCompositeComponent(AbstractComponent value);

	/**
	 * Returns the value of the '<em><b>All In Ports</b></em>' reference list.
	 * The list contents are of type {@link jp.go.aist.rtm.toolscommon.model.component.InPort}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>All In Ports</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All In Ports</em>' reference list.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#getAbstractComponent_AllInPorts()
	 * @model type="jp.go.aist.rtm.toolscommon.model.component.InPort" transient="true" changeable="false" volatile="true"
	 * @generated
	 */
	EList getAllInPorts();

	/**
	 * Returns the value of the '<em><b>All Out Ports</b></em>' reference list.
	 * The list contents are of type {@link jp.go.aist.rtm.toolscommon.model.component.OutPort}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>All Out Ports</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All Out Ports</em>' reference list.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#getAbstractComponent_AllOutPorts()
	 * @model type="jp.go.aist.rtm.toolscommon.model.component.OutPort" transient="true" changeable="false" volatile="true"
	 * @generated
	 */
	EList getAllOutPorts();

	/**
	 * Returns the value of the '<em><b>All Serviceports</b></em>' reference list.
	 * The list contents are of type {@link jp.go.aist.rtm.toolscommon.model.component.ServicePort}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>All Serviceports</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All Serviceports</em>' reference list.
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#getAbstractComponent_AllServiceports()
	 * @model type="jp.go.aist.rtm.toolscommon.model.component.ServicePort" transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	EList getAllServiceports();

	/**
	 * Returns the value of the '<em><b>Compsite Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Compsite Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compsite Type</em>' attribute.
	 * @see #setCompsiteType(int)
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#getAbstractComponent_CompsiteType()
	 * @model
	 * @generated
	 */
	int getCompsiteType();

	String getCompsiteTypeStr();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getCompsiteType <em>Compsite Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Compsite Type</em>' attribute.
	 * @see #getCompsiteType()
	 * @generated
	 */
	void setCompsiteType(int value);

	/**
	 * Returns the value of the '<em><b>Open Composite Component Action</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Open Composite Component Action</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Open Composite Component Action</em>' attribute.
	 * @see #setOpenCompositeComponentAction(Action)
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#getAbstractComponent_OpenCompositeComponentAction()
	 * @model dataType="jp.go.aist.rtm.toolscommon.model.component.Action"
	 * @generated
	 */
	Action getOpenCompositeComponentAction();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.component.AbstractComponent#getOpenCompositeComponentAction <em>Open Composite Component Action</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Open Composite Component Action</em>' attribute.
	 * @see #getOpenCompositeComponentAction()
	 * @generated
	 */
	void setOpenCompositeComponentAction(Action value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model listDataType="jp.go.aist.rtm.toolscommon.model.component.List" listMany="false" originallistDataType="jp.go.aist.rtm.toolscommon.model.component.List" originallistMany="false"
	 * @generated
	 */
	boolean updateConfigurationSetListR(List list, ConfigurationSet activeConfigurationSet, List originallist);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="jp.go.aist.rtm.toolscommon.model.component.RTCRTObject"
	 * @generated
	 */
	RTObject getCorbaObjectInterface();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	boolean isCompositeComponent();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="jp.go.aist.rtm.toolscommon.model.component.List" many="false"
	 * @generated NOT
	 */
	EList getAllComponents();

} // AbstractComponent