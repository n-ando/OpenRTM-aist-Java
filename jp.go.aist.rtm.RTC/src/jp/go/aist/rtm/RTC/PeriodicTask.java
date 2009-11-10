package jp.go.aist.rtm.RTC;


import java.lang.Object;
import java.lang.Thread;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.lang.IllegalAccessException;

import jp.go.aist.rtm.RTC.util.TimeValue;
import jp.go.aist.rtm.RTC.TimeMeasure;
import jp.go.aist.rtm.RTC.ObjectCreator;
import jp.go.aist.rtm.RTC.ObjectDestructor;
import jp.go.aist.rtm.RTC.PeriodicTaskFactory;



/**
* <p>PeriodicTask</p>
*/
public class PeriodicTask extends PeriodicTaskBase implements ObjectCreator<PeriodicTaskBase>, ObjectDestructor{

    /**
     * <p> ctor </p>
     */
    public PeriodicTask() {
        m_nowait = false;
        m_func = null;
        m_deleteInDtor = true;
        m_execCount = 0;
        m_execCountMax = 10;
        m_periodCount = 0;
        m_periodCountMax = 10;
        m_execTime = new TimeMeasure();
        m_execStat = new statistics_t();
        m_execStat.stat = m_execTime.getStatistics();

        m_periodTime = new TimeMeasure();
        m_periodStat = new statistics_t();
        m_periodStat.stat = m_periodTime.getStatistics();
    }
    
    /**
     * <p> Starting the task </p>
     *
     * Starting a thread to execute a task.  If the task/thread is
     * started properly, it will return 'TRUE'.  if the task/thread
     * are already started or task function object is not set, 'FALSE'
     * will be returned.
     *
     *
     */
    public void activate() {
        synchronized (m_alive.mutex) {
            if (m_func == null)   { return; }
            if (m_alive.value) { return; }

            m_alive.value = true;
            start();
        }
    }

    /**
     *
     * <p> Finalizing the task </p>
     *
     * Finalizing the task running.
     *
     */
    public void _finalize() {
        synchronized (m_alive.mutex) {
            m_alive.value = false;
        }
        synchronized (m_suspend.mutex) {
            m_suspend.suspend = false;
            try {
                m_suspend.mutex.notify();
            }
            catch(java.lang.IllegalMonitorStateException e) {
            }
        }
    }

    /**
     * <p> Suspending the task </p>
     *
     * Suspending the task running.
     *
     */
    public int _suspend() {
        synchronized (m_suspend.mutex) {
            m_suspend.suspend = true;
        }
        return 0;
    }

    /**
     * <p> Resuming the suspended task </p> 
     *
     * Resuming the suspended task
     *
     */
    public int _resume() {
        m_periodTime.reset();
        m_execTime.reset();

        synchronized (m_suspend.mutex) {
            m_suspend.suspend = false;
            try {
                m_suspend.mutex.notify();
            }
            catch(java.lang.IllegalMonitorStateException e) {
            }
        }
        return 0;
    }

    /**
     * <p> Executing the suspended task one tick </p>
     *
     * Executing the suspended task one tick
     *
     */
    public void signal() {
        synchronized (m_suspend.mutex) {
            try {
                m_suspend.mutex.notify();
            }
            catch(java.lang.IllegalMonitorStateException e) {
            }
        }
    }

    /**
     * <p> Setting task execution function </p>
     *
     * @param obj 
     * @param delete_in_dtor
     * @return boolean
     *
     */
    public boolean setTask(Object obj, String func, boolean delete_in_dtor) {
        if (obj == null) { 
            return false; 
        }
        m_deleteInDtor = delete_in_dtor;
        m_func = new TaskFuncBase(obj,func);
        return true;
    }
    /**
     * <p> Setting task execution function </p>
     *
     * @param obj Set int (*)() type function pointer
     * @return boolean
     *
     */
    public boolean setTask(Object obj) {
        return setTask(obj, "svc", true);
    }

    public boolean setTask(Object obj,  String func) {
        return setTask(obj, func, true);
    }

    public boolean setTask(Object obj, boolean delete_in_dtor) {
        return setTask(obj, "svc", delete_in_dtor);
    }
    /**
     * <p> Setting task execution period </p>
     *
     * @param period Execution period [sec]
     *
     */
    public void setPeriod(double period) {
        m_period.convert(period);

        if (m_period.sec() == 0 && m_period.usec() == 0)
          {
            m_nowait = true;
            return;
          }
        m_nowait = false;
        return;
    }

    /**
     * <p> Setting task execution period </p>
     *
     * @param period Execution period
     *
     */
    public void setPeriod(TimeValue period) {
        m_period = period;

        if (m_period.sec() == 0 && m_period.usec() == 0)
          {
            m_nowait = true;
            return;
          }
        m_nowait = false;
        return;
    }

    /**
     * <p> This function can set the measurement of the execution time 
     * effective/invalidly.  </p>
     * @param  value true:effectuation
     */
    public void executionMeasure(boolean value) {
        m_execMeasure = value;
    }
    
    /**
     * <p> This function sets the cycle to measure the execution time.  </p>
     *
     * @param n  Cycle frequency
     */
    public void executionMeasureCount(int n) {
        m_execCountMax = n;
    }
    
