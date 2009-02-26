package jp.go.aist.rtm.RTC.port;

import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.buffer.RingBuffer;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.NVListHolderFactory;
import jp.go.aist.rtm.RTC.util.NVUtil;
import junit.framework.TestCase;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.PortableServer.POA;

import RTC.InPortAnyPOA;
import _SDOPackage.NVListHolder;

/**
 * <p>InPortCorbaConsumerクラスのためのテストケースです。</p>
 */
public class InPortCorbaConsumerTest extends TestCase {

    class InPortAnyMock extends InPortAnyPOA {
        private Any m_data;

        public void put(Any data) {
            m_data = data;
        }
        public Any getData() {
            return m_data;
        }
    }

    /*!
     * @brief 書き込みも読み込みも不可のダミーバッファ
     */
    private class BufferDummy<DataType> implements BufferBase<DataType> {
        private DataType m_data;

        public DataType get() { return m_data; }
        public boolean isEmpty() { return true; }
        public boolean isFull() { return true; }
        public boolean isNew() { return false; }
        public int length() { return 0; }
        public void put(DataType data) {}
        public boolean read(DataRef<DataType> valueRef) { return false; }
        public boolean write(DataType value) { return false; }
    }
    
    private ORB m_orb;
    private POA m_poa;

    protected void setUp() throws Exception {
        super.setUp();
        // (1-1) ORBの初期化
        java.util.Properties props = new java.util.Properties();
        this.m_orb = ORB.init(new String[0], props);

        // (1-2) POAManagerのactivate
        this.m_poa = org.omg.PortableServer.POAHelper.narrow(
                this.m_orb.resolve_initial_references("RootPOA"));
        this.m_poa.the_POAManager().activate();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        if( m_orb != null) {
            m_orb.destroy();
            m_orb = null;
        }
    }

