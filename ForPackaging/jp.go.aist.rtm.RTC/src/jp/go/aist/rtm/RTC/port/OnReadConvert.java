package jp.go.aist.rtm.RTC.port;

/**
 * <p>InPort/OutPortのバッファからデータが読み出される際に呼び出されるコールバックインタフェースです。<br />
 * 本コールバックの戻り値が読み出し結果として使用されるため、読み出し値のフィルタとして機能します。</p>
 */
public interface OnReadConvert<DataType> {
    
    /**
     * <p>コールバックメソッドです。</p>
     * 
     * @param value フィルタ前の読み出しデータ
     * @return フィルタ後の読み出しデータ
     */
    DataType run(final DataType value);
    
}
