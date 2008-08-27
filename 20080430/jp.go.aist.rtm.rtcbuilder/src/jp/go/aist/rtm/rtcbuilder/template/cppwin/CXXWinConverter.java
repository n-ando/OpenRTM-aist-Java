package jp.go.aist.rtm.rtcbuilder.template.cppwin;

import java.util.UUID;

import jp.go.aist.rtm.rtcbuilder.template.cpp.CXXConverter;

/**
 * CXXソースを出力する際に使用されるユーティリティ
 */
public class CXXWinConverter extends CXXConverter{
	private final String idlLongDouble = "longdouble";

	private final String cppLongDouble = "CORBA::Double";

	public CXXWinConverter() {
		super();
		mapType.put(idlLongDouble, cppLongDouble);
	}
	/**
	 * Random UUIDを取得する
	 * 
	 * @return UUID
	 */
	public static String getRandomUUID() {
		return UUID.randomUUID().toString();
//		return "ef7bf9c2-3efa-11dc-8b67-000c297a69c0";
	}

	/**
	 * CPP型からCORBA型へのマッピングを定義する
	 * 
	 * @param strcpp
	 *            CPP型
	 * @return CORBA型
	 */
	public String convCpp2CORBA(String strcpp) {
		String result = mapType.get(strcpp);
		if( result == null ) result = strcpp + "*";
		return result;
	}
}
