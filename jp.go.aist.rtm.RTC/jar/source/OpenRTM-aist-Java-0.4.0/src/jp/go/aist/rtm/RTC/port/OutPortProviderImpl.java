package jp.go.aist.rtm.RTC.port;

import _SDOPackage.NVListHolder;
import jp.go.aist.rtm.RTC.util.NVListHolderFactory;
import jp.go.aist.rtm.RTC.util.NVUtil;

/**
 * <p>OutPortProviderインタフェースのベース実装クラスです。<p>
 */
public class OutPortProviderImpl implements OutPortProvider {

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
    
    public void publishInterface(NVListHolder properties) {
        
        if (!NVUtil.isStringValue(properties,
                "dataport.interface_type",
                this.m_interfaceType)) {
            return;
        }
        
        NVUtil.append(properties, this.m_properties);
    }
    
    /**
     * <p>インタフェースプロフィールのポートタイプを設定します。</p>
     * 
     * @param portType ポートタイプ
     */
    protected void setPortType(final String portType) {
        
        this.m_portType = portType;
    }
    
    /**
     * <p>インタフェースポロフィールのデータタイプを設定します。</p>
     * 
     * @param dataType データタイプ
     */
    protected void setDataType(final String dataType) {
        
        this.m_dataType = dataType;
    }
    
    /**
     * <p>インタフェースプロフィールのインタフェースタイプを設定します。<//p>
     * 
     * @param interfaceType インタフェースタイプ
     */
    protected void setInterfaceType(final String interfaceType) {
        
        this.m_interfaceType = interfaceType;
    }
    
    /**
     * <p>インタフェースプロフィールのデータフロータイプを設定します。</p>
     * 
     * @param dataFlowType データフロータイプ
     */
    protected void setDataFlowType(final String dataFlowType) {
        
        this.m_dataflowType = dataFlowType;
    }
    
    /**
     * <p>インタフェースプロフィールのサブスクリプションタイプを設定します。</p>
     * 
     * @param subscriptionType サブスクリプションタイプ
     */
    protected void setSubscriptionType(final String subscriptionType) {
        
        this.m_subscriptionType = subscriptionType;
    }
    
    /**
     * <p>接続プロフィールを保持するメンバ変数です。</p>
     */
    protected NVListHolder m_properties = NVListHolderFactory.create();
    
    private String m_portType = new String();
    private String m_dataType = new String();
    private String m_interfaceType = new String();
    private String m_dataflowType = new String();
    private String m_subscriptionType = new String();
}
