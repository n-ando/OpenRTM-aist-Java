package RTMExamples.StaticFsm;

import RTC.TimedLong;
/**
 * 
 */
public interface MicrowaveProtocol {


    void open();

    void close();

    void minute();

    void start();

    void stop();

    void tick();

}

