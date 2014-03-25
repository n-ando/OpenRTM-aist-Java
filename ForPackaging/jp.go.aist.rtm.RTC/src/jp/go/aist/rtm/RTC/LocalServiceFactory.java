package jp.go.aist.rtm.RTC;

/**
 * {@.ja LocalService用ファクトリの実装。}
 * {@.en This class is a factory for LocalService.}
 */
public class LocalServiceFactory<ABSTRACTCLASS,IDENTIFIER> extends FactoryGlobal<ABSTRACTCLASS,IDENTIFIER> {

    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     */
    private LocalServiceFactory() {

    }
    /**
     * {@.ja LocalServiceFactoryのインスタンスを生成する。} 
     * {@.en Creates a instance of LocalServiceFactory.}
     * @return
     *   {@.ja LocalServiceFactoryオブジェクト}
     *   {@.en LocalServiceFactory object}
     */
    public static LocalServiceFactory instance() {
        return (LocalServiceFactory)instance("jp.go.aist.rtm.RTC.LocalServiceFactory");
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


