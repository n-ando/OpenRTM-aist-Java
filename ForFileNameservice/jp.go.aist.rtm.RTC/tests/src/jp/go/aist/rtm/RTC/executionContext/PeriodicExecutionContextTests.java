package jp.go.aist.rtm.RTC.executionContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import jp.go.aist.rtm.RTC.DataFlowComponentBase;
import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.util.POAUtil;
import jp.go.aist.rtm.RTC.util.ORBUtil;
import junit.framework.TestCase;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAManager;
import org.omg.PortableServer.POAManagerPackage.State;

import RTC.ComponentProfile;
import RTC.ExecutionContext;
import RTC.ExecutionContextService;
import RTC.ExecutionKind;
import RTC.LifeCycleState;
import RTC.LightweightRTObject;
import RTC.LightweightRTObjectHelper;
import RTC.LightweightRTObjectPOA;
import RTC.PortService;
import RTC.ReturnCode_t;
import _SDOPackage.Configuration;
import _SDOPackage.DeviceProfile;
import _SDOPackage.InterfaceNotImplemented;
import _SDOPackage.InvalidParameter;
import _SDOPackage.Monitoring;
import _SDOPackage.NameValue;
import _SDOPackage.NotAvailable;
import _SDOPackage.Organization;
import _SDOPackage.SDOService;
import _SDOPackage.ServiceProfile;

/**
* PeriodicExecutionContextクラス　テスト
* 対象クラス：PeriodicExecutionContext
*/
public class PeriodicExecutionContextTests extends TestCase {
    private ORB m_pORB;
    private POA m_pPOA;
    private POAManager m_poaMgr;

    private final String[] ARGS = new String[] {
        "-ORBInitialPort 2809",
        "-ORBInitialHost localhost"
    };

