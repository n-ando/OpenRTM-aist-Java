package jp.go.aist.rtm.RTC.port;

import junit.framework.TestCase;

import java.util.Vector;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

import jp.go.aist.rtm.RTC.PeriodicTaskFactory;
import jp.go.aist.rtm.RTC.PeriodicTask;
import jp.go.aist.rtm.RTC.PeriodicTaskBase;
import jp.go.aist.rtm.RTC.port.publisher.PublisherPeriodic;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.ORBUtil;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.buffer.RingBuffer;
import jp.go.aist.rtm.RTC.buffer.CdrRingBuffer;
import jp.go.aist.rtm.RTC.log.Logbuf;


import _SDOPackage.NVListHolder;


/**
 * <p>PublisherPeriodicクラスのためのテストケースです。</p>
 */
public class PublisherPeriodicTests extends TestCase {

    class MockConsumer implements InPortConsumer {
        public MockConsumer() {
            super();
            clearLastTime();
        }
        
        public void init(Properties prop) {
        }
        public void push() {
            long now = System.currentTimeMillis();
            
            if( !isLastTimeCleared() ) {
                long interval = now - _lastTime;
                
                _intervalTicks.add(interval);
            }
            
            _lastTime = now;
        }
        
        public InPortConsumer clone() {
            MockConsumer clone = new MockConsumer();
            clone._intervalTicks = new Vector<Long>(_intervalTicks);
            clone._lastTime = _lastTime;
            return clone;
        }

        public boolean subscribeInterface(NVListHolder holder) {
            return true;
        }
        
        public void unsubscribeInterface(NVListHolder holder) {
            return;
        }
        
        public Vector<Long> getIntervalTicks() {
            return _intervalTicks;
        }
        
        public int getCount() {
            return _intervalTicks.size();
        }
        
        private Vector<Long> _intervalTicks = new Vector<Long>();
        long _lastTime;
        
        private void clearLastTime() {
            _lastTime = 0;
        }
        
        private boolean isLastTimeCleared() {
            return (_lastTime == 0);
        }
        public ReturnCode put(final OutputStream data) {
            return ReturnCode.PORT_OK;
        }
        public void publishInterfaceProfile(NVListHolder properties) {
        }
        public void setConnector(OutPortConnector connector){
        }
    };

    class CounterConsumer implements InPortConsumer {
   
	public CounterConsumer() {
	    this(null);
	}
	
	public CounterConsumer(CounterConsumer component) {
	    super();
	    _count = 0;
	    _component = component;
	}
        
        public void init(Properties prop) {
        }
        
        public void push() {
            _count++;
            if( _component != null) {
                 _component.push();
             }
        }
        
        public InPortConsumer clone() {
            CounterConsumer clone = new CounterConsumer();
            clone._count = _count;
            clone._component = _component;
            return clone;
        }
	
        public boolean subscribeInterface(NVListHolder holder) {
            return true;
        }
        
        public void unsubscribeInterface(NVListHolder holder) {
            return;
        }

        public int getCount() {
            return _count;
        }
        public ReturnCode put(final OutputStream data) {
            return ReturnCode.PORT_OK;
        }
        public void publishInterfaceProfile(NVListHolder properties) {
        }
        public void setConnector(OutPortConnector connector) {
        }
    
        private int _count;
        private CounterConsumer _component;
    };

    public PublisherPeriodicTests(String name){
        super(name);
    }


