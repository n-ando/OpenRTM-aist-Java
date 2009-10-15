package jp.go.aist.rtm.RTC.port;

import java.lang.ClassCastException;
import org.omg.CORBA.TypeCodePackage.BadKind;
import java.io.IOException;

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.lang.ClassNotFoundException;
import java.lang.NoSuchFieldException;
import java.lang.NoSuchMethodException;
import java.lang.reflect.Field;

import jp.go.aist.rtm.RTC.port.ReturnCode;
import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.buffer.RingBuffer;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.TypeCast;
import jp.go.aist.rtm.RTC.util.ORBUtil;


/**
 * <p>入力ポートのためのベース実装クラスです。
 * 外部から送信されてきたデータは順次、コンストラクタで指定されたバッファに格納されます。
 * バッファ内のデータはフラグによって未読／既読状態が管理され、isNew(), isEmpty()などの
 * メソッドによってハンドリングできます。</p>
 * 
 * @param <DataType> データ型を指定します。
 */
public class InPort<DataType> extends InPortBase {

    private static final long TIMEOUT_TICK_USEC = 10;
    private static final long TIMEOUT_TICK_MSEC_PART = TIMEOUT_TICK_USEC / 1000;
    private static final int TIMEOUT_TICK_NSEC_PART = ((int) (TIMEOUT_TICK_USEC % 1000)) * 1000;

