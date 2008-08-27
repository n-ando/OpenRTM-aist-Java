package jp.go.aist.rtm.RTC.port;

/**
 * <p>InPort/OutPortのバッファにデータが書き込まれる際、バッファオーバフローにより
 * バッファへの書き込みが行えない場合に呼び出されるコールバックメソッドです。</p>
 */
public interface OnOverflow<DataType> {

    /**
     * <p>コールバックメソッドです。</p>
     * 
     * @param value 書き込み値
     */
    void run(final DataType value);
    
}
