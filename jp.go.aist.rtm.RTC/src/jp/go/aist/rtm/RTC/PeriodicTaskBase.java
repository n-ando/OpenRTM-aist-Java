package jp.go.aist.rtm.RTC;

import java.lang.Thread;
import java.lang.reflect.Method;

import jp.go.aist.rtm.RTC.TaskFuncBase;
import jp.go.aist.rtm.RTC.TimeMeasure;
import jp.go.aist.rtm.RTC.util.TimeValue;

/**
 *
 * <p>  </p>
 *
 */
public abstract class PeriodicTaskBase extends Thread {

    public abstract void activate();
    public abstract void _finalize();
    public abstract int _suspend();
    public abstract int _resume();
    public abstract void signal();
    public abstract boolean setTask(Object obj, boolean delete_in_dtor);
    public abstract boolean setTask(Object obj);
    public abstract boolean setTask(Object obj,  String func); 
    public abstract boolean setTask(Object obj, String func, boolean delete_in_dtor);

    public abstract void setPeriod(double period);
    public abstract void setPeriod(TimeValue period);
    public abstract void executionMeasure(boolean value);
    public abstract void executionMeasureCount(int n);
    public abstract void periodicMeasure(boolean value);
    public abstract void periodicMeasureCount(int n);
    public abstract TimeMeasure.Statistics getExecStat();
    public abstract TimeMeasure.Statistics getPeriodStat();

}

