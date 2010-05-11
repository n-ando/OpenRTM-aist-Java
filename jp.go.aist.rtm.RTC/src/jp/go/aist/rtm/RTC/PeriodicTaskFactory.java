package jp.go.aist.rtm.RTC;

/**
 * <p>PeriodicTask用ファクトリの実装です。</p>
 */
public class PeriodicTaskFactory<ABSTRACTCLASS,IDENTIFIER> extends FactoryGlobal<ABSTRACTCLASS,IDENTIFIER> {

    /**
     * <p> constructor </p>
     */
    private PeriodicTaskFactory() {

    }
    /**
     * <p> instance </p>
     *
     * @return PeriodicTaskFactory object
     */
    public static PeriodicTaskFactory instance() {
        if (factory_global == null) {
            synchronized (factory_global_mutex) {
                if (factory_global == null) {
                    try {
                        factory_global = new PeriodicTaskFactory();
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
    private static PeriodicTaskFactory factory_global;
}

