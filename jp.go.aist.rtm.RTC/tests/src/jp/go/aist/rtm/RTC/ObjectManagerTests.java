package jp.go.aist.rtm.RTC;

import java.util.Vector;

import jp.go.aist.rtm.RTC.util.equalFunctor;
import junit.framework.TestCase;

/**
* ObjectManager　テスト
* 対象クラス：ObjectManager
*/
public class ObjectManagerTests extends TestCase {
    
    private class ObjectMock {
        public ObjectMock(String id) {
            m_id = id;
        }
        public String getId() {
            return m_id;
        }
    
        private String m_id;
    };
    
    private class PredicateMock implements equalFunctor {
        public PredicateMock(ObjectMock obj) {
            m_id = obj.getId();
        }
        public PredicateMock(String id) {
            m_id = id;
        }
        private String m_id;
        public boolean equalof(Object object) {
            return m_id.equals(((ObjectMock)object).getId());
        }
    };

    
    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>registerObject()メソッドのテスト
     * <ul>
     * <li>オブジェクトを正常に登録できるか？</li>
     * </ul>
     * </p>
     */
    public void test_registerObject() {
        ObjectManager<String, ObjectMock> objMgr = new ObjectManager<String, ObjectMock>();

        // オブジェクトを準備しておく
        ObjectMock obj1 = new ObjectMock("ID 1");
        ObjectMock obj2 = new ObjectMock("ID 2");
            
        // オブジェクトを正常に登録できるか？
        assertTrue(objMgr.registerObject(obj1, new PredicateMock(obj1)));
        assertTrue(objMgr.registerObject(obj2, new PredicateMock(obj2)));
    }
    /**
     * <p>registerObject()メソッドのテスト
     * <ul>
     * <li>同一のオブジェクト（当然、識別子も同じ）の登録を試みて、意図どおり失敗するか？</li>
     * <li>同一の識別子を持つ別のオブジェクトの登録を試みて、意図どおり失敗するか？</li>
     * </ul>
     * </p>
     */
    public void test_registerObject_with_overlapped_identifier() {
        ObjectManager<String, ObjectMock> objMgr = new ObjectManager<String, ObjectMock>();

        // 同一の識別子を持つオブジェクトを準備しておく
        ObjectMock obj1 = new ObjectMock("ID");
        ObjectMock obj2 = new ObjectMock("ID");

        // オブジェクトを登録する
        assertTrue(objMgr.registerObject(obj1, new PredicateMock(obj1)));
            
        // 同一のオブジェクト（当然、識別子も同じ）の登録を試みて、意図どおり失敗するか？
        assertFalse(objMgr.registerObject(obj1, new PredicateMock(obj1)));
            
        // 同一の識別子を持つ別のオブジェクトの登録を試みて、意図どおり失敗するか？
        assertFalse(objMgr.registerObject(obj1, new PredicateMock(obj2)));
    }
    /**
     * <p>find()メソッドのテスト
     * <ul>
     * <li>登録されたオブジェクトをfind()で正しく取得できるか？</li>
     * <li>存在しないIDを指定した場合、意図どおりNULLが取得されるか？</li>
     * </ul>
     * </p>
     */
    public void test_find() {
        ObjectManager<String, ObjectMock> objMgr = new ObjectManager<String, ObjectMock>();
        // オブジェクトを準備しておく
        ObjectMock obj1 = new ObjectMock("ID 1");
        ObjectMock obj2 = new ObjectMock("ID 2");

        // オブジェクトを登録する
        assertTrue(objMgr.registerObject(obj1, new PredicateMock(obj1)));
        assertTrue(objMgr.registerObject(obj2, new PredicateMock(obj2)));
            
        // 登録されたオブジェクトをfind()で正しく取得できるか？
        ObjectMock pObjRet1 = objMgr.find(new PredicateMock("ID 1"));
        assertNotNull(pObjRet1);
        assertEquals("ID 1", pObjRet1.getId());

        ObjectMock pObjRet2 = objMgr.find(new PredicateMock("ID 2"));
        assertNotNull(pObjRet2);
        assertEquals("ID 2", pObjRet2.getId());
            
        // 存在しないIDを指定した場合、意図どおりNULLが取得されるか？
        assertNull(objMgr.find(new PredicateMock("INEXIST ID")));
    }
    /**
     * <p>unregisterObject()メソッドとfind()メソッドのテスト
     * <ul>
     * <li>オブジェクトをunregisterObject()で登録解除して、そのオブジェクトを正しく取得できるか？</li>
     * <li>登録解除したオブジェクトのIDを指定してfind()した場合、意図どおりNULLを得るか？</li>
     * <li>登録解除していないオブジェクトは、依然としてfind()で正しく取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_unregisterObject_and_find() {
        ObjectManager<String, ObjectMock> objMgr = new ObjectManager<String, ObjectMock>();
            
        // オブジェクトを準備しておく
        ObjectMock obj1 = new ObjectMock("ID 1");
        ObjectMock obj2 = new ObjectMock("ID 2");

        // オブジェクトを登録する
        assertTrue(objMgr.registerObject(obj1, new PredicateMock(obj1)));
        assertTrue(objMgr.registerObject(obj2, new PredicateMock(obj2)));
            
        // オブジェクトを登録解除して、そのオブジェクトを正しく取得できるか？
        ObjectMock pObjRet1 = objMgr.unregisterObject(new PredicateMock("ID 1"));
        assertNotNull(pObjRet1);
        assertEquals("ID 1", pObjRet1.getId());
            
        // 登録解除したオブジェクトのIDを指定してfind()した場合、意図どおりNULLを得るか？
        assertNull(objMgr.find(new PredicateMock("ID 1")));
            
        // 登録解除していないオブジェクトは、依然としてfind()で正しく取得できるか？
        ObjectMock pObjRet2 = objMgr.find(new PredicateMock("ID 2"));
        assertNotNull(pObjRet2);
        assertEquals("ID 2", pObjRet2.getId());
    }
    /**
     * <p>getObjects()メソッドのテスト
     * <ul>
     * <li>getObjects()で、登録済みの全オブジェクトを正しく取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_getObjects() {
        ObjectManager<String, ObjectMock> objMgr = new ObjectManager<String, ObjectMock>();
        
        // オブジェクトを準備しておく
        ObjectMock obj1 = new ObjectMock("ID 1");
        ObjectMock obj2 = new ObjectMock("ID 2");

        // オブジェクトを登録する
        assertTrue(objMgr.registerObject(obj1, new PredicateMock(obj1)));
        assertTrue(objMgr.registerObject(obj2, new PredicateMock(obj2)));
        
        // getObjects()で、登録済みの全オブジェクトを正しく取得できるか？
        Vector<ObjectMock> objs = objMgr.getObjects();
        assertEquals(2, objs.size());
        assertEquals("ID 1", objs.elementAt(0).getId());
        assertEquals("ID 2", objs.elementAt(1).getId());
    }
}
