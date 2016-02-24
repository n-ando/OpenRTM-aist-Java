package jp.go.aist.rtm.RTC.util;

import java.util.Vector;

import RTC.RTObject;

import RTC.ComponentProfile;
import RTC.ConnectorProfile;
import RTC.ConnectorProfileHolder;
import RTC.ConnectorProfileListHolder;
import RTC.ExecutionContext;
import RTC.ExecutionContextProfile;
import RTC.ExecutionContextService;
import RTC.ExecutionContextServiceHelper;
import RTC.LifeCycleState;
import RTC.PortProfile;
import RTC.PortService;
import RTC.PortServiceListHolder;
import RTC.ReturnCode_t;

import _SDOPackage.NameValue;
import _SDOPackage.NVListHolder;

import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.port.PortBase;

/**
 * {@.ja コンポーネントの各種操作ユーティリティクラス。}
 * {@.en  }
 * <p>
 * {@.ja Rtshellライクにコンポーネントの各種操作を
 * 簡単に行うことができる関数セット}
 * {@.en  }
 *
 */
public class RTShellUtil {
    /**
     *
     * {@.ja コンポーネントのプロパティ取得}
     * {@.en Get RTC's profile}
     * <p>
     * {@.ja 当該コンポーネントのプロファイル情報を返す。}
     * {@.en This operation returns the ComponentProfile of the RTC.}
     * 
     * @param rtc 
     *   {@.ja RTコンポーネント}
     *   {@.ja RTComponent}
     *
     * @return 
     *   {@.ja コンポーネントのプロパティ}
     *   {@.en ComponentProfile}
     *
     * coil::Properties get_component_profile(const RTC::RTObject_ptr rtc)
     */
    public static Properties get_component_profile(final RTObject rtc){
            if(rtc == null){
            return null;
        }
        ComponentProfile prof = rtc.get_component_profile();
        NVListHolder nvholder = 
                new NVListHolder(prof.properties);
        Properties prop = new Properties();
        NVUtil.copyToProperties(prop, nvholder);
        return prop;

    }

    /**
     * {@.ja コンポーネントの生存を確認}
     * {@.en Check if the component is alive}
     * 
     * @param rtc
     *   {@.ja RTコンポーネント}
     *   {@.ja RTComponent}
     *
     * @return 
     *   {@.ja true:生存している, false:生存していない}
     *   {@.en true: alive, false:non not alive}
     *
     */
    public static boolean is_existing(RTObject rtc) {
        try{
            if (rtc._non_existent()) { 
                return false; 
            }
            return true;
        }
        catch (Exception ex) {
            return true;
        }
    }
    /**
     *
     * {@.ja RTCがデフォルトの実行コンテキストでalive状態かを判定する}
     * {@.en Confirm whether RTC is the alive state}
     *
     * @param rtc
     *   {@.ja RTコンポーネント}
     *   {@.ja RTComponent}
     *
     * @return 
     *   {@.ja true:alive状態}
     *   {@.en Result of Alive state confirmation}
     * 
     */
    public static boolean is_alive_in_default_ec(RTObject rtc){
        ExecutionContext ec = get_actual_ec(rtc);
        if(ec==null){
            return false;
        }
        try {
            return rtc.is_alive(ec);
        }
        catch (Exception ex) {
            return false;
        }
    }
    /**
     * {@.ja RTコンポーネントに関連付けした実行コンテキストから指定したIDの実行コンテキストを取得}
     * {@.en Get ExecutionContext.}
     * 
     * {@.en Obtain a reference to the execution context represented 
     * by the given handle and RTC.}
     * @param rtc 
     *   {@.ja 対象のRTコンポーネント}
     *   {@.en Target RT-Component's instances}
     *
     * @param ec_id 
     *   {@.ja 実行コンテキストのID}
     *   {@.en ExecutionContext handle}
     *
     * @return 
     *   {@.ja 実行コンテキスト}
     *   {@.en ExecutionContext}
     *
     * RTC::ExecutionContext_var get_actual_ec(const RTC::RTObject_ptr rtc,RTC::UniqueId ec_id = 0)
     */
    public static ExecutionContext get_actual_ec(RTObject rtc, int ec_id) {
        if( ec_id < 0){
            return null;
        }
        if(rtc == null) {
            return null;
        }
        if( ec_id < RTObject_impl.ECOTHER_OFFSET){
            ExecutionContext[] eclist = rtc.get_owned_contexts();
            if( ec_id >= eclist.length){
                return null;
            }
            if(eclist[ec_id] == null){
                return null;
            }
            return eclist[ec_id];
        }
        else {
            int pec_id = ec_id - RTObject_impl.ECOTHER_OFFSET;
            ExecutionContext[] eclist = rtc.get_participating_contexts();
            if(pec_id >= eclist.length){
                return null;
            }
            if(eclist[pec_id]==null){
                return null;
            }
            return eclist[pec_id];
        }
    }    

