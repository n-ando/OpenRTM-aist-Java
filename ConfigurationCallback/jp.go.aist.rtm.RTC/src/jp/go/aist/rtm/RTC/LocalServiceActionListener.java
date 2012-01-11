package jp.go.aist.rtm.RTC;


import java.util.Observable;
import java.util.Observer;

import jp.go.aist.rtm.RTC.util.Properties;

  /**
   * {@.ja LocalServiceActionListener クラス}
   * {@.en LocalServiceActionListener class}
   * <p>
   * {@.ja 各アクションに対応するユーザーコードが呼ばれる直前のタイミング
   * でコールされるリスなクラスの基底クラス。
   *
   * - ADD_PORT:
   * - REMOVE_PORT:}
   * {@.en This class is abstract base class for listener classes that
   * provides callbacks for various events in rtobject.}
   */
public abstract class LocalServiceActionListener  implements Observer{
    public void update(Observable o, Object obj) {
           LocalServiceActionListenerArgument arg = (LocalServiceActionListenerArgument)obj;
           if(arg.m_method_name.equals("preServiceRegister")){
               preServiceRegister(arg.m_service_name);
           }
           else if(arg.m_method_name.equals("postServiceRegister")){
               postServiceRegister(arg.m_service_name,arg.m_service);
           }
           else if(arg.m_method_name.equals("preServiceInit")){
               preServiceInit(arg.m_prop,arg.m_service);
           }
           else if(arg.m_method_name.equals("postServiceInit")){
               postServiceInit(arg.m_prop,arg.m_service);
           }
           else if(arg.m_method_name.equals("preServiceReinit")){
               preServiceReinit(arg.m_prop,arg.m_service);
           }
           else if(arg.m_method_name.equals("postServiceReinit")){
               postServiceReinit(arg.m_prop,arg.m_service);
           }
           else if(arg.m_method_name.equals("postServiceFinalize")){
               postServiceFinalize(arg.m_service_name, arg.m_service);
           }
           else if(arg.m_method_name.equals("preServiceFinalize")){
               preServiceFinalize(arg.m_service_name,arg.m_service);
           }
           else{
               operator(arg.m_service_name);
           }
    }
    /**
     * {@.ja 仮想コールバック関数}
     * {@.en Virtual Callback function}
     * <p>
     * {@.ja LocalServiceActionListener のコールバック関数}
     * {@.en This is a the Callback function for LocalServiceActionListener.}
     *
     */
    public abstract void operator(final String service_name);
    
    /**
     * {@.ja preServiceRegister コールバック関数}
     * {@.en preServiceRegister callback function}
     */
    public abstract void preServiceRegister(String service_name);
    
    /**
     * {@.ja postServiceRegister コールバック関数}
     * {@.en postServiceRegister callback function}
     */
    public abstract void postServiceRegister(String service_name,
                                     LocalServiceBase service);

    /**
     * {@.ja preServiceInit コールバック関数}
     * {@.en preServiceInit callback function}
     */
    public abstract void preServiceInit(Properties prop,
                                LocalServiceBase service);
    
    /**
     * {@.ja postServiceInit コールバック関数}
     * {@.en postServiceInit callback function}
     */
    public abstract void postServiceInit(Properties prop,
                                 LocalServiceBase service);
    
    /**
     * {@.ja preServiceReinit コールバック関数}
     * {@.en preServiceReinit callback function}
     */
    public abstract void preServiceReinit(Properties prop,
                                  LocalServiceBase service);
    
    /**
     * {@.ja postServiceReinit コールバック関数}
     * {@.en postServiceReinit callback function}
     */
    public abstract void postServiceReinit(Properties prop,
                                   LocalServiceBase service);
    
    /**
     * {@.ja postServiceFinalize コールバック関数}
     * {@.en postServiceFinalize callback function}
     */
    public abstract void postServiceFinalize(String service_name,
                                     LocalServiceBase service);
    
    /**
     * {@.ja preServiceFinalize コールバック関数}
     * {@.en preServiceFinalize callback function}
     */
    public abstract void preServiceFinalize(String service_name,
                                    LocalServiceBase service);
};

