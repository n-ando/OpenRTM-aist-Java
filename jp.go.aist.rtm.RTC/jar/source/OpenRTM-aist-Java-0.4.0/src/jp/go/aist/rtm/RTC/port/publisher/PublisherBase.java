package jp.go.aist.rtm.RTC.port.publisher;

/**
 * <p>データ送出タイミングを管理して送出を駆動するPublisherのベース実装クラスです。</p>
 */
public abstract class PublisherBase {

    /**
     * <p>送出タイミングを待つオブジェクトに、送出タイミングであることを通知します。</p>
     *
     */
    public abstract void update();

    /**
     * <p>当該Publisherの駆動が停止される際に、PublisherFactoryにより呼び出されます。</p>
     */
    public void release() {
    }

    /**
     * <p>当該Publisherが不要となり破棄される際に、PublisherFactoryにより呼び出されます。</p>
     */
    public void destruct() {
    }

}
