package jp.go.aist.rtm.RTC;

import java.lang.Exception;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;

import _SDOPackage.InvalidParameter;
import _SDOPackage.NVListHolder;
import _SDOPackage.ServiceProfile;
import _SDOPackage.ServiceProfileListHolder;
import _SDOPackage.SDOService;
import _SDOPackage.SDOServiceHolder;

import jp.go.aist.rtm.RTC.log.Logbuf;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.StringUtil;

  /**
   * {@.ja SDO service 管理クラス}
   * {@.en SDO service administration class}
   * <p>
   * {@.ja
   * このクラスは、SDO Service を管理するためのクラスである。SDO
   * Service は OMG SDO Specification において定義されている、SDOが特定
   * の機能のために提供また要求するサービスの一つである。詳細は仕様にお
   * いて定義されていないが、本クラスでは以下のように振る舞うサービスで
   * あるものとし、これらを管理するためのクラスが本クラスである。
   *
   * SDO Service においては、SDO/RTCに所有され、ある種のサービスを提供
   * するものを SDO Service Provider、他のSDO/RTCやアプリケーションが提
   * 供するサービスオブジェクトの参照を受け取り、それらの機能を利用する
   * ものを、SDO Service Consumer と呼ぶ。
   *
   * SDO Service Provider は他のアプリケーションから呼ばれ、SDO/RTC内部
   * の機能にアクセスするために用いられる。他のSDO/RTCまたはアプリケー
   * ションは、
   *
   * - SDO::get_service_profiles ()
   * - SDO::get_service_profile (in UniqueIdentifier id)
   * - SDO::get_sdo_service (in UniqueIdentifier id) 
   *
   * のいずれかのオペレーションにより、ServiceProfile または SDO
   * Service の参照を取得し、機能を利用するためのオペレーションを呼び出
   * す。他のSDO/RTCまたはアプリケーション上での参照の破棄は任意のタイ
   * ミングで行われ、サービス提供側では、どこからどれだけ参照されている
   * かは知ることはできない。一方で、SDO/RTC側も、任意のタイミングでサー
   * ビスの提供を停止することもできるため、サービスの利用側では、常にい
   * サービスが利用できるとは限らないものとしてサービスオペレーションを
   * 呼び出す必要がある。
   *
   * 一方、SDO Service Consumer は当該SDO/RTC以外のSDO/RTCまたはアプリ
   * ケーションがサービスの実体を持ち、当該SDO/RTCにオブジェクト参照を
   * 含むプロファイルを与えることで、SDO/RTC側からサービスオペレーショ
   * ンが呼ばれ外部のSDO/RTCまたはアプリケーションが提供する機能を利用
   * できる。また、オブザーバ的なオブジェクトを与えることで、SDO/RTC側
   * からのコールバックを実現するためにも利用することができる。コンシュー
   * マは、プロバイダとは異なり、SDO Configurationインターフェースから
   * 追加、削除が行われる。関連するオペレーションは以下のとおりである。
   *
   * - Configuration::add_service_profile (in ServiceProfile sProfile)
   * - Configuration::remove_service_profile (in UniqueIdentifier id)
   *
   * 外部のSDO/RTCまたはアプリケーションは、自身が持つSDO Servcie
   * Provider の参照をIDおよびinterface type、プロパティとともに
   * ServcieProfile にセットしたうえで、add_service_profile() の引数と
   * して与えることで、当該SDO/RTCにサービスを与える。この際、IDはUUID
   * など一意なIDでなければならない。また、削除する際にはIDにより対象と
   * するServiceProfileを探索するため、サービス提供側では削除時までIDを
   * 保持しておかなければならない。}
   *
   *
   */
