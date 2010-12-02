package jp.go.aist.rtm.RTC.port;

import java.util.Vector;

import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.util.Properties;

import org.omg.CORBA.portable.OutputStream;

/**
 * {@.ja Connector 基底クラス}
 * {@.en Connector Base class}
 * <p>
 * {@.ja InPort/OutPort, Push/Pull 各種 Connector を派生させるための
 * 基底クラス。}
 * {@.en The base class to derive subclasses for InPort/OutPort,
 * Push/Pull Connectors}
 *
 */

public abstract class ConnectorBase {
    /**
     * <p> ConnectorInfoHoldedr </p>
     *
     */
    public static class ConnectorInfoHolder {
        public ConnectorBase.ConnectorInfo value = null;
        public ConnectorInfoHolder() {
        }
        public ConnectorInfoHolder(ConnectorBase.ConnectorInfo initialValue) {
            value = initialValue;
        }
    };
    /**
     * <p> ConnectorInfo </p>
     * <p> local representation of Connector profile </p>
     *
     * <p> ConnectorProfile struct for ConnectorBase and its subclasses. </p>
     */
    public static class ConnectorInfo {
        public ConnectorInfo(final String name_, final String id_,
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
     * {@.ja Profile 取得}
     * {@.en Getting Profile}
     * <p>
     * {@.ja Connector Profile を取得する}
     * {@.en This operation returns Connector Profile}
     */
    public abstract ConnectorInfo profile();

    /**
     * {@.ja Connector ID 取得}
     * {@.en Getting Connector ID}
     * <p>
     * {@.ja Connector ID を取得する}
     * {@.en This operation returns Connector ID}
     *
     */
    public abstract String id();

    /**
     * {@.ja Connector 名取得}
     * {@.en Getting Connector name}
     * <p>
     * {@.ja Connector 名を取得する}
     * {@.en This operation returns Connector name}
     */
    public abstract String name();

    /**
     * {@.ja 接続解除関数}
     * {@.en Disconnect connection}
     * <p>
     * {@.ja Connector が保持している接続を解除する}
     * {@.en This operation disconnect this connection}
     */
    public abstract ReturnCode disconnect();

    /**
     * {@.ja Buffer を取得する}
     * {@.en Getting Buffer}
     * <p>
     * {@.ja Connector が保持している Buffer を返す}
     * {@.en This operation returns this connector's buffer}
     */
    public abstract BufferBase<OutputStream> getBuffer();

    public abstract void activate();
    public abstract void deactivate();

    // non-copyable class
    //    ConnectorBase(const ConnectorBase& x);
    //    ConnectorBase& operator=(const ConnectorBase& x);
}

