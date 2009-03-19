package jp.go.aist.rtm.RTC.executionContext;

import org.omg.CORBA.SystemException;

import java.util.Vector;

import RTC.RTObject;
import RTC.RTObjectHelper;
import RTC.ExecutionContext;
import RTC.ExecutionContextService;
import RTC.ReturnCode_t;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.ValueHolder;
import jp.go.aist.rtm.RTC.util.StringHolder;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.RtcDeleteFunc;
import jp.go.aist.rtm.RTC.RtcNewFunc;
import jp.go.aist.rtm.RTC.util.POAUtil;

import _SDOPackage.Organization;
import _SDOPackage.SDO;
import _SDOPackage.SDOListHolder;
import _SDOPackage.InvalidParameter;
import _SDOPackage.InternalError;
import _SDOPackage.NotAvailable;

import OpenRTM.DataFlowComponent;
import OpenRTM.DataFlowComponentHelper;

/**
 * <p></p>
 */
public class PeriodicECSharedComposite_impl extends RTObject_impl {

    /**
     * <p></p>
     */
    public PeriodicECSharedComposite_impl(Manager manager) {
        super(manager);
System.out.println( "PeriodicECSharedComposite_impl::PeriodicECSharedComposite_impl--000--" );
        m_ref = (DataFlowComponent)this._this()._duplicate();
System.out.println( "PeriodicECSharedComposite_impl::PeriodicECSharedComposite_impl--010--" );
        m_objref = (RTObject)m_ref._duplicate();
System.out.println( "PeriodicECSharedComposite_impl::PeriodicECSharedComposite_impl--020--" );
        m_org = new PeriodicECOrganization(this);
System.out.println( "PeriodicECSharedComposite_impl::PeriodicECSharedComposite_impl--030--" );

if(m_sdoOwnedOrganizations.value == null){
System.out.println( "PeriodicECSharedComposite_impl::PeriodicECSharedComposite_impl--030--m_sdoOwnedOrganizations is null." );
} else {
System.out.println( "PeriodicECSharedComposite_impl::PeriodicECSharedComposite_impl--030--m_sdoOwnedOrganizations is not null." );
}
if(m_sdoOwnedOrganizations == null){
System.out.println( "PeriodicECSharedComposite_impl::PeriodicECSharedComposite_impl--030--m_sdoOwnedOrganizations is null." );
} else {
System.out.println( "PeriodicECSharedComposite_impl::PeriodicECSharedComposite_impl--030--m_sdoOwnedOrganizations is not null." );
}
        CORBA_SeqUtil.push_back(m_sdoOwnedOrganizations,
                              (Organization)m_org.getObjRef()._duplicate());
System.out.println( "PeriodicECSharedComposite_impl::PeriodicECSharedComposite_impl--040--" );
//zxc        bindParameter("members", m_members, "", stringToStrVec);
        if( m_members==null ) {
            m_members = new StringHolder();
            m_members.value = new String();
        }
        bindParameter("members", m_members, "");
System.out.println( "PeriodicECSharedComposite_impl::PeriodicECSharedComposite_impl--0e0--" );
    }


