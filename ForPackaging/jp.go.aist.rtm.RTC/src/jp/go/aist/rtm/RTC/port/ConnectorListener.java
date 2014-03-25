package jp.go.aist.rtm.RTC.port;

import java.util.Observable;
import java.util.Observer;

public abstract class ConnectorListener implements Observer{
    public void update(Observable o, Object obj) {
           ConnectorBase.ConnectorInfo arg
               = (ConnectorBase.ConnectorInfo)obj;
           operator(arg);
    }
    public abstract void operator(final ConnectorBase.ConnectorInfo info);
}


