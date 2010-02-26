package jp.go.aist.rtm.RTC;

import org.omg.CORBA.ORB;
import org.omg.CORBA.LocalObject;
import org.omg.CORBA.Any;
import org.omg.CORBA.LocalObject;
import org.omg.PortableInterceptor.IORInterceptor;
import org.omg.IOP.TAG_INTERNET_IOP;
import org.omg.IOP.TAG_ALTERNATE_IIOP_ADDRESS;
import org.omg.IOP.TaggedComponent;
import org.omg.IOP.Codec;

import java.util.ArrayList;

import jp.go.aist.rtm.RTC.util.IiopAddressComp;
import jp.go.aist.rtm.RTC.util.IiopAddressCompHelper;

import jp.go.aist.rtm.RTC.log.Logbuf;

/**
 * {@.ja ポータブルインターセプタを利用してIORを書き換える.}
 * {@.en Rewrites IOR by using a portable interceptor.}
 *
 */
public class IopIorInterceptor extends LocalObject 
    implements org.omg.PortableInterceptor.IORInterceptor{

    /**
     * {@.jp}
     */
    private TaggedComponent[] m_components;
    /**
     * {@.jp}
     */
    private String m_name;
    /**
     * {@.jp}
     */
    static private Codec codec;
    /**
     * {@.jp}
     */
    static private ArrayList<IiopAddressComp> m_endpoints 
                                        =  new ArrayList<IiopAddressComp>();

    /**
     * {@.ja エンドポイントを設定. }
     * {@.en Sets the end points. }
     * @param  endpoints 
     *   {@.ja エンドポイント } 
     *   {@.en endpoints}
     *
     */
    public static void SetEndpoints(ArrayList endpoints){
System.out.println("IN   SetEndpoints()");
        m_endpoints = endpoints;
System.out.println("OUT   SetEndpoints()");
    }
    /**
     * {@.ja エンドポイントを設定. }
     * {@.en Sets the end points. }
     * @param  host 
     *   {@.ja ホスト} 
     *   {@.en host}
     * @param  port 
     *   {@.ja ポート} 
     *   {@.en port}
     *
     */
    public static void SetEndpoints(String hostString, String portString){
        short port = 0;
        try {
            port = (short)Integer.parseInt(portString);
        }
        catch(Exception ex){
        }
        
        IiopAddressComp comp = new IiopAddressComp(hostString,port);
        m_endpoints.add(comp);
    }
    public static Codec getCodec(){
        return codec;
    }

    /**
     * {@.ja コンストラクタ.}
     * {@.en Constructor.}
     * @param  code 
     *   {@.ja エンコード} 
     *   {@.en Encoding}
     *
     */
    public IopIorInterceptor( Codec codec ){ 
System.out.println("IN  CodebaseInterceptor");
        rtcout = new Logbuf("IopIorInterceptor");
        this.codec = codec;
System.out.println("OUT CodebaseInterceptor");
    }

    public String name() {
        return IORInterceptor.class.getName();
    }

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
        rtcout.println(rtcout.TRACE, "establish_components()");
System.out.println("IN  CodebaseInterceptor.establish_components");

        //Gets any type.
        ORB orb = ORB.init();
        Any any = orb.create_any();

        //Creates TaggedComponents
System.out.println("    m_endpoints.size()>:"+ m_endpoints.size());
        m_components = new TaggedComponent[m_endpoints.size()];
        for(int ic=0;ic<m_endpoints.size();++ic){
            IiopAddressComp lp = m_endpoints.get(ic);
            IiopAddressCompHelper.insert(any, lp);
            byte[] by = null;
            try {
                by = codec.encode_value(any);
            }
            catch (Exception ex){
                rtcout.println(rtcout.WARN, 
                    "Invalid Type For Encoding:" +lp.HostID+","+lp.Port);
                continue;
            }
            m_components[ic] = new TaggedComponent(
                org.omg.IOP.TAG_ALTERNATE_IIOP_ADDRESS.value, by);
        }

       
System.out.println("    m_components.length>:"+ m_components.length);
       for ( int ic=0; ic<m_components.length; ++ic ) {
           info.add_ior_component_to_profile( m_components[ic], 
                                               TAG_INTERNET_IOP.value );
       }

System.out.println("OUT CodebaseInterceptor.establish_components");
    }
 
    /**
     * {@.jp ロガーストリーム}
     */
    private Logbuf rtcout;
}




