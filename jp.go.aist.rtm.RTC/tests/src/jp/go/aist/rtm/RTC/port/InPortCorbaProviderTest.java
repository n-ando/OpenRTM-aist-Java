package jp.go.aist.rtm.RTC.port;

import jp.go.aist.rtm.RTC.buffer.RingBuffer;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.NVListHolderFactory;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.TypeCast;
import junit.framework.TestCase;

import org.omg.CORBA.Any;

import RTC.Time;
import RTC.TimedFloat;
import _SDOPackage.NVListHolder;

/**
 * <p>InPortProviderクラスのためのテストケースです。</p>
 */
public class InPortCorbaProviderTest extends TestCase {

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
        InPortCorbaProvider<TimedFloat> provider = new InPortCorbaProvider<TimedFloat>(TimedFloat.class, buffer); // will be deleted automatically
        
        NVListHolder profile = NVListHolderFactory.create();
        provider.publishInterfaceProfile(profile);
        
        // "dataport.data_type"プロパティを正しく取得できるか？
        assertEquals("TimedFloat", NVUtil.toString(profile, "dataport.data_type"));
        
        // "dataport.interface_type"プロパティを正しく取得できるか？
        assertEquals("CORBA_Any", NVUtil.toString(profile, "dataport.interface_type"));
        
        // "dataport.dataflow_type"プロパティを正しく取得できるか？
        assertEquals("Push, Pull", NVUtil.toString(profile, "dataport.dataflow_type"));
        
        // "dataport.subscription_type"プロパティを正しく取得できるか？
        assertEquals("Any", NVUtil.toString(profile, "dataport.subscription_type"));
    }
    /**
     * <p>（インタフェース・タイプが適合する場合の）publishInterface()メソッドのテスト
     * <ul>
     * <li>"dataport.corba_any.inport_ref"プロパティを正しく取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_publishInterface_matched_interfaceType() throws Exception {
        RingBuffer<TimedFloat> buffer = new RingBuffer<TimedFloat>(100);
        InPortCorbaProvider<TimedFloat> provider = new InPortCorbaProvider<TimedFloat>(TimedFloat.class, buffer); // will be deleted automatically
        
        // インタフェース・タイプが適合するように設定したうえで、インタフェース情報を取得する
        NVListHolder properties = NVListHolderFactory.create();
        NVUtil.appendStringValue(properties, "dataport.interface_type", "CORBA_Any");
        provider.publishInterface(properties);

        // "dataport.corba_any.inport_ref"プロパティを正しく取得できるか？
        Any inPortAnyRef =  NVUtil.find(properties, "dataport.corba_any.inport_ref");
        assertNotNull(inPortAnyRef);
    }
    /**
     * <p>（インタフェース・タイプが適合しない場合の）publishInterface()メソッドのテスト
     * <ul>
     * <li>"dataport.corba_any.inport_ref"プロパティは、意図どおり取得されていないか？</li>
     * </ul>
     * </p>
     */
    public void test_publishInterface_unmatched_interfaceType() throws Exception {
        RingBuffer<TimedFloat> buffer = new RingBuffer<TimedFloat>(100);
        InPortCorbaProvider<TimedFloat> provider = new InPortCorbaProvider<TimedFloat>(TimedFloat.class, buffer); // will be deleted automatically
        
        // インタフェース・タイプが適合するように設定したうえで、インタフェース情報を取得する
        NVListHolder properties = NVListHolderFactory.create();
        NVUtil.appendStringValue(properties, "dataport.interface_type", "UNMATCHED_INTERFACE_TYPE");
        provider.publishInterface(properties);

        // "dataport.corba_any.inport_ref"プロパティを正しく取得できるか？
        try{
            NVUtil.find(properties, "dataport.corba_any.inport_ref");
            fail();
        } catch(Exception ex) {
            assertEquals("Not found", ex.getMessage());
        }
    }
    /**
     * <p>put()メソッドのテスト
     * <ul>
     * <li>put()メソッドを通して書き込んだ値が、正しくバッファに書き込まれているか？</li>
     * </ul>
     * </p>
     */
    public void test_put() throws Exception {
        RingBuffer<TimedFloat> buffer = new RingBuffer<TimedFloat>(100);
        InPortCorbaProvider<TimedFloat> provider = new InPortCorbaProvider<TimedFloat>(TimedFloat.class, buffer); // will be deleted automatically
        
        // 値をInPortCorbaProviderを通して書き込む
        TimedFloat putValue = new TimedFloat();
        putValue.data = 3.14159f;
        putValue.tm = new Time();
        Any putValueAny = new TypeCast<TimedFloat>(TimedFloat.class).castAny(putValue);
        provider.put(putValueAny);
        
        // バッファから値を取り出して、正しく書き込まれたことを確認する
        TimedFloat readValue = new TimedFloat();
        DataRef<TimedFloat> readValuep = new DataRef<TimedFloat>(readValue);
        buffer.read(readValuep);
        assertEquals(putValue.data, readValuep.v.data);
    }
    
}
