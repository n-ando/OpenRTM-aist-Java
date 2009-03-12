package jp.go.aist.rtm.RTC.port;

import java.util.Iterator;
import java.util.Vector;

import _SDOPackage.NVListHolder;

import RTC.ConnectorProfile;
import RTC.ConnectorProfileHolder;
import RTC.ReturnCode_t;


/**
 * <p>データ入力ポートの実装クラスです。</p>
 * 
 * @param <DataType> データ型を指定します。 
 */
public class DataInPort<DataType> extends PortBase {
    /**
     * <p>コンストラクタです。</p>
     * 
     * @param DATA_TYPE_CLASS 型パラメータ&lt;DataType&gt;で指定したクラスのClassオブジェクト
     * @param name ポート名称
     * @param inPort 当該データ入力ポートに関連付けるInPortオブジェクト
     */
    public DataInPort(Class<DataType> DATA_TYPE_CLASS,
            final String name, InPort<DataType> inPort) throws Exception {
        super(name);
    
        // PortProfile::properties を設定
        addProperty("port.port_type", "DataInPort");

        this.m_providers.add(new InPortCorbaProvider<DataType>(DATA_TYPE_CLASS, inPort));
        NVListHolder holder = new NVListHolder(this.m_profile.properties);
        this.m_providers.lastElement().publishInterfaceProfile(holder);
        this.m_profile.properties = holder.value;
    
        this.m_consumers.add(new OutPortCorbaConsumer<DataType>(DATA_TYPE_CLASS, inPort));
        this.m_dummy.add(1);
    }
    
    /**
     * <p>Interface情報を公開します。このPortが所有しているプロバイダ(Provider)に関する情報を、
     * ConnectorProfile#propertiesに代入します。</p>
     * 
     * @param connector_profile プロバイダ(Provider)に関する情報を受け取るホルダオブジェクト
     * 
     * @return ReturnCode_t 戻り値
     */
    protected ReturnCode_t publishInterfaces(ConnectorProfileHolder connector_profile) {

        if (this.m_dummy.size() != 1) {
            StringBuffer msg = new StringBuffer();
            msg.append("Memory access violation was detected.\n");
            msg.append("dummy.size(): ").append(this.m_dummy.size()).append("\n");
            msg.append("size() should be 1.\n");
            throw new AssertionError(msg.toString());
        }
        
        for (Iterator<InPortProvider> it = this.m_providers.iterator(); it.hasNext(); ) {
            InPortProvider provider = it.next();
            NVListHolder holder = new NVListHolder(connector_profile.value.properties);
            provider.publishInterface(holder);
            connector_profile.value.properties = holder.value;
        }

        return ReturnCode_t.RTC_OK;
    }
    
    /**
     * <p>Interfaceに接続します。Portが所有するConsumerに適合するProviderに関する情報を
     * ConnectorProfile#propertiesから抽出し、ConsumerにCORBAオブジェクト参照を設定します。</p>
     * 
     * @param connector_profile 接続プロファイルオブジェクト
     * 
     * @return ReturnCode_t 戻り値
     */
    protected ReturnCode_t subscribeInterfaces(final ConnectorProfileHolder connector_profile) {
        
        for (Iterator<OutPortConsumer> it = this.m_consumers.iterator(); it.hasNext(); ) {
            OutPortConsumer consumer = it.next();
            consumer.subscribeInterface(new NVListHolder(connector_profile.value.properties));
        }
        
        return ReturnCode_t.RTC_OK;
    }
    
    /**
     * <p>Interfaceへの接続を解除します。
     * 与えられたConnectorProfileに関連するConsumerに設定された全てのObjectを解放し接続を解除します。</p>
     * 
     * @param connector_profile 接続プロファイルオブジェクト
     */
    protected void unsubscribeInterfaces(final ConnectorProfile connector_profile) {
        
        for (Iterator<OutPortConsumer> it = this.m_consumers.iterator(); it.hasNext(); ) {
            OutPortConsumer consumer = it.next();
            consumer.unsubscribeInterface(new NVListHolder(connector_profile.properties));
        }
    }
    
    private Vector<Integer> m_dummy = new Vector<Integer>();
    private Vector<InPortProvider> m_providers = new Vector<InPortProvider>();
    private Vector<OutPortConsumer> m_consumers = new Vector<OutPortConsumer>();
}
