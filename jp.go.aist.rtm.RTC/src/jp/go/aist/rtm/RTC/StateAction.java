package jp.go.aist.rtm.RTC;

/**
* {@.ja ステートマシン起動アクション用インターフェース}
* {@.en Interface for state machine start action}
* @param STATE 
*   {@.ja ステートマシンが管理する状態の型(LifeCycleState)}
*   {@.en Type in state that state machine manages}
*/
public interface StateAction<STATE> { 
    /**
     * {@.ja ステートマシンから起動されるアクション。}
     * {@.en This is an action started from the state machine.}
     * 
     * @param state  
     *   {@.ja 状態}
     *   {@.en State holder}
     */
    public void doAction(StateHolder<STATE> state);
}
