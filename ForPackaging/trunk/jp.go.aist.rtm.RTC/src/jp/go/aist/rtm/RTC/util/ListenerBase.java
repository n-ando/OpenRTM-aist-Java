package jp.go.aist.rtm.RTC.util;

/**
 * {@.ja タイマーに登録するリスナー用インタフェース}
 * {@.en Interface for listener registers in timer}
 */
public interface ListenerBase {
    /**
     * {@.ja タイマーがリスナーの処理を実行するためのメソッド}
     * {@.en Method for execution of timer of listener processing}
     */
    public void invoke();
}
