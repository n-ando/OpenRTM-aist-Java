package jp.go.aist.rtm.rtcbuilder.template.cpp;

import java.util.HashMap;
import java.util.Map;

import jp.go.aist.rtm.rtcbuilder.IRtcBuilderConstants;
import jp.go.aist.rtm.rtcbuilder.generator.param.idl.ServiceClassParam;
import jp.go.aist.rtm.rtcbuilder.template.TemplateHelper;

/**
 * CXXソースを出力する際に使用されるユーティリティ
 */
public class CXXConverter {
	protected Map<String, String> mapType;
	
	private final String idlLongLong = "longlong";
	private final String idlLong = "long";
	private final String idlUnsignedLong = "unsignedlong";
	private final String idlUnsignedLongLong = "unsignedlonglong";
	private final String idlShort = "short";
	private final String idlUnsignedShort = "unsignedshort";
	private final String idlFloat = "float";
	private final String idlDouble = "double";
	private final String idlLongDouble = "longdouble";
	private final String idlChar = "char";
	private final String idlWchar = "wchar";
	private final String idlOctet = "octet";
	private final String idlBoolean = "boolean";
	private final String idlString = "string";
	private final String idlWstring = "wstring";
	private final String idlAny = "any";
	private final String idlVoid= "void";

	private final String cppLongLong = "CORBA::LongLong";
	private final String cppLong = "CORBA::Long";
	private final String cppUnsignedLong = "CORBA::ULong";
	private final String cppUnsignedLongLong = "CORBA::ULongLong";
	private final String cppShort = "CORBA::Short";
	private final String cppUnsignedShort = "CORBA::UShort";
	private final String cppFloat = "CORBA::Float";
	private final String cppDouble = "CORBA::Double";
	private final String cppLongDouble = "CORBA::LongDouble";
	private final String cppChar = "CORBA::Char";
	private final String cppWchar = "CORBA::WChar";
	private final String cppOctet = "CORBA::Octet";
	private final String cppBoolean = "CORBA::Boolean";
	private final String cppString = "char*";
	private final String cppWstring = "CORBA::WChar*";
	private final String cppAny = "CORBA::Any*";
	private final String cppVoid= "void";

	public CXXConverter() {
		mapType = new HashMap<String, String>();
		mapType.put(idlLongLong, cppLongLong);
		mapType.put(idlLong, cppLong);
		mapType.put(idlUnsignedLong, cppUnsignedLong);
		mapType.put(idlUnsignedLongLong, cppUnsignedLongLong);
		mapType.put(idlShort, cppShort);
		mapType.put(idlUnsignedShort, cppUnsignedShort);
		mapType.put(idlFloat, cppFloat);
		mapType.put(idlDouble, cppDouble);
		mapType.put(idlLongDouble, cppLongDouble);
		mapType.put(idlChar, cppChar);
		mapType.put(idlWchar, cppWchar);
		mapType.put(idlOctet, cppOctet);
		mapType.put(idlBoolean, cppBoolean);
		mapType.put(idlString, cppString);
		mapType.put(idlWstring, cppWstring);
		mapType.put(idlAny, cppAny);
		mapType.put(idlVoid, cppVoid);
	}

	/**
	 * サービススタブ名を取得する
	 * 
	 * @param serviceClassParam
	 *            ServiceClassParam
	 * @return
	 */
	public static StringBuffer getSvcStubName(
			ServiceClassParam serviceClassParam) {
		StringBuffer result = new StringBuffer();

		result.append(serviceClassParam.getName());
		if (TemplateHelper.getServiceStubSuffix() != null
				&& !TemplateHelper.getServiceStubSuffix().equals(""))
			result.append(TemplateHelper.getServiceStubSuffix());
		else
			result.append(IRtcBuilderConstants.DEFAULT_SVC_STUB_SUFFIX);
		return result;
	}

	/**
	 * サービススケルトン名を取得する
	 * 
	 * @param serviceClassParam
	 *            ServiceClassParam
	 * @return
	 */
	public static StringBuffer getSvcSkelName(
			ServiceClassParam serviceClassParam) {
		StringBuffer result = new StringBuffer();

		result.append(serviceClassParam.getName());
		if (TemplateHelper.getServiceSkelSuffix() != null
				&& !TemplateHelper.getServiceSkelSuffix().equals(""))
			result.append(TemplateHelper.getServiceSkelSuffix());
		else
			result.append(IRtcBuilderConstants.DEFAULT_SVC_SKEL_SUFFIX);
		return result;
	}

	/**
	 * サービスインプル名を取得する
	 * 
	 * @param serviceClassParam
	 *            ServiceClassParam
	 * @return
	 */
	public static StringBuffer getSvcImplName(
			ServiceClassParam serviceClassParam) {
		StringBuffer result = new StringBuffer();

		result.append(serviceClassParam.getName());
		if (!TemplateHelper.getServiceImplSuffix().equals(""))
			result.append(TemplateHelper.getServiceImplSuffix());
		else
			result.append(IRtcBuilderConstants.DEFAULT_SVC_IMPL_SUFFIX);
		return result;
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

	/**
	 * CPP型からCORBA型へのマッピングを定義する(引数用)
	 * 
	 * @param strType
	 *            CPP型
	 * @param strDir
	 *            入出力
	 * @return CORBA型
	 */
	public String convCpp2CORBAforArg(String strType, String strDir) {
		String result = null;
		
		result = convCpp2CORBA(strType);
		
		if(strType.equals("string")) {
			if(strDir.equals("in"))
				result = "const char*";
			else if(strDir.equals("out"))
				result = "CORBA::String_out";
			else if(strDir.equals("inout"))
				result = "char*&";
		} else if(strType.equals("wstring")) {
			if(strDir.equals("in"))
				result = "const CORBA::WChar*";
			else if(strDir.equals("out"))
				result = "CORBA::WString_out";
			else if(strDir.equals("inout"))
				result = "CORBA::WChar*&";
		} else if(strType.equals("any")) {
			if(strDir.equals("in"))
				result = "const CORBA::Any&";
			else if(strDir.equals("out"))
				result = "CORBA::Any_OUT_arg";
			else if(strDir.equals("inout"))
				result = "CORBA::Any&";
		} else {
			if(strDir.equals("out") || strDir.equals("inout"))
				result = result + "&";
		}

		return result;
	}

	/**
	 * パッケージの区切り文字を「.」から「::」に変更する
	 * @param fullName
	 * @return
	 */
	public static String convertDelimiter(String fullName) {
		return fullName.replaceAll("\\.", "::");
	}
}
