package RTMExamples.StaticFsm;

import jp.go.aist.rtm.RTC.jfsm.DeepHistory;
import jp.go.aist.rtm.RTC.jfsm.State;

import RTC.TimedLong;
/**
 * 
 */
@DeepHistory
public class ShootingCamera_Storing extends ShootingCamera_Shooting {


    @Override
    public void onEntry() {
        System.out.println("ShootingCamera_Storing::onEntry");
        setOutputData(9);
    }

    @Override
    public void onExit() {
        System.out.println("ShootingCamera_Storing::onExit");
    }

    @Override
    public void on_do() {
        System.out.println("ShootingCamera_Storing::on_do");
    }

}


