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
import jp.go.aist.rtm.RTC.InPortConsumerFactory;
import jp.go.aist.rtm.RTC.OutPortProviderFactory;
import jp.go.aist.rtm.RTC.PublisherBaseFactory;
import jp.go.aist.rtm.RTC.PeriodicTask;
import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.buffer.RingBuffer;
import jp.go.aist.rtm.RTC.buffer.CdrRingBuffer;
import jp.go.aist.rtm.RTC.port.OutPortBase;
import jp.go.aist.rtm.RTC.port.OutPortProvider;
import jp.go.aist.rtm.RTC.port.publisher.PublisherBase;
import jp.go.aist.rtm.RTC.port.publisher.PublisherNew;
import jp.go.aist.rtm.RTC.port.publisher.PublisherPeriodic;
import jp.go.aist.rtm.RTC.port.publisher.PublisherFlush;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.ORBUtil;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.NVListHolderFactory;
import jp.go.aist.rtm.RTC.log.Logbuf;
import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.buffer.RingBuffer;

import RTC.ConnectorProfileHolder;
import RTC.ReturnCode_t;
import _SDOPackage.NVListHolder;

/**
 * <p>OutPortBaseクラスのためのテストケースです。</p>
 */
public class OutPortBaseTest extends TestCase {

  class OutPortPushConnector extends OutPortConnector {
    public OutPortPushConnector(Profile profile,
                         InPortConsumer consumer,
                         BufferBase<OutputStream> buffer) throws Exception {
        super(profile);
        try {
            _Constructor(profile,consumer,buffer);
        }
        catch(Exception e) {
            throw new Exception("bad_alloc()");
        } 
    }

    public OutPortPushConnector(Profile profile,
                         InPortConsumer consumer )  throws Exception {
        super(profile);
        BufferBase<OutputStream> buffer = null;
        try {
            _Constructor(profile,consumer,buffer);
        }
        catch(Exception e) {
            throw new Exception("bad_alloc()");
        } 
    }

    private void _Constructor(Profile profile,
                         InPortConsumer consumer,
                         BufferBase<OutputStream> buffer) throws Exception {

        if(profile.properties.getProperty("OutPortBaseTests").equals("bad_alloc")) {
            throw new Exception("bad_alloc()");
        }
    }
    public ReturnCode disconnect() {
        return ReturnCode.PORT_OK;
    }
    public BufferBase<OutputStream> getBuffer() {
        return new RingBufferMock();
    }
    public void activate() {
        m_mock_logger.log("OutPortPushConnector.activate"); 
    }
    public void deactivate() {
        m_mock_logger.log("OutPortPushConnector.deactivate"); 
    }
    public ReturnCode write(final OutputStream data_little,final OutputStream data_big){
        return ReturnCode.PORT_OK;
    }
    protected PublisherBase createPublisher(Profile profile) {
        return new PublisherFlush(); 
    }
    protected BufferBase<OutputStream> createBuffer(Profile profile) {
      return new RingBufferMock();
    }
    public <DataType> ReturnCode write(final DataType data) {
        return ReturnCode.PORT_OK;
    }
    public void setOutPortBase(OutPortBase outportbase){
    } 

  }
  class OutPortPullConnector extends OutPortConnector {

    public OutPortPullConnector(Profile profile,
                         OutPortProvider provider,
                         BufferBase<OutputStream> buffer) throws Exception {
        super(profile);
        if(profile.properties.getProperty("OutPortBaseTests").equals("bad_alloc")) {
            throw new Exception("bad_alloc()");
        }
    }
    public OutPortPullConnector(Profile profile,
                         OutPortProvider provider )  throws Exception {
        super(profile);
        if(profile.properties.getProperty("OutPortBaseTests").equals("bad_alloc")) {
            throw new Exception("bad_alloc()");
        }
    }
    public ReturnCode write(final OutputStream data_little,final OutputStream data_big){
        return ReturnCode.PORT_OK;
    }
    public BufferBase<OutputStream> getBuffer() {
        return new RingBufferMock();
    }
    public ReturnCode disconnect() {
        return ReturnCode.PORT_OK;
    }
    public void deactivate() {
    }
    public void activate() {
    }
    public void setOutPortBase(OutPortBase outportbase){
    } 
    public <DataType> ReturnCode write(final DataType data) {
        return ReturnCode.PORT_OK;
    }
  }


