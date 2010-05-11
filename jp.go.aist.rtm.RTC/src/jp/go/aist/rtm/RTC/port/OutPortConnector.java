package jp.go.aist.rtm.RTC.port;

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.log.Logbuf;


/**
 * <p> OutPortConnector </p>
 * <p> Out PortConnector base class </p>
 *
 *
 * <p> The base class to derive subclasses for OutPort's Push/Pull Connectors </p>
 */
public abstract class OutPortConnector extends ConnectorBase {

    /**
     * <p> Constructor </p>
     */
    public OutPortConnector(ConnectorBase.ConnectorInfo profile) {
        rtcout = new Logbuf("OutPortConnector");
        m_profile = profile;
        m_isLittleEndian = true;
    }

    /**
     * <p> Getting Profile </p>
     * <p> This operation returns Connector Profile </p>
     *
     */
    public final ConnectorInfo profile() {
        rtcout.println(rtcout.TRACE, "profile()");
        return m_profile;
    }

    /**
     * <p> Getting Connector ID </p>
     * <p> This operation returns Connector ID </p>
     *
     */
    public final String id() {
        rtcout.println(rtcout.TRACE, "id() = " + profile().id);
        return profile().id;
    }

    /**
     * <p> Getting Connector name </p>
     * <p> This operation returns Connector name </p>
     * 
     */
    public final String name(){
        rtcout.println(rtcout.TRACE, "name() = " + profile().name);
        return profile().name;
    }
    /**
     *
     * <p> Setting an endian type </p>
     *
     * <p> This operation set this connector's endian type </p>
     *
     */
    public void setEndian(boolean isLittleEndian){
        m_isLittleEndian = isLittleEndian;
    }
    /**
     * <p> This value is true if the architecture is little-endian; false if it is big-endian.  </p>
     * 
     */
    public boolean isLittleEndian(){
        return m_isLittleEndian;
    }
    /**
     * <p> Disconnect connection </p>
     * <p> This operation disconnect this connection </p>
     *
     */
    public abstract ReturnCode disconnect();

    /**
     * <p> Getting Buffer </p>
     * <p> This operation returns this connector's buffer </p>
     * 
     */
    public abstract BufferBase<OutputStream> getBuffer();

    /**
     * <p> Destructor </p>
     * <p> The write function to write data from OutPort to Buffer </p>
     *
     */
    public abstract <DataType> ReturnCode write(final DataType data);

    /**
     */
    public abstract void setOutPortBase(OutPortBase outportbase); 

    protected Logbuf rtcout;
    protected ConnectorInfo m_profile;
    protected boolean m_isLittleEndian;
}

