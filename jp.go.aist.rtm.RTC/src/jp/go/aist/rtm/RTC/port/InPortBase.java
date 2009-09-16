package jp.go.aist.rtm.RTC.port;

import java.util.Vector;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

import _SDOPackage.NVListHolder;
import RTC.ConnectorProfile;
import RTC.ConnectorProfileHolder;
import RTC.ReturnCode_t;

import jp.go.aist.rtm.RTC.FactoryGlobal;
import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.buffer.RingBuffer;
import jp.go.aist.rtm.RTC.port.ReturnCode;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.StringUtil;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;

 
/**
 * <p> InPortBase </p>
 * <p> Port for InPort </p>
 *
 * <p> This is an implementation class for the data input port. <p>
 *
 */
public class InPortBase extends PortBase {


    /**
     *
     * <p> Constructor </p>
     * @param name Port name
     * @param data_type Specify the data type used in the InPort object.
     */
    public InPortBase(final String name, final String data_type) {
        super(name);

        m_singlebuffer = true;
        m_thebuffer = null;
        // Set PortProfile::properties
        addProperty("port.port_type", "DataInPort",String.class);
        addProperty("dataport.data_type", data_type,String.class);
        addProperty("dataport.subscription_type", "Any",String.class);
    }
    
    public Properties properties() {
        rtcout.println(rtcout.TRACE, "properties()");
    
        return m_properties;
    }

    /**
     * <p> init </p>
     */
    public void init() {
        rtcout.println(rtcout.TRACE, "init()");

        if (m_singlebuffer) {
            rtcout.println(rtcout.DEBUG, "single buffer mode.");
            final FactoryGlobal<RingBuffer<OutputStream>,String> factory 
                    = FactoryGlobal.instance();
            m_thebuffer = factory.createObject("ring_buffer");

            if (m_thebuffer == null) {
                rtcout.println(rtcout.ERROR, "default buffer creation failed.");
            }
        }
        else {
            rtcout.println(rtcout.DEBUG, "multi buffer mode.");
        }
        initProviders();
        initConsumers();
      
    }
    /**
     * <p> Activate all Port interfaces </p>
     *
     * <p> This operation activate all interfaces that is registered in the </p>
     * <p> ports. </p>
     *
     */
    public void activateInterfaces() {
        rtcout.println(rtcout.TRACE, "activateInterfaces()");

        for (int i=0, len=m_connectors.size(); i < len; ++i) {
            m_connectors.elementAt(i).activate();
            rtcout.println(rtcout.DEBUG, 
                           "activate connector: "
                                + m_connectors.elementAt(i).name()
                                +" "
                                +m_connectors.elementAt(i).id());
          }
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
        rtcout.println(rtcout.TRACE, "deactivateInterfaces()");

        for (int i=0, len=m_connectors.size(); i < len; ++i) {
            m_connectors.elementAt(i).deactivate();
            rtcout.println(rtcout.DEBUG, 
                           "deactivate connector: "
                                + m_connectors.elementAt(i).name()
                                +" "
                                +m_connectors.elementAt(i).id());
        }
    }

    /**
     * <p> Publish interface information </p>
     *
     * <p> Publish interface information. </p>
     * <p> Assign the Provider information that owned by this port </p>
     * <p> to ConnectorProfile#properties </p>
     *
     * @param cprof The connector profile
     *
     * @return The return code of ReturnCode_t type
     *
     */
    protected ReturnCode_t publishInterfaces(ConnectorProfileHolder cprof) {
        rtcout.println(rtcout.TRACE, "publishInterfaces()");

        // prop: [port.outport].
        Properties prop = m_properties;
        {
            Properties conn_prop = new Properties();
            NVListHolder nvlist = new NVListHolder(cprof.value.properties);
            NVUtil.copyToProperties(conn_prop, nvlist);
            prop.merge(conn_prop.getNode("dataport")); //merge ConnectorProfile
        }

        /*
         * Because properties of ConnectorProfileHolder was merged, 
         * the accesses such as prop["dataflow_type"] and 
         * prop["interface_type"] become possible here.
         */
        String dflow_type = prop.getProperty("dataflow_type");
        StringUtil.normalize(dflow_type);

        if (dflow_type == "push") {
            rtcout.println(rtcout.DEBUG, 
                           "dataflow_type = push .... create PushConnector");

            // create InPortProvider
            InPortProvider provider = createProvider(cprof, prop);
            if (provider == null) {
                return ReturnCode_t.BAD_PARAMETER;
            }

            // create InPortPushConnector
            InPortConnector connector = createConnector(cprof, prop, provider);
            if (connector == null) {
                return ReturnCode_t.RTC_ERROR;
            }

            rtcout.println(rtcout.DEBUG, 
                           "publishInterface() successfully finished.");
            return ReturnCode_t.RTC_OK;
        }
        else if (dflow_type == "pull") {
            rtcout.println(rtcout.DEBUG, 
                           "dataflow_type = pull .... do nothing.");
            return ReturnCode_t.RTC_OK;
        }

        rtcout.println(rtcout.ERROR, "unsupported dataflow_type.");
        return ReturnCode_t.BAD_PARAMETER;
    }
    
