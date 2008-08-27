package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import jp.go.aist.rtm.toolscommon.profiles._test.SampleProfileGenerator;
import jp.go.aist.rtm.toolscommon.profiles.util.NamespacePrefixMapperImpl;

import org.ho.yaml.Yaml;
import org.openrtp.namespaces.RtcProfileHolder;
import org.openrtp.namespaces.rtc.ObjectFactory;
import org.openrtp.namespaces.rtc.RtcProfile;

public class XMLTest {

	/**
	 * @param args
	 * createExtendedMetaDataAnnotationsÇÃñºèÃïœçX
	 */
	public static void main(String[] args) {
//		XMLoutputSample();
//		YAMLoutputSample();
//		XMLinputSample();
//		YAMLinputSample();
		try {
			validateXml("resource/RTC/SampleXMLNull.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void YAMLinputSample() {
//		BufferedInputStream inputFile = null;
//		try {
//			inputFile = new BufferedInputStream(new FileInputStream("testY1.yaml"));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
////		Map yamlMap  = (Map)Yaml.load(inputFile);
//		YamlHandler handler = new YamlHandler();
//		RtcProfile profile = handler.restoreFromYamlRtc(inputFile);
//		System.out.println("aaa");
	}
	private static void YAMLoutputSample() {
		SampleProfileGenerator handle = new SampleProfileGenerator();
		RtcProfile profile = handle.generateProfile();
		RtcProfileHolder holder = new RtcProfileHolder();
		holder.setRtcProfile(profile);

		String yamlText = null;
		yamlText = Yaml.dump(holder,true);
//		YamlConfig config = YamlConfig.getDefaultConfig();
//		BiDirectionalMap<String, String> test = new BiDirectionalMap<String, String>();
//		test.put("org.openrtp.namespaces.rtc.impl.ActionsImpl", "aaa");
//		config.setTransfers(test);
//		config.dump(holder);
		
		System.out.println(yamlText);
		String[] yamlSplt = removeClassInfo(yamlText);
		if( yamlSplt.length > 0 ) {
			BufferedWriter outputFile;
			try {
				outputFile = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream("testY1.yaml"), "UTF-8"));
				for(int intIdx=0;intIdx<yamlSplt.length;intIdx++) {
					outputFile.write(yamlSplt[intIdx]);
					outputFile.newLine();
				}
				outputFile.close();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	final static private String prefixClass = ": !";
	final static private String prefixClass2 = "- !";
	private static String[] removeClassInfo(String strInput) {
		String lineSeparator = System.getProperty( "line.separator" );
		if( lineSeparator==null || lineSeparator.equals("") ) lineSeparator = "\n";
		String splitStr[] = strInput.split(lineSeparator);

		String strWork = "";
		for( int intidx=0;intidx<splitStr.length;intidx++ ) {
			strWork = splitStr[intidx];
			if(strWork.trim().contains(prefixClass)) {
				int end = strWork.indexOf(prefixClass); 
				splitStr[intidx] = strWork.substring(0,end+1);
			}
			if(strWork.trim().startsWith(prefixClass2)) {
				int end = strWork.indexOf(prefixClass2); 
				splitStr[intidx] = strWork.substring(0,end+1);
			}
		}
		return splitStr;
	}

	private static void XMLoutputSample() {
		SampleProfileGenerator handle = new SampleProfileGenerator();
		//èoóÕ
		RtcProfile profile = handle.generateProfile();
		//
	    String xmlString = "";
		try {
			ObjectFactory objFactory = new ObjectFactory();
//			JAXBContext jaxbContext = JAXBContext.newInstance("org.openrtp.namespaces.rtc");
			JAXBContext jaxbContext = JAXBContext.newInstance(RtcProfile.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
		    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT , new Boolean(true));
		    marshaller.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper",
	                new NamespacePrefixMapperImpl("http://www.openrtp.org/namespaces/rtc"));
		    StringWriter xmlFileWriter = new StringWriter();
		    marshaller.marshal(profile, xmlFileWriter);
		    xmlString = xmlFileWriter.toString();
		} catch (JAXBException exception) {
			System.out.println("XMLÇ÷ÇÃïœä∑Ç…é∏îsÇµÇ‹ÇµÇΩÅB" + exception);
		}

		BufferedWriter outputFile;
		try {
			outputFile = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream("test.xml"), "UTF-8"));
			String lineSeparator = System.getProperty( "line.separator" );
			if( lineSeparator==null || lineSeparator.equals("") ) lineSeparator = "\n";
			String splitStr[] = xmlString.split(lineSeparator);
			for(int intIdx=0;intIdx<splitStr.length;intIdx++) {
				outputFile.write(splitStr[intIdx]);
				outputFile.newLine();
			}
			outputFile.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	public static boolean validateXml(String targetString) throws Exception {
		try {
			JAXBContext jc = JAXBContext.newInstance("org.openrtp.namespaces.rtc");
			Unmarshaller unmarshaller = jc.createUnmarshaller();

			SchemaFactory sf = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);  
			Schema schema = sf.newSchema(new File("schema/RtcProfile_ext.xsd")); 
			unmarshaller.setSchema(schema);

			RtcProfile profile = (RtcProfile)((JAXBElement)unmarshaller.unmarshal(new StreamSource(new FileReader(targetString)))).getValue();
			System.out.println("aaa");
		} catch (JAXBException e) {
			throw new Exception("XML Validation Error", e);
		} catch (IOException e) {
			throw new Exception("XML Validation Error", e);
		}
		return true;
	}

	private static void XMLinputSample() {
		try {
			JAXBContext jc = JAXBContext.newInstance("org.openrtp.namespaces.rtc");
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			unmarshaller.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
		    StringReader xmlReader = new StringReader(readFile("test.xml"));
			RtcProfile profile = (RtcProfile)((JAXBElement)unmarshaller.unmarshal(xmlReader)).getValue();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("aaa");
	}
	
	protected static String readFile(String fileName) {
		StringBuffer stbRet = new StringBuffer();
		try{
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
	
			String str = new String();
			while( (str = br.readLine()) != null ){
				stbRet.append(str + "\n");
			}
			br.close();
			fr.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		return stbRet.toString();
	}
	
}
