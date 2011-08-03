package jp.go.aist.rtm.RTC.port;

import jp.go.aist.rtm.RTC.buffer.RingBuffer;
import jp.go.aist.rtm.RTC.util.NVListHolderFactory;
import jp.go.aist.rtm.RTC.util.NVUtil;
import junit.framework.TestCase;

import org.omg.CORBA.Any;

import RTC.TimedFloat;
import _SDOPackage.NVListHolder;

/**
 * <p>OutPortProviderImplクラスのためのテストケースです。</p>
 */
public class OutPortCorbaProviderTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>publishInterfaceProfile()メソッドのテスト
     * <ul>
     * <li>"dataport.data_type"プロパティを正しく取得できるか？</li>
     * <li>"dataport.interface_type"プロパティを正しく取得できるか？</li>
     * <li>"dataport.dataflow_type"プロパティを正しく取得できるか？</li>
     * <li>"dataport.subscription_type"プロパティを正しく取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_publishInterfaceProfile() throws Exception {
        RingBuffer<TimedFloat> buffer = new RingBuffer<TimedFloat>(100);
        OutPortCorbaProvider<TimedFloat> provider = new OutPortCorbaProvider<TimedFloat>(TimedFloat.class, buffer); // will be deleted automatically
        
        NVListHolder profile = NVListHolderFactory.create();
        provider.publishInterfaceProfile(profile);
        
        // "dataport.data_type"プロパティを正しく取得できるか？
        assertEquals("TimedFloat", NVUtil.toString(profile, "dataport.data_type"));
        
        // "dataport.interface_type"プロパティを正しく取得できるか？
        assertEquals("CORBA_Any", NVUtil.toString(profile, "dataport.interface_type"));
        
        // "dataport.dataflow_type"プロパティを正しく取得できるか？
        assertEquals("Push, Pull", NVUtil.toString(profile, "dataport.dataflow_type"));
        
        // "dataport.subscription_type"プロパティを正しく取得できるか？
        assertEquals("Flush, New, Periodic", NVUtil.toString(profile, "dataport.subscription_type"));
    }
    /**
     * <p>get()メソッドのテスト
     * <ul>
     * <li>バッファに書き込まれた値を、get()メソッドで正しく読み出せるか？</li>
     * </ul>
     * </p>
     */
    public void test_get() throws Exception {
        RingBuffer<Float> buffer = new RingBuffer<Float>(100);
        OutPortCorbaProvider<Float> provider = new OutPortCorbaProvider<Float>(Float.class, buffer); // will be deleted automatically
        
        for( int i = 0; i < 10; ++i ) {
            Float writeValue = new Float(3.14159 * i);
            buffer.write(writeValue);

            // バッファに書き込まれた値を、get()メソッドで正しく読み出せるか？           
            float readValue;
            Any any = provider.get();
            readValue = any.extract_float();
            assertEquals(writeValue.floatValue(), readValue);
        }
    }
    
}
