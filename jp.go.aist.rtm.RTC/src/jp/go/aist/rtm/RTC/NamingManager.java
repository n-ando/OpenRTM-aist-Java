package jp.go.aist.rtm.RTC;

import java.util.Vector;
import java.util.logging.FileHandler;
import java.util.logging.ConsoleHandler;
import java.io.IOException;
import jp.go.aist.rtm.RTC.log.Logbuf;
import jp.go.aist.rtm.RTC.util.CallbackFunction;
import jp.go.aist.rtm.RTC.util.StringUtil;

/**
* <p>Naming Service管理クラスです。コンポーネントの登録・解除などを管理します。</p>
*/
public class NamingManager implements CallbackFunction {

    /**
     * {@.ja コンストラクタです。}
     * {@.en Constructor}
     * 
     * @param manager 
     *   {@.ja Managerオブジェクト}
     *   {@.en Manager object}
     */
    public NamingManager(Manager manager) {
        m_manager = manager;

        rtcout = new Logbuf("NamingManager");
    }

    /**
     * {@.ja NameServer の登録}
     * {@.en Regster the NameServer}
     *
     * <p>
     * {@.ja 指定した形式の NameServer を登録する。
     * 現在指定可能な形式は CORBA のみ。}
     * {@.en Register NameServer by specified format.
     * Currently. only CORBA can be specified.}
     *
     * @param method 
     *   {@.ja NamingService の形式}
     *   {@.en Format of NamingService}
     * @param name_server 
     *   {@.ja 登録する NameServer の名称}
     *   {@.en Name of NameServer for registration}
     *
     */
    public void registerNameServer(final String method, 
                                                final String name_server) {
        rtcout.println(rtcout.TRACE, 
            "NamingManager.registerNameServer(" 
            + method + ", " + name_server + ")");
        NamingBase name = createNamingObj(method, name_server);


        synchronized (m_names) {
            m_names.add(new Names(method, name_server, name));
        }
    }

    /**
     * {@.ja 指定したオブジェクトのNamingServiceへバインド}
     * {@.en Bind the specified objects to NamingService}
     * 
     * <p>
     * {@.ja 指定したオブジェクトを指定した名称で CORBA NamingService へバイ
     * ンドする。}
     * {@.en Bind the specified objects to CORBA NamingService 
     * by specified names.}
     * 
     * @param name 
     *   {@.ja バインド時の名称}
     *   {@.en Names at the binding}
     * @param rtobj 
     *   {@.ja バインド対象オブジェクト}
     *   {@.en The target objects for the binding}
     *
     * 
     */
    public void bindObject(final String name, final RTObject_impl rtobj) {
        rtcout.println(rtcout.TRACE, "NamingManager.bindObject(" + name + ")");
        synchronized (m_names) {
            int len = m_names.size();
            for(int intIdx=0; intIdx < len; ++intIdx ) {
                if( m_names.elementAt(intIdx).ns != null ) {
                    try{
                        m_names.elementAt(intIdx).ns.bindObject(name, rtobj);
                    }
                    catch(Exception ex){
                        m_names.elementAt(intIdx).ns = null;
                    }
                }
            }
            this.registerCompName(name, rtobj);
        }
    }

    /**
     * <p> bindObject </p> 
     *
     * @param name bind時の名称
     * @param mgr bind対象マネージャサーバント
     */
    /**
     * {@.ja 指定したManagerServantのNamingServiceへバインド}
     * {@.en Bind the specified ManagerServants to NamingService}
     *
     * <p> 
     * {@.ja 指定したManagerServantを指定した名称で CORBA NamingService へバ
     * インドする。}
     * {@.en Bind the specified ManagerServants to CORBA NamingService 
     * by specified names.}
     * 
     * @param name 
     *   {@.ja バインド時の名称}
     *   {@.en Names at the binding}
     * @param mgr 
     *   {@.ja バインド対象ManagerServant}
     *   {@.en The target ManagerServants for the binding}
     *
     *
     * 
     */
    public void bindObject(final String name, final ManagerServant mgr) {
        rtcout.println(rtcout.TRACE, "NamingManager.bindObject(" + name + ")");
        synchronized (m_names) {
            int len = m_names.size();
            for(int intIdx=0; intIdx < len; ++intIdx ) {
                if( m_names.elementAt(intIdx).ns != null ) {
                   try{
                        m_names.elementAt(intIdx).ns.bindObject(name, mgr);
                    }
                    catch(Exception ex){
                        m_names.elementAt(intIdx).ns = null;
                    }
                }
            }
            this.registerMgrName(name, mgr);
        }
    }

