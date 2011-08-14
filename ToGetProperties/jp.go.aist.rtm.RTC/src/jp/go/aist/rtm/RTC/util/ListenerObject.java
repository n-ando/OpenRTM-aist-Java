package jp.go.aist.rtm.RTC.util;

/**
 * {@.ja タイマーに登録するリスナー用クラス}
 * {@.en Class for listener registered in timer}
 */
public class ListenerObject implements ListenerBase {

    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     * 
     * @param cbf 
     *   {@.ja コールバック関数用インスタンス}
     *   {@.en Instance of callback function}
     */
    public ListenerObject(CallbackFunction cbf) {
        m_cbf = cbf;
    }

    /**
     * {@.ja 登録先のタイマーから呼び出されるメソッド}
     * {@.en Method that calls from timer at registration destination}
     */
    public void invoke() {
        m_cbf.doOperate();
    }
    private CallbackFunction m_cbf;
}
