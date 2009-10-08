package jp.go.aist.rtm.RTC.port;

import junit.framework.TestCase;

import org.omg.CORBA.ORB;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.PortableServer.POA;

import _SDOPackage.NVListHolder;

import jp.go.aist.rtm.RTC.BufferFactory;
import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.buffer.RingBuffer;
import jp.go.aist.rtm.RTC.buffer.CdrRingBuffer;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.ORBUtil;
import jp.go.aist.rtm.RTC.util.NVListHolderFactory;

public class InPortCorbaCdrConsumerTest extends TestCase {
    /*!
     * 
     * 
     */
    class InPortCorbaCdrConsumerMock extends InPortCorbaCdrConsumer {
        /**
         * 
         * 
         */
        public InPortCorbaCdrConsumerMock() {
        }
        /**
         * for check
         */
        org.omg.CORBA.Object get_m_objre() {
            return m_objref;
        }
    };

    private ORB m_orb;
    private POA m_poa;
    /**
     * <p> Test initialization </p>
     */
    protected void setUp() throws Exception {
        super.setUp();
        java.util.Properties props = new java.util.Properties();
        this.m_orb = ORBUtil.getOrb();
        this.m_poa = org.omg.PortableServer.POAHelper.narrow(
                this.m_orb.resolve_initial_references("RootPOA"));
        this.m_poa.the_POAManager().activate();
    }
    /**
     * <p> Test finalization </p>
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    /**
     * <p>  </p>
     * 
     */
    public void test_case0() {
        //
        //
        //
        InPortCorbaCdrConsumerMock consumer = new InPortCorbaCdrConsumerMock();

        RTC.ConnectorProfile prof = new RTC.ConnectorProfile();
        boolean ret;
        byte[] testdata = { 12,34,56,78,90,23,45, };

        {
        NVListHolder holder = new NVListHolder(prof.properties);
        ret = consumer.subscribeInterface(holder);
        prof.properties = holder.value;
        }
        //subscribeInterface() returns false
        // because it has called subscribeInterface() before setting IOR.
        assertEquals(false, ret);

        InPortCorbaCdrProvider provider = new InPortCorbaCdrProvider();

        {
        NVListHolder holder = new NVListHolder(prof.properties);
        CORBA_SeqUtil.push_back(holder,
                                NVUtil.newNV("dataport.interface_type",
                                               "corba_cdr"));
        prof.properties = holder.value;
        }
        {
        NVListHolder holder = new NVListHolder(prof.properties);
        provider.publishInterface(holder);
        prof.properties = holder.value;
        }


        {
        NVListHolder holder = new NVListHolder(prof.properties);
        ret = consumer.subscribeInterface(holder);
        prof.properties = holder.value;
        assertEquals(true, ret);
        }

        org.omg.CORBA.Any any = m_orb.create_any(); 
        OutputStream indata = any.create_output_stream();
        ReturnCode retcode;

        RTC.Time tm = new RTC.Time(123,127);
        RTC.TimedOctetSeq tmoct = new RTC.TimedOctetSeq(tm,testdata);
        RTC.TimedOctetSeqHolder tmoctholder 
            = new RTC.TimedOctetSeqHolder(tmoct);
        tmoctholder._write(indata);
        
         

        //put() is called before the buffer is set to the provider.(error) 
        retcode = consumer.put(indata);
        assertEquals(ReturnCode.PORT_ERROR, retcode);

        RingBuffer<OutputStream> buffer;
        final BufferFactory<RingBuffer<OutputStream>,String> factory 
            = BufferFactory.instance();
        factory.addFactory("ring_buffer",
                    new CdrRingBuffer(),
                    new CdrRingBuffer());
        buffer = factory.createObject("ring_buffer");
        provider.setBuffer(buffer);

        for(int ic=0;ic<8;++ic) {
            retcode = consumer.put(indata);
            assertEquals(ReturnCode.PORT_OK, retcode);
         }

        //The buffer is full, and put() is called. 
        retcode = consumer.put(indata);
        assertEquals(ReturnCode.BUFFER_FULL, retcode);


        for(int icc=0;icc<8;++icc) {
            OutputStream cdr = any.create_output_stream();
            DataRef<OutputStream> ref = new DataRef<OutputStream>(cdr);
            buffer.read(ref);
            RTC.TimedOctetSeq to = new RTC.TimedOctetSeq();
            RTC.TimedOctetSeqHolder toh 
                = new RTC.TimedOctetSeqHolder(to);
            toh._read(ref.v.create_input_stream());

            assertEquals(123,toh.value.tm.sec); 
            assertEquals(127,toh.value.tm.nsec); 
            for(int ic=0;ic<testdata.length;++ic) {
                assertEquals(testdata[ic],toh.value.data[ic]);
            }
        }
        assertTrue(consumer.get_m_objre()!=null);
        {
        NVListHolder holder = new NVListHolder(prof.properties);
        consumer.unsubscribeInterface(holder);
        prof.properties = holder.value;
        }
        assertTrue(consumer.get_m_objre()==null);

        int index;
        {
        NVListHolder holder = new NVListHolder(prof.properties);
        index = NVUtil.find_index(holder,
                                   "dataport.corba_cdr.inport_ior");
        prof.properties = holder.value;
        }

         String ior = new String();
         try {
             ior = prof.properties[index].value.extract_wstring();
             org.omg.CORBA.Object var = m_orb.string_to_object(ior);
             org.omg.PortableServer.Servant ser 
                     = m_poa.reference_to_servant(var);
	     m_poa.deactivate_object(m_poa.servant_to_id(ser));
         }
         catch( org.omg.PortableServer.POAPackage.WrongAdapter e){
         }
         catch( org.omg.PortableServer.POAPackage.WrongPolicy e){
         }
         catch( org.omg.PortableServer.POAPackage.ServantNotActive e){
         }
         catch( org.omg.PortableServer.POAPackage.ObjectNotActive e){
         }
         catch(org.omg.CORBA.BAD_OPERATION e){
         }

    }
    
