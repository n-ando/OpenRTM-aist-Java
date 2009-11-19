package jp.go.aist.rtm;


import jp.go.aist.rtm.RTC.ConfigSampleTest;
import jp.go.aist.rtm.RTC.ConsoleInTest;
import jp.go.aist.rtm.RTC.ConsoleOutTest;
import jp.go.aist.rtm.RTC.ExTrigTest;
import jp.go.aist.rtm.RTC.MyServiceConsumerTest;
import jp.go.aist.rtm.RTC.MyServiceProviderTest;
import jp.go.aist.rtm.RTC.SeqInTest;
import jp.go.aist.rtm.RTC.SeqOutTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class SampleTests{

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for PTM");
		//$JUnit-BEGIN$
        suite.addTestSuite(ConsoleInTest.class);
        suite.addTestSuite(ConsoleOutTest.class);
        suite.addTestSuite(SeqInTest.class);
        suite.addTestSuite(SeqOutTest.class);
        suite.addTestSuite(ConfigSampleTest.class);
        suite.addTestSuite(ExTrigTest.class);
        suite.addTestSuite(MyServiceConsumerTest.class);
        suite.addTestSuite(MyServiceProviderTest.class);
//$JUnit-END$
		return suite;
	}

}
