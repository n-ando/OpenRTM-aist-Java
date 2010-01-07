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
        if (m_buffer == null || m_consumer==null) {
            throw new Exception("bad_alloc()");
        }
        m_consumer.setBuffer(m_buffer);
        m_consumer.setListener(profile, m_listeners);
    }
    /**
     * <p> Destructor </p>
     *
     * <p> The read function to read data from buffer to InPort </p>
     *
     */
    public ReturnCode read(DataRef<InputStream> data){
        if (m_buffer == null) {
            return ReturnCode.PORT_ERROR;
        }
        EncapsOutputStream cdr = new EncapsOutputStream(m_spi_orb, 
                                                    m_isLittleEndian);
        DataRef<OutputStream> dataref = new DataRef<OutputStream>(cdr);
        m_buffer.read(dataref);
        data.v = dataref.v.create_input_stream();

        return ReturnCode.PORT_OK;
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

    public void activate(){}; // do nothing
    public void deactivate(){}; // do nothing

    protected BufferBase<OutputStream> createBuffer(ConnectorInfo profile) {
        String buf_type;
        buf_type = profile.properties.getProperty("buffer_type",
                                              "ring_buffer");
        BufferFactory<BufferBase<OutputStream>,String> factory 
                = BufferFactory.instance();
        return factory.createObject(buf_type);
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
