package jp.go.aist.rtm.RTC;

import RTC.RTObject;

import java.util.Vector;

import java.lang.Integer;
/**
 * {@.ja オブジェクト生成時ネーミング・ポリシー(命名規則)管理用クラス.}
 * {@.en This class manages a naming policy.}
 *
 * <p>
 * {@.ja ネーミングサービスからRTCを検索してナンバリングを行うi}
 * {@.en This class searches for RTC from naming service and does a numbering.}
 */
class NamingServiceNumberingPolicy implements NumberingPolicy, ObjectCreator<NumberingPolicy>, ObjectDestructor {
    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     * <p>
     *
     */
    public NamingServiceNumberingPolicy() {
        m_num = 0;
        m_mgr = Manager.instance();
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
        int num = 0;
        while(true) {
            String num_str = Integer.toString(num);
            String name = obj.getTypeName() + num_str;
            boolean pos = this.find(name);
            if(pos){
                return num_str;
            }
            num = num + 1;

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
    }
    /**
     * {@.ja オブジェクトの検索}
     * {@.en Find the object}
     *
     * <p>
     * {@.ja オブジェクトリストから指定されたオブジェクトを検索し、
     * 該当するオブジェクトが格納されている場合にはTrueを返す。}
     * {@.en Find the specified object in the object list and return true }
     * 
     * @param name 
     *   {@.ja 検索対象オブジェクトの名前}
     *   {@.en The target object name for the find}
     *
     * @return 
     *   {@.ja true:The object exist.}
     *   {@.en true:The object exist.}
     *
     */
    protected boolean find(String name) {
        //String rtc_name = "rtcloc://*/*/";
        String rtc_name = "rtcname://*/*/";
        rtc_name = rtc_name + name;
        RTObject[] rtcs = m_mgr.getNaming().string_to_component(rtc_name);

        if(rtcs.length>0){
            return true;
        }
        else{
            return false;
        } 
    }
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
    /**
     * {@.ja NamingServiceNumberingPolicy を生成する}
     * {@.en Creats NamingServiceNumberingPolicy}
     * 
     * @return 
     *   {@.ja 生成されたNumberingPolicy}
     *   {@.en Object Created instances}
     *
     *
     */
    public NumberingPolicy creator_() {
        return new NamingServiceNumberingPolicy();
    }
    /**
     * {@.ja Object を破棄する}
     * {@.en Destructs Object}
     * 
     * @param obj
     *   {@.ja 破棄するインタスタンス}
     *   {@.en The target instances for destruction}
     *
     */
    public void destructor_(Object obj) {
        obj = null;
    }
    /**
     * {@.ja モジュール初期化関数}
     * {@.en Module initialization}
     * <p>
     * {@.ja DefaultNumberingPolicy のファクトリを登録する初期化関数。}
     * {@.en This initialization function registers DefaultNumberingPolicy's 
     * factory.}
     */
    public static void NamingServiceNumberingPolicyInit() {
        final NumberingPolicyFactory<NamingServiceNumberingPolicy,String> 
            factory 
                = NumberingPolicyFactory.instance();

        factory.addFactory("ns_unique",
                    new NamingServiceNumberingPolicy(),
                    new NamingServiceNumberingPolicy());
    
    }

    private Manager m_mgr;
    private int m_num;
    private Vector m_objects = new Vector();
}

