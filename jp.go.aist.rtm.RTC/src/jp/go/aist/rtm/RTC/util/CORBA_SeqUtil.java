package jp.go.aist.rtm.RTC.util;

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
import RTC.RTObject;
import _SDOPackage.NVListHolder;
import _SDOPackage.NameValue;
import _SDOPackage.Organization;
import _SDOPackage.OrganizationListHolder;
import _SDOPackage.SDO;
import _SDOPackage.SDOListHolder;
import _SDOPackage.ServiceProfile;
import _SDOPackage.ServiceProfileListHolder;

/**
 * {@.ja CORBAシーケンスに対するユーティリティクラス。}
 * {@.en Utility class to CORBA sequence.}
 * <p>
 * {@.ja 各メソッドはスレッドセーフではないため、
 * 必要に応じて呼び出し側で適切に排他処理を行ってください。}
 * {@.en Each method is not thread-safe. 
 * Please do the exclusive operation appropriately if necessary 
 * on the call side.}
 */
public class CORBA_SeqUtil {

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
    public static operatorFunc for_each(NVListHolder seq, operatorFunc func) {
        if( seq.value == null ) return null;
        for (int i = 0; i < seq.value.length; ++i) {
            func.operator(seq.value[i]);
        }
        
        return func;
    }
    
    /**
     * {@.ja CORBA sequence に対して functor を適用する。}
     * {@.en Apply the functor to all CORBA sequence elements}
     *
     * <p>
     * {@.ja 指定されたシーケンス内の各ConnectorProfileオブジェクト対して、
     * 順次、指定された操作を行う。}
     * {@.en Apply the given functor to the given CORBA sequence.}
     * 
     * @param seq 
     *   {@.ja ConnectorProfileオブジェクトシーケンスを
     *   内部に保持するConnectorProfileListHolderオブジェクト}
     *   {@.en ConnectorProfileHolder object that hold ConnectorProfile 
     *   object sequence internally.}
     * @param func 
     *   {@.ja 各ConnectorProfileオブジェクトに適用するoperatorFuncオブジェクト}
     *   {@.en OperatorFunc object applied to each ConnectorProfile object}
     * @return
     *   {@.ja 引数で指定されたoperatorFuncオブジェクト}
     *   {@.en OperatorFunc object specified by argument}
     */
    public static operatorFunc for_each(ConnectorProfileListHolder seq, operatorFunc func) {
        if( seq.value == null ) return null;
        for (int i = 0; i < seq.value.length; ++i) {
            func.operator(seq.value[i]);
        }
        
        return func;
    }
    

    /**
     * {@.ja CORBA sequence に対して functor を適用する。}
     * {@.en Apply the functor to all CORBA sequence elements}
     *
     * <p>
     * {@.ja 指定されたシーケンス内の各ExecutionContextServiceオブジェクトに
     * 対して、順次、指定された操作を行う。}
     * {@.en Apply the given functor to the given CORBA sequence.}
     * 
     * @param seq 
     *   {@.ja ExecutionContextServiceオブジェクトシーケンスを
     *   内部に保持するExecutionContextServiceListHolderオブジェクト}
     *   {@.en ExecutionContextServiceListHolder object that hold 
     *   ExecutionContextService object sequence internally.}
     * 
     * @param func 
     *   {@.ja 各ExecutionContextServiceオブジェクトに適用する
     *   operatorFuncオブジェクト}
     *   {@.en OperatorFunc object applied to each ExecutionContextService 
     *   object}
     * @return
     *   {@.ja 引数で指定されたoperatorFuncオブジェクト}
     *   {@.en OperatorFunc object specified by argument}
     */
    public static operatorFunc for_each(ExecutionContextServiceListHolder seq, operatorFunc func) {
        if( seq.value == null ) return null;
        for (int i = 0; i < seq.value.length; ++i) {
            func.operator(seq.value[i]);
        }
        
        return func;
    }
    
    /**
     * {@.ja CORBA sequence に対して functor を適用する。}
     * {@.en Apply the functor to all CORBA sequence elements}
     *
     * <p>
     * {@.ja 指定されたシーケンス内の各PortServiceListHolderオブジェクトに
     * 対して、順次、指定された操作を行う。}
     * {@.en Apply the given functor to the given CORBA sequence.}
     * 
     * @param seq 
     *   {@.ja PortServiceListHolderオブジェクトシーケンスを
     *   内部に保持するPortServiceListHolderオブジェクト}
     *   {@.en PortServiceListHolder object that hold 
     *   PortServiceListHolder object sequence internally.}
     *
     * @param func 
     *   {@.ja 各PortServiceListHolderオブジェクトに適用する
     *   operatorFuncオブジェクト}
     *   {@.en OperatorFunc object applied to each PortServiceListHolder
     *   object}
     *
     * @return
     *   {@.ja 引数で指定されたoperatorFuncオブジェクト}
     *   {@.en OperatorFunc object specified by argument}
     *
     */
    public static operatorFunc for_each(PortServiceListHolder seq, operatorFunc func) {
        if( seq.value == null ) return null;
        for (int i = 0; i < seq.value.length; ++i) {
            func.operator(seq.value[i]);
        }
        
        return func;
    }

    /**
     * {@.ja 指定されたシーケンス内の指定条件に合致するNameValueオブジェクトの
     * インデクスを取得する。}
     * {@.en Return the index of CORBA sequence element that functor matches}
     * 
     * @param seq 
     *   {@.ja NameValueオブジェクトシーケンスを保持する
     *   NVListHolderオブジェクト}
     *   {@.en NVListHolderHolder object that hold 
     *   NameValue object sequence internally.}
     * @param func 
     *   {@.ja 合致判定を行うequalFunctorオブジェクト}
     *   {@.en equalFunctor object that does agreement judgment}
     * @return 
     *   {@.ja 合致するNameValueオブジェクトが存在する場合は、
     *   そのオブジェクトのシーケンス内でのインデクスを返す。
     *   合致するNameValueオブジェクトが存在しない場合は、-1を返す。}
     *   {@.en When the agreeing NameValue object exists, 
     *   the index in the sequence of the object is returned. 
     *   When the agreeing NameValue object doesn't exist, -1 is returned.} 
     */
    public static int find(final NVListHolder seq, equalFunctor func) {
        if( seq.value == null ) return -1;
        for (int i = 0; i < seq.value.length; ++i) {
            if (func.equalof(seq.value[i])) {
                return i;
            }
        }
        
        return -1;
    }
    
