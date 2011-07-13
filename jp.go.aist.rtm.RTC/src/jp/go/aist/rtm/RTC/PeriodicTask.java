package jp.go.aist.rtm.RTC;

import jp.go.aist.rtm.RTC.util.TimeValue;



  /**
   * {@.ja 周期タスクスレッド実行クラス。}
   * {@.en PeriodicTask class}
   *
   * <p>
   * {@.ja 特定の関数を周期実行するためのスレッドオブジェクトを実現する。
   * 使用手順は以下の通り。
   * 
   * <ol>
   * <li>task; // インスタンス生成
   * <li>task.setTask(TaskFuncBase(obj, mem_func)); // 実行する関数を与える
   * <li>task.activate(); // スレッドをスタートさせる
   * </ol>
   *
   * <ul>
   * <li>task.suspend(); // 周期実行を止める
   * <li>task.signal(); // 1周期だけ実行
   * <li>task.resume(); // 周期実行を再開
   *
   * <li>task.finalize(); // タスクを終了させる</ul>}
   * 
   */
public class PeriodicTask extends PeriodicTaskBase implements ObjectCreator<PeriodicTaskBase>, ObjectDestructor{

    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
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
     * {@.ja タスク実行を開始する。}
     * {@.en Starting the task}
     *
     * <p>
     * {@.ja タスクの実行を開始するためにスレッドをスタートさせる。タスクが
     * 正常に開始された場合は true が返り、すでにタスクが開始済み、また
     * は実行するタスクが設定されていなければ false を返す。}
     * {@.en Starting a thread to execute a task.  If the task/thread is
     * started properly, it will return 'TRUE'.  if the task/thread
     * are already started or task function object is not set, 'FALSE'
     * will be returned.}
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
     * {@.ja タスク実行を終了する。}
     * {@.en Finalizing the task}
     *
     * <p>
     * {@.ja 実行中のタスクを終了する。}
     * {@.en Finalizing the task running.}
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
     * {@.ja タスク実行を中断する。}
     * {@.en Suspending the task}
     *
     * <p>
     * {@.ja 実行中のタスクを中断する。}
     * {@.en Suspending the task running.}
     *
     */
    public int _suspend() {
        synchronized (m_suspend.mutex) {
            m_suspend.suspend = true;
        }
        return 0;
    }

    /**
     * {@.ja 中断されているタスクを再開する。}
     * {@.en Resuming the suspended task}
     * <p>
     * {@.ja 中断されているタスクを再開する}
     * {@.en Resuming the suspended task}
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
     * {@.ja 中断されているタスクを1周期だけ実行する。}
     * {@.en Executing the suspended task one tick}
     *
     * <p>
     * {@.ja 中断されているタスクを1周期だけ実行する}
     * {@.en Executing the suspended task one tick}
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
     * {@.ja タスク実行関数をセットする。}
     * {@.en Setting task execution function}
     *
     *
     * @param obj 
     *   {@.ja オブジェクト}
     *   {@.en Object}
     * @param func 
     *   {@.ja 関数名}
     *   {@.en Function name}
     * @param delete_in_dtor 
     *   {@.ja 削除フラグ}
     *   {@.en Delete flag.}
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
     * {@.ja タスク実行関数をセットする。}
     * {@.en Setting task execution function}
     *
     * 
     * @param obj 
     *   {@.ja オブジェクト}
     *   {@.en object}
     */
    public boolean setTask(Object obj) {
        return setTask(obj, "svc", true);
    }

    /**
     * {@.ja タスク実行関数をセットする。}
     * {@.en Setting task execution function}
     *
     *
     * @param obj 
     *   {@.ja オブジェクト}
     *   {@.en Object}
     * @param func 
     *   {@.ja 関数名}
     *   {@.en Function name}
     */
    public boolean setTask(Object obj,  String func) {
        return setTask(obj, func, true);
    }

