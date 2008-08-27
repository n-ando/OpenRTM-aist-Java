/**
 * <copyright>
 * </copyright>
 *
 * $Id: CorePackage.java,v 1.2 2008/03/04 12:47:29 terui Exp $
 */
package jp.go.aist.rtm.toolscommon.model.core;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains
 * accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see jp.go.aist.rtm.toolscommon.model.core.CoreFactory
 * @model kind="package"
 * @generated
 */
public interface CorePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "core";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 */
	String eNS_URI = "http://rtm.aist.go.jp/toolscommon/model/toolscommon";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "jp.go.aist.rtm.toolscommon.model.core";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	CorePackage eINSTANCE = jp.go.aist.rtm.toolscommon.model.core.impl.CorePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.core.runtime.IAdaptable <em>IAdaptable</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.core.runtime.IAdaptable
	 * @see jp.go.aist.rtm.toolscommon.model.core.impl.CorePackageImpl#getIAdaptable()
	 * @generated
	 */
	int IADAPTABLE = 2;

	/**
	 * The number of structural features of the '<em>IAdaptable</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IADAPTABLE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.toolscommon.model.core.impl.ModelElementImpl <em>Model Element</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.toolscommon.model.core.impl.ModelElementImpl
	 * @see jp.go.aist.rtm.toolscommon.model.core.impl.CorePackageImpl#getModelElement()
	 * @generated
	 */
	int MODEL_ELEMENT = 1;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT__CONSTRAINT = IADAPTABLE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Model Element</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT_FEATURE_COUNT = IADAPTABLE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.toolscommon.model.core.impl.WrapperObjectImpl <em>Wrapper Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.toolscommon.model.core.impl.WrapperObjectImpl
	 * @see jp.go.aist.rtm.toolscommon.model.core.impl.CorePackageImpl#getWrapperObject()
	 * @generated
	 */
	int WRAPPER_OBJECT = 4;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WRAPPER_OBJECT__CONSTRAINT = MODEL_ELEMENT__CONSTRAINT;

	/**
	 * The number of structural features of the '<em>Wrapper Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WRAPPER_OBJECT_FEATURE_COUNT = MODEL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.toolscommon.model.core.impl.CorbaWrapperObjectImpl <em>Corba Wrapper Object</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.toolscommon.model.core.impl.CorbaWrapperObjectImpl
	 * @see jp.go.aist.rtm.toolscommon.model.core.impl.CorePackageImpl#getCorbaWrapperObject()
	 * @generated
	 */
	int CORBA_WRAPPER_OBJECT = 0;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CORBA_WRAPPER_OBJECT__CONSTRAINT = WRAPPER_OBJECT__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Corba Object</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORBA_WRAPPER_OBJECT__CORBA_OBJECT = WRAPPER_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Corba Wrapper Object</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORBA_WRAPPER_OBJECT_FEATURE_COUNT = WRAPPER_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.toolscommon.synchronizationframework.LocalObject <em>Local Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.toolscommon.synchronizationframework.LocalObject
	 * @see jp.go.aist.rtm.toolscommon.model.core.impl.CorePackageImpl#getLocalObject()
	 * @generated
	 */
	int LOCAL_OBJECT = 3;

	/**
	 * The number of structural features of the '<em>Local Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_OBJECT_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link jp.go.aist.rtm.toolscommon.model.core.impl.UnknownObjectImpl <em>Unknown Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.toolscommon.model.core.impl.UnknownObjectImpl
	 * @see jp.go.aist.rtm.toolscommon.model.core.impl.CorePackageImpl#getUnknownObject()
	 * @generated
	 */
	int UNKNOWN_OBJECT = 5;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_OBJECT__CONSTRAINT = WRAPPER_OBJECT__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Target Object</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_OBJECT__TARGET_OBJECT = WRAPPER_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Unknown Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_OBJECT_FEATURE_COUNT = WRAPPER_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '<em>Rectangle</em>' data type. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see jp.go.aist.rtm.toolscommon.model.core.Rectangle
	 * @see jp.go.aist.rtm.toolscommon.model.core.impl.CorePackageImpl#getRectangle()
	 * @generated
	 */
	int RECTANGLE = 6;

	/**
	 * The meta object id for the '<em>Visiter</em>' data type. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see jp.go.aist.rtm.toolscommon.model.core.Visiter
	 * @see jp.go.aist.rtm.toolscommon.model.core.impl.CorePackageImpl#getVisiter()
	 * @generated
	 */
	int VISITER = 7;

	/**
	 * The meta object id for the '<em>Object</em>' data type. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.omg.CORBA.Object
	 * @see jp.go.aist.rtm.toolscommon.model.core.impl.CorePackageImpl#getObject()
	 * @generated
	 */
	int OBJECT = 8;

	/**
	 * The meta object id for the '<em>Point</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see jp.go.aist.rtm.toolscommon.model.core.Point
	 * @see jp.go.aist.rtm.toolscommon.model.core.impl.CorePackageImpl#getPoint()
	 * @generated
	 */
	int POINT = 9;


	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.toolscommon.model.core.CorbaWrapperObject <em>Corba Wrapper Object</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Corba Wrapper Object</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.core.CorbaWrapperObject
	 * @generated
	 */
	EClass getCorbaWrapperObject();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.core.CorbaWrapperObject#getCorbaObject <em>Corba Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Corba Object</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.core.CorbaWrapperObject#getCorbaObject()
	 * @see #getCorbaWrapperObject()
	 * @generated
	 */
	EAttribute getCorbaWrapperObject_CorbaObject();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.toolscommon.model.core.ModelElement <em>Model Element</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model Element</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.core.ModelElement
	 * @generated
	 */
	EClass getModelElement();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.core.ModelElement#getConstraint <em>Constraint</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Constraint</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.core.ModelElement#getConstraint()
	 * @see #getModelElement()
	 * @generated
	 */
	EAttribute getModelElement_Constraint();

	/**
	 * Returns the meta object for class '{@link org.eclipse.core.runtime.IAdaptable <em>IAdaptable</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>IAdaptable</em>'.
	 * @see org.eclipse.core.runtime.IAdaptable
	 * @model instanceClass="org.eclipse.core.runtime.IAdaptable"
	 * @generated
	 */
	EClass getIAdaptable();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.toolscommon.synchronizationframework.LocalObject <em>Local Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Local Object</em>'.
	 * @see jp.go.aist.rtm.toolscommon.synchronizationframework.LocalObject
	 * @model instanceClass="jp.go.aist.rtm.toolscommon.synchronizationframework.LocalObject"
	 * @generated
	 */
	EClass getLocalObject();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.toolscommon.model.core.WrapperObject <em>Wrapper Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Wrapper Object</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.core.WrapperObject
	 * @generated
	 */
	EClass getWrapperObject();

	/**
	 * Returns the meta object for class '{@link jp.go.aist.rtm.toolscommon.model.core.UnknownObject <em>Unknown Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Unknown Object</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.core.UnknownObject
	 * @generated
	 */
	EClass getUnknownObject();

	/**
	 * Returns the meta object for the attribute '{@link jp.go.aist.rtm.toolscommon.model.core.UnknownObject#getTargetObject <em>Target Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Target Object</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.core.UnknownObject#getTargetObject()
	 * @see #getUnknownObject()
	 * @generated
	 */
	EAttribute getUnknownObject_TargetObject();

	/**
	 * Returns the meta object for data type '{@link jp.go.aist.rtm.toolscommon.model.core.Rectangle <em>Rectangle</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Rectangle</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.core.Rectangle
	 * @model instanceClass="jp.go.aist.rtm.toolscommon.model.core.Rectangle"
	 * @generated
	 */
	EDataType getRectangle();

	/**
	 * Returns the meta object for data type '{@link jp.go.aist.rtm.toolscommon.model.core.Visiter <em>Visiter</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Visiter</em>'.
	 * @see jp.go.aist.rtm.toolscommon.model.core.Visiter
	 * @model instanceClass="jp.go.aist.rtm.toolscommon.model.core.Visiter"
	 * @generated
	 */
	EDataType getVisiter();

	/**
	 * Returns the meta object for data type '{@link org.omg.CORBA.Object <em>Object</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Object</em>'.
	 * @see org.omg.CORBA.Object
	 * @model instanceClass="org.omg.CORBA.Object"
	 * @generated
	 */
	EDataType getObject();

	/**
	 * @model instanceClass="jp.go.aist.rtm.toolscommon.model.core.Point"
	 * @generated
	 */
	EDataType getPoint();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	CoreFactory getCoreFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals  {
		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.toolscommon.model.core.impl.CorbaWrapperObjectImpl <em>Corba Wrapper Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.toolscommon.model.core.impl.CorbaWrapperObjectImpl
		 * @see jp.go.aist.rtm.toolscommon.model.core.impl.CorePackageImpl#getCorbaWrapperObject()
		 * @generated
		 */
		EClass CORBA_WRAPPER_OBJECT = eINSTANCE.getCorbaWrapperObject();

		/**
		 * The meta object literal for the '<em><b>Corba Object</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CORBA_WRAPPER_OBJECT__CORBA_OBJECT = eINSTANCE.getCorbaWrapperObject_CorbaObject();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.toolscommon.model.core.impl.ModelElementImpl <em>Model Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.toolscommon.model.core.impl.ModelElementImpl
		 * @see jp.go.aist.rtm.toolscommon.model.core.impl.CorePackageImpl#getModelElement()
		 * @generated
		 */
		EClass MODEL_ELEMENT = eINSTANCE.getModelElement();

		/**
		 * The meta object literal for the '<em><b>Constraint</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODEL_ELEMENT__CONSTRAINT = eINSTANCE.getModelElement_Constraint();

		/**
		 * The meta object literal for the '{@link org.eclipse.core.runtime.IAdaptable <em>IAdaptable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.core.runtime.IAdaptable
		 * @see jp.go.aist.rtm.toolscommon.model.core.impl.CorePackageImpl#getIAdaptable()
		 * @generated
		 */
		EClass IADAPTABLE = eINSTANCE.getIAdaptable();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.toolscommon.synchronizationframework.LocalObject <em>Local Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.toolscommon.synchronizationframework.LocalObject
		 * @see jp.go.aist.rtm.toolscommon.model.core.impl.CorePackageImpl#getLocalObject()
		 * @generated
		 */
		EClass LOCAL_OBJECT = eINSTANCE.getLocalObject();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.toolscommon.model.core.impl.WrapperObjectImpl <em>Wrapper Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.toolscommon.model.core.impl.WrapperObjectImpl
		 * @see jp.go.aist.rtm.toolscommon.model.core.impl.CorePackageImpl#getWrapperObject()
		 * @generated
		 */
		EClass WRAPPER_OBJECT = eINSTANCE.getWrapperObject();

		/**
		 * The meta object literal for the '{@link jp.go.aist.rtm.toolscommon.model.core.impl.UnknownObjectImpl <em>Unknown Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.toolscommon.model.core.impl.UnknownObjectImpl
		 * @see jp.go.aist.rtm.toolscommon.model.core.impl.CorePackageImpl#getUnknownObject()
		 * @generated
		 */
		EClass UNKNOWN_OBJECT = eINSTANCE.getUnknownObject();

		/**
		 * The meta object literal for the '<em><b>Target Object</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UNKNOWN_OBJECT__TARGET_OBJECT = eINSTANCE.getUnknownObject_TargetObject();

		/**
		 * The meta object literal for the '<em>Rectangle</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.toolscommon.model.core.Rectangle
		 * @see jp.go.aist.rtm.toolscommon.model.core.impl.CorePackageImpl#getRectangle()
		 * @generated
		 */
		EDataType RECTANGLE = eINSTANCE.getRectangle();

		/**
		 * The meta object literal for the '<em>Visiter</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.toolscommon.model.core.Visiter
		 * @see jp.go.aist.rtm.toolscommon.model.core.impl.CorePackageImpl#getVisiter()
		 * @generated
		 */
		EDataType VISITER = eINSTANCE.getVisiter();

		/**
		 * The meta object literal for the '<em>Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.omg.CORBA.Object
		 * @see jp.go.aist.rtm.toolscommon.model.core.impl.CorePackageImpl#getObject()
		 * @generated
		 */
		EDataType OBJECT = eINSTANCE.getObject();

		/**
		 * The meta object literal for the '<em>Point</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jp.go.aist.rtm.toolscommon.model.core.Point
		 * @see jp.go.aist.rtm.toolscommon.model.core.impl.CorePackageImpl#getPoint()
		 * @generated
		 */
		EDataType POINT = eINSTANCE.getPoint();

	}

} // CorePackage