    /**
     * <p>  </p>
     * 
     */
    public void test_timedlong() {
        //
        //
        //
        InPortCorbaCdrConsumerMock consumer = new InPortCorbaCdrConsumerMock();

        RTC.ConnectorProfile prof = new RTC.ConnectorProfile();
        boolean ret;
        int[] testdata = { 12345,67890,123456,789012,4,8,15,16,23,42 };

        {
        NVListHolder holder = new NVListHolder(prof.properties);
        ret = consumer.subscribeInterface(holder);
        prof.properties = holder.value;
        }
        //subscribeInterface() returns false
        // because it has called subscribeInterface() before setting IOR.
        assertEquals(false, ret);

        InPortCorbaCdrProvider provider = new InPortCorbaCdrProvider();

        {
        NVListHolder holder = new NVListHolder(prof.properties);
        CORBA_SeqUtil.push_back(holder,
                                NVUtil.newNV("dataport.interface_type",
                                               "corba_cdr"));
        prof.properties = holder.value;
        }
        {
        NVListHolder holder = new NVListHolder(prof.properties);
        provider.publishInterface(holder);
        prof.properties = holder.value;
        }


        {
        NVListHolder holder = new NVListHolder(prof.properties);
        ret = consumer.subscribeInterface(holder);
        prof.properties = holder.value;
        assertEquals(true, ret);
        }

        org.omg.CORBA.Any any = m_orb.create_any(); 
        OutputStream indata = any.create_output_stream();
        ReturnCode retcode;

        RTC.Time tm = new RTC.Time(123,127);
        RTC.TimedLongSeq tmlong = new RTC.TimedLongSeq(tm,testdata);
        RTC.TimedLongSeqHolder tmlongholder 
            = new RTC.TimedLongSeqHolder(tmlong);
        tmlongholder._write(indata);
        

        RingBuffer<OutputStream> buffer;
        final BufferFactory<RingBuffer<OutputStream>,String> factory 
            = BufferFactory.instance();
        factory.addFactory("ring_buffer",
                    new CdrRingBuffer(),
                    new CdrRingBuffer());
        buffer = factory.createObject("ring_buffer");
        provider.setBuffer(buffer);

        for(int ic=0;ic<8;++ic) {
            retcode = consumer.put(indata);
            assertEquals(ReturnCode.PORT_OK, retcode);
        }


        for(int icc=0;icc<8;++icc) {
            OutputStream cdr = any.create_output_stream();
            DataRef<OutputStream> ref = new DataRef<OutputStream>(cdr);
            buffer.read(ref);
            RTC.TimedLongSeq tl = new RTC.TimedLongSeq();
            RTC.TimedLongSeqHolder tlh 
                = new RTC.TimedLongSeqHolder(tl);
            tlh._read(ref.v.create_input_stream());

            assertEquals(123,tlh.value.tm.sec); 
            assertEquals(127,tlh.value.tm.nsec); 
            for(int ic=0;ic<testdata.length;++ic) {
                assertEquals(testdata[ic],tlh.value.data[ic]);
            }
        }
        assertTrue(consumer.get_m_objre()!=null);
        {
        NVListHolder holder = new NVListHolder(prof.properties);
        consumer.unsubscribeInterface(holder);
        prof.properties = holder.value;
        }
        assertTrue(consumer.get_m_objre()==null);

        int index;
        {
        NVListHolder holder = new NVListHolder(prof.properties);
        index = NVUtil.find_index(holder,
                                   "dataport.corba_cdr.inport_ior");
        prof.properties = holder.value;
        }

        String ior = new String();
        try {
             ior = prof.properties[index].value.extract_wstring();
             org.omg.CORBA.Object var = m_orb.string_to_object(ior);
             org.omg.PortableServer.Servant ser 
                     = m_poa.reference_to_servant(var);
	     m_poa.deactivate_object(m_poa.servant_to_id(ser));
        }
        catch( org.omg.PortableServer.POAPackage.WrongAdapter e){
        }
        catch( org.omg.PortableServer.POAPackage.WrongPolicy e){
        }
        catch( org.omg.PortableServer.POAPackage.ServantNotActive e){
        }
        catch( org.omg.PortableServer.POAPackage.ObjectNotActive e){
        }
        catch(org.omg.CORBA.BAD_OPERATION e){
        }

    }
  }

