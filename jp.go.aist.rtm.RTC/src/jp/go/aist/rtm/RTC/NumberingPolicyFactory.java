package jp.go.aist.rtm.RTC;
/**
 * {@.ja NumberingPolicy用ファクトリの実装}
 * {@.en Implement of factory for NumberingPolicy} 
 */
public class NumberingPolicyFactory<ABSTRACTCLASS,IDENTIFIER> extends FactoryGlobal<ABSTRACTCLASS,IDENTIFIER> {
    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     * @param profile 
     *   {@.ja コンポーネントのプロファイル}
     *   {@.en Component profile}
     */
    private NumberingPolicyFactory() {
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
     *   {@.en InPortProviderFactory object}
     *
     */
    public static NumberingPolicyFactory instance() {
        if (factory_global == null) {
            synchronized (factory_global_mutex) {
                if (factory_global == null) {
                    try {
                        factory_global = new NumberingPolicyFactory();
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
    private static NumberingPolicyFactory factory_global;
}

