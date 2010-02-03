package jp.go.aist.rtm.RTC.port;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Any;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

import java.io.IOException;
import java.io.ByteArrayOutputStream;

import _SDOPackage.NVListHolder;
import OpenRTM.CdrDataHelper;
import OpenRTM.CdrDataHolder;
import OpenRTM.OutPortCdrPOA;
import OpenRTM.OutPortCdrOperations;
import OpenRTM.PortStatus;

import jp.go.aist.rtm.RTC.OutPortProviderFactory;
import jp.go.aist.rtm.RTC.ObjectCreator;
import jp.go.aist.rtm.RTC.ObjectDestructor;
import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.buffer.ReturnCode;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.POAUtil;
import jp.go.aist.rtm.RTC.util.ORBUtil;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.NVListHolderFactory;
import jp.go.aist.rtm.RTC.log.Logbuf;

import com.sun.corba.se.impl.encoding.EncapsOutputStream; 

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
public class OutPortCorbaCdrProvider extends OutPortCdrPOA implements OutPortProvider, ObjectCreator<OutPortProvider>, ObjectDestructor {
    /**
     * <p> Constructor </p>
     *
     */
    public OutPortCorbaCdrProvider() {
        m_buffer = null;
        rtcout = new Logbuf("OutPortCorbaCdrProvider");
        // PortProfile setting
        setInterfaceType("corba_cdr");
    
        // ConnectorProfile setting
        m_objref = this._this();
    
        // set outPort's reference
        ORB orb = ORBUtil.getOrb();
        CORBA_SeqUtil.
        push_back(m_properties,
                  NVUtil.newNVString("dataport.corba_cdr.outport_ior",
                              orb.object_to_string(m_objref)));
        CORBA_SeqUtil.
        push_back(m_properties,
                  NVUtil.newNV("dataport.corba_cdr.outport_ref",
                                 m_objref, OpenRTM.OutPortCdr.class ));
    }
    /**
     * 
     */
    public OpenRTM.OutPortCdr _this() {
        
        if (this.m_objref == null) {
            try {
                this.m_objref 
                    = OpenRTM.OutPortCdrHelper.narrow(POAUtil.getRef(this));
                
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
    public void setBuffer(BufferBase<OutputStream> buffer){
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
        rtcout.println(rtcout.PARANOID, "OutPortCorbaCdrProvider.get()");

        if (m_buffer == null) {
            onSenderError();
            rtcout.println(rtcout.PARANOID, "m_buffer is null.");
            return OpenRTM.PortStatus.UNKNOWN_ERROR;
        }

        OutputStream cdr = null;
        DataRef<OutputStream> cdr_ref = new DataRef<OutputStream>(cdr);
        jp.go.aist.rtm.RTC.buffer.ReturnCode ret 
                          = m_buffer.read(cdr_ref,0,0);

        if (ret.equals(jp.go.aist.rtm.RTC.buffer.ReturnCode.BUFFER_OK)) {

            EncapsOutputStream outcdr;
            outcdr = (EncapsOutputStream)cdr_ref.v;
/* zxc
byte[] ch = outcdr.toByteArray();
System.out.println("<+ch "+ch.length);
for(int ic=0;ic<ch.length;++ic){
System.out.print(ch[ic]+" ");
}
System.out.println("ch+>");
*/
            data.value =  outcdr.toByteArray();

        }
        return convertReturn(ret);
    }
    /**
     * <p> convertReturn </p>
     *
     */
    protected OpenRTM.PortStatus 
    convertReturn(jp.go.aist.rtm.RTC.buffer.ReturnCode status) {
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


    
    /**
     * <p> creator_ </p>
     * 
     * @return Object Created instances
     *
     */
    public OutPortProvider creator_() {
        return new OutPortCorbaCdrProvider();
    }
    /**
     * <p> destructor_ </p>
     * 
     * @param obj    The target instances for destruction
     *
     */
    public void destructor_(Object obj) {
        try{
            byte[] oid = _default_POA().servant_to_id((InPortCorbaCdrProvider)obj);
            _default_POA().deactivate_object(oid);
        }
        catch(Exception e){
            e.printStackTrace();
        } 
        obj = null;
    }
    /**
     * <p> OutPortCorbaCdrProviderInit </p>
     *
     */
    public static void OutPortCorbaCdrProviderInit() {
        final OutPortProviderFactory<OutPortProvider,String> factory 
            = OutPortProviderFactory.instance();

        factory.addFactory("corba_cdr",
                    new OutPortCorbaCdrProvider(),
                    new OutPortCorbaCdrProvider());
    
    }

    /**
     * <p> publishInterfaceProfile </p>
     *
     * @param properties 
     *
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
     * <p> publishInterface </p>
     *
     * @param properties 
     * @return boolean
     */
    public boolean publishInterface(NVListHolder properties) {
        
        if (!NVUtil.isStringValue(properties,
                "dataport.interface_type",
                this.m_interfaceType)) {
            return false;
        }
        
        NVUtil.append(properties, this.m_properties);
        return true;
    }
    /**
     * <p> setConnecotor </p>
     * @param connector
     */
    public void setConnector(OutPortConnector connector) {
        m_connector = connector;
    }
    /**
     * <p>インタフェースプロフィールのポートタイプを設定します。</p>
     * 
     * @param portType ポートタイプ
     */
    protected void setPortType(final String portType) {
        
        this.m_portType = portType;
    }
    
    /**
     * <p>インタフェースポロフィールのデータタイプを設定します。</p>
     * 
     * @param dataType データタイプ
     */
    protected void setDataType(final String dataType) {
        
        this.m_dataType = dataType;
    }
    
    /**
     * <p>インタフェースプロフィールのインタフェースタイプを設定します。<//p>
     * 
     * @param interfaceType インタフェースタイプ
     */
    protected void setInterfaceType(final String interfaceType) {
        
        this.m_interfaceType = interfaceType;
    }
    
    /**
     * <p>インタフェースプロフィールのデータフロータイプを設定します。</p>
     * 
     * @param dataFlowType データフロータイプ
     */
    protected void setDataFlowType(final String dataFlowType) {
        
        this.m_dataflowType = dataFlowType;
    }
    
    /**
     * <p>インタフェースプロフィールのサブスクリプションタイプを設定します。</p>
     * 
     * @param subscriptionType サブスクリプションタイプ
     */
    protected void setSubscriptionType(final String subscriptionType) {
        
        this.m_subscriptionType = subscriptionType;
    }
    /**
     * <p> Set the listener.  </p>
     */
    public void setListener(ConnectorBase.ConnectorInfo info,
                             ConnectorListeners listeners) {
        m_profile = info;
        m_listeners = listeners;
    }
    /**
     * <p>接続プロフィールを保持するメンバ変数です。</p>
     */
    protected NVListHolder m_properties = NVListHolderFactory.create();
    
    /**
     * <p> Connector data listener functions </p>
     */
//    private void onBufferWrite(final OutputStream data)
//    {
//      m_listeners.
//        connectorData_[ConnectorDataListenerType.ON_BUFFER_WRITE].notify(m_profile, data);
//    }
//
//    private void onBufferFull(final OutputStream data)
//    {
//      m_listeners.
//        connectorData_[ConnectorDataListenerType.ON_BUFFER_FULL].notify(m_profile, data);
//    }
//
//    private void onBufferWriteTimeout(final OutputStream data)
//    {
//      m_listeners.
//        connectorData_[ConnectorDataListenerType.ON_BUFFER_WRITE_TIMEOUT].notify(m_profile, data);
//    }
//
//    private void onBufferWriteOverwrite(final OutputStream data)
//    {
//      m_listeners.
//        connectorData_[ConnectorDataListenerType.ON_BUFFER_OVERWRITE].notify(m_profile, data);
//    }

    private void onBufferRead(final OutputStream data)
    {
      m_listeners.
        connectorData_[ConnectorDataListenerType.ON_BUFFER_READ].notify(m_profile, data);
    }

    private void onSend(final OutputStream data)
    {
      m_listeners.
        connectorData_[ConnectorDataListenerType.ON_SEND].notify(m_profile, data);
    }

//    private void onReceived(final OutputStream data)
//    {
//      m_listeners.
//        connectorData_[ConnectorDataListenerType.ON_RECEIVED].notify(m_profile, data);
//    }
//
//    private void onReceiverFull(final OutputStream data)
//    {
//      m_listeners.
//        connectorData_[ConnectorDataListenerType.ON_RECEIVER_FULL].notify(m_profile, data);
//    }
//
//    private void onReceiverTimeout(final OutputStream data)
//    {
//      m_listeners.
//        connectorData_[ConnectorDataListenerType.ON_RECEIVER_TIMEOUT].notify(m_profile, data);
//    }
//
//    private void onReceiverError(final OutputStream data)
//    {
//      m_listeners.
//        connectorData_[ConnectorDataListenerType.ON_RECEIVER_ERROR].notify(m_profile, data);
//    }

    /**
     * <p> Connector listener functions </p>
     */
    private void onBufferEmpty() {
      m_listeners.
        connector_[ConnectorListenerType.ON_BUFFER_EMPTY].notify(m_profile);
    }

    private void onBufferReadTimeout() {
      m_listeners.
        connector_[ConnectorListenerType.ON_BUFFER_READ_TIMEOUT].notify(m_profile);
    }

    private void onSenderEmpty() {
      m_listeners.
        connector_[ConnectorListenerType.ON_SENDER_EMPTY].notify(m_profile);
    }

    private void onSenderTimeout() {
      m_listeners.
        connector_[ConnectorListenerType.ON_SENDER_TIMEOUT].notify(m_profile);
    }

    private void onSenderError() {
      m_listeners.
        connector_[ConnectorListenerType.ON_SENDER_ERROR].notify(m_profile);
    }
    private String m_portType = new String();
    private String m_dataType = new String();
    private String m_interfaceType = new String();
    private String m_dataflowType = new String();
    private String m_subscriptionType = new String();
    private Logbuf rtcout;
    private BufferBase<OutputStream> m_buffer;
    private OpenRTM.OutPortCdr m_objref;
    private OutPortConnector m_connector;
    private ConnectorListeners m_listeners;
    private ConnectorBase.ConnectorInfo m_profile;
}
