package jp.go.aist.rtm.RTC.port;

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.port.ReturnCode;
import jp.go.aist.rtm.RTC.log.Logbuf;

/**
 * <p> InPortConnector </p>
 * <p> InPortConnector base class </p>
 *
 * <p> The base class to derive subclasses for InPort's Push/Pull Connectors </p>
 */
public abstract class InPortConnector extends ConnectorBase {
    /**
     * <p> Constructor </p>
     */
    public InPortConnector(ConnectorBase.Profile profile,
                    BufferBase<OutputStream> buffer) {
        rtcout = new Logbuf("InPortConnector");
        m_profile = profile;
        m_buffer = buffer;
    }


    /**
     * <p> Getting Profile </p>
     *
     * <p> This operation returns Connector Profile </p>
     *
     */
    public final Profile profile() {
        rtcout.println(rtcout.TRACE, "profile()");
        return m_profile;
    }
    /**
     * <p> Getting Connector ID </p>
     *
     * <p> This operation returns Connector ID </p>
     *
     */
    public final String id() {
        rtcout.println(rtcout.TRACE, "id() = "+profile().id);
        return profile().id;
    }

    /**
     * <p> Getting Connector name </p>
     *
     * <p> This operation returns Connector name </p>
     *
     */
    public final String name() {
        rtcout.println(rtcout.TRACE, "name() = "+profile().name);
        return profile().name;
    }

    /**
     * <p> Disconnect connection </p>
     *
     * <p> This operation disconnect this connection </p>
     *
     */
    public abstract ReturnCode disconnect();

    /**
     *
     * <p> Getting Buffer </p>
     *
     * <p> This operation returns this connector's buffer </p>
     *
     */
    public BufferBase<OutputStream> getBuffer() {
        return m_buffer;
    }

    /**
     *
     * <p> Destructor </p>
     *
     * <p> The read function to read data from buffer to InPort </p>
     *
     */
    public abstract ReturnCode read(OutputStream data);

    protected Logbuf rtcout;
    protected Profile m_profile;
    protected BufferBase<OutputStream> m_buffer;

}


