package jp.go.aist.rtm.RTC.port;

import jp.go.aist.rtm.RTC.ObjectCreator;
import jp.go.aist.rtm.RTC.ObjectDestructor;
import jp.go.aist.rtm.RTC.OutPortProviderFactory;
import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.log.Logbuf;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.NVListHolderFactory;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.ORBUtil;
import jp.go.aist.rtm.RTC.util.POAUtil;
import jp.go.aist.rtm.RTC.util.Properties;

import org.omg.CORBA.ORB;
import org.omg.CORBA.portable.OutputStream;

import OpenRTM.OutPortCdrPOA;
import _SDOPackage.NVListHolder;


/**
 * {@.ja OutPortCorbaCdrProvider クラス}
 * {@.en OutPortCorbaCdrProvider class}
 * <p>
 * {@.ja データ転送に CORBA の OpenRTM::OutPortCdr インターフェースを利用し
 * た、pull 型データフロー型を実現する OutPort プロバイダクラス。}
 * {@.en This is an implementation class of OutPort Provider that uses
 * CORBA for mean of communication.}
 *
 * @param DataType 
 *   {@.ja プロバイダに割り当てられたバッファによって確保されたDataType}
 *   {@.en Data type held by the buffer that is assigned to this 
 *        provider}
 *
 */
public class OutPortCorbaCdrProvider extends OutPortCdrPOA implements OutPortProvider, ObjectCreator<OutPortProvider>, ObjectDestructor {
    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
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
     * {@.ja 当該OpenRTM.OutPortCdrのCORBAオブジェクト参照を取得する。}
     * {@.en Gets CORBA object referense of this OpenRTM.OutPortCdr}
     * 
     * @return 
     *   {@.ja 当該PortのCORBAオブジェクト参照}
     *   {@.en CORBA object referense of this OpenRTM.OutPortCdr}
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
     * {@.ja 設定初期化}
     * {@.en Initializing configuration}
     * <p>
     * {@.ja OutPortCorbaCdrProvider の各種設定を行う。与えられた
     * Propertiesから必要な情報を取得して各種設定を行う。この init() 関
     * 数は、OutPortProvider生成直後および、接続時にそれぞれ呼ばれる可
     * 能性がある。したがって、この関数は複数回呼ばれることを想定して記
     * 述されるべきである。}
     * {@.en This operation would be called to configure in initialization. 
     * In the concrete class, configuration should be performed 
     * getting appropriate information from the given Properties data.
     * This function might be called right after instantiation and
     * connection sequence respectivly.  Therefore, this function
     * should be implemented assuming multiple call.}
     *
     * @param prop 
     *   {@.ja 設定情報}
     *   {@.en Configuration information}
     *
     */
    public void init(Properties prop) {
    }

    /**
     * {@.ja バッファをセットする}
     * {@.en Setting outside buffer's pointer}
     * <p>
     * {@.ja OutPortProvider がデータを取り出すバッファをセットする。
     * すでにセットされたバッファがある場合、以前のバッファへの
     * ポインタに対して上書きされる。
     * OutPortProviderはバッファの所有権を仮定していないので、
     * バッファの削除はユーザの責任で行わなければならない。}
     * {@.en A pointer to a buffer from which OutPortProvider retrieve data.
     * If already buffer is set, previous buffer's pointer will be 
     * overwritten by the given pointer to a buffer.  Since
     * OutPortProvider does not assume ownership of the buffer
     * pointer, destructor of the buffer should be done by user.}
     * 
     * @param buffer 
     *   {@.ja OutPortProviderがデータを取り出すバッファへのポインタ}
     *   {@.en A pointer to a data buffer to be used by OutPortProvider}
     *
     */
    public void setBuffer(BufferBase<OutputStream> buffer){
        m_buffer = buffer;
    }
    
