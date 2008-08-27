package jp.go.aist.rtm.RTC.buffer;

import java.util.Vector;

import jp.go.aist.rtm.RTC.util.DataRef;

/**
 * <p>リング状のバッファを持つバッファ実装です。
 * バッファ全体がデータで埋まった場合、以降のデータは古いデータから順次上書きされていきます。
 * これにより、直近のバッファ長分のデータのみが残ります。</p>
 *
 * @param <DataType> バッファ内のデータ型を指定します。
 */
public class RingBuffer<DataType> implements BufferBase<DataType> {

    /**
     * <p>コンストラクタです。</p>
     * 
     * @param length バッファ長
     */
    public RingBuffer(int length) {
        this.m_length = (length < 2) ? 2 : length;
        this.m_oldPtr = 0;
        this.m_newPtr = (length < 2) ? 1 : length - 1;
        this.m_buffer = new Vector<Data<DataType>>(this.m_length);
        for (int i = 0; i < this.m_length; i++) {
            this.m_buffer.add(new Data<DataType>());
        }
    }

    /**
     * <p>バッファ全体を指定されたデータで埋めます。</p>
     * 
     * @param data バッファ全体に設定されるデータ
     */
    public void init(DataType data) {
        for (int i = 0; i < this.m_length; i++) {
            put(data);
        }
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
        put(value);
        return true;
    }

    /**
     * <p>バッファからデータを読み込みます。</p>
     * 
     * @param valueRef 読み込んだデータを受け取るためのDataRefオブジェクト
     * @return 読み込みに成功した場合はtrueを、さもなくばfalseを返します。
     */
    public boolean read(DataRef<DataType> valueRef) {
        valueRef.v = get();
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
        return !this.isNew();
    }

    /**
     * <p>バッファ中に、まだ読み取られていないデータがあるかどうか判定します。</p>
     * 
     * @return 読み取られていないデータがあればtrueを、さもなくばfalseを返します。
     */
    public boolean isNew() {
        return this.m_buffer.get(this.m_newPtr).isNew();
    }

    /**
     * <p>バッファにデータを書き込みます。</p>
     * 
     * @param data 書き込むデータ
     */
    public void put(final DataType data) {
        this.m_buffer.get(this.m_oldPtr).write(data);
        this.m_newPtr = this.m_oldPtr;
        this.m_oldPtr = (++m_oldPtr) % this.m_length;
    }

    /**
     * <p>バッファからデータを読み込みます。</p>
     * 
     * @return 読み込んだデータ
     */
    public DataType get() {
        return this.m_buffer.get(this.m_newPtr).read();
    }

    class Data<D> {
        
        public Data() {
            this.data = null;
            this.is_new = false;
        }
        
        public void write(final D other) {
            this.data = other;
            this.is_new = true;
        }
        
        public D read() {
            this.is_new = false;
            return this.data;
        }
        
        public boolean isNew() {
            return this.is_new;
        }
        
        private D data;
        private boolean is_new;
    }
    
    private int m_length;
    private int m_oldPtr;
    private int m_newPtr;
    private Vector<Data<DataType>> m_buffer;
}
