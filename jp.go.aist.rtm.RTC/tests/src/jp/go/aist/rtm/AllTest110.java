package jp.go.aist.rtm;

import jp.go.aist.rtm.RTC.log.LogBufTimeTest;
import jp.go.aist.rtm.RTC.log.LogbufTest;
import jp.go.aist.rtm.RTC.util.AdjustedClockTest;
import jp.go.aist.rtm.RTC.util.TimeValueTest;
import jp.go.aist.rtm.RTC.buffer.RingBufferTest;
import jp.go.aist.rtm.RTC.executionContext.*;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTest110 {
    
    public static Test suite() {
        TestSuite suite = new TestSuite("Test for 1.1.0");
        
        suite.addTestSuite(TimeValueTest.class);
        suite.addTestSuite(LogbufTest.class);
        suite.addTestSuite(AdjustedClockTest.class);
        
        suite.addTestSuite(LogBufTimeTest.class);
        suite.addTestSuite(RingBufferTest.class);
        suite.addTestSuite(ExtTrigExecutionContextTests.class);
        suite.addTestSuite(PeriodicExecutionContextTests.class);

        return suite;
    }
}
