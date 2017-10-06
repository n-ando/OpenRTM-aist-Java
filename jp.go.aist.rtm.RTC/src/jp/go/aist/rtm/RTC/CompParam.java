package jp.go.aist.rtm.RTC;

import java.util.Collections;
import java.util.List;
import java.util.Arrays;


/**
 * {@.ja CompParam クラス}
 * {@.en CompParam class}
 * <p>
 * {@.ja RTCのベンダ名、カテゴリ名、ID、言語、バージョンを格納するクラス。}
 * {@.en This class stores a vendor name of RTC, a category name, 
 * ID, a language and the version.}
 *
 */
public class CompParam {

    public static final List<String> prof_list = 
            Collections.unmodifiableList(Arrays.asList(
                "RTC",
                "vendor",
                "category",
                "implementation_id",
                "language",
                "version"
            ));

    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     * @param module_name
     *   {@.ja モジュール名}
     *   {@.en Module Name}
     *
     */
    public CompParam(String module_name){
        String modulename = module_name.split("\\?")[0];
        String[] param_list = modulename.split(":",-1);
        if(param_list.length < prof_list.size()){
            m_type = "RTC";
            m_vendor = "";
            m_category = "";
            m_impl_id = modulename;
            m_language = "Java";
            m_version = "";
        }
        else{
            m_type = param_list[0];
            m_vendor = param_list[1];
            m_category = param_list[2];
            m_impl_id = param_list[3];
            if(!param_list[4].isEmpty()){
                m_language = param_list[4];
            }
            else{
                m_language = "Java";
            }
            m_version = param_list[5];
        }
    }
    /**
     * {@.ja ベンダ名取得}
     * {@.en Gets the vendor name.}
     * @return 
     *   {@.ja ベンダ名}
     *   {@.en Vendor Name}
     */
    public String vendor(){
        return m_vendor;
    }
    /**
     * {@.ja カテゴリ名取得}
     * {@.en Gets the category name.}
     * @return 
     *   {@.ja カテゴリ名取}
     *   {@.en Category Name}
     */
    public String category(){
        return m_category;
    }
    /**
     * {@.ja ID取得}
     * {@.en Gets the ID.}
     * @return 
     *   {@.ja ID}
     *   {@.en ID}
     */
    public String impl_id(){
        return m_impl_id;
    }
    /**
     * {@.ja 言語取得}
     * {@.en Gets the language.}
     * @return 
     *   {@.ja 言語}
     *   {@.en language}
     */
    public String language(){
        return m_language;
    }
    /**
     * {@.ja バージョン取得}
     * {@.en Gets the version.}
     * @return 
     *   {@.ja バージョン}
     *   {@.en version}
     */
    public String version(){
        return m_version;
    }
  
    private String m_type;
    private String m_vendor;
    private String m_category;
    private String m_impl_id;
    private String m_language;
    private String m_version;
}