    /**
     * {@.ja 指定されたシーケンス内の指定条件に合致するPortオブジェクトの
     * インデクスを取得する。}
     * {@.en Return the index of CORBA sequence element that functor matches}
     * 
     * @param seq 
     *   {@.ja Portオブジェクトシーケンスを保持する
     *   PortServiceListHolderオブジェクト}
     *   {@.en PortServiceListHolder object that hold 
     *   Port object sequence internally.}
     * @param func 
     *   {@.ja 合致判定を行うequalFunctorオブジェクト}
     *   {@.en equalFunctor object that does agreement judgment}
     * @return 
     *   {@.ja 合致するPortオブジェクトが存在する場合は、
     *   そのオブジェクトのシーケンス内でのインデクスを返す。
     *   合致するPortオブジェクトが存在しない場合は、-1を返す。}
     *   {@.en When the agreeing Port object exists, 
     *   the index in the sequence of the object is returned. 
     *   When the agreeing Port object doesn't exist, -1 is returned.} 
     */
    public static int find(final PortServiceListHolder seq, equalFunctor func) {
        if( seq.value == null ) return -1;
        for (int i = 0; i < seq.value.length; ++i) {
            if (func.equalof(seq.value[i])) {
                return i;
            }
        }
        
        return -1;
    }
    
    /**
     * {@.ja 指定されたシーケンス内の指定条件に合致する
     * PortInterfaceProfileオブジェクトのインデクスを取得する。}
     * {@.en Return the index of CORBA sequence element that functor matches}
     * 
     * @param seq 
     *   {@.ja PortInterfaceProfileオブジェクトシーケンスを保持する
     *   PortInterfaceProfileListHolderオブジェクト}
     *   {@.en PortInterfaceProfileListHolder object that hold 
     *   PortInterfaceProfile object sequence internally.}
     * @param func 
     *   {@.ja 合致判定を行うequalFunctorオブジェクト}
     *   {@.en equalFunctor object that does agreement judgment}
     * @return 
     *   {@.ja 合致するPortInterfaceProfileオブジェクトが存在する場合は、
     *   そのオブジェクトのシーケンス内でのインデクスを返す。
     *   合致するPortInterfaceProfileオブジェクトが存在しない場合は、-1を返す。}
     *   {@.en When the agreeing PortInterfaceProfile object exists, 
     *   the index in the sequence of the object is returned. 
     *   When the agreeing PortInterfaceProfile object doesn't exist,
     *   -1 is returned.} 
     */
    public static int find(final PortInterfaceProfileListHolder seq, equalFunctor func) {
        if( seq.value == null ) return -1;
        for (int i = 0; i < seq.value.length; ++i) {
            if (func.equalof(seq.value[i])) {
                return i;
            }
        }
        
        return -1;
    }
    
    /**
     * {@.ja 指定されたシーケンス内の指定条件に合致する
     * ConnectorProfileオブジェクトのインデクスを取得する。}
     * {@.en Return the index of CORBA sequence element that functor matches}
     * 
     * @param seq 
     *   {@.ja ConnectorProfileオブジェクトシーケンスを保持する
     *   ConnectorProfileListHolderオブジェクト}
     *   {@.en ConnectorProfileListHolder object that hold 
     *   ConnectorProfile object sequence internally.}
     * @param f 
     *   {@.ja 合致判定を行うequalFunctorオブジェクト}
     *   {@.en equalFunctor object that does agreement judgment}
     * @return 
     *   {@.ja 合致するConnectorProfileオブジェクトが存在する場合は、
     *   そのオブジェクトのシーケンス内でのインデクスを返す。
     *   合致するConnectorProfileオブジェクトが存在しない場合は、-1を返す。}
     *   {@.en When the agreeing ConnectorProfile object exists, 
     *   the index in the sequence of the object is returned. 
     *   When the agreeing ConnectorProfile object doesn't exist,
     *   -1 is returned.} 
     */
    public static int find(final ConnectorProfileListHolder seq, equalFunctor f) {
        if( seq.value == null ) return -1;
        for (int i = 0; i < seq.value.length; ++i) {
            if (f.equalof(seq.value[i])) {
                return i;
            }
        }
        
        return -1;
    }
    
    /**
     * {@.ja 指定されたシーケンス内の指定条件に合致する
     * ExecutionContextServiceオブジェクトのインデクスを取得する。}
     * {@.en Return the index of CORBA sequence element that functor matches}
     * 
     * @param seq 
     *   {@.ja ExecutionContextServiceオブジェクトシーケンスを保持する
     *   ExecutionContextServiceListHolderオブジェクト}
     *   {@.en ExecutionContextServiceListHolder object that hold 
     *   ExecutionContextService object sequence internally.}
     * @param f 
     *   {@.ja 合致判定を行うequalFunctorオブジェクト}
     *   {@.en equalFunctor object that does agreement judgment}
     * @return 
     *   {@.ja 合致するExecutionContextServiceオブジェクトが存在する場合は、
     *   そのオブジェクトのシーケンス内でのインデクスを返す。
     *   合致するExecutionContextServiceオブジェクトが存在しない場合は、
     *   -1を返す。}
     *   {@.en When the agreeing ExecutionContextService object exists, 
     *   the index in the sequence of the object is returned. 
     *   When the agreeing ExecutionContextService object doesn't exist,
     *   -1 is returned.} 
     */
    public static int find(final ExecutionContextServiceListHolder seq, equalFunctor f) {
        if( seq.value == null ) return -1;
        for (int i = 0; i < seq.value.length; ++i) {
            if (f.equalof(seq.value[i])) {
                return i;
            }
        }
        
        return -1;
    }
    
