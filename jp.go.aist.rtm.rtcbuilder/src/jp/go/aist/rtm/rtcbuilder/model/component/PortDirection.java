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
 * A representation of the literals of the enumeration '<em><b>Port Direction</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see jp.go.aist.rtm.rtcbuilder.model.component.ComponentPackage#getPortDirection()
 * @model
 * @generated
 */
public final class PortDirection extends AbstractEnumerator {
	/**
	 * The '<em><b>Left</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Left</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #LEFT_LITERAL
	 * @model name="Left"
	 * @generated
	 * @ordered
	 */
	public static final int LEFT = 0;

	/**
	 * The '<em><b>Right</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Right</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RIGHT_LITERAL
	 * @model name="Right"
	 * @generated
	 * @ordered
	 */
	public static final int RIGHT = 1;

	/**
	 * The '<em><b>Top</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Top</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TOP_LITERAL
	 * @model name="Top"
	 * @generated
	 * @ordered
	 */
	public static final int TOP = 2;

	/**
	 * The '<em><b>Bottom</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Bottom</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BOTTOM_LITERAL
	 * @model name="Bottom"
	 * @generated
	 * @ordered
	 */
	public static final int BOTTOM = 3;

	/**
	 * The '<em><b>Left</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LEFT
	 * @generated
	 * @ordered
	 */
	public static final PortDirection LEFT_LITERAL = new PortDirection(LEFT, "Left", "Left");

	/**
	 * The '<em><b>Right</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RIGHT
	 * @generated
	 * @ordered
	 */
	public static final PortDirection RIGHT_LITERAL = new PortDirection(RIGHT, "Right", "Right");

	/**
	 * The '<em><b>Top</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TOP
	 * @generated
	 * @ordered
	 */
	public static final PortDirection TOP_LITERAL = new PortDirection(TOP, "Top", "Top");

	/**
	 * The '<em><b>Bottom</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BOTTOM
	 * @generated
	 * @ordered
	 */
	public static final PortDirection BOTTOM_LITERAL = new PortDirection(BOTTOM, "Bottom", "Bottom");

	/**
	 * An array of all the '<em><b>Port Direction</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final PortDirection[] VALUES_ARRAY =
		new PortDirection[] {
			LEFT_LITERAL,
			RIGHT_LITERAL,
			TOP_LITERAL,
			BOTTOM_LITERAL,
		};

	/**
	 * A public read-only list of all the '<em><b>Port Direction</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Port Direction</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PortDirection get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PortDirection result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Port Direction</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PortDirection getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PortDirection result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Port Direction</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PortDirection get(int value) {
		switch (value) {
			case LEFT: return LEFT_LITERAL;
			case RIGHT: return RIGHT_LITERAL;
			case TOP: return TOP_LITERAL;
			case BOTTOM: return BOTTOM_LITERAL;
		}
		return null;	
	}

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private PortDirection(int value, String name, String literal) {
		super(value, name, literal);
	}

} //PortDirection
