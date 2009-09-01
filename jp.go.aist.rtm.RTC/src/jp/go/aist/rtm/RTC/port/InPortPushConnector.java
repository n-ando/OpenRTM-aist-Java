package jp.go.aist.rtm.RTC.port;

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.buffer.RingBuffer;
import jp.go.aist.rtm.RTC.port.ReturnCode;

public class InPortPushConnector extends InPortConnector {
    /*!
     * @brief Constructor
     *
     * InPortPushConnector assume ownership of InPortConsumer.
     * Therefore, InPortConsumer will be deleted when InPortPushConnector
     * is destructed.
     *
     * @param profile ConnectorProfile
     * @param consumer InPortConsumer
     *
     */
public InPortPushConnector() {
}
    public InPortPushConnector(Profile profile, InPortProvider provider,
                        BufferBase<OutputStream> buffer) {
    }
    public InPortPushConnector(Profile profile, InPortProvider provider) {
        BufferBase<OutputStream> buffer = null;
    }

    /*!
     *
     * @brief Reading data
     *
     * Read data from the buffer.
     *
     */
    public ReturnCode read(OutputStream data) {
        return ReturnCode.PORT_OK;
    }


    /*!
     *
     * @brief disconnect
     *
     * This operation destruct and delete the consumer, the publisher
     * and the buffer.
     *
     */
    public ReturnCode disconnect() {
        return ReturnCode.PORT_OK;
    }

    public  void activate(){}; // do nothing
    public void deactivate(){}; // do nothing
    /*!
     * @brief create buffer
     */
    protected BufferBase<OutputStream> createBuffer(Profile profile) {
        return new RingBuffer<OutputStream>();
    }

    /*!
     * @brief the pointer to the InPortConsumer
     */
    private InPortProvider m_provider;
    private boolean m_deleteBuffer;


}
