package jp.go.aist.rtm.RTC.port;

import java.util.Observer;
import java.util.Observable;

import java.lang.reflect.Field;

import org.omg.CORBA.portable.Streamable;
import org.omg.CORBA.portable.OutputStream;

import jp.go.aist.rtm.RTC.port.ConnectorBase;
import jp.go.aist.rtm.RTC.util.DataRef;

  /**
   * <p> ConnectorDataListenerT class </p>
   *
   * <p>This class is abstract base class for listener classes that
   * provides callbacks for various events in the data port's
   * connectors.<p>
   *
   * This class template can have practical data types that are used
   * as typed variable for DataPort as an argument of template instead
   * of cdrMemoryStream.
   *
   */
public abstract class ConnectorDataListenerT<DataType> implements Observer{
    /**
     * <p> Constructor </p>
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
     * <p>   </p> 
     * 
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
     * <p>   </p> 
     *
     */
    public abstract void operator(final ConnectorBase.ConnectorInfo info, 
                                  final DataType data);
    private Streamable m_streamable = null;
    private Field m_field = null;
    private DataType m_datatype = null;
}



