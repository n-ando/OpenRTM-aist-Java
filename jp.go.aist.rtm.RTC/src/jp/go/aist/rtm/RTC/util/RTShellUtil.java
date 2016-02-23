package jp.go.aist.rtm.RTC.util;

import RTC.RTObject;
/*
import java.util.Vector;

import org.omg.CORBA.ORB;

import RTC.ComponentProfile;
import RTC.ComponentProfileListHolder;
import RTC.ConnectorProfile;
import RTC.ConnectorProfileListHolder;
import RTC.ExecutionContext;
import RTC.ExecutionContextListHolder;
import RTC.ExecutionContextService;
import RTC.ExecutionContextServiceListHolder;
import RTC.PortService;
import RTC.PortInterfaceProfile;
import RTC.PortInterfaceProfileListHolder;
import RTC.PortProfile;
import RTC.PortProfileListHolder;
import RTC.PortServiceListHolder;
import RTC.RTCListHolder;
import _SDOPackage.NVListHolder;
import _SDOPackage.NameValue;
import _SDOPackage.Organization;
import _SDOPackage.OrganizationListHolder;
import _SDOPackage.SDO;
import _SDOPackage.SDOListHolder;
import _SDOPackage.ServiceProfile;
import _SDOPackage.ServiceProfileListHolder;
*/


/**
 * {@.ja コンポーネントの各種操作ユーティリティクラス。}
 * {@.en  }
 * <p>
 * {@.ja Rtshellライクにコンポーネントの各種操作を
 * 簡単に行うことができる関数セット}
 * {@.en  }
 *
 */
public class RTShellUtil {
    /**
     * {@.ja CORBA sequence に対して functor を適用する。}
     * {@.en Apply the functor to all CORBA sequence elements}
     *
     * <p>
     * {@.ja 指定されたシーケンス内の各NameValueオブジェクト対して、順次、
     * 指定された操作を行う。}
     * {@.en Apply the given functor to the given CORBA sequence.}
     * 
     * @param seq 
     *   {@.ja NameValueオブジェクトシーケンスを
     *   内部に保持するNVListHolderオブジェクト}
     *   {@.en NVListHolder object that hold NameValue object sequence 
     *   internally.}
     * @param func 
     *   {@.ja 各NameValueオブジェクトに適用するoperatorFuncオブジェクト}
     *   {@.en OperatorFunc object applied to each NameValue object}
     * @return 
     *   {@.ja 引数で指定されたoperatorFuncオブジェクト}
     *   {@.en OperatorFunc object specified by argument}
     */
/*
    public static operatorFunc for_each(NVListHolder seq, operatorFunc func) {
        if( seq.value == null ) return null;
        for (int i = 0; i < seq.value.length; ++i) {
            func.operator(seq.value[i]);
        }
        
        return func;
    }
*/
    /**
     *
     * {@.ja コンポーネントのプロパティ取得}
     * {@.en  }
     *
     * 
     * @param rtc 
     *   {@.ja RTコンポーネント}
     *   {@.en  }
     *
     * @return 
     *   {@.ja コンポーネントのプロパティ}
     *   {@.en  }
     *
     * coil::Properties get_component_profile(const RTC::RTObject_ptr rtc)
     */
/*
public get_component_profile(rtc):
  prop = OpenRTM_aist.Properties()
  if CORBA.is_nil(rtc):
    return prop
  prof = rtc.get_component_profile()
  OpenRTM_aist.NVUtil.copyToProperties(prop, prof.properties)
  return prop
*/



