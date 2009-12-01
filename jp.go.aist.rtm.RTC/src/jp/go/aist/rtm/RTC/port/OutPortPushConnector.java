package jp.go.aist.rtm.RTC.port;

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

import java.io.IOException;

import jp.go.aist.rtm.RTC.PublisherBaseFactory;
import jp.go.aist.rtm.RTC.InPortConsumerFactory;
import jp.go.aist.rtm.RTC.BufferFactory;
import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.buffer.RingBuffer;
import jp.go.aist.rtm.RTC.log.Logbuf;
import jp.go.aist.rtm.RTC.port.publisher.PublisherBase;
import jp.go.aist.rtm.RTC.port.publisher.PublisherFactory;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.StringUtil;

public class OutPortPushConnector extends OutPortConnector {
    /**
     * <p> Constructor </p>
     *
     * <p> OutPortPushConnector assume ownership of InPortConsumer. 
     * Therefore, InPortConsumer will be deleted when OutPortPushConnector
     * is destructed. </p>
     * @param profile ConnectorProfile
     * @param consumer InPortConsumer
     *
     */
    public OutPortPushConnector(Profile profile,
                         InPortConsumer consumer,
                         BufferBase<OutputStream> buffer) throws Exception {
        super(profile);
        try {
            _Constructor(profile,consumer,buffer);
        }
        catch(Exception e) {
            throw new Exception("bad_alloc()");
        } 
    }

    public OutPortPushConnector(Profile profile,
                         InPortConsumer consumer )  throws Exception {
        super(profile);
        BufferBase<OutputStream> buffer = null;
        try {
            _Constructor(profile,consumer,buffer);
        }
        catch(Exception e) {
            throw new Exception("bad_alloc()");
        } 
    }

    private void _Constructor(Profile profile,
                         InPortConsumer consumer,
                         BufferBase<OutputStream> buffer) throws Exception {
        m_consumer = consumer;
        m_publisher = null;
        m_buffer = buffer;

        // publisher/buffer creation. This may throw std::bad_alloc;
        m_publisher = createPublisher(profile);
        if (m_buffer == null) {
            m_buffer = createBuffer(profile);
        }
        if (m_publisher == null || m_buffer == null || m_consumer == null) { 
            if (m_publisher == null) { 
                rtcout.println(rtcout.PARANOID, "m_publisher is null");
            }
            if (m_buffer == null) { 
                rtcout.println(rtcout.PARANOID, "m_buffer is null");
            }
            if (m_consumer == null) { 
                rtcout.println(rtcout.PARANOID, "m_consumer is null");
            }
            throw new Exception("bad_alloc()");
        }

        ReturnCode ret = m_publisher.init(profile.properties);
        if (!ret.equals(ReturnCode.PORT_OK)) {
            throw new Exception("bad_alloc()");
        }
        m_consumer.init(profile.properties);

        m_publisher.setConsumer(m_consumer);
        m_publisher.setBuffer(m_buffer);
    }
    /**
     * <p> Writing data </p>
     *
     * <p> This operation writes data into publisher and then the data </p>
     * <p> will be transferred to correspondent InPort. </p>
     *
     * @param data_little
     * @param data_big
     * @return ReturnCode
     *
     */
//    public ReturnCode write(final OutputStream data) {
    public ReturnCode write(final OutputStream data_little,final OutputStream data_big) {
        rtcout.println(rtcout.TRACE, "write()");
        InputStream in = data_little.create_input_stream();
        try {
            rtcout.println(rtcout.PARANOID, "data size = "+ in.available() +"byte");
        }
        catch(IOException  e ){
            rtcout.println(rtcout.PARANOID, "an I/O error occurs.");
        }
        if(m_endian.equals("little")){
            return m_publisher.write(data_little,0,0);
        }
        else{
            return m_publisher.write(data_big,0,0);
        }
//        return m_publisher.write(data, 0, 0);
    }

    /**
     * <p> disconnect </p>
     *
     * <p> This operation destruct and delete the consumer, the publisher </p>
     * <p> and the buffer. </p>
     *
     */
    public ReturnCode disconnect() {
        rtcout.println(rtcout.TRACE, "disconnect()");
        // delete publisher
        if (m_publisher != null) {
            rtcout.println(rtcout.DEBUG, "delete publisher");
            PublisherBaseFactory<PublisherBase,String> pfactory 
                = PublisherBaseFactory.instance();
            pfactory.deleteObject(m_publisher.getName(),m_publisher);
        }
        m_publisher = null;
    
        // delete consumer
        if (m_consumer != null) {
            rtcout.println(rtcout.DEBUG, "delete consumer");
            InPortConsumerFactory<InPortConsumer,String> cfactory 
                = InPortConsumerFactory.instance();
            cfactory.deleteObject(m_consumer);
        }
        m_consumer = null;

        // delete buffer
        if (m_buffer != null) {
            rtcout.println(rtcout.DEBUG, "delete buffer");
            BufferFactory<BufferBase<OutputStream>,String> bfactory 
                = BufferFactory.instance();
            bfactory.deleteObject(m_buffer);
        }
        m_buffer = null;
        rtcout.println(rtcout.TRACE, "disconnect() done");
        return ReturnCode.PORT_OK;
    
    }
    /**
     *
     * <p> Connector activation </p>
     *
     * <p> This operation activates this connector </p>
     *
     */
    public void activate() {
        m_publisher.activate();
    }
    /**
     * <p> Getting Buffer </p>
     *
     * <p> This operation returns this connector's buffer </p>
     *
     */
    public BufferBase<OutputStream> getBuffer() {
        return m_buffer;
    }

    /**
     * <p> Connector deactivation </p>
     *
     * <p> This operation deactivates this connector </p>
     *
     */
     public void deactivate() {
         m_publisher.deactivate();
     }
    
    /**
     * <p> create publisher </p>
     */
    protected PublisherBase createPublisher(Profile profile) {
        String pub_type;
        pub_type = profile.properties.getProperty("subscription_type",
                                              "flush");
        pub_type = StringUtil.normalize(pub_type);
        PublisherBaseFactory<PublisherBase,String> factory  
                = PublisherBaseFactory.instance();
        return factory.createObject(pub_type);
    }

    /**
     * <p> create buffer </p>
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
     * <p> InPortConsumer </p>
     */
    private InPortConsumer m_consumer;

    /**
     * <p> publisher </p>
     */
    private PublisherBase m_publisher;

    /**
     * <p> the buffer </p>
     */
    private BufferBase<OutputStream> m_buffer;

}

