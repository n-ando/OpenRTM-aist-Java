package jp.go.aist.rtm.rtctemplate._test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.core.runtime.Platform;

import junit.framework.TestCase;

public class TestBase extends TestCase {
//	protected String rootPath = "C:\\Tech-Arts\\Eclipse\\jp.go.aist.rtm.rtctemplate\\";
	protected String rootPath;

	public TestBase () {
		File fileCurrent = new File(".");
		rootPath = fileCurrent.getAbsolutePath();
		rootPath = rootPath.substring(0,rootPath.length()-1);
	}
	protected String readFile(String fileName) {
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
