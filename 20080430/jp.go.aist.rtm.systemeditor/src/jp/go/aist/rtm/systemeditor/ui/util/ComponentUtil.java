package jp.go.aist.rtm.systemeditor.ui.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.go.aist.rtm.systemeditor.factory.SystemEditorWrapperFactory;
import jp.go.aist.rtm.systemeditor.ui.action.OpenCompositeComponentAction;
import jp.go.aist.rtm.toolscommon.model.component.AbstractComponent;
import jp.go.aist.rtm.toolscommon.model.component.ComponentFactory;
import jp.go.aist.rtm.toolscommon.model.component.ComponentSpecification;
import jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile;
import jp.go.aist.rtm.toolscommon.model.component.Port;
import jp.go.aist.rtm.toolscommon.model.component.PortConnectorSpecification;
import jp.go.aist.rtm.toolscommon.model.component.SystemDiagram;
import jp.go.aist.rtm.toolscommon.model.component.impl.PortConnectorSpecificationImpl;
import jp.go.aist.rtm.toolscommon.model.core.Rectangle;
import jp.go.aist.rtm.toolscommon.synchronizationframework.SynchronizationSupport;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.PlatformUI;

public class ComponentUtil {

	public static SystemDiagram getRootSystemDiagram(SystemDiagram systemDiagram) {
		SystemDiagram rootSystemDiagram = systemDiagram;
		OpenCompositeComponentAction action = (OpenCompositeComponentAction) rootSystemDiagram
				.getOpenCompositeComponentAction();
		while (action != null) {
			rootSystemDiagram = action.getParentSystemDiagramEditor()
					.getSystemDiagram();
			action = (OpenCompositeComponentAction) rootSystemDiagram
					.getOpenCompositeComponentAction();
		}
		return rootSystemDiagram;
	}

	/* target自分自身はParentに設定しないのでこのメソッドを呼ぶ前にtargetをParentに設定して置く(必要であれば)。 */
	public static void copieAndSetCompositeComponents(EObject parent,
			AbstractComponent target) {
		if (target.isCompositeComponent()) {
			List<AbstractComponent> compositeComponents = new ArrayList<AbstractComponent>();
			List<AbstractComponent> copyComponents = new ArrayList<AbstractComponent>();
			AbstractComponent copyTarget = null;
			if (target.getCorbaBaseObject() != null) {
				copyTarget = (AbstractComponent) SynchronizationSupport
						.findLocalObjectByRemoteObject(new Object[] { target
								.getCorbaBaseObject() }, parent);
			} else {
				copyTarget = ComponentUtil
						.findComponentByPathId(target, parent);
			}
			if (copyTarget != null) {
				compositeComponents.add(target);
			}
			for (Iterator iterator = target.getAllComponents().iterator(); iterator
					.hasNext();) {
				AbstractComponent object = (AbstractComponent) iterator.next();
				AbstractComponent copy = (AbstractComponent) SystemEditorWrapperFactory
						.getInstance().copy(object);
				copy.setOpenCompositeComponentAction(object
						.getOpenCompositeComponentAction());
				copyComponents.add(copy);
				if (object.isCompositeComponent()) {
					compositeComponents.add(object);
				}
			}
			if (parent instanceof SystemDiagram) {
				((SystemDiagram) parent).getComponents().addAll(copyComponents);
			} else if (parent instanceof AbstractComponent) {
				((AbstractComponent) parent).getComponents().addAll(
						copyComponents);
			}
			for (AbstractComponent compositeComponent : compositeComponents) {
				AbstractComponent copy = null;
				if (compositeComponent.getCorbaObject() != null) {
					copy = (AbstractComponent) SynchronizationSupport
							.findLocalObjectByRemoteObject(
									new Object[] { compositeComponent
											.getCorbaBaseObject() }, parent);
					if (compositeComponent.getCompositeComponent() != null) {
						// stub
						copy
								.setCompositeComponent((AbstractComponent) SynchronizationSupport
										.findLocalObjectByRemoteObject(
												new Object[] { compositeComponent
														.getCorbaBaseObject() },
												parent));
					}
				} else {
					copy = ComponentUtil.findComponentByPathId(
							compositeComponent, parent);
					if (compositeComponent.getCompositeComponent() != null) {
						// stub
						copy.setCompositeComponent(ComponentUtil
								.findComponentByPathId(compositeComponent
										.getCompositeComponent(), parent));
					}
				}
				for (Iterator iterator = compositeComponent.getComponents()
						.iterator(); iterator.hasNext();) {
					AbstractComponent component = (AbstractComponent) iterator
							.next();
					AbstractComponent localObject = null;
					if (component.getCorbaBaseObject() != null) {
						localObject = (AbstractComponent) SynchronizationSupport
								.findLocalObjectByRemoteObject(
										new Object[] { component
												.getCorbaBaseObject() }, parent);
					} else {
						localObject = ComponentUtil.findComponentByPathId(
								component, parent);
					}

					if (localObject != null) {
						copy.getComponents().add(localObject);
					}
				}
			}
		}
	}

