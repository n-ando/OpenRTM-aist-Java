package jp.go.aist.rtm.RTC.util;

import java.io.Serializable;

/**
* {@.ja int型データ格納用クラス}
* {@.en Class for int type data storage}
*/
public class IntegerHolder implements ValueHolder, Serializable {

    private static final long serialVersionUID = 4149201397182532484L;
    /**
     * {@.ja int型データ設定値}
     * {@.en int type data setting value}
     */
    public Integer value = null;

    /**
     * {@.ja デフォルトコンストラクタ}
     * {@.en Default constructor}
     */
    public IntegerHolder() {
    }

    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     * @param initialValue
     *   {@.ja 初期値}
     *   {@.en Initial value}
     */
    public IntegerHolder(int initialValue) {
      value = new Integer(initialValue);
    }

    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     * @param initialValue
     *   {@.ja 初期値}
     *   {@.en Initial value}
     */
    public IntegerHolder(Integer initialValue) {
      value = initialValue;
    }

    /**
     * {@.ja 文字列からint型に変換して設定する}
     * {@.en Converts from the character string into int type and sets it.}
     *
     * @param def_val
     *   {@.ja 設定値文字列表現}
     *   {@.en String}
     * 
     * @exception Exception 
     *   {@.ja 渡された文字列が構文解析可能な integer 値を含まない。}
     *   {@.en The character string of the argument is not good at parsing.}
     * 
     */
    public void stringFrom(String def_val) throws Exception {
        value = new Integer(def_val);
    }
    
    /**
     * {@.ja 設定値を取得する}
     * {@.en Gets a set value.}
     *
　   * @return 
     *   {@.ja 設定値}
     *   {@.en Set value}
     */
    public int getValue(){
        return value.intValue();
    }

    /**
     * {@.ja 設定値を文字列に変換して取得する}
     * {@.en Converts a set value into the character string and gets it. }
     *
　   * @return 
     *   {@.ja 設定値文字列表現}
     *   {@.en Converted character string}
     */
    public String toString(){
        return String.valueOf(value);
    }

    /**
     * {@.ja InputStreamのデータを読み込む}
     * {@.en Reads data from InputStream.}
     *
     * @param i
     *   {@.ja InputStream}
     *   {@.en InputStream}
     */
    public void _read (org.omg.CORBA.portable.InputStream i)
    {
        int data = i.read_long ();
        value = new Integer(data);
    }

    /**
     * {@.ja OutputStreamへデータを書き込む}
     * {@.en Writes data in OutputStream}
     *
     * @param o
     *   {@.ja OutputStream}
     *   {@.en OutputStream}
     */
    public void _write (org.omg.CORBA.portable.OutputStream o)
    {
        o.write_long(value.intValue());
    }

}
