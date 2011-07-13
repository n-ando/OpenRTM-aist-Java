package jp.go.aist.rtm.RTC.buffer;

import java.util.Vector;

import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.StringUtil;
import jp.go.aist.rtm.RTC.util.TimeValue;

  /**
   * {@.ja リングバッファ実装クラス。}
   * {@.en Ring buffer implementation class}
   * 
   * <p>
   * [@.ja 指定した長さのリング状バッファを持つバッファ実装クラス。
   * バッファ全体にデータが格納された場合、以降のデータは古いデータから
   * 順次上書きされる。
   * 従って、バッファ内には直近のバッファ長分のデータのみ保持される。
   *
   * 注)現在の実装では、一番最後に格納したデータのみバッファから読み出し可能}
   *
   * {@.en This is the buffer implementation class with ring shaped buffer of
   * specified length.
   * If data is stored in the entire buffer, data from now on will be
   * overwritten from old data one by one.
   * Therefore, only the length of latest data is stored in the buffer.
   *
   * Note: In the current implementation, only last stored data can be read
   *       from the buffer.}
   *
   * @param <DataType>
   *   {@.ja バッファに格納するデータ型}
   *   {@.en Data type to store in the buffer}
   */
public class RingBuffer<DataType> implements BufferBase<DataType> {

private static final int RINGBUFFER_DEFAULT_LENGTH = 8;
    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     * 
     */
    public RingBuffer() {
        this(RINGBUFFER_DEFAULT_LENGTH);

    }
    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     * 
     * <p>
     * {@.ja 指定されたバッファ長でバッファを初期化する。}
     * {@.en Initialize the buffer by specified buffer length.
     * However, if the specified length is less than two, the buffer should
     * be initialized by two in length.}
     * </p>
     *
     * @param length 
     *   {@.ja バッファ長}
     *   {@.en Buffer length}
     * 
     */
    public RingBuffer(int length) {
        this.m_length = (length < 2) ? 2 : length;
        this.m_oldPtr = 0;
        this.m_newPtr = (length < 2) ? 1 : length - 1;
        this.m_buffer = new Vector<DataType>(this.m_length);
        this.m_buffer.setSize(this.m_length);

        this.m_overwrite = true;
        this.m_readback = true;
        this.m_timedwrite = false;
        this.m_timedread = false;
        this.m_wtimeout = new TimeValue(1.0);
        this.m_rtimeout = new TimeValue(1.0);
        this.m_wpos = 0;
        this.m_rpos = 0;
        this.m_fillcount = 0;
        this.reset();
        this.m_wcount = 0;
    }

    /**
     * {@.ja バッファ長を取得する。}
     * {@.en Get the buffer length}
     * 
     * 
     * @return 
     *   {@.ja バッファ長}
     *   {@.en Buffer length}
     * 
     *
     */
    public int length() {
        synchronized (m_posmutex) {
            return this.m_length;
        }
    }
    /**
     * {@.ja バッファの長さをセットする。}
     * {@.en Get the buffer length}
     * 
     * <p>
     * {@.ja バッファ長を設定する。設定不可な場合はNOT_SUPPORTEDが返る。
     * この実装では BUFFER_OK しか返さない。}
     * {@.en Pure virtual function to get the buffer length.}
     * 
     * @return BUFFER_OK
     * 
     */
    public ReturnCode length(int n) {
        m_buffer.setSize(n);
        m_length = n;
        this.reset();
        return ReturnCode.BUFFER_OK; //BUFFER_OK;
    }

