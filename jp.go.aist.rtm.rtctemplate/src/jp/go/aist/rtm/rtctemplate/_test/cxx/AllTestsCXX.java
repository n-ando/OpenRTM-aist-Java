package jp.go.aist.rtm.rtctemplate._test.cxx;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTestsCXX {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for jp.go.aist.rtm.rtctemplate._test");
		//$JUnit-BEGIN$
		suite.addTestSuite(CXXConfigSetTest.class);
		suite.addTestSuite(CXXDiffTest.class);
		suite.addTestSuite(CXXMultiTest.class);
		suite.addTestSuite(CXXLuckTest.class);
		suite.addTestSuite(IDLParamConverterTest.class);
		suite.addTestSuite(CXXTemplateTestAIST.class);
		suite.addTestSuite(MergeBlockParserTest.class);
		suite.addTestSuite(IDLParserTest.class);
		suite.addTestSuite(CXXTemplateTest.class);
		suite.addTestSuite(PreProcessorTest.class);
		suite.addTestSuite(CommandLinePerserTest.class);
		//$JUnit-END$
		return suite;
	}

}
