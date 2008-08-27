package jp.go.aist.rtm.RTC;

/**
 * <p>コンポーネント破棄用インタフェースです。</p>
 */
public interface RtcDeleteFunc {
    
    /**
     * <p>コンポーネントの破棄処理を行います。</p>
     * 
     * @param component 破棄対象コンポーネントのオブジェクト
     */
    public void deleteRtc(RTObject_impl component);

}