public class  SdoServiceAdmin {
    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     *
     */
    public SdoServiceAdmin(RTObject_impl rtobj) {
        m_rtobj = rtobj;
        m_allConsumerEnabled = true;
        rtcout = new Logbuf("SdoServiceAdmin");
        rtcout.println(Logbuf.TRACE, "SdoServiceAdmin.SdoServiceAdmin(" + rtobj.getProperties().getProperty("instance_name") + ")");

        Properties prop = m_rtobj.getProperties();
        //------------------------------------------------------------
        // SDO service provider
        String[] enabledProviderTypes 
        = prop.getProperty("sdo.service.provider.enabled_services").split(",");
        rtcout.println(Logbuf.DEBUG,"sdo.service.provider.enabled_services:"+
                   prop.getProperty("sdo.service.provider.enabled_services"));

        Set availableProviderTypes 
          = SdoServiceProviderFactory.instance().getIdentifiers();
        prop.setProperty("sdo.service.provider.available_services", StringUtil.flatten(availableProviderTypes));
        rtcout.println(Logbuf.DEBUG,"sdo.service.provider.available_services:"+
                   prop.getProperty("sdo.service.provider.available_services"));

    
        // If types include '[Aa][Ll][Ll]', all types enabled in this RTC
        Set activeProviderTypes = new HashSet();
        for (int ic=0; ic < enabledProviderTypes.length; ++ic) {
            String tmp = enabledProviderTypes[ic];
            if(tmp.toLowerCase().equals("all")){
                activeProviderTypes = availableProviderTypes;
                rtcout.println(Logbuf.DEBUG,"sdo.service.provider.enabled_services: ALL");
                break;
            }
            for (Iterator jc=availableProviderTypes.iterator(); jc.hasNext();) {
                String str = (String)jc.next();
                if (str.equals(tmp)) {
                    activeProviderTypes.add(str);
                }
            }
        }
    
        SdoServiceProviderFactory<SdoServiceProviderBase,String> factory = SdoServiceProviderFactory.instance();
        for (Iterator ic=activeProviderTypes.iterator(); ic.hasNext();) {
            String str = (String)ic.next();
            SdoServiceProviderBase svc
              = factory.createObject(str);
        
            ServiceProfile prof = new ServiceProfile();
	    prof.id = str;
            prof.interface_type = str;
            prof.service = (SDOService)svc;
            String propkey = ifrToKey(str);
            NVListHolder nvholder = 
                new NVListHolder(prof.properties);
            NVUtil.copyFromProperties(nvholder, prop.getNode(propkey));
            prof.properties = nvholder.value;

            svc.init(rtobj, prof);
            m_providers.add(svc);
        }

        //------------------------------------------------------------
        // SDO service consumer
        // getting consumer types from RTC's properties

        String constypes = prop.getProperty("sdo.service.consumer.enabled_services");
        m_consumerTypes = Arrays.asList(constypes.split(","));
        rtcout.println(Logbuf.DEBUG,"sdo.service.consumer.enabled_services:"+constypes);

        prop.setProperty("sdo.service.consumer.available_services",
          StringUtil.flatten(SdoServiceConsumerFactory.instance().getIdentifiers()));
        rtcout.println(Logbuf.DEBUG,"sdo.service.consumer.available_services:"+ prop.getProperty("sdo.service.consumer.available_services"));

        // If types include '[Aa][Ll][Ll]', all types enabled in this RTC
        for (Iterator ic=m_consumerTypes.iterator(); ic.hasNext();) {
            String tmp = (String)ic.next();
            if(tmp.toLowerCase().equals("all")){
                m_allConsumerEnabled = true;
                rtcout.println(Logbuf.DEBUG,"sdo.service.consumer.enabled_services: ALL");
            }
        }
    }

    /**
     * {@.ja 仮想デストラクタ}
     * {@.en Virtual destractor}
     */
    //public ~SdoServiceAdmin();
    
    /**
     *
     * {@.ja SDO Service Provider の ServiceProfileList を取得する}
     * {@.en Get ServiceProfileList of SDO Service Provider}
     *
     */
    public ServiceProfile[] getServiceProviderProfiles() {
        ServiceProfileListHolder prof
          = new ServiceProfileListHolder();
        ServiceProfileListHolder prof2
          = new ServiceProfileListHolder();
        synchronized (m_provider_mutex) {
            for(Iterator ic=m_providers.iterator();ic.hasNext();){
                SdoServiceProviderBase sspb = (SdoServiceProviderBase)ic.next();
                CORBA_SeqUtil.push_back(prof2, sspb.getProfile());
            }
        }
        return prof.value;
    }

