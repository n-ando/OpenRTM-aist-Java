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
        }
        
        public void hello_world() {
            System.out.println("hello world");
        }
    }

    private ORB m_orb;
    private POA m_poa;

    public CorbaConsumerTest() {
    }
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
        
        this.m_orb.destroy();
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
}

