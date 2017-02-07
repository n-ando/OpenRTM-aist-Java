package RTMExamples.StaticFsm;

import jp.go.aist.rtm.RTC.jfsm.DeepHistory;
import jp.go.aist.rtm.RTC.jfsm.State;

import RTC.TimedLong;
/**
 * 
 */
public class Cooking extends Programmed {


    @Override
    public void onEntry() {
        System.out.println("[Microwave] Cooking::onEntry()");
        System.out.println("[Microwave] >>> Heating on <<<");
    }

    @Override
    public void onExit() {
        System.out.println("[Microwave] Cooking::onExit()");
        System.out.println("[Microwave] >>> Heating off <<<");
    }

    @Override
    public void tick() {
        System.out.println("[Microwave] >>> Clock tick <<<");
        Top.Data data = data(Top.class);
        data.decrementTimer();
        if (data.getRemainingTime() == 0) {
            System.out.println("[Microwave] >>> Finished <<<");
            setState(new State(Idle.class));
        } else {
            data.printTimer();
        }
    }
}