    /**
     * {@.ja RTコンポーネントに関連付けした実行コンテキストから指定したIDの実行コンテキストを取得}
     * {@.en Get ExecutionContext.}
     * 
     * {@.en Obtain a reference to the execution context represented 
     * by the given handle and RTC.}
     * @param rtc 
     *   {@.ja 対象のRTコンポーネント}
     *   {@.en Target RT-Component's instances}
     *
     * @return 
     *   {@.ja 実行コンテキスト}
     *   {@.en ExecutionContext}
     *
     */
    public static ExecutionContext get_actual_ec(RTObject rtc) {
        return get_actual_ec(rtc,0);
    }
    /**
     *
     * {@.ja 対象のRTコンポーネントから指定した実行コンテキストのIDを取得する}
     *
     * 
     * @param rtc 
     *   {@.ja 対象のRTコンポーネント}
     *   {@.en Target RT-Component's instances}
     *
     * @param ec 
     *   {@.ja 実行コンテキスト}
     *   {@.en ExecutionContext}
     *
     * @return 
     *   {@.ja 実行コンテキストのID 指定した実行コンテキストがRTコンポーネントに関連付けられていなかった場合は-1を返す}
     *
     */
    public static int get_ec_id(RTObject rtc, ExecutionContext ec){
        if(rtc == null){
            return -1;
        }
        ExecutionContext[] eclist = rtc.get_owned_contexts();
  
        for(int ic=0;ic<eclist.length;++ic){
            if(eclist[ic] != null){
                if(eclist[ic]._is_equivalent(ec)){
                      return ic;
                }
            }
        }
        ExecutionContext[] eclist_pec = rtc.get_participating_contexts();
        for(int ic=0;ic<eclist_pec.length;++ic){
            if(eclist_pec[ic] != null){
                if(eclist[ic]._is_equivalent(ec)){
                    return ic+RTObject_impl.ECOTHER_OFFSET;
                }
            }
        }
        return -1;
    }
    /**
     *
     * {@.ja RTCを指定した実行コンテキストでアクティベーションする}
     * {@.en Activates RTC via Specified ExecutionContext.}
     * 
     * @param rtc
     *   {@.ja 対象のRTコンポーネント}
     *   {@.en Target RT-Component's instances}
     *
     * @param ec_id 
     *   {@.ja 実行コンテキストのID}
     *   {@.en ExecutionContext handle}
     *
     * @return 
     *   {@.ja RTC、ECのオブジェクトリファレンスがnilの場合はBAD_PARAMETERを返す
     * nilではない場合はactivate_component関数の戻り値を返す。RTC_OKの場合はアクティベーションが成功}
     }
     *   {@.en Return code}
     * RTC::ReturnCode_t activate(RTC::RTObject_ptr rtc, RTC::UniqueId ec_id = 0)
     */
    public static ReturnCode_t activate(RTObject rtc, int ec_id){
        if(rtc==null){
            return ReturnCode_t.BAD_PARAMETER;
        }
        ExecutionContext ec = get_actual_ec(rtc, ec_id);
        if(ec==null){
            return ReturnCode_t.BAD_PARAMETER;
        }
        return ec.activate_component(rtc);
  
    }
    /**
     *
     * {@.ja RTCをアクティベーションする}
     * {@.en Activates RTC}
     * 
     * @param rtc
     *   {@.ja 対象のRTコンポーネント}
     *   {@.en Target RT-Component's instances}
     *
     * @return 
     *   {@.ja RTC、ECのオブジェクトリファレンスがnilの場合はBAD_PARAMETERを返す
     * nilではない場合はactivate_component関数の戻り値を返す。RTC_OKの場合はアクティベーションが成功}
     *   {@.en Return code}
     }
     * RTC::ReturnCode_t activate(RTC::RTObject_ptr rtc, RTC::UniqueId ec_id = 0)
     */
    public static ReturnCode_t activate(RTObject rtc){
        return activate(rtc,0);
    }
    /**
     *
     * {@.ja RTCを指定した実行コンテキストで非アクティベーションする}
     * {@.en Deactivates RTC via Specified ExecutionContext.}
     * 
     * @param rtc
     *   {@.ja 対象のRTコンポーネント}
     *   {@.en Target RT-Component's instances}
     *
     * @param ec_id 
     *   {@.ja 実行コンテキストのID}
     *   {@.en ExecutionContext handle}
     *
     * @return 
     *   {@.ja RTC、ECのオブジェクトリファレンスがnilの場合はBAD_PARAMETERを返す
     * nilではない場合はdeactivate_component関数の戻り値を返す。RTC_OKの場合は非アクティベーションが成功}
     * RTC::ReturnCode_t deactivate(RTC::RTObject_ptr rtc, RTC::UniqueId ec_id = 0)
     *   {@.en Return code}
     */
    public static ReturnCode_t deactivate(RTObject rtc, int ec_id){

        if(rtc==null){
            return ReturnCode_t.BAD_PARAMETER;
        }
        ExecutionContext ec = get_actual_ec(rtc, ec_id);
        if(ec==null){
            return ReturnCode_t.BAD_PARAMETER;
        }
        return ec.deactivate_component(rtc);
    }
    /**
     *
     * {@.ja RTCを非アクティベーションする}
     * {@.en Deactivates RTC.}
     * 
     * @param rtc
     *   {@.ja 対象のRTコンポーネント}
     *   {@.en Target RT-Component's instances}
     *
     * @return 
     *   {@.ja RTC、ECのオブジェクトリファレンスがnilの場合はBAD_PARAMETERを返す
     * nilではない場合はdeactivate_component関数の戻り値を返す。RTC_OKの場合は非アクティベーションが成功}
     * RTC::ReturnCode_t deactivate(RTC::RTObject_ptr rtc, RTC::UniqueId ec_id = 0)
     *   {@.en Return code}
     */
    public static ReturnCode_t deactivate(RTObject rtc){
        return deactivate( rtc, 0);
    }
    /**
     *
     * {@.ja RTCを指定した実行コンテキストでリセットする}
     * {@.en Resets RTC via Specified RTC.}
     * 
     * @param rtc
     *   {@.ja 対象のRTコンポーネント}
     *   {@.en Target RT-Component's instances}
     *
     * @param ec_id 
     *   {@.ja 実行コンテキストのID}
     *   {@.en ExecutionContext handle}
     *
     * @return 
     *   {@.ja RTC、ECのオブジェクトリファレンスがnilの場合はBAD_PARAMETERを返す
     * nilではない場合はdeactivate_component関数の戻り値を返す。RTC_OKの場合はリセットが成功}
     *   {@.en Return code}
     *
     * RTC::ReturnCode_t reset(RTC::RTObject_ptr rtc, RTC::UniqueId ec_id = 0)
     */
    public static ReturnCode_t reset(RTObject rtc, int ec_id){
        if(rtc==null){
            return ReturnCode_t.BAD_PARAMETER;
        }
        ExecutionContext ec = get_actual_ec(rtc, ec_id);
        if(ec==null){
            return ReturnCode_t.BAD_PARAMETER;
        }
        return ec.reset_component(rtc);
    }
    /**
     *
     * {@.ja RTCをリセットする}
     * {@.en Resets RTC}
     * 
     * @param rtc
     *   {@.ja 対象のRTコンポーネント}
     *   {@.en Target RT-Component's instances}
     *
     * @return 
     *   {@.ja RTC、ECのオブジェクトリファレンスがnilの場合はBAD_PARAMETERを返す
     * nilではない場合はdeactivate_component関数の戻り値を返す。RTC_OKの場合はリセットが成功}
     *   {@.en Return code}
     *
     */
    public static ReturnCode_t reset(RTObject rtc){
        return reset(rtc,0);
    }
    /**
     *
     * {@.ja 対象のRTコンポーネントの指定した実行コンテキストでの状態を取得}
     * {@.en Get RTC's status}
     * 
     * @param rtc
     *   {@.ja 対象のRTコンポーネント}
     *   {@.en Target RT-Component's instances}
     *
     * @param ec_id 
     *   {@.ja 実行コンテキストのID}
     *   {@.en ExecutionContext handle}
     *
     * @return 
     *   {@.ja RTCの状態}
     *   {@.en RTC's status}
     *
     *
     */
    public static LifeCycleState get_state(RTObject rtc, int ec_id){
        if(rtc==null){
            return LifeCycleState.ERROR_STATE;
        }
        ExecutionContext ec = get_actual_ec(rtc, ec_id);
        if(ec==null){
            return LifeCycleState.ERROR_STATE;
        }
        return ec.get_component_state(rtc);
    }
    /**
     *
     * {@.ja 対象のRTコンポーネントの状態を取得}
     * {@.en Get RTC's status}
     * 
     * @param rtc
     *   {@.ja 対象のRTコンポーネント}
     *   {@.en Target RT-Component's instances}
     *
     * @return 
     *   {@.ja RTCの状態}
     *   {@.en RTC's status}
     *
     *
     */
    public static LifeCycleState get_state(RTObject rtc){
        return get_state(rtc, 0);
    }
    /**
     *
     * {@.ja 対象のRTコンポーネントの指定した実行コンテキストでINACTIVE状態かどうか判定}
     * {@.en Confirm to INACTIVE}
     *
     * 
     * @param rtc
     *   {@.ja 対象のRTコンポーネント}
     *   {@.en Target RT-Component's instances}
     *
     * @param ec_id 
     *   {@.ja 実行コンテキストのID}
     *   {@.en ExecutionContext handle}
     *
     * @return 
     *   {@.ja INACTIVE状態の時はtrue、それ以外はfalse
     * rtc、ecがnilの場合もfalseを返す}
     *   {@.en Result of state confirmation
     *         (INACTIVE state:true, other state:false)}
     *
     */
    public static boolean is_in_inactive(RTObject rtc, int ec_id){
        if( get_state(rtc, ec_id) == LifeCycleState.INACTIVE_STATE){
                return true;
        }
        return false;
    }
    /**
     *
     * {@.ja 対象のRTコンポーネントの指定した実行コンテキストでINACTIVE状態かどうか判定}
     * {@.en Confirm to INACTIVE}
     *
     * 
     * @param rtc
     *   {@.ja 対象のRTコンポーネント}
     *   {@.en Target RT-Component's instances}
     *
     *
     * @return 
     *   {@.ja INACTIVE状態の時はtrue、それ以外はfalse
     * rtc、ecがnilの場合もfalseを返す}
     *   {@.en Result of state confirmation
     *         (INACTIVE state:true, other state:false)}
     *
     */
    public static boolean is_in_inactive(RTObject rtc){
        return is_in_inactive(rtc, 0);
    }
    /**
     *
     * {@.ja 対象のRTコンポーネントの指定した実行コンテキストでACTIVE状態かどうか判定}
     * {@.en Confirm to ACTIVE}
     * 
     * @param rtc
     *   {@.ja 対象のRTコンポーネント}
     *   {@.en Target RT-Component's instances}
     * 
     * @param ec_id 
     *   {@.ja 実行コンテキストのID}
     *   {@.en ExecutionContext handle}
     * 
     * @return 
     *   {@.ja ACTIVE状態の時はtrue、それ以外はfalse
     * rtc、ecがnilの場合もfalseを返す}
     *   {@.en Result of state confirmation
     *         (ACTIVE state:true, other state:false)}
     *
     */
    public static boolean is_in_active(RTObject rtc, int ec_id){
        if(get_state(rtc, ec_id) == LifeCycleState.ACTIVE_STATE){
                return true;
        }
        return false;
    }
    /**
     *
     * {@.ja 対象のRTコンポーネントがACTIVE状態かどうか判定}
     * {@.en Confirm to ACTIVE}
     * 
     * @param rtc
     *   {@.ja 対象のRTコンポーネント}
     *   {@.en Target RT-Component's instances}
     * 
     * @return 
     *   {@.ja ACTIVE状態の時はtrue、それ以外はfalse
     * rtc、ecがnilの場合もfalseを返す}
     *   {@.en Result of state confirmation
     *         (ACTIVE state:true, other state:false)}
     *
     */
    public static boolean is_in_active(RTObject rtc){
        return is_in_active(rtc, 0);
    }
    /**
     *
     * {@.ja 対象のRTコンポーネントの指定した実行コンテキストでERROR状態かどうか判定}
     * {@.en Confirm to ERROR}
     * 
     * @param rtc
     *   {@.ja 対象のRTコンポーネント}
     *   {@.en Target RT-Component's instances}
     * 
     * @param 
     *   {@.ja ec_id 実行コンテキストのID}
     *   {@.en ExecutionContext handle}
     * 
     * @return 
     *   {@.ja ERROR状態の時はtrue、それ以外はfalse
     * rtc、ecがnilの場合もfalseを返す}
     *   {@.en Result of state confirmation
     *         (ERROR state:true, other state:false)}
     *
     */
    public static boolean is_in_error(RTObject rtc, int ec_id){
        if(get_state(rtc, ec_id) == LifeCycleState.ERROR_STATE){
                return true;
        }
        return false;
    }
    /**
     *
     * {@.ja 対象のRTコンポーネントがERROR状態かどうか判定}
     * {@.en Confirm to ERROR}
     * 
     * @param rtc
     *   {@.ja 対象のRTコンポーネント}
     *   {@.en Target RT-Component's instances}
     * 
     * 
     * @return 
     *   {@.ja ERROR状態の時はtrue、それ以外はfalse
     * rtc、ecがnilの場合もfalseを返す}
     *   {@.en Result of state confirmation
     *         (ERROR state:true, other state:false)}
     *
     */
    public static boolean is_in_error(RTObject rtc){
        return is_in_error(rtc,0);
    }
    /**
     *
     * {@.ja RTCのデフォルトの実行コンテキストの実行周期を取得する}
     * {@.en Get execution rate(Hz) of ExecutionContext}
     *
     * @param rtc
     *   {@.ja 対象のRTコンポーネント}
     *   {@.en Target RT-Component's instances}
     * 
     * @return 
     *   {@.ja 実行周期}
     *   {@.en Execution cycle(Unit:Hz)}
     */
    public static double get_default_rate(RTObject rtc){
        ExecutionContext ec = get_actual_ec(rtc);
        return ec.get_rate();
    }
    /**
     *
     * {@.ja RTCのデフォルトの実行コンテキストの実行周期を設定する}
     * {@.en Set execution rate(Hz) of ExecutionContext}
     *
     * 
     * @param rtc
     *   {@.ja 対象のRTコンポーネント}
     *   {@.en Target RT-Component's instances}
     * 
     * 
     * @param rate 
     *   {@.ja 実行周期}
     *   {@.en Execution cycle(Unit:Hz)}
     * 
     * @return 
     *   {@.ja set_rate関数の戻り値を返す。 RTC_OKで設定が成功}
     *   {@.en Return code}
     *
     */
    public static ReturnCode_t set_default_rate(RTObject rtc, double rate){
        ExecutionContext ec = get_actual_ec(rtc);
        return ec.set_rate(rate);

    }  
    /**
     *
     * {@.ja RTCの指定IDの実行コンテキストの周期を取得}
     * {@.en Get execution rate(Hz) of specified ExecutionContext}
     * 
     * @param rtc
     *   {@.ja 対象のRTコンポーネント}
     *   {@.en Target RT-Component's instances}
     * 
     * @param ec_id 
     *   {@.ja 指定の実行コンテキストのID}
     *   {@.en ExecutionContext handle}
     * 
     * @return 
     *   {@.ja 実行周期}
     *   {@.en Execution cycle(Unit:Hz)}
     *
     */
    public static double get_current_rate(RTObject rtc, int ec_id){
        ExecutionContext ec = get_actual_ec(rtc, ec_id);
        return ec.get_rate();
    }
    /**
     *
     * {@.ja RTCの指定IDの実行コンテキストの周期を設定}
     * {@.en Set execution rate(Hz) of specified ExecutionContext}
     * 
     * @param rtc
     *   {@.ja 対象のRTコンポーネント}
     *   {@.en Target RT-Component's instances}
     * 
     * @param ec_id 
     *   {@.ja 指定の実行コンテキストのID}
     *   {@.en ExecutionContext handle}
     * 
     * @return 
     *   {@.ja set_rate関数の戻り値を返す。RTC_OKで設定が成功}
     *   {@.en Return code}
     *
     */
    public static ReturnCode_t set_current_rate(RTObject rtc, 
            int  ec_id, double rate){
        ExecutionContext ec = get_actual_ec(rtc, ec_id);
        return ec.set_rate(rate);
    }
    /**
     *
     * {@.ja 対象のRTCのデフォルトの実行コンテキストに指定のRTCを関連付ける}
     * {@.en Add an RT-component to The target RT-Component of ExecutionContext}
     *
     * 
     * @param localcomp
     *   {@.ja 対象のRTコンポーネント}
     *   {@.en Target RT-Component's instances}
     * 
     * @param othercomp 
     *   {@.ja 実行コンテキストに関連付けるRTコンポーネント}
     *   {@.en The target RT-Component for add}
     * 
     * @return 
     *   {@.ja ecの取得に失敗した場合はRTC_ERRORを返す
     * そうでない場合はaddComponent関数の戻り値を返す。RTC_OKで接続成功。}
     *   {@.en The return code of ReturnCode_t type}
     *
     */
    public static ReturnCode_t add_rtc_to_default_ec(RTObject localcomp, 
            RTObject othercomp){
        ExecutionContext ec = get_actual_ec(localcomp);
        if(ec==null){
            return ReturnCode_t.RTC_ERROR;
        }
        return ec.add_component(othercomp);
    }
    /**
     *
     * {@.ja 対象のRTCのデフォルトの実行コンテキストの指定のRTCへの関連付けを解除する}
     * {@.en Remove the RT-Component from participant list of  ExecutionContext}
     *
     * 
     * @param localcomp
     *   {@.ja 対象のRTコンポーネント}
     *   {@.en Target RT-Component's instances}
     * 
     * @param othercomp 
     *   {@.ja 実行コンテキストとの関連付けを解除するRTコンポーネント}
     *   {@.en The target RT-Component for delete}
     * 
     * @return 
     *   {@.ja ecの取得に失敗した場合はRTC_ERRORを返す
     * そうでない場合はremoveComponent関数の戻り値を返す。RTC_OKで接続成功。}
     *   {@.en The return code of ReturnCode_t type}
     *
     */
    public static ReturnCode_t remove_rtc_to_default_ec(RTObject localcomp, 
            RTObject othercomp){
        ExecutionContext ec = get_actual_ec(localcomp);
        if(ec==null){
            return ReturnCode_t.RTC_ERROR;
        }
        return ec.remove_component(othercomp);
    }
    /**
     *
     * {@.ja RTCのデフォルトの実行コンテキストに参加しているRTCのリストを取得する}
     * {@.en Getting participant RTC list}
     * <p>
     * {@.ja 実行コンテキストがnilの場合は空のリストを返す}
     *
     * 
     * @param rtc
     *   {@.ja RTコンポーネント}
     *   {@.en Target RT-Component's instances}
     * 
     * @return 
     *   {@.ja RTCのリスト}
     *   {@.en Participants RTC list}
     */
    public static RTObject[] get_participants_rtc(RTObject rtc){
        ExecutionContext ec = get_actual_ec(rtc);
        if(ec==null){
            return null;
        }
        ExecutionContextService ecs;
        ecs = ExecutionContextServiceHelper.narrow(ec);
        if(ecs==null){
            return null;
        }
        ExecutionContextProfile prifile = ecs.get_profile();
        return prifile.participants;
    }
    /**
     *
     * {@.ja 指定したRTCの保持するポートの名前を取得} 
     * {@.en Gets the port name a specified RTC maintains.}
     * 
     * @param rtc 
     *   {@.ja 対象のRTコンポーネント}
     *   {@.en Target RT-Component's instances}
     * 
     * @return 
     *   {@.ja ポート名のリスト}
     *   {@.en List of port names}
     * 
     */
    public static Vector<String> get_port_names(RTObject rtc){
        if(rtc==null){
            return null;
        }
        Vector<String> names = new Vector<String>();
        PortServiceListHolder ports = new PortServiceListHolder();
        ports.value = rtc.get_ports();
        for (int ic=0; ic < ports.value.length; ++ic) {
            PortProfile pp = ports.value[ic].get_port_profile();
            String str = pp.name;
            names.add(str);
        }
        return names;
    }
    /**
     *
     * {@.ja 指定したRTCの保持するインポートの名前を取得}
     * {@.en Gets the inport name a specified RTC maintains.}
     * 
     * @param rtc
     *   {@.ja 対象のRTコンポーネント}
     *   {@.en Target RT-Component's instances}
     * 
     * @return 
     *   {@.ja ポート名のリスト}
     *   {@.en List of port names}
     * 
     *
     */
    public static Vector<String> get_inport_names(RTObject rtc) {
        if(rtc==null){
            return null;
        }
  
        Vector<String> names = new Vector<String>();
        PortServiceListHolder ports = new PortServiceListHolder();
        ports.value = rtc.get_ports();
        for (int ic=0; ic < ports.value.length; ++ic) {
            PortProfile pp = ports.value[ic].get_port_profile();
            NVListHolder nvholder = 
                    new NVListHolder(pp.properties);
            Properties prop = new Properties();
            NVUtil.copyToProperties(prop, nvholder);
            String str = prop.getProperty("port.port_type");
            if(str.equals("DataInPort")){
                names.add(pp.name);
            }
        }
        return names;
    }
    /**
     *
     * {@.ja 指定したRTCの保持するアウトポートの名前を取得}
     * {@.en Gets the outport name a specified RTC maintains.}
     *
     * @param rtc
     *   {@.ja 対象のRTコンポーネント}
     *   {@.en Target RT-Component's instances}
     * 
     * @return 
     *   {@.ja ポート名のリスト}
     *   {@.en List of port names}
     *
     */
    public static  Vector<String> get_outport_names(RTObject rtc){
        if(rtc==null){
            return null;
        }
        Vector<String> names = new Vector<String>();
        PortServiceListHolder ports = new PortServiceListHolder();
  
        ports.value = rtc.get_ports();
        for (int ic=0; ic < ports.value.length; ++ic) {
            PortProfile pp = ports.value[ic].get_port_profile();
            NVListHolder nvholder = 
                    new NVListHolder(pp.properties);
            Properties prop = new Properties();
            NVUtil.copyToProperties(prop, nvholder);
            String str = prop.getProperty("port.port_type");
            if(str.equals("DataOutPort")){
                names.add(pp.name);
            }
        }
        return names;
    }


