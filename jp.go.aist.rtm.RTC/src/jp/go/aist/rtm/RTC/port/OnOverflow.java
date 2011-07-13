package jp.go.aist.rtm.RTC.port;

/**
 * {@.ja InPort/OutPortのバッファにデータが書き込まれる際、
 *  バッファオーバフローによりバッファへの書き込みが行えない場合に
 *   呼び出されるコールバックメソッド}
 * {@.en Method of callback called when data cannot be written by buffer
 * overflow}
 */
public interface OnOverflow<DataType> {

    /**
     * {@.ja コールバックメソッド}
     * {@.en Callback method}
     * 
     * @param value 
     *   {@.ja 書き込み値}
     *   {@.en Value that tried to write}
     */
    void run(final DataType value);
    
}
