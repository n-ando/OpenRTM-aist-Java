package jp.go.aist.rtm.RTC.port;

/**
 * <p>InPort/OutPortのバッファからデータが読み出される直前に呼び出される
 * コールバックインタフェースです。</p>
 */
public interface OnReadTimeout<DataType> {
    
    /**
     * <p>コールバックメソッドです。</p>
     * 
     * @return 代替となる読み出しデータ 
     */
    DataType run();
    
}
