package RTMExamples.StaticFsm;

import jp.go.aist.rtm.RTC.jfsm.DeepHistory;
import jp.go.aist.rtm.RTC.jfsm.State;

import RTC.TimedLong;
/**
 * 
 */
@DeepHistory
public class ShootingCamera_Focusing extends ShootingCamera_Shooting {

    @Override
    public void onEntry() {
        System.out.println("ShootingCamera_Focusing::onEntry");
    }

    @Override
    public void onExit() {
        System.out.println("ShootingCamera_Focusing::onExit");
    }

    @Override
    public void on_do() {
        System.out.println("ShootingCamera_Focusing::on_do");
    }

    @Override
    public void EvInFocus(TimedLong param){
        System.out.println("ShootingCamera_Focusing::EvInFocus");
        setState(new State(ShootingCamera_Focused.class));
    }

    @Override
    public void EvShutterFull(TimedLong param){
        System.out.println("ShootingCamera_Focusing::EvShutterFull");
    }
}




