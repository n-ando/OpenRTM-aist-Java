package jp.go.aist.rtm.RTC;

  /**
   * {@.ja ComponentActionListeners クラス}
   * {@.en ComponentActionListeners class}
   */

public class ComponentActionListeners {
    public PreComponentActionListenerHolder[] preaction_ 
    = new PreComponentActionListenerHolder[PreComponentActionListenerType.PRE_COMPONENT_ACTION_LISTENER_NUM];

    public PostComponentActionListenerHolder[] postaction_
    = new PostComponentActionListenerHolder[PostComponentActionListenerType.POST_COMPONENT_ACTION_LISTENER_NUM];

    public PortActionListenerHolder[] portaction_
    = new PortActionListenerHolder[PortActionListenerType.PORT_ACTION_LISTENER_NUM];

    public ExecutionContextActionListenerHolder[] ecaction_
    = new ExecutionContextActionListenerHolder[ExecutionContextActionListenerType.EC_ACTION_LISTENER_NUM];

   public ComponentActionListeners() {
       for(int ic=0;ic<PreComponentActionListenerType.PRE_COMPONENT_ACTION_LISTENER_NUM;++ic){
          preaction_[ic] = new PreComponentActionListenerHolder();
       }
       for(int ic=0;ic<PostComponentActionListenerType.POST_COMPONENT_ACTION_LISTENER_NUM;++ic){
          postaction_[ic] = new PostComponentActionListenerHolder();
       }
       for(int ic=0;ic<PortActionListenerType.PORT_ACTION_LISTENER_NUM;++ic){
          portaction_[ic] = new PortActionListenerHolder();
       }
       for(int ic=0;ic<ExecutionContextActionListenerType.EC_ACTION_LISTENER_NUM;++ic){
          ecaction_[ic] = new ExecutionContextActionListenerHolder();
       }
   }

}

