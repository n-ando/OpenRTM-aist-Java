package jp.go.aist.rtm.rtcbuilder.ui.preference;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import jp.go.aist.rtm.rtcbuilder.RtcBuilderPlugin;

public class ExportPreferenceManager {
	private static ExportPreferenceManager __instance = new ExportPreferenceManager();
	private static final String Separator = ":";

	/**
	 * コンストラクタ
	 * 
	 * @return シングルトン
	 */
	public static ExportPreferenceManager getInstance() {
		getDefaultExportSourceExt();
		getDefaultExportSourceFile();
		getDefaultExportBinaryExt();
		getDefaultExportBinaryFile();
		getDefaultExportSourceBinaryExt();
		getDefaultExportSourceBinaryFile();
		//
		return __instance;
	}

	//Export
	/**
	 * Source Export ファイル拡張子のキー
	 */
	private static final String Export_Source_EXT = ExportPreferenceManager.class.getName()
			+ "EXPORT_SOURCE_EXT";
	/**
	 * Source Export ファイルのキー
	 */
	private static final String Export_Source_File = ExportPreferenceManager.class.getName()
			+ "EXPORT_SOURCE_FILE";

	/**
	 * Binary Export ファイル拡張子のキー
	 */
	private static final String Export_Binary_EXT = ExportPreferenceManager.class.getName()
			+ "EXPORT_BINARY_EXT";
	/**
	 * Binary Export ファイルのキー
	 */
	private static final String Export_Binary_File = ExportPreferenceManager.class.getName()
			+ "EXPORT_BINARY_FILE";

	/**
	 * Source+Binary Export ファイル拡張子のキー
	 */
	private static final String Export_SourceBinary_EXT = ExportPreferenceManager.class.getName()
			+ "EXPORT_SOURCE_BINARY_EXT";
	/**
	 * Source+Binary Export ファイルのキー
	 */
	private static final String Export_SourceBinary_File = ExportPreferenceManager.class.getName()
			+ "EXPORT_SOURCE_BINARY_FILE";

	/**
	 * Source Export ファイル拡張子のデフォルト値
	 */
	public static ArrayList<String> defaultExportSourceExt = new ArrayList<String>();
	/**
	 * Source Export ファイルのデフォルト値
	 */
	public static ArrayList<String> defaultExportSourceFile = new ArrayList<String>();

	/**
	 * Binary Export ファイル拡張子のデフォルト値
	 */
	public static ArrayList<String> defaultExportBinaryExt = new ArrayList<String>();
	/**
	 * Binary Export ファイルのデフォルト値
	 */
	public static ArrayList<String> defaultExportBinaryFile = new ArrayList<String>();


	/**
	 * Source+Binary Export ファイル拡張子のデフォルト値
	 */
	public static ArrayList<String> defaultExportSourceBinaryExt = new ArrayList<String>();
	/**
	 * Source+Binary Export ファイルのデフォルト値
	 */
	public static ArrayList<String> defaultExportSourceBinaryFile = new ArrayList<String>();

