package jp.go.aist.rtm.RTC.port;

import java.util.Vector;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

import junit.framework.TestCase;
import _SDOPackage.NVListHolder;

import jp.go.aist.rtm.RTC.PeriodicTaskFactory;
import jp.go.aist.rtm.RTC.PeriodicTask;
import jp.go.aist.rtm.RTC.PeriodicTaskBase;
import jp.go.aist.rtm.RTC.port.publisher.PublisherNew;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.ORBUtil;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.buffer.RingBuffer;
import jp.go.aist.rtm.RTC.buffer.CdrRingBuffer;
import jp.go.aist.rtm.RTC.log.Logbuf;

/**
 * <p>PublisherNewクラスのためのテストケースです。</p>
 */
public class PublisherNewTests extends TestCase {

    protected Logbuf rtcout;
    protected ConsoleHandler m_stdout;
    protected FileHandler m_fh;
    protected void setUp() throws Exception {
        super.setUp();
        rtcout = new Logbuf("Manager");
        rtcout.setLevel("SILENT");
//        m_stdout = new ConsoleHandler();
//        rtcout.addStream(m_stdout);
        String logfile = "PublisherNewTests.log";
        m_fh = new FileHandler(logfile);
        rtcout.addStream(m_fh);
        rtcout.setLevel("TRACE");
        PeriodicTaskFactory<PeriodicTaskBase,String> factory 
            = PeriodicTaskFactory.instance();
        factory.addFactory("default",
			   new PeriodicTask(),
			   new PeriodicTask());
    }

