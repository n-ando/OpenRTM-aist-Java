package jp.go.aist.rtm.RTC.port;

import org.omg.CORBA.ORB;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.Object;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.TCKind;

import _SDOPackage.NVListHolder;
import OpenRTM.CdrDataHolder;
import OpenRTM.PortStatus;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.OutPortConsumerFactory;
import jp.go.aist.rtm.RTC.ObjectCreator;
import jp.go.aist.rtm.RTC.ObjectDestructor;
import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.port.ReturnCode;
import jp.go.aist.rtm.RTC.port.ConnectorDataListenerType;
import jp.go.aist.rtm.RTC.port.ConnectorListenerType;
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
        rtcout.println(rtcout.TRACE, "OutPutCorbaCdrConsumer.init()");
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
        rtcout.println(rtcout.TRACE, "OutPutCorbaCdrConsumer.setBuffer()");
        m_buffer = buffer;
    }
    /**
     * 
     */
    public void setListener(ConnectorBase.ConnectorInfo info, 
                            ConnectorListeners listeners) {
        rtcout.println(rtcout.TRACE, "OutPutCorbaCdrConsumer.setListener()");
        m_listeners = listeners;
        m_profile = info;
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
        rtcout.println(rtcout.TRACE, "OutPutCorbaCdrConsumer.get()");
        OpenRTM.CdrDataHolder cdr_data = new OpenRTM.CdrDataHolder();
        try {
            OpenRTM.PortStatus ret = _ptr().get(cdr_data);
            if (ret == OpenRTM.PortStatus.PORT_OK) {
                rtcout.println(rtcout.DEBUG, "get() successful");
                data.write_octet_array(cdr_data.value, 0, 
                                        cdr_data.value.length);
                rtcout.println(rtcout.PARANOID, 
                                "CDR data length: "+cdr_data.value.length);
  
                onReceived(data);
                onBufferWrite(data);

                if (m_buffer.full()) {
                    rtcout.println(rtcout.INFO, 
                                "InPort buffer is full.");
                    onBufferFull(data);
                    onReceiverFull(data);
                }

                m_buffer.put(data);
                m_buffer.advanceWptr();
                m_buffer.advanceRptr();

                return ReturnCode.PORT_OK;
            }
            return convertReturn(ret);
        }
        catch (Exception e) {
            rtcout.println(rtcout.WARN, 
                                "Exception caought from OutPort.get().");
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

        rtcout.println(rtcout.TRACE, 
                            "OutPortCorbaCdrConsumer.subscribeInterface()");
        int index;
        index = NVUtil.find_index(properties,
                                   "dataport.corba_cdr.outport_ior");
        if (index < 0) {
            rtcout.println(rtcout.DEBUG, 
                            "dataport.corba_cdr.outport_ior not found.");
            return false;
        }
    
        if (NVUtil.isString(properties,
                             "dataport.corba_cdr.outport_ior")) {
            rtcout.println(rtcout.DEBUG, 
                            "dataport.corba_cdr.outport_ior found.");
            final String ior;
            try {
                if( properties.value[index].value.type().kind() == 
                    TCKind.tk_wstring ) {
                    ior = properties.value[index].value.extract_wstring();
                } else {
                    ior = properties.value[index].value.extract_string();
                }
            }
            catch(BAD_OPERATION e) {
                rtcout.println(rtcout.ERROR, "outport_ior has no string");
                return false;
            }

            ORB orb = Manager.instance().getORB();
            Object var = orb.string_to_object(ior);
            if (var==null) {
                rtcout.println(rtcout.ERROR, 
                                    "invalid IOR string has been passed");
                return false;
            }
    
            if (!super.setObject(var)) {
                rtcout.println(rtcout.ERROR, 
                                    "Invalid object reference.");
                return false;
            }
            rtcout.println(rtcout.DEBUG, 
                                "CorbaConsumer was set successfully.");
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
        rtcout.println(rtcout.TRACE, 
                            "OutPortCorbaCdrConsumer.unsubscribeInterface()");
        int index;
        index = NVUtil.find_index(properties,
                                  "dataport.corba_cdr.outport_ior");
        if (index < 0) {
            rtcout.println(rtcout.DEBUG, 
                            "dataport.corba_cdr.outport_ior not found.");
            return;
        }
    
        final String ior;
        try {
            if( properties.value[index].value.type().kind() == 
                TCKind.tk_wstring ) {
                ior = properties.value[index].value.extract_wstring();
            } else {
                ior = properties.value[index].value.extract_string();
            }
        }
        catch(BAD_OPERATION e) {
            rtcout.println(rtcout.ERROR, "inport_ior has no string");
            return;
        }
        rtcout.println(rtcout.DEBUG, 
                            "dataport.corba_cdr.outport_ior found.");
        ORB orb = Manager.instance().getORB();
        Object var = orb.string_to_object(ior);
        if (_ptr()._is_equivalent(var)) {
            releaseObject();
            rtcout.println(rtcout.DEBUG, 
                            "CorbaConsumer's reference was released.");
            return;
        }
        rtcout.println(rtcout.ERROR, 
                            "hmm. Inconsistent object reference.");
    }
    
    /**
     * {@.ja リターンコード変換 (DataPortStatus -> BufferStatus)}
     * {@.en Return codes conversion}
     */
    protected ReturnCode convertReturn(OpenRTM.PortStatus status) {
        switch (status.value()) {
            case OpenRTM.PortStatus._PORT_OK:
                // never comes here
                return ReturnCode.PORT_OK;

            case OpenRTM.PortStatus._PORT_ERROR:
                onSenderError();
                return ReturnCode.PORT_ERROR;

            case OpenRTM.PortStatus._BUFFER_FULL:
                // never comes here
                return ReturnCode.BUFFER_FULL;

            case OpenRTM.PortStatus._BUFFER_EMPTY:
                onSenderEmpty();
                return ReturnCode.BUFFER_EMPTY;

            case OpenRTM.PortStatus._BUFFER_TIMEOUT:
                onSenderTimeout();
                return ReturnCode.BUFFER_TIMEOUT;
 
            case OpenRTM.PortStatus._UNKNOWN_ERROR:
                onSenderError();
                return ReturnCode.UNKNOWN_ERROR;

            default:
                onSenderError();
                return ReturnCode.UNKNOWN_ERROR;
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
    /**
     * <p> setConnecotor </p>
     * @param connector
     */
    public void setConnector(InPortConnector connector) {
        m_connector = connector;
    }

    /**
     * <p> Connector data listener functions </p>
     */
    private void onBufferWrite(final OutputStream data) {
        m_listeners.connectorData_[ConnectorDataListenerType.ON_BUFFER_WRITE].notify(m_profile, data);
    }

    private void onBufferFull(final OutputStream data) {
        m_listeners.connectorData_[ConnectorDataListenerType.ON_BUFFER_FULL].notify(m_profile, data);
    }

//    private void onBufferWriteTimeout(final OutputStream data) {
//        m_listeners.connectorData_[ConnectorDataListenerType.ON_BUFFER_WRITE_TIMEOUT].notify(m_profile, data);
//    }

//    private void onBufferWriteOverwrite(final OutputStream data) {
//        m_listeners.connectorData_[ConnectorDataListenerType.ON_BUFFER_OVERWRITE].notify(m_profile, data);
//    }

//    private void onBufferRead(final OutputStream data) {
//      m_listeners.connectorData_[ConnectorDataListenerType.ON_BUFFER_READ].notify(m_profile, data);
//    }

//    private void onSend(final OutputStream data) {
//        m_listeners.connectorData_[ConnectorDataListenerType.ON_SEND].notify(m_profile, data);
//    }

    private void onReceived(final OutputStream data) {
        m_listeners.connectorData_[ConnectorDataListenerType.ON_RECEIVED].notify(m_profile, data);
    }

    private void onReceiverFull(final OutputStream data) {
        m_listeners.connectorData_[ConnectorDataListenerType.ON_RECEIVER_FULL].notify(m_profile, data);
    }

//    private void onReceiverTimeout(final OutputStream data) {
//        m_listeners.connectorData_[ConnectorDataListenerType.ON_RECEIVER_TIMEOUT].notify(m_profile, data);
//    }

//    private void onReceiverError(final OutputStream data) {
//        m_listeners.connectorData_[ConnectorDataListenerType.ON_RECEIVER_ERROR].notify(m_profile, data);
//    }

    /**
     * <p> Connector listener functions </p>
     */
//    private void onBufferEmpty() {
//        m_listeners.connector_[ConnectorDataListenerType.ON_BUFFER_EMPTY].notify(m_profile);
//    }

//    private void onBufferReadTimeout() {
//        m_listeners.connector_[ConnectorDataListenerType.ON_BUFFER_READ_TIMEOUT].notify(m_profile);
//    }

    private void onSenderEmpty() {
        m_listeners.connector_[ConnectorListenerType.ON_SENDER_EMPTY].notify(m_profile);
    }

    private void onSenderTimeout() {
        m_listeners.connector_[ConnectorListenerType.ON_SENDER_TIMEOUT].notify(m_profile);
    }

    private void onSenderError() {
        m_listeners.connector_[ConnectorListenerType.ON_SENDER_ERROR].notify(m_profile);
    }

    //    RTC::OutPortCdr_var m_outport;
    private BufferBase<OutputStream> m_buffer;

    private Logbuf rtcout;
    private InPortConnector m_connector;
    private ConnectorListeners m_listeners;
    private ConnectorBase.ConnectorInfo m_profile;
}
