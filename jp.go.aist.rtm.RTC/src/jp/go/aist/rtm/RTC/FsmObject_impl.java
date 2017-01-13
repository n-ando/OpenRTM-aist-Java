package jp.go.aist.rtm.RTC;

import RTC.FsmObject;
import RTC.FsmObjectHelper;
import RTC.FsmObjectPOA;

import RTC.ReturnCode_t;
  /**
   * {@.ja RTコンポーネントクラス。}
   * {@.en RT-Component class}
   *
   * <p>
   * {@.ja FiniteStateMachineのベースクラス。
   * ユーザが新たなRTコンポーネントを作成する場合は、このクラスを拡張する。
   * 各RTコンポーネントのベースとなるクラス。}
   * {@.en This is a class to be a base of each RT-Component.
   * This is a implementation class of lightweightRTComponent in Robotic
   * Technology Component specification.}
   */
public class FsmObject_impl extends FsmObjectPOA {


    /**
     * {@.ja コンストラクタ。}
     * {@.en Constructor}
     */
    public FsmObject_impl() {
    }
    /**
     * {@.ja send_stimulus}
     * {@.en send_stimulus}
     * <p>
     * {@.ja Send a stimulus to an FSM that realizes this interface.
     * If the stimulus corresponds to any outgoing transition of the
     * current state, that transition shall be taken and the state
     * shall change. Any FSM participants associated with the exit of
     * the current state, the transition to the new state, or the
     * entry to the new state shall be invoked. If the stimulus does
     * not correspond to any such transition, this operation shall
     * succeed but have no effect.  
     *
     * If the given execution context is a non-nil reference to a
     * context in which this FSM participates, the transition shall be
     * executed in that context. If the argument is nil, the FSM shall
     * choose an EVENT_DRIVEN context in which to execute the
     * transition. If the argument is non-nil, but this FSM does not
     * participate in the given context, this operation shall fail
     * with * ReturnCode_t::BAD_PARAMETER.}
     * {@.en Send a stimulus to an FSM that realizes this interface.
     * If the stimulus corresponds to any outgoing transition of the
     * current state, that transition shall be taken and the state
     * shall change. Any FSM participants associated with the exit of
     * the current state, the transition to the new state, or the
     * entry to the new state shall be invoked. If the stimulus does
     * not correspond to any such transition, this operation shall
     * succeed but have no effect.  
     *
     * If the given execution context is a non-nil reference to a
     * context in which this FSM participates, the transition shall be
     * executed in that context. If the argument is nil, the FSM shall
     * choose an EVENT_DRIVEN context in which to execute the
     * transition. If the argument is non-nil, but this FSM does not
     * participate in the given context, this operation shall fail
     * with * ReturnCode_t::BAD_PARAMETER.}
     *
     * @param ids 
     *   {@.ja Event Message}
     *   {@.en Event Message}
     *
     * @param id 
     *   {@.ja EC handle}
     *   {@.en EC handle}
     *
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     *
     *
     */
    public ReturnCode_t send_stimulus(String ids, int id) { 
        return ReturnCode_t.RTC_OK; 
    }
    
}
