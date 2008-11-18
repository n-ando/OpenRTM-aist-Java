package jp.go.aist.rtm.RTC.sample;

import jp.go.aist.rtm.RTC.Manager;

public class ResetSampleComponentComp { 
    public static void main(String[] args) {
        final Manager manager = Manager.init(args);

        // Initialize manager
        manager.init(args);
        // Initialize my module on this maneger
        ResetSampleComponentInit init = new ResetSampleComponentInit();
        manager.setModuleInitProc(init);
        // Activate manager and register to naming service
        manager.activateManager();
        new Thread(new Runnable(){
            public void run(){
                manager.runManager();
            }
        }).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
////            manager.cleanupComponent("mod_cxt:HelloRTWorld", "cate_cxt:example");
//            manager.cleanupComponent(this);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // manager.shutdown();
        // Main loop
    }
}
