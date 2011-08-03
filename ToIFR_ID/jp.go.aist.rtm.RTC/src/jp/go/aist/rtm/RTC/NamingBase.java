package jp.go.aist.rtm.RTC;

  /**
   * {@.ja Naming Service管理用インターフェース。}
   * {@.en NamingService management interface}
   *
   * <p>
   * {@.ja NamingServer 管理用抽象インターフェースクラス。
   * 具象管理クラスは、以下の純粋仮想関数の実装を提供しなければならない。
   * <ul>
   * <li> bindObject() : 指定したオブジェクトのNamingServiceへのバインド
   * <li> unbindObject() : 指定したオブジェクトのNamingServiceからのアンバインド
   * <li> isAlive() : ネームサーバの生存を確認
   * </ul>}
   * {@.en This is the abstract interface class for NamingServer management.
   * Concrete management classes must implement the following pure virtual 
   * functions.
   * <ul>
   * <li> bindObject() : Bind the specified object to NamingService
   * <li> unbindObject() : Unbind the specified object from NamingService
   * <li> isAlive() : Check if the name service is alive
   * </ul>}
   *
   */
public interface NamingBase {
    /**
     * {@.ja 指定したオブジェクトをNamingServiceへバインドする純粋仮想関数}
     * {@.en Pure virtual function to bind the specified objects
     *        to the NamingService}
     * 
     * <p>
     * @param name 
     *   {@.ja バインド時の名称}
     *   {@.en The name to be bound to the NamingService}
     * @param rtobj 
     *   {@.ja バインド対象オブジェクト}
     *   {@.en The target objects to be bound to the NamingSerivce}
     */
    public void bindObject(final String name, final RTObject_impl rtobj);

    /**
     * {@.ja 指定したManagerServantをNamingServiceへバインドする純粋仮想関数}
     * {@.en Pure virtual function to bind the specified ManagerServants 
     *        to NamingService}
     *
     * @param name 
     *   {@.ja バインド時の名称}
     *   {@.en The name to be bound to the NamingService}
     * @param mgr 
     *   {@.ja バインド対象ManagerServant}
     *   {@.en The target objects to be bound to the NamingSerivce}
     */
    public void bindObject(final String name, final ManagerServant mgr);

    /**
     * {@.ja 指定したオブジェクトをNamingServiceからアンバインドするための
     *        純粋仮想関数}
     * {@.en Pure virtual function to unbind the specified objects from 
     *        NamingService}
     *
     * @param name 
     *   {@.ja アンバインド対象オブジェクト名}
     *   {@.en The name of the object released from NamingService}
     *
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
