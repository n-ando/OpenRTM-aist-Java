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
     * <p>オブジェクトをNameServerにbindします。</p>
     *
     * @param name bind時の名称
     * @param mgr bind対象マネージャサーバント
     */
    public void bindObject(final String name, final ManagerServant mgr);

    /**
     * <p>オブジェクトをNameServerからunbindします。</p>
     * 
     * @param name unbind対象オブジェクト名
     */
    public void unbindObject(final String name);

    /**
     * {@.ja ネームサーバの生存を確認する。}
     * {@.en Check if the name service is alive}
     * 
     * @return 
     *   {@.ja true:生存している, false:生存していない}
     *   {@.en true: alive, false:non not alive}
     *
     */
    public boolean isAlive();
}
