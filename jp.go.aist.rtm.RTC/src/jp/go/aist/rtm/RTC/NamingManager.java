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
public class NamingManager implements NamingBase, CallbackFunction {

    /**
     * <p>コンストラクタです。</p>
     * 
     * @param manager Managerオブジェクト
     */
    public NamingManager(Manager manager) {
        m_manager = manager;

        rtcout = new Logbuf("NamingManager");
//        rtcout.setLevel("PARANOID");
    }

    /**
     * <p>NameServerを登録します。</p>
     * 
     * @param method NameServerのタイプ
     * @param name_server NameServer名称
     */
    public void registerNameServer(final String method, final String name_server) {
        rtcout.println(rtcout.TRACE, "NamingManager.registerNameServer(" + method + ", " + name_server + ")");
        NamingBase name = createNamingObj(method, name_server);
        m_names.add(new Names(method, name_server, name));
    }

    /**
     * <p>オブジェクトをNameServerにbindします。</p>
     * 
     * @param name bind時の名称
     * @param rtobj bind対象オブジェクト
     */
    public void bindObject(final String name, final RTObject_impl rtobj) {
        rtcout.println(rtcout.TRACE, "NamingManager.bindObject(" + name + ")");
        synchronized (m_names) {
            for(int intIdx=0;intIdx<m_names.size();++intIdx ) {
                if( m_names.elementAt(intIdx).ns != null ) {
                    m_names.elementAt(intIdx).ns.bindObject(name, rtobj);
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
    public void bindObject(final String name, final ManagerServant mgr) {
        rtcout.println(rtcout.TRACE, "NamingManager.bindObject(" + name + ")");
        synchronized (m_names) {
            for(int intIdx=0;intIdx<m_names.size();++intIdx ) {
                if( m_names.elementAt(intIdx).ns != null ) {
                    m_names.elementAt(intIdx).ns.bindObject(name, mgr);
                }
            }
            this.registerMgrName(name, mgr);
        }
    }

    /**
     * <p>NameServerの情報を更新します。</p>
     */
    public void update(){
        rtcout.println(rtcout.TRACE, "NamingManager.update()");
        
        boolean rebind = StringUtil.toBool(m_manager.getConfig().getProperty("naming.update.rebind"), 
                            "YES", "NO", false);
        synchronized (m_names) {
            for( int intIdx=0;intIdx<m_names.size(); ++intIdx ) {
                if( m_names.elementAt(intIdx).ns == null ) { // if ns==NULL
                    // recreate NamingObj
                    NamingBase nsobj = createNamingObj(m_names.elementAt(intIdx).method,
                                                        m_names.elementAt(intIdx).nsname);
                    if( nsobj != null ) { // if succeed
                        rtcout.println(rtcout.INFO, "New name server found: " + 
                                 m_names.elementAt(intIdx).method + "/" +
                                 m_names.elementAt(intIdx).nsname);
                        m_names.elementAt(intIdx).ns = nsobj;
                        bindCompsTo(m_names.elementAt(intIdx).ns); // rebind all comps to new NS
                    }
                }
                else {
                    if( rebind ) {
                        bindCompsTo(m_names.elementAt(intIdx).ns);
                    }
                }
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
            for( int intIdx=0;intIdx<m_names.size();++intIdx ) {
                if( m_names.elementAt(intIdx).ns != null ) {
                    m_names.elementAt(intIdx).ns.unbindObject(name);
                }
            }
            unregisterCompName(name);
            unregisterMgrName(name);
        }
    }

    /**
     * <p>全てのオブジェクトをNameServerからunbindします。</p>
     */
    protected void unbindAll() {
        rtcout.println(rtcout.TRACE, "NamingManager.unbindAll(): " + m_compNames.size() + " names.");
        synchronized (m_compNames) {
            int compsnum = m_compNames.size();
            for(int intIdx=0;intIdx<compsnum;intIdx++) {
                unbindObject(m_compNames.elementAt(intIdx).name);
            }
        }
        synchronized (m_mgrNames) {
            int mgrsnum = m_mgrNames.size();
            for(int intIdx=0;intIdx<mgrsnum;intIdx++) {
                unbindObject(m_mgrNames.elementAt(intIdx).name);
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
        for(int intIdx=0;intIdx<m_compNames.size();++intIdx) {
            comps.add(m_compNames.elementAt(intIdx).rtobj);
        }
        return comps;
    }
    
    /**
     * <p>NameServerに登録するオブジェクトを生成します。</p>
     * 
     * @param method NameServerのタイプ
     * @param name_server NameServer名称
     * 
     * @return NameServerオブジェクト
     */
    protected NamingBase createNamingObj(final String method, final String name_server) {
        String m = method;
        if( m.endsWith("corba")) {
            try {
                NamingOnCorba nameb = new NamingOnCorba(m_manager.getORB(), name_server);
                NamingBase name = nameb;
                if( name==null ) return null;
                rtcout.println(rtcout.INFO, "NameServer connection succeeded: " + method + "/" + name_server);
                return name;
            } catch (Exception ex) {
                rtcout.println(rtcout.INFO, "NameServer connection failed: " + method +"/" + name_server);
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
        for( int intIdx=0;intIdx<m_compNames.size(); ++intIdx) {
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
        for(int intIdx=0;intIdx<m_compNames.size();++intIdx ) {
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
        for(int intIdx=0;intIdx<m_mgrNames.size();++intIdx ) {
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
        for( int intIdx=0;intIdx<m_compNames.size();++intIdx ) {
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
        for( int intIdx=0;intIdx<m_mgrNames.size();++intIdx ) {
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
            name = new String(n);
//            name = n;
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
