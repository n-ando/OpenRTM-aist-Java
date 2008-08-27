package jp.go.aist.rtm.rtctemplate._test.java;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTestsJava {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for jp.go.aist.rtm.rtctemplate._test");
		//$JUnit-BEGIN$
		suite.addTestSuite(ConfigSetTest.class);
		suite.addTestSuite(DiffTest.class);
		suite.addTestSuite(LuckTest.class);
		suite.addTestSuite(MultiTest.class);
		suite.addTestSuite(AISTTest.class);
		suite.addTestSuite(BaseTest.class);
		suite.addTestSuite(MLTest.class);
		//$JUnit-END$
		return suite;
	}

}
