package jp.go.aist.rtm.RTC;

import jp.go.aist.rtm.RTC.util.TimeValue;

  /**
   * {@.ja タスク制御用抽象クラス}
   * {@.en Abstract class for task control}
   */
public abstract class PeriodicTaskBase extends Thread {

    /**
     * {@.ja タスク実行を開始する。}
     * {@.en Starting the task}
     * <p>
     * {@.ja タスク実行を開始する純粋仮想関数}
     * {@.en Pure virtual function for starting the task.}
     */
    public abstract void activate();
    /**
     * {@.ja タスク実行を終了する。}
     * {@.en Finalizing the task}
     *
     * <p>
     * {@.ja タスク実行を終了する純粋仮想関数。}
     * {@.en Pure virtual function for finalizing the task.}
     */
    public abstract void _finalize();
    /**
     * {@.ja タスク実行を中断する。}
     * {@.en Suspending the task}
     *
     * <p>
     * {@.ja タスク実行を中断する純粋仮想関数。}
     * {@.en Pure virtual function for suspending the task.}
     *
     */
    public abstract int _suspend();
    /**
     * {@.ja 中断されているタスクを再開する。}
     * {@.en Resuming the suspended task}
     *
     * <p>
     * {@.ja 中断されているタスクを再開する純粋仮想関数。}
     * {@.en Pure virtual function for resuming the suspended task.}
     *
     */
    public abstract int _resume();
    /**
     * {@.ja 中断されているタスクを1周期だけ実行する。}
     * {@.en Executing the suspended task one tick}
     *
     * <p>
     * {@.ja 中断されているタスクを1周期だけ実行する純粋仮想関数。}
     * {@.en Pure virtual function for executing the suspended task one tick.}
     */
    public abstract void signal();
    /**
     * {@.ja タスク実行関数をセットする。}
     * {@.en Setting task execution function}
     *
     * <p>
     * {@.ja タスク実行関数をセットする純粋仮想関数。}
     * {@.en Pure virtual function for setting task execution function.}
     *
     * @param obj 
     *   {@.ja オブジェクト。}
     *   {@.en Object}
     * @param delete_in_dtor 
     *   {@.ja 削除フラグ}
     *   {@.en Delete flag.}
     */
    public abstract boolean setTask(Object obj, boolean delete_in_dtor);
    /**
     * {@.ja タスク実行関数をセットする。}
     * {@.en Setting task execution function}
     *
     * <p>
     * {@.ja タスク実行関数をセットする純粋仮想関数。}
     * {@.en Pure virtual function for setting task execution function.}
     *
     * @param obj 
     *   {@.ja オブジェクト}
     *   {@.en Object}
     */
    public abstract boolean setTask(Object obj);
    /**
     * {@.ja タスク実行関数をセットする。}
     * {@.en Setting task execution function}
     *
     * <p>
     * {@.ja タスク実行関数をセットする純粋仮想関数。}
     * {@.en Pure virtual function for setting task execution function.}
     *
     * @param obj 
     *   {@.ja オブジェクト}
     *   {@.en Object}
     * @param func 
     *   {@.ja 関数名}
     *   {@.en Function name}
     */
    public abstract boolean setTask(Object obj,  String func); 
    /**
     * {@.ja タスク実行関数をセットする。}
     * {@.en Setting task execution function}
     *
     * <p>
     * {@.ja タスク実行関数をセットする純粋仮想関数。}
     * {@.en Pure virtual function for setting task execution function.}
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
    public abstract boolean setTask(Object obj, String func, 
                                                    boolean delete_in_dtor);

    /**
     * {@.ja タスク実行周期をセットする。}
     * {@.en Setting task execution period}
     *
     * <p>
     * {@.ja タスク実行周期をセットする純粋仮想関数。}
     * {@.en Pure virtual function for setting task execution period.}
     *
     * @param period 
     *   {@.ja 実行周期}
     *   {@.en Execution period.}
     *
     */
    public abstract void setPeriod(double period);
    /**
     * {@.ja タスク実行周期をセットする。}
     * {@.en Setting task execution period}
     *
     * <p>
     * {@.ja タスク実行周期をセットする純粋仮想関数。}
     * {@.en Pure virtual function for setting task execution period.}
     *
     * @param period 
     *   {@.ja 実行周期}
     *   {@.en Execution period.}
     *
     */
    public abstract void setPeriod(TimeValue period);
    /**
     * {@.ja タスク関数実行時間計測を有効にする。}
     * {@.en Validate a Task execute time measurement}
     *
     * <p>
     * {@.ja タスク関数実行時間計測を有効にする純粋仮想関数。}
     * {@.en Pure virtual function for validate a Task execute time 
     * measurement.}
     *
     * @param value 
     *   {@.ja フラグ(true: 有効, false: 無効)}
     *   {@.en flag(true: Valid, false: Invalid).}
     *
     */
    public abstract void executionMeasure(boolean value);
    /**
     * {@.ja タスク関数実行時間計測周期用純粋仮想関数。}
     * {@.en Task execute time measurement period}
     *
     * <p>
     * {@.ja タスク関数実行時間計測周期用純粋仮想関数。}
     * {@.en Pure virtual function for task execute time measurement period.}
     *
     * @param n 
     *   {@.ja 計測周期}
     *   {@.en Measurement period.}
     *
     */
    public abstract void executionMeasureCount(int n);
    /**
     * {@.ja タスク周期時間計測を有効にする。}
     * {@.en Validate a Task period time measurement}
     *
     * <p>
     * {@.ja タスク周期時間計測を有効にする純粋仮想関数。}
     * {@.en Pure virtual function for validate a Task period time measurement.}
     *
     * @param value 
     *   {@.ja フラグ(true: 有効, false: 無効)}
     *   {@.en flag(true: Valid, false: Invalid).}
     */
    public abstract void periodicMeasure(boolean value);
    /**
     * {@.ja タスク周期時間計測周期用純粋仮想関数。}
     * {@.en Task period time measurement count}
     *
     * <p>
     * {@.ja タスク周期時間計測周期用純粋仮想関数。}
     * {@.en Pure virtual function for task period time measurement count.}
     *
     * @param n 
     *   {@.ja 計測周期}
     *   {@.en Measurement period.}
     *
     */
    public abstract void periodicMeasureCount(int n);
    /**
     * {@.ja タスク関数実行時間計測結果を取得する。}
     * {@.en Get a result in task execute time measurement}
     *
     * <p>
     * {@.ja タスク関数実行時間計測結果を取得する純粋仮想関数。}
     * {@.en Pure virtual function for get a result in task execute 
     * time measurement.}
     *
     * @return
     *   {@.ja 実行時間の統計}
     *   {@.en Statistics}
     *
     */
    public abstract TimeMeasure.Statistics getExecStat();
    /**
     * {@.ja タスク周期時間計測結果を取得する。}
     * {@.en Get a result in task period time measurement}
     *
     * <p>
     * {@.ja タスク周期時間計測結果を取得する純粋仮想関数。}
     * {@.en Pure virtual function for get a result in task period 
     * time measurement.}
     *
     * @return
     *   {@.ja 実行時間の統計}
     *   {@.en Statistics}
     */
    public abstract TimeMeasure.Statistics getPeriodStat();

}

