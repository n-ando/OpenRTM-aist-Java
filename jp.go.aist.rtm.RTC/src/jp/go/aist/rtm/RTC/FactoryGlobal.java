package jp.go.aist.rtm.RTC;

import java.lang.reflect.Constructor;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


/**
 * {@.ja ファクトリ管理クラス}
 * {@.en Factory Management class}
 */
public class FactoryGlobal<ABSTRACTCLASS,IDENTIFIER> {

    /**
     * {@.ja コンストラクタ}
     * {@.en FactoryGlobal constructor}
     */
    protected FactoryGlobal() {
        m_New = null;
        m_Delete = null;
        m_policy = null;
    }
    /**
     * <p>コンストラクタです。
     * 指定されたプロファイル、生成用メソッドおよび破棄メソッドを引数に取り、
     * コンポーネントの生成、破棄を管理します。</p>
     * 
     * @param profile コンポーネントのプロファイル
     * @param newFunc コンポーネント生成用メソッド
     * @param deleteFunc コンポーネント破棄用メソッド
     */
/*
    public FactoryGlobal(final Properties profile, RtcNewFunc newFunc,
            RtcDeleteFunc deleteFunc) {
        this(profile, newFunc, deleteFunc, new DefaultNumberingPolicy());
    }
*/
    
    /**
     * <p>コンストラクタです。
     * 指定されたプロファイル、生成用メソッド、破棄メソッドおよび
     * 生成したインスタンスのナンバーリング・ポリシ(命名ポリシー)を引数に取り、
     * コンポーネントの生成、破棄を管理します。</p>
     * 
     * @param profile コンポーネントのプロファイル
     * @param newFunc コンポーネント生成用メソッド
     * @param deleteFunc コンポーネント破棄用メソッド
     * @param policy 生成したインスタンスのナンバーリング・ポリシ(命名ポリシー)
     */
/*
    public FactoryGlobal(final Properties profile, RtcNewFunc newFunc,
            RtcDeleteFunc deleteFunc, NumberingPolicy policy) {
        super(profile);
        m_New = newFunc;
        m_Delete = deleteFunc;
        m_policy = policy;
    }
*/
    
    /**
     * {@.ja FactoryGlobalをインスタス化する。}
     * {@.en Creates FactoryGlobal.}
     *
     * @return 
     *   {@.ja FactoryGlobal オブジェクト}
     *   {@.en FactoryGlobal object}
     */
    synchronized public static FactoryGlobal instance() {
        if (factory_global == null) {
            try {
                factory_global = new FactoryGlobal();
            } catch (Exception e) {
                factory_global = null;
            }
        }

        return factory_global;
    }

    /**
     * {@.ja FactoryGlobalをインスタス化する。}
     * {@.en Creates FactoryGlobal.}
     * @param clazz
     *   {@.ja class name}
     *   {@.en class name}
     * @return 
     *   {@.ja オブジェクト}
     *   {@.en object}
     */
    public static Object instance(String clazz) {
        Object obj = (Object)factory_table.get(clazz);
        if(obj != null){
            return obj;
        } 
        else {
            try{
                Class cl  = Class.forName(clazz);
                Constructor cunstructor = cl.getDeclaredConstructor(null);
                cunstructor.setAccessible(true);
                Object obj2 = (Object)cunstructor.newInstance();
                //Object obj2 = (Object)(Class.forName(clazz).newInstance());
                factory_table.put(clazz,obj2);
                return obj2;
            }
            catch(Exception e){
                return null;
            }
        }
    }
    /**
     * <p>コンポーネントを生成します。</p>
     * 
     * @param mgr Managerオブジェクト
     * @return 生成されたコンポーネントのインスタンス
     */
/*
    public RTObject_impl create(Manager mgr) {
        try {
            RTObject_impl rtobj = m_New.createRtc(mgr);
            if (rtobj == null) {
                return null;
            }
            // mgr.m_objManager.activate(rtobj);

            ++m_Number;
            rtobj.setProperties(this.profile());

            // create instance_name
            StringBuffer instance_name = new StringBuffer(rtobj.getTypeName());
            instance_name.append(m_policy.onCreate(rtobj));
            rtobj.setInstanceName(instance_name.toString());

            return rtobj;
            
        } catch (Exception ex) {
            return null;
        }
    }
*/
    /**
     * <p>コンポーネントを破棄します。</p>
     * 
     * @param comp 破棄対象コンポーネントのインスタンス
     */
/*
    public RTObject_impl destroy(RTObject_impl comp) {
        --m_Number;
        m_policy.onDelete(comp);
        m_Delete.deleteRtc(comp);
        return comp;
    }
*/
        
