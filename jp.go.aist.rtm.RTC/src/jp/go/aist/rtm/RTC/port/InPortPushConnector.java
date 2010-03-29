package jp.go.aist.rtm.RTC.port;

import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
import com.sun.corba.se.impl.encoding.EncapsInputStream; 
import com.sun.corba.se.impl.encoding.EncapsOutputStream; 

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

import jp.go.aist.rtm.RTC.InPortProviderFactory;
import jp.go.aist.rtm.RTC.BufferFactory;
import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.buffer.RingBuffer;
import jp.go.aist.rtm.RTC.port.ReturnCode;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.log.Logbuf;

public class InPortPushConnector extends InPortConnector {
    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     * <p>
     * {@.ja InPortPushConnector のコンストラクタはオブジェクト生成時に下記を
     * 引数にとる。ConnectorInfo は接続情報を含み、この情報に従いバッファ
     * 等を生成する。InPort インターフェースのプロバイダオブジェクトへ
     * のポインタを取り、所有権を持つので、InPortPushConnector は
     * InPortProvider の解体責任を持つ。各種イベントに対するコールバッ
     * ク機構を提供する ConnectorListeners を持ち、適切なタイミングでコー
     * ルバックを呼び出す。データバッファがもし InPortBase から提供され
     * る場合はそのポインタを取る。}
     * {@.en InPortPushConnector's constructor is given the following
     * arguments.  According to ConnectorInfo which includes
     * connection information, a buffer is created.
     * It is also given a pointer to the provider object for the
     * InPort interface.  The owner-ship of the pointer is owned by
     * this InPortPushConnector, it has responsibility to destruct
     * the InPortProvider.  InPortPushConnector also has
     * ConnectorListeners to provide event callback mechanisms, and
     * they would be called at the proper timing.  If data buffer is
     * given by InPortBase, the pointer to the buffer is also given
     * as arguments.}
     * </p>
     *
     * @param profile 
     *   {@.ja ConnectorInfo}
     *   {@.en ConnectorInfo}
     * @param provider 
     *   {@.ja InPortProvider}
     *   {@.en InPortProvider}
     * @param listeners 
     *   {@.ja ConnectorListeners 型のリスナオブジェクトリスト}
     *   {@.en ConnectorListeners type lsitener object list}
     * @param buffer 
     *   {@.ja CdrBufferBase 型のバッファ}
     *   {@.en CdrBufferBase type buffer}
     */
    public InPortPushConnector(ConnectorInfo profile, InPortProvider provider,
                        ConnectorListeners listeners,
                        BufferBase<OutputStream> buffer) throws Exception {
        super(profile, buffer);
        m_provider = provider;
        m_listeners = listeners; 
        if (buffer == null ) {
            m_deleteBuffer = true;
        }
        else {
            m_deleteBuffer = false;
        }

        // publisher/buffer creation. This may throw std::bad_alloc;
        if (m_buffer == null) {
            m_buffer = createBuffer(profile);
        }
        if (m_buffer == null || m_provider==null) {
            if(m_buffer == null){
                rtcout.println(rtcout.PARANOID, "    m_buffer is null.");
            }
            if(m_provider == null){
                rtcout.println(rtcout.PARANOID, "    m_provider is null.");
            }

            throw new Exception("bad_alloc()");
        }

        m_buffer.init(profile.properties.getNode("buffer"));
        m_provider.init(profile.properties);
        m_provider.setBuffer(m_buffer);
        m_provider.setListener(profile, m_listeners);

        onConnect();
    }
    public void setListener(ConnectorInfo profile, 
                            ConnectorListeners listeners){
        m_provider.setListener(profile, m_listeners);
    }

