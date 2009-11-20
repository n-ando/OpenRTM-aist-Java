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

import jp.go.aist.rtm.RTC.BufferFactory;
import jp.go.aist.rtm.RTC.ObjectCreator;
import jp.go.aist.rtm.RTC.ObjectDestructor;
import jp.go.aist.rtm.RTC.InPortConsumerFactory;
import jp.go.aist.rtm.RTC.InPortProviderFactory;
import jp.go.aist.rtm.RTC.OutPortProviderFactory;
import jp.go.aist.rtm.RTC.OutPortConsumerFactory;
import jp.go.aist.rtm.RTC.PublisherBaseFactory;
import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.buffer.RingBuffer;
import jp.go.aist.rtm.RTC.buffer.CdrRingBuffer;
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
import RTC.ConnectorProfileHolder;
import _SDOPackage.NVListHolder;


public class InPortBaseTest extends TestCase {

  /**
   * 
   * 
   *
   */
  class InPortCorbaCdrProviderMock extends InPortCorbaCdrProvider {

      public InPortCorbaCdrProviderMock() {
          m_logger = null;
       }
      /**
       *
       *
       */
      public void setBuffer(BufferBase<OutputStream> buffer) {
          if (m_logger != null) {
              m_logger.log("InPortCorbaCdrProviderMock.setBuffer");
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
      public void init(Properties prop) {
          if (m_logger != null) {
              m_logger.log("InPortCorbaCdrProviderMock.init");
          }
      }
      /**
       *
       *
       */
      public ReturnCode put(final OutputStream data) {
          return ReturnCode.PORT_OK;
      }
      /**
       *
       *
       */
      public void publishInterfaceProfile(_SDOPackage.NVListHolder properties) {
          return;
      }

      /**
       *
       *
       */
/*
      public boolean subscribeInterface(fianl _SDOPackage.NVListHolder properties) {
    
          return true;
      }
*/  
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
      public void setLogger(Logger logger) {
          m_logger = logger;
      }
    private Logger m_logger;

  };
  /**
   * 
   * 
   *
   */
  public class OutPortCorbaCdrConsumerMock extends CorbaConsumer< OpenRTM.OutPortCdr> implements OutPortConsumer, ObjectCreator<OutPortConsumer>, ObjectDestructor {

  
    /**
     * <p> creator_ </p>
     * 
     * @return Object Created instances
     *
     */
    public OutPortConsumer creator_() {
        return new OutPortCorbaCdrConsumerMock();
    }
    /**
     * <p> destructor_ </p>
     * 
     * @param obj    The target instances for destruction
     *
     */
    public void destructor_(java.lang.Object obj) {
        obj = null;
    }
      public OutPortCorbaCdrConsumerMock() {
        super(OpenRTM.OutPortCdr.class);
          m_logger = null;
      }
      /**
       *
       *
       */
      public void setBuffer(BufferBase<OutputStream> buffer) {
          if (m_logger != null) {
              m_logger.log("OutPortCorbaCdrConsumerMock.setBuffer");
          }
      }
      /**
       *
       *
       */
/*
      .OpenRTM.PortStatus put(const .OpenRTM.CdrData& data)
         throw (CORBA.SystemException)
      {
          return .OpenRTM.PORT_OK;
      }
*/
      /*!
       *
       *
       */
      public void init(Properties prop) {
          if (m_logger != null)
          {
              m_logger.log("OutPortCorbaCdrConsumerMock.init");
          }
      }
      /**
       *
       *
       */
/*
      OutPortConsumer.ReturnCode put(const cdrMemoryStream& data)
      {
          return PORT_OK;
      }
*/
      /**
       *
       *
       */
/*
      void publishInterfaceProfile(SDOPackage.NVList& properties)
      {
          return;
      }
*/
      /*!
       *
       *
       */
      public boolean subscribeInterface(final NVListHolder properties) {
    
          return true;
      }
  
      /**
       *
       *
       */
      public void unsubscribeInterface(final NVListHolder properties) {
      }
      /**
       *
       *
       */
      public ReturnCode get(OutputStream data) {
          return ReturnCode.PORT_OK;
      }
  
  
      /*!
       *
       *
       */
      public void setLogger(Logger logger) {
          m_logger = logger;
      }
    private Logger m_logger;
  };
  /**
   * 
   * 
   */
  public class InPortBaseMock extends InPortBase {
      /**
       * 
       * 
       */
      public InPortBaseMock(final String name, final String data_type) {
          super(name, data_type);
          rtcout.setLevel("PARANOID");
      }
      /**
       * 
       * 
       */
      BufferBase getThebuffer()
      {
          return m_thebuffer;
      }
      /**
       * 
       * 
       */
      Vector<String> getProviderTypes()
      {
          return m_providerTypes;
      }
      /**
       * 
       * 
       */
      Vector<String> get_m_consumerTypes()
      {
          return m_consumerTypes;
      }
      /**
       * 
       * 
       */
      public void initConsumers_public() {
          initConsumers();
      }
      /**
       * 
       * 
       */
      public void initProviders_public() {
          initProviders();
      }
      /**
       * 
       * 
       */
      public void setSinglebuffer() {
          m_singlebuffer = true;
      }
      /*!
       * 
       * 
       */
      public void setMultibuffer() {
          m_singlebuffer = false;
      }
      /**
       * 
       * 
       */
      Vector<InPortConnector> get_m_connectors() {
          return m_connectors;
      }
      /*!
       * 
       * 
       */
       public ReturnCode_t publishInterfaces_public(ConnectorProfileHolder cprof) {
          ReturnCode_t ret = publishInterfaces(cprof);
          return ret;
      }
      /**
       * 
       * 
       */
      public ReturnCode_t subscribeInterfaces_public(final ConnectorProfileHolder cprof) {
          ReturnCode_t ret = subscribeInterfaces(cprof);
          return ret;
      }
      /**
       * 
       * 
       */
      protected InPortConnector
      createConnector_public(ConnectorProfileHolder cprof, Properties prop,
                    InPortProvider provider) {
         return createConnector(cprof, prop, provider);
      }
      /**
       * 
       * 
       */
      protected InPortProvider
      createProvider_public(ConnectorProfileHolder cprof, Properties prop) {
          return createProvider(cprof, prop);
      }
      /**
       * 
       * 
       */
/*
      coil.Properties get_m_properties()
      {
          return m_properties;
     }
*/
  };
    public static Logger m_mock_logger = null;
    private ORB m_orb;
    private POA m_poa;
    protected static Logbuf rtcout = null;;
    protected ConsoleHandler m_stdout;
    protected FileHandler m_fh;
    protected void setUp() throws Exception {
        super.setUp();
        if (m_mock_logger == null){
            m_mock_logger = new Logger();
        }
        if (RingBufferMock.m_mock_logger == null){
            RingBufferMock.m_mock_logger = new Logger();
        }

        java.util.Properties props = new java.util.Properties();
        this.m_orb = ORBUtil.getOrb();
        this.m_poa = org.omg.PortableServer.POAHelper.narrow(
                this.m_orb.resolve_initial_references("RootPOA"));
        this.m_poa.the_POAManager().activate();

        if (rtcout == null){
            rtcout = new Logbuf("Manager");
            m_fh = null; 
            rtcout.setLevel("SILENT");
            String logfile = "InPortBaseTest.log";
            m_fh = new FileHandler(logfile);
            rtcout.addStream(m_fh);
            rtcout.setLevel("PARANOID");

        }

        final InPortProviderFactory<InPortProvider,String> factoryI
            = InPortProviderFactory.instance();

        factoryI.addFactory("corba_cdr",
                    new InPortCorbaCdrProviderMock(),
                    new InPortCorbaCdrProviderMock());

        //
        if( OutPortConsumerFactory.instance().hasFactory("corba_cdr") )
        {
            OutPortConsumerFactory.instance().removeFactory("corba_cdr");
        }
        //
        OutPortConsumerFactory.instance().addFactory("corba_cdr",
                    new OutPortCorbaCdrConsumerMock(),
                    new OutPortCorbaCdrConsumerMock());


        //
        if( BufferFactory.instance().hasFactory("ring_buffer") )
        {
            BufferFactory.instance().removeFactory("ring_buffer");
        }
        CdrRingBufferMock.CdrRingBufferMockInit();
    }
    
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * @brief
     * 
     */
    public void test_constructor()
    {

        InPortBaseMock inport = new InPortBaseMock ("InPortBaseTest", "TimedFloat");

        PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
        portAdmin.registerPort(inport); 
        
        RTC.PortProfile profile = inport.getPortProfile();
        _SDOPackage.NVListHolder holder = new _SDOPackage.NVListHolder(profile.properties);
        Properties prop = NVUtil.toProperties(holder);
        profile.properties = holder.value;
        assertTrue(prop.getProperty("port.port_type").equals("DataInPort"));
        assertTrue(prop.getProperty("dataport.data_type").equals("TimedFloat")); 
        assertTrue(prop.getProperty("dataport.subscription_type").equals("Any")); 


        portAdmin.deletePort(inport);


    }
    /**
     * @brief init()
     * 
     */
    public void test_init() {
        //
        //Condition
        //  m_singlebuffer is true(singlebuffer).
        //  m_thebuffer is CdrRingBufferMock.
        //Check
        //  Check that buffer is generated.
        //
        if( InPortProviderFactory.instance().hasFactory("corba_cdr") )
        {
            InPortProviderFactory.instance().removeFactory("corba_cdr");
        }
        //
        InPortProviderFactory.instance().addFactory("corba_cdr",
                    new InPortCorbaCdrProviderMock(),
                    new InPortCorbaCdrProviderMock());

        //
        if( OutPortConsumerFactory.instance().hasFactory("corba_cdr") )
        {
            OutPortConsumerFactory.instance().removeFactory("corba_cdr");
        }
        //
        OutPortConsumerFactory.instance().addFactory("corba_cdr",
                    new OutPortCorbaCdrConsumerMock(),
                    new OutPortCorbaCdrConsumerMock());

        //
        if( BufferFactory.instance().hasFactory("ring_buffer") )
        {
            BufferFactory.instance().removeFactory("ring_buffer");
        }

        //
        CdrRingBufferMock.CdrRingBufferMockInit();

        InPortBaseMock inport = new InPortBaseMock("InPortBaseTest", "TimedFloat");

        PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
        portAdmin.registerPort(inport); 
        
        assertTrue("1:",null==inport.getThebuffer());
        RTC.PortProfile profile = inport.getPortProfile();
        _SDOPackage.NVListHolder holder = new _SDOPackage.NVListHolder(profile.properties);
        Properties prop = NVUtil.toProperties(holder);
        profile.properties = holder.value;
        assertTrue("2:",prop.getProperty("dataport.data_type").equals("TimedFloat"));
        assertTrue("3:",prop.getProperty("dataport.subscription_type").equals("Any")); 

        Vector<String> pstr = inport.getProviderTypes();
        assertEquals("4:",0, pstr.size());
        Vector<String> cstr = inport.get_m_consumerTypes();
        assertEquals("5:",0, cstr.size());
        RingBufferMock.m_mock_logger.clearLog();
        assertEquals("6:",0,
             RingBufferMock.m_mock_logger.countLog("RingBufferMock.Constructor"));

        inport.init();

        //
        assertTrue("7:",null!=inport.getThebuffer());
        assertEquals("8:",1,
             RingBufferMock.m_mock_logger.countLog("RingBufferMock.Constructor"));

        profile = inport.getPortProfile();
        holder = new _SDOPackage.NVListHolder(profile.properties);
        prop = NVUtil.toProperties(holder);
        profile.properties = holder.value;

        //
        assertTrue("9:",prop.getProperty("dataport.dataflow_type").equals("push,pull"));
        assertTrue("10:",prop.getProperty("dataport.interface_type").equals("corba_cdr")); 
 
        //
        pstr = inport.getProviderTypes();
        assertTrue("11:",pstr.elementAt(0).equals("corba_cdr"));
        cstr = inport.get_m_consumerTypes();
        assertTrue("12:",cstr.elementAt(0).equals("corba_cdr"));

        portAdmin.deletePort(inport);
    }
    /**
     * @brief init()
     * 
     */
    public void test_init2() {
        //
        //Condition
        //  m_singlebuffer is false(multibuffer).
        //  m_thebuffer is CdrRingBufferMock.
        //Check
        //  Check that buffer is not generated.
        //
        //
        if( InPortProviderFactory.instance().hasFactory("corba_cdr") )
        {
            InPortProviderFactory.instance().removeFactory("corba_cdr");
        }
        //
        InPortProviderFactory.instance().addFactory("corba_cdr",
                    new InPortCorbaCdrProviderMock(),
                    new InPortCorbaCdrProviderMock());

        //
        if( OutPortConsumerFactory.instance().hasFactory("corba_cdr") )
        {
            OutPortConsumerFactory.instance().removeFactory("corba_cdr");
        }
        //
        OutPortConsumerFactory.instance().addFactory("corba_cdr",
                    new OutPortCorbaCdrConsumerMock(),
                    new OutPortCorbaCdrConsumerMock());

        //
        if( BufferFactory.instance().hasFactory("ring_buffer") )
        {
            BufferFactory.instance().removeFactory("ring_buffer");
        }
        //
        CdrRingBufferMock.CdrRingBufferMockInit();
 
        InPortBaseMock inport = new InPortBaseMock("InPortBaseTest", "TimedFloat");

        PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
        portAdmin.registerPort(inport); 
        
        assertTrue(null==inport.getThebuffer());
        RTC.PortProfile profile = inport.getPortProfile();
        _SDOPackage.NVListHolder holder = new _SDOPackage.NVListHolder(profile.properties);
        Properties prop = NVUtil.toProperties(holder);
        profile.properties = holder.value;
        assertTrue("2:",prop.getProperty("dataport.dataflow_type").equals(""));
        assertTrue("3:",prop.getProperty("dataport.interface_type").equals("")); 

        Vector<String> pstr = inport.getProviderTypes();
        assertEquals(0, pstr.size());
        Vector<String> cstr = inport.get_m_consumerTypes();
        assertEquals(0, cstr.size());
        RingBufferMock.m_mock_logger.clearLog();
        assertEquals("6:",0,
             RingBufferMock.m_mock_logger.countLog("RingBufferMock.Constructor"));

        inport.setMultibuffer();
        inport.init();

        assertTrue(null==inport.getThebuffer());
        assertEquals("8:",0,
             RingBufferMock.m_mock_logger.countLog("RingBufferMock.Constructor"));

        profile = inport.getPortProfile();
        holder = new _SDOPackage.NVListHolder(profile.properties);
        prop = NVUtil.toProperties(holder);
        profile.properties = holder.value;

        //
        assertTrue("9:",prop.getProperty("dataport.dataflow_type").equals("push,pull"));
        assertTrue("10:",prop.getProperty("dataport.interface_type").equals("corba_cdr")); 
 
 
        //
        pstr = inport.getProviderTypes();
        assertTrue("11:",pstr.elementAt(0).equals("corba_cdr"));
        cstr = inport.get_m_consumerTypes();
        assertTrue("12:",cstr.elementAt(0).equals("corba_cdr"));
        portAdmin.deletePort(inport);
    }
    /**
     * @brief init()
     * 
     */
    public void test_init3() {
        //
        //Condition
        //  m_singlebuffer is true(singlebuffer).
        //  m_thebuffer is unset.(The buffer is not registered in Factory.)
        //Check
        //  Check that buffer is not generated.
        //  
        //
        if( InPortProviderFactory.instance().hasFactory("corba_cdr") )
        {
            InPortProviderFactory.instance().removeFactory("corba_cdr");
        }
        //
        InPortProviderFactory.instance().addFactory("corba_cdr",
                    new InPortCorbaCdrProviderMock(),
                    new InPortCorbaCdrProviderMock());

        //
        if( OutPortConsumerFactory.instance().hasFactory("corba_cdr") )
        {
            OutPortConsumerFactory.instance().removeFactory("corba_cdr");
        }
        //
        OutPortConsumerFactory.instance().addFactory("corba_cdr",
                    new OutPortCorbaCdrConsumerMock(),
                    new OutPortCorbaCdrConsumerMock());

        //
        //
        if( BufferFactory.instance().hasFactory("ring_buffer") )
        {
            BufferFactory.instance().removeFactory("ring_buffer");
        }
        //

        InPortBaseMock inport = new InPortBaseMock("InPortBaseTest", "TimedFloat");

        PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
        portAdmin.registerPort(inport); 
        
        assertTrue("1:",null==inport.getThebuffer());
        RTC.PortProfile profile = inport.getPortProfile();
        _SDOPackage.NVListHolder holder = new _SDOPackage.NVListHolder(profile.properties);
        Properties prop = NVUtil.toProperties(holder);
        profile.properties = holder.value;
        assertTrue("2:",prop.getProperty("dataport.data_type").equals("TimedFloat"));
        assertTrue("3:",prop.getProperty("dataport.subscription_type").equals("Any")); 


        Vector<String> pstr = inport.getProviderTypes();
        assertEquals("4:",0, pstr.size());
        Vector<String> cstr = inport.get_m_consumerTypes();
        assertEquals("5:",0, cstr.size());
        RingBufferMock.m_mock_logger.clearLog();
        assertEquals("6:",0,
             RingBufferMock.m_mock_logger.countLog("RingBufferMock.Constructor"));

        inport.init();

        //
        assertTrue("7:",null==inport.getThebuffer());
        assertEquals("8:",0,
             RingBufferMock.m_mock_logger.countLog("RingBufferMock.Constructor"));

        profile = inport.getPortProfile();
        holder = new _SDOPackage.NVListHolder(profile.properties);
        prop = NVUtil.toProperties(holder);
        profile.properties = holder.value;

        //
        assertTrue("9:",prop.getProperty("dataport.dataflow_type").equals("push,pull"));
        assertTrue("10:",prop.getProperty("dataport.interface_type").equals("corba_cdr")); 
 
 
        //
        pstr = inport.getProviderTypes();
        assertTrue("11:",pstr.elementAt(0).equals("corba_cdr"));
        cstr = inport.get_m_consumerTypes();
        assertTrue("12:",cstr.elementAt(0).equals("corba_cdr"));


        portAdmin.deletePort(inport);
    }
    /**
     * @brief initProviders()
     * 
     */
    public void test_initProviders()
    {
        //
        if( InPortProviderFactory.instance().hasFactory("corba_cdr") )
        {
            InPortProviderFactory.instance().removeFactory("corba_cdr");
        }
        //
        InPortProviderFactory.instance().addFactory("corba_cdr",
                    new InPortCorbaCdrProviderMock(),
                    new InPortCorbaCdrProviderMock());

        //
        if( BufferFactory.instance().hasFactory("ring_buffer") )
        {
            BufferFactory.instance().removeFactory("ring_buffer");
        }

        //
        if( OutPortConsumerFactory.instance().hasFactory("corba_cdr") )
        {
            OutPortConsumerFactory.instance().removeFactory("corba_cdr");
        }
        //
        OutPortConsumerFactory.instance().addFactory("corba_cdr",
                    new OutPortCorbaCdrConsumerMock(),
                    new OutPortCorbaCdrConsumerMock());

        //
        CdrRingBufferMock.CdrRingBufferMockInit();


        InPortBaseMock inport = new InPortBaseMock("InPortBaseTest", "TimedFloat");

        PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
        portAdmin.registerPort(inport); 
        
        RTC.PortProfile profile = inport.getPortProfile();
        _SDOPackage.NVListHolder holder = new _SDOPackage.NVListHolder(profile.properties);
        Properties prop = NVUtil.toProperties(holder);
        profile.properties = holder.value;
        assertTrue("2:",prop.getProperty("dataport.data_type").equals("TimedFloat"));
        assertTrue("3:",prop.getProperty("dataport.subscription_type").equals("Any")); 

        Vector<String> pstr = inport.getProviderTypes();
        assertEquals(0, pstr.size());
        inport.initProviders_public();


        profile = inport.getPortProfile();
        holder = new _SDOPackage.NVListHolder(profile.properties);
        prop = NVUtil.toProperties(holder);
        profile.properties = holder.value;

        //
        assertTrue(prop.getProperty("dataport.dataflow_type").equals("push"));
        assertTrue(prop.getProperty("dataport.interface_type").equals("corba_cdr")); 
 
        //
        pstr = inport.getProviderTypes();
        assertTrue(0!= pstr.size());
        assertTrue("11:",pstr.elementAt(0).equals("corba_cdr"));

        portAdmin.deletePort(inport);
    }
    /**
     * @brief initProviders()
     * The provider is not registered in Factory.
     * 
     */
    public void test_initProviders2()
    {
        //
        if( InPortProviderFactory.instance().hasFactory("corba_cdr") )
        {
            InPortProviderFactory.instance().removeFactory("corba_cdr");
        }

        //
        if( OutPortConsumerFactory.instance().hasFactory("corba_cdr") )
        {
            OutPortConsumerFactory.instance().removeFactory("corba_cdr");
        }
        //
        OutPortConsumerFactory.instance().addFactory("corba_cdr",
                    new OutPortCorbaCdrConsumerMock(),
                    new OutPortCorbaCdrConsumerMock());

        //
        if( BufferFactory.instance().hasFactory("ring_buffer") )
        {
            BufferFactory.instance().removeFactory("ring_buffer");
        }
        //
        CdrRingBufferMock.CdrRingBufferMockInit();


        InPortBaseMock inport= new InPortBaseMock("InPortBaseTest", "TimedFloat");

        PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
        portAdmin.registerPort(inport); 
        
        RTC.PortProfile profile = inport.getPortProfile();
        _SDOPackage.NVListHolder holder = new _SDOPackage.NVListHolder(profile.properties);
        Properties prop = NVUtil.toProperties(holder);
        profile.properties = holder.value;
        assertTrue("2:",prop.getProperty("dataport.dataflow_type").equals(""));
        assertTrue("3:",prop.getProperty("dataport.interface_type").equals("")); 

        Vector<String> pstr = inport.getProviderTypes();
        assertEquals(0, pstr.size());
        inport.initProviders_public();


        profile = inport.getPortProfile();
        holder = new _SDOPackage.NVListHolder(profile.properties);
        prop = NVUtil.toProperties(holder);
        profile.properties = holder.value;

        //
        assertTrue(prop.getProperty("dataport.dataflow_type").equals(""));
        assertTrue(prop.getProperty("dataport.interface_type").equals("")); 
 
        //
        pstr = inport.getProviderTypes();
        assertTrue(0== pstr.size());

        portAdmin.deletePort(inport);
    }
    /**
     * @brief initConsumers()
     * 
     */
    public void test_initConsumers()
    {
        //
        if( InPortProviderFactory.instance().hasFactory("corba_cdr") )
        {
            InPortProviderFactory.instance().removeFactory("corba_cdr");
        }
        //
        InPortProviderFactory.instance().addFactory("corba_cdr",
                    new InPortCorbaCdrProviderMock(),
                    new InPortCorbaCdrProviderMock());

        //
        if( OutPortConsumerFactory.instance().hasFactory("corba_cdr") )
        {
            OutPortConsumerFactory.instance().removeFactory("corba_cdr");
        }
        //
        OutPortConsumerFactory.instance().addFactory("corba_cdr",
                    new OutPortCorbaCdrConsumerMock(),
                    new OutPortCorbaCdrConsumerMock());

        //
        if( BufferFactory.instance().hasFactory("ring_buffer") )
        {
            BufferFactory.instance().removeFactory("ring_buffer");
        }
        //
        CdrRingBufferMock.CdrRingBufferMockInit();

        InPortBaseMock inport = new InPortBaseMock("InPortBaseTest", "TimedFloat");

        PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
        portAdmin.registerPort(inport); 

        RTC.PortProfile profile = inport.getPortProfile();
        _SDOPackage.NVListHolder holder = new _SDOPackage.NVListHolder(profile.properties);
        Properties prop = NVUtil.toProperties(holder);
        profile.properties = holder.value;
        assertTrue("2:",prop.getProperty("dataport.dataflow_type").equals(""));
        assertTrue("3:",prop.getProperty("dataport.interface_type").equals("")); 

        Vector<String> cstr = inport.get_m_consumerTypes();
        assertEquals("4:",0, cstr.size());

        inport.initConsumers_public();

        profile = inport.getPortProfile();
        holder = new _SDOPackage.NVListHolder(profile.properties);
        prop = NVUtil.toProperties(holder);
        profile.properties = holder.value;

        //
        assertTrue("5:",prop.getProperty("dataport.dataflow_type").equals("pull"));
        assertTrue("6:",prop.getProperty("dataport.interface_type").equals("corba_cdr")); 
 
        //
        cstr = inport.get_m_consumerTypes();
        assertTrue("7:",0!= cstr.size());
        assertTrue("11:",cstr.elementAt(0).equals("corba_cdr"));

        portAdmin.deletePort(inport);

    }
    /**
     * @brief initConsumers()
     * 
     */
    public void test_initConsumers2()
    {
        //
        if( InPortProviderFactory.instance().hasFactory("corba_cdr") )
        {
            InPortProviderFactory.instance().removeFactory("corba_cdr");
        }
        //
        InPortProviderFactory.instance().addFactory("corba_cdr",
                    new InPortCorbaCdrProviderMock(),
                    new InPortCorbaCdrProviderMock());

        //
        if( OutPortConsumerFactory.instance().hasFactory("corba_cdr") )
        {
            OutPortConsumerFactory.instance().removeFactory("corba_cdr");
        }

        //
        if( BufferFactory.instance().hasFactory("ring_buffer") )
        {
            BufferFactory.instance().removeFactory("ring_buffer");
        }
        //
        CdrRingBufferMock.CdrRingBufferMockInit();

        InPortBaseMock inport = new InPortBaseMock("InPortBaseTest", "TimedFloat");

        PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
        portAdmin.registerPort(inport); 

        RTC.PortProfile profile = inport.getPortProfile();
        _SDOPackage.NVListHolder holder = new _SDOPackage.NVListHolder(profile.properties);
        Properties prop = NVUtil.toProperties(holder);
        profile.properties = holder.value;
        assertTrue("2:",prop.getProperty("dataport.dataflow_type").equals(""));
        assertTrue("3:",prop.getProperty("dataport.interface_type").equals("")); 

        Vector<String> cstr = inport.get_m_consumerTypes();
        assertEquals("4:",0, cstr.size());

        inport.initConsumers_public();

        profile = inport.getPortProfile();
        holder = new _SDOPackage.NVListHolder(profile.properties);
        prop = NVUtil.toProperties(holder);
        profile.properties = holder.value;

        //
        assertTrue("5:",prop.getProperty("dataport.dataflow_type").equals(""));
        assertTrue("6:",prop.getProperty("dataport.interface_type").equals("")); 
 
        //
        cstr = inport.get_m_consumerTypes();
        assertTrue("7:",0== cstr.size());

        portAdmin.deletePort(inport);

    }

    /**
     * @brief activateInterfaces(),deactivateInterfaces()
     * 
     */
    public void test_activateInterfaces_deactivateInterfaces()
    {
        //
        if( InPortProviderFactory.instance().hasFactory("corba_cdr") )
        {
            InPortProviderFactory.instance().removeFactory("corba_cdr");
        }
        //
        InPortProviderFactory.instance().addFactory("corba_cdr",
                    new InPortCorbaCdrProviderMock(),
                    new InPortCorbaCdrProviderMock());

        //
        if( OutPortConsumerFactory.instance().hasFactory("corba_cdr") )
        {
            OutPortConsumerFactory.instance().removeFactory("corba_cdr");
        }
        //
        OutPortConsumerFactory.instance().addFactory("corba_cdr",
                    new OutPortCorbaCdrConsumerMock(),
                    new OutPortCorbaCdrConsumerMock());

        //
        if( BufferFactory.instance().hasFactory("ring_buffer") )
        {
            BufferFactory.instance().removeFactory("ring_buffer");
        }

        //
        CdrRingBufferMock.CdrRingBufferMockInit();

        InPortBaseMock inport = new InPortBaseMock("InPortBaseTest", "TimedFloat");
        inport.init();

        PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
        portAdmin.registerPort(inport); 

        RTC.ConnectorProfile prof = new RTC.ConnectorProfile();
        prof.connector_id = "id0";
        prof.name = "InPortBaseTest0";
        prof.ports = new RTC.PortService[1];
        prof.ports[0] = inport.get_port_profile().port_ref;

        NVListHolder holder = new NVListHolder(prof.properties);
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.interface_type",
                                 "corba_cdr"));
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.dataflow_type",
                                 "push"));
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.subscription_type",
                                 "new"));
        prof.properties = holder.value;
        
        ConnectorProfileHolder profh =  new ConnectorProfileHolder(prof);
        inport.publishInterfaces_public(profh);
        prof.connector_id = "id1";
        prof.name = "InPortBaseTest1";
        profh =  new ConnectorProfileHolder(prof);
        inport.publishInterfaces_public(profh);

        assertEquals(2,inport.get_m_connectors().size());
        Vector<InPortConnector> list =  inport.get_m_connectors();
        inport.activateInterfaces();
        inport.deactivateInterfaces();

        portAdmin.deletePort(inport);
    }
    /**
     * @brief publishInterfaces()
     * 
     */
    public void test_publishInterfaces()
    {
        //
        if( InPortProviderFactory.instance().hasFactory("corba_cdr") )
        {
            InPortProviderFactory.instance().removeFactory("corba_cdr");
        }
        //
        InPortProviderFactory.instance().addFactory("corba_cdr",
                    new InPortCorbaCdrProviderMock(),
                    new InPortCorbaCdrProviderMock());

        //
        if( OutPortConsumerFactory.instance().hasFactory("corba_cdr") )
        {
            OutPortConsumerFactory.instance().removeFactory("corba_cdr");
        }
        //
        OutPortConsumerFactory.instance().addFactory("corba_cdr",
                    new OutPortCorbaCdrConsumerMock(),
                    new OutPortCorbaCdrConsumerMock());

        //
        if( BufferFactory.instance().hasFactory("ring_buffer") )
        {
            BufferFactory.instance().removeFactory("ring_buffer");
        }

        //
        CdrRingBufferMock.CdrRingBufferMockInit();

        InPortBaseMock inport = new InPortBaseMock("InPortBaseTest", "TimedFloat");
        inport.init();

        PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
        portAdmin.registerPort(inport); 

        RTC.ConnectorProfile prof = new RTC.ConnectorProfile();
        prof.connector_id = "id0";
        prof.name = "InPortBaseTest0";
        prof.ports = new RTC.PortService[1];
        prof.ports[0] = inport.get_port_profile().port_ref;
        
        NVListHolder holder = new NVListHolder(prof.properties);
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.interface_type",
                                 "corba_cdr"));
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.dataflow_type",
                                 "push"));
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.subscription_type",
                                 "new"));
        prof.properties = holder.value;

        RTC.ReturnCode_t retcode;
        assertEquals(0,inport.get_m_connectors().size());
        ConnectorProfileHolder profh =  new ConnectorProfileHolder(prof);
        retcode = inport.publishInterfaces_public(profh);
        assertEquals(1,inport.get_m_connectors().size());
        assertEquals(ReturnCode_t.RTC_OK,retcode);

        prof.connector_id = "id1";
        prof.name = "InPortBaseTest1";
        profh =  new ConnectorProfileHolder(prof);
        retcode = inport.publishInterfaces_public(profh);
        assertEquals(2,(int)inport.get_m_connectors().size());
        assertEquals(ReturnCode_t.RTC_OK,retcode);

        portAdmin.deletePort(inport);
    }
    /**
     * @brief publishInterfaces()
     *
     * 
     */
    public void test_publishInterfaces2()
    {
        //
        //
        //

        //
        if( InPortProviderFactory.instance().hasFactory("corba_cdr") )
        {
            InPortProviderFactory.instance().removeFactory("corba_cdr");
        }
        //
        InPortProviderFactory.instance().addFactory("corba_cdr",
                    new InPortCorbaCdrProviderMock(),
                    new InPortCorbaCdrProviderMock());

        //
        if( OutPortConsumerFactory.instance().hasFactory("corba_cdr") )
        {
            OutPortConsumerFactory.instance().removeFactory("corba_cdr");
        }
        //
        OutPortConsumerFactory.instance().addFactory("corba_cdr",
                    new OutPortCorbaCdrConsumerMock(),
                    new OutPortCorbaCdrConsumerMock());

        //
        if( BufferFactory.instance().hasFactory("ring_buffer") )
        {
            BufferFactory.instance().removeFactory("ring_buffer");
        }

        //
        CdrRingBufferMock.CdrRingBufferMockInit();

        InPortBaseMock inport = new InPortBaseMock("InPortBaseTest", "TimedFloat");
        inport.init();

        PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
        portAdmin.registerPort(inport); 
        RTC.ConnectorProfile prof = new RTC.ConnectorProfile();
        prof.connector_id = "id0";
        prof.name = "InPortBaseTest0";
        prof.ports = new RTC.PortService[1];
        prof.ports[0] = inport.get_port_profile().port_ref;
        NVListHolder holder = new NVListHolder(prof.properties);
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.interface_type",
                                 "corba_cdr"));
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.dataflow_type",
                                 "pull"));
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.subscription_type",
                                 "new"));
        prof.properties = holder.value;

        RTC.ReturnCode_t retcode;
        assertEquals(0,inport.get_m_connectors().size());
        ConnectorProfileHolder profh =  new ConnectorProfileHolder(prof);
        retcode = inport.publishInterfaces_public(profh);
        assertEquals(0,inport.get_m_connectors().size());
        assertEquals(ReturnCode_t.RTC_OK,retcode);

        prof.connector_id = "id1";
        prof.name = "InPortBaseTest1";
        profh =  new ConnectorProfileHolder(prof);
        retcode = inport.publishInterfaces_public(profh);
        assertEquals(0,inport.get_m_connectors().size());
        assertEquals(ReturnCode_t.RTC_OK,retcode);

        portAdmin.deletePort(inport);
    }
    /**
     * @brief publishInterfaces()
     * 
     */
    public void test_publishInterfaces3()
    {
        //
        //
        //

        //
        if( InPortProviderFactory.instance().hasFactory("corba_cdr") )
        {
            InPortProviderFactory.instance().removeFactory("corba_cdr");
        }
        //
        InPortProviderFactory.instance().addFactory("corba_cdr",
                    new InPortCorbaCdrProviderMock(),
                    new InPortCorbaCdrProviderMock());

        //
        if( OutPortConsumerFactory.instance().hasFactory("corba_cdr") )
        {
            OutPortConsumerFactory.instance().removeFactory("corba_cdr");
        }
        //
        OutPortConsumerFactory.instance().addFactory("corba_cdr",
                    new OutPortCorbaCdrConsumerMock(),
                    new OutPortCorbaCdrConsumerMock());

        //
        if( BufferFactory.instance().hasFactory("ring_buffer") )
        {
            BufferFactory.instance().removeFactory("ring_buffer");
        }

        //
        CdrRingBufferMock.CdrRingBufferMockInit();

        InPortBaseMock inport = new InPortBaseMock("InPortBaseTest", "TimedFloat");
        inport.init();

        PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
        portAdmin.registerPort(inport); 
        RTC.ConnectorProfile prof = new RTC.ConnectorProfile();
        prof.connector_id = "id0";
        prof.name = "InPortBaseTest0";
        prof.ports = new RTC.PortService[1];
        prof.ports[0] = inport.get_port_profile().port_ref;
        NVListHolder holder = new NVListHolder(prof.properties);
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.interface_type",
                                 "corba_cdr"));
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.dataflow_type",
                                 "else"));
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.subscription_type",
                                 "new"));
        prof.properties = holder.value;
        RTC.ReturnCode_t retcode;
        assertEquals(0,inport.get_m_connectors().size());
        ConnectorProfileHolder profh =  new ConnectorProfileHolder(prof);
        retcode = inport.publishInterfaces_public(profh);
        assertEquals(0,inport.get_m_connectors().size());
        assertEquals(ReturnCode_t.BAD_PARAMETER,retcode);

        prof.connector_id = "id1";
        prof.name = "InPortBaseTest1";
        profh =  new ConnectorProfileHolder(prof);
        retcode = inport.publishInterfaces_public(profh);
        assertEquals(0,inport.get_m_connectors().size());
        assertEquals(ReturnCode_t.BAD_PARAMETER,retcode);

        portAdmin.deletePort(inport);
    }
    /**
     * @brief publishInterfaces(),deactivateInterfaces()
     * 
     */
    public void test_publishInterfaces4()
    {
        //
        //
        //

        //
        if( InPortProviderFactory.instance().hasFactory("corba_cdr") )
        {
            InPortProviderFactory.instance().removeFactory("corba_cdr");
        }

        //
        if( OutPortConsumerFactory.instance().hasFactory("corba_cdr") )
        {
            OutPortConsumerFactory.instance().removeFactory("corba_cdr");
        }
        //
        OutPortConsumerFactory.instance().addFactory("corba_cdr",
                    new OutPortCorbaCdrConsumerMock(),
                    new OutPortCorbaCdrConsumerMock());

        //
        if( BufferFactory.instance().hasFactory("ring_buffer") )
        {
            BufferFactory.instance().removeFactory("ring_buffer");
        }

        //
        CdrRingBufferMock.CdrRingBufferMockInit();

        InPortBaseMock inport = new InPortBaseMock("InPortBaseTest", "TimedFloat");
        inport.init();

        PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
        portAdmin.registerPort(inport); 
        RTC.ConnectorProfile prof = new RTC.ConnectorProfile();
        prof.connector_id = "id0";
        prof.name = "InPortBaseTest0";
        prof.ports = new RTC.PortService[1];
        prof.ports[0] = inport.get_port_profile().port_ref;
        NVListHolder holder = new NVListHolder(prof.properties);
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.interface_type",
                                 "corba_cdr"));
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.dataflow_type",
                                 "else"));
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.subscription_type",
                                 "new"));
        prof.properties = holder.value;
        RTC.ReturnCode_t retcode;
        assertEquals(0,inport.get_m_connectors().size());
        ConnectorProfileHolder profh =  new ConnectorProfileHolder(prof);
        retcode = inport.publishInterfaces_public(profh);
        assertEquals(0,inport.get_m_connectors().size());
        assertEquals(ReturnCode_t.BAD_PARAMETER,retcode);

        portAdmin.deletePort(inport);
    }
    /**
     * @brief publishInterfaces()
     * 
     */
    public void test_publishInterfaces5()
    {
        //
        //
        //

        //
        if( InPortProviderFactory.instance().hasFactory("corba_cdr") )
        {
            InPortProviderFactory.instance().removeFactory("corba_cdr");
        }
        //
        InPortProviderFactory.instance().addFactory("corba_cdr",
                    new InPortCorbaCdrProviderMock(),
                    new InPortCorbaCdrProviderMock());

        //
        if( OutPortConsumerFactory.instance().hasFactory("corba_cdr") )
        {
            OutPortConsumerFactory.instance().removeFactory("corba_cdr");
        }
        //
        OutPortConsumerFactory.instance().addFactory("corba_cdr",
                    new OutPortCorbaCdrConsumerMock(),
                    new OutPortCorbaCdrConsumerMock());

        //
        if( BufferFactory.instance().hasFactory("ring_buffer") )
        {
            BufferFactory.instance().removeFactory("ring_buffer");
        }


        InPortBaseMock inport = new InPortBaseMock("InPortBaseTest", "TimedFloat");
        inport.init();

        PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
        portAdmin.registerPort(inport); 
        RTC.ConnectorProfile prof = new RTC.ConnectorProfile();
        prof.connector_id = "id0";
        prof.name = "InPortBaseTest0";
        prof.ports = new RTC.PortService[1];
        prof.ports[0] = inport.get_port_profile().port_ref;
        NVListHolder holder = new NVListHolder(prof.properties);
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.interface_type",
                                 "corba_cdr"));
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.dataflow_type",
                                 "else"));
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.subscription_type",
                                 "new"));
        prof.properties = holder.value;
        RTC.ReturnCode_t retcode;
        assertEquals(0,inport.get_m_connectors().size());
        ConnectorProfileHolder profh =  new ConnectorProfileHolder(prof);
        retcode = inport.publishInterfaces_public(profh);
        assertEquals(0,inport.get_m_connectors().size());
        assertEquals(ReturnCode_t.BAD_PARAMETER,retcode);


        portAdmin.deletePort(inport);
    }
    /**
     * @brief subscribeInterfaces()
     * 
     */
    public void test_subscribeInterfaces()
    {
        //
        if( InPortProviderFactory.instance().hasFactory("corba_cdr") )
        {
            InPortProviderFactory.instance().removeFactory("corba_cdr");
        }
        //
        InPortProviderFactory.instance().addFactory("corba_cdr",
                    new InPortCorbaCdrProviderMock(),
                    new InPortCorbaCdrProviderMock());

        //
        if( OutPortConsumerFactory.instance().hasFactory("corba_cdr") )
        {
            OutPortConsumerFactory.instance().removeFactory("corba_cdr");
        }
        //
        OutPortConsumerFactory.instance().addFactory("corba_cdr",
                    new OutPortCorbaCdrConsumerMock(),
                    new OutPortCorbaCdrConsumerMock());

        //
        if( BufferFactory.instance().hasFactory("ring_buffer") )
        {
            BufferFactory.instance().removeFactory("ring_buffer");
        }

        //
        CdrRingBufferMock.CdrRingBufferMockInit();

        InPortBaseMock inport = new InPortBaseMock("InPortBaseTest", "TimedFloat");
        inport.init();

        PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
        portAdmin.registerPort(inport); 
        RTC.ConnectorProfile prof = new RTC.ConnectorProfile();
        prof.connector_id = "id0";
        prof.name = "InPortBaseTest0";
        prof.ports = new RTC.PortService[1];
        prof.ports[0] = inport.get_port_profile().port_ref;

        NVListHolder holder = new NVListHolder(prof.properties);
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.interface_type",
                                 "corba_cdr"));
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.dataflow_type",
                                 "pull"));
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.subscription_type",
                                 "new"));
        prof.properties = holder.value;
        RTC.ReturnCode_t retcode;
        assertEquals(0,inport.get_m_connectors().size());
        ConnectorProfileHolder profh =  new ConnectorProfileHolder(prof);
        retcode = inport.subscribeInterfaces_public(profh);
        assertEquals(ReturnCode_t.RTC_OK,retcode);
        assertEquals(1,inport.get_m_connectors().size());

        prof.connector_id = "id1";
        prof.name = "InPortBaseTest1";
        profh =  new ConnectorProfileHolder(prof);
        retcode = inport.subscribeInterfaces_public(profh);
        assertEquals(2,inport.get_m_connectors().size());
        assertEquals(ReturnCode_t.RTC_OK,retcode);

        portAdmin.deletePort(inport);
    }
    /**
     * @brief subscribeInterfaces()
     *
     */
    public void test_subscribeInterfaces2()
    {
        //
        //

        //
        if( InPortProviderFactory.instance().hasFactory("corba_cdr") )
        {
            InPortProviderFactory.instance().removeFactory("corba_cdr");
        }
        //
        InPortProviderFactory.instance().addFactory("corba_cdr",
                    new InPortCorbaCdrProviderMock(),
                    new InPortCorbaCdrProviderMock());

        //
        if( OutPortConsumerFactory.instance().hasFactory("corba_cdr") )
        {
            OutPortConsumerFactory.instance().removeFactory("corba_cdr");
        }
        //
        OutPortConsumerFactory.instance().addFactory("corba_cdr",
                    new OutPortCorbaCdrConsumerMock(),
                    new OutPortCorbaCdrConsumerMock());

        //
        if( BufferFactory.instance().hasFactory("ring_buffer") )
        {
            BufferFactory.instance().removeFactory("ring_buffer");
        }

        //
        CdrRingBufferMock.CdrRingBufferMockInit();

        InPortBaseMock inport = new InPortBaseMock("InPortBaseTest", "TimedFloat");
        inport.init();

        PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
        portAdmin.registerPort(inport); 
        RTC.ConnectorProfile prof = new RTC.ConnectorProfile();
        prof.connector_id = "id0";
        prof.name = "InPortBaseTest0";
        prof.ports = new RTC.PortService[1];
        prof.ports[0] = inport.get_port_profile().port_ref;
        NVListHolder holder = new NVListHolder(prof.properties);
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.interface_type",
                                 "corba_cdr"));
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.dataflow_type",
                                 "push"));
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.subscription_type",
                                 "new"));
        prof.properties = holder.value;
        RTC.ReturnCode_t retcode;
        assertEquals(0,inport.get_m_connectors().size());
        ConnectorProfileHolder profh =  new ConnectorProfileHolder(prof);
        retcode = inport.subscribeInterfaces_public(profh);
        assertEquals(0,inport.get_m_connectors().size());
        assertEquals(ReturnCode_t.RTC_OK,retcode);


        portAdmin.deletePort(inport);
    }
    /**
     * @brief subscribeInterfaces()
     * 
     */
    public void test_subscribeInterfaces3()
    {
        //
        //
        //

        //
        if( InPortProviderFactory.instance().hasFactory("corba_cdr") )
        {
            InPortProviderFactory.instance().removeFactory("corba_cdr");
        }
        //
        InPortProviderFactory.instance().addFactory("corba_cdr",
                    new InPortCorbaCdrProviderMock(),
                    new InPortCorbaCdrProviderMock());

        //
        if( OutPortConsumerFactory.instance().hasFactory("corba_cdr") )
        {
            OutPortConsumerFactory.instance().removeFactory("corba_cdr");
        }
        //
        OutPortConsumerFactory.instance().addFactory("corba_cdr",
                    new OutPortCorbaCdrConsumerMock(),
                    new OutPortCorbaCdrConsumerMock());

        //
        if( BufferFactory.instance().hasFactory("ring_buffer") )
        {
            BufferFactory.instance().removeFactory("ring_buffer");
        }

        //
        CdrRingBufferMock.CdrRingBufferMockInit();

        InPortBaseMock inport = new InPortBaseMock("InPortBaseTest", "TimedFloat");
        inport.init();

        PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
        portAdmin.registerPort(inport); 
        RTC.ConnectorProfile prof = new RTC.ConnectorProfile();
        prof.connector_id = "id0";
        prof.name = "InPortBaseTest0";
        prof.ports = new RTC.PortService[1];
        prof.ports[0] = inport.get_port_profile().port_ref;
        NVListHolder holder = new NVListHolder(prof.properties);
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.interface_type",
                                 "corba_cdr"));
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.dataflow_type",
                                 "else"));
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.subscription_type",
                                 "new"));
        prof.properties = holder.value;
        RTC.ReturnCode_t retcode;
        assertEquals(0,inport.get_m_connectors().size());
        ConnectorProfileHolder profh =  new ConnectorProfileHolder(prof);
        retcode = inport.subscribeInterfaces_public(profh);
        assertEquals(0,inport.get_m_connectors().size());
        assertEquals(ReturnCode_t.BAD_PARAMETER,retcode);


        portAdmin.deletePort(inport);
    }
    /**
     * @brief subscribeInterfaces()
     * 
     */
    public void test_subscribeInterfaces4()
    {
        //
        //
        //

        //
        if( InPortProviderFactory.instance().hasFactory("corba_cdr") )
        {
            InPortProviderFactory.instance().removeFactory("corba_cdr");
        }
        //
        InPortProviderFactory.instance().addFactory("corba_cdr",
                    new InPortCorbaCdrProviderMock(),
                    new InPortCorbaCdrProviderMock());

        //
        if( OutPortConsumerFactory.instance().hasFactory("corba_cdr") )
        {
            OutPortConsumerFactory.instance().removeFactory("corba_cdr");
        }

        //
        if( BufferFactory.instance().hasFactory("ring_buffer") )
        {
            BufferFactory.instance().removeFactory("ring_buffer");
        }

        //
        CdrRingBufferMock.CdrRingBufferMockInit();

        InPortBaseMock inport = new InPortBaseMock("InPortBaseTest", "TimedFloat");
        inport.init();

        PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
        portAdmin.registerPort(inport); 
        RTC.ConnectorProfile prof = new RTC.ConnectorProfile();
        prof.connector_id = "id0";
        prof.name = "InPortBaseTest0";
        prof.ports = new RTC.PortService[1];
        prof.ports[0] = inport.get_port_profile().port_ref;
        NVListHolder holder = new NVListHolder(prof.properties);
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.interface_type",
                                 "corba_cdr"));
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.dataflow_type",
                                 "pull"));
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.subscription_type",
                                 "new"));
        prof.properties = holder.value;
        RTC.ReturnCode_t retcode;
        assertEquals(0,inport.get_m_connectors().size());
        ConnectorProfileHolder profh =  new ConnectorProfileHolder(prof);
        retcode = inport.subscribeInterfaces_public(profh);
        assertEquals(0,inport.get_m_connectors().size());
        assertEquals(ReturnCode_t.BAD_PARAMETER,retcode);

        portAdmin.deletePort(inport);
    }
    /**
     * @brief subscribeInterfaces()
     * 
     */
    public void test_subscribeInterfaces5()
    {
        //
        //
        //

        //
        if( InPortProviderFactory.instance().hasFactory("corba_cdr") )
        {
            InPortProviderFactory.instance().removeFactory("corba_cdr");
        }
        //
        InPortProviderFactory.instance().addFactory("corba_cdr",
                    new InPortCorbaCdrProviderMock(),
                    new InPortCorbaCdrProviderMock());

        //
        if( OutPortConsumerFactory.instance().hasFactory("corba_cdr") )
        {
            OutPortConsumerFactory.instance().removeFactory("corba_cdr");
        }
        //
        OutPortConsumerFactory.instance().addFactory("corba_cdr",
                    new OutPortCorbaCdrConsumerMock(),
                    new OutPortCorbaCdrConsumerMock());

        //
        if( BufferFactory.instance().hasFactory("ring_buffer") )
        {
            BufferFactory.instance().removeFactory("ring_buffer");
        }

        InPortBaseMock inport = new InPortBaseMock("InPortBaseTest", "TimedFloat");
        inport.init();

        PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
        RTC.ConnectorProfile prof = new RTC.ConnectorProfile();
        prof.connector_id = "id0";
        prof.name = ("InPortBaseTest0");
        prof.ports = new RTC.PortService[1];
        prof.ports[0] = inport.get_port_profile().port_ref;
        NVListHolder holder = new NVListHolder(prof.properties);
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.interface_type",
                                 "corba_cdr"));
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.dataflow_type",
                                 "pull"));
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.subscription_type",
                                 "new"));
        prof.properties = holder.value;
        RTC.ReturnCode_t retcode;
        assertEquals(0,inport.get_m_connectors().size());
        ConnectorProfileHolder profh =  new ConnectorProfileHolder(prof);
        retcode = inport.subscribeInterfaces_public(profh);
        assertEquals(0,inport.get_m_connectors().size());
        assertEquals(ReturnCode_t.RTC_ERROR,retcode);

        portAdmin.deletePort(inport);
    }

    /**
     * @brief createConnector()
     * 
     */
    public void test_createConnector()
    {
        InPortBaseMock inport = new InPortBaseMock("InPortBaseTest", "TimedDouble");

        PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
        portAdmin.registerPort(inport); 

        RTC.ConnectorProfile prof = new RTC.ConnectorProfile();
        prof.connector_id = "id0";
        prof.name = ("InPortBaseTest0");
        prof.ports = new RTC.PortService[1];
        prof.ports[0] = inport.get_port_profile().port_ref;
        NVListHolder holder = new NVListHolder(prof.properties);
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.interface_type",
                                 "corba_cdr"));
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.dataflow_type",
                                 "pull"));
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.subscription_type",
                                 "new"));
        prof.properties = holder.value;

        ConnectorProfileHolder cprof = new ConnectorProfileHolder(prof);

        Properties inprop = new Properties(inport.properties());
        InPortProvider provider = new InPortCorbaCdrProviderMock();
        InPortConnector connector 
              = inport.createConnector_public(cprof, inprop, provider);
        assertTrue(null!= connector);
            

        portAdmin.deletePort(inport);
    }
    /*
     * @brief createConnector()
     * 
     */
    public void test_createConnector2()
    {
        InPortBaseMock inport = new InPortBaseMock("InPortBaseTest", "TimedDouble");

        PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
        portAdmin.registerPort(inport); 


        RTC.ConnectorProfile prof = new RTC.ConnectorProfile();
        prof.connector_id = "id0";
        prof.name = ("InPortBaseTest0");
        prof.ports = new RTC.PortService[1];
        prof.ports[0] = inport.get_port_profile().port_ref;
        NVListHolder holder = new NVListHolder(prof.properties);
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.interface_type",
                                 "corba_cdr"));
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.dataflow_type",
                                 "pull"));
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.subscription_type",
                                 "new"));
        prof.properties = holder.value;

        ConnectorProfileHolder cprof = new ConnectorProfileHolder(prof);

        Properties inprop = new Properties(inport.properties());
