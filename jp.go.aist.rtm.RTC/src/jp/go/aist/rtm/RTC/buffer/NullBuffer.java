package jp.go.aist.rtm.RTC.buffer;

import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.Properties;

/**
 * <p>バッファ長1固定のバッファ実装です。</p>
 *
 * @param <DataType> バッファ内のデータ型を指定します。
 */
public class NullBuffer<DataType> implements BufferBase<DataType> {

    /**
     * <p>コンストラクタです。</p>
     * 
     * @param size バッファ長
     */
    public NullBuffer(long size) {
        m_length = 1;
    }
    
    /**
     * <p>デフォルトコンストラクタです。</p>
     */
    public NullBuffer() {
        this(1);
    }
    
    /**
     * <p>バッファ長を取得します。</p>
     * 
     * @return バッファ長
     */
    public int length() {
        return this.m_length;
    }

    /**
     * <p>バッファにデータを書き込みます。</p>
     * 
     * @param value 書き込むデータ
     * @return 書き込みに成功した場合はtrueを、さもなくばfalseを返します。
     */
    public ReturnCode write(final DataType value) {
        this.m_data = value;
        return ReturnCode.BUFFER_OK;
    }

    /**
     * <p>バッファからデータを読み込みます。</p>
     * 
     * @param valueRef 読み込んだデータを受け取るためのDataRefオブジェクト
     * @return 読み込みに成功した場合はtrueを、さもなくばfalseを返します。
     */
    public boolean read(DataRef<DataType> valueRef) {
        valueRef.v = this.m_data;
        return true;
    }

    /**
     * <p>バッファがフルかどうか判定します。</p>
     * 
     * @return バッファがフルならばtrue、さもなくばfalseを返します。
     */
    public boolean isFull() {
        return false;
    }

    /**
     * <p>バッファが空かどうか判定します。</p>
     * 
     * @return バッファが空ならばtrue、さもなくばfalseを返します。
     */
    public boolean isEmpty() {
        return false;
    }

    /**
     * <p>バッファにデータを書き込みます。</p>
     * 
     * @param data 書き込むデータ
     */
    public ReturnCode put(final DataType data) {
        this.m_data = data;
    }

    /**
     * <p>バッファからデータを読み込みます。</p>
     * 
     * @return 読み込んだデータ
     */
    public DataType get() {
        return this.m_data;
    }

    /**
     * <p>バッファ中に、まだ読み取られていないデータがあるかどうか判定します。</p>
     * 
     * @return 読み取られていないデータがあればtrueを、さもなくばfalseを返します。
     */
    public boolean isNew() {
        return false;
    }

    /**
     * <p> get </p>
     * <p> This function is not implemented. </p>
     * @param DataType
     * @return ReturnCode
     */
    public ReturnCode get(DataType value) {
        return ReturnCode.BUFFER_OK;

    }
    /**
     * <p> init </p>
     * <p> This function is not implemented. </p>
     *
     * @param prop
     */
    public void init(final Properties prop) {
    }
    /**
     * <p> reset </p>
     * <p> This function is not implemented. </p>
     *
     * @return ReturnCode
     */
    public ReturnCode reset() {
        return ReturnCode.BUFFER_OK;
    }
    /**
     * <p> wptr </p>
     * <p> This function is not implemented. </p>
     * @param  n 
     * @return DataType
     */
    public DataType wptr(int n) {
        return m_data;
    }
    /**
     * <p> wptr </p>
     * <p> This function is not implemented. </p>
     * @return DataType
     */
    public DataType wptr() {
        return m_data;
    }
    /**
     * <p> advanceWptr </p>
     * <p> This function is not implemented. </p>
     *
     * @param  n
     * @return ReturnCode
     */
    public ReturnCode advanceWptr(int n) {
        return ReturnCode.BUFFER_OK;
    }
    /**
     * <p> advanceWptr </p>
     * <p> This function is not implemented. </p>
     *
     * @return ReturnCode
     */
    public ReturnCode advanceWptr() {
        return ReturnCode.BUFFER_OK;
    }
    /**
     * <p> writable </p>
     * <p> This function is not implemented. </p>
     * @return int 
     */
    public int writable() {
        return 0;
    }
    /**
     * <p> full </p>
     * <p> This function is not implemented. </p>
     *
     * @return boolean 
     */
    public boolean full() {
        return true;
    }
    /**
     * <p> rptr </p>
     * <p> This function is not implemented. </p>
     * @param  n
     * @return DataType 
     */
    public DataType rptr(int n) {
        return m_data;
    }
    /**
     * <p> rptr </p>
     * <p> This function is not implemented. </p>
     * @return DataType 
     */
    public DataType rptr() {
        return m_data;
    }
    /**
     * <p> advanceRptr </p>
     * <p> This function is not implemented. </p>
     * @param  n 
     * @return ReturnCode 
     */
    public ReturnCode advanceRptr(int n)
    {
        return ReturnCode.BUFFER_OK;
    }
    /**
     * <p> advanceRptr </p>
     * <p> This function is not implemented. </p>
     * @param  n 
     * @return ReturnCode 
     */
    public ReturnCode advanceRptr()
    {
        return ReturnCode.BUFFER_OK;
    }
    /**
     * <p> readable </p>
     * <p> This function is not implemented. </p>
     * @return int 
     */
    public int readable() {
        return 0;
    }     
    /**
     * <p> empty </p>
     * <p> This function is not implemented. </p>
     *
     * @return boolean 
     */
    public boolean empty() {
        return true;
    }
    /**
     * <p> length </p>
     * <p> This function is not implemented. </p>
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
