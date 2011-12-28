package jp.go.aist.rtm.RTC;

import java.util.Observable;
import java.util.Observer;

  /**
   * {@.ja ModuleActionListener クラス}
   * {@.en ModuleActionListener class}
   *
   */
public abstract class ModuleActionListener  implements Observer{
    public void update(Observable o, Object obj) {
           ModuleActionListenerArgument arg = (ModuleActionListenerArgument)obj;
           if(arg.m_method.equals("preLoad")){
               preLoad(arg.m_modname,arg.m_funcname);
           }
           else if(arg.m_method.equals("postLoad")){
               postLoad(arg.m_modname,arg.m_funcname);
           }
           else if(arg.m_method.equals("preUnload")){
               preUnload(arg.m_modname);
           }
           else if(arg.m_method.equals("postUnload")){
               postUnload(arg.m_modname);
           }
           else{
               operator();
           }
    }
    /**
     * {@.ja 仮想コールバック関数}
     * {@.en Virtual Callback function}
     * <p>
     * {@.ja ModuleActionListener のコールバック関数}
     * {@.en This is a the Callback function for ModuleActionListener.}
     *
     */
    public abstract void operator();
    
    /**
     * {@.ja preLoad コールバック関数}
     * {@.en preLoad callback function}
     */
    public abstract void preLoad(String modname,
                         String funcname);
    
    /**
     * {@.ja postLoad コールバック関数}
     * {@.en postLoad callback function}
     */
    public abstract void postLoad(String modname,
                          String funcname);
    
    /**
     * {@.ja preUnload コールバック関数}
     * {@.en preUnload callback function}
     */
    public abstract void preUnload(String modname);
    
    /**
     * {@.ja postUnload コールバック関数}
     * {@.en postUnload callback function}
     */
    public abstract void postUnload(String modname);
  };

