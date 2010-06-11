package jp.go.aist.rtm.RTC;

import jp.go.aist.rtm.RTC.log.Logbuf;

import org.omg.CORBA.LocalObject;
import org.omg.IOP.Codec;
import org.omg.PortableInterceptor.ORBInitInfo;
import org.omg.PortableInterceptor.ORBInitializer;

  /**
   * {@.ja インタセプタの登録と ORB の初期化をするクラス。}
   * {@.en Class that registration and ORB of Interceptor initialize.}
   *
   */
public class InterceptorInitializer 
      extends LocalObject
      implements ORBInitializer
{
    private static final long serialVersionUID = 5227697632011675642L;

    /**
     * {@.ja コンストラクタ.}
     * {@.en Constructor.}
     */
    public InterceptorInitializer() {
        rtcout = new Logbuf("InterceptorInitializer");
    }

    /**
     * {@.ja IorInterceptorを初期化する。}
     * {@.en Iinitializes IorInterceptor.}
     * @param info
     *   {@.ja インタセプタを登録するための初期化属性とオペレーションを
     *   提供するORBInitInfo}
     *   {@.en rovides initialization attributes and 
     *         operations by which Interceptors can be registered.} 
     */
    public void pre_init(ORBInitInfo info) {
      // do nothing
    }
    /**
     * {@.ja IorInterceptorを初期化する。}
     * {@.en Iinitializes IorInterceptor.}
     * @param info
     *   {@.ja インタセプタを登録するための初期化属性とオペレーションを
     *   提供する}
     *   {@.en rovides initialization attributes and 
     *         operations by which Interceptors can be registered.} 
     */
    public void post_init(ORBInitInfo info) {
        rtcout.println(Logbuf.TRACE, "post_init");
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



