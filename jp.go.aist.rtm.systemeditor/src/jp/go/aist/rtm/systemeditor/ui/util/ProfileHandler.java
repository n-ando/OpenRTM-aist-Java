package jp.go.aist.rtm.systemeditor.ui.util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.xml.datatype.DatatypeFactory;

import jp.go.aist.rtm.repositoryView.model.RepositoryViewItem;
import jp.go.aist.rtm.repositoryView.model.RepositoryViewLeafItem;
import jp.go.aist.rtm.systemeditor.factory.SystemEditorWrapperFactory;
import jp.go.aist.rtm.systemeditor.ui.editor.AbstractSystemDiagramEditor;
import jp.go.aist.rtm.systemeditor.ui.editor.OfflineSystemDiagramEditor;
import jp.go.aist.rtm.systemeditor.ui.editor.command.CombineCommand;
import jp.go.aist.rtm.toolscommon.model.component.AbstractComponent;
import jp.go.aist.rtm.toolscommon.model.component.Component;
import jp.go.aist.rtm.toolscommon.model.component.ComponentFactory;
import jp.go.aist.rtm.toolscommon.model.component.ComponentSpecification;
import jp.go.aist.rtm.toolscommon.model.component.ConfigurationSet;
import jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile;
import jp.go.aist.rtm.toolscommon.model.component.ConnectorTarget;
import jp.go.aist.rtm.toolscommon.model.component.InPort;
import jp.go.aist.rtm.toolscommon.model.component.NameValue;
import jp.go.aist.rtm.toolscommon.model.component.OutPort;
import jp.go.aist.rtm.toolscommon.model.component.Port;
import jp.go.aist.rtm.toolscommon.model.component.PortConnectorSpecification;
import jp.go.aist.rtm.toolscommon.model.component.ServicePort;
import jp.go.aist.rtm.toolscommon.model.component.SystemDiagram;
import jp.go.aist.rtm.toolscommon.model.component.SystemDiagramKind;
import jp.go.aist.rtm.toolscommon.model.core.Rectangle;
import jp.go.aist.rtm.toolscommon.profiles.util.XmlHandler;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.commands.CommandStack;
import org.openrtp.namespaces.rts.ComponentExt;
import org.openrtp.namespaces.rts.ConfigurationData;
import org.openrtp.namespaces.rts.Dataport;
import org.openrtp.namespaces.rts.DataportConnector;
import org.openrtp.namespaces.rts.Location;
import org.openrtp.namespaces.rts.ObjectFactory;
import org.openrtp.namespaces.rts.Participant;
import org.openrtp.namespaces.rts.RtsProfileExt;
import org.openrtp.namespaces.rts.Serviceport;
import org.openrtp.namespaces.rts.ServiceportConnector;
import org.openrtp.namespaces.rts.TargetComponent;
import org.openrtp.namespaces.rts.TargetPort;

import com.sun.org.apache.xerces.internal.jaxp.datatype.DatatypeFactoryImpl;

public class ProfileHandler {
	
