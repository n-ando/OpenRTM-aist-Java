package jp.go.aist.rtm.RTC.port;

import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.util.Properties;

import org.omg.CORBA.portable.OutputStream;

import _SDOPackage.NVListHolder;

/**
 * <p>OutPortに対して何を提供しているかを宣言するインタフェースです。</p>
 */
public interface OutPortProvider {
    
    /**
     * <p>InterfaceProfile情報を公開します。
     * 引数で指定するホルダ内のNameValueオブジェクトのdataport.interface_type値を調べ、
     * 当該ポートのインタフェースタイプと一致する場合のみ情報が取得されます。</p>
     * 
     * @param properties Interface情報を受け取るホルダオブジェクト
     */
    public void publishInterfaceProfile(NVListHolder properties);
    
    /**
     * <p>Interface情報を公開します。</p>
     * 
     * @param properties InterfaceProfile情報を受け取るホルダオブジェクト
     * @return boolean
     */
    public boolean publishInterface(NVListHolder properties);
    
    /**
     * <p> Initializing configuration </p>
     *
     * <p> This operation would be called to configure in initialization. </p>
     * <p> In the concrete class, configuration should be performed </p>
     * <p> getting appropriate information from the given Properties data. </p>
     * <p> This function might be called right after instantiation and </p>
     * <p> connection sequence respectivly.  Therefore, this function </p>
     * <p> should be implemented assuming multiple call.
     *
     * @param prop Configuration information
     */
    public void init(Properties prop);
 
    /**
     * <p> Setting outside buffer's pointer </p>
     *
     * <p> A pointer to a buffer from which OutPortProvider retrieve data. </p>
     * <p> If already buffer is set, previous buffer's pointer will be </p>
     * <p> overwritten by the given pointer to a buffer.  Since </p>
     * <p> OutPortProvider does not assume ownership of the buffer </p>
     * <p> pointer, destructor of the buffer should be done by user. </p>
     *  
     * @param buffer A pointer to a data buffer to be used by OutPortProvider
     */
//    public void setBuffer(BufferBase<InputStream> buffer);
    public void setBuffer(BufferBase<OutputStream> buffer);

    /**
     * <p> Set the listener.  </p>
     */
    public void setListener(ConnectorBase.ConnectorInfo info,
                             ConnectorListeners listeners);
    /**
     * <p> setting Connector </p>
     * 
     * @param connector
     */
    public void setConnector(OutPortConnector connector);
}
