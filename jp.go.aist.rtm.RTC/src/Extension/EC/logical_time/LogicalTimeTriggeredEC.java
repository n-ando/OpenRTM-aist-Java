package Extension.EC.logical_time;

import java.util.ArrayList;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.ObjectCreator;
import jp.go.aist.rtm.RTC.ObjectDestructor;
import jp.go.aist.rtm.RTC.RTObjectStateMachine;
import jp.go.aist.rtm.RTC.RTObjectStateMachineHolder;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.StateAction;
import jp.go.aist.rtm.RTC.StateHolder;
import jp.go.aist.rtm.RTC.StateMachine;
import jp.go.aist.rtm.RTC.executionContext.ExecutionContextBase;
import jp.go.aist.rtm.RTC.executionContext.ExecutionContextFactory;
import jp.go.aist.rtm.RTC.executionContext.ExecutionContextProfile;
import jp.go.aist.rtm.RTC.executionContext.ExecutionContextWorker;
import jp.go.aist.rtm.RTC.executionContext.ExtTrigExecutionContext;
import jp.go.aist.rtm.RTC.log.Logbuf;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.POAUtil;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.TimeValue;
import jp.go.aist.rtm.RTC.util.clock.ClockManager;
import jp.go.aist.rtm.RTC.util.clock.IClock;

import org.omg.CORBA.IntHolder;

import OpenRTM.DataFlowComponent;
import OpenRTM.LogicalTimeTriggeredECHelper;
import OpenRTM.LogicalTimeTriggeredECPOA;
import RTC.ExecutionContextService;
import RTC.ExecutionKind;
import RTC.LifeCycleState;
import RTC.LightweightRTObject;
import RTC.ReturnCode_t;
import _SDOPackage.NVListHolder;

/**
 * {@.ja ステップ実行が可能な ExecutionContext クラス} {@.en ExecutionContext class
 * that enables one step execution}
 * 
 * <p>
 * {@.ja 1周期毎の実行が可能なPeriodic Sampled Data Processing(周期実行用)
 * ExecutionContextクラス。 外部からのメソッド呼びだしによって時間が1周期づつ進む。} {@.en
 * ExecutionContext class that can execute every one cycle for Periodic Sampled
 * Data Processing. Time(Tick) advances one cycle by invoking method
 * externally.}
 */
