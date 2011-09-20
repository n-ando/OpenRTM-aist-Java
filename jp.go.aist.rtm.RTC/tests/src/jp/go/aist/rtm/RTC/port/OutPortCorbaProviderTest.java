package jp.go.aist.rtm.RTC.port;

import jp.go.aist.rtm.RTC.buffer.RingBuffer;
import jp.go.aist.rtm.RTC.util.NVListHolderFactory;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.FloatHolder;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.ORBUtil;
import junit.framework.TestCase;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

import RTC.TimedFloat;
import _SDOPackage.NVListHolder;

import com.sun.corba.se.impl.encoding.EncapsOutputStream; 
import com.sun.corba.se.impl.encoding.EncapsInputStream; 

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
        //OutPortCorbaProvider<TimedFloat> provider = new OutPortCorbaProvider<TimedFloat>(TimedFloat.class, buffer); // will be deleted automatically
        OutPortCorbaCdrProvider provider = new OutPortCorbaCdrProvider(); // will be deleted automatically
        
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
        //OutPortCorbaProvider<Float> provider = new OutPortCorbaProvider<Float>(Float.class, buffer); // will be deleted automatically
        OutPortCorbaCdrProvider provider = new OutPortCorbaCdrProvider(); // will be deleted automatically
        
        for( int i = 0; i < 10; ++i ) {
            Float writeValue = new Float(3.14159 * i);
            buffer.write(writeValue);

            // バッファに書き込まれた値を、get()メソッドで正しく読み出せるか？

            //float readValue;
            OpenRTM.CdrDataHolder cdr_data = new OpenRTM.CdrDataHolder();
            ORB m_orb = ORBUtil.getOrb();
            org.omg.CORBA.Any any = m_orb.create_any(); 
            OutputStream data = any.create_output_stream();
            provider.get(cdr_data);
            data.write_octet_array(cdr_data.value, 0, 
                                        cdr_data.value.length);
            EncapsOutputStream outcdr;
            outcdr = (EncapsOutputStream)data;
            DataRef<InputStream> dataref 
                    = new DataRef<InputStream>(outcdr.create_input_stream());
            FloatHolder holder = new FloatHolder();
            holder._read(dataref.v);
            Float readValue = holder.value;

            //float readValue;
            //Any any = provider.get();
            //readValue = any.extract_float();
            assertEquals(writeValue.floatValue(), readValue);
        }
    }
    
}
