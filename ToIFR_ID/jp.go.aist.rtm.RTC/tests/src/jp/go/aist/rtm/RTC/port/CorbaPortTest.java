package jp.go.aist.rtm.RTC.port;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAManager;
import org.omg.PortableServer.POAManagerPackage.State;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import RTC.ConnectorProfile;
import RTC.ConnectorProfileHolder;
import RTC.PortService;
import RTC.PortInterfacePolarity;
import RTC.PortInterfaceProfile;
import RTC.PortInterfaceProfileListHolder;
import RTC.PortProfile;
import RTC.ReturnCode_t;

import jp.go.aist.rtm.RTC.port.CorbaPort;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.ConnectorProfileFactory;
import jp.go.aist.rtm.RTC.util.ORBUtil;
import jp.go.aist.rtm.RTC.util.equalFunctor;
import jp.go.aist.rtm.bind.CorbaPort.MyService;
import jp.go.aist.rtm.bind.CorbaPort.MyServicePOA;
import junit.framework.TestCase;

/**
 * <p>CorbaPortクラスのためのテストケースです。</p>
 */
public class CorbaPortTest extends TestCase {

    class CorbaPortMock extends CorbaPort {
        /**
         *
         */
        public CorbaPortMock(final String name){
            super(name);
        }
        /**
         *
         */
        public void deactivateInterfaces_public() {
            deactivateInterfaces();
        }
        /**
         *
         */
        public void activateInterfaces_public() {
            activateInterfaces();
        }
    };
    private class OrbRunner implements Runnable {

        private final String[] ARGS = new String[] {
            "-ORBInitialPort 2809",
            "-ORBInitialHost localhost"
        };
        
        public void start() throws Exception {
            _orb = ORBUtil.getOrb(ARGS);
            _poa = org.omg.PortableServer.POAHelper.narrow(
                    _orb.resolve_initial_references("RootPOA"));
            _poaMgr = _poa.the_POAManager();
            if (! _poaMgr.get_state().equals(State.ACTIVE)) {
                _poaMgr.activate();
            }
            
            (new Thread(this)).start();
            Thread.sleep(1000);
        }
        public void shutdown() throws Exception {
            _poaMgr.discard_requests(false);
        }
        public void run() {
            _orb.run();
        }
        public ORB getORB() {
            return _orb;
        }
        
        private ORB _orb;
        private POA _poa;
        private POAManager _poaMgr;
    }
    
    private class MyService_impl extends MyServicePOA {
        
        public MyService_impl(){
            m_hello_world_called = false;
        }
        public void setName(final String name) {
            this.m_name = name;
        }
        public String name() {
            return "MyService";
        }
        public void hello_world() {
            System.out.println(this.m_name + ": Hello, World!!!");
            m_hello_world_called = true;
        }
        public void print_msg(final String msg) {
            System.out.println(this.m_name + ": " + msg);
        }
        public boolean is_hello_world_called() {
            return m_hello_world_called;
        }
        
        private String m_name;
        private boolean m_hello_world_called;
    }

//    private CorbaPort m_port0;
//    private Port m_port0ref;
//    private MyService_impl m_mysvc0 = new MyService_impl();
//    private CorbaConsumer<MyService> m_cons0 = new CorbaConsumer<MyService>(MyService.class);
    
//    private CorbaPort m_port1;
//    private Port m_port1ref;
//    private MyService_impl m_mysvc1 = new MyService_impl();
//    private CorbaConsumer<MyService> m_cons1 = new CorbaConsumer<MyService>(MyService.class);
    
