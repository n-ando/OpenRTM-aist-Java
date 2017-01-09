package jp.go.aist.rtm.RTC.jfsm.machine;

class RootStateInfo extends StateInfo {

    public <T, D> RootStateInfo(MachineBase machine, StateInfo parent) {
        super(machine, parent);
        setInstance(new StateBase());
        this.setStateInfo();
    }

    private void setStateInfo() {
        getInstance().setStateInfo(this);
    }

    @Override
    protected void createData() {
        // do nothing
    }

    @Override
    public String getName() {
        return "Root";
    }
}
