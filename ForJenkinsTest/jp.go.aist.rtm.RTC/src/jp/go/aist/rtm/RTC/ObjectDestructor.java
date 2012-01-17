package jp.go.aist.rtm.RTC;
/**
 * {@.ja オブジェクト破棄用インターフェース}
 * {@.en Interface for object destruction}
 */
public interface ObjectDestructor {
    
    /**
     * {@.ja インスタンスを破棄する。}
     * {@.en Destroys the object.}
     * <p> destructor_ </p>
     * 
     * @param obj    
     *   {@.ja 破壊するインスタンス}
     *   {@.en The target instances for destruction}
     *
     */
    public void destructor_(Object obj);

}

