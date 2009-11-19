package jp.go.aist.rtm.RTC.port;

import junit.framework.TestCase;

import java.util.Vector;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;

import org.omg.CORBA.ORB;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.PortableServer.POA;
import org.omg.CORBA.SystemException;

import jp.go.aist.rtm.RTC.InPortConsumerFactory;
import jp.go.aist.rtm.RTC.OutPortProviderFactory;
import jp.go.aist.rtm.RTC.PublisherBaseFactory;
import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.port.OutPortBase;
import jp.go.aist.rtm.RTC.port.publisher.PublisherBase;
import jp.go.aist.rtm.RTC.port.publisher.PublisherNew;
import jp.go.aist.rtm.RTC.port.publisher.PublisherPeriodic;
import jp.go.aist.rtm.RTC.port.publisher.PublisherFlush;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.ORBUtil;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.log.Logbuf;

import RTC.ReturnCode_t;

/**
 * <p>OutPortBaseクラスのためのテストケースです。</p>
 */
public class OutPortBaseTest extends TestCase {
    /**
     * * 
     *
     */
    class Logger {
        public void log(final String msg) {
            m_log.add(msg);
        }

        public int countLog(final String msg) {
            int count = 0;
            for (int i = 0; i < (int) m_log.size(); ++i) {
                if (m_log.elementAt(i).equals(msg)) {
                    ++count;
                }
            }
            return count;
        }
		
       private Vector<String> m_log = new Vector<String>();
    }
  /**
   * 
   * 
   *
   */
  class OutPortCorbaCdrProviderMock extends OutPortCorbaCdrProvider {

      public OutPortCorbaCdrProviderMock() {
          setInterfaceType("corba_cdr");
          m_logger = null;
       }
      /**
       *
       *
       */
      public void init(Properties prop)
      {
          if (m_logger != null)
          {
              m_logger.log("OutPortCorbaCdrProviderMock.init");
          }
      }
      /**
       *
       *
       */
      public OpenRTM.PortStatus get(OpenRTM.CdrDataHolder data) {
          return OpenRTM.PortStatus.PORT_OK;
      }
      /**
       *
       *
       */
      public ReturnCode put(final OutputStream data)
      {
          return ReturnCode.PORT_OK;
      }
      /**
       *
       *
       */
      public void publishInterfaceProfile(_SDOPackage.NVListHolder properties)
      {
          return;
      }

      /**
       *
       *
       */
      public boolean subscribeInterface(final _SDOPackage.NVListHolder properties)
      {
    
          return true;
      }
  
      /**
       *
       *
       */
      public void unsubscribeInterface(final _SDOPackage.NVListHolder properties) {
      }
      /**
       *
       *
       */
      public boolean publishInterface(_SDOPackage.NVListHolder prop)
      {
          return true;
      }
  
      /**
       *
       *
       */
      public void setLogger(Logger logger)
      {
          m_logger = logger;
      }
    private Logger m_logger;

  };
  /**
   * 
   * 
   *
   */
  class InPortCorbaCdrConsumerMock extends InPortCorbaCdrConsumer {

      public InPortCorbaCdrConsumerMock()
       {
          m_logger = null;
       }
      /**
       *
       *
       */
      public void init(Properties prop)
      {
          if (m_logger != null)
          {
              m_logger.log("InPortCorbaCdrConsumerMock.init");
          }
      }
      /**
       *
       *
       */
      public OpenRTM.PortStatus put(byte[] data)
      throws SystemException {
          return OpenRTM.PortStatus.PORT_OK;
      }
      /**
       *
       *
       */
      public void publishInterfaceProfile(_SDOPackage.NVListHolder properties)
      {
          return;
      }

      /**
       *
       *
       */
      public boolean subscribeInterface(final _SDOPackage.NVListHolder properties)
      {
    
          return true;
      }
  
      /**
       *
       *
       */
      public void unsubscribeInterface(final _SDOPackage.NVListHolder properties)
      {
          return;
      }
  
  
      /**
       *
       *
       */
      public void setLogger(Logger logger)
      {
          m_logger = logger;
      }
    private Logger m_logger;

  };

