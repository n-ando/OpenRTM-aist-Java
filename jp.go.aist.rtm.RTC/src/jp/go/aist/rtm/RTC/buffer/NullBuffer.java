package jp.go.aist.rtm.RTC.buffer;

import jp.go.aist.rtm.RTC.util.DataRef;

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
    public boolean write(final DataType value) {
        
        this.m_data = value;
        return true;
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
    public void put(final DataType data) {
        
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

    private DataType m_data;
    private int m_length;
}
