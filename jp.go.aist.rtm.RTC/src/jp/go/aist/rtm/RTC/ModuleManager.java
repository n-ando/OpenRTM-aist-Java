package jp.go.aist.rtm.RTC;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.StringUtil;



/**
* <p>モジュール管理クラスです。モジュールのロード・アンロードなどを管理します。</p>
*/
public class ModuleManager {
    
    private final String CONFIG_EXT = "manager.modules.config_ext";
    private final String CONFIG_PATH = "manager.modules.config_path";
    private final String DETECT_MOD = "manager.modules.detect_loadable";
    private final String MOD_LOADPTH = "manager.modules.load_path";
    private final String INITFUNC_SFX = "manager.modules.init_func_suffix";
    private final String INITFUNC_PFX = "manager.modules.init_func_prefix";
    private final String ALLOW_ABSPATH = "manager.modules.abs_path_allowed";
    private final String ALLOW_URL = "manager.modules.download_allowed";
    private final String MOD_DWNDIR = "manager.modules.download_dir";
    private final String MOD_DELMOD = "manager.modules.download_cleanup";
    private final String MOD_PRELOAD = "manager.modules.preload";

    /**
     * <p>コンストラクタです。
     * 指定されたPropertiesオブジェクト内の情報に基づいて初期化を行います。</p>
     * 
     * @param properties 初期化情報を持つPropertiesオブジェクト
     */
    public ModuleManager(Properties properties) {
        
        m_properties = properties;
        
        m_configPath = new Vector<String>();
        String[] configPath = properties.getProperty(CONFIG_PATH).split(",");
        for (int i = 0; i < configPath.length; i++) {
            m_configPath.add(configPath[i].trim());
        }
        
        m_loadPath = new Vector<String>();
        String[] loadPath = properties.getProperty(MOD_LOADPTH).split(",");
        for (int i = 0; i < configPath.length; i++) {
            m_loadPath.add(loadPath[i].trim());
        }
        
        m_absoluteAllowed = StringUtil.toBool(
                properties.getProperty(ALLOW_ABSPATH), "yes", "no", false);
        m_downloadAllowed = StringUtil.toBool(
                properties.getProperty(ALLOW_URL), "yes", "no", false);
        
        m_initFuncSuffix = properties.getProperty(INITFUNC_SFX);
        m_initFuncPrefix = properties.getProperty(INITFUNC_PFX);
    }
    
    /**
     * <p>デストラクタです。ロード済みモジュールのアンロードなど、リソースの解放処理を行います。
     * 当該ModuleManagerオブジェクトの使用を終えた際に、明示的に呼び出してください。</p>
     */
    public void destruct() {
        unloadAll();
    }
    
    /**
     * <p>ファイナライザです。</p>
     */
    protected void finalize() throws Throwable {
        
        try {
            destruct();
            
        } finally {
            super.finalize();
        }
    }

    /**
     * 
     */
    public String load(final String moduleName) throws Exception {
System.out.println( "ModuleManager::load--000--");
        String module_path = null;

        if(moduleName==null || moduleName.length()==0) {
            throw new IllegalArgumentException("moduleName is empty.:load()");
        }

        try {
            new URL(moduleName);
            if (! m_downloadAllowed) {
                throw new IllegalArgumentException("Downloading module is not allowed.");
            } else {
                throw new IllegalArgumentException("Not implemented." + moduleName);
            }
        } catch (MalformedURLException moduleName_is_not_URL) {
        }

        // Find local file from load path or absolute directory
        Class target = null;
        if (m_absoluteAllowed) {
            try {
                target = Class.forName(moduleName);
                module_path = moduleName;
            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
                throw new ClassNotFoundException("Not implemented." + moduleName, e);
            }
        } else {
            if( m_loadPath.size()==0 ) throw new ClassNotFoundException();
            for (int i = 0; i < m_loadPath.size(); i++) {
                String fullClassName = m_loadPath.elementAt(i) + "." + moduleName;
                try {
                    target = Class.forName(fullClassName);
                    module_path = fullClassName;                    
                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
                }
            }
            if( target==null ) throw new ClassNotFoundException("Not implemented." + moduleName);
        }
System.out.println( "ModuleManager::load--090--");
        m_modules.put(module_path, target);
System.out.println( "ModuleManager::load--0e0--");
        return module_path;
    }

