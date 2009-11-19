package jp.go.aist.rtm.RTC.port;

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

import org.omg.CORBA.Any;

import RTC.InPortAnyPOA;
import _SDOPackage.NVListHolder;
import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.TypeCast;
import jp.go.aist.rtm.RTC.buffer.BufferBase;

/**
 * <p>通信手段に TCP ソケットを利用した入力ポートプロバイダの実装クラスです。</p>
 */
//TODO 未実装
public class InPortTcpSockProvider<DataType>
        extends InPortProviderImpl implements InPortProvider {

    /**
     * <p>コンストラクタ
     * ポートプロパティに以下の項目を設定する。
     * <ul>
     * <li>インターフェースタイプ : TCP_Any</li>
     * <li>データフロータイプ : Push</li>
     * <li>サブスクリプションタイプ : Any</li>
     * </ul>
     * </p>
     *
     */
    InPortTcpSockProvider(Class<DataType> DATA_TYPE_CLASS,
            BufferBase<DataType> buffer, Properties prop) {

        //        m_server(buffer, prop);
        this.DATA_TYPE_CLASS = DATA_TYPE_CLASS;
        this.TYPE_CAST = new TypeCast<DataType>(DATA_TYPE_CLASS);
        this.m_buffer = buffer;
        m_prop = prop;

        //        CORBA::Any any_var;
//        DataType   tmp_var;
//        any_var <<= tmp_var;

        // PortProfile setting
        this.m_inPortProvider.setDataType(TypeCast.getDataTypeCodeName(DATA_TYPE_CLASS));
        this.m_inPortProvider.setInterfaceType("TCP_Any");
        this.m_inPortProvider.setDataFlowType("Push");
        this.m_inPortProvider.setSubscriptionType("Any");

        // setup TCP server
//        m_server.open(0);

        // set the TCP server address to properties
        StringBuffer addr = new StringBuffer();
//        addr  = m_server.getHostName();
        addr.append(":");
//        addr += otos(m_server.getPortNumber());
        CORBA_SeqUtil.push_back(m_properties,
                NVUtil.newNV("dataport.tcp_any.inport_addr", addr.toString(), String.class));
    }

    /**
     * Interface情報を公開する。
     *
     * @param prop Interface情報を受け取るプロパティ
     *
     */
//    public void publishInterface(NVListHolder prop)  {
    public boolean publishInterface(NVListHolder prop)  {
        if( !NVUtil.isStringValue(prop,
                "dataport.interface_type",
                "TCP_Any"))
        {
            return true;
        }
        NVListHolder nv = new NVListHolder(m_properties.value);
        NVUtil.append(prop, nv);
        return true;
    }

    public void publishInterfaceProfile(NVListHolder properties) {
        this.m_inPortProvider.publishInterfaceProfile(properties);
    }

    public void init(Properties prop) {
    }

    public void setBuffer(BufferBase<OutputStream> buffer) {
    }

    public void put(Any data) {
    }

//    private TcpServer<DataType> m_server;
    private BufferBase<DataType> m_buffer;
    private Properties m_prop;
    private Class<DataType> DATA_TYPE_CLASS;
    private TypeCast<DataType> TYPE_CAST;
    private InPortProviderImpl m_inPortProvider = new InPortProviderImpl();
}
