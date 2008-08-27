package jp.go.aist.rtm.rtcbuilder._test.java;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTestsJava {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for jp.go.aist.rtm.rtcbuilder._test");
		//$JUnit-BEGIN$
		suite.addTestSuite(ConfigSetTest.class);
		suite.addTestSuite(DiffTest.class);
		suite.addTestSuite(LuckTest.class);
		suite.addTestSuite(MultiTest.class);
		suite.addTestSuite(AISTTest.class);
		suite.addTestSuite(BaseTest.class);
		suite.addTestSuite(BuildPathTest.class);
		suite.addTestSuite(VarTest.class);
		suite.addTestSuite(JavaIDLPathTest.class);
		suite.addTestSuite(JavaDocTest.class);
		suite.addTestSuite(JavaDocLongTest.class);
		suite.addTestSuite(MLTest.class);
		suite.addTestSuite(JavaExCxtTest.class);
		suite.addTestSuite(JavaSystemConfigTest.class);
		suite.addTestSuite(JavaImplTest.class);
		suite.addTestSuite(JavaVariableTest.class);
		//$JUnit-END$
		return suite;
	}

}
