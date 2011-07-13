package jp.go.aist.rtm.RTC.port;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import jp.go.aist.rtm.RTC.BufferFactory;
import jp.go.aist.rtm.RTC.InPortProviderFactory;
import jp.go.aist.rtm.RTC.OutPortConsumerFactory;
import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.buffer.RingBuffer;
import jp.go.aist.rtm.RTC.log.Logbuf;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.StringUtil;

import org.omg.CORBA.TCKind;
import org.omg.CORBA.portable.OutputStream;

import _SDOPackage.NVListHolder;
import RTC.ConnectorProfile;
import RTC.ConnectorProfileHolder;
import RTC.ReturnCode_t;

 
/**
 * 
 * {@.ja InPort 用 Port}
 * {@.en Port for InPort}
 *
 * <p>
 * {@.en データ入力ポートの実装クラス。}
 * {@.en This is an implementation class for the data input port.}
 *
 */
public abstract class InPortBase extends PortBase {


    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     * @param name 
     *   {@.ja ポート名称}
     *   {@.en Port name}
     * @param data_type 
     *   {@.ja データタイプ}
     *   {@.en Specify the data type used in the InPort object.}
     */
    public InPortBase(final String name, final String data_type) {
        super(name);

        rtcout.println(Logbuf.DEBUG, "Port name: "+name);

        m_singlebuffer = true;
        m_thebuffer = null;
        m_isLittleEndian = true;
        // Set PortProfile::properties
        rtcout.println(Logbuf.DEBUG, "setting port.port_type: DataIntPort");
        addProperty("port.port_type", "DataInPort",String.class);
        rtcout.println(Logbuf.DEBUG, "setting dataport.data_type: "+data_type);
        addProperty("dataport.data_type", data_type,String.class);
        addProperty("dataport.subscription_type", "Any",String.class);
    }
    
    /**
     *
     * {@.ja プロパティを取得する}
     * {@.en Get properties}
     *
     * <p> 
     * {@.ja ポートのプロパティを取得する。}
     * {@.en This method gets properties in the port.}
     * @return 
     *   {@.ja プロパティ}
     *   {@.en Properties Properties}
     * 
     */
    public Properties properties() {
        rtcout.println(Logbuf.TRACE, "properties()");
    
        return m_properties;
    }

    /**
     * {@.ja Connector を取得}
     * {@.en Connector list}
     *
     * <p>
     * {@.ja 現在所有しているコネクタを取得する。}
     * {@.en This operation returns connector list}
     *
     * @return 
     *   {@.ja connector のリスト}
     *   {@.en connector list}
     *
     *
     */
    public final Vector<InPortConnector> connectors() {
        rtcout.println(Logbuf.TRACE, 
                            "connectors(): size = "+m_connectors.size());
        return m_connectors;
    }

    /**
     * {@.ja ConnectorProfile を取得}
     * {@.en ConnectorProfile list}
     *
     * <p>
     * {@.ja 現在所有しているコネクタのProfileを取得する。}
     * {@.en This operation returns ConnectorProfile list}
     *
     * @return 
     *   {@.ja ConnectorProfile のリスト}
     *   {@.en connector list}
     *
     *
     */
    public Vector<ConnectorBase.ConnectorInfo> getConnectorProfiles(){
        rtcout.println(Logbuf.TRACE, 
                        "getConnectorProfiles(): size = "+m_connectors.size());
        Vector<ConnectorBase.ConnectorInfo> profs 
                = new Vector<ConnectorBase.ConnectorInfo>();
        for (int i=0, len=m_connectors.size(); i < len; ++i) {
            profs.add(m_connectors.elementAt(i).profile());
        }
        return profs;
    }

    /**
     *
     * {@.ja ConnectorId を取得}
     * {@.en ConnectorId list}
     *
     * <p> 
     * {@.ja 現在所有しているコネクタのIDを取得する。}
     * {@.en This operation returns ConnectorId list}
     * @return 
     *   {@.ja ConnectorId のリスト}
     *   {@.en Vector<String> connector list}
     * 
     */
    public Vector<String> getConnectorIds() {
        Vector<String> ids = new Vector<String>();
        for (int i=0, len=m_connectors.size(); i < len; ++i) {
            ids.add(m_connectors.elementAt(i).id());
        }
        rtcout.println(Logbuf.TRACE, "getConnectorIds(): "+ids);
        return ids;
    }

    /**
     *
     * {@.ja Connectorの名前を取得}
     * {@.en Connector name list}
     * <p> 
     * {@.ja 現在所有しているコネクタの名前を取得する。}
     * {@.en This operation returns Connector name list}
     * @return 
     *   {@.en Connector名のリスト}
     *   {@.en Vector<String> connector name list}
     *
     */
    public Vector<String> getConnectorNames() {
        Vector<String> names = new Vector<String>();
        for (int i=0, len=m_connectors.size(); i < len; ++i) {
            names.add(m_connectors.elementAt(i).name());
        }
        rtcout.println(Logbuf.TRACE, "getConnectorNames(): "+names);
        return names;
    }

    /** 
     * {@.ja ConnectorProfileをIDで取得}
     * {@.en Getting ConnectorProfile by ID}
     *
     * <p> 
     * {@.ja 現在所有しているコネクタをIDで取得する。}
     * {@.en This operation returns Connector specified by ID.}
     * @param id 
     *   {@.ja Connector ID}
     *   {@.en Connector ID}
     * @return 
     *   {@.ja コネクタへのポインタ}
     *   {@.en InPortConnector connector}
     */
    public InPortConnector getConnectorById(final String id) {
        rtcout.println(Logbuf.TRACE, "getConnectorById(id = "+id+")");

        String sid = id;
        for (int i=0, len=m_connectors.size(); i < len; ++i) {
            if (sid.equals(m_connectors.elementAt(i).id())) {
                return m_connectors.elementAt(i);
            }
        }
        rtcout.println(Logbuf.WARN, 
                        "ConnectorProfile with the id("+id+") not found.");
        return null;
    }

