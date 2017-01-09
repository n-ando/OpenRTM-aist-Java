package jp.go.aist.rtm.RTC.jfsm;
//package jfsm;

import java.lang.annotation.*;

/**
 * ユーザー定義状態の保持するデータのクラスを宣言するためのアノテーション。
 *
 * {@code @DataType(MyData.class)} のように使う。
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface DataType {

    Class value();
    boolean persistent() default false;
}
