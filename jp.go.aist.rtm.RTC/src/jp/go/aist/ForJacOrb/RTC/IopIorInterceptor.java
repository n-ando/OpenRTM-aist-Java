package jp.go.aist.rtm.RTC;

import java.util.ArrayList;

import jp.go.aist.rtm.RTC.log.Logbuf;
import jp.go.aist.rtm.RTC.util.IiopAddressComp;
import jp.go.aist.rtm.RTC.util.IiopAddressCompHelper;

import org.omg.CORBA.Any;
import org.omg.CORBA.LocalObject;
import org.omg.CORBA.ORB;
import org.omg.IOP.Codec;
import org.omg.IOP.TAG_INTERNET_IOP;
import org.omg.IOP.TaggedComponent;
import org.omg.PortableInterceptor.IORInterceptor;

//<+JacORB
import org.jacorb.orb.etf.ProtocolAddressBase;
import org.jacorb.orb.iiop.IIOPAddress;
import org.jacorb.orb.iiop.IIOPProfile;
import java.util.List;
import java.util.Iterator;
//+>

/**
 * {@.ja ポータブルインターセプタを利用してIORを書き換える.}
 * {@.en Rewrites IOR by using a portable interceptor.}
 *
 */
public class IopIorInterceptor extends LocalObject 
    implements org.omg.PortableInterceptor.IORInterceptor{

    private static final long serialVersionUID = 7953662324638701357L;
    
    /**
     * {@.ja Codec}
     * {@.en Codec}
     */
    static private Codec codec;
    /**
     * {@.ja エンドポイント}
     * {@.en endpoints}
     */
    static private ArrayList<IiopAddressComp> m_endpoints 
                                        =  new ArrayList<IiopAddressComp>();

    /**
     * {@.ja エンドポイントを設定.}
     * {@.en Sets the end points.}
     * @param  endpoints 
     *   {@.ja エンドポイント} 
     *   {@.en endpoints}
     *
     */
    public static void setEndpoints(ArrayList endpoints){
        m_endpoints = endpoints;
    }

    /**
     * {@.ja エンドポイントを設定.}
     * {@.en Sets the end points.}
     * @param  hostString 
     *   {@.ja ホスト} 
     *   {@.en host}
     * @param  portString 
     *   {@.ja ポート} 
     *   {@.en port}
     *
     */
    public static void setEndpoints(String hostString, String portString){
        short port = 0;
        try {
            port = (short)Integer.parseInt(portString);
        }
        catch(Exception ex){
        }
        
        IiopAddressComp comp = new IiopAddressComp(hostString,port);
        m_endpoints.add(comp);
    }

    /**
     * {@.ja エンドポイントを書き換える.}
     * {@.en Replaces the end point the end point.}
     * <p>
     * {@.ja エンドポイントリスト内のポート番号が 0 のエンドポイントを
     * 与えられたポート番号に置き換える。}
     * {@.en Replaces the port number of the end point with the port number 
     * of the argument.Only the end point of port number 0 is replaced.} 
     * </p>
     * @param orb 
     *   {@.ja ORB}
     *   {@.en ORB}
     */
    public static void replacePort0(ORB orb) {
        if(orb==null){
            return;
        }
        if(m_endpoints==null){
            return;
        }
        if(m_endpoints.size()==0){
            return;
        }
        else {
            int ic;
            for(ic=0;ic<m_endpoints.size();++ic){
                if(m_endpoints.get(ic).Port==0){
                    break;
                }
            }
            if(ic==m_endpoints.size()){
                return ;
            }
        }

        //<+ JacORB
        ProtocolAddressBase address = null;
        org.jacorb.orb.ORB jacorb = (org.jacorb.orb.ORB)orb;
        if (jacorb.getBasicAdapter() == null) {
            return;
        }
        List eplist = jacorb.getBasicAdapter().getEndpointProfiles();
        for (Iterator i = eplist.iterator(); i.hasNext(); ) {
            org.omg.ETF.Profile p = (org.omg.ETF.Profile)i.next();
            if (p instanceof IIOPProfile) {
                address = ((IIOPProfile)p).getAddress();
                break;
            }
        }
        if (address == null) {
            return;
        }
        short port = (short)((IIOPAddress)address).getPort();
        //+>

        for(int ic=0;ic<m_endpoints.size();ic++){
            if(m_endpoints.get(ic).Port==0){
                m_endpoints.get(ic).Port = port;
            }
        }
    }
    /**
     * {@.ja コンストラクタ.}
     * {@.en Constructor.}
     * @param  codec 
     *   {@.ja エンコード} 
     *   {@.en Encoding}
     *
     */
    public IopIorInterceptor( Codec codec ){ 
        rtcout = new Logbuf("IopIorInterceptor");
        this.codec = codec;
    }

    /**
     * {@.ja インターセプタ名を返す}
     * {@.en Returns the name of the interceptor.}
     * @return 
     *   {@.ja インターセプタ名} 
     *   {@.en the name of the interceptor.}
     *
     */
    public String name() {
        return IORInterceptor.class.getName();
    }

    /**
     * {@.ja インターセプタの破棄}
     * {@.en Destroys he interceptor.}
     *
     */
    public void destroy() {
    }

    /**
     * {@.ja TAG_ALTERNATE_IIOP_ADDRESS へ endpoint を設定する}
     * {@.en sets endpoint to TAG_ALTERNATE_IIOP_ADDRESS.} 
     * @param  info 
     *   {@.ja IORInfo} 
     *   {@.en IORInfo}
     */
    public void establish_components(org.omg.PortableInterceptor.IORInfo info){
        rtcout.println(Logbuf.TRACE, "establish_components()");

        //Gets any type.
        ORB orb = ORB.init();
        Any any = orb.create_any();

        //Creates TaggedComponents
        TaggedComponent[] components = new TaggedComponent[m_endpoints.size()];
        for(int ic=0;ic<m_endpoints.size();++ic){
            IiopAddressComp lp = m_endpoints.get(ic);

            IiopAddressCompHelper.insert(any, lp);
            byte[] by = null;
            try {
                by = codec.encode_value(any);
            }
            catch (Exception ex){
                rtcout.println(Logbuf.WARN, 
                    "Invalid Type For Encoding:" +lp.HostID+","+lp.Port);
                continue;
            }
            components[ic] = new TaggedComponent(
                org.omg.IOP.TAG_ALTERNATE_IIOP_ADDRESS.value, by);
        }

       
       for ( int ic=0; ic<components.length; ++ic ) {
           info.add_ior_component_to_profile( components[ic], 
                                               TAG_INTERNET_IOP.value );
       }

    }
 
    /**
     * {@.ja ロガーストリーム}
     */
    private Logbuf rtcout;
}




