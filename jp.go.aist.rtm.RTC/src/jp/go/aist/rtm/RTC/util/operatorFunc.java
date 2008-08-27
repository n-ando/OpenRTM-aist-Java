package jp.go.aist.rtm.RTC.util;

/**
 * <p>CORBA_SeqUtilクラスと合わせて使用するヘルパインタフェースです。</p>
 */
public interface operatorFunc {
    
    /**
     * <p>対象のオブジェクトに対して操作を行います。</p>
     *
     * @param elem シーケンス内の要素オブジェクト
     */
    public void operator(Object elem);
}
