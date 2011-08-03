package jp.go.aist.rtm.RTC.port;

import java.util.Vector;

import jp.go.aist.rtm.RTC.ObjectManager;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.PortServiceListHolderFactory;
import jp.go.aist.rtm.RTC.util.equalFunctor;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;

import RTC.PortService;
import RTC.PortServiceListHolder;
import RTC.PortServiceOperations;
import RTC.PortProfile;
import RTC.PortProfileListHolder;

import jp.go.aist.rtm.RTC.log.Logbuf;
/**
 * <p>Portの管理を行うクラスです。</p>
 */
public class PortAdmin {

    /**
     * <p>コンストラクタです。</p>
     * 
     * @param orb ORBオブジェクト
     * @param poa POAオブジェクト
     */
    public PortAdmin(ORB orb, POA poa) {
	this.m_pORB = orb;
        this.m_pPOA = poa;
        rtcout = new Logbuf("PortAdmin");
    }
    
    /**
     * <p>登録されているPortのリストを取得します。</p>
     * 
     * @return Portオブジェクトリストを内包するPortServiceListHolderオブジェクト
     */
    public PortServiceListHolder getPortList() {
        return PortServiceListHolderFactory.clone(this.m_portRefs);
    }

    /**
     * {@.ja Port リストの取得}
     * {@.en Get PortServiceList}
     *
     * <p>
     * {@.ja addPort() により登録された Port の リストを取得する。}
     * {@.en This operation returns the pointer to the PortServiceList of
     * Ports registered by addPort().}
     * </p>
     *
     * @return 
     *   {@.ja Port リスト}
     *   {@.en The pointer points PortServiceList}
     *
     */
    public PortServiceListHolder getPortServiceList() {
        return PortServiceListHolderFactory.clone(this.m_portRefs);
    }

    /**
     * <p>全ての Port のインターフェースを activates する。</p>
     */
    public void activatePorts() {
	Vector<PortBase> ports;
	ports = this.m_portServants.getObjects();
	int len = ports.size();
	for (int i = 0; i < len; ++i) {
	    ports.get(i).activateInterfaces();
	}
    }

    /**
     * <p>全ての Port のインターフェースを deactivates する。</p>
     */
    public void deactivatePorts() {
	Vector<PortBase> ports;
	ports = this.m_portServants.getObjects();
	int len = ports.size();
	for (int i = 0; i < len; ++i) {
	    ports.get(i).deactivateInterfaces();
	}
    }

    /**
     * {@.ja PorProfile リストの取得}
     * {@.en Get PorProfileList}
     *
     * <p>
     * {@.ja addPort() により登録された Port の Profile リストを取得する。}A
     * {@.en This operation gets the Profile list of Ports registered by 
     * addPort().}
     * </p>
     *
     * @return 
     *   {@.ja Portオブジェクトリストを
     *   内包するPortServiceListHolderオブジェクト}
     *   {@.en The pointer points PortProfile list}
     *
     */
    public final PortProfileListHolder getPortProfileList() {
        PortProfileListHolder port_profs = new PortProfileListHolder();
        port_profs.value = new PortProfile[0]; 
        port_prof_collect p = new port_prof_collect(port_profs);
        //
	//        for( PortBase port : m_portServants.getObjects()) {
        for( PortService port : m_portRefs.value) {
            p.operator(port);
        }

        return port_profs;
    }
    
    /**
     * {@.ja Port のオブジェクト参照の取得}
     * {@.en Get the reference to Port object}
     *
     * <p>
     * {@.ja port_name で指定した Port のオブジェクト参照を返す。
     * port_name で指定する Port はあらかじめ addPort() で登録されてい
     * なければならない。}
     * {@.en This operation returns the reference of Port object specified
     * by port_name.
     * The port specified by port_name must be already registered in 
     * addPort().}
     * </p>
     *
     * @param portName 
     *   {@.ja Portの名前}
     *   {@.en The name of Port to be returned the reference.}
     * @return PortService_ptr 
     *   {@.ja 指定されたポート名を持つPortのCORBAオブジェクト参照を返します。
     * 合致するポート名を持つものが見つからない場合はnullを返します。}
     *   {@.en Port object reference.}
     *
     */
    public PortService getPortRef(final String portName) {
        
        PortService port = null;
        
        int index 
            = CORBA_SeqUtil.find(this.m_portRefs, new find_port_name(portName));
        if (index >= 0) {
            port = this.m_portRefs.value[index];
        }
        
        return port;
    }

    /**
     * {@.ja Port のサーバントのポインタの取得}
     * {@.en Get pointer to the Port's servant}
     *
     * <p>
     * {@.ja port_name で指定した Port のサーバントのポインタを返す。
     * port_name で指定する Port はあらかじめ addPort() で登録されてい
     * なければならない。}
     * {@.en This operation returns the pointer to the PortBase servant 
     * registered by addPort().
     * The port specified by port_name must be already registered in 
     * addPort().}
     * </p>
     *
     * @param portName 
     *   {@.ja Portの名前}
     *   {@.en The name of Port}
     * @return PortBase 
     *   {@.ja 指定されたポート名を持つPortサーバントのオブジェクト}
     *   {@.en The Ojbect to Port's servant.}
     *
     */
    public PortBase getPort(final String portName) {
        return this.m_portServants.find(new find_port_name(portName));
    }

