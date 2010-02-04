package jp.go.aist.rtm.RTC.port.publisher;

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import java.util.Vector;
import java.util.Set;

import jp.go.aist.rtm.RTC.PeriodicTaskFactory;
import jp.go.aist.rtm.RTC.PublisherBaseFactory;
import jp.go.aist.rtm.RTC.ObjectCreator;
import jp.go.aist.rtm.RTC.ObjectDestructor;
import jp.go.aist.rtm.RTC.PeriodicTaskBase;
import jp.go.aist.rtm.RTC.TaskFuncBase;
import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.port.InPortConsumer;
import jp.go.aist.rtm.RTC.port.ReturnCode;
import jp.go.aist.rtm.RTC.port.ConnectorListenerType;
import jp.go.aist.rtm.RTC.port.ConnectorDataListenerType;
import jp.go.aist.rtm.RTC.port.ConnectorListeners;
import jp.go.aist.rtm.RTC.port.ConnectorBase;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.StringUtil;
import jp.go.aist.rtm.RTC.log.Logbuf;
/**
 * <p>一定時間おきにコンシューマの送出処理を呼び出すPublisherです。</p>
 */
public class PublisherPeriodic extends PublisherBase implements Runnable, ObjectCreator<PublisherBase>, ObjectDestructor{

    /**
     * <p>コンストラクタです。</p>
     * 
     * <p>送出処理の呼び出し間隔を、Propertyオブジェクトのdataport.push_rateメンバに
     * 設定しておく必要があります。間隔は、Hz単位の浮動小数文字列で指定します。
     * たとえば、1000.0Hzの場合は、「1000.0」を設定します。</p>
     * 
     */
    public PublisherPeriodic() {
        rtcout = new Logbuf("PublisherPeriodic");
        m_consumer = null;
        m_buffer = null;
        m_task = null;
        m_retcode = ReturnCode.PORT_OK;
        m_pushPolicy = Policy.NEW;
        m_skipn = 0;
        m_active = false;
        m_readback = false;
        m_leftskip = 0;
    }
    
    /**
     * <p>本Publisher実装では、何も行いません。</p>
     */
    public void update() {
    }
    
    /**
     * <p>当該Publisherを駆動するスレッドコンテキストです。コンシューマの送出処理が呼び出されます。</p>
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

        while (m_buffer.readable() > 0) {
            final OutputStream cdr = m_buffer.get();
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
    /**
     * <p> pushFifo </p>
     * <p> push "fifo" policy </p>
     *
     * @return ReturnCode
     */
    protected ReturnCode pushFifo() {
        rtcout.println(rtcout.TRACE, "pushFifo()");

        if (m_buffer.empty() && !m_readback) {
            rtcout.println(rtcout.DEBUG, "buffer empty");
            return ReturnCode.BUFFER_EMPTY;
        }
        final OutputStream cdr = m_buffer.get();
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
    /**
     * <p> pushSkip </p>
     * <p> push "skip" policy </p>
     *
     * @return ReturnCode
     */
    protected ReturnCode pushSkip() {
        rtcout.println(rtcout.TRACE, "pushSkip()");
        if (m_buffer.empty() && !m_readback) {
            rtcout.println(rtcout.DEBUG, "buffer empty");
            return ReturnCode.BUFFER_EMPTY;
        }

        ReturnCode ret = ReturnCode.PORT_OK;
        int preskip = m_buffer.readable() + m_leftskip;
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
        m_leftskip = preskip % (m_skipn +1);
        return ret;
    }
    /**
     * <p> pushNew </p>
     * <p> push "new" policy </p>
     *
     * @return ReturnCode
     */
    protected ReturnCode pushNew() {
        rtcout.println(rtcout.TRACE, "pushNew()");

        if (m_buffer.empty() && !m_readback) {
            rtcout.println(rtcout.DEBUG, "buffer empty");
            return ReturnCode.BUFFER_EMPTY;
        }
    
        m_buffer.advanceRptr(m_buffer.readable() - 1);
    
        final OutputStream cdr = m_buffer.get();
        onBufferRead(cdr);

        onSend(cdr);
        ReturnCode ret = m_consumer.put(cdr);
        if (!ret.equals(ReturnCode.PORT_OK)) {
            rtcout.println(rtcout.DEBUG, ret +  " = consumer.put()");
            return invokeListener(ret, cdr);
        }

        onReceived(cdr);
        m_buffer.advanceRptr();
        return ret;
    }
    
    /**
     * <p>当該Publisherを駆動するスレッドコンテキストです。コンシューマの送出処理が呼び出されます。</p>
     */
    public void run() {
        this.svc();
    }

    /**
     * <p>当該Publisherの駆動を開始します。</p>
     */
    public int open() {
        m_running = true;
        Thread t = new Thread(this);
        t.start();
        
        return 0;
    }
    
    /**
     * <p>駆動フラグがオフとなり、Publisherの駆動が停止します。</p>
     * 
     * <p>ただし、最大１回のみコンシューマの送出処理が呼び出されることがあります。</p>
     */
    public void release() {
        m_running = false;
    }
    
    private InPortConsumer m_consumer;
    private boolean m_running;
    private long m_millisec;
    private int m_nanosec;

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
        rtcout.println(rtcout.PARANOID, str);
    
        setPushPolicy(prop);
        if (!createTask(prop)) {
            return ReturnCode.INVALID_ARGS;
        }
        return ReturnCode.PORT_OK;
    }

