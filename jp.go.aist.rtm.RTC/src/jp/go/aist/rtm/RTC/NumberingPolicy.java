package jp.go.aist.rtm.RTC;

/**
* <p>オブジェクト生成時のナンバーリング・ポリシー(命名規則)管理用インターフェースです。</p>
*/
public interface NumberingPolicy {
    /**
     * <p>オブジェクト生成時に対象オブジェクトの名称を決定します。</p>
     * 
     * @param obj 命名対象オブジェクト
     * 
     * @return オブジェクト名
     */
    public String onCreate(RTObject_impl obj);
    /**
     * <p>削除対象のオブジェクト名を解除します。</p>
     * 
     * @param obj 削除対象オブジェクト
     */
    public void onDelete(RTObject_impl obj);
}
