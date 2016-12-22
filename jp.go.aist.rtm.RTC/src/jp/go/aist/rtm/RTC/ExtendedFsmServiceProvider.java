package jp.go.aist.rtm.RTC;

import RTC.ExtendedFsmService;
import RTC.ExtendedFsmServiceHelper;
import RTC.ExtendedFsmServicePOA;
import RTC.FsmEventProfile;
import RTC.FsmStructure;
import RTC.FsmStructureHolder;
import RTC.ReturnCode_t;

import _SDOPackage.NVListHolder;

import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.Properties;
  /**
   * 
   * 
   * 
   */
public class ExtendedFsmServiceProvider extends ExtendedFsmServicePOA implements SdoServiceProviderBase , ObjectCreator<SdoServiceProviderBase>, ObjectDestructor{
    /**
     * {@.ja ctor of ExtendedFsmServiceProvider}
     * {@.en ctor of ExtendedFsmServiceProvider}
     */
    public ExtendedFsmServiceProvider(){
        m_rtobj = null;
        System.out.println("ExtendedFsmServiceProvider()");

        // dummy code
        m_fsmStructure.name = "dummy_name";
        m_fsmStructure.structure = 
        "<scxml xmlns=\"http://www.w3.org/2005/07/scxml\"" 
        + "           version=\"1.0\""
        + "           initial=\"airline-ticket\">"
        + "  <state id=\"state0\">"
        + "    <datamodel>"
        + "      <data id=\"data0\">"
        + "      </data>"
        + "    </datamodel>"
        + "    <transition event=\"toggle\" target=\"state1\" />"
        + "  </state>"
        + "  <state id=\"state1\">"
        + "    <datamodel>"
        + "      <data id=\"data1\">"
        + "      </data>"
        + "    </datamodel>"
        + "    <transition event=\"toggle\" target=\"state0\" />"
        + "  </state>"
        + " </scxml>" ;
        m_fsmStructure.event_profiles = new FsmEventProfile[1];
        FsmEventProfile event0 = new FsmEventProfile();
        event0.name = "toggle";
        event0.data_type = "TimedShort";
        m_fsmStructure.event_profiles[0] = event0;
        NVListHolder holder = new NVListHolder(m_fsmStructure.properties);
        NVUtil.appendStringValue(holder,
                                  "fsm_structure.format",
                                  "scxml");
        m_fsmStructure.properties = holder.value;
        System.out.println(m_fsmStructure.structure);
    }


    /**
     * {@.ja 初期化}
     * {@.en Initialization}
     */
    public boolean init(RTObject_impl rtobj,
                      final _SDOPackage.ServiceProfile profile){
        m_rtobj = rtobj;
        m_profile = profile;
/*
        NVListHolder nvholder = 
                new NVListHolder(profile.properties);
        Properties prop = new Properties();
        NVUtil.copyToProperties(prop, nvholder);
*/
        return true;

    }

    /**
     * {@.ja 再初期化}
     * {@.en Re-initialization}
     */
    public boolean reinit(final _SDOPackage.ServiceProfile profile){
        m_profile = profile;
/*
        NVListHolder nvholder = 
                new NVListHolder(profile.properties);
        Properties prop = new Properties();
        NVUtil.copyToProperties(prop, nvholder);
*/
        return true;
    }

    /**
     * {@.ja ServiceProfile を取得する}
     * {@.en getting ServiceProfile}
     */
    public final _SDOPackage.ServiceProfile getProfile() {
        return m_profile;
    }
    
    /**
     * {@.ja 終了処理}
     * {@.en Finalization}
     */
    public void finalize(){
    }


    //============================================================
    // CORBA operations
    //
    // string get_current_state();
    // ReturnCode_t set_fsm_structure(in FsmStructure fsm_structure);
    // ReturnCode_t get_fsm_structure(out FsmStructure fsm_structure);
    //============================================================

    /**
     * {@.ja FSMの現在の状態を取得}
     * {@.en Get Current FSM State}
     * <p>
     * {@.ja このオペレーションはFSMコンポーネントのFSMの現在の状態を返す。
     * (FSM4RTC spec. p.20)}
     * {@.en This operation returns the current state of an FSM in the
     * target FSM component. (FSM4RTC spec. p.20)}
     *
     * @return 
     *   {@.ja 現在の状態を表す文字列}
         {@.en A string which represent the current status}
     *
     */
    public String get_current_state() {
        return m_fsmState;
    }
    /**
     * {@.ja FSMの構造を設定する}
     * {@.en Set FSM Structure}
     * <p>
     * {@.ja このオペレーションは対象のコンポーネントに対して、FSMの構造を保
     * 持する FsmStruccture を設定する。対象コンポーネントは
     * fsm_structure に与えられた値を基に状態遷移ルール等のFSM構造を再
     * 設定する。このオペレーションが未実装の場合は、UNSUPPORTED を返す。}
     * {@.en This operation sets an FsmStructure to the target
     * component. Then the target component reconfigures its FSM
     * structure such as transition rules according to the values of
     * the given fsm_structure. RTCs may return UNSUPPORTED if this
     * operation is not implemented.}
     *
     * @param fsm_structure 
     *   {@.ja FSMの構造を表すFsmStructure構造体。}
     *   {@.en FsmStructure structure which represents}
     * @return 
     *   {@.ja RTC_OK 正常終了
     *         RTC_ERROR その他のエラー
     *         BAD_PARAMETER 不正なパラメータ
     *         UNSUPPORTED 未サポート}
     *   {@.en RTC_OK normal return
     *         RTC_ERROR other error
     *         BAD_PARAMETER invalid parameter
     *         UNSUPPORTED unsupported or not implemented}
     *
     */
    public ReturnCode_t
    set_fsm_structure(final RTC.FsmStructure fsm_structure) {
        m_fsmStructure = fsm_structure; 
        return ReturnCode_t.RTC_OK;
    }

