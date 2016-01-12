package jp.go.aist.rtm.RTC.util.clock;

import jp.go.aist.rtm.RTC.util.TimeValue;

/**
 * {@.ja 時刻設定・取得オブジェクトのインターフェース}
 * {@.en An interface to set and get time}
 *
 * <p>
 * {@.ja このクラスは ClockManager によって管理されるクロックオブジェクトの
 * ためのインターフェースである。ClockManager は複数のクロックオブジェ
 * クトを管理し、必要に応じて適切なクロックオブジェクトを IClock イン
 * ターフェースをもつオブジェクトとして返す。クロックオブジェクトは単
 * にシステム時刻を返すものや、独自の論理時刻を持つクロックオブジェク
 * ト等が考えられる。}
 * {@.en This class is a interface for clock objects managed by
 * ClockManager. ClockManager manages one or more clocks, and it
 * returns appropriate clock objects according to demands. The clock
 * object might be clock which just returns system time, or a clock
 * which returns individual logical time.}
 */
public interface IClock {
    
    /**
     * {@.ja 時刻を取得する}
     * {@.en Getting time}
     *
     * @return 
     *   {@.ja 現在の時刻}
     *   {@.en Current time}
     *
     */
    public TimeValue getTime();

    /**
     * {@.ja  時刻を設定する}
     * {@.en Setting time}
     *
     * @param clocktime 
     *   {@.ja 現在の時刻}
     *   {@.en clocktime Current time}
     *
     */
    public boolean setTime(TimeValue clocktime);
}
