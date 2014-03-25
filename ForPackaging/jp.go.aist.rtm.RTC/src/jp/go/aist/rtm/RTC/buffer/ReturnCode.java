package jp.go.aist.rtm.RTC.buffer;


    /**
     * {@.ja BufferStatus リターンコード}
     * {@.en iBufferStatus return codes}
     *
     * <p>
     * {@.ja バッファ関連のクラスで共通のリターンコード
     * <ul>
     * <li> BUFFER_OK:            正常終了
     * <li> BUFFER_ERROR:         バッファエラー
     * <li> BUFFER_FULL:          バッファフル
     * <li> BUFFER_EMPTY:         バッファエンプティ
     * <li> NOT_SUPPORTED:        未サポート機能
     * <li> TIMEOUT:              タイムアウト
     * <li> PRECONDITION_NOT_MET: 事前条件を満たしていない
     * </ul>}
     * {@.en Common return codes for buffer classes.
     * <ul>
     * <li> BUFFER_OK:            Normal return
     * <li> BUFFER_ERROR:         Buffer error
     * <li> BUFFER_FULL:          Buffer full
     * <li> BUFFER_EMPTY:         Buffer empty
     * <li> NOT_SUPPORTED:        Not supported function
     * <li> TIMEOUT:              Timeout
     * <li> PRECONDITION_NOT_MET: Precodition not met
     * </ul>}
     *
     */
public enum ReturnCode {
    BUFFER_OK,
    BUFFER_ERROR,
    BUFFER_FULL,
    BUFFER_EMPTY,
    NOT_SUPPORTED,
    TIMEOUT,
    PRECONDITION_NOT_MET
};

