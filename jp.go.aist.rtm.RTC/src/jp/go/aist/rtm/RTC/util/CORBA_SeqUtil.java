package jp.go.aist.rtm.RTC.util;

import _SDOPackage.NVListHolder;
import _SDOPackage.NameValue;
import _SDOPackage.Organization;
import _SDOPackage.OrganizationListHolder;
import _SDOPackage.SDO;
import _SDOPackage.SDOListHolder;
import _SDOPackage.ServiceProfile;
import _SDOPackage.ServiceProfileListHolder;
import RTC.ConnectorProfile;
import RTC.ConnectorProfileListHolder;
import RTC.ExecutionContextService;
import RTC.ExecutionContextServiceListHolder;
import RTC.PortService;
import RTC.PortInterfaceProfile;
import RTC.PortInterfaceProfileListHolder;
import RTC.PortServiceListHolder;
import RTC.PortProfile;
import RTC.PortProfileListHolder;

/**
 * <p>CORBAシーケンスに対するユーティリティクラスです。各メソッドはスレッドセーフではないため、
 * 必要に応じて呼び出し側で適切に排他処理を行ってください。</p>
 */
public class CORBA_SeqUtil {

    /**
     * <p>指定されたシーケンス内の各NameValueオブジェクト対して、順次、指定された操作を行います。</p>
     * 
     * @param seq NameValueオブジェクトシーケンスを内部に保持するNVListHolderオブジェクト
     * @param func 各NameValueオブジェクトに適用するoperatorFuncオブジェクト
     * @return 引数で指定されたoperatorFuncオブジェクト
     */
    public static operatorFunc for_each(NVListHolder seq, operatorFunc func) {
        if( seq.value==null ) return null;
        for (int i = 0; i < seq.value.length; ++i) {
            func.operator(seq.value[i]);
        }
        
        return func;
    }
    
    /**
     * <p>指定されたシーケンス内の各ConnectorProfileオブジェクトに対して、順次、指定された操作を行います。</p>
     * 
     * @param seq ConnectorProfileオブジェクトシーケンスを内部に保持するConnectorProfileListHolderオブジェクト
     * @param func 各ConnectorProfileオブジェクトに適用するoperatorFuncオブジェクト
     * @return 引数で指定されたoperatorFuncオブジェクト
     */
    public static operatorFunc for_each(ConnectorProfileListHolder seq, operatorFunc func) {
        if( seq.value==null ) return null;
        for (int i = 0; i < seq.value.length; ++i) {
            func.operator(seq.value[i]);
        }
        
        return func;
    }
    
    /**
     * <p>指定されたシーケンス内の指定条件に合致するNameValueオブジェクトのインデクスを取得します。</p>
     * 
     * @param seq NameValueオブジェクトシーケンスを保持するNVListHolderオブジェクト
     * @param func 合致判定を行うequalFunctorオブジェクト
     * @return 合致するNameValueオブジェクトが存在する場合は、そのオブジェクトのシーケンス内でのインデクスを返します。<br />
     * 合致するNameValueオブジェクトが存在しない場合は、-1を返します。
     */
    public static int find(final NVListHolder seq, equalFunctor func) {
        if( seq.value==null ) return -1;
        for (int i = 0; i < seq.value.length; ++i) {
            if (func.equalof(seq.value[i])) {
                return i;
            }
        }
        
        return -1;
    }
    
    /**
     * <p>指定されたシーケンス内の指定条件に合致するPortオブジェクトのインデクスを取得します。</p>
     * 
     * @param seq Portオブジェクトシーケンスを保持するPortServiceListHolderオブジェクト
     * @param func 合致判定を行うequalFunctorオブジェクト
     * @return 合致するPortオブジェクトが存在する場合は、そのオブジェクトのシーケンス内でのインデクスを返します。<br />
     * 合致するPortオブジェクトが存在しない場合は、-1を返します。
     */
    public static int find(final PortServiceListHolder seq, equalFunctor func) {
        if( seq.value==null ) return -1;
        for (int i = 0; i < seq.value.length; ++i) {
            if (func.equalof(seq.value[i])) {
                return i;
            }
        }
        
        return -1;
    }
    
