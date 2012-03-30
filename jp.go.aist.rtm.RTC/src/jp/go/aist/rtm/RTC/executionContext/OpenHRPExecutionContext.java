package jp.go.aist.rtm.RTC.executionContext;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.ObjectCreator;
import jp.go.aist.rtm.RTC.ObjectDestructor;
import jp.go.aist.rtm.RTC.log.Logbuf;
import jp.go.aist.rtm.RTC.util.TimeValue;

import org.omg.CORBA.SystemException;

import RTC.ReturnCode_t;

/**
 *  <p> OpenHRPExecutionContext </p>
 *
 */
public class OpenHRPExecutionContext
//        extends PeriodicExecutionContext implements Runnable{
extends PeriodicExecutionContext 
implements Runnable, ObjectCreator<ExecutionContextBase>, ObjectDestructor, ExecutionContextBase{
    /**
     * <p> Constructor </p>
     */
    public OpenHRPExecutionContext() {
        super();
        m_count = 0;
    }

    /**
     * <p> tick </p> 
     */
    public void tick() throws SystemException {
        if(m_workerthread.isRunning())
        {
            return ;
        }
        synchronized (m_tickmutex) {
            m_workerthread.invokeWorkerPostDo();
            TimeValue t0 = new TimeValue();
            t0.convert(System.nanoTime()/1000);
            m_workerthread.invokeWorkerDo();
            TimeValue t1 = new TimeValue();
            t1.convert(System.nanoTime()/1000);
            m_workerthread.invokeWorkerPostDo();
            TimeValue t2 = new TimeValue();
            t2.convert(System.nanoTime()/1000);
            
            TimeValue period = getPeriod();
            if(m_count > 1000)
            {
                TimeValue t1_w = t1;
                TimeValue t2_w = t2;
                TimeValue t2_w2 = t2;
                TimeValue period_w = period ;
                rtcout.println(Logbuf.PARANOID, "Period:     " + period_w + " [s]");
                rtcout.println(Logbuf.PARANOID, "Exec-Do:    " + t1_w.minus(t0) + " [s]");
                rtcout.println(Logbuf.PARANOID, "Exec-PostDo:" + t2_w.minus(t1) + " [s]");
                rtcout.println(Logbuf.PARANOID, "Sleep:      " + period_w.minus(t2_w2.minus(t0)) + " [s]");
            }
            TimeValue t3 = new TimeValue();
            t3.convert(System.nanoTime()/1000);
            t2.minus(t0);
            if( period.getUsec() > t2.getUsec() )
            {
                if( m_count > 1000)
                {
                    rtcout.println(Logbuf.PARANOID, "sleeping...");
                }
                period.minus(t2);
                try {
                    Thread.sleep(period.getUsec());
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            if(m_count > 1000)
            {
                TimeValue t4 = new TimeValue();
                t4.convert(System.nanoTime()/1000);
                rtcout.println(Logbuf.PARANOID, "Slept:     " + t4.minus(t3) + " [s]");
                m_count = 0;
            }
            ++m_count ;
            return ;
        }
    }

    private int m_count ;
    private String m_tickmutex  = new String();
    
    /**
     *
     */
    private ExecutionContextWorker m_workerthread = new ExecutionContextWorker();

    /**
     * {@.ja OpenHRPExecutionContext を生成する}
     * {@.en Creats OpenHRPExecutionContext}
     * 
     * @return 
     *   {@.ja 生成されたOpenHRPExecutionContext}
     *   {@.en Object Created instances}
     *
     *
     */
    public OpenHRPExecutionContext creator_() {
        return new OpenHRPExecutionContext();
    }
    /**
     * {@.ja Object を破棄する}
     * {@.en Destructs Object}
     * 
     * @param obj
     *   {@.ja 破棄するインタスタンス}
     *   {@.en The target instances for destruction}
     *
     */
    public void destructor_(Object obj) {
        obj = null;
    }
    /**
     * <p> OpenHRPExecutionContextInit </p>
     *
     * @param manager Manager
     */
    public static void OpenHRPExecutionContextInit(Manager manager) {
//        manager.registerECFactory("jp.go.aist.rtm.RTC.executionContext.OpenHRPExecutionContext");
        ExecutionContextFactory<ExecutionContextBase,String> factory 
                                        = ExecutionContextFactory.instance();
        factory.addFactory("jp.go.aist.rtm.RTC.executionContext.OpenHRPExecutionContext",
                    new OpenHRPExecutionContext(),
                    new OpenHRPExecutionContext());
    }
    
    /**
     * <p> ECDeleteFunc </p>
     *
     * @param comp ExecutionContextBase
     */
    public Object ECDeleteFunc(ExecutionContextBase comp) {
        return null;
    }

    /**
     * <p> ECNewFunc </p>
     *
     * @return ExecutionContextBase
     */
    public ExecutionContextBase ECNewFunc() {
        return this;
    }
    
}

