package jp.go.aist.rtm.RTC;

import org.omg.CORBA.TCKind;

import _SDOPackage.NVListHolder;
import jp.go.aist.rtm.RTC.util.Properties;
import junit.framework.TestCase;

/**
 * テスト用サンプルコンポーネント　ベース・クラス
 */
public class SampleTest extends TestCase {

    protected void copyToProperties(Properties prop, final NVListHolder nv) {
        
        for (int i = 0; i < nv.value.length; ++i) {
            String value = null;
            if( nv.value[i].value.type().kind() == TCKind.tk_wstring ) {
                value = nv.value[i].value.extract_wstring();
            } else {
                value = nv.value[i].value.extract_string();
            }
            final String name = new String(nv.value[i].name);
            prop.setProperty(name, value);
        }
    }
}
