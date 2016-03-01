package jp.go.aist.rtm.RTC.util;

import junit.framework.TestCase;

import java.util.Vector;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;


import java.lang.Thread;


import RTMExamples.SimpleIO.ConsoleIn;
import RTMExamples.SimpleIO.ConsoleOut;
import RTMExamples.SimpleService.MyServiceConsumer;
import RTMExamples.ConfigSample.ConfigSample;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.ModuleInitProc;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.CorbaNaming;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.executionContext.ExecutionContextWorker;
import jp.go.aist.rtm.RTC.port.CorbaConsumer;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.ORBUtil;
import jp.go.aist.rtm.RTC.util.StringUtil;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.CORBA_RTCUtil;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import RTC.ConnectorProfile;
import RTC.ConnectorProfileHolder;
import RTC.ExecutionContext;
import RTC.ExecutionContextListHolder;
import RTC.ExecutionContextService;
import RTC.ExecutionContextServiceHelper;
import RTC.ExecutionContextProfile;
import RTC.LifeCycleState;
import RTC.PortService;
import RTC.PortServiceListHolder;
import RTC.RTObject;
import RTC.RTObjectHolder;
import RTC.RTObjectHelper;
import RTC.ReturnCode_t;
import OpenRTM.DataFlowComponent;
import _SDOPackage.NVListHolder;
import _SDOPackage.NameValue;

public class CORBA_RTCUtilTest extends TestCase {
    private Manager m_manager;
    private ORB m_orb;
    private CorbaConsumer<DataFlowComponent> m_conout =
            new CorbaConsumer<DataFlowComponent>(DataFlowComponent.class);
    private CorbaConsumer<DataFlowComponent> m_conin =
            new CorbaConsumer<DataFlowComponent>(DataFlowComponent.class);
    private CorbaConsumer<DataFlowComponent> m_conin2 =
            new CorbaConsumer<DataFlowComponent>(DataFlowComponent.class);
    private CorbaConsumer<DataFlowComponent> m_sercon =
            new CorbaConsumer<DataFlowComponent>(DataFlowComponent.class);
    private CorbaConsumer<DataFlowComponent> m_config =
            new CorbaConsumer<DataFlowComponent>(DataFlowComponent.class);

    private RTObjectHolder m_conoutRef
                    = new RTObjectHolder();
    private RTObjectHolder m_coninRef
                    = new RTObjectHolder();
    private RTObjectHolder m_coninRef2
                    = new RTObjectHolder();
    private RTObjectHolder m_serconRef
                    = new RTObjectHolder();
    private RTObjectHolder m_configRef
                    = new RTObjectHolder();

    //private RTObject m_conoutRef;
    private ExecutionContextListHolder m_eclisto 
                    = new ExecutionContextListHolder();
    private ExecutionContextListHolder m_eclisti 
                    = new ExecutionContextListHolder();
    private ExecutionContextListHolder m_eclistseq 
                    = new ExecutionContextListHolder();

    private RTObject_impl m_out_impl;
    private RTObject_impl m_in_impl;
    private RTObject_impl m_out_seq_impl;
    private RTObject_impl m_config_impl;
    
