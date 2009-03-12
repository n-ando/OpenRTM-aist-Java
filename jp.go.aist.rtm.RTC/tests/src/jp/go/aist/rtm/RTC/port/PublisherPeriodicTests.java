package jp.go.aist.rtm.RTC.port;

import java.util.Vector;

import jp.go.aist.rtm.RTC.port.publisher.PublisherPeriodic;
import jp.go.aist.rtm.RTC.util.Properties;
import junit.framework.TestCase;
import _SDOPackage.NVListHolder;

/**
 * <p>PublisherPeriodicクラスのためのテストケースです。</p>
 */
public class PublisherPeriodicTests extends TestCase {

    class MockConsumer implements InPortConsumer {
        public MockConsumer() {
            super();
            clearLastTime();
        }
        
        public void push() {
            long now = System.currentTimeMillis();
            
            if( !isLastTimeCleared() ) {
                long interval = now - _lastTime;
                
                _intervalTicks.add(interval);
            }
            
            _lastTime = now;
        }
        
        public InPortConsumer clone() {
            MockConsumer clone = new MockConsumer();
            clone._intervalTicks = new Vector<Long>(_intervalTicks);
            clone._lastTime = _lastTime;
            return clone;
        }

        public boolean subscribeInterface(NVListHolder holder) {
            return true;
        }
        
        public void unsubscribeInterface(NVListHolder holder) {
            return;
        }
        
        public Vector<Long> getIntervalTicks() {
            return _intervalTicks;
        }
        
        public int getCount() {
            return _intervalTicks.size();
        }
        
        private Vector<Long> _intervalTicks = new Vector<Long>();
        long _lastTime;
        
        private void clearLastTime() {
            _lastTime = 0;
        }
        
        private boolean isLastTimeCleared() {
            return (_lastTime == 0);
        }
    };

    class CounterConsumer implements InPortConsumer {
   
       public CounterConsumer() {
           this(null);
       }

       public CounterConsumer(CounterConsumer component) {
           super();
           _count = 0;
           _component = component;
       }
        
       public void push() {
           _count++;
           if( _component != null) {
                _component.push();
            }
        }
        
        public InPortConsumer clone() {
            CounterConsumer clone = new CounterConsumer();
            clone._count = _count;
            clone._component = _component;
            return clone;
        }

        public boolean subscribeInterface(NVListHolder holder) {
            return true;
        }
        
        public void unsubscribeInterface(NVListHolder holder) {
            return;
        }

        public int getCount() {
            return _count;
        }
    
        private int _count;
        private CounterConsumer _component;
    };

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>release()メソッドのテスト
     * <ul>
     * <li>release()メソッド呼出によりPublisherの動作が停止するか？</li>
     * </ul>
     * </p>
     */
    public void test_release() {
        CounterConsumer consumer = new CounterConsumer();
        Properties prop = new Properties();
        prop.setProperty("dataport.push_rate", "10"); // 10 [Hz]
        PublisherPeriodic publisher = new PublisherPeriodic(consumer, prop);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Publisherの動作を停止させる
        publisher.release();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // この時点での呼出回数を記録する
        int countReleased = consumer.getCount();
        
        // さらにConsumerがコールバックされ得る時間を与える
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // この時点での呼出回数を取得し、先に記録しておいた回数から変化がない
        // （つまり、Publisherの動作が停止している）ことを確認する
        int countSleeped = consumer.getCount();
        assertEquals(countReleased, countSleeped);
    }
    /**
     * <p>PublisherによるConsumer呼出間隔精度のテスト
     * <ul>
     * <li>Publisherに指定した時間間隔で、正しくConsumerがコールバックされるか？</li>
     * </ul>
     * </p>
     */
    public void test_interval_accuracy() {
        MockConsumer consumer = new MockConsumer();
        Properties prop = new Properties();
        prop.setProperty("dataport.push_rate", "10"); // 10 [Hz]
        PublisherPeriodic publisher = new PublisherPeriodic(consumer, prop);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Publisherの動作を停止させる
        publisher.release();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // 指定した時間間隔で正しくConsumerがコールバックされているか？
        long permissibleTickMin = (long)(100 * 0.9);
        long permissibleTickMax = (long)(100 * 1.1);
        Vector<Long> intervalTicks = consumer.getIntervalTicks();
        assertTrue(intervalTicks.size() > 0);

        for( int i = 0; i < intervalTicks.size(); i++) {
            long tick = intervalTicks.get(i);
            assertTrue((permissibleTickMin <= tick) && (tick <= permissibleTickMax));
        }
    }
}
