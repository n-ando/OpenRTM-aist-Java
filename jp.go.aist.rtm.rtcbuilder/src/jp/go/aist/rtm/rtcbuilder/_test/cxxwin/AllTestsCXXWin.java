package jp.go.aist.rtm.rtcbuilder._test.cxxwin;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTestsCXXWin {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for jp.go.aist.rtm.rtcbuilder._test");
		//$JUnit-BEGIN$
//		suite.addTestSuite(CXXProConMultiTest.class);
		suite.addTestSuite(CXXConfigSetTest.class);
		suite.addTestSuite(CXXDiffTest.class);
		suite.addTestSuite(CXXMultiTest.class);
		suite.addTestSuite(CXXLuckTest.class);
		suite.addTestSuite(CXXTemplateTestAIST.class);
		suite.addTestSuite(CXXTemplateTest.class);
		suite.addTestSuite(CXXVarTest.class);
		suite.addTestSuite(CXXWinIDLPathTest.class);
		suite.addTestSuite(CXXWinDocLongTest.class);
		//$JUnit-END$
		return suite;
	}

}