    /**
     * {@.ja バッファに書き込む。}
     * {@.en Write data into the buffer}
     * 
     * <p>
     * {@.ja 引数で与えられたデータをバッファに書き込む。
     *
     * バッファフル時の書込みモード (overwrite, do_nothing, block) は 
     * init() で設定されたモードに従う。
     *
     * 書き込み時にバッファが空(empty)状態で、別のスレッドがblockモード
     * で読み出し待ちをしている場合、signalを発行して読み出し側のブロッ
     * キングが解除される。}
     * {@.en Write data which is given argument into the buffer.}
     * 
     * @param value 
     *   {@.ja 書き込み対象データ}
     *   {@.en Target data for writing}
     * @return 
     *   {@.ja BUFFER_OK            正常終了
     *         BUFFER_FULL          バッファがフル状態
     *         TIMEOUT              書込みがタイムアウトした
     *         PRECONDITION_NOT_MET 設定異常}
     *   {@.en BUFFER_OK            Normal return
     *         BUFFER_FULL          Buffer full
     *         TIMEOUT              Timeout
     *         PRECONDITION_NOT_MET Unknown condition}
     */
    public ReturnCode write(final DataType value) {
        return this.write(value,-1,0);
    }
    /**
     * {@.ja バッファに書き込む。}
     * {@.en Write data into the buffer}
     * 
     * <p>
     * {@.ja 引数で与えられたデータをバッファに書き込む。
     *
     *
     * 第2引数(sec) に引数が指定された場合は、init() で設定されたモード
     * に関わらず、block モードとなり、バッファがフル状態であれば指定時
     * 間まち、タイムアウトする。
     * write(final DataType value, int sec, int nsec)の第3引数(nsec)が0の場合
     * と同等。
     * タイムアウト待ち中に、読み出しスレッド側でバッファから
     * 読み出せば、ブロッキングは解除されデータが書き込まれる。
     *
     * 書き込み時にバッファが空(empty)状態で、別のスレッドがblockモード
     * で読み出し待ちをしている場合、signalを発行して読み出し側のブロッ
     * キングが解除される。}
     * {@.en Write data which is given argument into the buffer.}
     * 
     * @param value 
     *   {@.ja 書き込み対象データ}
     *   {@.en Target data for writing}
     * @param sec   
     *   {@.ja タイムアウト時間 sec}
     *   {@.en Timeout sec}
     * @return 
     *   {@.ja BUFFER_OK            正常終了
     *         BUFFER_FULL          バッファがフル状態
     *         TIMEOUT              書込みがタイムアウトした
     *         PRECONDITION_NOT_MET 設定異常}
     *   {@.en BUFFER_OK            Normal return
     *         BUFFER_FULL          Buffer full
     *         TIMEOUT              Timeout
     *         PRECONDITION_NOT_MET Unknown condition}
     */
    public ReturnCode write(final DataType value, int sec) {
        return this.write(value,sec,0);
    }
    /**
     * {@.ja バッファに書き込む。}
     * {@.en Write data into the buffer}
     * 
     * <p>
     * {@.ja 引数で与えられたデータをバッファに書き込む。
     *
     * 第2引数(sec) に引数が指定された場合は、init() で設定されたモード
     * に関わらず、block モードとなり、バッファがフル状態であれば指定時
     * 間まち、タイムアウトする。第3引数(nsec)は指定されない場合0として
     * 扱われる。タイムアウト待ち中に、読み出しスレッド側でバッファから
     * 読み出せば、ブロッキングは解除されデータが書き込まれる。
     *
     * 書き込み時にバッファが空(empty)状態で、別のスレッドがblockモード
     * で読み出し待ちをしている場合、signalを発行して読み出し側のブロッ
     * キングが解除される。}
     * {@.en Write data which is given argument into the buffer.}
     * 
     * @param value 
     *   {@.ja 書き込み対象データ}
     *   {@.en Target data for writing}
     * @param sec   
     *   {@.ja タイムアウト時間 sec}
     *   {@.en Timeout sec}
     * @param nsec  
     *   {@.ja タイムアウト時間 nsec}
     *   {@.en Timeout nsec}
     * @return 
     *   {@.ja BUFFER_OK            正常終了
     *         BUFFER_FULL          バッファがフル状態
     *         TIMEOUT              書込みがタイムアウトした
     *         PRECONDITION_NOT_MET 設定異常}
     *   {@.en BUFFER_OK            Normal return
     *         BUFFER_FULL          Buffer full
     *         TIMEOUT              Timeout
     *         PRECONDITION_NOT_MET Unknown condition}
     */
    public ReturnCode write(final DataType value, int sec, int nsec) {
        synchronized (m_full.mutex) {
      
            if (full()) {
                
                boolean timedwrite = m_timedwrite;
                boolean overwrite = m_overwrite;
    
                if (!(sec < 0)) { // if second arg is set -> block mode
                    timedwrite = true;
                    overwrite  = false;
                }
    
                if (overwrite && !timedwrite) {      // "overwrite" mode
                    advanceRptr();
                }
                else if (!overwrite && !timedwrite) { // "do_nothing" mode
                    return ReturnCode.BUFFER_FULL;
                }
                else if (!overwrite && timedwrite) { // "block" mode
                    if (sec < 0) {
                        sec = 
                            (int)(m_rtimeout.sec()*1000+m_rtimeout.usec()/1000);
                        nsec = (int)(m_rtimeout.usec() % 1000)*1000;
                    }
                    try {
                        if(sec==0 && nsec==0){
                            return ReturnCode.TIMEOUT;
                        }
                        m_full.mutex.wait(sec, (int)nsec);
                        return ReturnCode.TIMEOUT;
                    }
                    catch(InterruptedException e ){
                        throw new RuntimeException(e.toString()); 
                    }
                    catch(IllegalMonitorStateException e) {
                        throw new RuntimeException(e.toString()); 
                    }
                }
                else {                                   // unknown condition
                    return ReturnCode.PRECONDITION_NOT_MET;
                }
            }
          
            boolean empty_ = empty();
          
            put(value);
          
            advanceWptr(1);
            if (empty_) {
                synchronized (m_empty.mutex) {
                    try {
                        m_empty.mutex.notify();
                    }
                    catch(IllegalMonitorStateException e) {
                    }
                }
            }
            return ReturnCode.BUFFER_OK;
        }
    }

