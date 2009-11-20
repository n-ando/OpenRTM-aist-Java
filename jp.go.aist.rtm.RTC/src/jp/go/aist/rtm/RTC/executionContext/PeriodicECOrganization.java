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
import jp.go.aist.rtm.RTC.log.Logbuf;
import jp.go.aist.rtm.RTC.util.StringUtil;

/**
 * <p> PeriodicECOrganizationクラス </p>
 */
public class PeriodicECOrganization extends Organization_impl {
    /**
     * <p>RTコンポーネントオブジェクト</p>
     */
    protected RTObject_impl m_rtobj;

    /**
     * <p>ExecutionContextオブジェクト</p>
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
//            rtobj_ = rtobj;
            rtobj_ = (RTObject)rtobj._duplicate();
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
//            profile_ = new ComponentProfile();
            profile_ = x.profile_;
            eclist_ = x.eclist_;
            config_ = (Configuration)x.config_._duplicate();
        }

        /**
         * <p>operator</p>
         */
        public Member operator(final Member x) {
            Member tmp = x;
            tmp.swap(this);
            return this;
        }

        /**
         * <p>swap</p>
         */
        public void swap(Member x) {
            RTObject rtobj = x.rtobj_;
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
         * <p>RTObject</p>
         */
        public RTObject rtobj_;
        /**
         * <p>ComponentProfile</p>
         */
        public ComponentProfile profile_;
        /**
         * <p>ExecutionContext</p>
         */
        public ExecutionContext[] eclist_;
        /**
         * <p>Configuration</p>
         */
        public Configuration config_;

    };

    /**
     * <p>RTコンポーネントメンバー</p>
     */
    protected Vector<Member> m_rtcMembers = new Vector<Member>();

    /**
     * <p>ポートリスト</p>
     */
    protected Vector<String> m_expPorts = new Vector<String>();

    /**
     * <p>Constructor</p>
     */
    public PeriodicECOrganization(RTObject_impl rtobj) {
        super(rtobj.getObjRef());
        m_rtobj = rtobj;
        m_ec = null;
        rtcout = new Logbuf("PeriodicECOrganization");
    }

