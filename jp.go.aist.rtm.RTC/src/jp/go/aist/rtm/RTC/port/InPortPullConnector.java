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
     * <p> Constructor </p>
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

/* zxc
        EncapsOutputStream cdr = new EncapsOutputStream(m_spi_orb, 
                                                    m_isLittleEndian);
        DataRef<OutputStream> dataref = new DataRef<OutputStream>(cdr);
        m_buffer.read(dataref);
        data.v = dataref.v.create_input_stream();

        return ReturnCode.PORT_OK;
*/
    }
    /**
     * <p> Disconnect connection </p>
     *
     * <p> This operation disconnect this connection </p>
     *
     */
    public ReturnCode disconnect() {
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
     * <p> Invoke callback when connection is destroied </p>
     */
    protected void onDisconnect() {
        m_listeners.connector_[ConnectorListenerType.ON_DISCONNECT].notify(m_profile);
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