    /**
     * {@.ja コンポーネントの生存を確認}
     * {@.en Check if the component is alive}
     * 
     * @param rtc
     *   {@.ja RTコンポーネント}
     *   {@.ja RTComponent}
     *
     * @return 
     *   {@.ja true:生存している, false:生存していない}
     *   {@.en true: alive, false:non not alive}
     *
     */
    public boolean is_existing(RTObject rtc) {
        try{
            if (rtc._non_existent()) { 
                return false; 
            }
            return true;
        }
        catch (Exception ex) {
            return true;
        }
    }
    /**
     *
     * {@.ja RTCがデフォルトの実行コンテキストでalive状態かを判定する}
     *
     * @param rtc
     *   {@.ja RTコンポーネント}
     *
     * @return 
     *   {@.ja true:alive状態}
     * 
     */
/*
    public boolean is_alive_in_default_ec(rtc){
        ec = get_actual_ec(rtc)
        if  CORBA.is_nil(ec) {
            return false;
        }
        return rtc.is_alive(ec)
    }

*/
    /**
     * {@.ja RTコンポーネントに関連付けした実行コンテキストから指定したIDの実行コンテキストを取得}
     * 
     * @param rtc 
     *   {@.ja 対象のRTコンポーネント}
     *
     * @param ec_id 
     *   {@.ja 実行コンテキストのID}
     *
     * @return 
     *   {@.ja 実行コンテキストのオブジェクトリファレンス}
     *
     * RTC::ExecutionContext_var get_actual_ec(const RTC::RTObject_ptr rtc,RTC::UniqueId ec_id = 0)
     */
/*
    public get_actual_ec(rtc, ec_id=0) {
        if ec_id < 0{
            return RTC.ExecutionContext._nil
        }
        if CORBA.is_nil(rtc){
            return RTC.ExecutionContext._nil
        }
        if ec_id < 1000{
            eclist = rtc.get_owned_contexts()
            if ec_id >= len(eclist):
                return RTC.ExecutionContext._nil
            }
            if CORBA.is_nil(eclist[ec_id]):
                return RTC.ExecutionContext._nil
            }
            return eclist[ec_id]
        }
        elif ec_id >= 1000r{
            pec_id = ec_id - 1000
            eclist = rtc.get_participating_contexts()
            if pec_id >= len(eclist):
                return RTC.ExecutionContext._nil
            }
            if CORBA.is_nil(eclist[pec_id]):
                return RTC.ExecutionContext._nil
            }
            return eclist[pec_id]
        }
        return RTC.ExecutionContext._nil
    }    
*/

