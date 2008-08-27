package jp.go.aist.rtm.rtctemplate._test;

import java.io.File;

import jp.go.aist.rtm.rtctemplate.generator.PreProcessor;
import junit.framework.TestCase;

public class PreProcessorTest extends TestBase {
	private PreProcessor target;

	@Override
	protected void setUp() throws Exception {
		target = new PreProcessor();
	}

	public void testIsInclude() throws Exception {
		String result;

		result = target.getIncludeFileContent("#include <test.txt>", new File(rootPath + "\\resource"));
		assertEquals("testTextContents\n", result);

		result = target.getIncludeFileContent("#include  \"test.txt\"",
				new File(rootPath + "\\resource"));
		assertEquals("testTextContents\n", result);

	}

	public void testDefault() throws Exception {
		String result;

		result = target.parse("", null);
		assertEquals("", result);

		result = target.parse(
				"\n#IFDEF \n#IFDEF HOGE_TYPE \nhoge\n// #comment#\n#ENDIF",
				null);
		assertEquals("\n\n\nhoge\n// #comment#\n", result);

		result = target.parse(
				"\n#IFDEF \n#IFDEF HOGE_TYPE \nhoge\n// #comment#\n#ENDIF",
				null);
		assertEquals("\n\n\nhoge\n// #comment#\n", result);

	}
}