    /**
     * {@.ja 指定されたシーケンス内の指定条件に合致する
     * RTM.Managerオブジェクトのインデクスを取得する。}
     * {@.en Return the index of CORBA sequence element that functor matches}
     * 
     * @param seq 
     *   {@.ja RTM.Managerオブジェクトシーケンスを保持する
     *   RTM.ManagerListHolderオブジェクト}
     *   {@.en RTM.ManagerListHolder object that hold 
     *   RTM.Manager object sequence internally.}
     * @param f 
     *   {@.ja 合致判定を行うequalFunctorオブジェクト}
     *   {@.en equalFunctor object that does agreement judgment}
     * @return 
     *   {@.ja 合致するRTM.Managerオブジェクトが存在する場合は、
     *   そのオブジェクトのシーケンス内でのインデクスを返す。
     *   合致するRTM.Managerオブジェクトが存在しない場合は、
     *   -1を返す。}
     *   {@.en When the agreeing RTM.Manager object exists, 
     *   the index in the sequence of the object is returned. 
     *   When the agreeing RTM.Manager object doesn't exist,
     *   -1 is returned.} 
     */
    public static int find(final RTM.ManagerListHolder seq, equalFunctor f) {
        if( seq.value == null ) return -1;
        for (int i = 0; i < seq.value.length; ++i) {
            if (f.equalof(seq.value[i])) {
                return i;
            }
        }
        
        return -1;
    }

    /**
     * {@.ja 指定されたシーケンス内の指定条件に合致する
     * RTC.RTCオブジェクトのインデクスを取得する。}
     * {@.en Return the index of CORBA sequence element that functor matches}
     * 
     * @param seq 
     *   {@.ja RTC.RTCオブジェクトシーケンスを保持する
     *   RTC.RTCListHolderオブジェクト}
     *   {@.en RTC.RTCListHolder object that hold 
     *   RTC.RTC object sequence internally.}
     * @param f 
     *   {@.ja 合致判定を行うequalFunctorオブジェクト}
     *   {@.en equalFunctor object that does agreement judgment}
     * @return 
     *   {@.ja 合致するRTC.RTCオブジェクトが存在する場合は、
     *   そのオブジェクトのシーケンス内でのインデクスを返す。
     *   合致するRTC.RTCオブジェクトが存在しない場合は、
     *   -1を返す。}
     *   {@.en When the agreeing RTC.RTC object exists, 
     *   the index in the sequence of the object is returned. 
     *   When the agreeing RTC.RTC object doesn't exist,
     *   -1 is returned.} 
     */
    public static int find(final RTC.RTCListHolder seq, equalFunctor f) {
        if( seq.value == null ) return -1;
        for (int i = 0; i < seq.value.length; ++i) {
            if (f.equalof(seq.value[i])) {
                return i;
            }
        }
        
        return -1;
    }

    /**
     * {@.ja シーケンスの末尾にNameValueオブジェクトを追加する。}
     * {@.en Adds NameValue at the end of CORBA sequence.} 
     *
     * @param seq 
     *   {@.ja NameValueオブジェクトを追加するNVListHolderオブジェクト}
     *   {@.en NVListHolder object that adds NameValue object.}
     * @param elem 
     *   {@.ja 追加するNameValueオブジェクト}
     *   {@.en NameValue to be added to the CORBA sequence.}
     *
     */
    public static void push_back(NVListHolder seq, NameValue elem) {
        int len;
        if( seq.value == null ) {
            len = 0;
        } else {
            len = seq.value.length;
        }
        NameValue[] newlist = new NameValue[len + 1];
        for (int intIdx = 0; intIdx < len; ++intIdx) {
            newlist[intIdx] = seq.value[intIdx];
        }
        newlist[len] = elem;
        seq.value = newlist;
    }

    /**
     * {@.ja シーケンスの末尾にServiceProfileオブジェクトを追加する。}
     * {@.en Adds ServiceProfile at the end of CORBA sequence.} 
     *
     * @param seq 
     *   {@.ja ServiceProfileオブジェクトを追加する
     *   ServiceProfileListHolderオブジェクト}
     *   {@.en ServiceProfileListHolder object that adds ServiceProfile object.}
     * @param elem 
     *   {@.ja 追加するServiceProfileオブジェクト}
     *   {@.en ServiceProfile to be added to the CORBA sequence.}
     *
     */
    public static void push_back(ServiceProfileListHolder seq, ServiceProfile elem) {
        int len;
        if( seq.value == null ) {
            len = 0;
        } else {
            len = seq.value.length;
        }
        ServiceProfile[] newlist = new ServiceProfile[len + 1];
        for (int intIdx = 0; intIdx < len; ++intIdx) {
            newlist[intIdx] = seq.value[intIdx];
        }
        newlist[len] = elem;
        seq.value = newlist;
    }

    /**
     * {@.ja シーケンスの末尾にOrganizationオブジェクトを追加する。}
     * {@.en Adds Organization at the end of CORBA sequence.} 
     *
     * @param seq 
     *   {@.ja Organizationオブジェクトを追加する
     *   OrganizationListHolderオブジェクト}
     *   {@.en OrganizationListHolder object that adds Organization object.}
     * @param elem 
     *   {@.ja 追加するOrganizationオブジェクト}
     *   {@.en Organization to be added to the CORBA sequence.}
     *
     */
    public static void push_back(OrganizationListHolder seq, Organization elem) {
        int len;
        if( seq.value == null ) {
            len = 0;
        } else {
            len = seq.value.length;
        }
        Organization[] newlist = new Organization[len + 1];
        for (int intIdx = 0; intIdx < len; ++intIdx) {
            newlist[intIdx] = seq.value[intIdx];
        }
        newlist[len] = elem;
        seq.value = newlist;
    }

    /**
     * {@.ja シーケンスの末尾にExecutionContextServiceオブジェクトを追加する。}
     * {@.en Adds ExecutionContextService at the end of CORBA sequence.} 
     *
     * @param seq 
     *   {@.ja ExecutionContextServiceオブジェクトを追加する
     *   ExecutionContextServiceListHolderオブジェクト}
     *   {@.en ExecutionContextServiceListHolder object that adds 
     *   ExecutionContextService object.}
     * @param elem 
     *   {@.ja 追加するExecutionContextServiceオブジェクト}
     *   {@.en ExecutionContextService to be added to the CORBA sequence.}
     *
     */
    public static void push_back(ExecutionContextServiceListHolder seq, ExecutionContextService elem) {
        int len;
        if( seq.value == null ) {
            len = 0;
        } else {
            len = seq.value.length;
        }
        ExecutionContextService[] newlist = new ExecutionContextService[len + 1];
        for (int intIdx = 0; intIdx < len; ++intIdx) {
            newlist[intIdx] = seq.value[intIdx];
        }
        newlist[len] = elem;
        seq.value = newlist;
    }

