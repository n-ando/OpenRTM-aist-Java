package jp.go.aist.rtm.RTC.util;

/**
 * {@.ja データ参照を表現するクラス}
 * {@.en Class that expresses data reference}
 * <p>
 * {@.ja あるメソッドに対してデータを引数で渡した際に、
 * データオブジェクトそのものを操作して値を変更することができない場合で、
 * メソッド内でデータ書き換えをする場合に有用である}
 * 
 * @param <T> 
 *   {@.ja データ型を指定}
 *   {@.en Data type}
 */
public class DataRef<T> {

    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     * 
     * @param v 
     *   {@.ja 初期状態で割り当てるデータ}
     *   {@.en Data allocated in initial state}
     */
    public DataRef(T v) {
        
        this.v = v;
    }
    
    /**
     * {@.ja 等価演算子}
     * {@.en Processing that judges equivalence}
     * <p>
     * {@.ja 引数にDataRef型オブジェクトが指定された場合には、
     * それに内包されているデータと当該オブジェクトに内包するデータとの間で
     * 等価判断を行う。
     * また、本メソッドの引数に比較したいデータそのものを指定した場合には、
     * 当該オブジェクトに内包するデータと引数で指定されたデータとの間で
     * 等価判断を行う。}
     * 
     * @param rhs 
     *   {@.ja 等価判断対象のオブジェクト}
     *   {@.en Object of equivalent judgment}
     * @return 
     *   {@.ja 等価である場合はtrueを、さもなくばfalseを返す。}
     *   {@.en Equivalence is true.}
     */
    public boolean equals(Object rhs) {

        if (this == rhs) {
            return true;
        }
        
        if (rhs instanceof DataRef) {
            rhs = ((DataRef) rhs).v;
        }
        
        return this.v.equals(rhs);
    }
    
    /**
     * {@.ja ハッシュコード値を取得する}
     * {@.en Gets the hush code value.} 
     * 
     * @return 
     *   {@.ja ハッシュコード値}
     *   {@.en hush code}
     */
    public int hashCode() {
        
        return this.v.hashCode();
    }

    /**
     * {@.ja 当該オブジェクトが内包しているデータ}
     * {@.en Data where object concerned is stored}
     * <p>
     * {@.ja アクセスを容易にするためにpublicメンバとなっている}
     */
    public T v;
}