    /**
     * <p> toTypeCdoe </p>
     * <p> This function gets TypeCode of data. </p>
     *
     * @param value data
     * @return TypeCdoe(String)
     */
    private static <DataType> String toTypeCdoe(DataRef<DataType> value) { 
        DataType data = value.v;
        Class cl = data.getClass();
        String str = new String();
        TypeCast<DataType> cast = new TypeCast<DataType>(cl);
        org.omg.CORBA.Any any = ORBUtil.getOrb().create_any();
        any = cast.castAny(value.v);
        try {
            str = any.type().name();
        }
        catch(org.omg.CORBA.TypeCodePackage.BadKind e){
        }
        return str;

    }
    /**
     * <p> read_steram </p>
     * <p> This function reads data from InputStream.  </p>
     *
     * @param data  The read data is stored.  
     * @param cdr   InPutStream
     * @return Read data
     */
    private DataType read_stream(DataRef<DataType> data,InputStream cdr) {

        try {
            m_method.invoke(m_object ,cdr);
            data.v = (DataType)m_field.get(m_object);
        }
        catch(IllegalAccessException e){
            //set throws
            rtcout.println(rtcout.WARN, 
                   "Exception caught."+e.toString());
        }
        catch(IllegalArgumentException e){
            //invoke throws
            rtcout.println(rtcout.WARN, 
                   "Exception caught."+e.toString());
        }
        catch(InvocationTargetException e){
            //invoke throws
            rtcout.println(rtcout.WARN, 
                   "Exception caught."+e.toString());
        }

        return data.v;
    }
/* zxc Delete this function after the test ends.
    private DataType read_stream(DataRef<DataType> data,InputStream cdr) {

        //Reads an Any from this input stream.
        org.omg.CORBA.Any any = ORBUtil.getOrb().create_any();
        any = cdr.read_any();
        //Creates TypeCast.
        Class cl = data.v.getClass();
        TypeCast<DataType> cast = new TypeCast<DataType>(cl);
        //Casts Any into DataType.
        data.v = cast.castType(any);

        return data.v;
    }
*/
    /**
     * <p>コンストラクタです。</p>
     *
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
        
        super(name, toTypeCdoe(value));

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

        Class cl = value.v.getClass();
        String str = cl.getName();
        try {
            Class holder = Class.forName(str+"Holder",
                                         true,
                                         this.getClass().getClassLoader());
            m_object = holder.newInstance();
            m_method = holder.getMethod("_read",
                                   org.omg.CORBA.portable.InputStream.class);
            m_field = holder.getField("value");
        }
        catch(NoSuchFieldException e){
            //getField throws
            rtcout.println(rtcout.WARN, 
                   "Exception caught."+e.toString());
        }
        catch(java.lang.InstantiationException e){
            rtcout.println(rtcout.WARN, 
                   "Exception caught."+e.toString());
        }
        catch(ClassNotFoundException e){
            //forName throws
            rtcout.println(rtcout.WARN, 
                   "Exception caught."+e.toString());
        }
        catch(IllegalAccessException e){
            //set throws
            rtcout.println(rtcout.WARN, 
                   "Exception caught."+e.toString());
        }
        catch(NoSuchMethodException e){
            //getMethod throws
            rtcout.println(rtcout.WARN, 
                   "Exception caught."+e.toString());
        }
        catch(IllegalArgumentException e){
            //invoke throws
            rtcout.println(rtcout.WARN, 
                   "Exception caught."+e.toString());
        }
    }
    
    /**
     * <p>コンストラクタです。
     * 読み取り・書き込みともに非ブロックモードとなり、タイムアウト時間は0で設定されます。</p>
     *
     * @param name ポート名称
     * @param value このポートにバインドされるDataType型の変数
     */
    public InPort(final String name, DataRef<DataType> value) {
        this( new RingBuffer<DataType>(8), name, value, false, false, 0, 0);
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
     * <p> Check whether the data is newest </p>
     * 
     * <p> Check whether the data stored at a current buffer position is newest.</p>
     *
     * @return Newest data check result
     *         ( true:Newest data. Data has not been readout yet.
     *          false:Past data Data has already been readout.)
     * 
     */
    public boolean isNew() {

        rtcout.println(rtcout.TRACE, "isNew()");

        if (m_connectors.size() == 0) {
            rtcout.println(rtcout.DEBUG, "no connectors");
            return false;
        }
        int r = m_connectors.elementAt(0).getBuffer().readable();
        if (r > 0) {
            rtcout.println(rtcout.DEBUG, "isNew() = true, readable data: " + r);
            return true;
        }
      
        rtcout.println(rtcout.DEBUG, "isNew() = false, no readable data");
        return false;
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
        rtcout.println(rtcout.TRACE, "DataType read()");

        if (m_OnRead != null) {
            m_OnRead.run();
            rtcout.println(rtcout.TRACE, "OnRead called");
        }

        if (m_connectors.size() == 0) {
            rtcout.println(rtcout.DEBUG, "no connectors");
            return m_value.v;
        }

        org.omg.CORBA.Any any = ORBUtil.getOrb().create_any();
        OutputStream cdr = any.create_output_stream();

        DataRef<OutputStream> dataref = new DataRef<OutputStream>(cdr);
        ReturnCode ret = m_connectors.elementAt(0).read(dataref);
        cdr = dataref.v;
        if (ret.equals(ReturnCode.PORT_OK)) {
            rtcout.println(rtcout.DEBUG, "data read succeeded");
            InputStream input_stream = cdr.create_input_stream();
            m_value.v = read_stream(m_value,input_stream);
            if (m_OnReadConvert != null) {
                m_value.v = m_OnReadConvert.run(m_value.v);
                rtcout.println(rtcout.DEBUG, "OnReadConvert called");
                return m_value.v;
            }
            return m_value.v;
        }
        else if (ret.equals(ReturnCode.BUFFER_EMPTY)) {
            rtcout.println(rtcout.WARN, "buffer empty");
            return m_value.v;
        }
        else if (ret.equals(ReturnCode.BUFFER_TIMEOUT)) {
            rtcout.println(rtcout.WARN, "buffer read timeout");
            return m_value.v;
        }
        rtcout.println(rtcout.ERROR, "unknown retern value from buffer.read()");
        return m_value.v;
    }
    
    /**
     * <p>バインドされたDataType型のデータに、ポート内バッファの最新値を読み込みます。<br />
     * ただし、あらかじめコンストラクタで、DataType型の変数がバインドされていなければなりません。<br />
     */
    public void update() {
        this.read();
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
     * <p>バッファが空である、つまり読み取れるデータがないかどうかを取得します。</p>
     * 
     * @return バッファが空の場合はtrueを、さもなくばfalseを返します。
     */
    public boolean isEmpty() {
        rtcout.println(rtcout.TRACE, "isEmpty()");

        if (m_connectors.size() == 0) {
            rtcout.println(rtcout.DEBUG, "no connectors");
            return true;
        }
        int r = m_connectors.elementAt(0).getBuffer().readable();
        if (r == 0) {
            rtcout.println(rtcout.DEBUG, "isEmpty() = true, buffer is empty");
            return true;
        }
      
        rtcout.println(rtcout.DEBUG, 
                       "isEmpty() = false, data exists in the buffer");
        return false;
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

    private Object m_object = null;
    private Method m_method = null;
    private Field m_field = null;
    
}
