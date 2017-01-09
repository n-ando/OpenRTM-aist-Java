package jp.go.aist.rtm.RTC.jfsm.machine;

public class StateAlias {

    protected Object data;

    public StateAlias(Object data) {
        this.data = data;
    }

    public Object takeData() {
        Object data = this.data;
        this.data = null;
        return data;
    }

    /**
     * StateInfoを取得する。MachineBase#getInfo(Class)をprotectedにするため、ここで実装している。
     *
     * @param machine Machineオブジェクト。
     * @param stateClass この状態の情報を取得する。
     * @return StateInfoオブジェクト。
     */
    protected StateInfo getInfo(MachineBase machine, Class<? extends StateBase> stateClass) {
        return machine.getInfo(stateClass);
    }
}
