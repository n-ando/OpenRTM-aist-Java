package jp.go.aist.rtm.RTC.jfsm.machine;

import jp.go.aist.rtm.RTC.jfsm.Event;
import jp.go.aist.rtm.RTC.jfsm.State;

/**
 * ユーザー定義状態のスーパークラス。
 */
public class StateBase {

    protected StateInfo stateInfo;

    protected StateBase() {
    }

    void setStateInfo(StateInfo stateInfo) {
        // StateBaseのコンストラクタではStateInfoを渡せない
        // (ユーザー定義状態クラスのコンストラクタを書けないため)。
        // そのためMachineBaseでインスタンス生成するタイミングで設定するようにする
        assert this.stateInfo == null;
        this.stateInfo = stateInfo;
    }

    /**
     * <p>状態遷移する。イベントハンドラ内で使用する。</p>
     * <p>実際の遷移にするタイミングは、このメソッドを呼び出したイベントハンドラが終了した直後となる。
     * このメソッドでは、(ハンドラ後に)遷移する先を登録する。</p>
     *
     * @param state 遷移先の状態。
     *
     */
    protected void setState(State state) {
        final StateInfo info = state.getInfo(this.stateInfo.machine);
        this.stateInfo.machine.setPendingState(info, true, state.takeData());
    }

    /**
     * <p>履歴を無視して状態遷移する。イベントハンドラ内で使用する。</p>
     * <p>実際の遷移にするタイミングは、このメソッドを呼び出したイベントハンドラが終了した直後となる。
     * このメソッドでは、(ハンドラ後に)遷移する先を登録する。</p>
     *
     * @param state 遷移先の状態。
     */
    protected void setStateDirect(State state) {
        final StateInfo info = state.getInfo(this.stateInfo.machine);
        this.stateInfo.machine.setPendingState(info, false, state.takeData());
    }

    protected <T> T data() {
        return (T) stateInfo.getData();
    }

    protected <T> T data(Class<?> state) {
        return (T) stateInfo.machine.getInfo(state).getData();
    }

    public void onInit() {
    }

    public void onEntry() {
    }

    public void onExit() {
    }

    /**
     * @param event イベントオブジェクト。
     * @param name イベント名。イベント事にユニークな名前をつける。
     */
    public void defer(Event event, String name) {
//        # default value for parameter name is placed in StateDef.defer()
//        assert event
//        self._state_info.machine.add_deferred_event(event, name)
        assert event != null;
        this.stateInfo.machine.addDeferredEvent(event, name);
    }

    public void defer(Event event) {
        this.defer(event, "event");
    }

    String getStateName() {
        return getClass().getSimpleName();
    }

    /**
     * Machine内部で使用するメソッド。アプリケーションから呼び出してはならない。
     *
     * saveHistory()とsetHistorySuper()の組み合わせで、履歴の戦略の切り替えを実現する。
     */
    // リフレクション(getMethod)で取得するため、可視性をpublicとしている。
    public void setHistorySuper(StateInfo self, StateInfo deep) {
        // no implementation for StateBase
    }

    /**
     * Machine内部で使用するメソッド。アプリケーションから呼び出してはならない。
     *
     * saveHistory()とsetHistorySuper()の組み合わせで、履歴の戦略の切り替えを実現する。
     */
    protected void saveHistory(StateInfo self, StateInfo shallow, StateInfo deep) {
        // no implementation for StateBase
    }

    void deleteData(StateInfo stateInfo) {
        stateInfo.deleteData();
    }
}
