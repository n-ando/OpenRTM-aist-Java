package jp.go.aist.rtm.RTC.sample;

import jp.go.aist.rtm.RTC.DataFlowComponentBase;
import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.RtcNewFunc;


public class SampleComponentNew implements RtcNewFunc {

    public DataFlowComponentBase createRtc(Manager mgr) {
        return new SampleComponent(mgr);
    }

}
