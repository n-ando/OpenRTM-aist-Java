package jp.go.aist.rtm.RTC;

  /**
   * {@.ja FsmActionListeners クラス}
   * {@.en FsmActionListeners class}
   */

public class FsmActionListeners {
    public PreFsmActionListenerHolder[] preaction_ 
    = new PreFsmActionListenerHolder[PreFsmActionListenerType.PRE_FSM_ACTION_LISTENER_NUM];

    public PostFsmActionListenerHolder[] postaction_
    = new PostFsmActionListenerHolder[PostFsmActionListenerType.POST_FSM_ACTION_LISTENER_NUM];

    public FsmProfileListenerHolder[] profile_
    = new FsmProfileListenerHolder[FsmProfileListenerType.FSM_PROFILE_LISTENER_NUM];

    public FsmStructureListenerHolder[] structure_
    = new FsmStructureListenerHolder[FsmStructureListenerType.FSM_STRUCTURE_LISTENER_NUM];

   public FsmActionListeners() {
       for(int ic=0;ic<PreFsmActionListenerType.PRE_FSM_ACTION_LISTENER_NUM;++ic){
          preaction_[ic] = new PreFsmActionListenerHolder();
       }
       for(int ic=0;ic<PostFsmActionListenerType.POST_FSM_ACTION_LISTENER_NUM;++ic){
          postaction_[ic] = new PostFsmActionListenerHolder();
       }
       for(int ic=0;ic<FsmProfileListenerType.FSM_PROFILE_LISTENER_NUM;++ic){
          profile_[ic] = new FsmProfileListenerHolder();
       }
       for(int ic=0;ic<FsmStructureListenerType.FSM_STRUCTURE_LISTENER_NUM;++ic){
          structure_[ic] = new FsmStructureListenerHolder();
       }
   }

}