    /**
     * {@.ja [CORBA interface] バッファからデータを取得する}
     * {@.en [CORBA interface] Get data from the buffer}
     *
     * <p>
     * {@.ja 設定された内部バッファからデータを取得する。}
     * {@.en Get data from the internal buffer.}
     *
     * @param data
     *   {@.ja 取得データを格納するバッファ}
     *   {@.en The buffer to get data.}
     *
     * @return
     *   {@.ja ステータス}
     *   {@.en Prot status}
     *
     */
    public OpenRTM.PortStatus get(OpenRTM.CdrDataHolder data) {
        rtcout.println(Logbuf.PARANOID, "OutPortCorbaCdrProvider.get()");

        if (m_buffer == null) {
            onSenderError();
            rtcout.println(Logbuf.PARANOID, "m_buffer is null.");
            return OpenRTM.PortStatus.UNKNOWN_ERROR;
        }

        OutputStream cdr = null;
        DataRef<OutputStream> cdr_ref = new DataRef<OutputStream>(cdr);
        jp.go.aist.rtm.RTC.buffer.ReturnCode ret 
                          = m_buffer.read(cdr_ref,0,0);

        if (ret.equals(jp.go.aist.rtm.RTC.buffer.ReturnCode.BUFFER_OK)) {

            EncapsOutputStreamExt outcdr;
            outcdr = (EncapsOutputStreamExt)cdr_ref.v;
            data.value =  outcdr.getByteArray();

        }
        return convertReturn(ret);
    }
    /**
     * {@.ja ReturnCodeをPortStatusに変換する。}
     * {@.en Converts ReturnCode into PortStatus.}
     * 
     * @param status
     *   {@.ja ReturnCode}
     *   {@.en ReturnCode}
     * @return
     *   {@.ja PortStatus}
     *   {@.en PortStatus}
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
     * {@.ja OutPortCorbaCdrProvider を生成する}
     * {@.en Creats OutPortCorbaCdrProvider}
     * 
     * @return 
     *   {@.ja 生成されたOutPortProvider}
     *   {@.en Object Created instances}
     *
     */
    public OutPortProvider creator_() {
        return new OutPortCorbaCdrProvider();
    }
    /**
     * {@.ja OutPortCorbaCdrProvider を破棄する}
     * {@.en Destructs OutPortCorbaCdrProvider}
     * 
     * @param obj
     *   {@.ja 破棄するインタスタンス}
     *   {@.en The target instances for destruction}
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
     * {@.ja モジュール初期化関数}
     * {@.en Module initialization}
     * <p>
     * {@.ja OutPortCorbaCdrProvider のファクトリを登録する初期化関数。}
     * {@.en This initialization function registers OutPortCorbaCdrProvider's
     * factory.}
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
     * {@.ja InterfaceProfile情報を公開する}
     * {@.en Publish InterfaceProfile information}
     * 
     * <p>
     * {@.ja InterfaceProfile情報を公開する。
     * 引数で指定するプロパティ情報内の NameValue オブジェクトの
     * dataport.interface_type 値を調べ、当該ポートに設定されている
     * インターフェースタイプと一致する場合のみ情報を取得する。}
     * {@.en Publish interfaceProfile information.
     * Check the dataport.interface_type value of the NameValue object 
     * specified by an argument in property information and get information
     * only when the interface type of the specified port is matched.}
     *
     * @param properties 
     *   {@.ja InterfaceProfile情報を受け取るプロパティ}
     *   {@.en Properties to get InterfaceProfile information}
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
     * {@.ja Interface情報を公開する}
     * {@.en Publish interface information}
     * <p>
     * {@.ja Interface情報を公開する。引数で指定するプロパティ情報内の
     * NameValue オブジェクトのdataport.interface_type 値を調べ、当該ポー
     * トに設定されていなければNameValue に情報を追加する。すでに同一イ
     * ンターフェースが登録済みの場合は何も行わない。}
     * {@.en Publish interface information.  Check the
     * dataport.interface_type value of the NameValue object specified
     * by an argument in the property information, and add the
     * information to the NameValue if this port is not specified.
     * This does not do anything if the same interface is already
     * subscribed.}
     *
     * @param properties 
     *   {@.ja Interface情報を受け取るプロパティ}
     *   {@.en Properties to receive interface information}
     * @return 
     *   {@.ja true: 正常終了}
     *   {@.en true: normal return}
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
     * {@.ja Connectorを設定する。}
     * {@.en set Connector}
     * <p>
     * {@.ja OutPort は接続確立時に OutPortConnector オブジェクトを生成し、生
     * 成したオブジェクトのポインタと共にこの関数を呼び出す。所有権は
     * OutPort が保持するので OutPortProvider は OutPortConnector を削
     * 除してはいけない。}
     * {@.en OutPort creates OutPortConnector object when it establishes
     * connection between OutPort and InPort, and it calls this
     * function with a pointer to the connector object. Since the
     * OutPort has the ownership of this connector, OutPortProvider
     * should not delete it.}
     *
     * @param connector 
     *   {@.ja OutPortConnector}
     *   {@.en OutPortConnector}
     */
    public void setConnector(OutPortConnector connector) {
        m_connector = connector;
    }
    /**
     * {@.ja インタフェースプロフィールのポートタイプを設定する。}
     * {@.en Sets PortProfile of the interface profile}
     * 
     * @param portType 
     *   {@.ja ポートタイプ}
     *   {@.en port type}
     */
    protected void setPortType(final String portType) {
        
        this.m_portType = portType;
    }
    
