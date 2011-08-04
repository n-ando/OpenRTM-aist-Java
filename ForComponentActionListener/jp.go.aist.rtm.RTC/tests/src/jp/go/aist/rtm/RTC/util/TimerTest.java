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

    private class Listener implements ListenerBase {
        public Listener() {
            this("", false);
        }
        public Listener(String name) {
            this(name, false);
        }
        public Listener(String name, boolean printMsg) {
            _name = name;
            _printMsg = printMsg;
            _count = 0;
        }
        public void invoke() {
            _count++;
            
            if( _printMsg ) {
                System.out.println();
                System.out.println(_name + ":invoked. (count = " + _count + ")" );
                System.out.println();
            }
        }
        
        public String _name;
        public boolean _printMsg;
        public int _count;
    };

    /**
     * <p>registerListener()メソッドのテスト
     * <ul>
     * <li>タイマを起動し、あらかじめ登録されたリスナが意図どおりの時間間隔でコールバックされるか？</li>
     * </ul>
     */
    public void test_registerListener() {
        TimeValue timerInterval = new TimeValue(0, 100000); // 0.1 [sec]
        Timer timer = new Timer(timerInterval);

        Listener listener = new Listener();
        TimeValue listenerInterval = new TimeValue(0, 100000);
        timer.registerListener(listener, listenerInterval);

        timer.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        timer.stop();

        // １秒に１回の呼出なので、１０回カウントされているはず。精度を考慮して、９～１１回の範囲であることを確認する。
        assertTrue((9 <= listener._count) && (listener._count <= 11));
    }
    /**
     * <p>複数のタイマーを時間的に直列に動作させるテスト
     * <ul>
     * <li>２つのタイマーの動作が互いに干渉することなく、あらかじめ登録されたリスナが意図どおりの時間間隔でコールバックされるか？</li>
     * </ul>
     */
    public void test_activate_multi_timers_continuously() {
        // １つめのタイマーを起動する
        TimeValue timerInterval1 = new TimeValue(0, 100000); // 0.1 [sec]
        Timer timer1 = new Timer(timerInterval1);

        Listener listener1 = new Listener("listener-1");
        TimeValue listenerInterval1 = new TimeValue(0, 100000);
        timer1.registerListener(listener1, listenerInterval1);

        timer1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        timer1.stop();
        
        // ２つめのタイマーを起動する
        TimeValue timerInterval2 = new TimeValue(0, 100000); // 0.1 [sec]
        Timer timer2 = new Timer(timerInterval2);

        Listener listener2 = new Listener("listener-2");
        TimeValue listenerInterval2 = new TimeValue(0, 100000);
        timer2.registerListener(listener2, listenerInterval2);

        timer2.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        timer2.stop();

        // １秒に１回の呼出なので、１０回カウントされているはず。精度を考慮して、９～１１回の範囲であることを確認する。
        assertTrue((9 <= listener1._count) && (listener1._count <= 11));
        assertTrue((9 <= listener2._count) && (listener2._count <= 11));
    }
    /**
     * <p>複数のタイマーを時間的に直列に動作させるテスト
     * <ul>
     * <li>２つのタイマーの動作が互いに干渉することなく、あらかじめ登録されたリスナが意図どおりの時間間隔でコールバックされるか？</li>
     * </ul>
     */
    public void test_activate_multi_timers_concurrently() {
        // １つめのタイマーを生成する
        TimeValue timerInterval1 = new TimeValue(0, 100000); // 0.1 [sec]
        Timer timer1 = new Timer(timerInterval1);

        Listener listener1 = new Listener("listener-1");
        TimeValue listenerInterval1 = new TimeValue(0, 100000);
        timer1.registerListener(listener1, listenerInterval1);

        // ２つめのタイマーを生成する
        TimeValue timerInterval2 = new TimeValue(0, 100000); // 0.1 [sec]
        Timer timer2 = new Timer(timerInterval2);

        Listener listener2 = new Listener("listener-2");
        TimeValue listenerInterval2 = new TimeValue(0, 100000);
        timer2.registerListener(listener2, listenerInterval2);

        // ２つのタイマーを同時スタート・ストップさせる
        timer1.start();
        timer2.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        timer1.stop();
        timer2.stop();

        // １秒に１回の呼出なので、１０回カウントされているはず。精度を考慮して、９～１１回の範囲であることを確認する。
        assertTrue((9 <= listener1._count) && (listener1._count <= 11));
        assertTrue((9 <= listener2._count) && (listener2._count <= 11));
    }

    /**
     * <p>リスナーの登録/削除　チェック
     * <ul>
     * <li>タイマー起動用リスナーを登録/削除できるか？</li>
     * </ul>
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
