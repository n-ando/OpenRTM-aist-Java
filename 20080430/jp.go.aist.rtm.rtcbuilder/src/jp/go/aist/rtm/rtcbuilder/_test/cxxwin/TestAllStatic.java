package jp.go.aist.rtm.rtcbuilder._test.cxxwin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TestAllStatic {
	static public String readFile(String fileName) {
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
