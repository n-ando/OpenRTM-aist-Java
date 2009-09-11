package jp.go.aist.rtm.RTC.port;

import java.util.Vector;

import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.NVListHolderFactory;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.operatorFunc;

import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.Object;
import org.omg.CORBA.ObjectHelper;
import org.omg.PortableServer.Servant;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import _SDOPackage.NVListHolder;
import _SDOPackage.NameValue;

import RTC.ConnectorProfile;
import RTC.ConnectorProfileHolder;
import RTC.PortInterfacePolarity;
import RTC.ReturnCode_t;

/**
 * <p>CORBAを通信手段とするPortクラスです。</p>
 */
public class CorbaPort extends PortBase {

    /**
     * <p>コンストラクタです。</p>
     * 
     * @param name Port名称
     */
    public CorbaPort(final String name) {
        
        super(name);
        addProperty("port.port_type", "CorbaPort", String.class);
    }
    
    /**
     * <p>このPortにおいて提供したいサーバントを登録します。<br />
     * 引数で与えられるインスタンス名とタイプ名が、
     * サーバント自身のインスタンス名とタイプ名として関連付けられます。</p>
     * 
     * @param instance_name インスタンス名
     * @param type_name タイプ名
     * @param provider 当該Portに登録するProviderサーバント
     * 
     * @return 既に同名の instance_name が登録されていれば false を返します。
     */
    public boolean registerProvider(final String instance_name,
            final String type_name, Servant provider)
            throws ServantAlreadyActive, WrongPolicy, ObjectNotActive {

        if (! this.appendInterface(instance_name, type_name,
                PortInterfacePolarity.PROVIDED)) {
            return false;
        }

        byte[] oid = _default_POA().activate_object(provider);
        Object obj = _default_POA().id_to_reference(oid);

        StringBuffer key = new StringBuffer("port");
        key.append(".").append(type_name).append(".").append(instance_name);

        CORBA_SeqUtil.push_back(this.m_providers, NVUtil.newNV(key.toString(), obj, Object.class));

        return true;
    }
    
    /**
     * <p>このPortが要求するサービスのプレースホルダとしてのコンシューマ(Consumer)を登録します。<br />
     * 引数で指定されたインスタンス名とタイプ名が、指定されたコンシューマと関連付けられます。</p>
     * 
     * <p>Port間の接続時には、同一のインスタンス名とタイプ名を持つサービスが、
     * 他のPortから提供(Provide)されている場合、そのサービスのCORBAオブジェクト参照が
     * コンシューマ(Consumer)に設定されます。</p>
     * 
     * @param instance_name インスタンス名
     * @param type_name タイプ名
     * @param consumer Consumerオブジェクト
     * 
     * @return 既に同名の instance_name が登録されていれば false を返します。
     */
    public boolean registerConsumer(final String instance_name,
            final String type_name,
            CorbaConsumerBase consumer) {
        
        if (! appendInterface(instance_name, type_name,
                PortInterfacePolarity.REQUIRED)) {
            return false;
        }
        
        Consumer cons = new Consumer(instance_name, type_name, consumer);
        this.m_consumers.add(cons);
        
        return true;
    }
    
    /**
     * <p>Interface情報を公開します。このPortが所有しているプロバイダ(Provider)に関する情報を、
     * ConnectorProfile#propertiesに代入します。代入する情報は、NameValueのnameとvalueとして
     * 以下のものが格納されます。</p>
     * 
     * <p>port.&lt;type_name&gt;.&lt;instance_name&gt;: &lt;CORBA.Object&gt;</p>
     * 
     * <p>ここで、<br />
     * &lt;type_name&gt;: PortInterfaceProfile::type_name<br />
     * &lt;instance_name&gt;: PortInterfaceProfile::instance_name<br />
     * です。ConnectorProfile::propertiesでは、これらを .(ドット)表記で、
     * NameValue のキーとしています。したがって、
     * 
     * <pre>
     * PortInterfaceProfile
     * {
     *     instance_name = "PA10_0";
     *     type_name = "Manipulator";
     *     polarity = PROVIDED;
     * }
     * </pre>
     * 
     * ならば、
     * 
     * <pre>
     * NameValue = { "port.Manipulator.PA10_0": &lt;Object reference&gt; }
     * </pre>
     * 
     * といった値がConnectorProfile#propertiesに格納され、他のPortに対して伝達されます。
     * 他のPortでこのインタフェースを使用するConsumerが存在すれば、
     * ConnectorProfileからオブジェクトリファレンスが取得され、何らかの形で使用されます。
     * </p>
     * 
     * @param connector_profile プロバイダ(Provider)に関する情報を受け取るホルダオブジェクト
     * 
     * @return ReturnCode_t.RTC_OK
     */
    protected ReturnCode_t publishInterfaces(ConnectorProfileHolder connector_profile) {
        
        NVListHolder holder = new NVListHolder(connector_profile.value.properties);
        CORBA_SeqUtil.push_back_list(holder, this.m_providers);
        connector_profile.value.properties = holder.value;
        
        return ReturnCode_t.RTC_OK;
    }
    