    public static <DataType> String toTypeCode(DataRef<DataType> value) { 
        DataType data = value.v;
        String typeName = value.v.getClass().getSimpleName();
        return typeName;

    }
  /**
   *
   *
   *
   */
  class InPortMock<DataType> extends InPortBase {
    /**
     * <p> toTypeCode </p>
     * <p> This function gets TypeCode of data. </p>
     *
     * @param value data
     * @return TypeCdoe(String)
     */
/*
    private static <DataType> String toTypeCode(DataRef<DataType> value) { 
        DataType data = value.v;
        String typeName = value.v.getClass().getSimpleName();
        return typeName;

    }
*/
    public InPortMock(BufferBase<DataType> superClass,
            final String name, DataRef<DataType> value,
            boolean read_block, boolean write_block,
            long read_timeout, long write_timeout) {
        super(name, toTypeCode(value));
    }
    public InPortMock(final String name, DataRef<DataType> value) {
        this( new RingBuffer<DataType>(8), name, value, false, false, 0, 0);
    }
    /**
     * 
     */
    public ReturnCode_t publishInterfaces_public(ConnectorProfileHolder cprof) {
        return publishInterfaces(cprof);
     } 
    /**
     * 
     */
    public ReturnCode_t subscribeInterfaces_public(
            final ConnectorProfileHolder cprof) {
        return subscribeInterfaces(cprof);
     } 
  };
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

    /**
     * <p> creator_ </p>
     * 
     * @return Object Created instances
     *
     */
    public OutPortProvider creator_() {
        return new OutPortCorbaCdrProviderMock();
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

    /**
     * <p> creator_ </p>
     * 
     * @return Object Created instances
     *
     */
    public InPortConsumer creator_() {
        return new InPortCorbaCdrConsumerMock();
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
//        public static Logger m_mock_logger = null;
  };
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

    }

