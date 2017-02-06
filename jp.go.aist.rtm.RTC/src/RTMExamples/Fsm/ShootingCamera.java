package RTMExamples.Fsm;

import jp.go.aist.rtm.RTC.jfsm.DeepHistory;
import jp.go.aist.rtm.RTC.jfsm.State;

import RTC.TimedLong;
/**
 * 
 */
@DeepHistory
public class ShootingCamera extends Top {

    @Override
    public void onEntry() {
        System.out.println("ShootingCamera::onEntry");
        setOutputData(5);
    }

    @Override
    public void onInit() {
        System.out.println("ShootingCamera::onInit");
        setState(new State(ShootingCamera_Shooting.class));
    }

    @Override
    public void onExit() {
        System.out.println("ShootingCamera::onExit");
    }

    @Override
    public void on_do() {
        System.out.println("ShootingCamera::on_do");
    }

    @Override
    public void EvShutterReleased(TimedLong param){
        System.out.println("ShootingCamera::EvShutterReleased");
        setState(new State(NotShooting.class));
    }

}


