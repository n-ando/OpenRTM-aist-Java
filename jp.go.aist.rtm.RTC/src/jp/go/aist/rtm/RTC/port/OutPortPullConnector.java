package jp.go.aist.rtm.RTC.port;

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

import com.sun.corba.se.impl.encoding.EncapsOutputStream; 

import jp.go.aist.rtm.RTC.BufferFactory;
import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.util.ORBUtil;

public class OutPortPullConnector extends OutPortConnector {
    /**
     * <p> Constructor </p>
     *
     * <p> OutPortPullConnector assume ownership of InPortConsumer.
     * <p> Therefore, OutPortProvider will be deleted when OutPortPushConnector
     * <p> is destructed.
     *
     * @param profile pointer to a ConnectorProfile
     * @param provider pointer to an OutPortProvider
     * @param buffer pointer to a buffer
     *
     */
    public OutPortPullConnector(ConnectorInfo profile,
                         OutPortProvider provider,
                         ConnectorListeners listeners,
                         BufferBase<OutputStream> buffer)  throws Exception {
        super(profile);
        _constructor(profile, provider, listeners, buffer);
    }
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
     * <p> Writing data </p>
     * <p> This operation writes data into publisher and then the data </p>
     * <p> will be transferred to correspondent InPort. </p>
     *
     * @param data
     * @return ReturnCode
     *
     */
    public <DataType> ReturnCode write(final DataType data) {
        rtcout.println(rtcout.TRACE, "write()");
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
        return ReturnCode.PORT_OK;
    }

    /**
     * <p> Getting Buffer </p>
     * <p> This operation returns this connector's buffer </p>
     *
     */
    public BufferBase<OutputStream> getBuffer() {
        return m_buffer;
    }

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
     * <p> Connector activation </p>
     * <p> This operation activates this connector </p>
     *
     */
    public void activate(){
    } // do nothing

    /**
     * <p> Connector deactivation </p>
     * <p> This operation deactivates this connector </p>
     *
     */
    public void deactivate(){
    } // do nothing

    /**
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