    /**
     * {@.ja シーケンスの末尾にPortServiceオブジェクトを追加する。}
     * {@.en Adds PortService at the end of CORBA sequence.} 
     *
     * @param seq 
     *   {@.ja PortServiceオブジェクトを追加する
     *   PortServiceListHolderオブジェクト}
     *   {@.en PortServiceListHolder object that adds 
     *   PortService object.}
     * @param elem 
     *   {@.ja 追加するPortServiceオブジェクト}
     *   {@.en PortService to be added to the CORBA sequence.}
     *
     */
    public static void push_back(PortServiceListHolder seq, PortService elem) {
        int len;
        if( seq.value == null ) {
            len = 0;
        } else {
            len = seq.value.length;
        }
        PortService[] newlist = new PortService[len + 1];
        for (int intIdx = 0; intIdx < len; ++intIdx) {
            newlist[intIdx] = seq.value[intIdx];
        }
        newlist[len] = elem;
        seq.value = newlist;
    }

    /**
     * {@.ja シーケンスの末尾にConnectorProfileオブジェクトを追加する。}
     * {@.en Adds ConnectorProfile at the end of CORBA sequence.} 
     *
     * @param seq 
     *   {@.ja ConnectorProfileオブジェクトを追加する
     *   ConnectorProfileListHolderオブジェクト}
     *   {@.en ConnectorProfileListHolder object that adds 
     *   ConnectorProfile object.}
     * @param elem 
     *   {@.ja 追加するConnectorProfileオブジェクト}
     *   {@.en ConnectorProfile to be added to the CORBA sequence.}
     *
     */
    public static void push_back(ConnectorProfileListHolder seq, ConnectorProfile elem) {
        int len;
        if( seq.value == null ) {
            len = 0;
        } else {
            len = seq.value.length;
        }
        ConnectorProfile[] newlist = new ConnectorProfile[len + 1];
        for (int intIdx = 0; intIdx < len; ++intIdx) {
            newlist[intIdx] = seq.value[intIdx];
        }
        newlist[len] = elem;
        seq.value = newlist;
    }

    /**
     * {@.ja シーケンスの末尾にPortInterfaceProfileオブジェクトを追加する。}
     * {@.en Adds PortInterfaceProfile at the end of CORBA sequence.} 
     *
     * @param seq 
     *   {@.ja PortInterfaceProfileオブジェクトを追加する
     *   PortInterfaceProfileListHolderオブジェクト}
     *   {@.en PortInterfaceProfileListHolder object that adds 
     *   PortInterfaceProfile object.}
     * @param elem 
     *   {@.ja 追加するPortInterfaceProfileオブジェクト}
     *   {@.en PortInterfaceProfile to be added to the CORBA sequence.}
     *
     */
    public static void push_back(PortInterfaceProfileListHolder seq, PortInterfaceProfile elem) {
        int len;
        if( seq.value == null ) {
            len = 0;
        } else {
            len = seq.value.length;
        }
        PortInterfaceProfile[] newlist = new PortInterfaceProfile[len + 1];
        for (int i = 0; i < len; ++i) {
            newlist[i] = seq.value[i];
        }
        newlist[len] = elem;
        seq.value = newlist;
    }
    
    /**
     * {@.ja シーケンスの末尾にPortProfileオブジェクトを追加する。}
     * {@.en Adds PortInterfaceProfile at the end of CORBA sequence.} 
     *
     * @param seq 
     *   {@.ja PortProfileオブジェクトを追加する
     *   PortProfileListHolderオブジェクト}
     *   {@.en PortProfileListHolder object that adds 
     *   PortProfile object.}
     * @param elem 
     *   {@.ja 追加するPortProfileオブジェクト}
     *   {@.en PortProfile to be added to the CORBA sequence.}
     *
     */
    public static void push_back(PortProfileListHolder seq, PortProfile elem) {
        int len;
        if( seq.value == null ) {
            len = 0;
        } else {
            len = seq.value.length;
        }
        PortProfile[] newlist = new PortProfile[len + 1];
        for (int i = 0; i < len; ++i) {
            newlist[i] = seq.value[i];
        }
        newlist[len] = elem;
        seq.value = newlist;
    }

    /**
     * {@.ja シーケンスの末尾にExecutionContextオブジェクトを追加する。}
     * {@.en Adds ExecutionContext at the end of CORBA sequence.} 
     *
     * @param seq 
     *   {@.ja ExecutionContextオブジェクトを追加する
     *   ExecutionContextListHolderオブジェクト}
     *   {@.en ExecutionContextListHolder object that adds 
     *   ExecutionContext object.}
     * @param elem 
     *   {@.ja 追加するExecutionContextオブジェクト}
     *   {@.en ExecutionContext to be added to the CORBA sequence.}
     *
     */
    public static void push_back(ExecutionContextListHolder seq, ExecutionContext elem) {
        int len;
        if( seq.value == null ) {
            len = 0;
        } else {
            len = seq.value.length;
        }
        ExecutionContext[] newlist = new ExecutionContext[len + 1];
        for (int intIdx = 0; intIdx < len; ++intIdx) {
            newlist[intIdx] = seq.value[intIdx];
        }
        newlist[len] = elem;
        seq.value = newlist;
    }

    /**
     * {@.ja シーケンスの末尾にSDOオブジェクトを追加する。}
     * {@.en Adds SDO at the end of CORBA sequence.} 
     *
     * @param seq 
     *   {@.ja SDOオブジェクトを追加する
     *   SDOListHolderオブジェクト}
     *   {@.en SDOListHolder object that adds 
     *   SDO object.}
     * @param elem 
     *   {@.ja 追加するSDOオブジェクト}
     *   {@.en SDO to be added to the CORBA sequence.}
     *
     */
    public static void push_back(SDOListHolder seq, SDO elem) {
        int len;
        if( seq.value == null ) {
            len = 0;
        } else {
            len = seq.value.length;
        }
        SDO[] newlist = new SDO[len + 1];
        for (int intIdx = 0; intIdx < len; ++intIdx) {
            newlist[intIdx] = seq.value[intIdx];
        }
        newlist[len] = elem;
        seq.value = newlist;
    }

