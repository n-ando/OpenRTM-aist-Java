package jp.go.aist.rtm.RTC.util;

import RTC.Time;
import RTC.TimedFloat;

/**
 * {@.ja TimedFloatのファクトリ}
 * {@.en Implementation of TimedFloat factory}
 */
public class TimedFloatFactory {

    /**
     * {@.ja TimedFloatを生成する。}
     * {@.en Creates TimedFloat}
     * 
     * @return 
     *   {@.ja 生成されたTimedFloatオブジェクト}
     *   {@.en Created TimedFloat object}
     */
    public static TimedFloat create() {
        
        TimedFloat tf = new TimedFloat();
        tf.tm = new Time();
        
        return tf;
    }
}
