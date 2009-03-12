package jp.go.aist.rtm.RTC.port;

import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.buffer.RingBuffer;
import jp.go.aist.rtm.RTC.util.DataRef;

/**
 * <p>入力ポートのためのベース実装クラスです。
 * 外部から送信されてきたデータは順次、コンストラクタで指定されたバッファに格納されます。
 * バッファ内のデータはフラグによって未読／既読状態が管理され、isNew(), isEmpty()などの
 * メソッドによってハンドリングできます。</p>
 * 
 * @param <DataType> データ型を指定します。
 */
public class InPort<DataType> implements BufferBase<DataType> {

    private static final long TIMEOUT_TICK_USEC = 10;
    private static final long TIMEOUT_TICK_MSEC_PART = TIMEOUT_TICK_USEC / 1000;
    private static final int TIMEOUT_TICK_NSEC_PART = ((int) (TIMEOUT_TICK_USEC % 1000)) * 1000;

    /**
     * <p>コンストラクタです。</p>
     *
     * @param superClass ポートに割り当てるバッファ
     * @param name ポート名称
     * @param value このポートにバインドされるDataType型の変数
     * @param read_block データ読み込み時に未読データがない場合に、データ受信までブロックする場合はtrue、さもなくばfalse
     * @param write_block データ書き込み時にバッファがフルであった場合に、バッファに空きができるまでブロック場合はtrue、さもなくばfalse
     * @param read_timeout 非ブロック指定の場合の、データ読み取りのタイムアウト時間 (ミリ秒)
     * @param write_timeout 非ブロック指定の場合の、データ書き込みのタイムアウト時間 (ミリ秒)
     */
    public InPort(BufferBase<DataType> superClass,
            final String name, DataRef<DataType> value,
            boolean read_block, boolean write_block,
            long read_timeout, long write_timeout) {
        
        this.m_superClass = superClass;
        this.m_name = name;
        this.m_value = value;
        this.m_readBlock = read_block;
        this.m_writeBlock = write_block;
        this.m_readTimeout = read_timeout;
        this.m_writeTimeout = write_timeout;
        
        this.m_OnWrite = null;
        this.m_OnWriteConvert = null;
        this.m_OnRead = null;
        this.m_OnReadConvert = null;
        this.m_OnOverflow = null;
        this.m_OnUnderflow = null;
    }
    
    /**
     * <p>コンストラクタです。デフォルトの設定でバッファが生成され割り当てられます。
     * また、読み取り・書き込みともに非ブロックモードとなり、タイムアウト時間は0で設定されます。</p>
     * 
     * @param name ポート名称
     * @param value このポートにバインドされるDataType型の変数
     */
    public InPort(final String name, DataRef<DataType> value) {
        this(new RingBuffer<DataType>(64), name, value);
    }
    
    /**
     * <p>コンストラクタです。
     * 読み取り・書き込みともに非ブロックモードとなり、タイムアウト時間は0で設定されます。</p>
     *
     * @param superClass ポートに割り当てるバッファ
     * @param name ポート名称
     * @param value このポートにバインドされるDataType型の変数
     */
    public InPort(BufferBase<DataType> superClass, final String name, DataRef<DataType> value) {
        this(superClass, name, value, false, false, 0, 0);
    }
    
    /**
     * <p>ポート名称を取得します。<p>
     * 
     * @return ポート名称
     */
    public String name() {
        return this.m_name;
    }
    
