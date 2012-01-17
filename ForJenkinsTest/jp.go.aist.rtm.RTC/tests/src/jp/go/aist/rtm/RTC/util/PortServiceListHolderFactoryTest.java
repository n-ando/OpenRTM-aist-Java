package jp.go.aist.rtm.RTC.util;

import junit.framework.TestCase;

import org.omg.CORBA.Any;

import RTC.PortServiceListHolder;

import _SDOPackage.NameValue;

/**
* CORBA ユーティリティクラス　テスト
* 対象クラス：PortServiceListHolderFactory
*/
public class PortServiceListHolderFactoryTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>ポートプロファイルの生成テスト
     * <ul>
     * <li>PortProfileオブジェクトを生成できるか？</li>
     * </ul>
     * </p>
     */
    public void test_create() {
        PortServiceListHolder port_list = PortServiceListHolderFactory.create();
        if(port_list == null) 
            System.out.println("port_list is null");
        assertNotNull(port_list);
    }

    /**
     * <p>ポートプロファイルの複製を生成テスト
     * <ul>
     * <li>PortProfileオブジェクトがコピーされるか？</li>
     * </ul>
     * </p>
     */
    public void test_clone() {
        PortServiceListHolder PortServiceList = PortServiceListHolderFactory.create();
        PortServiceListHolder port_list = PortServiceListHolderFactory.clone(PortServiceList);
        if(port_list == null) 
            System.out.println("port_list is null");
        assertNotNull(port_list);
    }

}
