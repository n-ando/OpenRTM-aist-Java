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
     * <p>コンストラクタです。</p>
     * 
     */
    public PublisherFlush() {
        rtcout = new Logbuf("PublisherFlush");
        m_consumer = null;
        m_active = false;
    }
    /**
     * <p>コンストラクタです。</p>
     * 
     * @param consumer データ送出を待つコンシューマ
     * @param property 当該Publisherの駆動を制御する情報を持つPropertyオブジェクト
     */
    public PublisherFlush(InPortConsumer consumer, final Properties property) {
        rtcout = new Logbuf("PublisherFlush");
        m_consumer = consumer;
    }
    
    /**
     * <p>当該Publisherが不要となり破棄される際に、PublisherFactoryにより呼び出されます。</p>
     */
    public void destruct() {
        m_consumer = null;
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
     * <p>送出タイミング時に呼び出します。即座に同一スレッドにてコンシューマの送出処理が呼び出されます。</p>
     */
/*
    public void update() {
        m_consumer.push();
    }
*/

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
     * <p> write </p>
     *
     * @param data
     * @param sec
     * @param usec
     * @return ReturnCode
     */
    public ReturnCode write(final OutputStream data, int sec, int usec) {
        if (m_consumer == null ) { 
            return ReturnCode.PRECONDITION_NOT_MET; 
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
    
    private ConnectorListeners m_listeners = new  ConnectorListeners();
    private ConnectorBase.ConnectorInfo m_profile;
}