    /**
     * {@.ja ConnectorProfileを名前で取得}
     * {@.en Getting Connector by name}
     * <p> 
     * {@.ja 現在所有しているコネクタを名前で取得する。}
     * {@.en This operation returns Connector specified by name.}
     * @param name 
     *   {@.ja Connector name}
     *   {@.en Connector ID}
     * @return 
     *   {@.ja コネクタへのポインタ}
     *   {@.en InPortConnector connector}
     *
     */
    public InPortConnector getConnectorByName(final String name){
        rtcout.println(Logbuf.TRACE, "getConnectorByName(name = "+name+")");

        String sname = name;
        for (int i=0, len=m_connectors.size(); i < len; ++i) {
            if (sname.equals(m_connectors.elementAt(i).name())) {
                return m_connectors.elementAt(i);
            }
        }
        rtcout.println(Logbuf.WARN, 
                        "ConnectorProfile with the name("+name+") not found.");
        return null;
    }

    /**
     * {@.ja ConnectorProfileをIDで取得}
     * {@.en Getting ConnectorProfile by name}
     * <p> 
     * {@.ja 現在所有しているコネクタをIDで取得する。}
     * {@.en This operation returns ConnectorProfile specified by name}
     * @param id 
     *   {@.ja Connector ID}
     *   {@.en Connector ID}
     * @param prof 
     *   {@.ja ConnectorProfileHolder}
     *   {@.en ConnectorProfileHolder}
     * @return 
     *   {@.ja false 指定したIDがない}
     *   {@.en false specified ID does not exist}
     *
     */
    public boolean getConnectorProfileById(final String id, 
                                    ConnectorBase.ConnectorInfoHolder prof) {
        rtcout.println(Logbuf.TRACE, "getConnectorProfileById(id = "+id+")");

        InPortConnector conn = getConnectorById(id);
        if (conn == null) {
            return false;
        }
        prof.value = conn.profile();
        return true;
    }

    /**
     *
     * {@.ja ConnectorProfileを名前で取得}
     * {@.en Getting ConnectorProfile by name}
     *
     * <p>
     * {@.ja 現在所有しているコネクタを名前で取得する。}
     * {@.en This operation returns ConnectorProfile specified by name}
     *
     * @param name 
     *   {@.ja Connector name}
     *   {@.en Connector ID}
     * @param prof 
     *   {@.ja ConnectorProfile}
     *   {@.en ConnectorProfile}
     * @return 
     *   {@.ja false 指定した名前がない}
     *   {@.en false specified name does not exist}
     *
     */
    public boolean getConnectorProfileByName(final String name, 
                                      ConnectorBase.ConnectorInfoHolder prof) {
        rtcout.println(Logbuf.TRACE, 
                            "getConnectorProfileByName(name = "+name+")");
        InPortConnector conn = getConnectorByName(name);
        if (conn == null) {
            return false;
        }
        prof.value = conn.profile();
        return true;
    }

    /**
     * {@.ja プロパティの初期化}
     * {@.en Initializing properties}
     * <p> 
     * {@.ja 指定されたプロパティで初期化する。}
     * {@.en This method initializes the port in the specified property.}
     * @param prop 
     *   {@.ja 設定するプロパティ}
     *   {@.en Property for setting ports}
     */
    public void init(Properties prop) {
        rtcout.println(Logbuf.TRACE, "init()");

        m_properties.merge(prop);
        if (m_singlebuffer) {
            rtcout.println(Logbuf.DEBUG, "single buffer mode.");
            final BufferFactory<RingBuffer<OutputStream>,String> factory 
                    = BufferFactory.instance();
            m_thebuffer = factory.createObject("ring_buffer");

            if (m_thebuffer == null) {
                rtcout.println(Logbuf.ERROR, "default buffer creation failed.");
            }
        }
        else {
            rtcout.println(Logbuf.DEBUG, "multi buffer mode.");
        }
        initProviders();
        initConsumers();

        int num = -1;
        String limit = m_properties.getProperty("connection_limit","-1");
        try {
            num = Integer.parseInt(limit);
        }
        catch(Exception ex){
            rtcout.println(Logbuf.ERROR, 
                    "invalid connection_limit value: "+limit );
        }
        setConnectionLimit(num);
      
    }

    /**
     * {@.ja RTObject_impl::readAll()から呼ばれる仮想関数}
     * {@.en It is a virtual method that is called from 
     *       RTObject_impl::readAll().}
     * <p>
     * {@.ja DataPort からデータを読み出す}
     * {@.en  This method reads out data from DataPort.}
     * </p>
     * @return 
     *   {@.ja true:成功,false:失敗}
     *   {@.en true:Success,false:Failure}
     */
    public abstract boolean read();

    /**
     * {@.ja InPortを activates する}
     * {@.en Activate all Port interfaces}
     *
     * <p> 
     * {@.ja Port に登録されている全てのインターフェースを activate する。}
     * {@.en This operation activate all interfaces that is registered in the
     * ports.}
     *
     */
    public void activateInterfaces() {
        rtcout.println(Logbuf.TRACE, "activateInterfaces()");

        synchronized (m_connectors){
            for (int i=0, len=m_connectors.size(); i < len; ++i) {
                m_connectors.elementAt(i).activate();
                rtcout.println(Logbuf.DEBUG, 
                               "activate connector: "
                                    + m_connectors.elementAt(i).name()
                                    +" "
                                    +m_connectors.elementAt(i).id());
            }
        }
    }

    /**
     *
     * {@.ja 全ての Port のインターフェースを deactivates する}
     * {@.en Deactivate all Port interfaces}
     *
     * <p> 
     * {@.ja Port に登録されている全てのインターフェースを deactivate する。}
     * {@.en This operation deactivate all interfaces that is registered in 
     * the ports.} 
     *
     */
    public void deactivateInterfaces() {
        rtcout.println(Logbuf.TRACE, "deactivateInterfaces()");

        synchronized (m_connectors){
            for (int i=0, len=m_connectors.size(); i < len; ++i) {
                m_connectors.elementAt(i).deactivate();
                rtcout.println(Logbuf.DEBUG, 
                               "deactivate connector: "
                                    + m_connectors.elementAt(i).name()
                                    +" "
                                    +m_connectors.elementAt(i).id());
            }
        }
    }

