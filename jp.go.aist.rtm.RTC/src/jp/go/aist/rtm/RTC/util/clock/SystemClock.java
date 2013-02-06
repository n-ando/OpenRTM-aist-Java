package jp.go.aist.rtm.RTC.util.clock;

import jp.go.aist.rtm.RTC.util.TimeValue;

/**
 * {@.ja システム時刻を扱うクロックオブジェクト}
 * {@.en clock object to handle system clock}
 *
 * <p>
 * {@.ja このクラスはシステムクロックを設定または取得するクラスである。}
 * {@.en This class sets and gets system clock.}
 */
public class SystemClock implements IClock {

    public TimeValue getTime() {
        long msec = System.currentTimeMillis();
        long sec = msec/1000;
        long usec = (msec - sec*1000)*1000;
        TimeValue base = new TimeValue(sec, usec);
        return base;
    }

    public boolean setTime(TimeValue clocktime) {
        return false;
    }

}
