package jp.go.aist.rtm.RTC.jfsm;
//package jfsm;

import jp.go.aist.rtm.RTC.jfsm.machine.MachineBase;
import jp.go.aist.rtm.RTC.jfsm.machine.StateAlias;
import jp.go.aist.rtm.RTC.jfsm.machine.StateBase;
import jp.go.aist.rtm.RTC.jfsm.machine.StateInfo;

/**
 * 状態遷移を実行するときに遷移先を示すオブジェクト。
 * 以下のように、ユーザー定義状態のクラスを渡してオブジェクトを作り、{@link StateBase#setState(State)}に渡す。
 *
 * <pre>
 *     public void MyEventHandler() {
 *         setState(new State(MyDefinedState.class));
 *     }
 * </pre>
 *
 * C++版ではStateAliasを継承し、Stateクラスはテンプレートクラスになっているが、
 * Java版ではStateAliasを継承した普通のクラスになっている。
 *
 */
public class State extends StateAlias {
    private Class<? extends StateBase> stateClass;

    /**
     * 遷移先と初期データを指定してオブジェクトを作る。
     *
     * @param stateDef 遷移先の状態のクラス
     * @param data 遷移先状態の初期データ
     */
    public State(Class<? extends StateBase> stateDef, Object data) {
        super(data);
        this.stateClass = stateDef;
    }

    /**
     * 遷移先を指定してオブジェクトを作る。
     *
     * @param stateDef 遷移先の状態のクラス
     */
    public State(Class<? extends StateBase> stateDef) {
        this(stateDef, null);
    }

    /**
     * Machine内部で使用するメソッド。アプリケーションから呼び出してはならない。
     *
     * 状態に対応するStateInfoを取得する。
     *
     * @param machine Machineオブジェクト。
     * @return 自身の状態に対応するSateInfoオブジェクト。
     */
    public StateInfo getInfo(MachineBase machine) {
        return getInfo(machine, stateClass);
    }
}
