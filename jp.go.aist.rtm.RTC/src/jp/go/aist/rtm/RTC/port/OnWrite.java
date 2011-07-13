package jp.go.aist.rtm.RTC.port;

/**
 * {@.ja InPort/OutPortのバッファにデータが書き込まれる直前に呼び出される
 * コールバックインタフェース}
 * {@.en Callback interface called immediately before data is written in buffer
 * of InPort/OutPort}
 */
public interface OnWrite<DataType> {

    /**
     * {@.ja コールバックメソッド}
     * {@.en Callback method}
     * 
     * @param value 
     *   {@.ja バッファに書き込まれるデータ}
     *   {@.en Data written in buffer.}
     */
    void run(final DataType value);
    
}
