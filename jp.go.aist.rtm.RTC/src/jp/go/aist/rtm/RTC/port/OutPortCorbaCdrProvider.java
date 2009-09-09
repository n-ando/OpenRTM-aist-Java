package jp.go.aist.rtm.RTC.port;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Any;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

import java.io.IOException;

import _SDOPackage.NVListHolder;
import OpenRTM.CdrDataHelper;
import OpenRTM.CdrDataHolder;
import OpenRTM.OutPortCdrPOA;
import OpenRTM.OutPortCdrOperations;
import OpenRTM.PortStatus;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.buffer.ReturnCode;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.POAUtil;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.log.Logbuf;

/**
 * <p> OutPortCorbaCdrProvider </p>
 * <p> OutPortCorbaCdrProvider class </p>
 *
 * <p> This is an implementation class of OutPort Provider that uses </p>
 * <p> CORBA for mean of communication. </p>
 *
 * @param DataType Data type held by the buffer that is assigned to this 
 *        provider
 *
 */
//public class OutPortCorbaCdrProvider extends OutPortProviderImpl implements OutPortCdrOperations   {
//public class OutPortCorbaCdrProvider extends OutPortCdrPOA implements OutPortProvider   {
public class OutPortCorbaCdrProvider extends OutPortProviderImpl   {
    /**
     * <p> Constructor </p>
     *
     */
    public OutPortCorbaCdrProvider() {
        m_buffer = null;
        rtcout = new Logbuf("OutPortCorbaCdrProvider");
        rtcout.setLevel("PARANOID");
        // PortProfile setting
        super.setInterfaceType("corba_cdr");
    
        // ConnectorProfile setting
        m_objref = this._this();
    
        // set outPort's reference
        ORB orb = Manager.instance().getORB();
        CORBA_SeqUtil.
        push_back(super.m_properties,
                  NVUtil.newNV("dataport.corba_cdr.outport_ior",
                              orb.object_to_string(m_objref)));
        CORBA_SeqUtil.
        push_back(super.m_properties,
                  NVUtil.newNV("dataport.corba_cdr.outport_ref",
                                 m_objref, OpenRTM.OutPortCdr.class ));
    }
    /**
     * 
     */
    public OpenRTM.OutPortCdr _this() {
        
        if (this.m_objref == null) {
            try {
                this.m_objref = OpenRTM.OutPortCdrHelper.narrow(POAUtil.getRef(this));
                
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        
        return this.m_objref;
    }

    /**
     *
     * <p> Initializing configuration </p>
     *
     * <p> This operation would be called to configure in initialization. </p>
     * <p> In the concrete class, configuration should be performed </p>
     * <p> getting appropriate information from the given Properties data. </p>
     * <p> This function might be called right after instantiation and </p>
     * <p> connection sequence respectivly.  Therefore, this function </p>
     * <p> should be implemented assuming multiple call. </p>
     *
     * @param prop Configuration information
     *
     */
    public void init(Properties prop) {
    }

    /**
     * <p> Setting outside buffer's pointer </p>
     *
     * <p> A pointer to a buffer from which OutPortProvider retrieve data. </p>
     * <p> If already buffer is set, previous buffer's pointer will be </p>
     * <p> overwritten by the given pointer to a buffer.  Since </p>
     * <p> OutPortProvider does not assume ownership of the buffer </p>
     * <p> pointer, destructor of the buffer should be done by user. </p>
     * 
     * @param buffer A pointer to a data buffer to be used by OutPortProvider
     *
     */
    public void setBuffer(BufferBase<InputStream> buffer){
        m_buffer = buffer;
    }
    
    /**
     * <p> [CORBA interface] Get data from the buffer </p>
     *
     * <p> Get data from the internal buffer. </p>
     *
     * @param data
     * @return Data got from the buffer.
     *
     */
    public OpenRTM.PortStatus get(OpenRTM.CdrDataHolder data) {
        rtcout.println(rtcout.PARANOID, "get()");

        data = new OpenRTM.CdrDataHolder();
        if (m_buffer == null) {
            return OpenRTM.PortStatus.UNKNOWN_ERROR;
        }

        if (m_buffer.empty()) {
            return OpenRTM.PortStatus.BUFFER_EMPTY;
        }

        InputStream cdr = null;

        jp.go.aist.rtm.RTC.buffer.ReturnCode ret 
                          = m_buffer.read(new DataRef<InputStream>(cdr));
        if (ret == jp.go.aist.rtm.RTC.buffer.ReturnCode.BUFFER_OK) {
            try {
                int len = cdr.available();
                rtcout.println(rtcout.PARANOID, "converted CDR data size: "+len);
                data.value = new byte[len];
                cdr.read_octet_array(data.value, 0, len);
                return convertReturn(ret);
            }
            catch (IOException e){
                return OpenRTM.PortStatus.BUFFER_EMPTY;
            }
        }
        return convertReturn(ret);
    }
    /**
     * <p> convertReturn </p>
     *
     */
    protected OpenRTM.PortStatus convertReturn(jp.go.aist.rtm.RTC.buffer.ReturnCode status) {
        switch (status) {
            case BUFFER_OK:
                return OpenRTM.PortStatus.from_int(0);
            case BUFFER_EMPTY:
                return OpenRTM.PortStatus.from_int(3);
            case TIMEOUT:
                return OpenRTM.PortStatus.from_int(4);
            case PRECONDITION_NOT_MET:
                return OpenRTM.PortStatus.from_int(1);
            default:
                return OpenRTM.PortStatus.from_int(5);
        }
    }


    
    private Logbuf rtcout;
    private BufferBase<InputStream> m_buffer;
    private OpenRTM.OutPortCdr m_objref;
}