    /**
     * <p>指定されたモジュールをロードします。初期化メソッドを指定した場合には、
     * ロード時にそのメソッドが呼び出されます。これにより、モジュール初期化を行えます。</p>
     * 
     * <p>コンストラクタで指定した初期化情報の 'manager.modules.abs_path_allowed' が 'yes' の場合は、
     * className引数は、ロードモジュールのフルクラス名として解釈されます。<br />
     * 'no' が指定されている場合は、className引数はロードモジュールのシンプルクラス名として解釈され、
     * 規定のモジュールロードパス以下からモジュールが検索されます。</p>
     * 
     * <p>コンストラクタで指定した初期化情報の 'manager.modules.download_allowed' が 'yes' の場合は、
     * className引数は、ロードモジュールのURLとして解釈されます。（未実装）</p>
     * 
     * @param moduleName モジュール名
     * @param methodName 初期実行メソッド名
     * @return moduleName引数で指定したモジュール名がそのまま返されます。
     * @throws IllegalArgumentException 引数が正しく指定されていない場合にスローされます。
     */
    public String load(final String moduleName, final String methodName)
            throws Exception {
        
System.out.println( "ModuleManager::load--000--");
        if (moduleName == null || moduleName.length() == 0) {
System.out.println( "ModuleManager::load--0r0--");
            throw new IllegalArgumentException("moduleName is empty.:load()");
        }
        if (methodName == null || methodName.length() == 0) {
System.out.println( "ModuleManager::load--0r1--");
            throw new IllegalArgumentException("methodName is empty.:load()");
        }

        String module_path = null;
        try {
System.out.println( "ModuleManager::load--001--moduleName:" + moduleName);
            new URL(moduleName);
            if (! m_downloadAllowed) {
System.out.println( "ModuleManager::load--0r1--");
                throw new IllegalArgumentException("Downloading module is not allowed.");
                
            } else {
                throw new IllegalArgumentException("Not implemented.");
            }
        } catch (MalformedURLException moduleName_is_not_URL) {
System.out.println( "ModuleManager::load--0r2--");
        }

        Class target = null;
        if (m_absoluteAllowed) {
            try {
System.out.println( "ModuleManager::load--01?--moduleName:" + moduleName);
                target = Class.forName(moduleName);
System.out.println( "ModuleManager::load--011--moduleName:" + moduleName);
                module_path = moduleName;
            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
System.out.println( "ModuleManager::load--0r3--");
                throw new ClassNotFoundException("Not implemented.", e);
            }
        } else {
System.out.println( "ModuleManager::load--010--m_loadPath.size:" + m_loadPath.size());
            if( m_loadPath.size()==0 ) throw new ClassNotFoundException();
            for (int i = 0; i < m_loadPath.size(); i++) {
                String fullClassName = m_loadPath.elementAt(i) + "." + moduleName ;
                try {
System.out.println( "ModuleManager::load--020--fullClassName:" + fullClassName);
                    target = Class.forName(fullClassName);
                    module_path = fullClassName;
                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
                    // throw new ClassNotFoundException("Not implemented.", ex);
System.out.println( "ModuleManager::load--0r4--");
                }
            }
        }

        Method initMethod;
        try {
System.out.println( "ModuleManager::load--030--methodName:" + methodName);
if(target == null ) {
System.out.println( "ModuleManager::load--030--target is null");
} else {
System.out.println( "ModuleManager::load--030--target is not null");
}
            initMethod = target.getMethod(methodName);
System.out.println( "ModuleManager::load--040--");
            
        } catch (SecurityException e) {
//            e.printStackTrace();
System.out.println( "ModuleManager::load--0r5--");
            throw e;
            
        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
System.out.println( "ModuleManager::load--0r6--");
            throw e;
        }

        try {
System.out.println( "ModuleManager::load--050--");
            initMethod.invoke(target.newInstance());
System.out.println( "ModuleManager::load--060--");
        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
System.out.println( "ModuleManager::load--0r7--");
            throw e;
        } catch (IllegalAccessException e) {
//            e.printStackTrace();
System.out.println( "ModuleManager::load--0r8--");
            throw e;
        } catch (InvocationTargetException e) {
//            e.printStackTrace();
System.out.println( "ModuleManager::load--0r9--");
            throw e;
        } catch (InstantiationException e) {
//            e.printStackTrace();
System.out.println( "ModuleManager::load--0ra--");
            throw e;
        }
System.out.println( "ModuleManager::load--090--");
        m_modules.put(module_path, target);
System.out.println( "ModuleManager::load--0e0--");
        
        return module_path;
    }
    