    /**
     * {@.ja NamingServer の情報の更新}
     * {@.en Update information of NamingServer}
     * 
     * <p>
     * {@.ja 設定されている NameServer 内に登録されているオブジェクトの情報を
     * 更新する。}
     * {@.en Update the object information registered 
     * in the specified NameServer.}
     * 
     *
     */
    public void update(){
        rtcout.println(rtcout.TRACE, "NamingManager.update()");
        
        boolean rebind = StringUtil.toBool(
                    m_manager.getConfig().getProperty("naming.update.rebind"), 
                    "YES", "NO", false);

        synchronized (m_names) {
            int len = m_names.size();
            for( int intIdx=0; intIdx < len; ++intIdx ) {
                if( m_names.elementAt(intIdx).ns == null ) {
                    rtcout.println(rtcout.DEBUG, "Retrying connection to " 
                                 + m_names.elementAt(intIdx).method + "/" +
                                 m_names.elementAt(intIdx).nsname);
                    retryConnection(m_names.elementAt(intIdx));

                }
                else {
                    try{
                        if( rebind ) {
                            bindCompsTo(m_names.elementAt(intIdx).ns);
                        }
                        if (!m_names.elementAt(intIdx).ns.isAlive()) {
                            rtcout.println(rtcout.INFO, 
                                 "Name server: " 
                                 + m_names.elementAt(intIdx).nsname + " (" 
                                 + m_names.elementAt(intIdx).method 
                                 + ") disappeared.");

                            m_names.elementAt(intIdx).ns = null;
                        }
                    }
                    catch(Exception ex){
                        rtcout.println(rtcout.INFO, 
                                 "Name server: " 
                                 + m_names.elementAt(intIdx).nsname + " (" 
                                 + m_names.elementAt(intIdx).method 
                                 + ") disappeared.");

                        m_names.elementAt(intIdx).ns = null;
                    }
                }
            }
        }
    }

    /**
     * {@.ja コンポネントをリバインドする}
     * {@.en Rebind the component to NameServer}
     * 
     * <p>
     * {@.ja ネームサーバと接続してコンポネントをリバインドする。}
     * {@.en Connect with the NameServer and rebind the component.}
     *
     * @param ns 
     *   {@.ja NameServer}
     *   {@.en  NameServer}
     * 
     */
    protected void retryConnection(Names ns){
        // recreate NamingObj
        NamingBase nsobj = null;
        try {
            nsobj = createNamingObj(ns.method, ns.nsname);
            if (nsobj != null) {// if succeed
                rtcout.println(rtcout.INFO, 
                                 "Connected to a name server: " 
                                 + ns.nsname + "/" 
                                 + ns.method );
                ns.ns = nsobj;
                bindCompsTo(nsobj); // rebind all comps to new NS
                return;
            }
            else {
                rtcout.println(rtcout.DEBUG, 
                                 "Name service: " + ns.method + "/" 
                                 + ns.nsname  +" still not available.");
            }
        }
        catch (Exception ex) {
            rtcout.println(rtcout.DEBUG, 
                                 "Name service: " + ns.method + "/" 
                                 + ns.nsname  +" disappeared again.");
            if (nsobj != null) {
                ns.ns = null;
            } 
        }
    }

    /**
     * <p>オブジェクトをNameServerからunbindします。</p>
     * 
     * @param name unbind対象オブジェクト名
     */
    public void unbindObject(final String name) {
        rtcout.println(rtcout.TRACE, "NamingManager.unbindObject(" + name + ")");
        //
        synchronized (m_names) {
            int len = m_names.size();
            for( int intIdx=0; intIdx < len; ++intIdx ) {
                if( m_names.elementAt(intIdx).ns != null ) {
                    m_names.elementAt(intIdx).ns.unbindObject(name);
                }
            }
            unregisterCompName(name);
            unregisterMgrName(name);
        }
    }

