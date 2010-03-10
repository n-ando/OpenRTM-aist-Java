package jp.go.aist.rtm.RTC.port.publisher;

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import java.util.Vector;
import java.util.Set;
import java.lang.Thread;

import jp.go.aist.rtm.RTC.PeriodicTaskFactory;
import jp.go.aist.rtm.RTC.PublisherBaseFactory;
import jp.go.aist.rtm.RTC.ObjectCreator;
import jp.go.aist.rtm.RTC.ObjectDestructor;
import jp.go.aist.rtm.RTC.PeriodicTaskBase;
import jp.go.aist.rtm.RTC.TaskFuncBase;
import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.port.InPortConsumer;
import jp.go.aist.rtm.RTC.port.ReturnCode;
import jp.go.aist.rtm.RTC.port.ConnectorListeners;
import jp.go.aist.rtm.RTC.port.ConnectorDataListenerType;
import jp.go.aist.rtm.RTC.port.ConnectorListenerType;
import jp.go.aist.rtm.RTC.port.ConnectorBase;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.StringUtil;
import jp.go.aist.rtm.RTC.log.Logbuf;

/**
 * <p>データ送出タイミングを待つコンシューマを、送出する側とは異なるスレッドで動作させる場合に使用します。</p>
 * 
 * <p>Publisherの駆動は、データ送出のタイミングになるまでブロックされ、送出タイミングの通知を受けると、
 * 即座にコンシューマの送出処理を呼び出します。</p>
 */
public class PublisherNew extends PublisherBase implements Runnable, ObjectCreator<PublisherBase>, ObjectDestructor{
    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     */
    public PublisherNew() {
        rtcout = new Logbuf("PublisherNew");
        m_consumer = null;
        m_buffer = null;
        m_task = null;
        m_retcode = ReturnCode.PORT_OK;
        m_pushPolicy = Policy.NEW;
        m_skipn = 0;
        m_active = false;
        m_leftskip = 0;
        m_listeners = null;
    }
    