    /**
     * {@.ja バッファから読み出す}
     * {@.en Readout data from the buffer}
     * 
     * <p>
     * {@.ja バッファに格納されたデータを読み出す。
     *
     * バッファ空状態での読み出しモード (readback, do_nothing, block) は 
     * init() で設定されたモードに従う。
     *
     *
     * 読み出し時にバッファが空(empty)状態で、別のスレッドがblockモード
     * で書込み待ちをしている場合、signalを発行して書込み側のブロッキン
     * グが解除される。}
     * {@.en Readout data stored into the buffer.}
     * </p>
     * 
     * @param valueRef
     *   {@.ja 読み出し対象データ}
     *   {@.en Readout data}
     * @return 
     *   {@.ja BUFFER_OK            正常終了
     *         BUFFER_EMPTY         バッファが空状態
     *         TIMEOUT              書込みがタイムアウトした
     *         PRECONDITION_NOT_MET 設定異常}
     *   {@.en BUFFER_OK
     *         BUFFER_EMPTY
     *         TIMEOUT
     *         PRECONDITION_NOT_MET}
     * 
     */
    public ReturnCode read(DataRef<DataType> valueRef) {
        return read(valueRef, -1, 0);
    }
    /**
     * {@.ja バッファから読み出す}
     * {@.en Readout data from the buffer}
     * 
     * <p>
     * {@.ja バッファに格納されたデータを読み出す。
     *
     *
     * タイムアウト待ち中に、書込みスレッド側でバッファへ書込み
     * があれば、ブロッキングは解除されデータが読みだされる。
     *
     * 読み出し時にバッファが空(empty)状態で、別のスレッドがblockモード
     * で書込み待ちをしている場合、signalを発行して書込み側のブロッキン
     * グが解除される。}
     * {@.en Readout data stored into the buffer.}
     * </p>
     * 
     * @param valueRef
     *   {@.ja 読み出し対象データ}
     *   {@.en Readout data}
     * @param sec   
     *   {@.ja タイムアウト時間 sec  (default -1: 無効)}
     *   {@.en TimeOut sec order}
     * @return 
     *   {@.ja BUFFER_OK            正常終了
     *         BUFFER_EMPTY         バッファが空状態
     *         TIMEOUT              書込みがタイムアウトした
     *         PRECONDITION_NOT_MET 設定異常}
     *   {@.en BUFFER_OK
     *         BUFFER_EMPTY
     *         TIMEOUT
     *         PRECONDITION_NOT_MET}
     * 
     */
    public ReturnCode read(DataRef<DataType> valueRef, int sec) {
        return read(valueRef, sec, 0);
    }
    /**
     * {@.ja バッファから読み出す}
     * {@.en Readout data from the buffer}
     * 
     * <p>
     * {@.ja バッファに格納されたデータを読み出す。
     *
     * 第2引数(sec)、第3引数(nsec)が指定されていない場合、バッファ空状
     * 態での読み出しモード (readback, do_nothing, block) は init() で設
     * 定されたモードに従う。
     *
     * 第2引数(sec) に引数が指定された場合は、init() で設定されたモード
     * に関わらず、block モードとなり、バッファが空状態であれば指定時間
     * 待ち、タイムアウトする。第3引数(nsec)は指定されない場合0として扱
     * われる。タイムアウト待ち中に、書込みスレッド側でバッファへ書込み
     * があれば、ブロッキングは解除されデータが読みだされる。
     *
     * 読み出し時にバッファが空(empty)状態で、別のスレッドがblockモード
     * で書込み待ちをしている場合、signalを発行して書込み側のブロッキン
     * グが解除される。}
     * {@.en Readout data stored into the buffer.}
     * </p>
     * 
     * @param valueRef
     *   {@.ja 読み出し対象データ}
     *   {@.en Readout data}
     * @param sec   
     *   {@.ja タイムアウト時間 sec  (default -1: 無効)}
     *   {@.en TimeOut sec order}
     * @param nsec  
     *   {@.ja タイムアウト時間 nsec (default 0)}
     *   {@.en TimeOut nsec order}
     * @return 
     *   {@.ja BUFFER_OK            正常終了
     *         BUFFER_EMPTY         バッファが空状態
     *         TIMEOUT              書込みがタイムアウトした
     *         PRECONDITION_NOT_MET 設定異常}
     *   {@.en BUFFER_OK
     *         BUFFER_EMPTY
     *         TIMEOUT
     *         PRECONDITION_NOT_MET}
     * 
     */
    public ReturnCode read(DataRef<DataType> valueRef, int sec, int nsec) {
        long local_msec = 0;
        int local_nsec = 0;
        synchronized(m_empty.mutex){
            if (empty()) {
                boolean timedread = m_timedread;
                boolean readback = m_readback;
                if (!(sec < 0)) {// if second arg is set -> block mode
                    timedread = true;
                    readback  = false;
                    local_msec = m_rtimeout.sec()*1000+m_rtimeout.usec()/1000;
                    local_nsec = (int)(m_rtimeout.usec() % 1000)*1000;
                }
                if (readback && !timedread) {      // "readback" mode
                    if (!(m_wcount > 0)) {
                        return ReturnCode.BUFFER_EMPTY;
                    }
                    advanceRptr(-1);
                }
                else if (!readback && !timedread) { // "do_nothing" mode
                    return ReturnCode.BUFFER_EMPTY;
                }
                else if (!readback && timedread) { // "block" mode
                    //  true: signaled, false: timeout
                    try {
                        m_empty.mutex.wait(local_msec, local_nsec);
                        if (empty()) {
                            return ReturnCode.TIMEOUT;
                        }
                    }
                    catch(IllegalArgumentException e) {
                        throw new RuntimeException(e.toString()); 
                    }
                    catch(IllegalMonitorStateException e) {
                        throw new RuntimeException(e.toString()); 
                    }
                    catch(InterruptedException e) {
                        throw new RuntimeException(e.toString()); 
                    }
                }
                else {                                   // unknown condition
                    return ReturnCode.PRECONDITION_NOT_MET;
                }
            }

            boolean  full_ = full();
      
            get(valueRef);
            advanceRptr();

            if (full_) {
                synchronized(m_full.mutex){
                    try {
                        m_full.mutex.notify();
                    }
                    catch(IllegalMonitorStateException e) {
                    }
                }
            }
      
            return ReturnCode.BUFFER_OK;
        }
    }

