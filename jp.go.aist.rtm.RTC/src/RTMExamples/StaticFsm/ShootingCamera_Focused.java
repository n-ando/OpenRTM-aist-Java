package RTMExamples.StaticFsm;

import jp.go.aist.rtm.RTC.jfsm.DeepHistory;
import jp.go.aist.rtm.RTC.jfsm.State;

import RTC.TimedLong;
/**
 * 
 */
@DeepHistory
public class ShootingCamera_Focused extends ShootingCamera_Shooting {

    @Override
    public void onEntry() {
        System.out.println("ShootingCamera_Focused::onEntry");
    }

    @Override
    public void onExit() {
        System.out.println("ShootingCamera_Focused::onExit");
    }

    @Override
    public void on_do() {
        System.out.println("ShootingCamera_Focused::on_do");
    }

    @Override
    public void EvShutterFull(TimedLong param){
        System.out.println("ShootingCamera_Focused::EvShutterFull");
        setState(new State(ShootingCamera_Storing.class));
    }
}