    /**
     *
     * {@.ja 対象のRTコンポーネントから指定した実行コンテキストのIDを取得する}
     *
     * 
     * @param rtc 
     *   {@.ja 対象のRTコンポーネント}
     *
     * @param ec 
     *   {@.ja 実行コンテキスト}
     *
     * @return 
     *   {@.ja 実行コンテキストのID 指定した実行コンテキストがRTコンポーネントに関連付けられていなかった場合は-1を返す}
     *
     */
/*
    public get_ec_id(rtc, ec):
        if CORBA.is_nil(rtc){
            return -1
        }
        eclist_own = rtc.get_owned_contexts()
  
        count = 0
        for e in eclist_own{
            if not CORBA.is_nil(e){
                if e._is_equivalent(ec){
                      return count
                }
                count += 1
            }
        }
        eclist_pec = rtc.get_participating_contexts()
        count = 0
        for e in eclist_pec{
            if not CORBA.is_nil(e){
                if e._is_equivalent(ec){
                    return count+1000
                }
                count += 1
            }
        }
        return -1
    }
*/
    /**
     *
     * {@.ja RTCを指定した実行コンテキストでアクティベーションする}
     *
     * 
     * @param rtc
     *   {@.ja 対象のRTコンポーネント}
     *
     * @param ec_id 
     *   {@.ja 実行コンテキストのID}
     *
     * @return 
     *   {@.ja RTC、ECのオブジェクトリファレンスがnilの場合はBAD_PARAMETERを返す
     * nilではない場合はactivate_component関数の戻り値を返す。RTC_OKの場合はアクティベーションが成功}
     }
     * RTC::ReturnCode_t activate(RTC::RTObject_ptr rtc, RTC::UniqueId ec_id = 0)
     */
/*
    public activate(rtc, ec_id=0){
        if CORBA.is_nil(rtc){
          return RTC.BAD_PARAMETER
        }
        ec = get_actual_ec(rtc, ec_id)
        if CORBA.is_nil(ec){
            return RTC.BAD_PARAMETER
        }
        return ec.activate_component(rtc)
  
    }
*/
    /**
     *
     * {@.ja RTCを指定した実行コンテキストで非アクティベーションする}
     * 
     * @param rtc
     *   {@.ja 対象のRTコンポーネント}
     *
     * @param ec_id 
     *   {@.ja 実行コンテキストのID}
     *
     * @return 
     *   {@.ja RTC、ECのオブジェクトリファレンスがnilの場合はBAD_PARAMETERを返す
     * nilではない場合はdeactivate_component関数の戻り値を返す。RTC_OKの場合は非アクティベーションが成功}
     * RTC::ReturnCode_t deactivate(RTC::RTObject_ptr rtc, RTC::UniqueId ec_id = 0)
     */
/*
    public deactivate(rtc, ec_id=0){

        if CORBA.is_nil(rtc){
            return RTC.BAD_PARAMETER
        }
        ec = get_actual_ec(rtc, ec_id)
        if CORBA.is_nil(ec){
            return RTC.BAD_PARAMETER
        }
        return ec.deactivate_component(rtc)
    }
*/
    /**
     *
     * {@.ja RTCを指定した実行コンテキストでリセットする}
     * 
     * @param rtc
     *   {@.ja 対象のRTコンポーネント}
     *
     * @param ec_id 
     *   {@.ja 実行コンテキストのID}
     *
     * @return 
     *   {@.ja RTC、ECのオブジェクトリファレンスがnilの場合はBAD_PARAMETERを返す
     * nilではない場合はdeactivate_component関数の戻り値を返す。RTC_OKの場合はリセットが成功}
     *
     * RTC::ReturnCode_t reset(RTC::RTObject_ptr rtc, RTC::UniqueId ec_id = 0)
     */
/*
    public reset(rtc, ec_id=0){
        if CORBA.is_nil(rtc){
             return RTC.BAD_PARAMETER
        }
        ec = get_actual_ec(rtc, ec_id)
        if CORBA.is_nil(ec){
            return RTC.BAD_PARAMETER
        }
        return ec.reset_component(rtc)
    }
*/
    /**
     *
     * {@.ja 対象のRTコンポーネントの指定した実行コンテキストでの状態を取得}
     * 
     * @param rtc
     *   {@.ja 対象のRTコンポーネント}
     *
     * @param ec_id 
     *   {@.ja 実行コンテキストのID}
     *
     * @param ret 
     *   {@.ja RTCの状態}
     *
     * @return 
     *   {@.ja rtc、ecがnilの場合はfalseを返す。
     *         nilではない場合はret[0]に状態を代入してtrueを返す。}
     *
     *
     */
/*
    public get_state(rtc, ec_id=0, ret=[None]){
        if CORBA.is_nil(rtc){
            return false
        }
        ec = get_actual_ec(rtc, ec_id)
        if CORBA.is_nil(ec){
            return false
        }
        ret[0] = ec.get_component_state(rtc)
        return true
    }
*/
    /**
     *
     * {@.ja 対象のRTコンポーネントの指定した実行コンテキストでINACTIVE状態かどうか判定}
     *
     * 
     * @param rtc
     *   {@.ja 対象のRTコンポーネント}
     *
     * @param ec_id 
     *   {@.ja 実行コンテキストのID}
     *
     * @return 
     *   {@.ja INACTIVE状態の時はtrue、それ以外はfalse
     * rtc、ecがnilの場合もfalseを返す}
     *
     */
/*
    public is_in_inactive(rtc, ec_id=0){
        ret = [None]
        if get_state(rtc, ec_id, ret){
            if ret[0] == RTC.INACTIVE_STATE{
                return true
            }
        }
        return false
    }
*/
    /**
     *
     * {@.ja 対象のRTコンポーネントの指定した実行コンテキストでACTIVE状態かどうか判定}
     * 
     * @param rtc
     *   {@.ja 対象のRTコンポーネント}
     * 
     * @param ec_id 
     *   {@.ja 実行コンテキストのID}
     * 
     * @return 
     *   {@.ja ACTIVE状態の時はtrue、それ以外はfalse
     * rtc、ecがnilの場合もfalseを返す}
     *
     */
/*
    public is_in_active(rtc, ec_id=0){
        ret = [None]
        if get_state(rtc, ec_id, ret){
            if ret[0] == RTC.ACTIVE_STATE{
                return true
            }
        }
        return false
    }
*/
    /**
     *
     * {@.ja 対象のRTコンポーネントの指定した実行コンテキストでERROR状態かどうか判定}
     * 
     * @param rtc
     *   {@.ja 対象のRTコンポーネント}
     * 
     * @param 
     *   {@.ja ec_id 実行コンテキストのID}
     * 
     * @return 
     *   {@.ja ERROR状態の時はtrue、それ以外はfalse
     * rtc、ecがnilの場合もfalseを返す}
     *
     */
/*
    public is_in_error(rtc, ec_id=0){
        ret = [None]
        if get_state(rtc, ec_id, ret){
            if ret[0] == RTC.ERROR_STATE{
                return true
            }
        }
        return false
    }
*/
    /**
     *
     * {@.ja RTCのデフォルトの実行コンテキストの実行周期を取得する}
     *
     * @param rtc
     *   {@.ja 対象のRTコンポーネント}
     * 
     * @return 
     *   {@.ja 実行周期}
     */
/*
    public get_default_rate(rtc){
        ec = get_actual_ec(rtc)
        return ec.get_rate()
    }
*/
    /**
     *
     * {@.ja RTCのデフォルトの実行コンテキストの実行周期を設定する}
     *
     * 
     * @param rtc
     *   {@.ja 対象のRTコンポーネント}
     * 
     * @param rate 
     *   {@.ja 実行周期}
     * 
     * @return 
     *   {@.ja set_rate関数の戻り値を返す。 RTC_OKで設定が成功}
     *
     */
/*
    public set_default_rate(rtc, rate){
        ec = get_actual_ec(rtc)
        return ec.set_rate(rate)

    }  
*/
    /**
     *
     * {@.ja RTCの指定IDの実行コンテキストの周期を設定}
     * 
     * @param rtc
     *   {@.ja 対象のRTコンポーネント}
     * 
     * @param ec_id 
     *   {@.ja 指定の実行コンテキストのID}
     * 
     * @return 
     *   {@.ja 実行周期}
     *
     */
/*
    public get_current_rate(rtc, ec_id){
        ec = get_actual_ec(rtc, ec_id)
        return ec.get_rate()
    }
*/
    /**
     *
     * {@.ja RTCの指定IDの実行コンテキストの周期を取得}
     * 
     * @param rtc
     *   {@.ja 対象のRTコンポーネント}
     * 
     * @param ec_id 
     *   {@.ja 指定の実行コンテキストのID}
     * 
     * @return 
     *   {@.ja set_rate関数の戻り値を返す。RTC_OKで設定が成功}
     *
     *
     */
/*
    public set_current_rate(rtc, ec_id, rate){
        ec = get_actual_ec(rtc, ec_id)
        return ec.set_rate(rate)
    }
*/
    /**
     *
     * {@.ja 対象のRTCのデフォルトの実行コンテキストに指定のRTCを関連付ける}
     *
     * 
     * @param localcomp
     *   {@.ja 対象のRTコンポーネント}
     * 
     * @param othercomp 
     *   {@.ja 実行コンテキストに関連付けるRTコンポーネント}
     * 
     * @return 
     *   {@.ja ecの取得に失敗した場合はRTC_ERRORを返す
     * そうでない場合はaddComponent関数の戻り値を返す。RTC_OKで接続成功。}
     *
     */
/*
    public add_rtc_to_default_ec(localcomp, othercomp){
        ec = get_actual_ec(localcomp)
        if CORBA.is_nil(ec){
            return RTC.RTC_ERROR
        }
        return ec.add_component(othercomp)
    }
*/
    /**
     *
     * {@.ja 対象のRTCのデフォルトの実行コンテキストの指定のRTCへの関連付けを解除する}
     *
     * 
     * @param localcomp
     *   {@.ja 対象のRTコンポーネント}
     * 
     * @param othercomp 
     *   {@.ja 実行コンテキストとの関連付けを解除するRTコンポーネント}
     * 
     * @return 
     *   {@.ja ecの取得に失敗した場合はRTC_ERRORを返す
     * そうでない場合はremoveComponent関数の戻り値を返す。RTC_OKで接続成功。}
     *
     */
/*
    public remove_rtc_to_default_ec(localcomp, othercomp){
        ec = get_actual_ec(localcomp)
        if CORBA.is_nil(ec){
            return RTC.RTC_ERROR
        }
        return ec.remove_component(othercomp)
    }
*/
    /**
     * @if jp
     *
     * {@.ja RTCのデフォルトの実行コンテキストに参加しているRTCのリストを取得する}
     * <p>
     * {@.ja 実行コンテキストがnilの場合は空のリストを返す}
     *
     * 
     * @param rtc
     *   {@.ja RTコンポーネント}
     * 
     * @return 
     *   {@.ja RTCのリスト}
     */
/*
    public get_participants_rtc(rtc){
        ec = get_actual_ec(rtc)
        if CORBA.is_nil(ec){
            return []
        }
        prifile = ec.get_profile()
        return prifile.participants
    }
*/
    /**
     *
     * {@.ja 指定したRTCの保持するポートの名前を取得}
     * 
     * @param rtc 
     *   {@.ja 対象のRTコンポーネント}
     * 
     * @return 
     *   {@.ja ポート名のリスト}
     * 
     */
/*
    public get_port_names(rtc){
        names = []
        if CORBA.is_nil(rtc){
            return names
        }
        ports = rtc.get_ports()
        for p in ports{
            pp = p.get_port_profile()
            s = pp.name
           names.append(s)
        }
        return names
    }
*/
    /**
     *
     * {@.ja 指定したRTCの保持するインポートの名前を取得}
     * 
     * @param rtc
     *   {@.ja 対象のRTコンポーネント}
     * 
     * @return 
     *   {@.ja ポート名のリスト}
     * 
     *
     */
/*
    public get_inport_names(rtc) {
        names = []
        if CORBA.is_nil(rtc){
            return names
        }
  
        ports = rtc.get_ports()
        for p in ports{
            pp = p.get_port_profile()
            prop = OpenRTM_aist.Properties()
            OpenRTM_aist.NVUtil.copyToProperties(prop, pp.properties)
            if prop.getProperty("port.port_type") == "DataInPort" {
                s = pp.name
                names.append(s)
            }
        }
        return names
    }
*/
    /**
     *
     * {@.ja 指定したRTCの保持するアウトポートの名前を取得}
     *
     * @param rtc
     *   {@.ja 対象のRTコンポーネント}
     * 
     * @return 
     *   {@.ja ポート名のリスト}
     *
     */
/*
    public get_outport_names(rtc){
        names = []
        if CORBA.is_nil(rtc){
           return names
        }
  
        ports = rtc.get_ports()
        for p in ports{
            pp = p.get_port_profile()
            prop = OpenRTM_aist.Properties()
            OpenRTM_aist.NVUtil.copyToProperties(prop, pp.properties)
            if prop.getProperty("port.port_type") == "DataOutPort"{
                s = pp.name
                names.append(s)
            }
        }
        return names
    }
*/