    /**
     * {@.ja コンポーネント生成インタフェース格納用変数}
     * {@.en Interface for component creation variable}
     */
    protected RtcNewFunc m_New;
    /**
     * {@.ja コンポーネント破棄インタフェース格納用変数}
     * {@.en Interface for component destruction variable}
     */
    protected RtcDeleteFunc m_Delete;
    /**
     * {@.ja コンポーネント生成時のナンバーリング・ポリシ(命名ポリシー)管理用
     * クラス}
     * {@.en Class for numbering policy (naming policy) management.}
     */
    protected NumberingPolicy m_policy;
    /**
     *  <p> mutex </p>
     */
    private static String factory_global_mutex = new String();
    /**
     *  <p> object </p>
     */
    private static FactoryGlobal factory_global;
    /**
     *  {@.ja FactoryEntry のマップ}
     *  {@.en Map of FactoryEntry}
     */
    protected HashMap<IDENTIFIER, FactoryEntry> m_creators = new HashMap<IDENTIFIER, FactoryEntry>();
    private HashMap<ABSTRACTCLASS, FactoryEntry> m_objects = new HashMap<ABSTRACTCLASS, FactoryEntry>();

    /**
     * {@.ja Identifierがマップに存在するかチェックする。}
     * {@.en Checks whether Identifier exists in the map.}
     *
     * @param id
     *   {@.ja Identifier}
     *   {@.en Identifier}
     * @return boolean
     *   {@.ja 指定のIDを保持してる場合に true}
     */
    public boolean hasFactory(final IDENTIFIER id) {
        return m_creators.containsKey(id);
    }
    /**
     * {@.ja マップの Identifiers を返す}
     * {@.en Returns Identifiers of the map.}
     *
     * @return 
     *   {@.ja Identifiers}
     *   {@.en Identifiers}
     */
    public Set<IDENTIFIER> getIdentifiers()
    {
        return m_creators.keySet();
    }
    /**
     * {@.ja Factory をマップへ登録する。}
     * {@.en Resters Factory to the map.}
     *
     * @param id
     *   {@.en Identifier}
     * @param creator    
     *   {@.en creation function}
     * @param destructor 
     *   {@.en destruction function}
     * @return 
     *   {@.en The return code of ReturnCode type.}
     */
    public ReturnCode addFactory(final IDENTIFIER id,
                          ObjectCreator creator,
                          ObjectDestructor destructor)
    {
        if (m_creators.containsKey(id)){
            return ReturnCode.ALREADY_EXISTS;
        }
        FactoryEntry f = new FactoryEntry(id, creator, destructor);
        m_creators.put(id,f);
        return ReturnCode.FACTORY_OK;
    }
    /**
     * {@.ja マップからFactoryを削除する。}
     * {@.en Removes Factory from the map.}
     *
     * @param id         
     *   {@.ja 削除するIdentifier}
     *   {@.en Identifier}
     * @return 
     *   {@.ja リターンコード}
     *   {@.en The return code of ReturnCode type.}
     * 
     */
    public ReturnCode removeFactory(final IDENTIFIER id)
    {
        if (!m_creators.containsKey(id)){
            return ReturnCode.NOT_FOUND;
        }
        m_creators.remove(id);
        return ReturnCode.FACTORY_OK;
    }
    /**
     * {@.ja オブジェクト生成。}
     * {@.en Object generation processing}
     *
     * <p>
     * {@.ja Identifierで指定されたFactoryでObjectを生成する。}
     * {@.en This method creates the object with factory specified 
     * with identifier.}
     *
     * @param id
     *   {@.ja 生成するオブジェクトのIdentifier}
     *   {@.en Identifier of created object}
     * @return 
     *   {@.ja 生成したオブジェクト}
     *   {@.en Created object.}
     */
    public ABSTRACTCLASS createObject(final IDENTIFIER id)
    {
        if (!m_creators.containsKey(id)){
            return null;
        }
        ABSTRACTCLASS obj = m_creators.get(id).creator_.creator_();
        m_objects.put(obj, m_creators.get(id));
        return obj;
    }
    /**
     * {@.ja オブジェクト削除。}
     * {@.en Deletes the object.}
     *
     * <p>
     * {@.ja Identifierで指定されたFactoryでObjectを削除する。}
     * {@.en This method deletes the object with factory specified 
     * with identifier.}
     * @param id         
     *   {@.ja 削除するオブジェクトのIdentifier}
     *   {@.en Identifier of deleted object}
     * @param obj    
     *   {@.ja 削除するオブジェクト}
     *   {@.en Deleteed object.}
     * 
     */
    public ReturnCode deleteObject(final IDENTIFIER id, ABSTRACTCLASS obj) {
        if (!m_creators.containsKey(id)){
            return deleteObject(obj);
        }
        m_creators.get(id).destructor_.destructor_(obj);
        m_objects.remove(obj);
        return ReturnCode.FACTORY_OK;
    }
    /**
     * {@.ja オブジェクト削除。}
     * {@.en Deletes the object.}
     *
     * {@.ja 指定したObjectを削除する。}
     * {@.en This method deletes specified Object.}
     * with identifier.}
     *
     * @param obj    
     *   {@.ja 削除するオブジェクト}
     *   {@.en Deleteed object.}
     */
    public ReturnCode deleteObject(ABSTRACTCLASS obj) {
        if(!m_objects.containsKey(obj)) {
            return ReturnCode.NOT_FOUND; 
        }
        m_objects.get(obj).destructor_.destructor_(obj);
        m_objects.remove(obj);

        return ReturnCode.FACTORY_OK;
    }

