package jp.go.aist.rtm.RTC.util.clock;

import jp.go.aist.rtm.RTC.util.TimeValue;

/**
 * {@.ja 調整済み時刻を扱うクロックオブジェクト}
 * {@.en Clock object to handle adjusted clock}
 *
 * <p>
 * {@.ja settime() 呼び出し時に現在時刻との差をオフセットとして保持し、
 * gettime() によってオフセット調整済みの時刻を返す。}
 * {@.en This class stores a offset time with current system clock when
 * settime(), and gettime() returns adjusted clock by the offset.}
 */
public class AdjustedClock extends SystemClock {
    private TimeValue m_offset;
    private final Object m_lock = new Object();

    public AdjustedClock() {
        super();
        m_offset = new TimeValue(0.0);
    }
    
    public TimeValue getTime() {
        synchronized (m_lock) {
            TimeValue base = super.getTime();
            return base.minus(m_offset);
        }
    }

    public boolean setTime(TimeValue clocktime) {
        synchronized (m_lock) {
            long msec = System.currentTimeMillis();
            long sec = msec/TIME_CONV_UNIT;
            long usec = (msec - sec*TIME_CONV_UNIT)*TIME_CONV_UNIT;
            TimeValue base = new TimeValue(sec, usec);
            m_offset = base.minus(clocktime);
            nanoBase = System.nanoTime();
        }
        return true;
    }
}