	public static void removeComponents(SystemDiagram parent,
			AbstractComponent target) {
		List<AbstractComponent> components = new ArrayList<AbstractComponent>();
		components.add(target);
		components.addAll((Collection<? extends AbstractComponent>) target
				.getAllComponents());
		OpenCompositeComponentAction openAction = (OpenCompositeComponentAction) parent
				.getOpenCompositeComponentAction();
		// 親エディタから削除.
		if (openAction != null) {
			// 子エディタの場合親エディタに存在するtargetも削除する。
			OpenCompositeComponentAction parentAction = openAction;
			while (parentAction != null) {
				for (AbstractComponent component : components) {
					AbstractComponent localObject = null;
					if (component.getCorbaBaseObject() != null) {
						localObject = (AbstractComponent) SynchronizationSupport
								.findLocalObjectByRemoteObject(
										new Object[] { component
												.getCorbaBaseObject() },
										parentAction
												.getParentSystemDiagramEditor()
												.getSystemDiagram());
					} else {
						localObject = ComponentUtil.findComponentByPathId(
								component, parentAction
										.getParentSystemDiagramEditor()
										.getSystemDiagram());
					}
					if (localObject != null) {
						parentAction.getParentSystemDiagramEditor()
								.getSystemDiagram().getComponents().remove(
										localObject);
						if (component == target) {
							localObject.setCompositeComponent(null);
						}
						localObject.setOpenCompositeComponentAction(null);
					}
				}
				parentAction = (OpenCompositeComponentAction) parentAction
						.getParentSystemDiagramEditor().getSystemDiagram()
						.getOpenCompositeComponentAction();
			}
		}
		for (AbstractComponent component : components) {
			// 開いたエディタを閉じる
			if (component.isCompositeComponent()) {
				if (component.getOpenCompositeComponentAction() != null
						&& ((OpenCompositeComponentAction) component
								.getOpenCompositeComponentAction())
								.getCompositeComponentEditor() != null) {
					try {
						PlatformUI
								.getWorkbench()
								.getActiveWorkbenchWindow()
								.getActivePage()
								.closeEditor(
										((OpenCompositeComponentAction) component
												.getOpenCompositeComponentAction())
												.getCompositeComponentEditor(),
										false);
					} catch (Exception e) {
						e.printStackTrace();
						// void
					}
				}
				component.setOpenCompositeComponentAction(null);
			}
			// 当該エディタから削除。
			parent.getComponents().remove(component);
		}
		target.setCompositeComponent(null);
	}

