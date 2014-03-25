package jp.go.aist.rtm.RTC.executionContext;

import java.util.Vector;

import jp.go.aist.rtm.RTC.ObjectManager;
import jp.go.aist.rtm.RTC.executionContext.ECFactoryBase;
import jp.go.aist.rtm.RTC.executionContext.ECFactoryJava;
import jp.go.aist.rtm.RTC.util.equalFunctor;
import junit.framework.TestCase;

/**
* Object Managerクラス　テスト(2)
* 対象クラス：ObjectManager
*/
public class ObjectManagerTest extends TestCase {
    
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Factoryの登録/解除チェック
     *　・ファクトリを生成して、ObjectManagerに登録できるか？
     *　・同じファクトリを登録しようとして登録に失敗するか？
     *　・登録したファクトリの数を取得できるか？
     *　・名称を指定してファクトリを取得できるか？
     *　・名称を指定してファクトリの登録を解除できるか？
     *　・未登録のファクトリに対して登録解除した際にnullが返ってくるか？
     */
    public void test_all() {
        
        ObjectManager<String, java.lang.Object> objMgr =
            new ObjectManager<String, java.lang.Object>();
        
        // ファクトリを生成して、ObjectManagerに登録する
        ECFactoryJava factory1 = new ECFactoryJava(
                "jp.go.aist.rtm.RTC.executionContext.PeriodicExecutionContext");
        objMgr.registerObject(factory1, new ECFactoryPredicate(factory1));
        
        // 登録できていることを確認する
        assertEquals(1, objMgr.getObjects().size());
        
        // 同じファクトリを登録しようとして、登録に失敗することを確認する
        boolean result = objMgr.registerObject(factory1, new ECFactoryPredicate(factory1));
        assertEquals(false, result);
        
        // 別のファクトリを生成して、ObjectManagerに登録する
        ECFactoryJava factory2 = new ECFactoryJava(
                "jp.go.aist.rtm.RTC.executionContext.ExtTrigExecutionContext");
        objMgr.registerObject(factory2, new ECFactoryPredicate(factory2));
        
        // 登録できていることを確認する
        assertEquals(2, objMgr.getObjects().size());

        // 登録されている２つのファクトリが、意図どおりの２つであることを確認する
        Vector<Object> objects = objMgr.getObjects();
        assertEquals(2, objects.size());
        assertEquals("jp.go.aist.rtm.RTC.executionContext.PeriodicExecutionContext",
                ((ECFactoryJava) objects.elementAt(0)).m_name);
        assertEquals("jp.go.aist.rtm.RTC.executionContext.ExtTrigExecutionContext",
                ((ECFactoryJava) objects.elementAt(1)).m_name);
        
        // find()のテスト
        ECFactoryJava factory3 = (ECFactoryJava) objMgr.find(
                new ECFactoryPredicate("jp.go.aist.rtm.RTC.executionContext.ExtTrigExecutionContext"));
        assertEquals("jp.go.aist.rtm.RTC.executionContext.ExtTrigExecutionContext", factory3.m_name);

        // 登録解除する
        objMgr.unregisterObject(
                new ECFactoryPredicate("jp.go.aist.rtm.RTC.executionContext.ExtTrigExecutionContext"));
        
        // 正しく登録解除できていることを確認する
        assertEquals(1, objMgr.getObjects().size());
        
        // 登録解除済みのものを検索して、見つからないことを確認する
        factory3 = (ECFactoryJava) objMgr.find(
                new ECFactoryPredicate("jp.go.aist.rtm.RTC.executionContext.ExtTrigExecutionContext"));
        assertNull(factory3);
        
        // 登録解除済みのものを、さらに解除しようとして、解除できないことを確認する
        factory3 = (ECFactoryJava) objMgr.unregisterObject(
                new ECFactoryPredicate("jp.go.aist.rtm.RTC.executionContext.ExtTrigExecutionContext"));
        assertNull(factory3);
    }

    class ECFactoryPredicate implements equalFunctor {
        
        public ECFactoryPredicate(final String name) {
            m_name = name;
        }
        
        public ECFactoryPredicate(ECFactoryBase factory) {
            m_name = factory.name();
        }
        
        public boolean equalof(java.lang.Object factory) {
            return m_name.equals(((ECFactoryBase) factory).name());
        }
        
        public String m_name;
    }
    
}
