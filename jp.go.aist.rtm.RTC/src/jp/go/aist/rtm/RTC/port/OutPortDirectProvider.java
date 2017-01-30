package jp.go.aist.rtm.RTC.port;

import jp.go.aist.rtm.RTC.OutPortProviderFactory;
import jp.go.aist.rtm.RTC.ObjectCreator;
import jp.go.aist.rtm.RTC.ObjectDestructor;
import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.log.Logbuf;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.NVListHolderFactory;
import jp.go.aist.rtm.RTC.util.NVUtil;
//import jp.go.aist.rtm.RTC.util.ORBUtil;
//import jp.go.aist.rtm.RTC.util.POAUtil;
import jp.go.aist.rtm.RTC.util.Properties;

import org.omg.CORBA.ORB;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.OutputStream;

//import OpenRTM.OutPortCdrPOA;
import _SDOPackage.NVListHolder;


/**
 * {@.ja OutPortDirectProvider クラス}
 * {@.en OutPortDirectProvider class}
 * <p>
 * {@.ja データ転送に CORBA の OpenRTM::OutPortCdr インターフェースを利用し
 * た、push 型データフロー型を実現する OutPort プロバイダクラス。}
 * {@.en This is an implementation class of the input port Provider
 * that uses CORBA for means of communication.}
 *
 *
 */