    /**
     * {@.ja シーケンスの末尾にComponentProfileオブジェクトを追加する。}
     * {@.en Adds ComponentProfile at the end of CORBA sequence.} 
     *
     * @param seq 
     *   {@.ja ComponentProfileオブジェクトを追加する
     *   ComponentProfileListHolderオブジェクト}
     *   {@.en ComponentProfileListHolder object that adds 
     *   ComponentProfile object.}
     * @param elem 
     *   {@.ja 追加するComponentProfileオブジェクト}
     *   {@.en ComponentProfile to be added to the CORBA sequence.}
     *
     */
    public static void push_back(ComponentProfileListHolder seq, ComponentProfile elem) {
        int len;
        if( seq.value == null ) {
            len = 0;
        } else {
            len = seq.value.length;
        }
        ComponentProfile[] newlist = new ComponentProfile[len + 1];
        for (int intIdx = 0; intIdx < len; ++intIdx) {
            newlist[intIdx] = seq.value[intIdx];
        }
        newlist[len] = elem;
        seq.value = newlist;
    }

    /**
     * {@.ja シーケンスの末尾にRTObjectオブジェクトを追加する。}
     * {@.en Adds RTObject at the end of CORBA sequence.} 
     *
     * @param seq 
     *   {@.ja RTObjectオブジェクトを追加する
     *   RTCListHolderオブジェクト}
     *   {@.en RTCListHolder object that adds 
     *   RTObject object.}
     * @param elem 
     *   {@.ja 追加するRTObjectオブジェクト}
     *   {@.en RTObject to be added to the CORBA sequence.}
     *
     */
    public static void push_back(RTCListHolder seq, RTObject elem) {
        int len;
        if( seq.value == null ) {
            len = 0;
        } else {
            len = seq.value.length;
        }
        RTObject[] newlist = new RTObject[len + 1];
        for (int intIdx = 0; intIdx < len; ++intIdx) {
            newlist[intIdx] = seq.value[intIdx];
        }
        newlist[len] = elem;
        seq.value = newlist;
    }

    /**
     * {@.ja シーケンスの末尾にRTM.Managerオブジェクトを追加する。}
     * {@.en Adds RTM.Manager at the end of CORBA sequence.} 
     *
     * @param seq 
     *   {@.ja RTM.Managerオブジェクトを追加する
     *   RTM.ManagerListHolderオブジェクト}
     *   {@.en RTM.ManagerListHolder object that adds 
     *   RTM.Manager object.}
     * @param elem 
     *   {@.ja 追加するRTM.Managerオブジェクト}
     *   {@.en RTM.Manager to be added to the CORBA sequence.}
     *
     */
    public static void push_back(RTM.ManagerListHolder seq, RTM.Manager elem) {
        int len;
        if( seq.value == null ) {
            len = 0;
        } else {
            len = seq.value.length;
        }
        RTM.Manager[] newlist = new RTM.Manager[len + 1];
        for (int intIdx = 0; intIdx < len; ++intIdx) {
            newlist[intIdx] = seq.value[intIdx];
        }
        newlist[len] = elem;
        seq.value = newlist;
    }

    /**
     * {@.ja シーケンスの末尾に別のNameValueオブジェクトシーケンスを追加する。}
     * {@.en Adds another NameValue object sequence 
     * to the end of the sequence.} 
     *
     * @param seq1
     *   {@.ja 追加先のNameValueオブジェクトシーケンスを保持する
     *   NVListHolderオブジェクト}
     *   {@.en It is added to this NVListHolder object.}
     * @param seq2 
     *   {@.ja 追加するNameValueオブジェクトシーケンスを保持する
     *   NVListHolderオブジェクト}
     *   {@.en This NVListHolder object is added.}
     *
     */
    public static void push_back_list(NVListHolder seq1, NVListHolder seq2) {
        int len1, len2;
        if( seq1.value==null ) {
            len1 = 0;
        } else {
            len1 = seq1.value.length;
        }
        if( seq2.value==null ) {
            len2 = 0;
        } else {
            len2 = seq2.value.length;
        }
        int len = len1 + len2;
        NameValue[] newlist = new NameValue[len];
        for (int intIdx = 0; intIdx < len1; ++intIdx) {
            newlist[intIdx] = seq1.value[intIdx];
        }
        for (int intIdx = 0; intIdx < len2; ++intIdx) {
            newlist[len1 + intIdx] = seq2.value[intIdx];
        }
        seq1.value = newlist;
    }

    /**
     * {@.ja シーケンスの末尾に別の_SDOPackage.SDOオブジェクトシーケンスを
     * 追加する。}
     * {@.en Adds another _SDOPackage.SDO object sequence 
     * to the end of the sequence.} 
     *
     * @param seq1
     *   {@.ja 追加先の_SDOPackage.SDOオブジェクトシーケンスを保持する
     *   SDOListHolderオブジェクト}
     *   {@.en It is added to this SDOListHolder object.}
     * @param seq2 
     *   {@.ja 追加する_SDOPackage.SDOオブジェクトシーケンスを保持する
     *   SDOListHolderオブジェクト}
     *   {@.en This SDOListHolder object is added.}
     *
     */
    public static void push_back_list(SDOListHolder seq1, SDOListHolder seq2) {
        int len1, len2;
        if( seq1.value==null ) {
            len1 = 0;
        } else {
            len1 = seq1.value.length;
        }
        if( seq2.value==null ) {
            len2 = 0;
        } else {
            len2 = seq2.value.length;
        }
        int len = len1 + len2;
        SDO[] newlist = new SDO[len];
        for (int intIdx = 0; intIdx < len1; ++intIdx) {
            newlist[intIdx] = seq1.value[intIdx];
        }
        for (int intIdx = 0; intIdx < len2; ++intIdx) {
            newlist[len1 + intIdx] = seq2.value[intIdx];
        }
        seq1.value = newlist;
    }