//        inprop.setProperty("InPortBaseTests","bad_alloc");
//        InPortProvider provider = new InPortCorbaCdrProviderMock();
        InPortProvider provider = null;
        InPortConnector connector 
              = inport.createConnector_public(cprof, inprop, provider);
        assertTrue(null == connector);
            

        portAdmin.deletePort(inport);
    }

    
}
    /**
     * 
     * 
     *
     */
    class CdrRingBufferMock extends CdrRingBuffer{
    
        /**
         * <p> creator_ </p>
         * 
         * @return Object Created instances
         *
         */
        public BufferBase<OutputStream> creator_() {
            return new RingBufferMock<OutputStream>();
        }
        /**
         * <p> destructor_ </p>
         * 
         * @param obj    The target instances for destruction
         *
         */
        public void destructor_(Object obj) {
            obj = null;
        }

        /**
         * <p> CdrRingBufferInit </p>
         *
         */
        public static void CdrRingBufferMockInit() {
            final BufferFactory<RingBufferMock<OutputStream>,String> factory 
                = BufferFactory.instance();

            factory.addFactory("ring_buffer",
                        new CdrRingBufferMock(),
                        new CdrRingBufferMock());
    
        }
    }
    /**
     * 
     * 
     *
     */
    class RingBufferMock<DataType> extends RingBuffer<DataType>{
        public RingBufferMock() {
            m_logger = null;
            m_mock_logger.log("RingBufferMock.Constructor");
            m_read_return_value = jp.go.aist.rtm.RTC.buffer.ReturnCode.BUFFER_OK;
            m_write_return_value = jp.go.aist.rtm.RTC.buffer.ReturnCode.BUFFER_OK;
        }
        /**
         *
         *
         */
        public void set_read_return_value(jp.go.aist.rtm.RTC.buffer.ReturnCode value) {
            m_read_return_value = value;
        }
        /**
         *
         *
         */
        public void set_write_return_value(jp.go.aist.rtm.RTC.buffer.ReturnCode value) {
            m_write_return_value = value;
        }
        /**
         *
         *
         */
        public  void init(final Properties prop) {
        }
        /**
         *
         *
         */
        public int length() {
            return 0;
        }
        /**
         *
         *
         */
        public jp.go.aist.rtm.RTC.buffer.ReturnCode length(int n) {
            return jp.go.aist.rtm.RTC.buffer.ReturnCode.BUFFER_OK; //BUFFER_OK;
        }
        /**
         *
         *
         */
        public jp.go.aist.rtm.RTC.buffer.ReturnCode reset() {
            return jp.go.aist.rtm.RTC.buffer.ReturnCode.BUFFER_OK; //BUFFER_OK;
        }
        /**
         *
         *
         */
        public DataType wptr(int n) {
            return m_data;
        }
        /**
         *
         *
         */
        public  jp.go.aist.rtm.RTC.buffer.ReturnCode advanceWptr(int n) {
            return jp.go.aist.rtm.RTC.buffer.ReturnCode.BUFFER_OK; //BUFFER_OK;
        }
        /**
         *
         *
         */
        public jp.go.aist.rtm.RTC.buffer.ReturnCode put(final DataType value) {
            return jp.go.aist.rtm.RTC.buffer.ReturnCode.BUFFER_OK; //BUFFER_OK;
        }
        /**
         *
         *
         */
        public jp.go.aist.rtm.RTC.buffer.ReturnCode write(final DataType value,
                                 int sec, int nsec) {
            if (m_logger != null) {
                m_logger.log("RingBufferMock.write");
            }
            m_mock_logger.log("RingBufferMock.write");
            return m_write_return_value; //BUFFER_OK;
        }
        /**
         *
         *
         */
        public  int writable() {
            return 0;
        }
        /**
         *
         *
         */
        public boolean full() {
              return true;
        }
        /**
         *
         *
         */
        public DataType rptr(int n ) {
            return m_data;
        }
        /**
         *
         *
         */
        public jp.go.aist.rtm.RTC.buffer.ReturnCode advanceRptr(int n) {
            return jp.go.aist.rtm.RTC.buffer.ReturnCode.BUFFER_OK; //BUFFER_OK;
        }
        /**
         *
         *
         */
        public jp.go.aist.rtm.RTC.buffer.ReturnCode get(DataType value) {
            return jp.go.aist.rtm.RTC.buffer.ReturnCode.BUFFER_OK; //BUFFER_OK;
        }
        /**
         *
         *
         */
        public DataType  get() {
            return m_data;
        }
        /**
         *
         *
         */
        public jp.go.aist.rtm.RTC.buffer.ReturnCode read(DataRef<DataType> value,
                              int sec, int nsec) {
            if (m_logger != null) {
                m_logger.log("RingBufferMock.read");
            }
            m_mock_logger.log("RingBufferMock.read");
            return m_read_return_value; //BUFFER_OK;
        }
        /**
         *
         *
         */
        public int readable() {
            return 0;
        }
        /**
         *
         *
         */
        public boolean empty() {
            return true;
        }
        /**
         *
         *
         */
        public void setLogger(Logger logger) {
            m_logger = logger;
        }

        private DataType m_data;
        private Vector<DataType> m_buffer;
        private Logger m_logger;
        private jp.go.aist.rtm.RTC.buffer.ReturnCode m_read_return_value;
        private jp.go.aist.rtm.RTC.buffer.ReturnCode m_write_return_value;
        public static Logger m_mock_logger = null;
  };
    /**
     * 
     *  
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
        public void clearLog() {
            m_log.clear();
        }  
		
       private Vector<String> m_log = new Vector<String>();
    }