    /**
     * <p> Subscribe to the interface </p>
     *
     * <p> Subscribe to interface. </p>
     * <p> Derive Provider information that matches Consumer owned by the Port </p>
     * <p> from ConnectorProfile#properties and  </p>
     * <p> set the Consumer to the reference of the CORBA object. </p>
     *
     * @param cprof The connector profile
     *
     * @return ReturnCode_t The return code of ReturnCode_t type
     *
     */
    protected ReturnCode_t subscribeInterfaces(
            final ConnectorProfileHolder cprof) {
        rtcout.println(rtcout.TRACE, "subscribeInterfaces()");

        // prop: [port.outport].
        Properties prop = m_properties;
        {
            Properties conn_prop = new Properties();
            NVListHolder nvlist = new NVListHolder(cprof.value.properties);
            NVUtil.copyToProperties(conn_prop, nvlist);
            prop.merge(conn_prop.getNode("dataport")); //merge ConnectorProfile
        }

        /*
         * Because properties of ConnectorProfileHolder was merged, 
         * the accesses such as prop["dataflow_type"] and 
         * prop["interface_type"] become possible here.
         */
        String dflow_type = prop.getProperty("dataflow_type");
        StringUtil.normalize(dflow_type);

        if (dflow_type == "push") {
            rtcout.println(rtcout.DEBUG, 
                           "dataflow_type = push .... do nothing.");
            return ReturnCode_t.RTC_OK;
        }
        else if (dflow_type == "pull") {
            rtcout.println(rtcout.DEBUG, 
                           "dataflow_type = pull .... create PullConnector.");

            // create OutPortConsumer
            OutPortConsumer consumer = createConsumer(cprof, prop);
            if (consumer == null) {
                return ReturnCode_t.BAD_PARAMETER;
            }

            // create InPortPullConnector
            InPortConnector connector = createConnector(cprof, prop, consumer);
            if (connector == null) {
                return ReturnCode_t.RTC_ERROR;
            }

            rtcout.println(rtcout.DEBUG, 
                           "publishInterface() successfully finished.");
            return ReturnCode_t.RTC_OK;
        }

        rtcout.println(rtcout.ERROR, 
                       "unsupported dataflow_type.");
        return ReturnCode_t.BAD_PARAMETER;
    }
    
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
        rtcout.println(rtcout.TRACE, "unsubscribeInterfaces()");

        String id = connector_profile.connector_id;
        rtcout.println(rtcout.PARANOID, "connector_id: " + id);