    /**
     * <p> This function can set the measurement of the execution time i
     * effective/invalidly. </p> 
     * @param  value true:effectuation
     */
    public void periodicMeasure(boolean value) {
        m_periodMeasure = value;
    }
    
    /**
     * <p> This function sets the cycle to measure the execution time.  </p>
     *
     * @param n  Cycle frequency
     */
    public void periodicMeasureCount(int n) {
        m_periodCountMax = n;
    }
    
    /**
     * <p> This function acquires the measurement result of the execution 
     * time.  </p> 
     * @return TimeMeasure.Statistics
     */
    public TimeMeasure.Statistics getExecStat() {
        synchronized (m_execStat.mutex) {
            return m_execStat.stat;
        }
    }
    
    /**
     * <p> This function acquires the measurement result at time of the 
     * cycle.  </p>
     * @return TimeMeasure.Statistics
     */
    public TimeMeasure.Statistics getPeriodStat() {
        synchronized (m_periodStat.mutex) {
            return m_periodStat.stat;
        }
    }

    
    /**
     * <p> svc </p>
     *
     * @return int
     * 
     */
    protected int svc() {
        while (m_alive.value){ // needs lock?
            if (m_periodMeasure) { 
                m_periodTime.tack(); 
            }
            { // wait if suspended
              synchronized (m_suspend.mutex) {
                  if (m_suspend.suspend) {
                      try {
                          m_suspend.mutex.wait();
                      }
                      catch(InterruptedException e ){
                      }
                      // break if finalized
                      if (!m_alive.value)
                        {
                          return 0;
                        }
                    }
              }
            }
            if (m_periodMeasure) { m_periodTime.tick(); }

            // task execution
            if (m_execMeasure) { m_execTime.tick(); }
            m_func.svc();
            if (m_execMeasure) { m_execTime.tack(); }
    
            // wait for next period
            updateExecStat();
            sleep();
            updatePeriodStat();
        }
        return 0;
    }
    /**
     * <p> run </p>
     */
    public void run() {

        this.svc();
    }
    /**
     * <p> sleep </p>
     * 
     */
    protected void sleep() { 
        if (m_nowait) {
            return;
          }
        TimeValue tv = 
           new TimeValue(m_period.toDouble()-m_execTime.interval().toDouble());
        if (tv.toDouble()<0) {
            return;
        } 
        try{
            sleep((long)(tv.toDouble()*1000));
        }
        catch(InterruptedException e){
        }
    }
    /**
     * <p> updateExecStat </p>
     * 
     */
    protected void updateExecStat() {
        if (m_execCount > m_execCountMax) {
            synchronized (m_execStat.mutex) {
                m_execStat.stat = m_execTime.getStatistics();
                m_execCount = 0;
            }
        }
        ++m_execCount;
    }
    /**
     * <p> updatePeriodStat </p>
     * 
     */
    protected void updatePeriodStat() {
        if (m_periodCount > m_periodCountMax) {
            synchronized (m_periodStat.mutex) {
                m_periodStat.stat = m_periodTime.getStatistics();
                m_periodCount = 0;
            }
        }
        ++m_periodCount;
    }

    // execution period
    protected TimeValue m_period = new TimeValue(0.0);
    protected boolean m_nowait;
    protected TaskFuncBase m_func;
    protected boolean m_deleteInDtor;

    // alive flag
    protected class alive_t
    {
      public alive_t(boolean val) {
          value = val;
      }
      public boolean value;
      public String mutex = new String();
    };
    protected alive_t m_alive = new alive_t(false);

    // suspend flag
    protected class suspend_t
    {
      public suspend_t(boolean sus){
          suspend = sus;
      }
      public boolean suspend;
      public String mutex = new String();
    };
    protected suspend_t m_suspend = new suspend_t(false);
      
    // time measurement statistics struct
    protected class statistics_t
    {
      public TimeMeasure.Statistics stat;
      public String mutex = new String();
    };

    // variables for execution time measurement
    protected boolean              m_execMeasure;
    protected int      m_execCount;
    protected int      m_execCountMax;
    protected statistics_t m_execStat;
    protected TimeMeasure m_execTime;

    // variables for period time measurement
    protected boolean              m_periodMeasure;
    protected int      m_periodCount;
    protected int      m_periodCountMax;
    protected statistics_t m_periodStat;
    protected TimeMeasure m_periodTime;

    /**
     * <p> creator_ </p>
     * 
     * @return Object Created instances
     *
     */
    public PeriodicTaskBase creator_() {
        return new PeriodicTask();
    }
    /**
     * <p> destructor_ </p>
     * 
     * @param obj    The target instances for destruction
     *
     */
    public void destructor_(Object obj) {
        PeriodicTaskBase task = (PeriodicTaskBase)obj;
        task._finalize();
        try{
            task.join();
        }
        catch(java.lang.InterruptedException e){
            ;
        }
        obj = null;
    }

    /**
     * <p> PeriodicTaskInit </p>
     *
     */
    public static void PeriodicTaskInit() {
        final PeriodicTaskFactory<PeriodicTaskBase,String> factory 
            = PeriodicTaskFactory.instance();

        factory.addFactory("default",
			   new PeriodicTask(),
			   new PeriodicTask());
    
    }
};

