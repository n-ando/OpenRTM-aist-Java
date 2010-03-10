package jp.go.aist.rtm.RTC.port;

import java.util.Vector;

import org.omg.CORBA.portable.Streamable;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import com.sun.corba.se.impl.encoding.EncapsOutputStream; 

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
import jp.go.aist.rtm.RTC.util.NVUtil;
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
        String typeName = value.v.getClass().getSimpleName();
        return typeName;

    }
    /**
     * <p> write_stream </p>
     * <p> This function writes data from OutputStream.  </p>
     *
     * @param data  data   
     * @param cdr   OutputStream
     */
    public void write_stream(DataType data,OutputStream cdr) {
        try {
            m_field.set(m_streamable,data);
            m_streamable._write(cdr);

        }
        catch(IllegalAccessException e){
            rtcout.println(rtcout.WARN, 
                   "Exception caught."+e.toString());
        }
        catch(IllegalArgumentException e){
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

        m_spi_orb = (com.sun.corba.se.spi.orb.ORB)ORBUtil.getOrb();

        Class cl = valueRef.v.getClass();
        String str = cl.getName();
        try {
            Class holder = Class.forName(str+"Holder",
                                         true,
                                         this.getClass().getClassLoader());
            m_streamable = (Streamable)holder.newInstance();
            m_field = m_streamable.getClass().getField("value");

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
        catch(IllegalArgumentException e){
            rtcout.println(rtcout.WARN, 
                   "Exception caught."+e.toString());
        }
    }
    
    /**
     * {@.ja データ書き込み}
     * {@.en Write data}
     *
     * <p>
     * {@.ja ポートへデータを書き込む。
     * <ul>
     * <li> コールバックファンクタ OnWrite がセットされている場合、
     *      OutPort が保持するバッファに書き込む前に OnWrite が呼ばれる。</li>
     * <li> OutPort が保持するバッファがオーバーフローを検出できるバッファであ
     *      り、かつ、書き込む際にバッファがオーバーフローを検出した場合、
     *      コールバックファンクタ OnOverflow が呼ばれる。</li>
     * <li> コールバックファンクタ OnWriteConvert がセットされている場合、
     *      バッファ書き込み時に、 OnWriteConvert の operator() の戻り値が
     *      バッファに書き込まれる。</li>
     * </ul>}
     * {@.en Write data in the port.
     * <ul>
     * <li> When callback functor OnWrite is already set, OnWrite will be
     *      invoked before writing into the buffer held by OutPort.
     * <li> When the buffer held by OutPort can detect the overflow,
     *      and when it detected the overflow at writing, callback functor
     *      OnOverflow will be invoked.
     * <li> When callback functor OnWriteConvert is already set, 
     *      the return value of operator() of OnWriteConvert will be written
     *      into the buffer at the writing.
     * </ul>}
     * </p>
     * @param value 
     *   {@.ja 書き込み対象データ}
     *   {@.en The target data for writing}
     *
     * @return 
     *   {@.ja 書き込み処理結果(書き込み成功:true、書き込み失敗:false)}
     *   {@.en Writing result (Successful:true, Failed:false)}
     *
     */
    public boolean write(final DataType value) {
        rtcout.println(rtcout.TRACE, "DataType write()");
        synchronized (m_connectors){
            if (m_OnWrite != null) {
                m_OnWrite.run(value);
            }

            // check number of connectors
            int conn_size = m_connectors.size();
            if (!(conn_size > 0)) { 
                return false; 
            }
        
            // set timestamp
//            set_timestamp(value);

            // data -> (conversion) -> CDR stream


            DataType convervalue = value;
            if (m_OnWriteConvert != null) {
                convervalue = m_OnWriteConvert.run(value);
            }


            boolean result = true;
            m_status.setSize(conn_size);
            for (int i=0, len=conn_size; i < len; ++i) {
                ReturnCode ret = m_connectors.elementAt(i).write(convervalue);
                m_status.add(i, ret);
                if (ret.equals(ReturnCode.PORT_OK)) {
                    continue;
                }

                result = false;
                String id = m_connectors.elementAt(i).id();
                RTC.ConnectorProfile prof = findConnProfile(id);

                if (ret.equals(ReturnCode.CONNECTION_LOST)) {
                    rtcout.println(rtcout.TRACE, "connection_lost id: "+id);
                    if(m_onConnectionLost != null) {
                        RTC.ConnectorProfileHolder holder 
                            = new RTC.ConnectorProfileHolder(prof);
                        m_onConnectionLost.run(holder);
                    }
                    disconnect(m_connectors.elementAt(i).id());
                }
            }
            return result;
        }
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
     * {@.ja データ書き込み}
     * {@.en Write data}
     * <p>
     * {@.ja ポートへデータを書き込む。設定された値をポートに書き込む。}
     * {@.en  Write data to the port. Write the set value to the port.}
     * </p>
     * @param value 
     *   {@.ja 書き込み対象データ}
     *   {@.en The target data for writing}
     *
     * @return  
     *   {@.ja 書き込み処理結果(書き込み成功:true、書き込み失敗:false)}
     *   {@.en Writing result (Successful:true, Failed:false)}
     *
     */
    public boolean insert(DataType value) {
        return write(value);
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
     * {@.ja 特定のコネクタへの書き込みステータスを得る}
     * {@.en brief Getting specified connector's writing status}
     *
     * <p>
     * {@.ja OutPort は接続ごとに Connector と呼ばれる仮想データチャネルを持
     * つ。write() 関数はこれら Connector に対してデータを書き込むが、
     * 各 Connector は書き込みごとにステータスを返す。write() 関数では、
     * すべての Connector が正常終了したときのみ true を返し、それ以外
     * では false を返却する。この関数は write() が false の場合ステー
     * タスを調べるのに使用することができる。}
     * {@.en An OutPort has Connectors that are virtual data stream channel
     * for each connection.  "write()" function write into these
     * Connectors, and each Connector returns writing-status.  write()
     * function will return a true value if all Connectors return
     * normal status, and a false value will be returned if at least
     * one Connector failed.  This function can be used to inspect
     * each return status}
     * </p>
     * 
     * @param index 
     *   {@.ja Connector の index}
     *   {@.en Connector index}
     * @return 
     *   {@.ja ステータス}
     *   {@.en Writing status}
     *
     */
    ReturnCode getStatus(int index) {
        return m_status.elementAt(index);
    }

    /**
     * {@.ja 特定のコネクタへの書き込みステータスリストを得る}
     * {@.en Getting specified connector's writing status list}
     *
     * <p>
     * {@.ja OutPort は接続ごとに Connector と呼ばれる仮想データチャネルを持
     * つ。write() 関数はこれら Connector に対してデータを書き込むが、
     * 各 Connector は書き込みごとにステータスを返す。write() 関数では、
     * すべての Connector が正常終了したときのみ true を返し、それ以外
     * では false を返却する。この関数は write() が false の場合ステー
     * タスを調べるのに使用することができる。}
     * {@.en An OutPort has Connectors that are virtual data stream channel
     * for each connection.  "write()" function write into these
     * Connectors, and each Connector returns writing-status.  write()
     * function will return a true value if all Connectors return
     * normal status, and a false value will be returned if at least
     * one Connector failed.  This function can be used to inspect
     * each return status}
     * </p>
     * 
     * @return 
     *   {@.ja ステータスリスト}
     *   {@.en Writing status list}
     */
    Vector<ReturnCode> getStatusList() {
        return m_status;
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

    private Streamable m_streamable = null;
    private Field m_field = null;
    private com.sun.corba.se.spi.orb.ORB m_spi_orb;
    private Vector<ReturnCode> m_status = new Vector<ReturnCode>();
}
