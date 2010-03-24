package rtcd;

import jp.go.aist.rtm.RTC.Manager;
public class rtcd {

    public static void main(String[] args) {
        System.out.println("IN  main");
        // Initialize manager
        System.out.println("    Initialize manager");
        final Manager manager = Manager.init(args);

        // Activate manager and register to naming service
        System.out.println("    Activate manager ");
        manager.activateManager();

        // run the manager in blocking mode
        // runManager(false) is the default.
        System.out.println("    run the manager in blocking mode");
        manager.runManager();
        // If you want to run the manager in non-blocking mode, do like this
        // manager.runManager(true);
        //
    }

}

