package jp.go.aist.rtm.RTC.jfsm.machine;

import jp.go.aist.rtm.RTC.jfsm.DataType;

class SubStateInfo extends StateInfo {

    private final Class<? extends StateBase> stateClass;

    public SubStateInfo(Class<? extends StateBase> stateClass, MachineBase machine, StateInfo parent) {
        super(machine, parent);
        this.stateClass = stateClass;
        setInstance(newInstance(stateClass));
        this.setStateInfo();
    }

    private void setStateInfo() {
        getInstance().setStateInfo(this);
    }

    @Override
    protected void createData() {
        final DataType dataType = stateClass.getAnnotation(DataType.class);
        if (dataType == null) {
            return;
        }
        data = newInstance(dataType.value());
    }

    @Override
    public String getName() {
        return getInstance().getStateName();
    }

    private static <T> T newInstance(Class<T> type) {
        try {
            return type.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("failed to instantiate a user defined data - perhaps no default constructor?", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("failed to instantiate a user defined data - maybe not public?", e);
        }
    }
}
