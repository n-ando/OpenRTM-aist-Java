package jp.go.aist.rtm.RTC;

import junit.framework.TestCase;

import java.util.Vector;
import java.lang.System;
import java.lang.Math;

import jp.go.aist.rtm.RTC.TimeMeasure;
import jp.go.aist.rtm.RTC.util.TimeValue;

/**
* CORBA ユーティリティクラス　テスト
* 対象クラス：TimeMeasure
*/
public class TimeMeasureTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>引数なしコンストラクタによるテスト
     * <ul>
     * <li>引数なしで正しく動作するか？</li>
     * </ul>
     * </p>
     */
    public void test_case1() {

        TimeValue tv = new TimeValue();
        TimeMeasure tm1 = new TimeMeasure();
        TimeMeasure.Statistics st;
        st = tm1.getStatistics();
        tv = tm1.interval();

        assertEquals(0.0, st.max_interval);
        assertEquals(Double.MAX_VALUE, st.min_interval);
        assertEquals(0.0, st.mean_interval);
        assertEquals(0.0, st.std_deviation);
        assertEquals(0.0, tv.toDouble());

        tm1.tick();
        try {
            Thread.sleep(500);  // 500 msec
        } catch(InterruptedException e) {
        }
        tm1.tack();
        tv = tm1.interval();
        // ms単位のずれが生じます。
        assertTrue(tv.toDouble() >= 0.5);

        int cnt = tm1.count();
        assertEquals(1, cnt);

        st = tm1.getStatistics();
        assertTrue(st.max_interval >= 0.5);
        assertTrue(st.min_interval >= 0.5);
        assertTrue(st.mean_interval >= 0.5);
        assertEquals(0.0, st.std_deviation);

        TimeMeasure.Statistics ss = tm1.createStatistics();
        ss.max_interval  = -2.0;
        ss.min_interval  = -2.0;
        ss.mean_interval = -2.0;
        ss.std_deviation = -2.0;
        boolean bret = tm1.getStatistics(ss);

        assertTrue(bret);
        assertTrue(ss.max_interval >= 0.5);
        assertTrue(ss.min_interval >= 0.5);
        assertTrue(ss.mean_interval >= 0.5);
        assertEquals(0.0, ss.std_deviation);

        tm1.tick();
        try {
            Thread.sleep(800);  // 800 msec
        } catch(InterruptedException e) {
        }
        tm1.tack();
        tv = tm1.interval();
        // ms単位のずれが生じます。
        assertTrue(tv.toDouble() >= 0.8);

        cnt = tm1.count();
        assertEquals(2, cnt);

        st = tm1.getStatistics();
        assertTrue(st.max_interval >= 0.8);
        assertTrue(st.min_interval >= 0.8);
        assertTrue(st.mean_interval >= 0.8);
        assertEquals(0.0, st.std_deviation);

        tm1.reset();
        cnt = tm1.count();
        assertEquals(0, cnt);

    }

    /**
     * <p>引数ありコンストラクタによるテスト
     * <ul>
     * <li>引数ありで正しく動作するか？</li>
     * </ul>
     * </p>
     */

    public void test_case2() {

        TimeValue tv = new TimeValue();
        TimeMeasure tm1 = new TimeMeasure(20);
        TimeMeasure.Statistics st;
        st = tm1.getStatistics();
        tv = tm1.interval();

        assertEquals(0.0, st.max_interval);
        assertEquals(Double.MAX_VALUE, st.min_interval);
        assertEquals(0.0, st.mean_interval);
        assertEquals(0.0, st.std_deviation);
        assertEquals(0.0, tv.toDouble());

        tm1.tick();
        try {
            Thread.sleep(500);  // 500 msec
        } catch(InterruptedException e) {
        }
        tm1.tack();
        tv = tm1.interval();
        // ms単位のずれが生じます。
        assertTrue(tv.toDouble() >= 0.5);

        int cnt = tm1.count();
        assertEquals(1, cnt);

        st = tm1.getStatistics();
        assertTrue(st.max_interval >= 0.5);
        assertTrue(st.min_interval >= 0.5);
        assertTrue(st.mean_interval >= 0.5);
        assertEquals(0.0, st.std_deviation);

        TimeMeasure.Statistics ss = tm1.createStatistics();
        ss.max_interval  = -2.0;
        ss.min_interval  = -2.0;
        ss.mean_interval = -2.0;
        ss.std_deviation = -2.0;
        boolean bret = tm1.getStatistics(ss);

        assertTrue(bret);
        assertTrue(ss.max_interval >= 0.5);
        assertTrue(ss.min_interval >= 0.5);
        assertTrue(ss.mean_interval >= 0.5);
        assertEquals(0.0, ss.std_deviation);

        tm1.reset();
        cnt = tm1.count();
        assertEquals(0, cnt);

    }

}
