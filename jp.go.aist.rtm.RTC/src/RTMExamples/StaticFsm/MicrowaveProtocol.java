package RTMExamples.StaticFsm;

import RTC.TimedLong;
/**
 * 
 */
public interface MicrowaveProtocol {


    void open();

    void close();

    void minute(TimedLong time);

    void start();

    void stop();

    void tick();

}

