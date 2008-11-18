package jp.go.aist.rtm.RTC.util;

import _SDOPackage.NVListHolder;
import _SDOPackage.NameValue;

public class NVListHolderFactory {

    public static NVListHolder create() {
        
        return new NVListHolder(new NameValue[0]);
    }
    
    public static NVListHolder clone(final NVListHolder rhs) {
        
        NameValue[] value = new NameValue[rhs.value.length];
        for (int i = 0; i < value.length; i++) {
            value[i] = NameValueFactory.clone(rhs.value[i]);
        }
        
        return new NVListHolder(value);
    }
}
