package jp.go.aist.rtm.RTC;

import jp.go.aist.rtm.RTC.sample.SampleComponentDelete;
import jp.go.aist.rtm.RTC.sample.SampleComponentNew;
import jp.go.aist.rtm.RTC.util.Properties;
import junit.framework.TestCase;
import RTC.ComponentProfile;
import RTC.RTObject;

/**
* RTObjectRef　テスト
* 対象クラス：RTObject_impl
*/
public class RTObjectRefTest extends TestCase {

    /**
     * <p>RTObjectのコンポーネントプロファイルチェック
     * <ul>
     * <li>オブジェクトリファレンス経由でプロファイルを取得できるか？</li>
     * </ul>
     * </p>
     */
    public void test_BasicOperation() {
        try {
            RTObject_impl rtobj = prepareRTObject();
            RTObject ref = rtobj.getObjRef();

            ComponentProfile profile = ref.get_component_profile();
            
            assertEquals("type_sample0", profile.instance_name);
            assertEquals("type_sample", profile.type_name);
            assertEquals("description_sample", profile.description);
            assertEquals("version_sample", profile.version);
            assertEquals("vendor_sample", profile.vendor);
            assertEquals("category_sample", profile.category);
    
            
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    private RTObject_impl prepareRTObject() {
        Manager manager = Manager.instance();
        manager.activateManager();
     
        String component_conf[] = {
                "implementation_id", "sample",
                "type_name",         "type_sample",
                "description",       "description_sample",
                "version",           "version_sample",
                "vendor",            "vendor_sample",
                "category",          "category_sample",
                "activity_type",     "activity_type_sample",
                "max_instance",      "max_instance_sample",
                "language",          "language_sample",
                "lang_type",         "",
                "conf",              "",
                ""
        };
        Properties prop = new Properties(component_conf);
        manager.registerFactory(prop, new SampleComponentNew(), new SampleComponentDelete());
        RTObject_impl rtobj = manager.createComponent("sample");
        return rtobj;
    }
}

