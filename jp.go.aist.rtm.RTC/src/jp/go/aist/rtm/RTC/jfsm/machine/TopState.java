package jp.go.aist.rtm.RTC.jfsm.machine;

import jp.go.aist.rtm.RTC.jfsm.Event;

public class TopState extends StateBase {

    public TopState() {
    }

    protected void dispatch(Event event) {
        stateInfo.machine.setPendingEvent(event);
    }
}
