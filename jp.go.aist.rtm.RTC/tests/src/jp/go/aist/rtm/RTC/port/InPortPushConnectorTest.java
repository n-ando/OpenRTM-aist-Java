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

import _SDOPackage.NVListHolder;
import RTC.PortService;

import jp.go.aist.rtm.RTC.BufferFactory;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.ORBUtil;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.buffer.RingBuffer;
import jp.go.aist.rtm.RTC.buffer.CdrRingBuffer;
import jp.go.aist.rtm.RTC.log.Logbuf;


public class InPortPushConnectorTest extends TestCase {
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
    class InPortCorbaCdrConsumerMock extends InPortCorbaCdrConsumer {

        public InPortCorbaCdrConsumerMock() {
            m_logger = null;
        }
        /**
         *
         *
         */
        public void init(Properties prop) {
          if (m_logger != null) {
              m_logger.log("InPortCorbaCdrConsumerMock.init");
          }
        }
        /**
         *
         *
         */
        public ReturnCode put(final byte[] data) {
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
        public boolean subscribeInterface(final _SDOPackage.NVListHolder properties) {
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
        public void setLogger(Logger logger) {
            m_logger = logger;
        }
        private Logger m_logger;

    }
  /**
   * 
   * 
   *
   */
		
    public static Logger m_mock_logger = null;
    private ORB m_orb;
    private POA m_poa;
    protected static Logbuf rtcout = null;;
    protected ConsoleHandler m_stdout;
    protected FileHandler m_fh;

    /**
     * <p> Test initialization </p>
     */
    protected void setUp() throws Exception {
    
        super.setUp();
        if (m_mock_logger == null){
            m_mock_logger = new Logger();
        }
        if (RingBufferMock.m_mock_logger == null){
            RingBufferMock.m_mock_logger = new Logger();
        }

        java.util.Properties props = new java.util.Properties();
        this.m_orb = ORB.init(new String[0], props);
        this.m_poa = org.omg.PortableServer.POAHelper.narrow(
                this.m_orb.resolve_initial_references("RootPOA"));
        this.m_poa.the_POAManager().activate();

        if (rtcout == null){
            rtcout = new Logbuf("Manager");
            m_fh = null; 
            rtcout.setLevel("SILENT");
            String logfile = "InPortPushConnector.log";
            m_fh = new FileHandler(logfile);
            rtcout.addStream(m_fh);
            rtcout.setLevel("PARANOID");

        }

        CdrRingBufferMock.CdrRingBufferMockInit();
    }
		
    /**
     * <p> Test finalization </p>
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        if( m_orb != null) {
            m_orb.destroy();
            m_orb = null;
        }
    }
		
    /**
     * <p> Constructior </p>
     * 
     */
    public void test_InPortPushConnector() {

        rtcout.println(rtcout.PARANOID, "IN  test_InPortPushConnector()");
        RTC.ConnectorProfile prof = new RTC.ConnectorProfile();
        NVListHolder prof_holder = new NVListHolder(prof.properties);
        CORBA_SeqUtil.push_back(prof_holder,
	  		       NVUtil.newNV("dataport.buffer_type",
					     "ring_buffer_mock"));
        prof.properties = prof_holder.value;
        prof.ports = new PortService[2];
        // prop: [port.outport].
        Properties prop = new Properties();
        {
              Properties conn_prop = new Properties();
              NVListHolder holder = new NVListHolder(prof.properties);
              NVUtil.copyToProperties(conn_prop, holder);
              prop.merge(conn_prop.getNode("dataport")); // merge ConnectorProfile
        }
        InPortCorbaCdrProviderMock provider = new InPortCorbaCdrProviderMock();
        Logger logger = new Logger();
        provider.setLogger(logger);
          ConnectorBase.Profile profile_new = new ConnectorBase.Profile(
                                     prof.name,
                                     prof.connector_id,
                                     CORBA_SeqUtil.refToVstring(prof.ports),
                                     prop); 
        InPortConnector connector = null;
        int init_counter = logger.countLog("InPortCorbaCdrProviderMock.init");
        int setBuffer_counter = logger.countLog("InPortCorbaCdrProviderMock.setBuffer");
        int constructor_counter = RingBufferMock.m_mock_logger.countLog("RingBufferMock.Constructor");
          try {
              BufferBase<OutputStream> buffer = null;
              connector = new InPortPushConnector(profile_new, provider,buffer);
              assertEquals("1:",init_counter+1, 
                  logger.countLog("InPortCorbaCdrProviderMock.init"));
              assertEquals("2:",setBuffer_counter+1, 
                  logger.countLog("InPortCorbaCdrProviderMock.setBuffer"));
              assertEquals("3:",constructor_counter+1, 
                  RingBufferMock.m_mock_logger.countLog("RingBufferMock.Constructor"));
          }
          catch(Exception e) {
              fail("The exception not intended was thrown .");
          }


        //When provideris not set, the exception is thrown out. 
        InPortConnector connector_err = null;
        try {
            RTC.ConnectorProfile prof_err = new RTC.ConnectorProfile();
            NVListHolder prof_err_holder = new NVListHolder(prof_err.properties);
            CORBA_SeqUtil.push_back(prof_err_holder,
  			       NVUtil.newNV("",
  					     ""));
            prof_err.properties = prof_err_holder.value;
            prof_err.ports = new PortService[2];
            // prop: [port.outport].
            {
                  Properties conn_prop = new Properties();
                  NVListHolder holder = new NVListHolder(prof_err.properties);
                  NVUtil.copyToProperties(conn_prop, holder);
                  prop.merge(conn_prop.getNode("dataport"));
            }
            ConnectorBase.Profile profile_err = new ConnectorBase.Profile(
                                    prof_err.name,
                                    prof_err.connector_id,
                                    CORBA_SeqUtil.refToVstring(prof_err.ports),
                                    prop); 
            BufferBase<OutputStream> buffer = null;
            connector_err = new InPortPushConnector(profile_err, null, buffer);
            fail("The exception was not thrown. ");
        }
        catch(Exception e) {
            assertTrue(e.getMessage().equals("bad_alloc()"));
        }

        rtcout.println(rtcout.PARANOID, "OUT test_InPortPushConnector()");
    }
    /**
     *  <p> read </p>
     * 
     */
    public void test_read()   throws Exception {
        rtcout.println(rtcout.PARANOID, "IN  test_read()");
        RingBufferMock<OutputStream> pbuffer = new RingBufferMock<OutputStream>();
        RTC.ConnectorProfile prof = new RTC.ConnectorProfile();
        NVListHolder prof_holder = new NVListHolder(prof.properties);
        CORBA_SeqUtil.push_back(prof_holder,
	  		       NVUtil.newNV("dataport.buffer_type",
					     "ring_buffer_mock"));
        prof.properties = prof_holder.value;
        prof.ports = new PortService[2];
        // prop: [port.outport].
        Properties prop = new Properties();
        {
            Properties conn_prop = new Properties();
            NVListHolder holder = new NVListHolder(prof.properties);
            NVUtil.copyToProperties(conn_prop, holder);
            prop.merge(conn_prop.getNode("dataport"));
        }
        InPortCorbaCdrProviderMock provider = new InPortCorbaCdrProviderMock();
        Logger logger = new Logger();
        provider.setLogger(logger);
        ConnectorBase.Profile profile_new = new ConnectorBase.Profile (
                                   prof.name,
                                   prof.connector_id,
                                   CORBA_SeqUtil.refToVstring(prof.ports),
                                   prop); 
        InPortConnector connector= null;
        connector = new InPortPushConnector(profile_new, provider, pbuffer);

        OutputStream cdr = null;
        DataRef<OutputStream> cdrref = new DataRef<OutputStream>(cdr);
        int read_counter =  RingBufferMock.m_mock_logger.countLog("RingBufferMock.read");
        ReturnCode read_ret = connector.read(cdrref);
        assertEquals("1:",read_counter+1, 
               RingBufferMock.m_mock_logger.countLog("RingBufferMock.read"));

        ReturnCode ret;
        pbuffer.set_read_return_value(jp.go.aist.rtm.RTC.buffer.ReturnCode.BUFFER_OK);
        ret = connector.read(cdrref);
        assertTrue("2:",ret.equals(ReturnCode.PORT_OK));

        pbuffer.set_read_return_value(jp.go.aist.rtm.RTC.buffer.ReturnCode.BUFFER_EMPTY);
        ret = connector.read(cdrref);
        assertTrue("3:",ret.equals(ReturnCode.BUFFER_EMPTY));

        pbuffer.set_read_return_value(jp.go.aist.rtm.RTC.buffer.ReturnCode.TIMEOUT);
        ret = connector.read(cdrref);
        assertTrue("4:",ret.equals(ReturnCode.BUFFER_TIMEOUT));
        pbuffer.set_read_return_value(jp.go.aist.rtm.RTC.buffer.ReturnCode.PRECONDITION_NOT_MET);
        ret = connector.read(cdrref);
        assertTrue("5:",ret.equals(ReturnCode.PRECONDITION_NOT_MET));


        rtcout.println(rtcout.PARANOID, "OUT test_read()");
    }
    /**
     * <p> disconnect </p> 
     * 
     */
    public void test_disconnect() throws Exception {

        rtcout.println(rtcout.PARANOID, "IN  test_disconnect()");
        RTC.ConnectorProfile prof = new RTC.ConnectorProfile();
        NVListHolder prof_holder = new NVListHolder(prof.properties);
        CORBA_SeqUtil.push_back(prof_holder,
	  		       NVUtil.newNV("dataport.buffer_type",
					     "ring_buffer_mock"));
        prof.properties = prof_holder.value;
        prof.ports = new PortService[2];
        // prop: [port.outport].
        Properties prop = new Properties();
        {
            Properties conn_prop = new Properties();
            NVListHolder holder = new NVListHolder(prof.properties);
            NVUtil.copyToProperties(conn_prop, holder);
            prop.merge(conn_prop.getNode("dataport"));

        }
        InPortCorbaCdrProviderMock provider = new InPortCorbaCdrProviderMock();
        Logger logger = new Logger();
        provider.setLogger(logger);
        ConnectorBase.Profile profile_new = new ConnectorBase.Profile (
                                   prof.name,
                                   prof.connector_id,
                                   CORBA_SeqUtil.refToVstring(prof.ports),
                                   prop); 
        InPortConnector connector=null;
        BufferBase<OutputStream> buffer = null;
        connector = new InPortPushConnector(profile_new, provider,buffer);

        connector.disconnect();
        OutputStream cdr = null;
        DataRef<OutputStream> cdrref = new DataRef<OutputStream>(cdr);
        ReturnCode ret = connector.read(cdrref);
        assertTrue("1:"+"PRECONDITION_NOT_MET"+"read="+ret,ret.equals(ReturnCode.PRECONDITION_NOT_MET)); 

        rtcout.println(rtcout.PARANOID, "OUT test_disconnect()");

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

            factory.addFactory("ring_buffer_mock",
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
		
       private Vector<String> m_log = new Vector<String>();
    }