    protected void setUp() throws Exception {
        super.setUp();

        this.m_pORB = ORBUtil.getOrb(ARGS);
        this.m_pPOA = org.omg.PortableServer.POAHelper.narrow(
                this.m_pORB.resolve_initial_references("RootPOA"));
        this.m_poaMgr = this.m_pPOA.the_POAManager();
        if (! this.m_poaMgr.get_state().equals(State.ACTIVE)) {
            this.m_poaMgr.activate();
        }
            
        // (1-1) ORBの初期化
//        java.util.Properties props = new java.util.Properties();
//        props.put("org.omg.CORBA.ORBInitialPort", "2809");
//        props.put("org.omg.CORBA.ORBInitialHost", "localhost");
//        this.m_pORB = ORB.init(new String[0], props);
//
        // (1-2) POAManagerのactivate
//        this.m_pPOA = org.omg.PortableServer.POAHelper.narrow(
//                this.m_pORB.resolve_initial_references("RootPOA"));
//        this.m_pPOA.the_POAManager().activate();


    }
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>is_running()メソッドのテスト
     * <ul>
     * <li>start()呼出前は、非running状態か？</li>
     * <li>start()呼出し後は、running状態か？</li>
     * <li>stop()呼出後は、非running状態か？</li>
     * </ul>
     * </p>
     */
    public void test_is_running() {
        PeriodicExecutionContext ec = new PeriodicExecutionContext(); 
                 // will be deleted automatically
    
        // start()呼出し前は、非running状態か？
        assertFalse(ec.is_running());
    
        // start()呼出し後は、running状態か？
        assertEquals(ReturnCode_t.RTC_OK, ec.start());
        assertTrue(ec.is_running());
    
        // stop()呼出後は、非running状態か？
        assertEquals(ReturnCode_t.RTC_OK, ec.stop());
        assertFalse(ec.is_running());
    }
    /**
     * <p>start()メソッドのテスト
     * <ul>
     * <li>start()メソッド呼出しにより、コンポーネントのon_startup()が呼び出されるか？</li>
     * </ul>
     * </p>
     */
    public void test_start_invoking_on_startup() {

        // RTObjectを生成する
        Manager manager = Manager.instance();
        DataFlowComponentMock mock = new DataFlowComponentMock(manager); // will be deleted automatically
        manager.activateManager();
        
        // ExecutionContextを生成する
        PeriodicExecutionContext ec = new PeriodicExecutionContext(); // will be deleted automatically
            
        // ExecutionContextにRTObjectを登録する
        assertEquals(ReturnCode_t.RTC_OK, ec.add_component(mock._this()));
        
        // この時点では、まだon_startup()は呼び出されていないはず
        assertEquals(0, mock.countLog("on_startup"));
        
        // start()を呼び出す
        assertEquals(ReturnCode_t.RTC_OK, ec.start());
        
        // この時点で、on_startup()が1回だけ呼び出されているはず
        assertEquals(1, mock.countLog("on_startup"));

    }
    /**
     * <p>start()メソッドのテスト
     * <ul>
     * <li>すでにrunning状態の際、start()呼出が意図どおりに失敗するか？</li>
     * </ul>
     * </p>
     */
    public void test_start_with_running() {

        PeriodicExecutionContext ec = new PeriodicExecutionContext(); // will be deleted automatically
    
        // start()呼出し前は、非running状態のはず
        assertFalse(ec.is_running());
    
        // start()呼出し後は、running状態のはず
        ec.start();
        assertTrue(ec.is_running());
    
        // さらにstart()呼出しを行い、意図どおりのエラーコードで戻ることを確認する
        assertEquals(ReturnCode_t.PRECONDITION_NOT_MET, ec.start());

    }
    /**
     * <p>start()メソッドのテスト
     * <ul>
     * <li>Alive状態にない場合にstart()メソッドを呼出して、意図どおりに失敗するか？</li>
     * </ul>
     * </p>
     */
    public void test_start_with_not_alive() {

        // RTObjectを生成する
        Manager manager = Manager.instance();
        DataFlowComponentMock mock = new DataFlowComponentMock(manager); // will be deleted automatically
        manager.activateManager();
        
        // ExecutionContextを生成する
        PeriodicExecutionContext ec = new PeriodicExecutionContext(); // will be deleted automatically
        
        // ExecutionContextにRTObjectを登録する
        assertEquals(ReturnCode_t.RTC_OK, ec.add_component(mock._this()));
        
        // 非Alive状態にしておく
        mock.setAlive(false);
        assertFalse(mock.is_alive());
        
        // start()呼出しを行い、意図どおりのエラーコードで戻ることを確認する
        // assertEquals(ReturnCode_t.PRECONDITION_NOT_MET, ec.start());

    }
    /**
     * <p>stop()メソッドのテスト
     * <ul>
     * <li>stop()メソッド呼出しにより、コンポーネントのon_shutdown()が呼び出されるか？</li>
     * </ul>
     * </p>
     */
    public void test_stop_invoking_on_shutdown() {

        // RTObjectを生成する
        Manager manager = Manager.instance();
        DataFlowComponentMock mock = new DataFlowComponentMock(manager); // will be deleted automatically
        manager.activateManager();
        
        // ExecutionContextを生成する
        PeriodicExecutionContext ec = new PeriodicExecutionContext(); // will be deleted automatically
        
        // ExecutionContextにRTObjectを登録する
        assertEquals(ReturnCode_t.RTC_OK, ec.add_component(mock._this()));
        
        // start()を呼び出す
        assertEquals(ReturnCode_t.RTC_OK, ec.start());
        
        // この時点では、まだon_shutdown()は呼び出されていないはず
        assertEquals(0, mock.countLog("on_shutdown"));

        // stop()を呼び出す
        assertEquals(ReturnCode_t.RTC_OK, ec.stop());
        
        // この時点で、on_shutdown()が1回だけ呼び出されているはず
        assertEquals(1, mock.countLog("on_shutdown"));

    }
    /**
     * <p>stop()メソッドのテスト
     * <ul>
     * <li>非running状態でstop()メソッドを呼び出した場合、意図どおりに失敗するか？</li>
     * </ul>
     * </p>
     */
    public void test_stop_with_not_running() {

        // ExecutionContextを生成する
        PeriodicExecutionContext ec = new PeriodicExecutionContext(); // will be deleted automatically
        
        // まだstart()していない状態で、つまり非runningの状態で、
        // stop()を呼び出し、意図どおりのエラーコードで戻ることを確認する
        assertFalse(ec.is_running());
        assertEquals(ReturnCode_t.PRECONDITION_NOT_MET, ec.stop());

        // start()を呼び出す
        assertEquals(ReturnCode_t.RTC_OK, ec.start());
        
        // stop()を呼び出す
        assertTrue(ec.is_running());
        assertEquals(ReturnCode_t.RTC_OK, ec.stop());
        
        // さらにstop()を呼び出し、意図どおりのエラーコードで戻ることを確認する
        assertFalse(ec.is_running());
        assertEquals(ReturnCode_t.PRECONDITION_NOT_MET, ec.stop());

    }
    /**
     * <p>start()メソッドとstop()メソッドのテスト
     * <ul>
     * <li>start()とstop()を繰り返し連続で、正常に呼出しできるか？</li>
     * </ul>
     * </p>
     */
    public void test_start_and_stop_multiple_times() {

        // ExecutionContextを生成する
        PeriodicExecutionContext ec = new PeriodicExecutionContext(); // will be deleted automatically

        // start(), stop()を連続して正常に呼び出せるか？
        for( int i = 0; i < 10; ++i) {
            // start()を呼び出す
            assertEquals(ReturnCode_t.RTC_OK, ec.start());

            // stop()を呼び出す
            assertEquals(ReturnCode_t.RTC_OK, ec.stop());
        }

    }
    /**
     * <p>set_rate()メソッドとget_rate()メソッドのテスト
     * <ul>
     * <li>set_rate()で設定したレート値を、get_rate()で正しく取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_set_rate_and_get_rate() {

        // ExecutionContextを生成する
        PeriodicExecutionContext ec = new PeriodicExecutionContext(); // will be deleted automatically
        
        // set_rate()で設定したレートが、get_rate()で正しく取得できるか？
        for( int i = 1; i <= 10; ++i ) {
            double rate = 1.0 * i;
            assertEquals(ReturnCode_t.RTC_OK, ec.set_rate(rate));
            assertEquals(rate, ec.get_rate());
        }

    }
    /**
     * <p>set_rate()メソッドのテスト
     * <ul>
     * <li>レート値に0を指定してset_rate()を呼び出した場合、意図どおりに失敗するか？</li>
     * <li>レート値に負値を指定してset_rate()を呼び出した場合、意図どおりに失敗するか？</li>
     * </ul>
     * </p>
     */
    public void test_set_rate_with_zero_or_negative_rate() {

        // ExecutionContextを生成する
        PeriodicExecutionContext ec = new PeriodicExecutionContext(); // will be deleted automatically
        
        // 0または負数のレートを指定した場合、意図どおりのエラーコードで戻るか？
        for( int i = 0; i < 10; ++i ) {
            double rate = - 1.0 * i;
            assertEquals(ReturnCode_t.BAD_PARAMETER, ec.set_rate(rate));
        }

    }
    /**
     * <p>set_rate()メソッドのテスト
     * <ul>
     * <li>set_rate()呼出しにより、コンポーネントのon_rate_changed()が呼び出されるか？</li>
     * </ul>
     * </p>
     */
    public void test_set_rate_invoking_on_rate_changed() {

        // RTObjectを生成する
        Manager manager = Manager.instance();
        DataFlowComponentMock mock = new DataFlowComponentMock(manager); // will be deleted automatically
        manager.activateManager();
        
        // ExecutionContextを生成する
        PeriodicExecutionContext ec = new PeriodicExecutionContext(); // will be deleted automatically
        assertEquals(ExecutionKind.PERIODIC, ec.get_kind());
        
        // ExecutionContextにRTObjectを登録する
        assertEquals(ReturnCode_t.RTC_OK, ec.add_component(mock._this()));
        
        // この時点では、on_rate_changed()は1回も呼び出されていないはず
        assertEquals(0, mock.countLog("on_rate_changed"));
        
        // set_rate()を呼出す
        assertEquals(ReturnCode_t.RTC_OK, ec.set_rate(1.0d));
        
        // この時点で、on_rate_changed()が1回だけ呼び出されているはず
        assertEquals(1, mock.countLog("on_rate_changed"));

    }
    /**
     * <p>add()メソッドのテスト
     * <ul>
     * <li>add()メソッド呼出しにより、コンポーネントのattach_executioncontext()が呼び出されるか？</li>
     * </ul>
     * </p>
     */
    public void test_add_invoking_attach_executioncontext() {

        // RTObjectを生成する
        Manager manager = Manager.instance();
        DataFlowComponentMock mock = new DataFlowComponentMock(manager); // will be deleted automatically
        manager.activateManager();
        
        // ExecutionContextを生成する
        PeriodicExecutionContext ec = new PeriodicExecutionContext(); // will be deleted automatically
        assertEquals("1:",ExecutionKind.PERIODIC, ec.get_kind());
        
        // この時点では、attach_executioncontext()は1回も呼び出されていないはず
        assertEquals("2:",0, mock.countLog("attach_executioncontext"));

        // ExecutionContextにRTObjectを登録する
        assertEquals("3:",ReturnCode_t.RTC_OK, ec.add_component(mock._this()));
        
        // この時点で、attach_executioncontext()が1回だけ呼び出されているはず
        assertEquals("4:",1, mock.countLog("attach_executioncontext"));

    }
    /**
     * <p>add()メソッドのテスト
     * <ul>
     * <li>データフローコンポーネントではないLightweightRTObjectを指定してadd()メソッドを呼び出した場合、意図どおりに失敗するか？</li>
     * </ul>
     * </p>
     */
    public void test_add_not_with_data_flow_component() {

        // RTObjectを生成する
        LightweightRTObjectMock2 mock = new LightweightRTObjectMock2(); // will be deleted automatically
        // ExecutionContextを生成する
        PeriodicExecutionContext ec = new PeriodicExecutionContext(); // will be deleted automatically
        assertEquals(ExecutionKind.PERIODIC, ec.get_kind());
        
        // LightweightRTObjectではあるが、DataFlowComponentではないRTObjectを用いて、
        // add()呼出しを試みて、意図どおりエラーコードで戻ることを確認する
        assertEquals(ReturnCode_t.BAD_PARAMETER, ec.add_component(mock._this()));

    }
    /**
     * <p>remove()メソッドのテスト
     * <ul>
     * <li>remove()呼出しにより、コンポーネントのdetach_executioncontext()が呼び出されるか？</li>
     * </ul>
     * </p>
     */
    public void test_remove_invoking_detach_executioncontext() {

        // RTObjectを生成する
        Manager manager = Manager.instance();
        DataFlowComponentMock mock = new DataFlowComponentMock(manager); // will be deleted automatically
        manager.activateManager();
        
        // ExecutionContextを生成する
        PeriodicExecutionContext ec = new PeriodicExecutionContext(); // will be deleted automatically
        assertEquals("1:",ExecutionKind.PERIODIC, ec.get_kind());
        
        // ExecutionContextにRTObjectを登録する
        assertEquals("2:",ReturnCode_t.RTC_OK, ec.add_component(mock._this()));

        // この時点では、attach_executioncontext()は1回も呼び出されていないはず
        assertEquals("3:",0, mock.countLog("detach_executioncontext"));
        
        // ExecutionContextへの登録を解除する
        assertEquals("4:",ReturnCode_t.RTC_OK, ec.remove_component(mock._this()));
        
        // この時点で、detach_executioncontext()が1回だけ呼び出されているはず
        assertEquals("5:",1, mock.countLog("detach_executioncontext"));

    }
    /**
     * <p>remove()メソッドのテスト
     * <ul>
     * <li>まだExecutionContextに登録していないコンポーネントについて登録解除を試みて、意図どおりに失敗するか？</li>
     * </ul>
     * </p>
     */
    public void test_remove_with_not_attached_component() {

        // RTObjectを生成する
        Manager manager = Manager.instance();
        DataFlowComponentMock mock = new DataFlowComponentMock(manager); // will be deleted automatically
        manager.activateManager();
        
        // ExecutionContextを生成する
        PeriodicExecutionContext ec = new PeriodicExecutionContext(); // will be deleted automatically
        assertEquals(ExecutionKind.PERIODIC, ec.get_kind());

        // まだ登録していないコンポーネントについてExecutionContextからの登録解除を試みて、
        // 意図どおりのエラーコードで戻ることを確認する
        assertEquals(ReturnCode_t.BAD_PARAMETER, ec.remove_component(mock._this()));

    }
    /**
     * <p>remove()メソッドのテスト
     * <ul>
     * <li>Active状態のコンポーネントに対してremove()を試みた場合、意図どおりに失敗するか？</li>
     * </ul>
     * </p>
     */
    public void test_remove_when_component_is_still_active() {

        // RTObjectを生成する
        Manager manager = Manager.instance();
        DataFlowComponentMock mock = new DataFlowComponentMock(manager); // will be deleted automatically
        manager.activateManager();
        
        // ExecutionContextを生成する
        PeriodicExecutionContext ec = new PeriodicExecutionContext(); // will be deleted automatically
        assertEquals(ReturnCode_t.RTC_OK, ec.start());
        
        // ExecutionContextにRTObjectを登録する
        assertEquals(ReturnCode_t.RTC_OK, ec.add_component(mock._this()));
        
        // コンポーネントをActiveにする
        assertEquals(ReturnCode_t.RTC_OK, ec.activate_component(mock._this()));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        assertEquals(LifeCycleState.ACTIVE_STATE, ec.get_component_state(mock._this()));

        // コンポーネントがActiveのままでremove()を試みて、意図どおりのエラーコードが戻ることを確認する
        // assertEquals(ReturnCode_t.PRECONDITION_NOT_MET, ec.remove_component(mock._this()));

    }
    /**
     * <p>activate()メソッドのテスト
     * <ul>
     * <li>activate()呼出しにより、コンポーネントのon_activated()が呼び出されるか？</li>
     * </ul>
     * </p>
     */
    public void test_activate_component_invoking_on_activated() {

        // RTObjectを生成する
        Manager manager = Manager.instance();
        DataFlowComponentMock mock = new DataFlowComponentMock(manager); // will be deleted automatically
        manager.activateManager();
        
        // ExecutionContextを生成する
        PeriodicExecutionContext ec = new PeriodicExecutionContext(); // will be deleted automatically
        assertEquals(ReturnCode_t.RTC_OK, ec.start());
        
        // ExecutionContextにRTObjectを登録する
        assertEquals(ReturnCode_t.RTC_OK, ec.add_component(mock._this()));
        
        // この時点では、まだon_activated()は1回も呼び出されていないはず
        assertEquals(0, mock.countLog("on_activated"));
        
        // コンポーネントをActiveにする
        assertEquals(ReturnCode_t.RTC_OK, ec.activate_component(mock._this()));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        
        // activate_component()からon_activated()の呼出しは同期呼出であり、
        // この時点で、on_activated()が1回だけ呼び出されているはず
        assertEquals(1, mock.countLog("on_activated"));

        // activate_component()からon_activated()の呼出しは同期呼出であり、
        // スレッドコンテキストを切替えることなく、Active状態に遷移していることを確認する
        assertEquals(LifeCycleState.ACTIVE_STATE, ec.get_component_state(mock._this()));

    }
    /**
     * <p>activate()メソッドのテスト
     * <ul>
     * <li>ExecutionContextに未登録のコンポーネントに対してactivate()呼出しを行い、意図どおりに失敗するか？</li>
     * </ul>
     * </p>
     */
    public void test_activate_component_without_participating() {
 
        // RTObjectを生成する
        Manager manager = Manager.instance();
        DataFlowComponentMock mock = new DataFlowComponentMock(manager); // will be deleted automatically
        manager.activateManager();
        
        // ExecutionContextを生成する
        PeriodicExecutionContext ec = new PeriodicExecutionContext(); // will be deleted automatically
        assertEquals(ReturnCode_t.RTC_OK, ec.start());
        
        // ExecutionContextにコンポーネント登録することなくactivate_component()を呼出し、
        // 意図どおりのエラコードで戻ることを確認する
        assertEquals(ReturnCode_t.BAD_PARAMETER, ec.activate_component(mock._this()));

    }
    /**
     * <p>activate_component()メソッドのテスト
     * <ul>
     * <li>Error状態でactivate_component()呼出しを行い、意図どおりに失敗するか？</li>
     * </ul>
     * </p>
     */
    public void test_activate_component_in_Error_state() {

        // RTObjectを生成する
        Manager manager = Manager.instance();
        DataFlowComponentMock mock = new DataFlowComponentMock(manager); // will be deleted automatically
        manager.activateManager();
        
        // ExecutionContextを生成する
        PeriodicExecutionContext ec = new PeriodicExecutionContext(); // will be deleted automatically
        assertEquals(ReturnCode_t.RTC_OK, ec.start());
        
        // コンポーネントをError状態にまで遷移させる
        mock.setError(true);
        assertEquals(ReturnCode_t.RTC_OK, ec.add_component(mock._this()));
        assertEquals(ReturnCode_t.RTC_OK, ec.activate_component(mock._this()));
        // Error状態へ遷移するまで待つ。本来、このスリープが仕様上必要か否か？
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        assertEquals(LifeCycleState.ERROR_STATE, ec.get_component_state(mock._this()));
        
        // Error状態でactivate_component()呼出しを行い、意図どおりのエラーコードで戻ることを確認する
        assertEquals(ReturnCode_t.PRECONDITION_NOT_MET, ec.activate_component(mock._this()));

    }
    /**
     * <p>activate_component()メソッドのテスト
     * <ul>
     * <li>非Alive状態のコンポーネントに対してactivate_component()呼出しを行い、意図どおりに失敗するか？</li>
     * </ul>
     * </p>
     */
    public void test_activate_component_not_in_Alive_state() {

        // RTObjectを生成する
        Manager manager = Manager.instance();
        DataFlowComponentMock mock = new DataFlowComponentMock(manager); // will be deleted automatically
        manager.activateManager();
        
        // ExecutionContextを生成する
        PeriodicExecutionContext ec = new PeriodicExecutionContext(); // will be deleted automatically
        assertEquals(ReturnCode_t.RTC_OK, ec.start());
        
        // ExecutionContextにRTObjectを登録する
        assertEquals(ReturnCode_t.RTC_OK, ec.add_component(mock._this()));
        
        // 非Alive状態にしておく
        mock.setAlive(false);
        assertFalse(mock.is_alive());
        
        // activate_component()呼出しを行い、意図どおりのエラーコードで戻ることを確認する
        // assertEquals(ReturnCode_t.BAD_PARAMETER, ec.activate_component(mock._this()));

    }
    /**
     * <p>deactivate_component()メソッドのテスト
     * <ul>
     * <li>deactivate_component()呼出しにより、コンポーネントのon_deactivated()が呼び出されるか？</li>
     * </ul>
     * </p>
     */
    public void test_deactivate_component_invoking_on_deactivated() {

        // RTObjectを生成する
        Manager manager = Manager.instance();
        DataFlowComponentMock mock = new DataFlowComponentMock(manager); // will be deleted automatically
        manager.activateManager();
        
        // ExecutionContextを生成する
        PeriodicExecutionContext ec = new PeriodicExecutionContext(); // will be deleted automatically
        assertEquals(ReturnCode_t.RTC_OK, ec.start());
        
        // ExecutionContextにRTObjectを登録する
        assertEquals(ReturnCode_t.RTC_OK, ec.add_component(mock._this()));
        
        // コンポーネントをactivateする
        assertEquals(ReturnCode_t.RTC_OK, ec.activate_component(mock._this()));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        assertEquals(LifeCycleState.ACTIVE_STATE, ec.get_component_state(mock._this()));

        // この時点では、まだon_activated()は1回も呼び出されていないはず
        assertEquals(0, mock.countLog("on_deactivated"));

        // コンポーネントをdeactivateする
        assertEquals(ReturnCode_t.RTC_OK, ec.deactivate_component(mock._this()));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        assertEquals(LifeCycleState.INACTIVE_STATE, ec.get_component_state(mock._this()));

        // この時点で、on_deactivated()は1回だけ呼び出されているはず
        assertEquals(1, mock.countLog("on_deactivated"));

    }
    /**
     * <p>deactivate_component()メソッドのテスト
     * <ul>
     * <li>ExecutionContextに未登録のコンポーネントに対してdeactivate_component()を呼出し、意図どおりに失敗するか？</li>
     * </ul>
     * </p>
     */
    public void test_deactivate_component_without_participating() {

        // RTObjectを生成する
        Manager manager = Manager.instance();
        DataFlowComponentMock mock = new DataFlowComponentMock(manager); // will be deleted automatically
        manager.activateManager();
        
        // ExecutionContextを生成する
        PeriodicExecutionContext ec = new PeriodicExecutionContext(); // will be deleted automatically
        assertEquals(ReturnCode_t.RTC_OK, ec.start());
        
        // ExecutionContextに登録していないコンポーネントに対してdeactivateを試みて、
        // 意図どおりのエラーコードで戻ることを確認する
        assertEquals(ReturnCode_t.BAD_PARAMETER, ec.deactivate_component(mock._this()));

    }
    /**
     * <p>deactivate_component()メソッドのテスト
     * <ul>
     * <li>非Alive状態のコンポーネントに対してdeactivate_component()呼出しを行い、意図どおりに失敗するか？</li>
     * </ul>
     * </p>
     */
    public void test_deactivate_component_not_in_Alive_state() {

        // RTObjectを生成する
        Manager manager = Manager.instance();
        DataFlowComponentMock mock = new DataFlowComponentMock(manager); // will be deleted automatically
        manager.activateManager();
        
        // ExecutionContextを生成する
        PeriodicExecutionContext ec = new PeriodicExecutionContext(); // will be deleted automatically
        assertEquals(ReturnCode_t.RTC_OK, ec.start());
        
        // ExecutionContextにRTObjectを登録する
        assertEquals(ReturnCode_t.RTC_OK, ec.add_component(mock._this()));
        
        // コンポーネントをactivateする
        assertEquals(ReturnCode_t.RTC_OK, ec.activate_component(mock._this()));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        assertEquals(LifeCycleState.ACTIVE_STATE, ec.get_component_state(mock._this()));

        // 非Alive状態にしておく
        mock.setAlive(false);
        assertFalse(mock.is_alive());

        // 非Alive状態のコンポーネントに対してdeactivateを試みて、意図どおりのエラーコードで戻ることを確認する
        // assertEquals(ReturnCode_t.BAD_PARAMETER, ec.deactivate_component(mock._this()));

    }
    /**
     * <p>reset_component()メソッドのテスト
     * <ul>
     * <li>reset_component()呼出しにより、コンポーネントのon_reset()が呼び出されるか？</li>
     * </ul>
     * </p>
     */
    public void test_reset_component_invoking_on_reset() {

        // RTObjectを生成する
        Manager manager = Manager.instance();
        DataFlowComponentMock mock = new DataFlowComponentMock(manager); // will be deleted automatically
        manager.activateManager();
        
        // ExecutionContextを生成する
        PeriodicExecutionContext ec = new PeriodicExecutionContext(); // will be deleted automatically
        assertEquals(ReturnCode_t.RTC_OK, ec.start());
        
        // ExecutionContextにRTObjectを登録する
        assertEquals(ReturnCode_t.RTC_OK, ec.add_component(mock._this()));
        
        // コンポーネントをError状態にまで遷移させる
        mock.setError(true);
        assertEquals(ReturnCode_t.RTC_OK, ec.add_component(mock._this()));
        assertEquals(ReturnCode_t.RTC_OK, ec.activate_component(mock._this()));
        // Error状態へ遷移するまで待つ。本来、このスリープが仕様上必要か否か？
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        assertEquals(LifeCycleState.ERROR_STATE, ec.get_component_state(mock._this()));
        
        // この時点では、on_reset()は1回も呼び出されていないはず
        assertEquals(0, mock.countLog("on_reset"));

        // reset_component()を呼出し、成功することを確認する
        assertEquals(ReturnCode_t.RTC_OK, ec.reset_component(mock._this()));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        
        // この時点で、on_reset()が1回だけ呼び出されているはず
        assertEquals(1, mock.countLog("on_reset"));

    }
    /**
     * <p>reset_component()メソッドのテスト
     * <ul>
     * <li>非Error状態のコンポーネントに対してreset_component()呼出しを行い、意図どおりに失敗するか？</li>
     * </ul>
     * </p>
     */
    public void test_reset_component_not_in_Error_state() {

        // RTObjectを生成する
        Manager manager = Manager.instance();
        DataFlowComponentMock mock = new DataFlowComponentMock(manager); // will be deleted automatically
        manager.activateManager();
        
        // ExecutionContextを生成する
        PeriodicExecutionContext ec = new PeriodicExecutionContext(); // will be deleted automatically
        assertEquals(ReturnCode_t.RTC_OK, ec.start());
        
        // ExecutionContextにRTObjectを登録する
        assertEquals(ReturnCode_t.RTC_OK, ec.add_component(mock._this()));
        
        // この状態(Inactive)でreset_component()呼出しを行い、意図どおりのエラーコードで戻ることを確認する
        assertEquals(LifeCycleState.INACTIVE_STATE, ec.get_component_state(mock._this()));
        assertEquals(ReturnCode_t.PRECONDITION_NOT_MET, ec.reset_component(mock._this()));

    }
    /**
     * <p>reset_component()メソッドのテスト
     * <ul>
     * <li>非Alive状態のコンポーネントに対してreset_component()呼出しを行い、意図どおりに失敗するか？</li>
     * </ul>
     * </p>
     */
    public void test_reset_component_not_in_Alive_state() {

        // RTObjectを生成する
        Manager manager = Manager.instance();
        DataFlowComponentMock mock = new DataFlowComponentMock(manager); // will be deleted automatically
        manager.activateManager();
        
        // ExecutionContextを生成する
        PeriodicExecutionContext ec = new PeriodicExecutionContext(); // will be deleted automatically
        assertEquals(ReturnCode_t.RTC_OK, ec.start());
        
        // ExecutionContextにRTObjectを登録する
        assertEquals(ReturnCode_t.RTC_OK, ec.add_component(mock._this()));
        
        // 非Alive状態(Create状態)にしておく
        mock.setAlive(false);
        assertFalse(mock.is_alive());
        // この状態(Created)でreset_component()呼出しを行い、意図どおりのエラーコードで戻ることを確認する
        assertEquals(ReturnCode_t.PRECONDITION_NOT_MET, ec.reset_component(mock._this()));

    }

    
    
    

