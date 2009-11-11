package jp.go.aist.rtm.RTC.port.publisher;

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

import jp.go.aist.rtm.RTC.port.ReturnCode;
import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.port.InPortConsumer;

/**
 * <p>データ送出タイミングを管理して送出を駆動するPublisherのベース実装クラスです。</p>
 */
public abstract class PublisherBase {

    /**
     * <p>当該Publisherの駆動が停止される際に、PublisherFactoryにより呼び出されます。</p>
     */
    public void release() {
    }

    /**
     * <p> init </p>
     *
     * @param prop
     * @return ReturnCode
     */
    public abstract ReturnCode init(Properties prop);
    /**
     * <p> setConsumer </p>
     *
     * @param consumer
     * @return ReturnCode
     */
    public abstract ReturnCode setConsumer(InPortConsumer consumer);
    /**
     * <p> setBuffer </p>
     *
     * @param buffer
     * @return ReturnCode
     */
    public abstract ReturnCode setBuffer(BufferBase<OutputStream> buffer);
    /**
     * <p> write </p>
     *
     * @param data
     * @param sec
     * @param usec
     * @return ReturnCode
     */
    public abstract ReturnCode write(final OutputStream data, int sec, int usec);
    public abstract ReturnCode write(final OutputStream data);
    /**
     * <p> isActive </p>
     *
     * @return boolean 
     */
    public abstract boolean isActive();
    /**
     * <p> activate </p>
     *
     * @return ReturnCode 
     */
    public abstract ReturnCode activate();
    /**
     * <p> deactivate </p>
     *
     * @return ReturnCode 
     */
    public abstract ReturnCode deactivate();
    /**
     * <p> getName </p>
     *
     * @return String 
     */
    public abstract String getName();


}
