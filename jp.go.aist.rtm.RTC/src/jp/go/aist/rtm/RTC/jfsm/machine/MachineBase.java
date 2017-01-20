package jp.go.aist.rtm.RTC.jfsm.machine;

import static java.lang.String.format;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import jp.go.aist.rtm.RTC.ExtendedFsmServiceProvider;
import jp.go.aist.rtm.RTC.jfsm.Event;
import jp.go.aist.rtm.RTC.jfsm.State;
import jp.go.aist.rtm.RTC.jfsm.StateDef;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import jp.go.aist.rtm.RTC.log.Logbuf;

//public class MachineBase extends ExtendedFsmServiceProvider {
public class MachineBase {

    //private static final Logger LOGGER = LoggerFactory.getLogger(MachineBase.class);

    protected StateInfo currentState;
    private final Map<Class, StateInfo> states = new HashMap<>();
    private StateInfo pendingState;
    private final Map<String, EventBase> deferredEvents = new HashMap<>();
    private final Deque<String> deferredNames = new ArrayDeque<>();

    protected final Class<? extends StateBase> topState;
    private boolean pendingHistory;

    private Event pendingEvent;

    private Object pendingData;

    protected MachineBase(Class<? extends StateBase> topState) {
        this.topState = topState;
        rtcout = new Logbuf("MachineBase");

    }

    /**
     * 対応するStateInfoを取得する。
     *
     * @param stateClass この状態についての情報を取得する。
     * @return 対応するStateInfo。
     */
    protected final StateInfo getInfo(Class stateClass) {
        StateInfo info = states.get(stateClass);
        if (info == null) {
            info = stateClass.equals(StateDef.class)
                    ? new RootStateInfo(this, null) : new SubStateInfo(stateClass, this, getInfo(stateClass.getSuperclass()));
            setInfo(stateClass, info);
        }
        return info;
    }

    public void setInfo(Class stateClass, StateInfo info) {
        states.put(stateClass, info);
    }

//    #  void MachineBase::set_state(StateInfo & info, bool history, void * data) {
    public void setState(StateInfo info, boolean history, Object data) {
//    #      set_pending_state(info, history, data);
//    #      perform_pending();
        setPendingState(info, history, data);
        performPending();
    }

//        # statechart.h: 695
//        #  void set_pending_state(StateInfo & info, bool history, void * data) {
    protected void setPendingState(StateInfo info, boolean history, Object data) {
//        #      assert((!pending_state_ || pending_state_ == &info) &&
//        #      "There is already a state transition pending!");
        assert (pendingState == null || pendingState == info) : "There is already a state transition pending!";
//        #      pending_state_ = &info;
//        #      pending_data_ = data;
//        #      pending_history_ = history;
        pendingState = info;
        pendingData = data;
        pendingHistory = history;
    }

    protected void performPending() {
//        #    assert(current_state_);
        assert currentState != null;
//        #    if (pending_state_) {
        if (pendingState != null) {
//        #      // Loop here because init actions might change state again.
//        #      while (pending_state_) {
            while (pendingState != null) {
//        #        HRTM_DEBUG(LOGGER, current_state_->name() << ": transition to: " <<
//        #            pending_state_->name());
                rtcout.println(Logbuf.DEBUG, currentState.getName()
                                             + ": transition to: " 
                                             + pendingState.getName());
                //LOGGER.debug(format("%s: transition to: %s", currentState.getName(), pendingState.getName()));
//        #        // Perform exit actions (which exactly depends on new state).
//        #        current_state_->on_exit(*pending_state_);
                currentState.onExit(pendingState);
//        #        // Store history information for previous state now.
//        #        // Previous state will be used for deep history.
//        #        current_state_->set_history_super(*current_state_);
                currentState.setHistorySuper(currentState);
//        #        StateInfo * previous = current_state_;
                StateInfo previous = currentState;
//        #        current_state_ = pending_state_;
                currentState = pendingState;
//        #        if (pending_data_) {
//        #          current_state_->set_data(pending_data_);
//        #        }
                if (pendingData != null) {
                    currentState.setData(pendingData);
                }
//        #        // Perform entry actions (which exactly depends on previous state).
//        #        current_state_->on_entry(*previous);
                currentState.onEntry(previous);
//        #        // State transition complete.
//        #        // Clear 'pending' information just now so that set_state would assert
//        #        // in exits and entries, but not in init.
//        #        pending_state_ = 0;
                pendingState = null;
//        #        pending_data_ = 0;
                pendingData = null;
//        #        bool history = pending_history_;
                boolean history = pendingHistory;
//        #        pending_history_ = false;
                pendingHistory = false;
//        #        // "init" may change state again.
//        #        current_state_->on_init(history);
                currentState.onInit(history);
//        #        assert("Init may only transition to proper substates" &&
//        #            (!pending_state_ ||
//        #             (pending_state_->is_child(*current_state_) &&
//        #             (current_state_ != pending_state_))));
                if (pendingState != null && pendingState.isChild(currentState)) {
                    assert currentState != pendingState : "Init may only transition to proper substates";
                }
//        #        // Perform pending event
//        #        perform_pending_event();
                peformPendingEvent();
//        #        // Perform deferred events
//        #        perform_deferred_events();
                performDeferredEvents();
//        #      }  // while (pending_state_)
            }
//        #    } else {  // if (pending_state_)
        } else {
//        #      // Perform pending event
//        #      perform_pending_event();
            peformPendingEvent();
//        #    }
//        #  }

        }

    }