    /**
     * <p>指定されたシーケンス内の指定条件に合致するPortInterfaceProfileオブジェクトのインデクスを取得します。</p>
     * 
     * @param seq PortInterfaceProfileオブジェクトシーケンスを保持するPortInterfaceProfileListHolderオブジェクト
     * @param func 合致判定を行うequalFunctorオブジェクト
     * @return 合致するPortInterfaceProfileオブジェクトが存在する場合は、そのオブジェクトのシーケンス内でのインデクスを返します。<br />
     * 合致するPortInterfaceProfileオブジェクトが存在しない場合は、-1を返します。
     */
    public static int find(final PortInterfaceProfileListHolder seq, equalFunctor func) {
        if( seq.value==null ) return -1;
        for (int i = 0; i < seq.value.length; ++i) {
            if (func.equalof(seq.value[i])) {
                return i;
            }
        }
        
        return -1;
    }
    
    /**
     * <p>指定されたシーケンス内の指定条件に合致するConnectorProfileオブジェクトのインデクスを取得します。</p>
     * 
     * @param seq ConnectorProfileオブジェクトシーケンスを保持するConnectorProfileListHolderオブジェクト
     * @param f 合致判定を行うequalFunctorオブジェクト
     * @return 合致するConnectorProfileオブジェクトが存在する場合は、そのオブジェクトのシーケンス内でのインデクスを返します。<br />
     * 合致するConnectorProfileオブジェクトが存在しない場合は、-1を返します。
     */
    public static int find(final ConnectorProfileListHolder seq, equalFunctor f) {
        if( seq.value==null ) return -1;
        for (int i = 0; i < seq.value.length; ++i) {
            if (f.equalof(seq.value[i])) {
                return i;
            }
        }
        
        return -1;
    }
    
    /**
     * <p>シーケンスの末尾にNameValueオブジェクトを追加します。</p>
     * 
     * @param seq 追加先NameValueオブジェクトシーケンスを保持するNVListHolderオブジェクト
     * @param elem 追加するNameValueオブジェクト
     */
    public static void push_back(NVListHolder seq, NameValue elem) {
        int len;
        if( seq.value==null ) {
            len = 0;
        } else {
            len = seq.value.length;
        }
        NameValue[] newlist = new NameValue[len + 1];
        for (int intIdx = 0; intIdx < len; intIdx++) {
            newlist[intIdx] = seq.value[intIdx];
        }
        newlist[len] = elem;
        seq.value = newlist;
    }

    /**
     * <p>シーケンスの末尾にServiceProfileオブジェクトを追加します。</p>
     * 
     * @param seq 追加先ServiceProfileオブジェクトシーケンスを保持するServiceProfileListHolderオブジェクト
     * @param elem 追加するServiceProfileオブジェクト
     */
    public static void push_back(ServiceProfileListHolder seq, ServiceProfile elem) {
        int len;
        if( seq.value==null ) {
            len = 0;
        } else {
            len = seq.value.length;
        }
        ServiceProfile[] newlist = new ServiceProfile[len + 1];
        for (int intIdx = 0; intIdx < len; intIdx++) {
            newlist[intIdx] = seq.value[intIdx];
        }
        newlist[len] = elem;
        seq.value = newlist;
    }

    /**
     * <p>シーケンスの末尾にOrganizationオブジェクトを追加します。</p>
     * 
     * @param seq 追加先Organizationオブジェクトシーケンスを保持するOrganizationListHolderオブジェクト
     * @param elem 追加するOrganizationオブジェクト
     */
    public static void push_back(OrganizationListHolder seq, Organization elem) {
        int len;
        if( seq.value==null ) {
            len = 0;
        } else {
            len = seq.value.length;
        }
        Organization[] newlist = new Organization[len + 1];
        for (int intIdx = 0; intIdx < len; intIdx++) {
            newlist[intIdx] = seq.value[intIdx];
        }
        newlist[len] = elem;
        seq.value = newlist;
    }

    /**
     * <p>シーケンスの末尾にExecutionContextServiceオブジェクトを追加します。</p>
     * 
     * @param seq 追加先ExecutionContextServiceオブジェクトシーケンスを保持するExecutionContextServiceListHolderオブジェクト
     * @param elem 追加するExecutionContextServiceオブジェクト
     */
    public static void push_back(ExecutionContextServiceListHolder seq, ExecutionContextService elem) {
        int len;
        if( seq.value==null ) {
            len = 0;
        } else {
            len = seq.value.length;
        }
        ExecutionContextService[] newlist = new ExecutionContextService[len + 1];
        for (int intIdx = 0; intIdx < len; intIdx++) {
            newlist[intIdx] = seq.value[intIdx];
        }
        newlist[len] = elem;
        seq.value = newlist;
    }