    private OrbRunner m_orbRunner;

    
    protected void setUp() throws Exception {
        super.setUp();

        this.m_orbRunner = new OrbRunner();
        this.m_orbRunner.start();
/*
        this.m_port0 = new CorbaPort("port0");
        this.m_port1 = new CorbaPort("port1");
        
//        this.m_port0ref = this.m_port0.getPortRef();
//        this.m_port1ref = this.m_port1.getPortRef();

        this.m_mysvc0.setName("MyService0 in Port0");
        this.m_port0.registerProvider("MyService0", "Generic", this.m_mysvc0);
        this.m_port0.registerConsumer("MyService1", "Generic", this.m_cons0);

        this.m_mysvc1.setName("MyService1 in Port1");
        this.m_port1.registerProvider("MyService1", "Generic", this.m_mysvc1);
        this.m_port1.registerConsumer("MyService0", "Generic", this.m_cons1);
*/
    }
    protected void tearDown() throws Exception {
        super.tearDown();
        this.m_orbRunner.shutdown();
    }

    /**
     * <p>ポートインタフェースプロファイル取得のテストです。<p>
     * <p>get_port_profile()メソッドによって取得されたPortProfileオブジェクトに含まれている
     * PortInterfaceProfileオブジェクトについて、期待値と一致することを確認します。<br />
     * 具体的には以下の点を確認します。
     * <ul>
     * <li>PortInterfaceProfileオブジェクトの数が期待値と一致すること。</li>
     * <li>インタフェースのインスタンス名が期待値と一致すること。</li>
     * <li>インタフェースのタイプ名が期待値と一致すること。</li>
     * <li>インタフェースの極性が期待値と一致すること。</li>
     * </ul>
     * </p>
     */
    public void test_ifprofile() {
/*
        // Port0
        PortProfile prof0 = this.m_port0ref.get_port_profile();
        assertEquals("port0", prof0.name);
        
        int len0 = prof0.interfaces.length;
        assertEquals(2, len0);
        
        PortInterfaceProfile[] iflist0 = prof0.interfaces;
        
        int idx0_0 = CORBA_SeqUtil.find(new PortInterfaceProfileListHolder(iflist0),
                new equalFunctor() {
                    public boolean equalof(final Object elem) {
                        PortInterfaceProfile piprof = (PortInterfaceProfile) elem;
                        return piprof.instance_name.equals("MyService0");
                    }
                });
        assertFalse(idx0_0 == -1);
        assertEquals("Generic", iflist0[idx0_0].type_name);
        assertEquals(PortInterfacePolarity.PROVIDED, iflist0[idx0_0].polarity);
        
        int idx0_1 = CORBA_SeqUtil.find(new PortInterfaceProfileListHolder(iflist0),
                new equalFunctor() {
                    public boolean equalof(final Object elem) {
                        PortInterfaceProfile piprof = (PortInterfaceProfile) elem;
                        return piprof.instance_name.equals("MyService1");
                    }
                });
        assertFalse(idx0_1 == -1);
        assertEquals("Generic", iflist0[idx0_1].type_name);
        assertEquals(PortInterfacePolarity.REQUIRED, iflist0[idx0_1].polarity);
        
        // Port1
        PortProfile prof1 = this.m_port1ref.get_port_profile();
        assertEquals("port1", prof1.name);

        int len1 = prof1.interfaces.length;
        assertEquals(2, len1);
        
        PortInterfaceProfile[] iflist1 = prof1.interfaces;
        
        int idx1_0 = CORBA_SeqUtil.find(new PortInterfaceProfileListHolder(iflist1),
                new equalFunctor() {
                    public boolean equalof(final Object factory) {
                        PortInterfaceProfile piprof = (PortInterfaceProfile) factory;
                        return piprof.instance_name.equals("MyService1");
                    }
                });
        assertFalse(idx1_0 == -1);
        assertEquals("Generic", iflist1[idx1_0].type_name);
        assertEquals(PortInterfacePolarity.PROVIDED, iflist1[idx1_0].polarity);
        
        int idx1_1 = CORBA_SeqUtil.find(new PortInterfaceProfileListHolder(iflist1),
                new equalFunctor() {
                    public boolean equalof(final Object factory) {
                        PortInterfaceProfile piprof = (PortInterfaceProfile) factory;
                        return piprof.instance_name.equals("MyService0");
                    }
                });
        assertFalse(idx1_1 == -1);
        assertEquals("Generic", iflist1[idx1_1].type_name);
        assertEquals(PortInterfacePolarity.REQUIRED, iflist1[idx1_1].polarity);
*/
    }