    /**
     * {@.ja 全てのオブジェクトをNamingServiceからアンバインド}
     * {@.en Unbind all objects from NamingService}
     *  
     * <p>
     * {@.ja 全てのオブジェクトを CORBA NamingService からアンバインドする。}
     * {@.en Unbind all objects from CORBA NamingService.}
     * 
     */
    protected void unbindAll() {
        rtcout.println(rtcout.TRACE, 
            "NamingManager.unbindAll(): m_compNames=" 
            + Integer.toString(m_compNames.size()) 
            + " m_mgrNames=" + Integer.toString(m_mgrNames.size()));

        synchronized (m_compNames) {
            Vector<String> names = new Vector<String>();
            for (int i=0, len=m_compNames.size(); i < len; ++i) {
                names.add(m_compNames.elementAt(i).name);
            }
            for (int i=0; i < names.size(); ++i) {
                unbindObject(names.elementAt(i));
            }
        }
        synchronized (m_mgrNames) {
            Vector<String> names = new Vector<String>();
            // unbindObject modifiy m_mgrNames
            for (int i=0, len=m_mgrNames.size(); i < len; ++i) {
                names.add(m_mgrNames.elementAt(i).name);
            }
            for (int i=0; i < names.size(); ++i) {
                unbindObject(names.elementAt(i));
            }
        }
    }

    /**
     * <p>NameServerに登録されているオブジェクトを取得します。</p>
     * 
     * @return 登録オブジェクトのリスト
     */
    protected synchronized Vector<RTObject_impl> getObjects() {
        Vector<RTObject_impl> comps = new Vector<RTObject_impl>();
        int len = m_compNames.size();
        for(int intIdx=0; intIdx < len; ++intIdx) {
            comps.add(m_compNames.elementAt(intIdx).rtobj);
        }
        return comps;
    }
    
    /**
     * {@.ja NameServer 管理用オブジェクトの生成}
     * {@.en Create objects for NameServer management}
     * 
     * <p>
     * {@.ja 指定した型のNameServer 管理用オブジェクトを生成する。}
     * {@.en Create objects of specified type for NameServer management.}
     *
     * @param method 
     *   {@.ja NamingService 形式}
     *   {@.en NamingService format}
     * @param name_server 
     *   {@.ja NameServer 名称}
     *   {@.en NameServer name}
     * 
     * @return 
     *   {@.ja 生成した NameServer オブジェクト}
     *   {@.en Created NameServer objects}
     * 
     *
     */
    protected NamingBase createNamingObj(final String method, 
                                            final String name_server) {
        rtcout.println(rtcout.TRACE, 
                    "createNamingObj(method = " 
                    + method + ", nameserver = )" + name_server);
        String m = method;
        if( m.endsWith("corba")) {
            try {
                NamingOnCorba nameb 
                    = new NamingOnCorba(m_manager.getORB(), name_server);
                NamingBase name = nameb;
                if( name == null ) return null;
                rtcout.println(rtcout.INFO, 
                    "NameServer connection succeeded: " 
                    + method + "/" + name_server);
                return name;
            } catch (Exception ex) {
                rtcout.println(rtcout.INFO, 
                    "NameServer connection failed: " 
                    + method +"/" + name_server);
                return null;
            }
        }
        return null;
    }

    /**
     * <p>オブジェクトをNameServerにbindします。</p>
     * 
     * @param ns bind対象オブジェクト
     */
    protected void bindCompsTo(NamingBase ns) {
        int len = m_compNames.size();
        for( int intIdx=0; intIdx < len; ++intIdx) {
            ns.bindObject(m_compNames.elementAt(intIdx).name, m_compNames.elementAt(intIdx).rtobj);
        }
    }

    /**
     * <p>コンポーネントを登録します。
     * 対象コンポーネントが既に登録済みの場合は何もしません。</p>
     * 
     * @param name bind時の名称
     * @param rtobj bind対象オブジェクト
     */
    protected void registerCompName(final String name, final RTObject_impl rtobj) {
        int len = m_compNames.size();
        for(int intIdx=0; intIdx < len; ++intIdx ) {
            if( m_compNames.elementAt(intIdx).name.equals(name) ) {
                m_compNames.elementAt(intIdx).rtobj = rtobj;
                return;
            }
        }
        m_compNames.add(new Comps(name, rtobj));
        return;
    }

