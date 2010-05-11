package jp.go.aist.rtm.RTC.port;

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import com.sun.corba.se.impl.encoding.EncapsOutputStream; 

import jp.go.aist.rtm.RTC.BufferFactory;
import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.buffer.RingBuffer;
import jp.go.aist.rtm.RTC.port.ReturnCode;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.log.Logbuf;
import jp.go.aist.rtm.RTC.OutPortConsumerFactory;

/**
 * <p> InPortPullConnector </p>
 * <p> InPortPullConnector base class </p>
 *
 * <p> A connector class for pull type dataflow of InPort </p>
 *
 * @since 1.0.0
 *
 */
public class InPortPullConnector extends InPortConnector {
    /**
     * {@.ja コンストラクタ.}
     * {@.en Constructor.}
     *
     * <p>
     * {@.ja InPortPullConnector のコンストラクタはオブジェクト生成時に下記
     * を引数にとる。ConnectorInfo は接続情報を含み、この情報に従いバッ
     * ファ等を生成する。OutPort インターフェースのプロバイダオブジェク
     * トへのポインタを取り、所有権を持つので、InPortPullConnector は
     * OutPortConsumer の解体責任を持つ。各種イベントに対するコールバッ
     * ク機構を提供する ConnectorListeners を持ち、適切なタイミングでコー
     * ルバックを呼び出す。データバッファがもし InPortBase から提供さ
     * れる場合はそのポインタを取る。}
     * {@.en InPortPullConnector's constructor is given the following
     * arguments.  According to ConnectorInfo which includes
     * connection information, a buffer is created.  It is also given
     * a pointer to the consumer object for the OutPort interface.
     * The owner-ship of the pointer is owned by this
     * OutPortPullConnector, it has responsibility to destruct the
     * OutPortConsumer.  OutPortPullConnector also has
     * ConnectorListeners to provide event callback mechanisms, and
     * they would be called at the proper timing.  If data buffer is
     * given by OutPortBase, the pointer to the buffer is also given
     * as arguments.}
     * </p>
     *
     * @param profile 
     *   {@.ja ConnectorInfo}
     *   {@.en ConnectorInfo}
     * @param consumer 
     *   {@.ja OutPortConsumer}
     *   {@.en consumer OutPortConsumer}
     * @param listeners 
     *   {@.ja ConnectorListeners 型のリスナオブジェクトリスト}
     *   {@.en ConnectorListeners type lsitener object list}
     * @param buffer 
     *   {@.ja CdrBufferBase 型のバッファ}
     *   {@.en CdrBufferBase type buffer}
     */
    public InPortPullConnector(ConnectorInfo profile,
                        OutPortConsumer consumer,
                        ConnectorListeners listeners,
                        BufferBase<OutputStream> buffer) throws Exception {
        super(profile, buffer);
        m_consumer = consumer;
        m_listeners = listeners; 
        rtcout = new Logbuf("InPortPullConnector");

        if (buffer == null) {
            m_buffer = createBuffer(m_profile);
        }
        if (m_buffer == null || m_consumer == null) {
            throw new Exception("bad_alloc()");
        }
        m_buffer.init(profile.properties.getNode("buffer"));
        m_consumer.setBuffer(m_buffer);
        m_consumer.setListener(profile, m_listeners);

        onConnect();
    }
    /**
     * <p> Destructor </p>
     *
     * <p> The read function to read data from buffer to InPort </p>
     *
     */
    public ReturnCode read(DataRef<InputStream> data){
        rtcout.println(rtcout.TRACE, "InPortPullConnector.read()");
        if (m_consumer == null) {
            return ReturnCode.PORT_ERROR;
        }
        EncapsOutputStream cdr = new EncapsOutputStream(m_spi_orb, 
                                                    m_isLittleEndian);
        ReturnCode ret = m_consumer.get(cdr);
        DataRef<OutputStream> dataref = new DataRef<OutputStream>(cdr);
        data.v = dataref.v.create_input_stream();
        return ret;
    }
    /**
     * {@.ja 接続解除関数}
     * {@.en Disconnect connection}
     *
     * <p>
     * {@.ja Connector が保持している接続を解除する}
     * {@.en This operation disconnect this connection}
     * </p>
     */
    public ReturnCode disconnect() {
        rtcout.println(rtcout.TRACE, "disconnect()");
        onDisconnect();
        // delete consumer
        if (m_consumer != null) {
            rtcout.println(rtcout.DEBUG, "delete consumer");
            OutPortConsumerFactory<OutPortConsumer,String> cfactory 
                = OutPortConsumerFactory.instance();
            cfactory.deleteObject(m_consumer);
        }

        return ReturnCode.PORT_OK;
    }

    /**
     *
     * <p> Connector activation </p>
     * <p> This operation activates this connector </p>
     *
     */
    public void activate(){}; // do nothing

    /**
     * <p> Connector deactivation </p>
     * <p> This operation deactivates this connector </p>
     */
    public void deactivate(){}; // do nothing

    /**
     * <p> create buffer </p>
     */
    protected BufferBase<OutputStream> createBuffer(ConnectorInfo profile) {
        String buf_type;
        buf_type = profile.properties.getProperty("buffer_type",
                                              "ring_buffer");
        BufferFactory<BufferBase<OutputStream>,String> factory 
                = BufferFactory.instance();
        return factory.createObject(buf_type);
    }
    
    /**
     * <p> Invoke callback when connection is established </p>
     */
    protected void onConnect() {
        m_listeners.connector_[ConnectorListenerType.ON_CONNECT].notify(m_profile);
    }

    /**
     * {@.ja 接続切断時にコールバックを呼ぶ}
     * {@.en Invoke callback when connection is destroied}
     */
    protected void onDisconnect() {
        m_listeners.connector_[ConnectorListenerType.ON_DISCONNECT].notify(
                                                                    m_profile);
    }

    public void setListener(ConnectorInfo profile, 
                            ConnectorListeners listeners){
    }
    private OutPortConsumer m_consumer;

    private Logbuf rtcout;
    /**
     * A reference to a ConnectorListener
     */
    private ConnectorListeners m_listeners;
}