	public SystemDiagram convertToSystemEditor(String targetFile, ArrayList baseInfo,
			AbstractSystemDiagramEditor targetEditor) throws Exception {
		RtsProfileExt profile = null;
		XmlHandler handler = new XmlHandler();
		profile = handler.loadXmlRts(targetFile);
		//
		SystemDiagram diagram = ComponentFactory.eINSTANCE.createSystemDiagram();
		diagram.setKind(SystemDiagramKind.OFFLINE_LITERAL);
		diagram.setEditorId(OfflineSystemDiagramEditor.OFFLINE_SYSTEM_DIAGRAM_EDITOR_ID);
		diagram.setSystemId(profile.getId());
		for( Iterator iter = profile.getComponent().iterator(); iter.hasNext(); ) {
			ComponentExt component = (ComponentExt)iter.next();
			if( component.getCompositeType().equals("None")) { 
				ComponentSpecification spec = checkModels(component.getId(), baseInfo);
				if( spec==null ) {
					throw new Exception("Target Component does not exist in RepositoryView.");
				}
				AbstractComponent copy = restoreBasicInfo(component, spec, diagram);
				//ConfigurationSet
				restoreConfigurationSet(component, copy);
				diagram.getComponents().add(copy);
			}
		}
		//
		for( Iterator iter = profile.getComponent().iterator(); iter.hasNext(); ) {
			ComponentExt component = (ComponentExt)iter.next();
			//DataPortê⁄ë±
			List<Dataport> dataPortListBase = component.getDataPorts();
			for( Dataport dataPortBase : dataPortListBase) {
				if(dataPortBase.getPortType().toLowerCase().equals("dataoutport") ){
					List<DataportConnector> connListBase = dataPortBase.getDataPortConnectors();
					if( connListBase!=null ) {
						for( DataportConnector connBase : connListBase) {
							ConnectorProfile conn = ComponentFactory.eINSTANCE.createConnectorProfile();
							conn.setConnectorId(connBase.getConnectorId());
							conn.setName(connBase.getName());
							conn.setInterfaceType(connBase.getInterfaceType());
							conn.setDataType(connBase.getDataType());
							conn.setDataflowType(connBase.getDataflowType());
							if(connBase.getSubscriptionType()!=null) conn.setSubscriptionType(connBase.getSubscriptionType());
							if(connBase.getPushInterval()!=null) conn.setPushRate(connBase.getPushInterval());
	
							connectPorts(component, conn, 
									diagram.getComponents(),
									connBase.getTargetDataPort().getComponentId(),
									connBase.getTargetDataPort().getInstanceName(),
									connBase.getTargetDataPort().getPortName(),
									dataPortBase.getName());
						}
					}
				}
			}
			//ServicePortê⁄ë±
			List<Serviceport> servicePortListBase = component.getServicePorts();
			for( Serviceport servicePortBase : servicePortListBase) {
				List<ServiceportConnector> connListBase = servicePortBase.getServicePortConnectors();
				if( connListBase!=null ) {
					for( ServiceportConnector connBase : connListBase) {
						ConnectorProfile conn = ComponentFactory.eINSTANCE.createConnectorProfile();
						conn.setConnectorId(connBase.getConnectorId());
						conn.setName(connBase.getName());

						connectPorts(component, conn, 
								diagram.getComponents(), 
								connBase.getTargetServicePort().getComponentId(),
								connBase.getTargetServicePort().getInstanceName(),
								connBase.getTargetServicePort().getPortName(),
								servicePortBase.getName());
					}
				}
			}
		}
		//Composite Component
		for( Iterator iter = profile.getComponent().iterator(); iter.hasNext(); ) {
			ComponentExt component = (ComponentExt)iter.next();
			if( !component.getCompositeType().equals("None")) { 
				AbstractComponent compositeComponent = ComponentFactory.eINSTANCE.createComponentSpecification();
				List<AbstractComponent> selectedComponents = getChildren(component, diagram.getComponents());
				//
				compositeComponent.setInstanceNameL(component.getInstanceName());
				compositeComponent.setPathId(component.getId() + ":" + component.getInstanceName());
				Rectangle constraint = new Rectangle();
				constraint.setX(component.getLocation().getX().intValue());
				constraint.setY(component.getLocation().getY().intValue());
				constraint.setWidth(component.getLocation().getWidth().intValue());
				constraint.setHeight(component.getLocation().getHeight().intValue());
				compositeComponent.setConstraint(constraint);
				compositeComponent.setOutportDirection(component.getLocation().getDirection());
				//
				compositeComponent.getComponents().addAll(selectedComponents);
				compositeComponent.setCompsiteType(Component.COMPOSITE_COMPONENT);
				CombineCommand command = new CombineCommand();
				command.setParent(diagram);
				command.setTarget(compositeComponent);
				if( targetEditor.getAdapter(CommandStack.class) != null ) {
					((CommandStack) targetEditor.getAdapter(CommandStack.class))
							.execute(command);
				} else {
					throw new RuntimeException();
				}
			}
		}
		//
		return diagram;
	}
	
	private List<AbstractComponent> getChildren(ComponentExt component,List components) {
		List<AbstractComponent> result = new ArrayList<AbstractComponent>();
		for(TargetComponent target : component.getParticipants().getParticipant() ) {
			for( Object trgComp : components ) {
				if( target.getComponentId().equals(((ComponentSpecification)trgComp).getComponentId()) &&
						target.getInstanceName().equals(((ComponentSpecification)trgComp).getInstanceNameL())) {
					result.add((ComponentSpecification)trgComp);
					break;
				}
			}
		}
		return result;
	}

