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
//  class hello_impl
//  : virtual public POA_hello,
//    virtual public PortableServer::RefCountServantBase
//  {
//  public:
//      hello_impl(){};
//      virtual ~hello_impl(){};
//
//      void hello_world()
//      {
//          std::cout << "hello world" << std::endl;
//      }
//  };

//    CPPUNIT_TEST_SUITE(CorbaConsumerTests);
//    CPPUNIT_TEST(test_consumer);
//    CPPUNIT_TEST_SUITE_END();
  
//private:
    private ORB m_orb;
    private POA m_poa;
//    CORBA::ORB_ptr m_pORB;
//    PortableServer::POA_ptr m_pPOA;

//public:
  
    /*!
     * @brief Constructor
     */
    public CorbaConsumerTest() {
    }
//    CorbaConsumerTests()
//    {
//        int argc(0);
//        char** argv(NULL);
//        m_pORB = CORBA::ORB_init(argc, argv);
//        m_pPOA = PortableServer::POA::_narrow(
//                m_pORB->resolve_initial_references("RootPOA"));
//        m_pPOA->the_POAManager()->activate();
//    }
    
    /*!
     * @brief Destructor
     */
//    ~CorbaConsumerTests()
//    {
//    }

    /*!
     * @brief Test initialization
     */
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
//    virtual void setUp()
//    {
//    }
    
    /*!
     * @brief Test finalization
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        
        this.m_orb.destroy();
    }
//    virtual void tearDown()
//    { 
//    }
    
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
//    void test_consumer()
//    {
//        hello_impl* pHello = new hello_impl();
//      
//        PortableServer::ServantBase* servant;
//      
//        servant = pHello;
//      
//        PortableServer::ObjectId_var oid;
//        oid = m_pPOA->activate_object(servant);
//      
//        RTC::CorbaConsumer<hello> cons;
//        cons.setObject(m_pPOA->id_to_reference(oid));
//        cons._ptr()->hello_world();
//    }
}

