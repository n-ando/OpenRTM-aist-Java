package jp.go.aist.rtm.RTC.util.clock;

import jp.go.aist.rtm.RTC.util.TimeValue;

/**
 * {@.ja 論理時間を扱うクロックオブジェクト}
 * {@.en Clock object to handle logical clock}
 *
 * <p>
 * {@.ja このクラスは論理時間を設定または取得するクラスである。
 * 単純に settime() によって設定された時刻を gettime() によって取得する。}
 * {@.en This class sets and gets system clock.
 * It just sets time by settime() and gets time by gettime().}
 */
public class LogicalClock implements IClock {
    
    private TimeValue m_currentTime;
    private final Object m_lock = new Object();

    public LogicalClock() {
        m_currentTime = new TimeValue(0.0);
    }
    
    public TimeValue getTime() {
        synchronized (m_lock) {
            return m_currentTime;
        }
    }

    public boolean setTime(TimeValue clocktime) {
        synchronized (m_lock) {
            m_currentTime = clocktime;
        }
        return true;
    }

}