    /**
     * {@.ja シーケンスの末尾に別のRTM.ModuleProfileオブジェクトシーケンスを
     * 追加する。}
     * {@.en Adds another RTM.ModuleProfile object sequence 
     * to the end of the sequence.} 
     *
     * @param seq1
     *   {@.ja 追加先のRTM.ModuleProfileオブジェクトシーケンスを保持する
     *   RTM.ModuleProfileListHolderオブジェクト}
     *   {@.en It is added to this RTM.ModuleProfileListHolder object.}
     * @param seq2 
     *   {@.ja 追加するRTM.ModuleProfileオブジェクトシーケンスを保持する
     *   RTM.ModuleProfileListHolderオブジェクト}
     *   {@.en This RTM.ModuleProfileListHolder object is added.}
     *
     */
    public static void push_back_list(RTM.ModuleProfileListHolder seq1, 
                                       RTM.ModuleProfileListHolder seq2) {
        int len1, len2;
        if( seq1.value==null ) {
            len1 = 0;
        } else {
            len1 = seq1.value.length;
        }
        if( seq2.value==null ) {
            len2 = 0;
        } else {
            len2 = seq2.value.length;
        }
        int len = len1 + len2;
        RTM.ModuleProfile[] newlist = new RTM.ModuleProfile[len];
        for (int intIdx = 0; intIdx < len1; ++intIdx) {
            newlist[intIdx] = seq1.value[intIdx];
        }
        for (int intIdx = 0; intIdx < len2; ++intIdx) {
            newlist[len1 + intIdx] = seq2.value[intIdx];
        }
        seq1.value = newlist;
    }
    

    /**
     * {@.ja シーケンスの末尾に別のRTC.RTObjectオブジェクトシーケンスを
     * 追加する。}
     * {@.en Adds another RTC.RTObject object sequence 
     * to the end of the sequence.} 
     *
     * @param seq1
     *   {@.ja 追加先のRTC.RTObjectオブジェクトシーケンスを保持する
     *   RTC.RTCListHolderオブジェクト}
     *   {@.en It is added to this RTC.RTCListHolder object.}
     * @param seq2 
     *   {@.ja 追加するRTC.RTObjectオブジェクトシーケンスを保持する
     *   RTC.RTCListHolderオブジェクト}
     *   {@.en This RTC.RTCListHolder object is added.}
     *
     */
    public static void push_back_list(RTC.RTCListHolder seq1, 
                                       RTC.RTCListHolder seq2) {
        int len1, len2;
        if( seq1.value==null ) {
            len1 = 0;
        } else {
            len1 = seq1.value.length;
        }
        if( seq2.value==null ) {
            len2 = 0;
        } else {
            len2 = seq2.value.length;
        }
        int len = len1 + len2;
        RTC.RTObject[] newlist = new RTC.RTObject[len];
        for (int intIdx = 0; intIdx < len1; ++intIdx) {
            newlist[intIdx] = seq1.value[intIdx];
        }
        for (int intIdx = 0; intIdx < len2; ++intIdx) {
            newlist[len1 + intIdx] = seq2.value[intIdx];
        }
        seq1.value = newlist;
    }

    /**
     * {@.ja シーケンスの末尾に別のRTC.ComponentProfileオブジェクトシーケンスを
     * 追加する。}
     * {@.en Adds another RTC.ComponentProfile object sequence 
     * to the end of the sequence.} 
     *
     * @param seq1
     *   {@.ja 追加先のRTC.ComponentProfileオブジェクトシーケンスを保持する
     *   RTC.ComponentProfileListHolderオブジェクト}
     *   {@.en It is added to this RTC.ComponentProfileListHolder object.}
     * @param seq2 
     *   {@.ja 追加するRTC.ComponentProfileオブジェクトシーケンスを保持する
     *   RTC.ComponentProfileListHolderオブジェクト}
     *   {@.en This RTC.ComponentProfileListHolder object is added.}
     *
     */
    public static void push_back_list(RTC.ComponentProfileListHolder seq1, 
                                       RTC.ComponentProfileListHolder seq2) {
        int len1, len2;
        if( seq1.value==null ) {
            len1 = 0;
        } else {
            len1 = seq1.value.length;
        }
        if( seq2.value==null ) {
            len2 = 0;
        } else {
            len2 = seq2.value.length;
        }
        int len = len1 + len2;
        RTC.ComponentProfile[] newlist = new RTC.ComponentProfile[len];
        for (int intIdx = 0; intIdx < len1; ++intIdx) {
            newlist[intIdx] = seq1.value[intIdx];
        }
        for (int intIdx = 0; intIdx < len2; ++intIdx) {
            newlist[len1 + intIdx] = seq2.value[intIdx];
        }
        seq1.value = newlist;
    }

    /**
     * {@.ja シーケンス内の指定された位置にNameValueオブジェクトを挿入する。}
     * {@.en Inserts the NameValue object in the specified position 
     * in the sequence.} 
     *
     * @param seq
     *   {@.ja 挿入先のNameValueオブジェクトシーケンスを保持する
     *   NVListHolderオブジェクト}
     *   {@.en NVListHolder object.}
     * @param elem 
     *   {@.ja 挿入するNameValueオブジェクト}
     *   {@.en NameValue object}
     * @param index 
     *   {@.ja 挿入先を指すインデクス}
     *   {@.en Index that indicates insertion destination}
     *
     */
    public static void insert(NVListHolder seq, NameValue elem, int index) {
        int len;
        if( seq.value == null ) {
            len = 0;
        } else {
            len = seq.value.length;
        }
        if (index > len) {
            push_back(seq, elem);
            return;
        }
        NameValue[] newlist = new NameValue[len + 1];
        for (int intIdx = 0; intIdx < index; ++intIdx) {
            newlist[intIdx] = seq.value[intIdx];
        }
        newlist[index] = elem;
        for (int intIdx = len; intIdx > index; --intIdx) {
            newlist[intIdx] = seq.value[intIdx - 1];
        }
        seq.value = newlist;
    }
    
    /**
     * {@.ja シーケンス内の先頭NameValueオブジェクトを取得する}
     * {@.en Get the front element of the CORBA sequence}
     *
     * @param seq 
     *   {@.ja 要素を取得する CORBA sequence}
     *   {@.en CORBA sequence which acquires an element}
     * 
     * @return 
     *   {@.ja シーケンス内の先頭NameValueオブジェクト}
     *   {@.en An acquisition element}
     */
    public static NameValue front(NVListHolder seq) {
        return seq.value[0];
    }
    
    /**
     * {@.ja シーケンス内の末尾NameValueオブジェクトを取得する}
     * {@.en Get the last element of the CORBA sequence}
     * 
     * @param seq 
     *   {@.ja 要素を取得する CORBA sequence}
     *   {@.en The CORBA sequence to be get the element}
     * @return 
     *   {@.ja シーケンス内の末尾NameValueオブジェクト}
     *   {@.en An acquisition element}
     *
     */
    public static NameValue back(NVListHolder seq) {
        return seq.value[seq.value.length - 1];
    }
    
