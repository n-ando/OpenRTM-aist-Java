package Extension.LocalService.nameservice_file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.IndexOutOfBoundsException;
import java.lang.SecurityException;
import java.util.Arrays;
import java.util.ArrayList;
import jp.go.aist.rtm.RTC.LocalServiceBase;
import jp.go.aist.rtm.RTC.LocalServiceProfile;
import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.log.Logbuf;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.StringUtil;
    /**
     * {@.ja FileNameservice クラス}
     * {@.en FileNameservice class}
     */
public class FileNameservice implements LocalServiceBase {

    private static String service_name 
            = "org.openrtm.local_service.nameservice.file_nameservice";
    private static String service_uuid 
            = "cc1744c2-6952-4ad8-a7f6-ceaa041c5541";
    private static String default_config[] = {
        "base_path",         "/tmp/.openrtm_ns/",
        "file_structure",    "tree",
        "context_delimiter", "|",
        ""
    };
    /**
     * {@.ja コンストラクタ。}
     * {@.en Constructor}
     * 
     * <p>    
     * {@.ja Protected コンストラクタ}
     * {@.en Protected Constructor}
     *
     */
    public FileNameservice() {
        rtcout = new Logbuf("file_nameservice");
        m_profile.name = service_name;
        m_profile.uuid = service_uuid;
        Properties prop = new Properties(default_config);
        m_profile.properties = prop;
        m_profile.service = this;
        rtcout.println(Logbuf.DEBUG,"FileNameservice was created(");
        rtcout.println(Logbuf.DEBUG,"    name = "+m_profile.name);
        rtcout.println(Logbuf.DEBUG,"    uuid = "+m_profile.uuid);
        rtcout.println(Logbuf.DEBUG,"    properties =");
        String str = new String();
        str = m_profile.properties._dump(str,m_profile.properties,0);
        rtcout.println(Logbuf.DEBUG,str);
        rtcout.println(Logbuf.DEBUG,"    service = "+this+")");
    }

    /**
     * {@.ja 初期化関数}
     * {@.en Initialization function}
     * <p>
     * @param profile 
     *   {@.ja 外部から与えられた property}
     * @return 
     */
    public boolean init(final Properties props){
        rtcout.println(Logbuf.TRACE,"init()");
        String str = new String();
        str = props._dump(str,props,0);
        rtcout.println(Logbuf.DEBUG,str);
        m_profile.properties.merge(props);
      
        Manager manager = Manager.instance();
        manager.addNamingActionListener(new NamingAction(this));
        return true;
    }
      
    /**
     * {@.ja 再初期化関数}
     * {@.en Reinitialization function}
     *
     * @param profile 
     *   {@.ja 外部から与えられた property}
     * @return 
     *
     *
     */
    public boolean reinit(final Properties props){
        rtcout.println(Logbuf.TRACE,"init()");
        String str = new String();
        str = props._dump(str,props,0);
        rtcout.println(Logbuf.DEBUG,str);
        boolean ret = processServiceProfile(props);
        m_profile.properties.merge(props);
        return ret;
    }
      
    /**
     * {@.ja LocalServiceProfile を取得する}
     * {@.en Getting LocalServiceProfile}
     *
     *
     * @return 
     *   {@.ja このオブジェクトが保持している LocalServiceProfile}
     *   {@.en LocalServiceProfile of this service class}
     *
     */
    public final LocalServiceProfile getProfile() {
        rtcout.println(Logbuf.TRACE,"getProfile()");
        return m_profile;
    }
    
    /**
     * {@.ja 終了関数}
     * {@.en Finalization function}
     *
     *
     */
    public void _finalize(){
        cleanupFiles();
    }
    
    /**
     * {@.ja 名前登録時に呼ばれるコールバック}
     * {@.en A call-back at name registration}
     *
     *
     */
    public void onRegisterNameservice(String[] ns_path, String[] ns_info) {
        rtcout.println(Logbuf.TRACE,"onRegisterNameservice(path = "
                                        + Arrays.toString(ns_path)+")");
        rtcout.println(Logbuf.TRACE," nsinfo ="+Arrays.toString(ns_info));
      
        for (int ic=0; ic < ns_path.length; ++ic) {
            File filepath = new File(getFname(ns_path[ic]));
            File directory = filepath.getParentFile();
            rtcout.println(Logbuf.DEBUG,"file path: "+filepath);
            rtcout.println(Logbuf.DEBUG,"directory: "+directory);
            if (!createDirectory(directory)) {
                continue;
            }
            try {
                String filename = filepath.getName();
                rtcout.println(Logbuf.DEBUG,"file name: "+filename);
                FileWriter file = new FileWriter(filepath);
                BufferedWriter ofs = new BufferedWriter(file);
                for (int i=0; i < ns_info.length; ++i) {
                    ofs.write(ns_info[i]);
                }
                ofs.flush();
                ofs.close();
                rtcout.println(Logbuf.INFO,"RTC "+filename
                                +"'s IOR has been successfully registered: "
                                +filepath);
                m_files.add(filepath.getAbsolutePath());
            }
            catch (Exception ex) {
                rtcout.println(Logbuf.ERROR,"Creating file has been failed. "
                                 + filepath);
            }
        }
    }
    
