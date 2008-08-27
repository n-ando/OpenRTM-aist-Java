/**
 * <copyright>
 * </copyright>
 *
 * $Id: SystemDiagramKind.java,v 1.1 2008/03/04 12:47:29 terui Exp $
 */
package jp.go.aist.rtm.toolscommon.model.component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.AbstractEnumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>System Diagram Kind</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see jp.go.aist.rtm.toolscommon.model.component.ComponentPackage#getSystemDiagramKind()
 * @model
 * @generated
 */
public final class SystemDiagramKind extends AbstractEnumerator {
	/**
	 * The '<em><b>ONLINE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>ONLINE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ONLINE_LITERAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int ONLINE = 1;

	/**
	 * The '<em><b>OFFLINE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>OFFLINE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #OFFLINE_LITERAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int OFFLINE = 2;

	/**
	 * The '<em><b>ONLINE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ONLINE
	 * @generated
	 * @ordered
	 */
	public static final SystemDiagramKind ONLINE_LITERAL = new SystemDiagramKind(ONLINE, "ONLINE", "ONLINE");

	/**
	 * The '<em><b>OFFLINE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OFFLINE
	 * @generated
	 * @ordered
	 */
	public static final SystemDiagramKind OFFLINE_LITERAL = new SystemDiagramKind(OFFLINE, "OFFLINE", "OFFLINE");

	/**
	 * An array of all the '<em><b>System Diagram Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final SystemDiagramKind[] VALUES_ARRAY =
		new SystemDiagramKind[] {
			ONLINE_LITERAL,
			OFFLINE_LITERAL,
		};

	/**
	 * A public read-only list of all the '<em><b>System Diagram Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>System Diagram Kind</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SystemDiagramKind get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			SystemDiagramKind result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>System Diagram Kind</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SystemDiagramKind getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			SystemDiagramKind result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>System Diagram Kind</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SystemDiagramKind get(int value) {
		switch (value) {
			case ONLINE: return ONLINE_LITERAL;
			case OFFLINE: return OFFLINE_LITERAL;
		}
		return null;	
	}

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private SystemDiagramKind(int value, String name, String literal) {
		super(value, name, literal);
	}

} //SystemDiagramKind
