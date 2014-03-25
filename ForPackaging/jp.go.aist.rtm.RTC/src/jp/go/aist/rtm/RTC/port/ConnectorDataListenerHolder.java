package jp.go.aist.rtm.RTC.port;

import java.util.Observable;

import org.omg.CORBA.portable.OutputStream;

  /**
   * <p> ConnectorDataListener holder class </p>
   *
   * <p> This class manages one ore more instances of ConnectorDataListener class. </p>
   *
   */
public class ConnectorDataListenerHolder extends Observable{
    public void notify(final ConnectorBase.ConnectorInfo info,
                final OutputStream cdrdata) {
        super.setChanged();
        ConnectorDataListenerArgument arg 
            = new ConnectorDataListenerArgument(info,cdrdata);
        super.notifyObservers((Object)arg);    
    }
    

}

