package jp.go.aist.rtm.RTC;

import java.util.Vector;

import jp.go.aist.rtm.RTC.SDOPackage.Configuration_impl;
import jp.go.aist.rtm.RTC.port.DataInPort;
import jp.go.aist.rtm.RTC.port.DataOutPort;
import jp.go.aist.rtm.RTC.port.InPort;
import jp.go.aist.rtm.RTC.port.OutPort;
import jp.go.aist.rtm.RTC.port.PortAdmin;
import jp.go.aist.rtm.RTC.port.PortBase;
import jp.go.aist.rtm.RTC.port.InPortBase;
import jp.go.aist.rtm.RTC.port.OutPortBase;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.ORBUtil;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.ValueHolder;
import jp.go.aist.rtm.RTC.util.equalFunctor;
import jp.go.aist.rtm.RTC.util.operatorFunc;
import jp.go.aist.rtm.RTC.util.POAUtil;
import jp.go.aist.rtm.RTC.util.StringUtil;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.log.Logbuf;
import jp.go.aist.rtm.RTC.executionContext.ExecutionContextBase;

import org.omg.CORBA.Any;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;

import RTC.ComponentProfile;
import OpenRTM.DataFlowComponentPOA;
import OpenRTM.DataFlowComponent;
import OpenRTM.DataFlowComponentHelper;
import RTC.ExecutionContext;
import RTC.ExecutionContextHelper;
import RTC.ExecutionContextListHolder;
import RTC.ExecutionContextService;
import RTC.ExecutionContextServiceHelper;
import RTC.ExecutionContextServiceListHolder;
import RTC.LightweightRTObject;
import RTC.PortService;
import RTC.PortProfile;
import RTC.RTObject;
import RTC.RTObjectHelper;
import RTC.ReturnCode_t;

import _SDOPackage.Configuration;
import _SDOPackage.DeviceProfile;
import _SDOPackage.InterfaceNotImplemented;
import _SDOPackage.InternalError;
import _SDOPackage.InvalidParameter;
import _SDOPackage.Monitoring;
import _SDOPackage.NVListHolder;
import _SDOPackage.NameValue;
import _SDOPackage.NotAvailable;
import _SDOPackage.Organization;
import _SDOPackage.OrganizationListHolder;
import _SDOPackage.SDOService;
import _SDOPackage.SDOServiceHolder;
import _SDOPackage.ServiceProfile;
import _SDOPackage.ServiceProfileHolder;
import _SDOPackage.ServiceProfileListHolder;

/**
 * <p>DataFlowComponentのベースクラスです。
 * ユーザが新たなRTコンポーネントを作成する場合は、このクラスを拡張します。</p>
 */
public class RTObject_impl extends DataFlowComponentPOA {

    /**
     * <p>RTコンポーネントのデフォルト・コンポーネント・プロファイルです。</p>
     * 
     *
     */
    static final String default_conf[] = {
      "implementation_id", "",
      "type_name",         "",
      "description",       "",
      "version",           "",
      "vendor",            "",
      "category",          "",
      "activity_type",     "",
      "max_instance",      "",
      "language",          "",
      "lang_type",         "",
      "conf",              "",
      ""
    };

    /*
     *
     */
    public static final int ECOTHER_OFFSET = 1000;

    /**
     * <p>コンストラクタです。</p>
     * 
     * @param manager Managerオブジェクト
     */
    public RTObject_impl(Manager manager) {
        m_pManager =  manager;
        m_pORB = manager.getORB();
        m_pPOA = manager.getPOA();
        m_portAdmin = new PortAdmin(manager.getORB(), manager.getPOA());
        m_created = true;
        m_properties = new Properties(default_conf);
        m_configsets = new ConfigAdmin(m_properties.getNode("conf"));
        
        m_objref = this._this();
        m_pSdoConfigImpl = new Configuration_impl(m_configsets);
        m_pSdoConfig = m_pSdoConfigImpl.getObjRef();
        if( m_ecMine == null ) {
            m_ecMine = new ExecutionContextServiceListHolder();
            m_ecMine.value = new ExecutionContextService[0];
        }
        if( m_ecOther == null ) {
            m_ecOther = new ExecutionContextServiceListHolder();
            m_ecOther.value = new ExecutionContextService[0];
        }
        if(m_sdoOwnedOrganizations.value == null){
            m_sdoOwnedOrganizations.value = new Organization[0];
        }

        rtcout = new Logbuf("RTObject_impl");
         
    }

    /**
     * <p>コンストラクタです。</p>
     * 
     * @param orb ORB
     * @param poa POA
     */
    public RTObject_impl(ORB orb, POA poa) {
        m_pManager =  null;
        m_pORB = orb;
        m_pPOA = poa;
        m_portAdmin = new PortAdmin(orb, poa);
        m_created = true;
        m_properties = new Properties(default_conf);
        m_configsets = new ConfigAdmin(m_properties.getNode("conf"));
        
        m_objref = this._this();
        m_pSdoConfigImpl = new Configuration_impl(m_configsets);
        m_pSdoConfig = m_pSdoConfigImpl.getObjRef();

        if( m_ecMine == null ) {
            m_ecMine = new ExecutionContextServiceListHolder();
            m_ecMine.value = new ExecutionContextService[0];
        }
        if( m_ecOther == null ) {
            m_ecOther = new ExecutionContextServiceListHolder();
            m_ecOther.value = new ExecutionContextService[0];
        }

        Manager manager = Manager.instance();
        rtcout = new Logbuf("RTObject_impl");
    }