    /**
     *
   *
   *
   */
  class OutPortBaseMock extends OutPortBase {
        /**
         * 
         * 
         */
        public OutPortBaseMock(final String name, final String data_type) {
          super(name , data_type);
        }
        /**
         * 
         */
        public Properties get_m_properties() {
            return m_properties;
        }
        /**
         * 
         */
        public OutPortProvider createProvider_public(RTC.ConnectorProfileHolder cprof,
                                        Properties prop) {
            return createProvider(cprof, prop);
        }
        /**
         * 
         */
        public InPortConsumer createCondumer_public(RTC.ConnectorProfileHolder cprof,
                                        Properties prop) {
            return createConsumer(cprof, prop);
        }
        /**
         * 
         */
        public OutPortConnector createConnector_public(final RTC.ConnectorProfileHolder cprof,
                                      Properties prop,
                                      OutPortProvider provider) {
            return createConnector(cprof,prop,provider);
        }
        /**
         * 
         */
        public ReturnCode_t
        publishInterfaces_public(RTC.ConnectorProfileHolder connector_profile) {
            return publishInterfaces(connector_profile);
         } 
        /**
         * 
         */
        public ReturnCode_t
        subscribeInterfaces_public(RTC.ConnectorProfileHolder connector_profile) {
            return subscribeInterfaces(connector_profile);
        } 
        /**
         * 
         * 
         */
        public Vector<String> get_m_consumerTypes() {
          return m_consumerTypes;
        }
        /**
         * 
         * 
         */
        public Vector<String> get_m_providerTypes() {
            return m_providerTypes;
        }
        /**
         * 
         */
        public void initConsumers_public() {
            initConsumers();
        }
        /**
         * 
         */
        public void initProviders_public() {
            initProviders();
        }
        /**
         * 
         * 
         */
        public Vector<OutPortConnector> get_m_connectors() {
            return m_connectors;
        }
     
    }

    class PublisherA extends PublisherBase {

        public DataRef<String> m_str;
        
        public PublisherA(DataRef<String> str) {
            this.m_str = str;
        }
        public void update() {
            this.m_str.v += "A";
        }
        public ReturnCode init(Properties prop) {
            return ReturnCode.PORT_OK;
        }
        public ReturnCode setConsumer(InPortConsumer consumer) {
            return ReturnCode.PORT_OK;
        }
        public ReturnCode setBuffer(BufferBase<OutputStream> buffer) {
            return ReturnCode.PORT_OK;
        }
        public ReturnCode write(final OutputStream data, int sec, int usec) {
            return ReturnCode.PORT_OK;
        }
        public ReturnCode write(final OutputStream data) {
            return ReturnCode.PORT_OK;
        }
        public boolean isActive() {
            return true;
        }
        public ReturnCode activate() {
            return ReturnCode.PORT_OK;
        }
        public ReturnCode deactivate() {
            return ReturnCode.PORT_OK;
        }
        public String getName(){
            return "A";
        }
    }

    class PublisherB extends PublisherBase {

        public DataRef<String> m_str;
        
        public PublisherB(DataRef<String> str) {
            this.m_str = str;
        }
        public void update() {
            this.m_str.v += "B";
        }
        public ReturnCode init(Properties prop) {
            return ReturnCode.PORT_OK;
        }
        public ReturnCode setConsumer(InPortConsumer consumer) {
            return ReturnCode.PORT_OK;
        }
        public ReturnCode setBuffer(BufferBase<OutputStream> buffer) {
            return ReturnCode.PORT_OK;
        }
        public ReturnCode write(final OutputStream data, int sec, int usec) {
            return ReturnCode.PORT_OK;
        }
        public ReturnCode write(final OutputStream data) {
            return ReturnCode.PORT_OK;
        }
        public boolean isActive() {
            return true;
        }
        public ReturnCode activate() {
            return ReturnCode.PORT_OK;
        }
        public ReturnCode deactivate() {
            return ReturnCode.PORT_OK;
        }
        public String getName(){
            return "B";
        }
    }

    class PublisherC extends PublisherBase {

        public DataRef<String> m_str;
        
