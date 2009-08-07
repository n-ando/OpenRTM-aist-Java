package jp.go.aist.rtm.RTC;

import org.omg.CORBA.ORB;

//<+zxc
import jp.go.aist.rtm.RTC.util.StringUtil;
import jp.go.aist.rtm.RTC.log.Logbuf;
//+>
/**
* <p>CORBA用Naming Serviceクラスです。</p>
*/
class NamingOnCorba implements NamingBase {

    /**
     * <p>コンストラクタです。</p>
     * 
     * @param orb ORB
     * @param names Naming Server名称
     */
    public NamingOnCorba(ORB orb, final String names) {
        try {
            m_cosnaming = new CorbaNaming(orb, names);
        } catch (Exception e) {
            e.printStackTrace();
        }
//<+zxc
        Manager manager = Manager.instance();
        rtcout = new Logbuf("Manager.NamingOnCorba");
        rtcout.setLevel(manager.getConfig().getProperty("logger.log_level"));
        rtcout.setDateFormat(manager.getConfig().getProperty("logger.date_format"));
        rtcout.setLogLock(StringUtil.toBool(manager.getConfig().getProperty("logger.stream_lock"),
                   "enable", "disable", false));
//+>
    }
    
    /**
     * <p>オブジェクトをNameServerにbindします。</p>
     * 
     * @param name bind時の名称
     * @param rtobj bind対象オブジェクト
     */
    public void bindObject(final String name, final RTObject_impl rtobj) {
rtcout.println(rtcout.TRACE, "in  NamingOnCorba.bindObject(" + name + ")");//zxc
        try{
            m_cosnaming.rebindByString(name, rtobj.getObjRef(), true);
        } catch ( Exception ex ) {
rtcout.println(rtcout.TRACE, "    !!Exception");//zxc
        }
rtcout.println(rtcout.TRACE, "out NamingOnCorba.bindObject(" + name + ")");//zxc
    }

    /**
     * <p> bindObject </p>
     *
     * @param name String
     * @param mgr ManagerServant
     *
     */
    public void bindObject(final String name, final ManagerServant mgr) {
rtcout.println(rtcout.TRACE, "in  NamingOnCorba.bindObject(" + name + ")");//zxc
        try{
            m_cosnaming.rebindByString(name, mgr.getObjRef(), true);
        } catch ( Exception ex ) {
rtcout.println(rtcout.TRACE, "    !!Exception");//zxc
        }
rtcout.println(rtcout.TRACE, "out NamingOnCorba.bindObject(" + name + ")");//zxc
    }

    /**
     * <p>オブジェクトをNameServerからunbindします。</p>
     * 
     * @param name unbind対象オブジェクト名
     */
    public void unbindObject(final String name) {
        try {
            m_cosnaming.unbind(name);
        } catch (Exception ex) {
        }
    }

    private CorbaNaming m_cosnaming;
//<+zxc
    /**
     * <p>Logging用フォーマットオブジェクト</p>
     */
    protected Logbuf rtcout;
//+>
}
