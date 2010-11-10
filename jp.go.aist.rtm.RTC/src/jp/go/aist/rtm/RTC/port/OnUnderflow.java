package jp.go.aist.rtm.RTC.port;

/**
 * {@.ja InPort/OutPortのバッファからデータが読み出される際、
 * 読み出すべきデータがない場合に
 * 呼び出されるコールバックインタフェース}
 * {@.en Callback interface called when there is no data when data is read from
 * buffer of InPort/OutPort}
 * <p>
 * {@.ja 本コールバックの戻り値が読み出しの代替データとして
 * 使用されるため、データアンダーフロー時のデフォルト読み出し値を与える
 * フィルタとして機能する。}
 */
public interface OnUnderflow<DataType> {
   
    /**
     * {@.ja コールバックメソッド}
     * {@.en Callbakc method}
     * 
     * @return 
     *   {@.ja 代替となる読み出しデータ}
     *   {@.en Data of substitution}
     */
    DataType run();
    
}