    /**
     * <p>put()メソッドのテスト
     * <ul>
     * <li>InPortConsumerのput()メソッド呼出によって、InPortAnyのput()メソッド側へ正しくデータが渡されるか？</li>
     * </ul>
     * </p>
     */
    public void test_put() throws Exception {
        InPortAnyMock inPortAny = new InPortAnyMock();
        byte[] oid = this.m_poa.activate_object(inPortAny);

        RingBuffer<Float> buffer = new RingBuffer<Float>(100);
        InPortCorbaConsumer<Float> consumer = new InPortCorbaConsumer<Float>(Float.class, buffer);
        consumer.setObject(m_poa.id_to_reference(oid));
        
        // InPortConsumerのput()メソッドを呼び出す
        Float writeValue = new Float(3.14159f); 
        consumer.put(writeValue);
        
        // InPortAny側のput()メソッドにデータが正しく渡されているか？
        Any readValueAny = inPortAny.getData();
        Float pReadValue = readValueAny.extract_float();
        assertEquals(writeValue, pReadValue);
    }
    /**
     * <p>push()メソッドのテスト
     * <ul>
     * <li>バッファに書き込んでおいたデータが、push()メソッド呼出によってInPortAnyのput()へ正しく送出されるか？</li>
     * </ul>
     * </p>
     */
    public void test_push() throws Exception {
        InPortAnyMock inPortAny = new InPortAnyMock();
        byte[] oid = this.m_poa.activate_object(inPortAny);

        RingBuffer<Float> buffer = new RingBuffer<Float>(100);
        InPortCorbaConsumer<Float> consumer = new InPortCorbaConsumer<Float>(Float.class, buffer);
        consumer.setObject(m_poa.id_to_reference(oid));
        
        // バッファにデータを書き込んで、push()メソッドで送出させる
        Float writeValue = new Float(3.14159f);
        buffer.write(writeValue);
        consumer.push();
        
        // InPortAny側のput()メソッドにデータが正しく渡されているか？
        Any readValueAny = inPortAny.getData();
        Float pReadValue = readValueAny.extract_float();
        assertEquals(writeValue, pReadValue);
    }
    /**
     * <p>subscribeInterface()メソッドのテスト
     * <ul>
     * <li>プロパティにInPortAnyのリファレンスを設定して、subscribeInterface()により登録が成功するか？</li>
     * </ul>
     * </p>
     */
    public void test_subscribeInterface() throws Exception {
        InPortAnyMock inPortAny = new InPortAnyMock();
        byte[] oid = this.m_poa.activate_object(inPortAny);

        RingBuffer<Float> buffer = new RingBuffer<Float>(100);
        InPortCorbaConsumer<Float> consumer = new InPortCorbaConsumer<Float>(Float.class, buffer);
        org.omg.CORBA.Object inPortAnyRef = m_poa.id_to_reference(oid);
//        Any inPortAnyRefAny = ORBUtil.getOrb().create_any();
//        inPortAnyRefAny.insert_Object(inPortAnyRef);

        // プロパティにInPortAnyのリファレンスを設定して、subscribeInterface()により登録が成功するか？
        NVListHolder properties = NVListHolderFactory.create();
        CORBA_SeqUtil.push_back(properties, NVUtil.newNV("dataport.dataflow_type", "Push"));
        CORBA_SeqUtil.push_back(properties, NVUtil.newNV("dataport.corba_any.inport_ref", inPortAnyRef, Object.class));
        assertTrue(consumer.subscribeInterface(properties));
    }
    /**
     * <p>コピーコンストラクタのテスト
     * <ul>
     * <li>コピー生成されたInPortConsumerのput()メソッド呼出によって、InPortAnyのput()メソッド側へ正しくデータが渡されるか？</li>
     * </ul>
     * </p>
     */
    public void test_copy_constructor() throws Exception {
        InPortAnyMock inPortAny = new InPortAnyMock();
        byte[] oid = this.m_poa.activate_object(inPortAny);

        RingBuffer<Float> buffer = new RingBuffer<Float>(100);
        InPortCorbaConsumer<Float> consumer = new InPortCorbaConsumer<Float>(Float.class, buffer);
        consumer.setObject(m_poa.id_to_reference(oid));
        
        // 生成したInPortCorbaConsumerを元にして、別のInPortCorbaConsumerをコピー生成する
        InPortCorbaConsumer<Float> consumerNew = new InPortCorbaConsumer<Float>(consumer);
        
        // コピー生成されたInPortConsumerのput()メソッドを呼び出す
        Float writeValue = new Float(3.14159f);
        consumerNew.put(writeValue);
        
        // InPortAny側のput()メソッドにデータが正しく渡されているか？
        Any readValueAny = inPortAny.getData();
        Float pReadValue = readValueAny.extract_float();
        assertEquals(writeValue, pReadValue);
    }
    /**
     * <p>代入演算子（operator=）のテスト
     * <ul>
     * <li>既存のInPortConsumerを代入された別のInPortConsumerのput()メソッド呼出によって、InPortAnyのput()メソッド側へ正しくデータが渡されるか？</li>
     * </ul>
     * </p>
     */
    public void test_substitute_operator() throws Exception {
        InPortAnyMock inPortAny = new InPortAnyMock();
        byte[] oid = this.m_poa.activate_object(inPortAny);

        RingBuffer<Float> buffer = new RingBuffer<Float>(100);
        InPortCorbaConsumer<Float> consumer = new InPortCorbaConsumer<Float>(Float.class, buffer);
        consumer.setObject(m_poa.id_to_reference(oid));
        
        // 生成したInPortCorbaConsumerを元にして、別のInPortCorbaConsumerを代入生成する
        BufferDummy<Float> bufferDummy = new BufferDummy<Float>();
        InPortCorbaConsumer<Float> consumerNew = new InPortCorbaConsumer<Float>(Float.class, bufferDummy);
        consumerNew = consumer;

        // 代入生成されたInPortConsumerのput()メソッドを呼び出す
        Float writeValue = new Float(3.14159f);
        consumerNew.put(writeValue);
        
        // InPortAny側のput()メソッドにデータが正しく渡されているか？
        Any readValueAny = inPortAny.getData();
        Float pReadValue = readValueAny.extract_float();
        assertEquals(writeValue, pReadValue);
    }
    /**
     * <p>cloneのテスト
     * <ul>
     * <li>clone()によって生成されたInPortCorbaConsumerのput()メソッド呼出によって、InPortAnyのput()メソッド側へ正しくデータが渡されるか？</li>
     * </ul>
     * </p>
     */
    public void test_clone() throws Exception {
        InPortAnyMock inPortAny = new InPortAnyMock();
        byte[] oid = this.m_poa.activate_object(inPortAny);

        RingBuffer<Float> buffer = new RingBuffer<Float>(100);
        InPortCorbaConsumer<Float> consumer = new InPortCorbaConsumer<Float>(Float.class, buffer);
        consumer.setObject(m_poa.id_to_reference(oid));
        
        // 生成したInPortCorbaConsumerを元にして、別のInPortCorbaConsumerをclone()で生成する
        InPortCorbaConsumer<Float> consumerNew = consumer.clone();

        // 代入生成されたInPortConsumerのput()メソッドを呼び出す
        Float writeValue = new Float(3.14159f);
        consumerNew.put(writeValue);
        
        // InPortAny側のput()メソッドにデータが正しく渡されているか？
        Any readValueAny = inPortAny.getData();
        Float pReadValue = readValueAny.extract_float();
        assertEquals(writeValue, pReadValue);
    }
}
