package jp.go.aist.rtm.RTC.port;

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.SystemException;

import _SDOPackage.NVListHolder;
import OpenRTM.CdrDataHolder;
import OpenRTM.InPortCdrOperations;
import OpenRTM.InPortCdrPOA;
import OpenRTM.PortStatus;

import jp.go.aist.rtm.RTC.FactoryGlobal;
import jp.go.aist.rtm.RTC.ObjectCreator;
import jp.go.aist.rtm.RTC.ObjectDestructor;
import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.util.Properties;

/*!
 * @class InPortCorbaCdrProvider
 * @brief InPortCorbaCdrProvider class
 *
 * This is an implementation class of the input port Provider 
 * that uses CORBA for means of communication.
 *
 * @param DataType Data type held by the buffer that attached 
 *                 to this provider.
 *
 */
//public class InPortCorbaCdrProvider extends InPortCdrPOA implements InPortProvider  {
public class InPortCorbaCdrProvider extends InPortProviderImpl implements ObjectCreator<InPortProviderImpl>, ObjectDestructor {
    /*!
     * @brief Constructor
     *
     * Constructor
     * Set the following items to port properties
     *  - Interface type : CORBA_Any
     *  - Data flow type : Push, Pull
     *  - Subscription type : Any
     *
     * @param buffer Buffer object that is attached to this provider
     *
     */
    public InPortCorbaCdrProvider() {
    }
    public void init(Properties prop){
    }
    public void setBuffer(BufferBase<OutputStream> buffer) {
    }

    /*!
     * @brief [CORBA interface] Write data into the buffer
     *
     * Write data into the specified buffer.
     *
     * @param data The target data for writing
     *
     */
    public OpenRTM.PortStatus put(byte[] data)
      throws SystemException {
         return OpenRTM.PortStatus.PORT_OK;
    }
    public OpenRTM.PortStatus put(final OpenRTM.CdrDataHolder data)
      throws SystemException {
         return OpenRTM.PortStatus.PORT_OK;
    }
    
    public void publishInterface(NVListHolder properties) {
    }
    public void publishInterfaceProfile(NVListHolder properties) {
    }

    /**
     * <p> creator_ </p>
     * 
     * @return Object Created instances
     *
     */
    public InPortProviderImpl creator_() {
        return new InPortCorbaCdrProvider();
    }
    /**
     * <p> destructor_ </p>
     * 
     * @param obj    The target instances for destruction
     *
     */
    public void destructor_(Object obj) {
        obj = null;
    }

    /**
     * <p> InPortCorbaCdrProviderInit </p>
     *
     */
    public static void InPortCorbaCdrProviderInit() {
        final FactoryGlobal<InPortProviderImpl,String> factory 
            = FactoryGlobal.instance();

        factory.addFactory("corba_cdr",
                    new InPortCorbaCdrProvider(),
                    new InPortCorbaCdrProvider());
    
    }

    private BufferBase<OutputStream> m_buffer;
    private OpenRTM.InPortCdr m_objref;

}
  

