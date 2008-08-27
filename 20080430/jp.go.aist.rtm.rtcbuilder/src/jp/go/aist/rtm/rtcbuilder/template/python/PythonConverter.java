package jp.go.aist.rtm.rtcbuilder.template.python;

import java.util.HashMap;
import java.util.Map;

import jp.go.aist.rtm.rtcbuilder.generator.param.ConfigSetParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.idl.ServiceArgumentParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.idl.ServiceClassParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.idl.ServiceMethodParam;

/**
 * Pythonソースを出力する際に使用されるユーティリティ
 */
public class PythonConverter {
	protected Map<String, String> mapType;
	protected Map<String, String> mapTypeArgs;
	protected Map mapTypeArgsCmnt;

	private final String dirIn = "in";
	private final String dirOut = "out";
	private final String dirInOut = "inout";

	private final String idlLongLong = "longlong";
	private final String idlUnsignedLong = "unsignedlong";
	private final String idlUnsignedLongLong = "unsignedlonglong";
	private final String idlUnsignedShort = "unsignedshort";
	private final String idlString = "string";
	private final String idlWstring = "wstring";
	private final String idlVoid= "void";
	//
	private final String pythonLongLong = "longlong";
	private final String pythonUnsignedLong = "ulong";
	private final String pythonUnsignedLongLong = "ulonglong";
	private final String pythonUnsignedShort = "ushort";
	private final String pythonString = "string";
	//
	private final String pythonLongLongParam = "long long";
	private final String pythonUnsignedLongParam = "unsigned long";
	private final String pythonUnsignedLongLongParam = "unsigned long long";
	private final String pythonUnsignedShortParam = "unsigned short";

	public PythonConverter() {
		mapType = new HashMap<String, String>();
		mapType.put(idlLongLong, pythonLongLong);
		mapType.put(idlUnsignedLong, pythonUnsignedLong);
		mapType.put(idlUnsignedLongLong, pythonUnsignedLongLong);
		mapType.put(idlUnsignedShort, pythonUnsignedShort);
		//
		mapTypeArgs = new HashMap<String, String>(mapType);
		mapTypeArgs.put(idlLongLong, pythonLongLongParam);
		mapTypeArgs.put(idlUnsignedLong, pythonUnsignedLongParam);
		mapTypeArgs.put(idlUnsignedLongLong, pythonUnsignedLongLongParam);
		mapTypeArgs.put(idlUnsignedShort, pythonUnsignedShortParam);
		//
//		mapTypeArgsCmnt = new HashMap(mapTypeArgs);
		//
	}

	/**
	 * CORBA型からPython型へ型を変換する(TypeDef考慮)
	 * 
	 * @param strCorba CORBA型
	 * @return Python型
	 */
	public String convCORBA2PythonTypeDef(String strCorba, ServiceClassParam scp) {
		String strType = scp.getTypeDef().get(strCorba);
		String result = "";
		if(strType.endsWith("[]")) {
			strType = strType.substring(0, strType.length()-2);
		}
		
		if( strType.equals(pythonString)) {
			result = "(omniORB.tcInternal.tv_string,0)";
		} else {
			result = "omniORB.tcInternal.tv_" + strType;
		}
		return result;
	}
	/**
	 * CORBA型からPython型へ型を変換する
	 * 
	 * @param strCorba CORBA型
	 * @param scp サービスクラス
	 * @return Python型
	 */
	public String convCORBA2Python(String strCorba) {
		String result = mapType.get(strCorba);
		if( result == null ) result = strCorba;

		return result;
	}
	/**
	 * CORBA型からPython型へ型を変換する(コメント用)
	 * 
	 * @param strCorba CORBA型
	 * @param scp サービスクラス
	 * @return Python型
	 */
	public String convCORBA2PythonArg(String strCorba) {
		String result = mapTypeArgs.get(strCorba);
		if( result == null ) result = strCorba;

		return result;
	}
	/**
	 * CORBA型からPython型へ型を変換する
	 * 
	 * @param strCorba CORBA型
	 * @param scp サービスクラス
	 * @return Python型
	 */
	public String convCORBA2Python4IDL(String strCorba, ServiceClassParam scp) {
		String strType = scp.getTypeDef().get(strCorba);
		String result = "";
		if( strType==null) {
			if(strCorba.equals(idlString)) {
				result = "(omniORB.tcInternal.tv_string,0)";
			} else if(strCorba.equals(idlWstring)) {
				result = "(omniORB.tcInternal.tv_wstring,0)";
			} else {
				result = mapType.get(strCorba);
				if( result == null ) result = strCorba;
				result = "omniORB.tcInternal.tv_" + result;
			}
		} else {
			result = "omniORB.typeMapping[\"IDL:" + strCorba + ":1.0\"]";
		}
		return result;
	}
	/**
	 * メソッド入力パラメータの型を取得する
	 * 
	 * @param strCorba CORBA型
	 * @param scp サービスクラス
	 * @return 入力パラメータ
	 */
	public String selectInParamType(ServiceMethodParam smp, ServiceClassParam scp) {
		String result = "";
		int intHit = 0;

		for(ServiceArgumentParam arg : smp.getArguments() ) {
			if(arg.getDirection().equals(dirIn) || arg.getDirection().equals(dirInOut)) {
				result = result + convCORBA2Python4IDL(arg.getType(),scp) + ", ";
				intHit++;
			}
		}
		if( intHit > 1 ) result = result.substring(0, result.length()-2 );
		result = "(" + result;
		return result;
	}

