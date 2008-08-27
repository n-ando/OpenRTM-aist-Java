/**
 * <copyright>
 * </copyright>
 *
 * $Id: CategoryNamingContextImpl.java,v 1.5 2008/01/28 09:44:51 yamashita Exp $
 */
package jp.go.aist.rtm.nameserviceview.model.nameservice.impl;

import jp.go.aist.rtm.nameserviceview.model.nameservice.CategoryNamingContext;
import jp.go.aist.rtm.nameserviceview.model.nameservice.NameservicePackage;
import jp.go.aist.rtm.nameserviceview.model.nameservice.NamingContextNode;
import jp.go.aist.rtm.nameserviceview.model.nameservice.Node;
import jp.go.aist.rtm.toolscommon.model.core.CorePackage;
import jp.go.aist.rtm.toolscommon.synchronizationframework.LocalObject;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.AttributeMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ClassMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ConstructorParamMapping;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.MappingRule;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.ReferenceMapping;

import org.eclipse.emf.ecore.EClass;
import org.omg.CosNaming.Binding;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Category Naming Context</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class CategoryNamingContextImpl extends NamingContextNodeImpl implements
		CategoryNamingContext {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public CategoryNamingContextImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return NameservicePackage.Literals.CATEGORY_NAMING_CONTEXT;
	}

	public static final String KIND = "cate_cxt";

	public static final MappingRule MAPPING_RULE = new MappingRule(
			NamingContextNodeImpl.MAPPING_RULE,
			new ClassMapping(
					CategoryNamingContextImpl.class,
					new ConstructorParamMapping[] { new ConstructorParamMapping(
							NamingContextExt.class,
							CorePackage.eINSTANCE
									.getCorbaWrapperObject_CorbaObject()) }) {
				@Override
				public boolean isTarget(LocalObject parent,
						Object[] remoteObjects, java.lang.Object link) {
					boolean result = false;
					if (link != null) {
						NameComponent[] nameComponent = ((Binding) link).binding_name;

						if (super.isTarget(parent, remoteObjects, link)
								&& nameComponent[nameComponent.length - 1].kind
										.equals(KIND)) {
							result = true;
						}
					}

					return result;
				}

				@Override
				public LocalObject createLocalObject(LocalObject parent,
						Object[] remoteObjects, java.lang.Object link) {
					LocalObject result = super.createLocalObject(parent,
							remoteObjects, link);
					((NamingContextNode) result)
							.setNameServiceReference(((Node) parent)
									.getNameServiceReference()
									.createMergedNameServiceReference(
											(Binding) link));

					return result;
				}
			}, new AttributeMapping[] {}, new ReferenceMapping[] {});

} // CategoryNamingContextImpl
