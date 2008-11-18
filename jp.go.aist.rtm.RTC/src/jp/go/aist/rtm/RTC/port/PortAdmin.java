package jp.go.aist.rtm.RTC.port;

import java.util.Vector;

import jp.go.aist.rtm.RTC.ObjectManager;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.PortListHolderFactory;
import jp.go.aist.rtm.RTC.util.equalFunctor;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;

import RTC.Port;
import RTC.PortListHolder;
import RTC.PortOperations;
import RTC.PortProfile;
import RTC.PortProfileListHolder;

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
    }
    
    /**
     * <p>登録されているPortのリストを取得します。</p>
     * 
     * @return Portオブジェクトリストを内包するPortListHolderオブジェクト
     */
    public PortListHolder getPortList() {
        return PortListHolderFactory.clone(this.m_portRefs);
    }
    
    /**
     * <p>登録されているPortのリストを取得します。</p>
     * 
     * @return Portオブジェクトリストを内包するPortListHolderオブジェクト
     */
    public final PortProfileListHolder getPortProfileList() {
        PortProfileListHolder port_profs = new PortProfileListHolder();
        port_profs.value = new PortProfile[0]; 
        port_prof_collect p = new port_prof_collect(port_profs);
        //
        for( PortBase port : m_portServants.getObjects()) {
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
    public Port getPortRef(final String portName) {
        
        Port port = null;
        
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
     * <p>Portサーバントを登録します。</p>
     * 
     * @param port 登録するPortサーバント
     */
    public void registerPort(PortBase port) {
        
        // Store Port's ref to PortList
        CORBA_SeqUtil.push_back(this.m_portRefs, port.getPortRef());

        // Store Port servant
        m_portServants.registerObject(port, new find_port_name(port.get_port_profile().name));
    }
    
    /**
     * <p>指定されたPortサーバントの登録を解除します。</p>
     * 
     * @param port 登録解除するPortサーバントのオブジェクト
     */
    public void deletePort(PortBase port) {
        
        try {
            port.disconnect_all();
            // port.shutdown();

            final String tmp = port.getProfile().name;
            CORBA_SeqUtil.erase_if(m_portRefs, new find_port_name(tmp));

            m_pPOA.deactivate_object(m_pPOA.servant_to_id(port));
            port.setPortRef(null);

            m_portServants.unregisterObject(new find_port_name(tmp));
            
        } catch(Exception ignored) {
            ignored.printStackTrace();
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
    
    // ORB へのポインタ
    private ORB m_pORB;
    // POA へのポインタ
    private POA m_pPOA;
    // PortのCORBAオブジェクト参照のリスト
    private PortListHolder  m_portRefs = PortListHolderFactory.create();
    
    protected class find_port_name implements equalFunctor {
        
        public find_port_name(final String name) {
            this.m_name = name;
        }

        public boolean equalof(Object element) {
            PortOperations port = (PortOperations) element;
            return this.m_name.equals(port.get_port_profile().name);
        }
        
        public String m_name;
    }
    
    // サーバントを直接格納するオブジェクトマネージャ
    private ObjectManager<String, PortBase> m_portServants = new ObjectManager<String, PortBase>();
    
    protected class port_prof_collect {
        public port_prof_collect(PortProfileListHolder p){
            m_p = p;
        }
        public void operator(final PortBase port) {
            CORBA_SeqUtil.push_back(m_p, port.getPortProfile() );
        }
        private PortProfileListHolder m_p;
    }
}
