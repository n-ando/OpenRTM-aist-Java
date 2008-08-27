package jp.go.aist.rtm.rtcbuilder._test.cxx;

import java.util.HashMap;
import java.util.Map;

import jp.go.aist.rtm.rtcbuilder.generator.parser.MergeBlockParser;
import junit.framework.TestCase;

public class MergeBlockParserTest extends TestCase {

	public void testSingleLineParse() throws Exception {
//		MergeBlockParser target = new MergeBlockParser();
		String string;
		Map result;

		string = "<rtc-template block=\"module\"> hoge </rtc-template>";
		result = MergeBlockParser.parse(string);
		assertEquals(" hoge ", result.get("module"));
	}

	public void testMultiLineParse() throws Exception {
//		MergeBlockParser target = new MergeBlockParser();
		String string;
		Map result;

		string = "";
		result = MergeBlockParser.parse(string);
		assertEquals(null, result.get("hoge"));

		string = "<rtc-template block=\"module\">\n</rtc-template>";
		result = MergeBlockParser.parse(string);
		assertEquals("", result.get("module"));

		string = "<rtc-template block=\"module\">\nhoge\n</rtc-template>";
		result = MergeBlockParser.parse(string);
		assertEquals("hoge\n", result.get("module"));

		string = "<rtc-template block=\"module2\">\nhoge\n</rtc-template>";
		result = MergeBlockParser.parse(string);
		assertEquals("hoge\n", result.get("module2"));

		string = "<rtc-template block=\"module2\">\nhoge\n</rtc-template>";
		result = MergeBlockParser.parse(string);
		assertEquals("hoge\n", result.get("module2"));

		string = "<RTC-TEMPLATE BLOCK=\"module2\">\nhoge\n</RTC-TEMPLATE>";
		result = MergeBlockParser.parse(string);
		assertEquals("hoge\n", result.get("module2"));

		string = "<rtc-template \t block \t= \t \"module2\" \t >\nhoge\n</ \t rtc-template \t >";
		result = MergeBlockParser.parse(string);
		assertEquals("hoge\n", result.get("module2"));

		string = "// <rtc-template block=\"module\">\nhoge\n</rtc-template> \n // <rtc-template block=\"module2\">\nhoge2\n</rtc-template>";
		result = MergeBlockParser.parse(string);
		assertEquals("hoge\n", result.get("module"));
		assertEquals("hoge2\n", result.get("module2"));

	}

	public void testSingleLineMerge() throws Exception {
//		MergeBlockParser target = new MergeBlockParser();
		String result;
		String targetString;
		Map<String, String> mergeMap;

		mergeMap = new HashMap<String, String>();
		targetString = "<rtc-template block=\"module\">hoge</rtc-template>";
		result = MergeBlockParser.merge(targetString, mergeMap);
		assertEquals("<rtc-template block=\"module\"></rtc-template>", result);

		mergeMap = new HashMap<String, String>();
		mergeMap.put("module", "HOGE");
		mergeMap.put("module2", "HOGE2\n");
		targetString = "<rtc-template block=\"module\">hoge</rtc-template>\n"
				+ "<rtc-template block=\"module2\">\n" + "hoge2\n" + "</rtc-template>";
		result = MergeBlockParser.merge(targetString, mergeMap);
		assertEquals("<rtc-template block=\"module\">HOGE</rtc-template>\n"
				+ "<rtc-template block=\"module2\">\n" + "HOGE2\n" + "</rtc-template>", result);
	}

	public void testMultiLineMerge() throws Exception {
//		MergeBlockParser target = new MergeBlockParser();
		String result;
		String targetString;
		Map<String, String> mergeMap;

		mergeMap = new HashMap<String, String>();
		targetString = "";
		result = MergeBlockParser.merge(targetString, mergeMap);
		assertEquals("", result);

		mergeMap = new HashMap<String, String>();
		targetString = "<rtc-template block=\"module\">\n</rtc-template>";
		result = MergeBlockParser.merge(targetString, mergeMap);
		assertEquals("<rtc-template block=\"module\">\n\n\n</rtc-template>", result);

		mergeMap = new HashMap<String, String>();
		mergeMap.put("module", "MODULE\n");
		targetString = "<rtc-template block=\"module\">\n</rtc-template>";
		result = MergeBlockParser.merge(targetString, mergeMap);
		assertEquals("<rtc-template block=\"module\">\nMODULE\n</rtc-template>", result);

		mergeMap = new HashMap<String, String>();
		mergeMap.put("module", "hoge\nhoge2\n");
		targetString = " // <rtc-template block=\"module\">  \n#####\n // </rtc-template>";
		result = MergeBlockParser.merge(targetString, mergeMap);
		assertEquals(
				" // <rtc-template block=\"module\">  \nhoge\nhoge2\n // </rtc-template>",
				result);

		mergeMap = new HashMap<String, String>();
		mergeMap.put("module", "hoge\n");
		mergeMap.put("module2", "\nhoge2\n");
		targetString = "// <rtc-template block=\"module\">  \n\n PRE \n\n//</rtc-template><rtc-template block=\"module2\">\n</rtc-template>";
		result = MergeBlockParser.merge(targetString, mergeMap);
		assertEquals(
				"// <rtc-template block=\"module\">  \nhoge\n//</rtc-template><rtc-template block=\"module2\">\n\nhoge2\n</rtc-template>",
				result);

	}

}
