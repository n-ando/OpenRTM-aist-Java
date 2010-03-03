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
     * <p>登録されているPortのリストを取得します。</p>
     * 
     * @return Portオブジェクトリストを内包するPortServiceListHolderオブジェクト
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
     * <p>指定されたポート名を持つPortのCORBAオブジェクト参照を取得します。</p>
     * 
     * @param portName ポート名
     * @return 指定されたポート名を持つPortのCORBAオブジェクト参照を返します。
     * 合致するポート名を持つものが見つからない場合はnullを返します。
     */
    public PortService getPortRef(final String portName) {
        
        PortService port = null;
        
        int index = CORBA_SeqUtil.find(this.m_portRefs, new find_port_name(portName));
        if (index >= 0) {
            port = this.m_portRefs.value[index];
        }
        
        return port;
    }

    /**
     * <p>指定されたポート名を持つPortサーバントを取得します。</p>
     * 
     * @param portName ポート名
     * @return 指定されたポート名を持つPortサーバントのオブジェクト
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
     * <p> registerPort </p>
     *
     * @param port PortService
     */
    public void registerPort(PortService port) {
	if (port == null) {
	    System.out.println("registerPort() port is null.");
	}
        CORBA_SeqUtil.push_back(this.m_portRefs, port);
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
     *   {@.jaPort サーバント}
     *   {@.en The Port's servant.}
     * @returni
     *   {@.ja削除結果(削除成功:true，削除失敗:false)}
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
     *   {@.enThe Port's servant.}
     * @return
     *   {@.ja削除結果(削除成功:true，削除失敗:false)}
     *   {@.enUnregister result (Successful:true, Failed:false)}
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
     *   {@.en he Port's servant.}
     */
    public void deletePort(PortBase port) {
        
        if (!removePort(port)) {
            rtcout.println(rtcout.ERROR, "deletePort(PortBase&) failed.");
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
     *   {@.en he Port's servant.}
     */
    public void deletePort(PortService port) {

        if (!removePort(port)) {
            rtcout.println(rtcout.ERROR, "deletePort(PortService) failed.");
        }
    }

    /**
     * <p>指定されたポート名を持つPortサーバントの登録を解除します。</p>
     * 
     * @param portName ポート名
     */
    public void deletePortByName(final String portName) {
        
        if (portName == null || portName.length() == 0) {
            return;
        }
        
        deletePort(this.m_portServants.find(new find_port_name(portName)));
    }

    /**
     * <p>登録されているすべてのPortサーバントについて、deactivateしたうえで登録を解除します。</p>
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
