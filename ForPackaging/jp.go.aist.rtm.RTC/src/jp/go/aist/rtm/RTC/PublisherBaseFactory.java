package jp.go.aist.rtm.RTC;

/**
 * {@.ja Publisher用ファクトリの実装}
 * {@.en Implement of factory for Publisher} 
 */
public class PublisherBaseFactory<ABSTRACTCLASS,IDENTIFIER> extends FactoryGlobal<ABSTRACTCLASS,IDENTIFIER> {

    /**
     * {@.ja コンストラクタ。}
     * {@.en Constructor}
     */
    private PublisherBaseFactory() {

    }
    /**
     * {@.ja インスタンス生成。}
     * {@.en Create instance}
     *
     * @return 
     *   {@.ja インスタンス}
     *   {@.en PublisherBaseFactory object}
     *
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

