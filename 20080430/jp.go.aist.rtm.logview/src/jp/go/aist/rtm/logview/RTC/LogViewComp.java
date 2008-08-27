package jp.go.aist.rtm.logview.RTC;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.ModuleInitProc;
import jp.go.aist.rtm.RTC.util.Properties;

public class LogViewComp implements ModuleInitProc {

	public void myModuleInit(Manager mgr) {
      Properties prop = new Properties(LogView.component_conf);
      mgr.registerFactory(prop, new LogView(), new LogView());
      mgr.createComponent("LogView");
    }

    public static void main(String[] args) {
        // Initialize manager
        final Manager manager = Manager.init(args);

        // Set module initialization proceduer
        // This procedure will be invoked in activateManager() function.
        LogView init = new LogView();
        manager.setModuleInitProc(init);

        // Activate manager and register to naming service
        manager.activateManager();

        // run the manager in blocking mode
        // runManager(false) is the default.
        manager.runManager();

        // If you want to run the manager in non-blocking mode, do like this
        // manager->runManager(true);
    }


}
