/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package jp.go.aist.rtm.rtclink.model.component.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import jp.go.aist.rtm.rtclink.corba.CorbaUtil;
import jp.go.aist.rtm.rtclink.factory.CorbaWrapperFactory;
import jp.go.aist.rtm.rtclink.model.component.ComponentPackage;
import jp.go.aist.rtm.rtclink.model.component.NameValue;
import jp.go.aist.rtm.rtclink.model.core.Rectangle;
import jp.go.aist.rtm.rtclink.model.core.impl.WrapperObjectImpl;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.AttributeMapping;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.ClassMapping;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.ConstructorParamMapping;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.MappingRule;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.ReferenceMapping;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.TypeCodePackage.BadKind;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Name Value</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link jp.go.aist.rtm.rtclink.model.component.impl.NameValueImpl#getName <em>Name</em>}</li>
 *   <li>{@link jp.go.aist.rtm.rtclink.model.component.impl.NameValueImpl#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NameValueImpl extends WrapperObjectImpl implements NameValue {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final Any VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected Any value = VALUE_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public NameValueImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ComponentPackage.eINSTANCE.getNameValue();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.NAME_VALUE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Any getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(Any newValue) {
		Any oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.NAME_VALUE__VALUE, oldValue, value));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case ComponentPackage.NAME_VALUE__CONSTRAINT:
				return getConstraint();
			case ComponentPackage.NAME_VALUE__NAME:
				return getName();
			case ComponentPackage.NAME_VALUE__VALUE:
				return getValue();
		}
		return eDynamicGet(eFeature, resolve);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(EStructuralFeature eFeature, Object newValue) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case ComponentPackage.NAME_VALUE__CONSTRAINT:
				setConstraint((Rectangle)newValue);
				return;
			case ComponentPackage.NAME_VALUE__NAME:
				setName((String)newValue);
				return;
			case ComponentPackage.NAME_VALUE__VALUE:
				setValue((Any)newValue);
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
			case ComponentPackage.NAME_VALUE__CONSTRAINT:
				setConstraint(CONSTRAINT_EDEFAULT);
				return;
			case ComponentPackage.NAME_VALUE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ComponentPackage.NAME_VALUE__VALUE:
				setValue(VALUE_EDEFAULT);
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
			case ComponentPackage.NAME_VALUE__CONSTRAINT:
				return CONSTRAINT_EDEFAULT == null ? constraint != null : !CONSTRAINT_EDEFAULT.equals(constraint);
			case ComponentPackage.NAME_VALUE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ComponentPackage.NAME_VALUE__VALUE:
				return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
		}
		return eDynamicIsSet(eFeature);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", value: ");
		result.append(value);
		result.append(')');
		return result.toString();
	}

	/**
	 * CSVからList<String>を作成する
	 * <p>
	 * csvがnullの場合には空リストを返す
	 * 
	 * @param csv
	 *            csv
	 * @return
	 */
	public static List<String> csv2List(String csv) {
		List<String> result = new ArrayList<String>();
		StringTokenizer tokenizer = new StringTokenizer(csv == null ? "" : csv,
				",");
		while (tokenizer.hasMoreTokens()) {
			result.add(tokenizer.nextToken().trim());
		}

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NameValue == false) {
			return false;
		}

		NameValue n = (NameValue) obj;

		return new EqualsBuilder().append(getName(), n.getName()).isEquals()
				&& getValue().equal(n.getValue()); // equalsではないことに注意すること

	}

	public static final MappingRule MAPPING_RULE = new MappingRule(null,
			new ClassMapping(NameValueImpl.class,
					new ConstructorParamMapping[] {
							new ConstructorParamMapping(
									ComponentPackage.eINSTANCE
											.getNameValue_Name()),
							new ConstructorParamMapping(
									ComponentPackage.eINSTANCE
											.getNameValue_Value()) }) {
			}, new AttributeMapping[] {}, new ReferenceMapping[] {});

	public String getValueAsString() {
		String result = null;
		try {
			if (getValue() != null) {
				result = getValue().extract_string();
			}
		} catch (BAD_OPERATION e) {
			try {
				result = getValue().type().id();
			} catch (BadKind e1) {
				// void
			}
		}

		return result;
	}

	public void setValueAsString(String value) {
		Any any = CorbaUtil.getOrb().create_any();
		any.insert_string(value);
		setValue(any);
	}

	public List<String> getValueAsStringList() {
		return csv2List(getValueAsString());
	}

	/**
	 * SDOのNamevalue配列からListを作成する
	 * 
	 * @param values
	 * @return
	 */
	public static List createNameValueList(_SDOPackage.NameValue[] values) {
		List result = new ArrayList();
		for (_SDOPackage.NameValue value : values) {
			NameValue nameValue = (NameValue) CorbaWrapperFactory.getInstance()
					.createWrapperObject(value.name, value.value);
			result.add(nameValue);
		}

		return result;
	}

	/**
	 * NameValueのリストからnameを基に検索を行う
	 * 
	 * @param nameValues 
	 * @param name　検索キー
	 * @return　検索結果
	 */
	public static NameValue findByName(List nameValues, String name) {
		NameValue result = null;
		for (Iterator iter = nameValues.iterator(); iter.hasNext();) {
			NameValue nameValue = (NameValue) iter.next();
			if (name.equals(nameValue.getName())) {
				result = nameValue;
				break;
			}
		}

		return result;
	}

	/**
	 * ListからSDOのNamevalue配列を作成する
	 * 
	 * @param values
	 * @return
	 */
	public static _SDOPackage.NameValue[] createNameValueArray(List values) {
		List<_SDOPackage.NameValue> result = new ArrayList<_SDOPackage.NameValue>();
		for (Iterator iter = values.iterator(); iter.hasNext();) {
			NameValue nameValue = (NameValue) iter.next();
			result.add(new _SDOPackage.NameValue(nameValue.getName(), nameValue
					.getValue()));
		}

		return result.toArray(new _SDOPackage.NameValue[result.size()]);
	}

} // NameValueImpl
