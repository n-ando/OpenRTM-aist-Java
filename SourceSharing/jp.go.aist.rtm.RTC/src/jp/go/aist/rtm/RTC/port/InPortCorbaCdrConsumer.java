package jp.go.aist.rtm.RTC.port;

import jp.go.aist.rtm.RTC.InPortConsumerFactory;
import jp.go.aist.rtm.RTC.ObjectCreator;
import jp.go.aist.rtm.RTC.ObjectDestructor;
import jp.go.aist.rtm.RTC.log.Logbuf;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.ORBUtil;
import jp.go.aist.rtm.RTC.util.Properties;

import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.portable.OutputStream;

import _SDOPackage.NVListHolder;


/**
 * {@.ja InPortCorbaCdrConsumer クラス}
 * {@.en InPortCorbaCdrConsumer class}
 * <p>
 * {@.ja データ転送に CORBA の OpenRTM::InPortCdr インターフェースを利用し
 * た、push 型データフロー型を実現する InPort コンシューマクラス。}
 * {@.en This is an implementation class of the input port Consumer  </p>
 * that uses CORBA for means of communication. </p>
 * The InPort consumer class which uses the OpenRTM::InPortCdr
 * interface in CORBA for data transfer and realizes a push-type
 * dataflow.}
 */

public class InPortCorbaCdrConsumer extends CorbaConsumer< OpenRTM.InPortCdr > implements InPortConsumer, ObjectCreator<InPortConsumer>, ObjectDestructor {
    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     * <p>
     * {@.en buffer 当該コンシューマに割り当てるバッファオブジェクト}
     * {@.en buffer The buffer object that is attached to this Consumer}
     *
     */
    public InPortCorbaCdrConsumer() {
        super(OpenRTM.InPortCdr.class);
        rtcout = new Logbuf("InPortCorbaCdrConsumer");
//        rtcout.setLevel("PARANOID");
        m_orb = ORBUtil.getOrb();
    }
    /**
     * {@.ja 設定初期化}
     * {@.en Initializing configuration}
     * <p>
     * {@.ja InPortConsumerの各種設定を行う。実装クラスでは、与えられた
     * Propertiesから必要な情報を取得して各種設定を行う。この init() 関
     * 数は、InPortProvider生成直後および、接続時にそれぞれ呼ばれる可
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
    public void init(Properties prop) {
        m_properties = prop;
    }

    /**
     * <p> Send data to the destination port </p>
     * <p> Pure virtual function to send data to the destination port. </p>
     *
     */
    /**
     * {@.ja 接続先へのデータ送信}
     * {@.en Send data to the destination port}
     * <p>
     * {@.ja 接続先のポートへデータを送信するための純粋仮想関数。
     * 
     * この関数は、以下のリターンコードを返す。
     * <ul>
     * <li>- PORT_OK:     正常終了。
     * <li>- PORT_ERROR:  データ送信の過程で何らかのエラーが発生した。
     * <li>- SEND_FULL:   データを送信したが、相手側バッファがフルだった。
     * <li>- SEND_TIMEOUT:データを送信したが、相手側バッファがタイムアウトした。
     * <li>- UNKNOWN_ERROR:原因不明のエラー</ul>}
     * {@.en Pure virtual function to send data to the destination port.
     *
     * This function might the following return codes
     * <ul>
     * <li>- PORT_OK:       Normal return
     * <li>- PORT_ERROR:    Error occurred in data transfer process
     * <li>- SEND_FULL:     Buffer full although OutPort tried to send data
     * <li>- SEND_TIMEOUT:  Timeout although OutPort tried to send data
     * <li>- UNKNOWN_ERROR: Unknown error</ul>}
     *
     * @param data 
     *   {@.ja 送信するデータ}
     *   {@.en Data sent by this operation.}
     * @return 
     *   {@.ja リターンコード}
     *   {@.en ReturnCode}
     */
    public ReturnCode put(final OutputStream data) {
        rtcout.println(Logbuf.PARANOID, "put");
        
        EncapsOutputStreamExt cdr;
        cdr = (EncapsOutputStreamExt)data;
        byte[] ch = cdr.getByteArray();
        EncapsOutputStreamExt output_stream 
            = new EncapsOutputStreamExt(m_orb, m_connector.isLittleEndian());
        output_stream.write_octet_array(ch,0,ch.length);

        try {
            OpenRTM.PortStatus ret = _ptr().put(output_stream.getByteArray());
            return convertReturn(ret);
        }
        catch (Exception e) {
            return ReturnCode.CONNECTION_LOST;
        }
    }
    /**
     * {@.ja InterfaceProfile情報を公開する}
     * {@.en Publish InterfaceProfile information}
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
     */
    public void publishInterfaceProfile(NVListHolder properties) {
        return;
    }
    /**
     * {@.ja データ送信通知への登録}
     * {@.en Subscribe to the data sending notification}
     * <p>
     * {@.ja 指定されたプロパティに基づいて、データ送出通知の受け取り
     * に登録する。}
     * {@.en Subscribe to the data sending notification based on specified 
     * property information.}
     *
     * @param properties 
     *   {@.ja 登録情報}
     *   {@.en Information for subscription}
     *
     * @return 
     *   {@.ja 登録処理結果(登録成功:true、登録失敗:false)}
     *   {@.en Subscription result (Successful:true, Failed:false)}
     */
    public boolean subscribeInterface(final NVListHolder properties) {
        rtcout.println(Logbuf.TRACE, "subscribeInterface()");
        if(properties.value == null){
            rtcout.println(Logbuf.DEBUG, "NVListHolder is null.");
            return false;
        }
        rtcout.println(Logbuf.DEBUG, 
                       "Length of NVListHolder:"+properties.value.length);
        rtcout.println(Logbuf.DEBUG, NVUtil.toString(properties));
    
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
     * {@.ja データ送信通知からの登録解除}
     * {@.en Unsubscribe the data send notification}
     * <p>
     * {@.ja データ送出通知の受け取りから登録を解除する。}
     * {@.en Unsubscribe the data send notification.}
     *
     * @param properties 
     *   {@.ja 登録解除情報}
     *   {@.en Information for unsubscription}
     */
    public void unsubscribeInterface(final NVListHolder properties) {
        rtcout.println(Logbuf.TRACE, "unsubscribeInterface()");
        rtcout.println(Logbuf.DEBUG, NVUtil.toString(properties));
    
        if (unsubscribeFromIor(properties)) { 
            return; 
        }
        unsubscribeFromRef(properties);
    }

    /**
     * {@.ja IOR文字列からオブジェクト参照を取得する}
     * {@.en Getting object reference fromn IOR string}
     * @param properties 
     *   {@.ja Information for subscription}
     *   {@.en Information for subscription}
     *
     * @return 
     *   {@.ja true: 正常取得, false: 取得失敗}
     *   {@.en true: succeeded, false: failed}
     */
    private boolean subscribeFromIor(final NVListHolder properties) {
        rtcout.println(Logbuf.TRACE, "subscribeFromIor()");
    
        int index;
        index = NVUtil.find_index(properties,
                               "dataport.corba_cdr.inport_ior");
        if (index < 0) {
            rtcout.println(Logbuf.ERROR, "inport_ior not found");
            return false;
        }
        rtcout.println(Logbuf.DEBUG, "index:"+index);
    
        final String ior;

        try {
            rtcout.println(Logbuf.DEBUG, 
                            "type:"+properties.value[index].value.type());
            if( properties.value[index].value.type().kind() == 
                TCKind.tk_wstring ) {
                ior = properties.value[index].value.extract_wstring();
            } else {
                ior = properties.value[index].value.extract_string();
            }
        }
        catch(BAD_OPERATION e) {
            rtcout.println(Logbuf.ERROR, "inport_ior has no string");
            return false;
        }
    
        ORB orb = ORBUtil.getOrb();
        org.omg.CORBA.Object obj = orb.string_to_object(ior);
    
        if (obj==null) {
            rtcout.println(Logbuf.ERROR, "invalid IOR string has been passed");
            return false;
        }
    
        if (!super.setObject(obj)) {
            rtcout.println(Logbuf.WARN, "Setting object to consumer failed.");
            return false;
        }
        return true;
    }

    /**
     * {@.ja Anyから直接オブジェクト参照を取得する}
     * {@.en Getting object reference fromn Any directry}
     * @param properties 
     *   {@.ja Information for subscription}
     *   {@.en Information for subscription}
     * @return 
     *   {@.ja true: 正常取得, false: 取得失敗}
     *   {@.en true: succeeded, false: failed}
     *
     */
    private boolean subscribeFromRef(final NVListHolder properties) {
        rtcout.println(Logbuf.TRACE, "subscribeFromRef()");
        int index;
        index = NVUtil.find_index(properties,
                                   "dataport.corba_cdr.inport_ref");
        if (index < 0) {
            rtcout.println(Logbuf.ERROR, "inport_ref not found");
            return false;
        }
    
        org.omg.CORBA.Object obj;
        try {
            obj = properties.value[index].value.extract_Object();
        }
        catch(BAD_OPERATION e){
            rtcout.println(Logbuf.ERROR, "prop[inport_ref] is not objref");
            return true;
        }
    
        if (obj==null) {
            rtcout.println(Logbuf.ERROR, "prop[inport_ref] is not objref");
            return false;
        }
    
        if (!super.setObject(obj)) {
            rtcout.println(Logbuf.ERROR, "Setting object to consumer failed.");
            return false;
        }
        return true;
    }

    /**
     * {@.ja 接続解除(IOR版)}
     * {@.en ubsubscribing (IOR version)}
     *
     * @param properties 
     *   {@.ja Information for unsubscription}
     *   {@.en Information for unsubscription}
     *
     * @return 
     *   {@.ja true: 正常取得, false: 取得失敗}
     *   {@.en true: succeeded, false: failed}
     */
    private boolean unsubscribeFromIor(final NVListHolder properties) {
        rtcout.println(Logbuf.TRACE, "unsubscribeFromIor()");
        int index;
        index = NVUtil.find_index(properties,
                                   "dataport.corba_cdr.inport_ior");
        if (index < 0) {
            rtcout.println(Logbuf.ERROR, "inport_ior not found");
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
            rtcout.println(Logbuf.ERROR, "inport_ior has no string");
            return false;
        }
    
        ORB orb = ORBUtil.getOrb();
        org.omg.CORBA.Object var = orb.string_to_object(ior);
        if (!(_ptr()._is_equivalent(var))) {
            rtcout.println(Logbuf.ERROR, "connector property inconsistency");
            return false;
        }
    
        releaseObject();
        return true;
    }

    /**
     * {@.ja 接続解除(Object reference版)}
     * {@.en ubsubscribing (Object reference version)}
     *
     * @param properties 
     *   {@.ja Information for unsubscription}
     *   {@.en Information for unsubscription}
     * @return 
     *   {@.ja true: 正常取得, false: 取得失敗}
     *   {@.en true: succeeded, false: failed}
     */
    private boolean unsubscribeFromRef(final NVListHolder properties) {
        rtcout.println(Logbuf.TRACE, "unsubscribeFromRef()");
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
            rtcout.println(Logbuf.ERROR, "prop[inport_ref] is not objref");
            return false;
        }
    
        if (!(_ptr()._is_equivalent(obj))) {
            rtcout.println(Logbuf.ERROR, "connector property inconsistency");
            return false;
        }
        
        releaseObject();
        return true;
    }
    /**
     * {@.ja PortStatusをReturnCodeに変換する。}
     * {@.en Converts PortStatus into ReturnCode.}
     * 
     * @param status
     *   {@.ja PortStatus}
     *   {@.en PortStatus}
     * @return
     *   {@.ja ReturnCode}
     *   {@.en ReturnCode}
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
     * {@.ja InPortCorbaCdrConsumer を生成する}
     * {@.en Creats InPortCorbaCdrConsumer}
     * 
     * @return 
     *   {@.ja 生成されたInPortConsumer}
     *   {@.en Object Created instances}
     *
     *
     */
    public InPortConsumer creator_() {
        return new InPortCorbaCdrConsumer();
    }
    /**
     * {@.ja Object を破棄する}
     * {@.en Destructs Object}
     * 
     * @param obj
     *   {@.ja 破棄するインタスタンス}
     *   {@.en The target instances for destruction}
     *
     */
    public void destructor_(Object obj) {
        obj = null;
    }
    /**
     * {@.ja モジュール初期化関数}
     * {@.en Module initialization}
     * <p>
     * {@.ja InPortCorbaCdrConsumer のファクトリを登録する初期化関数。}
     * {@.en This initialization function registers InPortCorbaCdrConsumer's 
     * factory.}
     */
    public static void InPortCorbaCdrConsumerInit() {
        final InPortConsumerFactory<InPortConsumer,String> factory 
            = InPortConsumerFactory.instance();

        factory.addFactory("corba_cdr",
                    new InPortCorbaCdrConsumer(),
                    new InPortCorbaCdrConsumer());
    
    }
    /**
     * {@.ja Connectorを設定する。}
     * {@.en set Connector}
     *
     * @param connector 
     *   {@.ja OutPortConnector}
     *   {@.en OutPortConnector}
     */
    public void setConnector(OutPortConnector connector) {
        m_connector = connector;
    }

    private Logbuf rtcout;
    private Properties m_properties;
    private OutPortConnector m_connector;
    private ORB m_orb;

}

