package jp.go.aist.rtm.RTC.sample;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.ModuleInitProc;

public class loadSample implements ModuleInitProc {
    private static int m_counter = 0;
    private static int m_counter2 = 0;
    public void SampleMethod() {
        System.out.println("Sample Method invoked.");
    }
    public void myModuleInit(Manager mgr) {
        m_counter++;
    }
    public static int getInitProcCount() {
        return m_counter;
    }
    public static void resetInitProcCount() {
        m_counter = 0;
    }
    public static void addInitProcCount() {
        m_counter++;
    }
}