	private void connectPorts(ComponentExt component, ConnectorProfile conn, List components, String targetCompId, String targetInstanceName, String targetPortName, String sourcePortName) {
		PortConnectorSpecification connector = ComponentFactory.eINSTANCE.createPortConnectorSpecification();
		connector.setSource(getTargetPortforRestore(
				component.getId(), component.getInstanceName(),	sourcePortName,	components));
		connector.setTarget(getTargetPortforRestore(
				targetCompId, targetInstanceName, targetPortName, components));
		connector.setConnectorProfile(conn);
		connector.createConnectorR();
	}

	private void restoreConfigurationSet(ComponentExt component, AbstractComponent copy) {
		String activeId = component.getActiveConfigurationSet();
		List<org.openrtp.namespaces.rts.ConfigurationSet> configSetsBase = component.getConfigurationSets();
		for( org.openrtp.namespaces.rts.ConfigurationSet configBase : configSetsBase ) {
			jp.go.aist.rtm.toolscommon.model.component.ConfigurationSet configSet = ComponentFactory.eINSTANCE.createConfigurationSet();
			configSet.setId(configBase.getId());
			List<ConfigurationData> confdataBase = configBase.getConfigurationData();
			if(activeId!=null && activeId.equals(configBase.getId()) ) {
				copy.setActiveConfigurationSet(configSet);
			}
			for( ConfigurationData dataBase : confdataBase ) {
				NameValue value = ComponentFactory.eINSTANCE.createNameValue();
				value.setName(dataBase.getName());
				value.setValueAsString(dataBase.getData());
				configSet.getConfigurationData().add(value);
			}
			copy.getConfigurationSets().add(configSet);
		}
	}

	private AbstractComponent restoreBasicInfo(ComponentExt component, ComponentSpecification spec, SystemDiagram diagram) {
		AbstractComponent copy = (AbstractComponent) SystemEditorWrapperFactory
				.getInstance().copy(spec);
		copy.setOpenCompositeComponentAction(spec.getOpenCompositeComponentAction());
		
		copy.setInstanceNameL(component.getInstanceName());
		String pathUri = component.getPathUri();
		int compCount =	ComponentUtil.getComponentNumberByPathId(
				(ComponentSpecification) copy, diagram);
		copy.setPathId(pathUri + ":" + Integer.valueOf(compCount+1).toString() );
		Rectangle constraint = new Rectangle();
		constraint.setX(component.getLocation().getX().intValue());
		constraint.setY(component.getLocation().getY().intValue());
		constraint.setWidth(component.getLocation().getWidth().intValue());
		constraint.setHeight(component.getLocation().getHeight().intValue());
		copy.setConstraint(constraint);
		copy.setOutportDirection(component.getLocation().getDirection());
		return copy;
	}
	
	private ComponentSpecification checkModels(String strId, List models) {
		ComponentSpecification result = null;
		
		for(Iterator iter=models.iterator(); iter.hasNext();) {
			RepositoryViewItem item = (RepositoryViewItem)iter.next();
			if( item instanceof RepositoryViewLeafItem ) {
				ComponentSpecification target = ((RepositoryViewLeafItem)item).getComponent();
				if( target == null) return null;
				if(target.getComponentId().equals(strId)) return target;
			} else {
				if( item.getChildren() != null ) {
					result = checkModels(strId, item.getChildren());
					if( result!=null ) return result;
				}
			}
		}
		return result;
	}
	
	private Port getTargetPortforRestore(String componentId, String instanceName, String portName, List components) {
		Port result = null;
		
		for( Object trgComp : components ) {
			if( componentId.equals(((ComponentSpecification)trgComp).getComponentId()) &&
					instanceName.equals(((ComponentSpecification)trgComp).getInstanceNameL())) {
				for(Object trgPort : ((ComponentSpecification)trgComp).getPorts() ) {
					if(portName.equals(((Port)trgPort).getPortProfile().getNameL()) ) {
						return (Port)trgPort;
					}
				}
						
			}
		}
		
		return result;
	}
	
