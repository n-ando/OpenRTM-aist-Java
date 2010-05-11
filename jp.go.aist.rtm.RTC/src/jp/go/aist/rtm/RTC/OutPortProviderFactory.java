package jp.go.aist.rtm.RTC;

/**
 * <p>OutPortProvider用ファクトリの実装です。</p>
 */
public class OutPortProviderFactory<ABSTRACTCLASS,IDENTIFIER> extends FactoryGlobal<ABSTRACTCLASS,IDENTIFIER> {

    /**
     * <p> constructor </p>
     */
    private OutPortProviderFactory() {

    }
    /**
     * <p> instance </p>
     *
     * @return OutPortProviderFactory object
     */
    public static OutPortProviderFactory instance() {
        if (factory_global == null) {
            synchronized (factory_global_mutex) {
                if (factory_global == null) {
                    try {
                        factory_global = new OutPortProviderFactory();
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
    private static OutPortProviderFactory factory_global;
}

