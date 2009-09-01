package jp.go.aist.rtm.RTC.port;

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.SystemException;

import _SDOPackage.NVListHolder;
import OpenRTM.CdrDataHolder;
import OpenRTM.InPortCdrOperations;
import OpenRTM.InPortCdrPOA;
import OpenRTM.PortStatus;

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
public class InPortCorbaCdrProvider extends InPortCdrPOA implements InPortProvider  {
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

    private BufferBase<OutputStream> m_buffer;
    private OpenRTM.InPortCdr m_objref;

}
  

