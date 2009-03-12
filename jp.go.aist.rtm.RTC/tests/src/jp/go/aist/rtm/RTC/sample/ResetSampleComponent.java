package jp.go.aist.rtm.RTC.sample;

import RTC.ReturnCode_t;
import jp.go.aist.rtm.RTC.DataFlowComponentBase;
import jp.go.aist.rtm.RTC.Manager;

public class ResetSampleComponent extends DataFlowComponentBase {

    @Override
    protected ReturnCode_t onAborting(int ec_id) {
        System.out.println("ResetSample:onAborting");
        return super.onAborting(ec_id);
    }

    @Override
    protected ReturnCode_t onActivated(int ec_id) {
        System.out.println("ResetSample:onActivated");
        return ReturnCode_t.RTC_ERROR;
    }

    @Override
    protected ReturnCode_t onDeactivated(int ec_id) {
        System.out.println("ResetSample:onDeactivated");
        return super.onDeactivated(ec_id);
    }

    @Override
    protected ReturnCode_t onError(int ec_id) {
//        System.out.println("Sample:onError");
        return super.onError(ec_id);
    }

    @Override
    protected ReturnCode_t onExecute(int ec_id) {
        System.out.println("ResetSample:onExecute");
        return super.onExecute(ec_id);
    }

    @Override
    protected ReturnCode_t onFinalize() {
        System.out.println("ResetSample:onFinalize");
        return super.onFinalize();
    }

    @Override
    protected ReturnCode_t onInitialize() {
        System.out.println("ResetSample:onInitialize");
        return super.onInitialize();
    }

    @Override
    protected ReturnCode_t onRateChanged(int ec_id) {
        System.out.println("ResetSample:onRateChanged");
        return super.onRateChanged(ec_id);
    }

    @Override
    protected ReturnCode_t onReset(int ec_id) {
        System.out.println("ResetSample:onReset");
        return super.onReset(ec_id);
    }

    @Override
    protected ReturnCode_t onShutdown(int ec_id) {
        System.out.println("ResetSample:onShutdown");
        return super.onShutdown(ec_id);
    }

    @Override
    protected ReturnCode_t onStartup(int ec_id) {
        System.out.println("ResetSample:onStartup");
        return super.onStartup(ec_id);
    }

    @Override
    protected ReturnCode_t onStateUpdate(int ec_id) {
        System.out.println("ResetSample:onStateUpdate");
        return super.onStateUpdate(ec_id);
    }

    public ResetSampleComponent(Manager manager) {
        super(manager);
    }

}
