package jp.go.aist.rtm.RTC.port;

/**
 * <p>InPort/OutPortのバッファにデータが書き込まれる直前に呼び出されるコールバックインタフェースです。</p>
 */
public interface OnWriteTimeout<DataType> {
    
    /**
     * <p>コールバックメソッドです。</p>
     * 
     * @param value 書き込み値
     */
    void run(final DataType value);
    
}