    /**
     * {@.ja 指定したRTCの保持するサービスポートの名前を取得}
     *
     * @param rtc 対象のRTコンポーネント
     *   {@.ja 対象のRTコンポーネント}
     * 
     * @return 
     *   {@.ja ポート名のリスト}
     */
/*
    public get_svcport_names(rtc){
        names = []
        if CORBA.is_nil(rtc){
            return names
        }
  
        ports = rtc.get_ports()
        for p in ports{
            pp = p.get_port_profile()
            prop = OpenRTM_aist.Properties()
            OpenRTM_aist.NVUtil.copyToProperties(prop, pp.properties)
            if prop.getProperty("port.port_type") == "CorbaPort"{
                s = pp.name
                names.append(s)
            }
        }
        return names
    }

*/
    /**
     *
     * {@.ja 対象のRTCから指定した名前のポートを取得}
     *
     * @param rtc
     *   {@.ja RTコンポーネント}
     * 
     * @param name 
     *   {@.ja ポート名}
     * 
     * @return
     *   {@.ja ポート}
     *
     *
     *
     * RTC::PortService_var get_port_by_name(const RTC::RTObject_ptr rtc, std::string name)
     */
/*
    public get_port_by_name(rtc, name){
        if CORBA.is_nil(rtc){
            return RTC.PortService._nil
        }
        ports = rtc.get_ports()
        for p in ports{
            pp = p.get_port_profile()
            s = pp.name
    
            if name == s{
                return p
            }
        }
        return RTC.PortService._nil
    }
*/
    /**
     *
     * {@.ja 指定したポートの保持しているコネクタの名前のリストを取得}
     *
     * @param port 
     *   {@.ja 対象のポート}
     * 
     * @return 
     *   {@.ja コネクタ名のリスト}
     *
     *
     */
/*
    public get_connector_names(port){
        names = []
        if CORBA.is_nil(port){
            return names
        }
        conprof = port.get_connector_profiles()
        for c in conprof{
            names.append(c.name)
        }
        return names
  
    }
*/