    /**
     * {@.ja 指定したRTCの保持するサービスポートの名前を取得}
     * {@.en Gets names of service port a specified RTC maintains.}
     *
     * @param rtc
     *   {@.ja 対象のRTコンポーネント}
     *   {@.en Target RT-Component's instances}
     * 
     * @return 
     *   {@.ja ポート名のリスト}
     *   {@.en List of port names}
     */
    public static Vector<String> get_svcport_names(RTObject rtc){
        if(rtc==null){
            return null;
        }
  
        Vector<String> names = new Vector<String>();
  
        PortServiceListHolder ports = new PortServiceListHolder();
        ports.value = rtc.get_ports();
        for (int ic=0; ic < ports.value.length; ++ic) {
            PortProfile pp = ports.value[ic].get_port_profile();
            NVListHolder nvholder = 
                    new NVListHolder(pp.properties);
            Properties prop = new Properties();
            NVUtil.copyToProperties(prop, nvholder);
            String str = prop.getProperty("port.port_type");
            if(str.equals("CorbaPort")){
                names.add(pp.name);
            }
        }
        return names;
    }

    /**
     *
     * {@.ja 対象のRTCから指定した名前のポートを取得}
     * {@.en Get a port of the specified name from ports Component maintains.}
     *
     * @param rtc
     *   {@.ja RTコンポーネント}
     *   {@.en Target RT-Component's instances}
     * 
     * @param name 
     *   {@.ja ポート名}
     *   {@.en the name of port}
     * 
     * @return
     *   {@.ja ポート}
     *   {@.en PortService}
     *
     * RTC::PortService_var get_port_by_name(const RTC::RTObject_ptr rtc, std::string name)
     */
    public static PortService get_port_by_name(RTObject rtc, String name){
        if(rtc==null){
            return null;
        }
        PortServiceListHolder ports = new PortServiceListHolder();
        ports.value = rtc.get_ports();
        for (int ic=0; ic < ports.value.length; ++ic) {
            PortProfile pp = ports.value[ic].get_port_profile();
            if(pp.name.equals(name)){
                return ports.value[ic];
            }
        }
        return null;
    }
    /**
     *
     * {@.ja 指定したポートの保持しているコネクタの名前のリストを取得}
     * {@.en Gets a list of the ports name specified port maintains.}
     *
     * @param port 
     *   {@.ja 対象のポート}
     *   {@.en Target Port}
     * 
     * @return 
     *   {@.ja コネクタ名のリスト}
     *   {@.en List of port names}
     *
     *
     */
    public static Vector<String> get_connector_names(PortBase port){
        if(port==null){
            return null;
        }
        Vector<String> names = new Vector<String>();
        ConnectorProfileListHolder conprof =
                new ConnectorProfileListHolder();
        conprof.value = port.get_connector_profiles();
        for(int ic=0;ic<conprof.value.length;++ic){
            names.add(conprof.value[ic].name);
        }
        return names;
    }

