package jp.go.aist.rtm.RTC.port;

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

import jp.go.aist.rtm.RTC.port.publisher.PublisherBase;
import jp.go.aist.rtm.RTC.port.publisher.PublisherFactory;
import jp.go.aist.rtm.RTC.port.publisher.PublisherFlush;
import jp.go.aist.rtm.RTC.port.publisher.PublisherNew;
import jp.go.aist.rtm.RTC.port.publisher.PublisherPeriodic;
import jp.go.aist.rtm.RTC.util.Properties;
import junit.framework.TestCase;
import _SDOPackage.NVListHolder;

/**
 * <p>PublisherFactoryクラスのためのテストケースです。</p>
 */
public class PublisherFactoryTests extends TestCase {

    class NullConsumer implements InPortConsumer {
        public NullConsumer() {
            super();
        }
	public void init(Properties prop) {
	}
        public void push() {
        }
        public InPortConsumer clone() {
            return new NullConsumer();
        }
        public boolean subscribeInterface(NVListHolder holder) {
            return true;
        }
        public void unsubscribeInterface(NVListHolder holder) {
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
     * <p>create()メソッドのテスト
     * <ul>
     * <li>"dataport.subscription_type"を指定しない場合、デフォルトとしてPublisherNewが生成されるか？</li>
     * <li>"dataport.subscription_type"に"New"を指定した場合、PublisherNewが生成されるか？</li>
     * <li>"dataport.subscription_type"に"Periodic"を指定した場合、PublisherPeriodicが生成されるか？</li>
     * <li>"dataport.subscription_type"に"Flush"を指定した場合、PublisherFlushが生成されるか？</li>
     * </ul>
     * </p>
     */
    public void test_create() {
        PublisherFactory factory = new PublisherFactory();
        NullConsumer consumer = new NullConsumer();
        
        // (1) "dataport.subscription_type"を指定しない場合、デフォルトとしてPublisherNewが生成されるか？
        Properties propDefault = new Properties();
        PublisherBase publisherDefault = factory.create(consumer, propDefault);
        assertTrue( publisherDefault instanceof PublisherNew);
        
        // (2) "dataport.subscription_type"に"New"を指定した場合、PublisherNewが生成されるか？
        Properties propNew = new Properties();
        propNew.setProperty("dataport.subscription_type", "New");
        PublisherBase publisherNew = factory.create(consumer, propNew);
        assertNotNull( publisherNew instanceof PublisherNew);
        
        // (3) "dataport.subscription_type"に"Periodic"を指定した場合、PublisherPeriodicが生成されるか？
        Properties propPeriodic = new Properties();
        propPeriodic.setProperty("dataport.subscription_type", "Periodic");
        PublisherBase publisherPeriodic = factory.create(consumer, propPeriodic);
        assertNotNull(publisherPeriodic instanceof PublisherPeriodic);
        
        // (4) "dataport.subscription_type"に"Flush"を指定した場合、PublisherFlushが生成されるか？
        Properties propFlush = new Properties();
        propFlush.setProperty("dataport.subscription_type", "Flush");
        PublisherBase publisherFlush = factory.create(consumer, propFlush);
        assertTrue( publisherFlush instanceof PublisherFlush);
    }
}
