package jp.go.aist.rtm.RTC;

import java.lang.reflect.Method;
/**
 * <p>  </p>
 *
 */
public class TaskFuncBase {
    /**
     * <p>  </p>
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
     * <p>  </p>
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
    protected Method m_method = null;
    protected Class m_class = null;
    protected Object m_obj = null;
};
