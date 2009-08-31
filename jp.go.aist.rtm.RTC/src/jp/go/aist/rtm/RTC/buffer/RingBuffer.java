package jp.go.aist.rtm.RTC.buffer;

import java.util.Vector;
import java.lang.Integer;
import java.lang.Double;
import java.lang.Thread;

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

private final int RINGBUFFER_DEFAULT_LENGTH = 8;
    /**
     * <p>コンストラクタです。</p>
     * 
     */
    public RingBuffer() {
        int length = RINGBUFFER_DEFAULT_LENGTH;
        this.m_length = (length < 2) ? 2 : length;
        this.m_oldPtr = 0;
        this.m_newPtr = (length < 2) ? 1 : length - 1;
        this.m_buffer = new Vector<DataType>(this.m_length);
/*
        for (int i = 0; i < this.m_length; i++) {
            this.m_buffer.add(new Data<DataType>());
        }
*/

    }
    /**
     * <p>コンストラクタです。</p>
     * 
     * @param length バッファ長
     */
    public RingBuffer(int length) {
        this.m_length = (length < 2) ? 2 : length;
        this.m_oldPtr = 0;
        this.m_newPtr = (length < 2) ? 1 : length - 1;
        this.m_buffer = new Vector<DataType>(this.m_length);
/*
        for (int i = 0; i < this.m_length; i++) {
            this.m_buffer.add(new Data<DataType>());
        }
*/
        this.m_overwrite = true;
        this.m_readback = true;
        this.m_timedwrite = false;
        this.m_timedread = false;
        this.m_wtimeout.convert(1.0);
        this.m_rtimeout.convert(1.0);
        this.m_wpos = 0;
        this.m_rpos = 0;
        this.m_fillcount = 0;
        this.reset();
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
        synchronized (m_posmutex) {
            return this.m_length;
        }
    }
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
        long sec = 1;
        long nsec = 0;
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
                else if (!overwrite && !timedwrite) { // "do_notiong" mode
                    return ReturnCode.BUFFER_FULL;
                }
                else if (!overwrite && timedwrite) { // "block" mode
                    if (sec < 0) {
                        sec = m_wtimeout.sec();
                        nsec = m_wtimeout.usec() * 1000;
                      }
                  //  true: signaled, false: timeout
                      try {
                          m_full.wait(sec*1000, (int)nsec);
                          return ReturnCode.TIMEOUT;
                      }
                      catch(InterruptedException e ){
                      }
//                      if (!m_full.cond.wait(sec, nsec)) {
//                        return ReturnCodeTIMEOUT;
//                      }
                }
                else {                                   // unknown condition
                    return ReturnCode.PRECONDITION_NOT_MET;
                }
            }
          
            boolean empty_ = empty();
          
            put(value);
          
            if (empty_) {
                synchronized (m_empty.mutex) {
//                    m_empty.cond.signal();
                    m_empty.notify();
                }
            }
            advanceWptr(1);
            return ReturnCode.BUFFER_OK;
        }
    }
    public ReturnCode write(final DataType value, long sec, long nsec) {
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
                else if (!overwrite && !timedwrite) { // "do_notiong" mode
                    return ReturnCode.BUFFER_FULL;
                }
                else if (!overwrite && timedwrite) { // "block" mode
                    if (sec < 0) {
                        sec = m_wtimeout.sec();
                        nsec = m_wtimeout.usec() * 1000;
                      }
                  //  true: signaled, false: timeout
                      try {
                          m_full.wait(sec*1000, (int)nsec);
                          return ReturnCode.TIMEOUT;
                      }
                      catch(InterruptedException e ){
                      }
//                      if (!m_full.cond.wait(sec, nsec)) {
//                        return ReturnCode.TIMEOUT;
//                      }
                }
                else {                                   // unknown condition
                    return ReturnCode.PRECONDITION_NOT_MET;
                }
            }
          
            boolean empty_ = empty();
          
            put(value);
          
            if (empty_) {
                synchronized (m_empty.mutex) {
//                    m_empty.cond.signal();
                    m_empty.notify();
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
        return this.m_buffer.get(this.m_newPtr);
    }
    /**
     * <p> get </p>
     *
     * @param DataType
     * @return ReturnCode
     */
    public ReturnCode get(DataType value) {
        synchronized (m_posmutex) {
            value = m_buffer.get(m_rpos);
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
     * <p> reset </p>
     * <p> This function resets the state of the buffer. </p>
     *
     * @return ReturnCode
     */
    public ReturnCode reset() {
        synchronized (m_posmutex) {
            m_fillcount = 0;
            m_wpos = 0;
            m_rpos = 0;
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
     * <p> advanceWptr </p>
     * <p> This function advances the writing pointer. </p>
     *
     * @param  n
     * @return ReturnCode
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
          n < 0 && n < (-m_fillcount))
        {
            return ReturnCode.PRECONDITION_NOT_MET;
        }

        synchronized (m_posmutex) {
            m_wpos = (m_wpos + n + m_length) % m_length;
            m_fillcount += n;
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
      // n > 0 :
      //     n satisfies n <= writable elements
      //                 n <= m_length - m_fillcout
      // n < 0 : -n = n'
      //     n satisfies n'<= readable elements
      //                 n'<= m_fillcount
      //                 n >= - m_fillcount
        int n = 1;
        if (n > 0 && n > (m_length - m_fillcount) ||
          n < 0 && n < (-m_fillcount))
        {
            return ReturnCode.PRECONDITION_NOT_MET;
        }

        synchronized (m_posmutex) {
            m_wpos = (m_wpos + n + m_length) % m_length;
            m_fillcount += n;
            return ReturnCode.BUFFER_OK;
        }
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
    public ReturnCode advanceRptr(int n)
    {
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
     * @param  n 
     * @return ReturnCode 
     */
    public ReturnCode advanceRptr()
    {
        int n = 1;
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
     * <p> readable </p>
     * <p> This function returns the number of elements that can be read from the buffer.  </p>
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
        StringUtil.normalize(policy);
        if (policy == "overwrite") {
            m_overwrite = true;
            m_timedwrite = false;
        }
        else if (policy == "do_nothing") {
            m_overwrite = false;
            m_timedwrite = false;
        }
        else if (policy == "block") {
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
        StringUtil.normalize(policy);
        if (policy == "readback") {
            m_readback = true;
            m_timedread = false;
        }
        else if (policy == "do_nothing") {
            m_readback = false;
            m_timedread = false;
        }
        else if (policy == "block") {
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



/*
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
*/
    
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

    class condition extends Thread {
        public condition() {
//            cond = mutex
        }
//        coil::Condition<coil::Mutex> cond;
        public String mutex = new String();
    };
    private condition m_empty;
    private condition m_full;
}
