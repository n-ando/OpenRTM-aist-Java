package jp.go.aist.rtm.RTC;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import jp.go.aist.rtm.RTC.log.Logbuf;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.ORBUtil;
import jp.go.aist.rtm.RTC.util.POAUtil;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.StringUtil;
import jp.go.aist.rtm.RTC.util.equalFunctor;
import jp.go.aist.rtm.RTC.util.ManagerServantUtil;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;

import RTC.ComponentProfile;
import RTC.ComponentProfileListHolder;
import RTC.RTCListHolder;
import RTC.RTObject;
import RTC.ReturnCode_t;
import RTM.ManagerHelper;
import RTM.ManagerPOA;
import RTM.ManagerProfile;
import RTM.ModuleProfile;
import _SDOPackage.NVListHolder;


  /**
   * {@.ja ManagerのCORBA化クラス}
   * {@.en Manager CORBA class}
   *
   * <p>
   * {@.ja ManagerをCORBAサーバント化し、外部からコンポーネントの生成・削除、
   * システム状態の取得などが行える。}
   * {@.en This class changes Manager to CORBA Servant.
   * Generation/deletion of the component, to get the state of the system, 
   * etc. can be done from the outside.}
   *
   */
public class ManagerServant extends ManagerPOA {

    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     */
    public ManagerServant() {
        rtcout = new Logbuf("ManagerServant");
        m_mgr = jp.go.aist.rtm.RTC.Manager.instance();

        Properties config = m_mgr.getConfig();    
    
        if (StringUtil.toBool(config.getProperty("manager.is_master"), 
                                                    "YES", "NO", true)) {
            // this is master manager
            rtcout.println(Logbuf.TRACE, "This manager is master.");

            try{
                //Registers the reference
                createManagerCORBAServant();
            }
            catch(Exception ex){
                 rtcout.println(Logbuf.WARN, 
                           "Manager CORBA servant creation failed."+ex);
                 return ;
            }

            if (!createINSManager()) {
                rtcout.println(Logbuf.WARN, 
                    "Manager CORBA servant creation failed.");
                return;
            
            }
            m_isMaster = true;
            rtcout.println(Logbuf.WARN, 
                    "Manager CORBA servant was successfully created.");
/*
{
String ior;
ior = m_mgr.getORB().object_to_string(m_objref);
rtcout.println(Logbuf.DEBUG, 
        "Manager's IOR information: "+ior);
System.err.println("Manager's IOR information: "+ior);
}
*/
            return;
        }
        else { // manager is slave
            rtcout.println(Logbuf.TRACE, "This manager is slave.");
            try {
                RTM.Manager owner;
                owner = findManager(config.getProperty("corba.master_manager"));
                if (owner == null) {
                    rtcout.println(Logbuf.INFO, "Master manager not found.");
                    return;
                }



                try{
                    //Registers the reference
                    createManagerCORBAServant();
                }
                catch(Exception ex){
                     rtcout.println(Logbuf.WARN, 
                           "Manager CORBA servant creation failed."+ex);
                     return ;
                }



                if (!createINSManager()) {
                    rtcout.println(Logbuf.WARN, 
                        "Manager CORBA servant creation failed.");
                    return;
                }
                add_master_manager(owner);
                owner.add_slave_manager(m_objref);
/*
{
String ior;
ior = m_mgr.getORB().object_to_string(m_objref);
rtcout.println(Logbuf.DEBUG, 
        "Manager's IOR information: "+ior);
System.err.println("Manager's IOR information: "+ior);
}
*/
                return;
            }
            catch (Exception ex) {
                rtcout.println(Logbuf.ERROR, 
                        "Unknown exception caught.");
            }
        }

    }