    /**
     * {@.ja [CORBA interface] Port の接続を行う}
     * {@.en Connect the Port}
     *
     * <p> 
     * {@.ja 与えられた ConnectoionProfile の情報に基づき、Port間の接続を確立
     * する。この関数は主にアプリケーションプログラムやツールから呼び出
     * すことを前提としている。}
     * {@.en This operation establishes connection according to the given
     * ConnectionProfile inforamtion. This function is premised on
     * calling from mainly application program or tools.
     *
     * To establish the connection among Ports of RT-Components,
     * application programs must call this operation giving
     * ConnectorProfile with valid values as an argument.
     *
     * Out of ConnectorProfile member variables, "name", "ports"
     * and "properties" members shall be set valid
     * data. "connector_id" shall be set as empty string value or
     * valid string UUID value. 
     *
     * ConnectorProfile::name that is connection identifier shall
     * be any valid CORBA::string. 
     * 
     *
     * ConnectorProfile::connector_id shall be set unique
     * identifier (usually UUID is used) for all connections. Since
     * UUID string value is usually set in the connect() function,
     * caller should just set empty string. If the connect() is called
     * with the same UUID as existing connection, this function
     * returns PRECONDITION_NOT_MET error. However, in order to update
     * the existing connection profile, the "connect()" operation with
     * existing connector ID might be used as valid method by future
     * extension
     *
     * ConnectorProfile::ports, which is sequence of
     * RTC::PortService references, shall store usually two or more
     * ports' references. As exceptions, the "connect()" operation
     * might be called with only one reference in ConnectorProfile, in
     * case of just getting interfaces information from the port, or
     * connecting a special port (i.e. the peer port except
     * RTC::PortService on CORBA). 
     *
     * ConnectorProfile::properties might be used to give certain
     * properties to the service interfaces associated with the port.
     * The properties is a sequence variable with a pair of key string
     * and Any type value. Although the A variable can store any type
     * of values, it is not recommended except string.
     *
     * The following is the summary of the ConnectorProfile
     * member to be set when this operation is called.
     *
     * <ul>
     * <li>- ConnectorProfile::name: The any name of connection</li>
     * <li>- ConnectorProfile::connector_id: Empty string</li>
     * <li>- ConnectorProfile::ports: One or more port references</li>
     * <li>- ConnectorProfile::properties: Properties for the interfaces</li>
     * </ul>
     *
     * connect() operation will call the first port in the
     * sequence of the ConnectorProfile.
     *
     * "noify_connect()"s perform cascaded call to the ports
     * stored in the ConnectorProfile::ports by order. Even if errors
     * are raised by intermediate notify_connect() operation, as long
     * as ports' object references are valid, it is guaranteed that
     * this cascaded call is completed in all the ports.  If invalid
     * or dead ports exist in the port's sequence, the ports are
     * skipped and notify_connect() is called for the next valid port.
     *
     * connect() function returns RTC_OK if all the
     * notify_connect() return RTC_OK. At this time the connection is
     * completed.  If notify_connect()s return except RTC_OK,
     * connect() calls disconnect() operation with the connector_id to
     * destruct the connection, and then it returns error code from
     * notify_connect().
     *
     * The ConnectorProfile argument of the connect() operation
     * returns ConnectorProfile::connector_id and various information
     * about service interfaces that is published by
     * publishInterfaces() in the halfway ports. The connect() and
     * halfway notify_connect() functions never change
     * ConnectorProfile::(name, ports)}
     *
     * @param connector_profile 
     *   {@.ja ConnectorProfile}
     *   {@.en The ConnectorProfile.}
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type.}
     */
    public ReturnCode_t connect(ConnectorProfileHolder connector_profile) {
        //
        NVListHolder nvholder = 
                new NVListHolder(connector_profile.value.properties);
            
        Properties prop = new Properties();
        NVUtil.copyToProperties(prop,nvholder);
        if(null != prop.findNode("dataport")){
            int index = 
                NVUtil.find_index(nvholder,"dataport.serializer.cdr.endian");
            if(index<0){
                CORBA_SeqUtil.push_back(nvholder, 
                    NVUtil.newNVString("dataport.serializer.cdr.endian", 
                                    "little,big"));
                connector_profile.value.properties = nvholder.value;
            }
        }
        return super.connect(connector_profile);
    }