        public void CdrRingBufferMockInit() {
            final BufferFactory<RingBufferMock<OutputStream>,String> factory 
                = BufferFactory.instance();

            factory.addFactory("ring_buffer",
                        new CdrRingBufferMock(),
                        new CdrRingBufferMock());
    
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
        CdrRingBufferMockInit();
//        PeriodicTask.PeriodicTaskInit();
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
    /**
     * @brief connectors(),getConnectorProfiles()
     * 
     */
    public void test_connectors_getConnectorXX()
    {
        RTC.TimedDouble inbindValue = new RTC.TimedDouble();
        DataRef<RTC.TimedDouble> ref 
            = new DataRef<RTC.TimedDouble>(inbindValue);
        InPortMock<RTC.TimedDouble> inPort 
            = new InPortMock<RTC.TimedDouble>("in:OutPortBaseTest",ref);

        OutPortBaseMock outPort 
            = new OutPortBaseMock("OutPortBaseTest", "TimedDouble");

        PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
        portAdmin.registerPort(outPort); 
        portAdmin.registerPort(inPort); 

        RTC.ConnectorProfile inprof = new RTC.ConnectorProfile();
        inprof.ports = new RTC.PortService[1];
        inprof.ports[0] = outPort.get_port_profile().port_ref;
        NVListHolder holder = new NVListHolder(inprof.properties);
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.interface_type",
                                 "corba_cdr"));
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.dataflow_type",
                                 "push"));
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.subscription_type",
                                 "flush"));
        inprof.properties = holder.value;
        inprof.connector_id = "id0";
        inprof.name = "bar";
        inPort.init();
        ConnectorProfileHolder profh =  new ConnectorProfileHolder(inprof);
        inPort.publishInterfaces_public(profh);

        String[] vstrid = {"id0","id1","id2","id3","id4",
                                  "id5","id6","id7","id8","id9"};
        String[] vstrname = {"foo0","foo1","foo2","foo3","foo4",
                                    "foo5","foo6","foo7","foo8","foo9"};

        String[] vstrinterface = {"corba_cdr","corba_cdr","corba_cdr",
                                         "corba_cdr","corba_cdr","corba_cdr",
                                         "corba_cdr","corba_cdr","corba_cdr",
                                         "corba_cdr"};
        String[] vstrdataflow = {"push","push","push",
                                         "push","push","push",
                                         "pull","pull","pull","pull"};

        String[] vstrsubscription = {"flush","flush","flush",
                                            "flush","flush","flush",
                                            "flush","flush","flush","flush"};
        
        //
        //connectors()
        //
        for(int ic=0;ic<10;++ic)
        {
            RTC.ConnectorProfile prof = new RTC.ConnectorProfile();
            prof.ports = new RTC.PortService[1];
            prof.ports[0] = outPort.get_port_profile().port_ref;
            NVListHolder nholder = new NVListHolder(prof.properties);
            CORBA_SeqUtil.push_back(nholder,
                                     NVUtil.newNV("dataport.interface_type",
                                                   vstrinterface[ic]));
            CORBA_SeqUtil.push_back(nholder,
                                     NVUtil.newNV("dataport.dataflow_type",
                                                   vstrdataflow[ic]));
            CORBA_SeqUtil.push_back(nholder,
                                     NVUtil.newNV("dataport.subscription_type",
                                                   vstrsubscription[ic]));
            CORBA_SeqUtil.push_back(nholder,
                                     NVUtil.newNV("dataport.corba_cdr.inport_ior",
                                                   NVUtil.toString(new NVListHolder(inprof.properties),"dataport.corba_cdr.inport_ior")));
            prof.properties = nholder.value;
            prof.connector_id = vstrid[ic];
            prof.name = vstrname[ic];


            Properties prop = new Properties(outPort.properties());
            Properties conn_prop = new Properties();
            nholder = new NVListHolder(prof.properties);
            NVUtil.copyToProperties(conn_prop, nholder);
            prop.merge(conn_prop.getNode("dataport")); // merge ConnectorProfile
            ConnectorProfileHolder cprofh =  new ConnectorProfileHolder(prof);
            OutPortProvider provider = outPort.createProvider_public(cprofh, prop);
            outPort.createConnector_public(cprofh,prop,provider);
//            outPort.publishInterfaces_public(prof);

            Vector<OutPortConnector> objs = outPort.connectors();

            assertEquals("1:",(ic+1), objs.size());
            assertTrue("2:",vstrid[ic].equals(objs.elementAt(ic).id()));
            assertTrue("3:",vstrname[ic].equals(objs.elementAt(ic).name()));
        }


        //
        //getConnectorProfiles()
        //
        Vector<ConnectorBase.Profile> list = outPort.getConnectorProfiles();
        assertEquals(10, list.size());
        for(int ic=0;ic<10;++ic)
        {
            assertTrue("4:",vstrid[ic].equals(list.elementAt(ic).id));
            assertTrue("5:",vstrname[ic].equals(list.elementAt(ic).name));
            assertEquals("6:",1, list.elementAt(ic).ports.size());
            
            assertTrue("7:",vstrinterface[ic].equals(
                                 list.elementAt(ic).properties.getProperty("interface_type")));
            assertTrue("8:",vstrdataflow[ic].equals(
                                 list.elementAt(ic).properties.getProperty("dataflow_type")));
            assertTrue("9:",vstrsubscription[ic].equals(
                                 list.elementAt(ic).properties.getProperty("subscription_type")));

        }

        //
        //getConnectorIds()
        //
        Vector<String> ids = outPort.getConnectorIds();
        assertEquals(10, ids.size());
        for(int ic=0;ic<10;++ic)
        {
            assertTrue("10:",vstrid[ic].equals(ids.elementAt(ic)));
        }

        //
        //getConnectorNames()
        //
        Vector<String> names = outPort.getConnectorNames();
        assertEquals(10, names.size());
        for(int ic=0;ic<10;++ic)
        {
            assertTrue("11:",vstrname[ic].equals(names.elementAt(ic)));
        }

        //
        //getConnectorProfileById()
        //
        for(int ic=0;ic<10;++ic)
        {

            Vector<String> vstr = new Vector<String>();
            Properties prop = new Properties();
            
            ConnectorBase.Profile prof = new ConnectorBase.Profile("test","id",
                                                vstr,prop);
            ConnectorBase.ProfileHolder cbprofh 
                = new ConnectorBase.ProfileHolder(prof);
            boolean ret = outPort.getConnectorProfileById(vstrid[ic],cbprofh);
            prof = cbprofh.value;
            assertTrue("12:",ret);
            assertTrue("13:",vstrinterface[ic].equals(
                              prof.properties.getProperty("interface_type")));
            assertTrue("14:",vstrdataflow[ic].equals(
                                 prof.properties.getProperty("dataflow_type")));
            assertTrue("15:",vstrsubscription[ic].equals(
                                 prof.properties.getProperty("subscription_type")));
        }
        {
            Vector<String> vstr = new Vector<String>();
            Properties prop = new Properties();
            
            ConnectorBase.Profile prof = new ConnectorBase.Profile ("test","id",
                                             vstr,prop);
            ConnectorBase.ProfileHolder cbprofh 
                = new ConnectorBase.ProfileHolder(prof);
            boolean ret = outPort.getConnectorProfileById("foo",cbprofh);
            assertTrue("16:",!ret);
            ret = outPort.getConnectorProfileById("bar",cbprofh);
            assertTrue("17:",!ret);
        }

        //
        //getConnectorProfileByiName()
        //
        for(int ic=0;ic<10;++ic)
        {
            Vector<String> vstr = new Vector<String>();
            Properties prop = new Properties();
            
            ConnectorBase.Profile prof = new ConnectorBase.Profile("test","id",
                                                                     vstr,prop);
            ConnectorBase.ProfileHolder cbprofh 
                = new ConnectorBase.ProfileHolder(prof);
            boolean ret = outPort.getConnectorProfileByName(vstrname[ic],
                                                         cbprofh);
            prof = cbprofh.value;
            assertTrue("18:",ret);
            assertTrue("19:",vstrinterface[ic].equals(
                                 prof.properties.getProperty("interface_type")));
            assertTrue("20:",vstrdataflow[ic].equals(
                                 prof.properties.getProperty("dataflow_type")));
            assertTrue("21:",vstrsubscription[ic].equals(
                                 prof.properties.getProperty("subscription_type")));
        }
        {
            Vector<String> vstr = new Vector<String>();
            Properties prop = new Properties();
            
            ConnectorBase.Profile prof = new ConnectorBase.Profile("test","id",
                                                                    vstr,prop);
            ConnectorBase.ProfileHolder cbprofh 
                = new ConnectorBase.ProfileHolder(prof);
            boolean ret = outPort.getConnectorProfileByName("foo",cbprofh);
            prof = cbprofh.value;
            assertTrue("22:",!ret);
            ret = outPort.getConnectorProfileByName("bar",cbprofh);
            assertTrue("23:",!ret);
        }

