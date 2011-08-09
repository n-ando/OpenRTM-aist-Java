package jp.go.aist.rtm.RTC;

import java.util.Vector;
import java.lang.reflect.Method;

import jp.go.aist.rtm.RTC.SDOPackage.Configuration_impl;
import jp.go.aist.rtm.RTC.executionContext.ExecutionContextBase;
import jp.go.aist.rtm.RTC.port.CorbaPort;
import jp.go.aist.rtm.RTC.port.InPort;
import jp.go.aist.rtm.RTC.port.InPortBase;
import jp.go.aist.rtm.RTC.port.OutPort;
import jp.go.aist.rtm.RTC.port.OutPortBase;
import jp.go.aist.rtm.RTC.port.PortAdmin;
import jp.go.aist.rtm.RTC.port.PortBase;
import jp.go.aist.rtm.RTC.port.PortConnectListener;
import jp.go.aist.rtm.RTC.port.PortConnectListeners;
import jp.go.aist.rtm.RTC.port.PortConnectListenerType;
import jp.go.aist.rtm.RTC.port.PortConnectRetListener;
import jp.go.aist.rtm.RTC.port.PortConnectRetListenerType;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.ORBUtil;
import jp.go.aist.rtm.RTC.util.POAUtil;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.ValueHolder;
import jp.go.aist.rtm.RTC.util.equalFunctor;
import jp.go.aist.rtm.RTC.util.operatorFunc;
import jp.go.aist.rtm.RTC.log.Logbuf;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.SystemException;
import org.omg.PortableServer.POA;

import OpenRTM.DataFlowComponent;
import OpenRTM.DataFlowComponentHelper;
import OpenRTM.DataFlowComponentPOA;
import RTC.ComponentProfile;
import RTC.ConnectorProfile;
import RTC.ExecutionContext;
import RTC.ExecutionContextHelper;
import RTC.ExecutionContextListHolder;
import RTC.ExecutionContextService;
import RTC.ExecutionContextServiceHelper;
import RTC.ExecutionContextServiceListHolder;
import RTC.LightweightRTObject;
import RTC.PortProfile;
import RTC.PortService;
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
   * {@.ja RTコンポーネントクラス。}
   * {@.en RT-Component class}
   *
   * <p>
   * {@.ja DataFlowComponentのベースクラス。
   * ユーザが新たなRTコンポーネントを作成する場合は、このクラスを拡張する。
   * 各RTコンポーネントのベースとなるクラス。
   * Robotic Technology Component 仕様中の lightweightRTComponentの実装クラス。
   * コンポーネントの機能を提供する ComponentAction インターフェースと
   * コンポーネントのライフサイクル管理を行うための LightweightRTObject の実装を
   * 提供する。
   * 実際にユーザがコンポーネントを作成する場合には、Execution Semantics に対応
   * した各サブクラスを利用する。<BR>
   * (現状の実装では Periodic Sampled Data Processing のみサポートしているため、
   *  dataFlowComponent を直接継承している)}
   * {@.en This is a class to be a base of each RT-Component.
   * This is a implementation class of lightweightRTComponent in Robotic
   * Technology Component specification.
   * This provides a implementation of ComponentAction interface that offers
   * the the component's function and the implementation of LightweightRTObject
   * for management of the component's lifecycle.
   * When users actually create the components, they should use each subclass
   * corresponding to Execution Semantics.<BR>
   * (In current implementation, since only Periodic Sampled Data Processing is
   * supported, this class inherits dataFlowComponent directly.)}
   */
public class RTObject_impl extends DataFlowComponentPOA {

    /**
     * {@.ja RTコンポーネントのデフォルト・コンポーネント・プロファイル。}
     * {@.en Default component profile of RT component.}
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
     * {@.ja コンストラクタ。}
     * {@.en Constructor}
     * @param manager 
     *   {@.ja Managerオブジェクト}
     *   {@.en Manager object}
     */
    public RTObject_impl(Manager manager) {
        m_pManager =  manager;
        m_pORB = manager.getORB();
        m_pPOA = manager.getPOA();
        m_portAdmin = new PortAdmin(manager.getORB(), manager.getPOA());
        m_created = true;
        m_properties = new Properties(default_conf);
        m_configsets = new ConfigAdmin(m_properties.getNode("conf"));
        m_readAll = false;
        m_writeAll = false;
        m_readAllCompletion = false;
        m_writeAllCompletion = false;
        
        m_objref = this._this();
        m_pSdoConfigImpl = new Configuration_impl(m_configsets,m_sdoservice);
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

        m_profile.properties    = new NameValue[0];
        rtcout = new Logbuf("RTObject_impl");
         
    }

    /**
     * {@.ja コンストラクタ。}
     * {@.en Constructor}
     * @param orb 
     *   {@.ja ORB}
     *   {@.en ORB}
     * @param poa 
     *   {@.ja POA}
     *   {@.en POA}
     */
    public RTObject_impl(ORB orb, POA poa) {
        m_pManager =  null;
        m_pORB = orb;
        m_pPOA = poa;
        m_portAdmin = new PortAdmin(orb, poa);
        m_created = true;
        m_properties = new Properties(default_conf);
        m_configsets = new ConfigAdmin(m_properties.getNode("conf"));
        m_readAll = false;
        m_writeAll = false;
        m_readAllCompletion = false;
        m_writeAllCompletion = false;
        
        m_objref = this._this();
        m_pSdoConfigImpl = new Configuration_impl(m_configsets,m_sdoservice);
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
        m_profile.properties    = new NameValue[0];
        rtcout = new Logbuf("RTObject_impl");
    }

    /**
     * {@.ja DataFlowComponentオブジェクトの取得。}
     * {@.en Gets DataFlowComponent object.}
     *
     * <p>
     * {@.ja DataFlowComponentオブジェクト参照を取得する。}
     * {@.en Gets OpenRTM.DataFlowComponent object.}
     * 
     * @return 
     *   {@.ja DataFlowComponentオブジェクト}
     *   {@.en OpenRTM.DataFlowComponent object.}
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
     * {@.ja 初期化処理用コールバック関数。}
     * {@.en Callback function to initialize}
     * 
     * <p>
     * {@.ja コンポーネント生成時(Created->Alive)に呼び出されるアクション。
     *  ComponentAction.on_initialize が呼ばれた際に実行されるコールバック
     * 関数。<BR>
     * 本関数は無条件に ReturnCode_t.RTC_OK を返すようにダミー実装されている
     * ので、
     * 各コンポーネントの実際の初期化処理は、本関数をオーバーライドして実装する
     * 必要がある。}
     * {@.en This is a callback function that is executed when
     * ComponentAction::on_initialize was invoked.<BR>
     * As for actual initialization of each component, since this function is
     * dummy-implemented to return RTC::RTC_OK unconditionally, you need to
     * implement this function by overriding it.}
     * 
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     * 
     */
    protected ReturnCode_t onInitialize(){
        rtcout.println(Logbuf.TRACE, "RTObject_impl.onInitialize()");
        return ReturnCode_t.RTC_OK;
    }

    /**
     * {@.ja  終了処理用コールバック関数。}
     * {@.en Callback function to finalize}
     * 
     * <p>
     * {@.ja コンポーネント破棄時(Alive->Exit)に呼び出されるアクション。
     * ComponentAction.on_finalize が呼ばれた際に実行されるコールバック関数。
     * 本関数は無条件に ReturnCode_t.RTC_OK を返すようにダミー実装されている
     * ので、
     * 各コンポーネントの実際の終了処理は、本関数をオーバーライドして実装する
     * 必要がある。}
     * {@.en This is a callback function that is executed when
     * ComponentAction::on_finalize was invoked.<BR>
     * As for actual finalization of each component, since this function is
     * dummy-implemented to return RTC::RTC_OK unconditionally, you need to
     * implement this function by overriding it.}
     * 
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     * 
     */
    protected ReturnCode_t onFinalize() {
        rtcout.println(Logbuf.TRACE, "RTObject_impl.onFinalize()");
        return ReturnCode_t.RTC_OK;
    }

    /**
     * {@.ja 開始処理用コールバック関数。}
     * {@.en Callback function for startup action}
     * 
     * <p>
     * {@.ja ExecutionContextの動作開始時(Stopped->Started)に呼び出される
     * アクション。
     * ComponentAction.on_startup が呼ばれた際に実行されるコールバック
     * 関数。<BR>
     * 本関数は無条件に ReturnCode_t.RTC_OK を返すようにダミー実装されている
     * ので、
     * 各コンポーネントの実際の開始処理は、本関数をオーバーライドして実装する
     * 必要がある。}
     * {@.en Callback function that is executed when
     * ComponentAction::on_startup was invoked.<BR>
     * As for actual startup of each component, since this function is
     * dummy-implemented to return RTC::RTC_OK unconditionally, you need to
     * implement this function by overriding it.}
     * 
     * @param ec_id 
     *   {@.ja 対象ExecutionContext ID}
     *   {@.en ID of the participant ExecutionContext}
     *
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     */
    protected ReturnCode_t onStartup(int ec_id) {
        rtcout.println(Logbuf.TRACE, "RTObject_impl.onStartup(" + ec_id + ")");
        return ReturnCode_t.RTC_OK;
    }

    /**
     * {@.ja 停止処理用コールバック関数。}
     * {@.en Callback function for shutdown action}
     * 
     * <p>
     * {@.ja ExecutionContextの動作終了時(Started->Stopped)に呼び出される
     * アクション。
     * ComponentAction::on_shutdown が呼ばれた際に実行されるコールバック
     * 関数。<BR>
     * 本関数は無条件に RTC::RTC_OK を返すようにダミー実装されているので、
     * 各コンポーネントの実際の停止処理は、本関数をオーバーライドして実装する
     * 必要がある。}
     * {@.en Callback function that is executed when
     * ComponentAction::on_shutdown was invoked.<BR>
     * As for actual shutdown of each component, since this function is
     * dummy-implemented to return RTC::RTC_OK unconditionally, you need to
     * implement this function by overriding it.}
     * 
     * @param ec_id
     *   {@.ja 対象ExecutionContext ID}
     *   {@.en ID of the participant ExecutionContext}
     *
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     * 
     */
    protected ReturnCode_t onShutdown(int ec_id) {
        rtcout.println(Logbuf.TRACE, "RTObject_impl.onShutdown(" + ec_id + ")");
        return ReturnCode_t.RTC_OK;
    }

    /**
     * {@.ja 活性化処理用コールバック関数。}
     * {@.en Callback function to activate}
     * 
     * <p>
     * {@.ja RTコンポーネントのActivate時(Inactive->Active)に呼び出される
     * アクション。
     * ComponentAction.on_activated が呼ばれた際に実行されるコールバック
     * 関数。<BR>
     * 本関数は無条件に RTC::RTC_OK を返すようにダミー実装されているので、
     * 各コンポーネントの実際の活性化処理は、本関数をオーバーライドして実装する
     * 必要がある。}
     * {@.en This is a callback function that is executed when
     * ComponentAction::on_activated was invoked.<BR>
     * As for actual activation of each component, since this function is
     * dummy-implemented to return RTC::RTC_OK unconditionally, you need to
     * implement this function by overriding it.}
     * 
     * @param ec_id
     *   {@.ja 対象ExecutionContext ID}
     *   {@.en ID of the participant ExecutionContext}
     *
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     * 
     */
    protected ReturnCode_t onActivated(int ec_id) {
        rtcout.println(Logbuf.TRACE, "RTObject_impl.onActivated(" + ec_id + ")");
        return ReturnCode_t.RTC_OK;
    }

    /**
     * {@.ja 非活性化処理用コールバック関数。}
     * {@.en Callback function to deactivate}
     * 
     * <p>
     * {@.ja RTコンポーネントのDeactivate時(Active->Inactive)に呼び出される
     * アクション。
     * ComponentAction.on_deactivated が呼ばれた際に実行されるコールバック
     * 関数。<BR>
     * 本関数は無条件に ReturnCode_t.RTC_OK を返すようにダミー実装されている
     * ので、
     * 各コンポーネントの実際の非活性化処理は、本関数をオーバーライドして
     * 実装する必要がある。}
     * {@.en This is a callback function that is executed when
     * ComponentAction::on_deactivated was invoked.<BR>
     * As for actual deactivation of each component, since this function is
     * dummy-implemented to return RTC::RTC_OK unconditionally, you need to
     * implement this function by overriding it.}
     * 
     * @param ec_id 
     *   {@.ja 対象ExecutionContext ID}
     *   {@.en ID of the participant ExecutionContext}
     *
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     * 
     */
    protected ReturnCode_t onDeactivated(int ec_id) {
        rtcout.println(Logbuf.TRACE, "RTObject_impl.onDeactivated(" + ec_id + ")");
        return ReturnCode_t.RTC_OK;
    }

    /**
     * {@.ja 周期処理用コールバック関数。}
     * [@.en Callback function to execute periodically}
     *
     * <p>
     * {@.ja RTコンポーネントがActivate状態の間呼び出されるアクション。
     * DataFlowComponentAction::on_execute が呼ばれた際に実行される
     * コールバック関数。<BR>
     * 本関数は無条件に RTC::RTC_OK を返すようにダミー実装されているので、
     * 各コンポーネントの実際の周期処理は、本関数をオーバーライドして実装する
     * 必要がある。<BR>
     * 本関数は Periodic Sampled Data Processing における Two-Pass Executionの
     * １回目の実行パスとして定期的に呼び出される。}
     * {@.en This is a callback function that is executed when
     * DataFlowComponentAction::on_execute is invoked.<BR>
     * As for actual periodic execution of each component, 
     * since this function is
     * dummy-implemented to return RTC::RTC_OK unconditionally, you need to
     * implement this function by overriding it.
     * This function is invoked periodically as the first execution pass of
     * Two-Pass Execution in Periodic Sampled Data Processing.}
     * 
     * 
     * @param ec_id 
     *   {@.ja 対象ExecutionContext ID}
     *   {@.en ID of the participant ExecutionContext}
     *
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     * 
     */
    protected ReturnCode_t onExecute(int ec_id) {
        rtcout.println(Logbuf.TRACE, "RTObject_impl.onExecute(" + ec_id + ")");
        return ReturnCode_t.RTC_OK;
    }

    /**
     * {@.ja 中断処理用コールバック関数。}
     * {@.en Callback function to abort}
     * 
     * <p>
     * {@.ja RTコンポーネントにエラーが発生した時(Active->Error)に呼び出される
     * アクション。
     * ComponentAction::on_aborting が呼ばれた際に実行されるコールバック
     * 関数。<BR>
     * 本関数は無条件に RTC::RTC_OK を返すようにダミー実装されているので、
     * 各コンポーネントの実際の中断処理は、本関数をオーバーライドして実装する
     * 必要がある。}
     * {@.en This is a callback function that is executed when
     * ComponentAction::on_aborting was invoked.<BR>
     * As for actual abortion of each component, since this function is
     * dummy-implemented to return RTC::RTC_OK unconditionally, you need to
     * implement this function by overriding it.}
     * 
     * @param ec_id 
     *   {@.ja 対象ExecutionContext ID}
     *   {@.en ID of the participant ExecutionContext}
     *
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     * 
     */
    protected ReturnCode_t onAborting(int ec_id) {
        rtcout.println(Logbuf.TRACE, "RTObject_impl.onAborting(" + ec_id + ")");
        return ReturnCode_t.RTC_OK;
    }

    /**
     * {@.ja エラー処理用コールバック関数。}
     * {@.en Callback function for error handling}
     * 
     * <p>
     * {@.ja RTコンポーネントがError状態の間呼び出されるアクション。
     * ComponentAction::on_error が呼ばれた際に実行されるコールバック関数。<BR>
     * 本関数は無条件に RTC::RTC_OK を返すようにダミー実装されているので、
     * 各コンポーネントの実際のエラー処理は、本関数をオーバーライドして実装する
     * 必要がある。}
     * {@.en This is a callback function that is executed when
     * ComponentAction::on_error was invoked.<BR>
     * As for actual error handling of each component, since this function is
     * dummy-implemented to return RTC::RTC_OK unconditionally, you need to
     * implement this function by overriding it.}
     * 
     * @param ec_id 
     *   {@.ja 対象ExecutionContext ID}
     *   {@.en ID of the participant ExecutionContext}
     *
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     */
    protected ReturnCode_t onError(int ec_id) {
        rtcout.println(Logbuf.TRACE, "RTObject_impl.onError(" + ec_id + ")");
        return ReturnCode_t.RTC_OK;
    }

    /**
     * {@.ja リセット処理用コールバック関数。}
     * {@.en Callback function to reset}
     * 
     * <p>
     * {@.ja RTコンポーネントをresetする際(Error->Inactive)に呼び出される
     * アクション。
     * ComponentAction.on_reset が呼ばれた際に実行されるコールバック関数。<BR>
     * 本関数は無条件に ReturnCode_t.RTC_OK を返すようにダミー実装されている
     * ので、
     * 各コンポーネントの実際のリセット処理は、本関数をオーバーライドして実装
     * する必要がある。}
     * {@.en This is a callback function that is executed when
     * ComponentAction::on_reset was invoked.<BR>
     * As for actual reset of each component, since this function is
     * dummy-implemented to return RTC::RTC_OK unconditionally, you need to
     * implement this function by overriding it.}
     * 
     * @param ec_id 
     *   {@.ja 対象ExecutionContext ID}
     *   {@.en ID of the participant ExecutionContext}
     *
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     */
    protected ReturnCode_t onReset(int ec_id) {
        rtcout.println(Logbuf.TRACE, "RTObject_impl.onReset(" + ec_id + ")");
        return ReturnCode_t.RTC_OK;
    }

    /**
     * {@.ja 状態変更処理用コールバック関数。}
     * {@.en Callback function to update the state}
     * 
     * <p>
     * {@.ja RTコンポーネントがActivate状態の間、
     * on_executeの続いて呼び出されるアクション。
     * DataFlowComponentAction::on_state_update が呼ばれた際に実行される
     * コールバック関数。<BR>
     * 本関数は無条件に RTC::RTC_OK を返すようにダミー実装されているので、
     * 各コンポーネントの実際の状態変更処理は、本関数をオーバーライドして
     * 実装する必要がある。<BR>
     * 本関数は Periodic Sampled Data Processing における Two-Pass Executionの
     * ２回目の実行パスとして定期的に呼び出される。}
     * {@.en This is a callback function that is executed when
     * DataFlowComponentAction::on_state_update was invoked.<BR>
     * As for actual updating the state of each component, 
     * since this function is
     * dummy-implemented to return RTC::RTC_OK unconditionally, you need to
     * implement this function by overriding it.<BR>
     * This function is invoked periodically as the second execution pass of
     * Two-Pass Execution in Periodic Sampled Data Processing.}
     *
     * @param ec_id 
     *   {@.ja 対象ExecutionContext ID}
     *   {@.en ID of the participant ExecutionContext}
     * 
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     */
    protected ReturnCode_t onStateUpdate(int ec_id) {
        rtcout.println(Logbuf.TRACE, "RTObject_impl.onStateUpdete(" + ec_id + ")");
        return ReturnCode_t.RTC_OK;
    }

    /**
     * {@.ja 動作周期変更通知用コールバック関数。}
     * {@.en Callback function to change execution cycle}
     * 
     * <p>
     * {@.ja ExecutionContextの実行周期が変更になった時に呼び出される
     * アクション。
     * DataFlowComponentAction::on_rate_changed が呼ばれた際に実行される
     * コールバック関数。<BR>
     * 本関数は無条件に RTC::RTC_OK を返すようにダミー実装されているので、
     * 各コンポーネントの実際の状態変更処理は、本関数をオーバーライドして
     * 実装する必要がある。<BR>
     * 本関数は Periodic Sampled Data Processing において ExecutionContext の
     * 実行が更新された際に呼び出される。}
     * {@.en This is a callback function that is executed when
     * DataFlowComponentAction::on_rate_changed was invoked.<BR>
     * As for actual changing execution cycle of each component, since this 
     * function is dummy-implemented to return RTC::RTC_OK unconditionally,
     * you need to implement this function by overriding it.<BR>
     * This function is invoked when the execution of ExecutionContext 
     * was updated in Periodic Sampled Data Processing.}
     *
     * @param ec_id 
     *   {@.ja 対象ExecutionContext ID}
     *   {@.en ID of the participant ExecutionContext}
     * 
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     * 
     */
    protected ReturnCode_t onRateChanged(int ec_id) {
        rtcout.println(Logbuf.TRACE, "RTObject_impl.onRateChanged(" + ec_id + ")");
        return ReturnCode_t.RTC_OK;
    }

    /**
     * {@.ja [CORBA interface] RTCを初期化する}
     * {@.en [CORBA interface] Initialize the RTC that realizes 
     * this interface.}
     *
     * <p>
     * {@.ja このオペレーション呼び出しの結果として、
     * ComponentAction::on_initialize コールバック関数が呼ばれる。
     * 
     * 制約 <ul>
     * <li> RTC は Created状態の場合み初期化が行われる。他の状態にいる場合には
     *   ReturnCode_t::PRECONDITION_NOT_MET が返され呼び出しは失敗する。</li>
     * <li> このオペレーションは RTC のミドルウエアから呼ばれることを
     * 想定しており、アプリケーション開発者は直接このオペレーションを
     * 呼ぶことは想定されていない。</li></ul>}
     * {@.en The invocation of this operation shall result 
     * in the invocation of the
     * callback ComponentAction::on_initialize.
     *
     * Constraints <ul>
     * <li> An RTC may be initialized only while it is in the Created state. Any
     *   attempt to invoke this operation while in another state shall fail
     *   with ReturnCode_t::PRECONDITION_NOT_MET.</li>
     * <li> Application developers are not expected to call this operation
     *   directly; it exists for use by the RTC infrastructure.</li></ul>}
     * 
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     */
    public ReturnCode_t initialize() {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.initialize()");

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
            rtcout.println(Logbuf.DEBUG, "EC[" + intIdx + "] starting");
            m_ecMine.value[intIdx].start();
        }

