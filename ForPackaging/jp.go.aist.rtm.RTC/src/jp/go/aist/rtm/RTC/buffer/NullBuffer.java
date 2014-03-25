package jp.go.aist.rtm.RTC.buffer;

import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.Properties;

  /**
   * {@.ja ダミーバッファ実装クラス。}
   * {@.en Concrete buffer class for dummy.}
   * 
   * <p>
   * {@.ja バッファ長が１固定のダミーバッファ実装クラス。
   * \<DataType\>としてバッファ内で保持するデータ型を指定する。}
   *
   * {@.en Concrete buffer class for dummy. Buffer length is fixed to 1.
   * The users specify data type to hold it in a buffer as \<DataType\>.}
   *
   * @param <DataType>
   *   {@.ja バッファ内のデータ型を指定する。}
   *   {@.en Data type to hold in a buffer}
   */
//public class NullBuffer<DataType> implements BufferBase<DataType> {
public class NullBuffer<DataType> {

    /**
     * {@.ja コンストラクタ。}
     * {@.en Constructer.}
     * <p>
     * {@.ja バッファ長を1(固定)で初期化する。}
     * {@.en Initialize buffer length to always 1.}
     * 
     * @param size 
     *   {@.ja バッファ長}
     *   {@.en Buffer length}
     */
    public NullBuffer(long size) {
        m_length = 1;
    }
    
    /**
     * {@.ja デフォルトコンストラクタ。}
     * {@.en Default Constructer.}
     */
    public NullBuffer() {
        this(1);
    }
    
    /**
     * {@.ja バッファ長(1固定)を取得する。}
     * {@.en Get the buffer length (always 1)}
     * 
     * <p>
     * {@.ja バッファ長を取得する。(常に1を返す。)}
     * {@.en Get the buffer length. (Return always 1.)}
     * 
     * @return 
     *   {@.ja バッファ長(1固定)}
     *   {@.en buffer length(always 1)}
     * 
     */
    public int length() {
        return this.m_length;
    }

    /**
     * {@.ja バッファにデータを書き込む。}
     * {@.en Write data into the buffer}
     * 
     * <p>
     * {@.ja 引数で与えられたデータをバッファに書き込む。}
     * {@.en Write data which were given with an argument into the buffer.}
     * 
     * @param value 
     *   {@.ja 書き込み対象データ}
     *   {@.en Target data to write.}
     *
     * @return 
     *   {@.ja データ書き込み結果(true:書き込み成功，false:書き込み失敗)}
     *   {@.en Result of having written in data (true:Successful, false:Failed)}
     *
     */
    public ReturnCode write(final DataType value) {
        this.m_data = value;
        return ReturnCode.BUFFER_OK;
    }

    /**
     * {@.ja バッファからデータを読み出す。}
     * {@.en Read data from the buffer}
     * 
     * <p>
     * {@.ja バッファに格納されたデータを読み出す。}
     * {@.en Read data stored in the buffer.}
     * 
     * @param valueRef 
     *   {@.ja 読み込んだデータを受け取るためのDataRefオブジェクト}
     *   {@.en Object of DataRef type to receive read data}
     *
     * @return 
     *   {@.ja データ読み出し結果(true:読み出し成功，false:読み出し失敗)}
     *   {@.en Result of having read (true:Successful, false:Failed)}
     *
     */
    public boolean read(DataRef<DataType> valueRef) {
        valueRef.v = this.m_data;
        return true;
    }

    /**
     * {@.ja バッファがフルかどうか判定する。}
     * {@.en Check on whether the buffer is full.}
     * 
     * <p>
     * {@.ja バッファfullをチェックする。(常にfalseを返す。)}
     * {@.en Check on whether the buffer is full. (Always false.)}
     *
     * @return 
     *   {@.ja fullチェック結果(常にfalse)}
     *   {@.en Always false.}
     *
     */
    public boolean isFull() {
        return false;
    }

    /**
     * {@.ja バッファが空かどうか判定する。}
     * {@.en Check on whether the buffer is empty.}
     * 
     * <p>
     * {@.ja バッファemptyをチェックする。(常にfalseを返す。)}
     * {@.en Check on whether the buffer is empty. (Always false.)}
     *
     * @return 
     *   {@.ja emptyチェック結果(常にfalse)}
     *   {@.en Always false.}
     *
     */
    public boolean isEmpty() {
        return false;
    }

