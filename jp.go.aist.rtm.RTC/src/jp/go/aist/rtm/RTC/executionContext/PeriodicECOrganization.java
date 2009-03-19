package jp.go.aist.rtm.RTC.executionContext;

import org.omg.CORBA.SystemException;

import java.util.Vector;
import java.util.Iterator;

import RTC.ComponentProfile;
import RTC.PortService;
import RTC.PortProfile;
import RTC.RTObject;
import RTC.ExecutionContext;
import RTC.ExecutionContextListHolder;
import RTC.ExecutionContextService;


import OpenRTM.DataFlowComponent;
import OpenRTM.DataFlowComponentHelper;

import _SDOPackage.Configuration;
import _SDOPackage.Organization;
import _SDOPackage.SDO;
import _SDOPackage.SDOListHolder;
import _SDOPackage.InterfaceNotImplemented;
import _SDOPackage.InternalError;
import _SDOPackage.InvalidParameter;
import _SDOPackage.NotAvailable;

import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.SDOPackage.Organization_impl;

/**
 * <p> PeriodicECOrganization </p>
 */
public class PeriodicECOrganization extends Organization_impl {
    /**
     * <p></p>
     */
    protected RTObject_impl m_rtobj;

    /**
     * <p></p>
     */
    protected ExecutionContext m_ec;
    
    /**
     * <p> Member </p>
     */
    protected class Member {
        /**
         * <p> Constructor </p>
         */
        public Member(RTObject rtobj) {
            rtobj_ = rtobj;
            profile_ = rtobj.get_component_profile();
            eclist_ = rtobj.get_owned_contexts();
            try {
                config_ = rtobj.get_configuration();
            } catch(SystemException e) {
                ;
            } catch(InterfaceNotImplemented e) {
                ;
            } catch(NotAvailable e) {
                ;
            } catch(InternalError e) {
                ;
            }
        }

        /**
         * <p> Constructor </p>
         */
        public Member(final Member x) {
            rtobj_ = (RTObject)x.rtobj_._duplicate();
            profile_ = new ComponentProfile();
            profile_ = x.profile_;
            eclist_ = x.eclist_;
            config_ = (Configuration)x.config_._duplicate();
        }

        /**
         * <p></p>
         */
        public Member operator(final Member x) {
            Member tmp = x;
            tmp.swap(this);
            return this;
        }

        /**
         * <p></p>
         */
        public void swap(Member x) {
            RTObject rtobj= x.rtobj_;
            ComponentProfile profile = x.profile_;
            ExecutionContext[] eclist;
            eclist = x.eclist_;
            Configuration config = x.config_;

            x.rtobj_ = this.rtobj_;
            x.profile_ = this.profile_;
            x.eclist_ = this.eclist_;
            x.config_ = this.config_;

            this.rtobj_ = rtobj;
            this.profile_ = profile;
            this.eclist_ = eclist;
            this.config_ = config;
         }
      
        /**
         * <p></p>
         */
        public RTObject rtobj_;
        /**
         * <p></p>
         */
        public ComponentProfile profile_;
        /**
         * <p></p>
         */
        public ExecutionContext[] eclist_;
        /**
         * <p></p>
         */
        public Configuration config_;

    };

    /**
     * <p></p>
     */
    protected Vector<Member> m_rtcMembers = new Vector<Member>();


    /**
     * <p></p>
     */
    static final String periodicecsharedcomposite_spec[] = {
        "implementation_id", "PeriodicECSharedComposite",
        "type_name",         "PeriodicECSharedComposite",
        "description",       "PeriodicECSharedComposite",
        "version",           "1.0",
        "vendor",            "jp.go.aist",
        "category",          "composite.PeriodicECShared",
        "activity_type",     "DataFlowComponent",
        "max_instance",      "0",
        "language",          "C++",
        "lang_type",         "compile",
        "exported_ports",    "",
        "conf.default.members", "",
        "conf.default.exported_ports", "",
        ""
      };

    /**
     * <p></p>
     */
    public PeriodicECOrganization(RTObject_impl rtobj) {
        super(rtobj.getObjRef());
System.out.println( "PeriodicECOrganization::PeriodicECOrganization--000--" );
        m_rtobj = rtobj;
        m_ec = null;
    }


