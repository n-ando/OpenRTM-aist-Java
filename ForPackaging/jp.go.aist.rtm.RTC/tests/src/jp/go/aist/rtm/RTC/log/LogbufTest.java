package jp.go.aist.rtm.RTC.log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import jp.go.aist.rtm.RTC.Manager;
import junit.framework.TestCase;

/**
*
* ロギングクラス　テスト
* 対象クラス：Logbuf
*
*/
public class LogbufTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
    *
    * STDOUT出力のテスト
    * setLevel()未設定時と設定時の出力を確認する
    */
    public void test_case0() {
        Logbuf rtcout = new Logbuf("test0");

        rtcout.setEnabled();
        // STDOUT
        // Junit出力ファイル*.xmlの <system-err>の欄に出力される
        System.err.println("\n\n--- test_case0() setLevel() Not Set ---");
        rtcout.println(Logbuf.PARANOID, "PARANOID string to STDOUT 0");
        rtcout.println(Logbuf.VERBOSE, "VERBOSE string to STDOUT 0");
        rtcout.println(Logbuf.TRACE, "TRACE string to STDOUT 0");
        rtcout.println(Logbuf.DEBUG, "DEBUG string to STDOUT 0");
        rtcout.println(Logbuf.INFO, "INFO string to STDOUT 0");
        rtcout.println(Logbuf.WARN, "WARN string to STDOUT 0");
        rtcout.println(Logbuf.ERROR, "ERROR string to STDOUT 0");
        rtcout.println(Logbuf.FATAL, "FATAL string to STDOUT 0");
        rtcout.println(Logbuf.SILENT, "SILENT string to STDOUT 0");
        // addStream()していないので、printlnの文字列が出力されなければOK.
        // "Logbuf.println() destination handler was not registered."の出力あればOK.

        rtcout.setLevel("PARANOID");    //全て
        System.err.println("--- test_case0() setLevel(PARANOID), addStream(STDOUT) Not set ---");
        rtcout.println(Logbuf.PARANOID, "PARANOID string to STDOUT 1");
        rtcout.println(Logbuf.VERBOSE, "VERBOSE string to STDOUT 1");
        rtcout.println(Logbuf.TRACE, "TRACE string to STDOUT 1");
        rtcout.println(Logbuf.DEBUG, "DEBUG string to STDOUT 1");
        rtcout.println(Logbuf.INFO, "INFO string to STDOUT 1");
        rtcout.println(Logbuf.WARN, "WARN string to STDOUT 1");
        rtcout.println(Logbuf.ERROR, "ERROR string to STDOUT 1");
        rtcout.println(Logbuf.FATAL, "FATAL string to STDOUT 1");
        rtcout.println(Logbuf.SILENT, "SILENT string to STDOUT 1");
        // addStream()していないので、printlnの文字列が出力されなければOK.
        // "Logbuf.println() destination handler was not registered."の出力あればOK.

        LocalHandler handler = new LocalHandler();
        rtcout.addStream(handler);
        rtcout.setClockType("logical");
//        rtcout.addStream(new ConsoleHandler());
        System.err.println("--- test_case0() addStream(STDOUT) set ---");

        rtcout.setDisabled();
        rtcout.println(Logbuf.PARANOID, "PARANOID string to STDOUT 2");
        rtcout.println(Logbuf.VERBOSE, "VERBOSE string to STDOUT 2");
        rtcout.println(Logbuf.TRACE, "TRACE string to STDOUT 2");
        rtcout.println(Logbuf.DEBUG, "DEBUG string to STDOUT 2");
        rtcout.println(Logbuf.INFO, "INFO string to STDOUT 2");
        rtcout.println(Logbuf.WARN, "WARN string to STDOUT 2");
        rtcout.println(Logbuf.ERROR, "ERROR string to STDOUT 2");
        rtcout.println(Logbuf.FATAL, "FATAL string to STDOUT 2");
        rtcout.println(Logbuf.SILENT, "SILENT string to STDOUT 2");
        // 何も出力されなければOK.
        String expected = "";
        assertEquals(expected, handler.getStr());

        rtcout.setEnabled();
        System.err.println("--- test_case0() addStream(STDOUT) set ---");
        rtcout.println(Logbuf.PARANOID, "PARANOID string to STDOUT 2");
        rtcout.println(Logbuf.VERBOSE, "VERBOSE string to STDOUT 2");
        rtcout.println(Logbuf.TRACE, "TRACE string to STDOUT 2");
        rtcout.println(Logbuf.DEBUG, "DEBUG string to STDOUT 2");
        rtcout.println(Logbuf.INFO, "INFO string to STDOUT 2");
        rtcout.println(Logbuf.WARN, "WARN string to STDOUT 2");
        rtcout.println(Logbuf.ERROR, "ERROR string to STDOUT 2");
        rtcout.println(Logbuf.FATAL, "FATAL string to STDOUT 2");
        rtcout.println(Logbuf.SILENT, "SILENT string to STDOUT 2");
        // setLevel()以下の内容が、出力されればOK.
        expected = "00 000 test0 PARANOID : PARANOID string to STDOUT 2" + System.getProperty("line.separator") +
                   "00 000 test0 VERBOSE  : VERBOSE string to STDOUT 2" + System.getProperty("line.separator") +
                   "00 000 test0 TRACE    : TRACE string to STDOUT 2" + System.getProperty("line.separator") +
                   "00 000 test0 DEBUG    : DEBUG string to STDOUT 2" + System.getProperty("line.separator") +
                   "00 000 test0 INFO     : INFO string to STDOUT 2" + System.getProperty("line.separator") +
                   "00 000 test0 WARN     : WARN string to STDOUT 2" + System.getProperty("line.separator") +
                   "00 000 test0 ERROR    : ERROR string to STDOUT 2" + System.getProperty("line.separator") +
                   "00 000 test0 FATAL    : FATAL string to STDOUT 2" + System.getProperty("line.separator") +
                   "00 000 test0 SILENT   : SILENT string to STDOUT 2" + System.getProperty("line.separator");
        assertEquals(expected, handler.getStr());
    }

    /**
    *
    * STDOUT出力のテスト
    * setLevel(String)毎に出力内容が制限されているか？
    */
    public void test_stdout1() {
        Logbuf rtcout = new Logbuf("test1");

        rtcout.setEnabled();
        // STDOUT
//        // Junit出力ファイル*.xmlの <system-err>の欄に出力される
//        rtcout.addStream(new ConsoleHandler());
        rtcout.setEnabled();
        rtcout.setClockType("logical");
        rtcout.setLevel("PARANOID");
        LocalHandler handler = new LocalHandler();
        rtcout.addStream(handler);

        rtcout.setLevel("PARANOID");
        rtcout.println(Logbuf.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(Logbuf.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(Logbuf.TRACE, "TRACE string to STDOUT");
        rtcout.println(Logbuf.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(Logbuf.INFO, "INFO string to STDOUT");
        rtcout.println(Logbuf.WARN, "WARN string to STDOUT");
        rtcout.println(Logbuf.ERROR, "ERROR string to STDOUT");
        rtcout.println(Logbuf.FATAL, "FATAL string to STDOUT");
        rtcout.println(Logbuf.SILENT, "SILENT string to STDOUT");
        // setLevel()以下の内容が、出力されればOK.
        String expected =
            "00 000 test1 PARANOID : PARANOID string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test1 VERBOSE  : VERBOSE string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test1 TRACE    : TRACE string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test1 DEBUG    : DEBUG string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test1 INFO     : INFO string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test1 WARN     : WARN string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test1 ERROR    : ERROR string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test1 FATAL    : FATAL string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test1 SILENT   : SILENT string to STDOUT" + System.getProperty("line.separator");
        assertEquals(expected, handler.getStr());

        handler.clear();
        rtcout.setLevel("VERBOSE");
        rtcout.println(Logbuf.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(Logbuf.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(Logbuf.TRACE, "TRACE string to STDOUT");
        rtcout.println(Logbuf.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(Logbuf.INFO, "INFO string to STDOUT");
        rtcout.println(Logbuf.WARN, "WARN string to STDOUT");
        rtcout.println(Logbuf.ERROR, "ERROR string to STDOUT");
        rtcout.println(Logbuf.FATAL, "FATAL string to STDOUT");
        rtcout.println(Logbuf.SILENT, "SILENT string to STDOUT");
        // setLevel()以下の内容が、出力されればOK.
        expected =
            "00 000 test1 VERBOSE  : VERBOSE string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test1 TRACE    : TRACE string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test1 DEBUG    : DEBUG string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test1 INFO     : INFO string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test1 WARN     : WARN string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test1 ERROR    : ERROR string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test1 FATAL    : FATAL string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test1 SILENT   : SILENT string to STDOUT" + System.getProperty("line.separator");
        assertEquals(expected, handler.getStr());

        handler.clear();
        rtcout.setLevel("TRACE");
        rtcout.println(Logbuf.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(Logbuf.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(Logbuf.TRACE, "TRACE string to STDOUT");
        rtcout.println(Logbuf.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(Logbuf.INFO, "INFO string to STDOUT");
        rtcout.println(Logbuf.WARN, "WARN string to STDOUT");
        rtcout.println(Logbuf.ERROR, "ERROR string to STDOUT");
        rtcout.println(Logbuf.FATAL, "FATAL string to STDOUT");
        rtcout.println(Logbuf.SILENT, "SILENT string to STDOUT");
        // setLevel()以下の内容が、出力されればOK.
        expected =
            "00 000 test1 TRACE    : TRACE string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test1 DEBUG    : DEBUG string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test1 INFO     : INFO string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test1 WARN     : WARN string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test1 ERROR    : ERROR string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test1 FATAL    : FATAL string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test1 SILENT   : SILENT string to STDOUT" + System.getProperty("line.separator");
        assertEquals(expected, handler.getStr());

        handler.clear();
        rtcout.setLevel("DEBUG");
        System.err.println("--- setLevel:DEBUG check ---");
        rtcout.println(Logbuf.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(Logbuf.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(Logbuf.TRACE, "TRACE string to STDOUT");
        rtcout.println(Logbuf.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(Logbuf.INFO, "INFO string to STDOUT");
        rtcout.println(Logbuf.WARN, "WARN string to STDOUT");
        rtcout.println(Logbuf.ERROR, "ERROR string to STDOUT");
        rtcout.println(Logbuf.FATAL, "FATAL string to STDOUT");
        rtcout.println(Logbuf.SILENT, "SILENT string to STDOUT");
        // setLevel()以下の内容が、出力されればOK.
        expected =
            "00 000 test1 DEBUG    : DEBUG string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test1 INFO     : INFO string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test1 WARN     : WARN string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test1 ERROR    : ERROR string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test1 FATAL    : FATAL string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test1 SILENT   : SILENT string to STDOUT" + System.getProperty("line.separator");
        assertEquals(expected, handler.getStr());

        handler.clear();
        rtcout.setLevel("INFO");
        rtcout.println(Logbuf.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(Logbuf.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(Logbuf.TRACE, "TRACE string to STDOUT");
        rtcout.println(Logbuf.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(Logbuf.INFO, "INFO string to STDOUT");
        rtcout.println(Logbuf.WARN, "WARN string to STDOUT");
        rtcout.println(Logbuf.ERROR, "ERROR string to STDOUT");
        rtcout.println(Logbuf.FATAL, "FATAL string to STDOUT");
        rtcout.println(Logbuf.SILENT, "SILENT string to STDOUT");
        // setLevel()以下の内容が、出力されればOK.
        expected =
            "00 000 test1 INFO     : INFO string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test1 WARN     : WARN string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test1 ERROR    : ERROR string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test1 FATAL    : FATAL string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test1 SILENT   : SILENT string to STDOUT" + System.getProperty("line.separator");
        assertEquals(expected, handler.getStr());

        handler.clear();
        rtcout.setLevel("WARN");
        System.err.println("--- setLevel:WARN check ---");
        rtcout.println(Logbuf.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(Logbuf.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(Logbuf.TRACE, "TRACE string to STDOUT");
        rtcout.println(Logbuf.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(Logbuf.INFO, "INFO string to STDOUT");
        rtcout.println(Logbuf.WARN, "WARN string to STDOUT");
        rtcout.println(Logbuf.ERROR, "ERROR string to STDOUT");
        rtcout.println(Logbuf.FATAL, "FATAL string to STDOUT");
        rtcout.println(Logbuf.SILENT, "SILENT string to STDOUT");
        // setLevel()以下の内容が、出力されればOK.
        expected =
            "00 000 test1 WARN     : WARN string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test1 ERROR    : ERROR string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test1 FATAL    : FATAL string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test1 SILENT   : SILENT string to STDOUT" + System.getProperty("line.separator");
        assertEquals(expected, handler.getStr());

        handler.clear();
        rtcout.setLevel("ERROR");
        System.err.println("--- setLevel:ERROR check ---");
        rtcout.println(Logbuf.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(Logbuf.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(Logbuf.TRACE, "TRACE string to STDOUT");
        rtcout.println(Logbuf.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(Logbuf.INFO, "INFO string to STDOUT");
        rtcout.println(Logbuf.WARN, "WARN string to STDOUT");
        rtcout.println(Logbuf.ERROR, "ERROR string to STDOUT");
        rtcout.println(Logbuf.FATAL, "FATAL string to STDOUT");
        rtcout.println(Logbuf.SILENT, "SILENT string to STDOUT");
        // setLevel()以下の内容が、出力されればOK.
        expected =
            "00 000 test1 ERROR    : ERROR string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test1 FATAL    : FATAL string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test1 SILENT   : SILENT string to STDOUT" + System.getProperty("line.separator");
        assertEquals(expected, handler.getStr());

        handler.clear();
        rtcout.setLevel("FATAL");
        System.err.println("--- setLevel:FATAL check ---");
        rtcout.println(Logbuf.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(Logbuf.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(Logbuf.TRACE, "TRACE string to STDOUT");
        rtcout.println(Logbuf.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(Logbuf.INFO, "INFO string to STDOUT");
        rtcout.println(Logbuf.WARN, "WARN string to STDOUT");
        rtcout.println(Logbuf.ERROR, "ERROR string to STDOUT");
        rtcout.println(Logbuf.FATAL, "FATAL string to STDOUT");
        rtcout.println(Logbuf.SILENT, "SILENT string to STDOUT");
        // setLevel()以下の内容が、出力されればOK.
        expected =
            "00 000 test1 FATAL    : FATAL string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test1 SILENT   : SILENT string to STDOUT" + System.getProperty("line.separator");
        assertEquals(expected, handler.getStr());

        handler.clear();
        rtcout.setLevel("SILENT");
        System.err.println("--- setLevel:SILENT check ---");
        rtcout.println(Logbuf.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(Logbuf.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(Logbuf.TRACE, "TRACE string to STDOUT");
        rtcout.println(Logbuf.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(Logbuf.INFO, "INFO string to STDOUT");
        rtcout.println(Logbuf.WARN, "WARN string to STDOUT");
        rtcout.println(Logbuf.ERROR, "ERROR string to STDOUT");
        rtcout.println(Logbuf.FATAL, "FATAL string to STDOUT");
        rtcout.println(Logbuf.SILENT, "SILENT string to STDOUT");
        // 何も出力されなければOK.
        expected = "";
        assertEquals(expected, handler.getStr());

        handler.clear();
        rtcout.setLevel("other");
        System.err.println("--- setLevel:other check ---");
        rtcout.println(Logbuf.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(Logbuf.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(Logbuf.TRACE, "TRACE string to STDOUT");
        rtcout.println(Logbuf.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(Logbuf.INFO, "INFO string to STDOUT");
        rtcout.println(Logbuf.WARN, "WARN string to STDOUT");
        rtcout.println(Logbuf.ERROR, "ERROR string to STDOUT");
        rtcout.println(Logbuf.FATAL, "FATAL string to STDOUT");
        rtcout.println(Logbuf.SILENT, "SILENT string to STDOUT");
        // 何も出力されなければOK.
        expected = "";
        assertEquals(expected, handler.getStr());

    }

    /**
    *
    * STDOUT出力のテスト
    * setLevel(int)毎に出力内容が制限されているか？
    */
    public void test_stdout2() {
        Logbuf rtcout = new Logbuf("test2");

        rtcout.setEnabled();
        // STDOUT
//        // Junit出力ファイル*.xmlの <system-err>の欄に出力される
//        rtcout.addStream(new ConsoleHandler());
        rtcout.setEnabled();
        rtcout.setClockType("logical");
        rtcout.setLevel("PARANOID");
        LocalHandler handler = new LocalHandler();
        rtcout.addStream(handler);

        rtcout.setLevel(Logbuf.PARANOID);
        rtcout.println(Logbuf.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(Logbuf.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(Logbuf.TRACE, "TRACE string to STDOUT");
        rtcout.println(Logbuf.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(Logbuf.INFO, "INFO string to STDOUT");
        rtcout.println(Logbuf.WARN, "WARN string to STDOUT");
        rtcout.println(Logbuf.ERROR, "ERROR string to STDOUT");
        rtcout.println(Logbuf.FATAL, "FATAL string to STDOUT");
        rtcout.println(Logbuf.SILENT, "SILENT string to STDOUT");
        // setLevel()以下の内容が、出力されればOK.
        String expected =
            "00 000 test2 PARANOID : PARANOID string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test2 VERBOSE  : VERBOSE string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test2 TRACE    : TRACE string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test2 DEBUG    : DEBUG string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test2 INFO     : INFO string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test2 WARN     : WARN string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test2 ERROR    : ERROR string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test2 FATAL    : FATAL string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test2 SILENT   : SILENT string to STDOUT" + System.getProperty("line.separator");
        assertEquals(expected, handler.getStr());

        handler.clear();
        rtcout.setLevel(Logbuf.VERBOSE);
        rtcout.println(Logbuf.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(Logbuf.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(Logbuf.TRACE, "TRACE string to STDOUT");
        rtcout.println(Logbuf.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(Logbuf.INFO, "INFO string to STDOUT");
        rtcout.println(Logbuf.WARN, "WARN string to STDOUT");
        rtcout.println(Logbuf.ERROR, "ERROR string to STDOUT");
        rtcout.println(Logbuf.FATAL, "FATAL string to STDOUT");
        rtcout.println(Logbuf.SILENT, "SILENT string to STDOUT");
        // setLevel()以下の内容が、出力されればOK.
        expected =
            "00 000 test2 VERBOSE  : VERBOSE string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test2 TRACE    : TRACE string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test2 DEBUG    : DEBUG string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test2 INFO     : INFO string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test2 WARN     : WARN string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test2 ERROR    : ERROR string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test2 FATAL    : FATAL string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test2 SILENT   : SILENT string to STDOUT" + System.getProperty("line.separator");
        assertEquals(expected, handler.getStr());

        handler.clear();
        rtcout.setLevel(Logbuf.TRACE);
        rtcout.println(Logbuf.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(Logbuf.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(Logbuf.TRACE, "TRACE string to STDOUT");
        rtcout.println(Logbuf.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(Logbuf.INFO, "INFO string to STDOUT");
        rtcout.println(Logbuf.WARN, "WARN string to STDOUT");
        rtcout.println(Logbuf.ERROR, "ERROR string to STDOUT");
        rtcout.println(Logbuf.FATAL, "FATAL string to STDOUT");
        rtcout.println(Logbuf.SILENT, "SILENT string to STDOUT");
        // setLevel()以下の内容が、出力されればOK.
        expected =
            "00 000 test2 TRACE    : TRACE string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test2 DEBUG    : DEBUG string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test2 INFO     : INFO string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test2 WARN     : WARN string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test2 ERROR    : ERROR string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test2 FATAL    : FATAL string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test2 SILENT   : SILENT string to STDOUT" + System.getProperty("line.separator");
        assertEquals(expected, handler.getStr());

        handler.clear();
        rtcout.setLevel(Logbuf.DEBUG);
        rtcout.println(Logbuf.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(Logbuf.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(Logbuf.TRACE, "TRACE string to STDOUT");
        rtcout.println(Logbuf.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(Logbuf.INFO, "INFO string to STDOUT");
        rtcout.println(Logbuf.WARN, "WARN string to STDOUT");
        rtcout.println(Logbuf.ERROR, "ERROR string to STDOUT");
        rtcout.println(Logbuf.FATAL, "FATAL string to STDOUT");
        rtcout.println(Logbuf.SILENT, "SILENT string to STDOUT");
        // setLevel()以下の内容が、出力されればOK.
        expected =
            "00 000 test2 DEBUG    : DEBUG string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test2 INFO     : INFO string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test2 WARN     : WARN string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test2 ERROR    : ERROR string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test2 FATAL    : FATAL string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test2 SILENT   : SILENT string to STDOUT" + System.getProperty("line.separator");
        assertEquals(expected, handler.getStr());

        handler.clear();
        rtcout.setLevel(Logbuf.INFO);
        rtcout.println(Logbuf.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(Logbuf.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(Logbuf.TRACE, "TRACE string to STDOUT");
        rtcout.println(Logbuf.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(Logbuf.INFO, "INFO string to STDOUT");
        rtcout.println(Logbuf.WARN, "WARN string to STDOUT");
        rtcout.println(Logbuf.ERROR, "ERROR string to STDOUT");
        rtcout.println(Logbuf.FATAL, "FATAL string to STDOUT");
        rtcout.println(Logbuf.SILENT, "SILENT string to STDOUT");
        // setLevel()以下の内容が、出力されればOK.
        expected =
            "00 000 test2 INFO     : INFO string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test2 WARN     : WARN string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test2 ERROR    : ERROR string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test2 FATAL    : FATAL string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test2 SILENT   : SILENT string to STDOUT" + System.getProperty("line.separator");
        assertEquals(expected, handler.getStr());

        handler.clear();
        rtcout.setLevel(Logbuf.WARN);
        rtcout.println(Logbuf.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(Logbuf.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(Logbuf.TRACE, "TRACE string to STDOUT");
        rtcout.println(Logbuf.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(Logbuf.INFO, "INFO string to STDOUT");
        rtcout.println(Logbuf.WARN, "WARN string to STDOUT");
        rtcout.println(Logbuf.ERROR, "ERROR string to STDOUT");
        rtcout.println(Logbuf.FATAL, "FATAL string to STDOUT");
        rtcout.println(Logbuf.SILENT, "SILENT string to STDOUT");
        // setLevel()以下の内容が、出力されればOK.
        expected =
            "00 000 test2 WARN     : WARN string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test2 ERROR    : ERROR string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test2 FATAL    : FATAL string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test2 SILENT   : SILENT string to STDOUT" + System.getProperty("line.separator");
        assertEquals(expected, handler.getStr());

        handler.clear();
        rtcout.setLevel(Logbuf.ERROR);
        rtcout.println(Logbuf.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(Logbuf.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(Logbuf.TRACE, "TRACE string to STDOUT");
        rtcout.println(Logbuf.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(Logbuf.INFO, "INFO string to STDOUT");
        rtcout.println(Logbuf.WARN, "WARN string to STDOUT");
        rtcout.println(Logbuf.ERROR, "ERROR string to STDOUT");
        rtcout.println(Logbuf.FATAL, "FATAL string to STDOUT");
        rtcout.println(Logbuf.SILENT, "SILENT string to STDOUT");
        // setLevel()以下の内容が、出力されればOK.
        expected =
            "00 000 test2 ERROR    : ERROR string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test2 FATAL    : FATAL string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test2 SILENT   : SILENT string to STDOUT" + System.getProperty("line.separator");
        assertEquals(expected, handler.getStr());

        handler.clear();
        rtcout.setLevel(Logbuf.FATAL);
        rtcout.println(Logbuf.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(Logbuf.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(Logbuf.TRACE, "TRACE string to STDOUT");
        rtcout.println(Logbuf.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(Logbuf.INFO, "INFO string to STDOUT");
        rtcout.println(Logbuf.WARN, "WARN string to STDOUT");
        rtcout.println(Logbuf.ERROR, "ERROR string to STDOUT");
        rtcout.println(Logbuf.FATAL, "FATAL string to STDOUT");
        rtcout.println(Logbuf.SILENT, "SILENT string to STDOUT");
        // setLevel()以下の内容が、出力されればOK.
        expected =
            "00 000 test2 FATAL    : FATAL string to STDOUT" + System.getProperty("line.separator") +
            "00 000 test2 SILENT   : SILENT string to STDOUT" + System.getProperty("line.separator");
        assertEquals(expected, handler.getStr());

        handler.clear();
        rtcout.setLevel(Logbuf.SILENT);
        rtcout.println(Logbuf.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(Logbuf.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(Logbuf.TRACE, "TRACE string to STDOUT");
        rtcout.println(Logbuf.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(Logbuf.INFO, "INFO string to STDOUT");
        rtcout.println(Logbuf.WARN, "WARN string to STDOUT");
        rtcout.println(Logbuf.ERROR, "ERROR string to STDOUT");
        rtcout.println(Logbuf.FATAL, "FATAL string to STDOUT");
        rtcout.println(Logbuf.SILENT, "SILENT string to STDOUT");
        // 何も出力されなければOK.
        expected = "";
        assertEquals(expected, handler.getStr());

        handler.clear();
        rtcout.setLevel(999);
        rtcout.println(Logbuf.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(Logbuf.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(Logbuf.TRACE, "TRACE string to STDOUT");
        rtcout.println(Logbuf.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(Logbuf.INFO, "INFO string to STDOUT");
        rtcout.println(Logbuf.WARN, "WARN string to STDOUT");
        rtcout.println(Logbuf.ERROR, "ERROR string to STDOUT");
        rtcout.println(Logbuf.FATAL, "FATAL string to STDOUT");
        rtcout.println(Logbuf.SILENT, "SILENT string to STDOUT");
        // 何も出力されなければOK.
        expected = "";
        assertEquals(expected, handler.getStr());
        
    }

    /**
    *
    * ファイル出力のテスト
    * setLevel毎に出力内容が制限されているか？
    */
    public void test_fileout_PARANOID() {
        Logbuf rtcout = new Logbuf("TESTFILE1");

        rtcout.setEnabled();
        // FileOut
        // ファイルは /RELENG_1_0_0/jp.go.aist.rtm.RTC/ 配下に作成される
        //-----------------------------------------------------------------

        // PARANOID check
        String logfile = "./rtc01.log";
        try {
            rtcout.addStream(new FileHandler(logfile));
        } catch(IOException ex) {
            System.err.println("Error: cannot open logfile: " + logfile );
        }
        rtcout.setLevel("PARANOID");
        rtcout.println(Logbuf.PARANOID, "PARANOID string to rtc.log");
        rtcout.println(Logbuf.VERBOSE, "VERBOSE string to rtc.log");
        rtcout.println(Logbuf.TRACE, "TRACE string to rtc.log");
        rtcout.println(Logbuf.DEBUG, "DEBUG string to rtc.log");
        rtcout.println(Logbuf.INFO, "INFO string to rtc.log");
        rtcout.println(Logbuf.WARN, "WARN string to rtc.log");
        rtcout.println(Logbuf.ERROR, "ERROR string to rtc.log");
        rtcout.println(Logbuf.FATAL, "FATAL string to rtc.log");
        rtcout.println(Logbuf.SILENT, "SILENT string to rtc.log");

        //ファイル出力の確認
        int cnt = 0;
        String chkString = new String("string to rtc.log");
        try{
            FileReader fr = new FileReader(logfile);
            BufferedReader br = new BufferedReader(fr);

            String str = new String();
            while( (str = br.readLine()) != null ) {
//              System.out.println("str=("+str+")");
                int n = str.indexOf(chkString);
                if(n > 0) {
                    ++cnt;
                }
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            System.err.println("Error: cannot read logfile: " + logfile );
        }
//      System.out.println("PARANOID cnt=" + Integer.toString(cnt) );
        assertEquals(9, cnt);

    }

    /**
    *
    * ファイル出力のテスト
    * setLevel毎に出力内容が制限されているか？
    *
    */
    public void test_fileout_VERBOSE() {
        Logbuf rtcout = new Logbuf("TESTFILE2");

        rtcout.setEnabled();
        // FileOut
        // ファイルは /RELENG_1_0_0/jp.go.aist.rtm.RTC/ 配下に作成される
        //-----------------------------------------------------------------

        // VERBOSE check
        String logfile = "./rtc02.log";
        try {
            rtcout.addStream(new FileHandler(logfile));
        } catch(IOException ex) {
            System.err.println("Error: cannot open logfile: " + logfile );
        }
        rtcout.setLevel("VERBOSE");
        rtcout.println(Logbuf.PARANOID, "PARANOID string to rtc.log");
        rtcout.println(Logbuf.VERBOSE, "VERBOSE string to rtc.log");
        rtcout.println(Logbuf.TRACE, "TRACE string to rtc.log");
        rtcout.println(Logbuf.DEBUG, "DEBUG string to rtc.log");
        rtcout.println(Logbuf.INFO, "INFO string to rtc.log");
        rtcout.println(Logbuf.WARN, "WARN string to rtc.log");
        rtcout.println(Logbuf.ERROR, "ERROR string to rtc.log");
        rtcout.println(Logbuf.FATAL, "FATAL string to rtc.log");
        rtcout.println(Logbuf.SILENT, "SILENT string to rtc.log");

        //ファイル出力の確認
        int cnt = 0;
        String chkString = new String("string to rtc.log");
        try{
            FileReader fr = new FileReader(logfile);
            BufferedReader br = new BufferedReader(fr);

            String str = new String();
            while( (str = br.readLine()) != null ) {
                int n = str.indexOf(chkString);
                if(n > 0) {
                    ++cnt;
                }
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            System.err.println("Error: cannot read logfile: " + logfile );
        }
//      System.out.println("VERBOSE cnt=" + Integer.toString(cnt) );
        assertEquals(8, cnt);
        //-----------------------------------------------------------------

    }

    /**
    *
    * ファイル出力のテスト
    * setLevel毎に出力内容が制限されているか？
    *
    */
    public void test_fileout_TRACE() {
        Logbuf rtcout = new Logbuf("TESTFILE3");

        rtcout.setEnabled();
        // FileOut
        // ファイルは /RELENG_1_0_0/jp.go.aist.rtm.RTC/ 配下に作成される
        //-----------------------------------------------------------------

        // TRACE check
        String logfile = "./rtc03.log";
        try {
            rtcout.addStream(new FileHandler(logfile));
        } catch(IOException ex) {
            System.err.println("Error: cannot open logfile: " + logfile );
        }
        rtcout.setLevel("TRACE");
        rtcout.println(Logbuf.PARANOID, "PARANOID string to rtc.log");
        rtcout.println(Logbuf.VERBOSE, "VERBOSE string to rtc.log");
        rtcout.println(Logbuf.TRACE, "TRACE string to rtc.log");
        rtcout.println(Logbuf.DEBUG, "DEBUG string to rtc.log");
        rtcout.println(Logbuf.INFO, "INFO string to rtc.log");
        rtcout.println(Logbuf.WARN, "WARN string to rtc.log");
        rtcout.println(Logbuf.ERROR, "ERROR string to rtc.log");
        rtcout.println(Logbuf.FATAL, "FATAL string to rtc.log");
        rtcout.println(Logbuf.SILENT, "SILENT string to rtc.log");

        //ファイル出力の確認
        int cnt = 0;
        String chkString = new String("string to rtc.log");
        try{
            FileReader fr = new FileReader(logfile);
            BufferedReader br = new BufferedReader(fr);

            String str = new String();
            while( (str = br.readLine()) != null ) {
                int n = str.indexOf(chkString);
                if(n > 0) {
                    ++cnt;
                }
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            System.err.println("Error: cannot read logfile: " + logfile );
        }
//      System.out.println("TRACE cnt=" + Integer.toString(cnt) );
        assertEquals(7, cnt);

    }

    /**
    *
    * ファイル出力のテスト
    * setLevel毎に出力内容が制限されているか？
    *
    */
    public void test_fileout_DEBUG() {
        Logbuf rtcout = new Logbuf("TESTFILE4");

        rtcout.setEnabled();
        // FileOut
        // ファイルは /RELENG_1_0_0/jp.go.aist.rtm.RTC/ 配下に作成される
        //-----------------------------------------------------------------

        // DEBUG check
        String logfile = "./rtc04.log";
        try {
            rtcout.addStream(new FileHandler(logfile));
        } catch(IOException ex) {
            System.err.println("Error: cannot open logfile: " + logfile );
        }
        rtcout.setLevel("DEBUG");
        rtcout.println(Logbuf.PARANOID, "PARANOID string to rtc.log");
        rtcout.println(Logbuf.VERBOSE, "VERBOSE string to rtc.log");
        rtcout.println(Logbuf.TRACE, "TRACE string to rtc.log");
        rtcout.println(Logbuf.DEBUG, "DEBUG string to rtc.log");
        rtcout.println(Logbuf.INFO, "INFO string to rtc.log");
        rtcout.println(Logbuf.WARN, "WARN string to rtc.log");
        rtcout.println(Logbuf.ERROR, "ERROR string to rtc.log");
        rtcout.println(Logbuf.FATAL, "FATAL string to rtc.log");
        rtcout.println(Logbuf.SILENT, "SILENT string to rtc.log");

        //ファイル出力の確認
        int cnt = 0;
        String chkString = new String("string to rtc.log");
        try{
            FileReader fr = new FileReader(logfile);
            BufferedReader br = new BufferedReader(fr);

            String str = new String();
            while( (str = br.readLine()) != null ) {
                int n = str.indexOf(chkString);
                if(n > 0) {
                    ++cnt;
                }
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            System.err.println("Error: cannot read logfile: " + logfile );
        }
//      System.out.println("DEBUG cnt=" + Integer.toString(cnt) );
        assertEquals(6, cnt);

    }

    /**
    *
    * ファイル出力のテスト
    * setLevel毎に出力内容が制限されているか？
    *
    */
    public void test_fileout_INFO() {
        Logbuf rtcout = new Logbuf("TESTFILE5");

        rtcout.setEnabled();
        // FileOut
        // ファイルは /RELENG_1_0_0/jp.go.aist.rtm.RTC/ 配下に作成される
        //-----------------------------------------------------------------

        // INFO check
        String logfile = "./rtc05.log";
        try {
            rtcout.addStream(new FileHandler(logfile));
        } catch(IOException ex) {
            System.err.println("Error: cannot open logfile: " + logfile );
        }
        rtcout.setLevel("INFO");
        rtcout.println(Logbuf.PARANOID, "PARANOID string to rtc.log");
        rtcout.println(Logbuf.VERBOSE, "VERBOSE string to rtc.log");
        rtcout.println(Logbuf.TRACE, "TRACE string to rtc.log");
        rtcout.println(Logbuf.DEBUG, "DEBUG string to rtc.log");
        rtcout.println(Logbuf.INFO, "INFO string to rtc.log");
        rtcout.println(Logbuf.WARN, "WARN string to rtc.log");
        rtcout.println(Logbuf.ERROR, "ERROR string to rtc.log");
        rtcout.println(Logbuf.FATAL, "FATAL string to rtc.log");
        rtcout.println(Logbuf.SILENT, "SILENT string to rtc.log");

        //ファイル出力の確認
        int cnt = 0;
        String chkString = new String("string to rtc.log");
        try{
            FileReader fr = new FileReader(logfile);
            BufferedReader br = new BufferedReader(fr);

            String str = new String();
            while( (str = br.readLine()) != null ) {
                int n = str.indexOf(chkString);
                if(n > 0) {
                    ++cnt;
                }
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            System.err.println("Error: cannot read logfile: " + logfile );
        }
//      System.out.println("INFO cnt=" + Integer.toString(cnt) );
        assertEquals(5, cnt);

    }

    /**
    *
    * ファイル出力のテスト
    * setLevel毎に出力内容が制限されているか？
    *
    */
    public void test_fileout_WARN() {
        Logbuf rtcout = new Logbuf("TESTFILE6");

        rtcout.setEnabled();
        // FileOut
        // ファイルは /RELENG_1_0_0/jp.go.aist.rtm.RTC/ 配下に作成される
        //-----------------------------------------------------------------

        // WARN check
        String logfile = "./rtc06.log";
        try {
            rtcout.addStream(new FileHandler(logfile));
        } catch(IOException ex) {
            System.err.println("Error: cannot open logfile: " + logfile );
        }
        rtcout.setLevel("WARN");
        rtcout.println(Logbuf.PARANOID, "PARANOID string to rtc.log");
        rtcout.println(Logbuf.VERBOSE, "VERBOSE string to rtc.log");
        rtcout.println(Logbuf.TRACE, "TRACE string to rtc.log");
        rtcout.println(Logbuf.DEBUG, "DEBUG string to rtc.log");
        rtcout.println(Logbuf.INFO, "INFO string to rtc.log");
        rtcout.println(Logbuf.WARN, "WARN string to rtc.log");
        rtcout.println(Logbuf.ERROR, "ERROR string to rtc.log");
        rtcout.println(Logbuf.FATAL, "FATAL string to rtc.log");
        rtcout.println(Logbuf.SILENT, "SILENT string to rtc.log");

        //ファイル出力の確認
        int cnt = 0;
        String chkString = new String("string to rtc.log");
        try{
            FileReader fr = new FileReader(logfile);
            BufferedReader br = new BufferedReader(fr);

            String str = new String();
            while( (str = br.readLine()) != null ) {
                int n = str.indexOf(chkString);
                if(n > 0) {
                    ++cnt;
                }
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            System.err.println("Error: cannot read logfile: " + logfile );
        }
//      System.out.println("WARN cnt=" + Integer.toString(cnt) );
        assertEquals(4, cnt);

    }

    /**
    *
    * ファイル出力のテスト
    * setLevel毎に出力内容が制限されているか？
    *
    */
    public void test_fileout_ERROR() {
        Logbuf rtcout = new Logbuf("TESTFILE7");

        rtcout.setEnabled();
        // FileOut
        // ファイルは /RELENG_1_0_0/jp.go.aist.rtm.RTC/ 配下に作成される
        //-----------------------------------------------------------------

        // ERROR check
        String logfile = "./rtc07.log";
        try {
            rtcout.addStream(new FileHandler(logfile));
        } catch(IOException ex) {
            System.err.println("Error: cannot open logfile: " + logfile );
        }
        rtcout.setLevel("ERROR");
        rtcout.println(Logbuf.PARANOID, "PARANOID string to rtc.log");
        rtcout.println(Logbuf.VERBOSE, "VERBOSE string to rtc.log");
        rtcout.println(Logbuf.TRACE, "TRACE string to rtc.log");
        rtcout.println(Logbuf.DEBUG, "DEBUG string to rtc.log");
        rtcout.println(Logbuf.INFO, "INFO string to rtc.log");
        rtcout.println(Logbuf.WARN, "WARN string to rtc.log");
        rtcout.println(Logbuf.ERROR, "ERROR string to rtc.log");
        rtcout.println(Logbuf.FATAL, "FATAL string to rtc.log");
        rtcout.println(Logbuf.SILENT, "SILENT string to rtc.log");

        //ファイル出力の確認
        int cnt = 0;
        String chkString = new String("string to rtc.log");
        try{
            FileReader fr = new FileReader(logfile);
            BufferedReader br = new BufferedReader(fr);

            String str = new String();
            while( (str = br.readLine()) != null ) {
                int n = str.indexOf(chkString);
                if(n > 0) {
                    ++cnt;
                }
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            System.err.println("Error: cannot read logfile: " + logfile );
        }
//      System.out.println("ERROR cnt=" + Integer.toString(cnt) );
        assertEquals(3, cnt);

    }

    /**
    *
    * ファイル出力のテスト
    * setLevel毎に出力内容が制限されているか？
    *
    */
    public void test_fileout_FATAL() {
        Logbuf rtcout = new Logbuf("TESTFILE8");

        rtcout.setEnabled();
        // FileOut
        // ファイルは /RELENG_1_0_0/jp.go.aist.rtm.RTC/ 配下に作成される
        //-----------------------------------------------------------------

        // FATAL check
        String logfile = "./rtc08.log";
        try {
            rtcout.addStream(new FileHandler(logfile));
        } catch(IOException ex) {
            System.err.println("Error: cannot open logfile: " + logfile );
        }
        rtcout.setLevel("FATAL");
        rtcout.println(Logbuf.PARANOID, "PARANOID string to rtc.log");
        rtcout.println(Logbuf.VERBOSE, "VERBOSE string to rtc.log");
        rtcout.println(Logbuf.TRACE, "TRACE string to rtc.log");
        rtcout.println(Logbuf.DEBUG, "DEBUG string to rtc.log");
        rtcout.println(Logbuf.INFO, "INFO string to rtc.log");
        rtcout.println(Logbuf.WARN, "WARN string to rtc.log");
        rtcout.println(Logbuf.ERROR, "ERROR string to rtc.log");
        rtcout.println(Logbuf.FATAL, "FATAL string to rtc.log");
        rtcout.println(Logbuf.SILENT, "SILENT string to rtc.log");

        //ファイル出力の確認
        int cnt = 0;
        String chkString = new String("string to rtc.log");
        try{
            FileReader fr = new FileReader(logfile);
            BufferedReader br = new BufferedReader(fr);

            String str = new String();
            while( (str = br.readLine()) != null ) {
                int n = str.indexOf(chkString);
                if(n > 0) {
                    ++cnt;
                }
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            System.err.println("Error: cannot read logfile: " + logfile );
        }
//      System.out.println("FATAL cnt=" + Integer.toString(cnt) );
        assertEquals(2, cnt);

    }

    /**
    *
    * ファイル出力のテスト
    * setLevel毎に出力内容が制限されているか？
    *
    */
    public void test_fileout_SILENT() {
        Logbuf rtcout = new Logbuf("TESTFILE9");

        rtcout.setEnabled();
        // FileOut
        // ファイルは /RELENG_1_0_0/jp.go.aist.rtm.RTC/ 配下に作成される
        //-----------------------------------------------------------------

        // SILENT check
        String logfile = "./rtc09.log";
        try {
            rtcout.addStream(new FileHandler(logfile));
        } catch(IOException ex) {
            System.err.println("Error: cannot open logfile: " + logfile );
        }
        rtcout.setLevel("SILENT");
        rtcout.println(Logbuf.PARANOID, "PARANOID string to rtc.log");
        rtcout.println(Logbuf.VERBOSE, "VERBOSE string to rtc.log");
        rtcout.println(Logbuf.TRACE, "TRACE string to rtc.log");
        rtcout.println(Logbuf.DEBUG, "DEBUG string to rtc.log");
        rtcout.println(Logbuf.INFO, "INFO string to rtc.log");
        rtcout.println(Logbuf.WARN, "WARN string to rtc.log");
        rtcout.println(Logbuf.ERROR, "ERROR string to rtc.log");
        rtcout.println(Logbuf.FATAL, "FATAL string to rtc.log");
        rtcout.println(Logbuf.SILENT, "SILENT string to rtc.log");

        //ファイル出力の確認
        int cnt = 0;
        String chkString = new String("string to rtc.log");
        try{
            FileReader fr = new FileReader(logfile);
            BufferedReader br = new BufferedReader(fr);

            String str = new String();
            while( (str = br.readLine()) != null ) {
                int n = str.indexOf(chkString);
                if(n > 0) {
                    ++cnt;
                }
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            System.err.println("Error: cannot read logfile: " + logfile );
        }
//      System.out.println("SILENT cnt=" + Integer.toString(cnt) );
        assertEquals(0, cnt);


    }

    /**
    *
    * strToLogLevel()テスト
    * ログレベルを表す文字列コード変換が正しく行えるか？
    */
    public void test_strToLogLevel() {
        System.err.println("\n\n--- test_strToLogLevel() check ---");

        Logbuf rtcout = new Logbuf("test_strToLogLevel");
        rtcout.addStream(new ConsoleHandler());
        rtcout.setLevel("PARANOID");

        int lv = 0;
        lv = Logbuf.strToLogLevel("SILENT");
        assertEquals(Logbuf.SILENT, lv);

        lv = Logbuf.strToLogLevel("FATAL");
        assertEquals(Logbuf.FATAL, lv);

        lv = Logbuf.strToLogLevel("ERROR");
        assertEquals(Logbuf.ERROR, lv);

        lv = Logbuf.strToLogLevel("WARN");
        assertEquals(Logbuf.WARN, lv);

        lv = Logbuf.strToLogLevel("INFO");
        assertEquals(Logbuf.INFO, lv);

        lv = Logbuf.strToLogLevel("DEBUG");
        assertEquals(Logbuf.DEBUG, lv);

        lv = Logbuf.strToLogLevel("TRACE");
        assertEquals(Logbuf.TRACE, lv);

        lv = Logbuf.strToLogLevel("VERBOSE");
        assertEquals(Logbuf.VERBOSE, lv);

        lv = Logbuf.strToLogLevel("PARANOID");
        assertEquals(Logbuf.PARANOID, lv);

        lv = Logbuf.strToLogLevel("other");
        assertEquals(Logbuf.SILENT, lv);

        System.err.println("--- test_strToLogLevel() is OK ---");
    }

    /**
    *
    * setDateFormat()テスト
    * 日付形式の書式設定が正しく行えるか？
    */
    public void test_setDateFormat() {
        System.err.println("\n\n--- test_setDateFormat() check ---");

        Logbuf rtcout = new Logbuf("test_setDateFormat");
        rtcout.addStream(new ConsoleHandler());
        rtcout.setLevel("PARANOID");
        String format = new String();

        // default
        // "logger.date_format",     "%b %d %H:%M:%S",
        format = "%b %d %H:%M:%S";
        rtcout.setDateFormat(format);
        rtcout.println(Logbuf.TRACE, "DateFormat Month day HH:MI:SS");
        // Junit出力ファイル*.xmlの <system-err>の欄に出力される
        // 日付と時刻のパターンが、Month day hh:mi:ss であれがOK
        //Sep 29 16:36:53 TEST1 TRACE    : TRACE string to rtc.log

        format = "%Y/%m/%d %p %I:%M:%S";
        rtcout.setDateFormat(format);
        rtcout.println(Logbuf.TRACE, "DateFormat YYYY/MM/DD pp II:MI:SS");
        // Junit出力ファイル*.xmlの <system-err>の欄に出力される
        // 日付と時刻のパターンが、YYYY/MM/DD pp II:MI:SS であれがOK

        System.err.println("--- test_setDateFormat() is OK ---");
    }

    /*** （参考）
        // DefaultConfiguration.java の定義
        "logger.enable",          "YES",
        "logger.file_name",       "./rtc%p.log",
        "logger.date_format",     "%b %d %H:%M:%S",
        "logger.log_level",       "INFO",
        "logger.stream_lock",     "NO",
        "logger.master_logger",   "",
    ***/

    /**
    *
    * 親子ノードのテスト
    * 親ノードで作成したログに、子ノードで作成したログが正しく出力されるか？
    */
    public void test_manager_logbuf() {
        System.err.println("\n\n--- test_manager_logbuf() start ---");

        int cnt = 0;
        int cnt2 = 0;
        Logbuf rtcout = new Logbuf("Manager");
        rtcout.setEnabled();
        cnt = rtcout.getStreamCount();
//        assertEquals(0, cnt);

        ConsoleHandler stdout = new ConsoleHandler();
        rtcout.addStream(stdout);

        String logfile = "./rtc10.log";
        try {
            rtcout.addStream(new FileHandler(logfile));
        } catch(IOException ex) {
            System.err.println("Error: cannot open logfile: " + logfile );
        }
        cnt2 = rtcout.getStreamCount();
        assertEquals(cnt+2, cnt2);

        rtcout.setLevel("INFO");
        System.err.println("--- Logbuf(Manager) set ---");
        rtcout.println(Logbuf.INFO, "--- setLevel(INFO), addStream(STDOUT) set ---");
        rtcout.println(Logbuf.PARANOID, "PARANOID string 1");
        rtcout.println(Logbuf.VERBOSE, "VERBOSE string 1");
        rtcout.println(Logbuf.TRACE, "TRACE string 1");
        rtcout.println(Logbuf.DEBUG, "DEBUG string 1");
        rtcout.println(Logbuf.INFO, "INFO string 1");
        rtcout.println(Logbuf.WARN, "WARN string 1");
        rtcout.println(Logbuf.ERROR, "ERROR string 1");
        rtcout.println(Logbuf.FATAL, "FATAL string 1");
        rtcout.println(Logbuf.SILENT, "SILENT string 1");
        // setLevel(INFO)以下の内容が、出力されればOK.


        Logbuf rtcout2 = new Logbuf("Manager.hoge1");
        rtcout2.setEnabled();
        System.err.println("--- Logbuf(Manager.hoge1) set ---");
        rtcout2.println(Logbuf.INFO, "--- setLevel(INFO) Not set ---");
        rtcout2.println(Logbuf.PARANOID, "PARANOID string 2");
        rtcout2.println(Logbuf.VERBOSE, "VERBOSE string 2");
        rtcout2.println(Logbuf.TRACE, "TRACE string 2");
        rtcout2.println(Logbuf.DEBUG, "DEBUG string 2");
        rtcout2.println(Logbuf.INFO, "INFO string 2");
        rtcout2.println(Logbuf.WARN, "WARN string 2");
        rtcout2.println(Logbuf.ERROR, "ERROR string 2");
        rtcout2.println(Logbuf.FATAL, "FATAL string 2");
        rtcout2.println(Logbuf.SILENT, "SILENT string 2");
        // setLevel(INFO)以下の内容が、出力されればOK.

        rtcout2.setLevel("PARANOID");
        System.err.println("--- Logbuf(Manager.hoge1) set ---");
        rtcout2.println(Logbuf.INFO, "--- setLevel(PARANOID) set ---");
        rtcout2.println(Logbuf.PARANOID, "PARANOID string 3");
        rtcout2.println(Logbuf.VERBOSE, "VERBOSE string 3");
        rtcout2.println(Logbuf.TRACE, "TRACE string 3");
        rtcout2.println(Logbuf.DEBUG, "DEBUG string 3");
        rtcout2.println(Logbuf.INFO, "INFO string 3");
        rtcout2.println(Logbuf.WARN, "WARN string 3");
        rtcout2.println(Logbuf.ERROR, "ERROR string 3");
        rtcout2.println(Logbuf.FATAL, "FATAL string 3");
        rtcout2.println(Logbuf.SILENT, "SILENT string 3");
        // setLevel(PARANOID)以下の内容が、出力されればOK.

        Logbuf rtcout3 = new Logbuf("hoge2");
        rtcout3.setEnabled();
        System.err.println("--- Logbuf(hoge2) set ---");
        rtcout3.println(Logbuf.INFO, "--- setLevel(INFO) Not set ---");
        rtcout3.println(Logbuf.PARANOID, "PARANOID string 4");
        rtcout3.println(Logbuf.VERBOSE, "VERBOSE string 4");
        rtcout3.println(Logbuf.TRACE, "TRACE string 4");
        rtcout3.println(Logbuf.DEBUG, "DEBUG string 4");
        rtcout3.println(Logbuf.INFO, "INFO string 4");
        rtcout3.println(Logbuf.WARN, "WARN string 4");
        rtcout3.println(Logbuf.ERROR, "ERROR string 4");
        rtcout3.println(Logbuf.FATAL, "FATAL string 4");
        rtcout3.println(Logbuf.SILENT, "SILENT string 4");
        // setLevel(INFO)以下の内容が、出力されればOK.

        rtcout3.setLevel("PARANOID");
        System.err.println("--- Logbuf(hoge2) set ---");
        rtcout3.println(Logbuf.INFO, "--- setLevel(PARANOID) set ---");
        rtcout3.println(Logbuf.PARANOID, "PARANOID string 5");
        rtcout3.println(Logbuf.VERBOSE, "VERBOSE string 5");
        rtcout3.println(Logbuf.TRACE, "TRACE string 5");
        rtcout3.println(Logbuf.DEBUG, "DEBUG string 5");
        rtcout3.println(Logbuf.INFO, "INFO string 5");
        rtcout3.println(Logbuf.WARN, "WARN string 5");
        rtcout3.println(Logbuf.ERROR, "ERROR string 5");
        rtcout3.println(Logbuf.FATAL, "FATAL string 5");
        rtcout3.println(Logbuf.SILENT, "SILENT string 5");
        // setLevel(PARANOID)以下の内容が、出力されればOK.


        // 引数２個のコンストラクタ
        Logbuf rtcout4 = new Logbuf("hoge3", "Manager");
        rtcout4.setEnabled();
        System.err.println("--- Logbuf(Manager.hoge3) set ---");
        rtcout4.println(Logbuf.INFO, "--- setLevel(INFO) Not set ---");
        rtcout4.println(Logbuf.PARANOID, "PARANOID string 6");
        rtcout4.println(Logbuf.VERBOSE, "VERBOSE string 6");
        rtcout4.println(Logbuf.TRACE, "TRACE string 6");
        rtcout4.println(Logbuf.DEBUG, "DEBUG string 6");
        rtcout4.println(Logbuf.INFO, "INFO string 6");
        rtcout4.println(Logbuf.WARN, "WARN string 6");
        rtcout4.println(Logbuf.ERROR, "ERROR string 6");
        rtcout4.println(Logbuf.FATAL, "FATAL string 6");
        rtcout4.println(Logbuf.SILENT, "SILENT string 6");
        // setLevel(INFO)以下の内容が、出力されればOK.

        rtcout4.setLevel("PARANOID");
        System.err.println("--- Logbuf(Manager.hoge3) set ---");
        rtcout4.println(Logbuf.INFO, "--- setLevel(PARANOID) set ---");
        rtcout4.println(Logbuf.PARANOID, "PARANOID string 7");
        rtcout4.println(Logbuf.VERBOSE, "VERBOSE string 7");
        rtcout4.println(Logbuf.TRACE, "TRACE string 7");
        rtcout4.println(Logbuf.DEBUG, "DEBUG string 7");
        rtcout4.println(Logbuf.INFO, "INFO string 7");
        rtcout4.println(Logbuf.WARN, "WARN string 7");
        rtcout4.println(Logbuf.ERROR, "ERROR string 7");
        rtcout4.println(Logbuf.FATAL, "FATAL string 7");
        rtcout4.println(Logbuf.SILENT, "SILENT string 7");
        // setLevel(PARANOID)以下の内容が、出力されればOK.

        rtcout.removeStream(stdout);
        cnt2 = rtcout.getStreamCount();
        assertEquals(cnt+1, cnt2);
        System.err.println("--- test_manager_logbuf() end ---");

    }

    /**
    *
    * Managerのインスタンステスト
    * Managerが管理しているLogbufを取得しログ出力が行えるか？
    */
    public void test_manager_logbuf2() {
        System.err.println("\n\n--- test_manager_logbuf2() start ---");
        // Managerのインスタンスでロガー生成
        Manager manager = Manager.instance();
        manager.activateManager();

        // ログファイル rtc*.log が
        // /RELENG_1_0_0/jp.go.aist.rtm.RTC/ 配下に作成されて
        // 下記内容が記録されていればOK
        // "Manager INFO     : OpenRTM-aist-1.0.0"
        // "Manager INFO     : Copyright (C) 2003-2008"
        // "Manager INFO     :   Noriaki Ando"
        // "Manager INFO     :   Task-intelligence Research Group,"
        // "Manager INFO     :   Intelligent Systems Research Institute, AIST"
        // "Manager INFO     : Manager starting."
        // "Manager INFO     : Starting local logging."

        Logbuf rtcout = new Logbuf("FileOuttest");
        System.err.println("--- Logbuf(FileOuttest) set ---");
        rtcout.println(Logbuf.INFO, "--- setLevel(INFO) Not set ---");
        rtcout.println(Logbuf.PARANOID, "PARANOID string 10");
        rtcout.println(Logbuf.VERBOSE, "VERBOSE string 10");
        rtcout.println(Logbuf.TRACE, "TRACE string 10");
        rtcout.println(Logbuf.DEBUG, "DEBUG string 10");
        rtcout.println(Logbuf.INFO, "INFO string 10");
        rtcout.println(Logbuf.WARN, "WARN string 10");
        rtcout.println(Logbuf.ERROR, "ERROR string 10");
        rtcout.println(Logbuf.FATAL, "FATAL string 10");
        rtcout.println(Logbuf.SILENT, "SILENT string 10");
        // setLevel(INFO)以下の内容が、出力されればOK.

        rtcout.setLevel("PARANOID");
        System.err.println("--- Logbuf(Manager.FileOuttest) set ---");
        rtcout.println(Logbuf.INFO, "--- setLevel(PARANOID) set ---");
        rtcout.println(Logbuf.PARANOID, "PARANOID string 11");
        rtcout.println(Logbuf.VERBOSE, "VERBOSE string 11");
        rtcout.println(Logbuf.TRACE, "TRACE string 11");
        rtcout.println(Logbuf.DEBUG, "DEBUG string 11");
        rtcout.println(Logbuf.INFO, "INFO string 11");
        rtcout.println(Logbuf.WARN, "WARN string 11");
        rtcout.println(Logbuf.ERROR, "ERROR string 11");
        rtcout.println(Logbuf.FATAL, "FATAL string 11");
        rtcout.println(Logbuf.SILENT, "SILENT string 11");
        // setLevel()以下の内容が、出力されればOK.


        // 引数２個のコンストラクタ
        Logbuf rtcout2 = new Logbuf("PortBase", "");
        int cnt = rtcout2.getStreamCount();
        assertEquals(0, cnt);

        ConsoleHandler stdout = new ConsoleHandler();
        rtcout2.addStream(stdout);

        String logfile = "./rtc11.log";
        try {
            rtcout2.addStream(new FileHandler(logfile));
        } catch(IOException ex) {
            System.err.println("Error: cannot open logfile: " + logfile );
        }
        cnt = rtcout2.getStreamCount();
//        assertEquals(1, cnt);

        rtcout2.setLevel("INFO");
        System.err.println("--- Logbuf(PortBase) set ---");
        rtcout2.println(Logbuf.INFO, "--- setLevel(INFO) Not set ---");
        rtcout2.println(Logbuf.PARANOID, "PARANOID string 20");
        rtcout2.println(Logbuf.VERBOSE, "VERBOSE string 20");
        rtcout2.println(Logbuf.TRACE, "TRACE string 20");
        rtcout2.println(Logbuf.DEBUG, "DEBUG string 20");
        rtcout2.println(Logbuf.INFO, "INFO string 20");
        rtcout2.println(Logbuf.WARN, "WARN string 20");
        rtcout2.println(Logbuf.ERROR, "ERROR string 20");
        rtcout2.println(Logbuf.FATAL, "FATAL string 20");
        rtcout2.println(Logbuf.SILENT, "SILENT string 20");
        // setLevel()以下の内容が、出力されればOK.

        rtcout2.setLevel("PARANOID");
        System.err.println("--- Logbuf(PortBase) set ---");
        rtcout2.println(Logbuf.INFO, "--- setLevel(PARANOID) set ---");
        rtcout2.println(Logbuf.PARANOID, "PARANOID string 21");
        rtcout2.println(Logbuf.VERBOSE, "VERBOSE string 21");
        rtcout2.println(Logbuf.TRACE, "TRACE string 21");
        rtcout2.println(Logbuf.DEBUG, "DEBUG string 21");
        rtcout2.println(Logbuf.INFO, "INFO string 21");
        rtcout2.println(Logbuf.WARN, "WARN string 21");
        rtcout2.println(Logbuf.ERROR, "ERROR string 21");
        rtcout2.println(Logbuf.FATAL, "FATAL string 21");
        rtcout2.println(Logbuf.SILENT, "SILENT string 21");
        // setLevel()以下の内容が、出力されればOK.

        Logbuf rtcout3 = new Logbuf("PublisherFlush", "PortBase");
        System.err.println("--- Logbuf(PortBase.PublisherFlush) set ---");
        rtcout3.println(Logbuf.PARANOID, "PARANOID string 22");
        rtcout3.println(Logbuf.VERBOSE, "VERBOSE string 22");
        rtcout3.println(Logbuf.TRACE, "TRACE string 22");
        rtcout3.println(Logbuf.DEBUG, "DEBUG string 22");
        rtcout3.println(Logbuf.INFO, "INFO string 22");
        rtcout3.println(Logbuf.WARN, "WARN string 22");
        rtcout3.println(Logbuf.ERROR, "ERROR string 22");
        rtcout3.println(Logbuf.FATAL, "FATAL string 22");
        rtcout3.println(Logbuf.SILENT, "SILENT string 22");
        // setLevel()以下の内容が、出力されればOK.

        rtcout3.setLevel("PARANOID");
        System.err.println("--- Logbuf(PortBase.PublisherFlush) set ---");
        rtcout3.println(Logbuf.INFO, "--- setLevel(PARANOID) set ---");
        rtcout3.println(Logbuf.PARANOID, "PARANOID string 23");
        rtcout3.println(Logbuf.VERBOSE, "VERBOSE string 23");
        rtcout3.println(Logbuf.TRACE, "TRACE string 23");
        rtcout3.println(Logbuf.DEBUG, "DEBUG string 23");
        rtcout3.println(Logbuf.INFO, "INFO string 23");
        rtcout3.println(Logbuf.WARN, "WARN string 23");
        rtcout3.println(Logbuf.ERROR, "ERROR string 23");
        rtcout3.println(Logbuf.FATAL, "FATAL string 23");
        rtcout3.println(Logbuf.SILENT, "SILENT string 23");
        // setLevel()以下の内容が、出力されればOK.

        System.err.println("--- test_manager_logbuf2() end ---");

    }

    private class LocalHandler extends Handler {
        StringBuilder builder = new StringBuilder();

        @Override
        public void close() throws SecurityException {
        }

        @Override
        public void flush() {
        }

        @Override
        public void publish(LogRecord record) {
            builder.append(record.getMessage()).append(System.getProperty("line.separator"));
        }
        
        public String getStr() {
            return builder.toString();
        }
        
        public void clear() {
            builder = new StringBuilder();
        }
    }
}
