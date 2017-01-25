package jp.go.aist.rtm.RTC.port;

import java.lang.reflect.Field;
import java.util.Observable;
import java.util.Observer;

import org.omg.CORBA.portable.Streamable;
import org.omg.CORBA.portable.OutputStream;

import jp.go.aist.rtm.RTC.connectorListener.ReturnCode;
import jp.go.aist.rtm.RTC.log.Logbuf;

import RTC.ReturnCode_t;

  /**
   * {@.ja ConnectorDataListenerTクラス}
   * {@.en ConnectorDataListenerT class}
   *
   * <p>
   * {@.ja データポートの Connector において発生する各種イベントに対するコー
   *       ルバックを実現するリスナクラスの基底クラス。}
   * {@.en This class is abstract base class for listener classes that
   * provides callbacks for various events in the data port's
   * connectors.}
   *
   */
public abstract class ConnectorDataListenerT<DataType> implements Observer{
    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     */
    public ConnectorDataListenerT(Class<DataType> cl) {
        rtcout = new Logbuf("ConnectorDataListenerT");
        String class_name = cl.getName();
        try {
            Class datatype = Class.forName(class_name,
                                         true,
                                         this.getClass().getClassLoader());
            m_datatype = (DataType)datatype.newInstance();
            Class holder = Class.forName(class_name+"Holder",
                                         true,
                                         this.getClass().getClassLoader());
            m_streamable = (Streamable)holder.newInstance();
            m_field = m_streamable.getClass().getField("value");
        }
        catch(NoSuchFieldException e){
            //getField throws
            e.printStackTrace();
        }
        catch(java.lang.InstantiationException e){
            e.printStackTrace();
        }
        catch(ClassNotFoundException e){
            //forName throws
            e.printStackTrace();
        }
        catch(IllegalAccessException e){
            //set throws
            e.printStackTrace();
        }
        catch(IllegalArgumentException e){
            //invoke throws
            e.printStackTrace();
        }
    }
    /**
     * {@.ja コールバックメソッド}
     * {@.en  Callback method}
     * <p>
     * {@.ja データをデータポートで使用される変数型に変換して 
     * ConnectorDataListenerTのコールバックメソッドを呼び出す。}
     * {@.en This method invokes the callback method of ConnectorDataListenerT. 
     * Data is converted into the variable type used in DataPort.}
     *
     * @param o 
     *   {@.ja Observable}
     *   {@.en Observable}
     * @param obj
     *   {@.ja Object}
     *   {@.en Object}
     */
    public void update(Observable o, Object obj) {
        //ConnectorDataListenerArgument arg
        //       = (ConnectorDataListenerArgument)obj;
        ConnectorDataListenerArgumentDataRef<DataType> arg 
            = (ConnectorDataListenerArgumentDataRef<DataType>)obj;
        String type = arg.m_info.properties.getProperty("interface_type");
        //rtcout.println(Logbuf.TRACE, "interface_type:"+type);
        if(type.equals("direct")) {
            m_datatype = (DataType)arg.m_data;
        }
        else { 
            try {
                //m_streamable._read(arg.m_data.create_input_stream());
                OutputStream out_data = (OutputStream)arg.m_data;
                m_streamable._read(out_data.create_input_stream());
                m_datatype = (DataType)m_field.get(m_streamable);
            }
            catch(IllegalAccessException e){
                //set throws
                e.printStackTrace();
            }
            catch(IllegalArgumentException e){
                //invoke throws
                e.printStackTrace();
            }
        }
        ReturnCode ret = operator(arg.m_info,m_datatype);
        if (ret == ReturnCode.DATA_CHANGED || ret == ReturnCode.BOTH_CHANGED) {
            //cdrdata.rewindPtrs();
            //data >>= cdrdata;
            try {
                OutputStream out_data = (OutputStream)arg.m_data;
                m_field.set(m_streamable,m_datatype);
                m_streamable._write(out_data);
            }
            catch(IllegalAccessException e){
                //set throws
                e.printStackTrace();
            }
            catch(IllegalArgumentException e){
                //invoke throws
                e.printStackTrace();
            }
        }
        arg.setReturnCode(ret);
    }
    /**
     * {@.ja 抽象コールバックメソッド}
     * {@.en Abstract Callback method}
     * <p>
     * {@.ja データポートの Connector において発生する各種イベントに対するコー
     * ルバックメソッド}
     * {@.en This method invokes the callback method of ConnectorDataListenerT. 
     * Data is converted into the variable type used in DataPort.}
     *
     * @param info 
     *   {@.ja ConnectorInfo}
     *   {@.en ConnectorInfo}
     * @param data 
     *   {@.ja データ}
     *   {@.en Data}
     *
     */
    //public abstract void operator(final ConnectorBase.ConnectorInfo info, 
    //                              final DataType data);
    public abstract ReturnCode operator(ConnectorBase.ConnectorInfo info, 
                                  DataType data);
    private Streamable m_streamable = null;
    private Field m_field = null;
    private DataType m_datatype = null;
    protected Logbuf rtcout;
}



