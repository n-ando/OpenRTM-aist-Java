package jp.go.aist.rtm.RTC.port;

import junit.framework.TestCase;

import org.omg.CORBA.ORB;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.PortableServer.POA;

import java.util.logging.FileHandler;

import _SDOPackage.NVListHolder;

import jp.go.aist.rtm.RTC.BufferFactory;
import jp.go.aist.rtm.RTC.PeriodicTask;
import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.buffer.RingBuffer;
import jp.go.aist.rtm.RTC.buffer.CdrRingBuffer;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.ORBUtil;
import jp.go.aist.rtm.RTC.util.NVListHolderFactory;
import jp.go.aist.rtm.RTC.log.Logbuf;

public class OutPortCorbaCdrProviderTest extends TestCase {
    /**
     * 
     * 
     */
    class OutPortCorbaCdrProviderMock extends OutPortCorbaCdrProvider {
        /**
         * 
         * 
         */
        public OutPortCorbaCdrProviderMock() {
        }
        /**
         *  for check
         */
        _SDOPackage.NVListHolder get_m_properties() {
            return m_properties;
        }
    };
    protected static Logbuf rtcout = null; 
    protected FileHandler m_fh;
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
        if(rtcout == null){
            rtcout = new Logbuf("Manager");
            m_fh = null; 
            rtcout.setLevel("SILENT");
            String logfile = "OutPortCorbaCdrProvider.log";
            m_fh = new FileHandler(logfile);
            rtcout.addStream(m_fh);
            rtcout.setLevel("PARANOID");

        }
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
     *  
     */
    protected OutputStream toStream(byte[] data, int sec, int nsec){
            org.omg.CORBA.Any any = ORBUtil.getOrb().create_any();
            OutputStream cdr = any.create_output_stream();
            RTC.Time tm = new RTC.Time(sec,nsec);
            RTC.TimedOctetSeq tmlong = new RTC.TimedOctetSeq(tm,data);
            RTC.TimedOctetSeqHolder tmlongholder 
                = new RTC.TimedOctetSeqHolder(tmlong);
            tmlongholder._write(cdr);
            return cdr;

    }
    /**
     *  
     */
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
     * 
     */
    public void test_case0() {

        rtcout.println(rtcout.TRACE, "test_case0");
        //
        //
        //
        OutPortCorbaCdrProviderMock provider = 
            new OutPortCorbaCdrProviderMock();

        int index;
        //
        index = NVUtil.find_index(provider.get_m_properties(),
                                   "dataport.corba_cdr.outport_ior");
        assertTrue("1",0<=index);

        //
        index = NVUtil.find_index(provider.get_m_properties(),
                                   "dataport.corba_cdr.outport_ref");
        assertTrue("2",0<=index);


        //init() function is not implemented. 
        //provider.init();

         
        //RingBuffer<InputStream> buffer;
        RingBuffer<OutputStream> buffer;
        //final BufferFactory<RingBuffer<InputStream>,String> factory 
        final BufferFactory<RingBuffer<OutputStream>,String> factory 
            = BufferFactory.instance();
        factory.addFactory("ring_buffer",
                    new CdrRingBuffer(),
                    new CdrRingBuffer());
        buffer = factory.createObject("ring_buffer");

        OpenRTM.PortStatus retcode = null;

        byte[] cdr_data = new byte[256];
        OpenRTM.CdrDataHolder cdr_data_ref = new OpenRTM.CdrDataHolder(cdr_data);

        //get() is called without setting the buffer.
        retcode = provider.get(cdr_data_ref);
        assertEquals("3",OpenRTM.PortStatus.UNKNOWN_ERROR, retcode);
        
        provider.setBuffer(buffer);

        //get() is called without setting data. (empty)
        retcode = provider.get(cdr_data_ref);
        assertEquals("4",OpenRTM.PortStatus.BUFFER_EMPTY, retcode);

        byte testdata[] = { 4, 8, 15, 16, 23, 42, 49, 50};
        OutputStream cdr = toStream(testdata, 0, 0);
        //buffer.write(cdr.create_input_stream());
        buffer.write(cdr);

        retcode = provider.get(cdr_data_ref);
        assertEquals("5",OpenRTM.PortStatus.PORT_OK, retcode);

        org.omg.CORBA.Any any = ORBUtil.getOrb().create_any();
        OutputStream ostream = any.create_output_stream();
        ostream.write_octet_array(cdr_data_ref.value,0,cdr_data_ref.value.length);
        
        RTC.TimedOctetSeqHolder holder 
              = new RTC.TimedOctetSeqHolder();
        holder._read(ostream.create_input_stream());

        
        for(int ic=0;ic<testdata.length;++ic){
            assertEquals("6:"+ic+":",holder.value.data[ic],testdata[ic]);
        }
        

        _SDOPackage.NVListHolder list = provider.get_m_properties();
        {
        index = NVUtil.find_index(list,
                                   "dataport.corba_cdr.outport_ior");
        }

        String ior = null;
        try {
            ior = list.value[index].value.extract_wstring();
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

