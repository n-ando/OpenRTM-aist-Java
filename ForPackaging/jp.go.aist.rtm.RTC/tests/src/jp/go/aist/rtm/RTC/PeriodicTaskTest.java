package jp.go.aist.rtm.RTC;

import junit.framework.TestCase;

import java.util.Vector;
import java.lang.Thread;
import jp.go.aist.rtm.RTC.util.TimeValue;
import jp.go.aist.rtm.RTC.TimeMeasure;

public class PeriodicTaskTest extends TestCase {
    /**
     * <p> Test initialization </p>
     */
    protected void setUp() throws Exception {
        super.setUp();
    }
    /**
     * <p> Test finalization </p>
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    /**
     * <p> </p>
     * 
     */
    public void test_case0() {
    }
    /**
     * <p>signal(),suspend(),resume(),finalize() </p>
     * 
     */
    public void test_signal() {
        MySvc mysvc = new MySvc();
        PeriodicTask p = new PeriodicTask();

        Logger logger = new Logger();
        mysvc.setLogger(logger);

        p.setTask(mysvc, "mysvc2");
        p.setPeriod(0.05);

        assertEquals("1:",0, logger.countLog("mysvc2"));
        p.activate();
        try {
            Thread.sleep(200);
        }
        catch(java.lang.InterruptedException e) {
        }
        p._suspend();

        int count;
        count = logger.countLog("mysvc2");
        try {
            Thread.sleep(200);
        }
        catch(java.lang.InterruptedException e) {
        }
        assertTrue("2:",count == logger.countLog("mysvc2"));

        p.signal();
        try {
            Thread.sleep(200);
        }
        catch(java.lang.InterruptedException e) {
        }
        assertEquals("3:",count+1 , logger.countLog("mysvc2"));
        p.signal();
        try {
            Thread.sleep(200);
        }
        catch(java.lang.InterruptedException e) {
        }
        assertTrue("4:",count+2 == logger.countLog("mysvc2"));

        logger.clearLog();
        p._resume();
        try {
            Thread.sleep(200);
        }
        catch(java.lang.InterruptedException e) {
        }
        p._suspend();
        try {
            Thread.sleep(200);
        }
        catch(java.lang.InterruptedException e) {
        }
        assertTrue("5:",6>logger.countLog("mysvc2"));
        assertTrue("6:"+logger.countLog("mysvc2"),2<logger.countLog("mysvc2"));

        p._finalize();
        try {
            Thread.sleep(200);
        }
        catch(java.lang.InterruptedException e) {
        }
    }
    /**
     * <p> setTask() </p>
     * 
     */
    public void test_setTask() {
        MySvc mysvc = new MySvc();
        PeriodicTask p = new PeriodicTask(); 
        assertTrue(p.setTask(mysvc, "mysvc2"));

        Logger logger = new Logger();
        mysvc.setLogger(logger);

        assertEquals(0, logger.countLog("mysvc2"));
        p.activate();
        try {
            Thread.sleep(10);
        }
        catch(java.lang.InterruptedException e) {
        }
        p._finalize();
        try {
            Thread.sleep(5);
        }
        catch(java.lang.InterruptedException e) {
        }
        assertTrue(1<logger.countLog("mysvc2"));
    }
    /**
     * <p> setPeriod()  </p>
     * 
     */
    public void test_setPeriod() {
        String ss = new String();
        MySvc mysvc = new MySvc();
        PeriodicTask p = new PeriodicTask();

        Logger logger = new Logger();
        mysvc.setLogger(logger);

        p.setTask(mysvc, "mysvc2");
        p.setPeriod(0.05);

        assertEquals(0, logger.countLog("mysvc2"));
        p.activate();
        try {
            Thread.sleep(100);
        }
        catch(java.lang.InterruptedException e) {
        }
        p._suspend();
        try {
            Thread.sleep(50);
        }
        catch(java.lang.InterruptedException e) {
        }

//        System.out.println(logger.countLog("mysvc2"));
        assertTrue(4>logger.countLog("mysvc2"));
        assertTrue(0<logger.countLog("mysvc2"));

        logger.clearLog();

        p.setPeriod(0.01);
        assertEquals(0, logger.countLog("mysvc2"));
        p._resume();
        try {
            Thread.sleep(100);
        }
        catch(java.lang.InterruptedException e) {
        }
        p._suspend();
        try {
            Thread.sleep(100);
        }
        catch(java.lang.InterruptedException e) {
        }
//        System.out.println(logger.countLog("mysvc2"));
        assertTrue(12>logger.countLog("mysvc2"));
        assertTrue(8<logger.countLog("mysvc2"));

        logger.clearLog();

        TimeValue tm = new TimeValue(0.050);
        p.setPeriod(tm);
        assertEquals(0, logger.countLog("mysvc2"));
        p._resume();
        try {
            Thread.sleep(100);
        }
        catch(java.lang.InterruptedException e) {
        }
        p._finalize();
        try {
            Thread.sleep(50);
        }
        catch(java.lang.InterruptedException e) {
        }
//        System.out.println(logger.countLog("mysvc2"));
        assertTrue(4>logger.countLog("mysvc2"));
        assertTrue(0<logger.countLog("mysvc2"));
    }
    /**
     * <p> executionMeasure </p>
     * The execution cycle is 50ms. The execution time of svc is 30ms. 
     * 
     */
    public void test_executionMeasure() {
/*
System.out.println("test_executionMeasure---000---");
        final double wait = 0.03; // [s]
        MySvc mysvc = new MySvc();
        PeriodicTask p = new PeriodicTask();
        p.setTask(mysvc, "mysvc3");

        Logger logger = new Logger();
        mysvc.setLogger(logger);

        p.setPeriod(0.05);
        p.executionMeasure(true);

        //periodicMeasureConut is tested in the state of the default value. 
        p.activate();

        try {
            Thread.sleep(600);
        }
        catch(java.lang.InterruptedException e) {
        }

        p._suspend();
        try {
            Thread.sleep(50);
        }
        catch(java.lang.InterruptedException e) {
        }
System.out.println("test_executionMeasure---020---");
        TimeMeasure.Statistics estat = p.getExecStat();
        
        //System.out.println("estat max:  " + estat.max_interval);
        //System.out.println("estat min:  " + estat.min_interval);
        //System.out.println("estat mean: " + estat.mean_interval);
        //System.out.println("estat sdev: " + estat.std_deviation);
        
System.out.println("test_executionMeasure---030---");
        String ss = new String(); 
        String crlf = System.getProperty("line.separator");
        ss = ss + "wait:  " + wait + crlf;
        ss = ss + "estat max:  " + estat.max_interval + crlf;
        ss = ss + "estat min:  " + estat.min_interval + crlf;
        ss = ss + "estat mean: " + estat.mean_interval + crlf;
        ss = ss + "estat sdev: " + estat.std_deviation + crlf;
      
System.out.println("test_executionMeasure---040---");
        assertTrue(ss,estat.max_interval < (wait + 0.030));
        assertTrue(ss,estat.min_interval > (wait - 0.015));
        assertTrue(ss,Math.abs(estat.mean_interval - wait) < 0.03);
        assertTrue(ss,estat.std_deviation < (wait / 5.0));




        //5 is set to executionMeasureCount.
        p.executionMeasureCount(5);
        p._resume();
        try {
            Thread.sleep(300);
        }
        catch(java.lang.InterruptedException e) {
        }
        p._suspend();
        try {
            Thread.sleep(50);
        }
        catch(java.lang.InterruptedException e) {
        }
System.out.println("test_executionMeasure---050---");
        estat = p.getExecStat();
        
        //System.out.println("estat max:  " + estat.max_interval);
        //System.out.println("estat min:  " + estat.min_interval);
        //System.out.println("estat mean: " + estat.mean_interval);
        //System.out.println("estat sdev: " + estat.std_deviation);
        
System.out.println("test_executionMeasure---060---");
        ss ="";
        ss = ss + "wait:  " +  wait + crlf;
        ss = ss + "estat max:  " + estat.max_interval + crlf;
        ss = ss + "estat min:  " + estat.min_interval + crlf;
        ss = ss + "estat mean: " + estat.mean_interval + crlf;
        ss = ss + "estat sdev: " + estat.std_deviation + crlf;

System.out.println("test_executionMeasure---070---");
        assertTrue(ss,estat.max_interval < (wait + 0.030));
        assertTrue(ss,estat.min_interval > (wait - 0.015));
        assertTrue(ss,Math.abs(estat.mean_interval - wait) < 0.03);
        assertTrue(ss,estat.std_deviation < (wait / 5.0));

        //The execution frequency doesn't reach executionMeasureConut.
        p.executionMeasureCount(10);
        p._resume();
        try {
            Thread.sleep(300);
        }
        catch(java.lang.InterruptedException e) {
        }
        p._suspend();
        try {
            Thread.sleep(5);
        }
        catch(java.lang.InterruptedException e) {
        }
        p._finalize();
System.out.println("test_executionMeasure---080---");
        TimeMeasure.Statistics estat2 = p.getExecStat();
        //Because the frequency is less than periodicMeasureConut, 
        // the last value is returned. 
System.out.println("test_executionMeasure---090---");
        assertTrue(estat.max_interval == estat2.max_interval);
        assertTrue(estat.min_interval == estat2.min_interval);
        assertTrue(estat.mean_interval == estat2.mean_interval);
        assertTrue(estat.std_deviation == estat2.std_deviation);
*/
    }
    /**
     * <p> executionMeasure </p>
     * The execution cycle is 50ms. The execution time of svc is 30ms. 
     * 
     */
    public void test_periodicMeasure() {

        final  double wait = 0.05; // [s]
        MySvc mysvc = new MySvc();
        PeriodicTask p = new PeriodicTask();
        p.setTask(mysvc, "mysvc3");

        Logger logger = new Logger();
        mysvc.setLogger(logger);

        p.setPeriod(0.05);
        p.periodicMeasure(true);
        p.executionMeasure(true);

        p.activate();
        //periodicMeasureConut is tested in the state of the default value. 
        try {
            Thread.sleep(600);
        }
        catch(java.lang.InterruptedException e) {
        }

        p._suspend();
        try {
            Thread.sleep(50);
        }
        catch(java.lang.InterruptedException e) {
        }
        TimeMeasure.Statistics pstat = p.getPeriodStat();
        String ss = new String(); 
        String crlf = System.getProperty("line.separator");
        ss = ss + "wait:  " + wait + crlf;
        ss = ss + "pstat max:  " + pstat.max_interval + crlf;
        ss = ss + "pstat min:  " + pstat.min_interval + crlf;
        ss = ss + "pstat mean: " + pstat.mean_interval + crlf;
        ss = ss + "pstat sdev: " + pstat.std_deviation + crlf;
        assertTrue(ss,pstat.max_interval < (wait + 0.030));
        assertTrue(ss,pstat.min_interval > (wait - 0.010));
        assertTrue(ss,Math.abs(pstat.mean_interval - wait) < 0.03);
        assertTrue(ss,pstat.std_deviation < (wait / 5.0));


        //5 is set to executionMeasureCount.
        p.periodicMeasureCount(5);
        p._resume();
        try {
            Thread.sleep(300);
        }
        catch(java.lang.InterruptedException e) {
        }
        p._suspend();
        try {
            Thread.sleep(5);
        }
        catch(java.lang.InterruptedException e) {
        }
        pstat = p.getPeriodStat();
        assertTrue(pstat.max_interval < (wait + 0.030));
        assertTrue(pstat.min_interval > (wait - 0.010));
        assertTrue(Math.abs(pstat.mean_interval - wait) < 0.03);
        assertTrue(pstat.std_deviation < (wait / 5.0));


        //The execution frequency doesn't reach executionMeasureConut.
        p.periodicMeasureCount(10);
        p._resume();
        try {
            Thread.sleep(300);
        }
        catch(java.lang.InterruptedException e) {
        }
        p._suspend();
        try {
            Thread.sleep(5);
        }
        catch(java.lang.InterruptedException e) {
        }
        p._finalize();
        TimeMeasure.Statistics pstat2 = p.getPeriodStat();
        //Because the frequency is less than periodicMeasureConut, 
        // the last value is returned. 
        assertTrue(pstat.max_interval == pstat2.max_interval);
        assertTrue(pstat.min_interval == pstat2.min_interval);
        assertTrue(pstat.mean_interval == pstat2.mean_interval);
        assertTrue(pstat.std_deviation == pstat2.std_deviation);

    }

}

/**
 * <p>  </p>
 */
class MySvc {
    public int mysvc() {
//        static int cnt;
        System.out.println("mysvc(): " + cnt);
        ++cnt;
        try {
            Thread.sleep(10);
        }
        catch(java.lang.InterruptedException e) {
        }
        return 0;
    }

    public int mysvc2() {
        if (m_logger != null) {
            m_logger.log("mysvc2");
        } 
        return 0;
    }

    public int mysvc3() {
        if (m_logger != null) {
            m_logger.log("mysvc3");
        }
        try {
            Thread.sleep(30);
        }
        catch(java.lang.InterruptedException e) {
        }
        return 0;
    }

    public static void setLogger(Logger logger) {
        m_logger = logger;
    }

    static int cnt;
    private static Logger m_logger;
}
/**
 * <p>  </p>
 */
class Logger {
    public void log(final String msg) {
        m_log.add(msg);
    }
		
    public int countLog(final String msg) {
        int count = 0;
        for (int i = 0; i < (int) m_log.size(); ++i) {
            if (m_log.elementAt(i) == msg) {
                ++count;
             }
        }
        return count;
    }

    public void clearLog() {
        m_log.clear();
    }
    private Vector<String> m_log = new Vector<String>();
};

