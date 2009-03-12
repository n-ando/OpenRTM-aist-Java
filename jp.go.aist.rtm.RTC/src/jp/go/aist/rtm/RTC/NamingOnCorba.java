package jp.go.aist.rtm.RTC;

import org.omg.CORBA.ORB;

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
    }
    
    /**
     * <p>オブジェクトをNameServerにbindします。</p>
     * 
     * @param name bind時の名称
     * @param rtobj bind対象オブジェクト
     */
    public void bindObject(final String name, final RTObject_impl rtobj) {
        try{
            m_cosnaming.rebindByString(name, rtobj.getObjRef(), true);
        } catch ( Exception ex ) {
        }
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
}
