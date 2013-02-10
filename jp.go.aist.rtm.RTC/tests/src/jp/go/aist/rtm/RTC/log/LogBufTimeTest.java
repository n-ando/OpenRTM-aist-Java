package jp.go.aist.rtm.RTC.log;

import java.util.logging.ConsoleHandler;

import junit.framework.TestCase;

public class LogBufTimeTest extends TestCase {
    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
    *
    * マイクロ秒の出力テスト
    * clock_time=adjustedの場合，clock_time=systemの場合
    * それぞれでマイクロ秒の値が正常に出力されるかを確認する
    */
    public void test_case0() {
        Logbuf rtcout = new Logbuf("test0");

        rtcout.setEnabled();
        rtcout.setClockType("adjusted");
        rtcout.addStream(new ConsoleHandler());
        rtcout.setEnabled();
        // Junit出力ファイル*.xmlの <system-err>の欄に出力される
        System.err.println("--- test_case0() adjusted ---");
        rtcout.println(Logbuf.PARANOID, "PARANOID string to STDOUT 2");
        rtcout.setDateFormat("%b %d %H:%M:%S.%L %q");
        rtcout.println(Logbuf.VERBOSE, "VERBOSE string to STDOUT 2");
        rtcout.println(Logbuf.TRACE, "TRACE string to STDOUT 2");
        rtcout.println(Logbuf.DEBUG, "DEBUG string to STDOUT 2");
        rtcout.println(Logbuf.INFO, "INFO string to STDOUT 2");
        rtcout.println(Logbuf.WARN, "WARN string to STDOUT 2");
        rtcout.println(Logbuf.ERROR, "ERROR string to STDOUT 2");
        rtcout.println(Logbuf.FATAL, "FATAL string to STDOUT 2");
        rtcout.println(Logbuf.SILENT, "SILENT string to STDOUT 2");
        // PARANOIDはマイクロ秒の出力なし
        // VERBOSE以降はマイクロ秒の出力があればOK
        
        System.err.println("--- test_case0() system ---");
        rtcout.setClockType("system");
        rtcout.setDateFormat("%b %d %H:%M:%S.%L");
        rtcout.println(Logbuf.PARANOID, "PARANOID string to STDOUT 2");
        rtcout.setDateFormat("%b %d %H:%M:%S.%L %q");
        rtcout.println(Logbuf.VERBOSE, "VERBOSE string to STDOUT 2");
        rtcout.println(Logbuf.TRACE, "TRACE string to STDOUT 2");
        rtcout.println(Logbuf.DEBUG, "DEBUG string to STDOUT 2");
        rtcout.println(Logbuf.INFO, "INFO string to STDOUT 2");
        rtcout.println(Logbuf.WARN, "WARN string to STDOUT 2");
        rtcout.println(Logbuf.ERROR, "ERROR string to STDOUT 2");
        rtcout.println(Logbuf.FATAL, "FATAL string to STDOUT 2");
        rtcout.println(Logbuf.SILENT, "SILENT string to STDOUT 2");
        // PARANOIDはマイクロ秒の出力なし
        // VERBOSE以降はマイクロ秒の出力があればOK
    }
}
