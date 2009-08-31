package jp.go.aist.rtm.RTC.port;

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import java.util.Vector;

import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.port.ReturnCode;
import jp.go.aist.rtm.RTC.util.Properties;

/**
 * <p> ConnectorBase </p>
 * <p> Connector Base class </p>
 * <p> The base class to derive subclasses for InPort/OutPort, </p>
 * <p> Push/Pull Connectors </p>
 *
 */

public abstract class ConnectorBase {
    /**
     * <p> Profile </p>
     * <p> local representation of Connector profile </p>
     *
     * <p> ConnectorProfile struct for ConnectorBase and its subclasses. </p>
     */
    class Profile {
        public Profile(final String name_, final String id_,
              Vector<String> ports_, Properties properties_) {
            name = name_;
            id = id_;
            ports = ports_;
            properties = properties_;
        }
        public String name;
        public String id;
        public Vector<String> ports;
        public Properties properties;
    };

//    typedef std::vector<Profile> ProfileList;
//    typedef std::vector<ConnectorBase*> ConnectorList;

    /**
     * <p> Getting Profile </p>
     * <p> This operation returns Connector Profile </p>
     */
    public abstract Profile profile();

    /**
     * <p> Getting Connector ID </p>
     * <p> This operation returns Connector ID </p>
     *
     */
    public abstract String id();

    /**
     * <p> Getting Connector name </p>
     * <p> This operation returns Connector name </p>
     */
    public abstract String name();

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

    public abstract void activate();
    public abstract void deactivate();

    // non-copyable class
    //    ConnectorBase(const ConnectorBase& x);
    //    ConnectorBase& operator=(const ConnectorBase& x);
}

