package jp.go.aist.rtm.RTC;

/**
 * <p>Managerが初期化されてアクティブ化された後に呼び出される、
 * 初期化プロシジャコールバックインタフェースです。</p>
 */
public interface ModuleInitProc {
    
    /**
     * <p>Managerが初期化されてアクティブ化された後に呼び出されます。</p>
     * 
     * @param mgr Managerオブジェクト
     */
    public void myModuleInit(Manager mgr);

}