    /**
     * {@.ja バッファfullチェック}
     * {@.en Check on whether the buffer is full.}
     * 
     * <p>
     * {@.ja 未実装}
     * {@.en This is not implemented.}
     * @return 
     *   {@.ja fullチェック結果(true:バッファfull，false:バッファ空きあり)}
     *   {@.en True if the buffer is full, else false.}
     *
     */
    public boolean isFull() {
        return false;
    }

    /**
     * {@.ja バッファにデータを書き込む。}
     * {@.en Write data into the buffer}
     * 
     * <p>
     * {@.ja バッファにデータを書き込む。書き込みポインタの位置は変更されない。
     * この実装では常に BUFFER_OK を返す。}
     * {@.en Pure virtual function to write data into the buffer.
     * Always BUFFER_OK will be returned in this implementation.}
     * 
     * @param data 
     *   {@.ja 書き込み対象データ}
     *   {@.en Target data to write.}
     *
     * @return 
     *   {@.ja BUFFER_OK: 正常終了}
     *         BUFFER_ERROR: 異常終了}
     *   {@.en BUFFER_OK:    Successful
     *         BUFFER_ERROR: Failed}
     *
     */
    public ReturnCode put(final DataType data) {
        synchronized (m_posmutex) {
            this.m_buffer.set(m_wpos,data);
            return ReturnCode.BUFFER_OK;
        }
    }