    /**
     * <p> Regsiter the Port </p>
     *
     * This operation registers the Port's servant given by argument.
     * The given Port's servant will be activated on the POA that is given
     * to the constructor, and the created object reference is set
     * to the Port's profile.
     *
     * @param port The Port's servant.
     * @return Register result (Successful:true, Failed:false)
     *
     */
    public boolean addPort(PortBase port) {
        // Check for duplicate
        int index = 
            CORBA_SeqUtil.find(m_portRefs, new find_port_name(port.getName()));
        if(index != -1){
            return false;
        }

        // Store Port's ref to PortServiceList
        CORBA_SeqUtil.push_back(m_portRefs, port.getPortRef());
    
        // Store Port servant
        return m_portServants.registerObject(port, 
                            new find_port_name(port.get_port_profile().name));
    }

    /**
     *
     * <p> Regsiter the Port </p>
     *
     * This operation registers the Port's servant given by argument.
     * The given Port's servant will be activated on the POA that is given
     * to the constructor, and the created object reference is set
     * to the Port's profile.
     *
     * @param port The Port's servant.
     * @return Register result (Successful:true, Failed:false)
     *
     */
    public boolean addPort(PortService port) {
        // Check for duplicate
        PortProfile prof = port.get_port_profile();
        String name = prof.name;
        if (CORBA_SeqUtil.find(m_portRefs, new find_port_name(name)) != -1) {
            return false;
        }

        CORBA_SeqUtil.push_back(m_portRefs, port);
        return true;
    }

    /**
     * <p>Portサーバントを登録します。</p>
     * 
     * @param port 登録するPortサーバント
     */
    public void registerPort(PortBase port) {
        
        // Store Port's ref to PortList
        CORBA_SeqUtil.push_back(this.m_portRefs, port.getPortRef());

        // Store Port servant
        m_portServants.registerObject(port, 
                            new find_port_name(port.get_port_profile().name));
    }
    
    /**
     * {@.ja Port を登録する}
     * {@.en Regsiter the Port}
     *
     * <p>
     * {@.ja 引数 port で指定された Port のサーバントを登録する。
     * 登録された Port のサーバントはコンストラクタで与えられたPOA 上で
     * activate され、そのオブジェクト参照はPortのProfileにセットされる。}
     * {@.en This operation registers the Port's servant given by argument.
     * The given Port's servant will be activated on the POA that is given
     * to the constructor, and the created object reference is set
     * to the Port's profile.}
     * </p>
     *
     * @param port 
     *   {@.ja Port サーバント}
     *   {@.en The Port's servant.}
     *
     */
    public void registerPort(PortService port) {
        if(!addPort(port)){
            rtcout.println(Logbuf.ERROR, 
                                    "registerPort(PortService_ptr) failed.");
        }
    }
    
    /**
     * {@.ja Port の登録を解除する}
     * {@.en Unregister the Port registration}
     * <p>
     * {@.ja 引数 port で指定された Port の登録を解除する。
     * 削除時に Port は deactivate され、PortのProfileのリファレンスには、
     * nil値が代入される。}
     * {@.en This operation unregisters the Port registration.
     * When the Port is unregistered, Port is deactivated, and the object
     * reference in the Port's profile is set to nil.}
     * </p>
     * @param port
     *   {@.ja Port サーバント}
     *   {@.en The Port's servant.}
     * @return
     *   {@.ja 削除結果(削除成功:true，削除失敗:false)}
     *   {@.en Unregister result (Successful:true, Failed:false)}
     */
    public boolean removePort(PortBase port){
        try {
            port.disconnect_all();
            // port.shutdown();

            final String tmp = port.getProfile().name;
            CORBA_SeqUtil.erase_if(m_portRefs, new find_port_name(tmp));

            m_pPOA.deactivate_object(m_pPOA.servant_to_id(port));
            port.setPortRef(null);

            if(m_portServants.unregisterObject(new find_port_name(tmp))==null){
                return false;
            }
            else{
                return true;
            }
            
        } catch(Exception ignored) {
            ignored.printStackTrace();
            return false;
        }
    }

    /**
     * {@.ja Port の登録を解除する}
     * {@.en Unregister the Port registration}
     * <p>
     * {@.ja 引数 port で指定された Port の登録を解除する。
     * 削除時に Port は deactivate され、PortのProfileのリファレンスには、
     * nil値が代入される。}
     * {@.en This operation unregisters the Port registration.
     * When the Port is unregistered, Port is deactivated, and the object
     * reference in the Port's profile is set to nil.}
     * </p>
     * @param port
     *   {@.ja Port サーバント}
     *   {@.en The Port's servant.}
     * @return
     *   {@.ja 削除結果(削除成功:true，削除失敗:false)}
     *   {@.en Unregister result (Successful:true, Failed:false)}
     */
    public boolean removePort(PortService port) {
        try {
            // port.disconnect_all();
            // port.shutdown();

            // final String tmp = port.get_port_profile().name;
            // CORBA_SeqUtil.erase_if(m_portRefs, new find_port_name(tmp));
            CORBA_SeqUtil.erase_if(m_portRefs, new find_port(port));

            // m_pPOA.deactivate_object(m_pPOA.servant_to_id(port));
            // port.setPortRef(null);

            // m_portServants.unregisterObject(new find_port_name(tmp));
            return true;
        } catch(Exception ignored) {
            ignored.printStackTrace();
            return false;
        }
    }

