package jp.go.aist.rtm.rtcbuilder._test.cxx;

import java.io.File;

import jp.go.aist.rtm.rtcbuilder._test.TestBase;
import jp.go.aist.rtm.rtcbuilder.generator.PreProcessor;

public class PreProcessorTest extends TestBase {

	@Override
	protected void setUp() throws Exception {
	}

	public void testIsInclude() throws Exception {
		String result;

		result = PreProcessor.getIncludeFileContent("#include <test.txt>", new File(rootPath + "\\resource"));
		assertEquals("testTextContents\n", result);

		result = PreProcessor.getIncludeFileContent("#include  \"test.txt\"",
				new File(rootPath + "\\resource"));
		assertEquals("testTextContents\n", result);

	}

	public void testDefault() throws Exception {
		String result;

		result = PreProcessor.parse("", null);
		assertEquals("", result);

		result = PreProcessor.parse(
				"\n#IFDEF \n#IFDEF HOGE_TYPE \nhoge\n// #comment#\n#ENDIF",
				null);
		assertEquals("\n\n\nhoge\n// #comment#\n", result);

		result = PreProcessor.parse(
				"\n#IFDEF \n#IFDEF HOGE_TYPE \nhoge\n// #comment#\n#ENDIF",
				null);
		assertEquals("\n\n\nhoge\n// #comment#\n", result);

	}
}
