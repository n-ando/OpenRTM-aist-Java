package jp.go.aist.rtm.rtcbuilder.generator;

import java.io.BufferedReader;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import org.openrtp.namespaces.rtc.RtcProfile;

import jp.go.aist.rtm.rtcbuilder.generator.param.GeneratorParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.ParamUtil;
import jp.go.aist.rtm.rtcbuilder.generator.param.RtcParam;
import jp.go.aist.rtm.toolscommon.profiles.util.XmlHandler;
import jp.go.aist.rtm.toolscommon.profiles.util.YamlHandler;

public class ProfileHandler {
//	
//	public static final String RTCXmlModel = "jp.go.aist.rtm.rtcbuilder.model.jaxb"; 
//	private static final String RTCXmlSchema = "RtcProfile_ext.xsd"; 
//
	public boolean validateXml(String targetString) throws Exception {
		XmlHandler handler = new XmlHandler();
		handler.restoreFromXmlRtc(targetString);
		return true;
	}

	public RtcProfile restorefromXML(String targetXML) throws Exception {
		XmlHandler handler = new XmlHandler();
		RtcProfile profile = handler.restoreFromXmlRtc(targetXML);
		return profile;
	}
//	public static RtcProfileImpl unmarshalXML(String targetXML) {
//		RtcProfileImpl profile = null;
//		try {
//			JAXBContext jc = JAXBContext.newInstance(RTCXmlModel);
//			Unmarshaller unmarshaller = jc.createUnmarshaller();
//			profile = ((JAXBElement<RtcProfileImpl>)unmarshaller.unmarshal(new StreamSource(new StringReader(targetXML)))).getValue();
//		} catch (JAXBException e) {
//			e.printStackTrace();
//		}
//		return profile;
//		
//	}
//
	public GeneratorParam restorefromXMLFile(String filePath) throws Exception {
		GeneratorParam generatorParam = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
			String tmp_str = null;
			StringBuffer tmp_sb = new StringBuffer();
		    while((tmp_str = br.readLine()) != null){
		    	tmp_sb.append(tmp_str + "\r\n");
		    }
		    br.close();

		    XmlHandler handler = new XmlHandler();
		    RtcProfile profile = handler.restoreFromXmlRtc(tmp_sb.toString());

			generatorParam = new GeneratorParam();
			RtcParam rtcParam = ParamUtil.convertFromModule(profile, generatorParam);
		    rtcParam.setRtcXml(tmp_sb.toString());
			generatorParam.getRtcParams().add(rtcParam);
		} catch (FileNotFoundException e) {
			throw new Exception(
					"ファイルの読み込みに失敗しました。\r\nRTCBuilder以外のファイルが読み込まれていないか確認してください",
					e);
		} catch (IOException e) {
			throw new Exception(
					"ファイルの読み込みに失敗しました。\r\nRTCBuilder以外のファイルが読み込まれていないか確認してください",
					e);
		}
		return generatorParam;
	}
	
	public String convert2XML(GeneratorParam generatorParam) {
	    String xmlFile = "";
		try {
			RtcProfile profile = ParamUtil.convertToModule(generatorParam);
			XmlHandler handler = new XmlHandler();
			xmlFile = handler.convertToXmlRtc(profile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xmlFile;
	}

	public 	String createInitialRtcXml(String creationDate) {
		String result = "";
		RtcProfile profile = ParamUtil.initialXml(creationDate);
		XmlHandler handler = new XmlHandler();
		try {
			result = handler.convertToXmlRtc(profile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void storeToXML(String filePath, GeneratorParam generatorParam) throws Exception {

		RtcProfile profile = ParamUtil.convertToModule(generatorParam);
		XmlHandler handler = new XmlHandler();
		
		String xmlString = handler.convertToXmlRtc(profile);
		BufferedWriter outputFile = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"));
		String lineSeparator = System.getProperty( "line.separator" );
		if( lineSeparator==null || lineSeparator.equals("") ) lineSeparator = "\n";
		String splitStr[] = xmlString.split(lineSeparator);
		for(int intIdx=0;intIdx<splitStr.length;intIdx++) {
			outputFile.write(splitStr[intIdx]);
			outputFile.newLine();
		}
		outputFile.close();
	}
	
	//YAML
	public void createYaml(String targetFile, GeneratorParam targetRtc) throws Exception {
		RtcProfile profile = ParamUtil.convertToModule(targetRtc);
		YamlHandler handler = new YamlHandler();
		String yamlText = handler.convertToYamlRtc(profile);
		String lineSeparator = System.getProperty( "line.separator" );
		if( lineSeparator==null || lineSeparator.equals("") ) lineSeparator = "\n";
		String[] yamlSplt = yamlText.split(lineSeparator);
		if( yamlSplt.length > 0 ) {
			BufferedWriter outputFile = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(targetFile), "UTF-8"));
			for(int intIdx=0;intIdx<yamlSplt.length;intIdx++) {
				outputFile.write(yamlSplt[intIdx]);
				outputFile.newLine();
			}
			outputFile.close();
		}
	}
	public GeneratorParam readYaml(String targetFile) throws FileNotFoundException {
		BufferedInputStream inputFile = null;
		inputFile = new BufferedInputStream(new FileInputStream(targetFile));
		YamlHandler handler = new YamlHandler();
		RtcProfile profile = handler.restoreFromYamlRtc(inputFile);
		GeneratorParam generatorParam = new GeneratorParam();
		RtcParam rtcParam = ParamUtil.convertFromModule(profile, generatorParam);
		generatorParam.getRtcParams().add(rtcParam);
		return generatorParam;
		
	}
}