	public RtsProfileExt convertToProfile(EObject graphPart) {
		RtsProfileExt profile = null;
		ObjectFactory factory = new ObjectFactory();
		EObject systemRoot = EcoreUtil.getRootContainer(graphPart);
		if( systemRoot != null ) {
			profile = factory.createRtsProfileExt();
			profile.setId(((SystemDiagram)systemRoot).getSystemId());
			DatatypeFactory dateFactory = new DatatypeFactoryImpl();
			profile.setCreationDate(dateFactory.newXMLGregorianCalendar(((SystemDiagram)systemRoot).getCreationDate()));
			profile.setUpdateDate(dateFactory.newXMLGregorianCalendar(((SystemDiagram)systemRoot).getUpdateDate()));
			profile.setVersion("1.0");
			List<String> savedCon = new ArrayList<String>();
			for( Iterator iter = systemRoot.eAllContents(); iter.hasNext(); ) {
				Object obj = iter.next();
				if( obj instanceof ComponentSpecification ) {
					ComponentSpecification targetComp = (ComponentSpecification)obj;
					ComponentExt component = saveBasicInfo(targetComp, factory);
					component.setLocation(saveLocation(targetComp, factory));
					//ConfigurationSets
					saveConfigurationSet(factory, targetComp, component);
					//ExecutionContexts

					//DataInPorts
					saveDataPorts(factory, targetComp, component);
					//ServicePorts
					saveServicePorts(factory, targetComp, component, savedCon);
					profile.getComponent().add(component);
				}
			}
		}
		return profile;
	}

	private void saveConfigurationSet(ObjectFactory factory, ComponentSpecification targetComp, ComponentExt component) {
		List<ConfigurationSet> configs = targetComp.getConfigurationSets();
		if( configs != null && configs.size()>0 ) {
			for( Iterator iterconf = configs.iterator(); iterconf.hasNext(); ) {
				ConfigurationSet baseConfig = (ConfigurationSet)iterconf.next();
				component.getConfigurationSets().add(saveConfigurationSet(baseConfig, factory));
			}
		}
		if( targetComp.getActiveConfigurationSet()!=null ) {
			component.setActiveConfigurationSet(
					targetComp.getActiveConfigurationSet().getId());
		}
	}

	private void saveDataPorts(ObjectFactory factory, ComponentSpecification targetComp, ComponentExt component) {
		List<Port> inports = targetComp.getAllInPorts();
		if( inports != null && inports.size()>0 ) {
			for( Iterator iterport = inports.iterator(); iterport.hasNext(); ) {
				InPort portBase = (InPort)iterport.next();
				Dataport port = factory.createDataport();
				port.setPortType("DataInPort");
				port.setName(portBase.getPortProfile().getNameL());
				component.getDataPorts().add(port);
			}
		}
		List<Port> outports = targetComp.getAllOutPorts();
		if( outports != null && outports.size()>0 ) {
			for( Iterator iterport = outports.iterator(); iterport.hasNext(); ) {
				OutPort portBase = (OutPort)iterport.next();
				Dataport port = factory.createDataport();
				port.setPortType("DataOutPort");
				port.setName(portBase.getPortProfile().getNameL());
				component.getDataPorts().add(port);
				if(component.getCompositeType().equals("None")) {
					List<ConnectorProfile> connectors = portBase.getPortProfile().getConnectorProfiles();
					for( Iterator itercon = connectors.iterator(); itercon.hasNext(); ) {
						ConnectorProfile source = (ConnectorProfile)itercon.next();
						port.getDataPortConnectors().add(saveDataPortConnector(source, portBase, factory));
					}
				}
			}
		}
	}

	private void saveServicePorts(ObjectFactory factory, ComponentSpecification targetComp, ComponentExt component, List<String> savedCon) {
		List<Port> serviceports = targetComp.getAllServiceports();
		if( serviceports != null && serviceports.size()>0 ) {
			for( Iterator iterport = serviceports.iterator(); iterport.hasNext(); ) {
				ServicePort portBase = (ServicePort)iterport.next();
				org.openrtp.namespaces.rts.Serviceport port = factory.createServiceport();
				port.setName(portBase.getPortProfile().getNameL());
				component.getServicePorts().add(port);
				List<ConnectorProfile> connectors = portBase.getPortProfile().getConnectorProfiles();
				for( Iterator itercon = connectors.iterator(); itercon.hasNext(); ) {
					ConnectorProfile source = (ConnectorProfile)itercon.next();
					if( !savedCon.contains(source.getConnectorId()) ) {
						port.getServicePortConnectors().add(saveServicePortConnector(source,portBase,factory));
						savedCon.add(source.getConnectorId());
					}
				}
			}
		}
	}

