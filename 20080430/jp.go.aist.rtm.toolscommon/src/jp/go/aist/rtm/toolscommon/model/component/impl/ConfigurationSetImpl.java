/**
 * <copyright>
 * </copyright>
 *
 * $Id: ConfigurationSetImpl.java,v 1.1 2008/01/29 05:52:23 yamashita Exp $
 */
package jp.go.aist.rtm.toolscommon.model.component.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import jp.go.aist.rtm.toolscommon.model.component.ComponentPackage;
import jp.go.aist.rtm.toolscommon.model.component.ConfigurationSet;
import jp.go.aist.rtm.toolscommon.model.component.NameValue;
import jp.go.aist.rtm.toolscommon.model.core.impl.WrapperObjectImpl;
import jp.go.aist.rtm.toolscommon.synchronizationframework.LocalObject;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.AttributeMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ClassMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ConstructorParamMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.MappingRule;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ReferenceMapping;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Configuration Set</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.ConfigurationSetImpl#getId <em>Id</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.ConfigurationSetImpl#getSDOConfigurationSet <em>SDO Configuration Set</em>}</li>
 *   <li>{@link jp.go.aist.rtm.toolscommon.model.component.impl.ConfigurationSetImpl#getConfigurationData <em>Configuration Data</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConfigurationSetImpl extends WrapperObjectImpl implements
        ConfigurationSet {
    /**
     * The default value of the '{@link #getId() <em>Id</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getId()
     * @generated
     * @ordered
     */
    protected static final String ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getId() <em>Id</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getId()
     * @generated
     * @ordered
     */
    protected String id = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getSDOConfigurationSet() <em>SDO Configuration Set</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getSDOConfigurationSet()
	 * @generated
	 * @ordered
	 */
    protected static final _SDOPackage.ConfigurationSet SDO_CONFIGURATION_SET_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSDOConfigurationSet() <em>SDO Configuration Set</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getSDOConfigurationSet()
	 * @generated
	 * @ordered
	 */
    protected _SDOPackage.ConfigurationSet sDOConfigurationSet = SDO_CONFIGURATION_SET_EDEFAULT;

	/**
	 * The cached value of the '{@link #getConfigurationData() <em>Configuration Data</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getConfigurationData()
	 * @generated
	 * @ordered
	 */
    protected EList configurationData= null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public ConfigurationSetImpl() {
        super();
    }

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
				protected EClass eStaticClass() {
		return ComponentPackage.Literals.CONFIGURATION_SET;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.CONFIGURATION_SET__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public _SDOPackage.ConfigurationSet getSDOConfigurationSet() {
		return sDOConfigurationSet;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public void setSDOConfigurationSet(_SDOPackage.ConfigurationSet newSDOConfigurationSet) {
		_SDOPackage.ConfigurationSet oldSDOConfigurationSet = sDOConfigurationSet;
		sDOConfigurationSet = newSDOConfigurationSet;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.CONFIGURATION_SET__SDO_CONFIGURATION_SET, oldSDOConfigurationSet, sDOConfigurationSet));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public EList getConfigurationData() {
		if (configurationData == null) {
			configurationData = new EObjectContainmentEList(NameValue.class, this, ComponentPackage.CONFIGURATION_SET__CONFIGURATION_DATA);
		}
		return configurationData;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ComponentPackage.CONFIGURATION_SET__CONFIGURATION_DATA:
				return ((InternalEList)getConfigurationData()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ComponentPackage.CONFIGURATION_SET__ID:
				return getId();
			case ComponentPackage.CONFIGURATION_SET__SDO_CONFIGURATION_SET:
				return getSDOConfigurationSet();
			case ComponentPackage.CONFIGURATION_SET__CONFIGURATION_DATA:
				return getConfigurationData();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ComponentPackage.CONFIGURATION_SET__ID:
				setId((String)newValue);
				return;
			case ComponentPackage.CONFIGURATION_SET__SDO_CONFIGURATION_SET:
				setSDOConfigurationSet((_SDOPackage.ConfigurationSet)newValue);
				return;
			case ComponentPackage.CONFIGURATION_SET__CONFIGURATION_DATA:
				getConfigurationData().clear();
				getConfigurationData().addAll((Collection)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ComponentPackage.CONFIGURATION_SET__ID:
				setId(ID_EDEFAULT);
				return;
			case ComponentPackage.CONFIGURATION_SET__SDO_CONFIGURATION_SET:
				setSDOConfigurationSet(SDO_CONFIGURATION_SET_EDEFAULT);
				return;
			case ComponentPackage.CONFIGURATION_SET__CONFIGURATION_DATA:
				getConfigurationData().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ComponentPackage.CONFIGURATION_SET__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case ComponentPackage.CONFIGURATION_SET__SDO_CONFIGURATION_SET:
				return SDO_CONFIGURATION_SET_EDEFAULT == null ? sDOConfigurationSet != null : !SDO_CONFIGURATION_SET_EDEFAULT.equals(sDOConfigurationSet);
			case ComponentPackage.CONFIGURATION_SET__CONFIGURATION_DATA:
				return configurationData != null && !configurationData.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
				public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (id: ");
		result.append(id);
		result.append(", sDOConfigurationSet: ");
		result.append(sDOConfigurationSet);
		result.append(')');
		return result.toString();
	}

    //
    // /**
    // * NameValueをMapとして返す。
    // * <p>
    // * このMapを編集することはできない。storeConfigurationDataとremoveConfigurationDataを使用すること
    // *
    // * @return
    // */
    // public Map<String, Any> getKeyAndValueMap() {
    // Map<String, Any> result = new HashMap<String, Any>();
    // for (NameValue nameValue : delegate.configuration_data) {
    // result.put(nameValue.name, nameValue.value);
    // }
    //
    // return Collections.unmodifiableMap(result);
    // }
    //
    // /**
    // * NameValueをListとして返す。
    // * <p>
    // * rtclinkのNameValueクラスであることに注意すること<br>
    // * equals比較することができる
    // *
    // * @return
    // */
    // private List<jp.go.aist.rtm.systemeditor.model.component.NameValue>
    // getKeyAndValueList() {
    // List<jp.go.aist.rtm.systemeditor.model.component.NameValue> result = new
    // ArrayList<jp.go.aist.rtm.systemeditor.model.component.NameValue>();
    // for (NameValue nameValue : delegate.configuration_data) {
    // result
    // .add((jp.go.aist.rtm.systemeditor.model.component.NameValue)
    // CorbaWrapperFactory
    // .getInstance().createWrapperObject(nameValue.name,
    // nameValue.value));
    // }
    //
    // return Collections.unmodifiableList(result);
    // }
    //
    // /**
    // * ConfigurationDataを追加/修正する
    // *
    // * @param name
    // * 追加/修正するConfigurationData名
    // * @param value
    // * 追加/修正するConfigurationData値
    // */
    // public void storeConfigurationData(String name, Any value) {
    // delegate.configuration_data = SDOUtil.storeNameValue(
    // delegate.configuration_data, name, value);
    // }

    /**
     * ConfigurationSetからSDOのConfigurationSetを作成する
     */
	public void setSDOConfigurationSetForRestore(_SDOPackage.ConfigurationSet newSDOConfigurationSet) {
		this.setSDOConfigurationSet(newSDOConfigurationSet);
	    List result = new ArrayList();
		result = NameValueImpl
			.createNameValueList(newSDOConfigurationSet.configuration_data);
		getConfigurationData().addAll(result);
	}

/**
     * ConfigurationSetからSDOのConfigurationSetを作成する
     */
    public static _SDOPackage.ConfigurationSet createSdoConfigurationSet(
            ConfigurationSet local) {
        _SDOPackage.ConfigurationSet result = new _SDOPackage.ConfigurationSet();
        result.id = local.getId();

        result.description = "";
        if (local.getSDOConfigurationSet() != null) {
            result.description = local.getSDOConfigurationSet().description;
        }

        result.configuration_data = NameValueImpl.createNameValueArray(local
                .getConfigurationData());

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ConfigurationSet == false) {
            return false;
        }

        ConfigurationSet p = (ConfigurationSet) obj;

        if (new EqualsBuilder().append(getConfigurationData().toArray(),
                p.getConfigurationData().toArray()).isEquals() == false) {
            new EqualsBuilder().append(getConfigurationData().toArray(),
                    p.getConfigurationData().toArray()).isEquals();
        }

        return new EqualsBuilder().append(getId(), p.getId()).append(
                getConfigurationData().toArray(),
                p.getConfigurationData().toArray()).isEquals();
    }

    public static ConfigurationSet findConfigurationSetByID(String id,
            EList configurationSets) {
        ConfigurationSet result = null;
        for (Iterator iter = configurationSets.iterator(); iter.hasNext();) {
            ConfigurationSet tmp = (ConfigurationSet) iter.next();
            if (id.equals(tmp.getId())) {
                result = tmp;
                break;
            }
        }

        return result;
    }

    public static final MappingRule MAPPING_RULE = new MappingRule(
            null,
            new ClassMapping(
                    ConfigurationSetImpl.class,
                    new ConstructorParamMapping[] { new ConstructorParamMapping(
                            ComponentPackage.eINSTANCE
                                    .getConfigurationSet_SDOConfigurationSet()) }) {
            }, new AttributeMapping[] {
                    new AttributeMapping(ComponentPackage.eINSTANCE
                            .getConfigurationSet_Id(), true) {
                        @Override
                        public Object getRemoteAttributeValue(
                                LocalObject localObject, Object[] remoteObjects) {
                            Object result = null;
                            try {
                                result = ((ConfigurationSet) localObject)
                                        .getSDOConfigurationSet().id;
                            } catch (Exception e) {
                                // void
                            }

                            return result;
                        }
                    },
                    new AttributeMapping(ComponentPackage.eINSTANCE
                            .getConfigurationSet_ConfigurationData(), true) {
                        @Override
                        public Object getRemoteAttributeValue(
                                LocalObject localObject, Object[] remoteObjects) {
                            List result = new ArrayList();
                            try {
                                _SDOPackage.ConfigurationSet configurationSet = ((ConfigurationSet) localObject)
                                        .getSDOConfigurationSet();
                                if (configurationSet != null) {
                                    result = NameValueImpl
                                            .createNameValueList(configurationSet.configuration_data);
                                }
                            } catch (Exception e) {
                                // void
                            }

                            return result;
                        }
                    }, }, new ReferenceMapping[] {});

} // ConfigurationSetImpl
