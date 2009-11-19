package jp.go.aist.rtm.RTC;

import jp.go.aist.rtm.RTC.util.StringHolder;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
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

//<+zxc
import jp.go.aist.rtm.RTC.util.StringUtil;
import jp.go.aist.rtm.RTC.log.Logbuf;
import java.util.Properties;
//+>

/**
 *
 * <p>CORBA Naming Service ヘルパークラスです。
 *
 * このクラスは、NamingContext に対するラッパークラスです。
 * NamingContext が持つオペレーションとほぼ同じ機能の
 * オペレーションを提供するとともに、ネームコンポーネント NameComponent
 * の代わりに文字列による名前表現を受け付けるオペレーションも提供します。
 *
 * オブジェクトは生成時、あるいは生成直後に CORBA ネームサーバに接続し
 * 以後、このネームサーバのルートコンテキストに対して種々のオペレーション
 * を処理します。
 * 深い階層のネーミングコンテキストの作成やオブジェクトのバインドにおいて、
 * 途中のコンテキストが存在しない場合でも、強制的にコンテキストをバインド
 * し目的のコンテキストやオブジェクトのバインドを行うこともできます。</p>
 */
public class CorbaNaming {

    /**
     * <p>コンストラクタです。</p>
     *
     * @param orb　ORB
     */
    public CorbaNaming(ORB orb){
        m_varORB = orb;
        m_nameServer = "";
        m_rootContext = null;
        m_blLength = 100;
//<+zxc
        Manager manager = Manager.instance();
        rtcout = new Logbuf("Manager.CorbaNaming");
        // rtcout.setLevel(manager.getConfig().getProperty("logger.log_level"));
        // rtcout.setDateFormat(manager.getConfig().getProperty("logger.date_format"));
        // rtcout.setLogLock(StringUtil.toBool(manager.getConfig().getProperty("logger.stream_lock"),
        //           "enable", "disable", false));
//+>
    }

    /**
     * <p>コンストラクタです。</p>
     *
     * @param orb　ORB
     * @param name_server　ネーミングサービス名
     * 
     * @exception Exception
     */
    public CorbaNaming(ORB orb, final String name_server) throws Exception {
        m_varORB = orb;
        m_nameServer = name_server;
        m_rootContext = null;
        m_blLength = 100;
        
        Object obj;
//        m_nameServer = "corbaloc::" + m_nameServer + "/NameService";
        m_nameServer = "corbaloc:iiop:1.2@" + m_nameServer + "/NameService";

        obj = m_varORB.string_to_object(m_nameServer);
        m_rootContext =  NamingContextExtHelper.narrow(obj);
        if (m_rootContext==null) throw new Exception("bad_alloc()");
//<+zxc
        Manager manager = Manager.instance();
        rtcout = new Logbuf("Manager.CorbaNaming");
        // rtcout.setLevel(manager.getConfig().getProperty("logger.log_level"));
        // rtcout.setDateFormat(manager.getConfig().getProperty("logger.date_format"));
        // rtcout.setLogLock(StringUtil.toBool(manager.getConfig().getProperty("logger.stream_lock"),
        //           "enable", "disable", false));
rtcout.println(rtcout.TRACE, "CorbaNaming.CorbaNaming(" +name_server +")");//zxc
//+>
    }
    
    /**
     * <p>ネーミングサービスを初期化します。</p>
     *
     * @param name_server　ネーミングサービス名称
     * 
     * @exception Exception
     */
    public void init (final String name_server) throws Exception {
rtcout.println(rtcout.TRACE, "in  CorbaNaming.init(" +name_server +")");//zxc
        m_nameServer = name_server;
        m_nameServer = "corbaloc:iiop:1.2@" + m_nameServer + "/NameService";
        Object obj = m_varORB.string_to_object(m_nameServer);
        m_rootContext = NamingContextExtHelper.narrow(obj);
        if (m_rootContext==null) throw new Exception("bad_alloc()");
rtcout.println(rtcout.TRACE, "out CorbaNaming.init(" +name_server +")");//zxc
    }

