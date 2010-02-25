package jp.go.aist.rtm.RTC;

//import org.omg.CORBA.*;
//import org.omg.CosNaming.*;
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

public class InterceptorInitializer 
      extends LocalObject
      implements ORBInitializer
{
   public InterceptorInitializer() {
System.out.println("InterceptorInitializer");

   }

   public void pre_init(ORBInitInfo info) {
      // do nothing
   }
   public void post_init(ORBInitInfo info) 
   {
System.out.println("IN  post_init");
      try {
System.out.println("---010---");

org.omg.IOP.Encoding encoding = new org.omg.IOP.Encoding(org.omg.IOP.ENCODING_CDR_ENCAPS.value, 
                                (byte)1,   // GIOP version
                                (byte)2);  // GIOP revision
Codec codec = info.codec_factory().create_codec(encoding);
          IopIorInterceptor inter =  new IopIorInterceptor(codec);
System.out.println("---040---");
          info.add_ior_interceptor( inter );
System.out.println("---050---");
      }
      catch (Exception e) {
         System.out.println("caught an Exception;"+e);
         throw new RuntimeException("Unexpected " + e);
      }
System.out.println("OUT  post_init");
   }

}