	public static void removeCompositeComponent(SystemDiagram parent,
			AbstractComponent target) {
		List<AbstractComponent> components = new ArrayList<AbstractComponent>();
		components.addAll(target.getComponents());
		OpenCompositeComponentAction openAction = (OpenCompositeComponentAction) parent
				.getOpenCompositeComponentAction();
		if (target.getOpenCompositeComponentAction() != null) {
			for (AbstractComponent component : components) {
				if (component.getOpenCompositeComponentAction() != null) {
					((OpenCompositeComponentAction) component
							.getOpenCompositeComponentAction())
							.setParentSystemDiagramEditor(((OpenCompositeComponentAction) target
									.getOpenCompositeComponentAction())
									.getParentSystemDiagramEditor());
				}
			}
		}
		if (openAction != null) {
			OpenCompositeComponentAction parentAction = openAction;
			AbstractComponent compositeComponent = parentAction
					.getCompositeComponent();
			while (parentAction != null) {
				AbstractComponent localTarget = null;
				if (target.getCorbaBaseObject() != null) {
					localTarget = (AbstractComponent) SynchronizationSupport
							.findLocalObjectByRemoteObject(
									new Object[] { target.getCorbaBaseObject() },
									parentAction.getParentSystemDiagramEditor()
											.getSystemDiagram());
				} else {
					localTarget = ComponentUtil.findComponentByPathId(target,
							parentAction.getParentSystemDiagramEditor()
									.getSystemDiagram());
				}
				int count = 0;
				for (AbstractComponent component : components) {
					AbstractComponent localObject = null;

					if (component.getCorbaBaseObject() != null) {
						localObject = (AbstractComponent) SynchronizationSupport
								.findLocalObjectByRemoteObject(
										new Object[] { component
												.getCorbaBaseObject() },
										parentAction
												.getParentSystemDiagramEditor()
												.getSystemDiagram());
					} else {
						localObject = ComponentUtil.findComponentByPathId(
								component, parentAction
										.getParentSystemDiagramEditor()
										.getSystemDiagram());
					}
					if (localObject != null) {
						localObject.setCompositeComponent(localTarget
								.getCompositeComponent());
						localObject.setConstraint(getNewComponentConstraint(
								target.getConstraint(), count));
						count++;
					}
				}
				localTarget.setCompositeComponent(null);
				parentAction.getParentSystemDiagramEditor().getSystemDiagram()
						.getComponents().remove(localTarget);
				parentAction = (OpenCompositeComponentAction) parentAction
						.getParentSystemDiagramEditor().getSystemDiagram()
						.getOpenCompositeComponentAction();
			}
		}

		for (AbstractComponent component : components) {
			component.setCompositeComponent(null);
		}
		// 開いたエディタを閉じる
		if (target.getOpenCompositeComponentAction() != null
				&& ((OpenCompositeComponentAction) target
						.getOpenCompositeComponentAction())
						.getCompositeComponentEditor() != null) {

			final AbstractComponent compositeComponent = target;
			PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
				public void run() {
					try {
						PlatformUI
								.getWorkbench()
								.getActiveWorkbenchWindow()
								.getActivePage()
								.closeEditor(
										((OpenCompositeComponentAction) compositeComponent
												.getOpenCompositeComponentAction())
												.getCompositeComponentEditor(),
										false);

					} catch (Exception e) {
						e.printStackTrace();
						// void
					}
				}
			});
		}

		target.setCompositeComponent(null);
		target.setOpenCompositeComponentAction(null);
		parent.getComponents().remove(target);
	}

	// コンポーネントを探すメソッド
	// （PathIdで探す)
	public static AbstractComponent findComponentByPathId(
			AbstractComponent component, EObject object) {
		if (component == null || object == null) {
			return null;
		}
		List<AbstractComponent> components = getRegisteredComponents(object);
		AbstractComponent localObject = null;
		for (AbstractComponent tempComponent : components) {
			if (tempComponent.getPathId() != null
					&& tempComponent.getPathId().equals(component.getPathId())) {
				localObject = tempComponent;
				break;
			}
		}
		return localObject;
	}

	// コンポーネント数を検索するメソッド(オフラインエディタ用)
	public static int getComponentNumberByPathId(
			ComponentSpecification component, EObject object) {
		int result = 0;
		if (component == null || object == null) {
			return result;
		}
		List<AbstractComponent> components = getRegisteredComponents(object);
		for (AbstractComponent tempComponent : components) {
			if (tempComponent.getPathId() != null ) {
				String pathID = ((ComponentSpecification)tempComponent).getPathId();
				int index = pathID.lastIndexOf(":");
				pathID = pathID.substring(0, index);
				if( pathID.equals(component.getPathURI())) {
					result++;
				}
			}
		}
		return result;
	}

	private static List<AbstractComponent> getRegisteredComponents(EObject object) {
		List<AbstractComponent> components = new ArrayList<AbstractComponent>();
		EObject graphRoot = EcoreUtil.getRootContainer(object);
		for (Iterator iter = graphRoot.eAllContents(); iter.hasNext();) {
			Object obj = iter.next();
			if (obj instanceof AbstractComponent) {
				components.add((AbstractComponent) obj);
			}
		}
		if (object instanceof AbstractComponent) {
			components.add((AbstractComponent) object);
		}
		if (object instanceof AbstractComponent) {
			for (Iterator iterator = ((AbstractComponent) object)
					.getAllComponents().iterator(); iterator.hasNext();) {
				AbstractComponent tempComponent = (AbstractComponent) iterator
						.next();
				if (!components.contains(tempComponent)) {
					components.add(tempComponent);
				}
			}
		}
		return components;
	}

	public static boolean isSystemDiagramSynchronized(
			SystemDiagram systemDiagram) {

		OpenCompositeComponentAction action = null;
		if (systemDiagram.getOpenCompositeComponentAction() != null) {
			action = (OpenCompositeComponentAction) systemDiagram
					.getOpenCompositeComponentAction();
		}
		AbstractComponent compositeComponent = null;
		if (action != null) {
			compositeComponent = (AbstractComponent) action
					.getCompositeComponent();
		}
		AbstractComponent localObject = compositeComponent;
		while (action != null) {
			if (localObject != null) {
				if (compositeComponent.getCorbaBaseObject() != null) {
					localObject = (AbstractComponent) SynchronizationSupport
							.findLocalObjectByRemoteObject(
									new Object[] { compositeComponent
											.getCorbaBaseObject() }, action
											.getParentSystemDiagramEditor()
											.getSystemDiagram());
				} else {
					localObject = (AbstractComponent) ComponentUtil
							.findComponentByPathId(compositeComponent, action
									.getParentSystemDiagramEditor()
									.getSystemDiagram());
				}
			}
			action = (OpenCompositeComponentAction) action
					.getParentSystemDiagramEditor().getSystemDiagram()
					.getOpenCompositeComponentAction();
		}
		if (systemDiagram.getOpenCompositeComponentAction() != null
				&& localObject == null) {
			return false;
		}
		return true;
	}

	public static Rectangle getNewComponentConstraint(Rectangle rectangle,
			int count) {
		int x = rectangle.getX() + count * 100;
		int y = rectangle.getY();
		Rectangle result = new Rectangle();
		result.setX(x);
		result.setY(y);
		result.setHeight(rectangle.getHeight());
		result.setWidth(rectangle.getWidth());
		return result;
	}

	public static void createSpecificationConnector(
			SystemDiagram systemDiagram, boolean compositExc) {
		List<Port> ports = new ArrayList<Port>();
		for (Object object : systemDiagram.getComponents()) {
			if (!(object instanceof ComponentSpecification)) {
				return;
			}
			for (Object port : ((ComponentSpecification) object).getPorts()) {
				if (!ports.contains(port)
						&& !((Port) port).getPortProfile()
								.getConnectorProfiles().isEmpty()) {
					ports.add((Port) port);
				}
			}
		}
		if (ports.size() < 2) {
			return;
		}
		Map<String, Port> portMap = new HashMap<String, Port>();
		List<PortConnectorSpecification> connectors = new ArrayList<PortConnectorSpecification>();
		for (Port port : ports) {
			for (Iterator iterator = port.getPortProfile()
					.getConnectorProfiles().iterator(); iterator.hasNext();) {
				ConnectorProfile profile = (ConnectorProfile) iterator.next();
				if (profile.getConnectorId() != null) {
					if (portMap.containsKey(profile.getConnectorId())) {
						PortConnectorSpecification connector = (PortConnectorSpecification) profile
								.eContainer();
						if (connector == null) {
							connector = ComponentFactory.eINSTANCE
									.createPortConnectorSpecification();
							connector.setConnectorProfile(profile);
						}
						connector.setTarget(portMap.get(profile
								.getConnectorId()));
						connector.setSource(port);
						connectors.add(connector);
						portMap.remove(profile.getConnectorId());

					} else {
						portMap.put(profile.getConnectorId(), port);
					}
				}
			}
		}
		if (compositExc) {
			for (PortConnectorSpecification connector : connectors) {
				connector.createConnectorR();
			}
		} else {
			for (PortConnectorSpecification connector : connectors) {
				PortConnectorSpecificationImpl.createConnectorR((Port)connector
						.getSource(), (Port)connector.getTarget(), connector
						.getConnectorProfile());
			}
		}
		
	}

	public static void setConnectorProfiles(SystemDiagram systemDiagram) {
		List<Port> ports = new ArrayList<Port>();
		try{
			for (Object object : systemDiagram.getComponents()) {
				if (!(object instanceof ComponentSpecification)) {
					return;
				}
				for (Object port : ((ComponentSpecification) object).getPorts()) {
					if (!ports.contains(port)
							&& (((Port) port).getPortProfile()
									.getConnectorProfiles().isEmpty()
									&& !(((Port) port).getSourceConnectors()
											.isEmpty() && ((Port) port)
									.getTargetConnectors().isEmpty()))) {
						ports.add((Port) port);
					}
				}
			}
			List<PortConnectorSpecification> connectors = new ArrayList<PortConnectorSpecification>();
			for(Port port : ports) {
				if (port.getPortProfile().getConnectorProfiles().isEmpty()) {
					if (!port.getSourceConnectors().isEmpty()) {
						for (Iterator<PortConnectorSpecification> iterator = port
								.getSourceConnectors().iterator(); iterator.hasNext();) {
							PortConnectorSpecification connector = iterator.next();
							if (!connectors.contains(connector)) {
								connectors.add(connector);
							}
						}
					}
					if (!port.getTargetConnectors().isEmpty()) {
						for (Iterator<PortConnectorSpecification> iterator = port
								.getTargetConnectors().iterator(); iterator.hasNext();) {
							PortConnectorSpecification connector = iterator.next();
							if (!connectors.contains(connector)) {
								connectors.add(connector);
							}
						}
					}
				}
			}
			for (PortConnectorSpecification connector :connectors) {
				((Port) connector.getSource()).getPortProfile()
						.getConnectorProfiles()
						.add(connector.getConnectorProfile());
				((Port) connector.getTarget()).getPortProfile()
						.getConnectorProfiles()
						.add(connector.getConnectorProfile());
				EcoreUtil.remove(connector);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