public class OutPortDirectProvider implements OutPortProvider, ObjectCreator<OutPortProvider>, ObjectDestructor {
    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     *
     */
    public OutPortDirectProvider() {
        m_buffer = null; 
        rtcout = new Logbuf("OutPortDirectProvider");
        // PortProfile setting
        setInterfaceType("direct");
    
/*
        // ConnectorProfile setting
        m_objref = this._this();
    
        // set OutPort's reference
        ORB orb = ORBUtil.getOrb();
        CORBA_SeqUtil.push_back(m_properties,
                NVUtil.newNVString("dataport.corba_cdr.inport_ior",
                              orb.object_to_string(m_objref)));
        CORBA_SeqUtil.push_back(m_properties,
                NVUtil.newNV("dataport.corba_cdr.inport_ref",
                              m_objref, OpenRTM.OutPortCdr.class ));

        m_orb = ORBUtil.getOrb();
*/

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
/*
    public OpenRTM.OutPortCdr _this() {
        
        if (this.m_objref == null) {
            try {
                this.m_objref = 
                        OpenRTM.OutPortCdrHelper.narrow(POAUtil.getRef(this));
            } catch (Exception e) {
                rtcout.println(Logbuf.WARN, "The exception was caught.");
                throw new IllegalStateException(e);
            }
        }
        
        return this.m_objref;
    }
*/
    /**
     * {@.ja 設定初期化}
     * {@.en Initializing configuration}
     * <p>
     * {@.ja OutPortDirectProvider の各種設定を行う。与えられた
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
     */
    public void init(Properties prop){
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
     */
    public void setBuffer(BufferBase<OutputStream> buffer) {
        m_buffer = buffer;
    }

    /**
     * {@.ja [CORBA interface] バッファにデータを書き込む}
     * {@.en [CORBA interface] Write data into the buffer}
     *
     * <p>
     * {@.ja 設定されたバッファにデータを書き込む。}
     * {@.en Write data into the specified buffer.}
     * </p>
     *
     * @param data 
     *   {@.ja 書込対象データ}
     *   {@.en The target data for writing}
     *
     */
    public OpenRTM.PortStatus put(byte[] data)
      throws SystemException {

        rtcout.println(Logbuf.PARANOID, "OutPortDirectProvider.put()");
/*
        if (m_buffer == null) {
            EncapsOutputStreamExt cdr 
            = new EncapsOutputStreamExt(m_orb,m_connector.isLittleEndian());
            cdr.write_octet_array(data, 0, data.length);
            onReceiverError(cdr);
            return OpenRTM.PortStatus.PORT_ERROR;
        }


        rtcout.println(Logbuf.PARANOID, "received data size: "+data.length);


        EncapsOutputStreamExt cdr 
            = new EncapsOutputStreamExt(m_orb,m_connector.isLittleEndian());
        cdr.write_octet_array(data, 0, data.length);

        int len = cdr.getByteArray().length;
        rtcout.println(Logbuf.PARANOID, "converted CDR data size: "+len);
        onReceived(cdr);
        jp.go.aist.rtm.RTC.buffer.ReturnCode ret = m_buffer.write(cdr);
        return convertReturn(ret,cdr);
*/
        return OpenRTM.PortStatus.from_int(
                                    OpenRTM.PortStatus._UNKNOWN_ERROR);
    }

    /**
     * {@.ja [CORBA interface] バッファにデータを書き込む}
     * {@.en [CORBA interface] Write data into the buffer}
     * <p>
     * {@.ja 設定されたバッファにデータを書き込む。}
     * {@.en Write data into the specified buffer.}
     *
     * @param data 
     *   {@.ja 書込対象データ}
     *   {@.en The target data for writing}
     *
     * @return
     *   {@.ja ステータス}
     *   {@.en Prot status}
     */
    public OpenRTM.PortStatus put(final OpenRTM.CdrDataHolder data)
      throws SystemException {
        return put(data.value);

    }
    /**
     * {@.ja リターンコード変換}
     * {p.en Return codes conversion}
     *
     * @param status
     *   {@.ja ReturnCode}
     *   {@.en ReturnCode}
     * @return
     *   {@.ja PortStatus}
     *   {@.en PortStatus}
     */
    protected OpenRTM.PortStatus 
    convertReturn(jp.go.aist.rtm.RTC.buffer.ReturnCode status,
                  DataRef<OutputStream> data) {
        switch (status) {
            case BUFFER_OK:
                onBufferWrite(data);
                return OpenRTM.PortStatus.from_int(OpenRTM.PortStatus._PORT_OK);
            case BUFFER_ERROR:
                onReceiverError(data);
                return OpenRTM.PortStatus.from_int(
                                            OpenRTM.PortStatus._PORT_ERROR);

            case BUFFER_FULL:
                onBufferFull(data);
                onReceiverFull(data);
                return OpenRTM.PortStatus.from_int(
                                            OpenRTM.PortStatus._BUFFER_FULL);

            case BUFFER_EMPTY:
                // never come here
                return OpenRTM.PortStatus.from_int(
                                            OpenRTM.PortStatus._BUFFER_EMPTY);
            case TIMEOUT:
                onBufferWriteTimeout(data);
                onReceiverTimeout(data);
                return OpenRTM.PortStatus.from_int(
                                            OpenRTM.PortStatus._BUFFER_TIMEOUT);
            case PRECONDITION_NOT_MET:
                onReceiverError(data);
                return OpenRTM.PortStatus.from_int(
                                            OpenRTM.PortStatus._PORT_ERROR);
            default:
                onReceiverError(data);
                return OpenRTM.PortStatus.from_int(
                                            OpenRTM.PortStatus._UNKNOWN_ERROR);
        }
    }

    private Logbuf rtcout;

    /**
     * {@.ja OutPortDirectProvider を生成する}
     * {@.en Creats OutPortDirectProvider}
     * 
     * @return 
     *   {@.ja 生成されたOutPortProvider}
     *   {@.en Object Created instances}
     *
     */
    public OutPortProvider creator_() {
        return new OutPortDirectProvider();
    }
    /**
     * {@.ja OutPortDirectProvider を破棄する}
     * {@.en Destructs OutPortDirectProvider}
     * 
     * @param obj
     *   {@.ja 破棄するインタスタンス}
     *   {@.en The target instances for destruction}
     *
     */
    public void destructor_(Object obj) {
/*
        try{
            byte[] oid 
                = _default_POA().servant_to_id((OutPortDirectProvider)obj);
            _default_POA().deactivate_object(oid);
        }
        catch(Exception e){
            e.printStackTrace();
        } 
*/
        obj = null;
    }

    /**
     * {@.ja モジュール初期化関数}
     * {@.en Module initialization}
     * <p>
     * {@.ja OutPortCorbaCdrConsumer のファクトリを登録する初期化関数。}
     * {@.en This initialization function registers OutPortCorbaCdrConsumer's 
     * factory.}
     */
    public static void OutPortDirectProviderInit() {
        final OutPortProviderFactory<OutPortProvider,String> factory 
            = OutPortProviderFactory.instance();

        factory.addFactory("direct",
                    new OutPortDirectProvider(),
                    new OutPortDirectProvider());
    
    }
    /**
     * <p>InterfaceProfile情報を公開します。</p>
     * 
     * @param properties InterfaceProfile情報を受け取るホルダオブジェクト
     */
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
     *   {@.ja InterfaceProfile情報を受け取るホルダオブジェクト}
     *   {@.en Holder object to get InterfaceProfile information}
     *
     */
    public void publishInterfaceProfile(NVListHolder properties) {

        NVUtil.appendStringValue(properties, "dataport.interface_type",
                this.m_interfaceType);
        NVUtil.append(properties, this.m_properties);
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
     *   {@.ja properties Interface情報を受け取るホルダオブジェクト}
     *   {@.en Holder object to receive interface information}
     * @return 
     *   {@.ja true: 正常終了}
     *   {@.en true: normal return}
     */
    public boolean publishInterface(NVListHolder properties) {

        rtcout.println(Logbuf.TRACE, "publishInterface()");
        rtcout.println(Logbuf.DEBUG, NVUtil.toString(properties));


        if (! NVUtil.isStringValue(properties,
                "dataport.interface_type",
                this.m_interfaceType)) {
            return false;
        }

        NVUtil.append(properties, this.m_properties);
        return true;

    }

    /**
     * {@.ja リスナを設定する。}
     * {@.en Set the listener.}
     * 
     * <p>
     * {@.ja OutPort はデータ送信処理における各種イベントに対して特定のリスナ
     * オブジェクトをコールするコールバック機構を提供する。詳細は
     * ConnectorListener.h の ConnectorDataListener, ConnectorListener
     * 等を参照のこと。OutPortDirectProvider では、以下のコールバック
     * が提供される。}
     * {@.en OutPort provides callback functionality that calls specific
     * listener objects according to the events in the data publishing
     * process. For details, see documentation of
     * ConnectorDataListener class and ConnectorListener class in
     * ConnectorListener.h. In this OutPortDirectProvider provides
     * the following callbacks.}
     * <ul>
     * <li>- ON_BUFFER_WRITE
     * <li>- ON_BUFFER_FULL
     * <li>- ON_BUFFER_WRITE_TIMEOUT
     * <li>- ON_BUFFER_OVERWRITE
     * <li>- ON_RECEIVED
     * <li>- ON_RECEIVER_FULL
     * <li>- ON_RECEIVER_FULL
     * <li>- ON_RECEIVER_TIMEOUT
     * <li>- ON_RECEIVER_ERROR </li></ul>
     * 
     *
     * @param info 
     *   {@.ja 接続情報}
     *   {@.en Connector information}
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
     * {@.ja Connectorを設定する。}
     * {@.en set Connector}
     * <p>
     * {@.ja OutPort は接続確立時に OutPortConnector オブジェクトを生成し、生
     * 成したオブジェクトのポインタと共にこの関数を呼び出す。所有権は
     * OutPort が保持するので OutPortProvider は OutPortConnector を削
     * 除してはいけない。}
     * {@.en OutPort creates OutPortConnector object when it establishes
     * connection between OutPort and OutPort, and it calls this
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
     * {@.ja インタフェースプロフィールのデータタイプを設定する。}
     * {@.en Sets DataType of the interface profile}
     * 
     * @param dataType 
     *   {@.ja データタイプ}
     *   {@.en dataType} 
     */
/*
    protected void setDataType(final String dataType) {
        this.m_dataType = dataType;
    }
*/    
    /**
     * {@.ja インタフェースプロフィールのインタフェースタイプを設定する。}
     * {@.en Sets interface Type of the interface profile}
     * 
     * @param interfaceType 
     *   {@.ja インタフェースタイプ}
     *   {@.en Intereface Type}
     */
     protected void setInterfaceType(final String interfaceType) {
        rtcout.println(Logbuf.TRACE, "setInterfaceType("+interfaceType+")");
        this.m_interfaceType = interfaceType;
    }
    
    /**
     * {@.ja インタフェースプロフィールのデータフロータイプを設定する。}
     * {@.en Sets data flow type of the interface profile}
     * 
     * @param dataflowType 
     *   {@.ja データフロータイプ}
     *   {@.en Data flow type}
     */
/*
    protected void setDataFlowType(final String dataflowType) {
        rtcout.println(Logbuf.TRACE, "setDataFlowType("+dataflowType+")");
        this.m_dataflowType = dataflowType;
    }
*/  
    /**
     * {@.ja インタフェースプロフィールのサブスクリプションタイプを設定する。}
     * {@.en Sets subscription type of the interface profile}
     * 
     * @param subscriptionType 
     *   {@.ja サブスクリプションタイプ}
     *   {@.en Subscription type}
     */
/*
    protected void setSubscriptionType(final String subscriptionType) {
        rtcout.println(Logbuf.TRACE,
                       "setSubscriptionType("+subscriptionType+")");
        this.m_subscriptionType = subscriptionType;
    }
*/

    /**
     * <p> Connector data listener functions </p>
     */
    /**
     * {@.ja ON_BUFFER_WRITE のリスナへ通知する。}
     * {@.en Notify an ON_BUFFER_WRITE event to listeners}
     * @param data
     *   {@.ja OutputStream} 
     *   {@.en OutputStream} 
     */
    private void onBufferWrite(DataRef<OutputStream> data) {
        m_listeners.connectorData_[ConnectorDataListenerType.ON_BUFFER_WRITE].notify(m_profile, data);
    }

    /**
     * {@.ja ON_BUFFER_FULL のリスナへ通知する。}
     * {@.en Notify an ON_BUFFER_FULL event to listeners}
     * @param data
     *   {@.ja OutputStream} 
     *   {@.en OutputStream} 
     */
    private void onBufferFull(DataRef<OutputStream> data) {
      m_listeners.connectorData_[ConnectorDataListenerType.ON_BUFFER_FULL].notify(m_profile, data);
    }

    /**
     * {@.ja ON_BUFFER_WRITE_TIMEOUT のリスナへ通知する。} 
     * {@.en Notify an ON_BUFFER_WRITE_TIMEOUT event to listeners}
     * @param data
     *   {@.ja OutputStream} 
     *   {@.en OutputStream} 
     */
    private void onBufferWriteTimeout(DataRef<OutputStream> data) {
      m_listeners.connectorData_[ConnectorDataListenerType.ON_BUFFER_WRITE_TIMEOUT].notify(m_profile, data);
    }

    /**
     * {@.ja ON_BUFFER_WRITE_OVERWRITE のリスナへ通知する。}
     * {@.en Notify an ON_BUFFER_WRITE_OVERWRITE event to listeners}
     * @param data
     *   {@.ja OutputStream} 
     *   {@.en OutputStream} 
     */
    private void onBufferWriteOverwrite(DataRef<OutputStream> data) {
      m_listeners.connectorData_[ConnectorDataListenerType.ON_BUFFER_OVERWRITE].notify(m_profile, data);
    }

//    private void onBufferRead(final OutputStream data) {
//      m_listeners.connectorData_[ConnectorDataListenerType.ON_BUFFER_READ].notify(m_profile, data);
//    }

//    private void onSend(final OutputStream data) {
//      m_listeners.connectorData_[ConnectorDataListenerType.ON_SEND].notify(m_profile, data);
//    }

    /**
     * {@.ja ON_RECEIVED のリスナへ通知する。} 
     * {@.en Notify an ON_RECEIVED event to listeners}
     * @param data
     *   {@.ja OutputStream} 
     *   {@.en OutputStream} 
     */
    //private void onReceived(final OutputStream data) {
    private void onReceived(DataRef<OutputStream> data) {
      m_listeners.connectorData_[ConnectorDataListenerType.ON_RECEIVED].notify(m_profile, data);
    }

    /**
     * {@.ja ON_RECEIVER_FULL のリスナへ通知する。}
     * {@.en Notify an ON_RECEIVER_FULL event to listeners}
     * @param data
     *   {@.ja OutputStream} 
     *   {@.en OutputStream} 
     */
    private void onReceiverFull(DataRef<OutputStream> data) {
      m_listeners.connectorData_[ConnectorDataListenerType.ON_RECEIVER_FULL].notify(m_profile, data);
    }

    /**
     * {@.ja ON_RECEIVER_TIMEOUT のリスナへ通知する。}
     * {@.en Notify an ON_RECEIVER_TIMEOUT event to listeners}
     * @param data
     *   {@.ja OutputStream} 
     *   {@.en OutputStream} 
     */
    private void onReceiverTimeout(DataRef<OutputStream> data) {
      m_listeners.connectorData_[ConnectorDataListenerType.ON_RECEIVER_TIMEOUT].notify(m_profile, data);
    }

    /**
     * {@.ja ON_RECEIVER_ERRORのリスナへ通知する。}
     * {@.en Notify an ON_RECEIVER_ERROR event to listeners}
     * @param data
     *   {@.ja OutputStream} 
     *   {@.en OutputStream} 
     */
    private void onReceiverError(DataRef<OutputStream> data) {
      m_listeners.connectorData_[ConnectorDataListenerType.ON_RECEIVER_ERROR].notify(m_profile, data);
    }

    /**
     * <p> Connector listener functions </p>
     */
//    private void onBufferEmpty() {
//      m_listeners.connector_[ConnectorDataListenerType.ON_BUFFER_EMPTY].notify(m_profile);
//    }

//    privaet void onBufferReadTimeout(){
//      m_listeners.connector_[ConnectorDataListenerType.ON_BUFFER_READ_TIMEOUT].notify(m_profile);
//    }

//    privaet void onSenderEmpty() {
//      m_listeners.connector_[ConnectorDataListenerType.ON_SENDER_EMPTY].notify(m_profile);
//    }

//    privaet void onSenderTimeout() {
//      m_listeners.connector_[ConnectorDataListenerType.ON_SENDER_TIMEOUT].notify(m_profile);
//    }

//    private void onSenderError(){
//      m_listeners.connector_[ConnectorDataListenerType.ON_SENDER_ERROR].notify(m_profile);
//    }



    /**
     * <p>インタフェース情報を保持するオブジェクトです。</p>
     */
    protected NVListHolder m_properties = NVListHolderFactory.create();

    private String m_dataType = new String();
    private String m_interfaceType = new String();
    private String m_dataflowType = new String();
    private String m_subscriptionType = new String();

    private BufferBase<OutputStream> m_buffer;
    private OpenRTM.OutPortCdr m_objref;

//    private ORB m_orb;
    private OutPortConnector m_connector;
    private ConnectorListeners m_listeners;
    private ConnectorBase.ConnectorInfo m_profile; 
}

