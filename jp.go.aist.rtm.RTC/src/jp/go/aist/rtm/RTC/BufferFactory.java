package jp.go.aist.rtm.RTC;

/**
 * <p>CdrBuffer用ファクトリの実装です。</p>
 */
public class BufferFactory<ABSTRACTCLASS,IDENTIFIER> extends FactoryGlobal<ABSTRACTCLASS,IDENTIFIER> {

    /**
     * <p> constructor </p>
     */
    private BufferFactory() {

    }
    /**
     * <p> instance </p>
     *
     * @return BufferFactory object
     */
    public static BufferFactory instance() {
        if (factory_global == null) {
            synchronized (factory_global_mutex) {
                if (factory_global == null) {
                    try {
                        factory_global = new BufferFactory();
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
    private static BufferFactory factory_global;
}

