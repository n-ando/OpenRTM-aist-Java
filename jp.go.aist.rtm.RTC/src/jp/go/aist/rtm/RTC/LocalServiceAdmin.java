package jp.go.aist.rtm.RTC;

import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import jp.go.aist.rtm.RTC.log.Logbuf;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.StringUtil;

  /**
   * {@.ja LocalService 管理クラス}
   * {@.en SDO service administration class}
   *
   */
public class LocalServiceAdmin {
    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     * <p>
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     */
    private LocalServiceAdmin() {
        rtcout = new Logbuf("LocalServiceAdmin");
        rtcout.println(Logbuf.TRACE, "LocalServiceAdmin::LocalServiceAdmin()");
    }
    
    
    /**
     *
     */
    public static LocalServiceAdmin instance() {
        return _instance;
    }
    /**
     * {@.ja "all" 文字列探索Functor}
     * {@.en A functor to search "all"}
     */
    private class find_all {
        public boolean is_all(final String str) {
            String a = str;
            if(StringUtil.normalize(a).equals("all")){
                return true;
            }
            else {
                return false;
            }
        }
        public int find_if(String str[]) {
            int ic;
            for(ic=0;ic<str.length;++ic){
                if(this.is_all(str[ic])){
                    break;
                }
            }
            return ic;
        }
    };
    /**
     * {@.ja LocaServiceAdminの初期化}
     * {@.en Initialization of LocalServiceAdmin}
     *
     */
    public void init(Properties props) {
        rtcout.println(Logbuf.TRACE, "LocalServiceAdmin::init():");
        String str = new String();
        str = props._dump(str,props,0);
        rtcout.println(Logbuf.TRACE, str);
        String es = props.getProperty("enabled_services");
        String svcs[] = es.split(","); 
        find_all fa = new find_all();
        boolean all_enable = false;

        if(fa.find_if(svcs)!=svcs.length){
            rtcout.println(Logbuf.INFO,"All the local services are enabled.");
            all_enable = true;
        }
    
        LocalServiceFactory<LocalServiceBase,String> factory = LocalServiceFactory.instance();
        Set ids = factory.getIdentifiers();
        rtcout.println(Logbuf.DEBUG,"Available services: " + StringUtil.flatten(ids));

        Vector vsvcs = new Vector();
        for (int ic = 0; ic < svcs.length; ++ic) {
            vsvcs.add(svcs[ic]);
        }

        Iterator it  = ids.iterator();
        while (it.hasNext()) {
            String id_str = (String)it.next();
            if (all_enable || isEnabled(id_str, vsvcs)) {
                if (notExisting(id_str)) {
                    LocalServiceBase service = factory.createObject(id_str);
                    rtcout.println(Logbuf.DEBUG,"Service created: "+id_str);
                    Properties prop = props.getNode(id_str);
                    service.init(prop);
                    addLocalService(service);
                }
            }
        }
    }    
    /**
     * {@.ja LocalserviceAdmin の終了処理}
     * {@.en Finalization ofLocalServiceAdmin}
     *
     *
     */
    public void _finalize() {
        LocalServiceFactory factory = LocalServiceFactory.instance();
        Iterator it  = m_services.iterator();
        while (it.hasNext()) {
            LocalServiceBase lsb = (LocalServiceBase)it.next();
            lsb._finalize();
            factory.deleteObject(lsb);
        }
        m_services.clear();
    }

    
    /**
     * {@.ja LocalServiceProfileListの取得}
     * {@.en Getting LocalServiceProfileList}
     *
     */
    public LocalServiceProfile[] getServiceProfiles() {
        LocalServiceProfile[] profs = new LocalServiceProfile[m_services.size()];
        for (int ic=0; ic < m_services.size(); ++ic) {
            profs[ic] = m_services.get(ic).getProfile();
        }
        return profs;
    }
    
    /**
     * {@.ja LocalServiceProfile を取得する}
     * {@.en Get LocalServiceProfile of an LocalService}
     * <p>
     * {@.ja id で指定されたIDを持つLocalService の
     * LocalServiceProfile を取得する。id が NULL ポインタの場合、指定された
     * id に該当するServiceProfile が存在しない場合、falseを返す。}
     * {@.en This operation returns LocalServiceProfile of a LocalService
     * which has the specified id. If the specified id is
     * NULL pointer or the specified id does not exist in the
     * ServiceProfile list, false will be returned.}
     *
     * @param name 
     *   {@.ja LocalService の IFR ID}
     *   {@.en ID of an LocalService}
     * @param prof 
     * @return 
     *   {@.ja 指定された id を持つ LocalServiceProfile}
     *   {@.en LocalServiceProfile which has the specified id}
     *
     */
    public boolean getServiceProfile(String name,
                           LocalServiceProfile prof) {
        synchronized (m_services) {
            Iterator it  = m_services.iterator();
            while (it.hasNext()) {
                LocalServiceBase lsb = (LocalServiceBase)it.next();
                if (name == lsb.getProfile().name) {
                    prof = lsb.getProfile();
                    return true;
                }
            }
        }

        return false;
    }
    
