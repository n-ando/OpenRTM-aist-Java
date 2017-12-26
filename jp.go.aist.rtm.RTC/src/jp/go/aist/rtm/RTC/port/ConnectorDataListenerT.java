package jp.go.aist.rtm.RTC.port;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import org.omg.CORBA.portable.Streamable;
import org.omg.CORBA.portable.OutputStream;

import jp.go.aist.rtm.RTC.connectorListener.ReturnCode;
import jp.go.aist.rtm.RTC.log.Logbuf;

import jp.go.aist.rtm.RTC.util.ORBUtil;
import jp.go.aist.rtm.RTC.util.Properties;

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
            Class helper = Class.forName(class_name+"Helper");

            Method method = helper.getMethod("id");
            m_id =  (String)method.invoke( null );
        }
        catch(NoSuchFieldException e){
            //getField throws
            e.printStackTrace();
        }
        catch(NoSuchMethodException e){
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
        catch(InvocationTargetException e){
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
        rtcout.println(Logbuf.PARANOID, "update");
        ConnectorDataListenerArgumentDataRef<DataType> arg 
            = (ConnectorDataListenerArgumentDataRef<DataType>)obj;
        String type = arg.m_info.properties.getProperty("interface_type");

        String data_type = arg.m_info.properties.getProperty("data_type");
        rtcout.println(Logbuf.PARANOID, "interface_type:"+type);
        rtcout.println(Logbuf.PARANOID, "data_type:"+data_type);
        rtcout.println(Logbuf.PARANOID, "m_id:"+m_id);
        if(data_type.isEmpty()) {
             data_type = m_id;
        } 
        if(!m_id.equals(data_type)) {
            return;
        }
        if(type.equals("direct")) {
            m_datatype = (DataType)arg.m_data;
        }
        else { 
            try {
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
            arg.m_data  = m_datatype;
            try {

                String endian_type = arg.m_info.properties.
                        getProperty("serializer.cdr.endian","little");
                rtcout.println(Logbuf.PARANOID, 
                        "serializer.cdr.endian:"+endian_type);
                String[] endian = endian_type.split(",");
                boolean endian_flag = true;
                rtcout.println(Logbuf.PARANOID, 
                        "serializer.cdr.endian[0]:"+endian[0]);
                if(!endian[0].equals("little")){
                    endian_flag = false;
                }
                OutputStream out_data 
                    = new EncapsOutputStreamExt(ORBUtil.getOrb(),endian_flag);
                m_field.set(m_streamable,m_datatype);
                m_streamable._write(out_data);

                Class cl = obj.getClass();
                String str = cl.getName();
                rtcout.println(Logbuf.PARANOID,"class name:"+str);
                cl.getField("m_data").set(obj,out_data);
            }
            catch(NoSuchFieldException e){
                rtcout.println(Logbuf.WARN, 
                        "Exception caught."+e.toString());
            }
            catch(IllegalAccessException e){
                rtcout.println(Logbuf.WARN, 
                        "Exception caught."+e.toString());
            }
            catch(IllegalArgumentException e){
                rtcout.println(Logbuf.WARN, 
                        "Exception caught."+e.toString());
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
    public abstract ReturnCode operator(ConnectorBase.ConnectorInfo info, 
                                  DataType data);
    private Streamable m_streamable = null;
    private Field m_field = null;
    private DataType m_datatype = null;
    protected Logbuf rtcout;
    protected String m_id;
}