    //
    /**
     * {@.ja バッファからデータを読み出す}
     * {@.en Reading data from the buffer}
     * 
     * <p>
     * {@.ja 読み出しポインタの位置は変更されない。}
     * {@.en The position of the pointer for reading is not changed.}
     *
     * @return 
     *   {@.ja 読み出しデータ}
     *   {@.en Read data}
     */
    public DataType get() {
        synchronized (m_posmutex) {
            return m_buffer.get(m_rpos);
        }
    }
    /**
     * {@.ja バッファからデータを読み出す。}
     * {@.en Write data into the buffer}
     * 
     * <p>
     * {@.ja 読み出しポインタの位置は変更されない。}
     * {@.en The position of the pointer for reading is not changed.}
     * 
     * @param value 
     *   {@.ja 読み出しデータ}
     *   {@.en Target data to write.}
     *
     * @return 
     *   {@.ja BUFFER_OK: 正常終了
     *         BUFFER_ERROR: 異常終了}
     *   {@.en Result of having written in data (true:Successful, false:Failed)}
     *
     */
    public ReturnCode get(DataRef<DataType> value) {
        synchronized (m_posmutex) {
            value.v = m_buffer.get(m_rpos);
            return ReturnCode.BUFFER_OK;
        }

    }
    /**
     * {@.ja バッファの設定。}
     * {@.en Sets the buffer.}
     *
     * <p>
     * {@.ja Properties で与えられるプロパティにより、
     * バッファの設定を初期化する。
     * 使用できるオプションと意味は以下の通り
     * <ul>
     * <li> buffer.length:
     *     バッファの長さ。自然数以外の数値が指定されても無視される。す
     *     でにバッファが使用状態でも、長さが再設定されたのち、すべての
     *     ポインタが初期化される。
     *
     * <li> buffer.write.full_policy:
     *     上書きするかどうかのポリシー。
     *     overwrite (上書き), do_nothing (何もしない), block (ブロックする)
     *     block を指定した場合、次の timeout 値を指定すれば、指定時間後
     *     書き込み不可能であればタイムアウトする。
     *     デフォルトは  overwrite (上書き)。
     *
     * <li> buffer.write.timeout:
     *     タイムアウト時間を [sec] で指定する。デフォルトは 1.0 [sec]。
     *     1 sec -> 1.0, 1 ms -> 0.001, タイムアウトしない -> 0.0
     *
     * <li> buffer.read.empty_policy:
     *     バッファが空のときの読み出しポリシー。
     *     readback (最後の要素), do_nothing (何もしない), block (ブロックする)
     *     block を指定した場合、次の timeout 値を指定すれば、指定時間後
     *     読み出し不可能であればタイムアウトする。
     *     デフォルトは readback (最後の要素)。
     *
     * <li> buffer.read.timeout:
     *     タイムアウト時間 [sec] で指定する。デフォルトは 1.0 [sec]。
     *     1sec -> 1.0, 1ms -> 0.001, タイムアウトしない -> 0.0</ul>}
     * {@.en Initializes the setting of the buffer according to the property 
     * given by Properties. 
     * <li> buffer.length:
     *     Length of buffer
     *
     * <li> buffer.write.full_policy:
     *     Policy whether overwrite.Default is overwrite.  
     *
     * <li> buffer.write.timeout:
     *     The timeout period is specified every second. 
     *     Default is 1.0 seconds. 
     *
     * <li> buffer.read.empty_policy:
     *     Reading policy when buffer is empty.Default is readback. 
     *
     * <li> buffer.read.timeout:
     *     The timeout period is specified every second. 
     *     Default is 1.0 seconds. </ul>}
     *
     * @param prop
     *   {@.ja バッファを設定するためのプロパティ}
     *   {@.en Property to set buffer}
     */
    public void init(final Properties prop) {
        initLength(prop);
        initWritePolicy(prop);
        initReadPolicy(prop);
    }