    /**
     *
     * {@.ja 指定したポートの保持しているコネクタのIDのリストを取得}
     *
     * 
     * @param port 対象のポート
     *   {@.ja 対象のポート}
     * 
     * @return 
     *   {@.ja コネクタのIDのリスト}
     * 
     *
     */
/*
   public get_connector_ids(port){
        ids = []
        if CORBA.is_nil(port){
            return ids
        }
        conprof = port.get_connector_profiles()
        for c in conprof{
            ids.append(c.connector_id)
        }
        return ids
    }
*/
    /**
     *
     * {@.ja 指定したポートを接続するためのコネクタプロファイルを取得}
     *
     * 
     * @param name 
     *   {@.ja コネクタ名}
     * 
     * @param prop_arg 
     *   {@.ja 設定}
     * 
     * @param port0 
     *   {@.ja 対象のポート1}
     * 
     * @param port1 
     *   {@.ja 対象のポート2}
     *
     * @return 
     *   {@.ja コネクタプロファイル}
     *
     *
     * RTC::ConnectorProfile_var create_connector(const std::string name,const coil::Properties prop_arg,const RTC::PortService_ptr port0,const RTC::PortService_ptr port1)
     */
/*
    public create_connector(name, prop_arg, port0, port1){
        prop = prop_arg
        conn_prof = RTC.ConnectorProfile(name, "", [port0, port1],[])



        if not str(prop.getProperty("dataport.dataflow_type")){
            prop.setProperty("dataport.dataflow_type","push")
        }
 

        if not str(prop.getProperty("dataport.interface_type")){
            prop.setProperty("dataport.interface_type","corba_cdr")
        }

        conn_prof.properties = []
        OpenRTM_aist.NVUtil.copyFromProperties(conn_prof.properties, prop)
  
        return conn_prof
  
    }
 */ 
                                            
