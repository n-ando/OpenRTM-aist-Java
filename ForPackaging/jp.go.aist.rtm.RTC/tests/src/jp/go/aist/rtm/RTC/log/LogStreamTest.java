package jp.go.aist.rtm.RTC.log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.FileHandler;

//import jp.go.aist.rtm.RTC.log.LogStream;
import jp.go.aist.rtm.RTC.log.Logbuf;
//import jp.go.aist.rtm.RTC.log.LogbufOn;
import junit.framework.TestCase;

/**
*
* ロギングクラス　テスト
* 対象クラス：LogStream, MedLogbuf, LogbunOn
*
*/
public class LogStreamTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
        java.io.File fileCurrent = new java.io.File(".");
        String rootPath = fileCurrent.getAbsolutePath();
        rootPath = rootPath.substring(0,rootPath.length()-1);
        String logFileName = rootPath + "tests\\fixtures\\Logging\\rtc.log";
        File logFile = new File(logFileName);
        if( logFile.exists() ) {
            logFile.delete();
        }
        //
        logFileName = rootPath + "tests\\fixtures\\Logging\\rtch.log";
        logFile = new File(logFileName);
        if( logFile.exists() ) {
            logFile.delete();
        }
        //
        logFileName = rootPath + "tests\\fixtures\\Logging\\rtcl.log";
        logFile = new File(logFileName);
        if( logFile.exists() ) {
            logFile.delete();
        }
        //
        logFileName = rootPath + "tests\\fixtures\\Logging\\rtcls.log";
        logFile = new File(logFileName);
        if( logFile.exists() ) {
            logFile.delete();
        }
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
    *
    * 通常のログ出力
    *
    */
    public void test_Logging() {
/*
        Logbuf logbuf = new LogbufOn();
        MedLogbuf medLogbuf = new MedLogbuf(logbuf);
        LogStream rtcout = new LogStream(medLogbuf);
        
        java.io.File fileCurrent = new java.io.File(".");
        String rootPath = fileCurrent.getAbsolutePath();
        rootPath = rootPath.substring(0,rootPath.length()-1);
        String logFileName = rootPath + "tests\\fixtures\\Logging\\rtc.log";

        // Open logfile
        try {
            logbuf.open(new FileHandler(logFileName));
        } catch (SecurityException e) {
            e.printStackTrace();
            fail();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
        rtcout.println(rtcout.DEBUG, "test");
        String result = readFile(logFileName);
        String expected = readFile(logFileName);
//        assertEquals(expected, result);
        logbuf = null;
*/
    }
    
    /**
    *
    * ヘッダを設定したログ出力
    *
    */
    public void test_Headder() {
/*
        Logbuf logbuf = new LogbufOn();
        MedLogbuf medLogbuf = new MedLogbuf(logbuf);
        LogStream rtcout = new LogStream(medLogbuf);
        
        java.io.File fileCurrent = new java.io.File(".");
        String rootPath = fileCurrent.getAbsolutePath();
        rootPath = rootPath.substring(0,rootPath.length()-1);
        String logFileName = rootPath + "tests\\fixtures\\Logging\\rtch.log";

        // Open logfile
        try {
            logbuf.open(new FileHandler(logFileName));
        } catch (SecurityException e) {
            e.printStackTrace();
            fail();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
        medLogbuf.setSuffix("HD");
        rtcout.println(rtcout.DEBUG, "test");
        String result = readFile(logFileName);
        String expected = readFile(logFileName);
//        assertEquals(expected, result);
        logbuf = null;
*/
    }

    /**
    *
    * ログレベルを設定したログ出力
    *
    */
    public void test_Level() {
/*
        Logbuf logbuf = new LogbufOn();
        MedLogbuf medLogbuf = new MedLogbuf(logbuf);
        LogStream rtcout = new LogStream(medLogbuf);
        
        java.io.File fileCurrent = new java.io.File(".");
        String rootPath = fileCurrent.getAbsolutePath();
        rootPath = rootPath.substring(0,rootPath.length()-1);
        String logFileName = rootPath + "tests\\fixtures\\Logging\\rtcl.log";

        // Open logfile
        try {
            logbuf.open(new FileHandler(logFileName));
        } catch (SecurityException e) {
            e.printStackTrace();
            fail();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
        medLogbuf.setSuffix("HD");
        rtcout.setLogLevel(3);
        rtcout.println(rtcout.SILENT, "testsilent");
        rtcout.println(rtcout.INFO, "testnormal");
        String result = readFile(logFileName);
        String expected = readFile(logFileName);
//        assertEquals(expected, result);
        logbuf = null;
*/
    }

    /**
    *
    * 文字列にてログレベルを設定したログ出力
    *
    */
    public void test_LevelStr() {
/*
        Logbuf logbuf = new LogbufOn();
        MedLogbuf medLogbuf = new MedLogbuf(logbuf);
        LogStream rtcout = new LogStream(medLogbuf);
        
        java.io.File fileCurrent = new java.io.File(".");
        String rootPath = fileCurrent.getAbsolutePath();
        rootPath = rootPath.substring(0,rootPath.length()-1);
        String logFileName = rootPath + "tests\\fixtures\\Logging\\rtcls.log";

        // Open logfile
        try {
            logbuf.open(new FileHandler(logFileName));
        } catch (SecurityException e) {
            e.printStackTrace();
            fail();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
        medLogbuf.setSuffix("HD");
        rtcout.setLogLevel("INFO");
        rtcout.println(rtcout.WARN, "testwarn");
        rtcout.println(rtcout.INFO, "testinfo");
        rtcout.println(rtcout.NORMAL, "testnormal");
        String result = readFile(logFileName);
        String expected = readFile(logFileName);
//        assertEquals(expected, result);
        logbuf = null;
*/
    }

    /**
    *
    * ファイル読み込み
    * @param filename  ファイル名
    * @return ファイル内容
    *
    */
    private String readFile(String fileName) {
        StringBuffer stbRet = new StringBuffer();
        try{
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
    
            String str = new String();
            while( (str = br.readLine()) != null ){
                stbRet.append(str + "\r\n");
            }
            br.close();
            fr.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        return stbRet.toString();
    }
}

