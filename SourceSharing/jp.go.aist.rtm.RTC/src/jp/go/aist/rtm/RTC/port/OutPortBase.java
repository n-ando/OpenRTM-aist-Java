package jp.go.aist.rtm.RTC.port;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import jp.go.aist.rtm.Constants;
import jp.go.aist.rtm.RTC.InPortConsumerFactory;
import jp.go.aist.rtm.RTC.OutPortProviderFactory;
import jp.go.aist.rtm.RTC.PublisherBaseFactory;
import jp.go.aist.rtm.RTC.buffer.BufferBase;
import jp.go.aist.rtm.RTC.log.Logbuf;
import jp.go.aist.rtm.RTC.port.publisher.PublisherBase;
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
 * {@.ja 出力ポートのベース実装クラス}
 * {@.en Base implementation class for OutPort}
 * <p>
 * {@.ja Publisherの登録やPublisherへのデータ更新通知などの実装を提供する。}
 * {@.en The implementation such as the registration of Pubilsher and
 * notification to Publisher.}
 */
public abstract class OutPortBase extends PortBase {

    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     * 
     * @param name 
     *   {@.ja ポート名称}
     *   {@.en Port name}
     * @param data_type 
     *   {@.ja データタイプ}
     *   {@.en Specify the data type used in the InPort object.}
     */
    public OutPortBase(final String name,final String data_type) {
	super(name);
        m_isLittleEndian = true;

        rtcout.println(Logbuf.DEBUG, "Port name: "+name);
        rtcout.println(Logbuf.DEBUG, "setting port.port_type: DataOutPort");

        addProperty("port.port_type", "DataOutPort", String.class);

        rtcout.println(Logbuf.DEBUG,
                       "setting dataport.data_type: "+data_type);
        addProperty("dataport.data_type", data_type, String.class);

        // publisher list
        PublisherBaseFactory<PublisherBase,String> factory 
            = PublisherBaseFactory.instance();
        String pubs = StringUtil.flatten(factory.getIdentifiers());

        // blank characters are deleted for RTSE's bug
        pubs = pubs.trim();
        rtcout.println(Logbuf.DEBUG,
                       "available subscription_type: "+pubs);
        addProperty("dataport.subscription_type", pubs, String.class);
    }

