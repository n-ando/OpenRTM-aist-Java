package jp.go.aist.rtm.RTC.util;

import RTC.Time;
import RTC.TimedFloat;

public class TimedFloatFactory {

    public static TimedFloat create() {
        
        TimedFloat tf = new TimedFloat();
        tf.tm = new Time();
        
        return tf;
    }
}