    /**
     * {@.ja SDO Service Provider の ServiceProfile を取得する}
     * {@.en Get ServiceProfile of an SDO Service Provider}
     * <p>
     * {@.ja id で指定されたIFR IDを持つSDO Service Provider の
     * ServiceProfile を取得する。id が NULL ポインタの場合、指定された
     * id に該当するServiceProfile が存在しない場合、InvalidParameter
     * 例外が送出される。}
     * {@.en This operation returnes ServiceProfile of an SDO Service
     * Provider which has the specified id. If the specified id is
     * NULL pointer or the specified id does not exist in the
     * ServiceProfile list, InvalidParameter exception will be thrown.}
     *
     * @param id 
     *   {@.ja SDO Service provider の IFR ID}
     *   {@.en IFR ID of an SDO Service provider}
     * @return 
     *   {@.ja 指定された id を持つ ServiceProfile}
     *   {@.en ServiceProfile which has the specified id}
     */
    public ServiceProfile getServiceProviderProfile(final String id) throws InvalidParameter {
        String idstr = id;
        synchronized (m_provider_mutex) {
            for(Iterator ic=m_providers.iterator();ic.hasNext();){
                SdoServiceProviderBase sspb = (SdoServiceProviderBase)ic.next();
                if (idstr.equals(sspb.getProfile().id)) {
                    return sspb.getProfile();
                }
            }
        }
        throw new InvalidParameter();
        //return new ServiceProfile();
    }