    protected Logbuf rtcout;
    protected ConsoleHandler m_stdout;
    protected FileHandler m_fh;
    protected void setUp() throws Exception {
        super.setUp();
        rtcout = new Logbuf("Manager");
        rtcout.setLevel("SILENT");
//        m_stdout = new ConsoleHandler();
//        rtcout.addStream(m_stdout);
        String logfile = "PublisherPeriodicTests.log";
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

        PublisherPeriodicMock publisher = new PublisherPeriodicMock();
        ReturnCode retcode;
        Properties prop = new Properties();

        //init() operates normally even if the state of Properties is empty.
        retcode = publisher.init(prop);
        assertEquals(ReturnCode.PORT_OK, retcode);

        prop.setProperty("publisher.push_policy","new");
        prop.setProperty("thread_type","bar");
        prop.setProperty("measurement.exec_time","default");
        prop.setProperty("measurement.period_count","1");
        prop.setProperty("publisher.push_rate","1000.0");

        //When the value of thread_type is illegal, 
        // init() returns INVALID_ARGS. 
        retcode = publisher.init(prop);
        assertEquals(ReturnCode.INVALID_ARGS, retcode);

        prop.setProperty("publisher.push_policy","all");
        prop.setProperty("publisher.skip_count","0");
        prop.setProperty("thread_type","default");
        prop.setProperty("measurement.exec_time","enable");
        prop.setProperty("measurement.exec_count","0");
        prop.setProperty("measurement.period_time","enable");
        prop.setProperty("measurement.period_count","0");
        prop.setProperty("publisher.push_rate","");
        retcode = publisher.init(prop);
        assertEquals(ReturnCode.PORT_OK, retcode);

        prop.setProperty("publisher.push_policy","fifo");
        prop.setProperty("publisher.skip_count","1");
        prop.setProperty("thread_type","default");
        prop.setProperty("measurement.exec_time","disable");
        prop.setProperty("measurement.exec_count","1");
        prop.setProperty("measurement.period_time","disable");
        prop.setProperty("measurement.period_count","1");
        prop.setProperty("publisher.push_rate","");
        retcode = publisher.init(prop);
        assertEquals(ReturnCode.PORT_OK, retcode);

        prop.setProperty("publisher.push_policy","skip");
        prop.setProperty("publisher.skip_count","-1");
        prop.setProperty("thread_type","default");
        prop.setProperty("measurement.exec_time","bar");
        prop.setProperty("measurement.exec_count","-1");
        prop.setProperty("measurement.period_time","bar");
        prop.setProperty("measurement.period_count","-1");
        prop.setProperty("publisher.push_rate","");
        retcode = publisher.init(prop);
        assertEquals(ReturnCode.PORT_OK, retcode);

        prop.setProperty("publisher.push_policy","new");
        prop.setProperty("publisher.skip_count","foo");
        prop.setProperty("thread_type","default");
        prop.setProperty("measurement.exec_time","enable");
        prop.setProperty("measurement.exec_count","foo");
        prop.setProperty("measurement.period_time","enable");
        prop.setProperty("measurement.period_count","foo");
        prop.setProperty("publisher.push_rate","");
        retcode = publisher.init(prop);
        assertEquals(ReturnCode.PORT_OK, retcode);

        prop.setProperty("publisher.push_policy","bar");
        prop.setProperty("publisher.skip_count","0");
        prop.setProperty("thread_type","default");
        prop.setProperty("measurement.exec_time","enable");
        prop.setProperty("measurement.exec_count","0");
        prop.setProperty("measurement.period_time","enable");
        prop.setProperty("measurement.period_count","0");
        prop.setProperty("publisher.push_rate","");
        retcode = publisher.init(prop);
        assertEquals(ReturnCode.PORT_OK, retcode);

        prop.setProperty("publisher.push_policy","all");
        prop.setProperty("publisher.skip_count","0");
        prop.setProperty("thread_type","default");
        prop.setProperty("measurement.exec_time","enable");
        prop.setProperty("measurement.exec_count","0");
        prop.setProperty("measurement.period_time","enable");
        prop.setProperty("measurement.period_count","0");
        prop.setProperty("publisher.push_rate","0");
        retcode = publisher.init(prop);
        assertEquals(ReturnCode.PORT_OK, retcode);

        prop.setProperty("publisher.push_policy","all");
        prop.setProperty("publisher.skip_count","0");
        prop.setProperty("thread_type","default");
        prop.setProperty("measurement.exec_time","enable");
        prop.setProperty("measurement.exec_count","0");
        prop.setProperty("measurement.period_time","enable");
        prop.setProperty("measurement.period_count","0");
        prop.setProperty("publisher.push_rate","-1");
        retcode = publisher.init(prop);
        assertEquals(ReturnCode.PORT_OK, retcode);

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
        PublisherPeriodic publisher = new PublisherPeriodic();

        //
        assertEquals(ReturnCode.INVALID_ARGS, 
                             publisher.setConsumer(null));

        //
        assertEquals(ReturnCode.PORT_OK, 
                             publisher.setConsumer(consumer0));

        //
        assertEquals(ReturnCode.PORT_OK, 
                             publisher.setConsumer(consumer1));

    }
    /**
     * <p> setBuffer() </p>
     * 
     */
    public void test_setBuffer() {
        BufferBase<OutputStream> buffer0 = new RingBuffer<OutputStream>();
        BufferBase<OutputStream> buffer1 = new RingBuffer<OutputStream>();
        PublisherPeriodic publisher = new PublisherPeriodic();

        assertEquals(ReturnCode.INVALID_ARGS, 
                             publisher.setBuffer(null));

        //
        assertEquals(ReturnCode.PORT_OK, 
                             publisher.setBuffer(buffer0));

        //
        assertEquals(ReturnCode.PORT_OK, 
                             publisher.setBuffer(buffer1));

    }
    /**
     * <p> activate(),deactivate(),isActive </p>
     * 
     */
    public void test_activate_deactivate_isActive() {
        InPortCorbaCdrConsumer consumer 
                                    = new InPortCorbaCdrConsumer();
        PublisherPeriodic publisher = new PublisherPeriodic();
        publisher.setConsumer(consumer);

        //When activate() is called without calling init(), 
        // activate() returns PREONDITION_NOT_MET. 
        assertEquals(ReturnCode.PRECONDITION_NOT_MET, 
                             publisher.activate());

        //When deactivate() is called without calling init(), 
        // deactivate() returns PREONDITION_NOT_MET. 
        assertEquals(ReturnCode.PRECONDITION_NOT_MET, 
                             publisher.deactivate());

        //init()
        Properties prop = new Properties();
        prop.setProperty("publisher.push_policy","all");
        prop.setProperty("publisher.skip_count","0");
        prop.setProperty("thread_type","default");
        prop.setProperty("measurement.exec_time","enable");
        prop.setProperty("measurement.exec_count","0");
        prop.setProperty("measurement.period_time","enable");
        prop.setProperty("measurement.period_count","0");
        prop.setProperty("publisher.push_rate","");
        publisher.init(prop);

        
        //When activate() is called without calling setBuffer(), 
        // activate() returns PREONDITION_NOT_MET. 
        assertEquals(ReturnCode.PRECONDITION_NOT_MET, 
                             publisher.activate());

        //setBuffer()
        BufferBase<OutputStream> buffer = new RingBuffer<OutputStream>();
        publisher.setBuffer(buffer);


        assertEquals(false, 
                             publisher.isActive());
        
        assertEquals(ReturnCode.PORT_OK, 
                             publisher.activate());

        assertEquals(true, 
                             publisher.isActive());

        //When activete() is called after activate() is called, 
        // activate() returns PORT_OK. 
        assertEquals(ReturnCode.PORT_OK, 
                             publisher.activate());

        assertEquals(true, 
                             publisher.isActive());
        
        assertEquals(ReturnCode.PORT_OK, 
                             publisher.deactivate());

        assertEquals(false, 
                             publisher.isActive());
        
        //When deactivate() is called while not activated, 
        // deactivatei() returns PORT_OK.
        assertEquals(ReturnCode.PORT_OK, 
                             publisher.deactivate());
        
        assertEquals(false, 
                             publisher.isActive());
        
    }
    /**
     * <p> write(), pushAll() </p>
     * 
     * <p> Even if the buffer of provider is full, 
     * the data omission is not generated.</p>
     *
     */
    public void test_pushAll() {
        rtcout.println(rtcout.TRACE, "test_pushAll");
        InPortCorbaCdrConsumerMock consumer 
                                    = new InPortCorbaCdrConsumerMock();
        BufferBase<OutputStream> buffer = new RingBuffer<OutputStream>();

        PublisherPeriodicMock publisher = new PublisherPeriodicMock();


        Properties prop = new Properties();
        prop.setProperty("publisher.push_policy","all");
        prop.setProperty("publisher.skip_count","0");
        prop.setProperty("thread_type","default");
        prop.setProperty("measurement.exec_time","enable");
        prop.setProperty("measurement.exec_count","0");
        prop.setProperty("measurement.period_time","enable");
        prop.setProperty("measurement.period_count","0");
        prop.setProperty("publisher.push_rate","");
        publisher.init(prop);

        publisher.setConsumer(consumer);
        publisher.setBuffer(buffer);
        publisher.activate();

        for(int icc=0;icc<8;++icc) {
            org.omg.CORBA.Any any = ORBUtil.getOrb().create_any();
            OutputStream cdr = any.create_output_stream();
            RTC.Time tm = new RTC.Time(123,127);
            RTC.TimedLong tmlong = new RTC.TimedLong(tm,icc);
            RTC.TimedLongHolder tmlongholder 
                = new RTC.TimedLongHolder(tmlong);
            tmlongholder._write(cdr);

            assertEquals("1:",ReturnCode.PORT_OK,
                                 publisher.write(cdr,-1,0));

        }

        try{
            Thread.sleep(10);
        }
        catch(java.lang.InterruptedException e) {
        }
        //The buffer of provider is made full and write() is called. 
        {
        org.omg.CORBA.Any any = ORBUtil.getOrb().create_any();
        OutputStream cdr = any.create_output_stream();
        RTC.Time tm = new RTC.Time(123,127);
        RTC.TimedLong tmlong = new RTC.TimedLong(tm,8);
        RTC.TimedLongHolder tmlongholder 
                = new RTC.TimedLongHolder(tmlong);
        tmlongholder._write(cdr);
        assertEquals("2:",ReturnCode.PORT_OK,
                                 publisher.write(cdr,-1,0));
        try{
            Thread.sleep(10);
        }
        catch(java.lang.InterruptedException e) {
        }
        }
        {
        org.omg.CORBA.Any any = ORBUtil.getOrb().create_any();
        OutputStream cdr = any.create_output_stream();
        RTC.Time tm = new RTC.Time(123,127);
        RTC.TimedLong tmlong = new RTC.TimedLong(tm,9);
        RTC.TimedLongHolder tmlongholder 
                = new RTC.TimedLongHolder(tmlong);
        tmlongholder._write(cdr);
        assertEquals("3:",ReturnCode.BUFFER_FULL,
                                 publisher.write(cdr,-1,0));
        try{
            Thread.sleep(10);
        }
        catch(java.lang.InterruptedException e) {
        }
        }

        //Four data is acquired from the buffer of provider.
        //
        for(int icc=0;icc<4;++icc) {
            OutputStream data  = consumer.get_m_put_data();

            RTC.TimedLong tl = new RTC.TimedLong();
            RTC.TimedLongHolder tlh 
                = new RTC.TimedLongHolder(tl);
            tlh._read(data.create_input_stream());

            assertEquals("4:",123,tlh.value.tm.sec);
            assertEquals("5:",127,tlh.value.tm.nsec);
            assertEquals("6:",icc,tlh.value.data);
        }

        try{
            Thread.sleep(20);
        }
        catch(java.lang.InterruptedException e) {
        }
        //The buffer of provier is not full and calls write(). 
        {
        org.omg.CORBA.Any any = ORBUtil.getOrb().create_any();
        OutputStream cdr = any.create_output_stream();
        RTC.Time tm = new RTC.Time(123,127);
        RTC.TimedLong tmlong = new RTC.TimedLong(tm,10);
        RTC.TimedLongHolder tmlongholder 
                = new RTC.TimedLongHolder(tmlong);
        tmlongholder._write(cdr);
        assertEquals("7:",ReturnCode.PORT_OK,
                                 publisher.write(cdr,-1,0));
        try{
            Thread.sleep(10);
        }
        catch(java.lang.InterruptedException e) {
        }
        }
        {
        org.omg.CORBA.Any any = ORBUtil.getOrb().create_any();
        OutputStream cdr = any.create_output_stream();
        RTC.Time tm = new RTC.Time(123,127);
        RTC.TimedLong tmlong = new RTC.TimedLong(tm,11);
        RTC.TimedLongHolder tmlongholder 
                = new RTC.TimedLongHolder(tmlong);
        tmlongholder._write(cdr);
        assertEquals("8:",ReturnCode.PORT_OK,
                                 publisher.write(cdr,-1,0));
        try{
            Thread.sleep(10);
        }
        catch(java.lang.InterruptedException e) {
        }
        }

        for(int icc=0;icc<8;++icc) {
            OutputStream data  = consumer.get_m_put_data();

            RTC.TimedLong tl = new RTC.TimedLong();
            RTC.TimedLongHolder tlh 
                = new RTC.TimedLongHolder(tl);
            tlh._read(data.create_input_stream());

            assertEquals("9:",123,tlh.value.tm.sec);
            assertEquals("10:",127,tlh.value.tm.nsec);
            assertEquals("i1:",icc+4,tlh.value.data);
        }
        publisher.deactivate();
        
        
    }
    /**
     * <p> write(), pushAll() </p>
     *
     * -
     */
    public void test_pushAll_2() {
        rtcout.println(rtcout.TRACE, "test_pushAll_2");
        InPortCorbaCdrConsumerMock consumer 
                                    = new InPortCorbaCdrConsumerMock();
        BufferBase<OutputStream> buffer = new RingBuffer<OutputStream>();
        {
        Properties prop = new Properties();
        prop.setProperty("write.full_policy","do_nothing");
        buffer.init(prop);
        }
        PublisherPeriodicMock publisher = new PublisherPeriodicMock();

        Properties prop = new Properties();
        prop.setProperty("publisher.push_policy","all");
        prop.setProperty("publisher.skip_count","0");
        prop.setProperty("thread_type","default");
        prop.setProperty("measurement.exec_time","enable");
        prop.setProperty("measurement.exec_count","0");
        prop.setProperty("measurement.period_time","enable");
        prop.setProperty("measurement.period_count","0");
        prop.setProperty("publisher.push_rate","");
        publisher.init(prop);

        publisher.setConsumer(consumer);
        publisher.setBuffer(buffer);
        publisher.activate();

        for(int icc=0;icc<16;++icc) {
            org.omg.CORBA.Any any = ORBUtil.getOrb().create_any();
            OutputStream cdr = any.create_output_stream();
            RTC.Time tm = new RTC.Time(123,127);
            RTC.TimedLong tmlong = new RTC.TimedLong(tm,icc);
            RTC.TimedLongHolder tmlongholder 
                = new RTC.TimedLongHolder(tmlong);
            tmlongholder._write(cdr);

            ReturnCode ret;
            ret = publisher.write(cdr,-1,0);
            if(icc<9) {
                assertEquals(ReturnCode.PORT_OK,
                                     ret);
            }
            else {
                assertEquals(ReturnCode.BUFFER_FULL,
                                     ret);
            }
            try{
                Thread.sleep(10);
            }
            catch(java.lang.InterruptedException e) {
            }
        }
        try{
            Thread.sleep(10);
        }
        catch(java.lang.InterruptedException e) {
        }

        //Because the buffer of consumer and provider are full, 
        // data is not transmitted.
        {
        org.omg.CORBA.Any any = ORBUtil.getOrb().create_any();
        OutputStream cdr = any.create_output_stream();
        RTC.Time tm = new RTC.Time(123,127);
        RTC.TimedLong tmlong = new RTC.TimedLong(tm,16);
        RTC.TimedLongHolder tmlongholder 
            = new RTC.TimedLongHolder(tmlong);
        tmlongholder._write(cdr);
        assertEquals(ReturnCode.BUFFER_FULL,
                                 publisher.write(cdr,-1,0));
        try{
            Thread.sleep(20);
        }
        catch(java.lang.InterruptedException e) {
        }
        }

        //Check data.
        for(int icc=0;icc<8;++icc) {
            OutputStream data  = consumer.get_m_put_data();
            RTC.TimedLong tl = new RTC.TimedLong();
            RTC.TimedLongHolder tlh 
                = new RTC.TimedLongHolder(tl);
            tlh._read(data.create_input_stream());

            assertEquals("4:",123,tlh.value.tm.sec);
            assertEquals("5:",127,tlh.value.tm.nsec);
            assertEquals("6:",icc,tlh.value.data);
        }

        try{
            Thread.sleep(20);
        }
        catch(java.lang.InterruptedException e) {
        }
        // data are checked.
        for(int icc=0;icc<8;++icc) {
            OutputStream data  = consumer.get_m_put_data();

            RTC.TimedLong tl = new RTC.TimedLong();
            RTC.TimedLongHolder tlh 
                = new RTC.TimedLongHolder(tl);
            tlh._read(data.create_input_stream());

            assertEquals("7:",123,tlh.value.tm.sec);
            assertEquals("8:",127,tlh.value.tm.nsec);
            assertEquals("9:",icc+8,tlh.value.data);
        }
        {
        org.omg.CORBA.Any any = ORBUtil.getOrb().create_any();
        OutputStream cdr = any.create_output_stream();
        RTC.Time tm = new RTC.Time(123,127);
        RTC.TimedLong tmlong = new RTC.TimedLong(tm,17);
        RTC.TimedLongHolder tmlongholder 
                = new RTC.TimedLongHolder(tmlong);
        tmlongholder._write(cdr);
        assertEquals(ReturnCode.PORT_OK,
                                 publisher.write(cdr,-1,0));
        try{
            Thread.sleep(20);
        }
        catch(java.lang.InterruptedException e) {
        }
        }
        //Data are checked
        {
        OutputStream data  = consumer.get_m_put_data();

        RTC.TimedLong tl = new RTC.TimedLong();
        RTC.TimedLongHolder tlh 
            = new RTC.TimedLongHolder(tl);
        tlh._read(data.create_input_stream());

        assertEquals("4:",123,tlh.value.tm.sec);
        assertEquals("5:",127,tlh.value.tm.nsec);
        assertEquals("6:",17,tlh.value.data);

        }
        try{
            Thread.sleep(10);
        }
        catch(java.lang.InterruptedException e) {
        }

        publisher.deactivate();
        
        
    }
    /**
     * <p> pushFifo() </p>
     * 
     */
    public void test_pushFifo() {
        InPortCorbaCdrConsumerMock consumer 
                                    = new InPortCorbaCdrConsumerMock();
        BufferBase<OutputStream> buffer = new RingBuffer<OutputStream>();
        {
        Properties prop = new Properties();
        prop.setProperty("write.full_policy","do_nothing");
        buffer.init(prop);
        }
        PublisherPeriodicMock publisher = new PublisherPeriodicMock();

        Properties prop = new Properties();
        prop.setProperty("publisher.push_policy","fifo");
        prop.setProperty("publisher.skip_count","0");
        prop.setProperty("thread_type","default");
        prop.setProperty("measurement.exec_time","enable");
        prop.setProperty("measurement.exec_count","0");
        prop.setProperty("measurement.period_time","enable");
        prop.setProperty("measurement.period_count","0");
        prop.setProperty("publisher.push_rate","100");
        publisher.init(prop);

        publisher.setConsumer(consumer);
        publisher.setBuffer(buffer);
        publisher.activate();

        for(int icc=0;icc<8;++icc) {
            OutputStream cdr  = toStream(icc,123,127);
            assertEquals(ReturnCode.PORT_OK,
                                 publisher.write(cdr,-1,0));

            this.sleep(10);
        }

        //The buffer of provier is not full and calls write(). 
        {
        OutputStream cdr  = toStream(8,123,127);
        assertEquals(ReturnCode.PORT_OK,
                                 publisher.write(cdr,-1,0));
        this.sleep(20);
        }
        {
        OutputStream cdr  = toStream(9,123,127);
        assertEquals(ReturnCode.BUFFER_FULL,
                                 publisher.write(cdr,-1,0));
        this.sleep(10);
        }

        //Four data is acquired from the buffer of provider.
        for(int icc=0;icc<4;++icc) {
            OutputStream data  = consumer.get_m_put_data();
            RTC.TimedLong tl = new RTC.TimedLong();
            RTC.TimedLongHolder tlh 
                = new RTC.TimedLongHolder(tl);
            tlh._read(data.create_input_stream());

            assertEquals("4:",123,tlh.value.tm.sec);
            assertEquals("5:",127,tlh.value.tm.nsec);
            assertEquals("6:",icc,tlh.value.data);
        }
        this.sleep(40);

        //The buffer of provier is not full and calls write(). 
        {
        OutputStream cdr  = toStream(10,123,127);
        assertEquals(ReturnCode.PORT_OK,
                                 publisher.write(cdr,-1,0));
        this.sleep(30);
        }
        {
        OutputStream cdr  = toStream(11,123,127);
        assertEquals(ReturnCode.PORT_OK,
                                 publisher.write(cdr,-1,0));
        this.sleep(20);
        }
        {
        OutputStream cdr  = toStream(12,123,127);
        assertEquals(ReturnCode.PORT_OK,
                                 publisher.write(cdr,-1,0));
        this.sleep(20);
        }
        {
        OutputStream cdr  = toStream(13,123,127);
        assertEquals(ReturnCode.BUFFER_FULL,
                                 publisher.write(cdr,-1,0));
        this.sleep(10);
        }

        for(int icc=0;icc<8;++icc) {
            OutputStream data  = consumer.get_m_put_data();
            RTC.TimedLong tl = new RTC.TimedLong();
            RTC.TimedLongHolder tlh 
                = new RTC.TimedLongHolder(tl);
            tlh._read(data.create_input_stream());

            assertEquals("7:",123,tlh.value.tm.sec);
            assertEquals("8:",127,tlh.value.tm.nsec);
            assertEquals("9:",icc+4,tlh.value.data);

        }
        this.sleep(20);
        for(int icc=0;icc<2;++icc) {
            OutputStream data  = consumer.get_m_put_data();
            RTC.TimedLong tl = new RTC.TimedLong();
            RTC.TimedLongHolder tlh 
                = new RTC.TimedLongHolder(tl);
            tlh._read(data.create_input_stream());

            assertEquals("7:",123,tlh.value.tm.sec);
            assertEquals("8:",127,tlh.value.tm.nsec);
            assertEquals("9:",icc+12,tlh.value.data);
        }


        this.sleep(10);
        publisher.deactivate();
        
    }
    /**
     * <p> write(), pushFifo() </p>
     *
     * -
     */
    public void test_pushFifo_2() {
        InPortCorbaCdrConsumerMock consumer 
                                    = new InPortCorbaCdrConsumerMock();
        BufferBase<OutputStream> buffer = new RingBuffer<OutputStream>();
        {
        Properties prop = new Properties();
        prop.setProperty("write.full_policy","do_nothing");
        buffer.init(prop);
        }
        PublisherPeriodicMock publisher = new PublisherPeriodicMock();

        Properties prop = new Properties();
        prop.setProperty("publisher.push_policy","fifo");
        prop.setProperty("publisher.skip_count","0");
        prop.setProperty("thread_type","default");
        prop.setProperty("measurement.exec_time","enable");
        prop.setProperty("measurement.exec_count","0");
        prop.setProperty("measurement.period_time","enable");
        prop.setProperty("measurement.period_count","0");
        prop.setProperty("publisher.push_rate","100");
        publisher.init(prop);

        publisher.setConsumer(consumer);
        publisher.setBuffer(buffer);
        publisher.activate();

        for(int icc=0;icc<16;++icc) {
            OutputStream cdr  = toStream(icc,123,127);

            ReturnCode ret;
            ret = publisher.write(cdr,-1,0);
            if(icc<9) {
                assertEquals(ReturnCode.PORT_OK,
                                     ret);
            }
            else {
                assertEquals(ReturnCode.BUFFER_FULL,
                                     ret);
            }
            this.sleep(20);

        }

        //Because the buffer of consumer and provider are full, 
        // data is not transmitted.
        {
        OutputStream cdr  = toStream(16,123,127);
        assertEquals(ReturnCode.BUFFER_FULL,
                                 publisher.write(cdr,-1,0));
        this.sleep(10);
        }

        //Data are checked.
        for(int icc=0;icc<8;++icc) {
            OutputStream data  = consumer.get_m_put_data();

            RTC.TimedLong tl = new RTC.TimedLong();
            RTC.TimedLongHolder tlh 
                = new RTC.TimedLongHolder(tl);
            tlh._read(data.create_input_stream());

            assertEquals("9:",123,tlh.value.tm.sec);
            assertEquals("10:",127,tlh.value.tm.nsec);
            assertEquals("11:",icc,tlh.value.data);
        }

        this.sleep(80);
        // Data are transmitted.
        for(int icc=0;icc<8;++icc) {
            OutputStream cdr  = toStream(17+icc,123,127);

            ReturnCode ret;
            ret = publisher.write(cdr,-1,0);
            if(icc<1) {
                assertEquals(ReturnCode.PORT_OK,
                                     ret);
            }
            else {
                assertEquals(ReturnCode.BUFFER_FULL,
                                     ret);
            }
            this.sleep(10);
        }
        this.sleep(80);

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
        this.sleep(10);
        {
        OutputStream cdr  = toStream(25,123,127);
        assertEquals(ReturnCode.PORT_OK,
                                 publisher.write(cdr,-1,0));
        this.sleep(10);
        }
        this.sleep(80);

        for(int icc=0;icc<8;++icc) {
            OutputStream data  = consumer.get_m_put_data();
            RTC.TimedLong tl = new RTC.TimedLong();
            RTC.TimedLongHolder tlh 
                = new RTC.TimedLongHolder(tl);
            tlh._read(data.create_input_stream());

            assertEquals("4:",123,tlh.value.tm.sec);
            assertEquals("5:",127,tlh.value.tm.nsec);
            assertEquals("6:",icc+17,tlh.value.data);
        }


        this.sleep(10);
        publisher.deactivate();
        
        
    }

