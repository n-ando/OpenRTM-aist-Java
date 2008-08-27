package jp.go.aist.rtm.toolscommon.profiles._test;

import jp.go.aist.rtm.toolscommon.profiles.util.YamlHandler;

import org.openrtp.namespaces.rtc.RtcProfile;

public class YAMLTest extends TestBase {

	public void testRtcYamlExport() throws Exception{
		String resourceDir = rootPath +  "\\resource\\RTC\\SampleYaml.yaml";
		String expected = readFile(resourceDir, "\r\n");
		//
		SampleProfileGenerator handle = new SampleProfileGenerator();
		RtcProfile profile = handle.generateProfile();
		YamlHandler handler = new YamlHandler();
		String result = null;
		try {
			result = handler.convertToYamlRtc(profile);
		} catch(Exception ex) {
			ex.printStackTrace();
			fail();
		}
		assertEquals(expected, result);
	}
	public void testRtcYamlImport() throws Exception{
		String resourceDir = rootPath +  "\\resource\\RTC\\SampleYaml.yaml";
		String expected = readFile(resourceDir, "\r\n");
		YamlHandler handler = new YamlHandler();
		RtcProfile profile = null;
		try {
			profile = handler.restoreFromYamlRtc(expected);
		} catch(Exception ex) {
			fail(ex.getCause().getMessage());
		}
		checkDetail(profile);
	}
	public void testRtcYamlNullImport() throws Exception{
		String resourceDir = rootPath +  "\\resource\\RTC\\SampleYamlNull.yaml";
		String expected = readFile(resourceDir, "\r\n");
		YamlHandler handler = new YamlHandler();
		RtcProfile profile = null;
		try {
			profile = handler.restoreFromYamlRtc(expected);
		} catch(Exception ex) {
			fail(ex.getCause().getMessage());
		}
	}
}