    private class LightweightRTObjectMock2 extends LightweightRTObjectPOA {

        protected Vector<String> m_log = new Vector<String>();
        public LightweightRTObject _this() {
            LightweightRTObject ref;
            try {
                ref = LightweightRTObjectHelper.narrow(POAUtil.getRef(this));
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
            return ref;
        }
        public ReturnCode_t _finalize() {
            return null;
        }
        public ReturnCode_t exit() {
            return null;
        }
        public ExecutionContext get_context(int ec_id) {
            return null;
        }
        public ExecutionContext[] get_contexts() {
            return null;
        }
        public ReturnCode_t initialize() {
            return null;
        }
        public boolean is_alive(ExecutionContext exec_context) {
            return false;
        }
        public int attach_context(ExecutionContext exec_context) {
            m_log.add("attach_executioncontext");
            return 0;
        }
        public ReturnCode_t detach_context(int ec_id) {
            m_log.add("detach_executioncontext");
            return null;
        }
        public ReturnCode_t on_aborting(int ec_id) {
            return null;
        }
        public ReturnCode_t on_activated(int ec_id) {
            return null;
        }
        public ReturnCode_t on_deactivated(int ec_id) {
            return null;
        }
        public ReturnCode_t on_error(int ec_id) {
            return null;
        }
        public ReturnCode_t on_finalize() {
            return null;
        }
        public ReturnCode_t on_initialize() {
            return null;
        }
        public ReturnCode_t on_reset(int ec_id) {
            return null;
        }
        public ReturnCode_t on_shutdown(int ec_id) {
            return null;
        }
        public ReturnCode_t on_startup(int ec_id) {
            return null;
        }
        public int get_context_handle(ExecutionContext cxt) {
            return 0;
        }
        public ExecutionContext[] get_participating_contexts()  {
            return new ExecutionContext[0];
        }
        public ExecutionContext[] get_owned_contexts() {
            return new ExecutionContext[0];
        }
        public int countLog(String line) {
            int count = 0;
            for( int i = 0; i < m_log.size(); ++i) {
                if( m_log.get(i).equals(line)) ++count;
            }
            return count;
        }
    }

    
    private class LightweightRTObjectMock extends DataFlowComponentBase {