    /**
     * {@.ja LocalService の Service を取得する}
     * {@.en Get a pointer of a LocalService}
     * <p>
     * {@.ja id で指定されたIDを持つLocalService のポインタを取得する。id が
     * NULL ポインタの場合、指定された id に該当するServiceProfile が存
     * 在しない場合、NULLを返す。}
     * {@.en This operation returnes a pointer to the LocalService
     * which has the specified id. If the specified id is
     * NULL pointer or the specified id does not exist in the
     * ServiceProfile list, NULL pointer will be returned.}
     *
     * @param id 
     *   {@.ja LocalService の ID}
     *   {@.en ID of a LocalService}
     * @return 
     *   {@.ja 指定された id を持つ LocalService のポインタ}
     *   {@.en a pointer which has the specified id}
     *
     */
    public LocalServiceBase getService(final String id) {
        Iterator it  = m_services.iterator();
        while (it.hasNext()) {
            LocalServiceBase lsb = (LocalServiceBase)it.next();
            if (lsb.getProfile().name == id) {
                return lsb;
            }
        }
        return null;
    }
    
    /**
     * {@.ja SDO service provider をセットする}
     * {@.en Set a SDO service provider}
     *
     */
    public boolean addLocalService(LocalServiceBase service) {
        if (service == null) {
            rtcout.println(Logbuf.ERROR,"Invalid argument: addLocalService(service == NULL)");
            return false;
        }
        rtcout.println(Logbuf.TRACE,"LocalServiceAdmin::addLocalService("+
                   service.getProfile().name+")");
        synchronized (m_services) {
            m_services.add(service);
        }
        return true;
    }
    
    /**
     * {@.ja LocalService を削除する}
     * {@.en Remove a LocalService}
     *
     *
     */
    public boolean removeLocalService(final String name) {
        rtcout.println(Logbuf.TRACE,"removeLocalService("+name+")");
        synchronized (m_services){
    
            Iterator it = m_services.iterator();
            while (it.hasNext()) {
                LocalServiceBase lsb = (LocalServiceBase)it.next();
                if (name == lsb.getProfile().name) {
                    lsb._finalize();
                    LocalServiceFactory
                      factory = LocalServiceFactory.instance();
                    factory.deleteObject(lsb);
                    m_services.remove(lsb);
                    rtcout.println(Logbuf.INFO,"SDO service  has been deleted: "+name);
                    return true;
                }
            }
            rtcout.println(Logbuf.WARN,"Specified SDO service  not found: "+name);
            return false;
        } 
    }
    
    /**
     * {@.ja 指定されたIDが有効かどうかチェックする}
     * {@.en Check if specified ID is enabled}
     */
    private boolean isEnabled(final String id, final  Vector<String> enabled) {
        boolean ret = enabled.contains(id);
        rtcout.println(Logbuf.DEBUG,"Local service "+id +" "+ ret +" enabled.");
        return ret;
    }
    
    /**
     * {@.ja 指定されたIDがすでに存在するかどうかチェックする}
     * {@.en Check if specified ID is existing}
     */
    private boolean notExisting(final String id) {
        synchronized (m_services) {
            Iterator it  = m_services.iterator();
            while (it.hasNext()) {
                LocalServiceBase lsb = (LocalServiceBase)it.next();
                if (lsb.getProfile().name == id) {
                    rtcout.println(Logbuf.WARN, "Local service "+id+" already exists.");
                    return false;
                }
            }
            rtcout.println(Logbuf.DEBUG,"Local service "+id+" does not exist.");
        }
        return true;
    }
    
    /**
     * {@.ja Lock 付き SDO ServiceProfileList}
     * {@.en SDO ServiceProfileList with mutex lock}
     */
    private Vector<LocalServiceBase> m_services = new Vector<LocalServiceBase>();
    private String m_services_mutex;
    
    /**
     * {@.ja logger}
     * {@.en logger}
     */
    private Logbuf rtcout;
    /**
     * {@.ja The only instance}
     * {@.en The only instance}
     */
    private static final LocalServiceAdmin _instance = new LocalServiceAdmin();
}

