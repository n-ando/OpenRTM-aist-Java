package jp.go.aist.rtm.RTC.port;

import java.lang.reflect.Field;
import java.util.Observable;
import java.util.Observer;

import org.omg.CORBA.portable.Streamable;

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
        ConnectorDataListenerArgument arg
               = (ConnectorDataListenerArgument)obj;
        try {
            m_streamable._read(arg.m_data.create_input_stream());
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
        operator(arg.m_info,m_datatype);
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
    public abstract void operator(final ConnectorBase.ConnectorInfo info, 
                                  final DataType data);
    private Streamable m_streamable = null;
    private Field m_field = null;
    private DataType m_datatype = null;
}



