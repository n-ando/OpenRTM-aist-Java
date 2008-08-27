package jp.go.aist.rtm.RTC.executionContext;

/**
 * <p>ExecutionContextインスタンス生成用Factoryのベースとなる抽象クラスです。
 * ExecutionContextインスタンスの生成方法を変更したFactoryを実装する場合は、
 * 本クラスのサブクラスとして実装します。</p>
 */
public abstract class ECFactoryBase {
    
    /**
     * <p>生成対象のExecutionContextクラス名を取得するための抽象メソッドです。
     * Java言語用Executionの完全修飾名(フルパスのクラス名)を
     * 取得するための抽象メソッドです。</p>
     * 
     * @return 生成対象のExecutionContextクラス名の完全修飾名(フルパスのクラス名)
     */
    public abstract String name();
    
    /**
     * <p>生成対象のExecutionContextクラスのインスタンスを生成するための抽象メソッドです。</p>
     * 
     * @return 生成したExecutionContextインスタンス
     */
    public abstract ExecutionContextBase create();

    /**
     * <p>ExecutionContextのインスタンスを破棄するための抽象メソッドです。</p>
     * 
     * @param comp 破棄対象ExecutionContextインスタンス
     */
    public abstract void destroy(ExecutionContextBase comp);

}