    /**
     * <p>シーケンスの末尾にPortオブジェクトを追加します。</p>
     * 
     * @param seq 追加先Portオブジェクトシーケンスを保持するPortServiceListHolderオブジェクト
     * @param elem 追加するPortオブジェクト
     */
    public static void push_back(PortServiceListHolder seq, PortService elem) {
        int len;
        if( seq.value==null ) {
            len = 0;
        } else {
            len = seq.value.length;
        }
        PortService[] newlist = new PortService[len + 1];
        for (int intIdx = 0; intIdx < len; intIdx++) {
            newlist[intIdx] = seq.value[intIdx];
        }
        newlist[len] = elem;
        seq.value = newlist;
    }

    /**
     * <p>シーケンスの末尾にConnectorProfileオブジェクトを追加します。</p>
     * 
     * @param seq 追加先ConnectorProfileオブジェクトシーケンスを保持するConnectorProfileListHolderオブジェクト
     * @param elem 追加するConnectorProfileオブジェクト
     */
    public static void push_back(ConnectorProfileListHolder seq, ConnectorProfile elem) {
        int len;
        if( seq.value==null ) {
            len = 0;
        } else {
            len = seq.value.length;
        }
        ConnectorProfile[] newlist = new ConnectorProfile[len + 1];
        for (int intIdx = 0; intIdx < len; intIdx++) {
            newlist[intIdx] = seq.value[intIdx];
        }
        newlist[len] = elem;
        seq.value = newlist;
    }

    /**
     * <p>シーケンスの末尾にPortInterfaceProfileオブジェクトを追加します。</p>
     * 
     * @param seq 追加先PortInterfaceProfileオブジェクトシーケンスを保持するPortInterfaceProfileListHolderオブジェクト
     * @param elem 追加するPortInterfaceProfileオブジェクト
     */
    public static void push_back(PortInterfaceProfileListHolder seq, PortInterfaceProfile elem) {
        int len;
        if( seq.value==null ) {
            len = 0;
        } else {
            len = seq.value.length;
        }
        PortInterfaceProfile[] newlist = new PortInterfaceProfile[len + 1];
        for (int i = 0; i < len; i++) {
            newlist[i] = seq.value[i];
        }
        newlist[len] = elem;
        seq.value = newlist;
    }
    
    /**
     * <p>シーケンスの末尾にPortProfileオブジェクトを追加します。</p>
     * 
     * @param seq 追加先PortProfileオブジェクトシーケンスを保持するPortProfileListHolderオブジェクト
     * @param elem 追加するPortProfileオブジェクト
     */
    public static void push_back(PortProfileListHolder seq, PortProfile elem) {
        int len;
        if( seq.value==null ) {
            len = 0;
        } else {
            len = seq.value.length;
        }
        PortProfile[] newlist = new PortProfile[len + 1];
        for (int i = 0; i < len; i++) {
            newlist[i] = seq.value[i];
        }
        newlist[len] = elem;
        seq.value = newlist;
    }
    /**
     * <p>シーケンスの末尾に、別のNameValueオブジェクトシーケンスを追加します。</p>
     * 
     * @param seq1 追加先のNameValueオブジェクトシーケンスを保持するNVListHolderオブジェクト
     * @param seq2 追加するNameValueオブジェクトシーケンスを保持するNVListHolderオブジェクト
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
        for (int intIdx = 0; intIdx < len1; intIdx++) {
            newlist[intIdx] = seq1.value[intIdx];
        }
        for (int intIdx = 0; intIdx < len2; intIdx++) {
            newlist[len1 + intIdx] = seq2.value[intIdx];
        }
        seq1.value = newlist;
    }

    /**
     * <p>シーケンスの末尾に、別の_SDOPackage.SDOオブジェクトシーケンスを追加します。</p>
     * 
     * @param seq1 追加先の_SDOPackage.SDOオブジェクトシーケンスを保持するSDOListHolderオブジェクト
     * @param seq2 追加する_SDOPackage.SDOオブジェクトシーケンスを保持するSDOListHolderオブジェクト
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
        for (int intIdx = 0; intIdx < len1; intIdx++) {
            newlist[intIdx] = seq1.value[intIdx];
        }
        for (int intIdx = 0; intIdx < len2; intIdx++) {
            newlist[len1 + intIdx] = seq2.value[intIdx];
        }
        seq1.value = newlist;
    }
    
    /**
     * <p>シーケンス内の指定された位置にNameValueオブジェクトを挿入します。</p>
     * 
     * @param seq 挿入先のNameValueオブジェクトシーケンスを保持するNVListHolderオブジェクト
     * @param elem 挿入するNameValueオブジェクト
     * @param index 挿入先を指すインデクス
     */
    public static void insert(NVListHolder seq, NameValue elem, int index) {
        int len;
        if( seq.value==null ) {
            len = 0;
        } else {
            len = seq.value.length;
        }
        if (index > len) {
            push_back(seq, elem);
            return;
        }
        NameValue[] newlist = new NameValue[len + 1];
        for (int intIdx = 0; intIdx < index; intIdx++) {
            newlist[intIdx] = seq.value[intIdx];
        }
        newlist[index] = elem;
        for (int intIdx = len; intIdx > index; --intIdx) {
            newlist[intIdx] = seq.value[intIdx - 1];
        }
        seq.value = newlist;
    }
    
