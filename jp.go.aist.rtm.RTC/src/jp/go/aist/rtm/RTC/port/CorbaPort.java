package jp.go.aist.rtm.RTC.port;

import java.util.Vector;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.log.Logbuf;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.NVListHolderFactory;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.operatorFunc;

import org.omg.CORBA.ORB;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.Object;
import org.omg.CORBA.ObjectHelper;
import org.omg.CORBA.Any;
import org.omg.CORBA.TCKind;
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
        rtcout = new Logbuf("CorbaPort");
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

        rtcout.println(rtcout.TRACE, "registerProvider(instance="+instance_name+", type_name="+type_name+")");
        if (! this.appendInterface(instance_name, type_name,
				   PortInterfacePolarity.PROVIDED)) {
            return false;
        }
        // byte[] oid = _default_POA().activate_object(provider);
	byte[] oid = null;
	try {
	    oid = _default_POA().servant_to_id(provider);
	}
	catch (Exception e) {
	}

	try {
	    _default_POA().activate_object(provider);
	}
	catch (org.omg.PortableServer.POAPackage.ServantAlreadyActive e) {
	    return false;
	}
	
        Object obj = _default_POA().id_to_reference(oid);

        StringBuffer key = new StringBuffer("port");
        key.append(".").append(type_name).append(".").append(instance_name);

        ORB orb = Manager.instance().getORB();
	String ior = orb.object_to_string(obj);

        CORBA_SeqUtil.push_back(this.m_providers, NVUtil.newNV(key.toString(), ior));
	m_servants.put(instance_name, new ProviderInfo(provider, oid));

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
        
        rtcout.println(rtcout.TRACE, "registerConsumer()");
        if (! appendInterface(instance_name, type_name,
			      PortInterfacePolarity.REQUIRED)) {
            return false;
        }
        
        Consumer cons = new Consumer(instance_name, type_name, consumer);
        this.m_consumers.add(cons);
        
        return true;
    }
    
    //============================================================
    // Local operations
    //============================================================
    /**
     * <p> Port の全てのインターフェースを activates する </p>
     */
    public void activateInterfaces() {
	Set set = m_servants.keySet();

	Iterator it = set.iterator();

	java.lang.Object object;
	while(it.hasNext()) {
	    try {
		object = it.next();
		_default_POA().activate_object_with_id(m_servants.get(object).oid, m_servants.get(object).servant);
	    }
	    catch (org.omg.PortableServer.POAPackage.ServantAlreadyActive e) {
	    }
	    catch (org.omg.PortableServer.POAPackage.ObjectAlreadyActive e) {
	    }
	    catch (org.omg.PortableServer.POAPackage.WrongPolicy e) {
	    }
	}
    }
    
    /**
     * <p> 全ての Port のインターフェースを deactivates する </p>
     */
    public void deactivateInterfaces() {
	Set set = m_servants.keySet();

	Iterator it = set.iterator();

	java.lang.Object object;
	while(it.hasNext()) {
	    try {
		object = it.next();
		_default_POA().deactivate_object(m_servants.get(object).oid);
	    }
	    catch (org.omg.PortableServer.POAPackage.ObjectNotActive e) {
	    }
	    catch (org.omg.PortableServer.POAPackage.WrongPolicy e) {
	    }
	}
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
        
        rtcout.println(rtcout.TRACE, "publishInterfaces()");
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

        rtcout.println(rtcout.TRACE, "subscribeInterfaces()");
        final NVListHolder nv = new NVListHolder(connector_profile.value.properties);

        ORB orb = Manager.instance().getORB();

        // CORBA_SeqUtil.for_each(nv, new subscribe(this.m_consumers));
	int len = this.m_consumers.size();
	for (int i = 0; i < len; ++i) {
	    int index;
	    index = NVUtil.find_index(nv, this.m_consumers.get(i).name);
	    if (index < 0)
		continue;

	    Any anyVal = nv.value[index].value;
	    String ior = null;
	    if (anyVal.type().kind() == TCKind.tk_wstring) {
		ior = anyVal.extract_wstring();
	    }
	    else if (anyVal.type().kind() == TCKind.tk_string) {
		ior = anyVal.extract_string();
	    }
	    else {
		ior = anyVal.extract_Value().toString();
	    }

	    Object obj = orb.string_to_object(ior);
	    if (obj == null) {
		rtcout.println(rtcout.ERROR, "Extracted object is nul reference");
		continue;
	    }
	    
	    boolean result = this.m_consumers.get(i).consumer.setObject(obj);
	    if (!result) {
		rtcout.println(rtcout.ERROR, "Cannot narrow reference");
		continue;
	    }
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
        
        public String name;
        public CorbaConsumerBase consumer;
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
     * <p> Providerの情報を格納するクラス </p>
     */
    private class ProviderInfo {
	public ProviderInfo() {
	}
	public ProviderInfo(Servant servant, byte[] objectid) {
	    servant = servant;
	    oid = objectid;
	}
	public Servant servant = null;
	public byte[] oid = null;
    }
    protected HashMap<String, ProviderInfo> m_servants = new HashMap<String, ProviderInfo>();


    /**
     * <p>Logging用フォーマットオブジェクト</p>
     */
    protected Logbuf rtcout;
}
