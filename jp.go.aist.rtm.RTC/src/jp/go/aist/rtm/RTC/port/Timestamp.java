package jp.go.aist.rtm.RTC.port;

import jp.go.aist.rtm.RTC.connectorListener.ReturnCode;
import jp.go.aist.rtm.RTC.port.ConnectorBase;
import jp.go.aist.rtm.RTC.util.TimeValue;
import jp.go.aist.rtm.RTC.util.clock.ClockManager;
import jp.go.aist.rtm.RTC.util.clock.IClock;

public class Timestamp<DataType> extends ConnectorDataListenerT<DataType>{
    public Timestamp(final String name, Class cl){
        super(cl);
        m_tstype = name;
        m_clock = ClockManager.getInstance().getClock("system");
    }
    public ReturnCode operator(ConnectorBase.ConnectorInfo info,
                               DataType data) {
        if(!info.properties.getProperty("timestamp_policy").equals(m_tstype)){
            return ReturnCode.NO_CHANGE;
        }
        
        TimeValue tv = m_clock.getTime();
        RTC.Time tm = new RTC.Time((int)(tv.getSec()),
                                   (int)(tv.getUsec()*1000));
        Class cl = data.getClass();
        String str = cl.getName();
        try {
            cl.getField("tm").set(data,tm);
        }
        catch(NoSuchFieldException e){
            //getField throws
        }
        catch(IllegalAccessException e){
            //set throws
        }
        return ReturnCode.DATA_CHANGED;
    }

    public String m_tstype;
    private IClock m_clock;

}

