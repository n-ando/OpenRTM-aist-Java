package jp.go.aist.rtm.RTC.port.publisher;

import jp.go.aist.rtm.RTC.port.InPortConsumer;
import jp.go.aist.rtm.RTC.util.Properties;

/**
 * <p>一定時間おきにコンシューマの送出処理を呼び出すPublisherです。</p>
 */
public class PublisherPeriodic extends PublisherBase implements Runnable {

    /**
     * <p>コンストラクタです。</p>
     * 
     * <p>送出処理の呼び出し間隔を、Propertyオブジェクトのdataport.push_rateメンバに
     * 設定しておく必要があります。間隔は、Hz単位の浮動小数文字列で指定します。
     * たとえば、1000.0Hzの場合は、「1000.0」を設定します。</p>
     * 
     * @param consumer 送出の駆動を待つコンシューマ
     * @param property 送出処理の呼び出し間隔を指定するPropertyオブジェクト
     */
    public PublisherPeriodic(InPortConsumer consumer, final Properties property) {
        
        m_consumer = consumer;
        
        String rate = property.getProperty("dataport.push_rate");
        double hz;
        if (!rate.equals("")) {
            hz = Double.valueOf(rate).doubleValue();
            if (hz == 0) {
                hz = 1000.0;
            }
        } else {
            hz = 1000.0;
        }
        
        int usec = (int) (1000000.0 / hz);
        m_millisec = usec / 1000;
        m_nanosec = (usec % 1000) * 1000;
        
        this.open();
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
        
        while (m_running) {
            
            m_consumer.push();
            
            try {
                Thread.sleep(m_millisec, m_nanosec);
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        return 0;
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

}
