package RTMExamples.StaticFsm;

import jp.go.aist.rtm.RTC.jfsm.DeepHistory;
import jp.go.aist.rtm.RTC.jfsm.State;

import RTC.TimedLong;
/**
 * 
 */
@DeepHistory
public class ShootingCamera_Shooting extends ShootingCamera {

    @Override
    public void onEntry() {
        System.out.println("ShootingCamera_Shooting::onEntry");
    }

    @Override
    public void onInit() {
        System.out.println("ShootingCamera_Shooting::onInit");
        setState(new State(ShootingCamera_Focusing.class));
    }

    @Override
    public void on_do() {
        System.out.println("ShootingCamera_Shooting::on_do");
    }

}