    /**
     * {@.ja バッファにデータを書き込む。}
     * {@.en Store data into the buffer}
     * 
     * <p>
     * {@.ja 引数で与えられたデータをバッファに格納する。}
     * {@.en Store data which were given with an argument into the buffer.}
     * 
     * @param data 
     *   {@.ja 書き込むデータ}
     *   {@.en Target data to store.}
     */
    public ReturnCode put(final DataType data) {
        this.m_data = data;
        return ReturnCode.BUFFER_OK;
    }

    /**
     * {@.ja バッファからデータを読み込む。}
     * {@.en Get data from the buffer}
     *
     * <p>
     * {@.ja バッファに格納されたデータを取得する。}
     * {@.en Get data from the buffer.}
     *
     * @return 
     *   {@.ja 取得データ}
     *   {@.en Data got from buffer.}
     *
     */
    public DataType get() {
        return this.m_data;
    }

    /**
     * {@.ja 最新データが存在するか確認する。}
     * {@.en Check whether the data is newest}
     * <p>
     * {@.ja バッファ中に、まだ読み取られていないデータがあるかどうか
     * 判定する。}
     * {@.en Check whether the data stored at a current buffer position 
     * is newest.}
     * 
     * @return 
     *   {@.ja true 未読の最新データが存在する
     *         false 未接続またはバッファにデータが存在しない。}
     *   {@.en Newest data check result
     *         ( true:Newest data. Data has not been readout yet.
     *          false:Past data．Data has already been readout.)}
     */
    public boolean isNew() {
        return false;
    }

    /**
     * This function is not implemented. 
     * @param value
     * @return ReturnCode
     */
    public ReturnCode get(DataType value) {
        return ReturnCode.BUFFER_OK;

    }
    /**
     * This function is not implemented. 
     *
     * @param prop
     */
    public void init(final Properties prop) {
    }
    /**
     * This function is not implemented. 
     *
     * @return ReturnCode
     */
    public ReturnCode reset() {
        return ReturnCode.BUFFER_OK;
    }
    /**
     * This function is not implemented. 
     * @param  n 
     * @return DataType
     */
    public DataType wptr(int n) {
        return m_data;
    }
    /**
     * This function is not implemented. 
     * @return DataType
     */
    public DataType wptr() {
        return m_data;
    }
    /**
     * This function is not implemented. 
     *
     * @param  n
     * @return ReturnCode
     */
    public ReturnCode advanceWptr(int n) {
        return ReturnCode.BUFFER_OK;
    }
    /**
     * This function is not implemented.
     *
     * @return ReturnCode
     */
    public ReturnCode advanceWptr() {
        return ReturnCode.BUFFER_OK;
    }
    /**
     * This function is not implemented. 
     * @return int 
     */
    public int writable() {
        return 0;
    }
    /**
     * This function is not implemented. 
     *
     * @return boolean 
     */
    public boolean full() {
        return true;
    }
    /**
     * This function is not implemented. 
     * @param  n
     * @return DataType 
     */
    public DataType rptr(int n) {
        return m_data;
    }
    /**
     * This function is not implemented. 
     * @return DataType 
     */
    public DataType rptr() {
        return m_data;
    }
    /**
     *  This function is not implemented. 
     * @param  n 
     * @return ReturnCode 
     */
    public ReturnCode advanceRptr(int n)
    {
        return ReturnCode.BUFFER_OK;
    }
    /**
     * This function is not implemented. 
     * @return ReturnCode 
     */
    public ReturnCode advanceRptr()
    {
        return ReturnCode.BUFFER_OK;
    }
    /**
     * This function is not implemented. 
     * @return int 
     */
    public int readable() {
        return 0;
    }     
    /**
     * This function is not implemented. 
     *
     * @return boolean 
     */
    public boolean empty() {
        return true;
    }
    /**
     * This function is not implemented. 
     *
     * @param  n
     * @return ReturnCode 
     */
    public ReturnCode length(int n) {
        return ReturnCode.BUFFER_OK; //BUFFER_OK;
    }

    private DataType m_data;
    private int m_length;
}