/*
        //
        //publishInterfaceProfiles()
        //
        {
            NVListHolder properties = NVListHolderFactory.create();
            boolean ret = outPort.publishInterfaceProfiles(properties);
            assertTrue(ret);
            {
                String value;
                try {
                    assertEquals("24:","corba_cdr",
                    NVUtil.find(properties, 
                          "dataport.data_type").extract_wstring());
                }
                catch(Exception e) {
	            fail("dataport.data_type failure.");
                }
            }
        }
*/
        portAdmin.deletePort(outPort);
        portAdmin.deletePort(inPort);
    }
    /**
     * @brief activateInterfaces(),deactivateInterfaces()
     * 
     */
/*
    public void test_activateInterfaces_deactivateInterfaces()
    {
        RTC.TimedDouble inbindValue = new RTC.TimedDouble();
        DataRef<RTC.TimedDouble> ref = new DataRef<RTC.TimedDouble>(inbindValue);
        InPortMock<RTC.TimedDouble> inPort = new InPortMock<RTC.TimedDouble>("in:OutPortBaseTest",ref);

        OutPortBaseMock outPort = new OutPortBaseMock("out:OutPortBaseTest", "RTC.TimedDouble");

        PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
        portAdmin.registerPort(outPort); 
        portAdmin.registerPort(inPort); 

        RTC.ConnectorProfile inprof = new RTC.ConnectorProfile();
        inprof.ports = new RTC.PortService[1];
        inprof.ports[0] = outPort.get_port_profile().port_ref;
        NVListHolder holder = new NVListHolder(inprof.properties);
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.interface_type",
                                 "corba_cdr"));
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.dataflow_type",
                                 "push"));
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.subscription_type",
                                 "flush"));
        inprof.properties = holder.value;
        inprof.connector_id = "id0";
        inprof.name = "bar";
        inPort.init();
        ConnectorProfileHolder profh =  new ConnectorProfileHolder(inprof);
        inPort.publishInterfaces_public(profh);

        String[] vstrid = {"id0","id1","id2","id3","id4",
                                  "id5","id6","id7","id8","id9"};
        String[] vstrname = {"foo0","foo1","foo2","foo3","foo4",
                                    "foo5","foo6","foo7","foo8","foo9"};

        String[] vstrinterface = {"corba_cdr","corba_cdr","corba_cdr",
                                         "corba_cdr","corba_cdr","corba_cdr",
                                         "corba_cdr","corba_cdr","corba_cdr",
                                         "corba_cdr"};
        String[] vstrdataflow = {"push","push","push",
                                         "push","push","push",
                                         "push","push","push","push"};

        String[] vstrsubscription = {"flush","flush","flush",
                                            "flush","flush","flush",
                                            "flush","flush","flush","flush"};
        //
        //
        for(int ic=0;ic<10;++ic)
        {
            RTC.ConnectorProfile prof = new RTC.ConnectorProfile();
            prof.ports = new RTC.PortService[1];
            prof.ports[0] = outPort.get_port_profile().port_ref;
            NVListHolder nholder = new NVListHolder(prof.properties);
            CORBA_SeqUtil.push_back(nholder,
                                     NVUtil.newNV("dataport.interface_type",
                                                   vstrinterface[ic]));
            CORBA_SeqUtil.push_back(nholder,
                                     NVUtil.newNV("dataport.dataflow_type",
                                                   vstrdataflow[ic]));
            CORBA_SeqUtil.push_back(nholder,
                                     NVUtil.newNV("dataport.subscription_type",
                                                   vstrsubscription[ic]));
            CORBA_SeqUtil.push_back(nholder,
                                     NVUtil.newNV("dataport.corba_cdr.inport_ior",
                                                   NVUtil.toString(new NVListHolder(inprof.properties),"dataport.corba_cdr.inport_ior")));
            prof.properties = nholder.value;
            prof.connector_id = vstrid[ic];
            prof.name = vstrname[ic];


            ConnectorProfileHolder cprofh =  new ConnectorProfileHolder(prof);
            outPort.subscribeInterfaces_public(cprofh);

        }
        int logcnt;
        logcnt = m_mock_logger.countLog("OutPortPushConnector.activate"); 
        outPort.activateInterfaces();
        assertEquals("1:",logcnt+10,
                  m_mock_logger.countLog("OutPortPushConnector.activate"));


        logcnt = m_mock_logger.countLog("OutPortPushConnector.deactivate"); 
        outPort.deactivateInterfaces();
        assertEquals("2:",logcnt+10,
                  m_mock_logger.countLog("OutPortPushConnector.deactivate"));

        portAdmin.deletePort(outPort);
        portAdmin.deletePort(inPort);

    }
*/
    /**
     * @brief publishInterfaces()
     * 
     */
    public void test_publishInterfaces()
    {
        //
        if( OutPortProviderFactory.instance().hasFactory("corba_cdr") )
        {
            OutPortProviderFactory.instance().removeFactory("corba_cdr");
        }
        //
        OutPortProviderFactory.instance().addFactory("corba_cdr",
                    new OutPortCorbaCdrProviderMock(),
                    new OutPortCorbaCdrProviderMock());

        //
        if( InPortConsumerFactory.instance().hasFactory("corba_cdr") )
        {
            InPortConsumerFactory.instance().removeFactory("corba_cdr");
        }
        //
        InPortConsumerFactory.instance().
        addFactory("corba_cdr",
                    new InPortCorbaCdrConsumerMock(),
                    new InPortCorbaCdrConsumerMock());


        OutPortBaseMock outport = new OutPortBaseMock("OutPortBaseTest", 
                                "RTC.TimedFloat");

        PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
        portAdmin.registerPort(outport); 
        RTC.ConnectorProfile prof = new RTC.ConnectorProfile();
        prof.connector_id = "id0";
        prof.name = "OutPortBaseTest0";
        prof.ports = new RTC.PortService[1];
        prof.ports[0] = outport.get_port_profile().port_ref;
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
        assertEquals(0,outport.get_m_connectors().size());
        ConnectorProfileHolder profh =  new ConnectorProfileHolder(prof);
        retcode = outport.publishInterfaces_public(profh);
        assertEquals(1,outport.get_m_connectors().size());
        assertEquals(ReturnCode_t.RTC_OK,retcode);

        prof.connector_id = "id1";
        prof.name = "OutPortBaseTest1";
        profh =  new ConnectorProfileHolder(prof);
        retcode = outport.publishInterfaces_public(profh);
        assertEquals(2,(int)outport.get_m_connectors().size());
        assertEquals(ReturnCode_t.RTC_OK,retcode);

        portAdmin.deletePort(outport);
    }
    /**
     * @brief publishInterfaces()
     * 
     */
    public void test_publishInterfaces2()
    {
        //
        //
        //

        //
        if( OutPortProviderFactory.instance().hasFactory("corba_cdr") )
        {
            OutPortProviderFactory.instance().removeFactory("corba_cdr");
        }
        //
        OutPortProviderFactory.instance().addFactory("corba_cdr",
                    new OutPortCorbaCdrProviderMock(),
                    new OutPortCorbaCdrProviderMock());

        //
        if( InPortConsumerFactory.instance().hasFactory("corba_cdr") )
        {
            InPortConsumerFactory.instance().removeFactory("corba_cdr");
        }
        //
        InPortConsumerFactory.instance().
        addFactory("corba_cdr",
                    new InPortCorbaCdrConsumerMock(),
                    new InPortCorbaCdrConsumerMock());

        OutPortBaseMock outport = new OutPortBaseMock ("OutPortBaseTest", 
                                "RTC.TimedFloat");

        PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
        portAdmin.registerPort(outport); 
        RTC.ConnectorProfile prof = new RTC.ConnectorProfile();
        prof.connector_id = "id0";
        prof.name = "OutPortBaseTest0";
        prof.ports = new RTC.PortService[1];
        prof.ports[0] = outport.get_port_profile().port_ref;
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
        assertEquals(0,outport.get_m_connectors().size());
        ConnectorProfileHolder profh =  new ConnectorProfileHolder(prof);
        retcode = outport.publishInterfaces_public(profh);
        assertEquals(0,outport.get_m_connectors().size());
        assertEquals(ReturnCode_t.RTC_OK,retcode);


        portAdmin.deletePort(outport);
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
        if( OutPortProviderFactory.instance().hasFactory("corba_cdr") )
        {
            OutPortProviderFactory.instance().removeFactory("corba_cdr");
        }
        //
        OutPortProviderFactory.instance().addFactory("corba_cdr",
                    new OutPortCorbaCdrProviderMock(),
                    new OutPortCorbaCdrProviderMock());

        //
        if( InPortConsumerFactory.instance().hasFactory("corba_cdr") )
        {
            InPortConsumerFactory.instance().removeFactory("corba_cdr");
        }
        //
        InPortConsumerFactory.instance().
        addFactory("corba_cdr",
                    new InPortCorbaCdrConsumerMock(),
                    new InPortCorbaCdrConsumerMock());


        OutPortBaseMock outport = new OutPortBaseMock("OutPortBaseTest", 
                                "RTC.TimedFloat");

        PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
        portAdmin.registerPort(outport); 
        RTC.ConnectorProfile prof = new RTC.ConnectorProfile();
        prof.connector_id = "id0";
        prof.name = "OutPortBaseTest0";
        prof.ports = new RTC.PortService[1];
        prof.ports[0] = outport.get_port_profile().port_ref;
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
        assertEquals(0,outport.get_m_connectors().size());
        ConnectorProfileHolder profh =  new ConnectorProfileHolder(prof);
        retcode = outport.publishInterfaces_public(profh);
        assertEquals(0,outport.get_m_connectors().size());
        assertEquals(ReturnCode_t.BAD_PARAMETER,retcode);


        portAdmin.deletePort(outport);
    }
    /**
     * @brief publishInterfaces()
     * 
     */
    public void test_publishInterfaces4()
    {
        //
        //
        //

        //
        if( OutPortProviderFactory.instance().hasFactory("corba_cdr") )
        {
            OutPortProviderFactory.instance().removeFactory("corba_cdr");
        }

        //
        if( InPortConsumerFactory.instance().hasFactory("corba_cdr") )
        {
            InPortConsumerFactory.instance().removeFactory("corba_cdr");
        }
        //
        InPortConsumerFactory.instance().
        addFactory("corba_cdr",
                    new InPortCorbaCdrConsumerMock(),
                    new InPortCorbaCdrConsumerMock());

        OutPortBaseMock outport= new OutPortBaseMock("OutPortBaseTest", 
                                "RTC.TimedFloat");

        PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
        portAdmin.registerPort(outport); 
        RTC.ConnectorProfile prof = new RTC.ConnectorProfile();
        prof.connector_id = "id0";
        prof.name = ("OutPortBaseTest0");
        prof.ports = new RTC.PortService[1];
        prof.ports[0] = outport.get_port_profile().port_ref;
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
        assertEquals(0,outport.get_m_connectors().size());
        ConnectorProfileHolder profh =  new ConnectorProfileHolder(prof);
        retcode = outport.publishInterfaces_public(profh);
        assertEquals(0,outport.get_m_connectors().size());
        assertEquals(ReturnCode_t.BAD_PARAMETER,retcode);


        portAdmin.deletePort(outport);
    }
    /**
     * @brief publishInterfaces()
     * 
     */
