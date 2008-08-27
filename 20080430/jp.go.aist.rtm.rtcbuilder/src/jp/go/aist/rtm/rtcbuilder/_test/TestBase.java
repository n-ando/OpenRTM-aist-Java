package jp.go.aist.rtm.rtcbuilder._test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import jp.go.aist.rtm.rtcbuilder.generator.GeneratedResult;
import junit.framework.TestCase;

public class TestBase extends TestCase {
//	protected String rootPath = "C:\\Tech-Arts\\Eclipse\\jp.go.aist.rtm.rtcbuilder\\";
	protected String rootPath;
	protected String expPath;
	protected String expContent;
	protected int index;

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
	protected int getFileIndex(String targetName, List<GeneratedResult> targetList) {
		int resultindex = -1;
		
		for( int intIdx=0; intIdx<targetList.size(); intIdx++ ) {
			if( targetList.get(intIdx).getName().equals(targetName) ) {
				return intIdx;
			}
		}
		fail();
		return resultindex;
	}
	protected void checkCode(List<GeneratedResult> result, String resourceDir, String fileName) {
		index = getFileIndex(fileName, result);
		expPath = resourceDir + fileName;
		expContent = readFile(expPath);
		assertEquals(expContent, result.get(index).getCode());
	}
}
