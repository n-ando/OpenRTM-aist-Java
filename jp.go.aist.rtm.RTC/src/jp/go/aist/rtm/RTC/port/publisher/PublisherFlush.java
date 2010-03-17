package jp.go.aist.rtm.RTC.port.publisher;

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.port.ReturnCode;
import jp.go.aist.rtm.RTC.port.InPortConsumer;
import jp.go.aist.rtm.RTC.port.ConnectorDataListenerType;
import jp.go.aist.rtm.RTC.port.ConnectorListenerType;
import jp.go.aist.rtm.RTC.port.ConnectorListeners;
import jp.go.aist.rtm.RTC.port.ConnectorBase;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.PublisherBaseFactory;
import jp.go.aist.rtm.RTC.ObjectCreator;
import jp.go.aist.rtm.RTC.ObjectDestructor;
import jp.go.aist.rtm.RTC.log.Logbuf;

/**
 * <p>データ送出を待つコンシューマを、送出する側と同じスレッドで動作させる場合に使用します。</p>
 */
public class PublisherFlush extends PublisherBase implements ObjectCreator<PublisherBase>, ObjectDestructor{

    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     */
    public PublisherFlush() {
        rtcout = new Logbuf("PublisherFlush");
        m_consumer = null;
        m_listeners = null;
        m_retcode = ReturnCode.PORT_OK;
        m_active = false;
    }
    
    /**
     * <p>当該Publisherが不要となり破棄される際に、PublisherFactoryにより呼び出されます。</p>
     */
    public void destruct() {
        m_consumer = null;
        m_listeners = null;
    }
    
    /**
     * <p>ファイナライザです。</p>
     */
    protected void finalize() throws Throwable {
        try {
            destruct();
            
        } finally {
            super.finalize();
        }
    }


    /**
     * <p> init </p>
     * <p> initialization </p>
     *
     * @param prop
     * @return ReturnCode
     */
    public ReturnCode init(Properties prop) {
        return ReturnCode.PORT_OK;
    }
    /**
     * <p> setConsumer </p>
     * <p> Store InPort consumer </p>
     *
     * @param consumer
     * @return ReturnCode
     */
    public ReturnCode setConsumer(InPortConsumer consumer) {
        if (consumer == null) {
            return ReturnCode.INVALID_ARGS;
        }
        m_consumer = consumer;
        return ReturnCode.PORT_OK;
    }
    /**
     * <p> setBuffer </p>
     * <p> Setting buffer </p>
     *
     * @param buffer
     * @return ReturnCode
     */
    public ReturnCode setBuffer(BufferBase<OutputStream> buffer) {
        return ReturnCode.PORT_ERROR;
    }
    /**
     * <p> Setting buffer pointer </p>
     */ 
    public ReturnCode setListener(ConnectorBase.ConnectorInfo info,
                              ConnectorListeners listeners) {
        rtcout.println(rtcout.TRACE, "setListeners()" );

        if (listeners == null) {
            rtcout.println(rtcout.ERROR, 
                "setListeners(listeners == 0): invalid argument" );
            return ReturnCode.INVALID_ARGS;
        }

        m_profile = info;
        m_listeners = listeners;

        return ReturnCode.PORT_OK;
    }

