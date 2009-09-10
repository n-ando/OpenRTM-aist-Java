package jp.go.aist.rtm.RTC.port;

import java.util.Vector;

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

import RTC.ConnectorProfile;
import RTC.ConnectorProfileHolder;
import RTC.ReturnCode_t;

import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.port.ReturnCode;
import jp.go.aist.rtm.RTC.util.Properties;
/**
 * <p> InPortBase </p>
 * <p> Port for InPort </p>
 *
 * <p> This is an implementation class for the data input port. <p>
 *
 */
public class InPortBase extends PortBase {

//    typedef std::vector<InPortConnector*> ConnectorList;

    /**
     *
     * <p> Constructor </p>
     * @param name Port name
     * @param inport InPort object that is associated with this data input port.
     *               Specify also the data type and the buffer type used in 
     *               the InPort object.
     */
    public InPortBase(final String name, final String data_type) {
    }
    
    public Properties properties() {
        return new Properties();
    }

    public void init() {
      
    }
    /**
     * <p> Activate all Port interfaces </p>
     *
     * <p> This operation activate all interfaces that is registered in the </p>
     * <p> ports. </p>
     *
     */
    public void activateInterfaces() {
    ;
    }

    /**
     *
     * <p> Deactivate all Port interfaces </p>
     *
     * <p> This operation deactivate all interfaces that is registered in the </p>
     * <p> ports. </p>
     *
     */
    public void deactivateInterfaces() {
    }

    /**
     * <p> Publish interface information </p>
     *
     * <p> Publish interface information. </p>
     * <p> Assign the Provider information that owned by this port </p>
     * <p> to ConnectorProfile#properties </p>
     *
     * @param connector_profile The connector profile
     *
     * @return The return code of ReturnCode_t type
     *
     */
    protected ReturnCode_t publishInterfaces(ConnectorProfileHolder connector_profile) {
        return ReturnCode_t.RTC_OK;
    }
/*
    protected ReturnCode_t
    publishInterfaces(ConnectorProfile connector_profile) {
    }
*/
    
    /**
     * <p> Subscribe to the interface </p>
     *
     * <p> Subscribe to interface. </p>
     * <p> Derive Provider information that matches Consumer owned by the Port </p>
     * <p> from ConnectorProfile#properties and  </p>
     * <p> set the Consumer to the reference of the CORBA object. </p>
     *
     * @param connector_profile The connector profile
     *
     * @return ReturnCode_t The return code of ReturnCode_t type
     *
     */
    protected ReturnCode_t subscribeInterfaces(
            final ConnectorProfileHolder connector_profile) {
        return ReturnCode_t.RTC_OK;
    }
/*
    protected ReturnCode_t
    subscribeInterfaces(final ConnectorProfile connector_profile) {
    }
*/
    
    /**
     * <p> Disconnect the interface connection </p>
     *
     * <p> Disconnect the interface connection. </p>
     * <p> Release all objects set in Consumer associated with </p>
     * <p> given ConnectorProfile and unscribe the interface. </p>
     *
     * @param connector_profile The connector profile
     *
     */
    protected void
    unsubscribeInterfaces(final ConnectorProfile connector_profile) {
    }


    /**
     * <p> InPort provider initialization </p>
     */
    protected void initProviders() {
        ;
    }

    /*!
     * <p> OutPort consumer initialization </p>
     */
    protected void initConsumers() {
    }

    /**
     * <p> InPort provider creation </p>
     * <p> InPortProvider is created, </p>
     * <p> and information is published to ConnectorProfile. </p>
     * <p> 0 is returned if failing in creation. </p>
     */
    protected InPortProvider
    createProvider(ConnectorProfile cprof, Properties prop) {
        return new InPortCorbaCdrProvider();
    }

    /**
     * <p> InPort provider creation </p>
     * <p> OutPortConsumer is created. </p>
     * <p> 0 is returned if failing in creation. </p>
     */
    protected OutPortConsumer
    createConsumer(final ConnectorProfile cprof, Properties prop) {
        return new OutPortCorbaCdrConsumer();
    }

    /**
     * <p> InPortPushConnector creation </p>
     * <p> Connector is created, </p>
     * <p> preserves it in m_connectors. </p>
     * <p> 0 is returned if failing in creation. </p>
     */
    protected InPortConnector
    createConnector(ConnectorProfile cprof, Properties prop,
                    InPortProvider provider) {

        InPortConnector connector = null;
        return connector;
    }
    /**
     * <p> InPortPullConnector creation </p>
     * <p> Connector is created, </p>
     * <p> preserves it in m_connectors. </p>
     * <p> 0 is returned if failing in creation. </p>
     */
    protected InPortConnector
    createConnector(final ConnectorProfile cprof, Properties prop,
                    OutPortConsumer consumer) {
        InPortConnector connector = null;
        return connector;
    }
    protected boolean m_singlebuffer;
    protected BufferBase<OutputStream> m_thebuffer;
    protected Properties m_properties;
    protected Vector<String> m_providerTypes;
    protected Vector<String> m_consumerTypes;
    protected Vector<InPortConnector> m_connectors;

}


