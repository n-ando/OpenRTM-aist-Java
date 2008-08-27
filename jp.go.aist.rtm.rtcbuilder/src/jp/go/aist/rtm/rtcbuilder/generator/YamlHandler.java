//package jp.go.aist.rtm.rtcbuilder.generator;
//
//import java.io.BufferedInputStream;
//import java.io.BufferedWriter;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStreamWriter;
//import java.util.Map;
//
//import jp.go.aist.rtm.rtcbuilder.generator.param.GeneratorParam;
//import jp.go.aist.rtm.rtcbuilder.generator.param.ParamUtil;
//import jp.go.aist.rtm.rtcbuilder.generator.param.RtcParam;
//import jp.go.aist.rtm.rtcbuilder.model.jaxb.RtcProfileHolderImpl;
//import jp.go.aist.rtm.rtcbuilder.model.jaxb.RtcProfileImpl;
//
//import org.ho.yaml.Yaml;
//
//
//public class YamlHandler {
//	private String targetFile;
//	private GeneratorParam targetRtc;
//	
//	final private String prefixClass = "- !"; 
//	
//	public YamlHandler(String target, GeneratorParam generatorParam) {
//		this.targetFile = target;
//		this.targetRtc = generatorParam;
//	}
//	
//	public void createYaml() throws IOException {
//		RtcProfileImpl profile = ParamUtil.convertToModule(targetRtc);
//		RtcProfileHolderImpl holder = new RtcProfileHolderImpl();
//		holder.setRtc_profile(profile);
//		
//		String yamlText = Yaml.dump(holder, true);
//		String[] yamlSplt = removeClassInfo(yamlText);
//		if( yamlSplt.length > 0 ) {
//			BufferedWriter outputFile = new BufferedWriter(
//					new OutputStreamWriter(new FileOutputStream(targetFile), "UTF-8"));
//			for(int intIdx=0;intIdx<yamlSplt.length;intIdx++) {
//				outputFile.write(yamlSplt[intIdx]);
//				outputFile.newLine();
//			}
//			outputFile.close();
//		}
//	}
//	
//	public GeneratorParam readYaml() throws FileNotFoundException {
//		BufferedInputStream inputFile = null;
//		inputFile = new BufferedInputStream(new FileInputStream(targetFile));
//		Map yamlMap  = (Map)Yaml.load(inputFile);
//		RtcProfileImpl profile = ParamUtil.convertFromYaml(yamlMap);
//		GeneratorParam generatorParam = new GeneratorParam();
//		RtcParam rtcParam = ParamUtil.convertFromModule(profile, generatorParam);
//		//
////        rtcParam.setRtcXml(tmp_sb.toString());
//        //
//		generatorParam.getRtcParams().add(rtcParam);
//		return generatorParam;
//		
//	}
//	private String[] removeClassInfo(String strInput) {
//		String lineSeparator = System.getProperty( "line.separator" );
//		if( lineSeparator==null || lineSeparator.equals("") ) lineSeparator = "\n";
//		String splitStr[] = strInput.split(lineSeparator);
//
//		String strWork = "";
//		for( int intidx=0;intidx<splitStr.length;intidx++ ) {
//			strWork = splitStr[intidx];
//			if(strWork.trim().startsWith(prefixClass)) {
//				int end = strWork.indexOf('-'); 
//				splitStr[intidx] = strWork.substring(0,end+1);
//			}
//		}
//		return splitStr;
//	}
//}
