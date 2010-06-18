package jp.go.aist.rtm.RTC;

import jp.go.aist.rtm.RTC.util.StringHolder;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.SystemException;
import org.omg.CosNaming.BindingIteratorHolder;
import org.omg.CosNaming.BindingListHolder;
import org.omg.CosNaming.BindingType;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextExtPackage.InvalidAddress;
import org.omg.CosNaming.NamingContextPackage.AlreadyBound;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotEmpty;
import org.omg.CosNaming.NamingContextPackage.NotFound;


/**
 *
 * {@.ja CORBA Naming Service ヘルパークラス.}
 * {@.en CORBA Naming Service helper class.}
 * <p>
 * {@.ja このクラスは、NamingContext に対するラッパークラスです。
 * NamingContext が持つオペレーションとほぼ同じ機能の
 * オペレーションを提供するとともに、ネームコンポーネント NameComponent
 * の代わりに文字列による名前表現を受け付けるオペレーションも提供します。
 * <br>
 * オブジェクトは生成時、あるいは生成直後に CORBA ネームサーバに接続し
 * 以後、このネームサーバのルートコンテキストに対して種々のオペレーション
 * を処理します。
 * 深い階層のネーミングコンテキストの作成やオブジェクトのバインドにおいて、
 * 途中のコンテキストが存在しない場合でも、強制的にコンテキストをバインド
 * し目的のコンテキストやオブジェクトのバインドを行うこともできます。}
 *
 * {@.en This class is a wrapper class of CosNaming::NamingContext.
 * Almost the same operations which CosNaming::NamingContext has are
 * provided, and some operation allows string naming representation of
 * context and object instead of CosNaming::Name.
 * <br>
 * The object of the class would connect to a CORBA naming server at
 * the instantiation or immediately after instantiation.
 * After that the object invokes operations to the root context of it.
 * This class realizes forced binding to deep NamingContext, without binding
 * intermediate NamingContexts explicitly.}
 *
 */
public class CorbaNaming {

    /**
     * {@.ja コンストラクタ}
     * {@.en Consructor}
     *
     * @param orb 
     *   {@.ja ORB}
     *   {@.en ORB}
     *
     */
    public CorbaNaming(ORB orb){
        m_varORB = orb;
        m_nameServer = "";
        m_rootContext = null;
        m_blLength = 100;
    }

    /**
     * {@.ja コンストラクタ}
     * {@.en Consructor}
     *
     * @param orb 
     *   {@.ja ORB}
     *   {@.en ORB}
     * @param name_server 
     *   {@.ja ネームサーバの名称}
     *   {@.en Name of the name server}
     * @exception Exception
     *
     */
    public CorbaNaming(ORB orb, final String name_server) throws Exception {
        m_varORB = orb;
        m_nameServer = name_server;
        m_rootContext = null;
        m_blLength = 100;
        
        Object obj;
        int pos = m_nameServer.indexOf("iiop:");
        if(pos==0){
            m_nameServer = "corbaloc:" + m_nameServer + "/NameService";
        }
        else{
            m_nameServer = "corbaloc::" + m_nameServer + "/NameService";
        }
//        m_nameServer = "corbaloc:iiop:1.2@" + m_nameServer + "/NameService";

        obj = m_varORB.string_to_object(m_nameServer);
        m_rootContext =  NamingContextExtHelper.narrow(obj);
        if (m_rootContext==null) {
            throw new Exception("bad_alloc()");
        }
    }
    
    /**
     * {@.ja ネーミングサービスの初期化。}
     * {@.en Initialize the Naming Service}
     * 
     * <p>
     * {@.ja 指定されたネームサーバ上のネーミングサービスを初期化します。}
     * {@.en Initialize the Naming Service on the specified name server.}
     * </p>
     * 
     * @param name_server 
     *   {@.ja ネームサーバの名称}
     *   {@.en Name of the name server}
     * 
     * @exception Exception
     */
    public void init (final String name_server) throws Exception {
        m_nameServer = name_server;
        int pos = m_nameServer.indexOf("iiop:");
        if(pos==0){
            m_nameServer = "corbaloc:" + m_nameServer + "/NameService";
        }
        else{
            m_nameServer = "corbaloc::" + m_nameServer + "/NameService";
        }
//        m_nameServer = "corbaloc:iiop:1.2@" + m_nameServer + "/NameService";
        Object obj = m_varORB.string_to_object(m_nameServer);
        m_rootContext = NamingContextExtHelper.narrow(obj);
        if (m_rootContext==null) throw new Exception("bad_alloc()");
    }

