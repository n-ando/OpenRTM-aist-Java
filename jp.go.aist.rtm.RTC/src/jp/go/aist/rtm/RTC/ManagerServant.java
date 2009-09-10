package jp.go.aist.rtm.RTC;

import org.omg.CORBA.Object;

import java.util.Vector;

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



/**
 * <p> ManagerServant </p>
 */
public class ManagerServant extends ManagerPOA {

    /**
     * <p> Constructor </p>
     */
    public ManagerServant() {
        m_mgr = jp.go.aist.rtm.RTC.Manager.instance();
//        this.m_objref = RTM.ManagerHelper.narrow(this._this()._duplicate());
//        this.m_objref = RTM.ManagerHelper.narrow(this._this());
        this.m_objref = this._this();
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
     * <p> load_module </p>
     *
     * @param pathname String
     * @param initfunc String
     * @return RTC.ReturnCode_t
     */
    public RTC.ReturnCode_t load_module(final String pathname, final String initfunc) {
        m_mgr.load(pathname, initfunc);
        return ReturnCode_t.RTC_OK;
    }
  
    /**
     * <p> unload_module </p>
     *
     * @param pathname String
     * @return RTC.ReturnCode_t
     */
    public RTC.ReturnCode_t unload_module(final String pathname) {
      try {
          m_mgr.unload(pathname);
      } catch(Exception ex) {
      }
      return ReturnCode_t.RTC_OK;
    }
  
    /**
     * <p> get_loadable_modules </p>
     *
     * @return RTM.ModuleProfile[]
     */
    public RTM.ModuleProfile[] get_loadable_modules() {
        ModuleProfileListHolder cprof = new ModuleProfileListHolder();

        NVListHolder nvlist = new NVListHolder();
        Vector<Properties> prof = m_mgr.getLoadableModules();
        cprof.value = new ModuleProfile[prof.size()];

//zxc        cprof.length(prof.size());
System.out.println( "ManagerServant::get_loadable_modules--000--prof.size():"+ prof.size());
        for (int i=0; i < prof.size(); ++i) {
//            NVUtil.copyFromProperties(cprof.value[i].properties,
//                                       prof.elementAt(i));
            NVUtil.copyFromProperties(nvlist, prof.elementAt(i));
	    cprof.value[i].properties = nvlist.value;
	}
        return cprof.value;
    }
  
    /**
     * <p> get_loaded_modules </p>
     *
     * @return RTM.ModuleProfile[]
     */
    public RTM.ModuleProfile[] get_loaded_modules() {
        ModuleProfileListHolder cprof = new ModuleProfileListHolder();
  
        NVListHolder nvlist = new NVListHolder();
        Vector<Properties> prof = m_mgr.getLoadedModules();
        cprof.value = new ModuleProfile[prof.size()];
    
//zxc        cprof.length(prof.size());
System.out.println( "ManagerServant::get_loaded_modules--000--prof.size():"+ prof.size());
        for (int i=0; i < prof.size(); ++i) {
//            NVUtil.copyFromProperties(cprof.value[i].properties,
//                                       prof.elementAt(i));
            NVUtil.copyFromProperties(nvlist, prof.elementAt(i));
	    cprof.value[i].properties = nvlist.value;
        }
        return cprof.value;
    }
  
    /**
     * <p> get_factory_profiles </p>
     *
     * @return RTM.ModuleProfile[]
     */
    public RTM.ModuleProfile[] get_factory_profiles() {

        _SDOPackage.NVListHolder nvlist = new _SDOPackage.NVListHolder();
        nvlist.value = new _SDOPackage.NameValue[0];

        Vector<Properties> prof = m_mgr.getFactoryProfiles();
        ModuleProfile[] cprof = new ModuleProfile[prof.size()];
//zxc       cprof.length(prof.size());
        
        for (int i=0, len=prof.size(); i < len; ++i) {
//            NVUtil.copyFromProperties(cprof[i].properties,prof.elementAt(i));
            NVUtil.copyFromProperties(nvlist, prof.elementAt(i));
            ModuleProfile cprof2 = new ModuleProfile();
            cprof2.properties =  new _SDOPackage.NameValue [nvlist.value.length];
            cprof2.properties =  nvlist.value;
            cprof[i] = cprof2;
        }

        return cprof;
    }

    /**
     * <p> create_component </p>
     *
     * @param module_name
     * @return RTC.RTObject
     *
     */
    public RTC.RTObject create_component(final String module_name) {
        System.out.println( "Manager::create_component: "  + module_name );
        RTObject_impl rtc = m_mgr.createComponent(module_name);
        if (rtc == null) {
            System.out.println( "RTC not found: "  + module_name );
            return null;
        }
        return rtc.getObjRef();
    }
  
    /**
     * <p> delete_component </p>
     *
     * @param instance_name
     * @return RTC.ReturnCode_t
     *
     */
    public RTC.ReturnCode_t delete_component(final String instance_name) {
        m_mgr.deleteComponent(instance_name);
        return ReturnCode_t.RTC_OK;
    }
  
    /**
     * <p> get_components </p>
     *
     * @return RTC.RTObject[]
     */
    public RTC.RTObject[] get_components() {
        Vector<RTObject_impl> rtcs = m_mgr.getComponents();
        RTCListHolder crtcs = new RTCListHolder();
        crtcs.value = new RTObject[rtcs.size()];
//zxc        crtcs.length(rtcs.size());
        for (int i=0, len=rtcs.size(); i < len; ++i) {
	    crtcs.value[i] = rtcs.elementAt(i).getObjRef();
//            CORBA_SeqUtil.push_back(crtcs, rtcs.elementAt(i).getObjRef());
        }
        return crtcs.value;
    }
  
    /**
     * <p> get_component_profiles </p>
     *
     * @return RTC.ComponentProfile[]
     */
    public RTC.ComponentProfile[] get_component_profiles() {

        ComponentProfileListHolder cprofs = new ComponentProfileListHolder();

        Vector<RTObject_impl> rtcs = m_mgr.getComponents();
        cprofs.value = new ComponentProfile[rtcs.size()];

        for (int i=0, len=rtcs.size(); i < len; ++i) {
            cprofs.value[i] = rtcs.elementAt(i).get_component_profile();
	    //            CORBA_SeqUtil.push_back(cprofs, rtcs.elementAt(i).get_component_profile());
        }
        return cprofs.value;
    }
  
    /**
     * <p> get_profile </p>
     *
     * @return RTM.ManagerProfile
     */
    public RTM.ManagerProfile get_profile() {
        NVListHolder nvlist = new NVListHolder();
        ManagerProfile prof = new ManagerProfile();
//zxc        NVUtil.copyFromProperties(prof.properties,
//                                  (m_mgr.getConfig().getNode("manager")));
        NVUtil.copyFromProperties(nvlist,
                                 (m_mgr.getConfig().getNode("manager")));
	prof.properties = nvlist.value;
        return prof;
    }
  
    /**
     * <p> get_configuration </p>
     * 
     * @return _SDOPackage.NameValue[]
     */
    public _SDOPackage.NameValue[] get_configuration() {
        NVListHolder nvlist = new NVListHolder();
        NVUtil.copyFromProperties(nvlist, m_mgr.getConfig());
        return nvlist.value;
    }
  
    /**
     * <p> set_configuration </p>
     *
     * @param name
     * @param value
     * @return RTC.ReturnCode_t
     */
    public RTC.ReturnCode_t set_configuration(final String name, final String value) {
        m_mgr.getConfig().setProperty(name, value);
        return ReturnCode_t.RTC_OK;
    }
  
    /**
     * <p> get_owner </p>
     *
     * @return RTM.Manager
     */
    public RTM.Manager get_owner() {
        return null;
    }
  
    /**
     * <p> set_owner </p>
     *
     * @param mgr
     * @return RTM.Manager
     *
     */
    public RTM.Manager set_owner(RTM.Manager mgr) {
        return null;
    }
  
    /**
     * <p> get_child </p>
     *
     * @return RTM.Manager
     */
    public RTM.Manager get_child() {
        return null;
    }
  
    /**
     * <p> set_child </p>
     */
    public RTM.Manager set_child(RTM.Manager mgr) {
        return null;
    }
  
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
//        return (RTM.Manager)m_objref._this()._duplicate();
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

}