    /**
     * {@.ja Interface情報を公開する}
     * {@.en Publish interface information}
     *
     * <p>
     * {@.ja Interface情報を公開する。
     *       引数の ConnectorProfile に格納されている dataflow_type が push 型
     *       の場合は、指定された interface_type の InPortProvider に関する情報
     *       を ConnectorProfile::properties に書込み呼び出し側に戻す。}
     * {@.en Publish interface information.
     *       Assign the Provider information that owned by this port
     *       to ConnectorProfile#properties}
     * </p>
     * <pre><code>
     *  dataport.dataflow_type
     * </code></pre>
     *
     * @param cprof
     *   {@.ja コネクタプロファイル}
     *   {@.en The connector profile}
     *
     * @return
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     *
     */
    protected ReturnCode_t publishInterfaces(ConnectorProfileHolder cprof) {
        rtcout.println(Logbuf.TRACE, "publishInterfaces()");

        ReturnCode_t returnvalue = _publishInterfaces();
        if(returnvalue!=ReturnCode_t.RTC_OK) {
            return returnvalue;
        }

        // prop: [port.outport].
        Properties prop = m_properties;
        {
            Properties conn_prop = new Properties();
            NVListHolder nvlist = new NVListHolder(cprof.value.properties);
            NVUtil.copyToProperties(conn_prop, nvlist);
            prop.merge(conn_prop.getNode("dataport")); //merge ConnectorProfile
            /*
             * marge ConnectorProfile for buffer property.
             * e.g.
             *  prof[buffer.write.full_policy]
             *       << cprof[dataport.inport.buffer.write.full_policy]
             */
            prop.merge(conn_prop.getNode("dataport.inport"));
        }
        rtcout.println(Logbuf.DEBUG, 
                           "ConnectorProfile::properties are as follows.");
        String dumpString = new String();
        dumpString = prop._dump(dumpString, prop, 0);
        rtcout.println(Logbuf.DEBUG, dumpString);
                           

        //
       NVListHolder holder = new NVListHolder(cprof.value.properties);
       try{ 
            org.omg.CORBA.Any anyVal = NVUtil.find(holder,
                                           "dataport.serializer.cdr.endian");
            String endian_type;
            if( anyVal.type().kind() == TCKind.tk_wstring ) {
                endian_type = anyVal.extract_wstring();
            } else {
                endian_type = anyVal.extract_string();
            }

            endian_type = StringUtil.normalize(endian_type);
            String[] endian = endian_type.split(",");
            endian_type = "";
            for(int ic=0;ic<endian.length;++ic){
                String str = endian[ic].trim();
                if(str.equals("big") || str.equals("little")){
                    if(endian_type.length()!=0){
                        endian_type = endian_type + ","+ str;
                    }
                    else{
                        endian_type = endian_type + str;
                    }
                }
            }
            int index = NVUtil.find_index(holder, "dataport.serializer.cdr.endian");
            holder.value[index].value.insert_string(endian_type);
            cprof.value.properties = holder.value;
       }
       catch(Exception e){
            ;
       }
        /*
         * Because properties of ConnectorProfileHolder was merged, 
         * the accesses such as prop["dataflow_type"] and 
         * prop["interface_type"] become possible here.
         */
        String dflow_type = prop.getProperty("dataflow_type");
        dflow_type = StringUtil.normalize(dflow_type);

        if (dflow_type.equals("push")) {
            rtcout.println(Logbuf.DEBUG, 
                           "dataflow_type = push .... create PushConnector");

            // create InPortProvider
            InPortProvider provider = createProvider(cprof, prop);
            if (provider == null) {
                rtcout.println(Logbuf.ERROR, 
                           "InPort provider creation failed.");
                return ReturnCode_t.BAD_PARAMETER;
            }

            // create InPortPushConnector
            InPortConnector connector = createConnector(cprof, prop, provider);
            if (connector == null) {
                rtcout.println(Logbuf.ERROR, 
                           "PushConnector creation failed.");
                return ReturnCode_t.RTC_ERROR;
            }
            provider.setConnector(connector);

            rtcout.println(Logbuf.DEBUG, 
                           "publishInterface() successfully finished.");
            return ReturnCode_t.RTC_OK;
        }
        else if (dflow_type.equals("pull")) {
            rtcout.println(Logbuf.DEBUG, 
                           "dataflow_type = pull .... do nothing.");
            return ReturnCode_t.RTC_OK;
        }

        rtcout.println(Logbuf.ERROR, "unsupported dataflow_type:"+dflow_type);
        return ReturnCode_t.BAD_PARAMETER;
    }
    
    /**
     * {@.ja Interfaceに接続する}
     * {@.en Subscribe to the interface}
     *
     * <p>
     * {@.ja Interfaceに接続する。
     *       Portが所有するConsumerに適合するProviderに関する情報を 
     *       ConnectorProfile#properties から抽出し、
     *       ConsumerにCORBAオブジェクト参照を設定する。}
     * {@.en Subscribe to interface.
     *       Derive Provider information that matches Consumer owned by 
     *       the Port from ConnectorProfile#properties and 
     *       set the Consumer to the reference of the CORBA object.}
     * </p>
     *
     * @param cprof
     *   {@.ja コネクタ・プロファイル}
     *   {@.en The connector profile}
     *
     * @return
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en ReturnCode_t The return code of ReturnCode_t type}
     */
    protected ReturnCode_t subscribeInterfaces(
            final ConnectorProfileHolder cprof) {
        rtcout.println(Logbuf.TRACE, "subscribeInterfaces()");

        // prop: [port.outport].
        Properties prop = m_properties;
        {
            Properties conn_prop = new Properties();
            NVListHolder nvlist = new NVListHolder(cprof.value.properties);
            NVUtil.copyToProperties(conn_prop, nvlist);
            prop.merge(conn_prop.getNode("dataport")); //merge ConnectorProfile
            /*
             * marge ConnectorProfile for buffer property.
             * e.g.
             *  prof[buffer.write.full_policy]
             *       << cprof[dataport.inport.buffer.write.full_policy]
             */
            prop.merge(conn_prop.getNode("dataport.inport"));
        }

        rtcout.println(Logbuf.DEBUG, 
                           "ConnectorProfile::properties are as follows.");
        String dumpString = new String();
        dumpString = prop._dump(dumpString, prop, 0);
        rtcout.println(Logbuf.DEBUG, dumpString);

        //
       NVListHolder holder = new NVListHolder(cprof.value.properties);
       try{ 
            org.omg.CORBA.Any anyVal = NVUtil.find(holder,
                                           "dataport.serializer.cdr.endian");
            String endian_type;
            if( anyVal.type().kind() == TCKind.tk_wstring ) {
                endian_type = anyVal.extract_wstring();
            } else {
                endian_type = anyVal.extract_string();
            }

            endian_type = StringUtil.normalize(endian_type);
            String[] endian = endian_type.split(",");
            String str = endian[0].trim();
            if(str.length()==0){
                rtcout.println(Logbuf.ERROR, "unsupported endian");
                return ReturnCode_t.UNSUPPORTED;
            }
            if(str.equals("little")){
                m_isLittleEndian = true;
            }
            else if(str.equals("big")){
                m_isLittleEndian = false;
            }
            else {
                m_isLittleEndian = true;
            }
       }
       catch(Exception e){
            m_isLittleEndian = true;
       }
        rtcout.println(Logbuf.TRACE, "Little Endian = "+m_isLittleEndian);

        /*
         * Because properties of ConnectorProfileHolder was merged, 
         * the accesses such as prop["dataflow_type"] and 
         * prop["interface_type"] become possible here.
         */
        String dflow_type = prop.getProperty("dataflow_type");
        dflow_type = StringUtil.normalize(dflow_type);

        if (dflow_type.equals("push")) {
            rtcout.println(Logbuf.DEBUG, 
                           "dataflow_type is push.");
            //
            // setting endian type
            InPortConnector conn = getConnectorById(cprof.value.connector_id);
            if (conn == null) {
                rtcout.println(Logbuf.ERROR, 
                    "specified connector not found: "+cprof.value.connector_id);
                return ReturnCode_t.RTC_ERROR;
            }
            conn.setEndian(m_isLittleEndian);

            rtcout.println(Logbuf.DEBUG, 
                        "subscribeInterfaces() successfully finished.");
            return ReturnCode_t.RTC_OK;
            
/* zxc
            String id = cprof.value.connector_id;
            synchronized (m_connectors){
                Iterator it = m_connectors.iterator();
                ConnectorBase.ConnectorInfo profile 
                    = new ConnectorBase.ConnectorInfo(cprof.value.name,
                                 cprof.value.connector_id,
                                 CORBA_SeqUtil.refToVstring(cprof.value.ports),
                                 prop); 
                while (it.hasNext()) {
                    InPortConnector connector = (InPortConnector)it.next();
                    if (id.equals(connector.id())) {
                        connector.setEndian(m_isLittleEndian);
                        connector.setListener(profile,m_listeners);
                        return ReturnCode_t.RTC_OK;
                    }
                }
                rtcout.println(Logbuf.ERROR, 
                               "specified connector not found: " + id);
                return ReturnCode_t.RTC_ERROR;
            }
*/
        }
        else if (dflow_type.equals("pull")) {
            rtcout.println(Logbuf.DEBUG, 
                           "dataflow_type is pull.");

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
            consumer.setConnector(connector);

            connector.setEndian(m_isLittleEndian);
            rtcout.println(Logbuf.DEBUG, 
                           "publishInterface() successfully finished.");
            return ReturnCode_t.RTC_OK;
        }

        rtcout.println(Logbuf.ERROR, 
                       "unsupported dataflow_type:"+dflow_type);
        return ReturnCode_t.BAD_PARAMETER;
    }
    
