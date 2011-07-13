package jp.go.aist.rtm.RTC;

/**
 * {@.ja コンポーネント生成用インタフェース。}
 * {@.en Interface for component Creation}
 */
public interface RtcNewFunc {
    
    /**
     * {@.ja コンポーネントの生成処理を行う。}
     * {@.en Creates the component.}
     * 
     * @param mgr 
     *   {@.ja Managerオブジェクト}
     *   {@.en Manager object}
     * @return 
     *   {@.ja 生成されたコンポーネントオブジェクト}
     *   {@.en Generated component object}
     */
    public RTObject_impl createRtc(Manager mgr);

}
