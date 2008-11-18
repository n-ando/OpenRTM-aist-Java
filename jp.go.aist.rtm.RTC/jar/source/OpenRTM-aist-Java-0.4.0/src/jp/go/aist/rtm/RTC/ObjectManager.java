package jp.go.aist.rtm.RTC;

import java.util.Vector;

import jp.go.aist.rtm.RTC.util.equalFunctor;

/**
 * <p>オブジェクト管理クラスです。</p>
 *
 * @param <IDENTIFIER> オブジェクト識別子のデータ型を指定します。
 * @param <TARGET> 管理対象となるオブジェクトの型を指定します。
 */
public class ObjectManager<IDENTIFIER, TARGET> {

    public ObjectManager() {
    }
    
    /**
     * <p>オブジェクトを登録します。すでに同一と判定されるオブジェクトが登録済みの場合は、
     * 指定したオブジェクトの登録は行われません。</p>
     * 
     * @param obj 登録対象のオブジェクト
     * @param equalFunc 同一判定に用いるオブジェクト
     * @return 指定したオブジェクトが登録された場合trueを、さもなくばfalseを返します。
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
     * <p>指定した同一判定に合致するオブジェクトの登録を解除します。</p>
     * 
     * @param equalFunc 同一判定に用いるオブジェクト
     * @return 登録解除に成功した場合は、そのオブジェクトを返します。<br />
     * 指定した条件に合致するオブジェクトが存在しなかった場合は、nullを返します。
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
     * <p>登録されているオブジェクトの中から、指定した条件に合致するものを検索して取得します。</p>
     * 
     * @param equalFunc 同一判定に用いるオブジェクト
     * @return 条件に合致するオブジェクトが見つかった場合は、そのオブジェクトを返します。<br />
     * 条件に合致するオブジェクトが見つからない場合は、nullを返します。
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
     * <p>登録されているオブジェクトのリストを取得します。</p>
     * 
     * @return 登録されているオブジェクトリスト
     */
    public synchronized Vector<TARGET> getObjects() {
        
        return new Vector<TARGET>(m_objects);
    }
    
    /**
     * <p>登録されているオブジェクトリスト</p>
     */
    protected Vector<TARGET> m_objects = new Vector<TARGET>();

}
