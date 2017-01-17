package RTMExamples.StaticFsm;

import jp.go.aist.rtm.RTC.jfsm.DataType;
import jp.go.aist.rtm.RTC.jfsm.State;
import jp.go.aist.rtm.RTC.jfsm.StateDef;

import RTC.TimedLong;
/**
 * 
 */
//@DataType(Top.Data.class)
public class Top extends StateDef implements CameraProtocol {

    //private static final Logger LOGGER = LoggerFactory.getLogger(Top.class);
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
        System.out.println("Top::on_entry");
    }

    @Override
    public void onInit() {
        //setState(new State(Operational.class));
    }

    @Override
    public void on_do() {
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
/*
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
*/
}