    /**
     * <p>Interfaceに接続します。Portが所有するConsumerに適合するProviderに関する情報を
     * ConnectorProfile#propertiesから抽出し、ConsumerにCORBAオブジェクト参照を設定します。</p>
     * 
     * <p>たとえば、Consumerが
     * <pre>
     *  PortInterfaceProfile
     *  {
     *      instance_name = "PA10_0";
     *      type_name = "Manipulator";
     *      polarity = REQUIRED;
     *  }
     * </pre>
     * として登録されていれば、他のPortの
     * <pre>
     *  PortInterfaceProfile
     *  {
     *      instance_name = "PA10_0";
     *      type_name = "Manipulator";
     *      polarity = PROVIDED;
     *  }
     * </pre> 
     * として登録されているSerivce ProviderのCORBAオブジェクト参照を探して、Consumerに設定します。</p>
     * 
     * <p>実際には、ConnectorProfile#propertiesに
     * <pre>
     * NameValue = { "port.Manipulator.PA10_0": &lt;Object reference&gt; }
     * </pre>
     * として登録されているNameValueを探し、そのCORBAオブジェクト参照をConsumerに設定します。</p>
     * 
     * @param connector_profile 接続プロファイルオブジェクト
     * 
     * @return ReturnCode_t.RTC_OK
     */
    protected ReturnCode_t subscribeInterfaces(final ConnectorProfileHolder connector_profile) {

        final NVListHolder nv = new NVListHolder(connector_profile.value.properties);
        CORBA_SeqUtil.for_each(nv, new subscribe(this.m_consumers));

        return ReturnCode_t.RTC_OK;
    }
    
    /**
     * <p>Interfaceへの接続を解除します。
     * 与えられたConnectorProfileに関連するConsumerに設定された全てのObjectを解放し接続を解除します。</p>
     * 
     * @param connector_profile 接続プロファイルオブジェクト
     */
    protected void unsubscribeInterfaces(final ConnectorProfile connector_profile) {
        
        final NVListHolder nv = new NVListHolder(connector_profile.properties);
        CORBA_SeqUtil.for_each(nv, new unsubscribe(this.m_consumers));
        connector_profile.properties = nv.value;
    }
    
    private NVListHolder m_providers = NVListHolderFactory.create();

    private class Consumer {
        
        public Consumer(final String _instance_name,
                final String _type_name, CorbaConsumerBase _cons) {
            
            this.name = "port." + _type_name + "." + _instance_name;
            this.consumer = _cons;
        }
        
        public Consumer(final Consumer cons) {

            this.name = cons.name;
            this.consumer = cons.consumer;
        }
        
        private String name;
        private CorbaConsumerBase consumer;
    }
    
    private Vector<Consumer> m_consumers = new Vector<Consumer>();

    private class subscribe implements operatorFunc {
        
        public subscribe(Vector<Consumer> cons) {
            
            this.m_cons = new Vector<Consumer>(cons);
            this.m_len = cons.size();
        }
        
        public void operator(java.lang.Object elem) {
            
            operator((NameValue) elem);
        }
        
        public void operator(NameValue nv) {

            for (int i = 0; i < this.m_len; ++i) {
                if (this.m_cons.get(i).name.equals(nv.name)) {
                    try {
                        Object obj = ObjectHelper.extract(nv.value);
                        if( obj != null ) {
                            this.m_cons.get(i).consumer.setObject(obj);
                        }
                    } catch (BAD_OPERATION ignored) {
                    }
                }
            }
        }
        
        private Vector<Consumer> m_cons; // コンストラクタで必ず初期化されるので、ここではインスタンス生成しない。
        private int m_len;
    }
    
    private class unsubscribe implements operatorFunc {
        
        public unsubscribe(final Vector<Consumer> cons) {
            
            this.m_cons = new Vector<Consumer>(cons);
            this.m_len = cons.size();
        }
        
        public void operator(java.lang.Object elem) {

            operator((NameValue) elem);
        }
        
        public void operator(NameValue nv) {
            
            for (int i = 0; i < this.m_len; i++) {
                if (this.m_cons.get(i).name.equals(nv.name)) {
                    this.m_cons.get(i).consumer.releaseObject();
                }
            }
        }
        private Vector<Consumer> m_cons; // コンストラクタで必ず初期化されるので、ここではインスタンス生成しない。
        private int m_len;
    }
  
 

  /**
   * <p> Activate all Port interfaces </p>
   */
  public void activateInterfaces() {
/*
    ServantMap.iterator it = m_servants.begin();
    while(it != m_servants.end()) {
        try {
            Manager.instance().getPOA().activate_object_with_id(it.second.oid, it.second.servant);
        }
        catch(final org.omg.PortableServer.POAPackage.ServantAlreadyActive e) {
        }
        catch(final org.omg.PortableServer.POAPackage.ObjectAlreadyActive e) {
        }
	it++;
      }
*/
  }
  
  /**
   * <p> Deactivate all Port interfaces </p>
   */
  public void deactivateInterfaces() {
/*
    ServantMap.iterator it = m_servants.begin();
    while(it != m_servants.end()) {
        try {
            Manager.instance().getPOA().deactivate_object(it.second.oid);
        }
        catch(const org.omg.PortableServer.POAPackage.ObjectNotActive e ) {
        }
	it++;
      }
*/
  }
  /**
   * <p> The structure to be stored Provider's information. </p>
   */
/*
  private class ProviderInfo
    {
      ProviderInfo(PortableServer.RefCountServantBase _servant, PortableServer.ObjectId _objectid) {
          servant = _servant;
	  oid = _objectid;
      }
      ProviderInfo(final ProviderInfo pinfo) {
          servant = pinfo.servant;
	  oid = pinfo.oid;
      }
      ProviderInfo operator(final ProviderInfo _pinfo) {
        ProviderInfo pinfo = _pinfo;
        return pinfo;
      }
      PortableServer.RefCountServantBase servant;
      PortableServer.ObjectId oid;
    };
    Map<String, ProviderInfo> m_servants = new HashMap<String,ProviderInfo>();
*/
  
    
}
