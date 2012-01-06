package jp.go.aist.rtm.RTC;

/**
 * {@.ja コンポーネント破棄用インタフェース}
 * {@.en Interface for component destruction}
 */
public interface RtcDeleteFunc {
    
    /**
     * {@.ja コンポーネントの破棄処理を行う。}
     * {@.en Deletes the component.}
     * 
     * @param component 
     *   {@.ja 破棄対象コンポーネントのオブジェクト}
     *   {@.en Object of component for destruction}
     */
    public void deleteRtc(RTObject_impl component);

}