    /**
     * <p> pushSklip() </p>
     * 
     */
    public void test_pushSkip() {
        InPortCorbaCdrConsumerMock consumer 
                                    = new InPortCorbaCdrConsumerMock();
        BufferBase<OutputStream> buffer = new RingBuffer<OutputStream>();
        {
        Properties prop = new Properties();
        prop.setProperty("write.full_policy","do_nothing");
        buffer.init(prop);
        }
        PublisherPeriodicMock publisher = new PublisherPeriodicMock();

        Properties prop = new Properties();
        prop.setProperty("publisher.push_policy","skip");
        prop.setProperty("publisher.skip_count","1");
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

            assertEquals(ReturnCode.PORT_OK,
                                 publisher.write(cdr,-1,0));

            this.sleep(10);
        }

        //The buffer is full and calls write(). 
        {
        OutputStream cdr  = toStream(16,123,127);
        assertEquals(ReturnCode.PORT_OK,
                                 publisher.write(cdr,-1,0));
        this.sleep(10);
        }
        {
        OutputStream cdr  = toStream(17,123,127);
        assertEquals(ReturnCode.PORT_OK,
                                 publisher.write(cdr,-1,0));
        this.sleep(10);
        }

        //Four data is acquired from the buffer of provider.
        for(int icc=0;icc<4;++icc) {
            OutputStream data  = consumer.get_m_put_data();
            RTC.TimedLong tl = new RTC.TimedLong();
            RTC.TimedLongHolder tlh 
                = new RTC.TimedLongHolder(tl);
            tlh._read(data.create_input_stream());

            assertEquals("4:",123,tlh.value.tm.sec);
            assertEquals("5:",127,tlh.value.tm.nsec);
            assertEquals("6:",icc*2+1,tlh.value.data);
        }