    protected void tearDown() throws Exception {
        super.tearDown();
//        rtcout.removeStream(m_stdout);
        rtcout.removeStream(m_fh);
    }
    /**
     * <p> init() </p>
     * 
     */
    public void test_init() {
        PublisherNewMock publisher = new PublisherNewMock();
        ReturnCode retcode;
        Properties prop = new Properties();

        //Does init() operate normally when Properties is empty?
        retcode = publisher.init(prop);
        assertTrue(retcode.equals(ReturnCode.PORT_OK));

        prop.setProperty("publisher.push_policy","new");
        prop.setProperty("thread_type","bar");
        prop.setProperty("measurement.exec_time","default");
        prop.setProperty("measurement.period_count","1");

        //When thread_type is an illegal value, does init() return INVALID_ARGS?
        retcode = publisher.init(prop);
        assertTrue(retcode.equals(ReturnCode.INVALID_ARGS));

        prop.setProperty("publisher.push_policy","all");
        prop.setProperty("publisher.skip_count","0");
        prop.setProperty("thread_type","default");
        prop.setProperty("measurement.exec_time","enable");
        prop.setProperty("measurement.exec_count","0");
        prop.setProperty("measurement.period_time","enable");
        prop.setProperty("measurement.period_count","0");
        retcode = publisher.init(prop);
        assertTrue(retcode.equals(ReturnCode.PORT_OK));

        prop.setProperty("publisher.push_policy","fifo");
        prop.setProperty("publisher.skip_count","1");
        prop.setProperty("thread_type","default");
        prop.setProperty("measurement.exec_time","disable");
        prop.setProperty("measurement.exec_count","1");
        prop.setProperty("measurement.period_time","disable");
        prop.setProperty("measurement.period_count","1");
        retcode = publisher.init(prop);
        assertTrue(retcode.equals(ReturnCode.PORT_OK));

        prop.setProperty("publisher.push_policy","skip");
        prop.setProperty("publisher.skip_count","-1");
        prop.setProperty("thread_type","default");
        prop.setProperty("measurement.exec_time","bar");
        prop.setProperty("measurement.exec_count","-1");
        prop.setProperty("measurement.period_time","bar");
        prop.setProperty("measurement.period_count","-1");
        retcode = publisher.init(prop);
        assertTrue(retcode.equals(ReturnCode.PORT_OK));

        prop.setProperty("publisher.push_policy","new");
        prop.setProperty("publisher.skip_count","foo");
        prop.setProperty("thread_type","default");
        prop.setProperty("measurement.exec_time","enable");
        prop.setProperty("measurement.exec_count","foo");
        prop.setProperty("measurement.period_time","enable");
        prop.setProperty("measurement.period_count","foo");
        retcode = publisher.init(prop);
        assertTrue(retcode.equals(ReturnCode.PORT_OK));

        prop.setProperty("publisher.push_policy","bar");
        prop.setProperty("publisher.skip_count","0");
        prop.setProperty("thread_type","default");
        prop.setProperty("measurement.exec_time","enable");
        prop.setProperty("measurement.exec_count","0");
        prop.setProperty("measurement.period_time","enable");
        prop.setProperty("measurement.period_count","0");
        retcode = publisher.init(prop);
        assertTrue(retcode.equals(ReturnCode.PORT_OK));

    }
    /**
     * <p> setConsumer() </p>
     * 
     */
    public void test_setConsumer() {
        InPortCorbaCdrConsumer consumer0 
                                    = new InPortCorbaCdrConsumer();
        InPortCorbaCdrConsumer consumer1 
                                    = new InPortCorbaCdrConsumer();
        PublisherNew publisher = new  PublisherNew();

        //
        assertTrue(publisher.setConsumer(null).equals(ReturnCode.INVALID_ARGS));

        //
        assertTrue(publisher.setConsumer(consumer0).equals(ReturnCode.PORT_OK));

        //
        assertTrue(publisher.setConsumer(consumer1).equals(ReturnCode.PORT_OK));

    }
    /**
     * <p> setBuffer() </p>
     * 
     */
    public void test_setBuffer() {
        BufferBase<OutputStream> buffer0 = new RingBuffer<OutputStream>();
        BufferBase<OutputStream> buffer1 = new RingBuffer<OutputStream>();
        PublisherNew publisher = new PublisherNew();

        //
        assertTrue(publisher.setBuffer(null).equals(ReturnCode.INVALID_ARGS));

        //
        assertTrue(publisher.setBuffer(buffer0).equals(ReturnCode.PORT_OK));

        //
        assertTrue(publisher.setBuffer(buffer1).equals(ReturnCode.PORT_OK));

    }
    /**
     * <p> activate(),deactivate(),isActive </p>
     * 
     */
    public void test_activate_deactivate_isActive() {
        InPortCorbaCdrConsumer consumer 
                                    = new InPortCorbaCdrConsumer();
        PublisherNew publisher = new PublisherNew();
        publisher.setConsumer(consumer);

        assertEquals(false, 
                             publisher.isActive());
        
        assertTrue(publisher.activate().equals(ReturnCode.PORT_OK));

        assertEquals(true, 
                             publisher.isActive());
        
        //When activete() is called after activate() is called, 
        // activate() returns PORT_OK. 
        assertTrue(publisher.activate().equals(ReturnCode.PORT_OK));

        assertEquals(true, 
                             publisher.isActive());
        
        assertTrue(publisher.deactivate().equals(ReturnCode.PORT_OK));

        assertEquals(false, 
                             publisher.isActive());
        
        //When deactivate() is called while not activated, 
        // deactivatei() returns PORT_OK.
        assertTrue(publisher.deactivate().equals(ReturnCode.PORT_OK));
        
        assertEquals(false, 
                             publisher.isActive());
        
    }
    /**
     * <p> write(), pushAll() </p>
     *
     * <p> Even if the buffer of provider is full, 
     * the data omission is not generated.</p>
     */
    public void test_pushAll() {
        rtcout.println(rtcout.TRACE, "IN  test_pushAll()");
        InPortCorbaCdrConsumerMock consumer 
                                    = new InPortCorbaCdrConsumerMock();
        BufferBase<OutputStream> buffer = new RingBuffer<OutputStream>();
        {
        Properties prop = new Properties();
        prop.setProperty("write.full_policy","do_nothing");
        buffer.init(prop);
        }
        PublisherNewMock publisher = new PublisherNewMock();


        Properties prop = new Properties();
        prop.setProperty("publisher.push_policy","all");
        prop.setProperty("publisher.skip_count","0");
        prop.setProperty("thread_type","default");
        prop.setProperty("measurement.exec_time","enable");
        prop.setProperty("measurement.exec_count","0");
        prop.setProperty("measurement.period_time","enable");
        prop.setProperty("measurement.period_count","0");
        publisher.init(prop);

        this.sleep(10000);
        publisher.setConsumer(consumer);
        publisher.setBuffer(buffer);
        publisher.activate();

        for(int icc=0;icc<8;++icc) {
            OutputStream cdr  = toStream(icc,123,127);


            assertTrue(publisher.write(cdr,-1,0).equals(ReturnCode.PORT_OK));
            this.sleep(10000);
        }

        //The buffer of provier is not full and calls write(). 
        {
        OutputStream cdr  = toStream(8,123,127);
        assertTrue(publisher.write(cdr,-1,0).equals(ReturnCode.PORT_OK));
        this.sleep(10000);
        }
        {
        OutputStream cdr  = toStream(9,123,127);
        assertTrue(publisher.write(cdr,-1,0).equals(ReturnCode.BUFFER_FULL));
        this.sleep(10000);
        }

        //Four data is acquired from the buffer of provider.
        for(int icc=0;icc<4;++icc) {
            OutputStream data  = consumer.get_m_put_data();
            RTC.TimedLong tl = new RTC.TimedLong();
            RTC.TimedLongHolder tlh 
                = new RTC.TimedLongHolder(tl);
            tlh._read(data.create_input_stream());

            assertEquals("1:",123,tlh.value.tm.sec);
            assertEquals("2:",127,tlh.value.tm.nsec);
            assertEquals("3:",icc,tlh.value.data);
        }

        //The buffer of provier is not full and calls write(). 
        {
        OutputStream cdr  = toStream(10,123,127);
        assertTrue(publisher.write(cdr,-1,0).equals(ReturnCode.BUFFER_FULL));
        this.sleep(10000);
        }
        {
        OutputStream cdr  = toStream(11,123,127);
        assertTrue(publisher.write(cdr,-1,0).equals(ReturnCode.PORT_OK));
        this.sleep(10000);
        }

        //Data is not missed. 
        for(int icc=0;icc<8;++icc) {
            OutputStream data  = consumer.get_m_put_data();
            RTC.TimedLong tl = new RTC.TimedLong();
            RTC.TimedLongHolder tlh 
                = new RTC.TimedLongHolder(tl);
            tlh._read(data.create_input_stream());

            assertEquals("4:",123,tlh.value.tm.sec);
            assertEquals("5:",127,tlh.value.tm.nsec);
            assertEquals("6:",icc+4,tlh.value.data);
        }
        publisher.deactivate();
        
        rtcout.println(rtcout.TRACE, "OUT test_pushAll()");
        
    }
    /**
     * <p> write(), pushAll() </p>
     *
     * -
     */
    public void test_pushAll_2() {
        InPortCorbaCdrConsumerMock consumer 
                                    = new InPortCorbaCdrConsumerMock();
        BufferBase<OutputStream> buffer = new RingBuffer<OutputStream>();
        {
        Properties prop = new Properties();
        prop.setProperty("write.full_policy","do_nothing");
        buffer.init(prop);
        }
        PublisherNewMock publisher = new PublisherNewMock();


        Properties prop = new Properties();
        prop.setProperty("publisher.push_policy","all");
        prop.setProperty("publisher.skip_count","0");
        prop.setProperty("thread_type","default");
        prop.setProperty("measurement.exec_time","enable");
        prop.setProperty("measurement.exec_count","0");
        prop.setProperty("measurement.period_time","enable");
        prop.setProperty("measurement.period_count","0");
        publisher.init(prop);

        publisher.setConsumer(consumer);
        publisher.setBuffer(buffer);
        publisher.activate();

        for(int icc=0;icc<16;++icc) {
            OutputStream cdr  = toStream(icc,123,127);

            ReturnCode ret;
            ret = publisher.write(cdr,-1,0);
            if(icc<9) {
                assertTrue(ret.equals(ReturnCode.PORT_OK));
            }
            else {
                assertTrue(ret.equals(ReturnCode.BUFFER_FULL));
            }
            this.sleep(10000);

        }

        //Because the buffer of consumer and provider are full, 
        // data is not transmitted.
        {
        OutputStream cdr  = toStream(16,123,127);
        assertTrue(publisher.write(cdr,-1,0).equals(ReturnCode.BUFFER_FULL));
        this.sleep(10000);
        }

        // data are checked.
        for(int icc=0;icc<8;++icc) {
            OutputStream data  = consumer.get_m_put_data();

            RTC.TimedLong tl = new RTC.TimedLong();
            RTC.TimedLongHolder tlh 
                = new RTC.TimedLongHolder(tl);
            tlh._read(data.create_input_stream());

            assertEquals("1:",123,tlh.value.tm.sec);
            assertEquals("2:",127,tlh.value.tm.nsec);
            assertEquals("3:",icc,tlh.value.data);
        }

        //Because the buffer of consumer is full, 
        // data is not transmitted.
        {
        OutputStream cdr  = toStream(17,123,127);
        assertTrue(publisher.write(cdr,-1,0).equals(ReturnCode.BUFFER_FULL));
        this.sleep(10000);
        }
        // data are checked.
        for(int icc=0;icc<8;++icc) {
            OutputStream data  = consumer.get_m_put_data();

            RTC.TimedLong tl = new RTC.TimedLong();
            RTC.TimedLongHolder tlh 
                = new RTC.TimedLongHolder(tl);
            tlh._read(data.create_input_stream());

            assertEquals("4:",123,tlh.value.tm.sec);
            assertEquals("5:",127,tlh.value.tm.nsec);
            assertEquals("6:",icc+8,tlh.value.data);
        }
        {
        OutputStream cdr  = toStream(18,123,127);
        assertTrue(publisher.write(cdr,-1,0).equals(ReturnCode.PORT_OK));
        this.sleep(10000);
        }
        // data is checked.
        {
        OutputStream data  = consumer.get_m_put_data();

        RTC.TimedLong tl = new RTC.TimedLong();
        RTC.TimedLongHolder tlh 
            = new RTC.TimedLongHolder(tl);
        tlh._read(data.create_input_stream());

        assertEquals("7:",123,tlh.value.tm.sec);
        assertEquals("8:",127,tlh.value.tm.nsec);
        assertEquals("9:",18,tlh.value.data);
        }

        this.sleep(10000);
        publisher.deactivate();
        
    }
    /**
     * <p> pushFifo() </p>
     * 
     */
    public void test_pushFifo() {
        rtcout.println(rtcout.TRACE, "IN  test_pushFifo()");
        InPortCorbaCdrConsumerMock consumer 
                                    = new InPortCorbaCdrConsumerMock();
        BufferBase<OutputStream> buffer = new RingBuffer<OutputStream>();
        {
        Properties prop = new Properties();
        prop.setProperty("write.full_policy","do_nothing");
        buffer.init(prop);
        }
        PublisherNewMock publisher = new PublisherNewMock();


        Properties prop = new Properties();
        prop.setProperty("publisher.push_policy","fifo");
        prop.setProperty("publisher.skip_count","0");
        prop.setProperty("thread_type","default");
        prop.setProperty("measurement.exec_time","enable");
        prop.setProperty("measurement.exec_count","0");
        prop.setProperty("measurement.period_time","enable");
        prop.setProperty("measurement.period_count","0");
        publisher.init(prop);
        this.sleep(100000);

        publisher.setConsumer(consumer);
        publisher.setBuffer(buffer);
        publisher.activate();

        for(int icc=0;icc<8;++icc) {
            OutputStream cdr  = toStream(icc,123,127);
            ReturnCode ret = publisher.write(cdr,-1,0);
            rtcout.println(rtcout.TRACE, "    0:"+icc+":write()="+ret);
            assertTrue(ret.equals(ReturnCode.PORT_OK));

            this.sleep(200000);
        }

        this.sleep(100000);
        //The buffer of provier is not full and calls write(). 
        {
        OutputStream cdr  = toStream(8,123,127);
        ReturnCode ret = publisher.write(cdr,-1,0);
        rtcout.println(rtcout.TRACE, "    0:8:"+ret);
        assertTrue("0:8:",ret.equals(ReturnCode.PORT_OK));
        this.sleep(100000);
        }
        {
        OutputStream cdr  = toStream(9,123,127);
        ReturnCode ret = publisher.write(cdr,-1,0);
        rtcout.println(rtcout.TRACE, "    0:9:"+ret);
        assertTrue("0:9:",ret.equals(ReturnCode.BUFFER_FULL));
        this.sleep(10000);
        }

        //Four data is acquired from the buffer of provider.
        for(int icc=0;icc<4;++icc) {
            OutputStream data  = consumer.get_m_put_data();

            RTC.TimedLong tl = new RTC.TimedLong();
            RTC.TimedLongHolder tlh 
                = new RTC.TimedLongHolder(tl);
            tlh._read(data.create_input_stream());

            assertEquals("1:",123,tlh.value.tm.sec);
            assertEquals("2:",127,tlh.value.tm.nsec);
            assertEquals("3:",icc,tlh.value.data);
        }

        //The buffer of provier is not full and calls write(). 
        {
        OutputStream cdr  = toStream(10,123,127);
        assertTrue("3:10:",publisher.write(cdr,-1,0).equals(ReturnCode.BUFFER_FULL));
        this.sleep(30000);
        }
        {
        OutputStream cdr  = toStream(11,123,127);
        assertTrue("3:11",publisher.write(cdr,-1,0).equals(ReturnCode.PORT_OK));
        this.sleep(10000);
        }
        {
        OutputStream cdr  = toStream(12,123,127);
        assertTrue("3:12",publisher.write(cdr,-1,0).equals(ReturnCode.PORT_OK));
        this.sleep(10000);
        }
        {
        OutputStream cdr  = toStream(13,123,127);
        assertTrue(publisher.write(cdr,-1,0).equals(ReturnCode.PORT_OK));
        this.sleep(10000);
        }

        //Data is not missed.
        for(int icc=0;icc<8;++icc) {
            OutputStream data  = consumer.get_m_put_data();
            RTC.TimedLong tl = new RTC.TimedLong();
            RTC.TimedLongHolder tlh 
                = new RTC.TimedLongHolder(tl);
            tlh._read(data.create_input_stream());

            assertEquals("4:",123,tlh.value.tm.sec);
            assertEquals("5:",127,tlh.value.tm.nsec);
            assertEquals("6:",icc+4,tlh.value.data);
        }

        //Two data is stored in the buffer of provider. 

        this.sleep(10000);
        publisher.deactivate();
        
        rtcout.println(rtcout.TRACE, "OUT test_pushFifo()");
    }
    /**
     * <p> write(), pushFifo() </p>
     *
     * -
     */
    public void test_pushFifo_2() {
        rtcout.println(rtcout.TRACE, "IN  test_pushFifo_2()");
        InPortCorbaCdrConsumerMock consumer 
                                    = new InPortCorbaCdrConsumerMock();
        BufferBase<OutputStream> buffer = new RingBuffer<OutputStream>();
        {
        Properties prop = new Properties();
        prop.setProperty("write.full_policy","do_nothing");
        buffer.init(prop);
        }
        PublisherNewMock publisher = new PublisherNewMock();


        Properties prop = new Properties();
        prop.setProperty("publisher.push_policy","fifo");
        prop.setProperty("publisher.skip_count","0");
        prop.setProperty("thread_type","default");
        prop.setProperty("measurement.exec_time","enable");
        prop.setProperty("measurement.exec_count","0");
        prop.setProperty("measurement.period_time","enable");
        prop.setProperty("measurement.period_count","0");
        publisher.init(prop);
        this.sleep(100000);

        publisher.setConsumer(consumer);
        publisher.setBuffer(buffer);
        publisher.activate();

        for(int icc=0;icc<16;++icc) {
            OutputStream cdr  = toStream(icc,123,127);

            ReturnCode ret;
            ret = publisher.write(cdr,-1,0);
            rtcout.println(rtcout.TRACE, "    0:"+icc+":write()="+ret);
            if(icc<9) {
                assertTrue(ret.equals(ReturnCode.PORT_OK));
            }
            else {
                assertTrue(ret.equals(ReturnCode.BUFFER_FULL));
            }
            this.sleep(10000);

        }

        //Because the buffer of consumer and provider are full, 
        // data is not transmitted.
        {
        OutputStream cdr  = toStream(16,123,127);
        assertTrue(publisher.write(cdr,-1,0).equals(ReturnCode.BUFFER_FULL));
        this.sleep(10000);
        }

        //Data are checked.
        for(int icc=0;icc<8;++icc) {
            OutputStream data  = consumer.get_m_put_data();

            RTC.TimedLong tl = new RTC.TimedLong();
            RTC.TimedLongHolder tlh 
                = new RTC.TimedLongHolder(tl);
            tlh._read(data.create_input_stream());

            assertEquals("1:",123,tlh.value.tm.sec);
            assertEquals("2:",127,tlh.value.tm.nsec);
            assertEquals("3:",icc,tlh.value.data);
        }

        //Because the buffer of consumer is full, 
        // data is not transmitted.
        {
        OutputStream cdr  = toStream(17,123,127);
        assertTrue(publisher.write(cdr,-1,0).equals(ReturnCode.BUFFER_FULL));
        this.sleep(10000);
        }
        //This data is transmitted.
        for(int icc=0;icc<7;++icc) {
            OutputStream cdr  = toStream((18+icc),123,127);
            assertTrue(publisher.write(cdr,-1,0).equals(ReturnCode.PORT_OK));
            this.sleep(10000);
        }
        //Data are checked.
        for(int icc=0;icc<8;++icc) {
            OutputStream data  = consumer.get_m_put_data();

            RTC.TimedLong tl = new RTC.TimedLong();
            RTC.TimedLongHolder tlh 
                = new RTC.TimedLongHolder(tl);
            tlh._read(data.create_input_stream());

            assertEquals("4:",123,tlh.value.tm.sec);
            assertEquals("5:",127,tlh.value.tm.nsec);
            assertEquals("6:",icc+8,tlh.value.data);
        }
        {
        OutputStream cdr  = toStream(26,123,127);
        assertTrue(publisher.write(cdr,-1,0).equals(ReturnCode.PORT_OK));
        this.sleep(10000);
        }
        
        {
            OutputStream data  = consumer.get_m_put_data();

            RTC.TimedLong tl = new RTC.TimedLong();
            RTC.TimedLongHolder tlh 
                = new RTC.TimedLongHolder(tl);
            tlh._read(data.create_input_stream());

            assertEquals("7:",123,tlh.value.tm.sec);
            assertEquals("8:",127,tlh.value.tm.nsec);
            assertEquals("9:",18,tlh.value.data);
        }


        this.sleep(10000);
        publisher.deactivate();
        
        rtcout.println(rtcout.TRACE, "OUT test_pushFifo_2()");
    }
    /**
     * <p> pushSklip() </p>
     * 
     */
    public void test_pushSkip() {
        rtcout.println(rtcout.TRACE, "IN  test_pushSkip()");
        InPortCorbaCdrConsumerMock consumer 
                                    = new InPortCorbaCdrConsumerMock();
        BufferBase<OutputStream> buffer = new RingBuffer<OutputStream>();
        {
        Properties prop = new Properties();
        prop.setProperty("write.full_policy","do_nothing");
        buffer.init(prop);
        }
        PublisherNewMock publisher = new PublisherNewMock();


        Properties prop = new Properties();
        prop.setProperty("publisher.push_policy","skip");
        prop.setProperty("publisher.skip_count","1");
        prop.setProperty("thread_type","default");
        prop.setProperty("measurement.exec_time","enable");
        prop.setProperty("measurement.exec_count","0");
        prop.setProperty("measurement.period_time","enable");
        prop.setProperty("measurement.period_count","0");
        publisher.init(prop);
        this.sleep(10000);

        publisher.setConsumer(consumer);
        publisher.setBuffer(buffer);
        publisher.activate();

        for(int icc=0;icc<16;++icc) {
            OutputStream cdr  = toStream(icc,123,127);

            assertTrue(publisher.write(cdr,-1,0).equals(ReturnCode.PORT_OK));

            this.sleep(10000);
        }

        //The buffer is full and calls write(). 
        {
        OutputStream cdr  = toStream(8,123,127);
        assertTrue(publisher.write(cdr,-1,0).equals(ReturnCode.PORT_OK));
        this.sleep(10000);
        }
        {
        OutputStream cdr  = toStream(9,123,127);
        assertTrue(publisher.write(cdr,-1,0).equals(ReturnCode.PORT_OK));
        this.sleep(10000);
        }

        //Four data is acquired from the buffer of provider.
        for(int icc=0;icc<4;++icc) {
            OutputStream data  = consumer.get_m_put_data();

            RTC.TimedLong tl = new RTC.TimedLong();
            RTC.TimedLongHolder tlh 
                = new RTC.TimedLongHolder(tl);
            tlh._read(data.create_input_stream());

            assertEquals("1:",123,tlh.value.tm.sec);
            assertEquals("2:",127,tlh.value.tm.nsec);
            assertEquals("3:",icc*2+1,tlh.value.data);
        }

        //The buffer of provier is not full and calls write(). 
        {
        OutputStream cdr  = toStream(10,123,127);
        assertTrue(publisher.write(cdr,-1,0).equals(ReturnCode.BUFFER_FULL));
        this.sleep(10000);
        }
        {
        OutputStream cdr  = toStream(11,123,127);
        assertTrue(publisher.write(cdr,-1,0).equals(ReturnCode.PORT_OK));
        this.sleep(10000);
        }

        //Data is not missed.
        for(int icc=0;icc<2;++icc) {
            OutputStream data  = consumer.get_m_put_data();

            RTC.TimedLong tl = new RTC.TimedLong();
            RTC.TimedLongHolder tlh 
                = new RTC.TimedLongHolder(tl);
            tlh._read(data.create_input_stream());

            assertEquals("4:",123,tlh.value.tm.sec);
            assertEquals("5:",127,tlh.value.tm.nsec);
            assertEquals("6:",icc*2+9,tlh.value.data);
        }
       
        this.sleep(100000);
        publisher.deactivate();
        rtcout.println(rtcout.TRACE, "OUT test_pushSkip()");
    }
    /**
     * <p> write(), pushSkip() </p>
     *
     * -
     */
    public void test_pushSkip_2() {
        rtcout.println(rtcout.TRACE, "IN  test_pushSkip_2()");
        InPortCorbaCdrConsumerMock consumer 
                                    = new InPortCorbaCdrConsumerMock();
        BufferBase<OutputStream> buffer = new RingBuffer<OutputStream>();
        {
        Properties prop = new Properties();
        prop.setProperty("write.full_policy","do_nothing");
        buffer.init(prop);
        }
        PublisherNewMock publisher = new PublisherNewMock();


        Properties prop = new Properties();
        prop.setProperty("publisher.push_policy","skip");
        prop.setProperty("publisher.skip_count","1");
        prop.setProperty("thread_type","default");
        prop.setProperty("measurement.exec_time","enable");
        prop.setProperty("measurement.exec_count","0");
        prop.setProperty("measurement.period_time","enable");
        prop.setProperty("measurement.period_count","0");
        publisher.init(prop);
        this.sleep(10000);

        publisher.setConsumer(consumer);
        publisher.setBuffer(buffer);
        publisher.activate();

        //The buffer of provider is made full and write() is called. 
        for(int icc=0;icc<24;++icc) {
            OutputStream cdr  = toStream(icc,123,127);

            ReturnCode ret;
            ret = publisher.write(cdr,-1,0);
            if(icc<18) {
                assertTrue(ret.equals(ReturnCode.PORT_OK));
            }
            else {
                assertTrue(ret.equals(ReturnCode.BUFFER_FULL));
            }
            this.sleep(10000);

        }

        //Because the buffer of consumer and provider are full, 
        // data is not transmitted.
        {
        OutputStream cdr  = toStream(24,123,127);
        assertTrue(publisher.write(cdr,-1,0).equals(ReturnCode.BUFFER_FULL));
        this.sleep(10000);
        }

        //Data are checked.
        for(int icc=0;icc<8;++icc) {
            OutputStream data  = consumer.get_m_put_data();
            RTC.TimedLong tl = new RTC.TimedLong();
            RTC.TimedLongHolder tlh 
                = new RTC.TimedLongHolder(tl);
            tlh._read(data.create_input_stream());

            assertEquals("1:",123,tlh.value.tm.sec);
            assertEquals("2:",127,tlh.value.tm.nsec);
            assertEquals("3:",icc*2+1,tlh.value.data);
        }

        //Because the buffer of consumer and provider are full, 
        // data is not transmitted.
        {
        OutputStream cdr  = toStream(25,123,127);
        assertTrue(publisher.write(cdr,-1,0).equals(ReturnCode.BUFFER_FULL));
        this.sleep(10000);
        }
        //Data are checked.
        int len =consumer.get_m_put_data_len();
        assertEquals(4,len);
        for(int icc=0;icc<len;++icc) {
            OutputStream data  = consumer.get_m_put_data();
            RTC.TimedLong tl = new RTC.TimedLong();
            RTC.TimedLongHolder tlh 
                = new RTC.TimedLongHolder(tl);
            tlh._read(data.create_input_stream());

            assertEquals("4:",123,tlh.value.tm.sec);
            assertEquals("5:",127,tlh.value.tm.nsec);
            assertEquals("6:",icc*2+17,tlh.value.data);
        }
        {
        OutputStream cdr  = toStream(26,123,127);
        assertTrue(publisher.write(cdr,-1,0).equals(ReturnCode.PORT_OK));
        this.sleep(10000);
        }
        {
        OutputStream cdr  = toStream(27,123,127);
        assertTrue(publisher.write(cdr,-1,0).equals(ReturnCode.PORT_OK));
        this.sleep(10000);
        }
        //Data are checked.
        {
            OutputStream data  = consumer.get_m_put_data();
            RTC.TimedLong tl = new RTC.TimedLong();
            RTC.TimedLongHolder tlh 
                = new RTC.TimedLongHolder(tl);
            tlh._read(data.create_input_stream());

            assertEquals("7:",123,tlh.value.tm.sec);
            assertEquals("8:",127,tlh.value.tm.nsec);
            assertEquals("9:",27,tlh.value.data);
        }

        this.sleep(10000);
        publisher.deactivate();
        
        rtcout.println(rtcout.TRACE, "OUT test_pushSkip_2()");
    }
    /**
     * <p> pushNew() </p>
     * 
     */
    public void test_pushNew() {
        rtcout.println(rtcout.TRACE, "IN  test_pushNew()");
        InPortCorbaCdrConsumerMock consumer 
                                    = new InPortCorbaCdrConsumerMock();
        BufferBase<OutputStream> buffer = new RingBuffer<OutputStream>();
        {
        Properties prop = new Properties();
        prop.setProperty("write.full_policy","do_nothing");
        buffer.init(prop);
        }
        PublisherNewMock publisher = new PublisherNewMock();

        Properties prop = new Properties();
        prop.setProperty("publisher.push_policy","new");
        prop.setProperty("publisher.skip_count","0");
        prop.setProperty("thread_type","default");
        prop.setProperty("measurement.exec_time","enable");
        prop.setProperty("measurement.exec_count","0");
        prop.setProperty("measurement.period_time","enable");
        prop.setProperty("measurement.period_count","0");
        publisher.init(prop);
        this.sleep(10000);

        publisher.setConsumer(consumer);
        publisher.setBuffer(buffer);
        publisher.activate();

        //Eight data is not transmitted. 
        //Seven of the latest data is transmitted.
        for(int icc=0;icc<8;++icc) {
            OutputStream cdr  = toStream(icc,123,127);

            assertTrue(publisher.write(cdr,-1,0).equals(ReturnCode.PORT_OK));

        }

        this.sleep(10000);
        //Data is gotten from the buffer of provider.
        //
        int len = consumer.get_m_put_data_len() -1;
        this.sleep(10);
        //Is the latest data transmitted?
        {
            OutputStream data  = consumer.get_m_put_data();
            RTC.TimedLong tl = new RTC.TimedLong();
            RTC.TimedLongHolder tlh 
                = new RTC.TimedLongHolder(tl);
            tlh._read(data.create_input_stream());

            assertEquals("1:",123,tlh.value.tm.sec);
            assertEquals("2:",127,tlh.value.tm.nsec);
            assertEquals("3:",7,tlh.value.data);
        }

        this.sleep(10000);
        publisher.deactivate();
        rtcout.println(rtcout.TRACE, "OUT test_pushNew()");
    }
    /**
     * <p> write() </p>
     * 
     * The procedure is written disregarding it.
     */
    public void test_write() {
        rtcout.println(rtcout.TRACE, "IN  test_write()");
        InPortCorbaCdrConsumerMock consumer 
                                    = new InPortCorbaCdrConsumerMock();
        BufferBase<OutputStream> buffer = new RingBuffer<OutputStream>();
        {
        Properties prop = new Properties();
        prop.setProperty("write.full_policy","do_nothing");
        buffer.init(prop);
        }
        PublisherNewMock publisher = new PublisherNewMock();

        Properties prop = new Properties();
        prop.setProperty("publisher.push_policy","all");
        prop.setProperty("publisher.skip_count","0");
        prop.setProperty("thread_type","default");
        prop.setProperty("measurement.exec_time","enable");
        prop.setProperty("measurement.exec_count","0");
        prop.setProperty("measurement.period_time","enable");
        prop.setProperty("measurement.period_count","0");
        publisher.init(prop);

        {
        OutputStream cdr  = toStream(123,123,127);
        assertTrue("1:",
            publisher.write(cdr,-1,0).equals(ReturnCode.PRECONDITION_NOT_MET));
        this.sleep(10000);
        }
        publisher.setBuffer(buffer);
        {
        OutputStream cdr  = toStream(123,123,127);
        assertTrue("2:",
             publisher.write(cdr,-1,0).equals(ReturnCode.PRECONDITION_NOT_MET));
        this.sleep(10000);
        }
        publisher.setConsumer(consumer);
        {
        OutputStream cdr  = toStream(123,123,127);
        ReturnCode ret = publisher.write(cdr,-1,0);
        rtcout.println(rtcout.TRACE, "    3:"+ret);
        assertTrue("3:"+ret+":",ret.equals(ReturnCode.PORT_OK));
        this.sleep(10000);
        }
        publisher.activate();

        {
        OutputStream cdr  = toStream(123,123,127);
        assertTrue("5:",publisher.write(cdr,-1,0).equals(ReturnCode.PORT_OK));
        this.sleep(10000);
        }

        consumer.set_m_mode(1);
        {
        OutputStream cdr  = toStream(123,123,127);
        assertTrue("6:",publisher.write(cdr,-1,0).equals(ReturnCode.PORT_OK));
        this.sleep(10000);
        }
        {
        OutputStream cdr  = toStream(123,123,127);
        ReturnCode ret = publisher.write(cdr,-1,0);
        rtcout.println(rtcout.TRACE, "    7:"+ret);
        assertTrue("7:",ret.equals(ReturnCode.CONNECTION_LOST));
        this.sleep(10000);
        }

        rtcout.println(rtcout.TRACE, "OUT test_write()");

    }
    protected void sleep(int time){
        try{
            Thread.sleep(time/1000);
        }
        catch(java.lang.InterruptedException e) {
        }
    }
    protected OutputStream toStream(int data, int sec, int nsec){
            org.omg.CORBA.Any any = ORBUtil.getOrb().create_any();
            OutputStream cdr = any.create_output_stream();
            RTC.Time tm = new RTC.Time(sec,nsec);
            RTC.TimedLong tmlong = new RTC.TimedLong(tm,data);
            RTC.TimedLongHolder tmlongholder 
                = new RTC.TimedLongHolder(tmlong);
            tmlongholder._write(cdr);
            return cdr;

    }