        protected Map<Integer, ExecutionContext> m_execContexts = new HashMap<Integer, ExecutionContext>();
        protected int m_nextUniqueId;
        protected Vector<String> m_log = new Vector<String>();
        protected boolean m_alive;
        protected boolean m_error;

        public LightweightRTObjectMock(Manager manager) {
            super(manager);
            m_alive = true;
            m_error = false;
        }
    
        // RTC::_impl_ComponentAction
        public int attach_context(ExecutionContext exec_context) {
            m_log.add("attach_executioncontext");
            m_execContexts.put(new Integer(m_nextUniqueId++), exec_context);
            return m_nextUniqueId;
        }
        public ReturnCode_t detach_context(int ec_id) {
            m_log.add("detach_executioncontext");
//            m_execContexts.remove(ec_id);
            return ReturnCode_t.RTC_OK;
        }
        public ReturnCode_t on_initialize() {
            m_log.add("on_initialize");
            return ReturnCode_t.RTC_OK;
        }
        public ReturnCode_t on_finalize() {
            m_log.add("on_finalize");
            return ReturnCode_t.RTC_OK;
        }
        public ReturnCode_t on_startup(int ec_id) {
            m_log.add("on_startup");
            return ReturnCode_t.RTC_OK;
        }
        public ReturnCode_t on_shutdown(int ec_id) {
            m_log.add("on_shutdown");
            return ReturnCode_t.RTC_OK;
        }
        public ReturnCode_t on_activated(int ec_id) {
            m_log.add("on_activated");
            return returnCode(ReturnCode_t.RTC_OK);
        }
        public ReturnCode_t on_deactivated(int ec_id) {
            m_log.add("on_deactivated");
            return ReturnCode_t.RTC_OK;
        }
        public ReturnCode_t on_aborting(int ec_id) {
            m_log.add("on_aborting");
            return ReturnCode_t.RTC_OK;
        }
        public ReturnCode_t on_error(int ec_id) {
            m_log.add("on_error");
            return ReturnCode_t.RTC_OK;
        }
        public ReturnCode_t on_reset(int ec_id) {
            m_log.add("on_reset");
            return ReturnCode_t.RTC_OK;
        }

