package jp.go.aist.rtm.toolscommon.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jp.go.aist.rtm.toolscommon.model.component.ComponentFactory;
import jp.go.aist.rtm.toolscommon.model.component.ComponentSpecification;
import jp.go.aist.rtm.toolscommon.model.component.ConnectorProfile;
import jp.go.aist.rtm.toolscommon.model.component.NameValue;
import jp.go.aist.rtm.toolscommon.model.component.Port;
import jp.go.aist.rtm.toolscommon.model.component.PortProfile;
import jp.go.aist.rtm.toolscommon.profiles.util.XmlHandler;

import org.openrtp.namespaces.rtc.DataportExt;
import org.openrtp.namespaces.rtc.RtcProfile;
import org.openrtp.namespaces.rtc.ServiceinterfaceDoc;
import org.openrtp.namespaces.rtc.ServiceportExt;

import RTC.ComponentProfile;
import RTC.PortInterfacePolarity;
import RTC.PortInterfaceProfile;
//
public class ProfileHandler {
	private static String PORTTYPE_IN = "DataInPort";
	private static String PORTTYPE_OUT = "DataOutPort";
//
//	private static final String RTCXmlSchema = "RtcProfile_ext.xsd"; 
	private static final String SPEC_SUFFIX = "RTC";
	private static final String SPEC_MAJOR_SEPARATOR = ":";
	private static final String SPEC_MINOR_SEPARATOR = ".";
	
	private static final String INTERFACE_DIRECTION_PROVIDED = "Provided";
	private static final String INTERFACE_DIRECTION_REQUIRED = "Required";

	public static boolean validateXml(String targetString) throws Exception {
		XmlHandler handler = new XmlHandler();
		try {
			RtcProfile profile = handler.restoreFromXmlRtc(targetString);
		} catch (IOException e) {
			throw new Exception("XML Validation Error", e);
		}
		return true;
	}

	public ComponentSpecification createComponentFromXML(String targetXML) throws Exception {
		XmlHandler handler = new XmlHandler();
		RtcProfile profile = handler.restoreFromXmlRtc(targetXML);
		
		ComponentSpecification component = profile2ComponentEMF(profile, null);
		return component;
	}

	public ComponentSpecification createComponent(String targetFile) throws Exception  {
		
		//ëŒè€ÉtÉ@ÉCÉãÇÃì«Ç›çûÇ›
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(targetFile), "UTF-8"));
		String tmp_str = null;
		StringBuffer tmp_sb = new StringBuffer();
	    while((tmp_str = br.readLine()) != null){
	    	tmp_sb.append(tmp_str + "\r\n");
	    }
	    br.close();

	    ComponentSpecification component = createComponentFromXML(tmp_sb.toString());
		return component;
	}
	private ComponentSpecification profile2ComponentEMF(RtcProfile module,
			ComponentSpecification specification) {
		if (specification == null) {
			specification  = ComponentFactory.eINSTANCE.createComponentSpecification();
		}
		specification.setCompsiteType(0);	
		specification.setInstanceNameL(module.getBasicInfo().getName());
		ComponentProfile cmpProfile = new ComponentProfile();
		cmpProfile.type_name = module.getBasicInfo().getName();
		cmpProfile.category = module.getBasicInfo().getCategory();
		cmpProfile.vendor = module.getBasicInfo().getVendor();
		cmpProfile.version = module.getBasicInfo().getVersion();
		cmpProfile.description = module.getBasicInfo().getDescription();
		specification.setRTCComponentProfile(cmpProfile);
		specification.getPorts().addAll(profile2PortEMF(module));
		
		String moduleId = SPEC_SUFFIX + SPEC_MAJOR_SEPARATOR +
							cmpProfile.vendor + SPEC_MINOR_SEPARATOR +
							cmpProfile.category + SPEC_MINOR_SEPARATOR +
							cmpProfile.type_name + SPEC_MAJOR_SEPARATOR +
							cmpProfile.version;
		specification.setComponentId(moduleId);
		
		return specification;
	}

	private List<Port> profile2PortEMF(RtcProfile module) {
		List<Port> list = new ArrayList<Port>();
		Port port = null;
		for( Iterator iterator = module.getDataPorts().iterator(); iterator.hasNext(); ) {
			DataportExt dataPort = (DataportExt)iterator.next();
			if( dataPort.getPortType().equals(PORTTYPE_IN)) {
				port = ComponentFactory.eINSTANCE.createInPort();
			} else {
				port = ComponentFactory.eINSTANCE.createOutPort();
			}
			PortProfile profile = ComponentFactory.eINSTANCE.createPortProfile();
			port.setPortProfile(profile);
			port.getPortProfile().setNameL(dataPort.getName());
			NameValue nv = ComponentFactory.eINSTANCE.createNameValue();
			nv.setName(ConnectorProfile.NAME_VALUE_KEY_PORT_PORT_TYPE);
			nv.setValueAsString(dataPort.getPortType());
			port.getPortProfile().getProperties().add(nv);

			nv = ComponentFactory.eINSTANCE.createNameValue();
			nv.setName(ConnectorProfile.NAME_VALUE_KEY_DATAPORT_DATA_TYPE);
			nv.setValueAsString(dataPort.getType());
			port.getPortProfile().getProperties().add(nv);
			
			list.add(port);
		}
		for (Iterator iterator = module.getServicePorts().iterator(); iterator.hasNext();) {
			ServiceportExt servicePortExt = (ServiceportExt) iterator.next();
	
			port = ComponentFactory.eINSTANCE.createServicePort();
			PortProfile profile = ComponentFactory.eINSTANCE.createPortProfile();
			port.setPortProfile(profile);
			port.getPortProfile().setNameL(servicePortExt.getName());

			NameValue nv = ComponentFactory.eINSTANCE.createNameValue();
			nv.setName(ConnectorProfile.NAME_VALUE_KEY_PORT_PORT_TYPE);
			nv.setValueAsString(ConnectorProfile.NAME_VALUE_KEY_PORT_PORT_TYPE_SERVICE_PORT_VALUE);
			port.getPortProfile().getProperties().add(nv);
			
			RTC.PortInterfaceProfile[] serviceIFs = new RTC.PortInterfaceProfile[servicePortExt.getServiceInterface().size()];
			for( int intIdx=0; intIdx<servicePortExt.getServiceInterface().size(); intIdx++) {
				ServiceinterfaceDoc serviceIF = (ServiceinterfaceDoc)servicePortExt.getServiceInterface().get(intIdx);
				PortInterfaceProfile portIF = new PortInterfaceProfile();
				portIF.instance_name = serviceIF.getName();
				portIF.type_name = serviceIF.getType();
				if( serviceIF.getDirection().equals(INTERFACE_DIRECTION_PROVIDED) ) {
					portIF.polarity = PortInterfacePolarity.PROVIDED;
				} else if( serviceIF.getDirection().equals(INTERFACE_DIRECTION_REQUIRED) ) {
					portIF.polarity = PortInterfacePolarity.REQUIRED;
				}
				serviceIFs[intIdx] = portIF;
			}
			RTC.PortProfile rtcPort = new RTC.PortProfile();
			rtcPort.name = servicePortExt.getName();
			rtcPort.interfaces = serviceIFs;
			port.getPortProfile().setRtcPortProfile(rtcPort);
			list.add(port);
		}
		return list;
	}
}