    /**
     * <p>Object を bind します。
     *
     * 与えられたネームサーバのルートコンテキストに対して
     * Objectを強制的にbindします。</p>
     *
     * @param name オブジェクトに付ける名前の NameComponent
     * @param obj 関連付けられる Object
     *
     * @exception NotFound 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。
     * @exception CannotProceed 何らかの理由で処理を継続できない。
     * @exception InvalidName 引数 name の名前が不正。
     * @exception AlreadyBound name <c_n> の Object がすでにバインドされている。
     */
    public void bind(final NameComponent[] name, Object obj)
        throws NotFound, CannotProceed, InvalidName, AlreadyBound {
        this.bind(name, obj, true);
    }
    /**
     * <p>Object を bind します。
     *
     * NamingContextOperations.bind() とほぼ同等の働きをするが、常に与えられたネームサーバの
     * ルートコンテキストに対してbind()が呼び出される点が異なります。
     *
     * Name <name> と Object <obj> を当該 NamingContext 上にバインドします。
     * c_n が n 番目の NameComponent をあらわすとすると、
     * name が n 個の NameComponent から成るとき、以下のように扱われます。
     *
     * cxt->bind(<c_1, c_2, ... c_n>, obj) は以下の操作と同等です。
     * cxt->resolve(<c_1, ... c_(n-1)>)->bind(<c_n>, obj)
     *
     * すなわち、1番目からn-1番目のコンテキストを解決し、n-1番目のコンテキスト
     * 上に name <n> として　obj を bind します。
     * 名前解決に参加する <c_1, ... c_(n-1)> の NemingContext は、
     * bindContext() や rebindContext() で既にバインド済みでなければなりません。
     * もし <c_1, ... c_(n-1)> の NamingContext が存在しない場合には、
     * NotFound 例外が発生します。
     *
     * ただし、強制バインドフラグ force が true の時は、<c_1, ... c_(n-1)>
     * が存在しない場合にも、再帰的にコンテキストをバインドしながら、
     * 最終的に obj を名前 name <c_n> にバインドします。
     *
     * いずれの場合でも、n-1番目のコンテキスト上に name<n> のオブジェクト
     * (Object あるいは コンテキスト) がバインドされていれば
     * AlreadyBound 例外が発生します。
     *
     * @param name オブジェクトに付ける名前の NameComponent
     * @param obj 関連付けられる Object
     * @param force trueの場合、途中のコンテキストを強制的にバインドする
     *
     * @exception NotFound 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。
     * @exception CannotProceed 何らかの理由で処理を継続できない。
     * @exception InvalidName 引数 name の名前が不正。
     * @exception AlreadyBound name <c_n> の Object がすでにバインドされている。
     *
     */
    public void bind(final NameComponent[] name, Object obj, final boolean force)
        throws NotFound, CannotProceed, InvalidName, AlreadyBound {
        try{
            if( isNamingContext(obj) ) {
                m_rootContext.bind_context(name, NamingContextExtHelper.narrow(obj));
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
     * <p>文字列表現の Object を強制的に bind します。
     *
     * Object を bind する際に与える名前が文字列表現であること以外は、bind()
     * と同じです。bind(toName(string_name), obj) と等価。</p>
     *
     * @param string_name オブジェクトに付ける名前の文字列表現
     * @param obj 関連付けられるオブジェクト
     *
     * @exception NotFound 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。
     * @exception CannotProceed 何らかの理由で処理を継続できない。
     * @exception InvalidName 引数 string_name の名前が不正。
     * @exception AlreadyBound string_name の Object がすでにバインドされている。
     *
     */
    public void bindByString(final String string_name, Object obj)
        throws NotFound, CannotProceed, InvalidName, AlreadyBound {
        this.bindByString(string_name, obj, true);
    }
    /**
    * <p>文字列表現の Object を bind します。
    *
    * Object を bind する際に与える名前が文字列表現であること以外は、bind()
    * と同じです。bind(toName(string_name), obj) と等価。</p>
    *
    * @param string_name オブジェクトに付ける名前の文字列表現
    * @param obj 関連付けられるオブジェクト
    * @param force trueの場合、途中のコンテキストを強制的にバインドする
    *
    * @exception NotFound 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。
    * @exception CannotProceed 何らかの理由で処理を継続できない。
    * @exception InvalidName 引数 string_name の名前が不正。
    * @exception AlreadyBound string_name の Object がすでにバインドされている。
    *
    */
    public void bindByString(final String string_name, Object obj, final boolean force)
        throws NotFound, CannotProceed, InvalidName, AlreadyBound {
        this.bind(toName(string_name), obj, force);
    }

    /**
     * <p>途中のコンテキストを bind しながら Object を bind します。
     *
     * context で与えられた NamingContext に対して、name で指定された
     * ネームコンポーネント <c_1, ... c_(n-1)> を NamingContext として
     * 解決しながら、名前 <c_n> に対して obj を bind します。
     * もし、<c_1, ... c_(n-1)> に対応する NamingContext がない場合には
     * 新たな NamingContext をバインドします。
     *
     * 最終的に <c_1, c_2, ..., c_(n-1)> に対応する NamingContext が生成
     * または解決された上で、CosNaming::bind(<c_n>, object) が呼び出されます。
     * このとき、すでにバインディングが存在すれば AlreadyBound例外が発生します。
     *
     * 途中のコンテキストを解決する過程で、解決しようとするコンテキストと
     * 同じ名前の NamingContext ではない Binding が存在する場合、
     * CannotProceed 例外が発生し処理を中止します。</p>
     *
     * @param context bind を開始する　NamingContext
     * @param name オブジェクトに付ける名前のネームコンポーネント
     * @param obj 関連付けられるオブジェクト
     *
     * @exception CannotProceed <c_1, ..., c_(n-1)> に対応する NamingContext 
     *            のうちひとつが、すでに NamingContext 以外の object にバインド
     *            されており、処理を継続できない。
     * @exception InvalidName 名前 name が不正
     * @exception AlreadyBound name <c_n> にすでに何らかの object がバインド
     *            されている。
     */
    public void bindRecursive(NamingContext context,final NameComponent[] name, Object obj)
        throws CannotProceed, InvalidName, AlreadyBound, NotFound {
        int len = name.length;
        NamingContext cxt = (NamingContext)context._duplicate();
    
        for(int intIdx=0; intIdx<len; ++intIdx) {
            if( intIdx==(len-1)) {
                // this operation may throw AlreadyBound, 
                if(isNamingContext(obj)) {
                    cxt.bind_context(subName(name, intIdx, intIdx), NamingContextExtHelper.narrow(obj));
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
     * <p>Object を強制的に rebind します。</p>
     *
     * @param name オブジェクトに付ける名前の NameComponent
     * @param obj 関連付けられるオブジェクト
     */
    public void rebind(final NameComponent[] name, Object obj) 
        throws NotFound, CannotProceed, InvalidName {
        rebind(name, obj, true);
    }
    /**
     * <p>Object を rebind します。
     *
     * name で指定された Binding がすでに存在する場合を除いて bind() と同じ
     * です。バインディングがすでに存在する場合には、新しいバインディングに
     * 置き換えられます。</p>
     *
     * @param name オブジェクトに付ける名前の NameComponent
     * @param obj 関連付けられるオブジェクト
     * @param force trueの場合、途中のコンテキストを強制的にバインドする
     */
    public void rebind(final NameComponent[] name, Object obj, final boolean force)
        throws NotFound, CannotProceed, InvalidName {
rtcout.println(rtcout.TRACE, "in  CorbaNaming.rebind(" +name +"," +java.lang.Boolean.toString(force) +")");//zxc


        try {
            if( isNamingContext(obj) ) {
rtcout.println(rtcout.TRACE, "    isNamingContext is true.");//zxc
                m_rootContext.rebind(name, NamingContextExtHelper.narrow(obj));
            } else {
rtcout.println(rtcout.TRACE, "    isNamingContext is false.");//zxc
                m_rootContext.rebind(name, obj);
            }
        } catch(NotFound ex) {
rtcout.println(rtcout.TRACE, "    !!NotFound ex");//zxc
            if( force ) {
                rebindRecursive(m_rootContext, name, obj);
            } else {
                throw ex;
            }
        } catch (CannotProceed ex) {
rtcout.println(rtcout.TRACE, "    !!CannotProceed ex");//zxc
            if( force ) {
                rebindRecursive(ex.cxt, ex.rest_of_name, obj);
            } else {
                throw ex;
            }
        }
rtcout.println(rtcout.TRACE, "out  CorbaNaming.rebind()");//zxc
    }

    /**
     * <p>Object を強制的に rebind します。</p>
     *
     * @param string_name オブジェクトに付ける名前の文字列表現
     * @param obj 関連付けられるオブジェクト
     *
     * @exception NotFound 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。
     * @exception CannotProceed 何らかの理由で処理を継続できない。
     * @exception InvalidName 引数 string_name の名前が不正。
     */
    public void rebindByString(final String string_name, Object obj)
        throws NotFound, CannotProceed, InvalidName {
            rebindByString(string_name, obj, true);
    }
    /**
     * <p>文字列表現の Object を rebind します。
     *
     * Object を rebind する際に与える名前が文字列表現であること以外は rebind()
     * と同じです。rebind(toName(string_name), obj) と等価。</p>
     *
     * @param string_name オブジェクトに付ける名前の文字列表現
     * @param obj 関連付けられるオブジェクト
     * @param force trueの場合、途中のコンテキストを強制的にバインドする
     *
     * @exception NotFound 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。
     * @exception CannotProceed 何らかの理由で処理を継続できない。
     * @exception InvalidName 引数 string_name の名前が不正。
     */
    public void rebindByString(final String string_name, Object obj, final boolean force)
        throws NotFound, CannotProceed, InvalidName {
rtcout.println(rtcout.TRACE, "in  CorbaNaming.rebindByString(" + string_name + ")");//zxc
            rebind(toName(string_name), obj, force);
rtcout.println(rtcout.TRACE, "out CorbaNaming.rebindByString(" + string_name + ")");//zxc
    }

    /**
     * <p>途中のコンテキストを bind しながら Object を rebind します。
     *
     * name <c_n> で指定された NamingContext もしくは Object がすでに存在する
     * 場合を除いて bindRecursive() と同じです。
     *
     * name <c_n> で指定されたバインディングがすでに存在する場合には、
     * 新しいバインディングに置き換えられます。</p>
     *
     * @param context bind を開始する　NamingContext
     * @param name オブジェクトに付ける名前の文字列表現
     * @param obj 関連付けられるオブジェクト
     *
     * @exception CannotProceed 途中のコンテキストが解決できない。
     * @exception InvalidName 与えられた name が不正。
     * @exception NotFound 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。
     */
    public void rebindRecursive(NamingContext context, final NameComponent[] name, Object obj)
        throws CannotProceed, InvalidName, NotFound {
rtcout.println(rtcout.TRACE, "in  CorbaNamaing.rebindRecursive");//zxc
        int len = name.length;
        NamingContext cxt = (NamingContext)context._duplicate();
        
rtcout.println(rtcout.TRACE, "    name.length:" + len);//zxc
        for( int intIdx=0;intIdx<len;intIdx++ ) {
rtcout.println(rtcout.TRACE, "    name[" +intIdx +"].id:" +name[intIdx].id);//zxc
            if( intIdx==(len-1) ) {
                if( isNamingContext(obj) ) {
rtcout.println(rtcout.TRACE, "    isNamingContext returned true.");//zxc
                    cxt.rebind_context(subName(name, intIdx, intIdx), NamingContextExtHelper.narrow(obj));
                } else {
rtcout.println(rtcout.TRACE, "    isNamingContext returned false.");//zxc
                    cxt.rebind(subName(name, intIdx, intIdx), obj);
rtcout.println(rtcout.TRACE, "    rebind OK");//zxc
                }
rtcout.println(rtcout.TRACE, "out CorbaNamaing.rebindRecursive");//zxc
                return;
            }
            // If the context is not a NamingContext, CannotProceed is thrown
            if( isNamingContext(cxt)) {
                try {
rtcout.println(rtcout.TRACE, "    call bind_new_context");//zxc
                    cxt = cxt.bind_new_context(subName(name, intIdx, intIdx));
rtcout.println(rtcout.TRACE, "    bind_new_context OK");//zxc
                } catch (AlreadyBound ex) {
                    cxt = NamingContextExtHelper.narrow(cxt.resolve(subName(name, intIdx, intIdx)));
                }
            } else {
                throw new CannotProceed(cxt, subName(name, intIdx));
            }
        }
rtcout.println(rtcout.TRACE, "out CorbaNamaing.rebindRecursive");//zxc
        return;
    }

    /**
     * <p>NamingContext を強制的に bind します。</p>
     *
     * @param name オブジェクトに付ける名前のネームコンポーネント
     * @param name_cxt 関連付けられる NamingContext
     *
     * @exception NotFound 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。
     * @exception CannotProceed 何らかの理由で処理を継続できない。
     * @exception InvalidName 引数 name の名前が不正。
     * @exception AlreadyBound name <n> の Object がすでにバインドされている。
     */
    public void bindContext(final NameComponent[] name, NamingContext name_cxt)
        throws NotFound, CannotProceed, InvalidName, AlreadyBound {
        this.bindContext(name, name_cxt, true);
    }    
    /**
     * <p>NamingContext を bind します。
     *
     * bind されるオブジェクトが NamingContext であることを除いて bind() 
     * と同じです。
     *
     * @param name オブジェクトに付ける名前のネームコンポーネント
     * @param name_cxt 関連付けられる NamingContext
     * @param force trueの場合、途中のコンテキストを強制的にバインドする
     *
     * @exception NotFound 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。
     * @exception CannotProceed 何らかの理由で処理を継続できない。
     * @exception InvalidName 引数 name の名前が不正。
     * @exception AlreadyBound name <n> の Object がすでにバインドされている。
     */
    public void bindContext(final NameComponent[] name, NamingContext name_cxt, final boolean force)
        throws NotFound, CannotProceed, InvalidName, AlreadyBound {
        this.bind(name, name_cxt, force);
    }
    
    /**
    * <p>文字列表現の NamingContext を強制的に bind します。</p>
     *
     * @param string_name オブジェクトに付ける名前の文字列表現
     * @param name_cxt 関連付けられる NamingContext
     *
     * @exception NotFound 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。
     * @exception CannotProceed 何らかの理由で処理を継続できない。
     * @exception InvalidName 引数 string_name の名前が不正。
     * @exception AlreadyBound string_name の Object がすでにバインドされている。
     */
    public void bindContext(final String string_name, NamingContext name_cxt)
        throws NotFound, CannotProceed, InvalidName, AlreadyBound {
        this.bindContext(string_name, name_cxt, true);
    }
    /**
     * <p>文字列表現の NamingContext を bind します。
     *
     * bind されるオブジェクトが NamingContext であることを除いて bind() 
     * と同じです。
     *
     * @param string_name オブジェクトに付ける名前の文字列表現
     * @param name_cxt 関連付けられる NamingContext
     * @param force trueの場合、途中のコンテキストを強制的にバインドする
     *
     * @exception NotFound 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。
     * @exception CannotProceed 何らかの理由で処理を継続できない。
     * @exception InvalidName 引数 string_name の名前が不正。
     * @exception AlreadyBound string_name の Object がすでにバインドされている。
     */
    public void bindContext(final String string_name, NamingContext name_cxt, final boolean force)
        throws NotFound, CannotProceed, InvalidName, AlreadyBound {
        this.bindContext(toName(string_name), name_cxt, force);        
    }

    /**
     * <p>NamingContext を 再帰的に bind します。
     *
     * bind されるオブジェクトが NamingContext であることを除いて
     * bindRecursive() と同じです。
     *
     * @param context bind を開始する　NamingContext
     * @param name オブジェクトに付ける名前のネームコンポーネント
     * @param name_cxt 関連付けられる NamingContext
     *
     * @exception NotFound 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。
     * @exception CannotProceed 何らかの理由で処理を継続できない。
     * @exception InvalidName 引数 name の名前が不正。
     * @exception AlreadyBound name <n> の Object がすでにバインドされている。
     */
    public void bindContextRecursive(NamingContext context, final NameComponent[] name, NamingContext name_cxt) 
            throws CannotProceed, InvalidName, AlreadyBound, NotFound{
        this.bindRecursive(context, name, name_cxt);
        return;
        
    }

    /**
     * <p>NamingContext を強制的に rebind します。
     *
     * @param name オブジェクトに付ける名前のネームコンポーネント
     * @param name_cxt 関連付けられる NamingContext
     *
     * @exception NotFound 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。
     * @exception CannotProceed 何らかの理由で処理を継続できない。
     * @exception InvalidName 引数 name の名前が不正。
     */
    public void rebindContext(final NameComponent[] name, NamingContext name_cxt)
        throws NotFound, CannotProceed, InvalidName {
        this.rebindContext(name, name_cxt, true);
    }
    /**
    * <p>NamingContext を rebind します。
     *
     * name で指定されたコンテキストがすでに存在する場合を除いて bindContext() 
     * と同じです。
     * バインディングがすでに存在する場合には、新しいバインディングに
     * 置き換えられます。
     *
     * @param name オブジェクトに付ける名前のネームコンポーネント
     * @param name_cxt 関連付けられる NamingContext
     * @param force trueの場合、途中のコンテキストを強制的にバインドする
     *
     * @exception NotFound 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。
     * @exception CannotProceed 何らかの理由で処理を継続できない。
     * @exception InvalidName 引数 name の名前が不正。
     */
    public void rebindContext(final NameComponent[] name, NamingContext name_cxt, final boolean force)
        throws NotFound, CannotProceed, InvalidName {
        this.rebind(name, name_cxt, force);
        return;
        
    }

    /**
     * <p>文字列表現のNamingContext を強制的に rebind します。</p>
     *
     * @param string_name オブジェクトに付ける名前の文字列表現
     * @param name_cxt 関連付けられる NamingContext
     *
     * @exception NotFound 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。
     * @exception CannotProceed 何らかの理由で処理を継続できない。
     * @exception InvalidName 引数 string_name の名前が不正。
     */
    public void rebindContext(final String string_name, NamingContext name_cxt)
        throws NotFound, CannotProceed, InvalidName {
        this.rebindContext(string_name, name_cxt, true);
    }
    /**
     * <p>文字列表現のNamingContext を rebind します。
     *
     * name で指定されたコンテキストがすでに存在する場合を除いて bindContext() 
     * と同じです。
     * バインディングがすでに存在する場合には、新しいバインディングに
     * 置き換えられます。
     *
     * @param string_name オブジェクトに付ける名前の文字列表現
     * @param name_cxt 関連付けられる NamingContext
     * @param force trueの場合、途中のコンテキストを強制的にバインドする
     *
     * @exception NotFound 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。
     * @exception CannotProceed 何らかの理由で処理を継続できない。
     * @exception InvalidName 引数 string_name の名前が不正。
     */
    public void rebindContext(final String string_name, NamingContext name_cxt, final boolean force)
        throws NotFound, CannotProceed, InvalidName {
        this.rebindContext(toName(string_name), name_cxt, force);
    }

    /**
     * <p>途中のコンテキストを再帰的に rebind し NamingContext を rebind します。</p>
     * 
     * @param context bind を開始する　NamingContext
     * @param name オブジェクトに付ける名前の文字列表現
     * @param name_cxt 関連付けられる NamingContext
     *
     * @exception NotFound 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。
     * @exception CannotProceed 何らかの理由で処理を継続できない。
     * @exception InvalidName 引数 name の名前が不正。
     */
    public void rebindContextRecursive(NamingContext context, final NameComponent[] name, NamingContext name_cxt) 
        throws CannotProceed, InvalidName, NotFound {
        this.rebindRecursive(context, name, name_cxt);
      return;
    }

    /**
     * <p>Object を name から解決します。
     *
     * name に bind されているオブジェクト参照を返します。
     * ネームコンポーネント <c_1, c_2, ... c_n> は再帰的に解決されます。
     * 
     * NamingContextOperations.resolve() とほぼ同等の働きをするが、常に与えられた
     * ネームサーバのルートコンテキストに対して resolve() が呼び出される点が
     * 異なります。</p>
     *
     * @param name 解決すべきオブジェクトの名前のネームコンポーネント
     * @return 解決されたオブジェクト参照
     *
     * @exception NotFound Objectが存在しない。
     * @exception CannotProceed 何らかの理由で処理を継続できない。
     * @exception InvalidName 引数 name の名前が不正。
     *
     */
    public Object resolve(final NameComponent[] name)
        throws NotFound, CannotProceed, InvalidName {
        return m_rootContext.resolve(name);
    }

    /**
     * <p>文字列表現の Object を name から解決します。
     *
     * name に bind されているオブジェクト参照を返します。
     * ネームコンポーネント <c_1, c_2, ... c_n> は再帰的に解決されます。
     * 
     * NamingContextOperations.resolve() とほぼ同等の働きをするが、常に与えられた
     * ネームサーバのルートコンテキストに対して resolve() が呼び出される点が
     * 異なります。</p>
     *
     * @param string_name 解決すべきオブジェクトの名前の文字列表現
     * @return 解決されたオブジェクト参照
     *
     * @exception NotFound Objectが存在しない。
     * @exception CannotProceed 何らかの理由で処理を継続できない。
     * @exception InvalidName 引数 string_name の名前が不正。
     */
    public Object resolve(final String string_name)
        throws NotFound, CannotProceed, InvalidName {
        return resolve(toName(string_name));
    }

    /**
     * <p>指定された名前のオブジェクトの bind を解除します。
     *
     * name に bind されているオブジェクト参照を返します。
     * ネームコンポーネント <c_1, c_2, ... c_n> は再帰的に解決されます。
     * 
     * NamingContextOperations.unbind() とほぼ同等の働きをしますが、常に与えられた
     * ネームサーバのルートコンテキストに対して unbind() が呼び出される点が
     * 異なります。</p>
     *
     * @param name 解決すべきオブジェクトの名前のネームコンポーネント
     *
     * @exception NotFound 解除対象のオブジェクトが存在しない。
     * @exception CannotProceed 何らかの理由で処理を継続できない。
     * @exception InvalidName 引数 name の名前が不正。
     */
    public void unbind(final NameComponent[] name) throws NotFound, CannotProceed, InvalidName {
        m_rootContext.unbind(name);
    }

    /**
     * <p>文字列表現で指定された名前のオブジェクトの bind を解除します。
     *
     * name に bind されているオブジェクト参照を返します。
     * ネームコンポーネント <c_1, c_2, ... c_n> は再帰的に解決されます。
     * 
     * NamingContextOperations.unbind() とほぼ同等の働きをするが、常に与えられた
     * ネームサーバのルートコンテキストに対して unbind() が呼び出される点が
     * 異なります。</p>
     *
     * @param string_name 解決すべきオブジェクトの名前の文字列表現
     *
     * @exception NotFound 解除対象のオブジェクトが存在しない。
     * @exception CannotProceed 何らかの理由で処理を継続できない。
     * @exception InvalidName 引数 name の名前が不正。
     */
    public void unbind(final String string_name) throws NotFound, CannotProceed, InvalidName {
        this.unbind(this.toName(string_name));
    }

    /**
     * <p>新しいコンテキストを生成します。
     *
     * 与えられたネームサーバ上で生成された NamingContext を返します。
     * 返された NamingContext は bind されていません。</p>
     * 
     * @return 生成された新しい NamingContext
     */
    public NamingContext newContext() {
      return m_rootContext.new_context();
    }

    /**
     * <p>新しいコンテキストを強制的に bind します。</p>
     *
     * @param name NamingContextに付ける名前のネームコンポーネント
     * @return 生成された新しい NamingContext
     *
     * @exception NotFound 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。
     * @exception CannotProceed 何らかの理由で処理を継続できない。
     * @exception InvalidName 引数 name の名前が不正。
     * @exception AlreadyBound name <n> の Object がすでにバインドされている。
     */
    public NamingContext bindNewContext(final NameComponent[] name)
        throws NotFound, CannotProceed, InvalidName, AlreadyBound {
        return this.bindNewContext(name, true);
    }
    /**
     * <p>新しいコンテキストを bind します。
     *
     * 与えられた name に対して新しいコンテキストをバインドします。
     * 生成された　NamingContext はネームサーバ上で生成されたものです。</p>
     * 
     * @param name NamingContextに付ける名前のネームコンポーネント
     * @param force trueの場合、途中のコンテキストを強制的にバインドする
     * @return 生成された新しい NamingContext
     *
     * @exception NotFound 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。
     * @exception CannotProceed 何らかの理由で処理を継続できない。
     * @exception InvalidName 引数 name の名前が不正。
     * @exception AlreadyBound name <n> の Object がすでにバインドされている。
     */
    public NamingContext bindNewContext(final NameComponent[] name, boolean force)
        throws NotFound, CannotProceed, InvalidName, AlreadyBound {
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
     * <p>新しいコンテキストを強制的に bind します。
     *
     * 与えられた文字列表現の name に対して新しいコンテキストをバインドします。
     * 生成された　NamingContext はネームサーバ上で生成されたものです。</p>
     * 
     * @param string_name NamingContextに付ける名前の文字列表現
     * @return 生成された新しい NamingContext
     *
     * @exception NotFound 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。
     * @exception CannotProceed 何らかの理由で処理を継続できない。
     * @exception InvalidName 引数 string_name の名前が不正。
     * @exception AlreadyBound string_name の Object がすでにバインドされている。
     */
    public NamingContext bindNewContext(final String string_name)
        throws NotFound, CannotProceed, InvalidName, AlreadyBound {
        return this.bindNewContext(string_name, true);
    }
    /**
     * <p>新しいコンテキストを bind します。
     *
     * 与えられた文字列表現の name に対して新しいコンテキストをバインドします。
     * 生成された　NamingContext はネームサーバ上で生成されたものです。</p>
     * 
     * @param string_name NamingContextに付ける名前の文字列表現
     * @param force trueの場合、途中のコンテキストを強制的にバインドする
     * @return 生成された新しい NamingContext
     *
     * @exception NotFound 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。
     * @exception CannotProceed 何らかの理由で処理を継続できない。
     * @exception InvalidName 引数 string_name の名前が不正。
     * @exception AlreadyBound string_name の Object がすでにバインドされている。
     */
    public NamingContext bindNewContext(final String string_name, boolean force)
        throws NotFound, CannotProceed, InvalidName, AlreadyBound {
        return this.bindNewContext(toName(string_name));
    }

    /**
     * <p>NamingContext を非アクティブ化します。
     *
     * context で指定された NamingContext を非アクティブ化します。
     * context に他のコンテキストがバインドされている場合は NotEmpty 例外が発生します。</p>
     * 
     * @param context 非アクティブ化する NamingContext
     *
     * @exception NotEmpty 対象コンテキストに他のコンテキストがバインドされている
     */
    public void destroy(NamingContext context) throws NotEmpty {
      context.destroy();
    }

    /**
     * <p>NamingContext を再帰的に下って非アクティブ化します。</p>
     * 
     * @param context 非アクティブ化する NamingContext
     *
     * @exception NotEmpty 対象コンテキストに他のコンテキストがバインドされている
     * @exception NotFound 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。
     * @exception CannotProceed 何らかの理由で処理を継続できない。
     * @exception InvalidName 引数 context の名前が不正。
     */
    public void destroyRecursive(NamingContext context)
        throws NotEmpty, NotFound, CannotProceed, InvalidName {
        BindingListHolder bl = new BindingListHolder();
        BindingIteratorHolder bi = new BindingIteratorHolder();
        boolean cont = true;
        
        context.list(m_blLength, bl, bi);
      
        while( cont ) {
            int len = bl.value.length;
    
            for(int intIdx=0;intIdx<len;++intIdx) {
                if( bl.value[intIdx].binding_type == BindingType.ncontext ) {
                    // If Object is context, destroy recursive.
                    NamingContext next_context = NamingContextExtHelper.narrow(context.resolve(bl.value[intIdx].binding_name));
        
                    // Recursive function call
                    destroyRecursive(next_context); // +++ Recursive call +++
                    context.unbind(bl.value[intIdx].binding_name);
                    next_context.destroy();
                } else if( bl.value[intIdx].binding_type == BindingType.nobject) {
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
     * <p>すべての Binding を削除します。</p>
     *
     * @exception NotEmpty 対象コンテキストに他のコンテキストがバインドされている
     * @exception NotFound 途中の <c_1, c_2, ..., c_(n-1)> が存在しない。
     * @exception CannotProceed 何らかの理由で処理を継続できない。
     * @exception InvalidName 引数 の名前が不正。
     */
    public void clearAll() throws NotEmpty, NotFound, CannotProceed, InvalidName {
        if( m_rootContext!= null ) {
            this.destroyRecursive(m_rootContext);
        }
    }

    /**
     * <p>与えられた NamingContext の Binding を取得します。</p>
     *
     * @param name_cxt 取得対象の NamingContext
     * @param how_many 取得するBindingの最大数
     * @param bl 取得するBindingのリスト
     * @param bi 取得するBindingのイテレータ
     */
    public void list(NamingContext name_cxt, long how_many,
                        BindingListHolder bl, BindingIteratorHolder bi) {
        name_cxt.list((int)how_many, bl, bi);
    }

    /**
     * <p>与えられた NameComponent の文字列表現を返します。</p>
     *
     * @param name 取得対象の NameComponent
     * @exception InvalidName 引数 name の名前が不正。
     */
    public String toString(final NameComponent[] name) throws InvalidName {
        if( name==null || name.equals("")) throw new InvalidName();
    
        int slen = 0;
        slen = getNameLength(name);
    
        StringHolder string_name = new StringHolder();
        string_name.value = new String();
        nameToString(name, string_name, slen);
    
        return string_name.value;
    }

    /**
     * <p>与えられた文字列表現を NameComponent に分解します。</p>
     *
     * @param sname 分解対象文字列
     * @return 分解した NameComponent
     *
     * @exception InvalidName 引数 sname が不正。
     */
    public NameComponent[] toName(final String sname) throws InvalidName {
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
     * <p>与えられた addre と string_name から URL表現を取得します。</p>
     *
     * @param addr 取得対象アドレス
     * @param string_name 取得対象名
     *
     * @exception InvalidAddress 引数 addr が不正。
     * @exception InvalidName 引数 string_name が不正。
     */
    public String toUrl(String addr, String string_name)
        throws InvalidAddress, InvalidName {
      return m_rootContext.to_url(addr, string_name);
    }

    /**
     * <p>与えられた文字列表現を resolve しオブジェクトを返します。</p>
     *
     * @param string_name resolve対象文字列
     *
     * @exception NotFound 対象オブジェクトが存在しない。
     * @exception CannotProceed 何らかの理由で処理を継続できない。
     * @exception InvalidName 引数 string_name の名前が不正。
     * @exception AlreadyBound string_name の Object がすでにバインドされている。
     */
    public Object resolveStr(final String string_name)
        throws NotFound, CannotProceed, InvalidName, AlreadyBound {
      return resolve(string_name);
    }

    /**
     * <p>名前をバインドまたは解決します。</p>
     *
     * @param context bind を開始する　NamingContext
     * @param name オブジェクトに付ける名前
     * @param obj 関連付けられるオブジェクト
     *
     * @exception NotFound 対象オブジェクトが存在しない。
     * @exception CannotProceed 何らかの理由で処理を継続できない。
     * @exception InvalidName 引数 name の名前が不正。
     */
    public NamingContext bindOrResolve(NamingContext context, final NameComponent[] name, Object obj)
        throws NotFound, CannotProceed, InvalidName {
        
        context.rebind(name, obj);
        return context;
    }

    /**
     * <p>名前をバインドまたは解決します。</p>
     *
     * @param context bind を開始する　NamingContext
     * @param name オブジェクトに付ける名前
     * @param new_context 関連付けられるNamingContext
     *
     * @exception NotFound 対象オブジェクトが存在しない。
     * @exception CannotProceed 何らかの理由で処理を継続できない。
     * @exception InvalidName 引数 name の名前が不正。
     */
    public NamingContext bindOrResolveContext(NamingContext context,
            final NameComponent[] name, NamingContext new_context)
            throws NotFound, CannotProceed, InvalidName {
        return bindOrResolve(context, name, new_context);
    }
    
    /**
     * <p>名前をバインドまたは解決します。</p>
     *
     * @param context bind を開始する　NamingContext
     * @param name オブジェクトに付ける名前
     *
     * @exception NotFound 対象オブジェクトが存在しない。
     * @exception CannotProceed 何らかの理由で処理を継続できない。
     * @exception InvalidName 引数 name の名前が不正。
     */
    public NamingContext bindOrResolveContext(NamingContext context, final NameComponent[] name) 
        throws NotFound, CannotProceed, InvalidName{
        NamingContext new_context = context.new_context();
        context.rebind_context(name, new_context);
        return new_context;
    }

    /**
     * <p>ネームサーバの名前を取得します。</p>
     * 
     * @return ネームサーバ名
     */
    public final String getNameServer() {
        return m_nameServer;
    }

    /**
     * <p>ルートコンテキストを取得します。</p>
     *
     * @return ルートコンテキスト
     */
    public NamingContext getRootContext() {
      return m_rootContext;
    }

    /**
     * <p>オブジェクトがネーミングコンテキストか判別します。</p>
     *
     * @param obj 判断対象オブジェクト
     *
     * @return 判断結果
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
     * <p>与えられた名前がネーミングコンテキストかどうか判断します。</p>
     *
     * @param name 判断対象コンポーネント
     *
     * @exception NotFound 対象オブジェクトが存在しない。
     * @exception CannotProceed 何らかの理由で処理を継続できない。
     * @exception InvalidName 引数 name の名前が不正。
     */
    public boolean isNamingContext(final NameComponent[] name) 
        throws NotFound, CannotProceed, InvalidName {
        return isNamingContext(resolve(name));
    }

    /**
     * <p>与えられた名前がネーミングコンテキストかどうか判断します。<p>
     *
     * @param string_name 判断対象コンポーネント名称
     *
     * @exception NotFound 対象オブジェクトが存在しない。
     * @exception CannotProceed 何らかの理由で処理を継続できない。
     * @exception InvalidName 引数 name の名前が不正。
     */
    public boolean isNamingContext(final String string_name) 
        throws NotFound, CannotProceed, InvalidName {
      return isNamingContext(resolve(string_name));
    }

    /**
     * <p>ネームコンポーネントの部分を返します。</p>
     *
     * @param name 対象コンポーネント名称
     * @param begin 判断開始位置
     *
     * @return ネームコンポーネント
     */
    public NameComponent[] subName(NameComponent[] name, long begin) {
        return subName(name, begin, -1);
    }
    /**
     * <p>ネームコンポーネントの部分を返します。</p>
     *
     * @param name 対象コンポーネント名称
     * @param begin 判断開始位置
     * @param end 判断終了位置
     *
     * @return ネームコンポーネント
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
     * <p>ネームコンポーネントの文字列表現を取得します。</p>
     *
     * @param name 対象ネームコンポーネント
     * @param string_name 文字列取得結果
     * @param slen 文字列長さ
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
     * <p>ネームコンポーネントの文字列表現時の文字長を取得します。</p>
     *
     * @param name 対象ネームコンポーネント
     *
     * @return 文字長
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
     * ORB
     */
    protected ORB m_varORB;
    /**
     * ネームサーバ名称
     */
    protected String m_nameServer = new String();
    /**
     * ルートコンテキスト
     */
    protected NamingContextExt m_rootContext;
    /**
     * コンテキストの深さ
     */
    private int m_blLength;


//<+zxc
    /**
     * <p>Logging用フォーマットオブジェクト</p>
     */
    protected Logbuf rtcout;
//+>
}

