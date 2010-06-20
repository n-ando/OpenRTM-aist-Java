package jp.go.aist.rtm.RTC;

import java.util.Vector;

import jp.go.aist.rtm.RTC.util.equalFunctor;

/**
 * {@.ja オブジェクト管理クラス。}
 * {@.en Class for managing objects}
 *
 * <p>
 * {@.ja 各種オブジェクトを管理するためのクラス。}
 * {@.en This is a class for managing various objects.}
 *
 * @param <IDENTIFIER> 
 *   {@.ja オブジェクト識別子のデータ型を指定する}
 *   {@.en The data type of the object flag is specified.}
 * @param <TARGET> 
 *   {@.ja 管理対象となるオブジェクトの型を指定する}
 *   {@.en The type of the object to be managed is specified.}
 */
public class ObjectManager<IDENTIFIER, TARGET> {

    public ObjectManager() {
    }
    
    /**
     * {@.ja 指定したオブジェクトを登録。}
     * {@.en Register the specified object}
     * 
     * <p>
     * {@.ja 指定したオブジェクトを登録する。
     * 同一オブジェクトが登録済みの場合は、何も行わない。}
     * {@.en Register the object that was specified.
     * If the same object is already registered, this does not anything.}
     *
     * @param obj 
     *   {@.ja 登録対象オブジェクト}
     *   {@.en The target object for the registration}
     * @param equalFunc 
     *   {@.ja 同一判定に用いるオブジェクト}
     *   {@.en The Object used for the same judgment.}
     *
     * @return 
     *   {@.ja 登録処理結果(オブジェクトを登録した場合にtrue)}
     *   {@.en Registration result (True if object was registerd)}
     * 
     */
    public boolean registerObject(TARGET obj, equalFunctor equalFunc) {
        
        synchronized (m_objects) {
            
            for (int i = 0; i < m_objects.size(); i++) {
                if (equalFunc.equalof(m_objects.elementAt(i))) {
                    return false;
                }
            }
            
            m_objects.add(obj);
            
            return true;
        }
    }
    
    /**
     * {@.ja 指定したオブジェクトを登録解除する。}
     * {@.en Unregister the specified object}
     * 
     * <p>
     * {@.ja 指定した同一判定に合致するオブジェクトの登録を解除し、取得する。
     * 指定したオブジェクトが登録されていない場合にはNULLを返す。}
     * {@.en Unregister the object that was specified and get it.
     * This operation returns NULL if the specified object is not 
     * registered.}
     *
     * @param equalFunc 
     *   {@.ja 同一判定に用いるオブジェクト}
     *   {@.en The object of the target object for the unregistration}
     *
     * @return 
     *   {@.ja 登録解除に成功した場合は、そのオブジェクトを返す。
     *   指定した条件に合致するオブジェクトが存在しなかった場合は、
     *   nullを返す。}
     *   {@.en Unregistered object.
     *   Returns NULL if the specified object is not registered.}
     */
    public TARGET unregisterObject(equalFunctor equalFunc) {
        
        synchronized (m_objects) {
            
            for (int i = 0; i < m_objects.size(); i++) {
                if (equalFunc.equalof(m_objects.elementAt(i))) {
                    TARGET retVal = m_objects.elementAt(i);
                    m_objects.remove(m_objects.elementAt(i));
                    return retVal;
                }
            }
            
            return null;
        }
    }
    
    /**
     * {@.ja オブジェクトを検索する。}
     * {@.en Find the object}
     * 
     * <p>
     * {@.ja 登録されているオブジェクトの中から指定した条件に合致する
     * オブジェクトを検索して取得する。
     * 指定した条件に合致するオブジェクトが登録されていない場合にはNULLを返す。}
     * {@.en Find the object that matches the specified condition among 
     * the registered objects and get it.
     * This operation returns NULL if the object that does matches the specified
     * condition is not registered.}
     *
     * @param equalFunc 
     *   {@.ja 同一判定に用いるオブジェクト}
     *   {@.en The object of the target object for finding}
     *
     * @return 
     *   {@.ja 条件に合致するオブジェクトが見つかった場合は、
     *   そのオブジェクトを返す。
     *   条件に合致するオブジェクトが見つからない場合は、nullを返す。}
     *   {@.en Result of finding object}
     */
    public synchronized TARGET find(equalFunctor equalFunc) {
        
        for (int i = 0; i < m_objects.size(); i++) {
            if (equalFunc.equalof(m_objects.elementAt(i))) {
                return m_objects.elementAt(i);
            }
        }
        
        return null;
    }
    
    /**
     * {@.ja 登録されているオブジェクトのリストを取得する。}
     * {@.en Get a list of obejects that are registerd}
     *
     * @return 
     *   {@.ja 登録されているオブジェクト・リスト}
     *   {@.en List of registerd objects.}
     * 
     */
    public synchronized Vector<TARGET> getObjects() {
        
        return new Vector<TARGET>(m_objects);
    }
    
    /**
     * {@.ja 登録されているオブジェクトリスト}
     * {@.en The list of registered objects}
     */
    protected Vector<TARGET> m_objects = new Vector<TARGET>();

}