    /**
     * {@.ja バッファの状態をリセットする。}
     * {@.en Get the buffer length}
     * 
     * <p>
     * {@.ja バッファの読み出しポインタと書き込みポインタの位置をリセットする。
     * この実装では BUFFER_OK しか返さない。}
     * {@.en Pure virtual function to get the buffer length.}
     * </p>
     * 
     * @return 
     *   {@.ja BUFFER_OK: 正常終了
     *         NOT_SUPPORTED: リセット不可能
     *         BUFFER_ERROR: 異常終了}
     *   {@.en BUFFER_OK: Normal termination
     *         NOT_SUPPORTED:Reset failure
     *         BUFFER_ERROR:Abnormal termination}
     * 
     */ 
    public ReturnCode reset() {
        synchronized (m_posmutex) {
            m_fillcount = 0;
            m_wpos = 0;
            m_rpos = 0;
            m_wcount = 0;
            return ReturnCode.BUFFER_OK;
        }
    }
    /**
     * {@.ja バッファの現在の書込み要素のポインタ。}
     * {@.en Returns the pointer of a present writing element.}
     * 
     * <p>
     * {@.ja バッファの現在の書込み要素のポインタまたは、n個先のポインタを返す}
     * {@.en This function returns the object of a present writing 
     * element of the buffer. }
     * 
     * @param n 
     *   {@.ja 書込みポインタ + n の位置のポインタ}
     *   {@.en Pointer for writing }
     * @return 
     *   {@.ja 書込み位置のオブジェクト}
     *   {@.en Object at writing position}
     * 
     */ 
    public DataType wptr(int n) {
        synchronized(m_posmutex) {
            return this.m_buffer.get((m_wpos + n + m_length) % m_length);
        }
    }
    /**
     * {@.ja バッファの現在の書込み要素のポインタ。}
     * {@.en Returns the pointer of a present writing element.}
     * 
     * <p>
     * {@.ja バッファの現在の書込み要素のポインタを返す}
     * {@.en This function returns the pointer of a present writing element 
     * of the buffer. }
     * 
     * @return 
     *   {@.ja 書込み位置のオブジェクト}
     *   {@.en Object at writing position}
     * 
     */ 
    public DataType wptr() {
        int n = 0;
        synchronized(m_posmutex) {
            return m_buffer.get((m_wpos + n + m_length) % m_length);
        }
    }

