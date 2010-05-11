package jp.go.aist.rtm.RTC.util;

/**
* <p>データホルダークラス用共通インターフェースです。</p>
*/
public interface ValueHolder {
    /**
     * 文字列から各データホルダー型に変換して設定するための抽象メソッドです。</p>
     *
     * @param def_val　設定値文字列表現
     * 
     * @exception Exception 渡された文字列が構文解析可能な値を含まない。
     */
    public void stringFrom(final String def_val) throws Exception;
}
