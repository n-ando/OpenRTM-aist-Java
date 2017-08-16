package RTMExamples.Fsm;

import jp.go.aist.rtm.RTC.jfsm.DataType;
import jp.go.aist.rtm.RTC.jfsm.State;
import jp.go.aist.rtm.RTC.jfsm.StateDef;

import RTC.TimedLong;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 
 */
//@DataType(Top.Data.class)
public class Top extends StateDef implements CameraProtocol {

/*
    public static class Data {

        private int myCookingTime;
// pyfsm/test/api_test.py:10
//    class Data(object):
//        def __init__(self):
//            self.my_cooking_time = 0
//
//        def print_timer(self):
//            pyfsm_logger.debug("  Timer set to {0} minutes".format(self.my_cooking_time))
//
//        def increment_timer(self):
//            self.my_cooking_time += 1
//
//        def decrement_timer(self):
//            self.my_cooking_time -= 1
//
//        def reset_timer(self):
//            self.my_cooking_time = 0
//
//        def get_remaining_time(self):
//            return self.my_cooking_time

        public Data() {
            myCookingTime = 0;
        }

        public void printTimer() {
            LOGGER.debug("  Timer set to {} minutes", myCookingTime);
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
*/

    @Override
    public void onEntry() {
        System.out.println("Top::onEntry");
        setOutputData(0);
    }

    @Override
    public void onInit() {
        System.out.println("Top::onInit");
        setState(new State(Off.class));
    }

    @Override
    public void onExit() {
        System.out.println("Top::onExit");
    }

    @Override
    public void on_do() {
        System.out.println("Top::on_do");
    }

    @Override
    public void EvOn(TimedLong param){ 
    }
    @Override
    public void EvOff(TimedLong param){ 
    }
    @Override
    public void EvConfig(TimedLong param){
    }
    @Override
    public void EvInFocus(TimedLong param){
    }
    @Override
    public void EvShutterHalf(TimedLong param){
    }
    @Override
    public void EvShutterFull(TimedLong param){
    }
    @Override
    public void EvShutterReleased(TimedLong param){
    }

    @Override
    public int getOutputData() {
        Integer data = m_que.poll();
        if(data == null) {
            return 0;
        }
        else {
            return data.intValue(); 
        }
    }

    @Override
    public boolean isOutputData() {
        return m_que.isEmpty();
    }

    protected void setOutputData(int val) {
         m_que.offer(val);
    }

    private int m_val;
    private static Queue<Integer> m_que = new ArrayDeque<Integer>();;
}