    /**
     * {@.ja プロパティの初期化}
     * {@.en Initializing properties}
     * <p> 
     * {@.ja 指定されたプロパティで初期化する。}
     * {@.en This operation initializes outport's properties.}
     * @param prop 
     *   {@.ja 設定するプロパティ}
     *   {@.en Property for setting ports}
     */
    public void init(Properties prop) {
        rtcout.println(Logbuf.TRACE, "init()");

        rtcout.println(Logbuf.PARANOID, "given properties:");
        String str = new String();
        prop._dump(str,prop,0);
        rtcout.println(Logbuf.DEBUG, str);
        m_properties.merge(prop);

        rtcout.println(Logbuf.PARANOID, "updated properties:");
        str = "";
        m_properties._dump(str,m_properties,0);
        rtcout.println(Logbuf.DEBUG, str);

        configure();

        initConsumers();
        initProviders();

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
     * {@.ja データ書き込み}
     * {@.en Write data}
     * <p>
     * {@.ja ポートへデータを書き込む。
     *       バインドされた変数に設定された値をポートに書き込む。}
     * {@.en Write data to the port.
     *       Write the value, which was set to the bound variable, to the port.}
     * </p>
     * @return 
     *   {@.ja 書き込み処理結果(書き込み成功:true、書き込み失敗:false)}
     *   {@.en Writing result (Successful:true, Failed:false)}
     */
    public abstract boolean write();

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
    public final Vector<OutPortConnector> connectors(){
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
        synchronized (m_connectors){
            for (int i=0, len=m_connectors.size(); i < len; ++i) {
                profs.add(m_connectors.elementAt(i).profile());
            }
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
     *   {@.en connector list}
     * 
     */
    public Vector<String> getConnectorIds(){
        Vector<String> ids = new Vector<String>();
        synchronized (m_connectors){
            for (int i=0, len=m_connectors.size(); i < len; ++i) {
                ids.add(m_connectors.elementAt(i).id());
            }
        }
        rtcout.println(Logbuf.TRACE, 
                       "getConnectorIds(): "+ids.toString());
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
     *   {@.en connector name list}
     *
     */
    public Vector<String> getConnectorNames(){
        Vector<String> names = new Vector<String>();
        synchronized (m_connectors){
            for (int i=0, len=m_connectors.size(); i < len; ++i) {
                names.add(m_connectors.elementAt(i).name());
            }
        }
        rtcout.println(Logbuf.TRACE, 
                       "getConnectorNames(): "+names.toString());
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
    public OutPortConnector getConnectorById(final String id) {
        rtcout.println(Logbuf.TRACE, 
                       "getConnectorById(id = "+id+")");

        String sid = id;
        synchronized (m_connectors){
            for (int i=0, len=m_connectors.size(); i < len; ++i) {
                if (sid.equals(m_connectors.elementAt(i).id())) {
                    return m_connectors.elementAt(i);
                }
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
     *   {@.en OutPortConnector connector}
     *
     */
    OutPortConnector getConnectorByName(final String name) {
        rtcout.println(Logbuf.TRACE, 
                       "getConnectorByName(name = "+name+")");

        String sname = name;
        synchronized (m_connectors){
            for (int i=0, len=m_connectors.size(); i < len; ++i) {
                if (sname.equals(m_connectors.elementAt(i).name())) {
                    return m_connectors.elementAt(i);
                }
            }
        }
        rtcout.println(Logbuf.WARN, 
                       "ConnectorProfile with the name("+name+") not found.");
        return null;
    }
    /**
     * {@.ja ConnectorProfileをnameで取得}
     * {@.en Getting ConnectorProfile by name}
     * <p> 
     * {@.ja 現在所有しているコネクタをIDで取得する。}
     * {@.en This operation returns ConnectorProfile specified by name}
     * @param id 
     *   {@.ja Connector ID}
     *   {@.en Connector ID}
     * @param profh
     *   {@.ja ConnectorInfoHolder}
     *   {@.en ConnectorInfoHolder}
     * @return 
     *   {@.ja false 指定したIDがない}
     *   {@.en false specified ID does not exist}
     *
     */
    public boolean getConnectorProfileById(final String id,
                                 ConnectorBase.ConnectorInfoHolder profh) {
        rtcout.println(Logbuf.TRACE, 
                       "getConnectorProfileById(id = "+id+")");

        OutPortConnector conn = getConnectorById(id);
        if (conn == null) {
            return false;
          }
        profh.value = conn.profile();
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
     * @param profh 
     *   {@.ja ConnectorInfoHolder}
     *   {@.en ConnectorInfoHolder}
     * @return 
     *   {@.ja false 指定した名前がない}
     *   {@.en false specified name does not exist}
     *
     */
    public boolean getConnectorProfileByName(final String name,
                                   ConnectorBase.ConnectorInfoHolder profh) {
        rtcout.println(Logbuf.TRACE, 
                       "getConnectorProfileByNmae(name = "+name+")");

        OutPortConnector conn = getConnectorByName(name);
        if (conn == null) {
            return false;
        }
        profh.value = conn.profile();
        return true;
    }
    /**
     * <p> onConnect </p> 
     */
    public void onConnect(final String id, PublisherBase publisher){
    };
    /**
     *  <p> onDisconenct </p>
     */
    public void onDisconnect(final String id){
    };
    
    /**
     * <p>ポート名です。</p>
     */

    protected class Publisher {
        
        public Publisher(final String _id, PublisherBase _publisher) {
            
            this.id = _id;
            this.publisher = _publisher;
        }
        
        public String id;
        public PublisherBase publisher;
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
                String endian_str = new String();
                if(Constants.SUPPORTED_CDR_ENDIAN.length==0){
                     endian_str = "";
                }
                else {
                    int ic;
                    for(ic=0;ic<Constants.SUPPORTED_CDR_ENDIAN.length-1;++ic){
                        endian_str = endian_str + Constants.SUPPORTED_CDR_ENDIAN[ic] + ", ";
                     }
                     endian_str = endian_str + Constants.SUPPORTED_CDR_ENDIAN[ic];
                } 

//                CORBA_SeqUtil.push_back(nvholder, 
//                    NVUtil.newNVString("dataport.serializer.cdr.endian", 
//                                    "little,big"));
                CORBA_SeqUtil.push_back(nvholder, 
                    NVUtil.newNVString("dataport.serializer.cdr.endian", 
                                    endian_str));
                connector_profile.value.properties = nvholder.value;
            }
        }
        return super.connect(connector_profile);
    }

    /**
     * {@.ja Interface 情報を公開する}
     * {@.en Publish interface information}
     *
     * <p>
     * {@.ja このオペレーションは、notify_connect() 処理シーケンスの始めにコール
     * される純粋仮想関数である。
     * notify_connect() では、
     * <ul>
     * <li> publishInterfaces()
     * <li> connectNext()
     * <li> subscribeInterfaces()
     * <li> updateConnectorProfile()
     * </ul>
     * の順に protected 関数がコールされ接続処理が行われる。
     * <br>
     * このオペレーションは、新規の connector_id に対しては接続の生成、
     * 既存の connector_id に対しては更新が適切に行われる必要がある。}
     * {@.en This operation is pure virutal function that would be called at the
     * beginning of the notify_connect() process sequence.
     * In the notify_connect(), the following methods would be called in order.
     * <ul>
     * <li> publishInterfaces()
     * <li> connectNext()
     * <li> subscribeInterfaces()
     * <li> updateConnectorProfile()
     * </ul>
     * This operation should create the new connection for the new
     * connector_id, and should update the connection for the existing
     * connection_id.}
     * </p>
     *
     * @param cprof 
     *   {@.ja 接続に関するプロファイル情報}
     *   {@.en The connection profile information}
     *
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type.}
     */
    protected ReturnCode_t 
    publishInterfaces(ConnectorProfileHolder cprof) {
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
             *       << cprof[dataport.outport.buffer.write.full_policy]
             */
            prop.merge(conn_prop.getNode("dataport.outport"));
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
                for(int icc=0;icc<Constants.SUPPORTED_CDR_ENDIAN.length;++icc){
                    if(str.equals(Constants.SUPPORTED_CDR_ENDIAN[icc])){
                        if(endian_type.length()!=0){
                            endian_type = endian_type + ","+ str;
                        }
                        else{
                            endian_type = endian_type + str;
                        }
                    }
                }
/*
                if(str.equals("big") || str.equals("little")){
                    if(endian_type.length()!=0){
                        endian_type = endian_type + ","+ str;
                    }
                    else{
                        endian_type = endian_type + str;
                    }
                }
*/
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
                           "dataflow_type = push .... do nothing");
            return ReturnCode_t.RTC_OK;
        }
        else if (dflow_type.equals("pull")) {
            rtcout.println(Logbuf.DEBUG, 
                           "dataflow_type = pull .... create PushConnector");
            OutPortProvider provider=createProvider(cprof, prop);
            if (provider == null) {
                return ReturnCode_t.BAD_PARAMETER;
            }

            // create InPortPushConnector
            OutPortConnector connector=createConnector(cprof, prop, provider);
            if (connector == null) {
                return ReturnCode_t.RTC_ERROR;
            }
            provider.setConnector(connector);
            connector.setOutPortBase(this);

            rtcout.println(Logbuf.DEBUG, 
                           "publishInterface() successfully finished.");
            return ReturnCode_t.RTC_OK;
        }

        rtcout.println(Logbuf.ERROR, "unsupported dataflow_type");

        return ReturnCode_t.BAD_PARAMETER;
    }

    /**
     * {@.ja Interface に接続する}
     * {@.en Subscribe to the interface}
     *
     * <p>
     * {@.ja このオペレーションは、notify_connect() 処理シーケンスの中間にコール
     * される純粋仮想関数である。
     * notify_connect() では、
     * <ul>
     * <li> publishInterfaces()
     * <li> connectNext()
     * <li> subscribeInterfaces()
     * <li> updateConnectorProfile()
     * </ul>
     * の順に protected 関数がコールされ接続処理が行われる。}
     * {@.en This operation is pure virutal function that would be called at the
     * middle of the notify_connect() process sequence.
     * In the notify_connect(), the following methods would be called in order.
     * <ul>
     * <li> publishInterfaces()
     * <li> connectNext()
     * <li> subscribeInterfaces()
     * <li> updateConnectorProfile()
     * </ul>}
     * </p>
     *
     * @param cprof 
     *   {@.ja 接続に関するプロファイル情報}
     *   {@.en The connection profile information}
     *
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type.}
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
             *       << cprof[dataport.outport.buffer.write.full_policy]
             */
            prop.merge(conn_prop.getNode("dataport.outport"));
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

            //interface 
            InPortConsumer consumer = createConsumer(cprof, prop);
            if (consumer == null) {
                return ReturnCode_t.BAD_PARAMETER;
            }

            // create OutPortPushConnector
            OutPortConnector connector = createConnector(cprof, prop, consumer);
            if (connector == null) {
                return ReturnCode_t.RTC_ERROR;
            }
            consumer.setConnector(connector);
            connector.setOutPortBase(this);

            rtcout.println(Logbuf.DEBUG, 
                           "publishInterface() successfully finished.");
            connector.setEndian(m_isLittleEndian);
            return ReturnCode_t.RTC_OK;
        }
        else if (dflow_type.equals("pull")) {
            rtcout.println(Logbuf.DEBUG, 
                           "dataflow_type is pull.");
            // set endian type
            OutPortConnector conn = getConnectorById(cprof.value.connector_id);
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
            //
            String id = cprof.value.connector_id;
            synchronized (m_connectors){
                Iterator it = m_connectors.iterator();
                while (it.hasNext()) {
                    InPortConnector connector = (InPortConnector)it.next();
                    if (id.equals(connector.id())) {
                        connector.setEndian(m_isLittleEndian);
                        return ReturnCode_t.RTC_OK;
                    }
                }
                rtcout.println(Logbuf.ERROR, 
                           "specified connector not found: " + id);
                return ReturnCode_t.RTC_ERROR;
            }
*/
        }

        rtcout.println(Logbuf.ERROR, "unsupported dataflow_type:"+dflow_type);
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
                OutPortConnector connector = (OutPortConnector)it.next();
                if (id.equals(connector.id())) {
                    // Connector's dtor must call disconnect()
                    connector.disconnect();
                    it.remove();
                    rtcout.println(Logbuf.TRACE, "delete connector: " + id);
                    return;
                }
            }
        }
        rtcout.println(Logbuf.ERROR, "specified connector not found: " + id);
        return;
   }
  /**
   * {@.ja OutPortを activates する}
   * {@.en Activate all Port interfaces}
   *
   * <p> 
   * {@.ja Port に登録されている全てのインターフェースを activate する。}
   * {@.en This operation activate all interfaces that is registered in the
   * ports.}
   *
   */
  public void activateInterfaces() {
        synchronized (m_connectors){
            for (int i=0, len=m_connectors.size(); i < len; ++i) {
                m_connectors.elementAt(i).activate();
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
        synchronized (m_connectors){
            for (int i=0, len=m_connectors.size(); i < len; ++i) {
                m_connectors.elementAt(i).deactivate();
            }
        }
  }

    /**
     * {@.ja ConnectorDataListener リスナを追加する}
     * {@.en Adds ConnectorDataListener type listener}
     * <p>
     *
     * バッファ書き込みまたは読み出しイベントに関連する各種リスナを設定する。
     *
     * 設定できるリスナのタイプとコールバックイベントは以下の通り
     *
     * <ul>
     * <li> ON_BUFFER_WRITE:          バッファ書き込み時
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
     * <li> ON_BUFFER_WRITE:          At the time of buffer write
     * <li> ON_BUFFER_FULL:           At the time of buffer full
     * <li> ON_BUFFER_WRITE_TIMEOUT:  At the time of buffer write timeout
     * <li> ON_BUFFER_OVERWRITE:      At the time of buffer overwrite
     * <li> ON_BUFFER_READ:           At the time of buffer read
     * <li> ON_SEND:                  At the time of sending to InPort
     * <li> ON_RECEIVED:              At the time of finishing sending to InPort
     * <li> ON_SENDER_TIMEOUT:        At the time of timeout of OutPort
     * <li> ON_SENDER_ERROR:          At the time of error of OutPort
     * <li> ON_RECEIVER_FULL:         At the time of bufferfull of InPort
     * <li> ON_RECEIVER_TIMEOUT:      At the time of timeout of InPort
     * <li> ON_RECEIVER_ERROR:        At the time of error of InPort
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
    /**
     * {@.ja ConnectorDataListener リスナを追加する}
     * {@.en Adds ConnectorDataListener type listener}
     * <p>
     *
     * バッファ書き込みまたは読み出しイベントに関連する各種リスナを設定する。
     *
     * 設定できるリスナのタイプとコールバックイベントは以下の通り
     *
     * <ul>
     * <li> ON_BUFFER_WRITE:          バッファ書き込み時
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
     * removeConnectorDataListener() により削除時に自動的に解体される。}
     * {@.en This operation adds certain listeners related to buffer writing and
     * reading events.
     * The following listener types are available.
     *
     * <ul>
     * <li> ON_BUFFER_WRITE:          At the time of buffer write
     * <li> ON_BUFFER_FULL:           At the time of buffer full
     * <li> ON_BUFFER_WRITE_TIMEOUT:  At the time of buffer write timeout
     * <li> ON_BUFFER_OVERWRITE:      At the time of buffer overwrite
     * <li> ON_BUFFER_READ:           At the time of buffer read
     * <li> ON_SEND:                  At the time of sending to InPort
     * <li> ON_RECEIVED:              At the time of finishing sending to InPort
     * <li> ON_SENDER_TIMEOUT:        At the time of timeout of OutPort
     * <li> ON_SENDER_ERROR:          At the time of error of OutPort
     * <li> ON_RECEIVER_FULL:         At the time of bufferfull of InPort
     * <li> ON_RECEIVER_TIMEOUT:      At the time of timeout of InPort
     * <li> ON_RECEIVER_ERROR:        At the time of error of InPort
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
     * listener is deleted by removeConnectorDataListener() function.}
     * </p>
     *
     * @param type
     *   {@.ja リスナタイプ}
     *   {@.en A listener type}
     * @param listener
     *   {@.ja リスナオブジェクトへのポインタ}
     *   {@.en A pointer to a listener object}
     */
    public void addConnectorDataListener(int type,
                                        ConnectorDataListenerT listener) {
        this.addConnectorDataListener(type,listener,true);
    }

    /**
     * {@.ja ConnectorDataListener リスナを削除する}
     * {@.en Removes ConnectorDataListener type listener}
     * <p>
     * {@.ja 設定した各種リスナを削除する。}
     * {@.en This operation removes a specified listener.}
     * </p>
     * 
     * @param type 
     *   {@.ja リスナタイプ}
     *   {@.en A listener type}
     * @param listener
     *   {@.ja リスナオブジェクトへのポインタ}
     *   {@.en A pointer to a listener object}
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
     * {@.en Adds ConnectorListener type listener}
     *
     * <p>
     *
     * {@.ja バッファ書き込みまたは読み出しイベントに関連する各種リスナを
     * 設定する。
     *
     * 設定できるリスナのタイプは
     * 
     * <ul>
     * <li> ON_BUFFER_EMPTY:       バッファが空の場合
     * <li> ON_BUFFER_READTIMEOUT: バッファが空でタイムアウトした場合
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
     *
     * {@.en This operation adds certain listeners related to buffer writing and
     * reading events.
     * The following listener types are available.
     *
     * <ul>
     * <li> ON_BUFFER_EMPTY:       At the time of buffer empty
     * <li> ON_BUFFER_READTIMEOUT: At the time of buffer read timeout
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
    /**
     * {@.ja ConnectorListener リスナを追加する}
     * {@.en Adds ConnectorListener type listener}
     *
     * <p>
     *
     * {@.ja バッファ書き込みまたは読み出しイベントに関連する各種リスナを
     * 設定する。
     *
     * 設定できるリスナのタイプは
     * 
     * <ul>
     * <li> ON_BUFFER_EMPTY:       バッファが空の場合
     * <li> ON_BUFFER_READTIMEOUT: バッファが空でタイムアウトした場合
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
     * removeConnectorListener() により削除時に自動的に解体される。}
     *
     * {@.en This operation adds certain listeners related to buffer writing and
     * reading events.
     * The following listener types are available.
     *
     * <ul>
     * <li> ON_BUFFER_EMPTY:       At the time of buffer empty
     * <li> ON_BUFFER_READTIMEOUT: At the time of buffer read timeout
     * </ul>
     *
     * Listeners should have the following function operator().
     *
     * ConnectorListener::operator()(const ConnectorProfile&)
     *  
     * The ownership of the given listener object is transferred to
     * this OutPort object in default.  The given listener object will
     * be destroied automatically in the OutPort's dtor or if the
     * listener is deleted by removeConnectorListener() function.}
     * </p>
     * @param type 
     *   {@.ja リスナタイプ}
     *   {@.en A listener type}
     * @param listener 
     *   {@.ja リスナオブジェクトへのポインタ}
     *   {@.en A pointer to a listener object}
     */
    public void addConnectorListener(int type,ConnectorListener listener) {
        this.addConnectorListener(type,listener,true);
    }
    
    /**
     * {@.ja @brief ConnectorDataListener リスナを削除する}
     * {@.en Removes BufferDataListener type listener}
     *
     * <p>
     * {@.ja 設定した各種リスナを削除する。}
     * {@.en This operation removes a specified listener.}
     * </p>
     * 
     * @param type 
     *   {@.ja リスナタイプ}
     *   {@.en A listener type}
     * @param listener 
     *   {@.ja リスナオブジェクトへのポインタ}
     *   {@.en listener A pointer to a listener object}
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
     * {@.ja OutPortの設定を行う}
     * {@.en Configureing outport}
     * <p>
     * {@.ja propertiesの情報に基づきOutPortの各種設定を行う}
     * {@.en This operation configures the outport based on the properties.}
     *
     */
    protected void configure(){
    }
    /**
     * {@.ja OutPort provider の初期化}
     * {@.en OutPort provider initialization}
     */
    protected void initProviders(){
        rtcout.println(Logbuf.TRACE, "initProviders()");

        // create OutPort providers
        OutPortProviderFactory<OutPortProvider,String> factory 
            = OutPortProviderFactory.instance();
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
    
        // OutPortProvider supports "pull" dataflow type
        if (provider_types.size() > 0) {
            rtcout.println(Logbuf.DEBUG, 
                           "dataflow_type pull is supported");
            appendProperty("dataport.dataflow_type", "pull");
            appendProperty("dataport.interface_type",
                           StringUtil.flatten((Set)provider_types));
        }

	Iterator it = provider_types.iterator();
	while(it.hasNext()) {
	    m_providerTypes.add((String)it.next());
	}
    }

    /**
     * {@.ja InPort consumer の初期化}
     * {@.en InPort consumer initialization}
     */
    protected void initConsumers() {
        rtcout.println(Logbuf.TRACE, "initConsumers()");

        // create InPort consumers
        InPortConsumerFactory<InPortConsumer,String> factory 
            = InPortConsumerFactory.instance();
        Set consumer_types = factory.getIdentifiers();
        rtcout.println(Logbuf.DEBUG, 
                       "available InPortConsumer: "+consumer_types.toString());

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

        // OutPortConsumer supports "push" dataflow type
        if (consumer_types.size() > 0) {
            rtcout.println(Logbuf.DEBUG, 
                           "dataflow_type pull is supported");
            appendProperty("dataport.dataflow_type", "push");
            appendProperty("dataport.interface_type",
                           StringUtil.flatten(consumer_types));
        }

	Iterator it = consumer_types.iterator();
	while(it.hasNext()) {
	    m_consumerTypes.add((String)it.next());
	}

    }
    /**
     * {@.ja OutPort provider の生成}
     * {@.en OutPort provider creation}
     */
    protected OutPortProvider 
    createProvider(ConnectorProfileHolder cprof,
                                    Properties prop) {
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
        OutPortProvider provider;
        OutPortProviderFactory<OutPortProvider,String> factory 
            = OutPortProviderFactory.instance();
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
     * {@.ja InPort consumer の生成}
     * {@.en InPort consumer creation}
     */
    protected InPortConsumer 
    createConsumer(final ConnectorProfileHolder cprof,
                                   Properties prop) {
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
        InPortConsumer consumer;
        InPortConsumerFactory<InPortConsumer,String> factory 
            = InPortConsumerFactory.instance();
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
     * {@.ja OutPortPushConnector の生成}
     * {@.en OutPortPushConnector creation}
     */
    protected OutPortConnector 
    createConnector(final ConnectorProfileHolder cprof,
                                      Properties prop,
                                      InPortConsumer consumer) {
        ConnectorBase.ConnectorInfo profile 
            = new ConnectorBase.ConnectorInfo( cprof.value.name,
                                  cprof.value.connector_id,
                                  CORBA_SeqUtil.refToVstring(cprof.value.ports),
                                  prop); 
        OutPortConnector connector = null;
        synchronized (m_connectors){
            try {
                BufferBase<OutputStream> buffer = null;
                connector = new OutPortPushConnector(profile, consumer, 
                                                        m_listeners, buffer);

                if (connector == null) {
                    rtcout.println(Logbuf.ERROR, 
                                   "old compiler? new returned 0;");
                    return null;
                }
                rtcout.println(Logbuf.TRACE, "OutPortPushConnector create");
    
                m_connectors.add(connector);
                rtcout.println(Logbuf.PARANOID, 
                               "connector push backed: "+m_connectors.size());
                return connector;
            }
            catch (Exception e) {
                rtcout.println(Logbuf.ERROR,
                               "OutPortPushConnector creation failed");
                return null;
            }
        }
    }
    /**
     * {@.ja OutPortPullConnector の生成}
     * {@.en OutPortPullConnector creation}
     */
    protected OutPortConnector 
    createConnector(final ConnectorProfileHolder cprof,
                                      Properties prop,
                                      OutPortProvider provider) {
        ConnectorBase.ConnectorInfo profile 
            = new ConnectorBase.ConnectorInfo(cprof.value.name,
                                 cprof.value.connector_id,
                                 CORBA_SeqUtil.refToVstring(cprof.value.ports),
                                 prop); 
        OutPortConnector connector = null;
        synchronized (m_connectors){
            try {
                BufferBase<OutputStream> buffer = null;
                connector = new OutPortPullConnector(profile, provider, 
                                                        m_listeners, buffer);

                if (connector == null) {
                    rtcout.println(Logbuf.ERROR, 
                                   "old compiler? new returned 0;");
                    return null;
                }
                rtcout.println(Logbuf.TRACE, "OutPortPullConnector create");
    
                m_connectors.add(connector);
                rtcout.println(Logbuf.PARANOID, 
                           "connector push backed: "+m_connectors.size());
                return connector;
            }
            catch (Exception e) {
                rtcout.println(Logbuf.ERROR,
                               "OutPortPullConnector creation failed");
                return null;
            }
        }
    }
    /**
     * {@.ja endian 情報を返す}
     * {@.en Returns endian information}
     *
     * @return 
     *   {@.ja littleの場合true、bigの場合false を返す。}
     *   {@.en If endian information is "Little", it is "True."}
     *
     */
    public boolean isLittleEndian(){
        return m_isLittleEndian;
    }
    protected List<Publisher> m_publishers = new ArrayList<Publisher>();
    protected Properties m_properties = new Properties();
    protected Vector<OutPortConnector> m_connectors 
        = new Vector<OutPortConnector>();
    protected Vector<String> m_providerTypes = new Vector<String>();
    protected Vector<String> m_consumerTypes = new Vector<String>();
    
    private boolean m_isLittleEndian;
    protected ConnectorListeners m_listeners = new ConnectorListeners();
}