	/**
	 * メソッド入力パラメータの名称を取得する
	 * 
	 * @param strCorba CORBA型
	 * @param scp サービスクラス
	 * @return 入力パラメータ
	 */
	public String selectInParamName(ServiceMethodParam smp, ServiceClassParam scp) {
		String result = "";

		for(ServiceArgumentParam arg : smp.getArguments() ) {
			if(arg.getDirection().equals(dirIn) || arg.getDirection().equals(dirInOut)) {
				result =  result + ", " + arg.getName();
			}
		}
		return result;
	}
	
	/**
	 * メソッド出力パラメータの型を取得する
	 * 
	 * @param strCorba CORBA型
	 * @param scp サービスクラス
	 * @return 出力ラメータ
	 */
	public String selectOutParamType(ServiceMethodParam smp, ServiceClassParam scp) {
		String result = "";
		int intHit = 0;

		if(!smp.getIsVoid()) {
			result = result + convCORBA2Python4IDL(smp.getType(),scp) + ", ";
			intHit++;
		}
		for(ServiceArgumentParam aug : smp.getArguments() ) {
			if(aug.getDirection().equals(dirOut) || aug.getDirection().equals(dirInOut)) {
				result = result + convCORBA2Python4IDL(aug.getType(),scp) + ", ";
				intHit++;
			}
		}
		if( intHit > 1 ) result = result.substring(0, result.length()-2 );
		return result;
	}
	/**
	 * メソッド出力パラメータの名称を取得する
	 * 
	 * @param strCorba CORBA型
	 * @param scp サービスクラス
	 * @return 出力パラメータ
	 */
	public String selectOutParamName(ServiceMethodParam smp, ServiceClassParam scp) {
		String result = "";
		boolean blnHit = false;
		
		if( !smp.getType().equals(idlVoid) ) {
			result = " result";
		}
		for(ServiceArgumentParam arg : smp.getArguments() ) {
			if(arg.getDirection().equals(dirOut) || arg.getDirection().equals(dirInOut)) {
				if( result.length()>0 ) result += ",";
				result =  result + " " + arg.getName();
				blnHit = true;
			}
		}
		if( smp.getType().equals(idlVoid) && !blnHit) {
			result = " None";
		}
		return result;
	}
	/**
	 * Sequence型か判断する
	 * 
	 * @param type 検証対象型
	 * @return 検証結果
	 */
	public String convPortInit(String type) {
		if( this.isSequence(type) )
			return "[]";
		return "0";
	}
	/**
	 * Sequence型か判断する
	 * 
	 * @param type 検証対象型
	 * @return 検証結果
	 */
	private boolean isSequence(String type) {
		if( type.toLowerCase().endsWith("seq") )
			return true;
		return false;
	}
	/**
	 * String型か判断する
	 * 
	 * @param type 検証対象型
	 * @return 検証結果
	 */
	public boolean isString(String type) {
		if( type.toLowerCase().equals(pythonString) )
			return true;
		return false;
	}
	/**
	 * パラメータの初期値を取得する
	 * 
	 * @param config 対象パラメータ
	 * @return 初期値
	 */
	public String convDefaultVal(ConfigSetParam config) {
		String defVal = config.getDefaultVal();
		if( config.getName().startsWith("vector") ) {
			String[] eachVal = defVal.split(",");
			String result = "";
			for(int intIdx=0;intIdx<eachVal.length;intIdx++) {
				if(intIdx>0) result += ", ";
				result = result + eachVal[intIdx];
			}
			result = "[" + result + "]";
			return result;
		} else if(isString(config.getType()) ) {
			return "'" + defVal + "'";
		} else {
			return defVal;
		}
	}
}
