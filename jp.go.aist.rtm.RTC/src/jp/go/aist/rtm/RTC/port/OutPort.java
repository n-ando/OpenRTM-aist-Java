package jp.go.aist.rtm.RTC.port;

import org.omg.CORBA.portable.Streamable;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.lang.ClassNotFoundException;
import java.lang.NoSuchFieldException;
import java.lang.NoSuchMethodException;
import java.lang.reflect.Field;

import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.buffer.RingBuffer;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.util.TypeCast;
import jp.go.aist.rtm.RTC.util.ORBUtil;
import jp.go.aist.rtm.RTC.util.TimeValue;
import jp.go.aist.rtm.RTC.port.publisher.PublisherBase;

import RTC.Time;

/**
 * <p>出力ポートの実装です。さらに上位の出力ポートのベース実装として利用されます。</p>
 *
 * @param <DataType> データ型を指定します。
 */
public class OutPort<DataType> extends OutPortBase {

    /**
     * <p> toTypeCode </p>
     * <p> This function gets TypeCode of data. </p>
     *
     * @param value data
     * @return TypeCdoe(String)
     */
    private static <DataType> String toTypeCode(DataRef<DataType> value) { 
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
     * <p> write_stream </p>
     * <p> This function writes data from OutputStream.  </p>
     *
     * @param data  data   
     * @param cdr   OutputStream
     */
    private void write_stream(DataType data,OutputStream cdr) {
        try {
            m_field.set(m_object,data);
            m_method.invoke( m_object ,cdr);
//            m_streamable._write(cdr);

        }
        catch(IllegalAccessException e){
            rtcout.println(rtcout.WARN, 
                   "Exception caught."+e.toString());
        }
        catch(IllegalArgumentException e){
            rtcout.println(rtcout.WARN, 
                   "Exception caught."+e.toString());
        }
        catch(InvocationTargetException e){
            rtcout.println(rtcout.WARN, 
                   "Exception caught."+e.toString());
         }

    }
    /**
     * <p> set_timestamp </p>
     * <p> This function sets the timestamp.  </p>
     *
     * @param data  data   
     */
    private void set_timestamp(DataType data) {
        long nanotime = System.nanoTime();
        RTC.Time tm = new RTC.Time((int)(nanotime/1000000000),
                                   (int)(nanotime%1000000000));
        Class cl = data.getClass();
        String str = cl.getName();
        try {
            cl.getField("tm").set(data,tm);
        }
        catch(NoSuchFieldException e){
            //getField throws
        }
        catch(IllegalAccessException e){
            //set throws
        }
         
    }
    /**
     * <p> set_timestamp </p>
     * <p> This function sets the timestamp.  </p>
     *
     * @return RTC.Time
     */
    public static RTC.Time get_timestamp() {
        long nanotime = System.nanoTime();
        RTC.Time tm = new RTC.Time((int)(nanotime/1000000000),
                                   (int)(nanotime%1000000000));
        return tm; 
    }
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
        
        super(name,toTypeCode(valueRef));
        
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

        Class cl = valueRef.v.getClass();
        String str = cl.getName();
        try {
            Class holder = Class.forName(str+"Holder",
                                         true,
                                         this.getClass().getClassLoader());
            m_streamable = (Streamable)holder.newInstance();
            m_object = holder.newInstance();
            m_field = holder.getField("value");
            m_method = holder.getMethod("_write",
                                   org.omg.CORBA.portable.OutputStream.class);

        }
        catch(java.lang.InstantiationException e){
            rtcout.println(rtcout.WARN, 
                   "Exception caught."+e.toString());
         }

        catch(ClassNotFoundException e){
            rtcout.println(rtcout.WARN, 
                   "Exception caught."+e.toString());
        }
        catch(NoSuchFieldException e){
            rtcout.println(rtcout.WARN, 
                   "Exception caught."+e.toString());
        }
        catch(IllegalAccessException e){
            rtcout.println(rtcout.WARN, 
                   "Exception caught."+e.toString());
        }
        catch(NoSuchMethodException e){
            rtcout.println(rtcout.WARN, 
                   "Exception caught."+e.toString());
        }
        catch(IllegalArgumentException e){
            rtcout.println(rtcout.WARN, 
                   "Exception caught."+e.toString());
        }
    }
    
    /**
     * <p>データを書き込みます。<p>
     * 
     * @param value 書き込むデータ
     * @return データを書き込めた場合はtrueを、さもなくばfalseを返します。
     */
    public boolean write(final DataType value) {
        if (m_OnWrite != null) {
	    m_OnWrite.run(value);
	}

        // check number of connectors
        int conn_size = m_connectors.size();
        if (!(conn_size > 0)) { 
            return true; 
        }
        
        // set timestamp
//        set_timestamp(value);

        // data -> (conversion) -> CDR stream
        org.omg.CORBA.Any any = ORBUtil.getOrb().create_any();
        m_cdr = any.create_output_stream();


        if (m_OnWriteConvert != null) {
            DataType convervalue = m_OnWriteConvert.run(value);
            write_stream(convervalue,m_cdr); 
        }
        else {
            write_stream(value,m_cdr);
        }

        boolean result = true;
        for (int i=0, len=conn_size; i < len; ++i) {
            ReturnCode ret;
            ret = m_connectors.elementAt(i).write(m_cdr);
            if (ret != ReturnCode.PORT_OK) {
                result = false;
                if (ret.equals(ReturnCode.CONNECTION_LOST)) {
                    disconnect(m_connectors.elementAt(i).id());
                }
            }
        }
        return result;
    }
    
    /**
     * <p>データを書き込みます。本ポートにバインドされているデータ変数の値が書き込まれます。</p>
     * 
     * @return データを書き込めた場合はtrueを、さもなくばfalseを返します。
     */
    public boolean write() {
        return this.write(m_value.v);
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
     * <p> Set OnConnect callback </p>
     * 
     */
    public void setOnConnect(OnConnect on_connect) {
        m_OnConnect = on_connect;
    }
    /**
     * <p> Set OnDisConnect callback </p>
     * 
     */
    public void setOnDisconnect(OnDisconnect on_disconnect) {
        m_OnDisconnect = on_disconnect;
    }
    /**
     * 
     */
    public void onConnect(final String id, PublisherBase publisher) {
        rtcout.println(rtcout.TRACE, "onConnect(id = "+id+")");
    }
    /**
     * 
     */
    public void onDisconnect(final String id) {
        rtcout.println(rtcout.TRACE, "onDisconnect(id = "+id+")");
    }

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

    private OnConnect m_OnConnect;
    private OnDisconnect m_OnDisconnect;
    private OutputStream m_cdr;

    private Streamable m_streamable = null;
    private Object m_object = null;
    private Field m_field = null;
    private Method m_method = null;
}