    /**
     * {@.ja SDO Service Provider の Service を取得する}
     * {@.en Get ServiceProfile of an SDO Service}
     * <p>
     * {@.ja id で指定されたIFR IDを持つSDO Service のオブジェクトリファレン
     * ス を取得する。id が NULL ポインタの場合、指定された id に該当す
     * るServiceProfile が存在しない場合、InvalidParameter 例外が送出さ
     * れる。}
     * {@.en This operation returnes an object reference of an SDO Service
     * Provider which has the specified id. If the specified id is
     * NULL pointer or the specified id does not exist in the
     * ServiceProfile list, InvalidParameter exception will be thrown.}
     *
     * @param id 
     *   {@.ja SDO Service provider の IFR ID}
     *   {@.en IFR ID of an SDO Service provider}
     * @return 
     *   {@.ja 指定された id を持つ SDO Service のオブジェクトリファレンス}
     *   {@.en an SDO Service reference which has the specified id}
     *
     */
    public SDOService getServiceProvider(final String id) {
        try{
            ServiceProfile prof;
            prof = getServiceProviderProfile(id);
            SDOServiceHolder sdo 
            = new SDOServiceHolder(prof.service);
            return sdo.value;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * {@.ja SDO service provider をセットする}
     * {@.en Set a SDO service provider}
     */
    public boolean addSdoServiceProvider(final ServiceProfile prof,
                               SdoServiceProviderBase provider) {
        rtcout.println(Logbuf.TRACE,"SdoServiceAdmin.addSdoServiceProvider(if="+prof.interface_type+")");
        synchronized (m_provider_mutex) {
            String id = prof.id;
            for(Iterator ic=m_providers.iterator();ic.hasNext();){
                SdoServiceProviderBase sspb = (SdoServiceProviderBase)ic.next();
                if (id.equals(sspb.getProfile().id)) {
                    rtcout.println(Logbuf.ERROR,"SDO service(id="+prof.id+", ifr="+prof.interface_type+") already exists");
                    return false;
                }
            }
            m_providers.add(provider);
        }
        return true;
    }

    /**
     * {@.ja SDO service provider を削除する}
     * {@.en Remove a SDO service provider}
     */
    public boolean removeSdoServiceProvider(final String id) {
        rtcout.println(Logbuf.TRACE,"removeSdoServiceProvider("+id+")");
        synchronized (m_provider_mutex) {
            String strid = id;
            for(Iterator it=m_providers.iterator();it.hasNext();){
                SdoServiceProviderBase sspb = (SdoServiceProviderBase)it.next();
                if (strid.equals(sspb.getProfile().id)) {
                    sspb.finalize();
                    SdoServiceProviderFactory 
                    factory = SdoServiceProviderFactory.instance();
                    factory.deleteObject(sspb);
                    m_providers.remove(sspb);
                    rtcout.println(Logbuf.INFO,"SDO service provider has been deleted: "+id);
                    return true;
                }
            }
            rtcout.println(Logbuf.WARN,"Specified SDO service provider not found: "+id);
        }
        return false;
    
    }

    /**
     * {@.ja Service Consumer を追加する}
     * {@.en Add Service Consumer}
     */
    public boolean addSdoServiceConsumer(final ServiceProfile sProfile){
        synchronized (m_provider_mutex) {
            rtcout.println(Logbuf.TRACE,"addSdoServiceConsumer(IFR = "+sProfile.interface_type+")");
            // Not supported consumer type -> error return
            if (!isEnabledConsumerType(sProfile))  { return false; }
            if (!isExistingConsumerType(sProfile)) { return false; }
            rtcout.println(Logbuf.DEBUG,"Valid SDO service required");
            if (sProfile.id.length() < 1)   {
                rtcout.println(Logbuf.WARN,"No id specified. It should be given by clients.");
                return false;
            }
            rtcout.println(Logbuf.DEBUG,"Valid ID specified");
        }
        { // re-initialization
          String id=sProfile.id;
          for(Iterator it=m_consumers.iterator();it.hasNext();){
              SdoServiceConsumerBase sscb = (SdoServiceConsumerBase)it.next();
              if (id.equals(sscb.getProfile().id)) {
                  rtcout.println(Logbuf.INFO,"Existing consumer is reinitilized.");
                  NVListHolder nvlist = new NVListHolder(sProfile.properties);
                  rtcout.println(Logbuf.DEBUG,"Propeteis are: "+
                             NVUtil.toString(nvlist));
                  return sscb.reinit(sProfile);
              }
          }
        }
        rtcout.println(Logbuf.DEBUG,"SDO service properly initialized.");

        // new pofile
        SdoServiceConsumerFactory<SdoServiceConsumerBase,String>
          factory = SdoServiceConsumerFactory.instance();
        final String ctype = sProfile.interface_type;
        if (ctype.length() < 1) { return false; }
        SdoServiceConsumerBase consumer = factory.createObject(ctype);
        if (consumer == null) {
            rtcout.println(Logbuf.ERROR,"Hmm... consumer must be created.");
            return false; 
        }
        rtcout.println(Logbuf.DEBUG,"An SDO service consumer created.");

        // initialize
        if (!consumer.init(m_rtobj, sProfile)) {
            rtcout.println(Logbuf.WARN,"SDO service initialization was failed.");
            rtcout.println(Logbuf.DEBUG,"id:         "+sProfile.id);
            rtcout.println(Logbuf.DEBUG,"IFR:        "+
                       sProfile.interface_type);
            NVListHolder nvlist = new NVListHolder(sProfile.properties);
            rtcout.println(Logbuf.DEBUG,"properties: "+
                       NVUtil.toString(nvlist));
            factory.deleteObject(consumer);
            rtcout.println(Logbuf.INFO,"SDO consumer was deleted by initialization failure");
            return false;
        }
        rtcout.println(Logbuf.DEBUG,"An SDO service consumer initialized.");
        rtcout.println(Logbuf.DEBUG,"id:         "+sProfile.id);
        rtcout.println(Logbuf.DEBUG,"IFR:        "+
                   sProfile.interface_type);
        NVListHolder nvlist = new NVListHolder(sProfile.properties);
        rtcout.println(Logbuf.DEBUG,"properties: "+
                   NVUtil.toString(nvlist));

        // store consumer
        m_consumers.add(consumer);
    
        return true;
    }    
    /**
     * {@.ja Service Consumer を削除する}
     * {@.en Remove Service Consumer}
     *
     */
    public boolean removeSdoServiceConsumer(final String id) {
        synchronized (m_consumer_mutex) {
            if (id.length() < 1) {
                rtcout.println(Logbuf.ERROR,"removeSdoServiceConsumer(): id is invalid.");
                return false;
            }
            rtcout.println(Logbuf.TRACE,"removeSdoServiceConsumer(id = "+id+")");

            String strid = id;
            for(Iterator it=m_consumers.iterator();it.hasNext();){
                SdoServiceConsumerBase sscb = (SdoServiceConsumerBase)it.next();
                if (strid.equals(sscb.getProfile().id)) {
                    sscb.finalize();
                    SdoServiceConsumerFactory
                        factory = SdoServiceConsumerFactory.instance();
                    factory.deleteObject(sscb);
                    m_consumers.remove(sscb);
                    rtcout.println(Logbuf.INFO,"SDO service has been deleted: "+id);
                    return true;
                }
            }      
        }
        rtcout.println(Logbuf.WARN,"Specified SDO consumer not found: "+id);
        return false;
    }
    
    /**
     * {@.ja 許可されたサービス型かどうか調べる}
     * {@.en If it is enabled service type}
     *
     */
    protected boolean isEnabledConsumerType(final ServiceProfile sProfile){
        if (m_allConsumerEnabled) { return true; }

        for (Iterator ic=m_consumerTypes.iterator(); ic.hasNext();) {
            String str = (String)ic.next();
            if (str.equals(sProfile.interface_type)) {
                rtcout.println(Logbuf.DEBUG,sProfile.interface_type+" is supported SDO service.");
                return true;
            }
        }
        rtcout.println(Logbuf.WARN,"Consumer type is not supported: "+
              sProfile.interface_type);
        return false;
    }

    /**
     * {@.ja 存在するサービス型かどうか調べる}
     * {@.en If it is existing service type}
     *
     */
    protected boolean isExistingConsumerType(final ServiceProfile sProfile){
        SdoServiceConsumerFactory factory=SdoServiceConsumerFactory.instance();
        Set consumerTypes = factory.getIdentifiers();
    
        for (Iterator ic=consumerTypes.iterator(); ic.hasNext();) {
            String str = (String)ic.next();
            if (str.equals(sProfile.interface_type)) {
                rtcout.println(Logbuf.DEBUG,sProfile.interface_type+" exists in the SDO service factory.");
                rtcout.println(Logbuf.PARANOID,"Available SDO serices in the factory: "+ StringUtil.flatten(consumerTypes));
                return true;
            }
        }
        rtcout.println(Logbuf.WARN,"No available SDO service in the factory: "+
              sProfile.interface_type);
        return false;
    }

    final String getUUID() {
        return UUID.randomUUID().toString();
    }

    private String ifrToKey(String ifr) {
        String[] ifrvstr = ifr.split(":");
        String str = ifrvstr[1].toLowerCase();
        str = str.replace(".", "_");
        str = str.replace("/", ".");
        return str;
    }
    private RTObject_impl m_rtobj;

    private List<String> m_consumerTypes = new ArrayList<String>();
    private boolean m_allConsumerEnabled;
    
    /**
     * {@.ja Lock 付き SDO ServiceProfileList}
     * {@.en SDO ServiceProfileList with mutex lock}
     */
    private Vector<SdoServiceProviderBase> m_providers = new Vector<SdoServiceProviderBase>();
    private String m_provider_mutex = new String();

    /**
     * {@.ja Lock 付き SDO ServiceProfileList}
     * {@.en SDO ServiceProfileList with mutex lock}
     */
    private Vector<SdoServiceConsumerBase> m_consumers = new Vector<SdoServiceConsumerBase>();
    private String m_consumer_mutex = new String();

    /**
     * {@.ja logger}
     * {@.en logger}
     */
    protected Logbuf rtcout;
}
