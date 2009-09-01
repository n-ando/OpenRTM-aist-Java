package jp.go.aist.rtm.RTC.port;

import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.buffer.RingBuffer;
import jp.go.aist.rtm.RTC.util.DataRef;

/**
 * <p>出力ポートの実装です。さらに上位の出力ポートのベース実装として利用されます。</p>
 *
 * @param <DataType> データ型を指定します。
 */
public class OutPort<DataType> extends OutPortBase {

    /**
     * <p>コンストラクタです。内部的にバッファが生成されて割り当てられます。</p>
     * 
     * @param name ポート名
     * @param valueRef 本ポートにバインドするデータ変数を内包するDataRefオブジェクト
     */
    public OutPort(final String name, DataRef<DataType> valueRef) {
        this(new RingBuffer<DataType>(8), name, valueRef);
    }
    
    /**
     * <p>コンストラクタです。指定されたデータ長で内部的にバッファが生成されて割り当てられます。</p>
     * 
     * @param name ポート名
     * @param valueRef 本ポートにバインドするデータ変数を内包するDataRefオブジェクト
     * @param length バッファ長
     */
    public OutPort(final String name, DataRef<DataType> valueRef, int length) {
        this(new RingBuffer<DataType>(length), name, valueRef);
    }
    
    /**
     * <p>コンストラクタです。指定されたバッファを割り当てます。</p>
     * 
     * @param buffer 割り当てるバッファ
     * @param name ポート名
     * @param valueRef 本ポートにバインドするデータ変数を内包するDataRefオブジェクト
     */
    public OutPort(BufferBase<DataType> buffer,
            final String name, DataRef<DataType> valueRef) {
        
        super(name);
        
        this.m_buffer = buffer;
        this.m_value = valueRef;
        this.m_timeoutTick = 1000; // [usec]
        this.m_readBlock = false;
        this.m_readTimeout = 0; // [usec]
        this.m_writeBlock = false;
        this.m_writeTimeout = 0; // [usec]
        
        this.m_OnWrite = null;
        this.m_OnWriteConvert = null;
        this.m_OnRead = null;
        this.m_OnReadConvert = null;
        this.m_OnOverflow = null;
        this.m_OnUnderflow = null;
    }
    
    /**
     * <p>データを書き込みます。<p>
     * 
     * @param value 書き込むデータ
     * @return データを書き込めた場合はtrueを、さもなくばfalseを返します。
     */
    public boolean write(final DataType value) {
/*        
        if (this.m_OnWrite != null) {
            this.m_OnWrite.run(value);
        }
        
        long timeout = this.m_writeTimeout; // [usec]
        long tm_pre = System.nanoTime(); // [nsec]
        
        // blocking and timeout wait
        long TIMEOUT_TICK_MSEC_PART = this.m_timeoutTick / 1000;
        int TIMEOUT_TICK_NSEC_PART = ((int) (this.m_timeoutTick % 1000)) * 1000;
        long count = 0;
        while (this.m_writeBlock && isFull()) {
            
            if (this.m_writeTimeout < 0) {
                try {
                    Thread.sleep(TIMEOUT_TICK_MSEC_PART, TIMEOUT_TICK_NSEC_PART);
                    
                } catch (InterruptedException ignored) {
                    ignored.printStackTrace();
                }
                
                continue;
            }

            // timeout wait
            long tm_cur = System.nanoTime(); // [nsec]
            long tm_diff = tm_cur - tm_pre; // [nsec]
            
            timeout -= tm_diff / 1000; // [usec]
            if (timeout < 0) {
                break;
            }

            tm_pre = tm_cur;
            try {
                Thread.sleep(TIMEOUT_TICK_MSEC_PART, TIMEOUT_TICK_NSEC_PART);
                
            } catch (InterruptedException ignored) {
                ignored.printStackTrace();
            }
            ++count;
        }

        if (isFull()) {
            if (this.m_OnOverflow != null) {
                this.m_OnOverflow.run(value);
            }
            return false;
        }
    
        if (this.m_OnWriteConvert == null) {
            put(value);
        }
        else {
            put(this.m_OnWriteConvert.run(value));
        }
        
        update();
*/
        return true;
    }
    
    /**
     * <p>データを書き込みます。本ポートにバインドされているデータ変数の値が書き込まれます。</p>
     * 
     * @return データを書き込めた場合はtrueを、さもなくばfalseを返します。
     */
    public boolean write() {
//        return this.write(m_value.v);
        return true;
    }
    
    
    /**
     * <p>データ読み出し時における、ブロック/非ブロックモードを指定します。<br />
     * ブロックモードを指定した場合は、読み出せるデータを受信するかタイムアウトになるまで、
     * read()メソッドの呼び出しがブロックされます。</p>
     * 
     * @param block ブロックモードを指定する場合はtrue、さもなくばfalse
     */
    public void setReadBlock(boolean block) {
        this.m_readBlock = block;
    }
    
    /**
     * <p>データ書き込み時における、ブロック/非ブロックモードを指定します。<br />
     * ブロックモードを指定した場合は、バッファに書き込む余地ができるかタイムアウトになるまで、
     * write()メソッドの呼び出しがブロックされます。</p>
     * 
     * @param block ブロックモードを指定する場合はtrue、さもなくばfalse
     */
    public void setWriteBlock(boolean block) {
        this.m_writeBlock = block;
    }

