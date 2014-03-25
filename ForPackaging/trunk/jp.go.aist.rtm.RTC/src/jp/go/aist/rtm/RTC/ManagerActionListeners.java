package jp.go.aist.rtm.RTC;
  /**
   * {@.ja ManagerActionListeners クラス}
   * {@.en ManagerActionListeners class}
   */
  class ManagerActionListeners
  {
    public ManagerActionListenerHolder manager_ 
        = new ManagerActionListenerHolder();
    public ModuleActionListenerHolder module_
        = new ModuleActionListenerHolder();
    public RtcLifecycleActionListenerHolder rtclifecycle_
        = new RtcLifecycleActionListenerHolder();
    public NamingActionListenerHolder naming_
        = new NamingActionListenerHolder();
    public LocalServiceActionListenerHolder localservice_
        = new LocalServiceActionListenerHolder();
  };

