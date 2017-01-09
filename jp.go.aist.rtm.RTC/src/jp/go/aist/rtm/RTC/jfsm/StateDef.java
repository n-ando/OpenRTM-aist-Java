package jp.go.aist.rtm.RTC.jfsm;

import jp.go.aist.rtm.RTC.jfsm.DeepHistory;
import jp.go.aist.rtm.RTC.jfsm.History;
import jp.go.aist.rtm.RTC.jfsm.machine.StateInfo;
import jp.go.aist.rtm.RTC.jfsm.machine.TopState;

import java.lang.reflect.InvocationTargetException;
import java.lang.annotation.Annotation;

/**
 * ユーザー定義状態のスーパークラス。最上位状態は、かならずこのクラスを直接継承しなくてはならない。
 */
public class StateDef extends TopState {

    private final boolean history;
    private final boolean deepHistory;

    public StateDef() {
        history = isHistory();
        deepHistory = isDeepHistory();
    }

    /**
     * Machine内部で使用するメソッド。アプリケーションから呼び出してはならない。
     */
// statechar.h:77
//          void set_history_super(\
//               hrtm::sc::StateInfo & self, hrtm::sc::StateInfo & deep) \
    @Override
    public void setHistorySuper(StateInfo self, StateInfo deep) {
//           { self.set_history_super(deep); } \
        self.setHistorySuper(deep);
    }

    /**
     * Machine内部で使用するメソッド。アプリケーションから呼び出してはならない。
     */
    @Override
    protected void saveHistory(StateInfo self, StateInfo shallow, StateInfo deep) {
        if (history) {
// statechar.h:71
//            void save_history(hrtm::sc::StateInfo & self, hrtm::sc::StateInfo & shallow, \
//            hrtm::sc::StateInfo & deep) { \
//                self.set_history(&shallow); \
//                SUPER::set_history_super(self, deep); } \
            self.setHistory(shallow);
            invokeSetHistorySuper(self, deep);
            return;
        }
        if (deepHistory) {
// statechar.h:57
//           void save_history(hrtm::sc::StateInfo & self, \
//             hrtm::sc::StateInfo & /* shallow */, hrtm::sc::StateInfo & deep) { \
//           self.set_history(&deep); \
//           SUPER::set_history_super(self, deep); } \
            self.setHistory(deep);
        }
        invokeSetHistorySuper(self, deep);
    }

    private void invokeSetHistorySuper(final StateInfo self, final StateInfo deep) {
        try {
            this.getClass().getSuperclass().getMethod("setHistorySuper", new Class<?>[]{StateInfo.class, StateInfo.class}).invoke(this, self, deep);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isHistory() {
        //return getClass().getDeclaredAnnotation(History.class) != null;
        Annotation[] annotations = getClass().getDeclaredAnnotations();
        for(int ic=0;ic<annotations.length;++ic) {
            if(annotations[ic].annotationType() == History.class) {
                return true;
            }
        }
        return false;
    }

    private boolean isDeepHistory() {
        //return getClass().getDeclaredAnnotation(DeepHistory.class) != null;
        Annotation[] annotations = getClass().getDeclaredAnnotations();
        for(int ic=0;ic<annotations.length;++ic) {
            if(annotations[ic].annotationType() == DeepHistory.class) {
                return true;
            }
        }
        return false;
    }
}