    /**
     * {@.ja シーケンス内の指定された位置の要素を削除する}
     * {@.en Erase the element of the specified index}
     * 
     * @param seq 
     *   {@.ja 削除対象のNameValueオブジェクトシーケンスを保持する
     *   NVListHolderオブジェクト}
     *   {@.en The CORBA sequence to be get the element}
     * @param index 
     *   {@.ja 削除要素のシーケンス内インデクス}
     *   {@.en The index of the element to be removed}
     */
    public static void erase(NVListHolder seq, int index) {
        if( seq.value == null ) return;
        int len = seq.value.length;
        if (index > len) return;
        NameValue[] newlist = new NameValue[len - 1];
        for (int intIdx = 0; intIdx < index; ++intIdx) {
            newlist[intIdx] = seq.value[intIdx];
        }
        for (int intIdx = index; intIdx < len - 1; ++intIdx) {
            newlist[intIdx] = seq.value[intIdx + 1];
        }
        seq.value = newlist;
    }
    
    /**
     * {@.ja シーケンス内の指定された位置の要素を削除する}
     * {@.en Erase the element of the specified index}
     * 
     * @param seq 
     *   {@.ja 削除対象の_SDOPackage.SDOオブジェクトシーケンスを保持する
     *   SDOListHolderオブジェクト}
     *   {@.en The CORBA sequence to be get the element}
     * @param index 
     *   {@.ja 削除要素のシーケンス内インデクス}
     *   {@.en The index of the element to be removed}
     */
    public static void erase(SDOListHolder seq, int index) {
        if( seq.value == null ) return;
        int len = seq.value.length;
        if(index > len) return;
        SDO[] newlist = new SDO[len - 1];
        for(int intIdx = 0; intIdx < index; ++intIdx) {
            newlist[intIdx] = seq.value[intIdx];
        }
        for(int intIdx = index; intIdx < len-1; ++intIdx) {
            newlist[intIdx] = seq.value[intIdx + 1];            
        }
        seq.value = newlist;
    }
    
    /**
     * {@.ja シーケンス内の指定された位置の要素を削除する}
     * {@.en Erase the element of the specified index}
     * 
     * @param seq 
     *   {@.ja 削除対象のServiceProfileオブジェクトシーケンスを保持する
     *   ServiceProfileListHolderオブジェクト}
     *   {@.en The CORBA sequence to be get the element}
     * @param index 
     *   {@.ja 削除要素のシーケンス内インデクス}
     *   {@.en The index of the element to be removed}
     */
    public static void erase(ServiceProfileListHolder seq, int index) {
        if( seq.value == null ) return;
        int len = seq.value.length;
        if (index > len) return;
        ServiceProfile[] newlist = new ServiceProfile[len - 1];
        for (int intIdx = 0; intIdx < index; ++intIdx) {
            newlist[intIdx] = seq.value[intIdx];
        }
        for (int intIdx = index; intIdx < len - 1; ++intIdx) {
            newlist[intIdx] = seq.value[intIdx + 1];
        }
        seq.value = newlist;
    }
    
    /**
     * {@.ja シーケンス内の指定された位置の要素を削除する}
     * {@.en Erase the element of the specified index}
     * 
     * @param seq 
     *   {@.ja 削除対象のOrganizationオブジェクトシーケンスを保持する
     *   OrganizationListHolderオブジェクト}
     *   {@.en The CORBA sequence to be get the element}
     * @param index 
     *   {@.ja 削除要素のシーケンス内インデクス}
     *   {@.en The index of the element to be removed}
     */
    public static void erase(OrganizationListHolder seq, int index) {
        if( seq.value == null ) return;
        int len = seq.value.length;
        if (index > len) return;
        Organization[] newlist = new Organization[len - 1];
        for (int intIdx = 0; intIdx < index; ++intIdx) {
            newlist[intIdx] = seq.value[intIdx];
        }
        for (int intIdx = index; intIdx < len - 1; ++intIdx) {
            newlist[intIdx] = seq.value[intIdx + 1];
        }
        seq.value = newlist;
    }
    
    /**
     * {@.ja シーケンス内の指定された位置の要素を削除する}
     * {@.en Erase the element of the specified index}
     * 
     * @param seq 
     *   {@.ja 削除対象のPortServiceオブジェクトシーケンスを保持する
     *   PortServiceListHolderオブジェクト}
     *   {@.en The CORBA sequence to be get the element}
     * @param index 
     *   {@.ja 削除要素のシーケンス内インデクス}
     *   {@.en The index of the element to be removed}
     */
    public static void erase(PortServiceListHolder seq, int index) {
        if( seq.value == null ) return;
        int len = seq.value.length;
        if (index > len) return;
        PortService[] newlist = new PortService[len - 1];
        for (int intIdx = 0; intIdx < index; ++intIdx) {
            newlist[intIdx] = seq.value[intIdx];
        }
        for (int intIdx = index; intIdx < len - 1; ++intIdx) {
            newlist[intIdx] = seq.value[intIdx + 1];
        }
        seq.value = newlist;
    }
    
    /**
     * {@.ja シーケンス内の指定された位置の要素を削除する}
     * {@.en Erase the element of the specified index}
     * 
     * @param seq 
     *   {@.ja 削除対象のConnectorProfileオブジェクトシーケンスを保持する
     *   ConnectorProfileListHolderオブジェクト}
     *   {@.en The CORBA sequence to be get the element}
     * @param index 
     *   {@.ja 削除要素のシーケンス内インデクス}
     *   {@.en The index of the element to be removed}
     */
    public static void erase(ConnectorProfileListHolder seq, int index) {
        if( seq.value == null ) return;
        int len = seq.value.length;
        if (index > len) return;
        ConnectorProfile[] newlist = new ConnectorProfile[len - 1];
        for (int intIdx = 0; intIdx < index; ++intIdx) {
            newlist[intIdx] = seq.value[intIdx];
        }
        for (int intIdx = index; intIdx < len - 1; ++intIdx) {
            newlist[intIdx] = seq.value[intIdx + 1];
        }
        seq.value = newlist;
    }

