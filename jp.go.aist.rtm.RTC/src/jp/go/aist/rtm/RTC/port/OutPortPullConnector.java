package jp.go.aist.rtm.RTC.port;

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

import jp.go.aist.rtm.RTC.buffer.BufferBase;

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
    public OutPortPullConnector(Profile profile,
                         OutPortProvider provider,
                         BufferBase<OutputStream> buffer) {
        super(profile);
        m_provider = provider;
        m_buffer = buffer;
    }
    public OutPortPullConnector(Profile profile,
                         OutPortProvider provider ) {
        super(profile);
        BufferBase<OutputStream> buffer = null;
        m_provider = provider;
        m_buffer = buffer;
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
    public ReturnCode write(final OutputStream data) {
        m_buffer.write(data);
        return ReturnCode.PORT_OK;
    }

    /**
     * <p> disconnect </p>
     * <p> This operation destruct and delete the consumer, the publisher </p>
     * <p> and the buffer. </p>
     *
     * @return ReturnCode
     *
     */
    public ReturnCode disconnect() {
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
     * <p> the pointer to the OutPortProvider </p>
     */
    protected OutPortProvider m_provider;

    /**
     * <p> the pointer to the buffer </p>
     */
    protected BufferBase<OutputStream> m_buffer;
}
