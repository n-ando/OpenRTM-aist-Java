package jp.go.aist.rtm.RTC.port;

import jp.go.aist.rtm.RTC.BufferFactory;
import jp.go.aist.rtm.RTC.OutPortProviderFactory;
import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.log.Logbuf;
import jp.go.aist.rtm.RTC.util.ORBUtil;

import org.omg.CORBA.portable.OutputStream;

import com.sun.corba.se.impl.encoding.EncapsOutputStream; 

  /**
   * {@.ja OutPortPullConnector クラス}
   * {@.en OutPortPullConnector class}
   * <p>
   * {@.ja OutPort の pull 型データフローのための Connector クラス。このオブ
   * ジェクトは、接続時に dataflow_type に pull が指定された場合、
   * OutPort によって生成・所有され、InPortPullConnector と対になって、
   * データポートの pull 型のデータフローを実現する。一つの接続に対して、
   * 一つのデータストリームを提供する唯一の Connector が対応する。
   * Connector は 接続時に生成される UUID 形式の ID により区別される。
   *
   * OutPortPullConnector は以下の三つのオブジェクトを所有し管理する。
   * <ul>
   * <li>- InPortConsumer
   * <li>- Buffer
   * </ul>
   * OutPort に書き込まれたデータは OutPortPullConnector::write() に渡
   * され Buffer に書き込まれる。InPortPullConnector が
   * OutPortPullConnector からデータを読み出すことで InPort にデータが
   * 転送される。}
   * {@.en Connector class of OutPort for pull type dataflow. When "pull" is
   * specified as dataflow_type at the time of establishing
   * connection, this object is generated and owned by the OutPort.
   * This connector and InPortPullConnector make a pair and realize
   * pull type dataflow of data ports. One connector corresponds to
   * one connection which provides a data stream. Connector is
   * distinguished by ID of the UUID that is generated at establishing
   * connection.
   *
   * OutPortPullConnector owns and manages the following objects.
   * <ul>
   * <li>- InPortConsumer
   * <li>- Buffer
   * </ul>
   * Data written into the OutPort is passed to
   * OutPortPullConnector::write(), and it is written into the buffer.
   * By reading data from OutPortPullConnector to InPortPullConnector,
   * data transfer is realized.}
   */
public class OutPortPullConnector extends OutPortConnector {
    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     * <p>
     * {@.ja OutPortPullConnector のコンストラクタはオブジェクト生成時に下記
     * を引数にとる。ConnectorInfo は接続情報を含み、この情報に従いバッ
     * ファ等を生成する。OutPort インターフェースのプロバイダオブジェク
     * トへのポインタを取り、所有権を持つので、OutPortPullConnector は
     * OutPortProvider の解体責任を持つ。各種イベントに対するコールバッ
     * ク機構を提供する ConnectorListeners を持ち、適切なタイミングでコー
     * ルバックを呼び出す。データバッファがもし OutPortBase から提供さ
     * れる場合はそのポインタを取る。}
     * {@.en OutPortPullConnector's constructor is given the following
     * arguments.  According to ConnectorInfo which includes
     * connection information, a buffer is created.  It is also given
     * a pointer to the provider object for the OutPort interface.
     * The owner-ship of the pointer is owned by this
     * OutPortPullConnector, it has responsibility to destruct the
     * OutPortProvider.  OutPortPullConnector also has
     * ConnectorListeners to provide event callback mechanisms, and
     * they would be called at the proper timing.  If data buffer is
     * given by OutPortBase, the pointer to the buffer is also given
     * as arguments.}
     *
     * @param profile 
     *   {@.ja ConnectorInfo}
     *   {@.en ConnectorInfo}
     * @param provider 
     *   {@.ja OutPortProvider}
     *   {@.en OutPortProvider}
     * @param listeners 
     *   {@.ja ConnectorListeners 型のリスナオブジェクトリスト}
     *   {@.en ConnectorListeners type lsitener object list}
     * @param buffer 
     *   {@.ja CdrBufferBase 型のバッファ}
     *   {@.en CdrBufferBase type buffer}
     */
    public OutPortPullConnector(ConnectorInfo profile,
                         OutPortProvider provider,
                         ConnectorListeners listeners,
                         BufferBase<OutputStream> buffer)  throws Exception {
        super(profile);
        _constructor(profile, provider, listeners, buffer);
    }
    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     * <p>
     * {@.ja OutPortPullConnector のコンストラクタはオブジェクト生成時に下記
     * を引数にとる。ConnectorInfo は接続情報を含み、この情報に従いバッ
     * ファ等を生成する。OutPort インターフェースのプロバイダオブジェク
     * トへのポインタを取り、所有権を持つので、OutPortPullConnector は
     * OutPortProvider の解体責任を持つ。各種イベントに対するコールバッ
     * ク機構を提供する ConnectorListeners を持ち、適切なタイミングでコー
     * ルバックを呼び出す。}
     * {@.en OutPortPullConnector's constructor is given the following
     * arguments.  According to ConnectorInfo which includes
     * connection information, a buffer is created.  It is also given
     * a pointer to the provider object for the OutPort interface.
     * The owner-ship of the pointer is owned by this
     * OutPortPullConnector, it has responsibility to destruct the
     * OutPortProvider.  OutPortPullConnector also has
     * ConnectorListeners to provide event callback mechanisms, and
     * they would be called at the proper timing.}
     *
     * @param profile 
     *   {@.ja ConnectorInfo}
     *   {@.en ConnectorInfo}
     * @param provider 
     *   {@.ja OutPortProvider}
     *   {@.en OutPortProvider}
     * @param listeners 
     *   {@.ja ConnectorListeners 型のリスナオブジェクトリスト}
     *   {@.en ConnectorListeners type lsitener object list}
     */
    public OutPortPullConnector(ConnectorInfo profile,
                         OutPortProvider provider, 
                         ConnectorListeners listeners)  throws Exception {
        super(profile);
        BufferBase<OutputStream> buffer = null;
        _constructor(profile, provider, listeners, buffer);

    }