    /**
     *
     * {@.ja 指定したポート同士が接続されているかを判定}
     *
     * 
     * @param localport
     *   {@.ja 対象のポート1}
     * 
     * @param otherport 
     *   {@.ja 対象のポート2}
     * 
     * @return 
     *   {@.ja true: 接続済み、false: 未接続}
     *
     *
     */
/*
    public already_connected(localport, otherport){
        conprof = localport.get_connector_profiles()
        for c in conprof{
            for p in c.ports{
                if p._is_equivalent(otherport){
                    return true
                }
            }
        }

        return false

    } 
*/
    /**
     *
     *
     * {@.ja 指定したポートを接続する}
     *
     * 
     * @param name 
     *   {@.ja コネクタ名}
     * 
     * @param prop 
     *   {@.ja 設定}
     * 
     * @param port0 
     *   {@.ja 対象のポート1}
     * 
     * @param port1 
     *   {@.ja 対象のポート2}
     * 
     * @return 
     *   {@.ja RTC、ECのオブジェクトリファレンスがnilの場合はBAD_PARAMETERを返す
     * nilではない場合はport0.connect関数の戻り値を返す。RTC_OKの場合は接続が成功}
     *
     * RTC::ReturnCode_t connect(const std::string name,const coil::Properties prop,const RTC::PortService_ptr port0,const RTC::PortService_ptr port1)
     */
/*
    public connect(name, prop, port0, port1){
        if CORBA.is_nil(port0){
            RTC.BAD_PARAMETER
        }
        if CORBA.is_nil(port1){
            RTC.BAD_PARAMETER
        }
        if port0._is_equivalent(port1){
            RTC.BAD_PARAMETER
        }
        cprof = create_connector(name, prop, port0, port1)
        return port0.connect(cprof)[0]

    }
*/
    /**
     *
     * {@.ja 指定したポートと指定したリスト内のポート全てと接続する}
     *
     * 
     * @param name コネクタ名
     *   {@.ja コネクタ名}
     * 
     * @param prop 
     *   {@.ja 設定}
     * 
     * @param port0 
     *   {@.ja 対象のポート}
     * 
     * @param port1 
     *   {@.ja 対象のポートのリスト}
     * 
     * @return 
     *   {@.ja 全ての接続が成功した場合はRTC_OKを返す。
     * connect関数がRTC_OK以外を返した場合はRTC_ERRORを返す。}
     *
     *
     * RTC::ReturnCode_t connect_multi(const std::string name,const coil::Properties prop,const RTC::PortService_ptr port,RTC::PortServiceList_var& target_ports)
     */
/*
    public connect_multi(name, prop, port, target_ports){
        ret = RTC.RTC_OK
  
        for p in target_ports{
            if p._is_equivalent(port){
                continue
            }
            if already_connected(port, p){
                continue
            }
            if RTC.RTC_OK != connect(name, prop, port, p){
                ret = RTC.RTC_ERROR
            }
        }
        return ret
    }
*/
}
/**
 * {@.ja ポートを名前から検索}
 *
 */
//public class find_port {
  
    /**
     *
     * {@.ja コンストラクタ}
     * 
     * <p>
     * {@.ja 検索するポート名を指定する}
     *
     * @param name 
     *   {@.ja ポート名}
     *
     * find_port(const std::string name)
     */
/*
    public __init__(self, name){
        self._name = name
    }
*/
    /**
     *
     * {@.ja 対象のポートの名前と指定したポート名が一致するか判定}
     * 
     * @param p 
     *   {@.ja 対象のポート}
     * 
     * @return 
     *   {@.ja true: 名前が一致、false:　名前が不一致}
     * 
     *
     * bool operator()(RTC::PortService_var p)
     */
