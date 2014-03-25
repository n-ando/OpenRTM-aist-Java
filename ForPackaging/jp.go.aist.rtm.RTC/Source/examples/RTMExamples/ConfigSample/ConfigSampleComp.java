package RTMExamples.ConfigSample;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.ModuleInitProc;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.util.Properties;
import RTC.ExecutionContextServiceListHolder;

public class ConfigSampleComp implements ModuleInitProc {

    public void myModuleInit(Manager mgr) {
        Properties prop = new Properties(ConfigSample.component_conf);
        mgr.registerFactory(prop, new ConfigSample(), new ConfigSample());

        // Create a component
        RTObject_impl comp = mgr.createComponent("ConfigSample");
      
        // Example
        // The following procedure is examples how handle RT-Components.
        // These should not be in this function.

        // Get the component's object reference
//        RTObject_impl  rtobj = new RTObject_impl(mgr);
//        try {
//            rtobj.setObjRef(RTObjectHelper.narrow(mgr.getPOA().servant_to_reference(comp)));
//        } catch (ServantNotActive e) {
//            e.printStackTrace();
//        } catch (WrongPolicy e) {
//            e.printStackTrace(); 
//        }

        ExecutionContextServiceListHolder ecs = new ExecutionContextServiceListHolder();
        ecs.value = comp.get_execution_context_services();
        ecs.value[0].activate_component(comp.getObjRef());
        System.out.println("");
    }

    public static void main(String[] args) {
        // Initialize manager
        final Manager manager = Manager.init(args);

        // Set module initialization proceduer
        // This procedure will be invoked in activateManager() function.
        ConfigSampleComp init = new ConfigSampleComp();
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
