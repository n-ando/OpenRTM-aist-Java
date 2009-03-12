package jp.go.aist.rtm.RTC.port.publisher;

import jp.go.aist.rtm.RTC.port.InPortConsumer;
import jp.go.aist.rtm.RTC.util.Properties;

/**
 * <p>データ送出を待つコンシューマを、送出する側と同じスレッドで動作させる場合に使用します。</p>
 */
public class PublisherFlush extends PublisherBase {

    /**
     * <p>コンストラクタです。</p>
     * 
     * @param consumer データ送出を待つコンシューマ
     * @param property 当該Publisherの駆動を制御する情報を持つPropertyオブジェクト
     */
    public PublisherFlush(InPortConsumer consumer, final Properties property) {
        
        m_consumer = consumer;
    }
    
    /**
     * <p>当該Publisherが不要となり破棄される際に、PublisherFactoryにより呼び出されます。</p>
     */
    public void destruct() {
        
        m_consumer = null;
    }
    
    /**
     * <p>ファイナライザです。</p>
     */
    protected void finalize() throws Throwable {
        
        try {
            destruct();
            
        } finally {
            super.finalize();
        }
    }

    /**
     * <p>送出タイミング時に呼び出します。即座に同一スレッドにてコンシューマの送出処理が呼び出されます。</p>
     */
    public void update() {
        
        m_consumer.push();
    }
    private InPortConsumer m_consumer;

}
