package jp.go.aist.rtm.RTC;

import jp.go.aist.rtm.RTC.util.POAUtil;

import jp.go.aist.rtm.RTC.log.Logbuf;

//import OpenRTM.FiniteStateMachineComponent;
//import OpenRTM.FiniteStateMachineComponentHelper;

import RTC.ComponentProfile;
import RTC.ExecutionContext;
import RTC.ExecutionContextListHolder;
import RTC.FsmParticipant;
import RTC.FsmParticipantHelper;
import RTC.FsmParticipantPOA;
import RTC.PortService;
import RTC.ReturnCode_t;

/**
 * {@.ja FiniteStateMachineコンポーネントのベースクラスです。}
 * {@.en This is a base class of FiniteStateMachineTYpe RT-Component.}
 * <p>
 * {@.ja FiniteStateMachine型RTComponentの基底クラス。
 * データフローコンポーネントを実装する際には、
 * 本クラスのサブクラスとして実装します。}
 * {@.en Inherit this class when implementing various FiniteStateMachine
 * type RT-Components.}
 */
public class FiniteStateMachineComponentBase  extends RTObject_impl {
//public class FiniteStateMachineComponentBase  extends RTObject_impl<FiniteStateMachineComponent_impl> {

    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     */
    public FiniteStateMachineComponentBase(Manager manager) {
        super(manager);
        rtcout = new Logbuf("FiniteStateMachineComponentBase");
        //m_ref = this._this();
        //m_objref = m_ref;
    }
    
    /**
     * {@.ja 当該オブジェクトのCORBAオブジェクト参照を取得します。}
     *
     * @return 
     *   {@.ja 当該オブジェクトのCORBAオブジェクト参照}
     */
/*
    public RTObject _this() {
        
        if (this.m_ref == null) {
            try {
                this.m_ref = FsmParticipantHelper.narrow(POAUtil.getRef(this));
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        
        return this.m_ref;
    }
*/

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

        rtcout.println(Logbuf.TRACE, "initialize()");

        return ReturnCode_t.RTC_OK;
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
    public ReturnCode_t _finalize() {

        rtcout.println(Logbuf.TRACE, "_finalize()");

        return ReturnCode_t.RTC_OK;
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
    public ReturnCode_t exit() {

        rtcout.println(Logbuf.TRACE, ".exit()");
        return ReturnCode_t.RTC_OK;

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
    public boolean is_alive(ExecutionContext exec_context) {
        rtcout.println(Logbuf.TRACE, "is_alive()");
        return true;

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
    public ExecutionContext[] get_owned_contexts() {

        rtcout.println(Logbuf.TRACE, "get_owned_contexts()");

        ExecutionContextListHolder execlist;
        execlist = new ExecutionContextListHolder();
        execlist.value = new ExecutionContext[0];
    
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

        rtcout.println(Logbuf.TRACE, "get_context(" + ec_id + ")");

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
    public ExecutionContext[] get_participating_contexts() {
        rtcout.println(Logbuf.TRACE, "get_participating_contexts()");

        ExecutionContextListHolder execlist;
        execlist = new ExecutionContextListHolder();
        execlist.value = new ExecutionContext[0];


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
    public int get_context_handle(ExecutionContext cxt) {

        rtcout.println(Logbuf.TRACE, "get_context_handle()");

        return -1;
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

        rtcout.println(Logbuf.TRACE, "get_component_profile()");

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

        rtcout.println(Logbuf.TRACE, "get_ports()");

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
    public int attach_context(ExecutionContext exec_context) {

        rtcout.println(Logbuf.TRACE, "attach_context()");
        return 1;

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
    public ReturnCode_t detach_context(int ec_id) {

        rtcout.println(Logbuf.TRACE, "detach_context(" + ec_id + ")");

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

        rtcout.println(Logbuf.TRACE, "on_initialize()");

        return ReturnCode_t.RTC_OK;
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

        rtcout.println(Logbuf.TRACE, "on_finalize()");
        return ReturnCode_t.RTC_OK;

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

        rtcout.println(Logbuf.TRACE, "on_startup(" + ec_id + ")");
        return ReturnCode_t.RTC_OK;

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

        rtcout.println(Logbuf.TRACE, "on_shutdown(" + ec_id + ")");

        return ReturnCode_t.RTC_OK;
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

        rtcout.println(Logbuf.TRACE, "on_activated(" + ec_id + ")");
        return ReturnCode_t.RTC_OK;

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

        rtcout.println(Logbuf.TRACE, "on_deactivated(" + ec_id + ")");
        return ReturnCode_t.RTC_OK;

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
     *   FiniteStateMachineComponentAction::on_action が呼ばれた際に、替わりに呼び出される。
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
     *   whenever FiniteStateMachineComponentAction::on_action would otherwise have been 
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

        rtcout.println(Logbuf.TRACE, "on_error(" + ec_id + ")");
        return ReturnCode_t.RTC_OK;

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

        rtcout.println(Logbuf.TRACE, "on_aborting(" + ec_id + ")");
        return ReturnCode_t.RTC_OK;

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

        rtcout.println(Logbuf.TRACE, "on_reset(" + ec_id + ")");
        return ReturnCode_t.RTC_OK;

    }
    /**
     *
     * @param ec_id 
     *   {@.ja 対象ExecutionContextのID}
     *   {@.en ID of target ExecutionContext}
     *
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     */
    public ReturnCode_t on_action(int ec_id) {

        rtcout.println(Logbuf.TRACE, "on_action(" + ec_id + ")");
        return ReturnCode_t.RTC_OK;

    }




    private FsmParticipant m_ref;
    /**
     * {@.ja ロガーストリーム}
     * {@.en Logger stream}
     */
    protected Logbuf rtcout;


}
