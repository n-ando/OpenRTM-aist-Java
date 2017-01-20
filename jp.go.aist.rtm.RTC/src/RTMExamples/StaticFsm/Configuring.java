package RTMExamples.StaticFsm;

import jp.go.aist.rtm.RTC.jfsm.DeepHistory;
import jp.go.aist.rtm.RTC.jfsm.State;

import RTC.TimedLong;
/**
 * 
 */
@DeepHistory
public class Configuring extends NotShooting {

    @Override
    public void onEntry() {
        System.out.println("Configuring::onEntry");
        setOutputData(4);
    }

    @Override
    public void onExit() {
        System.out.println("Configuring::onExit");
    }

    @Override
    public void on_do() {
        System.out.println("Configuring::on_do");
    }

    @Override
    public void EvConfig(TimedLong param){
        System.out.println("Configuring::EvConfig");
        setState(new State(Idle.class));
    }

}

