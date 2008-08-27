/**
 * <copyright>
 * </copyright>
 *
 * $Id: NameServerNamingContextImpl.java,v 1.5 2008/01/28 09:44:51 yamashita Exp $
 */
package jp.go.aist.rtm.nameserviceview.model.nameservice.impl;

import jp.go.aist.rtm.nameserviceview.model.nameservice.NameServerNamingContext;
import jp.go.aist.rtm.nameserviceview.model.nameservice.NameServiceReference;
import jp.go.aist.rtm.nameserviceview.model.nameservice.NameservicePackage;
import jp.go.aist.rtm.toolscommon.model.core.CorePackage;
import jp.go.aist.rtm.toolscommon.synchronizationframework.LocalObject;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.AttributeMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ClassMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ConstructorParamMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.MappingRule;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ReferenceMapping;

import org.eclipse.emf.ecore.EClass;
import org.omg.CosNaming.NamingContextExt;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Name Server Naming Context</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class NameServerNamingContextImpl extends NamingContextNodeImpl
		implements NameServerNamingContext {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public NameServerNamingContextImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return NameservicePackage.Literals.NAME_SERVER_NAMING_CONTEXT;
	}

	public static final MappingRule MAPPING_RULE = new MappingRule(
			NamingContextNodeImpl.MAPPING_RULE,
			new ClassMapping(
					NameServerNamingContextImpl.class,
					new ConstructorParamMapping[] { new ConstructorParamMapping(
							NamingContextExt.class,
							CorePackage.eINSTANCE
									.getCorbaWrapperObject_CorbaObject()),new ConstructorParamMapping(
											NameServiceReference.class,
											NameservicePackage.eINSTANCE
													.getNode_NameServiceReference()) }) {
				@Override
				public boolean isTarget(LocalObject parent,
						Object[] remoteObjects, java.lang.Object link) {
					boolean result = false;
					if (super.isTarget(parent, remoteObjects, link)
							&& parent == null) {
						result = true;
					}

					return result;
				}

//				@Override
//				public LocalObject createLocalObject(LocalObject parent,
//						Object[] remoteObjects, java.lang.Object link) {
//					LocalObject result = super.createLocalObject(parent,
//							remoteObjects, link);
//
//
//					return result;
//				}
//
			}, new AttributeMapping[] {}, new ReferenceMapping[] {});

} // NameServerNamingContextImpl