    /**
     * <p>ポート間の接続テストです。</p>
     * <p>次の点をテストします。
     * <ul>
     * <li>2つのポート間を接続します。</li>
     * <li>リモートの接続相手のサービスを呼び出します。</li>
     * </ul>
     * </p>
     */
    public void test_connect() {

System.out.println("test_connect()");
        MyService_impl pMyServiceImplA 
            = new MyService_impl();          // will be deleted automatically
        CorbaConsumer<MyService> pMyServiceConsumerB 
            = new CorbaConsumer<MyService>(MyService.class);
                                             // will be deleted automatically
        MyService_impl pMyServiceImplB 
            = new MyService_impl();          // will be deleted automatically
        CorbaConsumer<MyService> pMyServiceConsumerA 
            = new CorbaConsumer<MyService>(MyService.class);
                                             // will be deleted automatically 

        CorbaPort port0 = new CorbaPort("name of port0");
        CorbaPort port1 = new CorbaPort("name of port1");

        try {
            port0.registerProvider("MyServiceA", "Generic", pMyServiceImplA);
            port0.registerConsumer("MyServiceB", "Generic", pMyServiceConsumerB);
            
            port1.registerProvider("MyServiceB", "Generic", pMyServiceImplB);
            port1.registerConsumer("MyServiceA", "Generic", pMyServiceConsumerA);
        } catch (ServantAlreadyActive e) {
            e.printStackTrace();
            fail();
        } catch (WrongPolicy e) {
            e.printStackTrace();
            fail();
        } catch (ObjectNotActive e) {
            e.printStackTrace();
            fail();
        }
        
        // 接続プロファイルの準備
        ConnectorProfileHolder prof = new ConnectorProfileHolder(
                ConnectorProfileFactory.create());
        prof.value.connector_id = "";
        prof.value.name = "connector0";
        prof.value.ports = new PortService[2];
        prof.value.ports[0] = port0.getPortRef();
        prof.value.ports[1] = port1.getPortRef();

        // 接続する
        ReturnCode_t ret = port0.connect(prof);
        
        // 接続IDが割り当てられたことを確認する
        assertFalse("1:",prof.value.connector_id.equals(""));

        assertEquals("2:","MyService", pMyServiceImplA.name());
        assertEquals("3:","MyService", pMyServiceImplB.name());

        // ポート1のコンシューマ側からメソッドを呼び出すと、ポート0のプロバイダ側が意図どおり呼び出されるか？
        assertFalse("4:",pMyServiceImplA.is_hello_world_called());
        pMyServiceConsumerA._ptr().hello_world();
        assertTrue("5:",pMyServiceImplA.is_hello_world_called());
        
        // ポート0のコンシューマ側からメソッドを呼び出すと、ポート1のプロバイダ側が意図どおり呼び出されるか？
        assertFalse("6:",pMyServiceImplB.is_hello_world_called());
        pMyServiceConsumerB._ptr().hello_world();
        assertTrue("7:",pMyServiceImplB.is_hello_world_called());
    }