    /**
     * {@.ja 生成済みオブジェクトリストの取得}
     * {@.en Getting created objects}
     * <p>
     * {@.ja このファクトリで生成されたオブジェクトのリストを取得する。}
     * {@.en This operation returns a list of created objects by the factory.}
     *
     * @return 
     *   {@.ja 生成済みオブジェクトリスト}
     *   {@.en created object list}
     *
     */
    public ArrayList<ABSTRACTCLASS> createdObjects() {
        ArrayList<ABSTRACTCLASS> objects = new ArrayList<ABSTRACTCLASS>();
        Iterator it = m_objects.keySet().iterator();
        while (it.hasNext()) {
            objects.add((ABSTRACTCLASS)it.next());
        }
        return objects;
    }

    /**
     * {@.ja オブジェクトがこのファクトリの生成物かどうか調べる}
     * {@.en Whether a object is a product of this factory}
     * <p>
     * @param obj 
     *   {@.ja 対象オブジェクト}
     *   {@.en A target object}
     * @return 
     *   {@.ja true: このファクトリの生成物
     *         false: このファクトリの生成物ではない}
     *   {@.en true: The object is a product of the factory
     *         false: The object is not a product of the factory}
     *
     */
    public boolean isProducerOf(ABSTRACTCLASS obj) {
        return m_objects.containsKey(obj);
    }

