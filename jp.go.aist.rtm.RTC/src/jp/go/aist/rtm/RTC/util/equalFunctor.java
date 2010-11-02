package jp.go.aist.rtm.RTC.util;

/**
 * {@.ja 条件合致判定に使用する汎用的なファンクタインタフェース}
 * {@.en General interface used to judge condition agreement}
 */
public interface equalFunctor {
    
    /**
     * {@.ja 条件に合致するかどうか判定する}
     * {@.en Judges whether to agree contingent on the object.}
     * 
     * @param object 
     *   {@.ja 判定対象のオブジェクト}
     *   {@.en Object to be judged}
     * @return 
     *   {@.ja 合致する場合はtrueを、さもなくばfalseを返す。}
     *   {@.en The agreement is true.}
     */
    public boolean equalof(final java.lang.Object object);
}