        Iterator it = m_connectors.iterator();
        while (it.hasNext()) {
            InPortConnector connector = (InPortConnector)it.next();
            if (id == connector.id()) {
                // Connector's dtor must call disconnect()
                it.remove();
                rtcout.println(rtcout.TRACE, "delete connector: " + id);
                return;
            }
        }
        rtcout.println(rtcout.ERROR, "specified connector not found: " + id);
        return;
    }


    /**
     * <p> InPort provider initialization </p>
     */
    protected void initProviders() {
        rtcout.println(rtcout.TRACE, "initProviders()");

        // create InPort providers
        FactoryGlobal<InPortProvider,String> factory 
            = FactoryGlobal.instance();
        Set provider_types = factory.getIdentifiers();
        rtcout.println(rtcout.DEBUG, 
                       "available providers: " + provider_types.toString());

//#ifndef RTC_NO_DATAPORTIF_ACTIVATION_OPTION
        if (m_properties.hasKey("provider_types")!=null &&
           StringUtil.normalize(m_properties.getProperty("provider_types")) 
           != "all") {
            rtcout.println(rtcout.DEBUG, 
                       "allowed providers: " 
                       + m_properties.getProperty("provider_types"));

            Set temp_types = provider_types;
            provider_types.clear();
            Vector<String> active_types 
                = StringUtil.split(m_properties.getProperty("provider_types"), 
                                   ",");

            Set temp_types_set = new HashSet(temp_types);
            Set active_types_set = new HashSet(active_types);
            Iterator it = temp_types_set.iterator();
            while(it.hasNext()) {
                String str = (String)it.next();
                if(active_types_set.contains(str)) {
                    provider_types.add(str);
                }
            }
      }
//#endif
    
        // InPortProvider supports "push" dataflow type
        if (provider_types.size() > 0) {
            rtcout.println(rtcout.DEBUG, 
                           "dataflow_type push is supported");
            appendProperty("dataport.dataflow_type", "push");
            appendProperty("dataport.interface_type",
                           provider_types.toString());
        }

        m_providerTypes = (Vector<String>)provider_types;
    }

    /**
     * <p> OutPort consumer initialization </p>
     */
    protected void initConsumers() {
        rtcout.println(rtcout.TRACE, "iinitConsumers()");

        // create OuPort consumers
        FactoryGlobal<OutPortProvider,String> factory 
            = FactoryGlobal.instance();
        Set consumer_types = factory.getIdentifiers();
        rtcout.println(rtcout.DEBUG, 
                       "available consumer: " + consumer_types.toString());

//#ifndef RTC_NO_DATAPORTIF_ACTIVATION_OPTION
        if (m_properties.hasKey("consumer_types")!=null &&
            StringUtil.normalize(m_properties.getProperty("consumer_types")) 
            != "all") {
            rtcout.println(rtcout.DEBUG, 
                       "allowed consumers: " 
                       + m_properties.getProperty("consumer_types"));

            Set temp_types = consumer_types;
            consumer_types.clear();
            Vector<String> active_types 
                = StringUtil.split(m_properties.getProperty("consumer_types"), 
                                   ",");

            Set temp_types_set = new HashSet(temp_types);
            Set active_types_set = new HashSet(active_types);
            Iterator it = temp_types_set.iterator();
            while(it.hasNext()) {
                String str = (String)it.next();
                if(active_types_set.contains(str)) {
                    consumer_types.add(str);
                }
            }
        }
//#endif

        // OutPortConsumer supports "pull" dataflow type
        if (consumer_types.size() > 0) {
            rtcout.println(rtcout.DEBUG, 
                           "dataflow_type pull is supported");
            appendProperty("dataport.dataflow_type", "pull");
            appendProperty("dataport.interface_type",
                           consumer_types.toString());
        }

        m_consumerTypes = (Vector<String>)consumer_types;
    }

    /**
     * <p> InPort provider creation </p>
     * <p> InPortProvider is created, </p>
     * <p> and information is published to ConnectorProfile. </p>
     * <p> null is returned if failing in creation. </p>
     */
    protected InPortProvider
    createProvider(ConnectorProfileHolder cprof, Properties prop) {
        if (prop.getProperty("interface_type").length()!=0 &&
            !StringUtil.includes(m_providerTypes, 
                      prop.getProperty("interface_type"),
                      true)) {
            rtcout.println(rtcout.ERROR, "no provider found");
            rtcout.println(rtcout.ERROR, 
                       "interface_type:  "+prop.getProperty("interface_type"));
            rtcout.println(rtcout.ERROR, 
                       "interface_types:  "+m_providerTypes.toString());
            return null;
        }
    
        rtcout.println(rtcout.DEBUG, 
                       "interface_type:  "+prop.getProperty("interface_type"));
        InPortProvider provider;
        FactoryGlobal<InPortProvider,String> factory 
            = FactoryGlobal.instance();
        provider = factory.createObject(prop.getProperty("interface_type"));
    
        if (provider != null) {
            rtcout.println(rtcout.DEBUG, "provider created");
            provider.init(prop.getNode("provider"));

            NVListHolder nvlist = new NVListHolder(cprof.value.properties);
            if (!provider.publishInterface(nvlist)) {
                rtcout.println(rtcout.ERROR, 
                               "publishing interface information error");
                factory.deleteObject(provider);
                return null;
            }
            return provider;
        }

        rtcout.println(rtcout.ERROR, "provider creation failed");
        return null;
    }

    /**
     * <p> InPort provider creation </p>
     * <p> OutPortConsumer is created. </p>
     * <p> null is returned if failing in creation. </p>
     */
    protected OutPortConsumer
    createConsumer(final ConnectorProfileHolder cprof, Properties prop) {
        if (prop.getProperty("interface_type").length()!=0 &&
            !StringUtil.includes(m_consumerTypes, 
                                 prop.getProperty("interface_type"),
                                 true)) {
            rtcout.println(rtcout.ERROR, "no consumer found");
            rtcout.println(rtcout.ERROR, 
                       "interface_type:  "+prop.getProperty("interface_type"));
            rtcout.println(rtcout.ERROR, 
                       "interface_types:  "+m_consumerTypes.toString());
            return null;
        }
    
        rtcout.println(rtcout.DEBUG, 
                       "interface_type:  "+prop.getProperty("interface_type"));
        OutPortConsumer consumer;
        FactoryGlobal<OutPortConsumer,String> factory 
            = FactoryGlobal.instance();
        consumer = factory.createObject(prop.getProperty("interface_type"));
    
        if (consumer != null) {
            rtcout.println(rtcout.DEBUG, "consumer created");
            consumer.init(prop.getNode("consumer"));
    
            NVListHolder nvlist = new NVListHolder(cprof.value.properties);
            if (!consumer.subscribeInterface(nvlist)) {
                rtcout.println(rtcout.ERROR, 
                               "interface subscription failed.");
                factory.deleteObject(consumer);
                return null;
              }

            return consumer;
        }

        rtcout.println(rtcout.ERROR, "consumer creation failed");
        return null;
    }
    /**
     * <p> InPortPushConnector creation </p>
     * <p> Connector is created, </p>
     * <p> preserves it in m_connectors. </p>
     * <p> null is returned if failing in creation. </p>
     */
    protected InPortConnector
    createConnector(ConnectorProfileHolder cprof, Properties prop,
                    InPortProvider provider) {

        ConnectorBase.Profile profile 
            = new ConnectorBase.Profile(cprof.value.name,
                                 cprof.value.connector_id,
                                 CORBA_SeqUtil.refToVstring(cprof.value.ports),
                                 prop); 
        InPortConnector connector = null;
        try {
            if (m_singlebuffer) {
                connector = new InPortPushConnector(profile, provider,
                                                m_thebuffer);
            }
            else {
                BufferBase<OutputStream> buffer = null;
                connector = new InPortPushConnector(profile, provider, buffer);
            }

            if (connector == null) {
                rtcout.println(rtcout.ERROR, "old compiler? new returned 0;");
                return null;
            }
            rtcout.println(rtcout.TRACE, "InPortPushConnector create");

            m_connectors.add(connector);
            rtcout.println(rtcout.PARANOID, 
                           "connector push backed: "+m_connectors.size());
            return connector;
        }
        catch (Exception e) {
            rtcout.println(rtcout.ERROR,"InPortPushConnector creation failed");
            return null;
        }
    }
    /**
     * <p> InPortPullConnector creation </p>
     * <p> Connector is created, </p>
     * <p> preserves it in m_connectors. </p>
     * <p> 0 is returned if failing in creation. </p>
     */
    protected InPortConnector
    createConnector(final ConnectorProfileHolder cprof, Properties prop,
                    OutPortConsumer consumer) {
        ConnectorBase.Profile profile 
            = new ConnectorBase.Profile( cprof.value.name,
                                  cprof.value.connector_id,
                                  CORBA_SeqUtil.refToVstring(cprof.value.ports),
                                  prop); 
        InPortConnector connector = null;
        try {
            if (m_singlebuffer) {
                connector = new InPortPullConnector(profile, consumer,
                                                    m_thebuffer);
            }
            else {
                BufferBase<OutputStream> buffer = null;
                connector = new InPortPullConnector(profile, consumer, buffer);
            }

            if (connector == null) {
                rtcout.println(rtcout.ERROR, "old compiler? new returned 0;");
                return null;
            }
            rtcout.println(rtcout.TRACE, "InPortPushConnector create");

            m_connectors.add(connector);
            rtcout.println(rtcout.PARANOID, 
                           "connector push backed: "+m_connectors.size());
            return connector;
        }
        catch (Exception e) {
            rtcout.println(rtcout.ERROR,"InPortPullConnector creation failed");
            return null;
        }
    }
    protected boolean m_singlebuffer;
    protected BufferBase<OutputStream> m_thebuffer;
    protected Properties m_properties = new Properties();
    protected Vector<String> m_providerTypes = new Vector<String>();
    protected Vector<String> m_consumerTypes = new Vector<String>();
    protected Vector<InPortConnector> m_connectors = new Vector<InPortConnector>();

}


