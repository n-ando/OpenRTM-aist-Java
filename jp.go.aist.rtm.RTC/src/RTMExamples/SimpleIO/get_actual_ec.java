package RTMExamples.SimpleIO;

import java.lang.Thread;


import RTMExamples.SimpleService.MyServiceConsumer;

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
import jp.go.aist.rtm.RTC.util.RTShellUtil;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import RTC.ConnectorProfile;
import RTC.ConnectorProfileHolder;
import OpenRTM.DataFlowComponent;
import RTC.ExecutionContext;
import RTC.ExecutionContextListHolder;
import RTC.LifeCycleState;
import RTC.PortService;
import RTC.PortServiceListHolder;
import RTC.RTObject;
import RTC.RTObjectHelper;
import RTC.ReturnCode_t;
import _SDOPackage.NVListHolder;
import _SDOPackage.NameValue;

public class get_actual_ec {

    public static void main(String[] args) {
        
        final Manager manager = Manager.init(args);

        // 
        // 
        // 
        manager.activateManager();
        // 
        // 
        //
        Properties prop_out = new Properties(ConsoleOut.component_conf);
        manager.registerFactory(prop_out, new ConsoleOut(), new ConsoleOut());
        RTObject_impl out_impl = manager.createComponent("ConsoleOut");
        if(out_impl==null)
        {
            System.out.println("ConsoleOut is null.");
            return;
        }
        //
        Properties prop_in = new Properties(ConsoleIn.component_conf);
        manager.registerFactory(prop_in, new ConsoleIn(), new ConsoleIn());
        RTObject_impl in_impl = manager.createComponent("ConsoleIn");
        if(in_impl==null)
        {
            System.out.println("ConsoleIn is null.");
            return;
        }
        //
        Properties prop_out_seq = new Properties(MyServiceConsumer.component_conf);
        manager.registerFactory(prop_out_seq, 
                new MyServiceConsumer(), new MyServiceConsumer());
        RTObject_impl out_seq_impl 
            = manager.createComponent("MyServiceConsumer");
        if(out_seq_impl==null)
        {
            System.out.println("MyServiceConsumer is null.");
            return;
        }
        //
        //
        //
        manager.runManager(true);
        // 
        // 
        // 
        ExecutionContextListHolder eclisto = new ExecutionContextListHolder();
        eclisto.value = new ExecutionContext[0];
        eclisto.value =  out_impl.get_owned_contexts();
        System.out.println( "eclisto.value.length : "+ eclisto.value.length);
        //
        ExecutionContextListHolder eclisti = new ExecutionContextListHolder();
        eclisti.value = new ExecutionContext[0];
        eclisti.value =  in_impl.get_owned_contexts();
        System.out.println( "eclisti.value.length : "+ eclisti.value.length);
        // 
        ExecutionContextListHolder eclistseq = new ExecutionContextListHolder();
        eclistseq.value = new ExecutionContext[0];
        eclistseq.value =  out_seq_impl.get_owned_contexts();
        System.out.println( "eclistseq.value.length : "+ eclistseq.value.length);
        //
        // bind
        //
        out_impl.bindContext(eclisti.value[0]);
        eclisto.value =  out_impl.get_owned_contexts();
        System.out.println( "eclisto.value.length : "+ eclisto.value.length);

        ORB orb = ORBUtil.getOrb(args);
        CorbaNaming naming = null;
        try {
            naming = new CorbaNaming(orb, "localhost:2809");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        CorbaConsumer<DataFlowComponent> conout =
            new CorbaConsumer<DataFlowComponent>(DataFlowComponent.class);
        // find ConsoleOut0 component
        try {
            conout.setObject(naming.resolve(".host_cxt/ConsoleOut0.rtc"));
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
        RTObject conoutRef = conout._ptr();
        eclist.value =  conoutRef.get_owned_contexts();
        System.out.println( "eclist.value.length : "+ eclist.value.length);


        //
        // get_actual_ec
        //
        System.out.println( "-get_actual_ec");
        ExecutionContext ec = RTShellUtil.get_actual_ec(conoutRef,0);
        assert ec.equals(eclisto.value[0]);
        if(ec.equals(eclisto.value[0])) {
             System.out.println( "OK");
        }
        else{
             System.out.println( "NG");
        }

        ec = RTShellUtil.get_actual_ec(conoutRef,1);
        assert ec.equals(eclisto.value[1]);
        if(ec.equals(eclisto.value[1])) {
             System.out.println( "OK");
        }
        else{
             System.out.println( "NG");
        }
        ec = RTShellUtil.get_actual_ec(conoutRef,2);
        assert ec == null;
        if(ec==null) {
             System.out.println( "OK");
        }
        else{
             System.out.println( "NG");
        }

        //
        // get_ec_id
        //
        System.out.println( "-get_ec_id");
        int id = RTShellUtil.get_ec_id(conoutRef, eclisto.value[0]);
        System.out.println( "id : " + id );
        assert id == 0;
        if(id == 0){
             System.out.println( "OK");
        }
        else{
             System.out.println( "NG");
        }
        id = RTShellUtil.get_ec_id(conoutRef, eclisto.value[1]);
        System.out.println( "id : " + id );
        assert id == 1;
        if(id == 1){
             System.out.println( "OK");
        }
        else{
             System.out.println( "NG");
        }
        id = RTShellUtil.get_ec_id(conoutRef, null);
        System.out.println( "id : " + id );
        assert id == -1;
        if(id == -1){
             System.out.println( "OK");
        }
        else{
             System.out.println( "NG");
        }
        id = RTShellUtil.get_ec_id(null, eclisto.value[1]);
        System.out.println( "id : " + id );
        assert id == -1;
        if(id == -1){
             System.out.println( "OK");
        }
        else{
             System.out.println( "NG");
        }
        id = RTShellUtil.get_ec_id(conoutRef, eclistseq.value[0]);
        System.out.println( "id : " + id );
        assert id == -1;
        if(id == -1){
             System.out.println( "OK");
        }
        else{
             System.out.println( "NG");
        }
        //
        // activate
        //
        {
        System.out.println( "-activate/deactivate");
        ReturnCode_t ret = RTShellUtil.activate(null, 0);
        assert ret == ReturnCode_t.BAD_PARAMETER;
        if(ret == ReturnCode_t.BAD_PARAMETER){
             System.out.println( "OK");
        }
        else{
             System.out.println( "NG");
        }
        ret = RTShellUtil.activate(conoutRef, 3);
        assert ret == ReturnCode_t.BAD_PARAMETER;
        if(ret == ReturnCode_t.BAD_PARAMETER){
             System.out.println( "OK");
        }
        else{
             System.out.println( "NG");
        }
        ret = RTShellUtil.deactivate(null, 0);
        assert ret == ReturnCode_t.BAD_PARAMETER;
        if(ret == ReturnCode_t.BAD_PARAMETER){
             System.out.println( "OK");
        }
        else{
             System.out.println( "NG");
        }
        ret = RTShellUtil.deactivate(conoutRef, 3);
        assert ret == ReturnCode_t.BAD_PARAMETER;
        if(ret == ReturnCode_t.BAD_PARAMETER){
             System.out.println( "OK");
        }
        else{
             System.out.println( "NG");
        }
        ret = RTShellUtil.activate(conoutRef, 0);
        assert ret == ReturnCode_t.RTC_OK;
        if(ret == ReturnCode_t.RTC_OK){
             System.out.println( "OK");
        }
        else{
             System.out.println( "NG");
        }
        ret = RTShellUtil.deactivate(conoutRef, 0);
        assert ret == ReturnCode_t.RTC_OK;
        if(ret == ReturnCode_t.RTC_OK){
             System.out.println( "OK");
        }
        else{
             System.out.println( "NG");
        }
/*
        ret = RTShellUtil.activate(conoutRef, 1);
        assert ret == ReturnCode_t.RTC_OK;
        if(ret == ReturnCode_t.RTC_OK){
             System.out.println( "OK");
        }
        else{
             System.out.println( "NG");
        }
        ret = RTShellUtil.deactivate(conoutRef, 1);
        assert ret == ReturnCode_t.RTC_OK;
        if(ret == ReturnCode_t.RTC_OK){
             System.out.println( "OK");
        }
        else{
             System.out.println( "NG");
        }
*/

        }
        //
        // get_state
        //
        //"CREATED_STATE",
        //"INACTIVE_STATE",
        //"ACTIVE_STATE",
        //"ERROR_STATE"
        {
        System.out.println( "-get_state");
        // 
        try{
            Thread.sleep(500); 
        }
        catch(InterruptedException e){
        }


        LifeCycleState ret = RTShellUtil.get_state(conoutRef, 0);
        String str_ret = getStateString(ret);
        System.out.println(str_ret);
        assert str_ret.equals("INACTIVE_STATE");
        if(str_ret.equals("INACTIVE_STATE")){
             System.out.println( "OK");
        }
        else{
             System.out.println( "NG");
        }
        //
        RTShellUtil.activate(conoutRef, 0);
        try{
            Thread.sleep(500); 
        }
        catch(InterruptedException e){
        }
        ret = RTShellUtil.get_state(conoutRef, 0);
        str_ret = getStateString(ret);
        System.out.println(str_ret);
        assert str_ret.equals("ACTIVE_STATE");
        if(str_ret.equals("ACTIVE_STATE")){
             System.out.println( "OK");
        }
        else{
             System.out.println( "NG");
        }
        //
        RTShellUtil.deactivate(conoutRef, 0);
        try{
            Thread.sleep(500); 
        }
        catch(InterruptedException e){
        }
        ret = RTShellUtil.get_state(conoutRef, 0);
        str_ret = getStateString(ret);
        assert str_ret.equals("INACTIVE_STATE");
        if(str_ret.equals("INACTIVE_STATE")){
             System.out.println( "OK");
        }
        else{
             System.out.println( "NG");
        }
        System.out.println(str_ret);
        //
        ret = RTShellUtil.get_state(null, 0);
        str_ret = getStateString(ret);
        assert str_ret.equals("ERROR_STATE");
        if(str_ret.equals("ERROR_STATE")){
             System.out.println( "OK");
        }
        else{
             System.out.println( "NG");
        }
        System.out.println(str_ret);
        //
        ret = RTShellUtil.get_state(conoutRef, 3);
        str_ret = getStateString(ret);
        assert str_ret.equals("ERROR_STATE");
        if(str_ret.equals("ERROR_STATE")){
             System.out.println( "OK");
        }
        else{
             System.out.println( "NG");
        }
        System.out.println(str_ret);

        }
        //
        // is_in_active
        //
        {
        System.out.println( "-is_in_active");
        if(!RTShellUtil.is_in_active(conoutRef, 0)){
             System.out.println( "OK");
        }
        else{
             System.out.println( "NG");
        }
        //
        RTShellUtil.activate(conoutRef, 0);
        try{
            Thread.sleep(500); 
        }
        catch(InterruptedException e){
        }
        if(RTShellUtil.is_in_active(conoutRef, 0)){
             System.out.println( "OK");
        }
        else{
             System.out.println( "NG");
        }
        //
        RTShellUtil.deactivate(conoutRef, 0);
        try{
            Thread.sleep(500); 
        }
        catch(InterruptedException e){
        }
        if(!RTShellUtil.is_in_active(conoutRef, 0)){
             System.out.println( "OK");
        }
        else{
             System.out.println( "NG");
        }
        //
        if(!RTShellUtil.is_in_active(null, 0)){
             System.out.println( "OK");
        }
        else{
             System.out.println( "NG");
        }
        if(!RTShellUtil.is_in_active(conoutRef, 3)){
             System.out.println( "OK");
        }
        else{
             System.out.println( "NG");
        }

        }
        //
        // get_default_rate/set_default_rate
        //
        {

        System.out.println( "-get_default_rate/set_default_rate");
        double ret = RTShellUtil.get_default_rate(conoutRef);
        if(ret == 1000.0){
             System.out.println( "OK");
        }
        else{
             System.out.println( "NG");
        }
        RTShellUtil.set_default_rate(conoutRef, 500.0);
        ret = RTShellUtil.get_default_rate(conoutRef);
        if(ret == 500.0){
             System.out.println( "OK");
        }
        else{
             System.out.println( "NG");
        }
        ret = RTShellUtil.get_default_rate(null);
        ReturnCode_t code = RTShellUtil.set_default_rate(null, 500.0);
        if(code == ReturnCode_t.BAD_PARAMETER){
             System.out.println( "OK");
        }
        else{
             System.out.println( "NG");
        }
 

        }
    }
    public static String getStateString(LifeCycleState state) {
      final String st[] = {
        "CREATED_STATE",
        "INACTIVE_STATE",
        "ACTIVE_STATE",
        "ERROR_STATE"
      };
        return st[state.value()]; 
    }
}

