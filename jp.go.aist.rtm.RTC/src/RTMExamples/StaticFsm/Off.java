package RTMExamples.StaticFsm;

import jp.go.aist.rtm.RTC.jfsm.DeepHistory;
import jp.go.aist.rtm.RTC.jfsm.State;

import RTC.TimedLong;
/**
 * 
 */
@DeepHistory
public class Off extends Top {

    @Override
    public void onEntry() {
        System.out.println("Off::onEntry");
        setOutputData(1);
    }

    @Override
    public void onExit() {
        System.out.println("Off::onExit");
    }

    @Override
    public void on_do() {
        System.out.println("Off::on_do");
    }

    @Override
    public void EvOn(TimedLong param){
        System.out.println("Off::EvOn");
        setState(new State(NotShooting.class));
    }
}

