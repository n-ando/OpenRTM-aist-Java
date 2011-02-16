package jp.go.aist.rtm.RTC;

/**
 * <p>InPortProvider用ファクトリの実装です。</p>
 */
public class InPortProviderFactory<ABSTRACTCLASS,IDENTIFIER> extends FactoryGlobal<ABSTRACTCLASS,IDENTIFIER> {

    /**
     * <p> constructor </p>
     */
    private InPortProviderFactory() {

    }

    /**
     * <p> instance </p>
     *
     * @return InPortProviderFactory object
     */
    public static InPortProviderFactory instance() {
        if (factory_global == null) {
            synchronized (factory_global_mutex) {
                if (factory_global == null) {
                    try {
                        factory_global = new InPortProviderFactory();
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
    private static InPortProviderFactory factory_global;
}

