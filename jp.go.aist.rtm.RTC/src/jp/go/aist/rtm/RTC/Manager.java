package jp.go.aist.rtm.RTC;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
import java.util.Set;
import java.util.Iterator;
import java.util.logging.FileHandler;
import java.util.logging.ConsoleHandler;

import jp.go.aist.rtm.RTC.executionContext.ECFactoryBase;
import jp.go.aist.rtm.RTC.executionContext.ECFactoryJava;
import jp.go.aist.rtm.RTC.executionContext.ExecutionContextBase;
import jp.go.aist.rtm.RTC.executionContext.ExtTrigExecutionContext;
import jp.go.aist.rtm.RTC.executionContext.PeriodicExecutionContext;
import jp.go.aist.rtm.RTC.executionContext.OpenHRPExecutionContext;
import jp.go.aist.rtm.RTC.executionContext.PeriodicECSharedComposite;
import jp.go.aist.rtm.RTC.FactoryInit;
import jp.go.aist.rtm.RTC.log.Logbuf;
import jp.go.aist.rtm.RTC.util.ORBUtil;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.RTCUtil;
import jp.go.aist.rtm.RTC.util.StringUtil;
import jp.go.aist.rtm.RTC.util.TimeValue;
import jp.go.aist.rtm.RTC.util.Timer;
import jp.go.aist.rtm.RTC.util.equalFunctor;
import jp.go.aist.rtm.RTC.util.IiopAddressComp;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.SystemException;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManager;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import org.omg.IOP.TAG_INTERNET_IOP;

import RTC.RTObject;
import RTC.ReturnCode_t;

/**
 * {@.ja 各コンポーネントの管理を行うクラスです.}
 * {@.en This is a manager class that manages various information 
 * such as components.}
 *
 */
public class Manager {

    /**
     * <p>コンストラクタです。</p>
     *     
     * Protected コンストラクタ
     *
     */
    protected Manager() {
        
        m_initProc = null;
        rtcout = new Logbuf("Manager");
        m_runner = null;
        m_terminator = null;
    }

    /**
     * <p>コピーコンストラクタです。</p>
     * 
     * @param rhs コピー元のManagerオブジェクト
     */
    public Manager(final Manager rhs) {
        
        m_initProc = null;
        rtcout = new Logbuf("Manager");
        m_runner = null;
        m_terminator = null;
    }
    
    /**
     * <p>初期化を行います。Managerを使用する場合には、必ず本メソッドを呼ぶ必要があります。<br />
     * コマンドライン引数を与えて初期化を行います。Managerオブジェクトを取得する方法としては、
     * init(), instance()の2メソッドがありますが、初期化はinit()でのみ行われるため、
     * Managerオブジェクトの生存期間の最初にinit()メソッドを呼び出す必要があります。</p>
     * 
     * @param argv コマンドライン引数の配列
     */
    public static Manager init(String[] argv) {
        
        if (manager == null) {
            synchronized (manager_mutex) {
                if (manager == null) {
                    try {
                        manager = new Manager();
                        manager.initManager(argv);
                        manager.initLogger();
                        manager.initORB();
                        manager.initNaming();
                        manager.initFactories();
                        manager.initExecContext();
                        manager.initComposite();
                        manager.initTimer();
                        manager.initManagerServant();
                        
                    } catch (Exception e) {
                        manager = null;
                    }
                }
            }
        }

        return manager;
    }
    
    /**
     * <p>Managerオブジェクトを取得します。</p>
     * 
     * @return Managerオブジェクト
     */
    public static Manager instance() {
        
        if (manager == null) {
            synchronized (manager_mutex) {
                if (manager == null) {
                    try {
                        manager = new Manager();
                        manager.initManager(null);
                        manager.initLogger();
                        manager.initORB();
                        manager.initNaming();
                        manager.initFactories();
                        manager.initExecContext();
                        manager.initComposite();
                        manager.initTimer();
                        
                    } catch (Exception e) {
                        manager = null;
                    }
                }
            }
        }

        return manager;
    }
    
    /**
     * <p>Managerの終了処理を行います。</p>
     */
    public void terminate() {
        
        if (m_terminator != null) {
            m_terminator.terminate();
        }
    }
    
    /**
     * <p>Managerオブジェクトを終了します。
     * ORBの終了後，同期を取って終了します。</p>
     */
    public void shutdown() {
        
        rtcout.println(rtcout.TRACE, "Manager.shutdown()");
        
        shutdownComponents();
        shutdownNaming();
        shutdownORB();
        shutdownManager();
        
        // 終了待ち合わせ
        if (m_runner != null) {
            try {
                m_runner.wait();
                
            } catch (InterruptedException e) {
                rtcout.println(rtcout.DEBUG, "Exception: Caught InterruptedException in Manager.shutdown().");
                rtcout.println(rtcout.DEBUG, e.getMessage());
                e.printStackTrace();
            }
        } else {
            join();
        }
        
        shutdownLogger();
    }
    