    /**
     * {@.ja データを書き込む}
     * {@.en Write data}
     *
     * <p>
     * {@.ja Publisher が保持するコンシューマに対してデータを書き込む。コン
     * シューマ、リスナ等が適切に設定されていない等、Publisher オブジェ
     * クトが正しく初期化されていない場合、この関数を呼び出すとエラーコー
     * ド PRECONDITION_NOT_MET が返され、コンシューマへの書き込み等の操
     * 作は一切行われない。
     *
     * コンシューマへの書き込みに対して、コンシューマがフル状態、コン
     * シューマのエラー、コンシューマへの書き込みがタイムアウトした場合
     * にはそれぞれ、エラーコード SEND_FULL, SEND_ERROR, SEND_TIMEOUT
     * が返される。
     *
     * これら以外のエラーの場合、PORT_ERROR が返される。}
     * {@.en This function writes data into the consumer associated with
     * this Publisher. If this function is called without initializing
     * correctly such as a consumer, listeners, etc., error code
     * PRECONDITION_NOT_MET will be returned and no operation of the
     * writing to the consumer etc. will be performed.
     *
     * When publisher writes data to the buffer, if the consumer
     * returns full-status, returns error, is returned with timeout,
     * error codes BUFFER_FULL, BUFFER_ERROR and BUFFER_TIMEOUT will
     * be returned respectively.
     *
     * In other cases, PROT_ERROR will be returned.}
     * </p>
     *
     * 
     *
     * @param data 
     *   {@.ja 書き込むデータ}
     *   {@.en Data to be wrote to the buffer}
     * @param sec 
     *   {@.ja タイムアウト時間}
     *   {@.en Timeout time in unit seconds}
     * @param usec 
     *   {@.ja タイムアウト時間}
     *   {@.en Timeout time in unit micro-seconds}
     *
     * @return 
     *   {@.ja PORT_OK             正常終了
     *         PRECONDITION_NO_MET consumer, buffer, listener等が適切に設定
     *                             されていない等、このオブジェクトの事前条件
     *                             を満たさない場合。
     *         SEND_FULL           送信先がフル状態
     *         SEND_TIMEOUT        送信先がタイムアウトした
     *         CONNECTION_LOST     接続が切断されたことを検知した。}
     *
     *   {@.en PORT_OK             Normal return
     *         PRECONDITION_NO_MET Precondition does not met. A consumer,
     *                             a buffer, listenes are not set properly.
     *         SEND_FULL           Data was sent but full-status returned
     *         SEND_TIMEOUT        Data was sent but timeout occurred
     *         CONNECTION_LOST     detected that the connection has been lost}
     *
     */
    public ReturnCode write(final OutputStream data, int sec, int usec) {
        if (m_consumer == null ) { 
            return ReturnCode.PRECONDITION_NOT_MET; 
        }
        if (m_listeners == null ) { 
            return ReturnCode.PRECONDITION_NOT_MET; 
        }
        if (m_retcode.equals(ReturnCode.CONNECTION_LOST)) {
            rtcout.println(rtcout.DEBUG, "write(): connection lost." );
            return m_retcode;
        }

        onSend(data);
        //return m_consumer.put(data);
        ReturnCode ret = m_consumer.put(data);
        switch (ret) {
            case PORT_OK:
                onReceived(data);
                return ret;
            case PORT_ERROR:
                onReceiverError(data);
                return ret;
            case SEND_FULL:
                 onReceiverFull(data);
                 return ret;
            case SEND_TIMEOUT:
                 onReceiverTimeout(data);
                 return ret;
            case UNKNOWN_ERROR:
                 onReceiverError(data);
                 return ret;
            default:
                 onReceiverError(data);
                 return ret;
        }

    }
    public ReturnCode write(final OutputStream data) {
        return this.write(data, -1, 0);
    }
    /**
     * <p> write </p>
     *
     * @return boolean 
     */
    public boolean isActive() {
        return m_active;
    }
    /**
     * <p> activate </p>
     *
     * @return ReturnCode 
     */
    public ReturnCode activate() {
        m_active = true;
        return ReturnCode.PORT_OK;
    }
    /**
     * <p> deactivate </p>
     *
     * @return ReturnCode 
     */
    public ReturnCode deactivate() {
        m_active = false;
        return ReturnCode.PORT_OK;
    }
    /**
     * <p> creator_ </p>
     * 
     * @return Object Created instances
     *
     */
    public PublisherBase creator_() {
        return new PublisherFlush();
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
     * <p> PublisherFlushInit </p>
     *
     */
    public static void PublisherFlushInit() {
        final PublisherBaseFactory<PublisherBase,String> factory 
            = PublisherBaseFactory.instance();

        factory.addFactory(id_name,
                    new PublisherFlush(),
                    new PublisherFlush());
    
    }
    /**
     * <p> getName </p>
     *
     */
    public String getName() {
        return id_name;
    }
    private static final String id_name = "flush";

    protected Logbuf rtcout;
    private InPortConsumer m_consumer;
    private boolean m_active;

    /**
     * <p> Connector data listener functions </p>
     */
//    protected void onBufferWrite(final OutputStream data) {
//        m_listeners.connectorData_[ConnectorDataListenerType.ON_BUFFER_WRITE].notify(m_profile, data);
//    }

//    protected void onBufferFull(final OutputStream data) {
//        m_listeners.connectorData_[ConnectorDataListenerType.ON_BUFFER_FULL].notify(m_profile, data);
//    }

//    protected void onBufferWriteTimeout(final OutputStream data) {
//        m_listeners.connectorData_[ConnectorDataListenerType.ON_BUFFER_WRITE_TIMEOUT].notify(m_profile, data);
//    }

//    protected void onBufferWriteOverwrite(final OutputStream data) {
//        m_listeners.connectorData_[ConnectorDataListenerType.ON_BUFFER_OVERWRITE].notify(m_profile, data);
//    }

//    protected void onBufferRead(final OutputStream data) {
//        m_listeners.connectorData_[ConnectorDataListenerType.ON_BUFFER_READ].notify(m_profile, data);
//    }

    protected void onSend(final OutputStream data) {
        m_listeners.connectorData_[ConnectorDataListenerType.ON_SEND].notify(m_profile, data);
    }

    protected void onReceived(final OutputStream data) {
        m_listeners.connectorData_[ConnectorDataListenerType.ON_RECEIVED].notify(m_profile, data);
    }

    protected void onReceiverFull(final OutputStream data) {
        m_listeners.connectorData_[ConnectorDataListenerType.ON_RECEIVER_FULL].notify(m_profile, data);
    }

    protected void onReceiverTimeout(final OutputStream data) {
        m_listeners.connectorData_[ConnectorDataListenerType.ON_RECEIVER_TIMEOUT].notify(m_profile, data);
    }

    protected void onReceiverError(final OutputStream data) {
        m_listeners.connectorData_[ConnectorDataListenerType.ON_RECEIVER_ERROR].notify(m_profile, data);
    }

    /**
     * <p> Connector listener functions </p>
     */
//    protected void onBufferEmpty() {
//        m_listeners.connector_[ConnectorDataListenerType.ON_BUFFER_EMPTY].notify(m_profile);
//    }

//    protected void onBufferReadTimeout() {
//        m_listeners.connector_[ConnectorDataListenerType.ON_BUFFER_READ_TIMEOUT].notify(m_profile);
//    }

//    protected void onSenderEmpty() {
//        m_listeners.connector_[ConnectorDataListenerType.ON_SENDER_EMPTY].notify(m_profile);
//    }
    
//    protected void onSenderTimeout() {
//        m_listeners.connector_[ConnectorDataListenerType.ON_SENDER_TIMEOUT].notify(m_profile);
//    }

//    protected void onSenderError() {
//        m_listeners.connector_[ConnectorDataListenerType.ON_SENDER_ERROR].notify(m_profile);
//    }
    
//zxc    private ConnectorListeners m_listeners = new  ConnectorListeners();
    private ConnectorListeners m_listeners;
    private ConnectorBase.ConnectorInfo m_profile;
    private ReturnCode m_retcode;
}