/*
    public void test_publishInterfaces5()
    {
        //
        if( OutPortProviderFactory.instance().hasFactory("corba_cdr") )
        {
            OutPortProviderFactory.instance().removeFactory("corba_cdr");
        }
        //
        OutPortProviderFactory.instance().addFactory("corba_cdr",
                    new OutPortCorbaCdrProviderMock(),
                    new OutPortCorbaCdrProviderMock());

        //
        if( InPortConsumerFactory.instance().hasFactory("corba_cdr") )
        {
            InPortConsumerFactory.instance().removeFactory("corba_cdr");
        }
        //
        InPortConsumerFactory.instance().
        addFactory("corba_cdr",
                    new InPortCorbaCdrConsumerMock(),
                    new InPortCorbaCdrConsumerMock());


        OutPortBaseMock outport = new OutPortBaseMock("OutPortBaseTest", 
                                "RTC.TimedFloat");

        PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
        portAdmin.registerPort(outport);

        RTC.ConnectorProfile prof = new RTC.ConnectorProfile();
        prof.connector_id = "id0";
        prof.name = ("OutPortBaseTest0");
        prof.ports = new RTC.PortService[1];
        prof.ports[0] = outport.get_port_profile().port_ref;
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
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.OutPortBaseTests",
                                 "bad_alloc"));
        prof.properties = holder.value;
        RTC.ReturnCode_t retcode;
        assertEquals("1:",0,outport.get_m_connectors().size());
        ConnectorProfileHolder profh =  new ConnectorProfileHolder(prof);
        retcode = outport.publishInterfaces_public(profh);
        assertEquals("2:",0,outport.get_m_connectors().size());
        assertEquals("3:",ReturnCode_t.RTC_ERROR,retcode);

        portAdmin.deletePort(outport);
    }
*/
    /**
     * @brief subscribeInterfaces()
     * 
     */
    public void test_subscribeInterfaces()
    {
        //
        if( OutPortProviderFactory.instance().hasFactory("corba_cdr") )
        {
            OutPortProviderFactory.instance().removeFactory("corba_cdr");
        }
        //
        OutPortProviderFactory.instance().addFactory("corba_cdr",
                    new OutPortCorbaCdrProviderMock(),
                    new OutPortCorbaCdrProviderMock());

        //
        if( InPortConsumerFactory.instance().hasFactory("corba_cdr") )
        {
            InPortConsumerFactory.instance().removeFactory("corba_cdr");
        }
        //
        InPortConsumerFactory.instance().
        addFactory("corba_cdr",
                    new InPortCorbaCdrConsumerMock(),
                    new InPortCorbaCdrConsumerMock());


        OutPortBaseMock outport = new OutPortBaseMock("OutPortBaseTest", 
                                "RTC.TimedFloat");

        PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
        portAdmin.registerPort(outport); 

        RTC.ConnectorProfile prof = new RTC.ConnectorProfile();
        prof.connector_id = "id0";
        prof.name = ("OutPortBaseTest0");
        prof.ports = new RTC.PortService[1];
        prof.ports[0] = outport.get_port_profile().port_ref;
        NVListHolder holder = new NVListHolder(prof.properties);
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.interface_type",
                                 "corba_cdr"));
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.dataflow_type",
                                 "push"));
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.subscription_type",
                                 "flush"));