public class LogicalTimeTriggeredEC extends LogicalTimeTriggeredECPOA implements
        Runnable, ObjectCreator<ExecutionContextBase>, ObjectDestructor,
        ExecutionContextBase {

    TimeValue m_activationTimeout = new TimeValue(0.5);
    TimeValue m_deactivationTimeout = new TimeValue(0.5);
    TimeValue m_resetTimeout = new TimeValue(0.5);
    boolean m_syncActivation = true;
    boolean m_syncDeactivation = true;
    boolean m_syncReset = true;

    /**
     * {@.ja デフォルトコンストラクタ} {@.en Default Constructor}
     */
    public LogicalTimeTriggeredEC() {
        super();
        rtcout = new Logbuf("exttrig_async_ec");
        m_clock = ClockManager.getInstance().getClock("logical");
        m_count = 0;
        m_svc = false;
        setObjRef((ExecutionContextService) this.__this());
        setKind(ExecutionKind.PERIODIC);
        setRate(1000);
        rtcout.println(Logbuf.DEBUG, "Actual period: "
                + m_profile.getPeriod().sec() + " [sec], "
                + m_profile.getPeriod().usec() + " [usec]");
    }
    
    /**
     * <p>
     * __this()
     * </p>
     * 
     * @return ExecutionContextService
     */
    public ExecutionContextService __this() {
        if (this.m_ref == null) {
            try {
                this.m_ref = LogicalTimeTriggeredECHelper.narrow(POAUtil
                        .getRef(this));
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return this.m_ref;
    }
    
    /**
     * {@.ja 処理を1ステップ進める} {@.en Move forward one step of ExecutionContext}
     * 
     * <p>
     * {@.ja ExecutionContextの処理を1周期分進める。} {@.en Move forward one
     * step of the ExecutionContext processing.}
     * 
     */
    public void tick(int sec, int usec) {
        rtcout.println(Logbuf.TRACE,
                "LogicalTimeTriggeredExecutionContext.tick()(sec = " + sec + ", usec = " + usec);
        TimeValue time = new TimeValue(sec, usec);
        m_clock.setTime(time);
        if( !isRunning() ) {
            rtcout.println(Logbuf.DEBUG, "EC is not running. do nothing.");
            return;
        }
        
        if( m_syncTick ) {
            // Synchronous tick mode
            invokeWorker();
        } else {
            // Asynchronous tick mode
            synchronized (m_workerthread.mutex_) {
                m_workerthread.ticked_ = true;
                m_workerthread.mutex_.notifyAll();
                rtcout.println(Logbuf.PARANOID, 
                        "EC was ticked. Signal was sent to worker thread.");
            }
        }
    }
    
    /**
     * {@.ja ExecutionContext用アクティビティスレッドを生成する} {@.en Generate internal
     * activity thread for ExecutionContext}
     * 
     * <p>
     * {@.ja Executioncontext 用の内部アクティビティスレッドを生成し起動する。} {@.en
     * Generate internal activity thread and run.}
     * 
     * @param args
     *            {@.ja 通常は0} {@.en Usually give 0}
     * 
     * @return {@.ja 生成処理実行結果} {@.en The generation result}
     */
    public int open() {
        rtcout.println(Logbuf.TRACE, "LogicalTimeTriggeredExecutionContext.open()");
        if(m_thread==null) {
            m_thread = new Thread(this, "LogicalTimeTriggeredExecutionContext");
            m_thread.start();
        }
        return 0;
    }

    /**
     * {@.ja 各 Component の処理を呼び出す。} {@.en Invoke each component's
     * operation}
     * 
     * <p>
     * {@.ja ExecutionContext に attach されている各 Component の処理を呼び出す。 全
     * Component の処理を呼び出した後、次の呼出が発生するまで休止する。} {@.en Invoke each
     * component's operation which is attached this ExecutionContext. Stop until
     * the next operation is invoked after all component operations are
     * invoked.}
     * 
     * @return {@.ja 処理結果} {@.en Operation result}
     */
    public int svc() {
        rtcout.println(Logbuf.TRACE,
            "LogicalTimeTriggeredExecutionContext.svc()");
         int count = 0;
         do {
             synchronized (m_workerthread.mutex_) {
                 rtcout.println(Logbuf.DEBUG,
                     "Start of worker invocation. ticked = " + (m_workerthread.ticked_ ? "true" : "false"));
                 while(m_workerthread.ticked_==false) {
                     // wait for tick
                     try {
                        m_workerthread.mutex_.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                     rtcout.println(Logbuf.DEBUG,"Thread was woken up.");
                 }
                 if(m_workerthread.ticked_==false) { continue; }
             }
             TimeValue t0 = new TimeValue(System.nanoTime());
             m_worker.invokeWorkerPreDo();
             m_worker.invokeWorkerDo();
             m_worker.invokeWorkerPostDo();
             TimeValue t1 = new TimeValue(System.nanoTime());
             synchronized (m_workerthread.mutex_) {
                 m_workerthread.ticked_ = false;
             }
             TimeValue period = getPeriod();
             if(true){
                 TimeValue t1_w = t1;
                 TimeValue period_w = period ;
                 rtcout.println(Logbuf.PARANOID, "Period:    " + period + " [s]");
                 rtcout.println(Logbuf.PARANOID, "Execution: " + t1_w.minus(t0) + " [s]");
                 rtcout.println(Logbuf.PARANOID, "Sleep:     " + period_w.minus(t1_w) + " [s]");
             }
             TimeValue t2 = new TimeValue(System.nanoTime());
             TimeValue t1_d = t1.minus(t0);
             if( period.toDouble() > t1_d.toDouble()) {
                 if (true /*count > 1000*/) {
                     rtcout.println(Logbuf.PARANOID, "sleeping...");
                 }
                 try {
                     period = period.minus(t1_d);
                     int millisec = 0;
                     int nanosec = (int)(period.toDouble()*1000000000); 
                     if ( nanosec > 999999) {
                         millisec = nanosec / 1000000;
                         nanosec = nanosec % 1000000;
                     }
                     
                     if ( millisec > 0 || nanosec > 0) {
                         Thread.sleep(millisec,nanosec);
                     }
                 } catch (InterruptedException e){
                     e.printStackTrace();
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             }
             if( true/*count > 1000*/) {
                 TimeValue t3 = new TimeValue(System.nanoTime());
                 rtcout.println(Logbuf.PARANOID, "Slept: " + t3.minus(t2).toDouble() + "[s]");
                 count = 0;
             }
             ++count;
        } while (threadRunning());

        return 0;
    }
    
    public void run() {
        rtcout.println(Logbuf.TRACE,
                "LogicalTimeTriggeredExecutionContext.run()");
        this.svc();
    }

    // ============================================================
    // ExecutionContext
    // ============================================================
    /**
     * {@.ja ExecutionContext 実行状態確認関数} {@.en heck for ExecutionContext
     * running state}
     * <p>
     * {@.ja この操作は ExecutionContext が Runnning 状態の場合に true を返す。
     * Executioncontext が Running の間、当該 Executioncontext に参加し
     * ている全てのアクティブRTコンポーネントが、ExecutionContext の実 行種類に応じて実行される。} {@.en This
     * operation shall return true if the context is in the Running state. While
     * the context is Running, all Active RTCs participating in the context
     * shall be executed according to the context’s execution kind.}
     * 
     * @return {@.ja 状態確認関数(動作中:true、停止中:false)} {@.en Check state
     *         function (Running:true、Stopping:false)}
     * 
     */
    public boolean is_running() {
        return isRunning();
    }
    public boolean isRunning() {
        rtcout.println(Logbuf.TRACE,
                "LogicalTimeTriggeredExecutionContext.isRunning()");
        return m_worker.isRunning();
    }

    public ReturnCode_t onStarting() {
        return ReturnCode_t.RTC_OK;
    }
    
    /**
     * {@.ja onStarted() template function} {@.en onStarted() template
     * function}
     */
    public ReturnCode_t onStarted() {
         // change EC thread state
        synchronized (m_svcmutex) {
            if (m_syncTick) {
                return ReturnCode_t.RTC_OK;
            }
            
        }
        if( !m_svc ) {
            // If start() is called first time, start the worker thread.
            m_svc = true;
            this.open();
        }
        return ReturnCode_t.RTC_OK;
    }

    public ReturnCode_t onStopping() {
        synchronized (m_workerthread.mutex_) {
            m_workerthread.running_ = false;
            return ReturnCode_t.RTC_OK;
        }
    }

    public ReturnCode_t onStopped() {
        return ReturnCode_t.RTC_OK;
    }

    public double onGetRate(double rate) {
        return rate;
    }

    public double onSettingRate(double rate) {
        return rate;
    }
    
    public ReturnCode_t onSetRate(double rate) {
        return ReturnCode_t.RTC_OK;
    }

    public ReturnCode_t onAddingComponent(LightweightRTObject rtobj) {
        return ReturnCode_t.RTC_OK;
    }
    
    public ReturnCode_t onAddedComponent(LightweightRTObject rtobj) {
        return ReturnCode_t.RTC_OK;
    }

    public ReturnCode_t onRemovingComponent(LightweightRTObject rtobj) {
        return ReturnCode_t.RTC_OK;
    }
    
    public ReturnCode_t onRemovedComponent(LightweightRTObject rtobj) {
        return ReturnCode_t.RTC_OK;
    }

    public ReturnCode_t onActivating(LightweightRTObject comp) {
        synchronized (m_workerthread.mutex_) {
            if (m_workerthread.running_ == false) {
                m_workerthread.running_ = true;
                m_workerthread.mutex_.notifyAll();
            }
            return ReturnCode_t.RTC_OK;
        }
    }
    
    public ReturnCode_t onActivated(RTObjectStateMachine comp, long count) {
        rtcout.println(Logbuf.TRACE,
                "LogicalTimeTriggeredExecutionContext.onActivated(count = "
                        + count + ")");
        // Now comp's next state must be ACTIVE state
        // If worker thread is stopped, restart worker thread.
        synchronized (m_workerthread.mutex_) {
            if (m_workerthread.running_ == false) {
                m_workerthread.running_ = true;
                m_workerthread.mutex_.notifyAll();
            }
            return ReturnCode_t.RTC_OK;
        }
    }

    public ReturnCode_t onDeactivating(LightweightRTObject comp) {
        return ReturnCode_t.RTC_OK;
    }
    
    public ReturnCode_t onDeactivated(RTObjectStateMachine comp, long count) {
        rtcout.println(Logbuf.TRACE,
                "LogicalTimeTriggeredExecutionContext.onDeactivated(count = (" + count + ")");
        if(isAllCurrentState(LifeCycleState.INACTIVE_STATE)) {
            synchronized (m_workerthread.mutex_) {
                if(m_workerthread.running_ == true) {
                    m_workerthread.running_ = false ;
                    rtcout.println(Logbuf.TRACE,
                        "All RTCs are INACTIVE. Stopping worker thread.");
                }
            }
        }
        return ReturnCode_t.RTC_OK;
    }

    public ReturnCode_t onResetting(LightweightRTObject comp) {
        return ReturnCode_t.RTC_OK;
    }

    public boolean threadRunning() {
        synchronized(m_svcmutex) {
            return m_svc;
        }
    }

    public ReturnCode_t onReset(RTObjectStateMachine comp, long count) {
        rtcout.println(Logbuf.TRACE,
                 "LogicalTimeTriggeredExecutionContext.onReset(count = " + count +")");
        if(isAllCurrentState(LifeCycleState.INACTIVE_STATE)) {
            synchronized (m_workerthread.mutex_) {
                if(m_workerthread.running_ == true) {
                    m_workerthread.running_ = false ;
                    rtcout.println(Logbuf.TRACE,
                        "All RTCs are INACTIVE. Stopping worker thread.");
                }
            }
        }
        return ReturnCode_t.RTC_OK;
    }

    public LifeCycleState onGetComponentState(LifeCycleState state) {
        return state;
    }
    
    public ExecutionKind onGetKind(ExecutionKind kind) {
        return kind;
    }

    public RTC.ExecutionContextProfile onGetProfile(
            RTC.ExecutionContextProfile profile) {
        return profile;
    }

    /**
     * {@.ja onWaitingActivated() template function} {@.en
     * onWaitingActivated() template function}
     */
    public ReturnCode_t onWaitingActivated(RTObjectStateMachine comp, long count) {
        rtcout.println(Logbuf.TRACE,
                "LogicalTimeTriggeredExecutionContext.onWaitingActivated(count =" + count + ")");
        rtcout.println(Logbuf.PARANOID, "curr: " + getStateString(comp.getStates().curr) 
                + ", next: " + getStateString(comp.getStates().next));
        // Now comp's next state must be ACTIVE state
        // If worker thread is stopped, restart worker thread.
        if( m_syncTick ) {
            // Synchronous tick mode
        } else {
            // Asynchronous tick mode
            synchronized (m_workerthread.mutex_) {
                m_workerthread.ticked_ = true;
                m_workerthread.mutex_.notifyAll();
            }
        }
        return ReturnCode_t.RTC_OK;
    }

    /**
     * {@.ja onWaitingDeactivated() template function} {@.en
     * onWaitingDeactivated() template function}
     */
    public ReturnCode_t onWaitingDeactivated(RTObjectStateMachine comp,
            long count) {
        rtcout.println(Logbuf.TRACE,
                "LogicalTimeTriggeredExecutionContext.onWaitingDeactivated(count =" + count + ")");
        rtcout.println(Logbuf.PARANOID, "curr: " + getStateString(comp.getStates().curr) 
                + ", next: " + getStateString(comp.getStates().next));
        if (m_syncTick) {
            // Synchronous tick mode
        } else {
            // Asynchronous tick mode
            synchronized (m_workerthread.mutex_) {
                m_workerthread.ticked_ = true;
                m_workerthread.mutex_.notifyAll();
            }
        }
        return ReturnCode_t.RTC_OK;
    }

    /**
     * {@.ja onWaitingReset() template function} {@.en onWaitingReset()
     * template function}
     */
    public ReturnCode_t onWaitingReset(RTObjectStateMachine comp, long count) {
        rtcout.println(Logbuf.TRACE,
            "LogicalTimeTriggeredExecutionContext.onWaitingReset(count =" + count + ")");
        rtcout.println(Logbuf.PARANOID, "curr: " + getStateString(comp.getStates().curr) 
                                    + ", next: " + getStateString(comp.getStates().next));
        if( m_syncTick ) { 
            // Synchronous tick mode
        } else {
            // Asynchronous tick mode
            synchronized (m_workerthread.mutex_) {
                m_workerthread.ticked_ = true;
                m_workerthread.mutex_.notifyAll();
            }
        }
        return ReturnCode_t.RTC_OK;
    }

    /**
     * {@.ja ExecutionContext の実行を開始} {@.en Start the ExecutionContext}
     * <p>
     * {@.ja ExecutionContext の実行状態を Runnning とするためのリクエストを
     * 発行する。ExecutionContext の状態が遷移すると ComponentAction::on_startup
     * が呼び出される。参加しているRTコンポー ネントが、初期化されるまで ExecutionContext を開始することはでき
     * ない。ExecutionContext は複数回開始/停止を繰り返すことができる。} {@.en Request that the
     * context enter the Running state. Once the state transition occurs, the
     * ComponentAction::on_startup operation will be invoked. An execution
     * context may not be started until the RT-Components that participate in it
     * have been initialized. An execution context may be started and stopped
     * multiple times.}
     * 
     * @return {@.ja ReturnCode_t 型のリターンコード} {@.en The return code
     *         of ReturnCode_t type}
     * 
     */
    public ReturnCode_t start() {
        rtcout.println(Logbuf.TRACE,
                "LogicalTimeTriggeredExecutionContext.start()");
        ReturnCode_t ret = onStarting();
        if (ret != ReturnCode_t.RTC_OK) {
            rtcout.println(Logbuf.ERROR,
                    "onStarting() failed. Starting EC aborted.");
            return ret;
        }
        ret = m_worker.start();
        if (ret != ReturnCode_t.RTC_OK) {
            rtcout.println(Logbuf.ERROR,
                    "Invoking on_startup() for each RTC failed.");
            return ret;
        }
        ret = onStarted();
        if (ret != ReturnCode_t.RTC_OK) {
            rtcout.println(Logbuf.ERROR,
                    "Invoking on_startup() for each RTC failed.");
            m_worker.stop();
            rtcout.println(Logbuf.ERROR,
                    "on_shutdown() was invoked, because of on_startup");
            return ret;
        }
        return ret;
    }

    /**
     * {@.ja ExecutionContext の実行を停止} {@.en Stop the ExecutionContext}
     * <p>
     * {@.ja ExecutionContext の状態を Stopped とするためのリクエストを発行す
     * る。遷移が発生した場合は、ComponentAction::on_shutdown が呼び出 される。参加しているRTコンポーネントが終了する前に
     * ExecutionContext を停止する必要がある。ExecutionContext は複数回 開始/停止を繰り返すことができる。}
     * {@.en Request that the context enter the Stopped state. Once the
     * transition occurs, the ComponentAction::on_shutdown operation will be
     * invoked. An execution context must be stopped before the RT components
     * that participate in it are finalized. An execution context may be started
     * and stopped multiple times.}
     * 
     * @return {@.ja ReturnCode_t 型のリターンコード} {@.en The return code
     *         of ReturnCode_t type}
     * 
     */
    public ReturnCode_t stop() {
        rtcout.println(Logbuf.TRACE,
                "LogicalTimeTriggeredExecutionContext.stop()");

        ReturnCode_t ret = onStopping(); // Template
        if (ret != ReturnCode_t.RTC_OK) {
            rtcout.println(Logbuf.ERROR,
                    "onStopping() failed. Stopping EC aborted.");
            return ret;
        }
        ret = m_worker.stop(); // Actual stop()
        if (ret != ReturnCode_t.RTC_OK) {
            rtcout.println(Logbuf.ERROR,
                    "Invoking on_shutdown() for each RTC failed.");
            return ret;
        }
        ret = onStopped(); // Template
        if (ret != ReturnCode_t.RTC_OK) {
            rtcout.println(Logbuf.ERROR,
                    "Invoking on_shutdown() for each RTC failed.");
            return ret;
        }
        return ret;
    }

    /**
     * {@.ja ExecutionContext の実行周期(Hz)を取得する} {@.en Get execution rate(Hz)
     * of ExecutionContext}
     * <p>
     * {@.ja Active 状態にてRTコンポーネントが実行される周期(単位:Hz)を取得す る。} {@.en This
     * operation shall return the rate (in hertz) at which its Active
     * participating RTCs are being invoked.}
     * 
     * @return {@.ja 処理周期(単位:Hz)} {@.en Execution cycle(Unit:Hz)}
     * 
     */
    public double get_rate() {
        rtcout.println(Logbuf.TRACE,
                "LogicalTimeTriggeredExecutionContext.get_rate()");
        double rate = m_profile.getRate();
        return onGetRate(rate);
    }

    public TimeValue getPeriod() {
        TimeValue period = m_profile.getPeriod();
        return period;
    }

    /**
     * <p>
     * ExecutionContextの実行周期(Hz)を設定します。
     * </p>
     * 
     * @param rate
     *            実行周期(Hz)
     */
    public ReturnCode_t set_rate(double rate) {
        rtcout.println(Logbuf.TRACE,
                "LogicalTimeTriggeredExecutionContext.set_rate(" + rate + ")");

        if (rate <= 0.0)
            return ReturnCode_t.BAD_PARAMETER;

        ReturnCode_t ret = m_profile.setRate(onSettingRate(rate));
        if (ret != ReturnCode_t.RTC_OK) {
            rtcout.println(Logbuf.ERROR, "Setting execution rate failed. "
                    + rate);
            return ret;
        }
        ret = onSetRate(rate);
        if (ret != ReturnCode_t.RTC_OK) {
            rtcout.println(Logbuf.ERROR, "onSetRate(" + rate + ") failed.");
            return ret;
        }
        rtcout.println(Logbuf.INFO, "onSetRate(" + rate + ") done.");
        return ret;
    }

    /**
     * {@.ja RTコンポーネントをアクティブ化する} {@.en Activate an RT-component}
     * 
     * <p>
     * {@.ja Inactive 状態にあるRTコンポーネントをActive に遷移させ、アクティ
     * ブ化する。この操作が呼ばれた結果、on_activate が呼び出される。指 定したRTコンポーネントが参加者リストに含まれない場合は、
     * BAD_PARAMETER が返される。指定したRTコンポーネントの状態が Inactive
     * 以外の場合は、PRECONDITION_NOT_MET が返される。} {@.en The given participant RTC
     * is Inactive and is therefore not being invoked according to the execution
     * context’s execution kind. This operation shall cause the RTC to
     * transition to the Active state such that it may subsequently be invoked
     * in this execution context. The callback on_activate shall be called as a
     * result of calling this operation. This operation shall not return until
     * the callback has returned, and shall result in an error if the callback
     * does.}
     * 
     * @param comp
     *            {@.ja アクティブ化対象RTコンポーネント} {@.en The target
     *            RT-Component for activation}
     * 
     * @return {@.ja ReturnCode_t 型のリターンコード} {@.en The return code
     *         of ReturnCode_t type}
     */
    public ReturnCode_t activate_component(LightweightRTObject comp) {
        rtcout.println(Logbuf.TRACE,
                "LogicalTimeTriggeredExecutionContext.activate_component()");
        return activateComponent(comp);
    }

    public ReturnCode_t activateComponent(LightweightRTObject comp) {

        rtcout.println(Logbuf.TRACE,
                "LogicalTimeTriggeredExecutionContext.activateComponent()");
        ReturnCode_t ret = onActivating(comp); // Template
        if (ret != ReturnCode_t.RTC_OK) {
            rtcout.println(Logbuf.ERROR, "onActivating() failed.");
            return ret;
        }

        RTObjectStateMachineHolder rtobjhldr = new RTObjectStateMachineHolder();
        ret = m_worker.activateComponent(comp, rtobjhldr); // Actual
                                                           // activateComponent()
        RTObjectStateMachine rtobj = rtobjhldr.rtobjsm;
        if (ret != ReturnCode_t.RTC_OK) {
            return ret;
        }
        // Asynchronous activation mode
        if (!m_syncActivation) {
            ret = onActivated(rtobjhldr.rtobjsm, -1);
            if (ret != ReturnCode_t.RTC_OK) {
                rtcout.println(Logbuf.ERROR, "onActivated() failed.");
            }
            return ret;
        }
        // ------------------------------------------------------------
        // Synchronized activation mode
        rtcout.println(Logbuf.DEBUG, "Synchronous activation mode. "
                + "Waiting for the RTC to be ACTIVE state. ");
        return waitForActivated(rtobjhldr.rtobjsm);
    }

    public ReturnCode_t waitForActivated(RTObjectStateMachine rtobj) {
        long count = 0;
        ReturnCode_t ret = onWaitingActivated(rtobj, count);
        if (ret != ReturnCode_t.RTC_OK) {
            rtcout.println(Logbuf.ERROR, "onWaitingActivated failed.");
            return ret;
        }
        long cycle = (long) (m_activationTimeout.toDouble() / getPeriod()
                .toDouble());
        rtcout.println(Logbuf.DEBUG, "Timeout is "
                + m_activationTimeout.toDouble() + "[s] (" + getRate()
                + "[s] in " + cycle + " times");
        // Wating INACTIVE -> ACTIVE
        TimeValue starttime = new TimeValue();
        starttime.convert(System.nanoTime() / 1000000000);
        while (rtobj.isCurrentState(LifeCycleState.INACTIVE_STATE)) {
            ret = onWaitingActivated(rtobj, count); // Template method
            if (ret != ReturnCode_t.RTC_OK) {
                rtcout.println(Logbuf.ERROR, "onWaitingActivated failed.");
                return ret;
            }
            try {
                int millisec = 0;
                int nanosec = (int) (getPeriod().toDouble() * 1000000000);
                if (nanosec > 999999) {
                    millisec = nanosec / 1000000;
                    nanosec = nanosec % 1000000;
                }

                if (millisec > 0 || nanosec > 0) {
                    Thread.sleep(millisec, nanosec);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            TimeValue delta = new TimeValue();
            delta.convert(System.nanoTime() / 1000000000);
            delta = delta.minus(starttime);
            rtcout.println(Logbuf.DEBUG, "Waiting to be ACTIVE state. " + delta
                    + " [s] slept (" + count + "/" + cycle);
            ++count;
            if (delta.toDouble() > m_activationTimeout.toDouble()
                    || count > cycle) {
                rtcout.println(Logbuf.WARN, "The component is not responding.");
                break;
            }
        }
        // Now State must be ACTIVE or ERROR
        if (rtobj.isCurrentState(LifeCycleState.INACTIVE_STATE)) {
            rtcout.println(Logbuf.ERROR,
                    "Unknown error: Invalid state transition.");
            return ReturnCode_t.RTC_ERROR;
        }
        rtcout.println(Logbuf.DEBUG, "Current state is " + rtobj.getState());
        ret = onActivated(rtobj, count); // Template method
        if (ret != ReturnCode_t.RTC_OK) {
            rtcout.println(Logbuf.ERROR, "onActivated() failed.");
        }
        rtcout.println(Logbuf.DEBUG, "onActivated() done.");
        return ret;
    }

    /**
     * {@.ja RTコンポーネントを非アクティブ化する} {@.en Deactivate an RT-component}
     * 
     * <p>
     * {@.ja Active 状態にあるRTコンポーネントを非アクティブ化し、Inactive
     * に遷移させる。この操作が呼ばれた結果、on_deactivate が呼び出され る。指定したRTコンポーネントが参加者リストに含まれない場合は、
     * BAD_PARAMETER が返される。指定したRTコンポーネントの状態が Active 以外の場合は、PRECONDITION_NOT_MET
     * が返される。} {@.en The given RTC is Active in the execution context.
     * Cause it to transition to the Inactive state such that it will not be
     * subsequently invoked from the context unless and until it is activated
     * again. The callback on_deactivate shall be called as a result of calling
     * this operation. This operation shall not return until the callback has
     * returned, and shall result in an error if the callback does.}
     * 
     * @param comp
     *            {@.ja 非アクティブ化対象RTコンポーネント} {@.en The target
     *            RT-Component for deactivate}
     * 
     * @return {@.ja ReturnCode_t 型のリターンコード} {@.en The return code
     *         of ReturnCode_t type}
     */
    public ReturnCode_t deactivate_component(LightweightRTObject comp) {
        rtcout.println(Logbuf.TRACE,
                "LogicalTimeTriggeredExecutionContext.deactivate_component()");
        return deactivateComponent(comp);
    }

    public ReturnCode_t deactivateComponent(LightweightRTObject comp) {
        rtcout.println(Logbuf.TRACE,
                "LogicalTimeTriggeredExecutionContext.deactivateComponent()");
        ReturnCode_t ret = onDeactivating(comp); // Template
        if (ret != ReturnCode_t.RTC_OK) {
            rtcout.println(Logbuf.ERROR, "onDeactivatingComponent() failed.");
            return ret;
        }
        // Deactivate all the RTCs
        RTObjectStateMachineHolder rtobjhldr = new RTObjectStateMachineHolder();
        ret = m_worker.deactivateComponent(comp, rtobjhldr);
        RTObjectStateMachine rtobj = rtobjhldr.rtobjsm;
        if (ret != ReturnCode_t.RTC_OK) {
            return ret;
        }
        if (!m_syncDeactivation) {
            ret = onDeactivated(rtobj, -1);
            if (ret != ReturnCode_t.RTC_OK) {
                rtcout.println(Logbuf.ERROR, "onDeactivated() failed.");
            }
            return ret;
        }
        // ------------------------------------------------------------
        // Waiting for synchronized deactivation
        rtcout.println(Logbuf.DEBUG, "Synchronous deactivation mode. "
                + "Waiting for the RTC to be INACTIVE state. ");
        return waitForDeactivated(rtobj);
    }

    public ReturnCode_t waitForDeactivated(RTObjectStateMachine rtobj) {
        long count = 0;
        ReturnCode_t ret = onWaitingDeactivated(rtobj, count);
        if (ret != ReturnCode_t.RTC_OK) {
            rtcout.println(Logbuf.ERROR, "onWaitingDeactivated failed.");
            return ret;
        }
        long cycle = (long) (m_deactivationTimeout.toDouble() / getPeriod()
                .toDouble());
        rtcout.println(Logbuf.DEBUG, "Timeout is "
                + m_deactivationTimeout.toDouble() + "[s] (" + getRate()
                + "[s] in " + cycle + " times");
        // Wating ACTIVE -> INACTIVE
        TimeValue starttime = new TimeValue();
        starttime.convert(System.nanoTime() / 1000);
        while (rtobj.isCurrentState(LifeCycleState.ACTIVE_STATE)) {
            ret = onWaitingDeactivated(rtobj, count); // Template method
            if (ret != ReturnCode_t.RTC_OK) {
                rtcout.println(Logbuf.ERROR, "onWaitingDeactivated failed.");
                return ret;
            }
            try {
                int millisec = 0;
                int nanosec = (int) (getPeriod().toDouble() * 1000000000);
                if (nanosec > 999999) {
                    millisec = nanosec / 1000000;
                    nanosec = nanosec % 1000000;
                }

                if (millisec > 0 || nanosec > 0) {
                    Thread.sleep(millisec, nanosec);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            TimeValue delta = new TimeValue();
            delta.convert(System.nanoTime() / 1000);
            delta = delta.minus(starttime);
            rtcout.println(Logbuf.DEBUG,
                    "Waiting to be INACTIVE state. Sleeping " + delta
                            + " [s] (" + count + "/" + cycle);
            ++count;
            if (delta.toDouble() > m_activationTimeout.toDouble()
                    || count > cycle) {
                rtcout.println(Logbuf.WARN, "The component is not responding.");
                break;
            }
        }
        // Now State must be INACTIVE or ERROR
        if (rtobj.isCurrentState(LifeCycleState.ACTIVE_STATE)) {
            rtcout.println(Logbuf.ERROR,
                    "Unknown error: Invalid state transition.");
            return ReturnCode_t.RTC_ERROR;
        }
        rtcout.println(Logbuf.DEBUG, "Current state is " + rtobj.getState());
        ret = onDeactivated(rtobj, count);
        if (ret != ReturnCode_t.RTC_OK) {
            rtcout.println(Logbuf.ERROR, "onDeactivated() failed.");
        }
        rtcout.println(Logbuf.DEBUG, "onDeactivated() done.");
        return ret;
    }

    /**
     * {@.ja RTコンポーネントをリセットする} {@.en Reset the RT-component}
     * 
     * <p>
     * {@.ja Error 状態のRTコンポーネントの復帰を試みる。この操作が呼ばれた結 果、on_reset
     * が呼び出される。指定したRTコンポーネントが参加者リ ストに含まれない場合は、BAD_PARAMETER が返される。指定したRTコン
     * ポーネントの状態が Error 以外の場合は、PRECONDITION_NOT_MET が返 される。} {@.en Attempt
     * to recover the RTC when it is in Error. The ComponentAction::on_reset
     * callback shall be invoked. This operation shall not return until the
     * callback has returned, and shall result in an error if the callback does.
     * If possible, the RTC developer should implement that callback such that
     * the RTC may be returned to a valid state.}
     * 
     * @param comp
     *            {@.ja リセット対象RTコンポーネント} {@.en The target
     *            RT-Component for reset}
     * 
     * @return {@.ja ReturnCode_t 型のリターンコード} {@.en The return code
     *         of ReturnCode_t type}
     */
    public ReturnCode_t reset_component(LightweightRTObject comp) {
        rtcout.println(Logbuf.TRACE,
                "LogicalTimeTriggeredExecutionContext.reset_component()");
        return resetComponent(comp);
    }

    public ReturnCode_t resetComponent(LightweightRTObject comp) {
        rtcout.println(Logbuf.TRACE,
                "LogicalTimeTriggeredExecutionContext.resetComponent()");

        ReturnCode_t ret = onResetting(comp); // Template
        if (ret != ReturnCode_t.RTC_OK) {
            rtcout.println(Logbuf.ERROR, "onResetting() failed.");
            return ret;
        }
        RTObjectStateMachineHolder rtobjhldr = new RTObjectStateMachineHolder();
        ret = m_worker.resetComponent(comp, rtobjhldr); // Actual
                                                        // resetComponent()
        RTObjectStateMachine rtobj = rtobjhldr.rtobjsm;
        if (ret != ReturnCode_t.RTC_OK) {
            return ret;
        }
        if (!m_syncReset) {
            ret = onReset(rtobj, -1);
            if (ret != ReturnCode_t.RTC_OK) {
                rtcout.println(Logbuf.ERROR, "onReset() failed.");
            }
            return ret;
        }
        // ------------------------------------------------------------
        // Waiting for synchronized reset
        rtcout.println(Logbuf.DEBUG, "Synchronous reset mode. "
                + "Waiting for the RTC to be INACTIVE state. ");
        return waitForReset(rtobj);
    }

    public ReturnCode_t waitForReset(RTObjectStateMachine rtobj) {
        long count = 0;
        ReturnCode_t ret = onWaitingReset(rtobj, count);
        if (ret != ReturnCode_t.RTC_OK) {
            rtcout.println(Logbuf.ERROR, "onWaitingReset() failed.");
            return ret;
        }
        long cycle = (long) (m_resetTimeout.toDouble() / getPeriod().toDouble());
        rtcout.println(Logbuf.DEBUG, "Timeout is " + m_resetTimeout.toDouble()
                + "[s] (" + getRate() + "[s] in " + cycle + " times");
        // Wating ERROR -> INACTIVE
        TimeValue starttime = new TimeValue();
        starttime.convert(System.nanoTime() / 1000);
        while (rtobj.isCurrentState(LifeCycleState.ERROR_STATE)) {
            ret = onWaitingReset(rtobj, count); // Template method
            if (ret != ReturnCode_t.RTC_OK) {
                rtcout.println(Logbuf.ERROR, "onWaitingReset failed.");
                return ret;
            }
            try {
                int millisec = 0;
                int nanosec = (int) (getPeriod().toDouble() * 1000000000);
                if (nanosec > 999999) {
                    millisec = nanosec / 1000000;
                    nanosec = nanosec % 1000000;
                }

                if (millisec > 0 || nanosec > 0) {
                    Thread.sleep(millisec, nanosec);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            TimeValue delta = new TimeValue();
            delta.convert(System.nanoTime() / 1000);
            delta = delta.minus(starttime);
            rtcout.println(Logbuf.DEBUG,
                    "Waiting to be INACTIVE state. Sleeping " + delta
                            + " [s] (" + count + "/" + cycle);
            ++count;
            if (delta.toDouble() > m_resetTimeout.toDouble() || count > cycle) {
                rtcout.println(Logbuf.WARN, "The component is not responding.");
                break;
            }
        }
        // Now State must be INACTIVE
        if (!rtobj.isCurrentState(LifeCycleState.INACTIVE_STATE)) {
            rtcout.println(Logbuf.ERROR,
                    "Unknown error: Invalid state transition.");
            return ReturnCode_t.RTC_ERROR;
        }
        rtcout.println(Logbuf.DEBUG, "Current state is " + rtobj.getState());
        ret = onReset(rtobj, count);
        if (ret != ReturnCode_t.RTC_OK) {
            rtcout.println(Logbuf.ERROR, "onReset() failed.");
        }
        rtcout.println(Logbuf.DEBUG, "onReset() done.");
        return ret;
    }
    
    /**
     * {@.ja RTコンポーネントの状態を取得する} {@.en Get RT-component's state}
     * 
     * <p>
     * {@.ja 指定したRTコンポーネントの状態(LifeCycleState)を取得する。指定し
     * たRTコンポーネントが参加者リストに含まれない場合は、 UNKNOWN_STATE が返される。} {@.en This
     * operation shall report the LifeCycleState of the given participant RTC.
     * UNKNOWN_STATE will be returned, if the given RT-Component is not inclued
     * in the participant list.}
     * 
     * @param comp
     *            {@.ja 状態取得対象RTコンポーネント} {@.en The target
     *            RT-Component to get the state}
     * 
     * @return {@.ja 現在の状態(LifeCycleState)} {@.en The current state
     *         of the target RT-Component(LifeCycleState)}
     */
    public LifeCycleState get_component_state(LightweightRTObject comp) {
        rtcout.println(Logbuf.TRACE,
                "LogicalTimeTriggeredExecutionContext.get_component_state()");
        return getComponentState(comp);
    }

    public LifeCycleState getComponentState(LightweightRTObject comp) {

        LifeCycleState state = m_worker.getComponentState(comp);
        rtcout.println(Logbuf.TRACE,
                "LogicalTimeTriggeredExecutionContext.getComponentState() = "
                        + getStateString(state));
        if (state == LifeCycleState.CREATED_STATE) {
            rtcout.println(Logbuf.ERROR, "CREATED state: not initialized "
                    + "RTC or unknwon RTC specified.");
        }
        return onGetComponentState(state);
    }

    /**
     * {@.ja ExecutionKind を取得する} {@.en Get the ExecutionKind}
     * 
     * <p>
     * {@.ja 本 ExecutionContext の ExecutionKind を取得する} {@.en This
     * operation shall report the execution kind of the execution context.}
     * 
     * @return {@.ja ExecutionKind} {@.en ExecutionKind}
     */
    public ExecutionKind get_kind() {
        rtcout.println(Logbuf.TRACE,
                "LogicalTimeTriggeredExecutionContext.get_kind() ="
                        + m_profile.getKindString());
        return getKind();
    }

    public ExecutionKind getKind() {
        ExecutionKind kind = m_profile.getKind();
        rtcout.println(Logbuf.TRACE,
                "LogicalTimeTriggeredExecutionContext.getKind() = "
                        + getKindString(kind));
        kind = onGetKind(kind);
        rtcout.println(Logbuf.DEBUG, "onGetKind() returns "
                + getKindString(kind));
        return kind;
    }

    /**
     * {@.ja RTコンポーネントを追加する} {@.en Add an RT-component}
     * 
     * <p>
     * {@.ja 指定したRTコンポーネントを参加者リストに追加する。追加されたRTコ ンポーネントは attach_context
     * が呼ばれ、Inactive 状態に遷移する。 指定されたRTコンポーネントがnullの場合は、BAD_PARAMETER が返され
     * る。指定されたRTコンポーネントが DataFlowComponent 以外の場合は、 BAD_PARAMETER が返される。} {@.en
     *  The operation causes the given RTC to begin participating in the
     * execution context. The newly added RTC will receive a call to
     * LightweightRTComponent::attach_context and then enter the Inactive state.
     * BAD_PARAMETER will be invoked, if the given RT-Component is null or if
     * the given RT-Component is other than DataFlowComponent.}
     * 
     * @param comp
     *            {@.ja 追加対象RTコンポーネント} {@.en The target RT-Component
     *            for add}
     * 
     * @return {@.ja ReturnCode_t 型のリターンコード} {@.en The return code
     *         of ReturnCode_t type}
     */
    public ReturnCode_t add_component(LightweightRTObject comp) {
        rtcout.println(Logbuf.TRACE,
                "LogicalTimeTriggeredExecutionContext.add_component()");
        return addComponent(comp);
    }

    public ReturnCode_t addComponent(LightweightRTObject comp) {
        rtcout.println(Logbuf.TRACE,
                "LogicalTimeTriggeredExecutionContext.addcomponent()");

        ReturnCode_t ret = onAddingComponent(comp); // Template
        if (ret != ReturnCode_t.RTC_OK) {
            rtcout.println(Logbuf.ERROR,
                    "Error: onAddingComponent(). RTC is not attached.");
            return ret;
        }
        ret = m_worker.addComponent(comp); // Actual addComponent()
        if (ret != ReturnCode_t.RTC_OK) {
            rtcout.println(Logbuf.ERROR,
                    "Error: ECWorker addComponent() faild.");
            return ret;
        }
        ret = m_profile.addComponent(comp); // Actual addComponent()
        if (ret != ReturnCode_t.RTC_OK) {
            rtcout.println(Logbuf.ERROR,
                    "Error: ECProfile addComponent() faild.");
            return ret;
        }
        ret = onAddedComponent(comp); // Template
        if (ret != ReturnCode_t.RTC_OK) {
            rtcout.println(Logbuf.ERROR, "Error: onAddedComponent() faild.");
            rtcout.println(Logbuf.INFO, "Removing attached RTC.");
            m_worker.removeComponent(comp);
            m_profile.removeComponent(comp);
            return ret;
        }
        rtcout.println(Logbuf.INFO, "Component has been added to this EC.");
        return ret;
    }
    
    /**
     * {@.ja コンポーネントをバインドする。} {@.en Bind the component.}
     * 
     * <p>
     * {@.ja コンポーネントをバインドする。} {@.en Bind the component.}
     * 
     * @param rtc
     *            {@.ja RTコンポーネント} {@.en RT-Component's instances}
     * @return {@.ja ReturnCode_t 型のリターンコード} {@.en The return code
     *         of ReturnCode_t type}
     * 
     */
    public ReturnCode_t bindComponent(RTObject_impl rtc) {
        rtcout.println(Logbuf.TRACE,
                "LogicalTimeTriggeredExecutionContext.bindComponent()");
        return m_worker.bindComponent(rtc);
    }

    /**
     * {@.ja RTコンポーネントを参加者リストから削除する} {@.en Remove the RT-Component from
     * participant list}
     * 
     * <p>
     * {@.ja 指定したRTコンポーネントを参加者リストから削除する。削除された RTコンポーネントは detach_context
     * が呼ばれる。指定されたRTコンポー ネントが参加者リストに登録されていない場合は、BAD_PARAMETER が返 される。} {@.en
     *  This operation causes a participant RTC to stop participating in
     * the execution context. The removed RTC will receive a call to
     * LightweightRTComponent::detach_context. BAD_PARAMETER will be returned,
     * if the given RT-Component is not participating in the participant list.}
     * 
     * @param comp
     *            {@.ja 削除対象RTコンポーネント} {@.en The target RT-Component
     *            for delete}
     * 
     * @return {@.ja ReturnCode_t 型のリターンコード} {@.en The return code
     *         of ReturnCode_t type}
     */
    public ReturnCode_t remove_component(LightweightRTObject comp) {
        rtcout.println(Logbuf.TRACE,
                "LogicalTimeTriggeredExecutionContext.remove_component()");
        return removeComponent(comp);
    }

    public ReturnCode_t removeComponent(LightweightRTObject comp) {
        rtcout.println(Logbuf.TRACE,
                "LogicalTimeTriggeredExecutionContext.removeComponent()");
        ReturnCode_t ret = onRemovingComponent(comp); // Template
        if (ret != ReturnCode_t.RTC_OK) {
            rtcout.println(Logbuf.ERROR, "Error: onRemovingComponent(). "
                    + "RTC will not not attached.");
            return ret;
        }
        ret = m_worker.removeComponent(comp); // Actual removeComponent()
        if (ret != ReturnCode_t.RTC_OK) {
            rtcout.println(Logbuf.ERROR,
                    "Error: ECWorker removeComponent() faild.");
            return ret;
        }
        ret = m_profile.removeComponent(comp); // Actual removeComponent()
        if (ret != ReturnCode_t.RTC_OK) {
            rtcout.println(Logbuf.ERROR,
                    "Error: ECProfile removeComponent() faild.");
            return ret;
        }
        ret = onRemovedComponent(comp); // Template
        if (ret != ReturnCode_t.RTC_OK) {
            rtcout.println(Logbuf.ERROR, "Error: onRemovedComponent() faild.");
            rtcout.println(Logbuf.INFO, "Removing attached RTC.");
            m_worker.removeComponent(comp);
            m_profile.removeComponent(comp);
            return ret;
        }
        rtcout.println(Logbuf.INFO, "Component has been removeed to this EC.");
        return ret;
    }

    /**
     * {@.ja ExecutionContextProfile を取得する} {@.en Get the
     * ExecutionContextProfile}
     * 
     * <p>
     * {@.ja 本 ExecutionContext のプロファイルを取得する。} {@.en This operation
     * provides a profile “descriptor” for the execution context.}
     * 
     * @return {@.ja ExecutionContextProfile} {@.en
     *         ExecutionContextProfile}
     */
    public RTC.ExecutionContextProfile get_profile() {
        rtcout.println(Logbuf.TRACE,
                "LogicalTimeTriggeredExecutionContext.get_profile()");
        return m_profile.getProfile();
    }

    /**
     * <p>DataFlowComponentのAction定義用抽象クラスです。</p>
     */
    protected abstract class DFPBase {
        /**
         * <p>コンストラクタです。</p>
         * 
         * @param id ExecutionContextのID
         */
        public DFPBase(int id) {
            ec_id = id;
            m_sm = new StateMachine(3);
            m_sm.setListener(this);
            StateHolder st = new StateHolder();
            m_sm.setEntryAction(LifeCycleState.ACTIVE_STATE, new onActivated());
            m_sm.setDoAction(LifeCycleState.ACTIVE_STATE, new onExecute());
            m_sm.setPostDoAction(LifeCycleState.ACTIVE_STATE, new onStateUpdate());
            m_sm.setExitAction(LifeCycleState.ACTIVE_STATE, new onDeactivated());
            m_sm.setEntryAction(LifeCycleState.ERROR_STATE, new onAborting());
            m_sm.setDoAction(LifeCycleState.ERROR_STATE, new onError());
            m_sm.setExitAction(LifeCycleState.ERROR_STATE, new onReset());

            st.prev = LifeCycleState.INACTIVE_STATE;
            st.curr = LifeCycleState.INACTIVE_STATE;
            st.next = LifeCycleState.INACTIVE_STATE;
            m_sm.setStartState(st);
            m_sm.goTo(LifeCycleState.INACTIVE_STATE);
        }
        /**
         * <p>onActivatedアクション定義用抽象クラスです。</p>
         */
        private class onActivated implements StateAction {
            public void doAction(StateHolder state) {
                on_activated(state);
            }
        }
        /**
         * <p>onExecuteアクション定義用抽象クラスです。</p>
         */
        private class onExecute implements StateAction {
            public void doAction(StateHolder state) {
                on_execute(state);
            }
        }
        /**
         * <p>onStateUpdateアクション定義用抽象クラスです。</p>
         */
        private class onStateUpdate implements StateAction {
            public void doAction(StateHolder state) {
                on_state_update(state);
            }
        }
        /**
         * <p>onDeactivatedアクション定義用抽象クラスです。</p>
         */
        private class onDeactivated implements StateAction {
            public void doAction(StateHolder state) {
                on_deactivated(state);
            }
        }
        /**
         * <p>onAbortingアクション定義用抽象クラスです。</p>
         */
        private class onAborting implements StateAction {
            public void doAction(StateHolder state) {
                on_aborting(state);
            }
        }
        /**
         * <p>onErrorアクション定義用抽象クラスです。</p>
         */
        private class onError implements StateAction {
            public void doAction(StateHolder state) {
                on_error(state);
            }
        }
        /**
         * <p>onResetアクション定義用抽象クラスです。</p>
         */
        private class onReset implements StateAction {
            public void doAction(StateHolder state) {
                on_reset(state);
            }
        }

        /**
         * <p>ExecutionContextのstart時に呼ばれる抽象メソッドです。</p>
         */
        public abstract void on_startup();
        /**
         * <p>ExecutionContextのstop時に呼ばれる抽象メソッドです。</p>
         */
        public abstract void on_shutdown();
        
        /**
         * <p>コンポーネントのactivate時に呼ばれる抽象メソッドです。</p>
         */
        public abstract void on_activated(final StateHolder st);
        /**
         * <p>コンポーネントのdeactivate時に呼ばれる抽象メソッドです。</p>
         */
        public abstract void on_deactivated(final StateHolder st);
        /**
         * <p>コンポーネントのabort時に呼ばれる抽象メソッドです。</p>
         */
        public abstract void on_aborting(final StateHolder st);
        /**
         * <p>コンポーネントがerror状態の時に呼ばれる抽象メソッドです。</p>
         */
        public abstract void on_error(final StateHolder st);
        /**
         * <p>コンポーネントreset時に呼ばれる抽象メソッドです。</p>
         */
        public abstract void on_reset(final StateHolder st);
        /**
         * <p>コンポーネント実行時に呼ばれる抽象メソッドです。</p>
         */
        public abstract void on_execute(final StateHolder st);
        /**
         * <p>コンポーネントの実行時に呼ばれる抽象メソッドです。</p>
         */
        public abstract void on_state_update(final StateHolder st);

        /**
         * <p>ExecutionContextの実行周期変更時に呼ばれる抽象メソッドです。</p>
         */
        public abstract void on_rate_changed();
        /**
         * <p>ExecutionContextの状態遷移用ワーカーです。</p>
         */
        public void worker() {
            m_sm.worker();
        }
        public void worker_pre() {
            m_sm.worker_pre();
        }
        public void worker_do() {
            m_sm.worker_do();
        }
        public void worker_post() {
            m_sm.worker_post();
        }

        /**
         * <p>現在の状態を取得します。</p>
         * 
         * @return 現在の状態
         */
        public LifeCycleState get_state(){
            return m_sm.getState();
        }

        /**
         * <p>ExecutionContextのID</p>
         */
        public int ec_id;
        /**
         * <p>ExecutionContextのStateMachine</p>
         */
        public StateMachine<LifeCycleState, DFPBase> m_sm;
        
    }
    
    /**
     * <p>DataFlowComponentのAction定義用抽象クラスです。</p>
     */
    protected class DFP extends DFPBase {
        /**
         * <p>コンストラクタです。</p>
         * 
         * @param obj 対象コンポーネント
         * @param id ExecutionContextのID
         */
        public DFP(DataFlowComponent obj, int id) {
            super(id);
            m_obj = obj;
        }
        /**
         * <p>onStartupアクション定義用メソッドです。</p>
         */
        public void on_startup() {
            m_obj.on_startup(ec_id);
        }
        /**
         * <p>onShutdownアクション定義用メソッドです。</p>
         */
        public void on_shutdown() {
            m_obj.on_shutdown(ec_id);
        }
        /**
         * <p>onActivatedアクション定義用メソッドです。</p>
         */
        public void on_activated(final StateHolder st) {
            if( m_obj.on_activated(ec_id) != ReturnCode_t.RTC_OK ) {
                m_sm.goTo(LifeCycleState.ERROR_STATE);
                return;
            }
            return;
        }
        /**
         * <p>onDeactivatedアクション定義用メソッドです。</p>
         */
        public void on_deactivated(final StateHolder st) {
            if( m_obj.on_deactivated(ec_id) != ReturnCode_t.RTC_OK ) {
                m_sm.goTo(LifeCycleState.ERROR_STATE);
                return;
            }
            return;
        }
        /**
         * <p>onAbortingアクション定義用メソッドです。</p>
         */
        public void on_aborting(final StateHolder st) {
            m_obj.on_aborting(ec_id);
        }
        /**
         * <p>onErrorアクション定義用メソッドです。</p>
         */
        public void on_error(final StateHolder st) {
            m_obj.on_error(ec_id);
        }
        /**
         * <p>onResetアクション定義用メソッドです。</p>
         */
        public void on_reset(final StateHolder st) {
            if( m_obj.on_reset(ec_id) != ReturnCode_t.RTC_OK) {
                m_sm.goTo(LifeCycleState.ERROR_STATE);
                return;
            }
            return;
        }
        /**
         * <p>onExecuteアクション定義用メソッドです。</p>
         */
        public void on_execute(final StateHolder st) {
            if( m_obj.on_execute(ec_id) != ReturnCode_t.RTC_OK) {
                m_sm.goTo(LifeCycleState.ERROR_STATE);
                return;
            }  
            return;
        }
        /**
         * <p>onStateUpdateアクション定義用メソッドです。</p>
         */
        public void on_state_update(final StateHolder st) {
            if( m_obj.on_state_update(ec_id) != ReturnCode_t.RTC_OK) {
                m_sm.goTo(LifeCycleState.ERROR_STATE);
                return;
            }
            return;
        }
        /**
         * <p>onRateChangedアクション定義用メソッドです。</p>
         */
        public void on_rate_changed() {
            m_obj.on_rate_changed(ec_id);
        }
        private DataFlowComponent m_obj; 
    }

    /**
     * <p>ExecutionContextにattachされたコンポーネントのメソッド呼出用クラスです。</p>
     */
    protected class Comp {
        /**
         * <p>コンストラクタです。</p>
         * 
         * @param ref 対象コンポーネント
         * @param dfp ExecutionContextの対象StateMachine
         * @param id ExecutionContextのID
         */
        public Comp(LightweightRTObject ref, DataFlowComponent dfp, int id) {
            this._ref = ref;
            this._sm = new DFP(dfp, id);
        }
        /**
         * <p>コンストラクタです。</p>
         * 
         * @param comp 対象コンポーネント
         */
        public Comp(final Comp comp) {
            this._ref = comp._ref;
            this._sm = new DFP(comp._sm.m_obj, comp._sm.ec_id);
        }
        /**
         * <p>コンポーネントをコピーします。</p>
         * 
         * @param comp 対象コンポーネント
         */
        public Comp substitute(final Comp comp) {
            this._ref = comp._ref;
            this._sm.m_obj = comp._sm.m_obj;
            this._sm.ec_id = comp._sm.ec_id;
            return this;
        }
        /**
         * <p>ExecutionContextから呼び出されるメソッドです。</p>
         */
        public void invoke(){
            this._sm.worker();
        }
        /**
         * <p>ExecutionContextから呼び出されるメソッドです。</p>
         */
        public void invoke_work_pre(){
            this._sm.worker_pre();
        }
        /**
         * <p>ExecutionContextから呼び出されるメソッドです。</p>
         */
        public void invoke_work_do(){
            this._sm.worker_do();
        }
        /**
         * <p>ExecutionContextから呼び出されるメソッドです。</p>
         */
        public void invoke_work_post(){
            this._sm.worker_post();
        }
        /**
         * <p>StartUp時に呼び出されるメソッドです。</p>
         */
        public void invoke_on_startup(){
            this._sm.on_startup();
        }
        /**
         * <p>ShutDown時に呼び出されるメソッドです。</p>
         */
        public void invoke_on_shutdown(){
            this._sm.on_shutdown();
        }
        /**
         * <p>RateChanged時に呼び出されるメソッドです。</p>
         */
        public void invoke_on_rate_changed(){
            this._sm.on_rate_changed();
        }
        /**
         * <p>StateMachine管理対象コンポーネントです。</p>
         */
        public LightweightRTObject _ref;
        /**
         * <p>対象コンポーネントのStateMachineです。</p>
         */
        public DFP _sm;
    }
    /**
     * <p>管理対象コンポーネント検索用ヘルパークラスです。</p>
     */
    protected class find_comp {
        private LightweightRTObject m_comp;
        public find_comp(LightweightRTObject comp) {
            m_comp = comp;
        }
        public boolean eqaulof(Comp comp) {
            return comp._ref._is_equivalent(m_comp);
        }
    };

    /**
     * <p>ExecutionContextにatttachされているコンポーネントのリストです。</p>
     */
    protected ArrayList<Comp>  m_comps = new ArrayList<Comp>();
    /**
     * <p>ExecutionContextの実行状態です。</p>
     */
    protected boolean m_running;
    /**
     * <p>ExecutionContext のスレッド実行フラグです。</p>
     */
    private boolean m_svc;
    private String m_svcmutex = new String();
    
    /**
     * {@.ja worker 用状態変数クラス} {@.en Condition variable class for worker}
     */
    private class WorkerThreadCtrl {
        private String mutex_ = new String();
        private boolean running_;
        private boolean ticked_;

        public WorkerThreadCtrl() {
            ticked_ = false;
            running_ = false;
        }
    };

    // A condition variable for external triggered worker
    private WorkerThreadCtrl m_workerthread = new WorkerThreadCtrl();
    /**
     * <p>
     * ExecutionContextWorkerです。
     * </p>
     */
    protected ExecutionContextWorker m_worker = new ExecutionContextWorker();
    /**
     * <p>
     * ExecutionContextProfileです。
     * </p>
     */
    protected ExecutionContextProfile m_profile = new ExecutionContextProfile();
    /**
     * <p>ExecutionContextの動作周期です。</p>
     */
    protected long m_usec;
    /**
     * <p>
     * ExecutionContextServiceとしてのCORBAオブジェクト参照です。
     * </p>
     */
    protected ExecutionContextService m_ref;
//    protected boolean m_nowait;
    protected Thread m_thread = null;

    /**
     * {@.ja LogicalTimeTriggeredExecutionContext を生成する} {@.en Creats
     * LogicalTimeTriggeredExecutionContext}
     * 
     * @return {@.ja 生成されたinstance} {@.en Object Created instances}
     */
    public ExecutionContextBase creator_() {
        return new LogicalTimeTriggeredEC();
    }

    /**
     * {@.ja Object を破棄する} {@.en Destructs Object}
     * 
     * @param obj
     *            {@.ja 破棄するインタスタンス} {@.en The target instances for
     *            destruction}
     * 
     */
    public void destructor_(Object obj) {
        rtcout.println(Logbuf.TRACE,
                "LogicalTimeTriggeredExecutionContext.destructor_");
        synchronized (m_workerthread.mutex_) {
            m_workerthread.ticked_ = true;
        }
        obj = null;
    }

    /**
     * <p>このExecutionContextを生成するFactoryクラスを
     * ExecutionContext用ObjectManagerに登録します。</p>
     * 
     * @param manager Managerオブジェクト
     */
    public static void ExtTrigExecutionContextInit(Manager manager) {
        ExecutionContextFactory<ExecutionContextBase,String> factory 
                                        = ExecutionContextFactory.instance();
        factory.addFactory("Extension.EC.logical_time.LogicalTimeTriggeredEC",
                    new ExtTrigExecutionContext(),
                    new ExtTrigExecutionContext());
    }
    
    /**
     * <p>ExecutionContextのインスタンスを破棄します。</p>
     * 
     * @param comp 破棄対象ExecutionContextインスタンス
     */
    public Object ECDeleteFunc(ExecutionContextBase comp) {
        return null;
    }

    /**
     * <p>ExecutionContextのインスタンスを取得します。</p>
     * 
     * @return ExecutionContextインスタンス
     */
    public ExecutionContextBase ECNewFunc() {
        return this;
    }
    /**
     * <p>
     * Logging用フォーマットオブジェクト
     * </p>
     */
    protected Logbuf rtcout;
    
    /**
     * {@.ja ExecutionContextクラスの初期化関数}
     * {@.en Initialization function of ExecutionContext class}
     */
    public void init(Properties props) {
        // ExecutionContextBase::init(props);
        rtcout.println(Logbuf.TRACE,
                "LogicalTimeTriggeredExecutionContext.init()");
        rtcout.println(Logbuf.DEBUG, props.toString());
        // getting rate
        setExecutionRate(props);
        // getting sync/async mode flag
        transitionModeHolder transitionMode = new transitionModeHolder();
        if(setTransitionMode(props, "sync_transition", transitionMode)) {
            m_syncActivation = transitionMode.flag;
            m_syncDeactivation = transitionMode.flag;
            m_syncReset = transitionMode.flag;
            
        }
        setTransitionMode(props, "sync_activation", transitionMode);
        m_syncActivation = transitionMode.flag;
        setTransitionMode(props, "sync_deactivation", transitionMode);
        m_syncDeactivation = transitionMode.flag;
        setTransitionMode(props, "sync_reset", transitionMode);
        m_syncReset = transitionMode.flag;
        // getting transition timeout
        TimeValue timeout = new TimeValue(0.0);
        if (setTimeout(props, "transition_timeout", timeout)) {
            m_activationTimeout   = timeout;
            m_deactivationTimeout = timeout;
            m_resetTimeout        = timeout;
        }
        setTimeout(props, "activation_timeout",   m_activationTimeout);
        setTimeout(props, "deactivation_timeout", m_deactivationTimeout);
        setTimeout(props, "reset_timeout",        m_resetTimeout);
        
        rtcout.println(Logbuf.DEBUG,"ExecutionContext's configurations:");
        rtcout.println(Logbuf.DEBUG,"Exec rate   : " + getRate() + " [Hz]");
        rtcout.println(Logbuf.DEBUG,"Activation  : Sync = " + ( m_syncActivation ? "YES" : "NO" ) + " Timeout = " + m_activationTimeout.toString());
        rtcout.println(Logbuf.DEBUG,"Deactivation: Sync = " + ( m_syncDeactivation ? "YES" : "NO" )+ " Timeout = "+ m_deactivationTimeout.toString());
        rtcout.println(Logbuf.DEBUG,"Reset       : Sync = " + ( m_syncReset ? "YES" : "NO" )+ " Timeout = "+ m_resetTimeout.toString());
        // Setting given Properties to EC's profile::properties
        setProperties(props);
        //
        if( props.findNode("sync_tick") != null) {
            if(props.getNode("sync_tick").getValue().equals("YES") ) {
                m_syncTick = true;
                rtcout.println(Logbuf.DEBUG, "Tick mode: Synchronous");
            } else {
                m_syncTick = false;
                rtcout.println(Logbuf.DEBUG, "Tick mode: Asynchronous");
            }
        }
        rtcout.println(Logbuf.DEBUG, "init() done");
    }
    
    /**
     * <p>
     * 本オブジェクトのExecutionContextServiceとしてのCORBAオブジェクト参照を設定します。
     * </p>
     * 
     * @param ref
     *            CORBAオブジェクト参照
     */
    public void setObjRef(final ExecutionContextService ref) {
        rtcout.println(Logbuf.TRACE,
                "LogicalTimeTriggeredExecutionContext.setObjRef()");
        m_worker.setECRef(ref);
        m_profile.setObjRef(ref);
        m_ref = ref;
    }
    
    /**
     * {@.ja CORBA オブジェクト参照の取得} {@.en Get the reference to the CORBA
     * object}
     * <p>
     * {@.ja 本オブジェクトの ExecutioncontextService としての CORBA オブジェ クト参照を取得する。}
     * {@.en Get the reference to the CORBA object as
     * ExecutioncontextService of this object.}
     * 
     * @return {@.ja CORBA オブジェクト参照} {@.en The reference to CORBA
     *         object}
     * 
     */
    public ExecutionContextService getObjRef() {
        return m_profile.getObjRef();
    }
    /**
     * {@.ja ExecutionContext の実行周期(Hz)を設定する} {@.en Set execution rate(Hz)
     * of ExecutionContext}
     * <p>
     * {@.ja Active 状態にてRTコンポーネントが実行される周期(単位:Hz)を設定す
     * る。実行周期の変更は、DataFlowComponentAction の on_rate_changed
     * によって各RTコンポーネントに伝達される。} {@.en This operation shall set the rate (in
     * hertz) at which this context’s Active participating RTCs are being
     * called. If the execution kind of the context is PERIODIC, a rate change
     * shall result in the invocation of on_rate_changed on any RTCs realizing
     * DataFlowComponentAction that are registered with any RTCs participating
     * in the context.}
     * 
     * @param rate
     *            {@.ja 処理周期(単位:Hz)} {@.en Execution cycle(Unit:Hz)}
     * 
     * @return {@.ja ReturnCode_t 型のリターンコード RTC_OK: 正常終了 BAD_PARAMETER:
     *         設定値が負の値} {@.en The return code of ReturnCode_t type RTC_OK:
     *         Succeed BAD_PARAMETER: Invalid value. The value might be
     *         negative.}
     * 
     */
    public ReturnCode_t setRate(double rate) {
        return m_profile.setRate(rate);
    }
    
    /**
     * {@.ja ExecutionContext の実行周期(Hz)を取得する} {@.en Get execution rate(Hz)
     * of ExecutionContext}
     * <p>
     * {@.ja Active 状態にてRTコンポーネントが実行される周期(単位:Hz)を取得す る。} {@.en This
     * operation shall return the rate (in hertz) at which its Active
     * participating RTCs are being invoked.}
     * 
     * @return {@.ja 処理周期(単位:Hz)} {@.en Execution cycle(Unit:Hz)}
     * 
     */
    public double getRate() {
        return m_profile.getRate();
    }
    
    /**
     * {@.ja ExecutionKind を文字列化する} {@.en Converting ExecutionKind enum to
     * string}
     * <p>
     * {@.ja RTC::ExecutionKind で定義されている PERIODIC, EVENT_DRIVEN, OTHER
     * を文字列化する。} {@.en This function converts enumeration (PERIODIC,
     * EVENT_DRIVEN, OTHER) defined in RTC::ExecutionKind to string.}
     * 
     * @param kind
     *            {@.ja ExecutionKind} {@.en ExecutionKind}
     * @return {@.ja 文字列化されたExecutionKind} {@.en String of
     *         ExecutionKind}
     * 
     */
    public final String getKindString(ExecutionKind kind) {
        return m_profile.getKindString(kind);
    }

    /**
     * {@.ja ExecutionKind を設定する} {@.en Set the ExecutionKind}
     * <p>
     * {@.ja この ExecutionContext の ExecutionKind を設定する} {@.en This
     * operation sets the kind of the execution context.}
     * 
     * @param kind
     *            {@.ja ExecutionKind} {@.en kind ExecutionKind}
     * 
     */
    public ReturnCode_t setKind(ExecutionKind kind) {
        return m_profile.setKind(kind);
    }
    
    /**
     * {@.ja Ownerコンポーネントをセットする。} {@.en Setting owner component of the
     * execution context}
     * <p>
     * {@.ja このECのOwnerとなるRTCをセットする。} {@.en This function sets an
     * RT-Component to be owner of the execution context.}
     * 
     * @param comp
     *            {@.ja OwnerとなるRTコンポーネント} {@.en an owner
     *            RT-Component of this execution context}
     * @return {@.ja ReturnCode_t 型のリターンコード} {@.en The return code
     *         of ReturnCode_t type}
     */
    public ReturnCode_t setOwner(LightweightRTObject comp) {
        return m_profile.setOwner(comp);
    }

    /**
     * {@.ja Ownerコンポーネントの参照を取得する} {@.en Getting a reference of the owner
     * component}
     * <p>
     * {@.ja このECのOwnerであるRTCの参照を取得する。} {@.en This function returns
     * a reference of the owner RT-Component of this execution context}
     * 
     * @return {@.ja OwnerRTコンポーネントの参照} {@.en a reference of the
     *         owner RT-Component}
     */
    public RTC.RTObject getOwner() {
        return m_profile.getOwner();
    }

    /**
     * {@.ja RTコンポーネントの参加者リストを取得する} {@.en @brief Getting participant RTC
     * list}
     * <p>
     * {@.ja 現在登録されている参加者RTCのリストを取得する。} {@.en This function returns
     * a list of participant RTC of the execution context.}
     * 
     * @return {@.ja 参加者RTCのリスト} {@.en Participants RTC list}
     * 
     */
    public RTC.RTObject[] getComponentList() {
        return m_profile.getComponentList();
    }

    /**
     * {@.ja Propertiesをセットする} {@.en Setting Properties}
     * <p>
     * {@.ja ExecutionContextProfile::properties をセットする。} {@.en This
     * function sets ExecutionContextProfile::properties by Properties.}
     * 
     * @param props
     *            {@.ja ExecutionContextProfile::properties にセットするプロパティー}
     *            {@.en Properties to be set to
     *            ExecutionContextProfile::properties.}
     * 
     */
    public void setProperties(Properties props) {
        m_profile.setProperties(props);
    }
    
    /**
     * {@.ja Propertiesを取得する} {@.en Setting Properties}
     * <p>
     * {@.ja ExecutionContextProfile::properties を取得する。} {@.en This
     * function sets ExecutionContextProfile::properties by Properties.}
     * 
     * @return {@.ja Propertiesに変換された ExecutionContextProfile::properties}
     *         {@.en Properties to be set to
     *         ExecutionContextProfile::properties.}
     * 
     */
    public Properties getProperties() {
        return m_profile.getProperties();
    }
    
    /**
     * {@.ja rofileを取得する} {@.en Getting Profile}
     * <p>
     * {@.ja ExecutionContextProfile を取得する。} {@.en This function
     * gets ExecutionContextProfile.}
     * 
     * @return {@.ja ExecutionContextProfile} {@.en
     *         ExecutionContextProfile}
     * 
     */
    public RTC.ExecutionContextProfile getProfile() {
        rtcout.println(Logbuf.TRACE,
                "LogicalTimeTriggeredExecutionContext.getProfile()");
        RTC.ExecutionContextProfile profile = m_profile.getProfile();
        rtcout.println(Logbuf.DEBUG, "kind: " + getKindString(profile.kind));
        rtcout.println(Logbuf.DEBUG, "rate: " + profile.rate);
        rtcout.println(Logbuf.DEBUG, "properties: ");
        String str = new String();
        Properties prop = new Properties();
        NVListHolder nvholder = new NVListHolder(profile.properties);
        NVUtil.copyToProperties(prop, nvholder);
        str = prop._dump(str, prop, 0);
        rtcout.println(Logbuf.TRACE, str);
        return onGetProfile(profile);
    }
    
    public boolean setTimeout(Properties props, String key, TimeValue timevalue) {
        rtcout.println(Logbuf.TRACE,
                "LogicalTimeTriggeredExecutionContext.setTimeout(" + key + ")");
        if (props.findNode(key) != null) {
            timevalue.convert(Double.valueOf(props.getNode(key).getValue()));
            rtcout.println(Logbuf.DEBUG, "Timeout (" + key + "): "
                    + props.getNode(key).getValue() + " [s]");
            return true;
        }
        rtcout.println(Logbuf.DEBUG, "Configuration " + key + " not found.");
        return false;
    }

    /**
     * {@.ja Propertiesから実行コンテキストをセットする} {@.en Setting execution rate from
     * given properties.}
     * 
     */
    public boolean setExecutionRate(Properties props) {
        if (props.findNode("rate") != null) {
            double rate;
            rate = Double.valueOf(props.getNode("rate").getValue());
            setRate(rate);
            return true;
        }
        return false;
    }
    
    /**
     * {@.ja Propertiesから状態遷移モードをセットする} {@.en Setting state transition
     * mode from given properties.}
     * 
     */
    public boolean setTransitionMode(Properties props, String key,
            transitionModeHolder tmhldr) {
        rtcout.println(Logbuf.TRACE,
                "ExtTrigExecutionContext.setTransitionMode(" + key + ")");
        if (props.findNode(key) != null) {
            tmhldr.flag = props.getNode(key).getValue() == "YES";
            rtcout.println(Logbuf.DEBUG, "Transition Mode: " + key + " = "
                    + (tmhldr.flag ? "YES" : "NO"));
            return true;
        }
        rtcout.println(Logbuf.DEBUG, "Configuration " + key + " not found.");
        return false;
    }
    
    /*
     * ! ============================================================
     *  Delegated functions to ExecutionContextWorker
     * ============================================================
     */
    public boolean isAllCurrentState(RTC.LifeCycleState state) {
        return m_worker.isAllCurrentState(state);
    }

    public boolean isAllNextState(RTC.LifeCycleState state) {
        return m_worker.isAllNextState(state);
    }

    public boolean isOneOfCurrentState(RTC.LifeCycleState state) {
        return m_worker.isOneOfCurrentState(state);
    }

    public boolean isOneOfNextState(RTC.LifeCycleState state) {
        return m_worker.isOneOfNextState(state);
    }

    public void invokeWorkerPreDo() {
        m_worker.invokeWorkerPreDo();
    }

    public void invokeWorkerDo() {
        m_worker.invokeWorkerDo();
    }

    public void invokeWorkerPostDo() {
        m_worker.invokeWorkerPostDo();
    }
    ///////////
    /**
     * {@.ja Logical clock} {@.en Logical clock}
     */
    private IClock m_clock;

    /**
     * {@.ja Synchronous tick flag} {@.en Synchronous tick flag}
     */
    private boolean m_syncTick;

    /**
     * {@.ja Tick counter} {@.en Tick counter}
     */
    private short m_count;

    private String m_tickmutex = new String();

    /**
     * {@.ja 各 ExecutionContext 用のスレッド実行関数}
     * {@.en Thread execution function for ExecutionContext}
     * 
     * <p>
     * {@.ja ExecutionContext 用のスレッド終了時に呼ばれる。コンポーネントオ
     * ブジェクトの非アクティブ化、マネージャへの通知を行う。} {@.en This function is invoked when
     * activity thread for ExecutionContext exits. Deactivate the component
     * object and notify it to manager.}
     * 
     * @param flags
     *            {@.ja 終了処理フラグ} {@.en Flag of the close}
     * 
     * @return {@.ja 終了処理結果} {@.en The close result}
     */
    public int close(long flags) {
        rtcout.println(Logbuf.TRACE,
                "LogicalTimeTriggeredExecutionContext.close()");
        // At this point, this component have to be finished.
        // Current state and Next state should be RTC_EXITING.
        return 0;
    }

    public final String getStateString(LifeCycleState state) {
        return m_worker.getStateString(state);
    }

    /**
     * {@.ja calling worker} {@.en calling worker}
     */
    public void invokeWorker() {
        rtcout.println(Logbuf.TRACE,
            "LogicalTimeTriggeredExecutionContext.invokeWorker()");
         if( isRunning()==false ) return;
         synchronized (m_tickmutex) {
             
         }
         invokeWorkerPreDo(); // update state
         TimeValue t0 = new TimeValue(System.nanoTime());
         invokeWorkerDo();
         TimeValue t1 = new TimeValue(System.nanoTime());
         invokeWorkerPostDo();
         TimeValue t2 = new TimeValue(System.nanoTime());
        //
         TimeValue period = getPeriod();
         if( m_count > 1000 ) {
             TimeValue t1_w = t1;
             TimeValue t2_w = t2;
             TimeValue period_w = period;
             rtcout.println(Logbuf.PARANOID, "Period:      " + period.getSec() +  "[s]");
             rtcout.println(Logbuf.PARANOID, "Exec-Do:     " + t1_w.minus(t0).getSec() +  "[s]");
             rtcout.println(Logbuf.PARANOID, "Exec-PostDo: " + t2_w.minus(t1).getSec() +  "[s]");
             rtcout.println(Logbuf.PARANOID, "Sleep:       " + period_w.minus(t2_w.minus(t0)).getSec() +  "[s]");
         }
         TimeValue t3 = new TimeValue(System.nanoTime());
         TimeValue delta = t2.minus(t0);
         if( period.toDouble() > delta.toDouble() ) {
             if (m_count > 1000) {
                 rtcout.println(Logbuf.PARANOID, "sleeping...");
             }
             try {
                 period = period.minus(delta);
                 int millisec = 0;
                 int nanosec = (int)(period.toDouble()*1000000000); 
                 if ( nanosec > 999999) {
                     millisec = nanosec / 1000000;
                     nanosec = nanosec % 1000000;
                 }
                 
                 if ( millisec > 0 || nanosec > 0) {
                     Thread.sleep(millisec,nanosec);
                 }
             } catch (InterruptedException e){
                 e.printStackTrace();
             } catch (Exception e) {
                 e.printStackTrace();
             }
         }
         if( m_count > 1000 ) {
             TimeValue t4 = new TimeValue(System.nanoTime());
             rtcout.println(Logbuf.PARANOID, "Slept: " + t4.minus(t3).getSec() + "[s]");
             m_count = 0;
         }
         ++m_count;
    }   

    public void get_time(IntHolder sec, IntHolder usec) {
        TimeValue time = m_clock.getTime();
        sec.value = (int)time.getSec();
        usec.value = (int)time.getUsec();
    }
    // //////////////////////////////////////////////////
    /**
     * <p>
     * 本オブジェクトのExecutionContextServiceとしてのCORBAオブジェクト参照を取得します。
     * </p>
     * 
     * @return CORBAオブジェクト参照
     */
    public ExecutionContextService getRef() {
        rtcout.println(Logbuf.TRACE,
                "LogicalTimeTriggeredExecutionContext.getRef()");
        return m_ref;
    }

    public ExecutionContextProfile onGetProfile(ExecutionContextProfile profile) {
        return profile;
    }
}