        public PublisherC(DataRef<String> str) {
            this.m_str = str;
        }
        public void update() {
            this.m_str.v += "C";
        }
        public ReturnCode init(Properties prop) {
            return ReturnCode.PORT_OK;
        }
        public ReturnCode setConsumer(InPortConsumer consumer) {
            return ReturnCode.PORT_OK;
        }
        public ReturnCode setBuffer(BufferBase<OutputStream> buffer) {
            return ReturnCode.PORT_OK;
        }
        public ReturnCode write(final OutputStream data, int sec, int usec) {
            return ReturnCode.PORT_OK;
        }
        public ReturnCode write(final OutputStream data) {
            return ReturnCode.PORT_OK;
        }
        public boolean isActive() {
            return true;
        }
        public ReturnCode activate() {
            return ReturnCode.PORT_OK;
        }
        public ReturnCode deactivate() {
            return ReturnCode.PORT_OK;
        }
        public String getName(){
            return "C";
        }
    }

    class PublisherD extends PublisherBase {

        public DataRef<String> m_str;
        
        public PublisherD(DataRef<String> str) {
            this.m_str = str;
        }
        public void update() {
            this.m_str.v += "D";
        }
        public ReturnCode init(Properties prop) {
            return ReturnCode.PORT_OK;
        }
        public ReturnCode setConsumer(InPortConsumer consumer) {
            return ReturnCode.PORT_OK;
        }
        public ReturnCode setBuffer(BufferBase<OutputStream> buffer) {
            return ReturnCode.PORT_OK;
        }
        public ReturnCode write(final OutputStream data, int sec, int usec) {
            return ReturnCode.PORT_OK;
        }
        public ReturnCode write(final OutputStream data) {
            return ReturnCode.PORT_OK;
        }
        public boolean isActive() {
            return true;
        }
        public ReturnCode activate() {
            return ReturnCode.PORT_OK;
        }
        public ReturnCode deactivate() {
            return ReturnCode.PORT_OK;
        }
        public String getName(){
            return "D";
        }
    }

    String outport_name = "MyOutPort";

    class OutPortMock extends OutPortBase {
        
        public OutPortMock(final String name) {
//            super(name);
            super(name,"dummy");
        }
    }
    private PublisherA m_pubA;
    private PublisherB m_pubB;
    private PublisherC m_pubC;
    private PublisherD m_pubD;
    private OutPortMock m_outport;
    private DataRef<String> m_str;

    public OutPortBaseTest(String name ) {
        super(name);
        
        this.m_str = new DataRef<String>("");
        this.m_pubA = new PublisherA(this.m_str);
        this.m_pubB = new PublisherB(this.m_str);
        this.m_pubC = new PublisherC(this.m_str);
        this.m_pubD = new PublisherD(this.m_str);
        this.m_outport = new OutPortMock(this.outport_name);
    }

    private ORB m_orb;
    private POA m_poa;
    protected static Logbuf rtcout = null;;
    protected ConsoleHandler m_stdout;
    protected FileHandler m_fh;
    protected void setUp() throws Exception {
        super.setUp();
        java.util.Properties props = new java.util.Properties();
        this.m_orb = ORBUtil.getOrb();
        this.m_poa = org.omg.PortableServer.POAHelper.narrow(
                this.m_orb.resolve_initial_references("RootPOA"));
        this.m_poa.the_POAManager().activate();

        if (rtcout == null){
            rtcout = new Logbuf("Manager");
            m_fh = null; 
            rtcout.setLevel("SILENT");
            String logfile = "OutPortBaseTest.log";
            m_fh = new FileHandler(logfile);
            rtcout.addStream(m_fh);
            rtcout.setLevel("PARANOID");

        }
        final InPortConsumerFactory<InPortConsumer,String> factory0
            = InPortConsumerFactory.instance();

        factory0.addFactory("corba_cdr",
                    new InPortCorbaCdrConsumerMock(),
                    new InPortCorbaCdrConsumerMock());

        final OutPortProviderFactory<OutPortProvider,String> factory1 
            = OutPortProviderFactory.instance();

        factory1.addFactory("corba_cdr",
                    new OutPortCorbaCdrProvider(),
                    new OutPortCorbaCdrProvider());

        final PublisherBaseFactory<PublisherBase,String> factory2
            = PublisherBaseFactory.instance();

        factory2.addFactory("new",
                    new PublisherNew(),
                    new PublisherNew());

        final PublisherBaseFactory<PublisherBase,String> factory3 
            = PublisherBaseFactory.instance();

        factory3.addFactory("periodic",
                    new PublisherPeriodic(),
                    new PublisherPeriodic());

        final PublisherBaseFactory<PublisherBase,String> factory4 
            = PublisherBaseFactory.instance();

        factory4.addFactory("flush",
                    new PublisherFlush(),
                    new PublisherFlush());
    }
    
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>ポート名取得のテストです。</p>
     * <p>name()メソッドによりポート名を正しく取得できることをテストします。</p>
     */
    public void test_name() {
        RTC.TimedDouble data = new RTC.TimedDouble();
        OutPortBaseMock outPort = new OutPortBaseMock("Hello, World!", "TimedLong");

        PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
        portAdmin.registerPort(outPort); 

        assertTrue(outPort.name().equals("Hello, World!") );
        portAdmin.deletePort(outPort);
    }

