package jp.go.aist.rtm.RTC.port;

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

import org.omg.CORBA.SystemException;

import OpenRTM.InPortCdrPOA;
import _SDOPackage.NVListHolder;

import jp.go.aist.rtm.RTC.util.NVListHolderFactory;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.buffer.BufferBase;
/**
 * <p>InPortProviderインタフェースを実装する際に利用するベースクラスです。</p>
 */
public class InPortProviderImpl extends InPortCdrPOA implements InPortProvider {

    /**
     * <p>InterfaceProfile情報を公開します。</p>
     * 
     * @param properties InterfaceProfile情報を受け取るホルダオブジェクト
     */
    public void publishInterfaceProfile(NVListHolder properties) {

        NVUtil.appendStringValue(properties, "dataport.data_type",
                this.m_dataType);
        NVUtil.appendStringValue(properties, "dataport.interface_type",
                this.m_interfaceType);
        NVUtil.appendStringValue(properties, "dataport.dataflow_type",
                this.m_dataflowType);
        NVUtil.appendStringValue(properties, "dataport.subscription_type",
                this.m_subscriptionType);
    }
    
    /**
     * <p>Interface情報を公開します。</p>
     * 
     * @param properties Interface情報を受け取るホルダオブジェクト
     */
//    public void publishInterface(NVListHolder properties) {
    public boolean publishInterface(NVListHolder properties) {

        if (! NVUtil.isStringValue(properties,
                "dataport.interface_type",
                this.m_interfaceType)) {
            return true;
        }

        NVUtil.append(properties, this.m_properties);
        return true;
    }
    
    /**
     * <p> [CORBA interface] Write data into the buffer </p>
     * <p> Write data into the specified buffer. </p>
     *
     * @param data The target data for writing
     *
     */
    public OpenRTM.PortStatus put(byte[] data)
      throws SystemException {
         return OpenRTM.PortStatus.PORT_OK;
    }
    /**
     * <p>データタイプを設定します。</p>
     * 
     * @param dataType データタイプ
     */
    protected void setDataType(final String dataType) {
        this.m_dataType = dataType;
    }
    
    /**
     * <p>インタフェースタイプを設定します。</p>
     * 
     * @param interfaceType インタフェースタイプ
     */
    protected void setInterfaceType(final String interfaceType) {
        this.m_interfaceType = interfaceType;
    }
    
    /**
     * <p>データフロータイプを設定します。</p>
     * 
     * @param dataflowType データフロータイプ
     */
    protected void setDataFlowType(final String dataflowType) {
        this.m_dataflowType = dataflowType;
    }
    
    /**
     * <p>サブスクリプションタイプを設定します。</p>
     * 
     * @param subscriptionType サブスクリプションタイプ
     */
    protected void setSubscriptionType(final String subscriptionType) {
        this.m_subscriptionType = subscriptionType;
    }
    
    public void init(Properties prop) {
    }

    public void setBuffer(BufferBase<OutputStream> buffer) {
    }
    public void setConnector(InPortConnector connector) {
    }
    /**
     * <p>インタフェース情報を保持するオブジェクトです。</p>
     */
    protected NVListHolder m_properties = NVListHolderFactory.create();

    private String m_dataType = new String();
    private String m_interfaceType = new String();
    private String m_dataflowType = new String();
    private String m_subscriptionType = new String();
}
