package jp.go.aist.rtm.RTC.port;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAManager;
import org.omg.PortableServer.POAManagerPackage.State;

import _SDOPackage.NVListHolder;

import RTC.ConnectorProfile;
import RTC.ConnectorProfileHolder;
import RTC.InPortAny;
import RTC.InPortAnyHelper;
import RTC.Port;
import RTC.PortHelper;
import RTC.PortInterfacePolarity;
import RTC.PortProfile;
import RTC.TimedFloat;
import RTC.TimedFloatHelper;


import junit.framework.TestCase;
import jp.go.aist.rtm.RTC.buffer.NullBuffer;
import jp.go.aist.rtm.RTC.port.DataInPort;
import jp.go.aist.rtm.RTC.port.InPort;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.ConnectorProfileFactory;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.ORBUtil;

/**
 * <p>DataInPortクラスのためのテストケースです。</p>
 */
public class DataInPortTest extends TestCase {

    private class OrbRunner implements Runnable {

        private final String[] ARGS = new String[] {
            "-ORBInitialPort 2809",
            "-ORBInitialHost localhost"
        };
        
        public void start() throws Exception {
            
            _orb = ORBUtil.getOrb(ARGS);
            POA poa = org.omg.PortableServer.POAHelper.narrow(
                    _orb.resolve_initial_references("RootPOA"));
            _poaMgr = poa.the_POAManager();
            if (! _poaMgr.get_state().equals(State.ACTIVE)) {
                _poaMgr.activate();
            }
            
            (new Thread(this)).start();
            Thread.sleep(1000);
        }
        
        public void shutdown() throws Exception {
            
            _poaMgr.discard_requests(true);
        }
        
        public void run() {
            
            _orb.run();
        }
        
        public ORB getORB() {
            
            return _orb;
        }
        
        private ORB _orb;
        private POAManager _poaMgr;
    }
    
    private class DataInPortMock extends DataInPort<TimedFloat> {
        
        public DataInPortMock(final String name, InPort<TimedFloat> inport) throws Exception {
            super(TimedFloat.class, name, inport);
            
            this.appendInterface(instance_name, type_name, pol);
        }
        
        public String instance_name = "DataInPortMock_instance_name";
        public String type_name = "DataInPortMock_type_name";
        public PortInterfacePolarity pol = PortInterfacePolarity.PROVIDED;
    }
    
    private class HogeConvert_TimedFloat implements OnReadConvert<TimedFloat> {

        public TimedFloat run(TimedFloat value) {
            
            TimedFloat d = new TimedFloat(value.tm, value.data);
            d.data = value.data * value.data;
            return d;
        }
    }
//    template <class DataType>
//    struct HogeCovnert : public RTC::OnReadConvert<DataType>
//    {
//        DataType operator()(const DataType& value)
//        {
//            DataType d(value);
//            d.data = value.data * value.data;
//            return d;
//        }
//    };

//    CPPUNIT_TEST_SUITE(DataInPortTests);
//    CPPUNIT_TEST(test_read);
//    CPPUNIT_TEST(test_profile);
//    CPPUNIT_TEST(test_connect);
//    CPPUNIT_TEST_SUITE_END();
   
//private:
    private InPort<TimedFloat> m_inport;
    private TimedFloat m_tfloat;
    private DataInPort<TimedFloat> m_dinport;
    private Port m_portref;
    private HogeConvert_TimedFloat m_conv;
    private NamingContextExt m_ncRef;
    private NameComponent[] m_path;
    private OrbRunner m_orbRunner;
    
//    RTC::InPort<RTC::TimedFloat> m_inport;
//    RTC::TimedFloat m_tfloat;
//    RTC::DataInPort* m_dinport;
//    RTC::Port_var m_portref;
//    HogeCovnert<RTC::TimedFloat>* m_conv;
//    CORBA::ORB_ptr m_pORB;
//    PortableServer::POA_ptr m_pPOA;
     
//public:
   
    /*!
     * @brief Constructor
     */
    public DataInPortTest() {
    }
//    DataInPortTests()
//    : m_inport("fin", m_tfloat)
//    {
//        m_conv = new HogeCovnert<RTC::TimedFloat>();
//        m_inport.setOnReadConvert(m_conv);
//        int argc(0);
//        char** argv(NULL);
//        m_pORB = CORBA::ORB_init(argc, argv);
//        m_pPOA = PortableServer::POA::_narrow(
//                m_pORB->resolve_initial_references("RootPOA"));
//        m_pPOA->the_POAManager()->activate();
//    
//        m_dinport = new RTC::DataInPort("DataInPortTest", m_inport);
//
//        //      PortableServer::ObjectId_var oid;
//        //      oid = m_pPOA->activate_object(m_doutport);
//        m_portref = m_dinport->get_port_profile()->port_ref;
//    }
     
