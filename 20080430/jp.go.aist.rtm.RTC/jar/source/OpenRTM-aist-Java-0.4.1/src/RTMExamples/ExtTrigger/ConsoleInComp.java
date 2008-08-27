package RTMExamples.ExtTrigger;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.ModuleInitProc;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.Properties;
import RTC.ComponentProfile;
import RTC.Port;
import RTC.PortInterfacePolarity;
import RTC.PortInterfaceProfileListHolder;
import RTC.PortListHolder;
import _SDOPackage.NVListHolder;

public class ConsoleInComp implements ModuleInitProc {

    public void myModuleInit(Manager mgr) {
      Properties prop = new Properties(ConsoleIn.component_conf);
      mgr.registerFactory(prop, new ConsoleIn(), new ConsoleIn());

      // Create a component
      System.out.println("Creating a component: \"ConsoleIn\"....");
      RTObject_impl comp = mgr.createComponent("ConsoleIn");
      System.out.println("succeed.");
      
      ComponentProfile prof = comp.get_component_profile();
      System.out.println( "=================================================" );
      System.out.println( " Component Profile" );
      System.out.println( "-------------------------------------------------" );
      System.out.println( "InstanceID:     " + prof.instance_name );
      System.out.println( "Implementation: " + prof.type_name );
      System.out.println( "Description:    " + prof.description );
      System.out.println( "Version:        " + prof.version );
      System.out.println( "Maker:          " + prof.vendor );
      System.out.println( "Category:       " + prof.category );
      System.out.println( "  Other properties   " );
      NVUtil.dump(new NVListHolder(prof.properties));
      System.out.println( "=================================================" );

      PortListHolder portlist = new PortListHolder(comp.get_ports());
      
      for( int intIdx=0;intIdx<portlist.value.length;++intIdx ) {
          Port port = portlist.value[intIdx];
          System.out.println( "=================================================" );
          System.out.println( "Port" + intIdx + " (name): ");
          System.out.println( port.get_port_profile().name );
          System.out.println( "-------------------------------------------------" );
          PortInterfaceProfileListHolder iflist = new PortInterfaceProfileListHolder(port.get_port_profile().interfaces);

          for( int intIdx2=0;intIdx2<iflist.value.length;++intIdx2 ) {
              System.out.println( "I/F name: " );
              System.out.println( iflist.value[intIdx2].instance_name );
              System.out.println( "I/F type: ");
              System.out.println( iflist.value[intIdx2].type_name );
              if( iflist.value[intIdx2].polarity==PortInterfacePolarity.PROVIDED ) {
                  System.out.println( "Polarity: PROVIDED" );
              } else {
                  System.out.println( "Polarity: REQUIRED" );
              }
          }
          System.out.println( "- properties -" );
          NVUtil.dump(new NVListHolder(port.get_port_profile().properties));
          System.out.println( "-------------------------------------------------" );
      }
    }

    public static void main(String[] args) {
        // Initialize manager
        final Manager manager = Manager.init(args);

        // Set module initialization proceduer
        // This procedure will be invoked in activateManager() function.
        ConsoleInComp init = new ConsoleInComp();
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