    /**
     * <p>Publisher登録のテストです。</p>
     * <p>attach()メソッドを用いて複数のPublisherを順番に登録した後にupdate()を呼び出し、
     * 登録されている各Publisherが登録順にコールバックされることを確認します。</p>
     */
    public void test_attach() {
/*
        this.m_outport.attach("A", this.m_pubA);
        this.m_outport.attach("B", this.m_pubB);
        this.m_outport.attach("C", this.m_pubC);
        this.m_outport.attach("D", this.m_pubD);
//        this.m_outport.update();
        
        assertEquals(this.m_str, "ABCD");
*/
    }

    /**
     * <p>Publisher登録のテストです。</p>
     * <p>attach_back()メソッドを用いて複数のPublisherを順番に登録した後にupdate()を呼び出し、
     * 登録されている各Publisherが登録順にコールバックされることを確認します。</p>
     */
/*
    public void test_attach_back() {
        this.m_outport.attach_back("A", this.m_pubA);
        this.m_outport.attach_back("B", this.m_pubB);
        this.m_outport.attach_back("C", this.m_pubC);
        this.m_outport.attach_back("D", this.m_pubD);
//        this.m_outport.update();

        assertEquals(this.m_str, "ABCD");
    }
*/

    /**
     * <p>Publisher登録のテストです。</p>
     * <p>attach_front()メソッドを用いて複数のPublisherを順番に登録した後にupdate()を呼び出し、
     * 登録されている各Publisherが登録順の逆順にコールバックされることを確認します。</p>
     */
/*
    public void test_attach_front() {
        this.m_outport.attach_front("A", this.m_pubA);
        this.m_outport.attach_front("B", this.m_pubB);
        this.m_outport.attach_front("C", this.m_pubC);
        this.m_outport.attach_front("D", this.m_pubD);
//        this.m_outport.update();

        assertEquals(this.m_str, "DCBA");
    }
*/
    
    /**
     * <p>Publisher登録のテストです。</p>
     * <p>attach_back()メソッドとattach_front()メソッドを用いて複数のPublisherを登録した後に
     * update()を呼び出し、登録されている各Publisherが意図どおりの順にコールバックされることを確認します。</p>
     */
/*
    public void test_attach_mix() {
        this.m_outport.attach_back ("A", this.m_pubA); // A
        this.m_outport.attach_back ("B", this.m_pubB); // AB
        this.m_outport.attach_front("C", this.m_pubC); // CAB
        this.m_outport.attach_front("D", this.m_pubD); // DCAB
//        this.m_outport.update();

        assertEquals(this.m_str, "DCAB");
    }
*/

