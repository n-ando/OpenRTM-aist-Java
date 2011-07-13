package jp.go.aist.rtm.RTC.util;

/**
 * {@.ja CORBA_SeqUtilクラスと合わせて使用するヘルパインタフェース}
 * {@.en Helper interface used together with CORBA_SeqUtil class}
 */
public interface operatorFunc {
    
    /**
     * {@.ja 対象のオブジェクトに対して操作を行う}
     * {@.en Operates the object}
     *
     * @param elem 
     *   {@.ja シーケンス内の要素オブジェクト}
     *   {@.en Element object in sequence}
     */
    public void operator(Object elem);
}