    /**
     * <p>シーケンス内の先頭NameValueオブジェクトを取得します。</p>
     * 
     * @return シーケンス内の先頭NameValueオブジェクト
     */
    public static NameValue front(NVListHolder seq) {
        return seq.value[0];
    }
    
    /**
     * <p>シーケンス内の末尾NameValueオブジェクトを取得します。</p>
     * 
     * @return シーケンス内の末尾NameValueオブジェクト
     */
    public static NameValue back(NVListHolder seq) {
        return seq.value[seq.value.length - 1];
    }
    
    /**
     * <p>シーケンス内の指定された位置の要素を削除します。</p>
     * 
     * @param seq 削除対象のNameValueオブジェクトシーケンスを保持するNVListHolderオブジェクト
     * @param index 削除要素のシーケンス内インデクス
     */
    public static void erase(NVListHolder seq, int index) {
        if( seq.value==null ) return;
        int len = seq.value.length;
        if (index > len) return;
        NameValue[] newlist = new NameValue[len - 1];
        for (int intIdx = 0; intIdx < index; intIdx++) {
            newlist[intIdx] = seq.value[intIdx];
        }
        for (int intIdx = index; intIdx < len - 1; ++intIdx) {
            newlist[intIdx] = seq.value[intIdx + 1];
        }
        seq.value = newlist;
    }
    
    /**
     * <p>シーケンス内の指定された位置の要素を削除します。</p>
     * 
     * @param seq 削除対象の_SDOPackage.SDOオブジェクトシーケンスを保持するSDOListHolderオブジェクト
     * @param index 削除要素のシーケンス内インデクス
     */
    public static void erase(SDOListHolder seq, int index) {
        if( seq.value==null ) return;
        int len = seq.value.length;
        if(index > len) return;
        SDO[] newlist = new SDO[len - 1];
        for(int intIdx = 0; intIdx < index; intIdx++) {
            newlist[intIdx] = seq.value[intIdx];
        }
        for(int intIdx = index; intIdx < len-1; ++intIdx) {
            newlist[intIdx] = seq.value[intIdx + 1];            
        }
        seq.value = newlist;
    }
    
    /**
     * <p>シーケンス内の指定された位置の要素を削除します。</p>
     * 
     * @param seq 削除対象のServiceProfileオブジェクトシーケンスを保持するServiceProfileListHolderオブジェクト
     * @param index 削除要素のシーケンス内インデクス
     */
    public static void erase(ServiceProfileListHolder seq, int index) {
        if( seq.value==null ) return;
        int len = seq.value.length;
        if (index > len) return;
        ServiceProfile[] newlist = new ServiceProfile[len - 1];
        for (int intIdx = 0; intIdx < index; intIdx++) {
            newlist[intIdx] = seq.value[intIdx];
        }
        for (int intIdx = index; intIdx < len - 1; ++intIdx) {
            newlist[intIdx] = seq.value[intIdx + 1];
        }
        seq.value = newlist;
    }
    