/*
    public __call__(self, p){
        prof = p.get_port_profile()
        c = prof.name
    
        return (self._name == c)
    }
*/ 
    /**
     *
     * {@.ja 対象のRTCの指定した名前のポートを接続する}
     *
     * @param name 
     *   {@.ja コネクタ名}
     * 
     * @param prop 
     *   {@.ja 設定}
     * 
     * @param rtc0 
     *   {@.ja 対象のRTCコンポーネント1}
     * 
     * @param portName0 
     *   {@.ja 対象のポート名1}
     * 
     * @param rtc1 
     *   {@.ja 対象のRTCコンポーネント2}
     * 
     * @param portName1 
     *   {@.ja 対象のRTCコンポーネント2}
     * 
     * @return 
     *   {@.ja RTC、ポートがnilの場合はBAD_PARAMETERを返す。
     * nilではない場合はport0.connect関数の戻り値を返す。RTC_OKの場合は接続が成功}
     *
     *
     * RTC::ReturnCode_t connect_by_name(std::string name, coil::Properties prop,RTC::RTObject_ptr rtc0,const std::string portName0,RTC::RTObject_ptr rtc1,const std::string portName1)
     */
/*
    public connect_by_name(name, prop, rtc0, portName0, rtc1, portName1){
        if CORBA.is_nil(rtc0){
            return RTC.BAD_PARAMETER
        }
        if CORBA.is_nil(rtc1){
            return RTC.BAD_PARAMETER
        }
        port0 = get_port_by_name(rtc0, portName0)
        if CORBA.is_nil(port0){
            return RTC.BAD_PARAMETER
        }

        port1 = get_port_by_name(rtc1, portName1)
        if CORBA.is_nil(port1){
            return RTC.BAD_PARAMETER
        }

        return connect(name, prop, port0, port1)

    }
*/
    /**
     *
     * {@.ja 指定のコネクタを切断する}
     *
     * 
     * @param connector_prof 
     *   {@.ja コネクタプロファイル}
     * 
     * @return 
     *   {@.ja コネクタプロファイルで保持しているポートのオブジェクトリファレンスがnilの場合はBAD_PARAMETERを返す
     * nilではない場合はports[0].disconnect関数の戻り値を返す。RTC_OKの場合は切断が成功}
     *
     *
     */
/*
    public disconnect(connector_prof){
        ports = connector_prof.ports
        return disconnect_by_connector_id(ports[0], connector_prof.connector_id)
    }  
*/  

    /**
     *
     * {@.ja 対象のポートで指定した名前のコネクタを切断}
     *
     * @param port 
     *   {@.ja 対象のポート}
     * 
     * @param name 
     *   {@.ja コネクタ名}
     * 
     * @return 
     *   {@.ja portがnilの場合はBAD_PARAMETERを返す
     * nilではない場合はdisconnect関数の戻り値を返す。RTC_OKの場合は切断が成功}
     *
     *
     */
/*
    public disconnect_by_connector_name(port, name){
        if CORBA.is_nil(port){
            return RTC.BAD_PARAMETER
        }
        conprof = port.get_connector_profiles()
        for c in conprof{
            if c.name == name{
                return disconnect(c)
            }
        }
        return RTC.BAD_PARAMETER

    }
*/

    /**
     *
     * {@.ja 対象のポートで指定したIDのコネクタを切断}
     *
     * 
     * @param port 
     *   {@.ja 対象のポート}
     * 
     * @param name 
     *   {@.ja コネクタID}
     * 
     * @return 
     *   {@.ja portがnilの場合はBAD_PARAMETERを返す
     * nilではない場合はdisconnect関数の戻り値を返す。RTC_OKの場合は切断が成功}
     *
     *
     */
/*
    public disconnect_by_connector_id(port, id){
        if CORBA.is_nil(port){
            return RTC.BAD_PARAMETER
        }
        return port.disconnect(id)

    }
*/
    /**
     *
     * {@.ja 対象ポートと接続しているポートで指定したポート名と一致した場合に切断}
     *
     * 
     * @param localport 
     *   {@.ja 対象のポート}
     *
     * @param othername 
     *   {@.ja 接続しているポート名}
     *
     * @return 
     *   {@.ja ポートがnilの場合、localportの名前とothernameが一致する場合、接続しているポートの名前でothernameと一致するものがない場合にBAD_PARAMETERを返す
     * 上記の条件に当てはまらない場合はdisconnect関数の戻り値を返す。RTC_OKの場合は切断が成功}
     *
     *
     */
