package jp.go.aist.rtm.RTC.jfsm.machine;

import static java.lang.String.format;
import java.lang.reflect.InvocationTargetException;
import java.lang.annotation.Annotation;

import jp.go.aist.rtm.RTC.jfsm.DataType;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import jp.go.aist.rtm.RTC.log.Logbuf;

/**
 * 状態に関する実行時情報を保持するオブジェクト。
 */
public class StateInfo {

    //private static final Logger LOGGER = LoggerFactory.getLogger(StateInfo.class);

    private final StateInfo parent;
    private StateInfo history;
    private StateBase instance;
    public MachineBase machine;
    protected Object data;

    public StateInfo(MachineBase machine, StateInfo parent) {
        this.machine = machine;
        this.parent = parent;
        rtcout = new Logbuf("StateInfo");
    }

    public void onExit(StateInfo next) {
        if (parent == null) {
            return;
        }
        if (this == next || !next.isChild(this)) {
            rtcout.println(Logbuf.DEBUG, getName() + ": exit");
            //LOGGER.debug(format("%s: exit", getName()));

            // onExit()を実装していない状態では、親のものを呼んではならない
            // C++版では継承関係にLinkを介在させていたが、Javaでは同じことができないので
            // リフレクションで検査している
            invokeMethod(instance, "onExit");
            instance.deleteData(this);
            parent.onExit(next);
        }
    }

    public boolean isChild(StateInfo state) {
        return this == state || (parent != null && parent.isChild(state));
    }

    public void onEntry(StateInfo previous) {
        onEntry(previous, true);
    }

//        # statechart.cc: 49
//        #  void StateInfo::on_entry(StateInfo & previous, bool first) {
    public void onEntry(StateInfo previous, boolean first) {
//        #    // Only Root has no parent
//        #    if (!parent_) {
//        #      return;
//        #    }
        if (parent == null) {
            return;
        }
//        #    // first entry or previous state is not substate -> perform entry
//        #    if (first || !previous.is_child(*this)) {
        if (first || !previous.isChild(this)) {
//        #      parent_->on_entry(previous, false);
            parent.onEntry(previous, false);
//        #      // Could be set from outside or persistent (or EmptyData)
//        #      if (!data_) {
//        #        create_data();
//        #      }
            if (data == null) {
                createData();
            }
//        #      HRTM_DEBUG(LOGGER, name() <<  ": entry");
//        #      instance_->on_entry();
            rtcout.println(Logbuf.DEBUG, getName() + ": entry");
            //LOGGER.debug(format("%s: entry", getName()));
            instance.onEntry();
//        #    }
        }
    }

    protected void createData() {
        throw new RuntimeException("create_data() must be overridden");
    }

//        # statechart.cc: 80
//        #  void StateInfo::on_init(bool history) {
    public void onInit(boolean history) {
//        #    if (history && history_) {
//        #      HRTM_DEBUG(LOGGER, name() << ": history transition to: " <<
//        #         history_->name());
//        #      machine_.set_pending_state(*history_, true, 0);
        if (history && this.history != null) {
            rtcout.println(Logbuf.DEBUG, getName()
                                         + ": history transition to: "
                                         + this.history.getName());
            //LOGGER.debug(format("%s: history transition to: %s", getName(), this.history.getName()));
            machine.setPendingState(this.history, true, 0);
        } else {
//        #    } else {
//        #      HRTM_DEBUG(LOGGER, name() << ": init");
//        #      instance_->on_init();
//        #    }
            // onInit()を実装していない状態では、親のものを呼んではならない
            // C++版では継承関係にLinkを介在させて実現していたが、Javaでは同じことができないので
            // リフレクションで検査している
            rtcout.println(Logbuf.DEBUG, getName() + ": init");
            //LOGGER.debug(format("%s: init", getName()));
            invokeMethod(instance, "onInit");
        }
//        #    history_ = 0;
        this.history = null;
    }

    private static void invokeMethod(final StateBase instance, final String methodName) {
        try {
            instance.getClass().getDeclaredMethod(methodName).invoke(instance);
        } catch (NoSuchMethodException e) {
            // method is not declared; just skip
        } catch (InvocationTargetException e) {
            throw new RuntimeException("exception in onInit", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("failed to invoke onInit() - perhaps non public?", e);
        }
    }

    final void setInstance(StateBase stateInstance) {
        assert instance == null;
        instance = stateInstance;
    }

    public final StateBase getInstance() {
        return instance;
    }

    protected Object getData() {
        return data;
    }

    public String getName() {
        return null;
    }

// statechart.h: 398
//          void save_history(StateInfo & shallow, StateInfo & deep) {
    public void saveHistory(StateInfo shallow, StateInfo deep) {
//            // Check state's history strategy.
//            instance_->save_history(*this, shallow, deep);
        instance.saveHistory(this, shallow, deep);
    }

// statechart.h: 402
//          // Update superstate's history information:
//          void set_history_super(StateInfo & deep) {
    public void setHistorySuper(StateInfo deep) {
//            if (parent_) {
//              // Let it choose between deep or shallow history.
//              parent_->save_history(*this, deep);
//            }
        if (parent != null) {
            parent.saveHistory(this, deep);
        }
    }

    public void setHistory(StateInfo history) {
        this.history = history;
    }

    void setData(Object data) {
        assert data != null;
        this.data = data;
    }

    void deleteData() {
        //DataType dataType = instance.getClass().getDeclaredAnnotation(DataType.class);
        Annotation[] annotations = instance.getClass().getDeclaredAnnotations();
        DataType dataType = null;
        for(int ic=0;ic<annotations.length;++ic) {
            if(annotations[ic].annotationType() == DataType.class) {
                dataType = (DataType)annotations[ic];
            }
        }
        if(dataType != null && dataType.persistent()) {
            return;
        }
        this.data = null;
    }

    /**
     * {@.ja Logging用フォーマットオブジェクト}
     * {@.en Format object for Logging}
     */
    protected Logbuf rtcout;
}
