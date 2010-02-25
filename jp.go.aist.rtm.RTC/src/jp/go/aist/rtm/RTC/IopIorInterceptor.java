package jp.go.aist.rtm.RTC;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Any;
import org.omg.CORBA.LocalObject;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManager;
import org.omg.IOP.TAG_INTERNET_IOP;
import org.omg.IOP.TAG_ALTERNATE_IIOP_ADDRESS;
import org.omg.IOP.TAG_ORB_TYPE;
import org.omg.IOP.TaggedProfile;
import org.omg.IOP.TaggedComponent;

import org.omg.CORBA.LocalObject;
import org.omg.PortableInterceptor.IORInterceptor;
import org.omg.IOP.Codec;

import jp.go.aist.rtm.RTC.util.IiopAddressComp;
import jp.go.aist.rtm.RTC.util.IiopAddressCompHelper;

public class IopIorInterceptor extends LocalObject 
    implements org.omg.PortableInterceptor.IORInterceptor{

    private TaggedComponent[] m_components;
    private int m_profile;
    private String m_name;
    static private Codec codec;
    static private org.omg.PortableInterceptor.IORInfo m_info;

    public static org.omg.PortableInterceptor.IORInfo getIORInfo(){
        return m_info;
    }
    public static Codec getCodec(){
        return codec;
    }

    public IopIorInterceptor( Codec codec ){ 
System.out.println("IN  CodebaseInterceptor");
      this.codec = codec;
System.out.println("OUT CodebaseInterceptor");
    }

    public String name() {
        return IORInterceptor.class.getName();
    }

    public void destroy() {
    }

   public void establish_components( org.omg.PortableInterceptor.IORInfo info) {
System.out.println("IN  CodebaseInterceptor.establish_components");

m_info = (org.omg.PortableInterceptor.IORInfo)info;
if(m_info==null){
System.out.println("m_info is null.");
}
else{
System.out.println("m_info is not null.");
}


          String ipstr = "192.168.100.224";
          short port = (short)65533;


ORB orb = ORB.init();
Any any = orb.create_any();

IiopAddressComp lp = new IiopAddressComp(ipstr,port);
IiopAddressCompHelper.insert(any, lp);

byte[] by =null;
try {
by = codec.encode_value(any);
}
catch (Exception ex){
}

System.out.print("by("+by.length+")>:");
for(int ic=0;ic<by.length;++ic){
System.out.print(" "+by[ic]);
}
System.out.println("");
          TaggedComponent altAddrComponent= new TaggedComponent(
              org.omg.IOP.TAG_ALTERNATE_IIOP_ADDRESS.value, by);

        m_components = new TaggedComponent[ 1 ];
        m_components[ 0 ] = altAddrComponent;
        m_profile = TAG_INTERNET_IOP.value;
       
System.out.println("    m_components.length>:"+ m_components.length);
System.out.println("    m_components[0]>:"+ m_components[0]);
System.out.println("    m_profile>:"+ m_profile);
       for ( int ic=0; ic<m_components.length; ++ic ) {
           info.add_ior_component_to_profile( m_components[ic], m_profile );
       }

System.out.println("OUT CodebaseInterceptor.establish_components");
   }
 
}




