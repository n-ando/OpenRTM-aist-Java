package jp.go.aist.rtm.RTC.port;

import org.omg.CORBA.ORB;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.Object;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

import _SDOPackage.NVListHolder;
import OpenRTM.CdrDataHolder;
import OpenRTM.PortStatus;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.OutPortConsumerFactory;
import jp.go.aist.rtm.RTC.ObjectCreator;
import jp.go.aist.rtm.RTC.ObjectDestructor;
import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.port.ReturnCode;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.log.Logbuf;

/**
 * <p> OutPortCorbaCdrConsumer </p>
 *
 * <p> OutPortCorbaCdrConsumer class </p>
 *
 * <p> This is an implementation class of the output Consumer  </p>
 * <p> that uses CORBA for means of communication. </p>
 *
 * @param DataType Data type for this port
 *
 */
public class OutPortCorbaCdrConsumer extends CorbaConsumer< OpenRTM.OutPortCdr> implements OutPortConsumer, ObjectCreator<OutPortConsumer>, ObjectDestructor {
    /*!
     * @brief Constructor
     *
     */
    public OutPortCorbaCdrConsumer() {
        super(OpenRTM.OutPortCdr.class);
        rtcout = new Logbuf("OutPortCorbaCdrConsumer");
        rtcout.setLevel("PARANOID");
    }

    /**
     *
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
    public void setBuffer(BufferBase<OutputStream> buffer) {
        m_buffer = buffer;
    }

    /**
     *
     * <p> Read data </p>
     *
     * @param data Object to receive the read data
     *
     * @return Read result (Successful:true, Failed:false)
     *
     */
    public ReturnCode get(OutputStream data) {
        OpenRTM.CdrDataHolder cdr_data = new OpenRTM.CdrDataHolder();
        try {
            OpenRTM.PortStatus ret = _ptr().get(cdr_data);
            if (ret == OpenRTM.PortStatus.PORT_OK) {
                data.write_octet_array(cdr_data.value, 0, cdr_data.value.length);
                m_buffer.put(data);
                m_buffer.advanceWptr();
                m_buffer.advanceRptr();

                return ReturnCode.PORT_OK;
            }
            return convertReturn(ret);
        }
        catch (Exception e) {
            return ReturnCode.CONNECTION_LOST;
        }
    }

    /**
     *
     * <p> Subscribe the data receive notification </p>
     *
     * <p> Subscribe the data receive notification based on specified property </p>
     * <p> information </p>
     *
     * @param properties Subscription information
     *
     * @return Subscription result (Successful:true, Failed:false)
     *
     */
    public boolean subscribeInterface(final NVListHolder properties) {

        int index;
        index = NVUtil.find_index(properties,
                                   "dataport.corba_cdr.outport_ior");
        if (index < 0) {
            return false;
        }
    
        if (NVUtil.isString(properties,
                             "dataport.corba_cdr.outport_ior")) {
            final String ior;
            try {
                ior = properties.value[index].value.extract_string();
            }
            catch(BAD_OPERATION e) {
                rtcout.println(rtcout.ERROR, "inport_ior has no string");
                return false;
            }

            ORB orb = Manager.instance().getORB();
            Object var = orb.string_to_object(ior);
            if (var==null) {
                rtcout.println(rtcout.ERROR, "invalid IOR string has been passed");
                return false;
            }
    
            if (!super.setObject(var)) {
                rtcout.println(rtcout.WARN, "Setting object to consumer failed.");
                return false;
            }
            return true;
        }
        
        return false;
    }
    
    /**
     * <p> Unsubscribe the data receive notification </p>
     *
     * <p> Unsubscribe the data receive notification. </p>
     *
     * @param properties Unsubscription information
     *
     */
    public void unsubscribeInterface(final NVListHolder properties) {
        int index;
        index = NVUtil.find_index(properties,
                                  "dataport.corba_cdr.outport_ior");
        if (index < 0) {
            return;
        }
    
        final String ior;
        try {
            ior = properties.value[index].value.extract_string();
        }
        catch(BAD_OPERATION e) {
            rtcout.println(rtcout.ERROR, "inport_ior has no string");
            return;
        }
        ORB orb = Manager.instance().getORB();
        Object var = orb.string_to_object(ior);
        if (_ptr()._is_equivalent(var)) {
            releaseObject();
        }
    }
    
    /**
     * <p> convertReturn </p>
     *
     */
    protected ReturnCode convertReturn(OpenRTM.PortStatus status) {
        switch (status.value()) {
            case 0:
                return ReturnCode.PORT_OK;
            case 1:
                return ReturnCode.BUFFER_EMPTY;
            case 3:
                return ReturnCode.BUFFER_TIMEOUT;
            case 4:
                return ReturnCode.PRECONDITION_NOT_MET;
            default:
                return ReturnCode.PORT_ERROR;
        }
    }
    /**
     * <p> creator_ </p>
     * 
     * @return Object Created instances
     *
     */
    public OutPortConsumer creator_() {
        return new OutPortCorbaCdrConsumer();
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
    /**
     * <p> OutPortCorbaCdrConsumerInit </p>
     *
     */
    public static void OutPortCorbaCdrConsumerInit() {
        final OutPortConsumerFactory<OutPortConsumer,String> factory 
            = OutPortConsumerFactory.instance();

        factory.addFactory("corba_cdr",
                    new OutPortCorbaCdrConsumer(),
                    new OutPortCorbaCdrConsumer());
    
    }

    //    RTC::OutPortCdr_var m_outport;
    private BufferBase<OutputStream> m_buffer;

    private Logbuf rtcout;
}
