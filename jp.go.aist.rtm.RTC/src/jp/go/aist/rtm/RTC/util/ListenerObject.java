package jp.go.aist.rtm.RTC.util;


/**
 * <p>タイマーに登録するリスナー用クラスです。</p>
 */
public class ListenerObject implements ListenerBase {

    /**
     * <p>コンストラクタです。</p>
     * 
     * @param cbf コールバック関数用インスタンス
     */
    public ListenerObject(CallbackFunction cbf) {
        m_cbf = cbf;
    }

    /**
     * <p>登録先のタイマーから呼び出されるメソッドです。</p>
     */
    public void invoke() {
        m_cbf.doOperate();
    }

    private CallbackFunction m_cbf;
}
