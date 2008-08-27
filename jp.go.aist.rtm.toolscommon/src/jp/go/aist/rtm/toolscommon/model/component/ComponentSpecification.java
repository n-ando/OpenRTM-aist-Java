/**
 * <copyright>
 * </copyright>
 *
 * $Id: ComponentSpecification.java,v 1.2 2008/03/05 12:01:47 terui Exp $
 */
package jp.go.aist.rtm.toolscommon.model.component;


import java.util.List;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Specification</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.ComponentSpecification#getAliasName <em>Alias Name</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.ComponentSpecification#isSpecUnLoad <em>Spec Un Load</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.ComponentSpecification#getComponentId <em>Component Id</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.ComponentSpecification#getPathURI <em>Path URI</em>}</li>
 * </ul>
 * </p>
 *
 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#getComponentSpecification()
 * @model
 * @generated
 */
public interface ComponentSpecification extends AbstractComponent {
	/**
	 * Returns the value of the '<em><b>Alias Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Alias Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Alias Name</em>' attribute.
	 * @see #setAliasName(String)
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#getComponentSpecification_AliasName()
	 * @model
	 * @generated
	 */
	String getAliasName();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.component.ComponentSpecification#getAliasName <em>Alias Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Alias Name</em>' attribute.
	 * @see #getAliasName()
	 * @generated
	 */
	void setAliasName(String value);

	/**
	 * Returns the value of the '<em><b>Spec Un Load</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Spec Un Load</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Spec Un Load</em>' attribute.
	 * @see #setSpecUnLoad(boolean)
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#getComponentSpecification_SpecUnLoad()
	 * @model default="false"
	 * @generated
	 */
	boolean isSpecUnLoad();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.component.ComponentSpecification#isSpecUnLoad <em>Spec Un Load</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Spec Un Load</em>' attribute.
	 * @see #isSpecUnLoad()
	 * @generated
	 */
	void setSpecUnLoad(boolean value);

	/**
	 * Returns the value of the '<em><b>Component Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component Id</em>' attribute.
	 * @see #setComponentId(String)
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#getComponentSpecification_ComponentId()
	 * @model
	 * @generated
	 */
	String getComponentId();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.component.ComponentSpecification#getComponentId <em>Component Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component Id</em>' attribute.
	 * @see #getComponentId()
	 * @generated
	 */
	void setComponentId(String value);

	/**
	 * Returns the value of the '<em><b>Path URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Path URI</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Path URI</em>' attribute.
	 * @see #setPathURI(String)
	 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#getComponentSpecification_PathURI()
	 * @model
	 * @generated
	 */
	String getPathURI();

	/**
	 * Sets the value of the '{@link jp.go.aist.rtm.toolscommon.model.component.ComponentSpecification#getPathURI <em>Path URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Path URI</em>' attribute.
	 * @see #getPathURI()
	 * @generated
	 */
	void setPathURI(String value);

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
	 * @model
	 * @generated
	 */
	void setSpecUnLoad();

} // ComponentSpecification