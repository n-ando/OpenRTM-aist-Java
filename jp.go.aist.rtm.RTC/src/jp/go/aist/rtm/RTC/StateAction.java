package jp.go.aist.rtm.RTC;

/**
* <p>ステートマシン起動アクション用インターフェースです。</p>
* @param STATE ステートマシンが管理する状態の型(LifeCycleState)
*/
public interface StateAction<STATE> { 
    /**
     * <p>ステートマシンから起動されるアクションです。</p>
     * 
     * @param state  状態遷移履歴
     */
    public void doAction(StateHolder<STATE> state);
}