    /**
     * 
     * {@.ja [local interface] Port の登録を削除する}
     * {@.en [local interface] Unregister Port}
     * <p>
     * {@.ja 指定されたPortサーバントの登録を解除します。
     *       削除時に Port は deactivate され、PortのProfileのリファレンスには、
     *       nil値が代入される。}
     * {@.en This operation unregisters a Port held by this RTC.
     *       When the Port is unregistered, Port is deactivated, and the object
     *       reference in the Port's profile is set to nil.}
     * </p>
     * @param port 
     *   {@.ja Port サーバント}
     *   {@.en The Port's servant.}
     */
    public void deletePort(PortBase port) {
        
        if (!removePort(port)) {
            rtcout.println(Logbuf.ERROR, "deletePort(PortBase&) failed.");
        }
    }

    /**
     * 
     * {@.ja [local interface] Port の登録を削除する}
     * {@.en [local interface] Unregister Port}
     * <p>
     * {@.ja 指定されたPortサーバントの登録を解除します。
     *       削除時に Port は deactivate され、PortのProfileのリファレンスには、
     *       nil値が代入される。}
     * {@.en This operation unregisters a Port held by this RTC.
     *       When the Port is unregistered, Port is deactivated, and the object
     *       reference in the Port's profile is set to nil.}
     * </p>
     * @param port 
     *   {@.ja Port サーバント}
     *   {@.en The Port's servant.}
     */
    public void deletePort(PortService port) {

        if (!removePort(port)) {
            rtcout.println(Logbuf.ERROR, "deletePort(PortService) failed.");
        }
    }

    /**
     * {@.ja 名称指定によりPort の登録を解除する}
     * {@.en Unregister the Port's registration by its name}
     *
     * <p>
     * {@.ja 引数で指定された名前を持つ Port の登録を削除する。
     * 削除時に Port は deactivate され、PortのProfileのリファレンスには、
     * nil値が代入される。}
     * {@.en This operation unregister the Port's registration specified by
     * port_ name.  When the Port is unregistered, Port is
     * deactivated, and the object reference in the Port's profile is
     * set to nil.}
     * </p>
     *
     * @param portName 
     *   {@.ja Port の名前}
     *   {@.en The Port's name.}
     *
     */
    public void deletePortByName(final String portName) {
        
        if (portName == null || portName.length() == 0) {
            return;
        }
        
        removePort(this.m_portServants.find(new find_port_name(portName)));
    }

    /**
     * {@.ja 全ての Port をdeactivateし登録を削除する}
     * {@.en Deactivate all Ports and unregister them}
     *
     * <p>
     * {@.ja 登録されている全てのPortに対して、サーバントのdeactivateを行い、
     * 登録リストから削除する。}
     * {@.en This operation deactivates the all Port and deletes the all Port's
     * registrations from the list.}
     *
     */
    public void finalizePorts() {
        
        Vector<PortBase> ports = m_portServants.getObjects();
        for (int i = 0; i < ports.size(); i++) {
            this.deletePort(ports.elementAt(i));
        }
    }
    
    // POA へのポインタ
    private POA m_pPOA;
    private ORB m_pORB;

    // PortのCORBAオブジェクト参照のリスト
    private PortServiceListHolder  m_portRefs = PortServiceListHolderFactory.create();
    
    protected class find_port_name implements equalFunctor {
        
        public find_port_name(final String name) {
            this.m_name = name;
        }

        public boolean equalof(Object element) {
            PortServiceOperations port = (PortServiceOperations) element;
            return this.m_name.equals(port.get_port_profile().name);
        }
        
        public String m_name;
    }
    
    protected class find_port implements equalFunctor {
        
        public find_port(final PortService port ) {
            this.m_port = port;
        }

        public boolean equalof(Object element) {
            return m_port._is_equivalent((PortService)element);
        }
        
        public PortService m_port;
    }
    
    // サーバントを直接格納するオブジェクトマネージャ
    private ObjectManager<String, PortBase> m_portServants = new ObjectManager<String, PortBase>();
    
    protected class port_prof_collect {
        public port_prof_collect(PortProfileListHolder p){
            m_p = p;
        }
	/*
        public void operator(final PortBase port) {
            CORBA_SeqUtil.push_back(m_p, port.getPortProfile() );
        }
	*/
        public void operator(final PortService port) {
            CORBA_SeqUtil.push_back(m_p, port.get_port_profile() );
        }
        private PortProfileListHolder m_p;
    }

    /**
     * {@.ja Logging用フォーマットオブジェクト}
     */
    private Logbuf rtcout;


}
