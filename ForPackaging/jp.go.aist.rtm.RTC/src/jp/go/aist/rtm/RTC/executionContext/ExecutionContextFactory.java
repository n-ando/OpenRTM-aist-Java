package jp.go.aist.rtm.RTC.executionContext;

import jp.go.aist.rtm.RTC.FactoryGlobal;
/**
 * {@.ja ExecutionContext用ファクトリの実装}
 * {@.en Implement of factory for ExecutionContext} 
 */

public class ExecutionContextFactory<ABSTRACTCLASS,IDENTIFIER> extends FactoryGlobal<ABSTRACTCLASS,IDENTIFIER> {

    /**
     * {@.ja コンストラクタ。}
     * {@.en Constructor}
     */
    private ExecutionContextFactory() {

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
     *   {@.en ExecutionContextFactory object}
     *
     */
    public static ExecutionContextFactory instance() {
        return (ExecutionContextFactory)instance("jp.go.aist.rtm.RTC.executionContext.ExecutionContextFactory");
    }
}

