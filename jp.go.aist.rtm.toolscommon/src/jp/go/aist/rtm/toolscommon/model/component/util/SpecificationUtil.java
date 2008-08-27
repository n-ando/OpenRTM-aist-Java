//package jp.go.aist.rtm.toolscommon.model.component.util;
//
//import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.StringReader;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Unmarshaller;
//import javax.xml.transform.stream.StreamSource;
//
//import jp.go.aist.rtm.toolscommon.model.component.ComponentFactory;
//import jp.go.aist.rtm.toolscommon.model.component.ComponentSpecification;
//import jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile;
//import jp.go.aist.rtm.toolscommon.model.component.NameValue;
//import jp.go.aist.rtm.toolscommon.model.component.Port;
//import jp.go.aist.rtm.toolscommon.model.component.PortProfile;
//import jp.go.aist.rtm.toolscommon.model.jaxb.RTC.DataPortImpl;
//import jp.go.aist.rtm.toolscommon.model.jaxb.RTC.RtcProfileImpl;
//import jp.go.aist.rtm.toolscommon.model.jaxb.RTC.ServicePortImpl;
//
//public class SpecificationUtil {
//
//	private static String PORTTYPE_IN = "DataInPort";
//
//	//OfflineEditorをRefreshのためのメッソド、
//	//Refresh機能がなくなったため、削除予定。
//	@Deprecated
//	public static void reloadComponent(String pathId, ComponentSpecification specification) {
////		Module module = loadFile(pathId, false);
////		module2Component(module, specification);
//	}
//	
//	public static RtcProfileImpl xmlRead2(String file, RtcProfileImpl module) {
//		try{
//			//
//			FileInputStream inputStream = null;
//			try {
//				inputStream = new FileInputStream(file);
//			} catch (Exception e){
//				
//			}
//			if (inputStream == null) {
//				return null;
//			}
//			 
//			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
//			String tmp_str = null;
//			StringBuffer tmp_sb = new StringBuffer();
//		    while((tmp_str = br.readLine()) != null){
//		    	tmp_sb.append(tmp_str + "\r\n");
//		    }
//		    br.close();
//
//			module = unmarshalXML(tmp_sb.toString());
//		
//		}catch(FileNotFoundException e){
//			System.out.println(e);
//		}catch(IOException e){
//			System.out.println(e);
//		}
//		return module;
//	}
//	
//	private static RtcProfileImpl unmarshalXML(String targetXML) {
//		RtcProfileImpl module = null;
//		try {
//			JAXBContext jc = JAXBContext.newInstance(RtcProfileImpl.class);
//			Unmarshaller unmarshaller = jc.createUnmarshaller();
//			module = (RtcProfileImpl)unmarshaller.unmarshal(new StreamSource(new StringReader(targetXML)));
//		} catch (JAXBException e) {
//			e.printStackTrace();
//		}
//		return module;	
//	}
//	
//	public static ComponentSpecification createComponent(RtcProfileImpl module) {
//		ComponentSpecification specification =  profile2ComponentEMF(module, null);
//		getEMFcomponents(specification, module);
//		return specification;
//	}
//
//	private static ComponentSpecification profile2ComponentEMF(RtcProfileImpl module,
//						ComponentSpecification specification) {
//		if (specification == null) {
//			specification  = ComponentFactory.eINSTANCE.createComponentSpecification();
//		}
//		try {
//			int type = Integer.parseInt(module.getBasic().getComponent_type());
//			specification.setCompsiteType(type);	
//		} catch (Exception e) {
//		}
//		specification.setInstanceNameL(module.getBasic().getName());
//		specification.getPorts().addAll(profile2PortEMF(module));
//	
//		return specification;
//	}
//
//	private static List getEMFcomponents(ComponentSpecification specification, RtcProfileImpl module){
//		List components = new ArrayList();
//		specification.getComponents().clear();
////		for (String componentPath : module.getProfile().getComponents()) {
////			RtcProfileImpl tempModule = SpecificationUtil.xmlRead(componentPath, module);
////			if (tempModule != null) {
////				ComponentSpecification tempCoSpecification = SpecificationUtil
////						.profile2ComponentEMF(tempModule, null);
////				tempCoSpecification.setPathId(componentPath);
////				specification.getComponents().add(tempCoSpecification);
////				components.add(tempCoSpecification);
////				components.addAll(getcomponents(tempCoSpecification, tempModule));
////			}
////		}
//		return components;
//	}
//
//	private static List<Port> profile2PortEMF(RtcProfileImpl module) {
//		List<Port> list = new ArrayList<Port>();
//		Port port = null;
//		for (Iterator iterator = module.getDataPort().iterator(); iterator.hasNext();) {
//			DataPortImpl dataPort = (DataPortImpl)iterator.next();
//			if( dataPort.getPort_type().equals(PORTTYPE_IN)) {
//				port = ComponentFactory.eINSTANCE.createInPort();
//			} else {
//				port = ComponentFactory.eINSTANCE.createOutPort();
//			}
//			PortProfile profile = ComponentFactory.eINSTANCE.createPortProfile();
//			port.setPortProfile(profile);
//			port.getPortProfile().setNameL(dataPort.getName());
//			NameValue nv = ComponentFactory.eINSTANCE.createNameValue();
//			nv.setName(ConnectorProfile.NAME_VALUE_KEY_PORT_PORT_TYPE);
//			nv.setValueAsString(dataPort.getPort_type());
//			port.getPortProfile().getProperties().add(nv);
//
//			nv = ComponentFactory.eINSTANCE.createNameValue();
//			nv.setName(ConnectorProfile.NAME_VALUE_KEY_DATAPORT_DATA_TYPE);
//			nv.setValueAsString(dataPort.getType());
////		nv = ComponentFactory.eINSTANCE.createNameValue();
////		nv.setName(ConnectorProfile.NAME_VALUE_KEY_DATAPORT_DATA_TYPE);
////		nv.setValueAsString(dataPort.getType());
////		port.getPortProfile().getProperties().add(nv);
////			
////		nv = ComponentFactory.eINSTANCE.createNameValue();
////		nv.setName(ConnectorProfile.NAME_VALUE_KEY_DATAPORT_DATAFLOW_TYPE);
////		nv.setValueAsString(dataPort.getType());
////		port.getPortProfile().getProperties().add(nv);
//			//
////		nv = ComponentFactory.eINSTANCE.createNameValue();
////		nv.setName(ConnectorProfile.NAME_VALUE_KEY_DATAPORT_SUBSCRIPTION_TYPE);
////		nv.setValueAsString(dataPort.getType());
////		port.getPortProfile().getProperties().add(nv);
////		
////		nv = ComponentFactory.eINSTANCE.createNameValue();
////		nv.setName(ConnectorProfile.NAME_VALUE_KEY_DATAPORT_INTERFACE_TYPE);
////		nv.setValueAsString(dataPort.getType());
////		port.getPortProfile().getProperties().add(nv);
//			list.add(port);
//		}
//		for (Iterator iterator = module.getServicePort().iterator(); iterator.hasNext();) {
//			ServicePortImpl servicePortImpl = (ServicePortImpl) iterator.next();
//	
//			port = ComponentFactory.eINSTANCE.createServicePort();
//			PortProfile profile = ComponentFactory.eINSTANCE.createPortProfile();
//			port.setPortProfile(profile);
//			port.getPortProfile().setNameL(servicePortImpl.getName());
//			list.add(port);
//		}
//		return list;
//	}
//
//}
