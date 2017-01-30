package jp.go.aist.rtm.RTC.port;

import java.util.Observable;

import jp.go.aist.rtm.RTC.connectorListener.ReturnCode;
import jp.go.aist.rtm.RTC.util.DataRef;
import jp.go.aist.rtm.RTC.log.Logbuf;

import org.omg.CORBA.portable.OutputStream;

  /**
   * <p> ConnectorDataListener holder class </p>
   *
   * <p> This class manages one ore more instances of ConnectorDataListener class. </p>
   *
   */
public class ConnectorDataListenerHolder<DataType> extends Observable
                            {
    public ConnectorDataListenerHolder(){
        rtcout = new Logbuf("ConnectorDataListenerHolder");
    }
    public ReturnCode notify(ConnectorBase.ConnectorInfo info,
                DataRef<OutputStream> cdrdata) {
        super.setChanged();
        ReturnCode ret =  ReturnCode.NO_CHANGE;
        //ConnectorDataListenerArgument arg 
        //    = new ConnectorDataListenerArgument(info,cdrdata);
        ConnectorDataListenerArgumentDataRef<OutputStream> arg 
            = new ConnectorDataListenerArgumentDataRef<OutputStream>(info,cdrdata.v);
        super.notifyObservers((Object)arg);
        cdrdata.v = arg.m_data;
        ret = arg.getReturnCode();
        return ret;
    }
    
    //public void notify(final ConnectorBase.ConnectorInfo info, final DataRef<DataType> data) {
    //public void notify(final ConnectorBase.ConnectorInfo info, 
    public ReturnCode notify(ConnectorBase.ConnectorInfo info, 
                DataType data) {
        super.setChanged();
        ReturnCode ret =  ReturnCode.NO_CHANGE;
        ConnectorDataListenerArgumentDataRef<DataType> arg 
            = new ConnectorDataListenerArgumentDataRef<DataType>(info,data);
        super.notifyObservers((Object)arg);
        super.clearChanged();
        ret = arg.getReturnCode();
        return ret;
    }
    protected Logbuf rtcout;
}

