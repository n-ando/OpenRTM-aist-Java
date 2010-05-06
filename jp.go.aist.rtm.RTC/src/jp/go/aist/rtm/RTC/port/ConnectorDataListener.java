package jp.go.aist.rtm.RTC.port;

import java.util.Observer;
import java.util.Observable;

import org.omg.CORBA.portable.OutputStream;

import jp.go.aist.rtm.RTC.port.ConnectorBase;
import jp.go.aist.rtm.RTC.util.DataRef;

  /**
   * {@.ja ConnectorDataListener クラス}
   * {@.en ConnectorDataListener class}
   *
   * <p>
   * {@.ja データポートの Connector において発生する各種イベントに対するコー
   *       ルバックを実現するリスナクラスの基底クラス。}
   * {@.en This class is abstract base class for listener classes that
   *       provides callbacks for various events in the data port's
   *       connectors.}
   * </p>
   *
   */
public abstract class ConnectorDataListener implements Observer{
    public void update(Observable o, Object obj) {
           ConnectorDataListenerArgument arg
               = (ConnectorDataListenerArgument)obj;
           operator(arg.m_info,arg.m_data);
    }
    public abstract void operator(final ConnectorBase.ConnectorInfo info, 
                                  final OutputStream data);
}