    /**
     * {@.ja 名前登録解除に呼ばれるコールバック}
     * {@.en A call-back at name runegistration}
     *
     *
     */
    public void onUnregisterNameservice(String[] ns_path){
        rtcout.println(Logbuf.TRACE,"onUnregisterNameservice("
                   +Arrays.toString(ns_path)+")");
        for (int ic=0; ic < ns_path.length; ++ic) {
            String filepath = getFname(ns_path[ic]);
            File file = new File(filepath);
            if (!file.exists()) {
                rtcout.println(Logbuf.ERROR,"No such file: "+ filepath);
                continue;
            }
            try {
                m_files.remove(filepath);
            }
            catch(IndexOutOfBoundsException ex){
                rtcout.println(Logbuf.WARN,"This file ("
                                            +filepath
                                            +") might not be my file.");
                continue;
            }
            
            boolean error = false;
            boolean result = false;
            rtcout.println(Logbuf.DEBUG,"Removing file: "
                                            +filepath);
            try{
                result = file.delete();
            }
            catch(SecurityException se) {
                error = true;
            }
            if (!result||error) {
                rtcout.println(Logbuf.ERROR,"Removing a file has been failed."
                                            + filepath);
                continue;
            }
            rtcout.println(Logbuf.PARANOID,"Removing a file done: "
                                            +filepath);
        }
        return;
    }

    /**
     * {@.ja ディレクトリ作成}
     * {@.en Creating directories}
     */
    protected boolean createDirectory(File directory){
        rtcout.println(Logbuf.TRACE,"createDirectory("
                                            + directory
                                            +")");
        if (!directory.exists()) {
            rtcout.println(Logbuf.DEBUG,"Directory "
                                            +directory 
                                            +"not found");
            boolean error = false;
            boolean result = false;
            rtcout.println(Logbuf.DEBUG,"Creating directory: "
                                            + directory);
            try{
                result = directory.mkdir();
            }
            catch(SecurityException se){
                error = true;
            }
            if (!result || error) {
                rtcout.println(Logbuf.ERROR,
                                        "Creating directory has been failed."
                                        + directory);
                return false;
            }
            rtcout.println(Logbuf.PARANOID,"Creating directory done: "
                                            +directory);

        }
        else if (directory.exists() && directory.isDirectory()) {
            rtcout.println(Logbuf.DEBUG,"Directory "
                                            +directory 
                                            +" exists.");
        }
        else {
            rtcout.println(Logbuf.ERROR,"File exists instead of base directory "
                                            +directory
                                            +" .");
            return false;
        }
        return true;
    }
    
    /**
     * {@.ja ファイル名取得}
     * {@.en Getting file name}
     */
    protected String getFname(String ns_path) {
        rtcout.println(Logbuf.TRACE,"getFname(" +ns_path +")");
      
        String pathstring = m_profile.properties.getProperty("base_path");
        String file_separator = System.getProperty("file.separator");
        pathstring += file_separator;
      
        String fs = m_profile.properties.getProperty("file_structure");
        fs = StringUtil.normalize(fs);
        if (fs.equals("flat")) {
            rtcout.println(Logbuf.DEBUG,"file_structure = flat");
            String d = m_profile.properties.getProperty("context_delimiter");
            ns_path.replaceAll(file_separator, d);
            pathstring += ns_path;
        }
        else if (fs.equals("tree")) {
            rtcout.println(Logbuf.DEBUG,"file_structure = tree");
            pathstring += ns_path;
        }
        rtcout.println(Logbuf.DEBUG,"path string = "+ pathstring);
      
        return pathstring;
    }
    
    /**
     * {@.ja 全ファイル削除}
     * {@.en Deleting all files}
     */
    protected void cleanupFiles() {
        rtcout.println(Logbuf.TRACE,"cleanupFiles()");
        for (int ic=0; ic < m_files.size(); ++ic) {
            File p = new File(m_files.get(ic));
            p.delete();
        }
        m_files.clear();
    }
      
    /**
     * {@.ja プロパティの処理}
     * {@.en Processing properties}
     */
    protected boolean processServiceProfile(final Properties props) {
        return true;
    }
      
    
    private LocalServiceProfile m_profile;
    private ArrayList<String> m_files;
    private  Logbuf rtcout;
}
