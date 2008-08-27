package jp.go.aist.rtm.rtctemplate._test;

import jp.go.aist.rtm.rtctemplate._test.cxx.CXXConfigSetTest;
import jp.go.aist.rtm.rtctemplate._test.cxx.CXXDiffTest;
import jp.go.aist.rtm.rtctemplate._test.cxx.CXXLuckTest;
import jp.go.aist.rtm.rtctemplate._test.cxx.CXXMultiTest;
import jp.go.aist.rtm.rtctemplate._test.cxx.CXXTemplateTest;
import jp.go.aist.rtm.rtctemplate._test.cxx.CXXTemplateTestAIST;
import jp.go.aist.rtm.rtctemplate._test.cxx.CommandLinePerserTest;
import jp.go.aist.rtm.rtctemplate._test.cxx.IDLParamConverterTest;
import jp.go.aist.rtm.rtctemplate._test.cxx.IDLParserTest;
import jp.go.aist.rtm.rtctemplate._test.cxx.MergeBlockParserTest;
import jp.go.aist.rtm.rtctemplate._test.cxx.PreProcessorTest;
import jp.go.aist.rtm.rtctemplate._test.java.AISTTest;
import jp.go.aist.rtm.rtctemplate._test.java.BaseTest;
import jp.go.aist.rtm.rtctemplate._test.java.ConfigSetTest;
import jp.go.aist.rtm.rtctemplate._test.java.DiffTest;
import jp.go.aist.rtm.rtctemplate._test.java.LuckTest;
import jp.go.aist.rtm.rtctemplate._test.java.MultiTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for All Languages");
		//$JUnit-BEGIN$
		//CXX
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
		//CXXWin
//		suite.addTestSuite(jp.go.aist.rtm.rtctemplate._test.cxxwin.CXXConfigSetTest.class);
//		suite.addTestSuite(jp.go.aist.rtm.rtctemplate._test.cxxwin.CXXDiffTest.class);
//		suite.addTestSuite(jp.go.aist.rtm.rtctemplate._test.cxxwin.CXXMultiTest.class);
//		suite.addTestSuite(jp.go.aist.rtm.rtctemplate._test.cxxwin.CXXLuckTest.class);
//		suite.addTestSuite(jp.go.aist.rtm.rtctemplate._test.cxxwin.CXXTemplateTestAIST.class);
//		suite.addTestSuite(jp.go.aist.rtm.rtctemplate._test.cxxwin.CXXTemplateTest.class);
		//Java
		suite.addTestSuite(ConfigSetTest.class);
		suite.addTestSuite(DiffTest.class);
		suite.addTestSuite(LuckTest.class);
		suite.addTestSuite(MultiTest.class);
		suite.addTestSuite(AISTTest.class);
		suite.addTestSuite(BaseTest.class);
		//Python
		suite.addTestSuite(jp.go.aist.rtm.rtctemplate._test.python.ConfigSetTest.class);
		suite.addTestSuite(jp.go.aist.rtm.rtctemplate._test.python.DiffTest.class);
		suite.addTestSuite(jp.go.aist.rtm.rtctemplate._test.python.LuckTest.class);
		suite.addTestSuite(jp.go.aist.rtm.rtctemplate._test.python.AISTTest.class);
		suite.addTestSuite(jp.go.aist.rtm.rtctemplate._test.python.MultiTest.class);
		suite.addTestSuite(jp.go.aist.rtm.rtctemplate._test.python.BaseTest.class);
		//$JUnit-END$
		return suite;
	}

}