    /**
     *  
     */
    private void _constructor(ConnectorInfo profile,
                         OutPortProvider provider,
                         ConnectorListeners listeners,
                         BufferBase<OutputStream> buffer)  throws Exception {
        m_provider = provider;
        m_buffer = buffer;
        m_listeners = listeners;
        m_spi_orb = (com.sun.corba.se.spi.orb.ORB)ORBUtil.getOrb();
        // create buffer
        if (m_buffer == null) {
            m_buffer = createBuffer(profile);
        }

        if (m_provider == null || m_buffer == null) { 
            throw new Exception("bad_alloc()");
        }

        m_buffer.init(profile.properties.getNode("buffer"));
        m_provider.setBuffer(m_buffer);
        m_provider.setConnector(this);
        //    m_provider.init(m_profile /* , m_listeners */);
        m_provider.setListener(profile, m_listeners);

        onConnect();
    }

    /**
     * {@.ja データの書き込み}
     * {@.en Writing data}
     * <p>
     * {@.ja Publisherに対してデータを書き込み、これにより対応するInPortへ
     * データが転送される。}
     * {@.en This operation writes data into publisher and then the data
     * will be transferred to correspondent InPort.}
     *
     */
    public <DataType> ReturnCode write(final DataType data) {
        rtcout.println(Logbuf.TRACE, "write()");
        OutPort out = (OutPort)m_outport;
        OutputStream cdr 
            = new EncapsOutputStream(m_spi_orb,m_isLittleEndian);
        out.write_stream(data,cdr); 
        m_buffer.write(cdr);
        return ReturnCode.PORT_OK;
    }

    /**
     * {@.ja 接続解除}
     * {@.en disconnect}
     *
     * <p>
     * {@.ja consumer, publisher, buffer が解体・削除される。}
     * {@.en This operation destruct and delete the consumer, the publisher
     * and the buffer.}
     * </p>
     */
    public ReturnCode disconnect() {
        onDisconnect();
        // delete provider 
        if (m_provider != null) {
            OutPortProviderFactory<OutPortProvider,String> cfactory 
                = OutPortProviderFactory.instance();
            cfactory.deleteObject(m_provider);
        }
        m_provider = null;

        // delete buffer
        if (m_buffer != null) {
            BufferFactory<BufferBase<OutputStream>,String> bfactory 
                = BufferFactory.instance();
            bfactory.deleteObject(m_buffer);
        }
        m_buffer = null;
        return ReturnCode.PORT_OK;
    }

    /**
     * {@.ja Buffer を取得する}
     * {@.en Getting Buffer}
     * <p>
     * {@.ja Connector が保持している Buffer を返す}
     * {@.en This operation returns this connector's buffer}
     * @return
     *   {@.ja Buffer を返す}
     *   {@.en This connector's buffer}
     */
    public BufferBase<OutputStream> getBuffer() {
        return m_buffer;
    }

    /**
     * {@.ja Bufferの生成}
     * {@.en create buffer}
     * @param profile
     *   {@.ja バッファ種類を指定}
     *   {@.en specify the buffer kind}
     * @return
     *   {@.ja 生成されたバッファ}
     *   {@.en Buffer created}
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
     * {@.ja 接続確立時にコールバックを呼ぶ}
     * {@.en Invoke callback when connection is established}
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
     * {@.ja アクティブ化}
     * {@.en Connector activation}
     * <p>
     * {@.ja このコネクタをアクティブ化する}
     * {@.en This operation activates this connector}
     */
    public void activate(){
    } // do nothing

    /**
     * {@.ja 非アクティブ化}
     * {@.en Connector deactivation}
     * <p>
     * {@.ja このコネクタを非アクティブ化する}
     * {@.en This operation deactivates this connector}
     */
    public void deactivate(){
    } // do nothing

    /**
     * {@.ja OutPortBaseを格納する。}
     * {@.en Stores OutPortBase.}
     *
     * @param outportbase
     *   {@.ja OutPortBase}
     *   {@.en OutPortBase}
     *
     */
    public void setOutPortBase(OutPortBase outportbase) {
        m_outport = outportbase;
    }
    /**
     * <p> the pointer to the OutPortProvider </p>
     */
    protected OutPortProvider m_provider;

    /**
     * <p> the pointer to the buffer </p>
     */
    protected BufferBase<OutputStream> m_buffer;
    private com.sun.corba.se.spi.orb.ORB m_spi_orb;
    private OutPortBase m_outport;
    /**
     * <p> A reference to a ConnectorListener </p>
     */
    private ConnectorListeners m_listeners;
}