    /**
     * {@.ja データの読み出し}
     * {@.en Reading data}
     *
     * <p>
     * {@.ja バッファからデータを読み出す。正常に読み出せた場合、戻り値は
     * PORT_OK となり、data に読み出されたデータが格納される。それ以外
     * の場合には、エラー値として BUFFER_EMPTY, TIMEOUT,
     * PRECONDITION_NOT_MET, PORT_ERROR が返される。}
     * {@.en This function reads data from the buffer. If data is read
     * properly, this function will return PORT_OK return code. Except
     * normal return, BUFFER_EMPTY, TIMEOUT, PRECONDITION_NOT_MET and
     * PORT_ERROR will be returned as error codes.}
     * </p>
     *
     * @return 
     *   {@.ja PORT_OK              正常終了
     *         BUFFER_EMPTY         バッファは空である
     *         TIMEOUT              タイムアウトした
     *         PRECONDITION_NOT_MET 事前条件を満たさない
     *         PORT_ERROR           その他のエラー}
     *
     *   {@.en PORT_OK              Normal return
     *         BUFFER_EMPTY         Buffer empty
     *         TIMEOUT              Timeout
     *         PRECONDITION_NOT_MET Preconditin not met
     *         PORT_ERROR           Other error}
     *
     */
    public ReturnCode read(DataRef<InputStream> data) {
        rtcout.println(rtcout.TRACE, "read()");
        /*
         * buffer returns
         *   BUFFER_OK
         *   BUFFER_EMPTY
         *   TIMEOUT
         *   PRECONDITION_NOT_MET
         */
        if (m_buffer == null) {
            return ReturnCode.PRECONDITION_NOT_MET;
        }
        
        org.omg.CORBA.Any any = m_orb.create_any(); 
        OutputStream cdr = any.create_output_stream();
        DataRef<OutputStream> dataref = new DataRef<OutputStream>(cdr);
        jp.go.aist.rtm.RTC.buffer.ReturnCode ret 
                                        = m_buffer.read(dataref, -1, -1);
        data.v = dataref.v.create_input_stream();
        return convertReturn(ret);
    }

    /**
     * {@.ja 接続解除}
     * {@.en disconnect}
     *
     * <p>
     * {@.ja consumer, publisher, buffer が解体・削除される。}
     * {@.en This operation destruct and delete the consumer, the publisher
     * and the buffer.}
     */
    public ReturnCode disconnect() {
        rtcout.println(rtcout.TRACE, "disconnect()");
        onDisconnect();
        // delete provider 
        if (m_provider != null) {
            InPortProviderFactory<InPortProvider,String> cfactory 
                = InPortProviderFactory.instance();
            cfactory.deleteObject(m_provider);
        }
        m_provider = null;

        // delete buffer
        if (m_buffer != null && m_deleteBuffer == true) {
            BufferFactory<BufferBase<OutputStream>,String> bfactory 
                = BufferFactory.instance();
            bfactory.deleteObject(m_buffer);
        }
        m_buffer = null;
        return ReturnCode.PORT_OK;
    }

    /**
     * <p> Connector activation </p>
     * <p> This operation activates this connector </p>
     */
    public  void activate(){}; // do nothing

    /**
     * <p> Connector activation </p>
     * <p> This operation activates this connector </p>
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

    /**
     * <p> convertReturn </p>
     *
     */
    protected ReturnCode convertReturn(jp.go.aist.rtm.RTC.buffer.ReturnCode status) {
        switch (status) {
            case BUFFER_OK:
                return ReturnCode.PORT_OK;
            case BUFFER_EMPTY:
                return ReturnCode.BUFFER_EMPTY;
            case TIMEOUT:
                return ReturnCode.BUFFER_TIMEOUT;
            case PRECONDITION_NOT_MET:
                return ReturnCode.PRECONDITION_NOT_MET;
            default:
                return ReturnCode.PORT_ERROR;
        }
    }
    /**
     * <p> the pointer to the InPortConsumer </p>
     */
    private InPortProvider m_provider;

    private boolean m_deleteBuffer;

    /**
     * <p> A reference to a ConnectorListener </p>
     */
    private ConnectorListeners m_listeners;

}
