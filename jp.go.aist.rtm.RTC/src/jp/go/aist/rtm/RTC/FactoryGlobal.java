package jp.go.aist.rtm.RTC;

import java.util.Iterator;
import java.util.HashMap;
import java.util.Set;

import jp.go.aist.rtm.RTC.ObjectCreator;
import jp.go.aist.rtm.RTC.ObjectDestructor;
import jp.go.aist.rtm.RTC.util.Properties;

/**
 * <p>Java用コンポーネントファクトリの実装です。</p>
 */
public class FactoryGlobal<ABSTRACTCLASS,IDENTIFIER> {

    /**
     * <p> FactoryGlobal constructor </p>
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
     * <p> instance </p>
     *
     * @return FactoryGlobal object
     */
    public static FactoryGlobal instance() {
        if (factory_global == null) {
            synchronized (factory_global_mutex) {
                if (factory_global == null) {
                    try {
                        factory_global = new FactoryGlobal();
                    } catch (Exception e) {
                        factory_global = null;
                    }
                }
            }
        }

        return factory_global;
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
     * <p>コンポーネント生成用インタフェース</p>
     */
    protected RtcNewFunc m_New;
    /**
     * <p>コンポーネント破棄用インタフェース</p>
     */
    protected RtcDeleteFunc m_Delete;
    /**
     * <p>コンポーネント生成時のナンバーリング・ポリシ(命名ポリシー)管理用クラス</p>
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
     *  <p> Map of FactoryEntry </p>
     */
    protected HashMap<IDENTIFIER, FactoryEntry> m_creators = new HashMap<IDENTIFIER, FactoryEntry>();

    /**
     * <p> hasFactory </p>
     *
     * @param id
     * @return boolean
     */
    public boolean hasFactory(final IDENTIFIER id) {
        return m_creators.containsKey(id);
    }
    /**
     * <p> getIdentifiers </p>
     *
     * @return Set<IDENTIFIER>
     */
    public Set<IDENTIFIER> getIdentifiers()
    {
        return m_creators.keySet();
    }
    /**
     * <p> addFactory </p>
     *
     * @param id         
     * @param creator    creation function
     * @param destructor destruction function
     * @return The return code of ReturnCode type. 
     */
    public ReturnCode addFactory(final IDENTIFIER id,
                          ObjectCreator creator,
                          ObjectDestructor destructor)
    {
        if (m_creators.containsKey(id)){
            return ReturnCode.ALREADY_EXISTS;
        }
        FactoryEntry f = new FactoryEntry(creator, destructor);
        m_creators.put(id,f);
        return ReturnCode.FACTORY_OK;
    }
    /**
     * <p> removeFactory </p>
     *
     * @param id         
     * @return The return code of ReturnCode type. 
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
     * <p> createObject </p>
     *
     * @param id         
     * @return Created object. 
     */
    public ABSTRACTCLASS createObject(final IDENTIFIER id)
    {
        if (!m_creators.containsKey(id)){
            return null;
        }
        return m_creators.get(id).creator_.creator_();
    }
    /**
     * <p> deleteObject </p>
     *
     * @param id         
     * @param obj    
     * 
     */
    public void deleteObject(final IDENTIFIER id, ABSTRACTCLASS obj) {
        if (!m_creators.containsKey(id)){
            return ;
        }
        m_creators.get(id).destructor_.destructor_(obj);
    }
    /**
     * <p> deleteObject </p>
     *
     * @param obj
     */
    public void deleteObject(ABSTRACTCLASS obj) {
        Iterator it = m_creators.keySet().iterator();

        while (it.hasNext()) {
            m_creators.get(it.next()).destructor_.destructor_(obj);
        }
    }

    class FactoryEntry
    {
      public FactoryEntry()
      {
      }
      public FactoryEntry(ObjectCreator creator, ObjectDestructor destructor)
      {
          creator_ = creator;
          destructor_ = destructor;
      }
      public ObjectCreator<ABSTRACTCLASS> creator_;
      public ObjectDestructor destructor_;
    };
   public enum ReturnCode
      {
        FACTORY_OK,
        FACTORY_ERROR,
        ALREADY_EXISTS,
        NOT_FOUND,
        INVALID_ARG,
        UNKNOWN_ERROR
      };

}

