package jp.go.aist.rtm.RTC.port;

import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.buffer.BufferBase;

import org.omg.CORBA.portable.OutputStream;

import _SDOPackage.NVListHolder;

/**
 * <p>出力ポートコンシューマが提供すべき機能のインタフェースです。</p>
 */
public interface OutPortConsumer {

    /**
     * <p>接続先ポートからデータを受信します。</p>
     */
//    public void pull();

    /**
     * <p>指定されたプロパティセットの内容に基づいて、データ受信通知の受け取りに登録します。</p>
     * 
     * @param properties 登録時に参照される情報
     * @return 登録された場合にはtrueを、さもなくばfalseを返します。
     */
    public boolean subscribeInterface(final NVListHolder properties);

    /**
     * <p>データ受信通知の受け取り登録を解除します。</p>
     * 
     * @param properties 登録解除時に参照される情報
     */
    public void unsubscribeInterface(final NVListHolder properties);

    /**
     * <p> Initializing configuration </p>
     *
     * <p>This operation would be called to configure in initialization.
     * In the concrete class, configuration should be performed
     * getting appropriate information from the given Properties data.
     * This function might be called right after instantiation and
     * connection sequence respectivly.  Therefore, this function
     * should be implemented assuming multiple call. </p>
     *
     * @param prop Configuration information
     *
     */
    public void init(Properties prop);

    /**
     * <p> Setting outside buffer's pointer </p>
     *
     * <p> A pointer to a buffer from which OutPortProvider retrieve data.
     * If already buffer is set, previous buffer's pointer will be
     * overwritten by the given pointer to a buffer.  Since
     * OutPortProvider does not assume ownership of the buffer
     * pointer, destructor of the buffer should be done by user. </p>
     * 
     * @param buffer A pointer to a data buffer to be used by OutPortProvider
     */
    public void setBuffer(BufferBase<OutputStream> buffer);

    /**
     *
     * <p> Pure virtual function to receive data. </p>
     *
     * @param data
     * @return PORT_OK
     *         BUFFER_TIMEOUT
     *         RECV_EMPTY
     *         CONNECTION_LOST
     *         PORT_ERROR
     *         UNKNOWN_ERROR
     *
     */
    public ReturnCode get(OutputStream data);
    /**
     * <p> setting Connector </p>
     * 
     * @param connector
     */
    public void setConnector(InPortConnector connector);
    public void setListener(ConnectorBase.ConnectorInfo info, 
                            ConnectorListeners listeners);
}
