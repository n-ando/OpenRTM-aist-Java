package jp.go.aist.rtm.RTC;

import jp.go.aist.rtm.RTC.util.DoubleHolder;
import jp.go.aist.rtm.RTC.util.FloatHolder;
import junit.framework.TestCase;

/**
* コンフィギュレーションクラス　テスト
* 対象クラス：Config
*/
public class ConfigTest extends TestCase {
    
    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>コンストラクタのテスト<p/>
     * <ul>
     * <li>インスタンス生成時に指定した値が正常に設定されているか？</li>
     * </ul>
     */
    public void test_constructor() {
        String name = "name of parameter";
        DoubleHolder value = new DoubleHolder(0.0);
        String default_value = "3.14159";
        
        Config config = new Config(name, value, default_value);
            
        // 指定した名称が正しく設定されているか？
        assertEquals(name, config.name);
            
        // 指定したデフォルト値（の文字列表現）が正しく設定されているか？
        assertEquals(default_value, config.default_value);
    }


    /**
     * <p>パラメータ値の更新</p>
     * <ul>
     * <li>バインドした変数が、update()で指定された値に正しく更新されるか？</li>
     * </ul>
     */
    public void test_update() {
        String name = "name of parameter";
        DoubleHolder value = new DoubleHolder(0.0);
        String default_value = "3.14159";
        
        Config config = new Config(name, value, default_value);
        
        // update()前は、変数値が初期状態のままのはず
        assertEquals(0.0, value.getValue());
        // update()する
        assertEquals(true, config.update("1.41421356"));
        // バインドした変数が、update()で指定された値に正しく更新されるか？
        assertEquals(1.41421356, value.getValue());
    }

    /**
     * <p>パラメータ値の更新</p>
     * <ul>
     * <li>指定の型に変換できない内容でupdate()を呼出し、意図どおり失敗するか？</li>
     * <li>バインドした変数の値が、コンストラクタで指定したデフォルト値に正しく更新されるか？</li>
     * </ul>
     */
    public void test_update_with_illegal_value() {
        String name = "name of parameter";
        FloatHolder value = new FloatHolder(0.0F);
        String default_value = "3.14159";
        
        Config config = new Config(name, value, default_value);
        
        // update()前は、変数値が初期状態のままのはず
        assertEquals(0.0F, value.getValue());
        // 浮動小数点値に変換できない内容でupdate()を呼出し、意図どおり失敗するか？
        assertEquals(false, config.update("Not float value"));
        // バインドした変数の値が、コンストラクタで指定したデフォルト値に正しく更新されるか？
        assertEquals(3.14159F, value.getValue());
    }

    /**
     * <p>パラメータ値の更新</p>
     * <ul>
     * <li>浮動小数点値に変換できないデフォルト値が指定され、かつ浮動小数点値に変換できない内容でupdate()を呼出した場合、
     *    バインドした変数の値は、更新されることなく元の値に留まるか？</li>
     * </ul>
     */
    public void test_update_with_illegal_default_value() {
        String name = "name of parameter";
        DoubleHolder value = new DoubleHolder(0.0);
        String illegal_default_value = "Not float value";
        
        Config config = new Config(name, value, illegal_default_value);

        // update()前は、変数値が初期状態のままのはず
        assertEquals(0.0, value.getValue());
        
        // 浮動小数点値に変換できないデフォルト値が指定され、
        // かつ浮動小数点値に変換できない内容でupdate()を呼出した場合、
        // バインドした変数の値は、更新されることなく元の値に留まるか？
        assertEquals(false, config.update("Not float value too"));
        assertEquals(0.0, value.getValue());
    }
}