    /*!
     * @brief Destructor
     */
//    ~DataInPortTests()
//    {
//    }

    /*!
     * @brief Test initialization
     */
    protected void setUp() throws Exception {
        super.setUp();
        
        this.m_orbRunner = new OrbRunner();
        this.m_orbRunner.start();

        this.m_inport = new InPort<TimedFloat>(new NullBuffer<TimedFloat>(),
                "fin", new DataRef<TimedFloat>(this.m_tfloat));
        this.m_conv = new HogeConvert_TimedFloat();
        this.m_dinport = new DataInPortMock("DataInPortTest", this.m_inport);
        this.m_portref = this.m_dinport.get_port_profile().port_ref;
    }
//    virtual void setUp()
//    {
//    }
     
    /*!
     * @brief Test finalization
     */
    protected void tearDown() throws Exception {
        super.tearDown();

        this.m_orbRunner.shutdown();
    }
//    virtual void tearDown()
//    { 
//    }
   
    /* test case */
//    void test_case0()
//    {
//    }
    
    /**
     * <p>get_port_profile()メソッドによるポートプロファイル取得をテストします。</p>
     * <p>取得されたPortProfileオブジェクトのうち、次のメンバについて期待値と一致することを確認します。
     * <ul>
     * <li>ポート名</li>
     * <li>インタフェースのインスタンス名</li>
     * <li>インタフェースのタイプ名</li>
     * <li>インタフェースの極性</li>
     * </ul>
     * </p>
     */
    public void test_profile() {
        
        PortProfile prof = this.m_dinport.get_port_profile();

        assertEquals("DataInPortTest", prof.name);
        assertEquals("DataInPortMock_instance_name", prof.interfaces[0].instance_name);
        assertEquals("DataInPortMock_type_name", prof.interfaces[0].type_name);
        assertEquals(PortInterfacePolarity.PROVIDED, prof.interfaces[0].polarity);
        assertTrue(this.m_orbRunner.getORB().object_to_string(prof.port_ref).length() > 0);

        for (int i = 0; i < prof.properties.length; ++i) {
            System.out.println("prop_name: " + prof.properties[i].name);
            System.out.println("prop_valu: "
                    + NVUtil.toString(new NVListHolder(prof.properties), prof.properties[i].name));
        }
    }
//    void test_profile()
//    {
//        RTC::PortProfile* prof;
//        prof = m_dinport->get_port_profile();
//       
//        std::cout << "port_name: " << prof->name << std::endl;
//        std::cout << "if_name: " << prof->interfaces[0].instance_name
//        << std::endl;
//        std::cout << "if_type: " << prof->interfaces[0].type_name << std::endl;
//        std::cout << "if_polr: " << prof->interfaces[0].polarity << std::endl;
//        std::cout << "IOR: " << m_pORB->object_to_string(prof->port_ref)
//        <<std::endl;
//
//        for (CORBA::ULong i = 0; i < prof->properties.length(); ++i)
//        {
//            std::cout << "prop_name: " << prof->properties[i].name << std::endl;
//            std::cout << "prop_valu: "
//            <<  NVUtil::toString(prof->properties,
//                        prof->properties[i].name)
//                        << std::endl;
//        }
//    }

