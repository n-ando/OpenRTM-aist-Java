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
import jp.go.aist.rtm.RTC.util.ORBUtil;
import jp.go.aist.rtm.RTC.util.equalFunctor;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.StringHolder;

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
     * <p> Initializing properties </p>
     *
     * This operation initializes outport's properties. If a property
     * "connection_limit" is set and appropriate value is set to this
     * property value, the number of maximum connection is set as this
     * value. If the property does not exist or invalid value is set
     * to this property, the maximum number of connection will be set
     * unlimited.
     *
     * @param prop properties of the CorbaPort
     */
    public void init(Properties prop) {
        rtcout.println(rtcout.TRACE, "init()");
        rtcout.println(rtcout.PARANOID, "given properties:");
        String dumpString = new String();
        dumpString = prop._dump(dumpString, prop, 0);
        rtcout.println(rtcout.DEBUG, dumpString);

        m_properties.merge(prop);

        rtcout.println(rtcout.PARANOID, "updated properties:");
        dumpString = m_properties._dump(dumpString, m_properties, 0);
        rtcout.println(rtcout.DEBUG, dumpString);

        int num = -1;
        String limit = m_properties.getProperty("connection_limit","-1");
        try {
            num = Integer.parseInt(limit);
        }
        catch(Exception ex){
            rtcout.println(rtcout.ERROR, 
                    "invalid connection_limit value: "+limit );
        }
        setConnectionLimit(num);
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
    /**
     * {@.ja @brief Provider を登録する}
     * {@.en brief Register the provider}
     *
     * <p>
     * {@.ja この Port において提供したいサーバントをこの Port に対して登録す
     * る。サーバントは、引数で与えられる instance_name, type_name を、
     * サーバント自身のインスタンス名およびタイプ名として、サーバントに
     * 関連付けられる。この関数により、サーバントは CorbaPort 内部に保
     * 持されるとともに、PortInterfaceProfile にRTC::PROVIDED インター
     * フェースとして登録される。}
     * {@.en This operation registers a servant, which is provided in this
     * Port, to the Port. The servant is associated with
     * "instance_name" and "type_name" as the instance name of the
     * servant and as the type name of the servant. A given servant
     * will be stored in the CorbaPort, and this is registered as
     * RTC::PROVIDED interface into the PortInterfaceProfile.}
     * </p>
     *
     * @param instance_name 
     *   {@.ja サーバントのインスタンス名}
     *   {@.en Instance name of servant}
     * @param type_name 
     *   {@.ja サーバントのタイプ名}
     *   {@.en Type name of the servant}
     * @param provider 
     *   {@.ja CORBA サーバント}
     *   {@.en CORBA servant}
     *
     * @return 
     *   {@.ja 既に同名の instance_name が登録されていれば false を返す。}
     *   {@.en Return false if the same name of instance_name is already 
     *         registered.}
     *
     */
    public boolean registerProvider(final String instance_name,
				    final String type_name, Servant provider)
	throws ServantAlreadyActive, WrongPolicy, ObjectNotActive {

        rtcout.println(rtcout.TRACE, 
        "registerProvider(instance="+instance_name+",type_name="+type_name+")");

        try{ 
            m_providers.add(new CorbaProviderHolder(type_name,
                                                  instance_name,
                                                  provider));
        }
        catch(Exception ex){
            rtcout.println(rtcout.ERROR, 
                        "appending provider interface failed");
            return false;
        }
        if (! this.appendInterface(instance_name, type_name,
				   PortInterfacePolarity.PROVIDED)) {
            return false;
        }
        return true;
    }
    
    /*
     * {@.ja Consumer を登録する}
     * {@.en Register the consumer}
     *
     * <p>
     * {@.ja この Port が要求するサービスのプレースホルダであるコンシューマ
     * (Consumer) を登録する。Consumer が関連付けられるサービスのインス
     * タンス名およびタイプ名として、引数に instance_name, type_name お
     * よび Consumer 自身を与えることにより、内部でこれらが関連付けられ
     * る。Port 間の接続 (connect) 時 には、subscribeInterfaces() で述
     * べられているルールに基づき、Provider Interface の参照が自動的に
     * Consumer にセットされる。}
     * {@.en This operation registers a consumer, which is a service
     * placeholder this port requires. These are associated internally
     * with specified instance_name, type_name and Consumer itself to
     * the argument as service's instance name and its type name
     * associated with Consumer.  The service Provider interface'
     * references will be set automatically to the Consumer Interface
     * object when connections are established, according to the rules
     * that are described at the subscribeInterface() function's
     * documentation.}
     * </p>
     *
     * @param instance_name 
     *   {@.ja Consumer が要求するサービスのインスタンス名}
     *   {@.en Instance name of the service Consumer requires}
     * @param type_name 
     *   {@.ja Consumer が要求するサービスのタイプ名}
     *   {@.en Type name of the service Consumer requires}
     * @param consumer 
     *   {@.ja CORBA サービスコンシューマ}
     *   {@.en CORBA service consumer}
     *
     * @return 
     *   {@.ja 既に同名の instance_name が登録されていれば false を返す。}
     *   {@.en False would be returned if the same instance_name was registered}
     *
     */
    public boolean registerConsumer(final String instance_name,
				    final String type_name,
				    CorbaConsumerBase consumer) {
        
        rtcout.println(rtcout.TRACE, "registerConsumer()");
        if (! appendInterface(instance_name, type_name,
			      PortInterfacePolarity.REQUIRED)) {
            return false;
        }
        
        m_consumers.add(new CorbaConsumerHolder(type_name,
                                              instance_name,
                                              consumer));
        
        return true;
    }
    
    //============================================================
    // Local operations
    //============================================================
    /**
     * {@.ja Port の全てのインターフェースを activates する}
     * {@.en Activate all Port interfaces}
     *
     * <p>
     * {@.ja Port に登録されている全てのインターフェースを activate する。}
     * {@.en This operation activate all interfaces that is registered in the
     * ports.}
     * </p>
     */
    public void activateInterfaces() {
	Iterator it = m_providers.iterator();
	while(it.hasNext()) {
            ((CorbaProviderHolder)it.next()).activate();
        }
    }
    
    /**
     * {@.ja @brief 全ての Port のインターフェースを deactivates する}
     * {@.en Deactivate all Port interfaces}
     *
     * <p>
     * {@.ja Port に登録されている全てのインターフェースを deactivate する。}
     * {@.en This operation deactivate all interfaces that is registered in the
     * ports.}
     * </p>
     */
    public void deactivateInterfaces() {
	Iterator it = m_providers.iterator();
	while(it.hasNext()) {
            ((CorbaProviderHolder)it.next()).deactivate();
        }
    }


    /**
     * {@.ja Provider Interface 情報を公開する}
     * {@.en Publish information about interfaces}
     *
     * <p>
     * {@.ja この Port が所有する Provider インターフェースに関する情報を
     * ConnectorProfile::properties に代入し他の Port に対して公開する。
     * 今、RTCのインスタンス名等の情報が以下の通りであるとして、
     * <ul>
     * <li> RTCインスタンス名:              rtc_iname
     * <li> ポート名:                       port_name
     * <li> インターフェース極性:           if_polarity
     * <li> インターフェース型名:           if_tname
     * <li> インターフェースインスタンス名: if_iname
     * </ul>
     * NameValue 型の ConnectorProfile::properties の name と value として
     * 以下のものが格納される。
     *
     * <ul>
     * <li> name
     *   <rtc_iname>.port.<port_name>.provided.<if_tname>.<if_iname> </li>
     * <li> value
     *   Provider インターフェースの IOR 文字列 </li>
     * </ul>
     * 
     * なお、旧バージョンとの互換性のため以下の表記の NameValue も同時
     * に格納されるが、将来のバージョンでは削除される可能性がある。
     * 
     * <ul>
     * <li> name
     *   port.<if_tname>.<if_iname> </li>
     * <li> value
     *   Provider インターフェースの IOR 文字列 </li>
     * </ul>
     *
     * これらの値は ConnectorProfile::properties に格納され、他のポートに対して
     * 伝達される。他の Port でこのインターフェースを使用する Consumer が
     * 存在すれば、ConnectorProfile からこのキーからオブジェクトリファレンスを
     * 取得し何らかの形で使用される。}
     * {@.en This operation publishes Provider interfaces information, which
     * is owned by this port, to the other Ports via
     * ConnectorProfile::properties.
     * Now it is assumed RTC instance name and other information is as follows,
     *
     * <ul>
     * <li> RTC instance name:              rtc_iname
     * <li> Port name:                      port_name
     * <li> Interface polarity:             if_polarity
     * <li> Interface type name:            if_tname
     * <li> Interface instance name:        if_iname
     * </ul>
     *
     * the following values are stored as the "name" and the "value"
     * of the NameValue typee element in ConnectorProfile::properties.
     *
     * <ul>
     * <li> name
     *   <rtc_iname>.port.<port_name>.provided.<if_tname>.<if_iname> </li>
     * <li> value
     *   IOR string value of interface reference </li>
     * </ul>
     * 
     * In addition, although the following NameValue values are also
     * stored for the backward compatibility, this will be deleted in
     * the future version.
     *
     * <ul>
     * <li> name
     *   port.<if_tname>.<if_iname> </li>
     * <li> value
     *   IOR string value of interface reference </li>
     * </ul>
     *
     * These values are stored in the ConnectorProfile::properties and
     * are propagated to the other Ports. If the Consumer interface
     * exists that requires this Provider interface, it will retrieve
     * reference from the ConnectorProfile and utilize it.}
     * </p>
     *
     * @param connector_profile 
     *   {@.ja コネクタプロファイル}
     *   {@.en Connector profile}
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     */
    protected 
    ReturnCode_t publishInterfaces(ConnectorProfileHolder connector_profile) {
        
        rtcout.println(rtcout.TRACE, "publishInterfaces()");

        ReturnCode_t returnvalue = _publishInterfaces();
        if(returnvalue!=ReturnCode_t.RTC_OK) {
            return returnvalue;
        }

        NVListHolder holder = new NVListHolder();
	Iterator it = m_providers.iterator();
	while(it.hasNext()) {
            CorbaProviderHolder provider = (CorbaProviderHolder)it.next();
          //------------------------------------------------------------
          // new version descriptor
          // <comp_iname>.port.<port_name>.provided.<type_name>.<instance_name>
            StringBuffer strbuff = new StringBuffer(m_profile.name);
            strbuff.insert(m_ownerInstanceName.length(), ".port");
            strbuff.append(".provided." + provider.descriptor());
            String newdesc = strbuff.substring(0);
            CORBA_SeqUtil.push_back(holder, 
                                NVUtil.newNVString(newdesc, provider.ior()));

          //------------------------------------------------------------
          // old version descriptor
          // port.<type_name>.<instance_name>
            String olddesc = new String();
            olddesc += "port." + provider.descriptor();
            CORBA_SeqUtil.push_back(holder, 
                                NVUtil.newNVString(olddesc, provider.ior()));
        }

        NVListHolder profholder 
            = new NVListHolder(connector_profile.value.properties);
        CORBA_SeqUtil.push_back_list(profholder, holder);
        connector_profile.value.properties = profholder.value;
        
        String dumpString = new String();
        dumpString = NVUtil.toString(holder);
        rtcout.println(rtcout.DEBUG, dumpString);

        return ReturnCode_t.RTC_OK;
    }
    
    /**
     * {@.ja Provider Interface 情報を取得する} 
     * {@.en Subscribe to interface}
     * 
     * <p>
     * {@.ja この Portが所有する Consumer Interface に適合する Provider
     * Interface に関する情報をConnectorProfile::properties から抽出し
     * Consumer Interface にオブジェクト参照をセットする。
     *
     * 今、RTC のインスタンス名や Consumer Interface 等の情報が以下のと
     * おりであると仮定すると、
     *
     * - RTCインスタンス名:              rtc_iname
     * - ポート名:                       port_name
     * - インターフェース極性:           if_polarity
     * - インターフェース型名:           if_tname
     * - インターフェースインスタンス名: if_iname
     *
     * この Consumer Interface を表すインターフェース指定子は以下のよう
     * に表される。
     *
     * <rtc_iname>.port.<port_name>.required.<if_tname>.<if_iname>
     *
     * この関数は、まず引数 ConnectorProfile::properties に上記インター
     * フェース指定子をキーとして格納されている Provider Interface 指定
     * 子を探し出す。さらに、その Provider Interface 指定子をキーとして
     * 格納されている Provider Interface の参照を表す IOR 文字列を取得
     * し、Consumer Interface にセットする。
     *
     * 今、仮に、Provider を prov(n), その参照をIOR(n) さらに Consumer
     * をcons(n) のように記述し、これらすべてのインターフェースの型が同
     * 一であり、ConnectorProfile に以下の値が設定されているとする。
     *
     * <pre>
     * ConnectorProfile::properties =
     * {
     *   prov0: IOR0,
     *   prov1: IOR1,
     *   prov2: IOR2,
     *   cons0: prov2,
     *   cons1: prov1,
     *   cons2: prov0
     * }
     * </pre>
     *
     * このとき、cons(0..2) にはそれぞれ、参照が以下のようにセットされる。
     *
     * <pre>
     *   cons0 = IOR2
     *   cons1 = IOR1
     *   cons2 = IOR0
     * </pre>
     *
     * なお、旧バージョンとの互換性のため、
     * ConnectorProfile::properties に Consumer Interface をキーとした
     * 値がセットされていない場合でも、次のルールが適用される。
     *
     * 今、仮に Consumer Interface が
     *
     * <pre>
     *  PortInterfaceProfile
     *  {
     *    instance_name = "PA10_0";
     *    type_name     = "Manipulator";
     *    polarity      = REQUIRED;
     *  }
     * </pre>
     *
     * として登録されていれば、他の Port の
     *
     * <pre>
     *  PortInterfaceProfile
     *  {
     *    instance_name = "PA10_0";
     *    type_name     = "Manipulator";
     *    polarity      = PROVIDED;
     *  }
     * </pre> 
     *
     * として登録されている Serivce Provider のオブジェクト参照を探し、
     * Consumer にセットする。実際には、ConnectorProfile::properties に
     *
     * <pre>
     * NameValue = { "port.Manipulator.PA10_0": <Object reference> }
     * </pre>
     *
     * として登録されている NameValue を探し、そのオブジェクト参照を
     * Consumer にセットする。}
     * {@.en Retrieve information associated with Provider matches Consumer
     * owned by this port and set the object reference to Consumer.
     *
     * Now, Consumer is registered as the following:
     * <pre>
     *  PortInterfaceProfile
     *  {
     *    instance_name = "PA10_0";
     *    type_name     = "Manipulator";
     *    polarity      = REQUIRED;
     *  }
     * </pre>
     * Find the object reference of Serivce Provider that is registered as
     * the following of other ports:
     * <pre>
     *  PortInterfaceProfile
     *  {
     *    instance_name = "PA10_0";
     *    type_name     = "Manipulator";
     *    polarity      = PROVIDED;
     *  }
     * </pre> 
     * and set to Consumer.
     * In fact, find NameValue that is registered as the following to 
     * ConnectorProfile::properties:
     * <pre>
     * NameValue = { "port.Manipulator.PA10_0": <Object reference> }
     * </pre>
     * and set the object reference to Consumer.}
     * </p>
     *
     * @param connector_profile 
     *   {@.ja コネクタプロファイル}
     *   {@.en Connector profile}
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     *
     */
    protected ReturnCode_t 
    subscribeInterfaces(final ConnectorProfileHolder connector_profile) {

        rtcout.println(rtcout.TRACE, "subscribeInterfaces()");
        final NVListHolder nv 
            = new NVListHolder(connector_profile.value.properties);

        ORB orb = ORBUtil.getOrb();

        boolean strict = false; // default is "best_effort"
        int index = NVUtil.find_index(nv, "port.connection.strictness");
        if (index >=  0) {
	    Any anyVal = nv.value[index].value;
            String strictness = null;
	    if (anyVal.type().kind() == TCKind.tk_wstring) {
		strictness = anyVal.extract_wstring();
	    }
	    else if (anyVal.type().kind() == TCKind.tk_string) {
		strictness = anyVal.extract_string();
	    }
	    else {
		strictness = anyVal.extract_Value().toString();
	    }
            if (strictness.equals("best_effort")) { 
                strict = false; 
            }
            else if (strictness.equals("strict")) { 
                strict = true; 
            }
            rtcout.println(rtcout.DEBUG, 
                "Connetion strictness is: "+ strictness);
        }

	Iterator it = m_consumers.iterator();
	while(it.hasNext()) {
            StringHolder ior = new StringHolder();
            CorbaConsumerHolder cons = (CorbaConsumerHolder)it.next();
            if (findProvider(nv, cons, ior)) { 
                setObject(ior.value, cons);
                continue; 
            }

            if (findProviderOld(nv, cons, ior)) { 
                setObject(ior.value, cons);
                continue; 
            }

            // never come here without error
            // if strict connection option is set, error is returned.
            if (strict) {
                rtcout.println(rtcout.ERROR, "subscribeInterfaces() failed.");
                return ReturnCode_t.RTC_ERROR; 
            }
            
        }
        rtcout.println(rtcout.TRACE, 
            "subscribeInterfaces() successfully finished.");

        return ReturnCode_t.RTC_OK;
    }
    
    /**
     * {@.ja Interface への接続を解除する}
     * {@.en Unsubscribe interfaces}
     *
     * <p>
     * {@.ja 与えられた ConnectorProfile に関連する Consumer にセットされた
     * すべての Object を解放し接続を解除する。}
     * {@.en Release all Objects that was set in Consumer associated with the 
     * given ConnectorProfile.}
     *
     * @param connector_profile 
     *   {@.ja コネクタプロファイルオブジェクト}
     *   {@.en Connector profile}
     *
     *
     */
    protected void 
    unsubscribeInterfaces(final ConnectorProfile connector_profile) {
        
        rtcout.println(rtcout.TRACE, "unsubscribeInterfaces()");

        final NVListHolder nv = new NVListHolder(connector_profile.properties);
        rtcout.println(rtcout.DEBUG, NVUtil.toString(nv));

	Iterator it = m_consumers.iterator();
	while(it.hasNext()) {
            StringHolder ior = new StringHolder();
            CorbaConsumerHolder cons = (CorbaConsumerHolder)it.next();
            if (findProvider(nv, cons, ior)) { 
                rtcout.println(rtcout.DEBUG, "Correspoinding consumer found.");
                releaseObject(ior.value, cons);
                continue; 
            }

            if (findProviderOld(nv, cons, ior)) { 
                rtcout.println(rtcout.DEBUG, "Correspoinding consumer found.");
                releaseObject(ior.value, cons);
                continue; 
            }

            
        }

        CORBA_SeqUtil.for_each(nv, new unsubscribe(this.m_consumers));
        connector_profile.properties = nv.value;
    }
    
    /**
     * {@.ja Provider の情報を格納する構造体}
     * {@.en The structure to be stored Provider information.}
     *
     * <p>
     * {@.ja CORBA Provider のホルダクラス}
     * {@.en CORBA Provider holder class}
     * </p>
     * 
     */
    private class CorbaProviderHolder {
        public CorbaProviderHolder(final String type_name,
                          final String instance_name,
                          Servant servant){
            m_typeName = type_name;
            m_instanceName = instance_name;
            m_servant = servant;
            m_ior = new String();
            try {
                m_oid = _default_POA().servant_to_id(servant);
            }
            catch (Exception e) {
                rtcout.println(rtcout.WARN, 
                    "Exception caught."+e.toString());
            }
           try {
                _default_POA().activate_object_with_id(m_oid,servant);
            }
            catch (org.omg.PortableServer.POAPackage.ServantAlreadyActive e) {
                ; // do nothing
            }
            catch (org.omg.PortableServer.POAPackage.ObjectAlreadyActive e) {
                ; // do nothing
            }
            catch (org.omg.PortableServer.POAPackage.WrongPolicy e) {
                ; // do nothing
            }
            org.omg.CORBA.Object obj = null; 
            try {
                obj = _default_POA().id_to_reference(m_oid);
            }
            catch (org.omg.PortableServer.POAPackage.ObjectNotActive e) {
                ; // do nothing
            }
            catch ( org.omg.PortableServer.POAPackage.WrongPolicy e){
                ; // do nothing
            }

            StringBuffer key = new StringBuffer("port");
            key.append(".").append(type_name).append(".").append(instance_name);

            ORB orb = ORBUtil.getOrb();
            String ior = orb.object_to_string(obj);
            m_ior = ior;
            deactivate();

        }

        public String instanceName() { 
            return m_instanceName;
        }
        public String typeName() {  
            return m_typeName;
        }
        public String ior() {
            return m_ior;
        }
        public String descriptor() {
            return m_typeName + "." + m_instanceName; 
        }
    
        public void activate() {
           try {
                _default_POA().activate_object_with_id(m_oid,m_servant);
            }
            catch (org.omg.PortableServer.POAPackage.ServantAlreadyActive e) {
                ; // do nothing
            }
            catch (org.omg.PortableServer.POAPackage.ObjectAlreadyActive e) {
                ; // do nothing
            }
            catch ( org.omg.PortableServer.POAPackage.WrongPolicy e){
                ; // do nothing
            }
        }
        public void deactivate() {
           try {
		_default_POA().deactivate_object(m_oid);
            }
            catch (org.omg.PortableServer.POAPackage.ObjectNotActive e) {
                ; // do nothing
            }
            catch ( org.omg.PortableServer.POAPackage.WrongPolicy e){
                ; // do nothing
            }
        }
        private String m_typeName;
        private String m_instanceName;
        private Servant m_servant;
        private byte[] m_oid;
        private String m_ior;
    };

    /**
     * {@.ja Provider の情報を格納する vector}
     * {@.en vector to stored Providers' information}
     */
    Vector<CorbaProviderHolder> m_providers = new Vector<CorbaProviderHolder>();

    /**
     * {@.ja Consumer の情報を格納する構造体}
     * {@.en @brief The structure to be stored Consumer information.}
     */
    private class CorbaConsumerHolder {
        public CorbaConsumerHolder(final String type_name,
                          final String instance_name,
                          CorbaConsumerBase consumer) {
            m_typeName = type_name;
            m_instanceName = instance_name;
            m_consumer = consumer;
            m_ior = new String();
        }
        public String instanceName() { 
            return m_instanceName; 
        }
        public String typeName() { 
            return m_typeName; 
        }
        public String descriptor() { 
            return m_typeName + "." + m_instanceName; 
        }

        public boolean setObject(final String ior) {
            m_ior = ior;
            ORB orb = ORBUtil.getOrb();
	    org.omg.CORBA.Object obj = orb.string_to_object(ior);
	    if (obj == null) {
		rtcout.println(rtcout.ERROR, 
                    "Extracted object is nul reference");
                return false;
	    }

            return m_consumer.setObject(obj);
        }
        public void releaseObject() {
            m_consumer.releaseObject();
        }
        public final String getIor() {
            return m_ior;
        }

        private String m_typeName;
        private String m_instanceName;
        private CorbaConsumerBase m_consumer;
        private String m_ior;
    };
    Vector<CorbaConsumerHolder> m_consumers = new Vector<CorbaConsumerHolder>();

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
                        org.omg.CORBA.Object obj = ObjectHelper.extract(nv.value);
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
    
    // functors
    /**
     * {@.ja Consumer のオブジェクトを解放するための Functor}
     * {@.en Functor to release Consumer's object}
     */
    private class unsubscribe implements operatorFunc {
        
        public unsubscribe(Vector<CorbaConsumerHolder> consumers) {
            m_consumers = consumers;
        }
        
        public void operator(java.lang.Object elem) {

            operator((NameValue) elem);
        }
        
        public void operator(NameValue nv) {
            
	    Iterator it = m_consumers.iterator();
	    while(it.hasNext()) {
                CorbaConsumerHolder consumer = (CorbaConsumerHolder)it.next();
                String  name = nv.name;
                if (consumer.descriptor().equals(nv.name)) {
                    consumer.releaseObject();
                }
            }
        }
/*
        private Vector<Consumer> m_cons; // コンストラクタで必ず初期化されるので、ここではインスタンス生成しない。
        private int m_len;
*/
        public Vector<CorbaConsumerHolder> m_consumers;
    }



    /**
     * <p>Logging用フォーマットオブジェクト</p>
     */
    protected Logbuf rtcout;

    /**
     * <p> Properties </p>
     */
    protected Properties m_properties = new Properties();

    /**
     * {@.ja Consumer に合致する Provider を NVList の中から見つける}
     * {@.en Find out a provider corresponding to the consumer from NVList}
     *
     * <p>
     * {@.ja NVList 中から CorbaConsumerHolder に保持されている Consumer に合
     * 致するキーを持つ Provider を見つけ、IOR を抽出しナローイングして
     * Consumer にセットする。対応するキーが存在しない、IOR が見つから
     * ない、ナローイングに失敗した場合、false を返す。}
     * {@.en This function finds out a Provider with the key that is matched
     * with Cosumer's name in the CorbaConsumerHolder, extracts IOR
     * and performs narrowing into the Consumer and set it to the
     * Consumer. False is returned when there is no corresponding key
     * and IOR and the narrowing failed.}
     * </p>
     *
     * @param nv 
     *   {@.ja Provider が含まれている ConnectorProfile::properties の NVList}
     *   {@.en NVlist of ConnectorProfile::properties that includes Provider}
     * @param cons 
     *   {@.ja Provider と対応する Consumer のホルダ}
     *   {@.en a Consumer holder to be matched with a Provider}
     * @param iorstr 
     *   {@.ja 見つかったIOR文字列を格納する変数}
     *   {@.en variable which is set IOR string}
     * 
     * @retrun 
     *   {@.ja Consumer に対応する Provider が見つからない場合 false}
     *   {@.en false is returned if there is no provider for the consumer}
     *
     */
    private 
    boolean findProvider(final NVListHolder nv, CorbaConsumerHolder cons,
                        StringHolder iorstr) {
        // new consumer interface descriptor
        StringBuffer strbuff = new StringBuffer(m_profile.name);
        strbuff.insert(m_ownerInstanceName.length(), ".port");
        strbuff.append(".required." + cons.descriptor());
        String newdesc = strbuff.substring(0);
  
        // find a NameValue of the consumer
        int  cons_index = NVUtil.find_index(nv, newdesc);
        if (cons_index < 0) { 
            return false; 
        }

        String provider = null;
        if (nv.value[cons_index].value.type().kind() == TCKind.tk_wstring) {
            provider = nv.value[cons_index].value.extract_wstring();
        }
        else if (nv.value[cons_index].value.type().kind() == TCKind.tk_string) {
            provider = nv.value[cons_index].value.extract_string();
        }
        else {
            provider = nv.value[cons_index].value.extract_Value().toString();
        }
    
        // find a NameValue of the provider
        int prov_index = NVUtil.find_index(nv, provider);
        if (prov_index < 0) { 
            return false; 
        }

        String ior = null;
        if (nv.value[prov_index].value.type().kind() == TCKind.tk_wstring) {
            ior = nv.value[prov_index].value.extract_wstring();
        }
        else if (nv.value[prov_index].value.type().kind() == TCKind.tk_string) {
            ior = nv.value[prov_index].value.extract_string();
        }
        else {
            ior = nv.value[prov_index].value.extract_Value().toString();
        }
 
        iorstr.value = ior;
        rtcout.println(rtcout.TRACE, 
                    "interface matched with new descriptor:"+newdesc);
  
        return true;
    }

    /**
     * {@.ja Consumer に合致する Provider を NVList の中から見つける}
     * {@.en Find out a provider corresponding to the consumer from NVList}
     *
     * <p>
     * {@.ja この関数は、古いバージョンの互換性のための関数である。
     *
     * NVList 中から CorbaConsumerHolder に保持されている Consumer に合
     * 致するキーを持つ Provider を見つけ、IOR を抽出しナローイングして
     * Consumer にセットする。対応するキーが存在しない、IOR が見つから
     * ない、ナローイングに失敗した場合、false を返す。}
     * {@.en This function is for the old version's compatibility.
     *
     * This function finds out a Provider with the key that is matched
     * with Cosumer's name in the CorbaConsumerHolder, extracts IOR
     * and performs narrowing into the Consumer and set it to the
     * Consumer. False is returned when there is no corresponding key
     * and IOR and the narrowing failed.}
     *
     * </p> 
     * @param nv 
     *   {@.ja Provider が含まれている ConnectorProfile::properties の NVList}
     *   {@.en NVlist of ConnectorProfile::properties that includes Provider}
     * @param cons 
     *   {@.ja Provider と対応する Consumer のホルダ}
     *   {@.en a Consumer holder to be matched with a Provider}
     * 
     * @retrun 
     *   {@.ja Consumer に対応する Provider が見つからない場合 false}
     *   {@.en false is returned if there is no provider for the consumer}
     *
     */
    private 
    boolean findProviderOld(final NVListHolder nv, CorbaConsumerHolder cons,
                        StringHolder iorstr) {
        // old consumer interface descriptor
        String olddesc = "port."; 
        olddesc += cons.descriptor();

        // find a NameValue of the provider same as olddesc
        int cons_index = NVUtil.find_index(nv, olddesc);
        if (cons_index < 0) { 
            return false; 
        }

        String ior = null;
        if (nv.value[cons_index].value.type().kind() == TCKind.tk_wstring) {
            ior = nv.value[cons_index].value.extract_wstring();
        }
        else if (nv.value[cons_index].value.type().kind() == TCKind.tk_string) {
            ior = nv.value[cons_index].value.extract_string();
        }
        else {
            ior = nv.value[cons_index].value.extract_Value().toString();
        }
 
        iorstr.value = ior;
        rtcout.println(rtcout.INFO, 
                    "interface matched with old descriptor:"+olddesc);

        return true;
    }

    /**
     * {@.ja Consumer に IOR をセットする}
     * {@.en Setting IOR to Consumer}
     *
     * <p>
     * {@.ja IOR をナローイングしてConsumer にセットする。ナローイングに失敗
     * した場合、false を返す。ただし、IOR文字列が、nullまたはnilの場合、
     * オブジェクトに何もセットせずに true を返す。}
     * {@.en This function performs narrowing into the Consumer and set it to 
     * the Consumer. False is returned when the narrowing failed. But, if IOR
     * string is "null" or "nil", this function returns true.}
     *
     * @param ior 
     *   {@.ja セットする IOR 文字列}
     *   {@.en ior IOR string}
     * @param cons 
     *   {@.ja Consumer のホルダ}
     *   {@.en Consumer holder}
     * 
     * @retrun boolean 
     *   {@.ja Consumer へのナローイングに失敗した場合 false}
     *   {@.en false if narrowing failed.}
     *
     *
     */
    private boolean setObject(final String ior, CorbaConsumerHolder cons){

        // if ior string is "null" or "nil", ignore it.
        if (ior.equals("null")) { 
            return true; 
        }
        if (ior.equals("nil")) { 
            return true; 
        }
        // IOR should be started by "IOR:"
        if (ior.indexOf("IOR:") != 0) { 
            return false; 
        }
        // set IOR to the consumer
        if (!cons.setObject(ior)) {
            rtcout.println(rtcout.ERROR, "Cannot narrow reference");
            return false;
        }
        if(!cons.setObject(ior)){
            rtcout.println(rtcout.ERROR, "Cannot narrow reference");
            return false;
        }
        rtcout.println(rtcout.TRACE, "setObject() done");
        return true;
    }
    /**
     * {@.ja Consumer のオブジェクトをリリースする}
     * {@.en Releasing Consumer Object}
     *
     * <p>
     * {@.ja Consumer にセットされた参照をリリースする。ConsumerのIORが与えら
     * れたIOR文字列と異なる場合、falseを返す。}
     * {@.en This function releases object reference of Consumer. If the
     * given IOR string is different from Consumer's IOR string, it
     * returns false.}
     *
     * @param ior 
     *   {@.ja セットする IOR 文字列}
     *   {@.en IOR string}
     * @param cons 
     *   {@.ja Consumer のホルダ}
     *   {@.en Consumer holder}
     * 
     * @retrun 
     *   {@.ja ConsumerのIORが与えられたIOR文字列と異なる場合、falseを返す。}
     *   {@.en False if IOR and Consumer's IOR are different}
     * 
     *
     */
    private boolean releaseObject(final String ior, CorbaConsumerHolder cons){
        if (ior == cons.getIor()) {
            cons.releaseObject();
            rtcout.println(rtcout.DEBUG, 
                                "Consumer "+cons.descriptor()+" released.");
            return true;
        }
        rtcout.println(rtcout.WARN, 
                        "IORs between Consumer and Connector are different.");
        return false;
    }
}
