package jp.go.aist.rtm.RTC.port;

import java.util.Vector;

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

import jp.go.aist.rtm.RTC.port.publisher.PublisherNew;
import jp.go.aist.rtm.RTC.util.Properties;
import junit.framework.TestCase;
import _SDOPackage.NVListHolder;

/**
 * <p>PublisherNewクラスのためのテストケースです。</p>
 */
public class PublisherNewTests extends TestCase {

    class MockConsumer implements InPortConsumer {
        public MockConsumer() {
            this(0L);
        }
        public MockConsumer(long sleepTick) {
            super();
            _sleepTick = sleepTick;
            _count = 0;
            resetDelayStartTime();
        }
        
	public void init(Properties prop) {
	}

        public void push() {
            try {
                Thread.sleep(_sleepTick);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long now = System.currentTimeMillis();
            
            long delayTick = now - _delayStartTime;
            
            _delayTicks.add(delayTick);
            
            resetDelayStartTime();

            _count++;
        }
        
        public InPortConsumer clone() {
            MockConsumer clone = new MockConsumer();
            clone._sleepTick = _sleepTick;
            return clone;
        }

        public boolean subscribeInterface(NVListHolder holder) {
            return true;
        }
        
        public void unsubscribeInterface(NVListHolder holder) {
            return;
        }
        
        public void setDelayStartTime() {
            if( _delayStartTime == 0 ) {
                _delayStartTime = System.currentTimeMillis();
            }
        }
        
        public Vector<Long> getDelayTicks() {
            return _delayTicks;
        }
        
        public int getCount() {
            return _count;
        }
        
        protected long _sleepTick;
        protected long _delayStartTime;
        protected long _returnStartTime;
        protected Vector<Long> _delayTicks = new Vector<Long>();
        protected int _count;
    
        protected void resetDelayStartTime() {
            _delayStartTime = 0;
        }
        
        protected void setReturnStartTime() {
            _returnStartTime = System.currentTimeMillis();
        }
        
        protected void resetReturnStartTime() {
            _returnStartTime = 0;
        }
        public ReturnCode put(final OutputStream data) {
            return ReturnCode.PORT_OK;
        }
        public void publishInterfaceProfile(NVListHolder properties) {
        }
    };

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>update()メソッドのテスト
     * <ul>
     * <li>「Publisherのupdate()メソッド呼出間隔」>「Consumerのpush()メソッド処理時間」の場合に、update()呼出からpush()呼出までの時間間隔が、所定時間内に収まっているか？</li>
     * </ul>
     * </p>
     */
    public void test_update_large_interval() {
        long sleepTick = 100;
        long intervalTick = sleepTick * 10;
        
        MockConsumer consumer = new MockConsumer(sleepTick);
        Properties prop = new Properties();
        PublisherNew publisher = new PublisherNew(consumer, prop);
        
        for( int i = 0; i < 5; i++ ) {
            consumer.setDelayStartTime();
            publisher.update();
            try {
                Thread.sleep(intervalTick);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        // Consumer呼出が完了するであろうタイミングまで待つ
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // update()呼出からpush()呼出までの時間間隔が、所定時間内に収まっているか？
        // （リアルタイム性が保証されているわけでもなく、仕様上も呼出までの時間を明記しているわけではないので、
        // ここでの所定時間はテスト作成者の主観によるものに過ぎない。）
        long permissibleDelay = sleepTick + 100000;
        Vector<Long> delayTicks = consumer.getDelayTicks();
        for( int i = 0; i < delayTicks.size(); i++) {
            //std::cout << "delay tick = " << delayTicks[i] << std::endl;
            assertTrue(delayTicks.get(i) < permissibleDelay);
        }
    }
    /**
     * <p>update()メソッドのテスト
     * <ul>
     * <li>「Publisherのupdate()メソッド呼出間隔」<「Consumerのpush()メソッド処理時間」の場合に、update()呼出が溜ってしまうことなく、update()呼出からpush()呼出までの時間間隔が、所定時間内に収まっているか？</li>
     * </ul>
     * </p>
     */
    public void test_update_small_interval() {
        long sleepTick = 100;
        long intervalTick = sleepTick / 10;
        
        MockConsumer consumer = new MockConsumer(sleepTick);
        Properties prop = new Properties();
        PublisherNew publisher = new PublisherNew(consumer, prop);
        
        for( int i = 0; i < 50; i++ ) {
            consumer.setDelayStartTime();
            publisher.update();
            try {
                Thread.sleep(intervalTick);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        // Consumer呼出が完了するであろうタイミングまで待つ
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // update()呼出からpush()呼出までの時間間隔が、所定時間内に収まっているか？
        // （リアルタイム性が保証されているわけでもなく、仕様上も呼出までの時間を明記しているわけではないので、
        // ここでの所定時間はテスト作成者の主観によるものに過ぎない。）
        long permissibleDelay = sleepTick + 100000;
        Vector<Long> delayTicks = consumer.getDelayTicks();
        for( int i = 0; i < delayTicks.size()-1; i++) {
            //std::cout << "delay tick = " << delayTicks[i] << std::endl;
            assertTrue(delayTicks.get(i) < permissibleDelay);
        }
    }
    /**
     * <p>release()メソッドのテスト
     * <ul>
     * <li>release()メソッド呼出によりPublisherの動作を確実に停止できるか？</li>
     * </ul>
     * </p>
     */
    public void test_release() {
        MockConsumer consumer = new MockConsumer(100);
        Properties prop = new Properties();
        PublisherNew publisher = new PublisherNew(consumer, prop);
        
        // update()を呼出して、Consumerを呼び出させる
        publisher.update();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        publisher.release();
        
        assertEquals(1, consumer.getCount());
        
        // 再度update()を呼出し、Consumerを呼出しうる時間を与える。
        // （実際には、前段のrelease()によりPublisherが停止済みであり、
        // update()呼出は何ら影響を与えないことを予期している。）
        publisher.update();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Consumer呼出回数が変わっていないこと、つまりPublisherの動作が停止していることを確認する
        assertEquals(1, consumer.getCount());
    }
}