        // ret must be RTC_OK
        return ret;
    }

    /**
     * {@.ja [CORBA interface] RTC を終了する。}
     * {@.en [CORBA interface] Finalize the RTC for destruction}
     *
     * <p>
     * {@.ja このオペレーション呼び出しの結果として 
     * ComponentAction.on_finalize() を呼び出す。
     *
     * 制約 <ul>
     * <li> RTC が ExecutionContext に所属している間は終了されない。この場合は、
     *   まず最初に ExecutionContextOperations::remove によって参加を
     *   解除しなければならない。これ以外の場合は、このオペレーション呼び出しは
     *   いかなる場合も ReturnCode_t::PRECONDITION_NOT_ME で失敗する。</li>
     * <li> RTC が Created 状態である場合、終了処理は行われない。
     *   この場合、このオペレーション呼び出しはいかなる場合も
     *   ReturnCode_t::PRECONDITION_NOT_MET で失敗する。</li>
     * <li> このオペレーションはRTCのミドルウエアから呼ばれることを
     *   想定しており、
     *   アプリケーション開発者は直接このオペレーションを呼ぶことは想定
     *   されていない。</li></ul>}
     * {@.en This invocation of this operation shall result 
     * in the invocation of the
     * callback ComponentAction::on_finalize.
     *
     * Constraints <ul>
     * <li> An RTC may not be finalized while it is participating 
     *   in any execution
     *   context. It must first be removed with 
     *   ExecutionContextOperations::remove. Otherwise, this operation
     *   shall fail with ReturnCode_t::PRECONDITION_NOT_MET. </li>
     * <li> An RTC may not be finalized while it is in the Created state. Any 
     *   attempt to invoke this operation while in that state shall fail with 
     *   ReturnCode_t::PRECONDITION_NOT_MET.</li>
     * <li> Application developers are not expected to call 
     *   this operation directly;</li></ul>
     *  it exists for use by the RTC infrastructure.}
     *
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     */
    public ReturnCode_t _finalize()  throws SystemException {

        rtcout.println(Logbuf.TRACE, "RTObject_impl._finalize()");

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
     * {@.ja [CORBA interface] RTC がオーナーである ExecutionContext を
     *        停止させ、そのコンテンツと共に終了させる}
     * {@.en [CORBA interface]top the RTC's execution context(s) and finalize
     *        it along with its contents.}
     *
     * <p>
     * {@.ja この RTC がオーナーであるすべての実行コンテキストを停止する。
     * この RTC が他の実行コンテキストを所有する RTC に属する実行コンテキスト
     * (i.e. 実行コンテキストを所有する RTC はすなわちその実行コンテキストの
     * オーナーである。)に参加している場合、当該 RTC はそれらのコンテキスト上
     * で非活性化されなければならない。
     * RTC が実行中のどの ExecutionContext でも Active 状態ではなくなった後、
     * この RTC とこれに含まれる RTC が終了する。
     * 
     * 制約 <ul>
     * <li> RTC が初期化されていなければ、終了させることはできない。
     *   Created 状態にある RTC に exit() を呼び出した場合、
     *   ReturnCode_t::PRECONDITION_NOT_MET で失敗する。</li></ul>}
     * {@.en Any execution contexts for which the RTC is the owner shall 
     * be stopped. 
     * If the RTC participates in any execution contexts belonging to another
     * RTC that contains it, directly or indirectly (i.e. the containing RTC
     * is the owner of the ExecutionContext), it shall be deactivated in those
     * contexts.
     * After the RTC is no longer Active in any Running execution context, it
     * and any RTCs contained transitively within it shall be finalized.
     *
     * Constraints <ul>
     * <li> An RTC cannot be exited if it has not yet been initialized. Any
     *   attempt to exit an RTC that is in the Created state shall fail with
     *   ReturnCode_t::PRECONDITION_NOT_MET.</li></ul>}
     *
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     */
    public ReturnCode_t exit() throws SystemException {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.exit()");

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
     * {@.ja [CORBA interface] RTC が Alive 状態であるかどうか確認する。}
     * {@.en [CORBA interface] Confirm whether RTC is the alive state}
     *
     * <p>
     * {@.ja RTC が指定した ExecutionContext に対して Alive状態であるかどうかi
     * 確認する。
     * RTC の状態が Active であるか、Inactive であるか、Error であるかは実行中の
     * ExecutionContext に依存する。すなわち、ある ExecutionContext に対しては
     * Active  状態であっても、他の ExecutionContext に対しては Inactive 状態と
     * なる場合もありえる。従って、このオペレーションは指定された
     * ExecutionContext に問い合わせて、この RTC の状態が Active、Inactive、
     * Error の場合には Alive 状態として返す。
     * RTコンポーネントがAliveであるかどうかは、
     * ExecutionContextの状態(Inactive，Active，Error)とは独立している。
     * １つのRTコンポーネントが、複数のExecutionContextにattachされる場合も
     * あるため、
     * ExecutionContextの状態が混在する場合
     * (ExecutionContext1に対してはActive、
     * ExecutionContext2に対してはInactiveなど)
     * があるため。}
     * {@.en A component is alive or not regardless of 
     * the execution context from
     * which it is observed. However, whether or not it is Active, Inactive,
     * or in Error is dependent on the execution context(s) in which it is
     * running. That is, it may be Active in one context but Inactive in
     * another. Therefore, this operation shall report whether this RTC is
     * either Active, Inactive or in Error; which of those states a component
     * is in with respect to a particular context may be queried from the
     * context itself.}
     *
     * @return 
     *   {@.ja Alive 状態確認結果}
     *   {@.en Result of Alive state confirmation}
     */
    public boolean is_alive(ExecutionContext exec_context) throws SystemException {
        rtcout.println(Logbuf.TRACE, "RTObject_impl.is_alive()");

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
     * {@.ja [CORBA interface] 所有する ExecutionContextListを 取得する。}
     * {@.en [CORBA interface] Get ExecutionContextList.}
     *
     * <p>
     * {@.ja この RTC が所有する ExecutionContext のリストを取得する。}
     * {@.en This operation returns a list of all execution contexts owned 
     * by this RTC.}
     *
     * @return 
     *   {@.ja ExecutionContext リスト}
     *   {@.en ExecutionContext List}
     */
    public ExecutionContext[] get_owned_contexts() throws SystemException {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.get_owned_contexts()");

        ExecutionContextListHolder execlist;
        execlist = new ExecutionContextListHolder();
        execlist.value = new ExecutionContext[0];
    
        CORBA_SeqUtil.for_each(m_ecMine, new ec_copy(execlist));
    
        return execlist.value;
     }

    /**
     * {@.ja [CORBA interface] ExecutionContextを取得する。}
     * {@.en [CORBA interface] Get ExecutionContext.}
     *
     * <p>
     * {@.ja 指定したハンドルの ExecutionContext を取得する。
     * ハンドルから ExecutionContext へのマッピングは、特定の RTC インスタンスに
     * 固有である。ハンドルはこの RTC を attach_context した際に取得できる。}
     * {@.en Obtain a reference to the execution context represented 
     * by the given 
     * handle.
     * The mapping from handle to context is specific to a particular RTC 
     * instance. The given handle must have been obtained by a previous call to 
     * attach_context on this RTC.}
     *
     * @param ec_id 
     *   {@.ja ExecutionContextのID}
     *   {@.en ExecutionContext handle}
     *
     * @return 
     *   {@.ja ExecutionContext}
     *   {@.en ExecutionContext}
     */
    public ExecutionContext get_context(int ec_id) {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.get_context(" + ec_id + ")");

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
     * {@.ja [CORBA interface] 参加している ExecutionContextList を取得する。}
     * {@.en [CORBA interface] Get participating ExecutionContextList.}
     *
     * <p>
     * {@.ja この RTC が参加している ExecutionContext のリストを取得する。}
     * {@.en This operation returns a list of all execution contexts in
     * which this RTC participates.}
     *
     * @return 
     *   {@.ja ExecutionContext リスト}
     *   {@.en ExecutionContext List}
     *
     */
    public ExecutionContext[] get_participating_contexts() throws SystemException {
        rtcout.println(Logbuf.TRACE, "RTObject_impl.get_participating_contexts()");

        ExecutionContextListHolder execlist;
        execlist = new ExecutionContextListHolder();
        execlist.value = new ExecutionContext[0];

        CORBA_SeqUtil.for_each(m_ecOther, new ec_copy(execlist));

        return execlist.value;
    }


    /**
     * {@.ja [CORBA interface] ExecutionContext のハンドルを返す。}
     * {@.en [CORBA interface] Return a handle of a ExecutionContext}
     *
     * <p>
     * {@.ja 与えられた実行コンテキストに関連付けられたハンドルを返す。}
     * {@.en This operation returns a handle that is associated with the given
     * execution context.}
     *
     * @param cxt 
     *   {@.ja ExecutionContext}
     *   {@.en ExecutionContext}
     *
     * @return 
     *   {@.ja ExecutionContextHandle_t}
     *   {@.en ExecutionContextHandle_t}
     *
     */
    public int get_context_handle(ExecutionContext cxt) throws SystemException {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.get_context_handle()");

        int num;
        num = CORBA_SeqUtil.find(m_ecMine, new ec_find(cxt));
        if(num != -1){
            return num;
        }
        num = CORBA_SeqUtil.find(m_ecOther, new ec_find(cxt));
        if(num != -1){
            return (ECOTHER_OFFSET+num);
        }
        return -1;
    }

    /**
     * {@.ja ECをバインドする。}
     * {@.en Binds ExecutionContext.}
     * 
     * @param exec_context
     *   {@.ja ExecutionContext}
     *   {@.en ExecutionContext}
     *
     * @return 
     *   {@.ja ID(失敗した場合は-1を返す。)}
     *   {@.en ID(Returns -1,When narrow fails.)}
     *
     */
    public int bindContext(ExecutionContext exec_context)
    {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.bindContext()");

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
                onAttachExecutionContext(i);
                return i;
            }
        }

        // no space in the list, push back ec to the last.
        CORBA_SeqUtil.push_back(m_ecMine, 
                                (ExecutionContextService)ecs._duplicate());
        int ec_id = (m_ecMine.value.length - 1);
        onDetachExecutionContext(ec_id);
        return ec_id;
    }


  //============================================================
  // RTC::RTObject
  //============================================================
  
    /**
     * {@.ja [RTObject CORBA interface] コンポーネントプロファイルを取得する。}
     * {@.en [RTObject CORBA interface] Get RTC's profile}
     *
     * <p>
     * {@.ja 当該コンポーネントのプロファイル情報を返す。}
     * {@.en This operation returns the ComponentProfile of the RTC.}
     * </p>
     *
     * @return 
     *   {@.ja コンポーネントプロファイル}
     *   {@.en ComponentProfile}
     *
     */
    public ComponentProfile get_component_profile() {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.get_component_profile()");

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
     * {@.ja [RTObject CORBA interface] ポートを取得する。}
     * {@.en [RTObject CORBA interface] Get Ports}
     *
     * <p>
     * {@.ja 当該コンポーネントが保有するポートの参照を返す。}
     * {@.en This operation returns the reference of ports held by RTC.}
     *
     * @return 
     *   {@.ja ポート参照情報}
     *   {@.en PortServiceList}
     */
    public PortService[] get_ports() {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.get_ports()");

        try {
            return m_portAdmin.getPortServiceList().value;
        } catch(Exception ex) {
            ; // This operation throws no exception.
        }
        return null;
    }

    /**
     * {@.ja [CORBA interface] ExecutionContextをattachする。}
     * {@.en [CORBA interface] Attach ExecutionContext}
     *
     * <p>
     * {@.ja 指定した ExecutionContext にこの RTC を所属させる。
     * この RTC と関連する ExecutionContext のハンドルを返す。
     * このオペレーションは、ExecutionContextOperations.add_component が
     * 呼ばれた際に呼び出される。
     * 返されたハンドルは他のクライアントで使用することを想定していない。}
     * {@.en Inform this RTC that it is participating 
     * in the given execution context. 
     * Return a handle that represents the association of this RTC with the 
     * context.
     * This operation is intended to be invoked by 
     * ExecutionContextOperations::add_component. It is not intended for use by 
     * other clients.}
     *
     * @param exec_context 
     *   {@.ja attach対象ExecutionContext}
     *   {@.en Participating ExecutionContext}
     *
     * @return 
     *   {@.ja attachされたExecutionContext数}
     *   {@.en ExecutionContext Handle}
     *
     */
    public int attach_context(ExecutionContext exec_context) throws SystemException {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.attach_context()");

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
                int ec_id = (i + ECOTHER_OFFSET);
                onAttachExecutionContext(ec_id);
                return (ec_id);
            }
        }

        // no space in the list, push back ec to the last.
        CORBA_SeqUtil.push_back(m_ecOther, 
                                (ExecutionContextService)ecs._duplicate());
    
        int ec_id =  (m_ecOther.value.length - 1) + ECOTHER_OFFSET;
        onAttachExecutionContext(ec_id);
        return ec_id;

    }
    /**
     * {@.ja [CORBA interface] ExecutionContextをdetachする。}
     * {@.en [CORBA interface] Detach ExecutionContext.}
     *
     * <p>
     * {@.ja 指定した ExecutionContext からこの RTC の所属を解除する。
     * このオペレーションは、ExecutionContextOperations::remove が呼ば
     * れた際に呼び出される。返されたハンドルは他のクライアントで使用することを
     * 想定していない。
     * 
     * 制約<ul>
     * <li> 指定された ExecutionContext に RTC がすでに所属していない場合には、
     *   ReturnCode_t.PRECONDITION_NOT_MET が返される。</li>
     * <li> 指定された ExecutionContext にたしいて対して RTC が
     *   Active 状態である場合には、 
     *   ReturnCode_t.PRECONDITION_NOT_MET が返される。</li></ul>}
     * {@.en Inform this RTC that it is no longer participating 
     * in the given execution context.
     * This operation is intended to be invoked by 
     * ExecutionContextOperations::remove. It is not intended for use 
     * by other clients.
     * Constraints<ul>
     * <li> This operation may not be invoked if this RTC is not already 
     *   participating in the execution context. Such a call shall fail with 
     *   ReturnCode_t::PRECONDITION_NOT_MET.</li>
     * <li> This operation may not be invoked if this RTC is Active 
     *   in the indicated
     *   execution context. Otherwise, it shall fail with 
     *   ReturnCode_t::PRECONDITION_NOT_MET.</li></ul>}
     *
     * @param ec_id 
     *   {@.ja detach対象ExecutionContextのID}
     *   {@.en Detaching ExecutionContext Handle}
     *
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     */
    public ReturnCode_t detach_context(int ec_id) throws SystemException {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.detach_context(" + ec_id + ")");

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
     * {@.ja [ComponentAction CORBA interface] RTC の初期化。}
     * {@.en [ComponentAction CORBA interface] Initialize RTC}
     *
     * <p>
     * {@.ja RTC が初期化され、Alive 状態に遷移する。
     * RTC 固有の初期化処理はここで実行する。
     * このオペレーション呼び出しの結果として onInitialize() コールバック関数が
     * 呼び出される。}
     * {@.en The RTC has been initialized and entered the Alive state.
     * Any RTC-specific initialization logic should be performed here.
     * As a result of this operation, onInitialize() callback function 
     * is called.}
     *
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     *
     */
    public ReturnCode_t on_initialize() {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.on_initialize()");

        ReturnCode_t ret = ReturnCode_t.RTC_ERROR;
        try {
            preOnInitialize(0);
            ret = onInitialize();
            postOnInitialize(0, ret);
            String active_set;
            active_set 
                = m_properties.getProperty("configuration.active_config",
                                            "default");
            if (m_configsets.haveConfig(active_set)) {
                m_configsets.update(active_set);
            }
            else {
                m_configsets.update("default");
            }
        } catch(Exception ex) {
            return ReturnCode_t.RTC_ERROR;
        }
        return ret;
    }

    /**
     * {@.ja [ComponentAction CORBA interface] 当該コンポーネントの終了時に
     * 呼び出される。}
     * {@.en [ComponentAction CORBA interface] Finalize RTC}
     *
     * <p>
     * {@.ja RTC が破棄される。
     * RTC 固有の終了処理はここで実行する。
     * このオペレーション呼び出しの結果として onFinalize() コールバック関数が
     * 呼び出される。}
     * {@.en The RTC is being destroyed.
     * Any final RTC-specific tear-down logic should be performed here.
     * As a result of this operation, onFinalize() callback function is called.}
     *
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     */
    public ReturnCode_t on_finalize() {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.on_finalize()");

        ReturnCode_t ret = ReturnCode_t.RTC_ERROR;
        try {
            preOnFinalize(0);
            ret = onFinalize();
            postOnFinalize(0, ret);
        } catch(Exception ex) {
            return ReturnCode_t.RTC_ERROR;
        }
        return ret;
    }

    /**
     * {@.ja [ComponentAction CORBA interface] 当該コンポーネントの
     * attachされているExecutionContextの実行開始時に呼び出される。}
     * {@.en [ComponentAction CORBA interface] Startup RTC}
     *
     * <p>
     * {@.ja RTC が所属する ExecutionContext が Stopped 状態から 
     * Running 状態へ遷移した場合に呼び出される。
     * このオペレーション呼び出しの結果として onStartup() コールバック関数が
     * 呼び出される。}
     * {@.en The given execution context, in which the RTC is participating, 
     * has transitioned from Stopped to Running.
     * As a result of this operation, onStartup() callback function is called.}
     *
     *
     * @param ec_id 
     *   {@.ja 対象ExecutionContextのID}
     *   {@.en ID of ExecutionContext that transited to the state}
     *
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     */
    public ReturnCode_t on_startup(int ec_id) {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.on_startup(" + ec_id + ")");

        ReturnCode_t ret = ReturnCode_t.RTC_ERROR;
        try {
            preOnStartup(ec_id);
            ret = onStartup(ec_id);
            postOnStartup(ec_id, ret);
        } catch (Exception ex) {
            return ReturnCode_t.RTC_ERROR;
        }
        return ret;
    }

    /**
     * {@.ja [ComponentAction CORBA interface] 当該コンポーネントのattachされ
     * ているExecutionContextの実行終了時に呼び出される。}
     * {@.en [ComponentAction CORBA interface] Shutdown RTC}
     *
     * <p>
     * {@.ja RTC が所属する ExecutionContext が Running 状態から 
     * Stopped 状態へ遷移した場合に呼び出される。
     * このオペレーション呼び出しの結果として onShutdown() コールバック関数が
     * 呼び出される。}
     * {@.en The given execution context, in which the RTC is participating, 
     * has transitioned from Running to Stopped.
     * As a result of this operation, onShutdown() callback function is called.}
     *
     * @param ec_id 
     *   {@.ja 対象ExecutionContextのID}
     *   {@.en ID of ExecutionContext that transited to the state}
     *
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     */
    public ReturnCode_t on_shutdown(int ec_id) {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.on_shutdown(" + ec_id + ")");

        ReturnCode_t ret = ReturnCode_t.RTC_ERROR;
        try {
            preOnShutdown(ec_id);
            ret = onShutdown(ec_id);
            postOnShutdown(ec_id, ret);
        } catch(Exception ex) {
            return ReturnCode_t.RTC_ERROR;
        }
        return ret;
    }

    /**
     * {@.ja [ComponentAction CORBA interface] 当該コンポーネントの
     * Activate時に呼び出される。}
     * {@.en [ComponentAction CORBA interface] Activate RTC}
     *
     * <p>
     * {@.ja 所属する ExecutionContext から RTC が活性化された際に呼び出される。
     * このオペレーション呼び出しの結果として onActivated() コールバック関数が
     * 呼び出される。}
     * {@.en The RTC has been activated in the given execution context.
     * As a result of this operation, onActivated() callback function 
     * is called.}
     *
     * @param ec_id 
     *   {@.ja 対象ExecutionContextのID}
     *   {@.en ID of activation ExecutionContext}
     *
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     */
    public ReturnCode_t on_activated(int ec_id) {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.on_activated(" + ec_id + ")");

        ReturnCode_t ret = ReturnCode_t.RTC_ERROR;
        try {
            preOnActivated(ec_id);
            m_configsets.update();
            ret = onActivated(ec_id);
            m_portAdmin.activatePorts();
            postOnActivated(ec_id, ret);
        } catch(Exception ex) {
            return ReturnCode_t.RTC_ERROR;
        }
        return ret;
    }
    
    /**
     * {@.ja [ComponentAction CORBA interface] 当該コンポーネントの
     * Deactivate時に呼び出される。}
     * {@.en [ComponentAction CORBA interface] Deactivate RTC}
     *
     * <p>
     * {@.ja 所属する ExecutionContext から RTC が非活性化された際に
     * 呼び出される。
     * このオペレーション呼び出しの結果として onDeactivated() コールバック関数が
     * 呼び出される。}
     * {@.en The RTC has been deactivated in the given execution context.
     * As a result of this operation, onDeactivated() callback function
     * is called.}
     *
     * @param ec_id 
     *   {@.ja 対象ExecutionContextのID}
     *   {@.en ID of deactivation ExecutionContext}
     *
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     */
    public ReturnCode_t on_deactivated(int ec_id) {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.on_deactivated(" + ec_id + ")");

        ReturnCode_t ret = ReturnCode_t.RTC_ERROR;
        try {
            preOnDeactivated(ec_id);
            m_portAdmin.deactivatePorts();
            ret = onDeactivated(ec_id);
            postOnDeactivated(ec_id, ret);
        } catch(Exception ex) {
            return ReturnCode_t.RTC_ERROR;
        }
        return ret;
    }

    /**
     * {@.ja [ComponentAction CORBA interface] RTC のエラー状態への遷移。}
     * {@.en [ComponentAction CORBA interface] Transition to Error State}
     *
     * <p>
     * {@.ja RTC が所属する ExecutionContext が Active 状態から 
     * Error 状態へ遷移した場合に呼び出される。
     * このオペレーションは RTC が Error 状態に遷移した際に一度だけ呼び
     * 出される。
     * このオペレーション呼び出しの結果として onAborting() コールバック関数が
     * 呼び出される。}
     * {@.en The RTC is transitioning from the Active state 
     * to the Error state in some execution context.
     * This callback is invoked only a single time for time that the RTC 
     * transitions into the Error state from another state. This behavior is in 
     * contrast to that of on_error.
     * As a result of this operation, onAborting() callback function 
     * is invoked.}
     *
     * @param ec_id 
     *   {@.ja 対象ExecutionContextのID}
     *   {@.en ID of ExecutionContext that transited to the state}
     *
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     */
    public ReturnCode_t on_aborting(int ec_id) {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.on_aborting(" + ec_id + ")");

        ReturnCode_t ret = ReturnCode_t.RTC_ERROR;
        try {
            preOnAborting(ec_id);
            ret = onAborting(ec_id);
            postOnAborting(ec_id, ret);
        } catch(Exception ex) {
            return ReturnCode_t.RTC_ERROR;
        }
        return ret;
    }
    
    /**
     * {@.ja [ComponentAction CORBA interface] RTC のエラー処理}
     * {@.en [ComponentAction CORBA interface] Error Processing of RTC}
     *
     * <p>
     * {@.ja RTC がエラー状態にいる際に呼び出される。
     * RTC がエラー状態の場合に、対象となる ExecutionContext のExecutionKind に
     * 応じたタイミングで呼び出される。例えば、<ul>
     * <li> ExecutionKind が PERIODIC の場合、本オペレーションは
     *   DataFlowComponentAction::on_execute と on_state_update の替わりに、
     *   設定された順番、設定された周期で呼び出される。
     * <li> ExecutionKind が EVENT_DRIVEN の場合、本オペレーションは
     *   FsmParticipantAction::on_action が呼ばれた際に、替わりに呼び出される。
     * </ul>
     * このオペレーション呼び出しの結果として onError() コールバック関数が呼び出
     * される。}
     * {@.en The RTC remains in the Error state.
     * If the RTC is in the Error state relative to some execution context when
     * it would otherwise be invoked from that context (according to the 
     * context’s ExecutionKind), this callback shall be invoked instead. 
     * For example,<ul>
     * <li> If the ExecutionKind is PERIODIC, this operation shall 
     *   be invoked in 
     *   sorted order at the rate of the context instead of 
     *   DataFlowComponentAction::on_execute and on_state_update.
     * <li> If the ExecutionKind is EVENT_DRIVEN, 
     *   this operation shall be invoked 
     *   whenever FsmParticipantAction::on_action would otherwise have been 
     *   invoked.
     * </ul>
     * As a result of this operation, onError() callback function is invoked.}
     *
     * @param ec_id 
     *   {@.ja 対象ExecutionContextのID}
     *   {@.en ID of target ExecutionContext}
     *
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     */
    public ReturnCode_t on_error(int ec_id) {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.on_error(" + ec_id + ")");

        ReturnCode_t ret = ReturnCode_t.RTC_ERROR;
        try {
            preOnError(ec_id);
            ret = onError(ec_id);
            m_configsets.update();
            postOnError(ec_id, ret);
        } catch(Exception ex) {
            return ReturnCode_t.RTC_ERROR;
        }
        return ret;
    }

    /**
     * {@.ja [ComponentAction CORBA interface] RTC のリセット。}
     * {@.en [ComponentAction CORBA interface] Resetting RTC}
     *
     * <p>
     * {@.ja Error 状態にある RTC のリカバリ処理を実行し、
     * Inactive 状態に復帰させる場合に呼び出される。
     * RTC のリカバリ処理が成功した場合は Inactive 状態に復帰するが、それ以外の
     * 場合には Error 状態に留まる。
     * このオペレーション呼び出しの結果として onReset() コールバック関数が呼び
     * 出される。}
     * {@.en The RTC is in the Error state. An attempt is being made 
     * to recover it such
     * that it can return to the Inactive state.
     * If the RTC was successfully recovered and can safely return to the
     * Inactive state, this method shall complete with ReturnCode_t::OK. Any
     * other result shall indicate that the RTC should remain 
     * in the Error state.
     * As a result of this operation, onReset() callback function is invoked.}
     *
     *
     * @param ec_id 
     *   {@.ja 対象ExecutionContextのID}
     *   {@.en ID of target ExecutionContext for the reset}
     *
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     */
    public ReturnCode_t on_reset(int ec_id) {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.on_reset(" + ec_id + ")");

        ReturnCode_t ret = ReturnCode_t.RTC_ERROR;
        try {
            preOnReset(ec_id);
            ret = onReset(ec_id);
            postOnReset(ec_id, ret);
        } catch(Exception ex) {
            return ReturnCode_t.RTC_ERROR;
        }
        return ret;
    }

    /**
     * {@.ja [DataFlowComponentAction CORBA interface] RTC の定常処理(第一周期)}
     * {@.en [DataFlowComponentAction CORBA interface] Primary Periodic 
     *        Operation of RTC}
     * <p>
     * {@.ja 当該コンポーネントがAvtive状態の間、呼び出されます。}
     * {@.en The component is called during the state of Avtive. }
     * </p>
     * @param ec_id 
     *   {@.ja 対象ExecutionContextのID}
     *   {@.en ID of ExecutionContext}
     * @return 
     *   {@.ja 実行結果}
     *   {@.en Execution result}
     */
    public ReturnCode_t on_execute(int ec_id) {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.on_execute(" + ec_id + ")");

        ReturnCode_t ret = ReturnCode_t.RTC_ERROR;
        try {
            preOnExecute(ec_id);
            if(m_readAll){
                readAll();
            }
            ret = onExecute(ec_id);
            if(m_writeAll){
                writeAll();
            }
            postOnExecute(ec_id, ret);
        } catch(Exception ex) {
            return ReturnCode_t.RTC_ERROR;
        }
        return ret;
    }

    /**
     * {@.ja [DataFlowComponentAction CORBA interface] 
     * RTC の定常処理(第二周期)。}
     * {@.en [DataFlowComponentAction CORBA interface] Secondary Periodic 
     *        Operation of RTC}
     *
     * <p>
     * {@.ja 以下の状態が保持されている場合に、
     * 設定された周期で定期的に呼び出される。
     * <ul>
     * <li> RTC は Alive 状態である。
     * <li> 指定された ExecutionContext が Running 状態である。
     * </ul>
     * 本オペレーションは、Two-Pass Execution の第二周期で実行される。
     * このオペレーション呼び出しの結果として onStateUpdate() コールバック関数が
     * 呼び出される。
     *
     * 制約<ul>
     * <li> 指定された ExecutionContext の ExecutionKind は、 
     *   PERIODIC でなければならない</li></ul>}
     * {@.en This operation will be invoked periodically 
     * at the rate of the given
     * execution context as long as the following conditions hold:<ul>
     * <li> The RTC is Active.
     * <li> The given execution context is Running
     * </ul>
     * This callback occurs during the second execution pass.
     * As a result of this operation, onStateUpdate() callback function is
     * invoked.
     *
     * Constraints<ul>
     * <li> The execution context of the given context shall be PERIODIC.</ul>}
     *
     *
     * @param ec_id 
     *   {@.ja 対象ExecutionContextのID}
     *   {@.en ID of target ExecutionContext for Secondary Periodic Operation}
     *
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     */
    public ReturnCode_t on_state_update(int ec_id) {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.on_state_update(" + ec_id + ")");

        ReturnCode_t ret =ReturnCode_t.RTC_ERROR;
        try {
            preOnStateUpdate(ec_id);
            ret = onStateUpdate(ec_id);
            m_configsets.update();
            postOnStateUpdate(ec_id, ret);
        } catch(Exception ex) {
            return ReturnCode_t.RTC_ERROR;
        }
        return ret;
    }

    /**
     * <p>[ComponentAction interface] 
     * 当該コンポーネントがattachされているExecutionContextの実行周期が変更になった時に呼び出されます。</p>
     *
     * 
     * @return 実行結果
     */
    /**
     * {@.ja [DataFlowComponentAction CORBA interface] 実行周期変更通知。}
     * {@.en [DataFlowComponentAction CORBA interface] Notify rate changed}
     *
     * <p>
     * {@.ja 本オペレーションは、ExecutionContext の実行周期が変更されたことを
     * 通知する際に呼び出される。
     * このオペレーション呼び出しの結果として onRateChanged() コールバック関数が
     * 呼び出される。
     *
     * 制約<ul>
     * <li> 指定された ExecutionContext の ExecutionKind は、 
     *      PERIODIC でなければならない</li></ul>}
     * {@.en This operation is a notification that the rate of the 
     * indicated execution context has changed.
     * As a result of this operation, onRateChanged() callback function is 
     * called.
     *
     * Constraints<ul>
     * <li> The execution context of the given context shall be PERIODIC.</ul>}
     *
     *
     * @param ec_id 
     *   {@.ja 対象ExecutionContextのID}
     *   {@.en ID of target ExecutionContext for Operation}
     *
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     */
    public ReturnCode_t on_rate_changed(int ec_id) {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.on_rate_changed(" + ec_id + ")");

        ReturnCode_t ret = ReturnCode_t.RTC_ERROR;
        try {
            preOnRateChanged(ec_id);
            ret = onRateChanged(ec_id);
            postOnRateChanged(ec_id, ret);
        } catch(Exception ex) {
            return ReturnCode_t.RTC_ERROR;
        }
        return ret;
    }

    /**
     * {@.ja [SDO interface] Organization リストの取得。}
     * {@.en [SDO interface] Get Organization list}
     *
     * <p>
     * {@.ja SDOSystemElement は0個もしくはそれ以上の 
     * Organization を所有することが出来る。 
     * SDOSystemElement が1つ以上の Organization を所有している場合
     * には、このオペレーションは所有する Organization のリストを返す。
     * もしOrganizationを一つも所有していないければ空のリストを返す。}
     * {@.en SDOSystemElement can be the owner of zero or more organizations.
     * If the SDOSystemElement owns one or more Organizations, this operation
     * returns the list of Organizations that the SDOSystemElement owns.
     * If it does not own any Organization, it returns empty list.}
     *
     * @return 
     *   {@.ja 所有している Organization リスト}
     *   {@.en Owned Organization List}
     *
     * @exception SDONotExists 
     *   {@.ja ターゲットのSDOが存在しない。(本例外は、CORBA標準
     *         システム例外のOBJECT_NOT_EXISTにマッピングされる)}
     *   {@.en The target SDO does not exist.(This exception 
     *         is mapped to CORBA standard system exception
     *         OBJECT_NOT_EXIST.)}
     * @exception NotAvailable 
     *   {@.ja SDOは存在するが応答がない。}
     *   {@.en The target SDO is reachable but cannot respond.}
     * @exception InternalError 
     *   {@.ja 内部的エラーが発生した。}
     *   {@.en The target SDO cannot execute the operation
     *         completely due to some internal error.}
     */
    public Organization[] get_owned_organizations() throws NotAvailable {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.get_owned_organizations()");

        try {
            return m_sdoOwnedOrganizations.value.clone();
        } catch(Exception ex) {
            throw new NotAvailable();
        }
    }

    /**
     * {@.ja [SDO interface] SDO ID の取得。}
     * {@.en [SDO interface] Get the SDO ID}
     *
     * <p>
     * {@.ja SDO ID を返すオペレーション。
     * このオペレーションは以下の型の例外を発生させる。}
     * {@.en This operation returns id of the SDO.
     * This operation throws SDOException with one of the following types.}
     * 
     * @return
     *   {@.ja リソースデータモデルで定義されている SDO の ID}
     *   {@.en id of the SDO defined in the resource data model.}
     * 
     * @exception SDONotExists 
     *   {@.ja ターゲットのSDOが存在しない。(本例外は、CORBA標準
     *         システム例外のOBJECT_NOT_EXISTにマッピングされる)}
     *   {@.en The target SDO does not exist.(This exception 
     *         is mapped to CORBA standard system exception
     *         OBJECT_NOT_EXIST.)}
     * @exception NotAvailable 
     *   {@.ja SDOは存在するが応答がない。}
     *   {@.en The target SDO is reachable but cannot respond.}
     * @exception InternalError 
     *   {@.ja 内部的エラーが発生した。}
     *   {@.en The target SDO cannot execute the operation
     *         completely due to some internal error.}
     */
    public String get_sdo_id() throws NotAvailable, InternalError {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.get_sdo_id()");

        try {
            String sdo_id = m_profile.instance_name;
            return sdo_id;
        } catch(Exception ex) {
            throw new InternalError("get_sdo_id()");
      }
    }
    
    /**
     * {@.ja [SDO interface] SDO タイプの取得。}
     * {@.en [SDO interface] Get SDO type}
     * 
     * <p>
     * {@.ja SDO Type を返すオペレーション。
     * このオペレーションは以下の型の例外を発生させる。}
     * {@.en This operation returns sdoType of the SDO.
     * This operation throws SDOException with one of the following types.}
     *
     * @return    
     *   {@.ja リソースデータモデルで定義されている SDO の Type}
     *   {@.en Type of the SDO defined in the resource data model.}
     *
     * @exception SDONotExists 
     *   {@.ja ターゲットのSDOが存在しない。(本例外は、CORBA標準
     *         システム例外のOBJECT_NOT_EXISTにマッピングされる)}
     *   {@.en The target SDO does not exist.(This exception 
     *         is mapped to CORBA standard system exception
     *          OBJECT_NOT_EXIST.)}
     * @exception NotAvailable 
     *   {@.ja SDOは存在するが応答がない。}
     *   {@.en The target SDO is reachable but cannot respond.}
     * @exception InternalError 
     *   {@.ja 内部的エラーが発生した。}
     *   {@.en The target SDO cannot execute the operation
     *         completely due to some internal error.}
     */
    public String get_sdo_type() throws NotAvailable, InternalError {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.get_sdo_type()");

        try {
            String sdo_type = m_profile.description;
            return sdo_type;
        } catch (Exception ex) {
            throw new InternalError("get_sdo_type()");
        }
    }

    /**
     * {@.ja [SDO interface] SDO DeviceProfile リストの取得。}
     * {@.en [SDO interface] Get SDO DeviceProfile list}
     *
     * <p>
     * {@.ja SDO の DeviceProfile を返すオペレーション。 
     * SDO がハードウエアデバイス
     * に関連付けられていない場合には、空の DeviceProfile が返される。
     * このオペレーションは以下の型の例外を発生させる。}
     * {@.en This operation returns the DeviceProfile of the SDO. 
     * If the SDO does not
     * represent any hardware device, then a DeviceProfile with empty values
     * are returned.
     * This operation throws SDOException with one of the following types.}
     *
     * @return    
     *   {@.ja SDO DeviceProfile}
     *   {@.en The DeviceProfile of the SDO.}
     *
     * @exception SDONotExists 
     *   {@.ja ターゲットのSDOが存在しない。(本例外は、CORBA標準
     *         システム例外のOBJECT_NOT_EXISTにマッピングされる)}
     *   {@.en The target SDO does not exist.(This exception 
     *         is mapped to CORBA standard system exception
     *         OBJECT_NOT_EXIST.)}
     * @exception NotAvailable 
     *   {@.ja SDOは存在するが応答がない。}
     *   {@.en The target SDO is reachable but cannot respond.}
     * @exception InternalError 
     *   {@.ja 内部的エラーが発生した。}
     *   {@.en The target SDO cannot execute the operation
     *         completely due to some internal error.}
     */
    public DeviceProfile get_device_profile() throws NotAvailable, InternalError {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.get_device_profile()");

        try {
            DeviceProfile dprofile = m_pSdoConfigImpl.getDeviceProfile();
            return dprofile;
        } catch(Exception ex) {
            throw new InternalError("get_device_profile()");
        }
    }
    
    /**
     * {@.ja [SDO interface] SDO ServiceProfile の取得。}
     * {@.en [SDO interface] Get SDO ServiceProfile}
     *
     * <p>
     * {@.ja SDO が所有している Service の ServiceProfile を返すオペレーション。
     * SDO がサービスを一つも所有していない場合には、空のリストを返す。
     * このオペレーションは以下の型の例外を発生させる。}
     * {@.en This operation returns a list of ServiceProfiles that the SDO has.
     * If the SDO does not provide any service, then an empty list is returned.
     * This operation throws SDOException with one of the following types.}
     * 
     * @return    
     *   {@.ja SDO が提供する全ての Service の ServiceProfile。}
     *   {@.en List of ServiceProfiles of all the services the SDO is
     *            providing.}
     * 
     * @exception SDONotExists 
     *   {@.ja ターゲットのSDOが存在しない。(本例外は、CORBA標準
     *         システム例外のOBJECT_NOT_EXISTにマッピングされる)}
     *   {@.en The target SDO does not exist.(This exception 
     *         is mapped to CORBA standard system exception
     *         OBJECT_NOT_EXIST.)}
     * @exception NotAvailable 
     *   {@.ja SDOは存在するが応答がない。}
     *   {@.en The target SDO is reachable but cannot respond.}
     * @exception InternalError 
     *   {@.ja 内部的エラーが発生した。}
     *   {@.en The target SDO cannot execute the operation
     *         completely due to some internal error.}
     */
    public ServiceProfile[] get_service_profiles() throws NotAvailable, InternalError {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.get_service_profiles()");

        try {
            return m_pSdoConfigImpl.getServiceProfiles().value;
        } catch(Exception ex) {
            throw new InternalError("get_service_profiles()");
        }
    }
    
    /**
     * {@.ja [SDO interface] 特定のServiceProfileの取得。}
     * {@.en [SDO interface] Get specified ServiceProfile}
     *
     * <p>
     * {@.ja 引数 "id" で指定された名前のサービスの ServiceProfile を返す。}
     * {@.en This operation returns the ServiceProfile that is specified by the
     * argument "id."}
     * 
     * @param     id 
     *   {@.ja SDO Service の ServiceProfile に関連付けられた識別子。}
     *   {@.en The identifier referring to one of the ServiceProfiles.}
     * 
     * @return    
     *   {@.ja 指定された SDO Service の ServiceProfile。}
     *   {@.en The profile of the specified service.}
     * 
     * @exception InvalidParameter 
     *   {@.ja "id" で指定した ServiceProfile が存在しない。
     *         "id" が null。}
     *   {@.en The ServiceProfile that is specified by 
     *         the argument 'id' does not exist or if 'id'
     *         is 'null.'}
     * @exception SDONotExists 
     *   {@.ja ターゲットのSDOが存在しない。(本例外は、CORBA標準
     *         システム例外のOBJECT_NOT_EXISTにマッピングされる)}
     *   {@.en The target SDO does not exist.(This exception 
     *         is mapped to CORBA standard system exception
     *         OBJECT_NOT_EXIST.)}
     * @exception NotAvailable 
     *   {@.ja SDOは存在するが応答がない。}
     *   {@.en The target SDO is reachable but cannot respond.}
     * @exception InternalError 
     *   {@.ja 内部的エラーが発生した。}
     *   {@.en The target SDO cannot execute the operation
     *          completely due to some internal error.}
     */
    public ServiceProfile get_service_profile(String id) throws InvalidParameter, NotAvailable, InternalError {


        rtcout.println(Logbuf.TRACE, "RTObject_impl.get_service_profile(" + id + ")");

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
     * {@.ja [SDO interface] 指定された SDO Service の取得。}
     * {@.en [SDO interface] Get specified SDO Service's reference}
     *
     * <p>
     * {@.ja このオペレーションは引数 "id" で指定された名前によって区別される
     * SDO の Service へのオブジェクト参照を返す。 SDO により提供される
     * Service はそれぞれ一意の識別子により区別される。}
     * {@.en This operation returns an object implementing an SDO's service that
     * is identified by the identifier specified as an argument. Different
     * services provided by an SDO are distinguished with different
     * identifiers. See OMG SDO specification Section 2.2.8, "ServiceProfile,"
     * on page 2-12 for more details.}
     *
     *
     * @param id 
     *   {@.ja SDO Service に関連付けられた識別子。}
     *   {@.en The identifier referring to one of the SDO Service}
     *
     * @return 
     *   {@.ja 要求された SDO Service への参照。}
     *   {@.en The reference requested to SDO Service.}
     *
     * 
     * @exception InvalidParameter 
     *   {@.ja "id" で指定した ServiceProfile が存在しない。
     *         "id" が null。}
     *   {@.en Argument “id” is null, or if the 
     *         ServiceProfile that is specified by argument
     *         "id" does not exist.}
     * @exception SDONotExists 
     *   {@.ja ターゲットのSDOが存在しない。(本例外は、CORBA標準
     *         システム例外のOBJECT_NOT_EXISTにマッピングされる)}
     *   {@.en The target SDO does not exist.(This exception 
     *         is mapped to CORBA standard system exception
     *         OBJECT_NOT_EXIST.)}
     * @exception NotAvailable 
     *   {@.ja SDOは存在するが応答がない。}
     *   {@.en The target SDO is reachable but cannot respond.}
     * @exception InternalError 
     *   {@.ja 内部的エラーが発生した。}
     *   {@.en The target SDO cannot execute the operation
     *         completely due to some internal error.}
     */
    public SDOService get_sdo_service(String id) throws InvalidParameter, NotAvailable, InternalError {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.get_sdo_service(" + id + ")");

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
     * {@.ja [SDO interface] Configuration オブジェクトの取得。}
     * {@.en [SDO interface] Get Configuration object}
     *
     * <p>
     * {@.ja このオペレーションは Configuration interface への参照を返す。
     * Configuration interface は各 SDO を管理するためのインターフェースの
     * ひとつである。このインターフェースは DeviceProfile, ServiceProfile,
     * Organization で定義された SDO の属性値を設定するために使用される。
     * Configuration インターフェースの詳細については、OMG SDO specification
     * の 2.3.5節, p.2-24 を参照のこと。}
     * {@.en This operation returns an object implementing the Configuration
     * interface. The Configuration interface is one of the interfaces that
     * each SDO maintains. The interface is used to configure the attributes
     * defined in DeviceProfile, ServiceProfile, and Organization.
     * See OMG SDO specification Section 2.3.5, "Configuration Interface,"
     * on page 2-24 for more details about the Configuration interface.}
     *
     * @return 
     *   {@.ja SDO の Configuration インターフェースへの参照}
     *   {@.en The Configuration interface of an SDO.}
     *
     * @exception InterfaceNotImplemented 
     *   {@.ja SDOはConfigurationインターフェースを
     *                                    持たない。}
     *   {@.en The target SDO has no Configuration
     *                                    interface.}
     * @exception SDONotExists 
     *   {@.ja ターゲットのSDOが存在しない。(本例外は、CORBA標準
     *         システム例外のOBJECT_NOT_EXISTにマッピングされる)}
     *   {@.en The target SDO does not exist.(This exception 
     *                         is mapped to CORBA standard system exception
     *                         OBJECT_NOT_EXIST.)}
     * @exception NotAvailable 
     *   {@.ja SDOは存在するが応答がない。}
     *   {@.en The target SDO is reachable but cannot respond.}
     * @exception InternalError 
     *   {@.ja 内部的エラーが発生した。}
     *   {@.en The target SDO cannot execute the operation
     *                          completely due to some internal error.}
     */
    public Configuration get_configuration() throws InterfaceNotImplemented, NotAvailable, InternalError {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.get_configuration()");

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
     * {@.ja [SDO interface] Monitoring オブジェクトの取得。}
     * {@.en [SDO interface] Get Monitoring object}
     *
     * <p>
     * {@.ja このオペレーションは Monitoring interface への参照を返す。
     * Monitoring interface は SDO が管理するインターフェースの一つである。
     * このインターフェースは SDO のプロパティをモニタリングするために
     * 使用される。
     * Monitoring interface の詳細については OMG SDO specification の
     * 2.3.7節 "Monitoring Interface" p.2-35 を参照のこと。}
     * {@.en This operation returns an object implementing 
     * the Monitoring interface.
     * The Monitoring interface is one of the interfaces that each SDO
     * maintains. The interface is used to monitor the properties of an SDO.
     * See OMG SDO specification Section 2.3.7, "Monitoring Interface," on
     * page 2-35 for more details about the Monitoring interface.}
     *
     * @return 
     *   {@.ja SDO の Monitoring interface への参照}
     *   {@.en The Monitoring interface of an SDO.}
     *
     * @exception InterfaceNotImplemented 
     *   {@.ja SDOはConfigurationインターフェースを
     *                                    持たない。}
     *   {@.en The target SDO has no Configuration
     *                                    interface.}
     * @exception SDONotExists 
     *   {@.ja ターゲットのSDOが存在しない。(本例外は、CORBA標準
     *         システム例外のOBJECT_NOT_EXISTにマッピングされる)}
     *   {@.en The target SDO does not exist.(This exception 
     *                         is mapped to CORBA standard system exception
     *                         OBJECT_NOT_EXIST.)}
     * @exception NotAvailable 
     *   {@.ja SDOは存在するが応答がない。}
     *   {@.en The target SDO is reachable but cannot respond.}
     * @exception InternalError 
     *   {@.ja 内部的エラーが発生した。}
     *   {@.en The target SDO cannot execute the operation
     *                          completely due to some internal error.}
     */
    public Monitoring get_monitoring() throws InterfaceNotImplemented, NotAvailable, InternalError {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.get_monitoring()");

        throw new InterfaceNotImplemented();
    }

    /**
     * {@.ja [SDO interface] Organization リストの取得。}
     * {@.en [SDO interface] Get Organization list}
     *
     * <p>
     * {@.ja SDO は0個以上の Organization (組織)に所属することができる。 
     * もし SDO が1個以上の Organization に所属している場合、
     * このオペレーションは所属する
     * Organization のリストを返す。SDO が どの Organization にも所属していない
     * 場合には、空のリストが返される。}
     * {@.en An SDO belongs to zero or more organizations. 
     * If the SDO belongs to one
     * or more organizations, this operation returns the list of organizations
     * that the SDO belongs to. An empty list is returned if the SDO does not
     * belong to any Organizations.}
     *
     * @return 
     *   {@.ja SDO が所属する Organization のリスト。}
     *   {@.en The list of Organizations that the SDO belong to.}
     *
     * @exception SDONotExists 
     *   {@.ja ターゲットのSDOが存在しない。(本例外は、CORBA標準
     *         システム例外のOBJECT_NOT_EXISTにマッピングされる)}
     *   {@.en  The target SDO does not exist.(This exception 
     *                         is mapped to CORBA standard system exception
     *                         OBJECT_NOT_EXIST.)}
     * @exception NotAvailable 
     *   {@.ja SDOは存在するが応答がない。}
     *   {@.en The target SDO is reachable but cannot respond.}
     * @exception InternalError 
     *   {@.ja 内部的エラーが発生した。}
     *   {@.en The target SDO cannot execute the operation
     *                          completely due to some internal error.}
     */
    public Organization[] get_organizations() throws NotAvailable, InternalError {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.get_organizations()");

        try {
            OrganizationListHolder orgList = new OrganizationListHolder(m_pSdoConfigImpl.getOrganizations().value);
            return orgList.value;
        } catch (Exception ex) {
            throw new InternalError("get_organizations()");
        }
    }

    /**
     * {@.ja [SDO interface] SDO Status リストの取得。}
     * {@.en [SDO interface] Get SDO Status list}
     *
     * <p>
     * {@.ja このオペレーションは SDO のステータスを表す NVList を返す。}
     * {@.en This operation returns an NVlist describing the status of an SDO.}
     *
     * @return 
     *   {@.ja SDO のステータス。}
     *   {@.en The actual status of an SDO.}
     *
     * @exception SDONotExists 
     *   {@.ja ターゲットのSDOが存在しない。(本例外は、CORBA標準
     *         システム例外のOBJECT_NOT_EXISTにマッピングされる)}
     *   {@.en The target SDO does not exist.(This exception 
     *                         is mapped to CORBA standard system exception
     *                         OBJECT_NOT_EXIST.)}
     * @exception NotAvailable 
     *   {@.ja SDOは存在するが応答がない。}
     *   {@.en The target SDO is reachable but cannot respond.}
     * @exception InternalError 
     *   {@.ja 内部的エラーが発生した。}
     *   {@.en The target SDO cannot execute the operation
     *                          completely due to some internal error.}
     */
    public NameValue[] get_status_list() throws NotAvailable, InternalError {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.get_status_list()");

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
     * {@.ja [SDO interface] SDO Status の取得。}
     * {@.en [SDO interface] Get SDO Status}
     *
     * <p>
     * {@.ja このオペレーションは 引数で指定された 
     * SDO のステータスを返す}
     * {@.en This operation returns the value of 
     * the specified status parameter.}
     * 
     * @param name 
     *   {@.ja SDO のステータスを定義するパラメータ。}
     *   {@.en One of the parameters defining the "status" of an SDO.}
     * 
     * @return 
     *   {@.ja 指定されたパラメータのステータス値。}
     *   {@.en The value of the specified status parameter.}
     * 
     * @exception SDONotExists 
     *   {@.ja ターゲットのSDOが存在しない。(本例外は、CORBA標準
     *         システム例外のOBJECT_NOT_EXISTにマッピングされる)}
     *   {@.en The target SDO does not exist.(This exception 
     *                         is mapped to CORBA standard system exception
     *                         OBJECT_NOT_EXIST.)}
     * @exception NotAvailable 
     *   {@.ja SDOは存在するが応答がない。}
     *   {@.en The target SDO is reachable but cannot respond.}
     * @exception InvalidParameter 
     *   {@.ja 引数 "name" が null あるいは存在しない。}
     *   {@.en The parameter defined by "name" is null or
     *                             does not exist.}
     * @exception InternalError 
     *   {@.ja 内部的エラーが発生した。}
     *   {@.en The target SDO cannot execute the operation
     *                          completely due to some internal error.}
     */
    public Any get_status(String name) throws InvalidParameter, NotAvailable, InternalError {


        rtcout.println(Logbuf.TRACE, "RTObject_impl.get_status(" + name + ")");

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
     * {@.ja [local interface] インスタンス名の取得。}
     * {@.en [local interface] Get instance name}
     * 
     * <p>
     * {@.ja ComponentProfile に設定されたインスタンス名を返す。}
     * {@.en Return the instance name that has been set in ComponentProfile.}
     * 
     * @return 
     *   {@.ja インスタンス名}
     *   {@.en Instance name}
     */
    public final String getInstanceName() {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.getInstanceName()");

        return m_profile.instance_name;
    }
    /**
     * {@.ja [local interface] インスタンス名の設定。}
     * {@.en [local interface] Set instance name}
     * 
     * <p>
     * {@.ja ComponentProfile に指定されたインスタンス名を設定する。}
     * {@.en Set the instance name specified in ComponentProfile.}
     * 
     * @param instance_name 
     *   {@.ja インスタンス名}
     *   {@.en Instance name}
     */
    public void setInstanceName(final String instance_name) {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.setInstanceName(" + instance_name + ")");

        m_properties.setProperty("instance_name", instance_name);
        m_profile.instance_name = m_properties.getProperty("instance_name");
    }
    /**
     * {@.ja [local interface] 型名の取得。}
     * {@.en [local interface] Get type name}
     * 
     * <p>
     * {@.ja ComponentProfile に設定された型名を返す。}
     * {@.en Get the type name has been set in ComponentProfile.}
     * 
     * @return 
     *   {@.ja 型名}
     *   {@.en Type name}
     */
    public final String getTypeName() {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.getTypeName()");

        return m_profile.type_name;
    }
    /**
     * {@.ja [local interface] カテゴリ情報の取得。}
     * {@.en [local interface] Get category information}
     * 
     * <p>
     * {@.ja ComponentProfile に設定されたカテゴリ情報を返す。}
     * {@.en Get the category information that has been set 
     * in ComponentProfile.}
     * 
     * @return 
     *   {@.ja カテゴリ情報}
     *   {@.en Category information}
     */
    public final String getCategory() {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.getCategory()");

        return m_profile.category;
    }
    /**
     * {@.ja [local interface] Naming Server 情報の取得。}
     * {@.en [local interface] Get Naming Server information}
     * 
     * <p>
     * {@.ja 設定された Naming Server 情報を返す。}
     * {@.en Get Naming Server information that has been set.}
     * 
     * @return 
     *   {@.ja Naming Server リスト}
     *   {@.en Naming Server list}
     */
    public String[] getNamingNames() {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.getNamingNames()");

        return m_properties.getProperty("naming.names").split(",");
    }
    /**
     * {@.ja [local interface] オブジェクトリファレンスの設定。}
     * {@.en [local interface] Set the object reference}
     * 
     * <p>
     * {@.ja RTC の CORBA オブジェクトリファレンスを設定する。}
     * {@.en Set RTC's CORBA object reference.}
     * 
     * @param rtobj 
     *   {@.ja オブジェクトリファレンス}
     *   {@.en The object reference}
     */
    public void setObjRef(final RTObject rtobj) {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.setObjRef()");

        m_objref = rtobj;
    }
    /**
     * {@.ja [local interface] オブジェクトリファレンスの取得。}
     * {@.en [local interface] Get the object reference}
     * 
     * <p>
     * {@.ja 設定された CORBA オブジェクトリファレンスを取得する。}
     * {@.en Get CORBA object reference that has been set}
     * 
     * @return 
     *   {@.ja オブジェクトリファレンス}
     *   {@.en The object reference}
     */
    public final RTObject getObjRef() {
        rtcout.println(Logbuf.TRACE, "RTObject_impl.getObjRef()");

        return (RTObject)m_objref._duplicate();
    }
    /**
     * {@.ja [local interface] RTC のプロパティを設定する。}
     * {@.en [local interface] Set RTC property}
     * 
     * <p>
     * {@.ja RTC が保持すべきプロパティを設定する。与えられるプロパティは、
     * ComponentProfile 等に設定されるべき情報を持たなければならない。
     * このオペレーションは通常 RTC が初期化される際に Manager から
     * 呼ばれることを意図している。}
     * {@.en This operation sets the properties to the RTC. The given property
     * values should include information for ComponentProfile.
     * Generally, this operation is designed to be called from Manager, when
     * RTC is initialized}
     * 
     * @param prop 
     *   {@.ja RTC のプロパティ}
     *   {@.en Property for RTC.}
     */
    public void setProperties(final Properties prop) {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.setProperties()");

        m_properties.merge(prop);
        try {
            syncAttributesByProperties();
        } catch (Exception ex) {
            
        }
    }

    /**
     * {@.ja プロパティを設定する }
     * {@.en Sets property}
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
     * {@.ja [local interface] RTC のプロパティを取得する。}
     * {@.en [local interface] Get RTC property}
     *
     * <p>
     * {@.ja RTC が保持しているプロパティを返す。
     * RTCがプロパティを持たない場合は空のプロパティが返される。}
     * {@.en This operation returns the properties of the RTC.
     * Empty property would be returned, if RTC has no property.}
     * 
     * @return 
     *   {@.ja RTC のプロパティ}
     *   {@.en Property for RTC.}
     */
    public Properties getProperties() {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.getProperties()");

        return m_properties;
    }

    /**
     * {@.ja コンフィギュレーションパラメータの設定。}
     * {@.en Setup for configuration parameters}
     * 
     * <p>
     * {@.ja コンフィギュレーションパラメータと変数をバインドする
     * \<ValueHolder\>としてコンフィギュレーションパラメータの
     * データ型を指定する。}
     * {@.en Bind configuration parameter to its variable.
     * Specify the data type of configuration parameter as \<ValueHolder\>.}
     *
     * @param param_name 
     *   {@.ja コンフィギュレーションパラメータ名}
     *   {@.en Configuration parameter name}
     * @param var 
     *   {@.ja コンフィギュレーションパラメータ格納用変数}
     *   {@.en Variables to store configuration parameter}
     * @param def_val 
     *   {@.ja コンフィギュレーションパラメータデフォルト値}
     *   {@.en Default value of configuration parameter}
     *
     * @return 
     *   {@.ja 設定結果(設定成功:true，設定失敗:false)}
     *   {@.en Setup result (Successful:true, Failed:false)}
     */
    public boolean bindParameter(final String param_name, ValueHolder var, final String def_val) {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.bindParameter(" + param_name + "," + def_val + ")");
        m_configsets.bindParameter(param_name, var, def_val);
        return true;
    }
    
    /**
     * {@.ja コンフィギュレーションパラメータの更新。}
     * {@.en Update configuration parameters}
     * 
     * <p>
     * {@.ja 指定したIDのコンフィギュレーションセットに設定した値で、
     * コンフィギュレーションパラメータの値を更新する}
     * {@.en Update configuration parameter value by the value that 
     * set to a configuration set of specified ID.}
     *
     * @param config_set 
     *   {@.ja 更新対象値}
     *   {@.en The target configuration set's ID for setup}
     */
    public void updateParameters(final String config_set) {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.updateParameters(" + config_set + ")");

        m_configsets.update(config_set);
        return;
    }

    /**
     * {@.ja [local interface] Port を登録する。}
     * {@.en [local interface] Register Port}
     *
     * <p>
     * {@.ja RTC が保持するPortを登録する。
     * Port を外部からアクセス可能にするためには、このオペレーションにより
     * 登録されていなければならない。登録される Port はこの RTC 内部において
     * PortProfile.name により区別される。したがって、Port は RTC 内において、
     * ユニークな PortProfile.name を持たなければならない。
     * 登録された Port は内部で適切にアクティブ化された後、その参照と
     * オブジェクト参照がリスト内に保存される。}
     * {@.en This operation registers a Port held by this RTC.
     * In order to enable access to the Port from outside of RTC, the Port
     * must be registered by this operation. The Port that is registered by
     * this operation would be identified by PortProfile.name in the inside of
     * RTC. Therefore, the Port should have unique PortProfile.name in the RTC.
     * The registering Port would be activated properly, and the reference
     * and the object reference would be stored in lists in RTC.}
     * </p>
     * 
     * @param port 
     *   {@.ja RTC に登録する Port}
     *   {@.en Port which is registered to the RTC}
     *
     */
    public void registerPort(PortBase port) {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.registerPort(PortBase)");

        if (!addPort(port)) {
            rtcout.println(Logbuf.ERROR, "addPort(PortBase&) failed.");
        }
    }

    /**
     * {@.ja [local interface] Port を登録する。}
     * {@.en [local interface] Register Port}
     * <p>
     * {@.ja RTC が保持するPortを登録する。
     * Port を外部からアクセス可能にするためには、このオペレーションにより
     * 登録されていなければならない。登録される Port はこの RTC 内部において
     * PortProfile.name により区別される。したがって、Port は RTC 内において、
     * ユニークな PortProfile.name を持たなければならない。
     * 登録された Port は内部で適切にアクティブ化された後、その参照と
     * オブジェクト参照がリスト内に保存される。}
     * {@.en This operation registers a Port held by this RTC.
     * In order to enable access to the Port from outside of RTC, the Port
     * must be registered by this operation. The Port that is registered by
     * this operation would be identified by PortProfile.name in the inside of
     * RTC. Therefore, the Port should have unique PortProfile.name in the RTC.
     * The registering Port would be activated properly, and the reference
     * and the object reference would be stored in lists in RTC.}
     * </p>
     * 
     * @param port 
     *   {@.ja RTC に登録する Port}
     *   {@.en Port which is registered to the RTC}
     * @return 
     *   {@.ja 登録結果(登録成功:true，登録失敗:false)}
     *   {@.en Register result (Successful:true, Failed:false)}
     *
     */
    public boolean addPort(PortBase port) {

        rtcout.println(Logbuf.TRACE, "addPort(PortBase)");

        port.setOwner(this.getObjRef());
        port.setPortConnectListenerHolder(m_portconnListeners);
        onAddPort(port.getPortProfile());

        return m_portAdmin.addPort(port);
    }

    /**
     * {@.ja [local interface] Port を登録する。}
     * {@.en [local interface] Register Port}
     *
     * <p>
     * {@.ja RTC が保持するPortを登録する。
     * Port を外部からアクセス可能にするためには、このオペレーションにより
     * 登録されていなければならない。登録される Port はこの RTC 内部において
     * PortProfile.name により区別される。したがって、Port は RTC 内において、
     * ユニークな PortProfile.name を持たなければならない。
     * 登録された Port は内部で適切にアクティブ化された後、その参照と
     * オブジェクト参照がリスト内に保存される。}
     * {@.en This operation registers a Port held by this RTC.
     * In order to enable access to the Port from outside of RTC, the Port
     * must be registered by this operation. The Port that is registered by
     * this operation would be identified by PortProfile.name in the inside of
     * RTC. Therefore, the Port should have unique PortProfile.name in the RTC.
     * The registering Port would be activated properly, and the reference
     * and the object reference would be stored in lists in RTC.}
     * 
     * @param port 
     *   {@.ja RTC に登録する Port}
     *   {@.en Port which is registered to the RTC}
     */
    public void registerPort(PortService port) {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.registerPort(PortService)");

        if (!addPort(port)){
            rtcout.println(Logbuf.ERROR, "addPort(PortBase&) failed.");
        }
    }

    /**
     * {@.ja [local interface] Port を登録する。}
     * {@.en [local interface] Register Port}
     *
     * <p>
     * {@.ja RTC が保持するPortを登録する。
     * Port を外部からアクセス可能にするためには、このオペレーションにより
     * 登録されていなければならない。登録される Port はこの RTC 内部において
     * PortProfile.name により区別される。したがって、Port は RTC 内において、
     * ユニークな PortProfile.name を持たなければならない。
     * 登録された Port は内部で適切にアクティブ化された後、その参照と
     * オブジェクト参照がリスト内に保存される。}
     * {@.en This operation registers a Port held by this RTC.
     * In order to enable access to the Port from outside of RTC, the Port
     * must be registered by this operation. The Port that is registered by
     * this operation would be identified by PortProfile.name in the inside of
     * RTC. Therefore, the Port should have unique PortProfile.name in the RTC.
     * The registering Port would be activated properly, and the reference
     * and the object reference would be stored in lists in RTC.}
     * 
     * @param port 
     *   {@.ja RTC に登録する Port}
     *   {@.en Port which is registered to the RTC}
     * @return 
     *   {@.ja 登録結果(登録成功:true，登録失敗:false)}
     *   {@.en Register result (Successful:true, Failed:false)}
     */
    public boolean addPort(PortService port) {

        rtcout.println(Logbuf.TRACE, "addPort(PortService_ptr)");
        return m_portAdmin.addPort(port);
    }

    /**
     * <p> [local interface] Register Port </p>
     *
     * This operation registers a Port held by this RTC.
     * In order to enable access to the Port from outside of RTC, the Port
     * must be registered by this operation. The Port that is registered by
     * this operation would be identified by PortProfile.name in the inside of
     * RTC. Therefore, the Port should have unique PortProfile.name in the RTC.
     * The registering Port would be activated properly, and the reference
     * and the object reference would be stored in lists in RTC.
     *
     * @param port Port which is registered to the RTC
     *
     */
    /**
     * {@.ja [local interface] Port を登録する。}
     * {@.en [local interface] Register Port}
     *
     * <p>
     * {@.ja RTC が保持するPortを登録する。
     * Port を外部からアクセス可能にするためには、このオペレーションにより
     * 登録されていなければならない。登録される Port はこの RTC 内部において
     * PortProfile.name により区別される。したがって、Port は RTC 内において、
     * ユニークな PortProfile.name を持たなければならない。
     * 登録された Port は内部で適切にアクティブ化された後、その参照と
     * オブジェクト参照がリスト内に保存される。}
     * {@.en This operation registers a Port held by this RTC.
     * In order to enable access to the Port from outside of RTC, the Port
     * must be registered by this operation. The Port that is registered by
     * this operation would be identified by PortProfile.name in the inside of
     * RTC. Therefore, the Port should have unique PortProfile.name in the RTC.
     * The registering Port would be activated properly, and the reference
     * and the object reference would be stored in lists in RTC.}
     * 
     * @param port 
     *   {@.ja RTC に登録する Port}
     *   {@.en Port which is registered to the RTC}
     */
    public void registerPort(CorbaPort port) {
        rtcout.println(Logbuf.TRACE, "registerPort(CorbaPort)");
        if (!addPort(port)) {
            rtcout.println(Logbuf.ERROR, "addPort(CorbaPort&) failed.");
        }
    }
    /**
     * {@.ja [local interface] Port を登録する。}
     * {@.en [local interface] Register Port}
     *
     * <p>
     * {@.ja RTC が保持するPortを登録する。
     * Port を外部からアクセス可能にするためには、このオペレーションにより
     * 登録されていなければならない。登録される Port はこの RTC 内部において
     * PortProfile.name により区別される。したがって、Port は RTC 内において、
     * ユニークな PortProfile.name を持たなければならない。
     * 登録された Port は内部で適切にアクティブ化された後、その参照と
     * オブジェクト参照がリスト内に保存される。}
     * {@.en This operation registers a Port held by this RTC.
     * In order to enable access to the Port from outside of RTC, the Port
     * must be registered by this operation. The Port that is registered by
     * this operation would be identified by PortProfile.name in the inside of
     * RTC. Therefore, the Port should have unique PortProfile.name in the RTC.
     * The registering Port would be activated properly, and the reference
     * and the object reference would be stored in lists in RTC.}
     * 
     * @param port 
     *   {@.ja RTC に登録する Port}
     *   {@.en Port which is registered to the RTC}
     * @return 
     *   {@.ja 登録結果(登録成功:true，登録失敗:false)}
     *   {@.en Register result (Successful:true, Failed:false)}
     */
    public boolean addPort(CorbaPort port) {
        rtcout.println(Logbuf.TRACE, "addPort(CrobaPort)");
        String propkey = "port.corbaport.";
        m_properties.getNode(propkey).merge(m_properties.getNode("port.corba"));
    
        port.init(m_properties.getNode(propkey));
        return addPort((PortBase)port);
    }
    /**
     * {@.ja [local interface] DataInPort を登録する.}
     * {@.en [local interface] Register DataInPort.}
     *
     * <p>
     * {@.ja RTC が保持するDataInPortを登録する。}
     * {@.en This operation registers DataInPort held by this RTC.}
     * 
     * @param DATA_TYPE_CLASS 
     *   {@.ja DataInPortがやりとりするデータ型}
     *   {@.en Data type}
     * @param name 
     *   {@.ja InPortの名称}
     *   {@.en InPort name}
     * @param inport 
     *   {@.ja InPortへの参照}
     *   {@.en InPort which is registered to the RTC}
     */
    public 
    <DataType, Buffer> void registerInPort(Class<DataType> DATA_TYPE_CLASS, 
                                final String name, InPort<DataType> inport) 
                                throws Exception {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.registerInPort("+name+")");

        this.registerInPort(name, inport);
//        String propkey = "port.dataport." + name + ".tcp_any";
//        PortBase port = new DataInPort(DATA_TYPE_CLASS, name, inport, m_properties.getNode(propkey));
//        this.registerPort(port);
    }

    /**
     * {@.ja [local interface] DataInPort を登録する.}
     * {@.en [local interface] Register DataInPort.}
     * <p>
     * {@.ja RTC が保持する DataInPort を登録する。
     * Port のプロパティにデータポートであること("port.dataport")、
     * TCPを使用すること("tcp_any")を設定するとともに、 DataInPort の
     * インスタンスを生成し、登録する。} 
     * {@.en This operation registers DataInPort held by this RTC.
     * Set "port.dataport" and "tcp_any" to property of Port, and
     * create instances of DataInPort and register it. }
     * </p>
     * @param name 
     *   {@.ja port 名称}
     *   {@.en Port name}
     * @param inport 
     *   {@.ja 登録対象 DataInPort}
     *   {@.en DataInPort which is registered to the RTC}
     * @return 
     *   {@.ja 登録結果(登録成功:true，登録失敗:false)}
     *   {@.en Register result (Successful:true, Failed:false)}
     */
    public boolean addInPort(final String name, InPortBase inport) {
        rtcout.println(Logbuf.TRACE, "addInPort("+name+")");

        String propkey = "port.inport.";
        propkey += name;
        m_properties.getNode(propkey).merge(m_properties.getNode("port.inport.dataport"));

        boolean ret = addPort(inport);
    
        if (!ret) {
            rtcout.println(Logbuf.ERROR, "addInPort() failed.");
            return ret;
        }

        inport.init(m_properties.getNode(propkey));
        synchronized (m_inports){
            m_inports.add(inport);
        }
        return ret;
    }
    /**
     * {@.ja [local interface] DataInPort を登録します。}
     * {@.en [local interface] Register DataInPort.}
     * <p>
     * {@.ja RTC が保持する DataInPort を登録する。
     *       Port のプロパティにデータポートであること("port.dataport")、
     *       TCPを使用すること("tcp_any")を設定するとともに、 DataInPort の
     *       インスタンスを生成し、登録する。}
     * {@.en This operation registers DataInPort held by this RTC.
     *       Set "port.dataport" and "tcp_any" to property of Port, and
     *       create instances of DataInPort and register it.}
     * </p>
     * @param name
     *   {@.ja port 名称}
     *   {@.en name Port name}
     * @param inport 
     *   {@.ja 登録対象 DataInPort}
     *   {@.en DataInPort which is registered to the RTC}
     */
    public void registerInPort(final String name, 
                                    InPortBase inport) throws Exception {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.registerInPort("+name+")");

        if (!addInPort(name, inport)){
            rtcout.println(Logbuf.ERROR, "addInPort("+name+") failed.");
        }

    }

    /**
     * {@.ja [local interface] DataOutPort を登録する.}
     * {@.en [local interface] Register DataOutPort.}
     * <p>
     * {@.ja RTC が保持する DataOutPortを登録する。
     * Port のプロパティにデータポートであること("port.dataport")、
     * TCPを使用すること("tcp_any")を設定するとともに、 DataOutPort の
     * インスタンスを生成し、登録する。}
     * {@.en This operation registers DataOutPor held by this RTC.
     * Set "port.dataport" and "tcp_any" to property of Port, and then
     * create instances of DataOutPort and register it.}
     * </p>
     * @param name i
     *   {@.ja port 名称}
     *   {@.en Port name}
     * @param outport 
     *   {@.ja 登録対象 DataOutPort}
     *   {@.en DataOutPort which is registered to the RTC}
     * @return 
     *   {@.ja 登録結果(登録成功:true，登録失敗:false)}
     *   {@.en Register result (Successful:true, Failed:false)}
     */
    public boolean addOutPort(final String name, OutPortBase outport) {
        rtcout.println(Logbuf.TRACE, "addOutPort("+name+")");
    
        String propkey = "port.outport.";
        propkey += name;
        m_properties.getNode(propkey).merge(m_properties.getNode("port.inport.dataport"));
    
        boolean ret = addPort(outport);
    
        if (!ret) {
            rtcout.println(Logbuf.ERROR, "addOutPort() failed.");
            return ret;
        }

        outport.init(m_properties.getNode(propkey));
        synchronized (m_outports){
            m_outports.add(outport);
        }
        return ret;

    }
    /**
     * {@.ja [local interface] DataOutPort を登録する.}
     * {@.en [local interface] Register DataOutPort.}
     *
     * {@.ja RTC が保持する DataOutPortを登録する。}
     * {@.en This operation registers DataOutPor held by this RTC.}
     * 
     * @param DATA_TYPE_CLASS 
     *   {@.ja DataOutPortがやりとりするデータ型}
     *   {@.en Data type}
     * @param name 
     *   {@.ja DataOutPortの名称}
     *   {@.en DataOutPort name}
     * @param outport 
     *   {@.ja OutPortへの参照}
     *   {@.en OutPort which is registered to the RTC}
     */
    public <DataType, Buffer> void registerOutPort(Class<DataType> DATA_TYPE_CLASS, 
                          final String name, OutPort<DataType> outport) throws Exception {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.registerOutPort()");

        this.registerOutPort(name, outport);
//	String propkey = "port.dataport." + name;
//        PortBase port = new DataOutPort(DATA_TYPE_CLASS, name, outport, m_properties.getNode(propkey));
//        this.registerPort(port);
    }

    /**
     * {@.ja [local interface] DataOutPort を登録します。}
     * {@.en [local interface] Register DataOutPort}
     * <p>
     * {@.ja RTC が保持する DataOutPortを登録する。
     *       Port のプロパティにデータポートであること("port.dataport")、
     *       TCPを使用すること("tcp_any")を設定するとともに、 DataOutPort の
     *       インスタンスを生成し、登録する。}
     * {@.en This operation registers DataOutPor held by this RTC.
     *       Set "port.dataport" and "tcp_any" to property of Port, and then
     *       create instances of DataOutPort and register it.}
     * </p>
     * @param name 
     *   {@.ja port 名称}
     *   {@.en Port name}
     * @param outport
     *   {@.ja 登録対象 DataOutPort}
     *   {@.en DataOutPort which is registered to the RTC}
     */
    public void registerOutPort(final String name, 
                                    OutPortBase outport) throws Exception {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.registerOutPort("+name+")");


        if (!addOutPort(name, outport)){
            rtcout.println(Logbuf.ERROR, "addOutPort("+name+") failed.");
        }

    }

    /**
     * {@.ja [local interface] InPort の登録を削除する}
     * {@.en [local interface] Unregister InPort}
     * <p>
     * {@.ja RTC が保持するInPortの登録を削除する。}
     * {@.en This operation unregisters a InPort held by this RTC.}
     * </p>
     * @param port
     *   {@.ja 削除対象 Port}
     *   {@.en Port which is unregistered}
     * @return
     *   {@.ja 削除結果(削除成功:true，削除失敗:false)}
     *   {@.en Unregister result (Successful:true, Failed:false)}
     */
    public boolean removeInPort(InPortBase port) {
        rtcout.println(Logbuf.TRACE, "removeInPort()");
        boolean  ret = removePort(port);

        synchronized (m_inports){
            java.util.Iterator it = m_inports.iterator(); 

            if(ret){
                while( !it.hasNext() ){
                    if ( it.next() == port) {
                        m_inports.remove(it);
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * {@.ja [local interface] OutPort の登録を削除する}
     * {@.en [local interface] Unregister OutPort.}
     * <p>
     * {@.ja RTC が保持するOutPortの登録を削除する。}
     * {@.en This operation unregisters a OutPort held by this RTC.}
     * </p> 
     * @param port
     *   {@.ja 削除対象 Port}
     *   {@.en Port which is unregistered}
     * @return
     *   {@.ja 削除結果(削除成功:true，削除失敗:false)}
     *   {@.en Unregister result (Successful:true, Failed:false)}
     */
    public boolean removeOutPort(OutPortBase port){
        rtcout.println(Logbuf.TRACE, "removeOutPort()");
        boolean  ret = removePort(port);

        synchronized (m_outports){
            java.util.Iterator it = m_outports.iterator(); 

            if(ret){
                while( !it.hasNext() ){
                    if ( it.next() == port) {
                        m_outports.remove(it);
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * {@.ja [local interface] Port の登録を削除する}
     * {@.en [local interface] Unregister Port}
     * <p>
     * {@.ja RTC が保持するPortの登録を削除する。}
     * {@.en  This operation unregisters a Port held by this RTC.}
     * </p>
     * @param port
     *   {@.ja 削除対象 Port}
     *   {@.en Port which is unregistered}
     * @return
     *   {@.ja 削除結果(削除成功:true，削除失敗:false)}
     *   {@.en Unregister result (Successful:true, Failed:false)}
     */
    public boolean removePort(PortBase port){
        rtcout.println(Logbuf.TRACE, "removePort(PortBase)");
        onRemovePort(port.getPortProfile());
        return m_portAdmin.removePort(port);
    }

    /**
     * {@.ja [local interface] Port の登録を削除する}
     * {@.en [local interface] Unregister Port}
     * <p>
     * {@.ja RTC が保持するPortの登録を削除する。}
     * {@.en This operation unregisters a Port held by this RTC.}
     * </p> 
     * @param port
     *   {@.ja 削除対象 Port}
     *   {@.en Port which is unregistered}
     * @return
     *   {@.ja 削除結果(削除成功:true，削除失敗:false)}
     *   {@.en Unregister result (Successful:true, Failed:false)}
     */
    public boolean removePort(PortService port){
        rtcout.println(Logbuf.TRACE, "removePort(PortService)");
        return m_portAdmin.removePort(port);
    }

    /**
     * {@.ja [local interface] Port の登録を削除する}
     * {@.en [local interface] Unregister Port}
     * <p>
     * {@.ja RTC が保持するPortの登録を削除する。}
     * {@.en This operation unregisters a Port held by this RTC.}
     * </p>
     * @param port
     *   {@.ja 削除対象 Port}
     *   {@.en Port which is unregistered}
     * @return
     *   {@.ja 削除結果(削除成功:true，削除失敗:false)}
     *   {@.en Unregister result (Successful:true, Failed:false)}
     */
    public boolean removePort(CorbaPort port) {
        rtcout.println(Logbuf.TRACE, "removePort(CorbaPortort)");
        onRemovePort(port.getPortProfile());
        return m_portAdmin.removePort((PortBase)port);
    }

    /**
     * {@.ja [local interface] Port の登録を削除します。}
     * {@.en [local interface] Unregister Port}
     * <p>
     * {@.ja RTC が保持するPortの登録を削除します。}
     * {@.en This operation unregisters a Port held by this RTC.}
     * </p>
     * @param port 
     *   {@.ja 削除対象 Port}
     *   {@.en Port which is unregistered}
     */
    public void deletePort(PortBase port) {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.deletePort(PortBase)");

        if (!removePort(port)){
            rtcout.println(Logbuf.ERROR, "removePort(PortBase&) failed.");
        }
        return;
    }

    /**
     * {@.ja [local interface] Port の登録を削除します。}
     * {@.en [local interface] Unregister Port}
     * <p>
     * {@.ja RTC が保持するPortの登録を削除します。}
     * {@.en This operation unregisters a Port held by this RTC.}
     * </p>
     * @param port 
     *   {@.ja 削除対象 Port}
     *   {@.en Port which is unregistered}
     */
    public void deletePort(PortService port) {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.deletePort(PortService)");

        if (!removePort(port)){
            rtcout.println(Logbuf.ERROR, "removePort(PortService_pt) failed.");
        }
        return;
    }

    /**
     * {@.ja [local interface] Port の登録を削除します。}
     * {@.en [local interface] Unregister Port}
     * <p>
     * {@.ja RTC が保持するPortの登録を削除します。}
     * {@.en This operation unregisters a Port held by this RTC.}
     * </p>
     * @param port 
     *   {@.ja 削除対象 Port}
     *   {@.en Port which is unregistered}
     */
    public void deletePort(CorbaPort port) {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.deletePort(CorbaPort)");

        if (!removePort(port)){
            rtcout.println(Logbuf.ERROR, "removePort(CorbaPort) failed.");
        }
        return;
    }

    /**
     * {@.ja [local interface] 名前指定により Port の登録を削除する。}
     * {@.en [local interface] Delete Port by specifying its name}
     *
     * <p>
     * {@.ja 名称を指定して RTC が保持するPortの登録を削除する。}
     * {@.en Delete Port which RTC has by specifying its name.}
     * 
     * @param port_name 
     *   {@.ja 削除対象 Port 名}
     *   {@.en Name of Port which is deleted}
     */
    public void deletePortByName(final String port_name) {

        rtcout.println(Logbuf.TRACE, 
                    "RTObject_impl.deletePortByNamed(" + port_name + ")");

        m_portAdmin.deletePortByName(port_name);
        return;
    }

    /**
     * {@.ja [local interface] 実行コンテキストを取得する}
     * {@.en [local interface] Getting current execution context}
     * <p>
     * {@.ja get_context() と同じ機能のローカル版。違いはない。
     * この関数は以下の関数内で呼ばれることを前提としている。
     *
     * - onStartup()
     * - onShutdown()
     * - onActivated()
     * - onDeactivated()
     * - onExecute()
     * - onAborting()
     * - onError()
     * - onReset()
     * - onStateUpdate()
     * - onRateChanged()
     *
     * この関数の引数はこれらの関数の引数 UniquieID exec_handle でなけ
     * ればならない。}
     * {@.en This function is the local version of get_context(). completely
     * same as get_context() function. This function is assumed to be
     * called from the following functions.
     *
     * - onStartup()
     * - onShutdown()
     * - onActivated()
     * - onDeactivated()
     * - onExecute()
     * - onAborting()
     * - onError()
     * - onReset()
     * - onStateUpdate()
     * - onRateChanged()
     *
     * The argument of this function should be the first argument
     * (UniqueId ec_id) of the above functions.}
     *
     * @param ec_id 
     *   {@.ja 上記関数の第1引数 exec_handle を渡す必要がある。}
     *   {@.en The above functions' first argument "exec_handle."}
     *
     */
    public ExecutionContext getExecutionContext(int ec_id){
        return get_context(ec_id);
    }

    /**
     * {@.ja [local interface] 実行コンテキストの実行レートを取得する}
     * {@.en [local interface] Getting current context' execution rate}
     * <p>
     * {@.ja 現在実行中の実行コンテキストの実行レートを取得する。実行コンテキ
     * ストのKindがPERIODIC以外の場合の動作は未定義である。この関数は以
     * 下の関数内で呼ばれることを前提としている。
     *
     * - onStartup()
     * - onShutdown()
     * - onActivated()
     * - onDeactivated()
     * - onExecute()
     * - onAborting()
     * - onError()
     * - onReset()
     * - onStateUpdate()
     * - onRateChanged()
     *
     * この関数の引数はこれらの関数の引数 UniquieID exec_handle でなけ
     * ればならない。}
     * {@.en This function returns current execution rate in this
     * context. If this context's kind is not PERIODC, behavior is not
     * defined. This function is assumed to be called from the
     * following functions.
     *
     * - onStartup()
     * - onShutdown()
     * - onActivated()
     * - onDeactivated()
     * - onExecute()
     * - onAborting()
     * - onError()
     * - onReset()
     * - onStateUpdate()
     * - onRateChanged()
     *
     * The argument of this function should be the first argument
     * (UniqueId ec_id) of the above functions.}
     *
     * @param ec_id
     *   {@.ja 上記関数の第1引数 exec_handle を渡す必要がある。}
     *   {@.en The above functions' first argument "exec_handle."}
     *
     */
    public double getExecutionRate(int ec_id) {
        ExecutionContext ec = getExecutionContext(ec_id);
        if (ec == null) {
            return 0.0;
        }
        return ec.get_rate();
    }

    /**
     * {@.ja [local interface] 実行コンテキストの実行レートを設定する}
     * {@.en [local interface] Setting current context' execution rate}
     * <p>
     * {@.ja 現在実行中の実行コンテキストの実行レートを設定する。実行コンテキ
     * ストのKindがPERIODIC以外の場合の動作は未定義である。この関数は以
     * 下の関数内で呼ばれることを前提としている。
     *
     * - onStartup()
     * - onShutdown()
     * - onActivated()
     * - onDeactivated()
     * - onExecute()
     * - onAborting()
     * - onError()
     * - onReset()
     * - onStateUpdate()
     * - onRateChanged()
     *
     * この関数の引数はこれらの関数の引数 UniquieID exec_handle でなけ
     * ればならない。}
     * {@.en This function sets a execution rate in the context. If this
     * context's kind is not PERIODC, behavior is not defined. This
     * function is assumed to be called from the following functions.
     *
     * - onStartup()
     * - onShutdown()
     * - onActivated()
     * - onDeactivated()
     * - onExecute()
     * - onAborting()
     * - onError()
     * - onReset()
     * - onStateUpdate()
     * - onRateChanged()
     *
     * The argument of this function should be the first argument
     * (UniqueId ec_id) of the above functions.}
     *
     *
     * @param ec_id 
     *   {@.ja 上記関数の第1引数 exec_handle を渡す必要がある。}
     *   {@.en The above functions' first argument "exec_handle."}
     * @param rate 
     *   {@.ja 実行レートを [Hz] で与える}
     *   {@.en Execution rate in [Hz].}
     *
     */
    public ReturnCode_t setExecutionRate(int ec_id, double rate){
        ExecutionContext ec=getExecutionContext(ec_id);
        if (ec==null) {
            return ReturnCode_t.RTC_ERROR;
        }
        ec.set_rate(rate);
        return ReturnCode_t.RTC_OK;
    }

    /**
     * {@.ja [local interface] 実行コンテキストの所有権を調べる}
     * {@.en [local interface] Checking if the current context is own context}
     * <p>
     * {@.ja 現在実行中の実行コンテキストの所有権を調べる。この関数は以下の関
     * 数内で呼ばれることを前提としている。
     *
     * - onStartup()
     * - onShutdown()
     * - onActivated()
     * - onDeactivated()
     * - onExecute()
     * - onAborting()
     * - onError()
     * - onReset()
     * - onStateUpdate()
     * - onRateChanged()
     *
     * この関数の引数はこれらの関数の引数 UniquieID exec_handle でなけ
     * ればならない。}
     * {@.en This function checks if the current context is own execution
     * context. This function is assumed to be called from the
     * following functions.
     *
     * - onStartup()
     * - onShutdown()
     * - onActivated()
     * - onDeactivated()
     * - onExecute()
     * - onAborting()
     * - onError()
     * - onReset()
     * - onStateUpdate()
     * - onRateChanged()
     *
     * The argument of this function should be the first argument
     * (UniqueId ec_id) of the above functions.}
     *
     *
     * @param ec_id 
     *   {@.ja 上記関数の第1引数 exec_handle を渡す必要がある。}
     *   {@.en The above functions' first argument "exec_handle."}
     * @return 
     *   {@.ja true: 自身の実行コンテキスト、false: 他の実行コンテキスト}
     *   {@.en true: Own context, false: other's context}
     *
     */
    public boolean isOwnExecutionContext(int ec_id){
        if (ec_id < ECOTHER_OFFSET) {
            return true;
        }
        return false;
    }

    /**
     * {@.ja [local interface] 状態を Inactive に遷移させる}
     * {@.en [local interface] Make transition to Inactive state}
     * <p>
     * {@.ja 状態を Active から Inactive に遷移させる。この関数は以下の関
     * 数内で呼ばれることを前提としている。
     *
     * - onActivated()
     * - onExecute()
     * - onStateUpdate()
     *
     * この関数の引数は上記の関数の引数 UniquieID exec_handle でなけ
     * ればならない。}
     * {@.en This function makes transition from Active to Inactive
     * state. This function is assumed to be called from the following
     * functions.
     *
     * - onActivated()
     * - onExecute()
     * - onStateUpdate()
     *
     * The argument of this function should be the first argument
     * (UniqueId ec_id) of the above function.}
     *
     * @param ec_id 
     *   {@.ja 上記関数の第1引数 exec_handle を渡す必要がある。}
     *   {@.en The above functions' first argument "exec_handle."}
     * @return 
     *   {@.ja リターンコード}
     *   {@.en Return code}
     *
     */
    public ReturnCode_t deactivate(int ec_id) {
        ExecutionContext ec=getExecutionContext(ec_id);
        if (ec==null) {
            return ReturnCode_t.RTC_ERROR;
        }
        return ec.deactivate_component((LightweightRTObject)getObjRef()._duplicate());
    }

    /**
     * {@.ja [local interface] 状態を Active に遷移させる}
     * {@.en [local interface] Make transition to Active state}
     * <p>
     * {@.ja 状態を Inactive から Active に遷移させる。この関数は以下の関
     * 数内で呼ばれることを前提としている。
     *
     * - onStartup()
     * - onDeactivated()
     *
     * この関数の引数は上記の関数の引数 UniquieID exec_handle でなけ
     * ればならない。}
     * {@.en This function makes transition from Inactive to Active
     * state. This function is assumed to be called from the following
     * functions.
     *
     * - onStartup()
     * - onDeactivated()
     *
     * The argument of this function should be the first argument
     * (UniqueId ec_id) of the above function.}
     *
     * @param ec_id 
     *   {@.ja 上記関数の第1引数 exec_handle を渡す必要がある。}
     *   {@.en The above functions' first argument "exec_handle."}
     * @return 
     *   {@.ja リターンコード}
     *   {@.en Return code}
     *
     */
    public ReturnCode_t activate(int ec_id){
        ExecutionContext ec = getExecutionContext(ec_id);
        if (ec == null) {
            return ReturnCode_t.RTC_ERROR;
        }
        return ec.activate_component((LightweightRTObject)getObjRef()._duplicate());
    }

    /**
     * {@.ja [local interface] 状態をリセットし Inactive に遷移させる}
     * {@.en [local interface] Resetting and go to Inactive state}
     * <p>
     * {@.ja 状態を Error から Inactive に遷移させる。この関数は以下の関
     * 数内で呼ばれることを前提としている。
     *
     * - onError()
     *
     * この関数の引数は上記の関数の引数 UniquieID exec_handle でなけ
     * ればならない。}
     * {@.en This function reset RTC and makes transition from Error to Inactive
     * state. This function is assumed to be called from the following
     * functions.
     *
     * - onError()
     *
     * The argument of this function should be the first argument
     * (UniqueId ec_id) of the above function.}
     *
     * @param ec_id 
     *   {@.ja 上記関数の第1引数 exec_handle を渡す必要がある。}
     *   {@.en The above functions' first argument "exec_handle."}
     * @return 
     *   {@.ja リターンコード}
     *   {@.en Return code}
     *
     */
    public ReturnCode_t reset(int ec_id) {
        ExecutionContext ec=getExecutionContext(ec_id);
        if (ec==null) {
            return ReturnCode_t.RTC_ERROR;
        }
        return ec.reset_component((LightweightRTObject)getObjRef()._duplicate());
    }

    /**
     * {@.ja [local interface] SDO service provider をセットする}
     * {@.en [local interface] Set a SDO service provider}
     */
    public boolean addSdoServiceProvider(final ServiceProfile prof,
                               SdoServiceProviderBase provider){
        return m_sdoservice.addSdoServiceProvider(prof, provider);
    }

    /**
     * {@.ja [local interface] SDO service provider を削除する}
     * {@.en [local interface] Remove a SDO service provider}
     */
    public boolean removeSdoServiceProvider(final String id) {
        return m_sdoservice.removeSdoServiceProvider(id);
    }

    /**
     * {@.ja [local interface] SDO service provider をセットする}
     * {@.en [local interface] Set a SDO service provider}
     */
    public boolean addSdoServiceConsumer(final ServiceProfile prof) {
        return m_sdoservice.addSdoServiceConsumer(prof);
    }

    /**
     * {@.ja [local interface] SDO service provider を削除する}
     * {@.en [local interface] Remove a SDO service provider}
     */
    public boolean removeSdoServiceConsumer(final String id) {
        return m_sdoservice.removeSdoServiceConsumer(id);
    }

    /**
     * {@.ja 全 InPort のデータを読み込む。}
     * {@.en Readout the value from All InPorts.}
     * <p>
     * {@.ja RTC が保持する全ての InPort のデータを読み込む。}
     * {@.en This operation read the value from all InPort 
     * registered in the RTC.}
     * </p>
     * @return  
     *   {@.ja 読み込み結果(全ポートの読み込み成功:true，失敗:false)}
     *   {@.en result (Successful:true, Failed:false)}
     *
     */
    public boolean readAll() {
        rtcout.println(Logbuf.TRACE, "readAll()");
        synchronized (m_inports){
            java.util.Iterator it = m_inports.iterator(); 

            boolean ret = true;
            while( it.hasNext() ) {
                if (!((InPortBase)it.next()).read()) {
                    rtcout.println(Logbuf.DEBUG, 
                            "The error occurred in readAll().");
                    ret = false;
                    if (!m_readAllCompletion) {
                        return false;
                    }
                }
            } 
            return ret;
        }
    }

    /**
     * {@.ja 全 OutPort のwrite()メソッドをコールする。}
     * {@.en The write() method of all OutPorts are called.}
     * <p>
     * {@.ja RTC が保持する全ての OutPort のwrite()メソッドをコールする。}
     * {@.en This operation call the write() method of all OutPort
     *       registered in the RTC.}
     * </p>
     * @return  
     *   {@.ja 読み込み結果(全ポートへの書き込み成功:true，失敗:false)}
     *   {@.en result (Successful:true, Failed:false)}
     *
     */
    public boolean writeAll() {
        rtcout.println(Logbuf.TRACE, "writeAll()");
        synchronized (m_outports){
            java.util.Iterator it = m_outports.iterator(); 
            boolean ret = true;
            while( it.hasNext() ){
                if (!((OutPortBase)it.next()).write()) {
                    rtcout.println(Logbuf.DEBUG, 
                            "The error occurred in writeAll().");
                    ret = false;
                    if (!m_writeAllCompletion) {
                        return false;
                    }
                }
            }
            return ret;
        } 
    }

    /**
     * {@.ja onExecute()実行前でのreadAll()メソッドの呼出を有効または
     *       無効にする。}
     * {@.en Set whether to execute the readAll() method.}
     * <p>
     * {@.ja このメソッドをパラメータをtrueとして呼ぶ事により、
     * onExecute()実行前に readAll()が呼出されるようになる。
     * パラメータがfalseの場合は、readAll()呼出を無効にする。}
     * {@.en  Set whether to execute the readAll() method.} 
     * </p>
     * @param read (default:true) 
     *   {@.ja (readAll()メソッド呼出あり:true, 
     *          readAll()メソッド呼出なし:false)}
     *   {@.en (readAll() is called:true, readAll() isn't called:false)}
     * @param completion (default:false) 
     *   {@.ja readAll()にて、どれかの一つのInPortのread()が失敗しても
     *         全てのInPortのread()を呼び出す:true,
     *         readAll()にて、どれかの一つのInPortのread()が失敗した場合、
     *         すぐにfalseで抜ける:false}
     *   {@.en All InPort::read() calls are completed.:true,
     *         If one InPort::read() is False, return false.:false}
     */
    public void setReadAll(boolean read, boolean completion){
        m_readAll = read;
        m_readAllCompletion = completion;
    }
    /**
     * {@.ja onExecute()実行前でのreadAll()メソッドの呼出を有効または
     *       無効にする。}
     * {@.en Set whether to execute the readAll() method.}
     * <p>
     * {@.ja readAll()メソッド呼出あり、
     *       readAll()にて、どれかの一つのInPortのread()が失敗した場合、
     *       すぐにfalseで抜ける}
     * {@.en readAll() is called, If one InPort::read() is False, 
     *       return false.} 
     */
    public void setReadAll(){
        this.setReadAll(true, false);
    }
    /**
     * {@.ja onExecute()実行前でのreadAll()メソッドの呼出を有効または
     *       無効にする。}
     * {@.en Set whether to execute the readAll() method.}
     * <p>
     * {@.ja readAll()にて、どれかの一つのInPortのread()が失敗した場合、
     *         すぐにfalseで抜ける}
     * {@.en If one InPort::read() is False, return false.}
     * </p>
     * @param read (default:true) 
     *   {@.ja (readAll()メソッド呼出あり:true, 
     *          readAll()メソッド呼出なし:false)}
     *   {@.en (readAll() is called:true, readAll() isn't called:false)}
     */
    public void setReadAll(boolean read){
        this.setReadAll(read, false);
    }

    /**
     * {@.ja onExecute()実行後にwriteAll()メソッドの呼出を有効または
     *       無効にする。}
     * {@.en Set whether to execute the writeAll() method.}
     * <p>
     * {@.ja このメソッドをパラメータをtrueとして呼ぶ事により、
     *       onExecute()実行後にwriteAll()が呼出されるようになる。
     *       パラメータがfalseの場合は、writeAll()呼出を無効にする。}
     * {@.en Set whether to execute the writeAll() method.}
     * </p>
     * @param write (default:true) 
     *   {@.ja (writeAll()メソッド呼出あり:true, 
     *          writeAll()メソッド呼出なし:false)}
     *   {@.en (writeAll() is called:true, writeAll() isn't called:false)}
     * @param completion (default:false) 
     *   {@.ja writeAll()にて、どれかの一つのOutPortのwrite()が失敗しても
     *         全てのOutPortのwrite()を呼び出しを行う:true,
     *         writeAll()にて、どれかの一つのOutPortのwrite()が失敗した場合、
     *         すぐにfalseで抜ける:false}
     *   {@.en All OutPort::write() calls are completed.:true,
     *         If one OutPort::write() is False, return false.:false}
     */
    public void setWriteAll(boolean write, boolean completion){
        m_writeAll = write;
        m_writeAllCompletion = completion;
    }
    /**
     * {@.ja onExecute()実行後にwriteAll()メソッドの呼出を有効または
     *       無効にする。}
     * {@.en Set whether to execute the writeAll() method.}
     * <p>
     * {@.ja writeAll()メソッド呼出あり、 
     *       writeAll()にて、どれかの一つのOutPortのwrite()が失敗した場合、
     *       すぐにfalseで抜ける}
     * {@.en writeAll() is called,
     *       If one OutPort::write() is False, return false.}
     */
    public void setWriteAll(){
        this.setWriteAll(true, false);
    }
    /**
     * {@.ja onExecute()実行後にwriteAll()メソッドの呼出を有効または
     *       無効にする。}
     * {@.en Set whether to execute the writeAll() method.}
     * <p>
     * {@.ja writeAll()にて、どれかの一つのOutPortのwrite()が失敗した場合、
     *         すぐにfalseで抜ける}
     * {@.en If one OutPort::write() is False, return false.}
     *
     * @param write (default:true) 
     *   {@.ja (writeAll()メソッド呼出あり:true, 
     *          writeAll()メソッド呼出なし:false)}
     *   {@.en (writeAll() is called:true, writeAll() isn't called:false)}
     */
    public void setWriteAll(boolean write){
        this.setWriteAll(write, false);
    }


    /**
     * {@.ja 登録されているすべてのPortの登録を削除します。}
     * {@.en Unregister All Ports}
     * <p>
     * {@.ja RTC が保持する全ての Port を削除する。}
     * {@.en This operation deactivates the all Ports and deletes the all Port's
     * registrations in the RTC}
     */
    public void finalizePorts() {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.finalizePorts()");

        m_portAdmin.finalizePorts();
        synchronized (m_inports){
            m_inports.clear();
        }
        synchronized (m_outports){
            m_outports.clear();
        }
    }

    /**
     * {@.ja ExecutionContextBaseリストの登録を削除する}
     * {@.en The ExecutionContextBase list is deleted.}
     */
    public void finalizeContexts() {

        rtcout.println(Logbuf.TRACE, "RTObject_impl.finalizeContexts()");

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
     * {@.ja PreComponentActionListener リスナを追加する}
     * {@.en Adding PreComponentAction type listener}
     * <p>
     * {@.ja ComponentAction 実装関数の呼び出し直前のイベントに関連する各種リ
     * スナを設定する。
     *
     * 設定できるリスナのタイプとコールバックイベントは以下の通り
     * <ul>
     * <li> PRE_ON_INITIALIZE:    onInitialize 直前
     * <li> PRE_ON_FINALIZE:      onFinalize 直前
     * <li> PRE_ON_STARTUP:       onStartup 直前
     * <li> PRE_ON_SHUTDOWN:      onShutdown 直前
     * <li> PRE_ON_ACTIVATED:     onActivated 直前
     * <li> PRE_ON_DEACTIVATED:   onDeactivated 直前
     * <li> PRE_ON_ABORTED:       onAborted 直前
     * <li> PRE_ON_ERROR:         onError 直前
     * <li> PRE_ON_RESET:         onReset 直前
     * <li> PRE_ON_EXECUTE:       onExecute 直前
     * <li> PRE_ON_STATE_UPDATE:  onStateUpdate 直前
     * </ul>
     *
     * リスナは PreComponentActionListener を継承し、以下のシグニチャを持つ
     * operator() を実装している必要がある。
     *
     * PreComponentActionListener::operator()(int ec_id)
     *
     * デフォルトでは、この関数に与えたリスナオブジェクトの所有権は
     * RTObjectに移り、RTObject解体時もしくは、
     * removePreComponentActionListener() により削除時に自動的に解体される。
     * リスナオブジェクトの所有権を呼び出し側で維持したい場合は、第3引
     * 数に false を指定し、自動的な解体を抑制することができる。}
     * {@.en This operation adds certain listeners related to ComponentActions
     * pre events.
     * The following listener types are available.
     * <ul>
     * <li> PRE_ON_INITIALIZE:    before onInitialize
     * <li> PRE_ON_FINALIZE:      before onFinalize
     * <li> PRE_ON_STARTUP:       before onStartup
     * <li> PRE_ON_SHUTDOWN:      before onShutdown
     * <li> PRE_ON_ACTIVATED:     before onActivated
     * <li> PRE_ON_DEACTIVATED:   before onDeactivated
     * <li> PRE_ON_ABORTED:       before onAborted
     * <li> PRE_ON_ERROR:         before onError
     * <li> PRE_ON_RESET:         before onReset
     * <li> PRE_ON_EXECUTE:       before onExecute
     * <li> PRE_ON_STATE_UPDATE:  before onStateUpdate
     * </ul>
     *
     * Listeners should have the following function operator().
     *
     * PreComponentActionListener::operator()(int ec_id)
     *
     * The ownership of the given listener object is transferred to
     * this RTObject object in default.  The given listener object will
     * be destroied automatically in the RTObject's dtor or if the
     * listener is deleted by removePreComponentActionListener() function.
     * If you want to keep ownership of the listener object, give
     * "false" value to 3rd argument to inhibit automatic destruction.}
     * 
     * @param listener_type 
     *   {@.ja リスナタイプ}
     *   {@.en A listener type}
     * @param listener 
     *   {@.ja リスナオブジェクトへのポインタ}
     *   {@.en A pointer to a listener object}
     * @param autoclean 
     *   {@.ja リスナオブジェクトの自動的解体を行うかどうかのフラグ}
     *   {@.en A flag for automatic listener destruction}
     */
    public void 
    addPreComponentActionListener(int listener_type,
                                  PreComponentActionListener listener,
                                  boolean autoclean) {
        if (listener_type < PreComponentActionListenerType.PRE_COMPONENT_ACTION_LISTENER_NUM ) {
            rtcout.println(Logbuf.TRACE,
                    "addPreComponentActionListener("
                    +PreComponentActionListenerType.toString(listener_type)
                    +")");
            m_actionListeners.
              preaction_[listener_type].addObserver(listener);
            return;
        }
        rtcout.println(Logbuf.ERROR, 
                    "addPreComponentActionListener(): Invalid listener type.");
        return;
    }
    /**
     * {@.ja PreComponentActionListener リスナを追加する}
     * {@.en Adding PreComponentAction type listener}
     * <p>
     * {@.ja ComponentAction 実装関数の呼び出し直前のイベントに関連する各種リ
     * スナを設定する。
     *
     * 設定できるリスナのタイプとコールバックイベントは以下の通り
     * <ul>
     * <li> PRE_ON_INITIALIZE:    onInitialize 直前
     * <li> PRE_ON_FINALIZE:      onFinalize 直前
     * <li> PRE_ON_STARTUP:       onStartup 直前
     * <li> PRE_ON_SHUTDOWN:      onShutdown 直前
     * <li> PRE_ON_ACTIVATED:     onActivated 直前
     * <li> PRE_ON_DEACTIVATED:   onDeactivated 直前
     * <li> PRE_ON_ABORTED:       onAborted 直前
     * <li> PRE_ON_ERROR:         onError 直前
     * <li> PRE_ON_RESET:         onReset 直前
     * <li> PRE_ON_EXECUTE:       onExecute 直前
     * <li> PRE_ON_STATE_UPDATE:  onStateUpdate 直前
     * </ul>
     *
     * リスナは PreComponentActionListener を継承し、以下のシグニチャを持つ
     * operator() を実装している必要がある。
     *
     * PreComponentActionListener::operator()(int ec_id)
     *
     * デフォルトでは、この関数に与えたリスナオブジェクトの所有権は
     * RTObjectに移り、RTObject解体時もしくは、
     * removePreComponentActionListener() により削除時に自動的に解体される。
     * リスナオブジェクトの所有権を呼び出し側で維持したい場合は、第3引
     * 数に false を指定し、自動的な解体を抑制することができる。}
     * {@.en This operation adds certain listeners related to ComponentActions
     * pre events.
     * The following listener types are available.
     * <ul>
     * <li> PRE_ON_INITIALIZE:    before onInitialize
     * <li> PRE_ON_FINALIZE:      before onFinalize
     * <li> PRE_ON_STARTUP:       before onStartup
     * <li> PRE_ON_SHUTDOWN:      before onShutdown
     * <li> PRE_ON_ACTIVATED:     before onActivated
     * <li> PRE_ON_DEACTIVATED:   before onDeactivated
     * <li> PRE_ON_ABORTED:       before onAborted
     * <li> PRE_ON_ERROR:         before onError
     * <li> PRE_ON_RESET:         before onReset
     * <li> PRE_ON_EXECUTE:       before onExecute
     * <li> PRE_ON_STATE_UPDATE:  before onStateUpdate
     * </ul>
     *
     * Listeners should have the following function operator().
     *
     * PreComponentActionListener::operator()(int ec_id)
     *
     * The ownership of the given listener object is transferred to
     * this RTObject object in default.  The given listener object will
     * be destroied automatically in the RTObject's dtor or if the
     * listener is deleted by removePreComponentActionListener() function.
     * If you want to keep ownership of the listener object, give
     * "false" value to 3rd argument to inhibit automatic destruction.}
     * 
     * @param listener_type 
     *   {@.ja リスナタイプ}
     *   {@.en A listener type}
     * @param listener 
     *   {@.ja リスナオブジェクトへのポインタ}
     *   {@.en A pointer to a listener object}
     */
    public void 
    addPreComponentActionListener(int listener_type,
                                  PreComponentActionListener listener) {
        this.addPreComponentActionListener(listener_type,listener,true);
    }

    /**
     * {@.ja PreComponentActionListener リスナを追加する}
     * {@.en Adding PreComponentAction type listener}
     * <p>
     * {@.ja ComponentAction 実装関数の呼び出し直前のイベントに関連する各種リ
     * スナを設定する。
     *
     * 設定できるリスナのタイプとコールバックイベントは以下の通り
     * <ul>
     * <li> PRE_ON_INITIALIZE:    onInitialize 直前
     * <li> PRE_ON_FINALIZE:      onFinalize 直前
     * <li> PRE_ON_STARTUP:       onStartup 直前
     * <li> PRE_ON_SHUTDOWN:      onShutdown 直前
     * <li> PRE_ON_ACTIVATED:     onActivated 直前
     * <li> PRE_ON_DEACTIVATED:   onDeactivated 直前
     * <li> PRE_ON_ABORTED:       onAborted 直前
     * <li> PRE_ON_ERROR:         onError 直前
     * <li> PRE_ON_RESET:         onReset 直前
     * <li> PRE_ON_EXECUTE:       onExecute 直前
     * <li> PRE_ON_STATE_UPDATE:  onStateUpdate 直前
     * </ul>
     *
     * リスナは PreComponentActionListener を継承し、以下のシグニチャを持つ
     * operator() を実装している必要がある。
     *
     * PreComponentActionListener::operator()(int ec_id)
     *
     * デフォルトでは、この関数に与えたリスナオブジェクトの所有権は
     * RTObjectに移り、RTObject解体時もしくは、
     * removePreComponentActionListener() により削除時に自動的に解体される。
     * リスナオブジェクトの所有権を呼び出し側で維持したい場合は、第3引
     * 数に false を指定し、自動的な解体を抑制することができる。}
     * {@.en This operation adds certain listeners related to ComponentActions
     * pre events.
     * The following listener types are available.
     * <ul>
     * <li> PRE_ON_INITIALIZE:    before onInitialize
     * <li> PRE_ON_FINALIZE:      before onFinalize
     * <li> PRE_ON_STARTUP:       before onStartup
     * <li> PRE_ON_SHUTDOWN:      before onShutdown
     * <li> PRE_ON_ACTIVATED:     before onActivated
     * <li> PRE_ON_DEACTIVATED:   before onDeactivated
     * <li> PRE_ON_ABORTED:       before onAborted
     * <li> PRE_ON_ERROR:         before onError
     * <li> PRE_ON_RESET:         before onReset
     * <li> PRE_ON_EXECUTE:       before onExecute
     * <li> PRE_ON_STATE_UPDATE:  before onStateUpdate
     * </ul>
     *
     * Listeners should have the following function operator().
     *
     * PreComponentActionListener::operator()(int ec_id)
     *
     * The ownership of the given listener object is transferred to
     * this RTObject object in default.  The given listener object will
     * be destroied automatically in the RTObject's dtor or if the
     * listener is deleted by removePreComponentActionListener() function.
     * If you want to keep ownership of the listener object, give
     * "false" value to 3rd argument to inhibit automatic destruction.}
     * 
     * @param listener_type 
     *   {@.ja リスナタイプ}
     *   {@.en A listener type}
     * @param obj 
     *   {@.ja リスナオブジェクト}
     *   {@.en listener object}
     * @param memfunc 
     *   {@.ja リスナのmethod名}
     *   {@.en Method name of listener}
     */
    public <DataType> 
    PreComponentActionListener
    addPreComponentActionListener(int listener_type,
                                   DataType obj,
                                   String memfunc) {
        class Noname extends PreComponentActionListener {
            public Noname(DataType obj, String memfunc) {
                m_obj = obj;
                try {
                    Class clazz = m_obj.getClass();

                    m_method = clazz.getMethod(memfunc,int.class);

                }
                catch(java.lang.Exception e){
                    rtcout.println(Logbuf.WARN, 
                        "Exception caught."+e.toString());
                }
            }
            public void operator(final int exec_handle) {
                try {
                    m_method.invoke(
                          m_obj,
                          exec_handle);
                }
                catch(java.lang.Exception e){
                    rtcout.println(Logbuf.WARN, 
                        "Exception caught."+e.toString());
                }
            }
            private DataType m_obj;
            private Method m_method;
        };
        Noname listener = new Noname(obj, memfunc);
        addPreComponentActionListener(listener_type, listener, true);
        return listener;
    }


    /**
     * {@.ja PreComponentActionListener リスナを削除する}
     * {@.en Removing PreComponentAction type listener}
     * <p>
     * {@.ja 設定した各種リスナを削除する。}
     * {@.en This operation removes a specified listener.}
     * @param listener_type 
     *   {@.ja リスナタイプ}
     *   {@.en A listener type}
     * @param listener 
     *   {@.ja リスナオブジェクトへのポインタ}
     *   {@.en A pointer to a listener object}
     */
    public void 
    removePreComponentActionListener( int listener_type,
                                      PreComponentActionListener listener){

        if (listener_type < PreComponentActionListenerType.PRE_COMPONENT_ACTION_LISTENER_NUM ) {
            rtcout.println(Logbuf.TRACE,
                    "removePreComponentActionListener("
                    +PreComponentActionListenerType.toString(listener_type)
                    +")");
            m_actionListeners.
              preaction_[listener_type].deleteObserver(listener);
            return;
        }
        rtcout.println(Logbuf.ERROR, 
                "removePreComponentActionListener(): Invalid listener type.");
        return;
    }

    /**
     * {@.ja PostComponentActionListener リスナを追加する}
     * {@.en Adding PostComponentAction type listener}
     * <p>
     * {@.ja ComponentAction 実装関数の呼び出し直後のイベントに関連する各種リ
     * スナを設定する。
     *
     * 設定できるリスナのタイプとコールバックイベントは以下の通り
     * <ul>
     * <li> POST_ON_INITIALIZE:    onInitialize 直後
     * <li> POST_ON_FINALIZE:      onFinalize 直後
     * <li> POST_ON_STARTUP:       onStartup 直後
     * <li> POST_ON_SHUTDOWN:      onShutdown 直後
     * <li> POST_ON_ACTIVATED:     onActivated 直後
     * <li> POST_ON_DEACTIVATED:   onDeactivated 直後
     * <li> POST_ON_ABORTED:       onAborted 直後
     * <li> POST_ON_ERROR:         onError 直後
     * <li> POST_ON_RESET:         onReset 直後
     * <li> POST_ON_EXECUTE:       onExecute 直後
     * <li> POST_ON_STATE_UPDATE:  onStateUpdate 直後
     * </ul>
     *
     * リスナは PostComponentActionListener を継承し、以下のシグニチャを持つ
     * operator() を実装している必要がある。
     *
     * PostComponentActionListener::operator()(int ec_id, ReturnCode_t ret)
     *
     * デフォルトでは、この関数に与えたリスナオブジェクトの所有権は
     * RTObjectに移り、RTObject解体時もしくは、
     * removePostComponentActionListener() により削除時に自動的に解体される。
     * リスナオブジェクトの所有権を呼び出し側で維持したい場合は、第3引
     * 数に false を指定し、自動的な解体を抑制することができる。}
     * {This operation adds certain listeners related to ComponentActions
     * post events.
     * The following listener types are available.
     * <ul>
     * <li> POST_ON_INITIALIZE:    after onInitialize
     * <li> POST_ON_FINALIZE:      after onFinalize
     * <li> POST_ON_STARTUP:       after onStartup
     * <li> POST_ON_SHUTDOWN:      after onShutdown
     * <li> POST_ON_ACTIVATED:     after onActivated
     * <li> POST_ON_DEACTIVATED:   after onDeactivated
     * <li> POST_ON_ABORTED:       after onAborted
     * <li> POST_ON_ERROR:         after onError
     * <li> POST_ON_RESET:         after onReset
     * <li> POST_ON_EXECUTE:       after onExecute
     * <li> POST_ON_STATE_UPDATE:  after onStateUpdate
     * </ul>
     *
     * Listeners should have the following function operator().
     *
     * PostComponentActionListener::operator()(int ec_id, ReturnCode_t ret)
     *
     * The ownership of the given listener object is transferred to
     * this RTObject object in default.  The given listener object will
     * be destroied automatically in the RTObject's dtor or if the
     * listener is deleted by removePostComponentActionListener() function.
     * If you want to keep ownership of the listener object, give
     * "false" value to 3rd argument to inhibit automatic destruction.}
     *
     * @param listener_type 
     *   {@.ja リスナタイプ}
     *   {@.en A listener type}
     * @param listener 
     *   {@.ja リスナオブジェクトへのポインタ}
     *   {@.en A pointer to a listener object}
     * @param autoclean 
     *   {@.ja リスナオブジェクトの自動的解体を行うかどうかのフラグ}
     *   {@.en A flag for automatic listener destruction}
     */
    public void 
    addPostComponentActionListener( int listener_type,
                                    PostComponentActionListener listener,
                                    boolean autoclean){
        if (listener_type < PostComponentActionListenerType.POST_COMPONENT_ACTION_LISTENER_NUM) {
            rtcout.println(Logbuf.TRACE,
                    "addPostComponentActionListener("
                    +PostComponentActionListenerType.toString(listener_type)
                    +")");
            m_actionListeners.postaction_[listener_type].addObserver(listener);
            return;
        }
        rtcout.println(Logbuf.ERROR, 
                    "addPostComponentActionListener(): Invalid listener type.");
        return;
    }
    /**
     * {@.ja PostComponentActionListener リスナを追加する}
     * {@.en Adding PostComponentAction type listener}
     * <p>
     * {@.ja ComponentAction 実装関数の呼び出し直後のイベントに関連する各種リ
     * スナを設定する。
     *
     * 設定できるリスナのタイプとコールバックイベントは以下の通り
     * <ul>
     * <li> POST_ON_INITIALIZE:    onInitialize 直後
     * <li> POST_ON_FINALIZE:      onFinalize 直後
     * <li> POST_ON_STARTUP:       onStartup 直後
     * <li> POST_ON_SHUTDOWN:      onShutdown 直後
     * <li> POST_ON_ACTIVATED:     onActivated 直後
     * <li> POST_ON_DEACTIVATED:   onDeactivated 直後
     * <li> POST_ON_ABORTED:       onAborted 直後
     * <li> POST_ON_ERROR:         onError 直後
     * <li> POST_ON_RESET:         onReset 直後
     * <li> POST_ON_EXECUTE:       onExecute 直後
     * <li> POST_ON_STATE_UPDATE:  onStateUpdate 直後
     * </ul>
     *
     * リスナは PostComponentActionListener を継承し、以下のシグニチャを持つ
     * operator() を実装している必要がある。
     *
     * PostComponentActionListener::operator()(int ec_id, ReturnCode_t ret)
     *
     * デフォルトでは、この関数に与えたリスナオブジェクトの所有権は
     * RTObjectに移り、RTObject解体時もしくは、
     * removePostComponentActionListener() により削除時に自動的に解体される。
     * リスナオブジェクトの所有権を呼び出し側で維持したい場合は、第3引
     * 数に false を指定し、自動的な解体を抑制することができる。}
     * {This operation adds certain listeners related to ComponentActions
     * post events.
     * The following listener types are available.
     * <ul>
     * <li> POST_ON_INITIALIZE:    after onInitialize
     * <li> POST_ON_FINALIZE:      after onFinalize
     * <li> POST_ON_STARTUP:       after onStartup
     * <li> POST_ON_SHUTDOWN:      after onShutdown
     * <li> POST_ON_ACTIVATED:     after onActivated
     * <li> POST_ON_DEACTIVATED:   after onDeactivated
     * <li> POST_ON_ABORTED:       after onAborted
     * <li> POST_ON_ERROR:         after onError
     * <li> POST_ON_RESET:         after onReset
     * <li> POST_ON_EXECUTE:       after onExecute
     * <li> POST_ON_STATE_UPDATE:  after onStateUpdate
     * </ul>
     *
     * Listeners should have the following function operator().
     *
     * PostComponentActionListener::operator()(int ec_id, ReturnCode_t ret)
     *
     * The ownership of the given listener object is transferred to
     * this RTObject object in default.  The given listener object will
     * be destroied automatically in the RTObject's dtor or if the
     * listener is deleted by removePostComponentActionListener() function.
     * If you want to keep ownership of the listener object, give
     * "false" value to 3rd argument to inhibit automatic destruction.}
     *
     * @param listener_type 
     *   {@.ja リスナタイプ}
     *   {@.en A listener type}
     * @param listener 
     *   {@.ja リスナオブジェクトへのポインタ}
     *   {@.en A pointer to a listener object}
     */
    public void 
    addPostComponentActionListener( int listener_type,
                                    PostComponentActionListener listener){
        this.addPostComponentActionListener(listener_type,listener,true);
    }

    /**
     * {@.ja PostComponentActionListener リスナを追加する}
     * {@.en Adding PostComponentAction type listener}
     * <p>
     * {@.ja ComponentAction 実装関数の呼び出し直後のイベントに関連する各種リ
     * スナを設定する。
     *
     * 設定できるリスナのタイプとコールバックイベントは以下の通り
     * <ul>
     * <li> POST_ON_INITIALIZE:    onInitialize 直後
     * <li> POST_ON_FINALIZE:      onFinalize 直後
     * <li> POST_ON_STARTUP:       onStartup 直後
     * <li> POST_ON_SHUTDOWN:      onShutdown 直後
     * <li> POST_ON_ACTIVATED:     onActivated 直後
     * <li> POST_ON_DEACTIVATED:   onDeactivated 直後
     * <li> POST_ON_ABORTED:       onAborted 直後
     * <li> POST_ON_ERROR:         onError 直後
     * <li> POST_ON_RESET:         onReset 直後
     * <li> POST_ON_EXECUTE:       onExecute 直後
     * <li> POST_ON_STATE_UPDATE:  onStateUpdate 直後
     * </ul>
     *
     * リスナは PostComponentActionListener を継承し、以下のシグニチャを持つ
     * operator() を実装している必要がある。
     *
     * PostComponentActionListener::operator()(int ec_id, ReturnCode_t ret)
     *
     * デフォルトでは、この関数に与えたリスナオブジェクトの所有権は
     * RTObjectに移り、RTObject解体時もしくは、
     * removePostComponentActionListener() により削除時に自動的に解体される。
     * リスナオブジェクトの所有権を呼び出し側で維持したい場合は、第3引
     * 数に false を指定し、自動的な解体を抑制することができる。}
     * {This operation adds certain listeners related to ComponentActions
     * post events.
     * The following listener types are available.
     * <ul>
     * <li> POST_ON_INITIALIZE:    after onInitialize
     * <li> POST_ON_FINALIZE:      after onFinalize
     * <li> POST_ON_STARTUP:       after onStartup
     * <li> POST_ON_SHUTDOWN:      after onShutdown
     * <li> POST_ON_ACTIVATED:     after onActivated
     * <li> POST_ON_DEACTIVATED:   after onDeactivated
     * <li> POST_ON_ABORTED:       after onAborted
     * <li> POST_ON_ERROR:         after onError
     * <li> POST_ON_RESET:         after onReset
     * <li> POST_ON_EXECUTE:       after onExecute
     * <li> POST_ON_STATE_UPDATE:  after onStateUpdate
     * </ul>
     *
     * Listeners should have the following function operator().
     *
     * PostComponentActionListener::operator()(int ec_id, ReturnCode_t ret)
     *
     * The ownership of the given listener object is transferred to
     * this RTObject object in default.  The given listener object will
     * be destroied automatically in the RTObject's dtor or if the
     * listener is deleted by removePostComponentActionListener() function.
     * If you want to keep ownership of the listener object, give
     * "false" value to 3rd argument to inhibit automatic destruction.}
     *
     * @param listener_type 
     *   {@.ja リスナタイプ}
     *   {@.en A listener type}
     * @param obj 
     *   {@.ja リスナオブジェクト}
     *   {@.en listener object}
     * @param memfunc 
     *   {@.ja リスナのmethod名}
     *   {@.en Method name of listener}
     */
    public <DataType> 
    PostComponentActionListener
    addPostComponentActionListener(int listener_type,
                                   DataType obj,
                                   String memfunc) {
        class Noname extends PostComponentActionListener {
            public Noname(DataType obj, String memfunc) {
                m_obj = obj;
                try {
                    Class clazz = m_obj.getClass();

                    m_method = clazz.getMethod(memfunc,int.class, RTC.ReturnCode_t.class);

                }
                catch(java.lang.Exception e){
                    rtcout.println(Logbuf.WARN, 
                        "Exception caught."+e.toString());
                }
            }
            public void operator(final int exec_handle, RTC.ReturnCode_t ret) {
                try {
                    m_method.invoke(
                          m_obj,
                          exec_handle, ret);
                }
                catch(java.lang.Exception e){
                    rtcout.println(Logbuf.WARN, 
                        "Exception caught."+e.toString());
                }
            }
            private DataType m_obj;
            private Method m_method;
        };
        Noname listener = new Noname(obj, memfunc);
        addPostComponentActionListener(listener_type, listener, true);
        return listener;
    }


    /**
     * {@.ja PostComponentActionListener リスナを削除する}
     * {@.en Removing PostComponentAction type listener}
     * <p>
     * {@.ja 設定した各種リスナを削除する。}
     * {@.en This operation removes a specified listener.}
     * 
     * @param listener_type 
     *   {@.ja リスナタイプ}
     *   {@.en A listener type}
     * @param listener 
     *   {@.ja リスナオブジェクトへのポインタ}
     *   {@.en A pointer to a listener object}
     */
    public void 
    removePostComponentActionListener( int listener_type,
                                       PostComponentActionListener listener){
        if (listener_type < PostComponentActionListenerType.POST_COMPONENT_ACTION_LISTENER_NUM) {
            rtcout.println(Logbuf.TRACE,
                    "removePostComponentActionListener("
                    +PostComponentActionListenerType.toString(listener_type)
                    +")");
            m_actionListeners.postaction_[listener_type].deleteObserver(listener);
            return;
        }
        rtcout.println(Logbuf.ERROR, 
                "removePostComponentActionListener(): Invalid listener type.");
        return;
    }



    /**
     * {@.ja PortActionListener リスナを追加する}
     * {@.en Adding PortAction type listener}
     * <p>
     * {@.ja Portの追加、削除時にコールバックされる各種リスナを設定する。
     * 
     * 設定できるリスナのタイプとコールバックイベントは以下の通り
     * <ul>
     * <li> ADD_PORT:    Port追加時
     * <li> REMOVE_PORT: Port削除時
     *
     * リスナは PortActionListener を継承し、以下のシグニチャを持つ
     * operator() を実装している必要がある。
     *
     * PortActionListener::operator()(PortProfile& pprof)
     *
     * デフォルトでは、この関数に与えたリスナオブジェクトの所有権は
     * RTObjectに移り、RTObject解体時もしくは、
     * removePortActionListener() により削除時に自動的に解体される。
     * リスナオブジェクトの所有権を呼び出し側で維持したい場合は、第3引
     * 数に false を指定し、自動的な解体を抑制することができる。}
     * {@.en This operation adds certain listeners related to ComponentActions
     * post events.
     * The following listener types are available.
     * <ul>
     * <li> ADD_PORT:    At adding Port
     * <li> REMOVE_PORT: At removing Port
     * </ul>
     * Listeners should have the following function operator().
     *
     * PortActionListener::operator()(RTC::PortProfile pprof)
     *
     * The ownership of the given listener object is transferred to
     * this RTObject object in default.  The given listener object will
     * be destroied automatically in the RTObject's dtor or if the
     * listener is deleted by removePortActionListener() function.
     * If you want to keep ownership of the listener object, give
     * "false" value to 3rd argument to inhibit automatic destruction.}
     *
     * @param listener_type 
     *   {@.ja リスナタイプ}
     *   {@.en A listener type}
     * @param listener 
     *   {@.ja リスナオブジェクトへのポインタ}
     *   {@.en A pointer to a listener object}
     * @param autoclean 
     *   {@.ja リスナオブジェクトの自動的解体を行うかどうかのフラグ}
     *   {@.en A flag for automatic listener destruction}
     *
     */
    public void 
    addPortActionListener(int listener_type,
                          PortActionListener listener,
                          boolean autoclean){
        if (listener_type < PortActionListenerType.PORT_ACTION_LISTENER_NUM) {
            rtcout.println(Logbuf.TRACE,
                    "addPortActionListener("
                    +PortActionListenerType.toString(listener_type)
                    +")");
            m_actionListeners.portaction_[listener_type].addObserver(listener);
            return;
        }
        rtcout.println(Logbuf.ERROR, 
                    "addPortActionListener(): Invalid listener type.");
        return;
    }
    /**
     * {@.ja PortActionListener リスナを追加する}
     * {@.en Adding PortAction type listener}
     * <p>
     * {@.ja Portの追加、削除時にコールバックされる各種リスナを設定する。
     * 
     * 設定できるリスナのタイプとコールバックイベントは以下の通り
     * <ul>
     * <li> ADD_PORT:    Port追加時
     * <li> REMOVE_PORT: Port削除時
     *
     * リスナは PortActionListener を継承し、以下のシグニチャを持つ
     * operator() を実装している必要がある。
     *
     * PortActionListener::operator()(PortProfile& pprof)
     *
     * デフォルトでは、この関数に与えたリスナオブジェクトの所有権は
     * RTObjectに移り、RTObject解体時もしくは、
     * removePortActionListener() により削除時に自動的に解体される。
     * リスナオブジェクトの所有権を呼び出し側で維持したい場合は、第3引
     * 数に false を指定し、自動的な解体を抑制することができる。}
     * {@.en This operation adds certain listeners related to ComponentActions
     * post events.
     * The following listener types are available.
     * <ul>
     * <li> ADD_PORT:    At adding Port
     * <li> REMOVE_PORT: At removing Port
     * </ul>
     * Listeners should have the following function operator().
     *
     * PortActionListener::operator()(RTC::PortProfile pprof)
     *
     * The ownership of the given listener object is transferred to
     * this RTObject object in default.  The given listener object will
     * be destroied automatically in the RTObject's dtor or if the
     * listener is deleted by removePortActionListener() function.
     * If you want to keep ownership of the listener object, give
     * "false" value to 3rd argument to inhibit automatic destruction.}
     *
     * @param listener_type 
     *   {@.ja リスナタイプ}
     *   {@.en A listener type}
     * @param listener 
     *   {@.ja リスナオブジェクトへのポインタ}
     *   {@.en A pointer to a listener object}
     *
     */
    public void 
    addPortActionListener(int listener_type,
                          PortActionListener listener){
        this.addPortActionListener(listener_type,listener,true);
    }
    
    /**
     * {@.ja PortActionListener リスナを追加する}
     * {@.en Adding PortAction type listener}
     * <p>
     * {@.ja Portの追加、削除時にコールバックされる各種リスナを設定する。
     * 
     * 設定できるリスナのタイプとコールバックイベントは以下の通り
     * <ul>
     * <li> ADD_PORT:    Port追加時
     * <li> REMOVE_PORT: Port削除時
     *
     * リスナは PortActionListener を継承し、以下のシグニチャを持つ
     * operator() を実装している必要がある。
     *
     * PortActionListener::operator()(PortProfile& pprof)
     *
     * デフォルトでは、この関数に与えたリスナオブジェクトの所有権は
     * RTObjectに移り、RTObject解体時もしくは、
     * removePortActionListener() により削除時に自動的に解体される。
     * リスナオブジェクトの所有権を呼び出し側で維持したい場合は、第3引
     * 数に false を指定し、自動的な解体を抑制することができる。}
     * {@.en This operation adds certain listeners related to ComponentActions
     * post events.
     * The following listener types are available.
     * <ul>
     * <li> ADD_PORT:    At adding Port
     * <li> REMOVE_PORT: At removing Port
     * </ul>
     * Listeners should have the following function operator().
     *
     * PortActionListener::operator()(RTC::PortProfile pprof)
     *
     * The ownership of the given listener object is transferred to
     * this RTObject object in default.  The given listener object will
     * be destroied automatically in the RTObject's dtor or if the
     * listener is deleted by removePortActionListener() function.
     * If you want to keep ownership of the listener object, give
     * "false" value to 3rd argument to inhibit automatic destruction.}
     *
     * @param listener_type 
     *   {@.ja リスナタイプ}
     *   {@.en A listener type}
     * @param obj 
     *   {@.ja リスナオブジェクト}
     *   {@.en listener object}
     * @param memfunc 
     *   {@.ja リスナのmethod名}
     *   {@.en Method name of listener}
     *
     */
    public <DataType> 
    PortActionListener
    addPortActionListener(int listener_type,
                                   DataType obj,
                                   String memfunc) {
        class Noname extends PortActionListener {
            public Noname(DataType obj, String memfunc) {
                m_obj = obj;
                try {
                    Class clazz = m_obj.getClass();

                    m_method = clazz.getMethod(memfunc,RTC.PortProfile.class);

                }
                catch(java.lang.Exception e){
                    rtcout.println(Logbuf.WARN, 
                        "Exception caught."+e.toString());
                }
            }
            public void operator(final RTC.PortProfile prof) {
                try {
                    m_method.invoke(
                          m_obj,
                          prof);
                }
                catch(java.lang.Exception e){
                    rtcout.println(Logbuf.WARN, 
                        "Exception caught."+e.toString());
                }
            }
            private DataType m_obj;
            private Method m_method;
        };
        Noname listener = new Noname(obj, memfunc);
        addPortActionListener(listener_type, listener, true);
        return listener;
    }


  

    /**
     * {@.ja PortActionListener リスナを削除する}
     * {@.en Removing PortAction type listener}
     * <p>
     * {@.ja 設定した各種リスナを削除する。}
     * {@.en This operation removes a specified listener.}
     * 
     * @param listener_type 
     *   {@.ja リスナタイプ}
     *   {@.en A listener type}
     * @param listener 
     *   {@.ja リスナオブジェクトへのポインタ}
     *   {@.en A pointer to a listener object}
     */
    public void 
    removePortActionListener(int listener_type,
                             PortActionListener listener)
    {
        if (listener_type < PortActionListenerType.PORT_ACTION_LISTENER_NUM) {
            rtcout.println(Logbuf.TRACE,
                    "removePortActionListener("
                    +PortActionListenerType.toString(listener_type)
                    +")");
            m_actionListeners.portaction_[listener_type].deleteObserver(listener);
            return;
        }
        rtcout.println(Logbuf.ERROR, 
                    "removePortActionListener(): Invalid listener type.");
        return;
    }



    /**
     * {@.ja ExecutionContextActionListener リスナを追加する}
     * {@.en Adding ExecutionContextAction type listener}
     * <p>
     * {@.ja ExecutionContextの追加、削除時にコールバックされる各種リスナを設定する。
     * 
     * 設定できるリスナのタイプとコールバックイベントは以下の通り
     * <ul>
     * <li> ATTACH_EC:    ExecutionContext アタッチ時
     * <li> DETACH_EC:    ExecutionContext デタッチ時
     * </ul>
     * リスナは ExecutionContextActionListener を継承し、以下のシグニチャを持つ
     * operator() を実装している必要がある。
     *
     * ExecutionContextActionListener::operator()(int　ec_id)
     *
     * デフォルトでは、この関数に与えたリスナオブジェクトの所有権は
     * RTObjectに移り、RTObject解体時もしくは、
     * removeExecutionContextActionListener() により削除時に自動的に解体される。
     * リスナオブジェクトの所有権を呼び出し側で維持したい場合は、第3引
     * 数に false を指定し、自動的な解体を抑制することができる。}
     * {@.en This operation adds certain listeners related to ComponentActions
     * post events.
     * The following listener types are available.
     * <ul>
     * <li> ADD_PORT:    At adding ExecutionContext
     * <li> REMOVE_PORT: At removing ExecutionContext
     * </ul>
     * Listeners should have the following function operator().
     *
     * ExecutionContextActionListener::operator()(int ec_id)
     *
     * The ownership of the given listener object is transferred to
     * this RTObject object in default.  The given listener object will
     * be destroied automatically in the RTObject's dtor or if the
     * listener is deleted by removeExecutionContextActionListener() function.
     * If you want to keep ownership of the listener object, give
     * "false" value to 3rd argument to inhibit automatic destruction.}
     *
     * @param listener_type 
     *   {@.ja リスナタイプ}
     *   {@.en A listener type}
     * @param listener 
     *   {@.ja リスナオブジェクトへのポインタ}
     *   {@.en A pointer to a listener object}
     * @param autoclean 
     *   {@.ja リスナオブジェクトの自動的解体を行うかどうかのフラグ}
     *   {@.en A flag for automatic listener destruction}
     *
     *
     */
    public void 
    addExecutionContextActionListener( int listener_type,
                                       ExecutionContextActionListener listener,
                                       boolean autoclean)
    {
        if (listener_type < ExecutionContextActionListenerType.EC_ACTION_LISTENER_NUM) {
            rtcout.println(Logbuf.TRACE,
                    "addExecutionContextActionListener("
                    +ExecutionContextActionListenerType.toString(listener_type)
                    +")");
            m_actionListeners.ecaction_[listener_type].addObserver(listener);
            return;
        }
        rtcout.println(Logbuf.ERROR, 
                    "addExecutionContextActionListener(): Invalid listener type.");
        return;
    }
    /**
     * {@.ja ExecutionContextActionListener リスナを追加する}
     * {@.en Adding ExecutionContextAction type listener}
     * <p>
     * {@.ja ExecutionContextの追加、削除時にコールバックされる各種リスナを設定する。
     * 
     * 設定できるリスナのタイプとコールバックイベントは以下の通り
     * <ul>
     * <li> ATTACH_EC:    ExecutionContext アタッチ時
     * <li> DETACH_EC:    ExecutionContext デタッチ時
     * </ul>
     * リスナは ExecutionContextActionListener を継承し、以下のシグニチャを持つ
     * operator() を実装している必要がある。
     *
     * ExecutionContextActionListener::operator()(int　ec_id)
     *
     * デフォルトでは、この関数に与えたリスナオブジェクトの所有権は
     * RTObjectに移り、RTObject解体時もしくは、
     * removeExecutionContextActionListener() により削除時に自動的に解体される。
     * リスナオブジェクトの所有権を呼び出し側で維持したい場合は、第3引
     * 数に false を指定し、自動的な解体を抑制することができる。}
     * {@.en This operation adds certain listeners related to ComponentActions
     * post events.
     * The following listener types are available.
     * <ul>
     * <li> ADD_PORT:    At adding ExecutionContext
     * <li> REMOVE_PORT: At removing ExecutionContext
     * </ul>
     * Listeners should have the following function operator().
     *
     * ExecutionContextActionListener::operator()(int ec_id)
     *
     * The ownership of the given listener object is transferred to
     * this RTObject object in default.  The given listener object will
     * be destroied automatically in the RTObject's dtor or if the
     * listener is deleted by removeExecutionContextActionListener() function.
     * If you want to keep ownership of the listener object, give
     * "false" value to 3rd argument to inhibit automatic destruction.}
     *
     * @param listener_type 
     *   {@.ja リスナタイプ}
     *   {@.en A listener type}
     * @param listener 
     *   {@.ja リスナオブジェクトへのポインタ}
     *   {@.en A pointer to a listener object}
     *
     *
     */
    public void 
    addExecutionContextActionListener( int listener_type,
                                       ExecutionContextActionListener listener)
    {
        this.addExecutionContextActionListener(listener_type,listener, true);
    }
    /**
     * {@.ja ExecutionContextActionListener リスナを追加する}
     * {@.en Adding ExecutionContextAction type listener}
     * <p>
     * {@.ja ExecutionContextの追加、削除時にコールバックされる各種リスナを設定する。
     * 
     * 設定できるリスナのタイプとコールバックイベントは以下の通り
     * <ul>
     * <li> ATTACH_EC:    ExecutionContext アタッチ時
     * <li> DETACH_EC:    ExecutionContext デタッチ時
     * </ul>
     * リスナは ExecutionContextActionListener を継承し、以下のシグニチャを持つ
     * operator() を実装している必要がある。
     *
     * ExecutionContextActionListener::operator()(int　ec_id)
     *
     * デフォルトでは、この関数に与えたリスナオブジェクトの所有権は
     * RTObjectに移り、RTObject解体時もしくは、
     * removeExecutionContextActionListener() により削除時に自動的に解体される。
     * リスナオブジェクトの所有権を呼び出し側で維持したい場合は、第3引
     * 数に false を指定し、自動的な解体を抑制することができる。}
     * {@.en This operation adds certain listeners related to ComponentActions
     * post events.
     * The following listener types are available.
     * <ul>
     * <li> ADD_PORT:    At adding ExecutionContext
     * <li> REMOVE_PORT: At removing ExecutionContext
     * </ul>
     * Listeners should have the following function operator().
     *
     * ExecutionContextActionListener::operator()(int ec_id)
     *
     * The ownership of the given listener object is transferred to
     * this RTObject object in default.  The given listener object will
     * be destroied automatically in the RTObject's dtor or if the
     * listener is deleted by removeExecutionContextActionListener() function.
     * If you want to keep ownership of the listener object, give
     * "false" value to 3rd argument to inhibit automatic destruction.}
     *
     * @param listener_type 
     *   {@.ja リスナタイプ}
     *   {@.en A listener type}
     * @param obj 
     *   {@.ja リスナオブジェクト}
     *   {@.en listener object}
     * @param memfunc 
     *   {@.ja リスナのmethod名}
     *   {@.en Method name of listener}
     *
     *
     */
    public <DataType> 
    ExecutionContextActionListener
    addExecutionContextActionListener(int listener_type,
                                   DataType obj,
                                   String memfunc) {
        class Noname extends ExecutionContextActionListener {
            public Noname(DataType obj, String memfunc) {
                m_obj = obj;
                try {
                    Class clazz = m_obj.getClass();

                    m_method = clazz.getMethod(memfunc,int.class);

                }
                catch(java.lang.Exception e){
                    rtcout.println(Logbuf.WARN, 
                        "Exception caught."+e.toString());
                }
            }
            public void operator(final int ec_id) {
                try {
                    m_method.invoke(
                          m_obj,
                          ec_id);
                }
                catch(java.lang.Exception e){
                    rtcout.println(Logbuf.WARN, 
                        "Exception caught."+e.toString());
                }
            }
            private DataType m_obj;
            private Method m_method;
        };
        Noname listener = new Noname(obj, memfunc);
        addExecutionContextActionListener(listener_type, listener, true);
        return listener;
    }
    

    /**
     * {@.ja ExecutionContextActionListener リスナを削除する}
     * {@.en @brief Removing ExecutionContextAction type listener}
     * <p>
     * {@.ja 設定した各種リスナを削除する。}
     * {@.en This operation removes a specified listener.}
     * 
     * @param listener_type 
     *   {@.ja リスナタイプ}
     *   {@.en A listener type}
     * @param listener 
     *   {@.ja リスナオブジェクトへのポインタ}
     *   {@.en A pointer to a listener object}
     *
     */
    public void 
    removeExecutionContextActionListener( int listener_type,
                                     ExecutionContextActionListener listener)
    {
        if (listener_type < ExecutionContextActionListenerType.EC_ACTION_LISTENER_NUM) {
            rtcout.println(Logbuf.TRACE,
                    "removeExecutionContextActionListener("
                    +ExecutionContextActionListenerType.toString(listener_type)
                    +")");
            m_actionListeners.ecaction_[listener_type].deleteObserver(listener);
            return;
        }
        rtcout.println(Logbuf.ERROR, 
                    "removeexecutionContextActionListener(): Invalid listener type.");
        return;
    }

    /**
     * {@.ja PortConnectListener リスナを追加する}
     * {@.en Adding PortConnect type listener}
     * <p>
     * {@.ja Portの接続時や接続解除時に呼び出される各種リスナを設定する。
     *
     * 設定できるリスナのタイプとコールバックイベントは以下の通り
     *
     * - ON_NOTIFY_CONNECT: notify_connect() 関数内呼び出し直後
     * - ON_NOTIFY_DISCONNECT: notify_disconnect() 呼び出し直後
     * - ON_UNSUBSCRIBE_INTERFACES: notify_disconnect() 内のIF購読解除時
     *
     * リスナは PortConnectListener を継承し、以下のシグニチャを持つ
     * operator() を実装している必要がある。
     *
     * PortConnectListener::operator()(const char*, ConnectorProfile)
     *
     * デフォルトでは、この関数に与えたリスナオブジェクトの所有権は
     * RTObjectに移り、RTObject解体時もしくは、
     * removePortConnectListener() により削除時に自動的に解体される。
     * リスナオブジェクトの所有権を呼び出し側で維持したい場合は、第3引
     * 数に false を指定し、自動的な解体を抑制することができる。}
     * {@.en This operation adds certain listeners related to Port's connect 
     * actions.
     * The following listener types are available.
     *
     * - ON_NOTIFY_CONNECT: right after entering into notify_connect()
     * - ON_NOTIFY_DISCONNECT: right after entering into notify_disconnect()
     * - ON_UNSUBSCRIBE_INTERFACES: unsubscribing IF in notify_disconnect()
     *
     * Listeners should have the following function operator().
     *
     * PortConnectListener::operator()(const char*, ConnectorProfile)
     *
     * The ownership of the given listener object is transferred to
     * this RTObject object in default.  The given listener object will
     * be destroied automatically in the RTObject's dtor or if the
     * listener is deleted by removePortConnectListener() function.
     * If you want to keep ownership of the listener object, give
     * "false" value to 3rd argument to inhibit automatic destruction.}
     *
     * @param listener_type 
     *   {@.ja リスナタイプ}
     *   {@.en A listener type}
     * @param listener 
     *   {@.ja リスナオブジェクトへのポインタ}
     *   {@.en A pointer to a listener object}
     *
     */
    public void addPortConnectListener(int listener_type,
                                           PortConnectListener listener) {
       addPortConnectListener(listener_type, listener, true);
    } 

    /**
     * {@.ja PortConnectListener リスナを追加する}
     * {@.en Adding PortConnect type listener}
     * <p>
     * {@.ja Portの接続時や接続解除時に呼び出される各種リスナを設定する。
     *
     * 設定できるリスナのタイプとコールバックイベントは以下の通り
     *
     * - ON_NOTIFY_CONNECT: notify_connect() 関数内呼び出し直後
     * - ON_NOTIFY_DISCONNECT: notify_disconnect() 呼び出し直後
     * - ON_UNSUBSCRIBE_INTERFACES: notify_disconnect() 内のIF購読解除時
     *
     * リスナは PortConnectListener を継承し、以下のシグニチャを持つ
     * operator() を実装している必要がある。
     *
     * PortConnectListener::operator()(const char*, ConnectorProfile)
     *
     * デフォルトでは、この関数に与えたリスナオブジェクトの所有権は
     * RTObjectに移り、RTObject解体時もしくは、
     * removePortConnectListener() により削除時に自動的に解体される。
     * リスナオブジェクトの所有権を呼び出し側で維持したい場合は、第3引
     * 数に false を指定し、自動的な解体を抑制することができる。}
     * {@.en This operation adds certain listeners related to Port's connect 
     * actions.
     * The following listener types are available.
     *
     * - ON_NOTIFY_CONNECT: right after entering into notify_connect()
     * - ON_NOTIFY_DISCONNECT: right after entering into notify_disconnect()
     * - ON_UNSUBSCRIBE_INTERFACES: unsubscribing IF in notify_disconnect()
     *
     * Listeners should have the following function operator().
     *
     * PortConnectListener::operator()(const char*, ConnectorProfile)
     *
     * The ownership of the given listener object is transferred to
     * this RTObject object in default.  The given listener object will
     * be destroied automatically in the RTObject's dtor or if the
     * listener is deleted by removePortConnectListener() function.
     * If you want to keep ownership of the listener object, give
     * "false" value to 3rd argument to inhibit automatic destruction.}
     *
     * @param listener_type 
     *   {@.ja リスナタイプ}
     *   {@.en A listener type}
     * @param listener 
     *   {@.ja リスナオブジェクトへのポインタ}
     *   {@.en A pointer to a listener object}
     * @param autoclean 
     *   {@.ja リスナオブジェクトの自動的解体を行うかどうかのフラグ}
     *   {@.en A flag for automatic listener destruction}
     *
     */
    public void addPortConnectListener(int listener_type,
                                           PortConnectListener listener,
                                           boolean autoclean) {
        if(listener_type < PortConnectListenerType.PORT_CONNECT_LISTENER_NUM){
            m_portconnListeners.
                portconnect_[listener_type].addObserver(listener);
        }
    }
    /**
     * {@.ja PortConnectListener リスナを追加する}
     * {@.en Adding PortConnect type listener}
     * <p>
     * {@.ja Portの接続時や接続解除時に呼び出される各種リスナを設定する。
     *
     * 設定できるリスナのタイプとコールバックイベントは以下の通り
     *
     * - ON_NOTIFY_CONNECT: notify_connect() 関数内呼び出し直後
     * - ON_NOTIFY_DISCONNECT: notify_disconnect() 呼び出し直後
     * - ON_UNSUBSCRIBE_INTERFACES: notify_disconnect() 内のIF購読解除時
     *
     * リスナは PortConnectListener を継承し、以下のシグニチャを持つ
     * operator() を実装している必要がある。
     *
     * PortConnectListener::operator()(const char*, ConnectorProfile)
     *
     * デフォルトでは、この関数に与えたリスナオブジェクトの所有権は
     * RTObjectに移り、RTObject解体時もしくは、
     * removePortConnectListener() により削除時に自動的に解体される。
     * リスナオブジェクトの所有権を呼び出し側で維持したい場合は、第3引
     * 数に false を指定し、自動的な解体を抑制することができる。}
     * {@.en This operation adds certain listeners related to Port's connect 
     * actions.
     * The following listener types are available.
     *
     * - ON_NOTIFY_CONNECT: right after entering into notify_connect()
     * - ON_NOTIFY_DISCONNECT: right after entering into notify_disconnect()
     * - ON_UNSUBSCRIBE_INTERFACES: unsubscribing IF in notify_disconnect()
     *
     * Listeners should have the following function operator().
     *
     * PortConnectListener::operator()(const char*, ConnectorProfile)
     *
     * The ownership of the given listener object is transferred to
     * this RTObject object in default.  The given listener object will
     * be destroied automatically in the RTObject's dtor or if the
     * listener is deleted by removePortConnectListener() function.
     * If you want to keep ownership of the listener object, give
     * "false" value to 3rd argument to inhibit automatic destruction.}
     *
     * @param listener_type 
     *   {@.ja リスナタイプ}
     *   {@.en A listener type}
     * @param obj 
     *   {@.ja リスナオブジェクト}
     *   {@.en listener object}
     * @param memfunc 
     *   {@.ja リスナのmethod名}
     *   {@.en Method name of listener}
     *
     */
    public <DataType> 
    PortConnectListener
    addPortConnectListener(int listener_type,
                                   DataType obj,
                                   String memfunc) {
        class Noname extends PortConnectListener {
            public Noname(DataType obj, String memfunc) {
                m_obj = obj;
                try {
                    Class clazz = m_obj.getClass();

                    m_method = clazz.getMethod(memfunc,String.class,ConnectorProfile.class);

                }
                catch(java.lang.Exception e){
                    System.out.println("Exception caught."+e.toString());
                    rtcout.println(Logbuf.WARN, 
                        "Exception caught."+e.toString());
                }
            }
            public void operator(final String portname, ConnectorProfile profile) {
                try {
                    m_method.invoke(
                          m_obj,
                          portname,profile);
                }
                catch(java.lang.Exception e){
                    rtcout.println(Logbuf.WARN, 
                        "Exception caught."+e.toString());
                }
            }
            private DataType m_obj;
            private Method m_method;
        };
        Noname listener = new Noname(obj, memfunc);
        addPortConnectListener(listener_type, listener, true);
        return listener;
    }

    /**
     * {@.ja PortConnectListener リスナを削除する}
     * {@.en Removing PortConnect type listener}
     * <p>
     * {@.ja 設定した各種リスナを削除する。}
     * {@.en This operation removes a specified listener.}
     * 
     * @param listener_type 
     *   {@.ja リスナタイプ}
     *   {@.en A listener type}
     * @param listener 
     *   {@.ja リスナオブジェクトへのポインタ}
     *   {@.en A pointer to a listener object}
     *
     */
    public void 
    removePortConnectListener(int listener_type,
                              PortConnectListener listener) {
        if(listener_type < PortConnectListenerType.PORT_CONNECT_LISTENER_NUM){
            m_portconnListeners.
                portconnect_[listener_type].deleteObserver(listener);
        }
    }

    /**
     * {@.ja PortConnectRetListener リスナを追加する}
     * {@.en Adding PortConnectRet type listener}
     * <p>
     * {@.ja Portの接続時や接続解除時に呼び出される各種リスナを設定する。
     *
     * 設定できるリスナのタイプとコールバックイベントは以下の通り
     *
     * - ON_CONNECT_NEXTPORT: notify_connect() 中のカスケード呼び出し直後
     * - ON_SUBSCRIBE_INTERFACES: notify_connect() 中のインターフェース購読直後
     * - ON_CONNECTED: nofity_connect() 接続処理完了時に呼び出される
     * - ON_DISCONNECT_NEXT: notify_disconnect() 中にカスケード呼び出し直後
     * - ON_DISCONNECTED: notify_disconnect() リターン時
     *
     * リスナは PortConnectRetListener を継承し、以下のシグニチャを持つ
     * operator() を実装している必要がある。
     *
     * PortConnectRetListener::operator()(const char*, ConnectorProfile)
     *
     * デフォルトでは、この関数に与えたリスナオブジェクトの所有権は
     * RTObjectに移り、RTObject解体時もしくは、
     * removePortConnectRetListener() により削除時に自動的に解体される。
     * リスナオブジェクトの所有権を呼び出し側で維持したい場合は、第3引
     * 数に false を指定し、自動的な解体を抑制することができる。}
     * {@.en This operation adds certain listeners related to Port's connect 
     * actions.
     * The following listener types are available.
     *
     * - ON_CONNECT_NEXTPORT: after cascade-call in notify_connect()
     * - ON_SUBSCRIBE_INTERFACES: after IF subscribing in notify_connect()
     * - ON_CONNECTED: completed nofity_connect() connection process
     * - ON_DISCONNECT_NEXT: after cascade-call in notify_disconnect()
     * - ON_DISCONNECTED: completed notify_disconnect() disconnection process
     *
     * Listeners should have the following function operator().
     *
     * PortConnectRetListener::operator()(const char*, ConnectorProfile)
     *
     * The ownership of the given listener object is transferred to
     * this RTObject object in default.  The given listener object will
     * be destroied automatically in the RTObject's dtor or if the
     * listener is deleted by removePortConnectRetListener() function.
     * If you want to keep ownership of the listener object, give
     * "false" value to 3rd argument to inhibit automatic destruction.}
     *
     * @param listener_type 
     *   {@.ja リスナタイプ}
     *   {@.en A listener type}
     * @param listener 
     *   {@.ja リスナオブジェクトへのポインタ}
     *   {@.en A pointer to a listener object}
     *
     */
    public void addPortConnectRetListener(int listener_type,
                                           PortConnectRetListener listener) {
        addPortConnectRetListener(listener_type, listener, true);
    }
    /**
     * {@.ja PortConnectRetListener リスナを追加する}
     * {@.en Adding PortConnectRet type listener}
     * <p>
     * {@.ja Portの接続時や接続解除時に呼び出される各種リスナを設定する。
     *
     * 設定できるリスナのタイプとコールバックイベントは以下の通り
     *
     * - ON_CONNECT_NEXTPORT: notify_connect() 中のカスケード呼び出し直後
     * - ON_SUBSCRIBE_INTERFACES: notify_connect() 中のインターフェース購読直後
     * - ON_CONNECTED: nofity_connect() 接続処理完了時に呼び出される
     * - ON_DISCONNECT_NEXT: notify_disconnect() 中にカスケード呼び出し直後
     * - ON_DISCONNECTED: notify_disconnect() リターン時
     *
     * リスナは PortConnectRetListener を継承し、以下のシグニチャを持つ
     * operator() を実装している必要がある。
     *
     * PortConnectRetListener::operator()(const char*, ConnectorProfile)
     *
     * デフォルトでは、この関数に与えたリスナオブジェクトの所有権は
     * RTObjectに移り、RTObject解体時もしくは、
     * removePortConnectRetListener() により削除時に自動的に解体される。
     * リスナオブジェクトの所有権を呼び出し側で維持したい場合は、第3引
     * 数に false を指定し、自動的な解体を抑制することができる。}
     * {@.en This operation adds certain listeners related to Port's connect 
     * actions.
     * The following listener types are available.
     *
     * - ON_CONNECT_NEXTPORT: after cascade-call in notify_connect()
     * - ON_SUBSCRIBE_INTERFACES: after IF subscribing in notify_connect()
     * - ON_CONNECTED: completed nofity_connect() connection process
     * - ON_DISCONNECT_NEXT: after cascade-call in notify_disconnect()
     * - ON_DISCONNECTED: completed notify_disconnect() disconnection process
     *
     * Listeners should have the following function operator().
     *
     * PortConnectRetListener::operator()(const char*, ConnectorProfile)
     *
     * The ownership of the given listener object is transferred to
     * this RTObject object in default.  The given listener object will
     * be destroied automatically in the RTObject's dtor or if the
     * listener is deleted by removePortConnectRetListener() function.
     * If you want to keep ownership of the listener object, give
     * "false" value to 3rd argument to inhibit automatic destruction.}
     *
     * @param listener_type 
     *   {@.ja リスナタイプ}
     *   {@.en A listener type}
     * @param listener 
     *   {@.ja リスナオブジェクトへのポインタ}
     *   {@.en A pointer to a listener object}
     * @param autoclean 
     *   {@.ja リスナオブジェクトの自動的解体を行うかどうかのフラグ}
     *   {@.en A flag for automatic listener destruction}
     *
     */
    public void addPortConnectRetListener(int listener_type,
                                           PortConnectRetListener listener,
                                           boolean autoclean) {
        if(listener_type < PortConnectRetListenerType.PORT_CONNECT_RET_LISTENER_NUM){
            m_portconnListeners.
                portconnret_[listener_type].addObserver(listener);
        }
    }

    /**
     * {@.ja PortConnectRetListener リスナを追加する}
     * {@.en Adding PortConnectRet type listener}
     * <p>
     * {@.ja Portの接続時や接続解除時に呼び出される各種リスナを設定する。
     *
     * 設定できるリスナのタイプとコールバックイベントは以下の通り
     *
     * - ON_CONNECT_NEXTPORT: notify_connect() 中のカスケード呼び出し直後
     * - ON_SUBSCRIBE_INTERFACES: notify_connect() 中のインターフェース購読直後
     * - ON_CONNECTED: nofity_connect() 接続処理完了時に呼び出される
     * - ON_DISCONNECT_NEXT: notify_disconnect() 中にカスケード呼び出し直後
     * - ON_DISCONNECTED: notify_disconnect() リターン時
     *
     * リスナは PortConnectRetListener を継承し、以下のシグニチャを持つ
     * operator() を実装している必要がある。
     *
     * PortConnectRetListener::operator()(const char*, ConnectorProfile)
     *
     * デフォルトでは、この関数に与えたリスナオブジェクトの所有権は
     * RTObjectに移り、RTObject解体時もしくは、
     * removePortConnectRetListener() により削除時に自動的に解体される。
     * リスナオブジェクトの所有権を呼び出し側で維持したい場合は、第3引
     * 数に false を指定し、自動的な解体を抑制することができる。}
     * {@.en This operation adds certain listeners related to Port's connect 
     * actions.
     * The following listener types are available.
     *
     * - ON_CONNECT_NEXTPORT: after cascade-call in notify_connect()
     * - ON_SUBSCRIBE_INTERFACES: after IF subscribing in notify_connect()
     * - ON_CONNECTED: completed nofity_connect() connection process
     * - ON_DISCONNECT_NEXT: after cascade-call in notify_disconnect()
     * - ON_DISCONNECTED: completed notify_disconnect() disconnection process
     *
     * Listeners should have the following function operator().
     *
     * PortConnectRetListener::operator()(const char*, ConnectorProfile)
     *
     * The ownership of the given listener object is transferred to
     * this RTObject object in default.  The given listener object will
     * be destroied automatically in the RTObject's dtor or if the
     * listener is deleted by removePortConnectRetListener() function.
     * If you want to keep ownership of the listener object, give
     * "false" value to 3rd argument to inhibit automatic destruction.}
     *
     * @param listener_type 
     *   {@.ja リスナタイプ}
     *   {@.en A listener type}
     * @param obj 
     *   {@.ja リスナオブジェクト}
     *   {@.en listener object}
     * @param memfunc 
     *   {@.ja リスナのmethod名}
     *   {@.en Method name of listener}
     *
     */
    public <DataType> 
    PortConnectRetListener
    addPortConnectRetListener(int listener_type,
                                   DataType obj,
                                   String memfunc) {
        class Noname extends PortConnectRetListener {
            public Noname(DataType obj, String memfunc) {
                m_obj = obj;
                try {
                    Class clazz = m_obj.getClass();

                    m_method = clazz.getMethod(memfunc,String.class,ConnectorProfile.class,ReturnCode_t.class);

                }
                catch(java.lang.Exception e){
                    rtcout.println(Logbuf.WARN, 
                        "Exception caught."+e.toString());
                }
            }
            public void operator(final String portname,
                            ConnectorProfile profile,
                            ReturnCode_t ret) {
                try {
                    m_method.invoke(
                          m_obj,
                          portname,profile,ret);
                }
                catch(java.lang.Exception e){
                    rtcout.println(Logbuf.WARN, 
                        "Exception caught."+e.toString());
                }
            }
            private DataType m_obj;
            private Method m_method;
        };
        Noname listener = new Noname(obj, memfunc);
        addPortConnectRetListener(listener_type, listener, true);
        return listener;
    }
    /**
     * {@.ja PortConnectRetListener リスナを削除する}
     * {@.en Removing PortConnectRet type listener}
     * <p>
     * {@.ja 設定した各種リスナを削除する。}
     * {@.en This operation removes a specified listener.}
     * 
     * @param listener_type 
     *   {@.ja リスナタイプ}
     *   {@.en A listener type}
     * @param listener 
     *   {@.ja リスナオブジェクトへのポインタ}
     *   {@.en A pointer to a listener object}
     *
     */
    public void 
    removePortConnectRetListener(int listener_type,
                                 PortConnectRetListener listener) {
        if(listener_type < PortConnectRetListenerType.PORT_CONNECT_RET_LISTENER_NUM){
            m_portconnListeners.
                portconnret_[listener_type].deleteObserver(listener);
        }
    }



    /**
     * {@.ja ConfigurationParamListener を追加する}
     * {@.en Adding ConfigurationParamListener}
     * <p>
     * {@.ja update(const char* config_set, const char* config_param) が呼ばれた際に
     * コールされるリスナ ConfigurationParamListener を追加する。
     * type には現在のところ ON_UPDATE_CONFIG_PARAM のみが入る。}
     * {@.en This function adds a listener object which is called when
     * update(const char* config_set, const char* config_param) is
     * called. In the type argument, currently only
     * ON_UPDATE_CONFIG_PARAM is allowed.}
     *
     * @param type 
     *   {@.ja ConfigurationParamListenerType型の値。
     *         ON_UPDATE_CONFIG_PARAM がある。}
     *   {@.en ConfigurationParamListenerType value
     *         ON_UPDATE_CONFIG_PARAM is only allowed.}
     * @param listener 
     *   {@.ja ConfigurationParamListener 型のリスナオブジェクト。}
     *   {@.en ConfigurationParamListener listener object.}
     * @param autoclean 
     *   {@.ja リスナオブジェクトを自動で削除するかどうかのフラグ}
     *   {@.en a flag whether if the listener object autocleaned.}
     *
     */
    public void addConfigurationParamListener(int type,
                                       ConfigurationParamListener listener,
                                       boolean autoclean)
    {
        m_configsets.addConfigurationParamListener(type,listener,autoclean);
    }

    /**
     * {@.ja ConfigurationParamListener を追加する}
     * {@.en Adding ConfigurationParamListener}
     * <p>
     * {@.ja update(const char* config_set, const char* config_param) が呼ばれた際に
     * コールされるリスナ ConfigurationParamListener を追加する。
     * type には現在のところ ON_UPDATE_CONFIG_PARAM のみが入る。}
     * {@.en This function adds a listener object which is called when
     * update(const char* config_set, const char* config_param) is
     * called. In the type argument, currently only
     * ON_UPDATE_CONFIG_PARAM is allowed.}
     *
     * @param type 
     *   {@.ja ConfigurationParamListenerType型の値。
     *         ON_UPDATE_CONFIG_PARAM がある。}
     *   {@.en ConfigurationParamListenerType value
     *         ON_UPDATE_CONFIG_PARAM is only allowed.}
     * @param listener 
     *   {@.ja ConfigurationParamListener 型のリスナオブジェクト。}
     *   {@.en ConfigurationParamListener listener object.}
     *
     */
    public void addConfigurationParamListener(int type,
                                       ConfigurationParamListener listener)
    {
        addConfigurationParamListener(type,listener,true);
    }

    /**
     * {@.ja ConfigurationParamListener を追加する}
     * {@.en Adding ConfigurationParamListener}
     * <p>
     * {@.ja update(const char* config_set, const char* config_param) が呼ばれた際に
     * コールされるリスナ ConfigurationParamListener を追加する。
     * type には現在のところ ON_UPDATE_CONFIG_PARAM のみが入る。}
     * {@.en This function adds a listener object which is called when
     * update(const char* config_set, const char* config_param) is
     * called. In the type argument, currently only
     * ON_UPDATE_CONFIG_PARAM is allowed.}
     *
     * @param listener_type  
     *   {@.ja ConfigurationParamListenerType型の値。
     *         ON_UPDATE_CONFIG_PARAM がある。}
     *   {@.en ConfigurationParamListenerType value
     *         ON_UPDATE_CONFIG_PARAM is only allowed.}
     * @param obj 
     *   {@.ja リスナオブジェクト}
     *   {@.en listener object}
     * @param memfunc 
     *   {@.ja リスナのmethod名}
     *   {@.en Method name of listener}
     *
     */
    public <DataType> 
    ConfigurationParamListener
    addConfigurationParamListener(int listener_type,
                                   DataType obj,
                                   String memfunc) {
        class Noname extends ConfigurationParamListener {
            public Noname(DataType obj, String memfunc) {
                m_obj = obj;
                try {
                    Class clazz = m_obj.getClass();

                    m_method = clazz.getMethod(memfunc,String.class,String.class);

                }
                catch(java.lang.Exception e){
                    rtcout.println(Logbuf.WARN, 
                        "Exception caught."+e.toString());
                }
            }
            public void operator(final String config_set_name,final String config_param_name) {
                try {
                    m_method.invoke(
                          m_obj,
                          config_set_name,config_param_name);
                }
                catch(java.lang.Exception e){
                    rtcout.println(Logbuf.WARN, 
                        "Exception caught."+e.toString());
                }
            }
            private DataType m_obj;
            private Method m_method;
        };
        Noname listener = new Noname(obj, memfunc);
        addConfigurationParamListener(listener_type, listener, true);
        return listener;
    }

    /**
     * {@.ja ConfigurationParamListener を削除する}
     * {@.en Removing ConfigurationParamListener}
     * <p>
     * {@.ja addConfigurationParamListener で追加されたリスナオブジェクトを削除する。}
     * {@.en This function removes a listener object which is added by
     * addConfigurationParamListener() function.}
     *
     * @param type 
     *   {@.ja ConfigurationParamListenerType型の値。
     *         ON_UPDATE_CONFIG_PARAM がある。}
     *   {@.en ConfigurationParamListenerType value
     *         ON_UPDATE_CONFIG_PARAM is only allowed.}
     * @param listener 
     *   {@.ja 与えたリスナオブジェクトへのポインタ}
     *   {@.en a pointer to ConfigurationParamListener listener object.}
     */
    public void removeConfigurationParamListener(int type,
                                          ConfigurationParamListener listener)
    {
        m_configsets.removeConfigurationParamListener(type,listener);
    }
    /**
     * {@.ja ConfigurationSetListener を追加する}
     * {@.en Adding ConfigurationSetListener }
     * <p>
     * {@.ja ConfigurationSet が更新されたときなどに呼ばれるリスナ
     * ConfigurationSetListener を追加する。設定可能なイベントは以下の
     * 2種類がある。
     * <ul>
     * <li> ON_SET_CONFIG_SET: setConfigurationSetValues() で
     *                      ConfigurationSet に値が設定された場合。</li>
     * <li> ON_ADD_CONFIG_SET: addConfigurationSet() で新しい
     *                      ConfigurationSet が追加された場合。</li></ul>}
     * {@.en This function add a listener object which is called when
     * ConfigurationSet is updated. Available events are the followings.}
     *
     * @param type 
     *   {@.ja ConfigurationSetListenerType型の値。}
     *   {@.en ConfigurationSetListenerType value}
     * @param listener 
     *   {@.ja ConfigurationSetListener 型のリスナオブジェクト。}
     *   {@.en ConfigurationSetListener listener object.}
     * @param autoclean 
     *   {@.ja リスナオブジェクトを自動で削除するかどうかのフラグ}
     *   {@.en a flag whether if the listener object autocleaned.}
     */
    public void addConfigurationSetListener(int type,
                                     ConfigurationSetListener listener,
                                     boolean autoclean)
    {
        m_configsets.addConfigurationSetListener(type,listener,autoclean);
    }
    /**
     * {@.ja ConfigurationSetListener を追加する}
     * {@.en Adding ConfigurationSetListener }
     * <p>
     * {@.ja ConfigurationSet が更新されたときなどに呼ばれるリスナ
     * ConfigurationSetListener を追加する。設定可能なイベントは以下の
     * 2種類がある。
     * <ul>
     * <li> ON_SET_CONFIG_SET: setConfigurationSetValues() で
     *                      ConfigurationSet に値が設定された場合。</li>
     * <li> ON_ADD_CONFIG_SET: addConfigurationSet() で新しい
     *                      ConfigurationSet が追加された場合。</li></ul>}
     * {@.en This function add a listener object which is called when
     * ConfigurationSet is updated. Available events are the followings.}
     *
     * @param type 
     *   {@.ja ConfigurationSetListenerType型の値。}
     *   {@.en ConfigurationSetListenerType value}
     * @param listener 
     *   {@.ja ConfigurationSetListener 型のリスナオブジェクト。}
     *   {@.en ConfigurationSetListener listener object.}
     */
    public void addConfigurationSetListener(int type,
                                     ConfigurationSetListener listener)
    {
        addConfigurationSetListener(type,listener,true);
    }
    /**
     * {@.ja ConfigurationSetListener を追加する}
     * {@.en Adding ConfigurationSetListener }
     * <p>
     * {@.ja ConfigurationSet が更新されたときなどに呼ばれるリスナ
     * ConfigurationSetListener を追加する。設定可能なイベントは以下の
     * 2種類がある。
     * <ul>
     * <li> ON_SET_CONFIG_SET: setConfigurationSetValues() で
     *                      ConfigurationSet に値が設定された場合。</li>
     * <li> ON_ADD_CONFIG_SET: addConfigurationSet() で新しい
     *                      ConfigurationSet が追加された場合。</li></ul>}
     * {@.en This function add a listener object which is called when
     * ConfigurationSet is updated. Available events are the followings.}
     *
     * @param listener_type 
     *   {@.ja ConfigurationSetListenerType型の値。}
     *   {@.en ConfigurationSetListenerType value}
     * @param obj 
     *   {@.ja リスナオブジェクト}
     *   {@.en listener object}
     * @param memfunc 
     *   {@.ja リスナのmethod名}
     *   {@.en Method name of listener}
     */
    public <DataType> 
    ConfigurationSetListener
    addConfigurationSetListener(int listener_type,
                                   DataType obj,
                                   String memfunc) {
        class Noname extends ConfigurationSetListener {
            public Noname(DataType obj, String memfunc) {
                m_obj = obj;
                try {
                    Class clazz = m_obj.getClass();

                    m_method = clazz.getMethod(memfunc,Properties.class);

                }
                catch(java.lang.Exception e){
                    rtcout.println(Logbuf.WARN, 
                        "Exception caught."+e.toString());
                }
            }
            public void operator(final Properties config_set) {
                try {
                    m_method.invoke(
                          m_obj,
                          config_set);
                }
                catch(java.lang.Exception e){
                    rtcout.println(Logbuf.WARN, 
                        "Exception caught."+e.toString());
                }
            }
            private DataType m_obj;
            private Method m_method;
        };
        Noname listener = new Noname(obj, memfunc);
        addConfigurationSetListener(listener_type, listener, true);
        return listener;
    }

    /**
     * {@.ja ConfigurationSetListener を削除する}
     * {@.en Removing ConfigurationSetListener}
     * <p>
     * {@.ja addConfigurationSetListener で追加されたリスナオブジェクトを削除する。}
     * {@.en This function removes a listener object which is added by
     * addConfigurationSetListener() function.}
     *
     * @param type 
     *   {@.ja ConfigurationSetListenerType型の値。}
     *   {@.en ConfigurationSetListenerType value}
     * @param listener
     *   {@.ja 与えたリスナオブジェクトへのポインタ}
     *   {@.en a pointer to ConfigurationSetListener listener object.}
     *
     */
    public void removeConfigurationSetListener(int type,
                                        ConfigurationSetListener listener)
    {
        m_configsets.removeConfigurationSetListener(type,listener);
    }
    /**
     * {@.ja ConfigurationSetNameListener を追加する}
     * {@.en Adding ConfigurationSetNameListener}
     * <p>
     * {@.ja ConfigurationSetName が更新されたときなどに呼ばれるリスナ
     * ConfigurationSetNameListener を追加する。設定可能なイベントは以下の
     * 3種類がある。
     * <ul>
     * <li> ON_UPDATE_CONFIG_SET: ある ConfigurationSet がアップデートされた
     * <li> ON_REMOVE_CONFIG_SET: ある ConfigurationSet が削除された
     * <li> ON_ACTIVATE_CONFIG_SET: ある ConfigurationSet がアクティブ化された
     * </ul>}
     * {@.en This function add a listener object which is called when
     * ConfigurationSetName is updated. Available events are the followings.
     * <ul>
     * <li> ON_UPDATE_CONFIG_SET: A ConfigurationSet has been updated.
     * <li> ON_REMOVE_CONFIG_SET: A ConfigurationSet has been deleted.
     * <li> ON_ACTIVATE_CONFIG_SET: A ConfigurationSet has been activated.
     * </ul>}
     * @param type 
     *   {@.ja ConfigurationSetNameListenerType型の値。}
     *   {@.en ConfigurationSetNameListenerType value}
     * @param listener 
     *   {@.ja ConfigurationSetNameListener 型のリスナオブジェクト。}
     *   {@.en ConfigurationSetNameListener listener object.}
     * @param autoclean 
     *   {@.ja リスナオブジェクトを自動で削除するかどうかのフラグ}
     *   {@.en a flag whether if the listener object autocleaned.}
     */
    public void 
    addConfigurationSetNameListener(int type,
                                    ConfigurationSetNameListener listener,
                                    boolean autoclean)
    {
        m_configsets.addConfigurationSetNameListener(type,listener,autoclean);
    }
    /**
     * {@.ja ConfigurationSetNameListener を追加する}
     * {@.en Adding ConfigurationSetNameListener}
     * <p>
     * {@.ja ConfigurationSetName が更新されたときなどに呼ばれるリスナ
     * ConfigurationSetNameListener を追加する。設定可能なイベントは以下の
     * 3種類がある。
     * <ul>
     * <li> ON_UPDATE_CONFIG_SET: ある ConfigurationSet がアップデートされた
     * <li> ON_REMOVE_CONFIG_SET: ある ConfigurationSet が削除された
     * <li> ON_ACTIVATE_CONFIG_SET: ある ConfigurationSet がアクティブ化された
     * </ul>}
     * {@.en This function add a listener object which is called when
     * ConfigurationSetName is updated. Available events are the followings.
     * <ul>
     * <li> ON_UPDATE_CONFIG_SET: A ConfigurationSet has been updated.
     * <li> ON_REMOVE_CONFIG_SET: A ConfigurationSet has been deleted.
     * <li> ON_ACTIVATE_CONFIG_SET: A ConfigurationSet has been activated.
     * </ul>}
     * @param type 
     *   {@.ja ConfigurationSetNameListenerType型の値。}
     *   {@.en ConfigurationSetNameListenerType value}
     * @param listener 
     *   {@.ja ConfigurationSetNameListener 型のリスナオブジェクト。}
     *   {@.en ConfigurationSetNameListener listener object.}
     */
    public void 
    addConfigurationSetNameListener(int type,
                                    ConfigurationSetNameListener listener)
    {
        addConfigurationSetNameListener(type,listener, true);
    }
    /**
     * {@.ja ConfigurationSetNameListener を追加する}
     * {@.en Adding ConfigurationSetNameListener}
     * <p>
     * {@.ja ConfigurationSetName が更新されたときなどに呼ばれるリスナ
     * ConfigurationSetNameListener を追加する。設定可能なイベントは以下の
     * 3種類がある。
     * <ul>
     * <li> ON_UPDATE_CONFIG_SET: ある ConfigurationSet がアップデートされた
     * <li> ON_REMOVE_CONFIG_SET: ある ConfigurationSet が削除された
     * <li> ON_ACTIVATE_CONFIG_SET: ある ConfigurationSet がアクティブ化された
     * </ul>}
     * {@.en This function add a listener object which is called when
     * ConfigurationSetName is updated. Available events are the followings.
     * <ul>
     * <li> ON_UPDATE_CONFIG_SET: A ConfigurationSet has been updated.
     * <li> ON_REMOVE_CONFIG_SET: A ConfigurationSet has been deleted.
     * <li> ON_ACTIVATE_CONFIG_SET: A ConfigurationSet has been activated.
     * </ul>}
     * @param listener_type 
     *   {@.ja ConfigurationSetNameListenerType型の値。}
     *   {@.en ConfigurationSetNameListenerType value}
     * @param obj 
     *   {@.ja リスナオブジェクト}
     *   {@.en listener object}
     * @param memfunc 
     *   {@.ja リスナのmethod名}
     *   {@.en Method name of listener}
     */
    public <DataType> 
    ConfigurationSetNameListener
    addConfigurationSetNameListener(int listener_type,
                                   DataType obj,
                                   String memfunc) {
        class Noname extends ConfigurationSetNameListener {
            public Noname(DataType obj, String memfunc) {
                m_obj = obj;
                try {
                    Class clazz = m_obj.getClass();

                    m_method = clazz.getMethod(memfunc,String.class);

                }
                catch(java.lang.Exception e){
                    rtcout.println(Logbuf.WARN, 
                        "Exception caught."+e.toString());
                }
            }
            public void operator(final String config_set_name) {
                try {
                    m_method.invoke(
                          m_obj,
                          config_set_name);
                }
                catch(java.lang.Exception e){
                    rtcout.println(Logbuf.WARN, 
                        "Exception caught."+e.toString());
                }
            }
            private DataType m_obj;
            private Method m_method;
        };
        Noname listener = new Noname(obj, memfunc);
        addConfigurationSetNameListener(listener_type, listener, true);
        return listener;
    }
    /**
     * {@.ja  ConfigurationSetNameListener を削除する}
     * {@.en  Removing ConfigurationSetNameListener}
     * <p> 
     * {@.ja addConfigurationSetNameListener で追加されたリスナオブジェクトを
     * 削除する。}
     * {@.en This function removes a listener object which is added by
     * addConfigurationSetNameListener() function.}
     *
     * @param type 
     *   {@.ja ConfigurationSetNameListenerType型の値。
     *         ON_UPDATE_CONFIG_PARAM がある。}
     *   {@.en ConfigurationSetNameListenerType value
     *         ON_UPDATE_CONFIG_PARAM is only allowed.}
     * @param listener 
     *   {@.ja 与えたリスナオブジェクトへのポインタ}
     *   {@.en a pointer to ConfigurationSetNameListener
     *             listener object.}
     * 
     */
    public void
    removeConfigurationSetNameListener(int type,
                                       ConfigurationSetNameListener listener)
    {
        m_configsets.removeConfigurationSetNameListener(type,listener);
    }
    /**
     * {@.ja RTC を終了する。}
     * {@.en Shutdown RTC}
     *
     * <p>
     * {@.ja RTC の終了処理を実行する。
     * 保持している全 Port の登録を解除するとともに、該当する CORBA オブジェクト
     * を非活性化し、RTC を終了する。}
     * {@.en This operation ececutes RTC's termination.
     * This unregisters all Ports, deactivates corresponding CORBA objects and 
     * shuts down RTC.}
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
    protected void preOnInitialize(int ec_id)
    {
      m_actionListeners.preaction_[PreComponentActionListenerType.PRE_ON_INITIALIZE].notify(ec_id);
    }

    protected void preOnFinalize(int ec_id)
    {
      m_actionListeners.preaction_[PreComponentActionListenerType.PRE_ON_FINALIZE].notify(ec_id);
    }

    protected void preOnStartup(int ec_id)
    {
      m_actionListeners.preaction_[PreComponentActionListenerType.PRE_ON_STARTUP].notify(ec_id);
    }

    protected void preOnShutdown(int ec_id)
    {
      m_actionListeners.preaction_[PreComponentActionListenerType.PRE_ON_SHUTDOWN].notify(ec_id);
    }

    protected void preOnActivated(int ec_id)
    {
      m_actionListeners.preaction_[PreComponentActionListenerType.PRE_ON_ACTIVATED].notify(ec_id);
    }

    protected void preOnDeactivated(int ec_id)
    {
      m_actionListeners.preaction_[PreComponentActionListenerType.PRE_ON_DEACTIVATED].notify(ec_id);
    }

    protected void preOnAborting(int ec_id)
    {
      m_actionListeners.preaction_[PreComponentActionListenerType.PRE_ON_ABORTING].notify(ec_id);
    }

    protected void preOnError(int ec_id)
    {
      m_actionListeners.preaction_[PreComponentActionListenerType.PRE_ON_ERROR].notify(ec_id);
    }

    protected void preOnReset(int ec_id)
    {
      m_actionListeners.preaction_[PreComponentActionListenerType.PRE_ON_RESET].notify(ec_id);
    }

    protected void preOnExecute(int ec_id)
    {
      m_actionListeners.preaction_[PreComponentActionListenerType.PRE_ON_EXECUTE].notify(ec_id);
    }

    protected void preOnStateUpdate(int ec_id)
    {
      m_actionListeners.preaction_[PreComponentActionListenerType.PRE_ON_STATE_UPDATE].notify(ec_id);
    }

    protected void preOnRateChanged(int ec_id)
    {
      m_actionListeners.preaction_[PreComponentActionListenerType.PRE_ON_RATE_CHANGED].notify(ec_id);
    }

    protected void postOnInitialize(int ec_id, ReturnCode_t ret)
    {
      m_actionListeners.postaction_[PostComponentActionListenerType.POST_ON_INITIALIZE].notify(ec_id, ret);
    }

    protected void postOnFinalize(int ec_id, ReturnCode_t ret)
    {
      m_actionListeners.postaction_[PostComponentActionListenerType.POST_ON_FINALIZE].notify(ec_id, ret);
    }

    protected void postOnStartup(int ec_id, ReturnCode_t ret)
    {
      m_actionListeners.postaction_[PostComponentActionListenerType.POST_ON_STARTUP].notify(ec_id, ret);
    }

    protected void postOnShutdown(int ec_id, ReturnCode_t ret)
    {
      m_actionListeners.postaction_[PostComponentActionListenerType.POST_ON_SHUTDOWN].notify(ec_id, ret);
    }

    protected void postOnActivated(int ec_id, ReturnCode_t ret)
    {
      m_actionListeners.postaction_[PostComponentActionListenerType.POST_ON_ACTIVATED].notify(ec_id, ret);
    }

    protected void postOnDeactivated(int ec_id, ReturnCode_t ret)
    {
      m_actionListeners.postaction_[PostComponentActionListenerType.POST_ON_DEACTIVATED].notify(ec_id, ret);
    }

    protected void postOnAborting(int ec_id, ReturnCode_t ret)
    {
      m_actionListeners.postaction_[PostComponentActionListenerType.POST_ON_ABORTING].notify(ec_id, ret);
    }

    protected void postOnError(int ec_id, ReturnCode_t ret)
    {
      m_actionListeners.postaction_[PostComponentActionListenerType.POST_ON_ERROR].notify(ec_id, ret);
    }

    protected void postOnReset(int ec_id, ReturnCode_t ret)
    {
      m_actionListeners.postaction_[PostComponentActionListenerType.POST_ON_RESET].notify(ec_id, ret);
    }

    protected void postOnExecute(int ec_id, ReturnCode_t ret)
    {
      m_actionListeners.postaction_[PostComponentActionListenerType.POST_ON_EXECUTE].notify(ec_id, ret);
    }

    protected void postOnStateUpdate(int ec_id, ReturnCode_t ret)
    {
      m_actionListeners.postaction_[PostComponentActionListenerType.POST_ON_STATE_UPDATE].notify(ec_id, ret);
    }

    protected void postOnRateChanged(int ec_id, ReturnCode_t ret)
    {
      m_actionListeners.postaction_[PostComponentActionListenerType.POST_ON_RATE_CHANGED].notify(ec_id, ret);
    }

    protected void onAddPort(final PortProfile pprof)
    {
      m_actionListeners.portaction_[PortActionListenerType.ADD_PORT].notify(pprof);
    }
    
    protected void onRemovePort(final PortProfile pprof)
    {
      m_actionListeners.portaction_[PortActionListenerType.REMOVE_PORT].notify(pprof);
    }
    
    protected void onAttachExecutionContext(int ec_id)
    {
      m_actionListeners.ecaction_[ExecutionContextActionListenerType.EC_ATTACHED].notify(ec_id);
    }
    
    protected void onDetachExecutionContext(int ec_id)
    {
      m_actionListeners.ecaction_[ExecutionContextActionListenerType.EC_DETACHED].notify(ec_id);
    }
    

    /**
     * {@.ja マネージャオブジェクト}
     * {@.en Manager object}
     */
    protected Manager m_pManager;

    /**
     * {@.ja ORB}
     * {@.en ORB}
     */
    protected ORB m_pORB;

    /**
     * {@.ja POA}
     * {@.en POA}
     */
    protected POA m_pPOA;

    /**
     * {@.ja SDO が保持する organization のリスト}
     * {@.en SDO owned organization list}
     */
    protected OrganizationListHolder m_sdoOwnedOrganizations = new OrganizationListHolder();

    /**
     * {@.ja  SDO Configuration オブジェクト}
     * {@.en  The SDO Configuration Object}
     */
    protected Configuration_impl m_pSdoConfigImpl;

    /**
     * {@.ja SDO Configuration Interface}
     * {@.en The SDO Configuration Interface}
     */
    protected Configuration m_pSdoConfig;

    /**
     * {@.ja SDO organization}
     * {@.en SDO organization}
     */
    protected OrganizationListHolder m_sdoOrganizations = new OrganizationListHolder();

    /**
     * {@.ja SDO Status}
     * {@.en SDO Status}
     */
    protected NVListHolder m_sdoStatus = new NVListHolder();

    /**
     * {@.ja コンポーネントプロファイル}
     * {@.en ComponentProfile}
     */
    protected ComponentProfile m_profile = new ComponentProfile();

    /**
     * {@.ja オブジェクトリファレンス}
     * {@.en Object reference}
     */
    protected RTObject m_objref;

    /**
     * {@.ja Port のオブジェクトリファレンスのリスト}
     * {@.en List of Port Object reference}
     */
    protected PortAdmin m_portAdmin;

    /**
     * {@.ja 自分がownerのExecutionContextService のリスト}
     * {@.en List of owned ExecutionContextService}
     */
    protected ExecutionContextServiceListHolder m_ecMine;
    
    /**
     * {@.ja ExecutionContextBase のリスト}
     * {@.en List of ExecutionContextBase}
     */
    protected Vector<ExecutionContextBase> m_eclist 
                                    = new Vector<ExecutionContextBase>();

    /**
     * {@.ja 参加しているExecutionContextService のリスト}
     * {@.en List of participating ExecutionContextService}
     */
    protected ExecutionContextServiceListHolder m_ecOther;

    /**
     * {@.ja Created 状態フラグ}
     * {@.en Created Status Flag}
     */
    protected boolean m_created;
    /**
     * {@.ja RTCの終了状態フラグ}
     * {@.en RTC Finalize Status Flag}
     */
    protected boolean m_exiting;
    /**
     * {@.ja RTC のプロパティ}
     * {@.en RTC's Property}
     */
    protected Properties m_properties = new Properties();
    /**
     * {@.ja コンフィギュレーション情報管理オブジェクト}
     * {@.en Configuration Administrator Object}
     */
    protected ConfigAdmin m_configsets;
    /**
     * {@.ja SDO Service 管理オブジェクト}
     * {@.en SDO Service Administrator Object}
     */
    protected SdoServiceAdmin m_sdoservice;

    /**
     * {@.ja readAll()呼出用のフラグ}
     * {@.en flag for readAll()}
     */
    protected boolean m_readAll;

    /**
     * {@.ja writeAll()呼出用のフラグ}
     * {@.en flag for writeAll()}
     */
    protected boolean m_writeAll;



    /**
     * {@.ja PortConnectListenerホルダ}
     * {@.en PortConnectListener holder}
     * <p>
     * {@.ja PortConnectListenrを保持するホルダ}
     * {@.en Holders of PortConnectListeners}
     *
     */
    protected PortConnectListeners m_portconnListeners = new PortConnectListeners();

    /**
     * {@.ja RTコンポーネント検索用ヘルパークラス}
     * {@.en Functor to find NVList}
     */
    class nv_name implements equalFunctor{
        /**
         * {@.ja コンストラクタ。}
         * {@.en Constructor}
         * @param name 
         *   {@.ja 名前}
         *   {@.en name}
         */
        public nv_name(final String name) {
            m_name = name;
        }
        /**
         * {@.ja NVList検索。}
         * {@.en Finds NVList}
         *
         * <p>
         * {@.ja 指定されたNVListを検索する}
         * {@.en The specified NVList is found.}
         *
         * @param nv 
         *   {@.ja NVList情報}
         *   {@.en NVList}
         *
         * @return 
         *   {@.ja 存在する場合はtrue}
         *   {@.en Returns true when existing.}
         *
         */
        public boolean equalof(final Object nv) {
            return m_name.equals(((NameValue)nv).name);
        }
        private String m_name;
    }

    /**
     * {@.ja ExecutionContext コピー用ファンクタ}
     * {@.en Functor to copy ExecutionContext}
     */
    class ec_copy implements operatorFunc
    {
        /**
         * {@.ja コンストラクタ。}
         * {@.en Constructor}
         * @param eclist 
         *   {@.ja ExecutionContext のリスト}
         *   {@.en List of ExecutionContext}
         */
        public ec_copy(ExecutionContextListHolder eclist)
        { 
            m_eclist = eclist; 
        }
        /**
         * {@.ja ECをリストへ格納する。} 
         * {@.en Stores EC in the list.}
         * @param elem 
         *   {@.ja ExecutionContext}
         *   {@.en ExecutionContext}
         */
        public void operator(Object elem) {
            operator((ExecutionContextService) elem);
        }
        /**
         * {@.ja ECをリストへ格納する。} 
         * {@.en Stores EC in the list.}
         * @param ecs 
         *   {@.ja ExecutionContext}
         *   {@.en ExecutionContext}
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
     * {@.ja ExecutionContext 検索用ファンクタ}
     * {@.en Functor to find ExecutionContext}
     */
    class ec_find implements equalFunctor
    {
        /**
         * {@.ja コンストラクタ。}
         * {@.en Constructor}
         * @param ec 
         *   {@.ja ExecutionContext}
         *   {@.en ExecutionContext}
         */
        public ec_find(ExecutionContext ec)
        {
            m_ec = ec;
        }
        /**
         * {@.ja ECを比較する。}
         * {@.en Compares EC.}
         * @param object
         *   {@.ja 比較するオブジェクト}
         *   {@.en Compared objects}
         * @return
         *   {@.ja 同一の場合はtrueを返す。}
         *   {@.en true in case of the same.}
         */
        public boolean equalof(final java.lang.Object object){
            return operator((ExecutionContextService) object);
        }
        /**
         * {@.ja ECを比較する。}
         * {@.en Compares EC.}
         * @param ecs 
         *   {@.ja 比較するオブジェクト}
         *   {@.en Compared objects}
         * @return
         *   {@.ja 同一の場合はtrueを返す。}
         *   {@.en true in case of the same.}
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
     * {@.ja RTC 非活性化用ファンクタ}
     * {@.en Functor to deactivate RTC}
     */
    class deactivate_comps implements operatorFunc
    {
        /**
         * {@.ja コンストラクタ。}
         * {@.en Constructor}
         * @param comp 
         *   {@.ja コンポーネント}
         *   {@.en Component}
         */
        deactivate_comps(LightweightRTObject comp)
        {
            m_comp = comp;
        }
        /**
         * {@.ja RTCを非活性化する。}
         * {@.en Deactivates RTC.}
         * @param elem
         *   {@.ja EC}
         *   {@.en EC}
         */
          public void operator(Object elem) {
            operator((ExecutionContextService) elem);
          }
        /**
         * {@.ja RTCを非活性化する。}
         * {@.en Deactivates RTC.}
         * @param ecs 
         *   {@.ja EC}
         *   {@.en EC}
         */
        void operator(ExecutionContextService ecs)
        {
            if(ecs != null && !ecs._non_existent())  {
                ecs.deactivate_component(
                                (LightweightRTObject)m_comp._duplicate());
                ecs.stop();
            }
        }
        LightweightRTObject m_comp;
    };

    /**
     * {@.ja ロガーストリーム}
     * {@.en Logger stream}
     */
    protected Logbuf rtcout;

    /**
     * {@.ja InPortBase のリスト.}
     * {@.en List of InPortBase.}
     */
    protected Vector<InPortBase> m_inports = new Vector<InPortBase>();

    /**
     * {@.ja OutPortBase のリスト.}
     * {@.en List of OutPortBase.}
     */
    protected Vector<OutPortBase> m_outports = new Vector<OutPortBase>();
    
    /**
     * {@.ja readAll()用のフラグ}
     * {@.en flag for readAll()}
     *  <p>
     * {@.ja true:readAll()の途中ででエラーが発生しても最後まで実施する。}
     * {@.ja false:readAll()の途中ででエラーが発生した場合終了。}
     * {@.en true:Even if the error occurs during readAll(), 
     * it executes it to the last minute.} 
     * {@.en false:End when error occurs during readAll().}
     * </p>
     */
    protected boolean m_readAllCompletion;

    /**
     * {@.ja writeAll()用のフラグ}
     * {@.en flag for writeAll().}
     * <p>
     * {@.ja true:writeAll()の途中ででエラーが発生しても最後まで実施する。}
     * {@.ja false:writeAll()の途中ででエラーが発生した場合終了。}
     * {@.en true:Even if the error occurs during writeAll(), 
     * it executes it to the last minute.}
     * {@.en false:End when error occurs during writeAll().}
     */
    protected boolean m_writeAllCompletion;
    /**
     * {@.ja ComponentActionListenerホルダ}
     * {@.en ComponentActionListener holder}
     * <p>
     * {@.ja ComponentActionListenrを保持するホルダ}
     * {@.en Holders of ComponentActionListeners}
     *
     */
    protected ComponentActionListeners m_actionListeners = new ComponentActionListeners();
}
