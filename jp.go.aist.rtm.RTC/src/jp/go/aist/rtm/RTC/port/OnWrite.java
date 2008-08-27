package jp.go.aist.rtm.RTC.port;

/**
 * <p>InPort/OutPortのバッファにデータが書き込まれる直前に呼び出されるコールバックインタフェースです。</p>
 */
public interface OnWrite<DataType> {

    /**
     * <p>コールバックメソッドです。</p>
     * 
     * @param value バッファに書き込まれるデータ
     */
    void run(final DataType value);
    
}
