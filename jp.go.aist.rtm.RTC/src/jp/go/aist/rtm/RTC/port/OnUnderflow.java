package jp.go.aist.rtm.RTC.port;

/**
 * <p>InPort/OutPortのバッファからデータが読み出される際、読み出すべきデータがない場合に
 * 呼び出されるコールバックインタフェースです。<br />
 * 本コールバックの戻り値が読み出しの代替データとして
 * 使用されるため、データアンダーフロー時のデフォルト読み出し値を与えるフィルタとして機能します。</p>
 */
public interface OnUnderflow<DataType> {
   
    /**
     * <p>コールバックメソッドです。</p>
     * 
     * @return 代替となる読み出しデータ 
     */
    DataType run();
    
}
