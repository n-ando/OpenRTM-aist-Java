package jp.go.aist.rtm.RTC.port.publisher;

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import java.util.Vector;
import java.util.Set;
import java.lang.Thread;

import jp.go.aist.rtm.RTC.FactoryGlobal;
import jp.go.aist.rtm.RTC.ObjectCreator;
import jp.go.aist.rtm.RTC.ObjectDestructor;
import jp.go.aist.rtm.RTC.PeriodicTaskBase;
import jp.go.aist.rtm.RTC.TaskFuncBase;
import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.port.InPortConsumer;
import jp.go.aist.rtm.RTC.port.ReturnCode;
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
     * <p>コンストラクタです。</p>
     * 
     */
    public PublisherNew() {
        rtcout = new Logbuf("Manager.PublisherNew");
        m_consumer = null;
        m_buffer = null;
        m_task = null;
        m_retcode = ReturnCode.PORT_OK;
        m_pushPolicy = Policy.NEW;
        m_skipn = 0;
        m_active = false;
        m_leftskip = 0;
        rtcout.setLevel("PARANOID");
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
                ReturnCode ret = m_consumer.put(cdr);
            
                if (ret.equals(ReturnCode.SEND_FULL)) {
                    return ReturnCode.SEND_FULL;
                }
                else if (!ret.equals(ReturnCode.PORT_OK)) {
                    return ret;
                }
            
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
            ReturnCode ret = m_consumer.put(cdr);
        
            if (ret.equals(ReturnCode.SEND_FULL)) {
                return ReturnCode.SEND_FULL;
            }
            else if (!ret.equals(ReturnCode.PORT_OK)) {
                return ret;
            }
        
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
                ret = m_consumer.put(cdr);
                if (ret != ReturnCode.PORT_OK) {
                    m_buffer.advanceRptr(-postskip);
                    return ret;
                }
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
            ReturnCode ret = m_consumer.put(cdr);

            if (ret.equals(ReturnCode.PORT_OK)) {
                m_buffer.advanceRptr();
            }
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
        rtcout.println(rtcout.PARANOID, str);
    
        // push_policy default: NEW
        String push_policy = prop.getProperty("publisher.push_policy", "new");
        rtcout.println(rtcout.DEBUG, "push_policy: " + push_policy );
    
        // skip_count default: 0
        String skip_count = prop.getProperty("publisher.skip_count", "0");
        rtcout.println(rtcout.DEBUG, "skip_count: " + skip_count );
    
        StringUtil.normalize(push_policy);
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
    
        FactoryGlobal<PeriodicTaskBase,String> factory 
            = FactoryGlobal.instance();
    
        Set hs = factory.getIdentifiers();
        rtcout.println(rtcout.DEBUG, 
                       "available task types: " + hs.toString());
    
        m_task = factory.createObject(prop.getProperty(
                                                   "thread_type", "default"));
        if (m_task == null) {
            rtcout.println(rtcout.ERROR, 
                           "Task creation failed: " 
                           + prop.getProperty("thread_type", "default"));
            return ReturnCode.INVALID_ARGS;
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
        return ReturnCode.PORT_OK;
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
            jp.go.aist.rtm.RTC.buffer.ReturnCode ret;
            ret  = m_buffer.write(data, sec, usec);
            m_task.signal();
            return ReturnCode.BUFFER_FULL;
        }
    
        assert m_buffer != null;
    
        jp.go.aist.rtm.RTC.buffer.ReturnCode ret;
        ret = m_buffer.write(data, sec, usec);
    
        m_task.signal();
        rtcout.println(rtcout.DEBUG, ret.name() +" = write()" );
    
        return convertReturn(ret);
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
        obj = null;
    }
    /**
     * <p> PublisherNewInit </p>
     *
     */
    public static void PublisherNewInit() {
        final FactoryGlobal<PublisherBase,String> factory 
            = FactoryGlobal.instance();

        factory.addFactory("new",
                    new PublisherNew(),
                    new PublisherNew());
    
    }
    /**
     * <p> convertReturn </p>
     *
     */
    protected ReturnCode convertReturn(jp.go.aist.rtm.RTC.buffer.ReturnCode status) {
        switch (status) {
            case BUFFER_OK:
                return ReturnCode.PORT_OK;
            case BUFFER_EMPTY:
                return ReturnCode.BUFFER_EMPTY;
            case TIMEOUT:
                return ReturnCode.BUFFER_TIMEOUT;
            case PRECONDITION_NOT_MET:
                return ReturnCode.PRECONDITION_NOT_MET;
            default:
                return ReturnCode.PORT_ERROR;
        }
    }

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
    private Policy m_pushPolicy;
    private int m_skipn;
    private BufferBase<OutputStream> m_buffer;
    private PeriodicTaskBase m_task;
    private ReturnCode m_retcode;
    private int m_leftskip;
    private String m_retmutex = new String();;
}
