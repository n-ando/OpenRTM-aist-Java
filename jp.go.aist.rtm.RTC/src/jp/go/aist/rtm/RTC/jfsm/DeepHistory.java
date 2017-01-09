package jp.go.aist.rtm.RTC.jfsm;
//package jfsm;

import java.lang.annotation.*;

/**
 * 深い履歴を指定するアノテーション。ユーザー定義状態クラスに付加する。
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DeepHistory {
}
