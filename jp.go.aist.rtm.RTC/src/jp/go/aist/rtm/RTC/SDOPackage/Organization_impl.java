package jp.go.aist.rtm.RTC.SDOPackage;

import java.util.UUID;

import jp.go.aist.rtm.RTC.log.Logbuf;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.ORBUtil;
import jp.go.aist.rtm.RTC.util.POAUtil;
import jp.go.aist.rtm.RTC.util.equalFunctor;

import org.omg.CORBA.Any;
import org.omg.CORBA.SystemException;

import _SDOPackage.DependencyType;
import _SDOPackage.InternalError;
import _SDOPackage.InvalidParameter;
import _SDOPackage.NVListHolder;
import _SDOPackage.NameValue;
import _SDOPackage.NotAvailable;
import _SDOPackage.Organization;
import _SDOPackage.OrganizationHelper;
import _SDOPackage.OrganizationPOA;
import _SDOPackage.OrganizationProperty;
import _SDOPackage.SDO;
import _SDOPackage.SDOListHolder;
import _SDOPackage.SDOSystemElement;

/**
* {@.ja SDO Organizationの実装クラス}
* {@.en Implementation class of Organization}
*/
public class Organization_impl extends OrganizationPOA{

    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     */
    public Organization_impl() {
        m_pId = UUID.randomUUID().toString();
        rtcout = new Logbuf("Organization_impl");
    }
    /**
     * {@.ja コンストラクタ}
     * {@.en constructor}
     * 
     * @param sdo
     *   {@.ja オーナーオブジェクト}
     *   {@.en Owner object}
     */
    public Organization_impl(SDOSystemElement sdo) {
        m_pId = UUID.randomUUID().toString();
        m_varOwner = sdo;
        m_dependency = DependencyType.OWN;
        m_objref = this._this();

        rtcout = new Logbuf("Organization_impl");
    }

