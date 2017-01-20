package RTMExamples.StaticFsm;

import jp.go.aist.rtm.RTC.jfsm.DeepHistory;
import jp.go.aist.rtm.RTC.jfsm.State;

import RTC.TimedLong;
/**
 * 
 */
@DeepHistory
public class NotShooting extends Top {

    @Override
    public void onEntry() {
        System.out.println("NotShooting::onEntry");
        setOutputData(2);
    }

    @Override
    public void onInit() {
        System.out.println("NotShooting::onInit");
        setState(new State(Idle.class));
    }

    @Override
    public void onExit() {
        System.out.println("NotShooting::onExit");
    }

    @Override
    public void on_do() {
        System.out.println("NotShooting::on_do");
    }

    @Override
    public void EvOff(TimedLong param){
        System.out.println("NotShooting::EvOff");
        setState(new State(Off.class));
    }

    @Override
    public void EvShutterHalf(TimedLong param){
        System.out.println("NotShooting::EvShutterHalf");
        setState(new State(ShootingCamera.class));
    }
}


