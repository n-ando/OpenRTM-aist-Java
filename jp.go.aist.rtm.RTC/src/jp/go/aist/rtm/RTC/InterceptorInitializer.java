package jp.go.aist.rtm.RTC;

import org.omg.PortableInterceptor.ORBInitInfo;
import org.omg.PortableInterceptor.ORBInitializer;
import org.omg.IOP.TAG_INTERNET_IOP;
import org.omg.IOP.TAG_ALTERNATE_IIOP_ADDRESS;
import org.omg.IOP.TAG_ORB_TYPE;
import org.omg.IOP.TaggedProfile;
import org.omg.IOP.TaggedComponent;
import org.omg.IOP.IORHolder;
import org.omg.IOP.IORHelper;
import org.omg.CORBA.portable.OutputStream;

import org.omg.CORBA.LocalObject;
import org.omg.PortableInterceptor.IORInterceptor;
import org.omg.IOP.Codec;

import jp.go.aist.rtm.RTC.IopIorInterceptor;

import jp.go.aist.rtm.RTC.log.Logbuf;

public class InterceptorInitializer 
      extends LocalObject
      implements ORBInitializer
{
    /**
     * {@.ja コンストラクタ. }
     * {@.en Constructor. }
     */
    public InterceptorInitializer() {
        rtcout = new Logbuf("InterceptorInitializer");
    }

    public void pre_init(ORBInitInfo info) {
      // do nothing
    }
    /**
     * {@.ja IorInterceptorを初期化します。}
     * {@.en Iinitializes IorInterceptor.}
     * @param info
     *   {@.ja インタセプタを登録するための初期化属性とオペレーションを提供する}
     *   {@.en rovides initialization attributes and 
     *         operations by which Interceptors can be registered.} 
     */
    public void post_init(ORBInitInfo info) {
        rtcout.println(rtcout.TRACE, "post_init");
        try {

            org.omg.IOP.Encoding encoding 
                = new org.omg.IOP.Encoding(
                                org.omg.IOP.ENCODING_CDR_ENCAPS.value, 
                                (byte)1,   // GIOP version
                                (byte)2);  // GIOP revision
            Codec codec = info.codec_factory().create_codec(encoding);
            IopIorInterceptor inter =  new IopIorInterceptor(codec);
            info.add_ior_interceptor( inter );
        }
        catch (Exception e) {
            System.out.println("caught an Exception;"+e);
            throw new RuntimeException("Unexpected " + e);
        }
   }

    /**
     * {@.ja ロガーストリーム}
     */
    private Logbuf rtcout;
}