    /**
     * {@.ja オブジェクトからクラス識別子(ID)を取得する}
     * {@.en Getting class identifier (ID) from a object}
     * <p>
     * {@.ja 当該オブジェクトのクラス識別子(ID)を取得する。}
     * {@.en This operation returns a class identifier (ID) from a object.}
     *
     * @param obj [in] 
     *   {@.ja クラス識別子(ID)を取得したいオブジェクト}
     *   {@.en An object to investigate its class ID.}
     * @param id [out] 
     *   {@.ja クラス識別子(ID)}
     *   {@.en Class identifier (ID)}
     * @return 
     *   {@.ja リターンコード NOT_FOUND: 識別子が存在しない
     *                        FACTORY_OK: 正常終了}
     *   {@.en Return code NOT_FOUND: ID not found
     *                        FACTORY_OK: normal return}
     */
    public ReturnCode objectToIdentifier(ABSTRACTCLASS obj, IDENTIFIER id) {
        if (!m_objects.containsKey(obj)) { 
            return ReturnCode.NOT_FOUND; 
        }
        id = m_objects.get(obj).id_;
        return ReturnCode.FACTORY_OK;
    }

    /**
     * {@.ja オブジェクトのコンストラクタを取得する}
     * {@.en Getting destructor of the object}
     * <p>
     * {@.ja このファクトリで生成されたオブジェクトのコンストラクタを取得する。
     * obj はこのファクトリで生成されたものでなければならない。予め
     * isProducerOf() 関数で当該オブジェクトがこのファクトリの生成物で
     * あるかどうかをチェックしなければならない。}
     * {@.en This operation returns a constructor of the object created by
     * the factory.  obj must be a product of the factory.  User must
     * check if the object is a product of the factory by using
     * isProducerOf()-function, before using this function.}
     *
     * @return 
     *   {@.ja オブジェクトのデストラクタ}
     *   {@.en destructor of the object}
     *
     */
    public ObjectCreator objectToCreator(ABSTRACTCLASS obj) {
        return m_objects.get(obj).creator_;
    }

    /**
     * {@.ja オブジェクトのデストラクタを取得する}
     * {@.en Getting destructor of the object}
     * <p>
     * {@.ja このファクトリで生成されたオブジェクトのデストラクタを取得する。
     * obj はこのファクトリで生成されたものでなければならない。予め
     * isProducerOf() 関数で当該オブジェクトがこのファクトリの生成物で
     * あるかどうかをチェックしなければならない。}
     * {@.en This operation returns a destructor of the object created by
     * the factory.  obj must be a product of the factory.  User must
     * check if the object is a product of the factory by using
     * isProducerOf()-function, before using this function.}
     *
     * @return 
     *   {@.ja オブジェクトのデストラクタ}
     *   {@.en destructor of the object}
     *
     */
    public ObjectDestructor objectToDestructor(ABSTRACTCLASS obj) {
        return m_objects.get(obj).destructor_;
    }

    /**
     * {@.ja 生成/削除インターフェース管理用クラス}
     * {@.en Class for creation/destruction interface management}
     */
    class FactoryEntry {
        /**
         * {@.ja コンストラクタ}
         * {@.en Constructor}
         */
        public FactoryEntry() {
        }
        /**
         * {@.ja コンストラクタ}
         * {@.en Constructor}
         * @param creator
         *   {@.ja 生成インターフェース}    
         *   {@.en Creation interface}    
         * @param destructor    
         *   {@.ja 削除インターフェース}    
         *   {@.en Destruction interface}    
         */
        public FactoryEntry(IDENTIFIER id,
                            ObjectCreator creator, 
                            ObjectDestructor destructor) {
          id_ = id;
          creator_ = creator;
          destructor_ = destructor;
        }
        /**
         * {@.ja 生成インターフェース保存用変数}
         * {@.en Creation interface variable}
         */
        public ObjectCreator<ABSTRACTCLASS> creator_;
        /**
         * {@.ja 削除インターフェース保存用変数}
         * {@.en Destruction interface variable}
         */
        public ObjectDestructor destructor_;
        /**
         * {@.ja }
         * {@.en }
         */
        public IDENTIFIER id_;
    };
    /**
     * {@.ja リターンコード}
     * {@.en Return code}
     */
    public enum ReturnCode
      {
        FACTORY_OK,
        FACTORY_ERROR,
        ALREADY_EXISTS,
        NOT_FOUND,
        INVALID_ARG,
        UNKNOWN_ERROR
      };

    private static Hashtable factory_table = new Hashtable();

}