/*
    public disconnect_by_port_name(localport, othername){
        if CORBA.is_nil(localport){
            return RTC.BAD_PARAMETER
        }
        prof = localport.get_port_profile()
        if prof.name == othername{
            return RTC.BAD_PARAMETER
        }
  
        conprof = localport.get_connector_profiles()
        for c in conprof{
            for p in c.ports{
                if not CORBA.is_nil(p){
                    pp = p.get_port_profile()
                    if pp.name == othername{
                        return disconnect(c)
                    }
                }
            }
        }
        return RTC.BAD_PARAMETER
    }
*/
    /**
     *
     * {@.ja 指定したRTコンポーネントのコンフィギュレーション取得}
     * 
     * @param rtc 
     *   {@.ja 対象のRTコンポーネント}
     *
     * @return 
     *   {@.ja rtcがnilの場合はNoneを返す。
     * nilではない場合はコンフィギュレーションを返す。}
     *
     *
     *
     */
/*
    public get_configuration(rtc){
        if CORBA.is_nil(rtc){
            return SDOPackage.Configuration._nil
        }
  
        return rtc.get_configuration()
    }
*/
    /**
     *
     * {@.ja 指定したコンフィギュレーションセット名、パラメータ名のコンフィギュレーションパラメータを取得}
     * <p>
     * {@.ja コンポーネントのプロパティ取得}
     * 
     * @param conf 
     *   {@.ja コンフィギュレーション}
     * 
     * @param confset_name 
     *   {@.ja コンフィギュレーションセット名}
     * 
     * @param value_name 
     *   {@.ja パラメータ名}
     * 
     * @return パラメータ
     *   {@.ja パラメータ}
     * 
     */
/*
    public get_parameter_by_key(rtc, confset_name, value_name){
        conf = rtc.get_configuration()
  
    
        confset = conf.get_configuration_set(confset_name)
        confData = confset.configuration_data
        prop = OpenRTM_aist.Properties()
        OpenRTM_aist.NVUtil.copyToProperties(prop, confData)
        return prop.getProperty(value_name)
    
    }  
*/

    /**
     *
     * {@.ja 対象のRTCのアクティブなコンフィギュレーションセット名を取得する}
     *
     * @param rtc 
     *   {@.ja RTコンポーネント}
     * 
     * @return 
     *   {@.ja コンフィギュレーションセット名
     * コンフィギュレーションの取得に失敗した場合は空の文字列を返す}
     * 
     *
     *
     */
/*
    public get_current_configuration_name(rtc){
        conf = rtc.get_configuration()
        confset = conf.get_active_configuration_set()
        return confset.id
    }
*/
    /**
     *:
     *
     * {@.ja アクティブなコンフィギュレーションセットを取得}
     *
     * @param rtc 
     *   {@.ja 対象のRTコンポーネント}
     * 
     * @return 
     *   {@.ja アクティブなコンフィギュレーションセット}
     * 
     *
     *
     */
/*
    public get_active_configuration(rtc){
        conf = rtc.get_configuration()

        confset = conf.get_active_configuration_set()
        confData = confset.configuration_data
        prop = OpenRTM_aist.Properties()
        OpenRTM_aist.NVUtil.copyToProperties(prop, confData)
        return prop
    
    }
*/
    /**
     *
     * {@.ja コンフィギュレーションパラメータを設定}
     *
     * @param confset_name 
     *   {@.ja コンフィギュレーションセット名}
     * 
     * @param value_name 
     *   {@.ja パラメータ名}
     * 
     * @param value パラメータ
     *   {@.ja パラメータ}
     * 
     * @return 
     *   {@.ja true:設定に成功、false:設定に失敗}
     * 
     *
     */
/*
    public set_configuration(rtc, confset_name, value_name, value){
        conf = rtc.get_configuration()
  
        confset = conf.get_configuration_set(confset_name)
        confData = confset.configuration_data
        prop = OpenRTM_aist.Properties()
        OpenRTM_aist.NVUtil.copyToProperties(prop, confData)
        prop.setProperty(value_name,value)
        OpenRTM_aist.NVUtil.copyFromProperties(confData,prop)
        confset.configuration_data = confData
        conf.set_configuration_set_values(confset)
  
        conf.activate_configuration_set(confset_name)
        return true
    }    
*/
//}    
