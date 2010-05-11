package jp.go.aist.rtm.RTC.port;

import org.omg.CORBA.ORB;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.TCKind;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import com.sun.corba.se.impl.encoding.EncapsOutputStream; 

import _SDOPackage.NVListHolder;
import OpenRTM.CdrDataHolder;


import jp.go.aist.rtm.RTC.InPortConsumerFactory;
import jp.go.aist.rtm.RTC.ObjectCreator;
import jp.go.aist.rtm.RTC.ObjectDestructor;
import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.port.ReturnCode;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.ORBUtil;
import jp.go.aist.rtm.RTC.log.Logbuf;

/**
 * <p> InPortCorbaCdrConsumer </p>
 * <p> InPortCorbaCdrConsumer class </p>
 * <p> This is an implementation class of the input port Consumer  </p>
 * <p> that uses CORBA for means of communication. </p>
 */

public class InPortCorbaCdrConsumer extends CorbaConsumer< OpenRTM.InPortCdr > implements InPortConsumer, ObjectCreator<InPortConsumer>, ObjectDestructor {
    /**
     * <p> Constructor </p>
     * <p> buffer The buffer object that is attached to this Consumer </p>
     *
     */
    public InPortCorbaCdrConsumer() {
        super(OpenRTM.InPortCdr.class);
        m_spi_orb = (com.sun.corba.se.spi.orb.ORB)ORBUtil.getOrb();
        rtcout = new Logbuf("InPortCorbaCdrConsumer");
//        rtcout.setLevel("PARANOID");
        m_spi_orb = (com.sun.corba.se.spi.orb.ORB)ORBUtil.getOrb();
        m_orb = ORBUtil.getOrb();
    }
    /**
     * <p> Initializing configuration </p>
     * <p> This operation would be called to configure this consumer </p>
     * <p> in initialization. </p>
     *
     */
    public void init(Properties prop) {
        m_properties = prop;
    }

    /**
     * <p> Send data to the destination port </p>
     * <p> Pure virtual function to send data to the destination port. </p>
     *
     */
    public ReturnCode put(final OutputStream data) {
        rtcout.println(rtcout.PARANOID, "put");
        
        EncapsOutputStream cdr;
        cdr = (EncapsOutputStream)data;
        byte[] ch = cdr.toByteArray();
        EncapsOutputStream output_stream 
            = new EncapsOutputStream(m_spi_orb, m_connector.isLittleEndian());
        output_stream.write_octet_array(ch,0,ch.length);

        try {
//            OpenRTM.PortStatus ret = _ptr().put(cdr.toByteArray());
            OpenRTM.PortStatus ret = _ptr().put(output_stream.toByteArray());
            return convertReturn(ret);
        }
        catch (Exception e) {
            return ReturnCode.CONNECTION_LOST;
        }
    }
    /**
     * <p> Publish InterfaceProfile information </p>
     *
     * <p> Publish interfaceProfile information. </p>
     * <p> Check the dataport.interface_type value of the NameValue object </p>
     * <p> specified by an argument in property information and get information </p>
     * <p> only when the interface type of the specified port is matched. </p>
     *
     * @param properties Properties to get InterfaceProfile information
     *
     */
    public void publishInterfaceProfile(NVListHolder properties) {
        return;
    }
    /**
     * <p> Subscribe to the data sending notification </p>
     * <p> Subscribe to the data sending notification based on specified  </p>
     * <p> property information. </p>
     * @param properties Information for subscription
     * @return Subscription result (Successful:true, Failed:false)
     *
     */
    public boolean subscribeInterface(final NVListHolder properties) {
        rtcout.println(rtcout.TRACE, "subscribeInterface()");
        if(properties.value == null){
            rtcout.println(rtcout.DEBUG, "NVListHolder is null.");
            return false;
        }
        rtcout.println(rtcout.DEBUG, 
                       "Length of NVListHolder:"+properties.value.length);
        rtcout.println(rtcout.DEBUG, NVUtil.toString(properties));
    
        // getting InPort's ref from IOR string
        if (subscribeFromIor(properties)) { 
            return true; 
        }
    
        // getting InPort's ref from Object reference
        if (subscribeFromRef(properties)) { 
            return true; 
        }
    
        return false;
    }
    
    /**
     * <p> Unsubscribe the data send notification </p>
     * <p> Unsubscribe the data send notification. </p>
     * @param properties Information for unsubscription
     *
     */
    public void unsubscribeInterface(final NVListHolder properties) {
        rtcout.println(rtcout.TRACE, "unsubscribeInterface()");
        rtcout.println(rtcout.DEBUG, NVUtil.toString(properties));
    
        if (unsubscribeFromIor(properties)) { 
            return; 
        }
        unsubscribeFromRef(properties);
    }

