package jp.go.aist.rtm.toolscommon.profiles._test;

import jp.go.aist.rtm.toolscommon.profiles.util.XmlHandler;

import org.openrtp.namespaces.rtc.RtcProfile;

public class XMLTest extends TestBase {

	public void testRtcXmlExport() {

		String resourceDir = rootPath +  "\\resource\\RTC\\SampleXML.xml";
		String expected = readFile(resourceDir,"\n");
		
		SampleProfileGenerator handle = new SampleProfileGenerator();
		RtcProfile profile = handle.generateProfile();
		XmlHandler handler = new XmlHandler();
		String result = null;
		try {
			result = handler.convertToXmlRtc(profile);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		assertEquals(expected, result);
	}
	
	public void testRtcXmlImport() throws Exception{
		String resourceDir = rootPath +  "\\resource\\RTC\\SampleXML.xml";
		String expected = readFile(resourceDir,"\n");
		XmlHandler handler = new XmlHandler();
		RtcProfile profile = null;
		try {
			profile = handler.restoreFromXmlRtc(expected);
		} catch(Exception ex) {
			fail(ex.getCause().getMessage());
		}
		checkDetail(profile);
	}
}
