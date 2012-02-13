package RTMExamples.Composite;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.ModuleInitProc;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.util.Properties;

/*!
 * @class ControllerComp
 * @brief Standalone component Class
 *
 */
public class ControllerComp implements ModuleInitProc {

    public void myModuleInit(Manager mgr) {
      Properties prop = new Properties(Controller.component_conf);
      mgr.registerFactory(prop, new Controller(), new Controller());

      // Create a component
      RTObject_impl comp = mgr.createComponent("Controller");
      if( comp==null ) {
    	  System.err.println("Component create failed.");
    	  System.exit(0);
      }
      
    }

    public static void main(String[] args) {
        // Initialize manager
        final Manager manager = Manager.init(args);

        // Set module initialization proceduer
        // This procedure will be invoked in activateManager() function.
        ControllerComp init = new ControllerComp();
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
