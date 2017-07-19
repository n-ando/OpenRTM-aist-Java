package jp.go.aist.rtm.RTC;

/**
 * {@.ja PeriodicTask用ファクトリの実装}
 * {@.en Implement of factory for PeriodicTask} 
 */
public class PeriodicTaskFactory<ABSTRACTCLASS,IDENTIFIER> extends FactoryGlobal<ABSTRACTCLASS,IDENTIFIER> {

    /**
     * {@.ja コンストラクタ。}
     * {@.en Constructor}
     */
    private PeriodicTaskFactory() {

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
     *   {@.en PeriodicTaskFactory object}
     *
     */
    synchronized public static PeriodicTaskFactory instance() {
        if (factory_global == null) {
            try {
                factory_global = new PeriodicTaskFactory();
            } catch (Exception e) {
                factory_global = null;
            }
        }

        return factory_global;
    }
    /**
     *  <p> object </p>
     */
    private static PeriodicTaskFactory factory_global;
}

