package jp.go.aist.rtm.RTC;

import org.omg.CORBA.portable.Streamable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.io.File;
import java.net.URI;
import java.net.URLClassLoader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.Set;
import java.util.Iterator;

import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.StringUtil;
import jp.go.aist.rtm.RTC.util.StringHolder;

import jp.go.aist.rtm.RTC.log.Logbuf;


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
     * {@.ja コンストラクタです。}
     * {@.en Constructor}
     * <p>
     * {@.ja 指定されたPropertiesオブジェクト内の情報に基づいてi
     * 初期化を行います。}
     * {@.en Initialize based on information in the set Property object.}
     *
     * @param properties
     *   {@.ja 初期化情報を持つPropertiesオブジェクト}
     *   {@.en Properties for initialization}
     */
    public ModuleManager(Properties properties) {
        rtcout = new Logbuf("ModuleManager");
        
        m_properties = properties;
        
        m_configPath = new Vector<String>();
        String[] configPath = properties.getProperty(CONFIG_PATH).split(",");
        for (int i = 0; i < configPath.length; ++i) {
            m_configPath.add(configPath[i].trim());
        }
        
        m_loadPath = new Vector<String>();
        String[] loadPath = properties.getProperty(MOD_LOADPTH).split(",");
        String separator =  System.getProperty("file.separator");
        for (int i = 0; i < loadPath.length; ++i) {
            loadPath[i] = loadPath[i].trim();
            if(loadPath[i].substring(0,2).equals("."+separator)){
                loadPath[i] = loadPath[i].substring(2);
            }
            m_loadPath.add(loadPath[i]);
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
     * {@.ja モジュールのロード}
     * {@.en Load the module}
     *
     * <p>
     * {@.ja file_name をDLL もしくは共有ライブラリとしてロードする。
     * file_name は既定のロードパス (manager.modules.load_path) に対する
     * 相対パスで指定する。
     *
     * Property manager.modules.abs_path_allowed が yes の場合、
     * ロードするモジュールを絶対パスで指定することができる。<br>
     * Property manager.modules.download_allowed が yes の場合、
     * ロードするモジュールをURLで指定することができる。
     *
     * file_name は絶対パスで指定することができる。
     * manager.modules.allowAbsolutePath が no の場合、
     * 既定のモジュールロードパスから、file_name のモジュールを探しロードする。}
     * {@.en Load file_name as DLL or a shared liblary.
     * The file_name is specified by the relative path to default load
     * path (manager.modules.load_path).
     *
     * If Property manager.modules.abs_path_allowed is yes,
     * the load module can be specified by the absolute path.<br>
     * If Property manager.modules.download_allowed is yes,
     * the load module can be specified with URL.
     *
     * The file_name can be specified by the absolute path.
     * If manager.modules.allowAbsolutePath is no, module of file_name
     * will be searched from the default module load path and loaded.}
     * 
     * @param moduleName 
     *   {@.ja ロード対象モジュール名}
     *   {@.en The target module name for the loading}
     *
     * @return 
     *   {@.ja 指定したロード対象モジュール名}
     *   {@.en Name of module for the specified load}
     *
     *
     */
    public String load(final String moduleName) throws Exception {
        rtcout.println(rtcout.TRACE, "load(fname = " + moduleName +")");
        String module_path = null;

        if(moduleName==null || moduleName.length()==0) {
            throw new IllegalArgumentException("moduleName is empty.:load()");
        }
        try {
            new URL(moduleName);
            if (! m_downloadAllowed) {
                throw new IllegalArgumentException(
                                    "Downloading module is not allowed.");
            } else {
                throw new IllegalArgumentException(
                                            "Not implemented." + moduleName);
            }
        } catch (MalformedURLException moduleName_is_not_URL) {
            // do nothing
        }

        // Find local file from load path or absolute directory
        String separator =  System.getProperty("file.separator");
        Class target = null;

        File file = new File(moduleName);
        if(file.exists()){ // When moduleName is AbsolutePath.
            if(!m_absoluteAllowed) {
                throw new IllegalArgumentException(
                                            "Absolute path is not allowed");
            }
            else {
                URLClassLoader url = createURLClassLoader(file.getParent());
                if(url!=null){
                    String name = file.getName();
                    name = getModuleName(name);

                    StringHolder packageModuleName = new StringHolder();
                    target = getClassFromName(url,name,packageModuleName);
                    module_path = packageModuleName.value;
                }
            }
        } else {
            if( m_loadPath.size()==0 ) {
                throw new ClassNotFoundException();
            }
            for (int i = 0; i < m_loadPath.size(); ++i) {
                String fullClassName ;
                if(m_loadPath.elementAt(i).equals("")
                        ||m_loadPath.elementAt(i).length()==0){
                    fullClassName = moduleName;
                }
                else {
                    String packageName = m_loadPath.elementAt(i);
                    fullClassName = packageName + separator + moduleName;
                }
                file = new File(fullClassName);
                if(file.isAbsolute()){
                    URLClassLoader url 
                            = createURLClassLoader(file.getParent());
                    if(url!=null){
                        String name = file.getName();
                        name = getModuleName(name);

                        StringHolder packageModuleName = new StringHolder();
                        target = getClassFromName(url,name,packageModuleName);
                        module_path = packageModuleName.value;
                    }
                }
                else{
                    try {
                        fullClassName = getModuleName(fullClassName);
                        fullClassName 
                                = fullClassName.replace(separator,".");
                        fullClassName = fullClassName.replace("..",".");
                        target = Class.forName(fullClassName);
                        module_path = fullClassName;                    
                    } catch (ClassNotFoundException e) {
                        // do nothing
                    }
                }
            }
        }
        if( target==null ) {
            throw new ClassNotFoundException(
                                    "Not implemented." + moduleName);
        }
        if(module_path==null || module_path.length()==0) {
            throw new IllegalArgumentException("Invalid file name.");
        }
        DLLEntity dll_entity = new DLLEntity();
        dll_entity.properties = new Properties();
        dll_entity.properties.setProperty("file_path",module_path);
        dll_entity.dll = target;
	
        m_modules.put(module_path, dll_entity);
        return module_path;
    }

    /**
     *
     */
    private Class getClassFromName(URLClassLoader url, 
                                    String name, 
                                    StringHolder holder){
        String separator =  System.getProperty("file.separator");
        Class target = null;

        try {
            target = url.loadClass(name);
            holder.value = name;
        } catch (java.lang.NoClassDefFoundError e) {
            String messagetString = e.getMessage();
            String key = "wrong name: ";
            int index = messagetString.indexOf(key);
            String packageName 
                = messagetString.substring(index+key.length(),
                                               messagetString.length()-1);
            URL[] urls = url.getURLs();
            java.util.ArrayList al 
                    = new java.util.ArrayList(java.util.Arrays.asList(urls));
            for(int ic=0;ic<urls.length;++ic){
                String stringPath = new String();
                String stringUrl = urls[ic].getPath();
                int pointer = packageName.lastIndexOf(name);
                String stringPackageName = packageName.substring(0, pointer);
                if(stringUrl.endsWith(stringPackageName)){
                    int point = stringUrl.lastIndexOf(stringPackageName);
                    stringPath = stringUrl.substring(0, point);
                    File path = new File(stringPath);
                    try{
                        URI uri = path.toURI();
                        al.add(uri.toURL());
                    }
                    catch(java.net.MalformedURLException ex){
                        System.err.println(
                 "java.net.MalformedURLException: toURL() threw Exception."+ex);
                    }
                }
            }
            URL[] addUrls = (URL[])al.toArray(new URL[]{});
            url = url.newInstance(addUrls, url);

            packageName = packageName.replace("/",".");
            packageName = packageName.trim();

            target = getClassFromName(url,packageName,holder);
        } catch (Exception e) {
            //
        }
        return target;
    }

    /**
     * {@.ja モジュール名作成する。}
     * <p>
     * {@.ja 拡張子を削除する。拡張子jarの場合はモジュール名を付加する}
     */
    private String getModuleName(String name){
        String extensions[] = {".class", ".jar"};
        for(int ic=0;ic<extensions.length;++ic){
            if(name.endsWith(extensions[ic])){
                int point = name.lastIndexOf(extensions[ic]);
                name =  name.substring(0, point);
                if(extensions[ic].equals(".jar")){
                    name =  name+"."+name;
                }
                break;
            }
        }
        return name;
    }

    /**
     *
     */
    private URLClassLoader createURLClassLoader(String parent){
        File path = new File(parent);
        URL[] urls = new URL[1];
        try{
            URI uri = path.toURI();
            urls[0] = uri.toURL();
        }
        catch(java.net.MalformedURLException ex){
            rtcout.println(rtcout.WARN, 
                "java.net.MalformedURLException: toURL() threw Exception."+ex);
            return null;
        }
        URLClassLoader url = new URLClassLoader(urls);
        return url;
    }

    /**
     * {@.ja モジュールのアンロード}
     * {@.en Load and intialize the module}
     *
     * <p>
     * {@.ja 指定されたモジュールをロードします。初期化メソッドを指定した場合
     * には、 * ロード時にそのメソッドが呼び出されます。
     * これにより、モジュール初期化を行えます。>
     * 
     * コンストラクタで指定した初期化情報の 'manager.modules.abs_path_allowed'
     * が 'yes' の場合は、className引数は、ロードモジュールのフルクラス名
     * として解釈されます。<br />
     * 'no' が指定されている場合は、className引数はロードモジュールの
     * シンプルクラス名として解釈され、
     * 規定のモジュールロードパス以下からモジュールが検索されます。
     * 
     * コンストラクタで指定した初期化情報の 'manager.modules.download_allowed'
     * が 'yes' の場合は、
     * className引数は、ロードモジュールのURLとして解釈されます。（未実装）}
     * {@.en Load the specified file as DLL or a shared library, and execute 
     * operation for specified initialization.}
     * 
     * @param moduleName 
     *   {@.ja モジュール名}
     *   {@.en A module name}
     * @param methodName 
     *   {@.ja 初期実行メソッド名}
     *   {@.en a initial method name }
     * @return 
     *   {@.ja moduleName引数で指定したモジュール名がそのまま返されます。}
     *   {@.en The module name specified by the argument is returned. }
     * @throws IllegalArgumentException 
     *   {@.ja 引数が正しく指定されていない場合にスローされます。}
     *   {@.en When the argument is not correctly specified, it is thrown out. }
     */
    public String load(final String moduleName, final String methodName)
            throws Exception {
        rtcout.println(rtcout.TRACE, 
                "load(fname = "+moduleName+"   init_func = "+methodName+")");
        
        if (moduleName == null || moduleName.length() == 0) {
            throw new IllegalArgumentException("moduleName is empty.:load()");
        }
        if (methodName == null || methodName.length() == 0) {
            throw new IllegalArgumentException("methodName is empty.:load()");
        }

	String module_path = load(moduleName);

	Method initMethod = symbol(module_path,methodName);


        DLLEntity dll_entity = m_modules.get(module_path);
        Class target = dll_entity.dll;
        if(target == null){
            throw new ClassNotFoundException("Not implemented." + moduleName); 
        }


        try {
            initMethod.invoke(target.newInstance());
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (IllegalAccessException e) {
            throw e;
        } catch (InvocationTargetException e) {
            throw e;
        } catch (InstantiationException e) {
            throw e;
        }
        
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
        m_modules = new HashMap<String, DLLEntity>();
    }
    
    /**
     * {@.ja モジュールのメソッドの参照。}
     * {@.en Refer to the symbol of the method}
     *
     * @param class_name 
     *   {@.ja クラスの名前}
     *   {@.en Name of class}
     * @param method_name
     *   {@.ja メソッドの名前}
     *   {@.en Name of method}
     *
     * @return
     *   {@.ja メソッド}
     *   {@.en method}
     */
    public Method symbol(String class_name, String method_name) 
        throws Exception {
        Class target = m_modules.get(class_name).dll;
        if( target==null ) {
            throw new IllegalArgumentException(
                                    "Not Found(symbol):" + class_name);
        }
        //
        Method initMethod;
        try {
            initMethod = target.getMethod(method_name);
        } catch (SecurityException e) {
            throw e;
        } catch (NoSuchMethodException e) {
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
    public Vector<Properties> getLoadedModules() {
        Vector<Properties> props = new Vector<Properties>();
        Set<String> str_set = m_modules.keySet();
        for(Iterator it=str_set.iterator();it.hasNext();){
           props.add(m_modules.get(it.next()).properties); 
        }
        return props;
    }
        
    /**
     * {@.ja ロード可能モジュールリストを取得する}
     * {@.en Get the loadable module list}
     *
     * <p>
     * {@.ja ロード可能なモジュールのリストを取得する。}
     * {@.en Get the loadable module list (not implemented).}
     *
     * @return
     *   {@.ja ロード可能モジュールリスト}
     *   {@.en Loadable module list}
     *
     */
    public Vector<Properties> getLoadableModules() {
        rtcout.println(rtcout.TRACE, "getLoadableModules()");
        Vector<String> dlls = new Vector<String>();
        String separator =  System.getProperty("file.separator");
        for (int i=0; i < m_loadPath.size(); ++i) {
            String loadpath = m_loadPath.elementAt(i);
            if(loadpath==null|loadpath.equals("")){
                continue;
            }
            java.io.File dir = new java.io.File(loadpath);
            String[] flist = dir.list(new FileFilter());
            for (int ic=0; ic < flist.length; ++ic) {
                dlls.add(loadpath+separator+flist[ic]);
            }  
        }

        Vector<Properties> props = new Vector<Properties>();
        for (int ic=0; ic < dlls.size(); ++ic) {
            Class target = null;
            File file = new File(dlls.elementAt(ic));
            if(file.isAbsolute()) {
                URLClassLoader url = createURLClassLoader(file.getParent());
                if(url!=null){
                    String name = file.getName();
                    name = getModuleName(name);
                    StringHolder packageModuleName = new StringHolder();
                    target = getClassFromName(url,name,packageModuleName);
                }
            }
            else{
                String str[] = dlls.elementAt(ic).split(".class");
                str[0] = str[0].replace(separator,".");
                str[0] = str[0].replace("..",".");
                try {
                    target = Class.forName(str[0],
                                         true,
                                         this.getClass().getClassLoader());
                }
                catch(Exception e){
                    continue;
                }
            }
            try {
                Field field = target.getField("component_conf");
                String[] data = (String[])field.get(null);
                java.util.ArrayList al 
                    = new java.util.ArrayList(java.util.Arrays.asList(data));
            
                al.add(0,"module_file_name");
                al.add(1,file.getName());
                al.add(2,"module_file_path");
                al.add(3,dlls.elementAt(ic));
                props.add(new Properties((String[])al.toArray(new String[]{})));
            } 
            catch(Exception e){
                continue;
            }
        }  
         
        return props;
    }
    
    /**
     * <p>モジュールのフルクラス名指定を指定します。</p>
     */
    public void allowAbsolutePath() {
        m_absoluteAllowed = true;
    }
    
    /**
     * <p>モジュールのフルクラス名指定解除を指定します。</p>
     */
    public void disallowAbsolutePath() {
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
     * <p> Module list that has already loaded </p>
     */

    protected Map<String, DLLEntity> m_modules 
                                        = new HashMap<String, DLLEntity>();
    private class DLLEntity {
        public Properties properties;
        public Class dll;
    }

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

    private class FileFilter implements java.io.FilenameFilter {
        private final String FILTER_KEYWORD = ".class,.jar";

        public boolean accept(java.io.File dir, String name) {
            java.io.File file = new java.io.File(name);

            if(file.isDirectory()){
               return false;
            }
            String[] filter = FILTER_KEYWORD.split(",");
            for(int ic=0;ic<filter.length;++ic){ 
                if(name.endsWith(filter[ic])){
                    return true;
                }
            }
            return false;
        }
    }
    private Logbuf rtcout;
}
