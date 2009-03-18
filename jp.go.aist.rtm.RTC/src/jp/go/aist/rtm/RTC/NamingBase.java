package jp.go.aist.rtm.RTC;

/**
 * <p>Naming Service管理用インターフェースです。</p>
 */
public interface NamingBase {
    /**
     * <p>オブジェクトをNameServerにbindします。</p>
     * 
     * @param name bind時の名称
     * @param rtobj bind対象オブジェクト
     */
    public void bindObject(final String name, final RTObject_impl rtobj);
    /**
     * <p>  </p>
     *
     * @param name
     * @param mgr
     */
    public void bindObject(final String name, final ManagerServant mgr);
    /**
     * <p>オブジェクトをNameServerからunbindします。</p>
     * 
     * @param name unbind対象オブジェクト名
     */
    public void unbindObject(final String name);
}
