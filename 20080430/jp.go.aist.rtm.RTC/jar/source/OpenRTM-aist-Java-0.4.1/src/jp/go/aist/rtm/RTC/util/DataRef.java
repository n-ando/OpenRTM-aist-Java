package jp.go.aist.rtm.RTC.util;

/**
 * <p>データ参照を表現するクラスです。
 * あるメソッドに対してデータを引数で渡した際に、データオブジェクトそのものを操作して
 * 値を変更することができない場合で、メソッド内でデータ書き換えをする場合に有用です。</p>
 * 
 * @param <T> データ型を指定します。
 */
public class DataRef<T> {

    /**
     * <p>コンストラクタです。</p>
     * 
     * @param v 初期状態で割り当てるデータ
     */
    public DataRef(T v) {
        
        this.v = v;
    }
    
    /**
     * <p>等価演算子です。引数にDataRef型オブジェクトが指定された場合には、
     * それに内包されているデータと当該オブジェクトに内包するデータとの間で等価判断を行います。
     * また、本メソッドの引数に比較したいデータそのものを指定した場合には、
     * 当該オブジェクトに内包するデータと引数で指定されたデータとの間で等価判断を行います。</p>
     * 
     * @param rhs 等価判断対象のオブジェクト
     * @return 等価である場合はtrueを、さもなくばfalseを返します。
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
     * <p>ハッシュコード値を取得します。</p>
     * 
     * @return ハッシュコード値
     */
    public int hashCode() {
        
        return this.v.hashCode();
    }
    
    /**
     * <p>当該オブジェクトが内包しているデータです。
     * アクセスを容易にするためにpublicメンバとなっています。</p>
     */
    public T v;
}
