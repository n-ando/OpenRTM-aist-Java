package jp.go.aist.rtm.RTC;

import junit.framework.TestCase;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;

/**
* NumberingPolicy　テスト
* 対象クラス：NumberingPolicy
*/
public class NumberingPolicyTests extends TestCase {
    
    private Manager m_mgr;
    private ORB m_pORB;
    private POA m_pPOA;
    
    protected void setUp() throws Exception {
        super.setUp();

        m_mgr = Manager.init(null);
        m_pORB = m_mgr.getORB();
        m_pPOA = m_mgr.getPOA();
}

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>DefaultNumberingPolicy::onCreate()とDefaultNumberingPolicy::onDelete()のテスト
     * <ul>
     * <li>onCreate()は意図どおりに名称を生成して返すか？</li>
     * <li>onDelete()で正しく登録解除されるか？</li>
     * <li>登録解除後に、onCreate()で登録した場合、解除されたオブジェクトの番号が再利用されるか？</li>
     * </ul>
     * </p>
     */
     public void test_onCreate_and_onDelete() throws Exception {
         DefaultNumberingPolicy policy = new DefaultNumberingPolicy();
         RTObject_impl rto1 = new RTObject_impl(m_pORB, m_pPOA);
         RTObject_impl rto2 = new RTObject_impl(m_pORB, m_pPOA);
         RTObject_impl rto3 = new RTObject_impl(m_pORB, m_pPOA);
            
         // onCreate()は意図どおりに名称を生成して返すか？
         assertEquals("0", policy.onCreate(rto1));
         assertEquals("1", policy.onCreate(rto2));
         assertEquals("2", policy.onCreate(rto3));
         
         // onDeleteで、いったん登録解除する
         policy.onDelete(rto1);
         policy.onDelete(rto2);
            
         // 登録順を入れ換えて再度onCreateを呼び出した場合、意図どおりの名称がアサインされるか？
         // （登録解除後に、onCreate()で登録した場合、解除されたオブジェクトの番号が再利用されるか？）
         assertEquals("0", policy.onCreate(rto2));
         assertEquals("1", policy.onCreate(rto1));
      }
}