    /**
     * _this
     *
     * @return a CORBA object reference 
     */
    public Organization _this() {
        
        if (this.m_objref == null) {
            try {
                this.m_objref = OrganizationHelper.narrow(POAUtil.getRef(this));
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        
        return this.m_objref;
    }

    /**
     * {@.ja [CORBA interface] Organization ID を取得する}
     * {@.en [CORBA interface] Gets Organization ID}
     *
     * @return 
     *   {@.ja Resource Data Model で定義された Organization ID}
     *   {@.en Organization ID defined by Data Model}
     * 
     * @exception SDONotExists 
     *   {@.ja ターゲットのSDOが存在しない}
     *   {@.en SDO of the target doesn't exist.}
     * @exception NotAvailable 
     *   {@.ja SDOは存在するが応答がない}
     *   {@.en There is no response though SDO exists.}
     * @exception InternalError 
     *   {@.ja 内部的エラーが発生した}
     *   {@.en An internal error occurred.}
     */
    public String get_organization_id() throws InvalidParameter, NotAvailable, InternalError {
        rtcout.println(Logbuf.TRACE, "Organization_impl.get_organization_id() = " + m_pId);
        return m_pId;
    }
    /**
     * {@.ja [CORBA interface] OrganizationProperty を取得する。}
     * {@.en [CORBA interface] Gets OrganizationProperty}
     * <p>
     * Organization が所有する OrganizationProperty を返すオペレーション。
     * Organization がプロパティを持たなければ空のリストを返す。
     *
     * @return 
     *   {@.ja Organization のプロパティのリスト}
     *   {@.en Property list of Organization}
     * 
     * @exception SDONotExists 
     *   {@.ja ターゲットのSDOが存在しない}
     *   {@.en SDO of the target doesn't exist.}
     * @exception NotAvailable 
     *   {@.ja SDOは存在するが応答がない}
     *   {@.en There is no response though SDO exists.}
     * @exception InternalError 
     *   {@.ja 内部的エラーが発生した}
     *   {@.en An internal error occurred.}
     */
    public synchronized OrganizationProperty get_organization_property() 
                                            throws NotAvailable, InternalError {
        rtcout.println(Logbuf.TRACE, 
                       "Organization_impl.get_organization_property()");
        try {
	    OrganizationProperty prop;
            if(m_orgProperty.properties == null){
                NVListHolder holder  = new NVListHolder();
                CORBA_SeqUtil.push_back(holder, 
                                        NVUtil.newNV("", "", String.class));
                prop = new OrganizationProperty(holder.value);
            }
            else{
   	        prop = new OrganizationProperty(m_orgProperty.properties);
            }
	    return prop;
        } catch (Exception ex) {
            throw new InternalError("get_organization_property()");
        }
    }
    /**
     * {@.ja [CORBA interface] OrganizationProperty の特定の値を取得する。}
     * {@.en [CORBA interface] Gets a specific value of OrganizationProperty}
     * <p>
     * OrganizationProperty の指定された値を返すオペレーション。
     * 引数 "name" で指定されたプロパティの値を返します。</p>
     *
     * @param name 
     *   {@.ja 値を返すプロパティの名前}
     *   {@.en name of Property}
     * 
     * @return 
     *   {@.ja 引数 "name" で指定されたプロパティの値}
     *   {@.en value of Property}
     * 
     * @exception SDONotExists 
     *   {@.ja ターゲットのSDOが存在しない}
     *   {@.en SDO of the target doesn't exist.}
     * @exception InvalidParameter 
     *   {@.ja 引数 "namne" で指定されたプロパティが存在しない}
     *   {@.en The specified property doesn't exist.}
     * @exception NotAvailable 
     *   {@.ja SDOは存在するが応答がない}
     *   {@.en There is no response though SDO exists.}
     * @exception InternalError 
     *   {@.ja 内部的エラーが発生した}
     *   {@.en An internal error occurred.}
     */
    public Any get_organization_property_value(final String name) throws InvalidParameter, NotAvailable, InternalError {
        rtcout.println(Logbuf.TRACE, "Organization_impl.get_organization_property_value("+name+")");
        if( name==null || name.equals("") ) throw new InvalidParameter("Rmpty name.");
        NVListHolder nvlist = new NVListHolder();
        nvlist.value = m_orgProperty.properties;
        int index = CORBA_SeqUtil.find(nvlist, new nv_name(name));

        if(index < 0) throw new InvalidParameter("Not Found.");
        try {
            Any value = ORBUtil.getOrb().create_any();
            value = m_orgProperty.properties[index].value;
            return value;
        } catch (Exception ex) {
            throw new InternalError("get_organization_property_value()");
        }
//        return ORBUtil.getOrb().create_any();
    }

    /**
     * {@.ja [CORBA interface] OrganizationProperty を設定する。}
     * {@.en [CORBA interface] Sets OrganizationProperty}
     * <p>
     * OrganizationProperty を Organization に追加するオペレーション。
     * OrganizationProperty は Organization のプロパティ記述。</p>
     *
     * @param organization_property 
     *   {@.ja セットする OrganizationProperty}
     *   {@.en OrganizationProperty}
     * 
     * @return 
     *   {@.ja オペレーションが成功したかどうかを返す。}
     *   {@.en The success is true.}
     * 
     * @exception SDONotExists 
     *   {@.ja ターゲットのSDOが存在しない。}
     *   {@.en SDO of the target doesn't exist.}
     * @exception InvalidParameter 
     *   {@.ja "org_property" が null。}
     *   {@.en "org_property" is null}
     * @exception NotAvailable 
     *   {@.ja SDOは存在するが応答がない。}
     *   {@.en There is no response though SDO exists.}
     * @exception InternalError 
     *   {@.ja 内部的エラーが発生した}
     *   {@.en An internal error occurred.}
     */
    // TODO ※ SDO Specification の PIM 記述とオペレーション名が異なる。
    //       ※ addOrganizationProperty に対応か？
    public boolean set_organization_property(final OrganizationProperty organization_property) 
            throws InvalidParameter, NotAvailable, InternalError {
        rtcout.println(Logbuf.TRACE, "Organization_impl.set_organization_property()");
        try {
            if( m_orgProperty==null ) m_orgProperty = new OrganizationProperty();
            synchronized (m_orgProperty) {
                m_orgProperty = organization_property;
                return true;
            }
        } catch (Exception ex) {
            throw new InternalError("set_organization_property()");
        }
//        return false;
    }

    /**
     * {@.ja [CORBA interface] OrganizationProperty のセット}
     * {@.en [CORBA interface] Set OrganizationProperty}
     *
     * <p>
     * {@.ja ※ SDO Specification の PIM 記述とオペレーション名が異なる。
     * ※ addOrganizationProperty に対応か？<BR>
     * OrganizationProperty を Organization に追加するオペレーション。
     * OrganizationProperty は Organization のプロパティ記述である。}
     * {@.en Note: The PIM description of SDO Specification differs from 
     * the operation name.
     * Note: Does this operation correspond to addOrganizationProperty?
     * This operation adds the OrganizationProperty to an Organization. The
     * OrganizationProperty is the property description of an Organization.}
     *
     * @param organization_property 
     *   {@.ja セットする OrganizationProperty}
     *   {@.en The type of organization to be added.}
     *
     * @return 
     *   {@.ja オペレーションが成功したかどうかを返す。}
     *   {@.en If the operation was successfully completed.}
     *
     * @exception SDONotExists 
     *   {@.ja ターゲットのSDOが存在しない。(本例外は、CORBA標準
     *         システム例外のOBJECT_NOT_EXISTにマッピングされる)}
     *   {@.en The target SDO does not exist.(This exception 
     *         is mapped to CORBA standard system exception
     *         OBJECT_NOT_EXIST.)}
     * @exception InvalidParameter 
     *   {@.ja "org_property" が null。}
     *   {@.en The argument "organizationProperty" is null.}
     * @exception NotAvailable 
     *   {@.ja SDOは存在するが応答がない。}
     *   {@.en The target SDO is reachable but cannot respond.}
     * @exception InternalError 
     *   {@.ja 内部的エラーが発生した。}
     *   {@.en The target SDO cannot execute the operation
     *         completely due to some internal error.}
     */
    public boolean add_organization_property(final OrganizationProperty organization_property) 
            throws SystemException, InvalidParameter, NotAvailable, InternalError {
        rtcout.println(Logbuf.TRACE, "Organization_impl.add_organization_property()");
        try {
            if( m_orgProperty==null ) m_orgProperty = new OrganizationProperty();
            synchronized (m_orgProperty) {
                m_orgProperty = organization_property;
                return true;
            }
        } catch (Exception ex) {
            throw new InternalError("add_organization_property()");
        }
    }

    /**
     * {@.ja [CORBA interface] OrganizationProperty の値を設定する。}
     * {@.ja [CORBA interface] Gets the value of OrganizationProperty}
     * <p>
     * {@.ja OrganizationProperty の NVList に name と value のセットを
     * 更新もしくは追加するオペレーション。
     * name と value は引数 "name" と "value" により指定する。}
     *
     * @param name 
     *   {@.ja 追加・更新されるプロパティの名前。}
     *   {@.en name of Property}
     * @param value 
     *   {@.ja 追加・更新されるプロパティの値。}
     *   {@.en value of Property}
     * 
     * @return 
     *   {@.ja オペレーションが成功したかどうかを返す。}
     *   {@.en If the operation was successfully completed.}
     * 
     * @exception SDONotExists
     *   {@.ja ターゲットのSDOが存在しない。}
     *   {@.en The target SDO does not exist.}
     * @exception InvalidParameter 
     *   {@.ja 引数 "name" で指定されたプロパティは存在しない。}
     *   {@.en The specified property doesn't exist.}
     * @exception NotAvailable 
     *   {@.ja SDOは存在するが応答がない。}
     *   {@.en The target SDO is reachable but cannot respond.}
     * @exception InternalError 
     *   {@.ja 内部的エラーが発生した。}
     *   {@.en An internal error occurred.}
     */
    public boolean set_organization_property_value(final String name, Any value)
                throws InvalidParameter, NotAvailable, InternalError {
        rtcout.println(Logbuf.TRACE, "Organization_impl.set_organization_property_value(name="+name+")");
        if( name==null || name.equals("") ) {
            throw new InvalidParameter("set_organization_property_value(): Enpty name.");
        }
        NVListHolder nvlist = new NVListHolder();
        nvlist.value = m_orgProperty.properties;
        int index = CORBA_SeqUtil.find(nvlist, new nv_name(name));
        if( index<0 ) {
            NameValue nv = new NameValue();
            nv.name = name;
            nv.value = value;
            CORBA_SeqUtil.push_back(nvlist, nv);
            m_orgProperty.properties = nvlist.value;
        } else {
            m_orgProperty.properties[index].value = value;
        }
        return true;
    }
    
    /**
     * {@.ja [CORBA interface] OrganizationProperty を削除する。}
     * {@.en [CORBA interface] Deletes OrganizationProperty}
     * <p>
     * {@.ja OrganizationProperty の NVList から特定のプロパティを削除する。
     * 削除されるプロパティの名前は引数 "name" により指定。}
     * {@.en This operation removes a property of Organization from NVList of 
     * the OrganizationProperty. The property to be removed is specified by
     * argument "name."}
     *
     * @param name 
     *   {@.ja 削除するプロパティの名前。}
     *   {@.en The name of the property to be removed.}
     * 
     * @return 
     *   {@.ja オペレーションが成功したかどうかを返す。}
     *   {@.en If the operation was successfully completed.}
     * 
     * @exception SDONotExists 
     *   {@.ja ターゲットのSDOが存在しない。}
     *   {@.en The target SDO does not exist.}
     * @exception InvalidParameter 
     *   {@.ja 引数 "name" で指定されたプロパティは存在しない。}
     *   {@.en The property that is specified by argument "name" does 
     *   not exist.}
     * @exception NotAvailable 
     *   {@.ja SDOは存在するが応答がない。}
     *   {@.en The target SDO is reachable but cannot respond.}
     * @exception InternalError 
     *   {@.ja 内部的エラーが発生した。}
     *   {@.en The target SDO cannot execute the operation
     *                          completely due to some internal error.}
     */
    public boolean remove_organization_property(final String name)
                throws InvalidParameter, NotAvailable, InternalError {
        rtcout.println(Logbuf.TRACE, "Organization_impl.remove_organization_property(name="+name+")");
        if(name==null || name.equals("")) throw new InvalidParameter("remove_organization_property(): Enpty name.");
        
        NVListHolder nvlist = new NVListHolder();
        nvlist.value = m_orgProperty.properties;
        int index = CORBA_SeqUtil.find(nvlist, new nv_name(name));
        if( index<0 ) throw new InvalidParameter("remove_organization_property(): Not found.");
        
        try{
            CORBA_SeqUtil.erase(nvlist, index);
            m_orgProperty.properties = nvlist.value;
            return true;
        } catch (Exception ex) {
            throw new InternalError("remove_organization_property()");
        }
//        return false;
    }

    /**
     * {@.ja [CORBA interface] Organization のオーナーを取得する。}
     * {@.en [CORBA interface] Get the owner of Organization}
     * <p>
     * {@.ja この Organization のオーナーへの参照を返す。}
     * {@.en This operation returns the SDOSystemElement that is owner of
     * the Organization.}
     *
     * @return 
     *   {@.ja オーナーオブジェクトへの参照。}
     *   {@.en Reference of owner object.}
     * 
     * @exception SDONotExists 
     *   {@.ja ターゲットのSDOが存在しない。}
     *   {@.en The target SDO does not exist.}
     * @exception NotAvailable 
     *   {@.ja SDOは存在するが応答がない。}
     *   {@.en The target SDO is reachable but cannot respond.}
     * @exception InternalError 
     *   {@.ja 内部的エラーが発生した。}
     *   {@.en The target SDO cannot execute the operation
     *                          completely due to some internal error.}
     */
    public SDOSystemElement get_owner() 
            throws NotAvailable, InternalError {
        rtcout.println(Logbuf.TRACE, "Organization_impl.get_owner()");
        return m_varOwner;
    }

    /**
     * {@.ja [CORBA interface] Organization にオーナーを設定する。}
     * {@.en [CORBA interface] Set the owner to the Organization}
     *
     * <p>
     * {@.ja Organization に対して SDOSystemElement をオーナーとしてセット。
     * 引数 "sdo" にセットする SDOSystemElement を指定。}
     * {@.en This operation sets an SDOSystemElement to the owner of the
     * Organization. The SDOSystemElement to be set is specified by argument
     * "sdo."}
     *
     * @param sdo 
     *   {@.ja オーナーオブジェクトの参照。}
     *   {@.en Reference of owner object.}
     * 
     * @return 
     *   {@.ja オペレーションが成功したかどうかを返す。}
     *   {@.en If the operation was successfully completed.}
     * 
     * @exception SDONotExists 
     *   {@.ja ターゲットのSDOが存在しない。}
     *   {@.en The target SDO does not exist.}
     * @exception InvalidParameter 
     *   {@.ja 引数 "sdo" が nullである、もしくは、 "sdo" が存在しない。}
     *   {@.en The argument "sdo" is null, or the object
     *                             that is specified by "sdo" in argument "sdo"
     *                             does not exist.}
     * @exception NotAvailable 
     *   {@.ja SDOは存在するが応答がない。}
     *   {@.en The target SDO is reachable but cannot respond.}
     * @exception InternalError 
     *   {@.ja 内部的エラーが発生した。}
     *   {@.en The target SDO cannot execute the operation
     *                          completely due to some internal error.}
     */
    public boolean set_owner(SDOSystemElement sdo) 
                throws InvalidParameter, NotAvailable, InternalError {
        rtcout.println(Logbuf.TRACE, "Organization_impl.set_owner()");
        if(sdo==null) throw new InvalidParameter("set_owner()");
        try {
            m_varOwner = sdo;
            return true;
        } catch (Exception ex) {
            throw new InternalError("set_owner()");
        }
//        return false;
    }

    /**
     * {@.ja [CORBA interface] Organization のメンバーを取得する。}
     * {@.en [CORBA interface] Get the member list of the Organization}
     *
     * <p>
     * {@.ja Organization のメンバーの SDO のリストを返します。
     * メンバーが存在しなければ空のリストを返す。}
     * {@.en This operation returns a list of SDOs that are members of an
     * Organization. An empty list is returned if the Organization does not
     * have any members.}
     *
     * @return 
     *   {@.ja Organization に含まれるメンバー SDO のリスト。}
     *   {@.en Member SDOs that are contained in the Organization object.}
     * 
     * @exception SDONotExists 
     *   {@.ja ターゲットのSDOが存在しない。}
     *   {@.en The target SDO does not exist.}
     * @exception NotAvailable 
     *   {@.ja SDOは存在するが応答がない。}
     *   {@.en The target SDO is reachable but cannot respond.}
     * @exception InternalError 
     *   {@.ja 内部的エラーが発生した。}
     *   {@.en The target SDO cannot execute the operation
     *                          completely due to some internal error.}
     */
    public SDO[] get_members() throws NotAvailable, InternalError {
        rtcout.println(Logbuf.TRACE, "Organization_impl.get_members()");
        try{
            SDOListHolder sdos = new SDOListHolder(m_memberList);
            return sdos.value;
        } catch(Exception ex) {
            throw new InternalError("get_members()");
        }
    }

    /**
     * {@.ja [CORBA interface] SDO の メンバー を設定する。}
     * {@.en [CORBA interface] Set SDO}
     *
     * <p>
     * {@.ja SDO のリストを Organization のメンバーとしてセットする。
     * Organization がすでにメンバーの SDO を管理している場合は、
     * 与えられた SDO のリストに置き換える。}
     * {@.en This operation assigns a list of SDOs to an Organization as its 
     * members.
     * If the Organization has already maintained a member SDO(s) when it is
     * called, the operation replaces the member(s) with specified list of
     * SDOs.}
     *
     * @param sdos 
     *   {@.ja メンバーの SDO。}
     *   {@.en Member SDOs to be assigned.}
     * 
     * @return 
     *   {@.ja オペレーションが成功したかどうかを返す。}
     *   {@.en If the operation was successfully completed.}
     * 
     * @exception SDONotExists 
     *   {@.ja ターゲットのSDOが存在しない。}
     *   {@.en The target SDO does not exist.}
     * @exception InvalidParameter 
     *   {@.ja 引数 "SDOList" が nullである、もしくは
     *            引数に指定された "SDOList" が存在しない。}
     *   {@.en The argument "SDOList" is null, or the object
     *                             that is specified by the argument "sdos"
     *                             does not exist.}
     * @exception NotAvailable 
     *   {@.ja SDOは存在するが応答がない。}
     *   {@.en The target SDO is reachable but cannot respond.}
     * @exception InternalError 
     *   {@.ja 内部的エラーが発生した。}
     *   {@.en The target SDO cannot execute the operation
     *                          completely due to some internal error.}
     */
    public boolean set_members(SDO[] sdos)
                    throws InvalidParameter, NotAvailable, InternalError {
        rtcout.println(Logbuf.TRACE, "Organization_impl.set_members()");
        if( sdos==null || sdos.length==0 ) 
            throw new InvalidParameter("set_members(): SDOList is empty.");
        try{
            m_memberList = sdos;
            return true;
        } catch(Exception ex) {
            throw new InternalError("set_members()");
        }
    }

    /**
     * {@.ja [CORBA interface] SDO メンバーを追加する。}
     * {@.en [CORBA interface] Add the member SDOs}
     *
     * <p>
     * {@.ja Organization にメンバーとして SDO を追加する。
     * 引数 "sdo" に追加するメンバー SDO を指定する。}
     * {@.en This operation adds a member that is an SDO to the organization.
     * The member to be added is specified by argument "sdo."}
     *
     * @param sdo_list 
     *   {@.ja Organization に追加される SDO のリスト。}
     *   {@.en The member to be added to the organization.}
     * 
     * @return 
     *   {@.ja オペレーションが成功したかどうかを返す。}
     *   {@.en If the operation was successfully completed.}
     * 
     * @exception SDONotExists 
     *   {@.ja ターゲットのSDOが存在しない。}
     *   {@.en The target SDO does not exist.}
     * @exception InvalidParameter 
     *   {@.ja 引数 "sdo" が nullである。}
     *   {@.en The argument "sdo" is null.}
     * @exception NotAvailable 
     *   {@.ja SDOは存在するが応答がない。}
     *   {@.en The target SDO is reachable but cannot respond.}
     * @exception InternalError 
     *   {@.ja 内部的エラーが発生した。}
     *   {@.en The target SDO cannot execute the operation
     *                          completely due to some internal error.}
     */
    public boolean add_members(SDO[] sdo_list)
            throws InvalidParameter, NotAvailable, InternalError {
        rtcout.println(Logbuf.TRACE, "Organization_impl.add_members()");
        try{
            SDOListHolder sdoList1 = new SDOListHolder();
            sdoList1.value = m_memberList;
            SDOListHolder sdoList2 = new SDOListHolder();
            sdoList2.value = sdo_list;
            CORBA_SeqUtil.push_back_list(sdoList1, sdoList2);
            m_memberList = sdoList1.value;
            return true;
        } catch(Exception ex) {
            throw new InternalError("add_members()");
        }
    }

    /**
     * {@.ja [CORBA interface] SDO メンバーを削除する。}
     * {@.en [CORBA interface] Remove member SDO from Organization}
     *
     * <p>
     * {@.ja Organization から引数で指定された "id" の SDO を削除する。}
     * {@.en This operation removes a member from the organization. 
     * The member to be removed is specified by argument "id."}
     *
     * @param id 
     *   {@.ja 削除する SDO の id。}
     *   {@.en Id of the SDO to be removed from the organization.}
     * 
     * @return 
     *   {@.ja オペレーションが成功したかどうかを返す。}
     *   {@.en If the operation was successfully completed.}
     * 
     * @exception SDONotExists 
     *   {@.ja ターゲットのSDOが存在しない。}
     *   {@.en The target SDO does not exist.}
     * @exception InvalidParameter 
     *   {@.ja 引数 "id" が null もしくは存在しない。}
     *   {@.en The argument "id" is null or does not exist.}
     * @exception NotAvailable 
     *   {@.ja SDOは存在するが応答がない。}
     *   {@.en The target SDO is reachable but cannot respond.}
     * @exception InternalError 
     *   {@.ja 内部的エラーが発生した。}
     *   {@.en The target SDO cannot execute the operation
     *                          completely due to some internal error.}
     */
    public boolean remove_member(final String id)
                throws InvalidParameter, NotAvailable, InternalError {
        rtcout.println(Logbuf.TRACE, "Organization_impl.remove_member("+id+")");
        if(id==null || id.equals("") )
            throw new InvalidParameter("remove_member(): Enpty name.");
       
        for(int index=0; index<m_memberList.length; index++ ){
            if( id.equals(m_memberList[index].get_sdo_id()) ) {
                SDOListHolder sdoList = new SDOListHolder();
                sdoList.value = m_memberList;
                CORBA_SeqUtil.erase(sdoList, index);
                m_memberList = sdoList.value;
                return true;
            }
        }
        throw new InvalidParameter("remove_member(): Not found.");
    }
    
    /**
     * {@.ja [CORBA interface] Organization の DependencyType を取得する。}
     * {@.en [CORBA interface] Get the DependencyType of the Organization}
     *
     * <p>
     * {@.ja Organization の関係を表す "DependencyType" を返す。}
     * {@.en This operation gets the relationship "DependencyType" of the
     * Organization.}
     *
     * @return 
     *   {@.ja Organizaton の依存関係 DependencyType を返す。
     *         DependencyType は OMG SDO 仕様の Section 2.2.2 2-3 ページの
     *         "Data Structures Used by Resource Data Model" を参照。}
     *   {@.en The relationship of the Organization as DependencyType.
     *         DependencyType is defined in Section 2.2.2, "Data Structures
     *         Used by Resource Data Model," on page 2-3
     *         of OMG SDO Specification.}
     *         
     * @exception SDONotExists 
     *   {@.ja ターゲットのSDOが存在しない。}
     *   {@.en The target SDO does not exist.}
     * @exception NotAvailable 
     *   {@.ja SDOは存在するが応答がない。}
     *   {@.en The target SDO is reachable but cannot respond.}
     * @exception InternalError 
     *   {@.ja 内部的エラーが発生した。}
     *   {@.en The target SDO cannot execute the operation
     *                          completely due to some internal error.}
     *
     */
    public DependencyType get_dependency() throws NotAvailable, InternalError {
        rtcout.println(Logbuf.TRACE, "Organization_impl.get_dependency()");
        return m_dependency;
    }

    /**
     * {@.ja [CORBA interface] Organization の DependencyType を設定する。}
     * {@.en [CORBA interface] Set the DependencyType of the Organization}
     * <p>
     * {@.ja Organization の依存関係 "DependencyType" をセットする。
     * 引数 "dependencty" により依存関係を与える。}
     * {@.en This operation sets the relationship "DependencyType" of the
     * Organization. The value to be set is specified by argument "dependency."}
     *
     * @param dependency 
     *   {@.ja Organization の依存関係を表す DependencyType。
     *        DependencyType は OMG SDO 仕様の Section 2.2.2、2-3 ページの
     *        "Data Structures Used by Resource Data Model" を参照。}
     *   {@.en The relationship of the Organization as
     *                   DependencyType. DependencyType is defined in Section
     *                   2.2.2, "Data Structures Used by Resource Data Model,"
     *                   on page 2-3.}
     *        
     * @return 
     *   {@.ja オペレーションが成功したかどうかを返す。}
     *   {@.en If the operation was successfully completed.}
     * 
     * @exception SDONotExists 
     *   {@.ja ターゲットのSDOが存在しない。}
     *   {@.en The target SDO does not exist.}
     * @exception InvalidParameter 
     *   {@.ja 引数 "sProfile" が nullである。}
     *   {@.en The argument "dependency" is null.}
     * @exception NotAvailable i
     *   {@.ja SDOは存在するが応答がない。}
     *   {@.en The target SDO is reachable but cannot respond.}
     * @exception InternalError 
     *   {@.ja 内部的エラーが発生した。}
     *   {@.en The target SDO cannot execute the operation
     *                          completely due to some internal error.}
     */
    public boolean set_dependency(DependencyType dependency)
            throws NotAvailable, InternalError {
        rtcout.println(Logbuf.TRACE, "Organization_impl.set_dependency()");
        try {
            m_dependency = dependency;
            return true;
        } catch (Exception ex) {
            throw new InternalError("set_dependency(): Unknown.");
        }
    }
    
    /**
     * {@.ja オブジェクトを取得する。}
     * {@.en Gets the object. }
     *
     * @return 
     *   {@.ja Organization}
     *   {@.en Organization}
     *
     */
    public Organization getObjRef() {
        return m_objref;
    };
    /**
     * <p>  </p>
     */
    protected Organization m_objref;

    /**
     * {@.ja Organization の識別子}
     */
    protected String m_pId = new String();
    /**
     * {@.ja Organization に関連付けられた SDO メンバのリスト}
     */
    protected SDO[] m_memberList = new SDO[0];
    /**
     * {@.ja Organization の owner}
     */   
    protected SDOSystemElement m_varOwner;

    /**
     * {@.ja 依存関係のタイプ}
     * {@.en Dependency type}
     * <p>
     * {@.ja Owner と member の依存関係を指定する属性。
     * Organization は以下のトポロジパターンを表現することができます。
     *
     * <ol>
     * <li>owenr が member を管理する階層的構造。
     * この場合 DependencyType は OWNという値を持つ。</li>
     * <li>members が owner を管理する逆向きの階層的構造。
     * この場合はDependencyType は OWNER という値を持つ。</li>
     * <li>owner と member に依存関係がないフラットな構造。
     * この場合はDependencyType は NO_DEPENDENCY という値を持つ。</li>
     * </ol>
     * 
     * <p>SDO および SDOSystemElement のサブクラスは Organization の 
     * owner として
     * 振舞うことが出来ます。SDO が owner の場合にはOrganization は上記の
     * いずれかのトポロジーパターンをとります。</p>
     *
     * <ol>
     * <li>Organization が 1. のトポロジーパターンを持つ場合、
     * 唯一つの owner SDOは member SDO を制御します。
     * たとえば、エアコン(owner)は、温度センサ(member)、湿度センサ(member)、
     * 風量制御器(member)を制御します。
     * <li>Organization が 2. のトポロジを持つ場合は、
     * 複数の SDO member が唯一のSDO owner を共有します。
     * たとえば、アンプ(owner)はいくつかのAVコンポーネント(member)から
     * 共有されます。</li>
     * <li>SDO ではない SDOSystemElement のサブクラスが owner の場合、
     * 以下のようなトポロジー例が考えられます。
     * <ol>
     * <li>User(owner)-SDO(member): ユーザ(owner) は一つ以上の SDO(member)を
     * 管理します。
     * これは上記トポロジパタン1.にあたります。</li>
     * <li>Location(owner)-SDO(members): 一つ以上の SDO(member) が特定の場所
     *   = location(owner) で動作している場合、Organization のトポロジパターン
     *   は 3. の場合になります。たとえば、複数の PDA がある部屋にあり、
     *   互いに同等
     *   な関係であり相互に通信可能な場合は
     *   これにあたります。</li></ol></li></ol>}
     *
     * {@.en This attribute specifies the dependency relation between 
     * the owner and
     * members of the organization.
     * Organization is used to form the following three patterns of topology.
     *
     * -# Hierarchical organization, which indicates owner supervises members.
     *    In this case, DependencyType should hold OWN value (see description
     *    of DependencyType on previous pages).
     * -# Reversely hierarchical organization, which indicates members
     *    supervise owner. In this case, DependencyType should hold OWNED value
     *    (see description of DependencyType on previous pages).
     * -# Flat organization, which indicates no dependency exists. In this
     *    case, DependencyType should hold NO_DEPENDENCY value (see description
     *    of DependencyType on previous pages).
     *
     * Both an SDO and another subclass of SDOSystemElement can act as owner
     * of an Organization. When an SDO is an owner, Organization can represent
     * any of the above three topology patterns.
     *
     * - When an Organization represents topology pattern (1), an SDO (owner)
     *   controls one or more SDOs (members). For example, air conditioner
     *   (owner) controls a temperature sensor (member), humidity sensor
     *   (member), and wind flow controller (member).
     * - When an Organization represents topology pattern (2), multiple
     *   SDOs(members) share an SDO (owner). For example, an amplifier (owner)
     *   is shared by several AV components (members) in an AV stereo.
     * - When a subclass of SDOSystemElement, which is not an SDO is an owner
     *   examples of the topology are as follows.
     * -- User (owner)-SDO (members): When a user (owner) supervises one or
     *    more SDOs (members), the organization represents topology pattern 1.
     * -- Location (owner)-SDO (members): When one or more SDOs (members) are
     *    operating in a specific location (owner), the organization represents
     *    topology pattern 3. For example, multiple PDAs in the same place
     *    (e.g., a room) have equal relationships among them to communicate
     *    with each other.}
     */
    protected DependencyType m_dependency;

     /**
     * {@.ja Organization プロパティ}
     * <p>
     * {@.ja OrganizationProperty は Organization のプロパティ情報を保持します。
     * 一つの Organization は0個もしくは1個の OrganizationProperty 
     * をもちます。}
     *
     * member:property NVList
     */
    OrganizationProperty m_orgProperty = new OrganizationProperty();
    /**
     * {@.ja Organization プロパティ検索用ヘルパークラス}
     */
    class nv_name implements equalFunctor{
        public nv_name(final String name) {
            m_name = name;
        }
        public boolean equalof(final java.lang.Object nv) {
            return m_name.equals(((NameValue)nv).name);
        }
        private String m_name;
    }

    protected Logbuf rtcout;
}