    /**
     * <p>マネージャサーバントを登録します。
     * 対象マネージャサーバントが既に登録済みの場合は何もしません。</p>
     *
     * @param name bind時の名称
     * @param mgr bind対象マネージャサーバント
     */
    protected void registerMgrName(final String name, final ManagerServant mgr) {
        int len = m_mgrNames.size();
        for(int intIdx=0; intIdx < len; ++intIdx ) {
            if( m_mgrNames.elementAt(intIdx).name.equals(name) ) {
                m_mgrNames.elementAt(intIdx).mgr = mgr;
                return;
            }
        }
        m_mgrNames.add(new Mgr(name, mgr));
        return;
    }

    /**
     * <p>登録済みコンポーネントの登録を解除します。</p>
     * 
     * @param name 解除対象コンポーネントの名称
     */
    protected void unregisterCompName(final String name) {
        int len = m_compNames.size();
        for( int intIdx=0; intIdx < len; ++intIdx ) {
            if( m_compNames.elementAt(intIdx).name.equals(name)) {
                m_compNames.remove(m_compNames.elementAt(intIdx));
                return;
            }
        }
        return;
    }

    /**
     * <p>登録済みマネージャサーバントの登録を解除します。</p>
     * 
     * @param name 解除対象マネージャサーバントの名称
     */
    protected void unregisterMgrName(final String name) {
        int len = m_mgrNames.size();
        for( int intIdx=0; intIdx < len; ++intIdx ) {
            if( m_mgrNames.elementAt(intIdx).name.equals(name)) {
                m_mgrNames.remove(m_mgrNames.elementAt(intIdx));
                return;
            }
        }
        return;
    }

    /**
    * <p>Naming Serviceクラスです。</p>
    */
    protected class Names {
        /**
         * <p>コンストラクタです。</p>
         * 
         * @param meth NamingServerタイプ
         * @param name NamingServer名称
         * @param naming NameServerオブジェクト
         */
        public Names(final String meth, final String name, NamingBase naming) {
            method = meth;
            nsname = name;
            ns = naming;
        }

        /**
         * <p>NamingServerタイプ</p>
         */
        public String method;
        /**
         * <p>NamingServer名称</p>
         */
        public String nsname;
        /**
         * <p>NameServerオブジェクト</p>
         */
        public NamingBase ns;
    }
    /**
     * <p>登録されたNameServer</p>
     */
    protected Vector<Names> m_names = new Vector<Names>();
    /**
     * <p>Naming Service登録用コンポーネントクラスです。</p>
     */
    protected class Comps {
        /**
         * <p>コンストラクタです。</p>
         * 
         * @param n コンポーネント名称
         * @param obj 登録対象オブジェクト
         */
        public Comps(final String n, final RTObject_impl obj) {
            name = n;
            rtobj = obj;
        }
        /**
         * <p>コンポーネント名称</p>
         */
        public String name;
        /**
         * <p>登録対象オブジェクト</p>
         */
        public RTObject_impl rtobj;
    }
    
    /**
     * <p>Naming Service登録用マネージャサーバントクラスです。</p>
     */
    protected class Mgr {
        /**
         * <p> constructor </p>
         * 
         * @param n
         * @param obj
         */
        public Mgr(final String n, final ManagerServant obj) {
            name = n;
            mgr = obj;
        }
        /**
         * <p>コンポーネント名称</p>
         */
        public String name;
        /**
         * <p>登録対象マネージャサーバント</p>
         */
        public ManagerServant mgr;
    }
    /**
     * <p>登録されたコンポーネント</p>
     */
    protected Vector<Comps> m_compNames = new Vector<Comps>();

    /**
     * <p>登録されたマネージャサーバント</p>
     */
    protected Vector<Mgr> m_mgrNames = new Vector<Mgr>();

    /**
     * <p>タイマーに登録されたリスナーから呼び出されるメソッドです。</p>
     */
    public void doOperate() {
        this.update();
    }

    /**
     * <p>Managerオブジェクト</p>
     */
    protected Manager m_manager;
    /**
     * <p>Logging用フォーマットオブジェクト</p>
     */
    protected Logbuf rtcout;
}
