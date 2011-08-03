package jp.go.aist.rtm.RTC.util;

import jp.go.aist.rtm.RTC.util.ListenerBase;

public class TimerOperation implements ListenerBase {

    public void invoke() {
        System.out.println("invoke\n");
    }

}