    /**
     * <p>接続のテストです。複数ポート間の接続ではなく、単体ポートのみでの接続動作をテストします。</p>
     */
    public void test_connect() {
        
        ConnectorProfile prof = ConnectorProfileFactory.create();
        prof.connector_id = "";
        prof.name = "connector0";
        prof.ports = new Port[1];
        prof.ports[0] = this.m_portref;
        
        NVListHolder properties = new NVListHolder(prof.properties);
        CORBA_SeqUtil.push_back(properties,
                NVUtil.newNV("dataport.interface_type", "CORBA_Any"));
        CORBA_SeqUtil.push_back(properties,
                NVUtil.newNV("dataport.dataflow_type", "Push"));
        CORBA_SeqUtil.push_back(properties,
                NVUtil.newNV("dataport.subscription_type", "New"));
        prof.properties = properties.value;
        
        this.m_dinport.connect(new ConnectorProfileHolder(prof));
        
        System.out.println(prof.connector_id);

        for (int i = 0; i < prof.properties.length; ++i) {
            System.out.println("prop_name: " + prof.properties[i].name);
            NVListHolder prof_properties = new NVListHolder(prof.properties);
            if (NVUtil.isString(prof_properties, prof.properties[i].name)) {
                System.out.println("prop_valu: "
                        + NVUtil.toString(prof_properties, prof.properties[i].name));
            }
            else {
                String s = prof.properties[i].name;
                if (s.equals("dataport.corba_any.inport_ref")) {
                    Object o = prof.properties[i].value.extract_Object();
                    Port p = PortHelper.narrow(prof.properties[i].value.extract_Object());
                    InPortAny ip = InPortAnyHelper.narrow(prof.properties[i].value.extract_Object());

                    System.out.println("prop_valu: "
                            + m_orbRunner.getORB().object_to_string(o));
                    System.out.println("prop_valu: "
                            + m_orbRunner.getORB().object_to_string(p));
                    System.out.println("prop_valu: "
                            + m_orbRunner.getORB().object_to_string(ip));
                    
                    for (int j = 0; j < 1000; ++j) {
                        TimedFloat fdata = new TimedFloat();
                        fdata.data = j;
                        Any adata = this.m_orbRunner.getORB().create_any();
                        TimedFloatHelper.insert(adata, fdata);
                        
                        ip.put(adata);

                        TimedFloat data = this.m_inport.read();
                        
                        System.out.println(data.data);
                    }
                }
            }
        }
    }
//    void test_connect()
//    {
//        RTC::ConnectorProfile prof;
//        prof.connector_id = "";
//        prof.name = CORBA::string_dup("connector0");
//        prof.ports.length(1);
//        prof.ports[0] = m_portref;
//        CORBA_SeqUtil::push_back(prof.properties,
//                NVUtil::newNV("dataport.interface_type",
//                "CORBA_Any"));
//        CORBA_SeqUtil::push_back(prof.properties,
//                NVUtil::newNV("dataport.dataflow_type",
//                "Push"));
//        CORBA_SeqUtil::push_back(prof.properties,
//                NVUtil::newNV("dataport.subscription_type",
//                "New"));
//        m_dinport->connect(prof);
//
//        std::cout << prof.connector_id << std::endl;
//
//        for (CORBA::ULong i = 0; i < prof.properties.length(); ++i)
//        {
//            std::cout << "prop_name: " << prof.properties[i].name << std::endl;
//            if (NVUtil::isString(prof.properties, prof.properties[i].name))
//            {
//                std::cout << "prop_valu: "
//                <<  NVUtil::toString(prof.properties,
//                        prof.properties[i].name)
//                        << std::endl;
//            }
//            else
//            {
//                std::string s(CORBA::string_dup(prof.properties[i].name));
//                if (s == "dataport.corba_any.inport_ref")
//                {
//                    CORBA::Object_ptr o;
//                    RTC::Port_ptr p;
//                    RTC::InPortAny_ptr ip;
//                    prof.properties[i].value >>= o;
//                    prof.properties[i].value >>= p;
//                    prof.properties[i].value >>= ip;
//                    //          prof.properties[i].value >>= i;
//          
//                    std::cout << "prop_valu: "
//                    <<  m_pORB->object_to_string(o)
//                    << std::endl;
//                    std::cout << "prop_valu: "
//                    <<  m_pORB->object_to_string(p)
//                    << std::endl;
//                    std::cout << "prop_valu: "
//                    <<  m_pORB->object_to_string(ip)
//                    << std::endl;
//                    /*
//                     std::cout << "prop_valu: "
//                     <<  m_pORB->object_to_string(i)
//                     << std::endl;
//                     */
//
//                    for (int j = 0; j < 1000; ++j)
//                    {
//                        RTC::TimedFloat fdata;
//                        fdata.data = j;
//                        CORBA::Any_var adata(new CORBA::Any());
//                        adata <<= fdata;
//              
//                        ip->put(adata);
//                        
//                        RTC::TimedFloat data;
//                        data = m_inport.read();
//              
//                        std::cout << data.data << std::endl;
//                    }
//                }
//            }
//        }
//    }
}
