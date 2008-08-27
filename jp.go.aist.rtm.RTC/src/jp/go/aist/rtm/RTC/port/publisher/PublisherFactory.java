package jp.go.aist.rtm.RTC.port.publisher;

import jp.go.aist.rtm.RTC.port.InPortConsumer;
import jp.go.aist.rtm.RTC.util.Properties;

/**
 * <p>各種のPublisherオブジェクトの生成・破棄を管理するファクトリクラスです。</p>
 */
public class PublisherFactory {

    /**
     * <p>Publisherオブジェクトを生成します。指定された引数に応じた適切なPublisher実装の
     * オブジェクトが生成されます。</p>
     * 
     * <p>生成するPublisherの種別を、指定されたPropertyオブジェクトの
     * dataport.subscription_typeメンバに設定しておく必要があります。また、種別によっては、
     * Publisherの駆動を制御する情報をさらに設定しておく必要があります。
     * それらの具体的な内容は、それぞれのPublisher実装を参照してください。</p>
     * 
     * @param consumer Publisherによってデータ送出を駆動されるコンシューマ
     * @param property 生成すべきPublisherを特定するための情報や、Publisherの駆動を制御するための
     * 情報が設定されているPropertyオブジェクト
     * @return 適切なPublisherを生成できた場合は、そのオブジェクトを返します。さもなくばnullを返します。
     */
    public PublisherBase create(InPortConsumer consumer, Properties property) {
        
        String pub_type = property.getProperty("dataport.subscription_type", "New");
        
        if (pub_type.equals("New")) {
            return new PublisherNew(consumer, property);
            
        } else if (pub_type.equals("Periodic")) {
            return new PublisherPeriodic(consumer, property);
            
        } else if (pub_type.equals("Flush")) {
            return new PublisherFlush(consumer, property);
        }
        
        return null;
    }
    
    /**
     * <p>指定されたPublisherを破棄します。</p>
     * 
     * @param publisher 破棄対象のPublisherオブジェクト
     */
    public void destroy(PublisherBase publisher) {
        
        publisher.release();
        publisher.destruct();
    }
}
