package RTMExamples.StaticFsm;

import jp.go.aist.rtm.RTC.jfsm.DeepHistory;
import jp.go.aist.rtm.RTC.jfsm.State;

import RTC.TimedLong;
/**
 * 
 */
@DeepHistory
public class Programmed extends Operational {

    @Override
    public void minute(TimedLong time) {
        System.out.println("[Microwave] >>> Timer incremented <<<");
        Top.Data data = data(Top.class);
        for(int ic=0;ic<time.data;++ic){
            data.incrementTimer();
        }
        data.printTimer();
    }

    @Override
    public void start() {
        System.out.println("[Microwave] >>> Microwave started <<<");
        setState(new State(Cooking.class));
    }

    @Override
    public void onInit() {
        System.out.println("[Microwave] Programmed::onInit()");
    }

    @Override
    public void onEntry() {
        System.out.println("[Microwave] Programmed::onEntry()");
    }

    @Override
    public void onExit() {
        System.out.println("[Microwave] Programmed::onExit()");
    }

}
