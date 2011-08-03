package jp.go.aist.rtm.RTC;

import java.lang.reflect.Method;
  /**
   * {@.ja TaskFuncBase クラス}
   * {@.en TaskFuncBase class}
   */
public class TaskFuncBase {
    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     * @param obj 
     *   {@.ja オブジェクト}
     *   {@.en obj Object.}
     * @param func 
     *   {@.ja 関数}
     *   {@.en func Function.}
     *
     */
    public TaskFuncBase(Object obj, String func) {
        m_obj = obj;
        m_class = obj.getClass();
        try {
            Class[] parameterTypes = null;
            m_method = m_class.getMethod(func, parameterTypes);
        }
        catch(NoSuchMethodException e){
            //getMethod throws
        }
    }

    /**
     * {@.ja オブジェクトの関数実行。}
     * {@.en Functor}
     *
     * <p>
     * {@.ja オブジェクトの関数を実行する。}
     * {@.en Execute a function of the object.}
     */
    public int svc() {
        if(m_method != null && m_class != null){
            try {
                Object[] args = null;
                Integer intg = (Integer)m_method.invoke(m_obj ,args);
                return intg.intValue();
            }
            catch(java.lang.IllegalAccessException e){
                //invoke throws
            }
            catch(java.lang.IllegalArgumentException e){
                //invoke throws
            }
            catch(java.lang.reflect.InvocationTargetException e){
                //invoke throws
            }
        }
        return 0;
    }
    /**
     * {@.ja メソッド}
     * {@.en method}
     */
    protected Method m_method = null;
    /**
     * {@.ja クラス}
     * {@.en class}
     */
    protected Class m_class = null;
    /**
     * {@.ja オブジェクト}
     * {@.en object}
     */
    protected Object m_obj = null;
};
