package jp.go.aist.rtm.RTC;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;

import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.Servant;

import java.util.Vector;
import java.util.List;
import java.util.ArrayList;

import RTM.ManagerPOA;
import RTM.ManagerHelper;
import RTM.ManagerProfile;
import RTM.ModuleProfile;
import RTM.ModuleProfileListHolder;

import RTC.RTObject;
import RTC.ComponentProfile;
import RTC.ComponentProfileListHolder;
import RTC.RTCListHolder;
import RTC.ReturnCode_t;

import _SDOPackage.NVListHolder;
import _SDOPackage.NameValue;

import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.POAUtil;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.ORBUtil;
import jp.go.aist.rtm.RTC.util.StringUtil;
import jp.go.aist.rtm.RTC.util.equalFunctor;
import jp.go.aist.rtm.RTC.log.Logbuf;


/**
 * <p> ManagerServant </p>
 */
public class ManagerServant extends ManagerPOA {

    /**
     * <p> Constructor </p>
     */
    public ManagerServant() {
        rtcout = new Logbuf("ManagerServant");
        m_mgr = jp.go.aist.rtm.RTC.Manager.instance();

        Properties config = m_mgr.getConfig();    
    
        if (StringUtil.toBool(config.getProperty("manager.is_master"), 
                                                    "YES", "NO", true)) {
            // this is master manager
            rtcout.println(rtcout.TRACE, "This manager is master.");

            try{
                //Registers the reference
                m_mgr.getPOA().activate_object( this );
                if(java.lang.System.getProperty("develop_prop.debug")!=null){ 
                    System.out.println("    activate_object");
                } 
                com.sun.corba.se.impl.orb.ORBImpl orb 
                        = (com.sun.corba.se.impl.orb.ORBImpl)m_mgr.getORB();
                orb.register_initial_reference( 
                        "manager", m_mgr.getPOA().servant_to_reference(this) );
                if(java.lang.System.getProperty("develop_prop.debug")!=null){ 
                    System.out.println("    register_initial_reference");
                } 
            }
            catch(Exception ex){
                 rtcout.println(rtcout.WARN, 
                           "Manager CORBA servant creation failed."+ex);
                 return ;
            }

            if (!createINSManager()) {
                rtcout.println(rtcout.WARN, 
                    "Manager CORBA servant creation failed.");
                return;
            
            }
            m_isMaster = true;
            rtcout.println(rtcout.WARN, 
                    "Manager CORBA servant was successfully created.");
            return;
        }
        else { // manager is slave
            if(java.lang.System.getProperty("develop_prop.debug")!=null){ 
                System.out.println("ManagerServant() is_master is NO.");
            }
            rtcout.println(rtcout.TRACE, "This manager is slave.");
            try {
                RTM.Manager owner;
                owner = findManager(config.getProperty("corba.master_manager"));
                if (owner == null) {
                    rtcout.println(rtcout.INFO, "Master manager not found.");
                    return;
                }
                if (!createINSManager()) {
                    rtcout.println(rtcout.WARN, 
                        "Manager CORBA servant creation failed.");
                    return;
                }
                add_master_manager(owner);
                owner.add_slave_manager(m_objref);
                return;
            }
            catch (Exception ex) {
                rtcout.println(rtcout.ERROR, 
                        "Unknown exception caught.");
            }
        }

    }

    /**
     * <p> _this </p>
     *
     * @return RTM.Manager
     *
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

        rtcout.println(rtcout.DEBUG, "createINSManager()");
        if(java.lang.System.getProperty("develop_prop.debug")!=null){ 
            System.out.println("IN  createINSManager()");
        }
        try{
/*
            //Registers the reference
            m_mgr.getPOA().activate_object( this );
            if(java.lang.System.getProperty("develop_prop.debug")!=null){ 
                System.out.println("    activate_object");
            } 
            com.sun.corba.se.impl.orb.ORBImpl orb 
                        = (com.sun.corba.se.impl.orb.ORBImpl)m_mgr.getORB();
            orb.register_initial_reference( 
                        "manager", m_mgr.getPOA().servant_to_reference(this) );
            if(java.lang.System.getProperty("develop_prop.debug")!=null){ 
                System.out.println("    register_initial_reference");
            } 
*/

