package jp.go.aist.rtm.RTC;

/**
 * <p>コンポーネント生成用インタフェースです。</p>
 */
public interface RtcNewFunc {
    
    /**
     * <p>コンポーネントの生成処理を行います。</p>
     * 
     * @param mgr Managerオブジェクト
     * @return 生成されたコンポーネントオブジェクト
     */
    public RTObject_impl createRtc(Manager mgr);

}
