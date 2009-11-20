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
    public OutPortConnector(ConnectorBase.Profile profile) {
        rtcout = new Logbuf("OutPortConnector");
//        rtcout.setLevel("PARANOID");
        m_profile = profile;
    }

    /**
     * <p> Getting Profile </p>
     * <p> This operation returns Connector Profile </p>
     *
     */
    public final Profile profile() {
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
    public abstract ReturnCode write(final OutputStream data);

    protected Logbuf rtcout;
    protected Profile m_profile;
}

