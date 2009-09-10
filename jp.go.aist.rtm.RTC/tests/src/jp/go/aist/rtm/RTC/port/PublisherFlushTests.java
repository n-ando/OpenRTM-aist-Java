package jp.go.aist.rtm.RTC.port;

import java.util.Vector;

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

import jp.go.aist.rtm.RTC.port.publisher.PublisherFlush;
import jp.go.aist.rtm.RTC.util.Properties;
import junit.framework.TestCase;
import _SDOPackage.NVListHolder;

/**
 * <p>PublisherFlushクラスのためのテストケースです。</p>
 */
public class PublisherFlushTests extends TestCase {

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
            long now = System.currentTimeMillis();
            
            long delayTick = now - _delayStartTime;
            
            _delayTicks.add(delayTick);
            
            resetDelayStartTime();

            try {
                Thread.sleep(_sleepTick);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            _count++;

            setReturnStartTime();
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
        
        public void recordReturnTick() {
            long now = System.currentTimeMillis();
            
            long returnTick = now - _returnStartTime;
            
            _returnTicks.add(returnTick);
        }
        
        public Vector<Long> getDelayTicks() {
            return _delayTicks;
        }
        
        public Vector<Long> getReturnTicks() {
            return _returnTicks;
        }
        
        public int getCount() {
            return _count;
        }
        
        protected long _sleepTick;
        protected long _delayStartTime;
        protected long _returnStartTime;
        protected Vector<Long> _delayTicks = new Vector<Long>();
        protected Vector<Long> _returnTicks = new Vector<Long>();
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
     * <p>update()メソッド呼出周辺の即時性のテスト
     * <ul>
     * <li>Publisherのupdate()メソッド呼出後、所定時間内にConsumerのpush()メソッドが呼び出されるか？</li>
     * <li>Consumerのpush()メソッド終了後、所定時間内にPublihserのupdate()メソッド呼出から復帰するか？</li>
     * </ul>
     * </p>
     */
    public void test_update_immediacy() {
        long sleepTick = 100;
        
        MockConsumer consumer = new MockConsumer(sleepTick);
        Properties prop = new Properties();
        PublisherFlush publisher = new PublisherFlush(consumer, prop);
        
        for( int i = 0; i < 20; i++ ) {
            consumer.setDelayStartTime();
//            publisher.update();
            consumer.recordReturnTick();
        }
        
        long permissibleDelayTick = 100;
        Vector<Long> delayTicks = consumer.getDelayTicks();
        for( int i = 0; i < delayTicks.size(); i++) {
            assertTrue(delayTicks.get(i) < permissibleDelayTick);
        }
        
        long permissibleReturnTick = 100;
        Vector<Long> returnTicks = consumer.getReturnTicks();
        for( int i = 0; i < returnTicks.size(); i++) {
            assertTrue(returnTicks.get(i) < permissibleReturnTick);
        }
    }
}