    /**
     * <p></p>
     */
     public boolean add_members(final SDO[] sdo_list) 
        throws SystemException, InvalidParameter, NotAvailable, InternalError {

        for (int i=0, len=sdo_list.length; i < len; ++i) {
            final SDO sdo = sdo_list[i];
 
            if (sdo == null) continue;
            // narrowing: SDO . RTC (DataFlowComponent)
            DataFlowComponent dfc = DataFlowComponentHelper.narrow(sdo);
            if (dfc == null) continue;

            Member member = new Member((DataFlowComponent)dfc._duplicate());
            stopOwnedEC(member);
            addOrganizationToTarget(member);
            addParticipantToEC(member);
            delegatePort(member);
            m_rtcMembers.add(member);
        }

        boolean result;
//zxc        result = Organization_impl.add_members(sdo_list);
        try {
            result = super.add_members(sdo_list);
        } catch(InvalidParameter e) {
            throw new InvalidParameter();
        } catch(NotAvailable e) {
            throw new NotAvailable();
        } catch(InternalError e) {
            throw new InternalError();
        }

        return result;
    }

    /**
     * <p></p>
     */
    public boolean set_members(final SDO[] sdo_list)
        throws SystemException, InvalidParameter, NotAvailable, InternalError {

        m_rtcMembers.clear();

        for (int i=0, len=sdo_list.length; i < len; ++i) {
            final SDO sdo  = sdo_list[i];

            if (sdo == null) continue;
            // narrowing: SDO . RTC (DataFlowComponent)
            DataFlowComponent dfc = DataFlowComponentHelper.narrow(sdo);
            if (dfc == null) continue;

            Member member = new Member((DataFlowComponent)dfc._duplicate());
            stopOwnedEC(member);
            addOrganizationToTarget(member);
            addParticipantToEC(member);
            delegatePort(member);
            m_rtcMembers.add(member);
        }
    
        boolean result;
//        result = Organization_impl.set_members(sdo_list);
        try { 
            result = super.set_members(sdo_list);
        } catch(InvalidParameter e) {
            throw new InvalidParameter();
        } catch(NotAvailable e) {
            throw new NotAvailable();
        } catch(InternalError e) {
            throw new InternalError();
        }

        return result;
      }

    /**
     * <p></p>
     */
    public boolean remove_member(final String id)
        throws SystemException, InvalidParameter, NotAvailable, InternalError {
        for (Iterator it = m_rtcMembers.iterator();it.hasNext();) {
            Member member = (Member)it.next();
            if (member.profile_.instance_name.indexOf(id) != 0 ) continue;

            removePort(member);
            removeParticipantFromEC(member);
            removeOrganizationFromTarget(member);
            startOwnedEC(member);
            it.remove();
         }

        boolean result;
        try {
            result = super.remove_member(id);
        } catch(InvalidParameter e) {
            throw new InvalidParameter();
        } catch(NotAvailable e) {
            throw new NotAvailable();
        } catch(InternalError e) {
            throw new InternalError();
        }
        return result;
     }

  
    /**
     * <p></p>
     */
    public void removeAllMembers() {
        for (Iterator it = m_rtcMembers.iterator();it.hasNext();) {
            Member member = (Member)it.next();
            removePort(member);
            removeParticipantFromEC(member);
            removeOrganizationFromTarget(member);
            startOwnedEC(member);
            try {
                super.remove_member(member.profile_.instance_name); 
            } catch(SystemException e) {
                ;
            } catch(InvalidParameter e) {
                ;
            } catch(NotAvailable e) {
                ;
            } catch(InternalError e) {
                ;
            }
            it.remove();
        }
        m_rtcMembers.clear();
    }

    /**
     * <p></p>
     */
    public boolean sdoToDFC(final SDO sdo, DataFlowComponent dfc) {
        if (sdo == null) return false;
    
        // narrowing: SDO . RTC (DataFlowComponent)
        dfc = DataFlowComponentHelper.narrow(sdo);
        if (dfc == null) return false;
        return true;
    }
  
