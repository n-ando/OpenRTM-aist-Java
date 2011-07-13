package jp.go.aist.rtm.RTC;

/**
 * {@.ja SdoServiceConsumer用ファクトリの実装}
 * {@.en Implement of factory for SdoServiceConsumer} 
 */

public class SdoServiceConsumerFactory<ABSTRACTCLASS,IDENTIFIER> extends FactoryGlobal<ABSTRACTCLASS,IDENTIFIER> {

    /**
     * {@.ja コンストラクタ。}
     * {@.en Constructor}
     */
    private SdoServiceConsumerFactory() {

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
    public static SdoServiceConsumerFactory instance() {
        if (factory_global == null) {
            synchronized (factory_global_mutex) {
                if (factory_global == null) {
                    try {
                        factory_global = new SdoServiceConsumerFactory();
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
    private static SdoServiceConsumerFactory factory_global;
}