    public CORBA_RTCUtilTest(String name){
        super(name);

        m_eclisto = new ExecutionContextListHolder();
        m_eclisti = new ExecutionContextListHolder();
        m_eclistseq = new ExecutionContextListHolder();

        m_conoutRef = new RTObjectHolder();
        m_coninRef = new RTObjectHolder();
        m_coninRef2 = new RTObjectHolder();
        m_serconRef = new RTObjectHolder();
        m_configRef = new RTObjectHolder();

        String[] args = new String[0];
        //Manager manager = Manager.init(null);
        m_manager = Manager.init(null);
        // 
        // 
        // 
        m_manager.activateManager();
        // 
        // 
        //
        Properties prop_out = new Properties(ConsoleOut.component_conf);
        m_manager.registerFactory(prop_out, 
                new ConsoleOut(), new ConsoleOut());
        //RTObject_impl out_impl = m_manager.createComponent("ConsoleOut");
        m_out_impl = m_manager.createComponent("ConsoleOut");
        if(m_out_impl==null)
        {
            System.out.println("ConsoleOut is null.");
        }
        //
        Properties prop_in = new Properties(ConsoleIn.component_conf);
        m_manager.registerFactory(prop_in, new ConsoleIn(), new ConsoleIn());
        //RTObject_impl in_impl = m_manager.createComponent("ConsoleIn");
        m_in_impl = m_manager.createComponent("ConsoleIn");
        if(m_in_impl==null)
        {
            System.out.println("ConsoleIn is null.");
        }
        //
        Properties prop_out_seq 
            = new Properties(MyServiceConsumer.component_conf);
        m_manager.registerFactory(prop_out_seq, 
                new MyServiceConsumer(), new MyServiceConsumer());
        //RTObject_impl out_seq_impl 
        m_out_seq_impl 
            = m_manager.createComponent("MyServiceConsumer");
        if(m_out_seq_impl==null)
        {
            System.out.println("MyServiceConsumer is null.");
        }
        //
        Properties prop_config 
            = new Properties(ConfigSample.component_conf);
        m_manager.registerFactory(prop_config,
                new ConfigSample(), new ConfigSample());
        //RTObject_impl 
        m_config_impl
            = m_manager.createComponent("ConfigSample");
        if(m_config_impl==null)
        {
            System.out.println("ConfigSample is null.");
        }
        //
        //
        //
        m_manager.runManager(true);
        // 
        // 
        // 
        //ExecutionContextListHolder eclisto = new ExecutionContextListHolder();
        m_eclisto.value = new ExecutionContext[0];
        m_eclisto.value =  m_out_impl.get_owned_contexts();
        //System.out.println( "m_eclisto.value.length : "
        //        + m_eclisto.value.length);
        //
        //ExecutionContextListHolder eclisti = new ExecutionContextListHolder();
        m_eclisti.value = new ExecutionContext[0];
        m_eclisti.value =  m_in_impl.get_owned_contexts();
        //System.out.println( "m_eclisti.value.length : "+ m_eclisti.value.length);
        // 
        //ExecutionContextListHolder eclistseq = new ExecutionContextListHolder();
        m_eclistseq.value = new ExecutionContext[0];
        m_eclistseq.value =  m_out_seq_impl.get_owned_contexts();
        //System.out.println( "m_eclistseq.value.length : "+ m_eclistseq.value.length);
        //
        // bind
        //
        //System.out.println( "bind0 : "+ m_eclisto.value[0]);
        //System.out.println( "bind1 : "+ m_eclisti.value[0]);
        m_out_impl.bindContext(m_eclisti.value[0]);
        m_eclisto.value =  m_out_impl.get_owned_contexts();
        //System.out.println( "m_eclisto.value.length : "
        //        + m_eclisto.value.length);

        //System.out.println( "bind2 : "+ m_eclisto.value[0]);
        //System.out.println( "bind3 : "+ m_eclisto.value[1]);
        //
        //
        //
        //ORB orb = ORBUtil.getOrb();
        m_orb = ORBUtil.getOrb();
        CorbaNaming naming = null;
        try {
            naming = new CorbaNaming(m_orb, "localhost:2809");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //CorbaConsumer<DataFlowComponent> conout =
        //    new CorbaConsumer<DataFlowComponent>(DataFlowComponent.class);
        // find ConsoleOut0 component
        try {
            m_conout.setObject(naming.resolve(".host_cxt/ConsoleOut0.rtc"));
        } catch (NotFound e) {
            e.printStackTrace();
        } catch (CannotProceed e) {
            e.printStackTrace();
        } catch (InvalidName e) {
            e.printStackTrace();
        }

        // 
        ExecutionContextListHolder eclist = new ExecutionContextListHolder();
        eclist.value = new ExecutionContext[0];
        m_conoutRef.value = m_conout._ptr();
        eclist.value =  m_conoutRef.value.get_owned_contexts();
        //System.out.println( "eclist.value.length : "+ eclist.value.length);


        try {
            m_conin.setObject(naming.resolve(".host_cxt/ConsoleIn0.rtc"));
        } catch (NotFound e) {
            e.printStackTrace();
        } catch (CannotProceed e) {
            e.printStackTrace();
        } catch (InvalidName e) {
            e.printStackTrace();
        }
        // 
        // 
        m_coninRef.value = m_conin._ptr();
        try {
            m_conin2.setObject(naming.resolve(".host_cxt/ConsoleIn1.rtc"));
        } catch (NotFound e) {
            e.printStackTrace();
        } catch (CannotProceed e) {
            e.printStackTrace();
        } catch (InvalidName e) {
            e.printStackTrace();
        }
        // 
        // 
        m_coninRef2.value = m_conin2._ptr();


        try {
            m_sercon.setObject(naming.resolve(".host_cxt/MyServiceConsumer0.rtc"));
        } catch (NotFound e) {
            e.printStackTrace();
        } catch (CannotProceed e) {
            e.printStackTrace();
        } catch (InvalidName e) {
            e.printStackTrace();
        }

        // 
        m_serconRef.value = m_sercon._ptr();
        // 
        //
        try {
            m_config.setObject(naming.resolve(".host_cxt/ConfigSample0.rtc"));
        } catch (NotFound e) {
            e.printStackTrace();
        } catch (CannotProceed e) {
            e.printStackTrace();
        } catch (InvalidName e) {
            e.printStackTrace();
        }

        // 
        m_configRef.value = m_config._ptr();
    }


    protected void setUp() throws Exception {
        super.setUp();
        try{
            Thread.sleep(500); 
        }
        catch(InterruptedException e){
        }
    }

    protected void tearDown() throws Exception {
        super.tearDown();
/*
        for(int ic=0;ic<m_eclisto.value.length;++ic){
            m_eclisto.value[ic].stop();
        }
        for(int ic=0;ic<m_eclisti.value.length;++ic){
            m_eclisti.value[ic].stop();
        }
        for(int ic=0;ic<m_eclistseq.value.length;++ic){
            m_eclistseq.value[ic].stop();
        }
*/
/*
        if(m_manager!=null){
            m_manager.shutdown();
            m_manager = null;
        }
*/
        Thread.sleep(300);
    }

    /**
     *
     * 
     */
    public void test_get_actual_ec() {
        System.out.println( "test_get_actual_ec()" );
        ExecutionContext[] list = m_conoutRef.value.get_owned_contexts();
        ExecutionContext ec = CORBA_RTCUtil.get_actual_ec(m_conoutRef.value,0);
        {
        //System.out.println( "m_conoutRef.value.get_owned_contexts()[0]: " 
        //        +list[0]);
        }
        //System.out.println( "ec : " +ec);
        //System.out.println( "m_eclisto.value[0] : " +m_eclisto.value[0]);
        //assertTrue("test:id is 0",ec._is_equivalent(m_eclisto.value[0]));
        assertTrue("test:id is 0",ec._is_equivalent(list[0]));

        ec = CORBA_RTCUtil.get_actual_ec(m_conoutRef.value,1);
        //assertTrue("test:id is 1",ec._is_equivalent(m_eclisto.value[1]));
        assertTrue("test:id is 1",ec._is_equivalent(list[1]));

        ec = CORBA_RTCUtil.get_actual_ec(m_conoutRef.value,2);
        assertTrue("test:id is out of range",ec == null);

        ec = CORBA_RTCUtil.get_actual_ec(null,0);
        assertTrue("test:rtc is null",ec == null);
    }
    /**
     *
     * 
     */
    public void test_get_ec_id(){
        System.out.println( "test_get_ec_id()" );
        ExecutionContext[] list = m_conoutRef.value.get_owned_contexts();
/*
        System.out.println( "115 : " +list.length);
        System.out.println( "116 : " +m_eclisto.value.length);
        System.out.println( "117-0 : " +list[0]);
        System.out.println( "117-1 : " +list[1]);
        System.out.println( "118-0 : " +m_eclisto.value[0]);
        System.out.println( "118-1 : " +m_eclisto.value[1]);
        if(list[0]._is_equivalent(list[0])){
           System.out.println( "119 : " );
        }
        if(list[0]._is_equivalent(m_eclisto.value[0])){
           System.out.println( "120 : " );
        }
        if(list[0] == m_eclisto.value[0]){
           System.out.println( "130 : " );
        }
        if(list[1]._is_equivalent(m_eclisto.value[0])){
           System.out.println( "121 : " );
        }
        if(list[0]._is_equivalent(m_eclisto.value[1])){
           System.out.println( "122 : " );
        }
        if(list[1]._is_equivalent(m_eclisto.value[1])){
           System.out.println( "123 : " );
        }
        System.out.println( "140 : " );
*/
        //int id = CORBA_RTCUtil.get_ec_id(m_conoutRef.value, m_eclisto.value[0]);
        int id = CORBA_RTCUtil.get_ec_id(m_conoutRef.value, list[0]);
        //System.out.println( "id : " + id );
        assertTrue("test:id is 0.",id == 0);

        //id = CORBA_RTCUtil.get_ec_id(m_conoutRef.value, m_eclisto.value[1]);
        id = CORBA_RTCUtil.get_ec_id(m_conoutRef.value, list[1]);
        //System.out.println( "id : " + id );
        assertTrue("test:id is 1",id == 1);

        id = CORBA_RTCUtil.get_ec_id(m_conoutRef.value, null);
        //System.out.println( "id : " + id );
        assertTrue("test:list is null",id == -1);

        //id = CORBA_RTCUtil.get_ec_id(null, m_eclisto.value[1]);
        id = CORBA_RTCUtil.get_ec_id(null, list[0]);
        //System.out.println( "id : " + id );
        assertTrue("test:rtc is null",id == -1);

        id = CORBA_RTCUtil.get_ec_id(m_conoutRef.value, m_eclistseq.value[0]);
        //System.out.println( "id : " + id );
        assertTrue("test:not foud",id == -1);
    }
    /**
     *
     * 
     */
    public void test_activate_deactivate(){
        System.out.println( "test_activate_deactivate()" );
        ReturnCode_t ret = CORBA_RTCUtil.activate(null, 0);
        assertTrue(ret == ReturnCode_t.BAD_PARAMETER);

        ret = CORBA_RTCUtil.activate(m_conoutRef.value, 3);
        assertTrue(ret == ReturnCode_t.BAD_PARAMETER);

        ret = CORBA_RTCUtil.deactivate(null, 0);
        assertTrue(ret == ReturnCode_t.BAD_PARAMETER);

        ret = CORBA_RTCUtil.deactivate(m_conoutRef.value, 3);
        assertTrue(ret == ReturnCode_t.BAD_PARAMETER);

        try{
            Thread.sleep(500); 
        }
        catch(InterruptedException e){
        }

        ret = CORBA_RTCUtil.activate(m_conoutRef.value, 0);
        assertTrue(ret == ReturnCode_t.RTC_OK);

        try{
            Thread.sleep(500); 
        }
        catch(InterruptedException e){
        }

        ret = CORBA_RTCUtil.deactivate(m_conoutRef.value, 0);
        assertTrue(ret == ReturnCode_t.RTC_OK);
/*
        ret = CORBA_RTCUtil.activate(m_conoutRef.value, 1);
        assertTrue(ret == ReturnCode_t.RTC_OK);

        ret = CORBA_RTCUtil.deactivate(m_conoutRef.value, 1);
        assertTrue(ret == ReturnCode_t.RTC_OK);
*/
    }
    /**
     *
     * get_state
     *
     */
    public static String getStateString(LifeCycleState state) {
      final String st[] = {
        "CREATED_STATE",
        "INACTIVE_STATE",
        "ACTIVE_STATE",
        "ERROR_STATE"
      };
        return st[state.value()]; 
    }
    public void test_get_state(){
        LifeCycleState ret = CORBA_RTCUtil.get_state(m_conoutRef.value, 0);
        String str_ret = getStateString(ret);
        System.out.println(str_ret);
        assertTrue("test:inactive_state",str_ret.equals("INACTIVE_STATE"));
        //
        CORBA_RTCUtil.activate(m_conoutRef.value, 0);
        try{
            Thread.sleep(500); 
        }
        catch(InterruptedException e){
        }
        ret = CORBA_RTCUtil.get_state(m_conoutRef.value, 0);
        str_ret = getStateString(ret);
        System.out.println(str_ret);
        assertTrue("test:active_state",str_ret.equals("ACTIVE_STATE"));
        //
        CORBA_RTCUtil.deactivate(m_conoutRef.value, 0);
        try{
            Thread.sleep(500); 
        }
        catch(InterruptedException e){
        }
        ret = CORBA_RTCUtil.get_state(m_conoutRef.value, 0);
        str_ret = getStateString(ret);
        System.out.println(str_ret);
        assertTrue("test:inactive_state",str_ret.equals("INACTIVE_STATE"));
        //
        ret = CORBA_RTCUtil.get_state(null, 0);
        str_ret = getStateString(ret);
        System.out.println(str_ret);
        assertTrue("test:error_state",str_ret.equals("ERROR_STATE"));
        //
        ret = CORBA_RTCUtil.get_state(m_conoutRef.value, 3);
        str_ret = getStateString(ret);
        System.out.println(str_ret);
        assertTrue("test:error_state",str_ret.equals("ERROR_STATE"));
    }
    /**
     *
     * is_in_active
     *
     */
    public void test_is_in_active(){
         boolean ret;
        ret = CORBA_RTCUtil.is_in_active(m_conoutRef.value, 0);
        assertTrue("test:is",!ret);
        //
        CORBA_RTCUtil.activate(m_conoutRef.value, 0);
        try{
            Thread.sleep(500); 
        }
        catch(InterruptedException e){
        }
        ret = CORBA_RTCUtil.is_in_active(m_conoutRef.value, 0);
        assertTrue("test:is",ret);
        //
        CORBA_RTCUtil.deactivate(m_conoutRef.value, 0);
        try{
            Thread.sleep(500); 
        }
        catch(InterruptedException e){
        }
        ret = CORBA_RTCUtil.is_in_active(m_conoutRef.value, 0);
        assertTrue("test:is",!ret);
        //
        ret = CORBA_RTCUtil.is_in_active(null, 0);
        assertTrue("test:is",!ret);
        ret = CORBA_RTCUtil.is_in_active(m_conoutRef.value, 3);
        assertTrue("test:is",!ret);
    }

    /**
     *
     * get_default_rate/set_default_rate
     *
     */
    public void test_get_default_rate_set_default_rate(){

        double ret = CORBA_RTCUtil.get_default_rate(m_conoutRef.value);
        assertTrue("test:get_default_rate 1000.0 get value="+ret,ret == 1000.0);
        CORBA_RTCUtil.set_default_rate(m_conoutRef.value, 500.0);
        ret = CORBA_RTCUtil.get_default_rate(m_conoutRef.value);
        assertTrue("test:get_default_rate 500.0",ret == 500.0);
        //
        //
        //
        ret = CORBA_RTCUtil.get_default_rate(null);
        ReturnCode_t code = CORBA_RTCUtil.set_default_rate(null, 500.0);
        assertTrue("test:set_default_rate",code == ReturnCode_t.BAD_PARAMETER);
        // 
    }
    /**
     *
     * get_current_rate/set_current_rate
     *
     */
    public void test_get_current_rate_set_current_rate(){
        CORBA_RTCUtil.set_default_rate(m_conoutRef.value, 1000.0);
        double ret = CORBA_RTCUtil.get_current_rate(m_conoutRef.value,0);
        assertTrue("test:get_rate 1000.0 get value="+ret,ret == 1000.0);
        ret = CORBA_RTCUtil.get_current_rate(m_conoutRef.value,1);
        assertTrue("test:get_rate 1000.0",ret == 1000.0);
        //
        CORBA_RTCUtil.set_current_rate(m_conoutRef.value,0,500.0);
        ret = CORBA_RTCUtil.get_current_rate(m_conoutRef.value,0);
        assertTrue("test:get_rate 500.0",ret == 500.0);
        CORBA_RTCUtil.set_current_rate(m_conoutRef.value,1,500.0);
        ret = CORBA_RTCUtil.get_current_rate(m_conoutRef.value,1);
        assertTrue("test:get_rate 500.0",ret == 500.0);
        //
        //
        //
        ret = CORBA_RTCUtil.get_current_rate(null,0);
        assertTrue("test:",ret == -1.0);
        ret = CORBA_RTCUtil.get_current_rate(null,1);
        assertTrue("test:",ret == -1.0);
        ReturnCode_t code;
        code = CORBA_RTCUtil.set_current_rate(null, 0, 500.0);
        assertTrue("test:set_rate",code == ReturnCode_t.BAD_PARAMETER);
        code = CORBA_RTCUtil.set_current_rate(null, 1, 500.0);
        assertTrue("test:set_rate",code == ReturnCode_t.BAD_PARAMETER);
        //
        ret = CORBA_RTCUtil.get_current_rate(m_conoutRef.value,2);
        code = CORBA_RTCUtil.set_current_rate(m_conoutRef.value, 2, 500.0);
        assertTrue("test:set_rate",code == ReturnCode_t.BAD_PARAMETER);
  
        CORBA_RTCUtil.set_current_rate(m_conoutRef.value,0,1000.0);
        CORBA_RTCUtil.set_current_rate(m_conoutRef.value,1,1000.0);
    }
    /**
     *
     * add_rtc_to_default_ec
     * remove_rtc_to_default_ec
     * get_participants_rtc
     *
     */
    public void test_rtc_to_default_ec(){
        ReturnCode_t code;
        code =  CORBA_RTCUtil.add_rtc_to_default_ec(m_conoutRef.value, 
                m_serconRef.value);
        assertTrue("test:add_rtc",code == ReturnCode_t.RTC_OK);

        RTObject[] objs;
        objs = CORBA_RTCUtil.get_participants_rtc(m_conoutRef.value);
        //System.out.println( "length : "+ objs.length);
        assertTrue("test:get_rtc",objs.length == 1);

        code = CORBA_RTCUtil.remove_rtc_to_default_ec(m_conoutRef.value,
                m_serconRef.value);
        assertTrue("test:remove_rtc : "+code.value() ,code == ReturnCode_t.RTC_OK);

        objs = CORBA_RTCUtil.get_participants_rtc(m_conoutRef.value);
        //System.out.println( "length : "+ objs.length);
        assertTrue("test:get_rtc",objs.length == 0);

        //
        //
        //
        code =  CORBA_RTCUtil.add_rtc_to_default_ec(null, m_serconRef.value);
        assertTrue("test:add_rtc",code == ReturnCode_t.RTC_ERROR);
        code =  CORBA_RTCUtil.add_rtc_to_default_ec(m_conoutRef.value, null);
        assertTrue("test:add_rtc",code == ReturnCode_t.RTC_ERROR);
        code =  CORBA_RTCUtil.add_rtc_to_default_ec(null, null);
        assertTrue("test:add_rtc",code == ReturnCode_t.RTC_ERROR);
        //
        code = CORBA_RTCUtil.remove_rtc_to_default_ec(null, m_serconRef.value);
        assertTrue("test:remove_rtc",code == ReturnCode_t.RTC_ERROR);
        code = CORBA_RTCUtil.remove_rtc_to_default_ec(m_conoutRef.value,null);
        assertTrue("test:remove_rtc",code == ReturnCode_t.RTC_ERROR);
        code = CORBA_RTCUtil.remove_rtc_to_default_ec(null,null);
        assertTrue("test:remove_rtc",code == ReturnCode_t.RTC_ERROR);
        objs = CORBA_RTCUtil.get_participants_rtc(null);
        assertTrue("test:",objs == null);

    }
    /**
     *
     * get_port_names
     * get_inport_names
     * get_outport_names
     * get_svcport_names
     *
     */
    public void test_get_port_names(){
        Vector<String> names;
        //
        //
        //
        names = CORBA_RTCUtil.get_port_names(m_conoutRef.value);
        System.out.println( "names : "+ names.toString());
        assertTrue("test:",names.size() == 1);

        names = CORBA_RTCUtil.get_port_names(m_coninRef.value);
        System.out.println( "names : "+ names.toString());
        assertTrue("test:",names.size() == 1);

        names = CORBA_RTCUtil.get_port_names(m_serconRef.value);
        System.out.println( "names : "+ names.toString());
        assertTrue("test:",names.size() == 1);

        //
        //
        //
        names = CORBA_RTCUtil.get_inport_names(m_conoutRef.value);
        System.out.println( "names : "+ names.toString());
        assertTrue("test:",names.size() == 1);

        names = CORBA_RTCUtil.get_inport_names(m_coninRef.value);
        System.out.println( "names : "+ names.toString());
        assertTrue("test:",names.size() == 0);

        names = CORBA_RTCUtil.get_inport_names(m_serconRef.value);
        System.out.println( "names : "+ names.toString());
        assertTrue("test:",names.size() == 0);

        //
        //
        //
        names = CORBA_RTCUtil.get_outport_names(m_conoutRef.value);
        System.out.println( "names : "+ names.toString());
        assertTrue("test:",names.size() == 0);

        names = CORBA_RTCUtil.get_outport_names(m_coninRef.value);
        System.out.println( "names : "+ names.toString());
        assertTrue("test:",names.size() == 1);

        names = CORBA_RTCUtil.get_outport_names(m_serconRef.value);
        System.out.println( "names : "+ names.toString());
        assertTrue("test:",names.size() == 0);

        //
        //
        //
        names = CORBA_RTCUtil.get_svcport_names(m_conoutRef.value);
        System.out.println( "names : "+ names.toString());
        assertTrue("test:",names.size() == 0);

        names = CORBA_RTCUtil.get_svcport_names(m_coninRef.value);
        System.out.println( "names : "+ names.toString());
        assertTrue("test:",names.size() == 0);

        names = CORBA_RTCUtil.get_svcport_names(m_serconRef.value);
        System.out.println( "names : "+ names.toString());
        assertTrue("test:",names.size() == 1);


        names = CORBA_RTCUtil.get_port_names(null);
        assertTrue("test:",names == null);
        names = CORBA_RTCUtil.get_inport_names(null);
        assertTrue("test:",names == null);
        names = CORBA_RTCUtil.get_outport_names(null);
        assertTrue("test:",names == null);
        names = CORBA_RTCUtil.get_svcport_names(null);
        assertTrue("test:",names == null);
    }
    /**
     *
     * get_port_by_name
     *
     */
    public void test_get_port_by_name(){
        PortService ps;
        ps = CORBA_RTCUtil.get_port_by_name(m_conoutRef.value, 
                 "ConsoleOut0.in");
        System.out.println( "name : "+ ps.get_port_profile().name);
        assertTrue("test:",ps.get_port_profile().name.equals("ConsoleOut0.in"));
        
        ps = CORBA_RTCUtil.get_port_by_name(null,
                 "ConsoleOut0.in");
        assertTrue("test:",ps == null);

        ps = CORBA_RTCUtil.get_port_by_name(m_conoutRef.value, "");
        assertTrue("test:",ps == null);
    }
    /**
     *
     * connect
     *
     */
    public void test_connect(){

        PortService port1 = CORBA_RTCUtil.get_port_by_name(m_conoutRef.value, 
                 "ConsoleOut0.in");
        PortService port2 = CORBA_RTCUtil.get_port_by_name(m_coninRef.value, 
                 "ConsoleIn0.out");
        Properties prop = new Properties();
        String[] conprop = {
            "dataport.interface_type","corba_cdr",
            "dataport.dataflow_type", "push",
            ""
        };
        
        prop.setDefaults(conprop);
        ReturnCode_t code;
        code = CORBA_RTCUtil.connect("kamo0",prop,port1,port2);
        assertTrue("test:connect",code == ReturnCode_t.RTC_OK);
/*
        try{
            Thread.sleep(10000); 
        }
        catch(InterruptedException e){
        }
*/
        //
        //
        //
        code = CORBA_RTCUtil.connect("",prop,port1,port2);
        assertTrue("test:disconnect",code == ReturnCode_t.RTC_OK);
        code = CORBA_RTCUtil.connect("kamo1",null,port1,port2);
        assertTrue("test:disconnect",code == ReturnCode_t.RTC_OK);
        code = CORBA_RTCUtil.connect("kamo2",prop,null,port2);
        assertTrue("test:",code == ReturnCode_t.BAD_PARAMETER);
        code = CORBA_RTCUtil.connect("kamo3",prop,port1,null);
        assertTrue("test:",code == ReturnCode_t.BAD_PARAMETER);
 
        code = CORBA_RTCUtil.disconnect_by_connector_name(port1, "kamo0");
        assertTrue("test:disconnect",code == ReturnCode_t.RTC_OK);
/*
        try{
            Thread.sleep(10000); 
        }
        catch(InterruptedException e){
        }
*/
        code = CORBA_RTCUtil.disconnect_by_connector_name(null, "kamo0");
        assertTrue("test:",code == ReturnCode_t.BAD_PARAMETER);
        code = CORBA_RTCUtil.disconnect_by_connector_name(port1, "kamo5");
        assertTrue("test:"+code.value(),code == ReturnCode_t.BAD_PARAMETER);
    
    }
    /**
     *
     * connect
     *
     */
    public void test_connect_by_name(){

        PortService port1 = CORBA_RTCUtil.get_port_by_name(m_conoutRef.value, 
                 "ConsoleOut0.in");
        PortService port2 = CORBA_RTCUtil.get_port_by_name(m_coninRef.value, 
                 "ConsoleIn0.out");
        Properties prop = new Properties();
        String[] conprop = {
            "dataport.interface_type","corba_cdr",
            "dataport.dataflow_type", "push",
            ""
        };
        
        prop.setDefaults(conprop);
        ReturnCode_t code;
        code = CORBA_RTCUtil.connect_by_name("kamo0",prop,
                m_conoutRef.value,"ConsoleOut0.in",
                m_coninRef.value,"ConsoleIn0.out");
        assertTrue("test:connect",code == ReturnCode_t.RTC_OK);
/*
        try{
            Thread.sleep(10000); 
        }
        catch(InterruptedException e){
        }
*/
        //
        //
        //
        code = CORBA_RTCUtil.connect_by_name("",prop,
                m_conoutRef.value,"ConsoleOut0.in",
                m_coninRef.value,"ConsoleIn0.out");
        assertTrue("test:disconnect",code == ReturnCode_t.RTC_OK);
        code = CORBA_RTCUtil.connect_by_name("kamo1",null,
                m_conoutRef.value,"ConsoleOut0.in",
                m_coninRef.value,"ConsoleIn0.out");
        assertTrue("test:disconnect",code == ReturnCode_t.RTC_OK);
        code = CORBA_RTCUtil.connect_by_name("kamo2",prop,
                null,"ConsoleOut0.in",
                m_coninRef.value,"ConsoleIn0.out");
        assertTrue("test:",code == ReturnCode_t.BAD_PARAMETER);
        code = CORBA_RTCUtil.connect_by_name("kamo3",prop,
                m_conoutRef.value,"ConsoleOut0.in",
                m_coninRef.value,"");
        assertTrue("test:",code == ReturnCode_t.BAD_PARAMETER);
        
        
        ConnectorProfile[] cprofs = port1.get_connector_profiles();
        for(int ic=0;ic<cprofs.length;++ic){
            if(cprofs[ic].name.equals("kamo0")){
                code = CORBA_RTCUtil.disconnect(cprofs[ic]);
                break;
            }
        }
        
        assertTrue("test:disconnect "+code.value(),code == ReturnCode_t.RTC_OK);
/*
        try{
            Thread.sleep(10000); 
        }
        catch(InterruptedException e){
        }
*/
        code = CORBA_RTCUtil.disconnect(null);
        assertTrue("test:",code == ReturnCode_t.BAD_PARAMETER);
    }
    /**
     *
     * disconnect
     *
     */
    public void test_disconnect_by_id(){

        PortService port1 = CORBA_RTCUtil.get_port_by_name(m_conoutRef.value, 
                 "ConsoleOut0.in");
        PortService port2 = CORBA_RTCUtil.get_port_by_name(m_coninRef.value, 
                 "ConsoleIn0.out");
        Properties prop = new Properties();
        String[] conprop = {
            "dataport.interface_type","corba_cdr",
            "dataport.dataflow_type", "push",
            ""
        };
        
        prop.setDefaults(conprop);
        ReturnCode_t code;
        code = CORBA_RTCUtil.connect("kamo0",prop,port1,port2);
        assertTrue("test:connect",code == ReturnCode_t.RTC_OK);
        //
        //
        //
        String id = new String(); 
        ConnectorProfile[] cprofs = port1.get_connector_profiles();
        for(int ic=0;ic<cprofs.length;++ic){
            if(cprofs[ic].name.equals("kamo0")){
                id = cprofs[ic].connector_id;
                break;
            }
        }
        code = CORBA_RTCUtil.disconnect_by_connector_id(port1,id);
        assertTrue("test:disconnect",code == ReturnCode_t.RTC_OK);



        code = CORBA_RTCUtil.disconnect_by_connector_id(null,id);
        assertTrue("test:",code == ReturnCode_t.BAD_PARAMETER);
        code = CORBA_RTCUtil.disconnect_by_connector_id(port1,"");
        assertTrue("test:"+code.value(),code == ReturnCode_t.BAD_PARAMETER);
    
    }
    /**
     *
     * disconnect
     *
     */
    public void test_disconnect_by_port_name(){
        PortService port1 = CORBA_RTCUtil.get_port_by_name(m_conoutRef.value, 
                 "ConsoleOut0.in");
        PortService port2 = CORBA_RTCUtil.get_port_by_name(m_coninRef.value, 
                 "ConsoleIn0.out");
        Properties prop = new Properties();
        String[] conprop = {
            "dataport.interface_type","corba_cdr",
            "dataport.dataflow_type", "push",
            ""
        };
        
        prop.setDefaults(conprop);
        ReturnCode_t code;
        code = CORBA_RTCUtil.connect("kamo0",prop,port1,port2);
        assertTrue("test:connect",code == ReturnCode_t.RTC_OK);




        code = CORBA_RTCUtil.disconnect_by_port_name(port1,"ConsoleIn0.out");
        assertTrue("test:disconnect",code == ReturnCode_t.RTC_OK);

        code = CORBA_RTCUtil.disconnect_by_port_name(port1,"");
        assertTrue("test:",code == ReturnCode_t.BAD_PARAMETER);
        code = CORBA_RTCUtil.disconnect_by_port_name(null,"ConsoleIn0.out");
        assertTrue("test:",code == ReturnCode_t.BAD_PARAMETER);

    }
    /**
     *
     * get_parameter_by_key
     * get_current_configuration_name
     * get_active_configuration
     * set_configuration
     */
    public void test_get_parameter_by_key(){
        String str;
        str = CORBA_RTCUtil.get_parameter_by_key(m_configRef.value,
                "default","double_param0");
        assertTrue("test:",str.equals("0.11"));
    
        str = CORBA_RTCUtil.get_parameter_by_key(m_configRef.value,
                "","double_param0");
        assertTrue("test:",str.equals(""));
        str = CORBA_RTCUtil.get_parameter_by_key(m_configRef.value,
                "default","");
        assertTrue("test:",str.equals(""));



        str = CORBA_RTCUtil.get_current_configuration_name(m_configRef.value);
        assertTrue("test:",str.equals("default"));
        str = CORBA_RTCUtil.get_current_configuration_name(null);
        assertTrue("test:",str.equals(""));



        Properties prop 
            = CORBA_RTCUtil.get_active_configuration(m_configRef.value);
        str = prop.getProperty("double_param0");
        assertTrue("test:",str.equals("0.11"));
        prop = CORBA_RTCUtil.get_active_configuration(null);
        assertTrue("test:",prop==null);
    
        boolean bool = CORBA_RTCUtil.set_configuration(m_configRef.value, 
                "default", "double_param0","305.8560");
        assertTrue("test:",bool);
        str = CORBA_RTCUtil.get_parameter_by_key(m_configRef.value,
                "default","double_param0");
        assertTrue("test:",str.equals("305.8560"));
        //
        bool = CORBA_RTCUtil.set_configuration(null, 
                "default", "double_param0","305.8560");
        assertTrue("test:",!bool);
        bool = CORBA_RTCUtil.set_configuration(m_configRef.value, 
                "", "double_param0","305.8560");
        assertTrue("test:",!bool);
        bool = CORBA_RTCUtil.set_configuration(m_configRef.value, 
                "default", "double_param2","123.456");
        assertTrue("test:",bool);
        str = CORBA_RTCUtil.get_parameter_by_key(m_configRef.value,
                "default","double_param2");
        assertTrue("test:",str.equals("123.456"));


    }
    /**
     *
     * connect
     *
     */
    public void test_connect_multi(){

        PortService port1 = CORBA_RTCUtil.get_port_by_name(m_conoutRef.value, 
                 "ConsoleOut0.in");
        PortService port2 = CORBA_RTCUtil.get_port_by_name(m_coninRef.value, 
                 "ConsoleIn0.out");
        PortService port3 = CORBA_RTCUtil.get_port_by_name(m_coninRef2.value, 
                 "ConsoleIn1.out");
        Properties prop = new Properties();
        String[] conprop = {
            "dataport.interface_type","corba_cdr",
            "dataport.dataflow_type", "push",
            ""
        };
        PortServiceListHolder target_ports = new PortServiceListHolder();
        target_ports.value = new PortService[2];
        target_ports.value[0] = port2;
        target_ports.value[1] = port3;
        prop.setDefaults(conprop);
        ReturnCode_t code;
        code = CORBA_RTCUtil.connect_multi("kamo10", prop, 
                    port1,  target_ports);
        assertTrue("test:",code == ReturnCode_t.RTC_OK);
/*
        try{
            Thread.sleep(30000); 
        }
        catch(InterruptedException e){
        }
*/
        code = CORBA_RTCUtil.connect_multi("kamo11", prop, 
                    null,  target_ports);
        assertTrue("test:",code == ReturnCode_t.BAD_PARAMETER);
        code = CORBA_RTCUtil.connect_multi("kamo11", prop, 
                    port1,  null);
        assertTrue("test:",code == ReturnCode_t.BAD_PARAMETER);
        PortServiceListHolder error_ports = new PortServiceListHolder();
        code = CORBA_RTCUtil.connect_multi("kamo11", prop, 
                    port1, error_ports );
        assertTrue("test:",code == ReturnCode_t.BAD_PARAMETER);
    }
}
  