    /**
     *  <p> _this </p>
     *
     *  @return DataFlowComponent
     *
     */
    public DataFlowComponent _this() {
        if (this.m_objref == null) {
            try {
                this.m_objref = RTObjectHelper.narrow(POAUtil.getRef(this));
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        
        return DataFlowComponentHelper.narrow(this.m_objref);
    }

    /**
     * <p>コンポーネント生成時(Created->Alive)に呼び出されるアクションです。</p>
     * 
     * @return 実行結果
     */
    protected ReturnCode_t onInitialize(){
        rtcout.println(rtcout.TRACE, "RTObject_impl.onInitialize()");
        return ReturnCode_t.RTC_OK;
    }

    /**
     * <p>コンポーネント破棄時(Alive->Exit)に呼び出されるアクションです。</p>
     * 
     * @return 実行結果
     */
    protected ReturnCode_t onFinalize() {
        rtcout.println(rtcout.TRACE, "RTObject_impl.onFinalize()");
        return ReturnCode_t.RTC_OK;
    }

    /**
     * <p>ExecutionContextの動作開始時(Stopped->Started)に呼び出されるアクションです。</p>
     * 
     * @param ec_id 対象ExecutionContext ID
     * 
     * @return 実行結果
     */
    protected ReturnCode_t onStartup(int ec_id) {
        rtcout.println(rtcout.TRACE, "RTObject_impl.onStartup(" + ec_id + ")");
        return ReturnCode_t.RTC_OK;
    }

    /**
     * <p>ExecutionContextの動作終了時(Started->Stopped)に呼び出されるアクションです。</p>
     * 
     * @param ec_id 対象ExecutionContext ID
     * 
     * @return 実行結果
     */
    protected ReturnCode_t onShutdown(int ec_id) {
        rtcout.println(rtcout.TRACE, "RTObject_impl.onShutdown(" + ec_id + ")");
        return ReturnCode_t.RTC_OK;
    }

    /**
     * <p>RTコンポーネントのActivate時(Inactive->Active)に呼び出されるアクションです。</p>
     * 
     * @param ec_id 対象ExecutionContext ID
     * 
     * @return 実行結果
     */
    protected ReturnCode_t onActivated(int ec_id) {
        rtcout.println(rtcout.TRACE, "RTObject_impl.onActivated(" + ec_id + ")");
        return ReturnCode_t.RTC_OK;
    }

    /**
     * <p>RTコンポーネントのDeactivate時(Active->Inactive)に呼び出されるアクションです。</p>
     * 
     * @param ec_id 対象ExecutionContext ID
     * 
     * @return 実行結果
     */
    protected ReturnCode_t onDeactivated(int ec_id) {
        rtcout.println(rtcout.TRACE, "RTObject_impl.onDeactivated(" + ec_id + ")");
        return ReturnCode_t.RTC_OK;
    }

    /**
     * <p>RTコンポーネントがActivate状態の間呼び出されるアクションです。</p>
     * 
     * @param ec_id 対象ExecutionContext ID
     * 
     * @return 実行結果
     */
    protected ReturnCode_t onExecute(int ec_id) {
        rtcout.println(rtcout.TRACE, "RTObject_impl.onExecute(" + ec_id + ")");
        return ReturnCode_t.RTC_OK;
    }

    /**
     * <p>RTコンポーネントにエラーが発生した時(Active->Error)に呼び出されるアクションです。</p>
     * 
     * @param ec_id 対象ExecutionContext ID
     * 
     * @return 実行結果
     */
    protected ReturnCode_t onAborting(int ec_id) {
        rtcout.println(rtcout.TRACE, "RTObject_impl.onAborting(" + ec_id + ")");
        return ReturnCode_t.RTC_OK;
    }

    /**
     * <p>RTコンポーネントがError状態の間呼び出されるアクションです。</p>
     * 
     * @param ec_id 対象ExecutionContext ID
     * 
     * @return 実行結果
     */
    protected ReturnCode_t onError(int ec_id) {
        rtcout.println(rtcout.TRACE, "RTObject_impl.onError(" + ec_id + ")");
        return ReturnCode_t.RTC_OK;
    }

    /**
     * <p>RTコンポーネントをresetする際(Error->Inactive)に呼び出されるアクションです。</p>
     * 
     * @param ec_id 対象ExecutionContext ID
     * 
     * @return 実行結果
     */
    protected ReturnCode_t onReset(int ec_id) {
        rtcout.println(rtcout.TRACE, "RTObject_impl.onReset(" + ec_id + ")");
        return ReturnCode_t.RTC_OK;
    }

    /**
     * <p>RTコンポーネントがActivate状態の間、
     * on_executeの続いて呼び出されるアクションです。</p>
     * 
     * @param ec_id 対象ExecutionContext ID
     * 
     * @return 実行結果
     */
    protected ReturnCode_t onStateUpdate(int ec_id) {
        rtcout.println(rtcout.TRACE, "RTObject_impl.onStateUpdete(" + ec_id + ")");
        return ReturnCode_t.RTC_OK;
    }

    /**
     * <p>ExecutionContextの実行周期が変更になった時に呼び出されるアクションです。</p>
     * 
     * @param ec_id 対象ExecutionContext ID
     * 
     * @return 実行結果
     */
    protected ReturnCode_t onRateChanged(int ec_id) {
        rtcout.println(rtcout.TRACE, "RTObject_impl.onRateChanged(" + ec_id + ")");
        return ReturnCode_t.RTC_OK;
    }

    /**
     * <p>RTコンポーネントを初期化します。<br />
     *
     * このオペレーション呼び出しの結果として、ComponentAction::on_initialize
     * コールバック関数が呼ばれます。<br />
     * 制約<br />
     * Created状態にいるときにのみ、初期化が行われます。他の状態にいる場合には
     * ReturnCode_t::PRECONDITION_NOT_MET が返され呼び出しは失敗します。
     * このオペレーションはRTCのミドルウエアから呼ばれることを想定しており、
     * アプリケーション開発者は直接このオペレーションを呼ぶことは想定
     * されていません。</p>
     * 
     * @return 実行結果
     */
    public ReturnCode_t initialize() {

        rtcout.println(rtcout.TRACE, "RTObject_impl.initialize()");

        if( !m_created ) {
            return ReturnCode_t.PRECONDITION_NOT_MET;
        }
      
        String ec_args = new String();

        ec_args += m_properties.getProperty("exec_cxt.periodic.type");
        ec_args += "?";
        ec_args += "rate=" + m_properties.getProperty("exec_cxt.periodic.rate");

        ExecutionContextBase ec;
        ec = Manager.instance().createContext(ec_args);
        if (ec == null) {
            return ReturnCode_t.RTC_ERROR;
        }
        ec.set_rate(Double.valueOf(m_properties.getProperty("exec_cxt.periodic.rate")).doubleValue());
        m_eclist.add(ec);
        ExecutionContextService ecv;
        ecv = ec.getObjRef();
        if (ecv == null) {
            return ReturnCode_t.RTC_ERROR;
        }
        ec.bindComponent(this);

        ReturnCode_t ret;
        ret = on_initialize();
        if( ret!=ReturnCode_t.RTC_OK ) {
            return ret;
        }
        m_created = false;

        // -- entering alive state --
        // at least one EC must be attached
        if (m_ecMine.value.length == 0) {
            return ReturnCode_t.PRECONDITION_NOT_MET;
        }
        for(int intIdx=0; intIdx < m_ecMine.value.length; ++intIdx) {
            rtcout.println(rtcout.DEBUG, "EC[" + intIdx + "] starting");
            m_ecMine.value[intIdx].start();
        }

        // ret must be RTC_OK
        return ret;
    }

    /**
     * <p>RTコンポーネントを終了します。<br />
     *
     * このオペレーション呼び出しの結果として、ComponentAction::on_finalize
     * コールバック関数が呼ばれます。<br />
     * 制約</p>
     * <ol>
     * <li>この RTC が属する Running 状態の実行コンテキスト中、Active 状態にある
     *   ものがあればこの RTC は終了されません。その場合、このオペレーション呼び
     *   出しはいかなる場合も ReturnCode_t::PRECONDITION_NOT_ME で失敗します。</li>
     * <li>この RTC が Created 状態である場合、終了処理は行われません。
     *   その場合、このオペレーション呼び出しはいかなる場合も 
     *   ReturnCode_t::PRECONDITION_NOT_MET で失敗します。</li>
     * <li>アプリケーション開発者はこのオペレーションを直接的に呼び出すことは
     *   まれであり、たいていはRTCインフラストラクチャから呼び出されるます。</li>
     * </ol>
     * 
     * @return 実行結果
     */
    public ReturnCode_t _finalize()  throws SystemException {

        rtcout.println(rtcout.TRACE, "RTObject_impl._finalize()");

        if( m_created ) {
            return ReturnCode_t.PRECONDITION_NOT_MET;
        }
        // Return RTC::PRECONDITION_NOT_MET,
        // When the component is registered in ExecutionContext.
        //if(m_ecMine.value.length != 0 || m_ecOther.value.length != 0)
        if(m_ecOther.value.length != 0)
        {
            for(int i=0, len=m_ecOther.value.length; i < len; ++i) {
                if (m_ecOther.value[i] != null) {
                    return ReturnCode_t.PRECONDITION_NOT_MET;
                }
            }
            m_ecOther.value = null;
        }

        ReturnCode_t ret = on_finalize();
        shutdown();
        return ret;
    }

    /**
     * <p>RTコンポーネントを停止し、そのコンテンツとともに終了します。<br />
     *
     * この RTC がオーナーであるすべての実行コンテキストが停止されます。
     * この RTC が他の実行コンテキストを所有する RTC に属する実行コンテキスト
     * (i.e. 実行コンテキストを所有する RTC はすなわちその実行コンテキストの
     * オーナーである。)に参加している場合、当該 RTC はそれらのコンテキスト上
     * で非活性化されなければなりません。<br />
     * 制約</p>
     * <ol>
     * <li>RTC が初期化されていなければ、終了させることはできません。</li>
     * <li>Created 状態にある RTC に exit() を呼び出した場合、
     * ReturnCode_t::PRECONDITION_NOT_MET で失敗します。</li>
     * </ol>
     * 
     * @return 実行結果
     */
    public ReturnCode_t exit() throws SystemException {

        rtcout.println(rtcout.TRACE, "RTObject_impl.exit()");

        if (m_created) {
            return ReturnCode_t.PRECONDITION_NOT_MET;
        }
        // deactivate myself on owned EC
        CORBA_SeqUtil.for_each(m_ecMine,
                               new deactivate_comps((LightweightRTObject)m_objref._duplicate()));

        // deactivate myself on other EC
        CORBA_SeqUtil.for_each(m_ecOther,
                               new deactivate_comps((LightweightRTObject)m_objref._duplicate()));

        // stop and detach myself from owned EC
        for(int ic=0, len=m_ecMine.value.length; ic < len; ++ic) {
//          m_ecMine.value[ic].stop();
//          m_ecMine.value[ic].remove_component(this._this());
        }

        // detach myself from other EC
        for(int ic=0, len=m_ecOther.value.length; ic < len; ++ic) {
//          m_ecOther.value[ic].stop();
            if (m_ecOther.value[ic] != null) {
                m_ecOther.value[ic].remove_component(this._this());
            }
        }
        ReturnCode_t ret = this._finalize();
        return ret;
    }

    /**
     * <p>RTコンポーネントがAlive状態であるか判断します。<br />
     *
     * RTコンポーネントがAliveであるかどうかは、
     * ExecutionContextの状態(Inactive，Active，Error)とは独立しています。
     * １つのRTコンポーネントが、複数のExecutionContextにattachされる場合もあるため、
     * ExecutionContextの状態が混在する場合
     * (ExecutionContext1に対してはActive、ExecutionContext2に対してはInactiveなど)
     * があるためです。</p>
     * 
     * @return Alive状態判断結果
     */
    public boolean is_alive(ExecutionContext exec_context) throws SystemException {
        rtcout.println(rtcout.TRACE, "RTObject_impl.is_alive()");

        for(int i=0, len=m_ecMine.value.length; i < len; ++i) {
            if (exec_context._is_equivalent(m_ecMine.value[i]))
                return true;
        }

        for(int i=0, len=m_ecOther.value.length; i < len; ++i) {
            if (m_ecOther.value[i] != null) {
                if (exec_context._is_equivalent(m_ecOther.value[i]))
                    return true;
            }
        }
        return false;
    }

    /**
     * <p>[CORBA interface] ExecutionContextListを取得します。</p>
     *
     * @return ExecutionContextリスト
     */
    public ExecutionContext[] get_owned_contexts() throws SystemException {

        rtcout.println(rtcout.TRACE, "RTObject_impl.get_owned_contexts()");

        ExecutionContextListHolder execlist;
        execlist = new ExecutionContextListHolder();
        execlist.value = new ExecutionContext[0];
    
        CORBA_SeqUtil.for_each(m_ecMine, new ec_copy(execlist));
    
        return execlist.value;
     }

    /**
     * <p>[CORBA interface] ExecutionContextを取得します。</p>
     *
     * @param ec_id ExecutionContextのID
     * 
     * @return ExecutionContext
     */
    public ExecutionContext get_context(int ec_id) {

        rtcout.println(rtcout.TRACE, "RTObject_impl.get_context(" + ec_id + ")");

        ExecutionContext ec;
        // owned EC
        if (ec_id < ECOTHER_OFFSET) {
            if (ec_id < m_ecMine.value.length) {
                ec =  m_ecMine.value[ec_id];
                return ec;
            }
            else {
                return null; 
            }
        }

        // participating EC
        int index = ec_id - ECOTHER_OFFSET;

        if (index < m_ecOther.value.length) {
            if (m_ecOther.value[index] != null) {
                ec =  m_ecOther.value[index];
                return ec;
            }
        }

        return null;
    }

    /**
     * <p>[CORBA interface] LightweightRTObject</p>
     *
     * 
     * @return ExecutionContextList
     *
     */ 
    public ExecutionContext[] get_participating_contexts() throws SystemException {
        rtcout.println(rtcout.TRACE, "RTObject_impl.get_participating_contexts()");

        ExecutionContextListHolder execlist;
        execlist = new ExecutionContextListHolder();
        execlist.value = new ExecutionContext[0];

        CORBA_SeqUtil.for_each(m_ecOther, new ec_copy(execlist));

        return execlist.value;
    }


    /**
     * <p>[CORBA interface] LightweightRTObject</p>
     *
     * @param cxt ExecutionContext
     * 
     * @return ExecutionContextHandle_t
     *
     */
    public int get_context_handle(ExecutionContext cxt) throws SystemException {

        rtcout.println(rtcout.TRACE, "RTObject_impl.get_context_handle()");

        int ech;
        ech = 0;
        // ec_id 0 : owned context
        // ec_id 1-: participating context
        if (cxt._is_equivalent(m_ecMine.value[0])) {
            return ech;
        }
        int num;
        num = CORBA_SeqUtil.find(m_ecOther, new ec_find(cxt));
        return (num +1);
    }

    /**
     * <p> bindContext </p>
     * 
     * @param exec_context ExecutionContext
     *
     * @return int
     *
     */
    public int bindContext(ExecutionContext exec_context)
    {

        rtcout.println(rtcout.TRACE, "RTObject_impl.bindContext()");

        // ID: 0 - (offset-1) : owned ec
        // ID: offset -       : participating ec
        // owned       ec index = ID
        // participate ec index = ID - offset
      
        ExecutionContextService ecs;
        ecs = ExecutionContextServiceHelper.narrow(exec_context);
        if (ecs == null) {
            return -1;
        }

        // if m_ecMine has nil element, insert attached ec to there.
        for(int i=0, len=m_ecMine.value.length; i < len; ++i) {
            if (m_ecMine.value[i] == null) {
                m_ecMine.value[i] = (ExecutionContextService)ecs._duplicate();
                return i;
            }
        }

        // no space in the list, push back ec to the last.
        CORBA_SeqUtil.push_back(m_ecMine, 
                                (ExecutionContextService)ecs._duplicate());

        return (m_ecMine.value.length - 1);
    }


  //============================================================
  // RTC::RTObject
  //============================================================
  
    /**
     * <p>[RTObject CORBA interface] 当該コンポーネントのプロファイル情報を取得します。</p>
     *
     * @return コンポーネントのプロファイル情報
     */
    public ComponentProfile get_component_profile() {

        rtcout.println(rtcout.TRACE, "RTObject_impl.get_component_profile()");

        try {
            ComponentProfile profile = new ComponentProfile();
            profile.instance_name = m_properties.getProperty("instance_name");
            profile.type_name = m_properties.getProperty("type_name");
            profile.description = m_properties.getProperty("description");
            profile.version = m_properties.getProperty("version");
            profile.vendor = m_properties.getProperty("vendor");
            profile.category = m_properties.getProperty("category");
            profile.parent = m_profile.parent;
            profile.properties = m_profile.properties;
            profile.port_profiles = m_portAdmin.getPortProfileList().value;
            return profile;
        } catch (Exception ex) {
            ; // This operation throws no exception.
        }
        return null;
    }

    /**
     * <p>[RTObject CORBA interface] 当該コンポーネントが保有するポートの参照を取得します。</p>
     *
     * @return ポート参照情報
     */
    public PortService[] get_ports() {

        rtcout.println(rtcout.TRACE, "RTObject_impl.get_ports()");

        try {
            return m_portAdmin.getPortServiceList().value;
        } catch(Exception ex) {
            ; // This operation throws no exception.
        }
        return null;
    }

    /**
     * <p>[CORBA interface] 当該コンポーネントをExecutionContextにattachします。</p>
     *
     * @param exec_context attach対象ExecutionContext
     * 
     * @return attachされたExecutionContext数
     */
    public int attach_context(ExecutionContext exec_context) throws SystemException {

        rtcout.println(rtcout.TRACE, "RTObject_impl.attach_context()");

        // ID: 0 - (offset-1) : owned ec
        // ID: offset -       : participating ec
        // owned       ec index = ID
        // participate ec index = ID - offset
        ExecutionContextService ecs;
        ecs = ExecutionContextServiceHelper.narrow(exec_context);
        if (ecs == null) {
            return -1;
        }

        // if m_ecOther has nil element, insert attached ec to there.
        for(int i=0, len=m_ecOther.value.length; i < len; ++i) {
            if (m_ecOther.value[i] == null) {
                m_ecOther.value[i] = (ExecutionContextService)ecs._duplicate();
                return (i + ECOTHER_OFFSET);
            }
        }

        // no space in the list, push back ec to the last.
        CORBA_SeqUtil.push_back(m_ecOther, 
                                (ExecutionContextService)ecs._duplicate());
    
        return ((m_ecOther.value.length - 1) + ECOTHER_OFFSET);

    }
    /**
     * <p>[CORBA interface] 当該コンポーネントをExecutionContextからdetachします。</p>
     *
     * @param ec_id detach対象ExecutionContextのID
     * 
     * @return 実行結果
     */
    public ReturnCode_t detach_context(int ec_id) throws SystemException {

        rtcout.println(rtcout.TRACE, "RTObject_impl.detach_context(" + ec_id + ")");

        int len = m_ecOther.value.length;
        // ID: 0 - (offset-1) : owned ec
        // ID: offset -       : participating ec
        // owned       ec index = ID
        // participate ec index = ID - offset
        if (ec_id < ECOTHER_OFFSET || (ec_id - ECOTHER_OFFSET) > len) {
            return ReturnCode_t.BAD_PARAMETER;
        }
        int index = (ec_id - ECOTHER_OFFSET);
        if (m_ecOther.value[index] == null) {
            return ReturnCode_t.BAD_PARAMETER;
        }
        m_ecOther.value[index] = null;

        return ReturnCode_t.RTC_OK;
    }

    /**
     * <p>[ComponentAction interface] 当該コンポーネントの初期化時に呼び出されます。</p>
     *
     * @return 実行結果
     */
    public ReturnCode_t on_initialize() {

        rtcout.println(rtcout.TRACE, "RTObject_impl.on_initialize()");

        ReturnCode_t ret = ReturnCode_t.RTC_ERROR;
        try {
            String active_config;
            active_config = m_properties.getProperty("active_config");
            if (active_config.length() == 0 || active_config.equals("")) {
                m_configsets.update("default");
            } else {
                if (m_configsets.haveConfig(active_config)) {
                    m_configsets.update(active_config);
                } else {
                    m_configsets.update("default");
                }
            }
            ret = onInitialize();
        } catch(Exception ex) {
            return ReturnCode_t.RTC_ERROR;
        }
        return ret;
    }

    /**
     * <p>[ComponentAction interface] 当該コンポーネントの終了時に呼び出されます。</p>
     *
     * @return 実行結果
     */
    public ReturnCode_t on_finalize() {

        rtcout.println(rtcout.TRACE, "RTObject_impl.on_finalize()");

        ReturnCode_t ret = ReturnCode_t.RTC_ERROR;
        try {
            ret = onFinalize();
        } catch(Exception ex) {
            return ReturnCode_t.RTC_ERROR;
        }
        return ret;
    }

    /**
     * <p>[ComponentAction interface] 
     * 当該コンポーネントのattachされているExecutionContextの実行開始時に呼び出されます。</p>
     *
     * @param ec_id 対象ExecutionContextのID
     * 
     * @return 実行結果
     */
    public ReturnCode_t on_startup(int ec_id) {

        rtcout.println(rtcout.TRACE, "RTObject_impl.on_startup(" + ec_id + ")");

        ReturnCode_t ret = ReturnCode_t.RTC_ERROR;
        try {
            ret = onStartup(ec_id);
        } catch (Exception ex) {
            return ReturnCode_t.RTC_ERROR;
        }
        return ret;
    }

    /**
     * <p>[ComponentAction interface] 
     * 当該コンポーネントのattachされているExecutionContextの実行終了時に呼び出されます。</p>
     *
     * @param ec_id 対象ExecutionContextのID
     * 
     * @return 実行結果
     */
    public ReturnCode_t on_shutdown(int ec_id) {

        rtcout.println(rtcout.TRACE, "RTObject_impl.on_shutdown(" + ec_id + ")");

        ReturnCode_t ret = ReturnCode_t.RTC_ERROR;
        try {
            ret = onShutdown(ec_id);
        } catch(Exception ex) {
            return ReturnCode_t.RTC_ERROR;
        }
        return ret;
    }

    /**
     * <p>[ComponentAction interface] 
     * 当該コンポーネントのActivate時に呼び出されます。</p>
     *
     * @param ec_id 対象ExecutionContextのID
     * 
     * @return 実行結果
     */
    public ReturnCode_t on_activated(int ec_id) {

        rtcout.println(rtcout.TRACE, "RTObject_impl.on_activated(" + ec_id + ")");

        ReturnCode_t ret = ReturnCode_t.RTC_ERROR;
        try {
            m_configsets.update();
            ret = onActivated(ec_id);
            m_portAdmin.activatePorts();
        } catch(Exception ex) {
            return ReturnCode_t.RTC_ERROR;
        }
        return ret;
    }
    
    /**
     * <p>[ComponentAction interface] 
     * 当該コンポーネントのDeactivate時に呼び出されます。</p>
     *
     * @param ec_id 対象ExecutionContextのID
     * 
     * @return 実行結果
     */
    public ReturnCode_t on_deactivated(int ec_id) {

        rtcout.println(rtcout.TRACE, "RTObject_impl.on_deactivated(" + ec_id + ")");

        ReturnCode_t ret = ReturnCode_t.RTC_ERROR;
        try {
            m_portAdmin.deactivatePorts();
            ret = onDeactivated(ec_id);
        } catch(Exception ex) {
            return ReturnCode_t.RTC_ERROR;
        }
        return ret;
    }

    /**
     * <p>[ComponentAction interface] 
     * 当該コンポーネントのAbort時に呼び出されます。</p>
     *
     * @param ec_id 対象ExecutionContextのID
     * 
     * @return 実行結果
     */
    public ReturnCode_t on_aborting(int ec_id) {

        rtcout.println(rtcout.TRACE, "RTObject_impl.on_aborting(" + ec_id + ")");

        ReturnCode_t ret = ReturnCode_t.RTC_ERROR;
        try {
            ret = onAborting(ec_id);
        } catch(Exception ex) {
            return ReturnCode_t.RTC_ERROR;
        }
        return ret;
    }
    
    /**
     * <p>[ComponentAction interface] 
     * 当該コンポーネントがError状態にいる間、呼び出されます。</p>
     *
     * @param ec_id 対象ExecutionContextのID
     * 
     * @return 実行結果
     */
    public ReturnCode_t on_error(int ec_id) {

        rtcout.println(rtcout.TRACE, "RTObject_impl.on_error(" + ec_id + ")");

        ReturnCode_t ret = ReturnCode_t.RTC_ERROR;
        try {
            ret = onError(ec_id);
            m_configsets.update();
        } catch(Exception ex) {
            return ReturnCode_t.RTC_ERROR;
        }
        return ret;
    }

    /**
     * <p>[ComponentAction interface] 
     * 当該コンポーネントのReset時に呼び出されます。</p>
     *
     * @param ec_id 対象ExecutionContextのID
     * 
     * @return 実行結果
     */
    public ReturnCode_t on_reset(int ec_id) {

        rtcout.println(rtcout.TRACE, "RTObject_impl.on_reset(" + ec_id + ")");

        ReturnCode_t ret = ReturnCode_t.RTC_ERROR;
        try {
            ret = onReset(ec_id);
        } catch(Exception ex) {
            return ReturnCode_t.RTC_ERROR;
        }
        return ret;
    }

    /**
     * <p>[ComponentAction interface] 
     * 当該コンポーネントがAvtive状態の間、呼び出されます。</p>
     *
     * @param ec_id 対象ExecutionContextのID
     * 
     * @return 実行結果
     */
    public ReturnCode_t on_execute(int ec_id) {

        rtcout.println(rtcout.TRACE, "RTObject_impl.on_execute(" + ec_id + ")");

        ReturnCode_t ret = ReturnCode_t.RTC_ERROR;
        try {
            ret = onExecute(ec_id);
        } catch(Exception ex) {
            return ReturnCode_t.RTC_ERROR;
        }
        return ret;
    }

    /**
     * <p>[ComponentAction interface] 
     * 当該コンポーネントがAvtive状態の間、on_executeの後に呼び出されます。</p>
     *
     * @param ec_id 対象ExecutionContextのID
     * 
     * @return 実行結果
     */
    public ReturnCode_t on_state_update(int ec_id) {

        rtcout.println(rtcout.TRACE, "RTObject_impl.on_state_update(" + ec_id + ")");

        ReturnCode_t ret =ReturnCode_t.RTC_ERROR;
        try {
            ret = onStateUpdate(ec_id);
            m_configsets.update();
        } catch(Exception ex) {
            return ReturnCode_t.RTC_ERROR;
        }
        return ret;
    }

    /**
     * <p>[ComponentAction interface] 
     * 当該コンポーネントがattachされているExecutionContextの実行周期が変更になった時に呼び出されます。</p>
     *
     * @param ec_id 対象ExecutionContextのID
     * 
     * @return 実行結果
     */
    public ReturnCode_t on_rate_changed(int ec_id) {

        rtcout.println(rtcout.TRACE, "RTObject_impl.on_rate_changed(" + ec_id + ")");

        ReturnCode_t ret = ReturnCode_t.RTC_ERROR;
        try {
            ret = onRateChanged(ec_id);
        } catch(Exception ex) {
            return ReturnCode_t.RTC_ERROR;
        }
        return ret;
    }

    /**
     * <p>[CORBA interface] Organization リストを取得します。<br />
     * SDOSystemElement は0個もしくはそれ以上の Organization を所有することが
     * 出来ます。 SDOSystemElement が1つ以上の Organization を所有している場合
     * には、このオペレーションは所有する Organization のリストを返します。
     * もしOrganizationを一つも所有していないければ空のリストを返します。</p>
     *
     * @return Organization リスト
     */
    public Organization[] get_owned_organizations() throws NotAvailable {

        rtcout.println(rtcout.TRACE, "RTObject_impl.get_owned_organizations()");

        try {
            return m_sdoOwnedOrganizations.value.clone();
        } catch(Exception ex) {
            throw new NotAvailable();
        }
    }

    /**
     * <p>[CORBA interface] SDO IDを取得します。<br />
     * SDO ID を返すオペレーション。
     * このオペレーションは以下の型の例外を発生させる場合があります。</p>
     * 
     * @return    リソースデータモデルで定義されている SDO の ID
     * 
     * @exception SDONotExists ターゲットのSDOが存在しない。
     * @exception NotAvailable SDOは存在するが応答がない。
     * @exception InternalError 内部的エラーが発生した。
     */
    public String get_sdo_id() throws NotAvailable, InternalError {

        rtcout.println(rtcout.TRACE, "RTObject_impl.get_sdo_id()");

        try {
            String sdo_id = m_profile.instance_name;
            return sdo_id;
        } catch(Exception ex) {
            throw new InternalError("get_sdo_id()");
      }
    }
    
    /**
     * <p>[CORBA interface] SDO タイプを取得します。<br />
     * SDO Type を返すオペレーション。
     * このオペレーションは以下の型の例外を発生させる場合があります。</p>
     * 
     * @return    リソースデータモデルで定義されている SDO の Type
     * 
     * @exception SDONotExists ターゲットのSDOが存在しない。
     * @exception NotAvailable SDOは存在するが応答がない。
     * @exception InternalError 内部的エラーが発生した。
     */
    public String get_sdo_type() throws NotAvailable, InternalError {

        rtcout.println(rtcout.TRACE, "RTObject_impl.get_sdo_type()");

        try {
            String sdo_type = m_profile.description;
            return sdo_type;
        } catch (Exception ex) {
            throw new InternalError("get_sdo_type()");
        }
    }

    /**
     * <p>[CORBA interface] SDO DeviceProfile リストを取得します。<br />
     * SDO の DeviceProfile を返すオペレーション。 
     * SDO がハードウエアデバイスに関連付けられていない場合には、空の DeviceProfile を返します。
     * このオペレーションは以下の型の例外を発生させる場合があります。</p>
     * 
     * @return    リソースデータモデルで定義されている SDO の DeviceProfile
     * 
     * @exception NotAvailable SDOは存在するが応答がない。
     * @exception InternalError 内部的エラーが発生した。
     */
    public DeviceProfile get_device_profile() throws NotAvailable, InternalError {

        rtcout.println(rtcout.TRACE, "RTObject_impl.get_device_profile()");

        try {
            DeviceProfile dprofile = m_pSdoConfigImpl.getDeviceProfile();
            return dprofile;
        } catch(Exception ex) {
            throw new InternalError("get_device_profile()");
        }
    }
    
    /**
     * <p>[CORBA interface] SDO ServiceProfile を取得します。<br />
     * SDO が所有している Service の ServiceProfile を返すオペレーション。
     * SDO がサービスを一つも所有していない場合には、空のリストを返します。
     * このオペレーションは以下の型の例外を発生させる場合があります。</p>
     * 
     * @return    リソースデータモデルで定義されている SDO の ServiceProfile
     * 
     * @exception SDONotExists ターゲットのSDOが存在しない。
     * @exception NotAvailable SDOは存在するが応答がない。
     * @exception InternalError 内部的エラーが発生した。
     */
    public ServiceProfile[] get_service_profiles() throws NotAvailable, InternalError {

        rtcout.println(rtcout.TRACE, "RTObject_impl.get_service_profiles()");

        try {
            return m_pSdoConfigImpl.getServiceProfiles().value;
        } catch(Exception ex) {
            throw new InternalError("get_service_profiles()");
        }
    }
    
    /**
     * <p>[CORBA interface] 特定のServiceProfile を取得します。<br />
     * 引数 "id" で指定された名前のサービスの ServiceProfile を返します。</p>
     * 
     * @param     id SDO Service の ServiceProfile に関連付けられた識別子。
     * 
     * @return    指定された SDO Service の ServiceProfile
     * 
     * @exception InvalidParameter パラメータ値が不正。
     * @exception NotAvailable SDOは存在するが応答がない。
     * @exception InternalError 内部的エラーが発生した。
     */
    public ServiceProfile get_service_profile(String id) throws InvalidParameter, NotAvailable, InternalError {


        rtcout.println(rtcout.TRACE, "RTObject_impl.get_service_profile(" + id + ")");

        try {
            if( id == null || id.equals("") ) {
                throw new InvalidParameter("get_service_profile(): Empty name.");
            }
            ServiceProfile svcProf = m_pSdoConfigImpl.getServiceProfile(id);
            if(svcProf == null || !id.equals(svcProf.id)) {
                throw new InvalidParameter("get_service_profile(): Inexist id.");
            }
            ServiceProfileHolder sprofile = new ServiceProfileHolder(svcProf);
            return sprofile.value; 
        } catch(InvalidParameter ex) {
            throw ex;
        } catch(Exception ex) {
            throw new InternalError("get_service_profile()");
        }
    }

    /**
     * <p>[CORBA interface] 指定された SDO Service を取得します。<br />
     * このオペレーションは引数 "id" で指定された名前によって区別される
     * SDO の Service へのオブジェクト参照を返します。 SDO により提供される
     * Service はそれぞれ一意の識別子により区別されます。</p>
     * 
     * @param id SDO Service に関連付けられた識別子。
     * 
     * @return 要求された SDO Service への参照。
     * 
     * @exception InvalidParameter パラメータ値が不正。
     * @exception NotAvailable SDOは存在するが応答がない。
     * @exception InternalError 内部的エラーが発生した。
     */
    public SDOService get_sdo_service(String id) throws InvalidParameter, NotAvailable, InternalError {

        rtcout.println(rtcout.TRACE, "RTObject_impl.get_sdo_service(" + id + ")");

        try {
            if( id == null || id.equals("") ) {
                throw new InvalidParameter("get_sdo_service(): Empty name.");
            }
            ServiceProfile svcProf = m_pSdoConfigImpl.getServiceProfile(id);
            if( svcProf == null || !id.equals(svcProf.id)) {
                throw new InvalidParameter("get_sdo_service(): Inexist id.");
            }
            SDOServiceHolder svcVar = new SDOServiceHolder(svcProf.service);
            return svcVar.value;
        } catch(InvalidParameter ex ) {
            throw ex;
        } catch(Exception ex) {
            throw new InternalError("get_service_profile()");
        }
    }
    
    /**
     * <p>[CORBA interface] Configuration オブジェクト を取得します。<br />
     * このオペレーションは Configuration interface への参照を返します。
     * Configuration interface は各 SDO を管理するためのインターフェースのひとつです。
     * このインターフェースは DeviceProfile, ServiceProfile,
     * Organization で定義された SDO の属性値を設定するために使用されます。
     * Configuration インターフェースの詳細については、OMG SDO specification
     * の 2.3.5節, p.2-24 を参照してください。</p>
     * 
     * @return SDO の Configuration インターフェースへの参照。
     * 
     * @exception InterfaceNotImplemented SDOはConfigurationインターフェースを持たない。
     * @exception SDONotExists ターゲットのSDOが存在しない。
     * @exception NotAvailable SDOは存在するが応答がない。
     * @exception InternalError 内部的エラーが発生した。
     */
    public Configuration get_configuration() throws InterfaceNotImplemented, NotAvailable, InternalError {

        rtcout.println(rtcout.TRACE, "RTObject_impl.get_configuration()");

        if( m_pSdoConfig == null) {
            throw new InterfaceNotImplemented();
        }
        try {
            Configuration config = m_pSdoConfig;
            return config;
        } catch(Exception ex) {
            throw new InternalError("get_configuration()");
        }
    }
    
    /**
     * <p>[CORBA interface] Monitoring オブジェクト を取得します。<br />
     *  このオペレーションは Monitoring interface への参照を返します。
     * Monitoring interface は SDO が管理するインターフェースの一つです。
     * このインターフェースは SDO のプロパティをモニタリングするために使用されます。
     * Monitoring interface の詳細については OMG SDO specification の
     * 2.3.7節 "Monitoring Interface" p.2-35 を参照してください。</p>
     * 
     * @return SDO の Monitoring interfaceへの参照。
     * 
     * @exception InterfaceNotImplemented SDOはMonitoringインターフェースを持たない。
     * @exception SDONotExists ターゲットのSDOが存在しない。
     * @exception NotAvailable SDOは存在するが応答がない。
     * @exception InternalError 内部的エラーが発生した。
     */
    public Monitoring get_monitoring() throws InterfaceNotImplemented, NotAvailable, InternalError {

        rtcout.println(rtcout.TRACE, "RTObject_impl.get_monitoring()");

        throw new InterfaceNotImplemented();
    }

    /**
     * <p>[CORBA interface] Organization リスト を取得します。<br />
     * SDO は0個以上の Organization (組織)に所属することができます。
     * もし SDO が1個以上の Organization に所属している場合、このオペレーションは所属する
     * Organization のリストを返します。SDO が どの Organization にも所属していない
     * 場合には、空のリストが返されます。</p>
     * 
     * @return SDO が所属する Organization のリスト。
     * 
     * @exception SDONotExists ターゲットのSDOが存在しない。
     * @exception NotAvailable SDOは存在するが応答がない。
     * @exception InternalError 内部的エラーが発生した。
     */
    public Organization[] get_organizations() throws NotAvailable, InternalError {

        rtcout.println(rtcout.TRACE, "RTObject_impl.get_organizations()");

        try {
            OrganizationListHolder orgList = new OrganizationListHolder(m_pSdoConfigImpl.getOrganizations().value);
            return orgList.value;
        } catch (Exception ex) {
            throw new InternalError("get_organizations()");
        }
    }

    /**
     * <p>[CORBA interface] SDO Status リスト を取得します。<br />
     * このオペレーションは SDO のステータスを表す NVList を返します</p>
     * 
     * @return SDO のステータス。
     * 
     * @exception SDONotExists ターゲットのSDOが存在しない。
     * @exception NotAvailable SDOは存在するが応答がない。
     * @exception InternalError 内部的エラーが発生した。
     */
    public NameValue[] get_status_list() throws NotAvailable, InternalError {

        rtcout.println(rtcout.TRACE, "RTObject_impl.get_status_list()");

        if(m_sdoStatus.value==null){
            NVListHolder holder  = new NVListHolder();
            CORBA_SeqUtil.push_back(holder, 
                                    NVUtil.newNV("", "", String.class));
            try {
                return holder.value.clone(); 
            } catch(Exception ex) {
                throw new InternalError("get_status_list()");
            }
        }
        else{
            try {
                return m_sdoStatus.value.clone(); 
            } catch(Exception ex) {
                throw new InternalError("get_status_list()");
            }
        }
    }

    /**
     * <p>[CORBA interface] SDO Status を取得します。<br />
     * このオペレーションは 引数で指定された SDO のステータスを返します</p>
     * 
     * @param name SDO のステータスを定義するパラメータ。
     * 
     * @return 指定されたパラメータのステータス値。
     * 
     * @exception SDONotExists ターゲットのSDOが存在しない。
     * @exception NotAvailable SDOは存在するが応答がない。
     * @exception InvalidParameter 引数 "name" が null あるいは存在しない。
     * @exception InternalError 内部的エラーが発生した。
     */
    public Any get_status(String name) throws InvalidParameter, NotAvailable, InternalError {


        rtcout.println(rtcout.TRACE, "RTObject_impl.get_status(" + name + ")");

        int index = CORBA_SeqUtil.find(m_sdoStatus, new nv_name(name));
        if( index < 0 ) {
            throw new InvalidParameter("get_status(): Not found");
        }
        try {
            Any status = ORBUtil.getOrb().create_any();
            status = m_sdoStatus.value[index].value;
            return status;
        } catch(Exception ex) {
            throw new InternalError("get_status()");
        }
    }

    /**
     * <p>当該オブジェクトのインスタンス名を取得します。</p>
     * 
     * @return インスタンス名
     */
    public final String getInstanceName() {

        rtcout.println(rtcout.TRACE, "RTObject_impl.getInstanceName()");

        return m_profile.instance_name;
    }
    /**
     * <p>当該オブジェクトのインスタンス名を設定します。</p>
     * 
     * @param instance_name インスタンス名
     */
    public void setInstanceName(final String instance_name) {

        rtcout.println(rtcout.TRACE, "RTObject_impl.setInstanceName(" + instance_name + ")");

        m_properties.setProperty("instance_name", instance_name);
        m_profile.instance_name = m_properties.getProperty("instance_name");
    }
    /**
     * <p>当該オブジェクトの型名を取得します。</p>
     * 
     * @return 型名
     */
    public final String getTypeName() {

        rtcout.println(rtcout.TRACE, "RTObject_impl.getTypeName()");

        return m_profile.type_name;
    }
    /**
     * <p>当該オブジェクトのカテゴリを取得します。</p>
     * 
     * @return カテゴリ
     */
    public final String getCategory() {

        rtcout.println(rtcout.TRACE, "RTObject_impl.getCategory()");

        return m_profile.category;
    }
    /**
     * <p>当該オブジェクトのNaming Seriveへの登録名を取得します。</p>
     * 
     * @return Naming Seriveへの登録名
     */
    public String[] getNamingNames() {

        rtcout.println(rtcout.TRACE, "RTObject_impl.getNamingNames()");

        return m_properties.getProperty("naming.names").split(",");
    }
    /**
     * <p>CORBAオブジェクト参照を設定します。</p>
     * 
     * @param rtobj CORBAオブジェクト参照
     */
    public void setObjRef(final RTObject rtobj) {

        rtcout.println(rtcout.TRACE, "RTObject_impl.setObjRef()");

        m_objref = rtobj;
    }
    /**
     * <p>CORBAオブジェクト参照を取得します。</p>
     * 
     * @return rtobj CORBAオブジェクト参照
     */
    public final RTObject getObjRef() {

        rtcout.println(rtcout.TRACE, "RTObject_impl.getObjRef()");

        return (RTObject)m_objref._duplicate();
    }
    /**
     * <p>[local interface] RTC のプロパティを設定します。<br />
     * RTコンポーネント が保持すべきプロパティを設定します。
     * 与えられるプロパティは、ComponentProfile 等に設定されるべき情報を持たなければなりません。
     * このオペレーションは通常 RTコンポーネント が初期化される際に Manager から
     * 呼ばれることを意図しています。</p>
     * 
     * @param prop RTC のプロパティ
     */
    public void setProperties(final Properties prop) {

        rtcout.println(rtcout.TRACE, "RTObject_impl.setProperties()");

        m_properties.merge(prop);
        try {
            syncAttributesByProperties();
        } catch (Exception ex) {
            
        }
    }

    /**
     * <p> syncAttributesByProperties </p>
     */
    protected void syncAttributesByProperties() throws Exception {
        // Properties --> DeviceProfile
        DeviceProfile devProf = m_pSdoConfigImpl.getDeviceProfile();
        devProf.device_type = m_properties.getProperty("category");
        devProf.manufacturer = m_properties.getProperty("vendor");
        devProf.model = m_properties.getProperty("type_name");
        devProf.version = m_properties.getProperty("version");
        devProf.properties = new NameValue[0];
        m_pSdoConfigImpl.set_device_profile(devProf);
        
        // Properties --> ComponentProfile
        m_profile.instance_name = m_properties.getProperty("instance_name");
        m_profile.type_name     = m_properties.getProperty("type_name");
        m_profile.description   = m_properties.getProperty("description");
        m_profile.version       = m_properties.getProperty("version");
        m_profile.vendor        = m_properties.getProperty("vendor");
        m_profile.category      = m_properties.getProperty("category");
        m_profile.properties    = new NameValue[0];
        m_profile.port_profiles = new PortProfile[0];
    }

    /**
     * <p>[local interface] RTC のプロパティを取得します。<br />
     * RTC が保持しているプロパティを返します。
     * RTCがプロパティを持たない場合は空のプロパティが返されます。</p>
     * 
     * @return RTC のプロパティ
     */
    public Properties getProperties() {

        rtcout.println(rtcout.TRACE, "RTObject_impl.getProperties()");

        return m_properties;
    }

    /**
     * <p>コンフィギュレーション・パラメータをbindします。</p>
     * 
     * @param param_name パラメータ名
     * @param var 値保持用オブジェクト
     * @param def_val デフォルト値
     * 
     * @return bind結果
     */
    public boolean bindParameter(final String param_name, ValueHolder var, final String def_val) {

        rtcout.println(rtcout.TRACE, "RTObject_impl.bindParameter(" + param_name + "," + def_val + ")");
        m_configsets.bindParameter(param_name, var, def_val);
        return true;
    }
    
    /**
     * <p>コンフィギュレーション・パラメータを更新します。</p>
     * 
     * @param config_set 更新対象値
     */
    public void updateParameters(final String config_set) {

        rtcout.println(rtcout.TRACE, "RTObject_impl.updateParameters(" + config_set + ")");

        m_configsets.update(config_set);
        return;
    }

    /**
     * <p>[local interface] Port を登録します。<br />
     *
     * RTC が保持するPortを登録します。
     * Port を外部からアクセス可能にするためには、このオペレーションにより
     * 登録されていなければなりません。登録される Port はこの RTC 内部において
     * PortProfile.name により区別されます。したがって、Port は RTC 内において、
     * ユニークな PortProfile.name を持たなければなりません。
     * 登録された Port は内部で適切にアクティブ化された後、その参照と
     * オブジェクト参照がリスト内に保存されます。</p>
     * 
     * @param port RTC に登録する Port
     */
    public void registerPort(PortBase port) {

        rtcout.println(rtcout.TRACE, "RTObject_impl.registerPort(PortBase)");

        m_portAdmin.registerPort(port);
        port.setOwner(this.getObjRef());
        return;
    }


    /**
     * <p> registerPort </p>
     *
     * @param port PortService
     *
     */
    public void registerPort(PortService port) {

        rtcout.println(rtcout.TRACE, "RTObject_impl.registerPort(PortService)");

        m_portAdmin.registerPort(port);
        return;
    }

    /**
     * <p>[local interface] DataInPort を登録します。<br />
     *
     * RTC が保持するDataInPortを登録します。</p>
     * 
     * @param DATA_TYPE_CLASS DataInPortがやりとりするデータ型
     * @param name DataInPortの名称
     * @param inport InPortへの参照
     */
    public <DataType, Buffer> void registerInPort(Class<DataType> DATA_TYPE_CLASS, 
                            final String name, InPort<DataType> inport) throws Exception {

        rtcout.println(rtcout.TRACE, "RTObject_impl.registerInPort()");

        this.registerInPort(name, inport);
//        String propkey = "port.dataport." + name + ".tcp_any";
//        PortBase port = new DataInPort(DATA_TYPE_CLASS, name, inport, m_properties.getNode(propkey));
//        this.registerPort(port);
    }

    /**
     * <p>[local interface] DataInPort を登録します。<br />
     *
     * RTC が保持するDataInPortを登録します。</p>
     * 
     * @param name DataInPortの名称
     * @param inport InPortへの参照
     */
    public void registerInPort(final String name, InPortBase inport) throws Exception {

        rtcout.println(rtcout.TRACE, "RTObject_impl.registerInPort()");

        if (m_properties.hasKey("port.inport") != null) {
	    
//          inport.properties() << m_properties.getNode("port.inport");
            inport.properties().merge(m_properties.getNode("port.inport"));
        }
        String propkey = "port.inport." + name;
        if (m_properties.hasKey(propkey) != null) {
//          inport.properties() << m_properties.getNode(propkey);
            inport.properties().merge(m_properties.getNode(propkey));
        }
        inport.init();
        this.registerPort(inport);
    }

    /**
     * <p>[local interface] DataOutPort を登録します。<br />
     *
     * RTC が保持するDataOutPortを登録します。</p>
     * 
     * @param DATA_TYPE_CLASS DataOutPortがやりとりするデータ型
     * @param name DataOutPortの名称
     * @param outport OutPortへの参照
     */
    public <DataType, Buffer> void registerOutPort(Class<DataType> DATA_TYPE_CLASS, 
                          final String name, OutPort<DataType> outport) throws Exception {

        rtcout.println(rtcout.TRACE, "RTObject_impl.registerOutPort()");

        this.registerOutPort(name, outport);
//	String propkey = "port.dataport." + name;
//        PortBase port = new DataOutPort(DATA_TYPE_CLASS, name, outport, m_properties.getNode(propkey));
//        this.registerPort(port);
    }

    /**
     * <p>[local interface] DataOutPort を登録します。<br />
     *
     * RTC が保持するDataOutPortを登録します。</p>
     * 
     * @param name DataOutPortの名称
     * @param outport OutPortへの参照
     */
    public void registerOutPort(final String name, OutPortBase outport) throws Exception {

        rtcout.println(rtcout.TRACE, "RTObject_impl.registerOutPort()");

        String propkey = "port.outport." + name;
//      m_properties.getNode(propkey) << m_properties.getNode("port.outport.dataport");
        m_properties.getNode(propkey).merge(m_properties.getNode("port.outport.dataport"));

//      outport.properties() << m_properties.getNode(propkey);
        outport.properties().merge(m_properties.getNode(propkey));
        this.registerPort(outport);
    }

    /**
     * <p>[local interface] Port の登録を削除します。<br />
     *
     * RTC が保持するPortの登録を削除します。</p>
     * 
     * @param port RTC に登録する Port
     */
    public void deletePort(PortBase port) {

      rtcout.println(rtcout.TRACE, "RTObject_impl.deletePort(PortBase)");

      m_portAdmin.deletePort(port);
      return;
    }

    /**
     * <p> deleteProt </p>
     *
     * @param port PortService
     */
    public void deletePort(PortService port) {

        rtcout.println(rtcout.TRACE, "RTObject_impl.deletePort(PortService)");

      m_portAdmin.deletePort(port);
      return;
    }

    /**
     * <p>[local interface] 名称によって指定された Port の登録を削除します。</p>
     * 
     * @param port_name 削除対象のポート名
     */
    public void deletePortByName(final String port_name) {

        rtcout.println(rtcout.TRACE, "RTObject_impl.deletePortByNamed(" + port_name + ")");

        m_portAdmin.deletePortByName(port_name);
        return;
    }

    /**
     * <p>登録されているすべてのPortの登録を削除します。</p>
     */
    public void finalizePorts() {

        rtcout.println(rtcout.TRACE, "RTObject_impl.finalizePorts()");

        m_portAdmin.finalizePorts();
    }

    /**
     * <p>登録されているすべてのContextの登録を削除します。</p>
     */
    public void finalizeContexts() {

        rtcout.println(rtcout.TRACE, "RTObject_impl.finalizeContexts()");

        for(int i=0, len=m_eclist.size(); i < len; ++i) {
            try {
                m_eclist.elementAt(i).stop();
		m_eclist.elementAt(i).finalizeExecutionContext();
                m_pPOA.deactivate_object(m_pPOA.servant_to_id(m_eclist.elementAt(i)));
            }
            catch(Exception ex) {
            }
        }
        if (!m_eclist.isEmpty()) {
            m_eclist.clear();
        }
    }

    /**
     * <p>当該コンポーネントを終了します。</p>
     */
    protected void shutdown() {

        try {
            finalizePorts();
            finalizeContexts();
            m_pPOA.deactivate_object(m_pPOA.servant_to_id(m_pSdoConfigImpl));
            m_pPOA.deactivate_object(m_pPOA.servant_to_id(this));
        } catch(Exception ex) {
        }

        if( m_pManager != null) {
            m_pManager.cleanupComponent(this);
        }
   }

    /**
     * <p>Manager オブジェクト</p>
     */
    protected Manager m_pManager;

    /**
     * <p>ORB</p>
     */
    protected ORB m_pORB;

    /**
     * <p>POA</p>
     */
    protected POA m_pPOA;

    /**
     * <p>SDO organization リスト</p>
     */
    protected OrganizationListHolder m_sdoOwnedOrganizations = new OrganizationListHolder();

    /**
     * SDOService のプロファイルリスト
     */
    protected ServiceProfileListHolder  m_sdoSvcProfiles = new ServiceProfileListHolder();

    /**
     * SDO Configuration Interface へのポインタ
     */
    protected Configuration_impl m_pSdoConfigImpl;

    /**
     * SDO Configuration
     */
    protected Configuration m_pSdoConfig;

    /**
     * SDO organization
     */
    protected OrganizationListHolder m_sdoOrganizations = new OrganizationListHolder();

    /**
     * SDO Status
     */
    protected NVListHolder m_sdoStatus = new NVListHolder();

    /**
     * ComponentProfile
     */
    protected ComponentProfile m_profile = new ComponentProfile();

    /**
     * CORBAオブジェクトへの参照
     */
    protected RTObject m_objref;

    /**
     * Port のオブジェクトリファレンスのリスト
     */
    protected PortAdmin m_portAdmin;

    /**
     * 自分がownerのExecutionContextService のリスト
     */
    protected ExecutionContextServiceListHolder m_ecMine;
    
    /**
     * ExecutionContextBase のリスト
     */
    protected Vector<ExecutionContextBase> m_eclist = new Vector<ExecutionContextBase>();

    /**
     * 参加しているExecutionContextService のリスト
     */
    protected ExecutionContextServiceListHolder m_ecOther;

    /**
     * 生成済みフラグ
     */
    protected boolean m_created;
    /**
     * RTC のプロパティ
     */
    protected Properties m_properties = new Properties();
    /**
     * RTC のコンフィギュレーション管理オブジェクト
     */
    protected ConfigAdmin m_configsets;
    /**
     * RTコンポーネント検索用ヘルパークラス
     */
    class nv_name implements equalFunctor{
        public nv_name(final String name) {
            m_name = name;
        }
        public boolean equalof(final Object nv) {
            return m_name.equals(((NameValue)nv).name);
        }
        private String m_name;
    }

    /**
     * ExecutionContext コピー用ファンクタ
     */
    class ec_copy implements operatorFunc
    {
        /**
         * <p> constructor </p>
         */
        public ec_copy(ExecutionContextListHolder eclist)
        { 
            m_eclist = eclist; 
        }
        /**
         * <p> operator </p>
         */
        public void operator(Object elem) {
            operator((ExecutionContextService) elem);
        }
        /**
         * <p> operator </p>
         */
        public void operator(ExecutionContextService ecs)
        {
            if(ecs != null)  {
                CORBA_SeqUtil.push_back(m_eclist, (ExecutionContext)ecs._duplicate());
            }
        }
        private ExecutionContextListHolder m_eclist;
    };

    /**
     * ExecutionContext 検索用ファンクタ
     */
    class ec_find implements equalFunctor
    {
        /**
         * <p> constructor </p> 
         */
        public ec_find(ExecutionContext ec)
        {
            m_ec = ec;
        }
        /**
         * <p> equalof </p> 
         */
        public boolean equalof(final java.lang.Object object){
            return operator((ExecutionContextService) object);
        }
        /**
         * <p> operator </p> 
         */
        public boolean operator(ExecutionContextService ecs)
        {
            try
            {
                if(ecs != null)  {
                    ExecutionContext ec;
                    ec = ExecutionContextHelper.narrow(ecs);
                    return m_ec._is_equivalent(ec);
                }
            }
            catch (Exception ex)
            {
                return false;
            }
            return false;
        }
        private ExecutionContext m_ec;
    };

    /**
     * RTC 非活性化用ファンクタ
     */
    class deactivate_comps implements operatorFunc
    {
        /**
         * <p> constructor </p> 
         */
        deactivate_comps(LightweightRTObject comp)
        {
            m_comp = comp;
        }
        /**
         * <p> operator </p> 
         */
          public void operator(Object elem) {
            operator((ExecutionContextService) elem);
          }
        /**
         *    
         * <p> operator </p> 
         *
         */
        void operator(ExecutionContextService ecs)
        {
            if(ecs != null)  {
                ecs.deactivate_component((LightweightRTObject)m_comp._duplicate());
            }
        }
        LightweightRTObject m_comp;
    };

    /**
     * <p>Logging用フォーマットオブジェクト</p>
     */
    protected Logbuf rtcout;
}