    /**
     * {@.ja FSMの構造を取得する}
     * {@.en Set FSM Structure}
     * <p>
     * {@.ja このオペレーションは対象のコンポーネントに対して、現在保持してい
     * るFSMの構造を取得する。ExtendedFsmService 構造体はフィールド
     * name (FSMの名称), structure (FSMの構造) 、EventProfile などを返
     * す。structure のフォーマットは、フィールド properties 内に格納さ
     * れたキー "fsm_structure.format" に指定される。このオペレーション
     * が未実装の場合は、UNSUPPORTED を返す。
     *
     * ref: SCXML https://www.w3.org/TR/scxml/}
     *
     * {@.en This operation returns the structure of an FSM in the target
     * FSM component. ExtendedFsmService returns the name, structure
     * with format specified by fsm_structure.format and
     * EventProfiles. RTCs may return UNSUPPORTED if this operation is
     * not implemented.}
     *
     * @param fsm_structure 
     *   {@.ja FSMの構造を表すFsmStructure構造体。}
     *   {@.en FsmStructure structure which represents FSM structure}
     *
     * @return 
     *   {@.ja RTC_OK 正常終了
     *         RTC_ERROR その他のエラー
     *         BAD_PARAMETER 不正なパラメータ
     *         UNSUPPORTED 未サポート}
     *   {@.en RTC_OK normal return
     *         RTC_ERROR other error
     *         BAD_PARAMETER invalid parameter
     *         UNSUPPORTED unsupported or not implemented}
     *
     */
    public ReturnCode_t
    get_fsm_structure(RTC.FsmStructureHolder fsm_structure) {
        fsm_structure.value = m_fsmStructure;
        return ReturnCode_t.RTC_OK;
    }

    /**
     * {@.ja RTObjectへのリスナ接続処理}
     * {@.en Connectiong listeners to RTObject}
     */
    protected void setListeners(Properties prop) {
    }
    
    /**
     * {@.ja FSM状態遷移}
     * {@.en FSM status change}
     */
    protected void changeStatus(String state) {
        m_fsmState = state;
    }

    /**
     * {@.ja ハートビートを解除する}
     * {@.en Unsetting heartbeat}
     */
    protected void changeStructure(String fsm_structure) {
        m_fsmStructure.structure = fsm_structure;
    }

    //============================================================
    // FSM status related functions
    /**
     * {@.ja FSM状態変化リスナの設定処理}
     * {@.en Setting FSM status listeners}
     */
    protected void setFSMStatusListeners(){
        // TODO: here should be inmplemented after FSM API defined.
        ;
    }

    /**
     * {@.ja FSM状態変化リスナの解除処理}
     * {@.en Unsetting FSM status listeners}
     */
    protected void unsetFSMStatusListeners(){
        // TODO: here should be inmplemented after FSM API defined.
        ;
    }





    //============================================================
    // FsmProfile related functions
    /**
     * {@.ja FsmProfile状態変化リスナの設定}
     * {@.en Setting FsmProfile listener}
     */
    protected void setFSMProfileListeners(){
        ;
    }

    /**
     * {@.ja FsmProfile状態変化リスナの解除}
     * {@.en Unsetting FsmProfile listener}
     */
    protected void unsetFSMProfileListeners(){
        ;
    }

    //============================================================
    // FsmStructure related functions
    /**
     * {@.ja FsmStructure状態変化リスナの設定}
     * {@.en Setting FsmStructure listener}
     */
    protected void setFSMStructureListeners(){
    }

    /**
     * {@.ja FsmStructure状態変化リスナの解除}
     * {@.en Unsetting FsmStructure listener}
     */
    protected void unsetFSMStructureListeners(){
        ;
    }


    /**
     * <p> creator_ </p>
     * 
     * @return Object Created instances
     *
     */
    public SdoServiceProviderBase creator_() {
        return new ExtendedFsmServiceProvider();
    }
    /**
     * <p> destructor_ </p>
     * 
     * @param obj    The target instances for destruction
     *
     */
    public void destructor_(Object obj) {
        obj = null;
    }
    /**
     * <p> ExtendedFsmServiceProviderInit </p>
     *
     */
    public static void  ExtendedFsmServiceProviderInit() {
        final SdoServiceProviderFactory<SdoServiceProviderBase,String> factory 
            = SdoServiceProviderFactory.instance();

        factory.addFactory(ExtendedFsmServiceHelper.id(),
                    new ExtendedFsmServiceProvider(),
                    new ExtendedFsmServiceProvider());
    
    }

    private _SDOPackage.ServiceProfile m_profile;
    private RTObject_impl m_rtobj;
    private FsmStructure m_fsmStructure = new  FsmStructure();
    private String m_fsmState = new String();

};

