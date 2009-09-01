package jp.go.aist.rtm.RTC.port;

import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.TypeCast;
import _SDOPackage.NVListHolder;

/**
 * <p>通信手段に TCP ソケットを利用した入力ポートコンシューマの実装クラスです。</p>
 */
//TODO 未実装
//public class InPortTcpSockConsumer<DataType> implements InPortConsumer {
public class InPortTcpSockConsumer<DataType> {
    
    public InPortTcpSockConsumer(Class<DataType> DATA_TYPE_CLASS,BufferBase<DataType> buffer, Properties prop) {
        this.DATA_TYPE_CLASS = DATA_TYPE_CLASS;
        this.TYPE_CAST = new TypeCast<DataType>(DATA_TYPE_CLASS);
        m_buffer = buffer;
        m_prop = prop;
        m_byteswap = true;
    }
    public InPortTcpSockConsumer clone() {
        return this;
    }

    
    private Class<DataType> DATA_TYPE_CLASS;
    private TypeCast<DataType> TYPE_CAST;
    private BufferBase<DataType> m_buffer;
    private Properties m_prop;
    private boolean m_byteswap;

    public void push() {
    }
    public boolean subscribeInterface(NVListHolder properties) {
        return false;
    }
    public void unsubscribeInterface(NVListHolder properties) {
    }
}