    /**
     * <p>Organizationメンバーを追加する。</p>
     */
     public boolean add_members(final SDO[] sdo_list) 
	 throws SystemException, InvalidParameter, NotAvailable, InternalError {

        rtcout.println(rtcout.DEBUG, "add_members()");
        updateExportedPortsList();

        for (int i=0, len=sdo_list.length; i < len; ++i) {
            final SDO sdo = sdo_list[i];
            if (sdo == null) {
                continue;
            }
            // narrowing: SDO . RTC (DataFlowComponent)
            DataFlowComponent dfc = DataFlowComponentHelper.narrow(sdo);
            if (dfc == null) {
                continue;
            }
            Member member = new Member((DataFlowComponent)dfc._duplicate());
            stopOwnedEC(member);
            addOrganizationToTarget(member);
            addParticipantToEC(member);
            addPort(member, m_expPorts);
            m_rtcMembers.add(member);
        }

        boolean result;
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
     * <p>Organizationメンバーをセットする。</p>
     */
    public boolean set_members(final SDO[] sdo_list)
        throws SystemException, InvalidParameter, NotAvailable, InternalError {

        rtcout.println(rtcout.DEBUG, "set_members()");
        removeAllMembers();
        updateExportedPortsList();

        for (int i=0, len=sdo_list.length; i < len; ++i) {
            final SDO sdo  = sdo_list[i];
            if (sdo == null) {
                continue;
            }
            // narrowing: SDO . RTC (DataFlowComponent)
            DataFlowComponent dfc = DataFlowComponentHelper.narrow(sdo);
            if (dfc == null) {
                continue;
            }
            Member member = new Member((DataFlowComponent)dfc._duplicate());
            stopOwnedEC(member);
            addOrganizationToTarget(member);
            addParticipantToEC(member);
            addPort(member, m_expPorts);
            m_rtcMembers.add(member);
        }

        boolean result;
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
     * <p>Organizationメンバーを削除する。</p>
     */
    public boolean remove_member(final String id)
        throws SystemException, InvalidParameter, NotAvailable, InternalError {

        rtcout.println(rtcout.DEBUG, "remove_member()" + " id=(" + id + ")");
        for (Iterator it = m_rtcMembers.iterator(); it.hasNext();) {
            Member member = (Member)it.next();
            if (member.profile_.instance_name.indexOf(id) != 0 ) {
                continue;
            }
            removePort(member, m_expPorts);
            m_rtobj.getProperties().setProperty("conf.default.exported_ports", StringUtil.flatten(m_expPorts));
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
     * <p>Organizationメンバーを全て削除する。</p>
     */
    public void removeAllMembers() {
        rtcout.println(rtcout.TRACE, "removeAllMembers()");
        updateExportedPortsList();

        for (Iterator it = m_rtcMembers.iterator(); it.hasNext();) {
            Member member = (Member)it.next();
            removePort(member, m_expPorts);
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
        m_expPorts.clear();
    }

    /**
     * <p>SDOからDFCへの変換</p>
     */
    public boolean sdoToDFC(final SDO sdo, DataFlowComponent dfc) {
        if (sdo == null) {
            return false;
        }
        // narrowing: SDO . RTC (DataFlowComponent)
        dfc = DataFlowComponentHelper.narrow(sdo);
        if (dfc == null) {
            return false;
        }
        return true;
    }

    /**
     * <p>Owned ExecutionContext を停止させる。</p>
     */
    public void stopOwnedEC(Member member) {
        rtcout.println(rtcout.DEBUG, "stopOwnedEC()");

        // stop target RTC's ExecutionContext
        ExecutionContextListHolder ecs = new ExecutionContextListHolder();
        ecs.value = member.eclist_;
        for (int i=0, len=ecs.value.length; i < len; ++i) {
            ecs.value[i].stop();
        }
        return;
     }

    /**
     * <p>Owned ExecutionContext を起動する。</p>
     */
    public void startOwnedEC(Member member) {
        rtcout.println(rtcout.DEBUG, "startOwnedEC()");

        // start target RTC's ExecutionContext
        ExecutionContextListHolder ecs = new ExecutionContextListHolder();
        ecs.value  = member.eclist_;
        for (int i=0, len=ecs.value.length; i < len; ++i) {
            ecs.value[i].start();
        }
        return;
    }

    /**
     * <p>DFC に Organization オブジェクトを与える。</p>
     */
    public void addOrganizationToTarget(Member member) {
        rtcout.println(rtcout.DEBUG, "addOrganizationToTarget()");

        // get given RTC's configuration object
        Configuration conf = member.config_;
        if (conf == null) {
            return;
        }
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
     * <p>Organization オブジェクトを DFCから削除する。</p>
     */
    public void removeOrganizationFromTarget(Member member) {
        rtcout.println(rtcout.DEBUG, "removeOrganizationFromTarget()");

        // get given RTC's configuration object
        Configuration conf = member.config_;
        if (conf == null) {
            return;
        }
        // set organization to target RTC's conf
        try{
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
     * <p>Composite の ExecutionContext を DFC にセットする。</p>
     */
    public void addParticipantToEC(Member member) {
        rtcout.println(rtcout.DEBUG, "addParticipantToEC()");

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
     * <p>Composite の ExecutionContext から DFC を削除する。</p>
     */
    public void removeParticipantFromEC(Member member) { 
        rtcout.println(rtcout.DEBUG, "removeParticipantFromEC()");

        if (m_ec == null) {
            ExecutionContext[] ecs = m_rtobj.get_owned_contexts();
            if (ecs.length > 0) {
                m_ec = (ExecutionContext)ecs[0]._duplicate();
            } else {
                rtcout.println(rtcout.FATAL, "removeParticipantFromEC() no owned EC");
                return;
            }
        }
        m_ec.remove_component((RTObject)member.rtobj_._duplicate());
    }

    /**
     * <p>ポートを委譲する。</p>
     */
    public void addPort(Member member, Vector<String> portlist) {
        rtcout.println(rtcout.TRACE, "addPort() portlist=" + StringUtil.flatten(portlist));

        if (portlist.size() == 0) {
            return;
        }
        String comp_name = new String();
        comp_name = member.profile_.instance_name;
        PortProfile[] plist = new PortProfile[member.profile_.port_profiles.length];
        plist = member.profile_.port_profiles;

        // port delegation
        for (int i=0, len=plist.length; i < len; ++i) {
            // port name . comp_name.port_name
            String port_name = comp_name;
            port_name = port_name.concat("."); 
            port_name = port_name.concat(plist[i].name);

            rtcout.println(rtcout.DEBUG, "port_name: " + port_name + 
                                         " is in " + StringUtil.flatten(portlist));
            int pos = -1;
            for (Iterator it = portlist.iterator(); it.hasNext();) {
                String str = (String)it.next();
                if (str.indexOf(port_name) != -1 ) {
                    pos = 1;
                    break;
                }
            }
            if (pos == -1) {
                rtcout.println(rtcout.DEBUG, "Not Found: " + port_name + 
                                             " is in " + StringUtil.flatten(portlist));
                continue;
            }
            rtcout.println(rtcout.DEBUG, "Found: " + port_name + 
                                         " is in " + StringUtil.flatten(portlist));
            m_rtobj.registerPort(
                        (PortService)plist[i].port_ref._duplicate());
            rtcout.println(rtcout.DEBUG, "Port " + port_name + " was delegated.");
        }
    }

    /**
     * <p>委譲したポートを削除する。</p>
     */
    public void removePort(Member member, Vector<String> portlist) {
        rtcout.println(rtcout.TRACE, "removePort() portlist=" + StringUtil.flatten(portlist));

        if (portlist.size() == 0) {
            return;
        }
        String comp_name = new String();
        comp_name = member.profile_.instance_name;
        PortProfile[] plist = new PortProfile[member.profile_.port_profiles.length];
        plist = member.profile_.port_profiles;

        // port delegation
        for (int i=0, len=plist.length; i < len; ++i) {
            // port name . comp_name.port_name
            String port_name = comp_name;
            port_name = port_name.concat("."); 
            port_name = port_name.concat(plist[i].name);

            rtcout.println(rtcout.DEBUG, "port_name: " + port_name + 
                                         " is in " + StringUtil.flatten(portlist));
            int pos = -1;
            for (Iterator it = portlist.iterator(); it.hasNext();) {
                String str = (String)it.next();
                if (str.indexOf(port_name) != -1 ) {
                    pos = 1;
                    break;
                }
            }
            if (pos == -1) {
                rtcout.println(rtcout.DEBUG, "Not Found: " + port_name + 
                                             " is in " + StringUtil.flatten(portlist));
                continue;
            }
            rtcout.println(rtcout.DEBUG, "Found: " + port_name + 
                                         " is in " + StringUtil.flatten(portlist));
            m_rtobj.deletePort(
                            (PortService)plist[i].port_ref._duplicate());

            portlist.remove((String)port_name);
            rtcout.println(rtcout.DEBUG, "Port " + port_name + " was deleted.");
        }
     }

    /**
     * <p>ポートリストを更新する。</p>
     */
    private void updateExportedPortsList() {
        rtcout.println(rtcout.DEBUG, "updateExportedPortsList()");

        String plist = new String();
        plist = m_rtobj.getProperties().getProperty("conf.default.exported_ports");
        m_expPorts = StringUtil.split(plist, ",");
	for (int i=0; i<m_expPorts.size(); ++i) {
	    m_expPorts.set(i,m_expPorts.get(i).trim());
	}
    }

    /**
     * <p>委譲したポートを更新する。</p>
     */
    public void updateDelegatedPorts() {
        rtcout.println(rtcout.DEBUG, "updateDelegatedPorts()");
        Vector<String> oldPorts = new Vector<String>();
        oldPorts = m_expPorts;

        String plist = new String();
        plist = m_rtobj.getProperties().getProperty("conf.default.exported_ports");
        Vector<String> newPorts = new Vector<String>();
        newPorts = StringUtil.split(plist, ",");

        Vector<String> removedPorts = new Vector<String>(oldPorts);
        Vector<String> createdPorts = new Vector<String>(newPorts);
        removedPorts.removeAll(newPorts);
        createdPorts.removeAll(oldPorts);


        rtcout.println(rtcout.VERBOSE, "old    Ports: " + StringUtil.flatten(oldPorts));
        rtcout.println(rtcout.VERBOSE, "new    Ports: " + StringUtil.flatten(newPorts));
        rtcout.println(rtcout.VERBOSE, "remove Ports: " + StringUtil.flatten(removedPorts));
        rtcout.println(rtcout.VERBOSE, "add    Ports: " + StringUtil.flatten(createdPorts));

        for (int i=0, len=m_rtcMembers.size(); i < len; ++i) {
            removePort(m_rtcMembers.elementAt(i), removedPorts);
            addPort(m_rtcMembers.elementAt(i), createdPorts);
        }

	m_expPorts = newPorts;

	/*
	int size = newPorts.size();
	m_expPorts.clear();
	
	for (int i=0; i<size; ++i) {
	    m_expPorts.add(newPorts.elementAt(i));
	}
	*/
  }

    protected Logbuf rtcout;

};
