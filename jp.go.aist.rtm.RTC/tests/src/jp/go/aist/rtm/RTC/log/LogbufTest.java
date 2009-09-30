package jp.go.aist.rtm.RTC.log;

import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.ConsoleHandler;

import jp.go.aist.rtm.RTC.log.Logbuf;
import jp.go.aist.rtm.RTC.Manager;

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

        // STDOUT
        // Junit出力ファイル*.xmlの <system-err>の欄に出力される
        System.err.println("\n--- test_case0() setLevel() Not Set ---");
        rtcout.println(rtcout.PARANOID, "PARANOID string to STDOUT 0");
        rtcout.println(rtcout.VERBOSE, "VERBOSE string to STDOUT 0");
        rtcout.println(rtcout.TRACE, "TRACE string to STDOUT 0");
        rtcout.println(rtcout.DEBUG, "DEBUG string to STDOUT 0");
        rtcout.println(rtcout.INFO, "INFO string to STDOUT 0");
        rtcout.println(rtcout.WARN, "WARN string to STDOUT 0");
        rtcout.println(rtcout.ERROR, "ERROR string to STDOUT 0");
        rtcout.println(rtcout.FATAL, "FATAL string to STDOUT 0");
        rtcout.println(rtcout.SILENT, "SILENT string to STDOUT 0");
        // rtcout.println()の内容が、defaultのINFO以下が出力されればOK.

        rtcout.setLevel("PARANOID");    //全て
        System.err.println("--- test_case0() setLevel(PARANOID), addStream(STDOUT) Not set ---");
        rtcout.println(rtcout.PARANOID, "PARANOID string to STDOUT 1");
        rtcout.println(rtcout.VERBOSE, "VERBOSE string to STDOUT 1");
        rtcout.println(rtcout.TRACE, "TRACE string to STDOUT 1");
        rtcout.println(rtcout.DEBUG, "DEBUG string to STDOUT 1");
        rtcout.println(rtcout.INFO, "INFO string to STDOUT 1");
        rtcout.println(rtcout.WARN, "WARN string to STDOUT 1");
        rtcout.println(rtcout.ERROR, "ERROR string to STDOUT 1");
        rtcout.println(rtcout.FATAL, "FATAL string to STDOUT 1");
        rtcout.println(rtcout.SILENT, "SILENT string to STDOUT 1");
        // setLevel()以下の内容が、出力されればOK.

        rtcout.addStream(new ConsoleHandler());
        System.err.println("--- test_case0() addStream(STDOUT) set ---");
        rtcout.println(rtcout.PARANOID, "PARANOID string to STDOUT 2");
        rtcout.println(rtcout.VERBOSE, "VERBOSE string to STDOUT 2");
        rtcout.println(rtcout.TRACE, "TRACE string to STDOUT 2");
        rtcout.println(rtcout.DEBUG, "DEBUG string to STDOUT 2");
        rtcout.println(rtcout.INFO, "INFO string to STDOUT 2");
        rtcout.println(rtcout.WARN, "WARN string to STDOUT 2");
        rtcout.println(rtcout.ERROR, "ERROR string to STDOUT 2");
        rtcout.println(rtcout.FATAL, "FATAL string to STDOUT 2");
        rtcout.println(rtcout.SILENT, "SILENT string to STDOUT 2");
        // setLevel()以下の内容が、出力されればOK.

    }

    /**
    *
    * STDOUT出力のテスト
    * setLevel(String)毎に出力内容が制限されているか？
    */
    public void test_stdout1() {
        Logbuf rtcout = new Logbuf("test1");

        // STDOUT
        // Junit出力ファイル*.xmlの <system-err>の欄に出力される
        rtcout.addStream(new ConsoleHandler());

        rtcout.setLevel("PARANOID");    //全て出力する
        System.err.println("--- setLevel:PARANOID check ---");
        rtcout.println(rtcout.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(rtcout.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(rtcout.TRACE, "TRACE string to STDOUT");
        rtcout.println(rtcout.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(rtcout.INFO, "INFO string to STDOUT");
        rtcout.println(rtcout.WARN, "WARN string to STDOUT");
        rtcout.println(rtcout.ERROR, "ERROR string to STDOUT");
        rtcout.println(rtcout.FATAL, "FATAL string to STDOUT");
        rtcout.println(rtcout.SILENT, "SILENT string to STDOUT");

        rtcout.setLevel("VERBOSE");     //これ以下が出力される
        System.err.println("--- setLevel:VERBOSE check ---");
        rtcout.println(rtcout.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(rtcout.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(rtcout.TRACE, "TRACE string to STDOUT");
        rtcout.println(rtcout.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(rtcout.INFO, "INFO string to STDOUT");
        rtcout.println(rtcout.WARN, "WARN string to STDOUT");
        rtcout.println(rtcout.ERROR, "ERROR string to STDOUT");
        rtcout.println(rtcout.FATAL, "FATAL string to STDOUT");
        rtcout.println(rtcout.SILENT, "SILENT string to STDOUT");

        rtcout.setLevel("TRACE");       //これ以下が出力される
        System.err.println("--- setLevel:TRACE check ---");
        rtcout.println(rtcout.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(rtcout.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(rtcout.TRACE, "TRACE string to STDOUT");
        rtcout.println(rtcout.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(rtcout.INFO, "INFO string to STDOUT");
        rtcout.println(rtcout.WARN, "WARN string to STDOUT");
        rtcout.println(rtcout.ERROR, "ERROR string to STDOUT");
        rtcout.println(rtcout.FATAL, "FATAL string to STDOUT");
        rtcout.println(rtcout.SILENT, "SILENT string to STDOUT");

        rtcout.setLevel("DEBUG");       //これ以下が出力される
        System.err.println("--- setLevel:DEBUG check ---");
        rtcout.println(rtcout.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(rtcout.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(rtcout.TRACE, "TRACE string to STDOUT");
        rtcout.println(rtcout.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(rtcout.INFO, "INFO string to STDOUT");
        rtcout.println(rtcout.WARN, "WARN string to STDOUT");
        rtcout.println(rtcout.ERROR, "ERROR string to STDOUT");
        rtcout.println(rtcout.FATAL, "FATAL string to STDOUT");
        rtcout.println(rtcout.SILENT, "SILENT string to STDOUT");

        rtcout.setLevel("INFO");        //これ以下が出力される
        System.err.println("--- setLevel:INFO check ---");
        rtcout.println(rtcout.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(rtcout.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(rtcout.TRACE, "TRACE string to STDOUT");
        rtcout.println(rtcout.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(rtcout.INFO, "INFO string to STDOUT");
        rtcout.println(rtcout.WARN, "WARN string to STDOUT");
        rtcout.println(rtcout.ERROR, "ERROR string to STDOUT");
        rtcout.println(rtcout.FATAL, "FATAL string to STDOUT");
        rtcout.println(rtcout.SILENT, "SILENT string to STDOUT");

        rtcout.setLevel("WARN");        //これ以下が出力される
        System.err.println("--- setLevel:WARN check ---");
        rtcout.println(rtcout.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(rtcout.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(rtcout.TRACE, "TRACE string to STDOUT");
        rtcout.println(rtcout.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(rtcout.INFO, "INFO string to STDOUT");
        rtcout.println(rtcout.WARN, "WARN string to STDOUT");
        rtcout.println(rtcout.ERROR, "ERROR string to STDOUT");
        rtcout.println(rtcout.FATAL, "FATAL string to STDOUT");
        rtcout.println(rtcout.SILENT, "SILENT string to STDOUT");

        rtcout.setLevel("ERROR");       //これ以下が出力される
        System.err.println("--- setLevel:ERROR check ---");
        rtcout.println(rtcout.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(rtcout.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(rtcout.TRACE, "TRACE string to STDOUT");
        rtcout.println(rtcout.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(rtcout.INFO, "INFO string to STDOUT");
        rtcout.println(rtcout.WARN, "WARN string to STDOUT");
        rtcout.println(rtcout.ERROR, "ERROR string to STDOUT");
        rtcout.println(rtcout.FATAL, "FATAL string to STDOUT");
        rtcout.println(rtcout.SILENT, "SILENT string to STDOUT");

        rtcout.setLevel("FATAL");       //これ以下が出力される
        System.err.println("--- setLevel:FATAL check ---");
        rtcout.println(rtcout.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(rtcout.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(rtcout.TRACE, "TRACE string to STDOUT");
        rtcout.println(rtcout.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(rtcout.INFO, "INFO string to STDOUT");
        rtcout.println(rtcout.WARN, "WARN string to STDOUT");
        rtcout.println(rtcout.ERROR, "ERROR string to STDOUT");
        rtcout.println(rtcout.FATAL, "FATAL string to STDOUT");
        rtcout.println(rtcout.SILENT, "SILENT string to STDOUT");

        rtcout.setLevel("SILENT");      //全て出力しない
        System.err.println("--- setLevel:SILENT check ---");
        rtcout.println(rtcout.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(rtcout.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(rtcout.TRACE, "TRACE string to STDOUT");
        rtcout.println(rtcout.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(rtcout.INFO, "INFO string to STDOUT");
        rtcout.println(rtcout.WARN, "WARN string to STDOUT");
        rtcout.println(rtcout.ERROR, "ERROR string to STDOUT");
        rtcout.println(rtcout.FATAL, "FATAL string to STDOUT");
        rtcout.println(rtcout.SILENT, "SILENT string to STDOUT");

        rtcout.setLevel("other");      //全て出力しない
        System.err.println("--- setLevel:other check ---");
        rtcout.println(rtcout.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(rtcout.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(rtcout.TRACE, "TRACE string to STDOUT");
        rtcout.println(rtcout.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(rtcout.INFO, "INFO string to STDOUT");
        rtcout.println(rtcout.WARN, "WARN string to STDOUT");
        rtcout.println(rtcout.ERROR, "ERROR string to STDOUT");
        rtcout.println(rtcout.FATAL, "FATAL string to STDOUT");
        rtcout.println(rtcout.SILENT, "SILENT string to STDOUT");

    }

    /**
    *
    * STDOUT出力のテスト
    * setLevel(int)毎に出力内容が制限されているか？
    */
    public void test_stdout2() {
        Logbuf rtcout = new Logbuf("test2");

        // STDOUT
        // Junit出力ファイル*.xmlの <system-err>の欄に出力される
        rtcout.addStream(new ConsoleHandler());

        rtcout.setLevel(rtcout.PARANOID);    //全て出力する
        System.err.println("--- setLevel:int PARANOID check ---");
        rtcout.println(rtcout.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(rtcout.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(rtcout.TRACE, "TRACE string to STDOUT");
        rtcout.println(rtcout.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(rtcout.INFO, "INFO string to STDOUT");
        rtcout.println(rtcout.WARN, "WARN string to STDOUT");
        rtcout.println(rtcout.ERROR, "ERROR string to STDOUT");
        rtcout.println(rtcout.FATAL, "FATAL string to STDOUT");
        rtcout.println(rtcout.SILENT, "SILENT string to STDOUT");

        rtcout.setLevel(rtcout.VERBOSE);     //これ以下が出力される
        System.err.println("--- setLevel:int VERBOSE check ---");
        rtcout.println(rtcout.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(rtcout.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(rtcout.TRACE, "TRACE string to STDOUT");
        rtcout.println(rtcout.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(rtcout.INFO, "INFO string to STDOUT");
        rtcout.println(rtcout.WARN, "WARN string to STDOUT");
        rtcout.println(rtcout.ERROR, "ERROR string to STDOUT");
        rtcout.println(rtcout.FATAL, "FATAL string to STDOUT");
        rtcout.println(rtcout.SILENT, "SILENT string to STDOUT");

        rtcout.setLevel(rtcout.TRACE);       //これ以下が出力される
        System.err.println("--- setLevel:int TRACE check ---");
        rtcout.println(rtcout.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(rtcout.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(rtcout.TRACE, "TRACE string to STDOUT");
        rtcout.println(rtcout.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(rtcout.INFO, "INFO string to STDOUT");
        rtcout.println(rtcout.WARN, "WARN string to STDOUT");
        rtcout.println(rtcout.ERROR, "ERROR string to STDOUT");
        rtcout.println(rtcout.FATAL, "FATAL string to STDOUT");
        rtcout.println(rtcout.SILENT, "SILENT string to STDOUT");

        rtcout.setLevel(rtcout.DEBUG);       //これ以下が出力される
        System.err.println("--- setLevel:int DEBUG check ---");
        rtcout.println(rtcout.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(rtcout.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(rtcout.TRACE, "TRACE string to STDOUT");
        rtcout.println(rtcout.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(rtcout.INFO, "INFO string to STDOUT");
        rtcout.println(rtcout.WARN, "WARN string to STDOUT");
        rtcout.println(rtcout.ERROR, "ERROR string to STDOUT");
        rtcout.println(rtcout.FATAL, "FATAL string to STDOUT");
        rtcout.println(rtcout.SILENT, "SILENT string to STDOUT");

        rtcout.setLevel(rtcout.INFO);        //これ以下が出力される
        System.err.println("--- setLevel:int INFO check ---");
        rtcout.println(rtcout.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(rtcout.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(rtcout.TRACE, "TRACE string to STDOUT");
        rtcout.println(rtcout.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(rtcout.INFO, "INFO string to STDOUT");
        rtcout.println(rtcout.WARN, "WARN string to STDOUT");
        rtcout.println(rtcout.ERROR, "ERROR string to STDOUT");
        rtcout.println(rtcout.FATAL, "FATAL string to STDOUT");
        rtcout.println(rtcout.SILENT, "SILENT string to STDOUT");

        rtcout.setLevel(rtcout.WARN);        //これ以下が出力される
        System.err.println("--- setLevel:int WARN check ---");
        rtcout.println(rtcout.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(rtcout.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(rtcout.TRACE, "TRACE string to STDOUT");
        rtcout.println(rtcout.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(rtcout.INFO, "INFO string to STDOUT");
        rtcout.println(rtcout.WARN, "WARN string to STDOUT");
        rtcout.println(rtcout.ERROR, "ERROR string to STDOUT");
        rtcout.println(rtcout.FATAL, "FATAL string to STDOUT");
        rtcout.println(rtcout.SILENT, "SILENT string to STDOUT");

        rtcout.setLevel(rtcout.ERROR);       //これ以下が出力される
        System.err.println("--- setLevel:int ERROR check ---");
        rtcout.println(rtcout.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(rtcout.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(rtcout.TRACE, "TRACE string to STDOUT");
        rtcout.println(rtcout.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(rtcout.INFO, "INFO string to STDOUT");
        rtcout.println(rtcout.WARN, "WARN string to STDOUT");
        rtcout.println(rtcout.ERROR, "ERROR string to STDOUT");
        rtcout.println(rtcout.FATAL, "FATAL string to STDOUT");
        rtcout.println(rtcout.SILENT, "SILENT string to STDOUT");

        rtcout.setLevel(rtcout.FATAL);       //これ以下が出力される
        System.err.println("--- setLevel:int FATAL check ---");
        rtcout.println(rtcout.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(rtcout.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(rtcout.TRACE, "TRACE string to STDOUT");
        rtcout.println(rtcout.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(rtcout.INFO, "INFO string to STDOUT");
        rtcout.println(rtcout.WARN, "WARN string to STDOUT");
        rtcout.println(rtcout.ERROR, "ERROR string to STDOUT");
        rtcout.println(rtcout.FATAL, "FATAL string to STDOUT");
        rtcout.println(rtcout.SILENT, "SILENT string to STDOUT");

        rtcout.setLevel(rtcout.SILENT);      //全て出力しない
        System.err.println("--- setLevel:int SILENT check ---");
        rtcout.println(rtcout.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(rtcout.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(rtcout.TRACE, "TRACE string to STDOUT");
        rtcout.println(rtcout.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(rtcout.INFO, "INFO string to STDOUT");
        rtcout.println(rtcout.WARN, "WARN string to STDOUT");
        rtcout.println(rtcout.ERROR, "ERROR string to STDOUT");
        rtcout.println(rtcout.FATAL, "FATAL string to STDOUT");
        rtcout.println(rtcout.SILENT, "SILENT string to STDOUT");

        rtcout.setLevel(999);      //全て出力しない
        System.err.println("--- setLevel:int 999 check ---");
        rtcout.println(rtcout.PARANOID, "PARANOID string to STDOUT");
        rtcout.println(rtcout.VERBOSE, "VERBOSE string to STDOUT");
        rtcout.println(rtcout.TRACE, "TRACE string to STDOUT");
        rtcout.println(rtcout.DEBUG, "DEBUG string to STDOUT");
        rtcout.println(rtcout.INFO, "INFO string to STDOUT");
        rtcout.println(rtcout.WARN, "WARN string to STDOUT");
        rtcout.println(rtcout.ERROR, "ERROR string to STDOUT");
        rtcout.println(rtcout.FATAL, "FATAL string to STDOUT");
        rtcout.println(rtcout.SILENT, "SILENT string to STDOUT");

    }

    /**
    *
    * ファイル出力のテスト
    *
    */
    public void test_fileout_PARANOID() {
        Logbuf rtcout = new Logbuf("TEST1");

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
        rtcout.setLevel("PARANOID");        //全て出力される
        rtcout.println(rtcout.PARANOID, "PARANOID string to rtc.log");
        rtcout.println(rtcout.VERBOSE, "VERBOSE string to rtc.log");
        rtcout.println(rtcout.TRACE, "TRACE string to rtc.log");
        rtcout.println(rtcout.DEBUG, "DEBUG string to rtc.log");
        rtcout.println(rtcout.INFO, "INFO string to rtc.log");
        rtcout.println(rtcout.WARN, "WARN string to rtc.log");
        rtcout.println(rtcout.ERROR, "ERROR string to rtc.log");
        rtcout.println(rtcout.FATAL, "FATAL string to rtc.log");
        rtcout.println(rtcout.SILENT, "SILENT string to rtc.log");

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
    *
    */
    public void test_fileout_VERBOSE() {
        Logbuf rtcout = new Logbuf("TEST2");

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
        rtcout.setLevel("VERBOSE");     //これ以下が出力される
        rtcout.println(rtcout.PARANOID, "PARANOID string to rtc.log");
        rtcout.println(rtcout.VERBOSE, "VERBOSE string to rtc.log");
        rtcout.println(rtcout.TRACE, "TRACE string to rtc.log");
        rtcout.println(rtcout.DEBUG, "DEBUG string to rtc.log");
        rtcout.println(rtcout.INFO, "INFO string to rtc.log");
        rtcout.println(rtcout.WARN, "WARN string to rtc.log");
        rtcout.println(rtcout.ERROR, "ERROR string to rtc.log");
        rtcout.println(rtcout.FATAL, "FATAL string to rtc.log");
        rtcout.println(rtcout.SILENT, "SILENT string to rtc.log");

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
    *
    */
    public void test_fileout_TRACE() {
        Logbuf rtcout = new Logbuf("TEST3");

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
        rtcout.setLevel("TRACE");       //これ以下が出力される
        rtcout.println(rtcout.PARANOID, "PARANOID string to rtc.log");
        rtcout.println(rtcout.VERBOSE, "VERBOSE string to rtc.log");
        rtcout.println(rtcout.TRACE, "TRACE string to rtc.log");
        rtcout.println(rtcout.DEBUG, "DEBUG string to rtc.log");
        rtcout.println(rtcout.INFO, "INFO string to rtc.log");
        rtcout.println(rtcout.WARN, "WARN string to rtc.log");
        rtcout.println(rtcout.ERROR, "ERROR string to rtc.log");
        rtcout.println(rtcout.FATAL, "FATAL string to rtc.log");
        rtcout.println(rtcout.SILENT, "SILENT string to rtc.log");

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
    *
    */
    public void test_fileout_DEBUG() {
        Logbuf rtcout = new Logbuf("TEST4");

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
        rtcout.setLevel("DEBUG");       //これ以下が出力される
        rtcout.println(rtcout.PARANOID, "PARANOID string to rtc.log");
        rtcout.println(rtcout.VERBOSE, "VERBOSE string to rtc.log");
        rtcout.println(rtcout.TRACE, "TRACE string to rtc.log");
        rtcout.println(rtcout.DEBUG, "DEBUG string to rtc.log");
        rtcout.println(rtcout.INFO, "INFO string to rtc.log");
        rtcout.println(rtcout.WARN, "WARN string to rtc.log");
        rtcout.println(rtcout.ERROR, "ERROR string to rtc.log");
        rtcout.println(rtcout.FATAL, "FATAL string to rtc.log");
        rtcout.println(rtcout.SILENT, "SILENT string to rtc.log");

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
    *
    */
    public void test_fileout_INFO() {
        Logbuf rtcout = new Logbuf("TEST5");

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
        rtcout.setLevel("INFO");        //これ以下が出力される
        rtcout.println(rtcout.PARANOID, "PARANOID string to rtc.log");
        rtcout.println(rtcout.VERBOSE, "VERBOSE string to rtc.log");
        rtcout.println(rtcout.TRACE, "TRACE string to rtc.log");
        rtcout.println(rtcout.DEBUG, "DEBUG string to rtc.log");
        rtcout.println(rtcout.INFO, "INFO string to rtc.log");
        rtcout.println(rtcout.WARN, "WARN string to rtc.log");
        rtcout.println(rtcout.ERROR, "ERROR string to rtc.log");
        rtcout.println(rtcout.FATAL, "FATAL string to rtc.log");
        rtcout.println(rtcout.SILENT, "SILENT string to rtc.log");

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
    *
    */
    public void test_fileout_WARN() {
        Logbuf rtcout = new Logbuf("TEST6");

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
        rtcout.setLevel("WARN");        //これ以下が出力される
        rtcout.println(rtcout.PARANOID, "PARANOID string to rtc.log");
        rtcout.println(rtcout.VERBOSE, "VERBOSE string to rtc.log");
        rtcout.println(rtcout.TRACE, "TRACE string to rtc.log");
        rtcout.println(rtcout.DEBUG, "DEBUG string to rtc.log");
        rtcout.println(rtcout.INFO, "INFO string to rtc.log");
        rtcout.println(rtcout.WARN, "WARN string to rtc.log");
        rtcout.println(rtcout.ERROR, "ERROR string to rtc.log");
        rtcout.println(rtcout.FATAL, "FATAL string to rtc.log");
        rtcout.println(rtcout.SILENT, "SILENT string to rtc.log");

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
    *
    */
    public void test_fileout_ERROR() {
        Logbuf rtcout = new Logbuf("TEST7");

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
        rtcout.setLevel("ERROR");       //これ以下が出力される
        rtcout.println(rtcout.PARANOID, "PARANOID string to rtc.log");
        rtcout.println(rtcout.VERBOSE, "VERBOSE string to rtc.log");
        rtcout.println(rtcout.TRACE, "TRACE string to rtc.log");
        rtcout.println(rtcout.DEBUG, "DEBUG string to rtc.log");
        rtcout.println(rtcout.INFO, "INFO string to rtc.log");
        rtcout.println(rtcout.WARN, "WARN string to rtc.log");
        rtcout.println(rtcout.ERROR, "ERROR string to rtc.log");
        rtcout.println(rtcout.FATAL, "FATAL string to rtc.log");
        rtcout.println(rtcout.SILENT, "SILENT string to rtc.log");

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
    *
    */
    public void test_fileout_FATAL() {
        Logbuf rtcout = new Logbuf("TEST8");

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
        rtcout.setLevel("FATAL");       //これ以下が出力される
        rtcout.println(rtcout.PARANOID, "PARANOID string to rtc.log");
        rtcout.println(rtcout.VERBOSE, "VERBOSE string to rtc.log");
        rtcout.println(rtcout.TRACE, "TRACE string to rtc.log");
        rtcout.println(rtcout.DEBUG, "DEBUG string to rtc.log");
        rtcout.println(rtcout.INFO, "INFO string to rtc.log");
        rtcout.println(rtcout.WARN, "WARN string to rtc.log");
        rtcout.println(rtcout.ERROR, "ERROR string to rtc.log");
        rtcout.println(rtcout.FATAL, "FATAL string to rtc.log");
        rtcout.println(rtcout.SILENT, "SILENT string to rtc.log");

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
    *
    */
    public void test_fileout_SILENT() {
        Logbuf rtcout = new Logbuf("TEST9");

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
        rtcout.setLevel("SILENT");      //これ以下が出力される
        rtcout.println(rtcout.PARANOID, "PARANOID string to rtc.log");
        rtcout.println(rtcout.VERBOSE, "VERBOSE string to rtc.log");
        rtcout.println(rtcout.TRACE, "TRACE string to rtc.log");
        rtcout.println(rtcout.DEBUG, "DEBUG string to rtc.log");
        rtcout.println(rtcout.INFO, "INFO string to rtc.log");
        rtcout.println(rtcout.WARN, "WARN string to rtc.log");
        rtcout.println(rtcout.ERROR, "ERROR string to rtc.log");
        rtcout.println(rtcout.FATAL, "FATAL string to rtc.log");
        rtcout.println(rtcout.SILENT, "SILENT string to rtc.log");

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
    *
    */
    public void test_strToLogLevel() {
        Logbuf rtcout = new Logbuf("test_strToLogLevel");
        rtcout.addStream(new ConsoleHandler());
        rtcout.setLevel("PARANOID");

        System.err.println("--- test_strToLogLevel() check ---");
        int lv = 0;
        lv = rtcout.strToLogLevel("SILENT");
        assertEquals(rtcout.SILENT, lv);

        lv = rtcout.strToLogLevel("FATAL");
        assertEquals(rtcout.FATAL, lv);

        lv = rtcout.strToLogLevel("ERROR");
        assertEquals(rtcout.ERROR, lv);

        lv = rtcout.strToLogLevel("WARN");
        assertEquals(rtcout.WARN, lv);

        lv = rtcout.strToLogLevel("INFO");
        assertEquals(rtcout.INFO, lv);

        lv = rtcout.strToLogLevel("DEBUG");
        assertEquals(rtcout.DEBUG, lv);

        lv = rtcout.strToLogLevel("TRACE");
        assertEquals(rtcout.TRACE, lv);

        lv = rtcout.strToLogLevel("VERBOSE");
        assertEquals(rtcout.VERBOSE, lv);

        lv = rtcout.strToLogLevel("PARANOID");
        assertEquals(rtcout.PARANOID, lv);

        lv = rtcout.strToLogLevel("other");
        assertEquals(rtcout.SILENT, lv);

        System.err.println("--- test_strToLogLevel() is OK ---");
    }

    /**
    *
    * setDateFormat()テスト
    * 引数 int
    */
    public void test_setDateFormat() {
        Logbuf rtcout = new Logbuf("test_setDateFormat");
        rtcout.addStream(new ConsoleHandler());
        rtcout.setLevel("PARANOID");
        String format = new String();

        System.err.println("--- test_setDateFormat() check ---");
        // default
        // "logger.date_format",     "%b %d %H:%M:%S",
        format = "%b %d %H:%M:%S";
        rtcout.setDateFormat(format);
        rtcout.println(rtcout.TRACE, "DateFormat Month day HH:MI:SS");
        // Junit出力ファイル*.xmlの <system-err>の欄に出力される
        // 日付と時刻のパターンが、Month day hh:mi:ss であれがOK
        //Sep 29 16:36:53 TEST1 TRACE    : TRACE string to rtc.log

        format = "%Y/%m/%d %p %I:%M:%S";
        rtcout.setDateFormat(format);
        rtcout.println(rtcout.TRACE, "DateFormat YYYY/MM/DD pp II:MI:SS");
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
    * Managerのインスタンステスト
    * Managerが管理しているLogbufを取得しログ出力が行えるか？
    */
    public void test_manager_logbuf() {
        System.err.println("--- test_manager_logbuf() start ---");
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

//        Logbuf rtcout = new Logbuf("test_manager_logbuf");
        Logbuf rtcout = new Logbuf("Manager");  //Manager 出力ファイルに出力される
//        Logbuf rtcout = new Logbuf("Manager.test");  //これは、以下のファイル出力なし

        // STDOUT
        // Junit出力ファイル*.xmlの <system-err>の欄に出力される
        System.err.println("--- test_manager_logbuf() setLevel() Not Set ---");
        rtcout.println(rtcout.INFO, "--- setLevel() Not Set ---");
        rtcout.println(rtcout.PARANOID, "PARANOID string 0");
        rtcout.println(rtcout.VERBOSE, "VERBOSE string 0");
        rtcout.println(rtcout.TRACE, "TRACE string 0");
        rtcout.println(rtcout.DEBUG, "DEBUG string 0");
        rtcout.println(rtcout.INFO, "INFO string 0");
        rtcout.println(rtcout.WARN, "WARN string 0");
        rtcout.println(rtcout.ERROR, "ERROR string 0");
        rtcout.println(rtcout.FATAL, "FATAL string 0");
        rtcout.println(rtcout.SILENT, "SILENT string 0");
        // rtcout.println()の内容が、defaultのINFO以下が出力されればOK.

        rtcout.setLevel("INFO");    //default
        System.err.println("--- setLevel(INFO), addStream(STDOUT) Not set ---");
        rtcout.println(rtcout.INFO, "--- setLevel(INFO), addStream(STDOUT) Not set ---");
        rtcout.println(rtcout.PARANOID, "PARANOID string 1");
        rtcout.println(rtcout.VERBOSE, "VERBOSE string 1");
        rtcout.println(rtcout.TRACE, "TRACE string 1");
        rtcout.println(rtcout.DEBUG, "DEBUG string 1");
        rtcout.println(rtcout.INFO, "INFO string 1");
        rtcout.println(rtcout.WARN, "WARN string 1");
        rtcout.println(rtcout.ERROR, "ERROR string 1");
        rtcout.println(rtcout.FATAL, "FATAL string 1");
        rtcout.println(rtcout.SILENT, "SILENT string 1");
        // setLevel()以下の内容が、出力されればOK.

        rtcout.setLevel("PARANOID");    //全て
        System.err.println("--- setLevel(PARANOID), addStream(STDOUT) Not set ---");
        rtcout.println(rtcout.INFO, "--- setLevel(PARANOID), addStream(STDOUT) Not set ---");
        rtcout.println(rtcout.PARANOID, "PARANOID string 2");
        rtcout.println(rtcout.VERBOSE, "VERBOSE string 2");
        rtcout.println(rtcout.TRACE, "TRACE string 2");
        rtcout.println(rtcout.DEBUG, "DEBUG string 2");
        rtcout.println(rtcout.INFO, "INFO string 2");
        rtcout.println(rtcout.WARN, "WARN string 2");
        rtcout.println(rtcout.ERROR, "ERROR string 2");
        rtcout.println(rtcout.FATAL, "FATAL string 2");
        rtcout.println(rtcout.SILENT, "SILENT string 2");
        // setLevel()以下の内容が、出力されればOK.

        rtcout.addStream(new ConsoleHandler());
        System.err.println("--- addStream(STDOUT) set ---");
        rtcout.println(rtcout.INFO, "--- addStream(STDOUT) set ---");
        rtcout.println(rtcout.PARANOID, "PARANOID string 3");
        rtcout.println(rtcout.VERBOSE, "VERBOSE string 3");
        rtcout.println(rtcout.TRACE, "TRACE string 3");
        rtcout.println(rtcout.DEBUG, "DEBUG string 3");
        rtcout.println(rtcout.INFO, "INFO string 3");
        rtcout.println(rtcout.WARN, "WARN string 3");
        rtcout.println(rtcout.ERROR, "ERROR string 3");
        rtcout.println(rtcout.FATAL, "FATAL string 3");
        rtcout.println(rtcout.SILENT, "SILENT string 3");
        // setLevel()以下の内容が、STDOUTに出力されればOK.

        System.err.println("--- test_manager_logbuf() end ---");

    }

}
