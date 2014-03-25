package jp.go.aist.rtm.RTC.port;
    /**
     * {@.ja DataPortStatus リターンコード}
     * {@.en @brief DataPortStatus return codes}
     *
     * <p>
     * {@.ja データポート関連のクラスで共通のリターンコード
     * <ul>
     * <li> PORT_OK:              正常終了
     * <li> PORT_ERROR:           異常終了
     * <li> BUFFER_ERROR:         バッファエラー
     * <li> BUFFER_FULL:          バッファフル
     * <li> BUFFER_EMPTY:         バッファエンプティ
     * <li> BUFFER_TIMEOUT:       バッファタイムアウト
     * <li> SEND_FULL:            データを送ったが相手側がバッファフル状態
     * <li> SEND_TIMEOUT:         データを送ったが相手側がタイムアウトした
     * <li> RECV_EMPTY:           データを受信しようとしたがデータが空状態
     * <li> RECV_TIMEOUT:         データを受信しようとしたがタイムうとした
     * <li> INVALID_ARGS:         不正な引数
     * <li> PRECONDITION_NOT_MET: 事前条件を満たしていない
     * <li> CONNECTION_LOST:      接続が切断された
     * <li> UNKNOWN_ERROR:        不明なエラー
     * </ul>
     * データポートのデータ経路上のエラー発生個所から呼び出し側へエラー
     * 情報を伝えるためにこのエラーコードを使用する。主に、伝送路上のエ
     * ラー、伝送先のエラーなどが考えられるが、各部分の界面で発生するエ
     * ラーを以下に列挙する。
     *
     * (1) Push型
     *  a) InPortConsumer と Publisher/Activity 間で発生するリターンコード
     *     PORT_OK, PORT_ERROR, SEND_FULL, SEND_TIMEOUT, CONNECTION_LOST,
     *     UNKNOWN_ERROR
     *
     *  b) Activity と OutPort の Buffer/Connector 間で発生するリターンコード
     *     PORT_OK, PORT_ERROR, BUFFER_ERROR, BUFFER_FULL, BUFFER_TIMEOUT,
     *     UNKNOWN_ERROR, 
     *
     * (2) Pull型
     *  a) Activity と InPort の間で発生するリターンコード
     *     PORT_OK, PORT_ERROR, RECV_EMPTY, RECV_TIMEOUT, CONNETION_LOST,
     *     UNKNOWN_ERROR
     *
     * 各関数が返すリターンコードは関数ごとのリファレンスを参照のこと。}
     * {@.en Common return codes for data ports related classes.
     *
     * - PORT_OK:              Normal return
     * - PORT_ERROR:           Error return
     * - BUFFER_ERROR:         Buffer error
     * - BUFFER_FULL:          Buffer full
     * - BUFFER_EMPTY:         Buffer empty
     * - BUFFER_TIMEOUT:       Buffer timeout
     * - SEND_FULL:            Buffer full although OutPort tried to send data
     * - SEND_TIMEOUT:         Timeout although OutPort tried to send data
     * - RECV_EMPTY:           Buffer empty although InPort tried to receive
     *                         data
     * - RECV_TIMEOUT:         Timeout although InPort tried to receive data
     * - INVALID_ARGS:         Invalid arguments
     * - PRECONDITION_NOT_MET: Precondition not met
     * - CONNECTION_LOST:      Connection has been lost
     * - UNKNOWN_ERROR:        Unknown error
     *
     * This error codes might be used to propagate error status from
     * the error occurring point to the function caller in the data
     * stream path. It would occur in data-transfer path and data
     * receiver/sender. The errors that occur in the interface of each
     * portion of data port are shown below.
     *
     * (1) Push Type
     *  a) The return codes between InPortConsumer and Publisher/Activity
     *     PORT_OK, PORT_ERROR, SEND_FULL, SEND_TIMEOUT, CONNECTION_LOST,
     *     UNKNOWN_ERROR
     *  b) The return codes between Activity and Buffer/Connector of OutPort
     *     PORT_OK, PORT_ERROR, BUFFER_ERROR, BUFFER_FULL, BUFFER_TIMEOUT,
     *     UNKNOWN_ERROR, 
     *
     * (2) Pull Type
     *  a) The return codes between Activity and InPort
     *     PORT_OK, PORT_ERROR, RECV_EMPTY, RECV_TIMEOUT, CONNETION_LOST,
     *     UNKNOWN_ERROR
     *
     * See function references for detailed return codes for each function.}
     * </p>
     */

public enum ReturnCode {
    PORT_OK,
    PORT_ERROR,
    BUFFER_ERROR,
    BUFFER_FULL,
    BUFFER_EMPTY,
    BUFFER_TIMEOUT,
    SEND_FULL,
    SEND_TIMEOUT,
    RECV_EMPTY,
    RECV_TIMEOUT,
    INVALID_ARGS,
    PRECONDITION_NOT_MET,
    CONNECTION_LOST,
    UNKNOWN_ERROR,
}


