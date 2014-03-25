package jp.go.aist.rtm.RTC;

/**
 * {@.ja SdoServiceProvider用ファクトリの実装}
 * {@.en Implement of factory for SdoServiceProvider} 
 */

public class SdoServiceProviderFactory<ABSTRACTCLASS,IDENTIFIER> extends FactoryGlobal<ABSTRACTCLASS,IDENTIFIER> {

    /**
     * {@.ja コンストラクタ。}
     * {@.en Constructor}
     */
    private SdoServiceProviderFactory() {

    }
    /**
     * {@.ja インスタンス生成。}
     * {@.en Create instance}
     *
     * <p>
     * {@.ja インスタンスを生成する。}
     *
     * @return 
     *   {@.ja インスタンス}
     *   {@.en SdoServiceConsumerFactory object}
     *
     */
    public static SdoServiceProviderFactory instance() {
/*
        if (factory_global == null) {
            synchronized (factory_global_mutex) {
                if (factory_global == null) {
                    try {
                        factory_global = new SdoServiceProviderFactory();
                    } catch (Exception e) {
                        factory_global = null;
                    }
                }
            }
        }
        return factory_global;
*/
        return (SdoServiceProviderFactory)instance("jp.go.aist.rtm.RTC.SdoServiceProviderFactory");
    }
    /**
     *  <p> mutex </p>
     */
    private static String factory_global_mutex = new String();
    /**
     *  <p> object </p>
     */
    private static SdoServiceProviderFactory factory_global;
}


