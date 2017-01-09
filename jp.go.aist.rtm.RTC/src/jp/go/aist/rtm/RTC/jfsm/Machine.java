package jp.go.aist.rtm.RTC.jfsm;

import jp.go.aist.rtm.RTC.jfsm.machine.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import jp.go.aist.rtm.RTC.log.Logbuf;

/**
 * 状態遷移マシンの実装クラス。アプリケーションに対するAPIを提供する。
 * アプリケーションはMachineのインスタンスを生成し、実際の状態遷移を実行する。
 * アプリケーションはMachineのインスタンスに対し、イベントの発生、
 * 現在の状態の取得、データの取得などができる。
 *
 * @param <T> ユーザー定義の最上位状態クラス。
 * @param <EP> イベントプロトコルとなるインターフェース。
 */
public class Machine<T extends StateDef, EP> extends MachineBase {

    //private static final Logger LOGGER = LoggerFactory.getLogger(Machine.class);

    private final Class<EP> eventProtocolInterface;

    /**
     * 状態遷移マシンを初期化する。
     *
     * @param topState ユーザー定義の最上位状態クラス。
     * @param eventProtocolInterface ベントプロトコルとなるインターフェース。
     * @param data 最上位状態クラスが初期状態で持つデータ。ない場合はnullを指定する。
     */
    public Machine(Class<T> topState, Class<EP> eventProtocolInterface, Object data) {
        super(topState);

        // some remainings of experimental code
        // require 'org.reflections:reflections:0.9.10'
        // Reflections reflections = new Reflections("");
        // Set<? extends Class<? extends StateBase>> subTypes = reflections.getSubTypesOf(topState);
        // for(Class s: subTypes) {
        // System.out.println(s.getName());
        // }
        this.eventProtocolInterface = eventProtocolInterface;
//        # statechart.h: 909
//        #  Machine(typename TOP::Data * data = 0) {  // NOLINT
//        #    // Compile time check: TOP must directly derive from TopState<TOP>
//        #    TopState<TOP> * p = 0;
//        #    typename TOP::SUPER * p2 = 0;
//        #    p2 = p;
//        #    allocate(the_state_count_);
//        #    start(TOP::get_info(*this), data);
        start(getInfo(topState), data);
//        #  }

        rtcout = new Logbuf("Machine");
    }

    /**
     * 最初の状態を指定して状態遷移マシンを初期化する。
     *
     * @param topState ユーザー定義の最上位状態クラス。
     * @param eventProtocolInterface ベントプロトコルとなるインターフェース。
     * @param state 開始時の状態。
     * @param data 最上位状態クラスが初期状態で持つデータ。ない場合はnullを指定する。
     */
    public Machine(Class<T> topState, Class<EP> eventProtocolInterface, State state, Object data) {
        super(topState);
        this.eventProtocolInterface = eventProtocolInterface;
        start(state.getInfo(this), data);
    }

    private void start(StateInfo info, Object data) {
//        #   HRTM_DEBUG(LOGGER, "Starting Machine");
        rtcout.println(Logbuf.DEBUG, "Starting Machine");
        //LOGGER.debug("Starting Machine");
//        #
//        #   // Start with Root state
//        #   current_state_ = &StateBase::get_info(*this);
        currentState = getInfo(StateDef.class);
//        #   // Then go to state
//        #   set_state(info, false, data);
        setState(info, false, data);
    }

    /**
     * 現在の状態を調べる。
     *
     * @param stateClass ここで指定した状態かどうかを調べる。
     * @return true - 現在stateClassかそのサブ状態。 false - それ以外。
     */
    public boolean isCurrent(Class<? extends T> stateClass) {
        return currentState.isChild(getInfo(stateClass));
    }

    /**
     * 現在の状態を調べる。
     *
     * @param stateClass ここで指定した状態かどうかを調べる。
     * @return true - 現在stateClassの状態(サブ状態は含まない)。 false - それ以外
     */
    public boolean isCurrentDirect(Class<? extends T> stateClass) {
        return currentState == getInfo(stateClass);
    }

    /**
     * イベントを発生させるためのエンドポイントを取得する。
     * イベントハンドラをメソッド呼び出しの形で以下のように記述できる。
     *
     * {@code machine.current().eventHandler(param)}
     *
     * エンドポイントは java.lang.reflect.Proxy のインスタンスとなっている。
     *
     * @return イベントハンドラの呼び出しエンドポイント。
     */
    public EP current() {
        InvocationHandler handler = new MyHandler((EP) currentState.getInstance());
        EP proxy = (EP) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{eventProtocolInterface}, handler);
        return proxy;
    }

    class MyHandler implements InvocationHandler {

        private final EP target;

        MyHandler(EP target) {
            assert target != null;
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object ret = method.invoke(target, args);
            performPending();
            return ret;
        }
    }
}
