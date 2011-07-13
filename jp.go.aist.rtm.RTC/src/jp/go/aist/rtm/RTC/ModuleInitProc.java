package jp.go.aist.rtm.RTC;

/**
 * {@.ja Managerが初期化されてアクティブ化された後に呼び出される、
 * 初期化プロシジャコールバックインタフェース}
 * {@.en This interface is an initialization procedure callback interface 
 * called after Manager is initialized and made active.}
 */
public interface ModuleInitProc {
    
    /**
     * {@.ja Managerが初期化されてアクティブ化された後に呼び出される}
     * {@.en After Manager is initialized and made active, 
     * this method is called.}
     * 
     * @param mgr 
     *   {@.ja Managerオブジェクト}
     *   {@.en Manager object}
     */
    public void myModuleInit(Manager mgr);

}
