package jp.go.aist.rtm.toolscommon.profiles.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.openrtp.namespaces.rtc.ObjectFactory;
import org.openrtp.namespaces.rtc.RtcProfile;
import org.openrtp.namespaces.rts.RtsProfile;
import org.openrtp.namespaces.rts.RtsProfileExt;

public class XmlHandler {
	
	public RtsProfileExt loadXmlRts(String targetFile) throws Exception {
		
		StringBuffer stbRet = new StringBuffer();
		try{
			InputStreamReader isr = new InputStreamReader(new FileInputStream(targetFile), "UTF-8");
			BufferedReader br = new BufferedReader(isr);
	
			String str = new String();
			while( (str = br.readLine()) != null ){
				stbRet.append(str + "\n");
			}
			br.close();
			isr.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		return restoreFromXmlRts(stbRet.toString());
	}

	public RtsProfileExt restoreFromXmlRts(String targetXML) throws Exception {
		JAXBContext jc = JAXBContext.newInstance("org.openrtp.namespaces.rts");
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
	    StringReader xmlReader = new StringReader(targetXML);
		RtsProfileExt profile = (RtsProfileExt)unmarshaller.unmarshal(xmlReader);
		return profile;
	}
	
	public boolean saveXmlRts(RtsProfile profile, String targetFile) throws Exception {
		String xmlString = convertToXmlRts(profile);

		String lineSeparator = System.getProperty( "line.separator" );
		if( lineSeparator==null || lineSeparator.equals("") ) lineSeparator = "\n";
		String xmlSplit[] = xmlString.split(lineSeparator);

		BufferedWriter outputFile = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(targetFile), "UTF-8"));
		for(int intIdx=0;intIdx<xmlSplit.length;intIdx++) {
			outputFile.write(xmlSplit[intIdx]);
			outputFile.newLine();
		}
		outputFile.close();
		
		return true;
	}
	
	public String convertToXmlRts(RtsProfile profile) throws Exception {
	    String xmlString = "";
		try {
			ObjectFactory objFactory = new ObjectFactory();
			JAXBContext jaxbContext = JAXBContext.newInstance("org.openrtp.namespaces.rts");
			Marshaller marshaller = jaxbContext.createMarshaller();
		    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT , new Boolean(true));
		    marshaller.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper",
	                new NamespacePrefixMapperImpl("http://www.openrtp.org/namespaces/rts"));
		    StringWriter xmlFileWriter = new StringWriter();
		    marshaller.marshal(profile, xmlFileWriter);
		    xmlString = xmlFileWriter.toString();
		} catch (JAXBException exception) {
			throw new Exception("XML‚Ö‚Ì•ÏŠ·‚ÉŽ¸”s‚µ‚Ü‚µ‚½B", exception);
		}
		return xmlString;
	}
	//
	public static boolean validateXmlRtc(String targetString) throws Exception {
		try {
			JAXBContext jc = JAXBContext.newInstance(RtcProfile.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();

			SchemaFactory sf = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);  
			Schema schema = sf.newSchema(new File("schema/RtcProfile_ext.xsd")); 
			unmarshaller.setSchema(schema);

			RtcProfile profile = (RtcProfile)((JAXBElement)unmarshaller.unmarshal(new StreamSource(new FileReader(targetString)))).getValue();
		} catch (JAXBException e) {
			throw new Exception("XML Validation Error", e);
		} catch (IOException e) {
			throw new Exception("XML Validation Error", e);
		}
		return true;
	}

	public RtcProfile restoreFromXmlRtc(String targetXML) throws Exception {
		JAXBContext jc = JAXBContext.newInstance("org.openrtp.namespaces.rtc");
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
	    StringReader xmlReader = new StringReader(targetXML);
		RtcProfile profile = (RtcProfile)((JAXBElement)unmarshaller.unmarshal(xmlReader)).getValue();
		return profile;
	}
	
	public String convertToXmlRtc(RtcProfile profile) throws Exception {
	    String xmlString = "";
		try {
			ObjectFactory objFactory = new ObjectFactory();
			JAXBContext jaxbContext = JAXBContext.newInstance("org.openrtp.namespaces.rtc");
			Marshaller marshaller = jaxbContext.createMarshaller();
		    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT , new Boolean(true));
		    marshaller.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper",
	                new NamespacePrefixMapperImpl("http://www.openrtp.org/namespaces/rtc"));
		    StringWriter xmlFileWriter = new StringWriter();
		    marshaller.marshal(profile, xmlFileWriter);
		    xmlString = xmlFileWriter.toString();
		} catch (JAXBException exception) {
			throw new Exception("XML‚Ö‚Ì•ÏŠ·‚ÉŽ¸”s‚µ‚Ü‚µ‚½B", exception);
		}
		return xmlString;
	}
}
