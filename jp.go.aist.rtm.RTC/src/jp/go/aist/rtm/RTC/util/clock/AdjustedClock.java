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
public class AdjustedClock implements IClock {
    private TimeValue m_offset;

    public AdjustedClock() {
        m_offset = new TimeValue(0.0);
    }
    
    public TimeValue getTime() {
        synchronized (m_offset) {
            long msec = System.currentTimeMillis();
            long sec = msec/1000;
            long usec = (msec - sec*1000)*1000;
            TimeValue base = new TimeValue(sec, usec);
            return base.minus(m_offset);
        }
    }

    public boolean setTime(TimeValue clocktime) {
        synchronized (m_offset) {
            long msec = System.currentTimeMillis();
            long sec = msec/1000;
            long usec = (msec - sec*1000)*1000;
            TimeValue base = new TimeValue(sec, usec);
            m_offset = base.minus(clocktime);
        }
        return true;
    }
}
