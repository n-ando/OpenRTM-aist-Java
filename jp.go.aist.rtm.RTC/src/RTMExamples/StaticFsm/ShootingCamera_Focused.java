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
    public void onInit() {
    }

    @Override
    public void on_do() {
    }


    @Override
    public void EvShutterFull(TimedLong param){
    }
}