    /**
     *
     * {@.ja 指定したポートの保持しているコネクタのIDのリストを取得}
     * {@.en Gets a list of the connectorIDs specified port maintains.}
     *
     * 
     * @param port 対象のポート
     *   {@.ja 対象のポート}
     *   {@.en Target Port}
     * 
     * @return 
     *   {@.ja コネクタのIDのリスト}
     *   {@.en List of connectorIDs}
     * 
     *
     */
    public static Vector<String> get_connector_ids(PortBase port){
        if(port == null){
            return null;
        }
        Vector<String> ids = new Vector<String>();
        ConnectorProfileListHolder conprof =
                new ConnectorProfileListHolder();
        conprof.value = port.get_connector_profiles();
        for(int ic=0;ic<conprof.value.length;++ic){
            ids.add(conprof.value[ic].connector_id);
        }
        return ids;
    }
    /**
     *
     * {@.ja 指定したポートを接続するためのコネクタプロファイルを取得}
     * {@.en Gets ConnectorProfile for connect specified ports}
     *
     * 
     * @param name 
     *   {@.ja コネクタ名}
     *   {@.en connector name}
     * 
     * @param prop_arg 
     *   {@.ja 設定}
     *   {@.en connection properties}
     * 
     * @param port0 
     *   {@.ja 対象のポート1}
     *   {@.en Target Port}
     * 
     * @param port1 
     *   {@.ja 対象のポート2}
     *   {@.en Target Port}
     *
     * @return 
     *   {@.ja コネクタプロファイル}
     *   {@.en ConnectorProfile}
     *
     *
     * RTC::ConnectorProfile_var create_connector(const std::string name,const coil::Properties prop_arg,const RTC::PortService_ptr port0,const RTC::PortService_ptr port1)
     */
    public static ConnectorProfile create_connector(String name, 
            Properties prop_arg, PortService port0, PortService port1){
        Properties prop = new Properties(prop_arg);
        String connector_id = new String();
        PortService[] ports = new PortService[2];
        ports[0] = port0;
        ports[1] = port1;
        NameValue[] properties = new NameValue[0];
        ConnectorProfile conn_prof = new  ConnectorProfile(
                name,
                connector_id,
                ports,
                properties 
        );

        if(prop.getProperty("dataport.dataflow_type").isEmpty()){
            prop.setProperty("dataport.dataflow_type","push");
        }
 

        if(prop.getProperty("dataport.interface_type").isEmpty()){
            prop.setProperty("dataport.interface_type","corba_cdr");
        }

        NVListHolder nvlist = new NVListHolder();
        NVUtil.copyFromProperties(nvlist, prop);
        conn_prof.properties = nvlist.value;
  
        return conn_prof;
  
    }
                                            
