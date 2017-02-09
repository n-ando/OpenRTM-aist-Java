package RTMExamples.StaticFsm;

import jp.go.aist.rtm.RTC.jfsm.DataType;
import jp.go.aist.rtm.RTC.jfsm.State;
import jp.go.aist.rtm.RTC.jfsm.StateDef;

import RTC.TimedLong;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 
 */
@DataType(Top.Data.class)
public class Top extends StateDef implements MicrowaveProtocol {

    public static class Data {

        private int myCookingTime;
        public Data() {
            myCookingTime = 0;
        }

        public void printTimer() {
            System.out.println("  Timer set to "+ myCookingTime+ " minutes" );
        }

        public void incrementTimer() {
            myCookingTime += 1;
        }

        public void decrementTimer() {
            myCookingTime -= 1;
        }

        public void resetTimer() {
            myCookingTime = 0;
        }

        public int getRemainingTime() {
            return myCookingTime;
        }
    }

    @Override
    public void onInit() {
        System.out.println("[Microwave] TOP::onInit()");
        setState(new State(Operational.class));
    }

    @Override
    public void onEntry() {
        System.out.println("[Microwave] TOP::onEntry()");
    }

    @Override
    public void onExit() {
        System.out.println("[Microwave] TOP::onExit()");
    }

    @Override
    public void open() {
        // do nothing
    }

    @Override
    public void close() {
        // do nothing
    }

    @Override
    public void minute() {
        // do nothing
    }

    @Override
    public void start() {
        // do nothing
    }

    @Override
    public void stop() {
        // do nothing
    }

    @Override
    public void tick() {
        // do nothing
    }

}

