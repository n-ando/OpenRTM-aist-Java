/**
 * <copyright>
 * </copyright>
 *
 * $Id: CoreFactoryImpl.java,v 1.4 2008/03/06 04:01:49 yamashita Exp $
 */
package jp.go.aist.rtm.toolscommon.model.core.impl;

import jp.go.aist.rtm.toolscommon.model.core.*;

import java.util.StringTokenizer;

import jp.go.aist.rtm.toolscommon.corba.CorbaUtil;
import jp.go.aist.rtm.toolscommon.model.core.CoreFactory;
import jp.go.aist.rtm.toolscommon.model.core.CorePackage;
import jp.go.aist.rtm.toolscommon.model.core.ModelElement;
import jp.go.aist.rtm.toolscommon.model.core.Point;
import jp.go.aist.rtm.toolscommon.model.core.Rectangle;
import jp.go.aist.rtm.toolscommon.model.core.UnknownObject;
import jp.go.aist.rtm.toolscommon.model.core.Visiter;
import jp.go.aist.rtm.toolscommon.model.core.WrapperObject;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 * @generated
 */
public class CoreFactoryImpl extends EFactoryImpl implements CoreFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CoreFactory init() {
		try {
			CoreFactory theCoreFactory = (CoreFactory)EPackage.Registry.INSTANCE.getEFactory("http://rtm.aist.go.jp/toolscommon/model/toolscommon"); 
			if (theCoreFactory != null) {
				return theCoreFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new CoreFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	public CoreFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case CorePackage.MODEL_ELEMENT: return createModelElement();
			case CorePackage.WRAPPER_OBJECT: return createWrapperObject();
			case CorePackage.UNKNOWN_OBJECT: return createUnknownObject();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case CorePackage.RECTANGLE:
				return createRectangleFromString(eDataType, initialValue);
			case CorePackage.VISITER:
				return createVisiterFromString(eDataType, initialValue);
			case CorePackage.OBJECT:
				return createObjectFromString(eDataType, initialValue);
			case CorePackage.POINT:
				return createPointFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case CorePackage.RECTANGLE:
				return convertRectangleToString(eDataType, instanceValue);
			case CorePackage.VISITER:
				return convertVisiterToString(eDataType, instanceValue);
			case CorePackage.OBJECT:
				return convertObjectToString(eDataType, instanceValue);
			case CorePackage.POINT:
				return convertPointToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ModelElement createModelElement() {
		ModelElementImpl modelElement = new ModelElementImpl();
		return modelElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WrapperObject createWrapperObject() {
		WrapperObjectImpl wrapperObject = new WrapperObjectImpl();
		return wrapperObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UnknownObject createUnknownObject() {
		UnknownObjectImpl unknownObject = new UnknownObjectImpl();
		return unknownObject;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 */
	public Rectangle createRectangleFromString(EDataType eDataType,
			String initialValue) {
		StringTokenizer tokenize = new StringTokenizer(initialValue, ",	");
		Rectangle rectangle = new Rectangle();
		rectangle.setX(Integer.parseInt(tokenize.nextToken()));
		rectangle.setY(Integer.parseInt(tokenize.nextToken()));
		rectangle.setWidth(Integer.parseInt(tokenize.nextToken()));
		rectangle.setHeight(Integer.parseInt(tokenize.nextToken()));

		return rectangle;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 */
	public String convertRectangleToString(EDataType eDataType,
			Object instanceValue) {
		Rectangle rectangle = ((Rectangle) instanceValue);
		return rectangle.getX() + "," + rectangle.getY() + ","
				+ rectangle.getWidth() + "," + rectangle.getHeight();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Visiter createVisiterFromString(EDataType eDataType, String initialValue) {
		return (Visiter)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String convertVisiterToString(EDataType eDataType,
			Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public org.omg.CORBA.Object createObjectFromString(EDataType eDataType,
			String initialValue) {
		return CorbaUtil.stringToObject(initialValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String convertObjectToString(EDataType eDataType,
			Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 */
	public Point createPointFromString(EDataType eDataType, String initialValue) {
		StringTokenizer tokenize = new StringTokenizer(initialValue, ",	");
		Point point = new Point();
		point.setX(Integer.parseInt(tokenize.nextToken()));
		point.setY(Integer.parseInt(tokenize.nextToken()));

		return point;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 */
	public String convertPointToString(EDataType eDataType, Object instanceValue) {
		Point point = ((Point) instanceValue);
		return point.getX() + "," + point.getY();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public CorePackage getCorePackage() {
		return (CorePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static CorePackage getPackage() {
		return CorePackage.eINSTANCE;
	}

} // CoreFactoryImpl
