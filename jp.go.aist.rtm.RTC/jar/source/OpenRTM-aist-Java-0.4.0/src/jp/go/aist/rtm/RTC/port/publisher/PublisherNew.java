package jp.go.aist.rtm.RTC.port.publisher;

import jp.go.aist.rtm.RTC.port.InPortConsumer;
import jp.go.aist.rtm.RTC.util.Properties;

/**
 * <p>データ送出タイミングを待つコンシューマを、送出する側とは異なるスレッドで動作させる場合に使用します。</p>
 * 
 * <p>Publisherの駆動は、データ送出のタイミングになるまでブロックされ、送出タイミングの通知を受けると、
 * 即座にコンシューマの送出処理を呼び出します。</p>
 */
public class PublisherNew extends PublisherBase implements Runnable {

    /**
     * <p>コンストラクタです。</p>
     * 
     * @param consumer 送出の駆動を待つコンシューマ
     * @param property （本Publisherでは利用されません。）
     */
    public PublisherNew(InPortConsumer consumer, final Properties property) {
        
        this.m_consumer = consumer;
        this.m_running = true;
        this.m_data = new NewData();
        
        open(null);
    }
    
    /**
     * <p>送出タイミング時に呼び出します。ブロックしている当該Publisherの駆動が開始され、
     * コンシューマへの送出処理が行われます。</p>
     */
    public void update() {
        
        synchronized (this.m_data) {
            
            this.m_data._updated = true;
            this.m_data.notify();
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
        
        while (this.m_running) {
            
            synchronized (this.m_data) {
                
                // Waiting for new data updated
                while (! this.m_data._updated && this.m_running) {
                    try {
                        this.m_data.wait();
                    } catch (Exception ignored) {
                        ignored.printStackTrace();
                    }
                }
                
                if (this.m_data._updated) {
                    this.m_consumer.push();
                    this.m_data._updated = false;
                }
            }
        }
        
        return 0;
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
    
    private NewData m_data;
}
