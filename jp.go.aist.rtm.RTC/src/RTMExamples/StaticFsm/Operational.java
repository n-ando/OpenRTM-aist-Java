package RTMExamples.StaticFsm;

import jp.go.aist.rtm.RTC.jfsm.DeepHistory;
import jp.go.aist.rtm.RTC.jfsm.State;

import RTC.TimedLong;
/**
 * 
 */
@DeepHistory
public class Operational extends Top {

    @Override
    public void onInit() {
        System.out.println("[Microwave] Operational::onInit()");
        setState(new State(Idle.class));
    }

    @Override
    public void open() {
        System.out.println("[Microwave] >>> Door opened <<<");
        setState(new State(Disabled.class));
    }

    @Override
    public void stop() {
        System.out.println("[Microwave] >>> Stopped <<<");
        setState(new State(Idle.class));
    }

}


