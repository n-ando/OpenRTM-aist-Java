package jp.go.aist.rtm.rtctemplate._test.python;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTestsPy {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for jp.go.aist.rtm.rtctemplate._test");
		//$JUnit-BEGIN$
		suite.addTestSuite(ConfigSetTest.class);
		suite.addTestSuite(DiffTest.class);
		suite.addTestSuite(LuckTest.class);
		suite.addTestSuite(AISTTest.class);
		suite.addTestSuite(MultiTest.class);
		suite.addTestSuite(BaseTest.class);
		//$JUnit-END$
		return suite;
	}

}
