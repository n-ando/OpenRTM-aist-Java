package jp.go.aist.rtm.RTC.jfsm;
//package jfsm;

import jp.go.aist.rtm.RTC.jfsm.machine.EventBase;
import jp.go.aist.rtm.RTC.jfsm.machine.StateInfo;

/**
 * <p>イベントオブジェクト。イベントをオブジェクトとして扱ったり、ハンドラ名(メソッド名)から
 * イベントを発火するときに使用する。</p>
 *
 * <p>C++版と異なりジェネリクスは用いていない。{@link Event#dispatch(StateInfo)}を隠蔽するため、
 * EventBaseと分離している。</p>
 */
public class Event extends EventBase {

    /**
     * イベントオブジェクトを構築する。handlerNameとkwargsから、リフレクションで適切な
     * ハンドラメソッドを呼び出せる。
     *
     * @param handlerName ハンドラのメソッド名。
     * @param kwargs ハンドラに渡すパラメータ。
     */
    public Event(String handlerName, Object... kwargs) {
        this(handlerName, null, kwargs);
    }

    /**
     * イベントオブジェクトを構築する。handlerNameとkwargsから、リフレクションで適切な
     * ハンドラメソッドを呼び出せる。
     *
     * @param handlerName ハンドラのメソッド名。
     * @param args ハンドラのパラメータ型。
     * @param kwargs ハンドラに渡すパラメータ。
     */
    public Event(String handlerName, Class<?>[] args, Object... kwargs) {
        super(handlerName, args, kwargs);
    }

}
