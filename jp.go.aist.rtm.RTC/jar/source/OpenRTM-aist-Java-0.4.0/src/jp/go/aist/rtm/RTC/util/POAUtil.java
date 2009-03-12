package jp.go.aist.rtm.RTC.util;

import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.Servant;

/**
 * <p>POAに関するユーティリティクラスです。</p>
 */
public class POAUtil {

    /**
     * <p>指定されたサーバントをアクティブ化し、そのCORBAリファレンスを取得します。</p>
     * 
     * @param servant サーバントオブジェクト
     * @return サーバントのCORBAリファレンス
     * 
     * @exception Exception 指定された名前が定義済みのサービスに関連していない。
     * 　　　　　　　　　　　 POA に UNIQUE_ID ポリシーが指定されているときに、サーバントが Active Object Map にすでに格納されている 
     * 　　　　　　　　　　　 SYSTEM_ID と RETAIN ポリシーが指定されていない。
     * 　　　　　　　　　　　 オブジェクト ID 値が POA でアクティブになっていない。
     * 　　　　　　　　　　　 ポリシーがない。
     */
    public static org.omg.CORBA.Object getRef(Servant servant) throws Exception {
        
        POA poa = POAHelper.narrow(ORBUtil.getOrb().resolve_initial_references("RootPOA"));
        byte[] id = poa.activate_object(servant);
        org.omg.CORBA.Object ref = poa.id_to_reference(id);
        
        return ref;
    }
}
