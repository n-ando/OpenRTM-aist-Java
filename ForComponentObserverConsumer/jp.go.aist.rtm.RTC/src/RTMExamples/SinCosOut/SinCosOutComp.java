package RTMExamples.SinCosOut;


import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.ModuleInitProc;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.util.Properties;

public class SinCosOutComp implements ModuleInitProc {

    public void myModuleInit(Manager mgr) {
      Properties prop = new Properties(SinCosOut.component_conf);
      mgr.registerFactory(prop, new SinCosOut(), new SinCosOut());

      // Create a component
      RTObject_impl comp = mgr.createComponent("SinCosOut");
    }

    public static void main(String[] args) {
        // Initialize manager
        final Manager manager = Manager.init(args);

        // Set module initialization proceduer
        // This procedure will be invoked in activateManager() function.
        SinCosOutComp init = new SinCosOutComp();
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
