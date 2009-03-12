package jp.go.aist.rtm.RTC.port;

import jp.go.aist.rtm.RTC.buffer.RingBuffer;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.NVListHolderFactory;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.ORBUtil;
import junit.framework.TestCase;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.PortableServer.POA;

import RTC.OutPortAnyPOA;
import _SDOPackage.NVListHolder;

/**
 * <p>OutPortCorbaConsumerクラスのためのテストケースです。</p>
 */
public class OutPortCorbaConsumerTest extends TestCase {

    class OutPortAnyMock extends OutPortAnyPOA {
        private Any m_data;

        public void setData(Any data) {
            m_data = data;
        }
        public Any get() {
            return m_data;
        }
    }

    private ORB m_orb;
    private POA m_poa;

    protected void setUp() throws Exception {
        super.setUp();
        // (1-1) ORBの初期化
        java.util.Properties props = new java.util.Properties();
        props.put("org.omg.CORBA.ORBInitialPort", "2809");
        props.put("org.omg.CORBA.ORBInitialHost", "localhost");
        this.m_orb = ORB.init(new String[0], props);

        // (1-2) POAManagerのactivate
        this.m_poa = org.omg.PortableServer.POAHelper.narrow(
                this.m_orb.resolve_initial_references("RootPOA"));
        this.m_poa.the_POAManager().activate();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>get()メソッドのテスト
     * <ul>
     * <li>OutPortConsumerのget()メソッド呼出によって、Provider側のデータを正しく取得できる？</li>
     * </ul>
     * </p>
     */
    public void test_get() throws Exception {
        OutPortAnyMock outPortAny = new OutPortAnyMock();
        byte[] oid = this.m_poa.activate_object(outPortAny);

        RingBuffer<Float> buffer = new RingBuffer<Float>(100);
        OutPortCorbaConsumer<Float> consumer = new OutPortCorbaConsumer<Float>(Float.class, buffer);
        consumer.setObject(m_poa.id_to_reference(oid));
        
        // InPortConsumerのput()メソッドを呼び出す
        Float writeValue = new Float(3.14159f); 
        Any writeValueAny = ORBUtil.getOrb().create_any();
        writeValueAny.insert_float(writeValue);
        outPortAny.setData(writeValueAny);
        
        // OutPortCorbaConsumer::get()を用いて、データを読み取る
        DataRef<Float> readValue = new DataRef<Float>(0f);
        assertTrue(consumer.get(readValue));
        
        // テスト用に設定しておいたデータを読み取ったデータを比較し、正しく取得できたことを確認する
        assertTrue( Math.abs(writeValue.doubleValue()-readValue.v) < 0.00001);
    }
    /**
     * <p>pull()メソッドのテスト
     * <ul>
     * <li>OutPortConsumerのpull()メソッド呼出によって、Provider側のデータが正しくバッファに書き込まれるか？</li>
     * </ul>
     * </p>
     */
    public void test_pull() throws Exception {
        // 接続先となるProvider側のオブジェクトを生成し、設定する
        OutPortAnyMock outPortAny = new OutPortAnyMock();
        byte[] oid = this.m_poa.activate_object(outPortAny);

        RingBuffer<Float> buffer = new RingBuffer<Float>(100);
        OutPortCorbaConsumer<Float> consumer = new OutPortCorbaConsumer<Float>(Float.class, buffer);
        consumer.setObject(m_poa.id_to_reference(oid));
        
        // Provider側にテスト用のデータを設定しておく
        Float writeValue = new Float(3.14159f); 
        Any writeValueAny = ORBUtil.getOrb().create_any();
        writeValueAny.insert_float(writeValue);
        outPortAny.setData(writeValueAny);
        
        // pull()メソッドを呼出して、OutPortAny側のデータをバッファへ読み込む
        consumer.pull();
        
        // バッファからデータを読み出して、テスト用に設定しておいたデータを読み取ったデータを比較し、正しく取得できたことを確認する
        DataRef<Float> readValue = new DataRef<Float>(0f);
        assertTrue(buffer.read(readValue));
        assertTrue( Math.abs(writeValue.doubleValue()-readValue.v) < 0.00001);
    }
    /**
     * <p>subscribeInterface()メソッドのテスト
     * <ul>
     * <li>プロパティにOutPortAnyのリファレンスを設定して、subscribeInterface()により登録が成功するか？</li>
     * </ul>
     * </p>
     */
    public void test_subscribeInterface() throws Exception {
        OutPortAnyMock outPortAny = new OutPortAnyMock();
        byte[] oid = this.m_poa.activate_object(outPortAny);

        RingBuffer<Float> buffer = new RingBuffer<Float>(100);
        OutPortCorbaConsumer<Float> consumer = new OutPortCorbaConsumer<Float>(Float.class, buffer);
        org.omg.CORBA.Object outPortAnyRef = m_poa.id_to_reference(oid);
//        Any outPortAnyRefAny = ORBUtil.getOrb().create_any();
//        outPortAnyRefAny.insert_Object(outPortAnyRef);

        // プロパティにOutPortAnyのリファレンスを設定して、subscribeInterface()により登録が成功するか？
        NVListHolder properties = NVListHolderFactory.create();
        CORBA_SeqUtil.push_back(properties, NVUtil.newNV("dataport.dataflow_type", "Pull"));
        CORBA_SeqUtil.push_back(properties, NVUtil.newNV("dataport.corba_any.outport_ref", outPortAnyRef, Object.class));
        assertTrue(consumer.subscribeInterface(properties));
    }
}