    /**
     * <p>Publisher登録解除のテストです。</p>
     * <p>はじめに複数のPublisherを登録します。その後、１つずつ登録解除していき、
     * 意図どおりに指定したPublihserが登録解除されていることを確認します。</p>
     */
/*
    public void test_detach() {
        
        this.m_outport.attach("A", m_pubA);
        this.m_outport.attach("B", m_pubB);
        this.m_outport.attach("C", m_pubC);
        this.m_outport.attach("D", m_pubD);
        
//        this.m_outport.update();
        assertEquals(this.m_str, "ABCD");
        
        this.m_str.v = "";
        this.m_outport.detach("A");
//        this.m_outport.update();
        
        assertEquals(this.m_str, "BCD");
        
        this.m_str.v = "";
        this.m_outport.detach("B");
//        this.m_outport.update();
        
        assertEquals(this.m_str, "CD");
        
        this.m_str.v = "";
        this.m_outport.detach("D");
//        this.m_outport.update();
        
        assertEquals(this.m_str, "C");

        this.m_str.v = "";
        this.m_outport.detach("C");
//        this.m_outport.update();
        
        assertEquals(this.m_str, "");
    }
*/
    /**
     * <p>constructor</p>
     * 
     */
    public void test_constructor()
    {


        // "subscription_type" is deleted.
        if( PublisherBaseFactory.instance().hasFactory("new") )
        {
            PublisherBaseFactory.instance().removeFactory("new");
        }
        if( PublisherBaseFactory.instance().hasFactory("periodic") )
        {
            PublisherBaseFactory.instance().removeFactory("periodic");
        }
        if( PublisherBaseFactory.instance().hasFactory("flush") )
        {
            PublisherBaseFactory.instance().removeFactory("flush");
        }

        {
            OutPortBaseMock outport = new OutPortBaseMock("OutPortBaseTest", 
                                    "TimedFloat");

            PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
            portAdmin.registerPort(outport); 
        
            RTC.PortProfile profile = outport.getPortProfile();
            _SDOPackage.NVListHolder holder = new _SDOPackage.NVListHolder(profile.properties);
            Properties prop = NVUtil.toProperties(holder);
            profile.properties = holder.value;
rtcout.println(rtcout.TRACE, "---010---");
            assertTrue(prop.getProperty("port.port_type").equals("DataOutPort"));
            assertTrue(prop.getProperty("dataport.data_type").equals("TimedFloat"));
            assertTrue(prop.getProperty("dataport.subscription_type").equals("")); 
            portAdmin.deletePort(outport);
        }
        final PublisherBaseFactory<PublisherBase,String> factory4 
            = PublisherBaseFactory.instance();

        factory4.addFactory("flush",
                    new PublisherFlush(),
                    new PublisherFlush());
        {
            OutPortBaseMock outport = new OutPortBaseMock("OutPortBaseTest", 
                                    "TimedFloat");

            PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
            portAdmin.registerPort(outport); 
        
            RTC.PortProfile profile = outport.getPortProfile();
            _SDOPackage.NVListHolder holder = new _SDOPackage.NVListHolder(profile.properties);
            Properties prop = NVUtil.toProperties(holder);
            profile.properties = holder.value;
            assertTrue(prop.getProperty("port.port_type").equals("DataOutPort"));
            assertTrue(prop.getProperty("dataport.data_type").equals("TimedFloat"));
            assertTrue(prop.getProperty("dataport.subscription_type").equals("flush")); 
            portAdmin.deletePort(outport);
        }

        final PublisherBaseFactory<PublisherBase,String> factory2
            = PublisherBaseFactory.instance();

        factory2.addFactory("new",
                    new PublisherNew(),
                    new PublisherNew());
        {
            OutPortBaseMock outport = new OutPortBaseMock("OutPortBaseTest", 
                                    "TimedFloat");

            PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
            portAdmin.registerPort(outport); 
        
            RTC.PortProfile profile = outport.getPortProfile();
            _SDOPackage.NVListHolder holder = new _SDOPackage.NVListHolder(profile.properties);
            Properties prop = NVUtil.toProperties(holder);
            profile.properties = holder.value;
            assertTrue(prop.getProperty("port.port_type").equals("DataOutPort"));
            assertTrue(prop.getProperty("dataport.data_type").equals("TimedFloat"));
//            assertTrue(prop.getProperty("dataport.subscription_type").equals("new,flush")); 
            String str = prop.getProperty("dataport.subscription_type"); 
            assertTrue(str.equals("new, flush")); 

            portAdmin.deletePort(outport);
        }
        final PublisherBaseFactory<PublisherBase,String> factory3 
            = PublisherBaseFactory.instance();

        factory3.addFactory("periodic",
                    new PublisherPeriodic(),
                    new PublisherPeriodic());
        {
            OutPortBaseMock outport = new OutPortBaseMock("OutPortBaseTest", 
                                    "TimedFloat");

            PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
            portAdmin.registerPort(outport); 
        
            RTC.PortProfile profile = outport.getPortProfile();
            _SDOPackage.NVListHolder holder = new _SDOPackage.NVListHolder(profile.properties);
            Properties prop = NVUtil.toProperties(holder);
            profile.properties = holder.value;
            assertTrue(prop.getProperty("port.port_type").equals("DataOutPort"));
            assertTrue(prop.getProperty("dataport.data_type").equals("TimedFloat"));
            String str = prop.getProperty("dataport.subscription_type");
            assertTrue(str.equals("new, flush, periodic")); 
//            assertTrue(prop.getProperty("dataport.subscription_type").equals("periodic, new, flush")); 

            portAdmin.deletePort(outport);
        }
    }
    /**
     * <p> initConsumers() </p>
     * 
     */
    public void test_initConsumers() {

        rtcout.println(rtcout.DEBUG, "IN  test_initConsumers()");

        if( InPortConsumerFactory.instance().hasFactory("corba_cdr") ) {
            InPortConsumerFactory.instance().removeFactory("corba_cdr");
        }

        if( OutPortProviderFactory.instance().hasFactory("corba_cdr") ) {
            OutPortProviderFactory.instance().removeFactory("corba_cdr");
        }

        OutPortBaseMock outport = new OutPortBaseMock("OutPortBaseTest",
                                                      "TimedFloat");

        PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
        portAdmin.registerPort(outport); 

        //InPortCorbaCdrConsumerMock is registered in "corba_cdr". 
        InPortConsumerFactory.instance().
        addFactory("corba_cdr",
                    new InPortCorbaCdrConsumerMock(),
                    new InPortCorbaCdrConsumerMock());
        //OutPortCorbaCdrProviderMock is registered in "corba_cdr". 
        OutPortProviderFactory.instance().
        addFactory("corba_cdr",
                    new OutPortCorbaCdrProviderMock(),
                    new OutPortCorbaCdrProviderMock());

        RTC.PortProfile profile = outport.getPortProfile();
        _SDOPackage.NVListHolder holder = new _SDOPackage.NVListHolder(profile.properties);
        Properties prop = NVUtil.toProperties(holder);
        profile.properties = holder.value;
	String str = prop.getProperty("dataport.dataflow_type");
rtcout.println(rtcout.DEBUG, "    ---010---");
        assertTrue(str.equals(""));
	str = prop.getProperty("dataport.interface_type");
rtcout.println(rtcout.DEBUG, "    ---020---");
        assertTrue(str.equals(""));

        Vector<String> cstr = outport.get_m_consumerTypes();
        assertEquals(0, cstr.size());

        outport.initConsumers_public();

        profile = outport.getPortProfile();
        holder = new _SDOPackage.NVListHolder(profile.properties);
        prop = NVUtil.toProperties(holder);
        profile.properties = holder.value;

        //The following were added to properties of getProtProfile.
	str = prop.getProperty("dataport.dataflow_type");
rtcout.println(rtcout.DEBUG, "    ---030---"+str);
        assertTrue(str.equals("push"));
	str = prop.getProperty("dataport.interface_type");
rtcout.println(rtcout.DEBUG, "    ---040---"+str);
        assertTrue(str.equals("corba_cdr"));
 
        //ProviderTypes,ConsumerTypes
        cstr = outport.get_m_consumerTypes();
rtcout.println(rtcout.DEBUG, "    ---050---");
        assertTrue(0!= cstr.size());
rtcout.println(rtcout.DEBUG, "    ---060---");
        assertTrue(cstr.elementAt(0).equals("corba_cdr"));

        portAdmin.deletePort(outport);

        rtcout.println(rtcout.DEBUG, "OUT test_initConsumers()");
    }

