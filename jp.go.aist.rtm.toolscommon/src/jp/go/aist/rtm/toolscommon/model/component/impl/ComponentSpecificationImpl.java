/**
 * <copyright>
 * </copyright>
 *
 * $Id: ComponentSpecificationImpl.java,v 1.2 2008/03/14 05:35:51 yamashita Exp $
 */
package jp.go.aist.rtm.toolscommon.model.component.impl;

import java.util.List;

import jp.go.aist.rtm.toolscommon.model.component.ComponentPackage;
import jp.go.aist.rtm.toolscommon.model.component.ComponentSpecification;
import jp.go.aist.rtm.toolscommon.model.component.ConfigurationSet;
import jp.go.aist.rtm.toolscommon.ui.propertysource.ComponentSpecificationPropertySource;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Specification</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.ComponentSpecificationImpl#getAliasName <em>Alias Name</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.ComponentSpecificationImpl#isSpecUnLoad <em>Spec Un Load</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.ComponentSpecificationImpl#getComponentId <em>Component Id</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.ComponentSpecificationImpl#getPathURI <em>Path URI</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ComponentSpecificationImpl extends AbstractComponentImpl implements ComponentSpecification {
	/**
	 * The default value of the '{@link #getAliasName() <em>Alias Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAliasName()
	 * @generated
	 * @ordered
	 */
	protected static final String ALIAS_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAliasName() <em>Alias Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAliasName()
	 * @generated
	 * @ordered
	 */
	protected String aliasName = ALIAS_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isSpecUnLoad() <em>Spec Un Load</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSpecUnLoad()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SPEC_UN_LOAD_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isSpecUnLoad() <em>Spec Un Load</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSpecUnLoad()
	 * @generated
	 * @ordered
	 */
	protected boolean specUnLoad = SPEC_UN_LOAD_EDEFAULT;

	/**
	 * The default value of the '{@link #getComponentId() <em>Component Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponentId()
	 * @generated
	 * @ordered
	 */
	protected static final String COMPONENT_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getComponentId() <em>Component Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponentId()
	 * @generated
	 * @ordered
	 */
	protected String componentId = COMPONENT_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getPathURI() <em>Path URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPathURI()
	 * @generated
	 * @ordered
	 */
	protected static final String PATH_URI_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPathURI() <em>Path URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPathURI()
	 * @generated
	 * @ordered
	 */
	protected String pathURI = PATH_URI_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public ComponentSpecificationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ComponentPackage.Literals.COMPONENT_SPECIFICATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAliasName() {
		return aliasName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAliasName(String newAliasName) {
		String oldAliasName = aliasName;
		aliasName = newAliasName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT_SPECIFICATION__ALIAS_NAME, oldAliasName, aliasName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSpecUnLoad() {
		return specUnLoad;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSpecUnLoad(boolean newSpecUnLoad) {
		boolean oldSpecUnLoad = specUnLoad;
		specUnLoad = newSpecUnLoad;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT_SPECIFICATION__SPEC_UN_LOAD, oldSpecUnLoad, specUnLoad));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getComponentId() {
		return componentId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComponentId(String newComponentId) {
		String oldComponentId = componentId;
		componentId = newComponentId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT_SPECIFICATION__COMPONENT_ID, oldComponentId, componentId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPathURI() {
		return pathURI;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPathURI(String newPathURI) {
		String oldPathURI = pathURI;
		pathURI = newPathURI;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT_SPECIFICATION__PATH_URI, oldPathURI, pathURI));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setSpecUnLoad() {
		specUnLoad = true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ComponentPackage.COMPONENT_SPECIFICATION__ALIAS_NAME:
				return getAliasName();
			case ComponentPackage.COMPONENT_SPECIFICATION__SPEC_UN_LOAD:
				return isSpecUnLoad() ? Boolean.TRUE : Boolean.FALSE;
			case ComponentPackage.COMPONENT_SPECIFICATION__COMPONENT_ID:
				return getComponentId();
			case ComponentPackage.COMPONENT_SPECIFICATION__PATH_URI:
				return getPathURI();
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
			case ComponentPackage.COMPONENT_SPECIFICATION__ALIAS_NAME:
				setAliasName((String)newValue);
				return;
			case ComponentPackage.COMPONENT_SPECIFICATION__SPEC_UN_LOAD:
				setSpecUnLoad(((Boolean)newValue).booleanValue());
				return;
			case ComponentPackage.COMPONENT_SPECIFICATION__COMPONENT_ID:
				setComponentId((String)newValue);
				return;
			case ComponentPackage.COMPONENT_SPECIFICATION__PATH_URI:
				setPathURI((String)newValue);
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
			case ComponentPackage.COMPONENT_SPECIFICATION__ALIAS_NAME:
				setAliasName(ALIAS_NAME_EDEFAULT);
				return;
			case ComponentPackage.COMPONENT_SPECIFICATION__SPEC_UN_LOAD:
				setSpecUnLoad(SPEC_UN_LOAD_EDEFAULT);
				return;
			case ComponentPackage.COMPONENT_SPECIFICATION__COMPONENT_ID:
				setComponentId(COMPONENT_ID_EDEFAULT);
				return;
			case ComponentPackage.COMPONENT_SPECIFICATION__PATH_URI:
				setPathURI(PATH_URI_EDEFAULT);
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
			case ComponentPackage.COMPONENT_SPECIFICATION__ALIAS_NAME:
				return ALIAS_NAME_EDEFAULT == null ? aliasName != null : !ALIAS_NAME_EDEFAULT.equals(aliasName);
			case ComponentPackage.COMPONENT_SPECIFICATION__SPEC_UN_LOAD:
				return specUnLoad != SPEC_UN_LOAD_EDEFAULT;
			case ComponentPackage.COMPONENT_SPECIFICATION__COMPONENT_ID:
				return COMPONENT_ID_EDEFAULT == null ? componentId != null : !COMPONENT_ID_EDEFAULT.equals(componentId);
			case ComponentPackage.COMPONENT_SPECIFICATION__PATH_URI:
				return PATH_URI_EDEFAULT == null ? pathURI != null : !PATH_URI_EDEFAULT.equals(pathURI);
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
		result.append(" (aliasName: ");
		result.append(aliasName);
		result.append(", specUnLoad: ");
		result.append(specUnLoad);
		result.append(", componentId: ");
		result.append(componentId);
		result.append(", pathURI: ");
		result.append(pathURI);
		result.append(')');
		return result.toString();
	}

	public boolean updateConfigurationSetListR(List list,
			ConfigurationSet activeConfigurationSet, List originallist) {
		
		getConfigurationSets().clear();
		getConfigurationSets().addAll(list);
		setActiveConfigurationSet(activeConfigurationSet);

		return true;
	}

	public java.lang.Object getAdapter(Class adapter) {
		java.lang.Object result = null;
		if (IPropertySource.class.equals(adapter)) {
			result = new ComponentSpecificationPropertySource(this);
		}

		if (result == null) {
			result = super.getAdapter(adapter);
		}

		return result;
	}
} //ComponentSpecificationImpl