    /**
     * <p> Getting object reference fromn IOR string </p>
     * @param properties Information for subscription
     * @return true: succeeded, false: failed
     *
     */
    private boolean subscribeFromIor(final NVListHolder properties) {
        rtcout.println(rtcout.TRACE, "subscribeFromIor()");
    
        int index;
        index = NVUtil.find_index(properties,
                               "dataport.corba_cdr.inport_ior");
        if (index < 0) {
            rtcout.println(rtcout.ERROR, "inport_ior not found");
            return false;
        }
        rtcout.println(rtcout.DEBUG, "index:"+index);
    
        final String ior;

        try {
            rtcout.println(rtcout.DEBUG, 
                            "type:"+properties.value[index].value.type());
            if( properties.value[index].value.type().kind() == 
                TCKind.tk_wstring ) {
                ior = properties.value[index].value.extract_wstring();
            } else {
                ior = properties.value[index].value.extract_string();
            }
        }
        catch(BAD_OPERATION e) {
            rtcout.println(rtcout.ERROR, "inport_ior has no string");
            return false;
        }
    
        ORB orb = ORBUtil.getOrb();
        org.omg.CORBA.Object obj = orb.string_to_object(ior);
    
        if (obj==null) {
            rtcout.println(rtcout.ERROR, "invalid IOR string has been passed");
            return false;
        }
    
        if (!super.setObject(obj)) {
            rtcout.println(rtcout.WARN, "Setting object to consumer failed.");
            return false;
        }
        return true;
    }

    /**
     * <p> Getting object reference fromn Any directry </p>
     * @param properties Information for subscription
     * @return true: succeeded, false: failed
     *
     */
    private boolean subscribeFromRef(final NVListHolder properties) {
        rtcout.println(rtcout.TRACE, "subscribeFromRef()");
        int index;
        index = NVUtil.find_index(properties,
                                   "dataport.corba_cdr.inport_ref");
        if (index < 0) {
            rtcout.println(rtcout.ERROR, "inport_ref not found");
            return false;
        }
    
        org.omg.CORBA.Object obj;
        try {
            obj = properties.value[index].value.extract_Object();
        }
        catch(BAD_OPERATION e){
            rtcout.println(rtcout.ERROR, "prop[inport_ref] is not objref");
            return true;
        }
    
        if (obj==null) {
            rtcout.println(rtcout.ERROR, "prop[inport_ref] is not objref");
            return false;
        }
    
        if (!super.setObject(obj)) {
            rtcout.println(rtcout.ERROR, "Setting object to consumer failed.");
            return false;
        }
        return true;
    }

    /**
     * <p> ubsubscribing (IOR version) </p>
     * @param properties Information for unsubscription
     * @return true: succeeded, false: failed
     *
     */
    private boolean unsubscribeFromIor(final NVListHolder properties) {
        rtcout.println(rtcout.TRACE, "unsubscribeFromIor()");
        int index;
        index = NVUtil.find_index(properties,
                                   "dataport.corba_cdr.inport_ior");
        if (index < 0) {
            rtcout.println(rtcout.ERROR, "inport_ior not found");
            return false;
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
            return false;
        }
    
        ORB orb = ORBUtil.getOrb();
        org.omg.CORBA.Object var = orb.string_to_object(ior);
        if (!(_ptr()._is_equivalent(var))) {
            rtcout.println(rtcout.ERROR, "connector property inconsistency");
            return false;
        }
    
        releaseObject();
        return true;
    }

    /**
     * <p> ubsubscribing (Object reference version) </p>
     * @param properties Information for unsubscription
     * @return true: succeeded, false: failed
     *
     */
    private boolean unsubscribeFromRef(final NVListHolder properties) {
        rtcout.println(rtcout.TRACE, "unsubscribeFromRef()");
        int index;
        index = NVUtil.find_index(properties,
                                   "dataport.corba_cdr.inport_ref");
        if (index < 0) { 
            return false; 
        }
    
        org.omg.CORBA.Object obj;
        try {
            obj = properties.value[index].value.extract_Object();
        }
        catch(BAD_OPERATION e){
            rtcout.println(rtcout.ERROR, "prop[inport_ref] is not objref");
            return false;
        }
    
        if (!(_ptr()._is_equivalent(obj))) {
            rtcout.println(rtcout.ERROR, "connector property inconsistency");
            return false;
        }
        
        releaseObject();
        return true;
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
                return ReturnCode.PORT_ERROR;
            case 2:
                return ReturnCode.BUFFER_FULL;
            case 3:
                return ReturnCode.BUFFER_EMPTY;
            case 4:
                return ReturnCode.BUFFER_TIMEOUT;
            default:
                return ReturnCode.UNKNOWN_ERROR;
        }
    }
    
    /**
     * <p> creator_ </p>
     * 
     * @return Object Created instances
     *
     */
    public InPortConsumer creator_() {
        return new InPortCorbaCdrConsumer();
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
     * <p> InPortCorbaCdrConsumerInit </p>
     *
     */
    public static void InPortCorbaCdrConsumerInit() {
        final InPortConsumerFactory<InPortConsumer,String> factory 
            = InPortConsumerFactory.instance();

        factory.addFactory("corba_cdr",
                    new InPortCorbaCdrConsumer(),
                    new InPortCorbaCdrConsumer());
    
    }
    /**
     * <p> setConnecotor </p>
     * @param connector
     */
    public void setConnector(OutPortConnector connector) {
        m_connector = connector;
    }

    private Logbuf rtcout;
    private Properties m_properties;
    private com.sun.corba.se.spi.orb.ORB m_spi_orb;
    private OutPortConnector m_connector;
    private ORB m_orb;

}