    /**
     *
     * {@.ja 指定したポート同士が接続されているかを判定}S
     * {@.en Cconfirms the connection in specified ports.}
     *
     * 
     * @param localport
     *   {@.ja 対象のポート1}
     *   {@.en Target Port}
     * 
     * @param otherport 
     *   {@.ja 対象のポート2}
     *   {@.en Target Port}
     * 
     * @return 
     *   {@.ja true: 接続済み、false: 未接続}
     *   {p.en already connected:true}
     *
     */
    public static boolean already_connected(PortService localport, 
            PortService otherport){
        ConnectorProfileListHolder conprof =
                new ConnectorProfileListHolder();
        conprof.value = localport.get_connector_profiles();
        for(int ic=0;ic<conprof.value.length;++ic){
            ConnectorProfile cprof = conprof.value[ic];
            for(int icc=0;icc<cprof.ports.length;++icc){
                if(cprof.ports[icc]._is_equivalent(otherport)){
                    return true;
                }
            }
        }
        return false;
    } 
    /**
     *
     *
     * {@.ja 指定したポートを接続する}
     * {@.en Connects specified ports.}
     *
     * 
     * @param name 
     *   {@.ja コネクタ名}
     *   {@.en connector name}
     * 
     * @param prop 
     *   {@.ja 設定}
     *   {@.en connection properties}
     * 
     * @param port0 
     *   {@.ja 対象のポート1}
     *   {@.en Target Port}
     * 
     * @param port1 
     *   {@.ja 対象のポート2}
     *   {@.en Target Port}
     * 
     * @return 
     *   {@.ja RTC、ECのオブジェクトリファレンスがnilの場合はBAD_PARAMETERを返す
     * nilではない場合はport0.connect関数の戻り値を返す。RTC_OKの場合は接続が成功}
     *   {@.en Return code}
     *
     * RTC::ReturnCode_t connect(const std::string name,const coil::Properties prop,const RTC::PortService_ptr port0,const RTC::PortService_ptr port1)
     */
    public static ReturnCode_t connect(String name, Properties prop, 
            PortService port0, PortService port1){
        if(port0 == null){
            return ReturnCode_t.BAD_PARAMETER;
        }
        if(port1 == null){
            return ReturnCode_t.BAD_PARAMETER;
        }
        if(port0._is_equivalent(port1)){
            return ReturnCode_t.BAD_PARAMETER;
        }
        ConnectorProfileHolder profileholder = new ConnectorProfileHolder();
        profileholder.value = create_connector(name, prop, port0, port1);
        return port0.connect(profileholder);

    }
    /**
     *
     * {@.ja 指定したポートと指定したリスト内のポート全てと接続する}
     *
     * 
     * @param name コネクタ名
     *   {@.ja コネクタ名}
     * 
     * @param prop 
     *   {@.ja 設定}
     * 
     * @param port0 
     *   {@.ja 対象のポート}
     * 
     * @param port1 
     *   {@.ja 対象のポートのリスト}
     * 
     * @return 
     *   {@.ja 全ての接続が成功した場合はRTC_OKを返す。
     * connect関数がRTC_OK以外を返した場合はRTC_ERRORを返す。}
     *
     *
     * RTC::ReturnCode_t connect_multi(const std::string name,const coil::Properties prop,const RTC::PortService_ptr port,RTC::PortServiceList_var& target_ports)
     */
/*
    public static connect_multi(name, prop, port, target_ports){
        ret = RTC.RTC_OK
  
        for p in target_ports{
            if p._is_equivalent(port){
                continue
            }
            if already_connected(port, p){
                continue
            }
            if RTC.RTC_OK != connect(name, prop, port, p){
                ret = RTC.RTC_ERROR
            }
        }
        return ret;
    }
*/
}
/**
 * {@.ja ポートを名前から検索}
 *
 */