//                                 "new"));
        prof.properties = holder.value;
        RTC.ReturnCode_t retcode;
        assertEquals(0,outport.get_m_connectors().size());
        ConnectorProfileHolder profh =  new ConnectorProfileHolder(prof);
        retcode = outport.subscribeInterfaces_public(profh);
        assertEquals("1:",1,outport.get_m_connectors().size());
        assertEquals("2:",ReturnCode_t.RTC_OK,retcode);

        prof.connector_id = "id1";
        prof.name = ("OutPortBaseTest1");
        profh =  new ConnectorProfileHolder(prof);
        retcode = outport.subscribeInterfaces_public(profh);
        assertEquals("3:",2,outport.get_m_connectors().size());
        assertEquals("4:",ReturnCode_t.RTC_OK,retcode);

        portAdmin.deletePort(outport);
    }
    /*!
     * @brief subscribeInterfaces()
     * 
     */
    public void test_subscribeInterfaces2()
    {
        //
        if( OutPortProviderFactory.instance().hasFactory("corba_cdr") )
        {
            OutPortProviderFactory.instance().removeFactory("corba_cdr");
        }
        //
        OutPortProviderFactory.instance().addFactory("corba_cdr",
                    new OutPortCorbaCdrProviderMock(),
                    new OutPortCorbaCdrProviderMock());

        //
        if( InPortConsumerFactory.instance().hasFactory("corba_cdr") )
        {
            InPortConsumerFactory.instance().removeFactory("corba_cdr");
        }
        //
        InPortConsumerFactory.instance().
        addFactory("corba_cdr",
                    new InPortCorbaCdrConsumerMock(),
                    new InPortCorbaCdrConsumerMock());


        OutPortBaseMock outport = new OutPortBaseMock("OutPortBaseTest", 
                                "RTC.TimedFloat");

        PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
        portAdmin.registerPort(outport); 

        RTC.ConnectorProfile prof = new RTC.ConnectorProfile();
        prof.connector_id = "id0";
        prof.name = ("OutPortBaseTest0");
        prof.ports = new RTC.PortService[1];
        prof.ports[0] = outport.get_port_profile().port_ref;
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
        assertEquals(0,outport.get_m_connectors().size());
        ConnectorProfileHolder profh =  new ConnectorProfileHolder(prof);
        retcode = outport.subscribeInterfaces_public(profh);
        assertEquals(0,outport.get_m_connectors().size());
        assertEquals(ReturnCode_t.RTC_OK,retcode);

        portAdmin.deletePort(outport);
    }
    /**
     * @brief subscribeInterfaces()
     * 
     */
    public void test_subscribeInterfaces3()
    {
        //
        if( OutPortProviderFactory.instance().hasFactory("corba_cdr") )
        {
            OutPortProviderFactory.instance().removeFactory("corba_cdr");
        }
        //
        OutPortProviderFactory.instance().addFactory("corba_cdr",
                    new OutPortCorbaCdrProviderMock(),
                    new OutPortCorbaCdrProviderMock());

        //
        if( InPortConsumerFactory.instance().hasFactory("corba_cdr") )
        {
            InPortConsumerFactory.instance().removeFactory("corba_cdr");
        }
        //
        InPortConsumerFactory.instance().
        addFactory("corba_cdr",
                    new InPortCorbaCdrConsumerMock(),
                    new InPortCorbaCdrConsumerMock());


        OutPortBaseMock outport = new OutPortBaseMock("OutPortBaseTest", 
                                "RTC.TimedFloat");

        PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
        portAdmin.registerPort(outport); 

        RTC.ConnectorProfile prof = new RTC.ConnectorProfile();
        prof.connector_id = "id0";
        prof.name = ("OutPortBaseTest0");
        prof.ports = new RTC.PortService[1];
        prof.ports[0] = outport.get_port_profile().port_ref;
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
        assertEquals(0,outport.get_m_connectors().size());
        ConnectorProfileHolder profh =  new ConnectorProfileHolder(prof);
        retcode = outport.subscribeInterfaces_public(profh);
        assertEquals(0,outport.get_m_connectors().size());
        assertEquals(ReturnCode_t.BAD_PARAMETER,retcode);

        portAdmin.deletePort(outport);
    }
    /**
     * @brief subscribeInterfaces()
     * 
     */
    public void test_subscribeInterfaces4()
    {
        //
        if( OutPortProviderFactory.instance().hasFactory("corba_cdr") )
        {
            OutPortProviderFactory.instance().removeFactory("corba_cdr");
        }
        //
        OutPortProviderFactory.instance().addFactory("corba_cdr",
                    new OutPortCorbaCdrProviderMock(),
                    new OutPortCorbaCdrProviderMock());

        //
        if( InPortConsumerFactory.instance().hasFactory("corba_cdr") )
        {
            InPortConsumerFactory.instance().removeFactory("corba_cdr");
        }


        OutPortBaseMock outport = new OutPortBaseMock("OutPortBaseTest", 
                                "RTC.TimedFloat");

        PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
        portAdmin.registerPort(outport); 

        RTC.ConnectorProfile prof = new RTC.ConnectorProfile();
        prof.connector_id = "id0";
        prof.name = ("OutPortBaseTest0");
        prof.ports = new RTC.PortService[1];
        prof.ports[0] = outport.get_port_profile().port_ref;
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
        assertEquals(0,outport.get_m_connectors().size());
        ConnectorProfileHolder profh =  new ConnectorProfileHolder(prof);
        retcode = outport.subscribeInterfaces_public(profh);
        assertEquals(0,outport.get_m_connectors().size());
        assertEquals(ReturnCode_t.BAD_PARAMETER,retcode);

        portAdmin.deletePort(outport);
    }
    /**
     * @brief subscribeInterfaces()
     * 
     */
    public void test_subscribeInterfaces5()
    {
        //
        if( OutPortProviderFactory.instance().hasFactory("corba_cdr") )
        {
            OutPortProviderFactory.instance().removeFactory("corba_cdr");
        }
        //
        OutPortProviderFactory.instance().addFactory("corba_cdr",
                    new OutPortCorbaCdrProviderMock(),
                    new OutPortCorbaCdrProviderMock());

        //
        if( InPortConsumerFactory.instance().hasFactory("corba_cdr") )
        {
            InPortConsumerFactory.instance().removeFactory("corba_cdr");
        }
        //
        InPortConsumerFactory.instance().
        addFactory("corba_cdr",
                    new InPortCorbaCdrConsumerMock(),
                    new InPortCorbaCdrConsumerMock());


        OutPortBaseMock outport = new OutPortBaseMock("OutPortBaseTest", 
                                "RTC.TimedFloat");

        PortAdmin portAdmin = new PortAdmin(m_orb,m_poa);
        portAdmin.registerPort(outport); 

        RTC.ConnectorProfile prof = new RTC.ConnectorProfile();
        prof.connector_id = "id0";
        prof.name = ("OutPortBaseTest0");
        prof.ports = new RTC.PortService[1];
        prof.ports[0] = outport.get_port_profile().port_ref;
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
        CORBA_SeqUtil.push_back(holder,
                                 NVUtil.newNV("dataport.OutPortBaseTests",
                                 "bad_alloc"));
        prof.properties = holder.value;
        RTC.ReturnCode_t retcode;
        assertEquals("1:",0,outport.get_m_connectors().size());
        ConnectorProfileHolder profh =  new ConnectorProfileHolder(prof);
        retcode = outport.subscribeInterfaces_public(profh);
        assertEquals("2:",0,outport.get_m_connectors().size());
        assertEquals("3:",ReturnCode_t.RTC_ERROR,retcode);

        portAdmin.deletePort(outport);
    }
}
