package jp.go.aist.rtm.RTC.util;

import jp.go.aist.rtm.RTC.util.clock.AdjustedClock;
import junit.framework.TestCase;

/**
* AdjustedClockクラス　テスト
* 対象クラス：AdjustedClock
*
*/
public class AdjustedClockTest extends TestCase {
    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    /**
    * 時間オフセットのテスト
    * setTime()で設定されたオフセットが有効となっているか確認する
    */
    public void test_offset() {
        AdjustedClock clock = new AdjustedClock();
        long msec = System.currentTimeMillis();
        long sec = msec/1000;
        long usec = (msec - sec*1000)*1000;
        TimeValue time = new TimeValue(sec-30, usec);
        clock.setTime(time);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //
        long msec2 = System.currentTimeMillis();
        long sec2 = msec2/1000;
        long usec2 = (msec2 - sec2*1000)*1000;
        assertEquals(sec2-30, clock.getTime().getSec());
        assertEquals(usec2, clock.getTime().getUsec());
    }

}