//public static class find_port {
  
    /**
     *
     * {@.ja コンストラクタ}
     * 
     * <p>
     * {@.ja 検索するポート名を指定する}
     *
     * @param name 
     *   {@.ja ポート名}
     *
     * find_port(const std::string name)
     */
/*
    public static __init__(self, name){
        self._name = name
    }
*/
    /**
     *
     * {@.ja 対象のポートの名前と指定したポート名が一致するか判定}
     * 
     * @param p 
     *   {@.ja 対象のポート}
     * 
     * @return 
     *   {@.ja true: 名前が一致、false:　名前が不一致}
     * 
     *
     * bool operator()(RTC::PortService_var p)
     */
/*
    public static __call__(self, p){
        prof = p.get_port_profile()
        c = prof.name
    
        return (self._name == c);
    }
*/ 
    /**
     *
     * {@.ja 対象のRTCの指定した名前のポートを接続する}
     *
     * @param name 
     *   {@.ja コネクタ名}
     * 
     * @param prop 
     *   {@.ja 設定}
     * 
     * @param rtc0 
     *   {@.ja 対象のRTCコンポーネント1}
     * 
     * @param portName0 
     *   {@.ja 対象のポート名1}
     * 
     * @param rtc1 
     *   {@.ja 対象のRTCコンポーネント2}
     * 
     * @param portName1 
     *   {@.ja 対象のRTCコンポーネント2}
     * 
     * @return 
     *   {@.ja RTC、ポートがnilの場合はBAD_PARAMETERを返す。
     * nilではない場合はport0.connect関数の戻り値を返す。RTC_OKの場合は接続が成功}
     *
     *
     * RTC::ReturnCode_t connect_by_name(std::string name, coil::Properties prop,RTC::RTObject_ptr rtc0,const std::string portName0,RTC::RTObject_ptr rtc1,const std::string portName1)
     */
