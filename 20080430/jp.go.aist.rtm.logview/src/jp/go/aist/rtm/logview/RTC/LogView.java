package jp.go.aist.rtm.logview.RTC;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.ModuleInitProc;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.RtcDeleteFunc;
import jp.go.aist.rtm.RTC.RtcNewFunc;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.logview.view.LogViewPart;

public class LogView implements ModuleInitProc, RtcNewFunc, RtcDeleteFunc, Runnable {
//  Module specification
//  <rtc-template block="module_spec">
    public static String component_conf[] = {
            "implementation_id", "LogView",
            "type_name",         "LogViewComponent",
            "description",       "LogView component",
            "version",           "1.0",
            "vendor",            "TA",
            "category",          "example",
            "activity_type",     "DataFlowComponent",
            "max_instance",      "10",
            "language",          "Java",
            "lang_type",         "compile",
            // Configuration variables
            "conf.default.RedrawCycle","100",
            ""
            };
//  </rtc-template>
    private String[] m_args;
	private static LogViewPart m_view;
    private static LogViewImpl m_logview;
    
    public RTObject_impl createRtc(Manager mgr) {
    	m_logview = new LogViewImpl(mgr);
    	m_logview.setLogView(this.m_view);
        return m_logview;
    }

    public void deleteRtc(RTObject_impl rtcBase) {
        rtcBase = null;
    }

    public void myModuleInit(Manager mgr) {
      Properties prop = new Properties(component_conf);
      mgr.registerFactory(prop, new LogView(), new LogView());
      mgr.createComponent("LogView");
    }

    public int open(LogViewPart view, String[] args) {
    	this.m_view = view;
    	m_args = args;
        Thread t = new Thread(this);
        t.start();
        return 0;
    }
    public void setView(LogViewPart view) {
    	this.m_view = view;
    	m_logview.setLogView(this.m_view);
    }
	public void run() {
//    public static void main(String[] args) {
        // Initialize manager
        final Manager manager = Manager.init(m_args);

        // Set module initialization proceduer
        // This procedure will be invoked in activateManager() function.
        LogView comp = new LogView();
        comp.m_view = this.m_view;
        manager.setModuleInitProc(comp);

        // Activate manager and register to naming service
        manager.activateManager();

        // run the manager in blocking mode
        // runManager(false) is the default.
        manager.runManager();

        // If you want to run the manager in non-blocking mode, do like this
        // manager->runManager(true);
    }


}