    /**
     * <p> initConsumers </p>
     * 
     */
    public void test_initConsumers2()
    {
     
        if( InPortConsumerFactory.instance().hasFactory("corba_cdr") )
        {
            InPortConsumerFactory.instance().removeFactory("corba_cdr");
        }

    
        if( OutPortProviderFactory.instance().hasFactory("corba_cdr") )
        {
            OutPortProviderFactory.instance().removeFactory("corba_cdr");
        }

        OutPortBaseMock outport = new OutPortBaseMock("OutPortBaseTest", 
                                "RTC.TimedFloat");

        PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
        portAdmin.registerPort(outport); 

        //OutPortCorbaCdrProviderMock is registered in "corba_cdr".
        OutPortProviderFactory.instance().
        addFactory("corba_cdr",
                    new OutPortCorbaCdrProviderMock(),
                    new OutPortCorbaCdrProviderMock());

        RTC.PortProfile profile = outport.getPortProfile();
        _SDOPackage.NVListHolder holder = new _SDOPackage.NVListHolder(profile.properties);
        Properties prop = NVUtil.toProperties(holder);
        profile.properties = holder.value;
        String str = prop.getProperty("dataport.dataflow_type");
        assertTrue(str.equals(""));
        str = prop.getProperty("dataport.interface_type");
        assertTrue(str.equals(""));

        Vector<String> cstr = outport.get_m_consumerTypes();
        assertEquals(0, cstr.size());

        outport.initConsumers_public();

        profile = outport.getPortProfile();
        holder = new _SDOPackage.NVListHolder(profile.properties);
        prop = NVUtil.toProperties(holder);
        profile.properties = holder.value;

        //The following were added to properties of getProtProfile.
        str = prop.getProperty("dataport.dataflow_type");
        assertTrue(str.equals(""));
        str = prop.getProperty("dataport.interface_type");
        assertTrue(str.equals(""));
 
        //ProviderTypes,ConsumerTypes
        cstr = outport.get_m_consumerTypes();
        assertTrue(0== cstr.size());

        portAdmin.deletePort(outport);
    }
    /**
     * <p> initProviders() </p>
     * 
     */
    public void test_initProviders()
    {
     
        if( InPortConsumerFactory.instance().hasFactory("corba_cdr") )
        {
            InPortConsumerFactory.instance().removeFactory("corba_cdr");
        }

    
        if( OutPortProviderFactory.instance().hasFactory("corba_cdr") )
        {
            OutPortProviderFactory.instance().removeFactory("corba_cdr");
        }

        OutPortBaseMock outport = new OutPortBaseMock("OutPortBaseTest", 
                                "TimedFloat");

        PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
        portAdmin.registerPort(outport); 

        //InPortCorbaCdrConsumer is registered in "corba_cdr".
        InPortConsumerFactory.instance().
        addFactory("corba_cdr",
                    new InPortCorbaCdrConsumerMock(),
                    new InPortCorbaCdrConsumerMock());
        //OutPortCorbaCdrProviderMock is registredd in "corba_cdr".
        OutPortProviderFactory.instance().
        addFactory("corba_cdr",
                    new OutPortCorbaCdrProviderMock(),
                    new OutPortCorbaCdrProviderMock());

        RTC.PortProfile profile = outport.getPortProfile();
        _SDOPackage.NVListHolder holder = new _SDOPackage.NVListHolder(profile.properties);
        Properties prop = NVUtil.toProperties(holder);
        profile.properties = holder.value;
        String str = prop.getProperty("dataport.dataflow_type");
        assertTrue(str.equals(""));
        str = prop.getProperty("dataport.interface_type");
        assertTrue(str.equals(""));

        Vector<String> cstr = outport.get_m_providerTypes();
        assertEquals(0, cstr.size());

        outport.initProviders_public();

        profile = outport.getPortProfile();
        holder = new _SDOPackage.NVListHolder(profile.properties);
        prop = NVUtil.toProperties(holder);
        profile.properties = holder.value;

        //The following are added to properties of getPortProfile.
        str = prop.getProperty("dataport.dataflow_type");
        assertTrue(str.equals("push"));
        str = prop.getProperty("dataport.interface_type");
        assertTrue(str.equals("corba_cdr"));
 
        //ProviderTypes,ConsumerTypes
        cstr = outport.get_m_providerTypes();
        assertTrue(0!= cstr.size());
        assertTrue(cstr.elementAt(0).equals("corba_cdr"));

        portAdmin.deletePort(outport);
    }
    /**
     * <p> initProviders() </p>
     * 
     */
    public void test_initProviders2()
    {
     
        if( InPortConsumerFactory.instance().hasFactory("corba_cdr") )
        {
            InPortConsumerFactory.instance().removeFactory("corba_cdr");
        }

    
        if( OutPortProviderFactory.instance().hasFactory("corba_cdr") )
        {
            OutPortProviderFactory.instance().removeFactory("corba_cdr");
        }

        OutPortBaseMock outport = new OutPortBaseMock("OutPortBaseTest", 
                                "TimedFloat");

        PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
        portAdmin.registerPort(outport); 

        //InPortCorbaCdrConsumerMock is registred in "corba_cdr".
        InPortConsumerFactory.instance().
        addFactory("corba_cdr",
                    new InPortCorbaCdrConsumerMock(),
                    new InPortCorbaCdrConsumerMock());

        RTC.PortProfile profile = outport.getPortProfile();
        _SDOPackage.NVListHolder holder = new _SDOPackage.NVListHolder(profile.properties);
        Properties prop = NVUtil.toProperties(holder);
        profile.properties = holder.value;
        String str = prop.getProperty("dataport.dataflow_type");
        assertTrue(str.equals(""));
        str = prop.getProperty("dataport.interface_type");
        assertTrue(str.equals(""));

        Vector<String> cstr = outport.get_m_providerTypes();
        assertEquals(0, cstr.size());

        outport.initProviders_public();

        profile = outport.getPortProfile();
        holder = new _SDOPackage.NVListHolder(profile.properties);
        prop = NVUtil.toProperties(holder);
        profile.properties = holder.value;

        //Following are added to porpeties of getPortProfile.
        str = prop.getProperty("dataport.dataflow_type");
        assertTrue(str.equals(""));
        str = prop.getProperty("dataport.interface_type");
        assertTrue(str.equals(""));
 
        //ProviderTypes,ConsumerTypes
        cstr = outport.get_m_providerTypes();
        assertTrue(0== cstr.size());

        portAdmin.deletePort(outport);
    }
    /**
     * <p> init() properties() </p>
     * 
     */
    public void test_init_properties()
    {
        OutPortBaseMock outPort = new OutPortBaseMock("OutPortBaseTest", 
                                "TimedDouble");

        Properties prop = new Properties();
        prop.setProperty("dataport.interface_type","corba_cdr");
        prop.setProperty("dataport.dataflow_type", "pull");
        prop.setProperty("dataport.subscription_type","new");

        PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
        portAdmin.registerPort(outPort); 

        outPort.init(prop);

        Properties prop2 = outPort.get_m_properties();
        assertEquals(prop.size(), prop2.size());
          
        String str = prop.getProperty("dataport.interface_type");
        assertTrue(str.equals(prop.getProperty("dataport.interface_type")));
        str = prop.getProperty("dataport.dataflow_type");
        assertTrue(str.equals(prop.getProperty("dataport.dataflow_type")));
        str = prop.getProperty("dataport.subscription_type");
        assertTrue(str.equals(prop.getProperty("dataport.subscription_type")));

        prop2 = outPort.properties();
        assertEquals(prop.size(), prop2.size());
          
        str = prop.getProperty("dataport.interface_type");
        assertTrue(str.equals(prop.getProperty("dataport.interface_type")));
        str = prop.getProperty("dataport.dataflow_type");
        assertTrue(str.equals(prop.getProperty("dataport.dataflow_type")));
        str = prop.getProperty("dataport.subscription_type");
        assertTrue(str.equals(prop.getProperty("dataport.subscription_type")));

        portAdmin.deletePort(outPort);
    }
}
