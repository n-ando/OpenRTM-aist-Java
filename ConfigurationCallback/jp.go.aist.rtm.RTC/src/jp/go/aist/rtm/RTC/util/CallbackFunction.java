package jp.go.aist.rtm.RTC.util;

/**
 * {@.ja タイマーから起動されるコールバック関数用インタフェース}
 * {@.en Interface for the callback function started when the timer ends. }
 */
public interface CallbackFunction {
    /**
     * {@.ja タイマーから起動されるコールバックメソッド}
     * {@.en Method of the callback started from Timer.}
     */
    public void doOperate();
}