    /**
     * {@.ja タスク実行関数をセットする。}
     * {@.en Setting task execution function}
     *
     * @param obj 
     *   {@.ja オブジェクト。}
     *   {@.en Object}
     * @param delete_in_dtor 
     *   {@.ja 削除フラグ}
     *   {@.en Delete flag.}
     */
    public boolean setTask(Object obj, boolean delete_in_dtor) {
        return setTask(obj, "svc", delete_in_dtor);
    }
    /**
     * {@.ja タスク実行周期をセットする。}
     * {@.en Setting task execution period}
     *
     * @param period 
     *   {@.ja 実行周期 [sec]}
     *   {@.en Execution period [sec]}
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
     * {@.ja タスク実行周期をセットする。}
     * {@.en Setting task execution period}
     *
     * @param period 
     *   {@.ja 実行周期}
     *   {@.en period Execution period}
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
     * {@.ja タスク関数実行時間計測を有効にするか。}
     * {@.en Validate a Task execute time measurement}
     *
     * <p> 
     * {@.en This function can set the measurement of the execution time 
     * effective/invalidly. }
     *
     * @param value 
     *   {@.ja フラグ(true: 有効, false: 無効)}
     *   {@.en true:effectuation}
     */
    public void executionMeasure(boolean value) {
        m_execMeasure = value;
    }
    
    /**
     * {@.ja タスク周期時間計測周期。}
     * {@.en Task period time measurement count}
     *
     * <p>
     * {@.en This function sets the cycle to measure the execution time.}
     *
     * @param n  
     *   {@.ja 計測周期}
     *   {@.en Cycle frequency}
     *
     */
    public void executionMeasureCount(int n) {
        m_execCountMax = n;
    }
    
    /**
     * {@.ja タスク周期時間計測を有効にするか。}
     * {@.en Validate a Task period time measurement}
     *
     * <p> 
     * {@.en This function can set the measurement of the execution time
     * effective/invalidly.}
     * @param  value 
     *   {@.ja フラグ(true: 有効, false: 無効)}
     *   {@.en true:effectuation}
     */
    public void periodicMeasure(boolean value) {
        m_periodMeasure = value;
    }
    
    /**
     * {@.ja タスク周期時間計測周期。}
     * {@.en Task period time measurement count}
     *
     * <p>
     * {@.en  This function sets the cycle to measure the execution time.}
     *
     * @param n  
     *   {@.ja 計測周期}
     *   {@.en Cycle frequency}
     */
    public void periodicMeasureCount(int n) {
        m_periodCountMax = n;
    }
    
    /**
     * {@.ja タスク関数実行時間計測結果を取得。}
     * {@.en Get a result in task execute time measurement}
     * <p> 
     * {@.en This function acquires the measurement result of the execution 
     * time.}
     * @return
     *   {@.ja 実行時間の統計}
     *   {@.en Statistics}
     */
    public TimeMeasure.Statistics getExecStat() {
        synchronized (m_execStat.mutex) {
            return m_execStat.stat;
        }
    }
    
    /**
     * {@.ja タスク周期時間計測結果を取得。}
     * {@.en Get a result in task period time measurement}
     *
     * <p> 
     * {@.en This function acquires the measurement result at time of the 
     * cycle.}
     * @return
     *   {@.ja 実行時間の統計}
     *   {@.en Statistics}
     */
    public TimeMeasure.Statistics getPeriodStat() {
        synchronized (m_periodStat.mutex) {
            return m_periodStat.stat;
        }
    }

    
    /**
     * {@.ja PeriodicTask 用のスレッド実行}
     * {@.en Thread execution for PeriodicTask}
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
     * {@.ja スレッド実行}
     * {@.en Thread execution}
     */
    public void run() {

        this.svc();
    }
    /**
     * {@.ja スレッド休止}
     * {@.en Thread sleep}
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
     * {@.ja 実行状態更新}
     * {@.en Update for execute state}
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
     * {@.ja 周期状態更新}
     * {@.en Update for period state}
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
    /**
     * {@.ja タスク実行周期}
     * {@.en Task execution period}
     */
    protected TimeValue m_period = new TimeValue(0.0);
    /**
     * {@.ja スレッド休止フラグ}
     * {@.en Thread sleep flag}
     */
    protected boolean m_nowait;
    /**
     * {@.ja タスク実行関数}
     * {@.en Task execution function}
     */
    protected TaskFuncBase m_func;
    /**
     * {@.ja タスク実行関数削除フラグ}
     * {@.en Task execution function delete flag}
     */
    protected boolean m_deleteInDtor;

