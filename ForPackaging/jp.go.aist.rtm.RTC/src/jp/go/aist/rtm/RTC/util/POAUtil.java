package jp.go.aist.rtm.RTC.util;

import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.Servant;

/**
 * {@.ja POAに関するユーティリティクラス}
 * {@.en  Utility class concerning POA}
 */
public class POAUtil {

    /**
     * {@.ja 指定されたサーバントをアクティブ化し、
     * そのCORBAリファレンスを取得する}
     * {@.en Makes the specified servant active, and gets CORBA reference.}
     * 
     * @param servant 
     *   {@.ja サーバントオブジェクト}
     *   {@.en Servant Object}
     * @return 
     *   {@.ja サーバントのCORBAリファレンス}
     *   {@.en CORBA reference of servant}
     * 
     * @exception Exception 
     *   {@.ja 指定された名前が定義済みのサービスに関連していない。
     *         POA に UNIQUE_ID ポリシーが指定されているときに、
     *         サーバントが Active Object Map にすでに格納されている 
     *         SYSTEM_ID と RETAIN ポリシーが指定されていない。
     *         オブジェクト ID 値が POA でアクティブになっていない。
     *         ポリシーがない。}
     *   {@.en Not relating to the service that the specified name defined. 
     *         Neither SYSTEM_ID nor RETAIN policy that the servant has already
     *         been stored in Active Object Map are specified when UNIQUE_ID 
     *         policy is specified for POA. 
     *         Object ID value is not active on POA. 
     *         There is no policy.}
     */
    public static org.omg.CORBA.Object getRef(Servant servant) throws Exception {
        
        POA poa = POAHelper.narrow(ORBUtil.getOrb().resolve_initial_references("RootPOA"));
        byte[] id = poa.activate_object(servant);
        org.omg.CORBA.Object ref = poa.id_to_reference(id);
        
        return ref;
    }
}
