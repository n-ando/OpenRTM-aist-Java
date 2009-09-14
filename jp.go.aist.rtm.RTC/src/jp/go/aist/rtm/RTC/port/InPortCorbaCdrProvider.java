package jp.go.aist.rtm.RTC.port;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.SystemException;

import java.io.IOException;

import _SDOPackage.NVListHolder;
import OpenRTM.CdrDataHolder;
import OpenRTM.InPortCdrOperations;
import OpenRTM.InPortCdrPOA;
import OpenRTM.PortStatus;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.FactoryGlobal;
import jp.go.aist.rtm.RTC.ObjectCreator;
import jp.go.aist.rtm.RTC.ObjectDestructor;
import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.POAUtil;
import jp.go.aist.rtm.RTC.util.NVListHolderFactory;
import jp.go.aist.rtm.RTC.log.Logbuf;

/**
 * <p> InPortCorbaCdrProvider </p>
 * <p> InPortCorbaCdrProvider class </p>
 *
 * <p>  This is an implementation class of the input port Provider  </p>
 * <p>  that uses CORBA for means of communication. </p>
 *
 *
 */
public class InPortCorbaCdrProvider extends InPortCdrPOA implements InPortProvider, ObjectCreator<InPortProvider>, ObjectDestructor {
    /**
     * <p> Constructor </p>
     *
     * <p>  Set the following items to port properties </p>
     * <p>   - Interface type : CORBA_Any </p>
     * <p>   - Data flow type : Push, Pull </p>
     * <p>   - Subscription type : Any </p>
     *
     *
     */
    public InPortCorbaCdrProvider() {
        m_buffer = null; 
        rtcout = new Logbuf("InPortCorbaCdrProvider");
        rtcout.setLevel("PARANOID");
        // PortProfile setting
        setInterfaceType("corba_cdr");
    
        // ConnectorProfile setting
        m_objref = this._this();
    
        // set InPort's reference
        ORB orb = Manager.instance().getORB();
        CORBA_SeqUtil.push_back(m_properties,
                NVUtil.newNV("dataport.corba_cdr.inport_ior",
                              orb.object_to_string(m_objref)));
        CORBA_SeqUtil.push_back(m_properties,
                NVUtil.newNV("dataport.corba_cdr.inport_ref",
                              m_objref, OpenRTM.InPortCdr.class ));
    }
    /**
     * 
     */
    public OpenRTM.InPortCdr _this() {
        
        if (this.m_objref == null) {
            try {
                this.m_objref = OpenRTM.InPortCdrHelper.narrow(POAUtil.getRef(this));
                
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        
        return this.m_objref;
    }
    /**
     * <p> init </p>
     *
     * @param prop
     */
    public void init(Properties prop){
    }
    /**
     * <p> setBuffer </p>
     *
     * @param buffer 
     */
    public void setBuffer(BufferBase<OutputStream> buffer) {
        m_buffer = buffer;
    }

    /**
     * <p> [CORBA interface] Write data into the buffer </p>
     *
     * <p> Write data into the specified buffer. </p>
     *
     * @param data The target data for writing
     *
     */
    public OpenRTM.PortStatus put(byte[] data)
      throws SystemException {

        rtcout.println(rtcout.PARANOID, "put()");

        if (m_buffer == null) {
            return OpenRTM.PortStatus.PORT_ERROR;
        }

        if (m_buffer.full()) {
            rtcout.println(rtcout.WARN, "buffer full");
            return OpenRTM.PortStatus.BUFFER_FULL;
        }

        rtcout.println(rtcout.PARANOID, "received data size: "+data.length);

        ORB orb = Manager.instance().getORB();
        Any any = orb.create_any();
        OutputStream cdr = any.create_output_stream();
        cdr.write_octet_array(data, 0, data.length);

        try {
            InputStream in = cdr.create_input_stream();
            int len = in.available();
            rtcout.println(rtcout.PARANOID, "converted CDR data size: "+len);
        }
        catch (IOException e){
            rtcout.println(rtcout.PARANOID, "An I/O error occurs");
        }

        jp.go.aist.rtm.RTC.buffer.ReturnCode ret = m_buffer.write(cdr);
        return convertReturn(ret);
    }

    public OpenRTM.PortStatus put(final OpenRTM.CdrDataHolder data)
      throws SystemException {
        rtcout.println(rtcout.PARANOID, "put()");

        if (m_buffer == null) {
            return OpenRTM.PortStatus.PORT_ERROR;
        }

        if (m_buffer.full()) {
            rtcout.println(rtcout.WARN, "buffer full");
            return OpenRTM.PortStatus.BUFFER_FULL;
        }

        rtcout.println(rtcout.PARANOID, "received data size: "+data.value.length);

        ORB orb = Manager.instance().getORB();
        Any any = orb.create_any();
        OutputStream cdr = any.create_output_stream();
        cdr.write_octet_array(data.value, 0, data.value.length);

        try {
            InputStream in = cdr.create_input_stream();
            int len = in.available();
            rtcout.println(rtcout.PARANOID, "converted CDR data size: "+len);
        }
        catch (IOException e){
            rtcout.println(rtcout.PARANOID, "An I/O error occurs");
        }

        jp.go.aist.rtm.RTC.buffer.ReturnCode ret = m_buffer.write(cdr);
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

    /**
     * <p> creator_ </p>
     * 
     * @return Object Created instances
     *
     */
    public InPortProvider creator_() {
        return new InPortCorbaCdrProvider();
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
     * <p> InPortCorbaCdrProviderInit </p>
     *
     */
    public static void InPortCorbaCdrProviderInit() {
        final FactoryGlobal<InPortProviderImpl,String> factory 
            = FactoryGlobal.instance();

        factory.addFactory("corba_cdr",
                    new InPortCorbaCdrProvider(),
                    new InPortCorbaCdrProvider());
    
    }
    /**
     * <p>InterfaceProfile情報を公開します。</p>
     * 
     * @param properties InterfaceProfile情報を受け取るホルダオブジェクト
     */
    public void publishInterfaceProfile(NVListHolder properties) {

        NVUtil.appendStringValue(properties, "dataport.data_type",
                this.m_dataType);
        NVUtil.appendStringValue(properties, "dataport.interface_type",
                this.m_interfaceType);
        NVUtil.appendStringValue(properties, "dataport.dataflow_type",
                this.m_dataflowType);
        NVUtil.appendStringValue(properties, "dataport.subscription_type",
                this.m_subscriptionType);
    }
    
    /**
     * <p>Interface情報を公開します。</p>
     * 
     * @param properties Interface情報を受け取るホルダオブジェクト
     */
    public boolean publishInterface(NVListHolder properties) {

        rtcout.println(rtcout.TRACE, "publishInterface()");
        rtcout.println(rtcout.DEBUG, NVUtil.toString(properties));


        if (! NVUtil.isStringValue(properties,
                "dataport.interface_type",
                this.m_interfaceType)) {
            return false;
        }

        NVUtil.append(properties, this.m_properties);
        return true;

    }
    
    /**
     * <p>データタイプを設定します。</p>
     * 
     * @param dataType データタイプ
     */
    protected void setDataType(final String dataType) {
        this.m_dataType = dataType;
    }
    
    /**
     * <p>インタフェースタイプを設定します。</p>
     * 
     * @param interfaceType インタフェースタイプ
     */
     protected void setInterfaceType(final String interfaceType) {
        rtcout.println(rtcout.TRACE, "setInterfaceType("+interfaceType+")");
        this.m_interfaceType = interfaceType;
    }
    
    /**
     * <p>データフロータイプを設定します。</p>
     * 
     * @param dataflowType データフロータイプ
     */
    protected void setDataFlowType(final String dataflowType) {
        rtcout.println(rtcout.TRACE, "setDataFlowType("+dataflowType+")");
        this.m_dataflowType = dataflowType;
    }
    
    /**
     * <p>サブスクリプションタイプを設定します。</p>
     * 
     * @param subscriptionType サブスクリプションタイプ
     */
    protected void setSubscriptionType(final String subscriptionType) {
        rtcout.println(rtcout.TRACE, "setSubscriptionType("+subscriptionType+")");
        this.m_subscriptionType = subscriptionType;
    }



    /**
     * <p>インタフェース情報を保持するオブジェクトです。</p>
     */
    protected NVListHolder m_properties = NVListHolderFactory.create();

    private String m_dataType = new String();
    private String m_interfaceType = new String();
    private String m_dataflowType = new String();
    private String m_subscriptionType = new String();

    private BufferBase<OutputStream> m_buffer;
    private OpenRTM.InPortCdr m_objref;

}