    /**
     * {@.ja シーケンス内の指定された位置の要素を削除する}
     * {@.en Erase the element of the specified index}
     * 
     * @param seq 
     *   {@.ja 削除対象のPortInterfaceProfileオブジェクトシーケンスを保持する
     *   PortInterfaceProfileListHolderオブジェクト}
     *   {@.en The CORBA sequence to be get the element}
     * @param index 
     *   {@.ja 削除要素のシーケンス内インデクス}
     *   {@.en The index of the element to be removed}
     */
    public static void erase(PortInterfaceProfileListHolder seq, int index) {
        if( seq.value == null ) return;
        int len = seq.value.length;
        if (index > len) return;
        PortInterfaceProfile[] newlist = new PortInterfaceProfile[len - 1];
        for (int intIdx = 0; intIdx < index; ++intIdx) {
            newlist[intIdx] = seq.value[intIdx];
        }
        for (int intIdx = index; intIdx < len - 1; ++intIdx) {
            newlist[intIdx] = seq.value[intIdx + 1];
        }
        seq.value = newlist;
    }

    /**
     * {@.ja シーケンス内の指定された位置の要素を削除する}
     * {@.en Erase the element of the specified index}
     * 
     * @param seq 
     *   {@.ja 削除対象のExecutionContextServiceオブジェクトシーケンスを保持する
     *   ExecutionContextServiceListHolderオブジェクト}
     *   {@.en The CORBA sequence to be get the element}
     * @param index 
     *   {@.ja 削除要素のシーケンス内インデクス}
     *   {@.en The index of the element to be removed}
     */
    public static void erase(ExecutionContextServiceListHolder seq, int index) {
        if ( seq.value == null ) return;
        int len = seq.value.length;
        if (index > len)  return;
        ExecutionContextService[] newlist = new ExecutionContextService[len - 1];
        for (int intIdx = 0; intIdx < index; ++intIdx) {
            newlist[intIdx] = seq.value[intIdx];
        }
        for (int intIdx = index; intIdx < len - 1; ++intIdx) {
            newlist[intIdx] = seq.value[intIdx + 1];
        }
        seq.value = newlist;
    }

    /**
     * {@.ja シーケンス内の指定された位置の要素を削除する}
     * {@.en Erase the element of the specified index}
     * 
     * @param seq 
     *   {@.ja 削除対象のRTM.Managerオブジェクトシーケンスを保持する
     *   RTM.ManagerListHolderオブジェクト}
     *   {@.en The CORBA sequence to be get the element}
     * @param index 
     *   {@.ja 削除要素のシーケンス内インデクス}
     *   {@.en The index of the element to be removed}
     */
    public static void erase(RTM.ManagerListHolder seq, int index) {
        if ( seq.value == null ) return;
        int len = seq.value.length;
        if (index > len)  return;
        RTM.Manager[] newlist = new RTM.Manager[len - 1];
        for (int intIdx = 0; intIdx < index; ++intIdx) {
            newlist[intIdx] = seq.value[intIdx];
        }
        for (int intIdx = index; intIdx < len - 1; ++intIdx) {
            newlist[intIdx] = seq.value[intIdx + 1];
        }
        seq.value = newlist;
    }

    /**
     * {@.ja シーケンス内の指定された位置の要素を削除する}
     * {@.en Erase the element of the specified index}
     * 
     * @param seq 
     *   {@.ja 削除対象のRTC.RTObjectオブジェクトシーケンスを保持する
     *   RTC.RTCListHolderオブジェクト}
     *   {@.en The CORBA sequence to be get the element}
     * @param index 
     *   {@.ja 削除要素のシーケンス内インデクス}
     *   {@.en The index of the element to be removed}
     */
    public static void erase(RTC.RTCListHolder seq, int index) {
        if ( seq.value == null ) return;
        int len = seq.value.length;
        if (index > len)  return;
        RTC.RTObject[] newlist = new RTC.RTObject[len - 1];
        for (int intIdx = 0; intIdx < index; ++intIdx) {
            newlist[intIdx] = seq.value[intIdx];
        }
        for (int intIdx = index; intIdx < len - 1; ++intIdx) {
            newlist[intIdx] = seq.value[intIdx + 1];
        }
        seq.value = newlist;
    }
    /**
     * {@.ja シーケンス内の条件に合致する要素を削除する}
     * {@.en Remove an element of a sequence according to a predicate}
     * 
     * @param seq 
     *   {@.ja NameValueオブジェクトシーケンスを保持するi
     *   NVListHolderオブジェクト}
     *   {@.en target CORBA sequence}
     * @param func 
     *   {@.ja 合致判定を行うequalFunctorオブジェクト}
     *   {@.en predicate which decides a sequence to remove}
     */
    public static void erase_if(NVListHolder seq, equalFunctor func) {
        
        int index = find(seq, func);
        if (index < 0) {
            return;
        }
        CORBA_SeqUtil.erase(seq, index);
    }
    
    /**
     * {@.ja シーケンス内の条件に合致する要素を削除する}
     * {@.en Remove an element of a sequence according to a predicate}
     * 
     * @param seq 
     *   {@.ja PortServiceオブジェクトシーケンスを保持するi
     *   PortServiceListHolderオブジェクト}
     *   {@.en target CORBA sequence}
     * @param f 
     *   {@.ja 合致判定を行うequalFunctorオブジェクト}
     *   {@.en predicate which decides a sequence to remove}
     */
    public static void erase_if(PortServiceListHolder seq, equalFunctor f) {
        
        int index = find(seq, f);
        if (index < 0) {
            return;
        }
        CORBA_SeqUtil.erase(seq, index);
    }

    /**
     * {@.ja 指定されたシーケンスの全要素を削除}
     * {@.en Erase all the elements of the CORBA sequence}
     * 
     * @param seq 
     *   {@.ja NameValueオブジェクトシーケンスを保持する
     *   NVListHolderオブジェクト}
     *   {@.en NVListHolder object} 
     */
    public static void clear(NVListHolder seq) {
        seq.value = new NameValue[0];
    }

    /**
     *  {@.ja オブジェクトをIORに変換する}
     *  {@.en converts the object into IOR}
     *  @param objlist
     *    {@.ja オブジェクトのリスト}
     *    {@.en List of object}
     *  @return Vector<String>
     *    {@.ja List of object}
     *    {@.en List of character string}
     */
    public static <T> Vector<String> refToVstring(final T[] objlist) {
        Vector<String> iorlist = new Vector<String>();
        ORB orb = ORBUtil.getOrb();

        for (int i=0, len=objlist.length; i < len; ++i) {
            iorlist.add(orb.object_to_string((org.omg.CORBA.Object)objlist[i]));
        }
        return iorlist;
    }
}