    /**
     * <p>Manager終了処理の待ち合わせを行います。</p>
     */
    public void join() {
        
        rtcout.println(rtcout.TRACE, "Manager.join()");
        
        synchronized (Integer.valueOf(m_terminate_waiting)) {
            ++m_terminate_waiting;
        }
        
        while (true) {
            synchronized (Integer.valueOf(m_terminate_waiting)) {
                if (m_terminate_waiting > 1) {
                    break;
                }
            }
            
            try {
                Thread.sleep(100);
                
            } catch (InterruptedException e) {
                rtcout.println(rtcout.DEBUG, "Exception: Caught InterruptedException in Manager.join().");
                rtcout.println(rtcout.DEBUG, e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * <p>マネージャのコンフィギュレーションを取得します。</p>
     *
     * @return マネージャコンフィギュレーション
     */ 
    public Properties getConfig() {
        return m_config;
    }
    
    /**
     * <p>初期化プロシジャコールバックインタフェースを設定します。
     * マネージャが初期化されてアクティブ化された後に、
     * 設定されたコールバックインタフェースが呼び出されます。</p>
     * 
     * @param initProc コールバックインタフェース
     */
    public void setModuleInitProc(ModuleInitProc initProc) {
        m_initProc = initProc;
    }
    
    /**
     * <p>Managerをアクティブ化します。
     * 初期化後にrunManager()呼び出しに先立ってこのメソッドを呼び出す必要があります。</p>
     * 
     * <p>具体的には以下の処理が行われます。
     * <ol>
     * <li>CORBA POAManagerのアクティブ化</li>
     * <li>Manager CORBAオブジェクトのアクティブ化</li>
     * <li>ManagerへのCORBAオブジェクト参照の登録</li>
     * </ol>
     * </p>
     * 
     * @return 正常にアクティブ化できた場合はtrueを、さもなくばfalseを返します。
     */
    public boolean activateManager() {
        
        rtcout.println(rtcout.TRACE, "Manager.activateManager()");
        
        try {
            if(this.getPOAManager() == null) {
                rtcout.println(rtcout.ERROR, "Could not get POA manager.");
                return false;
            }
            this.getPOAManager().activate();
            rtcout.println(rtcout.TRACE, "POA Manager activated.");
        } catch (Exception e) {
            rtcout.println(rtcout.DEBUG, "Exception: Caught unknown Exception in Manager.activateManager().");
            rtcout.println(rtcout.DEBUG, "POA Manager activation failed.");
            rtcout.println(rtcout.DEBUG, e.getMessage());
            return false;
        }

        bindManagerServant();

        preloadComponent();

        if (m_initProc != null) {
            m_initProc.myModuleInit(this);
        }

        precreateComponent();

        return true;
    }

    /**
     * <p> preloadComponent </p>
     *
     */
    private void preloadComponent() {
        String[] mods 
                = m_config.getProperty("manager.modules.preload").split(",");
        for (int i=0; i < mods.length; ++i) {
            if ( mods[i].length() == 0) {
                continue;
            }
	    mods[i] = mods[i].trim();
            String[] str = mods[i].split("\\.");
            try {
                m_module.load(mods[i], "registerModule");
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                rtcout.println(rtcout.ERROR, "Module load error: " + mods[i]);
            } catch (ClassNotFoundException e) {
                rtcout.println(rtcout.ERROR, "Module not found: " + mods[i]);
            } catch (Exception ex) {
                rtcout.println(rtcout.ERROR, "Unknown Exception");
            }
        }
    }

    /**
     * <p> precreateComponent </p>
     *
     */
    private void precreateComponent() {
        String[] comp 
            = m_config.getProperty("manager.components.precreate").split(",");
        for (int i=0; i < comp.length; ++i) {
            if ( comp[i].length() == 0) {
                continue;
            }
	    comp[i] = comp[i].trim();
            this.createComponent(comp[i]);
        }
    }

    /**
     * <p>Managerのメインループを実行します。本メソッドは、runManager(false)の呼び出しと同等です。</p>
     */
    public void runManager() {
        this.runManager(false);
    }
    
    /**
     * <p>Managerのメインループを実行します。
     * このメインループ内では、CORBA ORBのイベントループなどが処理されます。<br />
     * ブロッキングモードで起動された場合は、Manager#destroy()メソッドが呼び出されるまで、
     * 本runManager()メソッドは処理を戻しません。<br />
     * 非ブロッキングモードで起動された場合は、内部でイベントループを別スレッドで開始後、
     * ブロックせずに処理を戻します。</p>
     * 
     * @param noBlocking 非ブロッキングモードの場合はtrue、ブロッキングモードの場合はfalse
     */
    public void runManager(boolean noBlocking) {
        
        if (noBlocking) {
            rtcout.println(rtcout.TRACE, "Manager.runManager(): non-blocking mode");
            
            m_runner = new OrbRunner(m_pORB);
            m_runner.open("");
        } else {
            rtcout.println(rtcout.TRACE, "Manager.runManager(): blocking mode");
            
            m_pORB.run();

            rtcout.println(rtcout.TRACE, "Manager.runManager(): ORB was terminated");
            
            join();
        }
    }
    
    /**
     * <p>コンポーネントのモジュールをロードして、初期化メソッドを実行します。</p>
     *
     * @param moduleFileName モジュールファイル名
     * @param initFunc 初期化メソッド名
     */
    public String load(final String moduleFileName, final String initFunc) {
        
        rtcout.println(rtcout.TRACE, 
                        "Manager.load("+moduleFileName+","+initFunc+")");
        
        String file_name = moduleFileName;
        String init_func = initFunc;
        try {
            if (init_func==null||init_func.equals("")) {
                String[] mod = moduleFileName.split(".");
                init_func = mod[0] + "Init";
            }
            String path = m_module.load(file_name, initFunc);
            rtcout.println(rtcout.DEBUG, "module path: "+path);
            return path;
//            return m_module.load(moduleFileName, initFunc);
            
        } catch (Exception e) {
            rtcout.println(rtcout.DEBUG, 
                "Exception: Caught unknown Exception in Manager.load().");
            rtcout.println(rtcout.DEBUG, e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * <p>モジュールをアンロードします。</p>
     *
     * @param moduleFileName モジュールファイル名
     */ 
    public void unload(final String moduleFileName) throws Exception {
        
        rtcout.println(rtcout.TRACE, "Manager.unload("+moduleFileName+")");
        
        m_module.unload(moduleFileName);
    }
    
    /**
     * <p>すべてのモジュールをアンロードします。</p>
     */ 
    public void unloadAll() {
        
        rtcout.println(rtcout.TRACE, "Manager.unloadAll()");
        
        m_module.unloadAll();
    }
    
    /**
     * <p>ロード済みのモジュール名リストを取得します。</p>
     * 
     * @return ロード済みモジュール名リスト
     */
    public Vector<Properties> getLoadedModules() {
        
        rtcout.println(rtcout.TRACE, "Manager.getLoadedModules()");

        return m_module.getLoadedModules();
/*
        Set<String> key = m_module.getLoadedModules().keySet();

        Iterator it = key.iterator();
        Vector<Properties> props = new Vector<Properties>();
        while (it.hasNext()) {
            String str  = (String)it.next();
            Properties prop = new Properties(str);
            props.add(prop);
        } 
        return props;
*/
    }
    
    /**
     * <p>ロード可能なモジュール名リストを取得します。</p>
     *
     * @return ロード可能モジュール名リスト
     */
    public Vector<Properties> getLoadableModules() {
        
        rtcout.println(rtcout.TRACE, "Manager.getLoadableModules()");
        
        return m_module.getLoadableModules();
    }
    
    /**
     * <p>RTコンポーネントファクトリを登録します。</p>
     *
     * @param profile コンポーネントプロファイル 
     * @param new_func コンポーネント生成オブジェクト 
     * @param delete_func コンポーネント削除オブジェクト 
     *
     * @return 登録に成功した場合はtrueを、さもなくばfalseを返します。
     */
    public boolean registerFactory(Properties profile, RtcNewFunc new_func,
            RtcDeleteFunc delete_func) {
        
        rtcout.println(rtcout.TRACE, "Manager.registerFactory("
                + profile.getProperty("type_name") + ")");

        try {
            FactoryBase factory = new FactoryJava(profile, new_func, delete_func);
            m_factory.registerObject(factory, new FactoryPredicate(factory));
            return true;
            
        } catch (Exception ex) {
            rtcout.println(rtcout.DEBUG, 
           "Exception: Caught unknown Exception in Manager.registerFactory().");
            rtcout.println(rtcout.DEBUG, ex.getMessage());
            return false;
        }
    }

    /**
     * <p> getFactoryProfiles </p>
     *
     */
    public Vector<Properties> getFactoryProfiles() {
        rtcout.println(rtcout.TRACE, "Manager.getFactoryProfiles()");

        Vector<FactoryBase> factories = m_factory.getObjects();
        Vector<Properties> props = new Vector<Properties>();
        for (int i=0, len=factories.size(); i < len; ++i) {
            props.add(factories.elementAt(i).profile());
        }

        return props;
    }

   /**
    * <p>ExecutionContextファクトリを登録します。</p>
    *
    * @param name ExecutionContext名称 
    * @return 登録に成功した場合はtrueを、さもなくばfalseを返します。
    */
    public boolean registerECFactory(final String name) {
        
        rtcout.println(rtcout.TRACE, "Manager.registerECFactory(" + name + ")");
        
        try {
            ECFactoryBase factory = new ECFactoryJava(name);
            if( factory == null ) {
                return false;
            }
            if( !m_ecfactory.registerObject(factory, new ECFactoryPredicate(factory))) {
                factory = null;
                return false;
            }
            return true;
            
        } catch (Exception ex) {
            rtcout.println(rtcout.DEBUG, "Exception: Caught unknown Exception in Manager.registerECFactory().");
            rtcout.println(rtcout.DEBUG, ex.getMessage());
            return false;
        }
    }

    /**
     * <p>すべてのRTコンポーネントファクトリのリストを取得します。</p>
     * 
     * @return すべてのRTコンポーネントファクトリのリスト
     */
    public Vector<String> getModulesFactories() {
        
        rtcout.println(rtcout.TRACE, "Manager.getModulesFactories()");

        Vector<String> factoryIds = new Vector<String>();
        for (int i=0, len=m_factory.m_objects.size(); i < len; ++i) {
            factoryIds.add(m_factory.m_objects.elementAt(i).profile().getProperty("implementation_id"));
        }
        
        return factoryIds;
    }
    
    /**
     * <p>RTコンポーネントファクトリをクリアする。</p>
     */
    public void clearModulesFactories() {
        m_factory = new ObjectManager<String, FactoryBase>();
    }

    /**
     * <p>RTコンポーネントマネージャをクリアする。</p>
     */
    public void clearModules() {
        m_compManager = new ObjectManager<String, RTObject_impl>();
    }
    
    /**
     * {@.ja RTコンポーネントを生成する}
     * {@.en Create RT-Components}
     * <p>
     * {@.ja 指定したRTコンポーネントのインスタンスを登録されたFactory経由
     * で生成する。
     *
     * 生成されるコンポーネントの各種プロファイルは以下の優先順位で
     * 設定される。
     *
     * -# createComponent() の引数で与えられたプロファイル
     * -# rtc.confで指定された外部ファイルで与えられたプロファイル
     * --# category.instance_name.config_file
     * --# category.component_type.config_file
     * -# コードに埋め込まれたプロファイル 
     *
     * インスタンス生成が成功した場合、併せて以下の処理を実行する。
     *  - 外部ファイルで設定したコンフィギュレーション情報の読み込み，設定
     *  - ExecutionContextのバインド，動作開始
     *  - ネーミングサービスへの登録}
     * {@.en Create specified RT-Component's instances via registered Factory.
     * When its instances have been created successfully, the following
     * processings are also executed.
     *  - Read and set configuration information that was set by external file.
     *  - Bind ExecutionContext and start operation.
     *  - Register to naming service.}
     * </p>
     * @param comp_args
     *   {@.ja 生成対象RTコンポーネントIDおよびコンフィギュレー
     *         ション引数。フォーマットは大きく分けて "id" と "configuration" 
     *         部分が存在する。
     *
     * comp_args:     [id]?[configuration]
     *                id は必須、configurationはオプション
     * id:            RTC:[vendor]:[category]:[implementation_id]:[version]
     *                RTC は固定かつ必須
     *                vendor, category, version はオプション
     *                implementation_id は必須
     *                オプションを省略する場合でも ":" は省略不可
     * configuration: [key0]=[value0]&[key1]=[value1]&[key2]=[value2].....
     *                RTCが持つPropertiesの値をすべて上書きすることができる。
     *                key=value の形式で記述し、"&" で区切る
     *
     * 例えば、
     * RTC:jp.go.aist:example:ConfigSample:1.0?conf.default.str_param0=munya
     * RTC::example:ConfigSample:?conf.default.int_param0=100}
     *
     *   {@.en Target RT-Component names for the creation}
     * @return
     *   {@.ja 生成したRTコンポーネントのインスタンス}
     *   {@.en Created RT-Component's instances}
     */
    public RTObject_impl createComponent(final String comp_args) {
        
        rtcout.println(rtcout.TRACE, 
                            "Manager.createComponent(" + comp_args + ")");
        
        if( comp_args == null || comp_args.equals("") ) {
            return null;
        }

        //------------------------------------------------------------
        // extract "comp_type" and "comp_prop" from comp_arg
        Properties comp_prop = new Properties();
        Properties comp_id = new Properties();
        if (!procComponentArgs(comp_args, comp_id, comp_prop)) {
            return null;
        }

        if (!(comp_prop.getProperty("exported_ports") == null || 
              comp_prop.getProperty("exported_ports").equals(""))) {
            comp_prop.setProperty("conf.default.exported_ports"
                                    ,comp_prop.getProperty("exported_ports"));
        }

        //------------------------------------------------------------
        // Create Component
        RTObject_impl comp = null;
        Properties prop = new Properties();
        int i,len;
        for (i=0, len=m_factory.m_objects.size(); i < len; ++i) {
            FactoryBase factory = m_factory.m_objects.elementAt(i);
            if (factory == null) {
                return null;
            }

            if (factory.m_Profile.getProperty("implementation_id").equals(comp_id.getProperty("implementation_id"))) {
                prop = factory.profile();

                Vector<String> keyval = comp_prop.propertyNames();
                for (int ic=0, lenc=comp_prop.size(); ic < lenc; ++ic) {
                    prop.setProperty(keyval.get(ic) , 
                                        comp_prop.getProperty(keyval.get(ic)));
                }

                final String[] inherit_prop = {
                    "exec_cxt.periodic.type",
                    "exec_cxt.periodic.rate",
                    "exec_cxt.evdriven.type",
                    "naming.formats",
                    "logger.enable",
                    "logger.log_level",
                    "naming.enable",
                    "naming.type",
                    "naming.formats",
                    ""
                };

                for (int ic=0; inherit_prop[ic].length() != 0; ++ic) {
                    System.out.println( inherit_prop[ic] );
                    //        if (prop.hasKey() == NULL) continue;
                    prop.setProperty(inherit_prop[ic], 
                                        m_config.getProperty(inherit_prop[ic]));
                }

                comp = m_factory.m_objects.elementAt(i).create(this);
                if (comp == null) {
                    rtcout.println(rtcout.ERROR, 
                        "RTC creation failed: " 
                        + comp_id.getProperty("implementaion_id"));
                    return null;
                }
//<+zxc
{
    String ior = null;
    try {
        org.omg.CORBA.Object ref = m_pPOA.servant_to_reference(comp);
        ior = m_pORB.object_to_string(ref);
//        System.out.println(ior);
    } catch (Exception e) {
        System.out.println("create object:");
        e.printStackTrace(System.err);
    }
    try {
        // プロセスオブジェクトを生成
        Process process = Runtime.getRuntime().exec("catior "+ior);
        // 外部コマンドの標準出力を取得するための入力ストリームを取得
        java.io.InputStream is = process.getInputStream();
        BufferedReader br 
                = new BufferedReader(new java.io.InputStreamReader(is));
        // 標準出力を１行づつ取り出します
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
//+>
                rtcout.println(rtcout.TRACE, 
                    "RTC Created: " + comp_id.getProperty("implementaion_id"));
                break;
            }
        }
        if(i == m_factory.m_objects.size()) {
            rtcout.println(rtcout.ERROR, 
            "Factory not found: " + comp_id.getProperty("implementaion_id"));
            return null;
        } 
        if( comp == null ) {
            return null;
        }
        //------------------------------------------------------------
        // Load configuration file specified in "rtc.conf"
        //
        // rtc.conf:
        // [category].[type_name].config_file = file_name
        // [category].[instance_name].config_file = file_name
        configureComponent(comp, prop);

        // comp.setProperties(prop);

        //------------------------------------------------------------
        // Component initialization
        if( comp.initialize() != ReturnCode_t.RTC_OK ) {
            rtcout.println(rtcout.TRACE, "RTC initialization failed: " + comp_id.getProperty("implementaion_id"));
            comp.exit();
            return null;
        }
        rtcout.println(rtcout.TRACE, "RTC initialization succeeded: " + comp_id.getProperty("implementaion_id"));

        //------------------------------------------------------------
        // Bind component to naming service
        registerComponent(comp);

        return comp;
    }
    
    /**
     * <p>指定したRTコンポーネントを登録解除します。</p>
     * 
     * @param comp 登録解除するRTコンポーネントオブジェクト
     */
    public void cleanupComponent(RTObject_impl comp) {
        
        rtcout.println(rtcout.TRACE, "Manager.cleanupComponent()");
        
        unregisterComponent(comp);
    }
    
    /**
     * <p> procComponentArgs </p>
     *
     * @param comp_arg String
     * @param comp_id Properties
     * @param comp_conf Propertie
     * @return boolean
     *
     */
    public boolean procComponentArgs(final String comp_arg,
                                     Properties comp_id,
                                     Properties comp_conf)
    {
        rtcout.println(rtcout.TRACE, "Manager.procComponentArgs("+comp_arg+")");

        String[] id_and_conf = comp_arg.split("\\?");
        // arg should be "id?[conf]". id is mandatory, conf is optional
        if (id_and_conf.length != 1 && id_and_conf.length != 2) {
            rtcout.println(rtcout.ERROR, "args devided into " + id_and_conf.length);
            rtcout.println(rtcout.ERROR, "Invalid arguments. Two or more '?' in arg : " + comp_arg);
            return false;
        }
        if (id_and_conf[0].indexOf(":") == -1) {
            id_and_conf[0] = "RTC:::".concat(id_and_conf[0]);
            id_and_conf[0] = id_and_conf[0].concat(":");
        }
        System.out.println( "ID: " + id_and_conf[0] );
        String[] id = id_and_conf[0].split(":",-1);
        System.out.println( "id.size(): " + id.length );

        // id should be devided into 1 or 5 elements
        // RTC:[vendor]:[category]:impl_id:[version] => 5
        if (id.length != 5) {
            rtcout.println(rtcout.ERROR, "Invalid RTC id format.: " + id_and_conf[0]);
            return false;
        }

        final String[] prof = {
          "RTC",
          "vendor",
          "category",
          "implementation_id",
          "version"
        };

        if (id[0].trim().equals(prof[0]) == false) {
            rtcout.println(rtcout.ERROR, "Invalid id type: " + id[0]);
            return false;
        }
        for (int i = 1; i < 5; ++i) {
            comp_id.setProperty(prof[i], id[i].trim());
            rtcout.println(rtcout.TRACE, "RTC basic propfile " + prof[i] + ":" + id[i].trim());
        }

        if (id_and_conf.length == 2) {
            String[] conf = id_and_conf[1].split("&");
            for (int i = 0, len = conf.length; i < len; ++i) {
                String[] keyval = conf[i].split("=", -1);
                comp_conf.setProperty(keyval[0].trim(), keyval[1].trim());
                rtcout.println(rtcout.TRACE, "RTC property " + keyval[0] + ":" + keyval[1]);
            }
        }
        return true;
    }

    /**
     * <p>RTコンポーネントを、直接にManagerに登録します。</p>
     *
     * @param comp 登録対象のRTコンポーネントオブジェクト 
     * @return 正常に登録できた場合はtrueを、さもなくばfalseを返します。
     */
    public boolean registerComponent(RTObject_impl comp) {
        
        rtcout.println(rtcout.TRACE, "Manager.registerComponent("
                + comp.getInstanceName() + ")");
        
        // NamingManagerのみで代用可能
        m_compManager.registerObject(comp, new InstanceName(comp));
        
        String[] names = comp.getNamingNames();
        for (int i = 0; i < names.length; ++i) {
            rtcout.println(rtcout.TRACE, "Bind name: " + names[i]);
            
            m_namingManager.bindObject(names[i], comp);
        }

        return true;
    }
    
    /**
     * <p>指定したRTコンポーネントを登録解除します。</p>
     * 
     * @param comp 登録解除するRTコンポーネントオブジェクト
     */
    public boolean unregisterComponent(RTObject_impl comp) {
        
        rtcout.println(rtcout.TRACE, "Manager.unregisterComponent("
                + comp.getInstanceName() + ")");
        
        // NamingManager のみで代用可能
        m_compManager.unregisterObject(new InstanceName(comp));
        
        String[] names = comp.getNamingNames();
        for (int i = 0; i < names.length; ++i) {
            rtcout.println(rtcout.TRACE, "Unbind name: " + names[i]);
            
            m_namingManager.unbindObject(names[i]);
        }
        
        return true;
    }
    
    /**
     * <p> createContext </p>
     *
     * @param ec_args String
     * @return ExecutionContextBase
     *
     */
    public ExecutionContextBase createContext(final String ec_args) {
        rtcout.println(rtcout.TRACE, "Manager.createContext("+ec_args+")");
        rtcout.println(rtcout.TRACE, "ExecutionContext type: " + m_config.getProperty("exec_cxt.periodic.type") );

        StringBuffer ec_id = new StringBuffer();
        Properties ec_prop = new Properties();
        if (!procContextArgs(ec_args, ec_id, ec_prop)) {
            return null;
        }

        ECFactoryBase factory = (ECFactoryBase)m_ecfactory.find(new ECFactoryPredicate(ec_id.toString()));

        if(factory == null) {
            rtcout.println(rtcout.ERROR, "Factory not found: " + ec_id);
            return null;
        }

        ExecutionContextBase ec;
        ec = factory.create();
        return ec;

  }
    /**
     * <p> procContextArgs </p>
     *
     * @param ec_args String
     * @param ec_id StringBuffer
     * @param ec_conf Properties
     * @return boolean
     *
     */
    public boolean procContextArgs(final String ec_args,
                                   StringBuffer ec_id,
                                   Properties ec_conf) {

        rtcout.println(rtcout.TRACE, "Manager.procContextArgs("+ec_args+","+ec_id.toString()+")");

        String[] id_and_conf = ec_args.split("\\?");
        if (id_and_conf.length != 1 && id_and_conf.length != 2) {
            rtcout.println(rtcout.ERROR, "Invalid arguments. Two or more '?' in arg : " + ec_args);
            return false;
        }
        if (id_and_conf[0].length() == 0) {
            rtcout.println(rtcout.ERROR, "Empty ExecutionContext's name");
            return false;
        }
        ec_id.append(id_and_conf[0]);

        if (id_and_conf.length == 2) {
            String[] conf = id_and_conf[1].split("&");
            for (int i=0, len=conf.length; i < len; ++i) {
                String[] k = conf[i].split("=");
                ec_conf.setProperty(k[0], k[1]);
                rtcout.println(rtcout.TRACE, "EC property "+ k[0] + ":" + k[1]);
             }
        }

        return true;
    }
    /**
     * <p>指定したRTコンポーネントに、ExecutionContextをバインドします。</p>
     * 
     * @param comp バインド対象のRTコンポーネントオブジェクト
     * @return 正常にバインドできた場合はtrueを、さもなくばfalseを返します。
     */
/*
    public boolean bindExecutionContext(RTObject_impl comp) {
        
        rtcout.println(rtcout.TRACE, "Manager.bindExecutionContext()");
        rtcout.println(rtcout.TRACE, "ExecutionContext type: "
                + m_config.getProperty("exec_cxt.periodic.type"));

        RTObject rtobj = comp.getObjRef();

        ExecutionContextBase exec_cxt;

        if (RTCUtil.isDataFlowComponent(rtobj)) {
            final String ectype = m_config.getProperty("exec_cxt.periodic.type");

            ECFactoryBase ecfactory = (ECFactoryBase)(m_ecfactory.find(new ECFactoryPredicate(ectype)));
            if( ecfactory == null ) {
                return false;
            }
            exec_cxt = ecfactory.create();
            
            try {
                m_objManager.activate(exec_cxt);
                
            } catch (ServantAlreadyActive e) {
                rtcout.println(rtcout.DEBUG, "Exception: Caught ServantAlreadyActive Exception in Manager.bindExecutionContext() DataFlowParticipant.");
                rtcout.println(rtcout.DEBUG, e.getMessage());
                
            } catch (WrongPolicy e) {
                rtcout.println(rtcout.DEBUG, "Exception: Caught WrongPolicy Exception in Manager.bindExecutionContext() DataFlowParticipant.");
                rtcout.println(rtcout.DEBUG, e.getMessage());
                
            } catch (ObjectNotActive e) {
                rtcout.println(rtcout.DEBUG, "Exception: Caught ObjectNotActive Exception in Manager.bindExecutionContext() DataFlowParticipant.");
                rtcout.println(rtcout.DEBUG, e.getMessage());
            }
            
            final String rate = m_config.getProperty("exec_cxt.periodic.rate");
            exec_cxt.set_rate(Double.valueOf(rate).doubleValue());
        }
        else {
            final String ectype = m_config.getProperty("exec_cxt.evdriven.type");
            exec_cxt = ((ECFactoryBase) (m_ecfactory.find(new ECFactoryPredicate(ectype)))).create();
            
            try {
                m_objManager.activate(exec_cxt);
                
            } catch (ServantAlreadyActive e) {
                rtcout.println(rtcout.DEBUG, "Exception: Caught ServantAlreadyActive Exception in Manager.bindExecutionContext() FsmParticipant.");
                rtcout.println(rtcout.DEBUG, e.getMessage());
                
            } catch (WrongPolicy e) {
                rtcout.println(rtcout.DEBUG, "Exception: Caught WrongPolicy Exception in Manager.bindExecutionContext() FsmParticipant.");
                rtcout.println(rtcout.DEBUG, e.getMessage());
                
            } catch (ObjectNotActive e) {
                rtcout.println(rtcout.DEBUG, "Exception: Caught ObjectNotActive Exception in Manager.bindExecutionContext() FsmParticipant.");
                rtcout.println(rtcout.DEBUG, e.getMessage());
            }
        }

        exec_cxt.add_component(rtobj);
        exec_cxt.start();
        m_ecs.add(exec_cxt);
        
        return true;
    }
*/
    
    /**
     * <p>Managerに登録されているRTコンポーネントを削除します。</p>
     * <p>※未実装</p>
     *
     * @param instanceName 削除対象のRTコンポーネント名 
     */
    public void deleteComponent(final String instanceName) {
        
        rtcout.println(rtcout.TRACE, "Manager.deleteComponent(" + instanceName + ")");
        RTObject_impl comp = null;
        comp = m_compManager.find(new InstanceName(instanceName));
        if (comp == null) {
            return;
        }

        Properties comp_id = new Properties();
        comp_id.setProperty("vendor", comp.getProperties().getProperty("vendor"));
        comp_id.setProperty("category", comp.getProperties().getProperty("category"));
        comp_id.setProperty("implementation_id", comp.getProperties().getProperty("implementation_id"));
        comp_id.setProperty("version", comp.getProperties().getProperty("version"));

	FactoryBase factory = (FactoryBase)m_factory.find(new FactoryPredicate(comp.getProperties().getProperty("implementation_id")));

        ReturnCode_t ret = comp.exit();

        if (factory == null) {
            return;
        }
        else {
            factory.destroy(comp);
        }
    }
    
    /**
     * <p>Managerに登録されているRTコンポーネントを取得します。</p>
     * <p>※未実装</p>
     *
     * @param instanceName 取得対象RTコンポーネント名 
     * @return 対象RTコンポーネントオブジェクト
     */
    public RTObject_impl getComponent(final String instanceName) {
        
        rtcout.println(rtcout.TRACE, "Manager.getComponent(" + instanceName + ")");
        return m_compManager.find(new InstanceName(instanceName));
    }
    
    /**
     * <p>Managerに登録されている全てのRTコンポーネントを取得します。</p>
     *
     * @return RTコンポーネントのリスト
     */
    public Vector<RTObject_impl> getComponents() {
        
        rtcout.println(rtcout.TRACE, "Manager.getComponents()");
        
        return m_compManager.getObjects();
    }
    
    /**
     * <p>ORBを取得します。</p>
     *
     * @return ORBオブジェクト
     */
    public ORB getORB() {
        
        rtcout.println(rtcout.TRACE, "Manager.getORB()");
        
        return m_pORB;
    }
    
    /**
     * <p>RootPOAを取得します。</p>
     *
     * @return RootPOAオブジェクト
     */
    public POA getPOA() {
        
        rtcout.println(rtcout.TRACE, "Manager.getPOA()");
        
        return m_pPOA;
    }

    /**
     * <p>POAマネージャを取得します。</p>
     *
     * @return POAマネージャ
     */
    public POAManager getPOAManager() {
        
        rtcout.println(rtcout.TRACE, "Manager.getPOAManager()");
        
        return m_pPOAManager;
    }
    
    /**
     * <p>Managerの内部初期化処理を行います。</p>
     *
     * @param argv コマンドライン引数
     */
    protected void initManager(String[] argv) throws Exception {
        
        ManagerConfig config = new ManagerConfig(argv);
        if (m_config == null) {
            m_config = new Properties();
        }
        
        config.configure(m_config);
        m_config.setProperty("logger.file_name",
                formatString(m_config.getProperty("logger.file_name"), m_config));

        m_module = new ModuleManager(m_config);
        m_terminator = new Terminator(this);

        synchronized (m_terminator) {
            m_terminate_waiting = 0;
        }

        if (StringUtil.toBool(m_config.getProperty("timer.enable"), "YES", "NO", true)) {
            TimeValue tm = new TimeValue(0, 100);
            String tick = m_config.getProperty("timer.tick");
            if (! (tick == null || tick.equals(""))) {
                tm.convert((Double.valueOf(tick)).doubleValue());
                m_timer = new Timer(tm);
                m_timer.start();
            }
        }
    }
    
    /**
     * <p>Managerの終了処理を実行します。</p>
     */
    protected void shutdownManager() {
        
        rtcout.println(rtcout.TRACE, "Manager.shutdownManager()");
    }
    
    /**
     * <p>System loggerを初期化します。</p>
     *
     * @return 正常に初期化できた場合はtrueを、さもなくばfalseを返します。
     */
    protected boolean initLogger() {
        
        rtcout.setLevel("SILENT");
        
        if (StringUtil.toBool(m_config.getProperty("logger.enable"), "YES", "NO", true)) {
            
            rtcout.setEnabled();
            String[] logouts = m_config.getProperty("logger.file_name").split(",");
            for (int i=0; i < logouts.length; ++i) {
                String logfile = logouts[i].trim();
                if (logfile == null) continue;

                // Open logfile
                if (logfile.equals("STDOUT") || logfile.equals("stdout")) {

                    rtcout.addStream(new ConsoleHandler());
                    continue;
                }
                
                try {
                    rtcout.addStream(new FileHandler(logfile));
                }
                catch(IOException ex) {
                    continue;
                }
            }
            
            // Set date format for log entry header
            rtcout.setDateFormat(m_config.getProperty("logger.date_format"));

            // Loglevel was set from configuration file.
            rtcout.setLevel(m_config.getProperty("logger.log_level"));

            // Log stream mutex locking mode
            rtcout.setLogLock(StringUtil.toBool(
                    m_config.getProperty("logger.stream_lock"), "enable", "disable", false));

            rtcout.println(rtcout.INFO, m_config.getProperty("openrtm.version"));
            rtcout.println(rtcout.INFO, "Copyright (C) 2003-2008");
            rtcout.println(rtcout.INFO, "  Noriaki Ando");
            rtcout.println(rtcout.INFO, "  Task-intelligence Research Group,");
            rtcout.println(rtcout.INFO, "  Intelligent Systems Research Institute, AIST");
            rtcout.println(rtcout.INFO, "Manager starting.");
            rtcout.println(rtcout.INFO, "Starting local logging.");
        } else {
            rtcout.setDisabled();
            m_config.setProperty("logger.log_level","SILENT");
        }
        
        return true;
    }
    
    /**
     * <p>System Loggerの終了処理を行います。</p>
     */
    protected void shutdownLogger() {
        rtcout.println(rtcout.TRACE, "Manager.shutdownLogger()");
    }
    
    /**
     * {@.ja CORBA ORB の初期化処理}
     * {@.en CORBA ORB initialization}
     * <p>
     * {@.ja 引数により与えられた設定を元にORBを初期化する。}
     * {@.en Initialize ORB based on the configuration given by arguments.}
     * </p>
     * @return  
     *   {@.ja ORB 初期化処理結果(初期化成功:true、初期化失敗:false)}
     *   {@.en ORB initialization result (Successful:true, Failed:false)}
     */
    protected boolean initORB() {

        rtcout.println(rtcout.TRACE, "Manager.initORB()");
        
        // Initialize ORB
        try {
            String[] args = createORBOptions().split(" ");
            java.util.Properties prop = createORBProperties();

            // ORB initialization
            m_pORB = ORBUtil.getOrb(args, prop);

            // Sets ports of TAG_ALTERNATE_IIOP_ADDRESS(IOR).
            IopIorInterceptor.replacePort0(m_pORB);

            // Get the RootPOA
            Object obj = m_pORB.resolve_initial_references("RootPOA");
            m_pPOA = POAHelper.narrow(obj);
            if (m_pPOA == null) {
                rtcout.println(rtcout.ERROR, "Could not resolve RootPOA.");
                return false;
            }

            
            // Get the POAManager
            m_pPOAManager = m_pPOA.the_POAManager();
            m_objManager = new CorbaObjectManager(m_pORB, m_pPOA);
            
        } catch (Exception ex) {
            rtcout.println(rtcout.DEBUG, 
                "Exception: Caught unknown Exception in Manager.initORB().");
            rtcout.println(rtcout.DEBUG, ex.getMessage());
            return false;
        }
        
        return true;
    }
    
    /**
     * {@.ja ORBのコマンドラインオプションを生成します。}
     * {@.en Create ORB command options}
     * <p>
     * {@.ja コンフィギュレーション情報に設定された内容から
     *       ORB の起動時オプションを作成する。}
     * {@.en Create ORB launch options from configuration information
     *       that has been set.}
     * </p>
     * @return 
     *   {@.ja ORB 起動時オプション}
     *   {@.en ORB launch options}
     */
    protected String createORBOptions() {
        
        String opt = m_config.getProperty("corba.args");
        rtcout.println(rtcout.DEBUG, "corba.args: "+opt);
        String dumpString = new String();
        dumpString = m_config._dump(dumpString, m_config, 0);
        rtcout.println(rtcout.DEBUG, dumpString);

        Vector<String> endpoints = new Vector<String>();
/*
        createORBEndpoints(endpoints);
System.out.println("createORBOptions.endpoints>:"+endpoints);
        createORBEndpointOption(opt, endpoints);
System.out.println("createORBOptions.endpoints>:"+endpoints);
*/
        rtcout.println(rtcout.PARANOID, "ORB options: "+opt);
        
        return opt;
    }

    /**
     * {@.ja エンドポイントの生成}
     * {@.en Create Endpoints}
     * <p>
     * {@.ja コンフィグレーションからエンドポイントを生成する。}
     * {@.en Create Endpoints from the configuration.}
     * </p>
     * @param endpoints 
     *   {@.ja エンドポイントリスト}
     *   {@.en endpoints Endpoints list}
     */
    protected void createORBEndpoints(Vector<String> endpoints) {
System.out.println("IN  createORBEndpoints");
System.out.println("endpoints>:"+endpoints);
        // corba.endpoint is obsolete
        // corba.endpoints with comma separated values are acceptable
//        String[] endpoints_array = (String[])endpoints.toArray();
        String[] endpoints_array = new String[0];
        if (m_config.hasKey("corba.endpoints")!=null) {
            endpoints_array 
                = m_config.getProperty("corba.endpoints").split(",");
            rtcout.println(rtcout.DEBUG, 
                "corba.endpoints: "+m_config.getProperty("corba.endpoints"));
        }
        else if (m_config.hasKey("corba.endpoint")!=null) {
            endpoints_array
                = m_config.getProperty("corba.endpoint").split(",");
            rtcout.println(rtcout.DEBUG, 
                "corba.endpoint: "+m_config.getProperty("corba.endpoint"));
        }
System.out.println("endpoints_array.length>:"+endpoints_array.length);
        for(int ic=0;ic<endpoints_array.length;++ic){
System.out.println("endpoints_array[ic]>:"+endpoints_array[ic]);
            endpoints.add(endpoints_array[ic]);
        }
System.out.println("endpoints>:"+endpoints);
        // If this process has master manager,
        // master manager's endpoint inserted at the top of endpoints
        rtcout.println(rtcout.DEBUG, 
            "manager.is_master: "+m_config.getProperty("manager.is_master"));

        if(StringUtil.toBool(m_config.getProperty("manager.is_master"), 
                                                        "YES", "NO", false)){
            String  mm = m_config.getProperty("corba.master_manager", ":2810");
System.out.println("mm>:"+mm);
            String[] mmm = mm.split(":");
System.out.println("mmm.length>:"+mmm.length);
for(int ic=0;ic<mmm.length;++ic){
System.out.println("mmm[ic]>:"+mmm[ic]);
}
            if (mmm.length == 2) {
                endpoints.add(0, ":" + mmm[1]);
            }
            else {
                endpoints.add(0, ":2810");
            }
        }
System.out.println("endpoints>:"+endpoints);
System.out.println("OUT createORBEndpoints");
    }

    /**
     * {@.ja ORB の Endpoint のコマンドラインオプション作成}
     * {@.en Create a command optional line of Endpoint of ORB.}
     * 
     * @param opt 
     *   {@.ja コマンドラインオプション}
     *   {@.en ORB options}
     * @param endpoints
     *   {@.ja エンドポイントリスト}
     *   {@.en Endpoints list}
     */
    protected void createORBEndpointOption(String opt, 
                                            Vector<String> endpoints) {
System.out.println("IN  createORBEndpointOption>:"+opt+","+endpoints);
        String corba = m_config.getProperty("corba.id");
System.out.println("    corba.id>:"+corba);
        rtcout.println(rtcout.DEBUG, "corba.id: "+corba);

        for (int i=0; i < endpoints.size(); ++i) {
            String endpoint = endpoints.elementAt(i);
            rtcout.println(rtcout.DEBUG, "Endpoint is : "+endpoint);
            if (endpoint.indexOf(":") == -1) {
                endpoint += ":"; 
            }

            if (corba.equals("omniORB")) {
                endpoint = StringUtil.normalize(endpoint);
                if (StringUtil.normalize(endpoint).equals("all:")) {
                    // omniORB 4.1 or later
                    opt += " -ORBendPointPublish all(addr)";
                }
                else{
                    opt += " -ORBendPoint giop:tcp:" + endpoint;
                }
            }
            else if (corba == "TAO") {
                opt += "-ORBEndPoint iiop://" + endpoint;
            }
            else if (corba == "MICO") {
                opt += "-ORBIIOPAddr inet:" + endpoint;
            }
        }
System.out.println("OUT createORBEndpointOption");
    }

    /**
     * {@.jp "corba.endpoints" を分析してエンドポイントを 
     *       IiopAddressComp の listへ出力する。}
     * @param endpoint
     *   {@.jp }
     * @param result
     *   {@.jp }
     */
     private void parsesCorbaEndpointOutputToList(String endpoint ,
                                      java.util.ArrayList result){

        if(endpoint != null && (endpoint.indexOf(":")>=0))  {
            String[] endPointInfo = endpoint.split(":");
            if( !endPointInfo[0].equals("") ) {
            }
            else{
                rtcout.println(rtcout.WARN, 
                    "Host of corba.endpoints is illegal." +endPointInfo[0]);
                return;
            }
            short port = 0;
            if( endPointInfo.length>1 ) {
                try {
                    port = (short)Integer.parseInt(endPointInfo[1]);
                }
                catch(Exception ex){
                    rtcout.println(rtcout.WARN, 
                        "Port of corba.endpoints is illegal." +endPointInfo[1]);
System.out.println("Port of corba.endpoints is illegal." +endPointInfo[1]);
                }
            }
            IiopAddressComp comp = new IiopAddressComp(endPointInfo[0],port);
            result.add(comp);
        }
    }
    /**
     * {@.jp "corba.endpoint" を分析してエンドポイントを 
     *        Map へ出力する。}
     * {@.en Analyzes "corba.endpoint" and outputs the end point to Map.}
     *
     */
     private void parsesCorbaEndpoint(String endpoint ,
                                      java.util.Map result){

        if(endpoint != null && (endpoint.indexOf(":")>=0))  {
            String[] endPointInfo = endpoint.split(":");
            if( !endPointInfo[0].equals("") ) {
//                result.put("org.omg.CORBA.ORBInitialHost", endPointInfo[0]);
                result.put("com.sun.CORBA.ORBServerHost", endPointInfo[0]);
            }
            if( endPointInfo.length>1 ) {
                try {
                    short port = (short)Integer.parseInt(endPointInfo[1]);
//                    result.put("org.omg.CORBA.ORBInitialPort", endPointInfo[1]);
                    result.put("com.sun.CORBA.ORBServerPort", endPointInfo[1]);
System.out.println("com.sun.CORBA.ORBServerPort"+", "+endPointInfo[1]);
                }
                catch(Exception ex){
                    rtcout.println(rtcout.WARN, ""+endPointInfo[1]);
                }
            }
        }
    }
    /**
     * {@.ja プロパティの生成.}
     * {@.en Creates ORB Properties.}
     * @return java.util.Properties
     *   {@.ja  ORB.init() のプロパティ}
     *   {@.en  Property of ORB.init().}
     *
     */
    protected java.util.Properties createORBProperties() {
        java.util.Properties result = new java.util.Properties();

        //Registers Initializers.
        result.put("org.omg.PortableInterceptor.ORBInitializerClass.jp.go.aist.rtm.RTC.InterceptorInitializer","");

        //Parses "corba.endpoint".
        String endpoint = m_config.getProperty("corba.endpoint");
        parsesCorbaEndpoint(endpoint, result);

        //Parses "corba.endpoints".
        // Multiple endpoint addresses and ports can be specified using 
        // this option.
        //  Example:
        //   corba.endpoints: 192.168.1.10:1111, 192.168.10.11:2222
        //   corba.endpoints: 192.168.1.10:, 192.168.10.11:
        String endpoints = m_config.getProperty("corba.endpoints");
        if(endpoints != null) {
            endpoints = endpoints.trim();
            endpoints = StringUtil.normalize(endpoints);
            if(endpoints.equals("all")){
                try{
                    java.util.Enumeration<java.net.NetworkInterface> nic 
                         = java.net.NetworkInterface.getNetworkInterfaces();
                    endpoints = new String();
                    while(nic.hasMoreElements()) {
                        java.net.NetworkInterface netIf = nic.nextElement();
                        java.util.Enumeration<java.net.InetAddress> enumAddress 
                                = netIf.getInetAddresses();
                        while(enumAddress.hasMoreElements()){
                            java.net.InetAddress inetAdd 
                                = enumAddress.nextElement();
                            String hostString = inetAdd.getHostAddress();
                            if(isIpAddressFormat(hostString)){
                                if(endpoints.length()!=0){
                                    endpoints 
                                        = endpoints + "," + hostString + ":";
                                }
                                else{
                                    endpoints = hostString + ":";
                                }
                            }
                        }
                    }
                }
                catch(Exception ex){
                }
                if(endpoints == null) {
                    return result;
                }
            }
            java.util.ArrayList<IiopAddressComp> endpointsList = 
                    new java.util.ArrayList<IiopAddressComp>();
            if(endpoints.indexOf(",")!=-1){
                String[] endPoints = endpoints.split(",");
                int loopstart = 0;
                for(int ic=loopstart;ic<endPoints.length;++ic) {
                    if(result.getProperty("com.sun.CORBA.ORBServerHost")==null){
                        parsesCorbaEndpoint(endPoints[ic], result);
                    }
                    else{
                        parsesCorbaEndpointOutputToList(endPoints[ic], 
                                                    endpointsList);
                    }
                }
                IopIorInterceptor.setEndpoints(endpointsList);
            
            }
            else{
                if(result.getProperty("com.sun.CORBA.ORBServerHost")==null){
                    parsesCorbaEndpoint(endpoints, result);
                }
                else {
                    parsesCorbaEndpointOutputToList(endpoints, endpointsList);
                }
            }
            IopIorInterceptor.setEndpoints(endpointsList);
        }


        return result;
    }
    
    /**
     * {@.en Checks that the string is IPaddress. }
     */
    private boolean isIpAddressFormat(String string){
        java.util.regex.Pattern pattern 
            = java.util.regex.Pattern.compile(
               "(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})");
        java.util.regex.Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    /**
     * <p>ORBの終了処理を行います。</p>
     */
    protected void shutdownORB() {
        
        rtcout.println(rtcout.TRACE, "Manager.shutdownORB()");
        
        try {
            while (m_pORB.work_pending()) {
                rtcout.println(rtcout.PARANOID, "Pending work still exists.");
                
                if (m_pORB.work_pending()) {
                    m_pORB.perform_work();
                }
            }
        } catch (Exception e) {
            rtcout.println(rtcout.DEBUG, "Exception: Caught unknown Exception in Manager.shutdownORB().");
            rtcout.println(rtcout.DEBUG, e.getMessage());
        }
        
        rtcout.println(rtcout.DEBUG, "No pending works of ORB. Shutting down POA and ORB.");

        if (m_pPOA != null) {
            try {
                if (m_pPOAManager != null) {
                    m_pPOAManager.deactivate(false, true);
                    rtcout.println(rtcout.DEBUG, "POA Manager was deactivated.");
                }
                
                m_pPOA = null;
                
//                rtcout.println(rtcout.DEBUG, "POA was destroid.");
                
            } catch (SystemException ex) {
                rtcout.println(rtcout.ERROR, "Caught SystemException during root POA destruction");
                
            } catch (Exception ex) {
                rtcout.println(rtcout.ERROR, "Caught unknown exception during POA destruction.");
            }
        }

        if (m_pORB != null) {
            try {
                m_pORB.shutdown(true);
                
                rtcout.println(rtcout.DEBUG, "ORB was shutdown.");
                rtcout.println(rtcout.DEBUG, "ORB was destroied.");
                
                m_pORB.destroy();
                m_pORB = null;
                ORBUtil.clearOrb();
                
            } catch (SystemException ex) {
                rtcout.println(rtcout.ERROR, "Caught SystemException during ORB shutdown");
                
            } catch (Exception ex) {
                rtcout.println(rtcout.ERROR, "Caught unknown exception during ORB shutdown.");
            }
        }
    }
    
    /**
     * <p>NamingManagerを初期化します。</p>
     */
    protected boolean initNaming() {
        
        rtcout.println(rtcout.TRACE, "Manager.initNaming()");
        
        m_namingManager = new NamingManager(this);

        // If NameService is disabled, return immediately
        if (! StringUtil.toBool(m_config.getProperty("naming.enable"), "YES", "NO", true)) {
            return true;
        }

        // NameServer registration for each method and servers
        String[] meth = m_config.getProperty("naming.type").split(",");

        for (int i = 0; i < meth.length; ++i) {
            String names[] = m_config.getProperty(meth[i] + ".nameservers").split(",");

            for (int j = 0; j < names.length; ++j) {
                rtcout.println(rtcout.TRACE, 
                    "Register Naming Server: " + meth[i] + " " + names[j]);
                
                String[] nameServer = names[j].split(":");
                if (nameServer.length == 1 && !nameServer[0].equals("")) {
                    names[j] += ":2809";
                }
                
                if (!names[j].equals("")) {
                    m_namingManager.registerNameServer(meth[i], names[j]);
                }
            }
        }

        // NamingManager Timer update initialization
        if (StringUtil.toBool(m_config.getProperty("naming.update.enable"), "YES", "NO", true)) {
            TimeValue tm = new TimeValue(10, 0); // default interval = 10sec
                                                  // for safty
            
            String intr = m_config.getProperty("naming.update.interval");
            if (! (intr == null || intr.equals(""))) {
                tm.convert(Double.valueOf(intr).doubleValue());
            }
            
            if (m_timer != null) {
                m_timer.registerListenerObj(m_namingManager, tm);
            }
        }
        
        return true;
    }
    
    /**
     * <p>NamingManagerの終了処理を行います。</p>
     */
    protected void shutdownNaming() {
        
        rtcout.println(rtcout.TRACE, "Manager.shutdownNaming()");
        
        m_namingManager.unbindAll();
    }
    
    /**
     * <p>ネーミングサービスに登録されているコンポーネントの終了処理を行います。</p>
     */
    protected void shutdownComponents() {
        
        rtcout.println(rtcout.TRACE, "Manager.shutdownComponents()");
        
        Vector<RTObject_impl> comps = m_namingManager.getObjects();
        for (int i=0, len=comps.size(); i < len; ++i) {
            try {
                comps.elementAt(i).exit();
                Properties p = new Properties(comps.elementAt(i).getInstanceName());
                p.merge(comps.elementAt(i).getProperties());
                
                rtcout.level(Logbuf.PARANOID);

            } catch (Exception e) {
//                e.printStackTrace();
            }
        }
        
        for (int i=0, len=m_ecs.size(); i < len; ++i) {
            try {
                m_pPOA.deactivate_object(m_pPOA.servant_to_id(m_ecs.elementAt(i)));
                
            } catch (Exception e) {
                rtcout.println(rtcout.DEBUG, "Exception: Caught unknown Exception in Manager.shutdownComponents().");
                rtcout.println(rtcout.DEBUG, e.getMessage());
            }
        }
    }
    
    /**
     * <p>RTコンポーネントのコフィグレーション設定を行います。</p>
     * 
     * @param comp コンフィグレーション設定対象のRTコンポーネント
     */
    protected void configureComponent(RTObject_impl comp, final Properties prop ) {
        
        String category = comp.getCategory();
        String type_name = comp.getTypeName();
        String inst_name = comp.getInstanceName();
        
        String type_conf = category + "." + type_name + ".config_file";
        String name_conf = category + "." + inst_name + ".config_file";
        
        Properties type_prop = new Properties();
        Properties name_prop = new Properties();
        
        // Load "category.instance_name.config_file"
        if (!(m_config.getProperty(name_conf) == null
                || m_config.getProperty(name_conf).length() == 0)) {
            
            try {
                BufferedReader conff = new BufferedReader(
                        new FileReader(m_config.getProperty(name_conf)));
                name_prop.load(conff);
                
            } catch (FileNotFoundException e) {
                rtcout.println(rtcout.DEBUG, "Exception: Caught FileNotFoundException in Manager.configureComponent() name_conf.");
                rtcout.println(rtcout.DEBUG, e.getMessage());
                
            } catch (Exception e) {
                rtcout.println(rtcout.DEBUG, "Exception: Caught unknown in Manager.configureComponent() name_conf.");
                rtcout.println(rtcout.DEBUG, e.getMessage());
            }
        }

        if (!(m_config.getProperty(type_conf) == null
                || m_config.getProperty(type_conf).length() == 0)) {
            
            try {
                BufferedReader conff = new BufferedReader(
                        new FileReader(m_config.getProperty(type_conf)));
                type_prop.load(conff);
                
            } catch (FileNotFoundException e) {
                rtcout.println(rtcout.DEBUG, "Exception: Caught FileNotFoundException in Manager.configureComponent() type_conf.");
                rtcout.println(rtcout.DEBUG, e.getMessage());
                e.printStackTrace();
                
            } catch (Exception e) {
                rtcout.println(rtcout.DEBUG, "Exception: Caught unknown Exception in Manager.configureComponent() type_conf.");
                rtcout.println(rtcout.DEBUG, e.getMessage());
                e.printStackTrace();
            }
        }

        // Merge Properties. type_prop is merged properties
        comp.getProperties().merge(prop);
        type_prop.merge(name_prop);
        comp.getProperties().merge(type_prop);

        // ------------------------------------------------------------
        // Format component's name for NameService
        StringBuffer naming_formats = new StringBuffer();
        Properties comp_prop = comp.getProperties();

        naming_formats.append(m_config.getProperty("naming.formats"));
        naming_formats.append(", " + comp_prop.getProperty("naming.formats"));
        String naming_formats_result = StringUtil.flatten(
                StringUtil.unique_sv(naming_formats.toString().split(",")));

        comp.getProperties().setProperty("naming.formats", naming_formats.toString());
        String naming_names = this.formatString(naming_formats_result, comp.getProperties());
        comp.getProperties().setProperty("naming.names", naming_names);
    }
    
    /**
     * <p>ExecutionContextを初期化します。</p>
     * 
     * @return 正常に初期化できた場合はtrueを、さもなくばfalseを返します。
     */
    protected boolean initExecContext() {
        
        rtcout.println(rtcout.TRACE, "Manager.initExecContext()");
        
        PeriodicExecutionContext.PeriodicExecutionContextInit(this);
        ExtTrigExecutionContext.ExtTrigExecutionContextInit(this);
        OpenHRPExecutionContext.OpenHRPExecutionContextInit(this);
        
        return true;
    }
    
    /**
     * <p> intiComposite </p>
     *
     * @return boolan
     */
    protected boolean initComposite() {
        rtcout.println(rtcout.TRACE, "Manager.initComposite()");
        PeriodicECSharedComposite.PeriodicECSharedCompositeInit(this);

        return true;
    }

    /**
     * <p> intiFactories </p>
     *
     * @return boolan
     */
    protected boolean initFactories() {
        rtcout.println(rtcout.TRACE, "Manager.initFactories()");
        FactoryInit.init();
        return true;
    }

    /**
     * <p>Timerを初期化します。</p>
     */
    protected boolean initTimer() {
        return true;
    }
    
    /**
     * <p> initManagerServant </p>
     *
     * @return boolean
     *
     */
    protected boolean initManagerServant() {
        m_mgrservant = new ManagerServant();
        return true;
    }
    
    /**
     * <p> bindManagerServant </p>
     *
     * @return boolean
     *
     */
    protected boolean bindManagerServant() {

        if( m_mgrservant == null) {
            rtcout.println(rtcout.ERROR, "ManagerServant is not created.");
            return false;
        }

        Properties prop = (m_config.getNode("manager"));
        String[] names=prop.getProperty("naming_formats").split(",");

        for (int i=0; i < names.length; ++i) {
            String mgr_name = formatString(names[i], prop);
            m_namingManager.bindObject(mgr_name, m_mgrservant);
          }

        File otherref = new File(m_config.getProperty("manager.refstring_path"));
        if (!otherref.exists()) {
            try {
                FileWriter reffile = new FileWriter(otherref);
                reffile.write(m_pORB.object_to_string(m_mgrservant.getObjRef()));
                reffile.close();
            } catch (IOException e) {
            }
        }
        else {
            try{
                String refstring = new String();
                FileReader reffile = new FileReader(otherref);
                BufferedReader br = new BufferedReader(reffile); 
                String line;
                while ((line = br.readLine()) != null) {
                    refstring = refstring + line;
                }
                br.close();
                reffile.close();

                System.out.println( refstring );

                //Object obj = m_pORB.string_to_object(refstring);
                //Manager mgr = ManagerHelper.narrow(obj);
                //        if (mgr==null) return false;
                //        mgr.set_child(m_mgrservant.getObjRef());
                //        m_mgrservant.set_owner(mgr);
            } catch (IOException e) {
            }
        }

        return true;
    }

    /**
     * ManagerServant
     */
    ManagerServant m_mgrservant;

    /**
     * <p>プロパティファイルを読み込んで、指定されたPropertiesオブジェクトに設定します。</p>
     * 
     * @param properties 設定対象のPropertiesオブジェクト
     * @param fileName プロパティファイル名
     * @return 正常に設定できた場合はtrueを、さもなくばfalseを返します。
     */
    protected boolean mergeProperty(Properties properties, final String fileName) {
        
        if (fileName == null) {
            rtcout.println(rtcout.ERROR, "Invalid configuration file name.");

            return false;
        }
        
        if (! (fileName.length() == 0)) {

            try {
                BufferedReader conff = new BufferedReader(new FileReader(fileName));
                properties.load(conff);
                conff.close();
                
                return true;

            } catch (FileNotFoundException e) {
                rtcout.println(rtcout.DEBUG, "Exception: Caught FileNotFoundException in Manager.mergeProperty().");
                rtcout.println(rtcout.DEBUG, e.getMessage());
                e.printStackTrace();

            } catch (Exception e) {
                rtcout.println(rtcout.DEBUG, "Exception: Caught unknown Exception in Manager.mergeProperty().");
                rtcout.println(rtcout.DEBUG, e.getMessage());
                e.printStackTrace();
            }
        }
        
        return false;
    }
    
    /**
     * <p>指定されたPropertiesオブジェクトの内容を、指定された書式に従って文字列として出力します。</p>
     * 
     * @param namingFormat 書式指定
     * @param properties 出力対象となるPropertiesオブジェクト
     * @return Propertiesオブジェクトの内容を文字列出力したもの
     */
    protected String formatString(final String namingFormat, Properties properties) {
        
        StringBuffer str = new StringBuffer();
        int count = 0;

        for (int i = 0; i < namingFormat.length(); ++i) {
            char c = namingFormat.charAt(i);
            if (c == '%') {
                ++count;
                if ((count % 2) == 0) {
                    str.append(c);
                }
            } else if (c == '$') {
                count = 0;
                ++i;
                if (namingFormat.charAt(i) == '{' || namingFormat.charAt(i) == '(') {
                    ++i;
                    String env = "";
                    for ( ; i < namingFormat.length() && namingFormat.charAt(i) != '}' && namingFormat.charAt(i) != ')'; ++i) {
                        env += namingFormat.charAt(i);
                    }
                    String envval = System.getenv(env);
                    if (envval != null) str.append(envval);
                } else {
                    str.append(c);
                }
            } else {
                if (count > 0 && (count % 2) != 0) {
                    count = 0;
                    if (c == 'n') {
                        str.append(properties.getProperty("instance_name"));
                    }
                    else if (c == 't') {
                        str.append(properties.getProperty("type_name"));
                    }
                    else if (c == 'm') {
                        str.append(properties.getProperty("type_name"));
                    }
                    else if (c == 'v') {
                        str.append(properties.getProperty("version"));
                    }
                    else if (c == 'V') {
                        str.append(properties.getProperty("vendor"));
                    }
                    else if (c == 'c') {
                        str.append(properties.getProperty("category"));
                    }
                    else if (c == 'h') {
                        str.append(m_config.getProperty("manager.os.hostname"));
                    }
                    else if (c == 'M') {
                        str.append(m_config.getProperty("manager.name"));
                    }
                    else if (c == 'p') {
                        str.append(m_config.getProperty("manager.pid"));
                    }
                    else {
                        str.append(c);
                    }
                } else {
                    count = 0;
                    str.append(c);
                }
            }
        }
        
        return str.toString();
    }
    
    /**
     * <p>唯一のManagerインスタンスです。</p>
     */
    protected static Manager manager;
    /**
     * <p>Manager用ミューテックス変数です。</p>
     */
    protected static String manager_mutex = new String();
    /**
     * <p>ORB</p>
     */
    protected ORB m_pORB;
    /**
     * <p>POA</p>
     */
    protected POA m_pPOA;
    /**
     * <p>POAManager</p>
     */
    protected POAManager m_pPOAManager;
    
    /**
     * <p>ユーザコンポーネント初期化プロシジャオブジェクト</p>
     */
    protected ModuleInitProc m_initProc;
    /**
     * <p>Managerコンフィギュレーション</p>
     */
    protected Properties m_config = new Properties();
    /**
     * <p>Module Manager</p>
     */
    protected ModuleManager m_module;
    /**
     * <p>Naming Manager</p>
     */
    protected NamingManager m_namingManager;
    /**
     * <p>CORBA Object Manager</p>
     */
    protected CorbaObjectManager m_objManager;
    /**
     * <p>Timer</p>
     */
    protected Timer m_timer;
    /**
     * <p>ロガーストリーム</p>
     */
    protected Logbuf rtcout;
    
    /**
     * <p>Object検索用ヘルパークラスです。</p>
     */
    protected class InstanceName implements equalFunctor {
        
        public InstanceName(RTObject_impl comp) {
            m_name = comp.getInstanceName();
        }
        
        public InstanceName(final String name) {
            m_name = name;
        }
        
        public boolean equalof(java.lang.Object comp) {
            return m_name.equals(((RTObject_impl)comp).getInstanceName());
        }
        
        public String m_name;
    }
    
    /**
     * <p>Component Manager</p>
     */
    protected ObjectManager<String, RTObject_impl> m_compManager = new ObjectManager<String, RTObject_impl>();
    
    /**
     * <p>Factory検索用ヘルパークラスです。</p>
     */
    protected class FactoryPredicate implements equalFunctor {
        
        public FactoryPredicate(final String name) {
            m_name = name;
        }
        
        public FactoryPredicate(FactoryBase factory) {
            m_name = factory.profile().getProperty("implementation_id");
        }
        
        public boolean equalof(java.lang.Object factory) {
            return m_name.equals(((FactoryBase)factory).profile().getProperty("implementation_id"));
        }
        
        public String m_name;
    }
    
    /**
     * <p>Component Factory Manager</p>
     */
    protected ObjectManager<String, FactoryBase> m_factory = new ObjectManager<String, FactoryBase>();
    
    /**
     * <p>ECFactory検索用ヘルパークラスです。</p>
     */
    class ECFactoryPredicate implements equalFunctor {
        
        public ECFactoryPredicate(final String name) {
            m_name = name;
        }
        
        public ECFactoryPredicate(ECFactoryBase factory) {
            m_name = factory.name();
        }
        
        public boolean equalof(java.lang.Object factory) {
            return m_name.equals(((ECFactoryBase)factory).name());
        }
        
        public String m_name;
    }
    
    /**
     * <p>ExecutionContext Factory</p>
     */
    protected ObjectManager<String, java.lang.Object> m_ecfactory = new ObjectManager<String, java.lang.Object>();
    /**
     * <p>ExecutionContext</p>
     */
    protected Vector<ExecutionContextBase> m_ecs = new Vector<ExecutionContextBase>();
    /**
     * <p>ORB実行用ヘルパークラスです。</p>
     */
    protected class OrbRunner implements Runnable {

        public OrbRunner(ORB orb) {
            m_pORB = orb;
//            this.open("");
        }

        public int open(String args) {
            // activate();
            Thread t = new Thread(this);
            t.start();
            return 0;
        }

        public int svc() {
            m_pORB.run();
//            Manager.instance().shutdown();
            return 0;
        }

        public int close(long flags) {
            return 0;
        }

        public void run() {
            this.svc();
        }

        private ORB m_pORB;
    }
    /**
     * <p>ORB Runner</p>
     */
    protected OrbRunner m_runner;
    
    /**
     * <p>終了処理用ヘルパークラスです。</p>
     */
    protected class Terminator implements Runnable {

        public Terminator(Manager manager) {
            m_manager = manager;
        }
        
        public void terminate() {
            this.open("");
        }
        
        public int open(String args) {
//            activate();
            Thread t = new Thread(this);
            t.start();
            return 0;
        }
        
        public int svc() {
            Manager.instance().shutdown();
            return 0;
        }
        
        public void run() {
            this.svc();
        }
        
        public Manager m_manager;
    }

    /**
     * <p>Terminator</p>
     */
    protected Terminator m_terminator;
    /**
     * <p>Terminator用カウンタ</p>
     */
    protected int m_terminate_waiting;
}
