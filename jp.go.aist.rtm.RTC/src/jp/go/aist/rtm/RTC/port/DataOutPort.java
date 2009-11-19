package jp.go.aist.rtm.RTC.port;

import java.util.Iterator;
import java.util.Vector;

import jp.go.aist.rtm.RTC.port.publisher.PublisherBase;
import jp.go.aist.rtm.RTC.port.publisher.PublisherFactory;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.Properties;
import RTC.ConnectorProfile;
import RTC.ConnectorProfileHolder;
import RTC.ReturnCode_t;
import _SDOPackage.NVListHolder;


/*!
 * @if jp
 * @class DataOutPort
 * @brief InPort 用 Port
 * @else
 * @class DataOutPort
 * @brief InPort abstruct class
 * @endif
 */
/**
 * <p>データ出力ポートです。</p>
 * 
 * @param <DataType> データ型を指定します。
 */
public class DataOutPort<DataType> extends PortBase {

    /**
     * <p>コンストラクタです。</p>
     * 
     * @param DATA_TYPE_CLASS 型パラメータ&lt;DataType&gt;で指定したクラスのClassオブジェクト
     * @param name ポート名称
     * @param outPort 当該データ出力ポートに関連付けるOutPortオブジェクト
     */
    public DataOutPort(Class<DataType> DATA_TYPE_CLASS,
            final String name, OutPort<DataType> outPort, Properties prop) throws Exception {
/*
        
        super(name);
        this.m_outPort = outPort;
        
        // PortProfile::properties を設定
        addProperty("port.port_type", "DataOutPort", String.class);
        
        NVListHolder holder = new NVListHolder(this.m_profile.properties);
        // CORBA OutPort Provider
        this.m_providers.add(new OutPortCorbaProvider<DataType>(DATA_TYPE_CLASS, outPort));
        this.m_providers.lastElement().publishInterfaceProfile(holder);

        // TCP Socket OutPort Provider
        this.m_providers.add(new OutPortTcpSockProvider<DataType>(DATA_TYPE_CLASS, outPort));
        this.m_providers.lastElement().publishInterfaceProfile(holder);

        this.m_profile.properties = holder.value;

        this.m_consumers.add(new InPortCorbaConsumer<DataType>(DATA_TYPE_CLASS, outPort));
        this.m_consumers.add(new InPortTcpSockConsumer<DataType>(DATA_TYPE_CLASS, outPort, prop));
*/
    }
    
    /**
     * <p>Interface 情報を公開します。</p>
     *
     * <p>このメソッドは、notify_connect()処理シーケンスの始めに呼び出されるテンプレートメソッドです。
     * notify_connect()では、
     * <ol>
     * <li>publishInterfaces()</li>
     * <li>connectNext()</li>
     * <li>subscribeInterfaces()</li>
     * <li>updateConnectorProfile()</li>
     * </ol>
     * の順にprotectedメソッドが呼び出されて接続処理が行われます。
     * </p>
     * 
     * <p>このメソッドの処理は、新規のconnector_idに対しては接続の生成が適切に行われる必要があります。<br />
     * また、既存のconnector_idに対しては更新が適切に行われる必要があります。</p>
     *
     * @param connector_profile 接続プロファイル情報
     * @return ReturnCode_t 戻り値
     */
    protected ReturnCode_t publishInterfaces(ConnectorProfileHolder connector_profile) {
        
        NVListHolder properties = new NVListHolder(connector_profile.value.properties);
        
        for (Iterator<OutPortProvider> it = this.m_providers.iterator(); it.hasNext(); ) {
            OutPortProvider provider = it.next();
            provider.publishInterface(properties);
        }
        connector_profile.value.properties = properties.value;
        
        return ReturnCode_t.RTC_OK;
    }
    
    /**
     * <p>Interface情報を取得します。</p>
     *
     * <p>このメソッドは、notify_connect()処理シーケンスの中間に呼び出されるテンプレートメソッドです。
     * notify_connect()では、
     * <ol>
     * <li>publishInterfaces()</li>
     * <li>connectNext()</li>
     * <li>subscribeInterfaces()</li>
     * <li>updateConnectorProfile()</li>
     * </ol>
     * の順にprotectedメソッドが呼び出され接続処理が行われます。</p>
     *
     * @param connector_profile 接続プロファイル情報
     * @return ReturnCode_t 戻り値
     */
    protected ReturnCode_t subscribeInterfaces(final ConnectorProfileHolder connector_profile) {
/*
        
        subscribe s = new subscribe(connector_profile.value);
        for (Iterator<InPortConsumer> it = this.m_consumers.iterator(); it.hasNext(); ) {
            InPortConsumer consumer = it.next();
            s.subscribeInterface(consumer);
        }
        if (s._consumer == null) {
            return ReturnCode_t.RTC_OK;
        }
        
        // Pubslihser を生成
        Properties prop = NVUtil.toProperties(new NVListHolder(connector_profile.value.properties));
        PublisherBase publisher = this.m_pf.create(s._consumer.clone(), prop);

        // Publisher を OutPort にアタッチ
        this.m_outPort.attach(connector_profile.value.connector_id, publisher);
*/

        return ReturnCode_t.RTC_OK;
    }
    
    /**
     * <p>Interfaceの接続を解除します。</p>
     *
     * <p>このメソッドは、notify_disconnect()処理シーケンスの終わりに呼び出されるテンプレートメソッドです。
     * notify_disconnect()では、
     * <ol>
     * <li>disconnectNext()</li>
     * <li>unsubscribeInterfaces()</li>
     * <li>eraseConnectorProfile()</li>
     * </ol>
     * の順にprotectedメソッドが呼び出され接続解除処理が行われます。
     *
     * @param connector_profile 接続プロファイル情報
     */
    protected void unsubscribeInterfaces(final ConnectorProfile connector_profile) {
/*
        
        PublisherBase publisher = this.m_outPort.detach(connector_profile.connector_id);
        if( publisher != null ) {
            this.m_pf.destroy(publisher);
        }
*/
    }
    
    private Vector<OutPortProvider> m_providers = new Vector<OutPortProvider>();
    private Vector<InPortConsumer> m_consumers = new Vector<InPortConsumer>();
    private OutPortBase m_outPort;
    private PublisherFactory m_pf = new PublisherFactory();

    private class subscribe {
        
        public subscribe(final ConnectorProfile prof) {
            
            this.m_prof = prof;
            this._consumer = null;
        }

        public subscribe(final subscribe subs) {
            
            this.m_prof = subs.m_prof;
            this._consumer = subs._consumer;
        }

        public void subscribeInterface(InPortConsumer cons) {

            if (cons.subscribeInterface(new NVListHolder(this.m_prof.properties))) {
                this._consumer = cons;
            }
        }
        
        private final ConnectorProfile m_prof;
        private InPortConsumer _consumer;
    }
  /**
   * <p> Activate all Port interfaces </p>
   */
  public void activateInterfaces() {
  }
  
  /**
   * <p> Deactivate all Port interfaces </p>
   */
  public void deactivateInterfaces() {
  }
}
