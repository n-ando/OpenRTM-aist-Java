package jp.go.aist.rtm.RTC.sample;

import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.RtcDeleteFunc;

public class SampleComponentDelete implements RtcDeleteFunc {

    public void deleteRtc(RTObject_impl rtcBase) {
        rtcBase = null;
    }

}
