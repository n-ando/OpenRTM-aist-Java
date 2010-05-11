package jp.go.aist.rtm.RTC;

/**
 * <p>OutPortConsumer用ファクトリの実装です。</p>
 */
public class OutPortConsumerFactory<ABSTRACTCLASS,IDENTIFIER> extends FactoryGlobal<ABSTRACTCLASS,IDENTIFIER> {

    /**
     * <p> constructor </p>
     */
    private OutPortConsumerFactory() {

    }
    /**
     * <p> instance </p>
     *
     * @return OutPortConsumerFactory object
     */
    public static OutPortConsumerFactory instance() {
        if (factory_global == null) {
            synchronized (factory_global_mutex) {
                if (factory_global == null) {
                    try {
                        factory_global = new OutPortConsumerFactory();
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
    private static OutPortConsumerFactory factory_global;
}

