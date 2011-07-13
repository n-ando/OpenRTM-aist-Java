package jp.go.aist.rtm.RTC;

/**
 * {@.ja OutPortProvider用ファクトリの実装}
 * {@.en Implement of factory for OutPortProvider} 
 */
public class OutPortProviderFactory<ABSTRACTCLASS,IDENTIFIER> extends FactoryGlobal<ABSTRACTCLASS,IDENTIFIER> {

    /**
     * {@.ja コンストラクタ。}
     * {@.en Constructor}
     */
    private OutPortProviderFactory() {

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
     *   {@.en OutPortProviderFactory object}
     *
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
     * {@.ja 排他制御用変数}
     * {@.en Variable for exclusive control}
     */
    private static String factory_global_mutex = new String();
    /**
     *  <p> object </p>
     */
    private static OutPortProviderFactory factory_global;
}