    /**
     * {@.ja 書込みポインタを進める}
     * {@.en Get the buffer length}
     * 
     * <p>
     * {@.ja 現在の書き込み位置のポインタを n 個進める。
     * 書き込み可能な要素数以上の数値を指定した場合、PRECONDITION_NOT_MET
     * を返す。}
     * {@.en This function advances the writing pointer.}
     * </p>
     * 
     * @param  n 
     *   {@.ja 書込みポインタ + n の位置のポインタ}
     *   {@.en write pinter + n pointer}
     * @return 
     *   {@.ja BUFFER_OK:            正常終了
     *         PRECONDITION_NOT_MET: n > writable()}
     *   {@.en BUFFER_OK:Normal termination
     *         PRECONDITION_NOT_MET: n > writable()}
     * 
     */ 
    public ReturnCode advanceWptr(int n) {
      // n > 0 :
      //     n satisfies n <= writable elements
      //                 n <= m_length - m_fillcout
      // n < 0 : -n = n'
      //     n satisfies n'<= readable elements
      //                 n'<= m_fillcount
      //                 n >= - m_fillcount
        if (n > 0 && n > (m_length - m_fillcount) ||
          n < 0 && n < (-m_fillcount)) {
            return ReturnCode.PRECONDITION_NOT_MET;
        }

        synchronized (m_posmutex) {
            m_wpos = (m_wpos + n + m_length) % m_length;
            m_fillcount += n;
            m_wcount += n;
            return ReturnCode.BUFFER_OK;
        }
    }
    /**
     *
     * {@.ja 書込みポインタを進める。}
     * {@.en advances the writing pointer.} 
     *
     * <p>
     * {@.ja 現在の書き込み位置のポインタを n 個進める。
     * 書き込み可能な要素数以上の数値を指定した場合、PRECONDITION_NOT_MET
     * を返す。}
     * {@.en This function advances the writing pointer. }
     * 
     * @return 
     *   {@.ja BUFFER_OK:            正常終了
     *         PRECONDITION_NOT_MET: n > writable()}
     *   {@.en BUFFER_OK:            Normal termination
     *         PRECONDITION_NOT_MET: n > writable()}
     * 
     */ 
    public ReturnCode advanceWptr() {
        return this.advanceWptr(1);
    }
    /**
     * {@.ja バッファに書込み可能な要素数。}
     * {@.en Write data into the buffer}
     * 
     * <p>
     * {@.ja バッファに書込み可能な要素数を返す。}
     * {@.en This function returns the number of elements that can be 
     * written in the buffer. }
     * 
     * @return 
     *   {@.ja 書き込み可能な要素数}
     *   {@.en Recordable number of elements}
     */
    public int writable() {
        synchronized(m_posmutex) {
            return m_length - m_fillcount;
        }
    }
    /**
     * {@.ja バッファfullチェック}
     * {@.en Check on whether the buffer is full.}
     * 
     * <p>
     * {@.ja バッファfullチェック用純粋仮想関数}
     * {@.en Pure virtual function to check on whether the buffer is full.}
     *
     * @return 
     *   {@.ja fullチェック結果(true:バッファfull，false:バッファ空きあり)}
     *   {@.en True if the buffer is full, else false.}
     */
    public boolean full() {
        synchronized(m_posmutex) {
            return m_length == m_fillcount;
        }
    }
    /**
     * {@.ja バッファの現在の読み出し要素のポインタ.}
     * {@.en returns the pointer of a present reading element.}
     *
     * <p>
     * {@.ja バッファの現在の読み出し要素のポインタまたは、
     * n個先のポインタを返す}
     * {@.en This function returns the pointer of a present reading 
     * element of the buffer. }
     * 
     * @param  n 
     *   {@.ja 読み出しポインタ + n の位置のポインタ}
     *   {@.en reading position}
     * @return 
     *   {@.ja 読み出し位置のポインタ}
     *   {@.en Object at reading position}
     * 
     */ 
    public DataType rptr(int n) {
        synchronized(m_posmutex) {
            return (m_buffer.get((m_rpos + n + m_length) % m_length));
        }
    }
    /**
     * {@.ja バッファの現在の読み出し要素のポインタ.}
     * {@.en returns the pointer of a present reading element.}
     *
     * <p>
     * {@.ja バッファの現在の読み出し要素のポインタを返す}
     * {@.en This function returns the pointer of a present reading 
     * element of the buffer. }
     * 
     * @return 
     *   {@.ja 読み出し位置のポインタ}
     *   {@.en Object at reading position}
     * 
     */ 
    public DataType rptr() {
        int n = 0;
        synchronized(m_posmutex) {
            return (m_buffer.get((m_rpos + n + m_length) % m_length));
        }
    }
    /**
     * {@.ja 読み出しポインタを進める。}
     * {@.en advances the reading pointer.} 
     * 
     * <p>
     * {@.ja 現在の読み出し位置のポインタを n 個進める。}
     * {@.en This function advances the reading pointer.}
     * 
     * @param  n 
     *   {@.ja 読み出しポインタ + n の位置のポインタ}
     *   {@.en Advanced number of points}
     * @return 
     *   {@.ja BUFFER_OK: 正常終了
     *         BUFFER_ERROR: 異常終了}
     *   {@.en BUFFER_OK:            Normal termination
     *         BUFFER_ERROR: Abnormal termination}
     * 
     */ 
    public ReturnCode advanceRptr(int n) {
        // n > 0 :
        //     n satisfies n <= readable elements
        //                 n <= m_fillcout
        // n < 0 : -n = n'
        //     n satisfies n'<= m_length - m_fillcount
        //                 n >= m_fillcount - m_length
        if ((n > 0 && n > m_fillcount) ||
            (n < 0 && n < (m_fillcount - m_length)))
        {
            return ReturnCode.PRECONDITION_NOT_MET;
        }

        synchronized(m_posmutex) {
            m_rpos = (m_rpos + n + m_length) % m_length;
            m_fillcount -= n;
            return ReturnCode.BUFFER_OK;
        }
    }
    /**
     * {@.ja 読み出しポインタを進める。}
     * {@.en advances the reading pointer.} 
     * 
     * <p>
     * {@.ja 現在の読み出し位置のポインタを n 個進める。}
     * {@.en This function advances the reading pointer.}
     * 
     * @return 
     *   {@.ja BUFFER_OK: 正常終了
     *         BUFFER_ERROR: 異常終了}
     *   {@.en BUFFER_OK:            Normal termination
     *         BUFFER_ERROR: Abnormal termination}
     * 
     */ 
    public ReturnCode advanceRptr() {
        return advanceRptr(1);
    }
    /**
     * {@.ja バッファから読み出し可能な要素数。}
     * {@.en returns the number of elements that can be read.}
     * 
     * <p>
     * {@.ja バッファから読み出し可能な要素数を返す。}
     * {@.en This function returns the number of elements that can be read 
     * from the buffer. }
     * </p>
     * 
     * @return 
     *   {@.ja 読み出し可能な要素数}
     *   {@.en Number of elements that can be read}
     *
     */
    public int readable() {
        synchronized(m_posmutex) {
            return m_fillcount;
        }
    }     
    /**
     * {@.ja バッファemptyチェック}
     * {@.en Check on whether the buffer is empty.}
     * 
     * <p>
     * {@.ja バッファemptyチェック用純粋仮想関数}
     * {@.en Pure virtual function to check on whether the buffer is empty.}
     *
     * @return 
     *   {@.ja emptyチェック結果(true:バッファempty，false:バッファデータあり)}
     *   {@.en True if the buffer is empty, else false.}
     */
    public boolean empty() {
        synchronized(m_posmutex) {
            return m_fillcount == 0;
        }
    }
    /**
     * <p> initLength </p>
     *
     * @param prop
     */
    private void initLength(final Properties prop) {
        if (prop.getProperty("length") != "") {
            try {
                int n = Integer.parseInt(prop.getProperty("length"));
                if (n > 0) {
                    this.length(n);
                }
            }
            catch(NumberFormatException e){
            }
        }
    }
    /**
     * <p> initWritePolicy </p>
     *
     * @param prop
     */
    private void initWritePolicy(final Properties prop) {
        String policy = prop.getProperty("write.full_policy");
        policy = StringUtil.normalize(policy);
        if (policy.equals("overwrite")) {
            m_overwrite = true;
            m_timedwrite = false;
        }
        else if (policy.equals("do_nothing")) {
            m_overwrite = false;
            m_timedwrite = false;
        }
        else if (policy.equals("block")) {
            m_overwrite = false;
            m_timedwrite = true;

            try {
                double tm = Double.parseDouble(prop.getProperty("write.timeout"));
                if (!(tm < 0)) {
                    m_wtimeout.convert(tm);
                }
            }
            catch(NumberFormatException e){
            }
        }
    }
    /**
     * <p> iinitReadPolicy </p>
     *
     * @param prop
     */
    private void initReadPolicy(final Properties prop) {
        String policy = prop.getProperty("read.empty_policy");
        policy= StringUtil.normalize(policy);
        if (policy.equals("readback")) {
            m_readback = true;
            m_timedread = false;
        }
        else if (policy.equals("do_nothing")) {
            m_readback = false;
            m_timedread = false;
        }
        else if (policy.equals("block")) {
            m_readback = false;
            m_timedread = true;

            try {
                double tm = Double.parseDouble(prop.getProperty("read.timeout"));
                if (!(tm < 0)) {
                    m_rtimeout.convert(tm);
                }
            }
            catch(NumberFormatException e){
            }
        }
    }


    
    private boolean m_overwrite;
    private boolean m_readback;
    private boolean m_timedwrite;
    private boolean m_timedread;
    private TimeValue m_wtimeout;
    private TimeValue m_rtimeout;


    private int m_length;
    private int m_oldPtr;
    private int m_newPtr;
    private Vector<DataType> m_buffer;

    private int m_wpos;
    private int m_rpos;
    private int m_fillcount;
    private static String m_posmutex = new String();

    /**
     * {@.ja 条件変数構造体}
     * {@.en class for condition variable}
     */
    protected class condition {
        /**
         * {@.ja コンストラクタ}
         * {@.en Constructor}
         */
        public condition() {
        }
        /**
         * {@.ja ミューテックス用変数}
         * {@.en Variable for mutex}
         */
        public String mutex = new String();
    };
    private condition m_empty = new condition();
    private condition m_full = new condition();
    /**
     * {@.ja 書き込みカウント}
     * {@.en Counter for writing}
     */
    int m_wcount;
}