    // alive flag
    /**
     * {@.ja alive_t クラス}
     * {@.en alive_t class}
     */
    protected class alive_t
    {
      /**
       * {@.ja コンストラクタ}
       * {@.en Constructor}
       */
      public alive_t(boolean val) {
          value = val;
      }
      /**
       * {@.ja フラグ格納用変数}
       * {@.en Variable for flag storage}
       */
      public boolean value;
      /**
       * {@.ja 排他制御用変数}
       * {@.en Variable for exclusive control}
       */
      public String mutex = new String();
    };
    /**
     * {@.ja タスク生存フラグ}
     * {@.en Task alive flag}
     */
    protected alive_t m_alive = new alive_t(false);

    // suspend flag
    /**
     * {@.ja タスク中断管理用クラス}
     * {@.en Class for task suspend management}
     */
    protected class suspend_t
    {
      /**
       * {@.ja コンストラクタ}
       * {@.en Constructor}
       */
      public suspend_t(boolean sus){
          suspend = sus;
      }
      /**
       * {@.ja フラグ格納用変数}
       * {@.en Variable for flag storage}
       */
      public boolean suspend;
      /**
       * {@.ja 排他制御用変数}
       * {@.en Variable for exclusive control}
       */
      public String mutex = new String();
    };
    /**
     * {@.ja タスク中断情報}
     * {@.en Task suspend infomation}
     */
    protected suspend_t m_suspend = new suspend_t(false);
      
    // time measurement statistics struct
    /**
     * {@.ja タスク実行時間計測管理用クラス}
     * {@.en Structure for task execution time measurement management}
     */
    protected class statistics_t
    {
      /**
       * {@.ja 計測時間}
       * {@.en Measurement time}
       */
      public TimeMeasure.Statistics stat;
      /**
       * {@.ja 排他制御用変数}
       * {@.en Variable for exclusive control}
       */
      public String mutex = new String();
    };

    // variables for execution time measurement
    /**
     * {@.ja タスク実行時間計測フラグ}
     * {@.en Task execution time measurement flag}
     */
    protected boolean              m_execMeasure;
    /**
     * {@.ja タスク実行時間計測回数}
     * {@.en Task execution time measurement count}
     */
    protected int      m_execCount;
    /**
     * {@.ja タスク実行時間計測周期}
     * {@.en Task execution time measurement max count}
     */
    protected int      m_execCountMax;
    /**
     * {@.ja タスク実行時間計測統計}
     * {@.en Task execution time measurement statistics}
     */
    protected statistics_t m_execStat;
    /**
     * {@.ja タスク実行時間計測情報}
     * {@.en Task execution time  measurement infomation}
     */
    protected TimeMeasure m_execTime;

    // variables for period time measurement
    /**
     * {@.ja タスク周期時間計測フラグ}
     * {@.en Task periodic time measurement flag}
     */
    protected boolean              m_periodMeasure;
    /**
     * {@.ja タスク周期時間計測回数}
     * {@.en Task periodic time measurement count}
     */
    protected int      m_periodCount;
    /**
     * {@.ja タスク周期時間計測最大数}
     * {@.en Task periodic time measurement max count}
     */
    protected int      m_periodCountMax;
    /**
     * {@.ja タスク周期時間計測統計}
     * {@.en Task periodic time measurement statistics}
     */
    protected statistics_t m_periodStat;
    /**
     * {@.ja タスク周期時間計測情報}
     * {@.en Task periodic time  measurement infomation}
     */
    protected TimeMeasure m_periodTime;

    /**
     * {@.ja PeriodicTaskを生成する。}
     * {@.en Creats PeriodicTask.}
     * 
     * @return 
     *   {@.ja 生成したインスタンスのPeriodicTaskBase}
     *   {@.en Object Created PeriodicTaskBase}
     *
     */
    public PeriodicTaskBase creator_() {
        return new PeriodicTask();
    }
    /**
     * {@.ja インスタンスを破棄する。}
     * {@.en Destroys the object.}
     * 
     * @param obj    
     *   {@.ja 破壊するインスタンス}
     *   {@.en The target instances for destruction}
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
     * {@.ja 初期化処理。}
     * {@.en Initialization}
     * <p>
     * {@.ja ファクトリへオブジェクトを追加する。}
     * {@.en Adds the object to the factory.}
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