    /**
     * {@.ja インタフェースプロフィールのデータタイプを設定する。}
     * {@.en Sets DataType of the interface profile}
     * 
     * @param dataType 
     *   {@.ja データタイプ}
     *   {@.en dataType} 
     */
    protected void setDataType(final String dataType) {
        
        this.m_dataType = dataType;
    }
    
    /**
     * {@.ja インタフェースプロフィールのインタフェースタイプを設定する。}
     * {@.en Sets interface Type of the interface profile}
     * 
     * @param interfaceType 
     *   {@.ja インタフェースタイプ}
     *   {@.en Intereface Type}
     */
    protected void setInterfaceType(final String interfaceType) {
        
        this.m_interfaceType = interfaceType;
    }
    
    /**
     * {@.ja インタフェースプロフィールのデータフロータイプを設定する。}
     * {@.en Sets data flow type of the interface profile}
     * 
     * @param dataFlowType 
     *   {@.ja データフロータイプ}
     *   {@.en Data flow type}
     */
    protected void setDataFlowType(final String dataFlowType) {
        
        this.m_dataflowType = dataFlowType;
    }
    
    /**
     * {@.ja インタフェースプロフィールのサブスクリプションタイプを設定する。}
     * {@.en Sets subscription type of the interface profile}
     * 
     * @param subscriptionType 
     *   {@.ja サブスクリプションタイプ}
     *   {@.en Subscription type}
     */
    protected void setSubscriptionType(final String subscriptionType) {
        
        this.m_subscriptionType = subscriptionType;
    }
    /**
     * {@.ja リスナを設定する。}
     * {@.en Set the listener.}
     *
     * <p>
     * {@.ja OutPort はデータ送信処理における各種イベントに対して特定のリスナ
     * オブジェクトをコールするコールバック機構を提供する。詳細は
     * ConnectorListener.h の ConnectorDataListener, ConnectorListener
     * 等を参照のこと。OutPortCorbaCdrProvider では、以下のコールバック
     * が提供される。
     * <ol>
     * <li>- ON_BUFFER_READ
     * <li>- ON_SEND
     * <li>- ON_BUFFER_EMPTY
     * <li>- ON_BUFFER_READ_TIMEOUT
     * <li>- ON_SENDER_EMPTY
     * <li>- ON_SENDER_TIMEOUT
     * <li>- ON_SENDER_ERROR </ol>}
     * {@.en OutPort provides callback functionality that calls specific
     * listener objects according to the events in the data publishing
     * process. For details, see documentation of
     * ConnectorDataListener class and ConnectorListener class in
     * ConnectorListener.h. In this OutPortCorbaCdrProvider provides
     * the following callbacks.
     * <ol>
     * <li>- ON_BUFFER_READ
     * <li>- ON_SEND
     * <li>- ON_BUFFER_EMPTY
     * <li>- ON_BUFFER_READ_TIMEOUT
     * <li>- ON_SENDER_EMPTY
     * <li>- ON_SENDER_TIMEOUT
     * <li>- ON_SENDER_ERROR </ol>}
     *
     * @param info 
     *   {@.ja 接続情報}
     *   {@.en @param info Connector information}
     * @param listeners 
     *   {@.ja リスナオブジェクト}
     *   {@.en Listener objects}
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
