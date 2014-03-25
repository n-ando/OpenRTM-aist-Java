package RTMExamples.ConfigSample;

import java.io.Serializable;
import java.util.Vector;

import jp.go.aist.rtm.RTC.util.ValueHolder;

public class VectorHolder  implements ValueHolder, Serializable {

    /**
     * Vector型データ設定値
     */
    public Vector value = null;

    /**
     * デフォルトコンストラクタ
     *
     */
    public VectorHolder() {
    }

    /**
     * コンストラクタ
     *
     * @param initialValue　初期値
     *
     */
    public VectorHolder(Vector initialValue) {
        value = new Vector(initialValue);
    }

    /**
     * 文字列からVector型に変換して設定
     *
     * @param def_val　設定値文字列表現
     *
     */
    public void stringFrom(String def_val) throws Exception {
        value = new Vector();
        String values[] = def_val.split(",");
        for( int intIdx=0;intIdx<values.length;intIdx++ ) {
            value.add(values[intIdx]);
        }
    }
    /**
     * 設定値の取得
     *
　   * @return 設定値
     *
     */
    public Vector getValue(){
        return value;
    }
    /**
     * 設定値を文字列に変換
     *
　   * @return 変換文字列
     *
     */
    public String toString(){
        StringBuffer retVal = new StringBuffer();
        while(value.iterator().hasNext()) {
            retVal.append(value.iterator().next());
            if(value.iterator().hasNext()) retVal.append("'");
        }
        return retVal.toString();
    }
}
