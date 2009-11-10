package jp.go.aist.rtm.RTC.port;

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
     * <p> Constructor </p>
     *
     * <p> InPortPushConnector assume ownership of InPortConsumer. </p>
     * <p> Therefore, InPortConsumer will be deleted when InPortPushConnector </p>
     * <p> is destructed. </p>
     *
     * @param profile ConnectorProfile
     * @param provider InPortProvider
     * @param buffer
     *
     */
    public InPortPushConnector(Profile profile, InPortProvider provider,
                        BufferBase<OutputStream> buffer) throws Exception {
        super(profile, buffer);
        m_provider = provider;
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

        m_provider.init(profile.properties);
        m_provider.setBuffer(m_buffer);
    }
    /**
     *
     * <p> Reading data </p>
     *
     * <p> Read data from the buffer. </p>
     *
     * @param data
     * @return ReturnCode
     */
    public ReturnCode read(DataRef<OutputStream> data) {
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
        
        jp.go.aist.rtm.RTC.buffer.ReturnCode ret = m_buffer.read(data, 0, 0);

        return convertReturn(ret);
    }
    /**
     *
     * <p> disconnect </p>
     *
     * <p> This operation destruct and delete the consumer, the publisher
     * and the buffer. </p>
     *
     */
    public ReturnCode disconnect() {
        // delete consumer
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

    public  void activate(){}; // do nothing
    public void deactivate(){}; // do nothing
    /*!
     * @brief create buffer
     */
    protected BufferBase<OutputStream> createBuffer(Profile profile) {
        String buf_type;
        buf_type = profile.properties.getProperty("buffer_type",
                                              "ring_buffer");
        BufferFactory<BufferBase<OutputStream>,String> factory 
                = BufferFactory.instance();
        return factory.createObject(buf_type);
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
    /*!
     * @brief the pointer to the InPortConsumer
     */
    private InPortProvider m_provider;

    private boolean m_deleteBuffer;


}
