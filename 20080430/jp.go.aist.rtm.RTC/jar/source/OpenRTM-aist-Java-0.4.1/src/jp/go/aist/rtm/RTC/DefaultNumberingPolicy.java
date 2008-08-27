package jp.go.aist.rtm.RTC;

import java.util.Vector;

/**
* <p>オブジェクト生成時のデフォルト・ナンバーリング・ポリシー(命名規則)管理クラスです。</p>
*/
public class DefaultNumberingPolicy implements NumberingPolicy {

    /**
     * <p>デフォルト・コンストラクタです。</p>
     */
    public DefaultNumberingPolicy() {
        m_num = 0;
    }

    /**
     * <p>オブジェクト生成時に対象オブジェクトの名称を決定します。</p>
     * 
     * @param obj 命名対象オブジェクト
     * 
     * @return オブジェクト名
     */
    public String onCreate(RTObject_impl obj) {
        long pos;
        
        ++m_num;
        
        try {
            pos = this.find(null);
            m_objects.setElementAt(obj, (int)pos);
            return String.valueOf(pos);
        } catch (Exception ex) {
            m_objects.add(obj);
            return String.valueOf(m_objects.size()-1);
        }
    }

    /**
     * <p>削除対象のオブジェクト名を解除します。</p>
     * 
     * @param obj 削除対象オブジェクト
     */
    public void onDelete(RTObject_impl obj) {
        long pos = 0;
        try {
            pos = this.find(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        if( pos<m_objects.size() ) {
            m_objects.setElementAt(null, (int)pos);
        }
        --m_num;
    }

    /**
     * <p>オブジェクトを検索します。</p>
     * 
     * @param obj 検索対象オブジェクト
     * 
     * @return オブジェクト・インデックス
     * 
     * @exception Exception 検索対象オブジェクトが存在しない。
     */
    protected long find(Object obj) throws Exception {
        for(int intIdx=0;intIdx<m_objects.size();++intIdx) {
            if(m_objects.elementAt(intIdx) == obj) return intIdx;
        }
        throw new Exception("ObjectNotFound");
    }

    private int m_num;
    private Vector m_objects = new Vector();
}
