package jp.go.aist.rtm.RTC.util;

import java.io.Serializable;

/**
* {@.ja byte型データ格納用クラス}
* {@.en Class for int type data storage}
*/
public class ByteHolder implements ValueHolder, Serializable {

    private static final long serialVersionUID = 1259117692223412202L;
    /**
     * {@.ja byte型データ設定値}
     * {@.en byte type data setting value}
     */
    public Byte value = null;

    /**
     * {@.ja デフォルトコンストラクタ}
     * {@.en Default constructor}
     */
    public ByteHolder() {
    }

    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     * @param initialValue
     *   {@.ja 初期値}
     *   {@.en Initial value}
     */
    public ByteHolder(byte initialValue) {
        value = new Byte(initialValue);
    }

    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     * @param initialValue
     *   {@.ja 初期値}
     *   {@.en Initial value}
     */
    public ByteHolder(Byte initialValue) {
      value = initialValue;
    }

    /**
     * {@.ja 文字列からbyte型に変換して設定する}
     * {@.en Converts from String into byte type and sets it.}
     *
     * @param def_val
     *   {@.ja 設定値文字列表現}
     *   {@.en String}
     * 
     * @exception Exception 
     *   {@.ja 渡された文字列が構文解析可能な byte 値を含まない。}
     *   {@.en The character string of the argument is not good at parsing.}
     * 
     */
    public void stringFrom(String def_val) throws Exception {
        value = new Byte(def_val);
    }
    /**
     * {@.ja 設定値を取得する}
     * {@.en Gets a set value.}
     *
　   * @return 
     *   {@.ja 設定値}
     *   {@.en Set value}
     */
    public byte getValue(){
        return value.byteValue();
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
        byte data = i.read_octet();
        value = new Byte(data);
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
        o.write_octet(value.byteValue());
    }
}
