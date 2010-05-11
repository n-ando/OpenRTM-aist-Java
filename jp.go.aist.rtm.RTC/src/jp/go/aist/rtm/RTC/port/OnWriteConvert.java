package jp.go.aist.rtm.RTC.port;

/**
 * <p>InPort/OutPortのバッファにデータが書き込まれる際に呼び出されるコールバックインタフェースです。<br />
 * 本コールバックの戻り値がバッファに書き込まれるため、書き込み値のフィルタとして機能します。</p>
 */
public interface OnWriteConvert<DataType> {
    
    /**
     * <p>コールバックメソッドです。</p>
     * 
     * @param value フィルタ前の書き込み値
     * @return フィルタ後の書き込み値
     */
    DataType run(final DataType value);
    
}