    /**
     * <p>シーケンス内の指定された位置の要素を削除します。</p>
     * 
     * @param seq 削除対象のOrganizationオブジェクトシーケンスを保持するOrganizationListHolderオブジェクト
     * @param index 削除要素のシーケンス内インデクス
     */
    public static void erase(OrganizationListHolder seq, int index) {
        if( seq.value==null ) return;
        int len = seq.value.length;
        if (index > len) return;
        Organization[] newlist = new Organization[len - 1];
        for (int intIdx = 0; intIdx < index; intIdx++) {
            newlist[intIdx] = seq.value[intIdx];
        }
        for (int intIdx = index; intIdx < len - 1; ++intIdx) {
            newlist[intIdx] = seq.value[intIdx + 1];
        }
        seq.value = newlist;
    }
    
    /**
     * <p>シーケンス内の指定された位置の要素を削除します。</p>
     * 
     * @param seq 削除対象のPortオブジェクトシーケンスを保持するPortServiceListHolderオブジェクト
     * @param index 削除要素のシーケンス内インデクス
     */
    public static void erase(PortServiceListHolder seq, int index) {
        if( seq.value==null ) return;
        int len = seq.value.length;
        if (index > len) return;
        PortService[] newlist = new PortService[len - 1];
        for (int intIdx = 0; intIdx < index; intIdx++) {
            newlist[intIdx] = seq.value[intIdx];
        }
        for (int intIdx = index; intIdx < len - 1; ++intIdx) {
            newlist[intIdx] = seq.value[intIdx + 1];
        }
        seq.value = newlist;
    }
    
    /**
     * <p>シーケンス内の指定された位置の要素を削除します。</p>
     * 
     * @param seq 削除対象のConnectorProfileオブジェクトシーケンスを保持するConnectorProfileListHolderオブジェクト
     * @param index 削除要素のシーケンス内インデクス
     */
    public static void erase(ConnectorProfileListHolder seq, int index) {
        if( seq.value==null ) return;
        int len = seq.value.length;
        if (index > len) return;
        ConnectorProfile[] newlist = new ConnectorProfile[len - 1];
        for (int intIdx = 0; intIdx < index; intIdx++) {
            newlist[intIdx] = seq.value[intIdx];
        }
        for (int intIdx = index; intIdx < len - 1; ++intIdx) {
            newlist[intIdx] = seq.value[intIdx + 1];
        }
        seq.value = newlist;
    }

    /**
     * <p>シーケンス内の指定された位置の要素を削除します。</p>
     * 
     * @param seq 削除対象のPortInterfaceProfileオブジェクトシーケンスを保持するPortInterfaceProfileListHolderオブジェクト
     * @param index 削除要素のシーケンス内インデクス
     */
    public static void erase(PortInterfaceProfileListHolder seq, int index) {
        if( seq.value==null ) return;
        int len = seq.value.length;
        if (index > len) return;
        PortInterfaceProfile[] newlist = new PortInterfaceProfile[len - 1];
        for (int intIdx = 0; intIdx < index; intIdx++) {
            newlist[intIdx] = seq.value[intIdx];
        }
        for (int intIdx = index; intIdx < len - 1; ++intIdx) {
            newlist[intIdx] = seq.value[intIdx + 1];
        }
        seq.value = newlist;
    }

    /**
     * <p>シーケンス内の条件に合致する要素を削除します。</p>
     * 
     * @param seq NameValueオブジェクトシーケンスを保持するNVListHolderオブジェクト
     * @param func 合致判定を行うequalFunctorオブジェクト
     */
    public static void erase_if(NVListHolder seq, equalFunctor func) {
        
        int index = find(seq, func);
        if (index < 0) {
            return;
        }
        CORBA_SeqUtil.erase(seq, index);
    }
    
    /**
     * <p>シーケンス内の条件に合致する要素を削除します。</p>
     * 
     * @param seq Portオブジェクトシーケンスを保持するPortServiceListHolderオブジェクト
     * @param f 合致判定を行うequalFunctorオブジェクト
     */
    public static void erase_if(PortServiceListHolder seq, equalFunctor f) {
        
        int index = find(seq, f);
        if (index < 0) {
            return;
        }
        CORBA_SeqUtil.erase(seq, index);
    }

    /**
     * <p>指定されたシーケンスの全要素を削除します。</p>
     * 
     * @param seq NameValueオブジェクトシーケンスを保持するNVListHolderオブジェクト
     */
    public static void clear(NVListHolder seq) {
        seq.value = new NameValue[0];
    }
    
}
