package jp.go.aist.rtm.RTC;

/**
* <p>ステートマシン起動アクション用インターフェースです。</p>
* @param STATE ステートマシンが管理する状態の型(LifeCycleState)
* @param RESULT アクションの実行結果(ReturnCode_t)
*/
public interface StateAction<STATE, RESULT> {
    /**
     * <p>ステートマシンから起動されるアクションです。</p>
     * 
     * @param state  状態遷移履歴
     * 
     * @return 処理結果
     */
    public RESULT doAction(StateHolder<STATE> state);
}
