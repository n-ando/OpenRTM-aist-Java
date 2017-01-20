package RTMExamples.StaticFsm;

import RTC.TimedLong;
/**
 * 
 */
public interface CameraProtocol {

    void on_do();

    void EvOn(TimedLong param);
    void EvOff(TimedLong param);
    void EvConfig(TimedLong param);
    void EvInFocus(TimedLong param);
    void EvShutterHalf(TimedLong param);
    void EvShutterFull(TimedLong param);
    void EvShutterReleased(TimedLong param);

    public boolean isOutputData(); 
    public int getOutputData(); 
}

