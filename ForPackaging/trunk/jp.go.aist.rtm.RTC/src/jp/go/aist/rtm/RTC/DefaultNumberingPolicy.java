package jp.go.aist.rtm.RTC;

import java.util.Vector;

/**
 * {@.ja オブジェクト生成時ネーミング・ポリシー(命名規則)管理用クラス}
 * {@.en Class for naming policy management when creating objects}
 *
 * <p>
 * {@.ja オブジェクトを生成する際のネーミング・ポリシー(命名規則)を
 * 管理するためのクラス。}
 * {@.en This is a class to manage the naming policy when creating objects.}
 */
class DefaultNumberingPolicy implements NumberingPolicy {

  /**
   * {@.ja コンストラクタ}
   * {@.en Constructor}
   */
    public DefaultNumberingPolicy() {
        m_num = 0;
    }

  /**
   * {@.ja オブジェクト生成時の名称作成。}
   * {@.en Create the name when creating object}
   *
   * <p>
   * {@.ja オブジェクト生成時の名称を生成する。
   * 生成済みインスタンスの数に応じた名称を生成する。}
   * {@.en Create the name when creating object.
   * Create the name corresponding to the number of generated instances.}
   * 
   * @param obj 
   *   {@.ja 名称生成対象オブジェクト}
   *   {@.en The target object for the name creation}
   *
   * @return 
   *   {@.ja 生成したオブジェクト名称}
   *   {@.en Names of the created object}
   *
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
   * {@.ja オブジェクト削除時の名称解放。}
   * {@.en Delete the name when deleting object}
   *
   * <p>
   * {@.ja オブジェクト削除時に名称を解放する。
   * オブジェクト削除時に生成済みインスタンス数を減算する。}
   * {@.en Delete the name when deleting object.
   * Substract the generated number of instances when deleting the object.}
   * 
   * @param obj 
   *   {@.ja 名称解放対象オブジェクト}
   *   {@.en The target object for the name delete}
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
     */
  /**
   * {@.ja オブジェクトの検索}
   * {@.en Find the object}
   *
   * <p>
   * {@.ja オブジェクトリストから指定されたオブジェクトを検索し、
   * 該当するオブジェクトが格納されている場合にはインデックスを返す。}
   * {@.en Find the specified object in the object list and return its index
   * when it is stored.}
   * 
   * @param obj 
   *   {@.ja 検索対象オブジェクト}
   *   {@.en The target object for the find}
   *
   * @return 
   *   {@.ja オブジェクト格納インデックス}
   *   {@.en Object index for storage}
   *
   * @exception Exception 
   *   {@.ja 検索対象オブジェクトが存在しない。}
   *   {@.en The object doesn't exist.}
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
