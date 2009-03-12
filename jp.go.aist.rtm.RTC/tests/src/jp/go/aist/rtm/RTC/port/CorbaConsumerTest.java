package jp.go.aist.rtm.RTC.port;

import jp.go.aist.rtm.bind.CorbaConsumer.hello;
import jp.go.aist.rtm.bind.CorbaConsumer.helloPOA;
import junit.framework.TestCase;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;

/**
 * <p>CorbaConsumerクラスのためのテストケースです。</p>
 */
public class CorbaConsumerTest extends TestCase {

    private class hello_impl extends helloPOA {
        
        public hello_impl() {
            m_invokedCount = 0;
        }
        
        public void hello_world() {
            ++m_invokedCount;
            System.out.println("hello world");
        }
        public int m_invokedCount;
    }

    private ORB m_orb;
    private POA m_poa;

    public CorbaConsumerTest() {
    }
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
     * <p>次のテストを行います。
     * <ul>
     * <li>setObject()メソッドによるCORBAオブジェクト参照の設定</li>
     * <li>_ptr()メソッドによる、設定したCORBAオブジェクト取得</li>
     * </p>
     */
    public void test_consumer() throws Exception {
        
        Servant servant = new hello_impl();
        byte[] oid = this.m_poa.activate_object(servant);

        CorbaConsumer<hello> cons = new CorbaConsumer<hello>(hello.class);
        org.omg.CORBA.Object obj = this.m_poa.id_to_reference(oid);
        cons.setObject(obj);
        hello helloRef = cons._ptr();
        helloRef.hello_world();
    }
    
    /**
     * <p>setObject()メソッドと_ptr()メソッドのテスト
     * <ul>
     * <li>生成したサーバントをConsumerにセットした後、Consumerを通してサーバントのメソッドを正しく呼び出せるか？</li>
     * </p>
     */
    public void test_setObject_and__ptr() throws Exception {
        // Consumerに割り当てるオブジェクトを生成する
        hello_impl helloImpl = new hello_impl();
        byte[] oid = this.m_poa.activate_object(helloImpl);
        
        // 生成したオブジェクトをConsumerにセットする
        CorbaConsumer<hello> consumer = new CorbaConsumer<hello>(hello.class);
        org.omg.CORBA.Object obj = this.m_poa.id_to_reference(oid);
        consumer.setObject(obj);
        hello helloRef = consumer._ptr();
        
        // Consumerに割り当てたオブジェクトのメソッドを正しく呼び出せるか？
        assertEquals(0, helloImpl.m_invokedCount);
        helloRef.hello_world();
        assertEquals(1, helloImpl.m_invokedCount);
    }

    /**
     * <p>コピーコンストラクタのテスト
     * <ul>
     * <li>既存のConsumerオブジェクトを元に、別の新たなConsumerをコピーコンストラクトし、
     *     その新たなConsumerオブジェクトに対してメソッドを正しく呼び出せるか？</li>
     * </p>
     */
    public void test_copy_constructor() throws Exception {
        // Consumerに割り当てるオブジェクトを生成する
        hello_impl helloImpl = new hello_impl();
        byte[] oid = this.m_poa.activate_object(helloImpl);
        
        // 生成したオブジェクトをConsumerにセットする
        CorbaConsumer<hello> consumer = new CorbaConsumer<hello>(hello.class);
        org.omg.CORBA.Object obj = this.m_poa.id_to_reference(oid);
        consumer.setObject(obj);
        
        // 作成したConsumerオブジェクトを元に、別の新たなConsumerをコピーコンストラクトする
        CorbaConsumer<hello> consumerNew = new CorbaConsumer<hello>(consumer);
        hello helloRef = consumerNew._ptr();
        
        // 新たに作成したConsumerに対して、メソッドを正しく呼び出せるか？
        assertEquals(0, helloImpl.m_invokedCount);
        helloRef.hello_world();
        assertEquals(1, helloImpl.m_invokedCount);
    }

    /**
     * <p>releaseObject()メソッドのテスト
     * <ul>
     * <li>releaseObject()呼出によって、保持されているオブジェクト参照が正しくクリアされるか？</li>
     * </p>
     */
    public void test_releaseObject() throws Exception {
        // Consumerに割り当てるオブジェクトを生成する
        hello_impl helloImpl = new hello_impl();
        byte[] oid = this.m_poa.activate_object(helloImpl);
        
        // 生成したオブジェクトをConsumerにセットする
        CorbaConsumer<hello> consumer = new CorbaConsumer<hello>(hello.class);
        org.omg.CORBA.Object obj = this.m_poa.id_to_reference(oid);
        consumer.setObject(obj);

        // この時点では、オブジェクト参照は保持されているはず
        assertNotNull(consumer._ptr());
        
        // releaseObject()呼出によって、保持されているオブジェクト参照が正しくクリアされるか？
        consumer.releaseObject();
        assertNull(consumer._ptr());
    }
}