        // RTC::_impl_LightweightRTObject
        public ReturnCode_t initialize() {
            m_log.add("initialize");
            return ReturnCode_t.RTC_OK;
        }
        public ReturnCode_t _finalize() {
            m_log.add("finalize");
            return ReturnCode_t.RTC_OK;
        }
        public ReturnCode_t exit() {
            m_log.add("exit");
            return ReturnCode_t.RTC_OK;
        }
        public boolean is_alive() {
            m_log.add("is_alive");
            return m_alive;
        }
        public ExecutionContext[] get_contexts() {
            m_log.add("get_contexts");
            return null;
        }
        public ExecutionContext get_context(int ec_id) {
            m_log.add("get_context");
            return m_execContexts.get(ec_id);
        }

        public int countLog(String line) {
            int count = 0;
            for( int i = 0; i < m_log.size(); ++i) {
                if( m_log.get(i).equals(line)) ++count;
            }
            return count;
        }
            
        void setAlive(boolean alive) {
            m_alive = alive;
        }
            
        void setError(boolean error) {
            m_error = error;
        }

        private ReturnCode_t returnCode(ReturnCode_t rc) {
            return m_error ? ReturnCode_t.RTC_ERROR : rc;
        }

    };

    private class DataFlowComponentMock extends LightweightRTObjectMock {
        public DataFlowComponentMock(Manager manager) {
            super(manager);
        }
        //  SDOPackage::_impl_SDOSystemElement
        public Organization[] get_owned_organizations() throws NotAvailable {
            m_log.add("get_owned_organizations");
            return null; // dummy
        }
        public String get_sdo_id() throws NotAvailable, InternalError {
            m_log.add("get_sdo_id");
            return null; // dummy
        }
        public String get_sdo_type() throws NotAvailable, InternalError {
            m_log.add("get_sdo_type");
            return null; // dummy
        }
        public DeviceProfile get_device_profile() throws NotAvailable, InternalError {
            m_log.add("get_device_profile");
            return null; // dummy
        }
        public ServiceProfile[] get_service_profiles() throws NotAvailable {
            m_log.add("get_service_profiles");
            return null; // dummy
        }
        public ServiceProfile get_service_profile(final String id) throws InvalidParameter, NotAvailable, InternalError {
            m_log.add("get_service_profile");
            return null; // dummy
        }
        public SDOService get_sdo_service(final String id) throws InvalidParameter, NotAvailable, InternalError {
            m_log.add("get_sdo_service");
            return null; // dummy
        }
        public Configuration get_configuration() throws InterfaceNotImplemented, NotAvailable, InternalError {
            m_log.add("get_configuration");
            return null; // dummy
        }
        public Monitoring get_monitoring() throws InterfaceNotImplemented, NotAvailable, InternalError {
            m_log.add("get_monitoring");
            return null; // dummy
        }
        public Organization[] get_organizations() throws NotAvailable, InternalError {
            m_log.add("get_organizations");
            return null; // dummy
        }
        public NameValue[] get_status_list() throws NotAvailable, InternalError {
            m_log.add("get_status_list");
            return null; // dummy
        }
        public Any get_status(final String name) throws InvalidParameter, NotAvailable, InternalError {
            m_log.add("get_status");
            return null; // dummy
        }
    
        // RTC::_impl_RTObject
        public ComponentProfile get_component_profile() {
            m_log.add("get_component_profile");
            // dummy
            ComponentProfile prof = new ComponentProfile();
            return prof;
        }
/*
        public Port[] get_ports() {
            m_log.add("get_ports");
            // dummy
            Port[] ports = new Port[0];
            return ports;
        }
*/
        public ExecutionContextService[] get_execution_context_services() {
            m_log.add("get_execution_context_services");
            // dummy
            ExecutionContextService[] ec = new ExecutionContextService[0];
            return ec;
        }
    
        // RTC::_impl_DataFlowComponentAction
        public ReturnCode_t on_execute(int ec_id) {
            m_log.add("on_execute");
            return ReturnCode_t.RTC_OK; // dummy
        }
        public ReturnCode_t on_state_update(int ec_id) {
            m_log.add("on_state_update");
            return ReturnCode_t.RTC_OK; // dummy
        }
        public ReturnCode_t on_rate_changed(int ec_id) {
            m_log.add("on_rate_changed");
            return ReturnCode_t.RTC_OK; // dummy
        }
    }
}
