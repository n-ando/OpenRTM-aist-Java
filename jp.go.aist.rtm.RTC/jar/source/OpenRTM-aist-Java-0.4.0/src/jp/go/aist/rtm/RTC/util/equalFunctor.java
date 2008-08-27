package jp.go.aist.rtm.RTC.util;

/**
 * <p>条件合致判定に使用する汎用的なファンクタインタフェースです。</p>
 */
public interface equalFunctor {
    
    /**
     * <p>条件に合致するかどうか判定します。</p>
     * 
     * @param object 判定対象のオブジェクト
     * @return 合致する場合はtrueを、さもなくばfalseを返します。
     */
    public boolean equalof(final java.lang.Object object);
}
