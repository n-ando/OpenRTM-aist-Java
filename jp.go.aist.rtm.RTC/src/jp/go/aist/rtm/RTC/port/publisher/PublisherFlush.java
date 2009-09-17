package jp.go.aist.rtm.RTC.port.publisher;

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.port.ReturnCode;
import jp.go.aist.rtm.RTC.port.InPortConsumer;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.FactoryGlobal;
import jp.go.aist.rtm.RTC.ObjectCreator;
import jp.go.aist.rtm.RTC.ObjectDestructor;

/**
 * <p>データ送出を待つコンシューマを、送出する側と同じスレッドで動作させる場合に使用します。</p>
 */
public class PublisherFlush extends PublisherBase implements ObjectCreator<PublisherBase>, ObjectDestructor{

    /**
     * <p>コンストラクタです。</p>
     * 
     */
    public PublisherFlush() {
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
     * <p> write </p>
     *
     * @param data
     * @param sec
     * @param usec
     * @return ReturnCode
     */
    public ReturnCode write(final OutputStream data, int sec, int usec) {
        if (m_consumer == null ) { return ReturnCode.PRECONDITION_NOT_MET; }

        return m_consumer.put(data);

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
        final FactoryGlobal<PublisherBase,String> factory 
            = FactoryGlobal.instance();

        factory.addFactory("flush",
                    new PublisherFlush(),
                    new PublisherFlush());
    
    }

    private InPortConsumer m_consumer;
    private boolean m_active;

}