    /**
     * <p>update()メソッドのテスト
     * <ul>
     * <li>「Publisherのupdate()メソッド呼出間隔」>「Consumerのpush()メソッド処理時間」の場合に、update()呼出からpush()呼出までの時間間隔が、所定時間内に収まっているか？</li>
     * </ul>
     * </p>
     */
/*
    public void test_update_large_interval() {
        long sleepTick = 100;
        long intervalTick = sleepTick * 10;
        
        MockConsumer consumer = new MockConsumer(sleepTick);
        Properties prop = new Properties();
        PublisherNew publisher = new PublisherNew(consumer, prop);
        
        for( int i = 0; i < 5; i++ ) {
            consumer.setDelayStartTime();
            publisher.update();
            try {
                Thread.sleep(intervalTick);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        // Consumer呼出が完了するであろうタイミングまで待つ
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // update()呼出からpush()呼出までの時間間隔が、所定時間内に収まっているか？
        // （リアルタイム性が保証されているわけでもなく、仕様上も呼出までの時間を明記しているわけではないので、
        // ここでの所定時間はテスト作成者の主観によるものに過ぎない。）
        long permissibleDelay = sleepTick + 100000;
        Vector<Long> delayTicks = consumer.getDelayTicks();
        for( int i = 0; i < delayTicks.size(); i++) {
            //std::cout << "delay tick = " << delayTicks[i] << std::endl;
            assertTrue(delayTicks.get(i) < permissibleDelay);
        }
    }
*/
    /**
     * <p>update()メソッドのテスト
     * <ul>
     * <li>「Publisherのupdate()メソッド呼出間隔」<「Consumerのpush()メソッド処理時間」の場合に、update()呼出が溜ってしまうことなく、update()呼出からpush()呼出までの時間間隔が、所定時間内に収まっているか？</li>
     * </ul>
     * </p>
     */
/*
    public void test_update_small_interval() {
        long sleepTick = 100;
        long intervalTick = sleepTick / 10;
        
        MockConsumer consumer = new MockConsumer(sleepTick);
        Properties prop = new Properties();
        PublisherNew publisher = new PublisherNew(consumer, prop);
        
        for( int i = 0; i < 50; i++ ) {
            consumer.setDelayStartTime();
            publisher.update();
            try {
                Thread.sleep(intervalTick);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        // Consumer呼出が完了するであろうタイミングまで待つ
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // update()呼出からpush()呼出までの時間間隔が、所定時間内に収まっているか？
        // （リアルタイム性が保証されているわけでもなく、仕様上も呼出までの時間を明記しているわけではないので、
        // ここでの所定時間はテスト作成者の主観によるものに過ぎない。）
        long permissibleDelay = sleepTick + 100000;
        Vector<Long> delayTicks = consumer.getDelayTicks();
        for( int i = 0; i < delayTicks.size()-1; i++) {
            //std::cout << "delay tick = " << delayTicks[i] << std::endl;
            assertTrue(delayTicks.get(i) < permissibleDelay);
        }
    }
*/
    /**
     * <p>release()メソッドのテスト
     * <ul>
     * <li>release()メソッド呼出によりPublisherの動作を確実に停止できるか？</li>
     * </ul>
     * </p>
     */
/*
    public void test_release() {
        MockConsumer consumer = new MockConsumer(100);
        Properties prop = new Properties();
        PublisherNew publisher = new PublisherNew(consumer, prop);
        
        // update()を呼出して、Consumerを呼び出させる
        publisher.update();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        publisher.release();
        
        assertEquals(1, consumer.getCount());
        
        // 再度update()を呼出し、Consumerを呼出しうる時間を与える。
        // （実際には、前段のrelease()によりPublisherが停止済みであり、
        // update()呼出は何ら影響を与えないことを予期している。）
        publisher.update();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Consumer呼出回数が変わっていないこと、つまりPublisherの動作が停止していることを確認する
        assertEquals(1, consumer.getCount());
    }
*/

