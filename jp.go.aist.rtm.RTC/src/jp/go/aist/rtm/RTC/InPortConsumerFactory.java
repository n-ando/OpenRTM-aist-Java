package jp.go.aist.rtm.RTC;

/**
 * <p>InPortConsumer用ファクトリの実装です。</p>
 */
public class InPortConsumerFactory<ABSTRACTCLASS,IDENTIFIER> extends FactoryGlobal<ABSTRACTCLASS,IDENTIFIER> {

    /**
     * <p> constructor </p>
     */
    private InPortConsumerFactory() {

    }
    /**
     * <p> instance </p>
     *
     * @return InPortConsumerFactory object
     */
    public static InPortConsumerFactory instance() {
        if (factory_global == null) {
            synchronized (factory_global_mutex) {
                if (factory_global == null) {
                    try {
                        factory_global = new InPortConsumerFactory();
                    } catch (Exception e) {
                        factory_global = null;
                    }
                }
            }
        }

        return factory_global;
    }
    /**
     *  <p> mutex </p>
     */
    private static String factory_global_mutex = new String();
    /**
     *  <p> object </p>
     */
    private static InPortConsumerFactory factory_global;
}