/*
    public static connect_by_name(name, prop, rtc0, portName0, RTObject rtc1, portName1){
        if CORBA.is_nil(rtc0){
            return RTC.BAD_PARAMETER;
        }
        if CORBA.is_nil(rtc1){
            return RTC.BAD_PARAMETER;
        }
        port0 = get_port_by_name(rtc0, portName0)
        if CORBA.is_nil(port0){
            return RTC.BAD_PARAMETER;
        }

        port1 = get_port_by_name(rtc1, portName1)
        if CORBA.is_nil(port1){
            return RTC.BAD_PARAMETER;
        }

        return connect(name, prop, port0, port1);

    }
*/
    /**
     *
     * {@.ja 指定のコネクタを切断する}
     *
     * 
     * @param connector_prof 
     *   {@.ja コネクタプロファイル}
     * 
     * @return 
     *   {@.ja コネクタプロファイルで保持しているポートのオブジェクトリファレンスがnilの場合はBAD_PARAMETERを返す
     * nilではない場合はports[0].disconnect関数の戻り値を返す。RTC_OKの場合は切断が成功}
     *
     *
     */
/*
    public static disconnect(connector_prof){
        ports = connector_prof.ports
        return disconnect_by_connector_id(ports[0], connector_prof.connector_id);
    }  
*/  

    /**
     *
     * {@.ja 対象のポートで指定した名前のコネクタを切断}
     *
     * @param port 
     *   {@.ja 対象のポート}
     * 
     * @param name 
     *   {@.ja コネクタ名}
     * 
     * @return 
     *   {@.ja portがnilの場合はBAD_PARAMETERを返す
     * nilではない場合はdisconnect関数の戻り値を返す。RTC_OKの場合は切断が成功}
     *
     *
     */
