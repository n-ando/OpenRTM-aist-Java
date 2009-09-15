package jp.go.aist.rtm.RTC.port;

import org.omg.CORBA.Any;

import RTC.InPortAnyPOA;
import _SDOPackage.NVListHolder;
import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.TypeCast;

/**
 * <p>通信手段に TCP ソケットを利用した出力ポートプロバイダの実装クラスです。</p>
 */
//TODO 未実装
public class OutPortTcpSockProvider<DataType>
        extends OutPortProviderImpl implements OutPortProvider {

    /**
     * <p>コンストラクタ
     * ポートプロパティに以下の項目を設定する。
     * <ul>
     * <li>インターフェースタイプ : TCP_Any</li>
     * <li>データフロータイプ : Push, Pull</li>
     * <li>サブスクリプションタイプ : Flush, New, Periodic</li>
     * </ul>
     * </p>
     *
     */
    OutPortTcpSockProvider(Class<DataType> DATA_TYPE_CLASS,
            BufferBase<DataType> buffer) {

        //        m_server(buffer, prop);
        this.DATA_TYPE_CLASS = DATA_TYPE_CLASS;
        this.TYPE_CAST = new TypeCast<DataType>(DATA_TYPE_CLASS);
        this.m_buffer = buffer;

//        CORBA::Any any_var;
//        DataType   tmp_var;
//        any_var <<= tmp_var;

        // PortProfile setting
        this.m_outPortProvider.setDataType(TypeCast.getDataTypeCodeName(DATA_TYPE_CLASS));
        this.m_outPortProvider.setInterfaceType("TCP_Any");
        this.m_outPortProvider.setDataFlowType("Push, Pull");
        this.m_outPortProvider.setSubscriptionType("Flush, New, Periodic");
    }

    public void publishInterfaceProfile(NVListHolder properties) {
        this.m_outPortProvider.publishInterfaceProfile(properties);
    }
    
    public boolean publishInterface(NVListHolder properties) {
        return true;
    }
    private BufferBase<DataType> m_buffer;
    private Class<DataType> DATA_TYPE_CLASS;
    private TypeCast<DataType> TYPE_CAST;
    private OutPortProviderImpl m_outPortProvider = new OutPortProviderImpl();
}
