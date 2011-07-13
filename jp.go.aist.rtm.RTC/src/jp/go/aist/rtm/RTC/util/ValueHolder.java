package jp.go.aist.rtm.RTC.util;

/**
* {@.ja データホルダークラス用共通インターフェース}
* {@.en Common interface for data holder class}
*/
public interface ValueHolder {
    /**
     * {@.ja 文字列から各データホルダー型に変換して設定するための抽象メソッド}
     * {@.en Abstraction method to convert String 
     * into each data holder type and to set it}
     *
     * @param def_val
     *   {@.ja 設定値文字列表現}
     *   {@.en String}
     * 
     * @exception Exception 
     *   {@.ja 渡された文字列が構文解析可能な値を含まない。}
     *   {@.en The character string of the argument is not good at parsing.}
     */
    public void stringFrom(final String def_val) throws Exception;
}