    /**
     * {@.ja Interfaceへの接続を解除する}
     * {@.en Disconnect the interface connection}
     *
     * <p> 
     * {@.ja Interfaceへの接続を解除する。
     * 与えられたConnectorProfileに関連するConsumerに設定された全てのObjectを
     * 解放し接続を解除する。}
     * {@.en Disconnect the interface connection.
     * Release all objects set in Consumer associated with
     * given ConnectorProfile and unscribe the interface.}
     *
     * @param connector_profile 
     *   {@.ja コネクタ・プロファイル}
     *   {@.en The connector profile}
     *
     */
    protected void
    unsubscribeInterfaces(final ConnectorProfile connector_profile) {
        rtcout.println(Logbuf.TRACE, "unsubscribeInterfaces()");

        String id = connector_profile.connector_id;
        rtcout.println(Logbuf.PARANOID, "connector_id: " + id);


        synchronized (m_connectors){
            Iterator it = m_connectors.iterator();
            while (it.hasNext()) {
                InPortConnector connector = (InPortConnector)it.next();
                if (id.equals(connector.id())) {
                    // Connector's dtor must call disconnect()
                    connector.disconnect();
                    it.remove();
                    rtcout.println(Logbuf.TRACE, "delete connector: " + id);
                    return;
                }
            }
            rtcout.println(Logbuf.ERROR, 
                           "specified connector not found: " + id);
            return;
        }
    }

