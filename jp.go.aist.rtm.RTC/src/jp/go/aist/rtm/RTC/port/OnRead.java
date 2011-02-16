package jp.go.aist.rtm.RTC.port;

/**
 * <p>InPort/OutPortのバッファからデータが読み出される直前に呼び出される
 * コールバックインタフェースです。</p>
 */
public interface OnRead<DataType> {
    
    /**
     * <p>コールバックメソッドです。</p>
     */
    void run();
    
}
