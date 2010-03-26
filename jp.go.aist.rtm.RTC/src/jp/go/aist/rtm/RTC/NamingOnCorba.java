package jp.go.aist.rtm.RTC;

import org.omg.CORBA.ORB;

import jp.go.aist.rtm.RTC.log.Logbuf;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.StringUtil;
/**
* <p>CORBA用Naming Serviceクラスです。</p>
*/
class NamingOnCorba implements NamingBase {

    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     * <p>
     * {@.ja コンストラクタ。第2引数に与えるネームサービス名は、ネームサービ
     * スのホスト名とポート番号を ":" で区切ったものである。ポート番号
     * が省略された場合、2809番ポートが使用される。}
     * {@.en Constructor. Naming service name that is given at the second
     * argument is host name and port number hoined with ":". If the
     * port number is abbreviated, the default port number 2809 is
     * used.}
     * </p>
     *
     * @param orb 
     *   {@.ja ORB}
     *   {@.en ORB}
     * @param names 
     *   {@.ja NamingServer 名称}
     *   {@.en Name of NamingServer}
     *
     */
    public NamingOnCorba(ORB orb, final String names) {
        if(java.lang.System.getProperty("develop_prop.debug")!=null){ 
            System.out.println("IN  NamingOnCorba");
        }
        try {
            m_cosnaming = new CorbaNaming(orb, names);
        } catch (Exception e) {
            e.printStackTrace();
        }
        m_endpoint = "";
        m_replaceEndpoint = false;

        rtcout = new Logbuf("NamingOnCorba");

        Properties prop = Manager.instance().getConfig();
        String replace_endpoint 
            = prop.getProperty("corba.nameservice.replace_endpoint");
        m_replaceEndpoint = 
            StringUtil.toBool(replace_endpoint, "YES", "NO", true);

        String[] host_port = names.split(":");
        try{
            if(java.lang.System.getProperty("develop_prop.debug")!=null){ 
                System.out.println("getByName("+host_port[0]+")");
            }
//            java.net.NetworkInterface ni 
//                    = java.net.NetworkInterface.getByName(host_port[0]);
            java.net.InetAddress ni 
                    = java.net.InetAddress.getByName(host_port[0]);
            if(java.lang.System.getProperty("develop_prop.debug")!=null){ 
                if(ni==null){
                    System.out.println("ni is null.");
                }
                else{
                    System.out.println("ni is not null.>:"+ni);
                    System.out.println("ni is not null.>:"+ni.getHostName());
                }
            }
//            m_endpoint = ni.getHostAddress();
            java.net.NetworkInterface nic 
                    = java.net.NetworkInterface.getByInetAddress(ni); 
            if(java.lang.System.getProperty("develop_prop.debug")!=null){ 
                if(nic==null){
                    System.out.println("nic is null.");
                }
                else{
                    System.out.println("nic is not null.>:"+nic);
                    System.out.println("nic is not null.>:"+nic.getName());
                    System.out.println("nic is not null.>:"+nic.getDisplayName());
                }
            }
            if(java.lang.System.getProperty("develop_prop.debug")!=null){ 
                System.out.println("m_endpoint>: "+m_endpoint);
            }
        }
        catch(Exception ex){
            if(java.lang.System.getProperty("develop_prop.debug")!=null){ 
                System.out.println("Caught exceptionet.>: "+ex);
            }
        }

/*
        try{
            java.util.Enumeration<java.net.NetworkInterface> nic 
                 = java.net.NetworkInterface.getNetworkInterfaces();
            while(nic.hasMoreElements()) {
                java.net.NetworkInterface netIf = nic.nextElement();
                java.util.Enumeration<java.net.InetAddress> enumAddress 
                        = netIf.getInetAddresses();
                while(enumAddress.hasMoreElements()){
                    java.net.InetAddress inetAdd 
                        = enumAddress.nextElement();
                    String hostString = inetAdd.getHostAddress();
                    if(isIpAddressFormat(hostString)){
                        if(m_endpoint.length()!=0){
                            m_endpoint 
                                = m_endpoint + "," + hostString + ":";
                        }
                        else{
                            m_endpoint = hostString + ":";
                        }
                    }
                }
            }
        }
        catch(Exception ex){
        }
*/




//        if (coil::dest_to_endpoint(host_port[0], m_endpoint)) {
        if (m_endpoint != null) {
            rtcout.println(rtcout.INFO, 
                "Endpoint for the CORBA naming service ("
                 +host_port[0]+") is "+m_endpoint);
        }
        else {
            rtcout.println(rtcout.WARN, 
                "No endpoint for the CORBA naming service ("
                 +host_port[0]+") was found.");
        }
        if(java.lang.System.getProperty("develop_prop.debug")!=null){ 
            System.out.println("OUT NamingOnCorba");
        }
    }
    
    /**
     * {@.en Checks that the string is IPaddress. }
     */
    private boolean isIpAddressFormat(String string){
        java.util.regex.Pattern pattern 
            = java.util.regex.Pattern.compile(
               "(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})");
        java.util.regex.Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    /**
     * {@.ja 指定したオブジェクトのNamingServiceへバインド}
     * {@.en Bind the specified objects to NamingService}
     *
     * <p> 
     * {@.ja 指定したオブジェクトを指定した名称で CORBA NamingService へバイ
     * ンドする。}
     * {@.en Bind the specified objects to CORBA NamingService 
     * by specified names.}
     * </p>
     * 
     * @param name 
     *   {@.ja バインド時の名称}
     *   {@.en Names at the binding}
     * @param rtobj 
     *   {@.ja バインド対象オブジェクト}
     *   {@.en The target objects for the binding}
     *
     */
    public void bindObject(final String name, final RTObject_impl rtobj) {
        try{
            m_cosnaming.rebindByString(name, rtobj.getObjRef(), true);
        } catch ( Exception ex ) {
        }
    }

    /**
     * <p> bindObject </p>
     *
     * @param name bind時の名称
     * @param mgr bind対象マネージャサーバント
     *
     */
    public void bindObject(final String name, final ManagerServant mgr) {
        try{
            m_cosnaming.rebindByString(name, mgr.getObjRef(), true);
        } catch ( Exception ex ) {
        }
    }

    /**
     * {@.ja 指定した CORBA オブジェクトをNamingServiceからアンバインド}
     * {@.en Unbind the specified CORBA objects from NamingService}
     *
     * <p> 
     * {@.ja 指定した CORBA オブジェクトを CORBA NamingService から
     * アンバインドする。}
     * {@.en Unbind the specified CORBA objects from CORBA NamingService.}
     * 
     * @param name 
     *   {@.ja アンバインド対象オブジェクト}
     *   {@.en The target objects for the unbinding}
     *
     *
     */
    public void unbindObject(final String name) {
        rtcout.println(rtcout.INFO, "unbindObject(name  = " +name+")");
        try {
            m_cosnaming.unbind(name);
        } catch (Exception ex) {
        }
    }

    /**
     * {@.ja ネームサーバの生存を確認する。}
     * {@.en Check if the name service is alive}
     * 
     * @return 
     *   {@.ja true:生存している, false:生存していない}
     *   {@.en rue: alive, false:non not alive}
     *
     * 
     */
    public boolean isAlive() {
        rtcout.println(rtcout.TRACE, "isAlive()");
        return m_cosnaming.isAlive();
    }

    private CorbaNaming m_cosnaming;
    /**
     * {@.ja Logging用フォーマットオブジェクト}
     */
    protected Logbuf rtcout;
    private boolean m_replaceEndpoint;
    private String m_endpoint;
}