    /**
     * <p>読み出しがブロックモード指定されている場合のタイムアウト時間を設定します。</p>
     * 
     * @param timeout タイムアウト時間 [usec]
     */
    public void setReadTimeout(long timeout) {
        this.m_readTimeout = timeout;
    }
    
    /**
     * <p>書き込みがブロックモード指定されている場合のタイムアウト時間を設定します。</p>
     * 
     * @param timeout タイムアウト時間 [usec]
     */
    public void setWriteTimeout(long timeout) {
        this.m_writeTimeout = timeout;
    }
    
    /**
     * <p>データ書き込み直前に呼び出されるコールバックインタフェースを設定します。</p>
     * 
     * @param onWrite OnWriteコールバックインタフェースを持つオブジェクト
     */
    public void setOnWrite(OnWrite<DataType> onWrite) {
        this.m_OnWrite = onWrite;
    }
    
    /**
     * <p>データ書き込み時に呼び出されるコールバックインタフェースを設定します。</p>
     * 
     * <p>設定されたコールバックの戻り値のデータが書き込まれます。
     * これにより、書き込みデータのフィルタリングを行うことができます。</p>
     * 
     * @param onWriteConvert OutWriteConvertコールバックインタフェースを持つオブジェクト
     */
    public void setOnWriteConvert(OnWriteConvert<DataType> onWriteConvert) {
        this.m_OnWriteConvert = onWriteConvert;
    }
    
    /**
     * <p>バッファフルによりデータ書き込みできない場合に呼び出されるコールバックインタフェースを設定します。</p>
     * 
     * @param onOverflow OutOverflowコールバックインタフェースを持つオブジェクト
     */
    public void setOnOverflow(OnOverflow<DataType> onOverflow) {
        this.m_OnOverflow = onOverflow;
    }
    
    /**
     * <p>データ読み出しの直前に呼び出されるコールバックインタフェースを設定します。</p>
     * 
     * @param onRead OutReadコールバックインタフェースを持つオブジェクト
     */
    public void setOnRead(OnRead<DataType> onRead) {
        this.m_OnRead = onRead;
    }
    
    /**
     * <p>データ読み出し時に呼び出されるコールバックインタフェースを設定します。</p>
     * 
     * <p>設定されたコールバックの戻りデータ値が読み出されます。
     * これにより、読み出しデータをフィルタリングすることができます。</p>
     * 
     * @param onReadConvert OutReadConvertコールバックインタフェースを持つオブジェクト
     */
    public void setOnReadConvert(OnReadConvert<DataType> onReadConvert) {
        this.m_OnReadConvert = onReadConvert;
    }
    
    /**
     * <p>バッファ空により読み取れるデータがない場合に呼び出されるコールバックインタフェースを設定します。</p>
     * 
     * @param onUnderflow OutUnderflowコールバックインタフェースを持つオブジェクト
     */
    public void setOnUnderflow(OnUnderflow<DataType> onUnderflow) {
        this.m_OnUnderflow = onUnderflow;
    }
    
    /**
     * <p>内部に割り当てられているバッファ長を取得します。</p>
     * 
     * @return バッファ長
     */
/*
    public int length() {
        return this.m_buffer.length();
    }
*/
    
    /**
     * <p>バッファフルか否かを判定します。</p>
     * 
     * @return バッファフルの場合はtrueを、さもなくばfalseを返します。
     */
/*
    public boolean isFull() {
        return this.m_buffer.isFull();
    }
*/
    
    /**
     * <p>バッファが空か否かを判定します。</p>
     * 
     * @return バッファが空の場合はtrueを、さもなくばfalseを返します。
     */
/*
    public boolean isEmpty() {
        return this.m_buffer.isEmpty();
    }
*/

    /**
     * <p>内部に割り当てられているバッファに、データを直接書き込みます。<br />
     * バッファ状態のハンドリング、ブロッキング、タイムアウト、コールバック呼び出しなどは行われません。</p>
     * 
     * @param data 書き込むデータ
     */
/*
    public void put(DataType data) {
        this.m_buffer.put(data);
    }
*/
    
    /**
     * <p>内部に割り当てられているバッファから、データを直接読み出します。<br />
     * バッファ状態のハンドリング、ブロッキング、タイムアウト、コールバック呼び出しなどは行われません。</p>
     * 
     * @return 読み出したデータ
     */
/*
    public DataType get() {
        return this.m_buffer.get();
    }
*/
    
    /**
     * <p>バッファ内に、まだ読み出されていないデータが存在するかどうかを判定します。</p>
     * 
     * @return まだ読み出されていないデータがあればtrueを、さもなくばfalseを返します。
     */
/*
    public boolean isNew() {
        return m_buffer.isNew();
    }
*/

    private BufferBase<DataType> m_buffer;
    private DataRef<DataType> m_value;
    private long m_timeoutTick;
    private boolean m_readBlock;
    private long m_readTimeout;
    private boolean m_writeBlock;
    private long m_writeTimeout;
    
    private OnWrite<DataType> m_OnWrite;
    private OnWriteConvert<DataType> m_OnWriteConvert;
    private OnRead<DataType> m_OnRead;
    private OnReadConvert<DataType> m_OnReadConvert;
    private OnOverflow<DataType> m_OnOverflow;
    private OnUnderflow<DataType> m_OnUnderflow;

}