	private ServiceportConnector saveServicePortConnector(ConnectorProfile source, Port portBase, ObjectFactory factory) {
		ServiceportConnector connector = factory.createServiceportConnector();
		connector.setConnectorId(source.getConnectorId());
		connector.setName(source.getName());
		connector.setTargetServicePort(getTargetPortforSave(source,factory,portBase));
		return connector;
	}

	private DataportConnector saveDataPortConnector(ConnectorProfile source, Port portBase, ObjectFactory factory) {
		DataportConnector connector = factory.createDataportConnector();
		connector.setConnectorId(source.getConnectorId());
		connector.setName(source.getName());
		connector.setInterfaceType(source.getInterfaceType());
		connector.setDataType(source.getDataType());
		connector.setDataflowType(source.getDataflowType());
		if(source.getSubscriptionType()!=null) connector.setSubscriptionType(source.getSubscriptionType());
		if(source.getPushRate()!=null) connector.setPushInterval(source.getPushRate());
		connector.setTargetDataPort(getTargetPortforSave(source, factory, portBase));
		return connector;
	}

	private org.openrtp.namespaces.rts.ConfigurationSet saveConfigurationSet(ConfigurationSet baseConfig, ObjectFactory factory) {
		
		org.openrtp.namespaces.rts.ConfigurationSet config = factory.createConfigurationSet();
		config.setId(baseConfig.getId());
		List<NameValue> nvlistBase = baseConfig.getConfigurationData();
		if( nvlistBase!=null ) {
			for( Iterator iternv = nvlistBase.iterator(); iternv.hasNext(); ) {
				NameValue nvBase = (NameValue)iternv.next();
				ConfigurationData nv = factory.createConfigurationData();
				nv.setName(nvBase.getName());
				nv.setData(nvBase.getValueAsString());
				config.getConfigurationData().add(nv);
			}
		}
		return config;
	}

	private Location saveLocation(ComponentSpecification targetComp, ObjectFactory factory) {
		Location location = factory.createLocation();
		location.setX(BigInteger.valueOf(targetComp.getConstraint().getX()));
		location.setY(BigInteger.valueOf(targetComp.getConstraint().getY()));
		location.setWidth(BigInteger.valueOf(targetComp.getConstraint().getWidth()));
		location.setHeight(BigInteger.valueOf(targetComp.getConstraint().getHeight()));
		location.setDirection(targetComp.getOutportDirectionStr());
		return location;
	}

	private ComponentExt saveBasicInfo(ComponentSpecification targetComp, ObjectFactory factory) {
		ComponentExt component = factory.createComponentExt();
		component.setId(targetComp.getComponentId());
		component.setPathUri(targetComp.getPathURI());
		component.setInstanceName(targetComp.getInstanceNameL());
		if( targetComp.getActiveConfigurationSet() != null ) {
			component.setActiveConfigurationSet(
					targetComp.getActiveConfigurationSet().getId());
		}
		component.setCompositeType(targetComp.getCompsiteTypeStr());
		if(targetComp.getComponents().size()>0) {
			Participant participant = factory.createParticipant();
			for(Object childBase : targetComp.getComponents() ) {
				TargetComponent child = factory.createTargetComponent();
				child.setComponentId(((ComponentSpecification)childBase).getComponentId());
				child.setInstanceName(((ComponentSpecification)childBase).getInstanceNameL());
				participant.getParticipant().add(child);
			}
			component.setParticipants(participant);
		}
		if( targetComp.getActiveConfigurationSet()!=null ) {
			component.setActiveConfigurationSet(targetComp.getActiveConfigurationSet().getId());
		}
		component.setIsRequired(true);
		return component;
	}

	private TargetPort getTargetPortforSave(ConnectorProfile source, ObjectFactory factory, Port basePort) {
		ConnectorTarget target = ((PortConnectorSpecification)source.eContainer()).getTarget();
		if(target == basePort) {
			target = (ConnectorTarget)((PortConnectorSpecification)source.eContainer()).eContainer();
		}
		TargetPort port = factory.createTargetPort();
		port.setComponentId(((ComponentSpecification)target.eContainer()).getComponentId());
		port.setInstanceName(((ComponentSpecification)target.eContainer()).getInstanceNameL());
		port.setPortName(((Port)target).getPortProfile().getNameL());
		
		return port;
	}
}
