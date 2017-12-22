package RTMExamples.Throughput;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.ModuleInitProc;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.Properties;
import RTC.ComponentProfile;
import RTC.PortService;
import RTC.PortInterfacePolarity;
import RTC.PortInterfaceProfileListHolder;
import RTC.PortServiceListHolder;
import _SDOPackage.NVListHolder;

public class ThroughputComp implements ModuleInitProc {

  public void myModuleInit(Manager mgr) {
    Properties prop = new Properties(Throughput.component_conf);
    mgr.registerFactory(prop, new Throughput(), new Throughput());

    // Create a component
    RTObject_impl comp = mgr.createComponent("Throughput");
        
  }

  public static void main(String[] args) {
    // Initialize manager
    final Manager manager = Manager.init(args);

    // Set module initialization proceduer
    // This procedure will be invoked in activateManager() function.
    ThroughputComp init = new ThroughputComp();
    manager.setModuleInitProc(init);

    // Activate manager and register to naming service
    manager.activateManager();

    // run the manager in blocking mode
    // runManager(false) is the default.
    manager.runManager();

    // If you want to run the manager in non-blocking mode, do like this
    // manager.runManager(true);
  }

}