    /**
     * <p> Setting PushPolicy </p>
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
    
        // setting task function
        m_task.setTask(this);

        // Task execution rate
        String rate = prop.getProperty("publisher.push_rate");
        if(rate.equals("")){
            rate = prop.getProperty("push_rate");
            if(rate.equals("")){
                rtcout.println(rtcout.ERROR, 
                        "publisher.push_rate/push_rate were not found." );
                return false;
            }
        }
        double hz;
        hz = Double.valueOf(rate).doubleValue();
        if (hz <= 0) {
            rtcout.println(rtcout.ERROR, 
                        "invalid period: "+hz+"[s]" );
            return false;
        }
        m_task.setPeriod(1.0/hz);

        Properties mprop = prop.getNode("measurement");

    
        // Setting task measurement function
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
     * Setting buffer pointer
     */
    public ReturnCode setListener(ConnectorBase.ConnectorInfo info,
                           ConnectorListeners listeners) {
        rtcout.println(rtcout.TRACE, "setListeners()" );

        if (listeners == null) {
            rtcout.println(rtcout.ERROR, 
                           "setListeners(listeners == 0): invalid argument" );
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
    public ReturnCode write(final OutputStream data, int sec, int usec) {
        rtcout.println(rtcout.PARANOID, "write()" );
        if (m_consumer == null) { return ReturnCode.PRECONDITION_NOT_MET; }
        if (m_buffer == null) { return ReturnCode.PRECONDITION_NOT_MET; }
        if (m_retcode.equals(ReturnCode.CONNECTION_LOST)) {
            rtcout.println(rtcout.DEBUG, "write(): connection lost." );
            return m_retcode;
        }
    
        if (m_retcode.equals(ReturnCode.BUFFER_FULL)) {
            rtcout.println(rtcout.DEBUG, "write(): InPort buffer is full." );
            m_buffer.write(data, sec, usec);
            return ReturnCode.BUFFER_FULL;
        }
    
        onBufferWrite(data);
        jp.go.aist.rtm.RTC.buffer.ReturnCode ret;
        ret = m_buffer.write(data, sec, usec);
        rtcout.println(rtcout.DEBUG, ret.name() +" = write()" );
        m_task._resume();
    
        return convertReturn(ret,data);
    }
    public ReturnCode write(final OutputStream data) {
        return this.write(data,-1,0);
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
        if (m_task == null) { return ReturnCode.PRECONDITION_NOT_MET; }
        if (m_buffer == null) { return ReturnCode.PRECONDITION_NOT_MET; }
        m_active = true;
        m_task._resume();
        return ReturnCode.PORT_OK;
    }
    /**
     * <p> deactivate </p>
     *
     * @return ReturnCode 
     */
    public ReturnCode deactivate() {
        if (m_task == null) { return ReturnCode.PRECONDITION_NOT_MET; }
        m_active = false;
        m_task._suspend();
        return ReturnCode.PORT_OK;
    }

    /**
     * <p> creator_ </p>
     * 
     * @return Object Created instances
     *
     */
    public PublisherBase creator_() {
        return new PublisherPeriodic();
    }
    /**
     * <p> destructor_ </p>
     * 
     * @param obj    The target instances for destruction
     *
     */
    public void destructor_(Object obj) {
        PublisherPeriodic pb = (PublisherPeriodic)obj; 
        if (pb.m_task != null) {
            PeriodicTaskFactory<PeriodicTaskBase,String> factory 
                = PeriodicTaskFactory.instance();
            factory .deleteObject(pb.m_task);
        }
        obj = null;
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
     * <p> PublisherPeriodicInit </p>
     *
     */
    public static void PublisherPeriodicInit() {
        final PublisherBaseFactory<PublisherBase,String> factory 
            = PublisherBaseFactory.instance();

        factory.addFactory(id_name,
                    new PublisherPeriodic(),
                    new PublisherPeriodic());
    
    }
    /**
     * <p> getName </p>
     *
     */
    public String getName() {
        return id_name;
    }
    private static final String id_name = "periodic";

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
    protected void onBufferWrite(final OutputStream data) {
        m_listeners.connectorData_[ConnectorDataListenerType.ON_BUFFER_WRITE].notify(m_profile, data);
    }

    protected void onBufferFull(final OutputStream data) {
        m_listeners.connectorData_[ConnectorDataListenerType.ON_BUFFER_FULL].notify(m_profile, data);
    }

    protected void onBufferWriteTimeout(final OutputStream data) {
        m_listeners.connectorData_[ConnectorDataListenerType.ON_BUFFER_WRITE_TIMEOUT].notify(m_profile, data);
    }

//    protected void onBufferWriteOverwrite(final OutputStream data) {
//        m_listeners.connectorData_[ConnectorListenerType.ON_BUFFER_OVERWRITE].notify(m_profile, data);
//    }

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
    protected void onBufferEmpty() {
        m_listeners.connector_[ConnectorListenerType.ON_BUFFER_EMPTY].notify(m_profile);
    }

//    protected void onBufferReadTimeout() {
//        m_listeners.connector_[ConnectorListenerType.ON_BUFFER_READ_TIMEOUT].notify(m_profile);
//    }

    protected void onSenderEmpty() {
        m_listeners.connector_[ConnectorListenerType.ON_SENDER_EMPTY].notify(m_profile);
    }

//    protected void onSenderTimeout() {
//        m_listeners.connector_[ConnectorListenerType.ON_SENDER_TIMEOUT].notify(m_profile);
//    }

    protected void onSenderError() {
        m_listeners.connector_[ConnectorListenerType.ON_SENDER_ERROR].notify(m_profile);
    }

    private boolean m_active;
    private Policy m_pushPolicy;
    private BufferBase<OutputStream> m_buffer;
    private int m_skipn;
    private PeriodicTaskBase m_task;
    private ReturnCode m_retcode;
    private int m_leftskip;
    private String m_retmutex = new String();;
    private boolean m_readback;
    private ConnectorListeners m_listeners = new  ConnectorListeners();
    private ConnectorBase.ConnectorInfo m_profile;
}
