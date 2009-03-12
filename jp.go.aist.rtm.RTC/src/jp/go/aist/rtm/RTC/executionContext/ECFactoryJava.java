package jp.go.aist.rtm.RTC.executionContext;

/**
 * <p>Java言語版ExecutionContextインスタンス生成用Factoryクラスです。</p>
 */
public class ECFactoryJava extends ECFactoryBase {

    /**
     * <p>コンストラクタです。</p>
     * 
     * @param name 生成対象のExecutionContextクラス名の完全修飾名(フルパスのクラス名)
     */
    public ECFactoryJava(final String name) {
        m_name = name;
    }
    
    /**
     * <p>生成対象のExecutionContextの完全修飾名(フルパスのクラス名)を取得します。</p>
     * 
     * @return 生成対象のExecutionContextクラス名の完全修飾名(フルパスのクラス名)
     */
    public String name() {
        return m_name;
    }

    /**
     * <p>生成対象のExecutionContextクラスのインスタンスを生成します。</p>
     * 
     * @return 生成したExecutionContextインスタンス
     */
    public ExecutionContextBase create() {

        ECNewDeleteFunc target = null;
        
        try {
            target = (ECNewDeleteFunc) this.createClass(m_name);
            return target.ECNewFunc();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            
        } catch (InstantiationException e) {
            e.printStackTrace();
            
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <p>ExecutionContextのインスタンスを破棄します。</p>
     * 
     * @param comp 破棄対象ExecutionContextインスタンス
     */
    public ExecutionContextBase destroy(ExecutionContextBase comp) {
        
        ECNewDeleteFunc target = null;
        
        try {
            target = (ECNewDeleteFunc) this.createClass(m_name);
            comp = (ExecutionContextBase)target.ECDeleteFunc(comp);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
            
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
        return comp;
    }
    
    /**
     * <p>生成対象のExecutionContextクラス名の完全修飾名(フルパスのクラス名)</p>
     */
    protected String m_name;
    
    private Object createClass(String className)
        throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        Class targetClass = null;
        
        try {
            targetClass = Class.forName(className);
            
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            throw new ClassNotFoundException("Not implemented.", ex);
        }
        
        return targetClass.newInstance();
    }

}