    public void addDeferredEvent(Event event, String name) {
//        try:
//            queue = self._deferred_events.get(name, None)
//            if queue:
//                logger.debug("{0} is already deferred.".format(name))
//                return
//            self._deferred_events[name] = event
//            self._deferred_names.append(name)
//        except Exception as e:
//            logger.debug("Can't add defferd event by {0}".format(e))
        if (this.deferredNames.contains(name)) {
            rtcout.println(Logbuf.DEBUG, "{} is already deferred. " + name);
            //LOGGER.debug("{} is already deferred.", name);
            return;
        }
        this.deferredEvents.put(name, event);
        this.deferredNames.offerLast(name);
    }

    void performDeferredEvents() {
//        if not self._deferred_events:
//            return
        if (this.deferredEvents.isEmpty()) {
            return;
        }
//        for name in self._deferred_names:
//            if self._current_state:
//                self._deferred_events[name].dispatch(self._current_state)
        final Iterator<String> it = this.deferredNames.iterator();
        while (it.hasNext()) {
            final String name = it.next();
            if (this.currentState != null) {
                this.deferredEvents.get(name).dispatch(this.currentState);
            }
        }
//        self._deferred_events.clear()
//        del self._deferred_names[:]  # effectively self._deferred_names.clean() (not in Python2.7)
        this.deferredEvents.clear();
        this.deferredNames.clear();
    }

    public Object data() {
        return currentState.getInstance().data(topState);
    }

    void setPendingEvent(Event event) {
        assert this.pendingEvent == null;
        this.pendingEvent = event;
    }

    private void peformPendingEvent() {
        if (this.pendingEvent != null) {
            final EventBase event = this.pendingEvent;
            this.pendingEvent = null;
            event.dispatch(currentState);
        }
    }

//        # statechart.cc: 151
//        #  void MachineBase::shutdown() {
    public void shutdown() {
//        #    assert(!pending_state_);
        assert pendingState == null;

//        #    HRTM_DEBUG(LOGGER, "Shutting down Machine");
//        #    // Performs exit actions by going to Root (=State) state.
//        #    set_state(StateBase::get_info(*this), false, 0);
//        #    current_state_ = 0;
        rtcout.println(Logbuf.DEBUG, "Shutting down Machine");
        //LOGGER.debug("Shutting down Machine");
        this.setState(getInfo(StateDef.class), false, null);
        this.currentState = null;
    }

    /**
     * イベントを発生させる。
     *
     * @param event 発生させるイベントオブジェクト。
     */
    public void dispatch(Event event) {
        // C++版ではMachineのメソッドだが、EventBase#dispatch(StateInfo)を隠蔽するためにここで実装している。
        final EventBase base = event;
        assert base != null;
        base.dispatch(currentState);
        this.performPending();
    }

    /**
     * <p>保存された履歴を削除する。</p>
     * <p>C++の実装ではLinkクラスのstaticメソッドだが、Javaの制限により、Machineクラスのメソッドとしている。</p>
     *
     * @param stateClass 履歴を削除したいユーザー定義状態クラス
     */
    public void clearHistory(Class<? extends StateBase> stateClass) {
        getInfo(stateClass).setHistory(null);
    }

    /**
     * <p>保存された深い履歴を削除する。</p>
     * <p>C++の実装ではLinkクラスのstaticメソッドだが、Javaの制限により、Machineクラスのメソッドとしている。</p>
     *
     * @param stateClass 履歴を削除したいユーザー定義状態クラス
     */
    public void clearHistoryDeep(Class<? extends StateBase> stateClass) {
        StateInfo state = getInfo(stateClass);
        for (StateInfo s : states.values()) {
            if (s != null && s.isChild(state)) {
                s.setHistory(null);
            }
        }
    }

    /**
     * Machineに対して直接状態遷移を指示する。イベントハンドラ外から使用する。
     * アプリケーションで本来想定していない遷移も可能のため、主にテスト目的。
     *
     * @param state 遷移先の状態
     * @param history 履歴を使うかどうか
     */
    public void setState(State state, boolean history) {
        setState(state, history, null);
    }

    /**
     * Machineに対して直接状態遷移を指示する。イベントハンドラ外から使用する。
     * アプリケーションで本来想定していない遷移も可能のため、主にテスト目的。
     *
     * @param state 遷移先の状態
     * @param history 履歴を使うかどうか
     * @param data 遷移先の状態に設定するデータ
     */
    public void setState(State state, boolean history, Object data) {
        setPendingState(state.getInfo(this), history, data);
        performPending();
    }
    /**
     * {@.ja Logging用フォーマットオブジェクト}
     * {@.en Format object for Logging}
     */
    protected Logbuf rtcout;
}
