package jp.go.aist.rtm.RTC.port;

import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.log.Logbuf;

import org.omg.CORBA.portable.OutputStream;

/**
 * 
 * {@.ja OutPortConnector 基底クラス}
 * {@.en Out PortConnector base class}
 * <p>
 * {@.ja OutPort の Push/Pull 各種 Connector を派生させるための
 * 基底クラス。}
 * {@.en The base class to derive subclasses for OutPort's Push/Pull 
 * Connectors}
 */
public abstract class OutPortConnector extends ConnectorBase {

    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     * @param profile 
     *   {@.ja 接続情報を含む ConnectorInfo オブジェクト}
     *   {@.en ConnectorInfo object which includes connection information}
     */
    public OutPortConnector(ConnectorBase.ConnectorInfo profile) {
        rtcout = new Logbuf("OutPortConnector");
        m_profile = profile;
        m_isLittleEndian = true;
    }

    /**
     * {@.ja Profile 取得}
     * {@.en Getting Profile}
     * <p>
     * {@.ja Connector Profile を取得する}
     * {@.en This operation returns Connector Profile}
     *
     */
    public final ConnectorInfo profile() {
        rtcout.println(Logbuf.TRACE, "profile()");
        return m_profile;
    }

    /**
     * {@.ja Connector ID 取得}
     * {@.en Getting Connector ID}
     * <p>
     * {@.ja Connector ID を取得する}
     * {@.en This operation returns Connector ID}
     */
    public final String id() {
        rtcout.println(Logbuf.TRACE, "id() = " + profile().id);
        return profile().id;
    }

    /**
     * {@.ja Connector 名取得}
     * {@.en Getting Connector name}
     * <p>
     * {@.ja Connector 名を取得する}
     * {@.en This operation returns Connector name}
     */
    public final String name(){
        rtcout.println(Logbuf.TRACE, "name() = " + profile().name);
        return profile().name;
    }
    /**
     * {@.ja endianタイプ設定}
     * {@.en Setting an endian type}
     * <p>
     * {@.ja endianタイプを設定する}
     * {@.en This operation set this connector's endian type}
     */
    public void setEndian(boolean isLittleEndian){
        m_isLittleEndian = isLittleEndian;
    }
    /**
     * {@.ja endian 設定を返す}
     * {@.en Returns endian information}
     * <p>
     * {@.ja endian 設定のbool値を返す。}
     * {@.en This value is true if the architecture is little-endian; 
     *   false if it is big-endian.}
     *
     * @return 
     *   {@.ja m_littleEndian がlittleの場合true、bigの場合false を返す。}
     *   {@.en Returns the endian setting.}
     */
    public boolean isLittleEndian(){
        return m_isLittleEndian;
    }
    /**
     * {@.ja 接続解除関数}
     * {@.en Disconnect connection}
     * <p>
     * {@.ja Connector が保持している接続を解除する}
     * {@.en This operation disconnect this connection}
     *
     * @return 
     *   {@.ja ReturnCode}
     *   {@.en ReturnCode}
     */
    public abstract ReturnCode disconnect();

    /**
     * {@.ja Buffer を取得する}
     * {@.en Getting Buffer}
     * <p>
     * {@.ja Connector が保持している Buffer を返す}
     * {@.en This operation returns this connector's buffer}
     *
     * @return 
     *   {@.ja このコネクタが保持するバッファへのポインタ}
     *   {@.en A pointer to the buffer owned by this connector}
     *
     */
    public abstract BufferBase<OutputStream> getBuffer();

    /**
     * {@.ja write 関数}
     * {@.en Writes data}
     * <p>
     * {@.ja OutPort からデータを Buffer へ write する関数}
     * {@.en The write function to write data from OutPort to Buffer}
     *
     */
    public abstract <DataType> ReturnCode write(final DataType data);

    /**
     * {@.ja OutPortBaseを格納する。}
     * {@.en Stores OutPortBase.}
     *
     * @param outportbase
     *   {@.ja OutPortBase}
     *   {@.en OutPortBase}
     *
     */
    public abstract void setOutPortBase(OutPortBase outportbase); 

    protected Logbuf rtcout;
    protected ConnectorInfo m_profile;
    protected boolean m_isLittleEndian;
}

