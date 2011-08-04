package jp.go.aist.rtm.RTC.sample;

import RTC.ReturnCode_t;
import jp.go.aist.rtm.RTC.DataFlowComponentBase;
import jp.go.aist.rtm.RTC.Manager;

public class SampleComponent extends DataFlowComponentBase {

    @Override
    protected ReturnCode_t onAborting(int ec_id) {
        System.out.println("Sample:onAborting");
        return super.onAborting(ec_id);
    }

    @Override
    protected ReturnCode_t onActivated(int ec_id) {
        System.out.println("Sample:onActivated");
        return super.onActivated(ec_id);
    }

    @Override
    protected ReturnCode_t onDeactivated(int ec_id) {
        System.out.println("Sample:onDeactivated");
        return super.onDeactivated(ec_id);
    }

    @Override
    protected ReturnCode_t onError(int ec_id) {
        System.out.println("Sample:onError");
        return super.onError(ec_id);
    }

    @Override
    protected ReturnCode_t onExecute(int ec_id) {
        System.out.println("Sample:onExecute");
        return super.onExecute(ec_id);
    }

    @Override
    protected ReturnCode_t onFinalize() {
        System.out.println("Sample:onFinalize");
        return super.onFinalize();
    }

    @Override
    protected ReturnCode_t onInitialize() {
        System.out.println("Sample:onInitialize");
        return super.onInitialize();
    }

    @Override
    protected ReturnCode_t onRateChanged(int ec_id) {
        System.out.println("Sample:onRateChanged");
        return super.onRateChanged(ec_id);
    }

    @Override
    protected ReturnCode_t onReset(int ec_id) {
        System.out.println("Sample:onReset");
        return super.onReset(ec_id);
    }

    @Override
    protected ReturnCode_t onShutdown(int ec_id) {
        System.out.println("Sample:onShutdown");
        return super.onShutdown(ec_id);
    }

    @Override
    protected ReturnCode_t onStartup(int ec_id) {
        System.out.println("Sample:onStartup");
        return super.onStartup(ec_id);
    }

    @Override
    protected ReturnCode_t onStateUpdate(int ec_id) {
        System.out.println("Sample:onStateUpdate");
        return super.onStateUpdate(ec_id);
    }

    public SampleComponent(Manager manager) {
        super(manager);
    }
    
}
