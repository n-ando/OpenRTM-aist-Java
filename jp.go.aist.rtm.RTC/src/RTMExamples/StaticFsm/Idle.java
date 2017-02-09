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
    public void minute(TimedLong time) {
        System.out.println("[Microwave] >>> Timer incremented <<<");
        setState(new State(Programmed.class));
        Class<?>[] args = new Class<?>[1];
        args[0] = time.getClass();
        dispatch(new Event("minute",args,time));
    }


}


