package jp.go.aist.rtm.RTC.port;

import java.util.Observable;

import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.log.Logbuf;

import org.omg.CORBA.portable.OutputStream;

  /**
   * <p> ConnectorDataListener holder class </p>
   *
   * <p> This class manages one ore more instances of ConnectorDataListener class. </p>
   *
   */
public class ConnectorDataListenerHolder<DataType> extends Observable{
    public ConnectorDataListenerHolder(){
        rtcout = new Logbuf("ConnectorDataListenerHolder");
    }
    public void notify(final ConnectorBase.ConnectorInfo info,
                final OutputStream cdrdata) {
        super.setChanged();
        //ConnectorDataListenerArgument arg 
        //    = new ConnectorDataListenerArgument(info,cdrdata);
        ConnectorDataListenerArgumentDataRef<OutputStream> arg 
            = new ConnectorDataListenerArgumentDataRef<OutputStream>(info,cdrdata);
        super.notifyObservers((Object)arg);    
    }
    
    //public void notify(final ConnectorBase.ConnectorInfo info, final DataRef<DataType> data) {
    public void notify(final ConnectorBase.ConnectorInfo info, 
                final DataType data) {
        super.setChanged();
        ConnectorDataListenerArgumentDataRef<DataType> arg 
            = new ConnectorDataListenerArgumentDataRef<DataType>(info,data);
        super.notifyObservers((Object)arg);
        super.clearChanged();
    }
    protected Logbuf rtcout;

}

