package jp.go.aist.rtm.RTC;

/**
 * {@.ja オブジェクト生成時ネーミング・ポリシー(命名規則)管理用
 * 抽象インターフェース。}
 * {@.en Abstruct class for naming policy management when creating objects.}
 *
 * <p>
 * {@.ja オブジェクトを生成する際のネーミング・ポリシー(命名規則)を
 * 管理するための抽象インターフェースクラス。
 * 具象クラスは、以下の純粋仮想関数の実装を提供しなければならない。
 * <ul>
 * <li> onCreate() : オブジェクト生成時の名称作成
 * <li> onDelete() : オブジェクト削除時の名称解放
 * </ul>}
 * {@.en This is the abstract interface class to manage the naming policy when
 * creating objects.
 * Concrete classes must implement the following pure virtual functions.
 * <ul>
 * <li> onCreate() : Create the name when creating objects.
 * <li> onDelete() : Delete the name when deleting objects.
 * </ul>}
 */
public interface NumberingPolicy {
  /**
   * {@.ja オブジェクト生成時の名称作成。}
   * {@.en Create the name when creating objects.}
   *
   * <p>
   * {@.ja オブジェクト生成時の名称を生成するための純粋仮想関数}
   * {@.en Pure virtual function to create the name when creating objects.}
   * 
   * @param obj 
   *   {@.ja 名称生成対象オブジェクト}
   *   {@.en The target object for the creation}
   *
   * @return 
   *   {@.ja 生成したオブジェクト名称}
   *   {@.en Name of the created object}
   *
   */
    public String onCreate(RTObject_impl obj);
  /**
   * {@.ja オブジェクト削除時の名称解放。}
   * {@.en Delete the name when deleting objects.}
   *
   * <p>
   * {@.ja オブジェクト削除時に名称を解放するための純粋仮想関数}
   * {@.en Pure virtual function to delete the name when deleting object.}
   * 
   * @param obj 
   *   {@.ja 名称解放対象オブジェクト}
   *   {@.en The target object of the delete}
   *
   */
    public void onDelete(RTObject_impl obj);

}