    /**
     * <p>送出タイミング時に呼び出します。ブロックしている当該Publisherの駆動が開始され、
     * コンシューマへの送出処理が行われます。</p>
     */
    public void update() {
        synchronized (this.m_data) {
            
            this.m_data._updated = true;
            try{
                this.m_data.notify();
            } catch(Exception ex) {
            }
        }
        
        Thread.yield();
        try {
            Thread.sleep(0, 100000);
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }

    /**
     * <p>当該Publisherオブジェクトのスレッドコンテキストです。
     * 送出タイミングが通知されるまでブロックします。</p>
     */
    public int svc() {
        synchronized (m_retmutex) {
            switch (m_pushPolicy) {
                case ALL:
                    m_retcode = pushAll();
                    break;
                case FIFO:
                    m_retcode = pushFifo();
                    break;
                case SKIP:
                    m_retcode = pushSkip();
                    break;
                case NEW:
                    m_retcode = pushNew();
                    break;
                default:
                    m_retcode = pushNew();
                    break;
            }
        }
        return 0;
    }
    /**
     * <p> pushAll </p>
     * <p> push all policy </p>
     *
     * @return ReturnCode
     */
    protected ReturnCode pushAll() {
        rtcout.println(rtcout.TRACE, "pushAll()");
        try {
            while (m_buffer.readable() > 0) {
                OutputStream cdr = m_buffer.get();
                onBufferRead(cdr);

                onSend(cdr);
                ReturnCode ret = m_consumer.put(cdr);
            
                if (!ret.equals(ReturnCode.PORT_OK)) {
                    rtcout.println(rtcout.DEBUG, ret + " = consumer.put()");
                    return invokeListener(ret, cdr);
                }
                onReceived(cdr);

                m_buffer.advanceRptr();
            }
            return ReturnCode.PORT_OK;
        }
        catch (Exception e) {
            return ReturnCode.CONNECTION_LOST;
        }
    }
    /**
     * <p> pushFifo </p>
     * <p> push "fifo" policy </p>
     *
     * @return ReturnCode
     */
    protected ReturnCode pushFifo() {
        rtcout.println(rtcout.TRACE, "pushFifo()");
        try {
            OutputStream cdr = m_buffer.get();
            onBufferRead(cdr);

            onSend(cdr);
            ReturnCode ret = m_consumer.put(cdr);
        
            if (!ret.equals(ReturnCode.PORT_OK)) {
                rtcout.println(rtcout.DEBUG, ret + " = consumer.put()");
                return invokeListener(ret, cdr);
            }
            onReceived(cdr);

            m_buffer.advanceRptr();
        
            return ret;
        }
        catch (Exception e) {
            return ReturnCode.CONNECTION_LOST;
        }
    }
    /**
     * <p> pushSkip </p>
     * <p> push "skip" policy </p>
     *
     * @return ReturnCode
     */
    protected ReturnCode pushSkip() {
        rtcout.println(rtcout.TRACE, "pushSkip()");
        try {
            ReturnCode ret = ReturnCode.PORT_OK;
            int preskip = (m_buffer.readable() + m_leftskip);
            int loopcnt = preskip/(m_skipn +1);
            int postskip = m_skipn - m_leftskip;
            for (int i = 0; i < loopcnt; ++i) {
                m_buffer.advanceRptr(postskip);

                final OutputStream cdr = m_buffer.get();
                onBufferRead(cdr);
        
                onSend(cdr);
                ret = m_consumer.put(cdr);
                if (!ret.equals(ReturnCode.PORT_OK)) {
                    m_buffer.advanceRptr(-postskip);
                    rtcout.println(rtcout.DEBUG, ret + " = consumer.put()");
                    return invokeListener(ret, cdr);
                }
                onReceived(cdr);
                postskip = m_skipn +1;
            }
            m_buffer.advanceRptr(m_buffer.readable());
            if (loopcnt == 0) {  // Not put
                m_leftskip = preskip % (m_skipn +1);
            }
            else {
                if ( m_retcode != ReturnCode.PORT_OK ) {  // put Error after 
                    m_leftskip = 0;
                }
                else {  // put OK after
                    m_leftskip = preskip % (m_skipn +1);
                }
            }
            return ret;
        }
        catch (Exception e) {
            return ReturnCode.CONNECTION_LOST;
        }
    }
    /**
     * <p> pushNew </p>
     * <p> push "new" policy </p>
     *
     * @return ReturnCode
     */
    protected ReturnCode pushNew() {
        rtcout.println(rtcout.TRACE, "pushNew()");
        try {
            m_buffer.advanceRptr(m_buffer.readable() - 1);
        
            OutputStream cdr = m_buffer.get();
            onBufferRead(cdr);

            onSend(cdr);
            ReturnCode ret = m_consumer.put(cdr);

            if (!ret.equals(ReturnCode.PORT_OK)) {
                rtcout.println(rtcout.DEBUG, ret + " = consumer.put()");
                return invokeListener(ret, cdr);
            }
            onReceived(cdr);
            m_buffer.advanceRptr();
            return ret;
        }
        catch (Exception e) {
            return ReturnCode.CONNECTION_LOST;
        }
    }

    /**
     * <p>当該Publisherの駆動を開始します。</p>
     * 
     * @param args （本Publisherでは使用されません。）
     */
    public int open(Object[] args) {
        this.m_running = true;
        
        Thread thread = new Thread(this);
        thread.start();
        
        return 0;
    }
    
    /**
     * <p>駆動フラグがオフとなり、Publisherの駆動が停止します。</p>
     * 
     * <p>ただし、すでに駆動スレッドがブロックされている場合には、
     * 最大１回のみコンシューマの送出処理が呼び出されることがあります。</p>
     */
    public void release() {
        this.m_running = false;
        try {
            this.m_data.notify();
            this.m_data.wait();
        } catch (InterruptedException e) {
        } catch (IllegalMonitorStateException e) {
        }
    }
    
    /**
     * <p>当該Publisherオブジェクトのスレッドコンテキストです。
     * 送出タイミングが通知されるまでブロックします。</p>
     */
    public void run() {
        svc();
    }
    
    private InPortConsumer m_consumer;
    private boolean m_running;
    
    private class NewData {
        public boolean _updated = false;
    }
    
    // A condition variable for data update notification
    private NewData m_data;
    
    /**
     * <p> init </p>
     * <p> initialization </p>
     *
     * @param prop
     * @return ReturnCode
     */
    public ReturnCode init(Properties prop) {
        rtcout.println(rtcout.TRACE, "init()");
        String str = new String();
        prop._dump(str,prop,0);
        rtcout.println(rtcout.DEBUG, str);
    
        setPushPolicy(prop);
        if (!createTask(prop)) {
            return ReturnCode.INVALID_ARGS;
        }
        return ReturnCode.PORT_OK;
    }
    /**
     * <p> Setting PushPolicy </p>
     * @param prop Properties
     */
    protected void setPushPolicy(final Properties prop) {
        // push_policy default: NEW
        String push_policy = prop.getProperty("publisher.push_policy", "new");
        rtcout.println(rtcout.DEBUG, "push_policy: " + push_policy );
    
        push_policy = StringUtil.normalize(push_policy);
        if (push_policy.equals("all")) {
            m_pushPolicy = Policy.ALL;
        }
        else if (push_policy.equals("fifo")) {
            m_pushPolicy = Policy.FIFO;
        }
        else if (push_policy.equals("skip")) {
            m_pushPolicy = Policy.SKIP;
        }
        else if (push_policy.equals("new")) {
            m_pushPolicy = Policy.NEW;
        }
        else {
            rtcout.println(rtcout.ERROR, 
                           "invalid push_policy value: " + push_policy );
            m_pushPolicy = Policy.NEW;     // default push policy
        }

        // skip_count default: 0
        String skip_count = prop.getProperty("publisher.skip_count", "0");
        rtcout.println(rtcout.DEBUG, "skip_count: " + skip_count );
    
        try {
            m_skipn = Integer.parseInt(skip_count);
        }
        catch(NumberFormatException e){
            rtcout.println(rtcout.ERROR, 
                           "invalid skip_count value: " + skip_count );
            m_skipn = 0;           // default skip count
        }
        if (m_skipn < 0) {
            rtcout.println(rtcout.ERROR, 
                           "invalid skip_count value: " + m_skipn );
            m_skipn = 0;           // default skip count
        }
    }

    /**
     * <p> Setting Task </p>
     * @param prop Properties
     * @return false:Task creation failed
     */
    protected boolean createTask(final Properties prop) {
    
        PeriodicTaskFactory<PeriodicTaskBase,String> factory 
            = PeriodicTaskFactory.instance();
    
        Set hs = factory.getIdentifiers();
        rtcout.println(rtcout.DEBUG, 
                       "available task types: " + hs.toString());
    
        m_task = factory.createObject(prop.getProperty(
                                                   "thread_type", "default"));
        if (m_task == null) {
            rtcout.println(rtcout.ERROR, 
                           "Task creation failed: " 
                           + prop.getProperty("thread_type", "default"));
            return false;
        }
        rtcout.println(rtcout.PARANOID, "Task creation succeeded." );
    
        Properties mprop = prop.getNode("measurement");
    
        // setting task function
        m_task.setTask(this);
        m_task.setPeriod(0.0);
        m_task.executionMeasure(StringUtil.toBool(
                                        mprop.getProperty("exec_time"),
                                        "enable", "disable", true));
        
        int ecount;
        try {
            ecount = Integer.parseInt(mprop.getProperty("exec_count"));
            m_task.executionMeasureCount(ecount);
        }
        catch(NumberFormatException e){
        }
    
        m_task.periodicMeasure(StringUtil.toBool(
                                       mprop.getProperty("period_time"),
                                       "enable", "disable", true));
        int pcount;
        try {
            pcount = Integer.parseInt(mprop.getProperty("period_count"));
            m_task.periodicMeasureCount(pcount);
        }
        catch(NumberFormatException e){
        }
    
        m_task._suspend();
        m_task.activate();
        m_task._suspend();

        return true;
    }

    /**
     * <p> setConsumer </p>
     * <p> Store InPort consumer </p>
     *
     * @param consumer
     * @return ReturnCode
     */
    public ReturnCode setConsumer(InPortConsumer consumer) {
        rtcout.println(rtcout.TRACE, "setConsumer()" );
    
        if (consumer == null) {
            rtcout.println(rtcout.ERROR, 
                           "setConsumer(consumer = null): invalid argument." );
            return ReturnCode.INVALID_ARGS;
          }
        m_consumer = consumer;
        return ReturnCode.PORT_OK;
    }
    /**
     * <p> setBuffer </p>
     * <p> Setting buffer </p>
     *
     * @param buffer
     * @return ReturnCode
     */
    public ReturnCode setBuffer(BufferBase<OutputStream> buffer) {
        rtcout.println(rtcout.TRACE, "setBuffer()" );

        if (buffer == null)
          {
            rtcout.println(rtcout.ERROR, 
                           "setBuffer(buffer = null): invalid argument." );
            return ReturnCode.INVALID_ARGS;
          }
        m_buffer = buffer;
        return ReturnCode.PORT_OK;
    }
    /**
     * <p> Setting buffer pointer </p>
     */
    public ReturnCode setListener(ConnectorBase.ConnectorInfo info,
                           ConnectorListeners listeners) {
        rtcout.println(rtcout.TRACE, "setListeners()" );

        if (listeners == null) {
            rtcout.println(rtcout.ERROR, 
                           "setListeners(listeners == 0): invalid argument." );
            return ReturnCode.INVALID_ARGS;
          }
        m_profile = info;
        m_listeners = listeners;
        return ReturnCode.PORT_OK;
    }
    /**
     * <p> write </p>
     *
     * @param data
     * @param sec
     * @param usec
     * @return ReturnCode
     */
    /**
     * {@.ja データを書き込む}
     * {@.en Write data }
     *
     * <p>
     * {@.ja Publisher が保持するバッファに対してデータを書き込む。コンシュー
     * マ、バッファ、リスナ等が適切に設定されていない等、Publisher オブ
     * ジェクトが正しく初期化されていない場合、この関数を呼び出すとエラー
     * コード PRECONDITION_NOT_MET が返され、バッファへの書き込み等の操
     * 作は一切行われない。
     *
     * バッファへの書き込みと、InPortへのデータの送信は非同期的に行われ
     * るため、この関数は、InPortへのデータ送信の結果を示す、
     * CONNECTION_LOST, BUFFER_FULL などのリターンコードを返すことがあ
     * る。この場合、データのバッファへの書き込みは行われない。
     *
     * バッファへの書き込みに対して、バッファがフル状態、バッファのエ
     * ラー、バッファへの書き込みがタイムアウトした場合、バッファの事前
     * 条件が満たされない場合にはそれぞれ、エラーコード BUFFER_FULL,
     * BUFFER_ERROR, BUFFER_TIMEOUT, PRECONDITION_NOT_MET が返される。
     *
     * これら以外のエラーの場合、PORT_ERROR が返される。}
     * </p>
     * {@.en This function writes data into the buffer associated with this
     * Publisher.  If a Publisher object calls this function, without
     * initializing correctly such as a consumer, a buffer, listeners,
     * etc., error code PRECONDITION_NOT_MET will be returned and no
     * operation of the writing to a buffer etc. will be performed.
     *
     * Since writing into the buffer and sending data to InPort are
     * performed asynchronously, occasionally this function returns
     * return-codes such as CONNECTION_LOST and BUFFER_FULL that
     * indicate the result of sending data to InPort. In this case,
     * writing data into buffer will not be performed.
     *
     * When publisher writes data to the buffer, if the buffer is
     * filled, returns error, is returned with timeout and returns
     * precondition error, error codes BUFFER_FULL, BUFFER_ERROR,
     * BUFFER_TIMEOUT and PRECONDITION_NOT_MET will be returned
     * respectively.
     *
     * In other cases, PROT_ERROR will be returned.}
     * 
     *
     * @param data 
     *   {@.ja 書き込むデータ}
     *   {@.en Data to be wrote to the buffer}
     * @param sec 
     *   {@.ja タイムアウト時間}
     *   {@.en Timeout time in unit seconds}
     * @param nsec 
     *   {@.ja タイムアウト時間}
     *   {@.en Timeout time in unit nano-seconds}
     *
     * @return 
     *   {@.ja PORT_OK             正常終了
     *         PRECONDITION_NO_MET consumer, buffer, listener等が適切に設定
     *                             されていない等、このオブジェクトの事前条件
     *                             を満たさない場合。
     *         CONNECTION_LOST     接続が切断されたことを検知した。
     *         BUFFER_FULL         バッファがフル状態である。
     *         BUFFER_ERROR        バッファに何らかのエラーが生じた場合。
     *         NOT_SUPPORTED       サポートされない操作が行われた。
     *         TIMEOUT             タイムアウトした。}
     *
     *
     *
     *   {@.en PORT_OK             Normal return
     *         PRECONDITION_NO_MET Precondition does not met. A consumer,
     *                             a buffer, listenes are not set properly.
     *         CONNECTION_LOST     detected that the connection has been lost
     *         BUFFER_FULL         The buffer is full status.
     *         BUFFER_ERROR        Some kind of error occurred in the buffer.
     *         NOT_SUPPORTED       Some kind of operation that is not supported
     *                             has been performed.
     *         TIMEOUT             Timeout occurred when writing to the buffer.}
     *
     */
    public ReturnCode write(final OutputStream data, int sec, int usec) {
        rtcout.println(rtcout.PARANOID, "write()" );
        if (m_consumer == null) { return ReturnCode.PRECONDITION_NOT_MET; }
        if (m_buffer == null) { return ReturnCode.PRECONDITION_NOT_MET; }
        if (m_listeners == null) { return ReturnCode.PRECONDITION_NOT_MET; }
        if (m_retcode.equals(ReturnCode.CONNECTION_LOST)) {
            rtcout.println(rtcout.DEBUG, "write(): connection lost." );
            return m_retcode;
        }
    
        if (m_retcode.equals(ReturnCode.SEND_FULL)) {
            rtcout.println(rtcout.DEBUG, "write(): InPort buffer is full." );
            jp.go.aist.rtm.RTC.buffer.ReturnCode ret;
            ret  = m_buffer.write(data, sec, usec);
            m_task.signal();
            return ReturnCode.BUFFER_FULL;
        }
    
        assert m_buffer != null;
    
        onBufferWrite(data);
        jp.go.aist.rtm.RTC.buffer.ReturnCode ret;
        ret = m_buffer.write(data, sec, usec);
    
        m_task.signal();
        rtcout.println(rtcout.DEBUG, ret.name() +" = write()" );
    
        return convertReturn(ret,data);
    }
    public ReturnCode write(final OutputStream data) {
        return this.write(data, -1, 0);
    }
    /**
     * <p> write </p>
     *
     * @return boolean 
     */
    public boolean isActive() {
        return m_active;
    }
    /**
     * <p> activate </p>
     *
     * @return ReturnCode 
     */
    public ReturnCode activate() {
        m_active = true;
        return ReturnCode.PORT_OK;
    }
    /**
     * <p> deactivate </p>
     *
     * @return ReturnCode 
     */
    public ReturnCode deactivate() {
        m_active = false;
        return ReturnCode.PORT_OK;
    }

    /**
     * <p> creator_ </p>
     * 
     * @return Object Created instances
     *
     */
    public PublisherBase creator_() {
        return new PublisherNew();
    }
    /**
     * <p> destructor_ </p>
     * 
     * @param obj    The target instances for destruction
     *
     */
    public void destructor_(Object obj) {
        PublisherNew pb = (PublisherNew)obj; 
        if (pb.m_task != null) {
            PeriodicTaskFactory<PeriodicTaskBase,String> factory 
                = PeriodicTaskFactory.instance();
            factory .deleteObject(pb.m_task);
        }
        obj = null;
    }
    /**
     * <p> PublisherNewInit </p>
     *
     */
    public static void PublisherNewInit() {
        final PublisherBaseFactory<PublisherBase,String> factory 
            = PublisherBaseFactory.instance();

        factory.addFactory(id_name,
                    new PublisherNew(),
                    new PublisherNew());
    
    }
    /**
     * <p> convertReturn </p>
     *
     */
    protected ReturnCode convertReturn(
                                jp.go.aist.rtm.RTC.buffer.ReturnCode status, 
                                final OutputStream data) {
        switch (status) {
            case BUFFER_OK:
                return ReturnCode.PORT_OK;
            case BUFFER_ERROR:
                // no callback
                return ReturnCode.BUFFER_ERROR;
            case BUFFER_FULL:
                onBufferFull(data);
                return ReturnCode.BUFFER_FULL;
            case NOT_SUPPORTED:
                // no callback
                return ReturnCode.PORT_ERROR;
            case TIMEOUT:
                onBufferWriteTimeout(data);
                return ReturnCode.BUFFER_TIMEOUT;
            case PRECONDITION_NOT_MET:
                return ReturnCode.PRECONDITION_NOT_MET;
            default:
                return ReturnCode.PORT_ERROR;
        }
    }
    protected ReturnCode invokeListener(ReturnCode status,
                                    final OutputStream data) {
        // ret:
        // PORT_OK, PORT_ERROR, SEND_FULL, SEND_TIMEOUT, CONNECTION_LOST,
        // UNKNOWN_ERROR
        switch (status) {
            case PORT_ERROR:
                onReceiverError(data);
                return ReturnCode.PORT_ERROR;
        
            case SEND_FULL:
                onReceiverFull(data);
                return ReturnCode.SEND_FULL;
        
            case SEND_TIMEOUT:
                onReceiverTimeout(data);
                return ReturnCode.SEND_TIMEOUT;
        
            case CONNECTION_LOST:
                onReceiverError(data);
                return ReturnCode.CONNECTION_LOST;
        
            case UNKNOWN_ERROR:
                onReceiverError(data);
                return ReturnCode.UNKNOWN_ERROR;
        
            default:
                onReceiverError(data);
                return ReturnCode.PORT_ERROR;
       }
    }

    /**
     * <p> getName </p>
     *
     */
    public String getName() {
        return id_name;
    }
    private static final String id_name = "new";

    /**
     * <p>  </p>
     */
    private boolean m_active;
    /**
     * <p>  </p>
     */
    protected Logbuf rtcout;

    protected enum Policy {
        ALL,
        FIFO,
        SKIP,
        NEW
    }
    /**
     * <p> Connector data listener functions </p>
     */
    private void onBufferWrite(final OutputStream data) {
        m_listeners.connectorData_[ConnectorDataListenerType.ON_BUFFER_WRITE].notify(m_profile, data);
    }

    protected void onBufferFull(final OutputStream data) {
        m_listeners.connectorData_[ConnectorDataListenerType.ON_BUFFER_FULL].notify(m_profile, data);
    }

    protected void onBufferWriteTimeout(final OutputStream data) {
        m_listeners.connectorData_[ConnectorDataListenerType.ON_BUFFER_WRITE_TIMEOUT].notify(m_profile, data);
    }

    protected void onBufferWriteOverwrite(final OutputStream data) {
        m_listeners.connectorData_[ConnectorDataListenerType.ON_BUFFER_OVERWRITE].notify(m_profile, data);
    }

    protected void onBufferRead(final OutputStream data) {
        m_listeners.connectorData_[ConnectorDataListenerType.ON_BUFFER_READ].notify(m_profile, data);
    }

    protected void onSend(final OutputStream data) {
        m_listeners.connectorData_[ConnectorDataListenerType.ON_SEND].notify(m_profile, data);
    }

    protected void onReceived(final OutputStream data) {
        m_listeners.connectorData_[ConnectorDataListenerType.ON_RECEIVED].notify(m_profile, data);
    }

    protected void onReceiverFull(final OutputStream data) {
        m_listeners.connectorData_[ConnectorDataListenerType.ON_RECEIVER_FULL].notify(m_profile, data);
    }

    protected void onReceiverTimeout(final OutputStream data) {
        m_listeners.connectorData_[ConnectorDataListenerType.ON_RECEIVER_TIMEOUT].notify(m_profile, data);
    }

    protected void onReceiverError(final OutputStream data) {
        m_listeners.connectorData_[ConnectorDataListenerType.ON_RECEIVER_ERROR].notify(m_profile, data);
    }

    /**
     * <p> Connector listener functions </p>
     */
//    protected void onBufferEmpty() {
//        m_listeners.connector_[ConnectorDataListenerType.ON_BUFFER_EMPTY].notify(m_profile);
//    }

//    protected void onBufferReadTimeout() {
//        m_listeners.connector_[ConnectorDataListenerType.ON_BUFFER_READ_TIMEOUT].notify(m_profile);
//    }

//    protected void onSenderEmpty() {
//        m_listeners.connector_[ConnectorDataListenerType.ON_SENDER_EMPTY].notify(m_profile);
//    }

//    protected void onSenderTimeout() {
//        m_listeners.connector_[ConnectorDataListenerType.ON_SENDER_TIMEOUT].notify(m_profile);
//    }

    protected void onSenderError() {
        m_listeners.connector_[ConnectorListenerType.ON_SENDER_ERROR].notify(m_profile);
    }

    private Policy m_pushPolicy;
    private int m_skipn;
    private BufferBase<OutputStream> m_buffer;
    private PeriodicTaskBase m_task;
    private ReturnCode m_retcode;
    private int m_leftskip;
    private String m_retmutex = new String();;
//zxc    private ConnectorListeners m_listeners = new  ConnectorListeners();
    private ConnectorListeners m_listeners;
    private ConnectorBase.ConnectorInfo m_profile;
}
