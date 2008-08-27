/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package jp.go.aist.rtm.rtcbuilder.model.component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.AbstractEnumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Interface Direction</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see jp.go.aist.rtm.rtcbuilder.model.component.ComponentPackage#getInterfaceDirection()
 * @model
 * @generated
 */
public final class InterfaceDirection extends AbstractEnumerator {
	/**
	 * The '<em><b>Provided</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Provided</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PROVIDED_LITERAL
	 * @model name="Provided"
	 * @generated
	 * @ordered
	 */
	public static final int PROVIDED = 0;

	/**
	 * The '<em><b>Required</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Required</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #REQUIRED_LITERAL
	 * @model name="Required"
	 * @generated
	 * @ordered
	 */
	public static final int REQUIRED = 1;

	/**
	 * The '<em><b>Provided</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROVIDED
	 * @generated
	 * @ordered
	 */
	public static final InterfaceDirection PROVIDED_LITERAL = new InterfaceDirection(PROVIDED, "Provided", "Provided");

	/**
	 * The '<em><b>Required</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #REQUIRED
	 * @generated
	 * @ordered
	 */
	public static final InterfaceDirection REQUIRED_LITERAL = new InterfaceDirection(REQUIRED, "Required", "Required");

	/**
	 * An array of all the '<em><b>Interface Direction</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final InterfaceDirection[] VALUES_ARRAY =
		new InterfaceDirection[] {
			PROVIDED_LITERAL,
			REQUIRED_LITERAL,
		};

	/**
	 * A public read-only list of all the '<em><b>Interface Direction</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Interface Direction</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static InterfaceDirection get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			InterfaceDirection result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Interface Direction</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static InterfaceDirection getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			InterfaceDirection result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Interface Direction</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static InterfaceDirection get(int value) {
		switch (value) {
			case PROVIDED: return PROVIDED_LITERAL;
			case REQUIRED: return REQUIRED_LITERAL;
		}
		return null;	
	}

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private InterfaceDirection(int value, String name, String literal) {
		super(value, name, literal);
	}

} //InterfaceDirection