    /**
     * 
     * 
     */
    class PublisherNewMock extends PublisherNew {
      public PublisherNewMock() {
          ;
      }
    };
    /**
     * 
     * 
     */
    class InPortCorbaCdrConsumerMock extends InPortCorbaCdrConsumer {
        /**
         * 
         * 
         */
        private Logbuf rtcout;
        public InPortCorbaCdrConsumerMock() {
            m_buffer = new RingBuffer<OutputStream>();
            m_test_mode = 0;
            rtcout = new Logbuf("Manager.InPortCorbaCdrConsumerMock");
            rtcout.setLevel("PARANOID");
        }
        /**
         * 
         * 
         */
        public ReturnCode put(final OutputStream data) {
            rtcout.println(rtcout.TRACE, "put():"+m_buffer.readable());
            RTC.TimedLong tl = new RTC.TimedLong();
            RTC.TimedLongHolder tlh 
                  = new RTC.TimedLongHolder(tl);
            tlh._read(data.create_input_stream());
            rtcout.println(rtcout.TRACE, "putdata:"+tlh.value.data);
            if(m_test_mode == 0) {
                if (m_buffer.full()) {
                     rtcout.println(rtcout.TRACE, "m_buffer is full.");
                     return ReturnCode.BUFFER_FULL;
                }

                jp.go.aist.rtm.RTC.buffer.ReturnCode ret 
                                                 = m_buffer.write(data);
               rtcout.println(rtcout.TRACE, "put():"+m_buffer.readable());

                rtcout.println(rtcout.TRACE, "put():ret:"+ret);
                return convertReturn(ret);
            }
            else if(m_test_mode == 1) {
                 String str = "test";
//                 throw new Exception(str);
                 return ReturnCode.CONNECTION_LOST;
            }
            else {
            }
            return ReturnCode.UNKNOWN_ERROR;
        }
        protected ReturnCode convertReturn(jp.go.aist.rtm.RTC.buffer.ReturnCode status) {
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
            OutputStream out = any.create_output_stream();
            DataRef<OutputStream> cdr = new DataRef<OutputStream>(out);
            m_buffer.read(cdr);

            return cdr.v;
        }
        /**
         * 
         * 
         */
        public int get_m_put_data_len() {
            int ic;
            ic = (int)m_buffer.readable();

            return ic;
        }


        /**
         * 
         * 
         */
        public void set_m_mode(int mode) {
            m_test_mode = mode;
        }
       BufferBase<OutputStream> m_buffer;
//       OpenRTM.CdrData  m_put_data;
       int m_test_mode;
    }

}


