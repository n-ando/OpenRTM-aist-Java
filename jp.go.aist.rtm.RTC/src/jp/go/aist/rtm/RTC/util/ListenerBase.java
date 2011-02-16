package jp.go.aist.rtm.RTC.util;

/**
 * <p>タイマーに登録するリスナー用インタフェースです。</p>
 */
public interface ListenerBase {
    /**
     * <p>タイマーがリスナーの処理を実行するためのメソッドです。</p>
     */
    public void invoke();
}