    /**
     * {@.ja CORBAオブジェクトの取得。}
     * {@.en Gets CORBA object.}
     *
     * <p>
     * {@.ja CORBAオブジェクト参照を取得する。}
     * {@.en Gets RTM.Manager object.}
     * 
     * @return 
     *   {@.ja CORBAオブジェクト}
     *   {@.en RTM.Manager object.}
     */
    public RTM.Manager _this() {
        if (this.m_objref == null) {
            try {
                this.m_objref = ManagerHelper.narrow(POAUtil.getRef(this));
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return this.m_objref;
    }

    /**
     * {@.ja INSManagerの生成}
     * {@.en Generate INSManager. }
     * @return 
     *   {@.ja 成功:true, 失敗:false}
     *   {@.en Successful:true, Failed:false}
     */
    public boolean createINSManager() {

        rtcout.println(Logbuf.DEBUG, "createINSManager()");
        try{

            //
            rtcout.println(Logbuf.DEBUG, "gets object.");
            org.omg.CORBA.Object obj 
                    = m_mgr.getORB().resolve_initial_references("manager");
            this.m_objref = RTM.ManagerHelper.narrow(obj);
/*
            POA poa = POAHelper.narrow(obj);
            poa.the_POAManager().activate();

            byte[] id  = poa.activate_object( this );
            org.omg.CORBA.Object ref = poa.id_to_reference(id);
            this.m_objref = ManagerHelper.narrow(ref);
        
            String ior = m_mgr.getORB().object_to_string(m_objref);
            rtcout.println(Logbuf.DEBUG, 
                        "Manager's IOR information:"+ior);
 
*/
        }
        catch(Exception ex){
             rtcout.println(Logbuf.WARN, 
                       "Manager CORBA servant creation failed."+ex);
             return false;
        }

        return true;
    }

    /**
     * {@.ja Managerのリファレンスを検索する。}
     * {@.en Find the reference of Manager.}
     * return 
     *   {@.ja Managerのリファレンス}
     *   {@.en Manager reference}
     */
    public RTM.Manager findManager(final String host_port) {
        rtcout.println(Logbuf.TRACE, "findManager(host_port = "+host_port+")");

        try{
            Properties config = m_mgr.getConfig();
            String name = config.getProperty("manager.name");
            String mgrloc = "corbaloc:iiop:1.2@"+host_port+"/"+name;
            rtcout.println(Logbuf.DEBUG, "corbaloc: "+mgrloc);

            ORB orb = ORBUtil.getOrb();
            Object mobj;
            mobj = orb.string_to_object(mgrloc);
//            mobj = orb.resolve_initial_references("INSPOA");
//            mobj = m_mgr.getORB().resolve_initial_references("manager");
            RTM.Manager mgr 
                = RTM.ManagerHelper.narrow(mobj);


            String ior;
            ior = orb.object_to_string(mobj);
            rtcout.println(Logbuf.DEBUG, 
                    "Manager's IOR information: "+ior);
     
            return mgr;
        }
        catch(org.omg.CORBA.SystemException ex) {
            rtcout.println(Logbuf.DEBUG, 
                "CORBA SystemException caught (CORBA."+ex.toString()+")");
            return (RTM.Manager)null;
        }
        catch (Exception ex) {
            rtcout.println(Logbuf.DEBUG, "Unknown exception caught.");
            return (RTM.Manager)null;
        }

    }

    /**
     * {@.ja モジュールをロードする}
     * {@.en Loading a module}
     *
     * <p> 
     * {@.ja 当該マネージャに指定されたモジュールをロードし、指定された初期化
     * 関数で初期化を行う。}
     * {@.en This operation loads a specified loadable module and perform
     * initialization with the specified function.}
     *
     * @param pathname 
     *   {@.ja モジュールへのパス}
     *   {@.en A path to a loading module.}
     * @param initfunc 
     *   {@.ja モジュールの初期化関数}
     *   {@.en Module initialization function.}
     * @return 
     *   {@.ja リターンコード}
     *   {@.en The return code.}
     */
    public RTC.ReturnCode_t load_module(final String pathname, 
                                            final String initfunc) {
        rtcout.println(Logbuf.TRACE, 
                    "ManagerServant.load_module("+pathname+", "+initfunc+")");
        m_mgr.load(pathname, initfunc);
        return ReturnCode_t.RTC_OK;
    }

    /**
     * {@.ja モジュールをアンロードする}
     * {@.en Unloading a module}
     *
     * <p> 
     * {@.ja 当該マネージャに指定されたモジュールをアンロードする。}
     * {@.en This operation unloads a specified loadable module.}
     * @param pathname 
     *   {@.ja モジュールへのパス}
     *   {@.en A path to a loading module.}
     * @return 
     *   {@.ja リターンコード}
     *   {@.en The return code.}
     */
    public RTC.ReturnCode_t unload_module(final String pathname) {
        rtcout.println(Logbuf.TRACE, 
                        "ManagerServant.unload_module("+pathname+")");
        try {
            m_mgr.unload(pathname);
        } catch(Exception ex) {
            rtcout.println(Logbuf.WARN, 
                    "Exception caught.Not Found:"+pathname+" "+ex.toString());
        }
        return ReturnCode_t.RTC_OK;
    }

    /**
     * {@.ja ロード可能なモジュールのプロファイルを取得する}
     * {@.en Getting loadable module profiles}
     *
     * <p>
     * {@.ja ロード可能なモジュールのプロファイルを取得する。}
     * {@.en This operation returns loadable module profiles.}
     *
     * @return
     *   {@.ja モジュールプロファイル}
     *   {@.en A module profile list.}
     *
     */
    public RTM.ModuleProfile[] get_loadable_modules() {
        rtcout.println(Logbuf.TRACE, "get_loadable_modules()");
        // copy local module profiles
        Vector<Properties> prof = m_mgr.getLoadableModules();
        RTM.ModuleProfile[] cprof = new RTM.ModuleProfile[prof.size()];
        for (int i=0, len=prof.size(); i < len; ++i) {
            String dumpString = new String();
            dumpString = prof.elementAt(i)._dump(dumpString, 
                                                    prof.elementAt(i), 0);
            rtcout.println(Logbuf.VERBOSE, dumpString);
            _SDOPackage.NVListHolder nvlist = new _SDOPackage.NVListHolder();
            NVUtil.copyFromProperties(nvlist, prof.elementAt(i));
            cprof[i] = new RTM.ModuleProfile(nvlist.value);
        }

        if (false) {
            // copy slaves' module profiles
            synchronized(m_slaveMutex) {
                rtcout.println(Logbuf.DEBUG,
                                    m_slaves.length+" slaves exists.");
                for (int i=0, len=m_slaves.length; i < len; ++i) {
                    try {
                        if (m_slaves[i] != null) {
                            RTM.ModuleProfile[] sprof;
                            sprof = m_slaves[i].get_loadable_modules();
                            
                            RTM.ModuleProfileListHolder holder1 
                                = new RTM.ModuleProfileListHolder(cprof);
                            RTM.ModuleProfileListHolder holder2
                                = new RTM.ModuleProfileListHolder(sprof);
                            CORBA_SeqUtil.push_back_list(holder1, holder2);
                            cprof = holder1.value;
                            continue; 
                        }
                    }
                    catch(Exception ex) {
                        rtcout.println(Logbuf.INFO,
                                    "slave ("+i+") has disappeared.");
                        m_slaves[i] = (RTM.Manager)null;
                    }
                    RTM.ManagerListHolder holder 
                                    = new RTM.ManagerListHolder(m_slaves);
                    CORBA_SeqUtil.erase(holder, i); 
                    --i;
                    m_slaves = holder.value;
                }
            }
        }
        return cprof;

/*

        _SDOPackage.NVListHolder nvlist = new _SDOPackage.NVListHolder();
        Vector<Properties> prof = m_mgr.getLoadableModules();
        ModuleProfile[] cprof = new ModuleProfile[prof.size()];
        String[] strs = new String[2];

        for (int i=0, len=prof.size(); i < len; ++i) {
            strs[0] = prof.elementAt(i).getName();
            strs[1] = prof.elementAt(i).getValue();
            Properties prop = new Properties(strs);
            NVUtil.copyFromProperties(nvlist, prop);

            ModuleProfile cprof3 = new ModuleProfile();
            cprof2.properties =  new _SDOPackage.NameValue [nvlist.value.length];
            cprof2.properties =  nvlist.value;
            cprof[i] = cprof2;
        }

        return cprof;
*/
    }

    /**
     * {@.ja モジュールのプロファイルを取得}
     * {@.en Getting loaded module profiles}
     *
     * <p>
     * {@.ja ロード済みのモジュールのプロファイルを取得する。}
     * {@.en This operation returns loaded module profiles.}
     *
     *
     * @return 
     *   {@.ja モジュールプロファイル}
     *   {@.en A module profile list.}
     *
     */
    public RTM.ModuleProfile[] get_loaded_modules() {
        rtcout.println(Logbuf.TRACE, "get_loaded_modules()");

        // copy local module profiles
        RTM.ModuleProfileListHolder cprof = new RTM.ModuleProfileListHolder();
        Vector<Properties> prof = m_mgr.getLoadedModules();
        cprof.value = new RTM.ModuleProfile[prof.size()];

        for (int i=0, len=prof.size(); i < len; ++i) {
            String dumpString = new String();
            dumpString = prof.elementAt(i)._dump(dumpString, 
                                                    prof.elementAt(i), 1);
            
            _SDOPackage.NVListHolder nvlist = new _SDOPackage.NVListHolder();
            NVUtil.copyFromProperties(nvlist, prof.elementAt(i));
            cprof.value[i] = new RTM.ModuleProfile(nvlist.value);
        }

        if (false) {
            // copy slaves' module profile
            synchronized(m_slaveMutex) {

                rtcout.println(Logbuf.DEBUG,
                                    m_slaves.length+" slave managers exists.");
                for (int i=0, len= m_slaves.length; i < len; ++i) {
                    try {
                        if (m_slaves[i]!=null) {
                            RTM.ModuleProfile[] sprof;
                            sprof = m_slaves[i].get_loaded_modules();
                            RTM.ModuleProfileListHolder holder
                                = new RTM.ModuleProfileListHolder(sprof);
                            CORBA_SeqUtil.push_back_list(cprof, holder);
                            continue;
                        }
                    }
                    catch(Exception ex) {
                        rtcout.println(Logbuf.INFO,
                                    "slave ("+i+") has disappeared.");
                        m_slaves[i] = (RTM.Manager)null;
                    }
                    RTM.ManagerListHolder holder 
                                    = new RTM.ManagerListHolder(m_slaves);
                    CORBA_SeqUtil.erase(holder, i); 
                    --i;
                    m_slaves = holder.value;
                  }
            }
        }
        return cprof.value;
/*
        _SDOPackage.NVListHolder nvlist = new _SDOPackage.NVListHolder();
        Vector<Properties> prof = m_mgr.getLoadedModules();
        ModuleProfile[] cprof = new ModuleProfile[prof.size()];
        String[] strs = new String[2];

        for (int i=0, len=prof.size(); i < len; ++i) {
            strs[0] = prof.elementAt(i).getName();
            strs[1] = prof.elementAt(i).getValue();
            Properties prop = new Properties(strs);
            NVUtil.copyFromProperties(nvlist, prop);

            ModuleProfile cprof2 = new ModuleProfile();
            cprof2.properties =  new _SDOPackage.NameValue [nvlist.value.length];
            cprof2.properties =  nvlist.value;
            cprof[i] = cprof2;
        }

        return cprof;
*/
    }

    /**
     * {@.ja コンポーネントファクトリのプロファイルを取得する}
     * {@.en Getting component factory profiles}
     *
     * <p>
     * {@.ja ロード済みのモジュールのうち、RTコンポーネントのモジュールが持つ
     * ファクトリのプロファイルのリストを取得する。}
     * {@.en This operation returns component factory profiles from loaded
     * RT-Component module factory profiles.}
     *
     * @return 
     *   {@.ja コンポーネントファクトリのプロファイルリスト}
     *   {@.en An RT-Component factory profile list.}
     */
    public RTM.ModuleProfile[] get_factory_profiles() {
        rtcout.println(Logbuf.TRACE, "get_factory_profiles()");

        Vector<Properties> prof = m_mgr.getFactoryProfiles();
        ModuleProfile[] cprof = new ModuleProfile[prof.size()];

        for (int i=0, len=prof.size(); i < len; ++i) {
            Properties prop = prof.elementAt(i);
            String dumpString = new String();
            dumpString = prop._dump(dumpString, prop, 0);
            _SDOPackage.NVListHolder nvlist = new _SDOPackage.NVListHolder();
            NVUtil.copyFromProperties(nvlist, prop);
            cprof[i] =  new RTM.ModuleProfile(nvlist.value);
        }

        if (false) {
            // copy slaves' factory profile
            synchronized(m_slaveMutex) {
                rtcout.println(Logbuf.DEBUG,
                                    m_slaves.length+" slaves exists.");
                for (int i=0, len=m_slaves.length; i < len; ++i) {
                    try {
                        if (m_slaves[i]!=null) {
                            RTM.ModuleProfile[] sprof;
                            sprof = m_slaves[i].get_factory_profiles();
                            RTM.ModuleProfileListHolder holder1 
                                = new RTM.ModuleProfileListHolder(cprof);
                            RTM.ModuleProfileListHolder holder2
                                = new RTM.ModuleProfileListHolder(sprof);
                            CORBA_SeqUtil.push_back_list(holder1, holder2);
                            cprof = holder1.value;
                            continue;
                        }
                    }
                    catch(Exception ex) {
                        rtcout.println(Logbuf.INFO,
                                    "slave ("+i+") has disappeared.");
                        m_slaves[i] = (RTM.Manager)null;
                    }
                    RTM.ManagerListHolder holder 
                                    = new RTM.ManagerListHolder(m_slaves);
                    CORBA_SeqUtil.erase(holder, i); 
                    --i;
                    m_slaves = holder.value;
                }
            }
        }   
        return cprof;
    }

    /**
     * {@.ja コンポーネントを生成する}
     * {@.en Creating an RT-Component}
     *
     * <p>
     * {@.ja 引数に指定されたコンポーネントを生成する。}
     * {@.en This operation creates RT-Component according to the string
     * argument.}
     *
     * @return 
     *   {@.ja 生成されたRTコンポーネント}
     *   {@.en A created RT-Component}
     *
     */
    public RTC.RTObject create_component(final String module_name) {
        rtcout.println(Logbuf.TRACE, "create_component("+module_name+")");

        String arg = module_name;
        int pos0 = arg.indexOf("&manager=");
        int pos1 = arg.indexOf("?manager=");

        if (pos0 < 0 && pos1 < 0){
            if (false) { //is_master()
                rtcout.println(Logbuf.TRACE, 
                    "Master manager cannot create component: "+module_name);
                return null;
            }
        // create on this manager
            RTObject_impl rtc = m_mgr.createComponent(module_name);
            if (rtc == null) {
                return null;
            }
            return rtc.getObjRef();
        }


        // create other manager
        // extract manager's location
        int pos;
        if(! (pos0 < 0) ){
            pos = pos0;
        }
        else{
            pos = pos1;
        }
    
        int endpos;
        endpos = arg.indexOf('&', pos + 1);
        if(endpos<0){
            endpos = arg.length();
        }
        String mgrstr = arg.substring(pos + 1, endpos);
        rtcout.println(Logbuf.VERBOSE, "Manager arg: "+mgrstr);
        String[] mgrvstr = mgrstr.split(":");
        if (mgrvstr.length != 2) {
            rtcout.println(Logbuf.WARN, "Invalid manager name: "+mgrstr);
            return null;
        }
        int  eqpos = mgrstr.indexOf("=");
        if (eqpos < 0) {
            rtcout.println(Logbuf.WARN, "Invalid argument: "+module_name);
            return null;
        }
        mgrstr =  mgrstr.substring(eqpos + 1);
        rtcout.println(Logbuf.DEBUG, "Manager is : "+mgrstr);

        // find manager
        RTM.Manager mgrobj = findManager(mgrstr);
        if (mgrobj==null) {
            List<String> cmd = new ArrayList();
/*
            cmd.add("java");
            cmd.add("-jar");
            String rtm_java_root = System.getenv("RTM_JAVA_ROOT");
            String rtcd = rtm_java_root+"/jar/rtcd.jar";
            cmd.add(rtcd);
*/
            cmd.add("rtcd_java");
            cmd.add("-p");
            cmd.add(mgrvstr[1]); // port number

            rtcout.println(Logbuf.DEBUG, "Invoking command: "+cmd);
            try{
                ProcessBuilder pb = new ProcessBuilder(cmd);
                Process p = pb.start();
            }
            catch(Exception ex){
                rtcout.println(Logbuf.DEBUG, cmd + ": failed");
                return null;
            }

            // find manager
            try{
                Thread.sleep(10);   //10ms
            }
            catch(InterruptedException ex){
                 //do nothing
            }
            int count = 0;
            while (mgrobj == null) {
                mgrobj = findManager(mgrstr);
                ++count;
                if (count > 1000) { 
                    break; 
                }
                try{
                    Thread.sleep(10);   //10ms
                }
                catch(InterruptedException ex){
                    //do nothing
                }
            }
        }

        if (mgrobj == null) {
            rtcout.println(Logbuf.WARN, "Manager cannot be found.");
            return null;
        }
    
        // create component on the manager    
        arg = arg.substring(0, pos);
        rtcout.println(Logbuf.DEBUG, "Creating component on "+mgrstr);
        rtcout.println(Logbuf.DEBUG, "arg: "+arg);
        try {
            RTObject rtobj;
            rtobj = mgrobj.create_component(arg);
            rtcout.println(Logbuf.DEBUG, "Component created "+arg);
            return rtobj;
        }
        catch (org.omg.CORBA.SystemException e) {
            rtcout.println(Logbuf.DEBUG, 
                        "Exception was caught while creating component.");
            return null;
        }
    }

    /**
     * {@.ja コンポーネントを削除する}
     * {@.en Deleting an RT-Component}
     *
     * <p>
     * {@.ja 引数に指定されたコンポーネントを削除する。}
     * {@.en This operation delete an RT-Component according to the string
     * argument.}
     *
     * @param instance_name
     *   {@.ja インスタンス名}
     *   {@.en Instance name}
     * @return 
     *   {@.ja リターンコード}
     *   {@.en Return code}
     *
     */
    public RTC.ReturnCode_t delete_component(final String instance_name) {
        rtcout.println(Logbuf.TRACE, "delete_component("+instance_name+")");

        m_mgr.deleteComponent(instance_name);
        return ReturnCode_t.RTC_OK;
    }

    /**
     * {@.ja 起動中のコンポーネントのリストを取得する}
     * {@.en Getting RT-Component list running on this manager}
     *
     * <p>
     * {@.ja 現在当該マネージャ上で起動中のコンポーネントのリストを返す。}
     * {@.en This operation returns RT-Component list running on this manager.}
     *
     * @return 
     *   {@.ja RTコンポーネントのリスト}
     *   {@.en A list of RT-Components}
     *
     *
     */
    public RTC.RTObject[] get_components() {
        rtcout.println(Logbuf.TRACE, "get_component()");

        Vector<RTObject_impl> rtcs = m_mgr.getComponents();
        RTCListHolder crtcs = new RTCListHolder();
        crtcs.value = new RTObject[rtcs.size()];

        for (int i=0, len=rtcs.size(); i < len; ++i) {
            crtcs.value[i] = rtcs.elementAt(i).getObjRef();
        }
        // get slaves' component references
        rtcout.println(Logbuf.DEBUG,
                                    m_slaves.length+" slaves exists.");
        for (int i=0, len=m_slaves.length; i < len; ++i) {
            try {
                if (m_slaves[i]!=null) {
                    RTC.RTObject[] srtcs;
                    srtcs = m_slaves[i].get_components();
                    RTC.RTCListHolder holder
                            = new RTC.RTCListHolder(srtcs);
                    CORBA_SeqUtil.push_back_list(crtcs, holder);
                    continue;
                  }
            }
            catch(Exception ex) {
                rtcout.println(Logbuf.INFO,
                                    "slave ("+i+") has disappeared.");
                m_slaves[i] = (RTM.Manager)null;
            }
            RTM.ManagerListHolder holder 
                                = new RTM.ManagerListHolder(m_slaves);
            CORBA_SeqUtil.erase(holder, i); 
            --i;
            m_slaves = holder.value;
        }
        return crtcs.value;
    }
  
    /**
     * {@.ja 起動中のコンポーネントプロファイルのリストを取得する}
     * {@.en Getting RT-Component's profile list running on this manager}
     *
     * <p>
     * {@.ja 現在当該マネージャ上で起動中のコンポーネントのプロファイルのリス
     * トを返す。}
     * {@.en This operation returns RT-Component's profile list running on
     * this manager.}
     *
     * @return 
     *   {@.ja RTコンポーネントプロファイルのリスト}
     *   {@.en A list of RT-Components' profiles}
     *
     */
    public RTC.ComponentProfile[] get_component_profiles() {
        rtcout.println(Logbuf.TRACE, "get_component_profiles()");

        ComponentProfileListHolder cprofs = new ComponentProfileListHolder();
        Vector<RTObject_impl> rtcs = m_mgr.getComponents();
        cprofs.value = new ComponentProfile[rtcs.size()];

        for (int i=0, len=rtcs.size(); i < len; ++i) {
            cprofs.value[i] = rtcs.elementAt(i).get_component_profile();
        }
        // copy slaves' component profiles
        synchronized(m_slaveMutex) {
            rtcout.println(Logbuf.DEBUG,
                                    m_slaves.length+" slaves exists.");
            for (int i=0, len=m_slaves.length; i < len; ++i) {
                try {
                    if (m_slaves[i]!=null) {

                        RTC.ComponentProfile[] sprof;
                        sprof = m_slaves[i].get_component_profiles();
                            
                        ComponentProfileListHolder holder
                                = new ComponentProfileListHolder(sprof);
                        CORBA_SeqUtil.push_back_list(cprofs, holder);
                        continue; 
                      }
                }
                catch(Exception ex) {
                    rtcout.println(Logbuf.INFO,
                                    "slave ("+i+") has disappeared.");
                    m_slaves[i] = (RTM.Manager)null;
                }
                RTM.ManagerListHolder holder 
                                = new RTM.ManagerListHolder(m_slaves);
                CORBA_SeqUtil.erase(holder, i); 
                --i;
                m_slaves = holder.value;
            }
        }
        return cprofs.value;
    }

    /**
     * {@.ja マネージャのプロファイルを取得する}
     * {@.en Getting this manager's profile.}
     *
     * <p>
     * {@.ja 現在当該マネージャのプロファイルを取得する。}
     * {@.en This operation returns this manager's profile.}
     *
     * @return 
     *   {@.ja マネージャプロファイル}
     *   {@.en Manager's profile}
     *
     */
    public RTM.ManagerProfile get_profile() {
        rtcout.println(Logbuf.TRACE, "get_profile()");

        NVListHolder nvlist = new NVListHolder();
        ManagerProfile prof = new ManagerProfile();
        NVUtil.copyFromProperties(nvlist,
                                 (m_mgr.getConfig().getNode("manager")));
        prof.properties = nvlist.value;
        return prof;
    }

    /**
     * {@.ja マネージャのコンフィギュレーションを取得する}
     * {@.en Getting this manager's configuration.}
     *
     * <p>
     * {@.ja 現在当該マネージャのコンフィギュレーションを取得する。}
     * {@.en This operation returns this manager's configuration.}
     *
     * @return 
     *   {@.ja マネージャコンフィギュレーション}
     *   {@.en Manager's configuration}
     *
     */
    public _SDOPackage.NameValue[] get_configuration() {
        rtcout.println(Logbuf.TRACE, "get_configuration()");

        NVListHolder nvlist = new NVListHolder();
        NVUtil.copyFromProperties(nvlist, m_mgr.getConfig());
        return nvlist.value;
    }

    /**
     * {@.ja マネージャのコンフィギュレーションを設定する}
     * {@.en Setting manager's configuration}
     *
     * <p>
     * {@.ja 現在当該マネージャのコンフィギュレーションを設定する。}
     * {@.en This operation sets managers configuration.}
     *
     * @param name 
     *   {@.ja セットするコンフィギュレーションのキー名}
     *   {@.en A configuration key name to be set}
     * @param value 
     *   {@.ja セットするコンフィギュレーションの値}
     *   {@.en A configuration value to be set}
     * @return 
     *   {@.ja リターンコード}
     *   {@.en Return code}
     */
    public RTC.ReturnCode_t set_configuration(final String name, 
                                                    final String value) {
        rtcout.println(Logbuf.TRACE, "set_configuration()");

        m_mgr.getConfig().setProperty(name, value);
        return ReturnCode_t.RTC_OK;
    }

    /**
     * {@.ja マネージャがマスターかどうか。}
     * {@.en Whether this manager is master or not}
     *
     * <p>
     * {@.ja この関数はマネージャがマスターかどうかを返す。Trueならば、当該マ
     * ネージャはマスターであり、それ以外は False を返す。}
     * {@.en It returns "True" if this manager is a master, and it returns
     * "False" in other cases.}
     *
     * @return 
     *   {@.ja マスターマネージャかどうかのbool値}
     *   {@.en A boolean value that means it is master or not.}
     */
    public boolean is_master() {
        rtcout.println(Logbuf.TRACE, "is_master(): "+m_isMaster);
        return m_isMaster;

    }

    /**
     * {@.ja マスターマネージャの取得。}
     * {@.en Getting master managers}
     *
     * <p>
     * {@.ja このマネージャがスレーブマネージャの場合、マスターとなっているマ
     * ネージャのリストを返す。このマネージャがマスターの場合、空のリス
     * トが返る。}
     * {@.en This operation returns master manager list if this manager is
     * slave. If this manager is master, an empty sequence would be
     * returned.}
     *
     * @return 
     *   {@.ja マスターマネージャのリスト}
     *   {@.en Master manager list}
     */
    public RTM.Manager[] get_master_managers() {
        rtcout.println(Logbuf.TRACE, "get_master_managers()");

        synchronized(m_masterMutex) {
            RTM.ManagerListHolder holder = new RTM.ManagerListHolder(m_masters);
            return holder.value;
        }
    }

    /**
     * {@.ja マスターマネージャの追加。}
     * {@.en Getting a master manager}
     *
     * <p>
     * {@.ja このマネージャのマスタとしてマネージャを一つ追加する。戻り値には、
     * 当該マネージャ上で追加されたマスターマネージャを識別するユニーク
     * なIDが返される。このマネージャがマスタの場合、当該IDで指定された
     * マスターマネージャを返す。IDで指定されたマスターマネージャがない
     * 場合、nilオブジェクトが返る。}
     * {@.en This operation returns a master manager with specified id. If
     * the manager with the specified id does not exist, nil object
     * reference would be returned.}
     *
     * @return 
     *   {@.ja マスターマネージャ}
     *   {@.en A master manager}
     *
     */
    public ReturnCode_t add_master_manager(RTM.Manager mgr) {
        synchronized(m_masterMutex) {
            long index;
            rtcout.println(Logbuf.TRACE, 
                    "add_master_manager(), "+m_masters.length+" masters");

            RTM.ManagerListHolder holder = new RTM.ManagerListHolder(m_masters);
            index = CORBA_SeqUtil.find(holder, new is_equiv(mgr));
    
            if (!(index < 0)) {// found in my list
                rtcout.println(Logbuf.ERROR, "Already exists.");
                return ReturnCode_t.BAD_PARAMETER;
            }
    
            CORBA_SeqUtil.push_back(holder, (RTM.Manager)mgr._duplicate());
            m_masters = holder.value;
            rtcout.println(Logbuf.TRACE, 
                "add_master_manager() done, "+m_masters.length+" masters");
            return ReturnCode_t.RTC_OK;
        }
    }

    /**
     * {@.ja マスターマネージャの削除}
     * {@.en Removing a master manager}
     *
     * <p>
     * {@.ja このマネージャが保持するマスタのうち、指定されたものを削除する。}
     * {@.en This operation removes a master manager from this manager.}
     *
     * @param mgr 
     *   {@.ja マスターマネージャ}
     *   {@.en A master manager}
     * @return 
     *   {@.ja ReturnCode_t}
     *   {@.en ReturnCode_t}
     *
     */
    public ReturnCode_t remove_master_manager(RTM.Manager mgr) {
        synchronized(m_masterMutex) {
            rtcout.println(Logbuf.TRACE, 
                    "remove_master_manager(), "+m_masters.length+" masters");

            long index;
            RTM.ManagerListHolder holder = new RTM.ManagerListHolder(m_masters);
            index = CORBA_SeqUtil.find(holder, new is_equiv(mgr));
    
            if (index < 0) { // not found in my list
                rtcout.println(Logbuf.ERROR, "Not found.");
                return ReturnCode_t.BAD_PARAMETER;
            }
    
            CORBA_SeqUtil.erase(holder, (int)index);
            m_masters = holder.value;
            rtcout.println(Logbuf.TRACE, 
                "remove_master_manager() done, "+m_masters.length+" masters");
            return ReturnCode_t.RTC_OK;
        }
    }    


    /**
     * {@.ja スレーブマネージャの取得。}
     * {@.en Getting slave managers}
     *
     * <p>
     * {@.ja このマネージャがスレーブマネージャの場合、スレーブとなっているマ
     * ネージャのリストを返す。このマネージャがスレーブの場合、空のリス
     * トが返る。}
     * {@.en This operation returns slave manager list if this manager is
     * slave. If this manager is slave, an empty sequence would be
     * returned.}
     *
     * @return 
     *   {@.ja スレーブマネージャのリスト}
     *   {@.en Slave manager list}
     *
     */
    public RTM.Manager[] get_slave_managers() {
        synchronized(m_masterMutex) {
            rtcout.println(Logbuf.TRACE, 
                "get_slave_managers(), "+m_slaves.length+" slaves");
    
            RTM.ManagerListHolder holder = new RTM.ManagerListHolder(m_slaves);
            return holder.value;
        }
    }

    /**
     * {@.ja スレーブマネージャの追加}
     * {@.en Getting a slave manager}
     *
     * <p>
     * {@.ja このマネージャのマスタとしてマネージャを一つ追加する。}
     * {@.en This operation add a slave manager to this manager.}
     *
     * @param mgr 
     *   {@.ja スレーブマネージャ}
     *   {@.en A slave manager}
     * @return 
     *   {@.ja ReturnCode_t}
     *   {@.en ReturnCode_t}
     */
    public ReturnCode_t add_slave_manager(RTM.Manager mgr) {
        synchronized(m_masterMutex) {
            rtcout.println(Logbuf.TRACE, 
                "add_slave_manager(), "+m_slaves.length+" slaves");
    
            long index;
            RTM.ManagerListHolder holder = new RTM.ManagerListHolder(m_slaves);
            index = CORBA_SeqUtil.find(holder, new is_equiv(mgr));
    
            if (!(index < 0)) { // found in my list
                rtcout.println(Logbuf.ERROR, "Already exists.");
                return ReturnCode_t.BAD_PARAMETER;
            }
    
            CORBA_SeqUtil.push_back(holder, (RTM.Manager)mgr._duplicate());
            m_slaves = holder.value;
            rtcout.println(Logbuf.TRACE, 
                "add_slave_manager() done, "+m_slaves.length+" slaves");
            return ReturnCode_t.RTC_OK;
        }
    }

    /**
     * {@.ja スレーブマネージャの削除}
     * {@.en Removing a slave manager}
     *
     * <p>
     * {@.ja このマネージャが保持するマスタのうち、指定されたものを削除する。}
     * {@.en This operation removes a slave manager from this manager.}
     *
     * @param mgr 
     *   {@.ja スレーブマネージャ}
     *   {@.en A slave manager}
     * @return 
     *   {@.ja ReturnCode_t}
     *   {@.en ReturnCode_t}
     */
    public ReturnCode_t remove_slave_manager(RTM.Manager mgr) {
        synchronized(m_masterMutex) {
            rtcout.println(Logbuf.TRACE, 
                "remove_slave_manager(), "+m_slaves.length+" slaves");
            long index;
            RTM.ManagerListHolder holder = new RTM.ManagerListHolder(m_slaves);
            index = CORBA_SeqUtil.find(holder, new is_equiv(mgr));
    
            if (index < 0) {// not found in my list
                rtcout.println(Logbuf.ERROR, "Not found.");
                return ReturnCode_t.BAD_PARAMETER;
            }
    
            CORBA_SeqUtil.erase(holder, (int)index);
            m_slaves = holder.value;
            rtcout.println(Logbuf.TRACE, 
                "remove_slave_manager() done, "+m_slaves.length+" slaves");
            return ReturnCode_t.RTC_OK;
        }
    }

    /**
     * <p> get_owner </p>
     *
     * @return RTM.Manager
     */
/* zxc
    public RTM.Manager get_owner() {
        return null;
    }
*/

    /**
     * <p> set_owner </p>
     *
     * @param mgr
     * @return RTM.Manager
     *
     */
/* zxc
    public RTM.Manager set_owner(RTM.Manager mgr) {
        return null;
    }
*/

    /**
     * <p> get_child </p>
     *
     * @return RTM.Manager
     */
/* zxc
    public RTM.Manager get_child() {
        return null;
    }
*/

    /**
     * <p> set_child </p>
     */
/* zxc
    public RTM.Manager set_child(RTM.Manager mgr) {
        return null;
    }
*/

    /**
     * {@.ja プロセスのコピーを生成する}
     * {@.en The copy of the process is generated.}
     * 
     * @return 
     *   {@.ja ReturnCode_t}
     *   {@.en ReturnCode_t}
     */
    public RTC.ReturnCode_t fork() {
    //    m_mgr.fork();
        return ReturnCode_t.RTC_OK;
    }

    /**
     * {@.ja shutdownする}
     * {@.en This method shutdowns RTC.}
     * @return 
     *   {@.ja ReturnCode_t}
     *   {@.en ReturnCode_t}
     */
    public RTC.ReturnCode_t shutdown() {
        m_mgr.terminate();
        return ReturnCode_t.RTC_OK;
    }

    /**
     * {@.ja 再起動する。}
     * {@.en This method restarts RTC.}
     * @return
     *   {@.ja ReturnCode_t}
     *   {@.en ReturnCode_t}
     */
    public ReturnCode_t restart() {
    //    m_mgr.restart();
        return ReturnCode_t.RTC_OK;
    }

    /**
     * {@.ja RTCのリファレンスを取得する。}
     * {@.en Get the reference of RTC.}
     * @return 
     *   {@.ja RTCのリファレンス}
     *   {@.en RTC reference}
     */
    public org.omg.CORBA.Object get_service(final String name) {
        return null;
    }

    /**
     * {@.ja Managerのリファレンスを取得する。}
     * {@.en Get the reference of Manager.}
     * @return 
     *   {@.ja Managerのリファレンス}
     *   {@.en Manager reference}
     */
    public RTM.Manager getObjRef() {
        return (RTM.Manager)m_objref;
    }

    /**
     * <p> setObjRef </p>
     */
/*
    public void setObjRef(final RTM.Manager rtobj) {
        m_objref = rtobj;
    }
*/

    /**
     * 
     */
    private void createManagerCORBAServant() throws Exception {
        ManagerServantUtil.createManagerCORBAServant(m_mgr,this);
/*
        m_mgr.getPOA().activate_object( this );
        com.sun.corba.se.impl.orb.ORBImpl orb 
                = (com.sun.corba.se.impl.orb.ORBImpl)m_mgr.getORB();
//        org.jacorb.orb.ORB orb
//                = (org.jacorb.orb.ORB)m_mgr.getORB();
        orb.register_initial_reference( 
                "manager", m_mgr.getPOA().servant_to_reference(this) );
*/
    }
    /**
     * <p></p>
     */
    private jp.go.aist.rtm.RTC.Manager m_mgr;

    /**
     * <p></p>
     */
    private RTM.Manager m_objref;

    /**
     * {@.ja ロガーストリーム}
     * {@.en Logger stream}
     */
    protected Logbuf rtcout;
    private boolean m_isMaster;
    private String m_masterMutex = new String();
    private RTM.Manager m_masters[] = new RTM.Manager[1];
    private String m_slaveMutex = new String();
    private RTM.Manager m_slaves[] = new RTM.Manager[1];

    private class is_equiv implements equalFunctor {
        private RTM.Manager m_mgr;
        public is_equiv(RTM.Manager mgr) {
            m_mgr = (RTM.Manager)mgr._duplicate();
        }
/*
        public boolean operator(RTM.Manager mgr) {
            return m_mgr._is_equivalent(mgr);
        }
*/
        public boolean equalof(final java.lang.Object object) {
            return m_mgr._is_equivalent((RTM.Manager)object);
        }
    }
}
