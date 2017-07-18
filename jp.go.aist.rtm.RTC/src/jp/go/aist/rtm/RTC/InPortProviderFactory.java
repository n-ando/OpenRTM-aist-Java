package jp.go.aist.rtm.RTC;

import java.lang.Object;

/**
 * {@.ja InPortProvider用ファクトリの実装}
 * {@.en Implement of factory for InPortProvider} 
 */
public class InPortProviderFactory<ABSTRACTCLASS,IDENTIFIER> extends FactoryGlobal<ABSTRACTCLASS,IDENTIFIER> {

    /**
     * {@.ja コンストラクタ。}
     * {@.en Constructor}
     */
    private InPortProviderFactory() {

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
    synchronized public static InPortProviderFactory instance() {
        if (factory_global == null) {
            try {
                factory_global = new InPortProviderFactory();
            } catch (Exception e) {
                factory_global = null;
            }
        }

        return factory_global;
    }
    /**
     *  <p> object </p>
     */
    private static InPortProviderFactory factory_global;
}

