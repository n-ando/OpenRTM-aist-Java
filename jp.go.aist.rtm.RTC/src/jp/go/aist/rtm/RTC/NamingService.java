package jp.go.aist.rtm.RTC;


  /**
   * {@.ja NameServer 管理用構造体}
   * {@.en Structure for NameServer management}
   */
class NamingService {
    /**
     * {@.ja コンストラクタ。}
     * {@.en Constructor}
     * 
     * @param meth 
     *   {@.ja NamingServerタイプ}
     *   {@.en NamingServer type}
     * @param name 
     *   {@.ja NamingServer名称}
     *   {@.en NamingServer name}
     * @param naming 
     *   {@.ja NameServerオブジェクト}
     *   {@.en NamingServer object}
     *
     */
    public NamingService(final String meth, final String name, NamingBase naming) {
        method = meth;
        nsname = name;
        ns = naming;
    }


    /**
     * {@.ja NamingServerタイプ}
     * {@.en NamingServer type}
     */
    public String method;
    /**
     * {@.ja NamingServer名称}
     * {@.en NamingServer name}
     */
    public String  nsname;
    /**
     * {@.ja NameServerオブジェクト}
     * {@.en NameServer object}
     */
    public NamingBase ns;
}


