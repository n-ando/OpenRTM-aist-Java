package jp.go.aist.rtm.RTC.util;

import _SDOPackage.NVListHolder;
import _SDOPackage.NameValue;

/**
 * {@.ja NVListHolderのファクトリ}
 * {@.en Implementation of NVListHolder factory}
 */
public class NVListHolderFactory {

    /**
     * {@.ja NVListHolderを生成する}
     * {@.en Creates NVListHolder}
     * 
     * @return 
     *   {@.ja 生成されたNVListHolderオブジェクト}
     *   {@.en Created NVListHolder object}
     */
    public static NVListHolder create() {
        
        return new NVListHolder(new NameValue[0]);
    }
    
    /**
     * {@.ja NVListHolderの複製を生成する。}
     * {@.en Creates the clone of NVListHolder}
     * 
     * @param rhs 
     *   {@.ja NVListHolderオブジェクト}
     *   {@.en NVListHolder object}
     * @return 
     *   {@.ja コピーされたNVListHolderオブジェクト}
     *   {@.en Copied NVListHolder object}
     */
    public static NVListHolder clone(final NVListHolder rhs) {
        
        NameValue[] value = new NameValue[rhs.value.length];
        for (int i = 0; i < value.length; i++) {
            value[i] = NameValueFactory.clone(rhs.value[i]);
        }
        
        return new NVListHolder(value);
    }
}