    /**
     * {@.ja ConnectorDataListener リスナを追加する}
     * {@.en Adding BufferDataListener type listener}
     * <p>
     * {@.ja 
     * バッファ書き込みまたは読み出しイベントに関連する各種リスナを設定する。
     *
     * 設定できるリスナのタイプとコールバックイベントは以下の通り
     *
     * <ul> 
     * <li> - ON_BUFFER_WRITE:          バッファ書き込み時
     * <li> - ON_BUFFER_FULL:           バッファフル時
     * <li> - ON_BUFFER_WRITE_TIMEOUT:  バッファ書き込みタイムアウト時
     * <li> - ON_BUFFER_OVERWRITE:      バッファ上書き時
     * <li> - ON_BUFFER_READ:           バッファ読み出し時
     * <li> - ON_SEND:                  InProtへの送信時
     * <li> - ON_RECEIVED:              InProtへの送信完了時
     * <li> - ON_SEND_ERTIMEOUT:        OutPort側タイムアウト時
     * <li> - ON_SEND_ERERROR:          OutPort側エラー時
     * <li> - ON_RECEIVER_FULL:         InProt側バッファフル時
     * <li> - ON_RECEIVER_TIMEOUT:      InProt側バッファタイムアウト時
     * <li> - ON_RECEIVER_ERROR:        InProt側エラー時
     * </ul> 
     *
     * リスナは ConnectorDataListener を継承し、以下のシグニチャを持つ
     * operator() を実装している必要がある。
     *
     * <pre><code>
     * ConnectorDataListener::
     *         operator()(const ConnectorProfile&, const cdrStream&)
     * </code></pre>
     *
     * デフォルトでは、この関数に与えたリスナオブジェクトの所有権は
     * OutPortに移り、OutPort解体時もしくは、
     * removeConnectorDataListener() により削除時に自動的に解体される。
     * リスナオブジェクトの所有権を呼び出し側で維持したい場合は、第3引
     * 数に false を指定し、自動的な解体を抑制することができる。}
     * {@.en This operation adds certain listeners related to buffer writing and
     * reading events.
     * The following listener types are available.
     *
     * <ul> 
     * <li> - ON_BUFFER_WRITE:        At the time of buffer write
     * <li> - ON_BUFFER_FULL:         At the time of buffer full
     * <li> - ON_BUFFER_WRITE_TIMEOUT:At the time of buffer write timeout
     * <li> - ON_BUFFER_OVERWRITE:    At the time of buffer overwrite
     * <li> - ON_BUFFER_READ:         At the time of buffer read
     * <li> - ON_SEND:                At the time of sending to InPort
     * <li> - ON_RECEIVED:            At the time of finishing sending to InPort
     * <li> - ON_SENDER_TIMEOUT:      At the time of timeout of OutPort
     * <li> - ON_SENDER_ERROR:        At the time of error of OutPort
     * <li> - ON_RECEIVER_FULL:       At the time of bufferfull of InPort
     * <li> - ON_RECEIVER_TIMEOUT:    At the time of timeout of InPort
     * <li> - ON_RECEIVER_ERROR:      At the time of error of InPort
     * </ul> 
     *
     * Listeners should have the following function operator().
     *
     * <pre><code>
     * ConnectorDataListener::
     *         operator()(const ConnectorProfile&, const cdrStream&)
     * </code></pre>
     *
     * The ownership of the given listener object is transferred to
     * this OutPort object in default.  The given listener object will
     * be destroied automatically in the OutPort's dtor or if the
     * listener is deleted by removeConnectorDataListener() function.
     * If you want to keep ownership of the listener object, give
     * "false" value to 3rd argument to inhibit automatic destruction.}
     * </p>
     * @param type 
     *   {@.ja リスナタイプ}
     *   {@.en listener_type A listener type}
     * @param listener
     *   {@.ja リスナオブジェクトへのポインタ}
     *   {@.en A pointer to a listener object}
     * @param autoclean 
     *   {@.ja リスナオブジェクトの自動的解体を行うかどうかのフラグ}
     *   {@.en @param autoclean A flag for automatic listener destruction}
     */
    public void addConnectorDataListener(int type,
                             ConnectorDataListenerT listener,
                             boolean autoclean) {
  
        if (type < ConnectorDataListenerType.CONNECTOR_DATA_LISTENER_NUM) {
            rtcout.println(Logbuf.TRACE,
                    "addConnectorDataListener("
                    +ConnectorDataListenerType.toString(type)
                    +")");
            m_listeners.connectorData_[type].addObserver(listener);
            return;
        }
        rtcout.println(Logbuf.ERROR, 
                    "addConnectorDataListener(): Invalid listener type.");
        return;
    }
    public void addConnectorDataListener(int type,
                                        ConnectorDataListenerT listener) {
        this.addConnectorDataListener(type,listener,true);
    }

    /**
     * {@.ja ConnectorDataListener リスナを削除する}
     * {@.en Removing BufferDataListener type listener}
     * <p>
     * {@.ja 設定した各種リスナを削除する。}
     * [@.en This operation removes a specified listener.}
     * 
     * @param type 
     *   {@.ja リスナタイプ}
     *   {@.en listener_type A listener type}
     * @param listener 
     *   {@.ja リスナオブジェクトへのポインタ}
     *   {@.en listener A pointer to a listener object}
     */
    public void removeConnectorDataListener(int type,
                                ConnectorDataListenerT listener) {


        if (type < ConnectorDataListenerType.CONNECTOR_DATA_LISTENER_NUM) {
            rtcout.println(Logbuf.TRACE, 
                             "removeConnectorDataListener("
                             +ConnectorDataListenerType.toString(type)
                             +")");
            m_listeners.connectorData_[type].deleteObserver(listener);
            return;
        }
        rtcout.println(Logbuf.ERROR, 
                    "removeConnectorDataListener(): Invalid listener type.");
        return;
    }
  
    /**
     * {@.ja ConnectorListener リスナを追加する}
     * {@.en Adding ConnectorListener type listener}
     * <p>
     * {@.ja バッファ書き込みまたは読み出しイベントに関連する各種リスナを
     * 設定する。
     *
     * 設定できるリスナのタイプは
     * <ul>
     * <li> - ON_BUFFER_EMPTY:       バッファが空の場合
     * <li> - ON_BUFFER_READTIMEOUT: バッファが空でタイムアウトした場合
     * </ul>
     *
     * リスナは以下のシグニチャを持つ operator() を実装している必要がある。
     *
     * <pre><code>
     * ConnectorListener::operator()(const ConnectorProfile&)
     * </code></pre>
     *
     * デフォルトでは、この関数に与えたリスナオブジェクトの所有権は
     * OutPortに移り、OutPort解体時もしくは、
     * removeConnectorListener() により削除時に自動的に解体される。
     * リスナオブジェクトの所有権を呼び出し側で維持したい場合は、第3引
     * 数に false を指定し、自動的な解体を抑制することができる。}
     * {@.en This operation adds certain listeners related to buffer writing and
     * reading events.
     * The following listener types are available.
     *
     * <ul>
     * <li> - ON_BUFFER_EMPTY:       At the time of buffer empty
     * <li> - ON_BUFFER_READTIMEOUT: At the time of buffer read timeout
     * </ul>
     *
     * Listeners should have the following function operator().
     *
     * ConnectorListener::operator()(const ConnectorProfile&)
     *  
     * The ownership of the given listener object is transferred to
     * this OutPort object in default.  The given listener object will
     * be destroied automatically in the OutPort's dtor or if the
     * listener is deleted by removeConnectorListener() function.
     * If you want to keep ownership of the listener object, give
     * "false" value to 3rd argument to inhibit automatic destruction.}
     * </p>
     *
     * @param type 
     *   {@.ja リスナタイプ}
     *   {@.en A listener type}
     * @param listener 
     *   {@.ja リスナオブジェクトへのポインタ}
     *   {@.en A pointer to a listener object}
     * @param autoclean 
     *   {@.ja リスナオブジェクトの自動的解体を行うかどうかのフラグ}
     *   {@.en A flag for automatic listener destruction}
     */
    public void addConnectorListener(int type,
                                           ConnectorListener listener,
                                           boolean autoclean) {
  
        if (type < ConnectorListenerType.CONNECTOR_LISTENER_NUM) {
            rtcout.println(Logbuf.TRACE,
                           "addConnectorListener("
                            +ConnectorListenerType.toString(type)
                            +")");
            m_listeners.connector_[type].addObserver(listener);
            return;
        }
        rtcout.println(Logbuf.ERROR, 
                    "addConnectorListener(): Invalid listener type.");
        return;
    }
    public void addConnectorListener(int type,ConnectorListener listener) {
        this.addConnectorListener(type,listener,true);
    }
    