        this.sleep(40);
        //The buffer of provier is not full and calls write(). 
        {
        OutputStream cdr  = toStream(18,123,127);
        assertEquals(ReturnCode.PORT_OK,
                                 publisher.write(cdr,-1,0));
        this.sleep(10);
        }
        {
        OutputStream cdr  = toStream(19,123,127);
        assertEquals(ReturnCode.PORT_OK,
                                 publisher.write(cdr,-1,0));
        this.sleep(10);
        }

        assertEquals(6,
                              consumer.get_m_put_data_len());
        for(int icc=0;icc<6;++icc) {
            OutputStream data  = consumer.get_m_put_data();
            RTC.TimedLong tl = new RTC.TimedLong();
            RTC.TimedLongHolder tlh 
                = new RTC.TimedLongHolder(tl);
            tlh._read(data.create_input_stream());

            assertEquals("7:",123,tlh.value.tm.sec);
            assertEquals("8:",127,tlh.value.tm.nsec);
            assertEquals("9:",icc*2+9,tlh.value.data);

        }
       
        this.sleep(100);
        publisher.deactivate();
    }
    /**
     * <p> write(), pushSkip() </p>
     *
     * -
     */
    public void test_pushSkip_2() {
        InPortCorbaCdrConsumerMock consumer 
                                    = new InPortCorbaCdrConsumerMock();
        BufferBase<OutputStream> buffer = new RingBuffer<OutputStream>();
        {
        Properties prop = new Properties();
        prop.setProperty("write.full_policy","do_nothing");
        buffer.init(prop);
        }
        PublisherPeriodicMock publisher = new PublisherPeriodicMock();

        Properties prop = new Properties();
        prop.setProperty("publisher.push_policy","skip");
        prop.setProperty("publisher.skip_count","1");
        prop.setProperty("thread_type","default");
        prop.setProperty("measurement.exec_time","enable");
        prop.setProperty("measurement.exec_count","0");
        prop.setProperty("measurement.period_time","enable");
        prop.setProperty("measurement.period_count","0");
        publisher.init(prop);

        publisher.setConsumer(consumer);
        publisher.setBuffer(buffer);
        publisher.activate();

        //The buffer of provider is made full and write() is called. 
        for(int icc=0;icc<25;++icc) {
            OutputStream cdr  = toStream(icc,123,127);

            ReturnCode ret;
            ret = publisher.write(cdr,-1,0);
            if(icc<18) {
                assertEquals(ReturnCode.PORT_OK,
                                     ret);
            }
            else {
                assertEquals(ReturnCode.BUFFER_FULL,
                                     ret);
            }
            this.sleep(20);

        }

        //Because the buffer of consumer and provider are full, 
        // data is not transmitted.
        {
        OutputStream cdr  = toStream(25,123,127);

        assertEquals(ReturnCode.BUFFER_FULL,
                                 publisher.write(cdr,-1,0));
        this.sleep(10);
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

        this.sleep(20);
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
        this.sleep(20);
        {
        OutputStream cdr  = toStream(26,123,127);
        assertEquals(ReturnCode.PORT_OK,
                                 publisher.write(cdr,-1,0));
        this.sleep(10);
        }
        {
        OutputStream cdr  = toStream(27,123,127);
        assertEquals(ReturnCode.PORT_OK,
                                 publisher.write(cdr,-1,0));
        this.sleep(10);
        }
        //Data is checked.
        {
        OutputStream data  = consumer.get_m_put_data();
        RTC.TimedLong tl = new RTC.TimedLong();
        RTC.TimedLongHolder tlh 
            = new RTC.TimedLongHolder(tl);
        tlh._read(data.create_input_stream());

        assertEquals("7:",123,tlh.value.tm.sec);
        assertEquals("8:",127,tlh.value.tm.nsec);
        assertEquals("9:",26,tlh.value.data);
        }

        this.sleep(10);
        publisher.deactivate();
        
        
    }
    /**
     * <p> pushNew() </p>
     * 
     */
    public void test_pushNew() {
        InPortCorbaCdrConsumerMock consumer 
                                    = new InPortCorbaCdrConsumerMock();
        BufferBase<OutputStream> buffer = new RingBuffer<OutputStream>();
        {
        Properties prop = new Properties();
        prop.setProperty("write.full_policy","do_nothing");
        buffer.init(prop);
        }
        PublisherPeriodicMock publisher = new PublisherPeriodicMock();

        Properties prop = new Properties();
        prop.setProperty("publisher.push_policy","new");
        prop.setProperty("publisher.skip_count","0");
        prop.setProperty("thread_type","default");
        prop.setProperty("measurement.exec_time","enable");
        prop.setProperty("measurement.exec_count","0");
        prop.setProperty("measurement.period_time","enable");
        prop.setProperty("measurement.period_count","0");
        prop.setProperty("publisher.push_rate","100");
        publisher.init(prop);

        publisher.setConsumer(consumer);
        publisher.setBuffer(buffer);
        publisher.activate();

        //Eight data is not transmitted. 
        //Seven of the latest data is transmitted.
        for(int icc=0;icc<8;++icc) {
            OutputStream cdr  = toStream(icc,123,127);

            assertEquals(ReturnCode.PORT_OK,
                                 publisher.write(cdr,-1,0));

        }

  
        this.sleep(10);
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

            assertEquals("7:",123,tlh.value.tm.sec);
            assertEquals("8:",127,tlh.value.tm.nsec);
            assertEquals("9:",7,tlh.value.data);
        }

        this.sleep(10);
        publisher.deactivate();
    }

    /**
     * <p>write() </p>
     * The procedure is written disregarding it.
     * 
     */
    public void test_write() {
        InPortCorbaCdrConsumerMock consumer 
                                    = new InPortCorbaCdrConsumerMock();
        BufferBase<OutputStream> buffer = new RingBuffer<OutputStream>();
        {
        Properties prop = new Properties();
        prop.setProperty("write.full_policy","do_nothing");
        buffer.init(prop);
        }
        PublisherPeriodicMock publisher = new PublisherPeriodicMock();

        Properties prop = new Properties();
        prop.setProperty("publisher.push_policy","all");
        prop.setProperty("publisher.skip_count","0");
        prop.setProperty("thread_type","default");
        prop.setProperty("measurement.exec_time","enable");
        prop.setProperty("measurement.exec_count","0");
        prop.setProperty("measurement.period_time","enable");
        prop.setProperty("measurement.period_count","0");
        publisher.init(prop);

        //
        {
        OutputStream cdr  = toStream(123,123,127);
        assertEquals(ReturnCode.PRECONDITION_NOT_MET,
                                 publisher.write(cdr,-1,0));
        this.sleep(10);
        }

        //
        publisher.setBuffer(buffer);
        {
        OutputStream cdr  = toStream(123,123,127);
        assertEquals(ReturnCode.PRECONDITION_NOT_MET,
                                 publisher.write(cdr,-1,0));
        this.sleep(10);
        }

        //
        publisher.setConsumer(consumer);
        {
        OutputStream cdr  = toStream(123,123,127);
        assertEquals(ReturnCode.PORT_OK,
                                 publisher.write(cdr,-1,0));
        this.sleep(10);
        }

    }

    protected void sleep(int time){
        try{
            Thread.sleep(time);
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
     * <p>release()メソッドのテスト
     * <ul>
     * <li>release()メソッド呼出によりPublisherの動作が停止するか？</li>
     * </ul>
     * </p>
     */
/*
    public void test_release() {
        CounterConsumer consumer = new CounterConsumer();
        Properties prop = new Properties();
        prop.setProperty("dataport.push_rate", "10"); // 10 [Hz]
        PublisherPeriodic publisher = new PublisherPeriodic(consumer, prop);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Publisherの動作を停止させる
        publisher.release();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // この時点での呼出回数を記録する
        int countReleased = consumer.getCount();
        
        // さらにConsumerがコールバックされ得る時間を与える
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // この時点での呼出回数を取得し、先に記録しておいた回数から変化がない
        // （つまり、Publisherの動作が停止している）ことを確認する
        int countSleeped = consumer.getCount();
        assertEquals(countReleased, countSleeped);
    }
*/
    /**
     * <p>PublisherによるConsumer呼出間隔精度のテスト
     * <ul>
     * <li>Publisherに指定した時間間隔で、正しくConsumerがコールバックされるか？</li>
     * </ul>
     * </p>
     */
/*
    public void test_interval_accuracy() {
        MockConsumer consumer = new MockConsumer();
        Properties prop = new Properties();
        prop.setProperty("dataport.push_rate", "10"); // 10 [Hz]
        PublisherPeriodic publisher = new PublisherPeriodic(consumer, prop);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Publisherの動作を停止させる
        publisher.release();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // 指定した時間間隔で正しくConsumerがコールバックされているか？
        long permissibleTickMin = (long)(100 * 0.9);
        long permissibleTickMax = (long)(100 * 1.1);
        Vector<Long> intervalTicks = consumer.getIntervalTicks();
        assertTrue("1:",intervalTicks.size() > 0);

        for( int i = 0; i < intervalTicks.size(); i++) {
            long tick = intervalTicks.get(i);
            assertTrue("2:"+i,(permissibleTickMin <= tick) && (tick <= permissibleTickMax));
        }
    }
*/

    /**
     * 
     * 
     */
    class PublisherPeriodicMock extends PublisherPeriodic {
      public PublisherPeriodicMock() {
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
//                 throw str;
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
        /*!
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
      /*!
       * 
       * 
       */
      int get_m_put_data_len() {
          int ic;
          ic = (int)m_buffer.readable();

          return ic;
      }


      /*!
       * 
       * 
       */
/*
      void set_m_mode(int mode)
      {
          m_test_mode = mode;
      }
*/
       BufferBase<OutputStream> m_buffer;
/*
       ::OpenRTM::CdrData  m_put_data;
*/
       int m_test_mode;
    }


}
