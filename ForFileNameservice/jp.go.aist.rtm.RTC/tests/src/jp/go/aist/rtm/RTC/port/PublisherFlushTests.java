package jp.go.aist.rtm.RTC.port;

import java.util.Vector;

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;


import OpenRTM.CdrDataHolder;

import jp.go.aist.rtm.RTC.buffer.RingBuffer;
import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.port.publisher.PublisherFlush;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.ORBUtil;

import junit.framework.TestCase;
import _SDOPackage.NVListHolder;

/**
 * <p>PublisherFlushクラスのためのテストケースです。</p>
 */
public class PublisherFlushTests extends TestCase {

  class InPortCorbaCdrConsumerMock extends InPortCorbaCdrConsumer {
      /**
       * 
       * 
       */
      public InPortCorbaCdrConsumerMock() {
          m_buffer = new RingBuffer<OutputStream>();
      }
      /**
       * 
       * 
       */
      public ReturnCode put(final OutputStream data) {
          if (m_buffer.full()) {
               return ReturnCode.BUFFER_FULL;
           }



           jp.go.aist.rtm.RTC.buffer.ReturnCode ret = m_buffer.write(data);

           return convertReturn(ret);
      }
      /**
       * <p> convertReturn </p>
       *
       */
      protected ReturnCode 
      convertReturn(jp.go.aist.rtm.RTC.buffer.ReturnCode status) {
          switch (status) {
              case BUFFER_OK:
                  return ReturnCode.PORT_OK;
              case BUFFER_EMPTY:
                  return ReturnCode.BUFFER_EMPTY;
              case TIMEOUT:
                  return ReturnCode.BUFFER_TIMEOUT;
              case PRECONDITION_NOT_MET:
                  return ReturnCode.PRECONDITION_NOT_MET;
              default:
                  return ReturnCode.PORT_ERROR;
          }
      }
      /**
       * 
       * 
       */
      public OutputStream get_m_put_data() {
          org.omg.CORBA.Any any = ORBUtil.getOrb().create_any();
          OutputStream cdr = any.create_output_stream();
          DataRef<OutputStream> cdrref = new DataRef<OutputStream>(cdr);
          m_buffer.read(cdrref);

          return cdrref.v;
      }
      private BufferBase<OutputStream> m_buffer;
      private CdrDataHolder  m_put_data;
  };

/*
  class MockConsumer implements InPortConsumer {
        public MockConsumer() {
            this(0L);
        }
        public MockConsumer(long sleepTick) {
            super();
            _sleepTick = sleepTick;
            _count = 0;
            resetDelayStartTime();
        }
        
	public void init(Properties prop) {
	}

        public void push() {
            long now = System.currentTimeMillis();
            
            long delayTick = now - _delayStartTime;
            
            _delayTicks.add(delayTick);
            
            resetDelayStartTime();

            try {
                Thread.sleep(_sleepTick);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            _count++;

            setReturnStartTime();
        }
        
        public InPortConsumer clone() {
            MockConsumer clone = new MockConsumer();
            clone._sleepTick = _sleepTick;
            return clone;
        }

        public boolean subscribeInterface(NVListHolder holder) {
            return true;
        }
        
        public void unsubscribeInterface(NVListHolder holder) {
            return;
        }
        
        public void setDelayStartTime() {
            if( _delayStartTime == 0 ) {
                _delayStartTime = System.currentTimeMillis();
            }
        }
        
        public void recordReturnTick() {
            long now = System.currentTimeMillis();
            
            long returnTick = now - _returnStartTime;
            
            _returnTicks.add(returnTick);
        }
        
        public Vector<Long> getDelayTicks() {
            return _delayTicks;
        }
        
        public Vector<Long> getReturnTicks() {
            return _returnTicks;
        }
        
        public int getCount() {
            return _count;
        }
        
        protected long _sleepTick;
        protected long _delayStartTime;
        protected long _returnStartTime;
        protected Vector<Long> _delayTicks = new Vector<Long>();
        protected Vector<Long> _returnTicks = new Vector<Long>();
        protected int _count;
    
        protected void resetDelayStartTime() {
            _delayStartTime = 0;
        }
        
        protected void setReturnStartTime() {
            _returnStartTime = System.currentTimeMillis();
        }
        
        protected void resetReturnStartTime() {
            _returnStartTime = 0;
        }
        public ReturnCode put(final OutputStream data) {
            return ReturnCode.PORT_OK;
        }
        public void publishInterfaceProfile(NVListHolder properties) {
        }
    };
*/

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>update()メソッド呼出周辺の即時性のテスト
     * <ul>
     * <li>Publisherのupdate()メソッド呼出後、所定時間内にConsumerのpush()メソッドが呼び出されるか？</li>
     * <li>Consumerのpush()メソッド終了後、所定時間内にPublihserのupdate()メソッド呼出から復帰するか？</li>
     * </ul>
     * </p>
     */
/*
    public void test_update_immediacy() {
        long sleepTick = 100;
        
        MockConsumer consumer = new MockConsumer(sleepTick);
        Properties prop = new Properties();
        PublisherFlush publisher = new PublisherFlush(consumer, prop);
        
        for( int i = 0; i < 20; i++ ) {
            consumer.setDelayStartTime();
            publisher.update();
            consumer.recordReturnTick();
        }
        
        long permissibleDelayTick = 100;
        Vector<Long> delayTicks = consumer.getDelayTicks();
        for( int i = 0; i < delayTicks.size(); i++) {
            assertTrue(delayTicks.get(i) < permissibleDelayTick);
        }
        
        long permissibleReturnTick = 100;
        Vector<Long> returnTicks = consumer.getReturnTicks();
        for( int i = 0; i < returnTicks.size(); i++) {
            assertTrue(returnTicks.get(i) < permissibleReturnTick);
        }
    }
*/
    /**
     * <p> test of asetConsumer() </p>
     */
    public void test_setConsumer() {
        InPortCorbaCdrConsumer consumer0 
                                    = new InPortCorbaCdrConsumer();
        InPortCorbaCdrConsumer consumer1 
                                     = new InPortCorbaCdrConsumer();
        PublisherFlush publisher = new PublisherFlush();

        //When the argument of setConsumer is made null, 
        //setConsumer returns INVALID_ARGS. 
        assertEquals("1:",ReturnCode.INVALID_ARGS, 
                             publisher.setConsumer(null));

        //
        assertEquals("2:",ReturnCode.PORT_OK, 
                             publisher.setConsumer(consumer0));

        //
        assertEquals("3:",ReturnCode.PORT_OK, 
                             publisher.setConsumer(consumer1));

 
    }
    /**
     * <p> test of activate() and deactivate() and _isActiv() </p>
     */
    public void test_activate_deactivate_isActive() {
        InPortCorbaCdrConsumer consumer 
                                    = new InPortCorbaCdrConsumer();
        PublisherFlush publisher = new PublisherFlush();
        publisher.setConsumer(consumer);

        assertEquals("1:",false, 
                             publisher.isActive());
        
        assertEquals("2:",ReturnCode.PORT_OK, 
                             publisher.activate());

        assertEquals("3:",true, 
                             publisher.isActive());
        
        //PORT_OK is returned when activated by the active state.
        assertEquals("4:",ReturnCode.PORT_OK, 
                             publisher.activate());

        assertEquals("5:",true, 
                             publisher.isActive());
        
        assertEquals("6:",ReturnCode.PORT_OK, 
                             publisher.deactivate());

        assertEquals("7:",false, 
                             publisher.isActive());
        
        //PORT_OK is returned when activated by non-active state.
        assertEquals("8:",ReturnCode.PORT_OK, 
                             publisher.deactivate());
        
        assertEquals("9:",false, 
                             publisher.isActive());
        
    }
    /**
     * <p> test of write() </p>
     */
    public void test_write() {
        InPortCorbaCdrConsumerMock consumer 
                                    = new InPortCorbaCdrConsumerMock();

        PublisherFlush publisher = new PublisherFlush();

        //write() is called with the consumer not set. 
        {
        org.omg.CORBA.Any any = ORBUtil.getOrb().create_any();
        OutputStream cdr = any.create_output_stream();
        cdr.write_long(12345);
        assertEquals("1:",ReturnCode.PRECONDITION_NOT_MET, 
                             publisher.write(cdr,0,0));
        }

        assertEquals(ReturnCode.PORT_OK,publisher.setConsumer(consumer));
        ConnectorListeners listeners = new ConnectorListeners();
        Vector<String> ports  = new Vector<String>();
        Properties prop = new Properties("id","test");
        ConnectorBase.ConnectorInfo info 
            = new ConnectorBase.ConnectorInfo("test","test",ports,prop);
        assertEquals(ReturnCode.PORT_OK,publisher.setListener(info, listeners));

        //write() is called before activate() is called. 
        {
        org.omg.CORBA.Any any = ORBUtil.getOrb().create_any();
        OutputStream cdr = any.create_output_stream();
        cdr.write_long(123);
        assertEquals("2:",ReturnCode.PORT_OK,
                             publisher.write(cdr,0,0));
        }
        publisher.activate();

        int[] testdata = { 123,279,3106,31611,125563,
                                    125563,846459,2071690107, };

        for(int icc=0;icc<7;++icc) {
            org.omg.CORBA.Any any = ORBUtil.getOrb().create_any();
            OutputStream cdr = any.create_output_stream();
            cdr.write_long(testdata[icc+1]);

            assertEquals("3:",ReturnCode.PORT_OK,
                                 publisher.write(cdr,0,0));

        }

        //The buffer calls write() in the state of full. 
        {
        org.omg.CORBA.Any any = ORBUtil.getOrb().create_any();
        OutputStream cdr = any.create_output_stream();
        cdr.write_long(12345);
        assertEquals("4:",ReturnCode.BUFFER_FULL,
                                 publisher.write(cdr,0,0));
        }

        for(int icc=0;icc<8;++icc) {
            org.omg.CORBA.Any any = ORBUtil.getOrb().create_any();
            OutputStream data = any.create_output_stream();
            data = consumer.get_m_put_data();

            int intval = 0;
            try {
                intval = data.create_input_stream().read_long();
            }
            catch(org.omg.CORBA.NO_IMPLEMENT e) {
            }
            assertEquals("6:",testdata[icc],intval);
        }

        //After deactivate() is called, write() is called.
        publisher.deactivate();
        {
        org.omg.CORBA.Any any = ORBUtil.getOrb().create_any();
        OutputStream cdr = any.create_output_stream();
        cdr.write_long(12345);
        assertEquals("7:",ReturnCode.PORT_OK,
                             publisher.write(cdr,0,0));
        }
    }
}
