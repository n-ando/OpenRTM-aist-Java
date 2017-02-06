package RTMExamples.Fsm;

import jp.go.aist.rtm.RTC.jfsm.DeepHistory;
import jp.go.aist.rtm.RTC.jfsm.State;

import RTC.TimedLong;
/**
 * 
 */
@DeepHistory
public class Idle extends NotShooting {

    @Override
    public void onEntry() {
        System.out.println("Idle::onEntry");
        setOutputData(3);
    }

    @Override
    public void onExit() {
        System.out.println("Idle::onExit");
    }

    @Override
    public void on_do() {
        System.out.println("Idle::on_do");
    }

    @Override
    public void EvConfig(TimedLong param){
        System.out.println("Idle::EvConfig");
        setState(new State(Configuring.class));
    }

}


