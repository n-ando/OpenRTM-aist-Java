package jp.go.aist.rtm;

import Extension.EC.logical_time.LogicalTimeTriggeredECTests;
import jp.go.aist.rtm.RTC.log.LogBufTimeTest;
import jp.go.aist.rtm.RTC.log.LogbufTest;
import jp.go.aist.rtm.RTC.util.AdjustedClockTest;
import jp.go.aist.rtm.RTC.util.TimeValueTest;
import jp.go.aist.rtm.RTC.buffer.RingBufferTest;
import jp.go.aist.rtm.RTC.executionContext.ExtTrigExecutionContextTests;
import jp.go.aist.rtm.RTC.executionContext.PeriodicExecutionContextTests;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTest110 {
    
    public static Test suite() {
        TestSuite suite = new TestSuite("Test for 1.1.0");
        
        suite.addTestSuite(LogbufTest.class);
        suite.addTestSuite(LogBufTimeTest.class);
        
        suite.addTestSuite(PeriodicExecutionContextTests.class);
        suite.addTestSuite(ExtTrigExecutionContextTests.class);
        suite.addTestSuite(LogicalTimeTriggeredECTests.class);
        
        suite.addTestSuite(TimeValueTest.class);
        suite.addTestSuite(AdjustedClockTest.class);
        
        suite.addTestSuite(RingBufferTest.class);

        return suite;
    }
}