    /**
     * <p></p>
     *
     * @return A CORBA object reference  
     */
    public DataFlowComponent _this() {
        
        if (this.m_ref == null) {
            try {
                this.m_ref = DataFlowComponentHelper.narrow(POAUtil.getRef(this));
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        
        return this.m_ref;
    }
    /**
     * <p></p>
     */
    public ReturnCode_t onInitialize() {
System.out.println( "PeriodicECSharedComposite::onInitialize--000--" );
        Manager mgr = Manager.instance();

        Vector<RTObject_impl> comps = mgr.getComponents();
        for (int i=0, len=comps.size(); i < len; ++i) {
            System.out.println( comps.elementAt(i).getInstanceName() );
        }

System.out.println( "PeriodicECSharedComposite::onInitialize--005--" );
        SDOListHolder sdos = new SDOListHolder();
System.out.println( "PeriodicECSharedComposite::onInitialize--006--" );

	String[] str = m_members.toString().split(",");

System.out.println( "PeriodicECSharedComposite::onInitialize--010--" + m_members.toString());
//zxc        for (int i=0, len=m_members.size(); i < len; ++i) {
//zxc            RTObject_impl rtc = mgr.getComponent(m_members.elementAt(i));
        for (int i=0, len=str.length; i < len; ++i) {
            RTObject_impl rtc = mgr.getComponent(str[i]);
            if (rtc == null) {
                continue;
            }

System.out.println( "PeriodicECSharedComposite::onInitialize--030--");
            SDO sdo;
            sdo = (SDO)rtc.getObjRef()._duplicate();
            if (sdo == null) continue;

System.out.println( "PeriodicECSharedComposite::onInitialize--040--");
            CORBA_SeqUtil.push_back(sdos, sdo);
System.out.println( "PeriodicECSharedComposite::onInitialize--050--");
        }
  
        try {
            m_org.set_members(sdos.value);
        } catch (Exception e) {
System.out.println( "PeriodicECSharedComposite::onInitialize--0c1--" + e );
        }
System.out.println( "PeriodicECSharedComposite::onInitialize--0e0--" );
        return ReturnCode_t.RTC_OK;
    }

    /**
     * <p></p>
     */
    public ReturnCode_t onActivated(int exec_handle) {
        ExecutionContext[] ecs = get_owned_contexts();
        try {
            SDO[] sdos = m_org.get_members();
            for (int i=0, len=sdos.length; i < len; ++i) {
                RTObject rtc = RTObjectHelper.narrow(sdos[i]);
                ecs[0].activate_component(rtc);
            }
        } catch(NotAvailable e) {
            ;
        } catch(InternalError e) {
            ;
        }

	String[] str = m_members.toString().split(",");
        System.out.println( "num of mem:" + str.length );
//zxc        System.out.println( "num of mem:" + m_members.size() );
        return ReturnCode_t.RTC_OK;
    }

    /**
     * <p></p>
     */
    public ReturnCode_t onDeactivated(int exec_handle) {
        ExecutionContext[] ecs = get_owned_contexts();
        try { 
            SDO[] sdos = m_org.get_members();
            for (int i=0, len=sdos.length; i < len; ++i) {
                RTObject rtc = RTObjectHelper.narrow(sdos[i]);
                ecs[0].deactivate_component(rtc);
            }
        } catch(NotAvailable e) {
            ;
        } catch(InternalError e) {
            ;
        }

        return ReturnCode_t.RTC_OK;
     }

    /**
     * <p></p>
     */
    public ReturnCode_t onReset(int exec_handle) {
        ExecutionContext[] ecs = get_owned_contexts();
        try { 
            SDO[] sdos = m_org.get_members();

            for (int i=0, len=sdos.length; i < len; ++i) {
                RTObject rtc = RTObjectHelper.narrow(sdos[i]);
                ecs[0].reset_component(rtc);
            }
        } catch(NotAvailable e) {
            ;
        } catch(InternalError e) {
            ;
        }
        return ReturnCode_t.RTC_OK;
    }

    /**
     * <p></p>
     */
    public ReturnCode_t onFinalize() {
        System.out.println( "onFinalize" );
        m_org.removeAllMembers();
        System.out.println( "onFinalize done" );
        return ReturnCode_t.RTC_OK;
    }
    /**
     * <p></p>
     */
//    protected Vector<String> m_members = new Vector<String>;
    protected StringHolder m_members;
    /**
     * <p></p>
     */
    protected DataFlowComponent m_ref;
    /**
     * <p></p>
     */
    protected PeriodicExecutionContext m_pec;
    /**
     * <p></p>
     */
    protected ExecutionContextService m_ecref;
    /**
     * <p></p>
     */
    protected PeriodicECOrganization m_org;

    /**
     * <p></p>
     */
    public boolean stringToStrVec(Vector<StringBuffer> v, final String is) {
        String s = is;
        String[] str = s.split(",");
        for (int i=0, len = str.length; i<len ; ++i) {
            v.add(new StringBuffer(str[i]));
        }
        return true;
    }
};

