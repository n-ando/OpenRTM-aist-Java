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
    protected int TIME_CONV_UNIT = 1000;
    
    protected long nanoBase;
    private long prevmcSec;
    private long nanoOffset;
    
    public SystemClock() {
        nanoBase = System.nanoTime();
        nanoOffset = 0;
    }

    public TimeValue getTime() {
        long msec = System.currentTimeMillis();
        long diffNano = System.nanoTime() - nanoBase;
        //
        long sec = msec/TIME_CONV_UNIT;
        if(prevmcSec!=msec) {
            prevmcSec = msec;
            nanoOffset = diffNano;
        }
        long modNano = ((diffNano-nanoOffset)%(TIME_CONV_UNIT*TIME_CONV_UNIT))/TIME_CONV_UNIT;
        long usec = (msec - sec*TIME_CONV_UNIT)*TIME_CONV_UNIT + modNano;
        TimeValue base = new TimeValue(sec, usec);
        return base;
    }

    public boolean setTime(TimeValue clocktime) {
        return false;
    }

}
