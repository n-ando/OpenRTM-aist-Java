package jp.go.aist.rtm.rtctemplate.template._042.cpp;

import java.util.UUID;

/**
 * CXXソースを出力する際に使用されるユーティリティ
 */
public class CXXConverter extends jp.go.aist.rtm.rtctemplate.template.cpp.CXXConverter {
	private static String projectGUID = null;
	private static String projectID = null;
	private static String compProjectID = null;
	private static String sourceID = null;
	private static String headerID = null;
	private static String compSourceID = null;
	private static String compHeaderID = null;

	public static String getProjectGID(boolean isTest) {
		if( isTest ) return "8BC9CEB8-8B4A-11D0-8D11-00A0C91BC942";
		if( projectGUID==null ) projectGUID = UUID.randomUUID().toString();
		return projectGUID;
	}

	public static String getProjectUUID(boolean isTest) {
		if( isTest ) return "D1CD3730-28E2-11DD-88AA-005056C00008";
		if( projectID==null ) projectID = UUID.randomUUID().toString();
		return projectID;
	}

	public static String getCompProjectUUID(boolean isTest) {
		if( isTest ) return "D1322FAE-28E2-11DD-B62B-005056C00008";
		if( projectID==null ) compProjectID = UUID.randomUUID().toString();
		return compProjectID;
	}

	public static String getSourceUUID(boolean isTest) {
		if( isTest ) return "D1CD3730-28E2-11DD-B7E1-005056C00008";
		if( sourceID==null ) sourceID = UUID.randomUUID().toString();
		return sourceID;
	}

	public static String getHeaderUUID(boolean isTest) {
		if( isTest ) return "D1CD3730-28E2-11DD-A46E-005056C00008";
		if( headerID==null ) headerID = UUID.randomUUID().toString();
		return headerID;
	}

	public static String getCompSourceUUID(boolean isTest) {
		if( isTest ) return "D19D74A1-28E2-11DD-81A5-005056C00008";
		if( compSourceID==null ) compSourceID = UUID.randomUUID().toString();
		return compSourceID;
	}

	public static String getCompHeaderUUID(boolean isTest) {
		if( isTest ) return "D19D74A1-28E2-11DD-9671-005056C00008";
		if( compHeaderID==null ) compHeaderID = UUID.randomUUID().toString();
		return compHeaderID;
	}

}
