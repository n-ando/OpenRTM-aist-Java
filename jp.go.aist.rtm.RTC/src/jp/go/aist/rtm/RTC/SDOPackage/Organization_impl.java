package jp.go.aist.rtm.RTC.SDOPackage;

import java.util.UUID;

import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.ORBUtil;
import jp.go.aist.rtm.RTC.util.equalFunctor;

import org.omg.CORBA.Any;

import _SDOPackage.DependencyType;
import _SDOPackage.InternalError;
import _SDOPackage.InvalidParameter;
import _SDOPackage.NVListHolder;
import _SDOPackage.NameValue;
import _SDOPackage.NotAvailable;
import _SDOPackage.OrganizationProperty;
import _SDOPackage.SDO;
import _SDOPackage.SDOListHolder;
import _SDOPackage.SDOSystemElement;

/**
* <p>SDO Organizationの実装クラスです。</p>
*/
public class Organization_impl {

    /**
     * <p>デフォルトコンストラクタです。</p>
     */
    public Organization_impl() {
        m_pId = UUID.randomUUID().toString();
    }
    /**
     * <p>[CORBA interface] Organization ID を取得します。</p>
     *
     * @return Resource Data Model で定義された Organization ID
     * 
     * @exception SDONotExists ターゲットのSDOが存在しない
     * @exception NotAvailable SDOは存在するが応答がない
     * @exception InternalError 内部的エラーが発生した
     */
    public String get_organization_id() throws InvalidParameter, NotAvailable, InternalError {
        return m_pId;
    }
    /**
     * <p>[CORBA interface] OrganizationProperty を取得します。<br />
     *
     * Organization が所有する OrganizationProperty を返すオペレーション。
     * Organization がプロパティを持たなければ空のリストを返します。</p>
     *
     * @return Organization のプロパティのリスト
     * 
     * @exception SDONotExists ターゲットのSDOが存在しない
     * @exception NotAvailable SDOは存在するが応答がない
     * @exception InternalError 内部的エラーが発生した
     */
    public synchronized OrganizationProperty get_organization_property() 
                                            throws NotAvailable, InternalError {
        try {
            OrganizationProperty prop = new OrganizationProperty(m_orgProperty.properties);
            return prop;
        } catch (Exception ex) {
            return null;
//            throw new InternalError("get_organization_property()");
        }
    }
    /**
     * <p>[CORBA interface] OrganizationProperty の特定の値を取得します。<br />
     *
     * OrganizationProperty の指定された値を返すオペレーション。
     * 引数 "name" で指定されたプロパティの値を返します。</p>
     *
     * @param name 値を返すプロパティの名前
     * 
     * @return 引数 "name" で指定されたプロパティの値
     * 
     * @exception SDONotExists ターゲットのSDOが存在しない
     * @exception InvalidParameter 引数 "namne" で指定されたプロパティが
     *            存在しない
     * @exception NotAvailable SDOは存在するが応答がない
     * @exception InternalError 内部的エラーが発生した
     */
    public Any get_organization_property_value(final String name) throws InvalidParameter, NotAvailable, InternalError {
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
     * <p>[CORBA interface] OrganizationProperty を設定します。<br />
     *
     * OrganizationProperty を Organization に追加するオペレーション。
     * OrganizationProperty は Organization のプロパティ記述です。</p>
     *
     * @param organization_property セットする OrganizationProperty
     * 
     * @return オペレーションが成功したかどうかを返す。
     * 
     * @exception SDONotExists ターゲットのSDOが存在しない。
     * @exception InvalidParameter "org_property" が null。
     * @exception NotAvailable SDOは存在するが応答がない。
     * @exception InternalError 内部的エラーが発生した。
     */
    // TODO ※ SDO Specification の PIM 記述とオペレーション名が異なる。
    //       ※ addOrganizationProperty に対応か？
    public boolean set_organization_property(final OrganizationProperty organization_property) 
            throws InvalidParameter, NotAvailable, InternalError {
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
     * <p>[CORBA interface] OrganizationProperty の値を設定します。<br />
     *
     * OrganizationProperty の NVList に name と value のセットを更新もしくは
     * 追加するオペレーション。name と value は引数 "name" と "value" により
     * 指定します。</p>
     *
     * @param name 追加・更新されるプロパティの名前。
     * @param value 追加・更新されるプロパティの値。
     * 
     * @return オペレーションが成功したかどうかを返す。
     * 
     * @exception SDONotExists ターゲットのSDOが存在しない。
     * @exception InvalidParameter 引数 "name" で指定されたプロパティは
     *            存在しない。
     * @exception NotAvailable SDOは存在するが応答がない。
     * @exception InternalError 内部的エラーが発生した。
     */
    public boolean set_organization_property_value(final String name, Any value)
                throws InvalidParameter, NotAvailable, InternalError {
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
     * <p>[CORBA interface] OrganizationProperty を削除します。<br />
     *
     * OrganizationProperty の NVList から特定のプロパティを削除します。
     * 削除されるプロパティの名前は引数 "name" により指定されます。</p>
     *
     * @param name 削除するプロパティの名前。
     * 
     * @return オペレーションが成功したかどうかを返す。
     * 
     * @exception SDONotExists ターゲットのSDOが存在しない。
     * @exception InvalidParameter 引数 "name" で指定されたプロパティは
     *            存在しない。
     * @exception NotAvailable SDOは存在するが応答がない。
     * @exception InternalError 内部的エラーが発生した。
     */
    public boolean remove_organization_property(final String name)
                throws InvalidParameter, NotAvailable, InternalError {
        if(name==null || name.equals("")) throw new InvalidParameter("set_organization_property_value(): Enpty name.");
        
        NVListHolder nvlist = new NVListHolder();
        nvlist.value = m_orgProperty.properties;
        int index = CORBA_SeqUtil.find(nvlist, new nv_name(name));
        if( index<0 ) throw new InvalidParameter("set_organization_property_value(): Not found.");
        
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
     * <p>[CORBA interface] Organization のオーナーを取得します。<br />
     *
     * この Organization のオーナーへの参照を返します。</p>
     *
     * @return オーナーオブジェクトへの参照。
     * 
     * @exception SDONotExists ターゲットのSDOが存在しない。
     * @exception NotAvailable SDOは存在するが応答がない。
     * @exception InternalError 内部的エラーが発生した。
     */
    public SDOSystemElement get_owner() 
            throws NotAvailable, InternalError {
        return m_varOwner;
    }

    /**
     * <p>[CORBA interface] Organization にオーナーを設定します。<br />
     *
     * Organization に対して SDOSystemElement をオーナーとしてセットします。
     * 引数 "sdo" にセットする SDOSystemElement を指定します。</p>
     *
     * @param sdo オーナーオブジェクトの参照。
     * 
     * @return オペレーションが成功したかどうかを返す。
     * 
     * @exception SDONotExists ターゲットのSDOが存在しない。
     * @exception InvalidParameter 引数 "sdo" が nullである、もしくは、
     *                             "sdo" が存在しない。
     * @exception NotAvailable SDOは存在するが応答がない。
     * @exception InternalError 内部的エラーが発生した。
     */
    public boolean set_owner(SDOSystemElement sdo) 
                throws InvalidParameter, NotAvailable, InternalError {
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
     * <p>[CORBA interface] Organization のメンバーを取得します。<br />
     *
     * Organization のメンバーの SDO のリストを返します。
     * メンバーが存在しなければ空のリストを返します。</p>
     *
     * @return Organization に含まれるメンバー SDO のリスト。
     * 
     * @exception SDONotExists ターゲットのSDOが存在しない。
     * @exception NotAvailable SDOは存在するが応答がない。
     * @exception InternalError 内部的エラーが発生した。
     */
    public SDO[] get_members() throws NotAvailable, InternalError {
        try{
            SDOListHolder sdos = new SDOListHolder(m_memberList);
            return sdos.value;
        } catch(Exception ex) {
            throw new InternalError("get_members()");
        }
    }

    /**
     * <p>[CORBA interface] SDO の メンバー を設定します。<br />
     *
     * SDO のリストを Organization のメンバーとしてセットします。
     * Organization がすでにメンバーの SDO を管理している場合は、
     * 与えられた SDO のリストに置き換えます。</p>
     *
     * @param sdos メンバーの SDO。
     * 
     * @return オペレーションが成功したかどうかを返す。
     * 
     * @exception SDONotExists ターゲットのSDOが存在しない。
     * @exception InvalidParameter 引数 "SDOList" が nullである、もしくは
     *            引数に指定された "SDOList" が存在しない。
     * @exception NotAvailable SDOは存在するが応答がない。
     * @exception InternalError 内部的エラーが発生した。
     */
    public boolean set_members(SDO[] sdos)
                    throws InvalidParameter, NotAvailable, InternalError {
        if( sdos==null || sdos.length==0 ) 
            throw new InvalidParameter("set_members(): SDOList is empty.");
        try{
            m_memberList = sdos;
            return true;
        } catch(Exception ex) {
            throw new InternalError("set_members()");
        }
//        return false;
    }

    /**
     * <p>[CORBA interface] SDO メンバーを追加します。<br />
     *
     * Organization にメンバーとして SDO を追加します。
     * 引数 "sdo" に追加するメンバー SDO を指定します。</p>
     *
     * @param sdo_list Organization に追加される SDO のリスト。
     * 
     * @return オペレーションが成功したかどうかを返す。
     * 
     * @exception SDONotExists ターゲットのSDOが存在しない。
     * @exception InvalidParameter 引数 "sdo" が nullである。
     * @exception NotAvailable SDOは存在するが応答がない。
     * @exception InternalError 内部的エラーが発生した。
     */
    public boolean add_members(SDO[] sdo_list)
            throws InvalidParameter, NotAvailable, InternalError {
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
     * <p>[CORBA interface] SDO メンバーを削除します。<br />
     *
     * Organization から引数で指定された "id" の SDO を削除します。</p>
     *
     * @param id 削除する SDO の id。
     * 
     * @return オペレーションが成功したかどうかを返す。
     * 
     * @exception SDONotExists ターゲットのSDOが存在しない。
     * @exception InvalidParameter 引数 "id" が null もしくは存在しない。
     * @exception NotAvailable SDOは存在するが応答がない。
     * @exception InternalError 内部的エラーが発生した。
     */
    public boolean remove_member(final String id)
                throws InvalidParameter, NotAvailable, InternalError {
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
     * <p>[CORBA interface] Organization の DependencyType を取得します。<br />
     *
     * Organization の関係を表す "DependencyType" を返します。</p>
     *
     * @return Organizaton の依存関係 DependencyType を返します。
     *         DependencyType は OMG SDO 仕様の Section 2.2.2 2-3 ページの
     *         "Data Structures Used by Resource Data Model" を参照。
     *         
     * @exception SDONotExists ターゲットのSDOが存在しない。
     * @exception NotAvailable SDOは存在するが応答がない。
     * @exception InternalError 内部的エラーが発生した。
     *
     */
    public DependencyType get_dependency() throws NotAvailable, InternalError {
        return m_dependency;
    }

    /**
     * <p>[CORBA interface] Organization の DependencyType を設定します。<br />
     *
     * Organization の依存関係 "DependencyType" をセットします。
     * 引数 "dependencty" により依存関係を与えます。</p>
     *
     * @param dependency Organization の依存関係を表す DependencyType。
     *        DependencyType は OMG SDO 仕様の Section 2.2.2、2-3 ページの
     *        "Data Structures Used by Resource Data Model" を参照。
     *        
     * @return オペレーションが成功したかどうかを返します。
     * 
     * @exception SDONotExists ターゲットのSDOが存在しない。
     * @exception InvalidParameter 引数 "sProfile" が nullである。
     * @exception NotAvailable SDOは存在するが応答がない。
     * @exception InternalError 内部的エラーが発生した。
     */
    public boolean set_dependency(DependencyType dependency)
            throws NotAvailable, InternalError {
        try {
            m_dependency = dependency;
            return true;
        } catch (Exception ex) {
            throw new InternalError("set_dependency(): Unknown.");
        }
    }
    
    /**
     * <p>Organization の識別子</p>
     */
    protected String m_pId = new String();
    /**
     * <p>Organization に関連付けられた SDO メンバのリスト</p>
     */
    protected SDO[] m_memberList;
    /**
     * <p>Organization の owner</p>
     */   
    protected SDOSystemElement m_varOwner;

    /**
     * <p>依存関係のタイプ<br />
     *
     * Owner と member の依存関係を指定する属性。
     * Organization は以下のトポロジパターンを表現することができます。
     *
     * <ol>
     * <li>owenr が member を管理する階層的構造。この場合 DependencyType は OWNという値を持つ。</li>
     * <li>members が owner を管理する逆向きの階層的構造。
     * この場合はDependencyType は OWNER という値を持つ。</li>
     * <li>owner と member に依存関係がないフラットな構造。
     * この場合はDependencyType は NO_DEPENDENCY という値を持つ。</li>
     * </ol>
     * 
     * <p>SDO および SDOSystemElement のサブクラスは Organization の owner として
     * 振舞うことが出来ます。SDO が owner の場合にはOrganization は上記の
     * いずれかのトポロジーパターンをとります。</p>
     *
     * <ol>
     * <li>Organization が 1. のトポロジーパターンを持つ場合、唯一つの owner SDOは member SDO を制御します。
     * たとえば、エアコン(owner)は、温度センサ(member)、湿度センサ(member)、風量制御器(member)を制御します。
     * <li>Organization が 2. のトポロジを持つ場合は、複数の SDO member が唯一のSDO owner を共有します。
     * たとえば、アンプ(owner)はいくつかのAVコンポーネント(member)から共有されます。</li>
     * <li>SDO ではない SDOSystemElement のサブクラスが owner の場合、以下のようなトポロジー例が考えられます。
     * <ol>
     * <li>User(owner)-SDO(member): ユーザ(owner) は一つ以上の SDO(member)を管理します。
     * これは上記トポロジパタン1.にあたります。</li>
     * <li>Location(owner)-SDO(members): 一つ以上の SDO(member) が特定の場所
     *   = location(owner) で動作している場合、Organization のトポロジパターン
     *   は 3. の場合になります。たとえば、複数の PDA がある部屋にあり、互いに同等
     *   な関係であり相互に通信可能な場合はこれにあたります。</li>
     * </ol>
     * </li>
     * </ol>
     */
    protected DependencyType m_dependency;

    /**
     * <p>Organization プロパティ<br />
     *
     * OrganizationProperty は Organization のプロパティ情報を保持します。
     * 一つの Organization は0個もしくは1個の OrganizationProperty をもちます。</p>
     *
     * @member property NVList
     */
    OrganizationProperty m_orgProperty;
    /**
     * <p>Organization プロパティ検索用ヘルパークラス</p>
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
}
