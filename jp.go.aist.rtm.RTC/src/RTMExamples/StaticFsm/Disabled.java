package RTMExamples.StaticFsm;

import jp.go.aist.rtm.RTC.jfsm.DeepHistory;
import jp.go.aist.rtm.RTC.jfsm.State;

import RTC.TimedLong;
/**
 * 
 */
@DeepHistory
public class Disabled extends Top {

    @Override
    public void close() {
        System.out.println("[Microwave] >>> Door closed <<<");
        setState(new State(Operational.class));
    }

    @Override
    public void onEntry() {
        System.out.println("[Microwave] Disabled::onEntry()");
    }

    @Override
    public void onExit() {
        System.out.println("[Microwave] Disabled::onExit()");
    }
}