/*
    public static disconnect_by_connector_name(port, name){
        if CORBA.is_nil(port){
            return RTC.BAD_PARAMETER;
        }
        conprof = port.get_connector_profiles()
        for c in conprof{
            if c.name == name{
                return disconnect(c);
            }
        }
        return RTC.BAD_PARAMETERS;

    }
*/

    /**
     *
     * {@.ja 対象のポートで指定したIDのコネクタを切断}
     *
     * 
     * @param port 
     *   {@.ja 対象のポート}
     * 
     * @param name 
     *   {@.ja コネクタID}
     * 
     * @return 
     *   {@.ja portがnilの場合はBAD_PARAMETERを返す
     * nilではない場合はdisconnect関数の戻り値を返す。RTC_OKの場合は切断が成功}
     *
     *
     */
/*
    public static disconnect_by_connector_id(port, id){
        if CORBA.is_nil(port){
            return RTC.BAD_PARAMETER;
        }
        return port.disconnect(id);

    }
*/
    /**
     *
     * {@.ja 対象ポートと接続しているポートで指定したポート名と一致した場合に切断}
     *
     * 
     * @param localport 
     *   {@.ja 対象のポート}
     *
     * @param othername 
     *   {@.ja 接続しているポート名}
     *
     * @return 
     *   {@.ja ポートがnilの場合、localportの名前とothernameが一致する場合、接続しているポートの名前でothernameと一致するものがない場合にBAD_PARAMETERを返す
     * 上記の条件に当てはまらない場合はdisconnect関数の戻り値を返す。RTC_OKの場合は切断が成功}
     *
     *
     */
/*
    public static disconnect_by_port_name(localport, othername){
        if CORBA.is_nil(localport){
            return RTC.BAD_PARAMETER;
        }
        prof = localport.get_port_profile()
        if prof.name == othername{
            return RTC.BAD_PARAMETER;
        }
  
        conprof = localport.get_connector_profiles()
        for c in conprof{
            for p in c.ports{
                if not CORBA.is_nil(p){
                    pp = p.get_port_profile()
                    if pp.name == othername{
                        return disconnect(c);
                    }
                }
            }
        }
        return RTC.BAD_PARAMETER;
    }
*/
    /**
     *
     * {@.ja 指定したRTコンポーネントのコンフィギュレーション取得}
     * 
     * @param rtc 
     *   {@.ja 対象のRTコンポーネント}
     *
     * @return 
     *   {@.ja rtcがnilの場合はNoneを返す。
     * nilではない場合はコンフィギュレーションを返す。}
     *
     *
     *
     */
/*
    public static get_configuration(RTObject rtc){
        if CORBA.is_nil(rtc){
            return SDOPackage.Configuration._nil;
        }
  
        return rtc.get_configuration();
    }
*/
    /**
     *
     * {@.ja 指定したコンフィギュレーションセット名、パラメータ名のコンフィギュレーションパラメータを取得}
     * <p>
     * {@.ja コンポーネントのプロパティ取得}
     * 
     * @param conf 
     *   {@.ja コンフィギュレーション}
     * 
     * @param confset_name 
     *   {@.ja コンフィギュレーションセット名}
     * 
     * @param value_name 
     *   {@.ja パラメータ名}
     * 
     * @return パラメータ
     *   {@.ja パラメータ}
     * 
     */
/*
    public static get_parameter_by_key(RTObject rtc, confset_name, value_name){
        conf = rtc.get_configuration()
  
    
        confset = conf.get_configuration_set(confset_name)
        confData = confset.configuration_data
        prop = OpenRTM_aist.Properties()
        OpenRTM_aist.NVUtil.copyToProperties(prop, confData)
        return prop.getProperty(value_name);
    
    }  
*/

    /**
     *
     * {@.ja 対象のRTCのアクティブなコンフィギュレーションセット名を取得する}
     *
     * @param rtc 
     *   {@.ja RTコンポーネント}
     * 
     * @return 
     *   {@.ja コンフィギュレーションセット名
     * コンフィギュレーションの取得に失敗した場合は空の文字列を返す}
     * 
     *
     *
     */
/*
    public static get_current_configuration_name(RTObject rtc){
        conf = rtc.get_configuration()
        confset = conf.get_active_configuration_set()
        return confset.id;
    }
*/
    /**
     *:
     *
     * {@.ja アクティブなコンフィギュレーションセットを取得}
     *
     * @param rtc 
     *   {@.ja 対象のRTコンポーネント}
     * 
     * @return 
     *   {@.ja アクティブなコンフィギュレーションセット}
     * 
     *
     *
     */
/*
    public static get_active_configuration(RTObject rtc){
        conf = rtc.get_configuration()

        confset = conf.get_active_configuration_set()
        confData = confset.configuration_data
        prop = OpenRTM_aist.Properties()
        OpenRTM_aist.NVUtil.copyToProperties(prop, confData)
        return prop;
    
    }
*/
    /**
     *
     * {@.ja コンフィギュレーションパラメータを設定}
     *
     * @param confset_name 
     *   {@.ja コンフィギュレーションセット名}
     * 
     * @param value_name 
     *   {@.ja パラメータ名}
     * 
     * @param value パラメータ
     *   {@.ja パラメータ}
     * 
     * @return 
     *   {@.ja true:設定に成功、false:設定に失敗}
     * 
     *
     */
/*
    public static set_configuration(rRTObject tc, confset_name, value_name, value){
        conf = rtc.get_configuration()
  
        confset = conf.get_configuration_set(confset_name)
        confData = confset.configuration_data
        prop = OpenRTM_aist.Properties()
        OpenRTM_aist.NVUtil.copyToProperties(prop, confData)
        prop.setProperty(value_name,value)
        OpenRTM_aist.NVUtil.copyFromProperties(confData,prop)
        confset.configuration_data = confData
        conf.set_configuration_set_values(confset)
  
        conf.activate_configuration_set(confset_name)
        return true;
    }    
*/
//}    
