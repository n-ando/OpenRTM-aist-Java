package jp.go.aist.rtm.RTC;

/**
 * <p>Publisher用ファクトリの実装です。</p>
 */
public class PublisherBaseFactory<ABSTRACTCLASS,IDENTIFIER> extends FactoryGlobal<ABSTRACTCLASS,IDENTIFIER> {

    /**
     * <p> constructor </p>
     */
    private PublisherBaseFactory() {

    }
    /**
     * <p> instance </p>
     *
     * @return PublisherBaseFactory object
     */
    public static PublisherBaseFactory instance() {
        if (factory_global == null) {
            synchronized (factory_global_mutex) {
                if (factory_global == null) {
                    try {
                        factory_global = new PublisherBaseFactory();
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
    private static PublisherBaseFactory factory_global;
}