    /**
     * <p>ポートに値を書き込みます。</p>
     *
     * <ul>
     * <li>コールバックインタフェースOnWriteがセットされている場合は、
     * ポートが保持するバッファにデータが書き込まれる前にOnWrite#run()が呼びだされます。</li>
     * <li>ポートが保持するバッファがオーバーフローを検出できるバッファであり、
     * かつ、書き込む際にバッファがオーバーフローを検出した場合は、
     * コールバックインタフェースOnOverflowが呼び出されます。</li>
     * <li>コールバックインタフェースOnWriteConvertがセットされている場合は、
     * バッファ書き込み時に、OnWriteConvert#operator()の戻り値がバッファに書き込まれます。</li>
     * <li>setWriteTimeout()により書き込み時のタイムアウトが設定されている場合は、
     * タイムアウト時間だけバッファフル状態が解除するのを待ち、
     * OnOverflowがセットされていれば、これを呼び出して戻ります。</li>
     * </ul>
     * 
     * @param value 書き込むデータ
     * @return 書き込みに成功した場合はtrueを、さもなくばfalseを返します。
     */
    public boolean write(final DataType value) {
        
        if (this.m_OnWrite != null) {
            this.m_OnWrite.run(value);
        }

        long timeout = this.m_writeTimeout; // [usec]
        long tm_pre = System.nanoTime(); // [nsec]
        
        // blocking and timeout wait
        while (this.m_writeBlock && this.isFull()) {

            if (this.m_writeTimeout < 0) {
                try {
                    Thread.sleep(TIMEOUT_TICK_MSEC_PART, TIMEOUT_TICK_NSEC_PART);
                } catch (InterruptedException e) {
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
            } catch (InterruptedException e) {
            }
        }

        if (isFull() ) {
            if( this.m_OnOverflow != null ) {
                this.m_OnOverflow.run(value);
            }
            return false;
        }

        if (this.m_OnWriteConvert == null) {
            put(value);
            
        } else {
            put(this.m_OnWriteConvert.run(value));
        }
        
        return true;
    }
    
    /**
     * <p>ポートからデータを読み出します。</p>
     *
     * <ul>
     * <li>コールバックインタフェースOnReadがセットされている場合は、
     * ポートが保持するバッファからデータを読み出す前にOnReadが呼ばれます。</li>
     * <li>ポートが保持するバッファがアンダーフローを検出できるバッファで、
     * かつ、読み出す際にバッファがアンダーフローを検出した場合は、
     * コールバックインタフェースOnUnderflowが呼び出されます。</li>
     * <li>コールバックインタフェースOnReadConvertがセットされている場合は、
     * バッファからのデータ読み取り時に、OnReadConvert#operator()が呼び出され、
     * その戻り値がread()の戻り値となります。</li>
     * <li>setReadTimeout()により読み出し時のタイムアウトが設定されている場合は、
     * バッファアンダーフロー状態が解除されるまでタイムアウト時間だけ待ち、
     * OnUnderflowがセットされていれば、これを呼び出して戻ります。</li>
     * </ul>
     * 
     * @return 読み出したデータ
     */
    public DataType read() {
        
        if (this.m_OnRead != null) {
            this.m_OnRead.run();
        }

        long timeout = this.m_readTimeout * 1000; // [usec] --> [nsec]

        long tm_cur;
        long tm_pre = System.nanoTime();

        // blocking and timeout wait
        while (this.m_readBlock && this.isEmpty()) {
            
            if (this.m_readTimeout < 0) {
                try {
                    Thread.sleep(TIMEOUT_TICK_MSEC_PART, TIMEOUT_TICK_NSEC_PART);
                    
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            // timeout wait
            tm_cur = System.nanoTime();
            long tm_diff = tm_cur - tm_pre;
            
            timeout -= tm_diff;
            if (timeout < 0) {
                break;
            }
            
            tm_pre = tm_cur;
            try {
                Thread.sleep(TIMEOUT_TICK_MSEC_PART, TIMEOUT_TICK_NSEC_PART);
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (isEmpty() && this.m_OnUnderflow != null) {
            this.m_value.v = this.m_OnUnderflow.run();
            return this.m_value.v;
        }
        
        if (this.m_OnReadConvert == null) {
            this.m_value.v = get();
            return this.m_value.v;
        }
        this.m_value.v = this.m_OnReadConvert.run(get());
        return this.m_value.v;
    }
    
    /**
     * <p>当該ポートに割り当てられているバッファを、指定されたデータで埋め尽くします。</p>
     */
    public void init(DataType value) {
        // 何もしない
    }
    
    /**
     * <p>バインドされたDataType型のデータに、ポート内バッファの最新値を読み込みます。<br />
     * ただし、あらかじめコンストラクタで、DataType型の変数がバインドされていなければなりません。<br />
     */
    public void update() {
        
        try {
            this.m_value.v = get();
            
        } catch (Exception e) {
            if (this.m_OnUnderflow != null) {
                this.m_OnUnderflow.run();
            }
        }
    }
    
    /**
     * <p>ポートが持つバッファにデータが書き込まれる直前に呼び出される
     * コールバックインタフェースを設定します。</p>
     * 
     * @param onWrite OnWrite&lt;DataType&gt;インタフェースを持つオブジェクト
     */
    public void setOnWrite(OnWrite<DataType> onWrite) {
        this.m_OnWrite = onWrite;
    }
    
    /**
     * <p>ポートが持つバッファにデータが書き込まれる際に呼び出される
     * コールバックインタフェースを設定します。<br />
     * コールバックインタフェースの戻り値がバッファに書き込まれます。</p>
     * 
     * @param onWriteConvert OnWriteConvert&lt;DataType&gt;インタフェースを持つオブジェクト
     */
    public void setOnWriteConvert(OnWriteConvert<DataType> onWriteConvert) {
        this.m_OnWriteConvert = onWriteConvert;
    }
    
    /**
     * <p>ポートが持つバッファからデータが読み込まれる直前に呼び出される
     * コールバックインタフェースを設定します。</p>
     * 
     * @param onRead OnRead&lt;DataType&gt;インタフェースを持つオブジェクト
     */
    public void setOnRead(OnRead<DataType> onRead) {
        this.m_OnRead = onRead;
    }
    
    /**
     * <p>ポートが持つバッファからデータが読み出される際に呼び出される
     * コールバックインタフェースを設定します。<br />
     * コールバックインタフェースの戻り値がread()メソッドの読み出し結果として取得されます。</p>
     * 
     * @param onReadConvert OnReadConvert&lt;DataType&gt;インタフェースを持つオブジェクト
     */
    public void setOnReadConvert(OnReadConvert<DataType> onReadConvert) {
        this.m_OnReadConvert = onReadConvert;
    }
    
    /**
     * <p>データ書き込み時に、バッファオーバーフロー検出の際に呼び出される
     * コールバックインタフェースを設定します。</p>
     * 
     * @param onOverflow OnOverflow&lt;DataType&gt;インタフェースを持つオブジェクト
     */
    public void setOnOverflow(OnOverflow<DataType> onOverflow) {
        this.m_OnOverflow = onOverflow;
    }
    
    /**
     * <p>データ読み込みときに、バッファアンダーフロー検出の際に呼び出される
     * コールバックインタフェースを設定します。</p>
     * 
     * @param onUnderflow OnUnderflow&lt;DataType&gt;インタフェースを持つオブジェクト
     */
    public void setOnUnderflow(OnUnderflow<DataType> onUnderflow) {
        this.m_OnUnderflow = onUnderflow;
    }
    
    /**
     * <p>ポート内のバッファ長を取得します。</p>
     * 
     * @return バッファ長
     */
    public int length() {
        return this.m_superClass.length();
    }

    /**
     * <p>データを読み取ります。</p>
     * 
     * @param valueRef 読み取ったデータを受け取るためのDataRefオブジェクト
     */
    public boolean read(DataRef<DataType> valueRef) {
        return this.m_superClass.read(valueRef);
    }

    /**
     * <p>バッファフルかどうかを取得します。</p>
     * 
     * @return バッファフルの場合はtrueを、さもなくばfalseを返します。
     */
    public boolean isFull() {
        return this.m_superClass.isFull();
    }

    /**
     * <p>バッファが空である、つまり読み取れるデータがないかどうかを取得します。</p>
     * 
     * @return バッファが空の場合はtrueを、さもなくばfalseを返します。
     */
    public boolean isEmpty() {
        return this.m_superClass.isEmpty();
    }

    /**
     * <p>データを書き込みます。</p>
     * 
     * @param data 書き込むデータ
     */
    public void put(final DataType data) {
        this.m_superClass.put(data);
    }

    /**
     * <p>データを読み取ります。</p>
     * 
     * @return 読み取ったデータ
     */
    public DataType get() {
        return this.m_superClass.get();
    }
    
    private BufferBase<DataType> m_superClass;
    private String m_name;
    private DataRef<DataType> m_value;
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

    public boolean isNew() {
        return m_superClass.isNew();
    }
    
}
