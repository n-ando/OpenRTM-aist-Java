package jp.go.aist.rtm.RTC;
/**
 * {@.ja オブジェクト生成用インターフェース}
 * {@.en Interface for object generation}
 */
public interface ObjectCreator<ABSTRACTCLASS>{
    
    /**
     * {@.ja インスタンスを生成する。}
     * {@.en Generates the instance.}
     * 
     * @return 
     *   {@.ja 生成したインスタンス}
     *   {@.en Object Created instances}
     *
     */
    public ABSTRACTCLASS creator_();

}


