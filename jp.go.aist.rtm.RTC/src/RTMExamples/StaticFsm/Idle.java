package RTMExamples.StaticFsm;

import jp.go.aist.rtm.RTC.jfsm.DeepHistory;
import jp.go.aist.rtm.RTC.jfsm.Event;
import jp.go.aist.rtm.RTC.jfsm.State;

import RTC.TimedLong;
/**
 * 
 */
public class Idle extends Operational {

    @Override
    public void onEntry() {
        ((Top.Data) data()).resetTimer();
        System.out.println("[Microwave] Idle::onEntry()");
        System.out.println("[Microwave] >>> Microwave ready <<<");
    }

    @Override
    public void minute() {
        System.out.println("[Microwave] >>> Timer incremented <<<");
        setState(new State(Programmed.class));
        dispatch(new Event("minute"));
    }


}