	protected PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);

	public static ArrayList<String> getDefaultExportSourceExt() {
		defaultExportSourceExt = new ArrayList<String>();
		defaultExportSourceExt.add("conf");
		defaultExportSourceExt.add("cpp");
		defaultExportSourceExt.add("h");
		defaultExportSourceExt.add("vcproj");
		defaultExportSourceExt.add("java");
		defaultExportSourceExt.add("xml");
		defaultExportSourceExt.add("py");

		return defaultExportSourceExt;
	}
	
	public static ArrayList<String> getDefaultExportSourceFile() {
		defaultExportSourceFile = new ArrayList<String>();
		defaultExportSourceFile.add("Makefile");
		defaultExportSourceFile.add("README");

		return defaultExportSourceFile;
	}

	public static ArrayList<String> getDefaultExportBinaryExt() {
		defaultExportBinaryExt = new ArrayList<String>();
		defaultExportBinaryExt.add("conf");
		defaultExportBinaryExt.add("exe");
		defaultExportBinaryExt.add("class");
		defaultExportBinaryExt.add("py");

		return defaultExportBinaryExt;
	}

	public static ArrayList<String> getDefaultExportBinaryFile() {
		defaultExportBinaryFile = new ArrayList<String>();
		defaultExportBinaryFile.add("README");

		return defaultExportBinaryFile;
	}

	public static ArrayList<String> getDefaultExportSourceBinaryExt() {
		defaultExportSourceBinaryExt = new ArrayList<String>();
		defaultExportSourceBinaryExt.add("conf");
		defaultExportSourceBinaryExt.add("cpp");
		defaultExportSourceBinaryExt.add("h");
		defaultExportSourceBinaryExt.add("vcproj");
		defaultExportSourceBinaryExt.add("java");
		defaultExportSourceBinaryExt.add("xml");
		defaultExportSourceBinaryExt.add("py");
		defaultExportSourceBinaryExt.add("exe");
		defaultExportSourceBinaryExt.add("class");

		return defaultExportSourceBinaryExt;
	}

	public static ArrayList<String> getDefaultExportSourceBinaryFile() {
		defaultExportSourceBinaryFile = new ArrayList<String>();
		defaultExportSourceBinaryFile.add("Makefile");
		defaultExportSourceBinaryFile.add("README");

		return defaultExportSourceBinaryFile;
	}

	public static String convertList2String(ArrayList<String> source) {
		StringBuffer resultTemp = new StringBuffer();
		
		for(String target : source) {
			resultTemp.append(target);
			resultTemp.append(Separator);
		}
		String result = resultTemp.toString();
		if(result.length() ==0) return "";
		return result.substring(0, result.length()-1);
	}

	public static ArrayList<String> convertString2List(String source) {
		String[] resultTemp = source.split(Separator);
		ArrayList<String> result = new ArrayList<String>();
		
		for(int intIdx=0 ; intIdx<resultTemp.length; intIdx++) {
			result.add(resultTemp[intIdx]);
		}
		return result;
	}

	/**
	 * Source Export ファイル拡張子を取得する
	 * 
	 * @param key
	 *            キー
	 * @return Source Export ファイル拡張子リスト
	 */
	public ArrayList<String> getExportSourceExt() {
		RtcBuilderPlugin.getDefault().getPreferenceStore().setDefault(Export_Source_EXT, "");

		String resultTemp = RtcBuilderPlugin.getDefault().getPreferenceStore().getString(Export_Source_EXT);
		ArrayList<String> result = new ArrayList<String>();
		if (resultTemp.equals("")) { // defaultvalue
			result = defaultExportSourceExt;
		} else {
			result = convertString2List(resultTemp);
		}
		return result;
	}
	/**
	 * Source Export ファイル拡張子を設定する
	 * 
	 * @param key
	 *            キー
	 * @param exportSourceExt
	 *            Source Export ファイル拡張子リスト
	 */
	public void setExportSourceExt(ArrayList<String> exportSourceExt) {
		ArrayList<String> oldExportSourceExt = getExportSourceExt();

		RtcBuilderPlugin.getDefault().getPreferenceStore().setValue(Export_Source_EXT, convertList2String(exportSourceExt));

		propertyChangeSupport.firePropertyChange(Export_Source_EXT,
				oldExportSourceExt, exportSourceExt);
	}

	/**
	 * Source Export ファイルを取得する
	 * 
	 * @param key
	 *            キー
	 * @return Source Export ファイルリスト
	 */
	public ArrayList<String> getExportSourceFile() {
		RtcBuilderPlugin.getDefault().getPreferenceStore().setDefault(Export_Source_File, "");

		String resultTemp = RtcBuilderPlugin.getDefault().getPreferenceStore().getString(Export_Source_File);
		ArrayList<String> result = new ArrayList<String>();
		if (resultTemp.equals("")) { // defaultvalue
//			result = defaultExportSourceFile;
		} else {
			result = convertString2List(resultTemp);
		}
		return result;
	}
	/**
	 * Source Export ファイルを設定する
	 * 
	 * @param key
	 *            キー
	 * @param exportSourceFile
	 *            Source Export ファイルリスト
	 */
	public void setExportSourceFile(ArrayList<String> exportSourceFile) {
		ArrayList<String> oldExportSourceFile = getExportSourceFile();

		RtcBuilderPlugin.getDefault().getPreferenceStore().setValue(Export_Source_File, convertList2String(exportSourceFile));

		propertyChangeSupport.firePropertyChange(Export_Source_File, oldExportSourceFile, exportSourceFile);
	}

	/**
	 * Binary Export ファイル拡張子を取得する
	 * 
	 * @param key
	 *            キー
	 * @return Binary Export ファイル拡張子リスト
	 */
	public ArrayList<String> getExportBinaryExt() {
		RtcBuilderPlugin.getDefault().getPreferenceStore().setDefault(Export_Binary_EXT, "");

		String resultTemp = RtcBuilderPlugin.getDefault().getPreferenceStore().getString(Export_Binary_EXT);
		ArrayList<String> result = new ArrayList<String>();
		if (resultTemp.equals("")) { // defaultvalue
			result = defaultExportBinaryExt;
		} else {
			result = convertString2List(resultTemp);
		}
		return result;
	}
	/**
	 * Binary Export ファイル拡張子を設定する
	 * 
	 * @param key
	 *            キー
	 * @param exportBinaryExt
	 *            Binary Export ファイル拡張子リスト
	 */
	public void setExportBinaryExt(ArrayList<String> exportBinaryExt) {
		ArrayList<String> oldExportbinaryExt = getExportBinaryExt();

		RtcBuilderPlugin.getDefault().getPreferenceStore().setValue(Export_Binary_EXT, convertList2String(exportBinaryExt));

		propertyChangeSupport.firePropertyChange(Export_Binary_EXT, oldExportbinaryExt, exportBinaryExt);
	}

	/**
	 * Binary Export ファイルを取得する
	 * 
	 * @param key
	 *            キー
	 * @return Binary Export ファイルリスト
	 */
	public ArrayList<String> getExportBinaryFile() {
		RtcBuilderPlugin.getDefault().getPreferenceStore().setDefault(Export_Binary_File, "");

		String resultTemp = RtcBuilderPlugin.getDefault().getPreferenceStore().getString(Export_Binary_File);
		ArrayList<String> result = new ArrayList<String>();
		if (resultTemp.equals("")) { // defaultvalue
//			result = defaultExportBinaryFile;
		} else {
			result = convertString2List(resultTemp);
		}
		return result;
	}
	/**
	 * Binary Export ファイルを設定する
	 * 
	 * @param key
	 *            キー
	 * @param exportBinaryFile
	 *            Binary Export ファイルリスト
	 */
	public void setExportBinaryFile(ArrayList<String> exportBinaryFile) {
		ArrayList<String> oldExportBinaryFile = getExportBinaryFile();

		RtcBuilderPlugin.getDefault().getPreferenceStore().setValue(Export_Binary_File, convertList2String(exportBinaryFile));

		propertyChangeSupport.firePropertyChange(Export_Binary_File, oldExportBinaryFile, exportBinaryFile);
	}

	/**
	 * Source+Binary Export ファイル拡張子を取得する
	 * 
	 * @param key
	 *            キー
	 * @return Source+Binary Export ファイル拡張子リスト
	 */
	public ArrayList<String> getExportSourceBinaryExt() {
		RtcBuilderPlugin.getDefault().getPreferenceStore().setDefault(Export_SourceBinary_EXT, "");

		String resultTemp = RtcBuilderPlugin.getDefault().getPreferenceStore().getString(Export_SourceBinary_EXT);
		ArrayList<String> result = new ArrayList<String>();
		if (resultTemp.equals("")) { // defaultvalue
//			result = defaultExportSourceBinaryExt;
		} else {
			result = convertString2List(resultTemp);
		}
		return result;
	}
	/**
	 * Source+Binary Export ファイル拡張子を設定する
	 * 
	 * @param key
	 *            キー
	 * @param exportSourceBinaryExt
	 *            Source+Binary Export ファイル拡張子リスト
	 */
	public void setExportSourceBinaryExt(ArrayList<String> exportSourceBinaryExt) {
		ArrayList<String> oldExportSourceBinaryExt = getExportSourceBinaryExt();

		RtcBuilderPlugin.getDefault().getPreferenceStore().setValue(Export_SourceBinary_EXT, convertList2String(exportSourceBinaryExt));

		propertyChangeSupport.firePropertyChange(Export_SourceBinary_EXT, oldExportSourceBinaryExt, exportSourceBinaryExt);
	}

	/**
	 * Source+Binary Export ファイルを取得する
	 * 
	 * @param key
	 *            キー
	 * @return Source+Binary Export ファイルリスト
	 */
	public ArrayList<String> getExportSourceBinaryFile() {
		RtcBuilderPlugin.getDefault().getPreferenceStore().setDefault(Export_SourceBinary_File, "");

		String resultTemp = RtcBuilderPlugin.getDefault().getPreferenceStore().getString(Export_SourceBinary_File);
		ArrayList<String> result = new ArrayList<String>();
		if (resultTemp.equals("")) { // defaultvalue
			result = defaultExportSourceBinaryFile;
		} else {
			result = convertString2List(resultTemp);
		}
		return result;
	}
	/**
	 * Source+Binary Export ファイルを設定する
	 * 
	 * @param key
	 *            キー
	 * @param exportSourceBinaryFile
	 *            Source+Binary Export ファイルリスト
	 */
	public void setExportSourceBinaryFile(ArrayList<String> exportSourceBinaryFile) {
		ArrayList<String> oldExportSourceBinaryFile = getExportSourceBinaryFile();

		RtcBuilderPlugin.getDefault().getPreferenceStore().setValue(Export_SourceBinary_File, convertList2String(exportSourceBinaryFile));

		propertyChangeSupport.firePropertyChange(Export_SourceBinary_File, oldExportSourceBinaryFile, exportSourceBinaryFile);
	}

	/**
	 * @see PropertyChangeSupport#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	/**
	 * @see PropertyChangeSupport#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}
}
