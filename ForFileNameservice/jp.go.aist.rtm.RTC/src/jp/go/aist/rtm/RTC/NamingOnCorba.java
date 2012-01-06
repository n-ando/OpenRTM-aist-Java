package jp.go.aist.rtm.RTC;

import jp.go.aist.rtm.RTC.log.Logbuf;

import org.omg.CORBA.ORB;
  /**
   * {@.ja CORBA 用 NamingServer 管理クラス。}
   * {@.en NamingServer management class for CORBA}
   *
   * <p>
   * {@.ja CORBA 用 NamingServer 管理用クラス。
   * CORBA コンポーネントの NamingService への登録、解除などを管理する。}
   * {@.en NamingServer management class for CORBA.
   * Manage to register and unregister CORBA components to NamingService.}
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
        try {
            m_cosnaming = new CorbaNaming(orb, names);
        } catch (Exception e) {
            e.printStackTrace();
        }

        rtcout = new Logbuf("NamingOnCorba");

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
        rtcout.println(Logbuf.TRACE, "bindObject(" + name + ",rtobj)");
        try{
            m_cosnaming.rebindByString(name, rtobj.getObjRef(), true);
        } catch ( Exception ex ) {
        }
    }

    /**
     * {@.ja 指定したManagerServantをNamingServiceへバインド}
     * {@.en Bind the specified ManagerServants to NamingService}
     *
     * @param name 
     *   {@.ja バインド時の名称}
     *   {@.en Names at the binding}
     * @param mgr 
     *   {@.ja バインド対象ManagerServant}
     *   {@.en The target ManagerServants for the binding}
     */
    public void bindObject(final String name, final ManagerServant mgr) {
        rtcout.println(Logbuf.TRACE, "bindObject(" + name + ",mgr)");
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
        rtcout.println(Logbuf.TRACE, "unbindObject(name  = " +name+")");
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
        rtcout.println(Logbuf.TRACE, "isAlive()");
        return m_cosnaming.isAlive();
    }

    private CorbaNaming m_cosnaming;
    /**
     * {@.ja Logging用フォーマットオブジェクト}
     * {@.en Format object for Logging}
     */
    protected Logbuf rtcout;
}