    /**
     * <p>ポート間接続の切断をテストします。</p>
     */
    public void test_disconnect() {
        
        MyService_impl pMyServiceImplA 
            = new MyService_impl();          // will be deleted automatically
        CorbaConsumer<MyService> pMyServiceConsumerB 
            = new CorbaConsumer<MyService>(MyService.class);
                                             // will be deleted automatically
        MyService_impl pMyServiceImplB 
            = new MyService_impl();          // will be deleted automatically
        CorbaConsumer<MyService> pMyServiceConsumerA 
            = new CorbaConsumer<MyService>(MyService.class);
                                             // will be deleted automatically 

        CorbaPort port0 = new CorbaPort("name of port0");
        CorbaPort port1 = new CorbaPort("name of port1");

        try {
            port0.registerProvider("MyServiceA", "Generic", pMyServiceImplA);
            port0.registerConsumer("MyServiceB", "Generic", pMyServiceConsumerB);
            
            port1.registerProvider("MyServiceB", "Generic", pMyServiceImplB);
            port1.registerConsumer("MyServiceA", "Generic", pMyServiceConsumerA);
        } catch (ServantAlreadyActive e) {
            e.printStackTrace();
            fail();
        } catch (WrongPolicy e) {
            e.printStackTrace();
            fail();
        } catch (ObjectNotActive e) {
            e.printStackTrace();
            fail();
        }

        ConnectorProfileHolder prof = new ConnectorProfileHolder(
                ConnectorProfileFactory.create());
        prof.value.connector_id = "";
        prof.value.name = "connector0";
        prof.value.ports = new PortService[2];
        prof.value.ports[0] = port0.getPortRef();
        prof.value.ports[1] = port1.getPortRef();
        
        // ポート間を接続して、接続できていることを確認する
        ReturnCode_t retval = port0.connect(prof);

        assertFalse("1:",pMyServiceImplA.is_hello_world_called());
        pMyServiceConsumerA._ptr().hello_world();
        assertTrue("2:",pMyServiceImplA.is_hello_world_called());

        // 接続を解除し、正しく切断できていることを確認する
        port0.disconnect(prof.value.connector_id);

      try
	{
	  assertTrue("3:",! pMyServiceImplB.is_hello_world_called());
	  pMyServiceConsumerB._ptr().hello_world();
							
	  fail("Couldn't catch no exceptions. Disconnection failed.");
	}
      catch(Exception ex)
	{
	  // Properly disconnected.
	}


    }
    /**
     */ 
    public void test_get_port_profile() {
        //
        MyService_impl pMyServiceImpl
            = new MyService_impl(); // will be deleted automatically
        CorbaConsumer<MyService> pMyServiceConsumer
            = new CorbaConsumer<MyService>(MyService.class); 
                                    // will be deleted automatically
			
        CorbaPort port = new CorbaPort("name of port");
        try {
            port.registerProvider("MyService (provided)", "Generic (provided)", pMyServiceImpl);
            port.registerConsumer("MyService (required)", "Generic (required)", pMyServiceConsumer);
        } catch (ServantAlreadyActive e) {
            e.printStackTrace();
            fail();
        } catch (WrongPolicy e) {
            e.printStackTrace();
            fail();
        } catch (ObjectNotActive e) {
            e.printStackTrace();
            fail();
        }
			
        RTC.PortService portRef = port.getPortRef();
        RTC.PortProfile profile = portRef.get_port_profile();

        //
        assertTrue(profile.name.equals("name of port"));
			
        //
        RTC.PortInterfaceProfile[] profiles = profile.interfaces;
        for (int i = 0; i < profile.interfaces.length; ++i) {
            if (profiles[i].instance_name.equals("MyService (provided)")) {
	        //
	        assertTrue(profiles[i].type_name.equals("Generic (provided)"));
					
	        //
  	        assertEquals(RTC.PortInterfacePolarity.PROVIDED, profiles[i].polarity);
            }
            else if (profiles[i].instance_name.equals("MyService (required)")) {
	        //
	        assertTrue(profiles[i].type_name.equals("Generic (required)"));

                assertEquals(RTC.PortInterfacePolarity.REQUIRED, profiles[i].polarity);
	    }
	    else {
	        //
	        String msg = "Unexpected instance_name:";
	        msg += profiles[i].instance_name;
	        fail(msg);
	    }
	}
    }
    /**
     */
    public void test_registerProvider() {
        MyService_impl pImpl0
	    = new MyService_impl();

        MyService_impl pImpl1
            = new MyService_impl();
			
			
        CorbaPortMock port0 = new CorbaPortMock("name of port");
        boolean ret;
        try {
            ret = port0.registerProvider("registerProvider0", "Generic", pImpl0);
            assertEquals(true,ret);

            //
            ret = port0.registerProvider("registerProvider0", "Generic", pImpl0);
            assertEquals(false,ret);

            //
            ret = port0.registerProvider("registerProvider0", "Generic", pImpl1);
            assertEquals(false,ret);
     
            //
            //
            ret = port0.registerProvider("registerProvider1", "Generic", pImpl0);
            assertEquals(true,ret);

            //
            ret = port0.registerProvider("registerProvider2", "Generic", pImpl1);
            assertEquals(true,ret);
        } catch (ServantAlreadyActive e) {
              e.printStackTrace();
              fail();
        } catch (WrongPolicy e) {
              e.printStackTrace();
              fail();
        } catch (ObjectNotActive e) {
              e.printStackTrace();
              fail();
        }

        port0.deactivateInterfaces_public();

    }
    public void test_activateInterfaces_deactivateInterfaces() {
System.out.println("test_activateInterfaces_deactivateInterfaces()");
        // Create port0.
        MyService_impl pMyServiceImplA
            = new MyService_impl(); // will be deleted automatically
        CorbaConsumer<MyService> pMyServiceConsumerB
	    = new CorbaConsumer<MyService>(MyService.class); 
                                    // will be deleted automatically
			
        CorbaPortMock port0 = new CorbaPortMock("name of port0");
        try {
            port0.registerProvider("MyServiceAA", "Generic", pMyServiceImplA);
            port0.registerConsumer("MyServiceBB", "Generic", pMyServiceConsumerB);
        } catch (ServantAlreadyActive e) {
              e.printStackTrace();
              fail();
        } catch (WrongPolicy e) {
              e.printStackTrace();
              fail();
        } catch (ObjectNotActive e) {
              e.printStackTrace();
              fail();
        }

        // Create port1.
        MyService_impl pMyServiceImplB
	    = new MyService_impl(); // will be deleted automatically
        CorbaConsumer<MyService> pMyServiceConsumerA
	    = new CorbaConsumer<MyService>(MyService.class); 
                                    // will be deleted automatically
			
        CorbaPortMock port1 = new CorbaPortMock("name of port1");
        try{
            port1.registerProvider("MyServiceBB", "Generic", pMyServiceImplB);
            port1.registerConsumer("MyServiceAA", "Generic", pMyServiceConsumerA);
        } catch (ServantAlreadyActive e) {
              e.printStackTrace();
              fail();
        } catch (WrongPolicy e) {
              e.printStackTrace();
              fail();
        } catch (ObjectNotActive e) {
              e.printStackTrace();
              fail();
        }
			
        //Create profile
        ConnectorProfileHolder connProfile = new ConnectorProfileHolder(
                ConnectorProfileFactory.create());
        connProfile.value.connector_id = "";
        connProfile.value.name = "name of connector profile";
        connProfile.value.ports = new PortService[2];
        connProfile.value.ports[0] = port0.getPortRef();
        connProfile.value.ports[1] = port1.getPortRef();

        //
        port0.connect(connProfile);

        //
        assertTrue(! pMyServiceImplA.is_hello_world_called());
        pMyServiceConsumerA._ptr().hello_world();
        assertTrue(pMyServiceImplA.is_hello_world_called());
        //
        port0.disconnect(connProfile.value.connector_id);

        //
        port0.deactivateInterfaces_public();
        port1.deactivateInterfaces_public();

        port0.connect(connProfile);

        //
        try {
            pMyServiceConsumerA._ptr().hello_world();
	    fail("Couldn't catch no exceptions. Disconnection failed.");
        }
        catch(Exception ex) {
        }

			
        ReturnCode_t ret;
        //
        ret = port0.disconnect(connProfile.value.connector_id);

        port0.activateInterfaces_public();
        port1.activateInterfaces_public();

        ret = port0.connect(connProfile);

        //
        pMyServiceConsumerB._ptr().hello_world();

        // 
        port0.disconnect(connProfile.value.connector_id);
    }
		
}
