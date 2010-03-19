package jp.go.aist.rtm.RTC.buffer;

import java.util.Vector;
import java.lang.Integer;
import java.lang.Double;
import java.lang.Thread;
import java.lang.IllegalMonitorStateException; 

import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.StringUtil;
import jp.go.aist.rtm.RTC.util.TimeValue;
import jp.go.aist.rtm.RTC.util.Properties;

/**
 * <p>リング状のバッファを持つバッファ実装です。
 * バッファ全体がデータで埋まった場合、以降のデータは古いデータから順次上書きされていきます。
 * これにより、直近のバッファ長分のデータのみが残ります。</p>
 *
 * @param <DataType> バッファ内のデータ型を指定します。
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
     * <p>バッファ長を取得します。</p>
     * 
     * @return バッファ長
     */
    public int length() {
        synchronized (m_posmutex) {
            return this.m_length;
        }
    }
    /**
     * <p> sets the length of the buffer.  </p>
     * @param n length
     * @return BUFFER_OK
     */
    public ReturnCode length(int n) {
        m_buffer.setSize(n);
        m_length = n;
        this.reset();
        return ReturnCode.BUFFER_OK; //BUFFER_OK;
    }

    /**
     * <p>バッファにデータを書き込みます。</p>
     * 
     * @param value 書き込むデータ
     * @return 書き込みに成功した場合はtrueを、さもなくばfalseを返します。
     */
    public ReturnCode write(final DataType value) {
        return this.write(value,-1,0);
    }
    public ReturnCode write(final DataType value, int sec) {
        return this.write(value,sec,0);
    }
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
          
            if (empty_) {
                synchronized (m_empty.mutex) {
                    try {
                        m_empty.mutex.notify();
                    }
                    catch(IllegalMonitorStateException e) {
                    }
                }
            }
            advanceWptr(1);
            return ReturnCode.BUFFER_OK;
        }
    }

    /**
     * <p>バッファからデータを読み込みます。</p>
     * 
     * @param valueRef 読み込んだデータを受け取るためのDataRefオブジェクト
     * @return 読み込みに成功した場合はtrueを、さもなくばfalseを返します。
     */
    public ReturnCode read(DataRef<DataType> valueRef) {
        return read(valueRef, -1, 0);
    }
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
     * <p>バッファがフルかどうか判定します。</p>
     * 
     * @return バッファがフルならばtrue、さもなくばfalseを返します。
     */
    public boolean isFull() {
        return false;
    }

    /**
     * <p>バッファにデータを書き込みます。</p>
     * 
     * @param data 書き込むデータ
     * @return ReturnCode
     */
    public ReturnCode put(final DataType data) {
        synchronized (m_posmutex) {
            this.m_buffer.set(m_wpos,data);
            return ReturnCode.BUFFER_OK;
        }
    }

    /**
     * <p>バッファからデータを読み込みます。</p>
     * 
     * @return 読み込んだデータ
     */
    public DataType get() {
        synchronized (m_posmutex) {
            return m_buffer.get(m_rpos);
        }
    }
    /**
     * <p> get </p>
     *
     * @param value 
     * @return ReturnCode
     */
    public ReturnCode get(DataRef<DataType> value) {
        synchronized (m_posmutex) {
            value.v = m_buffer.get(m_rpos);
            return ReturnCode.BUFFER_OK;
        }

    }
    /**
     * <p> init </p>
     *
     * @param prop
     */
    public void init(final Properties prop) {
        initLength(prop);
        initWritePolicy(prop);
        initReadPolicy(prop);
    }

    /**
     * {@.ja バッファの状態をリセットする}
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
     * <p> wptr </p>
     * <p> This function returns the object of a present writing element of the buffer. </p>
     * @param  n 
     * @return DataType
     */
    public DataType wptr(int n) {
        synchronized(m_posmutex) {
            return this.m_buffer.get((m_wpos + n + m_length) % m_length);
        }
    }
    /**
     * <p> wptr </p>
     * <p> This function returns the pointer of a present writing element of the buffer. </p>
     * @return DataType
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
     * <p> advanceWptr </p>
     * <p> This function advances the writing pointer. </p>
     *
     * @return ReturnCode
     */
    public ReturnCode advanceWptr() {
        return this.advanceWptr(1);
    }
    /**
     * <p> writable </p>
     * <p> This function returns the number of elements that can be written in the buffer. </p>
     * @return int 
     */
    public int writable() {
        synchronized(m_posmutex) {
            return m_length - m_fillcount;
        }
    }
    /**
     * <p> full </p>
     * <p> Check on whether the buffer is full </p>
     *
     * @return boolean 
     */
    public boolean full() {
        synchronized(m_posmutex) {
            return m_length == m_fillcount;
        }
    }
    /**
     * <p> rptr </p>
     * <p> This function returns the pointer of a present reading element of the buffer.  </p>
     * @param  n
     * @return DataType 
     */
    public DataType rptr(int n) {
        synchronized(m_posmutex) {
            return (m_buffer.get((m_rpos + n + m_length) % m_length));
        }
    }
    /**
     * <p> rptr </p>
     * <p> This function returns the pointer of a present reading element of the buffer. </p>
     * @return DataType 
     */
    public DataType rptr() {
        int n = 0;
        synchronized(m_posmutex) {
            return (m_buffer.get((m_rpos + n + m_length) % m_length));
        }
    }
    /**
     * <p> advanceRptr </p>
     * <p> This function advances the reading pointer.  </p>
     * @param  n 
     * @return ReturnCode 
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
     * <p> advanceRptr </p>
     * <p> This function advances the reading pointer.  </p>
     * @return ReturnCode 
     */
    public ReturnCode advanceRptr() {
        return advanceRptr(1);
    }
    /**
     * <p> readable </p>
     * <p> This function returns the number of elements that can be read 
     * from the buffer.  </p>
     * @return int 
     */
    public int readable() {
        synchronized(m_posmutex) {
            return m_fillcount;
        }
    }     
    /**
     * <p> empty </p>
     * <p> Check on whether the buffer is empty. </p>
     *
     * @return boolean 
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

    protected class condition {
        public condition() {
        }
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