    /**
     * {@.ja ConnectorDataListener リスナを削除する}
     * {@.en Removing ConnectorListener type listener}
     * <p>
     * {@.ja 設定した各種リスナを削除する。}
     * {@.en This operation removes a specified listener.}
     * </p>
     * @param type 
     *   {@.ja リスナタイプ}
     *   {@.en A listener type}
     * @param listener 
     *   {@.ja リスナオブジェクトへのポインタ}
     *   {@.en A pointer to a listener object}
     */
    public void removeConnectorListener(int type,
                                              ConnectorListener listener) {
  
        if (type < ConnectorListenerType.CONNECTOR_LISTENER_NUM) {
            rtcout.println(Logbuf.TRACE,
                           "removeConnectorListener("
                           +ConnectorListenerType.toString(type)
                           +")");
            m_listeners.connector_[type].deleteObserver(listener);
            return;
        }
        rtcout.println(Logbuf.ERROR, 
                    "removeConnectorListener(): Invalid listener type.");
        return;
    }
  


    /**
     * {@.ja InPort provider の初期化}
     * {@.en InPort provider initialization}
     */
    protected void initProviders() {
        rtcout.println(Logbuf.TRACE, "initProviders()");

        // create InPort providers
        InPortProviderFactory<InPortProvider,String> factory 
            = InPortProviderFactory.instance();
        Set provider_types = factory.getIdentifiers();
        rtcout.println(Logbuf.DEBUG, 
                       "available providers: " + provider_types.toString());

//#ifndef RTC_NO_DATAPORTIF_ACTIVATION_OPTION
        String string_normalize = StringUtil.normalize(m_properties.getProperty("provider_types"));
        if (m_properties.hasKey("provider_types")!=null &&
            !string_normalize.equals("all")) {
            rtcout.println(Logbuf.DEBUG, 
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
            rtcout.println(Logbuf.DEBUG, 
                           "dataflow_type push is supported");
            appendProperty("dataport.dataflow_type", "push");
            appendProperty("dataport.interface_type",
                           StringUtil.flatten(provider_types));
        }

	Iterator it = provider_types.iterator();
	while(it.hasNext()) {
	    m_providerTypes.add((String)it.next());
	}
    }

    /**
     * {@.ja OutPort consumer の初期化}
     * {@.en OutPort consumer initialization}
     */
    protected void initConsumers() {
        rtcout.println(Logbuf.TRACE, "iinitConsumers()");

        // create OuPort consumers
        OutPortConsumerFactory<OutPortProvider,String> factory 
            = OutPortConsumerFactory.instance();
        Set consumer_types = factory.getIdentifiers();
        rtcout.println(Logbuf.DEBUG, 
                       "available consumer: " + StringUtil.flatten(consumer_types));

//#ifndef RTC_NO_DATAPORTIF_ACTIVATION_OPTION
        String string_normalize = StringUtil.normalize(m_properties.getProperty("consumer_types"));
        if (m_properties.hasKey("consumer_types")!=null &&
            !string_normalize.equals("all")) {
            rtcout.println(Logbuf.DEBUG, 
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
            rtcout.println(Logbuf.DEBUG, 
                           "dataflow_type pull is supported");
            appendProperty("dataport.dataflow_type", "pull");
            appendProperty("dataport.interface_type",
                           StringUtil.flatten(consumer_types));
        }

	Iterator it = consumer_types.iterator();
	while(it.hasNext()) {
	    m_consumerTypes.add((String)it.next());
	}
    }

    /**
     * {@.ja InPort provider の生成}
     * {@.en InPort provider creation}
     *
     * <p> 
     * {@.ja InPortProvider を生成し、情報を ConnectorProfile に公開する。}
     * {@.en InPortProvider is created,
     * and information is published to ConnectorProfile.}
     * @return
     *   {@.ja 生成に失敗した場合 0 を返す。}
     *   {@.en null is returned if failing in creation.}
     */
    protected InPortProvider
    createProvider(ConnectorProfileHolder cprof, Properties prop) {
        if (prop.getProperty("interface_type").length()!=0 &&
            !StringUtil.includes(m_providerTypes, 
                      prop.getProperty("interface_type"),
                      true)) {
            rtcout.println(Logbuf.ERROR, "no provider found");
            rtcout.println(Logbuf.ERROR, 
                       "interface_type:  "+prop.getProperty("interface_type"));
            rtcout.println(Logbuf.ERROR, 
                       "interface_types:  "+m_providerTypes.toString());
            return null;
        }
    
        rtcout.println(Logbuf.DEBUG, 
                       "interface_type:  "+prop.getProperty("interface_type"));
        InPortProvider provider;
        InPortProviderFactory<InPortProvider,String> factory 
            = InPortProviderFactory.instance();
        provider = factory.createObject(prop.getProperty("interface_type"));
    
        if (provider != null) {
            rtcout.println(Logbuf.DEBUG, "provider created");
            provider.init(prop.getNode("provider"));

            NVListHolder nvlist = new NVListHolder(cprof.value.properties);
            if (!provider.publishInterface(nvlist)) {
                rtcout.println(Logbuf.ERROR, 
                               "publishing interface information error");
                factory.deleteObject(provider);
                return null;
            }
	    cprof.value.properties = nvlist.value;
            return provider;
        }

        rtcout.println(Logbuf.ERROR, "provider creation failed");
        return null;
    }

    /**
     * {@.ja OutPort consumer の生成}
     * {@.en InPort provider creation}
     *
     * <p> 
     * {@.ja OutPortConsumer を生成する。}
     * {@.en OutPortConsumer is created.}
     * @return
     *   {@.ja 生成に失敗した場合 0 を返す。}
     *   {@.en null is returned if failing in creation.}
     */
    protected OutPortConsumer
    createConsumer(final ConnectorProfileHolder cprof, Properties prop) {
        if (prop.getProperty("interface_type").length()!=0 &&
            !StringUtil.includes(m_consumerTypes, 
                                 prop.getProperty("interface_type"),
                                 true)) {
            rtcout.println(Logbuf.ERROR, "no consumer found");
            rtcout.println(Logbuf.ERROR, 
                       "interface_type:  "+prop.getProperty("interface_type"));
            rtcout.println(Logbuf.ERROR, 
                       "interface_types:  "+m_consumerTypes.toString());
            return null;
        }
    
        rtcout.println(Logbuf.DEBUG, 
                       "interface_type:  "+prop.getProperty("interface_type"));
        OutPortConsumer consumer;
        OutPortConsumerFactory<OutPortConsumer,String> factory 
            = OutPortConsumerFactory.instance();
        consumer = factory.createObject(prop.getProperty("interface_type"));
    
        if (consumer != null) {
            rtcout.println(Logbuf.DEBUG, "consumer created");
            consumer.init(prop.getNode("consumer"));
    
            NVListHolder nvlist = new NVListHolder(cprof.value.properties);
            if (!consumer.subscribeInterface(nvlist)) {
                rtcout.println(Logbuf.ERROR, 
                               "interface subscription failed.");
                factory.deleteObject(consumer);
                return null;
              }

            return consumer;
        }

        rtcout.println(Logbuf.ERROR, "consumer creation failed");
        return null;
    }
    /**
     * {@.ja InPortPushConnector の生成}
     * {@.en InPortPushConnector creation}
     * <p> 
     * {@.ja Connector を生成し、生成が成功すれば m_connectors に保存する。}
     * {@.en Connector is created,
     * preserves it in m_connectors.}
     * @return
     *   {@.ja 生成に失敗した場合 0 を返す。}
     *   {@.en null is returned if failing in creation. }
     */
    protected InPortConnector
    createConnector(ConnectorProfileHolder cprof, Properties prop,
                    InPortProvider provider) {

        ConnectorBase.ConnectorInfo profile 
            = new ConnectorBase.ConnectorInfo(cprof.value.name,
                                 cprof.value.connector_id,
                                 CORBA_SeqUtil.refToVstring(cprof.value.ports),
                                 prop); 
        InPortConnector connector = null;
        synchronized (m_connectors){
            try {
                if (m_singlebuffer) {
                    connector = new InPortPushConnector(profile, provider,
                                                    m_listeners,m_thebuffer);
                }
                else {
                    BufferBase<OutputStream> buffer = null;
                    connector = new InPortPushConnector(profile, provider, 
                                                        m_listeners,buffer);
                }
    
                if (connector == null) {
                    rtcout.println(Logbuf.ERROR, 
                                   "old compiler? new returned 0;");
                    return null;
                }
                rtcout.println(Logbuf.TRACE, "InPortPushConnector create");
    
                m_connectors.add(connector);
                rtcout.println(Logbuf.PARANOID, 
                               "connector push backed: "+m_connectors.size());
                return connector;
            }
            catch (Exception e) {
                rtcout.println(Logbuf.ERROR,
                               "InPortPushConnector creation failed");
                return null;
            }
        }
    }
    /**
     * {@.ja InPortPullConnector の生成}
     * {@.en InPortPullConnector creation}
     * <p> 
     * {@.ja Connector を生成し、生成が成功すれば m_connectors に保存する。}
     * {@.en Connector is created,
     * preserves it in m_connectors.}
     * @return
     *   {@.ja 生成に失敗した場合 0 を返す。}
     *   {@.en 0 is returned if failing in creation.}
     */
    protected InPortConnector
    createConnector(final ConnectorProfileHolder cprof, Properties prop,
                    OutPortConsumer consumer) {
        ConnectorBase.ConnectorInfo profile 
            = new ConnectorBase.ConnectorInfo( cprof.value.name,
                                  cprof.value.connector_id,
                                  CORBA_SeqUtil.refToVstring(cprof.value.ports),
                                  prop); 
        InPortConnector connector = null;
        synchronized (m_connectors){
            try {
                if (m_singlebuffer) {
                    connector = new InPortPullConnector(profile, consumer,
                                                        m_listeners,
                                                        m_thebuffer);
                }
                else {
                    BufferBase<OutputStream> buffer = null;
                    connector = new InPortPullConnector(profile, consumer, 
                                                        m_listeners,
                                                        buffer);
                }

                if (connector == null) {
                    rtcout.println(Logbuf.ERROR, 
                                   "old compiler? new returned 0;");
                    return null;
                }
                rtcout.println(Logbuf.TRACE, "InPortPullConnector create");

                m_connectors.add(connector);
                rtcout.println(Logbuf.PARANOID, 
                               "connector push backed: "+m_connectors.size());
                return connector;
            }
            catch (Exception e) {
                rtcout.println(Logbuf.ERROR,
                               "InPortPullConnector creation failed");
                return null;
            }
        }
    }
    /**
     * {@.ja endian 設定を返す}
     * {@.en Returns endian}
     *
     * @return 
     *   {@.ja m_littleEndian がlittleの場合true、bigの場合false を返す。}
     *   {@.en Returns the endian setting.}
     * 
     */
    public boolean isLittleEndian(){
        return m_isLittleEndian;
    }
    protected boolean m_singlebuffer;
    protected BufferBase<OutputStream> m_thebuffer;
    protected Properties m_properties = new Properties();
    protected Vector<String> m_providerTypes = new Vector<String>();
    protected Vector<String> m_consumerTypes = new Vector<String>();
    protected Vector<InPortConnector> m_connectors = new Vector<InPortConnector>();
    private boolean m_isLittleEndian;
    protected ConnectorListeners m_listeners = new ConnectorListeners();
}