    /**
     * <p></p>
     */
    public void stopOwnedEC(Member member) {
        // stop target RTC's ExecutionContext
        ExecutionContextListHolder ecs = new ExecutionContextListHolder();
        ecs.value = member.eclist_;
        for (int i=0, len=ecs.value.length; i < len; ++i) {
            ecs.value[i].stop();
        }
        return;
     }

    /**
     * <p></p>
     */
    public void startOwnedEC(Member member) {
        // start target RTC's ExecutionContext
        ExecutionContextListHolder ecs = new ExecutionContextListHolder();
        ecs.value  = member.eclist_;
        for (int i=0, len=ecs.value.length; i < len; ++i) {
            ecs.value[i].start();
        }
        return;
    }

    /**
     * <p></p>
     */
    public void addOrganizationToTarget(Member member) {
        // get given RTC's configuration object
        Configuration conf = member.config_;
        if (conf==null) return;
    
        // set organization to target RTC's conf
        try {
            conf.add_organization((Organization)m_objref._duplicate());
        } catch(SystemException e) {
            ;
        } catch(InvalidParameter e) {
            ;
        } catch(NotAvailable e) {
            ;
        } catch(InternalError e) {
            ;
        }
    }

    /**
     * <p></p>
     */
    public void removeOrganizationFromTarget(Member member) {
        // get given RTC's configuration object
        Configuration conf = member.config_;
        if (conf == null) return;
    
        // set organization to target RTC's conf
        try{
//zxc        conf.remove_organization(string_dup(m_pId));
            String str = new String();
            str = m_pId;
            conf.remove_organization(str);
        } catch(SystemException e) {
            ;
        } catch(InvalidParameter e) {
            ;
        } catch(NotAvailable e) {
            ;
        } catch(InternalError e) {
            ;
        }
    }

    /**
     * <p></p>
     */
    public void addParticipantToEC(Member member) {
        if (m_ec == null) {
            ExecutionContext[] ecs = m_rtobj.get_owned_contexts();
            if (ecs.length > 0) {
                m_ec = (ExecutionContext)ecs[0]._duplicate();
            } else {
                return;
            }
        }
        // set ec to target RTC
        m_ec.add_component((RTObject)member.rtobj_._duplicate());
    }

    /**
     * <p></p>
     */
    public void removeParticipantFromEC(Member member) { 
        if (m_ec == null) {
            ExecutionContext[] ecs = m_rtobj.get_owned_contexts();
            if (ecs.length > 0) {
                m_ec = (ExecutionContext)ecs[0]._duplicate();
            } else {
                return;
            }
        }
        m_ec.remove_component((RTObject)member.rtobj_._duplicate());
    }

    /**
     * <p></p>
     */
    public void delegatePort(Member member) {
        String exported_ports = new String(); 
        exported_ports = m_rtobj.getProperties().getProperty("exported_ports");
      
        // get comp's/ports's profile
        ComponentProfile cprof = new ComponentProfile();
        cprof = member.profile_;
        PortProfile[] plist = new PortProfile[cprof.port_profiles.length];
        plist = cprof.port_profiles;

        // port delegation
        for (int i=0, len=plist.length; i < len; ++i) {
            // port name . comp_name.port_name
            String port_name = cprof.instance_name;
            port_name.concat("\\."); 
            port_name.concat(plist[i].name);

            int pos = exported_ports.indexOf(port_name);
            if (pos == -1) continue;

            m_rtobj.registerPort(
                        (PortService)plist[i].port_ref._duplicate());

        }
    }

    /**
     * <p></p>
     */
    public void removePort(Member member) {
        String exported_ports = new String(); 
        exported_ports = m_rtobj.getProperties().getProperty("exported_ports");
    
        // get comp's/ports's profile
        ComponentProfile cprof= new ComponentProfile();
        cprof= member.profile_;
        PortProfile[] plist = cprof.port_profiles;
    
        // port delegation
        for (int i=0, len=plist.length; i < len; ++i) {
            // port name . comp_name.port_name
            String port_name = cprof.instance_name;
            port_name.concat("."); 
            port_name.concat(plist[i].name);
        
            int pos = exported_ports.indexOf(port_name);
            if (pos == -1) continue;
        
            m_rtobj.deletePort(
                            (PortService)plist[i].port_ref._duplicate());
        
        }
     }

};





