package jp.go.aist.rtm.RTC.port;

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

import _SDOPackage.NVListHolder;
import OpenRTM.CdrDataHolder;

import jp.go.aist.rtm.RTC.FactoryGlobal;
import jp.go.aist.rtm.RTC.ObjectCreator;
import jp.go.aist.rtm.RTC.ObjectDestructor;
import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.port.ReturnCode;
import jp.go.aist.rtm.RTC.util.Properties;

/**
 * <p> OutPortCorbaCdrConsumer </p>
 *
 * <p> OutPortCorbaCdrConsumer class </p>
 *
 * <p> This is an implementation class of the output Consumer  </p>
 * <p> that uses CORBA for means of communication. </p>
 *
 * @param DataType Data type for this port
 *
 */
public class OutPortCorbaCdrConsumer extends CorbaConsumer< OpenRTM.OutPortCdrOperations > implements OutPortConsumer, ObjectCreator<OutPortConsumer>, ObjectDestructor {
    /*!
     * @brief Constructor
     *
     * Constructor
     *
     * @param buffer Buffer that is attached to this port
     *
     */
    public OutPortCorbaCdrConsumer() {
    }

    /*!
     *
     *
     * @brief Initializing configuration
     *
     * This operation would be called to configure in initialization.
     * In the concrete class, configuration should be performed
     * getting appropriate information from the given Properties data.
     * This function might be called right after instantiation and
     * connection sequence respectivly.  Therefore, this function
     * should be implemented assuming multiple call.
     *
     * @param prop Configuration information
     *
     */
    public void init(Properties prop) {
    }

    /*!
     * @brief Setting outside buffer's pointer
     *
     * A pointer to a buffer from which OutPortProvider retrieve data.
     * If already buffer is set, previous buffer's pointer will be
     * overwritten by the given pointer to a buffer.  Since
     * OutPortProvider does not assume ownership of the buffer
     * pointer, destructor of the buffer should be done by user.
     * 
     * @param buffer A pointer to a data buffer to be used by OutPortProvider
     *
     */
    public void setBuffer(BufferBase<OutputStream> buffer) {
    }

    /*!
     *
     * @brief Read data
     *
     * Read set data
     *
     * @param data Object to receive the read data
     *
     * @return Read result (Successful:true, Failed:false)
     *
     */
    public ReturnCode get(OutputStream data) {
        return ReturnCode.UNKNOWN_ERROR;
    }

    /*!
     *
     *
     *
     * @brief Subscribe the data receive notification
     *
     * Subscribe the data receive notification based on specified property
     * information
     *
     * @param properties Subscription information
     *
     * @return Subscription result (Successful:true, Failed:false)
     *
     */
    public boolean subscribeInterface(final NVListHolder properties) {
        return true;
    }
    
    /*!
     * @brief Unsubscribe the data receive notification
     *
     * Unsubscribe the data receive notification.
     *
     * @param properties Unsubscription information
     *
     */
    public void unsubscribeInterface(final NVListHolder properties) {
    }
    
    /**
     * <p> creator_ </p>
     * 
     * @return Object Created instances
     *
     */
    public OutPortConsumer creator_() {
        return new OutPortCorbaCdrConsumer();
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
     * <p> OutPortCorbaCdrConsumerInit </p>
     *
     */
    public static void OutPortCorbaCdrConsumerInit() {
        final FactoryGlobal<OutPortConsumer,String> factory 
            = FactoryGlobal.instance();

        factory.addFactory("corba_cdr",
                    new OutPortCorbaCdrConsumer(),
                    new OutPortCorbaCdrConsumer());
    
    }

    //    RTC::OutPortCdr_var m_outport;
    private BufferBase<OutputStream> m_buffer;

}