    /**
     * <p>指定されたモジュールをアンロードします。</p>
     * 
     * @param moduleName アンロードするモジュール名
     */
    public void unload(String moduleName) throws Exception {
        if( !m_modules.containsKey(moduleName) ) 
            throw new IllegalArgumentException("Not Found:" + moduleName);
        m_modules.remove(moduleName);
    }
    
    /**
     * <p>すべてのモジュールをアンロードします。</p>
     */
    public void unloadAll() {
        m_modules = new HashMap<String, Class>();
    }
    
    /**
     * <p>モジュールのメソッドの参照。</p>
     */
    public Method symbol(String class_name, String method_name) throws Exception {
        Class target = m_modules.get(class_name);
        if( target==null ) 
            throw new IllegalArgumentException("Not Found(symbol):" + class_name);
        //
        Method initMethod;
        try {
            initMethod = target.getMethod(method_name);
        } catch (SecurityException e) {
//            e.printStackTrace();
            throw e;
        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
            throw e;
        }
        
        return initMethod;
    }
    /**
     * <p>初期化関数シンボルを生成する</p>
     */
    public String getInitFuncName(String class_path) {
        if( class_path==null || class_path.length()==0 ) return null;
        String base_names[] = class_path.split("\\.");

        return m_initFuncPrefix + base_names[base_names.length-1] + m_initFuncSuffix;
    }

    /**
     * <p>規定となるモジュールロードパスを指定します。</p>
     * 
     * @param loadPath 規定ロードパス
     */
    public void setLoadpath(final Vector<String> loadPath) {
        m_loadPath = new Vector<String>(loadPath);
    }
    
    /**
    * <p>規定となるモジュールロードパスを取得します。</p>
    *
    * @return 規定モジュールロードパス
    */
    public Vector<String> getLoadPath() {
        return new Vector<String>(m_loadPath);
    }

    /**
     * <p>規定となるモジュールロードパスを追加します。</p>
     * 
     * @param loadPath 追加する規定ロードパス
     */
    public void addLoadPath(final Vector<String> loadPath) {
        m_loadPath.addAll(loadPath);
    }
    
    /**
     * <p>ロード済みのモジュールリストを取得します。</p>
     *
     * @return ロード済みモジュールリスト
     */
    public Map<String, Class> getLoadedModules() {
        return m_modules;
    }
        
    /**
     * <p>ロード可能なモジュールリストを取得します。</p>
     * 
     * @return ロード可能なモジュールリスト
     */
    public Vector<Properties> getLoadableModules() {
        return new Vector<Properties>();
    }
    
    /**
     * <p>モジュールのフルクラス名指定を指定します。</p>
     */
    public void allowAbsolutePath() {
System.out.println( "ModuleManager::allowAbsolutePath");
        m_absoluteAllowed = true;
    }
    
    /**
     * <p>モジュールのフルクラス名指定解除を指定します。</p>
     */
    public void disallowAbsolutePath() {
System.out.println( "ModuleManager::disallowAbsolutePath");
        m_absoluteAllowed = false;
    }
    
    /**
     * <p>モジュールのダウンロード許可を指定します。</p>
     */
    public void allowModuleDownload() {
        m_downloadAllowed = true;
    }
    
    /**
     * <p>モジュールのダウンロード許可を解除します。</p>
     */
    public void disallowModuleDownload() {
        m_downloadAllowed = false;
    }
    
    /**
     * <p>ModuleManagerプロパティ</p>
     */
    protected Properties m_properties;
    /**
     * <p>ロード済みモジュール</p>
     */
    protected Map<String, Class> m_modules = new HashMap<String, Class>();
    /**
     * <p>モジュールロードパス</p>
     */
    protected Vector<String> m_loadPath = new Vector<String>();
    /**
     * <p>コンフィギュレーションパス</p>
     */
    protected Vector<String> m_configPath = new Vector<String>();
    /**
     * <p>モジュールダウンロード許可フラグ</p>
     */
    protected boolean m_downloadAllowed;
    /**
     * <p>モジュール絶対パス指定許可フラグ</p>
     */
    protected boolean m_absoluteAllowed;
    /**
     * <p>初期実行関数サフィックス</p>
     */
    protected String m_initFuncSuffix = new String();
    /**
     * <p>初期実行関数プリフィックス</p>
     */
    protected String m_initFuncPrefix = new String();
}