            //
            rtcout.println(rtcout.DEBUG, "gets object.");
            org.omg.CORBA.Object obj 
                    = m_mgr.getORB().resolve_initial_references("manager");
            rtcout.println(rtcout.DEBUG, "---010---");
            if(java.lang.System.getProperty("develop_prop.debug")!=null){ 
                System.out.println("    resolve_initial_references");
                if(obj==null){
                    System.out.println("    obj is null.");
                }
                else{
                    System.out.println("    obj is not null.>:"+obj);
                }
            }
            rtcout.println(rtcout.DEBUG, "---020---");
            this.m_objref = RTM.ManagerHelper.narrow(obj);
            rtcout.println(rtcout.DEBUG, "---030---");
            if(java.lang.System.getProperty("develop_prop.debug")!=null){ 
                System.out.println("    ManagerHelper.narrow");
            } 
/*
            POA poa = POAHelper.narrow(obj);
            if(java.lang.System.getProperty("develop_prop.debug")!=null){ 
                System.out.println("    POAHelper.narrow");
            } 
            poa.the_POAManager().activate();
            if(java.lang.System.getProperty("develop_prop.debug")!=null){ 
                System.out.println("    the_POAManager().activate()");
            } 

            byte[] id  = poa.activate_object( this );
            if(java.lang.System.getProperty("develop_prop.debug")!=null){ 
                System.out.println("activate_object");
            }
            org.omg.CORBA.Object ref = poa.id_to_reference(id);
            this.m_objref = ManagerHelper.narrow(ref);
        
            String ior = m_mgr.getORB().object_to_string(m_objref);
            rtcout.println(rtcout.DEBUG, 
                        "Manager's IOR information:"+ior);
 
            if(java.lang.System.getProperty("develop_prop.debug")!=null){ 
                System.out.println("Manager's IOR information:"+ior);
            }
*/
        }
        catch(Exception ex){
             rtcout.println(rtcout.WARN, 
                       "Manager CORBA servant creation failed."+ex);
             return false;
        }

        rtcout.println(rtcout.DEBUG, "---040---");
        if(java.lang.System.getProperty("develop_prop.debug")!=null){ 
            System.out.println("OUT createINSManager()");
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
        rtcout.println(rtcout.TRACE, "findManager(host_port = "+host_port+")");

        try{
            Properties config = m_mgr.getConfig();
            String name = config.getProperty("manager.name");
            String mgrloc = "corbaloc:iiop:1.2@"+host_port+"/"+name;
            rtcout.println(rtcout.DEBUG, "corbaloc: "+mgrloc);

            ORB orb = ORBUtil.getOrb();
            Object mobj;
            mobj = orb.string_to_object(mgrloc);
//            mobj = orb.resolve_initial_references("INSPOA");
//            mobj = m_mgr.getORB().resolve_initial_references("manager");
            if(java.lang.System.getProperty("develop_prop.debug")!=null){ 
                if(mobj==null){
                    System.out.println("    mobj is null.");
                }
                else{
                    System.out.println("    mobj is not null.>:"+mobj);
                }
            }
            RTM.Manager mgr 
                = RTM.ManagerHelper.narrow(mobj);
            if(java.lang.System.getProperty("develop_prop.debug")!=null){ 
                System.out.println("    ManagerHelper.narrow");
            }


            String ior;
            ior = orb.object_to_string(mobj);
            rtcout.println(rtcout.DEBUG, 
                    "Manager's IOR information: "+ior);
     
            return mgr;
        }
        catch(org.omg.CORBA.SystemException ex) {
            rtcout.println(rtcout.DEBUG, 
                "CORBA SystemException caught (CORBA."+ex.toString()+")");
            if(java.lang.System.getProperty("develop_prop.debug")!=null){ 
//                System.out.println("    CORBA SystemException caught >:"+ex);
            }
            return (RTM.Manager)null;
        }
        catch (Exception ex) {
            rtcout.println(rtcout.DEBUG, "Unknown exception caught.");
            if(java.lang.System.getProperty("develop_prop.debug")!=null){ 
                System.out.println("    Unknown exception caught.");
            }
            return (RTM.Manager)null;
        }

    }

    /**
     * <p> load_module </p>
     *
     * <p> Loading a module </p>
     * <p> This operation loads a specified loadable module and perform
     * initialization with the specified function. </p>
     *
     * @param pathname A path to a loading module.
     * @param initfunc Module initialization function.
     * @return The return code.
     */
    public RTC.ReturnCode_t load_module(final String pathname, 
                                            final String initfunc) {
        rtcout.println(rtcout.TRACE, 
                    "ManagerServant.load_module("+pathname+", "+initfunc+")");
        m_mgr.load(pathname, initfunc);
        return ReturnCode_t.RTC_OK;
    }

    /**
     * <p> unload_module </p>
     *
     * <p> Unloading a module </p >
     * <p> This operation unloads a specified loadable module. </p >
     * @param pathname A path to a loading module.
     * @return The return code.
     */
    public RTC.ReturnCode_t unload_module(final String pathname) {
        rtcout.println(rtcout.TRACE, 
                        "ManagerServant.unload_module("+pathname+")");
        try {
            m_mgr.unload(pathname);
        } catch(Exception ex) {
            rtcout.println(rtcout.WARN, 
                    "Exception caught.Not Found:"+pathname+" "+ex.toString());
        }
        return ReturnCode_t.RTC_OK;
    }

    /**
     * <p> get_loadable_modules </p>
     * <p> Getting loadable module profiles </p>
     * <p> This operation returns loadable module profiles. </p>
     * @return A module profile list.
     */
    public RTM.ModuleProfile[] get_loadable_modules() {
        rtcout.println(rtcout.TRACE, "get_loadable_modules()");
        // copy local module profiles
        Vector<Properties> prof = m_mgr.getLoadableModules();
        RTM.ModuleProfile[] cprof = new RTM.ModuleProfile[prof.size()];
        for (int i=0, len=prof.size(); i < len; ++i) {
            String dumpString = new String();
            dumpString = prof.elementAt(i)._dump(dumpString, 
                                                    prof.elementAt(i), 0);
            rtcout.println(rtcout.VERBOSE, dumpString);
            _SDOPackage.NVListHolder nvlist = new _SDOPackage.NVListHolder();
            NVUtil.copyFromProperties(nvlist, prof.elementAt(i));
            cprof[i] = new RTM.ModuleProfile(nvlist.value);
        }

        if (false) {
            // copy slaves' module profiles
            synchronized(m_slaveMutex) {
                rtcout.println(rtcout.DEBUG,
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
                        rtcout.println(rtcout.INFO,
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
     * <p> get_loaded_modules </p>
     * <p> Getting loaded module profiles </p>
     * <p> This operation returns loaded module profiles. </p>
     * @return A module profile list.
     */
    public RTM.ModuleProfile[] get_loaded_modules() {
        rtcout.println(rtcout.TRACE, "get_loaded_modules()");

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

                rtcout.println(rtcout.DEBUG,
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
                        rtcout.println(rtcout.INFO,
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
     * <p> get_factory_profiles </p>
     * <p> Getting component factory profiles </p>
     * <p> This operation returns component factory profiles from loaded
     * RT-Component module factory profiles. </p>
     * @return An RT-Component factory profile list.
     */
    public RTM.ModuleProfile[] get_factory_profiles() {
        rtcout.println(rtcout.TRACE, "get_factory_profiles()");

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
                rtcout.println(rtcout.DEBUG,
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
                        rtcout.println(rtcout.INFO,
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
        rtcout.println(rtcout.TRACE, "create_component("+module_name+")");

        String arg = module_name;
        int pos0 = arg.indexOf("&manager=");
        int pos1 = arg.indexOf("?manager=");

        if (pos0 < 0 && pos1 < 0){
            if (false) { //is_master()
                rtcout.println(rtcout.TRACE, 
                    "Master manager cannot create component: "+module_name);
                return null;
            }
        // create on this manager
            RTObject_impl rtc = m_mgr.createComponent(module_name);
            if (rtc == null) {
                if(java.lang.System.getProperty("develop_prop.debug")!=null){ 
                    System.err.println( 
                        "ManagerServant.create_component() RTC not found: "  
                        + module_name );
                }
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
        if(java.lang.System.getProperty("develop_prop.debug")!=null){ 
            System.out.println("    pos >:"+pos);
        }
    
        int endpos;
        if(java.lang.System.getProperty("develop_prop.debug")!=null){ 
            System.out.println("    arg >:"+arg);
        }
        endpos = arg.indexOf('&', pos + 1);
        if(java.lang.System.getProperty("develop_prop.debug")!=null){ 
            System.out.println("    endpos >:"+endpos);
        }
        if(endpos<0){
            endpos = arg.length();
        }
        String mgrstr = arg.substring(pos + 1, endpos - 1);
        if(java.lang.System.getProperty("develop_prop.debug")!=null){ 
            System.out.println("    Manager arg:  >:"+mgrstr);
        }
        rtcout.println(rtcout.VERBOSE, "Manager arg: "+mgrstr);
        String[] mgrvstr = mgrstr.split(":");
        if (mgrvstr.length != 2) {
            rtcout.println(rtcout.WARN, "Invalid manager name: "+mgrstr);
            return null;
        }
        int  eqpos = mgrstr.indexOf("=");
        if (eqpos < 0) {
            rtcout.println(rtcout.WARN, "Invalid argument: "+module_name);
            return null;
        }
        mgrstr =  mgrstr.substring(eqpos + 1);
        if(java.lang.System.getProperty("develop_prop.debug")!=null){ 
            System.out.println("    Manager is:  >:"+mgrstr);
        }
        rtcout.println(rtcout.DEBUG, "Manager is : "+mgrstr);

        // find manager
        RTM.Manager mgrobj = findManager(mgrstr);
        if (mgrobj==null) {
            List<String> cmd = new ArrayList();
            cmd.add("java");
            cmd.add("-jar");
            cmd.add("rtcd.jar");
            cmd.add("-p");
            cmd.add(mgrvstr[1]); // port number

            if(java.lang.System.getProperty("develop_prop.debug")!=null){ 
                System.out.println("    Invoking command: >:"+cmd);
            }
            rtcout.println(rtcout.DEBUG, "Invoking command: "+cmd);
            try{
                ProcessBuilder pb = new ProcessBuilder(cmd);
                Process p = pb.start();
            }
            catch(Exception ex){
                if(java.lang.System.getProperty("develop_prop.debug")!=null){ 
                    System.out.println(cmd + ": failed");
                }
                rtcout.println(rtcout.DEBUG, cmd + ": failed");
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
            rtcout.println(rtcout.WARN, "Manager cannot be found.");
            return null;
        }
    
        // create component on the manager    
        arg = arg.substring(pos + 1, endpos);
        rtcout.println(rtcout.DEBUG, "Creating component on "+mgrstr);
        rtcout.println(rtcout.DEBUG, "arg: "+arg);
        try {
            RTObject rtobj;
            rtobj = mgrobj.create_component(arg);
            rtcout.println(rtcout.DEBUG, "Component created "+arg);
            return rtobj;
        }
        catch (org.omg.CORBA.SystemException e) {
            rtcout.println(rtcout.DEBUG, 
                        "Exception was caught while creating component.");
            return null;
        }
    }

    /**
     * <p> delete_component </p>
     * <p> Deleting an RT-Component </p>
     * <p> This operation delete an RT-Component according to the string
     * argument. </p>
     *
     * @param instance_name
     * @return Return code
     *
     */
    public RTC.ReturnCode_t delete_component(final String instance_name) {
        rtcout.println(rtcout.TRACE, "delete_component("+instance_name+")");

        m_mgr.deleteComponent(instance_name);
        return ReturnCode_t.RTC_OK;
    }

    /**
     * <p> get_components </p>
     * <p> Getting RT-Component list running on this manager </p>
     * <p> This operation returns RT-Component list running 
     * on this manager. </p>
     * @return A list of RT-Components
     */
    public RTC.RTObject[] get_components() {
        rtcout.println(rtcout.TRACE, "get_component()");

        Vector<RTObject_impl> rtcs = m_mgr.getComponents();
        RTCListHolder crtcs = new RTCListHolder();
        crtcs.value = new RTObject[rtcs.size()];

        for (int i=0, len=rtcs.size(); i < len; ++i) {
            crtcs.value[i] = rtcs.elementAt(i).getObjRef();
        }
//        return crtcs.value;
        // get slaves' component references
        rtcout.println(rtcout.DEBUG,
                                    m_slaves.length+" slaves exists.");
        for (int i=0, len=m_slaves.length; i < len; ++i) {
            try {
                if (m_slaves[i]!=null) {
                    RTC.RTObject[] srtcs;
                    srtcs = m_slaves[i].get_components();
                    RTC.RTCListHolder holder
                            = new RTC.RTCListHolder(srtcs);
                    CORBA_SeqUtil.push_back_list(holder, crtcs);
                    srtcs = holder.value;
                    continue;
                  }
            }
            catch(Exception ex) {
                rtcout.println(rtcout.INFO,
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
     * <p> get_component_profiles </p>
     * <p> Getting RT-Component's profile list running on this manager </p>
     * <p> This operation returns RT-Component's profile list running on
     * this manager. </p>
     * @return A list of RT-Components' profiles
     */
    public RTC.ComponentProfile[] get_component_profiles() {
        rtcout.println(rtcout.TRACE, "get_component_profiles()");

        ComponentProfileListHolder cprofs = new ComponentProfileListHolder();
        Vector<RTObject_impl> rtcs = m_mgr.getComponents();
        cprofs.value = new ComponentProfile[rtcs.size()];

        for (int i=0, len=rtcs.size(); i < len; ++i) {
            cprofs.value[i] = rtcs.elementAt(i).get_component_profile();
        }
        // copy slaves' component profiles
        synchronized(m_slaveMutex) {
            rtcout.println(rtcout.DEBUG,
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
                    rtcout.println(rtcout.INFO,
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
     * <p> get_profile </p>
     * <p> Getting this manager's profile. </p>
     * <p> This operation returns this manager's profile. </p>
     * @return Manager's profile
     */
    public RTM.ManagerProfile get_profile() {
        rtcout.println(rtcout.TRACE, "get_profile()");

        NVListHolder nvlist = new NVListHolder();
        ManagerProfile prof = new ManagerProfile();
        NVUtil.copyFromProperties(nvlist,
                                 (m_mgr.getConfig().getNode("manager")));
        prof.properties = nvlist.value;
        return prof;
    }

    /**
     * <p> get_configuration </p>
     * <p> Getting this manager's configuration. </p>
     * <p> This operation returns this manager's configuration. </p>
     * @return Manager's configuration
     */
    public _SDOPackage.NameValue[] get_configuration() {
        rtcout.println(rtcout.TRACE, "get_configuration()");

        NVListHolder nvlist = new NVListHolder();
        NVUtil.copyFromProperties(nvlist, m_mgr.getConfig());
        return nvlist.value;
    }

    /**
     * <p> set_configuration </p>
     * <p> Setting manager's configuration </p>
     * <p> This operation sets managers configuration. </p>
     * @param name A configuration key name to be set
     * @param value A configuration value to be set
     * @return Return code
     */
    public RTC.ReturnCode_t set_configuration(final String name, 
                                                    final String value) {
        rtcout.println(rtcout.TRACE, "set_configuration()");

        m_mgr.getConfig().setProperty(name, value);
        return ReturnCode_t.RTC_OK;
    }

    /**
     * <p> Whether this manager is master or not </p>
     * <p> It returns "True" if this manager is a master, and it returns
     * "False" in other cases. </p>
     * @return A boolean value that means it is master or not.
     */
    public boolean is_master() {
        rtcout.println(rtcout.TRACE, "is_master(): "+m_isMaster);
        return m_isMaster;

    }

    /**
     * <p> Getting master managers </p>
     * <p> This operation returns master manager list if this manager is
     * slave. If this manager is master, an empty sequence would be
     * returned. </p>
     * @return Master manager list
     */
    public RTM.Manager[] get_master_managers() {
        rtcout.println(rtcout.TRACE, "get_master_managers()");

        synchronized(m_masterMutex) {
            RTM.ManagerListHolder holder = new RTM.ManagerListHolder(m_masters);
            return holder.value;
        }
    }

    /**
     * <p> Getting a master manager </p>
     * <p> This operation returns a master manager with specified id. If
     * the manager with the specified id does not exist, nil object
     * reference would be returned. </p>
     * @return ReturnCode_t
     */
    public ReturnCode_t add_master_manager(RTM.Manager mgr) {
        synchronized(m_masterMutex) {
            long index;
            rtcout.println(rtcout.TRACE, 
                    "add_master_manager(), "+m_masters.length+" masters");

            RTM.ManagerListHolder holder = new RTM.ManagerListHolder(m_masters);
            index = CORBA_SeqUtil.find(holder, new is_equiv(mgr));
    
            if (!(index < 0)) {// found in my list
                rtcout.println(rtcout.ERROR, "Already exists.");
                return ReturnCode_t.BAD_PARAMETER;
            }
    
            CORBA_SeqUtil.push_back(holder, (RTM.Manager)mgr._duplicate());
            m_masters = holder.value;
            rtcout.println(rtcout.TRACE, 
                "add_master_manager() done, "+m_masters.length+" masters");
            return ReturnCode_t.RTC_OK;
        }
    }

    /**
     * <p> Removing a master manager </p>
     * <p> This operation removes a master manager from this manager. </p>
     * @param mgr A master manager
     * @return ReturnCode_t 
     */
    public ReturnCode_t remove_master_manager(RTM.Manager mgr) {
        synchronized(m_masterMutex) {
            rtcout.println(rtcout.TRACE, 
                    "remove_master_manager(), "+m_masters.length+" masters");

            long index;
            RTM.ManagerListHolder holder = new RTM.ManagerListHolder(m_masters);
            index = CORBA_SeqUtil.find(holder, new is_equiv(mgr));
    
            if (index < 0) { // not found in my list
                rtcout.println(rtcout.ERROR, "Not found.");
                return ReturnCode_t.BAD_PARAMETER;
            }
    
            CORBA_SeqUtil.erase(holder, (int)index);
            m_masters = holder.value;
            rtcout.println(rtcout.TRACE, 
                "remove_master_manager() done, "+m_masters.length+" masters");
            return ReturnCode_t.RTC_OK;
        }
    }    


    /**
     * <p> Getting slave managers </p>
     * <p> This operation returns slave manager list if this manager is
     * slave. If this manager is slave, an empty sequence would be
     * returned. </p>
     * @return Slave manager list
     */
    public RTM.Manager[] get_slave_managers() {
        synchronized(m_masterMutex) {
            rtcout.println(rtcout.TRACE, 
                "get_slave_managers(), "+m_slaves.length+" slaves");
    
            RTM.ManagerListHolder holder = new RTM.ManagerListHolder(m_slaves);
            return holder.value;
        }
    }

    /**
     * <p> Getting a slave manager </p>
     * <p> This operation add a slave manager to this manager. </p>
     * @param mgr A slave manager
     * @return ReturnCode_t
     */
    public ReturnCode_t add_slave_manager(RTM.Manager mgr) {
        synchronized(m_masterMutex) {
            rtcout.println(rtcout.TRACE, 
                "add_slave_manager(), "+m_slaves.length+" slaves");
    
            long index;
            RTM.ManagerListHolder holder = new RTM.ManagerListHolder(m_slaves);
            index = CORBA_SeqUtil.find(holder, new is_equiv(mgr));
    
            if (!(index < 0)) { // found in my list
                rtcout.println(rtcout.ERROR, "Already exists.");
                return ReturnCode_t.BAD_PARAMETER;
            }
    
            CORBA_SeqUtil.push_back(holder, (RTM.Manager)mgr._duplicate());
            m_slaves = holder.value;
            rtcout.println(rtcout.TRACE, 
                "add_slave_manager() done, "+m_slaves.length+" slaves");
            return ReturnCode_t.RTC_OK;
        }
    }

    /**
     * <p> Removing a slave manager </p>
     * <p> This operation removes a slave manager from this manager. </p>
     * @param mgr A slave manager
     * @return ReturnCode_t 
     */
    public ReturnCode_t remove_slave_manager(RTM.Manager mgr) {
        synchronized(m_masterMutex) {
            rtcout.println(rtcout.TRACE, 
                "remove_slave_manager(), "+m_slaves.length+" slaves");
            long index;
            RTM.ManagerListHolder holder = new RTM.ManagerListHolder(m_slaves);
            index = CORBA_SeqUtil.find(holder, new is_equiv(mgr));
    
            if (index < 0) {// not found in my list
                rtcout.println(rtcout.ERROR, "Not found.");
                return ReturnCode_t.BAD_PARAMETER;
            }
    
            CORBA_SeqUtil.erase(holder, (int)index);
            m_slaves = holder.value;
            rtcout.println(rtcout.TRACE, 
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
     * <p> fork </p>
     */
    public RTC.ReturnCode_t fork() {
    //    m_mgr.fork();
        return ReturnCode_t.RTC_OK;
    }

    /**
     * <p> shutdown </p>
     */
    public RTC.ReturnCode_t shutdown() {
        m_mgr.terminate();
        return ReturnCode_t.RTC_OK;
    }

    /**
     * <p> restart </p>
     */
    public ReturnCode_t restart() {
    //    m_mgr.restart();
        return ReturnCode_t.RTC_OK;
    }

    /**
     * <p> get_service </p>
     */
    public org.omg.CORBA.Object get_service(final String name) {
        return null;
    }

    /**
     * <p> getObjRef </p>
     */
    public RTM.Manager getObjRef() {
        return (RTM.Manager)m_objref;
    }

    /**
     * <p> setObjRef </p>
     */
    public void setObjRef(final RTM.Manager rtobj) {
        m_objref = rtobj;
    }

    /**
     * <p></p>
     */
    private jp.go.aist.rtm.RTC.Manager m_mgr;

    /**
     * <p></p>
     */
    private RTM.Manager m_objref;

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