    public boolean isAlive() {
        try {
            if (m_rootContext._non_existent()) { 
                return false; 
            }
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }

    /**
     * {@.ja Object を bind する。}
     * {@.en Bind object on specified name component position}
     * <p>
     * {@.ja CosNaming::bind() とほぼ同等の働きをするが、
     * 常に与えられたネームサーバの
     * ルートコンテキストに対してbind()が呼び出される点が異なる。
     * <br>
     * Name <name> と Object <obj> を当該 NamingContext 上にバインドする。
     * c_n が n 番目の NameComponent をあらわすとすると、
     * name が n 個の NameComponent から成るとき、以下のように扱われる。
     * <br>
     * cxt->bind(<c_1, c_2, ... c_n>, obj) は以下の操作と同等である。
     * cxt->resolve(<c_1, ... c_(n-1)>)->bind(<c_n>, obj)
     * <br>
     * すなわち、1番目からn-1番目のコンテキストを解決し、n-1番目のコンテキスト
     * 上に name <n> として　obj を bind する。
     * 名前解決に参加する <c_1, ... c_(n-1)> の NemingContext は、
     * bindContext() や rebindContext() で既にバインド済みでなければならない。
     * もし <c_1, ... c_(n-1)> の NamingContext が存在しない場合には、
     * NotFound 例外が発生する。
     * <br>
     * <c_1, ... c_(n-1)> が存在しない場合にも、
     * 再帰的にコンテキストをバインドしながら、
     * 最終的に obj を名前 name <c_n> にバインドする。
     * <br>
     * いずれの場合でも、n-1番目のコンテキスト上に name<n> のオブジェクト
     * (Object あるいは コンテキスト) がバインドされていれば
     * AlreadyBound 例外が発生する。}
     * {@.en Almost the same operation as CosNaming::bind(), 
     * but there is a difference 
     * that bind() is invoked for the root context of the given name server.
     *
     * Bind between Name <name> and Object <obj> on this NamingContext.
     * If c_n indicates the n-th of NameComponent,
     * when name consists of n pieces of NameComponent, it is handled as 
     * follows. 
     *
     * cxt->bind(<c_1, c_2, ... c_n>, obj) is the same as the following
     * operation.
     * cxt->resolve(<c_1, ... c_(n-1)>)->bind(<c_n>, obj)
     *
     * In other word, resolve from the first to the (n-1)th context and bind 
     * obj as name<n> on the (n-1)th context.
     * NemingContext of <c_1, ... c_(n-1)> for resolving name must be already 
     * bound in bindContext() or rebindContext().
     * If NamingContext of <c_1, ... c_(n-1)> does not exist, NotFound excption
     * will occur.
     *
     * Even if <c_1, ... c_(n-1)> does
     * not exist, finally obj will be bound to name name <c_n> by binding to 
     * the context recursively.
     *
     * Even in any case, if the object of name<n> (Object or context) is bound
     * on the (n-1)th context, AlreadyBound exception will occur.}
     * </p>
     *
     * @param name 
     *   {@.ja オブジェクトに付ける名前の NameComponent}
     *   {@.en NameComponent of name applied to object}
     * @param obj 
     *   {@.ja 関連付けられる Object}
     *   {@.en Object that is associated}
     *
     * @exception NotFound 
     *   {@.ja 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。}
     *   {@.en There is not <c_1, c_2, ..., c_(n-1)>.}
     * @exception CannotProceed 
     *   {@.ja 何らかの理由で処理を継続できない。}
     *   {@.en Processing cannot be continued for some reasons.}
     * @exception InvalidName 
     *   {@.ja 引数 name の名前が不正。}
     *   {@.en The argument 'name' is invalid.}
     * @exception AlreadyBound 
     *   {@.ja name <c_n> の Object がすでにバインドされている。}
     *   {@.en The object of name<c_n> is already bound.}
     *
     */
    public void bind(final NameComponent[] name, Object obj)
    throws SystemException, NotFound, CannotProceed, InvalidName, AlreadyBound {
        this.bind(name, obj, true);
    }

    /**
     * {@.ja Object を bind する。}
     * {@.en Bind object on specified name component position}
     * <p>
     * {@.ja CosNaming::bind() とほぼ同等の働きをするが、
     * 常に与えられたネームサーバの
     * ルートコンテキストに対してbind()が呼び出される点が異なる。
     *
     * Name <name> と Object <obj> を当該 NamingContext 上にバインドする。
     * c_n が n 番目の NameComponent をあらわすとすると、
     * name が n 個の NameComponent から成るとき、以下のように扱われる。
     *
     * cxt->bind(<c_1, c_2, ... c_n>, obj) は以下の操作と同等である。
     * cxt->resolve(<c_1, ... c_(n-1)>)->bind(<c_n>, obj)
     *
     * すなわち、1番目からn-1番目のコンテキストを解決し、n-1番目のコンテキスト
     * 上に name <n> として　obj を bind する。
     * 名前解決に参加する <c_1, ... c_(n-1)> の NemingContext は、
     * bindContext() や rebindContext() で既にバインド済みでなければならない。
     * もし <c_1, ... c_(n-1)> の NamingContext が存在しない場合には、
     * NotFound 例外が発生する。
     *
     * ただし、強制バインドフラグ force が true の時は、<c_1, ... c_(n-1)>
     * が存在しない場合にも、再帰的にコンテキストをバインドしながら、
     * 最終的に obj を名前 name <c_n> にバインドする。
     *
     * いずれの場合でも、n-1番目のコンテキスト上に name<n> のオブジェクト
     * (Object あるいは コンテキスト) がバインドされていれば
     * AlreadyBound 例外が発生する。}
     * {@.en Almost the same operation as CosNaming::bind(), 
     * but there is a difference 
     * that bind() is invoked for the root context of the given name server.
     *
     * Bind between Name <name> and Object <obj> on this NamingContext.
     * If c_n indicates the n-th of NameComponent,
     * when name consists of n pieces of NameComponent, it is handled as 
     * follows. 
     *
     * cxt->bind(<c_1, c_2, ... c_n>, obj) is the same as the following
     * operation.
     * cxt->resolve(<c_1, ... c_(n-1)>)->bind(<c_n>, obj)
     *
     * In other word, resolve from the first to the (n-1)th context and bind 
     * obj as name<n> on the (n-1)th context.
     * NemingContext of <c_1, ... c_(n-1)> for resolving name must be already 
     * bound in bindContext() or rebindContext().
     * If NamingContext of <c_1, ... c_(n-1)> does not exist, NotFound excption
     * will occur.
     *
     * However, when flag of forced bind is true, 
     * even if <c_1, ... c_(n-1)> does
     * not exist, finally obj will be bound to name name <c_n> by binding to 
     * the context recursively.
     *
     * Even in any case, if the object of name<n> (Object or context) is bound
     * on the (n-1)th context, AlreadyBound exception will occur.}
     * </p>
     *
     * @param name 
     *   {@.ja オブジェクトに付ける名前の NameComponent}
     *   {@.en NameComponent of name applied to object}
     * @param obj 
     *   {@.ja 関連付けられる Object}
     *   {@.en Object that is associated}
     * @param force 
     *   {@.ja trueの場合、途中のコンテキストを強制的にバインドする
     *              (デフォルト値:true)}
     *   {@.en force If true, the intermediate context is bound forcibly.
     *              (The default value:true)}
     *
     * @exception NotFound 
     *   {@.ja 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。}
     *   {@.en There is not <c_1, c_2, ..., c_(n-1)>.}
     * @exception CannotProceed 
     *   {@.ja 何らかの理由で処理を継続できない。}
     *   {@.en Processing cannot be continued for some reasons.}
     * @exception InvalidName 
     *   {@.ja 引数 name の名前が不正。}
     *   {@.en The argument 'name' is invalid.}
     * @exception AlreadyBound 
     *   {@.ja name <c_n> の Object がすでにバインドされている。}
     *   {@.en The object of name<c_n> is already bound.}
     *
     */
    public void bind(final NameComponent[] name, 
                            Object obj, final boolean force)
    throws SystemException, NotFound, CannotProceed, InvalidName, AlreadyBound {
        try{
            if( isNamingContext(obj) ) {
                m_rootContext.bind_context(name, 
                                        NamingContextExtHelper.narrow(obj));
            } else {
                m_rootContext.bind(name, obj);
            }
        } catch (NotFound ex) {
            if( force ){
                bindRecursive(m_rootContext, name, obj);
            } else {
                throw ex;
            }
        } catch (CannotProceed ex) {
            if( force ) {
                bindRecursive(ex.cxt, ex.rest_of_name, obj);
            } else {
                throw ex;
            }
        }
     }

    /**
     * {@.ja Object を bind する。}
     * {@.en Bind object on specified string name position}
     *
     * <p>
     * {@.ja Object を bind する際に与える名前が文字列表現であること以外は、
     * bind() と同じである。bind(toName(string_name), obj) と等価。
     * 途中のコンテキストも強制的にバインドする。}
     * {@.en This is the same as bind() except as the given name is string 
     * representation when Object is bound. 
     * bind(toName(string_name),obj) is the same.
     * The intermediate context is bound forcibly.}
     * </p>
     *
     * @param string_name 
     *   {@.ja オブジェクトに付ける名前の文字列表現}
     *   {@.en The string representation of name applied to object}
     * @param obj 
     *   {@.ja 関連付けられるオブジェクト}
     *   {@.en Object that is associated}
     *
     * @exception NotFound 
     *   {@.ja 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。}
     *   {@.en There is not <c_1, c_2, ..., c_(n-1)>.}
     * @exception CannotProceed 
     *   {@.ja 何らかの理由で処理を継続できない。}
     *   {@.en Processing cannot be continued for some reasons.}
     * @exception InvalidName 
     *   {@.ja 引数 name の名前が不正。}
     *   {@.en The argument 'name' is invalid.}
     * @exception AlreadyBound 
     *   {@.ja name <n> の Object がすでにバインドされている。}
     *   {@.en The object of name<c_n> is already bound.}
     *
     */
    public void bindByString(final String string_name, Object obj)
    throws SystemException, NotFound, CannotProceed, InvalidName, AlreadyBound {
        this.bindByString(string_name, obj, true);
    }

    /**
     * {@.ja Object を bind する。}
     * {@.en Bind object on specified string name position}
     *
     * <p>
     * {@.ja Object を bind する際に与える名前が文字列表現であること以外は、
     * bind() と同じである。bind(toName(string_name), obj) と等価。}
     * {@.en This is the same as bind() except as the given name is string 
     * representation when Object is bound. 
     * bind(toName(string_name),obj) is the same.}
     * </p>
     *
     * @param string_name 
     *   {@.ja オブジェクトに付ける名前の文字列表現}
     *   {@.en The string representation of name applied to object}
     * @param obj 
     *   {@.ja 関連付けられるオブジェクト}
     *   {@.en Object that is associated}
     * @param force 
     *   {@.ja trueの場合、途中のコンテキストを強制的にバインドする
     *              (デフォルト値:true)}
     *   {@.en If true, the intermediate context is bound forcibly.
     *              (The default value:true)}
     *
     * @exception NotFound 
     *   {@.ja 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。}
     *   {@.en There is not <c_1, c_2, ..., c_(n-1)>.}
     * @exception CannotProceed 
     *   {@.ja 何らかの理由で処理を継続できない。}
     *   {@.en Processing cannot be continued for some reasons.}
     * @exception InvalidName 
     *   {@.ja 引数 name の名前が不正。}
     *   {@.en The argument 'name' is invalid.}
     * @exception AlreadyBound 
     *   {@.ja name <n> の Object がすでにバインドされている。}
     *   {@.en The object of name<c_n> is already bound.}
     *
     */
    public void bindByString(final String string_name, 
                                            Object obj, final boolean force)
    throws SystemException, NotFound, CannotProceed, InvalidName, AlreadyBound {
        this.bind(toName(string_name), obj, force);
    }

    /**
     * {@.ja 途中のコンテキストを再帰的に bind しながら Object を 
     * bind する。}
     * {@.en Bind intermediate context recursively and bind object}
     *
     * <p>
     * {@.ja context で与えられた NamingContext に対して、name で指定された
     * ネームコンポーネント <c_1, ... c_(n-1)> を NamingContext として
     * 解決しながら、名前 <c_n> に対して obj を bind する。
     * もし、<c_1, ... c_(n-1)> に対応する NamingContext がない場合には
     * 新たな NamingContext をバインドする。
     *
     * 最終的に <c_1, c_2, ..., c_(n-1)> に対応する NamingContext が生成
     * または解決された上で、CosNaming::bind(<c_n>, object) が呼び出される。
     * このとき、すでにバインディングが存在すれば AlreadyBound例外が発生する。
     *
     * 途中のコンテキストを解決する過程で、解決しようとするコンテキストと
     * 同じ名前の NamingContext ではない Binding が存在する場合、
     * CannotProceed 例外が発生し処理を中止する。}
     * {@.en For NamingContext given in context, 
     * bind obj to name <c_n> with solving
     * name component <c_1, ... c_(n-1)> specified by name as NamingContext.
     * Bind new NamingContext when there is no NamingContext corresponding to
     * c_(n-1) >.
     *
     * Finally, NamingContext corresponding to <c_1, c_2, ..., c_(n-1)> 
     * will be generated, or CosNaming::bind(<c_n>, object) will be invoked
     * after solving. At this time, if the binding already exists, 
     * the AlreadyBound exception will occur.
     *
     * During process, when Binding that is not NamingContext of the same name
     * as the context for solving exists, CannotProceed exception will occur
     * and stop processing.}
     * </p>
     *
     * @param context 
     *   {@.ja bind を開始する　NamingContext}
     *   {@.en NamingContext that starts the bind}
     * @param name 
     *   {@.ja オブジェクトに付ける名前のネームコンポーネント}
     *   {@.en NameComponent of name applied to object}
     * @param obj 
     *   {@.ja 関連付けられるオブジェクト}
     *   {@.en Object that is associated}
     *
     * @exception CannotProceed 
     *   {@.ja <c_1, ..., c_(n-1)> に対応する NamingContext 
     *            のうちひとつが、すでに NamingContext 以外の object にバインド
     *            されており、処理を継続できない。}
     *   {@.en Since one of NamingContext corresponding to
     *                          <c_1, ..., c_(n-1)> is already bound to object
     *                          other than NamingContext and processing cannot 
     *                          be continued}
     * @exception InvalidName 
     *   {@.ja 名前 name が不正}
     *   {@.en name 'name' is invalid.}
     * @exception AlreadyBound 
     *   {@.ja name <c_n> にすでに何らかの object がバインド
     *            されている。}
     *   {@.en The object of name<c_n> is already bound.}
     *
     *
     */
    public void bindRecursive(NamingContext context,
                                        final NameComponent[] name, Object obj)
    throws SystemException, CannotProceed, InvalidName, AlreadyBound, NotFound {
        int len = name.length;
        NamingContext cxt = (NamingContext)context._duplicate();
    
        for(int intIdx=0; intIdx<len; ++intIdx) {
            if( intIdx==(len-1)) {
                // this operation may throw AlreadyBound, 
                if(isNamingContext(obj)) {
                    cxt.bind_context(subName(name, intIdx, intIdx), 
                                        NamingContextExtHelper.narrow(obj));
                } else {
                    cxt.rebind(subName(name, intIdx, intIdx), obj);
                }
                return;
            }
            // If the context is not a NamingContext, CannotProceed is thrown
            if( isNamingContext(cxt) ) {
                cxt = bindOrResolveContext(cxt, subName(name, intIdx, intIdx));
            } else {
                throw new CannotProceed(cxt, subName(name, intIdx));
            }
        }
        return;
    }
    
    /**
     * {@.ja Object を強制的に rebind する。}
     * {@.en Rebind object}
     *
     * <p>
     * {@.ja name で指定された Binding がすでに存在する場合を除いて 
     * bind() と同じである。
     * バインディングがすでに存在する場合には、新しいバインディングに
     * 置き換えられる。
     * 途中のコンテキストも強制的にバインドする。}
     * {@.en This is the same as bind() except as Binding specified by name 
     * already exists. If the binding already exists, new binding will be 
     * replaced.
     * The intermediate context is bound forcibly.}
     * </p>
     *
     * @param name 
     *   {@.ja オブジェクトに付ける名前の NameComponent}
     *   {@.en NameComponent of name applied to object}
     * @param obj 
     *   {@.ja 関連付けられるオブジェクト}
     *   {@.en Object that is associated}
     *
     * @exception NotFound 
     *   {@.ja 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。}
     *   {@.en There is not <c_1, c_2, ..., c_(n-1)>.}
     * @exception CannotProceed 
     *   {@.ja 何らかの理由で処理を継続できない。}
     *   {@.en Processing cannot be continued for some reasons.}
     * @exception InvalidName 
     *   {@.ja 名前 name が不正}
     *   {@.en Name 'name' is invalid.}
     *
     */
    public void rebind(final NameComponent[] name, Object obj) 
        throws SystemException, NotFound, CannotProceed, InvalidName {
        rebind(name, obj, true);
    }

    /**
     * {@.ja Object を rebind する。}
     * {@.en Rebind object}
     *
     * <p>
     * {@.ja name で指定された Binding がすでに存在する場合を除いて 
     * bind() と同じである。
     * バインディングがすでに存在する場合には、新しいバインディングに
     * 置き換えられる。}
     * {@.en This is the same as bind() except as Binding specified by name 
     * already exists. If the binding already exists, new binding will be 
     * replaced.}
     * </p>
     *
     * @param name 
     *   {@.ja オブジェクトに付ける名前の NameComponent}
     *   {@.en NameComponent of name applied to object}
     * @param obj 
     *   {@.ja 関連付けられるオブジェクト}
     *   {@.en Object that is associated}
     * @param force 
     *   {@.ja trueの場合、途中のコンテキストを強制的にバインドする
     *              (デフォルト値:true)}
     *   {@.en If true, the intermediate context is bound forcibly.
     *              (The default value:true)}
     *
     * @exception NotFound 
     *   {@.ja 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。}
     *   {@.en There is not <c_1, c_2, ..., c_(n-1)>.}
     * @exception CannotProceed 
     *   {@.ja 何らかの理由で処理を継続できない。}
     *   {@.en Processing cannot be continued for some reasons.}
     * @exception InvalidName 
     *   {@.ja 名前 name が不正}
     *   {@.en Name 'name' is invalid.}
     *
     */
    public void rebind(final NameComponent[] name, 
                        Object obj, final boolean force)
        throws SystemException, NotFound, CannotProceed, InvalidName {


        try {
            if( isNamingContext(obj) ) {
                m_rootContext.rebind(name, NamingContextExtHelper.narrow(obj));
            } else {
                m_rootContext.rebind(name, obj);
            }
        } catch(NotFound ex) {
            if( force ) {
                rebindRecursive(m_rootContext, name, obj);
            } else {
                throw ex;
            }
        } catch (CannotProceed ex) {
            if( force ) {
                rebindRecursive(ex.cxt, ex.rest_of_name, obj);
            } else {
                throw ex;
            }
        }
    }

    /**
     * {@.ja Object を強制的に rebind する。}
     * {@.en Rebind Object}
     *
     * <p>
     * {@.ja Object を rebind する際に与える名前が文字列表現であること以外は 
     * rebind()と同じである。rebind(toName(string_name), obj) と等価。
     * 途中のコンテキストも強制的にバインドする}
     * {@.en This is the same as rebind() except as the given name is string
     * representation when object is rebound. rebind(toName(string_name), obj) 
     * is the same.
     * The intermediate context is bound forcibly.}
     * </p>
     *
     * @param string_name 
     *   {@.ja オブジェクトに付ける名前の文字列表現}
     *   {@.en NameComponent of name applied to object}
     * @param obj 
     *   {@.ja 関連付けられるオブジェクト}
     *   {@.en Object that is associated}
     *
     * @exception NotFound 
     *   {@.ja 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。}
     *   {@.en There is not <c_1, c_2, ..., c_(n-1)>.}
     * @exception CannotProceed 
     *   {@.ja 何らかの理由で処理を継続できない。}
     *   {@.en Processing cannot be continued for some reasons.}
     * @exception InvalidName 
     *   {@.ja 引数 name の名前が不正。}
     *   {@.en Name The argument 'name' is invalid.}
     *
     */
    public void rebindByString(final String string_name, Object obj)
        throws SystemException, NotFound, CannotProceed, InvalidName {
            rebindByString(string_name, obj, true);
    }
    /**
     * {@.ja Object を rebind する。}
     * {@.en Rebind Object}
     *
     * <p>
     * {@.ja Object を rebind する際に与える名前が文字列表現であること以外は 
     * rebind()と同じである。rebind(toName(string_name), obj) と等価。}
     * {@.en This is the same as rebind() except as the given name is string
     * representation when object is rebound. rebind(toName(string_name), obj) 
     * is the same.}
     * </p>
     *
     * @param string_name 
     *   {@.ja オブジェクトに付ける名前の文字列表現}
     *   {@.en NameComponent of name applied to object}
     * @param obj 
     *   {@.ja 関連付けられるオブジェクト}
     *   {@.en Object that is associated}
     * @param force 
     *   {@.ja trueの場合、途中のコンテキストを強制的にバインドする
     *              (デフォルト値:true)}
     *   {@.en If true, the intermediate context is bound forcibly.
     *              (The default value:true)}
     *
     * @exception NotFound 
     *   {@.ja 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。}
     *   {@.en There is not <c_1, c_2, ..., c_(n-1)>.}
     * @exception CannotProceed 
     *   {@.ja 何らかの理由で処理を継続できない。}
     *   {@.en Processing cannot be continued for some reasons.}
     * @exception InvalidName 
     *   {@.ja 引数 name の名前が不正。}
     *   {@.en Name The argument 'name' is invalid.}
     *
     */
    public void rebindByString(final String string_name, 
                            Object obj, final boolean force)
        throws SystemException, NotFound, CannotProceed, InvalidName {
            rebind(toName(string_name), obj, force);
    }

    /**
     * {@.ja 途中のコンテキストを bind しながら Object を rebind する。}
     * {@.en Bind intermediate context recursively and rebind object}
     *
     * <p>
     * {@.ja name <c_n> で指定された NamingContext もしくは Object が
     * すでに存在する場合を除いて bindRecursive() と同じである。
     *
     * name <c_n> で指定されたバインディングがすでに存在する場合には、
     * 新しいバインディングに置き換えられる。}
     * {@.en This is the same as bindRecursive() except as NamingContext 
     * or Object specified by name <c_n> already exists.
     *
     * If the binding specified by name <c_n> already exists, 
     * new binding will be replaced.}
     *
     * @param context 
     *   {@.ja bind を開始する　NamingContext}
     *   {@.en NamingContext that starts the bind}
     * @param name 
     *   {@.ja オブジェクトに付ける名前の NameComponent}
     *   {@.en NameComponent of name applied to object}
     * @param obj 
     *   {@.ja 関連付けられるオブジェクト}
     *   {@.en Object that is associated}
     *
     * @exception CannotProceed 
     *   {@.ja 途中のコンテキストが解決できない。}
     *   {@.en The intermediate context cannot resolved.}
     * @exception InvalidName 
     *   {@.ja 与えられた name が不正。}
     *   {@.en The given name is invalid.}
     *
     */
    public void rebindRecursive(NamingContext context, 
                                final NameComponent[] name, Object obj)
        throws SystemException, CannotProceed, InvalidName, NotFound {
        int len = name.length;
        NamingContext cxt = (NamingContext)context._duplicate();
        
        for( int intIdx=0;intIdx<len;intIdx++ ) {
            if( intIdx==(len-1) ) {
                if( isNamingContext(obj) ) {
                    cxt.rebind_context(subName(name, intIdx, intIdx), NamingContextExtHelper.narrow(obj));
                } else {
                    cxt.rebind(subName(name, intIdx, intIdx), obj);
                }
                return;
            }
            // If the context is not a NamingContext, CannotProceed is thrown
            if( isNamingContext(cxt)) {
                try {
                    cxt = cxt.bind_new_context(subName(name, intIdx, intIdx));
                } catch (AlreadyBound ex) {
                    cxt = NamingContextExtHelper.narrow(cxt.resolve(subName(name, intIdx, intIdx)));
                }
            } else {
                throw new CannotProceed(cxt, subName(name, intIdx));
            }
        }
        return;
    }

    /**
     * {@.ja NamingContext を強制的に bind する。}
     * {@.en Bind NamingContext}
     *
     * <p>
     * {@.ja bind されるオブジェクトが NamingContext であることを除いて bind() 
     * と同じである。途中のコンテキストも強制的にバインドする 。}
     * {@.en This is the same as bind() except as the bound object is 
     * NamingContext.The intermediate context is bound forcibly.}
     *
     * @param name 
     *   {@.ja オブジェクトに付ける名前のネームコンポーネント}
     *   {@.en NameComponent of name applied to object}
     * @param name_cxt 
     *   {@.ja 関連付けられる NamingContext}
     *   {@.en Object that is associated}
     *
     * @exception NotFound 
     *   {@.ja 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。}
     *   {@.en There is not <c_1, c_2, ..., c_(n-1)>.}
     * @exception CannotProceed
     *   {@.ja 何らかの理由で処理を継続できない。}
     *   {@.en Processing cannot be continued for some reasons.}
     * @exception InvalidName
     *   {@.ja 引数 name の名前が不正。}
     *   {@.en The argument 'name' is invalid.}
     * @exception AlreadyBound 
     *   {@.ja name <c_n> の Object がすでにバインドされている。}
     *   {@.en The object of name<c_n> is already bound.}
     */
    public void bindContext(final NameComponent[] name, NamingContext name_cxt)
    throws SystemException, NotFound, CannotProceed, InvalidName, AlreadyBound {
        this.bindContext(name, name_cxt, true);
    }    
    /**
     * {@.ja NamingContext を bind する。}
     * {@.en Bind NamingContext}
     *
     * <p>
     * {@.ja bind されるオブジェクトが NamingContext であることを除いて bind() 
     * と同じである。}
     * {@.en This is the same as bind() except as the bound object is 
     * NamingContext.}
     *
     * @param name 
     *   {@.ja オブジェクトに付ける名前のネームコンポーネント}
     *   {@.en NameComponent of name applied to object}
     * @param name_cxt 
     *   {@.ja 関連付けられる NamingContext}
     *   {@.en Object that is associated}
     * @param force 
     *   {@.ja trueの場合、途中のコンテキストを強制的にバインドする
     *              (デフォルト値:true)}
     *   {@.en If true, the intermediate context is bound forcibly.
     *              (The default value:true)}
     *
     * @exception NotFound 
     *   {@.ja 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。}
     *   {@.en There is not <c_1, c_2, ..., c_(n-1)>.}
     * @exception CannotProceed
     *   {@.ja 何らかの理由で処理を継続できない。}
     *   {@.en Processing cannot be continued for some reasons.}
     * @exception InvalidName
     *   {@.ja 引数 name の名前が不正。}
     *   {@.en The argument 'name' is invalid.}
     * @exception AlreadyBound 
     *   {@.ja name <c_n> の Object がすでにバインドされている。}
     *   {@.en The object of name<c_n> is already bound.}
     */
    public void bindContext(final NameComponent[] name, NamingContext name_cxt, final boolean force)
    throws SystemException, NotFound, CannotProceed, InvalidName, AlreadyBound {
        this.bind(name, name_cxt, force);
    }
    
    /**
     * {@.ja NamingContext を強制的に bind する。}
     * {@.en Bind NamingContext}
     *
     * <p>
     * {@.ja bind されるオブジェクトが NamingContext であることを除いて
     * bindByString() と同じである。途中のコンテキストを強制的にバインドする。}
     * {@.en This is the same as bindByString() except as the bound object is
     * NamingContext.The intermediate context is bound forcibly.}
     *
     * @param string_name
     *   {@.ja オブジェクトに付ける名前の文字列表現}
     *   {@.en String representation of name applied to object}
     * @param name_cxt 
     *   {@.ja 関連付けられる NamingContext}
     *   {@.en NamingContext that is associated}
     *
     * @exception NotFound 
     *   {@.ja 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。}
     *   {@.en There is not <c_1, c_2, ..., c_(n-1)>.}
     * @exception CannotProceed 
     *   {@.ja 何らかの理由で処理を継続できない。}
     *   {@.en Processing cannot be continued for some reasons.}
     * @exception InvalidName 
     *   {@.ja 引数 name の名前が不正。}
     *   {@.en The argument 'name' is invalid.}
     * @exception AlreadyBound 
     *   {@.ja name <n> の Object がすでにバインドされている。}
     *   {@.en The object of name<n> is already bound.}
     *
     */
    public void bindContext(final String string_name, NamingContext name_cxt)
    throws SystemException, NotFound, CannotProceed, InvalidName, AlreadyBound {
        this.bindContext(string_name, name_cxt, true);
    }
    /**
     * {@.ja NamingContext を bind する。}
     * {@.en Bind NamingContext}
     *
     * <p>
     * {@.ja bind されるオブジェクトが NamingContext であることを除いて
     * bindByString() と同じである。途中のコンテキストを強制的にバインドする。}
     * {@.en This is the same as bindByString() except as the bound object is
     * NamingContext.The intermediate context is bound forcibly.}
     *
     * @param string_name
     *   {@.ja オブジェクトに付ける名前の文字列表現}
     *   {@.en String representation of name applied to object}
     * @param name_cxt 
     *   {@.ja 関連付けられる NamingContext}
     *   {@.en NamingContext that is associated}
     * @param force 
     *   {@.ja trueの場合、途中のコンテキストを強制的にバインドする
     *              (デフォルト値:true)}
     *   {@.en If true, the intermediate context is bound forcibly.
     *              (The default value:true)}
     *
     *
     * @exception NotFound 
     *   {@.ja 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。}
     *   {@.en There is not <c_1, c_2, ..., c_(n-1)>.}
     * @exception CannotProceed 
     *   {@.ja 何らかの理由で処理を継続できない。}
     *   {@.en Processing cannot be continued for some reasons.}
     * @exception InvalidName 
     *   {@.ja 引数 name の名前が不正。}
     *   {@.en The argument 'name' is invalid.}
     * @exception AlreadyBound 
     *   {@.ja name <n> の Object がすでにバインドされている。}
     *   {@.en The object of name<n> is already bound.}
     *
     */
    public void bindContext(final String string_name, NamingContext name_cxt, final boolean force)
    throws SystemException, NotFound, CannotProceed, InvalidName, AlreadyBound {
        this.bindContext(toName(string_name), name_cxt, force);        
    }

    /**
     * {@.ja 途中のコンテキストを再帰的に bind し NamingContext を bind する。}
     * {@.en Bind intermediate context recursively and bind NamingContext}
     *
     * <p>
     * {@.ja bind されるオブジェクトが NamingContext であることを除いて
     * bindRecursive() と同じである。}
     * {@.en This is the same as bindRecursive() except as the bound object
     * is NamingContext.}
     *
     * @param context 
     *   {@.ja bind を開始する　NamingContext}
     *   {@.en NamingContext that starts the bind}
     * @param name 
     *   {@.ja オブジェクトに付ける名前のネームコンポーネント}
     *   {@.en NameComponent of name applied to object}
     * @param name_cxt 
     *   {@.ja 関連付けられる NamingContext}
     *   {@.en NamingContext that is associated}
     */
    public void bindContextRecursive(NamingContext context, final NameComponent[] name, NamingContext name_cxt) 
            throws CannotProceed, InvalidName, AlreadyBound, NotFound{
        this.bindRecursive(context, name, name_cxt);
        return;
        
    }

    /**
     * {@.ja NamingContext を強制的に rebind する。}
     * {@.en Rebind NamingContext}
     *
     * <p>
     * {@.ja name で指定されたコンテキストがすでに存在する場合を
     * 除いて bindContext() と同じである。
     * バインディングがすでに存在する場合には、新しいバインディングに
     * 置き換えられる。途中のコンテキストも強制的にバインドする。}
     * {@.en This is the same as bindContext() except as context specified
     * by name already exists.
     * If the binding already exists, new binding will be replaced.
     * The intermediate context is bound forcibly.}
     *
     * @param name 
     *   {@.ja オブジェクトに付ける名前のネームコンポーネント}
     *   {@.en NameComponent applied to object}
     * @param name_cxt 
     *   {@.ja 関連付けられる NamingContext}
     *   {@.en Object that is associated}
     *
     * @exception NotFound 
     *   {@.ja 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。}
     *   {@.en There is not <c_1, c_2, ..., c_(n-1)>.}
     * @exception CannotProceed 
     *   {@.ja 何らかの理由で処理を継続できない。}
     *   {@.en Processing cannot be continued for some reasons.}
     * @exception InvalidName 
     *   {@.ja 引数 name の名前が不正。}
     *   {@.en The argument 'name' is invalid.}
     *
     */
    public void rebindContext(final NameComponent[] name, 
                                                    NamingContext name_cxt)
        throws SystemException, NotFound, CannotProceed, InvalidName {
        this.rebindContext(name, name_cxt, true);
    }
    /**
     * {@.ja NamingContext を rebind する。}
     * {@.en Rebind NamingContext}
     *
     * <p>
     * {@.ja name で指定されたコンテキストがすでに存在する場合を
     * 除いて bindContext() と同じである。
     * バインディングがすでに存在する場合には、新しいバインディングに
     * 置き換えられる。}
     * {@.en This is the same as bindContext() except as context specified
     * by name already exists.
     * If the binding already exists, new binding will be replaced.}
     *
     * @param name 
     *   {@.ja オブジェクトに付ける名前のネームコンポーネント}
     *   {@.en NameComponent applied to object}
     * @param name_cxt 
     *   {@.ja 関連付けられる NamingContext}
     *   {@.en Object that is associated}
     * @param force 
     *   {@.ja trueの場合、途中のコンテキストを強制的にバインドする
     *              (デフォルト値:true)}
     *   {@.en If true, the intermediate context is bound forcibly.
     *              (The default value:true)}
     *
     * @exception NotFound 
     *   {@.ja 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。}
     *   {@.en There is not <c_1, c_2, ..., c_(n-1)>.}
     * @exception CannotProceed 
     *   {@.ja 何らかの理由で処理を継続できない。}
     *   {@.en Processing cannot be continued for some reasons.}
     * @exception InvalidName 
     *   {@.ja 引数 name の名前が不正。}
     *   {@.en The argument 'name' is invalid.}
     *
     */
    public void rebindContext(final NameComponent[] name, 
                                NamingContext name_cxt, final boolean force)
        throws SystemException, NotFound, CannotProceed, InvalidName {
        this.rebind(name, name_cxt, force);
        return;
        
    }

    /**
     * {@.ja NamingContext を強制的に rebind する。}
     * {@.en Rebind NamingContext}
     *
     * <p>
     * {@.ja name で指定されたコンテキストがすでに存在する場合
     * を除いて bindContext() と同じである。
     * バインディングがすでに存在する場合には、新しいバインディングに
     * 置き換えられる。途中のコンテキストを強制的にバインドする。}
     * {@.en This is the same as bindContext() except as context specified
     * by name already exists.
     * If the binding already exists, new binding will be replaced.
     * The intermediate context is bound forcibly.}
     *
     * @param string_name 
     *   {@.ja オブジェクトに付ける名前の文字列表現}
     *   {@.en String representation of name applied to object}
     * @param name_cxt 
     *   {@.ja 関連付けられる NamingContext}
     *   {@.en NamingContext that is associated}
     *
     * @exception NotFound 
     *   {@.ja 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。}
     *   {@.en There is not <c_1, c_2, ..., c_(n-1)>.}
     * @exception CannotProceed 
     *   {@.ja 何らかの理由で処理を継続できない。}
     *   {@.en Processing cannot be continued for some reasons.}
     * @exception InvalidName 
     *   {@.ja 引数 name の名前が不正。}
     *   {@.en The argument 'name' is invalid.}
     */
    public void rebindContext(final String string_name, NamingContext name_cxt)
        throws SystemException, NotFound, CannotProceed, InvalidName {
        this.rebindContext(string_name, name_cxt, true);
    }
    /**
     * {@.ja NamingContext を rebind する。}
     * {@.en Rebind NamingContext}
     *
     * <p>
     * {@.ja name で指定されたコンテキストがすでに存在する場合
     * を除いて bindContext() と同じである。
     * バインディングがすでに存在する場合には、新しいバインディングに
     * 置き換えられる。}
     * {@.en This is the same as bindContext() except as context specified
     * by name already exists.
     * If the binding already exists, new binding will be replaced.}
     *
     * @param string_name 
     *   {@.ja オブジェクトに付ける名前の文字列表現}
     *   {@.en String representation of name applied to object}
     * @param name_cxt 
     *   {@.ja 関連付けられる NamingContext}
     *   {@.en NamingContext that is associated}
     * @param force 
     *   {@.ja trueの場合、途中のコンテキストを強制的にバインドする
     *              (デフォルト値:true)}
     *   {@.en If true, the intermediate context is bound forcibly.
     *              (The default value:true)}
     *
     * @exception NotFound 
     *   {@.ja 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。}
     *   {@.en There is not <c_1, c_2, ..., c_(n-1)>.}
     * @exception CannotProceed 
     *   {@.ja 何らかの理由で処理を継続できない。}
     *   {@.en Processing cannot be continued for some reasons.}
     * @exception InvalidName 
     *   {@.ja 引数 name の名前が不正。}
     *   {@.en The argument 'name' is invalid.}
     */
    public void rebindContext(final String string_name, 
                                NamingContext name_cxt, final boolean force)
        throws SystemException, NotFound, CannotProceed, InvalidName {
        this.rebindContext(toName(string_name), name_cxt, force);
    }

    /**
     * {@.ja 途中のコンテキストを再帰的に rebind し NamingContext 
     * を rebind する。}
     * {@.en Rebind intermediate context recursively and rebind NamingContext}
     *
     * <p>
     * {@.ja bind されるオブジェクトが NamingContext であることを除いて
     * rebindRecursive() と同じである。}
     * {@.en This is the same as rebindRecursive() except as the bound object 
     * is NamingContext.}
     *
     * @param context 
     *   {@.ja bind を開始する　NamingContext}
     *   {@.en NamingContext that starts the bind}
     * @param name 
     *   {@.ja オブジェクトに付ける名前の NameComponent}
     *   {@.en NameComponent applied to object}
     * @param name_cxt 
     *   {@.ja 関連付けられる NamingContext}
     *   {@.en NamingContext that is associated}
     */
    public void rebindContextRecursive(NamingContext context, final NameComponent[] name, NamingContext name_cxt) 
        throws CannotProceed, InvalidName, NotFound {
        this.rebindRecursive(context, name, name_cxt);
      return;
    }

    /**
     * {@.ja 与えられた NameComponent にバインドされている Object を返す。}
     * {@.en Return object bound on the specified NameComponent}
     *
     * <p>
     * {@.ja name に bind されているオブジェクト参照を返す。
     * ネームコンポーネント <c_1, c_2, ... c_n> は再帰的に解決される。
     * 
     * CosNaming::resolve() とほぼ同等の働きをするが、常に与えられた
     * ネームサーバのルートコンテキストに対して resolve() が呼び出される点が
     * 異なる。}
     * {@.en Return the object reference that is bound to name.
     * Resolve the name component<c_1, c_2, ... c_n> recursively.
     * 
     * Almost the same operation as CosNaming::resolve(), 
     * but there is a difference that resolve() is invoked for the root context
     * of the given name server.}
     *
     * @param name 
     *   {@.ja 解決すべきオブジェクトの名前のネームコンポーネント}
     *   {@.en The name component of object name that should be resolved}
     *
     * @return 
     *   {@.ja 解決されたオブジェクト参照}
     *   {@.en The reference to the resolved object}
     *
     * @exception NotFound 
     *   {@.ja 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。}
     *   {@.en There is not <c_1, c_2, ..., c_(n-1)>.}
     * @exception CannotProceed 
     *   {@.ja 何らかの理由で処理を継続できない。}
     *   {@.en Processing cannot be continued for some reasons.}
     * @exception InvalidName 
     *   {@.ja 引数 name の名前が不正。}
     *   {@.en The argument 'name' is invalid.}
     */
    public Object resolve(final NameComponent[] name)
        throws SystemException, NotFound, CannotProceed, InvalidName {
        return m_rootContext.resolve(name);
    }

    /**
     * {@.ja 与えられた NameComponent にバインドされている Object を返す。}
     * {@.en Return object bound on the specified name}
     *
     * <p>
     * {@.ja name に bind されているオブジェクト参照を返す。
     * ネームコンポーネント <c_1, c_2, ... c_n> は再帰的に解決される。
     * 
     * CosNaming::resolve() とほぼ同等の働きをするが、常に与えられた
     * ネームサーバのルートコンテキストに対して resolve() が呼び出される点が
     * 異なる。}
     * {@.en Return the object reference that is bound to name.
     * Resolve the name component<c_1, c_2, ... c_n> recursively.
     * 
     * Almost the same operation as CosNaming::resolve(), 
     * but there is a difference that resolve() is invoked for the root context
     * of the given name server.}
     *
     * @param string_name 
     *   {@.ja 解決すべきオブジェクトの名前の文字列表現}
     *   {@.en The string representation of object name that should be resolved}
     *
     * @return 
     *   {@.ja 解決されたオブジェクト参照}
     *   {@.en The reference to the resolved object}
     *
     * @exception NotFound 
     *   {@.ja 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。}
     *   {@.en There is not <c_1, c_2, ..., c_(n-1)>.}
     * @exception CannotProceed 
     *   {@.ja 何らかの理由で処理を継続できない。}
     *   {@.en Processing cannot be continued for some reasons.}
     * @exception InvalidName 
     *   {@.ja 引数 name の名前が不正。}
     *   {@.en The argument 'name' is invalid.}
     *
     */
    public Object resolve(final String string_name)
        throws SystemException, NotFound, CannotProceed, InvalidName {
        return resolve(toName(string_name));
    }

    /**
     * {@.ja 与えられた NameComponent のバインディングを削除する。}
     * {@.en Unbind a binding specified by NameComponent}
     *
     * <p>
     * {@.ja name に bind されているオブジェクト参照を返す。
     * ネームコンポーネント <c_1, c_2, ... c_n> は再帰的に解決される。
     * 
     * CosNaming::unbind() とほぼ同等の働きをするが、常に与えられた
     * ネームサーバのルートコンテキストに対して unbind() が呼び出される点が
     * 異なる。}
     * {@.en Return the object reference that is bound to name.
     * Resolve the name component<c_1, c_2, ... c_n> recursively.
     * 
     * Almost the same operation as CosNaming::unbind(), 
     * but there is a difference that unbind() is invoked for the root context
     * of the always given name server.}
     *
     *
     * @param name 
     *   {@.ja 削除するオブジェクトのネームコンポーネント}
     *   {@.en The name component of the deleted object}
     *
     * @exception NotFound 
     *   {@.ja 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。}
     *   {@.en There is not <c_1, c_2, ..., c_(n-1)>.}
     * @exception CannotProceed
     *   {@.ja 何らかの理由で処理を継続できない。}
     *   {@.en Processing cannot be continued for some reasons.}
     * @exception InvalidName 
     *   {@.ja 引数 name の名前が不正。}
     *   {@.en The argument 'name' is invalid.}
     */
    public void unbind(final NameComponent[] name) 
    throws SystemException, NotFound, CannotProceed, InvalidName {
        m_rootContext.unbind(name);
    }

    /**
     * {@.ja 与えられた NameComponent のバインディングを削除する。}
     * {@.en Unbind a binding specified by string representation}
     *
     * <p>
     * {@.ja name に bind されているオブジェクト参照を返す。
     * ネームコンポーネント <c_1, c_2, ... c_n> は再帰的に解決される。
     * 
     * CosNaming::unbind() とほぼ同等の働きをするが、常に与えられた
     * ネームサーバのルートコンテキストに対して unbind() が呼び出される点が
     * 異なる。}
     * {@.en Return the object reference that is bound to name.
     * Resolve the name component<c_1, c_2, ... c_n> recursively.
     * 
     * Almost the same operation as CosNaming::unbind(), 
     * but there is a difference that unbind() is invoked for the root context
     * of the always given name server.}
     *
     * @param string_name 
     *   {@.ja 解決すべきオブジェクトの名前の文字列表現}
     *   {@.en The string representation of object name that should be resolved}
     *
     * @exception NotFound 
     *   {@.ja 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。}
     *   {@.en There is not <c_1, c_2, ..., c_(n-1)>.}
     * @exception CannotProceed 
     *   {@.ja 何らかの理由で処理を継続できない。}
     *   {@.en Processing cannot be continued for some reasons.}
     * @exception InvalidName 
     *   {@.ja 引数 name の名前が不正。}
     *   {@.en The argument 'name' is invalid.}
     *
     */
    public void unbind(final String string_name) 
    throws SystemException, NotFound, CannotProceed, InvalidName {
        this.unbind(this.toName(string_name));
    }

    /**
     * {@.ja 新しいコンテキストを生成する。}
     * {@.en Create new NamingContext.}
     * <p>
     * {@.ja 与えられたネームサーバ上で生成された NamingContext を返す。
     * 返された NamingContext は bind されていない。}
     * {@.en Return NamingContext that has been created on the given name 
     * server. The returned NamingContext has not bound yet.}
     * 
     * @return 
     *   {@.ja 生成された新しい NamingContext}
     *   {@.en New created NamingContext}
     *
     */
    public NamingContext newContext() {
      return m_rootContext.new_context();
    }

    /**
     * {@.ja 新しいコンテキストを強制的に bind する。}
     * {@.en Bind new NamingContext}
     *
     * <p>
     * {@.ja 与えられた name に対して新しいコンテキストをバインドする。
     * 生成された　NamingContext はネームサーバ上で生成されたものである。
     * 途中のコンテキストを強制的にバインドする。}
     * {@.en Bind new context for the given name.
     * The created NamingContext is a creation on the name server.
     * The intermediate context is bound forcibly.}
     * 
     * 
     * @param name 
     *   {@.ja NamingContextに付ける名前のネームコンポーネント}
     *   {@.en name NameComponent applied to NamingContext}
     *
     * @return 
     *   {@.ja 生成された新しい NamingContext}
     *   {@.en New created NamingContext}
     *
     * @exception NotFound 
     *   {@.ja 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。}
     *   {@.en There is not <c_1, c_2, ..., c_(n-1)>.}
     * @exception CannotProceed 
     *   {@.ja 何らかの理由で処理を継続できない。}
     *   {@.en Processing cannot be continued for some reasons.}
     * @exception InvalidName 
     *   {@.ja 引数 name の名前が不正。}
     *   {@.en The argument 'name' is invalid.}
     * @exception AlreadyBound 
     *   {@.ja name <n> の Object がすでにバインドされている。}
     *   {@.en The object of name<n> is already bound.}
     */
    public NamingContext bindNewContext(final NameComponent[] name)
    throws SystemException, NotFound, CannotProceed, InvalidName, AlreadyBound {
        return this.bindNewContext(name, true);
    }
    /**
     * {@.ja 新しいコンテキストを bind する。}
     * {@.en Bind new NamingContext}
     *
     * <p>
     * {@.ja 与えられた name に対して新しいコンテキストをバインドする。
     * 生成された　NamingContext はネームサーバ上で生成されたものである。}
     * {@.en Bind new context for the given name.
     * The created NamingContext is a creation on the name server.}
     * 
     * 
     * @param name 
     *   {@.ja NamingContextに付ける名前のネームコンポーネント}
     *   {@.en name NameComponent applied to NamingContext}
     * @param force
     *   {@.ja trueの場合、途中のコンテキストを強制的にバインドする
     *              (デフォルト値:true)}
     *   {@.en If true, the intermediate context is bound forcibly.
     *              (The default value:true)}
     *
     * @return 
     *   {@.ja 生成された新しい NamingContext}
     *   {@.en New created NamingContext}
     *
     * @exception NotFound 
     *   {@.ja 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。}
     *   {@.en There is not <c_1, c_2, ..., c_(n-1)>.}
     * @exception CannotProceed 
     *   {@.ja 何らかの理由で処理を継続できない。}
     *   {@.en Processing cannot be continued for some reasons.}
     * @exception InvalidName 
     *   {@.ja 引数 name の名前が不正。}
     *   {@.en The argument 'name' is invalid.}
     * @exception AlreadyBound 
     *   {@.ja name <n> の Object がすでにバインドされている。}
     *   {@.en The object of name<n> is already bound.}
     */
    public NamingContext bindNewContext(final NameComponent[] name, 
                                                boolean force)
    throws SystemException, NotFound, CannotProceed, InvalidName, AlreadyBound {
        try {
            return m_rootContext.bind_new_context(name);
        } catch(NotFound ex ) {
            if( force ) {
                bindRecursive(m_rootContext, name, newContext());
            } else {
                throw ex;
            }
        } catch(CannotProceed ex) {
            if( force ) {
                bindRecursive(ex.cxt, ex.rest_of_name, newContext());
            } else {
                throw ex;
            }
        }
        return null;
    }

    /**
     * {@.ja 新しいコンテキストを強制的に bind する。}
     * {@.en Bind new NamingContext}
     *
     * <p>
     * {@.ja 与えられた文字列に対応する新しいコンテキストをバインドする。
     * 生成された　NamingContext はネームサーバ上で生成されたものである。
     * 途中のコンテキストを強制的にバインドする。}
     * {@.en Bind new context corresponding to the given string.
     * The created NamingContext is a creation on the name server.
     * The intermediate context is bound forcibly.}
     * 
     * @param string_name 
     *   {@.ja NamingContextに付ける名前の文字列表現}
     *   {@.en The string representation of name applied to 
     *                    NamingContext}
     * 
     * @return 
     *   {@.ja 生成された新しい NamingContext}
     * @return New created NamingContext
     *
     * @exception NotFound 
     *   {@.ja 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。}
     *   {@.en There is not <c_1, c_2, ..., c_(n-1)>.}
     * @exception CannotProceed 
     *   {@.ja 何らかの理由で処理を継続できない。}
     *   {@.en Processing cannot be continued for some reasons.}
     * @exception InvalidName 
     *   {@.ja 引数 name の名前が不正。}
     *   {@.en The argument 'name' is invalid.}
     * @exception AlreadyBound 
     *   {@.ja name <n> の Object がすでにバインドされている。}
     *   {@.en The object of name<n> is already bound.}
     */
    public NamingContext bindNewContext(final String string_name)
    throws SystemException, NotFound, CannotProceed, InvalidName, AlreadyBound {
        return this.bindNewContext(string_name, true);
    }
    /**
     * {@.ja 新しいコンテキストを bind する。}
     * {@.en Bind new NamingContext}
     *
     * <p>
     * {@.ja 与えられた文字列に対応する新しいコンテキストをバインドする。
     * 生成された　NamingContext はネームサーバ上で生成されたものである。}
     * {@.en Bind new context corresponding to the given string.
     * The created NamingContext is a creation on the name server.}
     * 
     * @param string_name 
     *   {@.ja NamingContextに付ける名前の文字列表現}
     *   {@.en The string representation of name applied to 
     *                    NamingContext}
     * @param force 
     *   {@.ja trueの場合、途中のコンテキストを強制的にバインドする
     *              (デフォルト値:true)}
     *   {@.en If true, the intermediate context is bound forcibly.
     *              (The default value:true)}
     * 
     * @return 
     *   {@.ja 生成された新しい NamingContext}
     * @return New created NamingContext
     *
     * @exception NotFound 
     *   {@.ja 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。}
     *   {@.en There is not <c_1, c_2, ..., c_(n-1)>.}
     * @exception CannotProceed 
     *   {@.ja 何らかの理由で処理を継続できない。}
     *   {@.en Processing cannot be continued for some reasons.}
     * @exception InvalidName 
     *   {@.ja 引数 name の名前が不正。}
     *   {@.en The argument 'name' is invalid.}
     * @exception AlreadyBound 
     *   {@.ja name <n> の Object がすでにバインドされている。}
     *   {@.en The object of name<n> is already bound.}
     */
    public NamingContext bindNewContext(final String string_name, boolean force)
    throws SystemException, NotFound, CannotProceed, InvalidName, AlreadyBound {
        return this.bindNewContext(toName(string_name));
    }

    /**
     * {@.ja NamingContext を非アクティブ化する。}
     * {@.en Destroy the naming context}
     *
     * <p>
     * {@.ja context で指定された NamingContext を非アクティブ化する。
     * context に他のコンテキストがバインドされている場合は NotEmpty 例外が
     * 発生する。}
     * {@.en Destroy the specified naming context.
     * Any bindings should be <unbind> in which the given context is bound to
     * some names before invoking <destroy> operation on it. }
     * 
     * @param context 
     *   {@.ja 非アクティブ化する NamingContext}
     *   {@.en NamingContext which is destroied.}
     *
     * @exception NotEmpty 
     *   {@.ja 対象context に他のコンテキストがバインドされている。}
     *   {@.en The target context is bound to the other context.}
     *
     */
    public void destroy(NamingContext context) 
        throws SystemException, NotEmpty {
      context.destroy();
    }

    /**
     * {@.ja NamingContext を再帰的に下って非アクティブ化する。}
     * {@.en Destroy the naming context recursively}
     *
     * <p>
     * {@.ja context で与えられた NamingContext に対して、name で指定された
     * ネームコンポーネント <c_1, ... c_(n-1)> を NamingContext として
     * 解決しながら、名前 <c_n> に対して 非アクティブ化を行う。}
     * {@.en For NamingContext given by Context, Destroy name <c_n> with 
     * solving the name component specified by name 
     * as NamingContext recursively.}
     *
     * @param context 
     *   {@.ja 非アクティブ化する NamingContext}
     *   {@.en context NamingContext which is Destroied.}
     *
     * @exception NotEmpty 
     *   {@.ja 対象context に他のコンテキストがバインドされている。}
     *   {@.en The target context is bound to the other context.}
     * @exception NotFound 
     *   {@.ja 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。}
     *   {@.en There is not <c_1, c_2, ..., c_(n-1)>.}
     * @exception CannotProceed 
     *   {@.ja 何らかの理由で処理を継続できない。}
     *   {@.en Processing cannot be continued for some reasons.}
     * @exception InvalidName 
     *   {@.ja 引数 name の名前が不正。}
     *   {@.en The argument 'name' is invalid.}
     */
    public void destroyRecursive(NamingContext context)
    throws SystemException, NotEmpty, NotFound, CannotProceed, InvalidName {
        BindingListHolder bl = new BindingListHolder();
        BindingIteratorHolder bi = new BindingIteratorHolder();
        boolean cont = true;
        
        context.list(m_blLength, bl, bi);
      
        while( cont ) {
            int len = bl.value.length;
    
            for(int intIdx=0;intIdx<len;++intIdx) {
                if( bl.value[intIdx].binding_type == BindingType.ncontext ) {
                    // If Object is context, destroy recursive.
                    NamingContext next_context 
                        = NamingContextExtHelper.narrow(
                              context.resolve(bl.value[intIdx].binding_name));
        
                    // Recursive function call
                    destroyRecursive(next_context); // +++ Recursive call +++
                    context.unbind(bl.value[intIdx].binding_name);
                    next_context.destroy();
                } 
                else if( bl.value[intIdx].binding_type == BindingType.nobject) {
                    // If Object is object, unbind it.
                    context.unbind(bl.value[intIdx].binding_name);
                }
            }
    
            // no more binding -> do-while loop will be finished
            if( bi==null ) cont = false;
            else {
                if( bi.value!=null ) {
                    if( bi.value.next_n(m_blLength, bl) == false )
                        return;
                } else {
                    return;
                }
	    }
        }
      
      if( bi!=null ) bi.value.destroy();
      return;
    }

    /**
     * {@.ja すべての Binding を削除する。}
     * {@.en Destroy all bindings}
     *
     * <p>
     * {@.ja 登録されている全てのBinding を削除する。}
     * {@.en Destroy all bindings that are registered.}
     * @exception NotEmpty 
     *   {@.ja 対象コンテキストに他のコンテキストがバインドされている}
     *   {@.en The target context is bound to the other context.}
     * @exception NotFound 
     *   {@.ja 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。}
     *   {@.en There is not <c_1, c_2, ..., c_(n-1)>.}
     * @exception CannotProceed 
     *   {@.ja 何らかの理由で処理を継続できない。}
     *   {@.en Processing cannot be continued for some reasons.}
     * @exception InvalidName 
     *   {@.ja 引数 name の名前が不正。}
     *   {@.en The argument 'name' is invalid.}
     *
     */
    public void clearAll() throws NotEmpty, NotFound, CannotProceed, InvalidName {
        if( m_rootContext!= null ) {
            this.destroyRecursive(m_rootContext);
        }
    }

    /**
     * {@.ja 与えられた NamingContext の Binding を取得する}
     * {@.en Get Binding of the given NamingContext}
     *
     * <p>
     * {@.ja 指定された NamingContext の Binding を取得する。}
     * {@.en Get Binding of the given NamingContext.}
     *
     * @param name_cxt 
     *   {@.ja Binding 取得対象 NamingContext}
     *   {@.en NamingContext of the getting target Binding}
     * @param how_many 
     *   {@.ja Binding を取得する階層の深さ}
     *   {@.en The depth to get Binding}
     * @param bl 
     *   {@.ja 取得した Binding を保持するホルダ}
     *   {@.en The holder to hold the got Binding}
     * @param bi 
     *   {@.ja 取得した Binding をたどるためのイテレータ}
     *   {@.en The iterator to detect the got Binding}
     */
    public void list(NamingContext name_cxt, long how_many,
                        BindingListHolder bl, BindingIteratorHolder bi) {
        name_cxt.list((int)how_many, bl, bi);
    }

    /**
     * {@.ja 与えられた NameComponent の文字列表現を返す。}
     * {@.en Get string representation of given NameComponent}
     *
     * <p>
     * {@.ja 指定された NameComponent を文字に変換する。}
     * {@.en Transform specified NameComponent into string representation.}
     *
     * @param name 
     *   {@.ja 変換対象 NameComponent}
     *   {@.en The target NameComponent for transformation}
     *
     * @return 
     *   {@.ja 文字列変換結果}
     *   {@.en Trnasformation result of string representation}
     *
     * @exception InvalidName 
     *   {@.ja 引数 name の名前が不正。}
     *   {@.en The argument 'name' is invalid.}
     */
    public String toString(final NameComponent[] name) 
    throws SystemException, InvalidName {
        if( name==null || name.equals("")) throw new InvalidName();
    
        int slen = 0;
        slen = getNameLength(name);
    
        StringHolder string_name = new StringHolder();
        string_name.value = new String();
        nameToString(name, string_name, slen);
    
        return string_name.value;
    }

    /**
     * {@.ja 与えられた文字列表現を NameComponent に分解する。}
     * {@.en Resolve given string representation to NameComponent}
     *
     * <p>
     * {@.ja 指定された文字列を NameComponent に変換する。}
     * {@.en Transform given string representation to NameComponent.}
     *
     * @param sname 
     *   {@.ja 変換対象文字列}
     *   {@.en The target string representation to transform}
     *
     * @return NameComponent 
     *   {@.ja 変換結果}
     *   {@.en NameComponent The result of transformation}
     *
     * @exception InvalidName 
     *   {@.ja 引数 string_name が不正。}
     *   {@.en The argument 'name' is invalid.}
     */
    public NameComponent[] toName(final String sname) 
    throws SystemException, InvalidName {
        if( sname==null || sname.equals("") ) throw new InvalidName();
        
        String string_name = sname;
        String[] name_comps;
      
        // String name should include 1 or more names
        int nc_length = 0;
        name_comps = string_name.split("/");
        nc_length = name_comps.length;
        if( !(nc_length>0) ) throw new InvalidName();
      
        // Name components are allocated
        NameComponent[] name = new NameComponent[nc_length];
      
        // Insert id and kind to name components
        for( int intIdx=0; intIdx<nc_length; ++intIdx ) {
            int pos = name_comps[intIdx].lastIndexOf(".");
            if( name[intIdx]==null ) name[intIdx] = new NameComponent();
            if( !(pos<0) ) {
                name[intIdx].id   = name_comps[intIdx].substring(0,pos);
                name[intIdx].kind = name_comps[intIdx].substring(pos+1);
            } else {
                name[intIdx].id   = name_comps[intIdx];
                name[intIdx].kind = "";
            }
        }
        return name;
    }

    /**
     * {@.ja 与えられた addre と string_name から URL表現を取得する。}
     * {@.en Get URL representation from given addr and string_name}
     *
     * <p>
     * {@.ja 指定されたアドレスと名称をURLに変換する。}
     * {@.en Convert specified addr and string_name into URL}
     *
     * @param addr 
     *   {@.ja 変換対象アドレス}
     *   {@.en The target address for conversion}
     * @param string_name 
     *   {@.ja 変換対象名称}
     *   {@.en The target name for conversion}
     *
     * @return 
     *   {@.ja URL 変換結果}
     *   {@.en URL Conversion result}
     *
     * @exception InvalidAddress 
     *   {@.ja 引数 addr が不正。}
     *   {@.en The argument 'addr' is invalid.}
     * @exception InvalidName 
     *   {@.ja 引数 string_name が不正。}
     *   {@.en The argument 'string_name' is invalid.}
     */
    public String toUrl(String addr, String string_name)
        throws SystemException, InvalidAddress, InvalidName {
      return m_rootContext.to_url(addr, string_name);
    }

    /**
     * {@.ja 与えられた文字列表現を resolve しオブジェクトを返す。}
     * {@.en Resolve from name of string representation and get object}
     *
     * <p>
     * {@.ja 指定された文字列表現をresolveし，オブジェクトを取得する。}
     * {@.en Resolve specified string representation and get object}
     *
     * @param string_name 
     *   {@.ja 取得対象オブジェクト文字列表現}
     *   {@.en The string representation of getting target object}
     *
     * @return 
     *   {@.ja 解決されたオブジェクト}
     *   {@.en The resolved object}
     *
     * @exception NotFound 
     *   {@.ja 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。}
     *   {@.en There is not <c_1, c_2, ..., c_(n-1)>.}
     * @exception CannotProceed 
     *   {@.ja 何らかの理由で処理を継続できない。}
     *   {@.en Processing cannot be continued for some reasons.}
     * @exception InvalidName 
     *   {@.ja 引数 name の名前が不正。}
     *   {@.en The argument 'name' is invalid.}
     * @exception AlreadyBound 
     *   {@.ja name <n> の Object がすでにバインドされている。}
     *   {@.en The object of name<n> is already bound.}
     */
    public Object resolveStr(final String string_name)
    throws SystemException, NotFound, CannotProceed, InvalidName, AlreadyBound {
      return resolve(string_name);
    }

    /**
     * {@.ja オブジェクトの名前をバインドまたは解決する。}
     * {@.en Bind or resolve the given name component}
     *
     * <p>
     * {@.ja 指定されたコンテキストに対してオブジェクトを NameComponent で
     * 指定された位置にバインドする。
     * 同一箇所に既に他の要素がバインド済みの場合は、既存のバインド済み要素を
     * 取得する。}
     * {@.en Bind object at the position that specified in NameComponent 
     * for the specified context.
     * When other elements are already bound at the same position, get the 
     * already bound element.}
     *
     * @param context 
     *   {@.ja bind もしくは resole 対象コンテキスト}
     *   {@.en The context to bind or resole}
     * @param name 
     *   {@.ja オブジェクトに付ける名前の NameComponent}
     *   {@.en NameComponent applied to object}
     * @param obj 
     *   {@.ja 関連付けられる Object}
     *   {@.en Object that is associated}
     *
     * @return 
     *   {@.ja NameComponent で指定された位置にバインドされているオブジェクト}
     *   {@.en The object that is bound at position specified with 
     *   NameComponent}
     *
     * @exception NotFound 
     *   {@.ja 対象オブジェクトが存在しない。}
     *   {@.en The object doesn't exist. }
     * @exception CannotProceed 
     *   {@.ja 何らかの理由で処理を継続できない。}
     *   {@.en Processing cannot be continued for some reasons.}
     * @exception InvalidName 
     *   {@.ja 引数 name の名前が不正。}
     *   {@.en The argument 'name' is invalid.}
     */
    public NamingContext bindOrResolve(NamingContext context, final NameComponent[] name, Object obj)
        throws NotFound, CannotProceed, InvalidName {
        
        context.rebind(name, obj);
        return context;
    }

    /**
     * {@.ja 名前をバインドまたは解決する。}
     * {@.en Bind or resolve the given name component}
     *
     * <p>
     * {@.en 指定されたコンテキストに対して Contextを NameComponent で
     * 指定された位置にバインドする。
     * 同一箇所に既に他の要素がバインド済みの場合は、既存のバインド済み要素を
     * 取得する。}
     * {@.en Bind Context at the position that specified in NameComponent 
     * for the specified context.
     * When other elements are already bound at the same position, get the 
     * already bound element.}
     *
     * @param context 
     *   {@.ja bind もしくは resole 対象コンテキスト}
     *   {@.en The context to bind or resole}
     * @param name 
     *   {@.ja コンテキストに付ける名前の NameComponent}
     *   {@.en NameComponent applied to object}
     * @param new_context 
     *   {@.ja 関連付けられる Context}
     *   {@.en Context that is associated}
     *
     * @return 
     *   {@.ja NameComponent で指定された位置にバインドされているContext}
     *   {@.en The Context that is bound at the position specified with 
     *         NameComponent}
     *
     */
    public NamingContext bindOrResolveContext(NamingContext context,
            final NameComponent[] name, NamingContext new_context)
            throws NotFound, CannotProceed, InvalidName {
        return bindOrResolve(context, name, new_context);
    }
    
    /**
     * {@.ja 名前をバインドまたは解決する。}
     * {@.en Bind or resolve the given name component}
     *
     * <p>
     * {@.ja 指定されたコンテキストに対して NameComponent で指定された位置に
     * 新規コンテキストをバインドする。
     * 同一箇所に既に他の要素がバインド済みの場合は、既存のバインド済み要素を
     * 取得する。}
     * {@.en Bind new Context at the position that specified in NameComponent 
     * for the specified context.
     * When other elements are already bound at the same position, get the 
     * already bound element.}
     *
     * @param context 
     *   {@.ja bind もしくは resole 対象コンテキスト}
     *   {@.en  The context to bind or resole}
     * @param name 
     *   {@.ja 新規作成するコンテキストの位置を表す NameComponent}
     *   {@.en NameComponent that indicates the position of new context}
     *
     * @return 
     *   {@.ja NameComponent で指定された位置にバインドされているContext}
     *   {@.en The Context that is bound at the position specified with 
     *         NameComponent}
     *
     */
    public NamingContext bindOrResolveContext(NamingContext context, final NameComponent[] name) 
        throws NotFound, CannotProceed, InvalidName{
        NamingContext new_context = context.new_context();
        context.rebind_context(name, new_context);
        return new_context;
    }

    /**
     * {@.ja ネームサーバの名前を取得する。}
     * {@.en Get the name of name server}
     *
     * <p>
     * {@.ja 設定したネームサーバの名前を取得する。}
     * {@.en Get the configured name of name server}
     *
     * @return 
     *   {@.ja ネームサーバの名前}
     *   {@.en The name of name server}
     *
     */
    public final String getNameServer() {
        return m_nameServer;
    }

    /**
     * {@.ja ルートコンテキストを取得する。}
     * {@.en Get the root context}
     *
     * <p>
     * {@.ja 設定したネームサーバのルートコンテキストを取得する。}
     * {@.en Get the root context of the configured name server}
     *
     * @return 
     *   {@.ja ネームサーバのルートコンテキスト}
     *   {@.en Root context ot name server}
     *
     */
    public NamingContext getRootContext() {
      return m_rootContext;
    }

    /**
     * {@.ja オブジェクトがネーミングコンテキストか判別する。}
     * {@.en Determine whether the object is NamingContext}
     *
     * <p>
     * {@.ja 指定した要素がネーミングコンテキストか判別する}
     * {@.en Determine whether the specified element is NamingContext}
     *
     * @param obj 
     *   {@.ja 判別対象要素}
     *   {@.en The target element for determination}
     *
     * @return 
     *   {@.ja 判別結果(ネーミングコンテキスト:true、それ以外:false)}
     *   {@.en Determination result (NamingContext:true, Else:false)}
     *
     */
    public boolean isNamingContext(Object obj) {
        try {
            NamingContext nc= NamingContextExtHelper.narrow(obj);
            if( nc!=null ) {
                return true;
            }
        } catch(Exception ex) {
        }
        return false;
    }

    /**
     * {@.ja 与えられた名前がネーミングコンテキストかどうか判断する。}
     * {@.en Determine whether the given name component is NamingContext}
     *
     * <p>
     * {@.ja NameComponentで指定した要素がネーミングコンテキストか判別する}
     * {@.en Determine whether the specified element is NameComponent}
     *
     * @param name 
     *   {@.ja 判別対象NameComponent}
     *   {@.en The target NameComponent for determination}
     *
     * @return 
     *   {@.ja 判別結果(ネーミングコンテキスト:true、それ以外:false)}
     *   {@.en Determination result (NamingContext:true, Else:false)}
     *
     * @exception NotFound 
     *   {@.ja 対象オブジェクトが存在しない。}
     *   {@.en The object doesn't exist. }
     * @exception CannotProceed 
     *   {@.ja 何らかの理由で処理を継続できない。}
     *   {@.en Processing cannot be continued for some reasons.}
     * @exception InvalidName 
     *   {@.ja 引数 name の名前が不正。}
     *   {@.en The argument 'name' is invalid.}
     */
    public boolean isNamingContext(final NameComponent[] name) 
        throws NotFound, CannotProceed, InvalidName {
        return isNamingContext(resolve(name));
    }

    /**
     * {@.ja 与えられた名前がネーミングコンテキストかどうか判断する。}
     * {@.en Determine whether the given string name is NamingContext}
     *
     * <p>
     * {@.ja 文字列で指定した要素がネーミングコンテキストか判別する}
     * {@.en Determine whether the element specified by string name is 
     * NamingContext}
     *
     * @param string_name 
     *   {@.ja 判別対象文字列}
     *   {@.en The string representation for determination}
     *
     * @return 
     *   {@.ja 判別結果(ネーミングコンテキスト:true、それ以外:false)}
     *   {@.en Determination result (NamingContext:true, Else:false)}
     *
     * @exception NotFound 
     *   {@.ja 対象オブジェクトが存在しない。}
     *   {@.en The object doesn't exist. }
     * @exception CannotProceed 
     *   {@.ja 何らかの理由で処理を継続できない。}
     *   {@.en Processing cannot be continued for some reasons.}
     * @exception InvalidName 
     *   {@.ja 引数 name の名前が不正。}
     *   {@.en The argument 'name' is invalid.}
     */
    public boolean isNamingContext(final String string_name) 
        throws NotFound, CannotProceed, InvalidName {
      return isNamingContext(resolve(string_name));
    }

    /**
     * {@.ja ネームコンポーネントの部分を返す。}
     * {@.en Get subset of given name component}
     *
     * <p>
     * {@.ja 指定された範囲のネームコンポーネントを取得する。
     * 最後の要素を除いたネームコンポーネントを返す。}
     * {@.en Get the name component in specified range.
     * Return the name component except the last element.}
     *
     * @param name 
     *   {@.ja 検索対象NameComponent}
     *   {@.en The target NameComponent for search}
     * @param begin 
     *   {@.ja 取得範囲開始位置}
     *   {@.en The beginning position for getting range}
     *
     * @return 
     *   {@.ja NameComponent 取得結果}
     *   {@.en NameComponent Getting result}
     *
     */
    public NameComponent[] subName(NameComponent[] name, long begin) {
        return subName(name, begin, -1);
    }
    /**
     * {@.ja ネームコンポーネントの部分を返す。}
     * {@.en Get subset of given name component}
     *
     * <p>
     * {@.ja 指定された範囲のネームコンポーネントを取得する。}
     * {@.en Get the name component in specified range.}
     *
     * @param name 
     *   {@.ja 検索対象NameComponent}
     *   {@.en The target NameComponent for search}
     * @param begin 
     *   {@.ja 取得範囲開始位置}
     *   {@.en The beginning position for getting range}
     * @param end 
     *   {@.ja 取得範囲終了位置}
     *   {@.en The end position for getting range}
     *
     * @return 
     *   {@.ja NameComponent 取得結果}
     *   {@.en NameComponent Getting result}
     *
     */
    public NameComponent[] subName(NameComponent[] name, long begin, long end) {
        
        if( end<0 ) end = name.length -1;
        
        NameComponent[] sub_name;
        int sub_len = (int)(end - (begin-1));
        if( sub_len>0 ) {
            sub_name = new NameComponent[sub_len];
        } else {
            sub_name = new NameComponent[0];
            return sub_name;
        }
    
        for (int intIdx=0; intIdx<sub_len; ++intIdx) {
            sub_name[intIdx] = name[(int)(begin + intIdx)];
        }
        return sub_name;
    }
        
    /**
     * {@.ja ネームコンポーネントの文字列表現を取得する。}
     * {@.en Get string representation of name component}
     *
     * <p>
     * {@.ja 指定した範囲のネームコンポーネントの文字列表現を取得する。
     * 文字列表現は、NameComponentの構成が{Nc[0], Nc[1], Nc[2]...}の場合、
     *   Nc[0]id.Nc[0].kind/Nc[1]id.Nc[1].kind/Nc[2].id/Nc[2].kind...
     * という形式で取得できる。
     * 取得した文字列の長さが指定した長さ以上の場合は、
     * 指定した長さで切り捨てられる。}
     * {@.en Get string representation of the name component in specified range.
     * In string representation, if NameComponent consists of 
     * {Nc[0],Nc[1],Nc[2]...}, the format of 
     * Nc[0]id.Nc[0].kind/Nc[1]id.Nc[1].kind/Nc[2].id/Nc[2].kind...
     * will be got.
     * It is rounded by the specified length when the length of the got
     * string is over the specified length. }
     *
     * @param name 
     *   {@.ja 取得対象NameComponent}
     *   {@.en The getting target NameComponent}
     * @param string_name 
     *   {@.ja 取得結果文字列}
     *   {@.en The string of getting result}
     * @param slen 
     *   {@.ja 取得対象文字列最大値}
     *   {@.en The maximum length value of getting string}
     */
    protected void nameToString(final NameComponent[] name, StringHolder string_name, long slen) {
        StringBuffer s = new StringBuffer(string_name.value);
        for( int intIdx=0;intIdx< name.length;++intIdx) {
            // Copy id to string_name
            for(int intIdx2=0;intIdx2<name[intIdx].id.length();++intIdx2) {
                final char id = name[intIdx].id.charAt(intIdx2);
                if( id=='/' || id=='.' || id=='\\' ) s.append('\\');
                s.append(id);
            }
            // '.' if there is a kind, or no id
            if( name[intIdx].id.equals("") ||  !name[intIdx].kind.equals("")) {
                s.append('.');
            }
            // Copy kind to string_name
            for(int intIdx2=0;intIdx2<name[intIdx].kind.length();++intIdx2) {
                final char kind = name[intIdx].kind.charAt(intIdx2);
                if( kind=='/' || kind=='.' || kind =='\\') s.append('\\');
                s.append(kind);
            } // The end of string_name will be overwritten by '\0'
            s.append('/');
        }
        string_name.value = s.toString();
    }
    
    /**
     * {@.ja ネームコンポーネントの文字列表現時の文字長を取得する。}
     * {@.en Get string length of the name component's string representation}
     *
     * <p>
     * {@.ja 指定したネームコンポーネントを文字列で表現した場合の長さを
     * 取得する。
     * 文字列表現は、NameComponentの構成が{Nc[0],Nc[1],Nc[2]...}の場合、
     *   Nc[0]id.Nc[0].kind/Nc[1]id.Nc[1].kind/Nc[2].id/Nc[2].kind...
     * という形式で取得できる。}
     * {@.en Get string length of the name component's string representation.
     * In string representation, if NameComponent consists of 
     * {Nc[0],Nc[1],Nc[2]･･･}, the format of 
     * Nc[0]id.Nc[0].kind/Nc[1]id.Nc[1].kind/Nc[2].id/Nc[2].kind･･･
     * will be got.}
     *
     * @param name 
     *   {@.ja 取得対象NameComponent}
     *   {@.en The getting target NameComponent}
     *
     * @return 
     *   {@.ja 指定したネームコンポーネントの文字列長さ}
     *   {@.en The string length value of specified component}
     *
     */
    protected int getNameLength(final NameComponent[] name) {
        int slen = 0;
        
        for( int intIdx=0;intIdx<name.length;++intIdx) {
            // Count string length of id(s)
            for(int intIdx2=0;intIdx2<name[intIdx].id.length();++intIdx2 ) {
                // Escape character '/', '.', '\' will convert to "\/", "\.", "\\".
                char id = name[intIdx].id.charAt(intIdx2);
                if( id=='/' || id=='.' || id=='\\') slen++;
                slen++;
            }
            // If kind exists, space for '.' is counted
            if( name[intIdx].id.equals("") || !name[intIdx].kind.equals("")) {
                slen++;
            }
            // Count string length of kind(s)
            for( int intIdx2=0;intIdx2<name[intIdx].kind.length();intIdx2++ ) {
                char kind = name[intIdx].kind.charAt(intIdx2);
                if( kind=='/' || kind=='.' || kind=='\\') slen++;
                slen++;
            }
            // Space for '/' or '\0'
            slen++;
        }
        return slen;
    }

    /**
     * {@.ja ORB。}
     * {@.en ORB}
     */
    protected ORB m_varORB;
    /**
     * {@.ja ネームサーバ名称。}
     * {@.en Name of the name server}
     */
    protected String m_nameServer = new String();
    /**
     * {@.ja 指定したネームサーバのルートコンテキスト。}
     * {@.en The root context of specified name server}
     */
    protected NamingContextExt m_rootContext;
    /**
     * コンテキストの深さ
     */
    private int m_blLength;


}

