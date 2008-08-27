package jp.go.aist.rtm.rtcbuilder._test.cxx;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTestsCXX {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for jp.go.aist.rtm.rtcbuilder._test");
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
		suite.addTestSuite(CXXVarTest.class);
		suite.addTestSuite(CXXIDLPathTest.class);
		suite.addTestSuite(CXXDocTest.class);
		suite.addTestSuite(CXXDocLongTest.class);
		suite.addTestSuite(CXXExCxtTest.class);
		suite.addTestSuite(CXXSystemConfigTest.class);
		suite.addTestSuite(CXXImplTest.class);
		suite.addTestSuite(CXXVariableTest.class);
		suite.addTestSuite(CXXTemplateTestAIST2.class);
		suite.addTestSuite(CXXLibraryTest.class);
		//$JUnit-END$
		return suite;
	}

}
