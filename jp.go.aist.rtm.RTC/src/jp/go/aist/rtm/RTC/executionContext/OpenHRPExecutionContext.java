package jp.go.aist.rtm.RTC.executionContext;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.RegisterModuleFunc;
import jp.go.aist.rtm.RTC.util.TimeValue;

import org.omg.CORBA.SystemException;

/**
 *  <p> OpenHRPExecutionContext </p>
 *
 */
public class OpenHRPExecutionContext
        extends PeriodicExecutionContext implements Runnable{

    /**
     * <p> Constructor </p>
     */
    public OpenHRPExecutionContext() {
        super();
    }

    /**
     * <p> tick </p> 
     */
    public void tick() throws SystemException {
        synchronized (m_comps) {
            for(int intIdx=0;intIdx<m_comps.size();intIdx++ ) {
                m_comps.elementAt(intIdx).invoke();
            }
        }
    }

    /**
     * <p> svc </p>
     *
     * @return int
     *
     */
    public int svc() {
        return 0;
    }

    /**
     * <p> run </p>
     *
     *
     */
    public void run() {
        this.svc();
    }

    /**
     *  <p> Worker </p> 
     */
    private class Worker {
        
        /**
         * <p> constructor </p>
         */
        public Worker() {
            this._called = false;
        }

        /**
         * 
         */
        public boolean _called;
    }
    
    /**
     *
     */
    private Worker m_worker = new Worker();

    /**
     * <p> OpenHRPExecutionContextInit </p>
     *
     * @param manager Manager
     */
    public static void OpenHRPExecutionContextInit(Manager manager) {
        manager.registerECFactory("jp.go.aist.rtm.RTC.executionContext.OpenHRPExecutionContext");
    }
    
    /**
     * <p> ECDeleteFunc </p>
     *
     * @param comp ExecutionContextBase
     */
    public Object ECDeleteFunc(ExecutionContextBase comp) {
        return null;
    }

    /**
     * <p> ECNewFunc </p>
     *
     * @return ExecutionContextBase
     */
    public ExecutionContextBase ECNewFunc() {
        return this;
    }
    
}

