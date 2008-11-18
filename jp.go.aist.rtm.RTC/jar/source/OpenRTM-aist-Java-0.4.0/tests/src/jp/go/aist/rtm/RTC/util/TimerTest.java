package jp.go.aist.rtm.RTC.util;

import jp.go.aist.rtm.RTC.util.ListenerBase;
import jp.go.aist.rtm.RTC.util.TimeValue;
import jp.go.aist.rtm.RTC.util.Timer;
import junit.framework.TestCase;

/**
* タイマークラス　テスト
* 対象クラス：Timer
*/
public class TimerTest extends TestCase {

    /**
     *<pre>
     * リスナーの登録/削除　チェック
     *　・タイマー起動用リスナーを登録/削除できるか？
     *</pre>
     */
    public void test_regist_unregist() {
        TimeValue tm = new TimeValue(1, 0);
        Timer timer = new Timer(tm);
        
        ListenerBase op1 = new TimerOperation();
        TimeValue tm1 = new TimeValue(3, 0);
        timer.registerListener(op1, tm1);
        ListenerBase op2 = new TimerOperation();
        TimeValue tm2 = new TimeValue(5, 0);
        timer.registerListener(op2, tm2);
        //
        boolean result = timer.unregisterListener(op1);
        assertEquals(true, result);
        //
        ListenerBase op3 = new TimerOperation();
        result = timer.unregisterListener(op3);
        assertEquals(false, result);
    }

}
